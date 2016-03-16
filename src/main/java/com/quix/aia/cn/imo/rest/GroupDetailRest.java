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
 * -----------------------------------------------------------------------------
 * 17-Feb-2016       Maunish             File Added
 ***************************************************************************** */

package com.quix.aia.cn.imo.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.text.ParseException;
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
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.common.RestForm;
import com.quix.aia.cn.imo.data.group.GroupDetail;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.GroupDetailMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.utilities.LMSUtil;

/**
 * <p>
 * Logic to Save GroupDetail through Rest service.
 * </p>
 * 
 * @author Maunish Soni
 * @version 1.0
 *
 *
 * */
@Path("/groupDetail")
public class GroupDetailRest {
	static Logger log = Logger.getLogger(GroupDetailRest.class.getName());

	/**
	 * <p>
	 * GroupDetail Synchronization rest service post method which gets Json
	 * string, which contains list of GroupDetail records. This method performs
	 * save or update operations. It returns Json string with local address code
	 * with IOS address code.
	 * </p>
	 * 
	 * @param jsonGroupDetailListString
	 * 
	 */
	@POST
	@Path("/syncGroupDetail")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response syncGroupDetail(String jsonGroupDetailListString) {
		log.log(Level.INFO, "GroupDetail --> Sync Record ");
		log.log(Level.INFO,"GroupDetail --> Sync Record --> Data for Sync...  ::::: "+jsonGroupDetailListString);
		boolean flag=LMSUtil.isJSONValid(jsonGroupDetailListString);
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		List<GroupDetail> jsonObjList = new ArrayList();
		GsonBuilder builder = new GsonBuilder();
		GroupDetailMaintenance groupDetailMaintenance = new GroupDetailMaintenance();
		Gson googleJson = null;
		Type listType = null;
		String returnJsonString = "";
		try {
			
			if(flag==true){
				
			
			returnJsonString = "[";

			builder.registerTypeAdapter(Date.class,
					new JsonDeserializer<Date>() {
						@Override
						public Date deserialize(JsonElement json, Type typeOfT,
								JsonDeserializationContext context)
								throws JsonParseException {
							Date date = null;
							try {
								date = LMSUtil.yyyymmddHHmmssdashed.parse(json.getAsString());
							} catch (ParseException e) {
//								e.printStackTrace();
							}
							return date;
						}
					});
			
			builder.registerTypeHierarchyAdapter(byte[].class,
	                new JsonDeserializer<byte[]>(){
				public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		            return Base64.decodeBase64(json.getAsString());
		        }
			});

			googleJson = builder.create();
			listType = new TypeToken<List<GroupDetail>>() {}.getType();
			jsonObjList = googleJson.fromJson(jsonGroupDetailListString,listType);

			// maintain in single transaction
			returnJsonString += groupDetailMaintenance.insertOrUpdateRestBatch(jsonObjList);
			returnJsonString += "]";

			log.log(Level.INFO, "GroupDetail --> saved successfully ");
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_GROUP_DETAIL,AuditTrail.FUNCTION_SUCCESS, "SUCCESS"));
			return Response.status(200).entity(returnJsonString).build();
			}else{
				boolean status=false;
				return Response.status(200).entity("[{\"status\":"+status+"}]").build();
			}
		} catch (Exception e) {

			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_GROUP_DETAIL, AuditTrail.FUNCTION_FAIL,"FAILED"));

			log.log(Level.SEVERE, "GroupDetail --> Error in Save Record.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("GroupDetailRest",Level.SEVERE+"",errors.toString());
			
			return Response.status(200).entity(googleJson.toJson(beans)).build();
		} finally {
			jsonObjList.clear();
			auditTrailMaint = null;
			groupDetailMaintenance = null;
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
	 * This method retrieves List of GroupDetail for particular Agent.
	 * </p>
	 * 
	 * @param agentId
	 * 
	 */
	@POST
	@Path("/getGroupDetails")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroupDetails(@Context HttpServletRequest request,
			   @Context ServletContext context,String jsonString) {
		log.log(Level.INFO, "GroupDetail --> getGroupDetails ");
		boolean flag=LMSUtil.isJSONValid(jsonString);
		GsonBuilder builder = new GsonBuilder();
		GroupDetailMaintenance groupDetailMaintenance = new GroupDetailMaintenance();
		Gson googleJson = builder.create();
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint = new AuditTrailMaintenance();
		List<GroupDetail> addressBookList = new ArrayList();
	//String jsonString = "";
		try {
			
			if(flag==true){
			
			
			
			/*Type listType = new TypeToken<List<RestForm>>(){}.getType();
	        List<RestForm> jsonObjList = googleJson.fromJson(jsonString, listType);
	        RestForm restForm = jsonObjList.get(0);  */
				RestForm restForm= googleJson.fromJson(jsonString, RestForm.class);
	        String agentId = restForm.getAgentId();
			String coBranch =restForm.getCo();
			String dateTime = restForm.getDateTime();
	        
			
			
			builder.registerTypeHierarchyAdapter(byte[].class,
	                new JsonSerializer<byte[]>(){
				public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
		            return new JsonPrimitive(Base64.encodeBase64String(src));
		        }
			});
			
			googleJson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

			log.log(Level.INFO, "GroupDetail --> fetching Information ... ");
			addressBookList = groupDetailMaintenance.getGroupDetails(request, context,agentId,dateTime,coBranch);
			
			String deletedList = "";
			// Convert the object to a JSON string
			log.log(Level.INFO,"GroupDetail --> Information fetched successfully... ");
			jsonString="";
			jsonString = googleJson.toJson(addressBookList);

			//String dateTime = request.getParameter("dateTime");
			if (null != dateTime && !"".equals(dateTime)) {
				deletedList = groupDetailMaintenance.getDeletedGroupDetails(request, context,agentId,dateTime,coBranch);
				if(!"".equals(deletedList)){
					jsonString = jsonString.substring(0, jsonString.length()-1);
					if(!addressBookList.isEmpty()){
						deletedList = ","+deletedList;
					}
					deletedList+="]";
					jsonString +=deletedList;
				}
			}
			}else{
				beans.setCode("500");
				beans.setMassage("Json not valid");
				return Response.status(500).entity(googleJson.toJson(beans)).build();
			}
			return Response.status(200).entity(jsonString).build();
		} catch (Exception e) {

			beans.setCode("500");
			beans.setMassage("Something wrong happens, please contact administrator. Error Message : "+ e.getMessage());
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest",AuditTrail.MODULE_GROUP_DETAIL, AuditTrail.FUNCTION_FAIL,"FAILED"));

			log.log(Level.SEVERE, "GroupDetail --> Error in fetching Record.");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("GroupDetailRest",Level.SEVERE+"",errors.toString());
			
			return Response.status(200).entity(googleJson.toJson(beans)).build();
		} finally {
			addressBookList.clear();
			auditTrailMaint = null;
			groupDetailMaintenance = null;
			jsonString = null;
			beans = null;
			builder = null;
			googleJson = null;
			System.gc();
		}
	}
}