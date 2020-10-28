<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--问题表单-->
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
		<form id="dom_formQuestion">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">题目:</td>
					<td colspan="7"><input type="text" style="width: 460px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_title" name="title" value="${entity.title}"></td>
				</tr>
				<tr>
					<td class="title"></td>
					<td colspan="7">${entity.description}</td>
				</tr>
				<c:forEach items="${FQA.questionpluses}" var="node">
					<tr>
						<td class="title"></td>
						<td colspan="7">${node.description}</td>
					</tr>
				</c:forEach>
				<tr>
					<td class="title">预览图id:</td>
					<td colspan="3"><c:if test="${pageset.operateType !=0}">
							<input type="button" id="uploadButton" value="上传预览图" />
							<script type="text/javascript">
								$(function() {
									var uploadbutton;
									uploadbutton = KindEditor
											.uploadbutton({
												button : KindEditor('#uploadButton')[0],
												fieldName : 'imgFile',
												url : 'webfile/PubFPuploadImg.do',
												afterUpload : function(data) {
													if (data.error === 0) {
														$("#entity_fileid")
																.val(data.id);
														$("#imgFileboxId")
																.html(
																		"<a  target='_blank'  href='" + data.url
																+ "'>下载</a>");
														$("#imgFileboxId")
																.show();
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
						</c:if> <span id="imgFileboxId"> <c:if test="${FQA.imgUrl!=null}">
								<a target='_blank' href='${FQA.imgUrl}'>下载</a>
								<c:if test="${pageset.operateType !=0}">
									<a href='javascript:void(0);' onclick="delImg()">删除</a>
								</c:if>
							</c:if>
					</span> <input type="hidden" id="entity_fileid" name="imgid"
						value="${entity.imgid}"></td>
					<td class="title"></td>
					<td colspan="3"></td>
				</tr>
				<tr>
					<!-- <td class="title">标签:</td>
					<td colspan="3"><input type="text" style="width: 180px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[128]']" id="entity_tags"
						name="tags" value="${entity.tags}"></td>
					 -->
					<td class="title">状态:</td>
					<td colspan="7"><select name="pstate" val="${entity.pstate}">
							<option value="1">提问中</option>
							<option value="2">完成</option>
							<!-- 1提问中，0删除，2完成 -->
					</select></td>
				</tr>
				<tr>
					<td class="title">访问数:</td>
					<td colspan="3">${entity.visitnum}</td>
					<td class="title">发布时间:</td>
					<td colspan="3"><PF:FormatTime date="${entity.pubtime}"
							yyyyMMddHHmmss="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<td class="title">关注数:</td>
					<td colspan="3">${entity.attentions}</td>
					<td class="title">回答数:</td>
					<td colspan="3">${entity.answers}</td>
				</tr>
				<tr>
					<td class="title">分类:</td>
					<td colspan="3">${FQA.type.name}</td>
					<td class="title">创建人:</td>
					<td colspan="3">${entity.cusername}</td>
				</tr>
				<!--<tr>
					<td class="title">内容:</td>
					<td colspan="7"><input type="text" style="width: 180px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32,767.5]']"
						id="entity_description" name="description"
						value="${entity.description}"></td>
				</tr>
				<tr><td class="title">评论数:</td>
					<td colspan="3"><input type="text" style="width: 180px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[5]']"
						id="entity_answeringnum" name="answeringnum"
						value="${entity.answeringnum}"></td>
				</tr> 
				<tr>
					<td class="title">差评数:</td>
					<td colspan="3"><input type="text" style="width: 180px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[5]']"
						id="entity_praiseno" name="praiseno" value="${entity.praiseno}">
					</td>
					<td class="title">好评数:</td>
					<td colspan="3"><input type="text" style="width: 180px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[5]']"
						id="entity_praiseyes" name="praiseyes" value="${entity.praiseyes}">
					</td>
				</tr> <tr>
					<td class="title">是否匿名:</td>
					<td colspan="3"><input type="text" style="width: 180px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[1]']"
						id="entity_anonymous" name="anonymous" value="${entity.anonymous}">
					</td>
				</tr> <tr>
					<td class="title">结束时间:</td>
					<td colspan="3"><input type="text" style="width: 180px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[5]']"
						id="entity_endtime" name="endtime" value="${entity.endtime}">
					</td>
				</tr><tr>
					<td class="title">悬赏:</td>
					<td colspan="3"><input type="text" style="width: 180px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[5]']"
						id="entity_price" name="price" value="${entity.price}"></td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><input type="text" style="width: 180px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[64]']" id="entity_pcontent"
						name="pcontent" value="${entity.pcontent}"></td>
				</tr><tr>
					<td class="title">创建时间:</td>
					<td colspan="3"><input type="text" style="width: 180px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[8]']"
						id="entity_ctime" name="ctime" value="${entity.ctime}"></td>
				</tr>-->
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityQuestion" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityQuestion" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formQuestion" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionQuestion = 'question/add.do';
	var submitEditActionQuestion = 'question/edit.do';
	var currentPageTypeQuestion = '${pageset.operateType}';
	var submitFormQuestion;
	$(function() {
		//表单组件对象
		submitFormQuestion = $('#dom_formQuestion').SubmitForm({
			pageType : currentPageTypeQuestion,
			grid : gridQuestion,
			currentWindowId : 'winQuestion'
		});
		//关闭窗口
		$('#dom_cancle_formQuestion').bind('click', function() {
			$('#winQuestion').window('close');
		});
		//提交新增数据
		$('#dom_add_entityQuestion').bind('click', function() {
			submitFormQuestion.postSubmit(submitAddActionQuestion);
		});
		//提交修改数据
		$('#dom_edit_entityQuestion').bind('click', function() {
			submitFormQuestion.postSubmit(submitEditActionQuestion);
		});
	});
//-->
</script>