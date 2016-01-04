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
import com.quix.aia.cn.imo.data.addressbook.CandidateProfessionalCertification;
import com.quix.aia.cn.imo.data.addressbook.CandidateProfessionalCertificationId;
import com.quix.aia.cn.imo.database.HibernateFactory;

/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 */
public class CandidateProfessionalCertificationMaintenance {
	static Logger log = Logger
			.getLogger(CandidateProfessionalCertificationMaintenance.class.getName());
	
	/**
	 * <p>
	 * This method performs insert or update of AddressBook from List of AddressBook called from rest. When
	 * addressCode is 0, it performs insert otherwise performs update.
	 * </p>
	 * 
	 * @param Set<CandidateProfessionalCertification/>
	 *           	List of Class Object
	 * @return void
	 * 
	 */
	public void saveOrUpdate(AddressBook addressBook) {

		Session session = null;
		Transaction tx = null;
		CandidateProfessionalCertificationId professionalCertificationId=null;
		
		try {
			deleteRecords(addressBook);
			
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			for (CandidateProfessionalCertification candidateProfessionalCertification : addressBook.getCandidateProfessionalCertifications()) {
				professionalCertificationId = new CandidateProfessionalCertificationId();
				professionalCertificationId.setAddressCode(addressBook.getAddressCode());
				professionalCertificationId.setIosAddressCode(candidateProfessionalCertification.getIosAddressCode());
				candidateProfessionalCertification.setProfessionalCertificationId(professionalCertificationId);
				session.saveOrUpdate(candidateProfessionalCertification);
			}
			tx.commit();
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateProfessionalCertificationMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			tx=null;
			session=null;
			professionalCertificationId=null;
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
		List<CandidateProfessionalCertification> list = null;
		CandidateProfessionalCertification candidateProfessionalCertification = null;

		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			
			criteria = session.createCriteria(CandidateProfessionalCertification.class);
			criteria.add(Restrictions.eq("professionalCertificationId.addressCode", addressBook.getAddressCode()));
			list = criteria.list();
			
			for(Iterator itr = list.iterator();itr.hasNext();){
				candidateProfessionalCertification = (CandidateProfessionalCertification) itr.next();
				session.delete(candidateProfessionalCertification);
			}
			
			tx.commit();
		    
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateProfessionalCertificationMaintenance",Level.SEVERE+"",errors.toString());
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
	 * @return Set<CandidateProfessionalCertification> List of Class Object
	 * 
	 */
	public Set<CandidateProfessionalCertification> readCandidateProfessionalCertifications(
			Set<CandidateProfessionalCertification> list) {

		for (CandidateProfessionalCertification candidateProfessionalCertification : list) {
			candidateProfessionalCertification.setIosAddressCode(candidateProfessionalCertification.getProfessionalCertificationId().getIosAddressCode());
			candidateProfessionalCertification.setProfessionalCertificationId(null);
		}
		return list;
	}

	

	public static final String Name = "name";

}
