<%--
  - Author(s):          Nibedita
  - Date:               30-Sept-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        question set page
--%>
<%@page import="java.io.File"%>
<%@page import="com.quix.aia.cn.imo.data.properties.ConfigurationProperties"%>
<%@page import="com.quix.aia.cn.imo.mapper.PropertiesMaintenance"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.utilities.KeyObjPair"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="java.util.*"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/Configuration.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>

<%
try{

User userObj = new User();
userObj = (User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ);
boolean modifyFlag = false;

FormObj formDetail = null;
if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
}
/* if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag = true;
} */

LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);

%>

<div id="maincontainer">
		<div class="head">
			<h2 align="center">
			<%=localeObj.getTranslatedText("Upload PLIST file of IOS App")%>
			</h2>
		</div>
		<div class="content" style="background-color:#ffffff;">
			<form action="FormManager" name="administrationForm" method="post" class="PT20">
			<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
				<table class="formDesign">
					<tr>
						<td colspan="2" style="text-align: center;color:#ec2028;">
							<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){%>	
                              	<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
                            <%}%>    
						</td>
					</tr>
					<tr>
						<td><label><%=localeObj.getTranslatedText("App Type")%></label></td>
						<td>
							<select name="appType" id="appType" class="comboObj" >
        						<option value="<%=ConfigurationProperties.E_RECRUITMENT_APP_URL%>" selected><%=localeObj.getTranslatedText("E-Recruitment App")%></option>
           						<option value="<%=ConfigurationProperties.EOP_SCAN_APP_URL%>"  ><%=localeObj.getTranslatedText("EOP Scan App")%></option>
           	 				</select>
						</td>
					</tr>
					
					<tr>
						<td><label><%=localeObj.getTranslatedText("Select PLIST file")%></label></td>
						<td id ="plistFileTD" >
						<input type="hidden" id="appURL" name="appURL"  maxlength="250" class="textObj"value="">
						<input name="plistFile" id="plistFile" type="file" class="fileObj"  onchange="uploadMaterial1();"/>
						<input type="hidden" name="uploadMaterialFile1"  id="uploadMaterialFile1" value="" /> 
						</td>
						</tr>
					<tr >
						<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                    		<a href="#"   class="ML10 btn1 " id="saveButton"><%=localeObj.getTranslatedText("Upload")%></a>
                    		<a href="#"   class="ML10 btn1 " id="cancelButton"><%=localeObj.getTranslatedText("Cancel")%></a>
						</td>
					</tr>
				</table>
			</form>
		</div>
</div>

	<script language="javascript">
	$('#saveButton').click(function(){
	    	var appType=$('#appType').val();
	    	var appURL=$('#appURL').val();
	    	if('' != appURL){
		    	if((appURL.indexOf('.plist')>-1) || (appURL.indexOf('.PLIST')>-1)) {
			    	Configuration.updateConfigurationProperties(appType,appURL,{
			    		callback : function(response) 
			    		{
			    			alert(response);
			    	    }	
			        });
		        }else{
		        	alert("Please Upload file with extension .plist");
		        }
	        }else{
	        	alert("Please Upload a file");
	        }
	});
		
			
	$('#cancelButton').click(function()
	{
   			 window.location.href="ContentManager?key=home";
	});
	
	
function uploadMaterial1(){
	
	$('#ajaxLoader').find(".lightbox").show();
	var appType = $('#appType').val();
	if(isAjaxUploadSupported()){
		if($('input[type=file]').get(0).files[0] !=undefined){
			var materialFile = $('input[type=file]').get(0).files[0];
			var file_name=materialFile.name;
				if((file_name.indexOf('.plist')>-1) || (file_name.indexOf('.PLIST')>-1))
						
			{
			if(materialFile==undefined)
				materialFile = $('#materialname1').html();
			var fd = new FormData();
				fd.append(appType, materialFile);
				var material_name=$("#plistFile").val();
				$('#uploadMaterialFile1').val(file_name);
				var size=$('input[type=file]').get(0).files[0].size;
				if(size<=5242880){
				
			  $.ajax({
					url : 'UploadMaterial?usedFor='+appType+'&image_name='+material_name,
					type: "POST",
					data: fd,
				   	processData: false,
				   	contentType: false,
				}).done(function(respond){
					//window.location.href = "ContentManager?key=AnnouncementMaintenance";
					
					$('#materialname1').html(file_name);
					$('#appURL').val(respond);
					$('#ajaxLoader').find(".lightbox").hide();
				});
				}else{
					alert("Please Upload File Less then 5 MB");
					$('#plistFile').val('');
				} 
			}else{
				alert("Please Upload file with extension .plist");
				$('#plistFile').val('');
				
				if(document.getElementById('plistFile') != null) 
					 document.getElementById('plistFile').outerHTML = document.getElementById('plistFile').outerHTML;
			}
		}else{
			
			alert("Please Upload a file");
			$('#uploadMaterialFile1').val('');
		}
	}else{
		var material_name=$("#plistFile").val();
		var appType=$("#appType").val();
		var fileName = material_name.replace(/^.*[\\\/]/, '');
		if('' != material_name){
			if((material_name.indexOf('.plist')>-1) || (material_name.indexOf('.PLIST')>-1))
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
		        form.setAttribute("action", "UploadMaterial?usedFor="+appType+"&image_name="+material_name); //change page to post
		        form.setAttribute("method", "post");
		        form.setAttribute("enctype", "multipart/form-data");
		        form.setAttribute("encoding", "multipart/form-data");
		        form.style.display = "none";
		
		        var files = document.getElementById("plistFile");
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
		            
// 		        	$('#appURL').val(response);
		        }
		
		        if (iframeIdmyFile.addEventListener) 
		            iframeIdmyFile.addEventListener("load", eventHandlermyFile, true);
		        if (iframeIdmyFile.attachEvent) 
		            iframeIdmyFile.attachEvent("onload", eventHandlermyFile);
		
		        form.submit();
		        
		        $('#appURL').val("resources/"+ appType+"/"+ fileName);
		        
		        var announcementMaterialTD = document.getElementById("plistFileTD");
		        announcementMaterialTD.appendChild(files);
		        files.style.display = "block";
			}else{
				alert("Please Upload file with extension .plist");
				$('#uploadMaterialFile1').val('');
				
				if(document.getElementById('plistFile') != null) 
					 document.getElementById('plistFile').outerHTML = document.getElementById('plistFile').outerHTML;
			}  

		}else{
			
			alert("Please Upload a file");
			$('#uploadMaterialFile1').val('');
		}

//         return;
	}
	$('#ajaxLoader').find(".lightbox").hide();
	
}
	</script>
 <%}catch(Exception e) {
	e.printStackTrace();}
%>