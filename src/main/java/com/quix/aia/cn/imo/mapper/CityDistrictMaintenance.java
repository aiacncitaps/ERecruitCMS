package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.city_dist.City_Dist;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FormObj;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;

public class CityDistrictMaintenance {

	static Logger log = Logger.getLogger(CityDistrictMaintenance.class.getName());
	
	public Object mapForm1(City_Dist Cityobj, HttpServletRequest requestParameters) {
		log.log(Level.INFO, "CityDistrictMaintenance --> mapForm1 ");
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if (Cityobj == null) {
			Cityobj = new City_Dist();
			return Cityobj;
		}

		FormObj formObj = (FormObj) requestParameters.getSession().getAttribute("formObj");
		String strname="",strfullname="";
		if (requestParameters.getParameter("district") == null
				|| requestParameters.getParameter("district").equals("0"))
		return new ErrorObject("District Name", " field is required",localeObj);
		
		if (requestParameters.getParameter("city") == null
				|| requestParameters.getParameter("city").equals("0"))
		return new ErrorObject("City Name", " field is required",localeObj);
		
		
		String[] str=requestParameters.getParameter("city").split("-");
		
			strname=str[0];
			strfullname=str[1];
		
		
		boolean flag=checkDuplicate(requestParameters,strname);
		if(flag==true){
			return new ErrorObject("City Already exists", "",localeObj);
		}
		
		Cityobj.setDistCode(Integer.parseInt(requestParameters.getParameter("district")));
		Cityobj.setCityCode(strname);
		Cityobj.setCityFullName(strfullname);
		
		
		return Cityobj;
		
	}
	
	
	private boolean checkDuplicate(HttpServletRequest req, String str) {
		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		log.log(Level.INFO, "CityDistrictMaintenance --> getAllCity");
		Session session = null;

		ArrayList arrActivity = new ArrayList();
		try {

			session = HibernateFactory.openSession();
			
			 Criteria criteria = session.createCriteria(City_Dist.class);
			
			criteria.add(Restrictions.eq("distCode", Integer.parseInt(req.getParameter("district"))));
			criteria.add(Restrictions.eq("cityCode", str));
			criteria.add(Restrictions.eq("status", true));
			arrActivity = (ArrayList) criteria.list();
			
			if(arrActivity.size()>0){
				return true;
			}
			

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			 e.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("CityMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

	
		
		return false;
	}


	public Object createNewCity(City_Dist CityClassObj,
			HttpServletRequest requestParameters) {

		log.log(Level.INFO, "CityDistrictMaintenance --> createNewCity ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute("currUserObj");
		CityClassObj.setCreatedBy(userObj.getStaffName());
		CityClassObj.setCreationDate(new Date());
		CityClassObj.setStatus(true);
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
		Pager pager=getAllCityListing(requestParameters);
		requestParameters.setAttribute("pager", pager);
		requestParameters.getSession().setAttribute("pager",pager);
		return CityClassObj;
	}


	public int insertCity(City_Dist CityClassObj) {
		log.log(Level.INFO, "CityDistrictMaintenance --> insertCity");
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

	public Pager getAllCityListing(HttpServletRequest requestParamters) {
		log.log(Level.INFO, "CityDistrictMaintenance --> getAllCityListing");
		LinkedList item = new LinkedList();
		City_Dist CityObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllCity(requestParamters);
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				CityObj = new City_Dist();
				CityObj = (City_Dist) vecAllRes.get(i);
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


	private ArrayList getAllCity(HttpServletRequest req) {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "CityDistrictMaintenance --> getAllCity");
		Session session = null;

		ArrayList arrActivity = new ArrayList();
		try {

			session = HibernateFactory.openSession();

			
			 Criteria criteria = session.createCriteria(City_Dist.class);
			
				if (null != req.getParameter("district1")) {
					if(!req.getParameter("district1").equals("0")){
						criteria.add(Restrictions.eq("distCode", Integer.parseInt(req.getParameter("district"))));
					}
					
				}
				
				if (null != req.getParameter("city1")){
					if(!req.getParameter("city1").equals("0")){
						criteria.add(Restrictions.eq("cityCode", req.getParameter("city1")));
					}
					
				}
				criteria.add(Restrictions.eq("status", true));
				 arrActivity = (ArrayList) criteria.list();
			

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


	public void deleteCity(int CODE, HttpServletRequest req) {
		// TODO Auto-generated method stub


		
		Session session = null;
		Transaction tx;
		User userObj = (User) req.getSession().getAttribute("currUserObj");
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			
			Query query = session.createQuery("from City_Dist where cityDistCode=:code ");
			query.setParameter("code", CODE);
			ArrayList<City_Dist> list=(ArrayList<City_Dist>) query.list();
			if(list!=null){
				for (City_Dist citydist:list) {
					citydist.setStatus(false);
					session.update(citydist);
				}
			}
			
			
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getUser_no() + "", AuditTrail.MODULE_CITY,
					AuditTrail.FUNCTION_DELETE, CODE+""));
			
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
		MsgObject msgObj = new MsgObject("The City has been successfully Deleted.");
		Pager pager=getAllCityListing(req);
		req.setAttribute("messageObject", msgObj);
		req.setAttribute("pager", pager);
		req.getSession().setAttribute("pager",pager);

	
	}

	
	
}
