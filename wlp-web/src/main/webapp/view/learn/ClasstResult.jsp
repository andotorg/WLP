<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>课程数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchClasstForm">
        <table class="editTable">
          <tr style="text-align: center;">
            <td colspan="4">
              <a id="a_search" href="javascript:void(0)"
                class="easyui-linkbutton" iconCls="icon-search">查询</a>
              <a id="a_reset" href="javascript:void(0)"
                class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
            </td>
          </tr>
        </table>
      </form>
    </div>
    <div data-options="region:'center',border:false">
      <table id="dataClasstGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="INTRODUCTIONID" data-options="sortable:true" width="140">INTRODUCTIONID</th>
            <th field="NAME" data-options="sortable:true" width="40">NAME</th>
            <th field="DIFFICULTY" data-options="sortable:true" width="100">DIFFICULTY</th>
            <th field="SHORTINTRO" data-options="sortable:true" width="100">SHORTINTRO</th>
            <th field="OUTAUTHOR" data-options="sortable:true" width="90">OUTAUTHOR</th>
            <th field="ALTIME" data-options="sortable:true" width="60">ALTIME</th>
            <th field="EVALUATION" data-options="sortable:true" width="100">EVALUATION</th>
            <th field="LEARNEDNUM" data-options="sortable:true" width="100">LEARNEDNUM</th>
            <th field="IMGID" data-options="sortable:true" width="50">IMGID</th>
            <th field="MINIMGID" data-options="sortable:true" width="80">MINIMGID</th>
            <th field="BOOKEDNUM" data-options="sortable:true" width="90">BOOKEDNUM</th>
            <th field="HOTSCORE" data-options="sortable:true" width="80">HOTSCORE</th>
            <th field="PSTATE" data-options="sortable:true" width="60">PSTATE</th>
            <th field="PCONTENT" data-options="sortable:true" width="80">PCONTENT</th>
            <th field="CUSERNAME" data-options="sortable:true" width="90">CUSERNAME</th>
            <th field="CUSER" data-options="sortable:true" width="50">CUSER</th>
            <th field="CTIME" data-options="sortable:true" width="50">CTIME</th>
            <th field="EUSERNAME" data-options="sortable:true" width="90">EUSERNAME</th>
            <th field="EUSER" data-options="sortable:true" width="50">EUSER</th>
            <th field="ETIME" data-options="sortable:true" width="50">ETIME</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionClasst = "classt/del.do";//删除URL
    var url_formActionClasst = "classt/form.do";//增加、修改、查看URL
    var url_searchActionClasst = "classt/query.do";//查询URL
    var title_windowClasst = "课程管理";//功能名称
    var gridClasst;//数据表格对象
    var searchClasst;//条件查询组件对象
    var toolBarClasst = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataClasst
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataClasst
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataClasst
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataClasst
    } ];
    $(function() {
      //初始化数据表格
      gridClasst = $('#dataClasstGrid').datagrid( {
        url : url_searchActionClasst,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarClasst,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchClasst = $('#searchClasstForm').searchForm( {
        gridObj : gridClasst
      });
    });
    //查看
    function viewDataClasst() {
      var selectedArray = $(gridClasst).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionClasst + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winClasst',
          width : 600,
          height : 300,
          modal : true,
          url : url,
          title : '浏览'
        });
      } else {
        $.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
            'info');
      }
    }
    //新增
    function addDataClasst() {
      var url = url_formActionClasst + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winClasst',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataClasst() {
      var selectedArray = $(gridClasst).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionClasst + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winClasst',
          width : 600,
          height : 300,
          modal : true,
          url : url,
          title : '修改'
        });
      } else {
        $.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
            'info');
      }
    }
    //删除
    function delDataClasst() {
      var selectedArray = $(gridClasst).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridClasst).datagrid('loading');
            $.post(url_delActionClasst + '?ids=' + $.farm.getCheckedIds(gridClasst,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridClasst).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridClasst).datagrid('reload');
              } else {
                var str = MESSAGE_PLAT.ERROR_SUBMIT
                    + jsonObject.MESSAGE;
                $.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
              }
            });
          }
        });
      } else {
        $.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
            'info');
      }
    }
  </script>
</html>