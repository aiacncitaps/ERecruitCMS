<%--
  - Author(s):          Jinatmayee
  - Date:               12-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Adding New Interview
--%>
<%@page import="com.quix.aia.cn.imo.mapper.InterviewMaintenance"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.data.interview.Interview"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.utilities.ErrorObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ page import='com.quix.aia.cn.imo.data.user.*'%>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes'%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>

<%
int bu=0;
int dis=0;
int branch=0;
String city=0+"";
String ssc=0+"";
String office=0+"";

User userObj = ((com.quix.aia.cn.imo.data.user.User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ));
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
ImoUtilityData imoUtility=new ImoUtilityData();

Interview interviewObj = new Interview();
interviewObj = (Interview)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);

boolean modifyFlag = false;
FormObj formDetail = null;
if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
}

String interviewType="";
String interviewName="";
String interviewDate="";
String location= "";
String interviewMaterial= "";
String estCand= "0";
String createdBy=userObj.getStaffName();

bu=userObj.getBuCode();
dis=userObj.getDistrict();
city=userObj.getCityCode()+"";
ssc=userObj.getSscCode()+"";
branch=userObj.getBranchCode();
office=userObj.getOfficeCode()+"";
if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	 modifyFlag = true;
	 interviewType=SecurityAPI.encodeHTML(interviewObj.getInterviewType());
	 interviewName=SecurityAPI.encodeHTML(interviewObj.getInterviewSessionName());
	 interviewDate=LMSUtil.convertDateToString(interviewObj.getInterviewDate());
	 location= SecurityAPI.encodeHTML(interviewObj.getLocation());
	 interviewMaterial= SecurityAPI.encodeHTML(interviewObj.getInterviewMaterial());
	 estCand= String.valueOf(interviewObj.getEstimatedCondidates());
	 bu=interviewObj.getBuCode();
	 dis=interviewObj.getDistrict();
	 branch=interviewObj.getBranchCode();
	 city=interviewObj.getCityCode();
	 ssc=interviewObj.getSscCode();
	 office=interviewObj.getOfficeCode();
	 createdBy=interviewObj.getCreatedBy();
}
if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){
	 interviewType=SecurityAPI.encodeHTML(request.getParameter("interviewType"));
	 interviewName=SecurityAPI.encodeHTML(request.getParameter("interviewName"));
	 interviewDate=SecurityAPI.encodeHTML(request.getParameter("interviewDate"));
	 location= SecurityAPI.encodeHTML(request.getParameter("location"));
	 interviewMaterial= SecurityAPI.encodeHTML(request.getParameter("interviewMaterial"));
	 estCand= SecurityAPI.encodeHTML(request.getParameter("estCand"));
	 bu=Integer.parseInt(request.getParameter("bu"));
	 dis=Integer.parseInt(request.getParameter("district"));
	 
	 branch=Integer.parseInt(request.getParameter("branch"));
	 city=request.getParameter("city");
	 ssc=request.getParameter("ssc");
	 office=request.getParameter("office");
	 createdBy=request.getParameter("createdBy");
}


%>

<script type="text/javascript">
$(document).ready(function(){
	<%
	
	if(userObj.getUserType().equals("AD") ){ %> 
			getBU('<%=bu %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ){ %>
					getDistrict('<%=bu %>','<%=dis %>','L');
				
				<% }%>
				
						<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isBranchLevel() ){ %>
					getBranch('<%=dis %>','<%=branch %>','L');
			
				<% }%>
			
				<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
				 
				getCity('<%=branch %>','<%=city %>','<%=dis %>','L');
			<% }%>
			
			<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
				
				getSSC('<%=city %>','<%=ssc %>','L');
			<% }%>
			
			<%if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
			
			getOffice('<%=ssc %>','<%=office %>');
		<% }%>

		
});
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
    $( ".datepicker" ).datepicker({
    	showOn: "button",
    	buttonImage: "images/calendar_icon.gif",
    	buttonImageOnly: true,
    	dateFormat:"dd/mm/yy",
    	changeMonth: true, 
    	changeYear: true,
    	minDate: 0
    	});
});
function interviewFormSubmit ( action )
{
  document.interviewForm.actionType.value = action ;
  document.interviewForm.submit() ;
 }
 /* function onlyNumbers(obj) {
	
	if (isNaN(obj.value))
	{
		
		$(obj)[0].value="";
	}
}  */
 function uploadFile(){
		$('#ajaxLoader').find(".lightbox").show();
		
		if(isAjaxUploadSupported()){
		
			if($('input[type=file]').get(0).files[0] !=undefined){
				var topicFile = $('input[type=file]').get(0).files[0];
				
				var file_extension=topicFile.name;
				
				if((file_extension.indexOf('.pdf')>-1) || (file_extension.indexOf('.PDF')>-1)
						|| (file_extension.indexOf('.jpg')>-1) || (file_extension.indexOf('.JPG')>-1)
						|| (file_extension.indexOf('.png')>-1)|| (file_extension.indexOf('.PNG')>-1)
						|| (file_extension.indexOf('.gif')>-1)|| (file_extension.indexOf('.GIF')>-1)){
				//	showProgress();
				var fd = new FormData();
				fd.append('InterviewTopicFile', topicFile);
				var fileName=$("#InterviewTopicFile").val();
				var size=$('input[type=file]').get(0).files[0].size;
				if(size<=5242880){
				  $.ajax({
						url : 'UploadMaterial?image_name='+fileName,
						type: "POST",
						data: fd,
					   	processData: false,
					   	contentType: false,
					}).done(function(respond){
						$('#ajaxLoader').find(".lightbox").hide();
					});
				}else{
					$('#ajaxLoader').find(".lightbox").hide();
					alert("Please Upload File Less then 5 MB");
					$("#topic_file").val('');
				}  
				 // hideProgress();
				 // alert("file Uploaded Successfully");
				 
				}
				else{
					$('#ajaxLoader').find(".lightbox").hide();
					alert("Please Upload a PDF or Image File");
				}
		   }
		  else{
			  $('#ajaxLoader').find(".lightbox").hide();
				alert("Please Upload a file");
		   }
		}else{
			var material_name=$("#InterviewTopicFile").val();
			if('' != material_name){
				if((material_name.indexOf('.pdf')>-1) || (material_name.indexOf('.PDF')>-1))
				{
				
						var iframe = document.createElement("iframe");
						iframe.setAttribute("name", "upload_iframe_myFile");
						iframe.setAttribute("id", "upload_iframe_myFile");
				        iframe.setAttribute("width", "0");
				        iframe.setAttribute("height", "0");
				        iframe.setAttribute("border", "2px");
				        iframe.setAttribute("src","javascript:false;");
				        iframe.style.display = "none";
			
				        var form = document.createElement("form");
				        form.setAttribute("target", "upload_iframe_myFile");
				        form.setAttribute("action", "UploadMaterial?image_name="+material_name); //change page to post
				        form.setAttribute("method", "post");
				        form.setAttribute("enctype", "multipart/form-data");
				        form.setAttribute("encoding", "multipart/form-data");
				        form.style.display = "none";
			
				        var files = document.getElementById("InterviewTopicFile");
				        files.style.display = "none";
				        
				        form.appendChild(files);
				        document.body.appendChild(form);
				        document.body.appendChild(iframe);
				        iframeIdmyFile = document.getElementById("upload_iframe_myFile");
			
				        // Add event...
				        var eventHandlermyFile = function () {
				            if (iframeIdmyFile.detachEvent) 
				                iframeIdmyFile.detachEvent("onload", eventHandlermyFile);
				            else 
				                iframeIdmyFile.removeEventListener("load", eventHandlermyFile, false);
			
				            response = getIframeContentJSON(iframeIdmyFile);
				        }
			
				        if (iframeIdmyFile.addEventListener) 
				            iframeIdmyFile.addEventListener("load", eventHandlermyFile, true);
				        if (iframeIdmyFile.attachEvent) 
				            iframeIdmyFile.attachEvent("onload", eventHandlermyFile);
			
				        form.submit();
				        
				        var announcementMaterialTD = document.getElementById("InterviewTopicFileTD");
				        announcementMaterialTD.appendChild(files);
				        files.style.display = "block";
	
				}else{
					$('#ajaxLoader').find(".lightbox").hide();
					alert("Please Upload a PDF or Image File");
				}   

			}else{
				
				$('#ajaxLoader').find(".lightbox").hide();
				alert("Please Upload a file");
			} 

			$('#ajaxLoader').find(".lightbox").hide();

//	         return;
		}
	 }
</script>


	<div id="maincontainer">

		<div class="head">
			<h2 align="center">
				<%if(modifyFlag == true){%>
				<%=localeObj.getTranslatedText("Edit Interview Schedule")%>
                <%}else{%>
				<%=localeObj.getTranslatedText("Add Interview Schedule")%>
                <%}%>
			</h2>
		</div>
		<div class="content" style="background-color: #ffffff;">
			<form name="interviewForm" method="post" action="FormManager"
				class="PT20">
				<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
				
				
				<input type="hidden" name="actionType" />
				<table class="formDesign">
					<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
					<tr>
						<td colspan="2" style="text-align: center"><span
							style="color: #ec2028;"><%=localeObj.getTranslatedText(((ErrorObject)request.getAttribute(RequestAttributes.ERROR_OBJ)).getErrorMsg())%></span>
						</td>
					</tr>

					<%}%>
					<tr>
					<tr>
						<td><span style="color: #ec2028;">* </span> <label><%=localeObj.getTranslatedText("Interview Type")%></label>
						</td>
						<td><select name="interviewType" id="interviewType"
							class="comboObj">
								<option value="2nd"  <%=interviewType.equals("2nd")?"selected" :"" %>      ><%=localeObj.getTranslatedText("2nd Interview")%></option>
								<option value="3rd" <%=interviewType.equals("3rd")?"selected" :"" %> ><%=localeObj.getTranslatedText("3rd Interview")%></option>
						</select></td>
					</tr>


					<tr>
						<td><span style="color: #ec2028;">* </span> <label><%=localeObj.getTranslatedText("Interview Session Name")%></label>
						</td>
						<td><input name="interviewName" id="interviewName"
							type="text" class="textObj" value="<%=interviewName%>"
							maxlength="250" /></td>
					</tr>

					<tr>
						<td><span style="color: #ec2028;">* </span> <label><%=localeObj.getTranslatedText("Interview Date")%></label>
						</td>
						<td><input name="interviewDate" id="interviewDate"
							type="text" class="textObj datepicker" value="<%=interviewDate%>"
							readonly="readonly" /></td>
					</tr>



					<tr>
						<td><span style="color: #ec2028;">* </span> <label></label> <label><%=localeObj.getTranslatedText("Start Time")%></label></td>

						<%
	                                    			String startTime = "",timeFromHour = "09",timeFromMinute = "00",timeFromAMPM = "AM";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		timeFromHour = SecurityAPI.encodeHTML(request.getParameter("timeFromHour"));
						                          		timeFromMinute = SecurityAPI.encodeHTML(request.getParameter("timeFromMinute"));
						                          		timeFromAMPM = SecurityAPI.encodeHTML(request.getParameter("timeFromAMPM"));
						                          		
						                          	}
						                          	else{
						                          		startTime = interviewObj.getStartTime()!=null?LMSUtil.converDateIntoHHMMAMPM(interviewObj.getStartTime()):"";
						                          		if(interviewObj.getStartTime()!=null){
							                          		String stime[] = startTime.split(":");
							                          		timeFromHour = stime[0];
							                          		timeFromMinute = stime[1].split(" ")[0];
							                          		timeFromAMPM = stime[1].split(" ")[1];
						                          		}
						                          	}
					                          		 %>
						<td><select name="timeFromHour" id="timeFromHour"
							class="comboObj" style="width:23%;">

								<option value="01" <%=timeFromHour.equals("01")?"selected" :""%>>1</option>
								<option value="02" <%=timeFromHour.equals("02")?"selected" :""%>>2</option>
								<option value="03" <%=timeFromHour.equals("03")?"selected" :""%>>3</option>
								<option value="04" <%=timeFromHour.equals("04")?"selected" :""%>>4</option>
								<option value="05" <%=timeFromHour.equals("05")?"selected" :""%>>5</option>
								<option value="06" <%=timeFromHour.equals("06")?"selected" :""%>>6</option>
								<option value="07" <%=timeFromHour.equals("07")?"selected" :""%>>7</option>
								<option value="08" <%=timeFromHour.equals("08")?"selected" :""%>>8</option>
								<option value="09" <%=timeFromHour.equals("09")?"selected" :""%>>9</option>
								<option value="10" <%=timeFromHour.equals("10")?"selected" :""%>>10</option>
								<option value="11" <%=timeFromHour.equals("11")?"selected" :""%>>11</option>
								<option value="12" <%=timeFromHour.equals("12")?"selected" :""%>>12</option>
						</select> HH <select name="timeFromMinute" id="timeFromMinute"
							class="comboObj" style="width:23%;">

								<option value="00"
									<%=timeFromMinute.equals("00")?"selected" :""%>>00</option>
								<option value="15"
									<%=timeFromMinute.equals("15")?"selected" :""%>>15</option>
								<option value="30"
									<%=timeFromMinute.equals("30")?"selected" :""%>>30</option>
								<option value="45"
									<%=timeFromMinute.equals("45")?"selected" :""%>>45</option>
						</select> MM <select name="timeFromAMPM" id="timeFromAMPM"
							class="comboObj" style="width:23%;">

								<option value="AM" <%=timeFromAMPM.equals("AM")?"selected" :""%>><%=localeObj.getTranslatedText("AM")%></option>
								<option value="PM" <%=timeFromAMPM.equals("PM")?"selected" :""%>><%=localeObj.getTranslatedText("PM")%></option>
						</select></td>
					</tr>

					<tr>
						<td><span style="color: #ec2028;">* </span> <label><%=localeObj.getTranslatedText("End Time")%></label>
						</td>
						<%
	                                    			String endTime = "",timeToHour = "05",timeToMinute = "00",timeToAMPM = "PM";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		timeToHour = SecurityAPI.encodeHTML(request.getParameter("timeToHour"));
						                          		timeToMinute = SecurityAPI.encodeHTML(request.getParameter("timeToMinute"));
						                          		timeToAMPM = SecurityAPI.encodeHTML(request.getParameter("timeToAMPM"));
						                          		
						                          	}else{
						                          		endTime = interviewObj.getEndTime()!=null?LMSUtil.converDateIntoHHMMAMPM(interviewObj.getEndTime()):"";
						                          		if(interviewObj.getEndTime()!=null){
							                          		String etime[] = endTime.split(":");
							                          		timeToHour = etime[0];
							                          		timeToMinute = etime[1].split(" ")[0];
							                          		timeToAMPM = etime[1].split(" ")[1];
						                          		}
						                          	}
						                        	
					                          		 %>
						<td><select name="timeToHour" id="timeToHour"
							class="comboObj" style="width:23%;">

								<option value="01" <%=timeToHour.equals("01")?"selected" :""%>>1</option>
								<option value="02" <%=timeToHour.equals("02")?"selected" :""%>>2</option>
								<option value="03" <%=timeToHour.equals("03")?"selected" :""%>>3</option>
								<option value="04" <%=timeToHour.equals("04")?"selected" :""%>>4</option>
								<option value="05" <%=timeToHour.equals("05")?"selected" :""%>>5</option>
								<option value="06" <%=timeToHour.equals("06")?"selected" :""%>>6</option>
								<option value="07" <%=timeToHour.equals("07")?"selected" :""%>>7</option>
								<option value="08" <%=timeToHour.equals("08")?"selected" :""%>>8</option>
								<option value="09" <%=timeToHour.equals("09")?"selected" :""%>>9</option>
								<option value="10" <%=timeToHour.equals("10")?"selected" :""%>>10</option>
								<option value="11" <%=timeToHour.equals("11")?"selected" :""%>>11</option>
								<option value="12" <%=timeToHour.equals("12")?"selected" :""%>>12</option>
						</select> HH <select name="timeToMinute" id="timeToMinute"
							class="comboObj" style="width:23%;">

								<option value="00" <%=timeToMinute.equals("00")?"selected" :""%>>00</option>
								<option value="15" <%=timeToMinute.equals("15")?"selected" :""%>>15</option>
								<option value="30" <%=timeToMinute.equals("30")?"selected" :""%>>30</option>
								<option value="45" <%=timeToMinute.equals("45")?"selected" :""%>>45</option>
						</select> MM <select name="timeToAMPM" id="timeToAMPM"
							class="comboObj" style="width:23%;">

								<option value="AM" <%=timeToAMPM.equals("AM")?"selected" :""%>><%=localeObj.getTranslatedText("AM")%></option>
								<option value="PM" <%=timeToAMPM.equals("PM")?"selected" :""%>><%=localeObj.getTranslatedText("PM")%></option>
						</select></td>
					</tr>



					<tr>
						<td style="vertical-align:middle"><span style="color: #ec2028;">* </span> <label><%=localeObj.getTranslatedText("Location")%></label>
						</td>
						<td><textarea name="location" id="location" class="textObj"
								rows="5" cols="5" maxlength="250"><%=location%></textarea></td>
					</tr>


					<tr>
						<td><span style="color: #ec2028;">* </span> <label><%=localeObj.getTranslatedText("Interview Material")%></label>
						</td>
						<td><input name="interviewMaterial" id="interviewMaterial"
							type="text" class="textObj" value="<%=interviewMaterial%>" placeholder="<%=localeObj.getTranslatedText("Required to be included in the interview carried by material")%>"
							maxlength="250" /></td>
					</tr>




					<tr>
						<td><label><span style="color:#ec2028;">* </span><%=localeObj.getTranslatedText("Estimated Candidates")%></label>


						</td>
						<td><input name="estCand" id="estCand" type="text" class="textObj" value="<%=estCand%>" maxlength="10"/></td>
							
					</tr>




					<tr>
						<td><span style="color: #ec2028;">* </span><label><%=localeObj.getTranslatedText("BU")%></label>
						</td>
						<td>
							<% if(userObj.getUserType().equals("AD")){ %> <select name="bu"
							id="bu" class="comboObj"
							onchange="getDistrict(this.value,'<%=dis %>','E');">
								<option value="0"><%=localeObj.getTranslatedText("Select")%></option>
						</select> <% }else{ %> <select name="bu" id="bu" class="comboObj">
								<option value="<%=userObj.getBuCode() %>"><%=imoUtility.getBuCode(userObj.getBuCode()) %></option>
						</select> <% }%>
						</td>
					</tr>

					<tr>
						<td><label><%=localeObj.getTranslatedText("District")%></label>
						</td>
						<td>
							<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel()){ %>
							<select name="district" id="district" class="comboObj"
							onchange="getBranch(this.value,'<%=branch %>','E');">
								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
						</select> <% }else{ %> <select name="district" id="district" class="comboObj">
								<option value="<%=userObj.getDistrict() %>"><%= imoUtility.getDistCode(userObj.getDistrict()) %></option>
						</select> <% }%>
						</td>
					</tr>
					
					
					<tr>
						<td><label><%=localeObj.getTranslatedText("Branch")%></label>
						</td>
						<td>
							<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ||  userObj.isDistrictLevel()){ %>
							<select name="branch" id="branch" class="comboObj"
							onchange="getCity(this.value,'<%=city %>',$('#district').val(),'E');">
								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
						</select> <% }else{ %> <select name="branch" id="branch" class="comboObj">
								<option value="<%=userObj.getBranchCode() %>"><%= imoUtility.getBranchCode(userObj.getBranchCode()) %></option>
						</select> <% }%>
						</td>
					</tr>

					<tr>
						<td><label><%=localeObj.getTranslatedText("City")%></label>
						</td>
						<td>
							<% if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
							<select name="city" id="city" class="comboObj"
							onchange="getSSC(this.value,'<%=ssc %>');">
								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
						</select> <% }else{ %> <select name="city" id="city" class="comboObj">
								<option value="<%=userObj.getCityCode() %>"><%=imoUtility.getCityName(userObj.getCityCode()) %></option>
						</select> <% }%>
						</td>
					</tr>
					
							
					

					<tr>
						<td><label><%=localeObj.getTranslatedText("SSC")%></label>
						</td>
						<td>
							<% if(userObj.getUserType().equals("AD") || userObj.isCityLevel() ||  userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
							<select name="ssc" id="ssc" class="comboObj" onchange="getOffice(this.value,'<%=office %>');" >
								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
						</select> <% }else{ %> <select name="ssc" id="ssc" class="comboObj">
								<option value="<%=userObj.getSscCode() %>"><%=imoUtility.getSscName(userObj.getSscCode()) %></option>
						</select> <% }%>
						</td>
					</tr>

							<tr  >                         
                   				<td style="border: none;"  >                                  				
                    				<label ><%=localeObj.getTranslatedText("Office")%></label>
                   				</td>
                       			<td>
                       			<% if(userObj.getUserType().equals("AD") || userObj.isCityLevel() ||  userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                       				<select name="office" id="office"  class="comboObj" >
                      						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                      	 				 </select>
                      	 				 <%}else{ %>
                      	 				 <select name="office" id="office"  class="comboObj" >
                      						<option value="<%=userObj.getOfficeCode() %>" ><%=imoUtility.getOfficeName(userObj.getOfficeCode()) %></option>	                                       	
                      	 				 </select>
                      	 				 <%} %>
                      	 			</td>                                     			   
                     			    </tr>
                     			    
                     			    
					<tr>
						<td><label><%=localeObj.getTranslatedText("TOPIC UPLOAD")%></label>
						</br><label><%=localeObj.getTranslatedText("Maximum 5MB")%></label>
						</td>
						<td id="InterviewTopicFileTD"><input type="file" name="InterviewTopicFile" id="InterviewTopicFile"
							class="fileObj" onChange="javascript:uploadFile()" /></td>
					</tr>
					<tr>
						<td></td>
						<%String fname = ""; 
						String fpath="#";
						InterviewMaintenance interMain=new InterviewMaintenance();
											 if(interviewObj.getAttachmentPath()!=null && interviewObj.getAttachmentPath().length() > 0){
												 fname = interviewObj.getAttachmentPath().substring(interviewObj.getAttachmentPath().lastIndexOf('/') + 1);
												 fpath=interMain.getmaterialFile(interviewObj,request);
											
						%>
						<td style="border: none;text-align:left;"><a href="<%=fpath %> " target="_new"  ><div id="materialname"><%=fname %></div></a></td>
						
						<%}%>
						
						
					</tr>

<!-- 					<tr> -->
<%-- 						<td><span style="color: #ec2028;">* </span> <label><%=localeObj.getTranslatedText("Created By")%></label> --%>
<!-- 						</td> -->
<!-- 						<td><input name="createdBy" id="createdBy" type="text" -->
<%-- 							class="textObj" value="<%=createdBy%>" readonly="readonly" /></td> --%>
<!-- 					</tr> -->

					<tr>
						<td colspan="2" class="MT30 MB30"
							style="text-align: center; padding-top: 20px;">
							<%if(modifyFlag == true){%> <a href="#" class="btn1 "
							onclick="javascript:interviewFormSubmit('<%=FormInfo.SUBMIT%>')"><%=localeObj.getTranslatedText("Modify")%>
						</a> <%}else{%> <a href="#" class="btn1 " style="margin-left: -2%;"
							onclick="javascript:interviewFormSubmit('<%=FormInfo.SUBMIT%>')"><%=localeObj.getTranslatedText("Submit")%>
						</a> <%}%> <a href="ContentManager?key=InterviewMaintenance"
							class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a>
						</td>
					</tr>

				</table>
				<br>
			</form>
		</div>
	</div>

