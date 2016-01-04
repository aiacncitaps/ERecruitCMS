<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tohours.imo.bean.Peoples" %>
<%@ page import="com.tohours.imo.util.TohoursUtils" %>
<%@ page import="org.nutz.lang.util.NutMap" %>
<%@ include file="inc-top.jsp" %>
<%
	Peoples peoples = (Peoples)obj.get("peoples");
	String name = "";
	String subCompany="";
	String joinDate = "";
	String oldJob = "";
	String fileName = "";
	String filePath = "";
	String contentType = "";
	String fileId = "";
	String shareWord = "";
	String id = "";
	String oldMark="";
	String newMark="";
	String marks="";
	if(peoples != null){
		 id = TohoursUtils.dealNull(peoples.getId() + "");
		 name = TohoursUtils.dealNull(peoples.getName());
		 subCompany=TohoursUtils.dealNull(peoples.getSubCompany());
		 oldJob = TohoursUtils.dealNull(peoples.getOldJob());
		 joinDate = TohoursUtils.dealNull(peoples.getJoinDate());
		 shareWord = TohoursUtils.dealNull(peoples.getShareWord());
		 fileName = TohoursUtils.dealNull(peoples.getFileName());
		 filePath = TohoursUtils.dealNull(peoples.getFilePath());
		 contentType = TohoursUtils.dealNull(peoples.getContentType());
		 fileId = TohoursUtils.dealNull(peoples.getFileId()+"");
		 oldMark=TohoursUtils.dealNull(peoples.getOldMark());
		 newMark=TohoursUtils.dealNull(peoples.getNewMark());
		 marks=TohoursUtils.dealNull(peoples.getMarks());
	} 
%>

<h2 class="page_title">peoples</h2>
<section class="form">
	<form id="myForm">
		<% if("".equals(id) == false){ %>
			<input type="hidden" name="id" value="<%= id %>" />
		<% } %>
		<input type="hidden" name="sub_excel_id" value="${param.sub_excel_id }" />
		<div class="input_box">
			<label class="label">姓名：</label>
			<div class="input_border radius"><input type="text" name="name" value="<%=name%>"></div>
		</div>
		<div class="input_box">
			<label class="label">分公司名称：</label>
			<div class="input_border radius"><input type="text" name="subCompany" value="<%=subCompany%>"></div>
		</div>
			<div class="input_box">
			<label class="label">入司时间：</label>
			<div class="input_border radius"><input type="text" name="joinDate" value="<%=joinDate%>"></div>
		</div>
		<div class="input_box">
			<label class="label">入司前职业：</label>
			<div class="input_border radius"><input type="text" name="oldJob" value="<%=oldJob%>">
			</div>
		</div>
		<div class="input_box">
			<label class="label">感悟与分享：</label>
			<div><textarea style="width:400px;height:200px;" name="shareWord"><%=shareWord%></textarea>
		</div>
		
		<div class="input_box">
			<label class="label">照片：</label>
			<div class="input_border radius">
				<input type="text" name="fileName" readonly="readonly">
				<input type="hidden" name="filePath" >
				<input type="hidden" name="contentType" >
				<input type="hidden" name="fileId" >
			</div>
		</div>
		<div class="input_box">
			<label class="label"></label>
			<input type="file" name="file" id="file1"/>
		</div>
		<div style="width:900px;height:50px;border:1px solid gray;text-align:center;line-height:50px;margin-bottom:17px;margin-left:-300px;">
			昨日与今日
		</div>
		<!-- --------------------------------------------- -->
		<div class="input_box">
			<label class="label">名称1</label>
			<div class="input_border radius"><input type="text" name="markName" value=""></div>
		</div>
		
		<div class="input_box">
			<label class="label">原评分</label>
			<div class="input_border radius"><input type="text"  name="oldMark" value=""></div>
		</div>
		
		<div class="input_box">
			<label class="label">新评分</label>
			<div class="input_border radius"><input type="text" name="newMark" value=""></div>
		</div>
		<div class="input_box">
			<label class="label">描述</label>
			<div class="input_border radius"><input type="text" name="desc" value=""></div>
		</div>
		<!-- --------------------------------------------- -->
		<div class="input_box">
			<label class="label">名称2</label>
			<div class="input_border radius"><input type="text" name="markName" value=""></div>
		</div>
		
		<div class="input_box">
			<label class="label">原评分</label>
			<div class="input_border radius"><input type="text"  name="oldMark" value=""></div>
		</div>
		
		<div class="input_box">
			<label class="label">新评分</label>
			<div class="input_border radius"><input type="text" name="newMark" value=""></div>
		</div>
		<div class="input_box">
			<label class="label">描述</label>
			<div class="input_border radius"><input type="text" name="desc" value=""></div>
		</div>
		<!-- --------------------------------------------- -->
		<div class="input_box">
			<label class="label">名称3</label>
			<div class="input_border radius"><input type="text" name="markName" value=""></div>
		</div>
		
		<div class="input_box">
			<label class="label">原评分</label>
			<div class="input_border radius"><input type="text"  name="oldMark" value=""></div>
		</div>
		
		<div class="input_box">
			<label class="label">新评分</label>
			<div class="input_border radius"><input type="text" name="newMark" value=""></div>
		</div>
		<div class="input_box">
			<label class="label">描述</label>
			<div class="input_border radius"><input type="text" name="desc" value=""></div>
		</div>
		<!-- --------------------------------------------- -->
		<div class="input_box">
			<label class="label">名称4</label>
			<div class="input_border radius"><input type="text" name="markName" value=""></div>
		</div>
		
		<div class="input_box">
			<label class="label">原评分</label>
			<div class="input_border radius"><input type="text"  name="oldMark" value=""></div>
		</div>
		
		<div class="input_box">
			<label class="label">新评分</label>
			<div class="input_border radius"><input type="text" name="newMark" value=""></div>
		</div>
		<div class="input_box">
			<label class="label">描述</label>
			<div class="input_border radius"><input type="text" name="desc" value=""></div>
		</div>
		<!-- --------------------------------------------- -->
		<div class="input_box">
			<label class="label">名称5</label>
			<div class="input_border radius"><input type="text" name="markName" value=""></div>
		</div>
		
		<div class="input_box">
			<label class="label">原评分</label>
			<div class="input_border radius"><input type="text"  name="oldMark" value=""></div>
		</div>
		
		<div class="input_box">
			<label class="label">新评分</label>
			<div class="input_border radius"><input type="text" name="newMark" value=""></div>
		</div>
		<div class="input_box">
			<label class="label">描述</label>
			<div class="input_border radius"><input type="text" name="desc" value=""></div>
		</div>
		<!-- --------------------------------------------- -->
		<div class="input_box btn_box cf">
			<div class="input_border submit_border"><input type="button" id="btnSubmit"></div>
		</div>
	</form>
</section>
<script type="text/javascript">
	$(function(){
		var fileName = '<%=fileName%>';
		var filePath = '<%=filePath%>';
		var contentType = '<%=contentType%>';
		var fileId = '<%=fileId%>';
		
		<% if("".equals(marks) == false){%>
			var oldMark=<%=oldMark%>;
			var newMark=<%=newMark%>;
			var marks=<%=marks%>;
			if(oldMark && newMark){
				for (var i = 0; i < marks.length; i++) {
					$('input[name="markName"]').eq(i).val(marks[i].name);
					$('input[name="oldMark"]').eq(i).val(marks[i].old_mark);
					$('input[name="newMark"]').eq(i).val(marks[i].new_mark);
					$('input[name="desc"]').eq(i).val(marks[i].desc);
				}
			}
		<%}%>
		if (fileName != '') {
			$('input[name="filePath"]').val(filePath);
			$('input[name="fileName"]').val(fileName);
			$('input[name="contentType"]').val(contentType);
			$('input[name="fileId"]').val(fileId ? fileId : '');
		}

		$('#btnSubmit').click(
				function() {

					var data = $('#myForm').serialize();
					//alert(data);
					showLoading('正在提交...');
					$.ajax({
						url : 'peoplesSave',
						data : data,
						type : 'post',
						dataType : 'json',
						success : function(json) {
							if (json.success) {
								updateLoading('操作成功！');
								location.href = 'peoplesIndex?sub_excel_id='
										+ "${param.sub_excel_id }";
							} else {
								closeLoading();
								showMessage(json.msg);
							}
						}
					});
				});

		$('input[name="file"]').change(function() {
			ajaxUpload('upload', $(this).attr('id'));
		});

		$('input[name="fileName"]').parent().click(function() {
			if ($(this).find('input[name="fileNames"]').val() != '') {
				var obj = this;
				showConfirm('您想删除这个文件吗？', function() {
					$(obj).find('input[name="fileName"]').val('');
					$(obj).find('input[name="filePath"]').val('');
					$(obj).find('input[name="contentType"]').val('');
					$(obj).find('input[name="fileId"]').val('');
				});
			}
		});
	});
</script>
<script type="text/javascript" src="static/js/upload.js"></script>
</body>
</html>