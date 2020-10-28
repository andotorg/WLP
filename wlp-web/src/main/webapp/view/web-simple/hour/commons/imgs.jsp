<%@page import="com.farm.parameter.FarmParameterService"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<script charset="utf-8" src="<PF:basePath/>text/lib/viewer/viewer.js"></script>
<link href="<PF:basePath/>text/lib/viewer/viewer.css" rel="stylesheet">
<style type="text/css">
.super_content {
	border-bottom: 0px;
}
</style>

<!-- /.navbar-collapse -->
<div oncontextmenu="customContextMenu(event)"
	style="background-color: #000000; border-radius: 15px 15px 15px 15px; padding-right: 10px;">
	<div style="float: left; width: 100px; overflow: auto;"
		id="imgsMinBoxDiv">
		<c:forEach begin="1" end="${hourview.pinum}" var="page">
			<c:if test="${page<10}">
				<img id="Page1" alt="${page}" class="img-responsive"
					onclick="scrollToMarkName('.pageName${page}')"
					src="download/PubDirFile.do?fileid=${hourview.hour.fileid}&num=${page-1}"
					data-src="download/PubDirFile.do?fileid=${hourview.hour.fileid}&num=${page-1}"
					style="margin: auto; width: 64px; cursor: pointer;" src="">
				<div
					style="text-align: center; color: #cccccc; padding-bottom: 16px; font-size: 10px;">第${page}页/共${hourview.pinum}页
				</div>
			</c:if>
			<c:if test="${page>=10}">
				<img id="Page1" alt="${page}" class="img-responsive"
					onclick="scrollToMarkName('.pageName${page}')"
					data-src="download/PubDirFile.do?fileid=${hourview.hour.fileid}&num=${page-1}"
					style="margin: auto; width: 64px; cursor: pointer;"
					src="text/img/demo/playerIcon.png">
				<div
					style="text-align: center; color: #cccccc; padding-bottom: 16px; font-size: 10px;">第${page}页/共${hourview.pinum}页
				</div>
			</c:if>
		</c:forEach>
	</div>
	<div class="text-center imgContainer" id="imgsBoxDiv"
		style="overflow: auto;">
		<c:forEach begin="1" end="${hourview.pinum}" var="page">
			<c:if test="${page<5}">
				<img id="Page1" alt="${page}" class="img-responsive pageName${page}"
					src="download/PubDirFile.do?fileid=${hourview.hour.fileid}&num=${page-1}"
					data-src="download/PubDirFile.do?fileid=${hourview.hour.fileid}&num=${page-1}"
					name="pageName${page}"
					style="margin: auto; min-height: 64px; min-width: 64px;" src="">
				<div
					style="text-align: center; color: #cccccc; padding-bottom: 16px; font-size: 14px;">第${page}页/共${hourview.pinum}页
				</div>
			</c:if>
			<c:if test="${page>=5}">
				<img id="Page1" alt="${page}" class="img-responsive pageName${page}"
					data-src="download/PubDirFile.do?fileid=${hourview.hour.fileid}&num=${page-1}"
					name="pageName${page}"
					style="margin: auto; min-height: 64px; min-width: 64px;"
					src="text/img/demo/playerIcon.png">
				<div
					style="text-align: center; color: #cccccc; padding-bottom: 16px; font-size: 14px;">第${page}页/共${hourview.pinum}页
				</div>
			</c:if>
		</c:forEach>
	</div>
</div>
<script type="text/javascript">
	//滾動到指定位置
	function scrollToMarkName(markName) {
		var mainContainer = $('#imgsBoxDiv'), //父级容器
		scrollToContainer = mainContainer.find(markName);//指定的class
		mainContainer.animate({
			scrollTop : scrollToContainer.offset().top
					- mainContainer.offset().top + mainContainer.scrollTop()
		}, 0);
	}
	$(function() {
		var height = $(window).height() - 170;
		if (height > 860) {
			height = 860;
		}
		$('#imgsBoxDiv').css('height', height);
		$('#imgsMinBoxDiv').css('height', height);
	});
	$(function() {
		$('#imgsBoxDiv').on('scroll', function() {//当页面滚动的时候绑定事件
			$('.imgContainer img').each(function() {//遍历所有的img标签
				if (checkShow($(this))) {
					$('#currentNumSpan').text($(this).attr("alt"));
					if (!isLoaded($(this))) {
						loadImg($(this));
					}
				}
			})
		})
		$('#imgsMinBoxDiv').on('scroll', function() {//当页面滚动的时候绑定事件
			$('#imgsMinBoxDiv img').each(function() {//遍历所有的img标签
				if (checkShow($(this))) {
					$('#currentNumSpan').text($(this).attr("alt"));
					if (!isLoaded($(this))) {
						loadImg($(this));
					}
				}
			})
		})
		//禁止图片拖动下载
		for (i in document.images) {
			document.images[i].ondragstart = imgdragstart;
		}
		try {
			var gallery = new Viewer(document.getElementById('imgsBoxDiv'), {
				navbar : true,
				url : 'data-src',
				keyboard : true,
			});
		} catch (e) {
		}
	});
	//图片是否可见
	function checkShow($img) { // 传入一个img的jq对象
		var scrollTop = $(window).scrollTop(); //即页面向上滚动的距离
		var windowHeight = $(window).height(); // 浏览器自身的高度
		var offsetTop = $img.offset().top; //目标标签img相对于document顶部的位置
		if (offsetTop < (scrollTop + windowHeight) && offsetTop > scrollTop) { //在2个临界状态之间的就为出现在视野中的
			return true;
		}
		return false;
	}
	//是否已经加载
	function isLoaded(imgObj) {
		return imgObj.attr('data-src') === imgObj.attr('src'); //如果data-src和src相等那么就是已经加载过了
	}
	//加载图片
	function loadImg(imgObj) {
		imgObj.attr('src', imgObj.attr('data-src')); // 加载就是把自定义属性中存放的真实的src地址赋给src属性
		imgObj.css("width", "860px");
		;
	}
	//不允許拖拽
	function imgdragstart() {
		return false;
	};
</script>
<script>
	function customContextMenu(event) {
		event.preventDefault ? event.preventDefault()
				: (event.returnValue = false);
		var cstCM = document.getElementById('cstCM');
		cstCM.style.left = event.clientX + 'px';
		cstCM.style.top = event.clientY + 'px';
		cstCM.style.display = 'block';
		document.onmousedown = clearCustomCM;
	}
	function clearCustomCM() {
		document.getElementById('cstCM').style.display = 'none';
		document.onmousedown = null;
	}
</script>