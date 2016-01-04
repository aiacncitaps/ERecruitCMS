<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<% 
String conPath  =  "ContentManager";
%>
<%
com.quix.aia.cn.imo.utilities.Pager pager=null;

if( request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT)!=null){
	pager = (com.quix.aia.cn.imo.utilities.Pager) request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
}else{
	pager = (com.quix.aia.cn.imo.utilities.Pager) request.getSession().getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.PAGING_OBJECT);
}
%> 
<pg:pager
    items="<%= pager.getItems().size() %>"
    maxPageItems="<%= pager.getMaxPageItems() %>"
    maxIndexPages="<%= pager.getMaxIndexPages() %>"
    isOffset="<%= pager.isOffset() %>"
    export="offset,currentPageNumber=pageNumber"
    scope="request"
    url="<%=conPath%>" >
	
	<pg:param name="<%=com.quix.aia.cn.imo.constants.RequestAttributes.PARAMETER_KEY%>" value="<%=(String)((com.quix.aia.cn.imo.interfaces.FormPageInterface)request.getSession().getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.CONTENT_PAGE)).getKey()%>"/>
	<%-- save pager offset during form changes --%>
  	<input type="hidden" name="pager.offset" value="<%= offset %>">
	<%if(pager.getItems().size() > pager.getMaxPageItems()){%>
	<div class="pagination">
	
		<pg:prev export="pageUrl">
		<a href="<%= pageUrl%>" class="prev">PREV.</a> 
		</pg:prev>
			
		<pg:pages>
			<%if (pageNumber.intValue() < com.quix.aia.cn.imo.constants.PagingInfo.SEARCH_INDEX_SIZE) {%>
			<%}if (pageNumber == currentPageNumber) {%>
				<a href="javascript:void(0);" class="current"><%= pageNumber %></a>
			<%} else {%>
				<a href="<%= pageUrl %>"><%= pageNumber %></a>
			<%}%>
		</pg:pages>
		
		<pg:next export="pageUrl">
			<a href="<%= pageUrl%>" class="next">NEXT</a>
		</pg:next>	
				
	</div>
	<%}%>
</pg:pager>