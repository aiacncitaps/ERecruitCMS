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
 *                           khyati             created
 * 15-may-2015               khyati             Added Copy rights and security 
 ***************************************************************************** */

package com.quix.aia.cn.imo.mapper;

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
import com.quix.aia.cn.imo.data.bu.Bu;
import com.quix.aia.cn.imo.data.category.PresenterCategory;
import com.quix.aia.cn.imo.data.egreeting.E_Greeting;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.presenter.Presenter;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FormObj;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;




/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 
 * </p>
 * 
 * @author khyati
 * @version 1.0
 */

public class PresenterCategoryMaintenance {

	static Logger log = Logger.getLogger(PresenterCategoryMaintenance.class
			.getName());

	
	
	
	/**
	 * <p>
	 * This method checks all validations & sets values to bean
	 * </p>
	 * 
	 * @param PresenterCategory class object
	 * @param requestParameters  Servlet Request Parameter
	 * @return PresenterCategory object/Error object
	 */
	
	public Object mapForm1(PresenterCategory PresenterCategoryobj,
			HttpServletRequest requestParameters) {

		if (PresenterCategoryobj == null) {
			PresenterCategoryobj = new PresenterCategory();
			return PresenterCategoryobj;
		}
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		FormObj formObj = (FormObj) requestParameters.getSession()
				.getAttribute("formObj");
		if (formObj.getFormType().equals("NEW")) {

			if (requestParameters.getParameterValues("name")[0] == null
					|| requestParameters.getParameterValues("name")[0].length() == 0)
			return new ErrorObject("PresenterCategory Name", " field is required",localeObj);
			String name = requestParameters.getParameterValues("name")[0]
					.trim();
			int count = validatename(name);
			if (count != 0) {
				return new ErrorObject("�PresenterCategory� Already exists�",
						"",localeObj);
			}
		} else if (formObj.getFormType().equals("MODIFY")) {
			if (requestParameters.getParameterValues("name")[0] == null
					|| requestParameters.getParameterValues("name")[0].length() == 0)
			return new ErrorObject("PresenterCategory Name", " field is required",localeObj);

			String name = requestParameters.getParameterValues("name")[0]
					.trim();
			int count = validatename(name);
			if (count > 1) {
				return new ErrorObject("�PresenterCategory� Already exists�",
						"",localeObj);
			}
		}
		log.log(Level.INFO, "presenterCategoryNameMaintenance --> mapForm1 ");
		PresenterCategory PresenterCategoryclassObj = new PresenterCategory();
		PresenterCategoryclassObj.setPresenterCategoryName(requestParameters
				.getParameter("name"));
		PresenterCategoryclassObj.setStatus(true);
		PresenterCategoryclassObj.setToken(LMSUtil.getRendomToken());

		return PresenterCategoryclassObj;
	}


	/**
	 * <p>
	 * This method performs validation of duplicate name
	 * </p>
	 * @param name
	 * @return integer count value of name available in database.
	 */
	private int validatename(String name) {
		Session session = null;
		Transaction tx;
		int count = 0;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("select count(*) from PresenterCategory where status=1 and presenterCategoryName=:presenterCategoryName");
			query.setParameter("presenterCategoryName", name);

			count = (Integer) query.uniqueResult();

			tx.commit();
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return count;
	}

	
	/**
	 * <p>
	 * This method performs call for insertion method for PresenterCategory & audit trail for
	 * PresenterCategory add & sets message object in request
	 * </p>
	 * 
	 * @param PresenterCategory class object
	 * @param Servlet Request Parameter
	 * @return PresenterCategory class object
	 */
	public Object createNewPresenterCategory(
			PresenterCategory PresenterCategoryClassObj,
			HttpServletRequest requestParameters) {

		log.log(Level.INFO,
				"PresenterCategoryMaintenance --> createNewPresenterCategory ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute(
				"currUserObj");
		PresenterCategoryClassObj.setCreatedBy(userObj.getStaffLoginId());;
		PresenterCategoryClassObj.setCreationDate(new Date());
		PresenterCategoryClassObj.setModifiedBy(userObj.getStaffLoginId());
		PresenterCategoryClassObj.setModificationDate(new Date());

		int status = insertPresenterCategory(PresenterCategoryClassObj);

		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_PRESENTER_CATEGORY,
					AuditTrail.FUNCTION_CREATE, PresenterCategoryClassObj
							.toString()));
			msgObj = new MsgObject(
					"The new PresenterCategory Name has been successfully created.");
		} else {
			msgObj = new MsgObject(
					"The new PresenterCategory Name has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager",
				getAllPresenterCategoryListing());
		requestParameters.getSession().setAttribute("pager",
				getAllPresenterCategoryListing());
		return PresenterCategoryClassObj;
	}

	
	/**
	 * <p>
	 * This method performs insert of PresenterCategory
	 * </p>
	 * 
	 * @param PresenterCategory class Object
	 * @return int key 0 or 1
	 */
	public int insertPresenterCategory(
			PresenterCategory PresenterCategoryClassObj) {
		Integer key = 0;
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			key = (Integer) session.save(PresenterCategoryClassObj);
			tx.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		return key;
	}

	
	
	/**
	 * <p>
	 * This method performs for search listing and after insert listing
	 * </p>
	 * 
	 * @param 
	 
	 * @return Pager
	 */
	
	public Pager getAllPresenterCategoryListing() {
		// TODO Auto-generated method stub
		LinkedList item = new LinkedList();
		PresenterCategory PresenterCategoryObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllPresenterCategory();
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				PresenterCategoryObj = new PresenterCategory();
				PresenterCategoryObj = (PresenterCategory) vecAllRes.get(i);
				item.add(PresenterCategoryObj
						.getGetPresenterCategoryListingTableRow(i));
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
	 * This is search listing submethod
	 * </p>
	 * 
	 * @param 
	 * @return ArrayList
	 */
	
	public ArrayList getAllPresenterCategory() {
		Session session = null;
		Transaction tx;
		log.log(Level.INFO,
				"PresenterCategoryMaintanence --> getAllPresenterCategory");
		ArrayList arrActivity = new ArrayList();
		try {

			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery(" from PresenterCategory where status = 1 ");
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;
	}

	
	
	

	/**
	 * <p>
	 * This method performs view data edit time
	 * </p>
	 * 
	 * @param PresenterCategoryCode
	 * @return PresenterCategory  class object
	 */
	
	public PresenterCategory getPresenterCategory(int PresenterCategoryCode) {
		Session session = null;

		PresenterCategory PresenterCategoryClassObj = new PresenterCategory();
		try {
			session = HibernateFactory.openSession();

			PresenterCategoryClassObj = (PresenterCategory) session.get(
					PresenterCategory.class, PresenterCategoryCode);

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return PresenterCategoryClassObj;
	}



	
	/**
	 * <p>
	 * This method performs For update  PresenterCategory  data.
	 * </p>
	 * 
	 * @param PresenterCategory class object
	 * @param Servlet Request Parameter
	 * @return PresenterCategory  class object
	 */
	public PresenterCategory updatePresenterCategory(
			PresenterCategory PresenterCategoryClassObj,
			HttpServletRequest requestParameters) {
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			User userObj = (User) requestParameters.getSession().getAttribute(
					"currUserObj");
			PresenterCategoryClassObj.setPresenterCategoryCode(Integer
					.parseInt(requestParameters.getParameter("presenterCode")));
			PresenterCategoryClassObj.setModifiedBy(userObj.getStaffLoginId());
			PresenterCategoryClassObj.setModificationDate(new Date());

			session.update(PresenterCategoryClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_PRESENTER_CATEGORY,
					AuditTrail.FUNCTION_UPDATE, PresenterCategoryClassObj
							.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}

		}
		MsgObject msgObj = new MsgObject(
				"The  PresenterCategory has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager",
				getAllPresenterCategoryListing());
		requestParameters.getSession().setAttribute("pager",
				getAllPresenterCategoryListing());
		return PresenterCategoryClassObj;
	}

	

	
	
	
	/**
	 * <p>
	 * This method performs For delete presenter category.
	 * </p>
	 * 
	 * @param PresenterCategory class object
	 * @param Servlet Request Parameter
	 * @return
	 */
	public void deletePresenterCategory(int PresenterCategoryCode,
			HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		PresenterCategory PresenterCategoryObj = new PresenterCategory();
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from PresenterCategory where status=1 and presenterCategoryCode=:presenterCategoryCode");
			query.setParameter("presenterCategoryCode", PresenterCategoryCode);
			ArrayList<PresenterCategory> list = new ArrayList<PresenterCategory>();
			list = (ArrayList<PresenterCategory>) query.list();

			PresenterCategory PresenterCategoryClassObj = new PresenterCategory();
			PresenterCategoryClassObj = list.get(0);

			PresenterCategoryClassObj
					.setPresenterCategoryCode(PresenterCategoryCode);
			PresenterCategoryClassObj.setStatus(false);
			User userObj = new User();
			userObj = (User) req.getSession().getAttribute("currUserObj");

			PresenterCategoryClassObj.setModifiedBy(userObj.getStaffLoginId());
			PresenterCategoryClassObj.setModificationDate(new Date());
			session.update(PresenterCategoryClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_PRESENTER_CATEGORY,
					AuditTrail.FUNCTION_DELETE, PresenterCategoryClassObj
							.toString()));
			tx.commit();
			DeleteRelatedPresenter(PresenterCategoryCode, req);
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		MsgObject msgObj = new MsgObject(
				"The PresenterCategory Name has been successfully Deleted.");
		req.setAttribute("messageObject", msgObj);
		req.setAttribute("pager", getAllPresenterCategoryListing());
		req.getSession()
				.setAttribute("pager", getAllPresenterCategoryListing());

	}
	
	
	
	
	/**
	 * <p>
	 * This method performs For delete presenter based on related presenter category get deleted.
	 * </p>
	 * 
	 * @param PresenterCategoryCode 
	 * @param Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedPresenter(int PresenterCategoryCode, HttpServletRequest req) {
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Presenter where status=1 and category=:category");
			query.setParameter("category", PresenterCategoryCode);

			ArrayList<Presenter> list = new ArrayList<Presenter>();
			list = (ArrayList<Presenter>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Presenter PresenterClassObj = new Presenter();

					PresenterClassObj = list.get(i);
					PresenterClassObj.setPresenterCode(PresenterClassObj.getPresenterCode());
					PresenterClassObj.setStatus(false);

					User userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					PresenterClassObj.setModified_by(userObj.getStaffLoginId());
					PresenterClassObj.setModified_date(new Date());
					session.update(PresenterClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance
							.insertAuditTrail(new AuditTrail(userObj
									.getUser_no() + "",
									AuditTrail.MODULE_PRESENTERCategory_PRESENTER,
									AuditTrail.FUNCTION_DELETE, PresenterClassObj
											.toString()));
					tx.commit();

					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

	}
	
	
	
	
	/**
	 * <p>
	 * This method performs particular search .
	 * </p>
	 * 
	 * @param Servlet Request Parameter
	 * @return pager
	 */
	
	public Pager PresenterCategorySearchListing(
			HttpServletRequest requestParamters) {
		// TODO Auto-generated method stub
		LinkedList item = new LinkedList();
		PresenterCategory PresenterCategoryClassObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getSearchPresenterCategory(requestParamters);
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				PresenterCategoryClassObj = new PresenterCategory();
				PresenterCategoryClassObj = (PresenterCategory) vecAllRes
						.get(i);
				item.add(PresenterCategoryClassObj
						.getGetPresenterCategoryListingTableRow(i));
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
	 * This is sub method for particular search.
	 * </p>
	 * 
	 * @param Servlet Request Parameter
	 * @return ArrayList
	 */
	// 
	private ArrayList getSearchPresenterCategory(HttpServletRequest req) {

		log.log(Level.INFO,
				"PresenterCategoryMaintanence --> getSearchPresenterCategory");
		ArrayList arrActivity = new ArrayList();
		Session session = null;

		String str = "";
		if (!req.getParameter("name").equals("")) {
			str = str + " and presenterCategoryName like :name ";
		}

		try {

			session = HibernateFactory.openSession();

			Query query = session
					.createQuery(" from PresenterCategory where status = 1 "
							+ str + " ");
			if (!req.getParameter("name").equals("")) {
				query.setParameter("name","%"+ req.getParameter("name")+"%");

			}

			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;

	}

	public static final String Name = "name";

}
