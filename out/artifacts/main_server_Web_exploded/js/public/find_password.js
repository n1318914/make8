var domain = "";

function refreshVCode() {
	var timestamp = Date.parse(new Date());
	var murl = domain + "/api/common/captcha" + "?timestamp=" + timestamp;
	$("#vcode_img").attr("src", murl);
}

$(document).ready(function() {
	$("#findPasswordForm").validate({
		errorElement: "span",
		messages: {
			loginName: {
				required: "请输入邮箱或手机号码"
			},
			vcode: {
				required: "请输入验证码"
			}
		},
		errorPlacement: function(error, element) {
			if (element.is("#loginName")) {
				error.appendTo($("#loginNameMsg"));
			}
			if (element.is("#vcode")) {
				error.appendTo($("#vcodeMsg"));
			}
		}
	});


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

	jQuery.validator.addMethod("loginNameExisting", function() {
		var loginName = $("#loginName").val();
		var murl = domain + "/api/u/check/exist?userName=" + loginName;

		var chkResult = false;

		$.ajax({
			"dataType": 'json',
			"type": "GET",
			"async": false,
			"url": murl,
			"success": function(data) {
				if (data.resultCode) {
					chkResult = true;
				}
			}
		});

		return chkResult;
	}, "用户不存在");
});

function doFindPassword() {
	var loginName = $("#loginName").val();
	var vcode = $("#vcode").val();

	var murl = domain + "/api/u/sendResetPwd";
	var paraData = "loginName=" + loginName + "&vcode=" + vcode;

	$.ajax({
		//"dataType" : "json",
		"type": "POST",
		"data": paraData,
		"async": "false",
		"url": murl,
		"success": function(data) {
			if (data.resultCode == 1) {
				$("#findPasswordMsg").html(data.errorMsg);
				$("#findPasswordMsg").show();
				refreshVCode();
				$("#vcode").val("");
			} else {
				$("#registerMessage").hide();
				window.location.href = data.msg;
			}
		}
	});
}