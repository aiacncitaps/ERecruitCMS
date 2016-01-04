package com.tohours.imo.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import cn.aia.tools.security.AESPasswordManager;

import com.alibaba.druid.util.StringUtils;
import com.tohours.imo.bean.Agent;
import com.tohours.imo.bean.Children;
import com.tohours.imo.bean.ChildrenNext;
import com.tohours.imo.bean.Config;
import com.tohours.imo.bean.Files;
import com.tohours.imo.bean.Peoples;
import com.tohours.imo.bean.Report;
import com.tohours.imo.bean.Resource;
import com.tohours.imo.bean.Setting;
import com.tohours.imo.bean.SpecialAgent;
import com.tohours.imo.bean.SubExcellence;
import com.tohours.imo.bean.Talent;
import com.tohours.imo.bean.TopExcellence;
import com.tohours.imo.bean.User;
import com.tohours.imo.exception.BusinessException;
import com.tohours.imo.util.AESUtils;
import com.tohours.imo.util.Constants;
import com.tohours.imo.util.QuixUtils;
import com.tohours.imo.util.TohoursUtils;
import com.tohours.imo.util.ZipUtils;

@IocBean
// 声明为Ioc容器中的一个Bean
@At("/attract")
// 整个模块的路径前缀
@Ok("json:{locked:'password|salt',ignoreNull:true}")
// 忽略password和salt属性,忽略空属性的json输出
@Fail("http:500")
// 抛出异常的话,就走500页面
//@Filters(@By(type=CheckSession.class, args={Constants.SESSION_USER, "/"})) //
// 检查当前Session是否带me这个属性
public class AttractModule extends BaseModule {  

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

	

	
//	@At
//	public Object zipResource() {
//		NutMap map = new NutMap();
//		NutMap nm= new NutMap();
//		clearUpload();
//		nm.setv("success", true);
//		try {
//			String version=TohoursUtils.date2string(new Date());
//			map.put("index_background", loadIndexBackground());
//			map.put("guide_page", loadGuidePage());
//			map.put("ten_objective_elements", loadObjectiveElements());
//			map.put("excellence", loadExcellence());
//			map.put("ten_aia_elements", loadAiaElements());
//			map.put("version", version);
//			String zipName = "resource.zip";
//			String downLoadPath=zip(Json.toJson(map), zipName);
//			System.out.println("resoure.zip path is:"+downLoadPath);
//			nm.setv("msg", downLoadPath);
//			nm.setv("version", version);
//		} catch (Exception e) {
//			e.printStackTrace();
//			nm.setv("success", false);
//			nm.setv("msg", "失败，错误信息如下："+e.getMessage());
//		}
//		return nm;
//	}
	@SuppressWarnings("unused")
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
	/**
	 * 下载data.json文件
	 * @param json
	 * @return
	 */
	@At
	public Object downloadDataJson(){
		NutMap map = new NutMap();
		NutMap nm= new NutMap();
		clearUpload();
		nm.setv("success", true);
		try {
			map.put("index_background", loadIndexBackground());
			map.put("guide_page", loadGuidePage());
			map.put("ten_objective_elements", loadObjectiveElements());
			map.put("excellence", loadExcellence());
			map.put("ten_aia_elements", loadAiaElements());
			map.put("version", this.loadVersion("VERSION"));
			String realPath = this.getRealPath(Constants.FILE_PATH);
			File data = new File(realPath + "/data.json");
			FileUtils.writeStringToFile(data, Json.toJson(map), "UTF-8");
			String contextPath=this.getContextPath();
			String downloadPath= contextPath+"/attract/uploads/data.json";
			System.out.println("downLoadPath is:"+downloadPath);
			nm.setv("msg", serverName()+downloadPath);
			System.out.println("downDataPath is :"+downloadPath);
		} catch (Exception e) {
			e.printStackTrace();
			nm.setv("success", false);
			nm.setv("msg", "失败，错误信息如下："+e.getMessage());
		}
		return nm;
	}
	private String loadVersion(String key){
		Cnd cnd=Cnd.where("1","=",1);
		cnd.and("keyA","=",key);
		List<Config> list=dao.query(Config.class, cnd);
		if(list != null && list.size()>0){
			return list.get(0).getValue();
		}
		return null;
	}
	private void clearUpload() {
//		try {
//			String realPath = getRealPath(Constants.FILE_PATH);
//			File file = new File(realPath);
//			if(file.exists() && file.isDirectory()){
//				FileUtils.deleteDirectory(file);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
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

	private String loadIndexBackground() {
		Resource resource = this.loadResourceByType(Constants.RESOURCE_TYPE_INDEX);
		generateFile(resource.getFileIds());// 只有一个
		return resource.getFilePaths();
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

	// 新增的字段

	/**
	 * 新增或者操作
	 * 
	 * @param talent
	 * @return
	 */
	@At("/save")
	@Ok("json")
	@POST
	public Object saveTalent(@Param("..") Talent talent) {
		NutMap re = new NutMap();
		re.setv("success", true).setv("msg", "操作成功");
		Talent tl = null;
		if (!StringUtils.isEmpty(talent.getId() + "")) {
			tl = dao.fetch(Talent.class, talent.getId());
		}
		try {
			if (tl == null) {
				talent.setCreateTime(new Date());
				tl = talent;
				/**
				 * 修改agentId
				 */
				if (!StringUtils.isEmpty(talent.getAgentId())) {
					dao.insert(tl);
				} else {
					re.setv("success", false).setv("msg", "agent_id为空,操作失败");
				}

			} else {
				tl = talent;
				talent.setUpdateTime(new Date());
				dao.updateIgnoreNull(tl);
			}
		} catch (Exception e) {
			re.setv("success", false).setv("msg", e.getMessage());
		}
		return re;
	}

	
	/**
	 * 通用的分页显示
	 * 
	 * @param dao
	 * @param pageNumber
	 * @param pageSize
	 * @param cas
	 *            实体的class
	 * @return 返回QueryResult对象
	 */
	public QueryResult getListPage(Dao dao, int pageNumber, int pageSize,
			Class<Object> cas) {
		Pager pager = dao.createPager(pageNumber, pageSize);// 创建pager对象
		Criteria cri = Cnd.cri();// 创建查询语句
		cri.getOrderBy().desc("updateTime");// 设置查询条件
		List<Object> list = dao.query(cas, cri, pager);// 得到list列表
		pager.setRecordCount(dao.count(cas));// 设置总的记录数
		return new QueryResult(list, pager);// 返回QueryResult对象
	}

	/**
	 * 得到所有的list集合
	 * 
	 * @param cas
	 * @return
	 */
	public <T> List<T> getList(Class<T> cas) {
		List<T> list = new ArrayList<T>();
		list = dao.query(cas, Cnd.wrap("1=1"));
		return list;
	}

	/**
	 * 个性化
	 * 
	 * @param individuality
	 * @return
	 */
	@At("/saveIdy")
	@Ok("json")
	@POST
	public Object saveIndividuality(@Param("..") Setting setting) {
		NutMap nt = new NutMap();
		nt.addv("success", true).addv("msg", "操作成功");
		Setting idy = null;
		if (!StringUtils.isEmpty(setting.getAgentId() + "")) {
			idy = dao.fetch(Setting.class, setting.getAgentId());
		}
		try {
			idy = dao.fetch(Setting.class, setting.getAgentId());
			if (idy == null) {
				idy = setting;
				idy.setCreateTime(new Date());
				dao.insert(idy);
			} else {
				idy = setting;
				idy.setUpdateTime(new Date());
				dao.updateIgnoreNull(idy);
			}
		} catch (Exception e) {
			nt.addv("success", false).addv("msg", e.getMessage());
		}
		return nt;
	}

	

	
	

	public static void main(String[] args) throws IOException {
	//	String str="任杰";
		System.out.println("加密："+AES("0986"));
		System.out.println("解密："+praseAES("463e0967d23392330db5d34dff7f66dc3854d859fbfd9acd9bd541490f246888e2b1ae7f5e73c338ac965651677c7a9efa4f111d6e538e6f229a59cba870f92f9684203d113e5115a1a4da4ffa6ccb33"));
	}
	
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  返回agent对应的所有talent
	 * @param agentId
	 * @return
	 */
		@At
		public Object getTalentByAgentId(@Param("agentId") String agentId) {
			agentId=praseAES(agentId);//解密
			NutMap nm = new NutMap();
			nm.setv("success", true);
			Cnd cn=Cnd.where("agentId","=",agentId);
			List<Talent> list=new ArrayList<Talent>();
			int size=0;
			System.out.println("list size1 =:"+size);
			try {
				 list=dao.query(Talent.class, cn);
				 size=list.size();
				 if(size<1){
					nm.setv("success", false);
					nm.setv("msg", "返回列表为空");
				 }
			} catch (Exception e) {
				nm.setv("success", false);
				nm.setv("msg", e.getMessage());
				e.printStackTrace();
			}
			System.out.println("list size2 =:"+size);
			if(nm.getBoolean("success")){
				nm.put("msg", loadTalent(list));
			}
			return nm;
		}
		private Object loadTalent(List<Talent> list){
			List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();
			for (Talent talent : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", AES(talent.getId()));
				map.put("name", AES(talent.getName()));
				map.put("sex", AES(talent.getSex()));
				map.put("birthday", AES(talent.getBirthday()));
				map.put("agentId", AES(talent.getAgentId()));
				map.put("chatTime", AES(talent.getChatTime()));
				map.put("reports", loadReports(talent.getId()));
				rv.add(map);
			}
			return rv;
		}

	private Object loadReports(String talentId) {
		List<Map<String, Object>> rv = new ArrayList<Map<String, Object>>();
		List<Report> list = new ArrayList<Report>();
		Cnd cnd = Cnd.where("1", "=", 1);
		cnd.and("talentId", "=", talentId);
		list = dao.query(Report.class, cnd);
		String cp = this.getContextPath();
		for (Report report : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("imageUr", AES(report.getImageUrl()));
			map.put("downImageUrl", AES(this.serverName() + cp + "/attract/reportImage/" + report.getId()));
			rv.add(map);
		}
		return rv;
	}
		/**
		 * 解析访问的域名
		 * @return
		 */
		private String serverName(){
			String serverName=Mvcs.getReq().getServerName();
			if(serverName.indexOf("imo.tohours.com")>=0){
				return "http://imo.tohours.com";
			}else if(serverName.indexOf("uat.aia.com.cn")>=0){
				return "http://uat.aia.com.cn";
			}else if(serverName.indexOf("aes.aia.com.cn")>=0){
				return "https://aes.aia.com.cn";
			}else{
				return "http://localhost:8080";
			}
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
	 * 更新设备号
	 * @param agent
	 * @param deviceNum
	 * @return
	 */
	private JSONObject updateDeviceNum(Agent agent,String deviceNum){
		JSONObject json=new JSONObject();
		json.put("success", true);
		agent.setDeviceNum(deviceNum);
		dao.update(agent);
		return null;
	}
	/**
	 * 根据设备号得到agent
	 * @param deviceNum
	 * @return
	 */
	private Agent getAgentByDeviceNum(String deviceNum){
		Cnd cnd=Cnd.where("1","=",1);
		cnd.and("deviceNum","=",deviceNum);
		List<Agent> list=dao.query(Agent.class, cnd);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * isp接口，营销员认证
	 * @param account	用户名
	 * @param subCompany	分公司
	 * @param password	密码
	 * @return
	 */
	@SuppressWarnings("static-access")
	@At
	public Object isp(@Param("account") String account, @Param("co") String co,
			@Param("password") String password,
			@Param("deviceNum") String deviceNum) { // 两个点号是按对象属性一一设置
		try {

			// 解密
			account = praseAES(account);
			password = praseAES(password);
			co = praseAES(co);
			deviceNum = praseAES(deviceNum);

			System.out.println("账号 : " + account);
			System.out.println("密码 : " + password);
			System.out.println("分公司 : " + co);
			System.out.println("设备号 : " + deviceNum);
			
			NutMap re = new NutMap();
			/** 判断该账号是否在测试agent表中存在，如果存在就直接返回该agent的信息 */
			Cnd cnd = Cnd.where("1", "=", 1);
			cnd.and("subCompanyId", "=", co);
			cnd.and("agentLongCode", "=", account);
			cnd.and("password", "=", password);
			cnd.and("source", "=", "2");
			List<Agent> testAgentList = dao.query(Agent.class, cnd);
			if (testAgentList != null && testAgentList.size() > 0) {
				System.out.println("测试账号登录！");
				Agent testAgent = testAgentList.get(0);
				System.out.println("testAgent is : " + testAgent);
				// 1.该设备号在agent表中不存在
				if (getAgentByDeviceNum(deviceNum) == null) {
					if (StringUtils.isEmpty(testAgent.getDeviceNum())) {
						this.updateDeviceNum(testAgent, deviceNum);
						re.setv("success", true).setv("msg",
								praseToJson(testAgent));// 加密
						this.updateTestAgentUt(testAgent);//更新测试账号的登录的更新时间
					} else {
						if (!deviceNum.equals(testAgent.getDeviceNum())) {
							re.setv("success", false).setv("msg",
									"该账号已经绑定其他设备，不能重复登录！");
						} else {
							re.setv("success", true).setv("msg",
									praseToJson(testAgent));// 加密
							this.updateTestAgentUt(testAgent);//更新测试账号的登录的更新时间
						}
					}
				} else {// 2.该设备号在agent表中存在
					Agent agent = getAgentByDeviceNum(deviceNum);
					if (agent.getAgentLongCode().equals(account)
							&& agent.getPassword().equals(password)
							&& agent.getSubCompanyId().equals(co)
							&& agent.getDeviceNum().equals(deviceNum)) {
						re.setv("success", true).setv("msg",
								praseToJson(testAgent));// 加密
						this.updateTestAgentUt(testAgent);//更新测试账号的登录的更新时间
					} else {
						re.setv("success", false).setv("msg",
								"该账号已经绑定其他设备，不能重复登录！");
					}
				}
				return re;
			}

			/**************************** end *************************/
			/**
			 * 下面是判断该账号是否在specialAgent表中存在并且访问isp接口返回成功，如果访问isp访问不成功，
			 * 接口会返回相应的信息，如果成功并且在specialAgent表中存在，就会返回该agent的信息，如果在specialAgent
			 * 中不存在的话，会提示"没有权限访问";
			 * */
			else {
				System.out.println("访问isp的账号登录！");
				/************************* 访问isp接口成功之后 ****************************/
				Cnd c = Cnd.where("1", "=", 1);
				c.and("subCompany", "=", co);
				c.and("agentCode", "=", account);
				List<SpecialAgent> specialAgentList = dao.query(
						SpecialAgent.class, c);
				System.out.println("special sql is : "+c.toString());
				System.out.println("special size is : "+specialAgentList.size());
				if (!(specialAgentList != null && specialAgentList.size() > 0)) {
					System.out.println("没有权限！");
					return re.setv("success", false).setv("msg","对不起，你没有权限访问！");
				} 
				/********************************** end ********************************/
				JSONObject json = new JSONObject();
				json = this.checkISP(co, account, password);
				System.out.println("返回json值是-------：" + json);
				if (json.containsKey("content")) {
					/**
					 * json中key值的含义： success：true表示成功,content表示agent的信息
					 * success：false表示失败,msg表示错误信息
					 */
					Agent agent = this.convertJsonToAgent(json, password);
					Agent tmpAgent = this.searchAgent(account, co, password);
					agent.setAgentCode(clear0(account));
					agent.setSubCompanyId(co);
					agent.setDeviceNum(deviceNum);
					System.out.println("searchAgent-----:" + tmpAgent);
					agent.setAgentLongCode(account);
					if (tmpAgent == null) {
						agent.setCreateTime(new Date());
						agent.setUpdateTime(new Date());
						agent.setSource("1");
						agent.setDeviceNum(deviceNum);
						dao.insert(agent);
					} else {
						if (getAgentByDeviceNum(deviceNum) == null) {
							if (StringUtils.isEmpty(tmpAgent.getDeviceNum())) {
								this.updateDeviceNum(tmpAgent, deviceNum);
							} else {
								if (!deviceNum.equals(tmpAgent.getDeviceNum())) {
									re.setv("success", false).setv("msg",
											"该账号已经绑定其他设备，不能重复登录！");
								}
							}
						} else {
							Agent agent1 = getAgentByDeviceNum(deviceNum);
							if (!(agent1.getAgentLongCode().equals(account)
									&& agent1.getPassword().equals(password)
									&& agent1.getSubCompanyId().equals(co) && agent1
									.getDeviceNum().equals(deviceNum))) {
								re.setv("success", false).setv("msg",
										"该账号已经绑定其他设备，不能重复登录！");
							}
						}
						tmpAgent.setAgentCode(agent.getAgentCode());
						tmpAgent.setName(agent.getName());
						tmpAgent.setPassword(agent.getPassword());
						tmpAgent.setSubCompanyId(agent.getSubCompanyId());
						tmpAgent.setSex(agent.getSex());
						tmpAgent.setUpdateTime(new Date());
						tmpAgent.setAgentLongCode(agent.getAgentLongCode());
						tmpAgent.setRegion(agent.getRegion());
						dao.update(tmpAgent);
					}
					JSONObject jsContent = json.fromObject(json
							.getJSONObject("content"));
					String tmp = jsContent.toString().substring(0,
							jsContent.toString().length() - 1);
					JSONObject jsTMp = new JSONObject();
					if (tmpAgent == null) {
						jsTMp = json.fromObject(tmp + ",agentId:\""
								+ agent.getId() + "\"}");
					} else {
						jsTMp = json.fromObject(tmp + ",agentId:\""
								+ tmpAgent.getId() + "\"}");
					}
					System.out.println("有权限！");
					return re.setv("success", json.getBoolean("success"))
							.setv("msg", AESJson(jsTMp, deviceNum));// 加密
				} else {
					return re.setv("success", json.getBoolean("success")).setv(
							"msg", json.getString("error"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private String clear0(String agentCode){
		int index=0;
		for (int i = 0; i < agentCode.length(); i++) {
			if(i == 0){
				if(!(agentCode.charAt(i)+"").equals("0")){
					index = i;
					break;
				}
			}
			if(!(agentCode.charAt(i)+"").equals("0")){
				index = i;
				break;
			}
		}
		return agentCode.substring(index,agentCode.length());
	}
	private JSONObject checkISP(String subCompany, String agentCode,String password) {

		JSONObject json = new JSONObject();
		String uri = String.format(Constants.ispUrl(Mvcs.getReq()), agentCode,subCompany, password);
		try {
			URL url = new URL(uri);
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();
			uc.setRequestMethod("GET");
			uc.setUseCaches(false);
			uc.setRequestProperty("Accept", "application/json");
			String msg = "", line = "";
			if (uc.getResponseCode() == 200){
				BufferedReader in = new BufferedReader(new InputStreamReader(
						uc.getInputStream(), "UTF-8"));
				while ((line = in.readLine()) != null) {
					msg = msg + line;
				}
				JSONObject tmpMsg = JSONObject.fromObject(msg);
				if (tmpMsg.containsKey("Error")) {
					json.put("success",false);
					json.put("error", tmpMsg.getString("Error"));
				} else if (tmpMsg.containsKey("content")) {
					String str = AESPasswordManager.getInstance().decryptPassword(JSONObject.fromObject(msg).getString("content"));
					JSONObject content = JSONObject.fromObject(str.replaceAll("NULL", "\"\""));
					json.put("success", true);
					json.put("content", content);
				}
			} else {
				json.put("success", false);
				json.put("msg", "responseCode is "+uc.getResponseCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("success", true);
			json.put("error", e.getMessage());
		}
		return json;
	}
	/**
	 * 测试账号登录后设置updateTime
	 * @param agent
	 */
	private void updateTestAgentUt(Agent agent){
		agent.setUpdateTime(new Date());
		dao.update(agent);
	}
	/**
	 * 根据subcompany,agentCode,password作为条件进行查询agent
	 * @param agentCode
	 * @param subCompany
	 * @param password
	 * @return
	 */
		public Agent searchAgent(String agentCode,String subCompanyId,String password){
			System.out.println("agentCode:"+agentCode+"--subCompanyId:"+subCompanyId+"--password:"+password);
				Cnd cn=Cnd.where("agentLongCode","=",agentCode).and("subCompanyId","=",subCompanyId).and("password","=",password);
				List<Agent> list=dao.query(Agent.class, cn);
				if(list != null && list.size()>0){
					return list.get(0);
				}
				return null;
		}
		/**
		 * 将isp得到的json值转换到agent实体中
		 * @param json
		 * @param password
		 * @return
		 */
		public Agent convertJsonToAgent(JSONObject json,String password){
			Agent agent = new Agent();
			json=json.getJSONObject("content");
			agent.setSubCompanyId(QuixUtils.dealBlank(json.getString("BRANCH")));
			agent.setName(QuixUtils.dealBlank(json.getString("USERNAME")));
			agent.setAgentCode(QuixUtils.dealBlank(json.getString("USERID")));
			agent.setPassword(password);
			agent.setSex(QuixUtils.dealBlank(json.getString("GENDER")));// 性别
			agent.setRegion(QuixUtils.dealBlank(json.getString("CITY")));
			return agent;
		}
		/**
		 * addTalent的参考界面
		 */
		@At
		@Ok("jsp:jsp.attract.talent_index")
		public void addTalentIndex(){
			
		}
		/**
		 * 新增talent
		 * @param id ：talent的id
		 * @param agentId：talent对应的agent的id
		 * @param name：名字
		 * @param sex：性别
		 * @param birthday：生日
		 * @return
		 */
		@At
		@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
		public Object addTalent(@Param("id")String id,@Param("agentId")String agentId,@Param("name")String name,@Param("sex")String sex,@Param("birthday")String birthday,@Param("imgFile")TempFile[] imgFile,@Param("imageUrl")String imageUrl,@Param("chatTime")String chatTime){
			//imageUrl="/static/1.png,/static/2.png";测试
			/**
			 * 解密
			 */
			id=praseAES(id);
			agentId=praseAES(agentId);
			name=praseAES(name);
			sex=praseAES(sex);
			birthday=praseAES(birthday);
			imageUrl=praseAES(imageUrl);
			chatTime=praseAES(chatTime);
			//imageUrl="/B23BDD2F-4BFA-48E5-ADD5-27FAB86914EB20151205044831/20151205044847.jpg,/B23BDD2F-4BFA-48E5-ADD5-27FAB86914EB20151205044831/20151105044847.jpg";
			System.out.println("id is : "+id);
			System.out.println("agentId is : "+agentId);
			System.out.println("name is :" + name);
			System.out.println("sex is : "+sex);
			System.out.println("birthday is :" +birthday);
			System.out.println("imageUrl is :" +imageUrl);
			System.out.println("chatTime is : "+chatTime);
			
			NutMap nm = new NutMap();
			nm.setv("success", true);
			Agent agent=dao.fetch(Agent.class,agentId);
			if(agent == null){
				nm.setv("success", false);
				nm.setv("msg", "agentId在agent中不存在");
			}
			Talent talent=dao.fetch(Talent.class,id);
			System.out.println("talent is :"+talent);
			if(talent != null){
				if(nm.getBoolean("success")){
					nm.setv("success", false);
					nm.setv("msg", "id在talent中已存在");
				}else{
					String msg=nm.getString("msg");
					nm.setv("msg", msg+" and "+"id在talent中已存在");
				}
			}
			try {
				QuixUtils.string2date(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
				if(nm.getBoolean("success")){
					nm.setv("success", false);
					nm.setv("msg", e.getMessage()+",日期格式错误,正确格式如下：2015-09-12");
				}else{
					String msg=nm.getString("msg");
					nm.setv("success", false);
					if(msg.indexOf("talent")>=0){
						nm.setv("msg", msg+" and "+e.getMessage()+",日期格式错误,正确格式如下：2015-09-12");
					}else{
						nm.setv("msg", msg+" and "+e.getMessage()+",日期格式错误,正确格式如下：2015-09-12");
					}
				}
			}
			System.out.println("nm is : "+Json.toJson(nm));
			Boolean flag=true;
			System.out.println("agentId is : "+agentId);
			System.out.println("name is : "+name);
			System.out.println("sex is : "+sex);
			System.out.println("birthday is : "+birthday);
			System.out.println("nm.getBoolean('success') is : "+nm.getBoolean("success"));
			if(nm.getBoolean("success")){
				Cnd cnd=Cnd.where("1","=",1);
				cnd.and("name","=",name);
				cnd.and("sex","=",sex);
				cnd.and("birthday","=",birthday);
				List<Talent> list=dao.query(Talent.class, cnd);
				talent=new Talent();
				if(list!=null && list.size()>0){
					flag=false;
					talent=list.get(0);
					System.out.println("talent已存在! & id is : "+talent.getId());
				}else{
					talent.setId(id);
					talent.setCreateTime(new Date());
					System.out.println("talent不存在!");
				}
				talent.setAgentCode(dao.fetch(Agent.class,agentId).getAgentCode());
				talent.setSubCompanyId(agent.getSubCompanyId());//设置分公司
				talent.setSex(sex);
				talent.setName(name);
				talent.setBirthday(birthday);
				talent.setChatTime(chatTime);
				talent.setUpdateTime(new Date());
				System.out.println("imageUrl is :"+imageUrl);
				talent.setAgentId(agentId);
				talent.setAgent(agent);
				nm.setv("msg", "talent新增成功");
			}
			String[] pathArr={};
			try {
				pathArr=imageUrl.split(",");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("pathArr is : " + pathArr);
			if(imgFile != null){
				System.out.println("imgFile size is : "+imgFile.length);
			}
			List<Report> reportList=new ArrayList<Report>();
			if(nm.getBoolean("success")){
				if(imgFile != null){
					for (int i = 0; i < imgFile.length; i++) {
						File f = imgFile[i].getFile();                  // 这个是保存的临时文件
						FieldMeta meta = imgFile[i].getMeta();          // 这个原本的文件信息
						String oldName = meta.getFileLocalName();    	// 这个时原本的文件名称
					    byte[] bt;
					    try {
							bt=FileUtils.readFileToByteArray(f);
							Report report=new Report();
							report.setTalentId(talent.getId());
							report.setCreateTime(new Date());
							report.setData(bt);
							report.setFileName(oldName);
							report.setImageUrl(pathArr[i]);
							int index1=pathArr[i].lastIndexOf("/");
							int index2=pathArr[i].indexOf(".");
							String year=pathArr[i].substring(index1+1,index2-10);
							String month=pathArr[i].substring(index2-10,index2-8);
							String day=pathArr[i].substring(index2-8,index2-6);
							String hour=pathArr[i].substring(index2-6,index2-4);
							String second=pathArr[i].substring(index2-4,index2-2);
							String mimite=pathArr[i].substring(index2-2,index2);
							String time=year+"-"+month+"-"+day+" "+hour+":"+second+":"+mimite;
							System.out.println("时间是："+time);
							try {
								report.setUpdateTime(TohoursUtils.string2date(time,"yyyy-MM-dd hh:mm:ss"));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							System.out.println("report updateTime is : "+TohoursUtils.date2string(report.getUpdateTime(),"yyyy-MM-dd hh:mm:ss"));
							dao.insert(report);//新增report
							reportList.add(report);
						} catch (IOException e) {
							nm.setv("success", false);
							nm.setv("msg", "文件上传失败："+e.getMessage());
							e.printStackTrace();
						}
					}
				}
				talent.setReportList(reportList);
				if(flag){
					dao.insertWith(talent, "reportList");
				//	dao.insert(talent);
					System.out.println("新增talent");
				}else{
					dao.updateWith(talent,"reportList");
				//	dao.update(talent);
					System.out.println("更新talent");
				}
				
			}
			return nm;
		}
		
		/**
		 * 解析addTalent中的imagePath
		 * @param imagePath
		 * @return
		 */
		@SuppressWarnings("unused")
		private String parseImageUrl(String imageUrl){
			JSONArray jsonArr=new JSONArray();
			jsonArr.add(imageUrl);
			JSONArray jsonArr1=jsonArr.getJSONArray(0);
			Object[] arr= jsonArr1.toArray();
			String path = "";
			for(int i=0;i<arr.length;i++){
				System.out.println("arr is :"+arr[i]);
				if(i == arr.length -1){
					path+=arr[i];
				}else{
					path+=arr[i]+",";
				}
			}
			return path;
		}
		//rest的支持
		@At("/reportImage/?")
		@GET
		public void reportImage(String id) throws IOException {
			Report report=dao.fetch(Report.class,id);
			HttpServletResponse response = Mvcs.getResp();
			//response.setContentType(files.getContentType());
			response.getOutputStream().write(report.getData());
		}
		/**
		 * topExcellence的排序
		 * @param id
		 * @param squence
		 * @param type
		 * @return
		 */
		@At
		public Object updateSqu(@Param("id")String id,@Param("squence")String squence,@Param("type")String type){
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
			if("top".equals(type)){//topExcellence
				for (int i = 0; i < idArr.length; i++) {
					TopExcellence top=dao.fetch(TopExcellence.class,idArr[i]);
					if("-".equals(squArr[i])){
						top.setSquence(null);
					}else{
						top.setSquence(Integer.parseInt(squArr[i]));
					}
					dao.update(top);
				}
			}else if("sub".equals(type)){//subExcellence
				for (int i = 0; i < idArr.length; i++) {
					SubExcellence sub=dao.fetch(SubExcellence.class,idArr[i]);
					if("-".equals(squArr[i])){
						sub.setSquence(null);
					}else{
						sub.setSquence(Integer.parseInt(squArr[i]));
					}
					dao.update(sub);
				}
			}else if("peoples".equals(type)){//peoples
				for (int i = 0; i < idArr.length; i++) {
					Peoples peoples=dao.fetch(Peoples.class,idArr[i]);
					if("-".equals(squArr[i])){
						peoples.setSquence(null);
					}else{
						peoples.setSquence(Integer.parseInt(squArr[i]));
					}
					dao.update(peoples);
				}
			}
			return nm.setv("success", true);
		}
		
		
		@At
		@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
		public Object ghost(@Param("id")String id,@Param("agentId")int agentId,@Param("imgFile")TempFile[] imgFile,@Param("name")String name,@Param("sex")String sex,@Param("birthday")String birthday){
			NutMap nm=new NutMap();
			Date now=new Date();
			Talent talent=new Talent();
		//	talent.setAgentId(agentId);
			talent.setBirthday(birthday);
			talent.setId(id);
			talent.setSex(sex);
			talent.setCreateTime(now);
			talent.setUpdateTime(now);
			talent.setName(name);
			talent=dao.insert(talent);
			for (int i = 0; i < imgFile.length; i++) {
				File f = imgFile[i].getFile();                       // 这个是保存的临时文件
				FieldMeta meta = imgFile[i].getMeta();               // 这个原本的文件信息
				String oldName = meta.getFileLocalName();    // 这个时原本的文件名称
			    byte[] bt;
			    try {
					bt=FileUtils.readFileToByteArray(f);
					
					Report report=new Report();
					report.setTalentId(talent.getId());
					report.setCreateTime(now);
					report.setData(bt);
					report.setUpdateTime(now);
					report.setFileName(oldName);
					dao.insert(report);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			return nm;
		}
		
		
	/**
	 * 设置app最后的使用时间
	 * @param lastdate
	 * @return
	 */
	@At("/setAppLastDate/?")
	@GET
	public Object setAppLastDate(@Param("lastdate")String lastdate){
		NutMap nm=new NutMap();
		nm.setv("success", true);
		System.out.println("lastDate is : "+lastdate);
		try {
			nm.setv("msg", "更新成功：App_Last_Date is "+TohoursUtils.string2date(lastdate,"yyyy-MM-dd"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			TohoursUtils.string2date(lastdate,"yyyy-MM-dd");
			Cnd cnd=Cnd.where("keyA","=","APP_LAST_DATE");
			Config config=dao.query(Config.class, cnd).get(0);
			System.out.println(Json.toJson(config));
			config.setUpdateTime(new Date());
			config.setValue(TohoursUtils.date2string(TohoursUtils.string2date(lastdate,"yyyy-MM-dd"),"yyyy-MM-dd"));
			dao.update(config);
		} catch (Exception e) {
			e.printStackTrace();
			nm.setv("success", false);
			nm.setv("msg", "更是失败："+e.getMessage());
		}
		return nm;
	}
	
	
	/**
	 * 获取app最后的使用时间
	 * @return
	 */
	@At("/getAppLastDate")
	public Object getAppLastDate(){
		NutMap nm = new NutMap();
		nm.put("success", true);
		Cnd cnd=Cnd.where("1","=",1);
		cnd.and("keyA","=","APP_LAST_DATE");
		List<Config> list=dao.query(Config.class, cnd);
		if(list != null && list.size()>0){
			nm.put("msg", AES(list.get(0).getValue()));
			nm.put("result", list.get(0).getValue());
		}else{
			nm.put("success", false);
			nm.put("msg", "APP_LAST_DATE is not exist !");
		}
		return nm;
	}
	@At
	@Ok("jsp:jsp.attract.setapp_lastdate_index")
	public void setAppLastDateIndex(){
	}
	/**
	 * 设置版本version
	 * @return
	 */
	@At("/setAppVersion/?")
	public Object setAppVersion(@Param("version")String version){
		NutMap nm = new NutMap();
		nm.put("success", true);
		Cnd cnd=Cnd.where("1","=",1);
		cnd.and("keyA","=","VERSION");
		List<Config> list=dao.query(Config.class, cnd);
		if(list != null && list.size()>0){
			Config config=list.get(0);
			config.setValue(version);
			dao.update(config);
			nm.put("msg", list.get(0).getValue());
		}else{
			nm.put("success", false);
			nm.put("msg", "Version is not exist !");
		}
		return nm;
	}
	/**
	 * 将Agent转换成json
	 * @param agent
	 * @return
	 */
	private JSONObject praseToJson(Agent agent){
		System.out.println("userName is : "+agent.getName());
		System.out.println("加密后userName is :"+AES(agent.getName()));
		JSONObject json=new JSONObject();
		json.put("BRANCH", "");
		json.put("CITY", AES(agent.getRegion()));
		json.put("SSC", "");
		json.put("USERTYPE", "");
		json.put("USERID", AES(agent.getAgentCode()));
		json.put("USERNAME", AES(agent.getName()));
		json.put("USERSTATUS", "");
		json.put("TEAMCODE", "");
		json.put("TEAMNAME", "");
		json.put("OFFICECODE", "");
		json.put("OFFICENAME", "");
		json.put("CERTIID", "");
		json.put("DATEOFBIRTH", "");
		json.put("GENDER", "");
		json.put("CONTRACTEDDATE", "");
		json.put("TITLE", "");
		json.put("DTLEADER", "");
		json.put("AGENTTYPE", "");
		json.put("AGENTDETAIL", "");
		json.put("MOBILE", "");
		json.put("EMAIL", "");
		json.put("TITLEDESCRIBE", "");
		json.put("agentId", AES(agent.getId()));
		json.put("DEVICENUM", AES(agent.getDeviceNum()));
		return json;
	}
	/**
	 * 加密isp返回的json
	 * @param json
	 * @return
	 */
	private JSONObject AESJson(JSONObject json,String deviceNum){
		System.out.println(" json userName is : "+json.getString("USERNAME"));
		System.out.println(" json 加密 后 is ： "+AES(json.getString("USERNAME")));
		JSONObject jsonResult=new JSONObject();
		jsonResult.put("BRANCH", AES(json.getString("BRANCH")));
		jsonResult.put("CITY", AES(json.getString("CITY")));
		jsonResult.put("SSC", AES(json.getString("SSC")));
		jsonResult.put("USERTYPE", AES(json.getString("USERTYPE")));
		jsonResult.put("USERID", AES(json.getString("USERID")));
		jsonResult.put("USERNAME", AES(json.getString("USERNAME")));
		jsonResult.put("USERSTATUS", AES(json.getString("USERSTATUS")));
		jsonResult.put("TEAMCODE", AES(json.getString("TEAMCODE")));
		jsonResult.put("TEAMNAME", AES(json.getString("TEAMNAME")));
		jsonResult.put("OFFICECODE", AES(json.getString("OFFICECODE")));
		jsonResult.put("OFFICENAME", AES(json.getString("OFFICENAME")));
		jsonResult.put("CERTIID", AES(json.getString("CERTIID")));
		jsonResult.put("DATEOFBIRTH", AES(json.getString("DATEOFBIRTH")));
		jsonResult.put("GENDER", AES(json.getString("GENDER")));
		jsonResult.put("CONTRACTEDDATE", AES(json.getString("CONTRACTEDDATE")));
		jsonResult.put("TITLE", AES(json.getString("TITLE")));
		jsonResult.put("DTLEADER", AES(json.getString("DTLEADER")));
		jsonResult.put("AGENTTYPE", AES(json.getString("AGENTTYPE")));
		jsonResult.put("AGENTDETAIL", AES(json.getString("AGENTDETAIL")));
		jsonResult.put("MOBILE", AES(json.getString("MOBILE")));
		jsonResult.put("EMAIL", AES(json.getString("EMAIL")));
		jsonResult.put("TITLEDESCRIBE", AES(json.getString("TITLEDESCRIBE")));
		jsonResult.put("agentId", AES(json.getString("agentId")));
		jsonResult.put("DEVICENUM",AES(deviceNum) );
		return jsonResult;
	}
	/**
	 * 加密字符串
	 * @param str
	 * @return
	 */
	private static String AES(String str){
		byte[] key;
		String result="";
		try {
			key = AESUtils.initkey();
			result=AESUtils.byte2hex(AESUtils.encrypt(str.getBytes("UTF-8"), key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 解密字符串
	 * @param str
	 * @return
	 */
	private static String praseAES(String str){
		String result="";
		byte[] key;
		try {
			key = AESUtils.initkey();
			// 解密数据
			byte[] data = AESUtils.decrypt(AESUtils.hex2byte(str.getBytes("UTF-8")), key);
			result=new String(data,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 设置app的下载次数
	 * @param lastdate
	 * @return
	 * @throws IOException 
	 */
	@At
	@Ok("void")
	@GET
	public void setAppDownloadNum(@Param("callback")String callback) throws IOException{
		
		NutMap nm=new NutMap();
		nm.setv("success", true);
		try {
			Cnd cnd=Cnd.where("keyA","=","APP_DOWNLOAD_COUNT");
			Config config=dao.query(Config.class, cnd).get(0);
			config.setUpdateTime(new Date());
			config.setValue(Long.parseLong(config.getValue())+1+"");
			dao.update(config);
			nm.setv("msg", "当前APP下载次数是："+config.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			nm.setv("success", false);
			nm.setv("msg", "更是失败："+e.getMessage());
		}
		String rv ;
		if(StringUtils.isEmpty(callback)){
			rv = nm.toString();
		} else {
			rv = String.format("%s(%s);", callback, Json.toJson(nm));
		}
		Mvcs.getResp().getWriter().print(rv);
	}
	/**
	 * 遍历talent，根据agent_id来设置talent表中的sub_company_id  & region字段的值
	 * @return
	 */
	@At
	public Object syncTalentByAgent(){
		NutMap nm=new NutMap();
		Cnd cnd=Cnd.where("1","=",1);
		try {
			List<Talent> talentList=dao.query(Talent.class, cnd);
			for (int i = 0; i < talentList.size(); i++) {
				String agentId=talentList.get(i).getAgentId();
				Agent agent=dao.fetch(Agent.class,agentId);
				String subCompanyId=agent.getSubCompanyId();
				String region=agent.getRegion();
				Talent talent=talentList.get(i);
				talent.setRegion(region);
				talent.setSubCompanyId(subCompanyId);
				dao.update(talent);
			}
			talentList=dao.query(Talent.class, cnd);
			nm.setv("success", true).setv("msg", "更新talent成功！");
		} catch (Exception e) {
			nm.setv("success", false).setv("msg", e.getMessage());
		}
	
		return nm;
	}
}
