<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--课程表单-->
<div class="easyui-layout" data-options="fit:true">
  <div class="TableTitle" data-options="region:'north',border:false">
    <span class="label label-primary"> 
    <c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
    <c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
    </span>
  </div>
  <div data-options="region:'center'">
    <form id="dom_formClasst">
      <input type="hidden" id="entity_id" name="id" value="${entity.id}">
      <table class="editTable">
      <tr>
        <td class="title">
        INTRODUCTIONID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[16]']"
          id="entity_introductionid" name="introductionid" value="${entity.introductionid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        NAME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[64]']"
          id="entity_name" name="name" value="${entity.name}">
        </td>
      </tr>
      <tr>
        <td class="title">
        DIFFICULTY:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[0.5]']"
          id="entity_difficulty" name="difficulty" value="${entity.difficulty}">
        </td>
      </tr>
      <tr>
        <td class="title">
        SHORTINTRO:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[256]']"
          id="entity_shortintro" name="shortintro" value="${entity.shortintro}">
        </td>
      </tr>
      <tr>
        <td class="title">
        OUTAUTHOR:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[32]']"
          id="entity_outauthor" name="outauthor" value="${entity.outauthor}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ALTIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_altime" name="altime" value="${entity.altime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        EVALUATION:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_evaluation" name="evaluation" value="${entity.evaluation}">
        </td>
      </tr>
      <tr>
        <td class="title">
        LEARNEDNUM:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_learnednum" name="learnednum" value="${entity.learnednum}">
        </td>
      </tr>
      <tr>
        <td class="title">
        IMGID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_imgid" name="imgid" value="${entity.imgid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        MINIMGID:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[16]']"
          id="entity_minimgid" name="minimgid" value="${entity.minimgid}">
        </td>
      </tr>
      <tr>
        <td class="title">
        BOOKEDNUM:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_bookednum" name="bookednum" value="${entity.bookednum}">
        </td>
      </tr>
      <tr>
        <td class="title">
        HOTSCORE:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[5]']"
          id="entity_hotscore" name="hotscore" value="${entity.hotscore}">
        </td>
      </tr>
      <tr>
        <td class="title">
        PSTATE:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[1]']"
          id="entity_pstate" name="pstate" value="${entity.pstate}">
        </td>
      </tr>
      <tr>
        <td class="title">
        PCONTENT:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[64]']"
          id="entity_pcontent" name="pcontent" value="${entity.pcontent}">
        </td>
      </tr>
      <tr>
        <td class="title">
        CUSERNAME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[32]']"
          id="entity_cusername" name="cusername" value="${entity.cusername}">
        </td>
      </tr>
      <tr>
        <td class="title">
        CUSER:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_cuser" name="cuser" value="${entity.cuser}">
        </td>
      </tr>
      <tr>
        <td class="title">
        CTIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[8]']"
          id="entity_ctime" name="ctime" value="${entity.ctime}">
        </td>
      </tr>
      <tr>
        <td class="title">
        EUSERNAME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[32]']"
          id="entity_eusername" name="eusername" value="${entity.eusername}">
        </td>
      </tr>
      <tr>
        <td class="title">
        EUSER:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[16]']"
          id="entity_euser" name="euser" value="${entity.euser}">
        </td>
      </tr>
      <tr>
        <td class="title">
        ETIME:
        </td>
        <td colspan="3">
          <input type="text" style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[8]']"
          id="entity_etime" name="etime" value="${entity.etime}">
        </td>
      </tr>
    </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="div_button" style="text-align: center; padding: 4px;">
      <c:if test="${pageset.operateType==1}">
      <a id="dom_add_entityClasst" href="javascript:void(0)"  iconCls="icon-save" class="easyui-linkbutton">增加</a>
      </c:if>
      <c:if test="${pageset.operateType==2}">
      <a id="dom_edit_entityClasst" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
      </c:if>
      <a id="dom_cancle_formClasst" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton"   style="color: #000000;">取消</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  var submitAddActionClasst = 'classt/add.do';
  var submitEditActionClasst = 'classt/edit.do';
  var currentPageTypeClasst = '${pageset.operateType}';
  var submitFormClasst;
  $(function() {
    //表单组件对象
    submitFormClasst = $('#dom_formClasst').SubmitForm( {
      pageType : currentPageTypeClasst,
      grid : gridClasst,
      currentWindowId : 'winClasst'
    });
    //关闭窗口
    $('#dom_cancle_formClasst').bind('click', function() {
      $('#winClasst').window('close');
    });
    //提交新增数据
    $('#dom_add_entityClasst').bind('click', function() {
      submitFormClasst.postSubmit(submitAddActionClasst);
    });
    //提交修改数据
    $('#dom_edit_entityClasst').bind('click', function() {
      submitFormClasst.postSubmit(submitEditActionClasst);
    });
  });
  //-->
</script>