<%--
  - Author(s):          Nibedita
  - Date:               06-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Added code to populate Bu,district,city,ssc dropdown
--%>
<%@page import="com.quix.aia.cn.imo.constants.ApplicationAttribute"%>
<%@page import="java.util.Map"%>
<%@page import="com.quix.aia.cn.imo.data.channel.Channel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.data.event.Event"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.mapper.EopMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ page import='com.quix.aia.cn.imo.data.user.*' %>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes'%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>

<%User user = ((com.quix.aia.cn.imo.data.user.User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ));
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
User userObj = (User)request.getSession().getAttribute("currUserObj");
boolean modifyFlag = false;
FormObj formDetail = null;
Event event = new Event();
if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
}
event = (Event)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);

int buCode = 0,distCode = 0,branch=0;
String cityCode = 0+"",sscCode = 0+"",office=0+"",agentTeam="";
buCode=userObj.getBuCode();
distCode=userObj.getDistrict();
cityCode=userObj.getCityCode();
sscCode=userObj.getSscCode();
branch=userObj.getBranchCode();
office=userObj.getOfficeCode();


if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag = true;
	buCode = Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(event.getBuCode())));
	distCode = Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(event.getDistrict())));
	cityCode = SecurityAPI.encodeHTML(String.valueOf(event.getCityCode()));
	sscCode = SecurityAPI.encodeHTML(String.valueOf(event.getSscCode().trim()));
	branch=Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(event.getBranchCode())));
	office=SecurityAPI.encodeHTML(String.valueOf(event.getOfficeCode()));
	agentTeam=SecurityAPI.encodeHTML(String.valueOf(event.getAgentTeam()));
	
	
}

if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
	buCode = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.BU_PARAM)));
	distCode = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.DISTRICT_PARAM)));
	cityCode = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.CITY_PARAM));
	sscCode = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.SSC_PARAM));
	branch = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter("branch")));
	office = SecurityAPI.encodeHTML(request.getParameter("office"));
}
ImoUtilityData imoUtilityDate=new ImoUtilityData();

String openTo = "";
	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
		openTo = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.OPENTO_PARAM));
	}else{
		openTo = SecurityAPI.encodeHTML(String.valueOf(event.getOpenTo()));
	}
String fname = ""; 
if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
	if(request.getParameter("uploadProfileFile")!=null){
		fname = request.getParameter("uploadProfileFile");
	 }
}else{
 if(event.getProfilePath()!=null && event.getProfilePath().length() > 0){
	 fname = event.getProfilePath().substring(event.getProfilePath().lastIndexOf('/') + 1);
 }
}
 String topicFileName = ""; 
 if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
		if(request.getParameter("uploadTopicFile")!=null){
			topicFileName = request.getParameter("uploadTopicFile");
		 }
 }else{
 if(event.getTopicPath()!=null && event.getTopicPath().length() > 0){
	 topicFileName = event.getTopicPath().substring(event.getTopicPath().lastIndexOf('/') + 1);
 }
 }
 String specialGrp = "";
 if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
	 if(request.getParameter("uploadSpecialGrp")!=null){
		 specialGrp = request.getParameter("uploadSpecialGrp");
	 }
 }else{
	 if(event.getSpecialGrListingPath()!=null && event.getSpecialGrListingPath().length() > 0){
		 specialGrp = event.getSpecialGrListingPath().substring(event.getSpecialGrListingPath().lastIndexOf('/') + 1);
}
}
String profile_path="#";
EopMaintenance eopMain=new EopMaintenance();
if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
		if(request.getParameter("uploadProfileFile")!=null){
			fname = request.getParameter("uploadProfileFile");
		 }
}else{
	if(event.getProfilePath()!=null && event.getProfilePath().length() > 0){
		 fname = event.getProfilePath().substring(event.getProfilePath().lastIndexOf('/') + 1);
		 profile_path=eopMain.getProfileFile(event, request);
		System.out.println("profile_path:"+profile_path);
	}
}

String topic_path="#";
if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
		if(request.getParameter("uploadTopicFile")!=null){
			topicFileName = request.getParameter("uploadTopicFile");
		 }
}else{
	if(event.getTopicPath()!=null && event.getTopicPath().length() > 0){
		 topicFileName = event.getTopicPath().substring(event.getTopicPath().lastIndexOf('/') + 1);
		 topic_path=eopMain.getTopicFile(event,request);
		System.out.println("topic_path:"+topic_path);
	}
}  
%>

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
		 
		getCity('<%=branch %>','<%=cityCode %>','<%=distCode %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
		
		getSSC('<%=cityCode %>','<%=sscCode %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
	
	getOffice('<%=sscCode %>','<%=office %>');
	//getAgentTeam1();
	<%if(modifyFlag==true){%>
		getAgentTeam('<%=cityCode %>', '<%=sscCode %>', '<%=branch %>','<%=office %>','<%=agentTeam %>');
	<%}else{%>
			getAgentTeam('<%=cityCode %>', '<%=sscCode %>', '<%=branch %>','<%=office %>','E');
	<%}%>
<% }%>

<%if("SG".equals(openTo)){%>
$('#specialGroup').show();
$('#csvFilename').html('<%=specialGrp%>');
$('#uploadSpecialGrp').val('<%=specialGrp%>');
$("#bu").prop("disabled", true);
$("#district").prop("disabled", true);
$("#branch").prop("disabled", true);
$("#city").prop("disabled", true);
$("#ssc").prop("disabled", true);
$("#office").prop("disabled", true);
$("#agentTeam").prop("disabled", true);



<%}else{%>
$('#specialGroup').hide();
$("#bu").prop("disabled", false);
$("#district").prop("disabled", false);
$("#branch").prop("disabled", false);
$("#city").prop("disabled", false);
$("#ssc").prop("disabled", false);
$("#office").prop("disabled", false);
$("#agentTeam").prop("disabled", false);

<%}%>

$('#materialname').html('<%=fname%>');
$('#uploadProfileFile').val('<%=fname%>');

$('#topicFile').html('<%=topicFileName%>');
$('#uploadTopicFile').val('<%=topicFileName%>');

});
 $(function() {
	 
	$( ".datepicker" ).datepicker({
		/* defaultDate: "+1w", */
	showOn: "button",
	buttonImage: "images/calendar_icon.gif",
	buttonImageOnly: true,
	dateFormat:"dd/mm/yy",
	changeMonth: true, 
	changeYear: true,
	minDate: 0
	
	});
 }); 
 
 function submitForm(){
		if(document.eopFrom.publicRegistration.checked==true)
			document.eopFrom.publicRegistration.value = "Y";
		else
			document.eopFrom.publicRegistration.value = "N";
		
		$("#bu").prop("disabled", false);
		$("#district").prop("disabled", false);
		$("#branch").prop("disabled", false);
		$("#city").prop("disabled", false);
		$("#ssc").prop("disabled", false);
		$("#office").prop("disabled", false);
		$("#agentTeam").prop("disabled", false);
		 document.eopFrom.submit();
	 
 }

//$('#uploadCSV').on('click', function(e) {
function uploadCSVFile(){	
	$('#ajaxLoader').find(".lightbox").show();
	if($('input[type=file]').get(0).files[0] !=undefined){
		var csvFile = $('input[type=file]').get(0).files[0];
		var file_name=csvFile.name;
		if((file_name.indexOf('.csv')>-1) || (file_name.indexOf('.CSV')>-1 ) ||
				(file_name.indexOf('.xlsx')>-1) || (file_name.indexOf('.XLSX')>-1 )||
				(file_name.indexOf('.xls')>-1) || (file_name.indexOf('.XLS')>-1 )){
			$('#uploadSpecialGrp').val(file_name);
		var fd = new FormData();
			fd.append('csvFile', csvFile);
			
			
		  $.ajax({
				url : 'UploadMaterial?',
				type: "POST",
				data: fd,
			   	processData: false,
			   	contentType: false,
			}).done(function(respond){
				//window.location.href = "ContentManager?key=EopMaintenance";
				$('#csvFilename').html(file_name);
			});
			
		}else{
			alert('<%=localeObj.getTranslatedText("Please Upload file with extension .csv or .xlsx or .xls")%>');
			$('#uploadSpecialGrp').val('');
			if(document.getElementById('UploadSpecialGroupListing') != null) 
			document.getElementById('UploadSpecialGroupListing').outerHTML = document.getElementById('UploadSpecialGroupListing').outerHTML;
			
			if('<%=modifyFlag%>' == 'false')
				$('#csvFilename').html('');
		
		}
	}else{
		alert('<%=localeObj.getTranslatedText("Please Upload a file")%>');
		$('#uploadSpecialGrp').val('');
	}
	$('#ajaxLoader').find(".lightbox").hide();
}
//});

//$('#uploadMaterial').on('click', function(e) {
function uploadMaterial(){
	$('#ajaxLoader').find(".lightbox").show();
	
	if(isAjaxUploadSupported()){
		if($('input[type=file]').get(1).files[0] !=undefined){
			var materialFile = $('input[type=file]').get(1).files[0];
			var file_name=materialFile.name;
			if((file_name.indexOf('.pdf')>-1) || (file_name.indexOf('.PDF')>-1)
					|| (file_name.indexOf('.jpg')>-1) || (file_name.indexOf('.JPG')>-1)
					|| (file_name.indexOf('.jpeg')>-1) || (file_name.indexOf('.JPEG')>-1)
					|| (file_name.indexOf('.doc')>-1) || (file_name.indexOf('.DOC')>-1)
					|| (file_name.indexOf('.docs')>-1) || (file_name.indexOf('.docs')>-1)){
			if(materialFile==undefined)
				materialFile = $('#materialname').html();
			var fd = new FormData();
				fd.append('ProfileMaterial', materialFile);
				var material_name=$("#ProfileMaterial").val();
				$('#uploadProfileFile').val(file_name);
				var size=$('input[type=file]').get(1).files[0].size;
				if(size<=5242880){
			  $.ajax({
					url : 'UploadMaterial?',
					type: "POST",
					data: fd,
				   	processData: false,
				   	contentType: false,
				}).done(function(respond){
					$('#materialname').html(file_name);
					$('#ajaxLoader').find(".lightbox").hide();
				});
				}else{
					$('#ajaxLoader').find(".lightbox").hide();
					alert('<%=localeObj.getTranslatedText("Please Upload File Less then 5 MB")%>');
					$('#ProfileMaterial').val('');
				}  
			}else{
				$('#ajaxLoader').find(".lightbox").hide();
				alert('<%=localeObj.getTranslatedText("Please Upload file with extension .pdf or .jpg or jpeg or .doc or .docs")%>');
				$('#uploadProfileFile').val('');
			
				if(document.getElementById('ProfileMaterial') != null) 
				 document.getElementById('ProfileMaterial').outerHTML = document.getElementById('ProfileMaterial').outerHTML;
				
				if('<%=modifyFlag%>' == 'true')
					$('#materialname').html('<%=fname%>');
				else
					$('#materialname').html('');
				
			}
		}else{
			$('#ajaxLoader').find(".lightbox").hide();
			alert('<%=localeObj.getTranslatedText("Please  Upload a file")%>');
			$('#uploadProfileFile').val('');
			
		}
	}else{
		var material_name=$("#ProfileMaterial").val();
		
    	if('' != material_name){
    		if((material_name.indexOf('.pdf')>-1) || (material_name.indexOf('.PDF')>-1)
					|| (material_name.indexOf('.jpg')>-1) || (material_name.indexOf('.JPG')>-1)
					|| (material_name.indexOf('.jpeg')>-1) || (material_name.indexOf('.JPEG')>-1)
					|| (material_name.indexOf('.doc')>-1) || (material_name.indexOf('.DOC')>-1)
					|| (material_name.indexOf('.docs')>-1) || (material_name.indexOf('.docs')>-1)){
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
	
		        var files = document.getElementById("ProfileMaterial");
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
		        
		        var announcementMaterialTD = document.getElementById("ProfileMaterialTD");
		        announcementMaterialTD.appendChild(files);
		        files.style.display = "block";
			  }else{
				  $('#ajaxLoader').find(".lightbox").hide();
					alert('<%=localeObj.getTranslatedText("Please Upload file with extension .pdf or .jpg or jpeg or .doc or .docs")%>');
				$('#uploadProfileFile').val('');
			
				if(document.getElementById('ProfileMaterial') != null) 
				 document.getElementById('ProfileMaterial').outerHTML = document.getElementById('ProfileMaterial').outerHTML;
				
				if('<%=modifyFlag%>' == 'true')
					$('#materialname').html('<%=fname%>');
				else
					$('#materialname').html('');
			
			} 
		}else{
			$('#ajaxLoader').find(".lightbox").hide();
			alert('<%=localeObj.getTranslatedText("Please Upload a file")%>');
			$('#uploadProfileFile').val('');
		}
	}
	$('#ajaxLoader').find(".lightbox").hide();

}
//});
function uploadEopTopic(){
	$('#ajaxLoader').find(".lightbox").show();
	
	if(isAjaxUploadSupported()){
	
		if($('input[type=file]').get(2).files[0] !=undefined){
			var topicFile = $('input[type=file]').get(2).files[0];
			var file_name=topicFile.name;
			if((file_name.indexOf('.pdf')>-1) || (file_name.indexOf('.PDF')>-1)
					|| (file_name.indexOf('.jpg')>-1) || (file_name.indexOf('.JPG')>-1)
					|| (file_name.indexOf('.png')>-1)|| (file_name.indexOf('.PNG')>-1)
					|| (file_name.indexOf('.gif')>-1)|| (file_name.indexOf('.GIF')>-1)){
			
			var fd = new FormData();
			fd.append('TopicFile', topicFile);
			var fileName=$("#TopicFile").val();
			$('#uploadTopicFile').val(file_name);
			var size=$('input[type=file]').get(2).files[0].size;
			if(size<=5242880){
			
			  $.ajax({
					url : 'UploadMaterial?',
					type: "POST",
					data: fd,
				   	processData: false,
				   	contentType: false,
				}).done(function(respond){
					$('#topicFile').html(file_name);
					$('#ajaxLoader').find(".lightbox").hide();
				});
			
			}else{
				$('#ajaxLoader').find(".lightbox").hide();
				alert('<%=localeObj.getTranslatedText("Please Upload File Less then 5 MB")%>');
				$("#TopicFile").val('');
			} 
			}
			else{
				$('#ajaxLoader').find(".lightbox").hide();
				alert('<%=localeObj.getTranslatedText("Please Upload a PDF or Image File")%>');
				if(document.getElementById('TopicFile') != null) 
				document.getElementById('TopicFile').outerHTML = document.getElementById('TopicFile').outerHTML;
				$('#uploadTopicFile').val('');
				if('<%=modifyFlag%>' == 'true')
					$('#topicFile').html('<%=topicFileName%>');
				else
					$('#topicFile').html('');
					
				
			}
	   }
	  else{
			$('#ajaxLoader').find(".lightbox").hide();
			alert('<%=localeObj.getTranslatedText("Please Upload a file")%>');
			$('#uploadTopicFile').val('');
	   }
	}else{
		var material_name=$("#ProfileMaterial").val();
		
    	if('' != material_name){
    		if((material_name.indexOf('.pdf')>-1) || (material_name.indexOf('.PDF')>-1)
					|| (material_name.indexOf('.jpg')>-1) || (material_name.indexOf('.JPG')>-1)
					|| (material_name.indexOf('.png')>-1)|| (material_name.indexOf('.PNG')>-1)
					|| (material_name.indexOf('.gif')>-1)|| (material_name.indexOf('.GIF')>-1)){
    			
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
	
		        var files = document.getElementById("TopicFile");
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
		        
		        var announcementMaterialTD = document.getElementById("TopicFileTD");
		        announcementMaterialTD.appendChild(files);
		        files.style.display = "block";
			  }else{
					$('#ajaxLoader').find(".lightbox").hide();
					alert('<%=localeObj.getTranslatedText("Please Upload a PDF or Image File")%>');
					if(document.getElementById('TopicFile') != null) 
					document.getElementById('TopicFile').outerHTML = document.getElementById('TopicFile').outerHTML;
					$('#uploadTopicFile').val('');
					if('<%=modifyFlag%>' == 'true')
						$('#topicFile').html('<%=topicFileName%>');
					else
						$('#topicFile').html('');
						
			
			} 
		}else{
			$('#ajaxLoader').find(".lightbox").hide();
			alert('<%=localeObj.getTranslatedText("Please Upload a file")%>');
			$('#uploadTopicFile').val('');
		}
	}
	$('#ajaxLoader').find(".lightbox").hide();
	
 }

 
 function checkValue(openTo){
	 if(openTo == 'SG'){
	$('#specialGroup').show();
	 $("#bu")[0].selectedIndex=0;
	  $("#district")[0].selectedIndex=0;
	  $("#branch")[0].selectedIndex=0;
	  $("#city")[0].selectedIndex=0;
	  $("#ssc")[0].selectedIndex=0;
	  $("#office")[0].selectedIndex=0;
	  $("#agentTeam")[0].selectedIndex=0;
	  
	  
	  $("#bu").prop("disabled", true);
	  $("#district").prop("disabled", true);
	  $("#branch").prop("disabled", true);
	  $("#city").prop("disabled", true);
	  $("#ssc").prop("disabled", true);
	  $("#office").prop("disabled", true);
	  $("#agentTeam").prop("disabled", true);
	 }else{
		 $('#specialGroup').hide(); 
		 $("#bu").prop("disabled", false);
	   	  $("#district").prop("disabled", false);
	   	  $("#branch").prop("disabled", false);
	   	  $("#city").prop("disabled", false);
	   	  $("#ssc").prop("disabled", false);
	   	  $("#office").prop("disabled", false);
	   		$("#agentTeam").prop("disabled", false);
	 }
		
 }
 
 function getAgentTeam1(){
	
	var branch = $('#branch option:selected').val();
	var city = $('#city option:selected').val();
	var ssc = $('#ssc option:selected').val();
	var office = $('#office option:selected').val();
	getAgentTeam(city, ssc, branch,office,'E');
 }
</script>

   	<div id="maincontainer">	
   					<div class="head"><h2 align="center">
   					<%if(modifyFlag == true){%>
   					<%=localeObj.getTranslatedText("EOP Edit schedule")%><%}else{ %><%=localeObj.getTranslatedText("EOP add schedule")%><%} %></h2></div>  	
   						<div class="content" style="background-color:#ffffff;">
   								 	  <form name="eopFrom" method="post" action="FormManager"  class="PT20">
   								 	  <input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
   								 	  <input type="hidden" name="<%=FormInfo.HIDDEN_ACTION%>" value="SUBMIT"/>
                           				<table class="formDesign" style="width:50%;">
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
                                    				<td style="width:40%;"><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Event Type")%></label></td>
                                    			
                                    			<%
                                    			String eventType = "";
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					                          		eventType = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.EVENT_TYPE_PARAM));
					                          	}else{
					                          		eventType = SecurityAPI.encodeHTML(event.getEventType());
					                          	}
					                          		
					                          		
					                            %>	
                                        			<td style="width:60%;">	
                                        			<select name="<%=EopMaintenance.EVENT_TYPE_PARAM %>" id="<%=EopMaintenance.EVENT_TYPE_PARAM %>" class="comboObj" >
                                       						<option value="eop" <%="eop".equals(eventType.trim())?"selected" : ""%>><%=localeObj.getTranslatedText("EOP")%></option>
                                       						<option value="companyevent"  <%="companyevent".equals(eventType.trim())?"selected" : ""%>><%=localeObj.getTranslatedText("Company Event")%></option>
                            								<option value="networking"  <%="networking".equals(eventType.trim())?"selected" : ""%>><%=localeObj.getTranslatedText("Networking")%></option>	
                                       						<option value="training"   <%="training".equals(eventType.trim())?"selected" : ""%>><%=localeObj.getTranslatedText("Training")%></option>                                       	
                                       	 			</select>
                                       	 			</td>                                    			   
                                      			    </tr>
                            		 				<%--
                            		 				<tr>                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("EOP Topic")%></label></td>	                                    				
                                    				
                                    				<%
	                                    			String topic = "";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		topic = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.TOPIC_PARAM));
						                          	}else{
						                          		topic = SecurityAPI.encodeHTML(event.getTopic());
						                          	}
					                          		
					                          		 %>	
                                        			<td>	
                                        				<input name="<%=EopMaintenance.TOPIC_PARAM %>" id="<%=EopMaintenance.TOPIC_PARAM %>" type="text" class="textObj"  maxlength="250" value="<%=topic%>"  />
                                       	 			</td>                                     			   
                                      			    </tr> --%>
                            		 				<tr>                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Event Name")%></label></td>	                                    				
                                    				
                                    				<%
	                                    			String eventName = "";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		eventName = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.EVENT_NAME_PARAM));
						                          	}else{
						                          		eventName = SecurityAPI.encodeHTML(event.getEventName());
						                          	}
					                          		
					                          		 %>	
                                        			<td>	
                                        				<input name="<%=EopMaintenance.EVENT_NAME_PARAM %>" id="<%=EopMaintenance.EVENT_NAME_PARAM %>" type="text" class="textObj"  maxlength="250"  value="<%=eventName %>"  />
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				<tr>                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Date")%></label></td>                                    				
                                    				
                                    				<%
	                                    			String eventDate = "";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		eventDate = request.getParameter(EopMaintenance.DATE_PARAM);
						                          	}else{
						                          		eventDate = event.getEventDate()!=null?LMSUtil.convertDateToString(event.getEventDate()):"";
						                          	}
					                          		
					                          		 %>	
                                        			<td>	
														<input name="<%=EopMaintenance.DATE_PARAM %>" id="<%=EopMaintenance.DATE_PARAM %>" type="text" class="textObj datepicker"  value="<%=eventDate%>" readonly="readonly" />
                                       				
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Start Time")%></label></td>                                  				
                                    				
                                    				<%
	                                    			String startTime = "",startHr = "09",startMin = "00",startMer = "AM";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		startHr = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.START_HOUR_PARAM));
						                          		startMin = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.START_MINUTE_PARAM));
						                          		startMer = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.START_MERIDIEM_PARAM));
						                          		
						                          	}else{
						                          		startTime = event.getStartTime()!=null?LMSUtil.converDateIntoHHMMAMPM(event.getStartTime()):"";
						                          		if(event.getStartTime()!=null){
							                          		String stime[] = startTime.split(":");
							                          		startHr = stime[0];
							                          		startMin = stime[1].split(" ")[0];
							                          		startMer = stime[1].split(" ")[1];
						                          		}
						                          	}
					                          		 %>
                                        			<td>	
                                        				<select name="<%=EopMaintenance.START_HOUR_PARAM %>" id="<%=EopMaintenance.START_HOUR_PARAM %>" class="comboObj" style="width:20%;">
                                        				<%for(int i = 0;i<EopMaintenance.HOUR_VALUE_PAIR.length;i++){ %>
                                       						<option value="<%=EopMaintenance.HOUR_VALUE_PAIR[i] %>"  <%=EopMaintenance.HOUR_VALUE_PAIR[i].equals(startHr)?"selected":"" %>><%=EopMaintenance.HOUR_VALUE_PAIR[i] %></option>
															<%} %>                                      	
                                       	 				 </select> HH
                                       	 				 
                                       	 				 
                                       	 				 <select name="<%=EopMaintenance.START_MINUTE_PARAM %>" id="<%=EopMaintenance.START_MINUTE_PARAM %>" class="comboObj" style="width:20%;">
                                       						<%for(int i = 0;i<EopMaintenance.MINUTE_VALUE_PAIR.length;i++){ %>
                                       						<option value="<%=EopMaintenance.MINUTE_VALUE_PAIR[i] %>"  <%=EopMaintenance.MINUTE_VALUE_PAIR[i].equals(startMin)?"selected":"" %>><%=EopMaintenance.MINUTE_VALUE_PAIR[i] %></option>
															<%} %>                            	
                                       	 				 </select> MM
                                       	 				 
                                       	 				 <select name="<%=EopMaintenance.START_MERIDIEM_PARAM %>" id="<%=EopMaintenance.START_MERIDIEM_PARAM %>" class="comboObj" style="width:20%;">
                                       						<option value="AM"  <%="AM".equals(startMer)?"selected":"" %>><%=localeObj.getTranslatedText("AM")%></option>
                                       						<option value="PM"  <%="PM".equals(startMer)?"selected":"" %>><%=localeObj.getTranslatedText("PM")%></option>                                     	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                                      			    
                                      			    <tr>                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("End Time")%></label></td>                                    				
                                    				
                                    				<%
	                                    			String endTime = "",endHr = "05",endMin = "00",endMer = "PM";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		endHr = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.END_HOUR_PARAM));
						                          		endMin = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.END_MINUTE_PARAM));
						                          		endMer = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.END_MERIDIEM_PARAM));
						                          		
						                          	}else{
						                          		endTime = event.getEndTime()!=null?LMSUtil.converDateIntoHHMMAMPM(event.getEndTime()):"";
						                          		if(event.getEndTime()!=null){
							                          		String etime[] = endTime.split(":");
							                          		endHr = etime[0];
							                          		endMin = etime[1].split(" ")[0];
							                          		endMer = etime[1].split(" ")[1];
						                          		}
						                          	}
						                        	
					                          		 %>
                                        			<td style="text-align: left;" >	
                                        				<select name="<%=EopMaintenance.END_HOUR_PARAM %>" id="<%=EopMaintenance.END_HOUR_PARAM %>" class="comboObj" style="width:20%;">
                                       						<%for(int i = 0;i<EopMaintenance.HOUR_VALUE_PAIR.length;i++){ %>
                                       						<option value="<%=EopMaintenance.HOUR_VALUE_PAIR[i] %>"  <%=EopMaintenance.HOUR_VALUE_PAIR[i].equals(endHr)?"selected":"" %>><%=EopMaintenance.HOUR_VALUE_PAIR[i] %></option>
															<%} %>                                        	
                                       	 				 </select> HH 
                                       	 				 
                                       	 				 
                                       	 				 <select name="<%=EopMaintenance.END_MINUTE_PARAM %>" id="<%=EopMaintenance.END_MINUTE_PARAM %>" class="comboObj" style="width:20%;">
                                       					<%for(int i = 0;i<EopMaintenance.MINUTE_VALUE_PAIR.length;i++){ %>
                                       						<option value="<%=EopMaintenance.MINUTE_VALUE_PAIR[i] %>"  <%=EopMaintenance.MINUTE_VALUE_PAIR[i].equals(endMin)?"selected":"" %>><%=EopMaintenance.MINUTE_VALUE_PAIR[i] %></option>
															<%} %>                                	
                                       	 				 </select> MM 
                                       	 				 
                                       	 				 <select name="<%=EopMaintenance.END_MERIDIEM_PARAM %>" id="<%=EopMaintenance.END_MERIDIEM_PARAM %>" class="comboObj" style="width:20%;">
                                       						<option value="AM"  <%="AM".equals(endMer)?"selected":"" %>><%=localeObj.getTranslatedText("AM")%></option>
                                       						<option value="PM"  <%="PM".equals(endMer)?"selected":"" %>><%=localeObj.getTranslatedText("PM")%></option>                                  	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("EOP Description")%></label></td>	                                    				
                                    				
                                    			  <%
	                                    			String description = "";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		description = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.DESCRIPTION_PARAM));
						                          	}else{
						                          		description = SecurityAPI.encodeHTML(event.getEopDescription());
						                          	}%>
                                        			<td style="text-align: left;">	
                                        				<input name="<%=EopMaintenance.DESCRIPTION_PARAM %>" id="<%=EopMaintenance.DESCRIPTION_PARAM %>"  maxlength="250" type="text" class="textObj"  value="<%=description%>"  />
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td style="vertical-align:middle"><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Location")%></label></td>                                  				
                                    				
                                    				 <%
	                                    			String location = "";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		location = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.LOCATION_PARAM));
						                          	}else{
						                          		location = SecurityAPI.encodeHTML(event.getLocation());
						                          	}%>
                                        			<td>	
                                        				<textarea name="<%=EopMaintenance.LOCATION_PARAM %>" id="<%=EopMaintenance.LOCATION_PARAM %>"  class="textObj" rows="5" cols="5" maxlength="250"><%=location%></textarea>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				<tr>                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Speaker")%></label></td>                                    				
                                    				
                                    				 <%
	                                    			String speaker = "";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		speaker = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.SPEAKER_PARAM));
						                          	}else{
						                          		speaker = SecurityAPI.encodeHTML(event.getSpeaker());
						                          	}%>
                                        			<td>	
                                        				<input name="<%=EopMaintenance.SPEAKER_PARAM %>" id="<%=EopMaintenance.SPEAKER_PARAM %>" type="text" class="textObj"  maxlength="20" value="<%=speaker %>"  />
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td>                                    				
	                                    				<label ><span style="color:#ec2028;">* </span><%=localeObj.getTranslatedText("Estimated Candidates")%></label>
                                    				</td>
                                    				<%
	                                    			String estdCand = "";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		estdCand = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.ESTD_PARAM));
						                          	}else{
						                          		estdCand = SecurityAPI.encodeHTML(String.valueOf(event.getEstimatedCandidates()));
						                          	}%>
                                        			<td>	
                                        				<input name="<%=EopMaintenance.ESTD_PARAM %>" id="<%=EopMaintenance.ESTD_PARAM %>" type="text" class="textObj"  value="<%=estdCand %>" maxlength="10" />
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Open To")%></label></td>                                 				
                                    			
                                        			<td>	
                                        			<%
                                        			ArrayList<Channel> channelList = ImoUtilityData.getActiveChannels(request);
                                        			%>
                                        				<select name="<%=EopMaintenance.OPENTO_PARAM %>" id="<%=EopMaintenance.OPENTO_PARAM %>"  class="comboObj"  onchange="javascript:checkValue(this.value);"  >
                                        				<option value="0"><%=localeObj.getTranslatedText("No")%></option>
                                        				<%if("SG".equals(openTo)){ %>
                                        					<option value="SG" selected><%=localeObj.getTranslatedText("Yes")%></option>
                                        				<%}else{%>
                                        					<option value="SG"><%=localeObj.getTranslatedText("Yes")%></option>
                                        				<%} %>
                                        					
                                        				<%-- <%for(Channel channel : channelList){ %>
                                       						<option value="<%=channel.getChannelCode()%>" <%=String.valueOf(channel.getChannelCode()).equals(openTo)?"selected" : "" %>><%=channel.getChannelName()%></option>
                                       					<%} %>	  --%>                                      	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                                      			    <tr  id="specialGroup" style="display:none;">                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Upload Special Group Listing")%></label>
                                    				
                                    				</td>	                                    				
                                        			<td style="text-align: left;border: none;">	
                                        				<input name="UploadSpecialGroupListing" id="UploadSpecialGroupListing" type="file" class="fileObj"  onchange="uploadCSVFile();"/>          
                                       	 			<!-- <div id="spinner">
       													Loading...
   												   </div> -->
                                       	 			</td> 
                                       	 			
                                      			    </tr>
                                      			    <tr>
                                      			    <td></td>
                                      			    <td style="text-align: left;border: none;"><a href="format/specialGroup.xls"  target="_new"><%=localeObj.getTranslatedText("Sample Template")%></a></td>
                                      			    </tr>
                                      			    <tr>
                                      			    
                                      			    
                                      			    
											 		<td style="border: none;"><input type="hidden" name="uploadSpecialGrp"  id="uploadSpecialGrp" value="" /></td>
													<td style="text-align:left;">  <div id="csvFilename"></div></td>
										 			</tr>    
                            		 				<tr>                         
                                    				<td>                                  				
	                                    				<label ><%=localeObj.getTranslatedText("Open To Public Registration")%></label>
                                    				</td>
                                    				<%
	                                    			String checked = "";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          	  if(("Y").equals(SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.PUBREG_PARAM))))
						                          		checked = "checked";
						                          	  else
						                          		checked = "";
						                          	}else{
						                          		 if(SecurityAPI.encodeHTML(event.getOpenToRegistration()).equals("Y"))
						                          			checked = "checked";
							                          	 else
							                          		checked = "";
						                          			 
						                          	}%>
                                        			<td>	
                                        				<input name="<%=EopMaintenance.PUBREG_PARAM %>" id="<%=EopMaintenance.PUBREG_PARAM %>" class="checkObj" type="checkbox" value = "N" <%=checked%> />
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				<tr>                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Organizer")%></label></td>                                    				
                                    				
                                    				<%
	                                    			String organiser = "";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		organiser = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.ORGANISER_PARAM));
						                          	}else{
						                          		organiser = SecurityAPI.encodeHTML(String.valueOf(event.getOrganizer()));
						                          	}%>
                                        			<td>	
                                        				<select name="<%=EopMaintenance.ORGANISER_PARAM %>" id="<%=EopMaintenance.ORGANISER_PARAM %>"  class="comboObj" >
                                        					   <option value="0" <%="0".equals(organiser)?"selected":"" %>><%=localeObj.getTranslatedText("Select")%></option>
                                        					   <%if(user.isCho()){ %>
                                        					   		<option value="1" <%="1".equals(organiser)?"selected":"" %>><%=localeObj.getTranslatedText("CHO")%></option>
                                        					   <%} %>
                                        					    
                                       						    
		                            						   <option value="2" <%="2".equals(organiser)?"selected":"" %>><%=localeObj.getTranslatedText("Bu")%></option> 
		                            						   <option value="3" <%="3".equals(organiser)?"selected":"" %>><%=localeObj.getTranslatedText("District")%></option>
		                            						    <option value="7" <%="7".equals(organiser)?"selected":"" %>><%=localeObj.getTranslatedText("Branch")%></option>
		                            						   <option value="4" <%="4".equals(organiser)?"selected":"" %>><%=localeObj.getTranslatedText("City")%></option> 
                          						               <option value="5" <%="5".equals(organiser)?"selected":"" %>><%=localeObj.getTranslatedText("SSC")%></option>   
                          						               <option value="8" <%="8".equals(organiser)?"selected":"" %>><%=localeObj.getTranslatedText("Office")%></option> 
                          						               <%if(!userObj.isSscLevel()){ %>    
                          						               	<option value="6" <%="6".equals(organiser)?"selected":"" %>><%=localeObj.getTranslatedText("Agent Team")%></option>
                          						               <%} %>                                         	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				<tr>                         
                                    				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("BU")%></label></td>                                    				
                                        			<td>	
                                        			<% if(userObj.getUserType().equals("AD")){ %>
                                        				<select name="<%=EopMaintenance.BU_PARAM %>" id="<%=EopMaintenance.BU_PARAM %>"  class="comboObj"   onchange="getDistrict(this.value,0,'E');">
                                       						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>	                                       	
                                       	 				 </select>
                                       	 			<%}else{ %>
                                       	 				<select name="<%=EopMaintenance.BU_PARAM %>" id="<%=EopMaintenance.BU_PARAM %>"  class="comboObj">
                                       						<option value="<%=userObj.getBuCode() %>" ><%=imoUtilityDate.getBuCode(userObj.getBuCode()) %></option>                                       	
                                       	 				 </select>
                                       	 			<%} %>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 					
                            		 				<tr>                         
                                    				<td>                                    				
	                                    				<label ><%=localeObj.getTranslatedText("District")%></label>
                                    				</td>
                                        			<td>	
                                        			<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel()){ %>
                                        				<select name="<%=EopMaintenance.DISTRICT_PARAM %>" id="<%=EopMaintenance.DISTRICT_PARAM %>"  class="comboObj"  onchange="getBranch(this.value,0,'E');">
                            								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option> 	                                 	
                            	 		  				 </select>
                            	 		  			 <%}else{ %>
                            	 		  			 	<select name="<%=EopMaintenance.DISTRICT_PARAM %>" id="<%=EopMaintenance.DISTRICT_PARAM %>"  class="comboObj">
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
                                        				<select name="branch" id="branch"  class="comboObj"  onchange="getCity(this.value,0,$('#district').val(),'E');">
                            								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option> 	                                 	
                            	 		  				 </select>
                            	 		  			 <%}else{ %>
                            	 		  			 	<select name="branch" id="branch"  class="comboObj"  onchange="getCity(this.value,0,$('#district').val(),'E');">
                            								<option value="<%=userObj.getBranchCode() %>" ><%= imoUtilityDate.getBranchCode(userObj.getBranchCode()) %></option>
                            								                         	
                            	 		  				</select>
                            	 		  			 <%} %>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td>                                    				
	                                    				<label ><%=localeObj.getTranslatedText("City")%></label>
                                    				</td>
                                        			<td>	
                                        			<% if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                                        				<select name="<%=EopMaintenance.CITY_PARAM %>" id="<%=EopMaintenance.CITY_PARAM %>"  class="comboObj"  onchange="getSSC(this.value,0,'E');" >
                                       						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                                       	 				 </select>
                                       	 			<%}else{ %>
                                       	 				<select name="<%=EopMaintenance.CITY_PARAM %>" id="<%=EopMaintenance.CITY_PARAM %>"  class="comboObj">
                                       						<option value="<%=userObj.getCityCode() %>" ><%=imoUtilityDate.getCityName(userObj.getCityCode()) %></option>		                                       	
                                       	 				 </select>
                                       	 			<%} %>
                                       	 			
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td>                                  				
	                                    				<label ><%=localeObj.getTranslatedText("SSC")%></label>
                                    				</td>
                                        			<td style="text-align: left;">
                                        			<% if(userObj.getUserType().equals("AD") || userObj.isCityLevel() ||  userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                                        				<select name="<%=EopMaintenance.SSC_PARAM %>" id="<%=EopMaintenance.SSC_PARAM %>"  class="comboObj" onchange="getOffice(this.value,0);" >
                                       						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                                       	 				 </select>
                                       	 			 <%}else{ %>
                                       	 			 <select name="<%=EopMaintenance.SSC_PARAM %>" id="<%=EopMaintenance.SSC_PARAM %>"  class="comboObj">
                                       						<option value="<%=userObj.getSscCode() %>" ><%=imoUtilityDate.getSscName(userObj.getSscCode()) %></option> 	                                       	
                                       	 				 </select>
                                       	 			 <%} %>
                                       	 			</td>                                     			   
                                      			    </tr>
                                      			     <tr>                         
		                   							<td style="border: none;"  >                                  				
		                    							<label ><%=localeObj.getTranslatedText("Office")%></label>
		                   							</td>
		                       					<td>
		                       					<% if(userObj.getUserType().equals("AD") ||  userObj.isSscLevel() || userObj.isCityLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>		
		                       				<select name="office" id="office"  class="comboObj" onchange="getAgentTeam1();"  >
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
                                    				<td><label ><%=localeObj.getTranslatedText("Agent Team")%></label></td>                                  				
                                    				
                                    			<%-- 	<%String agentTeam = "";
						                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
						                          		agentTeam = SecurityAPI.encodeHTML(request.getParameter(EopMaintenance.AGENT_TEAM_PARAM));
						                          	}else{
						                          		agentTeam = SecurityAPI.encodeHTML(String.valueOf(event.getAgentTeam()));
						                          	}%> --%>
                                        			<td>	
                                        				<select name="<%=EopMaintenance.AGENT_TEAM_PARAM %>" id="<%=EopMaintenance.AGENT_TEAM_PARAM %>"  class="comboObj" >
                                       						   <option value="0"><%=localeObj.getTranslatedText("Select")%></option>                                        	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				<tr>                         
                                    				<td>	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Profile Upload")%></label>
	                                    				</br><label><%=localeObj.getTranslatedText("Maximum 5MB")%></label>
                                    				</td>
                                        			<td id="ProfileMaterialTD">	
                                        				<input name="ProfileMaterial" id="ProfileMaterial" type="file" class="fileObj" onchange="uploadMaterial();"/>                
                                    					<!-- <div id="spinner">
       													Loading...
   														</div> -->
                                       	 			</td> 
                                      			    </tr>
                                      			    <tr >
											 		<td style="border: none;"><input type="hidden" name="uploadProfileFile"  id="uploadProfileFile" value="" /></td>
											 		<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  %>
													<td style="border: none;text-align:left;"><div id="materialname"><%=fname %></div></td>
													<% }else{
											if(event.getProfilePath()!=null && event.getProfilePath().length() > 0){ %>
												
												<td  style="border: none;text-align:left;"><a href="<%=profile_path %> " target="_new"  ><div id="materialname"><%=fname %></div></a> </td>
											<%}
											}
											%>
										 			</tr>    
										 			<tr>                         
                                    				<td>	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Topic Upload")%></label>
	                                    				</br><label><%=localeObj.getTranslatedText("Maximum 5MB")%></label>
                                    				</td>
                                        			<td id="TopicFileTD">	
                                        				<input name="TopicFile" id="TopicFile" type="file"class="fileObj"  onchange="uploadEopTopic();"/>                
                                    					<!-- <div id="spinner">
       													Loading...
   														</div> -->
                                       	 			</td> 
                                      			    </tr>
                                      			    <tr>
											 		<td><input type="hidden" name="uploadTopicFile"  id="uploadTopicFile" value="" /></td>
											 		<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  %>
													<td style="border: none;text-align:left;"><div id="topicFile"><%=topicFileName %></div></td>
													<% }else{
											if(event.getTopicPath()!=null && event.getTopicPath().length() > 0){ %>
												
												<td  style="border: none;text-align:left;"><a href="<%=topic_path %> " target="_new"  ><div id="materialname"><%=topicFileName %></div></a> </td>
											<%}
											}
											%>
										 			</tr>    
<!--                             		 				<tr> -->
<%--                                     				<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Created By")%></label></td>                                  				 --%>
                                    				
                                    				<%
		                           			    	String createdBy = "";
							                       if(modifyFlag){
							                    	   createdBy = SecurityAPI.encodeHTML(event.getUserName());
							                       	}
							                       	else{
							                       		createdBy = SecurityAPI.encodeHTML(userObj.getStaffName());
							                       	} 
							                       
					                      		  %>
<!--                                         			<td>	 -->
<%--                                         				<input name="<%=EopMaintenance.CREATEDBY_PARAM %>" id="<%=EopMaintenance.CREATEDBY_PARAM %>" type="text" class="textObj" value="<%=createdBy %>" disabled /> --%>
<!--                                        	 			</td>                                     			    -->
<!--                                       			    </tr> -->
													
                                      			    <tr>                         
                                        			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                                        				<a href="#"class="btn1" onclick="javascript:submitForm();" ><%=localeObj.getTranslatedText("Submit")%></a>
                            		 					<a href="ContentManager?key=EopMaintenance" class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a> 
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				 <tr>
											              <td style="border: none;"></td>
											              <td style="border: none;"></td>
											              <td style="border: none;"></td>
										              </tr>
										               <tr>
											              <td style="border: none;"></td>
											              <td style="border: none;"></td>
											              <td style="border: none;"></td>
										              </tr>
                                       				</table>
                                       				</form>
              
				</div>
			</div>
		
  	
