<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:if test="${USEROBJ==null}">
	<h1 style="color: #666; font-size: 24px; font-weight: 700;">
		<PF:ParameterValue key="config.sys.title" />
		用户登录
	</h1>
	<hr />
	<form class="form-signin" role="form" id="loginFormId"
		action="login/websubmit.do" method="post">
		<div class="form-group">
			<label for="exampleInputEmail1"> 登录名 </label> <input type="text"
				class="form-control" placeholder="手机/邮箱/用户登录名" value="${loginname}"
				style="margin-top: 4px;" id="loginNameId" name="name" required
				autofocus>
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1"> 登录密码 </label> <input type="password"
				class="form-control" placeholder="请录入密码" style="margin-top: 4px;"
				id="loginPassWId" required> <input name="password"
				type="hidden" id="realPasswordId">
		</div>
		<PF:IfParameterEquals key="config.sys.verifycode.able" val="true">
			<c:if test="${sessionScope.LOGINERRORNUM<=0}">
				<div class="form-group">
					<label for="exampleInputEmail1">验证码</label>
					<div class="input-group">
						<input type="text" class="form-control" placeholder="请录入验证码"
							id="checkcodeId" name="checkcode">
						<div class="input-group-addon" style="padding: 0px;">
							<img id="checkcodeimgId"
								style="cursor: pointer; height: 30px; width: 100px;"
								src="webfile/Pubcheckcode.do" />
						</div>
					</div>
				</div>
			</c:if>
		</PF:IfParameterEquals>
		<!-- <input type="hidden" name="url" id="loginUrlId"> -->
		<div>
			<button class="btn btn-danger text-left" id="loginButtonId"
				style="margin-top: 4px; width: 100%;" type="button">登录</button>
		</div>
	</form>
	<c:if test="${STATE=='1'}">
		<div class="text-center" id="romovealertMessageErrorId"
			style="margin: 4px; color: red; border-top: 1px dashed #ccc; padding-top: 20px;">
			<span class="glyphicon glyphicon-exclamation-sign"></span> ${MESSAGE}
		</div>
	</c:if>
	<div class="text-center" id="alertMessageErrorId"
		style="margin: 4px; color: red; border-top: 1px dashed #ccc; padding-top: 20px;"></div>
</c:if>
<c:if test="${USEROBJ!=null}">
	<div>
		<div style="text-align: center;margin-top: 100px;margin-bottom: 50px;" >当前用户已登陆</div>
		<a class="btn btn-danger text-left" href="#"
			style="margin-top: 4px; width: 100%;" type="button">进入首页</a>
	</div>
</c:if>
<script type="text/javascript">
	$(function() {
		$('#alertMessageErrorId').hide();
		$('#loginButtonId')
				.bind(
						'click',
						function() {
							//校验验证码
							if ($('#checkcodeId').length > 0
									&& !$('#checkcodeId').val()) {
								$('#alertMessageErrorId').show();
								$('#romovealertMessageErrorId').hide();
								$('#alertMessageErrorId')
										.html(
												'<span class="glyphicon glyphicon-exclamation-sign"></span>请输入验证码');
								return;
							}
							if ($('#loginNameId').val()
									&& $('#loginPassWId').val()) {
								//$('#loginUrlId').val(document.referrer);
								$('#loginButtonId').addClass("disabled");
								$('#loginButtonId').text("提交中...");
								var scret_password = (AuthKeyProvider.encode(
										'${config_password_provider_type}', $(
												'#loginPassWId').val()));
								$('#realPasswordId').val(scret_password);
								$('#loginFormId').submit();
							} else {
								$('#alertMessageErrorId').show();
								$('#romovealertMessageErrorId').hide();
								$('#alertMessageErrorId')
										.html(
												'<span class="glyphicon glyphicon-exclamation-sign"></span>请输入用户名/密码');
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
		$('#loginNameId').keydown(function(e) {
			if (e.keyCode == 13) {
				//$('#loginUrlId').val(window.location.href);
				$('#loginButtonId').click();
			}
		});
		$('#loginPassWId').keydown(function(e) {
			if (e.keyCode == 13) {
				//$('#loginUrlId').val(window.location.href);
				$('#loginButtonId').click();
			}
		});
		$('#checkcodeId').keydown(function(e) {
			if (e.keyCode == 13) {
				//$('#loginUrlId').val(window.location.href);
				$('#loginButtonId').click();
			}
		});
	});
//-->
</script>