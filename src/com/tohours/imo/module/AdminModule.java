package com.tohours.imo.module;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.tohours.imo.bean.Agent;
import com.tohours.imo.bean.Children;
import com.tohours.imo.bean.ChildrenNext;
import com.tohours.imo.bean.Config;
import com.tohours.imo.bean.Files;
import com.tohours.imo.bean.Peoples;
import com.tohours.imo.bean.Report;
import com.tohours.imo.bean.Resource;
import com.tohours.imo.bean.Setting;
import com.tohours.imo.bean.SubExcellence;
import com.tohours.imo.bean.Talent;
import com.tohours.imo.bean.TopExcellence;
import com.tohours.imo.bean.User;
import com.tohours.imo.exception.BusinessException;
import com.tohours.imo.util.Constants;
import com.tohours.imo.util.QuixUtils;
import com.tohours.imo.util.TohoursUtils;
import com.tohours.imo.util.ZipUtils;
@IocBean
//声明为Ioc容器中的一个Bean
@At("/attract")
//整个模块的路径前缀
@Ok("json:{locked:'password|salt',ignoreNull:false}")
//忽略password和salt属性,忽略空属性的json输出
@Fail("http:500")
//抛出异常的话,就走500页面
@Filters(@By(type=CheckSession.class, args={Constants.SESSION_USER, "/"})) //
//检查当前Session是否带me这个属性
public class AdminModule extends BaseModule{
	private String contextPath = Mvcs.getReq().getContextPath();
	@At
	@Filters
	// 覆盖UserModule类的@Filter设置,因为登陆可不能要求是个已经登陆的Session
	public Object login(@Param("name") String name,
			@Param("password") String password, HttpSession session) {
		User user = dao.fetch(User.class,
				Cnd.where("name", "=", name).and("password", "=", password));
		if (user == null) {
			return false;
		} else {
			session.setAttribute(Constants.SESSION_USER, user.getId());
			return true;
		}
	}
	@At
	@Ok(">>:/attract/index.jsp")
	// 跟其他方法不同,这个方法完成后就跳转首页了
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	@At
	@Ok(">>:/attract/login_index.jsp")
	public void loginIndex(){
		
	}
	@At
	public Object test(){
		NutMap nm=new NutMap();
		return nm.setv("succes", true).setv("msg", "test");
	}
	/**
	 * 导出报表并下载
	 */
	@At
	public Object exportPort(){
		JSONObject json=new JSONObject();
		json.put("success", true);
		String separator= System.getProperty("line.separator");
		try {
			NutMap nm=new NutMap();
			nm=(NutMap)getCount();
			String content="";
			content+="app下载次数是："+nm.get("appNum")+separator+separator+separator;
			@SuppressWarnings("unchecked")
			List<Map<String, String>> list=(List<Map<String, String>>) nm.get("msg");
			content+="按分公司划分的agent登录次数和上传人才库的数量如下："+separator+separator+separator;
			content+="分公司\t\t\tagent登录次数\t\t上传人才库数量"+separator+separator;
			for (int i = 0; i < list.size(); i++) {
				String subCompany=list.get(i).get("subCompany") == null ? "" : list.get(i).get("subCompany");
			//	String region=list.get(i).get("region") == null ? "" : list.get(i).get("region");
				String loginNum=list.get(i).get("loginNum") == null ? "" : list.get(i).get("loginNum");
				String talentNum=list.get(i).get("talentNum") == null ? "" : list.get(i).get("talentNum");
				content+=subCompany +"\t\t\t"+loginNum+"\t\t\t\t\t"+talentNum+separator;
			}
			String result=writeTxtFile(content);
			if(StringUtils.isNotEmpty(result)){
				json.put("msg", result);
			}else{
				json.put("success", false);
				json.put("msg", "数据写入失败！");
			}
		} catch (Exception e) {
			json.put("success", false);
			json.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * 写入到port.txt文件中
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String writeTxtFile(String content) throws Exception {
		String realPath = this.getRealPath(Constants.FILE_PATH);
		String path=this.getContextPath()+"/port.txt";
		File file=new File(realPath+path);
		File tmpFile=new File(realPath);
		if(!tmpFile.exists()){
			tmpFile.mkdirs();
		}
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			o.write(content.getBytes("UTF-8"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		if(flag){
			return "uploads"+path;
		}
		return null;
	}
	/**
	 * 获取contextPath路径
	 * @return
	 */
	
	private String getContextPath() {
		String contextPath = Mvcs.getReq().getContextPath();
		if ("/".equals(contextPath)) {
			contextPath = "";
		}
		return contextPath;
	}
	/**
	 * 报表首页
	 */
	@At
	@Ok("jsp:jsp.attract.port_index")
	public void portIndex(){
		
	}
	/**
	 * 按分公司查询agent登录次数和上传人才库的次数
	 * @param talent
	 * @return
	 */
	@At
	public  Object getCount(){
		NutMap nm=new NutMap();
		StringBuffer buf=new StringBuffer();
		buf.append(" SELECT sub_company_id subCompany,count(1) loginNum,");
		buf.append(" (SELECT count(1) from attract_talent al where al.sub_company_id = aa.sub_company_id)  as talentNum ");
		buf.append(" FROM attract_agent aa WHERE source = 1 or ut is not null GROUP BY sub_company_id");
		Sql sql =Sqls.create(buf.toString());
       // 返回多个
		sql.setCallback(new SqlCallback() {
		    public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
		        List<Map<String, String>> list = new LinkedList<Map<String,String>>();
		        while (rs.next()){
		        	Map<String, String> map = new HashMap<String, String>();
		        	map.put("subCompany", rs.getString("subCompany"));
		        //	map.put("region", rs.getString("region"));
		        	map.put("loginNum", rs.getString("loginNum"));
		        	map.put("talentNum", rs.getString("talentNum"));
		        	list.add(map);
		        }
		        return list;
		    }
		});
	   dao.execute(sql);
	   Integer appNum=this.getAppDownloadCount();
       return nm.setv("success", true).setv("msg", sql.getList(String.class)).setv("appNum", appNum);

	}
	/**
	 * 获取登录的营销员数
	 * @return
	 */
	@At
	public Integer getLoginAgentCount(){
		Cnd cnd = Cnd.where("1","=",1);
		cnd.and("source","=","1");
		cnd.or("ut", "is not", null);
		List<Agent>list=dao.query(Agent.class, cnd);
		if(list != null){
			return list.size();
		}
		return null;
	}
	/**
	 * 获取APP的下载次数
	 * @return
	 */
	@At
	public Integer getAppDownloadCount(){
		Cnd cnd=Cnd.where("1","=",1);
		cnd.and("keyA","=","APP_DOWNLOAD_COUNT");
		List<Config> list=dao.query(Config.class, cnd);
		if(list != null){
			return Integer.parseInt(list.get(0).getValue());
		}
		return null;
	}
	////////////////////////////////
	@At
	public int count() { // 统计用户数的方法,算是个测试点
		return dao.count(User.class);
	}
	@At
	public Object add(@Param("..") User user) { // 两个点号是按对象属性一一设置
		NutMap re = new NutMap();
		String msg = checkUser(user, true);
		if (msg != null) {
			return re.setv("ok", false).setv("msg", msg);
		}
		user = dao.insert(user);
		return re.setv("ok", true).setv("data", user);
	}

	@At
	public Object update(@Param("..") User user) {
		NutMap re = new NutMap();
		String msg = checkUser(user, false);
		if (msg != null) {
			return re.setv("ok", false).setv("msg", msg);
		}
		user.setName(null);// 不允许更新用户名
		user.setCreateTime(null);// 也不允许更新创建时间
		user.setUpdateTime(new Date());// 设置正确的更新时间
		dao.updateIgnoreNull(user);// 真正更新的其实只有password和salt
		return re.setv("ok", true);
	}

	@At
	public Object delete(@Param("id") int id, @Attr("me") int me) {
		if (me == id) {
			return new NutMap().setv("ok", false).setv("msg", "不能删除当前用户!!");
		}
		dao.delete(User.class, id); // 再严谨一些的话,需要判断是否为>0
		return new NutMap().setv("ok", true);
	}

	@At
	public Object query(@Param("name") String name, @Param("..") Pager pager) {
		Cnd cnd = Strings.isBlank(name) ? null : Cnd.where("name", "like", "%"
				+ name + "%");
		QueryResult qr = new QueryResult();
		qr.setList(dao.query(User.class, cnd, pager));
		pager.setRecordCount(dao.count(User.class, cnd));
		qr.setPager(pager);
		return qr; // 默认分页是第1页,每页20条
	}

	@At("/")
	@Ok("void")
	public void index() throws IOException {
		HttpServletResponse response = Mvcs.getResp();
		response.sendRedirect(contextPath + "/attract/resourceList?type=1");
	}

	@At
	public Object resourceSave(@Param("..") Resource resource)
			throws IOException {
		NutMap re = new NutMap();
		try {
			checkResource(resource);

			String filePaths = resource.getFilePaths();
			String fileNames = resource.getFileNames();
			String contentTypes = resource.getContentTypes();
			String[] arrPath = filePaths.split(",");
			String[] arrName = fileNames.split(",");
			String[] arrTypes = contentTypes.split(",");
			StringBuffer fileTypes = new StringBuffer();

			Date now = new Date();
			if (org.apache.commons.lang.StringUtils.isNotEmpty(resource.getId())) {
				String fileIds = resource.getFileIds();
				System.out.println("resource fileIds id is:"+fileIds);
				String[] arrFileIds = fileIds.split(",");
				if (arrFileIds.length != arrPath.length) {
					String[] tmp = arrFileIds;
					arrFileIds = new String[arrPath.length];
					System.arraycopy(tmp, 0, arrFileIds, 0, tmp.length);
				}
				Resource dbr = dao.fetch(Resource.class, resource.getId());
				for (int i = 0; i < arrPath.length; i++) {
					String path = arrPath[i];
					path = path.trim();
					String name = arrName[i];
					name = name.trim();
					String contentType = arrTypes[i];
					contentType = contentType.trim();
					String fileId = arrFileIds[i];
					if (Strings.isNotBlank(fileId)) {
						fileId = fileId.trim();
					}
					if (StringUtils.isEmpty(name)) {
						throw new BusinessException(String.format(
								"第%d个文件为空，所有文件必须上传！", i + 1));
					}

					if (Strings.isEmpty(fileId)) {
						String id = this.addFile(path, name, contentType);
						arrFileIds[i] = id + "";
					}
					if (i > 0) {
						fileTypes.append(",");
					}
					fileTypes.append(TohoursUtils.getFileExt(name));
				}
				dbr.setTitle(resource.getTitle());
				dbr.setContent(resource.getContent());
				dbr.setFileCounts(Long.valueOf(arrPath.length));
				dbr.setFileNames(fileNames);
				dbr.setFilePaths(filePaths);
				dbr.setContentTypes(contentTypes);
				dbr.setFileIds(Strings.join(",", arrFileIds));
				dbr.setFileTypes(fileTypes.toString());
				dbr.setUpdateTime(now);
				dao.update(dbr);

			} else {
				StringBuffer fileIds = new StringBuffer();
				for (int i = 0; i < arrPath.length; i++) {
					String path = arrPath[i];
					path = path.trim();
					String name = arrName[i];
					name = name.trim();
					String contentType = arrTypes[i];
					contentType = contentType.trim();
					if (StringUtils.isEmpty(name)) {
						throw new BusinessException(String.format(
								"第%d个文件为空，所有文件必须上传！", i + 1));
					}
					if (i > 0) {
						fileTypes.append(",");
						fileIds.append(",");
					}
					fileTypes.append(TohoursUtils.getFileExt(name));
					String fileId = addFile(path, name, contentType);
					fileIds.append(fileId);
				}
				resource.setCreateTime(now);
				resource.setUpdateTime(now);
				resource.setFileCounts((long) arrPath.length);
				resource.setFileTypes(fileTypes.toString());
				resource.setFileIds(fileIds.toString());
				resource.setDeleteFlag(false);
				resource = dao.insert(resource);
			}
			return re.setv("ok", true);
		} catch (BusinessException e) {
			return re.setv("ok", false).setv("msg", e.getMessage());
		}
	}
	private String addFile(String path, String name, String contentType)
			throws IOException {
		Date now = new Date();
		Files files = new Files();
		files.setName(name);
		files.setPath(path);
		files.setContentType(contentType);
		files.setCreateTime(now);
		files.setUpdateTime(now);
		File file = new File(this.getRealPath(path));
		files.setData(FileUtils.readFileToByteArray(file));
		files = dao.insert(files);
		return files.getId();
	}


	private void checkResource(Resource resource) {
		if (resource == null) {
			throw new BusinessException("对象不能为空！");
		} else {
			if (resource.equals("1")) {
				if (StringUtils.isEmpty(resource.getTitle())) {
					throw new BusinessException("标题不能为空！");
				}
				if (StringUtils.isEmpty(resource.getContent())) {
					throw new BusinessException("内容不能为空！");
				}
				if (StringUtils.isEmpty(resource.getFilePaths())) {
					throw new BusinessException("上传的文件不能为空！");
				}
				if (resource.getType() == null) {
					throw new BusinessException("资源的类型不能为空！");
				}
				if (Constants.isResourceExists(resource.getType()) == false) {
					throw new BusinessException("所传递的资源类型不正确，type="
							+ resource.getType());
				}
			} else if (resource.equals("3")) {
				if (StringUtils.isEmpty(resource.getFilePaths())) {
					throw new BusinessException("上传的文件不能为空！");
				}
			} else if (resource.equals("5")) {
				if (StringUtils.isEmpty(resource.getTitle())) {
					throw new BusinessException("标题不能为空！");
				}
				if (StringUtils.isEmpty(resource.getFilePaths())) {
					throw new BusinessException("上传的文件不能为空！");
				}
			}
		}
	}
	@At
	@Ok("jsp:jsp.attract.resource")
	public Object resource(@Param("type") String type, @Param("id") String id) {
		checkType(type);
		Long longType = Long.valueOf(type);
		NutMap re = new NutMap();
		Resource resource = null;
		if (id != null) {
			resource = dao.fetch(Resource.class, id);
			if (Constants.isTest) {
				System.out.println("id:" + id);
				System.out.println("resource:" + resource);
			}
		} else {
			resource = loadResourceByType(Long.valueOf(type));
		}
		String typeName = Constants.RESOURCE_TYPE.get(longType);
		return re.setv("resource", resource).setv("typeName", typeName);
	}

	private Resource loadResourceByType(Long type) {
		Cnd cnd = Cnd.where("type", "=", type).and("deleteFlag", "=", false);
		int count = dao.count(Resource.class, cnd);
		if (count == 1) {
			List<Resource> list = dao.query(Resource.class, cnd);
			return list.get(0);
		}
		return null;
	}

	private int countResourceByType(Long type) {
		Cnd cnd = Cnd.where("type", "=", type).and("deleteFlag", "=", false);
		return dao.count(Resource.class, cnd);
	}

	@At
	@Ok("jsp:jsp.attract.resource_edit_pre")
	public Object resourceEditPre(@Param("id") int id) {
		Resource resource = dao.fetch(Resource.class, id);
		if (Constants.isTest) {
			System.out.println("id:" + id);
			System.out.println("resource:" + resource);
		}
		return resource;
	}

	private void checkType(String type) {
		if (StringUtils.isEmpty(type)) {
			throw new BusinessException("类型不能为空！");
		}
		try {
			long longType = Long.parseLong(type);
			if (Constants.isResourceExists(longType) == false) {
				throw new BusinessException("资源类型不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("资源类型不正确");
		}
	}

	protected String checkUser(User user, boolean create) {
		if (user == null) {
			return "空对象";
		}
		if (create) {
			if (Strings.isBlank(user.getName())
					|| Strings.isBlank(user.getPassword()))
				return "用户名/密码不能为空";
		} else {
			if (Strings.isBlank(user.getPassword()))
				return "密码不能为空";
		}
		String passwd = user.getPassword().trim();
		if (6 > passwd.length() || passwd.length() > 12) {
			return "密码长度错误";
		}
		user.setPassword(passwd);
		if (create) {
			int count = dao.count(User.class,
					Cnd.where("name", "=", user.getName()));
			if (count != 0) {
				return "用户名已经存在";
			}
		} else {
			if (org.apache.commons.lang.StringUtils.isNotEmpty(user.getId())) {
				return "用户Id非法";
			}
		}
		if (user.getName() != null)
			user.setName(user.getName().trim());
		return null;
	}

	@Filters
	@At
	@Fail("jsp:jsp.500")
	public void error() {
		throw new RuntimeException();
	}

	@At
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	@Ok("json")
	public Object upload(@Param("file") TempFile tf) {
		NutMap re = new NutMap();
		FieldMeta meta = tf.getMeta();
		String path = copyToServer(tf);
		return re.setv("ok", true).setv("path", path)
				.setv("name", meta.getFileLocalName())
				.setv("contentType", meta.getContentType());
	}

	/**
	 * 将文件拷贝到attract/uplads目录下，并返回路径
	 * 
	 * @param tf
	 * @return
	 */
	private String copyToServer(TempFile tf) {
		FieldMeta fm = tf.getMeta();
		File file = tf.getFile();
		String realPath = getRealPath(Constants.FILE_PATH);
		Calendar calendar = Calendar.getInstance();
		String datePath = calendar.get(Calendar.YEAR) + "/";
		datePath += this.formatNum(calendar.get(Calendar.MONTH) + 1)
				+ this.formatNum(calendar.get(Calendar.DATE));
		String newFileName = TohoursUtils.randomKey(10) + fm.getFileExtension();
		String separator = "/";
		File dirFile = new File(realPath + separator + datePath + separator
				+ newFileName);
		File dirFolder = new File(realPath + separator + datePath + separator);
		if (dirFolder.exists() == false) {
			dirFolder.mkdirs();
		}
		try {
			FileUtils.copyFile(file, dirFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("文件拷贝出现错误！");
		}
		return Constants.FILE_PATH + separator + datePath + separator
				+ newFileName;
	}

	private String formatNum(int n) {

		return n < 10 ? "0" + n : n + "";
	}

	private String getRealPath(String path) {
		ServletContext sc = Mvcs.getServletContext();
		return sc.getRealPath(path);
	}

	@At
	@Ok("void")
	public void resourceList(@Param("type") String type) throws Exception {
		checkType(type);
		NutMap re = new NutMap();
		re.setv("type", type);
		if (countResourceByType(Long.valueOf(type)) == 1) {
			Mvcs.getResp().sendRedirect(contextPath + "/attract/resource?type=" + type);
			if (Constants.isTest) {
				System.out.println("debug:countResource is 1");
			}
		} else {
			Mvcs.getReq().getRequestDispatcher("/WEB-INF/jsp/attract/resource_list.jsp").forward(Mvcs.getReq(), Mvcs.getResp());
		}
	}

	@At
	public Object resourceListData(@Param("type") String type,@Param("key") String key, @Param("..") Pager pager) {
		try{
			NutMap re = new NutMap();
			checkType(type);
			Long longType = Long.valueOf(type);
			Cnd cnd = Cnd.where("type", "=", longType);
			if (StringUtils.isEmpty(key) == false) {
				cnd.and("title", "like", TohoursUtils.addPercent(key));
			}
			cnd.and("deleteFlag", "=", false).orderBy("squence", "asc");
			cnd.desc("createTime");
			if (Constants.isTest) {
				System.out.println("debug:type=" + type);
				System.out.println("debug:cnd=" + cnd);
			}
			QueryResult qr = new QueryResult();
			List<Resource> list = dao.query(Resource.class, cnd, pager);
			qr.setList(list);
			pager.setRecordCount(dao.count(Resource.class, cnd));
			qr.setPager(pager);
			System.out.println("qr list size : "+qr.getList().size());
			System.out.println("resource list size : "+list.size());
			return re.setv("qr", qr).setv("title", Constants.RESOURCE_TYPE.get(longType));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@At
	public Object resourceDelete(@Param("id") int id) {
		NutMap re = new NutMap();
		try {
			Resource resource = dao.fetch(Resource.class, id);
			resource.setDeleteFlag(true);
			dao.update(resource);
			re.setv("success", true);
		} catch (Exception e) {
			re.setv("success", false);
			re.setv("msg", e.getMessage());
		}
		return re;
	}

	@At
	@Ok("void")
	public void resourceFile(@Param("id") String id) throws IOException {
		Files files = dao.fetch(Files.class, id);
		HttpServletResponse response = Mvcs.getResp();
		response.setContentType(files.getContentType());
		response.getOutputStream().write(files.getData());
	}
	@At
	@Ok("void")
	public void reportFile(@Param("id") String id) throws IOException {
		Report report = dao.fetch(Report.class, id);
		HttpServletResponse response = Mvcs.getResp();
		response.getOutputStream().write(report.getData());
	}
	

	/**
	 * 人才资源列表显示
	 * 
	 * @param type
	 *            类型：人才资源
	 * @param key
	 *            搜索：关键字
	 * @param pager
	 *            分页
	 * @return 返回list与pager
	 */
	@SuppressWarnings("unchecked")
	@At
	public Object talentList(@Param("key") String key, @Param("..") Pager pager) {
		NutMap um = new NutMap();
		Cnd cnd = Cnd.where("1", "=", "1");
		System.out.println("key:"+key);
		if (StringUtils.isEmpty(key) == false) {
			cnd.and("agentCode", "=", key);
		}
		cnd.orderBy("createTime", "desc");
		QueryResult qr = new QueryResult();
		qr.setList(dao.query(Talent.class, cnd, pager));
		pager.setRecordCount(dao.count(Talent.class, cnd));
		qr.setPager(pager);
		
		List<Talent> list=(List<Talent>) qr.getList();
		List<JSONObject> listJson=new ArrayList<JSONObject>();
		for (int i = 0; i < list.size(); i++) {
			JSONObject json=new JSONObject();
			System.out.println("talentName is :"+list.get(i).getName());
			Agent agent = dao.fetch(Agent.class,list.get(i).getAgentId());
			json.put("agentName", agent.getName());
			System.out.println("agentName is : "+ agent.getName());
			json.put("subCompanyId", agent.getSubCompanyId());
			json.put("name", list.get(i).getName());
			json.put("sex", list.get(i).getSex());
			json.put("birthday", list.get(i).getBirthday());
			json.put("agentCode", agent.getAgentCode());
			Report report=getReportOrderByUpdateTime(list.get(i).getId());
			if(report != null){
				json.put("reportId",report.getId());
				json.put("reportFileName", report.getFileName());
				json.put("insertTime", TohoursUtils.date2string(report.getCreateTime(),"yyyy-MM-dd"));
			}else{
				json.put("reportId",null);
				json.put("reportFileName", null);
				json.put("insertTime", null);
			}
			System.out.println("json is :"+json.toString());
			listJson.add(i, json);
		}
		for (int i = 0; i < listJson.size(); i++) {
			System.out.println("---------------------listJson is :"+listJson.get(i).toString());
		}
		return um.setv("list", listJson).setv("pager", qr.getPager()); // 默认分页是第1页,每页20条
	}
	/**
	 * 更新插入时间倒序选出第一个
	 * @param talentId
	 * @return
	 */
	private Report getReportOrderByUpdateTime(String talentId){
		Cnd cnd=Cnd.where("1","=",1);
		cnd.and("talentId","=",talentId);
		cnd.orderBy("updateTime", "desc");
		List<Report> list = dao.query(Report.class, cnd, null);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 列表显示
	 * 
	 * @param pager
	 * @return
	 */
	@At
	public Object settingList(@Param("key") String key, @Param("..") Pager pager) {
		NutMap nm = new NutMap();
		Cnd cnd = Cnd.where("1", "=", "1");
		if (StringUtils.isEmpty(key) == false) {
			cnd.and("agentId", "like", TohoursUtils.addPercent(key));
		}
		cnd.orderBy("createTime", "desc");
		QueryResult qr = new QueryResult();
		qr.setList(dao.query(Setting.class, cnd, pager));
		pager.setRecordCount(dao.count(Setting.class, cnd));
		qr.setPager(pager);
		return nm.setv("list", qr.getList()).setv("pager", qr.getPager()); // 默认分页是第1页,每页20条
	}

	// 人力资源首页
	@At
	@Ok("jsp:jsp.attract.talent_list")
	public void talentIndex() {
	}

	// 个性化设置首页
	@At
	@Ok("jsp:jsp.attract.setting_list")
	public void settingIndex() {

	}

	// peoples首页
	@At
	@Ok("jsp:jsp.attract.peoples_list")
	public Object peoplesIndex(@Param("sub_excel_id") String sub_excel_id) {
		NutMap nm = new NutMap();
		Mvcs.getReq().setAttribute(
				"top_id",
				dao.fetch(SubExcellence.class, sub_excel_id).getTop_excel_id());
		return nm.setv("sub_excel_id", sub_excel_id).setv("addFlag", "peoples");
	}
	// peoples的列表显示供ajax调用
		@At
		public Object peoplesList(@Param("key") String key,
				@Param("..") Pager pager, @Param("sub_excel_id") String sub_excel_id) {
			NutMap re = new NutMap();
			Cnd cnd = Cnd.where("1", "=", "1");
			if (StringUtils.isEmpty(key) == false) {
				cnd.and("name", "like", TohoursUtils.addPercent(key));
			}
			if (StringUtils.isEmpty(sub_excel_id)) {
				throw new BusinessException("sub_excel_id不能为空");
			} else {
				cnd.and("sub_excel_id", "=", sub_excel_id);
			}
			cnd.and("deleteFlag", "=", false);
			cnd.orderBy("squence","asc");
			cnd.desc("createTime");
			QueryResult qr = new QueryResult();
			qr.setList(dao.query(Peoples.class, cnd, pager));
			pager.setRecordCount(dao.count(Peoples.class, cnd));
			qr.setPager(pager);
			Mvcs.getReq().setAttribute(
					"top_id",
					dao.fetch(SubExcellence.class, sub_excel_id)
							.getTop_excel_id() + "");
			return re.setv("list", qr.getList()).setv("pager", qr.getPager());
		}

		// peoples的新增与修改
		@At
		@Ok("jsp:jsp.attract.peoples")
		public Object peoplesPre(@Param("id") String id,
				@Param("sub_excel_id") String sub_excel_id) {
			NutMap re = new NutMap();
			Peoples peoples = null;
			if (id != null) {
				peoples = dao.fetch(Peoples.class, id);
			}
			return re.setv("peoples", peoples).setv("addFlag", "peoples")
					.setv("sub_excel_id", sub_excel_id);
		}

		// peoples的保存与更新
		@At
		public Object peoplesSave(@Param("..") Peoples peoples,
				@Param("markName") String markName,@Param("desc") String desc) throws IOException {
		NutMap re = new NutMap();
			Date now=new Date();
			try {
				checkPeoples(peoples);
				String filePath = peoples.getFilePath();
				String fileName = peoples.getFileName();
				String contentType = peoples.getContentType();
				String fileType=TohoursUtils.getFileExt(fileName);
				if (org.apache.commons.lang.StringUtils.isNotEmpty(peoples.getId())) {
					String fileId = peoples.getFileId();
					System.out.println("peoples fileId  is : "+fileId);
					Peoples pl = dao.fetch(Peoples.class, peoples.getId());
					if (Strings.isEmpty(fileId)) {
						fileId=this.addFile(filePath, fileName, contentType);
						System.out.println("new fileId is : "+fileId);
					}
					pl.setContentType(contentType);
					pl.setFileId(fileId);
					pl.setFileName(fileName);
					pl.setFilePath(filePath);
					pl.setFileType(fileType);
					pl.setJoinDate(peoples.getJoinDate());
					pl.setName(peoples.getName());
					pl.setSubCompany(peoples.getSubCompany());
//					pl.setNewMark(peoples.getNewMark());
//					pl.setOldMark(peoples.getOldMark());
					dealMark(pl, markName, peoples.getOldMark(), peoples.getNewMark() ,desc);
					pl.setOldJob(peoples.getOldJob());
					pl.setShareWord(peoples.getShareWord());
					dao.update(pl);
				} else {
					String fileId = addFile(filePath, fileName, contentType);
					peoples.setDeleteFlag(false);
					peoples.setCreateTime(now);
					peoples.setUpdateTime(now);
					peoples.setFileType(fileType);
					peoples.setFileId(fileId);
					dealMark(peoples, markName, peoples.getOldMark(), peoples.getNewMark(),desc);
					peoples = dao.insert(peoples);
				}
				return re.setv("success", true).setv("id", peoples.getId());
			} catch (BusinessException e) {
				e.printStackTrace();
				return re.setv("success", false).setv("msg", e.getMessage());
			}
		}
		private void dealMark(Peoples pl, String markName, String oldMark, String newMark ,String desc) {
			if(Constants.isTest){
				System.out.println("debug::" + markName);
			}
			String[] arrMarkNames = markName.split(",");
			String[] arrOldMarks = oldMark.split(",");
			String[] arrNewMarks = newMark.split(",");
			String[] arrDesc=desc.split(",");
			List<Map<String, String>> listNewMark = new ArrayList<Map<String, String>>();
			List<Map<String, String>> listOldMark = new ArrayList<Map<String, String>>();
			List<Map<String, String>> listMarks = new ArrayList<Map<String, String>>();
			for (int i = 0; i < 5; i++) {
				String mn = arrMarkNames[i];//名字
				mn = mn.trim();
				String om = arrOldMarks[i];//oldMark
				om = om.trim();
				String nm = arrNewMarks[i];//newMark
				nm = nm.trim();
				String de=arrDesc[i];//desc描述
				de=de.trim();
				Map<String, String> mapNewMark = new HashMap<String, String>();
				mapNewMark.put("name", mn);
				mapNewMark.put("mark", nm);
				Map<String, String> mapOldMark = new HashMap<String, String>();
				mapOldMark.put("name", mn);
				mapOldMark.put("mark", om);
				Map<String, String> mapMarks = new HashMap<String, String>();
				mapMarks.put("name", mn);
				mapMarks.put("new_mark", nm);
				mapMarks.put("old_mark", om);
				mapMarks.put("desc", de);
				listNewMark.add(mapNewMark);
				listOldMark.add(mapOldMark);
				listMarks.add(mapMarks);
			}
			
			pl.setOldMark(Json.toJson(listOldMark,JsonFormat.compact()));
			pl.setNewMark(Json.toJson(listNewMark,JsonFormat.compact()));
			pl.setMarks(Json.toJson(listMarks,JsonFormat.compact()));
		}

		// peoples保存时的校验
		private void checkPeoples(Peoples peoples) {
			if (peoples == null) {
				throw new BusinessException("对象不能为空！");
			} else {
				if (StringUtils.isEmpty(peoples.getName())) {
					throw new BusinessException("name不能为空");
				}
				if (StringUtils.isEmpty(peoples.getJoinDate() + "")) {
					throw new BusinessException("join_date不能为空！");
				}
				if (StringUtils.isEmpty(peoples.getShareWord())) {
					throw new BusinessException("share_word不能为空！");
				}
				if (StringUtils.isEmpty(peoples.getFilePath())) {
					throw new BusinessException("上传的文件不能为空！");
				}
			}
		}
		@At
		public Object peoplesDelete(@Param("id") String id) {

			NutMap re = new NutMap();
			try {
				Peoples peoples = dao.fetch(Peoples.class, id);
				peoples.setDeleteFlag(true);
				dao.update(peoples);
				re.setv("success", true);
			} catch (Exception e) {
				re.setv("success", false);
				re.setv("msg", e.getMessage());
			}
			return re;
		}
		// topExcellence首页
		@At
		@Ok("jsp:jsp.attract.top_excellence_list")
		public Object topExcellenceIndex() throws IOException {
			NutMap nm = new NutMap();
			return nm.setv("addFlag", "topexcellence");
		}

		@At
		public Object topExcellenceList(@Param("key") String key,
				@Param("..") Pager pager) {
			NutMap nm = new NutMap();
			Cnd cnd = Cnd.where("1", "=", "1");
			if (StringUtils.isEmpty(key) == false) {
				cnd.and("name", "like", TohoursUtils.addPercent(key));
			}
			cnd.and("deleteFlag", "=", false);
			cnd.orderBy("squence","asc");
			cnd.desc("createTime");
			QueryResult qr = new QueryResult();
			qr.setList(dao.query(TopExcellence.class, cnd, pager));
			List<TopExcellence> list=dao.query(TopExcellence.class, cnd, pager);
			for(int i=0;i<list.size();i++){
				System.out.println("名字："+list.get(i).getName()+"squenc："+list.get(i).getSquence());
			}
			pager.setRecordCount(dao.count(TopExcellence.class, cnd));
			qr.setPager(pager);
			return nm.setv("qr", qr).setv("addFlag", "topexcellence");
		}

		@At
		public Object topExcellenceSave(@Param("..") TopExcellence topExcellence)
				throws IOException {
			NutMap re = new NutMap();
			try {
				checkTopExcellence(topExcellence);
				String filePaths = topExcellence.getFilePaths();
				String fileNames = topExcellence.getFileNames();
				String contentTypes = topExcellence.getContentTypes();
				String[] arrPath = filePaths.split(",");
				String[] arrName = fileNames.split(",");
				String[] arrTypes = contentTypes.split(",");
				StringBuffer fileTypes = new StringBuffer();
				Date now = new Date();
				if (org.apache.commons.lang.StringUtils.isNotEmpty(topExcellence.getId())) {
					String fileIds = topExcellence.getFileIds();
					String[] arrFileIds = fileIds.split(",");
					if (arrFileIds.length != arrPath.length) {
						String[] tmp = arrFileIds;
						arrFileIds = new String[arrPath.length];
						System.arraycopy(tmp, 0, arrFileIds, 0, tmp.length);
					}
					TopExcellence dbr = dao.fetch(TopExcellence.class,
							topExcellence.getId());
					for (int i = 0; i < arrPath.length; i++) {
						String path = arrPath[i];
						path = path.trim();
						String name = arrName[i];
						name = name.trim();
						String contentType = arrTypes[i];
						contentType = contentType.trim();
						String fileId = arrFileIds[i];
						if (Strings.isNotBlank(fileId)) {
							fileId = fileId.trim();
						}
						if (StringUtils.isEmpty(name)) {
							throw new BusinessException(String.format(
									"第%d个文件为空，所有文件必须上传！", i + 1));
						}

						if (Strings.isEmpty(fileId)) {
							String id = this.addFile(path, name, contentType);
							arrFileIds[i] = id + "";
						}
						if (i > 0) {
							fileTypes.append(",");
						}
						fileTypes.append(TohoursUtils.getFileExt(name));
					}
					dbr.setContentTypes(contentTypes);
					dbr.setUpdateTime(now);
					dbr.setCreateTime(now);
					dbr.setFileCounts(Long.valueOf(arrPath.length));
					dbr.setFileIds(Strings.join(",", arrFileIds));
					dbr.setFileNames(fileNames);
					dbr.setFilePaths(filePaths);
					dbr.setFileTypes(fileTypes.toString());
					dbr.setName(topExcellence.getName());
					dao.update(dbr);

				} else {
					StringBuffer fileIds = new StringBuffer();
					for (int i = 0; i < arrPath.length; i++) {
						String path = arrPath[i];
						path = path.trim();
						String name = arrName[i];
						name = name.trim();
						String contentType = arrTypes[i];
						contentType = contentType.trim();
						if (StringUtils.isEmpty(name)) {
							throw new BusinessException(String.format(
									"第%d个文件为空，所有文件必须上传！", i + 1));
						}
						if (i > 0) {
							fileTypes.append(",");
							fileIds.append(",");
						}
						fileTypes.append(TohoursUtils.getFileExt(name));
						String fileId = addFile(path, name, contentType);
						fileIds.append(fileId);
					}
					topExcellence.setDeleteFlag(false);
					topExcellence.setUpdateTime(now);
					topExcellence.setFileCounts((long) arrPath.length);
					topExcellence.setFileTypes(fileTypes.toString());
					topExcellence.setFileIds(fileIds.toString());
					topExcellence = dao.insert(topExcellence);
				}
				return re.setv("success", true).setv("id", topExcellence.getId());
			} catch (BusinessException e) {
				return re.setv("success", false).setv("msg", e.getMessage());
			}
		}

		public void checkTopExcellence(TopExcellence topExcellence) {
			if (topExcellence == null) {
				throw new BusinessException("对象不能为空！");
			} else {
				if (StringUtils.isEmpty(topExcellence.getName())) {
					throw new BusinessException("标题不能为空！");
				}
				if (StringUtils.isEmpty(topExcellence.getFilePaths())) {
					throw new BusinessException("上传的文件不能为空！");
				}
			}

		}
		@At
		@Ok("jsp:jsp.attract.top_excellence")
		public Object topExcellencePre(@Param("id") String id) {
			NutMap re = new NutMap();
			TopExcellence topExcellence = null;
			if (id != null) {
				topExcellence = dao
						.fetch(TopExcellence.class, id);
			}
			return re.setv("topExcellence", topExcellence).setv("addFlag",
					"topexcellence");
		}

		@At
		public Object topExcellenceDelete(@Param("id") String id) {
			NutMap re = new NutMap();
			try {
				TopExcellence topExcellence = dao.fetch(TopExcellence.class,id);
				topExcellence.setDeleteFlag(true);
				dao.update(topExcellence);
				re.setv("success", true);
			} catch (Exception e) {
				re.setv("success", false);
				re.setv("msg", e.getMessage());
			}
			return re;
		}

		// subExcellence首页
		@At
		@Ok("jsp:jsp.attract.sub_excellence_list")
		public Object subExcellenceIndex(@Param("top_excel_id") String top_excel_id) {
			NutMap nm = new NutMap();
			return nm.setv("top_excel_id", top_excel_id).setv("addFlag",
					"subexcellence");
		}

		@At
		public Object subExcellenceList(@Param("key") String key,
				@Param("..") Pager pager, @Param("top_excel_id") String top_excel_id) {
			NutMap nm = new NutMap();
			Cnd cnd = Cnd.where("1", "=", "1");
			if (StringUtils.isEmpty(key) == false) {
				cnd.and("name", "like", TohoursUtils.addPercent(key));
			}
			if (StringUtils.isEmpty(top_excel_id)) {
				throw new BusinessException("top_excel_id不能为空");
			} else {
				cnd.and("top_excel_id", "=", top_excel_id);
			}
			cnd.and("deleteFlag", "=", false);
			cnd.orderBy("squence","asc");
			cnd.desc("createTime");
			QueryResult qr = new QueryResult();
			qr.setList(dao.query(SubExcellence.class, cnd, pager));
			pager.setRecordCount(dao.count(SubExcellence.class, cnd));
			qr.setPager(pager);
			return nm.setv("qr", qr);
		}

		@At
		public Object subExcellenceSave(@Param("..") SubExcellence subExcellence)
				throws IOException {
			NutMap re = new NutMap();
			try {
				checkSubExcellence(subExcellence);
				String filePaths = subExcellence.getFilePaths();
				String fileNames = subExcellence.getFileNames();
				String contentTypes = subExcellence.getContentTypes();
				String[] arrPath = filePaths.split(",");
				String[] arrName = fileNames.split(",");
				String[] arrTypes = contentTypes.split(",");
				StringBuffer fileTypes = new StringBuffer();
				Date now = new Date();
				if (org.apache.commons.lang.StringUtils.isNotEmpty(subExcellence.getId())) {
					String fileIds = subExcellence.getFileIds();
					String[] arrFileIds = fileIds.split(",");
					if (arrFileIds.length != arrPath.length) {
						String[] tmp = arrFileIds;
						arrFileIds = new String[arrPath.length];
						System.arraycopy(tmp, 0, arrFileIds, 0, tmp.length);
					}
					SubExcellence dbr = dao.fetch(SubExcellence.class,
							subExcellence.getId());
					for (int i = 0; i < arrPath.length; i++) {
						String path = arrPath[i];
						path = path.trim();
						String name = arrName[i];
						name = name.trim();
						String contentType = arrTypes[i];
						contentType = contentType.trim();
						String fileId = arrFileIds[i];
						if (Strings.isNotBlank(fileId)) {
							fileId = fileId.trim();
						}
						if (StringUtils.isEmpty(name)) {
							throw new BusinessException(String.format(
									"第%d个文件为空，所有文件必须上传！", i + 1));
						}

						if (Strings.isEmpty(fileId)) {
							String id = this.addFile(path, name, contentType);
							arrFileIds[i] = id + "";
						}
						if (i > 0) {
							fileTypes.append(",");
						}
						fileTypes.append(TohoursUtils.getFileExt(name));
					}
					dbr.setContentTypes(contentTypes);
					dbr.setUpdateTime(now);
					dbr.setFileCounts(Long.valueOf(arrPath.length));
					dbr.setFileIds(Strings.join(",", arrFileIds));
					dbr.setFileNames(fileNames);
					dbr.setFilePaths(filePaths);
					dbr.setFileTypes(fileTypes.toString());
					dbr.setName(subExcellence.getName());
					dao.update(dbr);

				} else {
					StringBuffer fileIds = new StringBuffer();
					for (int i = 0; i < arrPath.length; i++) {
						String path = arrPath[i];
						path = path.trim();
						String name = arrName[i];
						name = name.trim();
						String contentType = arrTypes[i];
						contentType = contentType.trim();
						if (StringUtils.isEmpty(name)) {
							throw new BusinessException(String.format(
									"第%d个文件为空，所有文件必须上传！", i + 1));
						}
						if (i > 0) {
							fileTypes.append(",");
							fileIds.append(",");
						}
						fileTypes.append(TohoursUtils.getFileExt(name));
						String fileId = addFile(path, name, contentType);
						fileIds.append(fileId);
					}
					subExcellence.setDeleteFlag(false);
					subExcellence.setCreateTime(now);
					subExcellence.setUpdateTime(now);
					subExcellence.setFileCounts((long) arrPath.length);
					subExcellence.setFileTypes(fileTypes.toString());
					subExcellence.setFileIds(fileIds.toString());
					subExcellence = dao.insert(subExcellence);
				}
				return re.setv("success", true).setv("id", subExcellence.getId());
			} catch (BusinessException e) {
				return re.setv("success", false).setv("msg", e.getMessage());
			}
		}

		public void checkSubExcellence(SubExcellence subExcellence) {
			if (subExcellence == null) {
				throw new BusinessException("对象不能为空！");
			} else {
				if (StringUtils.isEmpty(subExcellence.getName())) {
					throw new BusinessException("标题不能为空！");
				}
				if (StringUtils.isEmpty(subExcellence.getFilePaths())) {
					throw new BusinessException("上传的文件不能为空！");
				}
			}
		}

		@At
		@Ok("jsp:jsp.attract.sub_excellence")
		public Object subExcellencePre(@Param("id") String id,
				@Param("top_excel_id") String top_excel_id) {
			NutMap re = new NutMap();
			SubExcellence subExcellence = null;
			if (id != null) {
				subExcellence = dao.fetch(SubExcellence.class, id);
			}
			return re.setv("subExcellence", subExcellence)
					.setv("addFlag", "subexcellence")
					.setv("top_excel_id", top_excel_id);
		}

		@At
		public Object subExcellenceDelete(@Param("id") String id) {
			NutMap re = new NutMap();
			try {
				SubExcellence subExcellence = dao.fetch(SubExcellence.class,id);
				subExcellence.setDeleteFlag(true);
				dao.update(subExcellence);
				re.setv("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				re.setv("success", false);
				re.setv("msg", e.getMessage());
			}
			return re;
		}
		// children首页
				@At
				@Ok("jsp:jsp.attract.children_list")
				public Object childrenIndex(@Param("resourceId") String resourceId) {
					NutMap nm = new NutMap();
					return nm.setv("resourceId", resourceId).setv("addFlag",
							"children");
				}	
				@At
				public Object childrenList(@Param("key") String key,
						@Param("..") Pager pager, @Param("resourceId") String resourceId) {
					NutMap nm = new NutMap();
					Cnd cnd = Cnd.where("1", "=", "1");
					if (StringUtils.isEmpty(key) == false) {
						cnd.and("name", "like", TohoursUtils.addPercent(key));
					}
					if (StringUtils.isEmpty(resourceId)) {
						throw new BusinessException("resourceId不能为空");
					} else {
						cnd.and("resourceId", "=", resourceId);
					}
					cnd.orderBy("squence","asc");
					cnd.desc("createTime");
					QueryResult qr = new QueryResult();
					List<Children> list=dao.query(Children.class, cnd, pager);
					qr.setList(dao.query(Children.class, cnd, pager));
					pager.setRecordCount(dao.count(Children.class, cnd));
					qr.setPager(pager);
					System.out.println("children list size is :"+list.size());
					for (int i = 0; i < list.size(); i++) {
						System.out.println("children is :"+list.get(i).getName());
					}
					return nm.setv("qr", qr);
				}
				// childrenIndex首页
				@At
				@Ok("jsp:jsp.attract.children_next_list")
				public Object childrenNextIndex(@Param("childrenId") String childrenId) {
					NutMap nm = new NutMap();
					System.out.println("childrenId is :"+childrenId);
					Mvcs.getReq().setAttribute("resourceId",dao.fetch(Children.class, childrenId).getResourceId());
					return nm.setv("childrenId", childrenId).setv("addFlag", "childrenNext");
				}
				// childrenNext的列表显示供ajax调用
				@At
				public Object childrenNextList(@Param("key") String key,
						@Param("..") Pager pager, @Param("childrenId") String childrenId) {
					NutMap re = new NutMap();
					Cnd cnd = Cnd.where("1", "=", "1");
					if (StringUtils.isEmpty(key) == false) {
						cnd.and("name", "like", TohoursUtils.addPercent(key));
					}
					if (StringUtils.isEmpty(childrenId)) {
						throw new BusinessException("childrenId不能为空");
					} else {
						cnd.and("childrenId", "=", childrenId);
					}
					cnd.orderBy("squence","asc");
					cnd.desc("createTime");
					QueryResult qr = new QueryResult();
					qr.setList(dao.query(ChildrenNext.class, cnd, pager));
					pager.setRecordCount(dao.count(ChildrenNext.class, cnd));
					qr.setPager(pager);
					Mvcs.getReq().setAttribute(
							"childrenId",
							dao.fetch(Children.class, childrenId).getId());
					return re.setv("list", qr.getList()).setv("pager", qr.getPager());
				}
				
				//children的编辑
				@At
				@Ok("jsp:jsp.attract.children_pre")
				public Object childrenPre(@Param("id") String id,@Param("resourceId")String resourceId) {
					NutMap re = new NutMap();
					Children children = null;
					if (id != null) {
						children = dao.fetch(Children.class, id);
					}
					return re.setv("children", children).setv("addFlag","children");
				}
				//children的保存
				@At
				public Object childrenSave(@Param("..") Children children)
						throws IOException {
					NutMap re = new NutMap();
					try {
						if(children.getNextFlag() == null){
							children.setNextFlag(false);
						}
						checkChildren(children);
						String filePaths = children.getFilePaths();
						String fileNames = children.getFileNames();
						String contentTypes = children.getContentTypes();
						String[] arrPath = filePaths.split(",");
						String[] arrName = fileNames.split(",");
						String[] arrTypes = contentTypes.split(",");
						StringBuffer fileTypes = new StringBuffer();
						Date now = new Date();
						if (org.apache.commons.lang.StringUtils.isNotEmpty(children.getId())) {
							Children dbr = dao.fetch(Children.class,children.getId());
							if(!children.getNextFlag()){//
								if(children.getFileIds() == null || "".equals(children.getFileIds())){
									StringBuffer fileIds = new StringBuffer();
									for (int i = 0; i < arrPath.length; i++) {
										String path = arrPath[i];
										path = path.trim();
										String name = arrName[i];
										name = name.trim();
										String contentType = arrTypes[i];
										contentType = contentType.trim();
										if (StringUtils.isEmpty(name)) {
											throw new BusinessException(String.format(
													"第%d个文件为空，所有文件必须上传！", i + 1));
										}
										if (i > 0) {
											fileTypes.append(",");
											fileIds.append(",");
										}
										fileTypes.append(TohoursUtils.getFileExt(name));
										String fileId = addFile(path, name, contentType);
										fileIds.append(fileId);
										dbr.setFileIds(fileIds.toString());
									}
								}else{
									String fileIds = children.getFileIds();
									String[] arrFileIds = fileIds.split(",");
									if (arrFileIds.length != arrPath.length) {
										String[] tmp = arrFileIds;
										arrFileIds = new String[arrPath.length];
										System.arraycopy(tmp, 0, arrFileIds, 0, tmp.length);
									}
								for (int i = 0; i < arrPath.length; i++) {
									String path = arrPath[i];
									path = path.trim();
									String name = arrName[i];
									name = name.trim();
									String contentType = arrTypes[i];
									contentType = contentType.trim();
									String fileId = arrFileIds[i];
									if (Strings.isNotBlank(fileId)) {
										fileId = fileId.trim();
									}
									if (StringUtils.isEmpty(name)) {
										throw new BusinessException(String.format(
												"第%d个文件为空，所有文件必须上传！", i + 1));
									}
									if (Strings.isEmpty(fileId)) {
										String id = this.addFile(path, name, contentType);
										arrFileIds[i] = id + "";
									}
									if (i > 0) {
										fileTypes.append(",");
									}
									fileTypes.append(TohoursUtils.getFileExt(name));
								}
								dbr.setFileIds(Strings.join(",", arrFileIds));
							}
							}else{
								dbr.setFileIds(null);
							}
							System.out.println("nextFlag is:"+children.getNextFlag());
							dbr.setFileCounts(children.getNextFlag() == false?Long.valueOf(arrPath.length):null);
							dbr.setFileNames(children.getNextFlag() == false?fileNames:null);
							dbr.setFilePaths(children.getNextFlag()== false?filePaths:null);
							dbr.setFileTypes(children.getNextFlag()== false?fileTypes.toString():null);	
							dbr.setContentTypes(children.getNextFlag()== false?contentTypes:null);
							
							dbr.setUpdateTime(now);
							dbr.setName(children.getName());
							dbr.setNextFlag(children.getNextFlag());
							dao.update(dbr);
							System.out.println("-----------------更新children--------------------------");
						} else {
							StringBuffer fileIds = new StringBuffer();
							if(!children.getNextFlag()){
								for (int i = 0; i < arrPath.length; i++) {
									String path = arrPath[i];
									path = path.trim();
									String name = arrName[i];
									name = name.trim();
									String contentType = arrTypes[i];
									contentType = contentType.trim();
									if (StringUtils.isEmpty(name)) {
										throw new BusinessException(String.format(
												"第%d个文件为空，所有文件必须上传！", i + 1));
									}
									if (i > 0) {
										fileTypes.append(",");
										fileIds.append(",");
									}
									fileTypes.append(TohoursUtils.getFileExt(name));
									String fileId = addFile(path, name, contentType);
									fileIds.append(fileId);
								}
							}
							children.setCreateTime(now);
							children.setUpdateTime(now);
							System.out.println("nextFlag is:"+children.getNextFlag());
							children.setFileCounts(children.getNextFlag() == false?(long) arrPath.length:null);
							children.setFileTypes(children.getNextFlag() == false?fileTypes.toString():null);
							children.setFileIds(children.getNextFlag() == false?fileIds.toString():null);
							children = dao.insert(children);
							System.out.println("-----------------新增children--------------------------");
						}
						return re.setv("success", true).setv("id", children.getId());
					} catch (BusinessException e) {
						return re.setv("success", false).setv("msg", e.getMessage());
					}
				}
				public void checkChildren(Children children) {
					if (children == null) {
						throw new BusinessException("对象不能为空！");
					} else {
						if (StringUtils.isEmpty(children.getName())) {
							throw new BusinessException("标题不能为空！");
						}
						System.out.println("children nextflag is :"+children.getNextFlag());
						if (StringUtils.isEmpty(children.getFilePaths()) && children.getNextFlag() == false) {
							throw new BusinessException("上传的文件不能为空！");
						}
					}
				}
				// childrenNext的新增与修改
				@At
				@Ok("jsp:jsp.attract.children_next_pre")
				public Object childrenNextPre(@Param("id") String id,
						@Param("childrenId") String childrenId) {
					NutMap re = new NutMap();
					ChildrenNext childrenNext = null;
					if (id != null) {
						childrenNext = dao.fetch(ChildrenNext.class, id);
					}
					return re.setv("childrenNext", childrenNext).setv("addFlag", "childrenNext")
							.setv("childrenId", childrenId);
				}
				// childrenNext的保存与更新
				@At
				public Object childrenNextSave(@Param("..") ChildrenNext childrenNext) throws IOException {
					NutMap re = new NutMap();
					try {
						checkChildrenNext(childrenNext);
						String filePaths = childrenNext.getFilePaths();
						String fileNames = childrenNext.getFileNames();
						String contentTypes = childrenNext.getContentTypes();
						String[] arrPath = filePaths.split(",");
						String[] arrName = fileNames.split(",");
						String[] arrTypes = contentTypes.split(",");
						StringBuffer fileTypes = new StringBuffer();
						Date now = new Date();
						if (org.apache.commons.lang.StringUtils.isNotEmpty(childrenNext.getId())) {
							String fileIds = childrenNext.getFileIds();
							String[] arrFileIds = fileIds.split(",");
							if (arrFileIds.length != arrPath.length) {
								String[] tmp = arrFileIds;
								arrFileIds = new String[arrPath.length];
								System.arraycopy(tmp, 0, arrFileIds, 0, tmp.length);
							}
							ChildrenNext dbr = dao.fetch(ChildrenNext.class,
									childrenNext.getId());
							for (int i = 0; i < arrPath.length; i++) {
								String path = arrPath[i];
								path = path.trim();
								String name = arrName[i];
								name = name.trim();
								String contentType = arrTypes[i];
								contentType = contentType.trim();
								String fileId = arrFileIds[i];
								if (Strings.isNotBlank(fileId)) {
									fileId = fileId.trim();
								}
								if (StringUtils.isEmpty(name)) {
									throw new BusinessException(String.format(
											"第%d个文件为空，所有文件必须上传！", i + 1));
								}

								if (Strings.isEmpty(fileId)) {
									String id = this.addFile(path, name, contentType);
									arrFileIds[i] = id + "";
								}
								if (i > 0) {
									fileTypes.append(",");
								}
								fileTypes.append(TohoursUtils.getFileExt(name));
							}
							dbr.setContentTypes(contentTypes);
							dbr.setUpdateTime(now);
							dbr.setFileCounts(Long.valueOf(arrPath.length));
							dbr.setFileIds(Strings.join(",", arrFileIds));
							dbr.setFileNames(fileNames);
							dbr.setFilePaths(filePaths);
							dbr.setFileTypes(fileTypes.toString());
							dbr.setName(childrenNext.getName());
							dao.update(dbr);

						} else {
							StringBuffer fileIds = new StringBuffer();
							for (int i = 0; i < arrPath.length; i++) {
								String path = arrPath[i];
								path = path.trim();
								String name = arrName[i];
								name = name.trim();
								String contentType = arrTypes[i];
								contentType = contentType.trim();
								if (StringUtils.isEmpty(name)) {
									throw new BusinessException(String.format(
											"第%d个文件为空，所有文件必须上传！", i + 1));
								}
								if (i > 0) {
									fileTypes.append(",");
									fileIds.append(",");
								}
								fileTypes.append(TohoursUtils.getFileExt(name));
								String fileId = addFile(path, name, contentType);
								fileIds.append(fileId);
							}
							childrenNext.setCreateTime(now);
							childrenNext.setUpdateTime(now);
							childrenNext.setFileCounts((long) arrPath.length);
							childrenNext.setFileTypes(fileTypes.toString());
							childrenNext.setFileIds(fileIds.toString());
							childrenNext = dao.insert(childrenNext);
						}
						return re.setv("success", true).setv("id", childrenNext.getId());
					} catch (BusinessException e) {
						return re.setv("success", false).setv("msg", e.getMessage());
					}
				}

				// childrenNext保存时的校验
				public void checkChildrenNext(ChildrenNext childrenNext) {
					if (childrenNext == null) {
						throw new BusinessException("对象不能为空！");
					} else {
						if (StringUtils.isEmpty(childrenNext.getName())) {
							throw new BusinessException("标题不能为空！");
						}
						if (StringUtils.isEmpty(childrenNext.getFilePaths())) {
							throw new BusinessException("上传的文件不能为空！");
						}
					}
				}
				//children的删除
				@At
				public Object childrenDelete(@Param("id") String id) {
					NutMap re = new NutMap();
					try {
						Children children = dao.fetch(Children.class,id);
						//dao.update(children);
						dao.delete(children);
						re.setv("success", true);
					} catch (Exception e) {
						e.printStackTrace();
						re.setv("success", false);
						re.setv("msg", e.getMessage());
					}
					return re;
				}
				
				//childrenNext的删除
				@At
				public Object childrenNextDelete(@Param("id") String id) {
					NutMap re = new NutMap();
					try {
						ChildrenNext childrenNext = dao.fetch(ChildrenNext.class,id);
						//dao.update(childrenNext);
						dao.delete(childrenNext);
						re.setv("success", true);
					} catch (Exception e) {
						e.printStackTrace();
						re.setv("success", false);
						re.setv("msg", e.getMessage());
					}
					return re;
				}
				@At
				public Object updateTenEleSqu(@Param("id")String id,@Param("squence")String squence,@Param("type")String type){
					NutMap nm=new NutMap();
					String[] squArr=QuixUtils.string2array(squence);
					String[] idArr=QuixUtils.string2array(id);
					squArr=squence.split(",");
					idArr=id.split(",");
					System.out.println("id is :"+id);
					System.out.println("sequence is :"+squence);
					for (int i = 0; i < squArr.length; i++) {
						System.out.println("第"+i+"个squ："+squArr[i]);
						System.out.println("第"+i+"个id："+idArr[i]);
					}
					if("resource".equals(type)){//topExcellence
						for (int i = 0; i < idArr.length; i++) {
							Resource resource=dao.fetch(Resource.class,idArr[i]);
							if("-".equals(squArr[i])){
								resource.setSquence(null);
							}else{
								resource.setSquence(Integer.parseInt(squArr[i]));
							}
							dao.update(resource);
						}
					}else if("children".equals(type)){//subExcellence
						for (int i = 0; i < idArr.length; i++) {
							Children children=dao.fetch(Children.class,idArr[i]);
							if("-".equals(squArr[i])){
								children.setSquence(null);
							}else{
								children.setSquence(Integer.parseInt(squArr[i]));
							}
							dao.update(children);
						}
					}else if("childrenNext".equals(type)){//peoples
						for (int i = 0; i < idArr.length; i++) {
							ChildrenNext childrenNext=dao.fetch(ChildrenNext.class,idArr[i]);
							if("-".equals(squArr[i])){
								childrenNext.setSquence(null);
							}else{
								childrenNext.setSquence(Integer.parseInt(squArr[i]));
							}
							dao.update(childrenNext);
						}
					}
					return nm.setv("success", true);
				}
				@At
				@Ok("jsp:jsp.attract.unband_index")
				public void unbandIndex(){
					
				}
				@At
				public Object unbandList(@Param("key") String key,@Param("..") Pager pager){
					NutMap nm=new NutMap();
					Cnd cnd=Cnd.where("1","=",1);
					if (!StringUtils.isEmpty(key)) {
						cnd.and("agentCode","like","%"+key+"%");
					}
					QueryResult qr = new QueryResult();
					qr.setList(dao.query(Agent.class, cnd, pager));
					qr.setPager(pager);
					pager.setRecordCount(dao.count(Agent.class, cnd));
					return nm.setv("list", qr.getList()).setv("pager", qr.getPager()); // 默认分页是第1页,每页20条
				}
				@At
				public Object unbandAgent(@Param("id")String id){
					NutMap nm=new NutMap();
					if(!StringUtils.isEmpty(id)){
						Agent agent=dao.fetch(Agent.class,id);
						if(agent != null){
							agent.setDeviceNum(null);
							dao.update(agent);
							return nm.setv("success", true).setv("msg", "解绑成功");
						}else{
							return nm.setv("success", false).setv("msg", "营销员没找到");
						}
					}else{
						return nm.setv("success", false).setv("msg", "解绑失败,id为空");
					}
				}
				
	
				
				
		//资源包下载
				@At
				public Object zipResource() {
					NutMap map = new NutMap();
					NutMap nm= new NutMap();
					//clearUpload();
					nm.setv("success", true);
					try {
						String version=TohoursUtils.date2string(new Date());
						map.put("index_background", loadIndexBackground());
						map.put("guide_page", loadGuidePage());
						map.put("ten_objective_elements", loadObjectiveElements());
						map.put("excellence", loadExcellence());
						map.put("ten_aia_elements", loadAiaElements());
						map.put("version", version);
						String zipName = "resource.zip";
						String downLoadPath=zip(Json.toJson(map), zipName);
						System.out.println("resoure.zip path is:"+downLoadPath);
						nm.setv("msg", downLoadPath);
						nm.setv("version", version);
					} catch (Exception e) {
						e.printStackTrace();
						nm.setv("success", false);
						nm.setv("msg", "失败，错误信息如下："+e.getMessage());
					}
					return nm;
				}
			private String loadIndexBackground() {
					Resource resource = this.loadResourceByType(Constants.RESOURCE_TYPE_INDEX);
					generateFile(resource.getFileIds());// 只有一个
					return resource.getFilePaths();
				}
			private Object loadGuidePage() {
					Resource resource = this
							.loadResourceByType(Constants.RESOURCE_TYPE_GUIDE);
					String ids = resource.getFileIds();
					String[] arrId = ids.split(",");
					String[] arrPath = resource.getFilePaths().split(",");
					List<String> rv = new ArrayList<String>();
					for (int i = 0; i < arrId.length; i++) {
						rv.add(arrPath[i]);
						generateFile(arrId[i]);
					}
					return rv;
				}	
				private Object loadObjectiveElements() {
					Resource resource = this
							.loadResourceByType(Constants.RESOURCE_TYPE_OBJECTIVE_ELEMENTS);
					String ids = resource.getFileIds();
					String[] arrId = ids.split(",");
					String[] arrPath = resource.getFilePaths().split(",");
					List<String> rv = new ArrayList<String>();
					for (int i = 0; i < arrId.length; i++) {
						rv.add(arrPath[i]);
						generateFile(arrId[i]);
					}
					return rv;
				}
			private Object loadExcellence() {

					List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();

					Cnd cnd = Cnd.where("deleteFlag", "=", false);
					cnd.orderBy("squence", "asc").desc("createTime");
					List<TopExcellence> list = dao.query(TopExcellence.class, cnd);
					for (TopExcellence top : list) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", top.getName());
						map.put("index", loadIndex(top));
						map.put("sub_class", loadSub(top.getId()));
						rv.add(map);
					}
					return rv;
				}
				
			// 友邦十大要素
				private Object loadAiaElements() {
					List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();

					Cnd cnd = Cnd.where("type", "=", Constants.RESOURCE_TYPE_AIA_ELEMENTS);
					cnd.and("deleteFlag", "=", false).orderBy("squence", "asc").desc("createTime");
					List<Resource> list = dao.query(Resource.class, cnd);
					for (Resource resource : list) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", resource.getTitle());
						System.out.println("resourceId is :"+resource.getId());
						map.put("children",loadChildren(resource.getId()));
						rv.add(map);
					}
					return rv;
				}
				
				private String zip(String json, String zipName) {
					try {
						String realPath = this.getRealPath(Constants.FILE_PATH);
						File data = new File(realPath + "/data.json");
						FileUtils.writeStringToFile(data, json, "UTF-8");
						ZipUtils.zip(new File(realPath), zipName);
						String contextPath=this.getContextPath();
						return contextPath+"/attract/"+zipName;
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
				
				private void generateFile(String fileId) {
					if (Constants.isTest) {
						System.out.println(fileId);
					}
					
					try {
						Files files = dao.fetch(Files.class, fileId);
						String path = files.getPath();
						String realPath = this.getRealPath(path);
						String folderPath = realPath.replaceAll("[^/]*$", "");
						File folder = new File(folderPath);
						if (folder.exists() == false) {
							folder.mkdirs();
						}
						File file = new File(realPath);
						if (file.exists() == false) {
							try {
								FileUtils.writeByteArrayToFile(file, files.getData());
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("图片找不到,fileId is :"+fileId);
					}
					
				}	
				private Object loadIndex(TopExcellence top) {
					Map<String, Object> rv = new HashMap<String, Object>();
					String fileIds = top.getFileIds();
					String filePaths = top.getFilePaths();
					String[] arrIds = fileIds.split(",");
					String[] arrPaths = filePaths.split(",");
					List<String> pictures = new ArrayList<String>();
					for (int i = 0; i < arrIds.length; i++) {
						String id = arrIds[i];
						if (rv.get("video") == null) {
							rv.put("video", arrPaths[i]);
						} else {
							pictures.add(arrPaths[i]);
						}
						generateFile(id);
					}
					return rv;
				}			
				
				private Object loadSub(String topId) {

					List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();

					Cnd cnd = Cnd.where("deleteFlag", "=", false).and("top_excel_id", "=",
							topId);
					cnd.orderBy("squence", "asc").desc("createTime");
					List<SubExcellence> list = dao.query(SubExcellence.class, cnd);
					for (SubExcellence sub : list) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", sub.getName());
						map.put("icon", sub.getFilePaths());
						map.put("peoples", loadPeoples(sub.getId()));
						generateFile(sub.getFileIds());
						rv.add(map);
					}
					return rv;
				}	
				
				private Object loadChildren(String resourceId) {

					List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();

					Cnd cnd = Cnd.where("resourceId", "=",
							resourceId);
					cnd.orderBy("squence", "asc").desc("createTime");
					List<Children> list = dao.query(Children.class, cnd);
					for (Children child : list) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", child.getName());
						if(child.getNextFlag()){
							map.put("children", loadChildrenNext(child.getId()));
						}else{
							try {
								String[] filePaths=child.getFilePaths().split(",");
								map.put("pictures", Arrays.asList(filePaths));
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						String [] fileIds={};
						try {
							fileIds = child.getFileIds().split(",");
						} catch (Exception e) {
							e.printStackTrace();
						}
						for (int i = 0; i < fileIds.length; i++) {
							generateFile(fileIds[i]);
						}
						rv.add(map);
					}
					return rv;
				}			
		
				
				private Object loadChildrenNext(String childrenId) {

					List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();

					Cnd cnd = Cnd.where("childrenId", "=",childrenId);
					cnd.orderBy("squence", "asc").desc("createTime");
					List<ChildrenNext> list = dao.query(ChildrenNext.class, cnd);
					for (ChildrenNext c : list) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", c.getName());
						String[] filePaths=c.getFilePaths().split(",");
						map.put("pictures", Arrays.asList(filePaths));
						try {
							String[] fileIds=c.getFileIds().split(",");
							for (int i = 0; i < fileIds.length; i++) {
								generateFile(fileIds[i]);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						rv.add(map);
					}
					return rv;
				}
				
				private Object loadPeoples(String subId) {
					List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();
					Cnd cnd = Cnd.where("deleteFlag", "=", false).and("sub_excel_id", "=",subId);
					cnd.orderBy("squence", "asc").desc("createTime");
					List<Peoples> list = dao.query(Peoples.class, cnd);
					for (Peoples p : list) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", p.getName());
						map.put("join_date", p.getJoinDate());
						map.put("old_job", p.getOldJob());
						map.put("share_word", p.getShareWord());
						map.put("sub_company", p.getSubCompany());
						map.put("marks", Json.fromJson(p.getMarks()));
						map.put("picture", p.getFilePath());
						try {
							generateFile(p.getFileId());
						} catch (Exception e) {
						}
						rv.add(map);
					}
					return rv;
				}	
				
}
