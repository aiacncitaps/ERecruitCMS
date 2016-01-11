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
* 16-July-2015       Hemraj            candidate add from Rest
 * 20-Aug-2015    Nibedita          file uploaded for all candidate(2nd interview/3rd interview session)
****************************************** *********************************** */
package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.interview.Interview;
import com.quix.aia.cn.imo.data.interview.InterviewCandidate;
import com.quix.aia.cn.imo.data.interview.InterviewCandidateMaterial;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ExcelGenerator;
import com.quix.aia.cn.imo.utilities.ImoUtilityData;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;
/**
 * <p>to register candidate.</p>
 * 
 * @author Hemraj
 * @version 1.0
 *
 */
public class InterviewAttendanceMaintenance {
	static Logger log = Logger.getLogger(InterviewAttendanceMaintenance.class.getName());
	/**
	  * Constructor
	  * Hibernate configuration done
	  */
	public InterviewAttendanceMaintenance(){
		HibernateFactory.buildIfNeeded();
	}
	
	
	/**
	  * <p>Attendance listing done here.</p>
	  * @param req   Servlet Request Parameter
	  * @return  pager object
	  */
	@SuppressWarnings("unchecked")
	public  Pager interviewAttendanceListing(HttpServletRequest req)
	{
		LocaleObject localeObj = (LocaleObject)req.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		Interview interview =null;
		User userObj = (User)req.getSession().getAttribute("currUserObj");
		/* ArrayList interviewList=(ArrayList) req.getSession().getAttribute("selectedObj");
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

		    }*/
			int req_int_code= 0;
		    if(req.getParameter("interview_code") != null)
		    {
		    	req_int_code = Integer.parseInt(req.getParameter("interview_code"));
//		    }else{
		    	InterviewMaintenance interviewMaintenance = new InterviewMaintenance();
		    	//if(req.getSession().getAttribute("selectedObj")!=null)
		    		//interview = (Interview)req.getSession().getAttribute("selectedObj");
		    	//else
		    		interview = interviewMaintenance.getInterviewBasedOnInterviewCode(req_int_code);
//		    	req_int_code = interview.getInterview_code();
		    }
		
		   
		    
		InterviewMaintenance interviewMaintenance = new InterviewMaintenance();
		LinkedList item = new LinkedList();
		ArrayList<InterviewCandidate> listAllCandidates =new ArrayList<InterviewCandidate>();
		int eventCode = interview.getInterview_code();
		
		interview = interviewMaintenance.getInterviewBasedOnInterviewCode(eventCode);
	    	
		InterviewCandidate candidate = null;
		listAllCandidates = getAttendanceList(req,eventCode);
		ExcelGenerator excelGenerator = new ExcelGenerator();
		AamDataMaintenance aamDataMaintenance = new AamDataMaintenance();
		ImoUtilityData imoutill=new ImoUtilityData();
		
		
		if(listAllCandidates!=null && listAllCandidates.size() > 0)
		{
			String branchName=imoutill.getBranchNameBaseonCode(interview.getBranchCode());
			for(int i = 0; i < listAllCandidates.size(); i++)
			{
				candidate = new InterviewCandidate();
				candidate = (InterviewCandidate)listAllCandidates.get(i);
				
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
				   item.add(candidate.getGetAttendanceListingTableRow(localeObj,userObj));
			}
			String str = excelGenerator.GenerateInterviewRegistrationReport(listAllCandidates,interview, req.getRealPath("/resources/templates/"), req.getRealPath("/resources/userFiles/"));
			interview.setTempFilePath(str);
		}else{
			interview.setTempFilePath("#");
		}
		 req.getSession().setAttribute("selectedObj", interview);
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
	 *<p>New Candidate Registration</p>
	 * @param candidate
	 * @param requestParameters
	 * @return
	 */
	public String createNewCandidate(InterviewCandidate candidate, HttpServletRequest requestParameters)
	{
		 log.log(Level.INFO,"---Interview Registration Maintenance Creation--- ");
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		 MsgObject msgObj = null;
		String agentId = requestParameters.getParameter("agentId");
		int status = insertNewCandidate(candidate);
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String msg = "";
		if(status !=0){
			
			msg = "Candidate Registration Added Successfully .";
			msgObj = new MsgObject(msg);
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent(), AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
				requestParameters.setAttribute("CANDIDATE_REG_MSG", "Registration Done Successfully");
				return msg;
			}
		}
		else{
			msg = "Sorry, Candidate Registration Could Not be Added.";
			msgObj = new MsgObject(msg);
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent(), AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
				requestParameters.setAttribute("CANDIDATE_REG_MSG", "Sorry, Registration Not Done");
				return msg;
			}
				
		}
		
		requestParameters.setAttribute("messageObject", msgObj);
		
		return msg;
	}
	/**
	 * <p>Insert candidate done</p>
	 * @param candidate
	 * @return
	 */
	public int insertNewCandidate(InterviewCandidate candidate)
	{
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
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
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
	 * <p>get all attendance registered for particular interview</p>
	 * @param req   Servlet Request Parameter
	 * @param interviewCode
	 * @return List of Candidates
	 */
	public  ArrayList<InterviewCandidate> getAttendanceList(HttpServletRequest req,int interviewCode)
	{
		Session session = null;
		String agentId = req.getParameter("agentId");
		Boolean isRest = (Boolean) req.getAttribute("isRest");
		ArrayList<InterviewCandidate> attendanceList = new ArrayList<InterviewCandidate>();
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Criteria crit = session.createCriteria(InterviewCandidate.class);
			
			if(null != isRest && true == isRest){
				crit.add(Restrictions.eq("servicingAgent", agentId));
			}
			
			crit.add(Restrictions.eq("interviewCode", interviewCode));
			crit.add(Restrictions.eq("status", true));
			crit.addOrder(Order.desc("candidateCode"));
			attendanceList = (ArrayList)crit.list();
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		req.getSession().setAttribute("ATTENDANCE_LIST", attendanceList);
		return attendanceList;
	}
	 
	 
	/**
	 * <p>get all attendance registered for particular interview</p>
	 * @param req   Servlet Request Parameter
	 * @param interviewCode
	 * @return List of Candidates
	 */
	public  InterviewCandidate getAttendanceCandidateDetails(HttpServletRequest req,int interviewCode,int candidateCode)
	{
		Session session = null;
		InterviewCandidate interviewCandidate = new InterviewCandidate();
		ArrayList<InterviewCandidate> attendanceList = new ArrayList<InterviewCandidate>();
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Criteria crit = session.createCriteria(InterviewCandidate.class);
			
			crit.add(Restrictions.eq("interviewCode", interviewCode));
			crit.add(Restrictions.eq("interviewCandidateCode", ""+candidateCode));
			crit.add(Restrictions.eq("status", true));
//			crit.addOrder(Order.desc("candidateCode"));
			attendanceList = (ArrayList)crit.list();
			
			if(attendanceList.size()>0)
				interviewCandidate = (InterviewCandidate)attendanceList.get(0);
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		return interviewCandidate;
	}
	
	public  InterviewCandidate getAttendanceCandidateDetail1(HttpServletRequest req,int interviewCode,int candidateCode)
	{
		Session session = null;
		InterviewCandidate interviewCandidate = new InterviewCandidate();
		ArrayList<InterviewCandidate> attendanceList = new ArrayList<InterviewCandidate>();
		try{
			session = HibernateFactory.openSession();
			Criteria crit = session.createCriteria(InterviewCandidate.class);
			
			crit.add(Restrictions.eq("interviewCode", interviewCode));
			crit.add(Restrictions.eq("candidateCode", candidateCode));
			crit.add(Restrictions.eq("status", true));
//			crit.addOrder(Order.desc("candidateCode"));
			attendanceList = (ArrayList)crit.list();
			InterviewMaintenance interviewMain=new InterviewMaintenance();
			//int branchCode=interviewMain.getBranchCode(interviewCode);
			
			ImoUtilityData imoutill=new ImoUtilityData();
			
			if(attendanceList.size()>0)
				interviewCandidate = (InterviewCandidate)attendanceList.get(0);
				String branchName=imoutill.getBranchNameBaseonCode(interviewCandidate.getBranchCode());
			AamData aamData = AamDataMaintenance.retrieveDataToModel(interviewCandidate.getServicingAgent(), branchName); 
			interviewCandidate.setBuName(aamData.getBu());
			interviewCandidate.setDistName(aamData.getDistrict());
			interviewCandidate.setCityName(aamData.getCity());
			interviewCandidate.setSscName(aamData.getSsc());
			interviewCandidate.setBranchName(aamData.getBranch());
			interviewCandidate.setOfficeName(aamData.getOfficeName());
			interviewCandidate.setServiceingAgentName(aamData.getAgentName());
			
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		return interviewCandidate;
	}
	
	public  ArrayList<InterviewCandidateMaterial> getMaterailList(int candidateCode)
	{
		Session session = null;
		ArrayList<InterviewCandidateMaterial> list = new ArrayList<InterviewCandidateMaterial>();
		try{
			session = HibernateFactory.openSession();
			Criteria crit = session.createCriteria(InterviewCandidateMaterial.class);
			
			crit.add(Restrictions.eq("candidateCode", candidateCode));
			list = (ArrayList)crit.list();
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
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
	
	
	public  int insertCandidateMaterialDWR(int candidateCode, String materialName, String materialDesc)
	{
		Integer key = 0;
		Session session = null;
		InterviewCandidateMaterial material = new InterviewCandidateMaterial();
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			/*StringTokenizer st = new StringTokenizer(candidateCode, "|");
			while (st.hasMoreElements()) {
						material = new InterviewCandidateMaterial();
						material.setCandidateCode(Integer.parseInt((String)st.nextElement()));
						material.setMaterialFileName(materialName);
						material.setMaterialDescrption(materialDesc);
						key = (Integer)session.save(material);
			}*/
			material = new InterviewCandidateMaterial();
			material.setCandidateCode(candidateCode);
			material.setMaterialFileName(materialName);
			material.setMaterialDescrption(materialDesc);
			key = (Integer)session.save(material);
			tx.commit();
			log.log(Level.INFO,"---New Candidate Material Saved Successfully--- ");
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
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
	public  Map<String,ArrayList> getAttendanceCandidateDetailsDWR(String nric)
	{
		Session session = null;
		Map<String,ArrayList> map = new java.util.HashMap();
		InterviewCandidate interviewCandidate = new InterviewCandidate();
		ArrayList<Interview> interviewList = new ArrayList<Interview>();
		ArrayList<InterviewCandidate> attendanceList = new ArrayList<InterviewCandidate>();
		ArrayList<InterviewCandidateMaterial> materialList = new ArrayList<InterviewCandidateMaterial>();
		try{
			session = HibernateFactory.openSession();
			Criteria crit = session.createCriteria(InterviewCandidate.class);
			
			crit.add(Restrictions.eq("nric", nric));
			crit.add(Restrictions.eq("status", true));
//			crit.addOrder(Order.desc("candidateCode"));
			attendanceList = (ArrayList)crit.list();
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		
//		if(attendanceList.size()>0)
//			interviewCandidate = (InterviewCandidate)attendanceList.get(0);
//		for (Iterator iterator = attendanceList.iterator(); iterator.hasNext();) 
		Interview interview = null;
		InterviewMaintenance main = new InterviewMaintenance();
		for (int i = 0; i < attendanceList.size(); i++) {
			
			InterviewCandidate candidate = (InterviewCandidate) attendanceList.get(i);
			
			materialList = getMaterailList(candidate.getCandidateCode());
			String fileName = "";
				for (Iterator<InterviewCandidateMaterial> iterator2 = materialList.iterator(); iterator2.hasNext();) 
			{
					InterviewCandidateMaterial material = (InterviewCandidateMaterial) iterator2.next();
					fileName +=material.getMaterialFileName()+"|";
				
			}
			if(!fileName.equals(""))
				attendanceList.get(i).setFileNameList(fileName.substring(0,fileName.length()-1));
			
			interview = main.getInterviewBasedOnInterviewCode(candidate.getInterviewCode());
			if(null != interview && interview.isStatus()){
				interviewList.add(interview);
			}
		}

		/*for (Iterator iterator = interviewList.iterator(); iterator.hasNext();) 
		{
			InterviewCandidate candidate = (InterviewCandidate) iterator.next();
			materialList = getMaterailList(candidate.getCandidateCode());
		}*/
		map.put("list", attendanceList);
		map.put("interviewCandidateList", interviewList);
//		map.put("materialList", materialList);
		
		return map;
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
		InterviewCandidate candidate = null;
		@SuppressWarnings("unchecked")
		ArrayList<InterviewCandidate>  attendanceList = (ArrayList<InterviewCandidate>)requestParameters.getSession().getAttribute("ATTENDANCE_LIST");
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			
			for(InterviewCandidate candidateObj : attendanceList)
			{
				candidate = (InterviewCandidate)session.get(InterviewCandidate.class,candidateObj.getCandidateCode());
				
				String _ccStatus =  requestParameters.getParameter(candidateObj.getCandidateCode()+"_ccStatus");
				_ccStatus = _ccStatus == null ? "":_ccStatus;
				
				String _interviewResult =  requestParameters.getParameter(candidateObj.getCandidateCode()+"_interviewResult");
				_interviewResult = _interviewResult == null ? "":_interviewResult.toUpperCase()/*.substring(0,1)*/;
				
				String _recuritmentScheme =  requestParameters.getParameter(candidateObj.getCandidateCode()+"_recuritmentScheme");
				_recuritmentScheme = _recuritmentScheme == null ? "":_recuritmentScheme.toUpperCase()/*.substring(0,1)*/;
				
				String _p100 =  requestParameters.getParameter(candidateObj.getCandidateCode()+"_p100");
				_p100 = _p100 == null ? "0":_p100;
				
				String _remarks =  requestParameters.getParameter(candidateObj.getCandidateCode()+"_remarks");
				_remarks = _remarks == null ? "":_remarks;
				
				String _interviwer_name =  requestParameters.getParameter(candidateObj.getCandidateCode()+"_interviewer_name");
				_interviwer_name = _interviwer_name == null ? "":_interviwer_name;
				
				candidate.setCcTestResult(_ccStatus);
				candidate.setInterviewResult(_interviewResult);
				candidate.setRecruitmentScheme(_recuritmentScheme);
				candidate.setP100(Integer.parseInt(_p100));
				candidate.setRemarks(_remarks);
				candidate.setInterviwer_name(_interviwer_name);
				session.update(candidate);
				//;
				//candidateObj.getInterviewCode();
				String hql="UPDATE AddressBook SET ccTestResult=:ccTestResult,ccTestResultDate=:ccTestResultDate WHERE addressCode IN (SELECT interviewCandidateCode FROM InterviewCandidate WHERE candidateCode=:candidateCode AND interviewCode=:interviewCode)";
				Query query=session.createQuery(hql).setParameter("ccTestResult", _ccStatus).setParameter("ccTestResultDate", new Date()).setParameter("candidateCode", candidateObj.getCandidateCode()).setParameter("interviewCode", candidateObj.getInterviewCode());
				query.executeUpdate();
			}
			
			tx.commit();
			status = "Y";
			log.log(Level.INFO,"---Candidate Complete Status Updated Successfully--- ");
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
		MsgObject msgObj = null;
		 if(status.equals("Y"))
			 msgObj = new MsgObject("Candidate Status Updated Successfully.");
		 
		 else
			 msgObj = new MsgObject("The  Candidate has not been Updated."); 
		 
		requestParameters.setAttribute("messageObject", msgObj);
		return interviewAttendanceListing(requestParameters);
	}
	
	/**
	  * <p>Delete Candidate.Store action in audit table.</p>
	  * @param candidateCode  Candidate Code, interviewCode Interview Code
	  * @param requestParameters Servlet Request Parameter
	  */
	public void deleteCandidateReg(int candidateCode, int interviewCode, HttpServletRequest requestParameters)
	{
		log.log(Level.INFO,"inside InterviewAttendanceMaintenance Delete --");
		Session session = null;
		String status = "N";
		InterviewCandidate candidate = new InterviewCandidate();
		try{
		session = HibernateFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(InterviewCandidate.class);
		crit.add(Restrictions.eq("interviewCode", interviewCode));
		crit.add(Restrictions.eq("interviewCandidateCode", ""+candidateCode));
		List<InterviewCandidate> list=(ArrayList<InterviewCandidate>) crit.list();
		for(Iterator iterator = list.iterator(); iterator.hasNext();){
			candidate = (InterviewCandidate) iterator.next();
			candidate.setStatus(false);
			session.update(candidate);
		}
		tx.commit();
		status = "Y";
		
		log.log(Level.INFO,"---Candidate Deleted Successfully---");
	  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
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
				 msgObj = new MsgObject("The  Candidate has been successfully Deleted.");
				 auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent()+"", AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_SUCCESS +" "+candidate.toString()));
			 }
			 else{
				 msgObj = new MsgObject("The  Candidate has not been Deleted."); 
				 auditTrailMaint.insertAuditTrail(new AuditTrail(candidate.getServicingAgent()+"", AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_DELETE, AuditTrail.FUNCTION_FAILED +" "+candidate.toString()));
			 }
		
	}
	
	/**
	 * <p>Duplicate Registration checked</p>
	 * @param interviewCode
	 * @param servicingAgent
	 * @param candidateCode
	 * @return true/false
	 */
	public boolean checkDuplicateCandiadteReg(String  interviewCode,String servicingAgent,String candidateCode){
		Session session = null;
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Query selectQ = session.createQuery("select  candidateCode from InterviewCandidate  where interviewCode =:interviewCode and servicingAgent=:servicingAgent and interviewCandidateCode=:candidateCode and status=:status");
			selectQ.setParameter("interviewCode",Integer.parseInt(interviewCode));
			selectQ.setParameter("servicingAgent", servicingAgent);
			selectQ.setParameter("candidateCode", ""+Integer.parseInt(candidateCode));
			selectQ.setParameter("status", true);
			
			List list = selectQ.list();
			if(list!=null && list.size() > 0)
				return true;
			else 
				return false;
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	  }
		return false;
	}
	
	public InterviewCandidate getCandidateInterviewDetail(String candidateCode, String interviewType)
	{
		Session session = null;
		InterviewCandidate interviewCandidate = new InterviewCandidate();
		InterviewCandidate interviewCandidate1 = null;
		List<InterviewCandidate> attendanceList = new ArrayList();
		List<InterviewCandidate> interviewList = new ArrayList();
		boolean interviewFound = false;
		boolean fail = false;
		try{
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			Query selectQ = session.createQuery(" from InterviewCandidate where interviewCandidateCode=:candidateCode and status=:status ");
			selectQ.setParameter("candidateCode", candidateCode);
			selectQ.setParameter("status", true);
			attendanceList = selectQ.list();
			
			if(!attendanceList.isEmpty()){
				for(Iterator itr = attendanceList.iterator();itr.hasNext();){
					interviewCandidate1 = (InterviewCandidate) itr.next();
					selectQ = session.createQuery(" from Interview where interview_code=:interviewCode and interviewType=:interviewType and status=:status ");
					selectQ.setParameter("interviewCode", interviewCandidate1.getInterviewCode());
					selectQ.setParameter("interviewType", interviewType);
					selectQ.setParameter("status", true);
					interviewList = selectQ.list();
					
					if(!interviewList.isEmpty()){
						interviewFound = true;
						if("p".equalsIgnoreCase(interviewCandidate1.getInterviewResult())){
							interviewCandidate.setInterviewResult("PASS");
							break;
						}else if("f".equalsIgnoreCase(interviewCandidate1.getInterviewResult())){
							fail = true;
							interviewCandidate.setInterviewResult("FAIL");
						}else if(!fail){
							interviewCandidate.setInterviewResult("No Status");
						}
					}
					
				}
				
				if(!interviewFound){
					interviewCandidate = null;
				}
				
			}else{
				interviewCandidate = null;
			}
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		return interviewCandidate;
	}
	public static AddressBook getccTestResult(String interviewCandidateCode) {
		
		log.log(Level.INFO,"InterviewMaintenance --> getcctest result ");
		InterviewCandidate interviewcandidate = null;
		Session session = null;
		ArrayList list=null;
		AddressBook address=null;
		 try
			{
			    session = HibernateFactory.openSession();
			    	 address=(AddressBook) session.get(AddressBook.class,Integer.parseInt(interviewCandidateCode));
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
				 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
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
	
	public InterviewCandidate getCandidateDetailsRest(
			HttpServletRequest request, int interviewCode, int candidateCode,
			String agentId) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"InterviewMaintenance --> InterviewCandidate ");
		InterviewCandidate interviewcandidate = null;
		Session session = null;
		ArrayList listData=new ArrayList();
		try
			{
			    session = HibernateFactory.openSession();
			    session.setDefaultReadOnly(true);
			   // interviewcandidate=(InterviewCandidate) session.get(InterviewCandidate.class,Integer.parseInt(interviewCandidateCode));
			    
			    Criteria criteria = session.createCriteria(InterviewCandidate.class);
				criteria.add(Restrictions.eq("interviewCandidateCode", ""+candidateCode));
				criteria.add(Restrictions.eq("interviewCode", interviewCode));
				listData = (ArrayList) criteria.list();
				
				if(listData.size()>0){
					interviewcandidate=(InterviewCandidate) listData.get(0);
					
				}
				
				
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
				 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				try{
					session.setDefaultReadOnly(false);
					HibernateFactory.close(session);
				}catch(Exception e){
					
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}
			}
		return interviewcandidate;
	}
	
	
	public String getInterviewCandidateCode(int candidateCode) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"InterviewMaintenance --> InterviewCandidate ");
		InterviewCandidate interviewcandidate = null;
		Session session = null;
		ArrayList listData=new ArrayList();
		String interviewCandidateCode="";
		try
			{
			    session = HibernateFactory.openSession();
			   // interviewcandidate=(InterviewCandidate) session.get(InterviewCandidate.class,Integer.parseInt(interviewCandidateCode));
			    
			    Criteria criteria = session.createCriteria(InterviewCandidate.class);
				criteria.add(Restrictions.eq("candidateCode", candidateCode));
				
				listData = (ArrayList) criteria.list();
				
				if(listData.size()>0){
					interviewcandidate=(InterviewCandidate) listData.get(0);
					interviewCandidateCode=interviewcandidate.getInterviewCandidateCode();
					
				}
				
				
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
				 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("InterviewAttendanceMaintenance",Level.SEVERE+"",errors.toString());
				
			}finally{
				try{
					HibernateFactory.close(session);
				}catch(Exception e){
					
					log.log(Level.SEVERE,e.getMessage());
					e.printStackTrace();
				}
			}
		return interviewCandidateCode;
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
		 ArrayList<InterviewCandidate> attendanceList = new ArrayList<InterviewCandidate>();
		try{
			session = HibernateFactory.openSession();
			Criteria crit = session.createCriteria(InterviewCandidate.class);
			crit.add(Restrictions.eq("interviewCandidateCode", ""+candidateCode));
			attendanceList = (ArrayList)crit.list();
			
			tx = session.beginTransaction();
			for(InterviewCandidate interviewCandidate : attendanceList){
				interviewCandidate.setStatus(false);
				session.update(interviewCandidate);
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
	
	 public static String SOURCE_0F_REFERRAL(String name){
		 
		 if(name.equalsIgnoreCase("A")){return "Client";}
		 if(name.equalsIgnoreCase("B")){return "Client Recommendation";}
		 if(name.equalsIgnoreCase("C")){return "Agent Recommendation";}
		 if(name.equalsIgnoreCase("D")){return "Observation";}
		 if(name.equalsIgnoreCase("E")){return "Influence Center";}
		 if(name.equalsIgnoreCase("F")){return "Referral";}
		 if(name.equalsIgnoreCase("G")){return "Friend's Self Recommendation";}
		 if(name.equalsIgnoreCase("H")){return "Newspaper/Advertisement";}
		 if(name.equalsIgnoreCase("I")){return "University Campus";}
		 if(name.equalsIgnoreCase("J")){return "Job Market";}
		 if(name.equalsIgnoreCase("K")){return "Internet";}
		 if(name.equalsIgnoreCase("L")){return "Self Recommendation";}
		 if(name.equalsIgnoreCase("M")){return "Others";}
		 
		 return null;
		 
	  }


	
	 
	 
	 
	 
	
	
}
