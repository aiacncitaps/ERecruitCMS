<%--
  - Author(s):          Nibedita
  - Date:               16-June-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Attendance Details listed here
--%>
<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.data.event.Event"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %> 
<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Event event = (Event)request.getSession().getAttribute("EventObj");
com.quix.aia.cn.imo.utilities.Pager pager=null;
if( request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)!=null){
	pager = (com.quix.aia.cn.imo.utilities.Pager) request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
	
} else{
	pager = (com.quix.aia.cn.imo.utilities.Pager) request.getSession().getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
} 

boolean regFlag = false;
if(event!=null && event.getEvent_code() !=0){
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat ra = new SimpleDateFormat("HH:mm a");
	try {
		Date todayDate = new Date();
		if(sdf1.parse(sdf1.format(todayDate)).equals(event.getEventDate())){
			Date crtTime=new Date(System.currentTimeMillis());
			Date stTime=event.getStartTime();
			crtTime=ra.parse(ra.format(crtTime));
			stTime=ra.parse(ra.format(stTime));
			if(crtTime.after(stTime))
			{
				regFlag = true;
			
			}
		
		}else if(sdf1.parse(sdf1.format(todayDate)).after(event.getEventDate())){
			regFlag = true;
			
		}} catch (ParseException e) {
			e.printStackTrace();
		}
}
%>
<style>
.data_table table thead th label{
	font-family: 'DINFactBoldRegular';
    font-size: 11px;
 	text-transform: uppercase;
}
</style>

<script type="text/javascript">//<![CDATA[
function confirmDelete(code){
	var dialog = $('<p><%=localeObj.getTranslatedText("Are you sure you want to delete this Candidate?")%></p>').dialog({
   	 width: 550,
    	 cache: false,
        buttons: {
            "<%=localeObj.getTranslatedText("Yes")%>": function() {
            	window.location.href = 'FormManager?key=EopCandidateReg&type=DELETE&candidateCode='+code;
            },
            "<%=localeObj.getTranslatedText("No")%>":  function() {$('.ui-dialog').hide();},
            
        }
    });
}         
function submitForm(){
	document.subForm.submit();
}
             
</script>
<script>
$(function(){
	//$('form').jqTransform({imgPath:'jqtransformplugin/img/'});

	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});


</script>

   <div id="maincontainer">
                        <div class="head">
                                		<h2><%=localeObj.getTranslatedText("EOP Attendance Details")%></h2>
						</div>
						<div class="content" style="background-color:#ffffff;">	
						 <div class="headMsg">
					
						</div>
                            <form method="post" action="" name="newUserForm" class="PT20">
                            <input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
                            <table class="formDesign" style="width:90%;">
	                             <%
				                     if(request.getAttribute(RequestAttributes.MESSAGE_OBJ)!=null)
				                     {
				                      %>	
	                               	  <tr>                         
		                                  <td colspan="8" style="text-align: center">                                    				
		                                    	<span style="color:#ec2028;"><%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.MsgObject)request.getAttribute(RequestAttributes.MESSAGE_OBJ)).getMsg())%></span>
		                                  </td>
	                                       <%	request.removeAttribute(RequestAttributes.MESSAGE_OBJ);%>
	                                  </tr>
				                    <% } %>       
                               				 <tr>
                                				<td style="border:none;width:7%" ><label><%=localeObj.getTranslatedText("Event Name")%></label></td>
                                				<td style="border:none;width:15%">: <%=SecurityAPI.encodeHTML(event.getEventName())%></td>
                                				<td style="border:none;width:4%"><label><%=localeObj.getTranslatedText("Date")%></label></td>
                                				<td style="border:none;width:12%"> : <%=event.getEventDateStr()%></td>
                                				<td style="border:none; width:8%"><label><%=localeObj.getTranslatedText("Time Start")%></label></td>
                                				<td style="border:none;width:7%">: <%=SecurityAPI.encodeHTML(LMSUtil.converDateIntoHHMMAMPM(event.getStartTime()))%></td>
                                				
                                			</tr>
                                			<tr>
                                			
                                			
                                				<td style="border:none"><label><%=localeObj.getTranslatedText("BU")%></label></td>
                                				<td style="border:none">: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(event.getBuName()))%></td>
                                				<td style="border:none"><label><%=localeObj.getTranslatedText("Organizer")%></label></td>
                                				<td style="border:none"> : <%=SecurityAPI.encodeHTML(event.getOraganiserStr())%></td>
                                				<td style="border:none"><label><%=localeObj.getTranslatedText("Time Ends")%></label></td>
                                				<td style="border:none">:<%=SecurityAPI.encodeHTML(LMSUtil.converDateIntoHHMMAMPM(event.getEndTime()))%></td>
                                				
                                			</tr>
                                			<tr>
                                			    <td style="border:none"><label><%=localeObj.getTranslatedText("District")%></label></td>
                                				<td style="border:none">: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(event.getDistName()))%></td>
                                				<td style="border:none"><label><%=localeObj.getTranslatedText("Speaker")%></label></td>
                                				<td style="border:none"> : <%=SecurityAPI.encodeHTML(event.getSpeaker())%></td>
                                				<td style="border:none"><label><%=localeObj.getTranslatedText("Event Type")%></label></td>
                                				<td style="border:none">: <%=SecurityAPI.encodeHTML(event.getEventType())%></td>
                                				
                                			</tr>
                                			<tr>
                                			    <td style="border:none"><label><%=localeObj.getTranslatedText("Branch")%></label></td>
                                				<td style="border:none">: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(event.getBranchName()))%></td>
                                				<td style="border:none"><label><%=localeObj.getTranslatedText("Public")%></label></td>
                                				<td style="border:none">: <%=event.getOpenToRegistration()%></td>
                                				<td style="border:none"> <label> <%=localeObj.getTranslatedText("Special Group")%></label></td>
                                				<td style="border:none">: <%= event.getOpenToSpGrp()%></td>
                                				
                                			</tr>
                                			
                                			<tr>
                                			<td style="border:none"><label><%=localeObj.getTranslatedText("City")%></label></td>
                                				<td style="border:none">: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(event.getCityName()))%></td>
                                				
                                			   <td style="border:none"><label><%=localeObj.getTranslatedText("Public URL")%></label></td>
                                				<td style="border:none"> <div>: <%=event.getPublicUrl()%></div></td>
                                				<td style="border:none"></td>
                                				<td style="border:none"></td>
                                			</tr>
                                			<tr><td style="border:none"><label><%=localeObj.getTranslatedText("Ssc")%></label></td>
                                				<td style="border:none">: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(event.getSscName()))%></td>
                                				
                                			   <td style="border:none"><label><%=localeObj.getTranslatedText("Agent Team")%></label></td>
                                				<td style="border:none">: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(event.getAgentTeam()))%></td>
                                				<td style="border:none"></td>
                                				<td style="border:none"></td>
                                				
                                			</tr>
                                			<tr>
                                			<%
                                				if(event.getOfficeName()==null || event.getOfficeName().equals("null")){
                                					event.setOfficeName("");
                                				}
                                			%>
                                			   <td style="border:none"><label><%=localeObj.getTranslatedText("Office")%></label></td>
                                				<td style="border:none">: <%=SecurityAPI.encodeHTML(event.getOfficeName())%></td>
                                				 <td style="border:none"><label><%=localeObj.getTranslatedText("Materials")%></label></td>
                                				<td style="border:none">: <%=SecurityAPI.encodeHTML(event.getProfilePath())%></td>
                                				<td style="border:none"></td>
                                				<td style="border:none"></td>
                                			</tr>
                                			
                                </table> 
                                </form>
                                <br>
                            <%if(!regFlag){%>    
                         	 <div style="text-align:left;"><a href="FormManager?key=EopCandidateReg&type=NEW&eventCode=<%=event.getEvent_code() %>"  class="ML10 btn1"><%=localeObj.getTranslatedText("Add Candidate")%></a></div>
							<%} %>
							
							<table class="subFormDesign">
			     		<tr><td colspan="16" class="MT30 MB30" style="text-align:right;padding-top:20px"><%=localeObj.getTranslatedText("Total Candidates Registered:")%></td><td class="MT30 MB30" style="text-align:left;padding-top:20px"><%=event.getRegisteredCount() %></td></tr>
                   		<tr><td colspan="16" class="MT30 MB30" style="text-align:right;padding-top:20px"><%=localeObj.getTranslatedText("Total Attended:")%></td><td class="MT30 MB30" style="text-align:left;padding-top:20px"><%=event.getAttendeeCount() %></td></tr>           
<%--                    		<tr><td colspan="16" class="MT30 MB30" style="text-align:right;padding-top:20px"><%=localeObj.getTranslatedText("Total Completed:")%></td><td class="MT30 MB30" style="text-align:left;padding-top:20px"><%=event.getCompletedCount() %></td></tr>                                                			    --%>
					 	
				   			</table>
							
						<div class="data_table  MT20 MB20" >
						<form method="post" action="ContentManager" name="subForm">
						<input type="hidden" name="eventCode" value="<%=event.getEvent_code()%>"/>
						<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
			           		<table class="subFormDesign">
			           		<thead>
				 		  <tr>
							  	<th><label><%=localeObj.getTranslatedText("Candidate Name")%></label></th>
							    <th><label><%=localeObj.getTranslatedText("Servicing Agent")%></label></th>
							    <th><label><%=localeObj.getTranslatedText("Agent Name")%></label></th>
							 
							   <th><label><%=localeObj.getTranslatedText("BU")%></label></th>
							   <th><label><%=localeObj.getTranslatedText("District")%></label></th>
							    <th><label><%=localeObj.getTranslatedText("Branch")%></label></th>
							   <th><label><%=localeObj.getTranslatedText("City")%></label></th>
							    <th><label><%=localeObj.getTranslatedText("Office")%></label></th>
							   <th><label><%=localeObj.getTranslatedText("SSC")%></label></th>
							   <th><label><%=localeObj.getTranslatedText("Agency Leader Code")%></label></th>
							  <%--  <th><label><%=localeObj.getTranslatedText("Agency Leader Name")%></label></th> --%>
							    <th><label><%=localeObj.getTranslatedText("Team Name")%></label></th>
							   <th><label><%=localeObj.getTranslatedText("Source of Referral")%></label></th> 
							  
						      <th><label><%=localeObj.getTranslatedText("Age")%></label></th>
						      <th><label><%=localeObj.getTranslatedText("Date of Birth")%></label></th>
						      <th><label><%=localeObj.getTranslatedText("Gender")%></label></th>
						      <th><label><%=localeObj.getTranslatedText("Contact Number")%></label></th>
						      <th><label><%=localeObj.getTranslatedText("Registered Participants")%></label></th>
						      <th style="width: 10px;"><label><%=localeObj.getTranslatedText("Time In")%></label></th>
<%-- 						      <th><label><%=localeObj.getTranslatedText("Complete Status")%></label></th> --%>
				 		 </tr> 
			           		</thead>
			           		<tbody>
										
						<%if(pager != null){ %>
						<pg:pager items="<%= pager.getItems().size() %>"
							maxPageItems="500"
							maxIndexPages="<%= pager.getMaxIndexPages() %>"
							isOffset="<%= pager.isOffset() %>"
							export="offset,currentPageNumber=pageNumber" scope="request"
							url="/ContentManager">

							<%for (int i = offset.intValue(),l = Math.min(i + pager.getMaxPageItems(), pager.getItems().size());i < l; i++)
							{%>
							<pg:item>
								<%=(String)pager.getItems().get(i)%>
							</pg:item>
							<%}%>
							<%if(pager.getItems().size() <= 0){%>
							<tr>
								<td colspan="17" bordercolor="#FFFFFF">
									<div align="center">
										<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
									</div></td>
							</tr>
							<%}%>
						</pg:pager>

						<pg:pager items="<%= pager.getItems().size() %>"
							maxPageItems="<%= pager.getMaxPageItems() %>"
							maxIndexPages="<%= pager.getMaxIndexPages() %>"
							isOffset="<%= pager.isOffset() %>"
							export="offset,currentPageNumber=pageNumber" scope="request"
							url="ContentManager">

							<pg:param name="<%=RequestAttributes.PARAMETER_KEY%>"
								value="<%=(String)((FormPageInterface)request.getAttribute(RequestAttributes.CONTENT_PAGE)).getKey()%>" />
							<%-- save pager offset during form changes --%>
							<input type="hidden" name="pager.offset" value="<%= offset %>">
							<%if(pager.getItems().size() > pager.getMaxPageItems()){%>
							<table class="data_table_pagination" width="100%" cellpadding="0" cellspacing="0">
								<tr >
									<td style="width: 10%">
										<div align="center">
											<span class="button_label"> <!-- Prev Page Link -->
												<pg:prev export="pageUrl">
													<a href="<%= pageUrl%>" class="button_label"
														style="text-decoration: none; color: #ffffff; font-weight: bold"><%=localeObj.getTranslatedText("Previous")%></a>
												</pg:prev> </span>
										</div></td>
									<td >
										<div align="center">
											<pg:pages>
												<%if (pageNumber.intValue() < PagingInfo.SEARCH_INDEX_SIZE) {%> 
						
							&nbsp;
						<%}if (pageNumber == currentPageNumber) {%>
												<span style="color: orange"><b> <%= pageNumber %>
												</b>
												</span>
												<%} else {%>
												<a href="<%= pageUrl %>"
													style="text-decoration: none; color: #ffffff; font-weight: bold; padding: 5px;"><%= pageNumber %></a>
												<%}%>
							&nbsp;
										</pg:pages>
										</div></td>
									<td style="width: 10%">
										<div align="center">
											<span class="button_label"> <!-- Prev Page Link -->
												<pg:next export="pageUrl">
													<a href="<%= pageUrl%>" class="button_label"
														style="text-decoration: none; color: #ffffff; font-weight: bold"><%=localeObj.getTranslatedText("Next")%></a>
												</pg:next> </span>
										</div></td>
								</tr>
								<tr><td colspan="3"></td></tr>
							</table>
							<%}%>
						</pg:pager>
						<%}
				 	else
				  	{%>
					
					<tr>
						<td colspan="17" bordercolor="#FFFFFF">
							<div align="center">
								<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
							</div></td>
					</tr>
					<%} %>
						                        
				 </tbody> 
			     </table>
			     </form>
			    </div>
			     <table class="subFormDesign">
			     		 <tr>                         
                   			<td colspan="17" class="MT30 MB30" style="text-align:center;padding-top:20px">
                   				<a href="#" class="btn1" onclick="submitForm();" ><%=localeObj.getTranslatedText("Update")%></a>
       		 					<a href="<%=event.getTempFilePath()%>" class="ML10 btn1"><%=localeObj.getTranslatedText("Download into Excel")%></a> 
       		 					<a href="ContentManager?key=EopMaintenance" class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a> 
                  	 		</td>                                     			   
                   		 </tr>
			    	    <tr><td colspan="17" class="MT30 MB30" style="text-align:right;padding-top:20px"></td></tr>
                   		          
			     </table>
			   
</div>
</div>
                     
                     
                     
                   
             