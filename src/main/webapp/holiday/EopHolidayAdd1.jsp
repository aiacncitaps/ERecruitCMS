<%--
  - Author(s):          Jinatmayee
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Adding New Holiday
--%>

<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.data.holiday.Holiday"%>
<%@page import="com.quix.aia.cn.imo.utilities.ErrorObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<%
int bu=0;
int dis=0;
String city=0+"";
String ssc=0+"";
int branch=0;

LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
User userObj = (User)request.getSession().getAttribute("currUserObj");
ImoUtilityData imoUtility=new ImoUtilityData();
Holiday holidayObj = new Holiday();
holidayObj = (Holiday)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);

boolean modifyFlag = false;
FormObj formDetail = null;
if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
}

String holidayName="";
String strartDate="";
String endDate="";

bu=userObj.getBuCode();
dis=userObj.getDistrict();
city=userObj.getCityCode();
ssc=userObj.getSscCode();

String icon_type="";
String postedBy=userObj.getStaffName();
if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	 modifyFlag = true;
	 holidayName=SecurityAPI.encodeHTML(holidayObj.getHolidayName());
	 strartDate=LMSUtil.convertDateToString(holidayObj.getStartDate());
     endDate=LMSUtil.convertDateToString(holidayObj.getEndDate());	
	 bu=holidayObj.getBuCode();
	 dis=holidayObj.getDistrict();
	// city=holidayObj.getCityCode();
	 branch=holidayObj.getBranchCode();
	 //ssc=holidayObj.getSscCode();
	 icon_type=holidayObj.getIconSelection();
	 postedBy=holidayObj.getCreatedBy();
}
if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){
	 holidayName=SecurityAPI.encodeHTML(request.getParameter("name"));
	 strartDate=request.getParameter("startdate");
     endDate=request.getParameter("enddate");		 
	 bu=Integer.parseInt(request.getParameter("bu"));
	 dis=Integer.parseInt(request.getParameter("district"));
	 branch=Integer.parseInt(request.getParameter("branch"));
	 city=(request.getParameter("city"));
	 ssc=(request.getParameter("ssc"));
	 icon_type=request.getParameter("icons");
	 postedBy=request.getParameter("createdBy");
}


//------
%>

<script type="text/javascript">

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
});
function holidayFormSubmit ( action )
{
  document.holidayForm.actionType.value = action ;
  document.holidayForm.submit() ;
 }

</script>


	<div id="maincontainer">

		<div class="head">
			<h2 align="center">
				<%if(modifyFlag == true){%>
				<%=localeObj.getTranslatedText("Edit Holiday")%>
				<%}else{%>
				<%=localeObj.getTranslatedText("Add Holiday")%>
				<%}%>
			</h2>
		</div>
		<div class="content" style="background-color: #ffffff;">
			<form name="holidayForm" method="post" action="FormManager"
				class="PT20">
				<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
				<input type="hidden" name="actionType" />
				<table class="formDesign">
					<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
					<tr>
						<td colspan="2" style="text-align: center"><span
							style="color: #ec2028;"><%=localeObj.getTranslatedText(((ErrorObject)request.getAttribute(RequestAttributes.ERROR_OBJ)).getErrorMsg())%></span>
						</td>
					</tr>

					<%}%>
					<tr>

						<td><span style="color: #ec2028;">* </span><label><%=localeObj.getTranslatedText("Holiday Name")%></label></td>
						<td><input name="name" id="name" type="text" class="textObj"
							value="<%=holidayName%>" maxlength="250" /></td>
					</tr>
					<tr>
						<td><span style="color: #ec2028;">* </span><label><label><%=localeObj.getTranslatedText("Start Date")%></label></td>
						<td><input name="startdate" id="startdate" type="text"
							class="textObj datepicker" value="<%=strartDate%>"
							readonly="readonly" /></td>
					</tr>
					<tr>
						<td><span style="color: #ec2028;">* </span><label><%=localeObj.getTranslatedText("End Date")%></label></td>
						<td><input name="enddate" id="enddate" type="text"
							class="textObj datepicker" value="<%=endDate%>"
							readonly="readonly" /></td>
					</tr>

					<tr>
						<td><span style="color: #ec2028;">* </span><label><%=localeObj.getTranslatedText("BU")%></label>
						</td>
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
								<option value="<%=userObj.getBranchCode() %>"><%= imoUtility.getBranchCode(userObj.getBranchCode()) %></option>
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
								<option value="<%=userObj.getDistrict() %>"><%= imoUtility.getDistCode(userObj.getDistrict()) %></option>
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
						<td><label><%=localeObj.getTranslatedText("SSC")%></label>
						</td>
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
						<td><span style="color: #ec2028;">* </span><label><%=localeObj.getTranslatedText("Icon Selection")%></label>
						</td>
						<td>
							<%if(icon_type!=null){ %> <img src="images/star.png" width="50"
							height="25" style="margin-top: 2px;" border="0" alt="" />&nbsp;<input
							type="radio" name="icons" value="1"
							<%= icon_type.equals("1")?"checked=checked":"" %>> <img
							src="images/elephant.png" width="50" height="25"
							style="margin-top: 2px;" border="0" alt="" />&nbsp;<input
							type="radio" name="icons" value="2"
							<%= icon_type.equals("2")?"checked=checked":"" %>> <img
							src="images/minister.png" width="50" height="25"
							style="margin-top: 2px;" border="0" alt="" />&nbsp;<input
							type="radio" name="icons" value="3"
							<%= icon_type.equals("3")?"checked=checked":"" %>> <img
							src="images/horse.png" width="50" height="25"
							style="margin-top: 2px;" border="0" alt="" />&nbsp;<input
							type="radio" name="icons" value="4"
							<%= icon_type.equals("4")?"checked=checked":"" %>> <img
							src="images/boat.png" width="50" height="25"
							style="margin-top: 2px;" border="0" alt="" />&nbsp;<input
							type="radio" name="icons" value="5"
							<%= icon_type.equals("5")?"checked=checked":"" %>> <%}else{%>
							<img src="images/star.png" width="50" height="25"
							style="margin-top: 2px;" border="0" alt="" />&nbsp;<input
							type="radio" name="icons" value="1"> <img
							src="images/elephant.png" width="50" height="25"
							style="margin-top: 2px;" border="0" alt="" />&nbsp;<input
							type="radio" name="icons" value="2"> <img
							src="images/minister.png" width="50" height="25"
							style="margin-top: 2px;" border="0" alt="" />&nbsp;<input
							type="radio" name="icons" value="3"> <img
							src="images/horse.png" width="50" height="25"
							style="margin-top: 2px;" border="0" alt="" />&nbsp;<input
							type="radio" name="icons" value="4"> <img
							src="images/boat.png" width="50" height="25"
							style="margin-top: 2px;" border="0" alt="" />&nbsp;<input
							type="radio" name="icons" value="5"> <%}%>
						</td>
					</tr>

					<tr>
						<td><span style="color: #ec2028;">* </span><label><%=localeObj.getTranslatedText("Posted By")%></label>
						</td>
						<td><input name="createdBy" id="createdBy" type="text"
							class="textObj" value="<%=postedBy%>" readonly="readonly" /></td>
					</tr>



					<tr>
						<td colspan="2" class="MT30 MB30"
							style="text-align: center; padding-top: 20px">
							<%if(modifyFlag == true){%> <a href="#" class="btn1 "
							onclick="javascript:holidayFormSubmit('<%=FormInfo.SUBMIT%>')"><%=localeObj.getTranslatedText("Modify")%></a>
							<%}else{%> <a href="#" class="btn1 "
							onclick="javascript:holidayFormSubmit('<%=FormInfo.SUBMIT%>')"><%=localeObj.getTranslatedText("Submit")%></a>
							<%}%> <a href="ContentManager?key=HolidayMaintenance"
							class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a>
						</td>
					</tr>
				</table>
				<br>
			</form>
		</div>
	</div>


