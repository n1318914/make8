$(function() {
	//若擅长技术为空则隐藏
	if ($(".AuthenticationInformationDiv").html() == "") {
		$(".AuthenticationInformationDiv").css("display", "none")
	}

	// jQuery.validator.addMethod("isAlipayAccount", function(value, element, param) {
	// 	var account = value;
	// 	return isEmail(account) || isMobile(account);
	// }, "请输入正确的支付账号");
	//
	// $("#resumeForm").validate({
	// 	rules: {
	// 		accountName: {
	// 			isAlipayAccount: true,
	// 			required: true
	// 		}
	// 	},
	// 	errorElement: "span",
	// 	messages: {
	// 		accountName: {
	// 			isAlipayAccount: "请输入正确的支付账号",
	// 			required: "支付账号不能为空"
	// 		}
	// 	},
	// 	errorPlacement: function(error, element) {
	// 		if (element.is("#accountName")) {
	// 			$("#accountNameErr").append(error);
	// 		}
	// 	}
	// });
})

// 提交表单
function resumeForm() {
	var mURL = "/api/2/identify/resume";

	var data = $("#resumeForm").serialize();

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
	var checkUid = window.location.search;
	var uid = checkUid.match(/\?uid=(.*)/);

	if (isresume == "yes") {
		freelanceType = "freelanceType=0";
	} else {
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

	ajStr = freelanceType + candoStr + mainAbilityStr + otherMainAbility + workExperience + project + EduBackground + "&uid=" + uid[1] + "&introduction=" + $("#profile").val() + "&accountNum=" + $("#accountName").val();

	$.ajax({
		dataType: "json",
		type: "POST",
		async: false,
		data: ajStr,
		url: mURL,
		success: function(data) {
			if (data.resultCode == 1) {
				showMessageBox(data.errorMsg, "录入失败");
			} else {
				//				showMessageBox();
				showMessageDialog("提交成功", "认证提交", function() {
						location.reload()
					})
					// 执行操作....
			}
		}
	});
}

function checkNotNull(divStr, tf) {
	var div = document.getElementById(divStr)
	div.querySelector("span:last-of-type").style.display = "block";
	div.scrollIntoView(tf);
	return false;
}

//企业信息非空验证
var theNumberOfNum = 0;
var companyworktypeNum = 0;
var companytechnologyNum = 0;
var companyProductTypeNum = 0;

function checkCompany() {
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
		return true;
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

//校验公司表单并提交
function companyForm() {
	var data = $("#company_form").serialize();
	var operate = 'modify';
	type = "&identifyInfo.category=1";
	var arr = data.split("&");
	var checkUid = window.location.search;
	var uid = checkUid.match(/\?uid=(.*)/);
	//公司项目经验
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

	for (var w = 0; w < arr.length; w++) {
		if (arr[w].substr(0, 12) == "theNumberOf=") {
			companySize = "companySize=" + arr[w].substr(12)
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

	companyAjStr = companySize + CWT + CT + otherCaseType + PT + otherProductType + companyintro + addCPE + iranking +
	               type + "&operate=" + operate + "&uid=" + uid[1];

	if (checkCompany() == true) {
		$.ajax({
			type: "post",
			url: '/api/2/identify/resume',
			data:companyAjStr,
			async: false,
			success: function(response) {
				if (data.resultCode == 1) {
					showMessageBox(data.errorMsg, "录入失败");
				} else {
					//				showMessageBox();
					showMessageDialog("提交成功", "认证提交", function() {
							location.reload()
						})
						// 执行操作....
				}
			}
		});
	}
}

//校验个人表单
function checkResume() {
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
		resumeForm();
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

function addSelectClass(str) {
	str.classList.toggle("excelstechnologiesActive")
}

function resumeInput() {
	var identifyCategory = $("#identifyCategory").val();
	document.getElementById("autheninfo").classList.remove("active");
	if (identifyCategory == 0) {
		document.getElementById("ResumeEntry").classList.add("active");
	} else {
		document.getElementById("companySecond").classList.add("active");
	}
}

//公司认证信息修改
function companyInput() {
	document.getElementById("autheninfo").classList.remove("active");
	document.getElementById("ResumeEntry").classList.add("active");
}

function resumeLabelActive(str) {
	for (var j = 0; j < $$("#ResumeEntry div label div").length; j++) {
		$$("#ResumeEntry div label div")[j].style.background = "#f7f7f7";
	}
	for (var i = 0; i < $$("#ResumeEntry div label div div").length; i++) {
		$$("#ResumeEntry div label div div")[i].style.display = "none";
	}
	str.querySelector('div').style.background = "#3498dc";
	str.querySelector('div').querySelector('div').style.display = "block";
}

/**Angular app**/
var app = new AngularApp('userReviewApp');
app.createService('reviewService', function($http) {
	return {
		queryList: {
			method: 'GET',
			url: '/api/1/u/join/list'
		}
	};
});
app.createController('userReviewCtrl', function($scope, services) {
	$scope.listData = [];

	$scope.loadData = function(id) {
		app.setParam({
			userId: id
		});
		services.get('reviewService').queryList(function(data) {
			$scope.listData = data;
			if (data.length == 0) $scope.listDataMsg = "暂无项目参与记录";
		});
	};
});
