<!DOCTYPE html>
<html>

	<head>
		<title>联系我们 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes}
				<link href="${ctx}/css/about.css?v=${version}" rel="stylesheet" type="text/css">

				<script type="text/javascript">
					$(document).ready(function() {})
				</script>
	</head>

	<body>
		<!--header-->
	    <#include "../header.ftl">
	    <!--end of header-->
	    
	    <div class="col-xs-12 banner text-center">
	    	<div class="container">
	    		<img src="${store_location}/img/about/contactus_map.png">
	    	</div>
	    </div>
	    
		<!--<div class="wbm-contactus-banner">
			<img src="${ctx}/img/about/contact-me.jpg">
		</div>-->

		<div class="container">
			<div class="col-xs-12 col-sm-4">
				<div class="col-xs-12">
					<div class="col-xs-12 text-center contactus_logo"><img src="${store_location}/img/about/contactus_phone.png" alt="" /></div>
					<div class="col-xs-12 text-center contactus_title">服务热线</div>
					<div class="col-xs-12 text-center contactus_content">咨询服务专线：4000-818-530</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-4">
				<div class="col-xs-12">
					<div class="col-xs-12 text-center contactus_logo"><img src="${store_location}/img/about/contactus_market.png" alt="" /></div>
					<div class="col-xs-12 text-center contactus_title">市场合作</div>
					<div class="col-xs-12 text-center contactus_content">市场合作：service@yundaren.com</div>					
				</div>
			</div>
			<div class="col-xs-12 col-sm-4">
				<div class="col-xs-12">
					<div class="col-xs-12 text-center contactus_logo"><img src="${store_location}/img/about/contactus_addres.png" alt="" /></div>
					<div class="col-xs-12 text-center contactus_title">公司地址</div>
					<div class="col-xs-12 text-center contactus_content">广东省深圳市南山区软件产业基地4栋B座2楼</div>	
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