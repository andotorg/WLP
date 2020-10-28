<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--课程标签表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formClasstag">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        CLASSID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_classid" name="classid" value="${entity.classid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        TAGID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_tagid" name="tagid" value="${entity.tagid}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityClasstag" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityClasstag" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formClasstag" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionClasstag = 'classtag/add.do';
  var submitEditActionClasstag = 'classtag/edit.do';
  var currentPageTypeClasstag = '${pageset.operateType}';
  var submitFormClasstag;
  $(function() {
    //表单组件对象
    submitFormClasstag = $('#dom_formClasstag').SubmitForm( {
      pageType : currentPageTypeClasstag,
      grid : gridClasstag,
      currentWindowId : 'winClasstag'
    });
    //关闭窗口
    $('#dom_cancle_formClasstag').bind('click', function() {
      $('#winClasstag').window('close');
    });
    //提交新增数据
    $('#dom_add_entityClasstag').bind('click', function() {
      submitFormClasstag.postSubmit(submitAddActionClasstag);
    });
    //提交修改数据
    $('#dom_edit_entityClasstag').bind('click', function() {
      submitFormClasstag.postSubmit(submitEditActionClasstag);
    });
  });
  //-->
</script>