<!DOCTYPE html>
<html lang="zh-CN" ng-app='projectsApp' ng-controller='projectsCtrl'>
  <head>
    <title>项目审核 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no" />
    <link rel="stylesheet" href="${ctx}/css/admin/requests_review.css?v=${version}" />
    <script type="text/javascript" src="${ctx}/js/admin/requests_review.js?v=${version}"></script>
  </head>
  <body>
    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->
    <div class="body-offset"></div>

    <!--banner-->
    <div class="col-xs-12 col-md-12 banner-div"></div>
    <!--banner end-->

    <!-- <div class="container" style="overflow: hidden;">
    	<div class="breadcrumb-container breadcrumb-container-margin padding-left">
			  <ol class="breadcrumb padding-left">
				  <li><a href="/">首页</a></li>
				  <li><a href="#">项目审核</a></li>
			  </ol>
		  </div>
    </div> -->

		<div class="container project-screen text-center">
			<div class="row col-sm-12 col-md-12 project-type">
				<div class="col-sm-2 col-md-1 projectLeft">
					<div class="col-sm-12 col-md-12 project-bottom">类型:</div>
				</div>
				<div class="col-sm-10 col-md-11 projectRight">
					<div class="col-xs-12 col-sm-3 col-md-1 text-center project-bottom">
						<button class="btn btn-padding search-item {{isAllType?'btn-primary search-item-blank':''}}" value="" ng-click='searchType()'>全部</button>
					</div>
					<div class="col-xs-12 col-sm-3 col-md-1 text-center project-bottom" ng-repeat='projectType in projectTypeList'>
						<button class="btn btn-padding search-item {{projectType.isActive?'btn-primary search-item-blank':''}}" ng-click='searchType(projectType)' value="{{projectType.value}}" ng-model='projectType' ng-bind='projectType.name'
						ng-cloak></button>
					</div>
				</div>
			</div>
			<div class="row col-sm-12 col-md-12 project-state">
				<div class="col-sm-2 col-md-1 projectLeft">
					<div class="col-sm-12 col-md-12 project-bottom">状态:</div>
				</div>
				<div class="col-sm-10 col-md-11 projectRight">
					<div class="col-xs-12 col-sm-3 col-md-1 text-center project-bottom">
						<button class="btn btn-padding search-item {{isAllStatus?'btn-primary search-item-blank':''}}" value="" ng-click='findStatus()'>全部</button>
					</div>
					<div class="col-xs-12 col-sm-3 col-md-1 text-center project-bottom" ng-repeat='status in statusList'>
						<button class="btn btn-padding search-item {{status.isActive?'btn-primary search-item-blank':''}}" value="0" ng-click='findStatus(status)' ng-bind='status.name'></button>
					</div>
				</div>
			</div>

      <div class="row col-sm-12 col-md-12 project-state">
				<div class="col-sm-2 col-md-1 projectLeft">
					<div class="col-sm-12 col-md-12 project-bottom">顾问:</div>
				</div>
				<div class="col-sm-10 col-md-11 projectRight">
					<div class="col-xs-12 col-sm-3 col-md-1 text-center project-bottom">
						<button class="btn btn-padding search-item {{isAllConsultants?'btn-primary search-item-blank':''}}" value="" ng-click='searchConsultants()'>全部</button>
					</div>
					<div class="col-xs-12 col-sm-3 col-md-2 text-center project-bottom" ng-repeat='consultant in consultantList'>
						<button class="btn btn-padding search-item {{consultant.isActive?'btn-primary search-item-blank':''}}" value="0" ng-click='searchConsultants(consultant)' ng-bind='consultant.name'></button>
					</div>
				</div>
			</div>
		</div>

    <div class="container">
			<div class="col-xs-12 padding">
				<div class="col-xs-6 padding">
					<input id="keywords" type="text" style="border-radius: 4px 0px 0px 4px" class="form-control" ng-keyup="keySearch($event);"/>
				</div>
				<div class="col-xs-6 padding">
					<button class="btn btn-primary" style="border-radius: 0px 4px 4px 0px" ng-click="keySearch();">搜索</button>
				</div>
			</div>
    </div>

    <div class="container project-number-div">
    	<div class="breadcrumb-container breadcrumb-container-margin padding-left">
				<span class="span-project-number">相关项目</span>
				<span id="projectNum" class="project-number" ng-bind='totalRow'></span>
				<span class="span-project-number">个</span>
		  </div>
    </div>

		<div class="container">
			<div class="col-md-12 project-view" ng-repeat='project in dataList' ng-cloak>
				<div class="faith-project" ng-show="project.faithProject == 1"><a href="#"><img src="${ctx}/img/faith_project.png"/></a></div>
				<!-- <div class="col-xs-12 col-sm-1 col-md-1 project-view-pic">
					<i class="andriod-icon" ng-show="project.type==1"></i>
					<i class="html5-icon" ng-show="project.type==3"></i>
					<i class="ios-icon" ng-show="project.type==2"></i>
					<i class="website-icon" ng-show="project.type==4"></i>
					<i class="weixin-icon" ng-show="project.type==5"></i>
					<i class="ui-icon" ng-show="project.type==7"></i>
					<i class="others-icon" ng-show="project.type==8"></i>
				</div> -->
				<div class="col-xs-12 col-sm-11 col-md-11">
					<div class="col-xs-12 col-sm-12 col-md-12 project-view-title">
						<a target="_blank" ng-click='viewProject(project)' style="cursor: pointer;text-decoration: none;font-size:16px;">{{project.name | decodeStr}}</a>
            <a target="_blank" ng-click='viewProgress(project)' style="cursor: pointer;text-decoration: none;color:red;">&nbsp;&nbsp;&nbsp;&nbsp;查看进度&#62;&#62;</a>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-12 project-view-details">
						<div class="col-md-2 padding-left">
							<p class="text-default">预算：<span ng-bind='project.budget'></span></p>
						</div>
						<div class="col-md-2 padding-left">
							<p class="text-default">项目周期:<span ng-bind='project.period'></span>天</p>
						</div>
						<div class='col-md-2 padding-left'>
              <p class="text-default">项目状态:
                <span ng-show='project.status==-1'>待审核</span>
                <span ng-show='project.status==0'>待启动</span>
                <span ng-show='project.status==1'>开发中</span>
                <span ng-show='project.status==2'>已完成</span>
                <span ng-show='project.status==3'>未通过</span>
                <span ng-show='project.status==4'>已关闭</span>
              </p>
							<!-- <span class="badge badge-info project-view-btn" ng-show='project.status==-1'>待审核</span>
							<span class="badge badge-primary project-view-btn" ng-show='project.status==0'>待启动</span>
							<span class="badge badge-success project-view-btn" ng-show='project.status==1'>开发中</span>
							<span class="badge badge-warning project-view-btn" ng-show='project.status==2'>已完成</span>
							<span class="badge badge-danger project-view-btn" ng-show='project.status==3'>未通过</span> -->
						</div>
            <div class="col-md-3 padding-left">
              <p class="text-default">顾问:<span ng-bind='project.consultantName'></span></p>
            </div>
            <div class="col-md-3 padding-left">
              <p class="text-default">创建日期：<span ng-bind='project.createTime'></span></p>
            </div>
			<div class="col-md-3 padding-left" style="margin-top:-15px;">
              <p class="text-default">最后更新时间：<span ng-bind='project.latestUpdateTime'></span></p>
            </div>
					</div>
				</div>
			</div>
		</div>

		<div class="container">
			<div id="paginationView" class="col-md-12 pagination-view-container" ng-cloak>
						<ul class="m-pagination-page" style="">
							<li ng-hide='currentPage<showLimit+1'><a ng-click='firstPage()' data-page-index="1">首页</a></li>
							<li ng-hide='currentPage<showLimit+1'><a ng-click='prevPage()'>上一页</a></li>
							<li ng-repeat='page in showPages' class='{{page==currentPage?"active":""}}' ng-click='pagenate(page)' ng-cloak>
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

  <!--start of footer-->
  <#include "../footer.ftl">
  <!--end of footer-->
  <!---start of help docker-->
  <div id="top"></div>
  <!--end of help docker-->

   <div>
   	<input type="hidden" id="topurl" value="${ctx}/"/>
   	<input type="hidden" id="reload" value="1"/>
   	<input type="hidden" id="footerfixed" value="0"/>
   </div>
  </body>
</html>
