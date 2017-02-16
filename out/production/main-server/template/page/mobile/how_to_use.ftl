<!DOCTYPE html>
<html>
	<head>
		<#include "../common.ftl">
		<#include "../comm-includes.ftl">
		<title>码客帮 - 互联网软件外包交易平台</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="keywords" content="软件外包,项目外包,互联网软件外包,网站外包,安卓APP外包,Andriod APP,iOS APP外包,微信开发外包">
		<meta name="description" content="码客帮(www.make8.com)是国内第一家互联网软件外包交易平台，专注于帮助互联网早期创业团队找到靠谱的软件外包服务商。">
   		${mobile_includes}
	</head>
	
	<body>
		<header data-am-widget="header" class="am-header am-header-default">
			<div class="am-header-left am-header-nav">
				<a href="/mobile" class="">
					<i class="am-header-icon am-icon-home"></i>
				</a>
			</div>

			<div class="am-header-title">服务流程</div>
		</header>

		<nav data-am-widget="menu" class="am-menu  am-menu-offcanvas1" data-am-menu-offcanvas>
			<a href="javascript: void(0)" class="am-menu-toggle">
				<i class="am-menu-toggle-icon am-icon-bars"></i>
			</a>
			<div class="am-offcanvas">
				<div class="am-offcanvas-bar am-offcanvas-bar-flip">

					<ul class="am-menu-nav sm-block-grid-1">
						<li class="">
							<a href="/mobile" class="main-title">首页</a>
						</li>

						<li class="">
							<a href="/mobile/request">发布需求</a>
						</li>

						<li class="">
							<a href="/mobile/reserve">预约顾问</a>
						</li>

						<li class="">
							<a href="/mobile/market">项目市场</a>
						</li>

						<li class="">
							<a href="#">如何使用</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>

		<div class="service1">
			<img src="${store_location}/img/mobile/banner1.png" class="am-img-responsive" alt="" />
		</div>
		<div class="wbm-service">
			<div class="wbm-services">
				<div class="service2">
					<img src="${store_location}/img/mobile/liucheng.png" class="am-img-responsive" alt="" />
				</div>
			</div>
		</div>
		
		<!--CNZZ CODE-->
		${cnzz_html}
		<!--END OF CNZZ CODE-->
	</body>

</html>