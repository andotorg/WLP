<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程介绍- <PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/super-validate/validate.js"></script>

<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/themes/default/default.css" />
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="<PF:basePath/>text/lib/kindeditor/zh-CN.js"></script>
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-kindeditor.css" />
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/wcpplug/wcp-multiimage-kindeditor.js"></script>
<script charset="utf-8"
	src="<PF:basePath/>text/lib/kindeditor/htmltags.js"></script>

<!-- 附件批量上传组件 -->
<script src="text/lib/fileUpload/jquery.ui.widget.js"></script>
<script src="text/lib/fileUpload/jquery.iframe-transport.js"></script>
<script src="text/lib/fileUpload/jquery.fileupload.js"></script>
<link href="text/lib/fileUpload/jquery.fileupload.css" rel="stylesheet">
<!-- 附件批量上传组件 -->

<style>
</style>
</head>
<style>
<!--
.wlp-userspace-h2 {
	font-size: 16px;
	font-weight: 700;
	padding-left: 20px;
}

.wlp-userspace-hr {
	margin-top: 8px;
	margin-bottom: 8px;
}

form .help-block {
	font-size: 12px;
}
-->
</style>
<body>
	<div class="container-fluid">
		<div class="row">
			<!-- 导航 -->
			<jsp:include page="../commons/head.jsp"></jsp:include>
		</div>
	</div>
	<div class="container-fluid"
		style="padding-top: 20px; padding-bottom: 20px;">
		<div class="container" style="margin-top: 50px;">
			<div class="row">
				<div class="col-xs-3">
					<jsp:include page="commons/classLeftInfo.jsp">
						<jsp:param value="introduction" name="type" />
					</jsp:include>
				</div>
				<div class="col-xs-9">
					<div style="margin-top: 20px;">
						<div class="wlp-userspace-h2">课程简介</div>
						<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
						<form method="post" id="submitFormId"
							action="classweb/saveintroduction.do">
							<input value="${classview.classt.id}" name="id" type="hidden">
							<textarea name="text" id="contentId"
								style="height: 470px; width: 100%;">${fn:replace(fn:replace(classview.introText,'&lt;', '&amp;lt;'),'&gt;', '&amp;gt;')}</textarea>
							<div style="height: 16px;"></div>
							<a onclick="submitForm();" id="submitButtonId"
								class="btn btn-success">提交保存</a> <a
								href="userspace/tempclass.do" class="btn btn-default">返回</a>
							<p class="bg-danger"
								style="padding: 20px; margin-top: 20px; color: #f01414; display: none;"
								id="errormessageShowboxId"></p>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="padding: 0px;">
		<!-- 页脚 -->
		<jsp:include page="../commons/foot.jsp"></jsp:include>
	</div>
</body>
<script>
	$(function() {

	});
	var contentMaxSize = 50000;
	//提交表单
	function submitForm() {
		syncEditorcontent();
		if ($('#contentId').val() == null || $('#contentId').val() == '') {
			$('#errormessageShowboxId').text('请录入文档内容！');
			$('#errormessageShowboxId').show();
			return false;
		}
		if ($('#contentId').val().length > contentMaxSize) {
			$('#errormessageShowboxId').text(
					'文档内容超长（' + $('#contentId').val().length + '>'
							+ contentMaxSize + ')');
			$('#errormessageShowboxId').show();
			return false;
		}
		$('#errormessageShowboxId').text('');
		$('#errormessageShowboxId').hide();
		if (confirm("是否提交本课程？")) {
			$('#submitButtonId').attr('disabled', 'disabled');
			$('#submitFormId').submit();
		}
	}
</script>
<script type="text/javascript">
	var editor;
	var basePath = "<PF:basePath/>";
	$(function() {
		editor = KindEditor
				.create(
						'textarea[id="contentId"]',
						{
							resizeType : 1,
							afterChange : function() {
								//生成导航目录
							},
							cssPath : '<PF:basePath/>text/lib/kindeditor/editInner.css',
							uploadJson : basePath + 'upload/media.do',
							formatUploadUrl : false,
							allowPreviewEmoticons : false,
							allowImageRemote : true,
							allowImageUpload : true,
							items : [ 'fontsize', 'forecolor', 'bold',
									'italic', 'underline', '|', 'removeformat',
									'justifyleft', 'justifycenter',
									'insertorderedlist', 'insertunorderedlist',
									'formatblock', 'table', 'hr', 'link',
									'media', 'code', 'wcpimgs', 'source' ],
							afterCreate : function() {
								//粘贴的文件直接上传到后台
								pasteImgHandle(this, basePath
										+ 'upload/base64img.do');
								//禁止粘贴图片等文件:pasteNotAble(this.edit);
							},
							htmlTags : htmlTagsVar
						});
	});
	//刷新超文本内容到表单中
	function syncEditorcontent() {
		editor.sync();
	}
	//获得潮文本内容
	function getEditorcontent() {
		return editor.html();
	}
	//获得潮文本内容
	function getEditorcontentText() {
		return editor.text();
	}
	//设置超文本内容
	function setEditorcontent(text) {
		editor.html(text);
	}
	//禁止kindeditor中粘贴文件
	function pasteNotAble(edit) {
		var doc = edit.doc;
		var cmd = edit.cmd;
		$(doc.body).bind('paste', function(ev) {
			var $this = $(this);
			var dataItem = ev.originalEvent.clipboardData.items[0];
			if (dataItem) {
				var file = dataItem.getAsFile();
				if (file) {
					//暂时不处理，文件上传
					return false;
				}
			}
		});
	}
</script>
</html>