<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--追加提问表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<div class="tableTitle_msg">${MESSAGE}</div>
		<div class="tableTitle_tag">
			<c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</div>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formQuestionplus">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">CTIME:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[7]']"
						id="entity_ctime" name="ctime" value="${entity.ctime}"></td>
				</tr>
				<tr>
					<td class="title">DESCRIPTION:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32,767.5]']"
						id="entity_description" name="description"
						value="${entity.description}"></td>
				</tr>
				<tr>
					<td class="title">QUESTIONID:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[16]']"
						id="entity_questionid" name="questionid"
						value="${entity.questionid}"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityQuestionplus" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityQuestionplus" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formQuestionplus" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionQuestionplus = 'questionplus/add.do';
	var submitEditActionQuestionplus = 'questionplus/edit.do';
	var currentPageTypeQuestionplus = '${pageset.operateType}';
	var submitFormQuestionplus;
	$(function() {
		//表单组件对象
		submitFormQuestionplus = $('#dom_formQuestionplus').SubmitForm({
			pageType : currentPageTypeQuestionplus,
			grid : gridQuestionplus,
			currentWindowId : 'winQuestionplus'
		});
		//关闭窗口
		$('#dom_cancle_formQuestionplus').bind('click', function() {
			$('#winQuestionplus').window('close');
		});
		//提交新增数据
		$('#dom_add_entityQuestionplus').bind('click', function() {
			submitFormQuestionplus.postSubmit(submitAddActionQuestionplus);
		});
		//提交修改数据
		$('#dom_edit_entityQuestionplus').bind('click', function() {
			submitFormQuestionplus.postSubmit(submitEditActionQuestionplus);
		});
	});
//-->
</script>