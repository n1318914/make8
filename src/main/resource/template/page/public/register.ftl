<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<title>用户注册 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
		<#include "../comm-includes.ftl">
		${simple_includes}
		<link rel="stylesheet" type="text/css" href="${ctx}/css/public/register.css?v=${version}"/>
        <script type="text/javascript" src="${ctx}/js/public/register.js?v=${version}"></script>
	</head>

	<body>
		<!--start of header-->
		<#include "../simple-header.ftl">
			<!--end of header-->

			<div class="col-md-offset-1 col-xs-12 col-md-10 container join-form-marginbottom">
				<form id="phoneForm" method="post" autocomplete="off" role="form" action="javascript:doPhoneRegister()">
					<div class="col-md-offset-4 col-md-4 col-lg-4 join-bg join-overflow">
						<div class="join-title-margintop">
							<div class="col-xs-4 col-md-4 join-hr"></div>
							<div class="col-xs-4 col-md-4 join-title text-center">
								<p>注册新的账号</p>
							</div>
							<div class="col-xs-4 col-md-4 join-hr"></div>
						</div>

						<div class="col-lg-12 col-md-12 form-group-height join-phone-margintop">
							<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg join-overflow">
								<span class="col-xs-2 col-lg-2 span-left-bd"><p class="form-mobile-icon"></p></span>
								<div class="col-xs-10 col-lg-10">
									<input class="form-input input-color" name="phoneNumber" id="phoneNumber" placeholder="手机号" required="true" minlength="11" maxlength="11" isMobilePhoneNumber="true" phoneNumExisting="true" />
								</div>
							</div>
							<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="phoneMsg"></span>
						</div>

						<div class="col-lg-12 col-md-12 form-group-height" id="picCodeForm">
								<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg join-overflow">
									<span class="col-xs-2 col-md-2 span-left-bd"><p class="form-vcode-icon"></p></span>
									<div class="col-xs-5 col-md-5">
										<input class="form-input-small input-color" name="picCode" id="picCode" onkeyup="checkCode()" placeholder="图片验证码" required minlength="4" maxlength="4" />
									</div>
									<div class="col-xs-5 col-md-5 form-padding-right text-right">
										<img class="form-padding-right" id="vcode_img" src="/api/common/captcha" onclick="refreshVCode();">
									</div>
								</div>
								<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="picCodeMsg">验证码输入有误</span>
						</div>
						
						<div class="col-lg-12 col-md-12 form-group-height" id="phoneCodeForm">
							<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg join-overflow">
								<span class="col-xs-2 col-md-2 span-left-bd"><p class="form-vcode-icon"></p></span>
								<div class="col-xs-5 col-md-5">
									<input class="form-input-small input-color" name="phoneCode" id="phoneCode" placeholder="验证码" required minlength="4" maxlength="4"/>
								</div>
								<div class="col-xs-5 col-md-5 form-padding-right">
									<input type="button" id="sendVcodeBtn" class="col-xs-12 col-md-12 btn btn-primary form-padding-right form-btn form-btn-height join-btn" value="获取短信验证" onclick="javascript:sendSMS();" disabled="true" />
								</div>
							</div>
							<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="phoneCodeMsg"></span>
						</div>

						<div class="col-lg-12 col-md-12 form-group-height">
							<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg join-overflow">
								<span class="col-xs-2 col-md-2 span-left-bd"><p class="form-lock-icon"></p></span>
								<div class="col-xs-8 col-md-8">
									<input class="form-input input-color" name="phonePasswd" id="phonePasswd" placeholder="密码" minlength="6" required/>
								</div>
							</div>
							<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="phonePasswdMsg"></span>
						</div>

						<div class="col-lg-12 col-md-12 col-xs-12 desc-container">
							<div class="text-center">
								<p class="desc"><span class="glyphicon glyphicon-asterisk"></span>点击注册即表示您已同意<a class="make8-agreement" href="${ctx}/about/contract" target=_blank>《码客帮服务协议》</a></p>
							</div>
						</div>

						<div class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 btn-submit-container">
							<div class="btn-height">
								<button type="submit" id="btnSubmit" class="btn btn-primary btn-submit btn-submit-style">注册</button>
								<span class="col-lg-offset-1 col-lg-10 col-xs-10 msgspan" id="phoneRegisterMessage"></span>
							</div>
							<div class="text-center">已有账号?<a href="/public/login">马上登录</a></div>
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