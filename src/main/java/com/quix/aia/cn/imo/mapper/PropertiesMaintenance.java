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
 * 14-August-2015               Maunish            File Added
 ******************************************************************************/

package com.quix.aia.cn.imo.mapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;

import com.quix.aia.cn.imo.data.properties.ConfigurationProperties;
import com.quix.aia.cn.imo.data.properties.MailProperties;
import com.quix.aia.cn.imo.database.HibernateFactory;

/**
 * <p>
 * 
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 */
public class PropertiesMaintenance {
	static Logger log = Logger.getLogger(PropertiesMaintenance.class.getName());

	/**
	 * <p>
	 * This method retrieves Configuration Properties into Map
	 * </p>
	 * 
	 * @return Map<String,String>
	 * 
	 */
	public static Map<String,String> fetchConfigurationProperties(){
		Map<String,String> propertiesMap = new HashMap<String, String>();
		Session session = null;
		ConfigurationProperties configurationProperties=null;
		try {
			session = HibernateFactory.openSession();
			List list = session.createCriteria(ConfigurationProperties.class).list();
			
			for(Iterator itr = list.iterator(); itr.hasNext();){
				configurationProperties = (ConfigurationProperties) itr.next();
				propertiesMap.put(configurationProperties.getConfigurationKey(), configurationProperties.getConfigurationValue());
			}
			
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return propertiesMap;
	}

	/**
	 * <p>
	 * This method retrieves Mail Properties into Map
	 * </p>
	 * 
	 * @return Map<String,String>
	 * 
	 */
	public static Map<String,String> fetchMailProperties(){
		Map<String,String> propertiesMap = new HashMap<String, String>();
		Session session = null;
		MailProperties mailProperties=null;
		try {
			session = HibernateFactory.openSession();
			List list = session.createCriteria(MailProperties.class).list();
			
			for(Iterator itr = list.iterator(); itr.hasNext();){
				mailProperties = (MailProperties) itr.next();
				propertiesMap.put(mailProperties.getMailKey(), mailProperties.getMailValue());
			}
			
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				HibernateFactory.close(session);

			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}

		return propertiesMap;
	}
}
