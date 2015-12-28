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
	<div class="input_box">
		<label class="label">关键字：</label>
		<div class="input_border radius"><input type="text" name="key" id="key"></div>
	</div>
	<div class="input_box btn_box cf">
		<div class="input_border search_border search_border2"><input type="button" id="btnSearch"></div><div class="input_border clear_border"><input type="button" id="btnClear"></div>
	</div>
</form></section>
<section class="table">
	<ul class="table_list">
		<li class="cf title">
			<p class="column1" style="width:20%"><span style="text-align:center;">营销员编号</span></p>
			<p class="column2" style="width:20%"><span style="text-align:center;">营销员名字</span></p>
			<p class="column3" style="width:20%"><span style="text-align:center;">分公司</span></p>
			<p class="column4" style="width:20%"><span style="text-align:center;">设备号</span></p>
			<p class="column4" style="width:20%"><span style="text-align:center;">操作</span></p>
		</li>
	</ul>
</section>
<script type="text/javascript">
	function listReload(){
		showLoading('加载中...');
		$.ajax({
			url : 'unbandList',
			data : $("#queryForm").serialize(),
			dataType : "json",
			success : function(data) {
				console.log(data);
				closeLoading();
				var list = data.list;
				$('.page_title').html(data.title);
				$('.table_list .cf').each(function(i){
					if(i > 0){
						$(this).remove();
					}
				});
				$('.control').remove();
				for(var i = 0;i<list.length;i++){
					var bean = list[i];
					var agentCode=bean.agentCode == null || bean.agentCode == "undefined" ? "":bean.agentCode;
					var agentName=bean.name == null || bean.name == "undefined" ? "":bean.name;
					var subCompanyId=bean.subCompanyId == null || bean.subCompanyId == "undefined" ? "":bean.subCompanyId;
					var deviceNum=bean.deviceNum == null || bean.deviceNum == "undefined" ? "未绑定":bean.deviceNum;
					
					var tr = '';
						//首页资源显示控制
						tr += '	<li class="cf">';
						tr += '		<p class="column1" style="width:19.8%"><span style="text-align:center;">{0}</span></p>'.format(agentCode);
						tr += '		<p class="column2" style="width:20%"><span style="text-align:center;">{0}</span></p>'.format(agentName);
						tr += '		<p class="column3" style="width:20%"><span style="text-align:center;">{0}</span></p>'.format(subCompanyId);
						tr += '		<p class="column4" style="width:20%""><span id={0} style="text-align:center;">{1}</span></p>'.format(bean.id,deviceNum);
						tr += '		<p class="column4" style="width:20%"><span style="text-align:center;" class={0}>'.format(bean.id);
									if(!(bean.deviceNum == null || bean.deviceNum == 'undefined')){
										tr += '			<a class="" style="text-decoration:underline" href="javascript:;" onclick="edit(\'{0}\');">点击解绑</a>'.format(bean.id);			
									}else{
										tr+='无需解绑';
									}
						
						tr += '		</span></p>';
						tr += '	</li>';
					$('.table_list').append(tr);
				}
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
	function children(resourceId){
		window.location.href='childrenIndex?resourceId=' + resourceId;
		//alert(resourceId);
	}
	function edit(id){
		$.ajax({
			url : 'unbandAgent',
			data : 'id='+id,
			dataType : "json",
			success : function(json) {
				alert(json.msg);
				if(json.success){
					//location.href=location.href;
					$('#'+id).text('未绑定');
					$('.'+id).text('无需解绑');
				}
			}
		});
		location.href = 'update?id=' + id + '&type=' + type;
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
