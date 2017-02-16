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

		<script type="text/javascript" scr="${ctx}/js/home/request_handle.js?v=${version}"></script>
	</head>

	<body>
		<header data-am-widget="header" class="am-header am-header-default">
			<div class="am-header-left am-header-nav">
				 <a href="/mobile" class="">
					<i class="am-header-icon am-icon-home"> </i>
				</a>
			</div>
			<div class="am-header-title">发布需求</div>
		</header>
		
		<nav data-am-widget="menu" class="am-menu  am-menu-offcanvas1" data-am-menu-offcanvas>
    <a href="javascript: void(0)" class="am-menu-toggle">
        <i class="am-menu-toggle-icon am-icon-bars">
        </i>
    </a>
    <div class="am-offcanvas">
        <div class="am-offcanvas-bar am-offcanvas-bar-flip">
            <ul class="am-menu-nav sm-block-grid-1">
                <li class="">
                    <a href="/mobile" class="main-title">
                        首页
                    </a>
                </li>
                <li class="">
                    <a href="#">
                        发布需求
                    </a>
                </li>
                <li class="">
                    <a href="/mobile/reserve">
                        预约顾问
                    </a>
                </li>
                <li class="">
                    <a href="/mobile/market">
                        项目市场
                    </a>
                </li>
                <li class="">
                    <a href="/mobile/how_to_use">
                        如何使用
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
		
<div class="request-view">
	<form id="publishRequestForm" class="am-form" action="javascript:doPublish();" role="form" method="post">
		<fieldset>
			<div class="am-u-sm-12 form-group">
				<input type="text" name="contactsName" id="contactsName" class="am-form-field am-radius" aria-describedby="helpBlock" placeholder="您的称呼" maxlength="20" required>
		    </div>
		<div class="am-u-sm-12 form-group">
	<input class="am-form-field am-radius" name="contactNumber" id="contactNumber" aria-describedby="helpBlock" 
	 placeholder="您的电话" required maxlength="11" minlength="11" digits="true" isMobilePhoneNumber="true">
		</div>

<div class="wbm-item-price">
    <div class="am-form-group am-radius">
        <select id="price" class="select3 am-form-control" name="price">
            <option value="">项目预算</option>	
            <option value="">1000-5000元</option>
            <option value="">5000-10000元</option>
            <option value="">1万-3万</option>
            <option value="">3万-5万</option>
            <option value="">5万-10万</option>
            <option value="">10万以上</option>
            <option value="">待商议</option>
        </select>
    </div>
   </div>
       
        
	<div class="am-u-sm-12 form-group">
		<input type="text" class="am-form-input am-form-control" name="peroid" id="peroid" placeholder="交付周期(天数)" maxlength="3" digits="true"  required>
	</div>
	<div class="am-u-sm-12 form-group-4-textarea">
    	<textarea class="item-text" rows="4" id="content" name="content" maxlength="200" placeholder="项目描述，例如：做一款类似滴滴打车的软件..." required></textarea>
    </div>
		<div class="am-u-sm-12">
			<button type="submit" class="am-btn am-btn-primary am-btn-block">提交</button>
				</div>
				<div>
		      </fieldset>
			</form>
		</div>
		
	  <!--CNZZ CODE-->
	  ${cnzz_html}
	  <!--END OF CNZZ CODE-->
	</body>
</html>