<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--回答评论表单-->
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
		<form id="dom_formQanswercomment">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">DESCRIPTION:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32,767.5]']"
						id="entity_description" name="description"
						value="${entity.description}"></td>
				</tr>
				<tr>
					<td class="title">ANSWERID:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[16]']"
						id="entity_answerid" name="answerid" value="${entity.answerid}">
					</td>
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
						data-options="required:true,validType:[,'maxLength[16]']"
						id="entity_cuser" name="cuser" value="${entity.cuser}"></td>
				</tr>
				<tr>
					<td class="title">CUSERNAME:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_cusername" name="cusername" value="${entity.cusername}">
					</td>
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
				<a id="dom_add_entityQanswercomment" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityQanswercomment" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formQanswercomment" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionQanswercomment = 'qanswercomment/add.do';
	var submitEditActionQanswercomment = 'qanswercomment/edit.do';
	var currentPageTypeQanswercomment = '${pageset.operateType}';
	var submitFormQanswercomment;
	$(function() {
		//表单组件对象
		submitFormQanswercomment = $('#dom_formQanswercomment').SubmitForm({
			pageType : currentPageTypeQanswercomment,
			grid : gridQanswercomment,
			currentWindowId : 'winQanswercomment'
		});
		//关闭窗口
		$('#dom_cancle_formQanswercomment').bind('click', function() {
			$('#winQanswercomment').window('close');
		});
		//提交新增数据
		$('#dom_add_entityQanswercomment').bind('click', function() {
			submitFormQanswercomment.postSubmit(submitAddActionQanswercomment);
		});
		//提交修改数据
		$('#dom_edit_entityQanswercomment').bind(
				'click',
				function() {
					submitFormQanswercomment
							.postSubmit(submitEditActionQanswercomment);
				});
	});
//-->
</script>