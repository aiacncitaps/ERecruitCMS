// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AuditTrailMaintenance.java

package com.quix.aia.cn.imo.mapper;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.database.HibernateFactory;
import com.quix.aia.cn.imo.utilities.Pager;


public class AuditTrailMaintenance
{
	static Logger log = Logger.getLogger(AuditTrailMaintenance.class.getName());
	private  Session session;
	private  Transaction tx;
    public AuditTrailMaintenance()
    {
    	HibernateFactory.buildIfNeeded();
    }

    public static Pager auditTrailEnquiry(HttpServletRequest rq)
    {
        ArrayList auditTrailList = new ArrayList();//getAuditTrail(rq);
        return getAuditTrailListPager(auditTrailList);
    }

    public static Pager getAuditTrailListPager(ArrayList auditTrailList)
    {
        LinkedList item = new LinkedList();
        for(int i = 0; i < auditTrailList.size(); i++)
            item.add(((AuditTrail)auditTrailList.get(i)).getAuditTrailRow());

        Pager pager = new Pager();
        pager.setActualSize(item.size());
        pager.setCurrentPageNumber(0);
        pager.setMaxIndexPages(10);
        pager.setMaxPageItems(10);
        pager.setTableHeader(AuditTrail.getAuditTrailHdr());
        for(; item.size() % 10 != 0; item.add(""));
        pager.setItems(item);
        return pager;
    }

  /*  public static ArrayList getAuditTrail(HttpServletRequest rq)
    {
        AuditTrail auditTrail = null;
        ArrayList auditTrailList = new ArrayList(0);
        try
        {
        	String dateTo=rq.getParameter(DATE_TO_PARAM);
        	String dateFrom = rq.getParameter(DATE_FRM_PARAM);
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement statement =null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String sqlStr = "";
            if(dateFrom.equals("") || dateTo.equals("")){
            	sqlStr = "select at.log_date, at.log_time, at.module_name, at.action, tu.name ,at.item_code " +
            			"from T_USERS tu, T_AUDIT_TRAIL_CMS at " +
            			"where tu.user_code=at.user_code and module_name like ? "; //+
            			//"and (log_date >= ? and log_date <= ?)";
            }
            else{
            sqlStr = "select at.log_date, at.log_time, at.module_name, at.action, tu.name, at.item_code from T_USERS tu, T_AUDIT_TRAIL_CMS at where tu.user_code=at.user_code and module_name like ? and (log_date >= ? and log_date <= ?)";
            }
            if(dateFrom.equals("") || dateTo.equals("")){
            	statement=conn.prepareStatement(sqlStr);
            	statement.setString(1, "%" + rq.getParameter(MODULE_NAME_PARAM) + "%");
            }else{
            statement= conn.prepareStatement(sqlStr);
            Date frmDate = (new SimpleDateFormat("dd-MM-yyyy")).parse(rq.getParameter(DATE_FRM_PARAM));
            Date toDate = (new SimpleDateFormat("dd-MM-yyyy")).parse(rq.getParameter(DATE_TO_PARAM));
            statement.setString(1, "%" + rq.getParameter(MODULE_NAME_PARAM) + "%");
            statement.setString(2, (frmDate.getYear() + 1900) + "-" + (frmDate.getMonth() + 1) + "-" + frmDate.getDate());
            statement.setString(3, (toDate.getYear() + 1900) + "-" + (toDate.getMonth() + 1) + "-" + toDate.getDate());
            }
            log.log(Level.INFO,statement.toString());
            for(rs = statement.executeQuery(); rs.next(); auditTrailList.add(auditTrail))
            {
                auditTrail = new AuditTrail();
                auditTrail.setAction(rs.getString("action"));
                auditTrail.setModuleName(rs.getString("module_name"));
                auditTrail.setUserName(rs.getString("name"));
                auditTrail.setTime(rs.getString("log_time"));
                auditTrail.setItemName(rs.getString("item_code"));
                auditTrail.setDate(formatter.format(rs.getDate("log_date")));
            }

            rs.close();
            statement.close();
            conn.close();
        }
        catch(SQLException e)
        {
        	log.log(Level.SEVERE,e.getMessage());
            e.printStackTrace();
        }
        catch(ParseException e)
        {
        	log.log(Level.SEVERE,e.getMessage());
            e.printStackTrace();
        }
        return auditTrailList;
    }
*/
    public  void insertAuditTrail(AuditTrail auditTrail)
    {
    	Connection conn = null;
        try
        {
        	 java.sql.Date today = new java.sql.Date((new Date()).getTime());
             Date time = new Date();
        	session = HibernateFactory.openSession();
			tx= session.beginTransaction();
			auditTrail.setUserId(auditTrail.getUserId());
			auditTrail.setDate(today);
			auditTrail.setTime(time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds());
			auditTrail.setModuleName(auditTrail.getModuleName());
			auditTrail.setItemName(auditTrail.getItemName());
			auditTrail.setAction(auditTrail.getAction());
			session.save(auditTrail);
			tx.commit();
        	/*log.log(Level.INFO,"insertAuditTrail....");
            ResultSet rs = null;
            java.sql.Date today = new java.sql.Date((new Date()).getTime());
            Date time = new Date();
            String sqlStr = "";
            String category = "";
            String users = "";
            sqlStr = "INSERT INTO T_AUDIT_TRAIL_CMS (user_code, log_date, log_time, module_name, action, item_code)VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlStr);
            statement.setString(1, auditTrail.getUserId());
            statement.setDate(2, today);
            statement.setString(3, time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds());
            statement.setString(4, auditTrail.getModuleName());
            statement.setString(5, auditTrail.getAction());
            statement.setString(6, auditTrail.getItemName());
            statement.executeUpdate();
            log.log(Level.INFO,statement.toString());
       
            statement.close();
            conn.close();*/
        }
        catch(Exception e)
        {
        	log.log(Level.SEVERE,e.getMessage());
            e.printStackTrace();
        }
        finally{
			try{
				HibernateFactory.close(session);
			}catch(Exception e){
				
				log.log(Level.SEVERE,e.getMessage());
				e.printStackTrace();
			}
		}
        
    }

    public static String MODULE_NAME_PARAM = "MODULE_NAME_PARAM";
    public static String DATE_FRM_PARAM = "reportDateFrom";
    public static String DATE_TO_PARAM = "reportDateTo";

}
