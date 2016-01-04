<%--
  - Author(s):          Nibedita
  - Date:               16-June-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        EOP Candidate Registration
--%>
<%@page import="com.quix.aia.cn.imo.mapper.EopMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.event.Event"%>
<%@page import="com.quix.aia.cn.imo.mapper.AddressBookMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.addressbook.AddressBook"%>
<%@page import="com.quix.aia.cn.imo.mapper.AamDataMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.common.AamData"%>
<%@page import="cn.aia.tools.security.AESPasswordManager"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Map"%>
<%@page import="com.quix.aia.cn.imo.utilities.EncrypitDecrypit"%>
<%@page import="com.quix.aia.cn.imo.data.event.EventCandidate"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.data.announcement.Announcement"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.mapper.EopAttendanceMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.ApplicationAttribute"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/interface/AamData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>


<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
User userObj = (User)request.getSession().getAttribute("currUserObj");
Map<String,String> configurationMap =(Map<String,String>) application.getAttribute(ApplicationAttribute.CONFIGURATION_PROPERTIES_MAP);
// ResourceBundle msgProps = configurationMap.getBundle("configurations");
String encryptDecryptKey  = configurationMap.get("encryptDecryptKey");
boolean modifyFlag = false;
FormObj formDetail = null;

EventCandidate candidate = new EventCandidate();
Event event = new Event();
if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
}
candidate = (EventCandidate)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);
String agent_id = "",event_code="",agent_name = "", co="";
String age = "0";
int buCode = 0,distCode = 0,branch=0;
String cityCode = 0+"",sscCode = 0+"",office=0+"";
if(userObj!=null){
	buCode=userObj.getBuCode();
	distCode=userObj.getDistrict();
	cityCode=userObj.getCityCode()+"";
	sscCode=userObj.getSscCode()+"";
	branch=userObj.getBranchCode();
	office=userObj.getOfficeCode()+"";
	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null)
		event_code = request.getParameter("eCode");
	else
		event_code = request.getParameter("eventCode");
	 event =new EopMaintenance().getEvent(Integer.parseInt(event_code));
}else{
	if(request.getParameter("agentID")!=null)
		//agent_id =	AESPasswordManager.getInstance().decryptPassword(request.getParameter("agentID"));
		agent_id = request.getParameter("agentID");
	if(request.getParameter("co")!=null)
		co = request.getParameter("co");		
	if(request.getParameter("eventCode")!=null)
		event_code = request.getParameter("eventCode");
	 if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) == null)
		 agent_name = AamDataMaintenance.retrieveAgentName(agent_id, co); 
	
	  event = (Event)request.getSession().getAttribute("Event_OBJ");
}
String email = "",education = "", weChat = "",nric = "";
//This is modify
  if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag = true;
	buCode = Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(candidate.getBuCode())));
	distCode = Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(candidate.getDistrictCode())));
	branch = Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(candidate.getBranchCode())));
	cityCode =SecurityAPI.encodeHTML(String.valueOf(candidate.getCityCode()));
	sscCode = SecurityAPI.encodeHTML(String.valueOf(candidate.getSscCode()));
	office = SecurityAPI.encodeHTML(String.valueOf(candidate.getOfficeCode()));
	String eventCandidateCode = "0";
	if(candidate.getEventCandidateCode()!=null)
		eventCandidateCode = candidate.getEventCandidateCode();
	AddressBook  addressBook = new AddressBookMaintenance().getaddressDataForCCTest(eventCandidateCode);
	email = addressBook.geteMailId();
	education = addressBook.getEducation();
	weChat = addressBook.getWeChat();
	nric = addressBook.getNric();
	
}
  if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
	  age =  request.getParameter("age");
  }
 
/* if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
	buCode = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.BU)));
	distCode = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.DISTRICT)));
	cityCode = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.CITY)));
	sscCode = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.SSC)));
	branch = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter("branch")));
	office = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter("office")));

}  */
ImoUtilityData imoUtilityDate=new ImoUtilityData();


%>

<script type="text/javascript">//<![CDATA[
$(function() {
	 $( ".datepicker" ).datepicker({
    	showOn: "button",
    	buttonImage: "images/calendar_icon.gif",
    	buttonImageOnly: true,
    	dateFormat:"dd/mm/yy",
    	changeMonth: true, 
    	changeYear: true,
    	yearRange: '1950:2025',
    	});
	
});                                          
 
function getAgentName(agentId, coBranch){
	if(agentId!='' && '' != coBranch){
		$('#ajaxLoader').find(".lightbox").show();
		AamData.retrieveAgentName(agentId, coBranch,{
			callback : function(str) 
			{
		         $('#agentName').val(str);
		         $('#agent_name').val(str);
		         $('#ajaxLoader').find(".lightbox").hide();
		       
	         } 
	    });
 	}
}
<%-- $( document ).ready(function() {
<%
if(userObj!=null){
if(userObj.getUserType().equals("AD") ){ %> 
		getBU('<%=buCode %>');
		
<% }%>
<%if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ){ %>
					getDistrict('<%=buCode %>','<%=distCode %>','L');
				
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isBranchLevel() ){ %>
			getBranch('<%=distCode %>','<%=branch %>','L');
	
		<% }%>
	
		<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
		 
		getCity('<%=branch %>','<%=cityCode %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
		
		getSSC('<%=cityCode %>','<%=sscCode %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
	
	getOffice('<%=sscCode %>','<%=office %>');
<% }
}else{%>
getBU('<%=buCode %>');
getDistrict('<%=buCode %>','<%=distCode %>');
getBranch('<%=distCode %>','<%=branch %>');
getCity('<%=branch %>','<%=cityCode %>');
getSSC('<%=cityCode %>','<%=sscCode %>');
getOffice('<%=sscCode %>','<%=office %>');

<%}%>

}); --%>
function submitForm(){
	document.attendanceForm.submit();
	$('#submitBtn').hide();
	
}
function autoCalculateAge(obj){
	 if(obj.value !=""){
		
		 var dob = obj.value;
		 var today = new Date();
		 var bday = dob.split('/')[0];
		 var bmonth = dob.split('/')[1];
		 var byear = dob.split('/')[2];
		
		 var birthDate = new Date(bmonth +'/'+ bday + '/' + byear);
		 
		 var age = today.getFullYear() - birthDate.getFullYear();
		 var m = today.getMonth() - birthDate.getMonth();
		 if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
		     age--;
		 }
        if(age < 0){
        	age = '';
        	obj.value = '';
        	$('#agetxt').val('');
        	
        }
	 $('#age').val(age);
	 $('#agetxt').val(age);
	 }
}

</script>

<%if(userObj==null){%>
<div id="header">
		<div class="container_12">
 					<div class="grid_2" >
         				<!-- <a href="#" class="MT08"> --><img src="images/aia_logo.png" width="153" height="65" alt="" /><!-- </a> -->
          			</div>
         			<div class="grid_3">
          				<font color="white"><b><div align="left" style="margin-top:10px"></div><div align="left"></div> </b></font>
          			</div>
			 </div>
</div>
<%}%>
   	<div id="maincontainer">	
   					<div class="head">
   					<h2 align="center"> <%if(modifyFlag == true){%>
   					<%=localeObj!=null ?localeObj.getTranslatedText("EOP Edit Candidate Registration") : "编辑 EOP  候选人 注册"%>
   					<%}else{ %>
   					<%=localeObj!=null ?localeObj.getTranslatedText("EOP Add Candidate Registration") : "活动报名" %>
   					<%} %>
   					</h2></div>  	
   							<div class="content" style="background-color:#ffffff;">
   								<table class="formDesign">
   										<tr>                          
	                                   		<td colspan="2"><h2 align="center"  style="color:brown;"><%=localeObj!=null ?localeObj.getTranslatedText("Event Details") : "活动详情"%></h2></td>   
                                      	</tr>
                                      		<tr>                          
	                                   		<td colspan="2"></td>   
                                      	</tr>
                              	</table>
   							
   							<table class="formDesign">
   										<tr>                          
	                                   		<td style="width:30%;"><label><%=localeObj!=null ?localeObj.getTranslatedText("Topic") : "活动主题 "%>:</label></td>
	                                   		<td style="width:25%;"><%=event.getTopic() %></td>
                                      	</tr>
                                      	<tr>                          
	                                   		<td><label><%=localeObj!=null ?localeObj.getTranslatedText("Date") : " 日期 "%>:	</label></td>
                                      		<td><%=event.getEventDate()!=null?LMSUtil.convertDateToString(event.getEventDate()):"" %></td>     
                                      	</tr>
                                      	<tr>    
                                     	<td><label><%=localeObj!=null ?localeObj.getTranslatedText("Start Time") : "开始 时间 "%>:	</label></td>  
                                      		<td><%=SecurityAPI.encodeHTML(LMSUtil.converDateIntoHHMMAMPM(event.getStartTime())) %></td>                  
                                      	</tr>
                                      	 <tr>    
	                                        <td><label><%=localeObj!=null ?localeObj.getTranslatedText("End Time") : "结束 时间 "%>:	</label></td> 
	                                     	<td><%=SecurityAPI.encodeHTML(LMSUtil.converDateIntoHHMMAMPM(event.getEndTime())) %></td> 
                                      	</tr>
                                      	 <tr>                          
	                                   		<td><label><%=localeObj!=null ?localeObj.getTranslatedText("Location") : "地点 "%>:	</label></td>
	                                   		<td><%=event.getLocation() %></td>
                                      	</tr>
                                      	 <tr>                          
	                                     	<td><label><%=localeObj!=null ?localeObj.getTranslatedText("Presenter") : "主讲人 "%>:	</label>
	                                     	<td ><%= event.getSpeaker()%></td>
                                      	</tr>
                                      	 <tr>                          
	                                     	<td><label><%=localeObj!=null ?localeObj.getTranslatedText("Description") : "活动简介 "%>:	</label>
	                                     	<td ><%= event.getEopDescription()%></td>
                                      	</tr>
                                   
   							</table>
   								<form action="FormManager" name="attendanceForm" method="post"  class="PT20">
   								<input name="<%=EopAttendanceMaintenance.AGE %>" id="<%=EopAttendanceMaintenance.AGE %>" type="hidden"  value="<%=age%>"  />
   								 <input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
   								   <input type="hidden" name="<%=FormInfo.HIDDEN_ACTION%>" value="SUBMIT"/> 
					                 <%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){%>	
   								   	 <input type="hidden" name="eCode" id="eCode" value="<%=request.getParameter("eCode")%>"/> 
   								    <%}else{ %>
   								    <input type="hidden" name="eCode" id="eCode" value="<%=event_code%>"/> 
   								    <%} %>
                           			<table class="formDesign" style="width:47%;">
                           				<tr>                          
	                                   		<td colspan="2"><h2 align="center"  style="color:brown;"><%=localeObj!=null ?localeObj.getTranslatedText("Candidate Details") : "报名信息"%></h2></td>   
                                      	</tr>
                           				   <tr>
	                         				 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) == null){
					                          %>
					                           <td colspan="2" style="text-align: center"><span style="color:#ec2028;"></span></td>
					                          <%
					                          	}else{
					                          %>
					                          <td colspan="2" style="text-align: center"><span style="color:#ec2028;">
					                          &nbsp;<%=localeObj!=null ?localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg()) : ((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg()%></span></td>
					                          <%
					                          	}
					                          %>
	                      				  </tr>
                         
                                      	<tr> 
                                      	<%if(userObj!=null) {%>                         
	                                   		<td><span style="color:#ec2028;">* </span><label><%=localeObj!=null ?localeObj.getTranslatedText("Servicing Agent") : "引荐人"%></label> </td>
	                                   		<%}else{ %>
	                                   		<td></td>
	                                   		<%} %>
	                                     	<td style="width:50%;">
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		if(userObj==null && agent_id !=null){  %>
					                          		 <%-- <input name="serviceAgent" id="serviceAgent" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.SERVICING_AGENT))%>"  maxlength="30" disabled/> --%>
					                          		 <input name="<%=EopAttendanceMaintenance.CO %>" id="<%=EopAttendanceMaintenance.CO %>" type="hidden"   value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.CO))%>"  maxlength="30"/>
	                                       	         <input name="<%=EopAttendanceMaintenance.SERVICING_AGENT %>" id="<%=EopAttendanceMaintenance.SERVICING_AGENT %>" type="hidden"   value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.SERVICING_AGENT))%>"  maxlength="30"/>
	                                       	  <%}else{ %>	
	                                       	  		<input name="<%=EopAttendanceMaintenance.CO %>" id="<%=EopAttendanceMaintenance.CO %>" type="hidden"   value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.CO))%>"  maxlength="30"/>
	                                       			 <input name="<%=EopAttendanceMaintenance.SERVICING_AGENT %>" id="<%=EopAttendanceMaintenance.SERVICING_AGENT %>" type="text" class="textObj" value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.SERVICING_AGENT)) %>"   onblur ="getAgentName(this.value,'<%=request.getParameter(EopAttendanceMaintenance.CO) %>');" maxlength="30" />
	                                       	 <%}}else if(userObj==null && agent_id !=null){ %>
			                                       <%-- 	  <input name="serviceAgent" id="serviceAgent" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(agent_id)%>"  maxlength="30" disabled/> --%>
			                                       	  <input name="<%=EopAttendanceMaintenance.SERVICING_AGENT %>" id="<%=EopAttendanceMaintenance.SERVICING_AGENT %>" type="hidden"   value="<%=SecurityAPI.encodeHTML(agent_id)%>"  maxlength="30"/>
			                                       	  <input name="<%=EopAttendanceMaintenance.CO %>" id="<%=EopAttendanceMaintenance.CO %>" type="hidden"   value="<%=SecurityAPI.encodeHTML(co)%>"  maxlength="30"/>
	                                       	 <%}else{%>
	                                       	 		<input name="<%=EopAttendanceMaintenance.CO %>" id="<%=EopAttendanceMaintenance.CO %>" type="hidden"    value="<%=SecurityAPI.encodeHTML(co)%>"  maxlength="30"/>
	                                       	 		<input name="<%=EopAttendanceMaintenance.SERVICING_AGENT %>" id="<%=EopAttendanceMaintenance.SERVICING_AGENT %>" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(candidate.getServicingAgent())%>"   onblur ="getAgentName(this.value,'<%=co %>');" maxlength="30" />
	                                       	 <%} %>
	                                      	</td>                                    			   
                                      	</tr>
                                      	<tr>                          
	                                   		<td><span style="color:#ec2028;">* </span><label><%=localeObj!=null ?localeObj.getTranslatedText("Agent Name") : "引荐人"%></label></td>
	                                     	<td style="width:50%;">
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		if(userObj==null && agent_name !=null){  %>
					                          		 <input name="agent_name" id="agent_name" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.AGENT_NAME))%>"  maxlength="30" disabled/>
	                                       	         <input name="<%=EopAttendanceMaintenance.AGENT_NAME %>" id="<%=EopAttendanceMaintenance.AGENT_NAME %>" type="hidden"   value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.AGENT_NAME))%>"  maxlength="30"/>
	                                       	  <%}else{ %>	
	                                       			 <input name="agent_name" id="agent_name" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.AGENT_NAME))%>"  maxlength="30" disabled/>
	                                       	         <input name="<%=EopAttendanceMaintenance.AGENT_NAME %>" id="<%=EopAttendanceMaintenance.AGENT_NAME %>" type="hidden"   value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.AGENT_NAME))%>"  maxlength="30"/>
	                                       	 <%}}else if(userObj==null && agent_name !=null){ %>
			                                       	  <input name="agent_name" id="agent_name" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(agent_name)%>"  maxlength="30" disabled/>
			                                       	  <input name="<%=EopAttendanceMaintenance.AGENT_NAME %>" id="<%=EopAttendanceMaintenance.AGENT_NAME %>" type="hidden"   value="<%=SecurityAPI.encodeHTML(agent_name)%>"  maxlength="30"/>
	                                       	 <%}else{%>
	                                       	 		  <input name="agent_name" id="agent_name" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(candidate.getAgentName())%>"  maxlength="30" disabled/>
			                                       	  <input name="<%=EopAttendanceMaintenance.AGENT_NAME %>" id="<%=EopAttendanceMaintenance.AGENT_NAME %>" type="hidden"   value="<%=SecurityAPI.encodeHTML(candidate.getAgentName())%>"  maxlength="30"/>
	                                       	 <%} %>
	                                     	
	                                      	</td>                                    			   
                                      	</tr>
                            		 	 	
										<tr>                          
	                                   		<td><span style="color:#ec2028;">* </span><label><%=localeObj!=null ?localeObj.getTranslatedText("Candidate Name") :"您的姓名"%></label></td>
	                                     	<td>
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		
					                          %>	
	                                       	 <input name="<%=EopAttendanceMaintenance.CANDIDATE_NAME %>" id="<%=EopAttendanceMaintenance.CANDIDATE_NAME %>" type="text" class="textObj" value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.CANDIDATE_NAME ))%>"  maxlength="250" />
	                                       	 <%}else{ %>
	                                       	  <input name="<%=EopAttendanceMaintenance.CANDIDATE_NAME %>" id="<%=EopAttendanceMaintenance.CANDIDATE_NAME %>" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(candidate.getCandidateName())%>"   maxlength="250" />
	                                       	 <%} %>
	                                      	</td>                                    			   
                                      	</tr>		
                        		 	<%-- 	<tr>                         
                                			<td><span style="color:#ec2028;">* </span><label ><%=localeObj!=null ?localeObj.getTranslatedText("BU") : "BU" %></label></td>                                    				
                                  			 <td>		
                                        	<% if(userObj==null || (userObj!=null && userObj.getUserType().equals("AD"))){ %>
                                				<select name="<%=EopAttendanceMaintenance.BU %>" id="<%=EopAttendanceMaintenance.BU %>"  class="comboObj"  onchange="getDistrict(this.value,0);" >
                               						<option value="0"><%=localeObj!=null ?localeObj.getTranslatedText("Select") : " Select"%></option>	 
                               	 				 </select>
                               	 				<%}else{ %>
                               	 				<select name="<%=EopAttendanceMaintenance.BU %>" id="<%=EopAttendanceMaintenance.BU %>"  class="comboObj" >
                               						<option value="<%=userObj.getBuCode() %>" ><%=imoUtilityDate.getBuCode(userObj.getBuCode()) %></option>
                               	 				 </select>
                               	 				<%} %>
                                 	 		</td>                                      			   
                                  		</tr>
                        		 		<tr>                         
                                			<td><label ><%=localeObj!=null ?localeObj.getTranslatedText("District") : "District" %></label></td>                                     				
                                  			<td>	
                                  			<% if(userObj==null || (userObj!=null && (userObj.getUserType().equals("AD") || userObj.isBuLevel()))){ %>
                                				<select name="<%=EopAttendanceMaintenance.DISTRICT %>" id="<%=EopAttendanceMaintenance.DISTRICT %>"  class="comboObj"  onchange="getBranch(this.value,0);">
                               						<option value="0"><%=localeObj!=null ?localeObj.getTranslatedText("ALL") : "ALL"%></option>	 
                               	 				 </select>
                               	 			 <%}else{ %>
                               	 				 <select name="<%=EopAttendanceMaintenance.DISTRICT %>" id="<%=EopAttendanceMaintenance.DISTRICT %>"  class="comboObj">
                               						<option value="<%=userObj.getDistrict() %>" ><%= imoUtilityDate.getDistCode(userObj.getDistrict()) %></option> 
                               	 				 </select>
                               	 				 <%} %>
                                 	 		</td>                                     			   
                                  		</tr>
                                  		
                                  		<tr>                         
                                			<td><label ><%=localeObj!=null ?localeObj.getTranslatedText("Branch") : "Branch" %></label></td>                                     				
                                  			<td>	
                                  			<% if(userObj==null || (userObj!=null && (userObj.getUserType().equals("AD") || userObj.isBranchLevel() ||userObj.isBuLevel()))){ %>
                                				<select name="branch" id="branch"  class="comboObj"  onchange="getCity(this.value,0);">
                               						<option value="0"><%=localeObj!=null ?localeObj.getTranslatedText("ALL"):"ALL"%></option>	 
                               	 				 </select>
                               	 			 <%}else{ %>
                               	 				 <select name="branch" id="branch"  class="comboObj">
                               						<option value="<%=userObj.getBranchCode() %>" ><%= imoUtilityDate.getBranchCode(userObj.getBranchCode()) %></option> 
                               	 				 </select>
                               	 				 <%} %>
                                 	 		</td>                                     			   
                                  		</tr>	
                                  		
                                  		
                                  		
                                  			
                        		 		 <tr>                         
                            				<td>                                      				
                             				<label ><%=localeObj!=null ?localeObj.getTranslatedText("City") : "City"%> </label>
                            				</td>
                                			<td>
                                			<% if(userObj==null || (userObj!=null && (userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()))){ %>	
                               				<select name="<%=EopAttendanceMaintenance.CITY %>" id="<%=EopAttendanceMaintenance.CITY %>"  class="comboObj"  onchange="getOffice(this.value,0);">
                              						<option value="0"><%=localeObj!=null ?localeObj.getTranslatedText("ALL") : "ALL"%></option>	                                       	
                              	 			 </select>
                              	 			 <%}else{ %>
                              	 			 <select name="<%=EopAttendanceMaintenance.CITY %>" id="<%=EopAttendanceMaintenance.CITY %>"  class="comboObj" >
                              						<option value="<%=userObj.getCityCode() %>" ><%=imoUtilityDate.getCityName(userObj.getCityCode()) %></option>	                                       	
                              	 			 </select>
                              	 			 <%} %>
                               	 			</td>                                   			   
                                  		 </tr>
                                  			    
                                  			    
                                  			    <tr>                         
                                			<td><label ><%=localeObj!=null ?localeObj.getTranslatedText("Office") : "Office" %></label></td>                                     				
                                  			<td>	
                                  			<% if(userObj==null || (userObj!=null && (userObj.getUserType().equals("AD") || userObj.isBranchLevel() ||userObj.isBuLevel()))){ %>
                                				<select name="office" id="office"  class="comboObj"  onchange="getSSC(this.value,0);">
                               						<option value="0"><%=localeObj!=null ?localeObj.getTranslatedText("ALL"):"ALL"%></option>	 
                               	 				 </select>
                               	 			 <%}else{ %>
                               	 				 <select name="office" id="office"  class="comboObj">
                               						<option value="<%=userObj.getOfficeCode() %>" ><%= imoUtilityDate.getOfficeName(userObj.getOfficeCode()) %></option> 
                               	 				 </select>
                               	 				 <%} %>
                                 	 		</td>                                     			   
                                  		</tr>
                                  		
                                  		
                 		 				<tr>                         
                         				<td>                                    				
                          				<label ><%=localeObj!=null ?localeObj.getTranslatedText("SSC") : "SSC"%> </label>
                         				</td>
                             			<td>
                             			<% if(userObj==null || (userObj!=null && (userObj.getUserType().equals("AD") || userObj.isCityLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()))){ %>
                             				<select name="<%=EopAttendanceMaintenance.SSC %>" id="<%=EopAttendanceMaintenance.SSC %>"  class="comboObj" >
                            						<option value="0"><%=localeObj!=null ?localeObj.getTranslatedText("ALL"):"ALL"%></option>	                                       	
                            	 			</select>
                            	 		<%}else{ %>
                            	 		<select name="<%=EopAttendanceMaintenance.SSC %>" id="<%=EopAttendanceMaintenance.SSC %>"  class="comboObj" >
                            						<option value="<%=userObj.getSscCode() %>" ><%=imoUtilityDate.getSscName(userObj.getSscCode()) %></option>                                       	
                            	 			</select>
                            	 		<%} %>
                            	 		</td>                                     			   
                           			    </tr> --%>
                           			 <%--    <tr>                          
	                                   		<td><span style="color:#ec2028;">* </span><label><%=localeObj!=null ?localeObj.getTranslatedText("Agency Leader Code") : "Agency Leader Code" %></label></td>
	                                     	<td>
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		
					                          %>	
	                                       	 <input name="<%=EopAttendanceMaintenance.AGENCY_LEADER_CODE %>" id="<%=EopAttendanceMaintenance.AGENCY_LEADER_CODE %>" type="text" class="textObj" value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.AGENCY_LEADER_CODE)) %>"  maxlength="30"  />
	                                       	 <%}else{ %>
	                                       	  <input name="<%=EopAttendanceMaintenance.AGENCY_LEADER_CODE %>" id="<%=EopAttendanceMaintenance.AGENCY_LEADER_CODE %>" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(candidate.getAgencyLeaderCode())%>"  maxlength="30"  />
	                                       	 <%} %>
	                                      	</td>                                    			   
                                      	</tr>
                           			     <tr>                          
	                                   		<td><span style="color:#ec2028;">* </span><label><%=localeObj!=null ?localeObj.getTranslatedText("Agency Leader Name"):"Agency Leader Name"%></label></td>
	                                     	<td>
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		
					                          %>	
	                                       	 <input name="<%=EopAttendanceMaintenance.AGENCY_LEADER_NAME %>" id="<%=EopAttendanceMaintenance.AGENCY_LEADER_NAME %>" type="text" class="textObj" value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.AGENCY_LEADER_NAME)) %>"  maxlength="250" />
	                                       	 <%}else{ %>
	                                       	  <input name="<%=EopAttendanceMaintenance.AGENCY_LEADER_NAME %>" id="<%=EopAttendanceMaintenance.AGENCY_LEADER_NAME %>" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(candidate.getAgencyLeaderName())%>"  maxlength="250" />
	                                       	 <%} %>
	                                      	</td>                                    			   
                                      	</tr>
                                      	 <tr>                          
	                                   		<td><span style="color:#ec2028;">* </span><label><%=localeObj!=null ?localeObj.getTranslatedText("Source of Referral") : "Source of Referral" %></label></td>
	                                     	<td>
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		
					                          %>	
	                                       	 <input name="<%=EopAttendanceMaintenance.SOURCE_OF_REFERAL %>" id="<%=EopAttendanceMaintenance.SOURCE_OF_REFERAL %>" type="text" class="textObj" value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.SOURCE_OF_REFERAL)) %>"   maxlength="250" />
	                                       	 <%}else{ %>
	                                       	  <input name="<%=EopAttendanceMaintenance.SOURCE_OF_REFERAL %>" id="<%=EopAttendanceMaintenance.SOURCE_OF_REFERAL %>" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(candidate.getSourceOfReferal())%>"  maxlength="250" />
	                                       	 <%} %>
	                                      	</td>                                    			   
                                      	</tr>--%>
                                      	  	<tr>                          
                            				<td><span style="color:#ec2028;">* </span><label><%=localeObj!=null ? localeObj.getTranslatedText("Gender") : "性别"%></label></td>
                            				<%
                            				String gender = "";
                            				if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
                            					gender = request.getParameter(EopAttendanceMaintenance.GENDER);
                            				}else{
                            					gender = candidate.getGender();
                            				}
                            				
                            				%>
                                			<td>	
                                			<select name="<%=EopAttendanceMaintenance.GENDER %>" id="<%=EopAttendanceMaintenance.GENDER %>" class="comboObj" >
                               						<option value="1"><%=localeObj!=null ?localeObj.getTranslatedText("Select"):"请选择" %></option>
                    								<option value="F"  <%="F".equals(gender)?"selected":"" %>><%=localeObj!=null ?localeObj.getTranslatedText("Female") : "女"%></option>	
                               						<option value="M"  <%="M".equals(gender)?"selected":"" %>><%=localeObj!=null  ? localeObj.getTranslatedText("Male") : "男"%></option>                                       	
                               	 			</select>
                               	 			</td>                                    			   
                                      	 </tr>
                                      	 <tr>                           
	                                   		<td><span style="color:#ec2028;">* </span><label><%=localeObj!=null ?localeObj.getTranslatedText("Date of Birth") : "出生日期"%></label></td>
	                                     	<td>
	                                     	 <%String dob = "";
	                                 		
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		dob = request.getParameter(EopAttendanceMaintenance.DOB);
					                          	}
				                          		else{
				                          			dob = candidate.getDob()!=null?LMSUtil.convertDateToString(candidate.getDob()):"";	
				                          		}
					                          %>
	                                       	 <input name="<%=EopAttendanceMaintenance.DOB %>" id="<%=EopAttendanceMaintenance.DOB %>" type="text" class="textObj datepicker" value="<%=dob%>" onchange="autoCalculateAge(this)"  readonly="readonly" />
	                                       	 
	                                      	</td>                                    			   
                                      	</tr>
                                  <%--     	<tr>                          
	                                   		<td><span style="color:#ec2028;">* </span><label><%=localeObj!=null ?localeObj.getTranslatedText("Age") : "Age" %></label></td>
	                                     	<td>
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		
					                          %>	
	                                       	 <input name="agetxt" id="agetxt" type="text" class="textObj" value="<%=request.getParameter(EopAttendanceMaintenance.AGE ) %>" disabled  />
	                                       	  <input name="<%=EopAttendanceMaintenance.AGE %>" id="<%=EopAttendanceMaintenance.AGE %>" type="hidden"  value="<%=request.getParameter(EopAttendanceMaintenance.AGE )%>"  />
	                                       	 <%}else{ %>
	                                       	  <input name="agetxt" id="agetxt" type="text" class="textObj"    value="<%=candidate.getAge()%>"   disabled />
	                                       	   <input name="<%=EopAttendanceMaintenance.AGE %>" id="<%=EopAttendanceMaintenance.AGE %>" type="hidden"  value="<%=candidate.getAge()%>" />
	                                       	 <%} %>
	                                       	
	                                      	</td>                                    			   
                                      	</tr> --%>
                                    	 <tr>                          
	                                   		<td><%if(localeObj==null){%><span style="color:#ec2028;">* </span><%} %><label><%=localeObj!=null ?localeObj.getTranslatedText("Contact Number") : "联系电话"%></label></td>
	                                     	<td>
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		
					                          %>	
	                                       	 <input name="<%=EopAttendanceMaintenance.CONTACT_NUMBER %>" id="<%=EopAttendanceMaintenance.CONTACT_NUMBER %>" type="text" class="textObj" value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.CONTACT_NUMBER)) %>"   maxlength="50" />
	                                       	 <%}else{ %>
	                                       	  <input name="<%=EopAttendanceMaintenance.CONTACT_NUMBER %>" id="<%=EopAttendanceMaintenance.CONTACT_NUMBER %>" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(candidate.getContactNumber())%>"  maxlength="50"  />
	                                       	 <%} %>
	                                      	</td>                                    			   
                                      	</tr>
                                      	 <tr>                          
	                                   		<td><%if(localeObj==null){%><span style="color:#ec2028;">* </span><%} %><label><%=localeObj!=null ?localeObj.getTranslatedText("Email") : "电子邮件(我们会将活动信息发送到您的邮箱)"%></label></td>
	                                     	<td>
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		
					                          %>	
	                                       	 <input name="<%=EopAttendanceMaintenance.MAILID %>" id="<%=EopAttendanceMaintenance.MAILID %>" type="text" class="textObj" value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.MAILID)) %>"   />
	                                       	 <%}else{ %>
	                                       	  <input name="<%=EopAttendanceMaintenance.MAILID %>" id="<%=EopAttendanceMaintenance.MAILID %>" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(email)%>"  />
	                                       	 <%} %>
	                                      	</td>                                    			   
                                      	</tr>
                            		     <tr>                          
	                                   		<td><label><%=localeObj!=null ?localeObj.getTranslatedText("Education") : "学历"%></label></td>
	                                     	<td>
	                                     		<%
                            				String edu = "";
                            				if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
                            					edu = request.getParameter(EopAttendanceMaintenance.EDUCATION);
                            				}else{
                            					edu = education;
                            				}
                            				%>
	                                       	 	<select name="<%=EopAttendanceMaintenance.EDUCATION %>" id="<%=EopAttendanceMaintenance.EDUCATION %>" class="comboObj" >
                               						<option value="0"><%=localeObj!=null ?localeObj.getTranslatedText("Select"):"请选择" %></option>
                    								<option value="1"  <%="1".equals(edu)?"selected":"" %>><%=localeObj!=null ?localeObj.getTranslatedText("Degree") : "大学"%></option>	
                               						<option value="2"  <%="2".equals(edu)?"selected":"" %>><%=localeObj!=null  ? localeObj.getTranslatedText("Post-Graduate") : "研究生或以上"%></option>   
                               						<option value="3"  <%="3".equals(edu)?"selected":"" %>><%=localeObj!=null  ? localeObj.getTranslatedText("Junior College") : "大专"%></option>   
                               						<option value="4"  <%="4".equals(edu)?"selected":"" %>><%=localeObj!=null  ? localeObj.getTranslatedText("HighSchool") : "中专或高中"%></option>   
                               						<option value="5"  <%="5".equals(edu)?"selected":"" %>><%=localeObj!=null  ? localeObj.getTranslatedText("Secondary and below") : "初中或以下"%></option> 
                               						<option value="6"  <%="6".equals(edu)?"selected":"" %>><%=localeObj!=null  ? localeObj.getTranslatedText("Unknown") : "不详"%></option>                                       	
                               	 			</select>
	                                      	</td>                                    			   
                                      	</tr>
                                      		 <tr><td><label><%=localeObj!=null ?localeObj.getTranslatedText("WeChat ID") : "微信(方便我们微信与您联系)"%></label></td>
	                                     	<td>
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		
					                          %>	
	                                       	 <input name="<%=EopAttendanceMaintenance.WECHATID %>" id="<%=EopAttendanceMaintenance.WECHATID %>" type="text" class="textObj" value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.WECHATID)) %>"   />
	                                       	 <%}else{ %>
	                                       	  <input name="<%=EopAttendanceMaintenance.WECHATID %>" id="<%=EopAttendanceMaintenance.WECHATID %>" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(weChat)%>"  />
	                                       	 <%} %>
	                                      	</td>                                    			   
                                      	</tr>
                                      		
  										 <tr>                          
	                                   		<td><label><%=localeObj!=null ?localeObj.getTranslatedText("NRIC") : "证件号码"%></label></td>
	                                     	<td>
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		
					                          %>	
	                                       	 <input name="<%=EopAttendanceMaintenance.NRIC %>" id="<%=EopAttendanceMaintenance.NRIC %>" type="text" class="textObj" value="<%=SecurityAPI.encodeHTML(request.getParameter(EopAttendanceMaintenance.NRIC)) %>"  />
	                                       	 <%}else{ %>
	                                       	  <input name="<%=EopAttendanceMaintenance.NRIC %>" id="<%=EopAttendanceMaintenance.NRIC %>" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(nric)%>"  />
	                                       	 <%} %>
	                                      	</td>                                    			   
                                      	</tr>
                                      
                            			<tr>                         
                          				<td>&nbsp;</td>	</tr>
                                  		   <tr>                         
                                			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                                			<%if(modifyFlag){%>
                                			<a href="javascript:submitForm()" class="btn1" id="submitBtn"><%=localeObj!=null ?localeObj.getTranslatedText("Modify") : "修改"%></a> 
                                			<%}else{ %>
                                			<a href="javascript:submitForm()" class="btn1" id="submitBtn"><%=localeObj!=null ?localeObj.getTranslatedText("Submit") : "报名"%></a>
                                			<%} %>
                                				<%if(userObj!=null){ %>
                    		 					<a href="ContentManager?key=EopAttendanceDetails" class="ML10 btn1"><%=localeObj!=null ?localeObj.getTranslatedText("Back") : "向后"%></a> 
                    		 					<%} %>
                               	 			</td>                                     			   
                                      	 </tr>
                            		 	 <tr >
								              <td style="border: none;"></td>
								              <td style="border: none;"></td>
								              <td style="border: none;"></td>
									    </tr>
						                <tr >
							              <td style="border: none;"></td>
							              <td style="border: none;"></td>
							              <td style="border: none;"></td>
						               </tr>			
                                      </table>
                                      </form>
                                       				
					</div>
				</div>
	
	
     