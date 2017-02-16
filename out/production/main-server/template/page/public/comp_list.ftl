<!DOCTYPE html>
<html lang="zh-CN" ng-app='serverListApp' ng-controller='serverListCtrl'>
  <head>
    <title>服务商列表 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    ${tools_includes}
    <link href="/css/comp_list.css?v=${version}" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/js/jquery.pack.js"></script>
	<script type="text/javascript" src="/js/jQuery.blockUI.js"></script>
	<script type="text/javascript" src="/js/jquery.SuperSlide.js"></script>
    <script type="text/javascript" src="/js/comp_list.js?v=${version}" ></script>
  </head>
  <body>
    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->
    <div class="body-offset"></div>  
     <div class='banner'>
    	
     </div>   
	 	     
<div class="col-md-offset-1 col-md-10">      
	<div class="breadcrumb-container col-md-4">
		  <ol class="breadcrumb">
			  <li><a href="/">首页</a></li>
			  <li><a href="#">服务商列表</a></li>
		  </ol>
	  </div>
	 <!--搜索-->
 	 <div class="row col-md-8">
    	  <div class="col-lg-4 wbm-search" style="padding-top: 0px;">
		      <div class="input-group">
		       <input type="text" class="form-control" placeholder="关键词" id="pKey">
			      <span class="input-group-btn">
			        <button class="btn btn-default" type="button" ng-click="doFuzzySearch();">搜索</button>
			      </span>
		      </div>
	      </div>
	      <div class='category' style="padding-top: 0px;">
	      	  	<select id='category'>
	      	  		<option value='2'>全部</option>
	      	  		<#if userInfo.userType==-1><option value='0'>个人</option></#if>
	      	  		<option value='1'>企业</option>
	      	  	</select>
		    </div>
     </div>

		  
     <!--选择区域-->
     <div class="col-md-12" style="margin-bottom: 10px;">
	     	<div class="col-md-12" style="background: #fafafa;;height: 50px;line-height: 50px;color: #767676;">通过筛选您可以获得更准确的结果</div>
				<div class="col-md-12" style="background: white;line-height: 50px;border-bottom:dashed 1px #ccc;height: 50px">
					<div class="col-md-2">所在区域: </div>
	         	<div class="col-md-8">
		     			<div class="col-lg-3 col-md-3" style="padding-top: 10px;padding-left:0;">
		     	 			<select class="form-control" name="province" id="province" ng-model='province' ng-options="province.name for province in provinceList" ng-change='selectProvince()'>
		     	 				<option value=''>全部</option>
		     	 			</select>
		     			</div>
		     			<div class="col-lg-3 col-md-3" style="padding-top: 10px;" >
		     	 			<select class="form-control" name="city" id="city" ng-model='city' ng-options='city.name for city in cityList' required ng-change='selectCity()'>
		     	 				<option value=''>请选择</option>
		     	 			</select>
		     			</div>		         		
		     	</div>
	        </div>
			<div class="col-md-12" style="background: white;line-height: 50px;height: 50px;">
				<div class="col-md-2">产品领域: </div>
						<ul class="col-md-8 dropDownContent" style="background: white;">
							<li style="float: left;padding: 0px 10px 0 0;"><a id='allScope' class='{{isAllSelected?"tagActive":""}}' ng-click='selectAllCaseType()'>全部领域</a></li>
		        			<li style="float: left;padding: 0px 10px 0 0;" ng-repeat='caseTag in caseTypeList'><a  class='{{caseTag.active?"tagActive":""}}'  ng-bind='caseTag.name' ng-click='selectCaseType(caseTag)'></a></li>
						</ul>
					<div class="col-md-2" onclick="showMore(this)">
						<div class="button-more">
							<label for="">更多</label>
							<div class="dropDownIcon"></div>							
						</div>
					</div>
			</div>
   </div>
</div> 
     <!--列表-->        
  	<div class="col-md-offset-1 col-md-10" style="margin-top: 20px;">   	
  		<div class='comp-list' ng-cloak>		

  		<div class='comp-list'>		

  		<div class='col-md-10 comp-list'>		
				<span ng-bind="dataList.length==0?'没有找到匹配的数据':''" style='color: #767676;'></span>
				<!-- com-panel--> 
	  			<div class='comp-panel view-slide-in' model='true' ng-repeat='server in dataList' on-finish-render-filters>
					<div class='comp-pic' ng-click='openLink(server.name)'><img ng-src='{{server.firstPic}}'/></div>
					<!--detail-->
	  				<div class='comp-detail'>
	  					<div class='comp-detail-row'>
	  						<div class='comp-name' ng-bind='server.category==0?server.name:server.companyName'></div>
	  						<div class='com-size'>
		  						<i class='fa fa-user'></i>
		  						<span>		  							
		  							{{server.companySize==1||server.companySize==undefined?"小于10人":""}}
		  							{{server.companySize==2?"10-30人":""}}
		  							{{server.companySize==3?"31-100人":""}}
		  							{{server.companySize==4?"100人以上":""}}
		  						</span>
	  					  </div>
	  					</div>
	  					<div class='comp-detail-row'>
	  						<div id="proj" class='proj-text'>
								<div class="bd">
									<ul class="infoList">
										<li ng-repeat='experience in server.employeeProjectExperience'><div class='proj-title'>{{experience.projectName}}</div></li>									
									</ul>								
								</div>
							</div>
		  				</div>
		  				<#if userInfo.userType=-1>
			  				<!--修改下架(管理员可见)--> 
			  				<div class='comp-detail-row oper-row'>
			  					<i class='fa fa-edit edit-member' ng-click="editMember(server.id)">编辑</i>
			  					<i class="fa fa-trash-o del-member" ng-click="deleteMember($index,server.id)">删除</i>
			  					<i class='fa fa-times off-member' ng-click='memberDisplay($index,server.id,server.isDisply)'>{{server.isDisply==1?"下架":"上架"}}</i>									
			  				</div>
			  				<!--修改下架(管理员可见)end-->  	
		  				</#if>		  					
	  				</div>	  						
				</div>
				<!-- com-panel end--> 			
  		</div>
  		<!-- com-list end -->
  	</div>	  		
  	<!-- paginate-container -->
  	<div class="col-md-12 container">
		<div id="paginationView" class="col-md-12 pagination-view-container" style="overflow: hidden;margin-left: 0px;padding:0;">
			<ul class="m-pagination-page" style="">	
				<li ng-hide='currentPage<showLimit+1'><a ng-click='firstPage()' data-page-index="1" ng-hide='currentPage<showLimit+1'>首页</a></li>
				<li ng-hide='currentPage<showLimit+1'><a ng-click='prevPage()'>上一页</a></li>				
				<li ng-repeat='page in showPages' class='{{page==currentPage?"active":""}}' ng-click='pagenate(page)'><a data-page-index="{{page-1}}" ng-bind='page'></a></li>
				<li class='active' ng-show='showPages.length==0'><a>1</a></li>
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
					<input id='pageJump' type="text"><button  id='jump' data-page-btn="jump" type="button" ng-click='jump()'>跳转</button>
				</div>
			</div>
			<div class="m-pagination-info" style=""><span ng-bind='startRow'></span>-<span ng-bind='currentRow'></span>条，共<span ng-bind='totalRow'></span>条</div>
		</div>
	</div> 
	<!-- paginate-container end-->	
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
	</div>
	 <!--CNZZ CODE-->
      ${cnzz_html}
     <!--END OF CNZZ CODE-->	    
  </body>
</html>
