<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<title>找回密码 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${simple_includes}
				<link rel="stylesheet" type="text/css" href="${ctx}/css/public/find_password.css?v=${version}" />
				<script type="text/javascript" src="${ctx}/js/public/find_password.js?v=${version}"></script>

	</head>

	<body>
		<!--start of header-->
		<#include "../simple-header.ftl">
			<!--end of header-->

			<div class="col-md-offset-1 col-xs-12 col-md-10 container join-form-marginbottom">
				<form id="findPasswordForm" method="post" role="form" action="javascript:doFindPassword()">
					<fieldset>
						<div class="col-md-offset-4 col-md-4 col-lg-4 join-bg join-overflow">
							<div class="join-title-margin">
								<div class="col-xs-4 col-md-4 join-hr"></div>
								<div class="col-xs-4 col-md-4 join-title text-center">
									<p>找回密码</p>
								</div>
								<div class="col-xs-4 col-md-4 join-hr"></div>
							</div>

							<div class="col-lg-12 col-md-12 form-group-height">
								<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg">
									<div class="col-xs-12 col-lg-12">
										<input class="form-input input-color" id="loginName" name="loginName" placeholder="电子邮箱/手机号" required isLoginNameValid="true" loginNameExisting="true" />
									</div>
								</div>
								<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="loginNameMsg"></span>
							</div>

							<div class="col-lg-12 col-md-12 form-group-height">
								<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg">
									<div class="col-xs-6 col-md-6">
										<input class="form-input-small input-color" name="vcode" id="vcode" placeholder="验证码" required minlength="4" maxlength="4" />
									</div>
									<div class="col-xs-6 col-md-6 form-padding-right text-right">
										<img class="form-padding-right" id="vcode_img" src="/api/common/captcha" onclick="refreshVCode();">
									</div>
								</div>
								<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="vcodeMsg"></span>
							</div>

							<div class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 btn-submit-container">
								<div class="btn-height">
									<button type="submit" class="btn btn-lg btn-primary btn-block">找回</button>
									<span id="findPasswordMsg" class="col-lg-offset-1 col-lg-10 col-xs-10 msgspan"></span>
								</div>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<!--<div class="container">
  		 <div class="container findpassword-div" autocomplete="off">
				<form id="findPasswordForm" method="post" class="form-findpassword" role="form" action="javascript:doFindPassword()">
						<fieldset>
							<div class="col-lg-12 col-md-12 form-group">
								<div class="input-group">
	                <span class="input-group-addon"><p class="icon form-user-icon"></p></span>
	          	  	<i class="vertical-divider"></i>
					  	 		<input class="form-input" id="loginName" name="loginName" placeholder="电子邮箱/手机号"  required isLoginNameValid="true" loginNameExisting="true"/>
					  	  </div>
					  	  <span class="msgspan" id="loginNameMsg"></span>
					    </div>
					    
						  <div class="col-lg-12 col-md-12 form-group vcode-view" id="vcode-div">
						    	<div class="col-lg-8 col-md-9 col-xs-8">
						    		<div class="input-group">
	                  <span class="input-group-addon"><p class="icon form-vcode-icon"></p></span>
										<i class="vertical-divider"></i>
								  	<input class="form-input" name="vcode" id="vcode" placeholder="验证码" required minlength="4" maxlength="4"/>
								    </div>
								    <span class="msgspan" id="vcodeMsg"></span>
								  </div>
								  <div class="col-lg-4 col-md-3 col-xs-4">
									   <div class="captcha">
									   		<img  id="vcode_img" src="/api/common/captcha" onclick="refreshVCode();">
									   </div>
								  </div>
						</div>
					</fieldset>
						
					<div class="col-lg-12 col-md-12">
				    <button type="submit" class="btn btn-lg btn-primary btn-block">找回</button>
				    <span id="findPasswordMsg" class="msgspan"></span>
				  </div>
				</form>
		 </div>
		</div>-->

			<!--start of footer-->
			<#include "../simple-footer.ftl">
				<!--end of footer-->

				<!--CNZZ CODE-->
				${cnzz_html}
				<!--END OF CNZZ CODE-->
	</body>

</html>