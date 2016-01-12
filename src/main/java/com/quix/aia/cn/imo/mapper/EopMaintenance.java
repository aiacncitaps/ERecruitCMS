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
* 19-May-2015       Nibedita           copy rights added.Eop code,special group upload code added
* 08-June-2015      Nibedita          topic upload code added
* 12-June-2015      Nibedita          Eop multiple schedule upload issues          
* 14-August-2015    Maunish            Modified 
* 20-August-2015    Nibedita           Email sent  to registered candidates when an event  updated
* 09-Sept-2015         Nibedita           file uploaded to db
****************************************** *********************************** */
package com.quix.aia.cn.imo.mapper;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;

import com.archerlogic.aia.cn.helpers.iCalendar.iCalendarEOPHelper;
import com.quix.aia.cn.imo.constants.ApplicationAttribute;
import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.branch.Branch;
import com.quix.aia.cn.imo.data.bu.Bu;
import com.quix.aia.cn.imo.data.channel.Channel;
import com.quix.aia.cn.imo.data.city.City;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.district.District;
import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.event.EventCandidate;
import com.quix.aia.cn.imo.data.event.EventMaterial;
import com.quix.aia.cn.imo.data.event.SpecialGroup;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.office.Office;
import com.quix.aia.cn.imo.data.ssc.Ssc;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.EmailNotification;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FileUtils;
import com.quix.aia.cn.imo.utilities.ImoUtilityData;
import com.quix.aia.cn.imo.utilities.KeyObjPair;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;
import com.quix.aia.cn.imo.utilities.PathDetail;
/**
 * <p>Logic to create,update,delete,search EOP Schedules.</p>
 * 
 * @author Nibedita
 * @version 1.0
 *
 */
public class EopMaintenance {
	static Logger log = Logger.getLogger(EopMaintenance.class.getName());
	 /**
	  * Constructor
	  * Hibernate configuration done
	  */
	public EopMaintenance(){
		HibernateFactory.buildIfNeeded();
	}
	 /**
	  * <p>Method takes care to map Event fields.</p>
	  * @param event Event Object
	  * @param requestParameters  Servlet Request Parameter
	  * @return  Event Object/Error object
	  */
	
	public Object mapForm1(Event event, HttpServletRequest requestParameters)
	{
		log.log(Level.INFO,"EopMaintenance --> mapForm1 ");
		if(event == null)
		{
			event = new Event();
			return event;
		}
		
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		//if(requestParameters.getParameter("topic") == null || requestParameters.getParameter("topic") .length() == 0)
			// return new ErrorObject("This is a required field:", "EOP Topic",localeObj);
		if(requestParameters.getParameter("eventName") == null || requestParameters.getParameter("eventName") .length() == 0)
			 return new ErrorObject("Event Name", " field is required",localeObj);
		if(requestParameters.getParameter("eventDate") == null || requestParameters.getParameter("eventDate") .length() == 0)
			return new ErrorObject("Date", " field is required",localeObj);
		if(requestParameters.getParameter("description") == null || requestParameters.getParameter("description") .length() == 0)
			 return new ErrorObject("EOP Description", " field is required",localeObj);
		if(requestParameters.getParameter("location") == null || requestParameters.getParameter("location") .length() == 0)
			 return new ErrorObject("Location", " field is required",localeObj);
		if(requestParameters.getParameter("speaker") == null || requestParameters.getParameter("speaker") .length() == 0)
			 return new ErrorObject("Speaker", " field is required",localeObj);
//		if(requestParameters.getParameter("openTo") == null || requestParameters.getParameter("openTo").equals("0"))
//			 return new ErrorObject("This is a required field:", "Open To");
		if(requestParameters.getParameter("organizer") == null || requestParameters.getParameter("organizer").equals("0"))
			 return new ErrorObject("Organizer", " field is required",localeObj);
		
		System.out.println(requestParameters.getParameter("openTo"));
		System.out.println(requestParameters.getParameter("bu"));
		
		if(!"SG".equals(requestParameters.getParameter("openTo"))){	
			if(requestParameters.getParameter("bu") == null || requestParameters.getParameter("bu").equals("0"))
				 return new ErrorObject("BU", " field is required",localeObj);
		}
		
//		if(requestParameters.getParameter("agentTeam") == null || requestParameters.getParameter("agentTeam").equals("0"))
//			 return new ErrorObject("This is a required field:", "Agent Team");
		if(requestParameters.getParameter("organizer") == null || requestParameters.getParameter("organizer").equals("0"))
			return new ErrorObject("Organizer", " field is required",localeObj);
		if(requestParameters.getParameter("estCand")!=null && requestParameters.getParameter("estCand").length() > 0){
			if(!LMSUtil.validInt(requestParameters.getParameter("estCand"))){
				return new ErrorObject("", "Estimated Candidates Should be a Number",localeObj);
			}else{
				if(Integer.parseInt(requestParameters.getParameter("estCand"))<=0){
					return new ErrorObject("", "Estimated Candidates Should be more then 0",localeObj);
				}else{
					event.setEstimatedCandidates(Integer.parseInt(requestParameters.getParameter("estCand")));
				}
			}
		}
		
		if(requestParameters.getParameter("openTo")!=null && "SG".equals(requestParameters.getParameter("openTo"))){
			if(requestParameters.getSession().getAttribute("csv_file_name")==null && event.getSpecialGrListingPath().length() == 0){
				return new ErrorObject("", "Please Upload Special User Csv file",localeObj);
			}
		}
		if(requestParameters.getParameter("openTo")!=null){
			if(requestParameters.getSession().getAttribute("csv_file_name")!=null){
				Map<String,String> configurationMap =(Map<String,String>) requestParameters.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
				String eopPath  = configurationMap.get("EopPath");//"E:/IMOCN/eop"
				String tempDir = System.getProperty("java.io.tmpdir");
				String file_name = (String)requestParameters.getSession().getAttribute("csv_file_name");
				
				boolean flag=checkSpecialGroupAgentId(eopPath,tempDir,file_name,requestParameters,"csv_byte_session");
				if(flag==false){
					requestParameters.getSession().removeAttribute("csv_file_name");
					requestParameters.getSession().removeAttribute("csv_byte_session");
					return new ErrorObject("", "Please upload AgentCode Which belogs in your Id",localeObj);
					
				}
				
			
			}
			
			if("SG".equals(requestParameters.getParameter("openTo"))){
				event.setBuCode(userObj.getBuCode());
				event.setDistrict(userObj.getDistrict());
				event.setBranchCode(userObj.getBranchCode());
				event.setCityCode(userObj.getCityCode()+"");
				event.setOfficeCode(userObj.getOfficeCode()+"");
				event.setSscCode(userObj.getSscCode()+"");
				event.setAgentTeam(requestParameters.getParameter("agentTeam"));
			}else{
				event.setBuCode(Integer.parseInt(requestParameters.getParameter("bu")));
				event.setDistrict(Integer.parseInt(requestParameters.getParameter("district")));
				event.setBranchCode(Integer.parseInt(requestParameters.getParameter("branch")));
				event.setCityCode(requestParameters.getParameter("city"));
				event.setOfficeCode(requestParameters.getParameter("office"));
				event.setSscCode(requestParameters.getParameter("ssc"));
				event.setAgentTeam(requestParameters.getParameter("agentTeam"));
			}
			
			
		}
		event.setEventType(requestParameters.getParameter("eventType"));
		event.setEventName(requestParameters.getParameter("eventName").trim());
		event.setTopic(requestParameters.getParameter("eventName").trim()/*requestParameters.getParameter("topic")*/);
		event.setEopDescription(requestParameters.getParameter("description"));
		event.setEventDate(LMSUtil.convertStringToDate(requestParameters.getParameter("eventDate")));
		/*if(checkDuplicateEvent(event))
			 return new ErrorObject("", "Event Already Exist.Duplicate Event not allowed.",localeObj);*/
		event.setStartTime(LMSUtil.convertoDateHHMMAMPM1(requestParameters.getParameter("startHour") + ":" + requestParameters.getParameter("startMinute") + " " + requestParameters.getParameter("startMeridiem")));
		event.setEndTime(LMSUtil.convertoDateHHMMAMPM1(requestParameters.getParameter("endHour") + ":" + requestParameters.getParameter("endMinute") + " " + requestParameters.getParameter("endMeridiem")));
		event.setSpeaker(requestParameters.getParameter("speaker"));
		Date today = new Date();
		String toDayStr = LMSUtil.convertDateToString(today);
		today = LMSUtil.convertStringToDate(toDayStr);
		if(event.getEventDate().before(today)){
			 return new ErrorObject("Event Date  Should Not Be Before Today", "",localeObj);
		}
		
		if(event.getEndTime().before(event.getStartTime()) || event.getEndTime().equals(event.getStartTime()))
			 return new ErrorObject("Start Time Should Be Earlier Than End Time", "",localeObj);
		
		
		
		SimpleDateFormat ra = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		if(event.getEventDate().equals(today)){
			
			Date crtTime=new Date(System.currentTimeMillis());
			Date stTime=event.getStartTime();
			
			try {
				crtTime=ra.parse(ra.format(crtTime));
				stTime=ra.parse(ra.format(stTime));
				if(crtTime.after(stTime))
				{
					 return new ErrorObject("Start Time Should Be After Than Current Time", "",localeObj);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			}
		
		}
		
		event.setLocation(requestParameters.getParameter("location"));
		event.setOpenTo(requestParameters.getParameter("openTo"));
		if(requestParameters.getParameter("publicRegistration")!=null)
			event.setOpenToRegistration(requestParameters.getParameter("publicRegistration"));
		event.setOrganizer(Integer.parseInt(requestParameters.getParameter("organizer")));
		
		
		
		event.setStatus(true);
		
		return event;
	}
	
	/**
	  * <p> Code for Schedule creation.Success and failure message object set here.</p>
	  * @param event  Event Object
	  * @param requestParameters Servlet Request Parameter
	  * @return  Event Object
	  */
	public Object createNewEvent(Event event, HttpServletRequest requestParameters)
	{
		 log.log(Level.INFO,"---EopMaintenance Creation--- ");
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		 MsgObject msgObj = null;
		 	event.setCreatedBy(userObj.getStaffLoginId());;
			event.setCreationDate(new Date());
			event.setModifiedBy(userObj.getStaffLoginId());
			event.setModificationDate(new Date());
		int status = insertEvent(event,requestParameters);
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		if(status !=0){
			msgObj = new MsgObject("The new Event has been successfully created.");
			auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+event.toString()));
		}
		else{
			msgObj = new MsgObject("The new Event has not been created.");
			auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+event.toString()));
		}
		
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("CacheName", "EOP");
		Pager pager=eventListing(requestParameters);
		requestParameters.setAttribute("pager", pager);
	     requestParameters.getSession().setAttribute("pager",pager);
		//requestParameters.getSession().setAttribute("pager", eventSearchListing(requestParameters));
		
		return event;
	}
	/**
	 * <p>Schedule insert code done here.Store action in audit table.</p>
	 * @param event  Event Object
	 * @param requestParameters   Servlet Request Parameter
	 * @return  integer value(primary key) generated
	 */
	public int insertEvent(Event event,HttpServletRequest requestParameters){
		 log.log(Level.INFO,"EopMaintenance --> insertEvent");
		Integer key = 0;
		Session session = null;
		String file_name="";
//		ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		String eopPath  = msgProps.getString("EopPath");
		Map<String,String> configurationMap =(Map<String,String>) requestParameters.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		String eopPath  = configurationMap.get("EopPath");//"E:/IMOCN/eop"
		String tempDir = System.getProperty("java.io.tmpdir");
		event.setToken(LMSUtil.getRendomToken());
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
		
			//upload pdf material
			if(requestParameters.getSession().getAttribute("material_file_name")!=null){//checks if user  uploaded files or not
				file_name = (String)requestParameters.getSession().getAttribute("material_file_name");
				if(file_name.contains(".pdf") || file_name.contains(".PDF") ||
						file_name.contains(".jpg") || file_name.contains(".JPG") ||
						file_name.contains(".jpeg") || file_name.contains(".JPEG")||
						file_name.contains(".doc") || file_name.contains(".DOC")||
						file_name.contains(".docs") || file_name.contains(".DOCS") || 
						file_name.contains(".docx") || file_name.contains(".DOCX")){
					String filePath = uploadFile(eopPath,tempDir,file_name,requestParameters,event,"material_byte_session");
		        	event.setProfilePath(filePath);
				}
				
		   }
			//upload topic
			if(requestParameters.getSession().getAttribute("topic_file_name")!=null){
				file_name = (String)requestParameters.getSession().getAttribute("topic_file_name");
				if(file_name.contains(".pdf") || file_name.contains(".PDF") 
						|| file_name.contains(".jpg") || file_name.contains(".JPG")
						|| file_name.contains(".png") || file_name.contains(".PNG")
						|| file_name.contains(".gif") || file_name.contains("GIF")){
					String filePath = uploadFile(eopPath,tempDir,file_name,requestParameters,event,"topic_byte_session");
		        	event.setTopicPath(filePath);
				}
			
		   }
			
			//upload csv material
			if(requestParameters.getSession().getAttribute("csv_file_name")!=null){//checks if user csv uploaded files or not
				file_name = (String)requestParameters.getSession().getAttribute("csv_file_name");
				if(file_name.contains(".csv") || file_name.contains(".CSV")){
					String filePath = uploadFile(eopPath,tempDir,file_name,requestParameters,event,"csv_byte_session");
					event.setSpecialGrListingPath(filePath);
					
				}
				if(file_name.contains(".xlsx") || file_name.contains(".XLSX") || file_name.contains(".xls") || file_name.contains(".XLS") ){
					String filePath = uploadFile(eopPath,tempDir,file_name,requestParameters,event,"csv_byte_session");
					event.setSpecialGrListingPath(filePath);
					
					
					
				}
				requestParameters.getSession().removeAttribute("csv_file_name");
				requestParameters.getSession().removeAttribute("csv_byte_session");
				
		    }
			
			key = (Integer)session.save(event);
			
			if(event.getSpecialGrListingPath().length() > 0){
				if(file_name.contains(".xlsx") || file_name.contains(".XLSX") || file_name.contains(".xls") || file_name.contains(".XLS") ){
					insertSpecialGroup(session,key,eopPath +  File.separator  +event.getSpecialGrListingPath(),"INSERT","xls");
				}else{
					insertSpecialGroup(session,key,eopPath +  File.separator  +event.getSpecialGrListingPath(),"INSERT","csv");
				}
			}
			if(requestParameters.getSession().getAttribute("material_file_name")!=null){
				file_name = (String)requestParameters.getSession().getAttribute("material_file_name");
				 String field_name = (String)requestParameters.getSession().getAttribute("material_field_name");
				 if(file_name.contains(".pdf") || file_name.contains(".PDF") 
							|| file_name.contains(".jpg") || file_name.contains(".JPG")
							|| file_name.contains(".png") || file_name.contains(".PNG")
							|| file_name.contains(".gif") || file_name.contains("GIF")
							|| file_name.contains(".doc") || file_name.contains(".DOC")
							|| file_name.contains(".docs") || file_name.contains(".DOCS")
							|| file_name.contains(".docx") || file_name.contains(".DOCX")
							|| file_name.contains(".jpeg") || file_name.contains(".JPEG")
						 ){
					storeMaterialToDB(session,key,"INSERT",requestParameters,"material_byte_session",file_name,field_name);
				}
				requestParameters.getSession().removeAttribute("material_file_name");// removing uploaded file details from session after file is uploaded
				requestParameters.getSession().removeAttribute("material_byte_session");
				requestParameters.getSession().removeAttribute("material_field_name");
			  }
			if(requestParameters.getSession().getAttribute("topic_file_name")!=null){
				file_name = (String)requestParameters.getSession().getAttribute("topic_file_name");
				 String field_name = (String)requestParameters.getSession().getAttribute("topic_field_name");
				if(file_name.contains(".pdf") || file_name.contains(".PDF") 
						|| file_name.contains(".jpg") || file_name.contains(".JPG")
						|| file_name.contains(".png") || file_name.contains(".PNG")
						|| file_name.contains(".gif") || file_name.contains("GIF")){
					storeMaterialToDB(session,key,"INSERT",requestParameters,"topic_byte_session",file_name,field_name);
				}
				requestParameters.getSession().removeAttribute("topic_file_name");
				requestParameters.getSession().removeAttribute("topic_byte_session");
				requestParameters.getSession().removeAttribute("topic_field_name");
			}
		
			tx.commit();
			 log.log(Level.INFO,"---EopMaintenance Event Created Successfully--- ");
			
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
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
	  * <p>Schedule listing done here.</p>
	  * @param req   Servlet Request Parameter
	  * @return  pager object
	  */
	public  Pager eventSearchListing(HttpServletRequest req)
	{
		LinkedList item = new LinkedList();
		
		Map<String,String> eopMap = new HashMap<String,String>();
		if(req.getParameter(EVENT_NAME_PARAM)!=null)
			eopMap.put("EVENT_NAME", req.getParameter(EVENT_NAME_PARAM));
		if(req.getParameter(EVENT_TYPE_PARAM)!=null)
			eopMap.put("EVENT_TYPE", req.getParameter(EVENT_TYPE_PARAM));
		if(req.getParameter(MONTH_PARAM)!=null)
			eopMap.put("MONTH", req.getParameter(MONTH_PARAM));
		if(req.getParameter(YEAR_PARAM)!=null)
			eopMap.put("YEAR",req.getParameter(YEAR_PARAM));
		if(req.getParameter(ORGANISER_PARAM)!=null)
			eopMap.put("ORGANISER",req.getParameter(ORGANISER_PARAM));
		if(req.getParameter(BU_PARAM)!=null)
			eopMap.put("BU", req.getParameter(BU_PARAM));
		if(req.getParameter(DISTRICT_PARAM)!=null)
			eopMap.put("DIST", req.getParameter(DISTRICT_PARAM));
		
		if(req.getParameter("branch")!=null)
			eopMap.put("branchCode", req.getParameter("branch"));
		
		if(req.getParameter("office")!=null)
			eopMap.put("officeCode", req.getParameter("office"));
		if(req.getParameter(CITY_PARAM)!=null)
			eopMap.put("CITY", req.getParameter(CITY_PARAM));
		if(req.getParameter(SSC_PARAM)!=null)
			eopMap.put("SSC", req.getParameter(SSC_PARAM));
		if(req.getParameter(AGENT_TEAM_PARAM)!=null)
			eopMap.put("AGENT_TEAM", req.getParameter(AGENT_TEAM_PARAM));
		
		
		//req.getSession().setAttribute("EOP_SEARCH_OBJ", eopMap);
		
		Event event = null;
		ArrayList listAllEvents = getSearchedEvents(req);
		for(int i = 0; i < listAllEvents.size(); i++)
		{
			event = new Event();
			event = (Event)listAllEvents.get(i);
			item.add(event.getGetEventListingTableRow());
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
	  * <p>Schedule listing done here.</p>
	  * @param req   Servlet Request Parameter
	  * @return  pager object
	  */
	public  Pager eventListing(HttpServletRequest req)
	{
		LinkedList item = new LinkedList();
		Event event = null;
		ArrayList listAllEvents = getaLLEvents(req);
		for(int i = 0; i < listAllEvents.size(); i++)
		{
			event = new Event();
			event = (Event)listAllEvents.get(i);
			item.add(event.getGetEventListingTableRow());
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
	  *<p>Method is used to get searched Schedule details.</p>
	  * @param req  Servlet Request Parameter
	  * @return  ArrayList of Schedule
	  */
	public  ArrayList getSearchedEvents(HttpServletRequest req)
	{
		 Session session = null;
		ArrayList eventList = new ArrayList();
		String eventName = req.getParameter(EVENT_NAME_PARAM);
		String eventType = req.getParameter(EVENT_TYPE_PARAM);
		String month = req.getParameter(MONTH_PARAM);
		String year = req.getParameter(YEAR_PARAM);
		String organiser = req.getParameter(ORGANISER_PARAM);
		String bu = req.getParameter(BU_PARAM);
		String district = req.getParameter(DISTRICT_PARAM);
		String branchCode = req.getParameter("branch");
		String officeCode = req.getParameter("office");
		String city = req.getParameter(CITY_PARAM);
		String ssc = req.getParameter(SSC_PARAM);
		String agentTeam = req.getParameter(AGENT_TEAM_PARAM);
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			
			Criteria crit = session.createCriteria(Event.class);
			if(eventName!=null && eventName.length() > 0)
				crit.add(Restrictions.like("eventName",eventName,MatchMode.ANYWHERE));
			if(eventType!=null && eventType.length() > 0)
				crit.add(Restrictions.eq("eventType", eventType));
			if(organiser!=null && Integer.parseInt(organiser) !=0)
				crit.add(Restrictions.eq("organizer", Integer.parseInt(organiser)));
			if(month!=null)
				crit.add(Restrictions.sqlRestriction("MONTH(EVENT_DATE)=?", Integer.parseInt(month),IntegerType.INSTANCE));
			if(year!=null)
				crit.add(Restrictions.sqlRestriction("YEAR(EVENT_DATE)=?", Integer.parseInt(year),IntegerType.INSTANCE));
			if(bu!=null && Integer.parseInt(bu) !=0)
				crit.add(Restrictions.eq("buCode", Integer.parseInt(bu)));
			if(district!=null && Integer.parseInt(district) !=0)
				crit.add(Restrictions.eq("district", Integer.parseInt(district)));
			if(branchCode!=null && Integer.parseInt(branchCode) !=0)
				crit.add(Restrictions.eq("branchCode", Integer.parseInt(branchCode)));
			
			if(officeCode!=null && !officeCode.equals("0"))
				crit.add(Restrictions.eq("officeCode", officeCode));
			
			if(city!=null && !city.equals("0"))
				crit.add(Restrictions.eq("cityCode", city));
			if(ssc!=null && !ssc.equals("0"))
				crit.add(Restrictions.eq("sscCode", ssc));
			if(agentTeam!=null && Integer.parseInt(agentTeam) !=0)
				crit.add(Restrictions.eq("agentTeam", agentTeam));

			// get all events that are not deleted or have sync errors
            crit.add(Restrictions.or(
                    Restrictions.eq("status", true),
                    Restrictions.gt("calendarServiceError", 0)));
			
			eventList = (ArrayList)crit.list();
			
		  
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return eventList;
	}
	
	/**
	  *<p>Method is used to get searched Schedule details.</p>
	  * @param req  Servlet Request Parameter
	  * @return  ArrayList of Schedule
	  */
	public  ArrayList getaLLEvents(HttpServletRequest req)
	{
		 Session session = null;
		ArrayList eventList = new ArrayList();
		int month=Calendar.getInstance().get(Calendar.MONTH)+1;
		int year= Calendar.getInstance().get(Calendar.YEAR);
		User userObj = (User)req.getSession().getAttribute("currUserObj");
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			
			Criteria crit = session.createCriteria(Event.class);
			crit.add(Restrictions.sqlRestriction("MONTH(EVENT_DATE)=?", month,IntegerType.INSTANCE));
			crit.add(Restrictions.sqlRestriction("YEAR(EVENT_DATE)=?",year,IntegerType.INSTANCE));
			
			// get all that are not deleted, or those that have sync errors
            crit.add(Restrictions.or(
                    Restrictions.eq("status", true),
                    Restrictions.gt("calendarServiceError", 0))
            );
			
	        if(!userObj.getUserType().equalsIgnoreCase("AD")){
	        	
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
	        } 
			eventList = (ArrayList)crit.list();
			
		  
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
				//logsMain.insertLogs("EopMaintenance",Level.SEVERE+"","bu code : "+userObj.getBuCode()+"dist code : "+ userObj.getDistrict()+"branch code : "+userObj.getBranchCode()+"city code : "+ userObj.getCityCode()+"ssc code : "+ userObj.getSscCode()+"office code : "+userObj.getOfficeCode());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return eventList;
	}
	
	 /**
	  * <p>Method is used to get all Schedule created.</p>
	  * @return  ArrayList of announcement
	  */
  public  ArrayList getAllEvents()
	{
		Session session = null;
		ArrayList eventList = new ArrayList();
		
		try{
			session = HibernateFactory.openSession();
		
			Criteria crit = session.createCriteria(Event.class);
			crit.add(Restrictions.eq("status", true));
			eventList = (ArrayList)crit.list();
		  
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return eventList;
	}
   /**
	  * <p>Method used to get details of a particular Schedule.</p>
	  * @param eventCode   Event Code
	  * @return Event Object
	  */

	public Event getEvent(int eventCode)
	{
		 Session session = null;;
		 Event event = new Event();
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Query query= session.createQuery("FROM Event where event_code=:eventCode  and  status=:status");
			query.setParameter("eventCode", eventCode);
			query.setParameter("status", true);
			
		    List l = query.list();
		    if(l!=null && l.size() > 0){
		    	event = (Event)l.get(0);
		    }
		    
		  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		
		return event;
	}
	 /**
	  * <p>Method is used to update a Schedule details.Store action in audit table.</p>
	  * @param event  Event object
	  * @param requestParameters  Servlet Request Parameter
	  * @return  Event object
	  */
	public Event updateEvent(Event event, HttpServletRequest requestParameters)
	{
		Session session = null;
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		String status = "N";
		String file_name="";
//		ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		String eopPath  = msgProps.getString("EopPath");

		Map<String,String> configurationMap =(Map<String,String>) requestParameters.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		String eopPath  = configurationMap.get("EopPath");//"E:/IMOCN/eop"
		String tempDir = System.getProperty("java.io.tmpdir");
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
		
			if(requestParameters.getSession().getAttribute("material_file_name")!=null){
				file_name = (String)requestParameters.getSession().getAttribute("material_file_name");
				if(file_name.contains(".pdf") || file_name.contains(".PDF") 
						|| file_name.contains(".jpg") || file_name.contains(".JPG")
						|| file_name.contains(".png") || file_name.contains(".PNG")
						|| file_name.contains(".gif") || file_name.contains("GIF")
						|| file_name.contains(".doc") || file_name.contains(".DOC")
						|| file_name.contains(".docs") || file_name.contains(".DOCS")
						|| file_name.contains(".docx") || file_name.contains(".DOCX")
						|| file_name.contains(".jpeg") || file_name.contains(".JPEG")
					 ){
					String filePath = uploadFile(eopPath,tempDir,file_name,requestParameters,event,"material_byte_session");
		        	event.setProfilePath(filePath);
				}
		   }
			
			//upload topic
			if(requestParameters.getSession().getAttribute("topic_file_name")!=null){
				file_name = (String)requestParameters.getSession().getAttribute("topic_file_name");
				if(file_name.contains(".pdf") || file_name.contains(".PDF") 
						|| file_name.contains(".jpg") || file_name.contains(".JPG")
						|| file_name.contains(".png") || file_name.contains(".PNG")
						|| file_name.contains(".gif") || file_name.contains("GIF")){
					String filePath = uploadFile(eopPath,tempDir,file_name,requestParameters,event,"topic_byte_session");
		        	event.setTopicPath(filePath);
				}
		   }
		
			event.setModifiedBy(userObj.getStaffLoginId());
			event.setModificationDate(new Date());
			session.update(event);
			
			//upload csv material
			if(requestParameters.getSession().getAttribute("csv_file_name")!=null){
				file_name = (String)requestParameters.getSession().getAttribute("csv_file_name");
				if(file_name.contains(".csv") || file_name.contains(".CSV")){
					String filePath = uploadFile(eopPath,tempDir,file_name,requestParameters,event,"csv_byte_session");
					event.setSpecialGrListingPath(filePath);
					if(file_name.contains(".xlsx") || file_name.contains(".XLSX") || file_name.contains(".xls") || file_name.contains(".XLS") ){
						insertSpecialGroup(session,event.getEvent_code(),eopPath +  File.separator  +filePath,"UPDATE","xls");
					}else{
						insertSpecialGroup(session,event.getEvent_code(),eopPath +  File.separator  +filePath,"UPDATE","csv");
					}
					
				}
				requestParameters.getSession().removeAttribute("csv_file_name");
				requestParameters.getSession().removeAttribute("csv_byte_session");
				
		    }
			if(requestParameters.getSession().getAttribute("material_file_name")!=null){
				file_name = (String)requestParameters.getSession().getAttribute("material_file_name");
				 String field_name = (String)requestParameters.getSession().getAttribute("material_field_name");
				 if(file_name.contains(".pdf") || file_name.contains(".PDF") 
							|| file_name.contains(".jpg") || file_name.contains(".JPG")
							|| file_name.contains(".png") || file_name.contains(".PNG")
							|| file_name.contains(".gif") || file_name.contains("GIF")
							|| file_name.contains(".doc") || file_name.contains(".DOC")
							|| file_name.contains(".docs") || file_name.contains(".DOCS")
							|| file_name.contains(".docx") || file_name.contains(".DOCX")
							|| file_name.contains(".jpeg") || file_name.contains(".JPEG")
						 ){
					storeMaterialToDB(session,event.getEvent_code(),"UPDATE",requestParameters,"material_byte_session",file_name,field_name);
				}
				requestParameters.getSession().removeAttribute("material_file_name");// removing uploaded file details from session after file is uploaded
				requestParameters.getSession().removeAttribute("material_byte_session");
				requestParameters.getSession().removeAttribute("material_field_name");
			  }
			
			if(requestParameters.getSession().getAttribute("topic_file_name")!=null){
				file_name = (String)requestParameters.getSession().getAttribute("topic_file_name");
				 String field_name = (String)requestParameters.getSession().getAttribute("topic_field_name");
				if(file_name.contains(".pdf") || file_name.contains(".PDF") 
						|| file_name.contains(".jpg") || file_name.contains(".JPG")
						|| file_name.contains(".png") || file_name.contains(".PNG")
						|| file_name.contains(".gif") || file_name.contains("GIF")){
					storeMaterialToDB(session,event.getEvent_code(),"UPDATE",requestParameters,"topic_byte_session",file_name,field_name);
				}
				requestParameters.getSession().removeAttribute("topic_file_name");
				requestParameters.getSession().removeAttribute("topic_byte_session");
				requestParameters.getSession().removeAttribute("topic_field_name");
			}
		
			tx.commit();
			status = "Y";
			EmailNotification.sendEopUpdateEmailNotification(event,requestParameters);

            // call iCalendar web service to update the EOP
            iCalendarEOPHelper iCalHelper = new iCalendarEOPHelper();
            iCalHelper.updateEOP(event.getEvent_code(), userObj.getSsoSessionId());
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
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
			 msgObj =  new MsgObject("The Event has been successfully Updated.");
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_UPDATE, AuditTrail.FUNCTION_SUCCESS+" "+event.toString()));
		 }else{
			 msgObj =  new MsgObject("The Event has not been Updated.");
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_UPDATE, AuditTrail.FUNCTION_FAILED+" "+event.toString()));
		 }
		
		 requestParameters.setAttribute("messageObject", msgObj);
		 //requestParameters.getSession().setAttribute("pager", eventSearchListing(requestParameters));
		requestParameters.getSession().removeAttribute("material_file_name");
		requestParameters.getSession().removeAttribute("material_byte_session");
		requestParameters.setAttribute("CacheName", "EOP");
		Pager pager=eventListing(requestParameters);
		requestParameters.setAttribute("pager", pager);
	     requestParameters.getSession().setAttribute("pager",pager);
		return event;
	}
	/**
	  * <p>Method takes responsiblity to delete a event.Store action in audit table.</p>
	  * @param eventCode  Event Code
	  * @param requestParameters Servlet Request Parameter
	  */
	public void deleteEvent(int eventCode,HttpServletRequest requestParameters)
	{
		Session session = null;
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		String status = "N";
		Event event = new Event();
		try{
		session = HibernateFactory.openSession();
		Transaction tx = session.beginTransaction();
		event = (Event)session.get(Event.class,eventCode);
		event.setStatus(false);
		session.update(event);
		tx.commit();
		status = "Y";
		EmailNotification.sendEopDeleteEmailNotification(event,requestParameters);

        // call iCalendar web service to update the EOP
        iCalendarEOPHelper iCalHelper = new iCalendarEOPHelper();
        iCalHelper.deleteEOP(event.getEvent_code(), userObj.getSsoSessionId());
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
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
			 msgObj = new MsgObject("The Event has been successfully Deleted.");
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_DELETE,  AuditTrail.FUNCTION_SUCCESS+" " + event.toString()));
		 }
		 else{
			 msgObj = new MsgObject("The Event has not been Deleted."); 
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_FAILED+" "+event.toString()));
		 }
				
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("CacheName", "EOP");
		Pager pager=eventListing(requestParameters);
		requestParameters.setAttribute("pager", pager);
	     requestParameters.getSession().setAttribute("pager",pager);
	}
	/**
	 * <p>Method takes care for uploading file.</p>
	 * @param eopPath    path where file is stored.
	 * @param tempDir    temporary directory
	 * @param fileName   uploaded file name
	 * @param requestParameters Servlet Request Parameter
	 * @param event     Event Object
	 * @param byteSession      file in byte stored in seesion
	 * @return  path as string
	 */
	public String uploadFile(String eopPath,String tempDir,String fileName,HttpServletRequest requestParameters,Event event,String byteSession){
		log.log(Level.INFO,"---EopMaintenance File upload Started---");
		FileOutputStream stream=null;
		//upload material
		String path = "";
		try {
		String serverFilename = eopPath+"/"+ "EOP_" + event.getToken();
			File uploadedFolder = new File(serverFilename);
			if(!uploadedFolder.exists()){
				uploadedFolder.mkdirs();
			}
			byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute(byteSession);
		
				
						stream = new FileOutputStream(serverFilename+"/"+fileName);
					    stream.write(bytearray);
				
					    path = "EOP_" + event.getToken()+"/"+fileName;
			//delete temp directory
			File temp_file = new File(tempDir+"/" + fileName);
			log.log(Level.INFO,"---EopMaintenance deleting temp folder---");
       	    FileUtils.deleteFileNFolder(temp_file);
       	 requestParameters.setAttribute("CacheName", "EOP");
		} catch(Exception e){log.log(Level.SEVERE, e.getMessage());e.printStackTrace();}
		finally{if(stream!=null)try{stream.close();}catch(Exception e){log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
		}}
       	return path;
	
	}
	
	
	 private boolean checkSpecialGroupAgentId(String eopPath, String tempDir,
				String fileName, HttpServletRequest requestParameters,
				String byteSession) {
			// TODO Auto-generated method stub
		 log.log(Level.INFO,"---EopMaintenance checkSpecialGroupAgentId---");
			String path = "";
			FileOutputStream stream=null;
			String rendomStr=LMSUtil.getRendomToken();
			
			 boolean flag=false;
			try {
				String serverFilename = eopPath+"/"+ "EOP_" + rendomStr;
					File uploadedFolder = new File(serverFilename);
					if(!uploadedFolder.exists()){
						uploadedFolder.mkdirs();
					}
					byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute(byteSession);
				
						
								stream = new FileOutputStream(serverFilename+"/"+fileName);
							    stream.write(bytearray);
						
							    path = "EOP_" +rendomStr+"/"+fileName;
					
							    
					log.log(Level.INFO,"---EopMaintenance deleting temp folder---");
					FileInputStream fileInputStream = null;
					BufferedReader bufferedReader = null;
					String records = null;
					SpecialGroup sg = null;
					fileInputStream = new FileInputStream(new File(eopPath+"/"+path));
			    	bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			    	
			    	if(fileName.contains("xls")){
			    		
		        		 HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		        		 HSSFSheet sheet = workbook.getSheetAt(0);
		        		
		        		 Iterator rows = sheet.rowIterator();
		        		 int cellCount=0;
		        		  while (rows.hasNext()) {
		        			  
		        			  HSSFRow row = (HSSFRow) rows.next();
		                      Iterator cells = row.cellIterator();
		                      while (cells.hasNext()) {
		                    	  HSSFCell cell = (HSSFCell) cells.next();
		                          System.out.println("cell contenct "+cell.toString());
		                          if(cell.toString().trim().length()!=0 && cell.toString()!=null){
		                        	  flag=checkAgentCodeInAamData(cell.toString(),requestParameters);
		                          }else{
		                        	  flag=true;
		                          }
		                          
		                    	 if(flag==false){
		                    		 break;
		                    		 
		                    	 }
		                    	 
		                    	
		                      }
		        			  
		                      if(flag==false){
		                    		 break;
		                    		 
		                    	 }
		        		  }
			    		
			    		
			    	}else{
			    		while ((records = bufferedReader.readLine()) != null) {
			    			 flag=checkAgentCodeInAamData(records,requestParameters);
				    	}
			    	}
					
					
					
				} catch(Exception e){log.log(Level.SEVERE, e.getMessage());e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
				
				}
				finally{if(stream!=null)try{stream.close();}catch(Exception e){log.log(Level.SEVERE, e.getMessage());
					
				}}
		 
		 
			return flag;
		}
	 
	 
	private boolean checkAgentCodeInAamData(String agentCode, HttpServletRequest requestParameters) {
		// TODO Auto-generated method stub
		Session session = null;
		Session session2 = null;
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		 boolean flag=false;
		 try{
		session = HibernateFactory.openSession();
		session2 = clientSessionFactory.openSession();
		ArrayList<Bu> listBu=null;
		ArrayList<District> listDistrict=null;
        ArrayList<Branch> listBranch=null;
        ArrayList<City> listCity=null;
        ArrayList<Ssc> listSsc=null;
        ArrayList<Office> listOffice=null;
        ArrayList<AamData> listAam=null;
        int brCode=0,buCode=0,distCode=0;
        String cityCode=0+"",sscCode=0+"",officeCode=0+"";
        String buName="",distName="", brName="",cName="",sName="",oName="";
        Query query=null;
      if(userObj.getUserType().equals("AD")){
    	  
    	  query = session2.createQuery(" from AamData where  agentCode=:agentCode  ");  
  		  query.setParameter("agentCode", agentCode);
  		  listAam=(ArrayList<AamData>) query.list();
  		  if(listAam.size()>0){
  			  flag=true;
  		  }
  		  
      }else{
    	  
    	  
    	  if(userObj.isBuLevel()){
    		  query = session.createQuery(" from Bu where status = 1 and buCode=:code ");
	  	       query.setParameter("code", userObj.getBuCode());
	  	        listBu=(ArrayList<Bu>) query.list();
	  	        if(listBu.size()>0){
	  	      	  for(Bu br:listBu){
	  	      		  buCode=br.getBuCode();
	  	      		  buName=br.getBuName();
	  	      		  
	  	      		query = session.createQuery(" from District where status = 1 and buCode=:code ");
	    	        query.setParameter("code", buCode);
	    	        listDistrict=(ArrayList<District>) query.list();
	    	        if(listDistrict.size()>0){
	    	      	  for(District dr:listDistrict){
	    	      		  distCode=dr.getDistrictCode();
	    	      		  distName=dr.getDistrictName();
	    	      		  
	    	      		 query = session.createQuery(" from Branch where status = 1 and distCode=:code ");
	    	    	        query.setParameter("code", distCode);
	    	    	        listBranch=(ArrayList<Branch>) query.list();
	    	    	        if(listBranch.size()>0){
	    	    	      	  for(Branch branch:listBranch){
	    	    	      		  brCode=branch.getBranchCode();
	    	    	      		  brName=branch.getBranchName();
	    	    	      		  
	    	    	      		 query = session2.createQuery(" from AamData where "
	    	            				  +" (branch=:branch) and agentCode=:agentCode  "); 
	    	            		  query.setParameter("branch", brName);
	    	            		  query.setParameter("agentCode", agentCode);
	    	            		  listAam=(ArrayList<AamData>) query.list();
	    	            		  if(listAam.size()>0){
	    	            			  flag=true;
	    	            			  break;
	    	            		  }
	    	    	      		
	    	            		  if(flag==true)
	    	            			  break;
	    	    	      		  
	    	    	      	  }	//branch loop end
	    	    	      	  
	    	    	      	 if(flag==true)
   	            			  break;
	    	    	        } // list branch if end
	    	      		  
	    	    	        if(flag==true)
  	            			  break;
	    	      		  
	    	      	  }	// district loop end
	  	      		  
	    	      	 if(flag==true)
           			  break;
	    	        } // district if end 
	  	      		  
	  	      		  
	  	      	  } // loop for bu level
	  	      		  
	  	      }	
  	      	  
    		  
    		  
    	  }// bu level if end
    	  
    	  else if(userObj.isBuLevel() || userObj.isDistrictLevel()){
    	
	    	      		 query = session.createQuery(" from Branch where status = 1 and distCode=:code ");
	    	    	        query.setParameter("code", userObj.getDistrict());
	    	    	        listBranch=(ArrayList<Branch>) query.list();
	    	    	        if(listBranch.size()>0){
	    	    	      	  for(Branch branch:listBranch){
	    	    	      		  brCode=branch.getBranchCode();
	    	    	      		  brName=branch.getBranchName();
	    	    	      		  
	    	    	      		 query = session2.createQuery(" from AamData where "
	    	            				  +" (branch=:branch) and agentCode=:agentCode  "); 
	    	            		  query.setParameter("branch", brName);
	    	            		  query.setParameter("agentCode", agentCode);
	    	            		  listAam=(ArrayList<AamData>) query.list();
	    	            		  if(listAam.size()>0){
	    	            			  flag=true;
	    	            			  break;
	    	            		  }
	    	    	      		  
	    	    	      		  
	    	    	      	  }	//branch loop end
	    	    	      	  
	    	    	      	 
	    	    	        } // list branch if end
	    	      		  
    		  
    	  }// District level if end
    	  
    	  
    	  else if(userObj.isBranchLevel()){
    		  query = session.createQuery(" from Branch where status = 1 and branchCode=:code ");
    	        query.setParameter("code", userObj.getBranchCode());
    	        listBranch=(ArrayList<Branch>) query.list();
    	        if(listBranch.size()>0){
    	      	  for(Branch br:listBranch){
    	      		  brCode=br.getBranchCode();
    	      		  brName=br.getBranchName();
    	      	  }	
    	      	  
    	      	 query = session2.createQuery(" from AamData where "
         				  +" (branch=:branch) and agentCode=:agentCode  "); 
         		  query.setParameter("branch", brName);
         		  query.setParameter("agentCode", agentCode);
         		  listAam=(ArrayList<AamData>) query.list();
         		  if(listAam.size()>0){
         			  flag=true;
         		  }
         		  
    	        }// branch list if end
    		  
      	  }else if(userObj.isBranchLevel() || userObj.isCityLevel()){
      		  
      		query = session.createQuery(" from Branch where status = 1 and branchCode=:code ");
            query.setParameter("code", userObj.getBranchCode());
            listBranch=(ArrayList<Branch>) query.list();
            if(listBranch.size()>0){
          	  for(Branch br:listBranch){
          		  brCode=br.getBranchCode();
          		  brName=br.getBranchName();
          	  }		
          	  
          	  query = session.createQuery(" from City where  cityName=:code ");
                query.setParameter("code", userObj.getCityCode()+"");
                listCity=(ArrayList<City>) query.list();
                if(listCity.size()>0){
              	  for(City city:listCity){
              		  cityCode=city.getCityName();
              		  cName=city.getCityFullName();
              		  
              	  	}
              	  
              	 query = session2.createQuery(" from AamData where "
         				  +"( (branch=:branch)"
         				  +"or (branch=:branch and city=:city))"
         		  		+ " and agentCode=:agentCode  ");  
         		  query.setParameter("branch", brName);
         		  query.setParameter("city", cName);
         		  query.setParameter("agentCode", agentCode);
         		  listAam=(ArrayList<AamData>) query.list();
         		  if(listAam.size()>0){
         			  flag=true;
         		  }
              	  
              	  
                }// City list if end
              
            }// branch list if end
      		  
      		  
      	  }else if(userObj.isBranchLevel() || userObj.isCityLevel() ||  userObj.isSscLevel()){
      		  
      		 query = session.createQuery(" from Branch where status = 1 and branchCode=:code ");
             query.setParameter("code", userObj.getBranchCode());
             listBranch=(ArrayList<Branch>) query.list();
             if(listBranch.size()>0){
           	  for(Branch br:listBranch){
           		  brCode=br.getBranchCode();
           		  brName=br.getBranchName();
           	  }		
           	  
           	  
           	  
           	  query = session.createQuery(" from City where  cityName=:code ");
                 query.setParameter("code", userObj.getCityCode()+"");
                 listCity=(ArrayList<City>) query.list();
                 if(listCity.size()>0){
               	  for(City city:listCity){
               		  cityCode=city.getCityName();
               		  cName=city.getCityName();
               		  
               	  	}
               	  
               	  
               	  query = session.createQuery(" from Ssc where  sscName=:code ");
                     query.setParameter("code", userObj.getSscCode());
                     listSsc=(ArrayList<Ssc>) query.list();
                     if(listSsc.size()>0){
	                   	  for(Ssc ssc:listSsc){
	                   		  sscCode=ssc.getSscName();
	                   		  sName=ssc.getSscName();
	                   	  }
	                   	 
	                   	query = session2.createQuery(" from AamData where "
	              				  +"( (branch=:branch)"
	              				  +"or (branch=:branch and city=:city)"
	              				  +"or (branch=:branch and city=:city and ssc=:ssc))"
	              		  		+ " and agentCode=:agentCode  ");  
	              		  query.setParameter("branch", brName);
	              		  query.setParameter("city", cName);
	              		  query.setParameter("ssc", sName);
	              		  query.setParameter("agentCode", agentCode);
	              		  listAam=(ArrayList<AamData>) query.list();
	              		  if(listAam.size()>0){
	              			  flag=true;
	              		  }
                   	  
                     }// ssc list if end
                     
                 }// City list if end
                 
             }// branch list if end   
      		  
      		  
	  }else if(userObj.isBranchLevel() || userObj.isCityLevel() ||  userObj.isSscLevel() || userObj.isOfficeLevel()){
	      		  
	        query = session.createQuery(" from Branch where status = 1 and branchCode=:code ");
	        query.setParameter("code", userObj.getBranchCode());
	        listBranch=(ArrayList<Branch>) query.list();
	        if(listBranch.size()>0){
	      	  for(Branch br:listBranch){
	      		  brCode=br.getBranchCode();
	      		  brName=br.getBranchName();
	      	  }		
	      	  
	      	  query = session.createQuery(" from City where  cityName=:code ");
	            query.setParameter("code", userObj.getCityCode()+"");
	            listCity=(ArrayList<City>) query.list();
	            if(listCity.size()>0){
	          	  for(City city:listCity){
	          		  cityCode=city.getCityName();
	          		  cName=city.getCityName();
	          		  
	          	  	}
	          	  
	          	  
	          	  query = session.createQuery(" from Ssc where sscName=:code ");
	                query.setParameter("code", userObj.getSscCode()+"");
	                listSsc=(ArrayList<Ssc>) query.list();
	                if(listSsc.size()>0){
	              	  for(Ssc ssc:listSsc){
	              		  sscCode=ssc.getSscName();
	              		  sName=ssc.getSscName();
	              	  }
	              	  
	              	  query = session.createQuery(" from Office where officeCode=:code ");
	                    query.setParameter("code", userObj.getOfficeCode()+"");
	                    listOffice=(ArrayList<Office>) query.list();  
	              	  if(listOffice.size()>0){
	              		  for (Office ofc:listOffice) {
								officeCode=ofc.getOfficeName();
								oName=ofc.getOfficeName();
	              			  
							}
	              		  query = session2.createQuery(" from AamData where "
	              				  +"( (branch=:branch)"
	              				  +"or (branch=:branch and city=:city)"
	              				  +"or (branch=:branch and city=:city and ssc=:ssc)"
	              				  +"or (branch=:branch and city=:city and ssc=:ssc and officeCode=:office))"
	              		  		+ " and agentCode=:agentCode  ");  
	              		  query.setParameter("branch", brName);
	              		  query.setParameter("city", cName);
	              		  query.setParameter("ssc", sName);
	              		  query.setParameter("office", oName);
	              		  query.setParameter("agentCode", agentCode);
	              		  listAam=(ArrayList<AamData>) query.list();
	              		  if(listAam.size()>0){
	              			  flag=true;
	              		  }
	              		  
	              		  
	              	  }// Office IF End
	              	  
	                }//  SSC IF End
	          	  
	          	  
	            }//  City IF End
	      	  
	        }//  Branch IF End
	        
	     }
	}
	}catch(Exception e){
		log.log(Level.SEVERE, e.getMessage());e.printStackTrace();
		LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			 
	}finally {
		session.close();
		session2.close();
	}
        
	return flag;
	}
	/**
	 * <p>Method takes care for inserting special users  agency unit.</p>
	 * @param session  hibernate session object
	 * @param eventCode event code of current Event
	 * @param filePath  file path
	 * @param op  represents from where this method is called(during insert or update)
	 * @param fileExtension 
	 */
	public void insertSpecialGroup(Session session,int eventCode,String filePath,String op, String fileExtension){
		// Retrieve datas from CSV
		FileInputStream fileInputStream = null;
		BufferedReader bufferedReader = null;
		try{
			String records = null;
			SpecialGroup sg = null;
	    	fileInputStream = new FileInputStream(new File(filePath));
	    	bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
	    	
	    	if(op.equals("UPDATE")){
	    		Query deleteQ = session.createQuery("delete SpecialGroup where eventCode = "+eventCode+"");
		    	deleteQ.executeUpdate(); 
	    	}
	    	
	    	if(fileExtension.equals("xls")){
	    		
        		 HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        		 HSSFSheet sheet = workbook.getSheetAt(0);
        		
        		 Iterator rows = sheet.rowIterator();
        		 int cellCount=0;
        		  while (rows.hasNext()) {
        			  
        			  HSSFRow row = (HSSFRow) rows.next();
                      Iterator cells = row.cellIterator();
                      while (cells.hasNext()) {
                    	  HSSFCell cell = (HSSFCell) cells.next();
                          System.out.println("cell contenct "+cell.toString());
                          sg = new SpecialGroup();
      		    			sg.setEventCode(eventCode);
      		    			sg.setAgencyUnit(cell.toString());
      		    			session.save(sg);
                    	  
                    	  
                      }
        			  
        			  
        		  }
	    		
	    		
	    	}else{
	    		while ((records = bufferedReader.readLine()) != null) {
		    		sg = new SpecialGroup();
		    		sg.setEventCode(eventCode);
		    		sg.setAgencyUnit(records);
		    		
		    		session.save(sg);
		    		
		    	}
	    	}
	    	
	    	
	    	log.log(Level.INFO,"---EopMaintenance special user inserted---");
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				if(fileInputStream!=null)fileInputStream.close();
				if(bufferedReader!=null)bufferedReader.close();
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
	}
	/**
	 * <p>Method is used to check  duplicate Schedule.</p>
	 * @param event  Event object
	 * @return  true(if duplicate event found)/false(if duplicate event not found )
	 */
	public boolean checkDuplicateEvent(Event event){
		Session session = null;
		try{
			session = HibernateFactory.openSession();
			Query selectQ = session.createQuery("select  event_code from Event  where eventName =:eventName and eventDate=:eventDate and event_code<>:event_code");
			selectQ.setParameter("eventName", event.getEventName());
			selectQ.setParameter("eventDate", event.getEventDate());
			selectQ.setParameter("event_code", event.getEvent_code());
			List list = selectQ.list();
			if(list!=null && list.size() > 0)
				return true;
			else 
				return false;
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	  }
		return false;
		
	}
	/**
	 * <p>Method is used to save Event material details in db .</p>
	 * @param session  hibernate session object
	 * @param eCode event code of current Event
	 * @param requestParameters 
	 * @param byteSession 
	 * @param file_name 
	 * @param field_name
	 * @param op  represents from where this method is called(during insert or update)
	 */
	public void storeMaterialToDB(Session session,int eCode,String op,HttpServletRequest requestParameters,String byteSession,String file_name,String field_name){
	
		try{
		
	    	if(op.equals("UPDATE")){
	    		Query deleteQ = session.createQuery("delete EventMaterial where eventCode =:eventCode and fieldName =:fieldName");
	    		deleteQ.setParameter("eventCode", eCode);
	    		deleteQ.setParameter("fieldName", field_name);
		    	deleteQ.executeUpdate(); 
	    	}
	   	 EventMaterial eventMat = null;
    	 byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute(byteSession);
	    if(eCode>0){
	    	eventMat = new EventMaterial();
	    	eventMat.setEventCode(eCode);
	    	eventMat.setMaterialName(file_name);
	    	eventMat.setFieldName(field_name);
	    	eventMat.setMaterial(bytearray);
	    	session.save(eventMat);
	    }
	    	log.log(Level.INFO,"---EopMaintenance Upload Material  Done---");
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			
		}
	}
	/**
	 * <p>Method is used to upload  multiple Schedules.</p>
	 * @param event
	 * @param req
	 * @return
	 */
	public Object eopCSVUpload(Event event, HttpServletRequest req) {
		log.log(Level.INFO,"EOPMaintenance --> eopCSVUpload");
		 if(event == null)
	      {
			 event = new Event();
			 return event;
	      }
		LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
//		 ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		  String eopPath  = msgProps.getString("EopPath");

		Map<String,String> configurationMap =(Map<String,String>) req.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		//String eopPath  = configurationMap.get("EopPath");
		  String csv_file_name = "";
		  int record_created = 0;
		  String tempDir = System.getProperty("java.io.tmpdir");
		  if(req.getSession().getAttribute("csv_file_name")!=null){
			  csv_file_name=(String)req.getSession().getAttribute("csv_file_name");
		}
		  MsgObject msgObj=null;
	  	  StringBuffer strbuf=new StringBuffer();
	  	  LMSUtil  lmsUtil = new LMSUtil(); 
	      int sucessCnt=0;
	      int m=0;
      	  int l=0;
      	 int rowCount=0;
	  	  try{
	  		String serverFilename = "resources/upload/Excel"+"/"+"EOP_" + LMSUtil.getRendomToken();

			File uploadedFolder = new File(serverFilename);
			if(!uploadedFolder.exists()){
				uploadedFolder.mkdirs();
			}
           if(csv_file_name!=null && !csv_file_name.equals("")){
				
				byte[] bytearray=(byte[]) req.getSession().getAttribute("csv_byte_session");
				if(csv_file_name.contains(".xlsx") || csv_file_name.contains(".XLSX") ||
						csv_file_name.contains(".xls") || csv_file_name.contains(".XLS")){
					try {
						FileOutputStream stream = new FileOutputStream(serverFilename+"/"+csv_file_name);
					    stream.write(bytearray);
					    stream.close();
					} catch(Exception e){e.printStackTrace();}
				}
				
				File temp_file = new File(tempDir+"/" + csv_file_name);
	        	FileUtils.deleteFileNFolder(temp_file);
	        	
	        	// Retrieve datas from CSV
	        	/*FileInputStream fileInputStream = new FileInputStream(new File(serverFilename+"/"+csv_file_name));
	        	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));*/
	        	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(serverFilename+"/"+csv_file_name), "UTF-8"));
	        	
	        	String records = null;
	        	String interviewDate="",startTime="";
	        	int buCode=0,distCode=0,branchCode=0;
	        	String cityCode=0+"",sscCode=0+"",officeCode=0+"";
	        	ImoUtilityData imoUtilityData = new ImoUtilityData();
	        	String officeName="";
	        	User userObj = (User)req.getSession().getAttribute("currUserObj");
	        	event.setCreationDate(new Date());
	        	event.setCreatedBy(userObj.getStaffLoginId());;
	        	event.setModificationDate(new Date());
	        	event.setModifiedBy(userObj.getStaffLoginId());;
	        	event.setStatus(true);
        		AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
        		ArrayList<Channel> channelList = ImoUtilityData.getActiveChannels(req);
        		boolean flag=false;
        		
        		FileInputStream fis = null;
	       		 fis = new FileInputStream(serverFilename+"/"+csv_file_name);
	       		 HSSFWorkbook workbook = new HSSFWorkbook(fis);
	       		 HSSFSheet sheet = workbook.getSheetAt(0);
	       		
	       		 Iterator rows = sheet.rowIterator();
	       		 int cellCount=0;
	       		 
	       		 while (rows.hasNext()) {
	       			 
	       			 rowCount++;
	                 HSSFRow row = (HSSFRow) rows.next();
	                 Iterator cells = row.cellIterator();

	                 List data = new ArrayList();
	                 
	                 while (cells.hasNext()) {
	                	 if(cellCount==0){
	                		 break;
	                	 }
	                	 
	                	 HSSFCell cell = (HSSFCell) cells.next();
	                     System.out.println("cell contenct "+cell);
	                     
	                     if(cellCount==1){ 
	                    	 if("eop".equals(cell.toString()))
	                    		 event.setEventType(cell.toString());
	                    	 else if("companyevent".equals(cell.toString()))
	                    		 event.setEventType(cell.toString());
	                    	 else if("networking".equals(cell.toString()))
	                    		 event.setEventType(cell.toString());
	                         else if("training".equals(cell.toString()))
	                        	 event.setEventType(cell.toString());
	                         else{
	                        		flag=true;
	     							strbuf.append(localeObj.getTranslatedText("Event Type Value is Wrong")+rowCount);
	     							strbuf.append("\n");
	     							break;
	                         }
     							
	                      }
	                    /* if(cellCount==2){ 
	                    	 event.setTopic(cell.toString());
	                      }*/
	                     if(cellCount==2){ 
	                    	 event.setEventName(cell.toString());
	                    	 event.setTopic(cell.toString());
	                      }
		                   if(cellCount==3){ 
		                    	 if(cell.toString().equals("")){
	     							flag=true;
	     							strbuf.append(localeObj.getTranslatedText("Required DATE at row number")+rowCount);
	     							strbuf.append("\n");
	     							break;
	     						}else{
	     							Date today = new Date();
	     							String toDayStr = LMSUtil.convertDateToString(today);
	     							today = LMSUtil.convertStringToDate(toDayStr);
	     							
	     							 interviewDate = cell.getDateCellValue()+"";
	     		                	 interviewDate=LMSUtil.convertExcelDateToString(interviewDate);
	     		                	 
	     							 Date intDate=LMSUtil.convertExcelDateToDate(interviewDate);
	     								
	     								
	     							if(lmsUtil.validateDDMMYYYY(interviewDate)){
		        							event.setEventDate(intDate);
		        							if(intDate.before(today)){
		        								flag=true;
			        							strbuf.append(localeObj.getTranslatedText("Event Date Should Not Be Before Today at line number")+rowCount);
			        							strbuf.append("\n");
			        							break;
		        							}
		        							/*if(checkDuplicateEvent(event)){
		        								flag=true;
			        							strbuf.append("Schedule already Exists.Duplicate Schedule Not Allowed  at  line Number  "+l);
			        							strbuf.append("\n");
			        							break;
		        							}*/
	     						  }else{
	     							  	flag=true;
		        							strbuf.append(localeObj.getTranslatedText("Date Format Invalid.Should Be DD/MM/YYYY at line number")+l);
		        							strbuf.append("\n");
	     						  }
	     						}
		                     }
		                   
		                   if(cellCount==4){ 
		                	   
		                	    startTime = cell.getDateCellValue()+"";
		                	   event.setStartTime((LMSUtil.converExcelTimetoDateHHMMAMPM1(startTime)));		                	   
		                   }
		                   if(cellCount==5){ 
		                	   
		                	  String endTime = cell.getDateCellValue()+"";
								Date e1=LMSUtil.converExcelTimetoDateHHMMAMPM1(endTime);
								Date s1=LMSUtil.converExcelTimetoDateHHMMAMPM1(startTime);
								if(e1.before(s1) || e1.equals(s1)){
									flag=true;
									strbuf.append(localeObj.getTranslatedText("End Time should be earlier than Start Time at row number")+rowCount);
									break;
								}
								else{
									event.setEndTime((LMSUtil.converExcelTimetoDateHHMMAMPM1(endTime)));
								}	
								
								
		                   }
		                   if(cellCount==6){ 
		                	   event.setEopDescription(cell.toString());
		                   }
		                   
		                   if(cellCount==7){ 
		                	   if(cell.toString().equals("")){
       							flag=true;
       							strbuf.append(localeObj.getTranslatedText("Required LOCATION at row number")+rowCount);
       							strbuf.append("\n");
       							break;
       						}else{
       							event.setLocation(cell.toString());
       						}
		                   }
		                   if(cellCount==8){ 
		                	   if(cell.toString().equals("")){
       							flag=true;
       							strbuf.append(localeObj.getTranslatedText("Required SPEAKER at row number")+rowCount);
       							strbuf.append("\n");
       							break;
       						}else{
       							event.setSpeaker(cell.toString());
       						}
		                	   
		                   }
		                   if(cellCount==9){ 
		                	   int estimate=(int) cell.getNumericCellValue();
		                	   event.setEstimatedCandidates(estimate);
		                	  if(estimate==0){
		                		  event.setEstimatedCandidates(0);
		                	  }
       							
       						
		                   }
		                   if(cellCount==10){ 
		                	   if(cell.toString().equals("")){
       							flag=true;
       							strbuf.append(localeObj.getTranslatedText("Required OPEN TO at row number")+rowCount);
       							strbuf.append("\n");
       							break;
       						}else{
       							
       							if(cell.toString().equalsIgnoreCase("Y") ){
       								event.setOpenTo("Y");
       							}else if(cell.toString().equalsIgnoreCase("N") ){
       								event.setOpenTo("N");
       							}else{
       								flag=true;
           							strbuf.append(localeObj.getTranslatedText("Required Valid (Y/N)  OPEN TO at row number")+rowCount);
           							strbuf.append("\n");
           							break;
       							}
       							
       						        
       						}
		                   }
		                   if(cellCount==11){
		                	   if(cell.toString().equals("")){
       							flag=true;
       							strbuf.append(localeObj.getTranslatedText("Required OPEN TO PUBLIC REGISTRATION at row number")+rowCount);
       							strbuf.append("\n");
       							break;
       						}else{
       							event.setOpenToRegistration(cell.toString());
       						}
		                   }
		                   if(cellCount==12){
		                	   if(cell.toString().equals("")){
       							flag=true;
       							strbuf.append(localeObj.getTranslatedText("Required ORGANIZER at row number")+rowCount);
       							strbuf.append("\n");
       							break;
       						}else{
       							
       							if(!(cell.toString().equalsIgnoreCase("CHO") ||cell.toString().equalsIgnoreCase("BU") || cell.toString().equalsIgnoreCase("District") || cell.toString().equalsIgnoreCase("City") || cell.toString().equalsIgnoreCase("SSC") || cell.toString().equalsIgnoreCase("Agent Team"))){
       								flag=true;
	        							strbuf.append(localeObj.getTranslatedText("ORGANIZER value is incorrect at line number")+rowCount);
	        							strbuf.append("\n");
	        							break;
       							}else{
	        							if(cell.toString().equalsIgnoreCase("CHO"))
	        								event.setOrganizer(1);
	        							else if(cell.toString().equalsIgnoreCase("BU"))
	        								event.setOrganizer(2);
	        							else if(cell.toString().equalsIgnoreCase("District"))
	        								event.setOrganizer(3);
	        							else if(cell.toString().equalsIgnoreCase("Branch"))
	        								event.setOrganizer(7);
	        							else if(cell.toString().equalsIgnoreCase("City"))
	        								event.setOrganizer(4);
	        							else if(cell.toString().equalsIgnoreCase("SSC"))
	        								event.setOrganizer(5);
	        							else if(cell.toString().equalsIgnoreCase("Office"))
	        								event.setOrganizer(8);
	        							else if(cell.toString().equalsIgnoreCase("Agent Team"))
	        								event.setOrganizer(6);
	        						 }
       							}
		                   }
		                   if(cellCount==13){
		                	   if(cell.toString().equals("")){
       							flag=true;
       							strbuf.append(localeObj.getTranslatedText("Required BU at row number")+rowCount);
       							strbuf.append("\n");
       							break;
       						}else{
       							buCode=imoUtilityData.getBuCodeBasedOnBuName(cell.toString());
       							if(buCode==0){
       								flag=true;
	        							strbuf.append(localeObj.getTranslatedText("Invalid BU Name  row number")+rowCount);
	        							strbuf.append("\n");
	        							break;
       							}else{
	        							event.setBuCode(buCode);
	        							event.setBuName(cell.toString());
       							}
       						}
		                   }
		                   if(cellCount==14){
		                	   if(cell.toString().equals("0") || cell.toString().equals("0.0")){
	       							event.setDistrict(0);
	       							event.setDistName(localeObj.getTranslatedText("ALL"));
	       						}else{
	       							distCode=imoUtilityData.getDistrictCodeBasedOnDistrictName(cell.toString());
	       							if(distCode==0){
	       								flag=true;
		        							strbuf.append(localeObj.getTranslatedText("Invalid District Name at row number")+rowCount);
		        							strbuf.append("\n");
		        							break;
	       							}else{
	       								  flag=imoUtilityData.checkLevelOfCode(buCode,distCode,0,"0","0","0");
											  if(flag==true){
												  strbuf.append(localeObj.getTranslatedText("District Name Not available In this Bu  at row number")+rowCount);
				        							break;
											  }
											  
		        							event.setDistrict(distCode);
		        							event.setDistName(cell.toString());
	       							}
	       						 
	       						}
		                   }
		                   
		                   if(cellCount==15){
		                	   //cell.setCellType(1);
		                	   if(cell.toString().equals("0") || cell.toString().equals("0.0")){
	       							event.setBranchCode(0);
	       							event.setBranchName(localeObj.getTranslatedText("ALL"));
	       						}else{
	       							branchCode=imoUtilityData.getBranchCodeBasedOnBranchName(cell.toString());
	       							if(branchCode==0){
	       								flag=true;
		        							strbuf.append(localeObj.getTranslatedText("Invalid Branch Name at row number")+rowCount);
		        							strbuf.append("\n");
		        							break;
	       							}else{
	       								 flag=imoUtilityData.checkLevelOfCode(buCode,distCode,branchCode,"0","0","0");
											  if(flag==true){
												  strbuf.append(localeObj.getTranslatedText("Branch Name Not available In this District at row number")+rowCount);
				        							break;
											  }
		        							event.setBranchCode(branchCode);
		        							event.setBranchName(cell.toString());
	       							}
	       						 
	       						}
		                   }
		                   if(cellCount==16){
		                	   if(cell.toString().equals("0") || cell.toString().equals("0.0")){
	       							event.setCityCode("0");
	       							event.setCityName(localeObj.getTranslatedText("ALL"));
	       						}else{
	       							cityCode=imoUtilityData.getCityCodeBasedOnDistrictName(cell.toString());
	       							if(cityCode.equals("0")){
	       								flag=true;
		        							strbuf.append(localeObj.getTranslatedText("Invalid City Name at row number")+rowCount);
		        							strbuf.append("\n");
		        							break;
	       							}else{
	       								 flag=imoUtilityData.checkLevelOfCode(buCode,distCode,branchCode,cityCode,"0","0");
											  if(flag==true){
												  strbuf.append(localeObj.getTranslatedText("City Name Not available In this Branch  at row number")+rowCount);
				        							break;
											  }
		        							event.setCityCode(cityCode);
		        							event.setCityName(cell.toString());
	       							}
	       						 
	       						}
		                   }
		                   
		                   if(cellCount==17){
		                	   if(cell.toString().equals("0") || cell.toString().equals("0.0")){
	       							event.setSscCode("0");
	       							event.setSscName(localeObj.getTranslatedText("ALL"));
	       						}else{
	       							sscCode=imoUtilityData.getSSCCodeBasedOnSSCName(cell.toString());
	       							if(sscCode.equals("0")){
	       								flag=true;
		        							strbuf.append(localeObj.getTranslatedText("Invalid SSC Name  at row number")+rowCount);
		        							strbuf.append("\n");
		        							break;
	       							}else{
	       								flag=imoUtilityData.checkLevelOfCode(buCode,distCode,branchCode,cityCode,sscCode,"0");
											  if(flag==true){
												  strbuf.append(localeObj.getTranslatedText("Ssc Name Not available In this City at row number")+rowCount);
				        							break;
											  }
		        							event.setSscCode(sscCode);
		        							event.setSscName(cell.toString());
	       							}
	       						 
	       						}
		                   }
		                   if(cellCount==18){
		                	   if(cell.toString().equals("0") || cell.toString().equals("0.0")){
       							event.setOfficeCode("0");
       							event.setOfficeName(localeObj.getTranslatedText("ALL"));
       						}else{
       							
       							officeCode=imoUtilityData.getOfficeCodeBasedOnSSCName(cell.toString());
       							if(officeCode.equals("0")){
       								flag=true;
	        							strbuf.append(localeObj.getTranslatedText("Invalid Office Name  at row number")+rowCount);
	        							strbuf.append("\n");
	        							break;
       							}else{
       								flag=imoUtilityData.checkLevelOfCode(buCode,distCode,branchCode,cityCode,sscCode,officeCode);
										  if(flag==true){
											  strbuf.append(localeObj.getTranslatedText("Office Name Not available In this SSC at row number")+rowCount);
			        							break;
										  }
	        							event.setOfficeCode(officeCode);
	        							event.setOfficeName(cell.toString());
       							}
       						  
       						}
		                   }
		                   if(cellCount==19){
		                	   if(cell.toString().equals("")){
       							flag=true;
       							strbuf.append(localeObj.getTranslatedText("Required AGENT TEAM at row number")+rowCount);
       							strbuf.append("\n");
       							break;
       						}else{
       							event.setAgentTeam(cell.toString());
       						}
		                   }
		                   
		                   cellCount++;
	                	 
	                 }//cell while loop
	                 
	                 if(cellCount==0){
	            		 cellCount++;
	            		
	            	 }else{
	            		 
	            		 if(flag==true){
	            				cellCount=1;
	        					flag=false;
	        					strbuf.append("\n");
	        					continue;
	        				}else{
	        					if(userObj.getUserType().equals("AD")){
	        						record_created = insertEventCsv(event);
	        						sucessCnt++;
	        						cellCount=1;
	    	        			}
	        					else{
	        					if(userObj.isSscLevel()){
	    	        				if(buCode>0 && distCode>0 && !cityCode.equals("0") && !sscCode.equals("0")){
	    	        				record_created = insertEventCsv(event);
	    	        				sucessCnt++;
	    	        				cellCount=1;
	    	        				}
	    	        				
	    	        			}
	        					else if(userObj.isCityLevel()){
	    	        				if(buCode>0 && distCode>0 && !cityCode.equals("0")){
	    	        				record_created = insertEventCsv(event);
	    	        				sucessCnt++;
	    	        				cellCount=1;
	    	        				}
	    	        			}
	        					else if(userObj.isDistrictLevel()){
	    	        				if(buCode>0 && distCode>0){
	    	        				record_created = insertEventCsv(event);
	    	        				sucessCnt++;
	    	        				cellCount=1;
	    	        				}
	    	        			}
	        					else if(userObj.isBuLevel()){
	    	        				if(buCode>0){
	    	        				record_created = insertEventCsv(event);
	    	        				sucessCnt++;
	    	        				cellCount=1;
	    	        				}
	    	        			}
	    	        		}
	        					if(record_created>0){
	    	        			    auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"",AuditTrail.MODULE_EOP,AuditTrail.FUNCTION_CREATE,event.toString()));
	    	        			}
	        				}
	        				flag=false;
	            		 
	            		 
	            		 
	            	 }
	       			 
	       			 
	       		 }//end while loop
       		
        		
           }
           if(strbuf.length()>0){
				ImoUtilityData imoutill=new ImoUtilityData();
				imoutill.summaryReport(strbuf,req);
			}
	  	  }catch(Exception e){
	  		log.log(Level.SEVERE, e.getMessage());
	  		LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
	  	  }
	  	  int failedCnt = rowCount - sucessCnt;
		  req.getSession().removeAttribute("csv_file_name"); 
		  req.getSession().setAttribute("strbuf", strbuf);
		  req.getSession().setAttribute("formObj",new PathDetail().getFormObj("EopUploadCSV"));
		  req.setAttribute("CacheName", "EOP");
		
			//if(sucessCnt!=0){ 
		  String uploadSucessString=localeObj.getTranslatedText("Number of records uploaded successfully");
		  String recoredFailString=localeObj.getTranslatedText("Number of records fail");
				return new ErrorObject(uploadSucessString+" :"+sucessCnt+" <br> "+recoredFailString+" :"+(failedCnt-2)+"  ", "",localeObj);
	 		//}else{
	 			//return new ErrorObject("The new schedule csv file uploaded Successfully", "");
	 		//}
	}public int insertEventCsv(Event event){
		Integer key = 0;
		Session session = null;
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			event.setToken(LMSUtil.getRendomToken());
			key = (Integer)session.save(event);
			tx.commit();
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
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
	  * <p>Method is used to get all EOP for Rest service</p>
	 * @param context 
	  * @return  ArrayList of EOP
	  */
	public ArrayList getAllEopRest(AamData aamData,String agentId,String candidateCode, ServletContext context) {
		// TODO Auto-generated method stub
		log.log(Level.SEVERE,"EopMaintenance -->  getAllEopRest ");
		/*ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
        String appUrl=msgProps.getString("APP_URL");*/
        Map<String, String> map =(Map<String, String>) context.getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
        String appUrl=map.get("APP_URL");
        
		Session session = null;
		ArrayList<Event> eopList = new ArrayList<Event>();
		ArrayList<Event> eopList2 = new ArrayList<Event>();
		 List<EventCandidate> list1;
		 ArrayList eventListAddAll = new ArrayList();
		Event event;
		try{
			
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Date sdate=new Date();
			log.log(Level.SEVERE,"Start Time "+sdate.getTime());
			
			Criteria crit ;
			Date now=new Date();
			
           /* Query query=session.createQuery("FROM Event where status = 1 and eventDate >= :eventDate and "
                    + "( (buCode=:bucode  and district=0) "
                    + "or (district=:distcode and  branchCode=0)"
                    + "or (branchCode=:branchCode and  cityCode=0)"
                    + "or (cityCode=:citycode and sscCode=0)"
                    + "or (sscCode=:ssccode and officeCode = 0)"
                    + "or (officeCode =:officeCode )  )");*/

			
			
			
			
			
			
			ImoUtilityData utilityData=new ImoUtilityData();
			String branchName=utilityData.getBranchNameBaseonCode(aamData.getBranchCode());
			ArrayList<Branch> branchlist= utilityData.getBranchCodeListBasedOnBranchName(branchName);
			
			if(branchlist.size()>0){

				for(Branch bracnh:branchlist){
					
					
					District dist=utilityData.getDistrictCodeBucode(bracnh.getDistCode());
					aamData.setBranchCode(bracnh.getBranchCode());
					aamData.setDistrictCode(dist.getDistrictCode());
					aamData.setBuCode(dist.getBuCode());			
					
					
					System.out.println("bu "+aamData.getBuCode());
					System.out.println("dist "+aamData.getDistrictCode());
					System.out.println("branch "+aamData.getBranchCode());
			
			System.out.println(""+now);
			System.out.println(""+LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));

			Query query=session.createQuery("select distinct e FROM Event e,SpecialGroup s where status = 1 and  e.openTo='SG'"
					+ "  AND (e.eventDate > :eventDate OR ( e.eventDate = :eventDate AND e.startTime > :startTime ))"
					+ "and ( (e.buCode=:bucode  and e.district=0) "
					+ "or (e.buCode=:bucode  and e.district=:distcode and  e.branchCode=0)"
					+ "or (e.buCode=:bucode  and e.district=:distcode and e.branchCode=:branchCode and  e.cityCode='0')"
					+ "or (e.buCode=:bucode  and e.district=:distcode and e.branchCode=:branchCode and e.cityCode=:citycode and e.sscCode='0')"
					+ "or (e.buCode=:bucode  and e.district=:distcode and e.branchCode=:branchCode and e.cityCode=:citycode and e.sscCode=:ssccode and e.officeCode = '0')"
					+ "or (e.buCode=:bucode  and e.district=:distcode and e.branchCode=:branchCode and e.cityCode=:citycode and e.sscCode=:ssccode and e.officeCode =:officeCode )  ) "
					+ "and s.eventCode=e.event_code and s.agencyUnit=:leadcode) ");
			
			
			query.setParameter("startTime", LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));
			query.setParameter("eventDate", sdf1.parse(sdf1.format(now)));
			query.setParameter("bucode",aamData.getBuCode());
			query.setParameter("distcode",aamData.getDistrictCode());
			query.setParameter("branchCode",aamData.getBranchCode());
			query.setParameter("citycode", aamData.getCity());
			query.setParameter("ssccode", aamData.getSsc());
			query.setParameter("officeCode", aamData.getOfficeCode());
			query.setParameter("leadcode", aamData.getAgentCode());
			
			eopList=(ArrayList<Event>) query.setCacheable(true).list();
			
			
				  for(Iterator iterator=eopList.iterator();iterator.hasNext(); ){
				    event = (Event) iterator.next();
				    if("Y".equalsIgnoreCase(event.getOpenToRegistration()))
				    	event.setPublicUrl(appUrl + "FormManager?key=EopCandidateReg&type=NEW&eventCode="+event.getEvent_code()+"&agentID=");
				    else
				    	event.setPublicUrl(null);
					if(!"".equals(candidateCode)){
				    crit = session.createCriteria(EventCandidate.class);
				    crit.add(Restrictions.eq("eventCode", event.getEvent_code()));
				    crit.add(Restrictions.eq("eventCandidateCode", candidateCode));
				    crit.add(Restrictions.eq("status", true));
				    list1=(ArrayList<EventCandidate>) crit.list();
				    if(!list1.isEmpty()){
				     event.setIsRegistered(true);
				    }else{
				     event.setIsRegistered(false);
				    }

				   }
				   }
				  
				  
				  query=session.createQuery("FROM Event where status = 1 and openTo!='SG'"
						  + " AND (eventDate > :eventDate OR (eventDate = :eventDate AND startTime > :startTime ))"
		                    + " AND ( (buCode=:bucode  and district=0) "
		                    + "or (buCode=:bucode and district=:distcode and  branchCode=0)"
		                    + "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and  cityCode='0')"
		                    + "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode='0')"
		                    + "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode=:ssccode and officeCode = '0')"
		                    + "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode=:ssccode and officeCode =:officeCode )  )");
					
					
					
					System.out.println(""+now);

					query.setParameter("startTime", LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));
					query.setParameter("eventDate", sdf1.parse(sdf1.format(now)));
					query.setParameter("bucode",aamData.getBuCode());
					query.setParameter("distcode",aamData.getDistrictCode());
					query.setParameter("branchCode",aamData.getBranchCode());
					query.setParameter("citycode", aamData.getCity());
					query.setParameter("ssccode", aamData.getSsc());
					query.setParameter("officeCode", aamData.getOfficeCode());
					
					eopList2=(ArrayList<Event>) query.setCacheable(true).list();
					
					
						  for(Iterator iterator=eopList2.iterator();iterator.hasNext(); ){
						    event = (Event) iterator.next();
						    if("Y".equalsIgnoreCase(event.getOpenToRegistration()))
						    	event.setPublicUrl(appUrl + "FormManager?key=EopCandidateReg&type=NEW&eventCode="+event.getEvent_code()+"&agentID=");
						    else
						    	event.setPublicUrl(null);
							if(!"".equals(candidateCode)){
						    crit = session.createCriteria(EventCandidate.class);
						    crit.add(Restrictions.eq("eventCode", event.getEvent_code()));
						    crit.add(Restrictions.eq("eventCandidateCode", candidateCode));
						    crit.add(Restrictions.eq("status", true));
						    list1=(ArrayList<EventCandidate>) crit.list();
						    if(!list1.isEmpty()){
						     event.setIsRegistered(true);
						    }else{
						     event.setIsRegistered(false);
						    }

						   }
						   }
				  
				  eopList.addAll(eopList2);
				  
				  eventListAddAll.addAll(eopList);
				}// branch foor loop
				
				
				
			}else{
				
				Query query=session.createQuery("select distinct e FROM Event e,SpecialGroup s where status = 1 and  e.openTo='SG'"
						+ "  AND (e.eventDate > :eventDate OR ( e.eventDate = :eventDate AND e.startTime > :startTime ))"
						+ "and ( (e.buCode=:bucode  and e.district=0) "
						+ "or (e.buCode=:bucode  and e.district=:distcode and  e.branchCode=0)"
						+ "or (e.buCode=:bucode  and e.district=:distcode and e.branchCode=:branchCode and  e.cityCode='0')"
						+ "or (e.buCode=:bucode  and e.district=:distcode and e.branchCode=:branchCode and e.cityCode=:citycode and e.sscCode='0')"
						+ "or (e.buCode=:bucode  and e.district=:distcode and e.branchCode=:branchCode and e.cityCode=:citycode and e.sscCode=:ssccode and e.officeCode = '0')"
						+ "or (e.buCode=:bucode  and e.district=:distcode and e.branchCode=:branchCode and e.cityCode=:citycode and e.sscCode=:ssccode and e.officeCode =:officeCode )  ) "
						+ "and s.eventCode=e.event_code and s.agencyUnit=:leadcode) ");
				
				System.out.println(""+now);
				System.out.println(""+LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));

				query.setParameter("startTime", LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));
				query.setParameter("eventDate", sdf1.parse(sdf1.format(now)));
				query.setParameter("bucode",aamData.getBuCode());
				query.setParameter("distcode",aamData.getDistrictCode());
				query.setParameter("branchCode",aamData.getBranchCode());
				query.setParameter("citycode", aamData.getCity());
				query.setParameter("ssccode", aamData.getSsc());
				query.setParameter("officeCode", aamData.getOfficeCode());
				query.setParameter("leadcode", aamData.getAgentCode());
				
				eopList=(ArrayList<Event>) query.setCacheable(true).list();
				
				
					  for(Iterator iterator=eopList.iterator();iterator.hasNext(); ){
					    event = (Event) iterator.next();
					    if("Y".equalsIgnoreCase(event.getOpenToRegistration()))
					    	event.setPublicUrl(appUrl + "FormManager?key=EopCandidateReg&type=NEW&eventCode="+event.getEvent_code()+"&agentID=");
					    else
					    	event.setPublicUrl(null);
						if(!"".equals(candidateCode)){
					    crit = session.createCriteria(EventCandidate.class);
					    crit.add(Restrictions.eq("eventCode", event.getEvent_code()));
					    crit.add(Restrictions.eq("eventCandidateCode", candidateCode));
					    crit.add(Restrictions.eq("status", true));
					    list1=(ArrayList<EventCandidate>) crit.list();
					    if(!list1.isEmpty()){
					     event.setIsRegistered(true);
					    }else{
					     event.setIsRegistered(false);
					    }

					   }
					   }
					  
					  
					  query=session.createQuery("FROM Event where status = 1 and openTo!='SG'"
							  + " AND (eventDate > :eventDate OR (eventDate = :eventDate AND startTime > :startTime ))"
			                    + " AND ( (buCode=:bucode  and district=0) "
			                    + "or (buCode=:bucode and district=:distcode and  branchCode=0)"
			                    + "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and  cityCode='0')"
			                    + "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode='0')"
			                    + "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode=:ssccode and officeCode = '0')"
			                    + "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode=:ssccode and officeCode =:officeCode )  )");
						
						
						
						System.out.println(""+now);

						query.setParameter("startTime", LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));
						query.setParameter("eventDate", sdf1.parse(sdf1.format(now)));
						query.setParameter("bucode",aamData.getBuCode());
						query.setParameter("distcode",aamData.getDistrictCode());
						query.setParameter("branchCode",aamData.getBranchCode());
						query.setParameter("citycode", aamData.getCity());
						query.setParameter("ssccode", aamData.getSsc());
						query.setParameter("officeCode", aamData.getOfficeCode());
						
						eopList2=(ArrayList<Event>) query.setCacheable(true).list();
						
						
							  for(Iterator iterator=eopList2.iterator();iterator.hasNext(); ){
							    event = (Event) iterator.next();
							    if("Y".equalsIgnoreCase(event.getOpenToRegistration()))
							    	event.setPublicUrl(appUrl + "FormManager?key=EopCandidateReg&type=NEW&eventCode="+event.getEvent_code()+"&agentID=");
							    else
							    	event.setPublicUrl(null);
								if(!"".equals(candidateCode)){
							    crit = session.createCriteria(EventCandidate.class);
							    crit.add(Restrictions.eq("eventCode", event.getEvent_code()));
							    crit.add(Restrictions.eq("eventCandidateCode", candidateCode));
							    crit.add(Restrictions.eq("status", true));
							    list1=(ArrayList<EventCandidate>) crit.list();
							    if(!list1.isEmpty()){
							     event.setIsRegistered(true);
							    }else{
							     event.setIsRegistered(false);
							    }

							   }
							   }
					  
					  eopList.addAll(eopList2);
					  eventListAddAll.addAll(eopList);
				
				
			}
			Date edate=new Date();
			log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return eventListAddAll;
	
	
	} 
	
	/**
	  * <p>Method is used to get all Deleted EOP for Rest service</p>
	  * @return  ArrayList of EOP
	  */
	public ArrayList getAllDeletedEopRest() {
		// TODO Auto-generated method stub
		log.log(Level.SEVERE,"EopMaintenance -->  getAllDeletedEopRest ");
		Session session = null;
		ArrayList eopList = new ArrayList();
		try{
			
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Date sdate=new Date();
			log.log(Level.SEVERE,"Start Time "+sdate.getTime());
			
			Query query=session.createQuery("SELECT event_code FROM Event where status = 0 ");
			eopList=(ArrayList<Event>) query.setCacheable(true).list();
			
			Date edate=new Date();
			log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return eopList;
	
	
	} 
	
	/**
	  * <p>Method is used to get all EOP for Rest service</p>
	  * @return  ArrayList of EOP
	  */
	public ArrayList getAllEopRestPast(String agentId, String candidateCode, ServletContext context) {
		// TODO Auto-generated method stub
		log.log(Level.SEVERE,"EopMaintenance -->  getAllEopRestPast ");
		
		Session session = null;
		ArrayList<Event> eopList = new ArrayList<Event>();
		try{
			
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Date sdate=new Date();
			log.log(Level.SEVERE,"Start Time "+sdate.getTime());
			
			Date now=new Date();

			Query query=session.createQuery("select distinct e FROM Event e,EventCandidate ec where e.status = 1 "
					+ "  AND (e.eventDate < :eventDate OR ( e.eventDate = :eventDate AND e.startTime <= :startTime ))"
					+ "  AND ec.status = 1 AND ec.eventCode=e.event_code AND ec.eventCandidateCode = :candidateCode ");
			
			System.out.println(""+now);
			System.out.println(""+LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));
		
			query.setParameter("startTime", LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));
			query.setParameter("eventDate", sdf1.parse(sdf1.format(now)));
			query.setParameter("candidateCode", candidateCode);
			
			eopList=(ArrayList<Event>) query.setCacheable(true).list();
			
			Date edate=new Date();
			log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return eopList;
	
	
	} 
	
	
	/**
	  * <p>Method is used to get all EOP for particular Branch for Rest service</p>
	  * @return  ArrayList of EOP
	  */
	public ArrayList getAllEopBranchRest(String branchName) {
		// TODO Auto-generated method stub
		log.log(Level.SEVERE,"EopMaintenance -->  getAllEopBranchRest ");
		Session session = null;
		ArrayList eopList = new ArrayList();
		ArrayList<Event> list = new ArrayList<Event>();
		List<Branch> branchList=new ArrayList<Branch>();
		List<District> distList=new ArrayList<District>();
		Event event;
		Branch branch = new Branch();
		District dist=new District();
		try{
			
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Date sdate=new Date();
			log.log(Level.SEVERE,"Start Time "+sdate.getTime());
			
			Criteria crit = session.createCriteria(Branch.class);
			crit.add(Restrictions.eq("branchName", branchName));
			crit.add(Restrictions.eq("status", true));
			branchList=(ArrayList<Branch>) crit.list();
			if(!branchList.isEmpty() && 0 < branchList.size()){
				branch = branchList.get(0);
				crit = session.createCriteria(District.class);
				crit.add(Restrictions.eq("districtCode", branch.getDistCode()));
				crit.add(Restrictions.eq("status", true));
				distList=(ArrayList<District>) crit.list();
				dist=distList.get(0);
			}
			
			Query query=session.createQuery("FROM Event where status = 1  and "
					
					+ "( (buCode=:bucode  and district=0) "
					+ "or (district=:distcode and  branchCode=0)"
					+ "or (branchCode =:branchCode)"
					+ " )");
			
			query.setParameter("bucode",dist.getBuCode());
			query.setParameter("distcode",dist.getDistrictCode());
			query.setParameter("branchCode",branch.getBranchCode());
			list=(ArrayList<Event>) query.setCacheable(true).list();
			eopList.addAll(list);

			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
		}
		
		return eopList;
	
	} 
	 /**
	  * <p>Method is used to get email address of candidates registered for particular event </p>
	  * @param  code  eventCode 
	  * @return  ArrayList of email address
	  */
	public List getRegisteredEmailAddressForParticularEvent(int code){
		log.log(Level.INFO, "EopMaintenance -->getRegisteredEmailAddressForParticularEvent ");
		Session session = null;
		List  emailAddrs = null;
		try{
			session = HibernateFactory.openSession();
			Query selectQ = session.createQuery("select  eMailId, addressCode, agentId, name, gender, qrCode, co  from  AddressBook where addressCode in(select  eventCandidateCode from EventCandidate where status=1 and eventCode=:eventCode)");
			selectQ.setParameter("eventCode", code);
			emailAddrs =(ArrayList<String>) selectQ.list();
			
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	  }
		return emailAddrs;
		
	}
	 /**
	  * <p>Method is used to get Event material based on event code </p>
	  * @param  eventCode 
	  * @return  EventMaterial object
	  */
	public EventMaterial getEventMaterial(int eventCode,String materialName)
	{
		log.log(Level.INFO,"EopMaintenance ---> getEventMaterial ");
		 Session session = null;
		 EventMaterial mat = null;
		try{
		session = HibernateFactory.openSession();
		session.setDefaultReadOnly(true);
		Query selectQ=null;
		if(materialName!=null){
			if(materialName.equalsIgnoreCase("Profile")){
				selectQ = session.createQuery("select materialName,material from EventMaterial where eventCode = "+eventCode+" and fieldName = 'ProfileMaterial'");
			}
		}else{
			selectQ = session.createQuery("select materialName,material from EventMaterial where eventCode = "+eventCode+" and fieldName = 'TopicFile'");
		}
		 
		List list = selectQ.list();
		
		if(list!=null && list.size() > 0){
				mat = new EventMaterial();
			   Object [] objectDoc = (Object []) list.get(0);
			   mat.setMaterialName(( String)objectDoc[0]);
			   mat.setMaterial( ( byte[] )objectDoc[1]);
		}else{log.log(Level.INFO,"EopMaintenance ---> getEventMaterial : No Material Found ");}
	
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(true);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
			return mat;
	}
	
	public   String  getProfileFile(Event event,HttpServletRequest req)
	{
		log.log(Level.INFO,"EopMaintenance ---> getProfileFile ");
		 Session session = null;
		EventMaterial material=null;
		String path="#";
		try{
			session = HibernateFactory.openSession();
			 Criteria criteria = session.createCriteria(EventMaterial.class);
			 criteria.add(Restrictions.eq("eventCode", event.getEvent_code()));
			 ArrayList list = (ArrayList) criteria.list();
			
			 if(list.size()>0){
				 material=(EventMaterial)list.get(0);
				 String fname = event.getProfilePath().substring(event.getProfilePath().lastIndexOf('/') + 1);
				 path=req.getRealPath(File.separator)+ "resources" + File.separator+ "material"+File.separator+fname;
				 File file=new File(path);
				 if(!file.exists()){
					 file.createNewFile();
				 }
				 
				 FileOutputStream stream = new FileOutputStream(path);
				 stream.write(material.getMaterial());
				 stream.close();
				 path="resources" + File.separator+ "material"+File.separator+fname;
			 }
			 
			 
		
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
			return path;
	}
	public   String  getTopicFile(Event event,HttpServletRequest req)
	{
		log.log(Level.INFO,"EopMaintenance ---> getTopicFile ");
		 Session session = null;
		EventMaterial material=null;
		String path="#";
		try{
			session = HibernateFactory.openSession();
			 Criteria criteria = session.createCriteria(EventMaterial.class);
			 criteria.add(Restrictions.eq("eventCode", event.getEvent_code()));
			 ArrayList list = (ArrayList) criteria.list();
			
			 if(list.size()>0){
				 if(list.size()==2)
					 material=(EventMaterial)list.get(1);
				 else
					 material=(EventMaterial)list.get(0);	 
				 String fname = event.getTopicPath().substring(event.getTopicPath().lastIndexOf('/') + 1);
				 path=req.getRealPath(File.separator)+ "resources" + File.separator+ "material"+File.separator+fname;
				 File file=new File(path);
				 if(!file.exists()){
					 file.createNewFile();
				 }
				 
				 FileOutputStream stream = new FileOutputStream(path);
				 stream.write(material.getMaterial());
				 stream.close();
				 path="resources" + File.separator+ "material"+File.separator+fname;
			 }
			 
			 
		
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
			return path;
	}
	
	
	public ArrayList<EventMaterial> getMaterialForMail(int eventCode)
	{
		log.log(Level.INFO,"EopMaintenance ---> getEventMaterial ");
		 Session session = null;
		 ArrayList<EventMaterial> list=null;
		try{
		session = HibernateFactory.openSession();
		Query selectQ=null;
		
		selectQ = session.createQuery(" from EventMaterial where eventCode = "+eventCode+" ");
		list=(ArrayList<EventMaterial>) selectQ.list();
		 if(list.size()>0){
			 return list;
		 }else{
			 return null;
		 }
		
	
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
			return null;
	}
	 /**
	  * <p>Method used to check public registration open or not.</p>
	  * @param eventCode   Event Code
	  * @return boolean
	  */

	public boolean checkIfPublicRegistrationOpen(int eventCode)
	{
		 Session session = null;;
		 String openToReg = "N";
		try{
			session = HibernateFactory.openSession();
			Query query= session.createQuery("select openToRegistration from  Event where event_code=:eventCode and status=:status");
			query.setParameter("eventCode", eventCode);
			query.setParameter("status", 1);
		    List l = query.list();
		    if(l!=null && l.size() > 0){
		    	openToReg = (String)l.get(0);
		    }
		    if("Y".equalsIgnoreCase(openToReg))
		    	return true;
		    
		  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		
		return false;
	}
	
	public int getBranchNameBaseonCode(int eventCode) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"eventMaintenance ---> getBranchCode ");
		 Session session = null;
		 int code=0;
			try{
				session = HibernateFactory.openSession();
				Event event = (Event) session.get(Event.class,eventCode) ;
				if(event!=null){
					 code=event.getBranchCode();
				}
				
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
			}
			
			

		return code;
	}
	
	
	public static final String EVENT_NAME_PARAM = "eventName";
	public static final String EVENT_TYPE_PARAM = "eventType";
	public static final String TOPIC_PARAM = "topic";
	public static final String DESCRIPTION_PARAM = "description";
	public static final String DATE_PARAM = "eventDate";
	public static final String SPEAKER_PARAM = "speaker";
	public static final String START_HOUR_PARAM = "startHour";
	public static final String START_MINUTE_PARAM = "startMinute";
	public static final String START_MERIDIEM_PARAM = "startMeridiem"; 
	public static final String END_HOUR_PARAM = "endHour";
	public static final String END_MINUTE_PARAM = "endMinute";
	public static final String END_MERIDIEM_PARAM = "endMeridiem"; 
	public static final String LOCATION_PARAM = "location";
	public static final String BU_PARAM = "bu";
	public static final String DISTRICT_PARAM = "district";
	public static final String CITY_PARAM = "city";
	public static final String SSC_PARAM = "ssc";
	public static final String AGENT_TEAM_PARAM = "agentTeam";
	public static final String MONTH_PARAM = "month";
	public static final String YEAR_PARAM = "year";
	public static final String ESTD_PARAM = "estCand";
	public static final String OPENTO_PARAM = "openTo";
	public static final String PUBREG_PARAM = "publicRegistration";
	public static final String ORGANISER_PARAM = "organizer";
	public static final String CREATEDBY_PARAM = "createdBy";
	 public static final KeyObjPair MONTH_VALUE_PAIR[] = {
	        new KeyObjPair("01", "January"), new KeyObjPair("02", "February"), new KeyObjPair("03", "March"), new KeyObjPair("04", "April"), new KeyObjPair("05", "May"), new KeyObjPair("06", "June"), new KeyObjPair("07", "July"), new KeyObjPair("08", "August"), new KeyObjPair("09", "September"), new KeyObjPair("10", "October"), 
	        new KeyObjPair("11", "November"), new KeyObjPair("12", "December")
	    };
	 public static final String HOUR_VALUE_PAIR[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};  
	 public static final String MINUTE_VALUE_PAIR[] = {"00","15","30","45"};
	 
	 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	public EventCandidate getEopCandidateCode(int event_code) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"eventMaintenance ---> getInterviewCandidateCode ");
		 Session session = null;
		 EventCandidate candidate=null;
		
			try{
				session = HibernateFactory.openSession();
				 Criteria criteria = session.createCriteria(EventCandidate.class);
				 criteria.add(Restrictions.eq("eventCode", event_code));
				 ArrayList list = (ArrayList) criteria.list();
				
				 if(list.size()>0){
					candidate=(EventCandidate) list.get(0);
				 }
				 
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
			}
			
			

		return candidate;
	}
	
	public static final SessionFactory clientSessionFactory = new Configuration().configure("client_hibernate.cfg.xml").buildSessionFactory();
	
	//* ------------------------------------------------------------------------
    public void resyncEvent(int eventCode, HttpServletRequest requestParameters)
    {
        Session session = null;
        User userObj = (User) requestParameters.getSession().getAttribute("currUserObj");

        // get the event object to get the status
        boolean success = false;
        try
        {
            session = HibernateFactory.openSession();
            Event eop = (Event) session.load(Event.class, eventCode);

            if (eop != null)
            {
                if (eop.isStatus() == true)
                {
                    iCalendarEOPHelper helper = new iCalendarEOPHelper();
                    success = helper.updateEOP(eventCode, userObj.getSsoSessionId());
                }
                else
                {
                    iCalendarEOPHelper helper = new iCalendarEOPHelper();
                    success = helper.deleteEOP(eventCode, userObj.getSsoSessionId());
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }

        MsgObject msgObj = null;
        if (success)
        {
            msgObj = new MsgObject("Event Resynchronized to iCalendar");
        }
        else
        {
            msgObj = new MsgObject("Event Resynchronization Failed");
        }

        requestParameters.setAttribute("messageObject", msgObj);
        requestParameters.setAttribute("CacheName", "EOP");
        Pager pager = eventListing(requestParameters);
        requestParameters.setAttribute("pager", pager);
        requestParameters.getSession().setAttribute("pager", pager);
    }
	
}
