<%@page import="com.farm.parameter.FarmParameterService"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<script src="text/lib/dplayer/DPlayer.min.js"></script>
<style type="text/css">
.super_content {
	border-bottom: 0px;
}
</style>
<div
	style="background: #383838; border-radius: 8px; padding: 8px;">
	<div class="dplayer">
		<iframe id="player1" style="border: 1px solid #000000;"
			src="download/Pubload.do?id=${hourview.hour.fileid}" width="100%"></iframe>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		var height = $(window).height() - 170;
		if (height > 860) {
			height = 860;
		}
		$('#player1').css('height', height);
	});
</script>
