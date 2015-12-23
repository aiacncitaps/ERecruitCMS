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
 * 15-June-2015               Hemraj             File Added  
 * 22-June-2015               Maunish            Modified  
 * 23-July-2015               Maunish            Modified                
 * 06-August-2015    		  Maunish            Modified  
 * 05-Nov-2015               Nibedita            New method added
 ******************************************************************************/

package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.constants.ApplicationAttribute;
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.LMSUtil;


/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 */
public class AddressBookMaintenance {
	static Logger log = Logger
			.getLogger(AddressBookMaintenance.class.getName());
	
	/**
	 * <p>
	 * This method performs insert or update of AddressBook from List of
	 * AddressBook called from rest. When addressCode is 0, it performs insert
	 * otherwise performs update.
	 * </p>
	 * 
	 * @param List
	 *            <AddressBook/> List of Class Object
	 * @return List<AddressBook/> List of Class Object
	 * 
	 */
	public String insertOrUpdateRestBatch(List<AddressBook> addressBookList) {

		Session session = null;
		Transaction tx = null;
		int key = 0;
		int recordsToDelete = 0 ; 
		List<AddressBook> deleteAddressBookList = new ArrayList<AddressBook>();

		String returnJsonString = "";
		String deleteString = "";
		CandidateEducationMaintenance candidateEducationMaintenance = new CandidateEducationMaintenance();
		CandidateESignatureMaintenance candidateESignatureMaintenance = new CandidateESignatureMaintenance();
		CandidateFamilyInfoMaintenance candidateFamilyInfoMaintenance = new CandidateFamilyInfoMaintenance();
		CandidateGroupMaintenance candidateGroupMaintenance = new CandidateGroupMaintenance();
		CandidateProfessionalCertificationMaintenance candidateProfessionalCertificationMaintenance = new CandidateProfessionalCertificationMaintenance();
		CandidateWorkExperienceMaintenance candidateWorkExperienceMaintenance = new CandidateWorkExperienceMaintenance();
		CandidateNoteMaintenance candidateNoteMaintenance = new CandidateNoteMaintenance();

		try {
			session = HibernateFactory.openSession();
			int count = 0;

			boolean flag = true;
			for (AddressBook addressBook : addressBookList) {
				deleteString = "";

				tx = session.beginTransaction();
				key = (int) addressBook.getAddressCode();
				if (0 == key) {
					addressBook.setAddressCode(null);
				}
				if(addressBook.getDeleteStatus()){
					recordsToDelete++;
					deleteAddressBookList.add(addressBook);
					addressBook = getAddressBook(addressBook);
					addressBook.setDeleteStatus(true);
					deleteString = ",\"deleteStatus\":"+addressBook.getDeleteStatus();
				}
				session.saveOrUpdate(addressBook);
				tx.commit();

				if("".equals(deleteString)){
					candidateEducationMaintenance.saveOrUpdate(addressBook);
					candidateESignatureMaintenance.saveOrUpdate(addressBook);
					candidateFamilyInfoMaintenance.saveOrUpdate(addressBook);
					candidateGroupMaintenance.saveOrUpdate(addressBook);
					candidateProfessionalCertificationMaintenance.saveOrUpdate(addressBook);
					candidateWorkExperienceMaintenance.saveOrUpdate(addressBook);
					candidateNoteMaintenance.saveOrUpdate(addressBook);
				}

				if (flag) {
					flag = false;
				} else {
					returnJsonString += ",";
				}
				returnJsonString += "{\"iosAddressCode\":\""+ addressBook.getIosAddressCode() + "\",\"addressCode\":"+ addressBook.getAddressCode() +deleteString+"}";

				if (++count % 10 == 0) {
					session.flush();
					session.clear();
				}
			}

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			tx = null;
			session = null;
			candidateEducationMaintenance = null;
			candidateESignatureMaintenance = null;
			candidateFamilyInfoMaintenance = null;
			candidateGroupMaintenance = null;
			candidateProfessionalCertificationMaintenance = null;
			candidateWorkExperienceMaintenance = null;
			candidateNoteMaintenance = null;
		}
		
		if(recordsToDelete > 0){
			EopAttendanceMaintenance eventCandidateMain = new EopAttendanceMaintenance();
			InterviewAttendanceMaintenance interviewCandidateMain = new InterviewAttendanceMaintenance();
			for (AddressBook addressBook : deleteAddressBookList) {
				eventCandidateMain.updateRecordOnAddressBookDelete(addressBook.getAddressCode());
				interviewCandidateMain.updateRecordOnAddressBookDelete(addressBook.getAddressCode());
			}
		}
		
		return returnJsonString;
	}
	
	/**
	 * <p>
	 * This method performs inserts record
	 * </p>
	 * 
	 * @param AddressBook Class Object
	 *            
	 * @return int
	 * 
	 */
	public int save(AddressBook addressBook) {

		Session session = null;
		Transaction tx = null;
		int key = 0;

		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			key = (Integer) session.save(addressBook);
			tx.commit();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
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
		return key;
	}

	/**
	 * <p>
	 * This method retrieves all AddressBooks of Particular Agent
	 * <p>
	 * 
	 * @param agentID
	 * @return List<AddressBook> List of Class Object
	 * 
	 */
	public List<AddressBook> getAgentAddressBook(HttpServletRequest request, ServletContext context) {

		Session session = null;
		ArrayList<AddressBook> list = null;
		Criteria criteria = null;
		
		CandidateEducationMaintenance candidateEducationMaintenance = new CandidateEducationMaintenance();
		CandidateESignatureMaintenance candidateESignatureMaintenance = new CandidateESignatureMaintenance();
		CandidateFamilyInfoMaintenance candidateFamilyInfoMaintenance = new CandidateFamilyInfoMaintenance();
		CandidateGroupMaintenance candidateGroupMaintenance = new CandidateGroupMaintenance();
		CandidateProfessionalCertificationMaintenance candidateProfessionalCertificationMaintenance = new CandidateProfessionalCertificationMaintenance();
		CandidateWorkExperienceMaintenance candidateWorkExperienceMaintenance = new CandidateWorkExperienceMaintenance();
		CandidateNoteMaintenance candidateNoteMaintenance = new CandidateNoteMaintenance();
		
		Map<String,String> configurationMap =(Map<String,String>) context.getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
		
		int addressBookListingOffset = 0;
		addressBookListingOffset = Integer.parseInt(configurationMap.get("addressBookListingOffset"));

		String agentId = request.getParameter("agentId");
		String pageNo = request.getParameter("pageNo");
		String dateTime = request.getParameter("dateTime");
		
		String coBranch = request.getParameter("co");

		int firstRecordNo = 0;
		agentId = agentId == null ? "" : agentId;
		pageNo = pageNo == null ? "0" : pageNo;
		Date date = null;
		if (null != dateTime && !"".equals(dateTime)) {
			date = LMSUtil.convertDateToyyyymmddhhmmssDashed(dateTime);
		}

		if (Long.parseLong(pageNo) > 0) {
			firstRecordNo = ((addressBookListingOffset * Integer.parseInt(pageNo)) - addressBookListingOffset);
		}

		try {
			session = HibernateFactory.openSession();
			criteria = session.createCriteria(AddressBook.class);
			criteria.setFirstResult(firstRecordNo);
			criteria.setMaxResults(addressBookListingOffset);

			criteria.add(Restrictions.eq("agentId", agentId));
			if (null != date) {
				criteria.add(Expression.ge("modificationDate", date));
			}
			
			if (null != coBranch && !"".equals(coBranch)) {
				criteria.add(Restrictions.eq("co", coBranch));
			}
			list = (ArrayList) criteria.list();
			
			for(AddressBook addressBook : list){
				addressBook.setCandidateEducations(candidateEducationMaintenance.readCandidateEducations(addressBook.getCandidateEducations()));
				addressBook.setCandidateESignatures(candidateESignatureMaintenance.readCandidateESignatures(addressBook.getCandidateESignatures()));
				addressBook.setCandidateFamilyInfos(candidateFamilyInfoMaintenance.readCandidateFamilyInfos(addressBook.getCandidateFamilyInfos()));
				addressBook.setCandidateGroups(candidateGroupMaintenance.readCandidateGroups(addressBook.getCandidateGroups()));
				addressBook.setCandidateProfessionalCertifications(candidateProfessionalCertificationMaintenance.readCandidateProfessionalCertifications(addressBook.getCandidateProfessionalCertifications()));
				addressBook.setCandidateWorkExperiences(candidateWorkExperienceMaintenance.readCandidateWorkExperiences(addressBook.getCandidateWorkExperiences()));
				addressBook.setCandidateNotes(candidateNoteMaintenance.readCandidateNotes(addressBook.getCandidateNotes()));
			}

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
			list = new ArrayList<AddressBook>();
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
			criteria = null;
			candidateEducationMaintenance = null;
			candidateESignatureMaintenance = null;
			candidateFamilyInfoMaintenance = null;
			candidateGroupMaintenance = null;
			candidateProfessionalCertificationMaintenance = null;
			candidateWorkExperienceMaintenance = null;
			candidateNoteMaintenance = null;
		}
		return list;
	}
	
	/**
	 * <p>
	 * This method retrieves AddressBook of Particular nric code
	 * <p>
	 * 
	 * @param iosAddressCode and agentId
	 * @return AddressBook Class Object
	 * 
	 */
	public AddressBook getAddressBook(String iosAddressCode,String agentId) {

		Session session = null;
		Criteria criteria = null;
		ArrayList<AddressBook> list = null;

		try {
			session = HibernateFactory.openSession();
			criteria = session.createCriteria(AddressBook.class);
			criteria.add(Restrictions.eq("iosAddressCode", iosAddressCode));
			criteria.add(Restrictions.eq("agentId", agentId));
			list = (ArrayList) criteria.list();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
			criteria = null;
		}
		
		if(null == list){
			list = new ArrayList<AddressBook>();
		}
		
		return list.get(0);
	}
	
	/**
	 * <p>
	 * This method retrieves AddressBook of Particular nric code
	 * <p>
	 * 
	 * @param iosAddressCode and agentId
	 * @return AddressBook Class Object
	 * 
	 */
	public List getAddressBook1(AddressBook addressBook) {

		Session session = null;
		Criteria criteria = null;
		ArrayList<AddressBook> list = null;

		try {
			session = HibernateFactory.openSession();
			criteria = session.createCriteria(AddressBook.class);
			criteria.add(Restrictions.eq("name", addressBook.getName()));
			criteria.add(Restrictions.eq("birthDate", addressBook.getBirthDate()));
			criteria.add(Restrictions.or(Restrictions.eq("agentId", ""+Integer.parseInt(addressBook.getAgentId())), Restrictions.eq("agentId", addressBook.getAgentId())));
			list = (ArrayList) criteria.list();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
			criteria = null;
		}
		
		if(null == list){
			list = new ArrayList<AddressBook>();
		}
		
		return list;
	}
	
	/**
	 * <p>
	 * This method retrieves AddressBook of Particular nric code
	 * <p>
	 * 
	 * @param iosAddressCode and agentId
	 * @return AddressBook Class Object
	 * 
	 */
	public AddressBook getAddressBookNric(String nric,String agentId) {

		Session session = null;
		Criteria criteria = null;
		ArrayList<AddressBook> list = null;

		try {
			session = HibernateFactory.openSession();
			criteria = session.createCriteria(AddressBook.class);
			criteria.add(Restrictions.eq("nric", nric));
			criteria.add(Restrictions.eq("agentId", agentId));
			list = (ArrayList) criteria.setCacheable(true).list();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
			criteria = null;
		}
		
		if(null == list){
			list = new ArrayList<AddressBook>();
		}
		
		return list.get(0);
	}
	
	/**
	 * <p>
	 * This method update AddressBook of Particular nric code
	 * <p>
	 * 
	 * @param nricCode
	 * @return AddressBook Class Object
	 * 
	 */
	public void updateAddressBook(AddressBook addressBook) {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			session.update(addressBook);
			tx.commit();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
			tx = null;
		}
	}
	
	/**
	 * <p>
	 * This method get AddressBook of Particular address code
	 * <p>
	 * 
	 * @param address code
	 * @return AddressBook Class Object
	 * 
	 */
	public AddressBook getAddressBook(AddressBook addressBook) {

		Session session = null;
		Criteria criteria = null;
		ArrayList<AddressBook> list = null;

		try {
			session = HibernateFactory.openSession();
			criteria = session.createCriteria(AddressBook.class);
			criteria.add(Restrictions.eq("addressCode", addressBook.getAddressCode()));
			list = (ArrayList) criteria.setCacheable(true).list();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
			criteria = null;
		}
		
		if(null == list){
			list = new ArrayList<AddressBook>();
		}
		
		return list.get(0);
	}
	
	/**
	 * <p>
	 * This method update Contract Details
	 * <p>
	 * 
	 * @param address code
	 * @return AddressBook Class Object
	 * 
	 */
	public void updateContractDetails(AddressBook addressBook) {

		ArrayList<Object []> list = null;
		Object[] obj;
		
		String[] updateFieldName = {"candidateAgentCode","branchCode","contractDate","recruitmentType"};
		Object[] updateFieldValue = {addressBook.getCandidateAgentCode(),addressBook.getBranchCode(),addressBook.getContractDate(),addressBook.getRecruitmentType()};
		String[] conditionFieldName = {"agentId","nric"};
		String[] conditionFieldValue = {addressBook.getAgentId(),addressBook.getNric()};
		
		updateAddressBookSelectedField(updateFieldName, updateFieldValue, conditionFieldName, conditionFieldValue);
	}
	
	
	
	/**
	 * <p>
	 * This method update Contract Details
	 * <p>
	 * 
	 * @param address code
	 * @return AddressBook Class Object
	 * 
	 */
	public List<Object []> getAddressBookSelectedField(String[] fetchFields, String[] conditionFieldName, String[] conditionFieldValue) {

		Session session = null;
		Query query = null;
		List<Object []> list = null;

		try {
			
			session = HibernateFactory.openSession();
			String fetchField = getStringFromArrayString(fetchFields,null,"forSelect");
			String fetchCondition = getStringFromArrayString(conditionFieldName, conditionFieldValue,"forWhere");
			query = session.createQuery(" SELECT "+fetchField+" FROM AddressBook WHERE "+fetchCondition);
			list = query.setCacheable(true).list();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
		}
		
		return list;
	}
	
	/**
	 * <p>
	 * This method update Contract Details
	 * <p>
	 * 
	 * @param address code
	 * @return AddressBook Class Object
	 * 
	 */
	public void updateAddressBookSelectedField(Object[] updateFieldName, Object[] updateFieldValue, String[] conditionFieldName, String[] conditionFieldValue) {

		Session session = null;
		Query query = null;

		try {
			
			session = HibernateFactory.openSession();
			String updateParams = getStringFromArrayString(updateFieldName, updateFieldValue,"forUpdate");
			String updateCondition = getStringFromArrayString(conditionFieldName, conditionFieldValue,"forWhere");
			query = session.createQuery("UPDATE AddressBook SET "+updateParams+" WHERE "+updateCondition);
			query.executeUpdate();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method update Contract Details
	 * <p>
	 * 
	 * @param address code
	 * @return AddressBook Class Object
	 * 
	 */
	public void updateAddressBookStatus(String recruitmentProgressStatus, String[] conditionFieldName, String[] conditionFieldValue) {

		Session session = null;
		Query query = null;

		try {
			
			session = HibernateFactory.openSession();
			String updateCondition = getStringFromArrayString(conditionFieldName, conditionFieldValue,"forWhere");
			query = session.createQuery("UPDATE AddressBook SET recruitmentProgressStatus = '"+recruitmentProgressStatus+"' WHERE "+updateCondition);
			query.executeUpdate();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method get all data
	 * <p>
	 * 
	 * @param Array of object
	 * @return String
	 * 
	 */
	private String getStringFromArrayString(Object[] arrayObj, Object[] arrayObj1, String forCondition){
		String fetchField = "";
		boolean flag = false;
		for(int i=0;i<arrayObj.length;i++){
			if(flag){
				if("forWhere".equalsIgnoreCase(forCondition)){
					fetchField +=" and ";
				}else{
					fetchField +=", ";
				}
			}else{
				flag = true;
			}
			fetchField+= " "+arrayObj[i];
			if(null != arrayObj1){
				fetchField+=" = '"+arrayObj1[i]+"'";
			}
		}
		return fetchField;
	}
	
	/**
	 * <p>
	 * This method  to get email from Address book
	 * <p>
	 * 
	 * @param Addresscode
	 * @return String
	 * 
	 */
	public String getEmailAddress(int code){
		Session session = null;
		String emailAddrs = "";
		try{
			session = HibernateFactory.openSession();
			Query selectQ = session.createQuery("select  eMailId  from  AddressBook where addressCode =:addressCode");
			selectQ.setParameter("addressCode", code);
			List list = selectQ.list();
			if(list!=null && list.size() > 0)
				emailAddrs = (String)list.get(0);
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
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
	 * <p>
	 * This method is to get Nric from address book
	 * <p>
	 * 
	 * @param Addresscode
	 * @return String
	 * 
	 */
	public String getNric(int code){
		Session session = null;
		String nric = "";
		try{
			session = HibernateFactory.openSession();
			Query selectQ = session.createQuery("select  nric  from  AddressBook where addressCode =:addressCode");
			selectQ.setParameter("addressCode", code);
			List list = selectQ.list();
			if(list!=null && list.size() > 0)
				nric = (String)list.get(0);
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	  }
		return nric;
		
	}
	public static final String Name = "name";
//	public static final int addressBookListingOffset = Integer.parseInt(ResourceBundle.getBundle("configurations")
//			.getString("addressBookListingOffset"));

	public AddressBook getaddressDataForCCTest(String candidateCode) {
		// TODO Auto-generated method stub
		Session session = null;
		Criteria criteria = null;
		ArrayList<AddressBook> list = null;
		AddressBook address=null;

		try {
			
			session = HibernateFactory.openSession();
			criteria = session.createCriteria(AddressBook.class);
			criteria.add(Restrictions.eq("addressCode", Integer.parseInt(candidateCode)));
			list = (ArrayList) criteria.setCacheable(true).list();
			if(list.size()>0){
				address=list.get(0);
			}
			
			

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
			criteria = null;
		}
		
		if(null == list){
			list = new ArrayList<AddressBook>();
		}
		
		return address;
	}
	
	/**
	 * <p>Insert AddressBook done</p>
	 * @param candiAddressBookate
	 * @return
	 */
	public AddressBook insertAddressBook(AddressBook addressBook, HttpServletRequest requestParameters){
		Session session = null;
		 User userObj = (User)requestParameters.getSession().getAttribute("currUserObj");
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			Integer key =  getAddressBookIfExist(addressBook);
			if(key == 0){
				if(userObj!=null){
					addressBook.setCreatedBy(userObj.getStaffLoginId());
					addressBook.setModifiedBy(userObj.getStaffLoginId());
				}else{
					addressBook.setCreatedBy(addressBook.getAgentId());
					addressBook.setModifiedBy(addressBook.getAgentId());
				}
				addressBook.setCreationDate(new Date());
				addressBook.setModificationDate(new Date());
			
				session.save(addressBook);
			}else{
				AddressBook addressBookObj = (AddressBook)session.get(AddressBook.class, key);
				/*addressBookObj.setBirthDate(addressBook.getBirthDate());
				addressBookObj.setName(addressBook.getName());*/
				addressBookObj.setAge(addressBook.getAge());
				//addressBookObj.setGender(addressBook.getGender());
				if(addressBook.getEducation()!=null && addressBook.getEducation().length() > 0)
					addressBookObj.setEducation(addressBook.getEducation());
				if(addressBook.geteMailId()!=null && addressBook.geteMailId().length() > 0)
					addressBookObj.seteMailId(addressBook.geteMailId());
				if(addressBook.getWeChat()!=null && addressBook.getWeChat().length() > 0)
					addressBookObj.setWeChat(addressBook.getWeChat());
				if(addressBook.getNric()!=null && addressBook.getNric().length() > 0)
					addressBookObj.setNric(addressBook.getNric());
				if(addressBook.getMobilePhoneNo()!=null && addressBook.getMobilePhoneNo().length() > 0)
					addressBookObj.setMobilePhoneNo(addressBook.getMobilePhoneNo());
				addressBookObj.setModificationDate(new Date());
				if(userObj!=null)
					addressBookObj.setModifiedBy(userObj.getStaffLoginId());
				else
					addressBookObj.setModifiedBy(addressBook.getAgentId());
			    session.update(addressBookObj);
			    
			    addressBook = addressBookObj;
			}
			
			tx.commit();
		
		    log.log(Level.INFO,"---Insert To Address Book Successfully--- ");
			
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
	}
	
		return addressBook;
	}
	public Integer getAddressBookIfExist(AddressBook addressBook)
	{
		 Session session = null;
		 Integer key = 0; 
		try{
			session = HibernateFactory.openSession();
//			String queryString = "select addressCode from AddressBook where name=:name and  gender=:gender and CONVERT(DATE, birthDate)=:birthDate and agentId=:agentId ";
			String queryString = "select addressCode,birthDate from AddressBook where name=:name and  gender=:gender and agentId=:agentId ";
			if(null != addressBook.getCo() && !"".equals(addressBook.getCo())){
				queryString +=" and co=:co";
			}
			Query query = session.createQuery(queryString);
			query.setParameter("name",addressBook.getName());
			query.setParameter("gender",addressBook.getGender());
//			query.setParameter("birthDate",addressBook.getBirthDate());
			query.setParameter("agentId",addressBook.getAgentId());
			if(null != addressBook.getCo() && !"".equals(addressBook.getCo())){
				query.setParameter("co",addressBook.getCo());
			}
			
		    List<Object[]> l = query.list();
		    Date date = new Date();
		    if(l!=null && l.size() > 0){
		    	for(Object[] objs: l){
		    		date = (Date) objs[1];
		    		
		    		if(LMSUtil.dd_MM_yyyy.format(date).equals(LMSUtil.dd_MM_yyyy.format(addressBook.getBirthDate()))){
		    			key = (Integer)objs[0];
		    		}
		    	}
		    	
		    }
		  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookMaintenance",Level.SEVERE+"",errors.toString());
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
}
