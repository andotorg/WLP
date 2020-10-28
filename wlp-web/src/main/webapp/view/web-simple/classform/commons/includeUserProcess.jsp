<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div style="top: -50px; position: absolute; padding: 0px; width: 100%;">
	<div class="panel panel-default"
		style="filter: progid:DXImageTransform.Microsoft.Shadow(color=#909090, direction=120, strength=4); -moz-box-shadow: 2px 2px 10px #909090; -webkit-box-shadow: 2px 2px 10px #909090; box-shadow: 2px 2px 10px #909090;">
		<div class="panel-body" style="width: 100%;">
			<a href="hourweb/PubContinue.do?classid=${classview.classt.id}"
				class="btn btn-danger  btn-lg"
				style="width: 100%; margin-top: 20px; margin-bottom: 20px;"> <i
				class="glyphicon glyphicon-play-circle" style="top: 3px;"></i>&nbsp;开始学习
			</a> <img class="img-rounded"
				style="width: 100%; margin-top: 0px; margin-bottom: 20px;"
				src="${classview.imgurl}">
			<div class="wlp-userspace-h2" style="margin-left: -20px;">当前学习进度</div>
			<hr style="margin-bottom: 10px;" class="wlp-userspace-hr">
			<div class="progress">
				<div class="progress-bar progress-bar-danger progress-bar-striped"
					role="progressbar" aria-valuenow="80" aria-valuemin="0"
					aria-valuemax="100" style="width: 100%">完成学习需要${classview.classt.altime }小时</div>
			</div>

		</div>
	</div>
</div>