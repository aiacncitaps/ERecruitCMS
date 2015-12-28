<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Facetalk CMS 管理平台</title>
<!-- 导入jquery -->
<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/2.0.3/jquery-2.0.3.min.js"></script>
<!-- 把user id复制到一个js变量 -->
<script type="text/javascript">
	
	var me = '<%=session.getAttribute("currUserObj") %>';
	var base = '${base}';
	$(function() {
		$("#login_button").click(function() {
			$.ajax({
				url : base + "/attract/login",
				type: "POST",
				data:$('#loginForm').serialize(),
				error: function(request) {
					alert("Connection error");
				},
				dataType:"json",
				success: function(data) {
					if (data == true) {
						//location.reload();
						location.href = './resourceList?type=1';
					} else {
						alert("登陆失败,请检查账号密码")
					}
				}
			});
			return false;
		});
		if (me != "null") {
			location.href = './resourceList?type=1';
		} else {
			$("#login_div").show();
			$("#user_info_div").hide();
		}
	});
</script>
</head>
<body>
<div id="login_div" style="display:none">
	<form action="#" id="loginForm" method="post">
		用户名 <input name="name" type="text" autocomplete="off">
		密码 <input name="password" type=text id="pass" onfocus="this.type='password'" autocomplete="off">
		<button id="login_button">提交</button>
	</form>
</div>
<div id="user_info_div" style="display:none">
	<p id="userInfo"></p>
	<a href="${base}/attract/logout">登出</a>
</div>
</body>
</html>