<%@page import="com.farm.parameter.FarmParameterService"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
.wlp-hourview-chapter a {
	color: #cccccc;
}

.wlp-hourview-chapter a.active {
	color: #d44c4e;
}

.wlp-hourview-chapter .wlp-hourview-hourbox:HOVER a {
	color: #d44c4e;
	text-decoration: none;
}

.wlp-hourview-chapter .wlp-hourview-hourbox:HOVER i {
	color: #d44c4e;
}
</style>
<c:forEach items="${hourview.classview.chapters }" var="node">
	<div style="margin-bottom: 12px;" class="wlp-hourview-chapter">
		<h4 class="media-heading" style="font-size: 16px;">${node.title }</h4>
		<c:forEach items="${hourview.classview.hours}" var="hour">
			<c:if test="${hour.chapterid ==node.id}">
				<div class="media " style="margin-top: 8px; color: #cccccc;">
					<div class="media-body wlp-hourview-hourbox">
						<h4 class="media-heading"
							style="font-weight: 200; margin-top: 3px; font-size: 12px;">
							<c:if test="${hour.id==hourview.hour.id }">
								<a class="active"> <i
									class="glyphicon glyphicon-play-circle"
									style="top: 2px; position: relative;"></i>&nbsp;${hour.title }
								</a>
							</c:if>
							<c:if test="${hour.id!=hourview.hour.id }">
								<a
									href="hourweb/PubContinue.do?classid=${hourview.classview.classt.id}&hourid=${hour.id}">
									<i class="glyphicon glyphicon-play-circle"
									style="top: 2px; position: relative;"></i>&nbsp;${hour.title }
								</a>
							</c:if>
						</h4>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
</c:forEach>