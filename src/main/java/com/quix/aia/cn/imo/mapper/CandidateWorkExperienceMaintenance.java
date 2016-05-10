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
 * 17-July-2015               Maunish             File Added  
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.addressbook.CandidateWorkExperience;
import com.quix.aia.cn.imo.data.addressbook.CandidateWorkExperienceId;
import com.quix.aia.cn.imo.database.HibernateFactory;

/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 */
public class CandidateWorkExperienceMaintenance {
	static Logger log = Logger
			.getLogger(CandidateWorkExperienceMaintenance.class.getName());
	
	/**
	 * <p>
	 * This method performs insert or update of AddressBook from List of AddressBook called from rest. When
	 * addressCode is 0, it performs insert otherwise performs update.
	 * </p>
	 * @param session 
	 * 
	 * @param Set<CandidateWorkExperience/>
	 *           	List of Class Object
	 * @return void
	 * 
	 */
	public void saveOrUpdate(AddressBook addressBook, Session session) {

		/*Session session = null;
		Transaction tx = null;*/
		CandidateWorkExperienceId workExperienceId=null;

		try {
			deleteRecords(addressBook,session);
			
			/*session = HibernateFactory.openSession();
			tx = session.beginTransaction();*/
			for (CandidateWorkExperience candidateWorkExperience : addressBook.getCandidateWorkExperiences()) {
				workExperienceId = new CandidateWorkExperienceId();
				workExperienceId.setAddressCode(addressBook.getAddressCode());
				workExperienceId.setIosAddressCode(candidateWorkExperience.getIosAddressCode());
				candidateWorkExperience.setWorkExperienceId(workExperienceId);
				session.saveOrUpdate(candidateWorkExperience);
			}
			//tx.commit();
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateWorkExperienceMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			/*try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			tx=null;
			session=null;*/
			workExperienceId=null;
		}
	}

	/**
	 * <p>
	 * Delete Records of AddressBook record
	 * </p>
	 * @param session 
	 * 
	 * @param AddressBook addressBook
	 *            
	 * @return void
	 * 
	 */
	public void deleteRecords(AddressBook addressBook, Session session) {

	/*	Session session = null;
		Criteria criteria = null;
		Transaction tx = null;
		List<CandidateWorkExperience> list = null;
		CandidateWorkExperience candidateWorkExperience = null;*/

		try {
			/*session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			
			criteria = session.createCriteria(CandidateWorkExperience.class);
			criteria.add(Restrictions.eq("workExperienceId.addressCode", addressBook.getAddressCode()));
			list = criteria.list();
			
			for(Iterator itr = list.iterator();itr.hasNext();){
				candidateWorkExperience = (CandidateWorkExperience) itr.next();
				session.delete(candidateWorkExperience);
			}
			
			tx.commit();*/
			
			
			Query query = session.createQuery("DELETE FROM  CandidateWorkExperience  where workExperienceId.addressCode=:addressCode ");
			query.setParameter("addressCode", addressBook.getAddressCode());
			query.executeUpdate();
		    
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("CandidateWorkExperienceMaintenance",Level.SEVERE+"",errors.toString());
		} /*finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
		}*/
	}
	
	


	/**
	 * 
	 * @param list
	 * @return Set<CandidateWorkExperience> List of Class Object
	 * 
	 */
	public Set<CandidateWorkExperience> readCandidateWorkExperiences(
			Set<CandidateWorkExperience> list) {

		for (CandidateWorkExperience candidateWorkExperience : list) {
			candidateWorkExperience.setIosAddressCode(candidateWorkExperience.getWorkExperienceId().getIosAddressCode());
			candidateWorkExperience.setWorkExperienceId(null);
		}
		return list;
	}

	

	public static final String Name = "name";

}
