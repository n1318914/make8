<!DOCTYPE html>
<html lang="zh-CN" ng-app='userInfoImportApp' ng-controller='userInfoListCtrl'>

	<head>
		<title>码客库 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes}
				<link rel="stylesheet" href="${ctx}/css/admin/user_info_import.css?v=${version}" />
				<script type="text/javascript" src="${ctx}/js/user_info_import.js?v=${version}"></script>
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->
			<div class="container">
				<div class="col-xs-12 col-sm-2 col-md-2 body-tab">
					<div class="col-md-12 col-sm-12 col-xs-12 console text-center">控制台</div>
					<div class="col-sm-12 col-xs-12 col-md-12 tab-bg">
						<div id="make" class="col-md-12 col-sm-12 col-xs-12  text-center" onclick="bodyTab(this.id)">码客库</div>
						<div id="programmer" class="col-md-12 col-sm-12 col-xs-12 active text-center" onclick="bodyTab(this.id)">程序员库</div>
					</div>
				</div>
				<!--码客库-->
				<div id="makeLibrary" class="col-xs-12 col-sm-10 col-md-10">
					<div class="mainbody min_body_height">
						<div class="col-xs-4 col-md-2 padding-right search_div">
							<div class="form-control select_div" id="city" onclick="cityFocus(this)">
								<div>地区</div>
								<div class="glyphicon glyphicon-play select_icon hidden_xxs"></div>
							</div>
							<div class="options_div">
								<ul class="options_ul">
								<#list listRegion as tag>
							      <#if tag.parentId==0>
									<li onclick="addLabel(this)" onmouseover="secondOptions(this)" onmouseout="secondOptions(this)">
										<span value="${tag.id}">${tag.name}</span>
										<ul class="options_ul_ul">
											<#list tag.cites as city>
												<li onclick="secondAddLabel(this)" value="${city.id}">${city.name}</li>
											</#list>
										</ul>
									</li>
								  </#if>
								</#list>
								</ul>
							</div>
						</div>
						<div class="col-xs-4 col-md-2 padding-right search_div">
							<div class="form-control select_div" id="type" onclick="cityFocus(this)">
								<div>类型</div>
								<div class="glyphicon glyphicon-play select_icon hidden_xxs"></div>
							</div>
							<div class="options_div">
								<ul class="options_ul">
									<#list listDictItem as tag>
							        	<#if tag.type=="cando">
							  	  			<li value="${tag.value}" onclick="addLabel(this)">${tag.name}</li>
							  	  		</#if>
									</#list>
								</ul>
							</div>
						</div>
						<div class="col-xs-4 col-md-2 padding-right search_div">
							<div class="form-control select_div" id="technology" onclick="cityFocus(this)">
								<div>技术</div>
								<div class="glyphicon glyphicon-play select_icon hidden_xxs"></div>
							</div>
							<div class="options_div">
								<ul class="options_ul">
									<#list listDictItem as tag>
							        	<#if tag.type=="ability">
							  	  			<li value="${tag.value}" onclick="addLabel(this)">${tag.name}</li>
							  	  		</#if>
									</#list>
								</ul>
							</div>
						</div>
						<div class="col-xs-12 col-md-6 search_div">
							<div class="col-xs-10 col-sm-10 col-md-10 padding-right padding-left">
								<!-- <input onkeyup="seachOtherAbility()" type="text" id="otherAbility" class="form-control right-radius" placeholder="技术、手机、email、用户名"/> -->
								<input type="text" id="otherAbility" class="form-control right-radius" placeholder="技术、手机、email、用户名"/>
							</div>
							<div class="col-xs-2 col-sm-2 col-md-2 padding-left">
								<button class="btn btn-info left-radius" onclick="seachOtherAbility()">
									<span class="glyphicon glyphicon-search"></span>
								</button>
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12" id="selectLabel">
							<div id="cityId_div" class="select_label" onmouseover="selectLabelHover(this)" onmouseout="selectLabelHover(this)">
								<div><i>地区：</i><span id="cityId"></span></div>
								<div onclick="selectDelete(this)" class="glyphicon glyphicon-remove"></div>
							</div>
							<div id="cando_div" class="select_label" onmouseover="selectLabelHover(this)" onmouseout="selectLabelHover(this)">
								<div><i>类型：</i><span id="cando"></span></div>
								<div onclick="selectDelete(this)" class="glyphicon glyphicon-remove"></div>
							</div>
							<div id="ability_div" class="select_label" onmouseover="selectLabelHover(this)" onmouseout="selectLabelHover(this)">
								<div><i>技术：</i><span id="ability"></span></div>
								<div onclick="selectDelete(this)" class="glyphicon glyphicon-remove"></div>
							</div>
						</div>

						<#if projectVo?? && Session.SESSION_LOGIN_USER.userInfoVo.userType == 2>
							<div class="col-md-12 col-sm-12 col-xs-12 padding" style="margin-top: 15px;">
								<div class="col-md-2 col-sm-2 col-xs-12 padding-right"><button class="btn-push" onclick="pushProject(0)">推送选中开发者</button></div>
								<div class="col-md-2 col-sm-2 col-xs-12 padding-right"><button class="btn-push" onclick="pushProject(1)">推送该页开发者</button></div>
								<div class="col-md-2 col-sm-2 col-xs-12 padding-right"><button class="btn-push" onclick="pushProject(2)">推送全部开发者</button></div>
								<div class="col-md-12 col-sm-12 col-xs-12" style="margin-top: 49px;">
									<div class="col-md-12 col-sm-12 col-xs-12 padding" style="height: 1px;border-bottom: dashed 1px #ccc;"></div>
								</div>
							</div>
						</#if>

						<div class="col-xs-12 col-sm-12 col-md-12 info-margin-top">
							<div id="view">
								<!--data-->
							</div>

							<div class="col-xs-12 col-sm-12 col-md-12 page">
								<div class="page_div_style" id="homePage" onclick="Page(this)">首页</div>
								<div class="page_div_style" id="lastPage" onclick="Page(this)">上一页</div>
								<div id="pageNum">

								</div>
								<div class="page_div_style" id="nextPage" onclick="Page(this)">下一页</div>
								<div class="page_div_style" id="theLastPage" onclick="Page(this)">尾页</div>
								<!--<div id="selectPage">
									<select class="form-control selectPage">
										<option value="">10</option>
										<option value="">20</option>
										<option value="">30</option>
									</select>
								</div>-->
								<div id="jumpPage">
									<div>
										<input class="form-control jumpPageInput" id="targetPage" maxlength="5" type="text"/>
									</div>
									<div>
										<button class="btn btn-primary jumpPageBtn" id="jumpPageBtn" onclick="Page(this)">跳转</button>
									</div>
								</div>
								<div class="pageInfo">
									<span id="startPage">1</span>-<span id="endPage">10</span>条,
								</div>
								<div class="pageInfo">
									<div style="float: left;">共</div><div id="count"></div>条
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--程序员库-->
				<div id="programmerLibrary" class="col-xs-12 col-sm-10 col-md-10">
					<div class='mainbody' ng-show='isListActive'>
						<div class="row head">
							<div class='col-md-6'>
								<input id='search' type="text" class="form-control" placeholder="搜索姓名、区域、手机、邮箱、微信、QQ、微博" ng-keydown='keySearch($event)'>
							</div>
							<div class="col-md-6">
								<div class="input-group">
									<input id='skill' type="text" class="form-control" placeholder="搜索技能" ng-keydown='keySearch($event)'>
									<span class="input-group-btn">
                            <button class="btn btn-info" type="button" ng-click='search()'><span class="glyphicon glyphicon-search"></span></button>
									</span>
								</div>
							</div>
							<div class='col-md-3'>
							</div>
						</div>
						<div class='col-md-12' ng-bind="dataList.length==0?'没有找到匹配的数据':''" style='color: #767676;padding-top:15px;'></div>
						<div class='row' ng-repeat='userImport in dataList'>
							<div class='col-md-12 field'>
								<span class='username' ng-bind="userImport.name==''?'佚名':userImport.name"></span>
								<div class='tagbo mate_ajax_link ui compact tag label' ng-bind="userImport.location==''?'暂无':userImport.location"></div>
								<div class='tagbo mate_ajax_link ui label compact' ng-bind="userImport.status==''?'暂无':userImport.status"></div>
								<div class='tagbo mate_ajax_link ui label compact' ng-bind="userImport.work==''?'暂无':userImport.work"></div>
								<button class='btn btn-primary' style='float:right;' ng-click='view(userImport.id)'>查看</button>

							</div>
							<div class='col-md-12 field' style='color:rgba(0, 0, 0, 0.6);'>
								<span style='padding-right:15px;'><i class="fa fa-mobile fa-fw fa-lg" style='color:black;'></i><span ng-bind="userImport.mobile==''?'暂无':userImport.mobile">18932470315</span></span>
								<span style='padding-right:15px;'><i class="fa fa-envelope fa-fw" style='color:orange;'></i><span ng-bind="userImport.mail==''?'暂无':userImport.mail">345836586@qq.com</span></span>
								<span style='padding-right:15px;'><i class="fa fa-weibo fa-fw" style='color:red;'></i><span ng-bind="userImport.weibo==''?'暂无':userImport.weibo">asw21548</span></span>
								<span style='padding-right:15px;'><i class="fa fa-weixin fa-fw" style='color:green;'></i><span ng-bind="userImport.weixin==''?'暂无':userImport.weixin">sww5488</span></span>
								<span style='padding-right:15px;'><i class="fa fa-qq fa-fw" style='color:#337ab7;'></i><span ng-bind="userImport.qq==''?'暂无':userImport.qq">15485485</span></span>
							</div>
							<div class='col-md-12 field' ng-bind="userImport.introduction==''?'暂无简介':userImport.introduction">

							</div>
							<div class='col-md-12 field introduction' ng-bind-html="userImport.exp|to_trusted">

							</div>
							<div class='col-md-12' style='border-bottom:1px solid #ccc;'>
							</div>
						</div>
						<!-- paginate-container -->
						<div class="col-md-12 container">
							<div id="paginationView" class="col-md-12 pagination-view-container" style="overflow: hidden;margin-left: 0px;padding:0;">
								<ul class="m-pagination-page" style="">
									<li ng-hide='currentPage<showLimit+1'><a ng-click='firstPage()' data-page-index="1">首页</a></li>
									<li ng-hide='currentPage<showLimit+1'><a ng-click='prevPage()'>上一页</a></li>
									<li ng-repeat='page in showPages' class='{{page==currentPage?"active":""}}' ng-click='pagenate(page)'>
										<a data-page-index="{{page-1}}" ng-bind='page'></a>
									</li>
									<li class='active' ng-show='showPages==0'><a>1</a></li>
									<li><a ng-click='nextPage()'>下一页</a></li>
									<li><a ng-click='lastPage()'>尾页</a></li>
								</ul>
								<div class="m-pagination-size" style="">
									<select data-page-btn="size" ng-model='pageSize' ng-change='setPageSize()'>
										<option value="8" ng-selected='true'>8</option>
										<option value="12">12</option>
										<option value="20">20</option>
									</select>
								</div>
								<div class="m-pagination-jump" style="">
									<div class="m-pagination-group">
										<input id='pageJump' type="text">
										<button id='jump' data-page-btn="jump" type="button" ng-click='jump()'>跳转</button>
									</div>
								</div>
								<div class="m-pagination-info" style=""><span ng-bind='startRow'></span>-<span ng-bind='currentRow'></span>条，共<span ng-bind='totalRow'></span>条</div>
							</div>
						</div>
						<!-- paginate-container end-->
					</div>
					<div class='container mainbody detailBody' ng-show='!isListActive' style='padding:50px;'>
						<div class='col-md-12'>
							<button class='btn btn-primary' style='float:right;' ng-click='back()'>返回</button>
						</div>
						<div class='row'>
							<div class='col-md-12 username' ng-bind="userImportVo.name==''?'佚名':userImportVo.name"></div>
						</div>
						<div class='row'>
							<div class='col-md-12 title'>职能</div>
							<div class='col-md-12 info' ng-bind="userImportVo.work==''?'暂无':userImportVo.work"></div>
						</div>
						<div class='row'>
							<div class='col-md-12 title'>工作状态</div>
							<div class='col-md-12 info' ng-bind="userImportVo.status==''?'暂无':userImportVo.status"></div>
						</div>
						<div class='row'>
							<div class='col-md-12 title'>地区</div>
							<div class='col-md-12 info' ng-bind="userImportVo.location==''?'暂无':userImportVo.location"></div>
						</div>
						<div class='row'>
							<div class='col-md-12 title'>履历信息</div>
							<div class='col-md-12 info' ng-bind-html="userImportVo.exp.trim()==''?'暂无':userImportVo.exp|to_trusted"></div>
							<div class='col-md-12 info'><span ng-bind="userImportVo.exp.trim()=='&nbsp;'?'暂无履历':''"></span></div>
						</div>
						<div class='row'>
							<div class='col-md-12 title'>技能评级</div>
							<div class='col-md-12 info' ng-repeat="skill in userImportVo.skillList">
								<span ng-bind='skill.name' style='font-weight:bolder;'></span>
								<span ng-bind='skill.level'></span>
							</div>
							<div class='col-md-12 info'><span ng-bind="userImportVo.skillList.length==0?'暂无技能':''"></span></div>
						</div>
						<div class='row'>
							<div class='col-md-12 title'>项目作品</div>
							<div class='col-md-12 info' ng-repeat='job in userImportVo.jobList'>
								<div><span ng-bind='job.name' style='font-weight:bolder;'></span></div>
								<div>职责:<span ng-bind='job.duty'></span></div>
								<div>描述:<span ng-bind='job.description'></span></div>
								<div><a href='' ng-click='viewJobLink(job.href)'>查看</a></div>
							</div>
						</div>
						<div class='row'>
							<div class='col-md-12 title'>GIT项目</div>
							<div class='col-md-12 info' ng-repeat='git in userImportVo.gitList'>
								<div ng-bind='git.projectName' style='font-weight:bolder;'></div>
								<span ng-bind='git.projectDesc'></span>
								<span><a href='' ng-click='viewJobLink(git.href)'>查看</a></span>
								<hr>
							</div>
							<div class='col-md-12 info'><span ng-bind="userImportVo.gitList.length==0?'暂无GIT项目':''"></span></div>
						</div>
						<div class='row'>
							<div class='col-md-12 title'>知乎贡献</div>
							<div class='col-md-12 info' ng-repeat='zhihu in userImportVo.zhihuList' style='padding-top:15px;'>
								<div style='padding:15px;'><span ng-bind='zhihu.name' style='font-weight:bolder;'></span></div>
								<div style='padding:15px;'>问题:<span ng-bind='zhihu.header'></span></div>
								<div style='padding:15px;'>解决:<span ng-bind='zhihu.description'></span></div>
								<div style='padding:15px;'><a href='' ng-click='viewJobLink(zhihu.zhihuHref)'>查看</a></div>
							</div>
							<div class='col-md-12 info'><span ng-bind="userImportVo.zhihuList.length==0?'暂无知乎信息':''"></span></div>
						</div>
						<div class='row'>
							<div class='col-md-12 title'>stackoverflow贡献</div>
							<div class='col-md-12 info' ng-repeat='stack in userImportVo.stackList' style='padding-top:15px;'>
								<div style='padding:15px;'><span ng-bind='stack.name' style='font-weight:bolder;'></span></div>
								<div style='padding:15px;'>话题:<span ng-bind='stack.header'></span></div>
								<div style='padding:15px;'><a href='' ng-click='viewJobLink(stack.href)'>查看</a></div>
							</div>
							<div class='col-md-12 info'><span ng-bind="userImportVo.zhihuList.length==0?'暂无stackoverflow信息':''"></span></div>
						</div>
					</div>
				</div>
			</div>

			<!--start of footer-->
			<#include "../footer.ftl">
				<!--end of footer-->
				<!---start of help docker-->
				<div id="top"></div>
				<!--end of help docker-->

				<div>
					<input type="hidden" id="topurl" value="${ctx}/" />
					<input type="hidden" id="reload" value="1" />
					<#if projectVo?? && Session.SESSION_LOGIN_USER.userInfoVo.userType == 2>
						<input type='hidden' id='projectId' value='${projectVo.id!''}' />
					</#if>
				</div>
	</body>

</html>
