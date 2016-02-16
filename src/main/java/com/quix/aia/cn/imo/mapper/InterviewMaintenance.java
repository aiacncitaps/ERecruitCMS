/******************************************************************************
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
* 12-May-2015       Jinatmayee         DB Operation Code
* 18-May-2015       Jinatmayee         Search,Edit,Delete Operation Code & Audit Trail Added
* 20-May-2015       Jinatmayee         CSV File Upload code Added
* 28-May-2015       Jinatmayee         Topic Upload functionality  Added
* 08-Jun-2015       Jinatmayee         Comments Added          
* 14-August-2015    Maunish            Modified 
* 20-August-2015    Nibedita           Email sent  to registered candidates when an interview  updated
* 02-Sept-2015         Nibedita           file uploaded to db
* 03-Sept-2015         Nibedita           Csv download for registered candidate of 3rd interview
******************************************************************************/
package com.quix.aia.cn.imo.mapper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;

import com.archerlogic.aia.cn.helpers.iCalendar.iCalendarInterviewHelper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.quix.aia.cn.imo.constants.ApplicationAttribute;
import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.addressbook.CandidateESignature;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.branch.Branch;
import com.quix.aia.cn.imo.data.bu.Bu;
import com.quix.aia.cn.imo.data.city.City;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.district.District;
import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.interview.Interview;
import com.quix.aia.cn.imo.data.interview.InterviewCandidate;
import com.quix.aia.cn.imo.data.interview.InterviewCandidateMaterial;
import com.quix.aia.cn.imo.data.interview.InterviewMaterial;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
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
 * <p>This class defines the data operations & csv file upload </p>
 * @author Jinatmayee
 * @version 1.0
 */
public class InterviewMaintenance {
    static Logger log = Logger.getLogger(InterviewMaintenance.class.getName());
    
	public InterviewMaintenance()
	{
		HibernateFactory.buildIfNeeded();
	}
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * <p>This method checks all validations & sets values to bean</p>
	 * @param interview Interview Object
	 * @param requestParameters Servlet Request Parameter
	 * @return Interview Object/Error object 
	 */
	public  Object mapForm1(Interview interview, HttpServletRequest requestParameters)
    {
       log.log(Level.INFO,"InterviewMaintenance --> setting values ");
       LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if(interview == null)
        {
			interview = new Interview();
            return interview;
        }
		if(requestParameters.getParameter("interviewName") == null || requestParameters.getParameter("interviewName").equals("") )
				return new ErrorObject("Interview Session Name", " field is required",localeObj);
		
		 if(requestParameters.getParameter("interviewDate") == null || requestParameters.getParameter("interviewDate").equals(""))
			 	return new ErrorObject("Interview Date", " field is required",localeObj);
		
		 if(requestParameters.getParameter("location") == null || requestParameters.getParameter("location").equals(""))
			   return new ErrorObject("Location", " field is required",localeObj);
		 
		 if(requestParameters.getParameter("interviewMaterial") == null || requestParameters.getParameter("interviewMaterial").equals(""))
			  return new ErrorObject("Interview Material", " field is required",localeObj);
		 
		
		if(requestParameters.getParameter("bu").equals("0"))
				return new ErrorObject("BU", " field is required",localeObj);
		if(requestParameters.getParameter("estCand")!=null && requestParameters.getParameter("estCand").length() > 0){
			if(!LMSUtil.validInt(requestParameters.getParameter("estCand"))){
				return new ErrorObject("", "Estimated Candidates Should be a Number",localeObj);
			}else{
				if(Integer.parseInt(requestParameters.getParameter("estCand"))<=0){
					return new ErrorObject("", "Estimated Candidates Should be more then 0",localeObj);
				}else{
					interview.setEstimatedCondidates(Integer.parseInt(requestParameters.getParameter("estCand").trim()));
				}
			}
		}
		interview.setInterviewType(requestParameters.getParameter("interviewType").trim());
		interview.setInterviewSessionName(requestParameters.getParameter("interviewName").trim());
		/*if(checkDuplicateInterview(interview))
			 return new ErrorObject("Interview already Exists.Duplicate Interview not allowed.","",localeObj);*/
		interview.setInterviewDate(LMSUtil.convertStringToDate(requestParameters.getParameter("interviewDate").trim()));
		
		String timeFromStr = requestParameters.getParameter("timeFromHour") + ":" + requestParameters.getParameter("timeFromMinute") + " " + requestParameters.getParameter("timeFromAMPM");
		interview.setStartTime(LMSUtil.convertoDateHHMMAMPM1(timeFromStr));
		
		String timeToStr = requestParameters.getParameter("timeToHour") + ":" + requestParameters.getParameter("timeToMinute") + " " + requestParameters.getParameter("timeToAMPM");
		interview.setEndTime(LMSUtil.convertoDateHHMMAMPM1(timeToStr));
		
		interview.setLocation(requestParameters.getParameter("location").trim());
		interview.setInterviewMaterial(requestParameters.getParameter("interviewMaterial").trim());
		 
		interview.setBuCode(Integer.parseInt(requestParameters.getParameter("bu").trim()));
		interview.setDistrict(Integer.parseInt(requestParameters.getParameter("district").trim()));
		interview.setBranchCode(Integer.parseInt(requestParameters.getParameter("branch").trim()));
		
		interview.setCityCode(requestParameters.getParameter("city").trim());
		interview.setSscCode(requestParameters.getParameter("ssc").trim());
		
		interview.setOfficeCode(requestParameters.getParameter("office").trim());
		
		Date today = new Date();
		String toDayStr = LMSUtil.convertDateToString(today);
		today = LMSUtil.convertStringToDate(toDayStr);
		if(interview.getInterviewDate().before(today))
			 return new ErrorObject("Interview Date  should not be before Today", "",localeObj);
		if(interview.getEndTime().before(interview.getStartTime()) || interview.getEndTime().equals(interview.getStartTime()))
			 return new ErrorObject("End Time should be earlier than Start Time", "",localeObj);
		
		SimpleDateFormat ra = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		if(interview.getInterviewDate().equals(today)){
			
			Date crtTime=new Date(System.currentTimeMillis());
			Date stTime=interview.getStartTime();
			
			try {
				crtTime=ra.parse(ra.format(crtTime));
				stTime=ra.parse(ra.format(stTime));
				if(crtTime.after(stTime))
				{
					 return new ErrorObject("Start Time Should Be After Than Current Time", "",localeObj);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		return interview;
		
    }
	/**
	 * <p>This method performs call for insertion method for interview 
	 *  & audit trail for interviw add 
	 *  & sets message object in request</p> 
	 * @param interview Interview object
	 * @param req Servlet Request Parameter
	 * @return Interview object
	 */
	public Object addInterview(Interview interview, HttpServletRequest req) {
		log.log(Level.INFO,"InterviewMaintenance --> AddInterview ");
		MsgObject msgObj = null;
		User userObj = (User)req.getSession().getAttribute("currUserObj");
		
		interview.setCreationDate(new Date());
		interview.setCreatedBy(userObj.getStaffLoginId());
		interview.setModificationDate(new Date());
	    interview.setModifiedBy(userObj.getStaffLoginId());
		interview.setStatus(true);
		interview.setToken(LMSUtil.getRendomToken());
		
		int record_created=insertInterview(interview,req);
		if(record_created>0){
		   AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
		   auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"",AuditTrail.MODULE_INTERVIEW,AuditTrail.FUNCTION_CREATE,interview.toString()));
		   msgObj = new MsgObject("The new Interview has been successfully created");
		}
		else{
			   msgObj = new MsgObject("The new Interview has not been created");
			}
		 req.setAttribute("messageObject", msgObj);
		 req.setAttribute("CacheName", "Interview");
		 Pager pager=getAllinterview(req);
	     req.getSession().setAttribute("pager", pager);
		 req.setAttribute("pager",pager);
		 
	 return interview;
	}
	/**
	  *<p>This method performs Interview Insertion </p>
	 * @param interview Interview object
	 * @param requestParameters
	 * @return Interview creation record count 
	 */
public  int insertInterview(Interview interview,HttpServletRequest requestParameters) {
		
		log.log(Level.INFO,"InterviewMaintenance --> insertInterview ");
		int record_created=0;
		Session session = null;
		Transaction tx = null;
		String file_name="";
//		ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		String interviewPath  = msgProps.getString("InterviewPath");
		Map<String,String> configurationMap =(Map<String,String>) requestParameters.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		String interviewPath  = configurationMap.get("InterviewPath");
		
		String tempDir = System.getProperty("java.io.tmpdir");
		LogsMaintenance logsMain=new LogsMaintenance();
		 try
			{
				session = HibernateFactory.openSession();
				tx= session.beginTransaction();
				
				if(requestParameters.getSession().getAttribute("material_file_name")!=null){
					file_name = (String)requestParameters.getSession().getAttribute("material_file_name");
					if(file_name.contains(".pdf") || file_name.contains(".PDF") 
							|| file_name.contains(".jpg") || file_name.contains(".JPG")
							|| file_name.contains(".png") || file_name.contains(".PNG")
							|| file_name.contains(".gif") || file_name.contains("GIF")){
						String filePath = uploadFile(interviewPath,tempDir,file_name,requestParameters,interview,"material_byte_session");
						interview.setAttachmentName(file_name);
						interview.setAttachmentPath(filePath);
					}
			   }
				
				record_created=(Integer) session.save(interview);
				
				 if(requestParameters.getSession().getAttribute("material_file_name")!=null){
				    	if(file_name.contains(".pdf") || file_name.contains(".PDF") 
								|| file_name.contains(".jpg") || file_name.contains(".JPG")
								|| file_name.contains(".png") || file_name.contains(".PNG")
								|| file_name.contains(".gif") || file_name.contains("GIF")){
				    		InterviewMaterial IntMat = null;
				    	 byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute("material_byte_session");
				    	 String field_name = (String)requestParameters.getSession().getAttribute("material_field_name");
					    if(record_created>0){
					    	IntMat = new InterviewMaterial();
					    	IntMat.setInterviewCode(record_created);
					    	IntMat.setMaterialName(file_name);
					    	IntMat.setFieldName(field_name);
					    	IntMat.setMaterial(bytearray);
					    	session.save(IntMat);
					    	//tx.commit();
					    }
					   
						requestParameters.getSession().removeAttribute("material_file_name");// removing uploaded file details from session after file is uploaded
						requestParameters.getSession().removeAttribute("material_byte_session");//
						requestParameters.getSession().removeAttribute("material_field_name");//
						
						
				    }
				    }
				 
				tx.commit();
				
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage()); 
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
				e.printStackTrace();
			}finally{
				try{
					HibernateFactory.close(session);
				}catch(Exception e){
					
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}
			}
		return record_created;
 }
/**
 * <p>This method performs Interview Listing</p>
 * @param req Servlet Request Parameter
 * @return Pager object
 */
public   Pager interviewAllSearchListing(HttpServletRequest req)
{
	LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
	LinkedList item = new LinkedList();
    Interview interview = null;
    ArrayList interviewList=new  ArrayList();
    interviewList = getAllInterview(req);
  //  req.getSession().setAttribute("selectedObj", interviewList);
   
    for(int i = 0; i < interviewList.size(); i++)
    {
    	interview = new Interview();
    	interview = (Interview)interviewList.get(i);
    	item.add(interview.getInterviewListingTableRow(localeObj));
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
 * <p>This method performs Interview Listing</p>
 * @param req Servlet Request Parameter
 * @return Pager object
 */
public   Pager getAllinterview(HttpServletRequest req)
{
	LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
	LinkedList item = new LinkedList();
    Interview interview = null;
    ArrayList interviewList=new  ArrayList();
    interviewList = getInterview(req);
  //  req.getSession().setAttribute("selectedObj", interviewList);
   
    for(int i = 0; i < interviewList.size(); i++)
    {
    	interview = new Interview();
    	interview = (Interview)interviewList.get(i);
    	item.add(interview.getInterviewListingTableRow(localeObj));
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
 * <p>This method retrieves Interview lists based on search criteria</p>
 * @param req Servlet Request Parameter
 * @return Interview lists
 */
public  ArrayList getAllInterview(HttpServletRequest req) {
	
	log.log(Level.INFO,"InterviewMaintenance --> getSearchedInterview ");
	Interview interview = null;
	ArrayList listData =  new  ArrayList();
	
	String interviewName = req.getParameter("interviewName");
	String interviewType = req.getParameter("interviewType");
	String candidateName = req.getParameter("name");
	String month = req.getParameter("month");
	String year = req.getParameter("year");
	String bu = req.getParameter("bu");
	String district = req.getParameter("district");
	String city = req.getParameter("city");
	String office = req.getParameter("office");
	String ssc = req.getParameter("ssc");
	String branchCode=req.getParameter("branch");
	//To Retain Search Criteria
	Map<String,String> intMap = new HashMap<String,String>();
	if(interviewName!=null)
		intMap.put("interviewName", interviewName);
	if(interviewType!=null)
		intMap.put("interviewType", interviewType);
	if(candidateName!=null)
		intMap.put("candidateName", candidateName);
	if(month!=null)
		intMap.put("month", month);
	if(year!=null)
		intMap.put("year", year);
	if(bu!=null)
		intMap.put("bu", bu);
	if(district!=null)
		intMap.put("district", district);
	if(city!=null)
		intMap.put("city", city);
	if(office!=null)
		intMap.put("office", office);
	if(ssc!=null)
		intMap.put("ssc", ssc);
	if(branchCode!=null)
		intMap.put("branch", branchCode);
	
	req.getSession().setAttribute("INT_SEARCH_OBJ", intMap);
	String query="",sqlCond = "";
	Session session = null;
	 try	
		{
		    session = HibernateFactory.openSession();   
		    session.setDefaultReadOnly(true);
		   // Criteria criteria = session.createCriteria(Interview.class);
		    if(candidateName!=null && candidateName.length() > 0)
		    	query = " select  I.interview_code,I.interviewSessionName,I.interviewType,I.buName,I.distName,I.cityName,I.sscName,I.branchName," +
		    			"I.officeName,I.interviewDate,I.StartTime,I.EndTime,I.modifiedBy,I.modificationDate,I.registeredCount,I.attendeeCount from  " +
		    			"Interview I,InterviewCandidate IC  where I.interview_code = IC.interviewCode and MONTH(I.interviewDate) =:month and  YEAR(I.interviewDate)=:year and I.status = 1 and IC.status = 1";
		    else	
		    	query = " FROM Interview  I where MONTH(I.interviewDate) =:month and  YEAR(I.interviewDate)=:year and (I.status = 1 or I.calendarServiceError > 0)";
		    
			if(candidateName!=null && candidateName.length() > 0){
					sqlCond += " and IC.candidateName LIKE :candidateName";
				}
			if(interviewName!=null && interviewName.length() > 0){
			  //criteria.add(Restrictions.like("interviewSessionName", interviewName,MatchMode.ANYWHERE));
				sqlCond += " and I.interviewSessionName LIKE :interviewSessionName";
			}
			
			if(interviewType!=null && interviewType.length() > 0 && !interviewType.equals("0")){
				//  criteria.add(Restrictions.like("interviewType", interviewType));
				  sqlCond += " and I.interviewType LIKE :interviewType";
				}
			
			//  criteria.add(Restrictions.sqlRestriction("MONTH(INTERVIEW_DATE)=?", Integer.parseInt(month),IntegerType.INSTANCE));
			//  criteria.add(Restrictions.sqlRestriction("YEAR(INTERVIEW_DATE)=?", Integer.parseInt(year),IntegerType.INSTANCE));
			
			if(bu!=null && Integer.parseInt(bu) !=0){
			  //criteria.add(Restrictions.eq("buCode", Integer.parseInt(bu)));
				sqlCond += " and I.buCode =:bu";
			 }
			if(district!=null && Integer.parseInt(district) !=0){
			 // criteria.add(Restrictions.eq("district", Integer.parseInt(district)));
				sqlCond += " and I.district =:district";
			}
			
			if(branchCode!=null && Integer.parseInt(branchCode) !=0){
				  //criteria.add(Restrictions.eq("branchCode", Integer.parseInt(branchCode)));
				sqlCond += " and I.branchCode =:branchCode";
				}
			
			
			if(city!=null && !city.equals("0")){
			  //criteria.add(Restrictions.eq("cityCode", Integer.parseInt(city)));
				sqlCond += " and I.cityCode =:city";
			}
			if(ssc!=null && !ssc.equals("0")){
			  //criteria.add(Restrictions.eq("sscCode", Integer.parseInt(ssc)));
				sqlCond += " and I.sscCode =:ssc";
			}
			if(office!=null && !office.equals("0")){
				sqlCond += " and I.officeCode =:office";
			}
			
			query  = query + sqlCond;
			  //criteria.add(Restrictions.eq("status", true));
			Query cQuery  = session.createQuery(query);
			
			cQuery.setParameter("month", Integer.parseInt(month));
			cQuery.setParameter("year", Integer.parseInt(year));
			if(candidateName!=null && candidateName.length() > 0){
				cQuery.setParameter("candidateName", "%"+candidateName+"%");
			}
			if(interviewName!=null && interviewName.length() > 0){
				cQuery.setParameter("interviewSessionName", "%"+interviewName+"%");
				}
				
				if(interviewType!=null && interviewType.length() > 0 && !interviewType.equals("0")){
					cQuery.setParameter("interviewType", interviewType);
					}
				
				if(bu!=null && Integer.parseInt(bu) !=0){
					cQuery.setParameter("bu",  Integer.parseInt(bu));
				 }
				if(district!=null && Integer.parseInt(district) !=0){
					cQuery.setParameter("district",  Integer.parseInt(district));
				}
				
				if(branchCode!=null && Integer.parseInt(branchCode) !=0){
					cQuery.setParameter("branchCode",  Integer.parseInt(branchCode));
					}
				
				
				if(city!=null && !city.equals("0")){
					cQuery.setParameter("city", city);
				}
				if(ssc!=null && !ssc.equals("0")){
					cQuery.setParameter("ssc", ssc);
				}
				if(office!=null && !office.equals("0")){
					cQuery.setParameter("office", office);
				}
			//ArrayList interviewList = (ArrayList) criteria.list();
			ArrayList interviewList = (ArrayList) cQuery.list();
			Iterator iterator = interviewList.iterator();
			if(candidateName!=null && candidateName.length() > 0){
            while(iterator.hasNext()){
            	interview = new Interview();
            	Object[] intObj = (Object[]) iterator.next();
            	interview.setInterview_code((Integer) intObj[0]);
            	interview.setInterviewSessionName((String) intObj[1]);
            	interview.setInterviewType((String) intObj[2]);
            	interview.setBuName((String) intObj[3]);
            	interview.setDistName((String) intObj[4]);
            	interview.setCityName((String) intObj[5]);
            	interview.setSscName((String) intObj[6]);
            	interview.setBranchName((String) intObj[7]);
            	interview.setOfficeName((String) intObj[8]);
            	interview.setInterviewDate((Date) intObj[9]);
            	interview.setStartTime((Date) intObj[10]);
            	interview.setEndTime((Date) intObj[11]);
            	interview.setModifiedBy((String) intObj[12]);
            	interview.setModificationDate((Date) intObj[13]);
            	interview.setRegisteredCount((Integer) intObj[14]);
            	interview.setAttendeeCount((Integer) intObj[15]);
             
                listData.add(interview);
            }}
            else{
            	  while(iterator.hasNext()){
                      interview=(Interview) iterator.next();
                      listData.add(interview);
                  }
            }
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
			}catch(Exception e){
				
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}
		}
	return listData;
}


/**
 * <p>This method retrieves Interview lists based on search criteria</p>
 * @param req Servlet Request Parameter
 * @return Interview lists
 */
public  ArrayList getInterview(HttpServletRequest req) {
	
	log.log(Level.INFO,"InterviewMaintenance --> getSearchedInterview ");
	Interview interview = null;
	ArrayList listData =  new  ArrayList();
	
	User userObj = (User)req.getSession().getAttribute("currUserObj");
	//req.getSession().setAttribute("INT_SEARCH_OBJ", intMap);
	Session session = null;
	 try
		{
		    session = HibernateFactory.openSession();   
		    session.setDefaultReadOnly(true);
		    int month=Calendar.getInstance().get(Calendar.MONTH)+1;
			int year= Calendar.getInstance().get(Calendar.YEAR);
			
		    Criteria criteria = session.createCriteria(Interview.class);
		    criteria.add(Restrictions.sqlRestriction("MONTH(INTERVIEW_DATE)=?",month,IntegerType.INSTANCE));
		    criteria.add(Restrictions.sqlRestriction("YEAR(INTERVIEW_DATE)=?", year,IntegerType.INSTANCE));
			
		 // get all the interviews that are not deleted, or those with sync errors
            criteria.add(Restrictions.or(
                    Restrictions.eq("status", true),
                    Restrictions.gt("calendarServiceError", 0))
            );
			
			if(userObj.isBuLevel() && userObj.getBuCode()!=0){
				criteria.add(Restrictions.eq("buCode", userObj.getBuCode()));
			}
			if(userObj.isDistrictLevel()){
				criteria.add(Restrictions.eq("district", userObj.getDistrict()));
			}
			if(userObj.isBranchLevel()){
				criteria.add(Restrictions.eq("branchCode", userObj.getBranchCode()));
			}
			if(userObj.isCityLevel()){
				criteria.add(Restrictions.eq("cityCode", userObj.getCityCode()));
			}
			if(userObj.isSscLevel()){
				criteria.add(Restrictions.eq("sscCode", userObj.getSscCode()));
			}
			if(userObj.isOfficeLevel()){
				criteria.add(Restrictions.eq("officeCode", userObj.getOfficeCode()));
			}
			
			listData = (ArrayList) criteria.list();

		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
			}catch(Exception e){
				
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}
		}
	return listData;
}
/**
 * <p>This method retrieves Interview based on interviewCode  </p>
 * @param interview_code
 * @return Interview object
 */
public  Interview getInterviewBasedOnInterviewCode(int interview_code) {
	
	log.log(Level.INFO,"InterviewMaintenance -->Get Interview ");
	Interview interview = null;
	Session session = null;
	 try
		{
		    session = HibernateFactory.openSession();
		    session.setDefaultReadOnly(true);
		    interview = (Interview) session.get(Interview.class, interview_code);
			
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
			}catch(Exception e){
				
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}
		}
	return interview;
}

/**
 * <p>This method retrieves Interview candidates cc Test  </p>
 * @param interview_code,interview object
 * @return Addrecssbook object
 */
public  AddressBook getInterviewCandidateCCTest(Interview interview,int candidateCode) {
	
	log.log(Level.INFO,"InterviewMaintenance --> getInterviewCandidateCCTest ");
	InterviewCandidate interviewcandidate = null;
	Session session = null;
	ArrayList list=null;
	AddressBook address=null;
	 try
		{
		    session = HibernateFactory.openSession();
		   
		    Query query= session.createQuery("from InterviewCandidate where candidateCode=:candidatecode and interviewCode=:interviewCode ");
		    query.setParameter("candidatecode", candidateCode);
		    query.setParameter("interviewCode", interview.getInterview_code());
		   
		     list= (ArrayList) query.list();
		     if(list.size()>0){
		    	 interviewcandidate=(InterviewCandidate) list.get(0);
		    	 address=(AddressBook) session.get(AddressBook.class,Integer.parseInt(interviewcandidate.getInterviewCandidateCode()));
		     }
		     
			
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}
		}
	return address;
}

/**
 * <p>This method retrieves Application form in pdf format   </p>
 * @param interview_code
 * @return inteview pdf file
 */

public  InterviewCandidateMaterial getApplicationForm(int interview_code,HttpServletRequest req,String candidateCode) {
	
	log.log(Level.INFO,"InterviewMaintenance -->Get Interview ");
	InterviewCandidateMaterial interview = null;
	Session session = null;
	ArrayList list=new ArrayList();
	try
		{
		    session = HibernateFactory.openSession();
		    Query query= session.createQuery("from InterviewCandidateMaterial where interviewCode=:code ");
		    query.setParameter("code", interview_code);
		     list= (ArrayList) query.list();
		     if(list.size()>0){
		    	 interview=(InterviewCandidateMaterial) list.get(0);
		    	 
		    	 
		    	 String fileName=req.getRealPath(File.separator)+"resources"+File.separator+"upload"+File.separator;
				// FileWriter fstream = new FileWriter(fileName+interview.getMaterialFileName()); 
				 OutputStream out = new FileOutputStream(fileName+interview.getMaterialFileName());
				 
				// BufferedWriter out = new BufferedWriter(fstream);  
				 
				 for (Byte b: interview.getFormContent()) {  
			            out.write(b);  
			        }  
			        out.close(); 
		    	/* String fileName=req.getRealPath(File.separator)+"resources"+File.separator+"upload"+File.separator;
		    	 FileInputStream fileInputStream = null;
		    	 File file = new File(fileName+interview.getMaterialFileName());
		    	 if(!file.exists()){
		    		 file.createNewFile();
		    	 }
		    	 
		    	 byte[] bFile = new byte[(int) file.length()];
		    	 fileInputStream = new FileInputStream(file);
		         fileInputStream.read(bFile);
		         fileInputStream.close();*/
				 
		     }
		    
			
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}
		}
	return interview;
}


/**
 * <p>This method performs call for modify method for iterview 
 *  & audit trail for interview update 
 *  & sets message object in request</p>
 * @param interview Interview object
 * @param req Servlet Request Parameter
 * @return Interview object
 */
public Object updateInterview(Interview interview, HttpServletRequest req) {
	log.log(Level.INFO,"InterviewMaintenance --> Modify Interview");
	MsgObject msgObj = null;
    User userObj = (User)req.getSession().getAttribute("currUserObj");
    interview.setModificationDate(new Date());
    interview.setModifiedBy(userObj.getStaffLoginId());
    
	int record_updated= modifyInterview(interview,req);
	    
	if(record_updated>0){
		AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
	    auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"",AuditTrail.MODULE_INTERVIEW,AuditTrail.FUNCTION_UPDATE,interview.toString()));
		msgObj = new MsgObject("The Interview has been successfully updated");	
		EmailNotification.sendInterviewUpdateEmailNotification(interview,req);
		
		// send web service to iCalendar
        iCalendarInterviewHelper helper = new iCalendarInterviewHelper();
        if(!"".equals(userObj.getSsoSessionId())){
        	helper.updateInterview(interview.getInterview_code(), userObj.getSsoSessionId());
        }
	}
	else{
		msgObj = new MsgObject("The Interview has not been updated");	
	}
	req.setAttribute("messageObject", msgObj);
	 req.setAttribute("CacheName", "Interview");
	 Pager pager=getAllinterview(req);
     req.getSession().setAttribute("pager", pager);
	 req.setAttribute("pager",pager);
	
 return interview;
}

/**
 * <p>This method performs Edit of Interview </p> 
 * @param interview Interview object
 * @return interview updation record count 
 */
public  int modifyInterview(Interview interview,HttpServletRequest req) {
	
	log.log(Level.INFO,"InterviewMaintenance -->Modify Interview");
	int record_updated=0;
	Session session = null;
	Transaction tx = null;
	String file_name = "";
	Map<String,String> configurationMap =(Map<String,String>) req.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
	String interviewPath  = configurationMap.get("InterviewPath");
	String tempDir = System.getProperty("java.io.tmpdir");
	 try
		{
		    session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			
			if(req.getSession().getAttribute("material_file_name")!=null){
				file_name = (String)req.getSession().getAttribute("material_file_name");
				if(file_name.contains(".pdf") || file_name.contains(".PDF") 
						|| file_name.contains(".jpg") || file_name.contains(".JPG")
						|| file_name.contains(".png") || file_name.contains(".PNG")
						|| file_name.contains(".gif") || file_name.contains("GIF")){
					String filePath = uploadFile(interviewPath,tempDir,file_name,req,interview,"material_byte_session");
					interview.setAttachmentName(file_name);
					interview.setAttachmentPath(filePath);
				}
		   }
		    session.update(interview);
			
			record_updated=1;
			
			if(req.getSession().getAttribute("material_file_name")!=null){  //checks if user  uploaded files  to update
				if(file_name.contains(".pdf") || file_name.contains(".PDF") 
						|| file_name.contains(".jpg") || file_name.contains(".JPG")
						|| file_name.contains(".png") || file_name.contains(".PNG")
						|| file_name.contains(".gif") || file_name.contains("GIF")){ //checks if  file is uploaded
					Query deleteQ = session.createQuery("delete InterviewMaterial where interviewCode = "+interview.getInterview_code()+"");
			    	deleteQ.executeUpdate(); 
			  		InterviewMaterial IntMat = null;
			    	 byte[] bytearray=(byte[]) req.getSession().getAttribute("material_byte_session");
			    	 String field_name = (String)req.getSession().getAttribute("material_field_name");
				   
				    	IntMat = new InterviewMaterial();
				    	IntMat.setInterviewCode(interview.getInterview_code());
				    	IntMat.setMaterialName(file_name);
				    	IntMat.setFieldName(field_name);
				    	IntMat.setMaterial(bytearray);
				    	session.save(IntMat);
				    	//tx.commit();
				    
				    }
					
				req.getSession().removeAttribute("material_file_name");// deleting uploaded file from session
				req.getSession().removeAttribute("material_byte_session");
				req.getSession().removeAttribute("material_field_name");
		   }
			
			tx.commit();
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}
		}
	return record_updated;
}

/**
 * <p>This method performs call for delete method for interview 
 *  & audit trail for interview delete 
 *  & sets message object in request</p>
 * @param interview_code
 * @param req Servlet Request Parameter
 */
public void deleteInterview(int interview_code,HttpServletRequest req) {
    log.log(Level.INFO,"Interview Maintenance --> Delete Interview");
		
		MsgObject msgObj = null;
		User userObj = (User)req.getSession().getAttribute("currUserObj");
		int record_deleted=deleteParticularInterview(interview_code,req);
		
		if(record_deleted>0){
			msgObj = new MsgObject("The Interview has been successfully deleted");
			
			// call the iCalendar web service to delete interview
            iCalendarInterviewHelper helper = new iCalendarInterviewHelper();
            if(!"".equals(userObj.getSsoSessionId())){
            	helper.deleteInterview(interview_code, userObj.getSsoSessionId());
            }
		}
		else{
			msgObj = new MsgObject("The Interview has not been deleted");
		}
		req.setAttribute("messageObject", msgObj);
		 req.setAttribute("CacheName", "Interview");
		 Pager pager=getAllinterview(req);
	     req.getSession().setAttribute("pager", pager);
		 req.setAttribute("pager",pager);
}

/**
 * <p>This method performs Delete of Interview </p>
 * @param interview_code
 * @param req Servlet Request Parameter
 * @return interview deletion record count 
 */
public  int deleteParticularInterview(int interview_code,HttpServletRequest req) {
	
	 log.log(Level.INFO,"Interview Maintenance --> Delete Interview ");
		int record_deleted=0;
		User userObj = (User)req.getSession().getAttribute("currUserObj");
		Session session = null;
		Transaction tx = null;
		 try
			{
			    session = HibernateFactory.openSession();
				tx = session.beginTransaction();
				Interview interview=(Interview) session.get(Interview.class, interview_code);
				interview.setStatus(false);
			    session.update(interview);
				tx.commit();
				AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			    auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"",AuditTrail.MODULE_INTERVIEW,AuditTrail.FUNCTION_DELETE,interview.toString()));
				record_deleted=1;
				EmailNotification.sendInterviewDeleteEmailNotification(interview,req);
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					HibernateFactory.close(session);
				}catch(Exception e){
					
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}
			}
		return record_deleted;
}

/**
 * <p>This method performs multiple Interviews Upload through CSV File</p> 
 * @param interviewObj Interview object
 * @param requestParameters Servlet Request Parameter
 */
public Object cSVUpload(Interview interviewObj,HttpServletRequest requestParameters){
	 LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
	  if(interviewObj == null)
      {
		  interviewObj = new Interview();
		  return interviewObj;
      }
	 
//	  ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//	  String interviewPath  = msgProps.getString("InterviewPath");
		Map<String,String> configurationMap =(Map<String,String>) requestParameters.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		//String interviewPath  = configurationMap.get("InterviewPath");
	  String csv_file_name = "";
	  if(requestParameters.getSession().getAttribute("csv_file_name")!=null){
		  csv_file_name=(String)requestParameters.getSession().getAttribute("csv_file_name");
		}
		
	   requestParameters.getSession().removeAttribute("csv_file_name");
	   String tempDir = System.getProperty("java.io.tmpdir");
	   Session session= null;
	   int record_created=0;
	   StringBuffer strbuf=new StringBuffer();
	   int sucessCnt=0;
	   int m=0;
       int l=0;
       int rowCount=0;
	try{
		
		String serverFilename = "resources/upload/Excel"+"/"+"INT_" + LMSUtil.getRendomToken();

		File uploadedFolder = new File(serverFilename);
		if(!uploadedFolder.exists()){
			uploadedFolder.mkdirs();
		}
		if(csv_file_name!=null && !csv_file_name.equals("")){
			
			byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute("csv_byte_session");
			if(csv_file_name.contains(".xlsx") || csv_file_name.contains(".XLSX") ||
					csv_file_name.contains(".xls") || csv_file_name.contains(".XLS")){
				try {
					FileOutputStream stream = new FileOutputStream(serverFilename+"/"+csv_file_name);
				    stream.write(bytearray);
				    stream.close();
				} catch(Exception e){
					e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
				
				}
			}
			//delete temp directory
			File temp_file = new File(tempDir+"/" + csv_file_name);
      	FileUtils.deleteFileNFolder(temp_file);
      	
      	// Retrieve datas from CSV
      	/*FileInputStream fileInputStream = new FileInputStream(new File(serverFilename+"/"+csv_file_name));
      	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));*/
      	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(serverFilename+"/"+csv_file_name), "UTF-8"));
      
      	String records = null;
      	String interviewType="",interviewSessionName="",interviewDate="",startTime="",endTime="",location="",interviewMaterial="",
      			estimatedCondidates="",buName="",distName="",cityName="",sscName="",branchName="",officeName="";
      	int buCode=0,distCode=0,branchCode=0;
      			String cityCode="",sscCode="",officeCode="";
      	session = HibernateFactory.openSession();
      	ImoUtilityData imoUtilityData = new ImoUtilityData();
      	
      	User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
      	interviewObj.setCreationDate(new Date());
      	interviewObj.setCreatedBy(userObj.getStaffLoginId());;
      	interviewObj.setModificationDate(new Date());
      	interviewObj.setModifiedBy(userObj.getStaffLoginId());;
      	interviewObj.setStatus(true);
      	interviewObj.setToken(LMSUtil.getRendomToken());
		
        AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
  		boolean flag=false;
  		LMSUtil lmsUtil = new LMSUtil();
  		
  		
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
                	 interviewType = cell.toString();
						if(interviewType.equals("")){
						   flag=true;
						   strbuf.append(localeObj.getTranslatedText("Required Interview Type at row number")+rowCount);
						   strbuf.append("\n");
						   break;
				         }
						else{
						   interviewObj.setInterviewType(interviewType);
						}
                  }
                 
                 if(cellCount==2){ 
                	 interviewSessionName = cell.toString();
						interviewObj.setInterviewSessionName(interviewSessionName);
						if(interviewSessionName.equals("")){
							 flag=true;
							  strbuf.append(localeObj.getTranslatedText("Required Interview Session Name at row number")+rowCount);
							  strbuf.append("\n");
							  break;
				            
						}
						/*else if(checkDuplicateInterview(interviewObj)){
							 flag=true;
							 strbuf.append("Duplicate Interview Name at row number "+rowCount);
							 break;
						}*/
						else{
						     interviewObj.setInterviewSessionName(interviewSessionName);
						}
                  }
                 
                 if(cellCount==3){ 
                	 //System.out.println("date --> "+cell.getDateCellValue()+"");
                	 interviewDate = cell.getDateCellValue()+"";
                	 interviewDate=LMSUtil.convertExcelDateToString(interviewDate);
                	 
						Date intDate=LMSUtil.convertExcelDateToDate(interviewDate);
						if(interviewType.equals("")){
							flag=true;
							strbuf.append(localeObj.getTranslatedText("Required Interview Date at row number")+rowCount);
							strbuf.append("\n");
							break;
						}
						else{
							Date today = new Date();
							String toDayStr = LMSUtil.convertDateToString(today);
							today = LMSUtil.convertStringToDate(toDayStr);
							if(lmsUtil.validateDDMMYYYY(interviewDate)){
								interviewObj.setInterviewDate(intDate);
								
							    if(intDate.before(today)){
							     flag=true;
							     strbuf.append(localeObj.getTranslatedText("Interview Date should not be before Today at row number")+rowCount);
							     strbuf.append("\n");
							     break;
						     }
							}	
						else{
							flag=true;
							strbuf.append(localeObj.getTranslatedText("Date Format Invalid.Should Be DD/MM/YYYY at line number")+l);
							strbuf.append("\n");
							break;
						}
				    }
                 }
                 
                 
                 if(cellCount==4){ 
                	 
                	 startTime = cell.getDateCellValue()+"";
						if(interviewType.equals("")){
							flag=true;
							strbuf.append(localeObj.getTranslatedText("Required StartTime at row number")+rowCount);
							strbuf.append("\n");
							break;
						}
						else{
						    interviewObj.setStartTime((LMSUtil.converExcelTimetoDateHHMMAMPM1(startTime)));
						}
                 }
                 
                 if(cellCount==5){ 
                	 endTime = cell.getDateCellValue()+"";
						Date e1=LMSUtil.converExcelTimetoDateHHMMAMPM1(endTime);
						Date s1=LMSUtil.converExcelTimetoDateHHMMAMPM1(startTime);
						if(interviewType.equals("")){
							flag=true;
							strbuf.append(localeObj.getTranslatedText("Required EndTime at row number")+rowCount);
							strbuf.append("\n");
							break;
						}
						else if(e1.before(s1) || e1.equals(s1)){
							flag=true;
							strbuf.append(localeObj.getTranslatedText("End Time should be earlier than Start Time at row number")+rowCount);
							strbuf.append("\n");
							break;
						}
						else{
						    interviewObj.setEndTime((LMSUtil.converExcelTimetoDateHHMMAMPM1(endTime)));
						}
                 }
                 
                 if(cellCount==6){
                	 location = cell.toString();
						if(location.equals("")){
							flag=true;
							strbuf.append(localeObj.getTranslatedText("Required LOCATION at row number")+rowCount);
							strbuf.append("\n");
							break;
						}
						else{
						    interviewObj.setLocation(location);
						}
                 }
                 
                 if(cellCount==7){
                	 interviewMaterial = cell.toString();
						if(interviewMaterial.equals("")){
							flag=true;
							strbuf.append(localeObj.getTranslatedText("Required Interview Material at row number")+rowCount);
							strbuf.append("\n");
							break;
						}
						else{
						    interviewObj.setInterviewMaterial(interviewMaterial);
						}
                 }
                 
                 if(cellCount==8){
                	// estimatedCondidates = cell.getNumericCellValue()+"";
                	 int estimate=(int) cell.getNumericCellValue();
                	 System.out.println("esimate time "+estimate);
//						if(estimatedCondidates.equals("")){
//						   interviewObj.setEstimatedCondidates(0);
//						}
//						else if(!LMSUtil.validInt(estimatedCondidates)){
//							flag=true;
//							strbuf.append("Estimated Candidates Should be a Number at row number "+rowCount);
//							break;
//						}
						
							interviewObj.setEstimatedCondidates(estimate);
						
                 }
                 
                 if(cellCount==9){
                	 buName = cell.toString();
						if(buName.equals("")){
							flag=true;
							strbuf.append(localeObj.getTranslatedText("Required BU at row number")+rowCount);
							strbuf.append("\n");
							break;
						}
						else{
							buCode=imoUtilityData.getBuCodeBasedOnBuName(buName);	
							if(buCode==0){
								flag=true;
 							strbuf.append(localeObj.getTranslatedText("Invalid BU Name  row number")+rowCount);
 							strbuf.append("\n");
 							break;
							}
							else{
								interviewObj.setBuCode(buCode);
						    }
						}
                 }
                 if(cellCount==10){
                	 distName = cell.toString();
						if(distName.equals("")){
							interviewObj.setDistrict(0);
						}
							
						else{
							  distCode=imoUtilityData.getDistrictCodeBasedOnDistrictName(distName);	
							  if(distCode==0){
 								flag=true;
     							strbuf.append(localeObj.getTranslatedText("Invalid District Name at row number")+rowCount);
     							strbuf.append("\n");
     							break;
 							}
							  else{
								  flag=imoUtilityData.checkLevelOfCode(buCode,distCode,0,"0","0","0");
								  if(flag==true){
									  strbuf.append(localeObj.getTranslatedText("District Name Not available In this Bu  at row number")+rowCount);
									  strbuf.append("\n");
	        							break;
								  }
								  
								  interviewObj.setDistrict(distCode);
							  }
						 }
                 }
                 
                 if(cellCount==11){
                	 branchName = cell.toString();
						if(branchName.equals("")){
							interviewObj.setBranchCode(0);
						}	
							
						else{	   
							
							branchCode=imoUtilityData.getBranchCodeBasedOnBranchName(branchName);
							   if(branchCode==0){
 								flag=true;
     							strbuf.append(localeObj.getTranslatedText("Invalid Branch Name  at row number")+rowCount);
     							strbuf.append("\n");
     							break;
 							}
							   else{
								   flag=imoUtilityData.checkLevelOfCode(buCode,distCode,branchCode,"0","0","0");
									  if(flag==true){
										  strbuf.append(localeObj.getTranslatedText("Branch Name Not available In this District at row number")+rowCount);
										  strbuf.append("\n");
		        							break;
									  }
								   interviewObj.setBranchCode(branchCode);
							   }
						}
                 }
                 
                 if(cellCount==12){
                	 
                	 cityName = cell.toString();
						if(cityName.equals("")){
							interviewObj.setCityCode("0");
						}	
							
						else{	    
							   cityCode=imoUtilityData.getCityCodeBasedOnDistrictName(cityName);
							   if(cityCode.equals("0")){
 								flag=true;
     							strbuf.append(localeObj.getTranslatedText("Invalid City Name at row number")+rowCount);
     							strbuf.append("\n");
     							break;
 							}
							   else{
								   flag=imoUtilityData.checkLevelOfCode(buCode,distCode,branchCode,cityCode,"0","0");
									  if(flag==true){
										  strbuf.append(localeObj.getTranslatedText("City Name Not available In this Branch  at row number")+rowCount);
										  strbuf.append("\n");
		        							break;
									  }
								   interviewObj.setCityCode(cityCode);
							   }
						}
                 }
                 
                 if(cellCount==13){
                	 sscName = cell.toString();
						if(sscName.equals("")){
							interviewObj.setSscCode("0");
						}
						else{	
								sscCode=imoUtilityData.getSSCCodeBasedOnSSCName(sscName);
								if(sscCode.equals("0")){
 								flag=true;
     							strbuf.append(localeObj.getTranslatedText("Invalid SSC Name  at row number")+rowCount);
     							strbuf.append("\n");
     							break;
 							}
								else{
									
									flag=imoUtilityData.checkLevelOfCode(buCode,distCode,branchCode,cityCode,sscCode,"0");
									  if(flag==true){
										  strbuf.append(localeObj.getTranslatedText("Ssc Name Not available In this City at row number")+rowCount);
										  strbuf.append("\n");
		        							break;
									  }
									interviewObj.setSscCode(sscCode);
								}
						}
						                	 
                 }
                 
                 if(cellCount==14){
                	 officeName = cell.toString();
						if(officeName.equals("")){
							interviewObj.setOfficeCode("0");
						}
						else{	
								officeCode=imoUtilityData.getOfficeCodeBasedOnSSCName(officeName);
								if(officeCode.equals("0")){
 								flag=true;
     							strbuf.append(localeObj.getTranslatedText("Invalid Office Name  at row number")+rowCount);
     							strbuf.append("\n");
     							break;
 							}
								else{
									
									flag=imoUtilityData.checkLevelOfCode(buCode,distCode,branchCode,cityCode,sscCode,officeCode);
									  if(flag==true){
										  strbuf.append(localeObj.getTranslatedText("Office Name Not available In this SSC at row number")+rowCount);
										  strbuf.append("\n");
		        							break;
									  }
									interviewObj.setOfficeCode(officeCode);
								}
						}
                	 
                 }
            	 
                 cellCount++;
            	 
             }// cell while loop
             
             if(cellCount==0){
        		 cellCount++;
        		
        	 }else{
        		 if(flag==true){
        			cellCount=1;
 					flag=false;
 					strbuf.append("\n");
 					continue;
     			}else{
          			//insertion done here
        			
        			
          			if(userObj.getUserType().equals("AD")){
          				record_created = insertInterview(interviewObj,requestParameters);	
          				sucessCnt++;
          				cellCount=1;
          			}
          			else{
          				
          				if(userObj.isOfficeLevel()){
              				if(buCode>0 && distCode>0 && !cityCode.equals("0") && !sscCode.equals("0") && branchCode>0 && !officeCode.equals("0")){
              				record_created = insertInterview(interviewObj,requestParameters);
              				sucessCnt++;
              				cellCount=1;
              				}
          				}
          				else if(userObj.isSscLevel()){
          				if(buCode>0 && distCode>0 && !cityCode.equals("0") && !sscCode.equals("0") && branchCode>0){
          				record_created = insertInterview(interviewObj,requestParameters);
          				sucessCnt++;
          				cellCount=1;
          				}
          			}
          			else if(userObj.isCityLevel()){
          				if(buCode>0 && distCode>0 && !cityCode.equals("0") && branchCode>0){
          				record_created = insertInterview(interviewObj,requestParameters);
          				sucessCnt++;
          				cellCount=1;
          				}
          			}
          			
          			else if(userObj.isBranchLevel()){
          				if(buCode>0 && distCode>0 &&  branchCode>0){
          				record_created = insertInterview(interviewObj,requestParameters);
          				sucessCnt++;
          				cellCount=1;
          				}
          			}
          			
          			else if(userObj.isDistrictLevel()){
          				if(buCode>0 && distCode>0){
          				record_created = insertInterview(interviewObj,requestParameters);
          				sucessCnt++;
          				cellCount=1;
          				}
          			}
          			else if(userObj.isBuLevel()){
          				if(buCode>0){
          				record_created = insertInterview(interviewObj,requestParameters);
          				sucessCnt++;
          				cellCount=1;
          				}
          			}
    				 }
          			
          			   
          			if(record_created>0){
          			    auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"",AuditTrail.MODULE_INTERVIEW,AuditTrail.FUNCTION_CREATE,interviewObj.toString()));
          			}
        		}
        			flag=false;
        		 
        	 }
             
		}// row whicle loop
  		
  		
		if(strbuf.length()>0){
			ImoUtilityData imoutill=new ImoUtilityData();
			imoutill.summaryReport(strbuf,requestParameters);
		}
	}	
}
	catch(Exception e)
	{
		log.log(Level.SEVERE,e.getMessage());
		e.printStackTrace();
	}finally{
		try{
			//HibernateFactory.close(session);
		}catch(Exception e){
			
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
	}
	
	int failedCnt = rowCount - sucessCnt;
	requestParameters.getSession().setAttribute("strbuf", strbuf);
	requestParameters.getSession().setAttribute("formObj",new PathDetail().getFormObj("InterviewUploadCSV"));
	requestParameters.setAttribute("CacheName", "Interview");
	//if(sucessCnt!=0){ 
	String uploadSucessString=localeObj.getTranslatedText("Number of records uploaded successfully");
	String recoredFailString=localeObj.getTranslatedText("Number of records fail");
			return new ErrorObject(uploadSucessString+":"+sucessCnt+" <br> "+recoredFailString+" :"+(failedCnt-2)+"  ", "",localeObj);
		//}else{
			//return new ErrorObject("The new Holiday csv file uploaded Successfully", "");
		//}
	  
}

/**
 * <p>This method performs checking of Duplicate Interviews</p>
 * @param intName
 * @return true/false boolean value
 */
public boolean checkDuplicateInterview(Interview interviewObj){
	Session session = null;
	try{
		session = HibernateFactory.openSession();
		Query selectQ = session.createQuery("select  interview_code from Interview  where interviewSessionName =:interviewSessionName and interview_code!=:interview_code");
		selectQ.setParameter("interviewSessionName", interviewObj.getInterviewSessionName());
		selectQ.setParameter("interview_code", interviewObj.getInterview_code());
		List list = selectQ.list();
		if(list!=null && list.size() > 0)
			return true;
		else 
			return false;
	}
	catch(Exception e)
	{
		log.log(Level.SEVERE,e.getMessage());
		e.printStackTrace();
	}finally{
		try{
			HibernateFactory.close(session);
		}catch(Exception e){
			log.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
		}
	}
	return false;
	
}
/**
 * <p>This methods lists Candidate details</p>
 * @param req Servlet Request Parameter
 * @return Pager object
 */
public   Pager candidateAllSearchListing(HttpServletRequest req)
{
	log.log(Level.INFO, "InterviewMaintenace-->candidateAllSearchListing");
	Interview interview =null;
	 ArrayList interviewList=(ArrayList) req.getSession().getAttribute("selectedObj");
	 int req_int_code= Integer.parseInt(req.getParameter("interview_code"));
	    if(req.getParameter("interview_code") != null)
	    {
	        for(int i = 0; i < interviewList.size(); i++)
	        {
	        	
	        	interview=(Interview) interviewList.get(i);
	        	int list_int_code = interview.getInterview_code();
	            if(req_int_code != list_int_code)
	                continue;
	           // interview=(Interview) interviewList.get(i);
	            break;
	        }

	    }
	
	    req.getSession().setAttribute("selectedObj", interview);
	
	LinkedList item = new LinkedList();
   
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
 * <p>This method returns path of pdf file in Add/Edit of INterview</p>
 * @param intPath path where file is stored 
 * @param tempDir temporary directory
 * @param fileName uploaded file name
 * @param requestParameters Servlet Request Parameter
 * @param interview Interview object
 * @param byteSession file in byte stored in seesion
 * @return path as string
 */
public String uploadFile(String intPath,String tempDir,String fileName,HttpServletRequest requestParameters,Interview interview,String byteSession){
	FileOutputStream stream=null;
	String path = "";
	try {
	String serverFilename = intPath+"/"+ "INT_" + interview.getToken();
		File uploadedFolder = new File(serverFilename);
		if(!uploadedFolder.exists()){
			uploadedFolder.mkdirs();
		}
		byte[] bytearray=(byte[]) requestParameters.getSession().getAttribute(byteSession);
	
			
					stream = new FileOutputStream(serverFilename+"/"+fileName);
				    stream.write(bytearray);
			
				    path = "INT_" + interview.getToken()+"/"+fileName;
		//delete temp directory
		File temp_file = new File(tempDir+"/" + fileName);
   	    FileUtils.deleteFileNFolder(temp_file);
	} catch(Exception e){log.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
	}finally{if(stream!=null)try{stream.close();}catch(Exception e){log.log(Level.SEVERE, e.getMessage());e.printStackTrace();}}
   	return path;

}
/**
 * <p>This method retrieves all Interviews
 * & its used for Rest</p>
 * @return list of Interviews
 */
public ArrayList getAllInterview() {

	log.log(Level.INFO, "InterviewMaintanence --> getAllInterview");
	ArrayList arrActivity = new ArrayList();
	Session session = null;

	try {

		session = HibernateFactory.openSession();

		Query query = session
				.createQuery(" from Interview where status = 1 ");
		arrActivity = (ArrayList) query.list();

	} catch (Exception e) {
		log.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();
	} finally {
		HibernateFactory.close(session);
	}

	return arrActivity;
}



public static final KeyObjPair MONTH_VALUE_PAIR[] = {
    new KeyObjPair("01", "January"), new KeyObjPair("02", "February"), new KeyObjPair("03", "March"), new KeyObjPair("04", "April"), new KeyObjPair("05", "May"), new KeyObjPair("06", "June"), new KeyObjPair("07", "July"), new KeyObjPair("08", "August"), new KeyObjPair("09", "September"), new KeyObjPair("10", "October"), 
    new KeyObjPair("11", "November"), new KeyObjPair("12", "December")
};


/**
 * <p>Method is used to get all Interview for Rest service</p>
 * @return  ArrayList of Interview
 */
public ArrayList getAllInterviewRest(AamData aamData,String agentId, String candidateCode) {
	// TODO Auto-generated method stub
	log.log(Level.SEVERE,"InteriewMaintenance -->  getAllInterviewRest ");
	Session session = null;
	ArrayList interviewList = new ArrayList();
	ArrayList interviewListAddAll = new ArrayList();
	 List<InterviewCandidate> list1;
	 Interview interview;
	 
	try{
		
		session = HibernateFactory.openSession();
		session.setDefaultReadOnly(true);
		Date sdate=new Date();
		log.log(Level.SEVERE,"Start Time "+sdate.getTime());

		Criteria crit ;
		Date now=new Date();
		Query query=session.createQuery("FROM Interview where status = 1 "
				+ "  AND (interviewDate > :interviewDate OR ( interviewDate = :interviewDate AND StartTime > :startTime ))"
				+ " AND ( (buCode=:bucode  and district=0) "
				+ "or (buCode=:bucode and district=:distcode and  branchCode=0)"
				+ "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and  cityCode='0')"
				+ "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode='0')"
				+ "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode=:ssccode and officeCode = '0')"
				+ "or (buCode=:bucode and district=:distcode and branchCode=:branchCode and cityCode=:citycode and sscCode=:ssccode and officeCode =:officeCode ) )");
		
	ImoUtilityData utilityData=new ImoUtilityData();
	String branchName=utilityData.getBranchNameBaseonCode(aamData.getBranchCode());
	ArrayList<Branch> branchlist= utilityData.getBranchCodeListBasedOnBranchName(branchName);
	
	if(branchlist.size()>0){

		for(Branch bracnh:branchlist){
			
			District dist=utilityData.getDistrictCodeBucode(bracnh.getDistCode());
			aamData.setBranchCode(bracnh.getBranchCode());
			aamData.setDistrictCode(dist.getDistrictCode());
			aamData.setBuCode(dist.getBuCode());			
			
		
			
		query.setParameter("startTime", LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));
		query.setParameter("interviewDate",sdf1.parse(sdf1.format(now)));
		query.setParameter("bucode",aamData.getBuCode());
		query.setParameter("distcode",aamData.getDistrictCode());
		query.setParameter("branchCode",aamData.getBranchCode());
		query.setParameter("citycode", aamData.getCity());
		query.setParameter("ssccode", aamData.getSsc());
		query.setParameter("officeCode",aamData.getOfficeCode());
		interviewList=(ArrayList<Interview>) query.setCacheable(true).list();
		
		
		if(!"".equals(candidateCode)){
			   for(Iterator iterator=interviewList.iterator();iterator.hasNext(); ){
			    interview = (Interview) iterator.next();
			    
			    
			    crit = session.createCriteria(InterviewCandidate.class);
			    crit.add(Restrictions.eq("interviewCode", interview.getInterview_code()));
			    crit.add(Restrictions.eq("interviewCandidateCode", candidateCode));
			    crit.add(Restrictions.eq("status", true));
			    list1=(ArrayList<InterviewCandidate>) crit.list();
			    if(!list1.isEmpty()){
			     interview.setIsRegistered(true);
			    }else{
			     interview.setIsRegistered(false);
			    }

			   }
			}
		
		interviewListAddAll.addAll(interviewList);
		
		
		} // branchCode for loop
		
	}else{
		
		query.setParameter("startTime", LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));
		query.setParameter("interviewDate",sdf1.parse(sdf1.format(now)));
		query.setParameter("bucode",aamData.getBuCode());
		query.setParameter("distcode",aamData.getDistrictCode());
		query.setParameter("branchCode",aamData.getBranchCode());
		query.setParameter("citycode", aamData.getCity());
		query.setParameter("ssccode", aamData.getSsc());
		query.setParameter("officeCode",aamData.getOfficeCode());
		interviewList=(ArrayList<Interview>) query.setCacheable(true).list();
		
		
		if(!"".equals(candidateCode)){
			   for(Iterator iterator=interviewList.iterator();iterator.hasNext(); ){
			    interview = (Interview) iterator.next();
			    
			    
			    crit = session.createCriteria(InterviewCandidate.class);
			    crit.add(Restrictions.eq("interviewCode", interview.getInterview_code()));
			    crit.add(Restrictions.eq("interviewCandidateCode", candidateCode));
			    crit.add(Restrictions.eq("status", true));
			    list1=(ArrayList<InterviewCandidate>) crit.list();
			    if(!list1.isEmpty()){
			     interview.setIsRegistered(true);
			    }else{
			     interview.setIsRegistered(false);
			    }

			   }
			}
		
		interviewListAddAll.addAll(interviewList);
	}
		
		
		
		
		Date edate=new Date();
		log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
	
	return interviewListAddAll;
	}

/**
 * <p>Method is used to get all Deleted Interview for Rest service</p>
 * @return  ArrayList of Interview
 */
public ArrayList getAllDeletedInterviewRest() {
	// TODO Auto-generated method stub
	log.log(Level.SEVERE,"InteriewMaintenance -->  getAllDeletedInterviewRest ");
	Session session = null;
	ArrayList interviewList = new ArrayList();
	 
	try{
		
		session = HibernateFactory.openSession();
		session.setDefaultReadOnly(true);
		Date sdate=new Date();
		log.log(Level.SEVERE,"Start Time "+sdate.getTime());
		
		Query query=session.createQuery("SELECT interview_code FROM Interview where status = 0 ");
		interviewList=(ArrayList<Interview>) query.setCacheable(true).list();
		
		
		Date edate=new Date();
		log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
	
	return interviewList;
	}

/**
 * <p>Method is used to get all Interview for Rest service</p>
 * @return  ArrayList of Interview
 */
public ArrayList getAllInterviewRestPast(String agentId, String candidateCode) {
	// TODO Auto-generated method stub
	log.log(Level.SEVERE,"InteriewMaintenance -->  getAllInterviewRestPast ");
	Session session = null;
	ArrayList interviewList = new ArrayList();
	 
	try{
		
		session = HibernateFactory.openSession();
		session.setDefaultReadOnly(true);
		Date sdate=new Date();
		log.log(Level.SEVERE,"Start Time "+sdate.getTime());
		
		Date now=new Date();

		Query query=session.createQuery("select distinct i FROM Interview i,InterviewCandidate ic where i.status = 1 "
				+ "  AND (i.interviewDate < :interviewDate OR ( i.interviewDate = :interviewDate AND i.StartTime <= :startTime ))"
				+ "  AND ic.status = 1 AND ic.interviewCode=i.interview_code AND ic.interviewCandidateCode = :candidateCode ");
		
		System.out.println(""+now);
		System.out.println(""+LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));
	
		query.setParameter("startTime", LMSUtil.HH_MM_SS.parse(LMSUtil.HH_MM_SS.format(now)));
		query.setParameter("interviewDate", sdf1.parse(sdf1.format(now)));
		query.setParameter("candidateCode", candidateCode);
		
		interviewList=(ArrayList<Event>) query.setCacheable(true).list();
		
		Date edate=new Date();
		log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
	
	return interviewList;
	}


/**
 * <p>This method for use cache data for interview    </p>
 * @param sscCode
 * @return ArrayList
 */
public ArrayList getInterviewRest(int sscCode) {
	// TODO Auto-generated method stub
	log.log(Level.INFO,"InteriewMaintenance -->  getInterviewRest ");
	Session session = null;
	ArrayList interviewList = new ArrayList();
	ArrayList<Interview> list = new ArrayList<Interview>();
	ArrayList<Bu> bulist=new ArrayList<Bu>();
	ArrayList<District> distlist=new ArrayList<District>();
	ArrayList<City> citylist=new ArrayList<City>();
	ArrayList<Ssc> ssclist=new ArrayList<Ssc>();
	
	try{
		
		session = HibernateFactory.openSession();
		Date sdate=new Date();
		log.log(Level.SEVERE,"Start Time "+sdate.getTime());
		
		Query query= session.createQuery("FROM Ssc where status = 1 AND sscCode=:ssccode ");
		query.setParameter("ssccode", sscCode);
		ssclist=(ArrayList<Ssc>) query.setCacheable(true).list();
		Ssc ssc=ssclist.get(0);
		
		query=session.createQuery("FROM City where status = 1 and cityCode=:citycode ");
		query.setParameter("citycode", ssc.getCityCode());
		citylist=(ArrayList<City>) query.setCacheable(true).list();
		City city=citylist.get(0);
		
		query=session.createQuery("FROM District where status = 1 and districtCode=:distcode ");
		query.setParameter("distcode", city.getBranchCode());
		distlist=(ArrayList<District>) query.setCacheable(true).list();
		District dist=distlist.get(0);
		
		query = session.createQuery("FROM Bu where status = 1 and buCode=:bucode ");		
		query.setParameter("bucode", dist.getBuCode());
		bulist=(ArrayList<Bu>) query.setCacheable(true).list();
		Bu bu=bulist.get(0);

		query=session.createQuery("FROM Interview where status = 1 and buCode=:bucode  and district=0 ");
		query.setParameter("bucode",bu.getBuCode());
		list=(ArrayList<Interview>) query.setCacheable(true).list();
		interviewList.addAll(list);
		
		query=session.createQuery("FROM Interview where status = 1  and district=:distcode and  cityCode=0 ");
		query.setParameter("distcode",dist.getDistrictCode());
		list=(ArrayList<Interview>) query.setCacheable(true).list();
		interviewList.addAll(list);
		
		query=session.createQuery("FROM Interview where status = 1  and cityCode=:citycode and sscCode=0 ");
		query.setParameter("citycode", city.getCityCode());
		list=(ArrayList<Interview>) query.setCacheable(true).list();
		interviewList.addAll(list);
		
		
		query=session.createQuery("FROM Interview where status = 1 and  sscCode=:ssccode ");
		query.setParameter("ssccode", sscCode);
		list=(ArrayList<Interview>) query.setCacheable(true).list();
		interviewList.addAll(list);
		
		Date edate=new Date();
		log.log(Level.SEVERE,"Total  Time "+(edate.getTime()- sdate.getTime()));

		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
	
	return interviewList;
	}
/**
 * <p>Method is used to get email address of candidates registered for particular Interview </p>
 * @param  code  interviewCode 
 * @return  ArrayList of email address
 */
public List<Object[]> getRegisteredEmailAddressForParticularInterview(int code){
	log.log(Level.INFO, "InterviewMaintenance -->getRegisteredEmailAddressForParticularInterview ");
	Session session = null;
	List  emailAddrs = null;
	try{
		session = HibernateFactory.openSession();
		Query selectQ = session.createQuery("select eMailId, addressCode, agentId, name, gender, qrCode, co from  AddressBook where addressCode in(select  interviewCandidateCode from InterviewCandidate where status=1 and interviewCode=:interviewCode)");
		selectQ.setParameter("interviewCode", code);
		emailAddrs =(ArrayList<String>) selectQ.list();
		
	}catch(Exception e){
		
		log.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
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
 * <p>Method is used to get Interview material based on interview code </p>
 * @param  interviewCode 
 * @return  InterviewMaterial object
 */
public InterviewMaterial getInterviewMaterial(int interviewCode)
{
	 Session session = null;
	 InterviewMaterial mat = null;
	try{
	session = HibernateFactory.openSession();
	Query selectQ = session.createQuery("select materialName,material from InterviewMaterial where interviewCode = "+interviewCode+"");
	List list = selectQ.list();
	
	if(list!=null && list.size() > 0){
			mat = new InterviewMaterial();
		   Object [] objectDoc = (Object []) list.get(0);
		   mat.setMaterialName(( String)objectDoc[0]);
		   mat.setMaterial( ( byte[] )objectDoc[1]);
	}else{log.log(Level.INFO,"InterviewMaintenance ---> getInterviewMaterial : No Material Found ");}

	}catch(Exception e)
	{
		log.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
	}finally{
		try{
			HibernateFactory.close(session);
			
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
	}
		return mat;
}
/**
 * <p>Method is used to get registered candidate for 3rd interview  </p>
 * @param  HttpServletRequest request 
 * @return  csv path as String
 */
public String  getRegisteredCandidatesFor3rdInterviewSession(HttpServletRequest request)
{
	log.log(Level.INFO,"InterviewMaintenance --> getRegisteredCandidatesFor3rdInterviewSession");
	 Session session = null;
	String csvPath = "";
	try{
	session = HibernateFactory.openSession();
	Query selectQ = session.createQuery("select I.interviewSessionName,I.interviewDate,I.StartTime,I.EndTime,IC.candidateName,IC.servicingAgent,IC.agentName,IC.buName,IC.distName,IC.branchName,IC.cityName,IC.officeName,IC.sscName,IC.agencyLeaderCode,IC.agencyLeaderName,IC.sourceOfReferal,IC.age,IC.dob,IC.gender,IC.contactNumber,IC.ccTestResult,IC.recruitmentScheme from Interview I,InterviewCandidate IC  where I.interview_code = IC.interviewCode and I.status = 1 and I.interviewType = '3rd' and  IC.status =1");
	ArrayList list = (ArrayList)selectQ.list();

	csvPath = createCsv(list,request);
	

	}catch(Exception e)
	{
		log.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
	}finally{
		try{
			HibernateFactory.close(session);
			
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
	}
	return csvPath;
}
/**
 * <p>Method is used to create csv to store registration list for 3rd interview  </p>
 * @param  HttpServletRequest request 
 * @return  csv path as String
 */
public String createCsv(ArrayList  regList,HttpServletRequest request)
{
	log.log(Level.INFO,"Csv Generation Starts");
	String xlsPath="";
	  DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	FileWriter fw=null;
   /*BufferedWriter writer=null;
    String serverFilename = request.getRealPath("/");
	 if(!serverFilename.endsWith("/"))
			serverFilename = serverFilename + File.separator;
    try{
    
	serverFilename = serverFilename + "resources" + File.separator +  "downloads" + File.separator + "csv";
 
    File dir = new File(serverFilename);
    if(!dir.exists())
    	dir.mkdirs();
    
  
	serverFilename = serverFilename +  File.separator  +df.format(new Date()) + ".csv" ;
	
    File file = new File(serverFilename);
    if(!file.exists())
    	 file.createNewFile();
 
    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
    
    writer.write("Interview Session Name|");
    writer.write("Interview Date|");
    writer.write("Start Time|");
    writer.write("End Time|");
    writer.write("Candidate Name|");
    writer.write("Servicing Agent|");
    writer.write("Agent Name|");
    writer.write("BU|");
    writer.write("District|");
    writer.write("Branch|");
    writer.write("City|");		        
    writer.write("Office|");
    writer.write("SSC|");
    writer.write("Agency Leader Code|");
    writer.write("Agency Leader Name|");
    writer.write("Source of Referral|");
    writer.write("Age|");
    writer.write("Date of Birth|");
    writer.write("Gender|");
    writer.write("Contact Number|");
    writer.write("CC Test Result|");
    writer.write("Recruitment Scheme|");
  
	writer.newLine();
    
    if(regList!=null && regList.size()>0){
    	Iterator iteObj= regList.iterator();
    	 for(int i=0;i<regList.size();i++){
			Object [] obj= (Object []) iteObj.next();
		
    		writer.write(obj[0]+"|"); //Interview Session Name
    		if(obj[1]!=null )
    			writer.write(LMSUtil.convertDateToString((Date)obj[1]) +"|"); //Interview Date
    		else
    			writer.write("|");
    		
    		if(obj[2]!=null )
    			writer.write(LMSUtil.converDateIntoHHMMAMPM((Date)obj[2]) +"|");//Start Time
    		else
    			writer.write("|");
    		if(obj[3]!=null)
    			writer.write(LMSUtil.converDateIntoHHMMAMPM((Date)obj[3]) +"|");//End Time
    		else
    			writer.write("|");
    		
    		if(obj[4]!=null)writer.write(obj[4]+"|");else writer.write("|");//Candidate Name
    		if(obj[5]!=null)writer.write(obj[5]+"|");else writer.write("|");//Servicing Agent
    		if(obj[6]!=null)writer.write(obj[6]+"|");else writer.write("|");//Agent Name
    		if(obj[7]!=null)writer.write(obj[7]+"|");else writer.write("|");//BU
    		if(obj[8]!=null)writer.write(obj[8]+"|");else writer.write("|");//District
    		if(obj[9]!=null)writer.write(obj[9]+"|");else writer.write("|");//Branch
    		if(obj[10]!=null)writer.write(obj[10]+"|");else writer.write("|");//City
    		if(obj[11]!=null)writer.write(obj[11]+"|");else writer.write("|");//Office
    		if(obj[12]!=null)writer.write(obj[12]+"|");else writer.write("|");//SSC
    		if(obj[13]!=null)writer.write(obj[13]+"|");else writer.write("|");//Agency Leader Code
    		if(obj[14]!=null)writer.write(obj[14]+"|");else writer.write("|");//Agency Leader Name
    		if(obj[15]!=null)writer.write(obj[15]+"|");else writer.write("|");//Source of Referral
    		if(obj[16]!=null)writer.write(obj[16]+"|");else writer.write("|");//Age
    		if(obj[17]!=null )
    			writer.write(LMSUtil.convertDateToString((Date)obj[17]) +"|");//DOB
    		else
    			writer.write("|");
    		String gender = "";
    		if(obj[18]!=null){
    			if("F".equalsIgnoreCase((String)obj[18]))
    				gender = "Female";
    			else if("M".equalsIgnoreCase((String)obj[18]))//Gender
    				gender = "Male";
    		}
    		writer.write(gender+"|");
    		if(obj[19]!=null)writer.write(obj[19]+"|");else writer.write("|");//Contact Number
    		if(obj[20]!=null)writer.write(obj[20]+"|");else writer.write("|");//CC Test Result
    		if(obj[21]!=null)writer.write(obj[21]+"|");else writer.write("|");//Recruitment Scheme
    		
    		writer.newLine();
    		
    	}
    	 
    	 
    	 
    }else{
    	log.log(Level.INFO,"InterviewMaintenace - > createCsv : No candidate registration for 3rd interview");
    }
   
    }catch(Exception e){
    	log.log(Level.SEVERE, e.getMessage());
    	e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
    }finally{
			try{
				if(writer != null){
				    writer.flush();
			}
				if(writer != null){
					writer.close();
				}
				if(fw!=null)
					fw.close();
				
			}catch(Exception ioExc){
				log.log(Level.SEVERE, ioExc.getMessage());
				ioExc.printStackTrace();
				
			}
	}
    */
    
    	ArrayList arList=null;
		ArrayList al=null;
		String thisLine;
		int count=0;
		
		
		try
	    {
			
			xlsPath=request.getRealPath("/")+"resources" + File.separator +  "downloads" + File.separator + "xls";
	    		
			File dir = new File(xlsPath);
		    if(!dir.exists())
		    	dir.mkdirs();  //df.format(new Date())
		   xlsPath = xlsPath +  File.separator  +df.format(new Date()) + ".xls" ;
			
		HSSFWorkbook hwb = new HSSFWorkbook();
	    HSSFSheet sheet = (HSSFSheet) hwb.createSheet("Sheet1");
	    int rownum = 0;
	    HSSFRow row = sheet.createRow(rownum++);
	    int cellnum = 0;
	    HSSFCell cell = row.createCell((short) cellnum++);
	    
         cell.setCellValue("é�¢è¯• ä¼šè¯�");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("é�¢è¯• æ—¥æœŸ");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("å¼€å§‹  æ—¶é—´");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("ç»“æ�Ÿ  æ—¶é—´");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("å€™é€‰äºº  å��å­—");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("å¼•è��äºº");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("å¼•è��äººå��å­—");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("åˆ†å…¬å�¸");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("åœ°åŒº");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("æ”¯å…¬å�¸");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("åŸŽå¸‚");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("è�¥ç®¡å¤„");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("SSC");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("æ‰€å±žä¸šåŠ¡ç»„");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("ä¸»ç®¡å§“å��");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("æ�¥æº�  æ¸ é�“");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("Age");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("ç”Ÿæ—¥");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("æ€§åˆ«");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("è�”ç»œ  å�·ç �");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("CC æµ‹è¯•  ç»“æžœ");
         cell = row.createCell((short) cellnum++);
         cell.setCellValue("äººæ‰�è®¡åˆ’");
         cell = row.createCell((short) cellnum++);
         
         
         
         
         if(regList!=null && regList.size()>0){
         	Iterator iteObj= regList.iterator();
         	 for(int i=0;i<regList.size();i++){
         		row = sheet.createRow(rownum++);
         		cellnum=0;
     			Object [] obj= (Object []) iteObj.next();
     		
         		//writer.write(obj[0]+"|"); //Interview Session Name
     			if(obj[0] instanceof String)
     				cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[0]);
     				
         		
         		if(obj[1]!=null ){
         			//writer.write(LMSUtil.convertDateToString((Date)obj[1]) +"|"); //Interview Date
         			if(obj[1] instanceof Date) {
         				cell = row.createCell((short) cellnum++);
         				cell.setCellValue(LMSUtil.convertDateToString((Date)obj[1]));
         			}
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		if(obj[2]!=null ){
         			//writer.write(LMSUtil.converDateIntoHHMMAMPM((Date)obj[2]) +"|");//Start Time
         			if(obj[2] instanceof Date) {
         				cell = row.createCell((short) cellnum++);
         				cell.setCellValue(LMSUtil.converDateIntoHHMMAMPM((Date)obj[2]));
         			}
         		}else{
         			//writer.write("|");
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		if(obj[3]!=null){
         			//writer.write(LMSUtil.converDateIntoHHMMAMPM((Date)obj[3]) +"|");//End Time
         			if(obj[3] instanceof Date) {
         				cell = row.createCell((short) cellnum++);
         				cell.setCellValue(LMSUtil.converDateIntoHHMMAMPM((Date)obj[3]));
         			}
         		}else{
         			//writer.write("|");
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         		if(obj[4]!=null){
         			//writer.write(obj[4]+"|");//Candidate Name
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[4]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         		if(obj[5]!=null){
         			//Servicing Agent
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[5]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         		if(obj[6]!=null){
         			//Agent Name
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[6]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         		if(obj[7]!=null){
         			//BU
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[7]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         		if(obj[8]!=null){
         			//District
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[8]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         		if(obj[9]!=null){
         			//Branch
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[9]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         		if(obj[10]!=null){
         			//City
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[10]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		if(obj[11]!=null){
         			//Office
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[11]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		if(obj[12]!=null){
         			//SSC
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[12]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		if(obj[13]!=null){
         			//Agency Leader Code
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[13]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		if(obj[14]!=null){
         			//Agency Leader Name
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[14]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         		if(obj[15]!=null){
         			//Source of Referral
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[15]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		if(obj[16]!=null){
         			//Age
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((Integer)obj[16]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         		if(obj[17]!=null ){
         			//writer.write(LMSUtil.convertDateToString((Date)obj[17]) +"|");//DOB
         			if(obj[3] instanceof Date) {
         				cell = row.createCell((short) cellnum++);
         				cell.setCellValue(LMSUtil.convertDateToString((Date)obj[17]));
         			}
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         		
         		String gender = "";
         		if(obj[18]!=null){
         			if("F".equalsIgnoreCase((String)obj[18]))
         				gender = "Female";
         			else if("M".equalsIgnoreCase((String)obj[18]))//Gender
         				gender = "Male";
         		}
         		cell = row.createCell((short) cellnum++);
 				cell.setCellValue(gender);
         		
         		
 				if(obj[19]!=null){
 					//Contact Number
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[19]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
 				
 				if(obj[20]!=null){
 					//CC Test Result
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[20]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
 				if(obj[21]!=null){
 					//Recruitment Scheme
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue((String)obj[21]);
         			
         		}else{
         			cell = row.createCell((short) cellnum++);
     				cell.setCellValue("");
         		}
         		
         	}
        
         }
	    
   
    		FileOutputStream fileOut = new FileOutputStream(xlsPath);
    		hwb.write(fileOut);
    		fileOut.close();
    		System.out.println("Your excel file has been generated");
	    } catch ( Exception ex ) {
	    	ex.printStackTrace();
		} //main method ends
	
		
	 
 
    return xlsPath;
}

/**
 * <p>Method is used to Download material of interview   </p>
 * @param  HttpServletRequest request 
 * 
 */


public  void downloadFile(String filepath,HttpServletRequest req,HttpServletResponse resp){
	log.log(Level.INFO, "Inside Download");
    ServletOutputStream stream = null;
    BufferedInputStream buf = null;
		 try{
	            stream = resp.getOutputStream();
	            String separator = System.getProperty("file.separator");
	           
	            File file = new File(filepath);
	            if(file.exists()){
	                resp.addHeader("Content-Disposition", "attachment; filename=" +filepath.substring(filepath.lastIndexOf(separator)+1,filepath.length()));
	                resp.setContentLength((int)file.length());
	                FileInputStream input = new FileInputStream(file);
	                buf = new BufferedInputStream(input);
	                int readBytes = 0;
	                while ((readBytes = buf.read()) != -1)
	                	stream.write(readBytes);
	                }  
		 }catch(Exception e){
			 log.log(Level.SEVERE, e.getMessage());
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
		  }finally{
			  try{
		    		if (stream != null)
		    			stream.close();
		    		if (buf != null)
		    			buf.close();
		    	}catch(Exception e){log.log(Level.SEVERE, e.getMessage());}
		     }
		

	}


/**
 * <p>Method is used to retrive materil file for interview   </p>
 * @param  HttpServletRequest request 
 * @return  File path
 */
public   String  getmaterialFile(Interview interview,HttpServletRequest req)
{
	log.log(Level.INFO,"AnnouncementMaintenance ---> getmaterialFile ");
	 Session session = null;
	 InterviewMaterial material=null;
	String path="#";
	try{
		session = HibernateFactory.openSession();
		 Criteria criteria = session.createCriteria(InterviewMaterial.class);
		 criteria.add(Restrictions.eq("interviewCode", interview.getInterview_code()));
		 ArrayList list = (ArrayList) criteria.list();
		
		 if(list.size()>0){
			 material=(InterviewMaterial)list.get(0);
			 String fname = interview.getAttachmentPath().substring(interview.getAttachmentPath().lastIndexOf('/') + 1);
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
		e.printStackTrace();
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
public InterviewCandidate getInterviewCandidateCode(int interview_code) {
	// TODO Auto-generated method stub
	log.log(Level.INFO,"InterviewMaintenance ---> getInterviewCandidateCode ");
	 Session session = null;
	 InterviewCandidate candidate=null;
	
		try{
			session = HibernateFactory.openSession();
			 Criteria criteria = session.createCriteria(InterviewCandidate.class);
			 criteria.add(Restrictions.eq("interviewCode", interview_code));
			 ArrayList list = (ArrayList) criteria.list();
			
			 if(list.size()>0){
				candidate=(InterviewCandidate) list.get(0);
			 }
			 
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
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
public String getRegisteredCandidatesFor2ndInterviewSession(
		HttpServletRequest request) {
	// TODO Auto-generated method stub

	log.log(Level.INFO,"InterviewMaintenance --> getRegisteredCandidatesFor2ndInterviewSession");
	 Session session = null;
	String csvPath = "";
	try{
	session = HibernateFactory.openSession();
	Query selectQ = session.createQuery("select I.interviewSessionName,I.interviewDate,I.StartTime,I.EndTime,IC.candidateName,IC.servicingAgent,IC.agentName,IC.buName,IC.distName,IC.branchName,IC.cityName,IC.officeName,IC.sscName,IC.agencyLeaderCode,IC.agencyLeaderName,IC.sourceOfReferal,IC.age,IC.dob,IC.gender,IC.contactNumber,IC.ccTestResult,IC.recruitmentScheme from Interview I,InterviewCandidate IC  where I.interview_code = IC.interviewCode and I.status = 1 and I.interviewType = '2nd' and  IC.status =1");
	ArrayList list = (ArrayList)selectQ.list();

	csvPath = createCsv(list,request);
	

	}catch(Exception e)
	{
		log.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("InterViewMaintenance",Level.SEVERE+"",errors.toString());
	}finally{
		try{
			HibernateFactory.close(session);
			
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
	}
	return csvPath;

}
public int getBranchCode(int interviewCode) {
	// TODO Auto-generated method stub
	log.log(Level.INFO,"InterviewMaintenance --> getBranchCode");
	Session session = null;
	 int code=0;
		try{
			session = HibernateFactory.openSession();
			Interview interview = (Interview) session.get(Interview.class,interviewCode) ;
			if(interview!=null){
				 code=interview.getBranchCode();
			}
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			LogsMaintenance logsMain=new LogsMaintenance();e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("InterviewMaintenance",Level.SEVERE+"",errors.toString());
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
public   String  getmaterialFile(CandidateESignature candidateESignature,HttpServletRequest req)
{
	log.log(Level.INFO,"InterviewMaintenance ---> getmaterialFile ");
	 Session session = null;
	 CandidateESignature material=null;
	// ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
    String url  = "";//msgProps.getString("APP_URL");
	String path="#";
	LogsMaintenance logsMain=new LogsMaintenance();
	try{
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
		     
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 Date d1=new Date();
			 String time=(d1.getTime()+"").trim();
			 String str=df.format(new Date())+time;
		     path += File.separator + "signature_" + str + ".png";
			 File file=new File(path);
			 if(!file.exists()){
				 file.createNewFile();
			 }
			 
			 FileOutputStream stream = new FileOutputStream(path);
			 if(candidateESignature.geteSignaturePhoto()!=null){
				 stream.write(candidateESignature.geteSignaturePhoto());
				 stream.close();
				 path="resources" + File.separator+ "material"+File.separator+"signature_" + str + ".png";;
				 url = "resources/" +"material/"+"signature_" + str + ".png";
				 				 
				 logsMain.insertLogs("InterviewMaintenance",Level.SEVERE+"",url);
			 }else{
				url="#"; 
			 }
			 
			 

	}catch(Exception e)
	{
		log.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("InterviewMaintenance",Level.SEVERE+"",errors.toString());
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

//* ------------------------------------------------------------------------
public void resyncInterview(int interview_code, HttpServletRequest req)
{
    log.log(Level.INFO, "Interview Maintenance --> Resync Interview");

    MsgObject msgObj = null;
    User userObj = (User) req.getSession().getAttribute("currUserObj");

    Interview interview = getInterviewBasedOnInterviewCode(interview_code);

    boolean success = false;

    if (interview != null)
    {
        if (interview.isStatus() == true)
        {
            // is not deleted, re-update to iCalendar
            iCalendarInterviewHelper helper = new iCalendarInterviewHelper();
            if(!"".equals(userObj.getSsoSessionId())){
            	success = helper.updateInterview(interview_code, userObj.getSsoSessionId());
            }
        }
        else
        {
            // is deleted, re-delete from iCalendar
            iCalendarInterviewHelper helper = new iCalendarInterviewHelper();
            success = helper.deleteInterview(interview_code, userObj.getSsoSessionId());
        }
    }

    if (success)
    {
        msgObj = new MsgObject("Interview Resynchronized to iCalendar");
    }
    else
    {
        msgObj = new MsgObject("Interview Resynchronization Failed.");
    }

    // Codes copied from delete interview, is to redisplay the interview maintenance table
    req.setAttribute("messageObject", msgObj);
    req.setAttribute("CacheName", "Interview");
    Pager pager = getAllinterview(req);
    req.getSession().setAttribute("pager", pager);
    req.setAttribute("pager", pager);
}
}
