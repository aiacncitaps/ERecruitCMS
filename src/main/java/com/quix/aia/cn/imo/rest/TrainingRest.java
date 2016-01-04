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
 * 24-July-2015       Maunish              File Added
 * 28-July-2015       Maunish              File Updated
 * 18-Nov-2015            Nibedita          Error stored in db
 ***************************************************************************** */

package com.quix.aia.cn.imo.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.addressbook.CandidateTrainingDetail;
import com.quix.aia.cn.imo.data.addressbook.CandidateTrainingResult;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.mapper.AddressBookMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.CandidateNoteMaintenance;
import com.quix.aia.cn.imo.mapper.CandidateTrainingDetailMaintenance;
import com.quix.aia.cn.imo.mapper.CandidateTrainingResultMaintenance;
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
@Path("/training")
public class TrainingRest {
	static Logger log = Logger.getLogger(TrainingRest.class.getName());

	/**
	 * <p>
	 * This method inserts Candidate Training Detail record.
	 * </p>
	 */
	//[{"courseCode":"172926","courseName":"38","curriculamCode":"Participation","courseType":"description","startDate":"2015-06-06 08:00:00"}]
	@POST
	@Path("/pushDetails")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response pushTrainingDetails(@Context HttpServletRequest request,
	   		   @Context ServletContext context,
	   		   String jsonString) {
		log.log(Level.INFO, " Training --> push Details ");
	    log.log(Level.INFO," Training --> push Details  --> Data ...  ::::: "+jsonString);
		
		Gson googleJson = null;
        GsonBuilder builder = new GsonBuilder();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		CandidateTrainingDetailMaintenance candidateTrainingDetailMaint = new CandidateTrainingDetailMaintenance();
        CandidateTrainingDetail candidateTrainingDetail = null;
        int isSuccessful=0;
        
		try {
			log.log(Level.INFO, "Training --> Saving Candidate Training Details ... ");
			
			builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
	               @Override  
	               public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            	   		Date date = LMSUtil.convertDateToyyyymmddhhmmssDashed(json.getAsString());
            	   		if(null != date){
            	   			return date;
            	   		}else{
            	   			return  LMSUtil.convertDateToyyyy_mm_dd(json.getAsString());
            	   		}
	               }
	           });
	        
	        googleJson  = builder.create();
	        Type listType = new TypeToken<List<CandidateTrainingDetail>>(){}.getType();
	        List<CandidateTrainingDetail> jsonObjList = googleJson.fromJson(jsonString, listType);
	        candidateTrainingDetail = jsonObjList.get(0);  
	        candidateTrainingDetail.setCreationDate(new Date());
	        
	        candidateTrainingDetailMaint.createNewCandidateTrainingDetail(candidateTrainingDetail,request);
			
			log.log(Level.INFO,"Training --> Candidate Training Details Saved successfully... ");
			isSuccessful=1;
		} catch (Exception e) {
			log.log(Level.SEVERE, "Training --> Error in Saving Candidate Training Details.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("TrainingRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_FAIL,"FAILED"));
			
		} finally {
			builder = null;
			googleJson = null;
			auditTrailMaint = null;
			candidateTrainingDetailMaint = null;
			candidateTrainingDetail = null;
			System.gc();
		}
		return Response.status(200).entity("[{\"isSuccessful\":"+isSuccessful+"}]").build();
		
	}
	
	/**
	 * <p>
	 * This method inserts Candidate Training Result record.
	 * </p>
	 */
	//"recruiterAgentCode":"014222",
	//[{"perAgentName":"ABC","perAgentId":"38","branchCode":"014222","courseType":"description","passTime":"2015-06-06 08:00:00"}]
	@POST
	@Path("/pushResults")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response pushTrainingResults(@Context HttpServletRequest request,
	   		   @Context ServletContext context,
	   		   String jsonString) {
		log.log(Level.INFO, " Training --> push Results ");
	    log.log(Level.INFO," Training --> push Results  --> Data ...  ::::: "+jsonString);
		
		Gson googleJson = null;
        GsonBuilder builder = new GsonBuilder();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		CandidateTrainingResultMaintenance candidateTrainingResultMaint = new CandidateTrainingResultMaintenance();
        CandidateTrainingResult candidateTrainingResult = null;
        int isSuccessful=0;
        
		try {
			log.log(Level.INFO, "Training --> Saving Candidate Training Results ... ");
			
			builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
	               @Override  
	               public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            	   		Date date = LMSUtil.convertDateToyyyymmddhhmmssDashed(json.getAsString());
            	   		if(null != date){
            	   			return date;
            	   		}else{
            	   			return  LMSUtil.convertDateToyyyy_mm_dd(json.getAsString());
            	   		}
	               }
	           });
	        
	        googleJson  = builder.create();
	        Type listType = new TypeToken<List<CandidateTrainingResult>>(){}.getType();
	        List<CandidateTrainingResult> jsonObjList = googleJson.fromJson(jsonString, listType);
	        candidateTrainingResult = jsonObjList.get(0);
	        candidateTrainingResult.setCreationDate(new Date());  
	        
	        candidateTrainingResultMaint.createNewCandidateTrainingResult(candidateTrainingResult,request);
			
			log.log(Level.INFO,"Training --> Candidate Training Results Saved successfully... ");
			isSuccessful=1;
		} catch (Exception e) {
			log.log(Level.SEVERE, "Training --> Error in Saving Candidate Training Results.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("TrainingRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_FAIL,"FAILED"));
			
		} finally {
			builder = null;
			googleJson = null;
			auditTrailMaint = null;
			candidateTrainingResultMaint = null;
			candidateTrainingResult = null;
			System.gc();
		}
		return Response.status(200).entity("[{\"isSuccessful\":"+isSuccessful+"}]").build();
	}
	
	/**
	 * <p>
	 * This method gets Candidate Training Results.
	 * </p>
	 */
	@GET
	@Path("/getALEExamResults")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getALEExamResults(@Context HttpServletRequest request,
	   		   @Context ServletContext context) {
		
		log.log(Level.INFO, "Training --> get ALE Exam Results.");
		
		AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		AddressBook addressBook = new AddressBook();
		String jsonString = "";
		try {
			
			String candidateCode = request.getParameter("candidateCode");
			String agentId = request.getParameter("agentId");
			
			log.log(Level.INFO, "Training --> fetching Information ... ");
			
			String[] updateFieldName = {"recruitmentProgressStatus","modificationDate","modifiedBy"};
			Object[] updateFieldValue = {"7/9",new Date(),agentId};
			String[] conditionFieldName={"iosAddressCode","agentId"};
			String[] conditionFieldValue={candidateCode,agentId};
			addressBookMaintenance.updateAddressBookSelectedField(updateFieldName, updateFieldValue, conditionFieldName, conditionFieldValue);
			
			new CandidateNoteMaintenance().insertSystemNotes(Integer.parseInt(candidateCode), "ALE Exam", "ALE Exam Results");
			
			// Convert the object to a JSON string
			log.log(Level.INFO,"Training --> Information fetched successfully... ");
			jsonString="[{\"Date\":\""+LMSUtil.convertDateToyyyymmddhhmmssDashedString(new Date())+"\",\"Status\":\"Passed\"}]";
			

			return Response.status(200).entity(jsonString).build();
		} catch (Exception e) {
			log.log(Level.SEVERE, "Training --> Error in fetching Record.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("TrainingRest",Level.SEVERE+"",errors.toString());
			
			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_FAIL,"FAILED"));

			
			
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		} finally {
			auditTrailMaint = null;
			jsonString = null;
			beans = null;
			System.gc();
		}
	}
	
	/**
	 * <p>
	 * This method gets Candidate Training Result Results.
	 * </p>
	 */
	@GET
	@Path("/getABCTrainingResult")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getABCTrainingResult(@Context HttpServletRequest request,
	   		   @Context ServletContext context) {
		
		log.log(Level.INFO, "Training Result --> get ABC Training Results.");
		
		MsgBeans beans = new MsgBeans();
		Gson googleJson = null;
        GsonBuilder builder = new GsonBuilder();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		CandidateTrainingResultMaintenance candidateTrainingResultMaint = new CandidateTrainingResultMaintenance();
        CandidateTrainingResult candidateTrainingResult = null;
		String jsonString = "";
		
		try {
			
			String nric = request.getParameter("perAgentId");
			String recruiterAgentCode = request.getParameter("recruiterAgentCode");
			
			log.log(Level.INFO, "Training Result --> fetching Information ... ");
			
			candidateTrainingResult = candidateTrainingResultMaint.getCandidateTrainingResult(recruiterAgentCode, nric);

			if(null != candidateTrainingResult){
			
				candidateTrainingResult.setBranchCode(null);
				candidateTrainingResult.setCourseType(null);
				candidateTrainingResult.setCreationDate(null);
				candidateTrainingResult.setPerAgentId(null);
				candidateTrainingResult.setPerAgentName(null);
				candidateTrainingResult.setRecruiterAgentCode(null);
				candidateTrainingResult.setResultCode(null);
				candidateTrainingResult.setTrainingResult("PASS");
			}else{
				candidateTrainingResult = new CandidateTrainingResult();
			}
			
			AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
			
			String[] fetchFields={"addressCode","nric"};
			String[] conditionFieldName={"nric", "agentId"};
			String[] conditionFieldValue={nric ,recruiterAgentCode};
			List<Object []> list = addressBookMaintenance.getAddressBookSelectedField(fetchFields, conditionFieldName, conditionFieldValue);
			Object[] obj = null;
			
			if(null != list && !list.isEmpty()){
				obj = (Object[]) list.get(0);
				
				Integer candidateCode = (Integer) obj[0];
				String[] conditionFieldName1={"addressCode"};
				String[] conditionFieldValue1={""+candidateCode};
				addressBookMaintenance.updateAddressBookStatus("8/9", conditionFieldName1, conditionFieldValue1);
				new CandidateNoteMaintenance().insertSystemNotes(candidateCode , "ABC Training", "ABC Training Results : "+candidateTrainingResult.getTrainingResult());
			}
			
			// Convert the object to a JSON string
			log.log(Level.INFO,"Training Result --> Information fetched successfully... ");
			
			return Response.status(200).entity(builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(candidateTrainingResult)).build();
		} catch (Exception e) {
			log.log(Level.SEVERE, "Training --> Error in fetching Record.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("TrainingRest",Level.SEVERE+"",errors.toString());
			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_FAIL,"FAILED"));

		
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		} finally {
			auditTrailMaint = null;
			jsonString = null;
			beans = null;
			System.gc();
		}
	}
	
	/**
	 * <p>
	 * This method gets Candidate Training Details.
	 * </p>
	 */
	@GET
	@Path("/getAllTrainingDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTrainingDetails(@Context HttpServletRequest request,
	   		   @Context ServletContext context) {
		
		log.log(Level.INFO, "Training Rest --> get All Training Details.");
		
		MsgBeans beans = new MsgBeans();
		Gson googleJson = null;
		List list = new ArrayList();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		CandidateTrainingDetailMaintenance candidateTrainingDetailMaint = new CandidateTrainingDetailMaintenance();
		
		try {
			
			log.log(Level.INFO, "Training Rest --> fetching Information ... ");
			
			list = candidateTrainingDetailMaint.getAllCandidateTrainingDetail();
			
			googleJson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = googleJson.toJson(list);
			
			// Convert the object to a JSON string
			log.log(Level.INFO,"Training Rest --> Information fetched successfully... ");
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
			
		} catch (Exception e) {
			log.log(Level.SEVERE, "TrainingRest --> Error in fetching Record.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("TrainingRest",Level.SEVERE+"",errors.toString());
			
			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_TRAINING, AuditTrail.FUNCTION_FAIL,"FAILED"));

		
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		} finally {
			auditTrailMaint = null;
			beans = null;
			System.gc();
		}
	}
}