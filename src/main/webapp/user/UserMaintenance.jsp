<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@page import="com.quix.aia.cn.imo.data.channel.Channel"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.department.Department"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.quix.aia.cn.imo.mapper.UserMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<%LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ); %>
<script type="text/javascript">
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
function confirmDelete(usercode){
	var str="<%=localeObj.getTranslatedText("Are you sure you want to delete this User?")%>";
	var dialog = $('<p>'+str+'</p>').dialog({
   	 width: 550,
    	 cache: false,
        buttons: {
            "<%=localeObj.getTranslatedText("Yes")%>": function() {
            	window.location.href = 'FormManager?key=UserAdd&type=DELETE&userNo='+usercode;
            },
            "<%=localeObj.getTranslatedText("No")%>":  function() {
            	$('.ui-dialog').hide();
            },
            
        }
    });
}
</script>
<%
int bu=0;
int dis=0;
int branch=0;
String city=0+"";
String ssc=0+"";
String office=0+"";

Pager pager = (Pager) request.getAttribute(RequestAttributes.PAGING_OBJECT);

User userObj = (User)request.getSession().getAttribute("currUserObj");
String choY="",choN="";

if(userObj.getUserType().equals("AD")){
	choY="selected=selected";
}else{
	choN="selected=selected";
}

bu=userObj.getBuCode();
dis=userObj.getDistrict();
city=userObj.getCityCode();
ssc=userObj.getSscCode();
branch=userObj.getBranchCode();
office=userObj.getOfficeCode();
	ImoUtilityData imoUtilityDate=new ImoUtilityData();
	ArrayList<Channel> alChannel=imoUtilityDate.getActiveChannels(request);
%>

<style>
@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) {.class_limit{width:260px;}}
@media screen and (min-width:0\0) and (min-resolution: +72dpi) {.class_limit{width:270px;}}
</style>
<div class="page-wrap">
		<div id="maincontainer">
					<div class="head">
						<h2 align="center"><%=localeObj.getTranslatedText("User Management")%></h2>
					</div>
					<div class="content" style="background-color:#ffffff;">
				<form  method="post"  action="ContentManager" name="administrationForm"  >
				<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
  				<input type="hidden" name="pager.offset" id="pager.offset" value="0">
                 
     			<table class="formDesign">
                    <tr>
						<td colspan="2" style="text-align: center">
						<span style="color:#ec2028;">
						 <%if(request.getSession().getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.MESSAGE_OBJ) == null){%>
                        
                   		 <%}else{%>
                        <%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.MsgObject)request.getSession().getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.MESSAGE_OBJ)).getMsg())%>
                       <%
						request.getSession().removeAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.MESSAGE_OBJ);
					}%> 
						 </span></td>
					</tr>
                          
                          
                         						 <tr>                         
                                    				<td>	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("CHO")%></label>
                                    				</td>
                                        			<td style="border: none; "  >	
                                        			
                                       	 			<select name="cho" id="cho" class="comboObj"  >
                                       	 				 <option value="Select"  ><%=localeObj.getTranslatedText("Select") %></option>
                                       						<option value="N" <%=choN %> ><%=localeObj.getTranslatedText("No") %></option>
                                       						<% if(userObj.getUserType().equals("AD")){ %>
                                       						<option value="Y" ><%=localeObj.getTranslatedText("Yes") %></option>
                                       	 			<%} %>
                                       						<%-- <% if(userObj.getUserType().equals("AD")){ %>
                                       						<option value="Y" <%=choY %> ><%=localeObj.getTranslatedText("Yes") %></option>
                                       	 			<%} %> --%>
                                       	 			</select>
                                       	 			

                                       	 			</td>   
                                       	 			                                   			   
                                      			    </tr>
                                      			    
                                      		<tr>                      
	                       <td>                                    				
	                        	<label ><%=localeObj.getTranslatedText("Staff Id")%></label>
	                       	</td>
                           <td>
                           		<input name="staffId" id="staffId" type="text" class="textObj"  value=""  />
                          </td>                                     			   
                        </tr>	    
                                				
                  		<tr>                      
	                       <td>                                    				
	                        	<label ><%=localeObj.getTranslatedText("Staff Name")%></label>
	                       	</td>
                           <td>
                           		<input name="staffName" id="staffName" type="text" class="textObj"  value=""  />
                          </td>                                     			   
                        </tr>
                                      	
                       <%-- 	<tr>                         
	                      	<td>	                                    				
	                       		<label style="vertical-align: top; " ><%=localeObj.getTranslatedText("Channel")%></label>
	                      	</td>
                          <td>	
              				<select name="channel" id="channel" class="comboObj"  multiple="multiple">
             						<%for(Channel channel:alChannel){%>
                        			<option value="<%=channel.getChannelCode() %>" ><%=channel.getChannelName() %></option>     
                        			<%} %> 
             	 				 </select>
             	 		    </td>                                     			   
                       	</tr> --%>
                            		 				
   		 				<tr  >                         
            				<td>                                   				
             					<label ><%=localeObj.getTranslatedText("Department")%></label>
            				</td>
            				<%
	            				ImoUtilityData imoutility=new ImoUtilityData();
	                			ArrayList<Department> alDept=imoutility.getAllDepartment();
                            %>		
                			<td>	
                				<select name="dept" id="dept" class="comboObj"  >
                				  <option value="0"  ><%=localeObj.getTranslatedText("Select") %></option>
                				   <%for(Department department : alDept){%>
                           			
                           		<option value="<%=department.getDeptCode() %>" ><%=SecurityAPI.encodeHTML(department.getDeptName()) %></option>
                           					<%}%>	                               	
               	 				 </select>
               	 				 
               	 		    </td>                                     			   
             			    </tr> 
                                                    
                             <tr>                         
	              				<td>                                   				
	               					<label ><%=localeObj.getTranslatedText("Bu")%></label>
	              				</td>
	                  			<td>	
	                  				<% if(userObj.getUserType().equals("AD")){ %>
	                             		<select name="bu" id="bu" class="comboObj"  onchange="getDistrict(this.value,'<%=dis %>','E');"  >
	                            			<option value="0" ><%=localeObj.getTranslatedText("Select") %></option> 	
	                            	 	</select>
	                            	 <% }else{ %>
	                            	 			
                          	 			<select name="bu" id="bu" class="comboObj"  >
                          						<option value="<%=userObj.getBuCode() %>" ><%=imoUtilityDate.getBuCode(userObj.getBuCode()) %></option>
                          	 			</select>
	                            	 <%} %>	 
	                 	 		</td>                                     			   
               			    </tr>
                          			    
                          			     <tr>                         
	                         				<td>                                   				
	                          				<label ><%=localeObj.getTranslatedText("District")%></label>
	                         				</td>
	                             			<td>	
	                             				<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel()){ %>
                                        				<select name="district" id="district" class="comboObj"  onchange="getBranch(this.value,'<%=branch %>','E');"  >
                                       						<option value="0" ><%=localeObj.getTranslatedText("All") %></option>
                                       						                          	
                                       	 			   </select>
                                       	 			<%}else { %>
                                       	 			
                                       	 			<select name="district" id="district" class="comboObj"  >
                                       						<option value="<%=userObj.getDistrict() %>" ><%= imoUtilityDate.getDistCode(userObj.getDistrict()) %></option>
                                       						                          	
                                       	 			   </select>
                                       	 			<%} %>
	                            	 	    </td>                                     			   
                           			    </tr>
                           			    
                           			    
                           			    <tr>                         
	                         				<td>                                   				
	                          				<label ><%=localeObj.getTranslatedText("Branch")%></label>
	                         				</td>
	                             			<td>
                                        			<% if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
                                        				<select name="branch" id="branch" class="comboObj" onchange="getCity($('#district').val(),'<%=city %>','E');"  >
                                       						<option value="0" ><%=localeObj.getTranslatedText("All") %></option>
                                       						                          	
                                       	 			   </select>
                                       	 			<%}else { %>
                                       	 		
                                       	 		<select name="branch" id="branch" class="comboObj"   >
                                       						<option value="<%=userObj.getBranchCode() %>" ><%= imoUtilityDate.getBranchCode(userObj.getBranchCode()) %></option>
                                       						                          	
                                       	 			   </select> 
                                       	 			<%} %>
                                       	 			</td>                                      			   
                           			    </tr>
                           			    
                           			    
                           			    
                           			    
                           			    
                           			    
                          			
                                        <tr>                         
	                        				<td>                                   				
	                         					<label ><%=localeObj.getTranslatedText("City")%></label>
	                        				</td>
	                            			<td >	
	                            				<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel() || userObj.isDistrictLevel()){ %>
														<select name="city" id="city" class="comboObj"   onchange="getSSC(this.value,'<%=ssc %>');"  >
                                       						<option value="0" ><%=localeObj.getTranslatedText("All") %></option>	                                       	
                                       	 			   </select>
                                       	 			<%}else { %>
                                       	 			
                                       	 			<select name="city" id="city" class="comboObj"  >
                                       						<option value="<%=userObj.getCityCode() %>" ><%=imoUtilityDate.getCityName(userObj.getCityCode()) %></option>	                                       	
                                       	 			   </select>
                                       	 			<%} %>
	                           	 		    </td>                                     			   
                          			    </tr>
                                                    
                                        <tr  >                         
	                        				<td style="border: none;"  >                                   				
	                         				<label ><%=localeObj.getTranslatedText("SSC")%></label>
	                        				</td>
	                            			<td  style="text-align:left">	
		                          				<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel() || userObj.isDistrictLevel() || userObj.isCityLevel()){ %>
                                        				<select name="ssc" id="ssc" class="comboObj"  onchange="getOffice(this.value,'<%=office %>');"  >
                                       						<option value="0" ><%=localeObj.getTranslatedText("All") %></option>
                                       						                          	
                                       	 			     </select>
                                       	 			     
                                       	 	<%}else { %>
                                       	 			
                                       	 			<select name="ssc" id="ssc" class="comboObj"   >
                                       						<option value="<%=userObj.getSscCode() %>" ><%=imoUtilityDate.getSscName(userObj.getSscCode()) %></option>
                                       						                          	
                                       	 			     </select>
                                       	 			<%} %>
	                           	 		    </td>                                     			   
                          			    </tr>
                          			    
                          			    
                          			    
                          			    <tr  >                         
	                        				<td style="border: none;"  >                                   				
	                         				<label ><%=localeObj.getTranslatedText("Office")%></label>
	                        				</td>
	                            			<td  style="text-align:left">	
		                          				<% if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isBuLevel() || userObj.isDistrictLevel() || userObj.isCityLevel()){ %>
                                        				<select name="office" id="office" class="comboObj"  >
                                       						<option value="0" ><%=localeObj.getTranslatedText("All") %></option>
                                       						                          	
                                       	 			     </select>
                                       	 			     
                                       	 	<%}else { %>
                                       	 			
                                       	 			<select name="office" id="office" class="comboObj"   >
                                       						<option value="<%=userObj.getOfficeCode() %>" ><%=imoUtilityDate.getOfficeName(userObj.getOfficeCode()) %></option>
                                       						                          	
                                       	 			     </select>
                                       	 			<%} %>
	                           	 		    </td>                                     			   
                          			    </tr>
                          			    
                          			    
                          			    
                                 <tr >
									<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
									<a href="#"  class="btn1" " id="search" name="search" ><%=localeObj.getTranslatedText("Search")%></a>
	                 		 		<a href="#" id="clear" name="clear" class="ML10 btn1"><%=localeObj.getTranslatedText("Clear")%></a> 
									</td>
								</tr>	  
                           			    	
                 </table> 
                                	
                        <div class="MT30" >
								<table style="width:100%">
										<%int noOfAnnouncements = ((com.quix.aia.cn.imo.utilities.Pager)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)).getActualSize();%>								
										<tr><td align="left">
										<%if(!userObj.isSscLevel()){ %>
                            				<a href="FormManager?key=UserAdd&type=NEW" class="ML10 btn1" /><%=localeObj.getTranslatedText("Add Record")%></a>
                 					<%} %>
									 <a href="FormManager?key=userUploadCsv&type=NEW" class="ML10 btn1" ><%=localeObj.getTranslatedText("Upload")%></a></td>
										<td align="right" style="font-weight:bold;padding-right:10px"><%=localeObj.getTranslatedText("Search Result")%> : <%=noOfAnnouncements %></td></tr>
								</table>
							</div> 
                                	 <div class="form-item MT25" style="margin-left: 8px;">
                            		
                           </div> 
                    <div class="form-item MT25" style="margin-left: 15%;">
                            		
                            				
                  			
               </div> 
                  
                               
         <div class="data_table  MT20 MB20" style="width:100%" >
							<table class="subFormDesign">
							<thead>
           <tr>
        		<th><label><%=localeObj.getTranslatedText("Staff Login ID")%></label></th>
        		<%-- <th><label><%=localeObj.getTranslatedText("Channel")%></label></th> --%>
        		<th><label><%=localeObj.getTranslatedText("BU")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("District")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("Branch")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("City")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("SSC")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("Office")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("Department")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("Name")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("Email Address")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("Contact Number")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("Extension Number")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("Modified By")%></label></th>
				<th><label><%=localeObj.getTranslatedText("Modification Date")%></label></th>
        		<th><label><%=localeObj.getTranslatedText("Action")%></label></th>    
        		
			</tr>
		</thead>
		<tbody>
			  	<tr>
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
											<tr class="odd">
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


 <script type="text/javascript">
	
 
 <%-- $(document).ready(function(){
	   
	 <%if(choY!=""){  %>
		 $("#bu").prop("disabled", true);
		  $("#district").prop("disabled", true);
		  $("#branch").prop("disabled", true);
		  $("#city").prop("disabled", true);
		  $("#ssc").prop("disabled", true);
		  $("#office").prop("disabled", true);
	 <%  }else{%>
	 $("#bu").prop("disabled", false);
	  $("#district").prop("disabled", false);
	  $("#branch").prop("disabled", false);
	  $("#city").prop("disabled", false);
	  $("#ssc").prop("disabled", false);
	  $("#office").prop("disabled", false);
	 <%} %>
	 
 });  --%>
	 $(function() {
         $('.checked').click(function(e) {
             e.preventDefault();
             var dialog = $('<p>Are you sure you want to delete this user?</p>').dialog({
            	 width: 550,
             	 cache: false,
                 buttons: {
                     "<%=localeObj.getTranslatedText("Yes")%>": function() {},
                     "<%=localeObj.getTranslatedText("No")%>":  function() {},
                     
                 }
             });
         });
     });
	
	  
		   $('#search').click(function(){
			   document.administrationForm.submit() ;
		   		 
			});
		   
		   $('#clear').click(function(){
			   <%
				
				if(userObj.getUserType().equals("AD") ){ %> 
						getBU('<%=bu %>');
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ){ %>
					getDistrict('<%=bu %>','<%=dis %>');
				
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
				 
					getBranch('<%=dis %>','<%=branch %>');
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()  ){ %>
				 
					getCity('<%=dis %>','<%=city %>');
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
					
					getSSC('<%=city %>','<%=ssc %>');
				<% }%>
				$("#staffName").val('');
				$("#staffId").val('');
				
				$('#cho option:first-child').attr("selected", "selected");
				$('#dept option:first-child').attr("selected", "selected");
				
			});
		   
		   $( document ).ready(function() {
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
				<%if(!userObj.isCityLevel()){ %>
				getBranch('<%=dis %>','<%=branch %>','L');
				<%} %>
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
				 
					getCity('<%=dis %>','<%=city %>','L');
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
					
					getSSC('<%=city %>','<%=ssc %>','L');
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
				
				getOffice('<%=ssc %>','<%=office %>');
			<% }%>

				$("#progress").hide();
				$("#progress1").hide();
				
			});
		  
    </script>
