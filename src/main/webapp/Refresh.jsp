<html>
<head>

<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="GENERATOR" content="IBM WebSphere Studio">
<title>Welcome to IMO</title>

<link href="css/reset.css" rel="stylesheet" type="text/css">
<link href="css/grid1200.css" rel="stylesheet" type="text/css">
<link href="css/main.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/jqtranform.css" type="text/css" media="all">
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">
function reloadCache(){
	
	ImoUtilityData.getRefresh({
					callback : function(str) {
							window.location.href = "login.jsp";
							
					}
				});

}

function updateBaseonAgenter(){
	
	ImoUtilityData.updateCitySscOfficeBaseonAgenter({
		callback : function(str) {
				window.location.href = "login.jsp";
				
		}
	});
	
	
}

function reloadScript(){
	ImoUtilityData.insertScript({
					callback : function(str) {
							window.location.href = "login.jsp";
				
					}
				});

}
</script>
</head>
<%
  	try{
  	session.invalidate();
  	}catch(Exception e)
  	{}
  	
  	%>
<body onload="dwr.engine.setActiveReverseAjax(true);"  >

<div class="page-wrap">
					<div id="header" >
    					<div class="container_12">
        					<div class="grid_2">
                				<img src="images/aia_logo.png" width="153" height="65" alt="">
                 			</div>
                			
     					 </div>
    				</div>
    			
  
  	
   		<div class="clearfix" align="center" style="background-color:#ffffff;">
   				<div class="content MT100 MB100">	 
      				<table  class="formDesign" >
              				 <tr>
	               				<td colspan="3" style="border: none;color:red"  >	 
		               				
				              	 </td>
		               		</tr>     
			                <tr style="height: 17px;">
				               <td colspan="3"></td>
				            </tr>          					
			               <tr>                         
				              <td >	                                    				
				            		<a href="#" class="btn1" onclick="javascript:reloadCache()" >Refresh Cache</a>
				                </td>
			               </tr>
			               
			               <tr style="height: 17px;">
				               <td colspan="3"></td>
				            </tr>          					
			               <tr>                         
				              <td >	                                    				
				            		<a href="#" class="btn1" onclick="javascript:reloadScript()" >Script Add</a>
				                </td>
			               </tr>
			               
			               <tr style="height: 17px;">
				               <td colspan="3"></td>
				            </tr>  
				            
			                <tr>                         
				              <td >	                                    				
				            		<a href="#" class="btn1" onclick="javascript:updateBaseonAgenter()" >Refresh City,Ssc,Office Base on Agenter Data</a>
				                </td>
			               </tr>
			               
           			</table>
				</div>
			</div>
 	<div class="clearfix" style="margin-top:82px"></div>
	<div class="site-footer" >
				<div class="container_12">
							 <div class="grid_12 MT25">
						Copyright @ 2014, AIA Group Limited and its subsidiaries. All rights reserved.
					</div>
				</div>
	 </div>
</div>
</body></html>