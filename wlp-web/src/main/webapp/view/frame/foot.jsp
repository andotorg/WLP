<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div style="text-align: right; color: #000000;">
	<div
		style="font-size: 12px; color: gray; text-align: center; padding-top: 4px;">
		V
		<PF:ParameterValue key="config.sys.version" />
		&nbsp;/&nbsp;STATE:${licenceAble}&nbsp;/&nbsp;TYPE:${licenceType}&nbsp;/&nbsp;KEY:${licenceKey}&nbsp;/&nbsp;INFO:${licenceInfo}&nbsp;/&nbsp;TO:${licenceAuth}
		<div style="float: right;">
			<a href="demo/PubHome.do" style="color: gray;">T</a>&nbsp;
		</div>
	</div>
</div>