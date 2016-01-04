<%--
  - Author(s):          KHYATI
  - Date:               15-MAY-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        
--%>

<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.data.city.*"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.mapper.*"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>

<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %> 

<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Pager pager = (com.quix.aia.cn.imo.utilities.Pager) request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
City classObj = new  City();
int bu=0;
int dis=0;
int branch=0;


if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null && request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.MESSAGE_OBJ) == null){
	if(null!= request.getParameter("bu") && !"".equals(request.getParameter("bu"))){
		bu=Integer.parseInt(request.getParameter("bu"));
	}
	if(null!= request.getParameter("district") && !"".equals(request.getParameter("district"))){
		 dis=Integer.parseInt(request.getParameter("district"));
	}
	 if(null!= request.getParameter("branch") && !"".equals(request.getParameter("branch"))){
			branch=Integer.parseInt(request.getParameter("branch"));
	 }
	 
}


%>
<script type="text/javascript">
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  

});
			
			function submitForm(){
				document.BUForm.submit();
				
			}
             
</script>
<script>
$( document ).ready(function() {
	getBU('<%=bu %>','L');
	
	getDistrict('<%=bu %>','<%=dis %>','L');
	getBranch('<%=dis %>','<%=branch %>','L');
	
	$("#progress").hide();
	$("#progress1").hide();
	
});
function resetCitySearchCriteria(){
	$("#<%=DistrictMaintenance.BU_PARAM %>").val('0');
	$("#district").val('0');
	$("#branch").val('0');
	$("#<%=CityMaintenance.Name%>").val('');
}
</script>
<script>
function confirmDelete(code){
	var str="<%=localeObj.getTranslatedText("Are you sure you want to delete this City?")%>";
	var dialog = $('<p>'+str+'</p>').dialog({
   	 width: 550,
    	 cache: false,
        buttons: {
            "<%=localeObj.getTranslatedText("Yes")%>": function() {
            	window.location.href = 'FormManager?key=CITYAdd&type=DELETE&CITY_CODE='+code+'&CO='+co;
            },
            "<%=localeObj.getTranslatedText("No")%>":  function() {$('.ui-dialog').hide();},
            
        }
    });
}
</script>
<div id="maincontainer">
			<div class="head">
				<h2 align="center"><%=localeObj.getTranslatedText("City Maintenance")%></h2>
			</div>
<div class="content" style="background-color:#ffffff;">
<form action="ContentManager" name="BUForm" method="post">
<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
<table class="formDesign">
			<tr>
			<td colspan="2" style="text-align: center"><span style="color:#ec2028;">
			 
                   <%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.MESSAGE_OBJ) == null){%>
                        
                    <%}else{%>
                        <%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.MsgObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.MESSAGE_OBJ)).getMsg())%>
                       <%
						
					}%>                 
				     </span></td>                                  	
			
			</tr>     			
                                          <tr>                         
                                    				<td>	                                    				
	                                    				<label><%=localeObj.getTranslatedText("BU")%></label>
                                    				</td>
                                        			<td>	
                                        				<select name="<%=DistrictMaintenance.BU_PARAM %>" id="<%=DistrictMaintenance.BU_PARAM %>"  class="comboObj"   onchange="getDistrict(this.value,'<%=dis %>','E');" >
                                       						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>	                                  	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                           </tr>	
                                           
                                            			
                                				
                      <!-- -District -->     				
                            		    
                       <tr >                         
              				<td>                                    				
               				<label ><%=localeObj.getTranslatedText("District")%></label>
              				</td>
                  			<td >	
                  				<select name="district" id="district"  class="comboObj" onchange="getBranch(this.value,'<%=branch %>','E');"  >
                 						<option value="0" >All</option>                                      	
                 	 				 </select>
                 	 			</td>                                     			   
                			    </tr>
                                      			    
                               				
                      <!-- -District -->    
                                           
                                           
                           <tr >                         
              				<td>                                    				
               				<label ><%=localeObj.getTranslatedText("Branch")%></label>
              				</td>
                  			<td >	
                  				<select name="branch" id="branch"  class="comboObj"   >
                 						<option value="0" >All</option>                                      	
                 	 				 </select>
                 	 			</td>                                     			   
                			    </tr>    
                          <!-- -city name  -->
                            <tr>                         
                            <td>	                                    				
	                        <label><%=localeObj.getTranslatedText("City Name")%></label>
                            </td>
                            <%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
                            <td>	
                            <input name="<%=CityMaintenance.Name%>" id="<%=CityMaintenance.Name%>" type="text" class="textObj" value="<%=(request.getParameter(CityMaintenance.Name))%>"  />
                            </td> 
                             <%} else {%>
                            <td>
                         	<input name="<%=CityMaintenance.Name%>"  value="<%=classObj.getCityName()%>"   type="text" class="textObj" />
                         	</td>   
                         	 <%} %>
                                       	 			
                            </tr>
                             
                                  
                            <tr>                         
                            
                           <td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                           <a href="#" class="btn1"  onclick="javascript:submitForm();" ><%=localeObj.getTranslatedText("Search")%></a> 
                           <a href="javascript:document.BUForm.reset();" class="ML10 btn1"><%=localeObj.getTranslatedText("Clear")%></a> 
                           </td>                                     			   
                           </tr>
                            		 				
                          </table>
                                       				
                               <div class="MT30" >
								<table style="width:100%">
										<%int noOfAnnouncements = ((com.quix.aia.cn.imo.utilities.Pager)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)).getActualSize();%>								
										<tr><td align="left"><a href="FormManager?key=CITYAdd&type=NEW" class="ML10 btn1 " /><%=localeObj.getTranslatedText("Add City")%></a></td>
										<td align="right" style="font-weight:bold;padding-right:10px"><%=localeObj.getTranslatedText("Search Result")%> : <%=noOfAnnouncements %></td></tr>
								</table>
							</div>
                                	 <div class="data_table  MT20 MB20" style="width:100%" >
							<table class="subFormDesign">
								<thead>
										<tr>
		
							<th width="10%"><label><%=localeObj.getTranslatedText("City Code")%></label></th>
							<th width="20%"><label><%=localeObj.getTranslatedText("City Name")%></label></th>
							
							<th width="20%"><label><%=localeObj.getTranslatedText("Branch Name")%></label></th>
							<th width="20%"><label><%=localeObj.getTranslatedText("District Name")%></label></th>
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
											<td colspan="7" bordercolor="#FFFFFF">
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
											<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
										</div></td>
								</tr>
								<%} %>
								</tbody>
							</table>
							
							</div>
							
						</form>
											
					</div>
		</div>	
 
