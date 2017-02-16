<div class="container">
<nav id="nav">
	<div class="container">
		<h1>
			<a href="/">
				<img src="${store_location}/img/v2/logo_black.png">
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
					<li onclick="javascript:location.href='${ctx}/about/flink'" onmouseover="loginMouseOver(this)" onmouseout="loginMouseOut(this)">
						友情链接
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
					<li onclick="navLogin(this);" id = "consultantNav">
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
			<!--<li><a href="/public/comp_list">服务商库</a><div></div></li>-->
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
					<#if Session.SESSION_LOGIN_USER.userInfoVo.name??>
					<li onclick="javascript:location.href='${ctx}/home/useraccount'" onmouseover="loginMouseOver(this)" onmouseout="loginMouseOut(this)">
						财务中心
					</li>
					</#if>
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
			<!--<li><a href="/public/comp_list">服务商库</a><div></div></li>-->
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
</div>
