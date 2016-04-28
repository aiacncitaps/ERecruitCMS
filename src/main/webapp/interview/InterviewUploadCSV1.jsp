<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.quix.aia.cn.imo.utilities.ErrorObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
String localKey = localeObj.getKey();
%>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
 $(function(){
		$(".data_table tr:odd").addClass("odd");
	    $(".data_table tr:not(.odd)").addClass("even");  
	});
 $(document).ready(function(){
	 $("#submit").hide();
});
 function uploadCSV(lang){
	    $('#ajaxLoader').find(".lightbox").show();	
	    
	    if(isAjaxUploadSupported()){
	    
				if($('input[type=file]').get(0).files[0] !=undefined){
					var csvFile = $('input[type=file]').get(0).files[0];
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
					 // hideProgress();
					  //alert('<%=localeObj.getTranslatedText("CSV file Uploaded Successfully")%>');
					  $("#submit").show();
					}
					else{
						 if(lang == 'CN')
							 alert('<%=localeObj.getTranslatedText("请上传文件扩展XLSX或XLS")%>');
						 else
							alert('<%=localeObj.getTranslatedText("Please Upload file with extension xlsx OR xls")%>');
					}
			   }
			  else{
				  if(lang == 'CN')
						alert('<%=localeObj.getTranslatedText("请上传文件")%>');
				  else
					alert('<%=localeObj.getTranslatedText("Please Upload a file")%>');
			   }
	    }else{
	    	var material_name=$("#csvFile").val();
	    	if('' != material_name){
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
		    	}
				else{
					 if(lang == 'CN')
						 alert('<%=localeObj.getTranslatedText("请上传文件扩展XLSX或XLS")%>');
					 else
						alert('<%=localeObj.getTranslatedText("Please Upload file with extension xlsx OR xls")%>');
				}	

	    	}  else{
					  if(lang == 'CN')
							alert('<%=localeObj.getTranslatedText("请上传文件")%>');
					  else
						alert('<%=localeObj.getTranslatedText("Please Upload a file")%>');
				   }
	    }
		$('#ajaxLoader').find(".lightbox").hide();
	 }

function interviewCSVSubmit(action) 
{
 document.interviewCSVForm.actionType.value = action ;
 document.interviewCSVForm.submit() ;
  
}
</script>

	<div id="maincontainer">

		<div class="head">
			<h2 align="center"><%=localeObj.getTranslatedText("Interview Maintenance Upload CSV")%></h2>
		</div>
		<div class="content" style="background-color: #ffffff;">
			<form name="interviewCSVForm" method="post" action="FormManager"
				class="PT20">
				<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
				<input type="hidden" name="actionType" />
				<table class="formDesign">
					<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>

					<tr>
						<td colspan="2" style="text-align: center"><span
							style="color: #ec2028;"><%=localeObj.getTranslatedText(((ErrorObject)request.getAttribute(RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
					</tr>
					<%
					 request.removeAttribute(RequestAttributes.ERROR_OBJ);
					}%>



					<tr>
						<td><label><%=localeObj.getTranslatedText("Upload Interview CSV")%></label>
						</td>
						<td id="csvFileTD"><input type="file" name="csvFile" id="csvFile"
							class="fileObj" onChange="javascript:uploadCSV('<%=localKey %>')" /></td>
							
						<td>	<a href="format/interview.xls"  target="_new"><%=localeObj.getTranslatedText("Sample Template")%></a></td>

					</tr>


					<tr>
						<td colspan="3" class="MT30 MB30"
							style="text-align: center; padding-top: 20px"><a href="#"
							class="btn1 " id="submit" name="submit"
							onclick="javascript:interviewCSVSubmit('<%=FormInfo.SUBMIT%>')"><%=localeObj.getTranslatedText("Submit")%></a>
							&nbsp;&nbsp; <a href="ContentManager?key=InterviewMaintenance"
							class="btn1"><%=localeObj.getTranslatedText("Back")%></a> <%
                            		 				    if(request.getSession().getAttribute("Summarypath")!=null && request.getSession().getAttribute("strbuf")!=null ){ 
                                       	 	            String summaryPath=(String)request.getSession().getAttribute("Summarypath");
                                       	 			%> <a
							href="<%=summaryPath %>" target="_new" class="btn1"><%=localeObj.getTranslatedText("Download Error Summary")%>
						</a> <%
                                       	 		      }
                                       	 			%></td>
					</tr>

				</table>
			</form>
		</div>
	
</div>

</br>
</br>

<%
 request.getSession().removeAttribute("strbuf");
 request.getSession().removeAttribute("Summarypath");
 %>
