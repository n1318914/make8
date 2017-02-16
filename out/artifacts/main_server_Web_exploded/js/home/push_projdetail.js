$(document).ready(function() {
	$("#enrollForm").validate({
		errorElement: "span",
		ignore: []
	});
});


function lanuchJoinPlanModel(el){
	var isUserInfoComplete = $("#h_IsUserInfoComplete").val();

	if(isUserInfoComplete == 0){
				showMessageDialog("项目报名","为了严格控制项目质量，需要您完善个人信息，<a href = '/home/userinfo' target='_blank'>立即完善<a/>");
				return;
	}

	var isUserInfoIdentifyPassed = $("#h_IsIdentifiedPassed").val();

	if(isUserInfoIdentifyPassed == 0){
		showMessageDialog("项目报名","为了严格控制项目质量，需要您完成认证信息，<a href = '/home/userinfo' target='_blank'>立即认证<a/>");
		return;
	}

   $("#enroll").show();
}

function enrollCheckBox(str){
	str.querySelector("img").classList.toggle('push_active');
	var checkBoxDivs = document.querySelectorAll(".checkBoxDiv");
	var checkBoxDivLenth = checkBoxDivs.length;
	var enrollRole = [];
	for(var i = 0;i<checkBoxDivLenth;i++)
	{
		if(checkBoxDivs[i].querySelector("img").classList.contains("push_active"))
		{
			enrollRole.push(checkBoxDivs[i].parentNode.querySelector("div:last-of-type").getAttribute("value"));
		}
	}
	
	if(enrollRole.length == 0)
	{
		document.getElementById("enrollCheckBoxMsg").classList.add("push_active");
	}else{
		document.getElementById("enrollCheckBoxMsg").classList.remove("push_active");
	}
}

function enroll() {
	var pid = $("#projectId").val();
	var murl = "/api/1/p/join/projectSelf";
	var checkBoxDivs = document.querySelectorAll(".checkBoxDiv");
	var checkBoxDivLenth = checkBoxDivs.length;
	var enrollRole = [];
	for(var i = 0;i<checkBoxDivLenth;i++)
	{
		if(checkBoxDivs[i].querySelector("img").classList.contains("push_active"))
		{
			enrollRole.push(checkBoxDivs[i].parentNode.querySelector("div:last-of-type").getAttribute("value"));
		}
	}
	
	if(enrollRole.length == 0)
	{
		document.getElementById("enrollCheckBoxMsg").classList.add("push_active");
		return false;
	}
	
	var dataStr = "projectId=" + pid + "&joinPlan=" + $("#reason").val() + "&enrollRole=" + enrollRole.join(",");

  $.ajax({
	"type": "POST",
	"async": "false",
	"url": murl,
	"data":dataStr,
	"success": function(data) {
		if(data.success==0){
			$("#enroll").modal("hide");
			showMessageBox("报名成功,技术顾问稍后将与您取得联系","项目报名",window.location.href);
		}else if(data.success==-1){
			showMessageDialog("项目报名","为了严格控制项目质量，需要您完善个人信息并通过认证后方能参与项目，<a href = '/home/userinfo' target='_blank'>立即完善<a/>")
		}else{
			showMessageBox("系统异常,请联系管理员","项目报名",window.location.href);
		}
	}
});
}
