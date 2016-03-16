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
* 18-Nov-2015           Nibedita          Error stored in db
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
import com.quix.aia.cn.imo.mapper.AamDataMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.HolidayMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.utilities.LMSUtil;


/**
 * <p>Logic to get Holiday for Rest service.</p>
 * 
 * @author Jay
 * @version 1.0
 *
 */

@Path("/holiday")
public class HolidayRest {
	static Logger log = Logger.getLogger(HolidayRest.class.getName());
	@POST
	@Path("/getAllHoliday")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response getHoliday(@Context HttpServletRequest request,
						   @Context ServletContext context,String jsonString)
	{
		log.log(Level.INFO,"HolidayRest --> getHoliday ");
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
			HolidayMaintenance objHolidayMaintenance = new HolidayMaintenance();
			list = objHolidayMaintenance.getAllHolidayRest(aamData,agentId);
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_HOLIDAY, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
			 }else{
				 beans.setCode("500");
					beans.setMassage("Json not valid");
					return Response.status(500).entity(googleJson.toJson(beans)).build();
			 }
		}catch(Exception e){
			log.log(Level.INFO,"HolidayRest --> getHoliday --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("HolidayRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_HOLIDAY, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
}
