<%--
  - Author(s):          KHYATI
  - Date:               15-MAY-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        
--%>



<%@page import="com.quix.aia.cn.imo.data.district.District"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.quix.aia.cn.imo.data.branch.Branch"%>
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

String cty="";
int dis=0;
boolean modifyFlag = false;
FormObj formDetail = null;


/* if(request.getParameter(RequestAttributes.ERROR_OBJ) != null){
	
	 bu=Integer.parseInt(request.getParameter("bu"));
	 dis=Integer.parseInt(request.getParameter("district"));
	
	 
} */

if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
	/* modifyFlag=true;
	dis=branchObj.getDistCode();
	bu=branchObj.getBuCode(); */
}
if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag=true;
}


if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){
	
	if(request.getParameter("district")!=null){
		dis=Integer.parseInt(request.getParameter("district"));
	}
	
	if(request.getParameter("city")!=null){
		cty=request.getParameter("city");
	}
	
	
}
CityMaintenance citymain=new CityMaintenance();
ArrayList<City> listcity=citymain.getcityDropDown();
ArrayList<District> listDist=citymain.getdistDropDown();




%>

<script>
$( document ).ready(function() {

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
   
    <%if(modifyFlag) {%>
    	 <%-- <input type="hidden" id="createdBy" name="createdBy" value="<%=branchObj.getCreatedBy()%>" >
   		 <input type="hidden" id="createdDate" name="createdDate" value="<%=branchObj.getCreationDate()%>" > --%>
    <% }%>
                           				
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
	                                    				<label><%=localeObj.getTranslatedText("District Name")%></label>
                                    				</td>
                                        				
                                        			<%if(request.getParameter(RequestAttributes.ERROR_OBJ) != null){%>
                                        			<td>
                                        				<select name="district" id="district"  class="comboObj"  >
                                        				 <option value="0" ><%=localeObj.getTranslatedText("Select")%></option>
                                       						<%if(listDist!=null){ 
                  											for(District dist:listDist){
                  									%>
                  											
						                  					<option <%= dis==dist.getDistrictCode()?"selected=selected":"" %> value="<%=dist.getDistrictCode() %>" selected="selected" ><%=dist.getDistrictName() %> </option>
						                  			<%}}else{ %>
						                  					<option value="0" ><%=localeObj.getTranslatedText("All")%></option>
						                  			<%} %>                                      	
                                       	 				 </select>
                                       	 				 
                                       	 			</td> 
                                       	 			 <%} else {%>    
                                       	 			 <td>
                                        				<select name="district" id="district"  class="comboObj"  >
                                        				 <option value="0" ><%=localeObj.getTranslatedText("Select")%></option>
                                       						<%if(listDist!=null){ 
                  											for(District dist:listDist){
                  									%>
						                  					<option  <%= dis==dist.getDistrictCode()?"selected=selected":"" %>  value="<%=dist.getDistrictCode() %>" ><%=dist.getDistrictName() %> </option>
						                  			
						                  			<%}}else{ %>
						                  					<option value="0" ><%=localeObj.getTranslatedText("All")%></option>
						                  			<%} %>                                      	
                                       	 				 </select>

                                       	 				 
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
                  		<select class="comboObj" id="city" name="city" >
                  		 <option value="0" ><%=localeObj.getTranslatedText("Select")%></option>
                  			<%if(listcity!=null){ 
                  				for(City city:listcity){
                  			%>
                  					<option <%= cty==city.getCityName()?"selected=selected":"" %> value="<%=city.getCityName() %>" ><%=city.getCityFullName() %> </option>
                  			
                  			<%}}else{ %>
                  					<option value="0" ><%=localeObj.getTranslatedText("All")%></option>
                  			<%} %>
                  				
                  		</select>
                  	
                  	
                  	
                    </td> 
                    <%} else {%>
                   <td>
                   <select class="comboObj" id="city" name="city">
                  	 <option value="0" ><%=localeObj.getTranslatedText("Select")%></option>
                  			<%if(listcity!=null){ 
                  				for(City city:listcity){
                  			%>
                  					<option  value="<%=city.getCityName() %>" ><%=city.getCityFullName() %> </option>
                  				
                  			<%}} %>
                  					
                  			
                  				
                  		</select>
                  		
                  		
                  		
                    </td>   
                   <%} %>
                           
                           
                                       	 			                                    			   
                   </tr>
                     
                                      			   
       			    <tr>      
       			    
       			     <td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
       			     <%if(modifyFlag == true){ %>
                    	<a href="#"   class="ML10 btn1 " id="modifyButton" name="modifyButton" onclick="savedata()"><%=localeObj.getTranslatedText("Modify")%></a>
                    <%}else{%>
                   		<a href="#"   class="ML10 btn1" id="insertButton" name="insertButton" onclick="savedata()" ><%=localeObj.getTranslatedText("Submit")%></a>
                     <%}%>
					<a href="ContentManager?key=CITY_DIST"  class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a>
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