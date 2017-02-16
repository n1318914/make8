function isMobile(str) {
    //验证手机号码,
    var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$/;
    
    if (!patrn.exec(str)) return false;
    
     return true;
}

function warningMsg(strId,startWarningStr,endWarningStr){
	$(strId).html(startWarningStr);
	$(strId).css("display","block");
}

function warningMsgShow(strId,Content){
	$(strId).html(Content);
	$(strId).css("display","block");
	console.log(Content)
}

function checkInput(){
	if($("#name").val()==""){
		warningMsgShow("#nameMsg","姓名不能为空");
		return false;
	}else{
		$("#nameMsg").css("display","none");
	}
	
	if($("#TelPhone").val()=="")
	{
		warningMsgShow("#TelPhoneMsg","联系方式不能为空");
		return false;
	}else if(isMobile($("#TelPhone").val())==false){
		warningMsg("#TelPhoneMsg","请输入11位大陆地区手机号码","联系方式不能为空");
		return false;
	}else{
		$("#TelPhoneMsg").css("display","none");
	}

	if($("#demand").val()==""){
		warningMsgShow("#demandMsg","需求不能为空");
		return false;
	}else{
		$("#demandMsg").css("display","none");
	}
}

function successFun(){
	$("#successMsg").css("display","none");
}

function doServiceReservation(){
	
	checkInput();
	
	if(checkInput()==false)
	{
		return false;
	}

	var phoneNumber = $("#TelPhone").val();
	var name = $("#name").val();
	var requestDesc = $("#demand").val();
	
	var mURL = "/api/wx/request";
	var pData = "contactNumber=" + phoneNumber + "&contactsName=" + name  + "&content=" + requestDesc + "&type=pc";
	
	$.ajax({
		type:"POST",
		url:mURL,
		async:false,
        data:pData,
        success:function(data){
        	if(data.resultCode == 0){
        		$("#successMsg").css("display","block");
        		$("#TelPhone").val("");
        		$("#name").val("");
        		$("#demand").val("");
        		setTimeout(successFun,10000);
        	}else{
        		$("#reservationMsg").html(data.errorMsg);
        	}
        }
	});
}

function navTop()
{
	var nav = document.getElementById("nav");
	var aIcon = document.querySelectorAll(".nav-ul li a");
	if(document.documentElement.scrollTop>0 || window.pageYOffset>0 || document.body.scrollTop>0)
	{
		nav.style.borderBottom="solid 1px #ccc";
		for(var i = 0;i<aIcon.length;i++)
		{
			aIcon[i].style.color="black";
		}
	}else if(document.body.scrollTop==0 || window.pageYOffset==0 || document.documentElement.scrollTop==0){
		nav.style.borderBottom="none";
		for(var i = 0;i<aIcon.length;i++)
		{
			aIcon[i].style.color="white";
		}
	}
}

window.addEventListener("scroll",navTop);