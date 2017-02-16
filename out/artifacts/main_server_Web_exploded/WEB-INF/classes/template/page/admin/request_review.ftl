<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>项目审核 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    ${tools_includes}
		<link rel="stylesheet" href="${ctx}/css/admin/request_review.css?v=${version}" />
    <script type="text/javascript" src="${ctx}/js/admin/request_review.js?v=${version}"></script>
  </head>
  <body >
  <!--header-->
   <#include "../header.ftl">
   <!--end of header-->

  <div class="body-offset"></div>

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
  <form action="javascript:submitform()" method="post">
  <div class="container" no-cloak>
  	<div class="col-xs-12 col-md-12 project-review-title text-center">项目详情</div>
  	<div class="col-xs-12 col-md-12 project-review">
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				项目ID：
  			</div>
  			<div class="col-md-5 text-left project-right">
  				${projectInSelfRun.id!""}
  			</div>
  		</div>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				项目名称：
  			</div>
  			<div class="col-md-7 text-left project-right">
  				${projectInSelfRun.name!""}
  			</div>
  		</div>
  		<#if projectInSelfRun.creator.name??>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				雇主：
  			</div>
  			<div class="col-md-7 text-left project-right">
  				${projectInSelfRun.creator.name!""}
  			</div>
  		</div>
  		</#if>

  		<#if projectInSelfRun.creator.email??>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				雇主邮箱：
  			</div>
  			<div class="col-md-7 text-left project-right">
  				${projectInSelfRun.creator.email!""}
  			</div>
  		</div>
  		</#if>

  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				雇主手机号：
  			</div>
  			<div class="col-md-7 text-left project-right">
  				${projectInSelfRun.creator.mobile!""}
  			</div>
  		</div>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				项目类型：
  			</div>
  			<div class="col-md-5 text-left project-right">
  				${projectInSelfRun.type!""}
  			</div>
  		</div>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				项目状态：
  			</div>
  			<div class="col-md-5 text-left project-right">
  				${projectInSelfRun.statusTag!""}
  			</div>
  		</div>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				预算：
  			</div>
  			<div class="col-md-5 text-left project-right">
  				${projectInSelfRun.budget!""}
  			</div>
  		</div>
  		<#if projectInSelfRun.statusTag == '开发中' || projectInSelfRun.statusTag == '已完成'>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				成交金额：
  			</div>
  			<div class="col-md-5 text-left project-right">
  				${projectInSelfRun.dealCost!""}元
  			</div>
  		</div>
  		</#if>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				创建时间：
  			</div>
  			<div class="col-md-5 text-left project-right">
  				${projectInSelfRun.createTime?string("yyyy-MM-dd HH:mm")}
  			</div>
  		</div>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  			项目周期：
  			</div>
  			<div class="col-md-5 text-left project-right">
  				${projectInSelfRun.period!""}天
  			</div>
  		</div>
      <div class="col-md-12 project-review-div">
        <div class="col-md-3 text-right project-left">
        分配顾问：
        </div>
        <div class="col-md-5 text-left project-right">
          ${projectInSelfRun.consultant.name!""}[id:${projectInSelfRun.consultant.id!""}]
        </div>
      </div>
      <div class="col-md-12 project-review-div">
        <div class="col-md-3 text-right project-left">
        需要购买域名：
        </div>
        <div class="col-md-5 text-left project-right">
          <#if projectInSelfRun.isNeedBuyDomain == 1>
           是
           <#else>
           否
          </#if>
        </div>
      </div>
      <div class="col-md-12 project-review-div">
        <div class="col-md-3 text-right project-left">
        需要购买云服务器和数据库：
        </div>
        <div class="col-md-5 text-left project-right">
          <#if projectInSelfRun.isNeedBuyServerAndDB == 1>
           是
           <#else>
           否
          </#if>
        </div>
      </div>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				需求详情：
  			</div>
  			<div class="col-md-7 text-left project-content">
  				<p>${projectInSelfRun.content}</p>
  			</div>
  		</div>
  		<#if projectInSelfRun.attachments??>
	  		<div class="col-md-12 project-review-div">
	  			<div class="col-md-3 text-right project-left">
	  				项目附件：
	  			</div>
	  			<div class="col-md-7 text-left project-right-accessories">
	  			<#list projectInSelfRun.attachments as attachment>
	  			   <a href="${attachment.path}" style="text-decoration: none;cursor: pointer;">${attachment.name}</a><br>
	  				<!--<div class="select-enclosure"><a href="${projectInSelfRun.attachment!""}">查看附件</a></div>-->
	  			</#list>
	  			</div>
	  		</div>
  		</#if>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				招募角色：
  			</div>
  			<div class="col-md-9 padding-left">
  				<#if projectInSelfRun.statusTag == '审核中'>
						<#list listDictItem as tag>
			        <#if tag.dictGroupId==7>
								<div class="col-xs-6 col-sm-3 col-md-3 padding-left recruited">
									<div onclick="typeSelect(this)"><img src="${ctx}/img/checkboxchecked.png"/></div>
									<p value="${tag.value}">${tag.name}</p>
								</div>
			  	  	</#if>
						</#list>
					<#else>
					 <#if projectInSelfRun.enrollRoleList?? && projectInSelfRun.enrollRoleList?size gt 0>
							<#list projectInSelfRun.enrollRoleList as tag>
									<div class="col-xs-6 col-sm-3 col-md-3 padding-left recruited">
										<div><img class="active" src="${ctx}/img/checkboxchecked.png"/></div>
										<p value="${tag.value}">${tag.name}</p>
									</div>
							</#list>
						</#if>
					</#if>
  			<div id="recruitedMsg" class="warningMsg">至少招募一种角色</div>
  			</div>
  			<input type="hidden" value="${projectInSelfRun.enrollRole!""}" id="typeSelect"/>
  		</div>
  		<div id='gitPanel' class='col-md-12'>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				<span style='color:red;'>*</span>仓库别名(参考：项目拼音+项目ID，如：appdianshang121)：
  			</div>
  			<div class="col-md-7 text-left pass-div" style='padding-top:5px;margin-left:0;'>
  				<div class="col-xs-9 col-md-9">
  					<#if projectInSelfRun.statusTag == '审核中'>
  						<input class='form-control' id='repo_name' type='input'/>
  						<#else>
  						<input class='form-control' id='repo_name' type='input' readonly value='${projectInSelfRun.repoNick!""}'/>
  					</#if>
  				</div>
  			</div>
  		</div>
  		<#if projectInSelfRun.statusTag == '审核中'>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				仓库描述：
  			</div>
  			<div class="col-md-7 text-left pass-div" style='padding-top:5px;margin-left:0;'>
  				<div class="col-xs-9 col-md-9">
  						<textarea class='form-control' id='description' type='input' style='resize:vertical;' ></textarea>
  				</div>
  			</div>
  		</div>
  		</#if>
  		</div>
  		<#if projectInSelfRun.statusTag == '审核中'>
  		<div class="col-md-12 project-review-div">
  			<div class="col-md-3 text-right project-left">
  				审核操作：
  			</div>
  			<div class="col-md-7 text-left pass-div">
  				<div class="col-xs-6 col-md-6">
  					  <input type="radio" name="pass" value="1" onclick="failReasonShow()" checked>通过</input>
  				</div>
  				<div class="col-xs-6 col-md-6">
  					  <input type="radio" name="pass" id="fail-checked" value="2" onclick="failReasonShow()">不通过</input>
  				</div>
  			</div>
  		</div>

  		<div id="fail-reason" class="col-md-12 fail-reason">
  			<div class="col-md-3 text-right project-left">
  				不通过理由：
  			</div>
  			<div class="col-md-7 text-left project-content">
  				<textarea class="fail-textarea" name="fail-text" id="fail-text" onkeyup="checktextAreaNull(this.id)" placeholder="请在此输入不通过的理由..."></textarea>
  			</div>
  			<div class="col-md-offset-3 col-md-7 text-left padding-left warning">不通过理由不能为空！</div>
  		</div>
  		</#if>
  	</div>
  </div>

  <div class="container">
  	<div class="col-xs-12 col-md-12 project-operation text-center">
  		<#if projectInSelfRun.statusTag == '审核中'>
  		<div class="col-xs-6 col-md-6">
  			<button type="submit" class="btn btn-primary">审核提交</button>
  		</div>
  		</#if>
  		<div class="col-xs-6 col-md-6">
  			<button onclick="modifyBtn()" type="button" class="btn btn-primary">修改项目</button>
  			<!--<button type="button" class="btn btn-primary">修改项目</button>-->
  		</div>
  		<#if projectInSelfRun.statusTag == "待启动" || projectInSelfRun.statusTag == "开发中">
  			<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">项目转让</button>
  		</#if>
  	</div>
  </div>
</form>



<!-- 模态框（Modal） -->
<div class="container">
<form id="assignForm" role="form" action="javascript:submitTransform()">
<!--<form role="form" action="/api/selfrun/p/assign" method="post">-->
<input type="hidden" name="proId" id="proId"value="${projectInSelfRun.id!""}">
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" style="overflow: hidden;">
			<div class="modal-body my-body">
				<!--TODO -->
				<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h4 class="modal-title"><img src="${ctx}/img/trans.png">项目转让</h4>
				</div>

				<div class="form-group  col-md-10 col-md-offset-1">
						<div style="padding-top:2em;">当前创建者</div>
            <#if projectInSelfRun.creator.name??>
						   <input type="text" class="form-control" readonly name="creator" value="${projectInSelfRun.creator.name!""}">
            <#else>
                 <input type="text" class="form-control" readonly name="creator" value="${projectInSelfRun.creator.mobile!""}">
            </#if>
				</div>

				<div class="form-group-height dropdown col-md-10 col-md-offset-1">
					<div>转出者</div>
					<input type="text" class="form-control" required="true" maxlength="11" name="mobile" id="mobile" isMobilePhoneNumber="true" phoneNumExisting="true" placeholder="请输入手机号">
					<div class="msgspan" id="mobileMsg"></div>
<!--					<div class="input-group col-md-10 col-md-offset-1" style="padding-right:14px;padding-left:14px;">
						<input type="text" class="form-control" name="mobile" id="mobile" placeholder="搜索">
						<ul class="dropdown-menu col-xs-11 col-md-11 col-md-offset-1" style="margin-left:14px;">
	    	      <li role="presentation">
								<a  role="menuitem" tabindex="-1" href="#">数据挖掘</a>
							</li>
				    </ul>
            <span class="input-group-btn"   data-toggle="dropdown">
              <button class="btn sbtn" type="button">
                 <img src="${ctx}/img/search.png" style="width:20px">
              </button>
          	</span>
					</div>-->
				</div>

				<div class="form-group-height col-md-10 col-md-offset-1">
					<div>转让原因</div>
					<textarea class="form-control" rows="3" style="resize:none;" name="assignReason" id="assignReason" minlength="8"></textarea>
					<div class="msgspan" id="reasonMsg"></div>
				</div>

				<div class="m-footer col-md-10 col-md-offset-1 text-center btnstyle">
					<div class="col-xs-6">
						<button type="reset" class="btn btn-primary" data-dismiss="modal">取消</button>
					</div>
					<div class="col-xs-6">
						<button type="submit" class="btn btn-primary">转让</button>
					</div>
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
</div>
</form>
  <!--start of footer-->
	<div id="footerDiv"></div>
  <#include "../footer.ftl">
  <!--end of footer-->
  <!---start of help docker-->
  <div id="top"></div>
  <!--end of help docker-->

  <div><input type="hidden" id="topurl" value="${ctx}/"/></div>
</body>
</html>
