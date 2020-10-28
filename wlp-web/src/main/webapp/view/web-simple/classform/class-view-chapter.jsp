<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${classview.classt.name }-<PF:ParameterValue
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
	background-color: #424f5c;
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

.wlp-hour-box:HOVER {
	background-color: #fef3f3;
	color: #f20d0d;
}

.wlp-hour-box {
	padding: 4px;
	padding-top: 8px;
	cursor: pointer;
}
</style>
<body>
	<div class="container-fluid">
		<div class="row">
			<!-- 导航 -->
			<jsp:include page="../commons/head.jsp"></jsp:include>
		</div>
	</div>
	<div class="container-fluid  picLUp" style="padding-top: 20px;">
		<div class="container">
			<div class="row">
				<div class="col-xs-12">
					<jsp:include page="commons/viewClassHeadInfo.jsp"></jsp:include>
				</div>
			</div>
		</div>
		<div class="container wpl-class-tab">
			<ul class="nav nav-tabs" style="margin-top: 20px;">
				<c:if test="${!empty classview.introText }">
					<li role="presentation"><a
						href="classweb/Pubview.do?classid=${classview.classt.id }">&nbsp;&nbsp;&nbsp;课程介绍&nbsp;&nbsp;&nbsp;</a></li>
				</c:if>
				<li role="presentation" class="active"><a id="backTabLi"
					href="classweb/Pubview.do?type=chapter&classid=${classview.classt.id }">&nbsp;&nbsp;&nbsp;章节目录&nbsp;&nbsp;&nbsp;</a></li>
			</ul>
		</div>
	</div>
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<div class="col-xs-9">
					<div style="margin-top: 50px; margin-bottom: 100px;"
						class="ke-content">
						<c:forEach items="${classview.chapters }" var="node">
							<div style="margin-bottom: 12px;">
								<div class="media">
									<div class="media-left hidden-xs"
										style="padding: 20px; padding-top: 0px; padding-left: 0px;">
										<div style="width: 30px; text-align: center;">
											<span style="font-size: 26px; color: #666666;"><i
												class=" glyphicon glyphicon-expand "></i></span>
										</div>
									</div>
									<div class="media-body">
										<h4 class="media-heading"
											style="font-weight: 700; font-size: 18px;">${node.title }</h4>
										<div
											style="padding-top: 6px; font-size: 14px; color: #999999;">${node.note }</div>
										<c:forEach items="${classview.hours}" var="hour">
											<c:if test="${hour.chapterid ==node.id}">
												<div class="media wlp-hour-box"
													onclick="publicClass('${hour.classid }', '${hour.id }')">
													<div class="media-left hidden-xs">
														<div style="width: 24px; text-align: right;">
															<span style="font-size: 18px;"><i
																class="glyphicon glyphicon-play-circle"></i></span>
														</div>
													</div>
													<div class="media-body">
														<h4 class="media-heading"
															style="font-weight: 200; margin-top: 6px; font-size: 16px;">${hour.title }</h4>
													</div>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
							<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
						</c:forEach>
					</div>
				</div>
				<div class="col-xs-3">
					<jsp:include page="commons/includeUserProcess.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="padding: 0px;">
		<!-- 页脚 -->
		<jsp:include page="../commons/foot.jsp"></jsp:include>
	</div>
</body>
<script type="text/javascript">
	//发布课程
	function publicClass(classid, hourid) {
		window.location = "<PF:basePath/>hourweb/PubContinue.do?classid="
				+ classid + "&hourid=" + hourid;
	}
</script>
</html>