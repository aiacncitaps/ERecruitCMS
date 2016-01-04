	function ajaxUpload(url, id) {
		showLoading('上传中...');
		$.ajaxFileUpload({
			url: url,
			secureuri: false,
			fileElementId: id,
			dataType: 'text',
			beforeSend: function(data) {
			},
			complete: function() {
			},
			success: function(data, status) {
				closeLoading();
				data = data.replace(/<[^>]*>/ig, '');
				data = tohours.parseJSON(data);
				if(data && data.ok){
					addToForm(id, data.path, data.name, data.contentType);
					console.log(id+'--'+data.path+'--'+ data.name+'--'+ data.contentType);
					//新增的start
					addTopeoples(id,data.path, data.name, data.contentType);
					//end
				}else{
					alert(data.msg);
				}
				$('#' + id).val('').change(function(){
					ajaxUpload(url, id);
				});
			},
			error: function(data, status, e) {
				closeLoading();
				showMessage('出现错误：ajaxUplad row:28');
				console.log(arguments);
			}
		});
		return false;
	}
	
	function addToForm(id, path, name, contentType, fileId){
		console.log(arguments);
		var parent = $('#' + id).parent().prev();
		parent.find('input[name="filePaths"]').val(path);
		parent.find('input[name="fileNames"]').val(name);
		parent.find('input[name="contentTypes"]').val(contentType);
		parent.find('input[name="fileIds"]').val(fileId ? fileId : '');
		console.log('filePaths is :'+parent.find('input[name="filePaths"]').val());
		console.log('fileIds is :'+parent.find('input[name="fileIds"]').val());

//		var isImg = contentType.indexOf('image') >= 0;
//		var icon = isImg ? 'img':'file';
//		var light = isImg ?'data-lightbox="example-{0}"'.format(fileId):'';
//		parent.append('<a class="file-icon" href="resourceFile?id={0}" {1} title="{3}"><img src="static/images/{2}-icon.jpg"/></a>'.format(fileId, light, icon, name));
	}
	function addTopeoples(id, path, name, contentType, fileId){
			$('input[name="filePath"]').val(path);
			$('input[name="fileName"]').val(name);
			$('input[name="contentType"]').val(contentType);
			$('input[name="fileId"]').val(fileId ? fileId:'');
	}
	
	
