<!DOCTYPE html>
<html>
<html lang="zh-CN">
<head lang="en">
    <title>${notice.title}</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
	  ${basic_includes}
    
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
	    	//countDown(10,'login.html');
	    	showFooter(1);
	    });
    </script>
</head>
<body>
	<!--header-->
    <#include "../header.ftl">
    <!--end of header-->

	<div class="container">
		<div class="col-lg-offset-2 col-lg-8 col-md-8 register-notice-block">
		    <div class="col-lg-offset-1 col-lg-1 col-md-1">
		    	<i class="fa fa-4x fa-info-circle text-primary"></i>
		    </div>
		     <div class="col-lg-10 col-md-10 content">
		        ${notice.content}
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
   </div>
</body>
</html>