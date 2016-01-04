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
* 02-Sept-2015          Nibedita          Download file service added
* 18-Nov-2015           Nibedita          Error stored in db
***************************************************************************** */
package com.quix.aia.cn.imo.rest;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.egreeting.E_GreetingMaterial;
import com.quix.aia.cn.imo.mapper.AamDataMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.EgreetingMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;


/**
 * <p>Logic to get Egreeting for Rest service.</p>
 * 
 * @author Jay
 * @version 1.0
 *
 */

@Path("/egreeting")
public class EGreetingRest {
	static Logger log = Logger.getLogger(AnnouncementRest.class.getName());
	
	@GET
	@Path("/getAllegreeting")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEgreeting(@Context HttpServletRequest request,
						   @Context ServletContext context)
	{
		log.log(Level.INFO,"EGreetingRest --> getEgreeting ");
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		String coBranch = request.getParameter("co");
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			
			ArrayList list = new ArrayList();
			AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId, coBranch); 
			EgreetingMaintenance objEgreetingMain=new EgreetingMaintenance();
			list=objEgreetingMain.getEgreetingRest(aamData);
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
			
		}catch(Exception e){
			log.log(Level.INFO,"EGreetingRest --> getEgreeting --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EGreetingRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
		
	}
	@GET
	@Path("/downloadFile")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFile(@Context HttpServletRequest request,@Context HttpServletResponse response,
						   @Context ServletContext context)
	{
		log.log(Level.INFO,"EGreetingRest --> download File ");
		MsgBeans beans = new MsgBeans();
		String egreetingCode = request.getParameter("egreetingCode");
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
		
			E_GreetingMaterial  egreetMat = new  EgreetingMaintenance().getEgreetingMaterial(Integer.parseInt(egreetingCode));
			  if(egreetMat!=null && (egreetMat.getMaterialName() !=null && egreetMat.getMaterialName().length()>0)){
				    response.setContentLength((int)egreetMat.getMaterial().length);
				 //   response.setHeader("Content-Transfer-Encoding", "binary");
				    response.setHeader("Content-Disposition","attachment; filename="+egreetMat.getMaterialName());
				    response.getOutputStream().write(egreetMat.getMaterial(), 0, egreetMat.getMaterial().length);
				    response.getOutputStream().flush();
				    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_E_GREETING, AuditTrail.FUNCTION_REST, "SUCCESS"));
		    }else{	
		    	log.log(Level.INFO,"File Not found to download ");
		    
				beans.setCode("500");
				beans.setMassage("Download Error");
				auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_E_GREETING, AuditTrail.FUNCTION_REST, "FAILED"));
				return Response.status(500).entity(new Gson().toJson(beans)).build(); 
		    
		    }
			  return Response.status(200).build();
			
		}catch(Exception e){
			log.log(Level.INFO,"EGreetingRest --> download File --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EGreetingRest",Level.SEVERE+"",errors.toString());
			
			beans.setCode("500");
			beans.setMassage("Download Error");
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_E_GREETING, AuditTrail.FUNCTION_REST, "FAILED"));
			return Response.status(500).entity(new Gson().toJson(beans)).build();
			
		}
		
	}
}
