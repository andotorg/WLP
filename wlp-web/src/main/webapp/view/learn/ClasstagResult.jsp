<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>课程标签数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchClasstagForm">
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
      <table id="dataClasstagGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="CLASSID" data-options="sortable:true" width="70">CLASSID</th>
            <th field="TAGID" data-options="sortable:true" width="50">TAGID</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionClasstag = "classtag/del.do";//删除URL
    var url_formActionClasstag = "classtag/form.do";//增加、修改、查看URL
    var url_searchActionClasstag = "classtag/query.do";//查询URL
    var title_windowClasstag = "课程标签管理";//功能名称
    var gridClasstag;//数据表格对象
    var searchClasstag;//条件查询组件对象
    var toolBarClasstag = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataClasstag
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataClasstag
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataClasstag
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataClasstag
    } ];
    $(function() {
      //初始化数据表格
      gridClasstag = $('#dataClasstagGrid').datagrid( {
        url : url_searchActionClasstag,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarClasstag,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchClasstag = $('#searchClasstagForm').searchForm( {
        gridObj : gridClasstag
      });
    });
    //查看
    function viewDataClasstag() {
      var selectedArray = $(gridClasstag).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionClasstag + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winClasstag',
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
    function addDataClasstag() {
      var url = url_formActionClasstag + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winClasstag',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataClasstag() {
      var selectedArray = $(gridClasstag).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionClasstag + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winClasstag',
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
    function delDataClasstag() {
      var selectedArray = $(gridClasstag).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridClasstag).datagrid('loading');
            $.post(url_delActionClasstag + '?ids=' + $.farm.getCheckedIds(gridClasstag,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridClasstag).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridClasstag).datagrid('reload');
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