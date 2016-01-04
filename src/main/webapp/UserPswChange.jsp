<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<html><head>

<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="GENERATOR" content="IBM WebSphere Studio">
<title>Welcome to IMO</title>

<!-- Beginning of compulsory code for Menu-->

<link href="css/reset.css" rel="stylesheet" type="text/css">
<link href="css/grid1200.css" rel="stylesheet" type="text/css">
<link href="css/main.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/jqtranform.css" type="text/css" media="all">
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/UserManagement.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<%
User userObj = (User)request.getSession().getAttribute("currUserObj");
%>

<script  language="javascript" >
function addActivity()
{
if(document.getElementById("newPsw").value == ''){
	document.getElementById("p1").innerHTML = "Please Provide a Password ";
	
}
else{
	if( document.getElementById("newPsw").value  !=document.getElementById("conPsw").value){
		
		document.getElementById("p1").innerHTML = "Password Missmatch Try Again ";
		document.getElementById("newPsw").value="";
		document.getElementById("conPsw").value="";
		return false;
	}else{
		var newPsw=document.getElementById("newPsw").value;
		var userId=document.getElementById("userId").value;
		
		var flag=false;

		if(flag==false)
		{
			document.getElementById("p1").innerHTML = "";
			 UserManagement.changeFirstTimePassword(newPsw,userId,'<%=request%>', {
				callback : function(str) {
				
					if(str=="1"){
						window.location.href = "ContentManager?key=login";
					}else if(str=="2"){
						document.getElementById("p1").innerHTML = "Old password and New Password Cannot be Same.";
						document.getElementById("newPsw").value="";
						document.getElementById("conPsw").value="";
					}else{
						document.getElementById("p1").innerHTML = "InValid UserName!";
					}
					
				}
			});
	
		}	 
	}
}
		
}

</script>
</head>

<body onload="dwr.engine.setActiveReverseAjax(true);"  >

<div class="page-wrap" >
					<div id="header" >
    					<div class="container_12">
        					<div class="grid_2">
                				<img src="images/aia_logo.png" width="153" height="65" alt="">
                 			</div>
                			
     					 </div>
    				</div>
	
	 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	    <tr>
		<td align="center" valign="top">
  <form method="post" action="FormManager" name="administrationForm"  >
  <input type="hidden" id="userId" name="userId" value="<%=userObj.getUser_no() %>" >
   		<div class="container_12 clearfix" align="center">
   			<div class="grid_12">   
   				<div class="add_new_class"> 
   							<div class="content MT100 MB100">	 
   							<div class="form clearfix">
                           	
      						 <table width="53%" style=" border: none;"  >
                 
              				 <tr>
	               			<td width="143" colspan="3" style="border: none;"  >	
	               			
	               			<label  id="p1" name="p1" style="text-shadow: red;"  >  </label> 
		               <%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null)
												{
												com.quix.aia.cn.imo.utilities.ErrorObject errorObj = new com.quix.aia.cn.imo.utilities.ErrorObject();
												errorObj = (com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ);
												
												
													if(errorObj.getErrorMsg().equals(com.quix.aia.cn.imo.constants.ErrorMessages.ALREADY_LOGGED_IN))
													{
													
													}
													else
													{
												%>                                   				
		               <%=((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg()%>
		               		<%
												request.removeAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ);
												}}%>
												
		              	 </td>
               		</tr>     
	                <tr style="height: 7px;">
		               <td width="143" colspan="3" style="border: none;"  >	 
		               </td>
		           </tr>          					
	               <tr>                         
		               <td width="143" style="border: none;"  >	                                    				
		               <label >New Password</label>
		               </td>
		               <td width="143" style="border: none;"  >	                                    				
		               <input name="newPsw" id="newPsw" type="password" class="textbox class_limit" style="border: 4px solid #CCCCCC;padding: 9px 10px;margin-left:20px;"/>
		               </td>
		              </tr>
		              <tr>  
		               <td width="143" style="border: none;"  >	                                    				
		               <label >Confirm Password</label>
		               </td>
		               <td width="143" style="border: none;"  >	                                    				
		               <input name="conPsw" id="conPsw" type="password" class="textbox class_limit" style="border: 4px solid #CCCCCC;padding: 9px 10px;margin-left:20px;"/>
		               </td>
		                </tr> 
	              
	               
	                  <tr>
	                  <td style="border: none;">&nbsp;</td>
	                  <td style="border: none;">&nbsp;</td>
	                  <td style="border: none;">&nbsp;</td>
	                 </tr> 
	             
	               <tr style="border: none;">                         
	               <td  style="border: none;">&nbsp;&nbsp;</td>	                                    				
		                                    				
	               <td style="border: none; "  >
		            
		            <a href="#" class="btn1 " style="margin-left:20px;" onclick="addActivity();">SUBMIT</a>
	             </td>
	              <td style="border: none;"  >	
		                </td>
	             </tr>
             
           </table>
                                                    
                                                    
					</div>
					</div>
				</div>
			</div>
		</div>
    
 </form>

		 </td>
	 </tr>
   </tbody></table>
		
	<div class="site-footer" >
				<div class="container_12">
							 <div class="grid_12 MT25">
						Copyright @ 2014, AIA Group Limited and its subsidiaries. All rights reserved.
					</div>
				</div>
	 </div>
</div>

</body></html>