<%--
  - Author(s):          Nibedita
  - Date:               30-Sept-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        question set page
--%>
<%@page import="java.io.File"%>
<%@page import="com.quix.aia.cn.imo.data.properties.ConfigurationProperties"%>
<%@page import="com.quix.aia.cn.imo.mapper.PropertiesMaintenance"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.utilities.KeyObjPair"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="java.util.*"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/UserManagement.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>

<%
try{

//itms-services://?action=download-manifest&url=https://pba.prudential.com.my/LMS3/plist/PRUproduct.plist

User userObj = new User();
userObj = (User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ);
boolean modifyFlag = false;

FormObj formDetail = null;
if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
}
/* if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag = true;
} */

LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Map<String,String> configurationMap = new PropertiesMaintenance().fetchConfigurationProperties();
String appUrl = configurationMap.get("APP_URL");
String iosLeadingAppURL = configurationMap.get(ConfigurationProperties.IOS_LEADING_APP_URL);
String eRecruitmentAppURL = configurationMap.get(ConfigurationProperties.E_RECRUITMENT_APP_URL);
String eopScanAppURL = configurationMap.get(ConfigurationProperties.EOP_SCAN_APP_URL);

iosLeadingAppURL += appUrl;
eRecruitmentAppURL = iosLeadingAppURL + eRecruitmentAppURL;
eopScanAppURL = iosLeadingAppURL + eopScanAppURL;
%>

<div id="maincontainer">
		<div class="head">
			<h2 align="center">
			<%=localeObj.getTranslatedText("Download PLIST file of IOS App")%>
			</h2>
		</div>
		<div class="content" style="background-color:#ffffff;">
			<form action="FormManager" name="administrationForm" method="post" class="PT20">
			<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
				<table class="formDesign">
					<tr>
						<td colspan="2" style="text-align: center;color:#ec2028;">
							<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){%>	
                              	<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
                            <%}%>    
						</td>
					</tr>
					<tr>
						<td><label><%=localeObj.getTranslatedText("App Type")%></label></td>
						<td>
							<select name="appType" id="appType" class="comboObj" >
								<option value="0" ><%=localeObj.getTranslatedText("Select")%></option>
        						<option value="<%=ConfigurationProperties.E_RECRUITMENT_APP_URL%>"><%=localeObj.getTranslatedText("E-Recruitment App")%></option>
           						<option value="<%=ConfigurationProperties.E_RECRUITMENT_APP_URL%>"  ><%=localeObj.getTranslatedText("EOP Scan App")%></option>
           	 				</select>
						</td>
					</tr>
<!-- 					<tr > -->
<!-- 						<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px"> -->
<%--                     		<a href="<%=eRecruitmentAppURL%>" class="ML10 btn1 " id="saveButton"><%=localeObj.getTranslatedText("Download")%></a> --%>
<%--                     		<a href="#"   class="ML10 btn1 " id="cancelButton"><%=localeObj.getTranslatedText("Cancel")%></a> --%>
<!-- 						</td> -->
<!-- 					</tr> -->
				</table>
			</form>
		</div>
</div>

	<script language="javascript">
	
	
	$('#appType').change(function(){
	    	var appType=$('#appType').val();
	    	
	    	var logedInId = "<%=userObj.getClientRestUserID()%>";
	    	var co = "<%=userObj.getClientRestUserBranch()%>";
	    	var userType = "<%=userObj.getUserType()%>";
	    	UserManagement.insertDownloadPlistDetailsOfUser(logedInId, co, userType, appType,{
	    		callback : function(response) 
	    		{
  	    			//alert(response);
	    	    }	
	        });                
	    	
	    	if(appType == '<%=ConfigurationProperties.E_RECRUITMENT_APP_URL%>'){
				window.location.href = '<%=eRecruitmentAppURL%>';
	    	}else if(appType == '<%=ConfigurationProperties.EOP_SCAN_APP_URL%>'){ 
				window.location.href = '<%=eopScanAppURL%>';
	    	}
	});
	</script>
 <%}catch(Exception e) {
	e.printStackTrace();}
%>