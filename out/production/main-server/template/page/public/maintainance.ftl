<!DOCTYPE html>
<html>

	<head>
		<title>运维服务 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes}
				<link href="${ctx}/css/public/maintainance.css?v=${version}" rel="stylesheet" type="text/css">
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->

			<div class="col-xs-12 col-padding banner">
				<img class="hidden-xs" src="${store_location}/img/operation_service_banner.png"/>
                <img class="hidden-sm hidden-md hidden-lg" src="${store_location}/img/operation_service_banner_m.png"/>
			</div>	

			<div class="container">
				<div class="col-xs-12 col-sm-4">
					<div class="col-xs-12 col-padding text-center">
						<img src="${store_location}/img/operation_service_banner_01.png"/>
					</div>
					<div class="col-xs-12 col-padding text-center os-title">环境配置和数据迁移</div>
					<div class="col-xs-12 col-padding text-center os-content">各种应用发布生产环境及软件安装服务，支持网站、APP的数据迁移</div>
				</div>
				<div class="col-xs-12 col-sm-4">
					<div class="col-xs-12 col-padding text-center">
						<img src="${store_location}/img/operation_service_banner_02.png"/>
					</div>
					<div class="col-xs-12 col-padding text-center os-title">故障排查和系统加固</div>
					<div class="col-xs-12 col-padding text-center os-content">分析操作系统、应用环境的日志，结合常见故障联合诊断，找到问题并解决</div>
				</div>
				<div class="col-xs-12 col-sm-4">
					<div class="col-xs-12 col-padding text-center">
						<img src="${store_location}/img/operation_service_banner_03.png"/>
					</div>
					<div class="col-xs-12 col-padding text-center os-title">服务器（托管）代维</div>
					<div class="col-xs-12 col-padding text-center os-content">增强系统安全性稳定性，减轻运维成本，7x24小时服务器托管</div>
				</div>

				<div class="col-xs-12 text-center"><botton onclick="window.location.href='/home/request'" class="btn btn-os">立即发布需求</botton></div>
			</div>

			<div class="col-xs-12 col-padding yunweipai">
				<div class="container">
					<div class="col-xs-12 col-sm-3 col-padding text-center yunweipai-title"><img src="${store_location}/img/ywp_logo.png"/></div>
					<div class="col-xs-12 col-sm-9 col-padding-right">运维派成立于2012年，是一个垂直于IT运维领域的技术社区，包含行业资讯网站、社区问答、线下Ops-Day系列技术沙龙、与社区合作过的厂家和社区包括：亚马逊AWS、UCLOUD、又拍云、QingCloud、七牛、云之讯、云智慧、安全狗、英方云、塔布数据、铂涛集团、多备份、互联通、MessageSolution、segmentfault、DBA+等</div>
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
			<!--${cnzz_html}-->
			<!--END OF CNZZ CODE-->
	</body>

</html>
