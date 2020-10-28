<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>全部课程-<PF:ParameterValue key="config.sys.title" /></title>
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
<script
	src="<PF:basePath/>text/lib/bootstrap-treeview/bootstrap-treeview.min.js"></script>
</head>
<style>
.wlp-userspace-h1 {
	font-size: 36px;
	text-align: center;
}

.picLUp {
	background: url(text/img/demo/webfullstack-bg.jpg) no-repeat;
	width: 100%;
	background-size: 100% 100%;
	color: white;
	background-color: #000000;
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

.list-group {
	border-radius: 4px;
	-webkit-box-shadow: 0 0px 0px rgba(0, 0, 0, 0);
	box-shadow: 0 0px 0px rgba(0, 0, 0, 0);
}

.wlp-typeline-ul {
	margin-left: -20px;
}

.wlp-typeline-ul li {
	float: left;
	list-style-type: none;
	font-size: 16px;
	padding: 8px;
	margin-right: 20px;
	cursor: pointer;
}

.wlp-typeline-ul li:HOVER {
	color: #f20d0d;
}

.wlp-typeline-ul li.active {
	color: #f20d0d;
	font-weight: 700;
	background-color: #fef1f1;
	border-radius: 4px 4px 4px 4px;
}

.wlp-praiseback-box {
	background-color: #f2f2f2;
	padding: 13px;
	border-radius: 6px 6px 6px 6px;
	border: 1px solid #f2f2f2;
	margin: 8px;
	cursor: pointer;
	margin: 8px;
}

.wlp-praiseback-box:HOVER {
	background-color: #fafafa;
	padding: 13px;
	border-radius: 15px 15px 15px 15px;
	border: 1px dashed #dddddd;
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
	<div class="container-fluid "
		style="padding-top: 20px; background-color: #f3f5f7; padding-left: 0px; padding-right: 0px;">
		<div class="container-fluid "
			style="padding-top: 60px; background-color: #ffffff; box-shadow: 0 4px 8px 0 rgba(28, 31, 33, .1);">
			<div class="container">
				<div class="row">
					<div class="col-xs-12" style="padding-bottom: 30px;">
						<jsp:include page="commons/topSearchForm.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row" style="margin-top: 20px;">
				<div class="col-xs-3">
					<div id="tree"
						style="padding: 8px; padding-top: 20px; margin-bottom: 30px; padding-bottom: 20px; background-color: #ffffff; border-radius: 6px 6px 6px 6px;"></div>
					<script type="text/javascript">
						var treeObj;
						var treeBoxKey = "#tree";
						$(function() {
							treeObj = $(treeBoxKey).treeview({
								showBorder : false,
								data : '[{"text": "数据加载中...","id":"di1111"}]'
							});
							$
									.post(
											"category/Publoadclasstype.do?rootTypeid=${roottypeid}",
											{},
											function(flag) {
												treeObj = $(treeBoxKey)
														.treeview(
																{
																	data : flag.data,
																	showBorder : false,
																	highlightSelected : false,
																	onNodeSelected : function(
																			event,
																			data) {
																		callTreeselectBackFun(
																				event,
																				data);
																	}
																});
												$(treeBoxKey).treeview(
														'search',
														[ '${typeid}' ]);
											}, 'json');
						});
						function callTreeselectBackFun(event, data) {
							$('#chooseTypeModal').modal('hide');
							try {
								chooseClassTypeHandle('${param.appkey}', event,
										data);
							} catch (e) {
								alert('请实现方法	chooseClassTypeHandle(typekey,event, data);');
							}
						}
					</script>
				</div>
				<div class="col-xs-9">
					<div class="row">
						<c:forEach items="${newClasses.resultList}" var="node">
							<div class="col-md-4 wlp-classShow-box"
								onclick="openClassView('${node.ID}')"
								style="padding: 20px; padding-top: 20px;">
								<div style="text-align: center;">
									<img
										style="width: 230px; height: 161px; border-radius: 6px 6px 6px 6px;"
										src="${node.IMGURL}" alt="...">
								</div>
								<div class="media wlp-praiseback-box"
									style="text-align: center; height: 110px; overflow: hidden;">
									<div class="media-body">
										<div class="media-heading"
											style="height: 2.7em; overflow: hidden;">${node.NAME}</div>
										<div style="color: #999999;">
											<div>${node.TYPE.name}</div>
											<div
												style="text-overflow: ellipsis; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;">
												<span style="padding-top: 4px;">难度:${node.DIFFICULTY}</span>&nbsp;/&nbsp;<span
													style="padding-top: 4px;">作者:${node.OUTAUTHOR}</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					<div>
						<jsp:include page="/view/web-simple/commons/dataResultPages.jsp">
							<jsp:param value="${newClasses.currentPage}" name="current" />
							<jsp:param value="${newClasses.totalPage}" name="total" />
							<jsp:param
								value="category/Pubview.do?typeid=${typeid}&page=${newClasses.currentPage+1}&difficulty=${difficulty}"
								name="nexturl" />
							<jsp:param
								value="category/Pubview.do?typeid=${typeid}&page=${newClasses.currentPage-1}&difficulty=${difficulty}"
								name="lasturl" />
						</jsp:include>
						<script type="text/javascript">
							function openClassView(classid) {
								window.location = "classweb/Pubview.do?classid="
										+ classid;
							}
						</script>
					</div>
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
	function dosearchViewClass(page, typeid, difficulty) {
		var url = "<PF:basePath/>category/Pubview.do?typeid=" + typeid
				+ "&page=" + page + "&difficulty=" + difficulty;
		window.location = url;
	}
	function chooseClassTypeHandle(typekey, event, data) {
		dosearchViewClass(1, data.id, '${difficulty}');
	}
</script>
</html>