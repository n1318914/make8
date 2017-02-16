<!DOCTYPE html>
<html>

	<head>
		<title>客户案例 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes}
				<link href="${ctx}/css/public/customercases.css?v=${version}" rel="stylesheet" type="text/css">
				<script type="text/javascript" src="/js/public/customercases.js?v=${version}"></script>
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->

			<div class="col-md-12 banner padding" style="background:white url(${store_location}/img/cases/case_banner.png) center no-repeat"></div>

			<div class="container case_list">
				<#list customerCases as customerCase>
				<div class="col-xs-12 col-sm-4 padding" onmouseover="successProjectMouseover(this)" onmouseout="successProjectMouseout(this)">
					<div class="col-xs-12 padding case" style="background:white url(${store_location}/${customerCase.abbrPicUrl!''}) center no-repeat"></div>
					<div class="col-xs-12 padding item">
						<div class="text-left item-name">${customerCase.caseName!""}</div>
						<div class="text-left">周期：<span style="color: #a5a5a5">${customerCase.devsCycle!""}天</span> | 参与角色：<span style="color: #a5a5a5">${customerCase.roles!""}</span></div>
					</div>
				</div>
			  </#list>
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
