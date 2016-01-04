<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Facetalk CMS 管理平台</title>
<!-- 把user id复制到一个js变量 -->
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=PT+Sans:400,700'>
<link rel="stylesheet" href="static/css/reset.css">
<link rel="stylesheet" href="static/css/supersized.css">
<link rel="stylesheet" href="static/css/style.css">
<script type="text/javascript" src="https://lib.sinaapp.com/js/jquery/2.0.3/jquery-2.0.3.min.js"></script>
<!-- 把user id复制到一个js变量 -->
<script type="text/javascript">
	
	var me = '<%=session.getAttribute("currUserObj") %>';
	var base = '${base}';
	$(function() {
		$("#login_button").click(function() {
			var userName=$('.username').val();
			var pwd=$('.password').val();
			if(userName == ''){
				alert('请输入用户名！');
				return;
			}if(pwd == ''){
				alert('请输入密码！');
				return;
			}
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
	<div class="page-container">
		<form action="#" id="loginForm" method="post">
			 <input name="name" type="text" class="username" autocomplete="off" placeholder="用户名">
			 <input name="password" type=text id="pass" class="password" onfocus="this.type='password'" placeholder="密码" autocomplete="off">
			<button id="login_button">登录</button>
		</form>
	</div>
</div>
<div id="user_info_div" style="display:none">
	<p id="userInfo"></p>
	<a href="${base}/attract/logout">登出</a>
</div>
<script src="static/js/jquery-1.8.2.min.js"></script>
<script src="static/js/supersized.3.2.7.min.js"></script>
<script src="static/js/supersized-init.js"></script>
<script src="static/js/scripts.js"></script>

</body>
</html>