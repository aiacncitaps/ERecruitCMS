<%@page import="com.tohours.imo.bean.ChildrenNext"%>
<%@page import="com.tohours.imo.bean.Children"%>
<%@page import="com.tohours.imo.bean.SubExcellence"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tohours.imo.bean.SubExcellence" %>
<%@ page import="com.tohours.imo.util.TohoursUtils" %>
<%@ page import="org.nutz.lang.util.NutMap" %>
<%@ include file="inc-top.jsp" %>
<%
	ChildrenNext childrenNext = (ChildrenNext)obj.get("childrenNext");
	String id = "";
	String name = "";
	String fileNames = "";
	String filePaths = "";
	String contentTypes = "";
	String fileIds = "";
	

	if(childrenNext != null){
		 id = TohoursUtils.dealNull(childrenNext.getId() + "");
		 name = TohoursUtils.dealNull(childrenNext.getName());
		 fileNames = TohoursUtils.dealNull(childrenNext.getFileNames());
		 filePaths = TohoursUtils.dealNull(childrenNext.getFilePaths());
		 contentTypes = TohoursUtils.dealNull(childrenNext.getContentTypes());
		 fileIds = TohoursUtils.dealNull(childrenNext.getFileIds());
	} 
%>

<h2 class="page_title">childrenNext</h2>
<section class="form">
	<form id="myForm">
	<% if("".equals(id) == false){ %>
	<input type="hidden" name="id" value="<%= id %>" />
	<% } %>
	<input type="hidden" name=childrenId value="${param.childrenId }" />
	<input type="button" value="添加文件" class="paixu bgImage" onclick="addImg();">
	<div class="input_box">
		<label class="label">标题</label>
		<div class="input_border radius"><input type="text" name="name" value="<%=name%>">
		</div>
		<input type="button" value="添加图片" class="inpImg bgImage" onclick="addImg();">
	</div>
	
	<div class="input_box bgImage">
		<label class="label">背景图片1</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box bgImage">
		<label class="label"></label>
		<input type="file" name="file" id="file1"/>
	</div>
	<%
		String[] num= fileIds.split(",");
		for(int i=1;i<num.length;i++){
	%>
	<div class="input_box bgImage">
		<label class="label">背景图片<%=i+1%></label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box bgImage">
		<label class="label"></label>
		<input type="file" name="file" id="file<%=i+1%>"/>
	</div>
	<%
		}
	%>
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
				url:'childrenNextSave',
				data:data,
				type:'post',
				dataType:'json',
				success:function(json){
					if(json.success){
						updateLoading('操作成功！');
						location.href = 'childrenNextIndex?childrenId='+"${param.childrenId }";
					} else {
						closeLoading();
						showMessage(json.msg);
					}
				}
			});
		});
		rebind();
	});
	function rebind(){
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
	}
</script>
<script type="text/javascript" src="static/js/upload.js"></script>
</body>
</html>