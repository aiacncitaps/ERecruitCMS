/*******************************************************************************
* -----------------------------------------------------------------------------
* <br>
* <p><b>Copyright (c) 2014 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
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
* 23-March-2015       Jay Gagnani            added code for access control matrix.
***************************************************************************** */


package com.quix.aia.cn.imo.database;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {
   private static SessionFactory sessionFactory;
    private static Log log = LogFactory.getLog(HibernateFactory.class);
    
    public static SessionFactory buildSessionFactory() throws HibernateException {
        if(sessionFactory != null){
            closeFactory();
        }
        
        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
         
        return sessionFactory;
    }
    
    public static SessionFactory buildIfNeeded() throws HibernateException {
        if(sessionFactory == null){
            //Configuration configuration = new Configuration();
           // configuration.configure();
            //sessionFactory = configuration.buildSessionFactory();
            //sessionFactory = new Configuration().configure("com\\xml\\hibernate.cfg.xml").buildSessionFactory();
            sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
        }
        else
        {
           // sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session openSession() throws HibernateException {
        return sessionFactory.openSession();
    }
    
    public static void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
                sessionFactory = null;
            }
            catch (HibernateException ignored) {
                log.error("Couldn't close SessionFactory", ignored);
            }
        }
    }
    
    public static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            }
            catch (HibernateException ignored) {
                log.error("Couldn't close Session", ignored);
            }
        }
    }
    
    public static void rollback(Transaction tx) {
        try {
            if (tx != null) {
            tx.rollback();
            }
        }
        catch (HibernateException ignored) {
            log.error("Couldn't rollback Transaction", ignored);
        }
    }   
}
