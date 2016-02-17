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
 * 17-Feb-2016                Maunish             File Added  
 ******************************************************************************/

package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.data.group.GroupDetail;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.LMSUtil;


/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 */
public class GroupDetailMaintenance {
	static Logger log = Logger
			.getLogger(GroupDetailMaintenance.class.getName());
	
	/**
	 * <p>
	 * This method performs insert or update of GroupDetail from List of
	 * GroupDetail called from rest. When addressCode is 0, it performs insert
	 * otherwise performs update.
	 * </p>
	 * 
	 * @param List
	 *            <GroupDetail/> List of Class Object
	 * @return List<GroupDetail/> List of Class Object
	 * 
	 */
	public String insertOrUpdateRestBatch(List<GroupDetail> groupDetailList) {

		Session session = null;
		Transaction tx = null;
		int key = 0;
		List<GroupDetail> groupDetailListUpdated = new ArrayList();
		GroupCandidateDetailMaintenance groupCandidateDetailMaintenance = new GroupCandidateDetailMaintenance();

		String returnJsonString = "";
		boolean flag = true;
		try {
			session = HibernateFactory.openSession();
			int count = 0;
			
			tx = session.beginTransaction();
			for (GroupDetail groupDetail : groupDetailList) {
				
				key = (int) groupDetail.getGroupCode();
				if (0 == key) {
					groupDetail.setGroupCode(null);
				}
				session.saveOrUpdate(groupDetail);
				if (++count % 10 == 0) {
					session.flush();
					session.clear();
				}
				groupDetailListUpdated.add(groupDetail);
			}
			tx.commit();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			tx = null;
			session = null;
		}
		
		for (GroupDetail groupDetail : groupDetailListUpdated) {

			if(!groupDetail.isGroupIsDelete()){
				groupCandidateDetailMaintenance.saveOrUpdate(groupDetail);
			}
			if (flag) {
				flag = false;
			} else {
				returnJsonString += ",";
			}
			returnJsonString += "{\"groupCode\":"+ groupDetail.getGroupCode() + ",\"groupName\":\""+ groupDetail.getGroupName() + "\",\"groupIsDelete\":"+groupDetail.isGroupIsDelete()+"}";
		}
		
		return returnJsonString;
	}
	
	/**
	 * <p>
	 * This method retrieves all GroupDetails
	 * <p>
	 * 
	 * @param agentID
	 * @return List<GroupDetail> List of Class Object
	 * 
	 */
	public List<GroupDetail> getGroupDetails(HttpServletRequest request, ServletContext context) {

		Session session = null;
		ArrayList<GroupDetail> list = null;
		Criteria criteria = null;
		
		String agentId = request.getParameter("agentCode");
		String dateTime = request.getParameter("dateTime");
		String coBranch = request.getParameter("branchCode");

		agentId = agentId == null ? "" : agentId;
		Date date = null;
		if (null != dateTime && !"".equals(dateTime)) {
			try {
				date = LMSUtil.yyyymmddHHmmssdashed.parse(dateTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			criteria = session.createCriteria(GroupDetail.class);
			criteria.add(Restrictions.eq("agentCode", agentId));
			criteria.add(Restrictions.eq("groupIsDelete", false));
			if (null != date) {
				criteria.add(Restrictions.ge("groupModifiedDate", date));
			}
			if (null != coBranch && !"".equals(coBranch)) {
				criteria.add(Restrictions.eq("branchCode", coBranch));
			}
			list = (ArrayList) criteria.list();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("GroupDetailMaintenance",Level.SEVERE+"",errors.toString());
			list = new ArrayList<GroupDetail>();
		} finally {
			try {
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
			criteria = null;
		}
		
		return list;
	}
	
	/**
	 * <p>
	 * This method retrieves all GroupDetails of Particular Agent
	 * <p>
	 * 
	 * @param agentID
	 * @return List<GroupDetail> List of Class Object
	 * 
	 */
	public String getDeletedGroupDetails(HttpServletRequest request, ServletContext context) {

		Session session = null;
		ArrayList<GroupDetail> list = null;
		ArrayList<Object[]> tempList = new ArrayList<Object[]>();
		Query query = null;
		String jsonString = ""; 

		String agentId = request.getParameter("agentCode");
		String dateTime = request.getParameter("dateTime");
		String coBranch = request.getParameter("branchCode");

		agentId = agentId == null ? "" : agentId;
		Date date = null;
		if (null != dateTime && !"".equals(dateTime)) {
			try {
				date = LMSUtil.yyyymmddHHmmssdashed.parse(dateTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			String queryString = "SELECT groupCode, agentCode, branchCode, groupName FROM GroupDetail WHERE agentCode = :agentCode and groupIsDelete = :groupIsDelete"; 
			if (null != date) {
				queryString += " and groupModifiedDate >= :groupModifiedDate";
			}
			if (null != coBranch && !"".equals(coBranch)) {
				queryString += " and branchCode = :branchCode";
			}
			
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			query = session.createQuery(queryString);
			query.setParameter("agentCode", agentId);
			query.setParameter("groupIsDelete", true);
			if (null != date) {
				query.setParameter("groupModifiedDate", date);
			}
			if (null != coBranch && !"".equals(coBranch)) {
				query.setParameter("branchCode", coBranch);
			}
			tempList = (ArrayList) query.list();

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage());
			ex.printStackTrace();LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			ex.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("GroupDetailMaintenance",Level.SEVERE+"",errors.toString());
			list = new ArrayList<GroupDetail>();
		} finally {
			try {
				session.setDefaultReadOnly(false);
				HibernateFactory.close(session);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
			session = null;
			query = null;
		}
		
		GroupDetail groupDetail = null;  
		boolean flag = false;
		for(Object[] objectArray : tempList){
			if(flag){
				jsonString +=",";
			}else{
				flag = true;
			}
			jsonString += "{"
					+ "\"groupCode\" : "+objectArray[0]+","
					+ "\"agentCode\" : \""+objectArray[1]+"\","
					+ "\"branchCode\" : \""+objectArray[2]+"\","
					+ "\"groupName\" : \""+objectArray[3]+"\","
					+ "\"groupIsDelete\" : "+true+""
					+ "}";
		}
		
		return jsonString;
	}
}
