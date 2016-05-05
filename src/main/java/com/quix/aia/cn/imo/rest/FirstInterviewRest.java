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
 * 06-August-2015       Maunish              File Added
 * 18-Nov-2015           Nibedita          Error stored in db
 ***************************************************************************** */

package com.quix.aia.cn.imo.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.Date;
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

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.quix.aia.cn.imo.data.addressbook.CandidateFirstInterview;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.common.RestForm;
import com.quix.aia.cn.imo.mapper.AddressBookMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.CandidateFirstInterviewMaintenance;
import com.quix.aia.cn.imo.mapper.CandidateNoteMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.utilities.LMSUtil;

/**
 * <p>
 * Logic to Save Training Detail through Rest service.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 *
 *
 * */
@Path("/firstInterview")
public class FirstInterviewRest {
	static Logger log = Logger.getLogger(FirstInterviewRest.class.getName());

	/**
	 * <p>
	 * This method inserts Candidate First Interview Detail record.
	 * </p>
	 */
	//[{"agentId":"172926","candidateCode":"38","interviewResult":"PASS","recruitmentPlan":"ABC","remarks":"description","passTime":"2015-06-06 08:00:00"}]
	@POST
	@Path("/pushFirstInterview")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response pushFirstInterviewDetails(@Context HttpServletRequest request,
	   		   @Context ServletContext context,
	   		   String jsonString) {
		log.log(Level.INFO, " First Interview --> push Details ");
	    log.log(Level.INFO," First Interview --> push Details  --> Data ...  ::::: "+jsonString);
	    boolean flag=LMSUtil.isJSONValid(jsonString);
		Gson googleJson = null;
        GsonBuilder builder = new GsonBuilder();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		CandidateFirstInterviewMaintenance candidateFirstInterviewMaint = new CandidateFirstInterviewMaintenance();
        CandidateFirstInterview candidateFirstInterview = null;
        boolean status=false;
        
		try {
			log.log(Level.INFO, "First Interview --> Saving Candidate First Interview Details ... ");
			if(flag==true){
				
			
			builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
	               @Override  
	               public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            	   		Date date = LMSUtil.convertDateToyyyymmddhhmmssDashed24Hr(json.getAsString());
            	   		if(null != date){
            	   			return date;
            	   		}else{
            	   			return  LMSUtil.convertDateToyyyy_mm_dd(json.getAsString());
            	   		}
	               }
	           });
	        
	        googleJson  = builder.create();
	        Type listType = new TypeToken<List<CandidateFirstInterview>>(){}.getType();
	        List<CandidateFirstInterview> jsonObjList = googleJson.fromJson(jsonString, listType);
	        candidateFirstInterview = jsonObjList.get(0);  
	        
	        candidateFirstInterviewMaint.createNewCandidateFirstInterview(candidateFirstInterview,request);
	        
	        String conditionFieldName[]={"addressCode"};
	        String conditionFieldValue[]={candidateFirstInterview.getCandidateCode()};
	        new AddressBookMaintenance().updateAddressBookStatus("3/9", conditionFieldName, conditionFieldValue);
	        new CandidateNoteMaintenance().insertSystemNotes(Integer.parseInt(candidateFirstInterview.getCandidateCode()), "First Interview", "First Interview Results : "+candidateFirstInterview.getInterviewResult());
			
			log.log(Level.INFO,"First Interview --> Candidate First Interview Details Saved successfully... ");
			status=true;
			}else{
				status=false;
				return Response.status(200).entity("[{\"status\":"+status+"}]").build();
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "First Interview --> Error in Saving Candidate First Interview Details.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("FirstInterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_FAIL,"FAILED"));
			
		} finally {
			builder = null;
			googleJson = null;
			auditTrailMaint = null;
			candidateFirstInterviewMaint = null;
			candidateFirstInterview = null;
			System.gc();
		}
		return Response.status(200).entity("[{\"status\":"+status+"}]").build();
		
	}
	
	/**
	 * <p>
	 * This method gets Candidate First Interview Results.
	 * </p>
	 */
	@POST
	@Path("/getFirstInterview")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getALEExamResults(@Context HttpServletRequest request,
	   		   @Context ServletContext context,String jsonString) {
		
		log.log(Level.INFO, "First Interview --> get ALE Exam Results.");
		boolean flag=LMSUtil.isJSONValid(jsonString);
		MsgBeans beans = new MsgBeans();
		
		Gson googleJson = null;
        GsonBuilder builder = new GsonBuilder();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		CandidateFirstInterviewMaintenance candidateFirstInterviewMaint = new CandidateFirstInterviewMaintenance();
        CandidateFirstInterview candidateFirstInterview = null;
		//String jsonString = "";
		
		try {
			
			  /*Type listType = new TypeToken<List<RestForm>>(){}.getType();
		        List<RestForm> jsonObjList = googleJson.fromJson(jsonString, listType);
		        RestForm restForm = jsonObjList.get(0);  */
			
			builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
	               @Override  
	               public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
         	   		Date date = LMSUtil.convertDateToyyyymmddhhmmssDashed24Hr(json.getAsString());
         	   		if(null != date){
         	   			return date;
         	   		}else{
         	   			return  LMSUtil.convertDateToyyyy_mm_dd(json.getAsString());
         	   		}
	               }
	           });
	       
	        googleJson  = builder.create();
	        
			RestForm restForm= googleJson.fromJson(jsonString, RestForm.class);
			
		        String agentId = restForm.getAgentId();
		        String candidateCode = restForm.getCandidateCode();
		        
			 if(flag==true){
			log.log(Level.INFO, "First Interview --> fetching Information ... ");
			
			candidateFirstInterview = candidateFirstInterviewMaint.getCandidateFirstinterview(agentId, candidateCode,restForm);
			
			
			if(null == candidateFirstInterview){
				candidateFirstInterview = new CandidateFirstInterview();
			}
			
			candidateFirstInterview.setAgentId(null);
			candidateFirstInterview.setCandidateCode(null);
			candidateFirstInterview.setFirstInterviewCode(null);
		if(null!=candidateFirstInterview.getInterviewResult()){
			
			
			if(candidateFirstInterview.getInterviewResult().equalsIgnoreCase("Pass")){
				candidateFirstInterview.setInterviewResult("Pass");
			}else if(candidateFirstInterview.getInterviewResult().equalsIgnoreCase("Fail")){
				candidateFirstInterview.setInterviewResult("Fail");
			}else{
				candidateFirstInterview.setInterviewResult("");
			}
			
		}else{
			candidateFirstInterview.setInterviewResult("");
		}	
			//candidateFirstInterview.setRecruitmentPlan(null);
			//candidateFirstInterview.setRemarks(null);
			
			AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
			String[] conditionFieldName={"addressCode"};
			String[] conditionFieldValue={candidateCode};
			addressBookMaintenance.updateAddressBookStatus("7/9", conditionFieldName, conditionFieldValue);
			new CandidateNoteMaintenance().insertSystemNotes(Integer.parseInt(candidateCode), "ALE Exam", "ALE Exam Results : "+candidateFirstInterview.getInterviewResult());
			
			// Convert the object to a JSON string
			log.log(Level.INFO,"First Interview --> Information fetched successfully... ");
			
			return Response.status(200).entity(builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(candidateFirstInterview)).build();
			 }else{
				 beans.setCode("500");
					beans.setMassage("Json not valid");
					return Response.status(500).entity(googleJson.toJson(beans)).build();
			 }
		} catch (Exception e) {
			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			
			log.log(Level.SEVERE, "Training --> Error in fetching Record.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("FirstInterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_FAIL,"FAILED"));
			
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		} finally {
			auditTrailMaint = null;
			jsonString = null;
			beans = null;
			System.gc();
		}
	}
}