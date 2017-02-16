<!DOCTYPE html>
<html ng-app="flinkApp" ng-controller="flinkController">

	<head lang="zh-CN">
		<title>服务流程 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
			${basic_includes} ${tools_includes}
			<link href="${ctx}/css/about/serviceflow.css?v=${version}" rel="stylesheet" type="text/css">
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->

			<div class="col-xs-12 col-sm col-md-12 banner"><!--${store_location}-->
				<div class="container">
					<img src="${ctx}/img/about/serviceFlowBanner.png"/>
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-12 step1">
				<div class="container">
					<img src="${ctx}/img/about/serviceFlow_step1.png"/>
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-12 step2">
				<div class="container active">
					<img src="${ctx}/img/about/serviceFlow_step2.png"/>
				</div>
				<div class="container active_m" onclick="window.open('${ctx}/img/about/serviceFlow_step2.png')">
					<img src="${ctx}/img/about/serviceFlow_step2.png"/>
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-12 step3">
				<div class="container">
					<img src="${ctx}/img/about/serviceFlow_step3.png"/>
				</div>
			</div>

			<div>
				<input type="hidden" id="topurl" value="${ctx}/" />
				<input type="hidden" id="reload" value="0" />
			</div>

			<!--start of footer-->
			<#include "../footer.ftl">
				<!--end of footer-->
				<!---start of help docker-->
				<div id="top"></div>
				<!--end of help docker-->

				<!--CNZZ CODE-->
				${cnzz_html}
				<!--END OF CNZZ CODE-->
				<script type="text/javascript" src="${ctx}/js/about/flink.js?v=${version}"></script>

	</body>

</html>