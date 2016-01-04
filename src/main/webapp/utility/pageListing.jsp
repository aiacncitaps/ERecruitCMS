<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%  
String conPath  = "ContentManager";
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
%>

<%
com.quix.aia.cn.imo.utilities.Pager pager=null;

if( request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)!=null){
	pager = (com.quix.aia.cn.imo.utilities.Pager) request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
}else{
	pager = (com.quix.aia.cn.imo.utilities.Pager) request.getSession().getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
}

/* if(pager!=null)
{ */
%>
<pg:pager
    items="<%= pager.getItems().size() %>"
    maxPageItems="<%= pager.getMaxPageItems() %>"
    maxIndexPages="<%= pager.getMaxIndexPages() %>"
    isOffset="<%= pager.isOffset() %>"
    export="offset,currentPageNumber=pageNumber"
    scope="request"
    url="<%=conPath%>" >
	
<tr>
		<td colspan="3">
			<div align="center">
				<table border="1" bordercolor="#C0C0C0" cellspacing="1" cellpadding="0" width="100%" style="border-collapse: collapse">
					<%=pager.getTableHeader()%>
					<tr>
					<% for (int i = offset.intValue(),l = Math.min(i + pager.getMaxPageItems(), pager.getActualSize());i < l; i++)
					{%>
						<pg:item>
						<!-- 	<div class="list opener">  -->
							<%=(String)pager.getItems().get(i)%>
						<!-- </div>  -->	
						</pg:item>
					<%}%>			
								</table>
			<div align="center">
		</td>
	</tr>
	<%if(pager.getItems().size() <= 0){%>
		<tr>
		<td colspan="2" bordercolor="#FFFFFF">
			<div align="center">
				<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
			<div align="center">
		</td>
	</tr>
	<%}%>
	</tr>
	
</pg:pager>
<%-- <%} %> --%>
