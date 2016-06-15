<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.ApplicationAttribute"%>
<%
String version = "";
if(application.getAttribute(ApplicationAttribute.LATEST_JS_CSS_VERSION)!=null)
	version = (String)application.getAttribute(ApplicationAttribute.LATEST_JS_CSS_VERSION);
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
%>

 <div class="site-footer">
	<div class="container_12">
			<div class="grid_12 MT25" style="margin-left: -2%;" >
			 Copyright @ 2015, AIA Group Limited and its subsidiaries. All rights reserved. <div style="float:right;"> <a  style="color: white; font-size: 12px; text-decoration: none; " href="Terms&Condition/Tearms.docx"><%=  localeObj!=null?localeObj.getTranslatedText("Terms & Condition") : "Terms & Condition"%> </a> &nbsp;&nbsp;&nbsp;&nbsp; <a style="color: white; font-size: 12px; text-decoration: none;" href="Terms&Condition/Preivacy.docx"><%=  localeObj!=null ? localeObj.getTranslatedText("Privacy Statement") : "Privacy Statement" %>    </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      Version : 1.45 <%-- <%=version %> --%></div>
		</div>
	</div>
 </div>

<div id="ajaxLoader">
  	<div  class="lightbox" style="top:200px;right:42%;width:130px;border:1px solid #bfbfbf;">
	     <div id="tabs" class="tabs">
		     <div id="tabs-1" style="text-align:center">
		    	<img src="images/loading.gif" width="100px" height="100px"/>
		    	<div class="clear pad5"></div>
		     </div>
	     </div>
    </div>
</div>
<%
String str="";
	if(request.getAttribute("CacheName")!=null){
		str=(String)request.getAttribute("CacheName");
%>
<script>


		$.ajax({
			url : 'CacheRest?moduleName=<%=str %>',
			type: "POST",
		   	processData: false,
		   	contentType: false,
		});

	

</script>
<%}%>