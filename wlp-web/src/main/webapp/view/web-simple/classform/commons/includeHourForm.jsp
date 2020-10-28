<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- Modal -->
<div class="modal fade" id="classhourForm" tabindex="-1" role="dialog"
	aria-labelledby="hourOpLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="hourOpLabel">章节信息</h4>
			</div>
			<div class="modal-body">
				<form method="post" id="submithourFormId"
					action="classweb/savebase.do">
					<input name="classid" id="chapterId" type="hidden"> <input
						name="id" id="hourId" type="hidden">
					<fieldset id="hourfieldsetId">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group" id="hourTitleGroupId">
									<label for="hourTitleId" class="control-label">课时名称&nbsp;<span
										class="text-danger">*</span></label> <input name="title" type="text"
										class="form-control" id="hourTitleId" placeholder="课时名称">
									<span id="hourTitleErrorId" class="help-block"></span>
								</div>
								<div class="form-group" id="hourSortGroupId">
									<label for="hourSortId" class="control-label">排列顺序&nbsp;<span
										class="text-danger">*</span></label> <input name="sort" type="text"
										class="form-control" id="hourSortId" placeholder="整数">
									<span id="hourSortErrorId" class="help-block"></span>
								</div>
								<div class="form-group" id="hourFileGroupId">
									<label for="hourFileId" class="control-label">课件文件&nbsp;<span
										class="text-danger">*</span>&nbsp;&nbsp;<span
										style="font-weight: 200; font-size: 12px;">课件仅支持MP4和PDF文件（PDF文件请勿大于100m）</span></label>
									<div class="row">
										<div class="col-xs-6">
											<input type="text" class="form-control" id="hourFileId"
												readonly="readonly"></input> 
											<input type="hidden" class="form-control" name="fileid" id="hourFileIdValId" ></input>
										</div>
										<div class="col-xs-6"><jsp:include
												page="/view/web-simple/commons/fileUploadCommons.jsp">
												<jsp:param value="hourfile" name="appkey" />
												<jsp:param value="HOUR" name="type" />
												<jsp:param value="上传课件" name="title" />
											</jsp:include></div>
									</div>
									<span id="hourFileErrorId" class="help-block"></span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group" id="hourNoteGroupId">
									<label for="hourNoteId" class="control-label">课时简述</label>
									<textarea class="form-control" id="hourNoteId" name="note"
										placeholder="简述" rows="3"></textarea>
									<span id="hourNoteErrorId" class="help-block"></span>
								</div>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="hourSubmitButtonId"
					onclick="submithourForm();" class="btn btn-success">提交课时</button>
				<p class="bg-danger"
					style="padding: 20px; margin-top: 20px; color: #f01414; display: none;"
					id="hourErrorsShowboxId"></p>
			</div>
		</div>
	</div>
</div>
<script>
	$(function() {
		$('select', '#submithourFormId').each(function(i, obj) {
			var val = $(obj).attr('val');
			$(obj).val(val);
		});
		validateInput('hourTitleId', function(id, val, obj) {
			// 标题
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (valid_maxLength(val, 128)) {
				return {
					valid : false,
					msg : '长度不能大于' + 128
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'hourTitleErrorId', 'hourTitleGroupId');
		validateInput('hourSortId', function(id, val, obj) {
			// 时长
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			if (!valid_isNumber(val)) {
				return {
					valid : false,
					msg : '必须为数字'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'hourSortErrorId', 'hourSortGroupId');

		validateInput('hourFileId', function(id, val, obj) {
			// 课件文件
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'hourFileErrorId', 'hourFileGroupId');

		validateInput('hourNoteId', function(id, val, obj) {
			// 课程简介
			if (valid_maxLength(val, 512)) {
				return {
					valid : false,
					msg : '长度不能大于' + 512
				};
			}
			return {
				valid : true,
				msg : '正确'
			};
		}, 'hourNoteErrorId', 'hourNoteGroupId');
	});
	//初始化表单
	function inithourForm(chapterid, hourid) {
		$('#hourfieldsetId,#chapterId').val(chapterid);
		if (hourid) {
			$('#hourOpLabel').text("編輯课时");
			$('#hourfieldsetId,fieldset').attr('disabled', 'disabled');
			$('#hourSubmitButtonId').attr('disabled', 'disabled');
			$.post('classweb/loadhour.do', {
				'hourid' : hourid
			}, function(flag) {
				if (flag.STATE == 0) {
					$('#hourfieldsetId,fieldset').removeAttr("disabled");
					$('#hourSubmitButtonId').removeAttr("disabled");
					$('#hourfieldsetId,#hourId').val(flag.entity.id);
					$('#hourfieldsetId,#hourTitleId').val(flag.entity.title);
					$('#hourfieldsetId,#hourNoteId').val(flag.entity.note);
					$('#hourfieldsetId,#hourSortId').val(flag.entity.sort);
					if(flag.persist){
						$('#hourfieldsetId,#hourFileId').val(flag.persist.name);
					}else{
						$('#hourfieldsetId,#hourFileId').val('');
					}
					$('#hourfieldsetId,#hourFileIdValId').val(flag.entity.fileid);
				} else {
					alert(flag.MESSAGE);
				}
			}, 'json');
		} else {
			$('#hourfieldsetId,#hourId').val('');
			$('#hourfieldsetId,#hourTitleId').val('');
			$('#hourfieldsetId,#hourNoteId').val('');
			$('#hourfieldsetId,#hourSortId').val('');
			$('#hourfieldsetId,#hourFileId').val('');
			$('#hourfieldsetId,#hourFileIdValId').val('');
			$('#hourOpLabel').text("创建课时");
		}
	}

	//提交表单
	function submithourForm() {
		if (!validate('submithourFormId')) {
			$('#hourErrorsShowboxId').text('信息录入有误，请检查！');
			$('#hourErrorsShowboxId').show();
			return;
		}
		$('#hourErrorsShowboxId').text('');
		$('#hourErrorsShowboxId').hide();
		if (confirm("是否提交本章节？")) {
			$('#hourSubmitButtonId').attr('disabled', 'disabled');
			$.post('classweb/savehour.do', {
				'title' : $('#hourTitleId').val(),
				'fileid' : $('#hourFileIdValId').val(),
				'note' : $('#hourNoteId').val(),
				'chapterid' : $('#chapterId').val(),
				'sort' : $('#hourSortId').val(),
				'id' : $('#hourfieldsetId,#hourId').val()
			}, function(flag) {
				if (flag.STATE == 0) {
					//alert("提交成功");
					$('#myModal').modal('hide')
					location.reload();
				} else {
					alert(flag.MESSAGE);
					$('#hourfieldsetId,fieldset').removeAttr("disabled");
					$('#hourSubmitButtonId').removeAttr("disabled");
				}
			}, 'json');
		}
	}
	//图片提交成功
	function fileUploadHandle(appkey, url, id, fileName) {
		if ('hourfile' == appkey) {
			$('#hourFileId').val(fileName);
			$('#hourFileIdValId').val(id);
		}
	}
</script>