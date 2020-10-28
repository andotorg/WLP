<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--问答答案表单-->
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
		<form id="dom_formQanswer">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">好评数:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[5]']"
						id="entity_praiseyes" name="praiseyes" value="${entity.praiseyes}">
					</td>
				</tr>
				<tr>
					<td class="title">差评数:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[5]']"
						id="entity_praiseno" name="praiseno" value="${entity.praiseno}">
					</td>
				</tr>
				<tr>
					<td class="title">评论数:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[5]']"
						id="entity_answeringnum" name="answeringnum"
						value="${entity.answeringnum}"></td>
				</tr>
				<tr>
					<td class="title">采用时间:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[16]']" id="entity_adopttime"
						name="adopttime" value="${entity.adopttime}"></td>
				</tr>
				<tr>
					<td class="title">内容:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32,767.5]']"
						id="entity_description" name="description"
						value="${entity.description}"></td>
				</tr>
				<tr>
					<td class="title">PCONTENT:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[64]']" id="entity_pcontent"
						name="pcontent" value="${entity.pcontent}"></td>
				</tr>
				<tr>
					<td class="title">问题ID:</td>
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
				<a id="dom_add_entityQanswer" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityQanswer" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formQanswer" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionQanswer = 'qanswer/add.do';
	var submitEditActionQanswer = 'qanswer/edit.do';
	var currentPageTypeQanswer = '${pageset.operateType}';
	var submitFormQanswer;
	$(function() {
		//表单组件对象
		submitFormQanswer = $('#dom_formQanswer').SubmitForm({
			pageType : currentPageTypeQanswer,
			grid : gridQanswer,
			currentWindowId : 'winQanswer'
		});
		//关闭窗口
		$('#dom_cancle_formQanswer').bind('click', function() {
			$('#winQanswer').window('close');
		});
		//提交新增数据
		$('#dom_add_entityQanswer').bind('click', function() {
			submitFormQanswer.postSubmit(submitAddActionQanswer);
		});
		//提交修改数据
		$('#dom_edit_entityQanswer').bind('click', function() {
			submitFormQanswer.postSubmit(submitEditActionQanswer);
		});
	});
//-->
</script>