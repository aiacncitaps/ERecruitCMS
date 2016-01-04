<%--
  - Author(s):          Jinatmayee
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Holiday Listing Page
--%>

<%@page import="java.util.Map"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="com.quix.aia.cn.imo.utilities.MsgObject"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<%
int bu=0;
int dis=0;
int city=0;
int ssc=0;
int branch=0;
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Pager pager = null;
int noOfHolidays=0;
if(request.getAttribute(RequestAttributes.PAGING_OBJECT)!=null){
     pager=(Pager) request.getAttribute(RequestAttributes.PAGING_OBJECT);
     noOfHolidays= ((Pager)request.getAttribute(RequestAttributes.PAGING_OBJECT)).getActualSize();
 }
/* else if(request.getSession().getAttribute(RequestAttributes.PAGING_OBJECT)!=null){
    pager=(Pager) request.getSession().getAttribute(RequestAttributes.PAGING_OBJECT);
    noOfHolidays= ((Pager)request.getSession().getAttribute(RequestAttributes.PAGING_OBJECT)).getActualSize();
}
 */
 
 String holidayName="";
int year = GregorianCalendar.getInstance().get( Calendar.YEAR );
User userObj = (User)request.getSession().getAttribute("currUserObj");

bu=userObj.getBuCode();
dis=userObj.getDistrict();
//city=userObj.getCityCode();
//ssc=userObj.getSscCode();
branch=userObj.getBranchCode();

ImoUtilityData imoUtility=new ImoUtilityData();

if(request.getSession().getAttribute("HOL_SEARCH_OBJ")!=null){
	Map<String,String> holMap = (Map)request.getSession().getAttribute("HOL_SEARCH_OBJ");
	holidayName = holMap.get("name");
    year = Integer.parseInt(holMap.get("year"));
	bu = Integer.parseInt(holMap.get("bu"));
	dis = Integer.parseInt(holMap.get("district"));
	city = Integer.parseInt(holMap.get("city"));
	ssc = Integer.parseInt(holMap.get("ssc"));
	branch = Integer.parseInt(holMap.get("branchCode"));
}
/* if(request.getParameter("key")!=null){
	if(request.getSession().getAttribute("HOL_SEARCH_OBJ")!=null)
		request.getSession().removeAttribute("HOL_SEARCH_OBJ");
}
 */
%>

<script type="text/javascript">
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
function confirmDelete(holidayCode){
	var dialog = $('<p>Are you sure you want to delete this Holiday?</p>').dialog({
   	 width: 550,
    	 cache: false,
        buttons: {
            "<%=localeObj.getTranslatedText("Yes")%>": function() {
            	window.location.href = 'FormManager?key=EopHolidayAdd&type=DELETE&holiday_code='+holidayCode;
            },
            "<%=localeObj.getTranslatedText("No")%>":  function() {
            	$('.ui-dialog').hide();
            },
            
        }
    });
}

$(document).ready(function(){
	<%
	
	if(userObj.getUserType().equals("AD") ){ %> 
			getBU('<%=bu %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ){ %>
					getDistrict('<%=bu %>','<%=dis %>','L');
				
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
					getBranch('<%=dis %>','<%=branch %>','L');
			
				<% }else{%>
				getBranch('<%=dis %>','<%=branch %>','L');
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
				 
					getCity('<%=branch %>','<%=city %>','L');
				<% }%>
	<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() ||  userObj.isBuLevel() || userObj.isCityLevel() ){%>
		
		getSSC('<%=city %>','<%=ssc %>');
	<% }%>

		
});

</script>


	<div id="maincontainer">
		<div class="head">
			<h2 align="center"><%=localeObj.getTranslatedText("Holiday Maintenance")%></h2>
		</div>
		<div class="content" style="background-color: #ffffff;">
			<form name="holidayForm" method="post" action="ContentManager"
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
						<td><label><%=localeObj.getTranslatedText("Holiday Name")%></label>
						</td>
						<td><input name="name" id="name" type="text" class="textObj"
							value="<%=holidayName%>" /></td>
					</tr>

					<tr>
						<td><label><%=localeObj.getTranslatedText("Year")%></label>
						</td>
						<td><select name="year" id="year" class="comboObj">

								<%
                                                           
                              for(int i=2013; i<=year; i++){%>
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
							onchange="getBranch(this.value,'<%=city %>','E');">
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
							<% if(userObj.getUserType().equals("AD") || userObj.isBranchLevel()  || userObj.isDistrictLevel()  || userObj.isBuLevel()){ %>
							<select name="branch" id="branch" class="comboObj"
							onchange="getCity(this.value,'<%=city %>','E');">
								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
						</select> <% }else{ %> <select name="branch" id="branch" class="comboObj">
								<option value="<%=userObj.getBranchCode() %>"><%= imoUtility.getBranchCode(userObj.getBranchCode()) %></option>
						</select> <% }%>
						</td>
					</tr>

					<tr>
						<td><label><%=localeObj.getTranslatedText("City")%></label>
						</td>
						<td>
							<% if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
							<select name="city" id="city" class="comboObj"
							onchange="getSSC(this.value,'<%=ssc %>');">
								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
						</select> <% }else{ %> <select name="city" id="city" class="comboObj">
								<option value="<%=userObj.getCityCode() %>"><%=imoUtility.getCityName(userObj.getCityCode()) %></option>
						</select> <% }%>
						</td>
					</tr>

					<tr>
						<td><label><%=localeObj.getTranslatedText("SSC")%></label></td>
						<td>
							<% if(userObj.getUserType().equals("AD") || userObj.isCityLevel()){ %>
							<select name="ssc" id="ssc" class="comboObj">
								<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
						</select> <% }else{ %> <select name="ssc" id="ssc" class="comboObj">
								<option value="<%=userObj.getSscCode() %>"><%=imoUtility.getSscName(userObj.getSscCode()) %></option>
						</select> <% }%>
						</td>
					</tr>

					<tr>
						<td colspan="2" class="MT30 MB30" style="text-align: center; padding-top: 20px">
						    <a href="#" class="btn1" onclick="javascript:holidaySearch()"><%=localeObj.getTranslatedText("Search")%></a>
							<a href="javascript:document.holidayForm.reset();" class="ML10 btn1"><%=localeObj.getTranslatedText("Clear")%></a></td>
					</tr>

				</table>
			</form>


			<div class="MT30">
				<table style="width: 100%">
					<tr>
						<td align="left"><a
							href="FormManager?key=EopHolidayAdd&type=NEW" class="ML10 btn1 " /><%=localeObj.getTranslatedText("Add Hoilday")%></a>
							<a href="FormManager?key=HolidayUploadCSV&type=NEW"
							class="ML10 btn1" /><%=localeObj.getTranslatedText("Upload Multiple Holiday in CSV")%></a>
						</td>

						<td align="right" style="font-weight: bold; padding-right: 10px">
							<%if(pager != null){ %> <%=localeObj.getTranslatedText("Search Result")%>
							: <%=noOfHolidays %> <%}%>
						</td>
					</tr>
				</table>
			</div>


			<%if(pager != null){ %>
			<div class="data_table  MT20 MB20" style="width: 100%">
				<table class="subFormDesign">
					<thead>
						<tr>
							<th width="20%"><label><%=localeObj.getTranslatedText("Holiday Name")%></label></th>
							<th width="10%"><label><%=localeObj.getTranslatedText("Start Date")%></label></th>
							<th width="10%"><label><%=localeObj.getTranslatedText("End Date")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("BU")%></label></th>
							<th width="9%"><label><%=localeObj.getTranslatedText("District")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("City")%></label></th>
							<th width="7%"><label><%=localeObj.getTranslatedText("SSC")%></label></th>
							<th width="10%"><label><%=localeObj.getTranslatedText("Date Posted")%></label></th>
							<th width="10%"><label><%=localeObj.getTranslatedText("Posted By")%></label></th>
							<th width="10%"><label><%=localeObj.getTranslatedText("Action")%></label></th>
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
								<td colspan="10" bordercolor="#FFFFFF">
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
							<td colspan="10" bordercolor="#FFFFFF">
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
	
	



		<script type="text/javascript" language="javascript">
   function holidaySearch() 
	 {
	  document.holidayForm.submit() ;
	   
	 }
</script>