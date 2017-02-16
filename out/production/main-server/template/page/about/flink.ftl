<!DOCTYPE html>
<html ng-app="flinkApp" ng-controller="flinkController">

	<head lang="zh-CN">
		<title>友情链接 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
			${basic_includes} ${tools_includes}
			<link href="${ctx}/css/about.css?v=${version}" rel="stylesheet" type="text/css">
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->

			<!-- 添加友好链接 -->
			<div class="modal" id="add">
				<div class="modal-dialog modal-custom-class">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
							<h4 class="modal-title">添加友情链接</h4>
						</div>
						<form action="javascript:addFlink()" id="addFlinkForm">
						<div class="modal-body" style="overflow: hidden;">
							<div class="form-group">

								<div class="col-xs-12 col-sm-12 col-md-12 add-div">
									<div class="col-xs-2 col-md-2 text-right">
										<label for="projectname">公司名称:</label>
									</div>
									<div class="col-xs-10 col-md-10">
										<input class="form-control" type="text" id="companyName" maxlength="100" name="companyName" required>
									</div>
								</div>

								<div class="col-xs-12 col-sm-12 col-md-12 add-div">
									<div class="col-xs-2 col-md-2 text-right">
										<label for="projecturl">公司链接:</label>
									</div>
									<div class="col-xs-10 col-md-10">
										<input class="form-control" type="text" id="companyUrl" maxlength="200" name="companyUrl" required>
									</div>
								</div>

								<div class="col-xs-12 col-sm-12 col-md-12 add-div">
									<div class="col-xs-2 col-md-2 text-right">
										<label for="projectintro">公司logo:</label>
									</div>
									<div class="col-xs-10 col-md-10">
										<input name="companyLogo" id="companyLogo" class="file" type="file"></input>
										<div id="companyLogoWarning" class="warning"></div>
									</div>
								</div>

							</div>

							<div class="col-xs-12 col-sm-12 col-md-12 add-div div-margin">
								<div class="col-xs-6 col-sm-6 col-md-6 text-left">
									<div class="col-xs-12 col-md-12">
										<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
									</div>
								</div>

								<div class="col-xs-6 col-sm-6 col-md-6 text-right">
									<button type="submit" class="btn btn-primary">确定</button>
								</div>
							</div>
						</div>
						</form>
					</div>
				</div>
			</div>

			<div class="wbm-flink-banner">
				<img src="${store_location}/img/about/flink-banner.jpg">
			</div>
			<div class="col-xs-12 col-md-12 flink-title">
				<p>友情链接</p>
				<div></div>
			</div>
			<#if Session.SESSION_LOGIN_USER??>
					<div class="col-xs-12 col-md-12 wbm-friend-link">
						<div class="container" id="wbmFriendLink">
							
								<div ng-repeat="flink in flinkList" id="{{flink.id}}" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column">
									<a href="{{flink.link}}" target="_blank">
										<div class="col-xs-12 col-md-12 friend-link">
											<img ng-src="{{flink.logo}}"/>
										</div>
									</a>
									<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
										<div class="del-div" ng-click="deleteFlink(flink)"><img src="${store_location}/img/delect.png" /></div>
									</#if>
								</div>
							
							<!--<div id="order_1" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.jfz.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/jfz.png" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_2" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.sbanfu.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/sanbanfu.png" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_3" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.edaice.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/edaice.png" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_4" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.dbfen.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/dbfen.png" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_5" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.testbird.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/testbird.png" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_6" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.yunweipai.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/yunweipai.png" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_7" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.feimalv.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/feimalv.png" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_8" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://7glink.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/7GLink.jpg" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_9" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://mos.meituan.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img class="friend-link-img" src="${store_location}/img/flink/meituanyun.jpg" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_10" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.BestSDK.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img class="friend-link-img" src="${store_location}/img/flink/bestsdk.jpg" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_11" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.sdk.cn/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img class="friend-link-img" src="${store_location}/img/flink/sdk.jpg" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_12" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.superid.me/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img class="friend-link-img" src="${store_location}/img/flink/yideng.jpg" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>
							<div id="order_13" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.jisuanke.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img class="friend-link-img" src="${store_location}/img/flink/jisuanke.png" />
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="del-div" onclick="del_Url(this)"><img src="${store_location}/img/delect.png" /></div>
								</#if>
							</div>-->
						</div>

						<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
							<div class="container text-center">
								<div disabled="true" id="add_friend_link" class="col-xs-12 col-sm-offset-4 col-sm-4 fink-md-offset-4 fink-md-2 text-center friend-link-padding" href="#add" data-toggle="modal">
									<div class="col-xs-12 col-md-12 upload-link upload">
										<img src="${store_location}/img/flink/upload.png" />
									</div>
								</div>
							</div>

							<div class="container">
								<div class="col-xs-12 col-sm-12 col-md-12 div-padding">
									<div class="col-xs-6 col-sm-12 col-md-12 text-right div-padding">
										<button onclick="refresh()" class="btn btn-default">恢复排序</button>
										<button ng-click="addForm()" class="btn btn-primary">保存排序</button>
									</div>
								</div>
							</div>
						</#if>
				<#else>
					<div class="col-xs-12 col-md-12 wbm-friend-link">
						<div class="container" id="wbmFriendLink">
							
							<a href="{{flink.link}}" ng-repeat="flink in flinkList" target="_blank">
								<div class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column">
									<div class="col-xs-12 col-md-12 friend-link">
										<img ng-src="{{flink.logo}}" />
									</div>
								</div>
							</a>
							<!--<div id="order_1" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.jfz.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/jfz.png" />
								</div>
							</div>
							<div id="order_2" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.sbanfu.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/sanbanfu.png" />
								</div>
							</div>
							<div id="order_3" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.edaice.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/edaice.png" />
								</div>
							</div>
							<div id="order_4" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.dbfen.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/dbfen.png" />
								</div>
							</div>
							<div id="order_5" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.testbird.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/testbird.png" />
								</div>
							</div>
							<div id="order_6" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.yunweipai.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/yunweipai.png" />
								</div>
							</div>
							<div id="order_7" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.feimalv.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/feimalv.png" />
								</div>
							</div>
							<div id="order_8" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://7glink.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img src="${store_location}/img/flink/7GLink.jpg" />
								</div>
							</div>
							<div id="order_9" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://mos.meituan.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img class="friend-link-img" src="${store_location}/img/flink/meituanyun.jpg" />
								</div>
							</div>
							<div id="order_10" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.BestSDK.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img class="friend-link-img" src="${store_location}/img/flink/bestsdk.jpg" />
								</div>
							</div>
							<div id="order_11" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.sdk.cn/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img class="friend-link-img" src="${store_location}/img/flink/sdk.jpg" />
								</div>
							</div>
							<div id="order_12" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.superid.me/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img class="friend-link-img" src="${store_location}/img/flink/yideng.jpg" />
								</div>
							</div>
							<div id="order_13" class="col-xs-12 col-sm-4 fink-md-2 text-center friend-link-padding column" onclick="javascript:window.open('http://www.jisuanke.com/?make8')">
								<div class="col-xs-12 col-md-12 friend-link">
									<img class="friend-link-img" src="${store_location}/img/flink/jisuanke.png" />
								</div>
							</div>-->
						</div>
			</#if>
			</div>

			<div>
				<input type="hidden" id="topurl" value="${ctx}/" />
				<input type="hidden" id="reload" value="0" />
				<#if Session.SESSION_LOGIN_USER??>
					<input type="hidden" id="userType" value="${Session.SESSION_LOGIN_USER.userInfoVo.userType!""}"/>
				</#if>
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
				<script type="text/javascript" src="${ctx}/js/about/flink.js?v=${version}"></script>

	</body>

</html>