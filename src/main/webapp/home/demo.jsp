<%--
  - Author(s):          KHYATI
  - Date:               15-MAY-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        
--%>


<%@page import="com.quix.aia.cn.imo.mapper.DistrictMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Pager pager = (com.quix.aia.cn.imo.utilities.Pager) request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
int bu=0;
int dis=0;
int city=0;
int ssc=0;
%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<script language="javascript">
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
		$(document).ready(function(){
			getBU('<%=bu %>');
			getDistrict('<%=bu %>','<%=dis %>');
			getCity('<%=dis %>','<%=city %>');
			getSSC('<%=city %>','<%=ssc %>');
		});   
		function confirmDelete(code){
			var dialog = $('<p>Are you sure you want to delete this City?</p>').dialog({
		   	 width: 550,
		    	 cache: false,
		        buttons: {
		            "Yes": function() {
		            	window.location.href = 'FormManager?key=CITYAdd&type=DELETE&CITY_CODE='+code;
		            },
		            "No":  function() {$('.ui-dialog').hide();},
		            
		        }
		    });
		}
</script>	
	

		<div id="maincontainer">
					<div class="head">
						<h2 align="center"><%=localeObj.getTranslatedText("Demo Maintenance")%></h2>
					</div>
					<div class="content" style="background-color:#ffffff;">
						<form action="ContentManager" name="DemoForm" method="post" class="PT20">
							
							<table class="formDesign">
								<tr>
									<td colspan="2" style="text-align: center"><span style="color:#ec2028;">Save Record Successfully. </span></td>
								</tr>
								<tr>
									<td ><span style="color:#ec2028;">* </span><label>Exam Code</label></td>
									<td ><input type="text" id="code" name="code" class="textObj" value=""></td>
								</tr>
								<tr >
									<td><span style="color:#ec2028;">* </span><label>Exam Name</label></td>
									<td><input type="text" id="name" name="name" class="textObj" value=""></td>
								</tr>
								<tr>
									<td><span style="color:#ec2028;">* </span><label>BU</label></td>
									<td>
										<select class="comboObj" name="bu" id="bu" onchange="getDistrict(this.value,'<%=dis %>');">
                              					<option value="" selected="selected">All</option>
                              			</select>	
									</td>
								</tr>
								<tr>
									<td><span style="color:#ec2028;">* </span><label>District</label></td>
									<td>
									<select class="comboObj"  name="district" id="district"  onchange="getCity(this.value,'<%=city %>');"  >
                 						<option value="0" >All</option>                                      	
                 	 				 </select>
                 	 				 </td>
								</tr>
								<tr>
									<td><span style="color:#ec2028;">* </span><label>City</label></td>
									<td>
									<select class="comboObj"  name="city" id="city" onchange="getSSC(this.value,'<%=ssc %>');"  >
                 						<option value="0" >All</option>                                      	
                 	 				 </select>
                 	 				 </td>
								</tr>
								<tr>
									<td><span style="color:#ec2028;">* </span><label>SSC</label></td>
									<td>
									<select class="comboObj"  name="ssc" id="ssc"  >
                 						<option value="0" >All</option>                                      	
                 	 				 </select>
                 	 				 </td>
								</tr>
								<tr>
									<td><span style="color:#ec2028;">* </span><label>File</label></td>
									<td><input type="file" id="file" name="file" class="fileObj"  value=""></td>
								</tr>
								<tr>
                                 		<td ><label><%=localeObj.getTranslatedText("Start Date")%></label></td>
                                 		<td align="left"><input name="startdate" id="startdate" type="text" class="textObj datepicker"   readonly="readonly"/></td>
                                 </tr>
                                 <tr>
                                 		<td ><label><%=localeObj.getTranslatedText("End Date")%></label></td>
                                 		<td align="left"><input name="enddate" id="enddate" type="text" class="textObj datepicker"  readonly="readonly"/></td>
                                 </tr>
								<tr >
									<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
										<a href="" class="btn1" >Submit</a>
										<a href="" class="ML10 btn1">back</a>
									</td>
								</tr>
							</table>
							
							<div class="MT30" >
								<table style="width:100%">
										<%int noOfAnnouncements = ((com.quix.aia.cn.imo.utilities.Pager)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)).getActualSize();%>								
										<tr><td align="left"><a href="" class="ML10 btn1" >Add New</a> <a href="" class="ML10 btn1" >Upload</a></td>
										<td align="right" style="font-weight:bold;padding-right:10px"><%=localeObj.getTranslatedText("Search Result")%> : <%=noOfAnnouncements %></td></tr>
								</table>
							</div>
							
							<div class="data_table  MT20 MB20" style="width:100%" >
							<table class="subFormDesign">
								<thead>
										<tr>
											<th width="10%"><label><%=localeObj.getTranslatedText("BU Code")%></label></th>
											<th><label><%=localeObj.getTranslatedText("BU Name")%></label></th>
											<th width="10%"><label><%=localeObj.getTranslatedText("Action")%></label></th>
										</tr>
								</thead>
								<tbody>
										<!-- <tr>
											<td><label>11111111111111111111111111111111111111111111111</label></td>
											<td><label>11111111111111111111111111111111111111111111111</label></td>
										</tr>
										<tr >
											<td><label>A</label></td>
											<td><label>B</label></td>
										</tr>
										<tr >
											<td><label>A</label></td>
											<td><label>B</label></td>
										</tr>
										<tr>
											<td><label>A</label></td>
											<td><label>B</label></td>
										</tr> -->
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
											<td colspan="7" bordercolor="#FFFFFF">
												<div align="center">
													<strong>No Record Found</strong>
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
										</table>
										<%}%>
									</pg:pager>
									<%}
			  	else
			  	{%>
								
								<tr>
									<td colspan="3" bordercolor="#FFFFFF">
										<div align="center">
											<strong>No Record Found</strong>
										</div></td>
								</tr>
								<%} %>
								</tbody>
							</table>
							
							</div>
							
						</form>
											
					</div>
		</div>	
