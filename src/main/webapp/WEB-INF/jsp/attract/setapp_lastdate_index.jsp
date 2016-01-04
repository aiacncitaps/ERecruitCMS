<%@page import="org.nutz.lang.util.NutMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc-top.jsp" %>
<link rel="stylesheet" href="static/css/lightbox.css">
<script type="text/javascript" src="static/js/lightbox.js"></script>
<h2 class="page_title">设置APP最后使用时间</h2>

<section class="form">
	<div style="border:0px solid red;height:300px;margin-left:600px;">
		<form id="queryForm" style="margin-top:100px;">
				APP最后一次使用时间<br><br><input type="text" value="1" style="border:1px solid gray;" id="lastTime" class="sang_Calender"/><br><br>
				<input type="button" value="保存时间" style="border:1px solid gray;background:gray;width:100px;margin-left:30px;" onclick="saveAppLastDate();">
		</form>
	</div>
</section>

<script type="text/javascript" src="static/js/datetime.js"></script>
<script type="text/javascript">
function listReload(){
	showLoading('加载中...');
	$.ajax({
		url:'getAppLastDate.action',//url
		type:'post',//提交方式
		data:'',//参数
		dataType:'json',	//返回类型
		success:function(json){//正确返回执行的函数
			if(json.success){
				closeLoading();
				$('#lastTime').val(json.result);
			}else{
				//alert(window._shareId);
			}
		},
		error:function(json){//错误返回执行的函数
			
		}
	});
}
function saveAppLastDate(){
	$.ajax({
		url:'setAppLastDate/'+$('#lastTime').val(),//url
		type:'post',//提交方式
		data:'',//参数
		dataType:'json',	//返回类型
		success:function(json){//正确返回执行的函数
			if(json.success){
				closeLoading();
				alert(json.msg);
				location.href=location.href;
			}else{
				//alert(window._shareId);
			}
		},
		error:function(json){//错误返回执行的函数
			
		}
	});	
}
$(function(){
	listReload();
});
</script>
</body>
</html>