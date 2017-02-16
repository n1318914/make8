<nav id="nav" class="nav-simple">
	<div class="container">
		<h1>
			<a href="/">
				<img src="${store_location}/img/v2/logo.png">
			</a>
		</h1>
		<div class="menu" onclick="dropDownMenu()"><img src="${store_location}/img/v2/menu.png"/></div>
		<ul class="nav-ul">
	        <li><a style="color: white;" href="/">首页</a><div></div></li>
			<li class="newDiv"><a style="color: white;" href="/public/evaluate">项目估价</a><div></div></li>
			<li><a style="color: white;" href="/home/request">发布需求</a><div></div></li>
			<li><a style="color: white;" href="/market">项目市场</a><div></div></li>
			<li><a style="color: white;" href="/home/userinfo">码客认证</a><div></div></li>
			<!--<li><a style="color: white;" href="/public/comp_list">服务商库</a><div></div></li>-->
			<li><a style="color: white;" href="http://news.make8.com/">码客新闻</a><div></div></li>
			<li><a style="color: white;" href="/about/aboutus">关于我们</a><div></div></li>
			<li id="login_status">
				<input id="login" class="btn login" type="button" onclick="javascript:location.href='/public/login'" value="登录">
				<input id="join" class="btn join" type="button" onclick="javascript:location.href='/public/register'" value="注册">
			</li>
   </ul>
  </div>
</nav>
