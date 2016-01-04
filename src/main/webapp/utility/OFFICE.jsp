<%--
  - Author(s):          KHYATI
  - Date:               15-MAY-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        
--%>


<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.data.ssc.*"%>
<%@page import="com.quix.aia.cn.imo.mapper.*"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.utilities.Pager"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>


<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Pager pager = (com.quix.aia.cn.imo.utilities.Pager) request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
Ssc classObj = new  Ssc();
int bu=0;
int dis=0;
int branch=0;
int city=0;
int ssc=0;



if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){
	
	 bu=Integer.parseInt(request.getParameter("bu"));
	 dis=Integer.parseInt(request.getParameter("district"));
	 branch=Integer.parseInt(request.getParameter("branch"));
	 city=Integer.parseInt(request.getParameter("city"));
	 ssc=Integer.parseInt(request.getParameter("ssc"));
	 
}




%>
<script>
$(function(){
	$(".data_table tr:odd").addClass("odd");
    $(".data_table tr:not(.odd)").addClass("even");  
});
$( document ).ready(function() {
	getBU('<%=bu %>','L');
	getDistrict('<%=bu %>','<%=dis %>','L');
	getBranch('<%=dis %>','<%=branch %>','L');
	getCity('<%=branch %>','<%=city %>','L');
	getSSC('<%=city %>','<%=ssc %>','L');
	
	
	$("#progress").hide();
	$("#progress1").hide();
	
});
</script>

<script type="text/javascript">
    
			
			function submitForm(){
				document.BUForm.submit();
				
			}
             
</script>


<script>
function confirmDelete(code){
	var str="<%=localeObj.getTranslatedText("Are you sure you want to delete this OFFICE?")%>";
	var dialog = $('<p>'+str+'</p>').dialog({
   	 width: 550,
    	 cache: false,
        buttons: {
            "<%=localeObj.getTranslatedText("Yes")%>": function() {
            	window.location.href = 'FormManager?key=OFFICEAdd&type=DELETE&OFFICE_CODE='+code;
            },
            "<%=localeObj.getTranslatedText("No")%>":  function() {$('.ui-dialog').hide();},
            
        }
    });
}
</script>

   	<div id="maincontainer">	
   		
   					<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("Office Maintenance")%></h2></div>  	
   							<div class="content" style="background-color:#ffffff;"> 
								<form action="ContentManager" name="BUForm" method="post" class="PT20">
								<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
   												   
                           				 <table class="formDesign">
                           				 
                           			 <%
				                     if(request.getAttribute(RequestAttributes.MESSAGE_OBJ)!=null)
				                     {
				                      %>	
                                		<tr>                         
                                    	<td colspan="2" style="text-align: center"><span style="color:#ec2028;">	                                    				
	                                    	
<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.MsgObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.MESSAGE_OBJ)).getMsg())%>
</span>
                                    	</td>
                                        <%	request.removeAttribute(RequestAttributes.MESSAGE_OBJ);
                                        
				                     } %>                                			   
                                      	</tr>		 
                           				 
                           				 
                           				 
                                			
                                	      <tr>
	                       <%
					          if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) == null){
					        %>
					             <td colspan="2">
					                   <div align="center"></div>
					             </td>
					                          <%
					                          	}else{
					                          %>
					            <td colspan="2" style="text-align: center"><span style="color:#ec2028;">
					                       <%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
					                       </span>
					            </td>
					                          <%
					                          	}
					                          %>
	                       </tr>	
	                       
	                       
	                                      <tr>                         
                                    				<td style="border: none;">	                                    				
	                                    				<label><%=localeObj.getTranslatedText("BU")%></label>
                                    				</td>
                                        			<td>	
                                        				<select name="<%=DistrictMaintenance.BU_PARAM %>" id="<%=DistrictMaintenance.BU_PARAM %>"  class="comboObj" onchange="getDistrict(this.value,'<%=dis %>','E');" >
                                       						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>	                                  	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                           </tr>	
                                        	
	                       
	                   <!-- -District -->    
	                   
	                  
                                           
                             <tr >                         
              				<td style="border: none;"  >                                    				
               				<label ><%=localeObj.getTranslatedText("District")%></label>
              				</td>
                  			<td >	
                  		    <select name="district" id="district"  class="comboObj"  onchange="getBranch(this.value,'<%=branch %>','E');" >
                 						<option value="0" >All</option>                                      	
                 	 	    </select>
                 	 	     </td>                                     			   
                			  </tr>
                                      			    
                               				
                      <!-- -District --> 
	                                       
	                              <tr >                         
              				<td>                                    				
               				<label ><%=localeObj.getTranslatedText("Branch")%></label>
              				</td>
                  			<td >	
                  				<select name="branch" id="branch"  class="comboObj"  onchange="getCity(this.value,'<%=city %>','E');"  >
                 						<option value="0" >All</option>                                      	
                 	 				 </select>
                 	 			</td>                                     			   
                			    </tr>       
	                                       

                                            
                                      			    
                                      			    
                        <tr>                         
                                    				<td   >	                                    				
	                                    				<span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("City")%></label>
                                    				</td>
                                        			<td>	
                                        				<select name="city" id="city"  class="comboObj" onchange="getSSC(this.value,'<%=ssc %>','E');"  >
                                       						<option value="0" >All</option>                                        	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                           </tr>	
                                           
                                           
                                           
                                           <tr>                         
                                    				<td   >	                                    				
	                                    				<span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("SSC")%></label>
                                    				</td>
                                        			<td>	
                                        				<select name="ssc" id="ssc"  class="comboObj"  >
                                       						<option value="0" >All</option>                                        	
                                       	 				 </select>
                                       	 			</td>                                     			   
                                           </tr>	
          			    
          			    
          			   
          			    
          			    
          			    <tr >                         
        				<td style="border: none;"  >                                    				
         				<label ><%=localeObj.getTranslatedText("Office Name")%></label>
        				</td>
            			<td >	
            				<input name="name" id="name" type="text" class="textObj" value=""  />
           	 			</td>                                     			   
          			    </tr>
          			    
                            		 				
                                      			   
               			    <tr  >                         
                 			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                 				<a href="#" class="btn1 " style="margin-left: 2%; "  onclick="javascript:submitForm();"><%=localeObj.getTranslatedText("Search")%></a> &nbsp;&nbsp;
     		 					<a href="javascript:document.BUForm.reset();" class="btn1"><%=localeObj.getTranslatedText("Clear")%></a> 
                	 			</td>                                     			   
               			    </tr>
     		 				
                				</table>
                                       				
                                                    

                                	  <br>
                                	
                                	 <div class="MT30" >
						<table style="width:100%">
								<%int noOfAnnouncements = 0;
								  if(pager != null)
								  {
									noOfAnnouncements = ((com.quix.aia.cn.imo.utilities.Pager)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)).getActualSize();
							  	  }%>								
								<tr><td align="left"><a href="FormManager?key=OFFICEAdd&type=NEW" class="ML10 btn1" ><%=localeObj.getTranslatedText("Add New")%></a></td>
								<td align="right" style="font-weight:bold;padding-right:10px"><%=localeObj.getTranslatedText("Search Result")%> : <%=noOfAnnouncements %></td></tr>
						</table>
					</div> 
                                  
           		<div class="data_table  MT20 MB20" style="width:100%" >
							<table class="subFormDesign">
					<thead>
				
					<tr >
							<th style="width:10%"><%=localeObj.getTranslatedText("Office Code")%></th>
							<th ><%=localeObj.getTranslatedText("Office Name")%></th>
							<th ><%=localeObj.getTranslatedText("SSC Name")%></th>
							<th><label><%=localeObj.getTranslatedText("Modified By")%></label></th>
							<th><label><%=localeObj.getTranslatedText("Modification Date")%></label></th>
							<th style="width:10%" ><%=localeObj.getTranslatedText("Action")%></th>    
						</tr>
						
				
						
				</thead>		
									<tbody>
									<%if(pager != null){ %>
									<pg:pager items="<%= pager.getItems().size() %>"
										maxPageItems="500"
										maxIndexPages="<%= pager.getMaxIndexPages() %>"
										isOffset="<%= pager.isOffset() %>"
										export="offset,currentPageNumber=pageNumber" scope="request"
										url="/ContentManager">

										<%for (int i = offset.intValue(),l = Math.min(i + pager.getMaxPageItems(), pager.getItems().size());i < l; i++)
										{%>
										<pg:item>
											<%=(String)pager.getItems().get(i)%>
										</pg:item>
										<%}%>
										<%if(pager.getItems().size() <= 0){%>
										<tr>
											<td colspan="7" bordercolor="#FFFFFF">
												<div align="center">
													<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
												</div></td>
										</tr>
										<%}%>
									</pg:pager>

									<pg:pager items="<%= pager.getItems().size() %>"
										maxPageItems="<%= pager.getMaxPageItems() %>"
										maxIndexPages="<%= pager.getMaxIndexPages() %>"
										isOffset="<%= pager.isOffset() %>"
										export="offset,currentPageNumber=pageNumber" scope="request"
										url="ContentManager">

										<pg:param name="<%=RequestAttributes.PARAMETER_KEY%>"
											value="<%=(String)((FormPageInterface)request.getAttribute(RequestAttributes.CONTENT_PAGE)).getKey()%>" />
										<%-- save pager offset during form changes --%>
										<input type="hidden" name="pager.offset" value="<%= offset %>">
										<%if(pager.getItems().size() > pager.getMaxPageItems()){%>
										<table class="data_table_pagination" width="100%" cellpadding="0" cellspacing="0">
											<tr >
												<td style="width: 10%">
													<div align="center">
														<span class="button_label"> <!-- Prev Page Link -->
															<pg:prev export="pageUrl">
																<a href="<%= pageUrl%>" class="button_label"
																	style="text-decoration: none; color: #ffffff; font-weight: bold"><%=localeObj.getTranslatedText("Previous")%></a>
															</pg:prev> </span>
													</div></td>
												<td >
													<div align="center">
														<pg:pages>
															<%if (pageNumber.intValue() < PagingInfo.SEARCH_INDEX_SIZE) {%> 
															&nbsp;
															<%}if (pageNumber == currentPageNumber) {%>
															<span style="color: orange"><b> <%= pageNumber %>
															</b>
															</span>
															<%} else {%>
															<a href="<%= pageUrl %>"
																style="text-decoration: none; color: #ffffff; font-weight: bold; padding: 5px;"><%= pageNumber %></a>
															<%}%>
															&nbsp;
													</pg:pages>
													</div></td>
												<td style="width: 10%">
													<div align="center">
														<span class="button_label"> <!-- Prev Page Link -->
															<pg:next export="pageUrl">
																<a href="<%= pageUrl%>" class="button_label"
																	style="text-decoration: none; color: #ffffff; font-weight: bold"><%=localeObj.getTranslatedText("Next")%></a>
															</pg:next> </span>
													</div></td>
											</tr>
										</table>
										<%}%>
									</pg:pager>
									<%}
							  	else
							  	{%>
								<tr>
									<td colspan="3" bordercolor="#FFFFFF">
										<div align="center">
											<strong><%=localeObj.getTranslatedText("No Record Found")%></strong>
										</div></td>
								</tr>
								<%} %>
								
								</tbody>
					</table>
    
           </div>
				
					 </form>
					</div>
				
		</div>
    
 
     