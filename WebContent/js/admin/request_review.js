//修改按钮
function modifyBtn(){
	var locationUrl = window.location;
	var checkUrl = /.*\?(.*\=.*)/;
	window.location.href="/home/p/modify?"+locationUrl.toString().match(checkUrl)[1];
}

//类型选择
function typeSelect(str){
	str.querySelector("img").classList.toggle("active");
	var recruitedMsg = document.getElementById("recruitedMsg");
	if(recruitedMsg.classList.contains('active')) recruitedMsg.classList.remove("active");
}

//当不通过时不通过理由div显示
function failReasonShow(){
	var failReason = $("#fail-reason");
	var fail = document.getElementById("fail-checked");
	if(fail.checked)
	{
		$("#gitPanel").slideUp();
		failReason.slideDown();
	}else{
		$("#gitPanel").slideDown();
		failReason.slideUp();
	}
}

function checktextAreaNull(id){
	var warning = document.querySelector(".warning");
	var fail = document.getElementById("fail-checked");
	var str = document.getElementById(id);
	if(str.value=="" && fail.checked)
	{
		warning.style.display="block";
		return false;
	}
}

//表单提交
function submitform(){
	var Recruiters = [];//招聘人员
	var recruitersType = document.querySelectorAll(".recruited div");//招聘人员类型
	var warning = document.querySelector(".warning");
	var fail = document.getElementById("fail-checked");
	var apiUrl = fail.checked?"/api/2/p/reject":"/api/2/p/pass";
	var locationUrl = window.location;
	var checkUrl = /.*\?(.*\=.*)/;
	var Str = locationUrl.toString().match(checkUrl)[1];
	var recruitersTypeLength = recruitersType.length;
	
	
	for(var i = 0;i<recruitersTypeLength;i++)
	{
		if(recruitersType[i].querySelector("img").classList.contains("active")==true)
		{
			Recruiters.push(recruitersType[i].parentNode.querySelector("p").getAttribute("value"));
		}
	}
	
	if(fail.checked)
	{
		if($("#fail-text").val()=="" && fail.checked)
		{
			warning.style.display="block";
			return false;
		}
		Str += "&reason=" + $("#fail-text").val();
	}
	
	if(Recruiters.length == 0 && !fail.checked)
	{
		document.getElementById("recruitedMsg").classList.add("active");
		return false;
	}else{
		Recruiters = Recruiters.join(",");
	}
	
	if(!fail.checked){
		if($("#repo_name").val().trim()==""){
			$("#repo_name").css("border","1px solid red");
			$("#repo_name").after("<span style='color:red'>非空选项</span>");
			return;
		}
		var reg=/^[0-9a-zA-Z]+$/;
		var isOnlyKeyandNum = reg.test($("#repo_name").val())
		if(!isOnlyKeyandNum){
			$("#repo_name").css("border","1px solid red");
			$("#repo_name").after("<span style='color:red'>只能为数字和字母</span>");
		}
		
		Str +="&repo_name="+$("#repo_name").val().trim()+"&description="+$("#description").val().trim() + "&enrollRole=" + Recruiters;
	}

	$.ajax({
		type:"post",
		url:apiUrl,
		async:false,
		data:Str,
		success:function(data){
			if(data.resultCode == 0){
				showMessageDialog("项目审核",data.msg,function(){window.location.reload();});
			}else{
				showMessageDialog("项目审核",data.errorMsg);
			}


		}
	});
}
$("#repo_name").bind("focus blur",function(){
	$(this).css("border","1px solid #ccc");
	$(this).next("span").remove();
});

//项目转让
function submitTransform(){
		
	var proId = $("#proId").val();
	var mobile = $("#mobile").val();
	var assignReason = $("#assignReason").val();
	var Str = "proId="+proId+"&mobile="+mobile+"&assignReason="+assignReason;
	console.log(Str);
	$.ajax({
		type:"post",
		url:"/api/2/selfrun/p/assign",
		async:false,
		data:Str,
		success:function(data){
			console.log("callback data:"+JSON.stringify(data));	
			if(data.resultCode == 0){
				showMessageDialog("项目转让",data.msg,function(){window.location.reload();});
			}else{
				showMessageDialog("项目转让",data.errorMsg);
			}
		}
	});
}

$(document).ready(function() {
//	$("#assignForm").validate({
//	        debug:true  //启动调试， 不调用form请求
//  });
	
	jQuery.validator.addMethod("isMobilePhoneNumber", function() {
		var mobile = $("#mobile").val();
		var chkResult = isMobile(mobile);
		return chkResult;
	}, "手机号码不正确");
	
	
	jQuery.validator.addMethod("phoneNumExisting", function() {
		var phoneNumber = $("#mobile").val();
		var murl = "/api/u/check/exist?userName=" + phoneNumber;
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
		return (!chkResult);
	}, "该用户不存在");

	
	var mobileValidate = $("#assignForm").validate({
		errorElement:"span",
		messages: {
			mobile: {
				required: "请输入手机号码"
			},
			assignReason: {
				minlength: "转让原因不能少于8个字符"
			}
		},
		errorPlacement:function(error,element){
			if(element.is("#mobile")){
				error.appendTo("#mobileMsg");
			}else if(element.is("#assignReason")){
				error.appendTo("#reasonMsg");
			}
		}
	});
	
	$("#myModal").draggable({
	    handle: ".modal-header",   
	    cursor: 'move',   
	    refreshPositions: false  
	}); 
});