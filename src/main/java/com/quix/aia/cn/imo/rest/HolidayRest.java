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
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.mapper.AamDataMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.HolidayMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;


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
	@GET
	@Path("/getAllHoliday")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getHoliday(@Context HttpServletRequest request,
						   @Context ServletContext context)
	{
		log.log(Level.INFO,"HolidayRest --> getHoliday ");
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		String coBranch = request.getParameter("co");
		ArrayList list = new ArrayList();
		 AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId, coBranch); 
			HolidayMaintenance objHolidayMaintenance = new HolidayMaintenance();
			list = objHolidayMaintenance.getAllHolidayRest(aamData,agentId);
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_HOLIDAY, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
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
