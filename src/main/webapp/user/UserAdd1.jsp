<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="com.quix.aia.cn.imo.data.channel.Channel"%>
<%@page import="com.quix.aia.cn.imo.utilities.ErrorObject"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.data.department.Department"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.quix.aia.cn.imo.mapper.UserMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<%
int bu=0;
int dis=0;
int branch=0;
String city=0+"";
String ssc=0+"";
int dept=0;
String office=0+"";


String disabled = "";

User userObj = (User)request.getSession().getAttribute("currUserObj");


bu=userObj.getBuCode();
dis=userObj.getDistrict();
city=userObj.getCityCode();
ssc=userObj.getSscCode();
branch=userObj.getBranchCode();
office=userObj.getOfficeCode();

ImoUtilityData imoUtilityDate=new ImoUtilityData();



LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);

String staffLoginId="";
String staffName="";
String emial="";
String phno="";
String extensionNo="";
String channelName="";
String temp[]=null;
String selected="";
boolean modifyFlag = false;
FormObj formDetail = null;
selected="selected=selected";
String choY="",choN="";
ImoUtilityData imoutility=new ImoUtilityData();
ArrayList<Department> alDept=imoutility.getAllDepartment();
ArrayList<Channel> alChannel=imoutility.getActiveChannels(request);


if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){
	
	 bu=Integer.parseInt(request.getParameter("bu"));
	 dis=Integer.parseInt(request.getParameter("district"));
	 city=(request.getParameter("city"));
	 ssc=(request.getParameter("ssc"));
	 office=(request.getParameter("office"));
	 branch=Integer.parseInt(request.getParameter("branch"));
	 staffLoginId=SecurityAPI.encodeHTML(request.getParameter("staffLoginId"));
	 staffName=SecurityAPI.encodeHTML(request.getParameter("staffName"));
	 emial=SecurityAPI.encodeHTML(request.getParameter("email"));
	 phno=SecurityAPI.encodeHTML(request.getParameter("phno"));
	 extensionNo=SecurityAPI.encodeHTML(request.getParameter("extNo"));
	 dept=Integer.parseInt(request.getParameter("dept"));
	 
	/* if( request.getParameterValues("channel")!=null){
		for (int i = 0; i < request.getParameterValues("channel").length; i++) {
			 channelName=channelName+request.getParameterValues("channel")[i]+"#";
			 
		}
		 temp=channelName.split("#");
	} */
	 
	if(request.getParameter("cho").equalsIgnoreCase("Y")){
		choY="selected=selected";
	}else{
		choN="selected=selected";
	}
	 
}
if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
}
User userModify=new User();
userModify = (User)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);
if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag = true;
	bu=userModify.getBuCode();
	 dis=userModify.getDistrict();
	 branch=userModify.getBranchCode();
	 city=userModify.getCityCode() ;
	 ssc=userModify.getSscCode();
	 office=userModify.getOfficeCode();
	 
	 
	 staffLoginId=SecurityAPI.encodeHTML(userModify.getStaffLoginId());
	 staffName=SecurityAPI.encodeHTML(userModify.getStaffName());
	 emial=SecurityAPI.encodeHTML(userModify.getEmail());
	 phno=SecurityAPI.encodeHTML(userModify.getContactNo());
	 extensionNo=SecurityAPI.encodeHTML(userModify.getExtensionNo());
	 dept=userModify.getDepartment();
	 
	 if(userModify.isCho()){
			choY="selected=selected";
		}else{
			choN="selected=selected";
		}
	
	 temp=null;
	//temp=userModify.getChannelCode().split("\\|");
	 
}
%>

<style>
@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) {.class_limit{width:260px;}}
@media screen and (min-width:0\0) and (min-resolution: +72dpi) {.class_limit{width:260px;}}
</style>
<script language="javascript" type="text/javascript" >
        window.history.forward();
        
        function deleteClass() {
        	confirm("Are you sure you want to delete this class?");
    		
    }
   function modifyForm(){
        	document.announcementForm.submit();
        	
   }
</script>

<script>

$(function() {
	$( ".datepicker" ).datepicker({
	showOn: "button",
	buttonImage: "images/calendar_icon.gif",
	buttonImageOnly: true,
	dateFormat:"dd/mm/yy",
	changeMonth: true, 
	changeYear: true
	});
 });
$(function() {
    $(window).on('resize', function resize()  {
        $(window).off('resize', resize);
        setTimeout(function () {
            var content = $('#content');
            var top = (window.innerHeight - content.height()) / 2;
            content.css('top', Math.max(0, top) + 'px');
            $(window).on('resize', resize);
        }, 50);
    }).resize();
});
</script>

<style>
     .ui-widget-header,.ui-state-default, ui-button{
        background:blue;
        border: 1px solid blue;
        color: #FFFFFF;
        font-weight: bold;
     }
     .invalid { color: red; }
     textarea {
        display: inline-block;
        width: 100%;
        margin-bottom: 10px;
     }
 </style>

		<div id="maincontainer">
					<div class="head">
					<h2 align="center"><%=modifyFlag==true?localeObj.getTranslatedText("Edit User Management"):localeObj.getTranslatedText("Add User Management")%></h2></div>  	
						
					</div>
					<div class="content" style="background-color:#ffffff;">
					
<form  method="post"  action="FormManager?key=UserAdd" name="administrationForm"  >
<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
<input type="hidden" id="actionType" name="actionType">

<input type="hidden" name="channel" id="channel" value=""> 
<%if(modifyFlag){ %>
<input type="hidden" id="userNo" name="userNo" value="<%=SecurityAPI.encodeHTML(userModify.getUser_no()+"") %>" >
<% }%>
<table class="formDesign">
					<tr>
						<td colspan="2" style="text-align: center">
						<span style="color:#ec2028;">
                      <% if(request.getAttribute(RequestAttributes.ERROR_OBJ)	== null){%>
                     <%}else{%>&nbsp;
                    <%= 	localeObj.getTranslatedText(((ErrorObject)request.getAttribute(RequestAttributes.ERROR_OBJ)).getErrorMsg())	  %>			
                 	<%}%>	                            					
                     </span></td>
					</tr>
                                 
                                				<%-- <tr>                          
                                    				<td>
	                                    				<label style="vertical-align: top;" ><%=localeObj.getTranslatedText("Channel")%></label>
                                    				</td>
                                        			<td>
                                        			<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null  ||  modifyFlag==true){
                                        				
                                        			%>
                                        			<select name="channel" id="channel" class="comboObj"  multiple="multiple" >
                                       						
                                       						<%for(Channel channel:alChannel){%>
                                       						<option value="<%=channel.getChannelCode() %>" <% if(temp!=null){ for(int i = 0; i < temp.length; i++){
                                       							if(temp[i].equals(channel.getChannelCode()+"")){ %>
                                       						<%=selected %>  <%} }}%>  ><%=channel.getChannelName() %></option>     
                                       						<%} %>  
		                                       	
                                       	 			</select>
                                        			<%}else {%>
                                        			<select name="channel" id="channel" class="comboObj"  multiple="multiple" >
                                       						<%for(Channel channel:alChannel){%>
                                       						<option value="<%=channel.getChannelCode() %>" ><%=channel.getChannelName() %></option>     
                                       						<%} %>                                 						
                                       						
                                       						                                       	
                                       	 			</select>
                                        			
                                        			<% }%>
                                        			
                                       	 			</td>  
                                       	 			                         			   
                                      			    </tr> --%>
                                      			    
                                      			    
                                      			    <tr>                         
                                    				<td>	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("CHO")%></label>
                                    				</td>
                                        			<td style="border: none; "  >	
                                        			
                                       	 			<select name="cho" id="cho" class="comboObj"  >
                                       						<option value="N" <%=choN %> ><%=localeObj.getTranslatedText("No") %></option>
                                       						<% if(userObj.getUserType().equals("AD")){ %>
                                       						<option value="Y" <%=choY %> ><%=localeObj.getTranslatedText("Yes") %></option>
                                       	 			<%} %>
                                       	 			</select>
                                       	 			

                                       	 			</td>   
                                       	 			                                   			   
                                      			    </tr>
                                      			    
                                      			    
                                      			    
                            		 				<tr>                         
                                    				<td>	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("BU")%></label>
                                    				</td>
                                        			<td style="border: none; "  >	
                                        			
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
	                                    				<label ><%=localeObj.getTranslatedText("District")%> </label>
                                    				</td>
                                        			<td>
                                        			<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel()){ %>
                                        				<select name="district" id="district" class="comboObj" onchange="getBranch(this.value,'<%=branch %>','E');"  >
                                       						<option value="0" ><%=localeObj.getTranslatedText("All") %></option>
                                       						                          	
                                       	 			   </select>
                                       	 			<%}else { %>
                                       	 			
                                       	 			<select name="district" id="district" class="comboObj"   >
                                       						<option value="<%=userObj.getDistrict() %>" ><%= imoUtilityDate.getDistCode(userObj.getDistrict()) %></option>
                                       						                          	
                                       	 			   </select>
                                       	 			<%} %>
                                       	 			</td>   
                                       	 			                                    			   
                                      			    </tr>
                                      			    
                                      			    
                                      			    
                                      			     <tr>                         
                                    				<td>	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Branch")%> </label>
                                    				</td>
                                        			<td>
                                        			<% if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
                                        				<select name="branch" id="branch" class="comboObj" onchange="getCity(this.value,'<%=city %>','E');"  >
                                       						<option value="0" ><%=localeObj.getTranslatedText("All") %></option>
                                       						                          	
                                       	 			   </select>
                                       	 			<%}else { %>
                                       	 		
                                       	 		<select name="branch" id="branch" class="comboObj"   >
                                       						<option value="<%=userObj.getBranchCode() %>" ><%= imoUtilityDate.getBranchCode(userObj.getBranchCode()) %></option>
                                       						                          	
                                       	 			   </select> 
                                       	 			<%} %>
                                       	 			</td>   
                                       	 			                                    			   
                                      			    </tr>
                            		 				
                            		 				<tr >                         
                                    				<td>                                    				
	                                    				<label ><%=localeObj.getTranslatedText("City")%></label>
                                    				</td>
                                        			<td >	
                                        		<% if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
														<select name="city" id="city" class="comboObj"    onchange="getSSC(this.value,'<%=ssc %>');"  >
                                       						<option value="0" ><%=localeObj.getTranslatedText("All") %></option>	                                       	
                                       	 			   </select>
                                       	 			<%}else { %>
                                       	 			
                                       	 			<select name="city" id="city" class="comboObj" >
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
                                        		<% if(userObj.getUserType().equals("AD") || userObj.isCityLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
                                        				<select name="ssc" id="ssc" class="comboObj"   onchange="getOffice(this.value,'<%=office %>');" >
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
                                       						<option value="<%=userObj.getSscCode() %>" ><%=imoUtilityDate.getSscName(userObj.getSscCode()) %></option>
                                       						                          	
                                       	 			     </select>
                                       	 			<%} %>
	                           	 		    </td>                                     			   
                          			    </tr>
                          			    

                            		 				<tr  >                         
                                    			    <td>                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Department")%></label>
                                    				</td>
                                        			<td>
                                        		
                                        				<select name="dept" id="dept" class="comboObj" >
                                       						<option value="0" ><%=localeObj.getTranslatedText("All") %></option>
                                       						<%for(Department department : alDept){%>
                                       						<option <%= dept==department.getDeptCode()?"selected=selected":"" %> value="<%=SecurityAPI.encodeHTML(department.getDeptCode()+"") %>" ><%=SecurityAPI.encodeHTML(department.getDeptName()) %></option>
                                       						<%}%>
                  	
                                       	 			     </select>
                                       	 			</td> 
                                       	 			                                  			   
                                      			    </tr>                         		 				
                            		 				
                            		 				<tr>                         
                                    				<td >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Staff Login ID")%></label>
                                    				</td>
                                        			<td >	
                                        			<%if(modifyFlag){ %>
                                        				<input name="staffLoginId1" id="staffLoginId1"  type="text" class="textObj"   maxlength="20"  value="<%=staffLoginId %>" disabled/>
                                        				<input name="staffLoginId" id="staffLoginId"  type="hidden" class="textObj"  maxlength="20"   value="<%=staffLoginId %>" />
                                        			<%}else{ %>
                                        				<input name="staffLoginId" id="staffLoginId"  type="text" class="textObj"   maxlength="20"  value="<%=staffLoginId %>" />
                                        			<%} %>
                                       	 			</td> 
                                       	 			                                   			   
                                      			    </tr>
                            		 				
                            		 				
                            		 				
                            		 				<tr  >                         
                                    				<td  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Staff Name")%></label>
                                    				</td>
                                        			<td  >
                                        			 <input name="staffName" id="staffName" type="text" class="textObj"    maxlength="20" value="<%=staffName %>"  />
                                       	 			</td> 
                                       	 			                                     			   
                                      			    </tr>
                                      			    
                                      			    <tr>                         
                                    				<td  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Email Address")%></label>
                                    				</td>
                                        			<td  >	
                                        			
                                        			 <input name="email" id="email" estCand"" type="text" class="textObj"    maxlength="100"  value="<%=emial %>" />	
                                       	 			</td>
                                       	 			<td></td>                                       			   
                                      			    </tr>
                                      			    
                                      			    <tr>                         
                                    				<td>                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Contact Number")%></label>
                                    				</td>
                                        			<td>	
                                        		
                                        			<input name="phno" id="phno" estCand"" type="text" class="textObj"   value="<%=phno %>" maxlength="50"  />		
                                       	 			</td>  
                                       	 			<td></td>                                     			   
                                      			    </tr>
                                      			    
                                      			    <tr>                         
                                    				<td>                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Extension Number")%></label>
                                    				</td>
                                        			<td>	
                                        			
                                        			<input name="extNo" id="extNo" estCand"" type="text" class="textObj"      value="<%=extensionNo %>" maxlength="50" />
                                        		
                                       	 			</td>  
                                       	 			                                   			   
                                      			    </tr>
                                      			   
                            		 <tr >
									<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px; padding-bottom: 20px;">
										<%if(modifyFlag==true){%>
                            				<a href="#"  class="MB10 btn1"  id="modifyButton" name="modifyButton"  style="margin-left: -32%; "><%=localeObj.getTranslatedText("Modify")%></a>
                            				<%}else{ %>
                            				<a href="#"  class="MB10 btn1"  id="insertButton" name="insertButton"  style="margin-left: -32%; "><%=localeObj.getTranslatedText("Submit")%></a>
                		 					<%} %>
                		 					<a href="ContentManager?key=<%=(String)((com.quix.aia.cn.imo.utilities.PageObj)request.getSession().getAttribute(com.quix.aia.cn.imo.constants.SessionAttributes.PAGE_OBJ)).getKey()%>" class="MB10 btn1"><%=localeObj.getTranslatedText("Back")%></a> 
									</td>
								</tr>	
                               </table>
                                     				
 							</form>
                        </div>
					

 
 <script language="javascript">
   $(document).ready(function(){
	   
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
	
	   
	   $("#cho").change(function() {
          if( $("#cho").val()=='Y'){
        	 
        	  $("#bu")[0].selectedIndex=0;
        	  $("#district")[0].selectedIndex=0;
        	  $("#branch")[0].selectedIndex=0;
        	  $("#city")[0].selectedIndex=0;
        	  $("#ssc")[0].selectedIndex=0;
        	  $("#office")[0].selectedIndex=0;
        	  
        	  
        	  $("#bu").prop("disabled", true);
        	  $("#district").prop("disabled", true);
        	  $("#branch").prop("disabled", true);
        	  $("#city").prop("disabled", true);
        	  $("#ssc").prop("disabled", true);
        	  $("#office").prop("disabled", true);
        	
          }else{
        	  $("#bu").prop("disabled", false);
        	  $("#district").prop("disabled", false);
        	  $("#branch").prop("disabled", false);
        	  $("#city").prop("disabled", false);
        	  $("#ssc").prop("disabled", false);
        	  $("#office").prop("disabled", false);
          }
       });
	   
	   
	   
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
				 
					getCity('<%=branch %>','<%=city %>','L');
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
					
					getSSC('<%=city %>','<%=ssc %>');
				<% }%>
				
	<%if(userObj.getUserType().equals("AD") || userObj.isSscLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isCityLevel() ){%>
				
				getOffice('<%=ssc %>','<%=office %>');
			<% }%>


		$("#progress").hide();
		$("#progress1").hide();
	   $('#insertButton').click(function(){
		 
		  
	        	  $("#bu").prop("disabled", false);
	        	  $("#district").prop("disabled", false);
	        	  $("#branch").prop("disabled", false);
	        	  $("#city").prop("disabled", false);
	        	  $("#ssc").prop("disabled", false);
	        	  $("#office").prop("disabled", false);
	       
		   document.administrationForm.actionType.value = "SUBMIT" ;
		   document.administrationForm.submit() ;
	   		 
		});
	   
	   $('#modifyButton').click(function(){
		   
		   $("#bu").prop("disabled", false);
     	  $("#district").prop("disabled", false);
     	  $("#branch").prop("disabled", false);
     	  $("#city").prop("disabled", false);
     	  $("#ssc").prop("disabled", false);
     	  $("#office").prop("disabled", false);
		   document.administrationForm.actionType.value = "MODIFY" ;
		   document.administrationForm.submit() ;
	   		 
		});
	    
   });
   
</script>
	   
   