<%--
  - Author(s):          Nibedita
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@page import="java.util.Map"%>
<%@page import="com.quix.aia.cn.imo.utilities.KeyObjPair"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.data.announcement.Announcement"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.mapper.AnnouncementMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %> 

<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
if(localeObj == null)
	System.out.println("Session Object nul " );
else 
	System.out.println("Session Object not null" );
Pager pager = null; 
int noOfAnnouncements = 0;
 if(request.getAttribute(RequestAttributes.PAGING_OBJECT)!=null){
    pager=(Pager)request.getAttribute(RequestAttributes.PAGING_OBJECT);
    noOfAnnouncements= ((Pager)request.getAttribute(RequestAttributes.PAGING_OBJECT)).getActualSize();
} 
/* else if(request.getSession().getAttribute(RequestAttributes.PAGING_OBJECT)!=null){
   pager=(Pager) request.getSession().getAttribute(RequestAttributes.PAGING_OBJECT);
   noOfAnnouncements= ((Pager)request.getSession().getAttribute(RequestAttributes.PAGING_OBJECT)).getActualSize();
} */
User userObj = (User)request.getSession().getAttribute("currUserObj");
int buCode = 0,distCode = 0,branch=0;
String cityCode = 0+"",sscCode = 0+"",office=0+"";
buCode=userObj.getBuCode();
distCode=userObj.getDistrict();
cityCode=userObj.getCityCode();
sscCode=userObj.getSscCode();
branch=userObj.getBranchCode();
office=userObj.getOfficeCode();
ImoUtilityData imoUtilityDate=new ImoUtilityData();
KeyObjPair keyObjPair = null;
String subject = "";
int month=0,year=0;

Calendar calendar = GregorianCalendar.getInstance();
month = calendar.get( Calendar.MONTH ) + 1;
year = calendar.get( Calendar.YEAR );


if(request.getSession().getAttribute("ANN_SEARCH_OBJ")!=null){
	Map<String,String> annMap = (Map)request.getSession().getAttribute("ANN_SEARCH_OBJ");
	subject = annMap.get("SUBJECT");
	month = Integer.parseInt(annMap.get("MONTH"));
	year = Integer.parseInt(annMap.get("YEAR"));
	buCode = Integer.parseInt(annMap.get("BU"));
	distCode = Integer.parseInt(annMap.get("DIST"));
	branch=Integer.parseInt(annMap.get("branchCode"));
	cityCode = annMap.get("CITY");
	sscCode = annMap.get("SSC");
	office = annMap.get("officeCode");
}


%>

<script type="text/javascript">//<![CDATA[

function submitForm(){
	document.announcementForm.submit();
	
}

function confirmDelete(code){
	var str="<%=localeObj.getTranslatedText("Are you sure you want to delete this Announcement?")%>";
	var dialog = $('<p>'+str+'</p>').dialog({
   	 width: 550,
    	 cache: false,
        buttons: {
            "<%=localeObj.getTranslatedText("Yes")%>": function() {
            	window.location.href = 'FormManager?key=EopAnnouncementAdd&type=DELETE&annoucementCode='+code;
            },
            "<%=localeObj.getTranslatedText("No")%>":  function() {$('.ui-dialog').hide();},
            
        }
    });
}
             
</script>
<script>
$( document ).ready(function() {
	
	
	
	$('#clear').click(function(){
		   <%
			
			if(userObj.getUserType().equals("AD") ){ %> 
					getBU('<%=buCode  %>');
			<% }%>
			
			<%if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ){ %>
				getDistrict('<%=buCode %>','<%=distCode %>');
			
			<% }%>
			
			<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
			 
				getBranch('<%=distCode %>','<%=branch %>');
			<% }%>
			
			<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()  ){ %>
			 
				getCity('<%=branch %>','<%=cityCode %>');
			<% }%>
			
			<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
				
				getSSC('<%=cityCode %>','<%=sscCode %>');
			<% }%>
			$("#subject").val('');
			
			$('#month option[value='+<%=month %>+']').attr('selected', true);
			
		});
	   
	
	
	
<%
	
	if(userObj.getUserType().equals("AD") ){ %> 
			getBU('<%=buCode %>','L');
			
			
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ){ %>
					getDistrict('<%=buCode %>','<%=distCode %>','L');
					
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isBranchLevel() ){ %>
					getBranch('<%=distCode %>','<%=branch %>','L');
					
				<% }%>
			
				<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
				 
				getCity('<%=branch %>','<%=cityCode %>','L');
				
			<% }%>
			
			<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
				
				getSSC('<%=cityCode %>','<%=sscCode %>','L');
				
			<% }%>
			
			<%if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
			
			getOffice('<%=sscCode %>','<%=office %>');
			
		<% }%>
		
		
	
});
$(function() {

	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even"); 
 });
 
</script>


   	<div id="maincontainer">		
   					<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("Announcement Maintenance")%></h2></div>  	
   							<div class="content" style="background-color:#ffffff;">	 
   								  <form action="ContentManager" name="announcementForm" method="post" class="PT20">
       							 <input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
       							  <table class="formDesign">
       								  <%
				                     if(request.getAttribute(RequestAttributes.MESSAGE_OBJ)!=null)
				                     {
				                      %>	
                                		<tr>                         
                                    	<td colspan="2" style="text-align: center">
                                    	<span style="color:#ec2028;">                                  				
	                                    	<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.MsgObject)request.getAttribute(RequestAttributes.MESSAGE_OBJ)).getMsg())%>
	                                    </span>
                                    	</td>
                                        <%	request.removeAttribute(RequestAttributes.MESSAGE_OBJ);%>
                                        </tr>	 
				                    <% } %>                                			   
                                      		
                                		<tr>                         
                                    	<td>	                                    				
	                                    	<label ><%=localeObj.getTranslatedText("Subject")%></label>
                                    	</td>
                                        <td>	
                                        	<input name="<%=AnnouncementMaintenance.SUBJECT_PARAM %>" id="<%=AnnouncementMaintenance.SUBJECT_PARAM %>" type="text" class="textObj"  maxlength = "250"    />
                                       	</td>                                     			   
                                      	</tr> 
                                      
                		 				<tr>                         
                        				<td>                                   				
                         				<label ><%=localeObj.getTranslatedText("Month")%></label>
                        				</td>
                            			<td>	
                            				<select name="<%=AnnouncementMaintenance.MONTH_PARAM %>" id="<%=AnnouncementMaintenance.MONTH_PARAM %>" class="comboObj" style="width:35%;">
                           						<%for(int i=0; i<AnnouncementMaintenance.MONTH_VALUE_PAIR.length; i++){%>
															<%keyObjPair = AnnouncementMaintenance.MONTH_VALUE_PAIR[i];%>
															<%if(Integer.parseInt(keyObjPair.getKey().toString()) == month){%>
																<option value="<%=keyObjPair.getKey()%>" selected><%=localeObj.getTranslatedText((String)keyObjPair.getObj())%></option>
															<%}else{%>	
																<option value="<%=keyObjPair.getKey()%>"><%=localeObj.getTranslatedText((String)keyObjPair.getObj())%></option>
															<%}%>
														<%}%>                                      						                      	
                           	 				 </select>
                           	 				 
			                          
                           	 				 <select name="<%=AnnouncementMaintenance.YEAR_PARAM %>" id="<%=AnnouncementMaintenance.YEAR_PARAM %>" class="comboObj" style="width:29%;">
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
                                       <tr>                         
                        				<td>                                   				
                         				<label ><%=localeObj.getTranslatedText("Bu")%></label>
                        				</td>
                            			<td>	
                            			<% if(userObj.getUserType().equals("AD")){ %>
                            				<select name="<%=AnnouncementMaintenance.BU_PARAM %>" id="<%=AnnouncementMaintenance.BU_PARAM %>" class="comboObj"  onchange="getDistrict(this.value,0,'E');" >
                           						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>                                   	
                           	 				 </select>
                           	 				 <script> </script>
                           	 			<%}else{ %>
                           	 			<select name="<%=AnnouncementMaintenance.BU_PARAM %>" id="<%=AnnouncementMaintenance.BU_PARAM %>" class="comboObj">
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
                             				<select name="<%=AnnouncementMaintenance.DISTRICT_PARAM %>" id="<%=AnnouncementMaintenance.DISTRICT_PARAM %>" class="comboObj" onchange="getBranch(this.value,0,'E');" >
                            						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>                                  	
                            	 				 </select>
                            	 				
                            	 		<%}else{ %>
                            	 		<select name="<%=AnnouncementMaintenance.DISTRICT_PARAM %>" id="<%=AnnouncementMaintenance.DISTRICT_PARAM %>" class="comboObj">
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
                             		<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ||  userObj.isDistrictLevel()){ %>
                             				<select name="branch" id="branch" class="comboObj" onchange="getCity(this.value,0,'E');" >
                            						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>                                  	
                            	 				 </select>
                            	 		<%}else{ %>
                            	 		<select name="branch" id="branch" class="comboObj">
                            						<option value="<%=userObj.getBranchCode() %>" ><%= imoUtilityDate.getBranchCode(userObj.getBranchCode()) %></option>                                 	
                            	 		 </select>
                            	 		<%} %>
                            	 	    </td>                                     			   
                           			    </tr>
                           			    
                          			
                                        <tr>                         
                        				<td>                                   				
                         				<label ><%=localeObj.getTranslatedText("City")%></label>
                        				</td>
                            			<td>	
                            			<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ||  userObj.isDistrictLevel() || userObj.isBranchLevel()){ %>	
                            				<select name="<%=AnnouncementMaintenance.CITY_PARAM %>" id="<%=AnnouncementMaintenance.CITY_PARAM %>" class="comboObj" onchange="getSSC(this.value,0,'E');" >
                           						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>  
                           						                                	
                           	 				 </select>
                           	 			<%}else{ %>
                           	 			<select name="<%=AnnouncementMaintenance.CITY_PARAM %>" id="<%=AnnouncementMaintenance.CITY_PARAM %>" class="comboObj">
                           						<option value="<%=userObj.getCityCode() %>" ><%=imoUtilityDate.getCityName(userObj.getCityCode()) %></option> 
                           						                             	
                           	 		    </select>
                           	 			<%} %>
                           	 				 
                           	 		    </td>                                      			   
                          			    </tr>
                     			             
                                        <tr>                         
                        				<td>                                   				
                         				<label ><%=localeObj.getTranslatedText("SSC")%></label>
                        				</td>
                            			<td>
                            		<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ||  userObj.isDistrictLevel() || userObj.isBranchLevel() || userObj.isCityLevel()){ %>
                          				<select name="<%=AnnouncementMaintenance.SSC_PARAM %>" id="<%=AnnouncementMaintenance.SSC_PARAM %>" class="comboObj" onchange="getOffice(this.value,0);">
                         						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>
                         						                           	
                         	 			</select>
                         	 			<%}else{ %>
                         	 			<select name="<%=AnnouncementMaintenance.SSC_PARAM %>" id="<%=AnnouncementMaintenance.SSC_PARAM %>" class="comboObj">
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
                       				<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ||  userObj.isDistrictLevel() || userObj.isBranchLevel() || userObj.isCityLevel() || userObj.isSscLevel()){ %>	
                       				<select name="office" id="office"  class="comboObj"   >
                      						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                      	 				 </select>
                      	 				 <%}else{ %>
                      	 				 <select name="office" id="office"  class="comboObj" >
                      						<option value="<%=userObj.getOfficeCode() %>" ><%=imoUtilityDate.getOfficeName(userObj.getOfficeCode()) %></option>	                                       	
                      	 				 </select>
                      	 				 <%} %>
                      	 			</td>                                     			   
                     			    </tr>	  
                           			    <tr>                         
                             			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                             				<a href="#Search" class="btn1 " onclick="javascript:submitForm();"><%=localeObj.getTranslatedText("Search")%></a>
                 		 					<a href="#" id="clear" name="clear" class="ML10 btn1"><%=localeObj.getTranslatedText("Clear")%></a> 
                            	 		</td>                                     			   
                           			    </tr>
                                       </table>
                                       </form>
                                     <div class="MT5" >
									<table style="width:100%">
											<tr><td align="left"><a href="FormManager?key=EopAnnouncementAdd&type=NEW"  class="ML10 btn1"><%=localeObj.getTranslatedText("Add Announcement")%></a></td>
											<td align="right" style="font-weight:bold;padding-right:10px"><%if(pager != null){ %><%=localeObj.getTranslatedText("Search Result")%> : <%=noOfAnnouncements %><%} %></td></tr>
									</table>
									</div>
                                	 
                          <%if(pager != null){%>
                                     
      					 <div class="data_table  MT20 MB20" style="width:100%" >
			           		<table class="subFormDesign">
							<thead>
							<tr>
			        		<th width="10%"><label><%=localeObj.getTranslatedText("Subject")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Message Type")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Published Date")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Expired Date")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Announcement Status")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("BU")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("District")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Branch")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("City")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("SSC")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Office")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Post By")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Modified")%></label></th>
							<th><label><%=localeObj.getTranslatedText("Modification Date")%></label></th>
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
											<td colspan="17" bordercolor="#FFFFFF">
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
     
					</div><%} %>
				<br>	
				</div>
			</div>

   

