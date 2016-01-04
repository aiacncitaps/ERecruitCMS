<%--
  - Author(s):          Hemraj Rathod
  - Date:               06-Aug-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Interview Listing Page
--%>
<%@page import="java.util.Map"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.utilities.KeyObjPair"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="com.quix.aia.cn.imo.mapper.InterviewMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.utilities.MsgObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ page import='com.quix.aia.cn.imo.data.user.*'%>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes'%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>

<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Pager pager = null;
int noOfInterviews=0;
if(request.getAttribute(RequestAttributes.PAGING_OBJECT)!=null){
    pager=(Pager) request.getAttribute(RequestAttributes.PAGING_OBJECT);
    noOfInterviews= ((Pager)request.getAttribute(RequestAttributes.PAGING_OBJECT)).getActualSize();
}
KeyObjPair keyObjPair = null;
User userObj = (User)request.getSession().getAttribute("currUserObj");

ImoUtilityData imoUtility=new ImoUtilityData();

String interviewName="";
String candidateName ="";
int year = GregorianCalendar.getInstance().get( Calendar.YEAR );
int month = GregorianCalendar.getInstance().get( Calendar.MONTH )+1;
if(request.getSession().getAttribute("INT_SEARCH_OBJ")!=null){
	Map<String,String> holMap = (Map)request.getSession().getAttribute("INT_SEARCH_OBJ");
	interviewName = holMap.get("agent_code");
}
%>


<script type="text/javascript">
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");

    $( ".datepicker" ).datepicker({
    	showOn: "button",
    	buttonImage: "images/calendar_icon.gif",
    	buttonImageOnly: true,
    	dateFormat:"dd/mm/yy",
    	changeMonth: true, 
    	changeYear: true
    	});  
    $("#txtSubForm").val("N");
});

function interviewSearch() 
{
 document.ccTestForm.submit() ;
}
function submitForm(){
	$("#txtSubForm").val("Y");
	document.subForm.submit();
}

</script>


	<div id="maincontainer">
		<div class="head">
			<h2 align="center"><%=localeObj.getTranslatedText("CC Test Maintenance")%></h2>
		</div>
		<div class="content" style="background-color: #ffffff;">
			<form name="ccTestForm" method="post" action="ContentManager"
				class="PT20">
				<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
				<table class="formDesign">
					<%if(request.getAttribute(RequestAttributes.MESSAGE_OBJ) != null){%>

					<tr>
						<td colspan="2" style="text-align: center"><span
							style="color: #ec2028;"><%=localeObj.getTranslatedText(((MsgObject)request.getAttribute(RequestAttributes.MESSAGE_OBJ)).getMsg())%></span>
						</td>
					</tr>
					<%
						  request.removeAttribute(RequestAttributes.MESSAGE_OBJ);
					  }%>

					<tr>
						<td><label> <%=localeObj.getTranslatedText("Agent Id")%></label></td>
						<td><input name="agent_code" id="agent_code" maxlength="10" type="text" class="textObj" value="<%=interviewName%>" /></td>
					</tr>

					<tr>
						<td colspan="2" class="MT30 MB30"
							style="text-align: center; padding-top: 20px"><a href="#"
							class="btn1" onclick="javascript:interviewSearch()"> <%=localeObj.getTranslatedText("Search")%></a>
							<a href="javascript:document.ccTestForm.reset()"
							class="ML10 btn1"><%=localeObj.getTranslatedText("Clear")%></a></td>
					</tr>

				</table>
			</form>

			<div class="MT30">
				<table style="width: 100%">
					<tr>
						<td align="left">
						</td>
						<td align="right" style="font-weight: bold;">
							<%if(pager != null){ %> <%=localeObj.getTranslatedText("Search Result")%>
							: <%=noOfInterviews %> <%}%>
						</td>
					</tr>
				</table>
			</div>

			<%if(pager != null){ %>
			<form method="post" action="ContentManager" name="subForm">
			<input type="hidden" id="txtSubForm" name="txtSubForm" value="">
			<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
			<div class="data_table  MT20 MB20" style="width:100%">
				<table class="subFormDesign">
					<thead>
						<tr>
							<th width="33%"><label><%=localeObj.getTranslatedText("Interview Session Name")%></label></th>
							<th width="33%"><label><%=localeObj.getTranslatedText("CC Test")%></label></th>
							<th width="33%"><label><%=localeObj.getTranslatedText("CC Test Date")%></label></th>
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
								<td colspan="3" bordercolor="#FFFFFF">
									<div align="center">
										<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
									</div>
								</td>
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
							<table class="data_table_pagination" width="100%" cellpadding="0"
								cellspacing="0">
								<tr>
									<td style="width: 10%">
										<div align="center">
											<span class="button_label"> <!-- Prev Page Link --> <pg:prev
													export="pageUrl">
													<a href="<%= pageUrl%>" class="button_label"
														style="text-decoration: none; color: #ffffff; font-weight: bold"><%=localeObj.getTranslatedText("Previous")%></a>
												</pg:prev>
											</span>
										</div>
									</td>
									<td>
										<div align="center">
											<pg:pages>
												<%if (pageNumber.intValue() < PagingInfo.SEARCH_INDEX_SIZE) {%> 
									
										&nbsp;
									<%}if (pageNumber == currentPageNumber) {%>
												<span style="color: orange"><b> <%= pageNumber %>
												</b></span>
												<%} else {%>
												<a href="<%= pageUrl %>"
													style="text-decoration: none; color: #ffffff; font-weight: bold; padding: 5px;"><%= pageNumber %></a>
												<%}%>

											</pg:pages>
										</div>
									</td>
									<td style="width: 10%">
										<div align="center">
											<span class="button_label"> <!-- Prev Page Link --> <pg:next
													export="pageUrl">
													<a href="<%= pageUrl%>" class="button_label"
														style="text-decoration: none; color: #ffffff; font-weight: bold"><%=localeObj.getTranslatedText("Next")%></a>
												</pg:next>
											</span>
										</div>
									</td>
								</tr>
							</table>
							<%}%>
						</pg:pager>
						<%}
			  	else
			  	{%>
						<tr>
							<td colspan="3" bordercolor="#FFFFFF">
								<div align="center">
									<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
								</div>
							</td>
						</tr>
						<%} %>
						
					</tbody>
					 	
				</table>
				</div>
				
				<div>
                   	<a href="#" class="btn1" onclick="submitForm();" ><%=localeObj.getTranslatedText("Update")%></a>
                 </div>
				</form>
				<%}%>
				<br>
			

		</div>
	</div>
	







