<%--
  - Author(s):          Abinas
  - Date:               15-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:         Presenter search anf listing page dreated
--%>
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
String city=0+"";
String ssc=0+"";
int branch=0;
String office=0+"";
User userObj = (User)request.getSession().getAttribute("currUserObj");
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Pager pager = (Pager) request.getAttribute(RequestAttributes.PAGING_OBJECT);
PresenterMaintenance obj=new PresenterMaintenance();

//ArrayList<Presenter> arrPresenter = new ArrayList();
//arrPresenter=obj.getAllPresenter(request);
ArrayList<PresenterCategory> arrCategory = new ArrayList();
arrCategory=obj.getAllPresenterCategory(request);

bu=userObj.getBuCode();
dis=userObj.getDistrict();
branch=userObj.getBranchCode();
city=userObj.getCityCode();
ssc=userObj.getSscCode();
office=userObj.getOfficeCode();
ImoUtilityData imoUtilityDate=new ImoUtilityData();
KeyObjPair keyObjPair = null;
%>
<script>
$( document ).ready(function() {
<%
	
	if(userObj.getUserType().equals("AD") ){ %> 
			getBU('<%=bu %>','L');
			
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ){ %>
					getDistrict('<%=bu %>','<%=dis %>','L');
				
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isBranchLevel() ){ %>
					getBranch('<%=dis %>','<%=branch %>','L');
			
				<% }else{%>
				
					<%if(!userObj.isCityLevel()){ %>
						getBranch('<%=dis %>','<%=branch %>','L');
					<%} %>
				<% }%>
				
				
				<%if(userObj.getUserType().equals("AD") || userObj.isCityLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
				 
					getCity('<%=branch %>','<%=city %>','L');
				<% }%>
	
				
			
	<%if(userObj.getUserType().equals("AD") ||userObj.isSscLevel()  || userObj.isBranchLevel()  || userObj.isDistrictLevel() ||  userObj.isBuLevel() || userObj.isCityLevel() ){%>
		
		getSSC('<%=city %>','<%=ssc %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD")  ||userObj.isSscLevel()  || userObj.isOfficeLevel()   || userObj.isDistrictLevel() ||  userObj.isBuLevel() || userObj.isCityLevel() ){%>
	
	getOffice('<%=ssc %>','<%=office %>');
<% }%>
	
});
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
// search button
function searchButton(){
	document.presenterForm.submit();
}
//delete presenter
function deletePresenter(presenter_code){
	var str="<%=localeObj.getTranslatedText("Are you sure you want to delete this Presenter?")%>";
	var dialog = $('<p>'+str+'</p>').dialog({
	   	 width: 550,
	    	 cache: false,
	        buttons: {
	            "<%=localeObj.getTranslatedText("Yes")%>": function() {
	            	window.location.href = 'FormManager?key=PresenterAdminister&type=DELETE&presenter_code='+presenter_code;
	            },
	            "<%=localeObj.getTranslatedText("No")%>":  function() {
	            	$('.ui-dialog').hide();
	            },
	            
	        }
	    });
	
}
</script>
   	<div id="maincontainer">
   	<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("Presenter Maintenance")%></h2></div> 	
   			   
   					 	
   							<div class="content" style="background-color:#ffffff;" >	 
   												   <form name="presenterForm" action="ContentManager" method="post" class="PT20">
                           				<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
                           				 <table class="formDesign" >
                           			<tr>
									<td colspan="2" style="text-align: center;color:#ec2028;">
									<%if(request.getAttribute(RequestAttributes.MESSAGE_OBJ)!=null){%>
										<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.MsgObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.MESSAGE_OBJ)).getMsg())%>
									<%	request.removeAttribute(RequestAttributes.MESSAGE_OBJ);
				                     } %>
				                     
				                  
									</td>
								</tr>
                                		<tr  >                         
                                    	<td style="border: none;"  >	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Presenter Name")%></label>
                                    	</td>
                                        <td>
                                        <input name="<%=PresenterMaintenance.NAME_PARAM%>" id="<%=PresenterMaintenance.NAME_PARAM %>" type="text" class="textObj" value=""  />	
                                        				
                            	 			</td>                                     			   
                           			    </tr>
                           			    <!-- month year -->
                           			    	 				
                		 				<tr  >                         
                        				<td style="border: none;"  >                                   				
                         				<label ><%=localeObj.getTranslatedText("Month")%></label>
                        				</td>
                            			<td >
                            			<%
			                                int month = 0;
			                                Calendar calendar = GregorianCalendar.getInstance();
			                                month = calendar.get( Calendar.MONTH );
			                                System.out.println("Month==="+month);
			                               
									 	%>	
                            				<select name="<%=PresenterMaintenance.MONTH_PARAM %>" id="<%=PresenterMaintenance.MONTH_PARAM %>" class="comboObj" style="width:35%;">
                           						<%for(int i=0; i<PresenterMaintenance.MONTH_VALUE_PAIR.length; i++){%>
															<%keyObjPair = PresenterMaintenance.MONTH_VALUE_PAIR[i];%>
															<%if(Integer.parseInt(keyObjPair.getKey().toString()) == (month+1)){%>
																<option value="<%=keyObjPair.getKey()%>" selected><%=localeObj.getTranslatedText((String)keyObjPair.getObj())%></option>
															<%}else{%>	
																<option value="<%=keyObjPair.getKey()%>"><%=localeObj.getTranslatedText((String)keyObjPair.getObj())%></option>
															<%}%>
														<%}%>                                      						                      	
                           	 				 </select>
			                        
                           	 				 
                           	 			                                    			   
                           	 			
                            			
			                           	  <%
			                                int year = 0;
			                                 calendar = GregorianCalendar.getInstance();
									        year = calendar.get( Calendar.YEAR );
			                               
									 	%>
                           	 				 <select name="<%=PresenterMaintenance.YEAR_PARAM %>" id="<%=PresenterMaintenance.YEAR_PARAM %>" class="comboObj" style="width:29%;">
             		              	             <%for(int i=2004; i<=year+1; i++){%>
															<%if(i == year){%>
																<option value="<%=i%>" selected><%=i%></option>
															<%}else{%>
																<option value="<%=i%>"><%=i%></option>
															<%}%>
														<%}%>
                           						                                       	
                           	 				 </select>
                           	 				 
                           	 		
                          			    </tr>
                          			     <!-- month year -->
                           			    	 				
                		 				
                           			   <!--  presenter category -->
                                				<tr  >                         
                                    	<td style="width:30%;border: none;" ><label ><%=localeObj.getTranslatedText("Presenter Category")%></label>	</td>                                    				
                                        <td>	
                                        				<select name="presenter_category" id="presenter_category"  class="comboObj" >
			                                				<option value="0"><%=localeObj.getTranslatedText("Select")%></option>
			                                				<%
			                                				for(int i=0;i<arrCategory.size();i++){
			                                					PresenterCategory presentercategoryObj=(PresenterCategory)arrCategory.get(i);
			                                				
			                                				%>
			                                				<option value="<%=presentercategoryObj.getPresenterCategoryCode()%>"><%=SecurityAPI.encodeHTML(presentercategoryObj.getPresenterCategoryName()) %></option>
			                                				<%}   %>                                   	
                                	 			        </select>
                            	 			</td>                                     			   
                           			    </tr>
                           			          		    <!-- BU -->
                                      			    		<tr>                         
                                			<td style="border: none;"  >                                    				
                                 				<label ><%=localeObj.getTranslatedText("Bu")%></label>
                                			</td>
                                  			<td >	
                                  			<% if(userObj.getUserType().equals("AD")){ %>
                                				<select name="<%=PresenterMaintenance.BU_PARAM %>" id="<%=PresenterMaintenance.BU_PARAM %>"  class="comboObj" onchange="getDistrict(this.value,'<%=dis %>','E');" >
                               						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>	 
                               	 				 </select>
                               	 				  <% }else{ %>
                               	 				  <select name="<%=PresenterMaintenance.BU_PARAM %>" id="<%=PresenterMaintenance.BU_PARAM %>"  class="comboObj" onchange="getDistrict(this.value,'<%=dis %>','E');" >
                               						<option value="<%=userObj.getBuCode() %>" ><%=imoUtilityDate.getBuCode(userObj.getBuCode()) %></option>	 
                               	 				 </select>
                               	 				 <%} %>
                                 	 		</td>                                     			   
                                  		</tr>
                        		 		<tr>                         
                                			<td style="border: none;"  >                                    				
                                 				<label ><%=localeObj.getTranslatedText("District")%></label>
                                			</td>
                                  			<td >
                                  			<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel()){ %>	
                                				<select name="<%=PresenterMaintenance.DISTRICT_PARAM %>" id="<%=PresenterMaintenance.DISTRICT_PARAM %>"  class="comboObj"  onchange="getBranch(this.value,'<%=branch %>','E');" >
                               						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	 
                               	 				 </select>
                               	 				 <%}else{ %>
                               	 				 <select name="<%=PresenterMaintenance.DISTRICT_PARAM %>" id="<%=PresenterMaintenance.DISTRICT_PARAM %>"  class="comboObj"   >
                               						<option value="<%=userObj.getDistrict() %>" ><%= imoUtilityDate.getDistCode(userObj.getDistrict()) %></option>	 
                               	 				 </select>
                               	 				 <%} %>
                                 	 		</td>                                     			   
                                  		</tr>	
                                  		
                                  		
                                  		<tr>                         
                                			<td style="border: none;"  >                                    				
                                 				<label ><%=localeObj.getTranslatedText("Branch")%></label>
                                			</td>
                                  			<td >
                                  			<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel() || userObj.isDistrictLevel()  ){ %>	
                                				<select name="branch" id="branch"  class="comboObj"  onchange="getCity(this.value,'<%=city %>','E');" >
                               						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	 
                               	 				 </select>
                               	 				 <%}else{ %>
                               	 				 <select name="branch" id="branch"  class="comboObj"   >
                               						<option value="<%=userObj.getBranchCode() %>" ><%= imoUtilityDate.getBranchCode(userObj.getBranchCode()) %></option>
                               						 
                               	 				 </select>
                               	 				 <%} %>
                                 	 		</td>                                     			   
                                  		</tr>
                                  		
                        		 		 <tr>                         
                            				<td style="border: none;"  >                                    				
                             				<label ><%=localeObj.getTranslatedText("City")%> </label>
                            				</td>
                                			<td>
                                			<% if(userObj.getUserType().equals("AD") ||userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                               				<select name="<%=PresenterMaintenance.CITY_PARAM %>" id="<%=PresenterMaintenance.CITY_PARAM %>"  class="comboObj"  onchange="getSSC(this.value,'<%=ssc %>','E');">
                              						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                              	 			 </select>
                              	 			 <%}else{ %>
                              	 			 <select name="<%=PresenterMaintenance.CITY_PARAM %>" id="<%=PresenterMaintenance.CITY_PARAM %>"  class="comboObj"  >
                              							 <option value="<%=userObj.getCityCode() %>" ><%=imoUtilityDate.getCityName(userObj.getCityCode()) %></option>                                      	
                              	 			 </select>
                              	 			 <%} %>
                               	 			</td>                                     			   
                                  		 </tr>
                                  			   
                           			     
                 		 				<tr  >                         
                         				<td style="border: none;"  >                                  				
                          				<label ><%=localeObj.getTranslatedText("SSC")%></label>
                         				</td>
                             			<td>
                             			<% if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isCityLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                             				<select name="<%=PresenterMaintenance.SSC_PARAM %>" id="<%=PresenterMaintenance.SSC_PARAM %>"  class="comboObj"  onchange="getOffice(this.value,'<%=office %>');" >
                            						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                            	 				 </select>
                            	 				 <%}else{ %>
                            	 				 <select name="<%=PresenterMaintenance.SSC_PARAM %>" id="<%=PresenterMaintenance.SSC_PARAM %>"  class="comboObj"  >
                            						<option value="<%=userObj.getSscCode() %>" ><%=imoUtilityDate.getSscName(userObj.getSscCode()) %></option>	                                       	
                            	 				 </select>
                            	 				 <%} %>
                            	 			</td>                                     			   
                           			    </tr>
                           			    
                           			    
                           			     <tr  >                         
                         				<td style="border: none;"  >                                  				
                          				<label ><%=localeObj.getTranslatedText("Office")%></label>
                         				</td>
                             			<td>
                             			<% if(userObj.getUserType().equals("AD") || userObj.isOfficeLevel() || userObj.isSscLevel() || userObj.isCityLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                             				<select name="office" id="office"  class="comboObj"  >
                            						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                            	 				 </select>
                            	 				 <%}else{ %>
                            	 				 <select name="office" id="office"  class="comboObj"  >
                            						<option value="<%=userObj.getOfficeCode() %>" ><%=imoUtilityDate.getOfficeName(userObj.getOfficeCode()) %></option>	                                       	
                            	 				 </select>
                            	 				 <%} %>
                            	 			</td>                                     			   
                           			    </tr>
                           			    
                           			    
                           			    
                                      			    <!-- END -->
                                      			    <tr  >                         
	                                    				
   
                                        			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                                        				<a href="#"  class="btn1 " id="submitFile" name="submitFile" onClick="javascript:searchButton();" ><%=localeObj.getTranslatedText("Search")%></a>
                            		 					<a href="javascript:document.presenterForm.reset();" class=" ML10 btn1"><%=localeObj.getTranslatedText("Clear")%></a> 
                                       	 			</td>                                     			   
                                      			    </tr>
                                      			    
                                    
                            		 				
                                       				</table>
                                       				</form>
                                                   

                                	  <br>
                                	<div class="MT5 MB10" >
                                	<table style="width:100%">
										<%
										int noOfPresenter = 0;
	                                	if(pager != null){
											noOfPresenter = ((com.quix.aia.cn.imo.utilities.Pager)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)).getActualSize();
										}%>								
										<tr><td align="left"><a href="FormManager?key=PresenterAdminister&type=NEW" class="ML10 btn1 "><%=localeObj.getTranslatedText("Add Presenter")%></a> </td>
										<td align="right" style="font-weight:bold;padding-right:10px"><%if(pager != null){ %><%=localeObj.getTranslatedText("Search Result")%> : <%=noOfPresenter %><%} %></td></tr>
								</table>
								
							</div>
							<%if(pager != null){%>
                           	<div class="data_table  MT20 MB20" style="width:100%" >
							<table class="subFormDesign">
								<thead>
										<tr>
											<th width="7%"><label><%=localeObj.getTranslatedText("Presenter Name")%></label></th>
							        		<th  width="7%"><label><%=localeObj.getTranslatedText("Category")%></label></th>
							        		<th  width="7%"><label><%=localeObj.getTranslatedText("Start Date")%></label></th>
							        		<th  width="7%"><label><%=localeObj.getTranslatedText("Expiry Date")%></label></th>
							        		<th width="7%"><label><%=localeObj.getTranslatedText("BU")%></label></th>
							        		<th width="7%"><label><%=localeObj.getTranslatedText("District")%></label></th>
							        		<th  width="7%"><label><%=localeObj.getTranslatedText("Branch")%></label></th>
							        		<th  width="7%"><label><%=localeObj.getTranslatedText("City")%></label></th>
							        		
							        		<th width="7%"><label><%=localeObj.getTranslatedText("SSC")%></label></th>
							        		<th  width="7%"><label><%=localeObj.getTranslatedText("Office")%></label></th>
							        		<th width="7%"><label><%=localeObj.getTranslatedText("Date Posted")%></label></th>
							        		<th width="7%"><label><%=localeObj.getTranslatedText("Modified By")%></label></th>
											<th width="7%"><label><%=localeObj.getTranslatedText("Modification Date")%></label></th>
							        		<th width="7%"><label><%=localeObj.getTranslatedText("Action")%></label></th>    
							        	</tr>
								</thead>
			<tbody>
									<%
									if(pager != null){ %>
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
											<td colspan="11" bordercolor="#FFFFFF">
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
									<td colspan="11" bordercolor="#FFFFFF">
										<div align="center">
											<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
										</div></td>
								</tr>
								<%} %>
								
								</tbody>
							</table>
							
							</div>          
        
           <%} %>
				</div>
    </div>
	