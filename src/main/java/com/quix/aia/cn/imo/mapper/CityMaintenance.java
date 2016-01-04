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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.quix.aia.cn.imo.constants.FormInfo;
import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.announcement.Announcement;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.branch.Branch;
import com.quix.aia.cn.imo.data.bu.Bu;
import com.quix.aia.cn.imo.data.city.*;
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
public class CityMaintenance {

	static Logger log = Logger.getLogger(BUMaintenance.class.getName());
   
	
	
	/**
	 * <p>
	 * This method checks all validations & sets values to bean
	 * </p>
	 * 
	 * @param City class object
	 * @param requestParamet  Servlet Request Parameter
	 * @return City object/Error object
	 */
	public Object mapForm1(City Cityobj, HttpServletRequest requestParameters) {
		log.log(Level.INFO, "DistrictMaintenance --> mapForm1 ");
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if (Cityobj == null) {
			Cityobj = new City();
			return Cityobj;
		}

		FormObj formObj = (FormObj) requestParameters.getSession().getAttribute("formObj");

		if (requestParameters.getParameter("bu") == null
				|| requestParameters.getParameter("bu").equals("0"))
		return new ErrorObject("BU Name", " field is required",localeObj);
		
		if (requestParameters.getParameter("district") == null
				|| requestParameters.getParameter("district").equals("0"))
		return new ErrorObject("District Name", " field is required",localeObj);
		
		if (requestParameters.getParameter("branch") == null
				|| requestParameters.getParameter("branch").equals("0"))
		return new ErrorObject("Branch", " field is required",localeObj);
		
		
		if (requestParameters.getParameter("name") == null
				|| requestParameters.getParameter("name").length() == 0)
		return new ErrorObject("City Code", " field is required",localeObj);
        
        if (requestParameters.getParameter("fullname") == null
                || requestParameters.getParameter("fullname").length() == 0)
    	return new ErrorObject("City Name", " field is required",localeObj);
        

		String name = requestParameters.getParameterValues("name")[0].trim();
		
		String co=requestParameters.getParameterValues("branch")[0].trim();
		co = co == null || co.equals("") ? "0" : co;
		int count = validatename(name,co);
		
		if(requestParameters.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
		{
			FormObj formDetail = (FormObj)requestParameters.getSession().getAttribute(SessionAttributes.FORM_OBJ);
			if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
			{
				
				if (count > 1) {
					return new ErrorObject("City Already exists", "",localeObj);
				}
					
			}else{
				if (count !=0) {
					return new ErrorObject("City Already exists", "",localeObj);
				}
			}
		}else{
			if (count !=0) {
				return new ErrorObject("City Already exists", "",localeObj);
			}
		}
		


		Cityobj.setCityName(requestParameters.getParameter("name"));
        Cityobj.setCityFullName(requestParameters.getParameter("fullname"));
        Cityobj.setCo(requestParameters.getParameter("branch"));
        Cityobj.setRegCode("");
		//Cityobj.setBranchCode(Integer.parseInt(requestParameters.getParameter("branch")));
		
		return Cityobj;
	}

	
	
	/**
	 * <p>
	 * This method performs validation of duplicate name
	 * </p>
	 * 
	 * @param name
	 * @param CityCode
	 * @return integer count value of name available in database.
	 */
	private int validatename(String name,String co) {

		Session session = null;
		Transaction tx;
		int count = 0;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("select count(*) from City where cityName=:cityName and co=:co ");
			query.setParameter("cityName", name);
			query.setParameter("co", co);
			
			count = (Integer) query.uniqueResult();

			tx.commit();
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method performs call for insertion method for City & audit trail for
	 * city add & sets message object in request
	 * </p>
	 * 
	 * @param City class object
	 * @param requestParameters Servlet Request Parameter
	 * @return City CityClassObj
	 */
	public Object createNewCity(City CityClassObj,
			HttpServletRequest requestParameters) {

		log.log(Level.INFO, "CityMaintenance --> createNewCity ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute("currUserObj");
		
		int status = insertCity(CityClassObj);
		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_CITY,
					AuditTrail.FUNCTION_CREATE, CityClassObj.toString()));
			msgObj = new MsgObject(
					"The new City has been successfully created.");
		} else {
			msgObj = new MsgObject("The new City has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllCityListing());
		requestParameters.getSession().setAttribute("pager",getAllCityListing());
		return CityClassObj;
	}
	
	
	
	/**
	 * <p>
	 * This method performs insert of City
	 * </p>
	 * 
	 * @param City Class Object
	 * @return int key 0 or 1
	 */
	public int insertCity(City CityClassObj) {
		Session session = null;
		Transaction tx;
		Integer key = 0;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			session.save(CityClassObj);
			
			key = 1;
			tx.commit();
		} catch (Exception e) {
			key=0;
			log.log(Level.SEVERE, e.getMessage());
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method performs listing of default search result of  City Record 
	 * </p>
	 * 
	 * @param
	 * @return  pager
	 */
	public Pager getAllCityListing() {

		LinkedList item = new LinkedList();
		City CityObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllCity();
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				CityObj = new City();
				CityObj = (City) vecAllRes.get(i);
				item.add(CityObj.getGetCityListingTableRow(i));
			}
		}
		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);
		// pager.setTableHeader(BuObj.getGetBUListingTableHdr());
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
	
	private ArrayList getAllCity() {

		log.log(Level.INFO, "CityMaintanence --> getAllCity");
		Session session = null;

		ArrayList arrActivity = new ArrayList();
		try {

			session = HibernateFactory.openSession();

			Query query = session.createQuery(" from City");
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
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
	public Pager CitySearchListing(HttpServletRequest requestParamters) {

		LinkedList item = new LinkedList();
		City CityClassObj = null;
		ArrayList vecAllRes = new ArrayList();
		vecAllRes = getSearchCity(requestParamters);
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				CityClassObj = new City();
				CityClassObj = (City) vecAllRes.get(i);
				item.add(CityClassObj.getGetCityListingTableRow(i));
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
	private ArrayList getSearchCity(HttpServletRequest req) {

		log.log(Level.INFO, "CityMaintanence --> getSearchCity");
		ArrayList arrActivity = new ArrayList();
		Session session = null;

		String str = "";
		if (!req.getParameter("name").equals("")) {
			str = str + " and cityFullName LIKE :name ";
		}
		
		if(!req.getParameter("bu").equals("0") && req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") ){
			str=str+" and  branchCode In(select branchCode from Branch where status = 1 and  distCode IN (select districtCode from District where status = 1 and buCode IN (select buCode from Bu where status = 1 and buCode=:bu ) )  )";
		}
		
		if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") ){
			str=str+" and branchCode In(select branchCode from Branch where status = 1 and distCode IN (select districtCode from District where status = 1 and districtCode=:dist and  buCode IN (select buCode from Bu where status = 1 and buCode=:bu ) )  )";
		}
		
		if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") ){
			str=str+" and branchCode=:branch ";
		}
		
		
		try {

			session = HibernateFactory.openSession();

			Query query = session.createQuery(" from City where status = 1 "
					+ str + " ORDER BY orderCode ");
			
			if (!req.getParameter("name").equals("")) {
				query.setParameter("name", "%"+req.getParameter("name")+"%");

			}
			
			if(!req.getParameter("bu").equals("0") && req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") ){
				query.setParameter("bu", Integer.parseInt(req.getParameter("bu")));
			}
			
			if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && req.getParameter("branch").equals("0") ){
				query.setParameter("bu", Integer.parseInt(req.getParameter("bu")));
				query.setParameter("dist", Integer.parseInt(req.getParameter("district")));
			}
			
			if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") && !req.getParameter("branch").equals("0") ){
				query.setParameter("branch", Integer.parseInt(req.getParameter("branch")));
			}
		
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;

	}


	/**
	 * <p>
	 * This method performs edit view of City.
	 * </p>
	 * 
	 * @param  int CityCode
	 * @return City class object
	 */
	

	public City getCity(String CityCode,String co) {
		City CityClassObj = new City();
		Session session = null;

		try {
			session = HibernateFactory.openSession();

			CityClassObj = (City) session.get(City.class, CityCode);
			// District code
			Query query = null;
			query=session.createQuery("from Branch where status=1 and branchCode=:branchCode");
			query.setParameter("branchCode",Integer.parseInt(CityClassObj.getCo().trim()));
			ArrayList arrActivity = new ArrayList();
			arrActivity = (ArrayList) query.list();
			if (arrActivity.size() != 0) {
				Branch branch=new Branch();
				branch=(Branch)arrActivity.get(0);
				
				 District dist=(District)session.get(District.class, branch.getDistCode());
				 CityClassObj.setBuCode(dist.getBuCode());
				 CityClassObj.setDistrict(branch.getDistCode());
				 
//				District DistrictClassObj = new District();
//				DistrictClassObj = (District) arrActivity.get(0);
//				int BUCode = DistrictClassObj.getBuCode();
//				CityClassObj.setBuCode(BUCode);

			}
			
			
			

			

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return CityClassObj;
	}

	
	
	
	
	
	/**
	 * <p>
	 * This method performs update view of City.
	 * </p>
	 * 
	 * @param  City class object
	 * @param  requestParameters Servlet Request Parameter
	 * @return City class object
	 */
	
	public City updateCity(City CityClassObj,
			HttpServletRequest requestParameters) {
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			User userObj = (User) requestParameters.getSession().getAttribute("currUserObj");
			
			session.update(CityClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_CITY,
					AuditTrail.FUNCTION_UPDATE, CityClassObj.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}

		}
		MsgObject msgObj = new MsgObject(
				"The City has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllCityListing());
		requestParameters.getSession().setAttribute("pager",
				getAllCityListing());

		return CityClassObj;
	}

	/**
	 * <p>
	 * This method performs Delete of SSC.
	 * </p>
	 * @param string2 
	 * 
	 * @param  int SscCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */

	public void deleteCity(String cityCode, HttpServletRequest req, String co) {

	
		Session session = null;
		Transaction tx;
		User userObj = (User) req.getSession().getAttribute("currUserObj");
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			
			Query query = session.createQuery("Delete from City where cityName=:code and co=:Co");
			query.setParameter("code", cityCode);
			query.setParameter("Co", co);
			query.executeUpdate();
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getUser_no() + "", AuditTrail.MODULE_CITY,
					AuditTrail.FUNCTION_DELETE, cityCode+","+co));
			/*ArrayList<City> list = new ArrayList<City>();
			list = (ArrayList<City>) query.list();

			for(City city: list){
				session.delete(city);
				AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
				auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getUser_no() + "", AuditTrail.MODULE_CITY,
						AuditTrail.FUNCTION_DELETE, city.toString()));
			}
			*/
			
			
			tx.commit();
			
			//ImoUtilityData imoutill=new ImoUtilityData();
			//imoutill.DeleteRelatedOffice(string, req);

			//DeleteRelatedUser(CityCode, req);
			//DeleteRelatedAnnouncement(string, req);
			//DeleteRelatedholiday(CityCode, req);
			//DeleteRelatedPresenter(string, req);
			//DeleteRelatedSchedule(string, req);
			//DeleteRelatedInterview(string, req);
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		MsgObject msgObj = new MsgObject(
				"The City has been successfully Deleted.");
		req.setAttribute("messageObject", msgObj);
		req.setAttribute("pager", getAllCityListing());
		req.getSession().setAttribute("pager", getAllCityListing());

	}

	/**
	 * <p>
	 * This method performs Delete of SSC based on city.
	 * </p>
	 * 
	 * @param  int CityCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedSSC(int CityCode, HttpServletRequest req) {
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Ssc where status=1 and cityCode=:cityCode");
			query.setParameter("cityCode", CityCode);

			ArrayList<Ssc> list = new ArrayList<Ssc>();
			list = (ArrayList<Ssc>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					Ssc SscClassObj = new Ssc();

					SscClassObj = list.get(i);
					SscClassObj.setSscCode(SscClassObj.getSscCode());
					SscClassObj.setStatus(false);

					User userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					SscClassObj.setModifiedBy(userObj.getStaffLoginId());
					SscClassObj.setModificationDate(new Date());
					session.update(SscClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance
							.insertAuditTrail(new AuditTrail(userObj
									.getUser_no() + "",
									AuditTrail.MODULE_CITY_SSC,
									AuditTrail.FUNCTION_DELETE, SscClassObj
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
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
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
	 * This method performs Delete of User based on city.
	 * </p>
	 * 
	 * @param  int CityCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedUser(int CityCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from User where status=1 and cityCode=:cityCode");
			query.setParameter("cityCode", CityCode);

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
									AuditTrail.MODULE_CITY_USER,
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
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Announcement based on city.
	 * </p>
	 * 
	 * @param  int CityCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedAnnouncement(int CityCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Announcement where status=1 and cityCode=:cityCode");
			query.setParameter("cityCode", CityCode);

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
							AuditTrail.MODULE_CITY_ANNOUNCEMENT,
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
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Holiday based on city.
	 * </p>
	 * 
	 * @param  int CityCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedholiday(int CityCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Holiday where status=1 and cityCode=:cityCode");
			query.setParameter("cityCode", CityCode);

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
							AuditTrail.MODULE_CITY_HOLIDAY,
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
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Presenter based on city.
	 * </p>
	 * 
	 * @param  int CityCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedPresenter(int CityCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Presenter where status=1 and cityCode=:cityCode");
			query.setParameter("cityCode", CityCode);

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
							AuditTrail.MODULE_CITY_PRESENTER,
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
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Schedule based on city.
	 * </p>
	 * 
	 * @param  int CityCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	private void DeleteRelatedSchedule(int cityCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Event where status=1 and cityCode=:cityCode");
			query.setParameter("cityCode", cityCode);

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
							AuditTrail.MODULE_CITY_SCHEDULE_EVENT,
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
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		}

	}

	/**
	 * <p>
	 * This method performs Delete of Interview based on city.
	 * </p>
	 * 
	 * @param  int CityCode
	 * @param  requestParameters  Servlet Request Parameter
	 * @return 
	 */
	
	private void DeleteRelatedInterview(int cityCode, HttpServletRequest req) {

		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Interview where status=1 and cityCode=:cityCode");
			query.setParameter("cityCode", cityCode);

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
							AuditTrail.MODULE_CITY_INTERVIEW,
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
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		}

	}



	public static final String BU_PARAM = "bu";
	public static final String Name = "name";
}
