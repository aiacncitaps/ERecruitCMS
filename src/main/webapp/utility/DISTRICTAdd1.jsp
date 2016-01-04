<%--
  - Author(s):          KHYATI
  - Date:               15-MAY-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        
--%>

<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.district.*"%>
<%@page import="com.quix.aia.cn.imo.mapper.*"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>

<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
District classObj = new  District();

boolean modifyFlag = false;
FormObj formDetail = null;
classObj = (District)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);
int bu=0;

if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
	bu=classObj.getBuCode();
}

if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag=true;
}
%>
<script>
$( document ).ready(function() {
	getBU('<%=bu %>','L');
	
	$("#progress").hide();
	$("#progress1").hide();
});

function savedata()
{
	  document.administrationForm.actionType.value = "SUBMIT" ;
	  document.administrationForm.submit();
}
</script>

<div id="maincontainer">
		<div class="head">
			<h2 align="center">
				<%if(modifyFlag == true){ %>
					<%=localeObj.getTranslatedText("Edit District")%>
				<%} else {%>
					<%=localeObj.getTranslatedText("Add District")%>
				<%} %>
			</h2>
		</div>
		<div class="content" style="background-color:#ffffff;">
			<form action="FormManager" name="administrationForm" method="post" class="PT20">
			<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
				<input type="hidden" id="actionType" name="actionType">
                <input type="hidden" id="districtcode" name="districtcode" value="<%=classObj.getDistrictCode()%>" >
                <input type="hidden" id="bucode" name="bucode" value="<%=classObj.getBuCode()%>" >
				<table class="formDesign">
					<tr>
						<td colspan="2" style="text-align: center;color:#ec2028;">
							<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){%>	
                              	<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
                            <%}%>    
						</td>
					</tr>
					<tr>
						<td><label><%=localeObj.getTranslatedText("BU Name")%></label></td>
                        <td>	
						 <%if(request.getParameter(RequestAttributes.ERROR_OBJ) != null){%>
                 				<select name="<%=DistrictMaintenance.BU_PARAM %>" id="<%=DistrictMaintenance.BU_PARAM %>"  class="comboObj"  >
                						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>	                                  	
                	 			 </select>
                  	 	 <%} else {%>  
                  				<select name="<%=DistrictMaintenance.BU_PARAM %>" id="<%=DistrictMaintenance.BU_PARAM %>"  class="comboObj"  >
                 						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>	                                  	
                 	 			</select>
              	 		 <%} %>
                        </td>              
					</tr>
					<tr>
						<td ><label><%=localeObj.getTranslatedText("District Name")%></label></td>
						<td>
						<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
						<input name="<%=DistrictMaintenance.Name%>" id="<%=DistrictMaintenance.Name%>"  maxlength="250" type="text" class="textObj" value="<%=(request.getParameter(DistrictMaintenance.Name))%>"  />
						<%} else {%>
						<input name="<%=DistrictMaintenance.Name%>" id="<%=DistrictMaintenance.Name%>"  maxlength="250" type="text" class="textObj" value="<%=com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getDistrictName())%>"  />
						<%} %>
						</td>
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
                   		<a href="#" class="ML10 btn1 "  id="insertButton" onclick="savedata()"  ><%=localeObj.getTranslatedText("Submit")%></a>
                     <%}%>
							<a href="ContentManager?key=DISTRICT" class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a>
						</td>
					</tr>
				</table>
			</form>
		</div>
</div>
