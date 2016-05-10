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
* Date              Developer          Change Description
* 07-May-2015       Nibedita           Announcement Db operation code
* 11-May-2015       Nibedita           Audit code added
* 15-May-2015       Nibedita           File upload code added
* 20-May-2015       Nibedita           duplicate announcement checked           
* 14-August-2015    Maunish            Modified 
* 09-Sept-2015         Nibedita           file uploaded to db
* 18-Nov-2015         Jinatmayee         Error stored in db

****************************************** *********************************** */

package com.quix.aia.cn.imo.mapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.announcement.Announcement;
import com.quix.aia.cn.imo.data.announcement.AnnouncementMaterial;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FileUtils;
import com.quix.aia.cn.imo.utilities.KeyObjPair;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;
/**
 * <p>Logic to create,update,delete,search announcement.</p>
 * 
 * @author Nibedita
 * @version 1.0
 *
 */

public class AnnouncementMaintenance {
	static Logger log = Logger.getLogger(AnnouncementMaintenance.class.getName());
	 /**
	  * Constructor
	  * Hibernate configuration done
	  */
	public AnnouncementMaintenance(){
		HibernateFactory.buildIfNeeded();
	}
	 /**
	  * <p>Method takes care to map announcement fields.</p>
	  * @param announcement  Announcement object  
	  * @param requestParameters Servlet Request Parameter
	  * @return Announcement Object/Error object 
	  */
	public Object mapForm1(Announcement announcement, HttpServletRequest requestParameters)
	{
		 LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		log.log(Level.INFO,"AnnouncementMaintenance --> mapForm1 ");
		if(announcement == null)
		{
			announcement = new Announcement();
			return announcement;
		}
		
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		
		if(requestParameters.getParameter("subject") == null || requestParameters.getParameter("subject").length() == 0)
			return new ErrorObject("SUBJECT", " field is required",localeObj);
		if(requestParameters.getParameter("publishedDate") == null || requestParameters.getParameter("publishedDate").length() == 0)
			return new ErrorObject("Published Date", " field is required",localeObj);
		if(requestParameters.getParameter("expiredDate") == null || requestParameters.getParameter("expiredDate").length() == 0)
			return new ErrorObject("Expired Date", " field is required",localeObj);
		if(requestParameters.getParameter("messageType") == null || requestParameters.getParameter("messageType").length() == 0)
			return new ErrorObject("MESSAGE TYPE", " field is required",localeObj);
		if(requestParameters.getParameter("message") == null || requestParameters.getParameter("message").length() == 0)
			return new ErrorObject("Message", " field is required",localeObj);
		if(requestParameters.getParameter("bu") == null || requestParameters.getParameter("bu").equals("0"))
			return new ErrorObject("BU", " field is required",localeObj);
		
		
        announcement.setSubject(requestParameters.getParameter("subject").trim());
		announcement.setPublishedDate(LMSUtil.convertStringToDate(requestParameters.getParameter("publishedDate")));
		announcement.setExpDate(LMSUtil.convertStringToDate(requestParameters.getParameter("expiredDate")));
		if(announcement.getExpDate().before(announcement.getPublishedDate()) || announcement.getExpDate().equals(announcement.getPublishedDate())){
			 return new ErrorObject(" ", "Expired Date Should be after Published Date",localeObj);
		}
	//	if(checkDuplicateAnnouncement(announcement))
			// return new ErrorObject("", "Announcement Already Exist.Duplicate Announcement not allowed",localeObj);
		announcement.setMsgType(requestParameters.getParameter("messageType"));
		announcement.setMessage(requestParameters.getParameter("message"));
		announcement.setBuCode(Integer.parseInt(requestParameters.getParameter("bu")));
		announcement.setDistrict(Integer.parseInt(requestParameters.getParameter("district")));
		announcement.setBranchCode(Integer.parseInt(requestParameters.getParameter("branch")));
		announcement.setCityCode(requestParameters.getParameter("city"));
		announcement.setOfficeCode(requestParameters.getParameter("office"));
		announcement.setSscCode(requestParameters.getParameter("ssc"));
		
		
		announcement.setStatus(true);
		
		return announcement;
	}
	 /**
	  *<p> Code for announcement creation.Success and failure message object set here.</p>
	  * @param announcement  Announcement Object
	  * @param requestParameters  Servlet Request Parameter
	  * @return
	  */
	public Object createNewAnnouncement(Announcement announcement, HttpServletRequest requestParameters)
	{
		log.log(Level.INFO,"---AnnouncementMaintenance Creation--- ");
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		 MsgObject msgObj = null;
		 announcement.setCreatedBy(userObj.getStaffLoginId());
		announcement.setCreationDate(new Date());
		announcement.setModifiedBy(userObj.getStaffLoginId());
		announcement.setModificationDate(new Date());
		int status = insertAnnouncement(announcement,requestParameters);
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		if(status !=0){
			msgObj = new MsgObject("The new Announcement has been successfully created");
			auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_CREATE, "SUCCESS "+announcement.toString()));
		}
		else{
			msgObj = new MsgObject("The new Announcement has not been created");
			auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_CREATE, "FAILED "+announcement.toString()));
		}
		
		Pager pager=announcementListing(requestParameters);
		requestParameters.setAttribute("pager", pager);
	     requestParameters.getSession().setAttribute("pager",pager);
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("CacheName", "Announcement");
		return announcement;
	}
	 /**
	  * <p>Announcement insert code done here.Store action in audit table.</p>
	  * @param announcement        Announcement Object
	  * @param requestParameters   Servlet Request Parameter
	  * @return integer value(primary key) generated
	  */
	public int insertAnnouncement(Announcement announcement,HttpServletRequest requestParameters){
		 Session session = null;
		String file_name="";
		Integer key = 0;
//		ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		String announcementPath  = msgProps.getString("AnnouncementPath");
		//Map<String,String> configurationMap =(Map<String,String>) requestParameters.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		//String announcementPath  = configurationMap.get("AnnouncementPath");//E:/IMOCN/announcement
		String tempDir = System.getProperty("java.io.tmpdir");
		announcement.setToken(LMSUtil.getRendomToken());
		
		try{
			
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
		
			if(requestParameters.getSession().getAttribute("material_file_name")!=null){//checks if user  uploaded files or not
				file_name = (String)requestParameters.getSession().getAttribute("material_file_name");
				/*if(file_name.contains(".pdf") || file_name.contains(".PDF")||
						file_name.contains(".ppt") || file_name.contains(".PPT")||
						file_name.contains(".jpg") || file_name.contains(".JPG")||
						file_name.contains(".jpeg") || file_name.contains(".JPEG"))*/
				if(file_name.contains(".pdf") || file_name.contains(".PDF"))
						{ //checks if pdf file is uploaded
					String filePath = uploadFile(tempDir,file_name,requestParameters,announcement,"material_byte_session");
					announcement.setAttachmentPath(filePath);
				}
				
		   }
		
		    key = (Integer)session.save(announcement);
		    
		    if(requestParameters.getSession().getAttribute("material_file_name")!=null){
				/*if(file_name.contains(".pdf") || file_name.contains(".PDF") ||
						file_name.contains(".ppt") || file_name.contains(".PPT")||
						file_name.contains(".jpg") || file_name.contains(".JPG")||
						file_name.contains(".jpeg") || file_name.contains(".JPEG"))*/
		    	if(file_name.contains(".pdf") || file_name.contains(".PDF")) { //checks if pdf file is uploaded
		    	  AnnouncementMaterial annMat = null;
		    	 byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute("material_byte_session");
		    	 String field_name = (String)requestParameters.getSession().getAttribute("material_field_name");
			    if(key>0){
			    	annMat = new AnnouncementMaterial();
			    	annMat.setAnnoucementCode(key);
			    	annMat.setMaterialName(file_name);
			    	annMat.setFieldName(field_name);
			    	annMat.setMaterial(bytearray);
			    	session.save(annMat);
			    }
			   
				requestParameters.getSession().removeAttribute("material_file_name");// removing uploaded file details from session after file is uploaded
				requestParameters.getSession().removeAttribute("material_byte_session");//
				requestParameters.getSession().removeAttribute("material_field_name");//
		    }
		    }
		    tx.commit();
		    log.log(Level.INFO,"---AnnouncementMaintenance Created Successfully--- ");
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
				
			}finally{
				try{
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return key;
	}
	 /**
	  * <p>Anouncement listing done here.</p>
	  * @param req   Servlet Request Parameter
	  * @return  pager object
	  */
	public  Pager announcementSearchListing(HttpServletRequest req)
	{
	LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		LinkedList item = new LinkedList();
		Announcement announcement = null;
		ArrayList listAllAnnouncements = null;
		Map<String,String> annMap = new HashMap<String,String>();
		if(req.getParameter(SUBJECT_PARAM)!=null)
			annMap.put("SUBJECT", req.getParameter(SUBJECT_PARAM));
		if(req.getParameter(MONTH_PARAM)!=null)
			annMap.put("MONTH", req.getParameter(MONTH_PARAM));
		if(req.getParameter(YEAR_PARAM)!=null)
			annMap.put("YEAR", req.getParameter(YEAR_PARAM));
		if(req.getParameter(BU_PARAM)!=null)
			annMap.put("BU", req.getParameter(BU_PARAM));
		if(req.getParameter(DISTRICT_PARAM)!=null)
			annMap.put("DIST", req.getParameter(DISTRICT_PARAM));
		
		if(req.getParameter("branch")!=null)
			annMap.put("branchCode", req.getParameter("branch"));
		
		if(req.getParameter("office")!=null)
			annMap.put("officeCode", req.getParameter("office"));
		
		if(req.getParameter(CITY_PARAM)!=null)
			annMap.put("CITY", req.getParameter(CITY_PARAM));
		if(req.getParameter(SSC_PARAM)!=null)
			annMap.put("SSC", req.getParameter(SSC_PARAM));
		
		//req.getSession().setAttribute("ANN_SEARCH_OBJ", annMap);
		
		
		if(req.getParameter("year")!=null)
			 listAllAnnouncements = getSearchedAnnouncements(req);
		else
			 listAllAnnouncements = getAllAnnouncements(req);
		for(int i = 0; i < listAllAnnouncements.size(); i++)
		{
			announcement = new Announcement();
			announcement = (Announcement)listAllAnnouncements.get(i);
			item.add(announcement.getGetAnnouncementListingTableRow(localeObj));
		}

		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);
		for(; item.size() % 10 != 0; item.add("<tr></tr>"));
		pager.setItems(item);
		return pager;
		
		
	}
	
	
	 /**
	  * <p>Anouncement listing done here.</p>
	  * @param req   Servlet Request Parameter
	  * @return  pager object
	  */
	public  Pager announcementListing(HttpServletRequest req)
	{
	LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		LinkedList item = new LinkedList();
		Announcement announcement = null;
		ArrayList listAllAnnouncements = null;
		
		listAllAnnouncements = getAllAnnouncements(req);
		for(int i = 0; i < listAllAnnouncements.size(); i++)
		{
			announcement = new Announcement();
			announcement = (Announcement)listAllAnnouncements.get(i);
			item.add(announcement.getGetAnnouncementListingTableRow(localeObj));
		}

		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);
		for(; item.size() % 10 != 0; item.add("<tr></tr>"));
		pager.setItems(item);
		return pager;
		
		
	}
	 /**
	  *<p>Method is used to get searched announcement details.</p>
	  * @param req  Servlet Request Parameter
	  * @return  ArrayList of announcement
	  */
	public  ArrayList getSearchedAnnouncements(HttpServletRequest req)
	{
		 Session session = null;
		String subject = req.getParameter(SUBJECT_PARAM);
		String month = req.getParameter(MONTH_PARAM);
		String year = req.getParameter(YEAR_PARAM);
		String bu = req.getParameter(BU_PARAM);
		String district = req.getParameter(DISTRICT_PARAM);
		String city = req.getParameter(CITY_PARAM);
		String ssc = req.getParameter(SSC_PARAM);
		
		String office = req.getParameter("office");
		String branch=req.getParameter("branch");
		ArrayList announcementList = new ArrayList();
		
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Criteria crit = session.createCriteria(Announcement.class);
			if(subject!=null && subject.length() > 0)
				crit.add(Restrictions.like("subject",subject, MatchMode.ANYWHERE));
			if(month!=null)
				crit.add(Restrictions.sqlRestriction("MONTH(PUBLISHED_DATE)=?", Integer.parseInt(month),IntegerType.INSTANCE));
			if(year!=null)
				crit.add(Restrictions.sqlRestriction("YEAR(PUBLISHED_DATE)=?", Integer.parseInt(year),IntegerType.INSTANCE));
			if(bu!=null && Integer.parseInt(bu) !=0)
				crit.add(Restrictions.eq("buCode", Integer.parseInt(bu)));
			if(district!=null && Integer.parseInt(district) !=0)
				crit.add(Restrictions.eq("district", Integer.parseInt(district)));
			
			if(branch!=null && Integer.parseInt(branch) !=0)
				crit.add(Restrictions.eq("branchCode", Integer.parseInt(branch)));
			
			if(city!=null && !city.equals("0"))
				crit.add(Restrictions.eq("cityCode", city));
			
			if(office!=null && !office.equals("0"))
				crit.add(Restrictions.eq("officeCode", office));
			
			if(ssc!=null && !ssc.equals("0"))
				crit.add(Restrictions.eq("sscCode", ssc));
			crit.add(Restrictions.eq("status", true));
			
			announcementList=(ArrayList)crit.list();
		
		  
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return announcementList;
	}
	 /**
	  * <p>Method is used to get all announcement created.</p>
	 * @param req 
	  * @return  ArrayList of announcement
	  */
	public  ArrayList getAllAnnouncements(HttpServletRequest req)
	{
		Session session = null;
		ArrayList announcementList = new ArrayList();
		User userObj = (User)req.getSession().getAttribute("currUserObj");
		
		try{
			
			int month=Calendar.getInstance().get(Calendar.MONTH)+1;
			int year= Calendar.getInstance().get(Calendar.YEAR);
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			
			Criteria crit = session.createCriteria(Announcement.class);
			crit.add(Restrictions.eq("status", true));
			crit.add(Restrictions.sqlRestriction("MONTH(PUBLISHED_DATE)=?",month,IntegerType.INSTANCE));
			crit.add(Restrictions.sqlRestriction("YEAR(PUBLISHED_DATE)=?",year,IntegerType.INSTANCE));
			
			
			if(userObj.isBuLevel() && userObj.getBuCode()!=0){
				crit.add(Restrictions.eq("buCode", userObj.getBuCode()));
			}
			if(userObj.isDistrictLevel()){
				crit.add(Restrictions.eq("district", userObj.getDistrict()));
			}
			if(userObj.isBranchLevel()){
				crit.add(Restrictions.eq("branchCode", userObj.getBranchCode()));
			}
			if(userObj.isCityLevel()){
				crit.add(Restrictions.eq("cityCode", userObj.getCityCode()));
			}
			if(userObj.isSscLevel()){
				crit.add(Restrictions.eq("sscCode", userObj.getSscCode()));
			}
			if(userObj.isOfficeLevel()){
				crit.add(Restrictions.eq("officeCode", userObj.getOfficeCode()));
			}
			
			
			announcementList = (ArrayList)crit.list();
			
			
			
			
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return announcementList;
	}

	 /**
	  * <p>Method is used to get all announcement for Rest service</p>
	  * @return  ArrayList of announcement
	  */
	public  ArrayList getAnnouncementsRest(AamData aamData, String agentId)
	{
		log.log(Level.SEVERE,"AnnouncementMaintenance -->  getAnnouncementsRest ");
		Session session = null;
		ArrayList announcementList = new ArrayList();
		
		try{
			
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Date sdate=new Date();
			log.log(Level.SEVERE,"Start Time "+sdate.getTime());
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			Date now=new Date();
			Query query=session.createQuery("FROM Announcement where status = 1  and expDate >= :expDate and publishedDate <= :publishedDate and  "
					+ "( (buCode=:bucode  and district=0) "
					+ "or (buCode=:bucode  and district=:distcode and  branchCode=0)"
					+ "or (buCode=:bucode  and district=:distcode and branchCode=:branchCode and  cityCode='0')"
					+ "or (buCode=:bucode  and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode='0')"
					+ "or (buCode=:bucode  and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode=:ssccode and officeCode='0')"
					+ "or (buCode=:bucode  and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode=:ssccode and officeCode =:officeCode )  )");
			
			query.setParameter("expDate",sdf1.parse(sdf1.format(now)));
			query.setParameter("publishedDate",sdf1.parse(sdf1.format(now)));
			query.setParameter("bucode",aamData.getBuCode());
			query.setParameter("distcode",aamData.getDistrictCode());
			query.setParameter("branchCode",aamData.getBranchCode());
			query.setParameter("citycode", aamData.getCity());
			query.setParameter("ssccode", aamData.getSsc());
			query.setParameter("officeCode",aamData.getOfficeCode());
			announcementList=(ArrayList<Announcement>) query.setCacheable(true).list();
			
			Date edate=new Date();
			log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LogsMaintenance logsMain=new LogsMaintenance();
				logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return announcementList;
	}
	
	/**
	  * <p>Method is used to get all Deleted announcement for Rest service</p>
	  * @return  ArrayList of announcement
	  */
	public  ArrayList getDeletedAnnouncementsRest()
	{
		log.log(Level.SEVERE,"AnnouncementMaintenance -->  getDeletedAnnouncementsRest ");
		Session session = null;
		ArrayList announcementList = new ArrayList();
		
		
		try{
			
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Date sdate=new Date();
			log.log(Level.SEVERE,"Start Time "+sdate.getTime());
			
			Query query=session.createQuery("SELECT annoucement_code FROM Announcement where status = 0");
			announcementList=(ArrayList<Announcement>) query.setCacheable(true).list();
			
			Date edate=new Date();
			log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LogsMaintenance logsMain=new LogsMaintenance();
				logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return announcementList;
	}

	 /**
	  * <p>Method used to get details of a particular announcement.</p>
	  * @param announcementCode   Announcement Code
	  * @return Announcement Object
	  */


	public Announcement getAnnouncement(int announcementCode)
	{
		 Session session = null;;
		 Announcement announcement = new Announcement();
		 
		try{
		session = HibernateFactory.openSession();
		session.setDefaultReadOnly(true);
		announcement = (Announcement)session.get(Announcement.class,announcementCode);
	
	  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		
		return announcement;
	}
	 /**
	  * <p>Method takes responsiblity to delete a Announcement.Store action in audit table.</p>
	  * @param announcementCode  Announcement Code
	  * @param requestParameters Servlet Request Parameter
	  */
	public void deleteAnnouncement(int announcementCode,HttpServletRequest requestParameters)
	{
		log.log(Level.INFO,"inside AnnouncementMaintenance --> deleteAnnouncement --");
		
		Session session = null;
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		String status = "N";
		Announcement announcement = new Announcement();
		
		try{
		session = HibernateFactory.openSession();
		Transaction tx = session.beginTransaction();
	    announcement = (Announcement)session.get(Announcement.class,announcementCode);
		announcement.setStatus(false);
		session.update(announcement);
		tx.commit();
		status = "Y";
		
		log.log(Level.INFO,"---AnnouncementMaintenance Deleted Successfully---");
	  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		 MsgObject msgObj = null;
		 AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		 if(status.equals("Y")){
			 msgObj = new MsgObject("The Announcement has been successfully Deleted");
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_DELETE, "SUCCESS " + announcement.toString()));
		 }
		 else{
			 msgObj = new MsgObject("The Announcement has not been Deleted"); 
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_DELETE, "FAILED " +announcement.toString()));
		 }
				
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("CacheName", "Announcement");
		Pager pager=announcementListing(requestParameters);
		requestParameters.setAttribute("pager", pager);
	     requestParameters.getSession().setAttribute("pager",pager);
	}
	 /**
	  * <p>Method is used to update a announcement details.Store action in audit table.</p>
	  * @param announcement  Announcement object
	  * @param requestParameters  Servlet Request Parameter
	  * @return  Announcement object
	  */

	public Announcement updateAnnouncement(Announcement announcement, HttpServletRequest requestParameters)
	{
		log.log(Level.INFO,"AnnouncementMaintenance --> updateAnnouncement");
		Session session = null;
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		String status = "N";
//		ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		String announcementPath  = msgProps.getString("AnnouncementPath");
		//Map<String,String> configurationMap =(Map<String,String>) requestParameters.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		//String announcementPath  = configurationMap.get("AnnouncementPath");//E:/IMOCN/announcement
		String tempDir = System.getProperty("java.io.tmpdir");
		String file_name = "";
		announcement.setModifiedBy(userObj.getStaffLoginId());
		announcement.setModificationDate(new Date());
		
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			announcement.setModifiedBy(userObj.getStaffLoginId());
			announcement.setModificationDate(new Date());
			
			if(requestParameters.getSession().getAttribute("material_file_name")!=null){  //checks if user  uploaded files  to update
				file_name = (String)requestParameters.getSession().getAttribute("material_file_name");
				/*if(file_name.contains(".pdf") || file_name.contains(".PDF")||
						file_name.contains(".ppt") || file_name.contains(".PPT")||
						file_name.contains(".jpg") || file_name.contains(".JPG")||
						file_name.contains(".jpeg") || file_name.contains(".JPEG"))*/
				if(file_name.contains(".pdf") || file_name.contains(".PDF")){ //checks if pdf file is uploaded
					String filePath = uploadFile(tempDir,file_name,requestParameters,announcement,"material_byte_session");
					announcement.setAttachmentPath(filePath);
				}
				
		   }
			session.update(announcement);
			if(requestParameters.getSession().getAttribute("material_file_name")!=null){  //checks if user  uploaded files  to update
				/*if(file_name.contains(".pdf") || file_name.contains(".PDF")||
						file_name.contains(".ppt") || file_name.contains(".PPT")||
						file_name.contains(".jpg") || file_name.contains(".JPG")||
						file_name.contains(".jpeg") || file_name.contains(".JPEG"))*/
				if(file_name.contains(".pdf") || file_name.contains(".PDF")){ //checks if pdf file is uploaded
					Query deleteQ = session.createQuery("delete AnnouncementMaterial where annoucementCode = "+announcement.getAnnoucement_code()+"");
			    	deleteQ.executeUpdate(); 
					 
			    	 byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute("material_byte_session");
			    	 String field_name = (String)requestParameters.getSession().getAttribute("material_field_name");
			    	 AnnouncementMaterial annMat = new AnnouncementMaterial();
				    	annMat.setAnnoucementCode(announcement.getAnnoucement_code());
				    	annMat.setMaterialName(file_name);
				    	annMat.setFieldName(field_name);
				    	annMat.setMaterial(bytearray);
				    	session.save(annMat);
				    }
					
				requestParameters.getSession().removeAttribute("material_file_name");// deleting uploaded file from session
				requestParameters.getSession().removeAttribute("material_byte_session");//
				requestParameters.getSession().removeAttribute("material_field_name");//
				log.log(Level.INFO,"---Announcement Material removed from session---");
		   }
			tx.commit();
			status = "Y";
			log.log(Level.INFO,"---AnnouncementMaintenance Updated Successfully---");
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			
			
	}
		 MsgObject msgObj  = null;
		 AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		 if(status.equals("Y")){
			 msgObj =  new MsgObject("The Announcement has been successfully Updated");
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_UPDATE, "SUCCESS "+announcement.toString()));
		 }else{
			 msgObj =  new MsgObject("The Announcement has not been Updated");
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_UPDATE, "FAILED "+announcement.toString()));
		 }
		
		 requestParameters.setAttribute("messageObject", msgObj);
		 requestParameters.setAttribute("CacheName", "Announcement");	
		 Pager pager=announcementListing(requestParameters);
			requestParameters.setAttribute("pager", pager);
		     requestParameters.getSession().setAttribute("pager",pager);
		return announcement;
	}
	/**
	 * <p>Method takes care for uploading file.</p>
	 * @param annPath    path where file is stored.
	 * @param tempDir    temporary directory
	 * @param fileName   uploaded file name
	 * @param requestParameters Servlet Request Parameter
	 * @param announcement     Announcement Object
	 * @param byteSession      file in byte stored in seesion
	 * @return  path as string
	 */
	public String uploadFile(String tempDir,String fileName,HttpServletRequest requestParameters,Announcement announcement,String byteSession){
		FileOutputStream stream=null;
		//upload material
		String path = "";
		
		try {
			log.log(Level.INFO,"---AnnouncementMaintenance File upload Started---");
		    //String serverFilename = annPath+"/"+ "ANN_" + announcement.getToken();
		    String serverFilename = requestParameters.getRealPath( File.separator );
			if(!serverFilename.endsWith( File.separator ))
				serverFilename = serverFilename + File.separator;
			
				serverFilename = requestParameters.getRealPath( File.separator ) + File.separator + "resources" +  File.separator 
				+ "uploadMaterial"+  File.separator + "ANN_" + announcement.getToken() ;
				
			File uploadedFolder = new File(serverFilename);
			if(!uploadedFolder.exists()){
				uploadedFolder.mkdirs();
			}
			byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute(byteSession);
		
				
						stream = new FileOutputStream(serverFilename+"/"+fileName);
					    stream.write(bytearray);
				
					    path = "ANN_" + announcement.getToken()+"/"+fileName;
			//delete temp directory
			File temp_file = new File(tempDir+"/" + fileName);
			log.log(Level.INFO,"---Deleting temp folder---");
       	    FileUtils.deleteFileNFolder(temp_file);
       	 requestParameters.setAttribute("CacheName", "Announcement");
		} catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
			}
		finally{if(stream!=null)try{stream.close();}catch(Exception e){log.log(Level.SEVERE, e.getMessage());e.printStackTrace();}}
       	return path;
	
	}
	/**
	 * <p>Method is used to check  duplicate announcement.</p>
	 * @param announcement  Announcement object
	 * @return  true(if duplicate announcement found)/false(if duplicate announcement not found )
	 */
	public boolean checkDuplicateAnnouncement(Announcement announcement){
		log.log(Level.INFO,"---Duplicate Announcement checking---");
		Session session = null;
		
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Query selectQ = session.createQuery("select  annoucement_code from Announcement  where subject =:subject and publishedDate=:publishedDate and annoucement_code<>:annoucement_code");
			selectQ.setParameter("subject", announcement.getSubject());
			selectQ.setParameter("publishedDate", announcement.getPublishedDate());
			selectQ.setParameter("annoucement_code", announcement.getAnnoucement_code());
			
			List list = selectQ.list();
			if(list!=null && list.size() > 0)
				return true;
			else 
				return false;
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
		}
		return false;
		
	}
	 /**
	  * <p>Method is used to get Announcement material based on announcement code </p>
	  * @param  announcementCode 
	  * @return  AnnouncementMaterial object
	  */
	public   AnnouncementMaterial  getAnnouncementMaterial(int announcementCode)
	{
		log.log(Level.INFO,"AnnouncementMaintenance ---> getAnnouncementMaterial ");
		 Session session = null;
		 AnnouncementMaterial mat = null;
		
		try{
		session = HibernateFactory.openSession();
		session.setDefaultReadOnly(true);
		Query selectQ = session.createQuery("select materialName,material from AnnouncementMaterial where annoucementCode = "+announcementCode+"");
		List list = selectQ.list();
		
		if(list!=null && list.size() > 0){
			mat = new AnnouncementMaterial();
		   Object [] objectDoc = (Object []) list.get(0);
		   mat.setMaterialName(( String)objectDoc[0]);
		   mat.setMaterial( ( byte[] )objectDoc[1]);
		}else{log.log(Level.INFO,"AnnouncementMaintenance ---> getAnnouncementMaterial : No Material Found ");}
	
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			 LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
			return mat;
	}
	
	
	 /**
	  * <p>Method is used to get Announcement material File based on announcement code </p>
	  * @param  announcementCode 
	  * @return  String
	  */
	
	
	public   String  getmaterialFile(Announcement announcement,HttpServletRequest req)
	{
		log.log(Level.INFO,"AnnouncementMaintenance ---> getmaterialFile ");
		 Session session = null;
		AnnouncementMaterial material=null;
		// ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
	    String url  = "";//msgProps.getString("APP_URL");
		String path="#";
		
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			 Criteria criteria = session.createCriteria(AnnouncementMaterial.class);
			 criteria.add(Restrictions.eq("annoucementCode", announcement.getAnnoucement_code()));
			 ArrayList list = (ArrayList) criteria.list();
			
			 if(list.size()>0){
				 material=(AnnouncementMaterial)list.get(0);
				 String fname = announcement.getAttachmentPath().substring(announcement.getAttachmentPath().lastIndexOf('/') + 1);
				
				 path=req.getRealPath("/");
			     if(path.endsWith("/") || path.endsWith("\\")){
			      path += "resources" + File.separator+ "material";
			     }else{
			      path += File.separator+ "resources" + File.separator+ "material";  
			     }
			     
			     File  dir = new File(path);
			     if(!dir.exists()){
			    	 dir.mkdirs();
				 }
			     
			     path += File.separator + fname;
				 File file=new File(path);
				 if(!file.exists()){
					 file.createNewFile();
				 }
				 
				 FileOutputStream stream = new FileOutputStream(path);
				 stream.write(material.getMaterial());
				 stream.close();
				 path="resources" + File.separator+ "material"+File.separator+fname;
				 url = "resources/" +"material/"+fname;
			 }
			 
			 
		
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AnnouncementMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
			return url;
	}
	
	
	public static final String SUBJECT_PARAM = "subject";
	public static final String PUBLISHED_DATE_PARAM = "publishedDate";
	public static final String EXPIRED_DATE_PARAM = "expiredDate";
	public static final String MESSAGE_TYPE_PARAM = "messageType";
	public static final String MESSAGE_PARAM = "message";
	public static final String BU_PARAM = "bu";
	public static final String DISTRICT_PARAM = "district";
	public static final String CITY_PARAM = "city";
	public static final String SSC_PARAM = "ssc";
	public static final String POSTED_BY_PARAM = "postedBy";
	public static final String MONTH_PARAM = "month";
	public static final String YEAR_PARAM = "year";
	
	 public static final KeyObjPair MONTH_VALUE_PAIR[] = {
	        new KeyObjPair("01", "January"), new KeyObjPair("02", "February"), new KeyObjPair("03", "March"), new KeyObjPair("04", "April"), new KeyObjPair("05", "May"), new KeyObjPair("06", "June"), new KeyObjPair("07", "July"), new KeyObjPair("08", "August"), new KeyObjPair("09", "September"), new KeyObjPair("10", "October"), 
	        new KeyObjPair("11", "November"), new KeyObjPair("12", "December")
	    };
	
}
