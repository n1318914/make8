<#--ftl头部包含的css和js-->

<#assign basic_without_fh_includes='
	<meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content=""><meta name="description" content="">
    <!--<link href="${ctx}/css/bootstrap.min.css?v=${version}" rel="stylesheet">-->
    <link href="${ctx}/css/form.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/thirdparty/jqueryvalidate/css/cmxform.css?v=${version}" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.placeholder.js?v=${version}"></script>
    <!--<script type="text/javascript" src="${ctx}/js/bootstrap.min.js?v=${version}"></script>-->
    <script type="text/javascript" src="${ctx}/js/comm.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/jquery.validate.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/localization/messages_zh.js?v=${version}"></script>
'/>

<#assign simple_includes='
	<meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content=""><meta name="description" content="">
    <link href="${ctx}/css/bootstrap.min.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/form.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/header.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/footer-simple.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/thirdparty/jqueryvalidate/css/cmxform.css?v=${version}" rel="stylesheet">

    <script type="text/javascript" src="${ctx}/js/jquery.min.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.placeholder.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/comm.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/header.js?v=${version}"></script>

    <script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/jquery.validate.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/localization/messages_zh.js?v=${version}"></script>
'/>

<#assign basic_includes='
	<meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content=""><meta name="description" content="">
    <link href="${ctx}/css/bootstrap.min.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/header.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/footer.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/comm.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/helpdocker.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/thirdparty/jqueryvalidate/css/cmxform.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/thirdparty/jqueryconfirm/css/jquery-confirm.min.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/thirdparty/bootstrapdialog/css/bootstrap-dialog.min.css?v=${version}"  rel="stylesheet" type="text/css">
    <link href="${ctx}/thirdparty/jquerypagination/css/jquery.pagination.css?v=${version}"  rel="stylesheet" type="text/css">


    <script type="text/javascript" src="${ctx}/js/jquery.min.js?v=${version}"></script>
   	<script type="text/javascript" src="${ctx}/js/angular.min.js?v=${version}"></script>
   	<script type="text/javascript" src="${ctx}/js/angular-route.min.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/angular-resource.min.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/angular-comm.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/map.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.placeholder.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/header.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/comm.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/footer.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/helpdocker.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/jquery.validate.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/localization/messages_zh.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryconfirm/js/jquery-confirm.min.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/bootstrapdialog/js/bootstrap-dialog.min.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-ui.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jquerypagination/js/jquery.pagination-1.2.7.min.js?v=${version}"></script>
'/>

<#assign basic_includes2='
	<meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content=""><meta name="description" content="">
    <link href="${ctx}/css/bootstrap.min.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/header.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/footer.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/comm.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/css/helpdocker.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/thirdparty/jqueryvalidate/css/cmxform.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/thirdparty/jqueryconfirm/css/jquery-confirm.min.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/thirdparty/bootstrapdialog/css/bootstrap-dialog.min.css?v=${version}"  rel="stylesheet" type="text/css">
    <link href="${ctx}/thirdparty/jquerypagination/css/jquery.pagination.css?v=${version}"  rel="stylesheet" type="text/css">


    <script type="text/javascript" src="${ctx}/js/jquery.min.js?v=${version}"></script>
   	<script type="text/javascript" src="${ctx}/js/angular.min.js?v=${version}"></script>
   	<script type="text/javascript" src="${ctx}/js/angular-route.min.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/angular-resource.min.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/angular-comm.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/map.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.placeholder.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/header.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/comm.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/footer.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/js/helpdocker.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/jquery.validate.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/localization/messages_zh.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryconfirm/js/jquery-confirm.min.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/bootstrapdialog/js/bootstrap-dialog.min.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jquerypagination/js/jquery.pagination-1.2.7.min.js?v=${version}"></script>
'/>

<#assign tools_includes='
	<link href="${ctx}/thirdparty/countdown/css/jquery.countdown.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/thirdparty/fileinput/css/fileinput.min.css?v=${version}"  rel="stylesheet" type="text/css">
    <link href="${ctx}/thirdparty/datetimepicker/css/bootstrap-datetimepicker.css?v=${version}" rel="stylesheet">

    <script type="text/javascript" src="${ctx}/js/projectprogress.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/countdown/js/jquery.countdown.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryraty/jquery.raty.min.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/thirdparty/fileinput/js/fileinput.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/fileinput/js/fileinput_locale_zh.js?v=${version}"></script>

    <script type="text/javascript" src="${ctx}/thirdparty/datetimepicker/js/bootstrap-datetimepicker.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js?v=${version}"></script>
 '/>

 <#assign mobile_includes='
	<meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Cache-Control" content="no-siteapp"/>

	<link href="${ctx}/css/amazeui.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css?v=${version}" rel="stylesheet">
    <link href="${ctx}/thirdparty/jqueryvalidate/css/cmxform.css?v=${version}" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/css/mobile/wbm-app.css?v=${version}">

    <script type="text/javascript" src="${ctx}/js/jquery.min.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/comm.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/jquery.validate.js?v=${version}"></script>
    <script type="text/javascript" src="${ctx}/thirdparty/jqueryvalidate/js/localization/messages_zh.js?v=${version}"></script>
    <script src="${ctx}/js/amazeui.min.js?v=${version}"></script>
 '/>
