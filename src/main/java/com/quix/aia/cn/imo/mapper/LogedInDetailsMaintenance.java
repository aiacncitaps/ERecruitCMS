package com.quix.aia.cn.imo.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;











import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StringType;

import com.quix.aia.cn.imo.data.logedInDetail.LogedInDetails;
import com.quix.aia.cn.imo.data.properties.ConfigurationProperties;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.ExcelGenerator;
import com.quix.aia.cn.imo.utilities.Pager;

public class LogedInDetailsMaintenance {
	static Logger log = Logger.getLogger(LogedInDetailsMaintenance.class.getName());
	
	
	/**
	 * <p>
	 * This method performs all User Details listing
	 * </p>
	 * @param req 
	 * 
	 *
	 * @return Pager
	 */
	public Pager getAllLogedInListing(HttpServletRequest req) {

		LinkedList item = new LinkedList();
		LogedInDetails loginDetails=null;
		ArrayList vecAllRes = new ArrayList();

		vecAllRes = getAllUserDetails(req);
		
		ExcelGenerator excelGenerator = new ExcelGenerator();
		String str = excelGenerator.GenerateLogInDetailsReport(vecAllRes, req.getRealPath("/resources/templates/"), req.getRealPath("/resources/userFiles/"));
		if(str==null){
			req.setAttribute("path", "#");
			
		}else{
			req.setAttribute("path", str);
			
		}
		if (vecAllRes.size() != 0) {
			for (int i = 0; i < vecAllRes.size(); i++) {
				loginDetails = new LogedInDetails();
				loginDetails = (LogedInDetails) vecAllRes.get(i);
				item.add(loginDetails.getGetUserDetailsListingTableRow(i));
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


	private ArrayList getAllUserDetails(HttpServletRequest req) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx;
		log.log(Level.INFO, "LogedInDetailsMaintenance --> getAllUserDetails");
		ArrayList arrActivity = new ArrayList();
		try {

			String co="";
			if(req.getParameter("branch")!=null){
				co=co+"WHERE CO="+req.getParameter("branch").trim()+" ";
			}
			session = HibernateFactory.openSession();
			//tx = session.beginTransaction();

		SQLQuery  query=session.createSQLQuery("SELECT  LD.LOGEDINID as loginID,LD.CO as co,COUNT(LD.LOGEDINID) AS totalLogedIn,"+
			"(SELECT COUNT(1) FROM T_ADDRESS_BOOK TA WHERE TA.AGENT_ID=LD.LOGEDINID AND TA.CO=LD.CO) AS totalContacts,"+
			"CASE  WHEN LD.[USERTYPE]='AG' THEN (SELECT MAX(U.AGTNAME) FROM AGENTER U WHERE U.AGTCOD=LD.LOGEDINID)" +
            "ELSE (SELECT UU.STAFF_NAME FROM T_USER UU WHERE UU.STAFF_LOGIN_ID=LD.LOGEDINID ) END  AS logedInName, "+
 			"(SELECT COUNT(1) FROM DOWNLOAD_DETAIL DD1 WHERE DD1.LOGEDINID=LD.LOGEDINID AND DD1.CO=LD.CO AND DD1.APP_TYPE='"+ConfigurationProperties.E_RECRUITMENT_APP_URL+"') AS totalDownloadsOfERecruitmentApp, "+
 			"(SELECT COUNT(1) FROM DOWNLOAD_DETAIL DD2 WHERE DD2.LOGEDINID=LD.LOGEDINID AND DD2.CO=LD.CO AND DD2.APP_TYPE='"+ConfigurationProperties.EOP_SCAN_APP_URL+"') AS totalDownloadsOfEOPApp "+
            "FROM LOGEDINDETAIL LD "+co+"  GROUP BY LD.LOGEDINID,LD.CO,LD.USERTYPE ")
			.addScalar("loginID",new StringType()) .addScalar("co", new StringType()).addScalar("totalLogedIn", new StringType()).addScalar("totalContacts", new StringType())
			.addScalar("logedInName", new StringType())
			.addScalar("totalDownloadsOfERecruitmentApp", new StringType()).addScalar("totalDownloadsOfEOPApp", new StringType());
			List<Object[]> entities = query.list();
			LogedInDetails detais=null;
			for(Object[] obj:entities){
				
				detais=new LogedInDetails();
				detais.setLogedInId(obj[0]+"");
				detais.setCo(obj[1]+"");
				detais.setTotalLogedIn(Integer.parseInt(obj[2]+""));
				detais.setTotalContacts(Integer.parseInt(obj[3]+""));
				detais.setTotalDownloadsOfERecruitmentApp(Integer.parseInt(obj[5]+""));
				detais.setTotalDownloadsOfEOPApp(Integer.parseInt(obj[6]+""));
				
				String str="";
				if(obj[4]!=null){
					str=obj[4]+"";
				}
				detais.setLogedInName(str);
				arrActivity.add(detais);
				
			}
			
			/*Query query=session.createQuery("select logedInId,co from LogedInDetails group by logedInId,co");
			arrActivity=(ArrayList) query.list();
*/
			
			
			

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			   LogsMaintenance logsMain=new LogsMaintenance();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logsMain.insertLogs("BUMaintenance",Level.SEVERE+"",errors.toString());
		} finally {
			HibernateFactory.close(session);
		}

		return arrActivity;
	}

	
	
	
}
