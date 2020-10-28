<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:forEach items="${tops}" var="top" varStatus="status">
	<c:if test="${top.type=='1' }">
		<div class="col-md-3" style="padding: 0px; padding-top: 20px"
			onclick="clickPraiseTop('${top.id}')">
			<div class="media wlp-praise-box">
				<div class="media-left">
					<img class="media-object maxImg" src="${top.imgurl}" alt="...">
				</div>
				<div class="media-body">
					<div class="media-heading"
						style="font-size: 14px; height: 60px; overflow: hidden;"
						title="${top.title}">${top.title}</div>
				</div>
			</div>
		</div>
	</c:if>
</c:forEach>
<script type="text/javascript">
	function clickPraiseTop(topid) {
		window.location = "classweb/PubTopview.do?topid=" + topid;
	}
</script>
