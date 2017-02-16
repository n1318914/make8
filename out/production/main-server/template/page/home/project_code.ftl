<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<title>代码托管 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
		<#include "../comm-includes.ftl">
		${basic_includes}
		<script type="text/javascript" src="/js/project_code.js?v=${version}"></script>

		<style>
			/*.banner{
				background: url(/img/project_code_banner.jpg);
			    background-position: 50% 0;
			    background-repeat: no-repeat;
			    height: 300px;
			    margin-top:-22px;
			}*/
		</style>
	</head>

	<body>
		<#include "../header.ftl">
			<input type='hiden' id='url' value='${url}'/>
			<input type='hidden' id='status' value='${status}'/>
		    <div class="body-offset"></div>
			<!-- <div class='banner'></div> -->
			<div id="content" class='container' style='margin-top:-30px;min-height:90vh'>
				${page}
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
