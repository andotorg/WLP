<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页- <PF:ParameterValue key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="index,follow">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<jsp:include page="atext/include-web.jsp"></jsp:include>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<!-- 导航 -->
			<jsp:include page="commons/head.jsp"></jsp:include>
		</div>
	</div>
	<div class="container-fluid "
		style="background-color: #eceef1; padding-top: 100px; padding-bottom: 50px;">
		<div class="container hidden-xs hidden-sm hidden-md" style="">
			<!-- 橫幅,分类 -->
			<jsp:include page="commons/maxBanner.jsp"></jsp:include>
		</div>
		<div class="container" style="margin-top: 50px;">
			<div class="row wlp-column-title">
				<div class="col-xs-12">
					<span class="main">推荐课程</span> <span class="note">2012年3月4日更新</span>
				</div>
			</div>
			<div class="row ">
				<jsp:include page="commons/praiseClass.jsp"></jsp:include>
			</div> 
		</div>
		<!-- 
		<div class="container" style="margin-top: 50px;">
			<div class="row wlp-column-title">
				<div class="col-xs-12">
					<span class="main">推荐专业</span> <span class="note">2012年3月4日更新</span>
				</div>
			</div>
			<div class="row ">
				<jsp:include page="commons/praiseSpecial.jsp"></jsp:include>
			</div>
		</div> -->
	</div>
	<div class="container-fluid"
		style="padding-top: 20px; padding-bottom: 20px;">
		<div class="container" style="margin-top: 50px;">
			<div class="row wlp-column-title">
				<div class="col-xs-12">
					<span class="main">最新课程</span> <span class="note">${newClasses.resultList[0].ETIME}更新</span>
				</div>
			</div>
			<div class="row ">
				<jsp:include page="commons/newClass.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<!-- 
	<div class="container-fluid"
		style="padding-top: 20px; padding-bottom: 20px; background-color: #eceef1;">
		<div class="container" style="margin-top: 50px;">
			<div class="row wlp-column-title">
				<div class="col-xs-12">
					<span class="main">最新专业</span> <span class="note">2012年3月4日更新</span>
				</div>
			</div>
			<div class="row ">
				<jsp:include page="commons/newSpecial.jsp"></jsp:include>
			</div>
		</div>
	</div> -->
	<div class="container-fluid" style="padding: 0px;"> 
		<!-- 页脚 -->
		<jsp:include page="commons/foot.jsp"></jsp:include>
	</div>
</body>
</html>