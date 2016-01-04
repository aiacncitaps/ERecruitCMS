<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.nutz.lang.util.NutMap" %>
<%@ include file="inc-top.jsp" %>
<link rel="stylesheet" href="static/css/lightbox.css">
<script type="text/javascript" src="static/js/lightbox.js"></script>

<h2 class="page_title"></h2>
<section class="form">
<form id="queryForm">
		<input type="hidden" name="pageNumber" id="pageNumber" value="1">
		<input type="hidden" name="pageSize" value="10">
		<input type="hidden" name="type" value="${param.type}">
	<div class="input_box">
		<label class="label">关键字：</label>
		<div class="input_border radius"><input type="text" name="key" id="key"></div>
	</div>
	<div class="input_box btn_box cf">
		<div class="input_border search_border search_border2"><input type="button" id="btnSearch"></div><div class="input_border clear_border"><input type="button" id="btnClear"></div>
	</div>
</form></section>
<section class="table">

	<%
		if(_type.equals("1")){
	%>
	<ul class="table_list">
		<li class="cf title">
			<p class="column1"><span>标题</span></p>
			<p class="column2"><span>资源</span></p>
			<p class="column3"><span>内容</span></p>
			<p class="column4"><span>编辑</span></p>
		</li>
	</ul>
	<% 
		}else if(_type.equals("5")){
	%>
	<input type="button" value="排序" class="paixu" onclick="squence();">
	<ul class="table_list">
		<li class="cf title">
			<p class="column1" style="width:20%;"><span>标题</span></p>
			<p class="column2" style="width:40%;"><span>资源</span></p>
			<p class="column3" style="width:20%;"><span>序号</span></p>
			<p class="column4" style="width:20%;" ><span>编辑</span></p>
		</li>
	</ul>
	
	<%
		}
	%>
</section>
<script type="text/javascript">
function squence(){
	var arrSqu = new Array();
	var arrResourceId = new Array();
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
	$('ul').find(".resourceId").each(function(i){
		arrResourceId[i]=$(this).val();
	});
	//alert(arrTopId.join('')+"/"+arrSqu.join(''));
	if(index != ''){
		alert('第'+index+'不是数字，请重新输入');
	}else{
		//alert(arrSqu.length+"/"+arrTopId.length);
		$.ajax({
			url : 'updateTenEleSqu',
			data : 'id='+arrResourceId.join(',')+'&squence='+arrSqu.join(',')+'&type=resource',
			dataType : "json",
			success : function(data) {
				if(data.success){
					//alert("更新成功");
				}
			}
		});
		listReload();
		window.location.reload();
	}
}
	var type = ${param.type};
	function listReload(){
		showLoading('加载中...');
		$.ajax({
			url : 'resourceListData',
			data : $("#queryForm").serialize(),
			dataType : "json",
			success : function(data) {
				console.log(data);
				closeLoading();
				var list = data.qr.list;
				$('.page_title').html(data.title);
				$('.table_list .cf').each(function(i){
					if(i > 0){
						$(this).remove();
					}
				});
				$('.control').remove();
				for(var i = 0;i<list.length;i++){
					var bean = list[i];
					var fileIds = bean.fileIds.split(',');
					var fileTypes = bean.fileTypes.split(',');
					var contentTypes = bean.contentTypes.split(',');
					var fileNames = bean.fileNames.split(',');
					
					var tr = '';
					if(type == '5'){
						//友邦十大要素显示控制
						tr += '	<li class="cf">';
						tr += '		<p class="column1" style=\"width:19.8%\"><span>{0}</span></p>'.format(bean.title);
						tr += '		<p class="column2" style=\"width:40%\"><span>';
						for(var j = 0;j<bean.fileCounts;j++){
							var isImg = contentTypes[j].indexOf('image') >= 0;
							var icon = isImg ? 'img':'file';
							var light = isImg ?'data-lightbox="example-{0}"'.format(fileIds[j]):'';
							tr += '	<a class="file-icon" href="resourceFile?id={0}" {1} title="{3}"><img src="static/images/{2}-icon.jpg"/></a>'.format(fileIds[j], light, icon, fileNames[j]);
						}
						tr += '		</span></p>';
						tr += '		<p class="column3" style="width:20%;"><span>';
						var squ=bean.squence;
						var reg=/^[0-9]+$/;
						if(!reg.test(squ)){
							squ='';
						}
						if(bean.squence == null){
							squ='';
						}
						tr += '		<input type="text" value="{0}" class="inp squ"></span></p>.'.format(squ);
						tr += '		<p class="column4" style=\"width:19.8%\"><span>';
						tr +='			<input type="hidden" value="{0}" class="resourceId">'.format(bean.id);
						tr += '			<a class="next" href="javascript:children(\'{0}\');" title=\"下一个\"></a>'.format(bean.id);
						tr += '			<a class="edit" href="javascript:;" onclick="edit(\'{0}\');"></a>'.format(bean.id);
						tr += '		</span></p>';
						tr += '	</li>';
					}else if(type == '1'){
						//首页资源显示控制
						tr += '	<li class="cf">';
						tr += '		<p class="column1"><span>{0}</span></p>'.format(bean.title);
						tr += '		<p class="column2"><span>';
						for(var j = 0;j<bean.fileCounts;j++){
							var isImg = contentTypes[j].indexOf('image') >= 0;
							var icon = isImg ? 'img':'file';
							var light = isImg ?'data-lightbox="example-{0}"'.format(fileIds[j]):'';
							tr += '	<a class="file-icon" href="resourceFile?id={0}" {1} title="{3}"><img src="static/images/{2}-icon.jpg"/></a>'.format(fileIds[j], light, icon, fileNames[j]);
						}
						tr += '		</span></p>';
						var content = bean.content;
						if(!content){
							content = '';
						}
						tr += '		<p class="column3" title={1}><span>{0}</span></p>'.format(content.substring(0, 100), content );
						tr += '		<p class="column4"><span>';
						tr += '			<a class="edit" href="javascript:;" onclick="edit(\'{0}\');"></a>'.format(bean.id);
						tr += '			<a class="delete" href="javascript:void(0);" onclick="del(\'{0}\');"></a>'.format(bean.id);
						tr += '		</span></p>';
						tr += '	</li>';
					}
					
					$('.table_list').append(tr);
				}
				if(list.length == 0){
					$('.table_list').append('<li class="cf" style="text-align:center;font-color:gray;">没有找到数据</li>');
				} else {
					var pager = data.qr.pager;
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
	function children(resourceId){
		window.location.href='childrenIndex?resourceId=' + resourceId;
		//alert(resourceId);
	}
	function edit(id){
		location.href = 'resource?id=' + id + '&type=' + type;
	}
	
	function del(id){
		showConfirm('你确定要删除这条记录吗？', function(){
			if(id){
				$.ajax({
					url:'resourceDelete',
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
