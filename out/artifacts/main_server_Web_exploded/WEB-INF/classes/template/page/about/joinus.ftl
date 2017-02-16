<!DOCTYPE html>
<html>

	<head>
		<title>加入我们 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes}
				<link href="${ctx}/css/about.css?v=${version}" rel="stylesheet" type="text/css">

				<script type="text/javascript">
					$(document).ready(function(){
				    	})
				</script>
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->

			<div class="container">
				<div class="row">
					<div class="col-lg-2 col-md-2 list-group-div">
						<ul class="list-group">
							<a class="list-group-item" href="/about/aboutus">关于我们</a>
							<a class="list-group-item" href="/about/contactus">联系我们</a>
							<a class="list-group-item active" href="#">加入我们</a>
							<a class="list-group-item" href="/about/flink">友情链接</a>
							<a class="list-group-item" href="/about/contract">服务协议</a>
						</ul>
					</div>
					<div class="col-md-9">
						<div class="content-div">加入我们，建设中...</div>
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
				${cnzz_html}
				<!--END OF CNZZ CODE-->

	</body>

</html>