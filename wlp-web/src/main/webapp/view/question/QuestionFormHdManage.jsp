<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--小组用户-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="searchDocCommentHdForm">
			<table class="editTable">
				<tr>
					<td class="title">回答内容:</td>
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
		<table id="dataDocCommentGridHd">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="DESCRIPTION" data-options="sortable:true" width="70">
						内容</th>
					<th field="CUSERNAME" data-options="sortable:true" width="20">
						创建人</th>
					<th field="CTIME" data-options="sortable:true" width="20">
						创建时间</th>
					<th field="ANONYMOUS" data-options="sortable:true" width="20">是否匿名</th>
					<th field="PSTATE" data-options="sortable:true" width="20">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionDocCommentHd = "question/delhd.do";//删除URL
	var url_searchActionDocCommentHd = "question/hdquery.do?ids=${ids}";//查询URL
	var gridDocCommentHd;//数据表格对象
	var searchDocCommentHd;//条件查询组件对象
	var toolBarDocCommentHd = [ {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataDocCommentHd
	}, {
		id : 'del',
		text : '设置为采纳回答',
		iconCls : 'icon-ok',
		handler : chooseDataDocCommentHd
	} ];
	$(function() {
		//初始化数据表格
		gridDocCommentHd = $('#dataDocCommentGridHd').datagrid({
			url : url_searchActionDocCommentHd,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarDocCommentHd,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			border : false,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchDocCommentHd = $('#searchDocCommentHdForm').searchForm({
			gridObj : gridDocCommentHd
		});
	});

	//删除
	function delDataDocCommentHd() {
		var selectedArray = $(gridDocCommentHd).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridDocCommentHd).datagrid('loading');
					$.post(url_delActionDocCommentHd + '?ids='
							+ $.farm.getCheckedIds(gridDocCommentHd, 'ID'), {},
							function(flag) {
								$(gridDocCommentHd).datagrid('loaded');
								if (flag.STATE == 0) {
									$(gridDocCommentHd).datagrid('reload');
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
	//采纳
	function chooseDataDocCommentHd() {
		var selectedArray = $(gridDocCommentHd).datagrid('getSelections');
		if (selectedArray.length == 1) {
			// 有数据执行操作
			var str = "是否将该回答设置为采纳答案?";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridDocCommentHd).datagrid('loading');
					$(gridQuestion).datagrid('loading');
					$.post("question/chooseAnswer.do" + '?id='
							+ $.farm.getCheckedIds(gridDocCommentHd, 'ID'), {},
							function(flag) {
								$(gridDocCommentHd).datagrid('loaded');
								$(gridQuestion).datagrid('loaded');
								if (flag.STATE == 0) {
									$(gridDocCommentHd).datagrid('reload');
									$(gridQuestion).datagrid('reload');
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
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
</script>