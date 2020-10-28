<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div style="width: 100%; margin-top: 20px;">
	<c:if test="${!empty classview}">
		<div style="padding-bottom: 20px; text-align: center;">
			<img class="img-rounded"
				style="width: 226px; height: 160px; margin: auto;"
				src="${classview.imgurl }">
		</div>
		<div class="wlp-userspace-h2">${classview.classt.name }</div>
	</c:if>
	<c:if test="${empty classview}">
		<div class="wlp-userspace-h2">新课程</div>
	</c:if>
	<hr class="wlp-userspace-hr">
	<c:if test="${!empty classview}">
		<ul class="nav nav-pills nav-stacked">
			<li role="presentation" ${param.type=='base'?'class="active"':'' }><a
				href="classweb/mng.do?classid=${classview.classt.id}"><i class="glyphicon glyphicon-pencil"></i>&nbsp;&nbsp;修改课程信息</a></li>
			<li role="presentation" ${param.type=='introduction'?'class="active"':'' }><a
				href="classweb/mng.do?classid=${classview.classt.id}&type=introduction"><i class="glyphicon glyphicon-pencil"></i>&nbsp;&nbsp;修改课程介绍</a></li>
			<li role="presentation" ${param.type=='chapter'?'class="active"':'' }><a
				href="classweb/mng.do?classid=${classview.classt.id}&type=chapter"><i class="glyphicon glyphicon-pencil"></i>&nbsp;&nbsp;维护章节课时</a></li>
			<li role="presentation" ><a target="_blank"
				href="classweb/Pubview.do?classid=${classview.classt.id}"><i class="glyphicon glyphicon-log-out"></i>&nbsp;&nbsp;预览课程</a></li>
			<li role="presentation" ><a target="_blank"
				href="userspace/tempclass.do"><i class="glyphicon glyphicon-menu-left"></i>&nbsp;&nbsp;返回列表</a></li>
		</ul>
	</c:if>  
</div>
