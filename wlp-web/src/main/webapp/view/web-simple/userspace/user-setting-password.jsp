<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>密码修改- <PF:ParameterValue key="config.sys.title" /></title>
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
<script src="text/javascript/base64.js"></script>
<script src="text/javascript/md5.js"></script>
<script src="text/javascript/encode.provider.js"></script>
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
							name="current" value="password" /></jsp:include>
				</div>
				<div class="col-xs-9">
					<div style="margin-top: 20px;">
						<div class="wlp-userspace-h2">
							<div class="row">
								<div class="col-xs-6">密码修改</div>
								<div class="col-xs-6">
									<div class="input-group input-group-sm"></div>
								</div>
							</div>
						</div>
						<hr class="wlp-userspace-hr">
						<div style="padding: 20px;">
							<div class="row">
								<div class="col-xs-6">
									<form role="form"
										action="userspace/editCurrentUserPwdCommit.do"
										id="registSubmitFormId" method="post">
										<div class="row">
											<div class="col-md-12">
												<div class="form-group" id="passwordGroupId">
													<label for="passwordId" class="control-label"> 原始密码
														<span class="text-danger">*</span>
													</label>
													<div class="row">
														<div class="col-md-9">
															<input type="password" class="form-control"
																name="password" value="" id="passwordId"
																placeholder="输入当前密码" />
														</div>
														<div class="col-md-3"></div>
													</div>
													<span id="passwordErrorId" class="help-block"></span>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<div class="form-group" id="newPassword1GroupId">
													<label for="newPasswordId1" class="control-label">
														新密码 <span class="text-danger">*</span>
													</label>
													<div class="row">
														<div class="col-md-9">
															<input type="password" id="newPasswordId1"
																class="form-control" name="newPassword"
																placeholder="输入新密码" />
														</div>
														<div class="col-md-3"></div>
													</div>
													<span id="newPassword1ErrorId" class="help-block"></span>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<div class="form-group" id="newPassword2GroupId">
													<label for="newPasswordId2" class="control-label">
														重复新密码 <span class="text-danger">*</span>
													</label>
													<div class="row">
														<div class="col-md-9">
															<input type="password" id="newPasswordId2"
																class="form-control" placeholder="重复输入新密码" />
														</div>
														<div class="col-md-3"></div>
													</div>
													<span id="newPassword2ErrorId" class="help-block"></span>
												</div>
											</div>
										</div>
										<PF:IfParameterEquals key="config.sys.verifycode.able"
											val="true">
											<div class="row">
												<div class="col-md-12">
													<div class="form-group" id="codeGroupId">
														<label for="checkcodeId" class="control-label">验证码<span
															class="text-danger">*</span>
														</label>
														<div class="input-group"
															style="width: 200px; text-align: left;">
															<input type="text" class="form-control" placeholder="验证码"
																id="checkcodeId" name="checkcode"
																style="float: left; width: 100px;"><img
																id="checkcodeimgId"
																style="cursor: pointer; height: 34px; width: 100px; float: right;"
																src="webfile/Pubcheckcode.do" />
														</div>
														<span id="codeErrorId" class="help-block"></span>
													</div>
												</div>
											</div>
											<script type="text/javascript">
												$(function() {
													validateInput(
															'checkcodeId',
															function(id, val,
																	obj) {
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
															}, 'codeErrorId',
															'codeGroupId');
												});
											</script>
										</PF:IfParameterEquals>
										<div class="row">
											<div class="col-md-12">
												<button type="button" id="registSubmitButtonId"
													class="btn btn-success">提交新密碼</button>
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

<c:if test="${STATE=='1'}">
	<script type="text/javascript">
		$('#errormessageShowboxId').text('${MESSAGE}');
		$('#errormessageShowboxId').show();
	</script>
</c:if>
<c:if test="${OK=='OK'}"> 
	<script type="text/javascript">
		alert("修改成功!");
	</script>
</c:if>

<script type="text/javascript">
	$(function() {
		$('#passwordId').val('1');
		$('#registSubmitButtonId').bind(
				'click',
				function() {
					if (!validate('registSubmitFormId')) {
						$('#errormessageShowboxId').text('信息录入有误，请检查！');
						$('#errormessageShowboxId').show();
					} else {
						if (confirm("是否提交数据?")) {
							var loginname = '${USEROBJ.loginname}';
							var secret1 = (AuthKeyProvider.encode(
									'${config_password_provider_type}', $(
											'#passwordId').val()));
							$('#passwordId').val(secret1);
							var secret2 = (AuthKeyProvider.encode(
									'${config_password_provider_type}', $(
											'#newPasswordId1').val()));
							$('#newPasswordId1').val(secret2);
							$('#registSubmitFormId').submit();
							$('#registSubmitButtonId').addClass("disabled");
							$('#registSubmitButtonId').text("提交中...");
						}
					}
				});
		$('#checkcodeimgId').bind(
				"click",
				function(e) {
					$('#checkcodeimgId').attr(
							"src",
							"webfile/Pubcheckcode.do?time="
									+ new Date().getTime());
				});
		validateInput('passwordId', function(id, val, obj) {
			// 当前密码
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (!valid_maxLength(val, 4 - 1)) {
				return {
					valid : false,
					msg : '不能小于4个字符'
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
		}, 'passwordErrorId', 'passwordGroupId');
		validateInput('newPasswordId1', function(id, val, obj) {
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
					msg : '不能大于32个字符'
				};
			}
			try {
				var regex = new RegExp(/${config_sys_password_update_regex}/);
				if (!regex.test(val)) {
					return {
						valid : false,
						msg : '${config_sys_password_update_tip}'
					};
				}
			} catch (e) {
				//正则表达式验证失败
				alert(e);
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'newPassword1ErrorId', 'newPassword1GroupId');
		validateInput('newPasswordId2', function(id, val, obj) {
			// 重录密码
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if ($('#newPasswordId1').val() != $('#newPasswordId2').val()) {
				return {
					valid : false,
					msg : '两次密码输入不一样'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'newPassword2ErrorId', 'newPassword2GroupId');
	});
</script>
</html>