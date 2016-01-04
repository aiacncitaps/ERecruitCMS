package com.tohours.imo.util;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.directwebremoting.util.Logger;
import org.nutz.lang.Lang;

public class DataSourceFactory {
	private static Logger log = Logger.getLogger(DataSourceFactory.class);
	private static Context cntxt;

	public static DataSource born(String jndiName) throws NamingException {
		DataSource dataSource = (DataSource) lookup(jndiName);
		return dataSource;
	}

	private static Object lookup(String jndiName) {
		log.info(jndiName);
		try {
			if (cntxt == null)
				cntxt = (Context) new InitialContext().lookup("java:comp/env");
			return cntxt.lookup(jndiName);
		} catch (NamingException e) {
			try {
				return new InitialContext().lookup(jndiName);
			} catch (NamingException e2) {
				try {
					return ((Context) new InitialContext().lookup("java:/comp/env")).lookup(jndiName);
				} catch (NamingException e3) {
					throw Lang.wrapThrow(e3);
				}
			}
		}
	}
}

