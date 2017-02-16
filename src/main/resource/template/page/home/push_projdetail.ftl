<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<title>需求详情 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes} ${tools_includes}

				<link rel="stylesheet" href="${ctx}/css/home/request_handle.css?v=${version}">
				<script type="text/javascript" src="${ctx}/js/home/push_projdetail.js?v=${version}"></script>
				<!--<script type="text/javascript" src="${ctx}/js/home/request_handle.js?v=${version}"></script>-->
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->
			<!--start of hidden parameters-->
			<input type='hidden' id='projectId' value='${projectInSelfRun.id}' />

			<#if isIdentifiedPassed?? && isIdentifiedPassed == true>
			  	<input type="hidden" id="h_IsIdentifiedPassed" value="1"/>
			 <#else>
			 	 <input type="hidden" id="h_IsIdentifiedPassed" value="0"/>
			</#if>

			<#if isUserInfoComplete?? && isUserInfoComplete == true>
				 <input type="hidden" id="h_IsUserInfoComplete" value="1"/>
			<#else>
			   <input type="hidden" id="h_IsUserInfoComplete" value="0"/>
			</#if>
			<!--end of hidden parameters-->

			<!-- 项目报名 -->
			<div class="modal" id="enroll">
				<div class="modal-dialog modal-custom-class">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" onclick="$('#enroll').css('display','none')" aria-hidden="true"><i class="fa fa-remove"></i></button>
							<h4 class="modal-title">项目报名</h4>
						</div>
						<div class="modal-body">
							<form class="form-horizontal form-in-modal enroll-div" id="enrollForm" action="javascript:enroll()" role="form" method="post">
								<div class="col-xs-12 col-sm-12 col-md-12 enroll-div">
									<div class="col-xs-12 col-sm-12 col-md-12">
										<label for="reason"><span style="color: red;">*</span>胜任角色</label>
									</div>
									<div class="col-xs-12 col-sm-12 col-md-12">
										<#list enrollRoleList as tag>
										<div class="col-xs-6 col-sm-3 col-md-3 enroll_checkBox">
											<div class="checkBoxDiv" onclick="enrollCheckBox(this)"><img src="${store_location}/img/checkboxchecked.png" alt="" /></div>
											<div value="${tag.value}">${tag.name}</div>
										</div>
										</#list>
										<div id="enrollCheckBoxMsg" class="warning">至少选择一种胜任角色</div>
									</div>
								</div>

								<div class="col-xs-12 col-sm-12 col-md-12 enroll-div enroll-div-height">
									<div class="col-xs-12 col-sm-12 col-md-12">
										<label for="reason"><span style="color: red;">*</span>胜任理由(大于10小于500字)</label>
									</div>
									<div class="col-xs-12 col-sm-12 col-md-12">
										<textarea class="form-control enroll-div-textarea" id="reason" name="reason" minlength="11" maxlength="500" required></textarea>
										<div class="reasonWarning"></div>
									</div>
								</div>

								<div class="col-xs-12 col-sm-12 col-md-12 enroll-div enroll-div-margin">
									<div class="col-xs-6 col-sm-6 col-md-6 text-left">
										<button type="button" class="btn btn-default" onclick="$('#enroll').css('display','none')">取消</button>
									</div>
									<div class="col-xs-6 col-sm-6 col-md-6 text-right">
										<button type="submit" class="btn btn-primary">确定</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>

			<div class="body-offset" style="margin-top:100px;"></div>
			<div class="col-xs-12 padding" style="min-height: 486px">
				<div class="container">
					<div class="col-xs-12 col-sm-offset-1 col-sm-10 padding">
						<div class="col-xs-12 padding project-title">
							<div class="col-xs-12 col-sm-8 padding">
								<img style="width: 55px;display: block;float: left;height: 55px;margin-right: 15px;" src="${projectInSelfRun.abbrImagePath}" />
								<div style="float: left;font-size: 16px;font-weight: bold;margin-top: 16.5px;">${projectInSelfRun.name!""}</div>
							</div>
							<div class="hidden-xs col-sm-4 padding text-right">
								<#if isJoined && projectInSelfRun.status != 4 && projectInSelfRun.status != 2>
									<button class="title-signUp">已报名，等待结果确认</button>
								<#elseif projectInSelfRun.status==0 && projectInSelfRun.creatorId != Session.SESSION_LOGIN_USER.userInfoVo.id &&
									Session.SESSION_LOGIN_USER.userInfoVo.userType != 2 && Session.SESSION_LOGIN_USER.userInfoVo.userType != -1>
									<button id="enroll-btn" class="title-signUp" data-toggle="modal" onClick="javascript:lanuchJoinPlanModel(this);">立即报名</button>
								</#if>
							</div>

						<div class="col-xs-12 padding project-info">
							<div class="col-xs-12 padding">
								<input type="hidden" value="${projectInSelfRun.abbrImagePath}"/>
								<div class="col-xs-6 col-sm-4 padding">项目预算：<span style="color: #ff7575">${projectInSelfRun.budget!""}</span></div>
								<div class="col-xs-6 col-sm-4 padding">项目类型：<span class="data_span">${projectInSelfRun.type!""}</span></div>
								<div class="col-xs-6 col-sm-4 padding">项目周期：<span class="data_span">${projectInSelfRun.period!""}</span></div>
							</div>
							<div class="col-xs-12 padding" style="margin-top: 10px">浏览次数：<span class="data_span">${projectInSelfRun.viewCount!""}</span></div>
							<div class="col-xs-12 padding" style="margin-top: 10px">招募对象：<span class="data_span">
									<#if projectInSelfRun.enrollRoleList??>
										<#list projectInSelfRun.enrollRoleList as tag>
											${tag.name}
										</#list>
									</#if>
								</span>
							</div>
							<!-- <div class="col-md-12 project-handle-item">
								<div class="col-md-2 item-label">
									<p>创建时间：</p>
								</div>
								<div class="col-md-10 project-handle-item-content">
									<p>${projectInSelfRun.createTime?string("yyyy-MM-dd HH:mm")}</p>
								</div>
							</div> -->
						</div>

						<div class="col-xs-12 padding project-info" style="margin-top: 20px">
							<div class="col-xs-12 padding">${projectInSelfRun.content}</div>
						</div>

						<div class="col-xs-12 padding text-center">
							<!-- <button class="tail-signUp" data-toggle="modal" onClick="javascript:lanuchJoinPlanModel(this);">立即报名</button> -->
							<#if !isJoined && projectInSelfRun.status==0 && projectInSelfRun.creatorId != Session.SESSION_LOGIN_USER.userInfoVo.id &&
								Session.SESSION_LOGIN_USER.userInfoVo.userType != 2 && Session.SESSION_LOGIN_USER.userInfoVo.userType != -1>
								<button id="enroll-btn" class="title-signUp" data-toggle="modal" onClick="javascript:lanuchJoinPlanModel(this);">立即报名</button>
							</#if>
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
				</div>
				<!--<script type="text/javascript">
		initFileIuput("#fileUpload");
	</script>-->

				<!--CNZZ CODE-->
				${cnzz_html}
				<!--END OF CNZZ CODE-->
	</body>

</html>
