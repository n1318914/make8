<!DOCTYPE html>
<html>
<head>
    <title>如何运作 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    <style type="text/css">
    	.footer-advance{
    		margin-top:0px;
    	}
    </style>
</head>

<body>
	<!--header-->
    <#include "../header.ftl">
    <!--end of header-->
    
    <div style="background:url(../img/qa/htr-banner.jpg) no-repeat;background-position: 50% 0;margin-top:80px;height:533px;"></div>
	<div>
		<!--<img src="${store_location}/img/qa/htr-banner.jpg" style="margin-top:80px;width:100%;height:533px;"></img>-->
		<div class="text-center" style="background-color:#fffbe8;">
			<img src="${store_location}/img/qa/htr-content.png" style="margin-top:80px;width:899px;height:1742px;"></img>
		</div>
	</div>
	
    <!--start of footer-->
	<#include "../footer.ftl">
	<!--end of footer-->
	<!---start of help docker-->
	<div id="top"></div>
	<!--end of help docker-->
	
	<div><input type="hidden" id="topurl" value="${ctx}/"/></div>
	
    <!--CNZZ CODE-->
    ${cnzz_html}
    <!--END OF CNZZ CODE-->
      
</body>
</html>