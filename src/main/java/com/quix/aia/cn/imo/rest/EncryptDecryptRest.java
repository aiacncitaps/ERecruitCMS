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
* Date                        Developer                      Change Description
*  27-Aug-2015      	Nibedita             Copyright added/response sent as json
*  18-Nov-2015           Nibedita          Error stored in db
***************************************************************************** */
package com.quix.aia.cn.imo.rest;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import cn.aia.tools.security.AESPasswordManager;
import com.google.gson.Gson;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.utilities.EncrypitDecrypit;

@Path("/encryptDecrypt")
public class EncryptDecryptRest {
	static Logger log = Logger.getLogger(EncryptDecryptRest.class.getName());
	@POST
	@Path("/encrypt")
	@Consumes(MediaType.TEXT_PLAIN)
    @Produces({MediaType.APPLICATION_JSON})

	public Response encryptText(@Context HttpServletRequest request,
	   		   @Context ServletContext context,String textStr)
		{
			log.log(Level.INFO,"EncryptDecryptRest --> encrypt");
		    log.log(Level.INFO,"EncryptDecryptRest --> encrypt --> Data ...  ::::: "+textStr);
			MsgBeans beans = new MsgBeans();
			if(textStr == null || (textStr!=null && textStr.length() == 0)){
				beans.setCode("500");
				beans.setMassage("Text to be encrypt is empty ");
				log.log(Level.INFO,"EncryptDecryptRest --> Text to be encrypt is empty");
				return Response.status(200).entity(new Gson().toJson(beans)).build();
			}else{
				String encryptedText = "";
				EncryptDecrypt obj = new EncryptDecrypt();
				try{
					//encryptedText = EncrypitDecrypit.encrypt(textStr, "password");
					encryptedText =AESPasswordManager.getInstance().encryptPassword(textStr);
				//	encryptedText=encryptedText.replaceAll("[\\t\\n\\r]"," ");
					log.log(Level.INFO,"String after encryption --> encryptedText " +encryptedText );
					obj.setOutput(encryptedText);
					
				}catch(Exception e){
					e.printStackTrace();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					LogsMaintenance logsMain=new LogsMaintenance();
					logsMain.insertLogs("EncryptDecryptRest",Level.SEVERE+"",errors.toString());
					log.log(Level.SEVERE,"Error -->"+e.getMessage());
				}
			
				return Response.status(200).entity(new Gson().toJson(obj)).build();
				
			}
		
		}
	@POST
	@Path("/decrypt")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	
	public Response decryptText(@Context HttpServletRequest request,
	   		   @Context ServletContext context,String textStr)
		{
			log.log(Level.INFO,"EncryptDecryptRest --> decrypt");
		    log.log(Level.INFO,"EncryptDecryptRest --> encrypt --> Data ...  ::::: "+textStr);
			MsgBeans beans = new MsgBeans();
			if(textStr == null || (textStr!=null && textStr.length() == 0)){
				beans.setCode("500");
				beans.setMassage("Text to be decrypt is empty ");
				log.log(Level.INFO,"EncryptDecryptRest --> Text to be decrypt is empty");
				return Response.status(200).entity(new Gson().toJson(beans)).build();
			}else{
				String decryptedText = "";
				EncryptDecrypt obj = new EncryptDecrypt();
				try{
				//	decryptedText = EncrypitDecrypit.decrypt(textStr, "password");
					decryptedText =AESPasswordManager.getInstance().decryptPassword(textStr);
					obj.setOutput(decryptedText);
					log.log(Level.INFO,"String after decryption --> decryptedText ==" +decryptedText);
				}catch(Exception e){
					e.printStackTrace();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					LogsMaintenance logsMain=new LogsMaintenance();
					logsMain.insertLogs("EncryptDecryptRest",Level.SEVERE+"",errors.toString());
					log.log(Level.SEVERE,"Error -->"+e.getMessage());
				}
			
				return Response.status(200).entity(new Gson().toJson(obj)).build();
				
			}
		
		}
}
