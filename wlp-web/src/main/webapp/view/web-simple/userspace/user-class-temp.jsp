<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>待发布课程- <PF:ParameterValue key="config.sys.title" /></title>
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
					<jsp:include page="commons/include-class-leftmenu.jsp"><jsp:param
							name="current" value="tempclass" /></jsp:include>
				</div>
				<div class="col-xs-9">
					<div style="margin-top: 20px;">
						<div class="wlp-userspace-h2">
							待发布课程 <a href="classweb/create.do"
								style="float: right; margin-top: -8px;" type="button"
								class="btn btn-info">创建课程</a>
						</div>
						<hr class="wlp-userspace-hr">
						<div class="wlp-v-list">
							<ul>
								<c:forEach items="${result.resultList }" var="node">
									<li><div class="media">
											<div class="media-left">
												<a href="#"> <img style="height: 100px; width: 141px;"
													class="media-object" src="${node.IMGURL}" alt="课程展示图">
												</a>
											</div>
											<div class="media-body"
												style="padding: 8px; padding-top: 0px;">
												<h4 class="media-heading">${node.NAME}</h4>
												<div style="padding: 4px; padding-left: 0px;">
													<div
														style="text-overflow: ellipsis; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;">
														${node.SHORTINTRO}</div>
												</div>
												<div style="font-size: 12px; padding: 4px;">
													<c:forEach items="${node.TYPES}" var="type"
														varStatus="stat">
														<c:if test="${stat.index>0 }">
															<span style="color: #eeeeee; font-weight: 700;">&nbsp;/&nbsp;</span>
														</c:if>
														<span style="color: #999999;">${type.name }</span>
													</c:forEach>
												</div>
												<div>
													<span class="label label-default" style="padding-top: 4px;">难度:${node.DIFFICULTY}</span>
													<span class="label label-default" style="padding-top: 4px;">时长:${node.ALTIME}小时</span>
													<span class="label label-default" style="padding-top: 4px;">作者:${node.OUTAUTHOR}</span>
													<div style="float: right; margin-top: -8px;">
														<button onclick="mngClass('${node.ID}')" type="button"
															class="btn btn-primary btn-sm">配置課程</button>
														<button onclick="delClass('${node.ID}')" type="button"
															class="btn btn-danger btn-sm">刪除</button>
														<button onclick="publicClass('${node.ID}')" type="button"
															class="btn btn-success btn-sm">发布</button>
														<a target="_blank"
															href="classweb/Pubview.do?classid=${node.ID}"
															class="btn btn-default btn-sm">预览</a>
													</div>
												</div>
											</div>
										</div></li>
								</c:forEach>
							</ul>
						</div>
						<jsp:include page="/view/web-simple/commons/dataResultPages.jsp">
							<jsp:param value="${result.currentPage}" name="current" />
							<jsp:param value="${result.totalPage}" name="total" />
							<jsp:param
								value="userspace/tempclass.do?page=${result.currentPage+1}"
								name="nexturl" />
							<jsp:param
								value="userspace/tempclass.do?page=${result.currentPage-1}"
								name="lasturl" />
						</jsp:include>
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
	//刪除課程
	function delClass(classid) {
		if (confirm("是否刪除该课程刪除后将不可恢复？")) {
			window.location = "<PF:basePath/>classweb/del.do?classid="
					+ classid;
		}
	}
	//配置課程
	function mngClass(classid) {
		window.location = "<PF:basePath/>classweb/mng.do?classid=" + classid;
	}

	//发布课程
	function publicClass(classid) {
		if (confirm("课程发布后将向所有用户开放，是否继续？")) {
			window.location = "<PF:basePath/>classweb/public.do?classid="
					+ classid;
		}
	}
</script>
</html>