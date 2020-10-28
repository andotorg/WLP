<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--课程分类表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formClassclasstype">
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
        CLASSTYPEID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_classtypeid" name="classtypeid" value="${entity.classtypeid}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityClassclasstype" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityClassclasstype" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formClassclasstype" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionClassclasstype = 'classclasstype/add.do';
  var submitEditActionClassclasstype = 'classclasstype/edit.do';
  var currentPageTypeClassclasstype = '${pageset.operateType}';
  var submitFormClassclasstype;
  $(function() {
    //表单组件对象
    submitFormClassclasstype = $('#dom_formClassclasstype').SubmitForm( {
      pageType : currentPageTypeClassclasstype,
      grid : gridClassclasstype,
      currentWindowId : 'winClassclasstype'
    });
    //关闭窗口
    $('#dom_cancle_formClassclasstype').bind('click', function() {
      $('#winClassclasstype').window('close');
    });
    //提交新增数据
    $('#dom_add_entityClassclasstype').bind('click', function() {
      submitFormClassclasstype.postSubmit(submitAddActionClassclasstype);
    });
    //提交修改数据
    $('#dom_edit_entityClassclasstype').bind('click', function() {
      submitFormClassclasstype.postSubmit(submitEditActionClassclasstype);
    });
  });
  //-->
</script>