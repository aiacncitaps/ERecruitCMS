<%--
  - Author(s):          KHYATI
  - Date:               15-MAY-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        
--%>


<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.mapper.DepartmentMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.department.*"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>

<%
	LocaleObject localeObj = (LocaleObject) session
			.getAttribute(SessionAttributes.LOCALE_OBJ);

	Department classObj = new Department();

	boolean modifyFlag = false;
	FormObj formDetail = null;

	if (request.getSession().getAttribute(SessionAttributes.FORM_OBJ) != null) {
		formDetail = (FormObj) request.getSession().getAttribute(
				SessionAttributes.FORM_OBJ);

	}

	if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM)) {
		modifyFlag = true;
	}
	classObj = (Department) request.getSession().getAttribute(
			SessionAttributes.FUNCTION_OBJ);
%>
<div id="maincontainer">
			<div class="head">
				<h2 align="center">
						<%
							if (modifyFlag == true) {
						%>
						<%=localeObj.getTranslatedText("Edit Department")%>
						<%
							} else {
						%>
						<%=localeObj.getTranslatedText("Add Department")%>
						<%
							}
						%>
</h2>
			</div>
<div class="content" style="background-color:#ffffff;">
<form method="post" action="FormManager" name="administrationForm">
<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
	<input type="hidden" id="actionType" name="actionType">
	<input type="hidden" id="deptCode" name="deptCode" value="<%=classObj.getDeptCode()%>">
			
			  <table class="formDesign">  
                    <tr>
			<td colspan="2" style="text-align: center"><span style="color:#ec2028;">
			 
                   <%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) == null){  %> 
					 
                   <%}else{%>  <%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
                        <%}%>       
				     </span></td>                                  	
			
			</tr>
			<tr>
				<td><label><%=localeObj.getTranslatedText("Department Name")%></label>
				</td>

						<%
							if (request.getAttribute(RequestAttributes.ERROR_OBJ) != null) {
						%>
						<td><input name="<%=DepartmentMaintenance.Name%>"  maxlength="250"
							type="text"
							value="<%=(request.getParameter(DepartmentMaintenance.Name))%>"
							class="textObj"
							/>
						</td>
					<%
						} else {
					%>
					<td><input name="<%=DepartmentMaintenance.Name%>"  maxlength="250"
						value="<%=com.quix.aia.cn.imo.utilities.SecurityAPI
						.encodeHTML(classObj.getDeptName())%>"
						type="text" class="textObj"	/>
					</td>
					<%
						}
					%>


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

			<tr>
 				<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
					<%if(modifyFlag == true){ %>
                    	<a href="#"   class="ML10 btn1 " id="modifyButton" onclick="savedata()"><%=localeObj.getTranslatedText("Modify")%></a>
                    <%}else{%>
                     	<a href="#" class="ML10 btn1" " id="insertButton" onclick="savedata()"><%=localeObj.getTranslatedText("Submit")%></a>
                     <%}%>
					<a href="ContentManager?key=Department"class="ML10 btn1" ><%=localeObj.getTranslatedText("Back")%></a>
 				</td>  
			</tr>	
			</table>          
                            	 			
             </form>
		</div>
</div>					
<script language="javascript">
	function savedata() {
		document.administrationForm.actionType.value = "SUBMIT";
		document.administrationForm.submit();
	}
</script>
