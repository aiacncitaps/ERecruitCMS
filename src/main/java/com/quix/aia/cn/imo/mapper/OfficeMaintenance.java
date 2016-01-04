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
* Date              Developer          Change Description
* 07-May-2015       Jay          
* 
****************************************** *********************************** */
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
import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.interview.Interview;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.office.Office;
import com.quix.aia.cn.imo.data.presenter.Presenter;
import com.quix.aia.cn.imo.data.ssc.Ssc;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FormObj;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;

public class OfficeMaintenance {
	static Logger log = Logger.getLogger(OfficeMaintenance.class.getName());

	public Object mapForm1(Office officeObj, HttpServletRequest requestParameters) {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "OfficeMaintenance --> mapForm1 ");
		if (officeObj == null) {
			officeObj = new Office();
			return officeObj;
		}

		FormObj formObj = (FormObj) requestParameters.getSession()
				.getAttribute("formObj");
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if (requestParameters.getParameter("bu") == null
				|| requestParameters.getParameter("bu").equals("0"))
			return new ErrorObject("BU", " field is required",localeObj);
		if (requestParameters.getParameter("district") == null
				|| requestParameters.getParameter("district").equals("0"))
			return new ErrorObject("district", " field is required",localeObj);
		if (requestParameters.getParameter("city") == null
				|| requestParameters.getParameter("city").equals("0"))
			return new ErrorObject("City", " field is required",localeObj);
		if (requestParameters.getParameter("name") == null
				|| requestParameters.getParameter("name").length() == 0)
			return new ErrorObject("Office Code", " field is required",localeObj);
        
        if (requestParameters.getParameter("fullname") == null
                || requestParameters.getParameter("fullname").length() == 0)
        	return new ErrorObject("Office Name", " field is required",localeObj);

		String name = requestParameters.getParameterValues("name")[0].trim();
		String Fullname = requestParameters.getParameterValues("fullname")[0].trim();
		String officeCode = requestParameters.getParameterValues("officecode")[0].trim();
		officeCode = officeCode == null || officeCode.equals("") ? "0" : officeCode;
		
		
		
		int count = validatename(name, Integer.parseInt(officeCode));
		if (count != 0) {
			return new ErrorObject("OFFICE Code Already exists", "",localeObj);
		}
		count = validateFullname(Fullname, Integer.parseInt(officeCode));
		if (count != 0) {
			return new ErrorObject("OFFICE Name Already exists", "",localeObj);
		}

		if(requestParameters.getParameter("order") == null){
			officeObj.setOrderCode(0);
		}else{
			try{
				Integer.parseInt(requestParameters.getParameter("order"));
			}catch(Exception e){
				return new ErrorObject("Order","requires numeric value",localeObj);
			}
			officeObj.setOrderCode(Integer.parseInt(requestParameters.getParameter("order")));
		}

		officeObj.setOfficeName(requestParameters.getParameter("name"));
		officeObj.setOfficeFullName(requestParameters.getParameter("fullname"));
		officeObj.setSscCode(Integer.parseInt(requestParameters.getParameter("ssc")));
		officeObj.setCityCode(Integer.parseInt(requestParameters.getParameter("city")));
		officeObj.setStatus(true);
		officeObj.setToken(LMSUtil.getRendomToken());

		return officeObj;
		
	
	}

	private int validatename(String name, int code) {
		// TODO Auto-generated method stub
		Session session = null;

		int count = 0;
		try {
			session = HibernateFactory.openSession();

			Query query = session
					.createQuery("select count(*) from Office where status=1 and officeName=:name and officeCode!=:code");
			query.setParameter("name", name);
			query.setParameter("code", code);
			count = (Integer) query.uniqueResult();

			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
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
	
	private int validateFullname(String name, int code) {
		// TODO Auto-generated method stub
		Session session = null;

		int count = 0;
		try {
			session = HibernateFactory.openSession();

			Query query = session
					.createQuery("select count(*) from Office where status=1 and officeFullName=:name and officeCode!=:code");
			query.setParameter("name", name);
			query.setParameter("code", code);
			count = (Integer) query.uniqueResult();

			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
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

	public Object createNewOffice(Office officeObj, HttpServletRequest requestParameters) {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "OfficeMaintenance --> createNewOffice ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute("currUserObj");
		officeObj.setCreatedBy(userObj.getStaffLoginId());;
		officeObj.setCreationDate(new Date());
		officeObj.setModifiedBy(userObj.getStaffLoginId());
		officeObj.setModificationDate(new Date());
		int status = insertOffice(officeObj);
		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_OFFICE,
					AuditTrail.FUNCTION_CREATE, officeObj.toString()));
			msgObj = new MsgObject(
					"The new Office has been successfully created.");
		} else {
			msgObj = new MsgObject("The new Office has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllOfficeListing());
		requestParameters.getSession().setAttribute("pager", getAllOfficeListing());
		return officeObj;
		
	}

	public Pager getAllOfficeListing() {
		// TODO Auto-generated method stub
		LinkedList item = new LinkedList();
		Office officeObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllOffice();
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				officeObj = new Office();
				officeObj = (Office) vecAllRes.get(i);
				item.add(officeObj.getGetOfficeListingTableRow(i));
			}
		}
		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);

		for (; item.size() % 10 != 0; item.add("<tr></tr>"));
		pager.setItems(item);
		return pager;
	}

	private ArrayList getAllOffice() {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "OfficeMaintanence --> getAllOffice");
		ArrayList arrActivity = new ArrayList();
		Session session = null;

		try {

			session = HibernateFactory.openSession();

			Query query = session.createQuery(" from Office where status = 1  ORDER BY orderCode ");
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;
	}

	private int insertOffice(Office officeObj) {
		// TODO Auto-generated method stub
		Integer key = 0;
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			key = (Integer) session.save(officeObj);
			tx.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
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

	public Object getOfficeBaseOnCode(int code) {
		// TODO Auto-generated method stub
		Office office=new Office();
		Session session = null;
		
		try{
			session = HibernateFactory.openSession();
			office = (Office)session.get(Office.class,code);
	
	  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		
		return office;
	}

	public Object updateOffice(Office officeObj, HttpServletRequest requestParameters) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			User userObj = (User) requestParameters.getSession().getAttribute("currUserObj");
			officeObj.setOfficeCode(Integer.parseInt(requestParameters.getParameter("officecode")));
			officeObj.setModifiedBy(userObj.getStaffLoginId());
			officeObj.setModificationDate(new Date());

			session.update(officeObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_OFFICE,	AuditTrail.FUNCTION_UPDATE, officeObj.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}

		}
		MsgObject msgObj = new MsgObject("The Office has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		Pager Pager=getAllOfficeListing();
		requestParameters.setAttribute("pager", Pager);
		requestParameters.getSession().setAttribute("pager",Pager);

		return officeObj;
	}

	public void deleteOffice(int code, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Office officeObj = new Office();
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			Query query = session
					.createQuery("from Office where status=1 and officeCode=:code");
			query.setParameter("code", code);
			ArrayList<Office> list = new ArrayList<Office>();
			list = (ArrayList<Office>) query.list();

			Office officeClassObj = new Office();
			officeClassObj = list.get(0);

			officeClassObj.setOfficeCode(code);
			officeClassObj.setStatus(false);

			User userObj = (User) req.getSession().getAttribute("currUserObj");

			officeClassObj.setModifiedBy(userObj.getStaffLoginId());
			officeClassObj.setModificationDate(new Date());
			session.update(officeClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_OFFICE,
					AuditTrail.FUNCTION_DELETE, officeClassObj.toString()));
			tx.commit();
			
			
			DeleteRelatedAnnouncement(code, req);
			DeleteRelatedPresenter(code, req);
			DeleteRelatedSchedule(code, req);
			DeleteRelatedInterview(code, req);
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		MsgObject msgObj = new MsgObject("The Office has been successfully Deleted.");
		req.setAttribute("messageObject", msgObj);
		Pager pager=getAllOfficeListing();
		req.setAttribute("pager", pager);
		req.getSession().setAttribute("pager", pager);
	}

	public Pager OfficeSearchListing(HttpServletRequest requestParamters) {
		// TODO Auto-generated method stub
		LinkedList item = new LinkedList();
		Office officeClassObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getSearchOffice(requestParamters);
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				officeClassObj = new Office();
				officeClassObj = (Office) vecAllRes.get(i);
				item.add(officeClassObj.getGetOfficeListingTableRow(i));
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

	private ArrayList getSearchOffice(HttpServletRequest req) {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "SscMaintanence --> getSearchSsc");
		ArrayList arrActivity = new ArrayList();
		Session session = null;

		String str = "";
		if (!req.getParameter("name").equals("")) {
			str = str + " and officeName LIKE :name ";
		}
//
//		if (!req.getParameter("ssc").equals("")
//				&& !req.getParameter("ssc").equals("0")) {
//			str = str + " and sscCode=:code ";
//		}

		
		if(!req.getParameter("bu").equals("0") && req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") && req.getParameter("ssc").equals("0") ){
			str=str+" and sscCode In(select sscCode from Ssc where status = 1 and cityCode In(select cityCode from City where status = 1 and  branchCode In(select branchCode from Branch where status = 1 and  distCode IN (select districtCode from District where status = 1 and buCode IN (select buCode from Bu where status = 1 and buCode=:bu ) ) ) ))";
		}
		
		if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") && req.getParameter("ssc").equals("0") ){
			str=str+" and sscCode In(select sscCode from Ssc where status = 1 and cityCode In(select cityCode from City where status = 1 and  branchCode In(select branchCode from Branch where status = 1 and  distCode IN (select districtCode from District where status = 1 and districtCode=:dist and buCode IN (select buCode from Bu where status = 1 and buCode=:bu ) ) ) ))";
		}
		
		if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") && req.getParameter("ssc").equals("0") ){
			str=str+" and sscCode In(select sscCode from Ssc where status = 1 and cityCode In(select cityCode from City where status = 1 and  branchCode In(select branchCode from Branch where status = 1 and branchCode=:branch and  distCode IN (select districtCode from District where status = 1 and districtCode=:dist and buCode IN (select buCode from Bu where status = 1 and buCode=:bu ) ) ) ))";
		}
		
		if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") && !req.getParameter("city").equals("0") && req.getParameter("ssc").equals("0") ){
			str=str+" and sscCode In(select sscCode from Ssc where status = 1 and cityCode In(select cityCode from City where status = 1 and cityCode=:city and  branchCode In(select branchCode from Branch where status = 1 and branchCode=:branch and  distCode IN (select districtCode from District where status = 1 and districtCode=:dist and buCode IN (select buCode from Bu where status = 1 and buCode=:bu ) ) ) ))";
		}
		
		if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") && !req.getParameter("city").equals("0") && !req.getParameter("ssc").equals("0") ){
			str=str+" and sscCode=:ssc ";
		}
		
		
		try {

			session = HibernateFactory.openSession();

			Query query = session.createQuery(" from Office where status = 1 "
					+ str + "  ORDER BY orderCode ");
			
			if (!req.getParameter("name").equals("")) {
				query.setParameter("name", "%"+req.getParameter("name")+"%");

			}
			
			if(!req.getParameter("bu").equals("0") && req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") && req.getParameter("ssc").equals("0") ){
				query.setParameter("bu", Integer.parseInt(req.getParameter("bu")));
			}
			
			if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") && req.getParameter("ssc").equals("0") ){
				query.setParameter("bu", Integer.parseInt(req.getParameter("bu")));
				query.setParameter("dist", Integer.parseInt(req.getParameter("district")));
			}
			
			if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") && req.getParameter("ssc").equals("0") ){
				query.setParameter("bu", Integer.parseInt(req.getParameter("bu")));
				query.setParameter("dist", Integer.parseInt(req.getParameter("district")));
				query.setParameter("branch", Integer.parseInt(req.getParameter("branch")));
			}
			
			if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") && !req.getParameter("city").equals("0") && req.getParameter("ssc").equals("0") ){
				query.setParameter("bu", Integer.parseInt(req.getParameter("bu")));
				query.setParameter("dist", Integer.parseInt(req.getParameter("district")));
				query.setParameter("branch", Integer.parseInt(req.getParameter("branch")));	
				query.setParameter("city", Integer.parseInt(req.getParameter("city")));
			}
			
			if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") && !req.getParameter("city").equals("0") && !req.getParameter("ssc").equals("0") ){
				query.setParameter("ssc", Integer.parseInt(req.getParameter("ssc")));
			}

			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;

	}
	
	private void DeleteRelatedAnnouncement(int code, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Announcement where status=1 and officeCode=:code");
			query.setParameter("code", code);

			ArrayList<Announcement> list = new ArrayList<Announcement>();
			list = (ArrayList<Announcement>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Announcement AnnouncementClassObj = new Announcement();

					AnnouncementClassObj = list.get(i);
					AnnouncementClassObj
							.setAnnoucement_code(AnnouncementClassObj
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
							AuditTrail.MODULE_BU_ANNOUNCEMENT,
							AuditTrail.FUNCTION_DELETE, AnnouncementClassObj
									.toString()));
					tx.commit();
					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
		} /*
		 * finally { try { HibernateFactory.close(session);
		 * 
		 * } catch (Exception e) { log.log(Level.SEVERE, e.getMessage());
		 * e.printStackTrace(); } }
		 */

	}
	
	private void DeleteRelatedPresenter(int code, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Presenter where status=1 and officeCode=:code");
			query.setParameter("code", code);

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
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
		}

	}
	
	
	private void DeleteRelatedSchedule(int code, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Event where status=1 and officeCode=:code");
			query.setParameter("code", code);

			ArrayList<Event> list = new ArrayList<Event>();
			list = (ArrayList<Event>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Event EventClassObj = new Event();

					EventClassObj = list.get(i);
					EventClassObj.setEvent_code(EventClassObj.getEvent_code());
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
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
		} /*
		 * finally { try { HibernateFactory.close(session);
		 * 
		 * } catch (Exception e) { log.log(Level.SEVERE, e.getMessage());
		 * e.printStackTrace(); } }
		 */

	}
	
	
	private void DeleteRelatedInterview(int code, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Interview where status=1 and officeCode=:code");
			query.setParameter("code", code);

			ArrayList<Interview> list = new ArrayList<Interview>();
			list = (ArrayList<Interview>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Interview InterviewClassObj = new Interview();

					InterviewClassObj = list.get(i);
					InterviewClassObj.setInterview_code(InterviewClassObj.getInterview_code());

					InterviewClassObj.setStatus(false);
					User userObj = new User();
					userObj = (User) req.getSession().getAttribute("currUserObj");

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
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("OfficeMaintenance",Level.SEVERE+"",errors.toString());
		}

	}


}
