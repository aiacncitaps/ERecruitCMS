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
 * 10-june-2015              khyati             comments added.
 * 18-Nov-2015             Jinatmayee           Error stored in db
 ***************************************************************************** */

package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.announcement.Announcement;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.branch.Branch;
import com.quix.aia.cn.imo.data.bu.Bu;
import com.quix.aia.cn.imo.data.city.City;
import com.quix.aia.cn.imo.data.district.District;
import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.holiday.Holiday;
import com.quix.aia.cn.imo.data.interview.Interview;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.office.Office;
import com.quix.aia.cn.imo.data.presenter.Presenter;
import com.quix.aia.cn.imo.data.ssc.Ssc;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FormObj;
import com.quix.aia.cn.imo.utilities.ImoUtilityData;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author khyati
 * @version 1.0
 */
public class BUMaintenance {
	static Logger log = Logger.getLogger(BUMaintenance.class.getName());

	/**
	 * <p>
	 * This method performs call for insertion method for Bu & audit trail for
	 * BU add & sets message object in request
	 * </p>
	 * 
	 * @param Bu
	 *            BuClassObj object
	 * @param req
	 *            Servlet Request Parameter
	 * @return Bu object
	 */

	public Object createNewBU(Bu BuClassObj,
			HttpServletRequest requestParameters) {

		log.log(Level.INFO, "BUMaintenance --> createNewBU ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute(
				"currUserObj");
		BuClassObj.setCreatedBy(userObj.getStaffLoginId());;
		BuClassObj.setCreationDate(new Date());
		BuClassObj.setModifiedBy(userObj.getStaffLoginId());
		BuClassObj.setModificationDate(new Date());
		int status = insertBU(BuClassObj);

		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_BU,
					AuditTrail.FUNCTION_CREATE, BuClassObj.toString()));
			msgObj = new MsgObject(
					"The new BU Name has been successfully created.");
		} else {
			msgObj = new MsgObject("The new BU Name has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllBUListing());
		requestParameters.getSession().setAttribute("pager", getAllBUListing());

		return BuClassObj;
	}

	/**
	 * <p>
	 * This method checks all validations & sets values to bean
	 * </p>
	 * 
	 * @param Bu
	 *            BUobj object
	 * @param requestParameters
	 *            Servlet Request Parameter
	 * @return Bu object/Error object
	 */
	public Object mapForm1(Bu BUobj, HttpServletRequest requestParameters) {
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if (BUobj == null) {
			BUobj = new Bu();
			return BUobj;
		}

		if (requestParameters.getParameter("name") == null
				|| requestParameters.getParameter("name").length() == 0)
		return new ErrorObject("BU Name", " field is required",localeObj);
		String name = requestParameters.getParameter("name").trim();

		String bucode = requestParameters.getParameter("bucode").trim();
		bucode = bucode == null || bucode.equals("") ? "0" : bucode;
		long count = validatename(name, Integer.parseInt(bucode));
		if (count != 0) {
			return new ErrorObject("BU Already exists", "",localeObj);
		}

		
		log.log(Level.INFO, "BUMaintenance --> mapForm1 ");
		Bu BUclassObj = new Bu();
		if(requestParameters.getParameter("order") == null){
			BUclassObj.setOrderCode(0);
		}else{
			try{
				Integer.parseInt(requestParameters.getParameter("order"));
			}catch(Exception e){
				
				return new ErrorObject("Order","requires numeric value",localeObj);
			}
			BUclassObj.setOrderCode(Integer.parseInt(requestParameters.getParameter("order")));
		}
		
		BUclassObj.setBuName(name);
		BUclassObj.setStatus(true);
		BUclassObj.setToken(LMSUtil.getRendomToken());

		return BUclassObj;
	}

	/**
	 * <p>
	 * This method performs validation of duplicate name
	 * </p>
	 * 
	 * @param name
	 * @param bucode
	 * @return integer count value of name available in database.
	 */

	private long validatename(String name, int bucode) {
		long count = 0;
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("select count(*) from Bu where status=true and buName=:buName and buCode!=:buCode");
			query.setParameter("buName", name);
			query.setParameter("buCode", bucode);
			count = (Long) query.uniqueResult();

			tx.commit();
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method performs insert of BU
	 * </p>
	 * 
	 * @param Bu
	 *            Class Object
	 * @return int key 0 or 1
	 */
	public int insertBU(Bu BuClassObj) {

		Session session = null;
		Transaction tx = null;
		Integer key = 0;

		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			key = (Integer) session.save(BuClassObj);
			tx.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method performs Delete of BU
	 * </p>
	 * 
	 * @param BUCode
	 * @param req
	 *            Servlet Request Parameter
	 * @return
	 */
	public void deleteBU(int BUCode, HttpServletRequest req) {

		Bu BuObj = new Bu();
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Bu where status=1 and buCode=:buCode");
			query.setParameter("buCode", BUCode);

			ArrayList<Bu> list = new ArrayList<Bu>();
			list = (ArrayList<Bu>) query.list();

			Bu BuClassObj = new Bu();
			BuClassObj = list.get(0);

			BuClassObj.setBuCode(BUCode);
			BuClassObj.setStatus(false);
			User userObj = new User();
			userObj = (User) req.getSession().getAttribute("currUserObj");

			BuClassObj.setModifiedBy(userObj.getStaffLoginId());
			BuClassObj.setModificationDate(new Date());
			session.update(BuClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_BU,
					AuditTrail.FUNCTION_DELETE, BuClassObj.toString()));
			tx.commit();
			
			
			ImoUtilityData imoutil=new ImoUtilityData();
			imoutil.DeleteRelatedDistrict(BUCode, req);
			
			//DeleteRelatedUser(BUCode, req);
			DeleteRelatedAnnouncement(BUCode, req);
			//DeleteRelatedholiday(BUCode, req);
			DeleteRelatedPresenter(BUCode, req);
			DeleteRelatedSchedule(BUCode, req);
			DeleteRelatedInterview(BUCode, req);
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		MsgObject msgObj = new MsgObject(
				"The BU Name has been successfully Deleted.");
		req.setAttribute("messageObject", msgObj);
		req.setAttribute("pager", getAllBUListing());
		req.getSession().setAttribute("pager", getAllBUListing());

	}



	/**
	 * <p>
	 * This method performs Delete of User related to BU Delete
	 * </p>
	 * 
	 * @param BUCode
	 * @param req
	 *            Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedUser(int BUCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from User where status=1 and buCode=:buCode");
			query.setParameter("buCode", BUCode);

			ArrayList<User> list = new ArrayList<User>();
			list = (ArrayList<User>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					User UserClassObj = new User();

					UserClassObj = list.get(i);
					UserClassObj.setUser_no(UserClassObj.getUser_no());
					// DeleteRelatedCity(UserClassObj.getDistrictCode(), req);
					UserClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					UserClassObj.setModifiedBy(userObj.getStaffLoginId());
					UserClassObj.setModificationDate(new Date());
					session.update(UserClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance
							.insertAuditTrail(new AuditTrail(userObj
									.getUser_no() + "",
									AuditTrail.MODULE_BU_USER,
									AuditTrail.FUNCTION_DELETE, UserClassObj
											.toString()));
					tx.commit();

					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Announcement related to BU Delete
	 * </p>
	 * 
	 * @param BUCode
	 * @param req
	 *            Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedAnnouncement(int BUCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Announcement where status=1 and buCode=:buCode");
			query.setParameter("buCode", BUCode);

			ArrayList<Announcement> list = new ArrayList<Announcement>();
			list = (ArrayList<Announcement>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Announcement AnnouncementClassObj = new Announcement();

					AnnouncementClassObj = list.get(i);
					AnnouncementClassObj
							.setAnnoucement_code(AnnouncementClassObj
									.getAnnoucement_code());
					// DeleteRelatedCity(UserClassObj.getDistrictCode(), req);
					AnnouncementClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					AnnouncementClassObj.setModifiedBy(userObj.getStaffLoginId());
					AnnouncementClassObj.setModificationDate(new Date());
					session.update(AnnouncementClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance.insertAuditTrail(new AuditTrail(
							userObj.getUser_no() + "",
							AuditTrail.MODULE_BU_ANNOUNCEMENT,
							AuditTrail.FUNCTION_DELETE, AnnouncementClassObj
									.toString()));
					tx.commit();
					// DeleteRelatedCity(DistrictClassObj.getDistrictCode(),req);
					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		} /*
		 * finally { try { HibernateFactory.close(session);
		 * 
		 * } catch (Exception e) { log.log(Level.SEVERE, e.getMessage());
		 * e.printStackTrace(); } }
		 */

	}

	/**
	 * <p>
	 * This method performs Delete of Holiday related to BU Delete
	 * </p>
	 * 
	 * @param BUCode
	 * @param req
	 *            Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedholiday(int BUCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Holiday where status=1 and buCode=:buCode");
			query.setParameter("buCode", BUCode);

			ArrayList<Holiday> list = new ArrayList<Holiday>();
			list = (ArrayList<Holiday>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Holiday holidayClassObj = new Holiday();

					holidayClassObj = list.get(i);
					holidayClassObj.setHolidayCode(holidayClassObj
							.getHolidayCode());
					// DeleteRelatedCity(UserClassObj.getDistrictCode(), req);
					holidayClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					holidayClassObj.setModifiedBy(userObj.getStaffLoginId());
					holidayClassObj.setModificationDate(new Date());
					session.update(holidayClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance.insertAuditTrail(new AuditTrail(
							userObj.getUser_no() + "",
							AuditTrail.MODULE_BU_HOLIDAY,
							AuditTrail.FUNCTION_DELETE, holidayClassObj
									.toString()));
					tx.commit();
					// DeleteRelatedCity(DistrictClassObj.getDistrictCode(),req);
					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		} /*
		 * finally { try { HibernateFactory.close(session);
		 * 
		 * } catch (Exception e) { log.log(Level.SEVERE, e.getMessage());
		 * e.printStackTrace(); } }
		 */

	}

	/**
	 * <p>
	 * This method performs Delete of Presenter related to BU Delete
	 * </p>
	 * 
	 * @param BUCode
	 * @param req
	 *            Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedPresenter(int BUCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Presenter where status=1 and buCode=:buCode");
			query.setParameter("buCode", BUCode);

			ArrayList<Presenter> list = new ArrayList<Presenter>();
			list = (ArrayList<Presenter>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Presenter PresenterClassObj = new Presenter();

					PresenterClassObj = list.get(i);
					PresenterClassObj.setPresenterCode(PresenterClassObj
							.getPresenterCode());

					PresenterClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					PresenterClassObj.setModified_by(userObj.getStaffLoginId());
					PresenterClassObj.setModified_date(new Date());
					session.update(PresenterClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance.insertAuditTrail(new AuditTrail(
							userObj.getUser_no() + "",
							AuditTrail.MODULE_BU_PRESENTER,
							AuditTrail.FUNCTION_DELETE, PresenterClassObj
									.toString()));
					tx.commit();

					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Schedule related to BU Delete
	 * </p>
	 * 
	 * @param BUCode
	 * @param req
	 *            Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedSchedule(int BUCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Event where status=1 and buCode=:buCode");
			query.setParameter("buCode", BUCode);

			ArrayList<Event> list = new ArrayList<Event>();
			list = (ArrayList<Event>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Event EventClassObj = new Event();

					EventClassObj = list.get(i);
					EventClassObj.setEvent_code(EventClassObj.getEvent_code());
					// DeleteRelatedCity(UserClassObj.getDistrictCode(), req);
					EventClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					EventClassObj.setModifiedBy(userObj.getStaffLoginId());
					EventClassObj.setModificationDate(new Date());
					session.update(EventClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance.insertAuditTrail(new AuditTrail(
							userObj.getUser_no() + "",
							AuditTrail.MODULE_BU_SCHEDULE_EVENT,
							AuditTrail.FUNCTION_DELETE, EventClassObj
									.toString()));
					tx.commit();
					// DeleteRelatedCity(DistrictClassObj.getDistrictCode(),req);
					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		} /*
		 * finally { try { HibernateFactory.close(session);
		 * 
		 * } catch (Exception e) { log.log(Level.SEVERE, e.getMessage());
		 * e.printStackTrace(); } }
		 */

	}

	/**
	 * <p>
	 * This method performs Delete of Interview related to BU Delete
	 * </p>
	 * 
	 * @param BUCode
	 * @param req
	 *            Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedInterview(int BUCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Interview where status=1 and buCode=:buCode");
			query.setParameter("buCode", BUCode);

			ArrayList<Interview> list = new ArrayList<Interview>();
			list = (ArrayList<Interview>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Interview InterviewClassObj = new Interview();

					InterviewClassObj = list.get(i);
					InterviewClassObj.setInterview_code(InterviewClassObj
							.getInterview_code());

					InterviewClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					InterviewClassObj.setModifiedBy(userObj.getStaffLoginId());
					InterviewClassObj.setModificationDate(new Date());
					session.update(InterviewClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance.insertAuditTrail(new AuditTrail(
							userObj.getUser_no() + "",
							AuditTrail.MODULE_BU_INTERVIEW,
							AuditTrail.FUNCTION_DELETE, InterviewClassObj
									.toString()));
					tx.commit();

					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	

	/**
	 * <p>
	 * This method performs all BU listing
	 * </p>
	 * 
	 * @param BUCode
	 * @return Pager
	 */
	public Pager getAllBUListing() {

		LinkedList item = new LinkedList();
		Bu BuObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllBU();
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				BuObj = new Bu();
				BuObj = (Bu) vecAllRes.get(i);
				item.add(BuObj.getGetBUListingTableRow(i));
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
	 * This method performs all BU listing
	 * </p>
	 * 
	 * @param BUCode
	 * @return ArrayList
	 */
	public ArrayList getAllBU() {

		Session session = null;
		Transaction tx;
		log.log(Level.INFO, "BUMaintanence --> getAllBU");
		ArrayList arrActivity = new ArrayList();
		try {

			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session.createQuery(" from Bu where status = 1 order by orderCode,buName ");
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;
	}

	/**
	 * <p>
	 * This method performs edit view of particular BU based on BuCode
	 * </p>
	 * 
	 * @param BUCode
	 * @return Bu class object
	 */
	public Bu getBU(int buCode) {
		Session session = null;
		Transaction tx = null;
		Bu BuClassObj = new Bu();
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			BuClassObj = (Bu) session.get(Bu.class, buCode);

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return BuClassObj;
	}

	/**
	 * <p>
	 * This method performs update of particular BU based on BuCode
	 * </p>
	 * 
	 * @param Bu
	 *            class Object
	 * @param req
	 *            Servlet Request Parameter
	 * @return Bu class object
	 */
	public Bu updateBU(Bu BuClassObj, HttpServletRequest requestParameters) {

		Session session = null;
		Transaction tx;

		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			User userObj = new User();
			userObj = (User) requestParameters.getSession().getAttribute(
					"currUserObj");
			BuClassObj.setBuCode(Integer.parseInt(requestParameters
					.getParameter("bucode")));
			BuClassObj.setModifiedBy(userObj.getStaffLoginId());
			BuClassObj.setModificationDate(new Date());

			session.update(BuClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_BU,
					AuditTrail.FUNCTION_UPDATE, BuClassObj.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}

		}
		MsgObject msgObj = new MsgObject(
				"The BU Name has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllBUListing());
		requestParameters.getSession().setAttribute("pager", getAllBUListing());

		return BuClassObj;
	}

	/**
	 * <p>
	 * This method performs search of BU
	 * </p>
	 * 
	 * @param req
	 *            Servlet Request Parameter
	 * @return Pager
	 */
	public Pager BUSearchListing(HttpServletRequest requestParamters) {
		// TODO Auto-generated method stub
		LinkedList item = new LinkedList();
		Bu BuClassObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getSearchBU(requestParamters);
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				BuClassObj = new Bu();
				BuClassObj = (Bu) vecAllRes.get(i);
				item.add(BuClassObj.getGetBUListingTableRow(i));
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
	 * This method performs search of BU
	 * </p>
	 * 
	 * @param req
	 *            Servlet Request Parameter
	 * @return Pager
	 */
	private ArrayList getSearchBU(HttpServletRequest req) {

		Session session = null;

		log.log(Level.INFO, "BUMaintanence --> getSearchBU");
		ArrayList arrActivity = new ArrayList();
		String str = "";
		if (!req.getParameter("name").equals("")) {
			str = str + " and buName LIKE :name ";
		}

		try {

			session = HibernateFactory.openSession();

			Query query = session.createQuery(" from Bu where status = 1 "
					+ str + " order by orderCode,buName ");
			if (!req.getParameter("name").equals("")) {
				query.setParameter("name","%"+ req.getParameter("name")+"%");

			}

			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;

	}

	/**
	 * <p>
	 * This method performs search of BU
	 * </p>
	 * 
	 * @param req
	 *            Servlet Request Parameter
	 * @return arraylist
	 */
	public ArrayList getSearchedBU(HttpServletRequest req) {
		ArrayList BUList = new ArrayList();
		Session session = null;

		String NAME = req.getParameter(Name);

		try {
			session = HibernateFactory.openSession();

			Criteria crit = session.createCriteria(Bu.class);
			if (NAME != null && NAME.length() > 0)
				crit.add(Restrictions.eq("Name", NAME));

			List l = crit.list();
			Iterator it = l.iterator();

			while (it.hasNext()) {
				Bu BuClassObj = (Bu) it.next();
				BuClassObj.setBuCode(BuClassObj.getBuCode());
				BuClassObj.setBuName(BuClassObj.getBuName());

				BuClassObj.setCreatedBy(BuClassObj.getCreatedBy());
				BuClassObj.setCreationDate(BuClassObj.getCreationDate());

				BUList.add(BuClassObj);

			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return BUList;
	}

	public static final String Name = "name";

}
