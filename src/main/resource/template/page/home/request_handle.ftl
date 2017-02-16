
<!DOCTYPE html>
<html lang="zh-CN" ng-app="handleApp" ng-Controller="handleController">

<head>
	<title>项目管理 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
	<#include "../common.ftl">
	<#include "../comm-includes.ftl">
	${basic_includes}
	${tools_includes}

	<link rel="stylesheet" href="${ctx}/css/home/request_handle.css?v=${version}">
	<script type="text/javascript" src="${ctx}/js/home/request_handle.js?v=${version}"></script>
	<script src="${ctx}/js/angular-sanitize.min.js"></script>
</head>

<body>
	<!--header-->
	<#include "../header.ftl">
	<!--end of header-->

	<input type='hidden' id='projectId' value='${projectInSelfRun.id}' />
	<input type="hidden" id="projectStatus" value='${projectInSelfRun.status}'/>
	<input type='hidden' id='accessType' value='${accessType}' />
	<input type="hidden" id="currentLoginUserId" value='${Session.SESSION_LOGIN_USER.userInfoVo.id}'/>
	<input type="hidden" id="progressFeedbackHidden" value=""/>

<!-- 进展反馈 -->
	<div class="modal" id="progressFeedback">
	  <div class="modal_plan">
	    <div class="modal-content">
	      <div class="modal_title chooseRoleFunctionTitle">
	       	<div class="col-md-6 col-sm-6 col-xs-6">
	      		<img src="${store_location}/img/Feedback_icon.png" style="width: 25px;height: 25px;" alt="" />
	      		<span>进展反馈</span>
	      	</div>
	      	<div class="col-md-6 col-sm-6 col-xs-6 text-right">
	      		<img data-dismiss="modal" style="cursor: pointer;" src="${store_location}/img/project_functions_cancle.png" alt="" />
	      	</div>
	      </div>
	      <div class="modal_body_functions">
		        <form action="javascript:createProgressFeedback()" id="progressFeedbackForm">
		        	<input type='hidden' id='stepId' value=''/>
		        	<div class="col-xs-12 col-md-12 col-sm-12">
			        	<div class="col-md-12 col-sm-12 col-xs-12 padding-right">
			        		<div class="col-md-12 col-sm-12 col-xs-12 padding" style="margin:15px auto;"><span style="color: red;">*</span>进展反馈(至少10个字)</div>
			        		<div class="col-md-12 col-sm-12 col-xs-12 padding">
			        			<textarea class="form-control plan_textarea" id='monitorDesc' minlength="10" required></textarea>
			        		</div>
			        	</div>

						<!--header-->
		            	<div class="col-md-12 col-sm-12 col-xs-12" style="margin:15px auto;">附件</div>
		            	<div class='col-md-12 col-xs-12 col-sm-12 padding-right'>
		            		<input id="fileselect-monitor" name="pic" class="picselect file" type="file"></input>
		            	</div>
		            	<div class='col-md-12 fileupload-info-box'>
							<div class="col-md-10 project-handle-item-content padding-left" style="margin-left: 0px;">
								<div id='monitorFileInfo' class="alert alert-danger alert-dismissible fileupload-info" role="alert">
								  <button type="button" class="close"><span aria-hidden="true" class='info-close'>&times;</span></button>
								  <strong>错误!</strong><span class='info'></span>
								  <img id="tempimg" dynsrc="" src="" style="display:none" />
								</div>
							</div>
							<div class='col-md-12' style="margin-left:0px;">
								<div class="col-md-12 col-sm-12 col-xs-12 padding project-handle-item-content">
									<p style='color:#ccc;'>将需求描述文件上传,文件支持20M以内的doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar、jpg、png、jpeg、bmp、gif格式,也可联系码客帮客服上传。</p>
								</div>
							</div>
						</div>
		            	<!--header end-->
		            	<div class='col-md-12 col-xs-12 col-sm-12'>
								<div id='monitorFileList' class="col-md-10 project-handle-item-content file-list padding-left" style="margin-left: 0px;margin-top: 10px;margin-bottom: 10px;" max='5'>
									<!--附件模型-->
									<div class="btn-group attach-group attach-model">
								      	<button type="button" class="btn btn-primary btn-xs attach">PrimaryPrimaryPrimary</button>
								      	<button type="button" class="btn btn-primary btn-xs attach-del">
								       		<span class="fa fa-times"></span>
								        	<span class="sr-only">Toggle Dropdown</span>
								      	</button>
								    </div>
								    <!--附件模型end-->

									<input type='hidden' class='attachment' id='attachment' value=''/>
								 </div>
						</div>
						<!-- footer-->
		        		<div class="col-md-12 col-xs-12 col-sm-12 padding-right functionMarginTop">
		        			<div class="col-md-5 col-sm-5 col-xs-5 text-left padding">
		        				<input type="button" value="取消" data-dismiss="modal" class="btn btn-cancel btn-functions btn-cancel-functions"/>
		        			</div>
		        			<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-5 col-sm-5 col-xs-5 text-right padding">
		        				<input type="submit" value="确定" class="btn btn-primary btn-functions"/>
		        			</div>
		        		</div>
		        	</div>
		        </form>
	       </div>
	    </div>
	  </div>
	</div>

<!-- 计划 -->
	<div class="modal" id="plan">
	  <div class="modal_plan">
	    <div class="modal-content">
	      <div class="modal_title chooseRoleFunctionTitle">
	       	<div class="col-md-6 col-sm-6 col-xs-6">
	      		<img src="${store_location}/img/plan_icon.png" style="width: 25px;height: 25px;" alt="" />
	      		<span>阶段计划</span>
	      	</div>
	      	<div class="col-md-6 col-sm-6 col-xs-6 text-right">
	      		<img data-dismiss="modal" style="cursor: pointer;" src="${store_location}/img/project_functions_cancle.png" alt="" />
	      	</div>
	      </div>
	      <div class="modal_body_functions">
		        <form action="javascript:createPlan()" id="planForm">
		        	<div class="col-xs-12 col-md-12 col-sm-12">
			        	<div class="col-md-12 col-sm-12 col-xs-12 padding-right plan_margin_top">
			        		<div class="col-md-6 col-sm-6 col-xs-6 padding">
			        			<div class="col-md-3 col-sm-3 col-xs-5 plan_title padding"><span style="color: red;">*</span>开始时间</div>
			        			<div class="input-group col-md-9 col-sm-9 col-xs-7 date planStartTime" style="padding: 0px 15px 0px 0px;" id="planStartTimeDiv">
			        				<input type="text" class="form-control form-input-small" id="planStartTime" required name="planStartTime"/>
			        				<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
									<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
			        			</div>
			        			<div name="planStartTimeMsg" id="planStartTimeMsg"></div>
			        		</div>
			        		<div class="col-md-6 col-sm-6 col-xs-6 padding">
			        			<div class="col-md-3 col-sm-3 col-xs-5 plan_title padding text-right"><span style="color: red;">*</span>结束时间</div>
			        			<div class="input-group col-md-9 col-sm-9 col-xs-7 planEndTime date padding-right" style="padding: 0px 0px 0px 15px;" id="planEndTimeDiv">
			        				<input type="text" class="form-control" id="planEndTime" name="planEndTime" required checkTime="true"/>
			        				<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
									<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
			        			</div>
			        			<div name="planEndTimeMsg" style="padding-left: 30px;" id="planEndTimeMsg"></div>
			        		</div>
			        	</div>

						<div class="col-md-12 col-sm-12 col-xs-12 padding-right plan_margin_top">
			        		<div class="col-md-6 col-sm-6 col-xs-6 padding">
			        			<div class="col-md-3 col-sm-3 col-xs-5 plan_title padding"><span style="color: red;">*</span>阶段状态</div>
			        			<div class="col-md-9 col-sm-9 col-xs-7 padding-left">
			        				<select id="status" class="form-control" name="status" required>
										<#list dictItemList as item>
											<#if item.type == 'planStatus'>
												<option value='${item.value}'>${item.name}</option>
											</#if>
										</#list>
			        				</select>
			        			</div>
			        		</div>
			        		<div class="col-md-6 col-sm-6 col-xs-6 padding">
			        			<div class="col-md-3 col-sm-3 col-xs-5 plan_title padding text-right"><span style="color: red;">*</span>阶段金额</div>
			        			<div class="col-md-9 col-sm-9 col-xs-7 padding-right"><input type="text" name="price" id="price" class="form-control" required checkPrice="true"/></div>
			        			<div style="padding-left: 30px;" name="priceMsg" id="priceMsg"></div>
			        		</div>
			        	</div>

			        	<div class="col-md-12 col-sm-12 col-xs-12 padding-right plan_margin_top">
			        		<div class="col-md-6 col-sm-6 col-xs-6 padding">
			        			<div class="col-md-3 col-sm-3 col-xs-5 plan_title padding"><span style="color: red;">*</span>阶段执行者</div>
			        			<div class="col-md-9 col-sm-9 col-xs-7 padding-left">
			        				<select id='coper' name="coper" class="form-control" required>
			        					<!--执行者-->
			        				</select>
			        			</div>
			        		</div>

			        		<div class="col-md-6 col-sm-6 col-xs-6 padding isDelay">
			        			<div class="col-md-3 col-sm-3 col-xs-5 plan_title padding text-right"><span style="color: red;">*</span>是否延期</div>
			        			<div class="col-md-9 col-sm-9 col-xs-7 padding-right">
			        				<select id="isDelay" class="form-control" name="isDelay" required>
			        					<option value="0">否</option>
			        					<option value="1">是</option>
			        				</select>
			        			</div>
			        		</div>

			        	</div>

		        		<div class="col-md-12 col-sm-12 col-xs-12 padding plan_margin_top">
		        			<div class="col-md-12 col-sm-12 col-xs-12 plan_title">
		        				<span style="color: red;">*</span>阶段描述(不少于10个字)
		        			</div>
		        			<div class="col-md-12 col-xs-12 col-xs-12 padding-right">
		        				<textarea id="describe" name="describe" class="form-control plan_textarea" required minlength="10"></textarea>
		        			</div>
		        		</div>

		        		<div class="col-md-12 col-xs-12 col-sm-12 padding-right functionMarginTop">
		        			<div class="col-md-5 col-sm-5 col-xs-5 text-left padding">
		        				<input type="button" value="取消" data-dismiss="modal" class="btn btn-cancel btn-functions btn-cancel-functions"/>
		        			</div>
		        			<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-5 col-sm-5 col-xs-5 text-right padding">
		        				<input type="submit" value="确定" class="btn btn-primary btn-functions"/>
		        			</div>
		        		</div>
		        	</div>
		        </form>
	       </div>
	    </div>
	  </div>
	</div>

<!-- 选择角色职能 -->
	<div class="modal" id="functionChoice">
	  <div class="modal_dialog">
	    <div class="modal-content">
	      <div class="modal_title chooseRoleFunctionTitle">
	       	<div class="col-md-6 col-sm-6 col-xs-6">
	      		<img src="${store_location}/img/projectRole.png" style="width: 25px;height: 25px;" alt="" />
	      		<span>选择角色</span>
	      	</div>
	      	<div class="col-md-6 col-sm-6 col-xs-6 text-right">
	      		<img data-dismiss="modal" style="cursor: pointer;" src="${store_location}/img/project_functions_cancle.png" alt="" />
	      	</div>
	      </div>
	      <div class="modal_body_functions">
		        <form action="javascript:addChosenDev()" id="functionChoiceForm">
		        	<div class="col-xs-12 col-md-12 col-sm-12">
		        	<#list projectDevRoles as projectDevRole>
		        		<div class="col-md-4 col-sm-4 col-xs-6"><div class="functions" value="${projectDevRole.value!''}" onclick="choosefunctions(this)">${projectDevRole.name!''}</div></div>
		        	</#list>
		        		<div class="col-md-12 col-xs-12 col-sm-12 functionMarginTop">
		        			<div class="col-md-5 col-sm-5 col-xs-5 text-left padding">
		        				<input type="button" value="取消" data-dismiss="modal" class="btn btn-cancel btn-functions btn-cancel-functions"/>
		        			</div>
		        			<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-5 col-sm-5 col-xs-5 text-right padding">
		        				<input type="submit" value="确定" class="btn btn-primary btn-functions"/>
		        			</div>
		        		</div>
		        	</div>
		        </form>
		        <div class id="functionChoiceMsg" style="display: none;color: red;padding: 0px 30px;margin-top: 10px;">至少选择一个角色职能</div>
	       </div>
	    </div>
	  </div>
	</div>

<!-- 添加日志 -->
	<div class="modal" id="addLogDiv">
	  <div class="modal_dialog">
	    <div class="modal-content">
	      <div class="modal_title chooseRoleFunctionTitle">
	       	<div class="col-md-6 col-sm-6 col-xs-6">
	      		<img src="${store_location}/img/Feedback_icon.png" style="width: 25px;height: 25px;" alt="" />
	      		<span>进度日志</span>
	      	</div>
	      	<div class="col-md-6 col-sm-6 col-xs-6 text-right">
	      		<img data-dismiss="modal" style="cursor: pointer;" src="${store_location}/img/project_functions_cancle.png" alt="" />
	      	</div>
	      </div>
	      <div class="modal_body">
		        <form action="javascript:addLog()" id="addLogDivForm">
		        	<div class="col-xs-12 col-md-12 col-sm-12">
		        		<div class="col-md-12 col-sm-12 col-xs-12 openObj">
		        			<div class="col-md-12 col-sm-12 col-xs-12 padding logDiv_title"><span style="color: red;">*</span>公开对象</div>
		        			<div class="col-md-8 col-sm-8 col-xs-8 padding">
		        				<select class="form-control" id="objectOriented" name="objectOriented" required>
		        					<option value="1">所有人可见</option>
		        					<option value="2">仅顾问可见</option>
	        					    <option value="3">仅开发可见</option>
	        					    <option value="4">仅雇主可见</option>
		        				</select>
		        			</div>
		        		</div>
		        		<div class="col-md-12 col-sm-12 col-xs-12 logContent">
		        			<div class="col-md-12 col-xs-12 col-sm-12 padding logDiv_title"><span style="color: red;">*</span>日志内容(至少输入5个字)</div>
		        			<div class="col-md-12 col-sm-12 col-xs-12 padding">
		        				<textarea style="resize: none;height: 160px;" minlength="5" class="form-control" id="journalContent" name="journalContent" required></textarea>
		        			</div>
		        		</div>
		        		<div class="col-md-12 col-xs-12 col-sm-12">
		        			<div class="col-md-6 col-sm-6 col-xs-6 text-left padding">
		        				<input id="addLogDivCancle" type="button" value="取消" data-dismiss="modal" class="btn btn-cancel"/>
		        			</div>
		        			<div class="col-md-6 col-sm-6 col-xs-6 text-right padding">
		        				<input type="submit" value="确定" class="btn btn-primary"/>
		        			</div>
		        		</div>
		        	</div>
		        </form>
	       </div>
	    </div>
	  </div>
	</div>

	<div class="container body">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="col-md-4 col-sm-4 col-xs-12 projectInfo">
				<div class="col-md-12 col-sm-12 col-xs-12 padding">
					<div class="col-md-6 col-sm-6 col-xs-6 projectId padding">NO.${projectInSelfRun.id!""}</div>
					<div class="col-md-6 col-sm-6 col-xs-6 text-right padding">
						<div class="consultant" onclick='_MEIQIA._SHOWPANEL()'>
							<div>咨询顾问</div>
							<div>
								<img src="${store_location}/img/rh_yuyue.png"/>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding projectName">${projectInSelfRun.name!""}</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding projectMsg">
					<div class="col-md-12 col-sm-12 col-xs-12 padding projectMsgDiv">项目雇主：
						<span class="projectMsgSpan">
							<#if projectInSelfRun.creator.name??>
								${projectInSelfRun.creator.name!""}
							<#else>
								${projectInSelfRun.creator.mobile!""}
							</#if>
						</span>
					</div>
					<div class="col-md-12 col-sm-12 col-xs-12 padding projectMsgDiv">项目类型：<span class="projectMsgSpan">${projectInSelfRun.type!""}</span></div>
					<#if projectInSelfRun.status != 1 && projectInSelfRun.status != 2>
						<div class="col-md-12 col-sm-12 col-xs-12 padding projectMsgDiv">项目预算：<span class="projectMsgSpan">${projectInSelfRun.budget!""}</span></div>
					<#else>
						<div class="col-md-12 col-sm-12 col-xs-12 padding projectMsgDiv">项目金额：<span class="projectMsgSpan">${projectInSelfRun.dealCost!""}元</span></div>
					</#if>
					<div class="col-md-12 col-sm-12 col-xs-12 padding projectMsgDiv">创建时间：<span class="projectMsgSpan">${projectInSelfRun.createTime?string("yyyy-MM-dd HH:mm")}</span></div>
					<div class="col-md-12 col-sm-12 col-xs-12 padding projectMsgDiv">项目周期：<span class="projectMsgSpan">${projectInSelfRun.period!""}天</span></div>
					<div class="col-md-12 col-sm-12 col-xs-12 padding projectMsgDiv">分配顾问：<span class="projectMsgSpan">${projectInSelfRun.consultant.name!""}</span></div>
					<div class="col-md-12 col-sm-12 col-xs-12 padding projectMsgDiv">需要购买域名：
						<span class="projectMsgSpan">
	          <#if projectInSelfRun.isNeedBuyDomain == 1>
								是
						<#else>
							  否
						</#if>
						</span>
					</div>
					<div class="col-md-12 col-sm-12 col-xs-12 padding projectMsgDiv">需要购买云主机和数据库：
						<span class="projectMsgSpan">
						<#if projectInSelfRun.isNeedBuyServerAndDB == 1>
								是
						<#else>
								否
						</#if>
						</span>
					</div>

					<input type="hidden" value="${projectInSelfRun.creatorId!""}">
					<input type="hidden" value="${Session.SESSION_LOGIN_USER.userInfoVo.id!""}">
					<#if projectInSelfRun.status == 1 || projectInSelfRun.status == 2>
						<#if projectInSelfRun.creatorId != Session.SESSION_LOGIN_USER.userInfoVo.id>
							<div style="margin-top: 10px;" class="btn btn-primary" onclick="window.open('/home/c/${projectInSelfRun.consultant.mobile}/${projectInSelfRun.repoNick!''}')">
								查看代码
							</div>
						</#if>
					</#if>
				</div>
			</div>
			<div class="col-md-8 col-sm-12 col-xs-12 projectStatusDiv padding-right">
				<div class="col-md-12 padding projectLabel">
					<div class="border-left"></div>
					<div>项目状态</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 projectStatus">
					<#if projectInSelfRun.status == -1>
						<img class="projectStatusImg" src="${store_location}/img/m_request_handle_1.png"/>
					<#elseif projectInSelfRun.status == 0>
						<img class="projectStatusImg" src="${store_location}/img/m_request_handle_2.png"/>
					<#elseif projectInSelfRun.status == 1>
						<img class="projectStatusImg" src="${store_location}/img/m_request_handle_3.png"/>
					<#elseif projectInSelfRun.status == 2>
						<img class="projectStatusImg" src="${store_location}/img/m_request_handle_4.png"/>
					<#elseif projectInSelfRun.status == 4>
						<img src="${store_location}/img/m_request_handle_5.png"/>
					<#elseif projectInSelfRun.status == 3>
						<img src="${store_location}/img/m_request_han dle_6.png"/>
					</#if>
				</div>
			</div>
		</div>

		<#if projectInSelfRun.status == 1 && Session.SESSION_LOGIN_USER.userInfoVo.userType == 2>
		<div class="col-md-12 col-xs-12 col-sm-12 text-center projectDetailsMarginTop">
			<div class="col-md-offset-4 col-sm-offset-4 col-xs-offset-4 col-md-4 col-sm-4 col-xs-4 text-right padding">
				<input type="button" value="结项" onclick="enterFinishStatus()" class="btn btn-primary btn-functions"/>
			</div>
		</div>
		</#if>

		<div class="col-md-12 col-sm-12 col-xs-12 projectDetails">
			<div class="col-md-12 padding projectLabel">
				<div class="border-left"></div>
				<div>项目详情</div>
			</div>
			<div class="col-md-12 col-sm-12 col-xs-12 padding projectDetails_div">
				${projectInSelfRun.content}
			</div>
		</div>

		<#if projectInSelfRun.status == -1 || projectInSelfRun.status == 0>
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="col-md-12 padding projectLabel">
						<div class="border-left"></div>
						<div>项目附件</div>
					</div>
					<div class="col-md-12 col-sm-12 col-xs-12 padding projectAttachment">
						<div class="col-md-8 col-sm-10 col-xs-12 padding-left">
							<div class="col-md-12">
								<#if accessType!=1>
									<div class="col-md-10 padding-left project-handle-item-content">
							           		<input id="fileselect" name="pic" class="picselect file" type="file"></input>
									</div>
									<#else>
									<div id='projectList' class="col-md-10 padding-left project-handle-item-content file-list" max='2'>
										<!--附件模型-->
										<div class="btn-group attach-group attach-model">
									      <button type="button" class="btn btn-primary btn-xs attach">PrimaryPrimaryPrimary</button>
									      <#if accessType!=1>
										      <button type="button" class="btn btn-primary btn-xs attach-del">
										        <span class="fa fa-times"></span>
										        <span class="sr-only">Toggle Dropdown</span>
										      </button>
									      </#if>
									    </div>
									    <!--附件模型end-->

										<input type='hidden' id="projectAttachment" class='attachment' name='attachment' value='${projectInSelfRun.attachment!"[]"}'/>
									</div>
								</#if>
							</div>
							<#if accessType!=1>
								<div class='col-md-12 fileupload-info-box'>
									<div class="col-md-10 padding-left project-handle-item-content">
										<div id='projectFileInfo' class="alert alert-danger alert-dismissible fileupload-info" role="alert">
										  <button type="button" class="close"><span aria-hidden="true" class='info-close'>&times;</span></button>
										  <strong>错误!</strong><span class='info'></span>
										  <img id="tempimg" dynsrc="" src="" style="display:none" />
										</div>
									</div>
								</div>
								<div class='col-md-12'>
									<div class="col-md-12 col-sm-12 col-xs-12 padding project-handle-item-content">
										<p style='color:#ccc;'>将需求描述文件上传,文件支持20M以内的doc、docx、pdf、ppt、pptx、xls、xlsx、zip、jpg、png、jpeg、bmp、gif格式,也可联系码客帮客服上传。</p>
									</div>
								</div>

								<div class='col-md-12'>
									<div id='projectList' class="col-md-10 padding-left project-handle-item-content file-list padding-left" max='2'>
										<!--附件模型-->
										<div class="btn-group attach-group attach-model">
									      <button type="button" class="btn btn-primary btn-xs attach">PrimaryPrimaryPrimary</button>
									      <button type="button" class="btn btn-primary btn-xs attach-del">
									        <span class="fa fa-times"></span>
									        <span class="sr-only">Toggle Dropdown</span>
									      </button>
									    </div>
									    <!--附件模型end-->

										<input type='hidden' id="projectAttachment" class='attachment' name='attachment' value='${projectInSelfRun.attachment!"[]"}'/>
									</div>
								</div>
							</#if>
						</div>
					</div>
				</div>
		<#else>
			<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="col-md-12 padding projectLabel">
						<div class="border-left"></div>
						<div>项目附件</div>
					</div>
					<div class="col-md-12 col-sm-12 col-xs-12 padding projectAttachment">
						<div class="col-md-8 col-sm-10 col-xs-12 padding-left">
							<div class="col-md-12">
									<div id='projectList' class="col-md-10 padding-left project-handle-item-content file-list hidden_del" max='2'>
										<!--附件模型-->
										<div class="btn-group attach-group attach-model">
									      <button type="button" class="btn btn-primary btn-xs attach">PrimaryPrimaryPrimary</button>
									      <#if accessType!=1>
										      <button type="button" class="btn btn-primary btn-xs attach-del">
										        <span class="fa fa-times"></span>
										        <span class="sr-only">Toggle Dropdown</span>
										      </button>
									      </#if>
									    </div>
									    <!--附件模型end-->

										<input type='hidden' id="projectAttachment" class='attachment' name='attachment' value='${projectInSelfRun.attachment!"[]"}'/>
									</div>
							</div>
						</div>
					</div>
				</div>
		</#if>

		<#if Session.SESSION_LOGIN_USER.userInfoVo.userType == 2 && projectInSelfRun.status == 0>
			<div class="col-md-12 col-sm-12 col-xs-12 projectDetails">
				<div class="col-md-12 padding projectLabel">
					<div class="border-left"></div>
					<div>推送列表</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding">
					<div class="col-md-12 col-sm-12 col-xs-12 padding">
						<table class="col-md-12 col-sm-12 col-xs-12 padding entryList" cellpadding="0" border="1" cellspacing="0">
							<tbody>
								<tr class="tableThFloat">
									<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">ID</td>
									<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">用户名</td>
									<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">手机号</td>
									<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">QQ</td>
									<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">微信</td>
									<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">邮箱</td>
									<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">地区</td>
								</tr>
							</tbody>

							<tr class="padding text-center" onclick="showReasonForCompetence(this)" ng-repeat='pushDeveloper in dataList' ng-cloak>
								<td class="text-center"><span ng-bind='pushDeveloper.developerId'></span></td>
								<td class="text-center">
								  <#if Session.SESSION_LOGIN_USER.userInfoVo.userType = -1 ||
									     Session.SESSION_LOGIN_USER.userInfoVo.userType = 2>
										<a style="cursor:pointer;" ng-click='showDevInfo(pushDeveloper.developerId)'>
											<span ng-bind='pushDeveloper.developer.name'></span>
										</a>
									<#else>
										  <span ng-bind='pushDeveloper.developer.name'></span>
									</#if>
								</td>
								<td class="text-center"><span ng-bind='pushDeveloper.developer.mobile'></span></td>
								<td class="text-center"><span ng-bind='pushDeveloper.developer.qq'></span></td>
								<td class="text-center"><span ng-bind='pushDeveloper.developer.weixin'></span></td>
								<td class="text-center reasonForCompetence"><div class="reasonForCompetenceContent"><span ng-bind='pushDeveloper.developer.email'></span></div></td>
								<td class="text-center"><span ng-bind='pushDeveloper.developer.region'></span></td>
							</tr>

						</table>

					<!--推送列表分页-->
					<div class="container" ng-if="showPaginate">
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
								<select data-page-btn="size" ng-model='pageSize' ng-change='setPageSize(pageSize)'>
									<option value="10" ng-selected='true'>10</option>
									<option value="20">20</option>
									<option value="30">30</option>
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
				   </div>
				</div>
				<!--<div class="col-md-12 col-sm-12 col-xs-12 text-center marginTop"><button class="btn btn-primary btn-create" data-toggle="modal" href="#plan">新增</button></div>-->
			</div>

			<div class="col-md-12 col-sm-12 col-xs-12 projectDetails">
				<input type="hidden" id="developerHidden" value=""/>
				<div class="col-md-12 padding projectLabel">
					<div class="border-left"></div>
					<div>开发者报名列表</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding">
					<div class="col-md-12 col-sm-12 col-xs-12 padding">
						<table class="col-md-12 col-sm-12 col-xs-12 padding entryList" cellpadding="0" border="1" cellspacing="0">
							<tbody>
							<tr class="tableThFloat">
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">id</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">用户</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">报名角色</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">擅长技术</td>
								<td class="col-md-3 col-xs-3 col-sm-3 padding text-center">胜任理由</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">操作</td>
							</tr>
							</tbody>
							<#if (enrollDevelopers?size>0) >
								<#list enrollDevelopers as enrollDeveloper>
								<tr class="padding" onclick="showReasonForCompetence(this)">
									<td class="padding text-center">${enrollDeveloper.developerId}</td>
									<td class="padding text-center">
										<#if Session.SESSION_LOGIN_USER.userInfoVo.userType = -1 || Session.SESSION_LOGIN_USER.userInfoVo.userType = 2 >
											<a style="cursor:pointer;" onclick="javascript:window.open('/api/2/idntify/view?uid=${enrollDeveloper.developerId!""}')">${enrollDeveloper.name}</a>
										<#else>
										   ${enrollDeveloper.name}
										</#if>
									</td>
									<td class="padding text-center reasonForCompetence_padding">
										<#if enrollDeveloper.enrollRoleList?? && (enrollDeveloper.enrollRoleList?size>0) >
											<div class="reasonForCompetenceContent">
											<#list enrollDeveloper.enrollRoleList as enrollRole>
												<div>${enrollRole.name}</div>
											</#list>
											</div>
										</#if>
									</td>
									<td class="padding text-center reasonForCompetence_padding">
										<#if enrollDeveloper.developer.displayMainAbility?? && enrollDeveloper.developer.displayMainAbility?size gt 0>
								        	<div class="reasonForCompetenceContent">
								        	<#list enrollDeveloper.developer.displayMainAbility as mainAbility>
								  	  			<div>${mainAbility}</div>
											</#list>
											</div>
										</#if>
									</td>
									<td class="padding text-center reasonForCompetence reasonForCompetence_padding"><div class="reasonForCompetenceContent">${enrollDeveloper.joinPlan!""}</div></td>
									<#if enrollDeveloper.isChosen == 1>
									<td class="padding text-center console" developerId="${enrollDeveloper.developerId!''}" style="padding: 5px 0px;">
										<div>
											<button class="btn-choose btn-choose-cancle" onclick="cancelBubble(${enrollDeveloper.developerId!''},this)">撤销</button>
										</div>
										<#if enrollDeveloper.chosenRoleList?? && (enrollDeveloper.chosenRoleList?size>0) >
											<#list enrollDeveloper.chosenRoleList as chosenRole>
											<div class="rolefunctions">
												<div class="btn-default-choose">${chosenRole.name}</div>
											</div>
											</#list>
										</#if>
									</td>
									<#else>
									<td class="padding text-center console" developerId="${enrollDeveloper.developerId!''}">
										<a class="btn btn-primary btn-choose" onclick="developerModify(${enrollDeveloper.developerId!''},this)" data-toggle="modal" href="#functionChoice">
											选择
										</a>
									</td>
									</#if>
								</tr>
								</#list>
							</#if>
						</table>
					</div>
				</div>

			    <div class="col-md-12 col-xs-12 col-sm-12 padding projectDetailsMarginTop">
	    			<div class="col-md-4 col-sm-4 col-xs-4 text-left padding">
	    				<input type="button" value="推送" onclick="window.open('/admin/user_info_import?projectId=${projectInSelfRun.id}')" class="btn btn-cancel btn-functions btn-cancel-functions"/>
	    			</div>
	    			<div class="col-md-offset-4 col-sm-offset-4 col-xs-offset-4 col-md-4 col-sm-4 col-xs-4 text-right padding">
	    				<!--<input type="button" value="进入开发状态" onclick="entryDevelopment()" class="btn btn-primary btn-functions"/>-->
	    				<input type="button" value="进入开发状态" class="btn btn-primary btn-functions" data-toggle="modal" data-target="#confirmBox"/>
	    			</div>
	    		</div>
			</div>
		</#if>

		<#if projectInSelfRun.status == 1 || projectInSelfRun.status == 2>
			<div class="col-md-12 col-sm-12 col-xs-12 projectDetails">
				<input type="hidden" id="developerHidden" value=""/>
				<div class="col-md-12 padding projectLabel">
					<div class="border-left"></div>
					<div>分配开发人员</div>
				</div>
				
				<div class="col-md-12 col-sm-12 col-xs-12 padding">
					<div class="col-md-12 col-sm-12 col-xs-12 padding">
						<table class="col-md-12 col-sm-12 col-xs-12 padding entryList" cellpadding="0" border="1" cellspacing="0">
							<tbody>
							<tr class="tableThFloat">
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">id</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">用户名</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">选中的角色</td>
								<td class="col-md-3 col-xs-3 col-sm-3 padding text-center">联系电话</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">qq</td>
							</tr>
							</tbody>
							<#if chosenDevList?? && chosenDevList?size gt 0 >
								<#list chosenDevList as chosenDev>
								<tr class="padding" onclick="showReasonForCompetence(this)">
									<td class="padding text-center">${chosenDev.developerId}</td>
									<td class="padding text-center">
									<#if Session.SESSION_LOGIN_USER.userInfoVo.userType = 2 || Session.SESSION_LOGIN_USER.userInfoVo.userType == -1>
										<a style="cursor:pointer;" onclick="javascript:window.open('/api/2/idntify/view?uid=${chosenDev.developerId!""}')">
											${chosenDev.developer.name}
										</a>
									<#else>
											${chosenDev.developer.name}
									</#if>
									</td>
									<td class="padding text-center reasonForCompetence_padding">
										<#if chosenDev.roleList?? && (chosenDev.roleList?size>0) >
											<div class="reasonForCompetenceContent">
											<#list chosenDev.roleList as role>
												<div>${role.name}</div>
											</#list>
											</div>
										</#if>
									</td>
								    <td class="padding text-center">${chosenDev.developer.mobile}</td>
									<td class="padding text-center">${chosenDev.developer.qq}</td>
								</tr>
								</#list>
							</#if>
						</table>
					</div>
				</div>
			</div>
		</#if>

		<#if Session.SESSION_LOGIN_USER.userInfoVo.userType == 2>
			<#if projectInSelfRun.status == 1 || projectInSelfRun.status == 2>
			<div class="col-md-12 col-sm-12 col-xs-12 projectDetails">
				<div class="col-md-12 padding projectLabel">
					<div class="border-left"></div>
					<div>阶段计划</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding">
					<div class="col-md-12 col-sm-12 col-xs-12 padding">
						<table class="col-md-12 col-sm-12 col-xs-12 padding entryList stagePlan" cellpadding="0" border="1" cellspacing="0" ng-Cloak>
							<tr class="tableThFloat">
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">阶段ID</td>
								<td class="col-md-3 col-xs-3 col-sm-3 padding text-center">阶段描述</td>
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">开始日期</td>
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">截至日期</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">金额</td>
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">状态</td>
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">是否延期</td>
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">责任人</td>
								<#if projectInSelfRun.status == 1>
									<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">操作</td>
								</#if>
							</tr>
							<tr ng-repeat="plan in plans" planId="{{plan.stepId}}" onclick="showReasonForCompetence(this)">
								<td class="text-center" ng-bind="plan.stepId"></td>
								<td class="text-center reasonForCompetence reasonForCompetence_padding"><div class="reasonForCompetenceContent" ng-bind-html="plan.stepDesc"></div></td>
								<td class="text-center" ng-bind="plan.startTime"></td>
								<td class="text-center" ng-bind="plan.endTime"></td>
								<td class="text-center" ng-bind="plan.price"></td>
								<td class="text-center" ng-bind="plan.statusName"></td>
								<td class="text-center" ng-bind="plan.isDelayedNames"></td>
								<td class="text-center" ng-bind="plan.executorName"></td>
								<#if projectInSelfRun.status == 1>
									<td ng-show="{{plan.verifyStatus!=3}}" class="text-center" planId="{{plan.stepId}}"><div class="col-md-6 col-sm-6 col-xs-12 padding"><a style="color: red;cursor: pointer;" onclick="removeProjectPlan(this)">删除</a></div><div class="col-md-6 col-sm-6 col-xs-12 padding"><a style="color: black;" data-toggle="modal" modifyId='{{plan.stepId}}' onclick="planHandleType('modify',this)" href="#plan">修改</a></div></td>
									<td ng-show="{{plan.verifyStatus==3}}" class="text-center"><div class="col-md-12 col-sm-12 col-xs-12 padding"><a disabled="disabled">{{plan.verifyStatusName}}</a></div></td>
								</#if>
							</tr>
						</table>
					</div>
				</div>
				<#if projectInSelfRun.status == 1>
					<div class="col-md-12 col-sm-12 col-xs-12 text-center marginTop"><button class="btn btn-primary btn-create" onclick="planHandleType('create')" data-toggle="modal" href="#plan">新增</button></div>
				</#if>
			</div>

			<div class="col-md-12 col-sm-12 col-xs-12 projectDetails">
				<div class="col-md-12 padding projectLabel">
					<div class="border-left"></div>
					<div>进展反馈</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding">
					<div class="col-md-12 col-sm-12 col-xs-12 padding">
						<table class="col-md-12 col-sm-12 col-xs-12 padding entryList progressFeedbackDiv" cellpadding="0" border="1" cellspacing="0">
							<tr class="tableThFloat">
								<td class="col-md-2 col-xs-3 col-sm-2 padding text-center">反馈时间</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">反馈人</td>
								<td class="col-md-5 col-xs-3 col-sm-5 padding text-center">反馈信息</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">附件</td>
								<#if projectInSelfRun.status == 1>
									<td class="col-md-1 col-xs-2 col-sm-1 padding text-center">操作</td>
								</#if>
							</tr>
							<tr dId="{{projectFeedback.stepId}}" ng-repeat="projectFeedback in projectFeedbacks" onclick="showReasonForCompetence(this)">
								<td class="text-center" ng-bind="projectFeedback.createTime"></td>
								<td class="text-center" ng-bind="projectFeedback.name"></td>
								<td class="text-center reasonForCompetence"><div class="reasonForCompetenceContent monitorDescModify" ng-bind-html="projectFeedback.monitorDesc"></div></td>
								<td class="text-center">
									<div class="reasonForCompetence reasonForCompetenceModify">
										<div class="reasonForCompetenceContent" file="{{projectFeedbackLink}}" ng-repeat="projectFeedbackLink in projectFeedback.attachJSON">
											<a style="display: block;width: 100%;height: 100%;" href="{{projectFeedbackLink.path}}" ng-bind="projectFeedbackLink.fileName"></a>
										</div>
									</div>
								</td>
								<#if projectInSelfRun.status == 1>
									<td class="text-center"><div class="col-md-6 col-sm-6 col-xs-12 padding"><a style="color: red;cursor: pointer;font-size: 14px;" onclick="delMonitor(this)">删除</a></div><div class="col-md-6 col-sm-6 col-xs-12 padding"><a style="color: black;" data-toggle="modal" onclick="createProjectFb('modify',this)" href="#progressFeedback">修改</a></div></td>
								</#if>
							</tr>
						</table>
					</div>
				</div>
				<#if projectInSelfRun.status == 1>
					<div class="col-md-12 col-sm-12 col-xs-12 text-center marginTop"><button class="btn btn-primary btn-create" data-toggle="modal" onclick="createProjectFb('create')" href="#progressFeedback">新增</button></div>
				</#if>
			</div>
		 </#if>
		<#else>
		 <#if projectInSelfRun.status == 1 || projectInSelfRun.status == 2>
			<div class="col-md-12 col-sm-12 col-xs-12 projectDetails">
				<div class="col-md-12 padding projectLabel">
					<div class="border-left"></div>
					<div>阶段计划</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding">
					<div class="col-md-12 col-sm-12 col-xs-12 padding">
						<table class="col-md-12 col-sm-12 col-xs-12 padding entryList stagePlan" cellpadding="0" border="1" cellspacing="0">
							<tr class="tableThFloat">
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">阶段ID</td>
								<td class="col-md-4 col-xs-4 col-sm-3 padding text-center">阶段描述</td>
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">开始日期</td>
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">截至日期</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">金额</td>
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">状态</td>
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">是否延期</td>
								<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">责任人</td>
								<#if projectInSelfRun.status == 1>
									<td class="col-md-1 col-xs-1 col-sm-1 padding text-center">验收状态</td>
								</#if>
							</tr>
							<tr ng-repeat="plan in plans" planId="{{plan.stepId}}" onclick="showReasonForCompetence(this)">
								<td class="text-center" ng-bind="plan.stepId"></td>
								<td class="text-center reasonForCompetence reasonForCompetence_padding"><div class="reasonForCompetenceContent" ng-bind="plan.stepDesc"></div></td>
								<td class="text-center" ng-bind="plan.startTime"></td>
								<td class="text-center" ng-bind="plan.endTime"></td>
								<td class="text-center" ng-bind="plan.price"></td>
								<td class="text-center" ng-bind="plan.statusName"></td>
								<td class="text-center" ng-bind="plan.isDelayedNames"></td>
								<td class="text-center" ng-bind="plan.executorName"></td>
								<!--1.为开发者    需要-->
								<#if projectInSelfRun.status == 1 && projectInSelfRun.creator.mobile!=Session.SESSION_LOGIN_USER.userInfoVo.mobile>
									<td ng-show="{{plan.verifyStatus==1}}" class="text-center"><div class="col-md-12 col-sm-12 col-xs-12 padding"><a style="color: #3487bd;cursor: pointer;" planId="{{plan.stepId}}" onclick="submitVerify(this)">提交验收</a></div></td>
									<td ng-show="{{plan.verifyStatus!=1}}"class="text-center"><div class="col-md-12 col-sm-12 col-xs-12 padding"><a disabled="disabled">{{plan.verifyStatusName}}</a></div></td>
								<#elseif projectInSelfRun.status == 1 && projectInSelfRun.creator.mobile==Session.SESSION_LOGIN_USER.userInfoVo.mobile>
									<td ng-show="{{plan.verifyStatus!=2}}" class="text-center"><div class="col-md-12 col-sm-12 col-xs-12 padding"><a disabled="disabled">{{plan.verifyStatusName}}</a></div></td>
									<td ng-show="{{plan.verifyStatus==2}}" class="text-center"><div class="col-md-12 col-sm-12 col-xs-12 padding"><a style="color: #3487bd;cursor: pointer;"  planId="{{plan.stepId}}" onclick="toggleCheckVerifyBox(this)">验收</a></div></td>
								</#if>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12 col-xs-12 projectDetails">
				<div class="col-md-12 padding projectLabel">
					<div class="border-left"></div>
					<div>进展反馈</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding">
					<div class="col-md-12 col-sm-12 col-xs-12 padding">
						<table class="col-md-12 col-sm-12 col-xs-12 padding entryList progressFeedbackDiv" cellpadding="0" border="1" cellspacing="0">
							<tr class="tableThFloat">
								<td class="col-md-2 col-xs-3 col-sm-2 padding text-center">反馈时间</td>
								<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">反馈人</td>
								<td class="col-md-5 col-xs-3 col-sm-5 padding text-center">反馈信息</td>
								<td class="col-md-3 col-xs-3 col-sm-3 padding text-center">附件</td>
							</tr>
							<tr dId="{{projectFeedback.stepId}}" ng-repeat="projectFeedback in projectFeedbacks" onclick="showReasonForCompetence(this)">
								<td class="text-center" ng-bind="projectFeedback.createTime"></td>
								<td class="text-center" ng-bind="projectFeedback.name"></td>
								<td class="text-center reasonForCompetence"><div class="reasonForCompetenceContent monitorDescModify" ng-bind="projectFeedback.monitorDesc"></div></td>
								<td class="text-center">
									<div class="reasonForCompetence reasonForCompetenceModify">
										<div class="reasonForCompetenceContent" file="{{projectFeedbackLink}}" ng-repeat="projectFeedbackLink in projectFeedback.attachJSON">
											<a style="display: block;width: 100%;height: 100%;" href="{{projectFeedbackLink.path}}" ng-bind="projectFeedbackLink.fileName"></a>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 text-center marginTop"><button class="btn btn-primary btn-create" data-toggle="modal" onclick="createProjectFb('create')" href="#progressFeedback">新增</button></div>
			</div>
		 </#if>
		</#if>


		<div class="col-md-12 col-sm-12 col-xs-12 other">
			<#if projectInSelfRun.status == -1 || projectInSelfRun.status == 0>
			<div class="col-md-12 col-sm-12 col-xs-12 padding">
				<div class="col-md-12 padding projectLabel">
					<div class="border-left"></div>
					<div>雇主信息</div>
				</div>
				<div class="col-md-12 padding">
					<div class="col-md-12 col-sm-12 col-xs-12 padding">
						<div class="col-md-12 col-sm-12 col-xs-12 projectContactInformation">
							<div class="col-md-3 col-sm-6 col-xs-12 padding projectContactInformationDiv">
								<div><img src="${store_location}/img/user_icon.png" alt="" /></div>
								<div>${projectInSelfRun.creator.name!""}</div>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-12 padding projectContactInformationDiv">
								<div><img src="${store_location}/img/phone_icon.png" alt="" /></div>
								<div>${projectInSelfRun.creator.mobile!""}</div>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-12 padding projectContactInformationDiv">
								<div><img src="${store_location}/img/mail_icon.png" alt="" /></div>
								<div>${projectInSelfRun.creator.email!""}</div>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-12 padding projectContactInformationDiv">
								<div>
									<div><img src="${store_location}/img/qq_icon.png" alt="" /></div>
									<div>${projectInSelfRun.creator.qq!""}</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</#if>
			<div class="col-md-12 col-sm-12 col-xs-12 padding">
				<div class="col-md-12 padding projectLabel">
					<div class="border-left"></div>
					<div>项目进度日志</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 padding">
					<div class="col-md-12 col-sm-12 col-xs-12 padding">
						<#if Session.SESSION_LOGIN_USER??>
							<#if Session.SESSION_LOGIN_USER.userInfoVo.userType == 2>
								<table class="col-md-12 col-sm-12 col-xs-12 padding journal" cellpadding="0" border="1" cellspacing="0">
									<tr class="tableTr tableThFloat">
										<td class="col-md-3 col-xs-3 col-sm-3 padding text-center">时间</td>
										<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">发布人</td>
										<td class="col-md-5 col-xs-5 col-sm-5 padding text-center">内容</td>
									 <#if projectInSelfRun.status != 2>
										<td class="col-md-2 col-xs-2 col-sm-2 padding text-center">操作</td>
									 </#if>
									</tr>
									<#if projectLogs??>
							            <#list projectLogs as projectLog>
										<tr class="tableTr" onclick="showReasonForCompetence(this)">
											<td class="text-center">${projectLog.createTime?string("yyyy-MM-dd HH:mm")}</td>
											<td class="text-center">${projectLog.creator.name!""}</td>
											<td class="text-center reasonForCompetence"><div class="logContentDiv reasonForCompetenceContent">${projectLog.logContent!""}</div></td>
											<#if projectLog.creatorId == Session.SESSION_LOGIN_USER.userInfoVo.id && projectInSelfRun.status != 2>
											<td class="text-center">
												<div class="col-md-6 col-sm-6 col-xs-12 padding"><a projectId="${projectLog.id!""}" style="color: red;cursor: pointer;" onclick="removeLog(this)">删除</a></div>
												<div class="col-md-6 col-sm-6 col-xs-12 padding"><a class="modifyObj" style="color: black;" data-toggle="modal" modifyId="${projectLog.id!""}" logPermissionId="${projectLog.logPermission!""}"  onclick="modifyLogDiv(this,'modify')" href="#addLogDiv">修改</a></div></td>
											</#if>
										</tr>
							            </#list>
						            </#if>
								</table>
						    <#else>
						    	<table class="col-md-12 col-sm-12 col-xs-12 padding journal" cellpadding="0" border="1" cellspacing="0">
									<tr class="tableThFloat tableTr">
										<td class="col-md-3 col-sm-3 col-xs-3 text-center padding">时间</td>
										<td class="col-md-3 col-sm-3 col-xs-3 text-center padding">发布人</td>
										<td class="col-md-6 col-sm-6 col-xs-6 text-center padding">内容</td>
									</tr>
									<#if projectLogs??>
							            <#list projectLogs as projectLog>
											<tr class="tableTr" onclick="showReasonForCompetence(this)">
												<td class="text-center">${projectLog.createTime?string("yyyy-MM-dd HH:mm")}</td>
												<td class="text-center">${projectLog.creator.name!""}</td>
												<td class="text-center reasonForCompetence"><div class="reasonForCompetenceContent text-center">${projectLog.logContent!""}</div></td>
											</tr>
							            </#list>
						            </#if>
					            </table>
				            </#if>
				        </#if>
						</table>
						<#if Session.SESSION_LOGIN_USER??>
							<#if Session.SESSION_LOGIN_USER.userInfoVo.userType = 2 && projectInSelfRun.status != 2>
				                <div class="col-md-12 col-xs-12 col-sm-12 text-center marginTop">
					            	<a class="btn btn-primary btn-create" data-toggle="modal" id="createLog" onclick="newLog('create')" href="#addLogDiv">新增</a>
					            </div>
					        </#if>
				        </#if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 模态框（顾问操作项目进入开发状态） -->
	<div class="modal fade" id="confirmBox" tabindex="-1" role="dialog"
	   aria-labelledby="confirmBox" aria-hidden="true">
	   <div class="modal-dialog">
	      <div class="modal-content">
	      	<form action="javascript:entryDevelopment()" id="checkAccount">
	         <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times; </button>
	            <h4 class="modal-title" id="confirmBox"> 请先输入开发金额</h4>
	         </div>
	         <div class="modal-body" style="overflow:auto;padding-bottom: 30px;">
	         <div class="col-xs-12 " style="font-size: 16px;color: #3487bd;">项目金额:</div>
	         <div class="col-xs-12 ">
	         	<input id="proAccount" name="proAccount" type="text" class="form-control">
	         </div>
	         <p id="accountErr"></p>
	         </div>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            <!--<button id="confirm-btn" type="button" class="btn btn-primary" onclick="entryDevelopment()">确认</button>-->
	            <input type="submit" class="btn btn-primary" value="确认" />
	         </div>
	         </form>
	      </div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>

	<!-- 模态框（雇主提交验收确认） -->
	<div class="modal fade" id="checkVerifyBox" tabindex="-1" role="dialog" aria-hidden="true">
	   <div class="modal-dialog">
	   	<form action="javascript:checkVerify()" id="checkVerify">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"> &times; </button>
	            <h4 class="modal-title">项目阶段验证</h4>
	         </div>
	         <div class="modal-body" style="overflow:auto;padding-bottom: 30px;">
<!--TODO   通过不通过按钮-->
				<div class="form-group col-xs-12" >
				 <p>验收意见:</p>
				 <div class="col-xs-6 col-md-3 project-type" onclick="checkStatusRadio(this)">
		         	 <div><img class="project-type-img" src="${ctx}/img/checkboxchecked.png"/></div><p>通过</p>
		         </div>
		         <div class="col-xs-6 col-md-3 project-type" onclick="checkStatusRadio(this)">
		         	 <div><img class="project-type-img active" id="unPass" src="${ctx}/img/checkboxchecked.png" /></div><p>不通过</p>
		         </div>
			   </div>
	          	 <div class="form-group col-xs-12">
	         	 	<p>备注：</p>
	         	 	<!--<textarea class="form-control" style="resize: none;" rows="3"></textarea>-->
	         	 	<textarea style="resize: none;height: 120px;"class="form-control" id="checkRemark" name="checkRemark"></textarea>
	         	 	<span style="color: red;" id="checkRemarkMsg"></span>
	         	 </div>
			 </div>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            <input type="submit" class="btn btn-primary" value="确认">
	         </div>
	      </div><!-- /.modal-content -->
	     </form>
	</div><!-- /.modal -->
	</div>
	   	   </div>
		</div>
	<!--进展反馈对话框 end-->
	</form>
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
	<script type="text/javascript">
		initFileIuput("#fileUpload");
	</script>

	<!--CNZZ CODE-->
	${cnzz_html}
	<!--END OF CNZZ CODE-->
</body>
</html>
