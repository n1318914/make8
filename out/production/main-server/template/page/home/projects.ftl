<!DOCTYPE html>
<html lang="zh-CN" ng-app='projectsApp' ng-controller='projectsCtrl'>

	<head>
		<title>项目市场 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes}
				<link rel="stylesheet" href="${ctx}/css/home/projects.css?v=${version}" />
				<script type="text/javascript" src="${ctx}/js/projects.js?v=${version}"></script>
	</head>

	<body>
		<#include "../header.ftl">
			<div class="body-offset"></div>
			<!--banner-->
			<div style="background:#eff3f6 url(${store_location}/img/item-market-banner-2.png) center;min-height: 240px;">
				<div class="container banner-container">
					<div class="hidden-xs hidden-sm col-xs-12 padding">
						<div class="col-md-4 text-left padding">
							<div style="margin-top: 30px;background: url(${store_location}/img/item-market-banner-div.png) center;height: 121px;width: 222px;">
								<div class="text-center number-style">${projectNum}</div>
								<div class="text-center">累计项目总数</div>
							</div>
						</div>
						<div class="col-md-4 text-center padding">
							<div style="margin: 30px auto 0px auto;background: url(${store_location}/img/item-market-banner-div.png) center;height: 121px;width: 222px;">
								<div class="text-center number-style">${applyCount}</div>
								<div class="text-center">招募中的项目</div>
							</div>
						</div>
						<div class="col-md-4 text-right padding">
							<div style="margin:30px 0px 0px calc(100% - 222px);background: url(${store_location}/img/item-market-banner-div.png) center;height: 121px;width: 222px;">
								<div class="text-center number-style">${registersCount}</div>
								<div class="text-center">注册用户</div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 padding-all">
						<form id="form-reservation" method="POST" action="javascript:doServiceReservation();">
							<div class="col-cs-2 padding-all input-style">
								<input type="text" class="form-control" placeholder="姓名" id="name" name="name" required>
								<div id="nameWarning" class="appointmentWarning"></div>
							</div>
							<div class="col-cs-2 padding-all input-style">
								<input type="text" class="form-control" placeholder="联系方式" id="telPhone" name="telPhone"  maxlength="11" minlength="11" required isMobilePhoneNumber="true">
								<div id="telPhoneWarning" class="appointmentWarning"></div>
							</div>
							<div class="col-cs-4 padding-all input-style">
								<input type="text" class="form-control" name="demand" id="demand" placeholder="您的需求，例如：一个打车ios app预算10万，深圳。" required>
								<div id="demandWarning" class="appointmentWarning"></div>
							</div>
							<div class="col-cs-3 padding-all input-style">
								<!--<div id="submitForm" type="submit" class="reservation-btn">快速发布</div>-->
								<input class="btn reservation-btn" type="submit" value="快速发布" />
							</div>
                       </form>
					</div>
				</div>
			</div>

			<div class="col-xs-12 label-container" style="background: white;min-height: 70px;">
				<div class="container project-search-view">
					<div class="search-panel col-xs-12 col-md-8 padding">
						<button style="margin-left: 0px;" class="btn btn-link search-item {{isAllType?'btn-search-active search-item-blank':''}}" value="" ng-click='searchType()'>
							<span class="{{isAllType?'btn-search-span-active':''}}">全部</span>
						</button>
						<button class="btn btn-link search-item {{projectType.isActive?'btn-search-active search-item-blank':''}}" ng-click='searchType(projectType)' value="{{projectType.value}}" ng-model='projectType' ng-repeat='projectType in projectTypeList' ng-cloak>
							<span class="{{projectType.isActive?'btn-search-span-active':''}}" ng-bind='projectType.name'></span>
						</button>
					</div>
					<div class='breadcrumb-container col-xs-12 col-md-4 padding'>
						<div class="breadcrumb-container-div">
							<div style="margin-right: 12px" class="col-search-12">
								<input id='search' type="text" class="form-control" ng-keydown='keySearch($event)'>
							</div>
							<div class="input-group-btn">
								<button class="btn btn-info" type="button" ng-click='search()'><div><img src="${store_location}/img/search_icon.png"/></div></button>
							</div>
						</div>
					</div>

				</div>
			</div>

			<!--<div class="container" id="projectView">
				<div class="row search-result-num-panel">招募中的项目
					<p id="projectNum" class="result-num" ng-bind='totalRow'></p>个</div>
			</div>-->

			<div id="projectView" class="container container-padding">
				<div class="col-xs-12 col-sm-6 col-md-4" ng-repeat="project in dataList" ng-bloak>
					<div class="img-border">
						<div class="faith-project" ng-show="project.faithProject == 1">
							<a href="#"><img src="${ctx}/img/faith_project.png" /></a>
						</div>
						<div class="col-xs-12 col-md-12 project-bg">
							
							<div class="col-xs-12 padding text-center" style="margin-bottom: 31px" ng-cloak>
								<a ng-click="viewProject(project)"><img ng-src="{{project.abbrImagePath}}" / ></a>
							</div>
							
							<div class="col-xs-12 padding">
								<div class="col-xs-12 col-md-12 project-name" ng-bind="project.abbrName" title="{{project.name}}" ng-click="viewProject(project)"></div>
								<div class="col-xs-12 col-md-12 project-start-time padding">招募对象 ：<span class="project-span-style" title='{{project.enrollRoleListAllStr}}' ng-bind="project.enrollRoleListStr"></span></div>	
								<div class="col-xs-12 col-md-12 project-start-time padding">									
										<div style="float: left;">项目类型：</div>
										<div ng-repeat="userType in project.userType" style="float: left;">
											<span class="project-span-style" ng-show="userType==1">Andriod&nbsp;</span>
											<span class="project-span-style" ng-show="userType==3">HTML5&nbsp;</span>
											<span class="project-span-style" ng-show="userType==2">iOS&nbsp;</span>
											<span class="project-span-style" ng-show="userType==4">网站&nbsp;</span>
											<span class="project-span-style" ng-show="userType==5">微信&nbsp;</span>
											<span class="project-span-style" ng-show="userType==7">UI&nbsp;</span>
											<span class="project-span-style" ng-show="userType==8">其他&nbsp;</span>
										</div>
								</div>
								<div class="col-xs-12 col-md-12 project-start-time padding">项目周期：<span class="project-span-style" ng-bind="project.period"></span><span class="project-span-style">天</span></div>
								<div class="col-xs-12 col-md-12 project-start-time padding">浏览次数：<span class="project-span-style" ng-bind="project.viewCount"></span></div>
								<div class="col-xs-12 col-md-12 project-div-style project-start-time padding" ng-bloak>
									项目预算：<span style="color: red" ng-bind="project.budget"></span> 
									<span style="width:70px;height: 25px;text-align: center;line-height: 25px;border-radius: 5px;display: block;position: absolute;right: 0px;top: 0px;background: #ffb22b;color: white;" ng-show='project.status==0'>待启动</span>
									<span style="width:70px;height: 25px;text-align: center;line-height: 25px;border-radius: 5px;display: block;position: absolute;right: 0px;top: 0px;background: #ffb22b;color: white;" ng-show='project.status==1'>开发中</span>
									<span style="width:70px;height: 25px;text-align: center;line-height: 25px;border-radius: 5px;display: block;position: absolute;right: 0px;top: 0px;background: #ffb22b;color: white;" ng-show='project.status==2'>已完成</span>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>

			<!-- paginate-container -->
			<div class="container">
				<div id="paginationView" class="col-xs-12 pagination-view-container pageNum-style" style="overflow: hidden;margin-left: 0px;padding:0;">
					<ul class="m-pagination-page" style="">
						<!--<li ng-hide='currentPage<showLimit+1'><a ng-click='firstPage()' data-page-index="1">首页</a></li>
						<li ng-hide='currentPage<showLimit+1'><a ng-click='prevPage()'>上一页</a></li>
						<li ng-repeat='page in showPages' class='{{page==currentPage?"active":""}}' ng-click='pagenate(page)' ng-cloak>
							<a data-page-index="{{page-1}}" ng-bind='page'></a>
						</li>
						<li class='active' ng-show='showPages==0'><a>1</a></li>-->
						<li><a style="font-size: 14px" ng-click='loadMore()'>加载更多</a></li>
						<!--<li><a ng-click='lastPage()'>尾页</a></li>-->
					</ul>
				</div>
			</div>
			<!-- paginate-container end-->
			</div>

			<!--start of footer-->
			<#include "../footer.ftl">
				<!--end of footer-->
				<!---start of help docker-->
				<div id="top"></div>
				<!--end of help docker-->

				<div>
					<input type="hidden" id="url" value="${store_location}"/>
					<input type="hidden" id="topurl" value="${ctx}/" />
					<input type="hidden" id="reload" value="0" />
				</div>

				<!--CNZZ CODE-->
				${cnzz_html}
				<!--END OF CNZZ CODE-->
	</body>

</html>