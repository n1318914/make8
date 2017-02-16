﻿<!DOCTYPE html>
<html>
<head>
    <title>修改密码成功 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
	${basic_includes}
</head>
<body>
   	<!--header-->
    <#include "../header.ftl">
    <!--end of header-->
   <div id="navDiv"></div>

	<div class="container">
		<div class="col-lg-offset-2 col-lg-8 col-md-8 notice-block">
		    <div class="col-lg-offset-1 col-lg-1 col-md-1">
		    	<i class="fa fa-4x fa-info-circle text-primary"></i>
		    </div>
		    <div class="col-lg-8 col-md-8">
          <#if resetStatus??>
		        <#if resetStatus == "1">
	        		<p>密码找回成功，请妥善保管好您的密码</p>
	        	<#else>
	        		<p>重置密码失败，<a href='/public/find_password'>点击重试</a></p>
		        </#if>
          </#if>
		        <p>此页面<span id="jumpTo">10</span>秒后自动转至登录页，<a href="/public/login">点击此处</a>直接跳转</p>
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
   	  <input type="hidden" id="topurl" value="${ctx}/"/>
   	  <input type="hidden" id="reload" value="0"/>
   </div>
</body>
<script type="text/javascript">
    function countDown(secs,surl){
        var jumpTo = $('#jumpTo');
        jumpTo.html(secs);

        if(--secs > 0){
            setTimeout("countDown("+secs+",'"+surl+"')",1000);
        }else{
            location.href=surl;
        }
    }

    $(document).ready(function(){
    	countDown(10,'/public/login');
    	showFooter(1);
    });
</script>
</html>
