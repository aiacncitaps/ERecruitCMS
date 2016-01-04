package com.tohours.imo.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.nutz.castor.Castors;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.MultiLineProperties;

public class XmlProxy {



    private MultiLineProperties mp = new MultiLineProperties();
    public XmlProxy(){
    	this("/hibernate.cfg.xml");
    }

    public XmlProxy(String path){
        this.setPath(path);
    }

	public void setPath(String path) {
    	InputStream input = XmlProxy.class.getResourceAsStream(path);
    	String content;
        mp = new MultiLineProperties();
		try {
			content = TohoursUtils.inputStream2String(input);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	if(TohoursUtils.isNotEmpty(content)){
    		Pattern pattern= Pattern.compile("<property name=\"(.*)\">(.*)</property>");
    	    Matcher matcher = pattern.matcher(content);
    	    while(matcher.find()){
    	    	mp.put(matcher.group(1), matcher.group(2));
    	    }
    	}
        
    }


    /**
     * @param key
     *            键
     * @return 是否包括这个键
     * @since 1.b.50
     */
    public boolean has(String key) {
        return mp.containsKey(key);
    }

    public void put(String key, String value) {
        mp.put(key, value);
    }

    public XmlProxy set(String key, String val) {
        put(key, val);
        return this;
    }

    public String check(String key) {
        String val = get(key);
        if (null == val)
            throw Lang.makeThrow("Ioc.$conf expect property '%s'", key);
        return val;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean dfval) {
        String val = get(key);
        if (Strings.isBlank(val))
            return dfval;
        return Castors.me().castTo(val, Boolean.class);
    }

    public String get(String key) {
    	if("connection.datasource".equals(key)){
    		return mp.get(key).replaceAll("java:/?comp/env/", "");
    	} else {
            return mp.get(key);
    	}
    }

    public String get(String key, String defaultValue) {
        return Strings.sNull(mp.get(key), defaultValue);
    }

    public List<String> getList(String key) {
        List<String> re = new ArrayList<String>();
        String keyVal = get(key);
        if (Strings.isNotBlank(keyVal)) {
            String[] vlist = Strings.splitIgnoreBlank(keyVal, "\n");
            for (String v : vlist) {
                re.add(v);
            }
        }
        return re;
    }

    public String trim(String key) {
        return Strings.trim(get(key));
    }

    public String trim(String key, String defaultValue) {
        return Strings.trim(get(key, defaultValue));
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key));
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public long getLong(String key) {
        return getLong(key, -1);
    }

    public long getLong(String key, long dfval) {
        try {
            return Long.parseLong(get(key));
        }
        catch (NumberFormatException e) {
            return dfval;
        }
    }

    public String getTrim(String key) {
        return Strings.trim(get(key));
    }

    public String getTrim(String key, String defaultValue) {
        return Strings.trim(get(key, defaultValue));
    }

    public List<String> getKeys() {
        return mp.keys();
    }

    public Collection<String> getValues() {
        return mp.values();
    }

    public Properties toProperties() {
        Properties p = new Properties();
        for (String key : mp.keySet()) {
            p.put(key, mp.get(key));
        }
        return p;
    }


    public Map<String, String> toMap() {
        return new HashMap<String, String>(mp);
    }
    
    public static void main(String[] args) throws NamingException, SQLException {
    	// Obtain our environment naming context
    	Context initCtx = new InitialContext();
    	Context envCtx = (Context) initCtx.lookup("java:comp/env");

    	// Look up our data source
    	DataSource ds = (DataSource)
    	  envCtx.lookup("jdbc/EmployeeDB");

    	// Allocate and use a connection from the pool
    	Connection conn = ds.getConnection();
    	System.out.println(conn.getMetaData().getCatalogTerm());
    	//... use this connection to access the database ...
    	conn.close();
	}
}
