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
							name="current" value="publicclass" /></jsp:include>
				</div>
				<div class="col-xs-9">
					<div style="margin-top: 20px;">
						<div class="wlp-userspace-h2">
							<div class="row">
								<div class="col-xs-6">已发布课程</div>
								<div class="col-xs-6">
									<div class="input-group input-group-sm">
										<input type="text" class="form-control" id="classTypeId"
											value="${ctype.name}" readonly="readonly"
											placeholder="点击右侧按钮选择分类..."> <input
											name="classtypeid" type="hidden" id="classtypeidValId"
											value="${ctype.id}"> <span class="input-group-btn">
											<button class="btn btn-default"
												onclick="openClassTypeChooseWin()" type="button">查询分类</button>
											<button class="btn btn-default"
												onclick="clearClassTypeHandle()" type="button">清空</button>
										</span>
									</div>
								</div>
							</div>
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
														<button onclick="tempClass('${node.ID}')" type="button"
															class="btn btn-warning btn-sm">取消发布</button>
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
								value="userspace/publicclass.do?page=${result.currentPage+1}&classtype=${classtype}"
								name="nexturl" />
							<jsp:param
								value="userspace/publicclass.do?page=${result.currentPage-1}&classtype=${classtype}"
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
	<jsp:include page="/view/web-simple/commons/classTypeChoose.jsp">
		<jsp:param value="classtype" name="appkey" />
	</jsp:include>
</body>
<script type="text/javascript">
	//发布课程
	function tempClass(classid) {
		if (confirm("课程取消发布后将转移到待发布界面，是否继续？")) {
			$.post('classweb/temp.do', {
				'classid' : classid
			}, function(flag) {
				if (flag.STATE == 0) {
					location.reload();
				} else {
					alert(flag.MESSAGE);
				}
			}, 'json');
		}
	}
	//打開選擇分類窗口
	function openClassTypeChooseWin() {
		$('#chooseTypeModal').modal({
			keyboard : false
		})
	}
	//選中分類的回調函數
	function chooseClassTypeHandle(appkey, event, data) {
		$('#classtypeidValId').val(data.id);
		$('#classTypeId').val(data.text);
		window.location = "userspace/publicclass.do?classtype=" + data.id;
	}

	//清空分类查询条件
	function clearClassTypeHandle() {
		window.location = "userspace/publicclass.do";
	}
</script>
</html>