package com.quix.aia.cn.imo.utilities;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocaleUtil {
	 Logger log = Logger.getLogger(LocaleUtil.class.getName());
		private Map localeMap = new HashMap();
		private Map localeENMap = new HashMap();
		private Map localeCNMap = new HashMap();
		public Map refreshLocale()
		{
			log.log(Level.INFO , "Caching Trasnlated Text Started");
			try
			{
				ResourceBundle rb = ResourceBundle.getBundle("com.quix.aia.cn.imo.i18n.Translated");
				
				
				Enumeration<String> keys = rb.getKeys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					String value = rb.getString(key);
				
					localeENMap.put(key, value);
				}
				localeMap.put("EN", localeENMap);
				
				Locale chinese  = new Locale("zh","CN");
				
				rb = ResourceBundle.getBundle("com.quix.aia.cn.imo.i18n.Translated", chinese);
				
				
				keys = rb.getKeys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					String value = rb.getString(key);
					localeCNMap.put(key, new String(value.getBytes("ISO-8859-1"), "UTF-8"));
				}
				localeMap.put("CN", localeCNMap);
				log.log(Level.INFO , "Caching Trasnlated Text Finished");
				return localeMap;
			
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				return new HashMap();
			}
			finally
	        {
	        	
	        }
		}
}
