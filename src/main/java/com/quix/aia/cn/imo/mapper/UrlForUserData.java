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
package com.quix.aia.cn.imo.mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.quix.aia.cn.imo.rest.UserAuthResponds;

public class UrlForUserData {
	 static Logger log = Logger.getLogger(UrlForUserData.class.getName());
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserAuthResponds userAuth=new UserAuthResponds();
		try {
			
			 GsonBuilder builder = new GsonBuilder();
			  DefaultHttpClient httpClient = new DefaultHttpClient();
			  String username="",psw="",co="";
			  username="NSNP306";
			  psw="A111111A";
			  co="0986";
			  
			  HttpGet getRequest = new HttpGet("http://211.144.219.243/isp/rest/index.do?isAjax=true&account="+username+"&co="+co+"&password="+psw+"");
			  getRequest.addHeader("accept", "application/json");
			  HttpResponse response = httpClient.execute(getRequest);

			  if (response.getStatusLine().getStatusCode() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "+ response.getStatusLine().getStatusCode());
			  }

			  BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			  String output;
			  System.out.println("Output from Server .... \n");
			  while ((output = br.readLine()) != null) {
				  System.out.println(output);
				  Gson googleJson = new Gson();
				  userAuth = googleJson.fromJson(output,UserAuthResponds.class);
				  System.out.println("Success "+userAuth.getSuccess());
				  if(userAuth.getSuccess().equals("1")){
					  System.out.println("Login successfully Done");
				  }else{
					  
					  System.out.println("Login Failed ");
				  }
				  
			  }
			  httpClient.getConnectionManager().shutdown();
			  
//			  googleJson = builder.create();
//			  Type listType = new TypeToken<List<UserAuthResponds>>() {}.getType();
			  
			 

			   } catch (ClientProtocolException e) {
					log.log(Level.SEVERE, e.getMessage());
				   e.printStackTrace();
			   } catch (IOException e) {
					log.log(Level.SEVERE, e.getMessage());
				   e.printStackTrace();
			   }
		
	}
 
	
}
