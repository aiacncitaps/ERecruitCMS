<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>


<%User user = ((com.quix.aia.cn.imo.data.user.User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ));
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
%>	

		<div id="maincontainer">
			<div class="content" style="background-color:#ffffff;">
				<table class="formDesign" style="margin-top:200px;">
					<tr>
						<td ><label ><%=localeObj.getTranslatedText("Welcome")%> <%=user.getStaffName() %></label></td>
					</tr>
					<tr>
					
					<%
						
					%>
						<td><label ><%=user.getLastLogIn()==null?localeObj.getTranslatedText("This is the first time you log CMS."):localeObj.getTranslatedText("Your Last login was at")+user.getLastLogIn() %></label></td>
					</tr>
				</table>
			</div>
		</div>


   	

			