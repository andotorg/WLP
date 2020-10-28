<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
.wlp-view-types a {
	color: #dddddd;
	cursor: pointer;
	text-decoration: none;
}

.wlp-view-types a:HOVER {
	color: #ffffff;
}
</style>
<div style="margin-top: 50px; height: 20px;" class="wlp-view-types">
	<ul style="margin-left: -20px;">
		<c:forEach items="${classview.types }" var="node" varStatus="stat">
			<li style="list-style-type: none; float: left;"><a href="category/Pubview.do?typeid=${node.id}"> <c:if
						test="${stat.index>0 }"> &nbsp;&nbsp;/&nbsp;&nbsp; </c:if>${node.name }
			</a></li>
		</c:forEach>
	</ul>
</div>
<div class="wlp-userspace-h1"
	style="text-shadow: 5px 2px 8px #000000; margin-top: 10px; margin-bottom: 10px;">${classview.classt.name }</div>
<div class="wlp-userspace-h2">
	<span class="label label-default"
		style="padding-top: 4px; font-weight: 200; font-size: 14px;">难度:${classview.classt.difficulty}</span>
	<span class="label label-default"
		style="padding-top: 4px; font-weight: 200; font-size: 14px;">时长:${classview.classt.altime}小时</span>
	<span class="label label-default"
		style="padding-top: 4px; font-weight: 200; font-size: 14px;">作者:${classview.classt.outauthor}</span>
</div>
<div style="padding: 15px; text-shadow: 0px 0px 18px #000000;">
	<span style="font-size: 14px; font-weight: 700;">课程简介: </span>${classview.classt.shortintro }</div>
