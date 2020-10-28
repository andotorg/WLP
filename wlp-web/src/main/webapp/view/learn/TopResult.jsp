<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>置顶文档数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="text/lib/kindeditor/themes/default/default.css">
<script type="text/javascript"
	src="text/lib/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" src="text/lib/kindeditor/zh-CN.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchFarmtopForm">
			<table class="editTable">
				<tr>
					<td class="title">标题:</td>
					<td><input name="TITLE:like" type="text"></td>
					<td class="title">类型:</td>
					<td><select name="TYPE:=">
							<option value=""></option>
							<option value="1">推荐阅读</option>
							<option value="2">置顶大图</option>
					</select></td>
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
		<table id="dataFarmtopGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="TITLE" data-options="sortable:true" width="40">标题</th>
					<th field="CTIME" data-options="sortable:true" width="30">
						置顶时间</th>
					<th field="PSTATE" data-options="sortable:true" width="20">状态</th>
					<th field="MODEL" data-options="sortable:true" width="20">
						内容类型</th>
					<th field="SORT" data-options="sortable:true" width="20">排序</th>
					<th field="TYPE" data-options="sortable:true" width="20">类型</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionFarmtop = "top/del.do";//删除URL
	var url_formActionFarmtop = "top/form.do";//增加、修改、查看URL
	var url_searchActionFarmtop = "top/query.do";//查询URL
	var title_windowFarmtop = "置顶文档管理";//功能名称
	var gridFarmtop;//数据表格对象
	var searchFarmtop;//条件查询组件对象
	var toolBarFarmtop = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataFarmtop
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataFarmtop
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataFarmtop
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataFarmtop
	}, {
		id : 'sort',
		text : '格式化排序',
		iconCls : 'icon-remove',
		handler : formatSort
	} ];
	$(function() {
		//初始化数据表格
		gridFarmtop = $('#dataFarmtopGrid').datagrid({
			url : url_searchActionFarmtop,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarFarmtop,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchFarmtop = $('#searchFarmtopForm').searchForm({
			gridObj : gridFarmtop
		});
	});
	//查看
	function viewDataFarmtop() {
		var selectedArray = $(gridFarmtop).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionFarmtop + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winFarmtop',
				width : 600,
				height : 480,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//新增
	function addDataFarmtop() {
		var url = url_formActionFarmtop + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winFarmtop',
			width : 600,
			height : 480,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataFarmtop() {
		var selectedArray = $(gridFarmtop).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionFarmtop + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winFarmtop',
				width : 600,
				height : 480,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//删除
	function delDataFarmtop() {
		var selectedArray = $(gridFarmtop).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridFarmtop).datagrid('loading');
					$.post(url_delActionFarmtop + '?ids='
							+ $.farm.getCheckedIds(gridFarmtop, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridFarmtop).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridFarmtop).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ jsonObject.MESSAGE;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							});
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}

	//格式化排序
	function formatSort() {
		var selectedArray = $(gridFarmtop).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			$.messager.confirm(MESSAGE_PLAT.PROMPT, "该操作将序号顺序设置为当前展示顺序，是否继续？", function(flag) {
				if (flag) {
					$(gridFarmtop).datagrid('loading');
					$.post('top/formatSort.do?ids='
							+ $.farm.getCheckedIds(gridFarmtop, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridFarmtop).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridFarmtop).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ jsonObject.MESSAGE;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							});
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
</html>