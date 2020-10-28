<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${hourview.classview.classt.name }-<PF:ParameterValue
		key="config.sys.title" /></title>
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
<link rel="stylesheet"
	href="<PF:basePath/>text/lib/kindeditor/editInner.css" />
<script charset="utf-8"
	src="<PF:basePath/>text/lib/super-validate/validate.js"></script>
</head>
<style>
.wlp-userspace-h1 {
	font-size: 36px;
	text-align: center;
}

.picLUp {
	width: 100%;
	background-size: 100% 100%;
	color: white;
	background-color: #2a2f32;
}

.wpl-class-tab .nav  li a {
	color: #ffffff;
}

.wpl-class-tab .nav  li a:HOVER {
	color: #666666;
}

.wpl-class-tab .nav .active a {
	color: #666666;
}

.wlp-a-nounderline a:HOVER {
	text-decoration: none;
	font-weight: 200;
}
</style>
<body>
	<div class="container-fluid">
		<div class="row">
			<!-- 导航 -->
			<jsp:include page="../commons/head.jsp"></jsp:include>
		</div>
	</div>
	<div class="container picLUp">
		<div class="row" id="hourViewId"
			style="color: #ffffff; margin-top: 70px;">
			<div class="col-xs-9" style="padding-left: 30px; color: #ffffff;">
				<div style="padding: 20px; font-size: 16px;">
					<span style="font-weight: 700; font-size: 18px;">${hourview.chapter.title}</span>
					&nbsp;/&nbsp;${hourview.hour.title}
				</div>
				<c:if
					test="${hourview.filebase.exname=='mp4'||hourview.filebase.exname=='MP4'}">
					<jsp:include page="commons/dplayer.jsp"></jsp:include>
				</c:if>
				<c:if
					test="${hourview.filebase.exname=='pdf'||hourview.filebase.exname=='PDF'}">
					<c:if test="${hourview.pinum>0}">
						<jsp:include page="commons/imgs.jsp"></jsp:include>
					</c:if>
					<c:if test="${hourview.pinum==0}">
						<jsp:include page="commons/pdf.jsp"></jsp:include>
					</c:if>
				</c:if>
			</div>
			<div class="col-xs-3 wlp-a-nounderline"
				style="color: #ffffff; height: 100%; overflow: auto;">
				<div style="padding: 20px; padding-left: 0px; font-size: 28px;">
					<a style="color: #ffffff; text-decoration: none;"
						href="classweb/Pubview.do?classid=${hourview.classview.classt.id }">
						${hourview.classview.classt.name}</a>
				</div>
				<div
					style="padding-bottom: 20px; padding-left: 0px; font-size: 12px; color: #cccccc;">${hourview.classview.classt.shortintro }</div>
				<div style="text-align: center;">
					<img class="img-rounded"
						style="width: 70%; margin-top: 0px; margin-bottom: 20px;"
						src="${hourview.classview.imgurl}">
				</div>
				<hr
					style="margin-bottom: 10px; margin-top: 10px; color: #666666; border-color: #666666;"
					class="wlp-userspace-hr">
				<jsp:include page="commons/chapter.jsp"></jsp:include>
			</div>
		</div>
	</div>

	<div class="container-fluid picLUp" style="min-height: 70px;">
		<!-- 
		<div class="container wpl-class-tab">
			<ul class="nav nav-tabs" style="margin-top: 20px;">
				<li role="presentation" class="active"><a>&nbsp;&nbsp;&nbsp;知识库&nbsp;&nbsp;&nbsp;</a></li>
				<li role="presentation"><a>&nbsp;&nbsp;&nbsp;测试答题&nbsp;&nbsp;&nbsp;</a></li>
				<li role="presentation"><a>&nbsp;&nbsp;&nbsp;资源附件&nbsp;&nbsp;&nbsp;</a></li>
				<li role="presentation"><a>&nbsp;&nbsp;&nbsp;问答&nbsp;&nbsp;&nbsp;</a></li>
			</ul>
		</div>-->
	</div>
	<div class="container-fluid" style="padding: 0px;">
		<!-- 页脚 -->
		<jsp:include page="../commons/foot.jsp"></jsp:include>
	</div>
</body>
<script type="text/javascript">
	function getClientHeight() {
		return document.documentElement.clientHeight - 140;
	}
	$(function() {
		setWindowHeight();
	});
	function setWindowHeight() {
		$('#hourViewId').height(getClientHeight());
	}
</script>
</html>