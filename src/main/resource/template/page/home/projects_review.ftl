<!DOCTYPE html>
<html lang="zh-CN" ng-app='projectsReviewApp' ng-controller='projectsReviewCtrl'>
  <head>
    <title>项目审核 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    <link rel="stylesheet" href="${ctx}/css/home/projects_review.css?v=${version}" />
    <script type="text/javascript" src="${ctx}/js/home/project_review.js?v=${version}"></script>
  </head>
  <body>
    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->
    <div class="body-offset"></div>
    <div style="padding-top:40px;"></div>

      <!--start of search panel-->
      <div class="container">
        <div class="label-container" style="background: white;min-height: 70px;">
  				<div class="container project-search-view">
  					<div class="search-panel col-xs-12 col-md-8 padding">
  						<button style="margin-left: 0px;" class="btn btn-link search-item {{isAllType?'btn-search-active search-item-blank':''}}" value="" ng-click='searchType();'>
  							<span class="{{isAllType?'btn-search-span-active':''}}">全部</span>
  						</button>
  						<button class="btn btn-link search-item {{projectTypeItem.isActive?'btn-search-active search-item-blank':''}}" ng-click='searchType(projectTypeItem)' ng-repeat='projectTypeItem in projectTypeList' ng-cloak>
  							<span class="{{projectTypeItem.isActive?'btn-search-span-active':''}}" ng-bind='projectTypeItem.name'></span>
  						</button>
  					</div>

            <div class="search-panel col-xs-12 col-md-8 padding">
              <button style="margin-left: 0px;" class="btn btn-link search-item {{isAllStatus?'btn-search-active search-item-blank':''}}" value="" ng-click='searchStatus()'>
                <span class="{{isAllStatus?'btn-search-span-active':''}}">全部</span>
              </button>
              <button class="btn btn-link search-item {{projectType.isActive?'btn-search-active search-item-blank':''}}" ng-click='searchStatus(projectStatus)' ng-repeat='projectStatus in projectStatusList' ng-cloak>
                <span class="{{projectStatus.isActive?'btn-search-span-active':''}}" ng-bind='projectStatus.name'></span>
              </button>
            </div>
            <!--keywords search-->
            <div class='breadcrumb-container col-xs-12 col-md-4 padding'>
              <div class="breadcrumb-container-div">
                <div style="margin-right: 12px" class="col-search-12">
                  <input id='keywords' class="search-keywords-input" type="text" class="form-control" ng-keyup="searchByKeywords($event);">
                </div>
                <div class="input-group-btn">
                  <button class="btn btn-info" type="button" ng-click='searchByKeywords();'><div><img src="${store_location}/img/search_icon.png"/></div></button>
                </div>
              </div>
            </div>
  				</div>
  			</div>
      </div>
      <!--end of search panel-->

    <div class="container">
      <div class="search-result-num-panel">
        相关项目<p id="projectNum" class="result-num" ng-bind="totalRows"></p>个
      </div>
   </div>

    <div class="container">
	    <div id="projectView">
	    	<ul class="list-group project-list">
	    	  <li class="list-group-item project project-model" ng-repeat="project in projectList">
			      <div class="container-fluid">
				        <div class="col-md-12 navbar-header">
				        	<div class='col-md-10'>
				          		<Strong class='navbar-brand projectName view' style='color:#337ab7;padding-left:0;' ng-bind="project.name" ng-click="viewProjectDetail(project.id)"></Strong>
				          	</div>
				        </div>
				        <div class='col-md-12'>
				        	<div class='col-md-3'>
				        		<span>创建人:</span>
				        		<span class='creator' ng-bind="project.creatorName"></span>
				        	</div>
				        	<div class='col-md-3'>
				        		<span>创建时间:</span>
				        		<span class='createTime' ng-bind="project.createTime"></span>
				        	</div>
				        	<div class='col-md-3'>
				        		<span>联系方式:</span>
				        		<span class='mobile' ng-bind="project.mobile"></span>
				        	</div>
				        	<div class='col-md-3'>
				        		<span>预算:</span>
				        		<span class='budget' ng-bind="project.budget"></span>
				        	</div>
                </div>
                <div class='col-md-12'>
				        	<div class='col-md-3'>
				        		<span>项目状态:</span>
				        		<span class='status' ng-bind="project.statusTag"></span>
				        	</div>
				        	<div class='col-md-3'>
				        		<span>项目类型:</span>
				        		<span class='type' ng-bind="project.displayType"></span>
				        	</div>
							  <div class='col-md-3'>
				        		<span>分配顾问:</span>
				        	<span class='consultantName' ng-bind='project.consultantName'></span>
				        	</div>
				        	<div class='col-md-3'>
				        		<span>项目周期:</span>
				        		<span class='startTime' ng-bind='project.period'></span>天
				        	</div>
							<div class='col-md-3'>
				        		<span>最后更新时间:</span>
				        		<span class='startTime' ng-bind='project.latestUpdateTime'></span>
				        	</div>
				        </div>
              </div>
			      </div>
			  </li>
			</ul>
	  </div>

    <!-- paginate-container -->
    <div class="container">
	 	 <div id="paginationView" class="pagination-view-container"></div>
    </div>
    <!-- paginate-container end-->

  <div>
    <input type="hidden" id="topurl" value="${ctx}/" />
  </div>

  <!--start of footer-->
  <#include "../footer.ftl">
  <!--end of footer-->
  <!---start of help docker-->
  <div id="top"></div>
  <!--end of help docker-->
  </body>
</html>
