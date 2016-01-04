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
* 18-Nov-2015            Nibedita          Error stored in db       

****************************************** *********************************** */
package com.quix.aia.cn.imo.rest;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quix.aia.cn.imo.data.user.User;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.mapper.UserMaintenance;


@Path("/user")
public class UserAuthRest {
			
	static Logger log = Logger.getLogger(UserAuthRest.class.getName());
	
	@GET
	@Path("/getUserAuth")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUser(@Context HttpServletRequest request,
						   @Context ServletContext context)
	{
		
		log.log(Level.INFO,"UserAuthRest --> getUser ");
		MsgBeans beans = new MsgBeans();
		String userID = request.getParameter("agentId");
		String psw=request.getParameter("psw");
		String branch=request.getParameter("branch");
		
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		
		try{	
			
			UserMaintenance userMaintenance = new UserMaintenance();
            User user = userMaintenance.authenticateUser(userID, psw, branch, context);
            ArrayList<User> list=new ArrayList<User>();
            list.add(user);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		   // auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
            
		}catch(Exception e){
			log.log(Level.INFO,"EventRest --> getEvent --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("UserAuthRest",Level.SEVERE+"",errors.toString());
			//auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
		
		}
		
}
