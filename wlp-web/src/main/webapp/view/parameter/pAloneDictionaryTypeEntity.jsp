<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--数据类型-->
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
		<form id="dom_formparameter">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">展示名称:</td>
					<td colspan="3"><input type="text" style="width: 430px;"
						class="easyui-validatebox"
						data-options="required:true,validType:',maxLength[64]'"
						id="entity_name" name="name" value="${entity.name}" /> <input
						type="hidden" name="entity" value="${entity.entity}"></td>
				</tr>
				<tr>
					<td class="title"><c:if test="${type!='2'}">
						字典项值:</c:if> <c:if test="${type=='2'}">
						文件ID:</c:if></td>
					<td colspan="3"><c:if test="${type!='2'}">
							<input type="text" style="width: 430px;"
								class="easyui-validatebox"
								data-options="required:true,validType:',maxLength[128]'"
								id="entity_entitytype" name="entitytype"
								value="${entity.entitytype}" />
						</c:if> <c:if test="${type=='2'}">
							<input type="text" readonly="readonly" style="width: 300px;"
								class="easyui-validatebox"
								data-options="required:true,validType:',maxLength[128]'"
								id="entity_entitytype" name="entitytype"
								value="${entity.entitytype}" />&nbsp;<input type="button"
								id="uploadButton" value="上传文件" />
							<a id='checkFileAble'>校验文件</a>
						</c:if></td>
				</tr>
				<tr>
					<td class="title">排序号:</td>
					<td colspan="1"><input type="text" class="easyui-validatebox"
						data-options="required:true,validType:''" id="entity_sort" style="width: 120px;"
						name="sort" value="${entity.sort}"></td>
					<td class="title">状态:</td>
					<td colspan="1"><select name="state" id="entity_state"
						val="${entity.state}">
							<option value="1">可用</option>
							<option value="0">禁用</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><textarea rows="3" style="width: 430px;"
							class="easyui-validatebox"
							data-options="required:false,validType:',maxLength[64]'"
							id="entity_comments" name="comments">${entity.comments}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityparameter" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityparameter" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formparameter" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionparameter = 'dictionaryType/add.do';
	var submitEditActionparameter = 'dictionaryType/edit.do';
	var currentPageTypeparameter = '${pageset.operateType}';
	var submitFormparameter;
	$(function() {
		//表单组件对象
		submitFormparameter = $('#dom_formparameter').SubmitForm({
			pageType : currentPageTypeparameter,
			grid : gridDiclisttype,
			currentWindowId : 'winDiclisttype'
		});
		//关闭窗口
		$('#dom_cancle_formparameter').bind('click', function() {
			$('#winDiclisttype').window('close');
		});
		//提交新增数据
		$('#dom_add_entityparameter').bind('click', function() {
			submitFile();
			submitFormparameter.postSubmit(submitAddActionparameter);
		});
		//提交修改数据
		$('#dom_edit_entityparameter').bind('click', function() {
			submitFile();
			submitFormparameter.postSubmit(submitEditActionparameter);
		});
	});
</script>
<c:if test="${type!='2'}">
	<!-- 序列項字典，樹形字典項 -->
	<script type="text/javascript">
		function submitFile() {
			//不提交文件
		}
	</script>
</c:if>
<c:if test="${type=='2'}">
	<!-- 文件項字典 -->
	<script type="text/javascript">
		//提交字典附件
		function submitFile() {
			$.post('docfile/doPermanent.do', {
				'fileid' : $("#entity_entitytype").val(),
				'note' : '数据字典文件项'
			}, function(flag) {
				if (flag.STATE == '1') {
					alert(flag.MESSAGE);
				}
			}, 'json');
		}
	</script>
	<c:if test="${pageset.operateType!=0}">
		<script type="text/javascript">
			$(function() {
				//上次附件
				var uploadbutton = KindEditor.uploadbutton({
					button : KindEditor('#uploadButton')[0],
					fieldName : 'imgFile',
					url : 'webfile/PubFPupload.do',
					afterUpload : function(data) {
						if (data.error === 0) {
							$("#entity_entitytype").val(data.id);
						} else {
							if (data.message) {
								alert(data.message);
							} else {
								alert('请检查文件格式');
							}
						}
					},
					afterError : function(str) {
						alert('自定义错误信息: ' + str);
					}
				});
				uploadbutton.fileBox.change(function(e) {
					uploadbutton.submit();
				});
				//校验附件
				$('#checkFileAble').click(
						function() {
							$.post('docfile/fileAble.do', {
								'fileid' : $("#entity_entitytype").val()
							}, function(flag) {
								alert(flag.state ? ("可用,文件名称:" + flag.filename)
										: "不可用，请重新上传")
							}, 'json');
						});
			});
		</script>
	</c:if>
</c:if>
