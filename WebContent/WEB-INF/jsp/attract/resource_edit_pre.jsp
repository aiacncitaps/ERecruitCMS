<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tohours.imo.bean.Resource" %>
<%@ page import="com.tohours.imo.util.TohoursUtils" %>
<%
	Resource resource = (Resource)request.getAttribute("obj");
	String id = resource.getId();
	Long type = resource.getType();
	String title = TohoursUtils.dealNull(resource.getTitle());
	String content = TohoursUtils.dealNull(resource.getContent());
	String fileNames = TohoursUtils.dealNull(resource.getFileNames());
	String filePaths = TohoursUtils.dealNull(resource.getFilePaths());
%>


<%@ include file="inc-top.jsp" %>
<h2 class="page_title">管理首页资源</h2>
<section class="form">
<form id="myForm">
	<input type="hidden" name="id" value="<%= id %>" />
	<input type="hidden" name="type" value="<%= type %>"/>
	<div class="input_box">
		<label class="label">标题</label>
		<div class="input_border radius"><input type="text" name="title" value="<%= title %>"></div>
	</div>
	<div class="input_box">
		<label class="label">内容</label>
		<div class="input_border radius"><input type="text" name="content"  value="<%= content %>"></div>
	</div>
	<div class="input_box">
		<label class="label">背景图片</label>
		<div class="input_border radius"><input type="text" name="fileNames" id="fileNames" readonly="readonly"  value="<%= fileNames %>"><input type="hidden" name="filePaths" id="filePaths" value="<%= filePaths %>"></div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file" id="myFile"/>
	</div>
	
	<div class="input_box btn_box cf">
		<div class="input_border submit_border"><input type="button" id="btnSubmit"></div>
	</div>
</form></section>
<script type="text/javascript">
	$(function(){
		$('#btnSubmit').click(function(){
			var data = $('#myForm').serialize();
			$.ajax({
				url:'resourceEdit',
				data:data,
				type:'post',
				dataType:'json',
				success:function(json){
					if(json.ok){
						alert('修改成功');
					} else {
						alert(json.msg);
					}
				}
			});
		});
		
		$('#myFile').change(function(){
			ajaxUpload('upload', 'myFile');
		});
		
		$('#fileNames').parent().click(function(){
			if($('#fileNames').val() != '' && confirm('您想删除这个文件吗？')){
				$('#fileNames').val('');
				$('#filePaths').val('');
			}
		});
	});
	

</script>
<script type="text/javascript" src="static/js/upload.js"></script>
</body>
</html>