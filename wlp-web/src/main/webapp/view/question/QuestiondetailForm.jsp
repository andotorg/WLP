<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--问题用量明细表单-->
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
		<form id="dom_formQuestiondetail">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">VTYPE:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[1]']"
						id="entity_vtype" name="vtype" value="${entity.vtype}"></td>
				</tr>
				<tr>
					<td class="title">USERIP:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[64]']"
						id="entity_userip" name="userip" value="${entity.userip}">
					</td>
				</tr>
				<tr>
					<td class="title">QUESTIONID:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[16]']"
						id="entity_questionid" name="questionid"
						value="${entity.questionid}"></td>
				</tr>
				<tr>
					<td class="title">PSTATE:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[1]']"
						id="entity_pstate" name="pstate" value="${entity.pstate}">
					</td>
				</tr>
				<tr>
					<td class="title">CUSER:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[16]']" id="entity_cuser"
						name="cuser" value="${entity.cuser}"></td>
				</tr>
				<tr>
					<td class="title">CUSERNAME:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[32]']" id="entity_cusername"
						name="cusername" value="${entity.cusername}"></td>
				</tr>
				<tr>
					<td class="title">CTIME:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[8]']"
						id="entity_ctime" name="ctime" value="${entity.ctime}"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityQuestiondetail" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityQuestiondetail" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formQuestiondetail" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionQuestiondetail = 'questiondetail/add.do';
	var submitEditActionQuestiondetail = 'questiondetail/edit.do';
	var currentPageTypeQuestiondetail = '${pageset.operateType}';
	var submitFormQuestiondetail;
	$(function() {
		//表单组件对象
		submitFormQuestiondetail = $('#dom_formQuestiondetail').SubmitForm({
			pageType : currentPageTypeQuestiondetail,
			grid : gridQuestiondetail,
			currentWindowId : 'winQuestiondetail'
		});
		//关闭窗口
		$('#dom_cancle_formQuestiondetail').bind('click', function() {
			$('#winQuestiondetail').window('close');
		});
		//提交新增数据
		$('#dom_add_entityQuestiondetail').bind('click', function() {
			submitFormQuestiondetail.postSubmit(submitAddActionQuestiondetail);
		});
		//提交修改数据
		$('#dom_edit_entityQuestiondetail').bind(
				'click',
				function() {
					submitFormQuestiondetail
							.postSubmit(submitEditActionQuestiondetail);
				});
	});
//-->
</script>