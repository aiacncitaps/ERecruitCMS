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
* Date              Developer          Change Description
* 10-June-2015          Jay               File Added
* 18-Nov-2015            Nibedita          Error stored in db
***************************************************************************** */

package com.quix.aia.cn.imo.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.common.RestForm;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.utilities.LMSUtil;

/**
 * <p>Logic to get Webservice Url for Rest service.</p>
 * 
 * @author Jay
 * @version 1.0
 *
 */


@Path("/webServices")
public class WebServiceRest {
	
	static Logger log = Logger.getLogger(WebServiceRest.class.getName());
	@POST
	@Path("/getAllWebServices")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllWebservices(@Context HttpServletRequest request,
			   @Context ServletContext context,String jsonString)
	{
			log.log(Level.INFO,"WebServiceRest --> getAllWebservices ");
			boolean flag=LMSUtil.isJSONValid(jsonString);
			MsgBeans beans = new MsgBeans();
			AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
			try{
				GsonBuilder builder = new GsonBuilder();
				 Gson googleJson  = builder.create();
				if(flag==true){
					
					 /*Type listType = new TypeToken<List<RestForm>>(){}.getType();
				        List<RestForm> jsonObjList = googleJson.fromJson(jsonString, listType);
				        RestForm restForm = jsonObjList.get(0); */
					RestForm restForm= googleJson.fromJson(jsonString, RestForm.class);
				        String agentId = restForm.getAgentId();
						
						
					ArrayList<WebService> list=new ArrayList<WebService>();
					WebService webService=new WebService();
					Host hostbean=new Host();
		//			hostbean.setHost(host);
					webService.setHost(hostbean);
					list.add(webService);
					
					
					webService=new WebService();
					webService.setUrl("/rest/holiday/getAllHoliday");
					webService.setModuleName("Holiday");
					webService.setMethod("POST");
					webService.setJsonString(jsonString);
					webService.setDescription("Returns all holidayes in json format");
					list.add(webService);
					
					webService=new WebService();
					webService.setUrl("/rest/announcement/getAllannouncement");
					webService.setModuleName("Announcement");
					webService.setMethod("POST");
					webService.setJsonString(jsonString);
					webService.setDescription("Returns all Announcement in json format");
					list.add(webService);
					
					
					webService=new WebService();
					webService.setUrl("/rest/presenter/getAllpresenter");
					webService.setModuleName("Presenter");
					webService.setMethod("POST");
					webService.setJsonString(jsonString);
					webService.setDescription("Returns all Presenter in json format");
					list.add(webService);
					
					webService=new WebService();
					webService.setUrl("/rest/interview/getAllInterview");
					webService.setModuleName("Interview");
					webService.setMethod("POST");
					webService.setJsonString(jsonString);
					webService.setDescription("Returns all Interview in json format");
					list.add(webService);
					
					
					webService=new WebService();
					webService.setUrl("/rest/event/getAllEop");
					webService.setModuleName("EOP");
					webService.setMethod("POST");
					webService.setJsonString(jsonString);
					webService.setDescription("Returns all EOP in json format");
					list.add(webService);
					
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
				    String json = gson.toJson(list);
				    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_REST, "SUCCESS"));
					return Response.status(200).entity(json).build();
				}else{
					beans.setCode("500");
					beans.setMassage("Json not valid");
					return Response.status(500).entity(googleJson.toJson(beans)).build();
				}
			}catch(Exception e){
				log.log(Level.INFO,"WebServiceRest --> getAllWebservices  --> Exception..... ");
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				LogsMaintenance logsMain=new LogsMaintenance();
				logsMain.insertLogs("WebServiceRest",Level.SEVERE+"",errors.toString());
				
				beans.setCode("500");
				beans.setMassage("Error");
				Gson gson = new GsonBuilder().setExclusionStrategies().create(); //.serializeNulls()
			    String json = gson.toJson(beans);
			    
			    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_FAIL, "FAILED"));
				return Response.status(200).entity(json).build();
			}
			
			
	}

}
