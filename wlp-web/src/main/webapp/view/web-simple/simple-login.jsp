<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登录-<PF:ParameterValue key="config.sys.title" />
</title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="atext/include-web.jsp"></jsp:include>
<script src="text/javascript/base64.js"></script>
<script src="text/javascript/md5.js"></script>
<script src="text/javascript/encode.provider.js"></script>
</head>
<body>
	<script type="text/javascript">
		if (window != top) {
			$("#loginId").html(
					'<div style="font-size:25px;text-align:center;">'
							+ "无法在iframe中实现登录操作，正在跳转中。。。" + '<//div>');
			top.location.href = "login/webPage.html";
		}
	</script>
	<jsp:include page="commons/head.jsp"></jsp:include>
	<div class="containerbox" style="margin-top: 100px;">
		<div class="container ">
			<div
				style="box-shadow: 0px 0px 50px 15px rgba(0,0,0,0.1); margin: auto; border-radius: 6px 6px 6px 6px; width: 800px; border: 1px solid #e7e7e7; margin-top: 30px; margin-bottom: 100px; background-color: #fcfcfc;">
				<div class="panel-body" style="padding: 0px;">
					<div class="row">
						<div class="col-xs-6" style="padding: 50px;padding-top: 20px;padding-bottom: 10px;">
							<jsp:include page="commons/loginbox.jsp"></jsp:include>
						</div>
						<div class="col-xs-6"
							style="border-left: 1px solid #e7e7e7; padding-left: 0px;">
							<div
								style="height: 450px; overflow: hidden; border-radius: 0px 6px 6px 0px;">
								<img alt="" height="450px"
									src="text/img/demo/colors.jpg">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid " style="padding: 0px;">
		<jsp:include page="commons/foot.jsp"></jsp:include></div>
</body>
</html>