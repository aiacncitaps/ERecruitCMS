<%--
  - Author(s):          Abinas
  - Date:               15-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:         Presenter form created
  - 20-May-2015          upload button issue fixed.
--%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.mapper.*"%>
<%@page import="com.quix.aia.cn.imo.data.department.*"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.data.presenter.Presenter"%>
<%@ page import='java.util.*' %>
<%@ page import='java.text.*' %>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.data.category.PresenterCategory"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/ImoUtilityData.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<style>
@media only screen and (max-device-width: 1024px) and (min-device-width: 768px){.class_limit {width: 259px;}}
@media screen and (min-width:0\0) and (min-resolution: +72dpi) {.class_limit{width:290px;}}
</style>
<%
int buCode = 0,distCode = 0,branch=0;
String cityCode = 0+"",sscCode = 0+"",office=0+"";
boolean modifyFlag = false,errorFlag = false;
FormObj formDetail = null;
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
Department classObj = new  Department();
User userObj=(User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ);
Date today = new Date();
SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
String todayDate = formatter.format(today);
String modifyByName = "";
String lastModifyByName = "";
modifyByName = userObj.getStaffLoginId();
Presenter presenterObj=new Presenter();

PresenterMaintenance obj=new PresenterMaintenance();
ArrayList<PresenterCategory> arrPresenterCategory = new ArrayList();
arrPresenterCategory=obj.getAllPresenterCategory(request);
String strartDate="";
String endDate="";

buCode=userObj.getBuCode();
distCode=userObj.getDistrict();
branch=userObj.getBranchCode();
cityCode=userObj.getCityCode();
sscCode=userObj.getSscCode();
office=userObj.getOfficeCode();

ImoUtilityData imoUtilityDate=new ImoUtilityData();
if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
	presenterObj = (Presenter)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);
}

if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag = true;
	buCode = Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(presenterObj.getBuCode())));
	distCode = Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(presenterObj.getDistCode())));
	branch=Integer.parseInt(SecurityAPI.encodeHTML(String.valueOf(presenterObj.getBranchCode())));
	cityCode =(SecurityAPI.encodeHTML(String.valueOf(presenterObj.getCityCode())));
	sscCode = (SecurityAPI.encodeHTML(String.valueOf(presenterObj.getSscCode())));
	office = (SecurityAPI.encodeHTML(String.valueOf(presenterObj.getOfficeCode())));
	
}
if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
	errorFlag = true;
	buCode = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter(PresenterMaintenance.BU_PARAM)));
	distCode = Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter(PresenterMaintenance.DISTRICT_PARAM)));
	branch=Integer.parseInt(SecurityAPI.encodeHTML(request.getParameter("branch")));
	cityCode = (SecurityAPI.encodeHTML(request.getParameter(PresenterMaintenance.CITY_PARAM)));
	sscCode = (SecurityAPI.encodeHTML(request.getParameter(PresenterMaintenance.SSC_PARAM)));
	office=(SecurityAPI.encodeHTML(request.getParameter("office")));
}
%>
<style type="text/css">
div#spinner
{
    display: none;
    width:100px;
    height: 100px;
    position: fixed;
    top: 50%;
    left: 50%;
    background:url(images/loading.gif) no-repeat center #fff;
    text-align:center;
    padding:10px;
    font:normal 16px Tahoma, Geneva, sans-serif;
    border:1px solid #666;
    margin-left: -50px;
    margin-top: -50px;
    z-index:2;
    overflow: auto;
}
</style>
<script type="text/javascript">
var spinnerVisible = false;
function showProgress() {
    if (!spinnerVisible) {
        $("div#spinner").fadeIn("slow");
        spinnerVisible = true;
        $("body").find("*").attr("disabled", "disabled");
        $("body").find("a").click(function (e) { e.preventDefault(); });
        document.getElementById('created_by').setAttribute("disabled","disabled");
    }
};
function hideProgress() {
    if (spinnerVisible) {
        var spinner = $("div#spinner");
        spinner.stop();
        spinner.fadeOut("fast");
        spinnerVisible = false;
        $("body").find("*").removeAttr("disabled");
        $("body").find("a").unbind("click");
        document.getElementById('created_by').setAttribute("enabled","enabled");
    }
};
</script>
<script>
$( document ).ready(function() {
	<%
	if(userObj.getUserType().equals("AD") ){ %> 
			getBU('<%=buCode %>','L');
	<% }%>
	<%if(userObj.getUserType().equals("AD") || userObj.isBuLevel() ){ %>
					getDistrict('<%=buCode %>','<%=distCode %>','L');
				
				<% }%>
				
				<%if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel() || userObj.isBranchLevel() ){ %>
					getBranch('<%=distCode %>','<%=branch %>','L');
			
				<% }else{%>
				
					<%if(!userObj.isCityLevel()){ %>
						getBranch('<%=distCode %>','<%=branch %>','L');
					<%} %>
				<% }%>
				
				
				<%if(userObj.getUserType().equals("AD") || userObj.isCityLevel() || userObj.isBranchLevel() || userObj.isDistrictLevel() || userObj.isBuLevel() ){ %>
				 
					getCity('<%=branch %>','<%=cityCode %>','L');
				<% }%>
	
				
			
	<%if(userObj.getUserType().equals("AD") ||userObj.isSscLevel()  || userObj.isBranchLevel()  || userObj.isDistrictLevel() ||  userObj.isBuLevel() || userObj.isCityLevel() ){%>
		
		getSSC('<%=cityCode %>','<%=sscCode %>','L');
	<% }%>
	
	<%if(userObj.getUserType().equals("AD")  ||userObj.isSscLevel()  || userObj.isOfficeLevel()   || userObj.isDistrictLevel() ||  userObj.isBuLevel() || userObj.isCityLevel() ){%>
	
	getOffice('<%=sscCode %>','<%=office %>');
<% }%>
	
	
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
function submitForm(){
	document.presenterForm.submit();
}
</script>


   	<div id="maincontainer">
   	<div class="head"><h2 align="center">
   					<%if(modifyFlag == true){ %>
   					<%=localeObj.getTranslatedText("Edit Presenter")%>
   					<%}else{ %>
   					<%=localeObj.getTranslatedText("Add Presenter")%>
   					<%} %>
   					
   					</h2></div>
   		<div class="container_12 clearfix" align="center">
   			   
   					 	
   							<div class="content" style="background-color:#ffffff;" >	 
   									  <form action="FormManager" name="presenterForm" method="post" class="PT20">
   									 <input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
   									  <input type="hidden" name="<%=FormInfo.HIDDEN_ACTION%>" value="SUBMIT"/>
                                		<input type="hidden" name="presenter_id" id="presenter_id" value="<%=presenterObj.getPresenterCode() %>"/>
                           				
                           				 <table class="formDesign" style="width:50%">
                           				 <tr>
									<td colspan="2" style="text-align: center;color:#ec2028;">
									<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){%>
										<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
									<%	
				                     } %>
									</td>
								</tr>
								<!-- presenter category -->
                                				<tr  >                         
                                    	<td style="width:44%;" ><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Presenter Category")%></label>	</td>                                    				
                                        <td align="left">	
                                        				<select name="<%=PresenterMaintenance.PRESENTER_CATEGORY_PARAM %>" id="<%=PresenterMaintenance.PRESENTER_CATEGORY_PARAM %>"  class="comboObj"  >
			                                				<option value="%"><%=localeObj.getTranslatedText("Select")%></option>
			                                				<%
			                                				String presenter_cat_code="";
			                                				if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  
		    					                          		presenter_cat_code=request.getParameter(PresenterMaintenance.PRESENTER_CATEGORY_PARAM );
		    					                          	}else{
		    					                          		presenter_cat_code=presenterObj.getCategory()+"";
		    					                          	}
			                                				for(int i=0;i<arrPresenterCategory.size();i++){
			                                					PresenterCategory categoryObj=(PresenterCategory)arrPresenterCategory.get(i);
			    					                          	
			                                				%>
			                                				<option value="<%=categoryObj.getPresenterCategoryCode()%>" <%=presenter_cat_code.equals(categoryObj.getPresenterCategoryCode()+"")?"selected":"" %>><%=SecurityAPI.encodeHTML((categoryObj.getPresenterCategoryName())) %></option>
			                                				<%} %>                                      	
                                	 			        </select>
                            	 			</td>                                     			   
                           			    </tr>
                           				 <!-- presnter name -->	
											<tr>                         
                                    				<td style="" ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Presenter Name")%></label></td>                        				
                                        			<td>	
                                        			 <%
					                          	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  %>
                                        				<input name="<%=PresenterMaintenance.NAME_PARAM %>" id="<%=PresenterMaintenance.NAME_PARAM %>" type="text" class="textObj" maxlength="250"  value="<%=request.getParameter(PresenterMaintenance.NAME_PARAM) %>"/>
                                        		<%}else{ %>
                                        				<input name="<%=PresenterMaintenance.NAME_PARAM %>" id="<%=PresenterMaintenance.NAME_PARAM %>" type="text" class="textObj"  value="<%=SecurityAPI.encodeHTML(presenterObj.getPresenterName() )%>" maxlength="250" />
                                        		<%} %>
                                       	 			</td>                                     			   
                                      			    </tr>
                                      			       <!-- -start date -->
                                      			    	<tr>
	                                     		<td style=""><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Start Date")%></label></td>
	                                     		<td align="left">
	                                     		<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  %>
														<input name="<%=PresenterMaintenance.START_DATE_PARAM %>" id="<%=PresenterMaintenance.START_DATE_PARAM %>" type="text" class="textObj datepicker" value="<%=request.getParameter(PresenterMaintenance.START_DATE_PARAM ) %>" readonly="readonly"/>
													<%}else{ %>
													<input name="<%=PresenterMaintenance.START_DATE_PARAM %>" id="<%=PresenterMaintenance.START_DATE_PARAM %>" type="text" class="textObj datepicker" value="<%=SecurityAPI.encodeHTML(presenterObj.getStart_date()!=null?LMSUtil.convertDateToString(presenterObj.getStart_date()):"")%>" readonly="readonly"/>
													<%} %>
	                                     		
	                                     		</td>
	                                     	</tr>
	                                     	<tr>
	                                     		<td style=""><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Expiry Date")%></label></td>
	                                     		<td align="left">
	                                     		<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  %>
	<input name="<%=PresenterMaintenance.EXPIRY_DATE_PARAM %>" id="<%=PresenterMaintenance.EXPIRY_DATE_PARAM %>" type="text" class="textObj datepicker" value="<%=request.getParameter(PresenterMaintenance.EXPIRY_DATE_PARAM )%>" readonly="readonly"/>
													<%}else{ %>
													<input name="<%=PresenterMaintenance.EXPIRY_DATE_PARAM %>" id="<%=PresenterMaintenance.EXPIRY_DATE_PARAM %>" type="text" class="textObj datepicker" value="<%=SecurityAPI.encodeHTML(presenterObj.getExpiry_date()!=null?LMSUtil.convertDateToString(presenterObj.getExpiry_date()):"")%>" readonly="readonly"/>
													<%} %>
	                                     		</td>
	                                     	</tr>
	                                     	<!-- description -->
	                                     	<tr>                         
                                    				<td style="vertical-align:middle" ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("Presenter Description")%></label> </td>          				
                                        			<td>
                                        			<% if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  %>
													<textarea  maxlength="1000" rows="4" cols="50" name="<%=PresenterMaintenance.DESC_PARAM %>" id="<%=PresenterMaintenance.DESC_PARAM %>" class="textObj"><%=request.getParameter(PresenterMaintenance.DESC_PARAM )%></textarea>
													<%}else{ %>
													<textarea  maxlength="1000" rows="4" cols="50" name="<%=PresenterMaintenance.DESC_PARAM %>" id="<%=PresenterMaintenance.DESC_PARAM %>" class="textObj"><%=SecurityAPI.encodeHTML(presenterObj.getPresenterDescription()) %></textarea>
													<%} %>
                                        				
                                       	 			</td>                                     			   
                                      			    </tr>
                                      			    <!-- presenter visibility-->
                                				<tr  >                         
                                    	<td style="" ><label ><span style="color:#ec2028;">* </span><%=localeObj.getTranslatedText("Presenter is visible to")%></label>	</td>                                    				
                                        <td align="left">	
                                        				<select name="<%=PresenterMaintenance.PRESENTER_VISIBILITY_PARAM %>" id="<%=PresenterMaintenance.PRESENTER_VISIBILITY_PARAM %>"  class="comboObj"  >
			                                				<option value="%"><%=localeObj.getTranslatedText("Select")%></option>
			                                				<%
			                                				String presenter_visibility="";
			                                				if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  
			                                					presenter_visibility=request.getParameter(PresenterMaintenance.PRESENTER_VISIBILITY_PARAM );
		    					                          	}else{
		    					                          		presenter_visibility=SecurityAPI.encodeHTML(presenterObj.getVisibility()+"");
		    					                          	}
			                                				%>
			                                				<option value="BU" <%=presenter_visibility.equals("BU")?"selected":""%>><%=localeObj.getTranslatedText("Bu")%></option>
			                                				<option value="DISTRICT" <%=presenter_visibility.equals("DISTRICT")?"selected":""%>><%=localeObj.getTranslatedText("DISTRICT")%></option>
			                                				<option value="BRANCH" <%=presenter_visibility.equals("BRANCH")?"selected":""%>><%=localeObj.getTranslatedText("BRANCH")%></option>
			                                				<option value="CITY" <%=presenter_visibility.equals("CITY")?"selected":""%>><%=localeObj.getTranslatedText("City")%></option>
			                                				<option value="SSC" <%=presenter_visibility.equals("SSC")?"selected":""%>><%=localeObj.getTranslatedText("SSC")%></option>
			                                				<option value="OFFICE" <%=presenter_visibility.equals("OFFICE")?"selected":""%>><%=localeObj.getTranslatedText("Office")%></option>
			                                				
                                	 			        </select>
                            	 			</td>                                     			   
                           			    </tr>
                           			    <!-- BU -->
                                      			    		<tr>                         
                                			<td style=""  ><span style="color:#ec2028;">* </span>                                    				
                                 				<label ><%=localeObj.getTranslatedText("BU")%></label>
                                			</td>
                                  			<td >
                                  			<% if(userObj.getUserType().equals("AD")){ %>	
                                				<select name="<%=PresenterMaintenance.BU_PARAM %>" id="<%=PresenterMaintenance.BU_PARAM %>"  class="comboObj" onchange="getDistrict(this.value,0,'E');" >
                               						<option value="0"><%=localeObj.getTranslatedText("Select")%></option>	 
                               	 				 </select>
                               	 				 <%}else{ %>
                               	 				 <select name="<%=PresenterMaintenance.BU_PARAM %>" id="<%=PresenterMaintenance.BU_PARAM %>"  class="comboObj" onchange="getDistrict(this.value,0,'E');" >
                               						<option value="<%=userObj.getBuCode() %>" ><%=imoUtilityDate.getBuCode(userObj.getBuCode()) %></option> 
                               	 				 </select>
                               	 				 <%} %>
                                 	 		</td>                                     			   
                                  		</tr>
                        		 		<tr>                         
                                			<td style=""  >                                    				
                                 				<label ><%=localeObj.getTranslatedText("District")%></label>
                                			</td>
                                  			<td >
                                  				<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel()){ %>	
                                				<select name="district" id="district" class="comboObj" onchange="getBranch(this.value,'<%=branch %>','E');"  >
                               						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	 
                               	 				 </select>
                               	 				 <%}else{ %>
                               	 				 <select name="<%=PresenterMaintenance.DISTRICT_PARAM %>" id="<%=PresenterMaintenance.DISTRICT_PARAM %>"  class="comboObj"  onchange="getBranch(this.value,'<%=branch %>','E');">
                               						<option value="<%=userObj.getDistrict() %>" ><%= imoUtilityDate.getDistCode(userObj.getDistrict()) %></option>
                               	 				 </select>
                               	 				 <%} %>
                                 	 		</td>                                     			   
                                  		</tr>	
                                  		
                                  		
                                  		
                                  		<tr>                         
                                			<td style=""  >                                    				
                                 				<label ><%=localeObj.getTranslatedText("Branch")%></label>
                                			</td>
                                  			<td >
                                  				<% if(userObj.getUserType().equals("AD") || userObj.isBuLevel()){ %>	
                                				<select name="branch" id="branch" class="comboObj" onchange="getCity(this.value,'<%=cityCode %>','E');"  >
                               						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	 
                               	 				 </select>
                               	 				 <%}else{ %>
                               	 				 <select name="branch" id="branch"  class="comboObj"  onchange="getCity(this.value,'<%=cityCode %>','E');">
                               						<option value="<%=userObj.getBranchCode() %>" ><%= imoUtilityDate.getBranchCode(userObj.getBranchCode()) %></option>
                               	 				 </select>
                               	 				 <%} %>
                                 	 		</td>                                     			   
                                  		</tr>	
                                  		
                        		 		 <tr>                         
                            				<td style=""  >                                    				
                             				<label ><%=localeObj.getTranslatedText("City")%> </label>
                            				</td>
                                			<td>
                                			<% if(userObj.getUserType().equals("AD") || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                               				<select name="<%=PresenterMaintenance.CITY_PARAM %>" id="<%=PresenterMaintenance.CITY_PARAM %>"  class="comboObj"  onchange="getSSC(this.value,'<%=sscCode %>','E');">
                              						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                              	 			 </select>
                              	 			 <%}else{ %>
                              	 			 <select name="<%=PresenterMaintenance.CITY_PARAM %>" id="<%=PresenterMaintenance.CITY_PARAM %>"  class="comboObj" >
                              						<option value="<%=userObj.getCityCode() %>" ><%=imoUtilityDate.getCityName(userObj.getCityCode()) %></option>	                                       	
                              	 			 </select>
                              	 			 <%} %>
                               	 			</td>                                     			   
                                  		 </tr>
                                  			   
                 		 				<tr  >                         
                         				<td style=""  >                                  				
                          				<label ><%=localeObj.getTranslatedText("SSC")%></label>
                         				</td>
                             			<td>	
                             			<% if(userObj.getUserType().equals("AD") || userObj.isCityLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>
                             				<select name="<%=PresenterMaintenance.SSC_PARAM %>" id="<%=PresenterMaintenance.SSC_PARAM %>"  class="comboObj" onchange="getOffice(this.value,0);"   >
                            						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                            	 				 </select>
                            	 				 <%}else{ %>
                            	 				 <select name="<%=PresenterMaintenance.SSC_PARAM %>" id="<%=PresenterMaintenance.SSC_PARAM %>"  class="comboObj"  >
                            						<option value="<%=userObj.getSscCode() %>" ><%=imoUtilityDate.getSscName(userObj.getSscCode()) %></option>                                       	
                            	 				 </select>
                            	 				 <%} %>
                            	 			</td>                                     			   
                           			    </tr>
                           			    
                           			    
                           			     <tr  >                         
                         				<td style="border: none;"  >                                  				
                          				<label ><%=localeObj.getTranslatedText("Office")%></label>
                         				</td>
                             			<td>
                             			<% if(userObj.getUserType().equals("AD") || userObj.isCityLevel() || userObj.isDistrictLevel() || userObj.isBuLevel()){ %>	
                             				<select name="office" id="office"  class="comboObj"  >
                            						<option value="0"><%=localeObj.getTranslatedText("ALL")%></option>	                                       	
                            	 				 </select>
                            	 				 <%}else{ %>
                            	 				 <select name="office" id="office"  class="comboObj" >
                            						<option value="<%=userObj.getOfficeCode() %>" ><%=imoUtilityDate.getOfficeName(userObj.getOfficeCode()) %></option>	                                       	
                            	 				 </select>
                            	 				 <%} %>
                            	 			</td>                                     			   
                           			    </tr> 
                                      			    <!-- END --> 
                                      		<!-- Thumbnail image -->
                            		 				<tr>
                            		 					<td style=""  >	                                    				
	                                    				<label><%=localeObj.getTranslatedText("THUMBNAIL IMAGE")%></label>
                                    				</td>
                            		 					<td style="">
                            		 						<input name="filename1" id="filename1" type="file" class="fileObj" onChange="uploadThumbnail();"  style="width:223px;" />          
                            		 					</td>
                            		 					
                            		 				</tr>
                            		 				
                            		 				 <tr >
											              <td style=""></td>
											              <td style=""><div id="file_name1"><%=presenterObj.getImage_fileName()==null?"":presenterObj.getImage_fileName() %></div></td>
										              </tr>	        
                                      			     
                                        		<!-- Material Upload -->
										              	<tr>
                            		 					<td style=""  >	                                    				
	                                    				<label><%=localeObj.getTranslatedText("Material Upload")%></label>
                                    				</td>
                            		 					<td style="">
                            		 						<input name="filename2" id="filename2" type="file" class="fileObj" onChange="uploadMaterial();" style="width:223px;"  />                
                         								   <div id="spinner">
       														 Loading...
   															 </div>
                            		 					</td>
                            		 					
                            		 				</tr>
                            		 				
                            		 				 <tr >
											              <td style=""></td>
											              <td style=""><div id="file_name2"><%=presenterObj.getFileName()==null?"":presenterObj.getFileName() %></div></td>
										              </tr>
<!-- 										              <tr>                          -->
<%--                                     				<td style="" ><label><%=localeObj.getTranslatedText("Created By")%></label></td>	                                    				 --%>
<!--                                         			<td style=""> -->
                                        			<%	 
//                                         				String createdBy = "";
// 					                       	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
// 					                       		createdBy =  request.getParameter(PresenterMaintenance.CREATED_BY_PARAM );
// 					                       	}
// 					                       	else if(modifyFlag){
// 					                       		createdBy = SecurityAPI.encodeHTML(String.valueOf(presenterObj.getCreated_by()));
// 					                       	}
// 					                       	else{
// 					                       		createdBy = SecurityAPI.encodeHTML(userObj.getStaffName());
// 					                       	} 
					                       
				                        %>	 
<%--                                         				<input name="<%=PresenterMaintenance.CREATED_BY_PARAM %>" id="<%=PresenterMaintenance.CREATED_BY_PARAM %>" type="text" class="textObj"  value="<%=createdBy %>" readonly="readonly" /> --%>
                                       	 	
<!--                                        	 			</td>                                     			    -->
<!--                                       			    </tr> -->
													 <tr >
											              <td style=""></td>
											              <td style=""></td>
										              </tr>
													  	<tr>
                                        			<td colspan="2" style="text-align:center;margin-bottom:20px;">
                                        			<%if(modifyFlag){%>
                                        				<a href="javascript:submitForm()" class="ML10 btn1 "   ><%=localeObj.getTranslatedText("Modify")%></a>
                                        			<%}else{ %>
                                        			<a href="javascript:submitForm()" class="ML10 btn1 " style="margin-bottom:20px;"  ><%=localeObj.getTranslatedText("Submit")%></a>
                                        			<%} %>	
                            		 					<a href="ContentManager?key=PresenterMaintenance" class="ML10 btn1" ><%=localeObj.getTranslatedText("Back")%></a>
                                       	 			</td>
                                       	 			</tr>
                                       	 			  <tr >
											              <td style=""></td>
											              <td style=""></td>
										              </tr>
                           				 </table>
                           			 </form>
                           	</div>		 	 
         </div>
     </div>                      	
 
  <script> 
  
 // $('#uploadThumbnail').on('click', function(e) {
	   function uploadThumbnail(){
		$('#ajaxLoader').find(".lightbox").show();
		var imageFile1 = $('input[type=file]').get(0).files[0];
		if(imageFile1!=undefined){
		var file_name=imageFile1.name;
		if((file_name.indexOf('.jpg')>-1) || (file_name.indexOf('.png')>-1) || (file_name.indexOf('.gif')>-1) || (file_name.indexOf('.JPG')>-1) || (file_name.indexOf('.PNG')>-1) || (file_name.indexOf('.GIF')>-1)){
			 //showProgress();
		if(imageFile1==undefined)
			imageFile1 = $('#file_name1').html();
		var fd = new FormData();
		fd.append('filename1', imageFile1);
		var image_name1=$("#filename1").val();
		  $.ajax({
				url : 'UploadMaterial?usedFor=Presenter&image_name='+image_name1,
				type: "POST",
				data: fd,
			   	processData: false,
			   	contentType: false,
			}).done(function(respond){
				//window.location.href = "ContentManager?key=PresenterMaintenance";
				 $('#ajaxLoader').find(".lightbox").hide();
			});
		  
		}else{
			 $('#ajaxLoader').find(".lightbox").hide();
			alert("Please upload png/jpg/gif type file");
			$('#filename1').val('');
		}
		}else{
			 $('#ajaxLoader').find(".lightbox").hide();
			alert("Please Upload file");
		}
		
  }
 // });

 // $('#uploadMaterial').on('click', function(e) {
	 function uploadMaterial(){
		 $('#ajaxLoader').find(".lightbox").show();
		var imageFile2 = $('input[type=file]').get(1).files[0];
		if(imageFile2!=undefined){
		var file_name=imageFile2.name;
		if((file_name.indexOf('.pdf')>-1) || (file_name.indexOf('.PDF')>-1)){
			
		if(imageFile2==undefined)
			imageFile2 = $('#file_name2').html();
		var fd = new FormData();
			fd.append('filename2', imageFile2);
			var image_name2=$("#filename2").val();
		  $.ajax({
				url : 'UploadMaterial?usedFor=Presenter&image_name='+image_name2,
				type: "POST",
				data: fd,
			   	processData: false,
			   	contentType: false,
			}).done(function(respond){
				//window.location.href = "ContentManager?key=PresenterMaintenance";
				$('#ajaxLoader').find(".lightbox").hide();
			});
		  hideProgress();
		}else{
			$('#ajaxLoader').find(".lightbox").hide();
			alert("Please upload .pdf type file");
			$('#filename2').val('');
		}
	 
	 }else{
		 $('#ajaxLoader').find(".lightbox").hide();
		 alert("Please Upload file");
	 }
	
}
  </script>
 	
     