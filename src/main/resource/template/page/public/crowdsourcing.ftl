<!DOCTYPE html>
<html>

	<head>
		<title>软件外包 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes}
				<link href="${ctx}/css/public/crowdsourcing.css?v=${version}" rel="stylesheet" type="text/css">
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->

			<div class="col-xs-12 col-padding banner">
				<img class="hidden-xs" src="${store_location}/img/crowdsourcing_banner.png"/>
                <img class="hidden-sm hidden-md hidden-lg" src="${store_location}/img/crowdsourcing_banner_m.png"/>
			</div>

        <div class="container text-center proServices">
			<ul>
				<li class="col-xs-12 col-sm-3">
					<div>
						<div>
							<img src="${store_location}/img/v2/duochongrenzheng.png" />
						</div>
						<p>多重认证</p>
						<p>对服务商进行实名
							<br/>认证、实地验证和能力考察</p>
					</div>
				</li>
				<li class="col-xs-12 col-sm-3">
					<div>
						<div>
							<img src="${store_location}/img/v2/jisupipei.png" />
						</div>
						<p>极速匹配</p>
						<p>根据项目类型快速
							<br />匹配类似案例的认证服务商</p>
					</div>
				</li>
				<li class="col-xs-12 col-sm-3">
					<div>
						<div>
							<img src="${store_location}/img/v2/jishuguwen.png" />
						</div>
						<p>技术顾问</p>
						<p>协助雇主完成项目
							<br />功能点的梳理和技术选型</p>
					</div>
				</li>
				<li class="col-xs-12 col-sm-3">
					<div>
						<div>
							<img src="${store_location}/img/v2/danbaojiaoyi.png" />
						</div>
						<p>担保交易</p>
						<p>平台提供资金托管
							<br />项目交付直至满意后付款</p>
					</div>
				</li>
			</ul>
		</div>

        <div class="container" style="margin-top:30px">
            <div class="col-xs-12 text-center">
                <button class="btn btn-case" onclick="window.location.href='/public/customercases'">客户案例</button>
            </div>
            <div class="col-xs-12 text-center">
                <button class="btn btn-send" onclick="window.location.href='/home/request'">立即发布需求</button>
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
