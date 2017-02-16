<!DOCTYPE html>
<html>
<html lang="zh-CN">
<head lang="en">
	  <head>
    <title>如何支付 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    ${tools_includes}
    <link href="/css/pay.css?v=${version}" rel="stylesheet" type="text/css"/>
</head>
<body>
	    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->
    <div class="body-offset"></div>
    <!--banner pay-->
     <div class='banner'>
     <div class="container">
     	<div class="banner-left">
     		<img src="../img/banner_pay_word.png"/>
     	</div>
     	<div class="banner-right">
     		<img src="../img/banner_pay_img.png"/>
     	</div>
     </div>
	</div>
	<!--start of body  -->
	<div class="container">
		<div class="col-md-6 pay-card cmd-pay-card">
			<div class="head-title">
				<img class="head-title-img" src="../img/title.jpg" />
				付款账户一
			</div>
			<div class="body-card">
				<img src="../img/cmb.png">
				<div class="body-strong">招商银行（对公账号）</div>
				<div class="body-content">招商银行深圳分行中心城支行</div>
				<div>户 名&nbsp;:&nbsp;&nbsp;深圳市云达人科技有限公司</div>
				<div>账 号&nbsp;:&nbsp;&nbsp;7559 2705 0510 301</div>
			</div>
		</div>
		<div class="col-md-6 pay-card alipay-pay-card">
			<div class="head-title">
				<img class="head-title-img" src="../img/title.jpg" />
				付款账户二
			</div>
			<div class="body-card">
				<img src="../img/alipay.png">
				<div class="body-strong">支付宝</div>
				<div>户 名&nbsp;:&nbsp;&nbsp;深圳市云达人科技有限公司</div>
				<div>账 号&nbsp;:&nbsp;&nbsp;pay@yundaren.com</div>
			</div>
		</div>
	</div>
	<!--end of body -->

	<div>
		<input type="hidden" id="topurl" value="${store_location}/" />
	</div>
	<!--start of footer-->
	<#include "../footer.ftl">
	<!--end of footer-->
	<!---start of help docker-->
	<div id="top"></div>
	<!--end of help docker-->
</body>
</html>
