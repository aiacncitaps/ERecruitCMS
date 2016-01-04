<%@page import="com.tohours.imo.bean.TopExcellence"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tohours.imo.bean.SubExcellence" %>
<%@ page import="com.tohours.imo.util.TohoursUtils" %>
<%@ page import="org.nutz.lang.util.NutMap" %>
<%@ include file="inc-top.jsp" %>
<%
	TopExcellence topExcellence = (TopExcellence)obj.get("topExcellence");

	String id = "";
	String name = "";
	String fileNames = "";
	String filePaths = "";
	String contentTypes = "";
	String fileIds = "";

	if(topExcellence != null){
		 id = TohoursUtils.dealNull(topExcellence.getId() + "");
		 name = TohoursUtils.dealNull(topExcellence.getName());
		 fileNames = TohoursUtils.dealNull(topExcellence.getFileNames());
		 filePaths = TohoursUtils.dealNull(topExcellence.getFilePaths());
		 contentTypes = TohoursUtils.dealNull(topExcellence.getContentTypes());
		 fileIds = TohoursUtils.dealNull(topExcellence.getFileIds());
	} 
%>

<h2 class="page_title">TopExcellence</h2>
<section class="form">
	<form id="myForm">
	<% if("".equals(id) == false){ %>
	<input type="hidden" name="id" value="<%= id %>" />
	<% } %>
	
	<div class="input_box">
		<label class="label">标题</label>
		<div class="input_border radius"><input type="text" name="name" value="<%=name%>"></div>
	</div>
	
	<div class="input_box">
		<label class="label">视频</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file" id="file1"/>
	</div>
	<div class="input_box">
		<label class="label">背景图片1</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file" id="file2"/>
	</div>
	<div class="input_box">
		<label class="label">背景图片2</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file" id="file3"/>
	</div>
	<div class="input_box">
		<label class="label">背景图片3</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file" id="file4"/>
	</div>
	<div class="input_box btn_box cf">
		<div class="input_border submit_border"><input type="button" id="btnSubmit"></div>
	</div>
</form></section>
<script type="text/javascript">
	$(function(){
		var fileNames = '<%=fileNames%>';
		var filePaths = '<%=filePaths%>';
		var contentTypes = '<%=contentTypes%>';
		var fileIds = '<%=fileIds%>';
		if(fileNames != ''){
			var arrNames = fileNames.split(',');
			var arrPaths = filePaths.split(',');
			var arrContentTypes = contentTypes.split(',');
			var arrFileIds = fileIds.split(',');
			for(var i=0;i<arrNames.length; i++){
				var name = arrNames[i];
				var path = arrPaths[i];
				var contentType = arrContentTypes[i];
				var fileId = arrFileIds[i];
				addToForm('file' + (i+1), path, name, contentType, fileId);
			}
		}
		$('#btnSubmit').click(function(){
			var data = $('#myForm').serialize();
			showLoading('正在提交...');
			$.ajax({
				url:'topExcellenceSave',
				data:data,
				type:'post',
				dataType:'json',
				success:function(json){
					if(json.success){
						updateLoading('操作成功！');
						location.href = 'topExcellenceIndex';
					} else {
						closeLoading();
						showMessage(json.msg);
					}
				}
			});
		});
		
		$('input[name="file"]').change(function(){
			ajaxUpload('upload', $(this).attr('id'));
		});
		
		$('input[name="fileNames"]').parent().click(function(){
			if($(this).find('input[name="fileNames"]').val() != ''){
				var obj = this;
				showConfirm('您想删除这个文件吗？', function(){
					$(obj).find('input[name="fileNames"]').val('') ;
					$(obj).find('input[name="filePaths"]').val('') ;
					$(obj).find('input[name="contentTypes"]').val('') ;
					$(obj).find('input[name="fileIds"]').val('') ;
				});
			}
		});
	});
</script>
<script type="text/javascript" src="static/js/upload.js"></script>
</body>
</html>