<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<script src="text/lib/fileUpload/jquery.ui.widget.js"></script>
<script src="text/lib/fileUpload/jquery.iframe-transport.js"></script>
<script src="text/lib/fileUpload/jquery.fileupload.js"></script>
<link href="text/lib/fileUpload/jquery.fileupload.css" rel="stylesheet">
<span class="btn btn-info fileinput-button"> <span>${param.title }</span> <span
	id="html5uploadProsess-${param.appkey}"></span> <input
	id="fileupload-${param.appkey}" type="file" name="file" multiple>
</span>
<c:if test="${param.type=='IMG'||param.type=='PHOTO'}">
	<script type="text/javascript">
		//单个图片文件大小
		var maxSize = Number('${config_doc_img_upload_length_max}');
	</script>
</c:if>
<c:if test="${param.type=='MEDIA'||param.type=='IMMED'}">
	<script type="text/javascript">
		//单个多媒体文件大小
		var maxSize = Number('${config_doc_hour_upload_length_max}');
	</script>
</c:if>
<c:if test="${param.type!='FILE'}">
	<script type="text/javascript">
		//单个附件文件大小
		var maxSize = Number('${config_doc_upload_length_max}');
	</script>
</c:if>
<c:if test="${param.type!='HOUR'}">
	<script type="text/javascript">
		//单个课时文件大小
		var maxSize = Number('${config_doc_hour_upload_length_max}');
	</script>
</c:if>
<script type="text/javascript">
	//单次上传文件数量
	var maxFileNum = 1;
	$(function() {
		var maxSizeTitle = (maxSize / 1024 / 1024).toFixed(2);
		$('#fileupload-${param.appkey}')
				.fileupload(
						{
							url : "upload/general.do?type=${param.type}",
							dataType : 'json',
							change : function(e, data) {
								if (data.files.length > maxFileNum) {
									alert("单次上传文件数量不能大于" + maxFileNum + "个!");
									return false;
								}
								var isOK = true;
								$(data.files).each(
										function(i, obj) {
											if (obj.size > maxSize) {
												alert("文件" + obj.name
														+ "超大,请检查文件大小不能大于"
														+ maxSizeTitle + "m");
												isOK = false;
											}
										});
								return isOK;
							},
							done : function(e, data) {
								$('#html5uploadProsess-${param.appkey}')
								.hide();
								var url = data.result.url;
								var state = data.result.STATE;
								var message = data.result.MESSAGE;
								var id = data.result.id;
								var fileName = data.result.fileName;
								if (state == 1) {
									showErrorMessage(message)
									return;
								}
								try {
									fileUploadHandle('${param.appkey}', url,
											id, fileName);
								} catch (e) {
									alert("请实现fileUploadHandle(appkey,url,id,fileName);回调函数");
								}
							},
							progressall : function(e, data) {
								var progress = parseInt(data.loaded
										/ data.total * 100, 10);
								$('#html5uploadProsess-${param.appkey}').text(
										progress + '%');
								if (progress == 100) {
									$('#html5uploadProsess-${param.appkey}').text(
											'等待服务端处理...');
								} else {
									$('#html5uploadProsess-${param.appkey}')
											.show();
								}
							}
						});
	});

	//顯示错误消息弹框
	function showErrorMessage(message) {
		alert(message);
	}
</script>