
<!-- Session Shouldn't be null-->
<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.CONTENT_PAGE) != null){%>
	
	<!-- Include the actual page contents-->
	<jsp:include page="<%=(String)((com.quix.aia.cn.imo.interfaces.FormPageInterface)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.CONTENT_PAGE)).getPath()%>" flush="false"></jsp:include>
	
<%}%>

