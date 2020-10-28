<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html>
<head>
<title><PF:ParameterValue key="config.sys.title" /></title>
<jsp:include page="/view/conf/include.jsp"></jsp:include>
<script type="text/javascript" src="text/javascript/echarts.min.js"></script>
</head>
<body class="easyui-layout">
	<div
		style="text-align: right; color: #000000; background: #fff; padding: 20px; height: 100%;">
		<div style="text-align: center; padding: 50px;">
			<img src="<PF:basePath/>webfile/PubHomelogo.do"
				style="max-width: 128px;">
		</div>
		<div style="text-align: center;">
			<table style="width: 100%;">
				<tr>
					<td width="100%"><div id="usergraph"
							style="width: 100%; height: 400px;"></div></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script>
	// 第二个参数可以指定前面引入的主题
	var chart3 = echarts.init(document.getElementById('usergraph'));
	//'${STAT.TYPESNUM}', '${STAT.KNOWNUM}', '${STAT.USERNUM}','${STAT.GOODKNOWNUM}', '${STAT.BADKNOWNUM}''${STAT.ALLQUESTION}','${STAT.FINISHQUESTION}','${STAT.WAITINGQUESTION}'
	var option3 = {
		series : [ {
			type : 'gauge',
			startAngle : 180,
			endAngle : 0,
			radius : '90%',
			min : 0,
			max : 1000,
			detail : {
				formatter : '{value}人'
			},
			data : [ {
				value : '${STAT.USERNUM}',
				name : '人员数'
			} ]
		} ]
	};
	chart3.setOption(option3);
</script>
</html>