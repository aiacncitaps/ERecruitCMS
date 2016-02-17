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
 * 17-Feb-2016               Maunish             File Added  
 ***************************************************************************** */

package com.quix.aia.cn.imo.mapper;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.data.group.GroupCandidateDetail;
import com.quix.aia.cn.imo.data.group.GroupDetail;
import com.quix.aia.cn.imo.database.HibernateFactory;

/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 */
public class GroupCandidateDetailMaintenance {
	static Logger log = Logger.getLogger(GroupCandidateDetailMaintenance.class
			.getName());
	
	public GroupCandidateDetailMaintenance()
	{
		HibernateFactory.buildIfNeeded();
	}

	/**
	 * <p>
	 * This method performs insert or update of AddressBook from List of
	 * AddressBook called from rest. When addressCode is 0, it performs insert
	 * otherwise performs update.
	 * </p>
	 * 
	 * @param Set
	 *            <GroupCandidateDetail/> List of Class Object
	 * @return void
	 * 
	 */
	public void saveOrUpdate(GroupDetail groupDetail) {

		Session session = null;
		Transaction tx = null;

		try {
			deleteRecords(groupDetail);
			
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			for (GroupCandidateDetail groupCandidateDetail : groupDetail.getGroupContactData()) {
				groupCandidateDetail.setGroupCode(groupDetail.getGroupCode());
				session.saveOrUpdate(groupCandidateDetail);
			}
			tx.commit();
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("GroupCandidateDetailMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
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
	public void deleteRecords(GroupDetail groupDetail) {

		Session session = null;
		Criteria criteria = null;
		Transaction tx = null;
		List<GroupCandidateDetail> list = null;
		GroupCandidateDetail groupCandidateDetail = null;

		try {
			session = HibernateFactory.openSession();
			
			criteria = session.createCriteria(GroupCandidateDetail.class);
			criteria.add(Restrictions.eq("groupCode", groupDetail.getGroupCode()));
			list = criteria.list();
			
			tx = session.beginTransaction();
			for(Iterator itr = list.iterator();itr.hasNext();){
				groupCandidateDetail = (GroupCandidateDetail) itr.next();
				session.delete(groupCandidateDetail);
			}
			tx.commit();
		    
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("GroupCandidateDetailMaintenance",Level.SEVERE+"",errors.toString());
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

}
