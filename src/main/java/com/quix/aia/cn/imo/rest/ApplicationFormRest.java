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
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.applicationForm.ApplicationForm;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.interview.InterviewCandidateMaterial;
import com.quix.aia.cn.imo.data.interview.InterviewMaterial;
import com.quix.aia.cn.imo.mapper.AddressBookMaintenance;
import com.quix.aia.cn.imo.mapper.ApplicationFormPDFMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;



@Path("/ApplicationForm")
public class ApplicationFormRest {
	static Logger log = Logger.getLogger(ApplicationFormRest.class.getName());
	
	@GET
	@Path("/getApplicationForm")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getApplicationForm(@Context HttpServletRequest request,@Context HttpServletResponse response,
			   @Context ServletContext context){
		log.log(Level.INFO,"ApplicationFormRest --> getApplicationForm ");	
		MsgBeans beans = new MsgBeans();
		String candidateCode = request.getParameter("candidateCode");
		String interviewCode=request.getParameter("interviewCode");
		
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
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
