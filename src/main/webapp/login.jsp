<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.quix.aia.cn.imo.constants.ApplicationAttribute"%>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes'%>
<html>
<head>

<META http-equiv="Content-Type" content="text/html;charset=UTF-8">


<meta name="GENERATOR" content="IBM WebSphere Studio">
<title>Welcome to IMO</title>

<link href="css/reset.css" rel="stylesheet" type="text/css">
<link href="css/grid1200.css" rel="stylesheet" type="text/css">
<link href="css/main.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/jqtranform.css" type="text/css" media="all">

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8" ></script>
<script type="text/javascript" src="js/jquery-2.2.0.js" ></script>

<SCRIPT LANGUAGE="JavaScript">
function submitForm()
{
	document.loginForm.submit();	
}
</SCRIPT>

</head>

<body onload="dwr.engine.setActiveReverseAjax(true);">
<%
String version = "";
session.removeAttribute(SessionAttributes.CURR_USER_OBJ);
if(application.getAttribute(ApplicationAttribute.LATEST_JS_CSS_VERSION)!=null)
	version = (String)application.getAttribute(ApplicationAttribute.LATEST_JS_CSS_VERSION);

%>
<div class="page-wrap">
					<div id="header" >
    					<div class="container_12">
        					<div class="grid_2">
                				<img src="images/aia_logo.png" width="153" height="65" alt="">
                 			</div>
                			
     					 </div>
    				</div>
    			
  	<form method="post" action="ContentManager" name="loginForm" acceptCharset="UTF-8">
  	<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
  
   		<div class="clearfix" align="center" style="background-color:#ffffff;">
   				<div class="content MT100 MB100">	 
      				<table  class="formDesign" >
              				 <tr>
	               				<td colspan="3" style="border: none;color:red"  >	 
		               				<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null)
										{
											com.quix.aia.cn.imo.utilities.ErrorObject errorObj = new com.quix.aia.cn.imo.utilities.ErrorObject();
											errorObj = (com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ);
											if(!errorObj.getErrorMsg().equals(com.quix.aia.cn.imo.constants.ErrorMessages.ALREADY_LOGGED_IN))
											{%>                                   				
		              							 <%=((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg()%>
		               							 <%request.removeAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ);
											}
										}%>
				              	 </td>
		               		</tr>     
			                <tr style="height: 17px;">
				               <td colspan="3"></td>
				            </tr>          					
			               <tr>                         
				               <td ><label >用户</label></td>
				               <td ><input name="userID" id="userID" type="text" class="textObj" style="width:80%" autocomplete="off"/></td>
				               <td align="left">	
									<select name="SelectLang" id="SelectLang" class="comboObj" >
										 <option value="CN" >Chinese</option>           		 
				                          <option value="EN" >English</option>        
				                    </select>
				               </td>
			               </tr>
			                <tr  >                         
				               <td><label > 密码</label></td>
				               <td><input name="password" id="password" type="password" class="textObj" style="width:80%" autocomplete="off"/></td>
				               <td></td>
			               </tr>
			                 <tr  >                         
				               <td><label >分公司</label></td>
				               <td>
				               <select name="branch" id="branch"  class="comboObj" >
                            			<option value="">请选择</option> 	                                 	
                            	</select>
                            </td>
				               <td></td>
			               </tr>
		                   <tr>
			                  <td style="border: none;">&nbsp;</td>
			                  <td style="border: none;">&nbsp;</td>
			                  <td style="border: none;">&nbsp;</td>
		                   </tr> 
	             
	               			<tr style="border: none;">                         
	               				<td >&nbsp;&nbsp;</td>	                                    				
	               				<td >
		             				<a href="#" class="btn1 " onclick="javascript:submitForm()">登入</a>
	             				</td>
	              				<td></td>
	             			</tr>
	             			 <tr>
			                  <td style="border: none;">&nbsp;</td>
			                  <td style="border: none;">&nbsp;</td>
			                  <td style="border: none;">&nbsp;</td>
		                   </tr> 
	             			<!-- <tr>
			               		<td></td>                         
				               	<td >	                                    				
				            		<a href="#" class="forgot"  onclick="javascript:window.open('UserResetPsw.jsp', 'windowname', 'scrollbars,resizable,status,toolbar,menubar');">Reset password?</a>
				                </td>
				                <td></td>  
			               </tr>  -->
           			</table>
				</div>
			</div>
 </form>
 	<div class="clearfix" style="margin-top:82px"></div>
	<div class="site-footer"  style="position: absolute;">
				<div class="container_12">
							 <div class="grid_12 MT25">
						<div style="float:right;">
						 <a  style="color: white; font-size: 12px; text-decoration: none; " href="Terms&Condition/Tearms.docx">条款与条件</a> &nbsp;&nbsp;&nbsp;&nbsp; <a style="color: white; font-size: 12px; text-decoration: none;" href="Terms&Condition/Preivacy.docx">隐私声明</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						Version : 1.38</div> Copyright @ 2015, AIA Group Limited and its subsidiaries. All rights reserved. 
					</div> <%-- <%=version %> --%>
				</div>
	 </div>
</div>
<script>
getBranchForLogin('2', '', 'L');
</script>
</body></html>