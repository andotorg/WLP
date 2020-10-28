<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>问题数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="text/lib/kindeditor/themes/default/default.css">
<script type="text/javascript"
	src="text/lib/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" src="text/lib/kindeditor/zh-CN.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,border:false"
		style="width: 200px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="FarmdocTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新</a> <a id="FarmdocTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">展开</a>
		</div>
		<ul id="FarmdocTree"></ul>
	</div>
	<div data-options="region:'north',border:false">
		<form id="searchQuestionForm">
			<table class="editTable">
				<tr>
					<td class="title">题目:</td>
					<td><input name="TITLE:like" type="text"></td>
					<td class="title">分类:</td>
					<td><input id="PARENTTITLE_RULE" type="text"
						readonly="readonly" style="background: #F3F3E8"> <input
						id="PARENTID_RULE" name="b.TREECODE:like" type="hidden"></td>
					<td class="title">创建人:</td>
					<td><input name="A.CUSERNAME:like" type="text"></td>
				</tr>
				<tr>
					<td class="title">状态:</td>
					<td><select name="A.PSTATE:=">
							<option value=""></option>
							<option value="1">提问中</option>
							<option value="2">完成</option>
					</select></td>
					<td class="title">发布开始(含):</td>
					<td><input name="STARTTIME:like" type="text"
						class="easyui-datebox"></input></td>
					<td class="title">发布结束(含):</td>
					<td><input name="ENDTIME:like" type="text"
						class="easyui-datebox"></input></td>
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
		<table id="dataQuestionGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="TITLE" data-options="sortable:true" width="50">题目</th>
					<th field="TYPENAME" data-options="sortable:true" width="30">分类</th>
					<th field="ANONYMOUS" data-options="sortable:true" width="15">是否匿名</th>
					<th field="PUBTIME" data-options="sortable:true" width="30">发布时间</th>
					<th field="HOTNUM" data-options="sortable:true" width="10">热度</th>
					<th field="VISITNUM" data-options="sortable:true" width="15">访问数</th>
					<!-- <th field="ATTENTIONS" data-options="sortable:true" width="30">关注数</th> -->
					<th field="ANSWERS" data-options="sortable:true" width="15">回答数</th>
					<th field="PSTATE" data-options="sortable:true" width="10">状态</th>
					<th field="CUSERNAME" data-options="sortable:true" width="20">创提问人</th>
					<!-- 指派专家 -->
					<th field="EXPERTNAME" data-options="sortable:true" width="20">指定专家</th>
				</tr>
			</thead>
		</table>
		<form method="post" action="question/exportExcel.do" id="reportForm">
			<input type="hidden" name="ruleText" id="ruleTextId" />
		</form>
	</div>
	<div id="toolBarEditor">
		<a href="javascript:void(0)" class="easyui-menubutton"
			data-options="menu:'#mm2',iconCls:'icon-tip'">查看</a>
		<div id="mm2" style="width: 150px;">
			<div data-options="iconCls:'icon-tip'" onClick="viewDataQuestion()">查看问题信息</div>
			<div data-options="iconCls:'icon-brainstorming'"
				onClick="viewExpertBindInfo()">邀请专家记录</div>
			<div data-options="iconCls:'icon-world'" onClick="visitTheFqa()">前台打开</div>
		</div>
		<a class="easyui-linkbutton"
			data-options="iconCls:'icon-edit',plain:true,onClick:editDataQuestion">修改
		</a> <a href="javascript:void(0)" class="easyui-menubutton"
			data-options="menu:'#mm3',iconCls:'icon-settings'">内容管理</a>
		<div id="mm3" style="width: 150px;">
			<div data-options="iconCls:'icon-settings'"
				onClick="editZWDataQuestion()">追问管理</div>
			<div data-options="iconCls:'icon-settings'"
				onClick="editHDDataQuestion()">回答管理</div>
			<div data-options="iconCls:'icon-settings'"
				onClick="editPLDataQuestion()">评论管理</div>
		</div>
		<a href="javascript:void(0)" class="easyui-menubutton"
			data-options="menu:'#mm4',iconCls:'icon-edit'">批量操作</a>
		<div id="mm4" style="width: 150px;">
			<div data-options="iconCls:'icon-move_to_folder'"
				onClick="move2Type()">设置问题分类</div>
			<div data-options="iconCls:'icon-brainstorming'"
				onClick="setTheExpert()">设置指定专家</div>
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-remove'" onClick="delDataQuestion()">关闭删除</div>
		</div>
		<a class="easyui-linkbutton"
			data-options="iconCls:'icon-excel',plain:true,onClick:excelExport">excel导出
		</a>
	</div>
</body>
<script type="text/javascript">
	var url_delActionQuestion = "question/del.do";//删除URL
	var url_formActionQuestion = "question/form.do";//增加、修改、查看URL
	var url_searchActionQuestion = "question/query.do";//查询URL
	var title_windowQuestion = "问题管理";//功能名称
	var gridQuestion;//数据表格对象
	var searchQuestion;//条件查询组件对象
	$(function() {
		//初始化数据表格
		gridQuestion = $('#dataQuestionGrid').datagrid({
			url : url_searchActionQuestion,
			fit : true,
			fitColumns : true,
			'toolbar' : '#toolBarEditor',
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchQuestion = $('#searchQuestionForm').searchForm({
			gridObj : gridQuestion
		});
		$('#FarmdocTree').tree({
			url : 'doctype/FarmDoctypeLoadTreeNode.do',
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchQuestion.dosearch({
					'ruleText' : searchQuestion.arrayStr()
				});
			},
			loadFilter : function(data, parent) {
				return data.treeNodes;
			}
		});
		$('#FarmdocTreeReload').bind('click', function() {
			$('#FarmdocTree').tree('reload');
		});
		$('#FarmdocTreeOpenAll').bind('click', function() {
			$('#FarmdocTree').tree('expandAll');
		});
	});
	//前台访问该知识
	function visitTheFqa() {
		var selectedArray = $(gridQuestion).datagrid('getSelections');
		if (selectedArray.length == 1) {
			window.open('webquest/fqa/Pub' + selectedArray[0].ID + '.html');
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//excel导出
	function excelExport() {
		$.messager.alert('报表加载中...', '请等待,不要关闭本窗口直至报表导出完成... ...', 'warning');
		$('#ruleTextId').val(searchQuestion.arrayStr());
		$('#reportForm').submit();
	}
	//查看
	function viewDataQuestion() {
		var selectedArray = $(gridQuestion).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionQuestion + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winQuestion',
				width : 800,
				height : 350,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//查看邀请专家信息
	function viewExpertBindInfo() {
		var selectedArray = $(gridQuestion).datagrid('getSelections');
		if (selectedArray.length != 1 && selectedArray.length != 0) {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "请选择一条问题或者不选择问题!", 'info');
			return;
		}
		var questionIds = $.farm.getCheckedIds(gridQuestion, 'ID');
		var  dourl='expertquestion/list.do';
		if (selectedArray.length == 1) {
			dourl=dourl+"?questionid="+$.farm.getCheckedIds(gridQuestion, 'ID');
		}
		$.farm.openWindow({
			id : 'winQuestion',
			width : 900,
			height : 450,
			modal : true,
			url : dourl,
			title : '查看邀请专家记录'
		});
	}
	//新增
	function addDataQuestion() {
		var url = url_formActionQuestion + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winQuestion',
			width : 600,
			height : 450,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//设置指定专家
	function setTheExpert() {
		var selectedArray = $(gridQuestion).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var url = "expert/chooseExpert.do";
			$.farm.openWindow({
				id : 'chooseExpertWin',
				width : 800,
				height : 400,
				modal : true,
				url : url,
				title : '选择专家'
			});
			chooseWindowCallBackHandle = function(row) {
				if (row.length != 1) {
					$.messager.alert(MESSAGE_PLAT.PROMPT, "请选择一个专家", 'info');
				} else {
					//远程修改创建人
					var questionids = $.farm.getCheckedIds(gridQuestion, 'ID');
					$.messager.progress({
						title : '提示',
						msg : '',
						text : '设置中...'
					});
					$.post('webquest/bindManyExpert.do', {
						'expertid' : row[0].ID,
						'questionids' : questionids
					}, function(flag) {
						$.messager.progress('close');
						if (flag.STATE == 0) {
							$('#chooseExpertWin').window('close');
							$(gridQuestion).datagrid('reload');
						} else {
							var str = MESSAGE_PLAT.ERROR_SUBMIT + flag.MESSAGE;
							$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
						}
					}, 'json');
				}
			};
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}

	//修改
	function editDataQuestion() {
		var selectedArray = $(gridQuestion).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionQuestion + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winQuestion',
				width : 800,
				height : 450,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//追问管理
	function editZWDataQuestion() {
		var selectedArray = $(gridQuestion).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = "question/zwform.do" + '?ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winQuestion',
				width : 800,
				height : 450,
				modal : true,
				url : url,
				title : '追问管理'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//回答管理
	function editHDDataQuestion() {
		var selectedArray = $(gridQuestion).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = "question/hdform.do" + '?ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winQuestion',
				width : 800,
				height : 450,
				modal : true,
				url : url,
				title : '回答管理'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//评论管理
	function editPLDataQuestion() {
		var selectedArray = $(gridQuestion).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = "question/plform.do" + '?ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winQuestion',
				width : 800,
				height : 450,
				modal : true,
				url : url,
				title : '评论管理'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//移动到分类
	function move2Type() {
		var selectedArray = $(gridQuestion).datagrid('getSelections');
		if (selectedArray.length > 0) {
			$.farm.openWindow({
				id : 'toChooseTypeWin',
				width : 250,
				height : 300,
				modal : true,
				url : "question/chooseTree.do",
				title : '移动问答'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//删除
	function delDataQuestion() {
		var selectedArray = $(gridQuestion).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridQuestion).datagrid('loading');
					$.post(url_delActionQuestion + '?ids='
							+ $.farm.getCheckedIds(gridQuestion, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridQuestion).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridQuestion).datagrid('reload');
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
	//选择树回调函数
	function chooseTypeHook(obj) {
		var docIds = $.farm.getCheckedIds(gridQuestion, 'ID');
		var typeId = obj.id;
		var url = "question/move2Type.do";
		$.post(url, {
			questionids : docIds,
			typeId : typeId
		}, function(data) {
			if (data.STATE == 0) {
				$(gridQuestion).datagrid('reload');
				$("#toChooseTypeWin").window('close');
			}
		}, "json");
	}
</script>
</html>