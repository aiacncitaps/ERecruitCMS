<%--
  - Author(s):          Abinas
  - Date:               20-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:         history page created
--%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.utilities.KeyObjPair"%>


<%
User userObj = (User)request.getSession().getAttribute("currUserObj");
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Pager pager = (Pager) request.getAttribute(RequestAttributes.PAGING_OBJECT);
KeyObjPair keyObjPair = null;
%>
<script>
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
</script>
   	<div id="maincontainer">	
   		
   					<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("History of Agent")%> </h2></div>  	
   							<div class="content" style="background-color:#ffffff;">	 
   												  
   												   
                                                     

                                	  <br>
                                	
                                       	  
                                	  <%if(pager != null){%>
                                   
                                     
                                     
          <div class="data_table  MT20 MB20" style="width:100%" >
							<table class="subFormDesign">
				
				<thead>
            <tr >
        		<th valign=top align="left" width="7%"><%=localeObj.getTranslatedText("Date")%></th>
        		<th valign=top align="left"  width="7%"><%=localeObj.getTranslatedText("Agent Code")%></th>
        		<th valign=top align="left"  width="7%"><%=localeObj.getTranslatedText("Goal Status")%></th>
        		<th valign=top align="left"  width="7%"><%=localeObj.getTranslatedText("GA")%></th>
        		<th valign=top align="left"  width="7%"><%=localeObj.getTranslatedText("HA")%></th>
        		<th valign=top align="left"  width="7%"><%=localeObj.getTranslatedText("GEN_Y")%></th>
        		<th valign=top align="left"  width="7%"><%=localeObj.getTranslatedText("SA")%></th>
        		<th valign=top align="left" " width="7%"><%=localeObj.getTranslatedText("Total")%></th>
        		
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
													</div>
												</td>
												<td>
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
										</div></td>
								</tr>
								<%} }%>
								</tbody>  
					
			</table>
			</div>
			     <div style="margin-left:70%;margin-bottom:20px;">
			 
              <a href="ContentManager?key=TeamGoal_y" id="reset" class="btn1"><%=localeObj.getTranslatedText("Back")%></a>
							
						
           
					</div>
		
           
					
				</div>
			
    </div>
     
     