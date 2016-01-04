<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.quix.aia.cn.imo.utilities.PageObj"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import='com.quix.aia.cn.imo.data.user.*' %>
<%@ page import='java.util.*' %>
<%@ page import='java.text.*' %>
<%@ page import='com.quix.aia.cn.imo.constants.SessionAttributes' %>
<HTML>
<HEAD>

<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Welcome to IMO</TITLE>
<% String latestVersion = (String)application.getAttribute("latestVersion"); %>
<!-- Beginning of compulsory code for Menu-->

<!-- 
<link href="css/reset.css" rel="stylesheet" type="text/css" />

<link href="css/grid1200.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/jqtranform.css" type="text/css" media="all" />

<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.js"></script> 
<script type="text/javascript" src="js/jq_transform.js" ></script> 
<script type="text/javascript" src="js/simple-expand.js"></script> 
 
<script type="text/javascript" src="js/ts_picker.js"></script>
 
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.17/themes/base/jquery-ui.css">

<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
 -->


<link href="css/grid1200.css?t=<%=latestVersion%>" rel="stylesheet" type="text/css" />
<link href="css/main.css?t=<%=latestVersion%>" rel="stylesheet" type="text/css" />
<link href="css/jqtranform.css?t=<%=latestVersion%>" rel="stylesheet" type="text/css" media="all" />
<link href="css/jquery-ui.css?t=<%=latestVersion%>" rel="stylesheet" type="text/css" /> 

<script type="text/javascript" src="js/jquery-1.9.1.js?t=<%=latestVersion%>" ></script>
<script type="text/javascript" src="js/jq_transform.js?t=<%=latestVersion%>" ></script> 
<script type="text/javascript" src="js/simple-expand.js?t=<%=latestVersion%>"></script> 
<script type="text/javascript" src="js/jquery-ui.js?t=<%=latestVersion%>"></script> 

<link rel="stylesheet" type="text/css" href="css/addcontact.css?t=<%=latestVersion%>" />
<script type="text/javascript" src="js/default.js?t=<%=latestVersion%>"></script>
<link type="text/css" rel="stylesheet" href="css/popup.css?t=<%=latestVersion%>"/>

<style>
label{
  font-family: 'DINFactBoldRegular';
  font-size: 14px;
  text-transform: uppercase;
}

</style>

<script type="text/javascript">
function adjustFotter(){
	var clientHeight = $(document).height();
    var winHeight = $(window).height();
    if(clientHeight <= winHeight){
    	$("div.site-footer").css("position","absolute");
    }
}


$( document ).ready(function() {
    adjustFotter();
    
    document.onkeydown = function (e) {
        return ((e.which || e.keyCode ) != 116  || (window.event.ctrlKey && keycode != 82));
  	 
    };
});

window.history.forward(1);
function noBack(){
		window.history.forward(1);
}

</script>
</HEAD>
<%User user = ((com.quix.aia.cn.imo.data.user.User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ));
PageObj pageObj = (PageObj)request.getSession().getAttribute("pageObj");
%>
<body  onload="dwr.engine.setActiveReverseAjax(true);noBack();"  oncontextmenu="return false;" onpageshow="if (event.persisted) noBack();" onunload=""  onkeydown="return (event.keyCode != 116)">

	<div class="page-wrap" align="center">
					<%if(user!=null && pageObj != null &&!pageObj.getKey().equalsIgnoreCase("ViewEapplicationForm") ){ %>
				<jsp:include page="/main/header.jsp" flush="false"></jsp:include>
				<%} %>
			
				<jsp:include page="/main/content.jsp" flush="false"></jsp:include>
				
				<jsp:include page="/main/footer.jsp" flush="false"></jsp:include>
				
		
			
	</div>

</body>

</HTML>
