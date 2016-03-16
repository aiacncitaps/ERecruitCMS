package com.quix.aia.cn.imo.servlets;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.utilities.EmailNotification;

public class demo {

	public static String sstr;
	
	demo(){
		strrrr();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		EmailNotification mail=new EmailNotification();
//		User user=new User();
//		user.setStaffLoginId("新部门test123");
//		HttpServletRequest req = null;
//		System.out.println(user.getStaffLoginId());
//		mail.sendPasswordNotification(req, user, "Create");
//		
		
	/*	String temp="1|3|5|10|15|";
	String temp2[] = temp.split("\\|");
	for (String str : temp2)
		System.out.println(str);
		
		
	}*/
		/*Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 15);
		String o = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz").format( c.getTime() );
		System.out.println(o);*/
		
		
		System.out.println(demo.sstr);
		
		/*String str="[{'eventCode':143,'candidateName':'BHP','servicingAgent':'S00012','dob':'2013-07-04','gender':'F','timeIn':'10:10:00'}]";
		boolean flag=isJSONValid(str);
		System.out.println(flag);*/
	}
	
	public  boolean isJSONValid(String test) {
	
	    try {
	        new JSONObject(test);
	    } catch (JSONException ex) {
	        // edited, to include @Arthur's comment
	        // e.g. in case JSONArray is valid as well...
	        try {
	            new JSONArray(test);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public String strrrr(){
		demo.sstr="hiiii";
		
		return demo.sstr;
		
		
	}

}
