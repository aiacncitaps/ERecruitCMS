<%--
  - Author(s):          Hemraj
  - Date:               10-July-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        
--%>
<%@page import="com.quix.aia.cn.imo.data.goalsetting.GoalConfig"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/GoalConfig.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
GoalConfig classObj = new  GoalConfig();

FormObj formDetail = null;

if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
}

classObj = (GoalConfig)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);
%>

	<div id="maincontainer">
			<div class="head">
				<h2 align="center">
					<%=localeObj.getTranslatedText("Add Goal Configuration")%>
				</h2>
			</div>
			<div class="content" style="background-color:#ffffff;">
				<form action="FormManager" name="administrationForm" method="post" class="PT20">
				<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
					<input type="hidden" id="actionType" name="actionType">
					
					<table class="formDesign">
						<tr>
							<td colspan="2" style="text-align: center;color:#ec2028;">
							<%
					          if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
					        %>
								<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
							<%}%>
							</td>
						</tr>
	
						<tr>
							<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Potential Candidate")%></label></td>
							<td >
								<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
									<input type="text" id="potentialCandidate" name="potentialCandidate" class="textObj" maxlength="3" value="<%=(request.getParameter("potentialCandidate"))%>">
								<%}else{ %>
									<input type="text" id="potentialCandidate" name="potentialCandidate" class="textObj" maxlength="3" value="<%=(com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getPotentialCandidate()+""))%>">
								<%}%>
							</td>
						</tr>
						<tr>
							<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("EOP Registration")%></label></td>
							<td >
								<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
									<input type="text" id="eopRegistration" name="eopRegistration" class="textObj" maxlength="3" value="<%=(request.getParameter("eopRegistration"))%>">
								<%}else{ %>
									<input type="text" id="eopRegistration" name="eopRegistration" class="textObj" maxlength="3" value="<%=(com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getEopRegistration()+""))%>">
								<%}%>
							</td>
						</tr>
						<tr>
							<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("First Interview")%></label></td>
							<td >
								<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
									<input type="text" id="firstInterview" name="firstInterview" class="textObj" maxlength="3" value="<%=(request.getParameter("firstInterview"))%>">
								<%}else{ %>
									<input type="text" id="firstInterview" name="firstInterview" class="textObj" maxlength="3" value="<%=(com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getFirstInterview()+""))%>">
								<%}%>
							</td>
						</tr>
						<tr>
							<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("EOP Attendance")%></label></td>
							<td >
								<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
									<input type="text" id="eopAttendance" name="eopAttendance" class="textObj" maxlength="3" value="<%=(request.getParameter("eopAttendance"))%>">
								<%}else{ %>
									<input type="text" id="eopAttendance" name="eopAttendance" class="textObj" maxlength="3" value="<%=(com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getEopAttendance()+""))%>">
								<%}%>
							</td>
						</tr>
						<tr>
							<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("CC Assessment")%></label></td>
							<td >
								<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
									<input type="text" id="ccAssessment" name="ccAssessment" class="textObj" maxlength="3" value="<%=(request.getParameter("ccAssessment"))%>">
								<%}else{ %>
									<input type="text" id="ccAssessment" name="ccAssessment" class="textObj" maxlength="3" value="<%=(com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getCcAssessment()+""))%>">
								<%}%>
							</td>
						</tr>
						<tr>
							<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Company Interview")%></label></td>
							<td >
								<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
									<input type="text" id="companyInterview" name="companyInterview" class="textObj" maxlength="3" value="<%=(request.getParameter("companyInterview"))%>">
								<%}else{ %>
									<input type="text" id="companyInterview" name="companyInterview" class="textObj" maxlength="3" value="<%=(com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getCompanyInterview()+""))%>">
								<%}%>
							</td>
						</tr>
						<tr>
							<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Pass ALE")%></label></td>
							<td >
								<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
									<input type="text" id="passALE" name="passALE" class="textObj" maxlength="3" value="<%=(request.getParameter("passALE"))%>">
								<%}else{ %>
									<input type="text" id="passALE" name="passALE" class="textObj" maxlength="3" value="<%=(com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getPassALE()+""))%>">
								<%}%>
							</td>
						</tr>
						<tr>
							<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Attended Training")%></label></td>
							<td >
								<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
									<input type="text" id="attendedTraining" name="attendedTraining" class="textObj" maxlength="3" value="<%=(request.getParameter("attendedTraining"))%>">
								<%}else{ %>
									<input type="text" id="attendedTraining" name="attendedTraining" class="textObj" maxlength="3" value="<%=(com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getAttendedTraining()+""))%>">
								<%}%>
							</td>
						</tr>
						<tr>
							<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Sign Contract")%></label></td>
							<td >
								<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>
									<input type="text" id="signContract" name="signContract" class="textObj" maxlength="3" value="<%=(request.getParameter("signContract"))%>">
								<%}else{ %>
									<input type="text" id="signContract" name="signContract" class="textObj" maxlength="3" value="<%=(com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getSignContract()+""))%>">
								<%}%>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
								<a href="#" class="btn1"  id="insertButton" onclick="savedata()"><%=localeObj.getTranslatedText("Submit")%></a>
								<a href="ContentManager?key=GoalSetting" class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a>
							</td>
						</tr>
					</table>
				</form>
			</div>
	</div>

<script type="text/javascript">
  function savedata()
  {
	  document.administrationForm.actionType.value = "SUBMIT" ;
	  document.administrationForm.submit();
	  //window.location.href="/IMOCN/FormManager?key=GoalConfig&type=NEW"
  }

  $(document).ready(function() {
	  $('#ajaxLoader').find(".lightbox").show();
	  GoalConfig.getConfigData({
			callback : function(str) {
				var goalCon = str.goalConfigation;

				$("#potentialCandidate").val(goalCon.potentialCandidate);
				$("#eopRegistration").val(goalCon.eopRegistration);
				$("#firstInterview").val(goalCon.firstInterview);
				$("#eopAttendance").val(goalCon.eopAttendance);
				$("#ccAssessment").val(goalCon.ccAssessment);
				$("#companyInterview").val(goalCon.companyInterview);
				$("#passALE").val(goalCon.passALE);
				$("#attendedTraining").val(goalCon.attendedTraining);
				$("#signContract").val(goalCon.signContract);
				$('#ajaxLoader').find(".lightbox").hide();
			}
		});
	});
</script>
