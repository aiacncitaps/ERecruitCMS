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
 * 15-July-2015               Maunish             File Added  
 ***************************************************************************** */

package com.quix.aia.cn.imo.mapper;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.addressbook.CandidateESignature;
import com.quix.aia.cn.imo.data.addressbook.CandidateESignatureId;
import com.quix.aia.cn.imo.database.HibernateFactory;

/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 */
public class CandidateESignatureMaintenance {
	static Logger log = Logger
			.getLogger(CandidateESignatureMaintenance.class.getName());

	/**
	 * <p>
	 * This method performs insert or update of AddressBook from List of AddressBook called from rest. When
	 * addressCode is 0, it performs insert otherwise performs update.
	 * </p>
	 * 
	 * @param Set<CandidateESignature/>
	 *           	List of Class Object
	 * @return void
	 * 
	 */
	public void saveOrUpdate(AddressBook addressBook) {

		Session session = null;
		Transaction tx = null;
		CandidateESignatureId eSignatureId;

		try {
			deleteRecords(addressBook);
			
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			for (CandidateESignature candidateESignature : addressBook.getCandidateESignatures()) {
				eSignatureId = new CandidateESignatureId();
				eSignatureId.setAddressCode(addressBook.getAddressCode());
				eSignatureId.setIosAddressCode(candidateESignature.getIosAddressCode());
				candidateESignature.seteSignatureId(eSignatureId);
				session.saveOrUpdate(candidateESignature);
			}
			tx.commit();
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateSingnatureMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			tx=null;
			session=null;
			eSignatureId=null;
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
		List<CandidateESignature> list = null;
		CandidateESignature candidateESignature = null;

		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			
			criteria = session.createCriteria(CandidateESignature.class);
			criteria.add(Restrictions.eq("eSignatureId.addressCode", addressBook.getAddressCode()));
			list = criteria.list();
			
			for(Iterator itr = list.iterator();itr.hasNext();){
				candidateESignature = (CandidateESignature) itr.next();
				session.delete(candidateESignature);
			}
			
			tx.commit();
		    
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateSingnatureMaintenance",Level.SEVERE+"",errors.toString());
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
	 * @return Set<CandidateESignature> List of Class Object
	 * 
	 */
	public Set<CandidateESignature> readCandidateESignatures(
			Set<CandidateESignature> list) {

		for (CandidateESignature candidateESignature : list) {
			candidateESignature.setIosAddressCode(candidateESignature.geteSignatureId().getIosAddressCode());
			candidateESignature.seteSignatureId(null);
		}
		return list;
	}
	

	public static final String Name = "name";

}
