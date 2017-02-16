<!DOCTYPE html>
<html>

	<head>
		<title>关于我们 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes}
				<link href="${ctx}/css/about.css?v=${version}" rel="stylesheet" type="text/css">
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->

			<div class="col-xs-12 aboustUs-body" style="padding:0px;background:url(${store_location}/img/about/aboutusBg.jpg) no-repeat;background-position: center;background-size:cover;">
				<div class="container about-Title text-center">
					<img src="${store_location}/img/aboutTitle.png"/>
				</div>
				<div class="container">
					<div class="col-xs-12 about-content">
						<p>码客帮隶属于深圳市云达人科技有限公司，成立于2015年9月，公司位于深圳，创始团队主要来自华为、Oracle、金蝶、TCL、美团等知名企业，不仅拥有深厚的软件开发、运维和软件项目管理经验，更有丰富的产品设计、架构、和运营经验。团队融合了“传统IT”的严谨和“互联网+”的开放这两种文化，我们专注于以“众包+外包”的模式为客户提供高性价比的技术服务。</p>
						<p>码客帮是一个基于众包的互联网软件技术服务平台，您只需要在平台提交软件需求，码客帮项目顾问将会协助您完成需求梳理、技术选型和评估报价，并为您匹配适合该需求的工程师。为保障需求的高质量交付，码客帮提供了项目管理系统、代码托管系统和在线演示系统，使得整个开发过程是透明和可视的，从而降低开发过程的风险并提高交付质量。同时，码客帮自身拥有突出的项目管理和开发能力，我们可为大型项目提供增值服务，覆盖从需求分析、架构设计到项目人员管理、产品部署维护等端到端全程服务。</p>
						<p>截止目前，码客帮已为近百家互联网初创企业提供了包括网站、微信公众号、APP、HTML5等产品类型的技术服务，其中部分项目已完成下轮融资。码客帮将凭借自身的技术优势和技术社区资源，持续助力客户实现互联网+。</p>
					</div>
				</div>

				<div class="container">
					<div class="col-xs-12 about-blur">
						<div class="col-xs-12 text-center">
							<p>公司旗下的技术品牌</p>
							<div class="div-hr"></div>
						</div>
						<div class="col-xs-12">
							<div class="col-xs-12 col-sm-4 text-center about-blur-div" onclick="window.open('http://www.make8.com')"><img class="img_pc active" src="${store_location}/img/about/make8.png"/><img class="img_m" src="${store_location}/img/about/make8_m.png"/></div>
							<div class="col-xs-12 col-sm-4 text-center about-blur-div" onclick="window.open('http://www.yunweipai.com')"><img class="img_pc active" src="${store_location}/img/about/yunweipai.png"/><img class="img_m" src="${store_location}/img/about/yunweipai_m.png"/></div>
							<div class="col-xs-12 col-sm-4 text-center about-blur-div" onclick="window.open('http://www.chengxuyuan.com')"><img class="img_pc active" src="${store_location}/img/about/chengxuyuan.png"/><img class="img_m" src="${store_location}/img/about/chengxuyuan_m.png"/></div>
						</div>
					</div>
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
