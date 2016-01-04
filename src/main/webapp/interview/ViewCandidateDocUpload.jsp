<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@ page import='com.quix.aia.cn.imo.data.user.*' %>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes'%>


<%User user = ((com.quix.aia.cn.imo.data.user.User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ));
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
%>


<script language="javascript" type="text/javascript" >
        window.history.forward();
</script>

<script type="text/javascript">//<![CDATA[
     
        function uploadFile() {
       
        	  $("#progress").show();
        	}
			 function uploadFile1() {
       
        	  $("#progress1").show();
        	}
			 function uploadFile2() {
       
        	  $("#progress2").show();
        	}
             
</script>
<script>
$( document ).ready(function() {
	$("#progress").hide();
	$("#progress1").hide();
	$("#progress2").hide();
});

</script>

<div class="page-wrap"> 
   <div id="maincontainer">
   		<div class="container_12 clearfix">
                      <form method="post" action="FormManager?key=online_admin" name="newUserForm">
							<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
							<input type="hidden" name="<%=com.quix.aia.cn.imo.constants.FormInfo.HIDDEN_ACTION%>"/>
                        	<div class="add_new_class"> 
                        		<div class="head">
                                	 <h2><%=localeObj.getTranslatedText("View Candidate Doc Upload")%></h2>
                                                                                                
                                </div>
                                <!--  -->
                                  <div class="content">
                                	<!-- <h4 class="P4S_25">Main Details</h4> -->                               
                                	<div class="form clearfix">
                                
											<div align="center">
											</div>
											
                                	
                                     	<div class="clear"></div>                               
                            		 	<div class="form-item ML20">
                             					<label><%=localeObj.getTranslatedText("Candidate Name")%> : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Alex Wong </label>
                                    		
                                     	</div>  
                                                                                    
                       				<div class="form-item ML75">
                       				
                                    <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=localeObj.getTranslatedText("Servicing Agent")%>  :  55888</label>
                                    
                                                                    
                                   </div>
                                   
                                 
                            
                                <div class="clear"></div> 
                                   		
                                     
                             	<div class="form-item ML20" >
                                <br/><label><%=localeObj.getTranslatedText("Age")%> : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 29</label>
                                </div>
                                
                                <div class="form-item ML75" >
                                <br/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=localeObj.getTranslatedText("Source of Referral")%> :  Friend</label>
                                    		
                                </div>
                                
                                
                               
                                 
                               <div class="clear"></div> 
                               
                                <div class="form-item ML20" >
                                <br/><label><%=localeObj.getTranslatedText("Date of Birth")%> : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 5/10/2015</label>
                                </div>
                                
                                <div class="form-item ML75" >
                                <br/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=localeObj.getTranslatedText("Gender")%> :  M</lable>
                                    		
                                </div>
                                
                                
                                <div class="form-item ML75" >
                                <br/><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=localeObj.getTranslatedText("Contact number")%>: 123 5555 3333</label>
                                    		
                                </div>
                                 
                               
                                
                                 <div class="clear"></div>
                                
                                <div class="form-item ML15" >
                                <br/><label>&nbsp;&nbsp;<%=localeObj.getTranslatedText("Material Description")%> : <input name="name" id="name" type="text"  value=""  style="border: 4px solid #CCCCCC;padding: 9px 10px; margin-left:0px;width:170px;"/></label>  <input name="name" id="name" type="text"  value=""  style="border: 4px solid #CCCCCC;padding: 9px 10px; margin-left:172px;width:170px;"/><br/><br/>
                                <input name="name" id="name" type="text"  value=""  style="border: 4px solid #CCCCCC;padding: 9px 10px; margin-left:170px;width:170px;"/>
                                </div>
                                
                                <div class="form-item" >
                                
                                <br/><label><%=localeObj.getTranslatedText("resume.pdf")%> :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="material" id="material" type="file" style="margin-left:0px;; border: 4px solid #CCCCCC; width: 32%;" onChange="uploadFile1();"  />  </label><progress value="22" max="100" id="progress1" style="border: 4px solid #CCCCCC; width: 30%; margin-left:400px; margin-top:-32px;" ></progress> 
                                <label>&nbsp;<%=localeObj.getTranslatedText("application.pdf")%> :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="material" id="material" type="file" style="margin-left:0px;; border: 4px solid #CCCCCC; width: 32%;" onChange="uploadFile2();"  />       </label><progress value="22" max="100" id="progress2" style="border: 4px solid #CCCCCC; width: 30%; margin-left:400px; margin-top:-32px;" ></progress> 
                                 <label><%=localeObj.getTranslatedText("cc_test.pdf")%> :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="material" id="material" type="file" style="margin-left:0px;; border: 4px solid #CCCCCC; width: 32%;" onChange="uploadFile();"  />        </label><progress value="22" max="100" id="progress" style="border: 4px solid #CCCCCC; width: 30%; margin-left:400px; margin-top:-32px;" ></progress> 
                                </div>
                                
                                
                                
                                <div class="clear"></div>
                                
                                <div class="clear"></div>
                                
                                <div class="clear"></div>
                                <div class="clear"></div><div class="clear"></div><div class="clear"></div><div class="clear"></div><div class="clear"></div><div class="clear"></div><div class="clear"></div><div class="clear"></div><div class="clear"></div><div class="clear"></div>
                              
                                
                               <br/>
                               <br/>
                               <br/>

					            
					            <div class="clearfix"></div>
					            
					            <div class="clear"></div>
                                
                                
 							  </div>
                             </div> 
                             </form>
                             </div>
                           </div>  
                       </div>
               </div>
               

<script type="text/javascript">//<![CDATA[
  
        
        function uploadFile() {
        	
        	
        	  $("#progress").show();
        	}
			function uploadFile1() {
        	
        	
        	  $("#progress1").show();
        	}
             
</script>
<script>
$( document ).ready(function() {
	
	$("#progress").hide();
	$("#progress1").hide();
});
$(function() {
	$( ".datepicker" ).datepicker({
	showOn: "button",
	buttonImage: "images/calendar_icon.gif",
	buttonImageOnly: true,
	dateFormat:"dd/mm/yy",
	changeMonth: true, 
	changeYear: true
	});
 });
</script>