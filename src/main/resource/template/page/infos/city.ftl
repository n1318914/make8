<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台 - ${provinceNode.name} ${cityNode.name}</title>
		<meta name="keywords" content="码客帮, 软件外包, 软件众包, 代码众包, 开发者众包, 程序员兼职, 项目外包,互联网软件外包,网站外包,安卓APP外包,Andriod APP,iOS APP外包,微信开发外包,${provinceNode.name},${cityNode.name}">
		<meta name="description" content="码客帮是一个基于众包的互联网软件技术服务平台，建立项目需求方与技术大牛的连接。帮助需求方快速找到靠谱的软件工程师，同时也为程序员提供积累项目经验和收获财富的渠道。">
	<#include "../common.ftl">
	<link href="${ctx}/css/bootstrap.min.css?v=201502191400" rel="stylesheet">
    <link href="${ctx}/css/header.css?v=201502191400" rel="stylesheet">
    <link href="${ctx}/css/footer.css?v=201502191400" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css?v=201502191400" rel="stylesheet">
<script type="text/javascript" src="${ctx}/js/jquery.min.js?v=201502191400"></script>
    <script type="text/javascript" src="${ctx}/js/header.js?v=201504142230"></script>

    
    		<#include "../common.ftl">
		<link rel="stylesheet" href="${ctx}/css/infos/city.css"/>
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
				<div class="BreadcrumbNavigation">
					<a href="/infos/index">首页</a>
					<i class="fa fa-angle-right"></i>
					<a href="/infos/${provinceNode.urlId!""}">${provinceNode.name}</a>
					<i class="fa fa-angle-right"></i>
					<a>${cityNode.name}</a>
				</div>
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
						<#list districtNodes as districtNode>
						 <li>
							 <a href="/infos/${provinceNode.urlId}/${cityNode.urlId}/${districtNode.urlId}">
								 ${districtNode.name}
               </a>
						 </li>
						</#list>
					</ul>
				</div>
			<div class="body_left">
				<div class="appointment" style="display: block;">
					<div class="appointmentDiv">
						<div class="appointment-title">预约顾问</div>
						<form action="javascript:doServiceReservation()">
							<div class="form-div">
								<div><label for="userName">姓名：</label>&nbsp;</div>
								<div><input id="name" class="form-control" type="text" maxlength="20" onkeyup="checkInput()"/></div>
								<div class="warningMsg" id="nameMsg">姓名不能为空</div>
							</div>
							<div class="form-div">
								<div><label for="tel">联系方式：</label>&nbsp;</div>
								<div><input class="form-control" id="TelPhone" type="text" maxlength="11" onkeyup="checkInput()"/></div>
								<div class="warningMsg" id="TelPhoneMsg">联系方式不能为空</div>
							</div>
							<div class="form-div" style="height:88px">
								<div><label for="details">需求描述：</label>&nbsp;</div>
								<div><textarea class="form-control" id="demand" onkeyup="checkInput()" maxlength="500"></textarea></div>
								<div class="warningMsg" id="demandMsg">需求不能为空</div>
							</div>
							<div class="form-btn"><button class="btn btn-primary">提交</button></div>
							<div class="warningMsg" style="margin-left: 275px;" id="successMsg">预约成功，我们的客服会尽快和您取得联系</div>
						</form>
					</div>
				</div>

				<div class="content">
					<#list cityNode.items as item>
						<div class="article">
							<div class="article_title">${item.title!""}</div>
							<div class="article_content">${item.content!""}</div>
						</div>
					</#list>
				</div>
			</div>

			<div class="body_right">
				<ul>
					<#list siblingNodes as siblingNode>
						<li>
							<div class="div_radius"></div>
							<a href="/infos/${provinceNode.urlId!""}/${siblingNode.urlId!""}">
								${siblingNode.name}
							</a>
						</li>
					</#list>
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
