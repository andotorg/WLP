<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程編輯- <PF:ParameterValue key="config.sys.title" /></title>
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
<style>
</style>
</head>
<style>
<!--
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
						<jsp:param value="base" name="type" />
					</jsp:include>
				</div>
				<div class="col-xs-9">
					<div style="margin-top: 20px;">
						<form method="post" id="submitFormId"
							action="classweb/savebase.do">
							<input value="${classview.classt.id}" name="id" type="hidden">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group" id="classnameGroupId">
										<label for="classnameId" class="control-label">课程名称&nbsp;<span
											class="text-danger">*</span></label> <input name="name" type="text"
											class="form-control" id="classnameId" placeholder="课程名称"
											value="${classview.classt.name}"> <span
											id="classnameErrorId" class="help-block"></span>
									</div>
									<div class="form-group" id="classTypeGroupId">
										<label for="classTypeId" class="control-label">课程分类&nbsp;<span
											class="text-danger">*</span></label>
										<div class="input-group">
											<input type="text" class="form-control" id="classTypeId"
												value="${classview.type.name}" readonly="readonly"
												placeholder="点击右侧按钮选择分类..."> <input
												name="classtypeid" type="hidden" id="classtypeidValId"
												value="${classview.type.id}"> <span
												class="input-group-btn">
												<button class="btn btn-default"
													onclick="openClassTypeChooseWin()" type="button">选择分类</button>
											</span>
										</div>
										<span id="classTypeErrorId" class="help-block"></span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-6">
									<div class="form-group" id="difficultyGroupId">
										<label for="difficultyId" class="control-label">难易&nbsp;<span
											class="text-danger">*</span></label> <select id="difficultyId"
											name="difficulty" class="form-control"
											val="${classview.classt.difficulty}">
											<option value=""></option>
											<option value="1">入门</option>
											<option value="2">初级</option>
											<option value="3">中级</option>
											<option value="4">高级</option>
										</select> <span id="difficultyErrorId" class="help-block"></span>
									</div>
									<div class="form-group" id="outauthorGroupId">
										<label for="outauthorId" class="control-label">作者&nbsp;<span
											class="text-danger">*</span></label> <input name="outauthor"
											type="text" class="form-control" id="outauthorId"
											placeholder="作者" value="${classview.classt.outauthor}">
										<span id="outauthorErrorId" class="help-block"></span>
									</div>
									<div class="form-group" id="altimeGroupId">
										<label for="altimeId" class="control-label">课程时长（单位：小时）&nbsp;<span
											class="text-danger">*</span></label> <input name="altime" type="text"
											class="form-control" id="altimeId" placeholder="课程时长"
											value="${classview.classt.altime}"> <span
											id="altimeErrorId" class="help-block"></span>
									</div>
								</div>
								<div class="col-xs-6"
									style="text-align: center; padding-top: 20px;">
									<img style="width: 226px; height: 160px;" alt=""
										id="classViewImgId" class="img-thumbnail"
										src="${classview==null||classview.classt==null||classview.classt.imgid==null?'text/img/demo/colors-a4.jpg':classview.imgurl}">
									<div
										style="font-size: 12px; font-weight: 700; color: #666666; margin-top: 4px;">上传课程预览图</div>
									<input type="hidden" name="imgid" id="imgidId"
										value="${classview.classt.imgid}">
									<jsp:include
										page="/view/web-simple/commons/fileUploadCommons.jsp">
										<jsp:param value="classimg" name="appkey" />
										<jsp:param value="IMG" name="type" />
										<jsp:param value="上传图片" name="title" />
									</jsp:include>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group" id="shortintroGroupId">
										<label for="shortintroId" class="control-label">课程简述&nbsp;<span
											class="text-danger">*</span></label>
										<textarea class="form-control" id="shortintroId"
											name="shortintro" placeholder="简述" rows="3">${classview.classt.shortintro}</textarea>
										<span id="shortintroErrorId" class="help-block"></span>
									</div>
								</div>
							</div>
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
	<jsp:include page="/view/web-simple/commons/classTypeChoose.jsp">
		<jsp:param value="classtype" name="appkey" />
	</jsp:include>
</body>
<script>
	$(function() {
		$('select', '#submitFormId').each(function(i, obj) {
			var val = $(obj).attr('val');
			$(obj).val(val);
		});
		validateInput('classnameId', function(id, val, obj) {
			// 标题
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 128)) {
				return {
					valid : false,
					msg : '长度不能大于' + 128
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'classnameErrorId', 'classnameGroupId');
		validateInput('classTypeId', function(id, val, obj) {
			// 课程分类
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'classTypeErrorId', 'classTypeGroupId');
		validateInput('difficultyId', function(id, val, obj) {
			// 難易程度
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'difficultyErrorId', 'difficultyGroupId');

		validateInput('outauthorId', function(id, val, obj) {
			// 作者
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 64)) {
				return {
					valid : false,
					msg : '长度不能大于' + 64
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'outauthorErrorId', 'outauthorGroupId');

		validateInput('altimeId', function(id, val, obj) {
			// 时长
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (!valid_isNumber(val)) {
				return {
					valid : false,
					msg : '必须为数字'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'altimeErrorId', 'altimeGroupId');

		validateInput('shortintroId', function(id, val, obj) {
			// 课程简介
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 512)) {
				return {
					valid : false,
					msg : '长度不能大于' + 512
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'shortintroErrorId', 'shortintroGroupId');
	});
	//提交表单
	function submitForm() {
		if (!validate('submitFormId')) {
			$('#errormessageShowboxId').text('信息录入有误，请检查！');
			$('#errormessageShowboxId').show();
			return;
		}
		if (!$('#imgidId').val()) {
			$('#errormessageShowboxId').text('请上传课程预览图片！');
			$('#errormessageShowboxId').show();
			return;
		}
		$('#errormessageShowboxId').text('');
		$('#errormessageShowboxId').hide();
		if (confirm("是否提交本课程？")) {
			$('#submitButtonId').attr('disabled', 'disabled');
			$('#submitFormId').submit();
		}
	}
	//图片提交成功
	function fileUploadHandle(appkey, url, id, fileName) {
		if ('classimg' == appkey) {
			$('#classViewImgId').attr('src', url);
			$('#imgidId').val(id);
		}
	}
	//打開選擇分類窗口
	function openClassTypeChooseWin() {
		$('#chooseTypeModal').modal({
			keyboard : false
		})
	}
	//選中分類的回調函數
	function chooseClassTypeHandle(appkey, event, data) {
		$('#classtypeidValId').val(data.id);
		$('#classTypeId').val(data.text);
	}
</script>
</html>