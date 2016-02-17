<%--
  - Author(s):          Nibedita
  - Date:               06-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        Added code to populate Bu,district,city,ssc dropdown
--%>
<%@page import="java.util.Map"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.utilities.KeyObjPair"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.quix.aia.cn.imo.mapper.EopMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ page import='com.quix.aia.cn.imo.data.user.*' %>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes'%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %> 

<%User user = ((com.quix.aia.cn.imo.data.user.User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ));
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);

com.quix.aia.cn.imo.utilities.Pager pager=null;
int noOfEvents = 0;
if( request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)!=null){
	pager = (com.quix.aia.cn.imo.utilities.Pager) request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
	noOfEvents = ((Pager)request.getAttribute(RequestAttributes.PAGING_OBJECT)).getActualSize();
}/* else{
	pager = (com.quix.aia.cn.imo.utilities.Pager) request.getSession().getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
} */
User userObj = (User)request.getSession().getAttribute("currUserObj");
int buCode = 0,distCode = 0,organiser = 0,branch=0;
String cityCode = 0+"",sscCode = 0+"",office=0+"";
buCode=userObj.getBuCode();
distCode=userObj.getDistrict();
cityCode=userObj.getCityCode()+"";
sscCode=userObj.getSscCode()+"";
branch=userObj.getBranchCode();
office=user.getOfficeCode()+"";
ImoUtilityData imoUtilityDate=new ImoUtilityData();
KeyObjPair keyObjPair = null;
int month=0,year=0;
String eventName = "",eventType = "",agentTeam = "";
Calendar calendar = GregorianCalendar.getInstance();
month = calendar.get( Calendar.MONTH ) + 1;
year = calendar.get( Calendar.YEAR );


if(request.getSession().getAttribute("EOP_SEARCH_OBJ")!=null){
	Map<String,String> annMap = (Map)request.getSession().getAttribute("EOP_SEARCH_OBJ");
	eventName = annMap.get("EVENT_NAME");
	eventType = annMap.get("EVENT_TYPE");
	month = Integer.parseInt(annMap.get("MONTH"));
	year = Integer.parseInt(annMap.get("YEAR"));
	organiser = Integer.parseInt(annMap.get("ORGANISER"));
	buCode = Integer.parseInt(annMap.get("BU"));
	distCode = Integer.parseInt(annMap.get("DIST"));
	cityCode = annMap.get("CITY");
	sscCode = annMap.get("SSC");
	office = annMap.get("officeCode");
	agentTeam = annMap.get("AGENT_TEAM");
	branch=Integer.parseInt(annMap.get("branchCode"));
}
%>

<script>
$(function(){
	//$('form').jqTransform({imgPath:'jqtransformplugin/img/'});

	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
$( document ).ready(function() {
<%
	
if(userObj.getUserType().equals("AD") ){ %> 
		getBU('<%=buCode %>');
		
<% }%>

<%if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ){ %>
					getDistrict('<%=buCode %>','<%=distCode %>','L');
				
				<% }%>
				
				
				<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isBranchLevel() ){ %>
			getBranch('<%=distCode %>','<%=branch %>','L');
	
		<% }%>
	
		<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
		 
		getCity('<%=distCode %>','<%=cityCode %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
		
		getSSC('<%=cityCode %>','<%=sscCode %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
	
	getOffice('<%=sscCode %>','<%=office %>');
	getAgentTeam1();
<% }%>
		
});
function confirmDelete(code){
	
	var str="<%=localeObj.getTranslatedText("Are you sure you want to delete this Event?")%>";
	
	var dialog = $('<p>'+str+'</p>').dialog({
   	 width: 550,
    	 cache: false,
        buttons: {
            "<%=localeObj.getTranslatedText("Yes")%>": function() {
            	window.location.href = 'FormManager?key=EopScheduleAdd&type=DELETE&eventCode='+code;
            },
            "<%=localeObj.getTranslatedText("No")%>":  function() {$('.ui-dialog').hide();},
            
        }
    });
}

function getAgentTeam1(){
	var branch = $('#branch option:selected').val();
	var city = $('#city option:selected').val();
	var ssc = $('#ssc option:selected').val();
	var office = $('#office option:selected').val();
	getAgentTeam(city, ssc, branch,office);
}

function resetSearchForm(){
	var d = new Date();
	$("#<%=EopMaintenance.EVENT_NAME_PARAM %>").val("");
	$("#<%=EopMaintenance.EVENT_TYPE_PARAM %>").val("eop");
	$("#<%=EopMaintenance.ORGANISER_PARAM %>").val("0");
	$("#<%=EopMaintenance.MONTH_PARAM %>").val(d.getMonth()+1);
	$("#<%=EopMaintenance.YEAR_PARAM %>").val(d.getFullYear());
	
	$("#<%=EopMaintenance.BU_PARAM %>").val('0');
	$("#<%=EopMaintenance.DISTRICT_PARAM %>").val('0');
	$("#branch").val('0');
	$("#<%=EopMaintenance.CITY_PARAM %>").val('0');
	$("#<%=EopMaintenance.SSC_PARAM %>").val('0');
	$("#office").val('0');
	$("#<%=EopMaintenance.AGENT_TEAM_PARAM %>").val('0');
}

</script>

   	<div id="maincontainer">	
   					<div class="head">
   						<h2 align="center"><%=localeObj.getTranslatedText("EOP Maintenance")%></h2>
   					</div>  	
   					<div class="content" style="background-color:#ffffff;">	 
						<form action="ContentManager" name="eopForm" method="post" class="PT20">
						<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
                        <div class="headMsg">
					
						</div>
						<table class="formDesign">
                         		 <%
			                     if(request.getAttribute(RequestAttributes.MESSAGE_OBJ)!=null)
			                     {
			                      %>	
                               	  <tr>                         
	                                  <td colspan="2" style="text-align: center">                                    				
	                                    	<span style="color:#ec2028;"><%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.MsgObject)request.getAttribute(RequestAttributes.MESSAGE_OBJ)).getMsg())%></span>
	                                  </td>
                                       <%	request.removeAttribute(RequestAttributes.MESSAGE_OBJ);%>
                                  </tr>
			                    <% } %>             
                                		<tr>                         
	                                    	<td style="width:30"><label ><%=localeObj.getTranslatedText("Event Name")%></label></td>                                    				
	                                        <td>	
	                                         <input name="<%=EopMaintenance.EVENT_NAME_PARAM %>" id="<%=EopMaintenance.EVENT_NAME_PARAM %>" type="text" class="textObj"  value="<%=eventName %>"  />
	                                       	</td>                                     			   
                                      	</tr>
                                      	<tr>                         
                         				<td>                                   				
                          				<label><%=localeObj.getTranslatedText("Event Type")%></label>
                         				</td>
                             			<td>	
                             				<select name="<%=EopMaintenance.EVENT_TYPE_PARAM %>" id="<%=EopMaintenance.EVENT_TYPE_PARAM %>" class="comboObj" >
                            						<option value="eop" <%="eop".equals(eventType)?"selected" : ""%>><%=localeObj.getTranslatedText("EOP")%></option>
                                    				<option value="companyevent"  <%="companyevent".equals(eventType)?"selected" : ""%>><%=localeObj.getTranslatedText("Company Event")%></option>
                         							<option value="networking"  <%="networking".equals(eventType)?"selected" : ""%>><%=localeObj.getTranslatedText("Networking")%></option>	
                                    				<option value="training"   <%="training".equals(eventType)?"selected" : ""%>><%=localeObj.getTranslatedText("Training")%></option>                                       	
                            	 				 </select>
                            	 				 
                            	 			</td>                                     			   
                           			    </tr>
                 		 				<tr  >                         
                         				<td>                                   				
                          				<label ><%=localeObj.getTranslatedText("Month")%></label>
                         				</td>
                             			<td>
                             			
                             				<select name="<%=EopMaintenance.MONTH_PARAM %>" id="<%=EopMaintenance.MONTH_PARAM %>" class="comboObj" style="width:35%;">
                            						<%for(int i=0; i<EopMaintenance.MONTH_VALUE_PAIR.length; i++){%>
															<%keyObjPair = EopMaintenance.MONTH_VALUE_PAIR[i];%>
															<%if(Integer.parseInt(keyObjPair.getKey().toString()) == month){%>
																<option value="<%=keyObjPair.getKey()%>" selected><%=localeObj.getTranslatedText((String)keyObjPair.getObj())%></option>
															<%}else{%>	
																<option value="<%=keyObjPair.getKey()%>"><%=localeObj.getTranslatedText((String)keyObjPair.getObj())%></option>
															<%}%>
														<%}%>                                   	
                            	 			</select>
	                            	 		
                          	 				 <select name="<%=EopMaintenance.YEAR_PARAM %>" id="<%=EopMaintenance.YEAR_PARAM %>" class="comboObj" style="width:29%;">
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
                          				<label ><%=localeObj.getTranslatedText("Organizer")%></label>
                         				</td>
                             			<td>	
                             				<select name="<%=EopMaintenance.ORGANISER_PARAM %>" id="<%=EopMaintenance.ORGANISER_PARAM %>" class="comboObj" >
                             				       <option value="0"  <%=0==organiser ?"selected" : "" %>><%=localeObj.getTranslatedText("Select")%></option> 
                             				       <%if(user.isCho()){ %>
                             				       <option value="1"  <%=1==organiser ?"selected" : "" %>><%=localeObj.getTranslatedText("CHO")%></option> 
                                        					   		
                                        					   <%} %>
	                       						   <option value="2"  <%=2==organiser ?"selected" : "" %>><%=localeObj.getTranslatedText("Bu")%></option> 
	                       						   <option value="3"  <%=3==organiser ?"selected" : "" %>><%=localeObj.getTranslatedText("District")%></option>
	                       						   <option value="7"  <%=7==organiser ?"selected" : "" %>><%=localeObj.getTranslatedText("Branch")%></option>
	                       						   <option value="4"  <%=4==organiser ?"selected" : "" %>><%=localeObj.getTranslatedText("City")%></option>   
	                   						       <option value="5"  <%=5==organiser ?"selected" : "" %>><%=localeObj.getTranslatedText("SSC")%></option>
	                   						        <option value="8"  <%=8==organiser ?"selected" : "" %>><%=localeObj.getTranslatedText("Office")%></option>
	                   						       <%if(!userObj.isSscLevel()){ %>    
	                   						       <option value="6"  <%=6==organiser ?"selected" : "" %>><%=localeObj.getTranslatedText("Agent Team")%></option> 
	                   						       <%} %>                           	
                            	 		   </select>
                            	 	    </td>                                     			   
                           			    </tr>
                           			    	
                                      	<tr>                         
                         				<td>                                   				
                          				<label ><%=localeObj.getTranslatedText("BU")%></label>
                         				</td>
                             			<td>	
                             			<% if(userObj.getUserType().equals("AD")){ %>
                             				<select name="<%=EopMaintenance.BU_PARAM %>" id="<%=EopMaintenance.BU_PARAM %>" class="comboObj"  onchange="getDistrict(this.value,0,'E');">
                            					<option value="0"><%=localeObj.getTranslatedText("Select")%></option> 	  
                            	 		   </select>
                            	 		 <%}else{ %>  
                            	 		   <select name="<%=EopMaintenance.BU_PARAM %>" id="<%=EopMaintenance.BU_PARAM %>" class="comboObj">
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
                             				<select name="<%=EopMaintenance.DISTRICT_PARAM %>" id="<%=EopMaintenance.DISTRICT_PARAM %>" class="comboObj"   onchange="getBranch(this.value,0,'E');">
                            					<option value="0"><%=localeObj.getTranslatedText("ALL")%></option> 	                                 	
                            	 		   </select>
                            	 		 <%}else{ %>
                            	 		 <select name="<%=EopMaintenance.DISTRICT_PARAM %>" id="<%=EopMaintenance.DISTRICT_PARAM %>" class="comboObj">
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
                             				<select name="branch" id="branch" class="comboObj"   onchange="getCity($('#district').val(),0,'E');">
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
                             		<% if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
                             				<select name="<%=EopMaintenance.CITY_PARAM %>" id="<%=EopMaintenance.CITY_PARAM %>" class="comboObj"  onchange="getSSC(this.value,0,'E');">
                            					<option value="0"><%=localeObj.getTranslatedText("ALL")%></option> 	                               	
                            	 		   </select>
                            	 		<%}else{ %>
                            	 			<select name="<%=EopMaintenance.CITY_PARAM %>" id="<%=EopMaintenance.CITY_PARAM %>" class="comboObj">
                            					<option value="<%=userObj.getCityCode() %>" ><%=imoUtilityDate.getCityName(userObj.getCityCode()) %></option>                               	
                            	 		   </select>
                            	 		<%} %>		 
                            	 		</td>                                     			   
                           			    </tr>
                           			    
                           			    <tr  >                         
                         				<td>                                   				
                          				<label ><%=localeObj.getTranslatedText("SSC")%></label>
                         				</td>
                             			<td>	
                             			<% if(userObj.getUserType().equals("AD") || userObj.isCityLevel() ||  userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                             				<select name="<%=EopMaintenance.SSC_PARAM %>" id="<%=EopMaintenance.SSC_PARAM %>" class="comboObj" onchange="getOffice(this.value,0);" >
                             					<option value="0"><%=localeObj.getTranslatedText("ALL")%></option> 
                            	 			</select>
                            	 		<%}else{ %>	 
                            	 			<select name="<%=EopMaintenance.SSC_PARAM %>" id="<%=EopMaintenance.SSC_PARAM %>" class="comboObj">
                             					<option value="<%=userObj.getSscCode() %>" ><%=imoUtilityDate.getSscName(userObj.getSscCode()) %></option>
                            	 			</select>
                            	 		<%} %>
                            	 			</td>                                     			   
                           			    </tr>
                           			       <tr>                         
		                   				<td style="border: none;"  >                                  				
		                    				<label ><%=localeObj.getTranslatedText("Office")%></label>
		                   				</td>
                       					<td>
                       				<% if(userObj.getUserType().equals("AD") ||  userObj.isSscLevel() || userObj.isCityLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>		
                       				<select name="office" id="office"  class="comboObj" onchange="getAgentTeam1();"  >
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
                         				<td>                                   				
                          				<label ><%=localeObj.getTranslatedText("Agent Team")%></label>
                         				</td>
                             			<td>	
                             				<select name="<%=EopMaintenance.AGENT_TEAM_PARAM %>" id="<%=EopMaintenance.AGENT_TEAM_PARAM %>" class="comboObj">
                             						   <option value="0"><%=localeObj.getTranslatedText("Select")%></option>                             	
                            	 			</select>
                            	 	    </td>                                     			   
                           			    </tr>
                           			    
                           			    <tr>                         
                             			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                             				<a href="javascript:document.eopForm.submit();" class="btn1 " ><%=localeObj.getTranslatedText("Search")%></a>
                 		 					<a href="javascript:resetSearchForm();" class="ML10 btn1"><%=localeObj.getTranslatedText("Clear")%></a> 
                            	 		</td>                                     			   
                           			    </tr>
                                       </table>
                                       </form>
                                    <div class="MT5" >
									<table style="width:100%">
											<tr><td align="left"><a href="FormManager?key=EopScheduleAdd&type=NEW"  class="ML10 btn1"><%=localeObj.getTranslatedText("Add Schedule")%></a><a href="FormManager?key=EopUploadCSV&type=NEW" class="ML10 btn1" ><%=localeObj.getTranslatedText("Upload Multiple Schedule in CSV")%></a></td>
											<td align="right" style="font-weight:bold;padding-right:10px"><%if(pager != null){ %><%=localeObj.getTranslatedText("Search Result")%> : <%=noOfEvents %><%} %></td></tr>
									</table>
									</div>
						<%if(pager != null){ %>
                          	<div class="data_table  MT20 MB20" style="width:100%" >
			           		<table class="subFormDesign">
							<thead>
			           		<tr>
			        		<th width="10%"><label><%=localeObj.getTranslatedText("Event Name")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Type")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Date")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("BU")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("District")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Branch")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("City")%></label></th>
			        		
			        		<th><label><%=localeObj.getTranslatedText("SSC")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Office")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Organizer")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Public")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("Speaker")%></label></th>
			        		<th><label><%=localeObj.getTranslatedText("No. of Registered Participants")%></label></th>  
			        		<th><label><%=localeObj.getTranslatedText("No. of Attendees")%></label></th>  
			        		<th><label><%=localeObj.getTranslatedText("Modified By")%></label></th>
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
								<td colspan="13" bordercolor="#FFFFFF">
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
						<td colspan="13" bordercolor="#FFFFFF">
							<div align="center">
								<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
							</div></td>
					</tr>
					<%} %>
					</tbody> 
			
				</table>  
                </div> 
                <%} %>                          
                 <br>                    
				</div>
    </div>

	
     