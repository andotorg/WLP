<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout"  data-options="fit:true"><!-- 
	<div data-options="region:'north',border:false">
		<form id="searchTagForm">
			<table class="editTable">
				<tr style="text-align: center;">
					<td colspan="4"><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div> -->
	<div data-options="region:'center',border:false">
		<table id="dataTagGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="40">名称</th>
					<th field="SORT" data-options="sortable:true" width="40">排序</th>
					<th field="PSTATE" data-options="sortable:true" width="60">状态</th>
					<th field="TYPENAME" data-options="sortable:true" width="60">类别</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionTag = "tag/del.do";//删除URL
	var url_formActionTag = "tag/form.do";//增加、修改、查看URL
	var url_searchActionTag = "tag/query.do?typeids=${TYPEIDS}";//查询URL
	var title_windowTag = "标签管理";//功能名称
	var gridTag;//数据表格对象
	var searchTag;//条件查询组件对象
	var toolBarTag = [ {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataTag
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataTag
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataTag
	} ];
	$(function() {
		//初始化数据表格
		gridTag = $('#dataTagGrid').datagrid({
			url : url_searchActionTag,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarTag,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		//searchTag = $('#searchTagForm').searchForm({
		//	gridObj : gridTag
		//});
	});
	//查看
	function viewDataTag() {
		var selectedArray = $(gridTag).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionTag + '?pageset.pageType=' + PAGETYPE.VIEW
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winTag',
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
	//新增
	function addDataTag() {
		var url = url_formActionTag + '?typeids=${TYPEIDS}&operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winTag',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataTag() {
		var selectedArray = $(gridTag).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionTag + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winTag',
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
	function delDataTag() {
		var selectedArray = $(gridTag).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridTag).datagrid('loading');
					$.post(url_delActionTag + '?ids='
							+ $.farm.getCheckedIds(gridTag, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridTag).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridTag).datagrid('reload');
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