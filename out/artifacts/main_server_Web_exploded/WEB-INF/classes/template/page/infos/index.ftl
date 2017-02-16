<!DOCTYPE html>
<html lang="zh-CN" ng-app='userReviewApp' ng-controller='userReviewCtrl'>
	<head>
		<title>码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<meta charset="UTF-8">
		<meta name="keywords" content="码客帮, 软件外包, 软件众包, 代码众包, 开发者众包, 程序员兼职, 项目外包,互联网软件外包,网站外包,安卓APP外包,Andriod APP,iOS APP外包,微信开发外包">
		<meta name="description" content="码客帮是一个基于众包的互联网软件技术服务平台，建立项目需求方与技术大牛的连接。帮助需求方快速找到靠谱的软件工程师，同时也为程序员提供积累项目经验和收获财富的渠道。">
		<#include "../common.ftl">
	<link href="${ctx}/css/bootstrap.min.css?v=201502191400" rel="stylesheet">
    <link href="${ctx}/css/header.css?v=201502191400" rel="stylesheet">
    <link href="${ctx}/css/footer.css?v=201502191400" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css?v=201502191400" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js?v=201502191400"></script>
    <script type="text/javascript" src="${ctx}/js/header.js?v=201504142230"></script>
		<link rel="stylesheet" href="${ctx}/css/infos/index.css" />
		<script type="text/javascript" src="${ctx}/js/infos/infos.js"></script>
	</head>
	<body>
		<!--head-->
    <#include "../header.ftl">
		<!--head end-->

		<!--banner-->
		<div class="banner">
			<div class="banner_img"></div>
		</div>
		<!--banner end-->

		<div class="body">
			<!--BreadcrumbNavigation-->
			<!-- <div class="BreadcrumbNavigation"><a>首页</a>>></div> -->
			<!--BreadcrumbNavigation end-->

			<!--<div class="productType">
				<ul>
					<li>产品类型：</li>
					<li>APP</li>
					<li>微信</li>
					<li>网站</li>
					<li>UI</li>
					<li>其他</li>
				</ul>
			</div>-->

			<div class="region">
				<div>地区：</div>
				<ul>
					<#list provinceNodes as provinceNode>
						  <li><a href="/infos/${provinceNode.urlId}">${provinceNode.name}</a></li>
						</#list>
				</ul>
			</div>

			<div class="item">
				<ul>
					<li>
						<div class="item-logo">
							<img src="${store_location}/img/infos/andriod_logo.png" alt="" />
						</div>
						<div class="item-info">Android是一种基于Linux的自由及开放源代码的操作系统，主要使用于移动设备，如智能手机和平板电脑，由Google公司和开放手机联盟领导及开发。尚未有统一中文名称，中国大陆地区较多人使用“安卓”或“安致”。Android操作系统最初由Andy Rubin开发，主要支持手机。2005年8月由Google收购注资。2007年11月，Google与84家硬件制造商、软件开发商及电信营运商组建开放手机联盟共同研发改良Android系统。随后Google以Apache开源许可证的授权方式，发布了Android的源代码。</div>
					</li>
					<li>
						<div class="item-logo">
							<img src="${store_location}/img/infos/iOS_logo.png" alt="" />
						</div>
						<div class="item-info">iOS是由苹果公司开发的移动操作系统 。苹果公司最早于2007年1月9日的Macworld大会上公布这个系统，最初是设计给iPhone使用的，后来陆续套用到iPod touch、iPad以及Apple TV等产品上。iOS与苹果的Mac OS X操作系统一样，属于类Unix的商业操作系统。原本这个系统名为iPhone OS，因为iPad，iPhone，iPod touch都使用iPhone OS，所以2010WWDC大会上宣布改名为iOS（iOS为美国Cisco公司网络设备操作系统注册商标，苹果改名已获得Cisco公司授权）。</div>
					</li>
				</ul>
			</div>
		</div>

		<!--start of footer-->
		<#include "../footer.ftl">
		<!--end of footer-->
		<!---start of help docker-->
		<div id="top"></div>
		<!--end of help docker-->

		<div>
			<input type="hidden" id="topurl" value="${ctx}/" />
			<input type="hidden" id="reload" value="0" />
		</div>

		<!--CNZZ CODE-->
		${cnzz_html}
		<!--END OF CNZZ CODE-->
	</body>
</html>
