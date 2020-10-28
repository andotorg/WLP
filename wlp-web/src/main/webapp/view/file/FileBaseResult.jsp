<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>文件数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchFilebaseForm">
			<table class="editTable">
				<tr>
					<td class="title">业务系统名称:</td>
					<td><input name="SYSNAME:like" type="text"></td>
					<td class="title">状态:</td>
					<td><select class="easyui-validatebox" name="PSTATE:=">
							<option value="">~全部~</option>
							<option value="0">临时</option>
							<option value="1">持久</option>
							<option value="2">永久</option>
							<option value="9">删除</option>
					</select></td>
					<td class="title">文件扩展名:</td>
					<td><input name="EXNAME:like" type="text"></td>
				</tr>
				<tr>
					<td class="title">完整文件名:</td>
					<td><input name="FILENAME:like" type="text"></td>
					<td class="title">别名:</td>
					<td><input name="TITLE:like" type="text"></td>
					<td class="title"></td>
					<td></td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="6"><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataFilebaseGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="TITLE" data-options="sortable:true" width="50">别名</th>
					<th field="FILENAME" data-options="sortable:true" width="50">文件名</th>
					<th field="SECRET" data-options="sortable:true" width="50">校验码</th>
					<th field="EXNAME" data-options="sortable:true" width="20">文件扩展名</th>
					<th field="FILESIZE" data-options="sortable:true" width="20">大小</th>
					<th field="CTIME" data-options="sortable:true" width="20">创建时间</th>
					<th field="SYSNAME" data-options="sortable:true" width="30">业务系统名称</th>
					<th field="PSTATE" data-options="sortable:true" width="20">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionFilebase = "filebase/del.do";//删除URL
	var url_formActionFilebase = "filebase/form.do";//增加、修改、查看URL
	var url_searchActionFilebase = "filebase/query.do";//查询URL
	var title_windowFilebase = "文件管理";//功能名称
	var gridFilebase;//数据表格对象
	var searchFilebase;//条件查询组件对象
	var toolBarFilebase = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataFilebase
	}, {
		id : 'del',
		text : '物理删除',
		iconCls : 'icon-remove',
		handler : delDataFilebase
	}, {
		id : 'logicdel',
		text : '逻辑删除',
		iconCls : 'icon-remove',
		handler : delDataFilebaseByLogic
	} ];
	$(function() {
		//初始化数据表格
		gridFilebase = $('#dataFilebaseGrid').datagrid({
			url : url_searchActionFilebase,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarFilebase,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchFilebase = $('#searchFilebaseForm').searchForm({
			gridObj : gridFilebase
		});
	});
	//查看
	function viewDataFilebase() {
		var selectedArray = $(gridFilebase).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionFilebase + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winFilebase',
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
	//逻辑删除
	function delDataFilebaseByLogic() {
		var selectedArray = $(gridFilebase).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridFilebase).datagrid('loading');
					$.post("filebase/logicdel.do" + '?ids='
							+ $.farm.getCheckedIds(gridFilebase, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridFilebase).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridFilebase).datagrid('reload');
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

	//删除
	function delDataFilebase() {
		var selectedArray = $(gridFilebase).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridFilebase).datagrid('loading');
					$.post(url_delActionFilebase + '?ids='
							+ $.farm.getCheckedIds(gridFilebase, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridFilebase).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridFilebase).datagrid('reload');
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