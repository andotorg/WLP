<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人信息修改- <PF:ParameterValue key="config.sys.title" /></title>
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
.wlp-v-list ul li {
	list-style-type: none;
	padding: 10px;
	color: #666666;
	padding-top: 20px;
	padding-bottom: 20px;
}
</style>
</head>
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
					<jsp:include page="commons/include-setting-leftmenu.jsp"><jsp:param
							name="current" value="userinfo" /></jsp:include>
				</div>
				<div class="col-xs-9">
					<div style="margin-top: 20px;">
						<div class="wlp-userspace-h2">
							<div class="row">
								<div class="col-xs-6">个人信息修改： ${USEROBJ.loginname }</div>
								<div class="col-xs-6">
									<div class="input-group input-group-sm"></div>
								</div>
							</div>
						</div>
						<hr class="wlp-userspace-hr">
						<div style="padding: 20px;">
							<form role="form" action="userspace/editCurrentUser.do"
								id="registSubmitFormId" method="post">
								<input type="hidden" name="id" value="${USEROBJ.id }" />
								<div class="row">
									<div class="col-md-12"></div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group" id="photoGroupId">
											<label for="exampleInputEmail1"><span
												class="text-danger">*</span> 头像&nbsp;: </label>
											<c:if test="${!empty USEROBJ.imgid}">
												<img id="imgEditBoxId"
													src="download/Pubphone.do?id=${USEROBJ.imgid }" alt="头像"
													class="img-thumbnail"
													style="width: 128px; height: 128px; margin: 20px;" />
											</c:if>
											<c:if test="${empty USEROBJ.imgid}">
												<img id="imgEditBoxId"
													src="<PF:basePath/>text/img/photo.png" alt="头像"
													style="width: 128px; height: 128px; margin: 20px;" />
											</c:if>
											<input type="hidden" name="photoid" id="photoid"
												value="${USEROBJ.imgid}" style="width: 0px; border: 0px;" />
											<jsp:include
												page="/view/web-simple/commons/fileUploadCommons.jsp">
												<jsp:param value="userPhoto" name="appkey" />
												<jsp:param value="IMG" name="type" />
												<jsp:param value="上传头像" name="title" />
											</jsp:include>
											<span id="photoErrorId" class="help-block"></span>
										</div>
									</div>
								</div>
								<PF:IfParameterEquals key="config.useredit.showName" val="true">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group" id="nameGroupId">
												<label for="nameid" class="control-label"><span
													class="text-danger">*</span> 姓名&nbsp;: </label> <input type="text"
													class="form-control" name="name" id="nameid"
													style="max-width: 300px;" placeholder="输入真实姓名"
													value="${USEROBJ.name}" /> <span id="nameErrorId"
													class="help-block"></span>
											</div>
										</div>
									</div>
									<script type="text/javascript">
										
									</script>
								</PF:IfParameterEquals>
								<div class="row">
									<div class="col-md-12">
										<button type="button" id="registSubmitButtonId"
											class="btn btn-success">保存用户信息</button>
										<p class="bg-danger"
											style="padding: 20px; margin-top: 20px; color: #f01414; display: none;"
											id="errormessageShowboxId"></p>
									</div>
								</div>
							</form>
						</div>
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
<c:if test="${OK=='OK'}"> 
	<script type="text/javascript">
		alert("修改成功!");
	</script>
</c:if>
<c:if test="${STATE=='1'}">
	<script type="text/javascript">
		$('#errormessageShowboxId').text('${MESSAGE}');
		$('#errormessageShowboxId').show();
	</script>
</c:if>
<script type="text/javascript">
	//图片提交成功
	function fileUploadHandle(appkey, url, id, fileName) {
		if ('userPhoto' == appkey) {
			$('#imgEditBoxId').attr('src', url);
			$('#photoid').val(id);
		}
	}
	$(function() {
		$('.wcp-defaultPhotos').bind('click', function() {
			var src = 'actionImg/Publoadphoto.do?id=' + $(this).attr('id');
			$('#imgEditBoxId').attr('src', src);
			$('#imgEditBoxId2').attr('src', src);
			$('#photoid').val($(this).attr('id'));
		});
		$('#registSubmitButtonId').bind('click', function() {
			if (!validate('registSubmitFormId')) {
				$('#errormessageShowboxId').text('信息录入有误，请检查！');
				$('#errormessageShowboxId').show();
			} else {
				if (confirm("是否提交数据?")) {
					$('#registSubmitButtonId').addClass("disabled");
					$('#registSubmitButtonId').text("提交中...");
					$('#registSubmitFormId').submit();
				}
			}
		});

		validateInput('nameid', function(id, val, obj) {
			// 用户名
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (!valid_maxLength(val, 2 - 1)) {
				return {
					valid : false,
					msg : '不能小于2个字符'
				};
			}
			if (valid_maxLength(val, 16)) {
				return {
					valid : false,
					msg : '不能大于16个字符'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'nameErrorId', 'nameGroupId');

		validateInput('photoid', function(id, val, obj) {

			alert(val);
			// 用户名
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
		}, 'photoErrorId', 'photoGroupId');

		$('#openChooseTypeButtonId').bind('click', function() {
			$('#myModal').modal({
				keyboard : false
			})
		});
		$('#emailTypeId a').bind('click', function() {
			var emailTitle = $('#emailid').val();
			if (emailTitle != null && emailTitle.indexOf("@") > 0) {
				emailTitle = emailTitle.substr(0, emailTitle.indexOf('@'));
			} else {
				emailTitle = "";
			}
			$('#emailid').val(emailTitle + $(this).text());
		});

	});
</script>
</html>