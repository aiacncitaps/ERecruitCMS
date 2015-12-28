<%@page import="org.nutz.lang.util.NutMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc-top.jsp" %>
<link rel="stylesheet" href="static/css/lightbox.css">
<script type="text/javascript" src="static/js/lightbox.js"></script>
<h2 class="page_title">childrenNext</h2>
<section class="form">
<%
	String childrenId=obj.getString("childrenId");
%>
<form id="queryForm">
		<input type="hidden" name="pageNumber" id="pageNumber" value="1">
		<input type="hidden" name="pageSize" value="10">
		<input type="hidden" name="childrenId" value="<%=childrenId%>">
	<div class="input_box">
		<label class="label">关键字：</label>
		<div class="input_border radius"><input type="text" name="key" id="key"></div>
	</div>
	<div class="input_box btn_box cf">
		<div class="input_border search_border search_border2"><input type="button" id="btnSearch"></div><div class="input_border clear_border"><input type="button" id="btnClear"></div>
	</div>
</form></section>
<section class="table">
<input type="button" value="排序" class="paixu" onclick="squence();">
<ul class="table_list">
	<li class="cf title">
		<p class="column1" style="width:20%"><span>名字</span></p>
		<p class="column2" style="width:40%"><span>资源</span></p>
		<p class="column3" style="width:20%"><span>序号</span></p>
		<p class="column4" style="width:20%"><span>编辑</span></p>
	</li>
</ul></section>
<script type="text/javascript">
function squence(){
	var arrSqu = new Array();
	var arrPeoplesId = new Array();
	var index='';
	$('ul').find(".squ").each(function(i){
		var reg=/^[0-9]+$/;
		arrSqu[i]=$(this).val();
	   if(arrSqu[i] != ''){
		   if(!reg.test(arrSqu[i].trim())){
			   //alert("第"+(i+1)+"个不是数字"+arr[i].trim());
			   index+=i+1+',';
		   }
	   }else{
		   arrSqu[i]='-';
	   }
	});
	$('ul').find(".peoplesId").each(function(i){
		arrPeoplesId[i]=$(this).val();
	});
	//alert(arrTopId.join('')+"/"+arrSqu.join(''));
	if(index != ''){
		alert('第'+index+'不是数字，请重新输入');
	}else{
		//alert(arrSqu.length+"/"+arrTopId.length);
		$.ajax({
			url : 'updateTenEleSqu',
			data : 'id='+arrPeoplesId.join(',')+'&squence='+arrSqu.join(',')+'&type=childrenNext',
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert("更新成功");
				}
			}
		});
		listReload();
		window.location.reload();
	}
}
	function listReload(){
		showLoading('加载中...');
		$.ajax({
			url : 'childrenNextList',
			data : $("#queryForm").serialize(),
			dataType : "json",
			success : function(data) {
				console.log(data);
				closeLoading();
				var list = data.list;
				$('.page_title').html("childrenNext");
				$('.table_list .cf').each(function(i){
					if(i > 0){
						$(this).remove();
					}
				});
				$('.control').remove();
				var html="";
				for(var i = 0;i<list.length;i++){
					var bean = list[i];
					var fileIds = bean.fileIds.split(',');
					var fileTypes = bean.fileTypes.split(',');
					var contentTypes = bean.contentTypes.split(',');
					var fileNames = bean.fileNames.split(',');
					var name = bean.name == null || bean.name == "undefined" ? "":bean.name;
					var joinDate = bean.joinDate == null || bean.joinDate == "undefined" ? "":bean.joinDate;
					var fileId = bean.fileId == null || bean.fileId == "undefined" ? "":bean.fileId;
					var light = 'data-lightbox="example-{0}"'.format(fileId);
					html=html+"<li class=\"cf\"><p class=\"column1\" style=\"width:19.8%\"><span>"+name+"</span></p>";
					html += '<p class="column2" style="width:40%"><span>';
					for(var j = 0;j<bean.fileCounts;j++){
						var isImg = contentTypes[j].indexOf('image') >= 0;
						var icon = isImg ? 'img':'file';
						var light = isImg ?'data-lightbox="example-{0}"'.format(fileIds[j]):'';
						html += '	<a class="file-icon" href="resourceFile?id={0}" {1} title="{3}"><img src="static/images/{2}-icon.jpg"/></a>'.format(fileIds[j], light, icon, fileNames[j]);
					}
					html += '		</span></p>';
					html += '<p class="column3" style="width:20%;"><span>';
					var squ=bean.squence;
					var reg=/^[0-9]+$/;
					if(!reg.test(squ)){
						squ='';
					}
					if(bean.squence == null){
						squ='';
					}
					html += '<input type="text" value="{0}" class="inp squ"></span></p>.'.format(squ);
					html += '<p class="column4" style="width:19.5%;"><span>';
					html +='	<input type="hidden" value="{0}" class="peoplesId">'.format(bean.id);
					html += '	<a class="edit" href="javascript:;" onclick="edit(\'{0}\',\'{1}\');"></a>'.format(bean.id,bean.childrenId);
					html += '	<a class="delete" href="javascript:;" onclick="del(\'{0}\');"></a>'.format(bean.id);
					html += '</span></p>';
					html+="\</li>";
				}
				$('.table_list').append(html);
				if(list.length == 0){
					$('.table_list').append('<li class="cf" style="text-align:center;font-color:gray;">没有找到数据</li>');
				} else {
					var pager = data.pager;
					var foot = '';
					foot += '	<div class="control">';
					foot += '		<span><i>{0}</i>/<i>{1}</i>页</span>'.format(pager.pageNumber, pager.pageCount);
					if(pager.pageNumber > 1){
						foot += '<a href="javascript:;" onclick="go({0})">上一页</a> '.format(pager.pageNumber - 1);
					}
					if(pager.pageNumber < pager.pageCount){
						foot += '<a href="javascript:;" onclick="go({0})">下一页</a>'.format(pager.pageNumber + 1);
					}
					foot += '	</div>';

					$('.table_list').after(foot);
				}
			}
		});
	}
	
	$(function(){
		listReload();
		$('#btnSearch').click(function(){
			$('#pageNumber').val(1);
			listReload();
		});
		$('#btnClear').click(function(){
			$('#key').val('');
			$('#pageNumber').val(1);
			listReload();
		});
	});
	
	function edit(id,childrenId){
		location.href = 'childrenNextPre?id=' + id +"&childrenId="+childrenId;
	}
	
	function del(id){
		showConfirm('你确定要删除这条记录吗？', function(){
			if(id){
				$.ajax({
					url:'childrenNextDelete',
					data:'id=' + id,
					dataType:'json',
					success: function(json){
						if(json.success){
							listReload();
						} else {
							showMessage(json.msg);
						}
					}
				})
			} else {
				showMessage('所提供的ID不存在！');
			}
		});
	}
	function go(pageNumber){
		$('#pageNumber').val(pageNumber);
		listReload();
	}
</script>
</body>
</html>