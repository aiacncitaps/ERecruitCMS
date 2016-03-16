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
* 18-Nov-2015           Nibedita          Error stored in db
****************************************** *********************************** */
package com.quix.aia.cn.imo.rest;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
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
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.common.RestForm;
import com.quix.aia.cn.imo.data.interview.InterviewCandidateMaterial;
import com.quix.aia.cn.imo.mapper.AddressBookMaintenance;
import com.quix.aia.cn.imo.mapper.ApplicationFormPDFMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.utilities.LMSUtil;



@Path("/ApplicationForm")
public class ApplicationFormRest {
	static Logger log = Logger.getLogger(ApplicationFormRest.class.getName());
	
	@POST
	@Path("/getApplicationForm")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response getApplicationForm(@Context HttpServletRequest request,@Context HttpServletResponse response,
			   @Context ServletContext context,String jsonString){
		log.log(Level.INFO,"ApplicationFormRest --> getApplicationForm ");	
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
	        String candidateCode = restForm.getCandidateCode();
			String interviewCode=restForm.getInterviewCode();
			
			
			AddressBookMaintenance addressMain=new AddressBookMaintenance();
			AddressBook addressbook=new AddressBook();
			addressbook.setAddressCode(Integer.parseInt(candidateCode));
			addressbook=addressMain.getAddressBook(addressbook);
			ApplicationFormPDFMaintenance applicatonPdfMain=new ApplicationFormPDFMaintenance();
			InterviewCandidateMaterial material=new InterviewCandidateMaterial();
			
			material.setInterviewCode(Integer.parseInt(interviewCode));
			material=applicatonPdfMain.pdf(request,addressbook);
			if(material!=null){
				applicatonPdfMain.insertPdf(request,material);
				auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_APPLICATIONFORM, AuditTrail.FUNCTION_SUCCESS, "INSERT_PDF_SUCCESS"));
				response.setContentLength((int)material.getFormContent().length);
				 response.setHeader("Content-Disposition","attachment; filename="+material.getMaterialFileName());
				 response.getOutputStream().write(material.getFormContent(), 0,material.getFormContent().length);
				 response.getOutputStream().flush();
			}
			}else{
				beans.setCode("500");
				beans.setMassage("Json not valid");
				return Response.status(500).entity(googleJson.toJson(beans)).build();
			}
			
		}catch(Exception e){
			log.log(Level.INFO,"ApplicationFormRest --> getApplicationForm  --> Exception..... "+e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("ApplicationFormRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_APPLICATIONFORM, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
		return Response.status(200).build();

	}
	
}
