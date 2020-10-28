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

.wlp-hour-box:HOVER {
	background-color: #eeeeee;
	border: 1px dashed #aaaaaa;
}

.wlp-hour-box {
	border: 1px dashed #ffffff;
	padding: 4px;
	padding-top: 8px;
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
						<jsp:param value="chapter" name="type" />
					</jsp:include>
				</div>
				<div class="col-xs-9">
					<div style="margin-top: 20px;">
						<div class="wlp-userspace-h2">课程章节</div>
						<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
						<c:forEach items="${chapters }" var="node">
							<div style="margin: 40px; margin-bottom: 12px;">
								<div class="media">
									<div class="media-left"
										style="padding: 20px; padding-top: 0px; padding-left: 0px;">
										<div style="width: 30px; text-align: center;">
											<span style="font-size: 26px; color: #666666;"><i
												class=" glyphicon glyphicon-expand "></i></span> <span
												style="font-size: 14px; font-weight: 700; color: #aaaaaa;">NO.${node.sort }</span>
										</div>
									</div>
									<div class="media-body">
										<h4 class="media-heading"
											style="font-weight: 700; font-size: 18px;">${node.title }</h4>
										<div
											style="padding-top: 6px; font-size: 14px; color: #999999;">${node.note }</div>
									</div>
								</div>
								<c:forEach items="${hours}" var="hour">
									<c:if test="${hour.chapterid ==node.id}">
										<div class="media wlp-hour-box" style="margin-left: 40px;">
											<div class="media-left">
												<div style="width: 24px; text-align: right;">
													<span style="font-size: 18px; color: #666666;"><i
														class="glyphicon glyphicon-play-circle"></i></span>
												</div>
											</div>
											<div class="media-body">
												<h4 class="media-heading"
													style="font-weight: 200; margin-top: 2px; font-size: 16px;">${hour.title }</h4>
											</div>
											<div class="media-right">
												<div class="btn-group btn-group-xs" role="group"
													style="width: 125px; text-align: right;" aria-label="...">
													<button type="button"
														onclick="openHourFormWin('${hour.chapterid}','${hour.id}')"
														class="btn btn-primary">編輯课时</button>
													<button type="button" onclick="delHour('${hour.id}')"
														class="btn btn-danger">刪除课时</button>
												</div>
											</div>
										</div>
									</c:if>
								</c:forEach>
								<div style="text-align: right; padding-top: 8px;">
									<div class="btn-group btn-group-sm" role="group"
										aria-label="...">
										<button type="button" class="btn btn-info"
											onclick="openHourFormWin('${node.id}')">添加课程</button>
										<button type="button"
											onclick="openChapterFormWin('${node.id}')"
											class="btn btn-primary">編輯章节</button>
										<button type="button" onclick="delChapter('${node.id}')"
											class="btn btn-danger">刪除章节</button>
									</div>
								</div>
							</div>
							<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
						</c:forEach>
						<a onclick="openChapterFormWin()" class="btn btn-info">添加新章节</a> <a
							href="userspace/tempclass.do" class="btn btn-default">返回</a>
						<p class="bg-danger"
							style="padding: 20px; margin-top: 20px; color: #f01414; display: none;"
							id="errormessageShowboxId"></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="padding: 0px;">
		<!-- 页脚 -->
		<jsp:include page="../commons/foot.jsp"></jsp:include>
	</div>
	<jsp:include page="commons/includeChapterForm.jsp"></jsp:include>
	<jsp:include page="commons/includeHourForm.jsp"></jsp:include>
</body>
<script>
	$(function() {

	});
	//編輯或添加章节
	function openChapterFormWin(id) {
		$('#classChapterForm').modal({
			keyboard : false
		//,backdrop : false
		});
		//初始化章節表單
		initChapterForm(id);
	}
	//編輯或添加课时
	function openHourFormWin(chapterid, hourid) {
		$('#classhourForm').modal({
			keyboard : false
		//,backdrop : false
		});
		//初始化课时表單
		inithourForm(chapterid, hourid);
	}
	//刪除章节
	function delChapter(chapterid) {
		if (confirm("是否刪除本章节？")) {
			$.post('classweb/delchapter.do', {
				'chapterid' : chapterid
			}, function(flag) {
				if (flag.STATE == 0) {
					location.reload();
				} else {
					alert(flag.MESSAGE);
				}
			}, 'json');
		}
	}

	//刪除课时
	function delHour(hourid) {
		if (confirm("是否刪除本章节？")) {
			$.post('classweb/delhour.do', {
				'hourid' : hourid
			}, function(flag) {
				if (flag.STATE == 0) {
					location.reload();
				} else {
					alert(flag.MESSAGE);
				}
			}, 'json');
		}
	}
</script>
</html>