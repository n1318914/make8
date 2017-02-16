<!DOCTYPE html>
<html lang="zh-CN" ng-app='dataAnylizeApp' ng-controller='dataAnylizeCtrl'>
  <head>
    <title>用户认证审核 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    ${tools_includes}
    <script type="text/javascript" src="/js/dataAnylize.js?v=${version}" ></script>
    <script type="text/javascript" src="/js/Chart.js?v=${version}" ></script>
    <style>
    	.mainbody{
    		padding:25px;margin-top:100px;background-color:#fff;border-radius:2px;box-shadow:0 5px 5px #ccc;-webkit-box-shadow:0 5px 5px #ccc;
    	}
    	.field{
    		padding:15px;
    		float:left;
    	}
    	.tagbo{
    		border-radius:3px;
    		box-shadow:0 5px 5px #ccc;-webkit-box-shadow:0 5px 5px #ccc;
    		background-color:#
    	}
    	
    	.ui.label {
    	  cursor:pointer;
		  display: inline-block;
		  vertical-align: baseline;
		  line-height: 1;
		  margin: 0em 0.125em;
		  background-color: #e8e8e8;
		  border-color: #e8e8e8;
		  background-image: none;
		  padding: 0.6em 0.8em;
		  color: rgba(0, 0, 0, 0.6);
		  text-transform: none;
		  font-weight: bold;
		  border-radius: 0.2857rem;
		  box-sizing: border-box;
		  -webkit-transition: background 0.2s ease;
		  transition: background 0.2s ease;
		}
		.ui.compact.segment {
		  display: table;
		}
    	/*-------------------
         Tag
		--------------------*/
		
		.ui.tag.labels .label,
		.ui.tag.label {
		  margin-left: 1em;
		  position: relative;
		  padding-left: 1.5em;
		  padding-right: 1.5em;
		  border-radius: 0em 0.2857rem 0.2857rem 0em;
		}
		
		.ui.tag.labels .label:before,
		.ui.tag.label:before {
		  position: absolute;
		  -webkit-transform: translateY(-50%) translateX(50%) rotate(-45deg);
		  -ms-transform: translateY(-50%) translateX(50%) rotate(-45deg);
		  transform: translateY(-50%) translateX(50%) rotate(-45deg);
		  top: 50%;
		  right: 100%;
		  content: '';
		  background-color: #e8e8e8;
		  background-image: none;
		  width: 1.56em;
		  height: 1.56em;
		  -webkit-transition: background 0.2s ease;
		  transition: background 0.2s ease;
		}
		
		.ui.tag.labels .label:after,
		.ui.tag.label:after {
		  position: absolute;
		  content: '';
		  top: 50%;
		  left: -0.25em;
		  margin-top: -0.25em;
		  background-color: #ffffff !important;
		  width: 0.5em;
		  height: 0.5em;
		  box-shadow: 0 -1px 1px 0 rgba(0, 0, 0, 0.3);
		  border-radius: 500rem;
		}
		.username{
			font-weight:bolder;
			font-size:1.2em;
			cursor:pointer;
			color:#337ab7;
		}
		.username:hover{
			transition:color 0.2s ease;
		}
		.introduction{
			white-space:nowrap; 
			text-overflow:ellipsis; 
			-o-text-overflow:ellipsis; 
			overflow: hidden; 
		}
    </style>
  </head>
  <body>
	<!--header-->
	<#include "../header.ftl">
	<!--end of header-->
	<div class="body-offset"></div>
	 <div class='container mainbody' style='min-height:80vh;'>
	 	<!--日期-->
	 	<div class='col-md-12'>
	 		<div class='col-md-2'>
	 			<div class="btn-group">
				   <button type="button" class="btn btn-default dropdown-toggle" 
				      data-toggle="dropdown">
				     <p ng-model='option' ng-bind='option.name' style="display:'inline-block;'"></p><span class="caret"></span>
				   </button>
				   <ul class="dropdown-menu" role="menu">
				      <li ng-repeat='option in optionList' style='cursor:pointer;'><a ng-click='selectOption(option)' ng-bind='option.name'></a></li>
				   </ul>
				</div>
	 		</div>
			<div class='col-md-4' style='padding-left:0;'>			
				<div class="input-group date startDateDiv col-md-8" id="startDateDiv"   style='padding-left:0;'>
					<input class="form-control" type="text" required="true" ng-model='startTime' ng-change='setTimeQuery(startTime)' readonly="readonly"/>
					<span class="input-group-addon"><span class="fa fa-calendar"></span></span>							
				</div>
			</div>		
			<div class='col-md-4' style='padding-left:0;'>			
				<div class="input-group date endDateDiv col-md-8" id="endDateDiv"   style='float:left;padding-left:0;'>
					<input class="form-control" type="text" ng-model='endTime' required="true" ng-change='setTimeQuery(endTime)' readonly="readonly"/>
					<span class="input-group-addon"><span class="fa fa-calendar"></span></span>							
				</div>
			</div>	
				<div class='col-md-2'>
					<div class="btn-group">
					   <button type="button" class="btn btn-default dropdown-toggle" 
					      data-toggle="dropdown">
					     <p ng-model='timeOption' ng-bind='timeOption.name' style="display:'inline-block;'"></p><span class="caret"></span>
					   </button>
					   <ul class="dropdown-menu" role="menu">
					      <li ng-repeat='option in timeOptionList' style='cursor:pointer;'><a ng-click='selectTimeOption(option)' ng-bind='option.name'></a></li>
					   </ul>
					</div>
				</div>
		</div>
		<div class='col-md-12'>
			<div id='chart'>				
			</div>
		</div>
		<!--日期 end--> 
		<table class="table table-hover">
		      <thead>
		        <tr>
		          <th>日期</th>
		          <th>注册用户数</th>
		          <th>新增项目</th>
		          <th>新增预约</th>
		          <th>认证用户数(个人)</th>
		          <th>认证用户数(企业)</th>
		        </tr>
		      </thead>
		      <tbody>
		        <tr ng-repeat='tableData in totalTableList' ng-cloak>
		          <td><span ng-bind='tableData.time'></span></td>
		          <td ng-bind='tableData.userCount'></td>
		          <td ng-bind='tableData.projectCount'></td>
		          <td ng-bind='tableData.weixinProjectCount'></td>
		          <td ng-bind='tableData.personIdentifierCount'></td>		          
		          <td ng-bind='tableData.compIdentifierCount'></td>
		        </tr>		        
		      </tbody>
		      <thead>
		        <tr>
		          <th>总计</th>
		          <th ng-bind='total.totalUserCount'></th>
		          <th ng-bind='total.totalProjectCount'></th>
		          <th ng-bind='total.totalWeixinCount'></th>
		          <th ng-bind='total.totalPersonIdentifierCount'></th>
		          <th ng-bind='total.totalCompIdentifierCount'></th>
		        </tr>
		      </thead>
		</table>
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
  </body>
</html>
