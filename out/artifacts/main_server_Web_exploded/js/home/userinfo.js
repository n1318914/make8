//ctx路径
var topurl = $("#topurl").val();

var VCODE_SEND_IIMEOUT = 60;


$(document).ready(function() {

	//initialize the web pages
	//initTab();

	initUserBaseInfo();

	var isIdentifyModify = $("#isIdentifyModify").val();

	//alert(isIdentifyModify);

	//make the dialog draggable
	$('.modal-dialog').draggable({
		handle: ".modal-header"
	});


	if (isIdentifyModify == 1) {
		initUserAuthenInfo();
	}

	jQuery.validator.addMethod("isAlipayAccount",function(value,element,param){
		var account = value;
		return isEmail(account) || isMobile(account);
	},"请输入正确的支付账号");

	jQuery.validator.addMethod("isMobilePhoneNumber", function() {

		if (!isPhoneNumChanged()) {
			return true;
		}

		var mobile = $("#phoneNumber").val();

		chkResult = isMobile(mobile);

		//
		opsWhenPhoneNumInCheck(chkResult);
		return chkResult;

	}, "手机号码不正确");

	//认证信息非空认证
	jQuery.validator.addMethod("timeRangeCheck", function() {
		var startElId = "workinghoursstart";
		var endElId = "workinghoursend";

		var endELVal = $("#" + endElId).val();

        if(endELVal == ""){
        	return true;
        }else{
            return checkTime(startElId, endElId);
        }

	});

	jQuery.validator.addMethod("companyTimeRangeCheck", function() {
		var companyStartElId = "companyworkinghoursstart";
		var companyEndElId = "companyworkinghoursend";

		return checkTime(companyStartElId, companyEndElId);
	});
	$("#atheninfoForm").validate({
		rules:{
			payAccount:{
				isAlipayAccount:true,
				required:true
			},
			payAccount2:{
				isAlipayAccount:true,
				required:true
			}
		},
		errorElement:"span",
		messages:{
			payAccount:{
				isAlipayAccount:"请输入正确的支付账号",
				required:"支付账号不能为空"
			},
			payAccount2:{
				isAlipayAccount:"请输入正确的支付账号",
				required:"支付账号不能为空"
			}
		},
		errorPlacement:function(error,element){
			if(element.is("#payAccount")){
				$("#payAccountErr").append(error);
			}else if(element.is("#payAccount2")){
				$("#payAccountErr2").append(error);
			}
		}
	});


	$("#changePasswordForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			workinghoursstart: {
				required: "请输入开始日期"
			},
			confirmPassword: {
				equalTo: "两次输入的新密码不一致"
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

	$("#companyworkCheckForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			companyworkinghoursstart: {
				required: "请输入开始日期"
			},
			companyworkinghoursend: {
				required: "请输入结束日期",
				companyTimeRangeCheck: "开始时间必须小于结束时间"
			}
		},
		errorPlacement: function(error, element) {
			if (element.is("#companyPName")) {
				error.appendTo($("#companyPNameMsg"))
			} else if (element.is("#companyworkinghoursstart")) {
				error.appendTo($("#companyworkinghoursstartMsg"))
			} else if (element.is("#companyworkinghoursend")) {
				error.appendTo($("#companyworkinghoursendMsg"))
			} else if (element.is("#companyworkingcontent")) {
				error.appendTo($("#companyworkingcontentMsg"))
			} else if (element.is("#companyWorkingUrl")) {
				error.appendTo($("#companyWorkingUrlMsg"))
			} else {
				error.insertAfter(element);
			}
		}
	});

	$("#workCheckForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			workinghoursstart: {
				required: "请输入开始日期"
			},
			workinghoursend: {
				required: "请输入结束日期",
				timeRangeCheck: "开始时间必须小于结束时间"
			}
		},
		errorPlacement: function(error, element) {
			if (element.is("#workinghoursstart")) {
				error.appendTo($("#workinghoursstartMsg"));
			} else if (element.is("#workinghoursend")) {
				error.appendTo($("#workinghoursendMsg"));
			} else if (element.is("#company")) {
				error.appendTo($("#companyMsg"))
			} else if (element.is("#position")) {
				error.appendTo($("#positionMsg"))
			} else if (element.is("#workingcontent")) {
				error.appendTo($("#workingcontentMsg"))
			} else if (element.is("#projectname")) {
				error.appendTo($("#projectnameMsg"))
			} else if (element.is("#projecturl")) {
				error.appendTo($("#projecturlMsg"))
			} else if (element.is("#projectintro")) {
				error.appendTo($("#projectintroMsg"))
			} else {
				error.insertAfter(element);
			}
		}
	});

	$("#pojectCheckForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			workinghoursstart: {
				required: "请输入开始日期"
			},
			workinghoursend: {
				required: "请输入结束日期",
				timeRangeCheck: "开始时间必须小于结束时间"
			}
		},
		errorPlacement: function(error, element) {
			if (element.is("#projectname")) {
				error.appendTo($("#projectnameMsg"))
			} else if (element.is("#projecturl")) {
				error.appendTo($("#projecturlMsg"))
			} else if (element.is("#projectintro")) {
				error.appendTo($("#projectintroMsg"))
			} else {
				error.insertAfter(element);
			}
		}
	});

	$("#EduCheckForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			workinghoursstart: {
				required: "请输入开始日期"
			},
			workinghoursend: {
				required: "请输入结束日期",
				timeRangeCheck: "开始时间必须小于结束时间"
			}
		},
		errorPlacement: function(error, element) {
			if (element.is("#schoolname")) {
				error.appendTo($("#schoolnameMsg"))
			} else if (element.is("#major")) {
				error.appendTo($("#majorMsg"))
			} else {
				error.insertAfter(element);
			}
		}
	});

	function checkTime(startElId, endElId) {
		var startEl = $("#" + startElId);
		var endEl = $("#" + endElId);

		var startTimeVal = startEl.val();
		var endTimeVal = endEl.val();

		if (startTimeVal == null || endTimeVal == null) {
			return false;
		}

		var startTime = (new Date(startEl.val())).getTime();
		var endTime = (new Date(endEl.val())).getTime();

		if (startTime > endTime) {
			return false;
		} else {
			return true;
		}
	}
	//认证信息非空认证结束

	jQuery.validator.addMethod("validUserName", function() {
		var userName = $("#userName").val();

		var mURL = "/api/1/u/exist/nickname";
		var paraData = "userName=" + userName;
		var checkResult = true;

		//密码修改验证
		$("#changePasswordForm").validate({
			errorElement: "span"
		});

		$.ajax({
			type: "POST",
			url: mURL,
			data: paraData,
			async: false,
			success: function(data) {
				if (data.resultCode) {
					checkResult = false;
				}
			}
		});

		return checkResult;
	}, "昵称已存在或包含敏感词汇");

	jQuery.validator.addMethod("phoneNumExisting", function() {
		if (!isPhoneNumChanged()) {
			return true;
		}

		var checkResult = true;

		var mobile = $("#phoneNumber").val();

		var mURL = "/api/1/u/exist/mobile";

		var paraData = "mobile=" + mobile;


		$.ajax({
			type: "get",
			url: mURL,
			data: paraData,
			async: false,
			success: function(data) {
				if (data.resultCode) {
					checkResult = false;
				}
			}
		});

		opsWhenPhoneNumInCheck(checkResult);

		return checkResult;
	}, "手机号码已存在");

	var validatorForm = $("#userInfoForm").validate({
		errorElement: "span",
		success: function(label) {
			var id = $(label).prev().attr("id");

			if (id == "phoneNumber") {
				opsAfterPhoneNumCheck();
			}

			if (id == "email") {
				//alert("I am checking email!");
				if (!$("#email").attr("readonly")) {
					if (wait == VCODE_SEND_IIMEOUT) {
						$("#emailValidateBtn").attr("disabled", false);
					}

					$("#emailVCodeDiv").show();
				}
			}
		}
	});

	//时间选择
	generateDatePickerWithoutStartDate(".workinghoursstart", "workinghoursstartDiv","yyyy-mm",3,3);
	generateDatePickerWithoutStartDate(".workinghoursend", "workinghoursendDiv","yyyy-mm",3,3);
	generateDatePickerWithoutStartDate(".companyworkinghoursstart", "companyworkinghoursstartDiv","yyyy-mm",3,3);
	generateDatePickerWithoutStartDate(".companyworkinghoursend", "companyworkinghoursendDiv","yyyy-mm",3,3);

	jQuery.validator.addMethod("isIdCardNo", function() {
		var idCardNum = $("#idCardNum").val();
		return isIdCardNo(idCardNum);
	}, "身份证号码不正确");

	jQuery.validator.addMethod("isPhoneNumber", function() {
		var compPhoneNum = $("#compPhoneNum").val();

		return isPhoneNum(compPhoneNum);
	}, "电话号码不正确");

	jQuery.validator.addMethod("notexistingemail", function() {
		var email = $("#email").val();
		var murl = "/api/1/u/exist/email?email=" + email;

		var chkResult = true;

		$.ajax({
			"dataType": 'json',
			"type": "GET",
			"async": false,
			"url": murl,
			"success": function(data) {
				if (data.resultCode) {
					$("#emailValidateBtn").attr("disabled", true);
					$("#emailVCodeDiv").hide();
					chkResult = false;
				}
			}
		});

		return chkResult;
	}, "邮箱已存在");

	$("#phoneNumber").bind("input propertychange", function() {
		var inputLen = $("#phoneNumber").val().length;

		if (inputLen == 11) {
			validatorForm.element("#phoneNumber");
		} else if (inputLen == 0) {
			opsInPhoneNumberisEmpty();
		}

	});

	if (!$("#email").attr("readonly")) {
		$("#email").bind("input propertychange", function() {
			var email = $("#email").val().trim();

			if (!isEmail(email)) {
				$("#email").removeAttr("notexistingemail");
				$("#emailValidateBtn").attr("disabled", true);
				$("#emailVCodeDiv").hide();
			} else {
				$("#email").attr("notexistingemail", "true");
			}

			validatorForm.element("#email");
		});
	}

   //判断是否显示可以参与的项目
    var showAvaliableJoinProjectFinal = $("#showAvaliableJoinProjectFinal").val();

    if(showAvaliableJoinProjectFinal == 1){
    	showTabContentById("canJoinTheProject");
    }else{
    	$("#canJoinTheProjectItem").html('<div style="font-size:14px;padding-left:20px;margin-top:50px;"><i class="fa fa-volume-up">' +
    	                                 '</i><p style="color:red;display:inline">&nbsp;目前还没有和您匹配的项目，如有匹配的项目，我们会第一时间通知您，请您耐心等待！</p></div>');
    }

});

var userInfoNum = 0;
var userinfoId = "";
function userInfo(){
//	resumeStep2()
	if(userInfoNum==0)
	{
		userinfoId = $("#userinfoId").html()
	}else{
		return userinfoId
	}
	return userinfoId;
}

//简历上传步骤判断
var isresume = $("#isresume").val();
function uploadResume(str){
	var resumeNo = document.getElementById("resume-no");
	var resumeYes = document.getElementById("resume-yes");
	//点击前移除全部css再添加选中项样式
	resumeNo.classList.remove("resume-active");
	resumeYes.classList.remove("resume-active");
	str.classList.add("resume-active");
	//选中项图片切换
	var picUrl = /(.*)\img/;
	var pic = str.querySelector("img").src;
	var picStr = pic.match(picUrl)[1];
	resumeNo.querySelector("img").src=picStr+"img/resume-no.png";
	resumeYes.querySelector("img").src=picStr+"img/resume-no.png";
	str.querySelector("img").src=picStr+"img/resume-yes.png";
	if(resumeYes.classList.contains("resume-active")==true)
	{
		isresume = "yes";
	}else{
		isresume = "no";
		//清除所有警告信息
		$("#resumeWaring").html("");
		$("#fileUploadMsg").html("");
		$("#fileUploadErrMsg").html("");
	}
}

function resumeBack(){
	$("#is-resume").css("display","none");
	$("#userTypeChoosenDiv").show();
	scroll(0, 0)
}

function resumeFocus(){
	$("#resumeWaring").html("");
}

function resumeNext(){
	if(isresume=="yes")
	{
		var isIdentifyModify = $("#isIdentifyModify").val();
		var resumeType = $("#h_resumeType").val();
		if(isIdentifyModify == 1 && resumeType == "file")
		{
			  reqAttachementUploadResult = true;
		}

		if(reqAttachementUploadResult == false&&$("#resume-url").val()=="")
		{
			$("#resumeWaring").html("请上传您的简历或者在下面填写您简历的链接!");
		}else{
			if(reqAttachementUploadResult == true||$("#resume-url").val()!="")
			{
				$("#is-resume").css("display","none");
				$("#servicerPersonalDiv").show();
			}
		}
	}else{
		$("#is-resume").hide()
		$("#personalSecond").show();
	}
}

function resumeStep2(){
	if(isresume=="yes")
	{
		var isIdentifyModify = $("#isIdentifyModify").val();
		var resumeType = $("#h_resumeType").val();
		if(isIdentifyModify == 1 && resumeType == "file")
		{
			  reqAttachementUploadResult = true;
		}

		if(reqAttachementUploadResult == false&&$("#resume-url").val()=="")
		{
			$("#resumeWaring").html("请上传您的简历或者在下面填写您简历的链接!");
		}else{
			if(reqAttachementUploadResult == true||$("#resume-url").val()!="")
			{
				$("#is-resume").css("display","none");
				$("#servicerPersonalDiv").show();
				userInfoNum=1;
			}
		}
	}else{
		$("#is-resume").hide()
		$("#personalSecond").show();
		userInfoNum=1;
	}
}

function initTab() {
//	var operation = $("#showIdentifyInfo").val();
//	var isComplete = $("#isCompleteFlag").val();
//  var showAvailableJoinPorject = $("#h_showAvaliableJoinProject").val();
//	var isIdentifyModify = $("#isIdentifyModify").val();

    var showAvaliableJoinProjectFinal = $("#showAvaliableJoinProjectFinal").val();
    if(showAvaliableJoinProjectFinal == 1){
    	showTabContentById("canJoinTheProject");
    }else{
    	$("#canJoinTheProjectItem").html('<div style="font-size:14px;padding-left:20px;margin-top:50px;"><i class="fa fa-volume-up">' +
    	                                 '</i><p style="color:red;display:inline">&nbsp;目前还没有和您匹配的项目，如有匹配的项目，我们会第一时间通知您，请您耐心等待！</p></div>');
    }

//	if (isComplete == 1) {
//		if (operation == 1 || isIdentifyModify == 1) {
//			showTabContentById("navAuthInfo");
//		}
//	}
}

var CODEIDENTIFIEDTYPE = 0;

function initUserBaseInfo() {
	var email = $("#baseInfo-email").val();
	var nickName = $("#baseInfo-name").val();
	var mPhoneNum = $("#baseInfo-mobile").val();
	var provinceId = $("#baseInfo-provinceId").val();
	var cityId = $("#baseInfo-regionId").val();
	var account = $("#baseInfo-bankAccount").val();
	var userType = $("#baseInfo-userType").val();
	var qq = $("#baseInfo-qq").val();
	var weixin = $("#baseInfo-weixin").val();
	//alert(nickName);


	if (email != null && email.trim() != "") {
		$("#email").val(email);
		$("#email").attr("readonly", "true");
		$("#emailActivationDiv").hide();
		$("#emailVCodeVerfiedDiv").show();
	} else {
		$("#emailActivationDiv").show();
		$("#emailVCodeVerfiedDiv").hide();
		CODEIDENTIFIEDTYPE = 1;
	}

	if (nickName != null && nickName.trim() != "") {
		$("#userName").val(nickName);
		$("#userName").attr("readonly", "true");
	} else {
		$("#userName").attr("validUserName", "true");
		$("#userNameTip").show();
	}

	if (mPhoneNum != null && mPhoneNum.trim() != "") {
		$("#phoneNumber").val(mPhoneNum);
		$("#phoneNumber").attr("readonly", "true");

	}

	//initialize the common field

	$("#qqNumber").val(qq)
	$("#weixinNumber").val(weixin)

	$("#userType").val(userType);

	$("#phoneNumberOrg").val(mPhoneNum);
	$("#account").val(account);

	opsBeforePhoneNumCheck(mPhoneNum);

	initDistrictCity(provinceId, cityId);

	if (userType == 0) {
		hiddenFiledsWhenUserIsPublisher();
	}
}

function initUserAuthenInfo() {
	$("#userTypeRepDiv").show();
	$("#userTypeChoosenDiv").hide();

	var category = $("#h_category").val();

	if (category == 0) {
		refreshServiceTypeInternal("personalBtn");

		$("#realName").val($("#h_realName").val());
		$("#idCardNum").val($("#h_idCardNum").val());
		$("#userTypeBadge").html("个人");
	} else if (category == 1) {
		refreshServiceTypeInternal("companyBtn");
		$("#userTypeBadge").html("企业");

		$("#compName").val($("#h_compName").val());
		$("#compAddr").val($("#h_compAddr").val());
		$("#repName").val($("#h_repName").val());
		$("#compPhoneNum").val($("#h_compPhoneNum").val());
		$("#orgNum").val($("#h_orgNum").val());
		$("#taxNum").val($("#h_taxNum").val());
		$("#blNum").val($("#h_blNum").val());

	}
}


function hiddenFiledsWhenUserIsPublisher() {
	$("#introductiongroup").hide();
	$("#skillgroup").hide();
	$("#accountgroup").hide();
	$("#authentab").hide();
}

function opsWhenPhoneNumInCheck(chkResult) {
	$("#vcodeVerfiedDiv").hide();
	$("#vcodeSendDiv").show();

	if (!chkResult) {
		$("#sendVcodeBtn").attr("disabled", true);
		$("#vcodeinputdiv").hide();
	} else {
		$("#sendVcodeBtn").attr("disabled", false);
		$("#vcodeinputdiv").show();
	}
}

function opsBeforePhoneNumCheck(phoneNum) {
	if (phoneNum != "") {
		$("#vcodeVerfiedDiv").show();
	} else {
		$("#vcodeSendDiv").show();
		$("#sendVcodeBtn").attr("disabled", true);
	}
}

function opsAfterPhoneNumCheck() {
	if (!isPhoneNumChanged()) {
		$("#vcodeSendDiv").hide();
		$("#vcodeinputdiv").hide();
		$("#vcodeVerfiedDiv").show();
	} else {
		$("#sendVcodeBtn").attr("disabled", false);
		$("#vcodeinputdiv").show();
	}
}

function opsInPhoneNumberisEmpty() {
	$("#vcodeinputdiv").hide();
	$("#sendVcodeBtn").attr("disabled", true);
}

function isPhoneNumChanged() {
	var phoneNumberOrg = $("#phoneNumberOrg").val().trim();
	var phoneNumber = $("#phoneNumber").val().trim();

	if (phoneNumberOrg == phoneNumber) {
		return false;
	} else {
		return true;
	}
}

function initSkillSelect() {
	$('#skill').selectator({
		showAllOptionsOnFocus: true,
		keepOpen: true
	});
}

var wait = VCODE_SEND_IIMEOUT;

function time(btn) {
	if (wait == 0) {
		btn.attr("disabled", false);
		btn.val("重新发送");
		wait = VCODE_SEND_IIMEOUT;
	} else {
		btn.attr("disabled", true);
		btn.val("重新发送(" + wait + ")");
		wait--;
		setTimeout(function() {
				time(btn);
			},
			1000)
	}
}

function doSave() {
	var email = $("#email").val();
	var name = $("#userName").val();
	var mobile = $("#phoneNumber").val();
	var qq = $("#qqNumber").val();
	var weixin = $("#weixinNumber").val()

	var vcode;

	if (CODEIDENTIFIEDTYPE == 1) {
		vcode = $("#emailVCode").val();
	} else {
		vcode = $("#vcode").val();
	}
	var regionID = $("#city option:selected").val();

	var murl = "/api/1/u/info/modify";
	var paraData = "email=" + email + "&name=" + name + "&mobile=" + mobile + "&vcode=" + vcode +
		"&regionId=" + regionID + "&qq=" + qq + "&weixin=" + weixin;

	$.ajax({
		//"dataType" : "json",
		"type": "POST",
		"data": paraData,
		"async": false,
		"url": murl,
		"success": function(data) {
			if (data.resultCode == 0) {
				setLoginUserName(name);
				showMessageBox("信息保存成功！", "完善资料", data.msg);
			} else {
				showMessageBox("信息保存失败，" + data.errorMsg, "完善资料");
			}
		}
	});
}

function sendSMS() {
	var mobile = $("#phoneNumber").val();
	var murl = "/api/common/sendMobileVerifyCode";
	var paraData = "mobile=" + mobile;

	$.ajax({
		"type": "POST",
		"data": paraData,
		"async": false,
		"url": murl,
		"success": function(data) {
			sendBtn = $("#sendVcodeBtn");
			time(sendBtn);
		}
	});
}

function initDistrictCity(pid, cid) {
	if (pid == 0) {
		pid = 1;
		cid = 1;
	}

	requestURL = "/api/region/list";

	$.ajax({
		type: "GET",
		url: requestURL,
		async: false,
		success: function(data) {
			if (data != null) {
				$("#province").html("");

				for (i = 0; i < data.length; i++) {
					optionHTML = '<option value="' + data[i].id + '" ';

					if (pid != null && data[i].id == pid) {
						optionHTML += 'selected';
					}

					optionHTML += '>' + data[i].name + '</option>';

					$("#province").append(optionHTML);
				}

				retriveCitiesInternal(pid, cid);
			}
		}
	});
}

function retriveCities() {
	//
	var pOption = $("#province option:selected");
	var pId = pOption.val();

	retriveCitiesInternal(pId);
}

function retriveCitiesInternal(pId, cId) {

	if (pId != null) {
		var requestURL = "/api/region/citys";
		var paraData = "id=" + pId;

		$.ajax({
			type: "get",
			url: requestURL,
			data: paraData,
			async: true,
			success: function(data) {
				if (data != null) {
					$("#city").html("");

					for (i = 0; i < data.length; i++) {
						optionHTML = '<option value="' + data[i].id + '" ';

						if (cId != null && cId == data[i].id) {
							optionHTML += ' selected';
						}

						optionHTML += '>' + data[i].name + '</option>';
						$("#city").append(optionHTML);
					}
				}
			}
		});
	}
}

var uType = $("#h_category").val();
var orgElId = "";

if(uType == 1){
	orgElId="companyBtn";
}else{
	orgElId="personalBtn";
}


function checkServiceType(el) {

	refreshServiceTypeInternal(el.id);
}

function refreshServiceTypeInternal(id) {
	if (id == orgElId) {
		return;
	}

	var eid = "#" + id;

	$(eid).removeClass("unact");
	$(eid).addClass("act");
	if ($(eid + "img").attr("src") == topurl + "img/user.png") {
		$(eid + "img").attr("src", topurl + "img/user_active.png")
	} else {
		$(eid + "img").attr("src", topurl + "img/we_active.png")
	}
	$("#" + orgElId).removeClass("act");
	$("#" + orgElId).addClass("unact");
	if ($("#" + orgElId + "img").attr("src") == topurl + "img/we_active.png") {
		$("#" + orgElId + "img").attr("src", topurl + "img/we.png")
	}
	if ($("#" + orgElId + "img").attr("src") == topurl + "img/user_active.png") {
		$("#" + orgElId + "img").attr("src", topurl + "img/user.png")
	}
	orgElId = id;
}

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
function nextTosecond() {
	if (orgElId == "personalBtn") {
		$("#userTypeChoosenDiv").hide();
//		$("#personalSecond").show();
		$("#is-resume").show()
	} else if (orgElId == "companyBtn") {
		$("#userTypeChoosenDiv").hide();
		$("#companySecond").show();
	}
}

function personalToSecondHtml2(){
	$("#personalSecond").hide();
	userInfoNum=0;
	$("#is-resume").show();
	scroll(0, 0)
}

function personalToSecond(){
	$("#personalSecond").hide();
	$("#atheninfoForm h3").html(userInfo());
	userInfoNum=0;
	$("#is-resume").show();
	scroll(0, 0)
}

function Laststep() {
	$("#personalSecond").hide();
	$("#userTypeChoosenDiv").show();
	scroll(0, 0)
}

function clearCheckNotNull(str) {
	var divStr = document.getElementById(str)
	divStr.querySelector("span:last-of-type").style.display = "none";
	w = 0;
	worktypeNum = 0;
	technologyNum = 0;
	theNumberOfNum = 0;
	companyworktypeNum = 0;
	companytechnologyNum = 0;
	companyProductTypeNum = 0;
}

function checkNotNull(divStr, tf) {
	var div = document.getElementById(divStr)
	div.querySelector("span:last-of-type").style.display = "block";
	div.scrollIntoView(tf);
	return false;
}

var w = 0;
var worktypeNum = 0;
var technologyNum = 0;

function StatusNextTothird() {
	var workstate = document.getElementsByName("freelanceType");
	var worktype = document.getElementsByName("cando");
	var technology = document.getElementsByName("mainAbility");

	var addEduContentLength = document.querySelectorAll("#addEduContent>div").length;
	var addProjectContentLength = document.querySelectorAll("#addProjectContent>div").length;

	for (var i = 0; i < workstate.length; i++) {
		if (workstate[i].checked) {
			w = 1
		}
	}

	for (var j = 0; j < worktype.length; j++) {
		if (worktype[j].checked) {
			worktypeNum = 1;
		}
	}

	for (var k = 0; k < technology.length; k++) {
		if (technology[k].checked) {
			technologyNum = 1;
		}
	}

	if (w == 1 && worktypeNum == 1 && (technologyNum == 1 || document.getElementById("ActionScript").value != "") && addEduContentLength > 0) {
		$("#personalSecond").hide();
		$("#servicerPersonalDiv").show();
	} else if (w == 0) {
		checkNotNull("workStatus", false);
	} else if (worktypeNum == 0) {
		checkNotNull("canWorkType", false);
	} else if (technologyNum == 0 && document.getElementById("ActionScript").value == "") {
		checkNotNull("ExcelsTechnologies", false);
	} else if (addProjectContentLength == 0) {
		checkNotNull("addProjectContent", true);
	} else if (addEduContentLength == 0) {
		checkNotNull("addEduContent", true);
	}
}

function nextTothird() {
	var workstate = document.getElementsByName("freelanceType");
	var worktype = document.getElementsByName("cando");
	var technology = document.getElementsByName("mainAbility");

	var addEduContentLength = document.querySelectorAll("#addEduContent>div").length;
	var addProjectContentLength = document.querySelectorAll("#addProjectContent>div").length;

	for (var i = 0; i < workstate.length; i++) {
		if (workstate[i].checked) {
			w = 1
		}
	}

	for (var j = 0; j < worktype.length; j++) {
		if (worktype[j].checked) {
			worktypeNum = 1;
		}
	}

	for (var k = 0; k < technology.length; k++) {
		if (technology[k].checked) {
			technologyNum = 1;
		}
	}

	if (w == 1 && worktypeNum == 1 && (technologyNum == 1 || document.getElementById("ActionScript").value != "") &&
	    addEduContentLength > 0 && addProjectContentLength > 0) {
		$("#personalSecond").hide();
		$("#servicerPersonalDiv").show();
		// 调用保存接口
	    nextSave("personal");
		scroll(0, 0);
	} else if (w == 0) {
		checkNotNull("workStatus", false);
	} else if (worktypeNum == 0) {
		checkNotNull("canWorkType", false);
	} else if (technologyNum == 0 && document.getElementById("ActionScript").value == "") {
		checkNotNull("ExcelsTechnologies", false);
	} else if (addProjectContentLength == 0) {
		checkNotNull("addProjectContent", true);
	} else if (addEduContentLength == 0) {
		checkNotNull("addEduContent", true);
	}
}

function nextSave(opType) {
	var mURL = "/api/1/u/next/save";

	var data = $("#atheninfoForm").serialize();

	// 教育背景
	var EduBackground = "";
	//工作经验
	var workExperience = "";
	//项目作品
	var project = "";
	var ajStr = ""; //aj过去的数据
	var freelanceType = "";
	var candoStr = "";
	var mainAbilityStr = "";
	var cando = [];
	var mainAbility = [];
	var otherMainAbility = "";
	var arr = data.split("&");

	//企业部分
	var addCPE = "";
	var iranking = "";
	var companyAjStr = "";
	var companySize = "";
	//工作类型
	var companyworktype = [];
	var CWT = "";
	//擅长
	var companytechnology = [];
	var CT = "";
	var otherCaseType = "";
	//产品类型
	var productType = [];
	var PT = "";
	var otherProductType = "";
	var companyintro = "";
	var identifyStep = 1;


	if(opType != "company"){
		if(isresume == "yes"){
			freelanceType = "freelanceType=0";

		}else{
			freelanceType = "freelanceType=" + arr[0].substr(14);
		}

		if ($("#ActionScript").val() != "") {
		otherMainAbility = "&otherAbility=" + $("#ActionScript").val();
		} else {
			otherMainAbility = "&otherAbility=";
		}

		for (var z = 0; z < arr.length; z++) {
			if (arr[z].substr(0, 12) == "mainAbility=") {
				mainAbility.push(arr[z].substr(12))
			}
		}
		mainAbilityStr = "&mainAbility=" + mainAbility.join()

		for (var j = 0; j < arr.length; j++) {
			if (arr[j].substr(0, 6) == "cando=") {
				cando.push(arr[j].substr(6))
			}
		}
		candoStr = "&cando=" + cando.join()
	}else {
		for (var e = 0; e < arr.length; e++) {
			if (arr[e].substr(0, 16) == "companyworktype=") {
				companyworktype.push(arr[e].substr(16))
			}
		}
		candoStr = "&cando=" + companyworktype.join()

		if ($("#companytechnology").val() != "") {
			otherCaseType = "&otherAbility=" + $("#companytechnology").val()
		} else {
			otherCaseType = "&otherAbility=";
		}

		for (var r = 0; r < arr.length; r++) {
			if (arr[r].substr(0, 18) == "companytechnology=") {
				companytechnology.push(arr[r].substr(18))
			}
		}

		mainAbilityStr = "&mainAbility=" + companytechnology.join()
	}

	//工作经验
	for (var x = 0; x < $$("#addWorkContent>div").length; x++) {
		workExperience += "&employeeJobExperience[" + x + "].companyName=" + $$("#addWorkContent>div")[x].querySelector("div:first-of-type").lastChild.textContent + "&employeeJobExperience[" + x + "].office=" + $$("#addWorkContent>div")[x].querySelector("div:nth-of-type(4)").lastChild.textContent + "&employeeJobExperience[" + x + "].startTime=" + ($$("#addWorkContent>div")[x].querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[0] + "&employeeJobExperience[" + x + "].endTime=" + ($$("#addWorkContent>div")[x].querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[1] + "&employeeJobExperience[" + x + "].description=" + $$("#addWorkContent>div")[x].querySelector("div:last-of-type").lastChild.textContent;

	}

	//项目
	for (var y = 0; y < $$("#addProjectContent>div").length; y++) {
		project += "&employeeProduct[" + y + "].title=" + $$("#addProjectContent>div")[y].querySelector("div:first-of-type").lastChild.textContent + "&employeeProduct[" + y + "].description=" + $$("#addProjectContent>div")[y].querySelector("div:last-of-type").lastChild.textContent + "&employeeProduct[" + y + "].link=" + $$("#addProjectContent>div")[y].querySelector("div:nth-of-type(3)").lastChild.textContent
	}

	//教育背景
	for (var i = 0; i < $$("#addEduContent>div").length; i++) {
		EduBackground += "&employeeEduExperience[" + i + "].schoolName=" + $$("#addEduContent>div")[i].querySelector("div:first-of-type").lastChild.textContent + "&employeeEduExperience[" + i + "].discipline=" + $$("#addEduContent>div")[i].querySelector("div:nth-of-type(3)").lastChild.textContent + "&employeeEduExperience[" + i + "].eduBackgroud=" + $$("#addEduContent>div")[i].querySelector("div:nth-of-type(4)").lastChild.textContent + "&employeeEduExperience[" + i + "].graduationTime=" + $$("#addEduContent>div")[i].querySelector("div:last-of-type").lastChild.textContent
	}

	for (var w = 0; w < arr.length; w++) {
		if (arr[w].substr(0, 12) == "theNumberOf=") {
			companySize = "&companySize=" + arr[w].substr(12)
		}
	}

	for (var t = 0; t < arr.length; t++) {
		if (arr[t].substr(0, 19) == "companyProductType=") {
			productType.push(arr[t].substr(19))
		}
	}

	PT = "&caseType=" + productType.join()

	for (var q = 0; q < $$("#addCPE>div").length; q++) {
		addCPE += "&employeeProjectExperience[" + q + "].projectName=" + $$("#addCPE>div")[q].querySelector("div:first-of-type").lastChild.textContent + "&employeeProjectExperience[" + q + "].startTime=" + ($$("#addCPE>div")[q].querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[0] + "&employeeProjectExperience[" + q + "].endTime=" + ($$("#addCPE>div")[q].querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[1] + "&employeeProjectExperience[" + q + "].description=" + $$("#addCPE>div")[q].querySelector("div:nth-of-type(4)").lastChild.textContent + "&employeeProjectExperience[" + q + "].link=" + $$("#addCPE>div")[q].querySelector("div:last-of-type").lastChild.textContent
	}
	for (var rq = 0; rq < q; rq++) {
		iranking += "&employeeProjectExperience[" + rq + "].ranking=" + rq
	}

	if ($("#companyProductType").val() != "") {
		otherProductType = "&otherCaseType=" + $("#companyProductType").val();
	} else {
		otherProductType = "&otherCaseType=";
	}

	companyintro = "&introduction=" + $("#addCIntro").val();

	companyAjStr = companySize + PT + otherProductType + companyintro + addCPE + iranking;
	ajStr = freelanceType + candoStr + mainAbilityStr + otherMainAbility + workExperience + project + EduBackground + companyAjStr;

    if (orgElId == "personalBtn") {
    	ajStr += "&identifyInfo.category=0";
    }else{
    	ajStr += "&identifyInfo.category=1";
    }

	//记录当前的位置
	if(opType != "company"){
		identifyStep = 3;
	}else{
		identifyStep = 2;
	}

	ajStr += "&identifyStep=" + identifyStep;
	//alert(ajStr)

	$.ajax({
		dataType: "json",
		type: "POST",
		async: false,
		data: ajStr,
		url: mURL,
		success: function(data) {
			//
		}
	});
}

function LastCompany() {
	$("#companySecond").hide();
	$("#userTypeChoosenDiv").show();
	scroll(0, 0)
}

//企业信息非空验证
var theNumberOfNum = 0;
var companyworktypeNum = 0;
var companytechnologyNum = 0;
var companyProductTypeNum = 0;

function statusNextCompanyThird() {
	var theNumberOf = document.getElementsByName("theNumberOf");
	var companyworktype = document.getElementsByName("companyworktype");
	var companytechnology = document.getElementsByName("companytechnology");
	var companyProductType = document.getElementsByName("companyProductType");
	var addEduContentLength = document.querySelectorAll("#addCPE>div").length;

	for (var i = 0; i < theNumberOf.length; i++) {
		if (theNumberOf[i].checked) {
			theNumberOfNum = 1;
		}
	}

	for (var j = 0; j < companyworktype.length; j++) {
		if (companyworktype[j].checked) {
			companyworktypeNum = 1;
		}
	}

	for (var k = 0; k < companytechnology.length; k++) {
		if (companytechnology[k].checked) {
			companytechnologyNum = 1;
		}
	}

	for (var z = 0; z < companyProductType.length; z++) {
		if (companyProductType[z].checked) {
			companyProductTypeNum = 1;
		}
	}

	if (theNumberOfNum != 0 && companyworktypeNum != 0 && (companytechnologyNum != 0 || document.getElementById("companytechnology").value != "") && (companyProductTypeNum != 0 || document.getElementById("companyProductType").value != "") && document.getElementById("addCIntro").value != "" && addEduContentLength != 0) {
		$("#companySecond").hide();
		$("#servicerCompanyDiv").show();
	} else if (theNumberOfNum == 0) {
		checkNotNull("campanySize", false);
	} else if (companyworktypeNum == 0) {
		checkNotNull("companyCanWorkType", false)
	} else if (companytechnologyNum == 0 && document.getElementById("companytechnology").value == "") {
		checkNotNull("companyEtechnologies", false)
	} else if (companyProductTypeNum == 0 && document.getElementById("companyProductType").value == "") {
		checkNotNull("productType", true)
	} else if (document.getElementById("addCIntro").value == "") {
		checkNotNull("addCI", true)
	} else if (addEduContentLength == 0) {
		checkNotNull("addCPE", false)
	}
}

function nextCompanyThird() {
	var theNumberOf = document.getElementsByName("theNumberOf");
	var companyworktype = document.getElementsByName("companyworktype");
	var companytechnology = document.getElementsByName("companytechnology");
	var companyProductType = document.getElementsByName("companyProductType");
	var addEduContentLength = document.querySelectorAll("#addCPE>div").length;

	for (var i = 0; i < theNumberOf.length; i++) {
		if (theNumberOf[i].checked) {
			theNumberOfNum = 1;
		}
	}

	for (var j = 0; j < companyworktype.length; j++) {
		if (companyworktype[j].checked) {
			companyworktypeNum = 1;
		}
	}

	for (var k = 0; k < companytechnology.length; k++) {
		if (companytechnology[k].checked) {
			companytechnologyNum = 1;
		}
	}

	for (var z = 0; z < companyProductType.length; z++) {
		if (companyProductType[z].checked) {
			companyProductTypeNum = 1;
		}
	}

	if (theNumberOfNum != 0 && companyworktypeNum != 0 && (companytechnologyNum != 0 || document.getElementById("companytechnology").value != "") && (companyProductTypeNum != 0 || document.getElementById("companyProductType").value != "") && document.getElementById("addCIntro").value != "" && addEduContentLength != 0) {
		$("#companySecond").hide();
		$("#servicerCompanyDiv").show();
		//下一步保存
        nextSave("company");
        scroll(0,0);
	} else if (theNumberOfNum == 0) {
		checkNotNull("campanySize", false);
	} else if (companyworktypeNum == 0) {
		checkNotNull("companyCanWorkType", false)
	} else if (companytechnologyNum == 0 && document.getElementById("companytechnology").value == "") {
		checkNotNull("companyEtechnologies", false)
	} else if (companyProductTypeNum == 0 && document.getElementById("companyProductType").value == "") {
		checkNotNull("productType", true)
	} else if (document.getElementById("addCIntro").value == "") {
		checkNotNull("addCI", false)
	} else if (addEduContentLength == 0) {
		checkNotNull("addCPE", false)
	}
}

var idCardPicUploadResult = false;
var blPicUploadResult = false;
var type; //认证类型

// 提交表单
function doAthenInfoSave() {
	var mURL = "/api/1/u/identify";
	//var topURL = $("#topurl").val();
	var operate = 'add';
	var data = $("#atheninfoForm").serialize();

	var isIdentifyModify = $("#isIdentifyModify").val();

	if (isIdentifyModify == 1) {
		operate = 'modify';
	}

	if (orgElId == "personalBtn") {
		if ($("#realName").val() == "") {
			document.getElementById("realNameDiv").querySelector("span:last-of-type").style.display = "block"
			document.getElementById("realNameDiv").scrollIntoView(false);
			return false;
		}
		if ($("#idCardNum").val() == "" || isIdCardNo($("#idCardNum").val().trim()) == false) {
			if ($("#idCardNum").val() == "") {
				document.getElementById("idCardDiv").querySelector("span:last-of-type").innerHTML = "请填写身份证号"
				document.getElementById("idCardDiv").querySelector("span:last-of-type").style.display = "block"
				document.getElementById("idCardDiv").scrollIntoView(false);
			} else {
				document.getElementById("idCardDiv").querySelector("span:last-of-type").innerHTML = "不存在该身份证"
				document.getElementById("idCardDiv").querySelector("span:last-of-type").style.display = "block"
				document.getElementById("idCardDiv").scrollIntoView(false);
			}
			return false
		}
		// 教育背景
		var EduBackground = "";
		//工作经验
		var workExperience = "";
		//项目作品
		var project = "";
		var ajStr = ""; //aj过去的数据

		var freelanceType = "";
		var candoStr = "";
		var mainAbilityStr = "";
		var cando = [];
		var mainAbility = [];
		var otherMainAbility = "";
		var arr = data.split("&");
		// 个人认证提交
		type = "&identifyInfo.category=0";


        if(isresume == "yes"){
        	freelanceType = "freelanceType=0";
        }else{
        	freelanceType = "freelanceType=" + arr[0].substr(14);
        }

		if ($("#ActionScript").val() != "") {
			otherMainAbility = "&otherAbility=" + $("#ActionScript").val();
		} else {
			otherMainAbility = "&otherAbility=";
		}

		for (var z = 0; z < arr.length; z++) {
			if (arr[z].substr(0, 12) == "mainAbility=") {
				mainAbility.push(arr[z].substr(12))
			}
		}
		mainAbilityStr = "&mainAbility=" + mainAbility.join()
		for (var j = 0; j < arr.length; j++) {
			if (arr[j].substr(0, 6) == "cando=") {
				cando.push(arr[j].substr(6))
			}
		}
		candoStr = "&cando=" + cando.join()

		var xranking = "";
		var yranking = "";
		var iranking = "";
		for (var x = 0; x < $$("#addWorkContent>div").length; x++) {
			workExperience += "&employeeJobExperience[" + x + "].companyName=" + $$("#addWorkContent>div")[x].querySelector("div:first-of-type").lastChild.textContent + "&employeeJobExperience[" + x + "].office=" + $$("#addWorkContent>div")[x].querySelector("div:nth-of-type(4)").lastChild.textContent + "&employeeJobExperience[" + x + "].startTime=" + ($$("#addWorkContent>div")[x].querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[0] + "&employeeJobExperience[" + x + "].endTime=" + ($$("#addWorkContent>div")[x].querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[1] + "&employeeJobExperience[" + x + "].description=" + $$("#addWorkContent>div")[x].querySelector("div:last-of-type").lastChild.textContent;

		}
		for (var xr = 0; xr < x; xr++) {
			xranking += "&employeeJobExperience[" + xr + "].ranking=" + xr;
		}

		for (var y = 0; y < $$("#addProjectContent>div").length; y++) {
			project += "&employeeProduct[" + y + "].title=" + $$("#addProjectContent>div")[y].querySelector("div:first-of-type").lastChild.textContent + "&employeeProduct[" + y + "].description=" + $$("#addProjectContent>div")[y].querySelector("div:last-of-type").lastChild.textContent + "&employeeProduct[" + y + "].link=" + $$("#addProjectContent>div")[y].querySelector("div:nth-of-type(3)").lastChild.textContent
		}
		for (var yr = 0; yr < y; yr++) {
			yranking += "&employeeProduct[" + yr + "].ranking=" + yr;
		}

		for (var i = 0; i < $$("#addEduContent>div").length; i++) {
			EduBackground += "&employeeEduExperience[" + i + "].schoolName=" + $$("#addEduContent>div")[i].querySelector("div:first-of-type").lastChild.textContent + "&employeeEduExperience[" + i + "].discipline=" + $$("#addEduContent>div")[i].querySelector("div:nth-of-type(3)").lastChild.textContent + "&employeeEduExperience[" + i + "].eduBackgroud=" + $$("#addEduContent>div")[i].querySelector("div:nth-of-type(4)").lastChild.textContent + "&employeeEduExperience[" + i + "].graduationTime=" + $$("#addEduContent>div")[i].querySelector("div:last-of-type").lastChild.textContent
		}
		for (var ir = 0; ir < i; ir++) {
			iranking += "&employeeEduExperience[" + ir + "].ranking=" + ir;
		}

		var realName = "";
		for (var rN = 0; rN < arr.length; rN++) {
			if (arr[rN].substr(0, 9) == "realName=") {
				realName = "&identifyInfo.realName=" + arr[rN].substr(9)
			}
		}

		var idcardNum = "";
		for (var icNum = 0; icNum < arr.length; icNum++) {
			if (arr[icNum].substr(0, 10) == "idCardNum=") {
				idcardNum = "&identifyInfo.idCard=" + arr[icNum].substr(10);
			}
		}

		// 根据简历类型传递resumeType
		var resumeUrl = $("#resume-url").val();
		var resumeType = "input";
		if(isresume=="yes"){
/*			if(reqAttachementUploadResult == true){
				resumeType = "file";
			}else{
				resumeType = "link";
			}*/
			if($("#resume").val()!=""||$("#resume").val()!=null)
			{
				resumeType = "file";
			}else if($("#resume-url").val()!=""||$("#resume-url").val()!=null){
				resumeType = "link";
			}
		}

		ajStr = freelanceType + candoStr + mainAbilityStr + otherMainAbility + workExperience + xranking + project + yranking + EduBackground + iranking + realName + idcardNum + type + "&operate=" + operate + "&resumeUrl=" + resumeUrl + "&resumeType=" + resumeType + "&introduction=" + $("#profile").val() + "&accountNum=" + $("#payAccount").val();

        //个人认证步骤记录和认证状态
        var identifyStatus = $("#identifyStatus").val();

        ajStr += "&identifyInfo.status=" + identifyStatus + "&identifyStep=4";

		$.ajax({
			dataType: "json",
			type: "POST",
			async: false,
			data: ajStr,
			url: mURL,
			success: function(data) {
				if (data.resultCode == 0) {
					showMessageBox("提交成功", "认证提交",data.msg);
					// 执行操作....
					//location.reload();
				} else {
					showMessageBox(data.errorMsg, "认证提交");
				}
			}
		});
	} else {

		if ($("#compName").val() == "") {
			document.getElementById("checkCompanyName").querySelector("span:last-of-type").style.display = "block"
			document.getElementById("checkCompanyName").scrollIntoView(false);
			return false;
		}
		if ($("#compAddr").val() == "") {
			document.getElementById("checkCompanyAddr").querySelector("span:last-of-type").style.display = "block"
			document.getElementById("checkCompanyAddr").scrollIntoView(false);
			return false;
		}
		if ($("#compUrl").val() == "") {
			document.getElementById("checkCompanyUrl").querySelector("span:last-of-type").style.display = "block"
			document.getElementById("checkCompanyUrl").scrollIntoView(false);
			return false;
		}
		if ($("#repName").val() == "") {
			document.getElementById("checkLegalPersons").querySelector("span:last-of-type").style.display = "block"
			document.getElementById("checkLegalPersons").scrollIntoView(false);
			return false;
		}
		if ($("#blNum").val() == "") {
			document.getElementById("checkCompanyNum").querySelector("span:last-of-type").style.display = "block"
			document.getElementById("checkCompanyNum").scrollIntoView(false);
			return false;
		}

		type = "&identifyInfo.category=1";
		var arr = data.split("&");
		//公司项目经验
		var addCPE = "";
		var iranking = "";
		var companyAjStr = "";
		var companySize;
		//工作类型
		var companyworktype = [];
		var CWT = "";
		//擅长
		var companytechnology = [];
		var CT = "";
		var otherCaseType = "";
		//产品类型
		var productType = [];
		var PT = "";

		var otherProductType = "";

		var companyintro = "";

		for (var w = 0; w < arr.length; w++) {
			if (arr[w].substr(0, 12) == "theNumberOf=") {
				companySize = "&companySize=" + arr[w].substr(12)
			}
		}

		for (var e = 0; e < arr.length; e++) {
			if (arr[e].substr(0, 16) == "companyworktype=") {
				companyworktype.push(arr[e].substr(16))
			}
		}

		CWT = "&cando=" + companyworktype.join()

		for (var r = 0; r < arr.length; r++) {
			if (arr[r].substr(0, 18) == "companytechnology=") {
				companytechnology.push(arr[r].substr(18))
			}
		}

		CT = "&mainAbility=" + companytechnology.join()

		for (var t = 0; t < arr.length; t++) {
			if (arr[t].substr(0, 19) == "companyProductType=") {
				productType.push(arr[t].substr(19))
			}
		}

		PT = "&caseType=" + productType.join()

		for (var q = 0; q < $$("#addCPE>div").length; q++) {
			addCPE += "&employeeProjectExperience[" + q + "].projectName=" + $$("#addCPE>div")[q].querySelector("div:first-of-type").lastChild.textContent + "&employeeProjectExperience[" + q + "].startTime=" + ($$("#addCPE>div")[q].querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[0] + "&employeeProjectExperience[" + q + "].endTime=" + ($$("#addCPE>div")[q].querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[1] + "&employeeProjectExperience[" + q + "].description=" + $$("#addCPE>div")[q].querySelector("div:nth-of-type(4)").lastChild.textContent + "&employeeProjectExperience[" + q + "].link=" + $$("#addCPE>div")[q].querySelector("div:last-of-type").lastChild.textContent
		}
		for (var rq = 0; rq < q; rq++) {
			iranking += "&employeeProjectExperience[" + rq + "].ranking=" + rq
		}

		if ($("#companytechnology").val() != "") {
			otherCaseType = "&otherAbility=" + $("#companytechnology").val()
		} else {
			otherCaseType = "&otherAbility=";
		}

		if ($("#companyProductType").val() != "") {
			otherProductType = "&otherCaseType=" + $("#companyProductType").val();
		} else {
			otherProductType = "&otherCaseType=";
		}

		companyintro = "&introduction=" + $("#addCIntro").val();

		var companyname = "";
		var companyaddr = "";
		var companyurl = "";
		var repName = "";
		var compPhone = "";
		var blNum = "";

		for (var cpi = 0; cpi < arr.length; cpi++) {
			if (arr[cpi].substr(0, 9) == "compName=") {
				companyname = "&identifyInfo.companyName=" + arr[cpi].substr(9);
			}
			if (arr[cpi].substr(0, 9) == "compAddr=") {
				companyaddr = "&identifyInfo.companyAddr=" + arr[cpi].substr(9);
			}
			if (arr[cpi].substr(0, 8) == "compUrl=") {
				companyurl = "&identifyInfo.siteLink=" + arr[cpi].substr(8)
			}
			if (arr[cpi].substr(0, 8) == "repName=") {
				repName = "&&identifyInfo.legalRepresent=" + arr[cpi].substr(8)
			}
			if (arr[cpi].substr(0, 13) == "compPhoneNum=") {
				compPhone = "&identifyInfo.companyPhone=" + arr[cpi].substr(13)
			}
			if (arr[cpi].substr(0, 6) == "blNum=") {
				blNum = "&identifyInfo.businessLicense=" + arr[cpi].substr(6)
			}
		}

		companyAjStr = companySize + CWT + CT + otherCaseType + PT + otherProductType + companyintro + addCPE + iranking + companyname + companyaddr + companyurl + repName + compPhone + blNum + type + "&operate=" + operate + "&accountNum=" + $("#payAccount2").val();

        //企业认证步骤记录和认证状态
        var identifyStatus = $("#identifyStatus").val();

        companyAjStr += "&identifyInfo.status=" + identifyStatus + "&identifyStep=3";

		$.ajax({
			dataType: "json",
			type: "POST",
			async: false,
			data: companyAjStr,
			url: mURL,
			success: function(data) {
				if (data.resultCode == 0) {
					showMessageBox("提交成功", "认证提交",data.msg);
					// 执行操作....
					//location.reload();
				} else {
					showMessageBox(data.errorMsg, "认证提交");
				}
			}
		});
	}
}

//提交修改密码表单
function changePassword() {
	var curl = "/api/1/u/changePassword";
	var cdata = $("#changePasswordForm").serialize();
	$.ajax({
		data: cdata,
		type: "post",
		url: curl,
		async: false,
		success: function(data) {
			if (data.resultCode == 0) {
				showMessageBox("密码修改成功", "密码修改");
			} else {
				showMessageBox("密码修改失败: " + data.errorMsg, "密码修改");
			}
		}
	});
}

function doModifyAuthenInfo() {
	var mURL = "/home/identify/modify";

	$.ajax({
		type: "get",
		url: mURL,
		async: false,
		success: function(data) {
			location.href = "/home/userinfo";
		}
	});
}

function doValidateEmail() {
	var mURL = "/api/mail/validate";

	var email = $("#email").val();

	if (email == null || email.trim() == "") {
		return;
	}

	var paraData = "email=" + email;

	time($("#emailValidateBtn"));

	$.ajax({
		type: "get",
		url: mURL,
		data: paraData,
		async: false,
		success: function(data) {
			showMessageBox("验证码已发送到您的邮箱，请登录邮箱查收，然后在\"邮箱验证码\"里输入您收到的验证码。<br>若未收到邮件，请点击\"重新发送\"。", "邮箱验证");
		}
	});
}

//var orgTabContentId = "baseInfo";
//var orgNavId = "navBaseInfo";
var orgTabContentId = $("#tabContentId").val();
var orgNavId = $("#tabId").val();

function showTabContentById(id) {
	showTabContentInner(id);
}

function showTabContent(el,e) {

  var id = el.id;

  showTabContentInner(id);
}

// 生成项目列表
function queryProjectList(id) {
	var curl = "";

	if (id == "publishTheProject") {
		curl = "/api/1/u/request/list";
	} else if (id == "JoinTheProject") {
		curl = "/api/1/u/join/list";
	} else if (id == "canJoinTheProject") {
		curl = "/api/1/u/invite/list";
	}

	$.ajax({
		type: "GET",
		url: curl,
		async: false,
		success: function(data) {
			// 返回值非JSON，超时重新登录
			if (typeof data != "object") {
				location.href = "/public/login";
			} else {
				var itemHTML;
				//empty the original content
				if(data.length > 0){
				  $("#" + id + "Item").html("");
				}

				for (i = 0; i < data.length; i++) {
					itemHTML = showProjectList(id, data[i]);
					$("#" + id + "Item").append(itemHTML);
				}
			}
		}
	});
}

function showProjectList(id, data) {
	var projectName;
	var createTime;
	var status;
	var viewUrl;
	var joinStatus = -1;
	var btnName = "项目详情";
	var modifyUrl;

	if (id == "canJoinTheProject" || id == "JoinTheProject") {
		projectName = data.projectName;
		createTime = data.createTime;
		status = data.projectStatus;
		joinStatus = data.status
		viewUrl = "/home/selfrun/p/view?id=" + data.projectId;
		modifyUrl = "/home/p/modify?id=" + data.projectId;
	} else {
		projectName = data.name;
		createTime = data.createTime;
		status = data.displayStatus;
		viewUrl = "/home/selfrun/p/view?id=" + data.id;
		modifyUrl = "/home/p/modify?id=" + data.id
	}

	var projectRealName = projectName;//项目全称

	// if(projectName.length > 20)
	// {
	// 	projectName = projectName.substr(0,20) + "...";
	// }

	// 改变按钮名称
	if (joinStatus == 0 || joinStatus == 1 || joinStatus == 2 || joinStatus == 5 || joinStatus == 7 || status == "已关闭") {
		viewUrl = "/home/p/detail/" + data.projectId;
	}

	if (id == "publishTheProject"){
		var itemHTML = '<div class="col-xs-12 DivBackground">' +
			'<div class="col-xs-12 col-md-12 sendProject"><img class="smallImg" src="' + topurl + 'img/what.png"/><span title="'+projectRealName+'">' + projectName + '</span></div>' +
			'<div class="col-xs-12 col-md-3 sendProject"><img class="smallImg" src="' + topurl + 'img/data.png"/><p>' + createTime + '</p></div>' +
			'<div class="col-xs-12 col-md-2 sendProject"><img class="smallImg" src="' + topurl + 'img/time.png"/><p>' + status + '</p></div>' +
			'<div class="col-xs-4 col-md-2 operation" onclick="window.open('+ "'" + viewUrl + "'" +')"><span class="icon-span-style"><img class="smallImg" src="' + topurl + 'img/details.png"/></span><span>详情</span></div>' +
			// '<div class="col-xs-4 col-md-2 operation" onclick="window.open('+ "'" + modifyUrl + "'" +')"><span class="icon-span-style"><img class="smallImg" src="' + topurl + 'img/modify.png"/></span><span>修改</span></div>' +
			'</div>';
	}else{
		var itemHTML = '<div class="col-xs-12 col-md-12 DivBackground">' +
			'<div class="col-xs-12 col-sm-12 sendProject"><img class="smallImg" src="' + topurl + 'img/what.png"/><span title="'+projectRealName+'">' + projectName + '</span></div>' +
			'<div class="col-xs-12 col-sm-2 sendProject"><img class="smallImg" src="' + topurl + 'img/data.png"/><p>' + createTime + '</p></div>' +
			'<div class="col-xs-12 col-sm-2 sendProject"><img class="smallImg" src="' + topurl + 'img/time.png"/><p>' + status + '</p></div>' +
			'<div class="col-xs-12 col-sm-2 operation" onclick="window.open('+ "'" + viewUrl + "'" +')"><span class="icon-span-style"><img class="smallImg" src="' + topurl + 'img/details.png"/></span><span>详情</span></div>'
			+'</div>';
	}

	return itemHTML;
}

function showTabContentInner(id) {

	var isComplete = $("#isCompleteFlag").val();
	var identifiedStatus = $("#h_identifiedStatus").val();

	if (id == "navAuthInfo" && isComplete == 0) {
		showMessageBox("请您先完善基本信息", "完善资料");
		return;
	}

	if (id == "canJoinTheProject") {
		if(identifiedStatus == -1){
			showMessageBox("请您先完善认证信息，以方便我们精准为您推送项目，谢谢您的配合！", "完善资料");
			return;
		}else if(identifiedStatus == 0){
			showMessageBox("您提交的认证还在审核中，我们会尽快处理，请耐心等待！", "完善资料");
			return;
		}else if(identifiedStatus == 2){
			showMessageBox("您提交的认证信息没有通过审核，请修改后再次提交，谢谢您的配合！", "完善资料");
			return;
		}
	}

		if (id == "ability") {
		if(identifiedStatus == -1){
			showMessageBox("请您先完善认证信息，才可以进行测评，谢谢您的配合！", "完善资料");
			return;
		}else if(identifiedStatus == 0){
			showMessageBox("您提交的认证还在审核中，审核通过才可进行测评，请耐心等待！", "完善资料");
			return;
		}else if(identifiedStatus == 2){
			showMessageBox("您提交的认证信息没有通过审核，请修改后再次提交，审核通过后才能测评，谢谢您的配合！", "完善资料");
			return;
		}
	}

	$("#" + orgTabContentId).hide();
	$("#" + orgNavId).removeClass("active");
	$("#" + id).addClass("active");

	if (id == "navBaseInfo") {
		$("#baseInfo").show();
		orgTabContentId = "baseInfo";
		orgNavId = id;
	} else if (id == "navAuthInfo") {
//		//判断显示页面
//		var IdentifyStep = $("#IdentifyStep").val();//记录用户输入进度
//		if(IdentifyStep==1)
//		{
			$("#autheninfo").show();
//		}else if(IdentifyStep==3){
//			//记录用户输入步骤
//			$("#autheninfo").show();
//			$("#userTypeChoosenDiv").hide();
//			$("#atheninfoForm h3").html("Step 4.为了您的信用信息,请进行身份认证");
//			$("#servicerPersonalDiv").show()
//		}else if(IdentifyStep==2){
//			//记录企业输入步骤
//			$("#autheninfo").show();
//			$("#userTypeChoosenDiv").hide();
//			$("#atheninfoForm h3").html("Step 3.让我们了解您擅长的技能,为您推荐最合适的项目");
//			$("#servicerCompanyDiv").show()
//		}else{
//			$("#autheninfo").show();
//		}
		orgTabContentId = "autheninfo";
		orgNavId = id;
	} else if (id == "navOptions") {
		$("#navOptionsContent").show();
		orgTabContentId = "navOptionsContent";
		orgNavId = id;
	} else if (id == "publishTheProject") {
		$("#publishTheProjectContent").show();
		orgTabContentId = "publishTheProjectContent"
		orgNavId = id;
		queryProjectList(id);
	} else if (id == "JoinTheProject") {
		$("#JoinTheProjectContent").show();
		orgTabContentId = "JoinTheProjectContent"
		orgNavId = id;
		queryProjectList(id);
	} else if (id == "canJoinTheProject") {
		$("#canJoinTheProjectContent").show();
		orgTabContentId = "canJoinTheProjectContent"
		orgNavId = id;
		queryProjectList(id);
	}else if( id == "ability"){
		$("#abilityContent").show();
		orgTabContentId = "abilityContent"
		orgNavId = id;
	}
}

//init pic upload
var idCardInitHTML = "";
var blPicInitHTML = "";
var idCardPicName = "";
var blPicName = "";
var $$ = function(s) {
	return document.querySelectorAll(s)
}

var isIdentifyModify = $("#isIdentifyModify").val();

if (isIdentifyModify == 1) {
	var category = $("#h_category").val();
    var idCardImgPath = $("#h_idCardImg").val();
    var blPicImgPath = $("#h_blImg").val();

	if (category == 0 && idCardImgPath != null && idCardImgPath.trim() != "" ) {
		idCardInitHTML = '<img src="' + idCardImgPath + '" class="file-preview-image"/>';
		idCardPicUploadResult = true;
	} else if (category == 1 && blPicImgPath != null && blPicImgPath.trim() != "") {
		blPicInitHTML = '<img src="' + blPicImgPath + '" class="file-preview-image"/>';
		blPicUploadResult = true;
	}
}

//初始化时不需要预加载
initPicUploadWidgit("idCardPic", "/uploadify?ext=image&type=1", idCardInitHTML);
initPicUploadWidgit("blPic", "/uploadify?ext=image&type=4", blPicInitHTML);

//公司认证多选js
for (var cti = 0; cti < $$(".companyexcelstechnologies div label").length; cti++) {
	$$(".companyexcelstechnologies div label")[cti].onclick = function() {
		if (hasClass(this, "companyexcelstechnologiesActive")) {
			removeClass(this, "companyexcelstechnologiesActive")
		} else {
			addClass(this, "companyexcelstechnologiesActive")
		}
		clearCheckNotNull('companyEtechnologies')
	}
}

for (var cgt = 0; cgt < $$(".producttype div label").length; cgt++) {
	$$(".producttype div label")[cgt].onclick = function() {
		if (hasClass(this, "producttypeActive")) {
			removeClass(this, "producttypeActive")
		} else {
			addClass(this, "producttypeActive")
		}
		clearCheckNotNull('productType')
	}
}

function labelcheckboxActive(str) {
	if (str.querySelector("div").querySelector('img').style.display == "block") {
		str.querySelector("div").querySelector('img').style.display = "none";
	} else {
		str.querySelector("div").querySelector('img').style.display = "block";
	}

}

if ($("#companyworktypeHidden").val() != "") {
	var companyworktypeLabel = document.querySelectorAll("#companyCanWorkType div label")
	for (var companyWT = 0; companyWT < companyworktypeLabel.length; companyWT++) {
		for (var companyworktypeLength = 0; companyworktypeLength < ($("#companyworktypeHidden").val()).split(",").length; companyworktypeLength++)
			if (companyworktypeLabel[companyWT].getAttribute("for") == ("cCT" + ($("#companyworktypeHidden").val()).split(",")[companyworktypeLength])) {
				companyworktypeLabel[companyWT].click();
			}
	}
}

if ($("#companytechnologyText").val() != "") {
	$("#companytechnology").val($("#companytechnologyText").val())
}


if ($("#companytechnologyCheckbox").val() != "") {
	var companyEtechnologiesLabel = document.querySelectorAll("#companyEtechnologies div label");
	for (var cEL = 0; cEL < companyEtechnologiesLabel.length; cEL++) {
		for (var companyCLength = 0; companyCLength < ($("#companytechnologyCheckbox").val()).split(",").length; companyCLength++) {
			if (companyEtechnologiesLabel[cEL].getAttribute("for") == ("cET" + ($("#companytechnologyCheckbox").val()).split(",")[companyCLength])) {
				companyEtechnologiesLabel[cEL].click();
				companyEtechnologiesLabel[cEL].classList.add("companyexcelstechnologiesActive")
			}

		}
	}
}

if ($("#companyProductTypetext").val() != "") {
	$("#companyProductType").val($("#companyProductTypetext").val())
}

if ($("#mainAbilityT").val() != "") {
	$("#ActionScript").val($("#mainAbilityT").val())
}

if ($("#mainAbilityCheckBox").val() != "") {
	var mainAbilityLabel = document.querySelectorAll("#ExcelsTechnologies div label");
	for (var MaL = 0; MaL < mainAbilityLabel.length; MaL++) {
		for (var mainAbilityLength = 0; mainAbilityLength < ($("#mainAbilityCheckBox").val()).split(",").length; mainAbilityLength++) {
			if (mainAbilityLabel[MaL].getAttribute("for") == ("ETechnologies" + ($("#mainAbilityCheckBox").val()).split(",")[mainAbilityLength])) {
				mainAbilityLabel[MaL].click();
				mainAbilityLabel[MaL].classList.add("excelstechnologiesActive")
			}
		}
	}
}

if ($("#companyProductTypecheckbox").val() != "") {
	var productTypeLabel = document.querySelectorAll("#productType div label");
	for (var pTL = 0; pTL < productTypeLabel.length; pTL++) {
		for (var productTypeLength = 0; productTypeLength < ($("#companyProductTypecheckbox").val()).split(",").length; productTypeLength++) {
			if (productTypeLabel[pTL].getAttribute("for") == ("CPT" + ($("#companyProductTypecheckbox").val()).split(",")[productTypeLength])) {
				productTypeLabel[pTL].click();
				productTypeLabel[pTL].classList.add("producttypeActive")
			}

		}
	}
}

if ($("#freelanceTypeHidden").val() != "" && $("#freelanceTypeHidden").val() != -1) {
	if ($("#freelanceTypeHidden").val() == 0) {
		$("#freelanceTypeHidden").val("可兼职接活")
	} else if ($("#freelanceTypeHidden").val() == 1) {
		$("#freelanceTypeHidden").val("自由职业者")
	} else if ($("#freelanceTypeHidden").val() == 2) {
		$("#freelanceTypeHidden").val("在校学生")
	}
	var workStatusArr = document.querySelectorAll("#workStatus div label")
	for (var WS = 0; WS < workStatusArr.length; WS++) {
		if ($("#freelanceTypeHidden").val() == workStatusArr[WS].querySelector("p").innerHTML) {
			workStatusArr[WS].click();
		}
	}

}


if ($("#companyworktypeHidden").val() != "") {
	var canWorkTypeLabel = document.querySelectorAll("#canWorkType div label")
	for (var cwTNum = 0; cwTNum < canWorkTypeLabel.length; cwTNum++) {
		for (var canWorkTypeLength = 0; canWorkTypeLength < ($("#nameCando").val()).split(",").length; canWorkTypeLength++)
			if (canWorkTypeLabel[cwTNum].getAttribute("for") == ("WType" + ($("#nameCando").val()).split(",")[canWorkTypeLength])) {
				canWorkTypeLabel[cwTNum].click();
			}
	}
}

function initPicUploadWidgit(id, uploadURL, initImgHTML) {
	var eid = "#" + id;

	if (initImgHTML != null && initImgHTML.trim() != "") {
		$(eid).fileinput({
			allowedFileExtensions: ["jpg", "png", "gif"],
			maxFileSize: 5120,
			language: "zh",
			uploadUrl: uploadURL,
			showPreview: true,
			uploadAsync: false,
			maxFileCount: 1,
			showClose: false,
			showUpload: false,
			showCancel: false,
			showRemove: false,
			autoReplace: true,
			initialPreviewCount: 1,
			dropZoneEnabled: false,
			initialPreviewShowDelete: false,
			layoutTemplates: {
				actionDelete: ""
			},
			initialPreview: [initImgHTML]
				//initialPreviewShowDelete:true
				//initialPreview:["<img style='height:160px' src='http://loremflickr.com/200/150/people?random=1'>"]
		});
	} else {
		$(eid).fileinput({
			allowedFileExtensions: ["jpg", "png", "gif"],
			maxFileSize: 5120,
			language: "zh",
			uploadUrl: uploadURL,
			showPreview: true,
			uploadAsync: false,
			maxFileCount: 1,
			showClose: false,
			showUpload: false,
			showCancel: false,
			showRemove: false,
			autoReplace: true,
			dropZoneEnabled: false,
			initialPreviewShowDelete: false,
			layoutTemplates: {
				actionDelete: ""
			}
		});
	}


	if (id == "idCardPic") {
		$(eid).on('filebatchuploaderror', function(event, data, previewId, index) {
			idCardPicUploadResult = false;
			$("#idCardPicMsg").html("图片“" + idCardPicName + "” 上传失败");
		});

		$(eid).on('filebatchuploadsuccess', function(event, data, previewId, index) {
			idCardPicUploadResult = true;
			$("#idCardPicMsg").html("图片“" + idCardPicName + "” 上传成功");
		});

		$(eid).on('filecleared', function(event, key) {
			idCardPicUploadResult = false;

		});

		$(eid).on('filebrowse', function(event, key) {
			//
			$(eid).fileinput('clear');
		});

		$(eid).on('filebatchselected', function(event, key) {
			//
			$(eid).fileinput('upload');
			$("#idCardPicMsg").html("");
		});

		$(eid).on('filebatchpreupload', function(event, data, previewId, index) {
			idCardPicName = data.files[0].name;
		});

	} else if (id == "blPic") {
		$(eid).on('filebatchuploaderror', function(event, data, previewId, index) {
			blPicUploadResult = false;
			$("##blPicMsg").html("图片“" + blPicName + "” 上传失败");
		});

		$(eid).on('filebatchuploadsuccess', function(event, data, previewId, index) {
			blPicUploadResult = true;
			$("#blPicMsg").html("图片“" + blPicName + "” 上传成功");
		});

		$(eid).on('filecleared', function(event, key) {
			blPicUploadResult = false;
		});

		$(eid).on('filebrowse', function(event, key) {
			//
			$(eid).fileinput('clear');
		});

		$(eid).on('filebatchselected', function(event, key) {
			$(eid).fileinput('upload');
			$("#blPicMsg").html("");
		});

		$(eid).on('filebatchpreupload', function(event, data, previewId, index) {
			blPicName = data.files[0].name;
		});
	}
}

var $$ = function(s) {
	return document.querySelectorAll(s)
}

function statusThirdToSecond(){
	if(isresume=="yes")
	{
		$("#servicerPersonalDiv").hide();
		$("#is-resume").show();
		userInfoNum=0;
		scroll(0, 0)
	}else{
		$("#servicerPersonalDiv").hide();
		$("#personalSecond").show();
		scroll(0, 0)
	}
}

function cancel(str) {
	str.parentNode.querySelector("button:first-of-type").innerHTML = "确定"
	str.parentNode.querySelector("input").click();
}

//添加公司项目经验
function addcompanyWorkExperience() {
	var addCPE = document.getElementById("addCPE")
	var addCProject = document.getElementById("addCProject")
	if (addCProject.querySelector("img").style.float != "right") {
		addCProject.querySelector("p").style.display = "none"
			//									addCProject.removeChild(addCProject.querySelector("p"))
		addCProject.setAttribute("class", "col-md-2 col-md-offset-4");
		addCProject.querySelector("img").style.float = "right";
	}
	if (document.getElementById("addCompanyProjectExperience").querySelector("button[type='submit']").innerHTML == "修改") {
		var changing = document.getElementById("changing")
		changing.querySelector("div:first-of-type").innerHTML = "<i style='color:#3598db;font-style:normal'>项目名称: </i>" + $("#companyPName").val()
		changing.querySelector("div:nth-of-type(3)").innerHTML = "<i style='color:#3598db;font-style:normal'>项目时间: </i>" + $("#companyworkinghoursstart").val() + " - " + $("#companyworkinghoursend").val()
		changing.querySelector("div:nth-of-type(4)").innerHTML = "<i style='color:#3598db;font-style:normal;float:left'>项目描述: </i><span style='float:left'>" + ($("#companyworkingcontent").val()).replace(/\n/g, "<br/>") + "</span>";
		changing.querySelector("div:last-of-type").innerHTML = "<i style='color:#3598db;font-style:normal'>项目链接: </i>" + $("#companyWorkingUrl").val()

		changing.setAttribute("id", "");
		document.getElementById("addCompanyProjectExperience").querySelector("button[type='submit']").innerHTML = "确定"
	} else {
		var div = document.createElement("div")
		div.setAttribute("class", "col-md-12")
		div.setAttribute("style", "font-size:14px;border-bottom:dashed 1px #ccc;margin-bottom: 10px;")
		var companyPName = document.createElement("div")
		companyPName.setAttribute("style", "margin-bottom:10px")
		companyPName.setAttribute("class", "col-md-9")
		companyPName.innerHTML = "<i style='color:#3598db;font-style:normal'>项目名称: </i>" + $("#companyPName").val()
		var companytime = document.createElement("div")
		companytime.setAttribute("style", "margin-bottom:10px")
		companytime.setAttribute("class", "col-md-12")
		companytime.innerHTML = "<i style='color:#3598db;font-style:normal'>项目时间: </i>" + $("#companyworkinghoursstart").val() + " - " + $("#companyworkinghoursend").val();
		var companyWorkingUrl = document.createElement("div")
		companyWorkingUrl.setAttribute("style", "margin-bottom:10px");
		companyWorkingUrl.setAttribute("class", "col-md-12")
		companyWorkingUrl.innerHTML = "<i style='color:#3598db;font-style:normal'>项目链接: </i>" + $("#companyWorkingUrl").val()
			//操作按钮
		var operation = document.createElement("div");
		operation.setAttribute("class", "col-md-2 col-md-offset-1")
		var revise = document.createElement("img")
		revise.setAttribute("onclick", "revisecompanyWorkExperience(this)")
		revise.setAttribute("data-toggle", "modal")
		revise.setAttribute("href", "#addCompanyProjectExperience")
		revise.setAttribute("style", "width:26px;height:26px;padding-bottom:3px")
		revise.setAttribute("src", topurl + "img/revise.png")
		var del = document.createElement("img")
		del.setAttribute("style", "width:23px;height:23px;padding-bottom:1px")
		del.setAttribute("src", topurl + "img/delect.png")
		del.setAttribute("onclick", "deletecompanyWorkExperience(this)")

		var companyworkingcontent = document.createElement("div")
		companyworkingcontent.setAttribute("style", "margin-bottom:10px")
		companyworkingcontent.setAttribute("class", "col-md-12")
		companyworkingcontent.innerHTML = "<i style='color:#3598db;font-style:normal;float:left'>项目描述: </i><span style='float:left'>" + ($("#companyworkingcontent").val()).replace(/\n/g, "<br/>") + "</span>"
		div.appendChild(companyPName)
		div.appendChild(operation)
		div.appendChild(companytime)
		operation.appendChild(revise)
		operation.appendChild(del)
		div.appendChild(companyworkingcontent)
		div.appendChild(companyWorkingUrl)
		addCPE.appendChild(div)
	}
	$("#addCompanyProjectExperience").modal('hide');
	document.getElementById("resetToCompanyE").click();
}

//修改公司项目经验
function revisecompanyWorkExperience(str) {
	$("#companyPName").val(str.parentNode.parentNode.querySelector("div:first-of-type").lastChild.textContent)
	$("#companyworkinghoursstart").val(((str.parentNode.parentNode.querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[0]).replace())
	$("#companyworkinghoursend").val((str.parentNode.parentNode.querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[1])
	$("#companyworkingcontent").val((str.parentNode.parentNode.querySelector("div:nth-of-type(4)").lastChild.innerHTML).replace(/<br>/g, "\n"))
	$("#companyWorkingUrl").val(str.parentNode.parentNode.querySelector("div:last-of-type").lastChild.textContent)
	document.getElementById("addCompanyProjectExperience").querySelector("button[type='submit']").innerHTML = "修改";
	str.parentNode.parentNode.setAttribute("id", "changing")
}

//移除公司项目经验
function deletecompanyWorkExperience(str) {
	var addCPE = document.getElementById("addCPE");
	addCPE.removeChild(str.parentNode.parentNode)
	if (addCPE.children.length == 3) {
		addCProject.querySelector("p").style.display = "block"
		addCProject.setAttribute("class", "col-md-6 col-md-offset-5")
		addCProject.querySelector("img").style.float = "left";
		//									var p = document.createElement("p")
		//									p.innerHTML="添加项目经验";
		//									addCProject.appendChild(p)
	}
}

//添加教育背景
function addeducational() {
	var addEdu = document.getElementById("addEdu")
	var addEduContent = document.getElementById("addEduContent")
	if (addEdu.querySelector("img").style.float != "right") {
		addEdu.querySelector("p").style.display = "none";
		//									addEdu.removeChild(addEdu.querySelector("p"))
		addEdu.setAttribute("class", "col-md-2 col-md-offset-4");
		addEdu.querySelector("img").style.float = "right";
	}
	if (document.getElementById("educational").querySelector("button[type='submit']").innerHTML == "修改") {
		var changing = document.getElementById("changing")
		var education = document.getElementById("education")
		var graduationyears = document.getElementById("graduationyears");
		changing.querySelector("div:first-of-type").innerHTML = "<i style='color:#3598db;font-style:normal'>学校名称: </i>" + $("#schoolname").val();
		changing.querySelector("div:nth-of-type(3)").innerHTML = "<i style='color:#3598db;font-style:normal'>所学专业: </i>" + $("#major").val();
		changing.querySelector("div:nth-of-type(4)").innerHTML = "<i style='color:#3598db;font-style:normal'>学历: </i>" + education.options[education.selectedIndex].text
		changing.querySelector("div:last-of-type").innerHTML = "<i style='color:#3598db;font-style:normal'>毕业年份: </i>" + graduationyears.options[graduationyears.selectedIndex].text

		changing.setAttribute("id", "")
		document.getElementById("educational").querySelector("button[type='submit']").innerHTML = "确定"
	} else {
		var div = document.createElement("div")
		div.setAttribute("class", "col-md-12")
		div.setAttribute("style", "font-size:14px;border-bottom:dashed 1px #ccc;margin-bottom: 10px;padding:10px")
		var schoolname = document.createElement("div")
		schoolname.setAttribute("style", "margin-bottom:10px")
		schoolname.setAttribute("class", "col-md-9")
		schoolname.innerHTML = "<i style='color:#3598db;font-style:normal'>学校名称: </i>" + $("#schoolname").val()
		var major = document.createElement("div")
		major.setAttribute("style", "margin-bottom:10px")
		major.setAttribute("class", "col-md-12")
		major.innerHTML = "<i style='color:#3598db;font-style:normal'>所学专业: </i>" + $("#major").val()
		var education = document.createElement("div")
		education.setAttribute("style", "margin-bottom:10px")
		education.setAttribute("class", "col-md-12")
		var educationIndex = document.getElementById("education").selectedIndex;
		education.innerHTML = "<i style='color:#3598db;font-style:normal'>学历: </i>" + document.getElementById("education").options[educationIndex].text;
		var graduationyears = document.createElement("div")
		graduationyears.setAttribute("style", "margin-bottom:10px")
		graduationyears.setAttribute("class", "col-md-12")
		var g = document.getElementById("graduationyears")
		var gIndex = g.selectedIndex;
		graduationyears.innerHTML = "<i style='color:#3598db;font-style:normal'>毕业年份: </i>" + g.options[gIndex].text;
		graduationyears.setAttribute("name", "employeeEduExperience")
			// 编辑按钮
		var operation = document.createElement("div");
		operation.setAttribute("class", "col-md-2 col-md-offset-1")
		var revise = document.createElement("img")
		revise.setAttribute("onclick", "reviseeducational(this)")
		revise.setAttribute("data-toggle", "modal")
		revise.setAttribute("href", "#educational")
		revise.setAttribute("style", "width:26px;height:26px;padding-bottom:3px")
		revise.setAttribute("src", topurl + "img/revise.png")
		var del = document.createElement("img")
		del.setAttribute("style", "width:23px;height:23px;padding-bottom:1px")
		del.setAttribute("src", topurl + "img/delect.png")
		del.setAttribute("onclick", "deleteeducational(this)")
		operation.appendChild(revise)
		operation.appendChild(del)
		div.appendChild(schoolname)
		div.appendChild(operation)
		div.appendChild(major)
		div.appendChild(education)
		div.appendChild(graduationyears)
		addEduContent.appendChild(div)
	}


	$("#educational").modal('hide');
	document.getElementById("eduCheckFormreset").click();
}

function reviseeducational(str) {
	$("#schoolname").val(str.parentNode.parentNode.querySelector("div:first-of-type").lastChild.textContent);
	$("#major").val(str.parentNode.parentNode.querySelector("div:nth-of-type(3)").lastChild.textContent);
	var education = document.getElementById("education")
	for (var i = 0; i < education.options.length; i++) {
		if (education.options[i].text == str.parentNode.parentNode.querySelector("div:nth-of-type(4)").lastChild.textContent) {
			education.options[i].selected = 'selected';
		}
	}
	var graduationyears = document.getElementById("graduationyears");
	for (var j = 0; j < graduationyears.options.length; j++) {
		if (graduationyears.options[j].text == str.parentNode.parentNode.querySelector("div:last-of-type").lastChild.textContent) {
			graduationyears.options[j].selected = "selected";
		}
	}
	document.getElementById("educational").querySelector("button[type='submit']").innerHTML = "修改";
	str.parentNode.parentNode.setAttribute("id", "changing")
}

function deleteeducational(str) {
	var addEduContent = document.getElementById("addEduContent");
	addEduContent.removeChild(str.parentNode.parentNode)
	if (addEduContent.children.length == 3) {
		addEdu.setAttribute("class", "col-md-6 col-md-offset-5")
		addEdu.querySelector("img").style.float = "left";
		addEdu.querySelector("p").style.display = "block"
			//									var p = document.createElement("p")
			//									p.innerHTML="添加教育背景";
			//									addEdu.appendChild(p)
	}
}

//添加作品
function addpoject() {
	var addProject = document.getElementById("addProject")
	var addProjectContent = document.getElementById("addProjectContent")
	if (addProject.querySelector("img").style.float != "right") {
		addProject.querySelector("p").style.display = "none";
		//									addProject.removeChild(addProject.querySelector("p"))
		addProject.setAttribute("class", "col-md-2 col-md-offset-4");
		addProject.querySelector("img").style.float = "right";
	}
	if (document.getElementById("addprojectworks").querySelector("button[type='submit']").innerHTML == "修改") {
		var changing = document.getElementById("changing")
		changing.querySelector("div:first-of-type").innerHTML = "<i style='color:#3598db;font-style:normal'>作品标题: </i>" + $("#projectname").val();
		changing.querySelector("div:last-of-type").innerHTML = "<i style='color:#3598db;font-style:normal;float:left'>作品描述: </i><span style='float:left'>" + ($("#projectintro").val()).replace(/\n/g, "<br/>") + "</span>";
		changing.querySelector("div:nth-of-type(3)").innerHTML = "<i style='color:#3598db;font-style:normal'>作品链接: </i>" + $("#projecturl").val();

		document.getElementById("addprojectworks").querySelector("button[type='submit']").innerHTML = "确定";
		changing.setAttribute("id", "")
	} else {
		var div = document.createElement("div")
		div.setAttribute("class", "col-md-12")
		div.setAttribute("style", "font-size:14px;border-bottom:dashed 1px #ccc;margin-bottom: 10px;padding:10px")
		var projectname = document.createElement("div")
		projectname.setAttribute("style", "margin-bottom:10px")
		projectname.setAttribute("class", "col-md-9")
		projectname.innerHTML = "<i style='color:#3598db;font-style:normal'>作品标题: </i>" + $("#projectname").val();
		var projecturl = document.createElement("div")
		projecturl.setAttribute("style", "margin-bottom:10px")
		projecturl.setAttribute("class", "col-md-12")
		projecturl.innerHTML = "<i style='color:#3598db;font-style:normal'>作品链接: </i>" + $("#projecturl").val();
		var projectintro = document.createElement("div")
		projectintro.setAttribute("style", "margin-bottom:10px")
		projectintro.setAttribute("class", "col-md-12")
		projectintro.innerHTML = "<i style='color:#3598db;font-style:normal;float:left'>作品描述: </i><span style='float:left'>" + ($("#projectintro").val()).replace(/\n/g, "<br/>") + "</span>";
		// 编辑按钮
		var operation = document.createElement("div");
		operation.setAttribute("class", "col-md-2 col-md-offset-1")
		var revise = document.createElement("img")
		revise.setAttribute("onclick", "revisePoject(this)")
		revise.setAttribute("data-toggle", "modal")
		revise.setAttribute("href", "#addprojectworks")
		revise.setAttribute("style", "width:26px;height:26px;padding-bottom:3px")
		revise.setAttribute("src", topurl + "img/revise.png")
		var del = document.createElement("img")
		del.setAttribute("style", "width:23px;height:23px;padding-bottom:1px")
		del.setAttribute("src", topurl + "img/delect.png")
		del.setAttribute("onclick", "deletePoject(this)")
		operation.appendChild(revise)
		operation.appendChild(del)
		div.appendChild(projectname)
		div.appendChild(operation)
		div.appendChild(projecturl)
		div.appendChild(projectintro)
		addProjectContent.appendChild(div)
	}
	$("#addprojectworks").modal('hide');
	document.getElementById("pojectCheckFormreset").click();
}

function revisePoject(str) {
	$("#projectname").val(str.parentNode.parentNode.querySelector("div:first-of-type").lastChild.textContent);
	$("#projecturl").val(str.parentNode.parentNode.querySelector("div:nth-of-type(3)").lastChild.textContent);
	$("#projectintro").val((str.parentNode.parentNode.querySelector("div:last-of-type").lastChild.innerHTML).replace(/<br>/g, "\n"));

	document.getElementById("addprojectworks").querySelector("button[type='submit']").innerHTML = "修改";
	str.parentNode.parentNode.setAttribute("id", "changing")
}

function deletePoject(str) {
	var addProjectContent = document.getElementById("addProjectContent");
	addProjectContent.removeChild(str.parentNode.parentNode)
	if (addProjectContent.children.length == 3) {
		addProject.setAttribute("class", "col-md-6 col-md-offset-5")
		addProject.querySelector("img").style.float = "left";
		addProject.querySelector("p").style.display = "block";
		//									var p = document.createElement("p")
		//									p.innerHTML="添加工作经验";
		//									addProject.appendChild(p)
	}
}

// 添加工作经验
function addworkexperience() {
	var addWork = document.getElementById("addWork");
	var addWorkContent = document.getElementById("addWorkContent");
	if (addWork.querySelector("img").style.float != "right") {
		addWork.querySelector("p").style.display = "none"
			//									addWork.removeChild(addWork.querySelector("p"))
		addWork.setAttribute("class", "col-md-2 col-md-offset-4");
		addWork.querySelector("img").style.float = "right";
	}
	if (document.getElementById("addworkexperience").querySelector("button[type='submit']").innerHTML == "修改") {
		var changing = document.getElementById("changing")
		changing.querySelector("div:first-of-type").innerHTML = "<i style='color:#3598db;font-style:normal'>公司: </i>" + $("#company").val();
		changing.querySelector("div:nth-of-type(4)").innerHTML = "<i style='color:#3598db;font-style:normal'>职位: </i>" + $("#position").val();
		changing.querySelector("div:nth-of-type(3)").innerHTML = "<i style='color:#3598db;font-style:normal'>工作时间: </i>" + $("#workinghoursstart").val() + " - " + $("#workinghoursend").val();
		changing.querySelector("div:last-of-type").innerHTML = "<i style='color:#3598db;font-style:normal;float:left'>工作内容: </i><span style='float:left'>" + ($("#workingcontent").val()).replace(/\n/g, "<br>") + "</span>";
		document.getElementById("addworkexperience").querySelector("button[type='submit']").innerHTML = "确定";
		changing.setAttribute("id", "")
	} else {
		var div = document.createElement("div")
		div.setAttribute("class", "col-md-12")
		div.setAttribute("style", "font-size:14px;border-bottom:dashed 1px #ccc;margin-bottom: 10px;padding:10px")
		var company = document.createElement("div")
		company.setAttribute("style", "margin-bottom:10px")
		company.setAttribute("class", "col-md-9")
		company.innerHTML = "<i style='color:#3598db;font-style:normal'>公司: </i>" + $("#company").val();
		var position = document.createElement("div");
		position.setAttribute("style", "margin-bottom:10px")
		position.setAttribute("class", "col-md-12")
		position.innerHTML = "<i style='color:#3598db;font-style:normal'>职位: </i>" + $("#position").val();
		var time = document.createElement("div")
		time.setAttribute("style", "margin-bottom:10px")
		time.setAttribute("class", "col-md-12")
		if($("#workinghoursend").val()==""){$("#workinghoursend").val("至今")}
		time.innerHTML = "<i style='color:#3598db;font-style:normal'>工作时间: </i>" + $("#workinghoursstart").val() + " - " + $("#workinghoursend").val();
		var workingcontent = document.createElement("div")
		workingcontent.setAttribute("style", "margin-bottom:10px")
		workingcontent.setAttribute("class", "col-md-12")
		workingcontent.innerHTML = "<i style='color:#3598db;font-style:normal;float:left'>工作内容: </i><span style='float:left'>" + ($("#workingcontent").val()).replace(/\n/g, "<br>") + "</span>";
		// 编辑按钮
		var operation = document.createElement("div");
		operation.setAttribute("class", "col-md-2 col-md-offset-1")
		var revise = document.createElement("img")
		revise.setAttribute("onclick", "reviseWorkExperience(this)")
		revise.setAttribute("data-toggle", "modal")
		revise.setAttribute("href", "#addworkexperience")
		revise.setAttribute("style", "width:26px;height:26px;padding-bottom:3px")
		revise.setAttribute("src", topurl + "img/revise.png")
		var del = document.createElement("img")
		del.setAttribute("style", "width:23px;height:23px;padding-bottom:1px")
		del.setAttribute("src", topurl + "img/delect.png")
		del.setAttribute("onclick", "deleteWorkExperience(this)")

		operation.appendChild(revise)
		operation.appendChild(del)
		div.appendChild(company)
		div.appendChild(operation)
		div.appendChild(time)
		div.appendChild(position)
		div.appendChild(workingcontent)
		addWorkContent.appendChild(div)
	}
	$("#addworkexperience").modal('hide');
	document.getElementById("workCheckFormreset").click();
}

function reviseWorkExperience(str) {
	$("#company").val(str.parentNode.parentNode.querySelector("div:first-of-type").lastChild.textContent);
	$("#position").val(str.parentNode.parentNode.querySelector("div:nth-of-type(4)").lastChild.textContent);
	$("#workinghoursstart").val((str.parentNode.parentNode.querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[0]);
	$("#workinghoursend").val((str.parentNode.parentNode.querySelector("div:nth-of-type(3)").lastChild.textContent).split(" - ")[1]);
	$("#workingcontent").val((str.parentNode.parentNode.querySelector("div:nth-of-type(5)").lastChild.innerHTML).replace(/<br>/g, "\n"))

	document.getElementById("addworkexperience").querySelector("button[type='submit']").innerHTML = "修改";
	str.parentNode.parentNode.setAttribute("id", "changing")
}

function deleteWorkExperience(str) {
	var addWorkContent = document.getElementById("addWorkContent");
	addWorkContent.removeChild(str.parentNode.parentNode)
	if (addWorkContent.children.length == 2) {
		addWork.setAttribute("class", "col-md-6 col-md-offset-5")
		addWork.querySelector("img").style.float = "left";
		addWork.querySelector("p").style.display = "block"
			//									var p = document.createElement("p")
			//									p.innerHTML="添加工作经验";
			//									addWork.appendChild(p)
	}
}

function picturesModify(str) {
	str.parentNode.parentNode.style.display = "none"
	document.getElementById("ModifySend").style.display = "block";
}

function picturesModifyCancle() {
	document.getElementById("ModifySendCancle").style.display = "block"
	document.getElementById("ModifySend").style.display = "none";
}


function thirdToSecond() {
//	if(str == "yes")
//	{
//		$("#servicerPersonalDiv").hide();
//		$("#is-resume").show();
//		scroll(0, 0)
//	}else{
		$("#servicerPersonalDiv").hide();
		$("#personalSecond").show();
		scroll(0, 0)
//	}
}


if ($("#addCIntroHidden").val() != "") {
	$("#addCIntro").val($("#addCIntroHidden").val());
}


function companythirdToSecond() {
	$("#servicerCompanyDiv").hide();
	$("#companySecond").show();
	scroll(0, 0)
}

function statusCompanythirdToSecond() {
	$("#servicerCompanyDiv").hide();
	$("#companySecond").show();
	scroll(0, 0)
}

function companyChange(str) {
	str.parentNode.parentNode.style.display = "none"
	document.getElementById("companybusinessLicense").style.display = "block"
}

function companyDelChange() {
	document.getElementById("companybusinessLicense").style.display = "none"
	document.getElementById("compangChangeCancle").style.display="block"
}

function hasClass(obj, cls) {
	return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}

function addClass(obj, cls) {
	if (!this.hasClass(obj, cls)) obj.className += " " + cls;
}

function removeClass(obj, cls) {
	if (hasClass(obj, cls)) {
		var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
		obj.className = obj.className.replace(reg, ' ');
	}
}

function toggleClass(obj, cls) {
	if (hasClass(obj, cls)) {
		removeClass(obj, cls);
	} else {
		addClass(obj, cls);
	}
}

//个人认证checkbox选中js

for (var ti = 0; ti < $$(".excelstechnologies div label").length; ti++) {
	$$(".excelstechnologies div label")[ti].onclick = function() {
		if (hasClass(this, "excelstechnologiesActive")) {
			removeClass(this, "excelstechnologiesActive")
		} else {
			addClass(this, "excelstechnologiesActive")
		}
		clearCheckNotNull("ExcelsTechnologies")
	}
}

function labelActive(str) {
	for (var j = 0; j < $$("#personalSecond div label div").length; j++) {
		$$("#personalSecond div label div")[j].style.background = "#f7f7f7";
	}
	for (var i = 0; i < $$("#personalSecond div label div div").length; i++) {
		$$("#personalSecond div label div div")[i].style.display = "none";
	}
	str.querySelector('div').style.background = "#3498dc";
	str.querySelector('div').querySelector('div').style.display = "block";
}

function labelN0fCActive(str) {
	for (var j = 0; j < $$("#companySecond div label div").length; j++) {
		$$("#companySecond div label div")[j].style.background = "#f7f7f7";
	}
	for (var i = 0; i < $$("#personalSecond div label div div").length; i++) {
		$$("#companySecond div label div div")[i].style.display = "none";
	}
	str.querySelector('div').style.background = "#3498dc";
	str.querySelector('div').querySelector('div').style.display = "block";
}

if ($("#theNumberOfHidden").val() != "") {
	if ($("#theNumberOfHidden").val() == "1") {
		$("#theNumberOfHidden").val("小于10人")
	} else if ($("#theNumberOfHidden").val() == "2") {
		$("#theNumberOfHidden").val("10~30人")
	} else if ($("#theNumberOfHidden").val() == "3") {
		$("#theNumberOfHidden").val("31~100人")
	} else {
		$("#theNumberOfHidden").val("大于100人")
	}
	var campanySizeLabel = document.querySelectorAll("#campanySize div label")
	for (var companySL = 0; companySL < campanySizeLabel.length; companySL++) {
		if (campanySizeLabel[companySL].querySelector("p").innerHTML == $("#theNumberOfHidden").val()) {
			campanySizeLabel[companySL].click();
		}
	}
}

var resumeState = false;
function resumeModify(){
	if(resumeState==false){
		$("#oldResume").css("display","none");
		$("#uploadResume").css("display","block");
		resumeState = true;
	}else{
		$("#oldResume").css("display","block");
		$("#uploadResume").css("display","none");
		resumeState = false;
	}
}

initFileIuput("#resume","",3,"is-resume")

//水平测试
function abilityBtnActive(str){
	var abilitySelect = document.getElementById("abilitySelect");
	var abilityArr = abilitySelect.querySelectorAll("div")
	var abilityArrLength = abilityArr.length;
	for(var i = 0;i<abilityArrLength;i++)
	{
		abilityArr[i].classList.remove("abilityActive")
	}
	str.classList.add("abilityActive");
	var abilityInput = document.getElementById("abilityInput");
	abilityInput.value = str.getAttribute("aid")
}

function Level(str){
	var testLevel = document.getElementById("testLevel");
	var levelArr = testLevel.querySelectorAll("div")
	var levelArrLength = levelArr.length;
	for(var i = 0;i<levelArrLength;i++)
	{
		levelArr[i].classList.remove("abilityActive")
	}
	str.classList.add("abilityActive");
	var levelInput = document.getElementById("levelInput");
	levelInput.value = str.getAttribute("lid")
}

//审核状态或认证通过状态修改重新提交认证信息
function userModification(str){
	var modifyId = str.getAttribute("typeId");

	/*$("#autheninfo").hide();
	if(modifyId==0){
		$("#is-resume").show();
	}*/
	var topURL = $("#topurl").val();

	var mURL = topURL + "home/identify/amodify?mtype=11";

	$.ajax({
		type: "GET",
		url: mURL,
		//data: paraData,
		async: false,
		success: function(data) {
			if (data.resultCode) {
				checkResult = false;
			}
		}
	});
}

function toStatus3(){
	window.location.href = "/home/identify/amodify?mtype=11";
}

function toStatus4(){
	window.location.href = "/home/identify/amodify?mtype=12";
}

function msgHide(){
	$("#reminder").css("width","0px");
}

/**Angular app**/
var app = new AngularApp('userInfoApp');
app.createService('examService', function($http) {
	return {
		queryList:{method:'GET',url:'/api/1/u/exam/history'},
		createExam:{method:'POST',url:'/api/1/u/exam'}
	};
});
app.createController('userInfoCtrl',function($scope,services){
	$scope.listData = [];

	$scope.createExam = function(){
		 var skill = document.getElementById("abilityInput").value;
		 var grade = document.getElementById("levelInput").value;
		 if(skill==""){
		 	return checkNotNull("abilitySelect",false);
		 }
		 if(grade==""){
		 	return checkNotNull("testLevel",false);
		 }

	 	$("#startTestBtn").attr("disabled","disabled")

		 var paraData = "skill=" + skill + "&grade=" + grade;
		app.setParam({
			skill:skill,
			grade:grade
		});
		services.get('examService').createExam(function(data){
					if (data.resultCode == 0) {
						$("#startTestBtn").attr("disabled",false)
						showMessageBox("已成功发起能力评测，请登录您的邮箱" + data.email + "点击链接开始答题", "能力评测");
						$scope.listData = data.listData;
						$scope.listDataMsg = "";
					} else {
						showMessageBox(data.errorMsg, "能力评测");
			}
		});
	};

	$scope.showTabContent = function() {
		showTabContentInner("ability");
		services.get('examService').queryList(function(data){
			$scope.listData = data;
			if(data.length==0)$scope.listDataMsg= "暂无记录";
		});
	};
});
