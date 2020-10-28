<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>标签类别数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchTagtypeForm">
			<table class="editTable">
				<tr style="text-align: center;">
					<td class="title">名称:</td>
					<td colspan="4" style="text-align: left;"><input name="NAME:like" type="text"></td>
					<td ><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataTagtypeGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="80">类别名称</th>
					<th field="SORT" data-options="sortable:true" width="40">排序</th>
					<th field="PSTATE" data-options="sortable:true" width="40">状态</th>
					<th field="PCONTENT" data-options="sortable:true" width="40">备注</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionTagtype = "tagtype/del.do";//删除URL
	var url_formActionTagtype = "tagtype/form.do";//增加、修改、查看URL
	var url_searchActionTagtype = "tagtype/query.do";//查询URL
	var title_windowTagtype = "标签类别管理";//功能名称
	var gridTagtype;//数据表格对象
	var searchTagtype;//条件查询组件对象
	var toolBarTagtype = [{
		id : 'add',
		text : '新增类别',
		iconCls : 'icon-add',
		handler : addDataTagtype
	}, {
		id : 'edit',
		text : '修改类别',
		iconCls : 'icon-edit',
		handler : editDataTagtype
	}, {
		id : 'tags',
		text : '类别标签',
		iconCls : 'icon-tag',
		handler : editTags
	}, {
		id : 'del',
		text : '删除类别',
		iconCls : 'icon-remove',
		handler : delDataTagtype
	} ];
	$(function() {
		//初始化数据表格
		gridTagtype = $('#dataTagtypeGrid').datagrid({
			url : url_searchActionTagtype,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarTagtype,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchTagtype = $('#searchTagtypeForm').searchForm({
			gridObj : gridTagtype
		});
	});
	//查看
	function viewDataTagtype() {
		var selectedArray = $(gridTagtype).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionTagtype + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winTagtype',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	
	
	//編輯标签
	function editTags(){
		var selectedArray = $(gridTagtype).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url =  'tag/list.do?ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winTags',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '类别标签'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	
	
	//新增
	function addDataTagtype() {
		var url = url_formActionTagtype + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winTagtype',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataTagtype() {
		var selectedArray = $(gridTagtype).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionTagtype + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winTagtype',
				width : 600,
				height : 300,
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
	function delDataTagtype() {
		var selectedArray = $(gridTagtype).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridTagtype).datagrid('loading');
					$.post(url_delActionTagtype + '?ids='
							+ $.farm.getCheckedIds(gridTagtype, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridTagtype).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridTagtype).datagrid('reload');
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