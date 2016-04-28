<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.mapper.UserMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<script src="js/lms.js" type="text/javascript"></script>

<%

LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
String localKey = localeObj.getKey();
User userObj = (User)request.getSession().getAttribute("currUserObj");

%>
 <script type="text/javascript">
 $( document ).ready(function() {
	 $("#submit").hide();
	 
	});
function uploadCSV(lang){
		$('#ajaxLoader').find(".lightbox").show();	
		$("#submit").hide();
		
	    if(isAjaxUploadSupported()){
		
				var csvFile = $('#csvFile').get(0).files[0];
				var file_extension=csvFile.name;
				if((file_extension.indexOf('.xlsx')>-1) || (file_extension.indexOf('.XLSX')>-1) ||
						(file_extension.indexOf('.xls')>-1) || (file_extension.indexOf('.XLS')>-1)){
				//	showProgress();
				var fd = new FormData();
				fd.append('csvFile', csvFile);
				var fileName=$("#csvFile").val();
				
				  $.ajax({
						url : 'UploadMaterial?image_name='+fileName,
						type: "POST",
						data: fd,
					   	processData: false,
					   	contentType: false,
					}).done(function(respond){
						
					});

					
					 $("#submit").show();
				  $('#ajaxLoader').find(".lightbox").hide();
					if(lang == 'CN')
						 alert('<%=localeObj.getTranslatedText("文档上载成功")%>');
					else
				 		 alert('<%=localeObj.getTranslatedText("Xcel file Uploaded Successfully")%>');
				 
				}
				else{
					 $('#ajaxLoader').find(".lightbox").hide();
					 if(lang == 'CN')
						 document.getElementById("p1").innerHTML = "请上传文件扩展XLSX或XLS";
					 else
						document.getElementById("p1").innerHTML = "Please Upload file with extension xlsx OR xls";
				}

	    }else{
	    	var material_name=$("#csvFile").val();
// 	    	if('' != material_name){
	    	if((material_name.indexOf('.xlsx')>-1) || (material_name.indexOf('.XLSX')>-1) ||
					(material_name.indexOf('.xls')>-1) || (material_name.indexOf('.XLS')>-1)){
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
	
		        var files = document.getElementById("csvFile");
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
		        
		        var announcementMaterialTD = document.getElementById("csvFileTD");
		        announcementMaterialTD.appendChild(files);
		        files.style.display = "block";

				
				 $("#submit").show();
				  $('#ajaxLoader').find(".lightbox").hide();
					if(lang == 'CN')
						 alert('<%=localeObj.getTranslatedText("文档上载成功")%>');
					else
				 		 alert('<%=localeObj.getTranslatedText("Xcel file Uploaded Successfully")%>');
				 
				}
				else{
					 $('#ajaxLoader').find(".lightbox").hide();
					 if(lang == 'CN')
						 document.getElementById("p1").innerHTML = "请上传文件扩展XLSX或XLS";
					 else
						document.getElementById("p1").innerHTML = "Please Upload file with extension xlsx OR xls";
				}
		}
		 $('#ajaxLoader').find(".lightbox").hide();	
}

function userCSVSubmit(action,lang) 
{
	var csvFile = $('input[type=file]').get(0).files[0];
	if(csvFile==null){
		 if(lang == 'CN')
			 alert('<%=localeObj.getTranslatedText("先上传文件")%>');
		 else
			alert('<%=localeObj.getTranslatedText("First Upload File")%>');
		return false;
		
	}
	$('#ajaxLoader').find(".lightbox").show();	
 document.administrationForm.actionType.value = action ;
 document.administrationForm.submit() ;
  
}
</script>
<style>
@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) {.class_limit{width:260px;}}
@media screen and (min-width:0\0) and (min-resolution: +72dpi) {.class_limit{width:270px;}}
</style>


		<div id="maincontainer">
					<div class="head">
					<h2 align="center"><%=localeObj.getTranslatedText("User Management Upload CSV")%></h2></div>  	
						
					</div>
					<div class="content" style="background-color:#ffffff;">
<form method="post" action="FormManager" name="administrationForm"  >
<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
<input type="hidden" id="actionType" name="actionType">
<table class="formDesign MB20" >
					<tr>
						<td colspan="2" style="text-align: center">
						<span style="color:#ec2028;">
                     	   <%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){%>
											
							<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
							<%}%>                          					
                     </span></td>  <div style="text-shadow: red;" id="p1" name="p1">  </div>
                     <%request.removeAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ);  %>
					</tr>

                   		<tr>                         
                        	<td >	                                    				
                         	<label ><%=localeObj.getTranslatedText("Upload User CSV")%></label> 
                        	</td>
                        	
                        	<td id="csvFileTD">	
                        	<input type="file" name="csvFile" id="csvFile" class="fileObj" onChange="javascript:uploadCSV('<%=localKey %>')" /> 
                        	</td>
                        	<td>	<a href="format/UserManagement.xls"  target="_new"><%=localeObj.getTranslatedText("Sample Template")%></a></td>
                           			
               		 	</tr>
                            <tr></tr>        
                            </table>
                         <table style="width:55%;">  			   
           			    <tr >                         
             			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
             				<a href="#" class="btn1 "  id="submit" name="submit" onclick="javascript:userCSVSubmit('<%=FormInfo.SUBMIT%>','<%=localKey %>')"><%=localeObj.getTranslatedText("Submit")%></a> &nbsp;&nbsp;
 		 					<a href="ContentManager?key=<%=(String)((com.quix.aia.cn.imo.utilities.PageObj)request.getSession().getAttribute(com.quix.aia.cn.imo.constants.SessionAttributes.PAGE_OBJ)).getKey()%>" class="btn1"><%=localeObj.getTranslatedText("Back")%></a> &nbsp;&nbsp;
            	 			
            	 				<%if(request.getSession().getAttribute("Summarypath")!=null && request.getSession().getAttribute("strbuf")!=null ){ 
            	 				
            	 				String summaryPath=(String)request.getSession().getAttribute("Summarypath");
            	 				%>
            	 					 <a href="<%=summaryPath %>" target="_new" class="btn1"><%=localeObj.getTranslatedText("Download Error Summary")%> </a>
            	 				<% }  request.getSession().removeAttribute("Summarypath");
            	 				request.getSession().removeAttribute("strbuf");%>
            	 			
            	 			
            	 			</td>                                     			   
           			    </tr>
 		 				
			
			</table>
			</form>
     	</div>
    