package com.quix.aia.cn.imo.servlets;

import javax.servlet.http.HttpServletRequest;

import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.utilities.EmailNotification;

public class demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		EmailNotification mail=new EmailNotification();
//		User user=new User();
//		user.setStaffLoginId("新部门test123");
//		HttpServletRequest req = null;
//		System.out.println(user.getStaffLoginId());
//		mail.sendPasswordNotification(req, user, "Create");
//		
		
		String temp="1|3|5|10|15|";
	String temp2[] = temp.split("\\|");
	for (String str : temp2)
		System.out.println(str);
		
		
	}

}
