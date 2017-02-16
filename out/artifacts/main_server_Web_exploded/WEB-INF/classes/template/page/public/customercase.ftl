<!DOCTYPE html>
<html>

	<head lang="zh-CN">
		<title>客户案例 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
		<#include "../comm-includes.ftl">
		${basic_includes} ${tools_includes}
		<link rel="stylesheet" type="text/css" href="${ctx}/css/public/customercase.css?v=${version}"/>
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
		<!--end of header-->

		<div class="container">
			<div class="col-xs-12 projectDetails">
				<div class="col-xs-12 col-sm-12 col-md-8 padding text-center homePageView">
					<img class="col-xs-12" src="${store_location}/${customerCase.mainPageUrl!""}" alt="" />
				</div>
				<div class="col-xs-12 col-sm-12 col-md-4 padding-right">
					<div class="col-xs-12 padding text-center company_logo">
						<img src="${store_location}/${customerCase.logoUrl!""}" alt="" />
					</div>
					<div class="col-xs-12 padding projectContent">
						<div class="col-xs-12 padding label_style">项目周期 <b>/</b> <span id="projectCycle">${customerCase.devsCycle!""}天</span></div>
						<div class="col-xs-12 padding label_style">参与角色 <b>/</b> <span id="projectRole">${customerCase.roles!""}</span></div>
						<div class="col-xs-12 padding"><a id="project_link" href="${customerCase.siteURL!""}">${customerCase.siteURL!""}</a></div>
					</div>
					<div class="col-xs-12 padding projectName">${customerCase.caseName!""}</div>
					<div class="col-xs-12 padding companyIntroduce">${customerCase.caseIntroduction!""}</div>
					<div class="col-xs-12 padding companyIntroduce">${customerCase.caseDesc!""}</div>
					<div class="col-xs-12 padding just_do_it">心动了吗？现在开始吧！</div>
					<div class="col-xs-12 padding">
						<botton class="btn btn-primary col-xs-12" onclick="window.location.href='/home/request'">发布需求</botton>
						<botton class="btn-custom col-xs-12" onclick="window.location.href='/home/userinfo'">认证码客</botton>
					</div>
				</div>
			</div>
		</div>


		<input type="hidden" id="topurl" value="${ctx}/" />

		<!--start of footer-->
		<#include "../footer.ftl">
		<!--end of footer-->

		<!---start of help docker-->
		<div id="top"></div>
		<!--end of help docker-->

<!--CNZZ CODE-->
${cnzz_html}
<!--END OF CNZZ CODE-->


	</body>

</html>
