package com.quix.aia.cn.imo.mapper;

import com.quix.aia.cn.imo.utilities.PasswordHashing;

import cn.aia.tools.security.AESPasswordManager;

public class temp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
String str = "H8lNU/h1zzMk6x6oQZhneyTndlcy+dzD4IUHeCejuoYDCBBwRAiDX34GlZadNZx6o64Uk/BWRwdt\r\nB1DDoWXPAQPdYg0cf5Y+sJXvMiJPShaJxg5QWjHcRNdDRPgnQRwYZV/cAmCTKXXpr+OXjKUw0UHv\r\nYfjANPRBVuMWluEVc3YeF/YNxGq/I9yUMS2OYtJq3jG0r5gL0MZ9QocouJ7WiCZFVQJGzZbwWbrf\r\nyGgvfRpkPnPFib+nMwTsGuYLJZlS9dEFe5Ml/CTvDZmxVzryDnitRBcXcpoqM21uTLO2/3o+W4zT\r\nqQcAkmao2IBNw89YseqHdl/RSwNccr9g4mLl7r1mk3a1g+3V3ZjAasmz4CCFNLxBbQaCx2EUFBoy\r\n6lBPvW5PjLPABa9+Svu3uIewP4gIxdIwyCxm63ltNXnRX1DbiI0hKQuMNDXSHWeZ9JNjDfDY3bKh\r\nQA8hhVDLxGELbna2IaotKNHXpbE5QV8pcoa1YLK36IYeWf/rL/rhou85fUOT+pDrzxnLJQh0kgE4\r\nAx7wYonvQjCdVqDn7DviHuIovoHHDkujZ7eZNGqCSEpq";
        
        String str2 = AESPasswordManager.getInstance().decryptPassword(str);
        
        System.out.println(str2);
str2 = "{\"BRANCH\":\"0986\",\"CITY\":NULL,\"SSC\":NULL,\"USERTYPE\":NULL,\"USERID\":\"000015710\",\"USERNAME\":\"张雯燕                         \",\"USERSTATUS\":\"A\",\"TEAMCODE\":\"G00000060\",\"TEAMNAME\":\"孙海峰                        \",\"OFFICECODE\":\"YA01\",\"OFFICENAME\":\"进德一处                      \",\"CERTIID\":\"310110198008080808  \",\"DATEOFBIRTH\":\"1980-08-08\",\"GENDER\":\"F \",\"CONTRACTEDDATE\":\"2002-10-01 12:00:00.0\",\"TITLE\":\"L3\",\"DTLEADER\":NULL}";
	}

}
