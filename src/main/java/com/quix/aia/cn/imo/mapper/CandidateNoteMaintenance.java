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
 * 23-July-2015               Maunish             File Added  
 ***************************************************************************** */

package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.addressbook.CandidateNote;
import com.quix.aia.cn.imo.data.addressbook.CandidateNoteId;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;

/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 */
public class CandidateNoteMaintenance {
	static Logger log = Logger.getLogger(CandidateNoteMaintenance.class
			.getName());
	
	/**
	 * <p>
	 * This method performs insert or update of AddressBook from List of
	 * AddressBook called from rest. When addressCode is 0, it performs insert
	 * otherwise performs update.
	 * </p>
	 * 
	 * @param Set
	 *            <CandidateNote> List of Class Object
	 * @return void
	 * 
	 */
	public void saveOrUpdate(AddressBook addressBook) {

		Session session = null;
		Transaction tx = null;
		CandidateNoteId noteId;

		try {
//			deleteRecords(addressBook);
			
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			for (CandidateNote candidateNote : addressBook
					.getCandidateNotes()) {
				noteId = new CandidateNoteId();
				noteId.setAddressCode(addressBook.getAddressCode());
				noteId.setIosNoteCode(candidateNote
						.getIosNoteCode());
				candidateNote.setNoteId(noteId);
				session.saveOrUpdate(candidateNote);
			}
			tx.commit();
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateNoteMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			tx = null;
			session = null;
			noteId = null;
		}
	}

	/**
	 * <p>
	 * Delete Records of AddressBook record
	 * </p>
	 * 
	 * @param AddressBook addressBook
	 *            
	 * @return void
	 * 
	 */
	public void deleteRecords(AddressBook addressBook) {

		Session session = null;
		Criteria criteria = null;
		Transaction tx = null;
		List<CandidateNote> list = null;
		CandidateNote candidateNote = null;

		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			
			criteria = session.createCriteria(CandidateNote.class);
			criteria.add(Restrictions.eq("noteId.addressCode", addressBook.getAddressCode()));
			list = criteria.list();
			
			for(Iterator itr = list.iterator();itr.hasNext();){
				candidateNote = (CandidateNote) itr.next();
				session.delete(candidateNote);
			}
			
			tx.commit();
		    
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateNoteMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
		}
	}
	

	/**
	 * 
	 * @param list
	 * @return Set<CandidateNote> List of Class Object
	 * 
	 */
	public Set<CandidateNote> readCandidateNotes(
			Set<CandidateNote> list) {

		for (Iterator itr = list.iterator();itr.hasNext();) {
			CandidateNote candidateNote = (CandidateNote)itr.next();
			candidateNote.setIosNoteCode(candidateNote.getNoteId().getIosNoteCode());
			candidateNote.setNoteId(null);
			
			if(!candidateNote.getStatus()){
				itr.remove();
			}
		}
		return list;
	}
	

	/**
	 *<p>New Candidate Note</p>
	 * @param candidate
	 * @param requestParameters
	 * @return
	 */
	public String createNewCandidateNote(CandidateNote candidateNote, HttpServletRequest requestParameters)
	{
		 log.log(Level.INFO,"---Candidate Note Maintenance Creation--- ");
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		 MsgObject msgObj = null;
		String agentId = requestParameters.getParameter("agentId");
		int status = insertNewCandidateNote(candidateNote);
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String msg = "";
		if(status !=0){
			
			msg = "Candidate Note Added Successfully .";
			msgObj = new MsgObject(msg);
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_ADDRESS_BOOK, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidateNote.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(""+candidateNote.getNoteId().getAddressCode(), AuditTrail.MODULE_ADDRESS_BOOK, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_SUCCESS +" "+candidateNote.toString()));
				requestParameters.setAttribute("CANDIDATE_REG_MSG", msg);
				return msg;
			}
		}
		else{
			msg = "Sorry, Candidate Note Could Not be Added.";
			msgObj = new MsgObject(msg);
			if(userObj!=null)
				auditTrailMaint.insertAuditTrail(new AuditTrail(userObj.getStaffLoginId()+"", AuditTrail.MODULE_ADDRESS_BOOK, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidateNote.toString()));
			else{
				auditTrailMaint.insertAuditTrail(new AuditTrail(""+candidateNote.getNoteId().getAddressCode(), AuditTrail.MODULE_ADDRESS_BOOK, AuditTrail.FUNCTION_CREATE, AuditTrail.FUNCTION_FAILED +" "+candidateNote.toString()));
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
	public int insertNewCandidateNote(CandidateNote candidate)
	{
		Integer key = 0;
		Session session = null;

		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(candidate);
			key=1;
			tx.commit();
			log.log(Level.INFO,"---New Candidate Note Inserted Successfully--- ");
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateNoteMaintenance",Level.SEVERE+"",errors.toString());
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
	 * <p>
	 * This method is for delete Note.
	 * <p>
	 * 
	 * @param CandidateNote candidateNote
	 * @return void
	 * 
	 */
	public void deleteNote(CandidateNote candidateNote) {

		Session session = null;
		Criteria criteria = null;
		List<CandidateNote> list = null;

		try {
			
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			criteria = session.createCriteria(CandidateNote.class);
			criteria.add(Restrictions.eq("noteId.addressCode", candidateNote.getAddressCode()));
			criteria.add(Restrictions.eq("noteId.iosNoteCode", candidateNote.getIosNoteCode()));
			list = criteria.list();
			
			if(!list.isEmpty()){
				candidateNote = list.get(0);
				candidateNote.setStatus(false);
				session.saveOrUpdate(candidateNote);
			}

			tx.commit();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateNoteMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
		}
	}
	
	/**
	 * <p>
	 * This method delete Note
	 * <p>
	 * 
	 * @param CandidateNote candidateNote
	 * @return void
	 * 
	 */
	public List syncNotes(List<CandidateNote> candidateNoteList, HttpServletRequest requestParameters) {
		CandidateNote candidateNote=null;
		CandidateNote candidateNote1=null;
		CandidateNoteId candidateNoteId=null;
		List list = new ArrayList();
		
		for(Iterator itr = candidateNoteList.iterator();itr.hasNext();){
			candidateNote = (CandidateNote) itr.next();
			candidateNoteId = new CandidateNoteId();
	        candidateNoteId.setAddressCode(candidateNote.getAddressCode());
	        candidateNoteId.setIosNoteCode(candidateNote.getIosNoteCode());
	        candidateNote.setNoteId(candidateNoteId);
	        candidateNote.setIsSystem(false);
	        
	        if(candidateNote.getIsDelete()){
	        	deleteNote(candidateNote);
	        }else{
	        	candidateNote.setStatus(true);
	        	createNewCandidateNote(candidateNote, requestParameters);
	        }
	        
	        candidateNote1 = new CandidateNote();
	        candidateNote1.setIosNoteCode(candidateNote.getIosNoteCode());
	        candidateNote1.setStatus(null);
	        list.add(candidateNote1);
		}
		
		return list;
	}
	
	/**
	 * <p>Insert candidate note</p>
	 * @param candidate
	 * @return
	 */
	public void insertSystemNotes(int addressCode, String activityType, String description)
	{
		CandidateNoteId candidateNoteId = new CandidateNoteId();
		candidateNoteId.setAddressCode(addressCode);
		candidateNoteId.setIosNoteCode(LMSUtil.getRendomToken());
		
		CandidateNote candidateNote = new CandidateNote();
		candidateNote.setAddressCode(candidateNoteId.getAddressCode());
		candidateNote.setIosNoteCode(candidateNoteId.getIosNoteCode());
		candidateNote.setNoteId(candidateNoteId);
		candidateNote.setActivityType(activityType);
		candidateNote.setActivityDate(new Date());
		candidateNote.setDescription(description);
		candidateNote.setActivityStatus(true);
		candidateNote.setStatus(true);
		candidateNote.setIsSystem(true);
		
		//insertNewCandidateNote(candidateNote);
	}

	public static final String Name = "name";

}
