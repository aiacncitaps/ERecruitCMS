<%--
  - Author(s):          khyati
  - Date:               19-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        E-Greeting Cards search
--%>
<%@page import="com.quix.aia.cn.imo.mapper.EgreetingMaintenance"%>
<%@page import="com.quix.aia.cn.imo.mapper.PresenterMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.quix.aia.cn.imo.data.presenter.Presenter"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.data.category.PresenterCategory"%>
<%@page import="com.quix.aia.cn.imo.data.category.FestiveCategory"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.utilities.KeyObjPair"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script> 

<%
int bu=0;
int dis=0;
String city="";
String ssc="";
User userObj = (User)request.getSession().getAttribute("currUserObj");
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Pager pager = (Pager) request.getAttribute(RequestAttributes.PAGING_OBJECT);




EgreetingMaintenance obj=new EgreetingMaintenance();
ArrayList<FestiveCategory> arrFestiveCategory = new ArrayList();
arrFestiveCategory =obj.getAllFestiveCategory(request);

bu=userObj.getBuCode();
dis=userObj.getDistrict();
city=userObj.getCityCode();
ssc=userObj.getSscCode();
ImoUtilityData imoUtilityDate=new ImoUtilityData();
KeyObjPair keyObjPair = null;
%>
<script>

// search button
function searchButton(){
	document.presenterForm.submit();
}
/* //delete presenter
function deleteEgreeting(EGreetingCode,EGreetingName){
	var dialog = $('<p>Are you sure you want to delete this Presenter?</p>').dialog({
	   	 width: 550,
	    	 cache: false,
	        buttons: {
	            "<%=localeObj.getTranslatedText("Yes")%>": function() {
	            	window.location.href = 'FormManager?key=E_GreetingAdd&type=DELETE&E_GREETING_ID='+EGreetingCode+'&E_GREETING_NAME='+EGreetingName;
	            },
	            "<%=localeObj.getTranslatedText("No")%>":  function() {
	            	$('.ui-dialog').hide();
	            },
	            
	        }
	    });
	
} */
</script>



<script>
function confirmDelete(code){
var str="<%=localeObj.getTranslatedText("Are you sure you want to delete this E_GREETING?")%>";
var dialog = $('<p>'+str+'</p>').dialog({
   	 width: 550,
    	 cache: false,
        buttons: {
            "<%=localeObj.getTranslatedText("Yes")%>": function() {
            	window.location.href = 'FormManager?key=E_GreetingAdd&type=DELETE&E_GREETING_ID='+code;
            },
            "<%=localeObj.getTranslatedText("No")%>":  function() {$('.ui-dialog').hide();},
            
        }
    });
}
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
</script>
<div id="maincontainer">
			<div class="head">
				<h2 align="center"><%=localeObj.getTranslatedText("E-Greeting Card Maintenance")%></h2>
			</div>
<div class="content" style="background-color:#ffffff;">

  <form name="presenterForm" action="ContentManager" method="post">
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
                                      	 <!-- validation message End -->
                                      	
                                      	
                                      	
                                      	
                                      	 <!-- E-Greeting Cards Name Start-->
                                      	<tr  >                         
                                    	<td  >	                                    				
	                                    		<label ><%=localeObj.getTranslatedText("E-Greeting Cards Name")%></label>
                                    	</td>
                                        <td>
                                        <input name="<%=EgreetingMaintenance.NAME_PARAM%>" id="<%=EgreetingMaintenance.NAME_PARAM %>" type="text" class="textObj"  value=""  />	
                                        				
                            	 	    </td>                                     			   
                           			    </tr>
                           			    <!-- E-Greeting Cards Name End---->
                           			    
                           			    
                           			  
                           			      
                           			    <!--  Festive Category Start-->
                                				<tr  >                         
                                    	<td><label ><%=localeObj.getTranslatedText("Festive Category")%></label>	</td>                                    				
                                        <td>	
                                        				<select name="festive_category" id="festive_category"  class="comboObj"   >
			                                				<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
			                                				<%
			                                				for(int i=0;i<arrFestiveCategory.size();i++){
			                                					FestiveCategory FestivecategoryObj=(FestiveCategory)arrFestiveCategory.get(i);
			                                				
			                                				%>
			                                				<option value="<%=FestivecategoryObj.getfestiveCategoryCode()%>"><%=SecurityAPI.encodeHTML(FestivecategoryObj.getFestiveCategoryName()) %></option>
			                                				<%}   %>                                   	
                                	 			        </select>
                            	 			</td>                                     			   
                           			    </tr>
                           			    
                           			    
                           			    
                           			   <!--  Festive Category End-->
                           			    
                           			    
                           			       	
                           			          	
                           			    <!-- month year -->
                           			    	 				
                		 				<tr  >                         
                        				<td style="border: none;"  >                                   				
                         				<label ><%=localeObj.getTranslatedText("Year")%></label>
                        				</td>
                            			<td >
                            			<%
			                                int month = 0;
			                                Calendar calendar = GregorianCalendar.getInstance();
			                             //   month = calendar.get( Calendar.MONTH );
			                              //  System.out.println("Month==="+month);
			                               
									 	%>	
                            				
			                           	  <%
			                                int year = 0;
			                                 calendar = GregorianCalendar.getInstance();
									        year = calendar.get( Calendar.YEAR );
			                               
									 	%>
                           	 				 <select name="<%=PresenterMaintenance.YEAR_PARAM %>" id="<%=PresenterMaintenance.YEAR_PARAM %>" class="comboObj"  >
             		              	             <%for(int i=2004; i<=year+1; i++){%>
															<%if(i == year){%>
																<option value="<%=i%>" selected><%=i%></option>
															<%}else{%>
																<option value="<%=i%>"><%=i%></option>
															<%}%>
														<%}%>
                           						                                       	
                           	 				 </select>
                           	 				 
                           	 			</td>                                     			   
                          			    </tr>
                          			     <!-- month year -->
                          			    
                          			    
                          			    
                          			     <!-- search and clear Button -->	   
                                      			    <tr  >                         
	                                    				
   
                                        			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                                        			
                                        				<a href="#"  class="ML10 btn1 "  onClick="javascript:searchButton();"><%=localeObj.getTranslatedText("Search")%></a>
                            		 					<a href="javascript:document.presenterForm.reset();" class="ML10 btn1"><%=localeObj.getTranslatedText("Clear")%></a> 
                                       	 			</td>                                     			   
                                      			    </tr>
                                      			    
                                      			    
                                      	 <!-- search and clear Button -->
                            		 				
                                       				</table>
                                       				
                            <div class="MT30 MB10" >
								<table style="width:100%">
								<%
										int noOfAnnouncements = 0;
										if(pager != null){
											 noOfAnnouncements = ((com.quix.aia.cn.imo.utilities.Pager)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)).getActualSize();
										}%>	
																		
										<tr><td align="left"><a href="FormManager?key=E_GreetingAdd&type=NEW" class="ML10 btn1 " /><%=localeObj.getTranslatedText("Add E-Greeting")%></a></td>
										<td align="right" style="font-weight:bold;padding-right:10px"><%if(pager != null){%> <%=localeObj.getTranslatedText("Search Result")%> : <%=noOfAnnouncements %><%}%>	</td></tr>
								</table>
							</div>
					<%if(pager != null){ %>
                   <div class="data_table  MT20 MB20" >
					<table class="subFormDesign">
						<thead>
						<tr>
							<th width="25%"><label><%=localeObj.getTranslatedText("E-Greeting Cards Name")%></label></th>
			        		<th width="25%"><label><%=localeObj.getTranslatedText("Festive Category")%></label></th>
			        		<th width="20%"><label><%=localeObj.getTranslatedText("Date Created")%></label></th>
			        		<th width="20%"><label><%=localeObj.getTranslatedText("Posted By")%></label></th>
			        		<th width="20%"><label><%=localeObj.getTranslatedText("Modified By")%></label></th>
							<th width="20%"><label><%=localeObj.getTranslatedText("Modification Date")%></label></th>
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
											<td colspan="5" bordercolor="#FFFFFF">
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
									<td colspan="5" bordercolor="#FFFFFF">
										<div align="center">
											<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
										</div></td>
								</tr>
								<%} %>
								</tbody>
							</table>
							
							</div>
							<%} %>
						</form>
											
					</div>
		</div>	
 