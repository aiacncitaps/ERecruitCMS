<%--
  - Author(s):          Hemraj
  - Date:               16-July-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Interview Candidate Registration
--%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page import="com.quix.aia.cn.imo.mapper.LogsMaintenance"%>
<%@page import="java.io.File"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.data.interview.Interview"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ page import='com.quix.aia.cn.imo.data.user.*' %>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes'%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %> 

<%try{ %>

<%User user = ((User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ));
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Interview interviewObj=(Interview)request.getSession().getAttribute("selectedObj");
request.getSession().setAttribute("interviewDate", interviewObj.getInterviewDate());

com.quix.aia.cn.imo.utilities.Pager pager=null;
if( request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)!=null){
	pager = (com.quix.aia.cn.imo.utilities.Pager) request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
	
} else{
	pager = (com.quix.aia.cn.imo.utilities.Pager) request.getSession().getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
}
%>

<script type="text/javascript">
$(function(){
	
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
function submitForm(){
	document.subForm.submit();
}

</script>
<style>
.text {
  font-size: 15px;
  padding: 5px 0 5px 5px;
  color: #545454;
  font-family: 'DINFactRegularRegular';
}
</style>
   <div id="maincontainer">
					<div class="head">
						<h2 align="center"><%=localeObj.getTranslatedText("Interview Attendance Details")%></h2>
					</div>
				 <div class="content" style="background-color:#ffffff;">   		               		
                      <form method="post" action="FormManager?key=online_admin" name="newUserForm" class="PT20">
							<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
							
							<%
								System.out.println("**************   Token ************  "+request.getSession().getAttribute("Token")+"");
							%>
							<input type="hidden" name="<%= com.quix.aia.cn.imo.constants.FormInfo.HIDDEN_ACTION%>"/>
						<table class="formDesign" style="width:95%;">
											<tr>
		                                  		<td colspan="8" style="text-align: center">                                    				
													<%if(request.getAttribute(RequestAttributes.MESSAGE_OBJ)!=null)
		                     			  			{%>	
		                                  				<span style="color:#ec2028;"><%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.MsgObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.MESSAGE_OBJ)).getMsg())%></span>
	                                       			<%	request.removeAttribute(RequestAttributes.MESSAGE_OBJ);
	                                       			} %> 
												</td>
											</tr>
                                			<tr>
                                				<td width=15%><label><%=localeObj.getTranslatedText("Interview Session Name")%></label></td>
                                				<td width="15%">: <%=SecurityAPI.encodeHTML(interviewObj.getInterviewSessionName())%></td>
                                				<td width="10%" ><label><%=localeObj.getTranslatedText("Date")%></label></td>
                                				<td width="18%" > : <%=SecurityAPI.encodeHTML(LMSUtil.convertDateToString(interviewObj.getInterviewDate()))%></td>
                                				<td width="10%" ><label><%=localeObj.getTranslatedText("Time Start")%></label></td>
                                				<td width="10%" >: <%=SecurityAPI.encodeHTML(LMSUtil.converDateIntoHHMMAMPM(interviewObj.getStartTime()))%></td>
                                				<td><label><%=localeObj.getTranslatedText("Time Ends")%></label></td>
                                				<td><%=SecurityAPI.encodeHTML(LMSUtil.converDateIntoHHMMAMPM(interviewObj.getEndTime()))%></td>
                                			</tr>
                                			
                                			<tr>
                                				<td ><label><%=localeObj.getTranslatedText("BU")%></label></td>
                                				<td >: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(interviewObj.getBuName()))%></td>
                                				<td ><label><%=localeObj.getTranslatedText("Organizer")%></label></td>
                                				<td > : <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(interviewObj.getOrganizer()))%></td>
                                				<td ><label><%=localeObj.getTranslatedText("Interview Type")%></label></td>
                                				<td >: <%=SecurityAPI.encodeHTML(localeObj.getTranslatedText(interviewObj.getInterviewType()+" Interview"))%></td>
                                				<td ></td>
                                				<td ></td>
                                			</tr>
                                			
                                			<tr>
                                				<td ><label><%=localeObj.getTranslatedText("District")%></label></td>
                                				<td >: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(interviewObj.getDistName()))%></td>
                                				<td ><label><%=localeObj.getTranslatedText("Location")%></label></td>
                                				<td   valign="top"> : <%=SecurityAPI.encodeHTML(interviewObj.getLocation())%> </td>
                                				<td ></td>
                                				<td ></td>
                                				<td ></td>
                                				<td ></td>
                                			</tr>
                                			
                                			<tr>
                                				<td ><label><%=localeObj.getTranslatedText("City")%></label></td>
                                				<td >: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(interviewObj.getCityName()))%></td>
                                				<td ><label><%=localeObj.getTranslatedText("Branch")%></label></td>
                                				<td >: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(interviewObj.getBranchName()))%></td>
                                  				<td ><label><%=localeObj.getTranslatedText("Office")%></label></td>
                                				<td >: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(interviewObj.getOfficeName()))%></td>
                                				<td ></td>
                                				<td ></td>
                                			</tr>
                                			
                                			<tr>
                                				<td ><label><%=localeObj.getTranslatedText("SSC")%></label></td>
                                				<td >: <%=SecurityAPI.encodeHTML(LMSUtil.blankIfNull(interviewObj.getSscName()))%></td>
                                				<td ></td>
                                				<td ></td>
                                  				<td ></td>
                                				<td ></td>
                                				<td ></td>
                                				<td ></td>
                                			</tr>
                                			
                                			<tr>
                                				<td ><label><%=localeObj.getTranslatedText("Interview Materials")%></label></td>
                                				<td >: <%=SecurityAPI.encodeHTML(interviewObj.getInterviewMaterial())%></td>
                                				<td ></td>
                                				<td ></td>
                                  				<td ></td>
                                				<td ></td>
                                				<td ></td>
                                				<td ></td>
                                			</tr>
                                		</table>
										</form>	
	                                <div class="clear"></div>
	                              
                                             
                                 <!-- ---search--- -->
	            <form method="post" action="ContentManager" name="subForm">
	            <input type="hidden" name="interview_code" value="<%=interviewObj.getInterview_code()%>"/>
	            <input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
				<div class="data_table  MT20 MB20"  >
							<table class="subFormDesign">
								<thead>
								<tr>
								<th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Candidate Name")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Servicing Agent")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Agent Name")%></label></th>
							    
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("BU")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("District")%></label></th>
							     <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Branch")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("City")%></label></th>
							      <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Office")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("SSC")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Agency Leader Code")%></label></th>
							 <%--    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Agency Leader Name")%></label></th> --%>
							     <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Team Name")%></label></th>
							    
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Source of Referral")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Age")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Date of Birth")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Gender")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Contact Number")%></label></th>
								
							    <th style="padding: 8px 20px;"><label><%=localeObj.getTranslatedText("CC Test Result")%></label></th>
								<th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Recruitment Scheme")%></label></th>
								<th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("View Interview Records")%></label></th>
								 <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("View E-Application")%></label></th> 
								
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Documents Upload")%></label></th>
							    
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("P100")%></label></th>
							    <th style="padding: 8px 20px;"><label><%=localeObj.getTranslatedText("Interview Result")%></label></th>
							    <th style="padding: 8px 1px;"><label><%=localeObj.getTranslatedText("Remarks (maximum 100 characters)")%></label></th>
								<th style="padding: 8px 20px;"><label><%=localeObj.getTranslatedText("Interviewer Name")%></label></th>
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
								<td colspan="21" bordercolor="#FFFFFF">
									<div align="center" style="font-size: 20px">
										<strong> <%=localeObj.getTranslatedText("No Candidate Found")%></strong>
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
						<td colspan="21" bordercolor="#FFFFFF;`">
							<div align="center" style="font-size: 20px">
								<strong><%=localeObj.getTranslatedText("No Candidate Found")%></strong>
							</div>
						</td>
					</tr>
					<%} %>
					   
					  </tbody>
					 	 <tr>                         
                   			<td colspan="21" class="MT30 MB30" style="text-align:center;padding-top:20px">
                   				<a href="#" class="btn1" onclick="submitForm();" ><%=localeObj.getTranslatedText("Update")%></a>
       		 					<a href="<%=interviewObj.getTempFilePath() %>" class="ML10 btn1"><%=localeObj.getTranslatedText("Download Result in Excel format")%></a>
       		 					<a href="ContentManager?key=InterviewMaintenance" class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a> 
                  	 		</td>                                     			   
                   		 </tr>
						</table>
				</div>
				</form>				
</div>
</div>

<%}catch(Exception e){
	
	
	e.printStackTrace();
	LogsMaintenance logsMain=new LogsMaintenance();
	StringWriter errors = new StringWriter();
	e.printStackTrace(new PrintWriter(errors));
	logsMain.insertLogs("InterviewAttendanceDetails jsp page ","SEVERE"+"",errors.toString());
	
} %>          
    			<!--content end-->
    			
