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
 * 10-june-2015              khyati             Document comments added.
 * 18-Nov-2015              Jinatmayee          Error stored in db
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
import com.quix.aia.cn.imo.utilities.ImoUtilityData;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;
import com.quix.aia.cn.imo.mapper.BUMaintenance;
import com.quix.aia.cn.imo.utilities.FormObj;


/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author khyati
 * @version 1.0
 */
public class DistrictMaintenance {
	static Logger log = Logger.getLogger(DistrictMaintenance.class.getName());

	
	
	/**
	 * <p>
	 * This method performs call for insertion method for District & audit trail for
	 * District add & sets message object in request
	 * </p>
	 * 
	 * @param District DistrictClassObj object
	 * @param req Servlet Request Parameter
	 * @return District DistrictClassObj
	 */
	public Object createNewDistrict(District DistrictClassObj,
			HttpServletRequest requestParameters) {

		log.log(Level.INFO, "DistrictMaintenance --> createNewDistrict ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute(
				"currUserObj");
		DistrictClassObj.setCreatedBy(userObj.getStaffLoginId());;
		DistrictClassObj.setCreationDate(new Date());
		DistrictClassObj.setModifiedBy(userObj.getStaffLoginId());
		DistrictClassObj.setModificationDate(new Date());
		int status = insertDistrict(DistrictClassObj);
		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_DISTRICT,
					AuditTrail.FUNCTION_CREATE, DistrictClassObj.toString()));
			msgObj = new MsgObject(
					"The new District has been successfully created.");
		} else {
			msgObj = new MsgObject("The new District has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllDistrictListing());
		requestParameters.getSession().setAttribute("pager",getAllDistrictListing());
		return DistrictClassObj;
	}

	
	/**
	 * <p>
	 * This method performs insert of District
	 * </p>
	 * 
	 * @param District Class Object
	 * @return int key 0 or 1
	 */
	public int insertDistrict(District DistrictClassObj) {
		Session session = null;
		Transaction tx;
		Integer key = 0;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			key = (Integer) session.save(DistrictClassObj);
			tx.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method performs listing of default search result of  District Record 
	 * </p>
	 * 
	 * @param
	 * @return  pager
	 */
	public Pager getAllDistrictListing() {

		LinkedList item = new LinkedList();
		District DistrictObj = null;
		ArrayList listDistrict = new ArrayList();
		listDistrict = getAllDistrict();
		if (listDistrict.size() != 0) {
			for (int i = 0; i < listDistrict.size(); i++) {
				DistrictObj = new District();
				DistrictObj = (District) listDistrict.get(i);
				item.add(DistrictObj.getGetDistrictListingTableRow(i));
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
	 * This is sub method of default search listing method.
	 * </p>
	 * 
	 * @param
	 * @return ArrayList
	 */
	public ArrayList getAllDistrict() {

		log.log(Level.INFO, "DistrictMaintanence --> getAllDistrict");
		ArrayList listDitrict = new ArrayList();
		Session session = null;

		try {

			session = HibernateFactory.openSession();

			Query query = session
					.createQuery(" from District where status = 1 order by orderCode,districtName ");
			listDitrict = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return listDitrict;
	}

	
	
	
	/**
	 * <p>
	 * This method checks all validations & sets values to bean
	 * </p>
	 * 
	 * @param District class object
	 * @param requestParamet  Servlet Request Parameter
	 * @return District object/Error object
	 */
	public Object mapForm1(District Districtobj,
			HttpServletRequest requestParameters) {
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if (Districtobj == null) {
			Districtobj = new District();
			return Districtobj;
		}

		if (requestParameters.getParameter("bu") == null
				|| requestParameters.getParameter("bu").equals("0"))
			return new ErrorObject("BU Name", " field is required",localeObj);
		if (requestParameters.getParameter("name") == null
				|| requestParameters.getParameter("name").length() == 0)
		return new ErrorObject("District Name", " field is required",localeObj);
		String name = requestParameters.getParameterValues("name")[0].trim();
		String districtcode = requestParameters
				.getParameterValues("districtcode")[0].trim();
		districtcode = districtcode == null || districtcode.equals("") ? "0"
				: districtcode;
		long count = validatename(name, Integer.parseInt(districtcode));
		if (count != 0) {
			return new ErrorObject("District Already exists", "",localeObj);
		}

		log.log(Level.INFO, "DistrictMaintenance --> mapForm1 ");

		if(requestParameters.getParameter("order") == null){
			Districtobj.setOrderCode(0);
		}else{
			try{
				Integer.parseInt(requestParameters.getParameter("order"));
			}catch(Exception e){
				return new ErrorObject("Order","requires numeric value",localeObj);
			}
			Districtobj.setOrderCode(Integer.parseInt(requestParameters.getParameter("order")));
		}
		
		Districtobj.setDistrictName(requestParameters.getParameter("name"));
		Districtobj.setBuCode(Integer.parseInt(requestParameters
				.getParameterValues("bu")[0]));
		Districtobj.setStatus(true);
		Districtobj.setToken(LMSUtil.getRendomToken());
		

		return Districtobj;
	}

	
	
	
	/**
	 * <p>
	 * This method performs validation of duplicate name
	 * </p>
	 * 
	 * @param name
	 * @param districtCode
	 * @return integer count value of name available in database.
	 */
	private long validatename(String name, int districtCode) {
		Session session = null;

		long count = 0;
		try {
			session = HibernateFactory.openSession();

			Query query = session
					.createQuery("select count(*) from District where status=true and districtName=:districtName and districtCode!=:districtCode");
			query.setParameter("districtName", name);
			query.setParameter("districtCode", districtCode);

			count = (Long) query.uniqueResult();

			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method performs listing of particular search result of District Record based on criteria.
	 * </p>
	 * 
	 * @param  requestParameters Servlet Request Parameter
	 * @return  pager
	 */
	public Pager DistrictSearchListing(HttpServletRequest requestParamters) {

		LinkedList item = new LinkedList();
		District DistrictClassObj = null;
		ArrayList AllDistrict = new ArrayList();

		AllDistrict = getSearchDistrict(requestParamters);
		if (AllDistrict.size() != 0) {
			for (int i = 0; i < AllDistrict.size(); i++) {
				DistrictClassObj = new District();
				DistrictClassObj = (District) AllDistrict.get(i);
				item.add(DistrictClassObj.getGetDistrictListingTableRow(i));
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
	 * This is sub method for particular search listing method based of search criteria.
	 * </p>
	 * 
	 * @param  requestParameters Servlet Request Parameter
	 * @return ArrayList
	 */
	private ArrayList getSearchDistrict(HttpServletRequest req) {

		log.log(Level.INFO, "DistrictMaintanence --> getSearchDistrict");
		ArrayList arrActivity = new ArrayList();
		Session session = null;

		String str = "";
		if (!req.getParameter("name").equals("")) {
			str = str + " and districtName LIKE :name ";
		}

		if (!req.getParameter("bu").equals("")
				&& !req.getParameter("bu").equals("0")) {
			str = str + " and buCode=:buCode ";
		}

		try {

			session = HibernateFactory.openSession();

			Query query = session
					.createQuery(" from District where status = 1 " + str + " order by orderCode,districtName ");
			if (!req.getParameter("name").equals("")) {
				query.setParameter("name", "%"+req.getParameter("name")+"%");

			}
			if (!req.getParameter("bu").equals("")
					&& !req.getParameter("bu").equals("0")) {
				query.setParameter("buCode",
						Integer.parseInt(req.getParameter("bu")));

			}

			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;

	}

	/**
	 * <p>
	 * This method performs edit view of District.
	 * </p>
	 * 
	 * @param  int districtCode
	 * @return District class object
	 */

	public District getDistrict(int districtCode) {
		District DistrictClassObj = new District();
		Session session = null;

		try {
			session = HibernateFactory.openSession();

			DistrictClassObj = (District) session.get(District.class,
					districtCode);

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return DistrictClassObj;
	}

	/**
	 * <p>
	 * This method performs update view of District.
	 * </p>
	 * 
	 * @param  District class object
	 * @param  requestParameters Servlet Request Parameter
	 * @return District class object
	 */
	public District updateDistrict(District DistrictClassObj,
			HttpServletRequest requestParameters) {
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			User userObj = (User) requestParameters.getSession().getAttribute(
					"currUserObj");

			DistrictClassObj.setModifiedBy(userObj.getStaffLoginId());
			DistrictClassObj.setModificationDate(new Date());

			session.update(DistrictClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_DISTRICT,
					AuditTrail.FUNCTION_UPDATE, DistrictClassObj.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}

		}
		MsgObject msgObj = new MsgObject(
				"The District has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllDistrictListing());
		requestParameters.getSession().setAttribute("pager",
				getAllDistrictListing());

		return DistrictClassObj;
	}

	
	
	/**
	 * <p>
	 * This method performs Delete of District
	 * </p>
	 * 
	 * @param DistrictCode
	 * @param req Servlet Request Parameter
	 * @return 
	 */

	public void deleteDistrict(int DistrictCode, HttpServletRequest req) {

		District DistrictObj = new District();
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from District where status=1 and districtCode=:districtCode");
			query.setParameter("districtCode", DistrictCode);
			ArrayList<District> list = new ArrayList<District>();
			list = (ArrayList<District>) query.list();

			District DistrictClassObj = new District();
			DistrictClassObj = list.get(0);

			//DistrictClassObj.setBuCode(DistrictCode);
			DistrictClassObj.setStatus(false);

			User userObj = (User) req.getSession().getAttribute("currUserObj");

			DistrictClassObj.setModifiedBy(userObj.getStaffLoginId());
			DistrictClassObj.setModificationDate(new Date());
			session.update(DistrictClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_DISTRICT,
					AuditTrail.FUNCTION_DELETE, DistrictClassObj.toString()));
			tx.commit();

			ImoUtilityData imoutill=new ImoUtilityData();
			imoutill.DeleteRelatedBranch(DistrictCode, req);
			
			
			//DeleteRelatedUser(DistrictCode, req);
			DeleteRelatedAnnouncement(DistrictCode, req);
			//DeleteRelatedholiday(DistrictCode, req);
			DeleteRelatedPresenter(DistrictCode, req);
			DeleteRelatedSchedule(DistrictCode, req);
			DeleteRelatedInterview(DistrictCode, req);
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		MsgObject msgObj = new MsgObject(
				"The District Name has been successfully Deleted.");
		req.setAttribute("messageObject", msgObj);
		req.setAttribute("pager", getAllDistrictListing());
		req.getSession().setAttribute("pager", getAllDistrictListing());

	}

	
	
	
	/**
	 * <p>
	 * This method performs Delete of Interview based on related DistrictCode Delete.
	 * </p>
	 * @param DistrictCode
	 * @param req Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedInterview(int DistrictCode, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from Interview where status=1 and district=:district");
		query.setParameter("district", DistrictCode);

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
						AuditTrail.MODULE_DISTRICT_INTERVIEW,
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
			logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
	} 

}

	
	/**
	 * <p>
	 * This method performs Delete of Schedule related DistrictCode Delete
	 * </p>
	 * @param DistrictCode
	 * @param req Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedSchedule(int DistrictCode, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from Event where status=1 and district=:district");
		query.setParameter("district", DistrictCode);

		ArrayList<Event> list = new ArrayList<Event>();
		list = (ArrayList<Event>) query.list();

		if (list.size() != 0) {

			for (int i = 0; i < list.size(); i++) {

				Event EventClassObj = new Event();

				EventClassObj = list.get(i);
				EventClassObj.setEvent_code(EventClassObj
						.getEvent_code());
				
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
						AuditTrail.MODULE_DISTRICT_SCHEDULE_EVENT,
						AuditTrail.FUNCTION_DELETE, EventClassObj
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
			logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
	} 

}

	
	/**
	 * <p>
	 * This method performs Delete of User related DistrictCode Delete
	 * </p>
	 * @param DistrictCode
	 * @param req Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedUser(int DistrictCode, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from User where status=1 and district=:district");
		query.setParameter("district", DistrictCode);

		ArrayList<User> list = new ArrayList<User>();
		list = (ArrayList<User>) query.list();

		if (list.size() != 0) {

			for (int i = 0; i < list.size(); i++) {

				User UserClassObj = new User();

				UserClassObj = list.get(i);
				UserClassObj.setUser_no(UserClassObj
						.getUser_no());
				
				UserClassObj.setStatus(false);
				User userObj = new User();
				userObj = (User) req.getSession().getAttribute(
						"currUserObj");

				UserClassObj.setModifiedBy(userObj.getStaffLoginId());
				UserClassObj.setModificationDate(new Date());
				session.update(UserClassObj);
				AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
				auditTrailMaintenance.insertAuditTrail(new AuditTrail(
						userObj.getUser_no() + "",
						AuditTrail.MODULE_DISTRICT_USER,
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
			logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
	} 

}
	
	
	
	/**
	 * <p>
	 * This method performs Delete of Announcement related DistrictCode Deleted
	 * </p>
	 * @param DistrictCode
	 * @param req Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedAnnouncement(int DistrictCode, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from Announcement where status=1 and district=:district");
		query.setParameter("district", DistrictCode);

		ArrayList<Announcement> list = new ArrayList<Announcement>();
		list = (ArrayList<Announcement>) query.list();

		if (list.size() != 0) {

			for (int i = 0; i < list.size(); i++) {

				Announcement AnnouncementClassObj = new Announcement();

				AnnouncementClassObj = list.get(i);
				AnnouncementClassObj.setAnnoucement_code(AnnouncementClassObj
						.getAnnoucement_code());
				
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
						AuditTrail.MODULE_DISTRICT_ANNOUNCEMENT,
						AuditTrail.FUNCTION_DELETE, AnnouncementClassObj
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
			logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
	} 

}

	
	
	
	/**
	 * <p>
	 * This method performs Delete of holiday related DistrictCode Deleted.
	 * </p>
	 * @param DistrictCode
	 * @param req Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedholiday(int DistrictCode, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from Holiday where status=1 and district=:district");
		query.setParameter("district", DistrictCode);

		ArrayList<Holiday> list = new ArrayList<Holiday>();
		list = (ArrayList<Holiday>) query.list();

		if (list.size() != 0) {

			for (int i = 0; i < list.size(); i++) {

				Holiday holidayClassObj = new Holiday();

				holidayClassObj = list.get(i);
				holidayClassObj.setHolidayCode(holidayClassObj
						.getHolidayCode());
				
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
						AuditTrail.MODULE_DISTRICT_HOLIDAY,
						AuditTrail.FUNCTION_DELETE, holidayClassObj
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
			logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
	} 

}
	
	
	/**
	 * <p>
	 * This method performs Delete of Presenter related DistrictCode Deleted.
	 * </p>
	 * @param DistrictCode
	 * @param req Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedPresenter(int DistrictCode, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from Presenter where status=1 and distCode=:distCode");
		query.setParameter("distCode", DistrictCode);

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
						AuditTrail.MODULE_DISTRICT_PRESENTER,
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
			logsMain.insertLogs("DistrictMaintenance",Level.SEVERE+"",errors.toString());
	} 
	
}
	

	public static final String BU_PARAM = "bu";
	public static final String Name = "name";

}
