<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--选择文档-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="searchClasstForm">
			<table class="editTable">
				<tr style="text-align: center;">
					<td class="title">名称:</td>
					<td><input name="NAME:like" type="text"></td>
					<td><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dom_chooseGridChooseClass">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="160">课程名称</th>
					<th field="OUTAUTHOR" data-options="sortable:true" width="30">作者</th>
					<th field="ALTIME" data-options="sortable:true" width="30">课程时长</th>
					<th field="PSTATE" data-options="sortable:true" width="30">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var chooseGridChooseClass;
	var chooseSearchfarmChooseClass;
	var toolbar_chooseChooseClass = [ {
		text : '选择课程',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_chooseGridChooseClass').datagrid(
					'getSelections');
			if (selectedArray.length == 1) {
				chooseWindowCallBackHandle(selectedArray);
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT,
						MESSAGE_PLAT.CHOOSE_ONE_ONLY, 'info');
			}
		}
	} ];
	$(function() {//<input type="hidden" name="PSTATE:=" value="1">
		chooseGridChooseClass = $('#dom_chooseGridChooseClass').datagrid({
			url : 'classt/classChooseQuery.do',
			fit : true,
			'toolbar' : toolbar_chooseChooseClass,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		//初始化条件查询
		searchClasst = $('#searchClasstForm').searchForm({
			gridObj : chooseGridChooseClass
		});
	});
//-->
</script>