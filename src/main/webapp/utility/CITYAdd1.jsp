<%--
  - Author(s):          KHYATI
  - Date:               15-MAY-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        
--%>



<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.city.*"%>
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
City classObj = new  City();
int bu=0;
int dis=0;
int branch=0;
boolean modifyFlag = false;
FormObj formDetail = null;
classObj = (City)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);



if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);

}

if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){
	
	 bu=Integer.parseInt(request.getParameter("bu"));
	 dis=Integer.parseInt(request.getParameter("district"));
	branch=Integer.parseInt(request.getParameter("branch")); 
}

if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag=true;
	dis=classObj.getDistrict();
	bu=classObj.getBuCode();
	branch=Integer.parseInt(classObj.getCo().trim());
	
}

%>

<script>
$( document ).ready(function() {
	getBU('<%=bu %>');
	getDistrict('<%=bu %>','<%=dis %>');
	getBranch('<%=dis %>','<%=branch %>','L');
	
	
	$("#progress").hide();
	$("#progress1").hide();
	
});
</script>

<div id="maincontainer">
			<div class="head">
				<h2 align="center">
				 <%=localeObj.getTranslatedText("Add City")%>
   					</h2>
			</div>
<div class="content" style="background-color:#ffffff;">
<form method="post" action="FormManager" name="administrationForm">
<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
   	<input type="hidden" id="actionType" name="actionType">								    
    <input type="hidden" id="co" name="co" value="<%=classObj.getCo()%>" >
    <input type="hidden" id="citycode" name="citycode" value="<%=classObj.getCityName()%>" >
                           				
                           				  <table class="formDesign">  
                    <tr>
			<td colspan="2" style="text-align: center"><span style="color:#ec2028;">
			 
                   <%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) == null){  %> 
					 
                   <%}else{%>  <%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
                        <%}%>       
				     </span></td>                                  	
			
			</tr>
                                				
                                		   <tr>                         
                                    				<td>	                                    				
	                                    				<label><%=localeObj.getTranslatedText("BU Name")%></label>
                                    				</td>
                                        			<td>	
                                        				<select name="bu" id="bu"  class="comboObj"  onchange="getDistrict(this.value,'<%=dis %>');" >
                                       						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>                                       	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                           </tr>		
                                			
                                			<tr>                         
                                    				<td>	                                    				
	                                    				<label><%=localeObj.getTranslatedText("District Name")%></label>
                                    				</td>
                                        				
                                        			<%if(request.getParameter(RequestAttributes.ERROR_OBJ) != null){%>
                                        			<td>
                                        				<select name="district" id="district"  class="comboObj" onchange="getBranch(this.value,'<%=branch %>','E');" >
                                       						<option value="0" >All</option>                                            	
                                       	 				 </select>
                                       	 				 
                                       	 			</td> 
                                       	 			 <%} else {%>    
                                       	 			 <td>
                                        				<select name="district" id="district"  class="comboObj" onchange="getBranch(this.value,'<%=branch %>','E');"  >
                                       						<option value="0" >All</option>                                            	
                                       	 				 </select>
                                       	 				 
                                       	 			</td> 
                                       	 			 <%} %>                                   			   
                                           </tr>
                                           
                                           
                                           
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
                                           
                                           
                                           
                                            
                                           
                      <!-- -city name -->		
                                					
                     <tr>                         
                    <td style="border: none; text-align:left;"  >	                                    				
	                	<label><%=localeObj.getTranslatedText("City Code")%></label>
                    </td>
                    <%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
                    <td>	
                  	<input name="<%=CityMaintenance.Name%>" id="<%=CityMaintenance.Name%>"  maxlength="2" type="text" class="textObj"  value="<%=(request.getParameter(CityMaintenance.Name))%>"  />
                    </td> 
                    <%} else {%>
                   <td>
                    <input name="<%=CityMaintenance.Name%>"  maxlength="2"  value="<%=com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getCityName())%>" readonly="readonly"  type="text" class="textObj" />
                    </td>   
                   <%} %>
                                       	 			
                                       	 			                                    			   
                   </tr>
                      <!-- -city name -->	
                     
                       
                   
                   <tr>                         
                    <td style="border: none; text-align:left;"  >                                                        
                        <label><%=localeObj.getTranslatedText("City Name")%></label>
                    </td>
                    <%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
                    <td>    
                      <input name="fullname" id="fullname"  maxlength="30" type="text" class="textObj"  value="<%=(request.getParameter("fullname"))%>"  />
                    </td> 
                    <%} else {%>
                   <td>
                    <input name="fullname" id="fullname"  maxlength="30"  value="<%=com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getCityFullName())%>"   type="text" class="textObj" />
                    </td>   
                   <%} %>
                                                        
   						
                            		 				
                                      			   
       			    <tr>      
       			    
       			     <td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
       			     <%if(modifyFlag == true){ %>
                    	<a href="#"   class="ML10 btn1 " id="modifyButton" name="modifyButton" onclick="savedata()"><%=localeObj.getTranslatedText("Modify")%></a>
                    <%}else{%>
                   		<a href="#"   class="ML10 btn1" id="insertButton" name="insertButton" onclick="savedata()" ><%=localeObj.getTranslatedText("Submit")%></a>
                     <%}%>
					<a href="ContentManager?key=CITY"  class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a>
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