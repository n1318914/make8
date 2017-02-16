var formStep2Validator;
var formStep3Validator;

$(function() {
	checkNext()
})

var nextPass = false;

function checkNext() {
	if (document.getElementById("wbmEp").checked) {
		nextPass = true
	} else {
		nextPass = false
	}
	if (nextPass == false) {
		$("#next").attr("disabled", true)
	} else {
		$("#next").attr("disabled", false)
	}
}

$(document).ready(function() {
	jQuery.validator.addMethod("isMobilePhoneNumber", function() {

		var mobile = $("#loginName").val();

		var chkResult = isMobile(mobile);

		return chkResult;

	}, "手机号码不正确");

	formStep2Validator = $("#publishRequestFormStep2").validate({
		errorElement: "span",
		ignore: [],
		errorPlacement: function(error, element) {
			if ($(element).attr("name") == "Type") {
				error.appendTo($("#projectTypeWarning"));
			}else{
				error.insertAfter(element);
			}
		},
		messages: {
			projectName: {
				required: "请填写您的项目名称"
			},
			projectBudget: {
				required: "请填写您的项目预算"
			},
			projectStartTime: {
				required: "请填写您的项目开始时间"
			},
			Type: {
				required: "请填写您的项目类型"
			},
			projectDesc: {
				required: "请填写您的项目需求"
			}
		}
	});

	formStep3Validator = $("#publishRequestFormStep3").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			loginName: {
				required: "请填写您的手机号"
			},
			password: {
				required: "请填写您的密码"
			}
		}
	});
});

function doPublish() {
	var data = $("#publishRequestFormStep2").serialize();
  console.log(data);

	var typeData = data.split("&")
	var typeArr = [];
	var isNeedBuyDomain = 0;
	var isNeedBuyServerAndDB = 0;

	for(var i = 0;i<typeData.length;i++)
	{
		if(typeData[i].substr(0,5)=="Type=")
		{
			typeArr.push(typeData[i].substr(5))
		}else if(typeData[i].substr(0,11) == "pDomainChb="){
			isNeedBuyDomain = typeData[i].substr(11);
		}else if(typeData[i].substr(0,13) == "pCloudServer="){
      isNeedBuyServerAndDB = typeData[i].substr(13);
		}
	}

	var type = typeArr.join();
	console.log("isNeedBuyDomain=" + isNeedBuyDomain + ",isNeedBuyServerAndDB=" + isNeedBuyServerAndDB);

	var budget = $("#projectBudget").find("option:selected").val();
	var name = $("#projectName").val();
	var period = $("#period").val();
	var content = $("#projectDesc").val().trim();

	var isLoginUser = $("#isLoginUser").val();

	if (isLoginUser == 0) {
		var loginName = $("#loginName").val();
		var loginPassword = $("#password").val();
	} else {
		var loginName = $("#h_loginName").val();
	}

	//var isNeedBuyDomain

  //解决加号被转译成了空格
	content = content.replace(/\+/g,"%2B");
  content = encodeURIComponent(content);
	name = name.replace(/\+/g,"%2B");
	name = encodeURIComponent(name);

	var murl = "/api/selfrun/p/add";
	var paraData = "type=" + type + "&budget=" + budget + "&name=" + name + "&content=" + content +
	               "&period=" + period  + "&startTime=1" + "&isNeedBuyServerAndDB=" + isNeedBuyServerAndDB +
								 "&isNeedBuyDomain=" + isNeedBuyDomain;

	if (isLoginUser == 0) {
		paraData += "&ssoCreator.loginName=" + loginName + "&ssoCreator.password=" + loginPassword;
	} else {
		paraData += "&ssoCreator.loginName=" + loginName;
	}

	//alert(paraData);

	$.ajax({
		"type": "POST",
		"data": paraData,
		"async": "false",
		"url": murl,
		"success": function(data) {
			if (data.resultCode == 0) {
				//showMessageBox(data.msg,"发布需求");
				location.href = data.msg;
			} else {
				showMessageBox(data.errorMsg, "发布需求");
			}
		}
	});
}

function nextTosecond() {
	var isLoginUser = $("#isLoginUser").val();
	var requestbanner = document.querySelector(".request-banner")

	if (isLoginUser == 0) {
		requestbanner.querySelector("p").innerHTML = "Step 2 of 3"
		document.querySelector(".request-bannerBackground div").style.margin = "0px auto";
	} else {
		requestbanner.querySelector("p").innerHTML = "Step 2 of 2"
		document.querySelector(".request-bannerBackground").style.width = "60px";
		document.querySelector(".request-bannerBackground div").style.marginLeft = "30px";
	}
	$("#requestStep3Div").hide();
	$("#requestStep1Div").hide();
	$("#requestStep2Div").show();
}

function nextToThird() {
	var requestbanner = document.querySelector(".request-banner")

	var result = formStep2Validator.form();

	if (result) {
		requestbanner.querySelector("p").innerHTML = "Step 3 of 3"
		document.querySelector(".request-bannerBackground").style.width = "90px";
		document.querySelector(".request-bannerBackground div").style.marginLeft = "60px";
		$("#requestStep1Div").hide();
		$("#requestStep2Div").hide();
		$("#requestStep3Div").show();
	}
}

function previousToSec() {
	var requestbanner = document.querySelector(".request-banner")

	requestbanner.querySelector("p").innerHTML = "Step 2 of 3"
	document.querySelector(".request-bannerBackground").style.width = "90px";
	document.querySelector(".request-bannerBackground div").style.marginLeft = "30px";

	$("#requestStep1Div").hide();
	$("#requestStep3Div").hide();
	$("#requestStep2Div").show();
}

function previousToFirst() {
	var isLoginUser = $("#isLoginUser").val();
	var requestbanner = document.querySelector(".request-banner")
	if (isLoginUser == 0) {
		requestbanner.querySelector("p").innerHTML = "Step 1 of 3"
		document.querySelector(".request-bannerBackground div").style.margin = "0px";
	} else {
		requestbanner.querySelector("p").innerHTML = "Step 1 of 2"
		document.querySelector(".request-bannerBackground").style.width = "60px";
		document.querySelector(".request-bannerBackground div").style.marginLeft = "0px";
	}
	$("#requestStep3Div").hide();
	$("#requestStep2Div").hide();
	$("#requestStep1Div").show();
}

function checkboxActive(str) {
	if (str.querySelector("div").querySelector('img').style.display == "block") {
		str.querySelector("div").querySelector('img').style.display = "none";
	} else {
		str.querySelector("div").querySelector('img').style.display = "block";
	}

}

var is_agree = false;
function iAgree(str){
	if(is_agree==false)
	{
		str.style.background = "#ededed url("+$("#topurl").val()+"img/select.png) no-repeat center";
		is_agree=true;
		document.getElementById("wbmEp").click();
	}else{
		str.style.background = "#ededed";
		document.getElementById("wbmEp").click();
		is_agree=false;
	}

}

//验证发布需求第三步电话号码是否已注册
function isJoin(str){
	if(str.value.length==11){
	}
}
