<!DOCTYPE html>
<html>
	<head>
		<#include "../common.ftl">
		<#include "../comm-includes.ftl">
		<title>码客帮 - 互联网软件外包交易平台</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="keywords" content="软件外包,项目外包,互联网软件外包,网站外包,安卓APP外包,Andriod APP,iOS APP外包,微信开发外包">
		<meta name="description" content="码客帮(www.make8.com)是国内第一家互联网软件外包交易平台，专注于帮助互联网早期创业团队找到靠谱的软件外包服务商。">
   		${mobile_includes}
		
		<script type="text/javascript">
			$(document).ready(function() {
				jQuery.validator.addMethod("isMobilePhoneNumber", function() {
					var mobile = $("#contactNumber").val();
					return isMobile(mobile);
				}, "手机号码不正确");
				$("#publishRequestForm").validate({
					errorElement: "span",
					messages: {
						content: {
							required: "请输入项目需求"
						},
						contactsName: {
							required: "请输入您的姓名"
						},
						contactNumber: {
							required: "请输入您的手机号码"
						}
					}
				});
			});

			function doPublish() {
				var content = $("#content").val();
				var contactsName = $("#contactsName").val();
				var contactNumber = $("#contactNumber").val();
				var murl = "/api/wx/request";
				var paraData = "type=mobile&contactsName=" + contactsName + "&contactNumber=" + contactNumber + "&content=" + content;
				$.ajax({
					"type": "POST",
					"data": paraData,
					"async": "false",
					"url": murl,
					"success": function(data) {
						if (data.resultCode != 0) {
							alert(data.errorMsg);
						} else {
							location.href = data.msg;
						}
					}
				});
			}
		</script>
	</head>

	<body>
		<header data-am-widget="header" class="am-header am-header-default">
			<div class="am-header-left am-header-nav">
				<a href="/mobile" class="">
					<i class="am-header-icon am-icon-home"> </i>
				</a>
			</div>
			<div class="am-header-title">预约顾问</div>
		</header>
		<nav data-am-widget="menu" class="am-menu  am-menu-offcanvas1" data-am-menu-offcanvas>
			<a href="javascript: void(0)" class="am-menu-toggle">
				<i class="am-menu-toggle-icon am-icon-bars"></i>
			</a>
			<div class="am-offcanvas">
				<div class="am-offcanvas-bar am-offcanvas-bar-flip">
					<ul class="am-menu-nav sm-block-grid-1">
						<li>
							<a href="/mobile" class="main-title">首页</a>
						</li>
						<li>
							<a href="/mobile/request">发布需求</a>
						</li>
						<li>
							<a href="#">预约顾问</a>
						</li>
						<li >
							<a href="/mobile/market">项目市场 </a> 
						</li>
						<li>
							<a href="/mobile/how_to_use">如何使用</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		
		
		<form id="publishRequestForm" class="am-form" action="javascript:doPublish();" role="form" method="post">
			<fieldset>
				<div class="reservation-view">
					<div class="am-u-sm-12">
						<p class="main-header">码客帮？联系顾问吧！</p>
						<p class="secondary-header">项目询价、需求分析、找到最好的服务商</p>
					</div>
					
				<div class="am-u-sm-12 form-group">
		        	<input type="text" name="contactsName" id="contactsName" class="am-form-field am-radius" aria-describedby="helpBlock" placeholder="您的称呼" maxlength="20" required>
				</div>
						
			<div class="am-u-sm-12 form-group">
		       <input class="am-form-field am-radius" name="contactNumber" id="contactNumber" aria-describedby="helpBlock" placeholder="您的电话" required maxlength="11" minlength="11" digits="true" isMobilePhoneNumber="true">
		    </div>
		
				<div class="am-u-sm-12 form-group-4-textarea">
		    		<textarea type="text" class="am-form-control am-form-textarea"  rows="5" id="content" name="content" placeholder="您的需求，如:开发一个类似滴滴打车的IOS APP预算10万，深圳" required></textarea>
		    </div>
		   
		   
			
		          <div class="item-tijiao am-u-sm-12">
		          	<div class="item-demand">
		        	 <button type="submit" class="am-btn am-btn-primary am-btn-block" id="wbm-tijiaobt">免费预约</button>
		        	
		          </div>
		        </div>
		      </div>
		  
		</fieldset>
	</form>
   </div>
   
   <!--CNZZ CODE-->
   ${cnzz_html}
   <!--END OF CNZZ CODE-->
</body>
</html>