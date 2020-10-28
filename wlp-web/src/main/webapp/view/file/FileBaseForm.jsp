<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--文件表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formFilebase">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">ID:</td>
					<td colspan="3"><input type="text" style="width: 140px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[128]']"
						id="entity_id" name="id" value="${entity.id}"></td>
					
				</tr>
				<tr>
					<td class="title">别名:</td>
					<td colspan="1"><input type="text" style="width: 140px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[128]']"
						id="entity_title" name="title" value="${entity.title}"></td>
					<td class="title">业务系统名称:</td>
					<td><input type="text" style="width: 140px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[64]']"
						id="entity_sysname" name="sysname" value="${entity.sysname}">
					</td>
				</tr>
				<c:if test="${pageset.operateType!=2}">
					<tr>
						<td class="title">完整文件名:</td>
						<td colspan="3"><input type="text" style="width: 140px;"
							class="easyui-validatebox"
							data-options="required:true,validType:[,'maxLength[64]']"
							id="entity_filename" name="filename" value="${entity.filename}">
						</td>
					</tr>
					<tr>
						<td class="title">相对目录:</td>
						<td colspan="3"><input type="text" style="width: 430px;"
							class="easyui-validatebox"
							data-options="required:true,validType:[,'maxLength[512]']"
							id="entity_relativepath" name="relativepath"
							value="${entity.relativepath}"></td>
					</tr>
					<tr>
						<td class="title">校验码key:</td>
						<td colspan="3"><input type="text" style="width: 140px;"
							class="easyui-validatebox"
							data-options="required:true,validType:[,'maxLength[16]']"
							id="entity_secret" name="secret" value="${entity.secret}">
						</td>
					</tr>
					<tr>
						<td class="title">绝对路径:</td>
						<td colspan="3" style="font-size: 10px;">${realpath}</td>
					</tr>
					<tr>
						<td class="title">资源库:</td>
						<td>${entity.resourcekey}</td>
						<td class="title">状态:</td>
						<td><select style="width: 140px;" class="easyui-validatebox"
							data-options="required:true" id="entity_pstate" name="pstate"
							val="${entity.pstate}">
								<option value="0">临时</option>
								<option value="1">持久</option>
								<option value="2">永久</option>
								<option value="9">删除</option>
						</select></td>
					</tr>

					<tr>
						<td class="title">大小:</td>
						<td><input type="text" style="width: 140px;"
							class="easyui-validatebox"
							data-options="required:true,validType:[,'maxLength[5]']"
							id="entity_filesize" name="filesize" value="${entity.filesize}">
						</td>
						<td class="title">文件扩展名:</td>
						<td><input type="text" style="width: 140px;"
							class="easyui-validatebox"
							data-options="required:true,validType:[,'maxLength[16]']"
							id="entity_exname" name="exname" value="${entity.exname}">
						</td>
					</tr>
				</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityFilebase" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityFilebase" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formFilebase" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionFilebase = 'filebase/add.do';
	var submitEditActionFilebase = 'filebase/edit.do';
	var currentPageTypeFilebase = '${pageset.operateType}';
	var submitFormFilebase;
	$(function() {
		//表单组件对象
		submitFormFilebase = $('#dom_formFilebase').SubmitForm({
			pageType : currentPageTypeFilebase,
			grid : gridFilebase,
			currentWindowId : 'winFilebase'
		});
		//关闭窗口
		$('#dom_cancle_formFilebase').bind('click', function() {
			$('#winFilebase').window('close');
		});
		//提交新增数据
		$('#dom_add_entityFilebase').bind('click', function() {
			submitFormFilebase.postSubmit(submitAddActionFilebase);
		});
		//提交修改数据
		$('#dom_edit_entityFilebase').bind('click', function() {
			submitFormFilebase.postSubmit(submitEditActionFilebase);
		});
	});
//-->
</script>