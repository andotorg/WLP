<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="container-fluid" style="background-color: #f8fafc;">
	<div class="container"> 
		<div class="row">
			<div class="col-md-12">
				<div
					style="height: 100px; padding: 50px; text-align: center; color: #99a1a6;">
					<PF:ParameterValue key="config.sys.foot" />
					- v
					<PF:ParameterValue key="config.sys.version" />
				</div>
			</div>
		</div>
	</div>
</div>