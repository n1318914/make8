<!DOCTYPE html>
<html>
	<head>
		<#include "./common.ftl">
		<meta charset="utf-8">
		 <title>码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no" />
		<meta name="keywords" content="码客帮, 软件外包, 软件众包, 代码众包, 开发者众包, 程序员兼职, 项目外包,互联网软件外包,网站外包,安卓APP外包,Andriod APP,iOS APP外包,微信开发外包">
        <meta name="description" content="码客帮是一个基于众包的互联网软件技术服务平台，建立项目需求方与技术大牛的连接。帮助需求方快速找到靠谱的软件工程师，同时也为程序员提供积累项目经验和收获财富的渠道。">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/bootstrap.min.css?v=${version}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css?v=${version}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/css/helpdocker.css?v=${version}"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/font-awesome/css/font-awesome.min.css?v=${version}"/>
	    <link href="${ctx}/thirdparty/bootstrapdialog/css/bootstrap-dialog.min.css?v=${version}"  rel="stylesheet" type="text/css">
			<link rel="stylesheet" type="text/css" href="${ctx}/css/footer.css?v=${version}"/>
		<script src="${ctx}/js/jquery-2.2.0.min.js?v=${version}"></script>
		<script src="${ctx}/thirdparty/countUp/countUp.min.js?v=${version}"></script>
		<script src="${ctx}/js/bootstrap.min.js?v=${version}"></script>
		<script type="text/javascript" src="${ctx}/js/index.js?v=${version}"></script>
		<script type="text/javascript" src="${ctx}/js/comm.js?v=${version}"></script>
        <script type="text/javascript" src="${ctx}/js/helpdocker.js?v=${version}"></script>
        <script type="text/javascript" src="${ctx}/js/footer.js?v=${version}"></script>
		<script type="text/javascript" src="${ctx}/thirdparty/bootstrapdialog/js/bootstrap-dialog.min.js?v=${version}"></script>
		<script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/jquery.validate.js?v=${version}"></script>
		<script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/localization/messages_zh.js?v=${version}"></script>
	</head>
	<body>
		<!--start of hidden parameters-->
		<input type="hidden" id="h_developerNum" value="${registersCount}"/>
		<input type="hidden" id="h_companyNum" value="${developersCount}"/>
		<input type="hidden" id="h_projectNum" value="${projectNum}"/>
		<!-- end of hidden parameters-->

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
								<!--<i>︿</i>-->
								<i class="fa fa-angle-up"></i>
							</li>
							<li onclick="javascript:location.href='${ctx}/home/userinfo'" onmouseover="loginMouseOver(this)" onmouseout="loginMouseOut(this)">
								个人中心<#if Session.canJoinNum?? && Session.canJoinNum != 0><span style="border-radius: 50%;margin-left: 10px;font-size: 8px;width: 15px;height: 15px;background: red;color: white;display: inline-block;line-height: 15px;text-align: center;">${Session.canJoinNum}</span></#if>
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

		<div class="col-md-12 headImg">
			<div class="container text-center">
				<div class="col-md-12 head-Font-bg">让技术大牛为你开发，实现你的互联网+</div>
				<div class="col-md-12 head-Font-sm">专属项目经理 / BAT级工程师 / 更低的成本 / 一流的开发质量</div>
				<div class="col-md-12">
					<input id="Send" type="button" class="btn Send" onclick="javascript:location.href='/home/request'" value="发布需求" />
				</div>

				<div class="col-md-12 appointment">
					<form id="form-reservation" method="post" action="javascript:doServiceReservation();">
						<div class="col-md-offset-1 col-md-10 appointmentBackground">
							<span>
								<input type="text" id="name" class="name" name="name" onkeyup="inputBackground(this)" onblur="inputBlur(this)" required/>
								<div id="nameWarning" class="appointmentWarning"></div>
							</span>
							<span>
								<input type="text" id="TelPhone" class="TelPhone" name="TelPhone" onkeyup="inputBackground(this)" onblur="inputBlur(this)" maxlength="11" minlength="11" required isMobilePhoneNumber="true"/>
								<div id="telPhoneWarning" class="appointmentWarning"></div>
							</span>
							<span>
								<textarea type="text" id="demand" class="demand" name="demand"  onkeyup="inputBackground(this)" onblur="inputBlur(this)" required/></textarea>
								<div id="demandWarning" class="appointmentWarning"></div>
							</span>
							<div style="float:left;width: 9%;" class="appointmentMarginTop">
								<input class="btn-appointment btn" type="submit" value="预约顾问" />
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>

		<div class="col-md-12 col-sm-12 col-xs-12 padding news">
			<div class="col-md-4 col-sm-4 col-xs-4 newsContentLeft text-right">
				<div class="newsContentLeftDiv">
					<div class="text-left newsTitle">NEWS</div>
					<div class="text-left newsHr"></div>
					<div class="text-left">最新资讯</div>
				</div>
			</div>
			<div class="col-lg-5 col-md-7 col-sm-5 col-xs-6 padding-left newsContentRight">
				<!--<marquee scrollamount="" scrolldelay=""  >-->
				<div class="marquee">
					<#if news?? && news?size gt 0>
						<#list news as newsItem>
							<div class="marqueeDiv" onmouseover="marqueeMouseOver(this)" onmouseout="marqueeMouseOut(this)" onclick="window.open('${newsItem.linkUrl}')">
								<div class="col-md-2 col-xs-4 col-sm-4 padding marqueeTime hidden-xs">${newsItem.pubDate}</div>
								<div class="col-md-10 col-xs-12 col-sm-8 padding marqueeTitle">${newsItem.title}</div>
							</div>
						</#list>
					</#if>
				</div>
			</div>
			<div class="col-lg-2 col-md-1 col-sm-1 col-xs-4 more hidden-xs">
				<div class="col-md-12 col-sm-12 col-xs-12 moreBtnDiv">
					<button class="moreBtn" onclick="window.open('http://news.make8.com')">MORE</button>
				</div>
			</div>
			<div class="col-lg-2 col-md-1 col-sm-1 col-xs-2 more hidden-md hidden-lg hidden-sm" onclick="window.open('http://news.make8.com')">
				<img src="${store_location}/img/more.png" />
			</div>
		</div>

		<div class="col-xs-12 padding" style="background: #f5fbff">
			<div class="container text-center serviceContent">
				<h2>服务内容</h2>
				<div></div>
				<div class="col-md-12 text-left padding">

					<div class="col-xs-12 col-md-4 text-center padding-left serviceContent_padding">
						<div class="serviceContent_div">
							<div><img src="${store_location}/img/v2/serviceContent_01.png" /></div>
							<div class="serviceContent_title">解决方案</div>
							<div class="serviceContent_Content">与电商、O2O、直播、互金等行业合作伙伴为您提供从产品、技术、运营的综合解决方案</div>
							 <div onclick="window.location.href='/public/solution'" class="serviceContent_loadMore">详情</div> 
						</div>
					</div>

					<div class="col-xs-12 col-md-4 text-center serviceContent_padding">
						<div class="serviceContent_div">
							<div><img src="${store_location}/img/v2/serviceContent_02.png" /></div>
							<div class="serviceContent_title">软件众包</div>
							<div class="serviceContent_Content">拥有前华为、Oracle的工程师团队和平台数万名工程师资源，为您提供专业的软件众包服务</div>
							 <div onclick="window.location.href='/public/crowdsourcing'" class="serviceContent_loadMore">详情</div> 
						</div>
					</div>

					<div class="col-xs-12 col-md-4 text-center padding-right serviceContent_padding">
						<div class="serviceContent_div">
							<div><img src="${store_location}/img/v2/serviceContent_03.png" /></div>
							<div class="serviceContent_title">代维服务</div>
							<div class="serviceContent_Content">基于旗下运维派社区资源，为您提供系统上线后的日常代维、数据迁移、扩容等运维服务</div>
							 <div onclick="window.location.href='/public/maintainance'" class="serviceContent_loadMore">详情</div> 
						</div>
					</div>

				</div>
			</div>
		</div>

		<div class="container text-center professionalServices">
			<h2>服务优势</h2>
			<div></div>
			<div class="col-xs-12 padding">
				<div class="col-xs-12 padding border ps-list">
					<div class="col-xs-6 padding text-left">
						<div class="title">更专业的服务</div>
						<div class="content">配备专属项目经理，结合线上项目管理和代码托管系统，为您提供定制化服务</div>
					</div>
					<div class="col-xs-6 padding-right ps-img text-right"><img src="${store_location}/img/v2/professionalServices_01.png" alt="" /></div>
				</div>
				<div class="col-xs-12 padding border ps-list">
					<div class="col-xs-6 padding-left ps-img text-left"><img src="${store_location}/img/v2/professionalServices_02.png" alt="" /></div>
					<div class="col-xs-6 padding text-left">
						<div class="title">更高的性价比</div>
						<div class="content">平台直接对客户需求负责，确保交付质量，开发采用众包模式，降低交付成本</div>
					</div>
				</div>
				<div class="col-xs-12 padding border ps-list">
					<div class="col-xs-6 padding text-left">
						<div class="title">服务100+客户</div>
						<div class="content">成功服务100+企业客户的经验积累，包括电商、微信营销、APP、企业官网等</div>
					</div>
					<div class="col-xs-6 padding-right ps-img text-right"><img src="${store_location}/img/v2/professionalServices_03.png" alt="" /></div>
				</div>
			</div>
		</div>

		<div class="col-md-12 text-center successProject">
			<div class="container">
				<h2>客户案例</h2>
				<div></div>
				<div class="col-xs-12" style="padding-left: 0px;padding-right: 0px;">
					<ul>
						<#if customerCases?? && customerCases?size gt 0>
							<#list customerCases as customerCase>
								<li class="col-sm-12 col-sm-4 padding successProject_div">
									<div onmouseover="successProjectMouseover(this)" onmouseout="successProjectMouseout(this)" class="col-xs-12 col-md-12 text-center padding successProject-item" onclick="window.open('/public/case/detail/${customerCase.caseId}')">
										<div class="successProject-item-div" style="background:white url(${store_location}/${customerCase.abbrPicUrl!''})"></div>
										<div class="item">
											<div class="text-left item-name">${customerCase.caseName!""}</div>
											<div class="text-left">周期：<span style="color: #a5a5a5">${customerCase.devsCycle!""}天</span> | 参与角色：<span style="color: #a5a5a5">${customerCase.roles!""}</span></div>
										</div>
									</div>
								</li>
							</#list>
						</#if>
					</ul>
				</div>
				<div class="col-xs-12" onclick="window.open('/public/customercases')">
					<div class="loadMore">查看更多</div>
				</div>
			</div>
		</div>

		<div class="col-xs-12 col-md-12 mediaCoverage text-center">
			<div class="container">
				<h2>媒体报道</h2>
				<div></div>
				<div class="col-xs-12 col-md-4 mediaCoverage-padding">
					<div class="media">
						<a href="http://www.lieyunwang.com/archives/162529" target="_blank">
							<img src="${store_location}/img/v2/lieyun.png" />
						</a>
					</div>
				</div>
				<div class="col-xs-12 col-md-4 mediaCoverage-padding mediaCoverage-border">
					<div class="media">
						<a href="http://www.admin5.com/article/20160407/656414.shtml" target="_blank">
							<img src="${store_location}/img/v2/a5.png" />
						</a>
					</div>
				</div>
				<div class="col-xs-12 col-md-4 mediaCoverage-padding">
					<div class="media">
						<a href="http://do.chinabyte.com/162/13726662.shtml" target="_blank">
							<img src="${store_location}/img/v2/bite.png" />
						</a>
					</div>
				</div>
				<div class="col-xs-12 col-md-4 mediaCoverage-padding">
					<div class="media media1">
						<a href="http://tech.163.com/16/0610/18/BP7GFVGD00097U7V.html" target="_blank">
							<!-- <img src="${store_location}/img/v2/itzhuanjia.png" /> -->
							<img src="${store_location}/img/v2/wangyi.png" />
						</a>
					</div>
				</div>
				<div class="col-xs-12 col-md-4 mediaCoverage-padding mediaCoverage-border">
					<div class="media2">
						<a href="http://www.ctoutiao.com/56061.html" target="_blank">
							<img src="${store_location}/img/v2/chuangtoutiao.png" />
						</a>
					</div>
				</div>
				<div class="col-xs-12 col-md-4 mediaCoverage-padding">
					<div class="media3">
						<a href="http://www.cctime.com/html/2016-5-3/1166846.htm" target="_blank">
							<img src="${store_location}/img/v2/feixiang.png" />
						</a>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-12 data">
			<div class="container text-center">
				<div class="col-xs-12 col-sm-4 col-md-4 data-div">
					<div class="col-xs-6 col-sm-12 col-md-12 data-number" id="developerNum"></div>
					<div class="col-xs-6 col-sm-12 col-md-12 data-unit">注册用户</div>
				</div>
				<div class="col-xs-12 col-sm-4 col-md-4 data-div">
					<div class="col-xs-6 col-sm-12 col-md-12 data-number" id="companyNum"></div>
					<div class="col-xs-6 col-sm-12 col-md-12 data-unit">认证码客</div>
				</div>
				<div class="col-xs-12 col-sm-4 col-md-4 data-div">
					<div class="col-xs-6 col-sm-12 col-md-12 data-number" id="projectNum"></div>
					<div class="col-xs-6 col-sm-12 col-md-12 data-unit">交易项目</div>
				</div>
			</div>
		</div>

		<div class="col-md-12 his-said">
			<div class="col-xs-2 col-md-2 toLeftRight" id="to-left" onclick="heSaidUp()" onmouseover="heSaidBtnMOver(this)" onmouseout="heSaidBtnMOut(this)">
				<</div>
					<div class="col-md-8 col-xs-8 his-said-content">
						<ul class="he-said-container" id="he-said-ul">
							<li>
								<div class="col-sm-2 col-md-1 his-said-upDown hidden-xs">
									<img class="li-img-width" src="${store_location}/img/v2/his-said-up.png" alt="" />
								</div>
								<div class="col-md-3 he-pic text-center hidden-xs hidden-sm">
									<img src="${store_location}/img/v2/he-1.jpg" alt="" />
								</div>
								<div class="col-xs-12 col-sm-8 col-md-7 he-said">
									<p>码客帮是来自华为系的创业团队，其天生具有企业服务的基因，以客户为中心，相信码客帮通过众包模式可以提升软件外包的产品质量和服务水平。</p>
									<p>码客帮，帮码客。</p>
									<p class="text-right">——俞渭华 ，华友会创始人</p>
								</div>
								<div class="col-sm-2 col-md-1 his-said-upDown hidden-xs">
									<img class="li-img-width" src="${store_location}/img/v2/his-said-down.png" alt="" />
								</div>
							</li>

							<li>
								<div class="col-sm-2 col-md-1 his-said-upDown hidden-xs">
									<img class="li-img-width" src="${store_location}/img/v2/his-said-up.png" alt="" />
								</div>
								<div class="col-md-3 he-pic text-center hidden-xs hidden-sm">
									<img src="${store_location}/img/v2/he-4.jpg" alt="" />
								</div>
								<div class="col-xs-12 col-sm-8 col-md-7 he-said">
									<p>随着互联网+时代的来临，软件外包的需求越来越多。各家企业都需要寻找更优质的程序员和软件开发团队来帮助他们完成外包开发工作。</p>
									<p>有了码客帮，很好的解决了信息透明性问题，帮助企业找到最好的程序员！</p>
									<p class="text-right">——段晖(Bill)，e代测创始人，原迅雷高级副总裁</p>
								</div>
								<div class="col-sm-2 col-md-1 his-said-upDown hidden-xs">
									<img class="li-img-width" src="${store_location}/img/v2/his-said-down.png" alt="" />
								</div>
							</li>

							<li>
								<div class="col-sm-2 col-md-1 his-said-upDown hidden-xs">
									<img class="li-img-width" src="${store_location}/img/v2/his-said-up.png" alt="" />
								</div>
								<div class="col-md-3 he-pic text-center hidden-xs hidden-sm">
									<img src="${store_location}/img/v2/he-2.jpg" alt="" />
								</div>
								<div class="col-xs-12 col-sm-8 col-md-7 he-said">
									<p>码客帮的模式非常赞，如果你有一个想法，但是不知道如何实现，码客帮可以为你对接最专业的技术团队，放心靠谱。</p>
									<p>来码客帮，离你的梦想更接近，你还在犹豫吗？</p>
									<p class="text-right">——江明玉，金斧子CTO</p>
								</div>
								<div class="col-sm-2 col-md-1 his-said-upDown hidden-xs">
									<img class="li-img-width" src="${store_location}/img/v2/his-said-down.png" alt="" />
								</div>
							</li>

							<li>
								<div class="col-sm-2 col-md-1 his-said-upDown hidden-xs">
									<img class="li-img-width" src="${store_location}/img/v2/his-said-up.png" alt="" />
								</div>
								<div class="col-md-3 he-pic text-center hidden-xs hidden-sm">
									<img src="${store_location}/img/v2/he-3.jpg" alt="" />
								</div>
								<div class="col-xs-12 col-sm-8 col-md-7 he-said">
									<p>码客帮以一种创新的方式满足创业初期团队人员不够的痛点，随着软件行业进一步发展，中国一定会出现更多自由软件工作者</p>
									<p>码客帮平台正是他们所需要的</p>
									<p class="text-right">——王拓 ，三板斧CTO</p>
								</div>
								<div class="col-sm-2 col-md-1 his-said-upDown hidden-xs">
									<img class="li-img-width" src="${store_location}/img/v2/his-said-down.png" alt="" />
								</div>
							</li>

							<li>
								<div class="col-sm-2 col-md-1 his-said-upDown hidden-xs">
									<img class="li-img-width" src="${store_location}/img/v2/his-said-up.png" alt="" />
								</div>
								<div class="col-md-3 he-pic text-center hidden-xs hidden-sm">
									<img src="${store_location}/img/v2/he-5.png" alt="" />
								</div>
								<div class="col-xs-12 col-sm-8 col-md-7 he-said">
									<p>好的商业创意离不开好的技术团队来实现，如何找到靠谱的技术开发团队，是困扰很多初创企业的大问题！</p>
									<p>码客帮，汇聚大量优秀的Maker和技术团队，帮助初创企业在最短时间找到靠谱的“技术合伙人”！</p>
									<p class="text-right">——周维军 ，翼果CEO，原华为芬兰研究所负责人</p>
								</div>
								<div class="col-sm-2 col-md-1 his-said-upDown hidden-xs">
									<img class="li-img-width" src="${store_location}/img/v2/his-said-down.png" alt="" />
								</div>
							</li>
						</ul>
					</div>
					<div class="col-xs-2 col-md-2 toLeftRight" id="to-right" onclick="heSaidDown()" onmouseover="heSaidBtnMOver(this)" onmouseout="heSaidBtnMOut(this)">></div>
			</div>

			<div class="col-md-12 padding wantToDo">
				<div class="col-xs-12 col-sm-6 hidden-xs wantToDo_bg"></div>
				<div class="container">
					<div class="col-sm-6 col-md-6 text-center wanToDo-Div">
						<p>您有一个需求？</p>
						<img style="display: block;margin: 0 auto;" src="${store_location}/img/v2/demand.png" />
						<button class="btn btn-primary wanToDo-btn wantToDo_btn_left" onclick="javascript:window.location.href='/home/request'">发布需求</button>
					</div>
					<div class="col-sm-6 col-md-6 text-center wanToDo-Div">
						<p>您是技术大牛？</p>
						<img style="display: block;margin: 0 auto;" src="${store_location}/img/v2/technology.png" />
						<button class="btn btn-success wanToDo-btn wantToDo_btn_right" onclick="javascript:window.location.href='/home/userinfo'">认证码客</button>
					</div>
				</div>
			</div>

			<!--start of footer-->
			<#include "./footer.ftl">
				<!--end of footer-->
				<!---start of help docker-->
				<div id="top"></div>
				<!--end of help docker-->

				<div>
					<input type="hidden" id="topurl" value="${ctx}/" />
					<input type="hidden" id="reload" value="1" />
					<input type="hidden" id="version" value="${version}" />
				</div>

				<!--CNZZ CODE-->
				${cnzz_html}
				<!--END OF CNZZ CODE-->
	</body>

</html>
