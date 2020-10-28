<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>回答评论数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchQanswercommentForm">
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
      <table id="dataQanswercommentGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="DESCRIPTION" data-options="sortable:true" width="110">DESCRIPTION</th>
            <th field="ANSWERID" data-options="sortable:true" width="80">ANSWERID</th>
            <th field="PSTATE" data-options="sortable:true" width="60">PSTATE</th>
            <th field="CUSER" data-options="sortable:true" width="50">CUSER</th>
            <th field="CUSERNAME" data-options="sortable:true" width="90">CUSERNAME</th>
            <th field="CTIME" data-options="sortable:true" width="50">CTIME</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionQanswercomment = "qanswercomment/del.do";//删除URL
    var url_formActionQanswercomment = "qanswercomment/form.do";//增加、修改、查看URL
    var url_searchActionQanswercomment = "qanswercomment/query.do";//查询URL
    var title_windowQanswercomment = "回答评论管理";//功能名称
    var gridQanswercomment;//数据表格对象
    var searchQanswercomment;//条件查询组件对象
    var toolBarQanswercomment = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataQanswercomment
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataQanswercomment
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataQanswercomment
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataQanswercomment
    } ];
    $(function() {
      //初始化数据表格
      gridQanswercomment = $('#dataQanswercommentGrid').datagrid( {
        url : url_searchActionQanswercomment,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarQanswercomment,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchQanswercomment = $('#searchQanswercommentForm').searchForm( {
        gridObj : gridQanswercomment
      });
    });
    //查看
    function viewDataQanswercomment() {
      var selectedArray = $(gridQanswercomment).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionQanswercomment + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winQanswercomment',
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
    function addDataQanswercomment() {
      var url = url_formActionQanswercomment + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winQanswercomment',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataQanswercomment() {
      var selectedArray = $(gridQanswercomment).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionQanswercomment + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winQanswercomment',
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
    function delDataQanswercomment() {
      var selectedArray = $(gridQanswercomment).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridQanswercomment).datagrid('loading');
            $.post(url_delActionQanswercomment + '?ids=' + $.farm.getCheckedIds(gridQanswercomment,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridQanswercomment).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridQanswercomment).datagrid('reload');
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