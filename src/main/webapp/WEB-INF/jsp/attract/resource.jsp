<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tohours.imo.bean.Resource" %>
<%@ page import="com.tohours.imo.util.TohoursUtils" %>
<%@ page import="org.nutz.lang.util.NutMap" %>
<%@ include file="inc-top.jsp" %>
<%
	Resource resource = (Resource)obj.get("resource");
	String typeName = obj.getString("typeName");

	String id = "";
	String title = "";
	String content = "";
	String fileNames = "";
	String filePaths = "";
	String contentTypes = "";
	String fileIds = "";
	String type = "";

	if(resource != null){
		 id = TohoursUtils.dealNull(resource.getId() + "");
		 type = TohoursUtils.dealNull(resource.getType() + "");
		 title = TohoursUtils.dealNull(resource.getTitle());
		 content = TohoursUtils.dealNull(resource.getContent());
		 fileNames = TohoursUtils.dealNull(resource.getFileNames());
		 filePaths = TohoursUtils.dealNull(resource.getFilePaths());
		 contentTypes = TohoursUtils.dealNull(resource.getContentTypes());
		 fileIds = TohoursUtils.dealNull(resource.getFileIds());
	} 
%>

<h2 class="page_title"><%= typeName %></h2>
<section class="form">
	<!-- 
		首页资源
	 -->
	<%
		if(_type.equals("1")){
	%>
	<form id="myForm">
	<% if("".equals(id) == false){ %>
	<input type="hidden" name="id" value="<%= id %>" />
	<% } %>
	<input type="hidden" name="type" value="${param.type}"/>
	<div class="input_box">
		<label class="label">背景图片</label>
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
	
	<div class="input_box btn_box cf">
		<div class="input_border submit_border"><input type="button" id="btnSubmit"></div>
	</div>
	<!-- 
		引导页资源
	 -->
	<%
		}else if(_type.equals("3")){
	%>
	<form id="myForm">
	<% if("".equals(id) == false){ %>
	<input type="hidden" name="id" value="<%= id %>" />
	<% } %>
	<input type="hidden" name="type" value="${param.type}"/>
	<div class="input_box">
		<label class="label">图片1</label>
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
		<label class="label">图片2</label>
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
		<label class="label">图片3</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file"  id="file3"/>
	</div>
	<div class="input_box">
		<label class="label">图片4</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file"  id="file4"/>
	</div>
	
	<div class="input_box btn_box cf">
		<div class="input_border submit_border"><input type="button" id="btnSubmit"></div>
	</div>
	<!-- 
		十大要素
	 -->
	<%
		}else if(_type.equals("4")){
	%>
		<form id="myForm">
	<% if("".equals(id) == false){ %>
	<input type="hidden" name="id" value="<%= id %>" />
	<% } %>
	<input type="hidden" name="type" value="${param.type}"/>
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
		<input type="file" name="file" id="file1"/>
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
		<input type="file" name="file" id="file2"/>
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
		<input type="file" name="file"  id="file3"/>
	</div>
	<div class="input_box">
		<label class="label">背景图片4</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file"  id="file4"/>
	</div>
	<div class="input_box">
		<label class="label">背景图片5</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file"  id="file5"/>
	</div>
	<div class="input_box">
		<label class="label">背景图片6</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file"  id="file6"/>
	</div>
	<div class="input_box">
		<label class="label">背景图片7</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file"  id="file7"/>
	</div>
	<div class="input_box">
		<label class="label">背景图片8</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file"  id="file8"/>
	</div>
	<div class="input_box">
		<label class="label">背景图片9</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file"  id="file9"/>
	</div>
		<div class="input_box">
		<label class="label">背景图片10</label>
		<div class="input_border radius">
			<input type="text" name="fileNames" readonly="readonly">
			<input type="hidden" name="filePaths" >
			<input type="hidden" name="contentTypes" >
			<input type="hidden" name="fileIds" >
		</div>
	</div>
	<div class="input_box">
		<label class="label"></label>
		<input type="file" name="file"  id="file10"/>
	</div>
	
	<div class="input_box btn_box cf">
		<div class="input_border submit_border"><input type="button" id="btnSubmit"></div>
	</div>
	
	<%
		}else if(_type.equals("5")){
			
	%>
	<form id="myForm">
	<% if("".equals(id) == false){ %>
	<input type="hidden" name="id" value="<%= id %>" />
	<% } %>
	<input type="hidden" name="type" value="${param.type}"/>
	<div class="input_box">
		<label class="label">标题</label>
		<div class="input_border radius"><input type="text" name="title" value="<%=title%>"></div>
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
		<input type="file" name="file" id="file1"/>
	</div>
	<div class="input_box btn_box cf">
		<div class="input_border submit_border"><input type="button" id="btnSubmit"></div>
	</div>
	<%		
		}
	%>
</form></section>
<script type="text/javascript">
	var type = ${param.type};
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
			console.log('文件大小'+data);
			//alert(data);
			showLoading('正在提交...');
			$.ajax({
				url:'resourceSave',
				data:data,
				type:'post',
				dataType:'json',
				success:function(json){
					if(json.ok){
						updateLoading('操作成功！');
						location.href = 'resourceList?type=' + type;
					} else {
						closeLoading();
						showMessage(json.msg);
					}
				}
			});
		});
		//上传文件大小  start
		var isIE = /msie/i.test(navigator.userAgent) && !window.opera;         
		  function fileChange(target) {     
		      
		    var fileSize = 0;          
		    if (isIE && !target.files) {      
		      var filePath = target.value;      
		      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");         
		      var file = fileSystem.GetFile (filePath);      
		      fileSize = file.Size;     
		    } else {     
		     fileSize = target.files[0].size;      
		     }    
		     var size = fileSize / 1024; 
		     console.log(size);
		     if(size>10000){   
		      alert("附件不能大于10M");   
		     }   
		}
		  
		 //end 
		$('input[name="file"]').change(function(){
			fileChange(this);
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