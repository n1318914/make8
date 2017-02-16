<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<title>代码托管 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
		<#include "../comm-includes.ftl">
		${basic_includes}
		<script type="text/javascript" src="/js/project_code.js?v=${version}"></script>

		<!--gogs-->
		<!--<link rel="stylesheet" href="/assets/font-awesome-4.5.0/css/font-awesome.min.css">-->
		<link rel="stylesheet" href="/assets/octicons-3.5.0/octicons.css">
		<link rel="stylesheet" href="/plugins/simplemde-1.10.0/simplemde.min.css">
		<script src="/plugins/simplemde-1.10.0/simplemde.min.js"></script>
		<link rel="stylesheet" href="/css/semantic-2.1.8.min.css">
		<link rel="stylesheet" href="/css/gogs.css?v={{MD5 AppVer}}">
		<script src="/js/semantic-2.1.8.min.js"></script>
		<script src="/js/gogs.js?v={{MD5 AppVer}}"></script>

		<link rel="stylesheet" href="/plugins/highlight-9.2.0/github.css">
		<script src="/plugins/highlight-9.2.0/highlight.pack.js"></script>

		<link rel="stylesheet" href="/plugins/jquery.minicolors-2.2.3/jquery.minicolors.css">
		<script src="/plugins/jquery.minicolors-2.2.3/jquery.minicolors.min.js"></script>

		<link rel="stylesheet" href="/plugins/dropzone-4.2.0/dropzone.css">
		<script src="/plugins/dropzone-4.2.0/dropzone.js"></script>
		<script src="/js/libs/emojify-1.1.0.min.js"></script>
		<script src="/js/libs/clipboard-1.5.9.min.js"></script>

		<style>
			/*.banner{
				background: url(/img/project_code_banner.jpg);
			    background-position: 50% 0;
			    background-repeat: no-repeat;
			    height: 300px;
			}*/
		</style>
	</head>

	<body>
		<#include "../header.ftl">
			<input type='hiden' id='url' value='${url}'/>
			<input type='hidden' id='status' value='${status}'/>
		    <div class="body-offset"></div>
			<!-- <div class='banner'></div> -->
			<div id="content" class='container' style='min-height:90vh'>
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
