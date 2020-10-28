<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container" style="padding: 4px;">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header" style="padding-left: 40px;">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<PF:defaultIndexPage/>"
				style="padding: 5px;"> <img class="img-rounded"
				src="<PF:basePath/>webfile/Publogo.do" height="40" alt="WLP"
				align="middle" /></a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="<PF:defaultIndexPage/>">首页<span
						class="sr-only">(current)</span></a></li>
				<li><a href="category/Pubview.do">全部课程<span class="sr-only">(current)</span></a></li>
				<!-- <li><a href="<PF:defaultIndexPage/>">学习专辑<span
						class="sr-only">(current)</span></a></li> -->
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<!-- /input-group 	<li><form class="navbar-form ">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="请输入关键字...">
							<span class="input-group-btn">
								<button class="btn btn-default" type="button">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</span>
						</div>
						
					</form></li>-->
				<c:if test="${USEROBJ==null}">
					<li><a href="login/PubLogin.do"><i
							class="glyphicon glyphicon-log-in"></i>&nbsp;用户登陆</a></li>
					<!-- 	<li><a>/</a></li>
					<li><a href="#"><i
							class="glyphicon glyphicon-registration-mark"></i>&nbsp;注冊</a></li> -->
				</c:if>
				<c:if test="${USEROBJ!=null}">
					<li class="dropdown"><a  class="dropdown-toggle" style="min-width: 160px;"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"> <img alt=""
							src="download/Pubphone.do?id=${USEROBJ.imgid}"
							style="width: 24px; height: 24px; position: relative; top: -1px;"
							class="img-circle">&nbsp;&nbsp;&nbsp;&nbsp;${USEROBJ.name } <span
							class="caret"></span></a>
						<ul class="dropdown-menu">
							<!-- <li><a href="#">我的空间</a></li> -->
							<c:if test="${USEROBJ.type=='3' }">
								<li><a href="frame/index.do"><i
										class="glyphicon glyphicon-wrench"></i>&nbsp;管理控制台</a></li>
							</c:if>
							<li><a href="userspace/settinginfo.do"><i
									class="glyphicon glyphicon-cog"></i>&nbsp;个人设置</a></li>
							<c:if test="${USEROBJ.type=='3' }">
								<li><a href="userspace/tempclass.do"><i
										class=" glyphicon glyphicon-book"></i>&nbsp;课程管理</a></li>
							</c:if>
							<li role="separator" class="divider"></li>
							<li><a href="login/webout.do"><i
									class="glyphicon glyphicon-log-out"></i>&nbsp;注销</a></li>
						</ul></li>
				</c:if>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>
