$(document).ready(function() {
	$("#emailForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			mailList:{
				required: "请输入邮件人"
			},
			title:{
				required: "请输入标题"
			},
			content:{
				required: "请输入内容"
			}
		}
	});
});

function doSend(){
	var mailList = $("#mailList").val();
	var mailBccList = $("#mailBccList").val();
	var time = $("#time").val();
	var copies = $("#copies").val();
	var title = encodeURI($("#title").val());
	var content = encodeURI($("#content").val());

	var murl = "/api/2/u/emailsend";

	var paraData = "mailList=" + mailList + "&mailBccList=" + mailBccList + "&time=" + time + "&copies=" + copies + "&title=" + title + "&mailContent=" + content;
	
	var timeTick = 0;
	
	var isComplete = false;
	
	$("#mailSendConsole").html("邮件发送中，请耐心等候...");
	
	setInterval(function(){
		if(!isComplete){
			$("#mailSendConsole").html("邮件发送中，请耐心等候(" + timeTick + "秒)...");	
			++timeTick;
		}
	},1000);
	
//    content = encodeURIComponent(content);
//	content = encodeURIComponent(content);
	
	$.ajax({
		"type": "POST",
		"data": paraData,
		"async": true,
		"url": murl,
		"success": function(data) {
			isComplete = true;
			
			$("#mailSendConsole").html("发送耗时："  + timeTick + "秒<br>");
			
			if(data.resultCode == 0){
				
				$("#mailSendConsole").append(data.msg);
			}else{
				$("#mailSendConsole").append("邮件发送失败,请检查接受者的邮箱是否正确！");
			}
			
		}
	});
}
