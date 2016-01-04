/******************************************************************************
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
* Creation & Modification History:
* Date              Developer          Change Description
* 18-Nov-2015       Jay                 
* 
******************************************************************************/


package com.quix.aia.cn.imo.mapper;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.quix.aia.cn.imo.data.logs.Logs;
import com.quix.aia.cn.imo.database.HibernateFactory;

/**
 * <p>This class defines the data operations </p>
 * @author Jay
 * @version 1.0
 */
public class LogsMaintenance {
	
	 static Logger log = Logger.getLogger(LogsMaintenance.class.getName());
	 public LogsMaintenance()
	{
			HibernateFactory.buildIfNeeded();
	}
	public void insertLogs(String FileName, String level, String Msg) {
		// TODO Auto-generated method stub
		log.log(Level.INFO,"LogsMaintenance --> insertLogs ");
		Logs logs=new Logs();
		Session session = null;
		try{
			session = HibernateFactory.openSession();
			Transaction tx = session.beginTransaction();
			logs.setLogDate(new Date());
			logs.setFileName(FileName);
			logs.setLevel(level);
			logs.setMsg(Msg);
			session.save(logs);
			tx.commit();
			
		}catch(Exception e){
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		
		
	}

}
