<!DOCTYPE html>
<html lang="zh-CN" ng-app='projectApp' ng-controller='projectController'>

	<head>
		<title>项目审核 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes2} ${tools_includes}

				<link rel="stylesheet" href="${ctx}/thirdparty/summernote/css/summernote.css?v=${version}"/>
				<link rel="stylesheet" href="${ctx}/css/home/require_modify.css?v=${version}" />
				<script type="text/javascript" src="${ctx}/thirdparty/summernote/js/summernote.min.js?v=${version}"></script>
				<script type="text/javascript" src="${ctx}/thirdparty/summernote/lang/summernote-zh-CN.js?v=${version}"></script>
				<script type="text/javascript" src="${ctx}/thirdparty/summernote/js/angular-summernote.min.js?v=${version}"></script>
				<script type="text/javascript" src="${ctx}/js/home/request_modify.js?v=${version}"></script>
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->

			<!--<div class="container breadcrumb-container">
	  <ol class="breadcrumb">
		  <li><a href="/">首页</a></li>
		  <li><a href="/admin/projects_review">项目审核列表</a></li>
		  <li><a href="#">项目审核</a></li>
	  </ol>
  </div>-->

			<!--banner-->
			<div class="col-xs-12 col-md-12 banner-div"></div>
			<!--banner end-->

			<!--hidden parameters-->
			<input type="hidden" id="h_projectId" value="${projectId!" "}">
			<!--end of hidden parameters-->

			<div class="container">
				<div class="col-md-offset-1 col-md-9 container-bg-color">
					<div class="col-xs-12 col-md-12 text-center">
						<h1>修改用户需求</h1></div>
					<div class="col-xs-12 col-md-12 div-style">
						<div class="col-xs-12 col-md-12">
							<i class="icon-red">*</i><label for="projectName">项目名称</label></div>
						<div class="col-xs-12 col-md-12">
							<input ng-show="projectStatus==0 || projectStatus==1 || projectStatus==2 || projectStatus==4" maxlength="100" disabled="disabled" ng-model="projectName" placeholder="请填写您的需求" type="text" class="form-control" ng-keyup="checkProjectNameNull(projectName)" />
							<input ng-show="projectStatus==-1 || projectStatus==3" ng-model="projectName" maxlength="100" placeholder="请填写您的需求" type="text" class="form-control" ng-keyup="checkProjectNameNull(projectName)" />
						</div>
						<div class="col-xs-12 col-md-12 warning  divactive" id="projectNameWarning">请输入项目名称</div>
					</div>

					<div class="col-xs-12 col-md-12 div-style">
						<div class="col-xs-12 col-md-12">
						<i class="icon-red">*</i><label for="projectStatus">项目状态</label></div>
						<div class="col-xs-12 col-md-12">
							<#if userType == -1>
								<select class="form-control" ng-model="projectStatus">
									<option value="{{projectstatus.value}}" ng-repeat="projectstatus in projectStatusList" ng-bind="projectstatus.name" ng-selected="projectStatus==projectstatus.value"></option>
								</select>
							<#else>
								<input class="form-control" type="text" disabled="disabled" ng-model="projectStatusTag"/>
							</#if>
						</div>
					</div>
					<div class="col-xs-12 col-md-12  div-textarea-style" ng-show="projectStatus==4">
						<div class="col-xs-12 col-md-12">
							<i class="icon-red">*</i><label for="reason">请填写关闭理由(至少需要5个字符)</label>
							<textarea maxlength="1000" minlength="5" ng-model="reason" id="reason" class="form-control" placeholder="请填写您的关闭理由..." ng-keyup="checkReasonNull(reason)"></textarea>
						</div>
						<div class="col-xs-12 col-md-12 warning divactive" id="reasontWarning">请输入您的关闭理由</div>
					</div>
					<div class="col-xs-12 col-md-12 div-style">
						<div class="col-xs-12 col-md-12">
							<i class="icon-red">*</i><label for="projectAmount">项目预算</label></div>
						<div class="col-xs-12 col-md-12">
							<#if userType == -1>
								<select id="budget" ng-show="projectStatus==-1 || projectStatus==3" ng-model="startBudget" class="form-control"  onkeydown="catch_keydown(this)" onkeypress="catch_press(this)" onfocus="catch_focus(this)" ng-blur="updateVal()">
									<option value="{{projectSum.name}}" ng-repeat="projectSum in projectSums" ng-bind="projectSum.name" ng-selected="startBudget==projectSum.name"></option>
								</select>
								<select ng-show="projectStatus==0 || projectStatus==1 || projectStatus==2 || projectStatus==4" ng-model="startBudget" disabled="disabled" class="form-control">
									<option value="{{projectSum.name}}" ng-repeat="projectSum in projectSums" ng-bind="projectSum.name" ng-selected="startBudget==projectSum.name"></option>
								</select>
							<#else>
								<select ng-show="projectStatus==-1 || projectStatus==3" ng-model="startBudget" class="form-control">
									<option value="{{projectSum.name}}" ng-repeat="projectSum in projectSums" ng-bind="projectSum.name" ng-selected="startBudget==projectSum.name"></option>
								</select>
								<select ng-show="projectStatus==0 || projectStatus==1 || projectStatus==2 || projectStatus==4" ng-model="startBudget" disabled="disabled" class="form-control">
									<option value="{{projectSum.name}}" ng-repeat="projectSum in projectSums" ng-bind="projectSum.name" ng-selected="startBudget==projectSum.name"></option>
								</select>
							</#if>
						</div>
					</div>

					<div class="col-xs-12 col-md-12  div-style" ng-show="projectStatus==1 || projectStatus==2">
						<div class="col-xs-12 col-md-12">
							<i class="icon-red">*</i><label for="actualAmounts">成交金额</label></div>
						<div class="col-xs-12 col-md-12">
							<#if userType == -1>
								<input ng-model="actualAmounts" placeholder="请输入成交金额" type="text" class="form-control" id="actualAmounts" ng-keyup="checkActualAmountsNull(actualAmounts)" />
							<#else>
								<input ng-model="actualAmounts" placeholder="请输入成交金额" type="text" class="form-control" id="actualAmounts" disabled="disabled" ng-keyup="checkActualAmountsNull(actualAmounts)"/>
							</#if>
						</div>
						<div class="col-xs-12 col-md-12 warning  divactive" id="actualAmountsWarning">请输入成交金额</div>
					</div>

					<!--<div class="col-xs-12 col-md-12  div-style">
						<div class="col-xs-12 col-md-12">
							<i class="icon-red">*</i><label for="projectAmount">您期望项目什么时候启动</label></div>
						<div class="col-xs-12 col-md-12">
							<select ng-show="projectStatus==0 || projectStatus==2 || projectStatus==1 || projectStatus==4" class="form-control" disabled="disabled" ng-model="startTime">
								<option value="{{projectTime.value}}" ng-repeat="projectTime in projectTimes" ng-bind="projectTime.name" ng-selected="startTime==projectTime.value"></option>
							</select>
							<select ng-show="projectStatus==-1 || projectStatus==3" class="form-control" ng-model="startTime">
								<option value="{{projectTime.value}}" ng-repeat="projectTime in projectTimes" ng-bind="projectTime.name" ng-selected="startTime==projectTime.value"></option>
							</select>
						</div>
					</div>-->

					<div class="col-xs-12 col-md-12 div-style">
						<div class="col-xs-12 col-md-12">
							<i class="icon-red">*</i><label for="projectName">项目周期</label></div>
						<div class="col-xs-12 col-md-12">
							<input ng-show="projectStatus==0 || projectStatus==1 || projectStatus==2 || projectStatus==4" maxlength="100" disabled="disabled" ng-model="period" placeholder="请填写您的需求" type="text" class="form-control" ng-keyup="checkProjectPeriodNull(period)" />
							<input ng-show="projectStatus==-1 || projectStatus==3" ng-model="period" maxlength="3" placeholder="请填写您的需求" type="text" class="form-control" ng-keyup="checkProjectPeriodNull(period)" />
						</div>
						<div class="col-xs-12 col-md-12 warning  divactive" id="projectPeriodWarning">项目周期输入有误</div>
					</div>
					<div class="col-xs-12 col-md-12 div-project-style">
						<div class="col-xs-12 col-md-12">
							<i class="icon-red">*</i><label for="projectType">项目类型</label></div>
						<div class="col-xs-12 col-md-12">
							<div ng-show="projectStatus==0 || projectStatus==1 || projectStatus==2 || projectStatus==4" class="col-xs-6 col-md-3 project-type" ng-repeat="projectType in projectTypes">
								<div><img class="project-type-img {{projectType.active?'divactive':''}}" src="${ctx}/img/checkboxchecked.png" /></div>
								<p ng-bind="projectType.name"></p>
							</div>
							<div ng-show="projectStatus==-1 || projectStatus==3" class="col-xs-6 col-md-3 project-type" ng-repeat="projectType in projectTypes" ng-click="modifyProjectTypes(projectType);checkProjectTypeNull()">
								<div><img class="project-type-img {{projectType.active?'divactive':''}}" src="${ctx}/img/checkboxchecked.png" /></div>
								<p ng-bind="projectType.name"></p>
							</div>

						</div>
						<div class="col-xs-12 col-md-12 warning divactive" id="projectTypeWarning">请选择项目类型</div>
					</div>

					<#if userType == -1>
					<div class="col-xs-12 col-md-12 div-consultant-style">
						<div class="col-xs-12 col-md-12">
							<i class="icon-red">*</i><label for="projectType">分配顾问：</label></div>
						<div class="col-xs-12 col-md-12">
							<!-- <div ng-show="projectStatus==-1 || projectStatus== 0" class="col-xs-6 col-md-3 project-type">
                    <select ng-model="projectInSelfRun.consultant.id" ng-options="consultant.id as consultant.name for consultant in allConsultants" style="width:150px;">
										</select>
							</div>
							<div ng-show="projectStatus==1 || projectStatus==2 || projectStatus==3 || projectStatus==4" class="col-xs-6 col-md-3 ">
								<p ng-bind="projectInSelfRun.consultant.name"></p>
							</div> -->
							<div class="col-xs-6 col-md-3 project-type">
                    <select ng-model="projectInSelfRun.consultant.id" ng-options="consultant.id as consultant.name for consultant in allConsultants" style="width:150px;">
										</select>
							</div>
						</div>
						<div class="col-xs-12 col-md-12 warning divactive" id="projectTypeWarning">请选择项目类型</div>
					</div>
					<div class="col-xs-12 col-md-12 div-project-style">
						<div class="col-xs-12 col-md-12">
						<i class="icon-red">*</i><label for="projectType">招募角色</label></div>
						<div class="col-xs-12 col-md-12">
							<div class="col-xs-6 col-md-3 project-type" ng-repeat="enrollRole in enrollList" ng-click="modifyEnroll(enrollRole);checkEnrollNull()">
								<div><img class="project-type-img {{enrollRole.active?'divactive':''}}" src="${ctx}/img/checkboxchecked.png" /></div>
								<p ng-bind="enrollRole.name"></p>
							</div>
						</div>
						<div class="col-xs-12 col-md-12 warning divactive" id="enrollWarning">请选择招募角色</div>
					</div>
					<div class="col-xs-12 col-md-12" style="min-height: 50px;">
						<div class="col-xs-4 col-md-3">
						<i class="icon-red">*</i><label for="projectType">诚意项目</label></div>
						<div class="col-xs-8 col-md-8">
							<div class="col-xs-6 col-md-3 project-type" ng-click="modifyFaithProject(faithProject);">
								<div><img class="project-type-img {{(faithProject==1)?'':'divactive'}}" src="${ctx}/img/checkboxchecked.png" /></div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-md-12" style="min-height: 50px;">
						<div class="col-xs-4 col-md-3">
             <label for="projectType">需要购买域名</label></div>
						<div class="col-xs-8 col-md-8">
							<div class="col-xs-6 col-md-3 project-type" ng-click="modifyIsNeedBuyDomain(isNeedBuyDomain);">
								<div><img class="project-type-img {{(isNeedBuyDomain==1)?'':'divactive'}}" src="${ctx}/img/checkboxchecked.png" /></div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-md-12" style="min-height: 50px;">
						<div class="col-xs-4 col-md-3">
						<label for="projectType">需要购买云服务器和数据库</label></div>
						<div class="col-xs-8 col-md-8">
							<div class="col-xs-6 col-md-3 project-type" ng-click="modifyIsNeedBuyServerAndDB(isNeedBuyServerAndDB);">
								<div><img class="project-type-img {{(isNeedBuyServerAndDB==1)?'':'divactive'}}" src="${ctx}/img/checkboxchecked.png" /></div>
							</div>
						</div>
					</div>
					</#if>
					<input type="hidden" value="${userType!""}" id="userType"/>


					<div class="col-xs-12 col-md-12  div-textarea-style">
						<div class="col-xs-12 col-md-12">
							<i class="icon-red">*</i><label for="requirement">填写您的需求(至少需要20个字符)</label>
						</div>

						<#if userType == -1>
						 <div class="col-md-12">
							<summernote code="text" config="summernoteOptions" ng-model="requirement"></summernote>
						</div>
					  <#else>
							<div class="col-xs-12 col-md-12" ng-show="projectStatus==-1 || projectStatus==3 || projectStatus==0">
								<textarea maxlength="1000" name="requirement" ng-model="requirement" class="form-control" placeholder="请填写您的需求..." ng-keyup="checkRequirementNull(requirement)"></textarea>
							</div>
							<div class="col-xs-12 col-md-12" ng-show="projectStatus==1 || projectStatus==2 || projectStatus==4">
								<textarea maxlength="1000" disabled="disabled" name="requirement" ng-model="requirement" class="form-control" placeholder="请填写您的需求..." ng-keyup="checkRequirementNull(requirement)"></textarea>
							</div>
						</#if>
						<div class="col-xs-12 col-md-12 warning divactive" id="requirementWarning">请输入您的需求</div>
					</div>


					<!--项目附件开始-->
					<div class="col-xs-12 col-md-12"  ng-show="projectStatus==1 || projectStatus==2 || projectStatus==4">
						<div class='col-md-12 text-left'>
							项目附件
						</div>
						<div class="col-sm-4 col-xs-12 col-md-3" ng-repeat="attachment in projectAttachment">
							<a href="{{attachment.path}}"  ng-bind="attachment.fileName"></a>
						</div>
						<div class='col-md-12' style="margin-left:-15px;">
							<div class="col-md-12 col-sm-12 col-xs-12 padding project-handle-item-content">
								<p style='color:#ccc;'>将需求描述文件上传,文件支持20M以内的doc、docx、pdf、ppt、pptx、xls、xlsx、zip、jpg、png、jpeg、bmp、gif格式,也可联系码客帮客服上传。</p>
							</div>
						</div>
					</div>

					<div class='col-xs-12 col-md-12'  ng-show="projectStatus==-1 || projectStatus==3 || projectStatus==0">
						<!--header-->
						<div class='col-md-12 text-left'>
							项目附件
						</div>
						<div class='col-md-12'>
							<input type="file" id="fileselect" name="pic" class="picselect file" />
						</div>
						<div class='col-md-12 fileupload-info-box'>
							<div class="col-md-10 project-handle-item-content">
								<div id='projectFileInfo' class="alert alert-danger alert-dismissible fileupload-info opacityHide" role="alert">
									<button type="button" class="close"><span aria-hidden="true" class='info-close'>&times;</span></button>
									<strong>错误!</strong><span class='info'></span>
									<img id="tempimg" dynsrc="" src="" style="display:none"/>
								</div>
							</div>
							<div class='col-md-12' style="margin-left:-15px;">
								<div class="col-md-12 col-sm-12 col-xs-12 padding project-handle-item-content">
									<p style='color:#ccc;'>将需求描述文件上传,文件支持20M以内的doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar、jpg、png、jpeg、bmp、gif格式,也可联系码客帮客服上传。</p>
								</div>
							</div>
						</div>
						<!--header end-->
						<div class='col-md-12'>
							<div id='monitorFileList' class="row col-md-12 project-handle-item-content file-list" max='5'>
								<!--附件模型-->
								<div class="btn-group attach-group attach-model opacityHide">
									<button type="button" class="btn btn-primary btn-xs attach"></button>
									<button type="button" class="btn btn-primary btn-xs attach-del ">
										<span class="fa fa-times"></span>
										<span class="sr-only"></span>
									</button>
								</div>
								<!--附件模型end-->
							</div>
						</div>
						<!-- footer-->
						<div class='row row-bg'>
							<div class='col-md-12'>
								<hr>
							</div>
						</div>
						<!--footer end-->
					</div>

					<div class="col-xs-12 col-md-12 project-btn">
						<button ng-click="Sendform()" class="btn btn-primary col-xs-offset-3 col-md-offset-3 col-xs-6 col-md-6">提交</button>
					</div>
				</div>
			</div>

			<!--start of footer-->
			<div id="footerDiv"></div>
			<#include "../footer.ftl">
				<!--end of footer-->
				<!---start of help docker-->
				<div id="top"></div>
				<!--end of help docker-->

				<div>
					<input type="hidden" id="topurl" value="${ctx}/" />
				</div>
	</body>

</html>
