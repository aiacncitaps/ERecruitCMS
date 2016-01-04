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
 * 22-april-2015             khyati             add,update,delete and search functionality added.
 * 15-may-2015               khyati             Added Copy rights and security 
 * 11-june-2015              khyati             document added
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

import com.quix.aia.cn.imo.constants.FormInfo;
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


/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author khyati
 * @version 1.0
 */
public class SscMaintenance {
	static Logger log = Logger.getLogger(SscMaintenance.class.getName());
	
	
	
	/**
	 * <p>
	 * This method checks all validations & sets values to bean
	 * </p>
	 * 
	 * @param Ssc class object
	 * @param requestParameters  Servlet Request Parameter
	 * @return Ssc object/Error object
	 */
	
	public Object mapForm1(Ssc Sscobj, HttpServletRequest requestParameters) {

		if (Sscobj == null) {
			Sscobj = new Ssc();
			return Sscobj;
		}

		FormObj formObj = (FormObj) requestParameters.getSession()
				.getAttribute("formObj");
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if (requestParameters.getParameter("bu") == null
				|| requestParameters.getParameter("bu").equals("0"))
			return new ErrorObject("BU Name", " field is required",localeObj);
		if (requestParameters.getParameter("district") == null
				|| requestParameters.getParameter("district").equals("0"))
			return new ErrorObject("District Name", " field is required",localeObj);
		if (requestParameters.getParameter("city") == null
				|| requestParameters.getParameter("city").equals("0"))
			return new ErrorObject("City Name", " field is required",localeObj);
		
		if (requestParameters.getParameter("branch") == null
				|| requestParameters.getParameter("branch").length() == 0)
			return new ErrorObject("Branch Name", " field is required",localeObj);
		
		
		if (requestParameters.getParameter("name") == null
				|| requestParameters.getParameter("name").length() == 0)
			return new ErrorObject("Ssc Code", " field is required",localeObj);
		
		
        
        if (requestParameters.getParameter("fullname") == null
                || requestParameters.getParameter("fullname").length() == 0)
    		return new ErrorObject("Ssc Name", " field is required",localeObj);

		
        String name = requestParameters.getParameterValues("name")[0].trim();
        String co=requestParameters.getParameterValues("branch")[0].trim();
        co = co == null || co.equals("") ? "0" : co;
		String cityCode = requestParameters.getParameterValues("city")[0].trim();
		cityCode = cityCode == null || cityCode.equals("") ? "0" : cityCode;
		int count = validatename(name, co,cityCode);
		
		if(requestParameters.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
		{
			FormObj formDetail = (FormObj)requestParameters.getSession().getAttribute(SessionAttributes.FORM_OBJ);
			if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
			{
				
				if (count > 1) {
					return new ErrorObject("Ssc Already exists", "",localeObj);
				}
					
			}else{
				if (count !=0) {
					return new ErrorObject("Ssc Already exists", "",localeObj);
				}
			}
		}else{
			if (count !=0) {
				return new ErrorObject("Ssc Already exists", "",localeObj);
			}
		}
		

		log.log(Level.INFO, "DistrictMaintenance --> mapForm1 ");

		
		Sscobj.setSscName(requestParameters.getParameter("name"));
        Sscobj.setSscFullName(requestParameters.getParameter("fullname"));
		Sscobj.setCityCodeStr(requestParameters.getParameter("city"));
		Sscobj.setCo(requestParameters.getParameter("branch"));
		

		return Sscobj;
	}

	
	
	/**
	 * <p>
	 * This method performs validation of duplicate name
	 * </p>
	 * 
	 * @param name
	 * @param SscCode
	 * @return integer count value of name available in database.
	 */
	private int validatename(String name, String co,String cityCode) {

		Session session = null;

		int count = 0;
		try {
			session = HibernateFactory.openSession();

			Query query = session
					.createQuery("select count(*) from Ssc where co=:Co and cityCodeStr=:cityCode and sscName=:sscName");
			query.setParameter("Co", co);
			query.setParameter("cityCode", cityCode);
			query.setParameter("sscName", name);
			count = (Integer) query.uniqueResult();

			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method performs call for insertion method for SSC & audit trail for
	 * SSC add & sets message object in request
	 * </p>
	 * 
	 * @param SSC Class object
	 * @param requestParameters  Servlet Request Parameter
	 * @return SSC object
	 */
	public Object createNewSsc(Ssc SscClassObj,
			HttpServletRequest requestParameters) {

		log.log(Level.INFO, "SscMaintenance --> createNewSsc ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute("currUserObj");

		int status = insertSsc(SscClassObj);
		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_SSC,
					AuditTrail.FUNCTION_CREATE, SscClassObj.toString()));
			msgObj = new MsgObject(
					"The new Ssc has been successfully created.");
		} else {
			msgObj = new MsgObject("The new Ssc has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllSscListing());
		requestParameters.getSession()
				.setAttribute("pager", getAllSscListing());
		return SscClassObj;
	}
	
	
	
	
	/**
	 * <p>
	 * This method performs insert of SSC
	 * </p>
	 * 
	 * @param SSC Class Object
	 * @return int key 0 or 1
	 */
	public int insertSsc(Ssc SscClassObj) {
		Integer key = 0;
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			session.save(SscClassObj);
			key = 1 ;
			tx.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			key=0;
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method performs listing of default search result of  SSC Record 
	 * </p>
	 * 
	 * @param SSC Class Object
	 * @return int key 0 or 1
	 */
	public Pager getAllSscListing() {

		LinkedList item = new LinkedList();
		Ssc SscObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllSsc();
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				SscObj = new Ssc();
				SscObj = (Ssc) vecAllRes.get(i);
				item.add(SscObj.getGetSscListingTableRow(i));
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
	private ArrayList getAllSsc() {

		log.log(Level.INFO, "SscMaintanence --> getAllSsc");
		ArrayList arrActivity = new ArrayList();
		Session session = null;

		try {

			session = HibernateFactory.openSession();

			Query query = session.createQuery(" from Ssc ");
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;
	}

	
	
	/**
	 * <p>
	 * This is particular search listing method based of search criteria.
	 * </p>
	 * 
	 * @param  requestParamters Servlet Request Parameter
	 * @return Pager
	 */
	public Pager SscSearchListing(HttpServletRequest requestParamters) {

		LinkedList item = new LinkedList();
		Ssc SscClassObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getSearchSsc(requestParamters);
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				SscClassObj = new Ssc();
				SscClassObj = (Ssc) vecAllRes.get(i);
				item.add(SscClassObj.getGetSscListingTableRow(i));
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
	private ArrayList getSearchSsc(HttpServletRequest req) {

		log.log(Level.INFO, "SscMaintanence --> getSearchSsc");
		ArrayList arrActivity = new ArrayList();
		Session session = null;

		String str = "";
		if (!req.getParameter("name").equals("")) {
			str = str + " and sscFullName LIKE :name ";
		}
		if(!req.getParameter("bu").equals("0") && req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") ){
			str=str+" and   cityCode In(select cityCode from City where status = 1 and  branchCode In(select branchCode from Branch where status = 1 and  distCode IN (select districtCode from District where status = 1 and buCode IN (select buCode from Bu where status = 1 and buCode=:bu ) )  ))";
		}
		
		if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") ){
			str=str+" and  cityCode In(select cityCode from City where status = 1 and  branchCode In(select branchCode from Branch where status = 1 and  distCode IN (select districtCode from District where status = 1 and districtCode=:dist and buCode IN (select buCode from Bu where status = 1 and buCode=:bu ) ) ) )";
		}
		
		if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") ){
			str=str+" and  cityCode In(select cityCode from City where status = 1 and  branchCode In(select branchCode from Branch where status = 1 and branchCode=:branch and  distCode IN (select districtCode from District where status = 1 and  districtCode=:dist and buCode IN (select buCode from Bu where status = 1 and buCode=:bu) ) ) )";
		}
		
		if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") && !req.getParameter("city").equals("0") ){
			str=str+" and  cityCode=:city ";
		}

		try {

			session = HibernateFactory.openSession();

			Query query = session.createQuery(" from Ssc where status = 1 "
					+ str + " ORDER BY orderCode ");
			
			if (!req.getParameter("name").equals("")) {
				query.setParameter("name", "%"+req.getParameter("name")+"%");

			}
			
			if(!req.getParameter("bu").equals("0") && req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") ){
				query.setParameter("bu", Integer.parseInt(req.getParameter("bu")));
			}
			
			if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") ){
				query.setParameter("bu", Integer.parseInt(req.getParameter("bu")));
				query.setParameter("dist", Integer.parseInt(req.getParameter("district")));
			}
			
			if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") && req.getParameter("city").equals("0") ){
				query.setParameter("bu", Integer.parseInt(req.getParameter("bu")));
				query.setParameter("dist", Integer.parseInt(req.getParameter("district")));
				query.setParameter("branch", Integer.parseInt(req.getParameter("branch")));
			}
			
			if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") && !req.getParameter("city").equals("0") ){
				query.setParameter("city", Integer.parseInt(req.getParameter("city")));
			}
			
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;

	}

	
	
	/**
	 * <p>
	 * This method performs edit view of SSC.
	 * </p>
	 * 
	 * @param  int SscCode
	 * @return SSC class object
	 */
	

	public Ssc getSsc(int SscCode) {
		Ssc SscClassObj = new Ssc();
		Session session = null;

		try {
			session = HibernateFactory.openSession();

			SscClassObj = (Ssc) session.get(Ssc.class, SscCode);

			// GET DISTRICT CODE

			Query query = session.createQuery("from City where status=1 and cityCode=:cityCode");
			query.setParameter("cityCode", SscClassObj.getCityCode());
			ArrayList arrActivity = new ArrayList();
			arrActivity = (ArrayList) query.list();
			int DistrictCode = 0;
			int BUCode = 0;
			if (arrActivity.size() != 0) {
				City CityClassObj = new City();
				CityClassObj = (City) arrActivity.get(0);
				
				Branch branch=(Branch)session.get(Branch.class, CityClassObj.getBranchCode());
				SscClassObj.setBranchCode(branch.getBranchCode());
				
				District dist=(District)session.get(District.class,branch.getDistCode());
				SscClassObj.setDistrictCode(dist.getDistrictCode());
				SscClassObj.setBuCode(dist.getBuCode());
				
		

			}

			// GET BU CODE

//			Query query1 = session
//					.createQuery("from District where status=1 and districtCode=:districtCode");
//			query1.setParameter("districtCode", DistrictCode);
//			ArrayList arrActivityBU = new ArrayList();
//			arrActivityBU = (ArrayList) query1.list();
//
//			if (arrActivityBU.size() != 0) {
//
//				District DistrictClassObj = new District();
//				DistrictClassObj = (District) arrActivityBU.get(0);
//				BUCode = DistrictClassObj.getBuCode();
//				SscClassObj.setBuCode(BUCode);
//
//			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return SscClassObj;
	}

	

	

	/**
	 * <p>
	 * This method performs update of SSC.
	 * </p>
	 * 
	 * @param  SSC class object 
	 * @param  requestParameters  Servlet Request Parameter
	 * @return SSC class object
	 */
	
	public Ssc updateSsc(Ssc SscClassObj, HttpServletRequest requestParameters) {
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			User userObj = (User) requestParameters.getSession().getAttribute(
					"currUserObj");
			SscClassObj.setSscCode(Integer.parseInt(requestParameters
					.getParameter("ssccode")));
			SscClassObj.setModifiedBy(userObj.getStaffLoginId());
			SscClassObj.setModificationDate(new Date());

			session.update(SscClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_SSC,
					AuditTrail.FUNCTION_UPDATE, SscClassObj.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}

		}
		MsgObject msgObj = new MsgObject(
				"The SSC has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllSscListing());
		requestParameters.getSession()
				.setAttribute("pager", getAllSscListing());

		return SscClassObj;
	}

	/**
	 * <p>
	 * This method performs Delete of SSC.
	 * </p>
	 * 
	 * @param  int SscCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */

	public void deleteSsc(int SscCode, HttpServletRequest req) {

		Ssc SscObj = new Ssc();
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			Query query = session
					.createQuery("from Ssc where status=1 and sscCode=:sscCode");
			query.setParameter("sscCode", SscCode);
			ArrayList<Ssc> list = new ArrayList<Ssc>();
			list = (ArrayList<Ssc>) query.list();

			Ssc SscClassObj = new Ssc();
			SscClassObj = list.get(0);

			SscClassObj.setSscCode(SscCode);
			SscClassObj.setStatus(false);

			User userObj = (User) req.getSession().getAttribute("currUserObj");

			SscClassObj.setModifiedBy(userObj.getStaffLoginId());
			SscClassObj.setModificationDate(new Date());
			session.update(SscClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_SSC,
					AuditTrail.FUNCTION_DELETE, SscClassObj.toString()));
			tx.commit();
			
			ImoUtilityData imoutill=new ImoUtilityData();
			imoutill.DeleteRelatedSSC(SscCode, req);
			//DeleteRelatedUser(SscCode, req);
			DeleteRelatedAnnouncement(SscCode, req);
			//DeleteRelatedholiday(SscCode, req);
			DeleteRelatedPresenter(SscCode, req);
			DeleteRelatedSchedule(SscCode, req);
			DeleteRelatedInterview(SscCode, req);
			session.flush();
			// req.setAttribute("pager",getAllBUListing());

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		MsgObject msgObj = new MsgObject(
				"The SSC has been successfully Deleted.");
		req.setAttribute("messageObject", msgObj);
		req.setAttribute("pager", getAllSscListing());
		req.getSession().setAttribute("pager", getAllSscListing());

	}

	/**
	 * <p>
	 * This method performs Delete of User based on deleted ssc 
	 * </p>
	 * 
	 * @param  int SscCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedUser(int SscCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from User where status=1 and sscCode=:sscCode");
			query.setParameter("sscCode", SscCode);

			ArrayList<User> list = new ArrayList<User>();
			list = (ArrayList<User>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					User UserClassObj = new User();

					UserClassObj = list.get(i);
					UserClassObj.setUser_no(UserClassObj.getUser_no());

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
									AuditTrail.MODULE_SSC_USER,
									AuditTrail.FUNCTION_DELETE, UserClassObj
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
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Announcement based on deleted ssc 
	 * </p>
	 * 
	 * @param  int SscCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedAnnouncement(int SscCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Announcement where status=1 and sscCode=:sscCode");
			query.setParameter("sscCode", SscCode);

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
							AuditTrail.MODULE_SSC_ANNOUNCEMENT,
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
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	
	/**
	 * <p>
	 * This method performs Delete of holiday based on deleted ssc 
	 * </p>
	 * 
	 * @param  int SscCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedholiday(int SscCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Holiday where status=1 and sscCode=:sscCode");
			query.setParameter("sscCode", SscCode);

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
							AuditTrail.MODULE_SSC_HOLIDAY,
							AuditTrail.FUNCTION_DELETE, holidayClassObj
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
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Presenter based on deleted ssc 
	 * </p>
	 * 
	 * @param  int SscCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedPresenter(int SscCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Presenter where status=1 and sscCode=:sscCode");
			query.setParameter("sscCode", SscCode);

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
							AuditTrail.MODULE_SSC_PRESENTER,
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
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Schedule based on deleted ssc 
	 * </p>
	 * 
	 * @param  int SscCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedSchedule(int sscCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Event where status=1 and sscCode=:sscCode");
			query.setParameter("sscCode", sscCode);

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
							AuditTrail.MODULE_SSC_SCHEDULE_EVENT,
							AuditTrail.FUNCTION_DELETE, EventClassObj
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
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Interview based on deleted ssc 
	 * </p>
	 * 
	 * @param  int SscCode
	 * @param  requestParameters Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedInterview(int sscCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Interview where status=1 and sscCode=:sscCode");
			query.setParameter("sscCode", sscCode);

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
							AuditTrail.MODULE_SSC_INTERVIEW,
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
				logsMain.insertLogs("SscMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	

	public static final String BU_PARAM = "bu";
	public static final String Name = "name";
}
