<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.nutz.lang.util.NutMap" %>
<%@ include file="inc-top.jsp" %>
<link rel="stylesheet" href="static/css/lightbox.css">
<script type="text/javascript" src="static/js/lightbox.js"></script>

<h2 class="page_title"></h2>
<section class="form">
	<div class="input_box">
		<label class="label">APP下载次数：</label>
		<div class="input_border radius"><input type="text" name="key" id="key" readonly="readonly" value=""></div>
	</div>
</section>
<input type="button" value="导出下载" class="paixu" onclick="exportPort();">
<section class="table">
	<ul class="table_list">
		<li class="cf title">
			<p class="column1" style="width:30%;"><span>分公司</span></p>
			<!-- <p class="column2" style="width:25%;"><span>地区</span></p> -->
			<p class="column2" style="width:35%;"><span>agent登录次数</span></p>
			<p class="column3" style="width:35%;"><span>上传人才库数量</span></p>
		</li>
	</ul>
</section>
<script type="text/javascript">
	function exportPort(){
		$.ajax({
			url:'exportPort.action',//url
			type:'post',//提交方式
			data:'',//参数
			dataType:'json',	//返回类型
			success:function(json){//正确返回执行的函数
				if(json.success){
					window.location.href=json.msg;
				}else{
					alert(json.msg);;
				}
			},
			error:function(json){//错误返回执行的函数
			}
		});
	}
	function listReload(){
		showLoading('加载中...');
		$.ajax({
			url : 'getCount',
			data : $("#queryForm").serialize(),
			dataType : "json",
			success : function(data) {
				console.log(data);
				closeLoading();
				var list = data.msg;
				var appNum=data.appNum;
				$('#key').val(appNum);
				$('.page_title').html(data.title);
				$('.table_list .cf').each(function(i){
					if(i > 0){
						$(this).remove();
					}
				});
				$('.control').remove();
				for(var i = 0;i<list.length;i++){
					var bean = list[i];
					var subCompany=bean.subCompany == null || bean.subCompany == 'undefined'?"":bean.subCompany;
					var region=bean.region == null || bean.region == 'undefined'? "" :bean.region;
					var loginNum=bean.loginNum == null || bean.loginNum == 'undefined' ? "" : bean.loginNum;
					var talentNum=bean.talentNum == null || bean.talentNum == 'undefined' ? "" : bean.talentNum;
					var tr = '';
						//友邦十大要素显示控制
						tr += '	<li class="cf">';
						tr += '		<p class="column1" style="width:29.8%"><span style="text-align:center;">{0}</span ></p>'.format(subCompany);
						//tr += '		<p class="column2" style="width:25%"><span style="text-align:center;">{0}</span></p>'.format(region);
						tr += '		<p class="column2" style="width:35%"><span style="text-align:center;">{0}</span></p>'.format(loginNum);
						tr += '		<p class="column3" style="width:35%"><span style="text-align:center;">{0}</span></p>'.format(talentNum);
						tr += '	</li>';
					$('.table_list').append(tr);
					if(list.length == 0){
						$('.table_list').append('<li class="cf" style="text-align:center;font-color:gray;">没有找到数据</li>');
					} 
				}
			}
		});
	}
	$(function(){
			listReload();
	});
</script>
</body>
</html>
