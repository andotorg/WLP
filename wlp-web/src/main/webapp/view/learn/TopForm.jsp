<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--置顶文档表单-->
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
		<form id="dom_formFarmtop">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td width="70"></td>
					<td width="100"></td>
					<td width="70"></td>
					<td width="100"></td>
				</tr>
				<c:if test="${pageset.operateType!=0}">
					<tr>
						<td class="title"></td>
						<td colspan="3"><a id="buttonChooseClassChoose"
							href="javascript:void(0)" iconCls="icon-blog"
							class="easyui-linkbutton">选择课程</a></td>
					</tr>
				</c:if>
				<tr>
					<td class="title">文档标题:</td>
					<td colspan="3"><input type="text" style="width: 380px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[128]']"
						id="entity_title" name="title" value="${entity.title}"></td>
				</tr>
				<tr>
					<td class="title">内容类型:</td>
					<td colspan="3"><span id="lable_modelName">自定义</span> <input
						id="lable_modelType" type="hidden" name="model" value="4">
					</td>
				</tr>
				<tr>
					<td class="title">链接地址:</td>
					<td colspan="3"><input type="text" style="width: 380px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[512]']"
						id="entity_url" name="url" value="${entity.url}"></td>
				</tr>
				<tr>
					<td class="title">展示类型:</td>
					<td colspan="3"><select id="entity_type" name="type"
						val="${entity.type}">
							<option value="1">首页推荐</option>
							<option value="2">大图推荐</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">预览图片:</td>
					<td><c:if test="${pageset.operateType!=0}">
							<input type="button" id="uploadButton" value="上传预览图片" />
							<script type="text/javascript">
								$(function() {
									var uploadbutton;
									uploadbutton = KindEditor
											.uploadbutton({
												button : KindEditor('#uploadButton')[0],
												fieldName : 'imgFile',
												url : 'upload/media.do',
												afterUpload : function(data) {
													if (data.error === 0) {
														$('#entity_imgid').val(
																data.id);
														$('#view_img')
																.attr(
																		"src",
																		data.url);
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
								});
							</script>
						</c:if></td>
					<td rowspan="3" colspan="2"
						style="text-align: center; background-color: #eee;"><input
						type="hidden" id="entity_imgid" name="imgid"
						value="${entity.imgid}"> <img id="view_img"
						style="max-height: 64px; padding: 4px; background-color: white; border: 1px #ccc;"
						src="text/img/demo/colors-a4.jpg"></td>
				</tr>
				<tr>
					<td class="title">排序:</td>
					<td><input id="entity_sort" name="sort"
						class="easyui-numberspinner" required="required"
						data-options="min:1,max:100,editable:false,height:26"
						value="${entity.sort}"></td>
				</tr>
				<tr>
					<td class="title">状态:</td>
					<td><select id="entity_pstate" name="pstate"
						val="${entity.pstate}">
							<option value="1">可用</option>
							<option value="0">禁用</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">备注简介:</td>
					<td colspan="3"><textarea name="pcontent" id="contentId"
							class="easyui-validatebox"
							data-options="required:false,validType:[,'maxLength[128]']"
							style="height: 42px; width: 380px;">${entity.pcontent}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityFarmtop" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityFarmtop" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formFarmtop" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionFarmtop = 'top/add.do';
	var submitEditActionFarmtop = 'top/edit.do';
	var currentPageTypeFarmtop = '${pageset.operateType}';
	var submitFormFarmtop;
	$(function() {
		//表单组件对象
		submitFormFarmtop = $('#dom_formFarmtop').SubmitForm({
			pageType : currentPageTypeFarmtop,
			grid : gridFarmtop,
			currentWindowId : 'winFarmtop'
		});
		//关闭窗口
		$('#dom_cancle_formFarmtop').bind('click', function() {
			$('#winFarmtop').window('close');
		});
		//提交新增数据
		$('#dom_add_entityFarmtop').bind('click', function() {
			if (!selfPageValidate()) {
				return;
			}
			submitFormFarmtop.postSubmit(submitAddActionFarmtop);
		});
		//提交修改数据
		$('#dom_edit_entityFarmtop').bind('click', function() {
			if (!selfPageValidate()) {
				return;
			}
			submitFormFarmtop.postSubmit(submitEditActionFarmtop);
		});
		$("#entity_url").change(function() {
			$("#lable_modelName").text("自定义");
			$("#lable_modelType").val("0");
			$("#entity_docid").val(null);
		});
		//打开文档页面
		$('#buttonChooseClassChoose').bindChooseWindow(
				'chooseChoosedocWin',
				{
					width : 600,
					height : 400,
					modal : true,
					url : 'top/toDocTopChooseClass.do',
					title : '选择课程',
					callback : function(rows) {
						$("#entity_docid").val(rows[0].ID);
						$("#entity_url").val(
								"classweb/Pubview.do?classid=" + rows[0].ID);
						$("#entity_title").val(rows[0].NAME);
						$("#lable_modelName").text("课程");
						$("#lable_modelType").val("1");
						$('#dom_formFarmtop').form('validate');
						loadClassImg(rows[0].ID);
					}
				});
		$('#view_img').attr("src", "text/img/demo/colors-a4.jpg");
		loadModelTitle();
	});
	//通过docid加载图片i
	function loadClassImg(classid) {
		$.post("classt/findImg.do", {
			"id" : classid
		}, function(flag) {
			if (flag.STATE == 0) {
				$('#entity_imgid').val(flag.imgid);
				$('#view_img').attr('src', flag.url);
			}
		}, 'json');
	}
	//加载内容类型
	function loadModelTitle() {
		if ('${entity.model}' == '1') {
			$("#lable_modelName").text("知识");
			$("#lable_modelType").val("1");
		}
		if ('${entity.model}' == '2') {
			$("#lable_modelName").text("专题");
			$("#lable_modelType").val("2");
		}
		if ('${entity.model}' == '3') {
			$("#lable_modelName").text("考场");
			$("#lable_modelType").val("3");
		}
		if ('${entity.model}' == '4') {
			$("#lable_modelName").text("自定义");
			$("#lable_modelType").val("4");
		}
	}
	function selfPageValidate() {
		if (!$('#entity_imgid').val()) {
			$.messager.alert('警告', '请上传预览图!');
			return false;
		}
		if (($('#entity_type').val() == '3' || $('#entity_type').val() == '4')
				&& ($("#entity_docid").val() == null || $("#entity_docid")
						.val() == '')) {
			$.messager.alert('警告', '只有"知识类型"内容可选择"小组首页"或"专家首页"!');
			return false;
		}
		return true;
	}
//-->
</script>