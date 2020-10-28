<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<PF:basePath/>">
    <title>追加回答数据管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <jsp:include page="/view/conf/include.jsp"></jsp:include>
  </head>
  <body class="easyui-layout">
    <div data-options="region:'north',border:false">
      <form id="searchQanswerplusForm">
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
      <table id="dataQanswerplusGrid">
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="CTIME" data-options="sortable:true" width="50">CTIME</th>
            <th field="DESCRIPTION" data-options="sortable:true" width="110">DESCRIPTION</th>
            <th field="ANSWERID" data-options="sortable:true" width="80">ANSWERID</th>
            <th field="ID" data-options="sortable:true" width="20">ID</th>
          </tr>
        </thead>
      </table>
    </div>
  </body>
  <script type="text/javascript">
    var url_delActionQanswerplus = "qanswerplus/del.do";//删除URL
    var url_formActionQanswerplus = "qanswerplus/form.do";//增加、修改、查看URL
    var url_searchActionQanswerplus = "qanswerplus/query.do";//查询URL
    var title_windowQanswerplus = "追加回答管理";//功能名称
    var gridQanswerplus;//数据表格对象
    var searchQanswerplus;//条件查询组件对象
    var toolBarQanswerplus = [ {
      id : 'view',
      text : '查看',
      iconCls : 'icon-tip',
      handler : viewDataQanswerplus
    }, {
      id : 'add',
      text : '新增',
      iconCls : 'icon-add',
      handler : addDataQanswerplus
    }, {
      id : 'edit',
      text : '修改',
      iconCls : 'icon-edit',
      handler : editDataQanswerplus
    }, {
      id : 'del',
      text : '删除',
      iconCls : 'icon-remove',
      handler : delDataQanswerplus
    } ];
    $(function() {
      //初始化数据表格
      gridQanswerplus = $('#dataQanswerplusGrid').datagrid( {
        url : url_searchActionQanswerplus,
        fit : true,
        fitColumns : true,
        'toolbar' : toolBarQanswerplus,
        pagination : true,
        closable : true,
        checkOnSelect : true,
        border:false,
        striped : true,
        rownumbers : true,
        ctrlSelect : true
      });
      //初始化条件查询
      searchQanswerplus = $('#searchQanswerplusForm').searchForm( {
        gridObj : gridQanswerplus
      });
    });
    //查看
    function viewDataQanswerplus() {
      var selectedArray = $(gridQanswerplus).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionQanswerplus + '?pageset.pageType='+PAGETYPE.VIEW+'&ids='
            + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winQanswerplus',
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
    function addDataQanswerplus() {
      var url = url_formActionQanswerplus + '?operateType='+PAGETYPE.ADD;
      $.farm.openWindow( {
        id : 'winQanswerplus',
        width : 600,
        height : 300,
        modal : true,
        url : url,
        title : '新增'
      });
    }
    //修改
    function editDataQanswerplus() {
      var selectedArray = $(gridQanswerplus).datagrid('getSelections');
      if (selectedArray.length == 1) {
        var url = url_formActionQanswerplus + '?operateType='+PAGETYPE.EDIT+ '&ids=' + selectedArray[0].ID;
        $.farm.openWindow( {
          id : 'winQanswerplus',
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
    function delDataQanswerplus() {
      var selectedArray = $(gridQanswerplus).datagrid('getSelections');
      if (selectedArray.length > 0) {
        // 有数据执行操作
        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
        $.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
          if (flag) {
            $(gridQanswerplus).datagrid('loading');
            $.post(url_delActionQanswerplus + '?ids=' + $.farm.getCheckedIds(gridQanswerplus,'ID'), {},
              function(flag) {
                var jsonObject = JSON.parse(flag, null);
                $(gridQanswerplus).datagrid('loaded');
                if (jsonObject.STATE == 0) {
                  $(gridQanswerplus).datagrid('reload');
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