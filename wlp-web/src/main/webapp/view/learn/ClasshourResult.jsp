<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>课程课时数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchClasshourForm">
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
      <table id="dataClasshourGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="NOTE" data-options="sortable:true" width="40">NOTE</th>
            <th field="CHAPTERID" data-options="sortable:true" width="90">CHAPTERID</th>
            <th field="TITLE" data-options="sortable:true" width="50">TITLE</th>
            <th field="SORT" data-options="sortable:true" width="40">SORT</th>
            <th field="FILEID" data-options="sortable:true" width="60">FILEID</th>
            <th field="AUTHORID" data-options="sortable:true" width="80">AUTHORID</th>
            <th field="PCONTENT" data-options="sortable:true" width="80">PCONTENT</th>
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
    var url_delActionClasshour = "classhour/del.do";//删除URL
    var url_formActionClasshour = "classhour/form.do";//增加、修改、查看URL
    var url_searchActionClasshour = "classhour/query.do";//查询URL
    var title_windowClasshour = "课程课时管理";//功能名称
    var gridClasshour;//数据表格对象
    var searchClasshour;//条件查询组件对象
    var toolBarClasshour = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataClasshour
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataClasshour
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataClasshour
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataClasshour
    } ];
    $(function() {
      //初始化数据表格
      gridClasshour = $('#dataClasshourGrid').datagrid( {
        url : url_searchActionClasshour,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarClasshour,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchClasshour = $('#searchClasshourForm').searchForm( {
        gridObj : gridClasshour
      });
    });
    //查看
    function viewDataClasshour() {
      var selectedArray = $(gridClasshour).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionClasshour + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winClasshour',
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
    function addDataClasshour() {
      var url = url_formActionClasshour + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winClasshour',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataClasshour() {
      var selectedArray = $(gridClasshour).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionClasshour + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winClasshour',
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
    function delDataClasshour() {
      var selectedArray = $(gridClasshour).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridClasshour).datagrid('loading');
            $.post(url_delActionClasshour + '?ids=' + $.farm.getCheckedIds(gridClasshour,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridClasshour).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridClasshour).datagrid('reload');
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