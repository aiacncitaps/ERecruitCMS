<%--
  - Author(s):          KHYATI
  - Date:               15-MAY-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        
--%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.mapper.ChannelMaintenance"%>
<%@page import="com.quix.aia.cn.imo.data.channel.*"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>


<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);

Channel classObj = new  Channel();

boolean modifyFlag = false;
FormObj formDetail = null;


if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
{
	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
	
}
classObj = (Channel)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);

if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
{
	modifyFlag=true;
}

%>

<div id="maincontainer">
			<div class="head">
				<h2 align="center"><%if(modifyFlag == true){ %>
                          <%=localeObj.getTranslatedText("Edit Channel")%>
                    <%} else {%>
   				           <%=localeObj.getTranslatedText("Add Channel")%>
   					<%} %></h2>
			</div>
<div class="content" style="background-color:#ffffff;">
    <form method="post" action="FormManager" name="administrationForm">	
    <input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">				
   		<input type="hidden" id="actionType" name="actionType">
        <input type="hidden" id="channelCode" name="channelCode"  value="<%=classObj.getChannelCode() %>" >
   				   
                    <table class="formDesign">  
                    <tr>
			<td colspan="2" style="text-align: center"><span style="color:#ec2028;">
			 
                   <%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) == null){  %> 
					 
                   <%}else{%>  <%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
                        <%}%>       
				     </span></td>                                  	
			
			</tr>
                    
      <tr>           
	     <td>	                                    				
	      <label><%=localeObj.getTranslatedText("Channel Name")%></label>
	     </td>
	                   			
	 	<%if(request.getAttribute(RequestAttributes.ERROR_OBJ) != null){%>		
	                    <td>	
	                    <input  name="<%=ChannelMaintenance.Name%>"  maxlength="250" type="text"  value="<%=(request.getParameter(ChannelMaintenance.Name))%>" class="textObj" />
	    	         </td>            
	                    <%} else {%>
	                    <td>
	    	             <input name="<%=ChannelMaintenance.Name%>"  maxlength="250"  value="<%=com.quix.aia.cn.imo.utilities.SecurityAPI.encodeHTML(classObj.getChannelName())%>"    type="text" class="textObj" />
	    	             </td>   
	    	             <%} %>              
	                   				
                                                                        			   
                 </tr>    
                   <tr  > 
                                      			    	                        
                    <td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                    <%if(modifyFlag == true){ %>
                    	<a href="#"   class="ML10 btn1 " id="modifyButton" name="modifyButton" onclick="savedata()"><%=localeObj.getTranslatedText("Modify")%></a>
                    <%}else{%>
                     	<a href="#"   class="ML10 btn1" id="insertButton" name="insertButton" onclick="savedata()"><%=localeObj.getTranslatedText("Submit")%></a>
                     <%}%>
                     <a href="ContentManager?key=Channel"   class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a> 
                    </td>                                     			   
                 </tr>
             </table>          
                              	 			
             </form>
		</div>
</div>
				
 
 <script language="javascript">
 
  function savedata()
  {
	  document.administrationForm.actionType.value = "SUBMIT" ;
	  document.administrationForm.submit();
  }
  
  </script>
     