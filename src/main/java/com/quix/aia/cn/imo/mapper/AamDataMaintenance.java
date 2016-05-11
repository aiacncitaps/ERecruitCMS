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
 * 30-July-2015               Maunish            File Added
 * 05-Nov-2015               Nibedita            New method added
 ******************************************************************************/

package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.database.HibernateFactory;

/**
 * <p>
 * This class defines the data operations-Add,Update,Delete,search.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 */
public class AamDataMaintenance {
	static Logger log = Logger.getLogger(AamDataMaintenance.class.getName());

	/**
	 * <p>
	 * This method retrieves single Model for particular agentId
	 * </p>
	 * 
	 * @param agentId
	 * @return AamData Class Object
	 * 
	 */
	public static AamData retrieveDataToModel(String agentId, String coBranch) {

		ArrayList<AamData> list = new ArrayList();
		AamData aamData = new AamData();
		try {
			session = clientSessionFactory.openSession();
			session.setDefaultReadOnly(true);

			Criteria crit = session.createCriteria(AamData.class);
			if (null != agentId && !"".equals(agentId)) {
				crit.add(Restrictions.eq("agentCode", agentId));
			}
			if (null != coBranch && !"".equals(coBranch)) {
				crit.add(Restrictions.eq("branch", coBranch));
			}
			crit.setFirstResult(0);
			crit.setMaxResults(1);
			list = (ArrayList) crit.setCacheable(true).list();
		} catch (Exception he) {
			he.printStackTrace();
			
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			he.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AamDataMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			session.setDefaultReadOnly(false);
			session.close();
		}

		if(!list.isEmpty()){
			aamData =  list.get(0);
			aamData = retrieveOtherData(aamData);
		}
		
		return aamData;
	}

	/**
	 * <p>
	 * This method retrieves list of data for matching bu, district, city, ssc
	 * </p>
	 * @param office 
	 * 
	 * @param bu, district, city, ssc
	 *            
	 * @return List (List of Class Object)
	 * 
	 */
//	public static List retrieveDataToList(String bu, String district, String city, String ssc) {
	public static List retrieveDataToList(String city, String ssc,String branch, String office) {
		ArrayList list = new ArrayList();
		
			
		try {
			session = clientSessionFactory.openSession();
			session.setDefaultReadOnly(true);

			Criteria crit = session.createCriteria(AamData.class);
//			if (null != bu && !"".equals(bu)) {
//				crit.add(Restrictions.eq("bu", bu));
//			}
//			if (null != district && !"".equals(district)) {
//				crit.add(Restrictions.eq("district", district));
//			}
			if (null != branch && !"".equals(branch)) {
				crit.add(Restrictions.eq("branch", branch));
			}
			
			if (null != city && !"".equals(city)) {
				crit.add(Restrictions.eq("city", city));
			}
			if (null != ssc && !"".equals(ssc)) {
				crit.add(Restrictions.eq("ssc", ssc));
			}
			
			if (null != office && !"".equals(office)) {
				crit.add(Restrictions.eq("officeCode", office));
			}

			list = (ArrayList) crit.setCacheable(true).list();
		} catch (Exception he) {
			he.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			he.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AamDataMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			session.close();
		}

		return list;
	}
	
	/**
	 * <p>
	 * This method retrieves list of data for matching bu, district, city, ssc
	 * </p>
	 * 
	 * @param AamData
	 *            
	 * @return AamData model
	 * 
	 */
	public static AamData retrieveOtherData(AamData aamData) {
//		Session session = null;
//		Transaction tx;
		try {
//			session = HibernateFactory.openSession();

			//aamData=getData(aamData, "office");
		//	aamData=getData(aamData, "ssc");
		//	aamData=getData(aamData, "city");
			aamData=getData(aamData, "branch");
			aamData=getData(aamData, "district");
			aamData=getData(aamData, "bu");

//			session.flush();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AamDataMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
//			try {
//				session.flush();HibernateFactory.close(session);
//
//			} catch (Exception e) {
//				log.log(Level.SEVERE, e.getMessage());
//				e.printStackTrace();
//			}
		}

		return aamData;
	}
	
	/**
	 * <p>
	 * This method retrieves list of data for matching bu, district, city, ssc
	 * </p>
	 * 
	 * @param AamData
	 * @param typeOfData
	 *            
	 * @return AamData model
	 * 
	 */
	
	public static AamData getData(AamData aamData,String typeOfData){
		Integer i = 0;
		String queryStr = "";
		Session session = null;
		Query query = null;
		Object[] objs;
		try {
			session = HibernateFactory.openSession();
			session.setDefaultReadOnly(true);
			
			if("office".equalsIgnoreCase(typeOfData)){
				
				queryStr ="SELECT officeCode, officeName  FROM Office WHERE officeName = '"+aamData.getOfficeCode()+"'  AND status=1 ";
			}else if("ssc".equalsIgnoreCase(typeOfData)){
				
				queryStr ="SELECT sscCode, sscName  FROM Ssc WHERE sscName = '"+aamData.getSsc()+"' AND status=1";
			}else if("city".equalsIgnoreCase(typeOfData)){
				
				queryStr ="SELECT cityCode, cityName FROM City WHERE cityName = '"+aamData.getCity()+"' AND status=1";
			}else if("branch".equalsIgnoreCase(typeOfData)){
				
				queryStr ="SELECT branchCode, distCode, branchFullName FROM Branch WHERE branchName = '"+aamData.getBranch()+"' AND status=1";
			}else if("district".equalsIgnoreCase(typeOfData)){
				
				queryStr ="SELECT districtName, buCode FROM District WHERE  status=1  AND districtCode = "+aamData.getDistrictCode();
			}else if("bu".equalsIgnoreCase(typeOfData)){
				
				queryStr ="SELECT buCode, buName FROM Bu WHERE status=1  AND buCode = "+aamData.getBuCode();
			}
			
			query = session.createQuery(queryStr);
			List list = query.setCacheable(true).list();
			if(!list.isEmpty()){
				objs = (Object []) list.get(0);
				if("office".equalsIgnoreCase(typeOfData)){
					
					aamData.setOfficeCode(""+ objs[0]);
					aamData.setOfficeName(""+ objs[1]);
				}else if("ssc".equalsIgnoreCase(typeOfData)){
					aamData.setSscCode((Integer) objs[0]);
					aamData.setSsc((String) objs[1]);
				}else if("city".equalsIgnoreCase(typeOfData)){
					aamData.setCityCode((Integer) objs[0]);
					aamData.setCity((String) objs[1]);
				}else if("branch".equalsIgnoreCase(typeOfData)){
					
					aamData.setBranchCode((Integer) objs[0]);
					aamData.setDistrictCode((Integer) objs[1]);
					aamData.setBranchFulleName((String) objs[2]);
				}else if("district".equalsIgnoreCase(typeOfData)){
					
					aamData.setDistrict((String) objs[0]);
					aamData.setBuCode((Integer) objs[1]);
				}else if("bu".equalsIgnoreCase(typeOfData)){
					
					aamData.setBu((String) objs[1]);
				}
			}else{
				if("office".equalsIgnoreCase(typeOfData)){
					
					aamData.setOfficeCode("0");
				}else if("ssc".equalsIgnoreCase(typeOfData)){
					
					aamData.setSscCode(0);
				}else if("city".equalsIgnoreCase(typeOfData)){
					
					aamData.setCityCode(0);
				}else if("branch".equalsIgnoreCase(typeOfData)){
					
					aamData.setBranchCode(0);
				}else if("district".equalsIgnoreCase(typeOfData)){
					
					aamData.setDistrictCode(0);
				}else if("bu".equalsIgnoreCase(typeOfData)){
					
					aamData.setBuCode(0);
				}
			}
			session.flush();
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AamDataMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			try {
					session.flush();HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		
		return aamData;
	}
	/**
	 * <p>
	 * This method retrieves agentName for particular agentId
	 * </p>
	 * 
	 * @param agentId
	 * @return String 
	 * 
	 */
	public static String retrieveAgentName(String agentId, String coBranch) {
		String agentName = "";
		try {
			session = clientSessionFactory.openSession();
			session.setDefaultReadOnly(true);

			Criteria crit = session.createCriteria(AamData.class);
			if (null != agentId && !"".equals(agentId)) {
				crit.setProjection(Projections.property("agentName"));
				crit.add(Restrictions.eq("agentCode", agentId));
			}
			if (null != coBranch && !"".equals(coBranch)) {
				crit.add(Restrictions.eq("branch", coBranch));
			}
			ArrayList  list = (ArrayList) crit.setCacheable(true).list();
			if(list!=null && list.size() > 0)
				agentName = (String)list.get(0);
		} catch (Exception he) {
			he.printStackTrace();
			
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			he.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AamDataMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			session.close();
		}

		return agentName;
	}
	/**
	 * <p>
	 * This method retrieves Id for particular agentId
	 * </p>
	 * 
	 * @param agentId
	 * @return Integer 
	 * 
	 */
	public static Integer checkIfAgentExist(String agentId,String coBranch) {
		Integer id = 0;
		try {
			session = clientSessionFactory.openSession();
			session.setDefaultReadOnly(true);

			Criteria crit = session.createCriteria(AamData.class);
			if (null != agentId && !"".equals(agentId)) {
				//crit.setProjection(Projections.property("id"));
				crit.add(Restrictions.eq("agentCode", agentId));
			}
			if (null != coBranch && !"".equals(coBranch)) {
				//crit.setProjection(Projections.property("id"));
				crit.add(Restrictions.eq("branch", coBranch));
			}

			ArrayList  list = (ArrayList) crit.setCacheable(true).list();
			if(list!=null && list.size() > 0)
				id = 1;
			else
				id=0;
		} catch (Exception he) {
			he.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			he.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AamDataMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			session.close();
		}

		return id;
	}
	public static final SessionFactory clientSessionFactory = new Configuration().configure("client_hibernate.cfg.xml").buildSessionFactory();
	public static Session session = null;
//	public static Transaction transaction = null;

}
