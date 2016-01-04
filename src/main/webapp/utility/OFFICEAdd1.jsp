<%--
  - Author(s):          KHYATI
  - Date:               15-MAY-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        
--%>


<%@page import="com.quix.aia.cn.imo.data.office.Office"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.ssc.*"%>
<%@page import="com.quix.aia.cn.imo.mapper.*"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>


<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Office classObj = new  Office();
int bu=0;
int dis=0;
int branch=0;
int city=0;
int ssc=0;


if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){
	
	 bu=Integer.parseInt(request.getParameter("bu"));
	 dis=Integer.parseInt(request.getParameter("district"));
	 city=Integer.parseInt(request.getParameter("city"));
	 branch=Integer.parseInt(request.getParameter("branch"));
	 ssc=Integer.parseInt(request.getParameter("ssc"));
	 
}

boolean modifyFlag = false;
FormObj formDetail = null;
classObj = (Office)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);

if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
	
	
	
}

if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag=true;
	bu=classObj.getBuCode();
	dis=classObj.getDistrictCode();
	branch=classObj.getBranchCode();
	city=classObj.getCityCode();
	ssc=classObj.getSscCode();
}

%>
<script>
$( document ).ready(function() {
	getBU('<%=bu %>','L');
	getDistrict('<%=bu %>','<%=dis %>','L');
	getBranch('<%=dis %>','<%=branch %>','L');
	getCity('<%=branch %>','<%=city %>','L');
	getSSC('<%=city %>','<%=ssc %>','L');
	
	$("#progress").hide();
	$("#progress1").hide();
	
});
</script>
   	<div id="maincontainer">	
   		 
   					<div class="head">
   					<h2 align="center">
   					<%if(modifyFlag == true){ %>
						<%=localeObj.getTranslatedText("Edit OFFICE")%>
						<%} else {%>
						<%=localeObj.getTranslatedText("Add OFFICE")%>
						<%} %>
   					
   					
   					</h2></div>  	
   						<div class="content" style="background-color:#ffffff;"> 
   									  
   									    <form method="post" action="FormManager" name="administrationForm" class="PT20">
   									    <input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
                           				<input type="hidden" id="actionType" name="actionType">
                           				<input type="hidden" id="officecode" name="officecode" value="<%=classObj.getOfficeCode()%>" >
                           				<table class="formDesign">
                           				 
                           				   <tr>
	                       <%
					          if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) == null){
					        %>
					             <td colspan="2">
					                   <div align="center"></div>
					             </td>
					                          <%
					                          	}else{
					                          %>
					            <td colspan="2" style="text-align: center"><span style="color:#ec2028;">
					                    <%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
					            </span>
					            </td>
					                          <%
					                          	}
					                          %>
	                       </tr>
                           				 
                                				
                                		   <tr>                         
                                    				<td   >	                                    				
	                                    				<span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("BU Name")%></label>
                                    				</td>
                                        			<td>	
                                        				<select name="bu" id="bu"  class="comboObj" onchange="getDistrict(this.value,'<%=dis %>','E');" >
                                       						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>	                                        	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                           </tr>		
                                				
                                			 		
                                				
                                			<tr>                         
                                    				<td   >	                                    				
	                                    				<span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("District Name")%></label>
                                    				</td>
                                        			<td>	
                                        				<select name="district" id="district"  class="comboObj" onchange="getBranch(this.value,'<%=branch %>','E');" >
                                       						<option value="0" >All</option>                                           	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                           </tr>	
                                          	
                                          	<tr >                         
              				<td>                                    				
               				<span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Branch")%></label>
              				</td>
                  			<td >	
                  				<select name="branch" id="branch"  class="comboObj"  onchange="getCity(this.value,'<%=city %>','E');"  >
                 						<option value="0" >All</option>                                      	
                 	 				 </select>
                 	 			</td>                                     			   
                			    </tr> 
                                           
                                           <tr>                         
                                    				<td   >	                                    				
	                                    				<span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("City Name")%></label>
                                    				</td>
                                        			<td>	
                                        				<select name="city" id="city"  class="comboObj" onchange="getSSC(this.value,'<%=ssc %>','E');"  >
                                       						<option value="0" >All</option>                                        	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                           </tr>	
                                           
                                           
                                           
                                           <tr>                         
                                    				<td   >	                                    				
	                                    				<span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("SSC Name")%></label>
                                    				</td>
                                        			<td>	
                                        				<select name="ssc" id="ssc"  class="comboObj"  >
                                       						<option value="0" >All</option>                                        	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                           </tr>	
                                				
                                				
                                		   				
                                	<!-- SSC Name -->
                                	 <tr>                         
                    <td   >	                                    				
                         <span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Office Code")%></label>

                    </td>
                    <%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
                    <td>	
                  	<input name="name" id="name"  maxlength="250" type="text" class="textObj"  value="<%=(request.getParameter("name"))%>"  maxlength="250" />
                    </td> 
                    <%} else {%>
                   <td>
                    <input name="name" id="name"  maxlength="250"  value="<%=com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getOfficeName())%>"   type="text" class="textObj" maxlength="250" />
                    </td>   
                   <%} %>
                                       	 			
                                       	 			                                    			   
                   </tr>
                   
                                      
                   
                   
                    <tr>                         
                    <td   >                                                        
                         <span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Office Name")%></label>
                    </td>
                    <%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
                    <td>    
                      <input name="fullname" id="fullname"  maxlength="250" type="text" class="textObj"  value="<%=(request.getParameter("fullname"))%>"  maxlength="250" />
                    </td> 
                    <%} else {%>
                   <td>
                    <input name="fullname"  maxlength="250"  value="<%=com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getOfficeFullName())%>"   type="text" class="textObj" maxlength="250" />
                    </td>   
                   <%} %>
                                                        
                                                                                                           
                   </tr>
                   
                    <tr>
							<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("ORDER")%></label></td>
							<td >
								<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
									<input type="text" id="order" name="order"  maxlength="250" class="textObj"value="<%=(request.getParameter("order"))%>">
								<%}else{ %>
									<input type="text" id="order" name="order"  maxlength="250" class="textObj"value="<%=classObj.getOrderCode()%>">
								<%}%>
							</td>
						</tr>
                   
                   
                   
                       <tr >
						<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
						<%if(modifyFlag == true){ %>
                    	<a href="#"   class="ML10 btn1 " id="modifyButton" onclick="savedata()"><%=localeObj.getTranslatedText("Modify")%></a>
                    <%}else{%>
                   		<a href="#" class="btn1"  id="insertButton" onclick="savedata()"  ><%=localeObj.getTranslatedText("Submit")%></a>
                     <%}%>
							
							<a href="ContentManager?key=OFFICE" class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a>
						</td>
					</tr>
                            		 				
                                       				</table>
                                       				</form>
											
					</div>
				
		</div>
   
 
 
  <script language="javascript">
 
  function savedata()
  {
	  document.administrationForm.actionType.value = "SUBMIT" ;
	  document.administrationForm.submit();
  }
  
  </script>

     