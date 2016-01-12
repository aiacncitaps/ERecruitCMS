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
 *                          			 khyati             created
 * 15-may-2015               khyati             Added Copy rights and security 
 * 18-Nov-2015               Nibedita          Error stored in db
 ***************************************************************************** */

package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.category.FestiveCategory;
import com.quix.aia.cn.imo.data.category.PresenterCategory;
import com.quix.aia.cn.imo.data.egreeting.E_Greeting;
import com.quix.aia.cn.imo.data.egreeting.E_GreetingMaterial;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FormObj;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;



/**
 * <p>
 * This class defines the data operations like Add,Update,Delete,search for Festive Category

 * </p>
 * 
 * @author khyati
 * @version 1.0
 */

public class FestiveCategoryMaintenance {

	static Logger log = Logger.getLogger(FestiveCategoryMaintenance.class
			.getName());
	
	
	
	
	/**
	 * <p>
	 * This method is for search listing and after insert listing
	 * </p>
	 * 
	 * @param 
	 * @param 
	 * @return Pager
	 */
	
	public Pager getAllFestiveCategoryListing() {

		LinkedList item = new LinkedList();
		FestiveCategory FestiveCategoryObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllFestiveCategory();
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				FestiveCategoryObj = new FestiveCategory();
				FestiveCategoryObj = (FestiveCategory) vecAllRes.get(i);
				item.add(FestiveCategoryObj
						.getGetFestiveCategoryListingTableRow(i));
			}
		}
		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);

		for (; item.size() % 10 != 0; item.add("<tr></tr>"))
			;
		pager.setItems(item);
		return pager;
	}

	
	/**
	 * <p>
	 * This method is for search listing submethod
	 * </p>
	 * 
	 * @param 
	 * @return ArrayList
	 */
	
	
	public ArrayList getAllFestiveCategory() {

		Session session = null;

		log.log(Level.INFO,
				"FestiveCategoryMaintanence --> getAllFestiveCategory");
		ArrayList arrActivity = new ArrayList();
		LogsMaintenance logsMain=new LogsMaintenance();
		try {

			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);

			Query query = session
					.createQuery(" from FestiveCategory where status = 1 ");
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			session.setDefaultReadOnly(false);
			HibernateFactory.close(session);
		}

		return arrActivity;
	}

	
	/**
	 * <p>
	 * This method is for particular search method
	 * </p>
	 * 
	 * @param requestParameters  Servlet Request Parameter
	 * @return Pager
	 */
	
	public Pager FestiveCategorySearchListing(
			HttpServletRequest requestParamters) {

		LinkedList item = new LinkedList();
		FestiveCategory FestiveCategoryClassObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getSearchFestiveCategory(requestParamters);
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				FestiveCategoryClassObj = new FestiveCategory();
				FestiveCategoryClassObj = (FestiveCategory) vecAllRes.get(i);
				item.add(FestiveCategoryClassObj
						.getGetFestiveCategoryListingTableRow(i));
			}
		}
		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);
		for (; item.size() % 10 != 0; item.add("<tr></tr>"))
			;
		pager.setItems(item);
		return pager;

	}

	
	/**
	 * <p>
	 * This method is for sub method for particular search
	 * </p>
	 * 
	 * @param requestParameters  Servlet Request Parameter
	 * @return Pager
	 */
	
	private ArrayList getSearchFestiveCategory(HttpServletRequest req) {

		Session session = null;

		log.log(Level.INFO,
				"FestiveCategoryMaintanence --> getSearchFestiveCategory");
		ArrayList arrActivity = new ArrayList();
		String str = "";
		if (!req.getParameter("name").equals("")) {
			str = str + " and FestiveCategoryName like :name ";
		}
		LogsMaintenance logsMain=new LogsMaintenance();
		try {

			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);

			Query query = session
					.createQuery(" from FestiveCategory where status = 1 "
							+ str + " ");
			if (!req.getParameter("name").equals("")) {
				query.setParameter("name", "%"+req.getParameter("name")+"%");

			}

			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			session.setDefaultReadOnly(false);
			HibernateFactory.close(session);
		}

		return arrActivity;

	}
	
	
	
	/**
	 * <p>
	 * This method checks all validations & sets values to bean.
	 * </p>
	 * 
	 * @param FestiveCategory FestiveCategoryobj object
	 * @param requestParameters  Servlet Request Parameter
	 * @return FestiveCategory object/Error object
	 */
   
	public Object mapForm1(FestiveCategory FestiveCategoryobj,
			HttpServletRequest requestParameters) {

		if (FestiveCategoryobj == null) {
			FestiveCategoryobj = new FestiveCategory();
			return FestiveCategoryobj;
		}
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		FormObj formObj = (FormObj) requestParameters.getSession()
				.getAttribute("formObj");

		if (requestParameters.getParameterValues("name")[0] == null
				|| requestParameters.getParameterValues("name")[0].length() == 0)
		return new ErrorObject("Festive Category Name", " field is required",localeObj);

		String name = requestParameters.getParameterValues("name")[0].trim();
		String festiveCode = requestParameters
				.getParameterValues("festiveCode")[0].trim();// for validation
																// check come
																// from jsp
																// hidden filed
		festiveCode = festiveCode == null || festiveCode.equals("") ? "0"
				: festiveCode;
		long count = validatename(name, Integer.parseInt(festiveCode));
		if (count != 0) {
			return new ErrorObject("ï¿½FestiveCategoryï¿½ Already existsï¿½", "",localeObj);
		}

		log.log(Level.INFO, "FestiveCategoryMaintenance --> mapForm1 ");
		FestiveCategory FestiveCategoryclassObj = new FestiveCategory();
		FestiveCategoryclassObj.setFestiveCategoryName(requestParameters
				.getParameter("name"));
		FestiveCategoryclassObj.setStatus(true);
		FestiveCategoryclassObj.setToken(LMSUtil.getRendomToken());

		return FestiveCategoryclassObj;
	}

	
	
	/**
	 * <p>
	 * This method performs validation of duplicate name
	 * </p>
	 * @param name
	 * @param FestiveCategoryCode
	 * @return integer count value of name available in database.
	 */
	
	private long validatename(String name, int FestiveCategoryCode) {
		long count = 0;
		Session session = null;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			session = HibernateFactory.openSession();

			Query query = session
					.createQuery("select count(*) from FestiveCategory where status=true and FestiveCategoryName=:FestiveCategoryName and festiveCategoryCode!=:festiveCategoryCode");
			query.setParameter("FestiveCategoryName", name);
			query.setParameter("festiveCategoryCode", FestiveCategoryCode);

			count = (Long) query.uniqueResult();

			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
			}
		}

		return count;
	}



	/**
	 * <p>
	 * This method performs call for insertion method for FestiveCategory & audit trail for
	 * FestiveCategory add & sets message object in request
	 * </p>
	 * 
	 * @param FestiveCategory class object
	 * @param Servlet Request Parameter
	 * @return FestiveCategory class object
	 */
	public Object createNewFestiveCategory(
			FestiveCategory FestiveCategoryClassObj,
			HttpServletRequest requestParameters) {

		log.log(Level.INFO,
				"FestiveCategoryMaintenance --> createNewFestiveCategory ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute(
				"currUserObj");
		FestiveCategoryClassObj.setCreatedBy(userObj.getStaffLoginId());;
		FestiveCategoryClassObj.setCreationDate(new Date());
		FestiveCategoryClassObj.setModifiedBy(userObj.getStaffLoginId());
		FestiveCategoryClassObj.setModificationDate(new Date());
		int status = insertFestiveCategory(FestiveCategoryClassObj);

		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_PRESENTER_CATEGORY,
					AuditTrail.FUNCTION_CREATE, FestiveCategoryClassObj
							.toString()));
			msgObj = new MsgObject(
					"The new FestiveCategory Name has been successfully created.");
		} else {
			msgObj = new MsgObject(
					"The new FestiveCategory Name has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllFestiveCategoryListing());
		requestParameters.getSession().setAttribute("pager",
				getAllFestiveCategoryListing());
		return FestiveCategoryClassObj;
	}



	/**
	 * <p>
	 * This method performs insert of FestiveCategory
	 * </p>
	 * 
	 * @param FestiveCategory class object
	 * @return int key 0 or 1
	 */
	public int insertFestiveCategory(FestiveCategory FestiveCategoryClassObj) {
		Integer key = 0;
		Session session = null;
		Transaction tx = null;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			key = (Integer) session.save(FestiveCategoryClassObj);
			tx.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
			}
		}
		return key;
	}

	

	/**
	 * <p>
	 * This method performs view data at the edit time
	 * </p>
	 * 
	 * @param FestiveCategoryCode
	 * @return FestiveCategory object
	 */
	public FestiveCategory getFestiveCategory(int FestiveCategoryCode) {
		Session session = null;
		LogsMaintenance logsMain=new LogsMaintenance();
		FestiveCategory FestiveCategoryClassObj = new FestiveCategory();
		try {
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);

			FestiveCategoryClassObj = (FestiveCategory) session.get(
					FestiveCategory.class, FestiveCategoryCode);

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
			}
		}

		return FestiveCategoryClassObj;
	}

	
	
	
	/**
	 * <p>
	 * This method performs For update data
	 * </p>
	 * 
	 * @param FestiveCategory class object
	 * @return FestiveCategory object
	 */
	
	public FestiveCategory updateFestiveCategory(
			FestiveCategory FestiveCategoryClassObj,
			HttpServletRequest requestParameters) {
		Session session = null;
		Transaction tx;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {

			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			User userObj = (User) requestParameters.getSession().getAttribute(
					"currUserObj");
			FestiveCategoryClassObj.setfestiveCategoryCode(Integer
					.parseInt(requestParameters.getParameter("festiveCode")));
			FestiveCategoryClassObj.setModifiedBy(userObj.getStaffLoginId());
			FestiveCategoryClassObj.setModificationDate(new Date());

			session.update(FestiveCategoryClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_FESTIVE_CATEGORY,
					AuditTrail.FUNCTION_UPDATE, FestiveCategoryClassObj
							.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {

				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
			}

		}
		MsgObject msgObj = new MsgObject(
				"The  FestiveCategory has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllFestiveCategoryListing());
		requestParameters.getSession().setAttribute("pager",
				getAllFestiveCategoryListing());

		return FestiveCategoryClassObj;
	}

	
	
	
	
	/**
	 * <p>
	 * This method performs For delete FestiveCategory
	 * </p>
	 * 
	 * @param FestiveCategoryCode
	 * @param Servlet Request Parameter
	 * @return 
	 */
	
	public void deleteFestiveCategory(int FestiveCategoryCode,
			HttpServletRequest req) {

		FestiveCategory FestiveCategoryObj = new FestiveCategory();
		Session session = null;
		Transaction tx;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {

			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			DeleteRelatedEgreeting(FestiveCategoryCode, req,session,tx);
			
			
			Query query = session
					.createQuery("from FestiveCategory where status=1 and festiveCategoryCode=:festiveCategoryCode");
			query.setParameter("festiveCategoryCode", FestiveCategoryCode);
			ArrayList<FestiveCategory> list = new ArrayList<FestiveCategory>();
			list = (ArrayList<FestiveCategory>) query.list();

			
			FestiveCategory FestiveCategoryClassObj = new FestiveCategory();
			FestiveCategoryClassObj = list.get(0);

			FestiveCategoryClassObj.setfestiveCategoryCode(FestiveCategoryCode);
			FestiveCategoryClassObj.setStatus(false);

			User userObj = (User) req.getSession().getAttribute("currUserObj");

			FestiveCategoryClassObj.setModifiedBy(userObj.getStaffLoginId());
			FestiveCategoryClassObj.setModificationDate(new Date());
			session.update(FestiveCategoryClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_FESTIVE_CATEGORY,
					AuditTrail.FUNCTION_DELETE, FestiveCategoryClassObj
							.toString()));
			tx.commit();
			
			session.flush();
			// req.setAttribute("pager",getAllBUListing());

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
			}
		}
		MsgObject msgObj = new MsgObject(
				"The FestiveCategory Name has been successfully Deleted.");
		req.setAttribute("messageObject", msgObj);
		req.setAttribute("pager", getAllFestiveCategoryListing());
		req.getSession().setAttribute("pager", getAllFestiveCategoryListing());

	}
	
	
	/**
	 * <p>
	 * This method performs delete of Egreeting based on deleted FestiveCategory.
	 * </p>
	 * 
	 * @param FestiveCategoryCode
	 * @param Servlet Request Parameter
	 * @return 
	 */
	
	private void DeleteRelatedEgreeting(int festiveCategoryCode, HttpServletRequest req,Session session,Transaction tx) {
		
		
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			/*session = HibernateFactory.openSession();
			tx = session.beginTransaction();
*/
			Query query = session
					.createQuery("from E_Greeting where status=1 and festive_category=:festive_category");
			query.setParameter("festive_category", festiveCategoryCode);

			ArrayList<E_Greeting> list = new ArrayList<E_Greeting>();
			list = (ArrayList<E_Greeting>) query.list();

			if (list.size() != 0) {

				for (E_Greeting E_GreetingClassObj:list) {

					query=session.createQuery("from E_GreetingMaterial where eGreetingCode=:code");
					query.setParameter("code", E_GreetingClassObj.getEGreetingCode());
					ArrayList<E_GreetingMaterial> listmaterial=new ArrayList<E_GreetingMaterial>();
					listmaterial=(ArrayList<E_GreetingMaterial>) query.list();
					for(E_GreetingMaterial eMaterial:listmaterial){
						session.delete(eMaterial);
					}
					
					
					
					User userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					E_GreetingClassObj.setEGreetingCode(E_GreetingClassObj.getEGreetingCode());
					E_GreetingClassObj.setStatus(false);
					E_GreetingClassObj.setModified_by(userObj.getStaffLoginId());
					E_GreetingClassObj.setModified_date(new Date());
					session.update(E_GreetingClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance
							.insertAuditTrail(new AuditTrail(userObj
									.getUser_no() + "",
									AuditTrail.MODULE_E_GREETING,
									AuditTrail.FUNCTION_DELETE, E_GreetingClassObj
											.toString()));
					/*tx.commit();

					session.flush();*/
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
		} /*finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("FestiveCategoryMaintenance",Level.SEVERE+"",errors.toString());
			}
		}*/

	}
	
	

	public static final String Name = "name";

}
