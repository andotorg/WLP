<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<center>
	<nav aria-label="...">
		<ul class="pagination">
			<c:if test="${param.current>1 }">
				<li><a href="${param.lasturl}">上一页</a></li>
			</c:if>
			<li class="active"><a>第${param.current}页/共${param.total}页</a></li>
			<c:if test="${param.current<param.total}">
				<li><a href="${param.nexturl}">下一页</a></li>
			</c:if>
		</ul>
	</nav>
</center>
