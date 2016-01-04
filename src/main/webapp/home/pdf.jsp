<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.mapper.UserMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<script src="js/lms.js" type="text/javascript"></script>

 <script type="text/javascript">
 function userCSVSubmit(action) 
 {
 	
  document.administrationForm.actionType.value = action ;
  document.administrationForm.submit() ;
   
 }
</script>
<style>
@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) {.class_limit{width:260px;}}
@media screen and (min-width:0\0) and (min-resolution: +72dpi) {.class_limit{width:270px;}}
</style>


		<div id="maincontainer">
					<div class="head">
					<h2 align="center"></h2></div>  	
						
					</div>
					<div class="content" style="background-color:#ffffff;">
<form method="post" action="FormManager" name="administrationForm"  >
<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
<input type="hidden" id="actionType" name="actionType">
<table class="formDesign MB20" >
					<tr>
						<td colspan="2" style="text-align: center">
						<span style="color:#ec2028;">
                     	  
					</tr>

                   		       			   
           			    <tr >                         
             			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
             				<a href="ContentManager?key=pdf" class="btn1 "  id="submit" name="submit" onclick="">Submit</a> &nbsp;&nbsp;
 		 					
            	 			
            	 				<%if(request.getSession().getAttribute("pdf")!=null ){ 
            	 				
            	 				String summaryPath=(String)request.getSession().getAttribute("pdf");
            	 				%>
            	 					 <a href="<%=summaryPath %>" target="_new" class="btn1">Download Error Summary </a>
            	 				<% }  request.getSession().removeAttribute("pdf");
            	 				request.getSession().removeAttribute("strbuf");%>
            	 			
            	 			
            	 			</td>                                     			   
           			    </tr>
 		 				
			
			</table>
			</form>
     	</div>
    