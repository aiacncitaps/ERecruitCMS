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
* 18-Nov-2015       Jinatmayee               Error stored in db
****************************************** *********************************** */
package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
import com.quix.aia.cn.imo.data.holiday.Holiday;
import com.quix.aia.cn.imo.data.interview.Interview;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.presenter.Presenter;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.FormObj;
import com.quix.aia.cn.imo.utilities.ImoUtilityData;
import com.quix.aia.cn.imo.utilities.LMSUtil;
import com.quix.aia.cn.imo.utilities.MsgObject;
import com.quix.aia.cn.imo.utilities.Pager;

public class BranchMaintenance {
	
	static Logger log = Logger.getLogger(BranchMaintenance.class.getName());

	public Pager BranchSearchListing(HttpServletRequest requestParamters) {
		
		LinkedList item = new LinkedList();
		Branch branchClassObj = null;
		ArrayList vecAllRes = new ArrayList();
		vecAllRes = getSearchBranch(requestParamters);
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				branchClassObj = new Branch();
				branchClassObj = (Branch) vecAllRes.get(i);
				item.add(branchClassObj.getGetBranchListingTableRow(i));
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

	private ArrayList getSearchBranch(HttpServletRequest req) {
		// TODO Auto-generated method stub
		log.log(Level.INFO, "BranchMaintanence --> getSearchBranch");
		ArrayList arrActivity = new ArrayList();
		Session session = null;

		String str = "";
		if (!req.getParameter("name").equals("")) {
			str = str + " and branchFullName LIKE :name ";
		}
		if(!req.getParameter("bu").equals("0") && req.getParameter("district").equals("0") ){
			str=str+" and  distCode IN (select districtCode from District where status = 1 and buCode IN (select buCode from Bu where status = 1 and buCode=:bu ) )  )";
		}
		
		if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") ){
			str=str+" and  distCode=:dist ";
		}
		

		try {

			session = HibernateFactory.openSession();

			Query query = session.createQuery(" from Branch where status = 1 "
					+ str + " order by orderCode ");
			log.log(Level.INFO, str);
			if (!req.getParameter("name").equals("")) {
				query.setParameter("name", "%"+req.getParameter("name")+"%");

			}
			
			if(!req.getParameter("bu").equals("0") && req.getParameter("district").equals("0") ){
				query.setParameter("bu", Integer.parseInt(req.getParameter("bu")));
			}
			
			if(!req.getParameter("bu").equals("0") && !req.getParameter("district").equals("0") ){
				query.setParameter("dist", Integer.parseInt(req.getParameter("district")));
			}			
			log.log(Level.INFO, "Query "+query);

			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;
	}

	public Object mapForm1(Branch branchobj, HttpServletRequest requestParameters) {
		// TODO Auto-generated method stub
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if (branchobj == null) {
			branchobj = new Branch();
			return branchobj;
		}

		FormObj formObj = (FormObj) requestParameters.getSession()
				.getAttribute("formObj");

		if (requestParameters.getParameter("bu") == null	|| requestParameters.getParameter("bu").equals("0"))
			return new ErrorObject("BU Name", " field is required",localeObj);
		
		if (requestParameters.getParameter("district") == null 	|| requestParameters.getParameter("district").equals("0"))
			return new ErrorObject("District Name", " field is required",localeObj);
		
		
		if (requestParameters.getParameter("name") == null 	|| requestParameters.getParameter("name").length() == 0)
			return new ErrorObject("Branch Code", " field is required",localeObj);
        
        if (requestParameters.getParameter("fullname") == null     || requestParameters.getParameter("fullname").length() == 0)
        	return new ErrorObject("Branch Name", " field is required",localeObj);

		
		String name = requestParameters.getParameterValues("name")[0].trim();
		
//		String citycode = requestParameters.getParameterValues("citycode")[0].trim();
//		citycode = citycode == null || citycode.equals("") ? "0" : citycode;
//		int count = validatename(name, Integer.parseInt(citycode));
//		if (count != 0) {
//			return new ErrorObject("�City� Already exists�", "");
//		}

		log.log(Level.INFO, "BranchMaintenance --> mapForm1 ");

		branchobj.setBranchName(requestParameters.getParameter("name"));
        branchobj.setBranchFullName(requestParameters.getParameter("fullname"));

        if(requestParameters.getParameter("order") == null){
        	branchobj.setOrderCode(0);
		}else{
			try{
				Integer.parseInt(requestParameters.getParameter("order"));
			}catch(Exception e){
				return new ErrorObject("Order","requires numeric value",localeObj);
			}
			branchobj.setOrderCode(Integer.parseInt(requestParameters.getParameter("order")));
		}
		
        
        String branchName="";
		 for (int i = 0; i < requestParameters.getParameterValues("district").length; i++) {
			 log.log(Level.INFO,requestParameters.getParameterValues("district")[i]+"");
			 branchName=branchName+requestParameters.getParameterValues("district")[i]+"|";
		}
        
		 branchobj.setBranchMulti(branchName);
		//branchobj.setDistCode (Integer.parseInt(requestParameters.getParameter("district")));
		branchobj.setStatus(true);
		branchobj.setToken(LMSUtil.getRendomToken());
        if(formObj.getFormType().equals("MODIFY")){

	        if(checkBranch(requestParameters,Integer.parseInt(requestParameters.getParameter("branchcode")),"code")){
	            return new ErrorObject("This is already Exist:", "Branch Code",localeObj);
	        }
	        if(checkBranch(requestParameters,Integer.parseInt(requestParameters.getParameter("branchcode")),"name")){
	            return new ErrorObject("This is already Exist:", "Branch Name",localeObj);
	        }

		 }else{
             if(checkBranch(requestParameters,0,"code")){
                 return new ErrorObject("This is already Exist:", "Branch Code",localeObj);
             }
             if(checkBranch(requestParameters,0,"name")){
                 return new ErrorObject("This is already Exist:", "Branch Name",localeObj);
             }

		 }
		return branchobj;
	}

    private boolean checkBranch(HttpServletRequest req, int branchCode, String nameOrCode) {
		// TODO Auto-generated method stub
		 boolean flag=false;
		 String str="";
		 Session session = null;
         if(nameOrCode.equalsIgnoreCase("code")){
             str=str+" and branchName=:codename ";
         }
         if(nameOrCode.equalsIgnoreCase("name")){
             str=str+" and branchFullName=:name ";
         }
         

		 if(branchCode!=0){
			 str=str+" and branchCode!=:Code";
		 }
		 
		 str=str+" and distCode=:distCode";
			try
			{
				List arrActivity = new ArrayList();
				session = HibernateFactory.openSession();
				
				Query query = session.createQuery(" from Branch  where  status = 1 "+str+" ");
				//query.setParameter("name", branchName);
				
                
				if(branchCode!=0){
					query.setParameter("Code", branchCode);
				 }
                if(nameOrCode.equalsIgnoreCase("code")){
                    query.setParameter("codename", req.getParameter("name"));
                }
                if(nameOrCode.equalsIgnoreCase("name")){
                    query.setParameter("name", req.getParameter("fullname"));
                }

               String branchName[]= req.getParameterValues("district");
    			
   			 for (int i = 0; i < branchName.length ; i++) {
   				 log.log(Level.INFO,branchName[i]+"");
   				query.setParameter("distCode",Integer.parseInt(branchName[i].trim()));
				arrActivity=query.list();
				 
				if(arrActivity.size()!=0)
		        	   flag=true;
   				 
   			}
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				   LogsMaintenance logsMain=new LogsMaintenance();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
			}finally{
				HibernateFactory.close(session);
			}
			
			
			return flag;
		
	}

	public Object createNewBranch(Branch branchobj, HttpServletRequest requestParameters) {
		// TODO Auto-generated method stub
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute("currUserObj");
		branchobj.setCreatedBy(userObj.getStaffLoginId());;
		branchobj.setCreationDate(new Date());
		branchobj.setModifiedBy(userObj.getStaffLoginId());
		branchobj.setModificationDate(new Date());
		int status = insertBranch(branchobj);
		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_BRANCH,
					AuditTrail.FUNCTION_CREATE, branchobj.toString()));
			msgObj = new MsgObject(
					"The new Branch has been successfully created.");
		} else {
			msgObj = new MsgObject("The new Branch has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		Pager Pager=getAllBranchListing();
		requestParameters.setAttribute("pager", Pager);
		requestParameters.getSession().setAttribute("pager",Pager);
		return branchobj;
	}

	public Pager getAllBranchListing() {
		// TODO Auto-generated method stub
		LinkedList item = new LinkedList();
		Branch branchObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllBranch();
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				branchObj = new Branch();
				branchObj = (Branch) vecAllRes.get(i);
				item.add(branchObj.getGetBranchListingTableRow(i));
			}
		}
		Pager pager = new Pager();
		pager.setActualSize(item.size());
		pager.setCurrentPageNumber(0);
		pager.setMaxIndexPages(10);
		pager.setMaxPageItems(10);
		// pager.setTableHeader(BuObj.getGetBUListingTableHdr());
		for (; item.size() % 10 != 0; item.add("<tr></tr>"));
		pager.setItems(item);
		return pager;
	}

	private ArrayList getAllBranch() {
		// TODO Auto-generated method stub
		Session session = null;

		ArrayList arrActivity = new ArrayList();
		try {

			session = HibernateFactory.openSession();

			Query query = session.createQuery(" from Branch where status = 1 order by orderCode ");
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;
	}

	private int insertBranch(Branch branchobj) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx;
		Integer key = 0;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			
			String branchName[]=branchobj.getBranchMulti().split("\\|");
			
			 for (int i = 0; i < branchName.length ; i++) {
				 log.log(Level.INFO,branchName[i]+"");
				 branchobj.setDistCode(Integer.parseInt(branchName[i].trim()));
				 Branch br=new Branch();
				 br.setBranchFullName(branchobj.getBranchFullName());
				 br.setBranchName(branchobj.getBranchName());
				 br.setBuCode(branchobj.getBuCode());
				 br.setCreatedBy(branchobj.getCreatedBy());
				 br.setCreationDate(branchobj.getCreationDate());
				 br.setModifiedBy(branchobj.getCreatedBy());
				 br.setModificationDate(branchobj.getCreationDate());
				 br.setDistCode(branchobj.getDistCode());
				 br.setDistName(branchobj.getDistName());
				 br.setStatus(branchobj.isStatus());
				 br.setToken(branchobj.getToken());
				 br.setOrderCode(branchobj.getOrderCode());
				 
				 key = (Integer) session.save(br);
				 
			}
			 tx.commit();
			
			
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
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

	public Object getBranchBaseOnCode(int branchCode) {
		// TODO Auto-generated method stub
		
		Branch branch=new Branch();
		Session session = null;
		
		try{
			session = HibernateFactory.openSession();
			branch = (Branch)session.get(Branch.class,branchCode);
	
	  
		}catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
		}finally{
			try{
				HibernateFactory.close(session);
				
			}catch(Exception e){
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		
		return branch;
	}

	public Object updateBranch(Branch branchobj, HttpServletRequest requestParameters) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			User userObj = (User) requestParameters.getSession().getAttribute("currUserObj");
			branchobj.setBranchCode(Integer.parseInt(requestParameters.getParameter("branchcode")));
			session.delete(branchobj);
			branchobj.setModifiedBy(userObj.getStaffLoginId());
			branchobj.setModificationDate(new Date());
			int status = insertBranch(branchobj);
			
			//session.update(branchobj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_BRANCH,	AuditTrail.FUNCTION_UPDATE, branchobj.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}

		}
		MsgObject msgObj = new MsgObject("The Branch has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		Pager Pager=getAllBranchListing();
		requestParameters.setAttribute("pager", Pager);
		requestParameters.getSession().setAttribute("pager",Pager);

		return branchobj;
	}

	public void deleteBranch(int branchCode, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Branch branchObj = new Branch();
		Session session = null;
		Transaction tx;
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Branch where status=1 and branchCode=:branchcode");
			query.setParameter("branchcode", branchCode);
			ArrayList<Branch> list = new ArrayList<Branch>();
			list = (ArrayList<Branch>) query.list();

			Branch branchClassObj = new Branch();
			branchClassObj = list.get(0);

			branchClassObj.setBranchCode(branchCode);
			branchClassObj.setStatus(false);

			User userObj = (User) req.getSession().getAttribute("currUserObj");

			branchClassObj.setModifiedBy(userObj.getStaffLoginId());
			branchClassObj.setModificationDate(new Date());
			session.update(branchClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_CITY,
					AuditTrail.FUNCTION_DELETE, branchClassObj.toString()));
			tx.commit();
			
			ImoUtilityData imoutill=new ImoUtilityData();
			imoutill.DeleteRelatedCity(branchClassObj.getBranchCode(), req);
			
			//DeleteRelatedUser(branchCode, req);
			DeleteRelatedAnnouncement(branchCode, req);
			//DeleteRelatedholiday(branchCode, req);
			DeleteRelatedPresenter(branchCode, req);
			DeleteRelatedSchedule(branchCode, req);
			DeleteRelatedInterview(branchCode, req);
			
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		MsgObject msgObj = new MsgObject("The Branch has been successfully Deleted.");
		req.setAttribute("messageObject", msgObj);
		Pager Pager=getAllBranchListing();
		req.setAttribute("pager", Pager);
		req.getSession().setAttribute("pager",Pager);
	}



/**
 * <p>
 * This method performs Delete of User related DistrictCode Delete
 * </p>
 * @param DistrictCode
 * @param req Servlet Request Parameter
 * @return
 */
	private void DeleteRelatedUser(int branchCode, HttpServletRequest req) {
	
	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();
	
		Query query = session
				.createQuery("from User where status=1 and branchCode=:code");
		query.setParameter("code", branchCode);
	
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
			logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
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
	private void DeleteRelatedAnnouncement(int branchCode, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from Announcement where status=1  and branchCode=:code");
		query.setParameter("code", branchCode);

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
			logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
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
	private void DeleteRelatedholiday(int code, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from Holiday where status=1 and branchCode=:code");
		query.setParameter("codde", code);

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
			logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
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
	private void DeleteRelatedPresenter(int code, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from Presenter where status=1 and branchCode=:code");
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
			logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
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
	private void DeleteRelatedSchedule(int code, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from Event where status=1 and branchCode=:code");
		query.setParameter("code", code);

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
			logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
	} 

}
	
	
	/**
	 * <p>
	 * This method performs Delete of Interview based on related DistrictCode Delete.
	 * </p>
	 * @param DistrictCode
	 * @param req Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedInterview(int code, HttpServletRequest req) {

	Session session = null;
	Transaction tx;
	try {
		session = HibernateFactory.openSession();
		tx = session.beginTransaction();

		Query query = session
				.createQuery("from Interview where status=1 and branchCode=:code");
		query.setParameter("code", code);

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
			logsMain.insertLogs("BranchMaintenance",Level.SEVERE+"",errors.toString());
	} 

}
	
}

