<!DOCTYPE html>
<html>
	<head>
		<#include "../common.ftl">
		<#include "../comm-includes.ftl">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>码客帮 - 互联网软件外包交易平台</title>
		<meta name="keywords" content="软件外包,项目外包,互联网软件外包,网站外包,安卓APP外包,Andriod APP,iOS APP外包,微信开发外包">
		<meta name="description" content="码客帮(www.make8.com)是国内第一家互联网软件外包交易平台，专注于帮助互联网早期创业团队找到靠谱的软件外包服务商。">
        ${mobile_includes}
        <script src="${ctx}/thirdparty/countUp/countUp.min.js?v=${version}"></script>
        <script type="text/javascript">
        
	   	function showAnalysisNum(){
		      	var regNum = $("#h_registerNum").val();
				var projectNum = $("#h_projectNum").val();
				var totalAmount = $("#h_totalAmount").val();
			
			var options = {
				useEasing : true,
				useGrouping : true,
				separator : ',',
				decimal : '.',
				prefix : '',
				suffix : ''
			};
			
			var duration = 3;
			var registerNumCP = new CountUp("registerNum", 0, regNum, 0, duration, options);
			var projectNumCP = new CountUp("projectNum", 0, projectNum, 0, duration, options);
			var totalAmountCP = new CountUp("totalAmount", 0, totalAmount, 0, duration, options);
			
			//注释
			/*$(window).scroll(function(){
				if($(window).scrollTop() >= 0){
					registerNumCP.start();
					projectNumCP.start();
					totalAmountCP.start();
				}
			});*/
			
			registerNumCP.start();
			projectNumCP.start();
			totalAmountCP.start();
	   	}
	   	
	    $(document).ready(function(){
	    	showAnalysisNum();
	    });
	   	</script>
	</head>

	<body>
		
	<!--hidden parameters-->
	<div>
		<input type="hidden" id="h_registerNum" value="${registerNum}"/>
		<input type="hidden" id="h_projectNum" value="${projectNum}"/>
		<input type="hidden" id="h_totalAmount" value="${totalAmount}"/>
	</div>
	<!--end of hidden parameters-->
	
		<div class="">
			<div class="wbm-background">
				<div class="logos">
					<img src="${store_location}/img/mobile/logo.png">
				</div>
				<!--<div class="wbm-slogan">-->
					<div class="am-g">
						<div class="am-u-sm-12">
							<h1 class="wbm-slog">让互联网软件外包更简单 </h1>
							<p>网站 iOS APP 安卓APP 微信开发 HTML5应用</p>
						</div>
						<div class="am-u-sm-12 wbm-munbg">
							<div class="am-u-sm-4">
								<div class="wbm-pep">
								<p class="wbm-pt"><span class="num"><b id="registerNum"></b></span></p>
								<p class="wbm-mb">注册人数</p>
									</div>
										</div>
							<div class="am-u-sm-4">
								<div class="wbm-mun">
								<p class="wbm-pt"><span class="num"><b id="projectNum"></b></span></p>
								<p class="wbm-mb">项目数</p>
									</div>
								</div>
							<div class="am-u-sm-4">
								<div class="wbm-mon">
								<p class="wbm-pt"><span class="num"><b id="totalAmount"></b></span></p>
								<p class="wbm-mb">项目金额</p>
									</div>
								</div>
							</div>
					</div>
				</div>
			

			<div class="wbm-needName">
				<div class="wbm-needName-w">
					<ul data-am-widget="gallery" class="am-gallery am-avg-sm-2 am-avg-md-3 am-avg-lg-4 am-gallery-default">
						<li class="bitar1">
							<div class="am-gallery-item">
								<div class="aqkit">
									<a href="/mobile/request">
										<img src="${store_location}/img/mobile/request.png">
								     </a>
									 <p class="market-pit">发布需求</p>
								</div>
							</div>
						</li>

						<li class="bitar2">
							<div class="am-gallery-item">
								<div class="aqkit">
									<a href="/mobile/reserve">
										<img src="${store_location}/img/mobile/reservation.png">
										<p class="market-pit">预约顾问</p>
									</a>
								</div>
							</div>
						</li>

						<li class="bikar">
							<div class="am-gallery-item">
								<div class="aqkit">
									<a href="/mobile/market">
										<img src="${store_location}/img/mobile/market.png">
										<p class="market-pit">项目市场</p>
									</a>
								</div>
							</div>
						</li>

						<li>
							<div class="am-gallery-item">
								<div class="aqkit">
									<a href="/mobile/how_to_use" class="aqkit">
										<img src="${store_location}/img/mobile/help.png">
										<p class="market-pit">如何使用</p>
									</a>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>

			<div class="wbm-connect">
				<div class="wbm-connects">
					<span class="connected">客户服务</span>
					<p>电话: 4000-818-530</p>
					<p>邮箱: service@waibao.me</p>
				</div>
			</div>

			<div class="show-me">
				<div class="about-me">
					<p class="abouts">关于我们</p>
					<p class="abouts-show">码客帮隶属于深圳市云达人科技有限公司，是一个专注于为互联网早期创业团队提供技术外包服务的互联网平台，公司位于深圳市软件产业基地，创始团队主要来自于华为、Oracle、金蝶、软通动力等知名企业。
					</p>
				</div>
			</div>
			
	    <#include "../mobile-footer.ftl">
				
		<!--CNZZ CODE-->
		${cnzz_html}
		<!--END OF CNZZ CODE-->
	</body>
</html>