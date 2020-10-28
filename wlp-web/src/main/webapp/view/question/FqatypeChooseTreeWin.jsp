<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div style="margin: 4px;">
	<div style="text-align: center;">
		<a id="doctypeChooseTreeOpenAll" href="javascript:void(0)"
			class="easyui-linkbutton" data-options="plain:true"
			iconCls="icon-sitemap">全部展开</a>
	</div>
	<hr />
	<ul id="doctypeTreeNodeDomTree"></ul>
</div>
<script type="text/javascript">
	$(function() {
		$('#doctypeChooseTreeOpenAll').bind('click', function() {
			$('#doctypeTreeNodeDomTree').tree('expandAll');
		});
		
		$('#doctypeTreeNodeDomTree').tree( {
			url : 'doctype/FarmDoctypeLoadTreeNode.do',
			onSelect : function(node){
				$.messager.confirm(MESSAGE_PLAT.PROMPT, "确定移动到【"+node.text+"】分类？", function(flag) {
					if (flag) {
						chooseTypeHook(node);
					}
				});
			},
			loadFilter:function(data,parent){
				return data.treeNodes;
			}
		});
	});
	//-->
</script>