<!DOCTYPE html>
<html>

	<head>
		<title>解决方案 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
			<#include "../comm-includes.ftl">
				${basic_includes}
				<link href="${ctx}/css/public/solution.css?v=${version}" rel="stylesheet" type="text/css">
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->

			<div class="col-xs-12 text-center col-padding banner">
				<div class="container">
					<img src="${store_location}/img/solution_banner.png"/>
				</div>
			</div>	

			<div class="shop col-xs-12 col-padding">
				<div class="container">
					<div class="col-xs-4 col-padding hidden-xs">
						<img src="${store_location}/img/shop_left.png" alt="">
					</div>
					<div class="col-xs-12 col-sm-4">
						<div class="text-center shop-title">微信电商</div>
						<div class="text-center">
							<button onclick="window.location.href='/home/request'" class="btn btn-shop">立即发布需求</button>
						</div>
					</div>
					<div class="col-xs-4 shop-right col-padding hidden-xs">
						<img src="${store_location}/img/shop_right.png" alt="">
					</div>
				</div>
			</div>

			<div class="video col-xs-12 col-padding">
				<div class="container">
					<div class="col-xs-4 text-center col-padding hidden-xs">
						<img src="${store_location}/img/video_left.png" alt="">
					</div>
					<div class="col-xs-12 col-sm-4">
							<div class="text-center video-title">视频直播</div>
							<div class="text-center partner">
								合作伙伴 : <img src="${store_location}/img/huke_logo.png" alt="">
							</div>
							<div class="text-center">
								<button onclick="window.location.href='/home/request'" class="btn btn-video">立即发布需求</button>
							</div>
					</div>
					<div class="col-xs-4 text-center video-right hidden-xs col-padding">
						<img src="${store_location}/img/video_right.png" alt="">
					</div>
				</div>
			</div>

			<div class="col-xs-12" style="background:url(${store_location}/img/jinfuzi_bg.png) center;padding:54px 15px">
				<div class="text-center ft-title">金融科技</div>
				<div class="text-center partner">
					合作伙伴 : <img src="${store_location}/img/jinfuzi_logo.png" alt="">
				</div>
				<div class="text-center">
					<button onclick="window.location.href='/home/request'" class="btn btn-ft">立即发布需求</button>
				</div>
			</div>

			<div class="col-xs-12 face-recognition" style="background:url(${store_location}/img/face_bg.jpg) center;">
				<div class="container">
					<div class="col-xs-4 col-visible-sm hidden-xs col-padding">
						<img src="${store_location}/img/face_left.png" alt="">
					</div>
					<div class="col-xs-12 col-sm-4">
						<div class="text-center fr-title">人脸识别</div>
						<div class="text-center partner">
							合作伙伴 : <img src="${store_location}/img/plxtalks_logo.png" alt="">
						</div>
						<div class="text-center">
							<button onclick="window.location.href='/home/request'" class="btn btn-fr">立即发布需求</button>
						</div>
					</div>
					<div class="col-xs-4 text-right face-right hidden-xs col-padding">
						<div>
							<img src="${store_location}/img/face_right.png" alt="">
						</div>
					</div>
				</div>	
			</div>

			<div>
				<input type="hidden" id="topurl" value="${ctx}/" />
				<input type="hidden" id="reload" value="0" />
			</div>

			<!--start of footer-->
			<#include "../footer.ftl">
			<!--end of footer-->
			<!---start of help docker-->
			<div id="top"></div>
			<!--end of help docker-->

			<!--CNZZ CODE-->
			<!--${cnzz_html}-->
			<!--END OF CNZZ CODE-->
	</body>

</html>
