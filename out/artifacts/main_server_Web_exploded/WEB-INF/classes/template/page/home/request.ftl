<!DOCTYPE html>
<html>
	<head>
		<title>发布需求 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
		<#include "../comm-includes.ftl">
	    ${basic_includes}
	    ${tools_includes}

        <link rel="stylesheet" href="${ctx}/css/home/request.css?v=${version}">
        <link rel="stylesheet" href="${ctx}/css/style.css?v=${version}">
        <script type="text/javascript" src="${ctx}/js/home/request.js?v=${version}"></script>
	</head>

	<body>
	 <!--start of header-->
	  <nav id="nav">
	    <div class="container">
		<h1>
			<a href="/">
				<img src="${store_location}/img/v2/logo.png">
			</a>
		</h1>
		<div class="menu" onclick="dropDownMenu()"><img src="${store_location}/img/v2/menu.png"/></div>
		<ul class="nav-ul">
		 	 <#if Session.SESSION_LOGIN_USER??>
				 <input type="hidden" id="isLogged" value="true" />
				<#if Session.SESSION_LOGIN_USER.userInfoVo.userType = -1>
					<li><a href="${ctx}/admin/requests_review">项目审核</a><div></div></li>
					<li><a href="${ctx}/admin/users_review">用户审核</a><div></div></li>
					<li><a href="${ctx}/admin/reserve_review">预约审核</a><div></div></li>
					<li><a href="${ctx}/public/comp_list">服务商库</a><div></div></li>
					<li><a href="${ctx}/admin/user_info_import">码客库</a><div></div></li>
					<li>
						<ul class="admin">
							<li onclick="navLogin(this)" id='mgrMenu'>
								后台管理
							</li>
							<li onclick="javascript:location.href='${ctx}/admin/compinfo_modify'" onmouseover="loginMouseOver(this)" onmouseout="loginMouseOut(this)">
								服务商录入
							</li>
							<li onclick="javascript:location.href='${ctx}/admin/mail_send'" onmouseover="loginMouseOver(this)" onmouseout="loginMouseOut(this)">
								邮件推送
							</li>
							<li onclick="javascript:location.href='${ctx}/admin/dataAnylize'" onmouseover="loginMouseOver(this)" onmouseout="loginMouseOut(this)">
								数据分析
							</li>
						</ul>
					</li>
					<li>
						<ul class="use">
							<li onclick="navLogin(this)" id="adminNav">
								<i title="${Session.SESSION_LOGIN_USER.userInfoVo.name!""}">${Session.SESSION_LOGIN_USER.userInfoVo.displayName!""}</i>
								<i class="fa fa-angle-up"></i>
							</li>
							<li onclick="javascript:logout();" onmouseover="loginMouseOver(this)" onmouseout="loginMouseOut(this)">
								退出登录
							</li>
						</ul>
						<!--<input id="join" class="btn join" type="button" onclick="javascript:logout();" value="退出">-->
					</li>
				</#if>

				 <#if Session.SESSION_LOGIN_USER.userInfoVo.userType = 2>
				 <li><a href="/">首页</a><div></div></li>
				 <li class="newDiv"><a href="/public/evaluate">项目估价</a><div></div></li>
				 <li><a href="/market">项目市场</a><div></div></li>
				 <li><a href="${ctx}/home/projects_review">项目管理</a><div></div></li>
				 <li><a href="${ctx}/admin/users_review">用户查询</a><div></div></li>
				 <li><a href="${ctx}/public/comp_list">服务商库</a><div></div></li>
				 <li><a href="${ctx}/admin/user_info_import">码客库</a><div></div></li>
					<li>
						<ul class="use">
							<li onclick="navLogin(this);" id="consultantNav">
								<i title="${Session.SESSION_LOGIN_USER.userInfoVo.name!""}">${Session.SESSION_LOGIN_USER.userInfoVo.displayName!""}</i>
								<i class="fa fa-angle-up"></i>
							</li>
							<li onclick="javascript:logout();" onmouseover="loginMouseOver(this)" onmouseout="loginMouseOut(this)">
							退出登录
							</li>
						</ul>
					<!--<input id="join" class="btn join" type="button" onclick="javascript:logout();" value="退出">-->
					</li>
				  </#if>

			      <#if Session.SESSION_LOGIN_USER.userInfoVo.userType = 0 || Session.SESSION_LOGIN_USER.userInfoVo.userType = 1>
					<li><a href="/">首页</a><div></div></li>
					<li class="newDiv"><a href="/public/evaluate">项目估价</a><div></div></li>
					<li><a href="/home/request">发布需求</a><div></div></li>
					<li><a href="/market">项目市场</a><div></div></li>
					<li><a href="/home/userinfo">码客认证</a><div></div></li>
					<!-- <li><a href="/public/comp_list">服务商库</a><div></div></li> -->
					<li><a href="http://news.make8.com/">码客新闻</a><div></div></li>
					<li><a href="/about/aboutus">关于我们</a><div></div></li>
					<li>
						<ul class="use">
							<li onclick="navLogin(this)">
								<i title="${Session.SESSION_LOGIN_USER.userInfoVo.name!""}">${Session.SESSION_LOGIN_USER.userInfoVo.displayName!""}</i>
								<#if Session.canJoinNum?? && Session.canJoinNum != 0><span>${Session.canJoinNum}</span></#if>
								<i class="fa fa-angle-up"></i>
							</li>
							<li onclick="javascript:location.href='${ctx}/home/userinfo'" onmouseover="loginMouseOver(this)" onmouseout="loginMouseOut(this)">
								个人中心
							</li>
							<li onclick="javascript:logout();" onmouseover="loginMouseOver(this)" onmouseout="loginMouseOut(this)">
								退出登录
							</li>
						</ul>
					</li>
		          </#if>
		       <#else>
			        <li><a href="/">首页</a><div></div></li>
					<li class="newDiv"><a href="/public/evaluate">项目估价</a><div></div></li>
					<li><a href="/home/request">发布需求</a><div></div></li>
					<li><a href="/market">项目市场</a><div></div></li>
					<li><a href="/home/userinfo">码客认证</a><div></div></li>
					<!-- <li><a href="/public/comp_list">服务商库</a><div></div></li> -->
					<li><a href="http://news.make8.com/">码客新闻</a><div></div></li>
					<li><a href="/about/aboutus">关于我们</a><div></div></li>
					<li id="login_status">
						<input id="login" class="btn login" type="button" onclick="javascript:location.href='/public/login'" value="登录">
						<input id="join" class="btn join" type="button" onclick="javascript:location.href='/public/register'" value="注册">
					</li>
		 	 </#if>
		   </ul>
		  </div>
		</nav>
		<!--end of header-->

        <!--hidden parameter-->
        <#if ssoUser??>
        	<input type="hidden" id="isLoginUser" value="1"></input>
        	<input type="hidden" id="h_loginName" value="${ssoUser.loginName!""}"></input>
        <#else>
        	<input type="hidden" id="isLoginUser" value="0"></input>
        </#if>
        <!--end of parameter-->

		<div class="request-banner text-center">
			<#if Session.SESSION_LOGIN_USER??>
				<p>Step 1 of 2</p>
				<div class="request-bannerBackground" style="width: 60px;"><div></div></div>
			<#else>
				<p>Step 1 of 3</p>
				<div class="request-bannerBackground"><div></div></div>
			</#if>
		</div>
		<div class="container">
			<div class="breadcrumb-container-4-request">
				<ol class="breadcrumb">
				</ol>
			</div>
		</div>

		<div class="container">
			<div class="col-md-offset-1 col-md-10 form">
				<!--  发布需求第一步 -->
				<div id="requestStep1Div">
					<form class="form-horizontal" id="publishRequestFormStep1" role="form">
						<fieldset>
							<div class="wbm-state">
								<h1 class="wbm-state-tit text-center">签署服务协议</h1>
								<p class="text-center wbm-stata-main">
									<br/>您的项目信息不会透露给任何第三方，
									<br/>为确保服务质量，需要签署服务协议，
									<br/>服务协议详细条款，<a href="${ctx}/about/contract" target="_blank">点击这里</a>
								</p>

								<div class="col-xs-offset-3 ol-xs-9 col-md-offset-5 col-md-4">
									<div class="agree" id="agree" onclick="iAgree(this)"></div>
									<input type="checkbox" id="wbmEp" class="wbm-ep" onclick="checkNext()">
									<p class="wbm-eo text-center">我同意</p>
								</div>
								<div class="col-md-12 col-xs-12 col-sm-12 text-center">
									<input id="next" type="button" class="btn btn-lg btn-primary" style="margin-left: 0px;" onclick="javascript:nextTosecond();" value="下一步"></input>
								</div>
							</div>
						</fieldset>
					</form>
				 </div>

				<!-- 发布需求第二步 -->
				<div class="col-md-offset-1" id="requestStep2Div" style="display:none">
				   <#if ssoUser??>
				   	  <form class="form-horizontal" id="publishRequestFormStep2" role="form" method="post" action="javascript:doPublish();">
				   <#else>
					  <form class="form-horizontal" id="publishRequestFormStep2" role="form" method="post">
				   </#if>
						<fieldset>
							<h1 class="text-center wbm-tell-name">告诉我们您的项目</h1>
							<div class="form-group form-group-a col-md-12">
								<label for="projectName"><i class="form-required">*</i>项目名称</label>
								<div class="col-md-12">
									<input type="text" class="form-control form-control-a" id="projectName" name="projectName" placeholder="例如：电商类APP、网站前端开发、一个技术难点等" required>
							   </div>
							</div>
							<div class="form-group form-group-a col-md-12">
								<label for="projectBudget"><i class="form-required">*</i>项目预算</label>
								<div class="col-md-12">
									<select class="form-control form-control-a" name="projectBudget" id="projectBudget" required>
										<option value="">项目预算</option>
										<#list listDictItem as tag>
											<#if tag.type == "projectBudget">
												<option value="${tag.name}">${tag.name}</option>
											</#if>
										</#list>
									</select>
								</div>
							</div>

							<div class="form-group form-group-a col-md-12">
								<label for="projectName"><i class="form-required">*</i>项目周期</label>
								<div class="col-md-12">
									<input type="text" 	digits=true class="form-control form-control-a" id="period" name="period" maxlength="3" placeholder="您期望的项目交付天数" required>
							   </div>
							</div>

							<div class="form-group col-md-12">
								<label for="projectType"><i class="form-required">*</i>项目类型</label>
								<div class="col-md-12">
									<div style="overflow: hidden;margin-top:5px ;">
										<#list listDictItem as tag>
				  		  	  				<#if tag.type=="projectType">
												<label for="pType${tag.value}" class="col-xs-6 col-md-4 checkbox-padding" onclick="checkboxActive(this)">
													<div class="checkbox-img"><img src="${ctx}/img/checkboxchecked.png"></div>
													<p>${tag.name}</p>
												</label>

												<input type="checkbox" class="checkbox-display" name="Type" id="pType${tag.value}" value="${tag.value}" required="true" />
											</#if>
										</#list>
									</div>
									<div id="projectTypeWarning" class="col-md-12 checkbox-padding checkbox-warning"></div>
								</div>
							</div>

							<div class="form-group col-md-12">
							 <div class="col-md-3">
								<label for="pDomainChb">需要协助购买域名吗？</label>
							 </div>
								<div class="col-md-2">
									<div>
										<label for="pDomainChb" class="col-md-12 col-xs-12 checkbox-padding" onclick="checkboxActive(this)">
											<div class="checkbox-img"><img src="${ctx}/img/checkboxchecked.png"></div>
											<p>需要</p>
										</label>
										<input type="checkbox" class="checkbox-display" name="pDomainChb" id="pDomainChb" value="1" />
									</div>
                </div>
							</div>

							<div class="form-group col-md-12">
								<div class="col-md-4">
									<label for="projectType">需要协助购买云主机和数据库吗？</label>
								</div>
								<div class="col-md-2">
									<div>
										<label for="pCloudServer" class="col-md-12 col-xs-12 checkbox-padding" onclick="checkboxActive(this)">
											<div class="checkbox-img"><img src="${ctx}/img/checkboxchecked.png"></div>
											<p>需要</p>
										</label>
										<input type="checkbox" class="checkbox-display" name="pCloudServer" id="pCloudServer" value="1" />
									</div>
								</div>
							</div>

							<div class="form-group form-group-4-textarea col-md-12">
								<label for="projectDesc"><i class="form-required">*</i>填写您的需求（至少需要20个字符）</label>
								<div class="col-md-12">
									<textarea class="form-control form-textarea" rows="5" id="projectDesc" name="projectDesc" placeholder="例如: 1. 主要功能点；2. 可参考的项目；3. 其它说明" required="true" minlength="20" maxlength="1000"></textarea>
								</div>
							</div>

							<div class="col-md-11">
								<div class="col-xs-6 col-md-6">
									<input type="button" class="btn btn-lg btn-default" onclick="javascript:previousToFirst();" value="上一步"></button>
								</div>
								<#if ssoUser??>
									<div class="col-xs-6 col-md-6">
										<button type="submit" class="btn btn-lg btn-primary pull-right">发布</button>
									</div>
								<#else>
									<div class="col-xs-6 col-md-6">
										<input type="button" class="btn btn-lg btn-primary pull-right" value="下一步" onclick="javascript:nextToThird()"></input>
									</div>
								</#if>

							</div>
                        </fieldset>
					</form>
				</div>

				<!--发布需求第三步 -->
				<div class="col-md-offset-1" id="requestStep3Div" style="display:none;">
					<form class="form-horizontal" id="publishRequestFormStep3" action="javascript:doPublish();" role="form" method="post">
							<fieldset>
								<h1 class="text-center wbm-tell-name">创建您的项目</h1>
								<div class="form-group form-group-a col-md-12">
									<label for="loginName"><i class="form-required">*</i>手机号</label>
									<div class="col-md-12">
										<input type="text" class="form-control form-control-a" name="loginName" id="loginName" placeholder="输入您的手机号" required="true" isMobilePhoneNumber="true"
										       minlength="11" maxlength="11"  onkeyup="isJoin(this)">
									</div>
								</div>

								<!--<div class="form-group form-group-a col-md-12">
									<label for="password"><i class="form-required">*</i>验证码</label>
									<div class="col-md-12">
										<input type="text" class="form-control form-control-a" name="verificationCode" id="verificationCode" minlength="4" maxlength="4" placeholder="请输入验证码" required="true">
								    </div>
								</div>-->

								<div class="form-group form-group-a col-md-12">
									<label for="password"><i class="form-required">*</i>密码</label>
									<div class="col-md-12">
										<input type="password" class="form-control form-control-a" name="password" id="password" placeholder="输入您的密码" required="true">
								    </div>
								</div>

								<div class="col-md-11">
									<div class="col-xs-6 col-md-6">
										<input type="button" class="btn btn-lg btn-default" onclick="javascript:previousToSec();" value="上一步"></input>
									</div>
									<div class="col-xs-6 col-md-6">
										<button type="submit" class="btn btn-lg btn-primary pull-right">发布</button>
									</div>
							    </div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>

		<script>
			window.addEventListener("scroll",navTop)
		</script>

		<!--start of footer-->
		<#include "../footer.ftl">
		<!--end of footer-->
		<!---start of help docker-->
		<div id="top"></div>
		<!--end of help docker-->

		<div>
			<input type="hidden" id="topurl" value="${ctx}/" />
			<input type="hidden" id="reload" value="1" />
		</div>

		<!--CNZZ CODE-->
		${cnzz_html}
		<!--END OF CNZZ CODE-->
	</body>

</html>
