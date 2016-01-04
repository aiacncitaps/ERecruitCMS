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
 * 24-July-2015               Maunish             File Added  
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
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.data.addressbook.CandidateTrainingDetail;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.event.Event;
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
public class CandidateTrainingDetailMaintenance {
	static Logger log = Logger.getLogger(CandidateTrainingDetailMaintenance.class
			.getName());
	

	/**
	 *<p>New Candidate Training Detail</p>
	 * @param candidate
	 * @param requestParameters
	 * @return
	 */
	public String createNewCandidateTrainingDetail(CandidateTrainingDetail candidateTrainingDetail, HttpServletRequest requestParameters)
	{
		 log.log(Level.INFO,"---Candidate Training Detail Maintenance Creation--- ");
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		 MsgObject msgObj = null;
		String agentId = requestParameters.getParameter("agentId");
		int status = insertNewCandidateTrainingDetail(candidateTrainingDetail);
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String msg = "";
		if(status !=0){
			
			msg = "Candidate Training Detail Added Successfully .";
			msgObj = new MsgObject(msg);
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidateTrainingDetail.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(""+candidateTrainingDetail.getCourseCode(), AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidateTrainingDetail.toString()));
				requestParameters.setAttribute("CANDIDATE_REG_MSG", msg);
				return msg;
			}
		}
		else{
			msg = "Sorry, Candidate Training Detail Could Not be Added.";
			msgObj = new MsgObject(msg);
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidateTrainingDetail.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(""+candidateTrainingDetail.getCourseCode(), AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidateTrainingDetail.toString()));
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
	public int insertNewCandidateTrainingDetail(CandidateTrainingDetail candidate)
	{
		Integer key = 0;
		Session session = null;

		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(candidate);
			key=1;
			tx.commit();
			log.log(Level.INFO,"---New Candidate Training Detail Inserted Successfully--- ");
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateTrainingDetailMaintenance",Level.SEVERE+"",errors.toString());
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
	 * <p>Get All Candidate Training Details</p>
	 * 
	 * @return
	 */
	public List getAllCandidateTrainingDetail()
	{
		Session session = null;
		ArrayList eventList = new ArrayList();
		
		try{
			session = HibernateFactory.openSession();
		
			Criteria crit = session.createCriteria(CandidateTrainingDetail.class);
			eventList = (ArrayList)crit.list();
		  
			}catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("CandidateTrainingDetailMaintenance",Level.SEVERE+"",errors.toString());
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

	public static final String Name = "name";

}
