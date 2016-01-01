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
* 16-June-2015       Nibedita          candidate add/update/delete/listing
* 17-June-2015       Nibedita          Download excel code added
* 18-June-2015       Nibedita          condition added for independent registration

****************************************** *********************************** */
package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.constants.ApplicationAttribute;
import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.event.EventCandidate;
import com.quix.aia.cn.imo.data.event.EventMaterial;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.EmailNotification;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.ExcelGenerator;
import com.quix.aia.cn.imo.utilities.ImoUtilityData;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;
/**
 * <p>Logic to create,update,delete,search Candidate.</p>
 * 
 * @author Nibedita
 * @version 1.0
 *
 */
public class EopAttendanceMaintenance {
	static Logger log = Logger.getLogger(EopAttendanceMaintenance.class.getName());
	/**
	  * Constructor
	  * Hibernate configuration done
	  */
	public EopAttendanceMaintenance(){
		HibernateFactory.buildIfNeeded();
	}
	 /**
	  * <p>Method takes care to map  fields.</p>
	  * @param candidate  EventCandidate object  
	  * @param requestParameters Servlet Request Parameter
	  * @return EventCandidate Object/Error object 
	  */
	public Object mapForm1(EventCandidate candidate, HttpServletRequest requestParameters)
	{
		log.log(Level.INFO,"EopAttendanceMaintenance --> mapForm1 ");
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
	
		if(requestParameters.getParameter("candidateName") == null)
			candidate = null;
		if(candidate == null)
		{
			if(localeObj == null){
				String errorMsg = validatePublicRegistration(requestParameters);
				if(errorMsg.length() > 0){
					requestParameters.setAttribute("CANDIDATE_REG_MSG", errorMsg);
					return candidate;
				}
			}
			candidate = new EventCandidate();
			return candidate;
		}
		
		Map<String,String> mapFields = new HashMap<String,String>();
		mapFields.put("C_NAME", requestParameters.getParameter("candidateName"));
		mapFields.put("S_AGENT", requestParameters.getParameter("servicingAgent"));
		mapFields.put("A_NAME", requestParameters.getParameter("agentName"));
		mapFields.put("BU", requestParameters.getParameter("bu"));
		mapFields.put("LEADER_CODE", requestParameters.getParameter("agencyLeaderCode"));
		mapFields.put("LEADER_NAME", requestParameters.getParameter("agencyLeaderName"));
		mapFields.put("S_REF", requestParameters.getParameter("sourceOfReferal"));
		mapFields.put("AGE", requestParameters.getParameter("age"));
		mapFields.put("DOB", requestParameters.getParameter("dob"));
		mapFields.put("GENDER", requestParameters.getParameter("gender"));
		mapFields.put("C_NUMBER", requestParameters.getParameter("contactNumber"));
		mapFields.put("EVENT_CODE", requestParameters.getParameter("eCode"));
		mapFields.put("C_CODE", String.valueOf(candidate.getCandidateCode()));
		mapFields.put("E_MAIL", requestParameters.getParameter("mailId"));
		
		if(localeObj != null){
			if(requestParameters.getParameter("servicingAgent")!=null && requestParameters.getParameter("servicingAgent").length() > 0){
				Integer id = AamDataMaintenance.checkIfAgentExist(requestParameters.getParameter("servicingAgent"), requestParameters.getParameter("co"));
				if(id == 0)
					return new ErrorObject("" + "<br>", "Servicing Agent is not valid",localeObj);
			}
		}
		String errMessage = validateFields(mapFields,localeObj);
		if(errMessage.length() > 0){
			return new ErrorObject("" + "<br>", errMessage,localeObj);
		}
		candidate.setCandidateName(requestParameters.getParameter("candidateName"));
		candidate.setServicingAgent(requestParameters.getParameter("servicingAgent"));
		candidate.setAgentName(requestParameters.getParameter("agentName"));
	/*	candidate.setBuCode(Integer.parseInt(requestParameters.getParameter("bu")));
		candidate.setDistrictCode(Integer.parseInt(requestParameters.getParameter("district")));
		candidate.setBranchCode(Integer.parseInt(requestParameters.getParameter("branch")));
		candidate.setCityCode(Integer.parseInt(requestParameters.getParameter("city")));
		candidate.setOfficeCode(Integer.parseInt(requestParameters.getParameter("office")));
		candidate.setSscCode(Integer.parseInt(requestParameters.getParameter("ssc")));
		candidate.setAgencyLeaderCode(requestParameters.getParameter("agencyLeaderCode"));
		candidate.setAgencyLeaderName(requestParameters.getParameter("agencyLeaderName"));
		candidate.setSourceOfReferal(requestParameters.getParameter("sourceOfReferal"));*/
		candidate.setAge(Integer.parseInt( requestParameters.getParameter("age")));
		candidate.setDob(LMSUtil.convertStringToDate(requestParameters.getParameter("dob")));
		candidate.setGender(requestParameters.getParameter("gender"));
		candidate.setContactNumber(requestParameters.getParameter("contactNumber"));
		candidate.setEventCode(Integer.parseInt(requestParameters.getParameter("eCode")));
		candidate.setCandidateCode(candidate.getCandidateCode());
		candidate.setNric(requestParameters.getParameter("nric"));
		candidate.setMailId(requestParameters.getParameter("mailId"));
		candidate.setWeChat(requestParameters.getParameter("weChatID"));
		candidate.setEducation(requestParameters.getParameter("education"));
		candidate.setStatus(true);
		AddressBook addressBook = new AddressBook();
		addressBook.setAgentId(candidate.getServicingAgent());
		addressBook.setBirthDate(candidate.getDob());
		addressBook.setGender(candidate.getGender());
		addressBook.setName(candidate.getCandidateName());
		addressBook.setCo(requestParameters.getParameter("co"));
	    Integer key = new AddressBookMaintenance().getAddressBookIfExist(addressBook);
	    if(localeObj == null){
	    	if(key != 0){
			    if(checkDuplicateCandiadteReg(mapFields.get("EVENT_CODE"),mapFields.get("S_AGENT"),String.valueOf(key))){
			    	return new ErrorObject("" + "<br>", "候选人已注册",localeObj);
				}
	    	}
	    }else{
	    	 if(checkDuplicateReg(mapFields.get("EVENT_CODE"),mapFields.get("S_AGENT"),String.valueOf(key),mapFields.get("C_CODE"))){
			    	return new ErrorObject("" + "<br>", "Candidate Already Registered",localeObj);
				}
	    }
		return candidate;
	}
	/**
	 * <p>Validation done</p>
	 * @param mapFields
	 * @return String all validation error
	 */
	public String validateFields(Map<String,String> mapFields,LocaleObject localeObj ){
		String errMsg = "";
		if(mapFields!=null && mapFields.size() > 0){
			
			if(mapFields.get("C_NAME") == null || (mapFields.get("C_NAME") != null && mapFields.get("C_NAME").length() == 0))
				if(localeObj !=null)
					errMsg += localeObj.getTranslatedText("Candidate Name Required") + "<br>";
				else
					errMsg += "候选名称必需" + "<br>";
					
			
			if(mapFields.get("S_AGENT") == null || (mapFields.get("S_AGENT") != null  && mapFields.get("S_AGENT").length() == 0))
				if(localeObj !=null)
					errMsg += localeObj.getTranslatedText("Servicing Agent Required") + "<br>";
				else
					errMsg +="服务代理要求" + "<br>";
					
		
			if(mapFields.get("A_NAME") == null || (mapFields.get("A_NAME") != null && mapFields.get("A_NAME").length() == 0))
				if(localeObj !=null)
					errMsg += localeObj.getTranslatedText("Agent Name Required") + "<br>";
				else
					errMsg += "代理名称必需" + "<br>";
			
	/*		if(mapFields.get("BU") == null || (mapFields.get("BU") != null && (mapFields.get("BU").equals("0") || mapFields.get("BU").length() == 0)))
				errMsg += "BU Required" + "<br>";
			
			if(mapFields.get("LEADER_CODE") == null || (mapFields.get("LEADER_CODE") != null && mapFields.get("LEADER_CODE").length() == 0))
				errMsg += "Agency Leader Code Required" + "<br>";
			
			if(mapFields.get("LEADER_NAME") == null || (mapFields.get("LEADER_NAME") != null && mapFields.get("LEADER_NAME").length() == 0))
				errMsg += "Agency Leader Name Required" + "<br>";
			
			if(mapFields.get("S_REF") == null || (mapFields.get("S_REF") != null && mapFields.get("S_REF").length() == 0))
				errMsg += "Source Of Referal Required" + "<br>";*/
			
			/*if(mapFields.get("AGE") == null || (mapFields.get("AGE") != null && mapFields.get("AGE").length() == 0 ))
				errMsg += "AGE Required" + "<br>";*/
			
			if(mapFields.get("DOB") == null || (mapFields.get("DOB") != null  && mapFields.get("DOB").length() == 0 ))
				if(localeObj !=null)
					errMsg += localeObj.getTranslatedText("Date of Birth Required") + "<br>";
				else
					errMsg += "出生日期必填" + "<br>";
			
			if(mapFields.get("GENDER") == null || (mapFields.get("GENDER") != null  && (mapFields.get("GENDER").length() == 0 || mapFields.get("GENDER").equals("1"))))
				if(localeObj !=null)
					errMsg += localeObj.getTranslatedText("Gender Required") + "<br>";
				else
					errMsg += "性别 必填" + "<br>";
			if(localeObj ==null){
				if(mapFields.get("C_NUMBER") == null || (mapFields.get("C_NUMBER") != null && mapFields.get("C_NUMBER").length() == 0))
					//errMsg += "Contact Number Required" + "<br>";
					errMsg += "联系电话必填" + "<br>";
			}
		
		/*	if(mapFields.get("AGE") != null && mapFields.get("AGE").length() > 0 ){
				if(!LMSUtil.validInt((mapFields.get("AGE"))))
					errMsg += "Age Should Be Integer " + "<br>";
			}*/
			
			
			if(mapFields.get("DOB") != null  && mapFields.get("DOB").length() > 0 ){
				LMSUtil lmsUtil = new LMSUtil();
				if(!lmsUtil.validateDDMMYYYY(mapFields.get("DOB"))){
					errMsg += localeObj.getTranslatedText("Date of Birth Format Wrong.Should be in DD/MM/YYYY") + "<br>";
			}
			
			}
			if(localeObj ==null){
				if(mapFields.get("E_MAIL") == null || (mapFields.get("E_MAIL") != null && mapFields.get("E_MAIL").length() == 0))
					errMsg += "电子邮件必填" + "<br>";
			}
			if(mapFields.get("E_MAIL") != null  && mapFields.get("E_MAIL").length() > 0 ){
				 if(!LMSUtil.emailValidation(mapFields.get("E_MAIL"))){
				if(localeObj !=null)
					 errMsg += localeObj.getTranslatedText("Email Should Be Valid") + "<br>";
				else
					 errMsg += "电子邮件应该是有效的 " + "<br>";
				 }
			}
			/*if(mapFields.get("C_NUMBER") != null && mapFields.get("C_NUMBER").length() > 0){
			 if(!LMSUtil.validatePhoneNumber(mapFields.get("C_NUMBER"))){
				 errMsg += "Please Enter 10 or 12 Digit Contact Number " + "<br>";
			 }}*/
			
			
		}
		
		
		return errMsg;
	}
	/**
	 *<p>New Candidate Registration</p>
	 * @param candidate
	 * @param requestParameters
	 * @return
	 */
	public Object createNewCandidate(EventCandidate candidate, HttpServletRequest requestParameters)
	{
		 log.log(Level.INFO,"---EopMaintenance Creation--- ");
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		 MsgObject msgObj = null;
		 EopMaintenance eventMain=new EopMaintenance();
		/* int branchCode=eventMain.getBranchNameBaseonCode(candidate.getEventCode());
		 ImoUtilityData imoutill=new ImoUtilityData();
		 String branchName=imoutill.getBranchNameBaseonCode(branchCode);*/
		AddressBook addressBook = new AddressBook();
		addressBook.setAgentId(candidate.getServicingAgent());
		addressBook.setBirthDate(candidate.getDob());
		addressBook.setName(candidate.getCandidateName());
		addressBook.setAge(candidate.getAge());
		addressBook.setGender(candidate.getGender());
		addressBook.setEducation(candidate.getEducation());
		addressBook.seteMailId(candidate.getMailId());
		addressBook.setWeChat(candidate.getWeChat());
		addressBook.setNric(candidate.getNric());
		addressBook.setMobilePhoneNo(candidate.getContactNumber());
		//addressBook.setCo(branchName);
		AddressBook addressBookObj = new AddressBookMaintenance().insertAddressBook(addressBook,requestParameters);
		candidate.setEventCandidateCode(""+addressBookObj.getAddressCode());
		
		AamData aamData = AamDataMaintenance.retrieveDataToModel(candidate.getServicingAgent(),null); 
		candidate.setAgencyLeaderCode(aamData.getLeaderCode()!=null ? aamData.getLeaderCode() : "");
		candidate.setAgencyLeaderName(AamDataMaintenance.retrieveAgentName(aamData.getLeaderCode(), aamData.getBranch())); 
		candidate.setSscCode(aamData.getSscCode()+"");
		candidate.setCityCode(aamData.getCityCode()+"");
		candidate.setOfficeCode(aamData.getOfficeCode());
		candidate.setBranchCode(aamData.getBranchCode());
		candidate.setDistrictCode(aamData.getDistrictCode());
		candidate.setBuCode(aamData.getBuCode());
		int status = insertNewCandidate(candidate);
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		if(status !=0){
			msgObj = new MsgObject("Candidate Added Successfully .");
			  if(candidate.getMailId()!=null && candidate.getMailId().length()>0){
		        	EmailNotification.sendEopRegEmailNotification(candidate,candidate.getMailId(), aamData);
		        }
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent(), AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
				requestParameters.setAttribute("CANDIDATE_REG_MSG", "恭喜您，活动报名成功。我们会发送二维码门票到您注册时的邮箱。");
				return candidate;
			}
		}
		else{
			msgObj = new MsgObject("Sorry.Candidate Could Not be Added.");
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent(), AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
				requestParameters.setAttribute("CANDIDATE_REG_MSG", "活动报名是不是成功");
				return candidate;
			}
				
		}
		
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.getSession().setAttribute("pager", eopAttendanceListing(requestParameters));
		
		return candidate;
	}
	/**
	 *<p>New Candidate Registration</p>
	 * @param candidate
	 * @param requestParameters
	 * @return
	 */
	public Object createNewCandidateRest(EventCandidate candidate, HttpServletRequest requestParameters,AamData aamData)
	{
		 log.log(Level.INFO,"---EopMaintenance Creation--- ");
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		 MsgObject msgObj = null;
		AddressBook addressBook = new AddressBook();
		addressBook.setCo(aamData.getBranch());
		addressBook.setAgentId(candidate.getServicingAgent());
		addressBook.setBirthDate(candidate.getDob());
		addressBook.setName(candidate.getCandidateName());
		addressBook.setAge(candidate.getAge());
		addressBook.setGender(candidate.getGender());
		addressBook.setEducation(candidate.getEducation());
		addressBook.seteMailId(candidate.getMailId());
		addressBook.setWeChat(candidate.getWeChat());
		addressBook.setNric(candidate.getNric());
		addressBook.setMobilePhoneNo(candidate.getContactNumber());

		AddressBook addressBookObj = new AddressBookMaintenance().insertAddressBook(addressBook,requestParameters);
		
		//
//		AamData aamData = AamDataMaintenance.retrieveDataToModel(candidate.getServicingAgent(), null); 
		candidate.setAgencyLeaderCode(aamData.getLeaderCode()!=null ? aamData.getLeaderCode() : "");
		candidate.setAgencyLeaderName(AamDataMaintenance.retrieveAgentName(aamData.getLeaderCode(), aamData.getBranch())); 
		candidate.setSscCode(aamData.getSsc());
		candidate.setCityCode(aamData.getCity());
		candidate.setOfficeCode(aamData.getOfficeCode());
		candidate.setBranchCode(aamData.getBranchCode());
		candidate.setDistrictCode(aamData.getDistrictCode());
		candidate.setBuCode(aamData.getBuCode());
		candidate.setEventCandidateCode(addressBookObj.getAddressCode()+"");
		
		int status = insertNewCandidate(candidate);
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		if(status !=0){
			msgObj = new MsgObject("Candidate Added Successfully .");
			  if(candidate.getMailId()!=null && candidate.getMailId().length()>0){
		        	EmailNotification.sendEopRegEmailNotification(candidate,candidate.getMailId(), aamData);
		        }
			//EmailNotification.sendEopRegEmailNotification(candidate,candidate.getMailId(), aamData);
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent(), AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
				requestParameters.setAttribute("CANDIDATE_REG_MSG", "活动报名成功");
				 
				return candidate;
			}
		}
		else{
			msgObj = new MsgObject("Sorry.Candidate Could Not be Added.");
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent(), AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
				requestParameters.setAttribute("CANDIDATE_REG_MSG", "活动报名是不是成功");
				return candidate;
			}
				
		}
		
		//requestParameters.setAttribute("messageObject", msgObj);
		//requestParameters.getSession().setAttribute("pager", eopAttendanceListing(requestParameters));
		
		return candidate;
	}
	/**
	 * <p>Insert candidate done</p>
	 * @param candidate
	 * @return
	 */
	public int insertNewCandidate(EventCandidate candidate){
		Integer key = 0;
		Session session = null;
		
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			key = (Integer)session.save(candidate);
			
			tx.commit();
		
		    log.log(Level.INFO,"---New Candidate Registered Successfully--- ");
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
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
	 * <p>Duplicate Registration checked</p>
	 * @param eventCode
	 * @param servicingAgent
	 * @param eventCandidateCode
	 * @param candidateCode
	 * @return true/false
	 */
	public boolean checkDuplicateReg(String  eventCode,String servicingAgent,String eventCandidateCode,String candidateCode){
		Session session = null;
		try{
			session = HibernateFactory.openSession();
		    Query selectQ = session.createQuery("select  candidateCode from EventCandidate  where eventCode =:eventCode and servicingAgent=:servicingAgent and eventCandidateCode=:eventCandidateCode and candidateCode<>:candidateCode and status=:status");
			selectQ.setParameter("eventCode",Integer.parseInt(eventCode));
			selectQ.setParameter("servicingAgent", servicingAgent);
			selectQ.setParameter("eventCandidateCode", Integer.parseInt(eventCandidateCode));
			selectQ.setParameter("candidateCode", Integer.parseInt(candidateCode));
		    selectQ.setParameter("status", true);

			List list = selectQ.list();
			if(list!=null && list.size() > 0)
				return true;
			else 
				return false;
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
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
	  * <p>Attendance listing done here.</p>
	  * @param req   Servlet Request Parameter
	  * @return  pager object
	  */
	@SuppressWarnings("unchecked")
	public  Pager eopAttendanceListing(HttpServletRequest req)
	{
		EopMaintenance eopMaintenance = new EopMaintenance();
		AamDataMaintenance aamDataMaintenance = new AamDataMaintenance();
		LinkedList item = new LinkedList();
		ArrayList<EventCandidate> listAllCandidates =new ArrayList<EventCandidate>();
		Event event =null;
		int eventCode = 0;
		if(req.getParameter("eventCode") != null)
	    {
			eventCode = Integer.parseInt(req.getParameter("eventCode"));
	    }else{
	    	if(req.getSession().getAttribute("EventObj")!=null)
		    	event = (Event)req.getSession().getAttribute("EventObj");
	    	eventCode = event.getEvent_code();
	    }
	    
//		ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
//		String encryptDecryptKey  = msgProps.getString("encryptDecryptKey");
		//Map<String,String> configurationMap =(Map<String,String>) req.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
	
	    	event = eopMaintenance.getEvent(eventCode);
	    	if(event.getOrganizer() == 1)
	    		event.setOraganiserStr("CHO");
			else if(event.getOrganizer() == 2)
				event.setOraganiserStr("BU");
			else if(event.getOrganizer() == 3)
				event.setOraganiserStr("DISTRICT");
			else if(event.getOrganizer() == 4)
				event.setOraganiserStr("CITY");
			else if(event.getOrganizer() == 5)
				event.setOraganiserStr("SSC");
			else if(event.getOrganizer() == 6)
				event.setOraganiserStr("Agent Team");
	    	
			else if(event.getOrganizer() == 7)
				event.setOraganiserStr("Branch");
			else if(event.getOrganizer() == 8)
				event.setOraganiserStr("Office");
			if(event.getOpenToRegistration()!=null && event.getOpenToRegistration().equals("Y")){
				event.setOpenToRegistration("Yes");
				try{
				//	String appUrl = configurationMap.get("AppUrl");
					/*ResourceBundle msgProps = ResourceBundle.getBundle("configurations");
			        String appUrl=msgProps.getString("APP_URL");*/
			        
			        Map<String,String> configurationMap =(Map<String,String>) req.getSession().getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
			        String appUrl  = configurationMap.get("APP_URL");//"E:/IMOCN/eop"
			        
			        
					String publicUrl = appUrl + "FormManager?key=EopCandidateReg&type=NEW&eventCode="+eventCode+"&agentID=";
					event.setPublicUrl(publicUrl);
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
				}
				
			}
			else{
				event.setOpenToRegistration("No");
			}
			event.setEventDateStr(event.getEventDate()!=null?LMSUtil.convertDateToString(event.getEventDate()):"");
			event.setTimeStart(LMSUtil.converDateIntoHHMMAMPM(event.getStartTime()));
			event.setTimeEnd(LMSUtil.converDateIntoHHMMAMPM(event.getEndTime()));
			
              if("eop".equals(event.getEventType()))
            	  event.setEventType("EOP");
              else if("companyevent".equals(event.getEventType()))
            	  event.setEventType("Company Event");
              else if("networking".equals(event.getEventType()))
            	  event.setEventType("Networking");
              else if("training".equals(event.getEventType()))
            	  event.setEventType("Training");
         
              event.setOpenToSpGrp("SG".equals(event.getOpenTo()) ? "Yes" : "No"); 
              if("1".equals(event.getAgentTeam()))
            		  event.setAgentTeam("Agent1");
              else if("2".equals(event.getAgentTeam()))
        		  event.setAgentTeam("Agent2");
              if(event.getProfilePath()!=null && event.getProfilePath().length() > 0){
            	  event.setProfilePath(event.getProfilePath().substring(event.getProfilePath().lastIndexOf('/') + 1));
            	 }
              if("0".equals(event.getAgentTeam())){
            	  event.setAgentTeam("");
              }
	    	
	    	EopMaintenance eventMain=new EopMaintenance();
	    	ImoUtilityData imoutill=new ImoUtilityData();
	
		EventCandidate candidate = null;
		listAllCandidates = getAttendanceList(req,event.getEvent_code());
		LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if(listAllCandidates!=null && listAllCandidates.size() > 0){
			for(int i = 0; i < listAllCandidates.size(); i++)
			{
				candidate = new EventCandidate();
				candidate = (EventCandidate)listAllCandidates.get(i);
				// int branchCode=eventMain.getBranchNameBaseonCode(candidate.getEventCode());
				 String branchName=imoutill.getBranchNameBaseonCode(candidate.getBranchCode());
                AamData aamData=aamDataMaintenance.retrieveDataToModel(candidate.getServicingAgent(), branchName); 
                //candidate.setAgencyLeaderName(AamDataMaintenance.retrieveAgentName(aamData.getLeaderCode())); 
                candidate.setAgentName(aamData.getAgentName()!=null ? aamData.getAgentName() : "");
                candidate.setAgencyLeaderCode(aamData.getLeaderCode()!=null ? aamData.getLeaderCode() : "");
                candidate.setTeamName(aamData.getTeamName()!=null ? aamData.getTeamName() : "");
                candidate.setBuName(aamData.getBu());
                candidate.setDistName(aamData.getDistrict());
                candidate.setBranchName(aamData.getBranchFulleName());
                candidate.setCityName(aamData.getCity());
                candidate.setOfficeName(aamData.getOfficeName());
                candidate.setSscName(aamData.getSsc());
				item.add(candidate.getGetAttendanceListingTableRow(localeObj));
			}
		}
		ExcelGenerator excelGenerator = new ExcelGenerator();
		String str = excelGenerator.GenerateEopRegistrationReport(listAllCandidates,event, req.getRealPath("/resources/templates/"), req.getRealPath("/resources/userFiles/"));
		if(str==null){
			event.setTempFilePath("#");
		}else{
			event.setTempFilePath(str);
		}
		req.getSession().setAttribute("EventObj", event);
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
	 * <p>get all attendance registered for particular event</p>
	 * @param req   Servlet Request Parameter
	 * @param eventCode
	 * @return List of Candidates
	 */
	public  ArrayList<EventCandidate> getAttendanceList(HttpServletRequest req,int eventCode)
	{
		 Session session = null;
			String agentId = req.getParameter("agentId");
			Boolean isRest = (Boolean) req.getAttribute("isRest");
		ArrayList<EventCandidate> attendanceList = new ArrayList<EventCandidate>();
		try{
			session = HibernateFactory.openSession();
			Criteria crit = session.createCriteria(EventCandidate.class);
			
			if(null != isRest && true == isRest){
				crit.add(Restrictions.eq("servicingAgent", agentId));
			}
			
			crit.add(Restrictions.eq("eventCode", eventCode));
			crit.add(Restrictions.eq("status", true));
			crit.addOrder(Order.desc("candidateCode"));
			attendanceList = (ArrayList)crit.list();
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		req.getSession().setAttribute("EOP_ATTENDANCE_LIST", attendanceList);
		return attendanceList;
	}
	 /**
	  * <p>Method used to get details of a particular Candidate.</p>
	  * @param candidateCode   Candidate Code
	  * @return EventCandidate Object
	  */

	public EventCandidate getCandidate(int candidateCode)
	{
		 Session session = null;;
		 EventCandidate candidate = new EventCandidate();
		try{
			session = HibernateFactory.openSession();
			candidate = (EventCandidate)session.get(EventCandidate.class,candidateCode);
	  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
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
	 /**
	  * <p>candidate update method called.Message set.Store action in audit table</p>
	  * @param candidate
	  * @param requestParameters
	  * @return EventCandidate Object
	  */
	public EventCandidate updateCandidate(EventCandidate candidate,HttpServletRequest requestParameters){
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		MsgObject msgObj  = null;
		AddressBook addressBook = new AddressBook();
		addressBook.setAgentId(candidate.getServicingAgent());
		addressBook.setBirthDate(candidate.getDob());
		addressBook.setName(candidate.getCandidateName());
		addressBook.setAge(candidate.getAge());
		addressBook.setGender(candidate.getGender());
		addressBook.setEducation(candidate.getEducation());
		addressBook.seteMailId(candidate.getMailId());
		addressBook.setWeChat(candidate.getWeChat());
		addressBook.setNric(candidate.getNric());
		addressBook.setMobilePhoneNo(candidate.getContactNumber());

		AddressBook addressBookObj = new AddressBookMaintenance().insertAddressBook(addressBook,requestParameters);
		candidate.setEventCandidateCode(""+addressBookObj.getAddressCode());
		EopMaintenance eventMain=new EopMaintenance();
    	ImoUtilityData imoutill=new ImoUtilityData();
		//int branchCode=eventMain.getBranchNameBaseonCode(candidate.getEventCode());
		 String branchName=imoutill.getBranchNameBaseonCode(candidate.getBranchCode());
		AamData aamData = AamDataMaintenance.retrieveDataToModel(candidate.getServicingAgent(), branchName); 
		candidate.setAgencyLeaderCode(aamData.getLeaderCode()!=null ? aamData.getLeaderCode() : "");
		candidate.setAgencyLeaderName(AamDataMaintenance.retrieveAgentName(aamData.getLeaderCode(), branchName)); 
		candidate.setSscCode(aamData.getSsc());
		candidate.setCityCode(aamData.getCity());
		candidate.setOfficeCode(aamData.getOfficeCode());
		candidate.setBranchCode(aamData.getBranchCode());
		candidate.setDistrictCode(aamData.getDistrictCode());
		candidate.setBuCode(aamData.getBuCode());
		String status = updateCandidate(candidate);
		 if(status.equals("Y")){
			 msgObj =  new MsgObject("The Candidate has been successfully Updated.");
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_UPDATE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
			 
		 }else{
			 msgObj =  new MsgObject("The Candidate has not been Updated.");
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_UPDATE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
		 }
		 requestParameters.setAttribute("messageObject", msgObj);
		 requestParameters.getSession().setAttribute("pager", eopAttendanceListing(requestParameters));
		 
		 return candidate;
	}
	 /**
	  * <p>Candidate details updated</p>
	  * @param candidate
	  * @return string as success/failure
	  */
	public String updateCandidate(EventCandidate candidate)
	{
		Session session = null;
		String status = "N";
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.update(candidate);
			
			tx.commit();
			status = "Y";
			log.log(Level.INFO,"---Candidate Details Updated Successfully--- ");
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		
		return status;
	}
	 /**
	  * <p>Delete Candidate.Store action in audit table.</p>
	  * @param candidateCode  Candidate Code
	  * @param requestParameters Servlet Request Parameter
	  */
	public void deleteCandidate(int candidateCode,HttpServletRequest requestParameters)
	{
		log.log(Level.INFO,"inside EopAttendanceMaintenance Delete --");
		User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		
		Session session = null;
		String status = "N";
		EventCandidate candidate = new EventCandidate();
		try{
		session = HibernateFactory.openSession();
		Transaction tx = session.beginTransaction();
		candidate = (EventCandidate)session.get(EventCandidate.class,candidateCode);
		candidate.setStatus(false);
		session.update(candidate);
		tx.commit();
		status = "Y";
		
		log.log(Level.INFO,"---Candidate Deleted Successfully---");
	  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		Boolean isRest =(Boolean) requestParameters.getAttribute("isRest"); 
		/*if(null == isRest && false == isRest){
			
		 MsgObject msgObj = null;
		 AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		 if(status.equals("Y")){
			 msgObj = new MsgObject("The Candidate has been successfully Deleted.");
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
		 }
		 else{
			 msgObj = new MsgObject("The Candidate has not been Deleted."); 
			 auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
		 }
				
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.getSession().setAttribute("pager", eopAttendanceListing(requestParameters));
		}else{*/
			MsgObject msgObj = null;
			 AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
			 if(status.equals("Y")){
				 msgObj = new MsgObject("The Candidate has been successfully Deleted.");
				 auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
			 }
			 else{
				 msgObj = new MsgObject("The Candidate has not been Deleted."); 
				 auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
			 }
			 requestParameters.getSession().setAttribute("pager", eopAttendanceListing(requestParameters));	
			requestParameters.setAttribute("messageObject", msgObj);
		//}
		
	}
	 /**
	  * <p>Updating Candidate Complete Status</p>
	  * @param requestParameters  Servlet Request Parameter
	  * @return pager
	  */
	public Pager updateCandidateCompleteStatus(HttpServletRequest requestParameters)
	{
		Session session = null;
		String status = "N";
		EventCandidate candidate = null;
		MsgObject msgObj = null;
		boolean flag=false;
		@SuppressWarnings("unchecked")
		ArrayList<EventCandidate>  attendanceList = (ArrayList<EventCandidate>)requestParameters.getSession().getAttribute("EOP_ATTENDANCE_LIST");
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			for(EventCandidate candidateObj : attendanceList){
				candidate = (EventCandidate)session.get(EventCandidate.class,candidateObj.getCandidateCode());
				System.out.println(requestParameters.getParameter(candidateObj.getCandidateCode()+"_timeIn"));
				if(requestParameters.getParameter(candidateObj.getCandidateCode()+"_timeIn").equals("Y")){
					try{
						
						SimpleDateFormat format = new SimpleDateFormat("hh:mm");
						candidate.setTimeIn(new Date());
						
					}catch(Exception e){
						flag=true;
						
					}
					
				}else{
					candidate.setTimeIn(null);
				}
				
				candidate.setCompleteStatus(requestParameters.getParameter(candidateObj.getCandidateCode()+"_completeSts"));
				session.update(candidate);
			}
			
			tx.commit();
			status = "Y";
			log.log(Level.INFO,"---Candidate Complete Status Updated Successfully--- ");
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		if(flag==true){
			msgObj = new MsgObject("The Candidate Time has not been Updated. Because Time format shoutld be hh:mm");
		}else{
			if(status.equals("Y"))
				 msgObj = new MsgObject("Candidate Status Updated Successfully.");
			 
			 else
				 msgObj = new MsgObject("The Candidate has not been Updated."); 
		}
		 
		 
		requestParameters.setAttribute("messageObject", msgObj);
		return eopAttendanceListing(requestParameters);
	}
	
	/**
	  * <p>Duplicate Registration checked</p>
	  * @param eventCode
	  * @param servicingAgent
	  * @param candidateCode
	  * @return true/false
	  */
	 public boolean checkDuplicateCandiadteReg(String  eventCode,String servicingAgent,String candidateCode){
	  Session session = null;
	  try{
	   session = HibernateFactory.openSession();
	   Query selectQ = session.createQuery("select  candidateCode from EventCandidate  where eventCode =:eventCode and servicingAgent=:servicingAgent and eventCandidateCode=:candidateCode and status=:status");
	   selectQ.setParameter("eventCode",Integer.parseInt(eventCode));
	   selectQ.setParameter("servicingAgent", servicingAgent);
	   selectQ.setParameter("candidateCode", Integer.parseInt(candidateCode));
	   selectQ.setParameter("status", true);
	   
	   List list = selectQ.list();
	   if(list!=null && list.size() > 0)
	    return true;
	   else 
	    return false;
	  }catch(Exception e){
	   log.log(Level.SEVERE, e.getMessage());
	   e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
	  }finally{
	   try{
	    HibernateFactory.close(session);
	    
	   }catch(Exception e){
	    log.log(Level.SEVERE, e.getMessage());
	    e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
	   }
	   }
	  return false;
	 }
	 
	 /**
	  * <p>Duplicate Registration checked</p>
	  * @param eventCode
	  * @param servicingAgent
	  * @param candidateCode
	  * @return true/false
	  */
	 public List checkDuplicateCandiadteReg1(String  eventCode,String servicingAgent,String candidateCode){
	  Session session = null;
	  List list = new ArrayList<EventCandidate>();
	  try{
	   session = HibernateFactory.openSession();
	   Query selectQ = session.createQuery("from EventCandidate  where eventCode =:eventCode and servicingAgent=:servicingAgent and eventCandidateCode=:candidateCode and status=:status");
	   selectQ.setParameter("eventCode",Integer.parseInt(eventCode));
	   selectQ.setParameter("servicingAgent", servicingAgent);
	   selectQ.setParameter("candidateCode", Integer.parseInt(candidateCode));
	   selectQ.setParameter("status", true);
	   
	    list= selectQ.list();
	  }catch(Exception e){
	   log.log(Level.SEVERE, e.getMessage());
	   e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
	  }finally{
	   try{
	    HibernateFactory.close(session);
	    
	   }catch(Exception e){
	    log.log(Level.SEVERE, e.getMessage());
	    e.printStackTrace();
	   }
	   }
	  return list;
	 }
	 
	 /**
	   * <p>Delete Candidate.Store action in audit table.</p>
	   * @param candidateCode  Candidate Code, eventCode Event Code
	   * @param requestParameters Servlet Request Parameter
	   */
	 public void deleteCandidateReg(int candidateCode, int eventCode, HttpServletRequest requestParameters)
	 {
	  log.log(Level.INFO,"inside EopAttendanceMaintenance Delete --");
	  Session session = null;
	  String status = "N";
	  EventCandidate candidate = new EventCandidate();
	  try{
	  session = HibernateFactory.openSession();
	  Transaction tx = session.beginTransaction();
	  
	  Criteria crit = session.createCriteria(EventCandidate.class);
	  crit.add(Restrictions.eq("eventCode", eventCode));
	  crit.add(Restrictions.eq("eventCandidateCode", ""+candidateCode));
	  List<EventCandidate> list=(ArrayList<EventCandidate>) crit.list();
	  for(Iterator iterator = list.iterator(); iterator.hasNext();){
	   candidate = (EventCandidate) iterator.next();
	   candidate.setStatus(false);
	   session.update(candidate);
	  }
	  tx.commit();
	  status = "Y";
	  
	  log.log(Level.INFO,"---Candidate Deleted Successfully---");
	   
	  }catch(Exception e)
	  {
	   log.log(Level.SEVERE, e.getMessage());
	   e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
	  }finally{
	   try{
	    HibernateFactory.close(session);
	    
	   }catch(Exception e){
	    log.log(Level.SEVERE, e.getMessage());
	    e.printStackTrace();
	   }
	  }
	  Boolean isRest =(Boolean) requestParameters.getAttribute("isRest"); 
	  
	   MsgObject msgObj = null;
	    AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
	    if(status.equals("Y")){
	     msgObj = new MsgObject("The Candidate has been successfully Deleted.");
	     auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
	    }
	    else{
	     msgObj = new MsgObject("The Candidate has not been Deleted."); 
	     auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
	    }
	 }
	 
	 /**
	   * <p>Delete Candidate.Store action in audit table.</p>
	   * @param candidateCode  Candidate Code, eventCode Event Code
	   * @param requestParameters Servlet Request Parameter
	   */
	 public List<EventCandidate> getCandidateRegisteredEOP(String candidateCode, String agentId)
	 {
	  log.log(Level.INFO,"inside EopAttendanceMaintenance Delete --");
	  Session session = null;
	  String status = "N";
	  List<EventCandidate> list = new ArrayList();
	  EventCandidate candidate = new EventCandidate();
	  Event event;
	  try{
	  session = HibernateFactory.openSession();
	  
	  Criteria crit = session.createCriteria(EventCandidate.class);
	  crit.add(Restrictions.eq("servicingAgent", agentId));
	  crit.add(Restrictions.eq("eventCandidateCode", candidateCode));
	  list=(ArrayList<EventCandidate>) crit.list();
	  status = "Y";
	  
	  log.log(Level.INFO,"---Candidate Deleted Successfully---");
	  }catch(Exception e)
	  {
	   log.log(Level.SEVERE, e.getMessage());
	   e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
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
	     msgObj = new MsgObject("The Candidate has been successfully Deleted.");
	     auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
	    }
	    else{
	     msgObj = new MsgObject("The Candidate has not been Deleted."); 
	     auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent()+"", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
	    }
	    
	    return list;
	 }
	 
	 /**
		 * <p>Update Attendance Info</p>
		 * @param candidate
		 * @return
		 */
		public void candidateAttendanceRest(EventCandidate candidate, HttpServletRequest request){
			Session session = null;
			Transaction tx = null;
			
			AddressBook addressBook = null;
			EventCandidate candidate1 = null;
			
			try{
				addressBook = new AddressBook();
				candidate1 = new EventCandidate();
				addressBook.setName(candidate.getCandidateName());
				addressBook.setBirthDate(candidate.getDob());
				addressBook.setAgentId(candidate.getServicingAgent());
				addressBook.setGender(candidate.getGender());
				AddressBookMaintenance addressBookMaintenance=  new AddressBookMaintenance();
//				List<AddressBook> list = (List<AddressBook>) addressBookMaintenance.getAddressBook1(addressBook);
//				if(!list.isEmpty() && 0 < list.size()){
//					addressBook = list.get(0);
//				}else{
//					addressBookMaintenance.save(addressBook);
//				}
				
				addressBook = new AddressBookMaintenance().insertAddressBook(addressBook, request);
				
				candidate.setEventCandidateCode(""+addressBook.getAddressCode());
				List<EventCandidate> list1 = checkDuplicateCandiadteReg1(""+candidate.getEventCode(), candidate.getServicingAgent(), candidate.getEventCandidateCode());
				
				if(!list1.isEmpty() && 0 < list1.size()){
					candidate1 = list1.get(0);
					candidate1.setCandidateName(candidate.getCandidateName());
					candidate1.setDob(candidate.getDob());
					candidate1.setGender(candidate.getGender());
					candidate1.setTimeIn(candidate.getTimeIn());
				}else{
					candidate1 = candidate;
				}
				
				
				candidate1.setStatus(true);
				session = HibernateFactory.openSession();
				tx = session.beginTransaction();
				session.saveOrUpdate(candidate1);
				tx.commit();
			
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try {
					HibernateFactory.close(session);
				} catch (Exception e) {
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
				tx = null;
				session = null;
			}
			
		}
	 public String validatePublicRegistration(HttpServletRequest requestParameters){
		 String errorMsg = "";
		 Event event = null;
		 boolean errorFlag = false;
		 if(requestParameters.getParameter("eventCode")==null  || (requestParameters.getParameter("eventCode")!=null && requestParameters.getParameter("eventCode").length() == 0))
			 errorFlag =  true;
			 else if(requestParameters.getParameter("agentID")==null  || (requestParameters.getParameter("agentID")!=null && requestParameters.getParameter("agentID").length() == 0))
					 errorFlag =  true;
			 else{
				if(LMSUtil.validInt(requestParameters.getParameter("eventCode"))){
					 event =new EopMaintenance().getEvent(Integer.parseInt(requestParameters.getParameter("eventCode")));
					if(event!=null && event.getEvent_code() !=0){
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat ra = new SimpleDateFormat("HH:mm a");
						try {
							Date todayDate = new Date();
							if(sdf1.parse(sdf1.format(todayDate)).equals(event.getEventDate())){
								Date crtTime=new Date(System.currentTimeMillis());
								Date stTime=event.getStartTime();
								crtTime=ra.parse(ra.format(crtTime));
								stTime=ra.parse(ra.format(stTime));
								if(crtTime.after(stTime))
								{
									errorFlag = true;
								
								}
								 
							
							}else if(sdf1.parse(sdf1.format(todayDate)).after(event.getEventDate())){
								errorFlag = true;
								
							}
						} catch (ParseException e) {
							log.log(Level.SEVERE, e.getMessage());
							 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
								StringWriter errors = new StringWriter();
								e.printStackTrace(new PrintWriter(errors));
								logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
						}
						if(!errorFlag){
						boolean publiceRegOpen = new EopMaintenance().checkIfPublicRegistrationOpen(Integer.parseInt(requestParameters.getParameter("eventCode")));
						if(!publiceRegOpen)
							//errorMsg += "Sorry!!!Public Registration is not opened for this event<br>";
							errorFlag = true; // if event not opened for public registration
						}
					}else{
						errorFlag = true; // if event not found
					}
				
				}else
				//	errorMsg += "Event Code Should be a Valid Number <br>";
					errorFlag = true;
		
			
			if(!errorFlag){
					Integer id = AamDataMaintenance.checkIfAgentExist(requestParameters.getParameter("agentID"), requestParameters.getParameter("co"));
					if(id == 0)
						errorFlag = true;
				}
			}
	
			if(errorFlag)
				errorMsg = "您所访问的网址无效";
			else
				requestParameters.getSession().setAttribute("Event_OBJ", event);
			return errorMsg;
	 }
	 
	 /**
		 * <p>update Records on Delete of AddressBook </p>
		 * @param req   Servlet Request Parameter
		 * @param candidateCode
		 * @return void
		 */
		public void updateRecordOnAddressBookDelete(int candidateCode)
		{
			 Session session = null;
			 Transaction tx= null;
			 ArrayList<EventCandidate> attendanceList = new ArrayList<EventCandidate>();
			try{
				session = HibernateFactory.openSession();
				Criteria crit = session.createCriteria(EventCandidate.class);
				crit.add(Restrictions.eq("eventCandidateCode", ""+candidateCode));
				attendanceList = (ArrayList)crit.list();
				
				tx = session.beginTransaction();
				for(EventCandidate eventCandidate : attendanceList){
					eventCandidate.setStatus(false);
					session.update(eventCandidate);
				}
				tx.commit();
				
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("EopAttendanceMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					HibernateFactory.close(session);
					
				}catch(Exception e){
					log.log(Level.SEVERE, e.getMessage());
					e.printStackTrace();
				}
			}
		}
	
	public static final String CANDIDATE_NAME = "candidateName";
	public static final String SERVICING_AGENT = "servicingAgent";
	public static final String CO = "co";
	public static final String AGENT_NAME = "agentName";
	public static final String BU = "bu";
	public static final String DISTRICT = "district";
	public static final String CITY = "city";
	public static final String SSC = "ssc";
	public static final String AGENCY_LEADER_CODE = "agencyLeaderCode";
	public static final String AGENCY_LEADER_NAME = "agencyLeaderName";
	public static final String SOURCE_OF_REFERAL = "sourceOfReferal";
	public static final String AGE = "age";
	public static final String DOB = "dob";
	public static final String GENDER = "gender";
	public static final String CONTACT_NUMBER = "contactNumber";
	public static final String EDUCATION = "education";
	public static final String WECHATID = "weChatID";
	public static final String MAILID = "mailId";
	public static final String NRIC = "nric";
	
	
}
