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
* 14-August-2015    	Maunish            Modified 
* 02-Sept-2015          Nibedita          Download file service added
* 18-Nov-2015            Nibedita          Error stored in db
***************************************************************************** */

package com.quix.aia.cn.imo.rest;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.common.RestForm;
import com.quix.aia.cn.imo.data.presenter.PresenterMaterial;
import com.quix.aia.cn.imo.mapper.AamDataMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.mapper.PresenterMaintenance;
import com.quix.aia.cn.imo.utilities.LMSUtil;


/**
 * <p>Logic to get Presenter for Rest service.</p>
 * 
 * @author Jay
 * @version 1.0
 *
 */

@Path("/presenter")
public class PresenterRest {
	static Logger log = Logger.getLogger(PresenterRest.class.getName());
	@POST
	@Path("/getAllpresenter")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPresenter(@Context HttpServletRequest request,
						   @Context ServletContext context,String jsonString)
	{
		log.log(Level.INFO,"PresenterRest --> getPresenter ");
		boolean flag=LMSUtil.isJSONValid(jsonString);
		MsgBeans beans = new MsgBeans();
		ArrayList list = new ArrayList();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			GsonBuilder builder = new GsonBuilder();
			 Gson googleJson  = builder.create();
			if(flag==true){
				 
				/* Type listType = new TypeToken<List<RestForm>>(){}.getType();
			        List<RestForm> jsonObjList = googleJson.fromJson(jsonString, listType);
			        RestForm restForm = jsonObjList.get(0);*/
				RestForm restForm= googleJson.fromJson(jsonString, RestForm.class);
			        String agentId = restForm.getAgentId();
					String coBranch =restForm.getCo();
				AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId, coBranch); 
				PresenterMaintenance objPresenterMaintenance = new PresenterMaintenance();
				list = objPresenterMaintenance.getAllPresenterRest(aamData, agentId);
				
				googleJson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
			    String json = googleJson.toJson(list);
			    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_REST, "SUCCESS"));
				return Response.status(200).entity(json).build();
			}else{
				beans.setCode("500");
				beans.setMassage("Json not valid");
				return Response.status(500).entity(googleJson.toJson(beans)).build();
			}
		}catch(Exception e){
			log.log(Level.INFO,"PresenterRest --> getPresenter--> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("PresenterRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	@POST
	@Path("/downloadFile")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFile(@Context HttpServletRequest request,@Context HttpServletResponse response,
						   @Context ServletContext context,String jsonString)
	{
		log.log(Level.INFO,"PresenterRest --> download File ");
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
			        String presenterCode = restForm.getPresenterCode();
			
					 PresenterMaterial  presentMat = new  PresenterMaintenance().getPresenterMaterial(Integer.parseInt(presenterCode));
					   if(presentMat!=null && (presentMat.getMaterialName() !=null && presentMat.getMaterialName().length()>0)){
						    response.setContentLength((int)presentMat.getMaterial().length);
						   // response.setHeader("Content-Transfer-Encoding", "binary");
						    response.setHeader("Content-Disposition","attachment; filename="+presentMat.getMaterialName());
						    response.getOutputStream().write(presentMat.getMaterial(), 0, presentMat.getMaterial().length);
						    response.getOutputStream().flush();
						    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_PRESENTER, AuditTrail.FUNCTION_REST, "SUCCESS"));
				    }else{	log.log(Level.INFO,"File Not found to download ");
				    
						beans.setCode("500");
						beans.setMassage("Download Error");
						auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_PRESENTER, AuditTrail.FUNCTION_REST, "FAILED"));
						return Response.status(500).entity(new Gson().toJson(beans)).build(); 
				    
				    }
			 }else{
				 beans.setCode("500");
					beans.setMassage("Json not valid");
					return Response.status(500).entity(googleJson.toJson(beans)).build();
			 }
			   return Response.status(200).build();
			
		}catch(Exception e){
			log.log(Level.INFO,"PresenterRest --> download File --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("PresenterRest",Level.SEVERE+"",errors.toString());
			
			beans.setCode("500");
			beans.setMassage("Download Error");
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_PRESENTER, AuditTrail.FUNCTION_REST, "FAILED"));
			return Response.status(500).entity(new Gson().toJson(beans)).build();
			
		}
		
	}
}
