<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>课程章节数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchClasschapterForm">
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
      <table id="dataClasschapterGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="CLASSID" data-options="sortable:true" width="70">CLASSID</th>
            <th field="NOTE" data-options="sortable:true" width="40">NOTE</th>
            <th field="TITLE" data-options="sortable:true" width="50">TITLE</th>
            <th field="SORT" data-options="sortable:true" width="40">SORT</th>
            <th field="PSTATE" data-options="sortable:true" width="60">PSTATE</th>
            <th field="EUSER" data-options="sortable:true" width="50">EUSER</th>
            <th field="EUSERNAME" data-options="sortable:true" width="90">EUSERNAME</th>
            <th field="CUSER" data-options="sortable:true" width="50">CUSER</th>
            <th field="CUSERNAME" data-options="sortable:true" width="90">CUSERNAME</th>
            <th field="ETIME" data-options="sortable:true" width="50">ETIME</th>
            <th field="CTIME" data-options="sortable:true" width="50">CTIME</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionClasschapter = "classchapter/del.do";//删除URL
    var url_formActionClasschapter = "classchapter/form.do";//增加、修改、查看URL
    var url_searchActionClasschapter = "classchapter/query.do";//查询URL
    var title_windowClasschapter = "课程章节管理";//功能名称
    var gridClasschapter;//数据表格对象
    var searchClasschapter;//条件查询组件对象
    var toolBarClasschapter = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataClasschapter
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataClasschapter
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataClasschapter
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataClasschapter
    } ];
    $(function() {
      //初始化数据表格
      gridClasschapter = $('#dataClasschapterGrid').datagrid( {
        url : url_searchActionClasschapter,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarClasschapter,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchClasschapter = $('#searchClasschapterForm').searchForm( {
        gridObj : gridClasschapter
      });
    });
    //查看
    function viewDataClasschapter() {
      var selectedArray = $(gridClasschapter).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionClasschapter + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winClasschapter',
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
    function addDataClasschapter() {
      var url = url_formActionClasschapter + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winClasschapter',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataClasschapter() {
      var selectedArray = $(gridClasschapter).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionClasschapter + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winClasschapter',
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
    function delDataClasschapter() {
      var selectedArray = $(gridClasschapter).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridClasschapter).datagrid('loading');
            $.post(url_delActionClasschapter + '?ids=' + $.farm.getCheckedIds(gridClasschapter,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridClasschapter).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridClasschapter).datagrid('reload');
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