/*******************************************************************************
* -----------------------------------------------------------------------------
* <br>
* <p><b>Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
* <br>
* <br>
* This SOURCE CODE FILE, which has been provided by Quix as part
* of a Quix Creations product for use ONLY by licensed users of the product,
* includes CONFIDENTIAL and PROPRIETARY information of Quix Creations.
* <br>
* USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
* OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
* THE PRODUCT.<br>
* <br>
* </p>
* -----------------------------------------------------------------------------
* <br>
* <br>
* Modification History:
* Date                       Developer          Change Description
* 02-Sept-2015         Nibedita              Copyrights added / file uploaded to db
* 04-Sept-2015         Nibedita              Upload material made mandatory
* 18-Nov-2015          Nibedita              Error stored in db
****************************************** *********************************** */
package com.quix.aia.cn.imo.mapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;

import com.quix.aia.cn.imo.constants.ApplicationAttribute;
import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.category.FestiveCategory;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.egreeting.E_Greeting;
import com.quix.aia.cn.imo.data.egreeting.E_GreetingMaterial;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FileUtils;
import com.quix.aia.cn.imo.utilities.FormObj;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;



/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search,upload pdf file.
 * </p>
 * 
 * @author khyati
 * @version 1.0
 */
public class EgreetingMaintenance {
	static Logger log = Logger.getLogger(EgreetingMaintenance.class.getName());

	
	/**
	 * <p>
	 * This method is for bind festive category dropdown list Code
	 * </p>
	 * 
	 * @param
	 * @return ArrayList
	 */
	
	public ArrayList getAllFestiveCategory(HttpServletRequest request) {
		log.log(Level.INFO,"EgreetingMaintenance ---> getAllFestiveCategory ");
		ArrayList<FestiveCategory> arrFestiveCategory = new ArrayList();
		LogsMaintenance logsMain=new LogsMaintenance();
		Session session = HibernateFactory.openSession();
		try {
			Query query = session
					.createQuery("from FestiveCategory where status=:status");
			query.setParameter("status", true);
			arrFestiveCategory = (ArrayList<FestiveCategory>) query.list();
		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> getAllFestiveCategory ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}
		return arrFestiveCategory;
	}
	
	
	
	/**
	 * <p>
	 * This method checks all validations & sets values to bean
	 * </p>
	 * 
	 * @param E_Greeting class object
	 * @param requestParamet  Servlet Request Parameter
	 * @return E_Greeting object/Error object
	 */

	public Object mapForm1(E_Greeting e_GreetingObj,
			HttpServletRequest requestParameters,FormObj formObj) {
		log.log(Level.INFO,"EgreetingMaintenance ---> mapForm1 ");
		if (e_GreetingObj == null) {
			e_GreetingObj = new E_Greeting();
			return e_GreetingObj;
		}
		User userObj = (User) requestParameters.getSession().getAttribute(
				"currUserObj");
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		String festive_cat_code = requestParameters
				.getParameter("festive_category_code");
		String Festive_name = requestParameters.getParameter("name");
		if (Festive_name == null || Festive_name.length() == 0)
		return new ErrorObject("E-Greeting Cards Name", " field is required",localeObj);
		if (festive_cat_code.equals("%"))
		return new ErrorObject("Festive Category", " field is required",localeObj);
	
		String EGreetingCode = requestParameters
				.getParameterValues("EGreetingCode")[0].trim();
		EGreetingCode = EGreetingCode == null || EGreetingCode.equals("") ? "0"
				: EGreetingCode;
		/*int count = validatename(Festive_name, Integer.parseInt(EGreetingCode));
		if (count != 0) {
			return new ErrorObject("E-Greeting Card Name Already exists", "",localeObj);
		}*/
		
		
		 if(formObj.getFormType().equals("NEW")){
			if(requestParameters.getSession().getAttribute("material_file_name")==null ||  requestParameters.getSession().getAttribute("material_byte_session")==null || requestParameters.getSession().getAttribute("material_field_name")==null){
					return new ErrorObject("Material Upload", " field is required",localeObj);
			}else{
				String file_name = (String) requestParameters.getSession().getAttribute("material_file_name");
				if(!( file_name.contains(".jpg") || file_name.contains(".JPG") ||
						file_name.contains(".jpeg") || file_name.contains(".JPEG") ||
						file_name.contains(".png") || file_name.contains(".PNG") ||
						file_name.contains(".gif") || file_name.contains(".GIF"))){ 
					return new ErrorObject("", "Please upload png/jpg/gif type file",localeObj);
				}
					
		    }
		 }
		e_GreetingObj.setFestive_category(Integer.parseInt(festive_cat_code));
		e_GreetingObj.setEGreetingName(Festive_name);
		e_GreetingObj.setCreated_by(userObj.getStaffLoginId());
		e_GreetingObj.setCreated_date(new Date());
		e_GreetingObj.setStatus(true);
		e_GreetingObj.setToken(LMSUtil.getRendomToken());

		return e_GreetingObj;
	}
	
	
	
	/**
	 * <p>
	 * This method performs validation of duplicate name
	 * </p>
	 * 
	 * @param name
	 * @param E_Greeting_Code
	 * @return integer count value of name available in database.
	 */
	private int validatename(String name, int E_Greeting_Code) {
		log.log(Level.SEVERE,"EgreetingMaintenance ---> validatename ");
		int count = 0;
		Session session = null;
		Transaction tx;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("select count(*) from E_Greeting where status=1 and EGreetingName=:EGreetingName and EGreetingCode!=:EGreetingCode");
			query.setParameter("EGreetingName", name);
			query.setParameter("EGreetingCode", E_Greeting_Code);
			count = (Integer) query.uniqueResult();

			tx.commit();
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> validatename ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE,"EgreetingMaintenance ---> validatename ", e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
			}
		}

		return count;
	}

	
	
	/**
	 * <p>
	 * This method performs call for insertion method for E_Greeting & audit trail for
	 * E_Greeting add & sets message object in request
	 * </p>
	 * 
	 * @param E_Greeting class object
	 * @param req Servlet Request Parameter
	 * @return E_Greeting class object
	 */
	public Object createNewEgreeting(E_Greeting E_GreetingClassObj,
			HttpServletRequest requestParameters) {

		log.log(Level.INFO, "E_GreetingMaintenance --> createNewE_Greeting ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute(
				"currUserObj");
		E_GreetingClassObj.setCreated_by(userObj.getStaffLoginId());
		E_GreetingClassObj.setCreated_date(new Date());
		E_GreetingClassObj.setModified_by(userObj.getStaffLoginId());
		E_GreetingClassObj.setModified_date(new Date());

		int status = insertE_Greeting(E_GreetingClassObj, requestParameters);
		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_E_GREETING,
					AuditTrail.FUNCTION_CREATE, E_GreetingClassObj.toString()));
			msgObj = new MsgObject(
					"The new E_Greeting has been successfully created.");
		} else {
			msgObj = new MsgObject("The new E_Greeting has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("CacheName", "EGreeting");
		// requestParameters.setAttribute("pager", getAllE_GreetingListing());
		// requestParameters.getSession().setAttribute("pager",
		// getAllE_GreetingListing());
		
		Pager pager=eGreetingListing(requestParameters);
		requestParameters.setAttribute("pager", pager);
	     requestParameters.getSession().setAttribute("pager",pager);
	     
		return E_GreetingClassObj;
	}

	
	/**
	 * <p>
	 * This method performs insert of E_Greeting
	 * </p>
	 * 
	 * @param E_Greeting Class Object
	 * @return int key 0 or 1
	 */
	public int insertE_Greeting(E_Greeting E_GreetingClassObj,
			HttpServletRequest requestParameters) {
		log.log(Level.INFO,"EgreetingMaintenance ---> insertE_Greeting ");
		Integer key = 0;
		Session session = null;
		String file_name = "";
		Transaction tx;
		LogsMaintenance logsMain=new LogsMaintenance();
//		ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		String announcementPath = msgProps.getString("EgreetingPath");
		Map<String,String> configurationMap =(Map<String,String>) requestParameters.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		String announcementPath = configurationMap.get("EgreetingPath");
		String tempDir = System.getProperty("java.io.tmpdir");
		FileOutputStream stream = null;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			// upload start
			if (requestParameters.getSession().getAttribute(
					"material_file_name") != null) {
				file_name = (String) requestParameters.getSession()
						.getAttribute("material_file_name");
				if( file_name.contains(".jpg") || file_name.contains(".JPG") ||
						file_name.contains(".jpeg") || file_name.contains(".JPEG") ||
						file_name.contains(".png") || file_name.contains(".PNG") ||
						file_name.contains(".gif") || file_name.contains(".GIF")){ 
					String filePath = uploadFile(announcementPath, tempDir,
							file_name, requestParameters, E_GreetingClassObj,
							"material_byte_session");
					E_GreetingClassObj.setFileLocation(filePath);
					E_GreetingClassObj.setFileName(file_name);
				}
			}
			// upload End

			key = (Integer) session.save(E_GreetingClassObj);
			
			 if(requestParameters.getSession().getAttribute("material_file_name")!=null){
					if( file_name.contains(".jpg") || file_name.contains(".JPG") ||
							file_name.contains(".jpeg") || file_name.contains(".JPEG") ||
							file_name.contains(".png") || file_name.contains(".PNG") ||
							file_name.contains(".gif") || file_name.contains(".GIF")){ 
						E_GreetingMaterial egretMat = null;
			    	 byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute("material_byte_session");
			    	 String field_name = (String)requestParameters.getSession().getAttribute("material_field_name");
				    if(key>0){
				    	egretMat = new E_GreetingMaterial();
				    	egretMat.seteGreetingCode(key);
				    	egretMat.setMaterialName(file_name);
				    	egretMat.setFieldName(field_name);
				    	egretMat.setMaterial(bytearray);
				    	session.save(egretMat);
				    }
			    }
					requestParameters.getSession().removeAttribute("material_file_name");// removing uploaded file details from session after file is uploaded
					requestParameters.getSession().removeAttribute("material_byte_session");//
					requestParameters.getSession().removeAttribute("material_field_name");//
					log.log(Level.SEVERE,"EgreetingMaintenance ---> insertE_Greeting : Material details removed from session ");
			    }
			 
			tx.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> insertE_Greeting ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE,"EgreetingMaintenance ---> insertE_Greeting ", e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
			}
		}
		return key;
	}

	
	/**
	 * <p>
	 * This method performs Upload of pdf file. This upload method is SubMethod inside insertMethod
	 * </p>
	 * 
	 * @param String eopPath
	 * @param String tempDir
	 * @param String fileName
	 * @param requestParamet  Servlet Request Parameter
	 * @param E_Greeting class object
	 * @param String byteSession
	 * @return String path value
	 */
	
	public String uploadFile(String eopPath, String tempDir, String fileName,
			HttpServletRequest requestParameters,
			E_Greeting e_GreetingClassObj, String byteSession) {
		log.log(Level.INFO,"EgreetingMaintenance ---> uploadFile ");
		FileOutputStream stream = null;
		// upload material
		String path = "";
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			String serverFilename = eopPath + "/" + "E_Greeting_"
					+ e_GreetingClassObj.getToken();
			File uploadedFolder = new File(serverFilename);
			if (!uploadedFolder.exists()) {
				uploadedFolder.mkdirs();
			}
			byte[] bytearray = (byte[]) requestParameters.getSession()
					.getAttribute(byteSession);

			stream = new FileOutputStream(serverFilename + "/" + fileName);
			stream.write(bytearray);

			path = "E_Greeting_" + e_GreetingClassObj.getToken() + "/"
					+ fileName;
			// delete temp directory
			File temp_file = new File(tempDir + "/" + fileName);
			FileUtils.deleteFileNFolder(temp_file);
			requestParameters.setAttribute("CacheName", "EGreeting");
		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> uploadFile ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			if (stream != null)
				try {
					stream.close();
				} catch (Exception e) {
					log.log(Level.SEVERE,"EgreetingMaintenance ---> uploadFile ", e.getMessage());
					e.printStackTrace();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
				}
		}
		return path;

	}

	/**
	 * <p>
	 * This method performs listing of particular search result of eGreeting Record based on criteria.
	 * </p>
	 * 
	 * @param  requestParameters Servlet Request Parameter
	 * @return  pager
	 */
	public Pager eGreetingSearchListing(HttpServletRequest requestParamters) {
		log.log(Level.INFO,"EgreetingMaintenance ---> eGreetingSearchListing ");
		LinkedList item = new LinkedList();
		E_Greeting eGreetingClassObj = null;
		ArrayList vecAlleGreeting = new ArrayList();

		vecAlleGreeting = getSearcheGreeting(requestParamters);
		if (vecAlleGreeting.size() != 0) {
			for (int i = 0; i < vecAlleGreeting.size(); i++) {
				eGreetingClassObj = new E_Greeting();
				eGreetingClassObj = (E_Greeting) vecAlleGreeting.get(i);
				item.add(eGreetingClassObj.getGeteGreetingListingTableRow(i));
			}
		}
		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);
		for (; item.size() % 10 != 0; item.add("<tr></tr>"))
			;
		pager.setItems(item);
		return pager;

	}
	
	
	/**
	 * <p>
	 * This method performs listing of particular search result of eGreeting Record based on criteria.
	 * </p>
	 * 
	 * @param  requestParameters Servlet Request Parameter
	 * @return  pager
	 */
	public Pager eGreetingListing(HttpServletRequest requestParamters) {
		log.log(Level.INFO,"EgreetingMaintenance ---> eGreetingSearchListing ");
		LinkedList item = new LinkedList();
		E_Greeting eGreetingClassObj = null;
		ArrayList vecAlleGreeting = new ArrayList();

		vecAlleGreeting = geteGreeting(requestParamters);
		if (vecAlleGreeting.size() != 0) {
			for (int i = 0; i < vecAlleGreeting.size(); i++) {
				eGreetingClassObj = new E_Greeting();
				eGreetingClassObj = (E_Greeting) vecAlleGreeting.get(i);
				item.add(eGreetingClassObj.getGeteGreetingListingTableRow(i));
			}
		}
		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);
		for (; item.size() % 10 != 0; item.add("<tr></tr>"))
			;
		pager.setItems(item);
		return pager;

	}


	
	
	/**
	 * <p>
	 * This is sub method for particular search listing method based of search criteria.
	 * </p>
	 * 
	 * @param  requestParameters Servlet Request Parameter
	 * @return ArrayList
	 */
	public ArrayList getSearcheGreeting(HttpServletRequest req) {
		log.log(Level.INFO,"EgreetingMaintenance ---> getSearcheGreeting ");
		ArrayList arrPresenter = new ArrayList();

		String E_GreetingName = req.getParameter(NAME_PARAM);
		String category = req.getParameter("festive_category");
		//String month = req.getParameter(MONTH_PARAM);
		String year = req.getParameter(YEAR_PARAM);

		Session session = HibernateFactory.openSession();
		session.setDefaultReadOnly(true);
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			Criteria crit = session.createCriteria(E_Greeting.class);
			if (E_GreetingName != null && E_GreetingName.length() > 0)
				crit.add(Restrictions.like("EGreetingName",E_GreetingName,MatchMode.ANYWHERE));
			if (category != null && Integer.parseInt(category) != 0)
				crit.add(Restrictions.eq("festive_category",
						Integer.parseInt(category)));
			/*if (month != null)
				crit.add(Restrictions.sqlRestriction("MONTH(CREATION_DATE)=?",
						Integer.parseInt(month), IntegerType.INSTANCE));*/
			if (year != null)
				crit.add(Restrictions.sqlRestriction("YEAR(CREATION_DATE)=?",
						Integer.parseInt(year), IntegerType.INSTANCE));
			crit.add(Restrictions.eq("status", true));

			List l = crit.list();
			Iterator it = l.iterator();

			while (it.hasNext()) {
				E_Greeting E_GreetingClassObj = (E_Greeting) it.next();
				arrPresenter.add(E_GreetingClassObj);

			}
		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> getSearcheGreeting ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		}
		try {
			session.setDefaultReadOnly(false);
			HibernateFactory.close(session);
		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> getSearcheGreeting ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		}
		return arrPresenter;
	}

	
	/**
	 * <p>
	 * This is sub method for particular search listing method based of search criteria.
	 * </p>
	 * 
	 * @param  requestParameters Servlet Request Parameter
	 * @return ArrayList
	 */
	public ArrayList geteGreeting(HttpServletRequest req) {
		log.log(Level.INFO,"EgreetingMaintenance ---> geteGreeting ");
		ArrayList arrPresenter = new ArrayList();

		Session session = HibernateFactory.openSession();
		session.setDefaultReadOnly(true);
		int month=Calendar.getInstance().get(Calendar.MONTH)+1;
		int year= Calendar.getInstance().get(Calendar.YEAR);
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			Criteria crit = session.createCriteria(E_Greeting.class);
			crit.add(Restrictions.sqlRestriction("MONTH(CREATION_DATE)=?",month, IntegerType.INSTANCE));
				crit.add(Restrictions.sqlRestriction("YEAR(CREATION_DATE)=?",year, IntegerType.INSTANCE));
			crit.add(Restrictions.eq("status", true));

			List l = crit.list();
			Iterator it = l.iterator();

			while (it.hasNext()) {
				E_Greeting E_GreetingClassObj = (E_Greeting) it.next();
				arrPresenter.add(E_GreetingClassObj);

			}
		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> getSearcheGreeting ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		}
		try {
			session.setDefaultReadOnly(false);
			HibernateFactory.close(session);
		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> getSearcheGreeting ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		}
		return arrPresenter;
	}

	// search second coding method
	/*
	 * private ArrayList getSearcheGreeting(HttpServletRequest req) {
	 * 
	 * log.log(Level.INFO,"eGreetingMaintanence --> getSearcheGreeting");
	 * ArrayList arrActivity = new ArrayList(); Session session = null;
	 * 
	 * String str=""; if(!req.getParameter("name").equals("")){
	 * str=str+" and EGreetingName=:name "; }
	 * 
	 * if(!req.getParameter("festive_category").equals("") &&
	 * !req.getParameter("festive_category").equals("0")){
	 * str=str+" and festive_category=:festive_category "; }
	 * 
	 * try {
	 * 
	 * session = HibernateFactory.openSession();
	 * 
	 * 
	 * Query query =
	 * session.createQuery(" from E_Greeting where status = 1 "+str+" ");
	 * if(!req.getParameter("name").equals("")){ query.setParameter("name",
	 * req.getParameter("name"));
	 * 
	 * } if(!req.getParameter("festive_category").equals("")&&
	 * !req.getParameter("festive_category").equals("0")){
	 * query.setParameter("festive_category",
	 * Integer.parseInt(req.getParameter("festive_category")));
	 * 
	 * }
	 * 
	 * arrActivity=(ArrayList) query.list();
	 * 
	 * 
	 * } catch(Exception e) { log.log(Level.SEVERE,e.getMessage());
	 * e.printStackTrace(); }finally{ HibernateFactory.close(session); }
	 * 
	 * return arrActivity;
	 * 
	 * }
	 */

	/**
	 * <p>
	 * This method performs Delete E_Greeting.
	 * </p>
	 * @param E_GreetingCode
	 * @param req Servlet Request Parameter
	 * @return
	 */

	public void deleteE_Greeting(int E_GreetingCode,
			HttpServletRequest requestParameters) {
		log.log(Level.INFO,"EgreetingMaintenance ---> deleteE_Greeting ");
		Session session = null;
		User userObj = (User) requestParameters.getSession().getAttribute(
				"currUserObj");
		String status = "N";
		E_Greeting E_GreetingObj = new E_Greeting();
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			E_GreetingObj = (E_Greeting) session.get(E_Greeting.class,
					E_GreetingCode);
			E_GreetingObj.setStatus(false);
			session.update(E_GreetingObj);
			tx.commit();
			status = "Y";

		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> deleteE_Greeting ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE,"EgreetingMaintenance ---> deleteE_Greeting ", e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
			}
		}
		MsgObject msgObj = null;
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		if (status.equals("Y")) {
			msgObj = new MsgObject(
					"The E_Greeting has been successfully Deleted.");
			auditTrailMaint.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_E_GREETING,
					AuditTrail.FUNCTION_DELETE, "SUCCESS "
							+ E_GreetingObj.toString()));
		} else {
			msgObj = new MsgObject("The  E_Greeting has not been Deleted.");
			auditTrailMaint.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_E_GREETING,
					AuditTrail.FUNCTION_DELETE, "FAILED "
							+ E_GreetingObj.toString()));
		}

		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("CacheName", "EGreeting");
		Pager pager=eGreetingListing(requestParameters);
		requestParameters.setAttribute("pager", pager);
	     requestParameters.getSession().setAttribute("pager",pager);
		// requestParameters.getSession().setAttribute("pager",
		// announcementSearchListing(requestParameters));

	}



	/**
	 * <p>
	 * This method performs edit view of E_Greeting.
	 * </p>
	 * 
	 * @param  int E_GreetingCode
	 * @return E_Greeting class object
	 */

	public E_Greeting getE_GreetingDetail(int E_GreetingCode,HttpServletRequest req) {
		log.log(Level.INFO,"EgreetingMaintenance ---> getE_GreetingDetail ");
		E_Greeting e_Greeting = new E_Greeting();
		Session session = HibernateFactory.openSession();
		session.setDefaultReadOnly(true);
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			e_Greeting = (E_Greeting) session.get(E_Greeting.class,
					E_GreetingCode);
			E_GreetingMaterial matObj = getEgreetingMaterial(e_Greeting.getEGreetingCode());
			if(matObj!=null){
				e_Greeting.seteGreetingMaterial(matObj);
			
				 String serverFilename = req.getRealPath("/");
				 if(!serverFilename.endsWith("/"))
						serverFilename = serverFilename + File.separator;
				 
				serverFilename = serverFilename + "resources" + File.separator + "downloads" + File.separator + "images" ;
			 
			    File dir = new File(serverFilename);
			    if(!dir.exists())
			    	dir.mkdirs();
				
				serverFilename = serverFilename +  File.separator + matObj.getMaterialName();
				
				    byte[] bFile1 =  matObj.getMaterial();
		            FileOutputStream fos = new FileOutputStream(serverFilename); 
		            fos.write(bFile1);
		            fos.close();
		            e_Greeting.setFileLocation(req.getContextPath() + "/" + "resources" + "/"+ "downloads" + "/" + "images" + "/"+  matObj.getMaterialName()) ;
			}
	            
		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> getE_GreetingDetail ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE,"EgreetingMaintenance ---> getE_GreetingDetail ", e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
			}
		}

		return e_Greeting;
	}

	/**
	 * <p>
	 * This method performs update  of E_Greeting.
	 * </p>
	 * 
	 * @param  E_Greeting class object
	 * @param  requestParameters Servlet Request Parameter
	 * @return E_Greeting class object
	 */

	public E_Greeting updateE_Greeting(E_Greeting E_GreetingClassObj,
			HttpServletRequest requestParameters) {
		log.log(Level.INFO,"EgreetingMaintenance ---> updateE_Greeting ");
		Session session = null;
		Transaction tx;

//		ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		String announcementPath = msgProps.getString("EgreetingPath");
		Map<String,String> configurationMap =(Map<String,String>) requestParameters.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		String announcementPath = configurationMap.get("EgreetingPath");
		String tempDir = System.getProperty("java.io.tmpdir");
		String file_name = "";
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			User userObj = (User) requestParameters.getSession().getAttribute(
					"currUserObj");
			E_GreetingClassObj.setEGreetingCode(Integer
					.parseInt(requestParameters.getParameter("EGreetingCode")));
			E_GreetingClassObj.setFestive_category(E_GreetingClassObj
					.getFestive_category());
			E_GreetingClassObj.setModified_by(userObj.getStaffLoginId());
			E_GreetingClassObj.setModified_date(new Date());

			// upload
			if (requestParameters.getSession().getAttribute(
					"material_file_name") != null) {
				file_name = (String) requestParameters.getSession()
						.getAttribute("material_file_name");
				if( file_name.contains(".jpg") || file_name.contains(".JPG") ||
						file_name.contains(".jpeg") || file_name.contains(".JPEG") ||
						file_name.contains(".png") || file_name.contains(".PNG") ||
						file_name.contains(".gif") || file_name.contains(".GIF")){ 
					String filePath = uploadFile(announcementPath, tempDir,
							file_name, requestParameters, E_GreetingClassObj,
							"material_byte_session");
					E_GreetingClassObj.setFileLocation(filePath);
					E_GreetingClassObj.setFileName(file_name);
				}
			}

			session.update(E_GreetingClassObj);
			
			if(requestParameters.getSession().getAttribute("material_file_name")!=null){  //checks if user  uploaded files  to update
				if( file_name.contains(".jpg") || file_name.contains(".JPG") ||
						file_name.contains(".jpeg") || file_name.contains(".JPEG") ||
						file_name.contains(".png") || file_name.contains(".PNG") ||
						file_name.contains(".gif") || file_name.contains(".GIF")){ 
					Query deleteQ = session.createQuery("delete E_GreetingMaterial where eGreetingCode = "+E_GreetingClassObj.getEGreetingCode()+"");
			    	deleteQ.executeUpdate(); 
					 
			    	byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute("material_byte_session");
			    	 String field_name = (String)requestParameters.getSession().getAttribute("material_field_name");
			    	 E_GreetingMaterial egretMat= null;
				  
				    	egretMat = new E_GreetingMaterial();
				    	egretMat.seteGreetingCode(E_GreetingClassObj.getEGreetingCode());
				    	egretMat.setMaterialName(file_name);
				    	egretMat.setFieldName(field_name);
				    	egretMat.setMaterial(bytearray);
				    	session.save(egretMat);
				   
				}
				requestParameters.getSession().removeAttribute("material_file_name");// deleting uploaded file from session
				requestParameters.getSession().removeAttribute("material_byte_session");//
				requestParameters.getSession().removeAttribute("material_field_name");//
				
				log.log(Level.INFO,"EgreetingMaintenance ---> updateE_Greeting: Material details removed from session ");
				
		   }
			
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_E_GREETING,
					AuditTrail.FUNCTION_UPDATE, E_GreetingClassObj.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE,"EgreetingMaintenance ---> updateE_Greeting ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE,"EgreetingMaintenance ---> updateE_Greeting ", e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
			}

		}
		MsgObject msgObj = new MsgObject(
				"The E_Greeting has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("CacheName", "EGreeting");
		Pager pager=eGreetingListing(requestParameters);
		requestParameters.setAttribute("pager", pager);
	     requestParameters.getSession().setAttribute("pager",pager);
		// requestParameters.setAttribute("pager", getAllSscListing());
		// requestParameters.getSession().setAttribute("pager",
		// getAllSscListing());

		return E_GreetingClassObj;
	}

	

	public static final String FESTIVE_CATEGORY_PARAM = "festive_category_code";
	public static final String NAME_PARAM = "name";
	public static final String CREATED_BY_PARAM = "created_by";
	public static final String MONTH_PARAM = "month";
	public static final String YEAR_PARAM = "year";

	 /**
	  * <p>Method is used to get all Egreeting for Rest service</p>
	  * @return  ArrayList of Egreeting
	  */
	public ArrayList getEgreetingRest(AamData aamData) {
		// TODO Auto-generated method stub

		log.log(Level.INFO,"EgreetingMaintenance -->  getEgreetingRest ");
		Session session = null;
		ArrayList egreetingList = new ArrayList();
		ArrayList<E_Greeting> list = new ArrayList<E_Greeting>();
		LogsMaintenance logsMain=new LogsMaintenance();
		try{
			
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Date sdate=new Date();
			log.log(Level.SEVERE,"Start Time "+sdate.getTime());
			
			Query query=session.createQuery("FROM E_Greeting where status = 1 ");
			
			list=(ArrayList<E_Greeting>) query.setCacheable(true).list();
			egreetingList.addAll(list);

			Date edate=new Date();
			log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

			}catch(Exception e)
			{
				log.log(Level.SEVERE,"EgreetingMaintenance ---> getEgreetingRest ", e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
				}catch(Exception e){
					log.log(Level.SEVERE,"EgreetingMaintenance ---> getEgreetingRest ", e.getMessage());
					e.printStackTrace();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
				}
		}
		
		return egreetingList;
	
	}
	 /**
	  * <p>Method is used to get Egreeting material based on egreeting code </p>
	  * @param  eGreetingCode 
	  * @return  E_GreetingMaterial object
	  */
	public E_GreetingMaterial getEgreetingMaterial(int eGreetingCode)
	{
		log.log(Level.INFO,"EgreetingMaintenance ---> getEgreetingMaterial ");
		 Session session = null;
		 E_GreetingMaterial mat = null;
		 LogsMaintenance logsMain=new LogsMaintenance();
		try{
		session = HibernateFactory.openSession();
		session.setDefaultReadOnly(true);
		Query selectQ = session.createQuery("select materialName,material from E_GreetingMaterial where eGreetingCode = "+eGreetingCode+"");
		List list = selectQ.list();
		
		if(list!=null && list.size() > 0){
				mat = new E_GreetingMaterial();
			   Object [] objectDoc = (Object []) list.get(0);
			   mat.setMaterialName(( String)objectDoc[0]);
			   mat.setMaterial( ( byte[] )objectDoc[1]);
		}else{log.log(Level.INFO,"EgreetingMaintenance ---> getEgreetingMaterial : No Material Found ");}

		}catch(Exception e)
		{
			log.log(Level.SEVERE,"EgreetingMaintenance ---> getEgreetingMaterial ", e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE,"EgreetingMaintenance ---> getEgreetingMaterial ", e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EgreetingMaintenance",Level.SEVERE+"",errors.toString());
			}
		}
			return mat;
	}

}
