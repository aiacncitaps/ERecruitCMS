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
 * 19-May-2015       Hemraj             File Added
 * 22-June-2015      Maunish           	Modified                    
 * 06-August-2015    Maunish            Modified 
 * 18-Nov-2015         Nibedita          Error stored in db
 ***************************************************************************** */

package com.quix.aia.cn.imo.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
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
import com.quix.aia.cn.imo.data.addressbook.AddressBook;
import com.quix.aia.cn.imo.data.addressbook.CandidateNote;
import com.quix.aia.cn.imo.data.addressbook.ContractDetail;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.mapper.AddressBookMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.CandidateNoteMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.utilities.LMSUtil;

/**
 * <p>
 * Logic to Save Address Book through Rest service.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 *
 *
 * */
@Path("/addressBook")
public class AddressBookRest {
	static Logger log = Logger.getLogger(AddressBookRest.class.getName());

	/**
	 * <p>
	 * Address Book Synchronization rest service post method which gets Json
	 * string, which contains list of Address Book records. This method performs
	 * save or update operations. It returns Json string with local address code
	 * with IOS address code.
	 * </p>
	 * 
	 * @param jsonAddressBookListString
	 * 
	 */
	@POST
	@Path("/sync")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response syncAddressBook(String jsonAddressBookListString) {
		log.log(Level.INFO, "Address Book --> Sync Record ");
		log.log(Level.INFO,"Address Book --> Sync Record --> Data for Sync...  ::::: "+jsonAddressBookListString);
		
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		List<AddressBook> jsonObjList = new ArrayList();
		GsonBuilder builder = new GsonBuilder();
		AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
		Gson googleJson = null;
		Type listType = null;
		String returnJsonString = "";
		try {
			returnJsonString = "[";

			builder.registerTypeAdapter(Date.class,
					new JsonDeserializer<Date>() {
						@Override
						public Date deserialize(JsonElement json, Type typeOfT,
								JsonDeserializationContext context)
								throws JsonParseException {
							Date date = LMSUtil.convertDateToyyyymmddhhmmssDashed(json.getAsString());
							if (null != date) {
								return date;
							} else {
								return LMSUtil.convertDateToyyyy_mm_dd(json.getAsString());
							}
						}
					});
			
			builder.registerTypeHierarchyAdapter(byte[].class,
	                new JsonDeserializer<byte[]>(){
				public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		            return Base64.decodeBase64(json.getAsString());
		        }
			});

			googleJson = builder.create();
			listType = new TypeToken<List<AddressBook>>() {}.getType();
			jsonObjList = googleJson.fromJson(jsonAddressBookListString,listType);

			// maintain in single transaction
			returnJsonString += addressBookMaintenance.insertOrUpdateRestBatch(jsonObjList);
			returnJsonString += "]";

			log.log(Level.INFO, "Address Book --> saved successfully ");
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_ADDRESS_BOOK,AuditTrail.FUNCTION_SUCCESS, "SUCCESS"));
			return Response.status(200).entity(returnJsonString).build();

		} catch (Exception e) {

			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_ADDRESS_BOOK, AuditTrail.FUNCTION_FAIL,"FAILED"));

			log.log(Level.SEVERE, "Address Book --> Error in Save Record.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AddressBookRest",Level.SEVERE+"",errors.toString());
			
			return Response.status(200).entity(googleJson.toJson(beans)).build();
		} finally {
			jsonObjList.clear();
			auditTrailMaint = null;
			addressBookMaintenance = null;
			builder = null;
			googleJson = null;
			listType = null;
			returnJsonString = null;
			beans = null;
			System.gc();
		}
	}

	/**
	 * <p>
	 * This method retrieves List of Address Book for particular Agent.
	 * </p>
	 * 
	 * @param agentId
	 * 
	 */
	@GET
	@Path("/getAgentAddressBook")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAgentAddressBook(@Context HttpServletRequest request,
			   @Context ServletContext context) {
		log.log(Level.INFO, "Address Book --> getAgentAddressBook ");
		
		GsonBuilder builder = new GsonBuilder();
		AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
		Gson googleJson = null;
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		List<AddressBook> addressBookList = new ArrayList();
		String jsonString = "";
		try {
			
			builder.registerTypeHierarchyAdapter(byte[].class,
	                new JsonSerializer<byte[]>(){
				public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
		            return new JsonPrimitive(Base64.encodeBase64String(src));
		        }
			});
			
			googleJson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

			log.log(Level.INFO, "Address Book --> fetching Information ... ");
			addressBookList = addressBookMaintenance.getAgentAddressBook(request,context);
			// Convert the object to a JSON string
			log.log(Level.INFO,"Address Book --> Information fetched successfully... ");
			jsonString = googleJson.toJson(addressBookList);

			return Response.status(200).entity(jsonString).build();
		} catch (Exception e) {

			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_ADDRESS_BOOK, AuditTrail.FUNCTION_FAIL,"FAILED"));

			log.log(Level.SEVERE, "Address Book --> Error in fetching Record.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AddressBookRest",Level.SEVERE+"",errors.toString());
			
			return Response.status(200).entity(googleJson.toJson(beans)).build();
		} finally {
			addressBookList.clear();
			auditTrailMaint = null;
			addressBookMaintenance = null;
			jsonString = null;
			beans = null;
			builder = null;
			googleJson = null;
			System.gc();
		}
	}
	
	/**
	 * <p>
	 * This method inserts Candidate Note record.
	 * </p>
	 */
	//[{"addressCode":172926,"iosAddressCode":"38","activityType":"Participation","activityDate":"2015-06-06 08:00:00","description":"description","activityStatus":true}]
	@POST
	@Path("/syncNotes")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response syncNotes(@Context HttpServletRequest request,
	   		   @Context ServletContext context,
	   		   String jsonString) {
		log.log(Level.INFO, "Address Book --> syncNotes ");
		log.log(Level.INFO,"Address Book --> syncNotes --> Data for Sync Notes...  ::::: "+jsonString);
//		String responseString = "[{\"status\":";
		
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		Gson googleJson = null;
        GsonBuilder builder = new GsonBuilder();
//        CandidateNoteId candidateNoteId = new CandidateNoteId();
        CandidateNote candidateNote = null;
        
		CandidateNoteMaintenance candidateNoteMaint = new CandidateNoteMaintenance();
		try {
			log.log(Level.INFO, "Address Book --> Saving Candidate Notes ... ");
			
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
	        Type listType = new TypeToken<List<CandidateNote>>(){}.getType();
	        List<CandidateNote> jsonObjList = googleJson.fromJson(jsonString, listType);
	        
	        List list = candidateNoteMaint.syncNotes(jsonObjList, request);
	        
			log.log(Level.INFO,"Address Book --> Candidate Notes Saved successfully... ");
//		    responseString +=true;
			
			return Response.status(200).entity(googleJson.toJson(list)).build();
		} catch (Exception e) {
			e.printStackTrace();
			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_ADDRESS_BOOK, AuditTrail.FUNCTION_FAIL,"FAILED"));

			log.log(Level.SEVERE, "Address Book --> Error in Saving Candidate Notes.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AddressBookRest",Level.SEVERE+"",errors.toString());
			
//		    responseString +=false; 
			return Response.status(500).entity("[{\"status\":\"false\"}]").build();
		} finally {
			builder = null;
			googleJson = null;
//			candidateNoteId=null;
			auditTrailMaint = null;
			candidateNoteMaint = null;
			candidateNote = null;
			beans = null;
			System.gc();
		}
		
//		responseString+="}]";
	
	}
	
	/**
	 * <p>
	 * This method Push Contract Details
	 * </p>
	 */
	//[{"branchCode":"12345","candidateAgentCode":"1255210","candidateNric":"nric2","recruiterAgentCode":"125521","recruitmentType":"recruitmentType","contractDate":"2015-06-06 08:00:00"}]
	@POST
	@Path("/pushContractedDetails")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON})
	public Response pushContractedAamData(@Context HttpServletRequest request,
	   		   @Context ServletContext context,String jsonString) {
		
		log.log(Level.INFO, "Address Book --> pushContractedDetails ... ");
		log.log(Level.INFO,"Address Book --> pushContractedDetails --> Data...  ::::: "+jsonString);
		ContractDetail contractDetail;
		AddressBook addressBook;
		Gson googleJson = null;
		String responseString = "[{\"status\":";
		MsgBeans beans = new MsgBeans();
		AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			GsonBuilder builder = new GsonBuilder();
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
	        Type listType = new TypeToken<List<ContractDetail>>(){}.getType();
	        List<ContractDetail> jsonObjList = googleJson.fromJson(jsonString, listType);
	        if(!jsonObjList.isEmpty()){
	        	contractDetail = jsonObjList.get(0);
	        	addressBook = new AddressBook();
				
				addressBook = addressBookMaintenance.getAddressBookNric(contractDetail.getCandidateNric(), contractDetail.getRecruiterAgentCode());
			        	
			    addressBook.setCandidateAgentCode(contractDetail.getCandidateAgentCode());
				addressBook.setBranchCode(contractDetail.getBranchCode());
				addressBook.setContractDate(contractDetail.getContractDate());
				addressBook.setRecruitmentType(contractDetail.getRecruitmentType());
				addressBook.setNric(contractDetail.getCandidateNric());
				addressBook.setAgentId(contractDetail.getRecruiterAgentCode());
				addressBook.setRecruitmentProgressStatus("9/9");
				
				addressBookMaintenance.updateAddressBook(addressBook);
	        }
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
		    responseString +=true; 
		}
		catch(Exception e){
			log.log(Level.INFO,"EventRest --> candidateRegister --> Exception..... ");
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
		    responseString +=false; 
		    
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AddressBookRest",Level.SEVERE+"",errors.toString());
		}
		
		responseString+="}]";
		return Response.status(200).entity(responseString).build();
	}
	
	@GET
	@Path("/getContractDetail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContractDetails(@Context HttpServletRequest request) {
		log.log(Level.INFO, "Address Book --> getContractDetail ");

		ContractDetail contractDetail = new ContractDetail();
		AddressBook addressBook;
		Object[] obj;
		GsonBuilder builder = new GsonBuilder();
		AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
		
		Gson googleJson = null;
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		String jsonString = "";
		try {
			
			googleJson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

			log.log(Level.INFO, "Address Book --> fetching Information ... ");
			addressBook = new AddressBook();
			String candidateAgentCode = request.getParameter("candidateAgentCode");
			candidateAgentCode = null != candidateAgentCode ? candidateAgentCode:"";
			
			
			String[] fetchFields={"branchCode","candidateAgentCode","recruitmentType","contractDate","addressCode"};
			String[] conditionFieldName={"addressCode"};
			String[] conditionFieldValue={candidateAgentCode};
			List<Object []> list = addressBookMaintenance.getAddressBookSelectedField(fetchFields, conditionFieldName, conditionFieldValue);
			
			if(null != list && !list.isEmpty()){
				obj = (Object[]) list.get(0);
				
				contractDetail.setBranchCode((String)obj[0]);
				contractDetail.setCandidateAgentCode((String)obj[1]);
				contractDetail.setRecruitmentType((String)obj[2]);
				contractDetail.setContractDate((Date)obj[3]);
				
				String conditionFieldName1[]={"addressCode"};
		        String conditionFieldValue1[]={""+(Integer)obj[4]};
		        new AddressBookMaintenance().updateAddressBookStatus("9/9", conditionFieldName1, conditionFieldValue1);
			}
			// Convert the object to a JSON string
			log.log(Level.INFO,"Address Book --> Information fetched successfully... ");
			
			jsonString = googleJson.toJson(contractDetail);

			return Response.status(200).entity(jsonString).build();
		} catch (Exception e) {

			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_ADDRESS_BOOK, AuditTrail.FUNCTION_FAIL,"FAILED"));

			log.log(Level.SEVERE, "Address Book --> Error in fetching Record.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			LogsMaintenance logsMain=new LogsMaintenance();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookRest",Level.SEVERE+"",errors.toString());
			
			return Response.status(200).entity(googleJson.toJson(beans)).build();
		} finally {
			auditTrailMaint = null;
			addressBookMaintenance = null;
			jsonString = null;
			beans = null;
			builder = null;
			googleJson = null;
			System.gc();
		}
	}
	
	@GET
	@Path("/getCCTestResults")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCCTestResults(@Context HttpServletRequest request) {
		log.log(Level.INFO, "Address Book --> getCCTestResults ");
		LogsMaintenance logsMain=new LogsMaintenance();
		ContractDetail contractDetail = new ContractDetail();
		AddressBook addressBook;
		GsonBuilder builder = new GsonBuilder();
		AddressBookMaintenance addressBookMaintenance = new AddressBookMaintenance();
		
		Gson googleJson = null;
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		List<AddressBook> addressBookList = new ArrayList();
		String jsonString = "";
		try {
			
			builder.registerTypeHierarchyAdapter(byte[].class,
	                new JsonSerializer<byte[]>(){
				public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
		            return new JsonPrimitive(Base64.encodeBase64String(src));
		        }
			});
			
			googleJson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

			log.log(Level.INFO, "Address Book --> fetching Information ... ");
			addressBook = new AddressBook();
			String candidateCode = request.getParameter("candidateCode");
			candidateCode = null != candidateCode ? candidateCode:"";
			String agentId = request.getParameter("agentId");
			agentId = null != agentId ? agentId:"";
			
			//addressBook = addressBookMaintenance.getAddressBook(candidateCode, agentId);
			String[] fetchFields={"ccTestResult","ccTestResultDate"};
			String[] conditionFieldName={"addressCode","agentId"};
			String[] conditionFieldValue={candidateCode,agentId};
			List<Object []> list = addressBookMaintenance.getAddressBookSelectedField(fetchFields, conditionFieldName, conditionFieldValue);
			if(!list.isEmpty()){
				Object[] obj = list.get(0);
				addressBook.setCcTestResult(""+obj[0]);
				addressBook.setCcTestResultDate((Date)obj[1]);
			}
			
			addressBookMaintenance.updateAddressBookStatus("5/9", conditionFieldName, conditionFieldValue);
			new CandidateNoteMaintenance().insertSystemNotes(Integer.parseInt(candidateCode), "CC Test", "CC Test Results : "+addressBook.getCcTestResult());
			
			String date="";
			if(addressBook.getCcTestResultDate()!=null)
					LMSUtil.convertDateToyyyymmddhhmmssDashedString(addressBook.getCcTestResultDate());
			
			jsonString+="[{\"CCTestResult\":\""+addressBook.getCcTestResult()+"\",\"Date\":\""+date+"\"}]";
			// Convert the object to a JSON string
			log.log(Level.INFO,"Address Book --> Information fetched successfully... ");

			return Response.status(200).entity(jsonString).build();
		} catch (Exception e) {
			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_ADDRESS_BOOK, AuditTrail.FUNCTION_FAIL,"FAILED"));
			
			log.log(Level.SEVERE, "Address Book --> Error in fetching Record.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			logsMain.insertLogs("AddressBookRest",Level.SEVERE+"",errors.toString());
			
			return Response.status(200).entity(googleJson.toJson(beans)).build();
		} finally {
			addressBookList.clear();
			auditTrailMaint = null;
			addressBookMaintenance = null;
			jsonString = null;
			beans = null;
			builder = null;
			googleJson = null;
			System.gc();
		}
	}




@GET
@Path("/insertCCTestResults")
@Produces(MediaType.APPLICATION_JSON)
public Response insertCCTestResults(@Context HttpServletRequest request) {
	log.log(Level.INFO, "Address Book --> insertCCTestResults ");
	MsgBeans beans = new MsgBeans();
	String agentId = request.getParameter("agentId");
	String candidateCode=request.getParameter("candidateCode");
	String ccTestResult=request.getParameter("ccTestResult");
	String ccTestResultDateStr = request.getParameter("ccTestResultDate");
	AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
	Date ccTestResultDate = new Date();
	
	if(null != ccTestResultDateStr && !"".equals(ccTestResultDateStr)){
		ccTestResultDate = LMSUtil.convertDateToyyyymmddhhmmssDashed(ccTestResultDateStr);
	}
	try{
		if(agentId!=null && candidateCode!=null && ccTestResult!=null){
			
			if(!ccTestResult.equalsIgnoreCase("Urgent") && !ccTestResult.equalsIgnoreCase("Normal") && !ccTestResult.equalsIgnoreCase("Caution")  ){
				beans.setCode("500");
				beans.setMassage("CC Test Result Sould be Urgent,Normal,Caution");
				return Response.status(500).entity(new Gson().toJson(beans)).build();
			}else{
				AddressBookMaintenance addressBookMain=new AddressBookMaintenance();
				AddressBook addressbook=addressBookMain.getaddressDataForCCTest(candidateCode);
				addressbook.setCcTestResult(ccTestResult);
				addressbook.setCcTestResultDate(ccTestResultDate);
				addressBookMain.updateAddressBook(addressbook);
				auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INSERT_CC_TEST, AuditTrail.FUNCTION_SUCCESS, "SUCCESS"));
				beans.setCode("200");
				beans.setMassage("Success");
				return Response.status(200).entity(new Gson().toJson(beans)).build();
				
			}
			
		}else{
			beans.setCode("500");
			beans.setMassage("Agent Id and Candidate Code must be required ");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
		
	}catch(Exception e){
			log.log(Level.INFO,"Iddress Book --> insertCCTestResults --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("AddressBookRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	
	}
}