<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>file上传测试</title>
</head>
<body>
<form action="addTalent.action" enctype="multipart/form-data" method="post">
	<h4>addTalent</h4>
	talentId：<input type="text" name="id" value=""><br><br><br>
	agentId：<input type="text" name="agentId" value=""><br><br><br>
	姓名：<input type="text" name="name" value=""><br><br><br>
	性别：<input type="text" name="sex" value=""><br><br><br>
	生日：<input type="text" name="birthday" value=""><br><br><br>
	图片file1：<input type="file" name="imgFile" value=""><br><br><br>
	图片file2：<input type="file" name="imgFile" value=""><br><br><br>
	imageUrl1：<input type="text" name="imageUrl" value=""><br><br><br>
	imageUrl2:<input type="text" name="imageUrl" value=""><br><br><br>
	<input type="submit">
</form>
</body>
</html>