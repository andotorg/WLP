<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--选择问答-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchQuestion">
			<table class="editTable">
				<tr>
					<td class="title">题目:</td>
					<td>
						<input name="TITLE:like" type="text">
					</td>
					<td class="title">发布开始(含):</td>
					<td>
						<input name="STARTTIME:like" type="text" class="easyui-datebox"></input>
					</td>
					<td class="title">发布结束(含):</td>
					<td>
						<input name="ENDTIME:like" type="text" class="easyui-datebox"></input>
					</td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="6">
						<a id="a_search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
						<a id="a_reset" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_chooseGridQuestion">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="TITLE" data-options="sortable:true" width="60">题目</th>
					<th field="TYPENAME" data-options="sortable:true" width="40">分类</th>
					<th field="PUBTIME" data-options="sortable:true" width="40">发布时间</th>
					<th field="PSTATE" data-options="sortable:true" width="20">状态</th>
					<th field="CUSERNAME" data-options="sortable:true" width="30">创建人</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var chooseGridQuestion;
	var chooseSearchfarmQuestion;
	var toolbar_chooseQuestion = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_chooseGridQuestion').datagrid(
					'getSelections');
			if (selectedArray.length > 0) {
				chooseWindowCallBackHandle(selectedArray);
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
						'info');
			}
		}
	} ];
	$(function() {
		chooseGridQuestion = $('#dom_chooseGridQuestion').datagrid({
			url : 'question/questionChooseQuery.do',
			fit : true,
			'toolbar' : toolbar_chooseQuestion,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfarmQuestion = $('#dom_chooseSearchQuestion').searchForm({
			gridObj : chooseGridQuestion
		});
	});
//-->
</script>







<!--1.在调用JSP页面，中粘贴下面js中的一段（绑定到按钮事件，或通过方法打开窗口） 
//---------------------------使用下面的（绑定到按钮事件）----------------------------------------------------- 
<a id="buttonQuestionChoose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
  $(function() {
    $('#buttonQuestionChoose').bindChooseWindow('chooseQuestionWin', {
      width : 600,
      height : 300,
      modal : true,
      url : 'admin/QuestionChooseGridPage.do',
      title : '选择问答',
      callback : function(rows) {
        //$('#NAME_like').val(rows[0].NAME);
      }
    });
  });
</script>
//----------------------或----使用下面的（窗口中打开）----------------------------------------------------- 
chooseWindowCallBackHandle = function(row) {
    $("#chooseQuestionWin").window('close');  
};
$.farm.openWindow( {
  id : 'chooseQuestionWin',
  width : 600,
  height : 300,
  modal : true,
  url : 'admin/QuestionChooseGridPage.do',
  title : '选择问答'
});
 -->





<!--2.粘贴到Action中的java方法
@RequestMapping("/QuestionChooseQuery")
@ResponseBody
public Map<String, Object> QuestionChooseQuery(DataQuery query,
		HttpServletRequest request) {
	try {
		query = EasyUiUtils.formatGridQuery(request, query);
		DataResult result = //////;
		return ViewMode.getInstance()
				.putAttrs(EasyUiUtils.formatGridData(result))
				.returnObjMode();
	} catch (Exception e) {
		log.error(e.getMessage());
		return ViewMode.getInstance().setError(e.getMessage())
				.returnObjMode();
	}
}
@RequestMapping("/QuestionChooseGridPage")
	public ModelAndView QuestionChooseGridPage(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("device/ChoosedeviceChooseGridWin");
	}
-->