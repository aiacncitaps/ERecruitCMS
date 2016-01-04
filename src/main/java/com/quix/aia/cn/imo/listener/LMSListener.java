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
* 
****************************************** *********************************** */
package com.quix.aia.cn.imo.listener;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.quix.aia.cn.imo.constants.ApplicationAttribute;
import com.quix.aia.cn.imo.mapper.AamDataMaintenance;
import com.quix.aia.cn.imo.mapper.PropertiesMaintenance;
import com.quix.aia.cn.imo.mapper.UserMaintenance;
import com.quix.aia.cn.imo.utilities.LocaleUtil;

public class LMSListener implements ServletContextListener  {
	private Logger log = Logger.getLogger(LMSListener.class.getName());
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		
		LocaleUtil localeUtil = new LocaleUtil();
		Map localeMap = localeUtil.refreshLocale();
		context.setAttribute(ApplicationAttribute.LOCALE_MAP, localeMap);
		
		
		UserMaintenance userMaintenance = new UserMaintenance();
		String latestJSVersion = userMaintenance.getLatestJSVersion();
		context.setAttribute(ApplicationAttribute.LATEST_JS_CSS_VERSION, latestJSVersion);
		
		context.setAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP, PropertiesMaintenance.fetchConfigurationProperties());
//		context.setAttribute(ApplicationAttribute.MAIL_PROPERTIES_MAP, PropertiesMaintenance.fetchMailProperties());
		
		AamDataMaintenance aamDataMaintenance = new AamDataMaintenance();
	}
	
}
