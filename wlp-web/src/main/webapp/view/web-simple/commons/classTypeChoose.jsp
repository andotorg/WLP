<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
<!--
.list-group {
	border-radius: 4px;
	-webkit-box-shadow: 0 0px 0px rgba(0, 0, 0, 0);
	box-shadow: 0 0px 0px rgba(0, 0, 0, 0);
}
-->
</style>
<script
	src="<PF:basePath/>text/lib/bootstrap-treeview/bootstrap-treeview.min.js"></script>
<!-- Modal -->
<div class="modal fade" id="chooseTypeModal" tabindex="-1" role="dialog"
	aria-labelledby="chooseTypeModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="chooseTypeModalLabel">选择课程分类</h4>
			</div>
			<div class="modal-body">
				<div id="tree"></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var treeObj;
	var treeBoxKey = "#tree";
	$(function() {
		treeObj = $(treeBoxKey).treeview({
			showBorder : false,
			data : '[{"text": "数据加载中...","id":"di1111"}]'
		});
		$.post("category/Publoadclasstype.do", {}, function(flag) {
			treeObj = $(treeBoxKey).treeview({
				data : flag.data,
				showBorder : false,
				highlightSelected : false,
				onNodeSelected : function(event, data) {
					callTreeselectBackFun(event, data);
				}
			});
		}, 'json');
	});
	function callTreeselectBackFun(event, data) {
		$('#chooseTypeModal').modal('hide');
		try {
			chooseClassTypeHandle('${param.appkey}', event, data);
		} catch (e) {
			alert('请实现方法	chooseClassTypeHandle(typekey,event, data);');
		}
	}
</script>