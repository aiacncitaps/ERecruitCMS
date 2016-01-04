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
 *                                     khyati             created
 * 15-may-2015               khyati             Added Copy rights and security 
 *  18-Nov-2015               Nibedita          Error stored in db
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
import com.quix.aia.cn.imo.data.department.Department;
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
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author khyati
 * @version 1.0
 */


public class DepartmentMaintenance {
	static Logger log = Logger.getLogger(DepartmentMaintenance.class.getName());
	private Session session;
	private Transaction tx;

	
	
	/**
	 * <p>
	 * This method performs listing of default search result of  Department Record 
	 * </p>
	 * 
	 * @param
	 * @return  pager
	 */
	
	public Pager getAllDepartmentListing() {

		LinkedList item = new LinkedList();
		Department departmentObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllDepartment();
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				departmentObj = new Department();
				departmentObj = (Department) vecAllRes.get(i);
				item.add(departmentObj.getGetDepartmentListingTableRow(i));
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

	public ArrayList getAllDepartment() {

		log.log(Level.INFO, "DepartmentMaintanence --> getAllDepartment");
		ArrayList arrActivity = new ArrayList();
		Session session = null;
		LogsMaintenance logsMain=new LogsMaintenance();

		try {

			session = HibernateFactory.openSession();

			Query query = session
					.createQuery(" from Department where status = 1  order by orderCode,deptName");
			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;
	}
	
	

	/**
	 * <p>
	 * This method performs call for insertion method for Department & audit trail for
	 * Department add & sets message object in request
	 * </p>
	 * 
	 * @param Department class object
	 * @param req Servlet Request Parameter
	 * @return Department class object
	 */
	public Object createNewDepartment(Department DepartmentClassObj,
			HttpServletRequest requestParameters) {

		log.log(Level.INFO, "DepartmentMaintenance --> createNewDepartment ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute(
				"currUserObj");
		DepartmentClassObj.setCreatedBy(userObj.getStaffLoginId());;
		DepartmentClassObj.setCreationDate(new Date());
		DepartmentClassObj.setModifiedBy(userObj.getStaffLoginId());
		DepartmentClassObj.setModificationDate(new Date());
		int status = insertDepartment(DepartmentClassObj);

		if (status != 0) {
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_DEPARTMENT,
					AuditTrail.FUNCTION_CREATE, DepartmentClassObj.toString()));
			msgObj = new MsgObject(
					"The new Department has been successfully created.");
		} else {
			msgObj = new MsgObject("The new Department has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllDepartmentListing());
		requestParameters.getSession().setAttribute("pager",
				getAllDepartmentListing());
		return DepartmentClassObj;
	}

	
	/**
	 * <p>
	 * This method performs insert of Department
	 * </p>
	 * 
	 * @param Department Class Object
	 * @return int key 0 or 1
	 */

	public int insertDepartment(Department DepartmentClassObj) {
		Integer key = 0;
		Session session = null;
		Transaction tx = null;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			key = (Integer) session.save(DepartmentClassObj);
			tx.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
			}
		}
		return key;
	}

	/**
	 * <p>
	 * This method performs edit view of Department.
	 * </p>
	 * 
	 * @param  int DepartmentCode
	 * @return Department class object
	 */
    public Department getDepartment(int DepartmentCode) {
		Department DepartmentClassObj = new Department();
		Session session = null;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			session = HibernateFactory.openSession();
			DepartmentClassObj = (Department) session.get(Department.class,
					DepartmentCode);

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
			}
		}

		return DepartmentClassObj;
	}

	/**
	 * <p>
	 * This method performs update view of Department.
	 * </p>
	 * 
	 * @param  Department class object
	 * @param  requestParameters Servlet Request Parameter
	 * @return Department class object
	 */

	public Department updateDepartment(Department DepartmentClassObj,
			HttpServletRequest requestParameters) {
		Session session = null;
		Transaction tx = null;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			User userObj = (User) requestParameters.getSession().getAttribute(
					"currUserObj");
			DepartmentClassObj.setDeptCode(Integer.parseInt(requestParameters
					.getParameter("deptCode")));
			DepartmentClassObj.setModifiedBy(userObj.getStaffLoginId());
			DepartmentClassObj.setModificationDate(new Date());

			session.update(DepartmentClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_DEPARTMENT,
					AuditTrail.FUNCTION_UPDATE, DepartmentClassObj.toString()));
			tx.commit();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
			}

		}
		MsgObject msgObj = new MsgObject(
				"The Department has been successfully Updated.");
		requestParameters.setAttribute("messageObject", msgObj);
		requestParameters.setAttribute("pager", getAllDepartmentListing());
		requestParameters.getSession().setAttribute("pager",
				getAllDepartmentListing());

		return DepartmentClassObj;
	}

	/**
	 * <p>
	 * This method performs Delete Department.
	 * </p>
	 * @param DepartmentCode
	 * @param req Servlet Request Parameter
	 * @return
	 */
	public void deleteDepartment(int DepartmentCode, HttpServletRequest req) {

		Department DepartmentObj = new Department();
		Session session = null;
		Transaction tx = null;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {

			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from Department where status=1 and deptCode=:deptCode");
			query.setParameter("deptCode", DepartmentCode);
			ArrayList<Department> list = new ArrayList<Department>();
			list = (ArrayList<Department>) query.list();

			Department DepartmentClassObj = new Department();
			DepartmentClassObj = list.get(0);

			DepartmentClassObj.setDeptCode(DepartmentCode);
			DepartmentClassObj.setStatus(false);

			User userObj = (User) req.getSession().getAttribute("currUserObj");

			DepartmentClassObj.setModifiedBy(userObj.getStaffLoginId());
			DepartmentClassObj.setModificationDate(new Date());
			session.update(DepartmentClassObj);
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj
					.getUser_no() + "", AuditTrail.MODULE_DEPARTMENT,
					AuditTrail.FUNCTION_DELETE, DepartmentClassObj.toString()));
			tx.commit();
			DeleteRelatedUSER(DepartmentCode, req);
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
			}
		}
		MsgObject msgObj = new MsgObject(
				"The Department has been successfully Deleted.");
		req.setAttribute("messageObject", msgObj);
		req.setAttribute("pager", getAllDepartmentListing());
		req.getSession().setAttribute("pager", getAllDepartmentListing());

	}

	/**
	 * <p>
	 * This method performs Delete of User based on related DepartmentCode Deleted.
	 * </p>
	 * @param DepartmentCode
	 * @param req Servlet Request Parameter
	 * @return
	 */
	private void DeleteRelatedUSER(int DepartmentCode, HttpServletRequest req) {
		Session session = null;
		Transaction tx;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("from User where status=1 and department=:department");
			query.setParameter("department", DepartmentCode);

			ArrayList<User> list = new ArrayList<User>();
			list = (ArrayList<User>) query.list();

			if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {

					User UserClassObj = new User();

					UserClassObj = list.get(i);
					UserClassObj.setUser_no(UserClassObj.getUser_no());
					UserClassObj.setStatus(false);

					User userObj = (User) req.getSession().getAttribute(
							"currUserObj");

					UserClassObj.setModifiedBy(userObj.getStaffLoginId());
					UserClassObj.setModificationDate(new Date());
					session.update(UserClassObj);
					AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
					auditTrailMaintenance
							.insertAuditTrail(new AuditTrail(userObj
									.getUser_no() + "",
									AuditTrail.MODULE_DEPARTMENT_USER,
									AuditTrail.FUNCTION_DELETE, UserClassObj
											.toString()));
					tx.commit();

					session.flush();
				}
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
			}
		}

	}
	
	
	
	/**
	 * <p>
	 * This is  particular search listing method based on particular criteria.
	 * </p>
	 * 
	 * @param
	 * @return ArrayList
	 */
	public Pager DepartmentSearchListing(HttpServletRequest requestParamters) {

		LinkedList item = new LinkedList();
		Department DepartmentClassObj = null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getSearchDepartment(requestParamters);
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				DepartmentClassObj = new Department();
				DepartmentClassObj = (Department) vecAllRes.get(i);
				item.add(DepartmentClassObj.getGetDepartmentListingTableRow(i));
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
	 * This is sub method of particular search listing method based on particular criteria.
	 * </p>
	 * 
	 * @param
	 * @return ArrayList
	 */
	
	private ArrayList getSearchDepartment(HttpServletRequest req) {

		log.log(Level.INFO, "DepartmentMaintanence --> getSearchDepartment");
		ArrayList arrActivity = new ArrayList();
		String str = "";
		if (!req.getParameter("name").equals("")) {
			str = str + " and deptName LIKE :name ";
		}
		LogsMaintenance logsMain=new LogsMaintenance();
		Session session = null;
		try {

			session = HibernateFactory.openSession();

			Query query = session
					.createQuery(" from Department where status = 1 " + str
							+ " order by orderCode,deptName");
			if (!req.getParameter("name").equals("")) {
				query.setParameter("name", "%"+req.getParameter("name")+"%");

			}

			arrActivity = (ArrayList) query.list();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;

	}

	/**
	 * <p>
	 * This method checks all validations & sets values to bean
	 * </p>
	 * 
	 * @param Department class object
	 * @param requestParamet  Servlet Request Parameter
	 * @return Department object/Error object
	 */
	public Object mapForm1(Department departmentobj,
			HttpServletRequest requestParameters) {
		LocaleObject localeObj = (LocaleObject)requestParameters.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if (departmentobj == null) {
			departmentobj = new Department();
			return departmentobj;
		}
		FormObj formObj = (FormObj) requestParameters.getSession()
				.getAttribute("formObj");

		if (requestParameters.getParameter("name") == null
				|| requestParameters.getParameter("name").length() == 0)
		return new ErrorObject("Department Name", " field is required",localeObj);
		log.log(Level.INFO, "DepartmentMaintenance --> mapForm1 ");
		String name = requestParameters.getParameterValues("name")[0].trim();
		String deptCode = requestParameters.getParameterValues("deptCode")[0]
				.trim();
		deptCode = deptCode == null || deptCode.equals("") ? "0" : deptCode;
		long count = validatename(name, Integer.parseInt(deptCode));
		if (count != 0) {
			return new ErrorObject("Department Already exists", "",localeObj);
		}

		Department DepartmentclassObj = new Department();
		if(requestParameters.getParameter("order") == null){
			DepartmentclassObj.setOrderCode(0);
		}else{
			try{
				Integer.parseInt(requestParameters.getParameter("order"));
			}catch(Exception e){
				return new ErrorObject("Order","requires numeric value",localeObj);
			}
			DepartmentclassObj.setOrderCode(Integer.parseInt(requestParameters.getParameter("order")));
		}
		
		DepartmentclassObj.setDeptName(requestParameters.getParameter("name"));
		DepartmentclassObj.setStatus(true);
		DepartmentclassObj.setToken(LMSUtil.getRendomToken());

		return DepartmentclassObj;
	}

	
	
	
	/**
	 * <p>
	 * This method performs validation of duplicate name
	 * </p>
	 * 
	 * @param name
	 * @param departmentCode
	 * @return integer count value of name available in database.
	 */
	
	private long validatename(String name, int departmentCode) {
		long count = 0;
		LogsMaintenance logsMain=new LogsMaintenance();
		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();

			Query query = session
					.createQuery("select count(*) from Department where status=true and deptName=:deptName and deptCode!=:deptCode");
			query.setParameter("deptName", name);
			query.setParameter("deptCode", departmentCode);
			count = (Long) query.uniqueResult();

			tx.commit();
			session.flush();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("DepartmentMaintenance",Level.SEVERE+"",errors.toString());
			}
		}

		return count;
	}

	public static final String Name = "name";

}
