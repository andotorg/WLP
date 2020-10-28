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
	style="background: #000000; border-radius: 8px; padding-top: 8px; padding-bottom: 6px;">
	<div id="player1" class="dplayer"></div>
</div>
<script type="text/javascript">
	$(function() {
		var height = $(window).height() - 220;
		if (height > 860) {
			height = 860;
		}
		$('#player1').css('height', height);
		var dp = new DPlayer({
			element : document.getElementById('player1'),
			autoplay : false,
			theme : '#FADFA3',
			loop : false,
			lang : 'zh-cn',
			screenshot : false,
			hotkey : true,
			preload : 'auto',
			video : {
				url : 'download/Pubload.do?id=${hourview.hour.fileid}',
				pic : 'text/img/demo/playerIcon.png'
			},
			contextmenu : []
		});
	});
</script>
