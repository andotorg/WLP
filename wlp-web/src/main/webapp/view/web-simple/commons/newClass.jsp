<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:forEach items="${newClasses.resultList}" var="node">
	<div class="col-md-3 wlp-classShow-box" onclick="openClassView('${node.ID}')"
		style="padding: 20px; padding-top: 20px;">
		<div style="text-align: center;">
			<img
				style="width: 230px; height: 161px; border-radius: 6px 6px 6px 6px;"
				src="${node.IMGURL}" alt="...">
		</div>
		<div class="media wlp-praiseback-box"
			style="text-align: center; height: 110px; overflow: hidden;">
			<div class="media-body">
				<div class="media-heading" style="height: 2.7em; overflow: hidden;">${node.NAME}</div>
				<div style="color: #999999;">
					<div>${node.TYPE.name}</div>
					<span style="padding-top: 4px;">难度:${node.DIFFICULTY}</span>&nbsp;/&nbsp;<span
						style="padding-top: 4px;">作者:${node.OUTAUTHOR}</span>
				</div>
			</div>
		</div>
	</div>
</c:forEach>
<script type="text/javascript">
	function openClassView(classid) {
		window.location = "classweb/Pubview.do?classid=" + classid;
	}
</script>
