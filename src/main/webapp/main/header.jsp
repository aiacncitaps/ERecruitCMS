<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ page import='com.quix.aia.cn.imo.data.user.*' %>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes'%>

<%User user = ((com.quix.aia.cn.imo.data.user.User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ));
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
%>
<style>
.naviga{font:15px;text-align:left;background-color: #a3002c;color:#ffffff;padding:5px 10px 10px 10px;background-image:url(images/menu.png);background-repeat:no-repeat;background-position:5% 6px;}
.naviga div{font:13px;text-align:left;background-color: #a3002c;padding:10px;}
.naviga div table tr td a{margin-right:20px;color:#ffffff;padding:10px;text-decoration:none;font-weight:bold;}
</style>
<center>	

		
					<div id="header">
	 					<div class="container_12">
	     					<div class="grid_2" >
	             				<!-- <a href="#" class="MT08"> --><img src="images/aia_logo.png" width="153" height="65" alt="" /><!-- </a> -->
	              			</div>
	             			<div class="grid_3">
	              				<font color="white"><b><div align="left" style="margin-top:10px"><%=localeObj.getTranslatedText("Name")%>  : <%=user.getStaffName() %></div><%-- <div align="left"><%=localeObj.getTranslatedText("Email")%> : <%=user.getEmail() %></div> --%> </b></font>
	              			</div>
	  					 </div>
	 				</div>
    				<div id="showMenu" class="naviga" >
						<b><%=localeObj.getTranslatedText("Menu")%></b> 
						<div style="float:right;margin-top:-1%;margin-right:1%">
						<%-- <a href="ContentManager?key=refresh"  style="text-decoration:none;color: #ffffff;font-weight:bold"><%=localeObj.getTranslatedText("Refresh")%></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; --%>
						<%if(user.isCho()){%>
						<a href="attract/"  style="text-decoration:none;color: #ffffff;font-weight:bold"><%=localeObj.getTranslatedText("Interactive Presenter")%></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<%} %>	
							<a href="ContentManager?key=login" style="text-decoration:none;color: #ffffff;font-weight:bold"><%=localeObj.getTranslatedText("Logout")%></a>
						</div>
						<div id="showMe" style="display: none;">
							<table width="80%">
								<tr>
									<td style="padding:10px"><a href="ContentManager?key=home"><%=localeObj.getTranslatedText("Home")%></a></td>
									<%-- <td style="padding:10px"><a href="ContentManager?key=DEMO"  ><%=localeObj.getTranslatedText("Demo")%></a></td> --%>
									
									
									<%-- <td style="padding:10px"><a href="ContentManager?key=PresenterMaintenance"  ><%=(localeObj.getTranslatedText("Presenter".toUpperCase())).toLowerCase()%></a></td> --%>
									<td style="padding:10px"><a href="ContentManager?key=InterviewMaintenance"  ><%=localeObj.getTranslatedText("Interview")%></a></td>
									<td style="padding:10px"><a href="ContentManager?key=AnnouncementMaintenance"  ><%=localeObj.getTranslatedText("Announcement")%></a></td>
									<td style="padding:10px"><a href="ContentManager?key=EopMaintenance"  ><%=localeObj.getTranslatedText("Schedules")%></a></td>
								</tr>
								<tr>
									
									<%//if(!user.isSscLevel() && !user.isOfficeLevel()){
										if(user.getUserType().equals("AD") || user.isCho() || user.isBuLevel()){ %>
										<td style="padding:10px"><a href="ContentManager?key=UserMaintenance" ><%=localeObj.getTranslatedText("User Management")%></a></td>
									<%} %>
									
<%-- 									<td style="padding:10px"><a href="ContentManager?key=HolidayMaintenance" ><%=localeObj.getTranslatedText("Holiday")%></a></td> --%>
									
								    <td style="padding:10px"><a href="ContentManager?key=E_Greeting"><%=localeObj.getTranslatedText("E-Greeting")%></a></td>
								    <td style="padding:10px"><a href="ContentManager?key=FestiveCategory" ><%=localeObj.getTranslatedText("Festive Category")%></a></td>
									<%-- <td style="padding:10px"><a href="ContentManager?key=IndividualGoal_Y" ><%=localeObj.getTranslatedText("Goal Setting")%></a></td> --%>
<%-- 									<td style="padding:10px"><a href="ContentManager?key=CCTestMaintenance"><%=localeObj.getTranslatedText("CC Test")%></a></td> --%>
								</tr>
								<tr>
									<%-- <td style="padding:10px"><a href="FormManager?key=GoalConfig"><%=localeObj.getTranslatedText("Goal Configuration")%></a></td> --%>
									
									<%-- <td style="padding:10px"><a href="ContentManager?key=PresenterCategory" ><%=localeObj.getTranslatedText("Presenter Category")%></a></td> --%>
									<%if(user.getUserType().equals("AD")){ %>
									 <td style="padding:10px"><a href="ContentManager?key=BU"><%=localeObj.getTranslatedText("Business Unit")%></a></td>
									<td style="padding:10px"><a href="ContentManager?key=DISTRICT" ><%=localeObj.getTranslatedText("District")%></a></td>
									<td style="padding:10px"><a href="ContentManager?key=BRANCH" ><%=localeObj.getTranslatedText("Branch")%></a></td>
									<%--<td style="padding:10px"><a href="ContentManager?key=CITY" ><%=localeObj.getTranslatedText("City")%></a></td> --%>
<!-- 									<td style="padding:10px">&nbsp;</td> -->
									
								</tr>
								
								<tr>
									<%-- <td style="padding:10px"><a href="ContentManager?key=SSC" ><%=localeObj.getTranslatedText("SSC")%></a></td>
									<td style="padding:10px"><a href="ContentManager?key=OFFICE" ><%=localeObj.getTranslatedText("Office")%></a></td> --%>
									<td style="padding:10px"><a href="ContentManager?key=Department" ><%=localeObj.getTranslatedText("Department")%></a></td>
									
								</tr>
								
									
								<%}%>
<!-- 								<tr> -->
									
<!-- 								<td style="padding:10px">&nbsp;</td> -->
<%-- 									<td style="padding:10px"><a href="ContentManager?key=Channel" ><%=localeObj.getTranslatedText("Channel")%></a></td> --%>
<!-- 								<td style="padding:10px">&nbsp;</td> -->
<!-- 								<td style="padding:10px">&nbsp;</td> -->
<!-- 								</tr> -->
								<%--<tr>
									<td ><a href="ContentManager?key=IndividualGoal_Y"><%=localeObj.getTranslatedText("Goal Settings")%></a></td>
									 <td > <a href="ContentManager?key=GoalBreakDown_M"><%=localeObj.getTranslatedText("GoalBreakDown Monthly")%></a></td>
									<td ><a href="ContentManager?key=GoalBreakDown_Y"><%=localeObj.getTranslatedText("GoalBreakDown Yearly")%></a></td>
									<td ><a href="ContentManager?key=TeamGoal_M"><%=localeObj.getTranslatedText("TeamGoal_Monthly")%></a></td>
									<td ><a href="ContentManager?key=TeamGoal_y"><%=localeObj.getTranslatedText("TeamGoal_Yearly")%></a></td>
								</tr> --%>
							</table>
					</div> 
					</div>
    			</center>
    				 <br/>
                     <br/>	
<script>
$(document).ready(function(){
// 	$("#showMe").hide(0); 
    $("#showMenu").click(function(){
        $("#showMe").toggle(10);
    });
});
</script>
