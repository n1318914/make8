var domain = "";
var loginFailedTimeExceeded = 0;

function doLogin() {
	var email = $("#loginName").val().trim();
	var password = $("#wpassword").val();

	var murl = domain + "/api/u/login";
	var paraData = "loginName=" + email + "&password=" + password;

	if (loginFailedTimeExceeded == 1) {
		paraData += "&vcode=" + $("#vcode").val();
	}

	$.ajax({
		"dataType": "json",
		"type": "POST",
		"data": paraData,
		"async": "false",
		"url": murl,
		"success": function(data) {
			if (data.resultCode == 0) {
				location.href = data.msg;
				$("#loginMessage").html("");
			} else {
				$("#loginMessage").html(data.errorMsg);
				$("#loginMessage").show();

				if (data.loginFailedTimeExceeded != null && data.loginFailedTimeExceeded == 1) {
					loginFailedTimeExceeded = 1;
					$("#vcode-div").show();
					refreshVCode();
				}
			}
		}
	});
}

$(document).ready(function() {
	var requestURL = "/api/u/isLogin";

	$.ajax({
		"type": "GET",
		"async": false,
		"url": requestURL,
		"success": function(data) {
			var isLogin = true;

			if (data.resultCode) {
				isLogin = false;
				removeLoginCookie();
			} else {
				//已登录过跳转到首页
				location.href = '/';
			}
		}
	});


	var signinFormValidator = $("#loginForm").validate({
		errorElement: "span",
		messages: {
			loginName: {
				required: "请输入手机号或邮箱"
			},
			wpassword: {
				required: "请输入密码"
			},
			vcode: {
				required: "请输入验证码"
			}
		},
		errorPlacement: function(error, element) {
			if (element.is("#loginName")) {
				error.appendTo($("#loginNameMsg"));
			} else if (element.is("#wpassword")) {
				error.appendTo($("#passwdMsg"));
			} else if (element.is("#vcode")) {
				error.appendTo($("#vcodeMsg"));
			}
		}
	});

	//
	loginFailedTimeExceeded = $("#h_loginFailedTimeExceeded").val();
   
	if (loginFailedTimeExceeded == 1) {
		$("#vcode-div").show();
	}

	jQuery.validator.addMethod("isMobilePhoneNumber", function() {
		var mobile = $("#loginName").val();
		var chkResult = isMobile(mobile);

		return chkResult;
	}, "手机号码不正确");

	jQuery.validator.addMethod("isLoginNameValid", function() {
		var loginName = $("#loginName").val();

		if (loginName == null || loginName.trim() == "") {
			return false;
		}

		loginName = loginName.trim();

		if (loginName.length == 11) {
			if (isMobile(loginName) || isEmail(loginName)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (isEmail(loginName)) {
				return true;
			} else {
				return false;
			}
		}
	}, "手机号或邮箱不正确");

	var loginName = $("#h_LoginName").val();

	if (loginName != null && loginName.trim() != "") {
		$("#loginName").val(loginName);
	}

	var failedMsg = $("#h_failedMsg").val();

	if (failedMsg != null && failedMsg.trim() != "") {
		$("#loginMessage").html(failedMsg);
		$("#loginMessage").show();
	}
});

function refreshVCode() {
	var timestamp = Date.parse(new Date());
	var murl = "/api/common/captcha" + "?timestamp=" + timestamp;
	$("#vcode_img").attr("src", murl);
}