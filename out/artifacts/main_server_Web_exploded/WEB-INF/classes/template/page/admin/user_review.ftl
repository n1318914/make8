<!DOCTYPE html>
<html lang="zh-CN" ng-app='userReviewApp' ng-controller='userReviewCtrl'>

	<head>
		<title>用户认证审核 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes} ${tools_includes}
				<link rel="stylesheet" href="${ctx}/css/admin/user_review.css?v=${version}">
				<link rel="stylesheet" href="${ctx}/css/home/userinfo.css?v=${version}">
				<script type="text/javascript" src="${ctx}/js/admin/user_review.js?v=${version}"></script>
				<script type="text/javascript">
					$(document).ready(function() {
						//initialize the web pages
						$("#userReviewForm").validate({
							errorElement: "span"
						});

						initReviewDescView();
					});

					var reviewResult = false;

					function checkReview(el) {
						var id = $(el).attr("id");

						if (id == "review_unok") {
							$("#reviewDescDiv").show();
							reviewResult = false;
						} else {
							$("#reviewDescDiv").hide();
							reviewResult = true;
						}
					}

					function initReviewDescView() {
						checkReview($('input[name="review"]:checked'));
					}

					function doSaveReviewInfo() {
						if (reviewResult == true) {
							if ($("#pass").val() != "input") {
								showMessageBox("请根据简历录入认证信息,再进行审核操作", "审核操作")
								return;
							}
						}

						var mURL = "";
						var uid = $("#userId").val();
						var topURL = $("#topurl").val();

						var paraData = "uid=" + uid;
						var confirmContent = "";

						if (reviewResult) {
							mURL = "/api/2/identify/pass";
							confirmContent = "确认通过认证信息？";
						} else {
							mURL = "/api/2/identify/reject";
							var reviewDesc = $("#reviewDesc").val().trim();
							paraData += "&reason=" + reviewDesc;
							confirmContent = "确认驳回认证信息？"
						}

						$.confirm({
							title: '认证审核',
							content: confirmContent,
							confirmButton: '确认',
							cancelButton: '取消',
							confirmButtonClass: 'btn-primary',
							cancelButtonClass: 'btn-danger',
							backgroundDismiss: false,
							animation: 'scale',
							confirm: function() {
								$.ajax({
									type: "Post",
									url: mURL,
									data: paraData,
									async: false,
									success: function() {
										showMessageBox("审核信息提交成功！", "用户审核", topURL + "api/2/idntify/view?uid=" + uid)
									}
								})
							}
						});

					}

					function launchRemark(el) {
						var remark = $("#remarkDiv").html();

						if (remark != null && remark.trim() != "") {
							remark = remark.replace(/<br\/>|<br>/g, "\n");
							$("#jNote").val(remark);
						} else {
							$("#jNote").val("");
						}

						el.href = "#noteDialog";
					}

					function doRemark() {
						var mURL = "/api/2/member/remark";
						var remark = $("#jNote").val();
						var uid = $("#userId").val();

						var paraData = "uid=" + uid + "&remark=" + remark;

						$.ajax({
							type: "POST",
							data: paraData,
							url: mURL,
							async: false,
							success: function(data) {
								$("#remarkDiv").html(remark);
								$('#noteDialog').modal('hide');
							}
						});
					}
				</script>
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->

			<div class="body-offset"></div>
			<!--<div class="container breadcrumb-container">
		  <ol class="breadcrumb">
			  <li><a href="/">首页</a></li>
			  <li><a href="javascript:history.go(-1);">用户审核列表</a></li>
			  <li><a href="#">用户审核</a></li>
		  </ol>
   </div>-->

			<!--hidden parameters-->
			<div>
				<input type="hidden" value="${userInfo.id!" "}" id="userId" />
				<input type="hidden" value="${userInfo.userType!" "}" id="userType" />
				<#if userInfo.identifyInfo??>
					<input type="hidden" value="${userInfo.identifyInfo.category!" "}" id="identifyCategory" />
				<#else>
					<input type="hidden" value="0" id="identifyCategory" />
				</#if>
			</div>
			<!---end of hidden parameters-->

			<div class="container">
				<div class="col-lg-offset-1 tabbable tabs-left">
					<ul class="nav nav-tabs " id="infoTab">
						<li class="active"><a href="#baseInfo" data-toggle="tab">基本信息</a>
						</li>

						<li><a href="#autheninfo" data-toggle="tab">认证信息</a>
						</li>

						<li><a href="#joinInfo" data-toggle="tab" ng-click="loadData(${userInfo.id!" "})">参与的项目</a>
						</li>

						<#if userInfo.identifyInfo?? && userInfo.identifyInfo.status==0>
							<li><a href="#authenops" data-toggle="tab">审核操作</a>
							</li>
						</#if>

					</ul>
				</div>
				<div class="tab-content">
					<div id="baseInfo" class="tab-pane active">
						<div class="col-lg-9 col-md-9 cpl-sm-10 col-xs-12 tab-form">
							<div class="col-lg-offset-1 col-md-offset-1 col-sm-offset-1 col-xs-offset-1">
								<div class="form-header">
									<h3>基本信息</h3>
								</div>
								<div class="col-md-12 col-lg-12 form-desc-line">
									<p class="i-label">用户ID：</p>
									<p>${userInfo.id!""}</p>
								</div>
								<div class="col-md-12 col-lg-12 form-desc-line">
									<p class="i-label">邮箱：</p>
									<p>${userInfo.email!""}</p>
								</div>
								<div class="col-md-12 col-lg-12 form-desc-line">
									<p class="i-label">昵称：</p>
									<p>${userInfo.name!""}</p>
								</div>
								<div class="col-md-12 col-lg-12 form-desc-line">
									<p class="i-label">手机号码：</p>
									<p>${userInfo.mobile!""}</p>
								</div>
								<div class="col-md-12 col-lg-12 form-desc-line">
									<p class="i-label">QQ：</p>
									<p>${userInfo.qq!""}</p>
								</div>
								<div class="col-md-12 col-lg-12 form-desc-line">
									<p class="i-label">微信：</p>
									<p>${userInfo.weixin!""}</p>
								</div>
								<div class="col-md-12 col-lg-12 form-desc-line">
									<p class="i-label">所在区域：</p>
									<p>${userInfo.region!""}</p>
								</div>
								<div class="col-md-12 col-lg-12 form-desc-line" id="introDiv">
									<p class="i-label">自我展示：</p>
									<p style="word-break: break-all;">${userInfo.introduction!""}</p>
								</div>
								<div class="col-md-12 col-lg-12 form-desc-line">
									<p class="i-label">备注：</p>
									<p id="remarkDiv">${userInfo.remark!""}</p>
								</div>
								<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1>
									<div class="col-lg-3 col-md-3">
										<a data-toggle="modal" class="btn btn-block btn-primary" href="javascript:void(0);" onclick="javascript:launchRemark(this);">修改备注</a>
									</div>
								</#if>
							</div>
						</div>
					</div>

					<!--用户参与的项目-->
					<div id="joinInfo" class="tab-pane">
						<div class="col-lg-9 col-md-9 cpl-sm-10 col-xs-12 tab-form">
							<div class="col-lg-offset-1 col-md-offset-1 col-sm-offset-1 col-xs-offset-1">
								<div class="form-header">
									<h3>参与的项目</h3>
								</div>
								<div class="col-md-12 historyDiv">
									<div class="col-md-12 histroyContentBackground">
										<div class="col-md-12 text-center histroyTitle">
											<div class="col-sm-4 col-md-4">项目名称</div>
											<div class="col-sm-3 col-md-3">参与时间</div>
											<div class="col-sm-2 col-md-2">项目状态</div>
											<div class="col-sm-3 col-md-3">报名/承接</div>
										</div>
										<div ng-repeat='listJoin in listData' class="col-md-12 text-center histroyContent" ng-cloak>
											<div class="col-sm-4 col-md-4">
												<a href="/home/selfrun/p/view?id={{listJoin.projectId}}" ng-bind='listJoin.projectName'></a>
											</div>
											<div class="col-sm-3 col-md-3" ng-bind='listJoin.joinTime'>2016-3-20</div>
											<div class="col-sm-2 col-md-2" ng-bind='listJoin.projectStatus'>未开始</div>
											<div class="col-sm-3 col-md-3" ng-if='listJoin.status==0 || listJoin.status==1'>已邀请</div>
											<div class="col-sm-3 col-md-3" ng-if='listJoin.status==2'>接受邀请</div>
											<div class="col-sm-3 col-md-3" ng-if='listJoin.status==3'>拒绝邀请</div>
											<div class="col-sm-3 col-md-3" ng-if='listJoin.status==4'>邀请报名后被选中</div>
											<div class="col-sm-3 col-md-3" ng-if='listJoin.status==5'>主动报名</div>
											<div class="col-sm-3 col-md-3" ng-if='listJoin.status==6'>主动报名选中</div>
											<div class="col-sm-3 col-md-3" ng-if='listJoin.status==7'>主动报名选中被撤销</div>
										</div>
										<div class="col-md-12 text-center histroyContent" ng-cloak>
											<div class="col-sm-12 col-md-12" ng-bind='listDataMsg'></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!--遮罩层div-->

					<!-- 添加项目经验(企业) -->
					<div class="modal" id="addCompanyProjectExperience">
						<div class="modal-dialog modal-custom-class">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
									<h4 class="modal-title">添加项目经验</h4>
								</div>
								<div class="modal-body">
									<form class="form-horizontal form-in-modal" action="javascript:addcompanyWorkExperience()" id="companyworkCheckForm">
										<div class="form-group">
											<div class="col-lg-12 col-md-12">
												<label class="control-label form-label col-md-2 fromDiv" for="companyPName">项目名称</label>
												<input class="col-md-9" style="padding:5px" type="text" id="companyPName" maxlength="100" name="companyPName" required>
											</div>
											<div class="col-md-offset-2 col-md-4 marginTop" id="companyPNameMsg"></div>
											<div class="col-lg-12 col-md-12 fromDiv" style="margin-bottom:1%">
												<label class="control-label form-label col-md-2" for="workinghours">项目时间</label>
												<div class="input-group date companyworkinghoursstart col-md-4" id="companyworkinghoursstartDiv">
													<input class="form-control  form-input-small" id="companyworkinghoursstart" name="companyworkinghoursstart" type="text" required/>
													<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
													<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
												</div>
												<p class="col-md-1" style="text-align:center;padding-top:10px;"> - </p>
												<div class="input-group date companyworkinghoursend col-md-4" id="companyworkinghoursendDiv">
													<input class="form-control  form-input-small" id="companyworkinghoursend" name="companyworkinghoursend" type="text" required companyTimeRangeCheck="true" />
													<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
													<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
												</div>
											</div>
											<div class="col-md-offset-2 col-md-4 marginTop" id="companyworkinghoursstartMsg"></div>
											<div class="col-md-offset-7 col-md-4 marginTop" id="companyworkinghoursendMsg"></div>
											<div class="col-lg-12 col-md-12" style="height: 232px;">
												<label class="control-label form-label col-md-2" for="companyworkingcontent">项目描述</label>
												<textarea style="padding:5px" class="col-md-9" name="companyworkingcontent" id="companyworkingcontent" maxlength="1000" cols="30" rows="10" required></textarea>
											</div>
											<div class="col-md-offset-2 col-md-4 marginTop" id="companyworkingcontentMsg"></div>
											<div class="col-lg-12 col-md-12 fromDiv" style="margin-top:1%">
												<label class="control-label form-label col-md-2" for="companyWorkingUrl">项目链接</label>
												<input class="col-md-9" style="padding:5px" type="text" id="companyWorkingUrl" name="companyWorkingUrl" maxlength="200" required="true">
											</div>
											<div class="col-md-offset-2 col-md-4 marginTop" id="companyWorkingUrlMsg"></div>
										</div>
										<hr>
										<div style="overflow:hidden">
											<button type="submit" class="btn btn-lg btn-primary form-btn col-md-2 col-md-offset-3">确定</button>
											<button type="button" class="btn btn-lg btn-default form-btn col-md-2 col-md-offset-1" data-dismiss="modal" onclick="cancel(this)">取消</button>
											<input type="reset" style="display:none" id="resetToCompanyE">
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>

					<!-- 添加工作经验 -->
					<div class="modal" id="addworkexperience">
						<div class="modal-dialog modal-custom-class">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
									<h4 class="modal-title">添加工作经验</h4>
								</div>
								<div class="modal-body">
									<form class="form-horizontal form-in-modal" action="javascript:addworkexperience()" id="workCheckForm">
										<div class="form-group">
											<div class="col-lg-12 col-md-12 fromDiv">
												<label class="control-label form-label col-md-2" for="company">公司</label>
												<input class="col-md-9" type="text" id="company" name="company" required>
											</div>
											<div id="companyMsg" class="col-md-offset-2 col-md-4 marginTop"></div>
											<div class="col-lg-12 col-md-11 fromDiv">
												<label class="control-label form-label col-md-2" for="position">职位</label>
												<input class="col-md-9" type="text" id="position" name="position" required>
											</div>
											<div id="positionMsg" class="col-md-offset-2 col-md-4 marginTop"></div>
											<div class="col-lg-12 col-md-11 fromDiv">
												<label class="control-label form-label col-md-2" for="workinghours">工作时间</label>
												<div class="input-group date workinghoursstart col-md-4" id="workinghoursstartDiv">
													<input class="form-control  form-input-small" id="workinghoursstart" name="workinghoursstart" type="text" required="true" />
													<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
													<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
												</div>
												<p class="col-md-1" style="text-align:center;padding-top:10px;"> - </p>
												<div class="input-group date workinghoursend col-md-4" id="workinghoursendDiv">
													<input class="form-control  form-input-small" id="workinghoursend" name="workinghoursend" type="text" timeRangeCheck="true" />
													<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
													<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
												</div>
											</div>

											<div class="col-md-offset-2 col-md-4 marginTop" id="workinghoursstartMsg">
											</div>
											<div class="col-md-offset-7 col-md-4 marginTop" id="workinghoursendMsg">
											</div>

											<div class="col-lg-12 col-md-11" style="height:233px">
												<label class="control-label form-label col-md-2" for="workingcontent">工作内容</label>
												<textarea class="col-md-9" name="workingcontent" id="workingcontent" cols="30" s="10" required></textarea>
												<div class="col-md-offset-2 col-md-3" id="workingcontentMsg">
												</div>
											</div>
										</div>
										<hr>
										<div style="overflow:hidden">
											<button type="submit" class="btn btn-lg btn-primary form-btn col-md-2 col-md-offset-3">确定</button>
											<button type="button" class="btn btn-lg btn-default form-btn col-md-2 col-md-offset-1" data-dismiss="modal" onclick="cancel(this)">取消</button>
											<input type="reset" style="display:none" id="workCheckFormreset">
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>

					<!-- 添加作品 -->
					<div class="modal" id="addprojectworks">
						<div class="modal-dialog modal-custom-class">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
									<h4 class="modal-title">添加作品</h4>
								</div>
								<div class="modal-body">
									<form class="form-horizontal form-in-modal" id="pojectCheckForm" action="javascript:addpoject()" role="form" method="post">
										<div class="form-group">
											<div class="col-lg-12 col-md-12">
												<label class="control-label form-label col-md-2 fromDiv" for="projectname">作品标题</label>
												<input class="col-md-9" type="text" id="projectname" name="projectname" required>
											</div>
											<div id="projectnameMsg" class="col-md-offset-2 col-md-4 marginTop"></div>
											<div class="col-lg-12 col-md-12">
												<label class="control-label form-label col-md-2 fromDiv" for="projecturl">作品链接</label>
												<input class="col-md-9" type="text" id="projecturl" name="projecturl" required>
											</div>
											<div id="projecturlMsg" class="col-md-offset-2 col-md-4 marginTop"></div>
											<div class="col-lg-12 col-md-12" style="height: 220px;">
												<label class="control-label form-label col-md-2 fromDiv" for="projectintro">作品描述</label>
												<textarea class="col-md-9" type="text" id="projectintro" style="padding: 10px;height: 200px;" name="projectintro" required></textarea>
												<div id="projectintroMsg" class="col-md-offset-2 col-md-4"></div>
											</div>
										</div>
										<hr>
										<div style="overflow:hidden">
											<button type="submit" class="btn btn-lg btn-primary form-btn col-md-2 col-md-offset-3">确定</button>
											<button type="button" class="btn btn-lg btn-default form-btn col-md-2 col-md-offset-1" data-dismiss="modal" onclick="cancel(this)">取消</button>
											<input type="reset" style="display:none" id="pojectCheckFormreset">
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>

					<!-- 学历遮罩层 -->
					<div class="modal" id="educational">
						<div class="modal-dialog modal-custom-class">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
									<h4 class="modal-title">添加教育背景</h4>
								</div>
								<div class="modal-body">
									<form class="form-horizontal form-in-modal" id="EduCheckForm" action="javascript:addeducational()" role="form" method="post">
										<div class="form-group">
											<div class="col-lg-12 col-md-12 fromDiv">
												<label class="control-label form-label col-md-2" for="schoolname">学校名称</label>
												<input class="col-md-9" type="text" id="schoolname" name="schoolname" required>
											</div>
											<div class="col-md-offset-2 col-md-4 marginTop" id="schoolnameMsg"></div>
											<div class="col-lg-12 col-md-12 fromDiv">
												<label class="control-label form-label col-md-2" for="major">所学专业</label>
												<input class="col-md-9" type="text" id="major" name="major" required>
											</div>
											<div class="col-md-offset-2 col-md-4 marginTop" id="majorMsg"></div>
											<div class="col-lg-12 col-md-12 fromDiv">
												<label class="control-label form-label col-md-2" for="education">学历</label>
												<select class="form-control" name="education" id="education" required>
													<option value="1">大学专科</option>
													<option value="2">大学本科</option>
													<option value="3">硕士</option>
													<option value="4">博士</option>
													<option value="5">其他</option>
												</select>
											</div>
											<div class="col-lg-12 col-md-12 fromDiv">
												<label class="control-label form-label col-md-2" for="graduationyears">毕业时间</label>
												<select class="form-control" name="graduationyears" id="graduationyears" required>
													<option value="1">2018</option>
													<option value="2">2017</option>
													<option value="3">2016</option>
													<option value="4">2015</option>
													<option value="5">2014</option>
													<option value="6">2013</option>
													<option value="7">2012</option>
													<option value="8">2011</option>
													<option value="9">2010</option>
													<option value="10">2009</option>
													<option value="11">2008</option>
													<option value="12">2007</option>
													<option value="13">2006</option>
													<option value="14">2005</option>
													<option value="15">2004</option>
													<option value="16">2003</option>
													<option value="17">2002</option>
													<option value="18">2001</option>
													<option value="19">2000</option>
													<option value="20">1999</option>
													<option value="21">1998</option>
													<option value="22">1997</option>
													<option value="23">1996</option>
													<option value="24">1995</option>
													<option value="25">1994</option>
													<option value="26">1993</option>
													<option value="27">1992</option>
													<option value="28">1991</option>
													<option value="29">1990</option>
												</select>
											</div>
										</div>
										<hr>
										<div style="overflow:hidden">
											<button type="submit" class="btn btn-lg btn-primary form-btn col-md-2 col-md-offset-3">确定</button>
											<button type="button" class="btn btn-lg btn-default form-btn col-md-2 col-md-offset-1" data-dismiss="modal" onclick="cancel(this)">取消</button>
											<input type="reset" style="display:none" id="eduCheckFormreset">
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!--遮罩层div end-->

					<#if userInfo.identifyInfo??>
						<div class="form-group tab-pane" id="ResumeEntry">
							<div class="col-lg-9 col-md-9 col-sm-10 col-xs-12 tab-form">
								<form action="javascript:checkResume()" id="resumeForm">
									<div class="col-md-offset-1 col-md-10 resumeEntryTitle">
										<h3>用户信息录入</h3>
									</div>
									<div class=" col-md-10 col-md-offset-1 workstatus DivmarginTop" id="workStatus">
										<label class="col-md-12"><i class="form-required">*</i>工作状态:</label>
										<div class="col-lg-10 col-md-11 col-md-offset-1">
											<label for="worker" class="col-md-4" onclick="resumeLabelActive(this);clearCheckNotNull('workStatus')">
												<div>
													<div></div>
												</div>
												<p>可兼职接活</p>
											</label>
											<label for="freelance" class="col-md-4" onclick="resumeLabelActive(this);clearCheckNotNull('workStatus')">
												<div>
													<div></div>
												</div>
												<p>自由职业者</p>
											</label>
											<label for="student" class="col-md-4" onclick="resumeLabelActive(this);clearCheckNotNull('workStatus')">
												<div>
													<div></div>
												</div>
												<p>在校学生</p>
											</label>
											<input type="radio" name="freelanceType" id="worker" value="0">
											<input type="radio" name="freelanceType" id="freelance" value="1">
											<input type="radio" name="freelanceType" id="student" value="2">
											<input type="hidden" id="freelanceTypeHidden" value="${userInfo.freelanceType!" "}">
										</div>
										<span class="col-md-11 col-md-offset-1 warningDiv marginTop">请您选择工作状态</span>
									</div>

									<div class=" col-md-10 col-md-offset-1 canworktype" id="canWorkType">
										<label class="col-md-12"><i class="form-required">*</i>能胜任的工作类型:</label>
										<div class="col-lg-10 col-md-11 col-md-offset-1">
											<#list listDictItem as tag>
												<#if tag.type=="cando">
													<label for="WType${tag.value}" class="col-md-4" onclick="labelcheckboxActive(this);clearCheckNotNull('canWorkType')">
														<div><img src="${ctx}/img/checkboxchecked.png"></div>
														<p>${tag.name}</p>
													</label>

													<input type="checkbox" name="cando" id="WType${tag.value}" value="${tag.value}">
												</#if>
											</#list>
											<input type="hidden" id="nameCando" value="${userInfo.cando!" "}">
										</div>
										<span class="col-md-11 col-md-offset-1 warningDiv marginTop">请您选择能胜任的工作类型</span>
									</div>

									<div class=" col-md-10 col-md-offset-1 excelstechnologies" id="ExcelsTechnologies">
										<label class="col-md-12"><i class="form-required">*</i>擅长的技术:</label>
										<div class="col-lg-10 col-md-11">
											<#list listDictItem as tag>
												<#if tag.type=="ability">
													<label for="ETechnologies${tag.value}" class="col-md-2 col-md-offset-1" onclick="addSelectClass(this)">${tag.name}</label>

													<input type="checkbox" name="mainAbility" value="${tag.value}" id="ETechnologies${tag.value}">
												</#if>
											</#list>
											<input type="hidden" id="mainAbilityCheckBox" value="${userInfo.mainAbility!" "}" />
											<input type="text" name="otherMainAbility" id="ActionScript" class="col-md-11 col-md-offset-1" placeholder="其他技术 :" onkeyup="clearCheckNotNull('ExcelsTechnologies')">
											<input type="hidden" id="mainAbilityT" value="${userInfo.otherAbility!" "}" />
										</div>
										<span class="col-md-11 col-md-offset-1 warningDiv marginTop">请您选择擅长的技术</span>
									</div>

									<div id="addWorkContent" class=" col-md-10 col-md-offset-1 workexperience">
										<label class="col-md-6">工作经验:</label>
										<#if userInfo.employeeJobExperience?? && (userInfo.employeeJobExperience?size>0) >
											<span id="addWork" href="#addworkexperience" class="col-md-2 col-md-offset-4" data-toggle="modal">
									<img src="${ctx}/img/add.png" style="float: right;">
									<p style="display: none;">添加工作经验</p>
								</span>
											<#list userInfo.employeeJobExperience as tag>
												<div class="col-md-12" style="font-size:14px;margin-top:10px;margin-bottom: 10px;border-bottom:dashed 1px #ccc;padding: 10px;">
													<div class="col-md-9" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">公司名称 :</i>${tag.companyName}</div>
													<div class="col-md-2 col-md-offset-1">
														<img style="width:26px;height:26px;padding-bottom:3px" href="#addworkexperience" data-toggle="modal" onclick="reviseWorkExperience(this)" src="${ctx}/img/revise.png" />
														<img src="${ctx}/img/delect.png" style="width:23px;height:23px;padding-bottom:1px" onclick="deleteWorkExperience(this)" />
													</div>
													<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">工作时间: </i>${tag.startTime} - ${tag.endTime}</div>
													<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">公司职位: </i>${tag.office}</div>
													<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">工作内容: </i>${tag.description}</div>
												</div>
											</#list>
											<#else>
												<span id="addWork" href="#addworkexperience" class="col-md-6 col-md-offset-5" data-toggle="modal">
								<img src="${ctx}/img/add.png">
								<p>添加工作经验</p>
							</span>
										</#if>
									</div>

									<div id="addProjectContent" class=" col-md-10 col-md-offset-1 projectworks">
										<label class="col-md-6"><i class="form-required">*</i>项目作品:</label>
										<#if userInfo.employeeProduct?? && (userInfo.employeeProduct?size>0) >
											<span id="addProject" onclick="clearCheckNotNull('addProjectContent')" href="#addprojectworks" class="col-md-2 col-md-offset-4" data-toggle="modal">
									<img src="${ctx}/img/add.png" style="float: right;">
									<p style="display: none;">项目作品</p>
								</span>
											<#list userInfo.employeeProduct as tag>
												<div class="col-md-12" style="font-size:14px;margin-top:10px;margin-bottom: 10px;border-bottom:dashed 1px #ccc;padding: 10px;">
													<div class="col-md-9" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">项目名称: </i>${tag.title}</div>
													<div class="col-md-2 col-md-offset-1">
														<img style="width:26px;height:26px;padding-bottom:3px" href="#addprojectworks" data-toggle="modal" onclick="revisePoject(this)" src="${ctx}/img/revise.png" />
														<img src="${ctx}/img/delect.png" style="width:23px;height:23px;padding-bottom:1px" onclick="deletePoject(this)" />
													</div>
													<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">项目链接: </i>${tag.link}</div>
													<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">项目描述: </i>${tag.description!""}</div>
												</div>
											</#list>
											<#else>
												<span id="addProject" href="#addprojectworks" onclick="clearCheckNotNull('addProjectContent')" class="col-md-6 col-md-offset-5" data-toggle="modal">
								<img src="${ctx}/img/add.png">
								<p>项目作品</p>
							</span>
										</#if>
										<span class="col-md-12 warningDiv marginTop">请填写项目作品</span>
									</div>

									<div id="addEduContent" class=" col-md-10 col-md-offset-1 educational">
										<label class="col-md-6"><i class="form-required">*</i>教育背景:</label>
										<#if userInfo.employeeEduExperience?? && (userInfo.employeeEduExperience?size>0) >
											<span id="addEdu" href="#educational" onclick="clearCheckNotNull('addEduContent')" class="col-md-2 col-md-offset-4" data-toggle="modal">
									<img src="${ctx}/img/add.png" style="float:right">
									<p style="display: none;">学历</p>
								</span>
											<#list userInfo.employeeEduExperience as tag>
												<div class="col-md-12" style="font-size:14px;margin-top:10px;margin-bottom: 10px;border-bottom:dashed 1px #ccc;padding: 10px;">
													<div class="col-md-9" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">学校名称: </i>${tag.schoolName}</div>
													<div class="col-md-2 col-md-offset-1">
														<img style="width:26px;height:26px;padding-bottom:3px" href="#educational" data-toggle="modal" onclick="reviseeducational(this)" src="${ctx}/img/revise.png" />
														<img src="${ctx}/img/delect.png" style="width:23px;height:23px;padding-bottom:1px" onclick="deleteeducational(this)" />
													</div>
													<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">所学专业: </i>${tag.discipline}</div>
													<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">学历: </i>${tag.eduBackgroud}</div>
													<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">毕业年份: </i>${tag.graduationTime}</div>
												</div>
											</#list>
											<#else>
												<span id="addEdu" href="#educational" onclick="clearCheckNotNull('addEduContent')" class="col-md-6 col-md-offset-5" data-toggle="modal">
									<img src="${ctx}/img/add.png">
									<p>学历</p>
								</span>
										</#if>
										<span class="col-md-12 warningDiv marginTop">请填写学历</span>
									</div>

									<div class=" col-md-10 col-md-offset-1 educational">
										<label class="col-md-6"><i class="form-required" style="visibility: hidden;">*</i>个人简介:</label>
										<div class="col-md-12">
											<textarea id="profile" maxlength="1000" class="form-control">${userInfo.introduction!""}</textarea>
										</div>
									</div>

									<div class=" col-md-10 col-md-offset-1 educational">
										<label class="col-md-6">
											<!-- <i class="form-required">*</i> -->
											支付宝账号:
										</label>
										<div class="col-md-12">
											<input type="text" id="accountName" name="accountName" class="form-control" value="${userInfo.accountNum!" "}"/>
											<span id="accountNameErr" style="color:red;"></span>
										</div>
									</div>

									<div class="col-md-offset-1 col-md-10" style="padding:0px;">
										<div class="col-xs-6 col-md-6" style="padding-left:0px">
											<button class="btn btn-primary" type="reset">取消</button>
										</div>
										<div class="col-xs-6 col-md-6 text-right" style="padding-right:0px">
											<button class="btn btn-primary" type="submit">提交</button>
										</div>
									</div>
								</form>
							</div>
						</div>
						<!--ResumeEntry end-->

						<!--公司修改-->
						<div class="form-group" id="companySecond" style="display:none">
							<div class="col-lg-9 col-md-9 col-sm-10 col-xs-12 tab-form">
								<form action="javascript:companyForm()" id="company_form">
								<div class="form-header">
									<#if userInfo.identifyInfo?? && userInfo.identifyInfo.status==2>
										<h3 id="userinfoId">
										<#if userInfo.identifyInfo.category == 1>
					       					<div id="userInfoName" style="float:left;margin-right:10px">${userInfo.identifyInfo.companyName}</div>
					       				<#elseif userInfo.identifyInfo.category == 0>
											<div id="userInfoName" style="float:left;margin-right:10px">${userInfo.identifyInfo.realName}</div>
										</#if>
					       		 		<div>
					       					<img src="${ctx}/img/fail.png" style="width:25px;height:25px;float:left;margin-right:10px">
					       					<p style="color:red;height:25px;line-height:25px;font-size:16px;margin: 0px;">认证未通过</p>
					       				</div>
						     		</h3>
										<div id="userInfoMsg" style="font-family:'微软雅黑';font-size:8px;color:black;padding-left: 10px;">由于<i style="font-style: none;color: red;"> ${userInfo.identifyInfo.failReason} </i>原因,您的认证不通过,请修改后重新认证</div>
										<#else>
											<h3 id="userinfoId"><i class="fa fa-paperclip"></i>&nbsp;让我们了解贵公司的情况,以便为您推荐最合适的项目</h3>
									</#if>
								</div>
								<div class=" col-md-10 col-md-offset-1 workstatus" id="campanySize">
									<label class="col-md-12"><i class="form-required">*</i>公司规模:</label>
									<div class="col-lg-10 col-md-11 col-md-offset-1">
										<label for="10" class="col-md-4" onclick="labelN0fCActive(this);clearCheckNotNull('campanySize')">
										<div><div></div></div>
										<p>小于10人</p>
									</label>
										<label for="30" class="col-md-4" onclick="labelN0fCActive(this);clearCheckNotNull('campanySize')">
										<div><div></div></div>
										<p>10~30人</p>
									</label>
										<label for="100" class="col-md-4" onclick="labelN0fCActive(this);clearCheckNotNull('campanySize')">
										<div><div></div></div>
										<p>31~100人</p>
									</label>
										<label for="other" class="col-md-4" onclick="labelN0fCActive(this);clearCheckNotNull('campanySize')">
										<div><div></div></div>
										<p>大于100人</p>
									</label>
										<input type="radio" name="theNumberOf" id="10" value="1">
										<input type="radio" name="theNumberOf" id="30" value="2">
										<input type="radio" name="theNumberOf" id="100" value="3">
										<input type="radio" name="theNumberOf" id="other" value="4">
										<input type="hidden" value="${userInfo.companySize!" "}" id="theNumberOfHidden">
									</div>
									<span class="col-md-11 col-md-offset-1 warningDiv marginTop">请选择贵公司的规模</span>
								</div>

								<div class=" col-md-10 col-md-offset-1 companycanworktype" id="companyCanWorkType">
									<label class="col-md-12"><i class="form-required">*</i>能胜任的工作类型:</label>
									<div class="col-lg-10 col-md-11 col-md-offset-1">
										<#list listDictItem as tag>
											<#if tag.type=="cando">
												<label for="cCT${tag.value}" class="col-md-4" onclick="labelcheckboxActive(this);clearCheckNotNull('companyCanWorkType')">
											<div><img src="${ctx}/img/checkboxchecked.png"></div>
											<p>${tag.name}</p>
										</label>

												<input type="checkbox" name="companyworktype" id="cCT${tag.value}" value="${tag.value}">
											</#if>
										</#list>
										<input type="hidden" value="${userInfo.cando!" "}" id="companyworktypeHidden">
									</div>
									<span class="col-md-11 col-md-offset-1 warningDiv marginTop">请选择贵公司能胜任的工作类型</span>
								</div>

								<div class=" col-md-10 col-md-offset-1 companyexcelstechnologies" id="companyEtechnologies">
									<label class="col-md-12"><i class="form-required">*</i>擅长的技术:</label>
									<div class="col-lg-10 col-md-11">
										<#list listDictItem as tag>
											<#if tag.type=="ability">
												<label for="cET${tag.value}" class="col-md-2 col-md-offset-1">${tag.name}</label>

												<input type="checkbox" name="companytechnology" value="${tag.value}" id="cET${tag.value}">
											</#if>
										</#list>
										<input type="text" id="companytechnology" class="col-md-11 col-md-offset-1" onkeyup="clearCheckNotNull('companyEtechnologies')" placeholder="其他技术">
										<input type="hidden" id="companytechnologyCheckbox" value="${userInfo.mainAbility!" "}"/>
										<input type="hidden" id="companytechnologyText" value="${userInfo.otherAbility!" "}"/>
									</div>
									<span class="col-md-11 col-md-offset-1 warningDiv marginTop">请选择或填写贵公司擅长的技术</span>
								</div>

								<div class=" col-md-10 col-md-offset-1 producttype" id="productType">
									<label class="col-md-12" style="padding:2% 0px"><i class="form-required">*</i>产品类型:</label>
									<div class="col-lg-11 col-md-12">
										<#list listDictItem as tag>
											<#if tag.type=="caseType">
												<label for="CPT${tag.value}" class="col-md-5 col-md-offset-1">${tag.name}</label>

												<input type="checkbox" name="companyProductType" value="${tag.value}" id="CPT${tag.value}">
											</#if>
										</#list>
										<input type="text" id="companyProductType" class="col-md-11 col-md-offset-1" onkeyup="clearCheckNotNull('productType')" placeholder="其他产品类型">
										<input type="hidden" value="${userInfo.caseType!" "}" id="companyProductTypecheckbox" />
										<input type="hidden" id="companyProductTypetext" value="${userInfo.otherCaseType!" "}" />
									</div>
									<span class="col-md-11 col-md-offset-1 warningDiv" style="margin-top:-15px">请选择贵公司的产品类型</span>
								</div>

								<div class=" col-md-10 col-md-offset-1 companyProjectExperience" id="addCPE">
									<label class="col-md-6"><i class="form-required">*</i>经典案例:</label>
									<#if userInfo.employeeProjectExperience?? && (userInfo.employeeProjectExperience?size>0) >
										<span id="addCProject" href="#addCompanyProjectExperience" onclick="clearCheckNotNull('addCPE')" class="col-md-2 col-md-offset-4" data-toggle="modal">
											<img src="${ctx}/img/add.png" style="float:right">
											<p style="display: none;">项目经验</p>
										</span>
										<#list userInfo.employeeProjectExperience as tag>
											<div class="col-md-12" style="font-size:14px;margin-top:10px;border-bottom: dashed 1px #ccc;margin-bottom: 10px;">
												<div class="col-md-9" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">项目名称: </i>${tag.projectName}</div>
												<div class="col-md-2 col-md-offset-1">
													<img style="width:26px;height:26px;padding-bottom:3px" href="#addCompanyProjectExperience" data-toggle="modal" onclick="revisecompanyWorkExperience(this)" src="${ctx}/img/revise.png" />
													<img src="${ctx}/img/delect.png" style="width:23px;height:23px;padding-bottom:1px" onclick="deletecompanyWorkExperience(this)" />
												</div>
												<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">项目时间: </i>${tag.startTime} - ${tag.endTime}</div>
												<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">项目描述: </i>${tag.description}</div>
												<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">项目链接: </i>${tag.link}</div>
											</div>
										</#list>
										<#else>
											<span id="addCProject" href="#addCompanyProjectExperience" onclick="clearCheckNotNull('addCPE')" class="col-md-6 col-md-offset-5" data-toggle="modal">
											<img src="${ctx}/img/add.png">
											<p>项目经验</p>
										</span>
									</#if>
									<span class="col-md-12 warningDiv marginTop">请添加贵公司完成的经典案例</span>
								</div>

								<div class=" col-md-10 col-md-offset-1 companyIntro" id="addCI">
									<label class="col-md-12"><i class="form-required">*</i>公司简介</label>
									<div class="col-md-12" style="padding:0 0 2% 0">
										<textarea class="col-md-12 addCIntro" onkeyup="clearCheckNotNull('addCI')" name="addCIntro" id="addCIntro" cols="30" s="10"></textarea>
									</div>
									<input type="hidden" id="addCIntroHidden" value="${userInfo.introduction!" "}">
									<span class="col-md-12 warningDiv marginTop">请简易对贵公司做一个介绍</span>
								</div>

								<div class="col-xs-10 col-xs-offset-1" style="padding: 0px;">
									<div class="col-xs-6" style="padding: 0px;">
										<button type="button" class="btn button-style" onclick="">取消</button>
									</div>
									<div class="col-xs-6 text-right" style="padding: 0px;">
										<button type="submit" class="btn button-style">提交</button>
									</div>
								</div>
								</form>
							</div>
						</div>
						<!--公司修改结束-->

						<div id="autheninfo" class="tab-pane">
							<div class="col-lg-9 col-md-9 col-sm-10 col-xs-12 tab-form">
								<div class="col-lg-offset-1 col-md-offset-1 col-sm-offset-1 col-xs-offset-1">
									<div class="col-xs-10 col-md-10 form-header">
										<h3>认证信息
		       			<#if userInfo.identifyInfo.status == 0>
		       		 		<span class="badge badge-warning badge-md"><i class="fa fa-tag"></i>审核中</span>
		       		 	<#elseif userInfo.identifyInfo.status == 1>
		       		 		<span class="badge badge-success badge-md"><i class="fa fa-tag"></i>已认证</span>
		       		 	<#elseif  userInfo.identifyInfo.status == 2>
		       		 		<span class="badge badge-danger badge-md"><i class="fa fa-tag"></i>认证未通过</span>
		       		 	</#if>
		       			</h3>
									</div>

									<#if Session.SESSION_LOGIN_USER.userInfoVo.userType=- 1 && userInfo.identifyInfo.status !=0>
										<div class="col-xs-2 col-md-2 form-header">
											<input class="btn btn-primary" style="margin-top: 20px;" type="button" value="修改" onclick="resumeInput()" />
										</div>
									</#if>

									<#if userInfo.userType==1 || userInfo.userType==0>
										<#if userInfo.identifyInfo.status==2>
											<div class="col-lg-12 col-md-12 form-desc-line">
												<p class="i-label">认证不通过原因：</p>
												<p class="error-message">${userInfo.identifyInfo.failReason}</p>
												<br>
											</div>
										</#if>
										<div class="col-md-12 col-lg-12 form-desc-line">
											<p class="i-label">认证类型:</p>
											<span class="badge badge-success badge-md">
										<#if userInfo.identifyInfo.category == 0>
								           个人
						      	 <#else>
						      	   企业
						      	 </#if>
									</span>
										</div>
										<#if userInfo.identifyInfo.category==1>
											<div class="col-md-12 col-lg-12 form-desc-line">
												<p class="i-label">公司名称：</p>
												<p>${userInfo.identifyInfo.companyName!""}</p>
											</div>
											<div class="col-md-12 col-lg-12 form-desc-line">
												<p class="i-label">公司地址：</p>
												<p>${userInfo.identifyInfo.companyAddr!""}</p>
											</div>
											<div class="col-md-12 col-lg-12 form-desc-line">
												<p class="i-label">支付宝账号：</p>
												<p>${userInfo.accountNum!""}</p>
											</div>
											<div class="col-md-12 col-lg-12 form-desc-line">
												<p class="i-label">法人代表：</p>
												<p>${userInfo.identifyInfo.legalRepresent!""}</p>
											</div>
											<div class="col-md-12 col-lg-12 form-desc-line">
												<p class="i-label">公司电话：</p>
												<p>${userInfo.identifyInfo.companyPhone!""}</p>
											</div>

											<div class="col-md-12 col-lg-12 form-desc-line">
												<p class="i-label">营业执照号：</p>
												<p> ${userInfo.identifyInfo.businessLicense!""}</p>
											</div>

											<div class="col-md-12 col-lg-12 form-desc-line">
												<p class="i-label">公司规模：</p>
												<p>
													<#if userInfo.companySize??>
														<#switch userInfo.companySize>
															<#case '1'>小于10人
																<#break>
																	<#case '2'>10-30人
																		<#break>
																			<#case '3'>31-100人
																				<#break>
																					<#case '4'>100人以上
																						<#break>
																							<#default>小于10人
														</#switch>
													</#if>
												</p>
											</div>

											<div class="col-md-12 col-lg-12 form-desc-line">
												<p class="i-label">公司官网：</p>
												<p>${userInfo.identifyInfo.siteLink!""}</p>
											</div>

											<div class="col-md-12 col-lg-12 form-desc-line">
												<p class="i-label">公司介绍：</p>
												<p>${userInfo.introduction!""}</p>
											</div>

											<div class="col-md-12 col-lg-12 form-desc-line">
												<p class="i-label">营业执照照片：</p>
												<a target="_blank" href="${userInfo.identifyInfo.businessLicenseImg!" "}"><img class="img-responsive authen-img" src="${userInfo.identifyInfo.businessLicenseImg!" "}"></img>
												</a>
											</div>

											<div></div>

											<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
												<p class="col-md-12">能胜任的工作类型:</p>
												<div class="col-md-12">
													<#if userInfo.displayCando??>
														<#list userInfo.displayCando as tag>
															<div class="col-xs-4 col-md-3 text-center">${tag}</div>
														</#list>
													</#if>
												</div>
											</div>
											<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
												<p class="col-md-12">擅长的技术:</p>
												<div class="col-md-9 col-md-offset-1">
													<#if userInfo.displayMainAbility??>
														<#list userInfo.displayMainAbility as tag>
															<div class='col-md-2 col-md-offset-1 AuthenticationInformationDiv'>${tag}</div>
														</#list>
													</#if>
													<#if userInfo.otherAbility?? && userInfo.otherAbility !="">
														<div class="col-md-11 col-md-offset-1 AuthenticationInformationInput">${userInfo.otherAbility!""}</div>
														<#else>
															<div style="display: none;" class="col-md-11 col-md-offset-1 AuthenticationInformationInput">${userInfo.otherAbility!""}</div>
													</#if>
												</div>
											</div>
											<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
												<p class="col-md-12">产品类型:</p>
												<div class="col-md-10 col-md-offset-1">
													<#if userInfo.displayCaseType??>
														<#list userInfo.displayCaseType as tag>
															<div class='col-md-5 col-md-offset-1 PType'>${tag}</div>
														</#list>
													</#if>
													<#if userInfo.otherCaseType??>
														<div class="col-md-8 col-md-offset-1 PTypeInput">${userInfo.otherCaseType!""}</div>
														<#else>
															<div style="display: none;" class="col-md-8 col-md-offset-1 PTypeInput">${userInfo.otherCaseType!""}</div>
													</#if>

												</div>
											</div>
											<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
												<p class="col-md-12">经典案例:</p>
												<#if userInfo.employeeProjectExperience??>
													<#list userInfo.employeeProjectExperience as tag>
														<div class="col-md-10 col-md-offset-1" style="font-size:14px;margin-top:10px;border-bottom: dashed 1px #ccc;margin-bottom: 10px">
															<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">项目名称: </i>${tag.projectName}</div>
															<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">项目时间: </i>${tag.startTime} - ${tag.endTime}</div>
															<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">项目描述: </i>${tag.description}</div>
															<div class="col-md-12"><i style="color:#3598db;font-style:normal">项目链接: </i>${tag.link}</div>
														</div>
													</#list>
												</#if>

											</div>

											<#elseif userInfo.identifyInfo.category==0>
												<#if (userInfo.resumeType=="input" ) || (userInfo.resumeType !="input" && userInfo.identifyInfo.status==1 )>
													<div class="col-md-12 col-lg-12 form-desc-line">
														<p class="i-label">真实姓名：</p>
														<p>${userInfo.identifyInfo.realName!""}</p>
													</div>
													<div class="col-md-12 col-lg-12 form-desc-line">
														<p class="i-label">身份证号码：</p>
														<p>${userInfo.identifyInfo.idCard!""}</p>
													</div>
													<div class="col-md-12 col-lg-12 form-desc-line">
														<p class="i-label">支付宝账号：</p>
														<p>${userInfo.accountNum!""}</p>
													</div>
													<div class="col-md-12 col-lg-12 form-desc-line">
														<p class="i-label">工作状态：</p>
														<p>
															<#if userInfo.freelanceType??>
																<#switch userInfo.freelanceType>
																	<#case 0>可兼职接活
																		<#break>
																			<#case 1>自由职业者
																				<#break>
																					<#case 2>在校学生
																						<#break>
																							<#default>在校学生
																</#switch>
															</#if>
														</p>
													</div>
													<div class="col-md-12 col-lg-12 form-desc-line">
														<p class="i-label">身份证照片：</p>
														<a target="_blank" href="${userInfo.identifyInfo.idCardImg!" "}"><img class="img-responsive authen-img" src="${userInfo.identifyInfo.idCardImg!" "}"></img>
														</a>
													</div>

													<div></div>

													<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
														<p class="col-md-12">能胜任的工作类型:</p>
														<div class="col-md-12">
															<#if userInfo.displayCando??>
																<#list userInfo.displayCando as tag>
																	<div class="col-xs-4 col-md-3 text-center">${tag}</div>
																</#list>
															</#if>
														</div>
													</div>
													<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
														<p class="col-md-12">擅长的技术:</p>
														<div class="col-md-9 col-md-offset-1">
															<#if userInfo.displayMainAbility?? && userInfo.displayMainAbility?size gt 0>
																<#list userInfo.displayMainAbility as tag>
																	<div class='col-md-2 col-md-offset-1 AuthenticationInformationDiv'>${tag}</div>
																</#list>
															</#if>

															<#if userInfo.otherAbility?? && userInfo.otherAbility !="">
																<div class="col-md-11 col-md-offset-1 AuthenticationInformationInput">${userInfo.otherAbility!""}</div>
																<#else>
																	<div style="display: none;" class="col-md-11 col-md-offset-1 AuthenticationInformationInput">${userInfo.otherAbility!""}</div>
															</#if>

														</div>
													</div>
													<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation" style="padding-bottom:50px">
														<p class="col-md-12">工作经验:</p>
														<#if userInfo.employeeJobExperience??>
															<#list userInfo.employeeJobExperience as tag>
																<div class="col-md-10 col-md-offset-1" style="font-size:14px;margin-top:10px;border-bottom: dashed 1px #ccc;margin-bottom: 10px;padding:10px 0px">
																	<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">公司名称: </i>${tag.companyName}</div>
																	<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">公司时间: </i>${tag.startTime} - ${tag.endTime}</div>
																	<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">公司职位: </i>${tag.office}</div>
																	<div class="col-md-12"><i style="color:#3598db;font-style:normal">公司内容: </i>${tag.description}</div>
																</div>
															</#list>
														</#if>
													</div>

													<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation" style="padding-bottom:50px">
														<p class="col-md-12">项目产品:</p>
														<#if userInfo.employeeProduct??>
															<#list userInfo.employeeProduct as tag>
																<div class="col-md-10 col-md-offset-1" style="font-size:14px;margin-top:10px;;border-bottom: dashed 1px #ccc;margin-bottom: 10px;padding:10px 0px">
																	<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">标题: </i>${tag.title}</div>
																	<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">描述: </i>${tag.description}</div>
																	<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">链接: </i>${tag.link}</div>
																</div>
															</#list>
														</#if>
													</div>
													<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation" style="padding-bottom:50px">
														<p class="col-md-12">教育背景:</p>
														<#if userInfo.employeeEduExperience??>
															<#list userInfo.employeeEduExperience as tag>
																<div class="col-md-10 col-md-offset-1" style="font-size:14px;margin-top:10px;border-bottom: dashed 1px #ccc;margin-bottom: 10px;padding: 10px 0px;">
																	<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">学校名称: </i>${tag.schoolName}</div>
																	<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">所学专业: </i>${tag.discipline}</div>
																	<div class="col-md-12" style="margin-bottom: 10px;"><i style="color:#3598db;font-style:normal">教育背景: </i>${tag.eduBackgroud}</div>
																	<div class="col-md-12"><i style="color:#3598db;font-style:normal">毕业年份: </i>${tag.graduationTime}</div>
																</div>
															</#list>
														</#if>
													</div>

													<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation" style="padding-bottom:50px">
														<p class="col-md-12">个人简介:</p>
														<div class="col-md-10 col-md-offset-1" style="font-size:14px;margin-top:10px;border-bottom: dashed 1px #ccc;margin-bottom: 10px;padding: 10px 0px;word-break: break-all;">
															${userInfo.introduction!""}
														</div>
													</div>

													<#if userInfo.resumeType?? && userInfo.resumeType !="input">
														<div class="col-md-12 col-xs-12 form-desc-line">
															<p class="i-label">原始简历：</p>
															<#if userInfo.resumeType=="file">
																<p>
																	<a href="${userInfo.resumeUrl!" "}" target="_blank">
																		<#if userInfo.resumeUrl?index_of( ".doc") !=- 1>
																			<i class="fa fa-file-word-o"></i>
																			<#elseif userInfo.resumeUrl?index_of( ".pdf") !=- 1>
																				<i class="fa fa-file-pdf-o"></i>
																				<#elseif userInfo.resumeUrl?index_of( ".zip") !=- 1>
																					<i class="fa fa-file-zip-o"></i></#if>点此查看</a>
																</p>
																<#else>
																	<p><a href="${userInfo.resumeUrl}" target="_blank">${userInfo.resumeUrl}<a></p>
												</#if>
									  </div>
									 </#if>
									<#elseif userInfo.resumeType != "input">
									 <div class="col-md-12 col-lg-12 form-desc-line">
										  <p  class="i-label">真实姓名：</p>
										  <p>${userInfo.identifyInfo.realName!""}</p>
									  </div>
									  <div class="col-md-12 col-lg-12 form-desc-line">
										  <p  class="i-label">身份证号码：</p>
										  <p>${userInfo.identifyInfo.idCard!""}</p>
									  </div>
									  <div class="col-md-12 col-lg-12 form-desc-line">
										  <p  class="i-label">支付宝账号：</p>
										  <p>${userInfo.accountNum!""}</p>
									  </div>

									  <#if userInfo.resumeType?? && userInfo.resumeType != "input">
									   	<div class="col-md-6 col-xs-6 form-desc-line">
									  <#else>
									  	<div class="col-md-12 col-xs-12 form-desc-line">>
										</#if>
													<p  class="i-label">个人简历：</p>
										        <#if userInfo.resumeType == "file">
													<p>
														<a href="${userInfo.resumeUrl!""}" target="_blank">
														<!--<a href="http://officeweb365.com/o/?i=9648&ssl=1&furl=${userInfo.resumeUrl!""}" target="_blank">-->
													<#if userInfo.resumeUrl?index_of(".doc") != -1>
											<i class="fa fa-file-word-o"></i>
											<#elseif  userInfo.resumeUrl?index_of(".pdf") != -1>
											<i class="fa fa-file-pdf-o"></i>
											<#elseif  userInfo.resumeUrl?index_of(".zip") != -1>
											<i class="fa fa-file-zip-o"></i></#if>点此查看</a></p>
																	<#else>
																		<p><a href="${userInfo.resumeUrl}" target="_blank">${userInfo.resumeUrl}<a></p>

												</#if>
									  </div>
									  <#if userInfo.resumeType?? && userInfo.resumeType != "input">
									  	<div class="col-md-6 col-xs-6">
									  		<button class="btn btn-primary" onclick="resumeInput()">录入</button>
									  	</div>

									  </#if>
									  <div class="col-md-12 col-lg-12 form-desc-line">
										  <p  class="i-label">身份证照片：</p>
										  <a target="_blank" href="${userInfo.identifyInfo.idCardImg!""}"><img class="img-responsive authen-img" src="${userInfo.identifyInfo.idCardImg!""}"></img></a>
														</div>
														</#if>
													</#if>
												</#if>
								</div>
							</div>
						</div>
						</#if>
						<#if userInfo.identifyInfo?? && userInfo.identifyInfo.status==0>
							<div id="authenops" class="tab-pane">
								<div class="col-lg-9 col-md-9 cpl-sm-10 col-xs-12 tab-form">
									<div class="col-lg-offset-1 col-md-offset-1 col-sm-offset-1 col-xs-offset-1">
										<div class="form-header">
											<h3>审核操作</h3>
										</div>
										<form class="form-horizontal" id="userReviewForm" action="javascript:doSaveReviewInfo()" role="form" method="post">
											<fieldset>
												<div class="form-group" id="reviewOperationDiv">
													<div class="col-lg-2 col-md-3">
														<lable class="form-label">
															<input type="radio" id="review_unok" name="review" onclick="checkReview(this);" value="1" required checked></input>不通过</label>
													</div>

													<div class="col-lg-2 col-md-3">
														<lable class="form-label">
															<input type="radio" id="review_ok" name="review" onclick="checkReview(this);" value="0" required></input>通过</label>
													</div>

													<div id="reviewMsgDiv" class="col-lg-12 col-md-12"></div>
												</div>

												<div id="reviewDescDiv" class="form-group" style="display:none">
													<div class="col-lg-4 col-md-4">
														<label class="control-label form-label" for="reviewDesc"><i class="form-required">*</i>审核意见（至少10个字符）：</label>
													</div>
													<div class="col-lg-11 col-md-11">
														<textarea class="form-control" s="5" name="reviewDesc" id="reviewDesc" required minlength="10"></textarea>
													</div>
												</div>
											</fieldset>

											<br>
											<div class="col-lg-4 col-md-4">
												<button id="submitBtn" type="submit" class="btn btn-lg btn-block btn-primary">提交</button>
											</div>
										</form>
									</div>
								</div>
							</div>
						</#if>
				</div>
			</div>

			<div class="modal" id="noteDialog">
				<div class="modal-dialog modal-custom-class">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
							<h4 class="modal-title">我要备注</h4>
						</div>
						<div class="modal-body">
							<form class="form-horizontal form-in-modal" id="noteForm" action="javascript:doRemark();" role="form" method="post">
								<div class="form-group">
									<div class="form-group">
										<div class="col-lg-12 col-md-12">
											<label class="control-label form-label" for="jPeriod"><i class="form-required">*</i>备注内容（至少5个字符）:</label>
										</div>
										<div class="col-lg-11 col-md-11">
											<textarea id="jNote" name="jNote" class="form-control form-input-area" type="text" s="5" required minlength="5" maxlength="500"></textarea>
										</div>
									</div>

									<div>
										<button type="button" class="btn btn-lg btn-default form-btn " data-dismiss="modal">取消</button>
										<button type="submit" class="btn btn-lg btn-primary form-btn">保存</button>
									</div>
							</form>
							</div>
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
					<input type="hidden" id="pass" value="${userInfo.resumeType!" "}"/>
					<input type="hidden" id="topurl" value="${ctx}/" />
					<input type="hidden" id="reload" value="1" />
				</div>
				<script type="text/javascript" src="${ctx}/js/home/userinfo.js?v=${version}"></script>
	</body>

</html>
