<%--
  - Author(s):          Jinatmayee
  - Date:               12-May-2015
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
int bu=0;
int dis=0;
String city="0";
int branch=0;
String ssc="0";
String office="0";
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Pager pager = null;
int noOfInterviews=0;
if(request.getAttribute(RequestAttributes.PAGING_OBJECT)!=null){
    pager=(Pager) request.getAttribute(RequestAttributes.PAGING_OBJECT);
    noOfInterviews= ((Pager)request.getAttribute(RequestAttributes.PAGING_OBJECT)).getActualSize();
}
KeyObjPair keyObjPair = null;
User userObj = (User)request.getSession().getAttribute("currUserObj");

bu=userObj.getBuCode();
dis=userObj.getDistrict();
city=userObj.getCityCode()+"";
ssc=userObj.getSscCode()+"";
branch=userObj.getBranchCode();
office=userObj.getOfficeCode()+"";

ImoUtilityData imoUtility=new ImoUtilityData();

String interviewName="";
String candidateName ="",interviewType = "";
int year = GregorianCalendar.getInstance().get( Calendar.YEAR );
int month = GregorianCalendar.getInstance().get( Calendar.MONTH )+1;
if(request.getSession().getAttribute("INT_SEARCH_OBJ")!=null){
	Map<String,String> holMap = (Map)request.getSession().getAttribute("INT_SEARCH_OBJ");
	interviewName = holMap.get("interviewName");
	interviewType = holMap.get("interviewType");
	candidateName = holMap.get("candidateName");
	month = Integer.parseInt(holMap.get("month"));
    year = Integer.parseInt(holMap.get("year"));
	bu = Integer.parseInt(holMap.get("bu"));
	dis = Integer.parseInt(holMap.get("district"));
	city = holMap.get("city");
	ssc = holMap.get("ssc");
	branch=Integer.parseInt(holMap.get("branch"));
	office=holMap.get("office");
}
%>


<script type="text/javascript">
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
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
		 
		getCity('<%=dis %>','<%=city %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
		
		getSSC('<%=city %>','<%=ssc %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
	
	getOffice('<%=ssc %>','<%=office %>');
<% }%>

		
});

function interviewSearch() 
{
 document.interviewForm.submit() ;
  
}
function confirmDelete(interviewCode){
	var str="<%=localeObj.getTranslatedText("Are you sure you want to delete this interview? Doing so will also delete participant records.")%>";
	var dialog = $('<p>'+str+'</p>').dialog({
   	 width: 550,
    	 cache: false,
        buttons: {
            "<%=localeObj.getTranslatedText("Yes")%>": function() {
            	window.location.href = 'FormManager?key=InterviewAdd&type=DELETE&interview_code='+interviewCode;
            },
            "<%=localeObj.getTranslatedText("No")%>":  function() {
            	$('.ui-dialog').hide();
            },
            
        }
    });
}

function resetSearchForm(){
	var d = new Date();
	$("#interviewName").val("");
	$("#interviewType").val("0");
	$("#name").val("");
	$("#month").val(d.getMonth()+1);
	$("#year").val(d.getFullYear());
	$("#bu").val("0");
	$("#district").val('0');
	$("#branch").val('0');
	$("#city").val('0');
	$("#ssc").val('0');
	$("#office").val('0');
}

</script>


	<div id="maincontainer">
		<div class="head">
			<h2 align="center"><%=localeObj.getTranslatedText("Interview Maintenance")%></h2>
		</div>
		<div class="content" style="background-color: #ffffff;">
			<form name="interviewForm" method="post" action="ContentManager"
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
						<td><label> <%=localeObj.getTranslatedText("Interview Session Name")%></label>
						</td>
						<td><input name="interviewName" id="interviewName"
							type="text" class="textObj" value="<%=interviewName%>" /></td>
					</tr>

					<tr>
						<td> <label><%=localeObj.getTranslatedText("Interview Type")%></label>
						</td>
						<td><select name="interviewType" id="interviewType"
							class="comboObj">
							<option value="0" <%="0".equals(interviewType) ? "selected" : "" %>><%=localeObj.getTranslatedText("All")%></option>
								<option value="2nd"  <%="2nd".equals(interviewType)  ? "selected" : "" %>><%=localeObj.getTranslatedText("2nd Interview")%></option>
								<option value="3rd"  <%="3rd".equals(interviewType)  ? "selected" : "" %>><%=localeObj.getTranslatedText("3rd Interview")%></option>
						</select></td>
					</tr>
					
					
					<tr>
						<td><label> <%=localeObj.getTranslatedText("Candidate Name")%></label>
						</td>
						<td><input name="name" id="name" type="text" class="textObj"
							value="<%=candidateName%>" /></td>
					</tr>

					<tr>
						<td><label><%=localeObj.getTranslatedText("Month")%></label>
						</td>
						<td><select name="month" id="month" class="comboObj"
							style="width: 35%;">
								<%for(int i=0; i<InterviewMaintenance.MONTH_VALUE_PAIR.length; i++){%>
								<%keyObjPair = InterviewMaintenance.MONTH_VALUE_PAIR[i];%>
								<%if(Integer.parseInt(keyObjPair.getKey().toString()) == month){%>
								<option value="<%=keyObjPair.getKey()%>" selected><%=localeObj.getTranslatedText((String)keyObjPair.getObj())%></option>
								<%}else{%>
								<option value="<%=keyObjPair.getKey()%>"><%=localeObj.getTranslatedText((String)keyObjPair.getObj())%></option>
								<%}%>
								<%}%>
						</select> <select name="year" id="year" class="comboObj"
							style="width: 29%;">
								<%
                                                           
                               for(int i=2013; i<=year+1; i++){%>
								<%if(i == year){%>
								<option value="<%=i%>" selected><%=i%></option>
								<%}else{%>
								<option value="<%=i%>"><%=i%></option>
								<%}%>
								<%}%>
						</select></td>
					</tr>

					<tr>
						<td><label><%=localeObj.getTranslatedText("BU")%></label></td>
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
							onchange="getCity($('#district').val(),'<%=city %>','E');">
								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
						</select> <% }else{ %> <select name="branch" id="branch" class="comboObj">
								<option value="<%=userObj.getBranchCode() %>"><%= imoUtility.getBranchCode(userObj.getBranchCode()) %></option>
						</select> <% }%>
						</td>
					</tr>

					<tr>
						<td><label><%=localeObj.getTranslatedText("City")%></label></td>
						<td>
							<% if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
							<select name="city" id="city" class="comboObj"
							onchange="getSSC(this.value,'<%=ssc %>','E');">
								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
						</select> <% }else{ %> <select name="city" id="city" class="comboObj">
								<option value="<%=userObj.getCityCode() %>"><%=imoUtility.getCityName(userObj.getCityCode()) %></option>
						</select> <% }%>
						</td>
					</tr>


						


					<tr>
						<td><label><%=localeObj.getTranslatedText("SSC")%></label></td>
						<td>
							<% if(userObj.getUserType().equals("AD") || userObj.isCityLevel() ||  userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
							<select name="ssc" id="ssc" class="comboObj"  onchange="getOffice(this.value,'<%=office %>');"  >
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
                       			<% if(userObj.getUserType().equals("AD") ||  userObj.isSscLevel() || userObj.isCityLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                       				<select name="office" id="office"  class="comboObj"  >
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
						<td colspan="2" class="MT30 MB30"
							style="text-align: center; padding-top: 20px"><a href="#"
							class="btn1" onclick="javascript:interviewSearch()"> <%=localeObj.getTranslatedText("Search")%></a>
							<a href="javascript:resetSearchForm();"
							class="ML10 btn1"><%=localeObj.getTranslatedText("Clear")%></a></td>
					</tr>

				</table>
			</form>

			<div class="MT30">
				<table style="width: 100%" >
					<tr>
						<td align="left">
							<a href="FormManager?key=InterviewAdd&type=NEW" class="ML10 btn1 "><%=localeObj.getTranslatedText("Add Interview Schedule")%></a>
							<%-- <a href="ContentManager?key=Download3rdInterviewRegistrations" class="ML10 btn1 "><%=localeObj.getTranslatedText("Download Candidate that Registered 3rd Interview in CSV")%></a> --%>
							<%-- <a href="ContentManager?key=Download2ndInterviewRegistrations" class="ML10 btn1 "><%=localeObj.getTranslatedText("Download Candidate that Registered 2nd Interview")%></a> --%>
							<a href="FormManager?key=InterviewUploadCSV&type=NEW"
							class="ML10 btn1"><%=localeObj.getTranslatedText("Upload Multiple Interview Session in CSV")%></a>
							
						</td>
						
					</tr>
					<tr>
					<td style="font-weight: bold;" align="right"  >
						<div style="margin-left: -2%; padding-right: 2%;" >	<%if(pager != null){ %> <%=localeObj.getTranslatedText("Search Result")%>
							: <%=noOfInterviews %> <%}%></div>
						</td>
					</tr>
				</table>
			</div>

			<%if(pager != null){ %>

			<div class="data_table  MT20 MB20" style="width: 100%">
				<table class="subFormDesign">
					<thead>
						<tr>
							<th width="7%"><label><%=localeObj.getTranslatedText("Interview Session Name")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("Type")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("Interview Date")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("Start Time")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("End Time")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("BU")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("District")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("Branch")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("City")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("SSC")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("Office")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("Modified By")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("Modification Date")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("Registered Participants")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("Attendees")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("Action")%></label></th>
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
								<td colspan="13" bordercolor="#FFFFFF">
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
							<td colspan="13" bordercolor="#FFFFFF">
								<div align="center">
									<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
								</div>
							</td>
						</tr>
						<%} %>
					</tbody>
				</table>
				</div>
				<%}%>
				<br>
			

		</div>
	</div>
	







