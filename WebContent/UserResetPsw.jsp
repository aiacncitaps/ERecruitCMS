<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
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


<script  language="javascript" >
function addActivity()
{
	
		var userid=document.getElementById("id").value;
	
		var flag=false;

		if(flag==false)
		{
			UserManagement.resetPassword(userid,'<%=request%>', {
				callback : function(str) {
				
					if(str=="1"){
						window.location.href = "login.jsp";
					}else{
						document.getElementById("p1").innerHTML = "Invalid User Id!";
					}
					
				}
			});
	
		}
}

</script>

</head>

<body onload="dwr.engine.setActiveReverseAjax(true);"  >

<div class="page-wrap">
					<div id="header" >
    					<div class="container_12">
        					<div class="grid_2">
                				<img src="images/aia_logo.png" width="153" height="65" alt="">
                 			</div>
                			
     					 </div>
    				</div>
  <form method="post" action="ContentManager" name="loginForm">
   				<div  style="background-color:#ffffff;"> 
   							<div class="content  MT100 MB100">	 
   							<div class="form clearfix">
                           	
      						 <table width="100%" style=" border: none;"  >
                 
              				 <tr>
	               			<td colspan="2" style="border: none;text-align: center;color:red"  >	
	               			<label id="p1"></label>
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
		               <td colspan="2" style="border: none;"  >	 
		               </td>
		           </tr>          					
	               <tr>                         
		               <td style="border: none;text-align:right;" width="40%" >	                                    				
		               <label style="margin-right:20px">User Id</label>
		               </td>
		               <td style="border: none;"  >	                                    				
		               <input name="id" id="id" type="text" class="textbox class_limit" size="10" style="width:200px;border: 4px solid #CCCCCC;padding: 9px 10px;margin-left:20px;"/>
		               </td>
	               </tr>
	               
	               <tr>
	                  <td style="border: none;">&nbsp;</td>
	                  <td style="border: none;">&nbsp;</td>
	               </tr> 
	             
	               <tr style="border: none;">                         
	               <td  style="border: none;">&nbsp;&nbsp;</td>	                                    				
		                                    				
	               <td style="border: none; "  >
		            
		            <a href="#" class="btn1 " onclick="addActivity();">Reset</a>
	             </td>
	             </tr>
             
           </table>
                                                    
                                                    
					</div>
					</div>
				</div>
			
 </form>

		
<div class="site-footer" >
				<div class="container_12">
							 <div class="grid_12 MT25">
						Copyright @ 2014, AIA Group Limited and its subsidiaries. All rights reserved.
					</div>
				</div>
 </div>
	</div> 
</body></html>