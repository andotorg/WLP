<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.wlp-home-maximg {
	border-radius: 15px 15px 15px 15px;
	overflow: hidden;
}

.wlp-home-maximg .carousel-inner img {
	height: 400px;
	width: 100%;
	overflow: hidden;
}

.wlp-home-maximg .menuContent {
	height: 400px;
	padding-left: 10px;
	padding-top: 20px;
	padding-bottom: 20px;
}

.wlp-home-maximg .menuContent .item {
	padding: 15px;
	padding-left: 20px;
	border-radius: 4px 0px 0px 4px;
	cursor: pointer;
}

.wlp-home-maximg .menuContent .item a {
	color: #bbbbbb;
	text-decoration: none;
}

.wlp-home-maximg .menuContent .item:HOVER {
	background-color: #666666;
}

.wlp-home-maximg .menuContent .item:HOVER  a {
	color: #ffffff;
}

.wlp-home-maximg .menuContent .item a .glyphicon {
	float: right;
}

.wlp-home-maximg .col-md-9 {
	padding-left: 0px;
	padding-right: 0px;
}

.menu-float-infobox {
	background-color: #ffffff;
	height: 100%;
	min-width: 300px;
	max-width: 60%;
	position: absolute;
	z-index: 999999;
	padding: 20px;
	display: none;
	max-width: 60%;
	overflow: auto;
}

.leve2Title {
	clear: both;
	color: #1c1f21;
	font-size: 14px;
	font-weight: 700;
	padding: 10px;
	color: #1c1f21;
}

.leve2Subtype {
	padding-left: 10px;
}

.leve3Title {
	padding-top: 5px;
	padding-bottom: 5px;
	float: left;
	color: #1c1f21;
	font-size: 14px;
	font-weight: 300;
	margin-right: 20px;
	float: left;
}

.leve2Title a {
	text-decoration: none;
	color: #1c1f21;
}

.leve3Title a {
	text-decoration: none;
	color: #1c1f21;
}

.leve2Title a:HOVER {
	color: #666666;
}

.leve3Title a:HOVER {
	color: #666666;
}
</style>
<div class="row wlp-home-maximg">
	<div class="col-md-3"
		style="background-color:#2b333b; padding-right: 0px;">
		<div class="menuContent" style="">
			<c:set var="level1Num" value="0"></c:set>
			<c:forEach var="node" varStatus="stat" items="${types}">
				<c:if test="${node.parentid=='NONE' }">
					<c:set var="level1Num" value="${level1Num+1 }"></c:set>
					<c:if test="${level1Num<=homeMaxTypeNum }">
						<div class="item" data-id="a" id="wlp-box-${node.id}">
							<a href="javascript:void(0)"> <span class="group">${node.name}</span>
								<i class="glyphicon glyphicon-triangle-right"></i>
							</a>
						</div>
					</c:if>
				</c:if>
			</c:forEach>
			<c:if test="${fn:length(types)>homeMaxTypeNum }">
				<div class="item" data-id="b" id="wlp-float-moreLevel">
					<a href="javascript:void(0)"> <span class="group">更多分类</span> <i
						class="glyphicon glyphicon-triangle-right"></i>
					</a>
				</div>
			</c:if>
		</div>
	</div>
	<div class="col-md-9 ">
		<div class="menu-float-infobox">
			<c:set var="innerLevel1Num" value="0"></c:set>
			<c:forEach var="node" varStatus="stat" items="${types}">
				<c:if test="${node.parentid=='NONE' }">
					<c:set var="innerLevel1Num" value="${innerLevel1Num+1 }"></c:set>
					<c:if test="${innerLevel1Num<=homeMaxTypeNum }">
						<!-- 一級目錄BOX -->
						<div class="wlp-float-level wlp-box-${node.id}"
							title="${node.name}">
							<!-- 二级目录 -->
							<c:forEach var="node2" items="${types}">
								<c:if test="${node2.parentid==node.id }">
									<div class="leve2Title">
										<a href="category/Pubview.do?typeid=${node2.id}">${node2.name}</a>
									</div>
									<div class="leve2Subtype">
										<!-- 三级目录 -->
										<c:forEach var="node3" items="${types}">
											<c:if test="${node3.parentid==node2.id }">
												<div class="leve3Title">
													<a href="category/Pubview.do?typeid=${node3.id}">${node3.name}</a>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</c:if>
					<c:if test="${innerLevel1Num>homeMaxTypeNum }">
						<!--更多目錄 -->
						<div class="wlp-float-moreLevel wlp-float-level">
							<!-- 一级目录 -->
							<div class="leve2Title">
								<a href="category/Pubview.do?typeid=${node.id}">${node.name}</a>
							</div>
							<div class="leve2Subtype">
								<!-- 二级目录 -->
								<c:forEach var="node2" items="${types}">
									<c:if test="${node2.parentid==node.id }">
										<div class="leve3Title">
											<a href="category/Pubview.do?typeid=${node2.id}">${node2.name}</a>
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</c:if>
				</c:if>
			</c:forEach>
		</div>
		<div id="carousel-example-generic" class="carousel slide"
			data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">

				<c:forEach items="${tops}" var="top" varStatus="status">
					<c:if test="${top.type=='2' }">
						<c:if test="${status.index==0}">
							<li data-target="#carousel-example-generic"
								data-slide-to="${status.index}" class="active"></li>
						</c:if>
						<c:if test="${status.index!=0}">
							<li data-target="#carousel-example-generic"
								data-slide-to="${status.index}"></li>
						</c:if>
					</c:if>
				</c:forEach>
			</ol>
			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">
				<c:forEach items="${tops}" var="top" varStatus="status">
					<c:if test="${top.type=='2' }">
						<c:if test="${status.index==0}">
							<div class="item active">
								<img style="cursor: pointer;" onclick="clickMaxTop('${top.id}')"
									src="${top.imgurl}" alt="${top.title}" title="${top.title}">
								<!-- 	<div class="carousel-caption">${top.title}</div> -->
							</div>
						</c:if>
						<c:if test="${status.index!=0}">
							<div class="item">
								<img style="cursor: pointer;" onclick="clickMaxTop('${top.id}')"
									src="${top.imgurl}" alt="${top.title}" title="${top.title}">
								<!-- <div class="carousel-caption">${top.title}</div> -->
							</div>
						</c:if>
					</c:if>
				</c:forEach>
			</div>
			<!-- Controls -->
			<a class="left carousel-control" href="#carousel-example-generic"
				role="button" data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#carousel-example-generic"
				role="button" data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		//鼠标离开
		$('.wlp-home-maximg,.menu-float-infobox').mouseleave(function() {
			$('.menu-float-infobox').hide();
		});
		//鼠標進入菜單
		$('.wlp-home-maximg .menuContent .item').mouseover(function() {
			var targetClass = $(this).attr("id");
			$('.menu-float-infobox').show();
			$('.wlp-float-level').hide();
			$('.' + targetClass).show();
		});
	});
	function clickMaxTop(topid) {
		window.location = "classweb/PubTopview.do?topid=" + topid;
	}
</script>