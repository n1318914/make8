var domain = "";

function refreshVCode() {
	var timestamp = Date.parse(new Date());
	var murl = domain + "/api/common/captcha" + "?timestamp=" + timestamp;
	$("#vcode_img").attr("src", murl);
}

function checkCode(){
	var code = $("#picCode").val();
	var murl = "/api/common/captcha/check?vCode=" + code;
	$("#picCodeMsg").css("display","none")
	
	if(code.length==4)
	{
		$.ajax({
			type:"get",
			url:murl,
			async:false,
			success:function(data){
				if(data.resultCode==undefined){
					$("#picCodeForm").css("display","none");
					$("#phoneCodeForm").css("display","block");
					if($("#phoneNumber").val()!="")
					{
						document.getElementById("sendVcodeBtn").click();
					}
				}else{
					refreshVCode();
					$("#picCodeMsg").css("display","block")
				}
			}
		});
	}
}

$(document).ready(function() {
	//showFooter(0);

	//init the validator
	jQuery.validator.addMethod("notexistingemail", function() {
		var email = $("#email").val();
		var murl = domain + "/api/u/check/exist?userName=" + email;
		var chkResult = true;

		$.ajax({
			"dataType": 'json',
			"type": "GET",
			"async": false,
			"url": murl,
			"success": function(data) {
				if (data.resultCode) {
					//$("#emailChkResult").val("false");
					chkResult = false;
				}
			}
		});

		return chkResult;
	}, "邮箱已注册");


	jQuery.validator.addMethod("phoneNumExisting", function() {
		var phoneNumber = $("#phoneNumber").val();

		var murl = domain + "/api/u/check/exist?userName=" + phoneNumber;

		var chkResult = true;

		$.ajax({
			"dataType": 'json',
			"type": "GET",
			"async": false,
			"url": murl,
			"success": function(data) {
				if (data.resultCode) {
					chkResult = false;
				}
			}
		});

		return chkResult;
	}, "手机号已注册");

	jQuery.validator.addMethod("isMobilePhoneNumber", function() {

		var mobile = $("#phoneNumber").val();

		var chkResult = isMobile(mobile);

		return chkResult;

	}, "手机号码不正确");


	$("#emailForm").validate({
		errorElement: "span",
		messages: {
			email: {
				required: "请输入邮箱"
			},
			emailPassword: {
				required: "请输入密码"
			},
			vcode: {
				required: "请输入验证码"
			}
		},
		errorPlacement: function(error, element) {
			if (element.is("#publisher") || element.is("#servicer")) {
				error.appendTo($("#userTypeMsg"));
			} else if (element.is("#email")) {
				error.appendTo($("#emailMsg"));
			} else if (element.is("#emailPassword")) {
				error.appendTo($("#passwdMsg"));
			} else if (element.is("#vcode")) {
				error.appendTo($("#vcodeMsg"));
			}
		}
	});

	var validatorPhoneForm = $("#phoneForm").validate({
		errorElement: "span",
		messages: {
			phoneNumber: {
				required: "请输入手机号码"
			},
			phonePasswd: {
				required: "请输入密码"
			},
			phoneCode: {
				required: "请输入验证码"
			}
		},
		errorPlacement: function(error, element) {
			if (element.is("#phonePublisher") || element.is("#phoneServicer")) {
				error.appendTo($("#phoneUserTypeMsg"));
			} else if (element.is("#phoneNumber")) {
				error.appendTo($("#phoneMsg"));
			} else if (element.is("#phonePasswd")) {
				error.appendTo($("#phonePasswdMsg"));
			} else if (element.is("#phoneCode")) {
				error.appendTo($("#phoneCodeMsg"));
			}
		},
		success: function(data) {
			if (wait == VCODE_SEND_IIMEOUT) {
				$("#sendVcodeBtn").attr("disabled", false);
			}
		}
	});

	$("#phoneNumber").bind("input propertychange", function() {
		var inputLen = $("#phoneNumber").val().length;

		$("#sendVcodeBtn").attr("disabled", true);

		if (inputLen == 11) {
			validatorPhoneForm.element("#phoneNumber");
		} else if (inputLen == 0) {
			if (wait == 0) {
				$("#sendVcodeBtn").val("发送");
			}
		}
	});


	//user type
	$("input:radio[name=phoneUserType]").change(function() {
		var userType = $('input[name="phoneUserType"]:checked').val();

		if (userType == 0) {
			$("#phoneUserTypeBG").attr("style", "margin-left:0px;border-top-left-radius: 5px;border-bottom-left-radius: 5px;");
		} else {
			$("#phoneUserTypeBG").attr("style", "margin-left:130px;border-top-left-radius:0px;border-bottom-left-radius:0px;border-top-right-radius:5px;border-bottom-right-radius:5px;");
		}

	});

	$("input:radio[name=userType]").change(function() {
		var userType = $('input[name="userType"]:checked').val();

		if (userType == 0) {
			$("#userTypeBG").attr("style", "margin-left:0px;border-top-left-radius: 5px;border-bottom-left-radius: 5px;");
		} else {
			$("#userTypeBG").attr("style", "margin-left:130px;border-top-left-radius:0px;border-bottom-left-radius:0px;border-top-right-radius:5px;border-bottom-right-radius:5px;");
		}

	});

	//trick to disable the autofill of some browsers,like chrome,FF,360,uc browser
	setTimeout(function() {
		$("#phonePasswd").attr("type", "password");
		$("#emailPassword").attr("type", "password");
	}, 500);

	setTimeout(function() {
		$("#emailPassword").val("");
		$("#email").val("");
	}, 600)
});

function doEmailRegister() {
	var email = $("#email").val();
	var pwd = $("#emailPassword").val();
	var vcode = $("#vcode").val();
	var userType = $('input[name="userType"]:checked').val();

	doRegisterInternal(email, pwd, userType, vcode, "registerMessage", "email");
}

function doPhoneRegister() {
	var phoneNumber = $("#phoneNumber").val();
	var pwd = $("#phonePasswd").val();
	var vcode = $("#phoneCode").val();
	var userType = $('input[name="phoneUserType"]:checked').val();

	doRegisterInternal(phoneNumber, pwd, userType, vcode, "phoneRegisterMessage", "phone");
}

function doRegisterInternal(loginName, password, userType, vcode, errorMsgId, type) {
	var murl = domain + "/api/u/register";
	var userType = 1;
	var paraData = "loginName=" + loginName + "&password=" + password + "&userType=" + userType + "&vcode=" + vcode;

	$.ajax({
		//"dataType" : "json",
		"type": "POST",
		"data": paraData,
		"async": "false",
		"url": murl,
		"success": function(data) {
			if (data.resultCode == 0) {
				location.href = data.msg;
			} else {
				$('#' + errorMsgId).html(data.errorMsg);
				$('#' + errorMsgId).show();

				if (type == "email") {
					refreshVCode();
					$("#vcode").val("");
				} else {
					$("#phoneCode").val("");
				}
			}
		}
	});
}

function resetMessage() {
	$("#emailMessage").html("");
	$("#emailMessage").hide();
}

function sendSMS() {
	var mobile = $("#phoneNumber").val();
	var murl = "/api/common/sendMobileVerifyCode2";
	var paraData = "mobile=" + mobile + "&vcode=" +$("#vcode").val();

	$.ajax({
		"type": "POST",
		"data": paraData,
		"async": false,
		"url": murl,
		"success": function(data) {
			time($("#sendVcodeBtn"));
		}
	});
}

var VCODE_SEND_IIMEOUT = 60;
var wait = VCODE_SEND_IIMEOUT;

function time(btn) {
	if (wait == 0) {

		if ($("#phoneNumber").val() != null && $("#phoneNumber").val().length == 11) {
			btn.val("重新发送");
			btn.attr("disabled", false);
		} else {
			btn.val("发送");
		}

		wait = VCODE_SEND_IIMEOUT;
	} else {
		btn.attr("disabled", true);
		btn.val("重新发送(" + wait + ")");
		wait--;

		setTimeout(function() {
			time(btn);
		}, 1000);
	}
}


function transferTab(el) {
	var id = el.id;

	if (id == "phoneNavTab") {
		$("#lineTab").attr("style", "left:0px");
		$("#phoneNavTab").attr("href", "#phoneTab");
	} else {
		$("#lineTab").attr("style", "left:170px");
		$("#mailNavTab").attr("href", "#emailTab");
	}
}

	$("#changePasswordForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			workinghoursstart: {
				required: "请输入开始日期"
			},
			workinghoursend: {
				required: "请输入结束日期"
			}
		},
		errorPlacement: function(error, element) {
			if (element.is("#oldPassword")) {
				error.appendTo($("#oldPasswordWarning"))
			} else if (element.is("#newPassword")) {
				error.appendTo($("#newPasswordWarning"))
			} else if (element.is("#confirmPassword")) {
				error.appendTo($("#confirmPasswordWarning"))
			} else {
				error.insertAfter(element);
			}
		}
	});