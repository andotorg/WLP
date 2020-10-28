<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:forEach begin="1" end="8">
	<div class="col-md-3" style="padding: 0px; padding-top: 20px">
		<div class="media wlp-praise-box">
			<div class="media-left">
				<a href="#"> <img class="media-object minImg" src="text/img/logo.png"
					alt="...">
				</a>
			</div>
			<div class="media-body">
				<div class="media-heading">Java工程师2020</div>
				到具备1年开发经验
			</div>
		</div>
	</div>
</c:forEach>

