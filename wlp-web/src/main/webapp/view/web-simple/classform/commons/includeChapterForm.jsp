<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- Modal -->
<div class="modal fade" id="classChapterForm" tabindex="-1"
	role="dialog" aria-labelledby="chapterOpLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="chapterOpLabel">章节信息</h4>
			</div>
			<div class="modal-body">
				<form method="post" id="submitChapterFormId"
					action="classweb/savebase.do">
					<input name="classid" id="classidId" type="hidden"
						value="${classview.classt.id}"> <input name="id"
						id="chapterId" type="hidden">
					<fieldset id="chapterfieldsetId">
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group" id="titleGroupId">
									<label for="titleId" class="control-label">章节名称&nbsp;<span
										class="text-danger">*</span></label> <input name="title" type="text"
										class="form-control" id="titleId" placeholder="章节名称">
									<span id="titleErrorId" class="help-block"></span>
								</div>
								<div class="form-group" id="sortGroupId">
									<label for="sortId" class="control-label">排列顺序&nbsp;<span
										class="text-danger">*</span></label> <input name="sort" type="text"
										class="form-control" id="sortId" placeholder="整数"> <span
										id="sortErrorId" class="help-block"></span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="form-group" id="noteGroupId">
									<label for="noteId" class="control-label">章节简述&nbsp;<span
										class="text-danger">*</span></label>
									<textarea class="form-control" id="noteId" name="note"
										placeholder="简述" rows="3"></textarea>
									<span id="noteErrorId" class="help-block"></span>
								</div>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="chapterSubmitButtonId"
					onclick="submitChapterForm();" class="btn btn-success">提交章节</button>
				<p class="bg-danger"
					style="padding: 20px; margin-top: 20px; color: #f01414; display: none;"
					id="chapterErrorsShowboxId"></p>
			</div>
		</div>
	</div>
</div>
<script>
	$(function() {
		$('select', '#submitChapterFormId').each(function(i, obj) {
			var val = $(obj).attr('val');
			$(obj).val(val);
		});
		validateInput('titleId', function(id, val, obj) {
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
		}, 'titleErrorId', 'titleGroupId');
		validateInput('sortId', function(id, val, obj) {
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
		}, 'sortErrorId', 'sortGroupId');
		validateInput('noteId', function(id, val, obj) {
			// 课程简介
			if (valid_isNull(val)) {
				return {
					valid : false,
					msg : '不能为空'
				};
			}
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
		}, 'noteErrorId', 'noteGroupId');
	});
	//初始化表单
	function initChapterForm(id) {
		if (id) {
			$('#chapterOpLabel').text("編輯章节");
			$('#chapterfieldsetId,fieldset').attr('disabled', 'disabled');
			$('#chapterSubmitButtonId').attr('disabled', 'disabled');
			$.post('classweb/loadchapter.do', {
				'chapterid' : id
			}, function(flag) {
				if (flag.STATE == 0) {
					$('#chapterfieldsetId,fieldset').removeAttr("disabled");
					$('#chapterSubmitButtonId').removeAttr("disabled");
					$('#chapterfieldsetId,#chapterId').val(flag.entity.id);
					$('#chapterfieldsetId,#titleId').val(flag.entity.title);
					$('#chapterfieldsetId,#noteId').val(flag.entity.note);
					$('#chapterfieldsetId,#sortId').val(flag.entity.sort);
				} else {
					alert(flag.MESSAGE);
				}
			}, 'json');
		} else {
			$('#chapterfieldsetId,#chapterId').val('');
			$('#chapterfieldsetId,#titleId').val('');
			$('#chapterfieldsetId,#noteId').val('');
			$('#chapterfieldsetId,#sortId').val('');
			$('#chapterOpLabel').text("创建章节");
		}
	}

	//提交表单
	function submitChapterForm() {
		if (!validate('submitChapterFormId')) {
			$('#chapterErrorsShowboxId').text('信息录入有误，请检查！');
			$('#chapterErrorsShowboxId').show();
			return;
		}
		$('#chapterErrorsShowboxId').text('');
		$('#chapterErrorsShowboxId').hide();
		if (confirm("是否提交本章节？")) {
			$('#chapterSubmitButtonId').attr('disabled', 'disabled');
			$.post('classweb/savechapter.do', {
				'title' : $('#titleId').val(),
				'note' : $('#noteId').val(),
				'classid' : $('#classidId').val(),
				'sort' : $('#sortId').val(),
				'id' : $('#chapterfieldsetId,#chapterId').val()
			}, function(flag) {
				if (flag.STATE == 0) {
					//alert("提交成功");
					$('#myModal').modal('hide')
					location.reload() ;
				} else {
					alert(flag.MESSAGE);
					$('#chapterfieldsetId,fieldset').removeAttr("disabled");
					$('#chapterSubmitButtonId').removeAttr("disabled");
				}
			}, 'json');
		}
	}
</script>