<!DOCTYPE html>
<html lang="zh-CN" ng-app="useraccountApp" ng-controller="useraccountController">
  <head>
    <title>财务中心- 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
		<#include "../comm-includes.ftl">
	  ${basic_includes}
	  ${tools_includes}
	  <link rel="stylesheet" href="${ctx}/css/home/useraccount.css">
  </head>
  <body>
    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->
		<div class="body-offset"></div>
		
		<div class="container body-container">
			<!--财务信息-->
			<div class="col-xs-12 col-md-12">
				<div class="head">财务信息</div>
				<div class="content col-xs-12">
					<div class="col-xs-12 col-md-2">
						<div class="col-xs-12 col-md-12">
							<img src="${ctx}/img/head.jpg"/>
						</div>
						<div class="col-xs-12 col-md-12">
							<span>${userInfo.name}</span>
						</div>
					</div>
					<div class="col-xs-12 col-md-3 style" style="margin-top:4px">
						提现账户:<#if userInfo.accountId==-1><a href="#" data-toggle="modal" data-target="#payAccountBox">立即添加</a>
									<#else><img src="${ctx}/img/card-type${accountInfo.accountType}.png">${accountInfo.accountNum}							
									</#if>
					</div>
					<div class="col-xs-12 col-md-3 style">
						账号总收入:<span><span class="green">￥</span>${totalIncome}</span>
					</div>
					<div class="col-xs-12 col-md-3 style">
						可提现余额:<span><span class="red">￥</span>${totalAvalible}</span>
					</div>
					<div class="col-xs-12 col-md-1 style">
						<#if userInfo.accountId==-1>
							<input type="button" value="提现" class="btn btn-primary btn-functions" data-toggle="modal" data-target="#payAccountBox"/>
						<#else>
							<input type="button" value="提现" class="btn btn-primary btn-functions" data-toggle="modal" data-target="#confirmBox"/>
						</#if>
					</div>
				</div>
			</div>
			<!--财务记录-->
			<div class="col-xs-12 col-md-12 body-container">
				<div class="head">财务记录</div>
				<!--标签-->
				<div class="col-xs-12 col-md-12 content-info content-chosen-div">
					<div class="content-line">
						<div class="col-md-2 col-xs-6 active" type="0" ng-click="getRecord()">转账记录</div>
						<div class="col-md-2 col-xs-6 " type="1" ng-click="getRecord()">提现记录</div>
					</div>
		  	</div>
		  	<div class="col-md-12 col-xs-12 content-out">
					<div class="content-title col-md-12 col-xs-12">
						<div class="col-xs-12 col-md-2">时间</div>
						<div class="col-xs-12 col-md-2">金额</div>
						<div ng-show="recordType == 1" class="col-xs-12 col-md-1">状态</div>
						<div ng-show="recordType == 1" class="col-xs-12 col-md-2">到账时间</div>
						<div class="col-xs-12 col-md-5">备注</div>
					</div>
					<div ng-repeat="record in dataList" class="content-info col-xs-12" style="border-top: 1px solid #E6E8EA;">
						<div class="col-xs-12 col-md-2" ng-bind="record.date"></div>
						<div class="col-xs-12 col-md-2" ng-bind="record.amount"></div>
						<div ng-show="recordType == 1" class="col-xs-12 col-md-1" ng-bind="record.status"></div>
						<div ng-show="recordType == 1" class="col-xs-12 col-md-2" ng-bind="record.confirmTime"></div>
						<div class="col-xs-12 col-md-5" ng-bind="record.comment"></div>
					</div>
				</div>
			
				<!-- paginate-container -->
				<div class="container col-md-12 col-xs-12 body-container">
					<div id="paginationView" class="col-md-12 pagination-view-container" style="overflow: hidden;margin-left: 0px;padding:0;">
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
				<!-- paginate-container end-->
				</div>
		</div>
		
	<!-- 模态框（用户提现输入框） -->
	<div class="modal fade" id="confirmBox" tabindex="-1" role="dialog" 
	   aria-labelledby="confirmBox" aria-hidden="true">
	   <div class="modal-dialog">
	      <div class="modal-content">
	      	<form action="javascript:getCash()" id="checkAccount">
	         <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times; </button>
	            <h4 class="modal-title" id="confirmBox">提现申请</h4>
	         </div>
	         <div class="modal-body" style="overflow:auto;padding-bottom: 30px;">
	         <div class="col-xs-12 " style="font-size: 16px;color: #3487bd;">金额:</div>
	         <div class="col-xs-12 ">
	         	<input id="amount" name="amount" type="text" class="form-control">
	         </div>
	         <p id="accountErr"></p>
	         </div>
	         <p id="tip"><span style="color:red;">*</span>系统将会在3个工作日内处理您的申请</p>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            <!--<button id="confirm-btn" type="button" class="btn btn-primary" onclick="entryDevelopment()">确认</button>-->
	            <input type="submit" class="btn btn-primary" value="确认" />
	         </div>
	         </form>
	      </div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>
		
		<!-- 模态框（用户输入支付宝、银行账号） -->
	<div class="modal fade" id="payAccountBox" tabindex="-1" role="dialog" 
	   aria-labelledby="payAccountBox" aria-hidden="true">
	   <div class="modal-dialog">
	      <div class="modal-content">
	      	<form action="javascript:updatePayAccount()" id="checkPayAccount">
	         <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times; </button>
	            <h4 class="modal-title" id="payAccountBox">完善收款信息</h4>
	         </div>
	         <div class="modal-body" style="overflow:auto;padding-bottom: 30px;">
	         <div class="col-xs-12 " style="font-size: 16px;color: #3487bd;">请输入您的支付宝账号:</div>
	         <div class="col-xs-12 ">
	         	<input id="payAccount" name="payAccount" type="text" class="form-control">
	         </div>
	         <p id="payAccountErr"></p>
	         </div>
	         <p id="tip"><span style="color:red;">*</span>目前只支持支付宝账号，录入后不可修改请谨慎填写</p>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            <input type="submit" class="btn btn-primary" value="确认" />
	         </div>
	         </form>
	      </div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>
	
	
  <div class="footer-offset"></div>
  <!--start of footer-->
  <input type="hidden" id="topurl" value="${ctx}/"/>
  <#include "../footer.ftl">
  <!--end of footer-->
  <!---start of help docker-->
  <div id="top"></div>
  <!--end of help docker-->
  <script type="text/javascript" src="${ctx}/js/home/useraccount.js?v=${version}"></script>
	<!--CNZZ CODE-->
	  ${cnzz_html}
	<!--END OF CNZZ CODE-->
  </body>
</html>
