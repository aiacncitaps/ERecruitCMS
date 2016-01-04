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
 * Date                       Developer           Description
 * -----------------------------------------------------------------------------
 * 06-August-2015             Maunish              File Added
 ***************************************************************************** */

package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.data.addressbook.CandidateFirstInterview;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.MsgObject;

/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 */
public class CandidateFirstInterviewMaintenance {
	static Logger log = Logger.getLogger(CandidateFirstInterviewMaintenance.class
			.getName());
	
	/**
	 *<p>New Candidate Training Result</p>
	 * @param candidate
	 * @param requestParameters
	 * @return
	 */
	public String createNewCandidateFirstInterview(CandidateFirstInterview candidateFirstInterview, HttpServletRequest requestParameters)
	{
		 log.log(Level.INFO,"---Candidate Training Result Maintenance Creation--- ");
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		 MsgObject msgObj = null;
		String agentId = requestParameters.getParameter("agentId");
		int status = insertNewCandidateFirstInterview(candidateFirstInterview);
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String msg = "";
		if(status !=0){
			
			msg = "Candidate Training Result Added Successfully .";
			msgObj = new MsgObject(msg);
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidateFirstInterview.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(""+candidateFirstInterview.getAgentId(), AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidateFirstInterview.toString()));
				requestParameters.setAttribute("CANDIDATE_REG_MSG", msg);
				return msg;
			}
		}
		else{
			msg = "Sorry, Candidate Training Result Could Not be Added.";
			msgObj = new MsgObject(msg);
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidateFirstInterview.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(""+candidateFirstInterview.getAgentId(), AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidateFirstInterview.toString()));
				requestParameters.setAttribute("CANDIDATE_REG_MSG", msg);
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
	public int insertNewCandidateFirstInterview(CandidateFirstInterview candidate)
	{
		Integer key = 0;
		Session session = null;

		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(candidate);
			key=1;
			tx.commit();
			log.log(Level.INFO,"---New Candidate Training Result Inserted Successfully--- ");
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateFirstInterviewMaintenance",Level.SEVERE+"",errors.toString());
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
	 * <p>Insert candidate done</p>
	 * @param candidate
	 * @return
	 */
	public CandidateFirstInterview getCandidateFirstinterview(String agentId, String candidateCode)
	{
		Integer key = 0;
		Session session = null;
		List<CandidateFirstInterview> list = new ArrayList();
		try{
			
			session = HibernateFactory.openSession();Criteria crit = session.createCriteria(CandidateFirstInterview.class);
			crit.add(Restrictions.eq("agentId", agentId));
			crit.add(Restrictions.eq("candidateCode", candidateCode));
			crit.addOrder(Order.desc("firstInterviewCode"));
			list=(ArrayList<CandidateFirstInterview>) crit.list();
			log.log(Level.INFO,"---New Candidate Training Result Fetched Successfully--- ");
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateFirstInterviewMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}

	public static final String Name = "name";

}
