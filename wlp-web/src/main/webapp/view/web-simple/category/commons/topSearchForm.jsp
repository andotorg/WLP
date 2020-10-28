<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="media">
	<div class="media-left">
		<div class="wlp-userspace-h2"
			style="width: 90px; text-align: right; margin-left: -20px; margin-top: 10px;">一级分类:</div>
	</div>
	<div class="media-body">
		<ul class="wlp-typeline-ul">
			<c:if test="${empty roottypeid }">
				<li onclick="dosearchViewClass(1,'','${difficulty}');"
					class="active">全部</li>
			</c:if>
			<c:if test="${!empty roottypeid }">
				<li onclick="dosearchViewClass(1,'','${difficulty}');">全部</li>
			</c:if>
			<c:forEach items="${alltypes}" var="node">
				<c:if test="${node.parentid=='NONE' }">
					<c:if test="${node.id==roottypeid }">
						<li class="active"
							onclick="dosearchViewClass(1,'${node.id}','${difficulty}');">${node.name }</li>
					</c:if>
					<c:if test="${node.id!=roottypeid }">
						<li onclick="dosearchViewClass(1,'${node.id}','${difficulty}');">${node.name }</li>
					</c:if>
				</c:if>
			</c:forEach>
		</ul>
	</div>
</div>
<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
<div class="media">
	<div class="media-left">
		<div class="wlp-userspace-h2"
			style="width: 90px; text-align: right; margin-left: -20px; margin-top: 10px;">学习难度:</div>
	</div>
	<div class="media-body">
		<ul class="wlp-typeline-ul">
			<c:if test="${empty difficulty}">
				<li class="active" onclick="dosearchViewClass(1,'${typeid}','');">全部</li>
			</c:if>
			<c:if test="${!empty difficulty}">
				<li onclick="dosearchViewClass(1,'${typeid}','');">全部</li>
			</c:if>
			<c:if test="${difficulty=='1'}">
				<li class="active" onclick="dosearchViewClass(1,'${typeid}','1');">入门</li>
			</c:if>
			<c:if test="${difficulty!='1'}">
				<li onclick="dosearchViewClass(1,'${typeid}','1');">入门</li>
			</c:if>
			<c:if test="${difficulty=='2'}">
				<li class="active" onclick="dosearchViewClass(1,'${typeid}','2');">初级</li>
			</c:if>
			<c:if test="${difficulty!='2'}">
				<li onclick="dosearchViewClass(1,'${typeid}','2');">初级</li>
			</c:if>
			<c:if test="${difficulty=='3'}">
				<li class="active" onclick="dosearchViewClass(1,'${typeid}','3');">中级</li>
			</c:if>
			<c:if test="${difficulty!='3'}">
				<li onclick="dosearchViewClass(1,'${typeid}','3');">中级</li>
			</c:if>
			<c:if test="${difficulty=='4'}">
				<li class="active" onclick="dosearchViewClass(1,'${typeid}','4');">高级</li>
			</c:if>
			<c:if test="${difficulty!='4'}">
				<li onclick="dosearchViewClass(1,'${typeid}','4');">高级</li>
			</c:if>
		</ul>
	</div>
</div>