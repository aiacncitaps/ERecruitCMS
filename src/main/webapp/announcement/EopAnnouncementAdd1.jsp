<%--
  - Author(s):          Nibedita
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:         Announcement Creation
--%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="com.quix.aia.cn.imo.mapper.LogsMaintenance"%>
<%@page import="java.io.File"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.data.announcement.Announcement"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.mapper.AnnouncementMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="java.util.Date"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<%
		try{
%>
<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
User userObj = (User)request.getSession().getAttribute("currUserObj");

boolean modifyFlag = false;
FormObj formDetail = null;
Announcement announcement = new Announcement();
if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
}
announcement = (Announcement)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);

int buCode = 0,distCode = 0,branch=0;
String cityCode = 0+"",sscCode = 0+"",office=0+"";
buCode=userObj.getBuCode();
distCode=userObj.getDistrict();
cityCode=userObj.getCityCode()+"";
sscCode=userObj.getSscCode()+"";
branch=userObj.getBranchCode();
office=userObj.getOfficeCode()+"";
//This is modify
if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag = true;
	buCode = Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(announcement.getBuCode())));
	distCode = Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(announcement.getDistrict())));
	branch = Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(announcement.getBranchCode())));
	cityCode = SecurityAPI.encodeHTML(String.valueOf(announcement.getCityCode()));
	sscCode =SecurityAPI.encodeHTML(String.valueOf(announcement.getSscCode()));
	office = SecurityAPI.encodeHTML(String.valueOf(announcement.getOfficeCode()));
	
}

if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
	buCode = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter(AnnouncementMaintenance.BU_PARAM)));
	distCode = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter(AnnouncementMaintenance.DISTRICT_PARAM)));
	branch = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter("branch")));
	cityCode = SecurityAPI.encodeHTML(request.getParameter(AnnouncementMaintenance.CITY_PARAM));
	office = SecurityAPI.encodeHTML(request.getParameter("office"));
	sscCode = SecurityAPI.encodeHTML(request.getParameter(AnnouncementMaintenance.SSC_PARAM));
}
String fname = ""; 
String fpath="#";
AnnouncementMaintenance announcementMain=new AnnouncementMaintenance();
if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
	if(request.getParameter("uploadMaterialFile")!=null){
		fname = request.getParameter("uploadMaterialFile");
	 }
}else{
if(announcement.getAttachmentPath()!=null && announcement.getAttachmentPath().length() > 0){
	 fname = announcement.getAttachmentPath().substring(announcement.getAttachmentPath().lastIndexOf('/') + 1);
	 fpath=announcementMain.getmaterialFile(announcement,request);
	
}
}
ImoUtilityData imoUtilityDate=new ImoUtilityData();
%>

<script type="text/javascript">//<![CDATA[
   
function submitForm(){
	document.announcementForm.submit();
	
}
             
</script>
<script>
$( document ).ready(function() {
	
	<%
	if(userObj.getUserType().equals("AD") ){ %> 
			getBU('<%=buCode %>','L');
			
	<% }%>
	<%if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ){ %>
					getDistrict('<%=buCode %>','<%=distCode %>','L');
				
				<% }%>
				
						<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isBranchLevel() ){ %>
					getBranch('<%=distCode %>','<%=branch %>','L');
			
				<% }%>
			
				<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
				 
				getCity('<%=distCode %>','<%=cityCode %>','L');
			<% }%>
			
			<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
				
				getSSC('<%=cityCode %>','<%=sscCode %>','L');
			<% }%>
			
			<%if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
			
			getOffice('<%=sscCode %>','<%=office %>');
		<% }%>
	
$('#materialname').html('<%=fname%>');
$('#uploadMaterialFile').val('<%=fname%>');
});
$(function() {
	 $( ".datepicker" ).datepicker({
     	showOn: "button",
     	buttonImage: "images/calendar_icon.gif",
     	buttonImageOnly: true,
     	dateFormat:"dd/mm/yy",
     	changeMonth: true, 
    	changeYear: true
     	});
	
});
function uploadMaterial(){
	
	$('#ajaxLoader').find(".lightbox").show();
	
	if(isAjaxUploadSupported()){
		if($('input[type=file]').get(0).files[0] !=undefined){
			var materialFile = $('input[type=file]').get(0).files[0];
			var file_name=materialFile.name;
			/* if((file_name.indexOf('.pdf')>-1) || (file_name.indexOf('.PDF')>-1)
				|| (file_name.indexOf('.ppt')>-1) || (file_name.indexOf('.PPT')>-1)
				|| (file_name.indexOf('.jpg')>-1) || (file_name.indexOf('.JPG')>-1)
				|| (file_name.indexOf('.jpeg')>-1) || (file_name.indexOf('.JPEG')>-1)
				) */
				if((file_name.indexOf('.pdf')>-1) || (file_name.indexOf('.PDF')>-1))
						
			{
			if(materialFile==undefined)
				materialFile = $('#materialname').html();
			var fd = new FormData();
				fd.append('AnnouncementMaterial', materialFile);
				var material_name=$("#AnnouncementMaterial").val();
				$('#uploadMaterialFile').val(file_name);
				var size=$('input[type=file]').get(0).files[0].size;
				if(size<=5242880){
				
			  $.ajax({
					url : 'UploadMaterial?usedFor=Announcement&image_name='+material_name,
					type: "POST",
					data: fd,
				   	processData: false,
				   	contentType: false,
				}).done(function(respond){
					//window.location.href = "ContentManager?key=AnnouncementMaintenance";
					
					$('#materialname').html(file_name);
					$('#ajaxLoader').find(".lightbox").hide();
				});
				}else{
					alert("Please Upload File Less then 5 MB");
					$('#AnnouncementMaterial').val('');
				} 
			}else{
				alert("Please Upload file with extension .pdf");
				$('#uploadMaterialFile').val('');
				
				if(document.getElementById('AnnouncementMaterial') != null) 
					 document.getElementById('AnnouncementMaterial').outerHTML = document.getElementById('AnnouncementMaterial').outerHTML;
				if('<%=modifyFlag%>' == 'true')
					$('#materialname').html('<%=fname%>');
				else
					$('#materialname').html('');
			}
		}else{
			
			alert("Please Upload a file");
			$('#uploadMaterialFile').val('');
		}
	}else{
		var material_name=$("#AnnouncementMaterial").val();
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
		        form.setAttribute("action", "UploadMaterial?usedFor=Announcement&image_name="+material_name); //change page to post
		        form.setAttribute("method", "post");
		        form.setAttribute("enctype", "multipart/form-data");
		        form.setAttribute("encoding", "multipart/form-data");
		        form.style.display = "none";
		
		        var files = document.getElementById("AnnouncementMaterial");
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
		        
		        var announcementMaterialTD = document.getElementById("AnnouncementMaterialTD");
		        announcementMaterialTD.appendChild(files);
		        files.style.display = "block";
			}else{
				alert("Please Upload file with extension .pdf");
				$('#uploadMaterialFile').val('');
				
				if(document.getElementById('AnnouncementMaterial') != null) 
					 document.getElementById('AnnouncementMaterial').outerHTML = document.getElementById('AnnouncementMaterial').outerHTML;
				if('<%=modifyFlag%>' == 'true')
					$('#materialname').html('<%=fname%>');
				else
					$('#materialname').html('');
			}  

		}else{
			
			alert("Please Upload a file");
			$('#uploadMaterialFile').val('');
		}

//         return;
	}
	$('#ajaxLoader').find(".lightbox").hide();
	
}
 

</script>

   	<div id="maincontainer">	
   					<div class="head">
   					<h2 align="center"> <%if(modifyFlag == true){%>
   					<%=localeObj.getTranslatedText("EOP Edit Announcement")%>
   					<%}else{ %>
   					<%=localeObj.getTranslatedText("EOP Add Announcement")%>
   					<%} %>
   					</h2></div>  	
   							<div class="content" style="background-color:#ffffff;">
   								<form action="FormManager" name="announcementForm" method="post"  class="PT20">
   								  <input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
   								   <input type="hidden" name="<%=FormInfo.HIDDEN_ACTION%>" value="SUBMIT"/> 
                           			<table class="formDesign">
                           				   <tr>
	                         				 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) == null){
					                          %>
					                           <td colspan="2" style="text-align: center"><span style="color:#ec2028;"></span></td>
					                          <%
					                          	}else{
					                          %>
					                          <td colspan="2" style="text-align: center"><span style="color:#ec2028;">
					                          &nbsp;<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%></span></td>
					                          <%
					                          	}
					                          %>
	                      				  </tr>
                                				
										<tr>                          
	                                   		<td><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Subject")%></label></td>
	                                     	<td>
	                                     	 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		
					                          %>	
	                                       	 <input name="<%=AnnouncementMaintenance.SUBJECT_PARAM %>" id="<%=AnnouncementMaintenance.SUBJECT_PARAM %>" maxlength="250" type="text" class="textObj" value="<%=SecurityAPI.encodeHTML(request.getParameter(AnnouncementMaintenance.SUBJECT_PARAM )) %>"   />
	                                       	 <%}else{ %>
	                                       	  <input name="<%=AnnouncementMaintenance.SUBJECT_PARAM %>" id="<%=AnnouncementMaintenance.SUBJECT_PARAM %>" maxlength="250" type="text" class="textObj"    value="<%=SecurityAPI.encodeHTML(announcement.getSubject())%>"   />
	                                       	 <%} %>
	                                      	</td>                                    			   
                                      	</tr>
                     		 			<tr >                         
	                             			<td ><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Published Date")%></label></td>     
	                                 		<td>	
	                                 		 <%String publishDt = "";
	                                 		
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		publishDt = request.getParameter(AnnouncementMaintenance.PUBLISHED_DATE_PARAM );
					                          	}
				                          		else{
				                          			publishDt = announcement.getPublishedDate()!=null?LMSUtil.convertDateToString(announcement.getPublishedDate()):"";	
				                          		}
					                          %>
												<input name="<%=AnnouncementMaintenance.PUBLISHED_DATE_PARAM %>" id="<%=AnnouncementMaintenance.PUBLISHED_DATE_PARAM %>" type="text" class="textObj datepicker"  value="<%=SecurityAPI.encodeHTML(publishDt)%>"  readonly="readonly" />
	                                	 	</td>                                     			   
                               			 </tr>
                            		 				
                   		 				<tr>                         
	                           				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Expired Date")%></label></td>                                   				
	                               			<td>		
	                               			 <%String expDt = "";
	                                 		
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		expDt = request.getParameter(AnnouncementMaintenance.EXPIRED_DATE_PARAM );
					                          	}
				                          		else{
				                          			expDt = announcement.getExpDate()!=null?LMSUtil.convertDateToString(announcement.getExpDate()):"";	
				                          		}
					                          %>
												<input name="<%=AnnouncementMaintenance.EXPIRED_DATE_PARAM %>" id="<%=AnnouncementMaintenance.EXPIRED_DATE_PARAM %>" type="text" class="textObj datepicker"  value="<%=SecurityAPI.encodeHTML(expDt) %>" readonly="readonly" />
												
	                              	 		</td>                                     			   
                             			</tr>
                   		 				 <%
                   		 				 String messageType = "";
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		messageType = request.getParameter(AnnouncementMaintenance.MESSAGE_TYPE_PARAM);
					                          	}else{
					                          		messageType = SecurityAPI.encodeHTML(announcement.getMsgType());
					                          	}
					                     %>
                            		 	<tr>                         
                               				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Message Type")%></label></td>                                   				
                                 		   <td>	
                                 		   		<input type="radio" name="<%=AnnouncementMaintenance.MESSAGE_TYPE_PARAM %>" value="Normal" <%=null == messageType||"".equals(messageType)||messageType.equals("Normal")?"checked":"" %>/><%=localeObj.getTranslatedText("Normal")%>
                                 		   		<input type="radio" name="<%=AnnouncementMaintenance.MESSAGE_TYPE_PARAM %>" value="Important" <%=messageType.equals("Important")?"checked":"" %>/><%=localeObj.getTranslatedText("Important")%>
<%--                                  				<select name="" id="<%=AnnouncementMaintenance.MESSAGE_TYPE_PARAM %>"  class="comboObj" > --%>
<%--                                 				<option value="Normal" <%=messageType.equals("Normal")?"selected":"" %>>Normal</option>	 --%>
<%--                                 				<option value="Important" <%=messageType.equals("Important")?"selected":"" %>>Important</option>                                         	 --%>
<!--                                 	 			</select> -->
                                	 		</td>                                     			   
                                      	</tr>
                            		 				
                        		 		<tr>                         
                               				<td style="vertical-align:middle"><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Message")%></label></td>                               				
                                  			 <td>	
                                  				<%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){%>
                                  				<textarea name="<%=AnnouncementMaintenance.MESSAGE_PARAM %>" id="<%=AnnouncementMaintenance.MESSAGE_PARAM %>"   class="textObj" rows="5" cols="5" maxlength="1000" ><%=SecurityAPI.encodeHTML(request.getParameter(AnnouncementMaintenance.MESSAGE_PARAM))%></textarea>
                                  				<%}else{ %>
                                  				<textarea name="<%=AnnouncementMaintenance.MESSAGE_PARAM %>" id="<%=AnnouncementMaintenance.MESSAGE_PARAM %>"    class="textObj" rows="5" cols="5" maxlength="1000" ><%=SecurityAPI.encodeHTML(announcement.getMessage())%></textarea>
                                  				<%} %>
                                 	 		</td>                                     			   
                                  		 </tr>
                        		 				
                        		 		<tr>                         
                                			<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("BU")%></label></td>                                    				
                                  			 <td>		
                                        	<% if(userObj.getUserType().equals("AD")){ %>
                                				<select name="<%=AnnouncementMaintenance.BU_PARAM %>" id="<%=AnnouncementMaintenance.BU_PARAM %>"  class="comboObj"  onchange="getDistrict(this.value,0,'E');" >
                               						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>	 
                               	 				 </select>
                               	 				<%}else{ %>
                               	 				<select name="<%=AnnouncementMaintenance.BU_PARAM %>" id="<%=AnnouncementMaintenance.BU_PARAM %>"  class="comboObj" >
                               						<option value="<%=userObj.getBuCode() %>" ><%=imoUtilityDate.getBuCode(userObj.getBuCode()) %></option>
                               	 				 </select>
                               	 				<%} %>
                                 	 		</td>                                      			   
                                  		</tr>
                        		 		<tr>                         
                                			<td><label ><%=localeObj.getTranslatedText("District")%></label></td>                                     				
                                  			<td>	
                                  			<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel()){ %>
                                				<select name="<%=AnnouncementMaintenance.DISTRICT_PARAM %>" id="<%=AnnouncementMaintenance.DISTRICT_PARAM %>"  class="comboObj"  onchange="getBranch(this.value,0,'E');">
                               						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	 
                               	 				 </select>
                               	 			 <%}else{ %>
                               	 				 <select name="<%=AnnouncementMaintenance.DISTRICT_PARAM %>" id="<%=AnnouncementMaintenance.DISTRICT_PARAM %>"  class="comboObj">
                               						<option value="<%=userObj.getDistrict() %>" ><%= imoUtilityDate.getDistCode(userObj.getDistrict()) %></option> 
                               	 				 </select>
                               	 				 <%} %>
                                 	 		</td>                                     			   
                                  		</tr>	
                                  		
                                  		
                                  		<tr>                         
                         				<td>                                   				
                          				<label ><%=localeObj.getTranslatedText("Branch")%></label>
                         				</td>
                             			<td>	
                             			<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ||  userObj.isDistrictLevel()){ %>
                             				<select name="branch" id="branch" class="comboObj" onchange="getCity($('#district').val(),0,'E');" >
                            						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>                                  	
                            	 				 </select>
                            	 		<%}else{ %>
                            	 		<select name="branch" id="branch" class="comboObj">
                            						<option value="<%=userObj.getBranchCode() %>" ><%= imoUtilityDate.getBranchCode(userObj.getBranchCode()) %></option>                                 	
                            	 		 </select>
                            	 		<%} %>
                            	 	    </td>                                     			   
                           			    </tr>
                           			    
                           			    
                           			    
                        		 		 <tr>                         
                            				<td>                                      				
                             				<label ><%=localeObj.getTranslatedText("City")%> </label>
                            				</td>
                                			<td>
                                			<% if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                               				<select name="<%=AnnouncementMaintenance.CITY_PARAM %>" id="<%=AnnouncementMaintenance.CITY_PARAM %>"  class="comboObj"  onchange="getSSC(this.value,0,'E');" >
                              						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                              	 			 </select>
                              	 			 <%}else{ %>
                              	 			 <select name="<%=AnnouncementMaintenance.CITY_PARAM %>" id="<%=AnnouncementMaintenance.CITY_PARAM %>"  class="comboObj" >
                              						<option value="<%=userObj.getCityCode() %>" ><%=imoUtilityDate.getCityName(userObj.getCityCode()) %></option>	                                       	
                              	 			 </select>
                              	 			 <%} %>
                               	 			</td>                                   			   
                                  		 </tr>
                                  			 
                 		 				<tr>                         
                         				<td>                                    				
                          				<label ><%=localeObj.getTranslatedText("SSC")%> </label>
                         				</td>
                             			<td>
                             			<% if(userObj.getUserType().equals("AD") || userObj.isCityLevel() ||  userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
                             				<select name="<%=AnnouncementMaintenance.SSC_PARAM %>" id="<%=AnnouncementMaintenance.SSC_PARAM %>"  class="comboObj"  onchange="getOffice(this.value,0);">
                            						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                            	 			</select>
                            	 		<%}else{ %>
                            	 		<select name="<%=AnnouncementMaintenance.SSC_PARAM %>" id="<%=AnnouncementMaintenance.SSC_PARAM %>"  class="comboObj" >
                            						<option value="<%=userObj.getSscCode() %>" ><%=imoUtilityDate.getSscName(userObj.getSscCode()) %></option>                                       	
                            	 			</select>
                            	 		<%} %>
                            	 		</td>                                     			   
                           			    </tr>
                           			    		 
                                           <tr  >                         
                   				<td style="border: none;"  >                                  				
                    				<label ><%=localeObj.getTranslatedText("Office")%></label>
                   				</td>
                       			<td>
                       		<% if(userObj.getUserType().equals("AD") ||  userObj.isSscLevel() || userObj.isCityLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                       				<select name="office" id="office"  class="comboObj"  >
                      						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                      	 				 </select>
                      	 				 <%}else{ %>
                      	 				 <select name="office" id="office"  class="comboObj" >
                      						<option value="<%=userObj.getOfficeCode() %>" ><%=imoUtilityDate.getOfficeName(userObj.getOfficeCode()) %></option>	                                       	
                      	 				 </select>
                      	 				 <%} %>
                      	 			</td>                                     			   
                     			    </tr>
                 		 				<tr>                         
                         				<td>	                                    				
                          				<label ><%=localeObj.getTranslatedText("Attachment")%></label>
                          				</br><label><%=localeObj.getTranslatedText("Maximum 5MB")%></label>
                         				</td>
                             			<td id="AnnouncementMaterialTD" >	
                             				<input name="AnnouncementMaterial" id="AnnouncementMaterial" type="file" class="fileObj"  onchange="uploadMaterial();"/>                
                         					<!-- <div id="spinner">
       											Loading...
   											</div> -->
                            	 		</td> 
                           			    </tr>
                           			     <tr>
											 <td style="border: none;"><input type="hidden" name="uploadMaterialFile"  id="uploadMaterialFile" value="" /></td>
											<%--  <td  style="border: none;text-align:left;"><div id="materialname"><%=fname %></div></td> --%>
											
											<%
											if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  %>
												 <td  style="border: none;text-align:left;"><div id="materialname"><%=fname %></div></td>
											<% }else{
											if(announcement.getAttachmentPath()!=null && announcement.getAttachmentPath().length() > 0){ %>
												
												<td  style="border: none;text-align:left;"><a href="<%=fpath %> " target="_new"  ><div id="materialname"><%=fname %></div></a> </td>
											<%}
											}
											%>
											
											
											
											
											  
											 
										  </tr>
                           			    	<%
                           			    	String publishBy = "";
					                      if(modifyFlag){
					                       		publishBy = SecurityAPI.encodeHTML(announcement.getCreatedBy());
					                       	}
					                       	else{
					                       		publishBy = SecurityAPI.encodeHTML(userObj.getStaffName());
					                       	} 
					                       
					                        %>
					                       	
                  		 				<%-- <tr>                         
                          				<td>                                    				
                           				<label ><%=localeObj.getTranslatedText("Posted By")%></label>
                          				</td>
                              			<td style="border: none;text-align:left">	
                              				<input name="<%=AnnouncementMaintenance.POSTED_BY_PARAM %>" id="<%=AnnouncementMaintenance.POSTED_BY_PARAM %>" type="text" class="textObj"  value="<%=publishBy %>" disabled  />
                             	 	   </td>                                     			   
                            			</tr>
 --%>
                            			<tr>                         
                          				<td>&nbsp;</td>	</tr>
                                  		   <tr>                         
                                			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                                		<%// expiry records can viwed only no update
                                			Date todayDate = new Date();
                                			if(modifyFlag){
                                				if(todayDate.before(announcement.getExpDate())){%>
                                			
                                		
                                			
                                			<a href="javascript:submitForm()" class="btn1"><%=localeObj.getTranslatedText("Modify")%></a> 
                                			<%}}else{ %>
                                			<a href="javascript:submitForm()" class="btn1"><%=localeObj.getTranslatedText("Submit")%></a>
                                			<%} %>
                                			
                                				
                    		 					<a href="ContentManager?key=AnnouncementMaintenance" class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a> 
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
			
	<%}catch(Exception e){
		e.printStackTrace();
		LogsMaintenance logsMain=new LogsMaintenance();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logsMain.insertLogs("EopAnnouncementAdd1 jsp page ","SEVERE"+"",errors.toString());
		
	} %>	
     