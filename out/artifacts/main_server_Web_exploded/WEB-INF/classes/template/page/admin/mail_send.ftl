<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>用户认证审核 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    ${tools_includes}
    <link rel="stylesheet" type="text/css" href="${ctx}/css/admin/mail_send.css?v=${version}"/>
    <script type="text/javascript" src="${ctx}/js/admin/mail_send.js?v=${version}"></script>
  </head>
  <body>
  <#include "../header.ftl">
  
  <div class="container body">
  	<form action="javascript:doSend();" method="post" id="emailForm">
  		<div class="col-md-12 text-center"><h3>活动邮件推送工具</h3></div>
  		<div class="col-md-12">
  			
  			<div class="col-xs-offset-1 col-xs-10 col-md-offset-1 col-md-10">
  				<div class="col-xs-12 col-md-2 text-center">
  					<label for="mailList"><i class="form-required">*</i>收件人邮件列表（请以分号";"分割）：</label>
  				</div>
  				<div class="col-xs-12 col-md-10">
  					<textarea name="mailList" id="mailList" rows="5" placeholder="请填写接收人" required></textarea>
  				</div>
  			</div>

			<div class="col-xs-offset-1 col-xs-10 col-md-offset-1 col-md-10">
				<div class="col-xs-12 col-md-2 text-center">
					<label for="maillist">密送人邮件列表（请以分号";"分割）：</label>
				</div>
				<div class="col-xs-12 col-md-10">
					<textarea name="mailList" id="mailBccList" rows="5"  placeholder="请输入密送人邮件列表"></textarea>
				</div>
			</div>

			<div class="col-xs-offset-1 col-xs-10 col-md-offset-1 col-md-10">
				<div class="col-xs-12 col-md-2 text-center">
					<label for="content">发送频率间隔(秒)：</label>
				</div>
				<div class="col-xs-12 col-md-10">
					<input class="form-control" name="time" id="time" type="text" placeholder="请输入发送频率间隔，默认为5秒" />
				</div>
			</div>

			<div class="col-xs-offset-1 col-xs-10 col-md-offset-1 col-md-10">
				<div class="col-xs-12 col-md-2 text-center">
					<label for="content">每次投递份数(只对密送有效)：</label>
				</div>
				<div class="col-xs-12 col-md-10">
					<input class="form-control" name="copies" id="copies" placeholder="请输入每次投递份数，默认为5份" type="text"/>
				</div>
			</div>

			<div class="col-xs-offset-1 col-xs-10 col-md-offset-1 col-md-10">
				<div class="col-xs-12 col-md-2 text-center">
					<label for="content"><i class="form-required">*</i>邮件标题：</label>
				</div>
				<div class="col-xs-12 col-md-10">
					<input class="form-control" name="title" id="title" type="text" placeholder="请输入标题" required/>
				</div>
			</div>

  			<div class="col-xs-offset-1 col-xs-10 col-md-offset-1 col-md-10 contentMsg-margin-top">
  				<div class="col-xs-12 col-md-2 text-center">
  					<label for="content"><i class="form-required">*</i>邮件内容：</label>
  				</div>
  				<div class="col-xs-12 col-md-10">
  					<textarea name="content" id="content" rows="10" placeholder="请输入内容" required></textarea>
  				</div>
  			</div>
  			<div class="col-xs-offset-1 col-xs-10 col-md-offset-1 col-md-10 text-center">
  				<button type="submit" class="btn btn-primary col-xs-offset-3 col-xs-6 col-md-offset-3 col-md-6">发送</button>
  			</div>
  			
  			<div class="col-xs-offset-1 col-xs-10 col-md-offset-1 col-md-10 sendMsg-margin-top">
 					<div class="col-xs-12 col-md-2 text-center">
 						<label for="Msg">发送日志：</label>
 					</div>
  				<div class="col-xs-12 col-md-10">
  					<div id="mailSendConsole" class="col-md-12 sendMsg"></div>
  				</div>
  			</div>
  		</div>
  	</form>
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