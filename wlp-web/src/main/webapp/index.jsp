<%@page isELIgnored="false" %>
<%@page import="com.farm.parameter.service.impl.ConstantVarService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.farm.parameter.FarmParameterService"%>
<%@page import="com.farm.util.spring.BeanFactory"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title><PF:ParameterValue key="config.sys.title" /></title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="/view/web-simple/atext/include-web.jsp"></jsp:include>
<style>
.wcp-hometypes a {
	font-size: 10px;
	margin-left: 4px;
}
</style>
</head>
<body style="background-color: #000000;">
	<div class="containerbox ">
		<div class="container">
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<div
						style="color: #ffffff; text-align: center; margin: auto; margin-top: 200px;">
						<div>
							<img style="max-width: 64px; max-height: 64px;"
								src="<PF:basePath/>webfile/Publogo.do" alt="..."
								class="img-rounded">
						</div>
						<div>
							<h1>
								<PF:ParameterValue key="config.sys.title" />
							</h1>
							<div
								style="border-bottom: 1px dashed #cccccc; padding-bottom: 30px;">

							</div>
							<h3 style="color: #cccccc; font-size: 14px; padding-top: 20px;">
								<PF:ParameterValue key="config.sys.foot" />
								-V
								<PF:ParameterValue key="config.sys.version" />
								-社区版
								<br /> <br /> <a style="color: #ffffff;" 
									href="<PF:defaultIndexPage/>" title="立即加载"><span
									id="waitTime"></span>加载首页....</a> <br /> <br />
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script language="javascript" type="text/javascript">
	var waitTime = 1;
	$(function() {
		if (waitTime == 0) {
			gotoHomePage();
		} else {
			$('#waitTime').text(waitTime + "秒后 ");
			waitTime = waitTime - 1;
			setInterval("redirect()", 1000);
		}
	});
	function redirect() {
		if (waitTime >= 0) {
			$('#waitTime').text(waitTime + "秒后 ");
		}
		if (waitTime < 0) {
			gotoHomePage();
		}
		waitTime--;
	}
	function gotoHomePage() { 
		location.href = '<PF:defaultIndexPage/>';
	}
</script>
</html>