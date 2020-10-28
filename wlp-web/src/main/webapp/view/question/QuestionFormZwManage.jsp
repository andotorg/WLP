<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--小组用户-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="searchDocCommentZwForm">
			<table class="editTable">
				<tr>
					<td class="title">追问内容:</td>
					<td><input name="DESCRIPTION:like" type="text"></td>
					<td><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataDocCommentGridZw">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="DESCRIPTION" data-options="sortable:true" width="70">
						内容</th>
					<th field="CTIME" data-options="sortable:true" width="50">
						创建时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionDocCommentZw = "question/delzw.do";//删除URL
	var url_searchActionDocCommentZw = "question/zwquery.do?ids=${ids}";//查询URL
	var gridDocCommentZw;//数据表格对象
	var searchDocCommentZw;//条件查询组件对象
	var toolBarDocCommentZw = [ {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataDocCommentZw
	} ];
	$(function() {
		//初始化数据表格
		gridDocCommentZw = $('#dataDocCommentGridZw').datagrid({
			url : url_searchActionDocCommentZw,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarDocCommentZw,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			border : false,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchDocCommentZw = $('#searchDocCommentZwForm').searchForm({
			gridObj : gridDocCommentZw
		});
	});

	//删除
	function delDataDocCommentZw() {
		var selectedArray = $(gridDocCommentZw).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridDocCommentZw).datagrid('loading');
					$.post(url_delActionDocCommentZw + '?ids='
							+ $.farm.getCheckedIds(gridDocCommentZw, 'ID'), {},
							function(flag) {
								$(gridDocCommentZw).datagrid('loaded');
								if (flag.STATE == 0) {
									$(gridDocCommentZw).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ flag.MESSAGE;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							}, 'json');
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>