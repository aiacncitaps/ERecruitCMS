<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@page import="com.quix.aia.cn.imo.utilities.MsgObject"%>
<%@page import="com.quix.aia.cn.imo.utilities.ErrorObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>

<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
%>

<script type="text/javascript">
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
$(document).ready(function(){
	 $("#submit").hide();
});
 function uploadCSV(){
	 $('#ajaxLoader').find(".lightbox").show();	
	if($('input[type=file]').get(0).files[0] !=undefined){
		var csvFile = $('input[type=file]').get(0).files[0];
		var file_extension=csvFile.name;
		if((file_extension.indexOf('.csv')>-1) || (file_extension.indexOf('.CSV')>-1)){
		//	showProgress();
		var fd = new FormData();
		fd.append('csvFile', csvFile);
		var fileName=$("#csv_file").val();
		
		  $.ajax({
				url : 'UploadMaterial?image_name='+fileName,
				type: "POST",
				data: fd,
			   	processData: false,
			   	contentType: false,
			}).done(function(respond){
				
			});
		 // hideProgress();
		 // alert("CSV file Uploaded Successfully");
		  $("#submit").show();
		}
		else{
			alert("Please Upload file with extension .csv");
		}
   }
  else{
		alert("Please Upload a file");
   }
	$('#ajaxLoader').find(".lightbox").hide();	
 }
function holidayCSVSubmit(action) 
{
 document.holidayCSVForm.actionType.value = action ;
 document.holidayCSVForm.submit() ;
  
}

</script>


	<div id="maincontainer">

		<div class="head">
			<h2 align="center"><%=localeObj.getTranslatedText("Holiday Maintenance Upload CSV")%></h2>
		</div>
		<div class="content" style="background-color: #ffffff;">
			<form name="holidayCSVForm" method="post" action="FormManager"
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
						<td><label><%=localeObj.getTranslatedText("Upload Holiday CSV")%></label>
						</td>
						<td><input type="file" name="csv_file" id="csv_file"
							class="fileObj" onChange="javascript:uploadCSV()" /></td>

					</tr>

					<tr>
						<td colspan="3" class="MT30 MB30"
							style="text-align: center; padding-top: 20px"><a href="#"
							class="btn1 " id="submit" name="submit"
							onclick="javascript:holidayCSVSubmit('<%=FormInfo.SUBMIT%>')"><%=localeObj.getTranslatedText("Submit")%></a>
							<a href="ContentManager?key=HolidayMaintenance" class="btn1"><%=localeObj.getTranslatedText("Back")%></a>

							                        <%
                            		 				    if(request.getSession().getAttribute("Summarypath")!=null && request.getSession().getAttribute("strbuf")!=null ){ 
                                       	 	            String summaryPath=(String)request.getSession().getAttribute("Summarypath");
                                       	 			%> 
                                       	 			<a href="<%=summaryPath%>" target="_new" class="btn1"><%=localeObj.getTranslatedText("Download Error Summary")%></a>
                                                   <%}%>
                  </td>
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
