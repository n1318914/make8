<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<title>用户登录 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
	
		<#include "../common.ftl">
		<#include "../comm-includes.ftl">
		${simple_includes}
		<link rel="stylesheet" type="text/css" href="${ctx}/css/public/register.css?v=${version}" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/public/login.css?v=${version}" />
		<script type="text/javascript" src="${ctx}/js/public/login.js?v=${version}"></script>
	</head>

	<body>
		<!--start of hidden parameters-->
		<div>
			<#if projectInSelfCreateLoginName??>
				<input type="hidden" id="h_LoginName" value="${projectInSelfCreateLoginName!" "}"/>
				<#else>
					<input type="hidden" id="h_LoginName" />
			</#if>

			<#if projectInSelfCreateFailedMsg??>
				<input type="hidden" id="h_failedMsg" value="${projectInSelfCreateFailedMsg!" "}"/>
				<#else>
					<input type="hidden" id="h_failedMsg" />
			</#if>
           
            <input type="hidden" id="h_loginFailedTimeExceeded" value="${loginFailedTimeExceeded}"></input>
		</div>
		<!--end of hidden parameters-->

		<!--start of header-->
		<#include "../simple-header.ftl">
		<!--end of header-->

		<div class="col-md-offset-1 col-xs-12 col-md-10 container join-form-marginbottom">
			<form id="loginForm" method="post" autocomplete="off" role="form" action="javascript:doLogin()">
				<div class="col-md-offset-4 col-md-4 col-lg-4 join-bg join-overflow">
					<div class="join-title-margintop">
						<div class="col-xs-4 col-md-4 join-hr"></div>
						<div class="col-xs-4 col-md-4 join-title text-center">
							<p>登录</p>
						</div>
						<div class="col-xs-4 col-md-4 join-hr"></div>
					</div>

					<div class="col-lg-12 col-md-12 form-group-height join-phone-margintop">
						<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg join-overflow">
							<span class="col-xs-2 col-lg-2 span-left-bd"><p class="form-mobile-icon"></p></span>
							<div class="col-xs-10 col-lg-10">
								<input id="loginName" name="loginName" class="form-input input-color" placeholder="手机号/邮箱" required="true" isloginnamevalid="true" autocomplete="off" aria-required="true">
							</div>
						</div>
						<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="loginNameMsg"></span>
					</div>

					<div class="col-lg-12 col-md-12 form-group-height">
						<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg join-overflow">
							<span class="col-xs-2 col-md-2 span-left-bd"><p class="form-lock-icon"></p></span>
							<div class="col-xs-10 col-md-10">
								<input type="password" class="form-input input-color" name="wpassword" id="wpassword" placeholder="密码" minlength="6" required autocomplete="off" />
							</div>
						</div>
						<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="passwdMsg"></span>
					</div>
					
					<div class="col-lg-12 col-md-12 form-group-height" id="vcode-div" style="display:none;">
						<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg">
							<span class="col-xs-2 col-md-2 span-left-bd"><p class="form-vcode-icon"></p></span>
							<div class="col-xs-5 col-md-5" style="padding-right: 0px;">
								<input class="form-input-small input-color" name="vcode" id="vcode" placeholder="验证码" required minlength="4" maxlength="4" autocomplete="off" />
							</div>
							<div class="col-xs-5 col-md-5 form-padding-right text-right" style="padding-left: 0px;">
								<img class="form-padding-right" id="vcode_img" src="/api/common/captcha" onclick="refreshVCode();">
							</div>
						</div>
						<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="vcodeMsg"></span>
					</div>

					<!--<div class="col-lg-4 col-md-4 col-xs-4">
							<div class="captcha">
								<img id="vcode_img" src="/api/common/captcha" onclick="refreshVCode();">
							</div>
						</div>-->

					<div class="gou">
						<div class="dagou">
							<input type="checkbox" id="wbmEp" class="wbm-ep" onclick="javascript:void(0)">
						</div>
						<p class="jizhu">记住我</p>
						<p class="wangji"><a href="/public/find_password">忘记密码</a></p>
					</div>

					<div class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 btn-submit-container">
						<div class="btn-height">
							<button type="submit" id="btnSubmit" class="btn btn-primary btn-submit btn-submit-style">登录</button>
							<span class="col-lg-offset-1 col-lg-10 col-xs-10 msgspan" id="loginMessage"></span>
						</div>
						<div class="text-center">没有账号?<a href="/public/register">立即注册</a></div>
					</div>
				</div>
			</form>
		</div>

		<!--start of footer-->
		<#include "../simple-footer.ftl">
		<!--end of footer-->

		<!--CNZZ CODE-->
		${cnzz_html}
		<!--END OF CNZZ CODE-->
  </body>
</html>