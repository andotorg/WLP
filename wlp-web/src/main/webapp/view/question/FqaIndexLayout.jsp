<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>知识管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="dom_searchfarmdoc">
			<table class="editTable">
				<tr>
					<td class="title">标题:</td>
					<td  colspan="3"><input name="a.TITLE:like" type="text"></td>
				</tr>
				<tr>
					<td class="title">发布时间起:</td>
					<td><input name="STARTTIME:like" type="text" class="easyui-datebox"></td>
					<td class="title">发布时间止:</td>
					<td><input name="ENDTIME:like" type="text" class="easyui-datebox"></td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="4"><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_datagridfarmdoc">
			<thead data-options="frozen:true,autoRowHeight:true,nowrap:false">
				<tr>
					<th field="TITLE" data-options="sortable:true" width="300">标题
					</th>
					<th field="PSTATE" data-options="sortable:true" width="80">
						问答状态</th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th field="VISITNUM" data-options="sortable:true" width="80">
						访问数</th>
					<th field="ANSWERS" data-options="sortable:true" width="80">
						回答数</th>
					<th field="CUSERNAME" data-options="sortable:true" width="80">
						作者</th>
					<th field="PUBTIME" data-options="sortable:true" width="180">
						发布时间</th>
					<th field="TAGS" data-options="sortable:true" width="80">标签</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_searchActionfarmdoc = "fqaIndex/query.do";//查询URL
	var title_windowfarmdoc = "知识管理";//功能名称
	var gridfarmdoc;//数据表格对象
	var searchfarmdoc;//条件查询组件对象
	var isLoadSta = false;
	var TOOL_BARfarmdoc = [ {
		id : 'view',
		text : '对所查询问答重建索引',
		iconCls : 'icon-invoice',
		handler : reIndexDoc
	}, {
		id : 'del',
		text : '删除索引(问答)',
		iconCls : 'icon-remove',
		handler : delIndexDoc
	} ];
	$(function() {
		//初始化数据表格
		gridfarmdoc = $('#dom_datagridfarmdoc').datagrid({
			url : url_searchActionfarmdoc,
			'toolbar' : TOOL_BARfarmdoc,
			fit : true,
			fitColumns : false,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchfarmdoc = $('#dom_searchfarmdoc').searchForm({
			gridObj : gridfarmdoc
		});
	});
	//建立索引
	function reIndexDoc() {
		// 有数据执行操作
		$.messager.confirm(MESSAGE_PLAT.PROMPT, "是否对所查询文档进行索引?",
				function(flag) {
					if (flag) {
						var limitStr = searchfarmdoc.arrayStr();
						var limitObj = {};
						if (limitStr != 'RESET') {
							limitObj = {
								'ruleText' : limitStr
							};
						}
						var pro = $.messager.progress({
							msg : '正在后台建立索引，请等待...',
							interval : 300,
							text : '完成后系统会自动关闭该窗口'
						});
						isLoadSta = true;
						$.post("fqaIndex/reIndex.do", limitObj, function(flag) {
							if (flag.STATE == 1) {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ flag.MESSAGE;
								$.messager.alert(MESSAGE_PLAT.ERROR, str,
										'error');
							}
							loadproSta();
						}, 'json');
					}
				});
	}

	//删除索引
	function delIndexDoc() {
		var url = 'lucene/delForm.do?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winDocindexdel',
			width : 600,
			height : 200,
			modal : true,
			url : url,
			title : '删除索引'
		});
	}

	//
	function loadproSta() {
		if (isLoadSta) {
			$.post("fqaIndex/indexPersont.do", {}, function(flag) {
				//0初始化，1删除原索引，2添加新索引，3完成，-1无任务
				if (flag.task.state == 0) {
					var progress=flag.task.processNum + "/" + flag.task.maxNum;
					$(".messager-p-msg").text("初始化文档中(1/3):["+progress+"]...:");
				}
				if (flag.task.state == 1) {
					var progress=flag.task.processNum + "/" + flag.task.maxNum;
					$(".messager-p-msg").text("删除历史索引中(2/3):["+progress+"]...:");
				}
				if (flag.task.state == 2) {
					var progress=flag.task.processNum + "/" + flag.task.maxNum;
					$(".messager-p-msg").text("添加新索引中(3/3):["+progress+"]...:");
				}
				if (flag.task.state == 3) {
					$(".messager-p-msg").text("重建索引完成，請關閉窗口！");
					$.messager.progress('close');
					isLoadSta = false;
					$.messager.alert(MESSAGE_PLAT.PROMPT, "操作成功!", 'info');
				}
				if (flag.task.state == -2) {
					$(".messager-p-msg").text("重建索引完成，請關閉窗口！");
					$.messager.progress('close');
					isLoadSta = false;
					$.messager.alert(MESSAGE_PLAT.ERROR, flag.task.message,
							'error');
				}
				setTimeout("loadproSta()", "500");
			}, 'json');
		}
	}
</script>
</html>