<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@page import="com.quix.aia.cn.imo.mapper.EopMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
%>
<script type="text/javascript">//<![CDATA[
               
      /*   Calendar.setup({
  
             inputField : "dateRcv",
             trigger    : "cal1",
       showTime   : true,
             onSelect   : function() { this.hide()},
       dateFormat : "%d/%m/%Y"
         });   */
        
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
	dateFormat:"dd/mm/yy"
	});
 });
</script>
<form name="tstest" method="post">
<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
<div class="page-wrap"> 
   	<div id="maincontainer">	
   		<div class="container_12 clearfix" align="center">
   				<div class="add_new_class" > 
   					<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("EOP ADD Special Group")%></h2></div>  	
   							<div class="content" >	 
   								 	  	  <div class="form clearfix" >
                                				<table width="53%" style="border: none;"  >
                                				
                                				<tr>                          
                                    				<td style="width: 35%; border: none;"  >
	                                    				<label><%=localeObj.getTranslatedText("Event Type")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        			<select name="type" id="type" class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 70%; "   >
                                       						<option value="eop">EOP</option>	
                                       						<option value="eop">Training</option>                                       	
                                       	 			</select>
                                       	 			</td>                                    			   
                                      			    </tr>
                                      			    <tr>                         
                                    				<td style="border: none;"  >	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("EOP Topic")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<input name="name" id="name" type="text" class="textbox class_limit" style="border: 4px solid #CCCCCC;padding: 9px 10px; width: 64%;" value="New Gen New born"  />
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				<tr>                         
                                    				<td style="border: none;"  >	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Event Name")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<input name="name" id="name" type="text" class="textbox class_limit" style="border: 4px solid #CCCCCC;padding: 9px 10px; width: 64%;" value="Training Workshop for New Gen!"  />
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td style="border: none;"  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Date")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
														<input name="timestamp" id="timestamp" type="text" class="textbox datepicker" style="border: 4px solid #CCCCCC;padding: 9px 10px;width: 64%;" value="" onkeypress='return false' />
                                        				
                                    				<!-- 	<a href="javascript:show_calendar('document.tstest.timestamp', document.tstest.timestamp.value);"><img src="images/calendar2.jpg" width="18" height="18" border="0" alt="Click Here to Pick up the timestamp"></a> -->
                                       				
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td style="border: none;"  >                                   				
	                                    				<label ><%=localeObj.getTranslatedText("Start Time")%></label>
                                    				</td>
                                        			<td style="text-align: left;" >	
                                        				<select name="type" id="type" class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 16%;" >
                                       						<option>01</option><option>02</option><option>03</option><option>04</option><option>5</option>
															<option>06</option><option>07</option><option>08</option><option>09</option><option>10</option>
															<option>11</option><option>12</option>                                       	
                                       	 				 </select>&nbsp;&nbsp;HH  &nbsp&nbsp
                                       	 				 
                                       	 				 
                                       	 				 <select name="type" id="type" class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 20%; " >
                                       						<option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>
															<option>6</option><option>7</option><option>8</option><option>9</option><option>10</option>
															<option>11</option><option>12</option>                                       	
                                       	 				 </select>&nbsp;&nbsp; MM &nbsp&nbsp
                                       	 				 
                                       	 				 <select name="type" id="type" class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 20%; " >
                                       						<option>AM</option><option>PM</option>                                     	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                                      			    
                                      			    <tr  >                         
                                    				<td style="border: none;"  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("End Time")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<select name="type" id="type" class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 16%;" >
                                       						<option>01</option><option>02</option><option>03</option><option>04</option><option>5</option>
															<option>06</option><option>07</option><option>08</option><option>09</option><option>10</option>
															<option>11</option><option>12</option>                                       	
                                       	 				 </select>&nbsp;&nbsp;HH  &nbsp&nbsp
                                       	 				 
                                       	 				 
                                       	 				 <select name="type" id="type" class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 20%; " >
                                       						<option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>
															<option>6</option><option>7</option><option>8</option><option>9</option><option>10</option>
															<option>11</option><option>12</option>                                       	
                                       	 				 </select>&nbsp;&nbsp; MM &nbsp&nbsp
                                       	 				 
                                       	 				 <select name="type" id="type" class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 20%; " >
                                       						<option>AM</option><option>PM</option>                                     	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				<tr  >                         
                                    				<td style="border: none;"  >	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("EOP Description")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<input name="<%=EopMaintenance.DESCRIPTION_PARAM %>" id="<%=EopMaintenance.DESCRIPTION_PARAM %>" type="text" class="textbox class_limit" style="border: 4px solid #CCCCCC;padding: 9px 10px;" value="Encourage New Gen"  />
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				
                            		 				<tr>                         
                                    				<td style="border: none;"  >                                   				
	                                    				<label ><%=localeObj.getTranslatedText("Location")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<textarea name="location" id="location"    style="border: 4px solid #CCCCCC; width: 68%;height: 52px;" rows="5" cols="5" >No.16 LiangJing Rd, Zhang Jiang, PuDong New District, Shanghai, 201203 P. R. China</textarea>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				
                            		 				<tr>                         
                                    			    <td style="border: none;"  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Speaker")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<input name="speaker" id="speaker" type="text" class="textbox class_limit" style="border: 4px solid #CCCCCC;padding: 9px 10px;"  value="Maggie Tan"  />
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td style="border: none;"  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Estimated Candidates")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<input name="estCand" id=""estCand"" type="text" class="textbox class_limit" style="border: 4px solid #CCCCCC;padding: 9px 10px;"  value="50" />
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				
                            		 				<tr  >                         
                                    				<td style="border: none;"  >                                  				
	                                    				<label ><%=localeObj.getTranslatedText("Open To")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<select name="openTo" id="openTo"  class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 70%; "  >
                                       						<option value="sg">Special Group</option>	                                       	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr  >                         
                                    				<td style="border: none;"  >	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Upload Special Group Listing")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<input name="UploadSpecialGroupListing" id="UploadSpecialGroupListing" type="file" style=" border: 4px solid #CCCCCC;" onChange="uploadFile1();"  />          
                                    					 <progress value="22" max="100" id="progress1" style="border: 4px solid #CCCCCC; width: 30%;" ></progress> 
                                       	 			</td> 
                                       	 			                                    			   
                                      			    </tr>
                            		 				
                            		 				<tr >                         
                                    				<td style="border: none;"  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("BU")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<select name="openTo" id="openTo"  class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 70%; "  >
                                       						<option value="sg">ALL</option>	                                       	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr >                         
                                    				<td style="border: none;"  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("District")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<select name="openTo" id="openTo"  class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 70%; "  >
                                       						<option value="sg">ALL</option>	                                       	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				<tr >                         
                                    				<td style="border: none;"  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("City")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<select name="openTo" id="openTo"  class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 70%; "  >
                                       						<option value="sg">ALL</option>	                                       	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                            		 				<tr>                         
                                    				<td style="border: none;"  >                                  				
	                                    				<label ><%=localeObj.getTranslatedText("SSC")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<select name="openTo" id="openTo"  class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 70%; "  >
                                       						<option value="sg">ALL</option>	                                       	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                                      			     <tr  >                         
                                    				<td style="border: none;"  >                                  				
	                                    				<label ><%=localeObj.getTranslatedText("Agent Team")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<select name="<%=EopMaintenance.AGENT_TEAM_PARAM %>" id="<%=EopMaintenance.AGENT_TEAM_PARAM %>"  class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 70%; "  >
                                       						   <option value="0"><%=localeObj.getTranslatedText("Select")%></option> 
		                            						   <option value="1">Agent1</option> 
		                            						   <option value="2">Agent2</option>                                          	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				<tr  >                         
                                    				<td style="border: none;"  >	                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Profile Upload")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<input name="material" id="material" type="file" style=" border: 4px solid #CCCCCC;" onChange="uploadFile();"  />                
                                    					 <progress value="22" max="100" id="progress" style="border: 4px solid #CCCCCC; width: 30%;" ></progress> 
                                       	 			</td> 
                                       	 			                                    			   
                                      			    </tr>
                            		 				<tr  >                         
                                    				<td style="border: none;"  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Organizer")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<select name="openTo" id="openTo"  class="jqTransformSelectWrapper"  style="border: 4px solid #CCCCCC;  width: 70%; "  >
                                       						 <option value="CHO">CHO</option> 
		                            						  <option value="BU">BU</option> 
		                            						  <option value="District">District</option>
		                            						  <option value="District">District</option>                                                 	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                      			    </tr>
                                      			   <tr>                         
                                    				<td style="border: none;"  >                                    				
	                                    				<label ><%=localeObj.getTranslatedText("Created By")%></label>
                                    				</td>
                                        			<td style="text-align: left;">	
                                        				<input name="estCand" id="estCand" type="text" class="textbox class_limit" style="border: 4px solid #CCCCCC;padding: 9px 10px;"  value="Maggie Tan" />
                                       	 			</td>                                     			   
                                      			    </tr>
													<tr>                         
                                    				<td>&nbsp;</td>	</tr>
                                      			    <tr>                         
                                        			<td style="border: none;text-align:center " colspan="2" >
                                        				<a href="#" class="btn1 "  ><%=localeObj.getTranslatedText("Submit")%></a> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                            		 					<a href="#" class="btn1"><%=localeObj.getTranslatedText("Cancel")%></a> 
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                                       				</table>
 							
                          							</div> 
				</div>
			</div>
		</div>
    </div>
 </div>
 </form>
