<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
.wlp-usermenu-list {
	padding-top: 20px;
	padding-bottom: 20px;
}

.wlp-usermenu-list ul {
	padding: 0px;
	text-align: center;
}

.wlp-usermenu-list ul li {
	list-style-type: none;
	padding: 10px;
	color: #666666;
}

.wlp-usermenu-list ul li i {
	float: right;
	top: 4px;
	margin-right: 20px;
}

.wlp-usermenu-list ul .waiting:HOVER {
	color: #f01414;
	cursor: pointer;
}

.wlp-usermenu-list ul .active {
	background-color: #f01414;
	color: #ffffff;
}
</style>
<div style="width: 100%; background-color: #f8fafc; margin-top: 20px;">
	<div style="padding: 20px; text-align: center;">
		<!-- 头像用户名 -->
		<div>
			<img alt="" src="download/Pubphone.do?id=${USEROBJ.imgid}"
				style="width: 96px; height: 96px;" class="img-circle">
		</div>
		<div style="font-size: 16px; margin-top: 8px;">${USEROBJ.name}</div>
	</div>
	<div class="wlp-userspace-h2">个人设置</div>
	<hr class="wlp-userspace-hr">
	<div class="wlp-usermenu-list">
		<ul>
			<c:if test="${param.current=='userinfo' }">
				<li class="active">修改信息<i
					class="glyphicon glyphicon-menu-right"></i></li>
			</c:if>
			<c:if test="${param.current!='userinfo' }">
				<li class="waiting" onclick="gotoUrl('userspace/settinginfo.do')">修改信息<i
					class="glyphicon glyphicon-menu-right"></i></li>
			</c:if>
			<c:if test="${param.current=='password' }">
				<li class="active">修改密码<i
					class="glyphicon glyphicon-menu-right"></i></li>
			</c:if>
			<c:if test="${param.current!='password' }">
				<li class="waiting" onclick="gotoUrl('userspace/settingpsd.do')">修改密码<i
					class="glyphicon glyphicon-menu-right"></i></li>
			</c:if>
		</ul>
	</div>
</div>
<script type="text/javascript">
	function gotoUrl(url) {
		window.location = "<PF:basePath/>" + url;
	}
</script>