<!DOCTYPE html>
<html lang="zh-CN" ng-app='userInfoApp' ng-controller='userInfoCtrl'>
  <head>
    <title>我的资料 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
		<#include "../comm-includes.ftl">
	  ${basic_includes}
	  ${tools_includes}
	  <link rel="stylesheet" href="${ctx}/css/home/userinfo.css?v=${version}">
  </head>
  <body>
    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->

    <div class="body-offset"></div>

    <div class="container">
    	<div class="breadcrumb-container-4-userinfo">
			  <ol class="breadcrumb">
				  <li><a href="/">首页</a></li>
				  <li><a href="#">个人中心</a></li>
			  </ol>
		  </div>
   </div>

    <!--hidden parameters-->
    <div>
   	 	<input type="hidden" id="phoneNumberOrg" value=""/>
   	 	<input type="hidden" id="userType" value=""/>
   	 	<#if isComplete>
   	 		<input type="hidden" id="isCompleteFlag" value="1"/>
   	 	<#else>
   	 		<input type="hidden" id="isCompleteFlag" value="0"/>
   	 	</#if>
   	 	<input type="hidden" id="showIdentifyInfo" value="${showIdentifyInfo}"/>
   	 	<input type="hidden" id="h_identifiedStatus" value="${userInfo.identifyInfo.status}"/>
   	 	<input type="hidden" id="h_showAvaliableJoinProject" value="${showAvaliableJoinProject!""}"/>

   		<#if userInfo.identifyInfo??>
   			<input type="hidden" id="h_category" value="${userInfo.identifyInfo.category!""}"/>
   		<#else>
   			<input type="hidden" id="h_category" value="0"/>
   		</#if>

   	 	<#if isIdentifyModify>
   	 		<input type="hidden" id="isIdentifyModify" value="1"/>
			  <input type="hidden" id="h_resumeType" value="${userInfo.resumeType!""}"/>

   	 		<input type="hidden" id="h_realName" value="${userInfo.identifyInfo.realName!""}"/>
   	 		<input type="hidden" id="h_idCardNum" value="${userInfo.identifyInfo.idCard!""}"/>
   	 		<input type="hidden" id="h_compName" value="${userInfo.identifyInfo.companyName!""}"/>
   	 		<input type="hidden" id="h_compAddr" value="${userInfo.identifyInfo.companyAddr!""}"/>
   	 		<input type="hidden" id="h_repName" value="${userInfo.identifyInfo.legalRepresent!""}"/>
   	 		<input type="hidden" id="h_compPhoneNum" value="${userInfo.identifyInfo.companyPhone!""}"/>
   	 		<input type="hidden" id="h_blNum" value="${userInfo.identifyInfo.businessLicense!""}"/>
   	 		<input type="hidden" id="h_idCardImg" value="${userInfo.identifyInfo.idCardImg!""}"/>
   	 		<input type="hidden" id="h_blImg" value="${userInfo.identifyInfo.businessLicenseImg!""}"/>
   	 	<#elseif IS_IDENTIFY_MODIFY?? && IS_IDENTIFY_MODIFY == true>
				<input type="hidden" id="isIdentifyModify" value="1"/>
			<#else>
				<input type="hidden" id="isIdentifyModify" value="0"/>
   	 	</#if>

   	 	<input type="hidden" id="identifyStep" value="${userInfo.identifyStep!""}"/>
   	 	<input type="hidden" id="identifyStatus" value="${userInfo.identifyInfo.status!""}"/>
    </div>
    <!---end of hidden parameters-->

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
								<input class="form-control  form-input-small" id="companyworkinghoursend" name="companyworkinghoursend" type="text" required companyTimeRangeCheck="true"/>
								<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
								<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
							</div>
						</div>
						<div class="col-md-offset-2 col-md-4 marginTop" id="companyworkinghoursstartMsg"></div>
						<div class="col-md-offset-7 col-md-4 marginTop" id="companyworkinghoursendMsg"></div>
						<div class="col-lg-12 col-md-12" style="height: 232px;">
							<label class="control-label form-label col-md-2"  for="companyworkingcontent">项目描述</label>
							<textarea style="padding:5px" class="col-md-9" name="companyworkingcontent" id="companyworkingcontent" maxlength="1000" cols="30" rows="10" required></textarea>
						</div>
						<div class="col-md-offset-2 col-md-4 marginTop" id="companyworkingcontentMsg"></div>
						<div class="col-lg-12 col-md-12 fromDiv" style="margin-top:1%">
							<label class="control-label form-label col-md-2"  for="companyWorkingUrl">项目链接</label>
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
		        			<input class="col-md-9" type="text" id="company" maxlength="100" name="company" required>
		        		</div>
		        		<div id="companyMsg" class="col-md-offset-2 col-md-4 marginTop"></div>
		        		<div class="col-lg-12 col-md-11 fromDiv">
		        			<label class="control-label form-label col-md-2" for="position">职位</label>
		        			<input class="col-md-9" type="text" id="position" name="position" maxlength="50" required>
		        		</div>
		        		<div id="positionMsg" class="col-md-offset-2 col-md-4 marginTop"></div>
						<div class="col-lg-12 col-md-11 fromDiv">
							<label class="control-label form-label col-md-2" for="workinghours">工作时间</label>
							<div class="input-group date workinghoursstart col-md-4" id="workinghoursstartDiv">
								<input class="form-control  form-input-small" id="workinghoursstart" name="workinghoursstart" type="text" required="true"/>
								<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
								<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
							</div>
							<p class="col-md-1" style="text-align:center;padding-top:10px;"> - </p>
							<div class="input-group date workinghoursend col-md-4" id="workinghoursendDiv">
								<input class="form-control  form-input-small" id="workinghoursend" name="workinghoursend" type="text" timeRangeCheck="true"/>
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
							<textarea class="col-md-9" name="workingcontent" id="workingcontent" maxlength="1000" cols="30" rows="10" required></textarea>
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
		        <form class="form-horizontal form-in-modal" id="pojectCheckForm" action="javascript:addpoject()" role="form"  method="post">
		        	<div class="form-group">
		        		<div class="col-lg-12 col-md-12">
		        			<label class="control-label form-label col-md-2 fromDiv" for="projectname">作品标题</label>
		        			<input class="col-md-9" type="text" id="projectname" maxlength="100" name="projectname" required>
		        		</div>
		        		<div id="projectnameMsg" class="col-md-offset-2 col-md-4 marginTop"></div>
		        		<div class="col-lg-12 col-md-12">
		        			<label class="control-label form-label col-md-2 fromDiv" for="projecturl">作品链接</label>
		        			<input class="col-md-9" type="text" id="projecturl" maxlength="200" name="projecturl" required>
		        		</div>
		        		<div id="projecturlMsg" class="col-md-offset-2 col-md-4 marginTop"></div>
		        		<div class="col-lg-12 col-md-12" style="height: 220px;">
		        			<label class="control-label form-label col-md-2 fromDiv" for="projectintro">作品描述</label>
		        			<textarea class="col-md-9" type="text" id="projectintro" maxlength="1000" style="padding: 10px;height: 200px;" name="projectintro" required></textarea>
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
		        <form class="form-horizontal form-in-modal" id="EduCheckForm" action="javascript:addeducational()" role="form"  method="post">
		        	<div class="form-group">
		        		<div class="col-lg-12 col-md-12 fromDiv">
		        			<label class="control-label form-label col-md-2" for="schoolname">学校名称</label>
		        			<input class="col-md-9" type="text" id="schoolname" maxlength="50" name="schoolname" required>
		        		</div>
		        		<div class="col-md-offset-2 col-md-4 marginTop" id="schoolnameMsg"></div>
						<div class="col-lg-12 col-md-12 fromDiv">
							<label class="control-label form-label col-md-2" for="major">所学专业</label>
		        	<input class="col-md-9" type="text" id="major" name="major" maxlength="100" required>
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

  	<div class="container">
	      <div class="col-lg-2 col-md-2 userInfo-left-nav">
            <ul class="list-group userInfo-service" id="userinfoNav">
                <a class="header">个人中心</a>
                <#if (showIdentifyInfo?? && showIdentifyInfo == 1) ||
                     (isIdentifyModify?? && isIdentifyModify == true) ||
                     (IS_IDENTIFY_MODIFY?? && IS_IDENTIFY_MODIFY == true) ||
                     (showAvaliableJoinProject?? && showAvaliableJoinProject == 1) ||
                     (canJoinNum?? && canJoinNum != 0)>
                	<a class="list-group-item" id="navBaseInfo" onclick="javascript:showTabContent(this);">基本信息</a>
                <#else>
                  <input type="hidden" id="tabId" value="navBaseInfo">
                  <input type="hidden" id="tabContentId" value="baseInfo">
                	<a class="list-group-item active" id="navBaseInfo" onclick="javascript:showTabContent(this);">基本信息</a>
                </#if>

                <#if (showIdentifyInfo?? && showIdentifyInfo == 1) ||
                     (isIdentifyModify?? && isIdentifyModify == true) ||
                     (IS_IDENTIFY_MODIFY?? && IS_IDENTIFY_MODIFY == true)>
                   <input type="hidden" id="tabId" value="navAuthInfo">
                  <input type="hidden" id="tabContentId" value="autheninfo">
                	<a class="list-group-item active" id="navAuthInfo" onclick="javascript:showTabContent(this);">
                		认证信息
                		<#if userInfo.identifyInfo.status == -1 || userInfo.identifyInfo.status == 3 || userInfo.identifyInfo.status == 4>
											<div id="reminder" class="hidden-xs reminder">
												<div class="reminder_div">
													<div class="reminder_before"></div>
		                			<div class="reminder_content">
		                				温馨提示：请您尽快完成认证，方便平台推荐合适您的项目
		                			</div>
		                			<div class="glyphicon glyphicon-remove reminder_close" onclick="msgHide()"></div>
		                		</div>
	                		</div>
	                		<script>
	                			window.addEventListener("load",function(){$("#reminder").css("width","460px")})
	                			setTimeout(function(){$("#reminder").css("width","0px")},30000)
	                		</script>
                		</#if>
                	</a>
                <#else>
                	<a class="list-group-item" id="navAuthInfo" onclick="javascript:showTabContent(this);">
                		认证信息
                		<#if userInfo.identifyInfo.status == -1 || userInfo.identifyInfo.status == 3 || userInfo.identifyInfo.status == 4>
											<div id="reminder" class="reminder">
												<div id="reminder" class="reminder">
													<div class="reminder_before"></div>
		                			<div class="reminder_content">
		                				温馨提示：请您尽快完成认证，方便平台推荐合适您的项目
		                			</div>
		                			<div class="glyphicon glyphicon-remove reminder_close" onclick="msgHide()"></div>
		                		</div>
	                		</div>
	                		<script>
	                			window.addEventListener("load",function(){$("#reminder").css("width","460px")})
	                			setTimeout(function(){$("#reminder").css("width","0px")},30000)
	                		</script>
                		</#if>
                	</a>
                </#if>
                <a class="list-group-item" id="publishTheProject" onclick="javascript:showTabContent(this);">发布的项目</a>
                <a class="list-group-item" id="JoinTheProject" onclick="javascript:showTabContent(this);">参与的项目</a>


               	<#if (canJoinNum?? && canJoinNum != 0)>
               		<input type="hidden" id="tabId" value="canJoinTheProject">
                  <input type="hidden" id="tabContentId" value="canJoinTheProjectContent">
                	<a class="list-group-item active" id="canJoinTheProject" onclick="javascript:showTabContent(this);">推送的项目<span class="spanTip">${canJoinNum}</span></a>
                <#else>
                	<a class="list-group-item" id="canJoinTheProject" onclick="javascript:showTabContent(this);">推送的项目</a>
                </#if>
								<a class="list-group-item" id="ability" ng-click="showTabContent();" ng-model="ability">能力测评</a>
                <a class="list-group-item" id="navOptions" onclick="javascript:showTabContent(this);">设置</a>
            </ul>
  		  </div>

	     <#if (showIdentifyInfo?? && showIdentifyInfo == 1) ||
			      (isIdentifyModify?? && isIdentifyModify == true) ||
			      (IS_IDENTIFY_MODIFY?? && IS_IDENTIFY_MODIFY == true) ||
			      (showAvaliableJoinProject?? && showAvaliableJoinProject == 1) ||
			      (canJoinNum?? && canJoinNum != 0)>
		  	<div id="baseInfo" class="col-lg-9 col-md-9 tab-form" style="display:none;">
		  	<#else>
		  		<div id="baseInfo" class="col-lg-9 col-md-9 tab-form">
		    </#if>
		     <form id="userInfoForm" class="form-horizontal" action="javascript:doSave()" role="form" method="post">
		     	<div class="form-header">
		       		<h3>完善您的基本信息</h3>
		       </div>
		       <fieldset>

		       <div class="col-md-12 phone">
		         <div class="col-md-3 col-md-offset-1">
		           <label class="control-label form-label" for="phoneNumber"><i class="form-required">*</i>手机号码:</label>
		         </div>
		         <div class="col-md-8">
		           <div class="col-lg-7 col-md-7">
		          	 <input type="text" name="phoneNumber" id="phoneNumber" value="" class="form-control form-input" required minlength="11" maxlength="11" isMobilePhoneNumber="true" phoneNumExisting="true"/>
		           </div>
		           <div id="vcodediv" class="col-lg-5 col-md-5">
		           	 <div id="vcodeSendDiv " style="display:none">
		           	 	<input type="button" id="sendVcodeBtn" class="btn btn-primary form-btn" value="发送验证码" onclick="javascript:sendSMS();" disabled="true"/>
		           	 </div>
		          	 <div id="vcodeVerfiedDiv" style="display:none">
		          	 	<i class="fa fa-2x fa-check-circle-o text-primary"></i>
		          	 	<p class="text-primary" style="display:inline-block;font-size:18px;font-weight: bold;">已验证</p>
		          	</div>
		           </div>
		         </div>
		       </div>

		       <div id="vcodeinputdiv" class="col-md-12" style="display:none;margin-bottom:20px">
		       	 <div class="col-md-3 col-md-offset-1">
		           <label class="control-label form-label" for="vcode"><i class="form-required">*</i>手机验证码：</label>
		         </div>
		          <div class="col-md-8">
		          	<div class="col-lg-7 col-md-7">
			           <input class="form-control form-input-small" type="text" name="vcode" id="vcode" value=""  required  maxlength="11"/>
		          	</div>
		         </div>
		       </div>

		       <div class="col-md-12 UserBaseinfo">
		       	 <div class="col-md-3 col-md-offset-1">
		           	<label class="control-label form-label" for="vcode"><i class="form-required">*</i>用户昵称:</label>
		       		<div id="userNameTip" style="display:none;color:red;font-size:12px;">(只能填写一次，请谨慎输入)
		        	</div>
		         </div>
		         <div class="col-md-8">
		         	<div class="col-md-7">
		           		<input type="text" class="form-control form-input" name="userName" id="userName" required minlength="2" maxlength="20" validUserName="true"></input>
		         	</div>
		         </div>
		       </div>

		       <div class="col-md-12" id="emailDiv">
		         <div class="col-md-3 col-md-offset-1">
		         	<label class="control-label form-label" for="email"><i class="form-required">*</i>E-mail:</label>
		         </div>
		         <div class="col-md-8">
		          	<div class="col-lg-7 col-md-7">
		           		<input type="email" name="email" id="email" class="form-control form-input" required minlength="3" maxlength="50" />
		         	</div>
		         	<div class="col-lg-5 col-md-5">
				    	<div id="emailActivationDiv" style="display:none;" >
		         	    	<input id="emailValidateBtn" type="button" class="btn btn-primary form-btn" value="发送验证码" disabled="true" onclick="javascript:doValidateEmail();" style="margin: 0px !important;"/>
						</div>
						<div id="emailVCodeVerfiedDiv" style="display:none">
	          	 			<i class="fa fa-2x fa-check-circle-o text-primary"></i>
	          	 			<p class="text-primary" style="display:inline;font-size:18px;font-weight: bold;">已验证</p>
		          		</div>
		         	</div>
		         </div>
		       </div>

		       <div id="emailVCodeDiv" class="col-md-12" style="display:none;border:solid 1px #ccc;padding:20px 0px;margin-bottom:20px">
		       	 <div  class="col-md-3 col-md-offset-1">
		           <label class="control-label form-label" for="emailVCode"><i class="form-required">*</i>邮箱验证码：</label>
		         </div>
		          <div class="col-md-8">
		          		<div class="col-lg-7 col-md-7">
				           <input style="padding-left: 50px;background: url(${ctx}/img/email.png);background-position: 15px center;background-repeat: no-repeat;" class="form-control form-input-small" type="text" name="emailVCode" id="emailVCode" value=""  required  maxlength="11"/>
		          		</div>
		         </div>
		       </div>

		       <div class="col-md-12 qq">
		         	<div class="col-md-3 col-md-offset-1">
		           		<label class="control-label form-label" for="phoneNumber"><i class="form-required">*</i>QQ:</label>
		         	</div>
		         	<div class="col-md-8">
		           		<div class="col-lg-7 col-md-7">
		          	 		<input type="text" name="qqNumber" id="qqNumber" value="" class="form-control form-input" required digits="true"/>
		           		</div>
		          	</div>
		        </div>

		       <div class="col-md-12 weixin">
		         	<div class="col-md-3 col-md-offset-1">
		           		<label class="control-label form-label" for="phoneNumber"><i class="form-required weixinIconHidden">*</i>微信:</label>
		         	</div>
		         	<div class="col-md-8">
		           		<div class="col-lg-7 col-md-7">
		          	 		<input type="text" name="weixinNumber" id="weixinNumber" value="" class="form-control form-input"/>
		           		</div>
		          	</div>
		        </div>

		       <div><span id="vcodespan" style="display: none;"></span></div>

		       <div class="col-md-12 LocalArea">
		       		<div class="col-md-2 col-md-offset-1">
		         		<label class="control-label form-label" for="location"><i class="form-required">*</i>所在区域:</label>
		         	</div>
		         	<div class="col-md-8 col-md-offset-1">
		         		<div class="col-lg-3 col-md-3">
		         	 		<select class="form-control form-select" name="province" id="province" onchange="retriveCities();" required>
		         	 	</select>
		         		</div>
		         		<div class="col-lg-3 col-md-3">
		         	 		<select class="form-control form-select" name="city" id="city" required>
		         	 		</select>
		         		</div>
		         	</div>
		       </div>
     				<input type="hidden" value="${userInfo.email!""}" id="baseInfo-email"/>
     				<input type="hidden" value="${userInfo.name!""}" id="baseInfo-name"/>
     				<input type="hidden" value="${userInfo.mobile!""}" id="baseInfo-mobile"/>
     				<input type="hidden" value="${userInfo.provinceId!""}" id="baseInfo-provinceId"/>
     				<input type="hidden" value="${userInfo.regionId!""}" id="baseInfo-regionId"/>
     				<input type="hidden" value="${userInfo.bankAccount!""}" id="baseInfo-bankAccount"/>
     				<input type="hidden" value="${userInfo.userType!""}" id="baseInfo-userType"/>
     				<input type="hidden" value="${userInfo.qq!""}" id="baseInfo-qq"/>
     				<input type="hidden" value="${userInfo.weixin!""}" id="baseInfo-weixin"/>
			     <br>
		      	 <button type="submit"  class="btn button-style col-md-2 col-md-offset-5" style="margin-top:20px;">保存</button>

		        <br>
		       <div>
		       		<span class="text-left form-message-span" style="display:none" id="msgspan"></span>
		       </div>
		    </form>
		  </div>

<!--能力测评-->
			<div id="abilityContent" class="col-lg-9 col-md-9 tab-form" style="display: none;">
						<div class="col-md-12" style="margin-top:10px">
				 			<div class="col-md-12 titleDiv">能力测评</div>
				 		</div>
				 		<div class="col-md-12">
				 			<div class="col-md-12 abilityDivBackground">
				 				<div class="abilityDivBorder">
					 				<div class="col-xs-12 col-md-2">点击选择能力:</div>
					 				<div class="col-xs-12 col-md-10 " id="abilitySelect">
					 				 <#list listDictItem as tag>
			  	  				<#if tag.type=="examSkill">
		  	  						<div class="col-md-offset-1 col-md-2 text-center skillsTests" aid="${tag.name}" onclick="abilityBtnActive(this);clearCheckNotNull('abilitySelect')">${tag.name}</div>
			  	  				</#if>
										</#list>
										<span class="col-md-10 ol-md-offset-1 warningDiv">请选择评测能力</span>
					 				</div>


					 				<div class="col-xs-12 col-md-2">选择难度:</div>
					 				<div class="col-xs-12 col-md-10 " id="testLevel">
					 						<div class="col-xs-offset-1 col-md-offset-1 col-xs-3 text-center col-md-2 skillsTests" lid="1" onclick="Level(this);clearCheckNotNull('testLevel')">初级</div>
					 						<div class="col-xs-offset-1 col-md-offset-1 col-xs-3 text-center col-md-2 skillsTests" lid="2" onclick="Level(this);clearCheckNotNull('testLevel')">中极</div>
					 						<div class="col-xs-offset-1 col-md-offset-1 col-xs-3 text-center col-md-2 skillsTests" lid="3" onclick="Level(this);clearCheckNotNull('testLevel')">高级</div>
					 						<span class="col-md-10 ol-md-offset-1 warningDiv">请选择评测难度</span>
					 				</div>
				 				</div>


				 				<div class="abilityDiv-warning">
				 						<div class="col-xs-12 col-md-offset-1 abilityDiv-warning-div"><p class="abilityDiv-warning-p">注意事项</p></div>
				 						<div class="col-xs-12 col-md-offset-1 abilityDiv-warning-padding">
				 							<p>1.请在PC端使用Chrome浏览器进行比试。</p>
				 							<p>2.为保证公平性,请诚信比试</p>
				 							<p>3.本次测试由e代测提供技术支持,有问题可发邮件到service@edaice.com或拨打电话0755-86615853.</p>
				 						</div>
				 						<div class="col-xs-12 col-md-offset-3 col-md-9 abilityDiv-warning-padding">
				 							<button class="btn btn-primary" id="startTestBtn" ng-click="createExam()">开始测评</button>
				 						</div>
				 				</div>
				 				<input type="hidden" value="" id="abilityInput" ng-model='abilityInput'/>
				 				<input type="hidden" value="" id="levelInput" ng-model='levelInput'/>
				 			</div>
				 		</div>

						<div class="col-md-12" style="margin-top:10px">
				 			<div class="col-md-12 titleDiv">历史成绩</div>
				 			<div class="col-md-12 historyDiv">
								<div class="col-md-12 histroyContentBackground">
									<div class="col-md-12 text-center histroyTitle">
										<div class="col-sm-4 col-md-4">试题名称</div>
										<div class="col-sm-3 col-md-3">开始时间</div>
										<div class="col-sm-3 col-md-3">结束时间</div>
										<div class="col-sm-2 col-md-2">成绩</div>
									</div>
									<div ng-repeat='listExamHistory in listData'  class="col-md-12 text-center histroyContent" ng-cloak>
										<div class="col-sm-4 col-md-4" ng-bind='listExamHistory.paperName'>JavaScript</div>
										<div class="col-sm-3 col-md-3" ng-bind='listExamHistory.startTime'>2016.3.31 9:00</div>
										<div class="col-sm-3 col-md-3" ng-bind='listExamHistory.endTime'>2016.3.31 10:00</div>
										<div class="col-sm-2 col-md-2" ng-bind='listExamHistory.score' ng-if='listExamHistory.status=="completing"'>90</div>
										<div class="col-sm-2 col-md-2" ng-bind='listExamHistory.status' ng-if='listExamHistory.status!="completing"'>starting</div>
									</div>
									<div class="col-md-12 text-center histroyContent" ng-cloak>
										<div class="col-sm-12 col-md-12" ng-bind='listDataMsg'></div>
									</div>
								</div>
							</div>
				 		</div>
			</div>

	   <#if ((showAvaliableJoinProject?? && showAvaliableJoinProject == 1) ||
	        (canJoinNum?? && canJoinNum > 0)) &&
   		    (showIdentifyInfo?? && showIdentifyInfo != 1) &&
   		    (isIdentifyModify?? && isIdentifyModify == false)>
   		    <input type="hidden" id="showAvaliableJoinProjectFinal" value="1">
   	<#else>
   			<input type="hidden" id="showAvaliableJoinProjectFinal" value="0">
   	</#if>
			<div id="canJoinTheProjectContent" class="col-xs-12 col-md-9 tab-form" style="display:none;">
				 		<div class="col-md-12" style="margin-top:10px">
				 			<div class="col-md-12 titleDiv">推送的项目</div>
				 		</div>
						<div id="canJoinTheProjectItem"></div>
			</div>


			<div id="JoinTheProjectContent" class="col-xs-12 col-md-9 tab-form" style="display: none;">
				 		<div class="col-xs-11 col-md-11" style="margin-top:10px;padding: 0px;">
				 			<div class="col-xs-12 col-md-12 titleDiv">参与的项目</div>
				 		</div>
						<div id="JoinTheProjectItem">
						</div>
			</div>

			<div id="publishTheProjectContent" class="col-xs-12 col-md-9 tab-form" style="display: none;">
				 		<div class="col-xs-10" style="margin-top:10px;padding: 0px;">
				 			<div class="col-xs-12 titleDiv">发布的项目</div>
				 		</div>
				 		<div class="col-xs-2 text-right" style="margin-top: 10px;padding: 0px;">
				 			<a href="${ctx}/home/request"><img src="${ctx}/img/add.png" style="width:25px;height: 25px;"/></a>
				 		</div>
						<div id="publishTheProjectItem">
						</div>
			</div>

			<div id="navOptionsContent" class="col-lg-9 col-md-9 tab-form" style="display: none;">
				 <form action="javascript:changePassword()" id="changePasswordForm" role="form" method="post">
				 		<div class="form-group">
				 			<div class="col-md-12 titleDiv">修改密码</div>
				 		</div>
				 		<div class="col-md-12 form-group DivBackground">
				 				<div class="col-md-12 DivmarginTop">
				 					<div class="col-md-2 text-center">
				 						<label for="oldPassword">旧密码: </label>
				 					</div>
				 					<div class="col-md-8">
				 						<input class="form-control" type="password" id="oldPassword" name="oldPassword" required/>
				 					</div>
				 				</div>
				 				<div class="col-md-12"><div class="col-md-offset-2 col-md-8"  id="oldPasswordWarning" style="height: 0px;"></div></div>
				 				<div class="col-md-12 DivmarginTop">
				 					<div class="col-md-2 text-center">
				 						<label for="newPassword">新密码: </label>
				 					</div>
				 					<div class="col-md-8">
				 						<input class="form-control" type="password" id="newPassword" name="newPassword" rangelength="6,12" required/>
				 					</div>
				 				</div>
				 				<div class="col-md-12"><div class="col-md-offset-2 col-md-8"  id="newPasswordWarning" style="height: 0px;"></div></div>
				 				<div class="col-md-12 DivmarginTop">
				 					<div class="col-md-2 text-center">
				 						<label for="confirmPassword">重复新密码: </label>
				 					</div>
				 					<div class="col-md-8">
				 						<input class="form-control" type="password" id="confirmPassword" name="confirmPassword" required equalTo="#newPassword" />
				 					</div>
				 				</div>
				 				<div class="col-md-12"><div class="col-md-offset-2 col-md-8"  id="confirmPasswordWarning" style="height: 0px;"></div></div>
				 				<div class="col-md-offset-5 col-md-2 DivmarginTop">
				 					<button type="submit" class="btn btn-primary">保存</button>
				 				</div>
				 		</div>
				 </form>
				 <form action="" id="changePushForm" role="form" method="post">
				 		<div class="form-group">
				 			<div class="col-md-12 titleDiv">信息推送</div>
				 		</div>
				 		<div class="col-md-12 from-group DivBackground">
				 			<div class="col-md-12 text-center" style="background:#b8dfff;border:solid 1px #ccc;height:35px;line-height: 35px;">
				 					<p class="col-md-6">推送项目</p>
				 					<p class="col-md-6">电子邮件</p>
				 			</div>
				 			<div class="col-md-12 form-group text-center" style="background: white;border: solid 1px #ccc;border-top:none;height:35px;line-height: 35px;">
				 					<p class="col-md-6">新的开发任务</p>
				 					<p class="col-md-6">
				 						<input type="checkbox" name="Push" value="openPush"/>
				 					</p>
				 			</div>
				 			<div class="col-md-offset-5 col-md-2 DivmarginTop">
				 				<button type="button" class="btn btn-primary">保存</button>
				 			</div>
				 		</div>
				 </form>
			</div>

		  <#if userInfo.identifyInfo?? && (userInfo.identifyInfo.status == 0 || userInfo.identifyInfo.status ==1)>
		  	 <#if (showIdentifyInfo?? && showIdentifyInfo == 1) ||
				      (isIdentifyModify?? && isIdentifyModify) ||
				      (IS_IDENTIFY_MODIFY?? && IS_IDENTIFY_MODIFY == true)>
		  	 	<div id="autheninfo" class="col-sm-offset-1 col-xs-12 col-sm-9 tab-form">
		  	 <#else>
		  	 	<div id="autheninfo" class="col-sm-offset-1 col-xs-12 col-sm-9 tab-form" style="display:none;">
		  	 </#if>
			  		<div class="col-xs-12 col-md-9 form-header" style="padding: 0px;">
		       			<h3>
							<#if userInfo.identifyInfo.category == 1>
		       				 	<div style="float:left;margin-right:10px">${userInfo.identifyInfo.companyName}</div>
		       				<#elseif userInfo.identifyInfo.category == 0>
								<div style="float:left;margin-right:10px">${userInfo.identifyInfo.realName}</div>
							</#if>
		       			<#if userInfo.identifyInfo.status == 0>
		       				<div style="float:left;width:100px;">
		       					<img src="${ctx}/img/type.png" style="width:25px;height:25px;float:left;margin-right:10px">
		       					<p style="height:25px;line-height:25px;font-size:16px">审核中</p>
		       				</div>
		       		 	<#elseif userInfo.identifyInfo.status == 1>
		       				<div style="float:left;width:100px;">
		       					<img src="${ctx}/img/pass.png" style="width:30px;height:30px;float:left;margin-right:10px">
		       					<p style="height:30px;line-height:30px;font-size:16px">已认证</p>
		       				</div>
		       		 	</#if>
		       			</h3>
		          </div>
<!--修改类型[mtype:11审核中修改;mtype=12审核完修改]-->
<!--[status:0等待审核、1通过审核]-->
				<#if userInfo.identifyInfo.status == 0>
					<div class="col-xs-12 col-md-3 text-right" style="margin-top: 18px;padding-left: 0px;margin-bottom: 10px;padding-right: 0px;">
		          	<#if userInfo.identifyInfo.category == 0>
		          		<!--<button typeId="0" class="btn btn-primary" onclick="userModification(this)">修改</button>-->
		          		<a class="btn btn-primary" onclick="showConfirmDialog('认证信息','确认修改已认证的信息？',toStatus3)">修改</a>
					<#elseif userInfo.identifyInfo.category == 1>
						<!--<button typeId="2" class="btn btn-primary" onclick="userModification(this)">修改</button>-->
						<a class="btn btn-primary" onclick="showConfirmDialog('认证信息','确认修改已认证的信息？',toStatus3)">修改</a>
		          	</#if>
		          	</div>
		        <#elseif userInfo.identifyInfo.status == 1>
		        	<div class="col-xs-12 col-md-3 text-right" style="margin-top: 18px;padding-left: 0px;margin-bottom: 10px;padding-right: 0px;">
		          		<a class="hidden-xs hidden-sm btn btn-primary" onclick="showConfirmDialog('认证信息','确认修改已认证的信息？',toStatus4)">修改</a>
		          		<a class="visible-sm-block col-xs-12 btn btn-primary" onclick="showConfirmDialog('认证信息','确认修改已认证的信息？',toStatus4)">修改</a>
		          	</div>
				</#if>

		          <#if userInfo.userType == 1 || userInfo.userType == 0>
									<#if userInfo.identifyInfo.category == 1>
									<div class="col-md-12 col-xs-12 form-desc-line">
							   			<div class="col-md-3">
							   				<img src="${ctx}/img/campanyName.png">
							   				<p>
												<#if userInfo.companySize??>
								        			<#switch userInfo.companySize>
														<#case '1'>小于10人<#break>
														<#case '2'>10-30人<#break>
														<#case '3'>31-100人<#break>
														<#case '4'>100人以上<#break>
														<#default>小于10人
													</#switch>
							        			</#if>
						        			</p>
							   			</div>
							   			<div class="col-md-3">
								   			<img src="${ctx}/img/companyUrl.png">
												<p>${userInfo.identifyInfo.siteLink!""}</p>
							   			</div>
										<div class="col-md-6">
											<img src="${ctx}/img/whereCompany.png">
							   			<p>${userInfo.identifyInfo.companyAddr!""}</p>
										</div>
							   		</div>
							   		<div class="col-md-12 col-xs-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>法人代表:</p>
							   			<p class="col-md-9">${userInfo.identifyInfo.legalRepresent!""}</p>
							   		</div>
							   		<div class="col-md-12 col-xs-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required" style="visibility: hidden;">*</i>公司电话:</p>
							   			<p class="col-md-9">${userInfo.identifyInfo.companyPhone!""}</p>
							   		</div>
							   		<div class="col-md-12 col-xs-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>营业执照号:</p>
							   			<p class="col-md-9">${userInfo.identifyInfo.businessLicense!""}</p>
							   		</div>
							   		<div class="col-md-12 col-xs-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>能胜任的工作类型:</p>
							   			<div class="col-md-9">
											<#if userInfo.displayCando??>
									        	<#list userInfo.displayCando as tag>
									  	  			<p style="padding-right:10px">${tag}</p>
									  	  		</#list>
											</#if>
										</div>
							   		</div>
							   		<div class="col-md-12 col-xs-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>擅长的技术:</p>
										<div class="col-md-9 col-md-offset-1">
											<#if userInfo.displayMainAbility?? && userInfo.displayMainAbility?size gt 0>
								        	<#list userInfo.displayMainAbility as tag>
								  	  			<div class='col-md-2 col-md-offset-1 AuthenticationInformationDiv'>${tag}</div>
													</#list>
											</#if>
											<#if userInfo.otherAbility??  && userInfo.otherAbility != "">
												<div class="col-md-11 col-md-offset-1 AuthenticationInformationInput">${userInfo.otherAbility!""}</div>
											<#else>
												<div style="display: none;" class="col-md-11 col-md-offset-1 AuthenticationInformationInput">${userInfo.otherAbility!""}</div>
											</#if>
										</div>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required">*</i>产品类型:</p>
										<div class="col-md-10 col-md-offset-1">
											<#if userInfo.displayCaseType?? && userInfo.displayCaseType?size gt 0>
								        		<#list userInfo.displayCaseType as tag>
								  	  				<div class='col-md-5 col-md-offset-1 PType'>${tag}</div>
												</#list>
											</#if>
											<#if userInfo.otherCaseType?? && userInfo.otherCaseType != "">
												<div class="col-md-8 col-md-offset-1 PTypeInput">${userInfo.otherCaseType!""}</div>
											<#else>
												<div style="display: none;" class="col-md-8 col-md-offset-1 PTypeInput">${userInfo.otherCaseType!""}</div>
											</#if>

										</div>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>经典案例:</p>
											<#if userInfo.employeeProjectExperience??>
												<#list userInfo.employeeProjectExperience as tag>
													<div class="col-md-10 col-md-offset-1" style="font-size:14px;margin-top:10px;border-bottom: dashed 1px #ccc;margin-bottom: 10px">
										  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">项目名称: </i>${tag.projectName}</div>
										  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">项目时间: </i>${tag.startTime} - ${tag.endTime}</div>
										  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">项目描述: </i>${tag.description}</div>
										  	  				<div class="col-md-12"><i class="iColorNormal">项目链接: </i>${tag.link}</div>
													</div>
												</#list>
											</#if>

							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required">*</i>公司简介:</p>
							   			<p class="col-md-11 col-md-offset-1">${userInfo.introduction!""}</p>
							   		</div>
									<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
										<p class="col-md-12"><i class="form-required">*</i>营业执照照片:</p>
										<img class="img-responsive authen-img" style="margin: 0px auto 10px auto" src="${userInfo.identifyInfo.businessLicenseImg!""}" onclick="javascript:doImageZoom(this);"></img>
									</div>

							   <#elseif userInfo.identifyInfo.category == 0>
										<#if userInfo.resumeType?? && userInfo.resumeType == "input">
							   		<div class="col-md-12 col-xs-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>工作状态:</p>
							   			<div class="col-md-3">
								   			<#if userInfo.freelanceType??>
		        								<#switch userInfo.freelanceType>
													<#case 0>可兼职接活<#break>
													<#case 1>自由职业者<#break>
													<#case 2>在校学生<#break>
													<#default>在校学生
												</#switch>
						        			</#if>
						        		</div>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>身份证号码:</p>
							   			<div class="col-md-9">${userInfo.identifyInfo.idCard!""}</div>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>能胜任的工作类型:</p>
							   			<div class="col-md-9">
											<#if userInfo.displayCando??>
									        	<#list userInfo.displayCando as tag>
									  	  			<p style="padding-right:10px">${tag}</p>
									  	  		</#list>
											</#if>
										</div>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required">*</i>擅长的技术:</p>
										<div class="col-md-9 col-md-offset-1">
											<#if userInfo.displayMainAbility?? && userInfo.displayMainAbility?size gt 0>
								        		<#list userInfo.displayMainAbility as tag>
								  	  				<div class='col-md-2 col-md-offset-1 AuthenticationInformationDiv'>${tag}</div>
												</#list>
											</#if>

											<#if userInfo.otherAbility??  && userInfo.otherAbility != "">
												<div class="col-md-11 col-md-offset-1 AuthenticationInformationInput">${userInfo.otherAbility!""}</div>
											<#else>
												<div style="display: none;" class="col-md-11 col-md-offset-1 AuthenticationInformationInput">${userInfo.otherAbility!""}</div>
											</#if>

										</div>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required">*</i>工作经验:</p>
											<#if userInfo.employeeJobExperience??>
												<#list userInfo.employeeJobExperience as tag>
												<div class="col-md-10 col-md-offset-1">
								  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">公司名称: </i>${tag.companyName}</div>
								  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">工作时间: </i>${tag.startTime} - ${tag.endTime}</div>
								  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">公司职位: </i>${tag.office}</div>
								  	  				<div class="col-md-12"><i class="iColorNormal">工作内容: </i>${tag.description}</div>
												</div>
												</#list>
											</#if>
							   		</div>


							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required">*</i>项目作品:</p>
											<#if userInfo.employeeProduct??>
												<#list userInfo.employeeProduct as tag>
												<div class="col-md-10 col-md-offset-1">
								        			<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">项目名称: </i>${tag.title}</div>
								        			<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">项目链接: </i>${tag.link}</div>
								  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">项目描述: </i>${tag.description}</div>
												</div>
												</#list>
											</#if>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required">*</i>教育背景:</p>
											<#if userInfo.employeeEduExperience??>
												<#list userInfo.employeeEduExperience as tag>
												<div class="col-md-10 col-md-offset-1">
									  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">学校名称: </i>${tag.schoolName}</div>
									  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">所学专业: </i>${tag.discipline}</div>
									  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">学历: </i>${tag.eduBackgroud}</div>
									  	  				<div class="col-md-12"><i class="iColorNormal">毕业时间: </i>${tag.graduationTime}</div>
												</div>
												</#list>
											</#if>
							   		</div>

							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required" style="visibility: hidden;">*</i>个人简介:</p>
							   			<div class="col-md-10 col-md-offset-1" style="word-break: break-all;">
							   				${userInfo.introduction!""}
							   			</div>
							   		</div>

							   		<#elseif userInfo.resumeType?? && userInfo.resumeType != "input" && userInfo.identifyInfo.status == 1>
							   			<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>工作状态:</p>
							   			<div class="col-md-3">
								   			<#if userInfo.freelanceType??>
		        								<#switch userInfo.freelanceType>
													<#case 0>全职工作<#break>
													<#case 1>自由职业者<#break>
													<#case 2>在校学生<#break>
													<#default>在校学生
												</#switch>
						        			</#if>
						        		</div>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>身份证号码:</p>
							   			<div class="col-md-9">${userInfo.identifyInfo.idCard!""}</div>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-3"><i class="form-required">*</i>能胜任的工作类型:</p>
							   			<div class="col-md-9">
											<#if userInfo.displayCando??>
									        	<#list userInfo.displayCando as tag>
									  	  			<p style="padding-right:10px">${tag}</p>
									  	  		</#list>
											</#if>
										</div>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required">*</i>擅长的技术:</p>
										<div class="col-md-9 col-md-offset-1">
											<#if userInfo.displayMainAbility??>
								        		<#list userInfo.displayMainAbility as tag>
								        			<#if tag??>
								        					<div class='col-md-2 col-md-offset-1 AuthenticationInformationDiv'>${tag!""}</div>
								        			</#if>
												</#list>
											</#if>

											<#if userInfo.otherAbility?? && userInfo.otherAbility != "">
												<div class="col-md-11 col-md-offset-1 AuthenticationInformationInput">${userInfo.otherAbility!""}</div>
											<#else>
												<div style="display: none;" class="col-md-11 col-md-offset-1 AuthenticationInformationInput">${userInfo.otherAbility!""}</div>
											</#if>

										</div>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required">*</i>工作经验:</p>
											<#if userInfo.employeeJobExperience??>
												<#list userInfo.employeeJobExperience as tag>
												<div class="col-md-10 col-md-offset-1">
								  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">公司名称: </i>${tag.companyName}</div>
								  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">工作时间: </i>${tag.startTime} - ${tag.endTime}</div>
								  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">公司职位: </i>${tag.office}</div>
								  	  				<div class="col-md-12"><i class="iColorNormal">工作内容: </i>${tag.description}</div>
												</div>
												</#list>
											</#if>
							   		</div>


							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required">*</i>项目作品:</p>
											<#if userInfo.employeeProduct??>
												<#list userInfo.employeeProduct as tag>
												<div class="col-md-10 col-md-offset-1">
								        			<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">项目名称: </i>${tag.title}</div>
								        			<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">项目链接: </i>${tag.link}</div>
								  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">项目描述: </i>${tag.description}</div>
												</div>
												</#list>
											</#if>
							   		</div>
							   		<div class="col-md-12 col-lg-12 form-desc-line AuthenticationInformation">
							   			<p class="col-md-12"><i class="form-required">*</i>教育背景:</p>
											<#if userInfo.employeeEduExperience??>
												<#list userInfo.employeeEduExperience as tag>
												<div class="col-md-10 col-md-offset-1">
									  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">学校名称: </i>${tag.schoolName}</div>
									  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">所学专业: </i>${tag.discipline}</div>
									  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i class="iColorNormal">学历: </i>${tag.eduBackgroud}</div>
									  	  				<div class="col-md-12"><i class="iColorNormal">毕业时间: </i>${tag.graduationTime}</div>
												</div>
												</#list>
											</#if>
							   		</div>

							   		<#elseif userInfo.resumeType?? && userInfo.resumeType != "input">
							  				<div class="col-md-12 col-xs-12 form-desc-line AuthenticationInformation">
												<#if userInfo.resumeType == "file">
													<p class="col-xs-3 col-md-3" style="color:#337ab7"><i class="form-required">*</i>个人简历链接:</p>
														<div class="col-xs-9 col-md-9">
														<a href="${userInfo.resumeUrl!""}" target="_blank">
							    					<#if userInfo.resumeUrl?index_of(".doc") != -1>
													<i class="fa fa-file-word-o"></i>
													<#elseif  userInfo.resumeUrl?index_of(".pdf") != -1>
													<i class="fa fa-file-pdf-o"></i>
													<#elseif  userInfo.resumeUrl?index_of(".zip") != -1>
													<i class="fa fa-file-zip-o"></i>
													</#if>
							    					点此查看
														</a></div>
												<#else>
													<p class="col-xs-3 col-md-3" style="color:#337ab7"><i class="form-required">*</i>个人简历链接:</p>
														<div class="col-xs-9 col-md-9"><a href="${userInfo.resumeUrl!""}" target="_blank">${userInfo.resumeUrl!""}<a></div>
												</#if>
							  				</div>
											<div class="col-md-12 form-desc-line AuthenticationInformation">
												<p class="col-xs-3 col-md-3"><i class="form-required">*</i>身份证号码:</p>
												<div class="col-xs-9 col-md-9">${userInfo.identifyInfo.idCard!""}</div>
											</div>
										</#if>
									<div class="col-xs-12 form-desc-line AuthenticationInformation">
										<p class="col-xs-12"><i class="form-required">*</i>身份证照片:</p>
										<img class="img-responsive authen-img" style="margin: 0px auto 10px auto" src="${userInfo.identifyInfo.idCardImg!""}"></img>
									</div>
							 </#if>
						 </#if>
			  		</div>
		  <#else>
		  	 <#if (showIdentifyInfo?? && showIdentifyInfo == 1) ||
			      (isIdentifyModify?? && isIdentifyModify) ||
			      (IS_IDENTIFY_MODIFY?? && IS_IDENTIFY_MODIFY == true)>
				  	<div id="autheninfo" class="col-lg-9 col-md-9 tab-form">
				 <#else>
				 	  <div id="autheninfo" class="col-lg-9 col-md-9 tab-form" style="display:none;">
				 </#if>
			  	<div>
			     <form id="atheninfoForm" class="form-horizontal" action="javascript:doAthenInfoSave()" role="form" method="post">
			       <fieldset>

			       <#if userInfo.identifyInfo.status == -1 && userInfo.identifyStep != 3>
			       	 <div class="form-group" id="userTypeChoosenDiv">
			       <#else>
			       	 <div class="form-group" id="userTypeChoosenDiv" style="display:none;">
			       </#if>
			      <div class="form-header">
			     			<h3 id="userinfoId"><i class="fa fa-paperclip"></i>&nbsp;码客帮欢迎不同的开发力量加入,请问您的类型是？</h3>
		        </div>
			            <div class="col-lg-10 col-md-11 col-md-offset-1">
			            	<div class="">
								<div class="col-md-5 thumbnail pORc act" id="personalBtn" name="servicerType" onclick="checkServiceType(this);">
									<img id="personalBtnimg" src="${ctx}/img/user_active.png">
									<p>个人</p>
								</div>

								<div class="col-md-5 col-md-offset-1 thumbnail pORc unact" id="companyBtn" name="servicerType" onclick="checkServiceType(this);">
									<img id="companyBtnimg" src="${ctx}/img/we.png">
									<p>公司</p>
								</div>
			            	</div>
			            	<div class="">
			        			<button type="button" class="btn button-style col-md-2 col-md-offset-9" onclick="nextTosecond()">下一步 ></button>
			            	</div>
						</div>
			       </div>

			       <!-- 个人认证第二步 -->
			       		<#if (userInfo.identifyInfo.status == 4) && userInfo.identifyInfo.category == 0 && userInfo.identifyStep != 3>
				       		<div class="form-group" id="personalSecond">
				       	<#else>
				       		<div class="form-group" id="personalSecond" style="display:none">
                     	</#if>
                     <!--<div class=" col-md-10 col-md-offset-1" id="userTypeRepDiv" style="display:none">
						<label class="control-label form-label">认证类型:</label>
						<span id="userTypeBadge" class="badge badge-success badge-md"></span>
					</div>-->
						<div class="form-header">
			     			<h3 id="userinfoId"><i class="fa fa-paperclip"></i>&nbsp;让我们了解您擅长的技能,为您推荐最合适的项目</h3>
		        </div>
					<div class=" col-md-10 col-md-offset-1 workstatus" id="workStatus">
						<label class="col-md-12"><i class="form-required">*</i>工作状态:</label>
						<div class="col-lg-10 col-md-11 col-md-offset-1">
							<label for="worker" class="col-md-4" onclick="labelActive(this);clearCheckNotNull('workStatus')">
								<div><div></div></div>
								<p>可兼职接活</p>
							</label>
							<label for="freelance" class="col-md-4" onclick="labelActive(this);clearCheckNotNull('workStatus')">
								<div><div></div></div>
								<p>自由职业者</p>
							</label>
							<label for="student" class="col-md-4" onclick="labelActive(this);clearCheckNotNull('workStatus')">
								<div><div></div></div>
								<p>在校学生</p>
							</label>
							<input type="radio" name="freelanceType" id="worker" value="0">
							<input type="radio" name="freelanceType" id="freelance" value="1">
							<input type="radio" name="freelanceType" id="student" value="2">
							<input type="hidden" id="freelanceTypeHidden" value="${userInfo.freelanceType!""}">
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
							<input type="hidden" id="nameCando" value="${userInfo.cando!""}">
						</div>
						<span class="col-md-11 col-md-offset-1 warningDiv marginTop">请您选择能胜任的工作类型</span>
					</div>

					<div class=" col-md-10 col-md-offset-1 excelstechnologies"  id="ExcelsTechnologies">
						<label class="col-md-12"><i class="form-required">*</i>擅长的技术:</label>
						<div class="col-lg-10 col-md-11">
	  		  	  <#list listDictItem as tag>
	  	  				<#if tag.type=="ability">
	  	  					<label for="ETechnologies${tag.value}" class="col-md-2 col-md-offset-1">${tag.name}</label>

	  	  					<input type="checkbox" name="mainAbility" value="${tag.value}" id="ETechnologies${tag.value}">
	  	  				</#if>
							</#list>
							<input type="text" name="otherMainAbility" id="ActionScript" class="col-md-11 col-md-offset-1" placeholder="其他技术 :" onkeyup="clearCheckNotNull('ExcelsTechnologies')">
							<input type="hidden" id="mainAbilityCheckBox" value="${userInfo.mainAbility!""}" />
							<input type="hidden" id="mainAbilityT" value="${userInfo.otherAbility!""}" />
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
			  	  						<img style="width:26px;height:26px;padding-bottom:3px" href="#addworkexperience" data-toggle="modal" onclick="reviseWorkExperience(this)" src="${ctx}/img/revise.png"/>
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
			  	  						<img style="width:26px;height:26px;padding-bottom:3px" href="#addprojectworks" data-toggle="modal" onclick="revisePoject(this)" src="${ctx}/img/revise.png"/>
			  	  						<img src="${ctx}/img/delect.png" style="width:23px;height:23px;padding-bottom:1px" onclick="deletePoject(this)" />
			  	  				</div>
			  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">项目链接: </i>${tag.link}</div>
			  	  				<div class="col-md-12" style="margin-bottom: 10px;"><i style="font-style: normal;color: #3598db;">项目描述: </i>${tag.description}</div>
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
			  	  						<img style="width:26px;height:26px;padding-bottom:3px" href="#educational" data-toggle="modal" onclick="reviseeducational(this)" src="${ctx}/img/revise.png"/>
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

					<div class=" col-md-10 col-md-offset-1 projectworks">
						<label class="col-md-6"><i class="form-required" style="visibility: hidden;">*</i>个人简介:</label>
						<div class="col-md-12">
							<textarea id="profile" maxlength="1000" class="form-control">${userInfo.introduction!""}</textarea>
						</div>
					</div>

					<#if userInfo.identifyInfo.status == 2>
					    <button onclick="personalToSecond()" type="button" class="btn button-style col-md-2 col-md-offset-1">< 上一步</button>
		        		<button onclick="StatusNextTothird()" type="button" class="btn button-style col-md-2 col-md-offset-6">下一步 ></button>
					<#elseif userInfo.resumeType?? && userInfo.resumeType!="input" && (userInfo.identifyInfo.status == 3 || userInfo.identifyInfo.status == 4)>
						<button onclick="StatusNextTothird()" type="button" class="btn button-style col-xs-2 col-xs-offset-9 col-md-2 col-md-offset-9">下一步 ></button>
					<#elseif userInfo.resumeType?? && userInfo.resumeType=="input" && (userInfo.identifyInfo.status == 3 || userInfo.identifyInfo.status == 4)>
						<button onclick="personalToSecondHtml2()" type="button" class="btn button-style col-md-2 col-md-offset-1">< 上一步</button>
						<button onclick="nextTothird()" type="button" class="btn button-style col-xs-2 col-xs-offset-6 col-md-2 col-md-offset-6">下一步 ></button>
					<#else>
						<button onclick="personalToSecondHtml2()" type="button" class="btn button-style col-md-2 col-md-offset-1">< 上一步</button>
		        		<button onclick="nextTothird()" type="button" class="btn button-style col-md-2 col-md-offset-6">下一步 ></button>
					</#if>
				 </div>

					<!-- 公司认证第二步 -->
						<#if (userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 4 || userInfo.identifyInfo.status == 3)
							 && userInfo.identifyInfo.category == 1 && userInfo.identifyStep != 2>
							<div class="form-group" id="companySecond">
						<#else>
							<div class="form-group" id="companySecond" style="display:none">
						</#if>
						<div class="form-header">
			     		<#if userInfo.identifyInfo?? && userInfo.identifyInfo.status == 2>
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
								<input type="hidden" value="${userInfo.companySize!""}" id="theNumberOfHidden">
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
							<input type="hidden" value="${userInfo.cando!""}" id="companyworktypeHidden">
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
								<input type="text" id="companytechnology" class="col-md-11 col-md-offset-1" onkeyup="clearCheckNotNull('companyEtechnologies')" placeholder="其他技术" >
									<input type="hidden" id="companytechnologyCheckbox" value="${userInfo.mainAbility!""}"/>
									<input type="hidden" id="companytechnologyText" value="${userInfo.otherAbility!""}"/>
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
								<input type="hidden" value="${userInfo.caseType!""}" id="companyProductTypecheckbox"/>
								<input type="hidden" id="companyProductTypetext" value="${userInfo.otherCaseType!""}" />
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
								  	  						<img style="width:26px;height:26px;padding-bottom:3px" href="#addCompanyProjectExperience" data-toggle="modal" onclick="revisecompanyWorkExperience(this)" src="${ctx}/img/revise.png"/>
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
							<input type="hidden" id="addCIntroHidden" value="${userInfo.introduction!""}">
							<span class="col-md-12 warningDiv marginTop">请简易对贵公司做一个介绍</span>
						</div>

						<div class="">
							<#if userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 3 || userInfo.identifyInfo.status == 4>
									<button type="button" class="btn button-style col-md-2 col-md-offset-9" onclick="statusNextCompanyThird()">下一步 ></button>
							<#else>
									<button type="button" class="btn button-style col-md-2 col-md-offset-1" onclick="LastCompany()">< 上一步 </button>
			        		<button type="button" class="btn button-style col-md-2 col-md-offset-6" onclick="nextCompanyThird()">下一步 ></button>
							</#if>
			            </div>
					</div>

						<!--简历类型-->
						<#if userInfo.resumeType?? && userInfo.resumeType != "input">
							<input type="hidden" id="isresume" value="yes"/>
						<#else>
							<input type="hidden" id="isresume" value="no"/>
						</#if>

						<!--个人认证(简历上传)-->
						<#if (userInfo.identifyInfo.status == 3 || userInfo.identifyInfo.status == 2)
							    && userInfo.identifyInfo.category == 0 && userInfo.identifyStep != 3>
							<div id="is-resume">
						<#else>
							<div id="is-resume" style="display: none;">
						</#if>
						<div class="form-header">
							<#if userInfo.identifyInfo?? && userInfo.identifyInfo.status == 2>
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
				     		<div id="userInfoMsg" style="font-family:'微软雅黑';font-size:8px;color:black;">由于<i style="font-style: none;color: red;"> ${userInfo.identifyInfo.failReason} </i>原因,您的认证不通过,请修改后重新认证</div>
							<#else>
			     			<!--<h3 id="userinfoId"><i class="fa fa-paperclip"></i>&nbsp;码客帮欢迎您完善自己的个人信息</h3>-->
			     			<h3 id="userinfoId"><i class="fa fa-paperclip"></i>&nbsp;请选择您要认证的方式</h3>
							</#if>
						</div>
					<#if (userInfo.identifyInfo.status == 3 || userInfo.identifyInfo.status == 2) && userInfo.resumeType?? && userInfo.resumeType == "input">
			       		<div class="col-md-12 resume-no-div resume-margin-bottom resume-active" onclick="uploadResume(this)" id="resume-no">
			       	<#elseif userInfo.identifyInfo.status == -1>
			       		<div class="col-md-12 resume-no-div resume-margin-bottom resume-active"  onclick="uploadResume(this)" id="resume-no">
			       	<#else>
			       		<div class="col-md-12 resume-no-div resume-margin-bottom"  onclick="uploadResume(this)" id="resume-no">
			       	</#if>
			       		<div class="resume-title">
			       			<div style="margin-top: 50px;">
				       			<div class="col-xs-2 col-md-1">
				       				<#if (userInfo.identifyInfo.status == 3 || userInfo.identifyInfo.status == 2) && userInfo.resumeType?? && userInfo.resumeType == "input">
				       					<img src="${ctx}/img/resume-yes.png"/>
				       				<#elseif userInfo.identifyInfo.status == -1>
												<img src="${ctx}/img/resume-yes.png"/>
											<#else>
				       					<img src="${ctx}/img/resume-no.png"/>
				       				</#if>
				       			</div>
				       			<div class="col-xs-10 col-md-10">
				       				<#if userInfo.identifyInfo.status == 2 && userInfo.resumeType?? && userInfo.resumeType == "input">
				       						<!--<p class="resume-font">很抱歉您填写的认证信息未能通过认证,请重新填写,或者可以点击上面上传已有的简历进行认证</p>-->
				       							<p class="resume-font">方式一：您填写的认证信息未通过审核，请按照此方式修改或者按照“方式二”上传简历重新进行认证</p>
				       				<#elseif userInfo.identifyInfo.status == -1 || (userInfo.resumeType?? && userInfo.resumeType != "input")>
				       						<p class="resume-font">方式一：选择手工录入认证信息，点击“下一步”继续</p>
				       				<#elseif userInfo.identifyInfo.status == 2 && userInfo.resumeType?? && userInfo.resumeType != "input">
				       						<!--p class="resume-font">很抱歉您填写的认证信息未能通过认证,可以选择手动输入完成认证</p>-->
				       						<p class="resume-font">方式一：您填写的认证信息未通过审核，请重新按照此方式填写或者按照“方式二”上传简历进行认证</p>
				       				<#elseif userInfo.identifyInfo.status == 3>
				       						<p class="resume-font">方式一：您上一次的认证信息为手工录入，如需修改，点击“下一步”继续</p>
				       				<#else>
				       						<p class="resume-font">方式一：您上一次的认证信息为手工录入，如需修改，点击“下一步”继续</p>
				       				</#if>
				       			</div>
			       			</div>
			       		</div>
			       	</div>

						<#if (userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 3) &&  userInfo.resumeType?? && userInfo.resumeType != "input">
			       	<div class="col-md-12 resume-yes-div resume-active" id="resume-yes" onclick="uploadResume(this)">
			      <#else>
							<div class="col-md-12 resume-yes-div" id="resume-yes" onclick="uploadResume(this)">
						</#if>
			      			<div class="resume-title div-overflow resume-margin-bottom">
			       				<div class="col-xs-2 col-md-1">
			       					<#if (userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 3) && userInfo.resumeType?? && userInfo.resumeType != "input">
			       						<img src="${ctx}/img/resume-yes.png"/>
			       					<#else>
			       						<img src="${ctx}/img/resume-no.png"/>
			       					</#if>
			       				</div>
			       				<div class="col-xs-10 col-md-10">
			       					<#if userInfo.identifyInfo.status == 2 &&  userInfo.resumeType?? && userInfo.resumeType != "input">
			       						<p class="resume-font">方式二：您的简历信息未通过，请重传简历或者按照“方式一”重新填写认证信息</p>
			       					<#elseif userInfo.identifyInfo.status == 3>
				       						<p class="resume-font">方式二：您上一次的认证信息为简历录入，如需修改，点击“下一步”继续</p>
			       					<#else>
			       						<p class="resume-font">方式二：上传简历或者填写简历链接进行认证，点击“下一步”继续</p>
			       					</#if>
			       				</div>
			       			</div>

			       			<div class="resume-file-height">
			       			<#if (userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 3) && userInfo.resumeType?? && userInfo.resumeType == "file">
			       				<div id="oldResume" class="col-md-offset-1 col-md-10">
			       					<div class="col-xs-6 col-md-6" style="padding-left: 0px;">您上传的简历如下：
			       						<a title="点击可下载您上传的文件" href="${userInfo.resumeUrl!""}" target="_blank">
											<#if userInfo.resumeUrl?index_of(".doc") != -1>
											<i class="fa fa-file-word-o"></i>
											<#elseif  userInfo.resumeUrl?index_of(".pdf") != -1>
											<i class="fa fa-file-pdf-o"></i>
											<#elseif  userInfo.resumeUrl?index_of(".zip") != -1>
											<i class="fa fa-file-zip-o"></i>
											</#if>
			       							点击查看
			       						</a>
			       					</div>
			       					<div class="col-xs-6 col-md-6"><img style="width: 20px;height: 20px;cursor: pointer;" onclick="resumeModify()" src="${ctx}/img/revise.png"/></div>
			       				</div>
			       				<div id="uploadResume" class="col-md-offset-1 col-md-10" style="display: none;">
			       					<div class="col-xs-10 col-md-10" style="padding-left: 0px;">
			       						<input onfocus="resumeFocus()" name="resume" id="resume" class="file" type="file" placeholder="文件格式为DOC或者PDF,文件大小不超过5M"></input>
			       					</div>
			       					<div class="col-xs-2 col-md-2"><img style="width: 20px;height: 20px;cursor: pointer;" onclick="resumeModify()" src="${ctx}/img/delect.png"/></div>
									    <div class="col-md-12" style="padding-left:0px;padding-bottom:5px;overflow: hidden;font-weight: 100;">上传文档支持5M以内的doc、pdf、zip格式</div>
			       				</div>
			       			<#else>
					       		<div class="col-md-offset-1 col-md-10">
						          <input onfocus="resumeFocus()" name="resume" id="resume" class="file" type="file" placeholder="文件格式为DOC或者PDF,文件大小不超过5M"></input>
			       				</div>
			       				<div class="col-md-offset-1 col-md-10" style="padding-bottom:5px;font-weight: 100;">上传文档支持5M以内的doc、pdf、zip格式</div>
			       			</#if>
		       				  <div class="col-md-offset-1 col-md-10">
		       				  		<span id="resumeWaring" class="resume-message"></span>
						         	 	<span id="fileUploadMsg" class="error-message"></span>
						         	 	<span id="fileUploadErrMsg" class="error-message"></span>
						        </div>
			       			</div>

			       			<div class="col-md-offset-1 col-md-10 resume-margin-bottom">
			       				<p class="resume-p-font">或者输入您的简历链接</p>
			       			</div>
			       			<div class="col-md-offset-1 col-md-10 resume-margin-bottom">
			       				<#if (userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 3) && userInfo.resumeType?? && userInfo.resumeType == "link">
			       					<input onfocus="resumeFocus()" class="form-control" type="text" value="${userInfo.resumeUrl!""}" name="resume-url" id="resume-url" placeholder="http://www.make8.com"/>
			       				<#else>
			       					<input onfocus="resumeFocus()" class="form-control" type="text" name="resume-url" id="resume-url" placeholder="http://www.make8.com"/>
			       				</#if>
			       			</div>
			       	</div>



			       	<div class="col-md-12 resume-padding">
			       		<div class="col-xs-6 col-md-6 resume-padding">
						<#if userInfo.identifyInfo.status == 2>
			       			<input class="btn col-md-4 btn-primary" type="button" onclick="resumeBack()" style="visibility:hidden" value="< 上一步 >"/>
						<#else>
							<input class="btn col-md-4 btn-primary" type="button" onclick="resumeBack()" value="< 上一步"/>
						</#if>
			       		</div>
			       		<div class="col-xs-6 col-md-6 resume-padding text-right">
			       			<#if userInfo.identifyInfo.status == 2>
			       				<input class="btn col-md-offset-8 col-md-4 btn-primary" type="button" onclick="userInfo();resumeStep2()" value="下一步 >"/>
			       			<#else>
			       				<input class="btn col-md-offset-8 col-md-4 btn-primary" type="button" onclick="resumeNext()" value="下一步 >"/>
			       			</#if>
			       		</div>
			       	</div>

			      </div>

			       <!-- 个人身份证验证信息 -->
			      <#if userInfo.identifyStep == 3 && userInfo.identifyInfo.category == 0>
			      	<div id="servicerPersonalDiv">
			      <#else>
			      	<div id="servicerPersonalDiv" style="display:none;">
			      </#if>
			      <div class="form-header">
			      	<#if userInfo.resumeType?? && userInfo.resumeType == "input">
			     			<h3 id="userinfoId"><i class="fa fa-paperclip"></i>&nbsp;为了您的信用信息,请进行身份认证</h3>
			     		<#elseif userInfo.resumeType?? && userInfo.resumeType = "input" && userInfo.identifyStep == 3>
			     			<h3 id="userinfoId"><i class="fa fa-paperclip"></i>&nbsp;为了您的信用信息,请进行身份认证</h3>
			     		<#else>
			     			<h3 id="userinfoId"><i class="fa fa-paperclip"></i>&nbsp;为了您的信用信息,请进行身份认证</h3>
			     		</#if>
		        </div>
			      	<div class=" col-md-10 col-md-offset-1" id="realNameDiv">
				      	<div class="form-group col-lg-10 col-md-10 col-md-offset-2">
				       	 	<div class="col-lg-4 col-md-3 col-xs-12">
				           		<label class="control-label form-label" for="realName"><i class="form-required">*</i>真实姓名：</label>
				         	</div>
				         	<div class="col-lg-6 col-md-7 col-xs-12">
				           		<input class="form-control form-input" onkeyup="clearCheckNotNull('realNameDiv')" type="text" name="realName" id="realName" value="${userInfo.identifyInfo.realName!""}" minlength="2" maxlength="50"></input>
				         	</div>
			        	</div>
			        	<span class="col-md-12 warningDiv marginTop">请填写真实姓名</span>
			      	</div>

			        <div class=" col-md-10 col-md-offset-1" id="idCardDiv">
			        	<div class="form-group col-lg-10 col-md-10 col-md-offset-1">
				       	 	<div class="col-lg-4 col-md-3 col-xs-12">
				           		<label class="control-label form-label" for="idCardNum"><i class="form-required">*</i>身份证号码：</label>
				         	</div>
				         	<div class="col-lg-6 col-md-7 col-xs-12">
				         		<#if userInfo.identifyInfo.status == 4>
				         			<input class="form-control form-input" onkeyup="clearCheckNotNull('idCardDiv')" type="text" name="idCardNum" id="idCardNum" value="${userInfo.identifyInfo.idCard!""}" readonly ></input>
				         		<#else>
				         			<input class="form-control form-input" onkeyup="clearCheckNotNull('idCardDiv')" type="text" name="idCardNum" id="idCardNum" value="${userInfo.identifyInfo.idCard!""}"></input>
				         		</#if>
				         	</div>
			        	</div>
				      	<span class="col-md-12 warningDiv marginTop">不存在该身份证</span>
			        </div>

			      	<div class=" col-md-10 col-md-offset-1" id="realNameDiv">
				      	<div class="form-group col-lg-10 col-md-10 col-md-offset-2">
				       	 	<div class="col-lg-4 col-md-3 col-xs-12">
				           		<label class="control-label form-label" for="realName"><i class="form-required">*</i>支付宝账号：</label>
				         	</div>
				         	<div class="col-lg-6 col-md-7 col-xs-12">
			         			<#if userInfo.identifyInfo.status == 4 && userInfo.accountNum??>
				         			<input class="form-control form-input" type="text" name="payAccount" id="payAccount" value="${userInfo.accountNum!""}" readonly></input>
				         		<#else>
				         			<input class="form-control form-input" type="text" name="payAccount" id="payAccount" value="${userInfo.accountNum!""}"></input>
				         		</#if>
				         	</div>
			        	</div>
			        	<span class="col-md-12 warningDiv marginTop" style="display:block;"id="payAccountErr"></span>
			      	</div>

					<div class=" col-md-10 col-md-offset-1" style="border:solid 1px #ccc">
			         	<div class="form-group col-md-12">
				       	 	<div class="col-md-3">
				           		<label class="control-label form-label" for="idCardPic"><i class="form-required">*</i>身份证照片</label>
				         	</div>
				         	<#if userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 3 || userInfo.identifyInfo.status == 4>
				         		<div class="col-md-12" id="ModifySendCancle">
				         			<div class="col-md-offset-1 col-md-8">
				         				<img style="width:400px;height: 450px;" src="${userInfo.identifyInfo.idCardImg!""}"/>
				         			</div>
				         			<#if userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 3>
						         		<div class="col-md-offset-1 col-md-2">
						         			<img src="${ctx}/img/revise.png" onclick="picturesModify(this)" style="width:25px;height: 25px;">
						         		</div>
					         		</#if>
					         		<span class="col-md-offset-1 col-md-11 warningDiv">请等待图片上传完毕再保存</span>
				         		</div>
				         		<div class="col-md-12" style="display:none" id="ModifySend">
											<div class="col-md-11">
						            	<input id="idCardPic" name="idCarPic" class="file" type="file"></input>
						         	</div>
						         	<div class="col-md-1">
						         		<img src="${ctx}/img/delect.png" onclick="picturesModifyCancle()" style="width: 23px;height: 23px;">
						         	</div>
						         	<div class="col-md-12">
						         	 	<span id="idCardPicMsg" class="error-message"></span>
						         	</div>
						         	<div class="col-md-12">
						         		<p>请使用真实有效的身份证或护照按下图示例拍照，需确保证件内容及脸部清晰,上传图片支持2M以内的PNG、JPG、GIF格式;Mac OS系统请使用新版Firefox浏览器上传，也可联系码客帮客服协助上传</p>
			                                                 查看示例：
			                			<img class="img-responsive" style="border:1px solid #E2E2E2;"src="${ctx}/img/idcardshow.png"></img>
						         	</div>
				         		</div>
				         		<#else>
											<div class="col-lg-12 col-md-12">
						            	<input id="idCardPic" name="idCarPic" class="file" type="file"></input>
						         	</div>
						         	<div class="col-lg-12 col-md-12" >
						         	 	<span id="idCardPicMsg" class="error-message"></span>
						         	</div>
						         	<div class="col-lg-11 col-md-11 col-sm-12 col-xs-12">
						         		<p>请使用真实有效的身份证或护照按下图示例拍照，需确保证件内容及脸部清晰,上传图片支持2M以内的PNG、JPG、GIF格式;Mac OS系统请使用新版Firefox浏览器上传，也可联系码客帮客服协助上传</p>
			                                                 查看示例：
			                			<img class="img-responsive" style="border:1px solid #E2E2E2;"src="${ctx}/img/idcardshow.png"></img>
						         	</div>
				         	</#if>

			        	</div>
					</div>

				<!-- 信息保存 -->
            <#if (userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 3) && userInfo.resumeType?? && userInfo.resumeType != "input">
              <button type="button" onclick="thirdToSecond()" class="btn button-style col-md-2 col-md-offset-1">< 上一步</button>
						<#else>
							<button type="button" onclick="statusThirdToSecond()" class="btn button-style col-md-2 col-md-offset-1">< 上一步</button>
						</#if>

			     	<button type="submit" class="btn button-style col-md-2 col-md-offset-6">保存</button>
			      </div>

			     <!-- 公司认证 -->
			     <#if userInfo.identifyStep == 2>
			     	<div id="servicerCompanyDiv">
			     <#else>
			     	<div id="servicerCompanyDiv" style="display:none">
			     </#if>
			     	<div class="form-header">
			     			<h3 id="userinfoId"><i class="fa fa-paperclip"></i>&nbsp;为了贵公司的信用信息,请进行公司认证</h3>
		        </div>
			     	<div class=" col-md-10 col-md-offset-1">
 						<div class="form-group " id="checkCompanyName">
			     	    	<div class="col-lg-3 col-md-4 col-md-offset-1">
				         		<label class="control-label form-label" for="compName"><i class="form-required">*</i>公司名称：</label>
				         		<br />
				         	</div>
				         	<div class="col-lg-6 col-md-7">
				           		<input class="form-control form-input" onkeyup="clearCheckNotNull('checkCompanyName')" value="${userInfo.identifyInfo.companyName!""}" type="text" name="compName" id="compName"></input>
				         	</div>
				         	<span class="col-md-offset-1 col-md-11 warningDiv marginTopL">请填写公司名称</span>
			        	</div>

			        	<div class="form-group" id="checkCompanyAddr">
				       	 	<div class="col-lg-3 col-md-4 col-md-offset-1" >
				           		<label class="control-label form-label" for="compAddr"><i class="form-required">*</i>公司地址：</label>
				         	</div>
				         	<div class="col-lg-6 col-md-7">
				           		<input class="form-control form-input" onkeyup="clearCheckNotNull('checkCompanyAddr')" value="${userInfo.identifyInfo.companyAddr!""}" type="text" name="compAddr" id="compAddr"></input>
				         	</div>
				         	<span class="col-md-offset-1 col-md-11 warningDiv marginTopL">请填写公司地址</span>
			        	</div>

			        	<div class="form-group"  id="checkCompanyUrl">
				       	 	<div class="col-lg-3 col-md-4 col-md-offset-1">
				           		<label class="control-label form-label" for="compUrl"><i class="form-required">*</i>公司网站：</label>
				         	</div>
				         	<div class="col-lg-6 col-md-7">
				           		<input class="form-control form-input" onkeyup="clearCheckNotNull('checkCompanyUrl')" value="${userInfo.identifyInfo.siteLink!""}" type="text" name="compUrl" id="compUrl"></input>
				         	</div>
				         	<span class="col-md-offset-1 col-md-11 warningDiv marginTopL">请填写公司网站</span>
			        	</div>
			     	</div>

					<div class=" col-md-10 col-md-offset-1">
						<div class="form-group" id="checkLegalPersons">
					       	<div class="col-lg-3 col-md-4 col-md-offset-1">
					           	<label class="control-label form-label" for="repName"><i class="form-required">*</i>法人代表：</label>
					        </div>
					        <div class="col-lg-6 col-md-7">
					        		<input class="form-control form-input" onkeyup="clearCheckNotNull('checkLegalPersons')" maxlength="20" value="${userInfo.identifyInfo.legalRepresent!""}" type="text" name="repName" id="repName" placeholder="请填写公司的法人代表姓名"></input>
					        </div>
					        <span class="col-md-offset-1 col-md-11 warningDiv marginTopL">请填写法人代表</span>
					    </div>

					    <div class="form-group"  id="checkCompanyTel">
					       	 <div class="col-lg-3 col-md-4 col-md-offset-1">
					           <label class="control-label form-label" for="compPhoneNum"><i class="form-required" style="visibility: hidden;">*</i>公司电话：</label>
					         </div>
					         <div class="col-lg-6 col-md-7">
					           <input class="form-control form-input" value="${userInfo.identifyInfo.companyPhone!""}" type="text" name="compPhoneNum" id="compPhoneNum" placeholder="区号-电话号码（0755-88888888）"></input>
					         </div>
					    </div>
					</div>

					<div class=" col-md-10 col-md-offset-1" id="realNameDiv">
		      	<div class="form-group">
		       	 	<div class="col-lg-3 col-md-4 col-md-offset-1">
		           		<label class="control-label form-label" for="accountNum"><i class="form-required">*</i>支付宝账号：</label>
		         	</div>
		         	<div class="col-lg-6 col-md-7">
	         			<#if userInfo.identifyInfo.status == 4>
		         			<input class="form-control form-input" type="text" name="payAccount2" id="payAccount2" value="${userInfo.accountNum!""}" readonly></input>
		         		<#else>
		         			<input class="form-control form-input" type="text" name="payAccount2" id="payAccount2" value="${userInfo.accountNum!""}"></input>
		         		</#if>
		         	</div>
	        	</div>
	        	<span class="col-lg-3 col-md-4 col-md-offset-1 warningDiv marginTop" style="display:block;"id="payAccountErr2"></span>
	      	</div>

					<div class=" col-md-10 col-md-offset-1">
			      		<div class="form-group"  id="checkCompanyNum">
				       		<div class="col-lg-3 col-md-4 col-md-offset-1">
				           		<label class="control-label form-label" for="blNum"><i class="form-required">*</i>营业执照号：</label>
				        	</div>
				       		<div class="col-lg-6 col-md-7 col-md-offset-1">
				       		<#if userInfo.identifyInfo.status == 4>
				       			<input class="form-control form-input-left-offset" onkeyup="clearCheckNotNull('checkCompanyNum')" value="${userInfo.identifyInfo.businessLicense!""}" type="text" name="blNum" id="blNum" placeholder="1-20位字符" maxlength="20" readonly></input>
				       		<#else>
				       			<input class="form-control form-input-left-offset" onkeyup="clearCheckNotNull('checkCompanyNum')" value="${userInfo.identifyInfo.businessLicense!""}" type="text" name="blNum" id="blNum" placeholder="1-20位字符" maxlength="20"></input>
				       		</#if>
				        	</div>
				        	<span class="col-md-offset-1 col-md-11 warningDiv marginTopL">请填写营业执照号</span>
			       		</div>

			      		<div class="form-group">
			       	 		<div class="col-lg-12 col-md-11 col-md-offset-1">
			           			<label class="control-label form-label" for="blPic"><i class="form-required">*</i>营业执照：</label>
			         		</div>
			         		<#if userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 3 || userInfo.identifyInfo.status == 4>
			         			<div class="col-md-12" id="compangChangeCancle">
			         				<div class="col-md-offset-1 col-md-8">
			         					<img src="${userInfo.identifyInfo.businessLicenseImg!""}" style="width:400px;height: 450px;"/>
			         				</div>
			         				<#if userInfo.identifyInfo.status == 2 || userInfo.identifyInfo.status == 3>
				       				<div class="col-md-2">
				       					<img style="width:26px;height: 26px;" src="${ctx}/img/revise.png" onclick="companyChange(this)">
				       				</div>
				       				</#if>
			         			</div>
			         			<div style="display:none" id="companybusinessLicense">
					       			<div class="col-lg-10 col-md-10  col-md-offset-1">
					           			<input id="blPic" name="blPic" class="file" type="file"></input>
					         		</div>
					         		<div class="col-md-1">
				       					<img style="width:23px;height: 23px;" src="${ctx}/img/delect.png" onclick="companyDelChange()"/>
					         		</div>
					         		<div class="col-lg-12 col-md-11  col-md-offset-1" >
						         	 	<span id="blPicMsg" class="error-message"></span>
						       		</div>
					         		<div class="col-lg-11 col-md-11  col-md-offset-1">
					         	 		<p>将<font color="red">通过年检</font>的营业执照原件或盖章的复印件扫描、拍照后上传，文件支持5M以内的PNG、JPG、GIF格式;Mac OS系统请使用新版Firefox浏览器上传，也可联系码客帮客服协助上传</p>
					         		</div>
			         			</div>
			       			<#else>
					       			<div class="col-lg-10 col-md-10  col-md-offset-1">
					           			<input id="blPic" name="blPic" class="file" type="file"></input>
					         		</div>
					         		<div class="col-lg-12 col-md-11  col-md-offset-1" >
						         	 	<span id="blPicMsg" class="error-message"></span>
						       		</div>
					         		<div class="col-lg-11 col-md-11  col-md-offset-1">
					         	 		<p>将<font color="red">通过年检</font>的营业执照原件或盖章的复印件扫描、拍照后上传，文件支持5M以内的PNG、JPG、GIF格式;Mac OS系统请使用新版Firefox浏览器上传，也可联系码客帮客服协助上传</p>
					         		</div>
			       			</#if>
			     		</div>
					</div>
						<#if userInfo.identifyInfo.status == 2>
							<button type="button" onclick="statusCompanythirdToSecond()" class="btn button-style col-md-2 col-md-offset-1">< 上一步</button>
						<#else>
							<button type="button" onclick="companythirdToSecond()" class="btn button-style col-md-2 col-md-offset-1">< 上一步</button>
						</#if>

							<button type="submit" class="btn button-style col-md-2 col-md-offset-6">保存</button>
			   </div>

			   </fieldset>

	       <div>
	       		<span class="text-left form-message-span" style="display:none" id="athenmsgspan"></span>
	       </div>
			    </form>
			    </div>
			  </div>
		  </#if>
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
   	<input type="hidden" id="IdentifyStep" value="${userInfo.identifyStep!""}"/>
   	<div id="userInfoBase" style="display: none;"></div>
   </div>

		  <script type="text/javascript" src="${ctx}/js/home/userinfo.js?v=${version}"></script>
      <!--CNZZ CODE-->
      ${cnzz_html}
     <!--END OF CNZZ CODE-->
  </body>
</html>
