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
 * -----------------------------------------------------------------------------
 * 10-July-2014                Hemraj             created
 * 18-Nov-2015            Nibedita          Error stored in db
 ***************************************************************************** */

package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.quix.aia.cn.imo.constants.SessionAttributes;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.goalsetting.GoalConfig;
import com.quix.aia.cn.imo.data.locale.LocaleObject;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ErrorObject;
import com.quix.aia.cn.imo.utilities.MsgObject;

/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author Hemraj
 * @version 1.0
 */
public class GoalConfigMaintenance {
	static Logger log = Logger.getLogger(GoalConfigMaintenance.class.getName());

	
	/**
	 * <p>
	 * This method checks all validations & sets values to bean
	 * </p>
	 * 
	 * @param GoalConfig
	 *            goalConfig object
	 * @param requestParameters
	 *            Servlet Request Parameter
	 * @return GoalConfig object/Error object
	 */
	public Object mapForm1(GoalConfig goalConfig, HttpServletRequest request) 
	{
		log.log(Level.INFO, "goalConfigMaintenance --> mapForm1 ");
		if (goalConfig == null) {
			goalConfig = new GoalConfig();
			return goalConfig;
		}
		LocaleObject localeObj = (LocaleObject)request.getSession().getAttribute(SessionAttributes.LOCALE_OBJ);
		if (request.getParameter("potentialCandidate") == null)
			return new ErrorObject("Potential Candidate", " field is required",localeObj);
		
		if(!validateInt(request.getParameter("potentialCandidate").trim()))
	        return new ErrorObject("Please enter a number value for the following field: ", "Potential Candidate",localeObj);
		
		if(!validatePositiveNum(request.getParameter("potentialCandidate").trim()))
            return new ErrorObject("Please enter a value greater than 0 following field: ", "Potential Candidate",localeObj);
		
		if (request.getParameter("eopRegistration") == null )
			return new ErrorObject("EOP Registration", " field is required",localeObj);
		
		if(!validateInt(request.getParameter("eopRegistration").trim()))
	        return new ErrorObject("Please enter a number value for the following field: ", "EOP Registration",localeObj);
		
		if(!validatePositiveNum(request.getParameter("eopRegistration").trim()))
            return new ErrorObject("Please enter a value greater than 0 following field: ", "EOP Registration",localeObj);
		
		if (request.getParameter("firstInterview") == null )
			return new ErrorObject("First Interview", " field is required",localeObj);
		
		if(!validateInt(request.getParameter("firstInterview").trim()))
	        return new ErrorObject("Please enter a number value for the following field: ", "First Interview",localeObj);
		
		if(!validatePositiveNum(request.getParameter("firstInterview").trim()))
            return new ErrorObject("Please enter a value greater than 0 following field: ", "First Interview",localeObj);
		
		if (request.getParameter("eopAttendance") == null)
			return new ErrorObject("EOP Attendance", " field is required",localeObj);
		
		if(!validateInt(request.getParameter("eopAttendance").trim()))
	        return new ErrorObject("Please enter a number value for the following field: ", "EOP Attendance",localeObj);
		
		if(!validatePositiveNum(request.getParameter("eopAttendance").trim()))
            return new ErrorObject("Please enter a value greater than 0 following field: ", "EOP Attendance",localeObj);
		
		if (request.getParameter("ccAssessment") == null)
			return new ErrorObject("CC Assessment", " field is required",localeObj);
		
		if(!validateInt(request.getParameter("ccAssessment").trim()))
	        return new ErrorObject("Please enter a number value for the following field: ", "CC Assessment",localeObj);
		
		if(!validatePositiveNum(request.getParameter("ccAssessment").trim()))
            return new ErrorObject("Please enter a value greater than 0 following field: ", "CC Assessment",localeObj);
		
		if (request.getParameter("companyInterview") == null)
			return new ErrorObject("Company Interview", " field is required",localeObj);
		
		if(!validateInt(request.getParameter("companyInterview").trim()))
	        return new ErrorObject("Please enter a number value for the following field: ", "Company Interview",localeObj);
		
		if(!validatePositiveNum(request.getParameter("companyInterview").trim()))
            return new ErrorObject("Please enter a value greater than 0 following field: ", "Company Interview",localeObj);
		
		if (request.getParameter("passALE") == null)
			return new ErrorObject("Pass ALE", " field is required",localeObj);
		
		if(!validateInt(request.getParameter("passALE").trim()))
	        return new ErrorObject("Please enter a number value for the following field: ", "Pass ALE",localeObj);
		
		if(!validatePositiveNum(request.getParameter("passALE").trim()))
            return new ErrorObject("Please enter a value greater than 0 following field: ", "Pass ALE",localeObj);
		
		if (request.getParameter("attendedTraining") == null)
			return new ErrorObject("Attended Training", " field is required",localeObj);
		
		if(!validateInt(request.getParameter("attendedTraining").trim()))
	        return new ErrorObject("Please enter a number value for the following field: ", "Attended Training",localeObj);
		
		if(!validatePositiveNum(request.getParameter("attendedTraining").trim()))
            return new ErrorObject("Please enter a value greater than 0 following field: ", "Attended Training",localeObj);
		
		if (request.getParameter("signContract") == null)
			return new ErrorObject("Sign Contract", " field is required",localeObj);
		
		if(!validateInt(request.getParameter("signContract").trim()))
	        return new ErrorObject("Please enter a number value for the following field: ", "Sign Contract",localeObj);
		
		if(!validatePositiveNum(request.getParameter("signContract").trim()))
            return new ErrorObject("Please enter a value greater than 0 following field: ", "Sign Contract",localeObj);
		
		
		String cell = request.getParameter("potentialCandidate").trim();
		goalConfig.setPotentialCandidate(Integer.parseInt(cell));

		cell = request.getParameter("eopRegistration").trim();
		goalConfig.setEopRegistration(Integer.parseInt(cell));
		
		cell = request.getParameter("firstInterview").trim();
		goalConfig.setFirstInterview(Integer.parseInt(cell));
		
		cell = request.getParameter("eopAttendance").trim();
		goalConfig.setEopAttendance(Integer.parseInt(cell));
		
		cell = request.getParameter("ccAssessment").trim();
		goalConfig.setCcAssessment(Integer.parseInt(cell));
		
		cell = request.getParameter("companyInterview").trim();
		goalConfig.setCompanyInterview(Integer.parseInt(cell));
		
		cell = request.getParameter("passALE").trim();
		goalConfig.setPassALE(Integer.parseInt(cell));
		
		cell = request.getParameter("attendedTraining").trim();
		goalConfig.setAttendedTraining(Integer.parseInt(cell));
		
		cell = request.getParameter("signContract").trim();
		goalConfig.setSignContract(Integer.parseInt(cell));
		
		return goalConfig;
	}

	/**
	 * <p>
	 * This method performs call for insertion method for GoalConfig & audit trail for
	 * GoalConfig add & sets message object in request
	 * </p>
	 * 
	 * @param GoalConfig
	 *            GoalConfigClassObj object
	 * @param req
	 *            Servlet Request Parameter
	 * @return GoalConfig object
	 */

	public Object createNewConfig(GoalConfig goalConfig, HttpServletRequest requestParameters) {

		log.log(Level.INFO, "GoalConfigMaintenance --> createNewGoalConfig ");
		MsgObject msgObj = null;

		User userObj = (User) requestParameters.getSession().getAttribute("currUserObj");
		goalConfig.setCreated_by(userObj.getStaffLoginId());
		goalConfig.setCreated_date(new Date());
		int status = insertConfig(goalConfig);

		if (status != 0) 
		{
			AuditTrailMaintenance auditTrailMaintenance = new AuditTrailMaintenance();
			auditTrailMaintenance.insertAuditTrail(new AuditTrail(userObj.getUser_no() + "", AuditTrail.MODULE_GOAL_CONFIG,AuditTrail.FUNCTION_CREATE, goalConfig.toString()));
			msgObj = new MsgObject("The new Goal Configuration has been successfully created.");
		}
		else 
		{
			msgObj = new MsgObject("The new Goal Configuration has not been created.");
		}
		requestParameters.setAttribute("messageObject", msgObj);

		return goalConfig;
	}

	
	

	/**
	 * <p>
	 * This method performs insert of GoalConfig
	 * </p>
	 * 
	 * @param GoalConfig
	 *            Class Object
	 * @return int key 0 or 1
	 */
	public int insertConfig(GoalConfig goalConfig) {

		Session session = null;
		Transaction tx = null;
		Integer key = 0;

		try {
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			key = (Integer) session.save(goalConfig);
			tx.commit();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("GoalConfigMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LogsMaintenance logsMain=new LogsMaintenance();
				logsMain.insertLogs("GoalConfigMaintenance",Level.SEVERE+"",errors.toString());
			}
		}
		return key;
	}

	/**
	 * <p>
	 * This method performs edit view of particular GoalConfig based on code
	 * </p>
	 * 
	 * @param code
	 * @return GoalConfig class object
	 */
	public GoalConfig getGoalConfig(int code) 
	{
		Session session = null;
		Transaction tx = null;
		GoalConfig goalConfig = new GoalConfig();
		try 
		{
			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			goalConfig = (GoalConfig) session.get(GoalConfig.class, code);
		}
		catch (Exception e) 
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("GoalConfigMaintenance",Level.SEVERE+"",errors.toString());
		}
		finally 
		{
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LogsMaintenance logsMain=new LogsMaintenance();
				logsMain.insertLogs("GoalConfigMaintenance",Level.SEVERE+"",errors.toString());
			}
		}

		return goalConfig;
	}

	public Map<String, GoalConfig> getConfigData() {

		Session session = null;
		Transaction tx;
		log.log(Level.INFO, "GoalConfigMaintanence --> getConfigData");
		GoalConfig goalConfig = new GoalConfig();
		Map<String,GoalConfig> map = new HashMap<String, GoalConfig>();
		try {

			session = HibernateFactory.openSession();
			tx = session.beginTransaction();
			String SQL_QUERY = "from GoalConfig where created_date in(select  max(created_date) from GoalConfig)";
			Query query = session.createQuery(SQL_QUERY);
			List list = query.list();
			if(list.size()>0)
			{  
				int maxId = ((GoalConfig)list.get(0)).getGoal_config_id();
				goalConfig = (GoalConfig) session.get(GoalConfig.class,maxId);
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("GoalConfigMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
			map.put("goalConfigation", goalConfig);
		}

		return map;
	}
	
	public static boolean validatePositiveNum(String strIn)
	{
		int num = Integer.parseInt(strIn);
		return num > 0;
	}

	public static boolean validateInt(String strIn)
	{
		try
		{
			int number = Integer.parseInt(strIn);
			return number > -1;
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("GoalConfigMaintenance",Level.SEVERE+"",errors.toString());
			return false;
		}
	}

	public static final String Name = "name";

}
