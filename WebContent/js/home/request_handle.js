$(function(){
	//时间选择
	generateDatePickerWithoutStartDate(".planStartTime","planStartTimeDiv","yyyy-mm-dd");
	generateDatePickerWithoutStartDate(".planEndTime","planEndTimeDiv","yyyy-mm-dd");
	//加载阶段执行人
	loadInviter();

	//模态窗支持拖拉
	$("#addLogDiv").draggable({
	    handle: ".modal_title",
	    cursor: 'move',
	    refreshPositions: false
	});

	$("#progressFeedback").draggable({
	    handle: ".modal_title",
	    cursor: 'move',
	    refreshPositions: false
	});

	$("#plan").draggable({
	    handle: ".modal_title",
	    cursor: 'move',
	    refreshPositions: false
	});
	//判断项目附件是否为空
	var projectStatus = $("#projectStatus").val();


	if(projectStatus != -1 && projectStatus != 0){
	//	checkAttachmentNull($("#projectAttachment").val(),".projectAttachment");
	}

})

$(document).ready(function() {

	$("#addLogDivForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			objectOriented: {
				required: "请选择公开对象"
			},
			journalContent: {
				required: "请输入日志内容"
			}
		},
		errorPlacement: function(error, element) {
			error.insertAfter(element);
		}
	});

	$("#checkVerify").validate({
		ingnor:[],
		rules:{
			checkRemark:{
				required:true,
				minlength:10
			}
		},
		messages: {
			checkRemark: {
				required: "备注不能为空",
				minlength:"至少输入10个字符"
			}
		},
		errorElement: "span",
		errorPlacement: function(error, element) {
			error.appendTo(element);
			$("#checkRemarkMsg").append(error);
		}
	});

	$("#checkAccount").validate({
		ingnor:[],
		rules:{
			proAccount:{
				required:true,
				digits:true
			}
		},
		messages: {
			proAccount: {
				required: "请输入项目金额",
				digits:"请输入合法的整数"
			}
		},
		errorElement: "span",
		errorPlacement: function(error, element) {
			error.appendTo(element);
			$("#accountErr").append(error);
		}
	});

	$("#progressFeedbackForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			objectOriented: {
				required: "请输入进展反馈"
			}
		},
		errorPlacement: function(error, element) {
			error.insertAfter(element);
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

	jQuery.validator.addMethod("checkTime", function() {
		var companyStartElId = "planStartTime";
		var companyEndElId = "planEndTime";

		return checkTime(companyStartElId, companyEndElId);
	});

	var avalidPrice = -2;     //一个初始默认值
	jQuery.validator.addMethod("checkPrice", function() {
		var projectId = $("#projectId").val();
		var price = $("#price").val();
		if(avalidPrice == -2){
			$.ajax({  //验证时避免重复发送请求
				type:"post",
				url:"/api/1/u/avalidprice",
				async:false,
				data:"projectId="+projectId,
				success:function(data){
					avalidPrice = data.avaliPrice;
				}
			});
		}
		if(avalidPrice == -1){    //表示该项目没有明确的金额
			return true;
		}else{
			return price <= avalidPrice;
		}
	});
	$("#planForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			planStartTime: {
				required: "请输入开始日期"
			},
			price:{
				checkPrice:"金额超出可用资金范围"
			},
			planEndTime: {
				required: "请输入结束日期",
				checkTime: "开始时间必须小于结束时间"
			}
		},errorPlacement: function(error, element) {
			if(element.is("#planStartTime")) {
				error.appendTo($("#planStartTimeMsg"))
			}else if(element.is("#planEndTime")) {
				error.appendTo($("#planEndTimeMsg"))
			}else if(element.is("#price")) {
				error.appendTo($("#priceMsg"))
			}else {
				error.insertAfter(element);
			}
		}
	});
});

function checkStatusRadio(ele){
	var eles =  document.getElementsByClassName("project-type-img");
	for(var i=0;j=eles.length,i<j;i++){
		eles[i].classList.add("active");
	}
	$(ele).find("img")[0].classList.remove("active")
}
//TODO
//项目计划阶段：用户提交验收
function submitVerify(ele){
	var stepId = ele.getAttribute("planId");
	var projectId = $("#projectId").val();
	var param = "stepId="+stepId+"&projectId="+projectId;
	$.ajax({
		type:"post",
		url:"/api/1/u/submitVerify",
		async:false,
		data:param,
		success:function(data){
			var message = data.msg?data.msg:data.errorMsg;
			showMessageDialog("提示", message, function(){
    			window.location.reload();
			});
		}
	});
}

var stepId,projectId;
function toggleCheckVerifyBox(ele){
	//弹出摸态框
	$("#checkVerifyBox").modal("toggle");
	stepId = ele.getAttribute("planId");
	projectId = $("#projectId").val();

}
//雇主确认验收
function checkVerify(){
	//弹出摸态框
	var isPass =$("#unPass")[0].classList.contains("active")?true:false;
	var reason = $("#checkRemark").val();
	var param = "stepId="+stepId+"&projectId="+projectId+"&reason="+reason+"&isPass="+isPass;
	if(isPass){
		showConfirmDialog("提示", "验收通过后，系统会自动将阶段金额转给开发人员，请确认阶段已完成?",function(){
		$.ajax({
			type:"post",
			url:"/api/1/u/checkVerify",
			async:false,
			data:param,
			success:function(data){
				var message = data.msg?data.msg:data.errorMsg;
				showMessageDialog("提示", message, function(){
	    			window.location.reload();
				});
			}
		});
	});
	}else{
		$.ajax({
			type:"post",
			url:"/api/1/u/checkVerify",
			async:false,
			data:param,
			success:function(data){
				var message = data.msg?data.msg:data.errorMsg;
				showMessageDialog("提示", message, function(){
	    			window.location.reload();
				});
			}
		});
	}
}

//检测项目附件是否存在
function checkAttachmentNull(str,msg){
	if(str==null||str==undefined||str==""||str=="[]")
	{
		$(msg).html("无附件")
	}
}

//进入完成状态
function enterFinishStatus(){
	showConfirmDialog("提示", "确认将项目进入完成状态?", function(){
		$.ajax({
			type:"post",
			url:"/api/1/selfrun/p/go2complete",
			async:false,
			data:{projectId:$("#projectId").val()},
			success:function(){
				window.location.reload();
			}
		});
	})
}

var phType = "";
var planEle = "";
function planHandleType(str,ele){
	if(str == "modify"){
		$(".isDelay").css("display","block")
	}else{
		$(".isDelay").css("display","none")
	}

//	var statusIndex = document.getElementById("status").selectedIndex;
//	var status = document.getElementById("status").options[statusIndex].value;
	var statusArr = document.getElementById("status").querySelectorAll("option");
	var statusArrLen = statusArr.length;

	var coperArr = document.getElementById("coper").querySelectorAll("option");
	var coperArrLen = coperArr.length;

	var isDelayArr = document.getElementById("isDelay").querySelectorAll("option");
	var isDelayArrLen = isDelayArr.length;

	phType = str;
	if(str == "modify"){
		var eleTableTr = ele.parentNode.parentNode.parentNode;
		var statusId;
		planEle = ele.getAttribute("modifyId");

		//修改框数据
		$("#planStartTime").val(eleTableTr.querySelector("td:nth-of-type(3)").innerHTML);
		$("#planEndTime").val(eleTableTr.querySelector("td:nth-of-type(4)").innerHTML);
		$("#price").val(eleTableTr.querySelector("td:nth-of-type(5)").innerHTML);
		$("#describe").val(checkParagraph(eleTableTr.querySelector("td:nth-of-type(2)").querySelector(".reasonForCompetenceContent").innerHTML));

		for(var i = 0;i<statusArrLen;i++)
		{
			if(statusArr[i].innerHTML==eleTableTr.querySelector("td:nth-of-type(6)").innerHTML)
			{
				statusArr[i].setAttribute("selected","true")
			}
		}

		for(var j=0;j<coperArrLen;j++){
			if(coperArr[j].innerHTML==eleTableTr.querySelector("td:nth-of-type(8)").innerHTML)
			{
				coperArr[j].setAttribute("selected","true")
			}
		}

		for(var x = 0;x<isDelayArrLen;x++){
			if(isDelayArr[x].innerHTML==eleTableTr.querySelector("td:nth-of-type(7)").innerHTML)
			{
				isDelayArr[x].setAttribute("selected","true")
			}
		}
	}else{
		clearProjectPlan();//清空输入框
	}
}

function createPlan(){

	//clearProjectPlan();//清空新增框数据
	var statusIndex = document.getElementById("status").selectedIndex;
	var status = document.getElementById("status").options[statusIndex].value;
	var coperIndex = document.getElementById("coper").selectedIndex;
	var coper = document.getElementById("coper").options[coperIndex].value;
	var isDelayIndex = document.getElementById("isDelay").selectedIndex;
	var isDelay = document.getElementById("isDelay").options[isDelayIndex].value;
	var stagePlan = document.querySelector(".stagePlan");

	var params;
	var mUrl = phType == "modify" ? "/api/1/u/editProjectPlan" : "/api/1/u/saveProjectPlan";
	if(phType == "modify"){
		params ="projectId="+$("#projectId").val() + "&stepId=" + planEle + "&status=" + status + "&stepDesc=" + $("#describe").val() + "&startTime=" + $("#planStartTime").val() + "&endTime=" + $("#planEndTime").val() + "&price=" + $("#price").val() + "&executorId=" + coper + "&isDelayed=" + isDelay;
	}else{
		params ="projectId="+$("#projectId").val() + "&status=" + status + "&stepDesc=" + $("#describe").val() + "&startTime=" + $("#planStartTime").val() + "&endTime=" + $("#planEndTime").val() + "&price=" + $("#price").val() + "&executorId=" + coper;
	}
	$.ajax({
		data:params,
		type:"post",
		url:mUrl,
		async:false,
		success:function(response){

			var isDelayedName = "";
			switch(response.projectInSelfRunPlanVo.isDelayed){
				case 0:
					isDelayedName = "否";
				break;
				case 1:
					isDelayedName = "是";
				break;
				default:
				break;
			}

			if(phType == "modify"){
				var modifyEle = planEle;//修改target的tr
				var modifyTr = stagePlan.querySelectorAll("tr");
				var modifyTrLen = modifyTr.length;

				for(var i=0;i<modifyTrLen;i++)
				{
					if(modifyTr[i].getAttribute("planId")==modifyEle)
					{
						modifyTr[i].querySelector("td:nth-of-type(2) .reasonForCompetenceContent").innerHTML = response.projectInSelfRunPlanVo.stepDesc;
						modifyTr[i].querySelector("td:nth-of-type(3)").innerHTML = response.projectInSelfRunPlanVo.startTime;
						modifyTr[i].querySelector("td:nth-of-type(4)").innerHTML = response.projectInSelfRunPlanVo.endTime;
						modifyTr[i].querySelector("td:nth-of-type(5)").innerHTML = response.projectInSelfRunPlanVo.price;
						modifyTr[i].querySelector("td:nth-of-type(6)").innerHTML = response.projectInSelfRunPlanVo.statusName;
						modifyTr[i].querySelector("td:nth-of-type(7)").innerHTML = isDelayedName;
						modifyTr[i].querySelector("td:nth-of-type(8)").innerHTML = response.projectInSelfRunPlanVo.executorName;
					}
				}

				$("#plan").modal("hide");
			}else{
				var tr = document.createElement("tr");
				tr.setAttribute("planId",response.projectInSelfRunPlanVo.stepId);
				tr.setAttribute("onclick","showReasonForCompetence(this)");
				tr.innerHTML = "<td class='text-center'>"+ response.projectInSelfRunPlanVo.stepId +"</td>" +
								"<td class='text-center reasonForCompetence reasonForCompetence_padding'><div class='reasonForCompetenceContent'>"+ response.projectInSelfRunPlanVo.stepDesc +"</div></td>" +
								"<td class='text-center' >" + response.projectInSelfRunPlanVo.startTime + "</td>" +
								"<td class='text-center' >" + response.projectInSelfRunPlanVo.endTime + "</td>" +
								"<td class='text-center' >" + response.projectInSelfRunPlanVo.price + "</td>" +
								"<td class='text-center' >" + response.projectInSelfRunPlanVo.statusName + "</td>" +
								"<td class='text-center' >" + isDelayedName + "</td>" +
								"<td class='text-center' >" + response.projectInSelfRunPlanVo.executorName + "</td>" +
								"<td class='text-center' planId='" +response.projectInSelfRunPlanVo.stepId+ "'><div class='col-md-6 col-sm-6 col-xs-12 padding'><a style='color: red;cursor: pointer;' onclick='removeProjectPlan(this)'>删除</a></div><div class='col-md-6 col-sm-6 col-xs-12 padding'><a style='color: black;' data-toggle='modal' modifyId='"+ response.projectInSelfRunPlanVo.stepId +"' onclick='planHandleType(\"modify\",this)' href='#plan'>修改</a></div></td>";
				stagePlan.appendChild(tr);
				$("#plan").modal("hide");
			}
		}
	})
}

function developerModify(str){
	$("#developerHidden").val(str)
}

function addChosenDev(){
	var mUrl = "/api/1/selfrun/p/adddev";
	var consoles = document.querySelectorAll(".console");
	var consoleLen = consoles.length;
	var functions = document.querySelectorAll(".functions");
	var functionLength = functions.length;
	var chooseIndex = [];
	for(var i=0;i<functionLength;i++){
		if(functions[i].classList.contains("functionsActive") == true){
			chooseIndex.push(functions[i].getAttribute("value"))
		}
	}

	if(chooseIndex.length <= 0){
		showMessageBox("请选择角色！","提示");
		return;
	}

	var params = "projectId="+ $("#projectId").val() + "&chosenRole=" + chooseIndex.join(",") + "&developerId=" + $("#developerHidden").val();
	$.ajax({
		data:params,
		type:"post",
		url:mUrl,
		async:false,
		success:function(data){
			var chosenDev = data.chosenDev.chosenRoleList;
			var chosenDevLen = chosenDev.length;
			for(var j = 0;j<consoleLen;j++)
			{
				var role = "";
				var roleName = "";
				for(var x = 0;x<chosenDevLen;x++){
					role += "<div class='rolefunctions'>" +
						"<div class='btn-default-choose'>"+ chosenDev[x].name +"</div>" +
					"</div>";
				}
				if(consoles[j].getAttribute("developerId")==$("#developerHidden").val())
				{
					consoles[j].innerHTML = "<div>" +
												"<button class='btn-choose btn-choose-cancle' onclick='cancelBubble("+ consoles[j].getAttribute("developerId") +",this)'>撤销</button>"+
											"</div>" + role;
				}
			}
			$("#functionChoice").modal("hide");
		}
	});
}

function checkParagraph(str){
	if(/<br>/.test(str)||/<br\/>/.test(str))
	{
		str = str.replace(/<br>/g,"\n");
	}
	return str;
}

function choosefunctions(str){
	str.classList.toggle("functionsActive");
	var num = 0;
	var functions = document.querySelectorAll(".functions");
	var functionsLen = functions.length;
	for(var i=0;i<functionsLen;i++){
		if(functions[i].classList.contains("functionsActive")==true){
			num++;
		}
	}
	if(num==0){
		$("#functionChoiceMsg").css("display","black")
	}else{
		$("#functionChoiceMsg").css("display","none")
	}
}

function showReasonForCompetence(str){
	var btnDefaultChoose = str.querySelectorAll(".btn-default-choose");
	var btnDefaultChooseLength = btnDefaultChoose.length;
	var reasonForCompetenceContent = str.querySelectorAll(".reasonForCompetenceContent");
	for(var i=0;i<btnDefaultChooseLength;i++)
	{
		btnDefaultChoose[i].classList.toggle("btn-active");
	}
	for(var j = 0;j<reasonForCompetenceContent.length;j++){
		reasonForCompetenceContent[j].classList.toggle("reasonForCompetenceActive");
	}
}

function cancelBubble(data,str){
	developerModify(data);//锁定撤销对象
	var enrollDev = data;
	window.event? window.event.cancelBubble = true : evt.stopPropagation();
	var params = "projectId="+$("#projectId").val() + "&developerId=" + $("#developerHidden").val();
	$.confirm({
			title: '操作',
			content: "确定删除该条日志",
			confirmButton: '确认',
			cancelButton: '取消',
			confirmButtonClass: 'btn-primary',
			cancelButtonClass: 'btn-danger',
			backgroundDismiss:false,
			animation: 'scale',
			confirm: function(){
				$.ajax({
					type:"post",
					data:params,
					url:"/api/1/selfrun/p/deletedev",
					async:false,
					success:function(data){
						if(data.resultCode==0)
						{
							str.parentNode.parentNode.innerHTML = "<a class='btn btn-primary btn-choose' onclick='developerModify("+ enrollDev +")' data-toggle='modal' href='#functionChoice'>选择</a>";
						}else{
							showMessageBox("分配角色职能失败,请联系工作人员及时处理","提示")
						}
					}
				});
			}
 });
}

var logStatus = "";
function newLog(str){
	$("#journalContent").val("");
	logStatus = str;
}

var modifyId = "";
function modifyLogDiv(str,status){
	$("#journalContent").val(checkParagraph(str.parentNode.parentNode.parentNode.querySelector(".logContentDiv").innerHTML));
	modifyId = str.getAttribute("modifyId");
	logStatus = status;
	for(var i=0;i<document.getElementById("objectOriented").querySelectorAll("option").length;i++)
	{
		if(document.getElementById("objectOriented").querySelectorAll("option")[i].getAttribute("value")==str.getAttribute("logpermissionid"))
		{
			document.getElementById("objectOriented").querySelectorAll("option")[i].selected = true;
		}
	}
}

function removeLog(str){
	var mURL = "/api/1/p/log/delete";
	var removeId = str.getAttribute("projectId");
	$.confirm({
			title: '提示',
			content: "确定删除该条日志",
			confirmButton: '确认',
			cancelButton: '取消',
			confirmButtonClass: 'btn-primary',
			cancelButtonClass: 'btn-danger',
			backgroundDismiss:false,
			animation: 'scale',
			confirm: function(){
			  $.ajax({
			  	type:"post",
			  	url:mURL,
			  	data: "logId=" + removeId,
			  	async:false,
			  	success:function(data){
					if(data.resultCode == 0)
					{
						str.parentNode.parentNode.parentNode.remove();
					}else{
						showMessageBox(data.msg,"操作");
					}
			  	}
			  })
			}
 });
}

function timeStampFormat(ms){
	var ms = new Date(ms);
	var year = ms.getFullYear();
	var month = ms.getMonth()+1 < 10 ? '0'+(ms.getMonth()+1) : ms.getMonth()+1;
	var day = ms.getDate();
	var hours = ms.getHours() > 9 ? ms.getHours() : "0" + ms.getHours();
	var min = ms.getMinutes() > 9 ? ms.getMinutes() : "0" + ms.getMinutes();
	return year+"-"+month+"-"+day+" "+hours+":"+min;
}

function addLog(){
	var journal = document.querySelector(".journal");
	var journalTbody = document.querySelector(".journal .tableTr:nth-child(2)");
	var objectOriented = document.getElementById("objectOriented");
	var journalContent = document.getElementById("journalContent");
	var index = objectOriented.selectedIndex;
	var mUrl = (logStatus == "create") ? "/api/1/p/log/add" : "/api/1/p/log/modify";
	var dataStr = "";
	if(logStatus == "create")
	{
		dataStr = "logPermission=" + objectOriented.options[index].value + "&logContent=" + $("#journalContent").val() +
		          "&projectId=" + $("#projectId").val() + "&creatorId=" + $("#currentLoginUserId").val();
		$.ajax({
			data:dataStr,
			type:"post",
			url:mUrl,
			async:false,
			success:function(data){
				if(data == null)
				{
				 	showMessageBox("新增失败","日志");
				}else{
					var tmp = document.createElement("tr");
					tmp.setAttribute("class","tableTr");
					tmp.setAttribute("onclick","showReasonForCompetence(this)");
					tmp.innerHTML = "<td class='text-center'>"+ timeStampFormat(data.createTime) +"</td>" +
								"<td class='text-center'>"+ data.creator.name +"</td>" +
								"<td class='text-center reasonForCompetence'><div class='logContentDiv reasonForCompetenceContent'>"+ data.logContent +"</div></td>" +
								"<td class='text-center'><div class='col-md-6 col-sm-6 col-xs-12 padding'><a projectId='"+ data.id +"' style='color: red;cursor: pointer;' onclick='removeLog(this)'>删除</a></div><div class='col-md-6 col-sm-6 col-xs-12 padding'><a class='modifyObj' logpermissionid='"+ data.logPermission +"' style='color: black;' data-toggle='modal' modifyId='"+ data.id +"' onclick=\"modifyLogDiv(this,'modify')\" href='#addLogDiv'>修改</a></div></td>";
					journal.querySelector("tbody").insertBefore(tmp,journal.querySelector(".tableTr:nth-of-type(2)"));
					document.getElementById("addLogDivCancle").click();
				}
			}
		});
	}else{
		dataStr = "id=" + modifyId + "&logPermission=" + objectOriented.options[index].value + "&logContent=" + $("#journalContent").val();
		$.ajax({
			data:dataStr,
			type:"post",
			url:mUrl,
			async:false,
			success:function(data){
				if(data == 0)
				{
					showMessageBox("修改失败","日志");
				}else{
					var logId = logStatus;
					var modifyObj = document.querySelectorAll(".modifyObj");
					var modifyObjLength = modifyObj.length;
					for(var i = 0;i<modifyObjLength;i++){
						if(modifyObj[i].getAttribute("modifyId")==modifyId){
							modifyObj[i].parentNode.parentNode.parentNode.querySelector("td:nth-of-type(3) div").innerHTML = data.logContent;
						}
					}
					document.getElementById("addLogDivCancle").click();
				}
			}
		});
	}

}

var isAccess = true;
var accessType;
var iconOF = false;

var agt = navigator.userAgent.toLowerCase();
var is_op = (agt.indexOf("opera") != -1);
var is_ie = (agt.indexOf("msie") != -1) && document.all && !is_op;
function ResizeTextarea(a,row){
    if(!a){return}
    if(!row)
        row=5;
    var b=a.value.split("\n");
    var c=is_ie?1:0;
    c+=b.length;
    var d=a.cols;
    if(d<=20){d=40}
    for(var e=0;e<b.length;e++){
        if(b[e].length>=d){
            c+=Math.ceil(b[e].length/d)
        }
    }
    c=Math.max(c,row);
    if(c!=a.rows){
        a.rows=c;
    }
}
function showMore(str){
	if(iconOF)
	{
		str.querySelector(".dropDownIcon").style.transform="rotate(225deg)";
		document.querySelector(".dropDownContent").style.height="50px"
		iconOF = false
	}else{
		str.querySelector(".dropDownIcon").style.transform="rotate(45deg)";
		document.querySelector(".dropDownContent").style.height="600px"
		iconOF = true
	}
}
document.onkeydown = function(event) {
    var target, code, tag;
    if (!event) {
        event = window.event; //针对ie浏览器
        target = event.srcElement;
        code = event.keyCode;
        if (code == 13) {
            tag = target.tagName;
            if (tag == "TEXTAREA") { return true; }
            else { return false; }
        }
    }
    else {
        target = event.target; //针对遵循w3c标准的浏览器，如Firefox
        code = event.keyCode;
        if (code == 13) {
            tag = target.tagName;
            if (tag == "INPUT") { return false; }
            else { return true; }
        }
    }
};
/**
 * 权限控制
 */
function allPropertyController(accessType){
	if(accessType==-1)return;
//	$(".developer-content").find(".planUser-del").each(function(){
//		if($(this).parents(".developer").attr("class").indexOf("developer-model")>=0)return;
//		$(this).remove();
//	});
//	$("#projectList").find(".attach-group").each(function(){
//
//	});
}
function setVisibleByPro(accessType,element,type){
	if(accessType==-1)return;
	//附件模块
	if(type=='attachment'){
		if(accessType==1){
			//普通开发人员
			element.remove();
		}
	}
}
function doPublish() {
	var type = $("#price").find("option:selected").text();
	var content = $("#content").val();
	var contactsName = $("#contactsName").val();
	var contactNumber = $("#contactNumber").val();
	var peroid = $("#peroid").val();

	var remark = "priceRange=" + type + ",period=" + peroid;
	var murl = "/api/wx/request";
	var paraData = "type=request" + "&contactsName=" + contactsName + "&contactNumber=" + contactNumber + "&content=" + content + "&remark=" + remark;

	$.ajax({
		"type" : "POST",
		"data" : paraData,
		"async" : "false",
		"url" : murl,
		"success" : function(data) {
			if (data.resultCode != 0) {
				//alert(data.errorMsg);
        		showMessageDialog("项目发布", data.errorMsg);
			} else {
				location.href = data.msg;
			}
		}
	});
}
//获取名字
function getUserNameAuto(user){
	if(user.category==1)return user.companyName;
	else return user.name;
}
/**
 * 文件处理
 */
//var fileListData = new Array();


/**
 *
 * @param file js文件对象
 * @param fileselector JQuery文件元素对象
 * @param fileListSelector JQuery附件列表对象 ftl中的file-list对应的id
 * @param fileUploadInfo JQuery bootstrap提示框
 */
function UpladFile(file,fileselector,fileListSelector,fileUploadInfo,url,updateCall) {
    var fileObj = file.files[0]; // js 获取文件对象
    var FileController = url;
    var fileName = fileObj.name;
    // FormData 对象
    var form = new FormData();

    form.append("author", "hooyes");                        // 可以增加表单数据

    form.append("file", fileObj);                           // 文件对象
    // XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();

    xhr.open("post", FileController, true);

    xhr.onload = function () {

    };
    xhr.send(form);

    xhr.onreadystatechange = function(){
    	if(xhr.readyState == 4 && xhr.status == 200){
        var data = xhr.responseText;
        if(data){
        	$(".file-caption-name").html("");
        	fillAttach(fileselector,fileListSelector,fileUploadInfo,fileName,data,updateCall);
        }
	}
    };
}

/**
 * 填充附件common func
 */
function fillAttach(fileselector,fileListSelector,fileUploadInfo,fileName,data,updateCall){
	var fileListJson = fileListSelector.find(".attachment").val()==undefined||fileListSelector.find(".attachment").val().trim()==""?"[]":fileListSelector.find(".attachment").val();


	var fileListData = eval('('+fileListJson+')');//列表数组
	var fileAttach = {};//附件对象
	var idx = fileListSelector.find(".attach-group").length-1;
	var newAttach = fileListSelector.find(".attach-model").clone(true,true);
	fileListSelector.append(newAttach);
	newAttach.removeClass("attach-model");
	newAttach.attr("idx",idx);
	newAttach.attr("fileName",fileName);
	newAttach.attr("path",data);
	newAttach.find(".attach").html(fileName);
	newAttach.show();
	//删除附件
	newAttach.find(".attach-del").click(function(){
		var fileObj = $(this).parents(".attach-group");
		showConfirmDialog("提示", "确认删除附件"+fileObj.find(".attach").html()+"吗?", function(){
			fileAttach = {};
			fileListData = new Array();
			var idx = fileObj.attr("idx");
			var display = 0;
			fileListSelector.find(".attach-group").each(function(){
				if($(this).attr("idx")!=idx&&$(this).attr("class").indexOf("attach-model")<0){
		        	fileAttach['display'] = display;
		        	fileAttach['fileName'] = $(this).attr("fileName");
		        	fileAttach['path'] = $(this).attr("path");
		        	fileListData[display] = fileAttach;
		        	fileAttach = {};
		        	display++;
				}
			});
			fileListSelector.find(".attachment").val(JSON.stringify(fileListData));
			fileObj.remove();
			updateCall(fileListSelector);
    	});
	});
	//下载附件
	newAttach.find(".attach").click(function(){
		window.open($(this).parents(".attach-group").attr("path"));
	});
	fileAttach['display'] = idx;
	fileAttach['fileName'] = fileName;
	fileAttach['path'] = data;

	fileListData[idx] = fileAttach;
	fileAttach = {};
	fileListSelector.find(".attachment").val(JSON.stringify(fileListData));
	showUploadMessageBox(fileselector,fileUploadInfo,"上传成功!", 1);
	//更新附件记录
	updateCall(fileListSelector);
	setTimeout(function(){fileUploadInfo.fadeOut();},1200);
}
function checkSize(fileElement,fileUploadInfo,size){
	var maxsize = size*1024*1024;//2M
	var  browserCfg = {};
	var ua = window.navigator.userAgent;
	if (ua.indexOf("MSIE")>=1){
	    browserCfg.ie = true;
	}else if(ua.indexOf("Firefox")>=1){
		browserCfg.firefox = true;
	}else if(ua.indexOf("Chrome")>=1){
		browserCfg.chrome = true;
	}

	try{
        var obj_file = fileElement;
        var filesize = 0;
        if(browserCfg.firefox || browserCfg.chrome ){
            filesize = obj_file.files[0].size;
        }else if(browserCfg.ie){
            var obj_img = document.getElementById('tempimg');
            obj_img.dynsrc=obj_file.value;
            filesize = obj_img.fileSize;
        }
        if(filesize==-1){
        	showUploadMessageBox($("#fileselect"),fileUploadInfo,"附件大小不符合要求!");
            return false;
        }else if(filesize>maxsize){
			showUploadMessageBox($("#fileselect"),fileUploadInfo,"附件大小不能超过"+size+"M!");
            return false;
        }else{
            return true;
        }
    }catch(e){
    	showUploadMessageBox($("#fileselect"),fileUploadInfo,"上传异常!");
    }
    return false;
}

function isCommonFile(fileElement,fileUploadInfo,size){
	var fileName = fileElement.val();
	if(fileName.trim()=="")return;
	var extStart=fileName.lastIndexOf(".");
	var ext=fileName.substring(extStart,fileName.length).toUpperCase();
	if(ext!=".XLS"&&ext!=".XLSX"&&ext!=".DOC"&&ext!=".DOCX"&&ext!=".PDF"&&ext!=".ZIP"&&ext!=".RAR"&&ext!=".PPT"&&ext!=".PPTX"&&
     ext!=".JPG"&&ext!=".PNG"&&ext!=".BMP"&&ext!=".JPEG"&&ext!=".GIF"){
		showUploadMessageBox(fileElement,fileUploadInfo,"附件限于doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar、jpg、png、jpeg、bmp、gif格式");
		return false;
	}else {
		var isComfort = checkSize(fileElement[0],fileUploadInfo,size);
		if(!isComfort){
			return false;
		}
	}
	return true;
}

//更新项目附件记录
function uploadProjectAttachment(fileListSelector){
	var param ="projectId="+$("#projectId").val()+"&attachment="+fileListSelector.find(".attachment").val();
	var murl = "/api/1/u/updateAttachment";
	$.ajax({
        "dataType" : "json",
        "type" : "POST",
        "async": "false",
        "data" : param,
        "url" : murl,
        "success" : function(data){

        }
   });
}

//加载附件列表
function loadAttachmentList(fileselector,fileListSelector,updateCall){
	var fileListJson = fileListSelector.find(".attachment").val();
	var fileListData = eval('('+fileListJson+')');
	if(!fileListData) return;
	var fileAttach = {};
	for(var i=0;i<fileListData.length;i++){
    	var newAttach = fileListSelector.find(".attach-model").clone(true,true);
    	fileListSelector.append(newAttach);
    	newAttach.removeClass("attach-model");
    	newAttach.attr("idx",i);
    	newAttach.attr("fileName",fileListData[i].fileName);
    	newAttach.attr("path",fileListData[i].path);
    	newAttach.find(".attach").html(fileListData[i].fileName);
    	newAttach.show();
    	//删除附件
    	newAttach.find(".attach-del").click(function(){
    		var fileObj = $(this).parents(".attach-group");
    		showConfirmDialog("提示", "确认删除附件"+fileObj.find(".attach").html()+"吗?", function(){
    			fileAttach = {};
    			fileListData = new Array();
    			var idx = fileObj.attr("idx");
    			var display = 0;
    			fileListSelector.find(".attach-group").each(function(){
    				if($(this).attr("idx")!=idx&&$(this).attr("class").indexOf("attach-model")<0){
    		        	fileAttach['display'] = display;
    		        	fileAttach['fileName'] = $(this).attr("fileName");
    		        	fileAttach['path'] = $(this).attr("path");
    		        	fileListData[display] = fileAttach;
    		        	fileAttach = {};
    		        	display++;
    				}
    			});
    			fileListSelector.find(".attachment").val(JSON.stringify(fileListData));
    			fileObj.remove();
    			updateCall(fileListSelector);
        	});
    	});
    	//下载附件
    	newAttach.find(".attach").click(function(){
    		window.open($(this).parents(".attach-group").attr("path"));
    	});
	}
}
/**
 * 模式对话框
 */
function showConfirmDialog(title,message,callback){
	BootstrapDialog.show({
        title:title,
        message:message,
        animate:false,
        draggable:true,
        autodestroy:false,
        closable: false,
        type:"",
        size:"",
        closeByBackdrop:false,
        closeByKeyboard:false,
        buttons:[{
        	label:"确定",
        	action: function(dialog) {
        		dialog.close();
        		callback();
        	}
        },
        {
        	label:"取消",
        	action: function(dialog) {
        		dialog.close();
        	}
        }]
   });
}
function showMessageDialog(title,message,callback){
	BootstrapDialog.show({
        title:title,
        message:message,
        animate:false,
        draggable:true,
        autodestroy:false,
        closable: false,
        type:"",
        size:"",
        closeByBackdrop:false,
        closeByKeyboard:false,
        buttons:[{
        	label:"确定",
        	action: function(dialog) {
        		dialog.close();
        		callback();
        	}
        }]
   });
}
function showUploadMessageBox(fileselector,fileUploadInfo,message,step){
	fileUploadInfo.find(".close").show();
	fileselector.attr("disabled",false);
	$(".btn-file").css("background-color","#3c8ec5");
	$(".btn-file").css("border","1px solid #3c8ec5");
	if(step==undefined||step==-1){
		fileselector.val("");
		fileUploadInfo.removeClass("alert-info");
		fileUploadInfo.removeClass("alert-success");
		fileUploadInfo.addClass("alert-danger");
	}else if(step==0){
		fileUploadInfo.removeClass("alert-danger");
		fileUploadInfo.removeClass("alert-success");
		fileUploadInfo.addClass("alert-info");
		fileUploadInfo.find("strong").html("提示:");
		fileUploadInfo.find(".close").hide();
		message +="<i class='fa fa-spinner fa-spin' style=''></i><i class='fa fa-stop' style='float:right;cursor:pointer;'></i>";
		$(".btn-file").css("background-color","#ccc");
		$(".btn-file").css("border","1px solid #ccc");
		fileselector.attr("disabled",true);
	}else if(step==1){
		fileselector.val("");
		fileUploadInfo.removeClass("alert-info");
		fileUploadInfo.removeClass("alert-danger");
		fileUploadInfo.addClass("alert-success");
		fileUploadInfo.find("strong").html("上传提示:");
		message +="<i class='fa fa-check-circle-o' style='float:right;'></i>";
	}
	fileUploadInfo.find(".info").html(message);
	fileUploadInfo.find(".info>.fa-stop").click(function(){
		showConfirmDialog("提示", "确认停止上传?", function(){
			fileselector.removeAttr("disabled");
			$(".btn-file").css("background-color","#3498DB");
			$(".btn-file").css("border","1px solid #3498DB");
			fileUploadInfo.hide();
		});
	});
	fileUploadInfo.fadeIn();
	$(".file-preview").hide();
}
/**
 * 文件上传初始化
 * @param fileselector
 */
function generateFileUploadModel(fileselector,fileListSelector,fileUploadInfo,url,uploadCall){
	//上传附件
	fileselector.change(function(){
		var max = parseInt(fileListSelector.attr("max")==undefined?3:fileListSelector.attr("max"));
		if(fileListSelector.find(".attach-group").length-1>=max){
			showUploadMessageBox(fileselector,fileUploadInfo,"最多上传"+max+"个项目附件");
			return;
		}
		var isFile = isCommonFile($(this),fileUploadInfo,20);
		if(isFile){
			showUploadMessageBox(fileselector,fileUploadInfo,"上传中,请稍后...", 0);
			if(uploadCall==undefined)uploadCall = function(){

			};
			UpladFile($(this)[0],$(this),fileListSelector,fileUploadInfo,url,uploadCall);
		}
	});
}
/**
 * 提交邀约对象
 */
function projectSelfRunSubmit(){
	var param = "";
	var murl = "";
	var isPush =$("#inviteDialog").attr("isPush")!=undefined;
	if(isPush){
		var allMailStr = "";
		var count = 0;
		$(".servicer").each(function(){
			if($(this).attr("class").indexOf("model")>=0)return;
			//连接email
			if($(this).attr("selected")=='selected'){
				if(count==0)allMailStr +=$(this).find(".email").val();
				else allMailStr +=(","+$(this).find(".email").val());
				count++;
			}
		});
//		alert(allMailStr);
		murl = "/api/1/p/push/project?allMailStr="+allMailStr;
		param = $("#projectSelfRunform").serialize();
		param +="&projectId="+$("#projectId").val();

	}else{
		murl = "/api/1/u/saveSelfprovider";
		param = $("#projectSelfRunform").serialize();
		param +="&projectId="+$("#projectId").val();
	}
//	alert(param);
	$.ajax({
        "dataType" : "json",
        "type" : "POST",
        "async": "false",
        "data" : param,
        "url" : murl,
        "success" : function(data){
        	var message = "保存成功!";
        	if(isPush)message = "推送成功!";
        	showMessageDialog("操作提示", message, function(){
        		$("#inviteDialog").modal("hide");
        		if(!isPush){
        			$("#search").trigger("click");
            		reloadPlanUser(data.planUserInfoList);

            		//刷新项目状态
	        		if(data.planUserInfoList != null && data.planUserInfoList.length > 0){
	        			$("#projectStatus").html("开发中");
	        		}
        		}
        	});
        }
   });
}
//刷新开发人员
function reloadPlanUser(planUserList){
	//刷新加载开发人员
	$(".developer-content").find(".developer").each(function(){
		if($(this).attr("class").indexOf("developer-model")>=0)return;
		$(this).remove();
	});
	if(planUserList)
	for(var i=0;i<planUserList.length;i++){
		var id = planUserList[i].id;
		var nickname = planUserList[i].name;
    	var developer = $(".developer-content").find(".developer-model").clone(true,true);
		developer.removeClass("developer-model");
		developer.attr("uid",id);//绑定ID
		var roleName = $(".addedrole>option[value='"+planUserList[i].role+"']").html();
		developer.find(".planUser").attr("title",roleName);
		var name = getUserNameAuto(planUserList[i]);
		developer.find(".planUser").attr("nickname",nickname);
		developer.find(".planUser-del").click(function(){
			delPlanUser($(this).parents(".developer").attr("uid"));
		});
		developer.find(".planUser").html(name);
		developer.css("margin-right","5px");
		developer.insertBefore(".developer-content>#invite");
		developer.show();
	}
	$(".planUser").click(function(){
		window.open("/public/m/"+$(this).attr("nickname"));
	});
}
//删除开发人员
function delPlanUser(uId){
	var param = $("#projectSelfRunform").serialize();
	param +="&projectId="+$("#projectId").val()+"&uid="+uId;;
	var murl = "/api/1/u/deleteProjectUser";
	$.ajax({
        "dataType" : "json",
        "type" : "POST",
        "async": "false",
        "data" : param,
        "url" : murl,
        "success" : function(data){
        	if(data.status != "1"){
        		showConfirmDialog("提示", "系统错误", function(){

        		});
        	}else{
        		reloadPlanUser(data.planUserInfoList);

        		//刷新项目状态
        		if(data.planUserInfoList == null || data.planUserInfoList.length == 0){
        			$("#projectStatus").html("待启动");
        		}
        	}

        }
	});
}
/**
 * 项目计划
 */
//表单验证
var isPermit = true;
function showErrorForColMdRow(message,element){
	element.parents(".col-md-12").append("<div class='col-md-12 custom-error' style='padding:0;padding-top:5px;display:none;'><div class='alert alert-danger' style='margin-bottom:0;' role='alert'>"+message+"</div></div>");
	element.parents(".col-md-12").find(".custom-error").fadeIn();
	setTimeout(function(){element.parents(".row").find(".custom-error").fadeOut();element.parents(".col-md-12").find(".custom-error").remove();},1200);
}

//查询项目计划
function loadPlan(){
	$.ajax({
        "dataType" : "json",
        "type" : "POST",
        "async": "false",
        "data" : "&projectId="+$("#projectId").val(),
        "url" : "/api/1/u/loadProjectPlan",
        "success" : function(data){
        	if(data.projectInSelfRunPlanVoList!=undefined&&data.projectInSelfRunPlanVoList!=null){
        		var planList = data.projectInSelfRunPlanVoList;
        		$(".plan-step-table").find(".plan-table-data").each(function(){
        			if($(this).attr("class").indexOf("plan-table-data-model")>=0)return;
        			$(this).remove();
        		});
        		if(planList.length==0){
        			$(".plan-step-table").append("<div class='row plan-table-data' style='text-align:center;'>暂无计划</div>");
        		}
        		for(var i=0;i<planList.length;i++){
        			var newTr = $(".plan-table-data-model").clone(true,true);
        			newTr.removeClass("plan-table-data-model");
        			newTr.show();
        			newTr.attr("stepId",planList[i].stepId);
        			newTr.find(".stepTd").html(planList[i].step);
        			newTr.find(".stepDescTd").html(planList[i].stepDesc);
        			newTr.find(".endTimeTd").html(planList[i].endTime);
        			newTr.append("<input type='hidden' class='startTime' value='"+planList[i].startTime+"'/>");
        			var creatorName = "";
        			if(planList[i].creatorCategory==1)creatorName = planList[i].creatorCompanyName;
        			else creatorName = planList[i].creatorName;
        			var executorName = "";
        			if(planList[i].executorCategory==1)executorName = planList[i].executorCompanyName;
        			else executorName = planList[i].executorName;
        			newTr.find(".executorTd").html(executorName);
        			newTr.find(".executorTd").attr("executorId",planList[i].executorId);
        			newTr.find(".cashTd").find(".price").html(planList[i].price);
        			newTr.find(".statusTd").html(planList[i].statusName);
        			newTr.find(".statusTd").attr("status",planList[i].status);
        			newTr.appendTo(".plan-step-table");
        		}
        	}
        }
	});
}
//提交项目计划
function savePlan(){
	dateCompare($("#startTime"), $("#endTime"));
//	alert($("#coper").val()==undefined);
	if(!isAccess){
		return;
	}
	var isEdit = $("#planDialog").attr("isE dit")!=undefined;
	var param ="";
	var murl = "";
	if(isEdit){
		//编辑
		param = $("#projectPlanform").serialize();
		param +="&projectId="+$("#projectId").val();
		murl = "/api/1/u/editProjectPlan";
	}else{
		//新增
		param = $("#projectPlanform").serialize();
		param +="&projectId="+$("#projectId").val();
		murl = "/api/1/u/saveProjectPlan";
	}

	$.ajax({
        "dataType" : "json",
        "type" : "POST",
        "async": "false",
        "data" : param,
        "url" : murl,
        "success" : function(data){
        	var message = "新增项目计划成功!";
        	if(isEdit)message = "修改项目计划成功!";
    		showMessageDialog("提示", message, function(){
    			loadPlan();
    			$("#planDialog").modal("hide");
    		});
        }
	});
}
//删除项目计划
function delPlan(row){
	var param ="&projectId="+$("#projectId").val()+"&stepId="+row.attr("stepId");
	var murl = "/api/1/u/deleteProjectPlan";
	showConfirmDialog("提示", "确认删除?", function(){
		$.ajax({
	        "dataType" : "json",
	        "type" : "POST",
	        "async": "false",
	        "data" : param,
	        "url" : murl,
	        "success" : function(data){
	    		showMessageDialog("提示", "删除项目计划成功!", function(){
	    			loadPlan();
	    		});
	        }
		});
	});
}
//加载执行人列表
function loadInviter(executorId){
	//加载表单数据
	var param = "&projectId="+$("#projectId").val();
	var murl = "/api/1/u/loadProjectUser";
	$.ajax({
        "dataType" : "json",
        "type" : "POST",
        "async": "false",
        "data" : param,
        "url" : murl,
        "success" : function(data){
        	var planUserList = data.planUserInfoList;
        	if(planUserList){
        		$("#coper").html("");
        		for(var i=0;i<planUserList.length;i++){
	        		var name = getUserNameAuto(planUserList[i]);
	        		$("#coper").append("<option value='"+planUserList[i].id+"'>"+name+"</option>");
	        	}
        	}
//  		if(executorId!=undefined)$("#planDialog").find("select[name='executorId']>option[value='"+executorId+"']").attr("selected",true);
        }
	});
}

/**
 * 反馈
 */
//添加反馈
function addMonitor(){
	var isEdit = $("#monitorDialog").attr("isEdit")!=undefined;
	var param ="";
	var murl = "";
	if(isEdit){
		//编辑
		param = $("#projectMonitorform").serialize();
		param +="&projectId="+$("#projectId").val();
		murl = "/api/1/u/editProjectMonitor";
	}else{
		//新增
		param = $("#projectMonitorform").serialize();
		param +="&projectId="+$("#projectId").val();
		murl = "/api/1/u/saveProjectMonitor";
	}
	$.ajax({
        "dataType" : "json",
        "type" : "POST",
        "async": "false",
        "data" : param,
        "url" : murl,
        "success" : function(data){
        	var message = "反馈成功!";
        	if(isEdit)message= "修改反馈信息成功!";
    		showMessageDialog("提示", message, function(){
    			loadMonitorList();
    			$("#monitorDialog").modal("hide");
    		});
        }
	});
}

//删除创建的项目模型
function removeModel(){
	var e = document.querySelectorAll(".new-file");
	var lengths = e.length
	for(var j=0;j<lengths;j++){
		e[j].remove();
	}
}

//删除指定项目模型
var removeNum;
function removeTargetModel(str){
	showConfirmDialog("提示", "确认删除?", function(){
		//var attachmentsArry = removeNum;
		var fileListJson = $("#attachment").val()==undefined||$("#attachment").val().trim()==""?"[]":$("#attachment").val();

	    var attachmentsArry = eval('('+fileListJson+')');//列表数组

		var attachmentsArryLength = attachmentsArry.length;
		var displayNum = str.getAttribute("display");
		var pathStr = str.getAttribute("path");
		var arrJSON = new Array();
		var tempAttachment;

		for(var i=0;i<attachmentsArryLength;i++){
//			var len = attachmentsArry[i].split(",")[0].length;
//			var removeId = attachmentsArry[i].split(",")[0].substr(len-1,1)
//			if(removeId != Num)
//			{
//				arrJSON.push(attachmentsArry[i])
//			}
			tempAttachment = attachmentsArry[i];

			var removeId = tempAttachment.path;

			if(removeId != pathStr && tempAttachment.display != displayNum){
				arrJSON.push(attachmentsArry[i]);
			}
		}
		//$("#attachment").val("["+arrJSON.join(",")+"]");

		//removeNum = arrJSON;
		$("#attachment").val(JSON.stringify(arrJSON));

		str.parentNode.remove();
	});
}

var modifyFbId;
function createProjectFb(str,sid){
	clearProgressFeedback();//初始化
	$("#progressFeedbackHidden").val(str);

	if(str == "modify")
	{
		removeModel();
		var fileObj = sid.parentNode.parentNode.parentNode.querySelectorAll("td:nth-of-type(4) .reasonForCompetenceContent");
		var fileObjLength = fileObj.length;
		var fileArr = new Array();
		var fileJson;

		//初始化数据
		$("#monitorDesc").val("");

		modifyFbId = sid.parentNode.parentNode.parentNode.getAttribute("did");
		$("#monitorDesc").val(checkParagraph(sid.parentNode.parentNode.parentNode.querySelector("td:nth-of-type(3) .reasonForCompetenceContent").innerHTML));
		var monitorFileList = document.getElementById("monitorFileList");
		for(var i=0;i<fileObjLength;i++){
			fileJson = JSON.parse(fileObj[i].getAttribute("file"));
			//fileArr.push(fileJson)
			fileArr.push(fileJson);

			//创建项目模型
			var div = document.createElement("div");
			div.setAttribute("class","btn-group attach-group new-file");
			div.setAttribute("idx",i);
			div.setAttribute("filename",fileJson.fileName);
			div.setAttribute("path",fileJson.path);
			div.setAttribute("style","display:block")
			div.innerHTML = "<button type='button' onclick=\"window.open('"+fileJson.path+"')\" class='btn btn-primary btn-xs attach'>"+ fileJson.fileName +"</button>" +
					   		"<button type='button' path='"+ fileJson.path +"' display='"+ fileJson.display +"' onclick='removeTargetModel(this)' class='btn btn-primary btn-xs attach-del'><span class='fa fa-times'></span><span class='sr-only'>Toggle Dropdown</span></button>";
			monitorFileList.appendChild(div);
		}

		$("#attachment").val(JSON.stringify(fileArr));

		removeNum = fileArr;
	}else{
		$("#monitorDesc").val("");
	}
}

function createProgressFeedback(){
	var murl = $("#progressFeedbackHidden").val() == "modify" ? "/api/1/u/editProjectMonitor" : "/api/1/u/saveProjectMonitor";
	if($("#progressFeedbackHidden").val() == "modify"){
		var files = $("#attachment").val();
		var filesJson = files.replace(/\\/g,"");

		var params = "monitorDesc=" + $("#monitorDesc").val() + "&attachment=" + $.trim(filesJson) + "&stepId=" + modifyFbId  + "&projectId=" + $("#projectId").val();
	}else{
		var params = "monitorDesc=" + $("#monitorDesc").val() + "&attachment=" + $("#attachment").val() + "&projectId=" + $("#projectId").val();
	}
	$.ajax({
		type:"post",
		url:murl,
		data:params,
		async:false,
		success:function(data){
			var progressFeedbackDiv = document.querySelector(".progressFeedbackDiv");//进展反馈Div
			var progressFeedbackTr = document.querySelector(".progressFeedbackDiv").querySelectorAll("tr");
			var progressFeedbackTrLen = progressFeedbackTr.length;
			var message = "反馈成功!";
        	if(murl == "modify") message= "修改反馈信息成功!";
			showMessageDialog("提示", message, function(){
    			$("#progressFeedback").modal("hide");
    			//添加新增记录在目标table下
    			//$("#attachment").val();

    			if($("#progressFeedbackHidden").val() == "modify"){

					//同步修改后的数据
    				var dataAttachment = data.projectInSelfRunMonitorVo.attachment.replace(/&quot;/g,"\"");
					var dataAttachmentJson = dataAttachment == "" ? "" : JSON.parse(dataAttachment);
					var dataAttachmentJsonLen = dataAttachmentJson.length;
					var tmpStr = "";
					for(var x=0;x<dataAttachmentJsonLen;x++){
						tmpStr += "<div class='reasonForCompetenceContent' file='"+ JSON.stringify(dataAttachmentJson[x]) +"'>" +
									"<a style='display: block;width: 100%;height: 100%;' href='"+ dataAttachmentJson[x].path +"'>"+dataAttachmentJson[x].fileName+"</a>" +
								 "</div>";
					}
					for(var j =0;j<progressFeedbackTrLen;j++)
					{
						if(progressFeedbackTr[j].getAttribute("dId") == data.projectInSelfRunMonitorVo.stepId){
							progressFeedbackTr[j].querySelector(".monitorDescModify").innerHTML = data.projectInSelfRunMonitorVo.monitorDesc;
							progressFeedbackTr[j].querySelector(".reasonForCompetenceModify").innerHTML = tmpStr;
						}
					}
					//初始化编辑框
					clearProgressFeedback();
    			}else{
    				//同步新增后数据
    				var dataAttachment = data.projectInSelfRunMonitorVo.attachment.replace(/&quot;/g,"\"");
					var dataAttachmentJson = dataAttachment == "" ? "" : JSON.parse(dataAttachment);
					var dataAttachmentJsonLen = dataAttachmentJson.length;
					var tmpStr = "";
					for(var i=0;i<dataAttachmentJsonLen;i++){
						tmpStr += "<div class='reasonForCompetenceContent' file='"+ JSON.stringify(dataAttachmentJson[i]) +"'>" +
									"<a style='display: block;width: 100%;height: 100%;' href='"+ dataAttachmentJson[i].path +"'>"+dataAttachmentJson[i].fileName+"</a>" +
								 "</div>"
					}
    				var cTr = document.createElement("tr");
    				cTr.setAttribute("dId",data.projectInSelfRunMonitorVo.stepId);
    				cTr.setAttribute("onclick","showReasonForCompetence(this)");
    				if($("#currentLoginUserId").val()!=2)
    				{
					cTr.innerHTML = "<td class='text-center'>"+ data.projectInSelfRunMonitorVo.createTime +"</td>" +
						"<td class='text-center'>"+ data.projectInSelfRunMonitorVo.name +"</td>" +
						"<td class='text-center reasonForCompetence'><div class='monitorDescModify reasonForCompetenceContent'>"+ data.projectInSelfRunMonitorVo.monitorDesc +"</div></td>" +
						"<td class='text-center'>" +
							"<div class='reasonForCompetence reasonForCompetenceModify'>" +
								tmpStr +
							"</div>" +
						"</td>";
    				}else{
    					cTr.innerHTML = "<td class='text-center'>"+ data.projectInSelfRunMonitorVo.createTime +"</td>" +
						"<td class='text-center'>"+ data.projectInSelfRunMonitorVo.name +"</td>" +
						"<td class='text-center reasonForCompetence'><div class='monitorDescModify reasonForCompetenceContent'>"+ data.projectInSelfRunMonitorVo.monitorDesc +"</div></td>" +
						"<td class='text-center'>" +
							"<div class='reasonForCompetence reasonForCompetenceModify'>" +
								tmpStr +
							"</div>" +
						"</td>" +
						"<td class='text-center'><div class='col-md-6 col-sm-6 col-xs-12 padding'><a style='color: red;cursor: pointer;font-size: 14px;' onclick='delMonitor(this)'>删除</a></div><div class='col-md-6 col-sm-6 col-xs-12 padding'><a style='color: black;' data-toggle='modal' onclick=\"createProjectFb('modify',this)\" href='#progressFeedback'>修改</a></div></td>";
    				}

    				progressFeedbackDiv.appendChild(cTr);
					//初始化编辑框
					clearProgressFeedback();
    			}
    		});
		}
	});
}
//验证
function checkAccount(){
	var account = $("#proAccount").val();
	if(!/^\d+$/g.test(account) || !account){
		$("#accountErr").css("display","block");
		$("#confirm-btn").attr("onclick","");
		return false;
	}else{
		$("#accountErr").css("display","none");
		$("#confirm-btn").attr("onclick","entryDevelopment()");
		return true;
	};
}
//将数字转化为金钱形式   200,000
function cuter(str){
	var len = str.length, str2 = '', max = Math.floor((len-1) / 3);
	for(var i = 0 ; i < max ; i++){
	var s = str.slice(len - 3, len);
	str = str.substr(0, len - 3);
	str2 = (',' + s) + str2;
	len = str.length;
	}
	str += str2;
	return str;
}
//进入开发者状态
function entryDevelopment(){
	//首先需要输入项目金额才能确认进入开发
	if(!checkAccount()){
		return;
	};
	var account = $("#proAccount").val()?$("#proAccount").val():0;
	account = cuter(account);
	showConfirmDialog("提示", "确认进入开发状态?", function(){
		var mUrl = "/api/1/selfrun/p/go2dev";
		$.ajax({
			type:'post',
			data:{projectId:$("#projectId").val(),proAccount:account},
			url:mUrl,
			success:function(data){
				if(data.errorMsg == 2){
					showMessageBox("您还没有选择开发人员","提示");
				}else{
					window.location.reload();
				}
			}
		})
	})
}

//清空进展反馈提交或修改后内容
function clearProgressFeedback(){
	$("#attachment").val("");
	var attachGroup = document.querySelectorAll(".attach-group");
	var attachGroupLength = attachGroup.length;
	for(var i=0;i<attachGroupLength;i++)
	{
		if(attachGroup[i].classList.contains("attach-model")==false){
			attachGroup[i].remove();
		}
	}
}

//删除指定阶段计划
function removeProjectPlan(str){
	showConfirmDialog("确定","确认删除该计划",function(){
		$.ajax({
			method:'post',
			url:"/api/1/u/deleteProjectPlan",
			data:"projectId="+$("#projectId").val()+"&stepId="+ str.parentNode.parentNode.getAttribute("planid"),
			success:function(data){
				if(data.resultCode==0)
				{
					showMessageBox("删除成功","提示");
					str.parentNode.parentNode.parentNode.remove();
				}
			}
		})
	})
}

function clearProjectPlan(){
	$("#planStartTime").val("");
	$("#planEndTime").val("");
	$("#price").val("");
	$("#describe").val("");
}

//加载反馈列表
function loadMonitorList(){
	var param ="&projectId="+$("#projectId").val();
	var murl = "/api/1/u/loadProjectMonitor";
	$.ajax({
        "dataType" : "json",
        "type" : "POST",
        "async": "false",
        "data" : param,
        "url" : murl,
        "success" : function(data){
//      	fillTable(data.projectInSelfRunMonitorList);
			return;
        }
	});
}

//进展反馈
var handleApp = new AngularApp('handleApp','ngSanitize');

handleApp.createService('joinerService',function(){
	return {
		loadProjectMonitor:{url:'/api/1/u/loadProjectMonitor',method:'POST'},
		loadProjectPlan:{url:'/api/1/u/loadProjectPlan',method:'POST'},
		queryPushList:{url:'/api/1/selfrun/p/push/list',method:'GET'}
	};
});
handleApp.createController("handleController",function($scope,services){
	/**初始化**/
	$scope.pageSize = 10;
	$scope.showLimit = 5;
	$scope.showPages = [];
	$scope.showPaginate = true;//大于10行数据才显示分页控件

	handleApp.setParam({projectId:$("#projectId").val()});
	services.get('joinerService').loadProjectMonitor(function(response){
		var attachStr;
		$scope.projectFeedbacks = response.projectInSelfRunMonitorList;
		for(var i =0;i<$scope.projectFeedbacks.length;i++){
			attachStr = $scope.projectFeedbacks[i].attachment.replace(/&quot;/g,"\"");
			var attachJson = attachStr == "" ? "" : JSON.parse(attachStr);
			$scope.projectFeedbacks[i].attachJSON = attachJson;
		}
	});

	handleApp.setParam({projectId:$("#projectId").val()});
	services.get('joinerService').loadProjectPlan(function(response){
		var planStr;
		$scope.plans = response.projectInSelfRunPlanVoList;
		$.each($scope.plans, function(index,element) {
			if(element.isDelayed==0){
				element.isDelayedNames = "否";
			}else{
				element.isDelayedNames = "是";
			}
		});
	});

	/**推送列表**/
	handleApp.setParam({projectId:$("#projectId").val()});
	paginateFill($scope,services.get('joinerService').queryPushList,function(data){
		if($scope.totalRow <= 10){$scope.showPaginate=false;}
	});

	$scope.firstPage = function(){
		var firstPage = 1;
		if(firstPage!=$scope.currentPage){
			handleApp.setParam({
				currentPage:firstPage,
				projectId:$("#projectId").val()
			});
			paginateFill($scope,services.get('joinerService').queryPushList,function(data){});
		}
	}
	$scope.prevPage = function(){
		if($scope.currentPage>1){
			$scope.currentPage--;
			handleApp.setParam({
				currentPage:$scope.currentPage,
				projectId:$("#projectId").val()
			});
			paginateFill($scope,services.get('joinerService').queryPushList,function(data){});
		}
	}
	$scope.nextPage = function(){
		if($scope.currentPage<$scope.totalPage.length){
			$scope.currentPage++;
			handleApp.setParam({
				currentPage:$scope.currentPage,
				projectId:$("#projectId").val()
			});
			paginateFill($scope,services.get('joinerService').queryPushList,function(data){});
		}
	}
	$scope.lastPage = function(){
		var lastPage = $scope.totalPage.length;
		if(lastPage!=$scope.currentPage){
			handleApp.setParam({
				currentPage:lastPage,
				projectId:$("#projectId").val()
			});
			paginateFill($scope,services.get('joinerService').queryPushList,function(data){});
		}
	}
	//跳页
	$scope.jump = function(){
		var page = $("#pageJump").val().trim();
		if(page>0&&page<=$scope.totalPage.length){
			handleApp.setParam({
				currentPage:page,
				projectId:$("#projectId").val()
			});
			paginateFill($scope,services.get('joinerService').queryPushList,function(data){});
		}
	}
	//设置查询记录数
	$scope.setPageSize = function(str){
		handleApp.setParam({
			currentPage:1,
			projectId:$("#projectId").val(),
			pageSize:str
		});
		paginateFill($scope,services.get('joinerService').queryPushList,function(data){});
	}
	//分页
	$scope.pagenate = function(page){
		if(page!=$scope.currentPage){
			handleApp.setParam({
				currentPage:page,
				projectId:$("#projectId").val()
			});
			paginateFill($scope,services.get('joinerService').queryPushList,function(data){});
		}
	}

	$scope.showDevInfo = function(id){
		window.open('/api/2/idntify/view?uid=' + id);
	}
});

function fillTable(data){
	$(".monitor-table-data").each(function(){
		if($(this).attr("class").indexOf('monitor-table-data-mode')>=0)return;
		$(this).remove();
	});
	if(data!=null){
		if(data.length==0){
			$(".monitor-step-table").append("<div class='row monitor-table-data' style='text-align:center;'>暂无反馈</div>");
		}
		for(var i=0;i<data.length;i++){
			var newTr = $(".monitor-step-table").find(".monitor-table-data-model").clone(true,true);
			newTr.removeClass("monitor-table-data-model");
			newTr.find(".createTime").html(data[i].createTime);
			var name = getUserNameAuto(data[i]);
			newTr.find(".creatorName").html(name);
			newTr.find(".monitorDesc").html(data[i].monitorDesc);
			newTr.attr("stepId",data[i].stepId);
			newTr.find(".monitor-attach-value").val(data[i].attachment);
			var attachJson = data[i].attachment.replace(/&quot;/g,"\"");
			if(attachJson==undefined||attachJson.trim()=="")attachJson = "[]";
			var attachList = eval("("+attachJson+")");
			for(var count=0;count<attachList.length;count++){
				var newAttach = newTr.find(".monitor-attach-model").clone(true,true);
				newAttach.removeClass("monitor-attach-model");
				newAttach.html(attachList[count].fileName);
				newAttach.attr("path",attachList[count].path);
				newAttach.attr("title",attachList[count].fileName);
				newTr.find(".monitor-attach-group").append(newAttach);
				newAttach.show();
				newAttach.click(function(){
					window.open($(this).attr("path"));
				});
			}
			newTr.appendTo(".monitor-step-table");
			newTr.show();
		}
	}
}
//删除反馈
function delMonitor(row){
//	var param ="&projectId="+$("#projectId").val()+"&stepId="+row.attr("stepId");
	var param ="&projectId="+$("#projectId").val()+"&stepId="+ row.parentNode.parentNode.parentNode.getAttribute("did");
	var murl = "/api/1/u/deleteProjectMonitor";
	showConfirmDialog("提示", "确认删除?", function(){
		$.ajax({
	        "dataType" : "json",
	        "type" : "POST",
	        "async": "false",
	        "data" : param,
	        "url" : murl,
	        "success" : function(data){
	    		showMessageDialog("提示", "删除反馈成功!", function(){
//	    			loadMonitorList();
					row.parentNode.parentNode.parentNode.remove();
	    		});
	        }
		});
	});
}
//更新反馈附件记录
function uploadMonitorAttachment(fileListSelector){

}
/**
 * 日期比较
 * @param startTimeselector
 * @param endTimeselector
 * @param isStart
 */
function dateCompare(startTimeselector,endTimeselector,isStart){
	isAccess = false;
	var validatorType;//0. 所有 1. 开始  2. 结束
	if(isStart==undefined){
		validatorType = 0;
	}else if(isStart){
		validatorType = 1;
	}else{
		validatorType = 2;
	}
	if(startTimeselector.val()==undefined||startTimeselector.val()==""){
		if(validatorType!=2)showErrorForColMdRow("开始日期必填", startTimeselector);
		return;
	}
	if(endTimeselector.val()==undefined||endTimeselector.val()==""){
		if(validatorType!=1)showErrorForColMdRow("完成日期必填", endTimeselector);
		return;
	}

	var endTime=endTimeselector.val();
	//根据日期字符串转换成日期
	var regEx = new RegExp("\\-","gi");
	endTime=endTime.replace(regEx,"/");
	var endMilliseconds=Date.parse(endTime);

	var startTime=startTimeselector.val();
	//根据日期字符串转换成日期
	var regEx = new RegExp("\\-","gi");
	startTime=startTime.replace(regEx,"/");
	var startMilliseconds=Date.parse(startTime);

	if(endMilliseconds<startMilliseconds){
		if(validatorType ==1)showErrorForColMdRow("日期大于完成日期", startTimeselector);
		else if(validatorType==2)showErrorForColMdRow("日期小于开始日期", endTimeselector);
		else{
			showErrorForColMdRow("日期小于开始日期", endTimeselector);
			showErrorForColMdRow("日期大于完成日期", startTimeselector);
		}
	}else{
		isAccess = true;
	}
}
/**
 * 金额
 */
function setCashValidator(cashselector){
	var limit = cashselector.attr("limit");
	var decimal  = cashselector.attr("decimal") == undefined?2:cashselector.attr("decimal");
	//缓存
	cashselector.attr("cache",cashselector.val());
	cashselector.keyup(function(event){
		cashselector.val(cashselector.val().replace(/。/g, ""));
		cashselector.attr("cache",cashselector.val());
	});
	cashselector.keydown(function(event){
		 var num = cashselector.val();
		 var keycode = event.which;
		 if((keycode<48||keycode>57)&&(keycode<96||keycode>105)&&keycode!=110&&keycode!=8&&keycode!=190&&keycode!=229)return false;
		 if(keycode==110||keycode==229||keycode==190){
			 if(cashselector.val().indexOf(".")>=0||cashselector.val().indexOf("。")>=0){

				 return false;
			 }
		 }

	});
	cashselector.bind('input propertychange', function() {
		var num = $(this).val();
		if(!isNaN(num)){
            var dot = num.indexOf(".");
            if(dot != -1){
                var dotCnt = num.substring(dot+1,num.length);
                if(dotCnt.length > 2){
               	 	cashselector.val(cashselector.attr("cache"));
                }
            }
        }else{
        	 cashselector.val(cashselector.attr("cache"));
        }
	});
	cashselector.blur(function(){
		var cash = $(this).val();
		if(cash.indexOf(".")<0){
			if($(this).val().trim().length==0)$(this).val("0");
			$(this).val($(this).val()+".");
			for(var i=0;i<decimal;i++)$(this).val($(this).val()+"0");
			cashselector.attr("cache",$(this).val());
		}else{
			var start = cash.indexOf(".");
			var remain = decimal-cash.substring(start).length+1;
			if(remain>0){
				for(var i=0;i<remain;i++)$(this).val($(this).val()+"0");
				cashselector.attr("cache",$(this).val());
			}
		}
	});
}
$(document).ready(function(){
//	$("#searchText").keydown(function(event){
//		alert(event.keyCode);
//		return false;
//	});
	accessType = $("#accessType").val();
	allPropertyController(accessType);
	jQuery.validator.addMethod("isMobilePhoneNumber", function() {
		var mobile = $("#contactNumber").val();

		return isMobile(mobile);
	}, "手机号码不正确");
	$("#publishRequestForm").validate({
		errorElement : "span",
		messages : {
			content : {
				required : "请输入项目描述"
			},
			price : {
				required : "请选择项目预算"
			},
			contactsName : {
				required : "请输入您的姓名"
			},
			contactNumber : {
				required : "请输入您的手机号码"
			},
			peroid : {
				required : "请输入交付周期"
			}
		}
	});
	generateFileUploadModel($("#fileselect"),$("#projectList"),$("#projectFileInfo"),"/uploadify?ext=file&type=2&needPath=1",uploadProjectAttachment);
	generateFileUploadModel($("#fileselect-monitor"),$("#monitorFileList"),$("#monitorFileInfo"),"/uploadify?ext=file&type=2&needPath=1",uploadMonitorAttachment);
	loadAttachmentList($("#fileselect"),$("#projectList"),uploadProjectAttachment);
	loadMonitorList();
	$(".file-preview").hide();
	$(".fileinput-remove-button").hide();
	$(".fileinput-upload-button").hide();
	$(".btn-file").each(function(){
		var i = $(this).find("i");
   		var input = $(this).find("input").clone(true,true);
   		$(this).html("");
   		i.appendTo($(this));
   		$(this).html($(this).html()+"&nbsp;选择");
   		input.appendTo($(this));
	});
	$(".fileinput-remove-button").each(function(){
		var i = $(this).find("i");
   		var input = $(this).find("input").clone(true,true);
   		$(this).html("");
   		i.appendTo($(this));
   		$(this).html($(this).html()+"&nbsp;清除");
   		input.appendTo($(this));
   		$(this).click(function(){
   			fileUploadInfo.hide();
   		});
   		$(".fileinput-remove").click(function(){
   			fileUploadInfo.hide();
   		});
	});
	$(".info-close").click(function(){
		$(this).parents(".fileupload-info").hide();
	});
	$(".fileinput-upload-button").each(function(){
		var i = $(this).find("i");
   		var input = $(this).find("input").clone(true,true);
   		$(this).html("");
   		i.appendTo($(this));
   		$(this).html($(this).html()+"&nbsp;上传");
   		input.appendTo($(this));
	});
//	$("#inviteDialog").modal("show");
	$('#inviteDialog').draggable({
        handle: ".dialog-header"
    });
	$("#invite").click(function(){
		$("#inviteDialog").modal("show");
	});
	$(".servicer").click(function(){
		var isPush = $("#inviteDialog").attr("isPush")!=undefined;
		if($(this).attr("selected")=="selected"){
			$(this).find("i").remove();
			$(this).css("color","#000");
			$(this).removeAttr("selected");
			if(isPush){
				//缓存
				$(this).find("input[type='hidden']").each(function(){
					$(this).attr("name-cache",$(this).attr("name"));
					$(this).removeAttr("name");
				});
			}
		}else{
			$(this).attr("selected","true");
			$(this).css("color","green");
			var text = $(this).html();
			$(this).html(text+"<i class='fa fa-check-circle-o'></i>");
			//数据绑定
			if(isPush){
				$(this).find("input[type='hidden']").each(function(){
					$(this).attr("name",$(this).attr("name-cache"));
					$(this).removeAttr("name-cache");
				});
			}
		}
		//数据解绑
		if(!isPush){
			//总是移除
			$(this).find("input[type='hidden']").each(function(){
				$(this).attr("name-cache",$(this).attr("name"));
				$(this).removeAttr("name");
			});
		}
		//排序
		var objCount = 0;
		$(".servicer").each(function(index,element){
			var isSelected = $(this).attr("selected")=='selected';

			$(this).find("input[type='hidden']").each(function(){
				if(isSelected){
					var name = $(this).attr("name");
					if(name!=undefined){
						//待提交的表单字段
						if(name.indexOf("projectId")>=0){
							$(this).attr("name","pushList["+objCount+"].projectId");
						}else if(name.indexOf("developerId")>=0){
							$(this).attr("name","pushList["+objCount+"].developerId");
						}
					}
				}
			});
			if(isSelected)objCount++;
		});
	});
	$(".servicer-added>.row").children("*").click(function(){
		if($(this).attr("class").indexOf("servicer-added-opr")>=0)return;
		if($(this).parents(".servicer-added").attr("selected")=="selected"){
			$(this).parents(".servicer-added").find("i").remove();
			$(this).parents(".servicer-added").find(".servicer-added-name").css("color","#000");
			$(this).parents(".servicer-added").removeAttr("selected");
		}else{
			$(this).parents(".servicer-added").attr("selected","true");
			$(this).parents(".servicer-added").find(".servicer-added-name").css("color","#ce4844");
			var text = $(this).parents(".servicer-added").find(".servicer-added-name").html();
			$(this).parents(".servicer-added").find(".servicer-added-name").html("<i class='fa fa-times-circle-o'></i>"+text);
		}
	});
	$(".cancel").click(function(){
		$("#inviteDialog").modal("hide");
	});
	$(".servicer-list").find("li").first().hide();
	$(".servicer-added-list").find("li").first().hide();
	$(".add-servicer").click(function(){
		$(".servicer[selected='selected']").each(function(){
			$(this).find("i").remove();
			var servicerName = $(this).html();
			$(this).remove();
			var uid = $(this).attr("uid");
			var count = $(".servicer-added-list").find(".servicer-added").length;
			var newLi = $(".servicer-added-list").find(".servicer-model").first().clone(true,true);
			newLi.removeClass("servicer-model");
			newLi.find("select").attr("name","list["+(count-1)+"].role");
			newLi.find("select").append("<input class='addedprojectid' type='hidden' name='list["+(count-1)+"].projectId' value='"+$("#projectId").val()+"' />");
			newLi.append("<input class='addeddeveloperid' type='hidden' name='list["+(count-1)+"].developerId' value='"+uid+"' />");
			newLi.show();
			newLi.appendTo(".servicer-added-list");
			newLi.find(".servicer-added-name").html(servicerName);
			newLi.attr("uid",uid);
		});
	});
	$(".remove-servicer").click(function(){
		//移除
		$(".servicer-added[selected='selected']").each(function(){
			$(this).find(".servicer-added-name>i").remove();
			var servicerName = $(this).find(".servicer-added-name").html();
			$(this).remove();
			var uid = $(this).attr("uid");
			var newLi = $(".servicer-list").find(".servicer-model").first().clone(true,true);
			newLi.removeClass("servicer-model");
			newLi.show();
			newLi.appendTo(".servicer-list");
			newLi.html(servicerName);
			newLi.attr("uid",uid);
		});
		//排序
		$(".servicer-added").each(function(index,element){
			if($(this).attr("class").indexOf("servicer-model")>=0)return;
			$(this).find(".addedrole").attr("name","list["+(index-1)+"].role");
			$(this).find(".addedprojectid").attr("name","list["+(index-1)+"].projectId");
			$(this).find(".addeddeveloperid").attr("name","list["+(index-1)+"].developerId");
			$(this).parents(".servicer-added-list");
		});
	});

	//搜索
	$("#search").click(function(){
		//未添加的服务商
		var param = "query="+$("#searchText").val()+"&projectId="+$("#projectId").val();
		var murl = "/api/1/u/invitePprovider";
		if($("#city").val()!="")param +="&regionId="+$("#city").val();
		if($(".tagActive").attr("value")!=undefined){
			var caseTypeStr = $(".tagActive").attr("value");
			param +="&caseTypeStr="+caseTypeStr;
		}
		$.ajax({
	        "dataType" : "json",
	        "type" : "POST",
	        "async": "false",
	        "data" : param,
	        "url" : murl,
	        "success" : function(data){
	        	var model = $(".servicer-list").find(".servicer-model").clone(true,true);
	        	$(".servicer-list").html("");
	        	model.appendTo(".servicer-list");
	        	if(data.inviteServicerFuzzList!=null){
	        		var fuzzList = data.inviteServicerFuzzList;
	        		for(var i =0;i<fuzzList.length;i++){
		        		var newLi = $(".servicer-list").find(".servicer-model").clone(true,true);
		        		newLi.removeClass("servicer-model");
		        		newLi.appendTo(".servicer-list");
		        		newLi.parents("ul").append;
		        		//公司名
		        		var name = getUserNameAuto(fuzzList[i]);
		        		newLi.html(name);
		        		newLi.append("<input type='hidden' name-cache='pushList["+i+"].projectId' value='"+$("#projectId").val()+"'/>");
		        		newLi.append("<input type='hidden' name-cache='pushList["+i+"].developerId' value='"+fuzzList[i].id+"'/>");
		        		newLi.append("<input type='hidden' class='email' value='"+fuzzList[i].email+"'/>");
		        		newLi.attr("uid",fuzzList[i].id);
		        		var introduction = "";
		        		if(fuzzList[i].introduction==null||fuzzList[i].introduction.trim()=="")introduction = "暂无简介";
		        		else introduction = fuzzList[i].introduction;
		        		newLi.attr("title",introduction);
		        		newLi.show();
	        		}
	        	}
	        }
	   });
		//重新加载已添加服务商
		param = "&projectId="+$("#projectId").val();
		murl = "/api/1/u/loadProjectUser";
		$.ajax({
	        "dataType" : "json",
	        "type" : "POST",
	        "async": "false",
	        "data" : param,
	        "url" : murl,
	        "success" : function(data){
	        	var addedList = data.planUserInfoList;
	        	$(".servicer-added-list").find(".servicer-added").each(function(){
	        		if($(this).attr("class").indexOf("servicer-model")>=0)return;
	        		$(this).remove();
	        	});
	        	if(addedList)
	        	for(var count=1;count<=addedList.length;count++){
	        		var newLi = $(".servicer-added-list").find(".servicer-model").first().clone(true,true);
	    			newLi.removeClass("servicer-model");
	    			newLi.find("select").attr("name","list["+(count-1)+"].role");
	    			newLi.find("select>option[value='"+addedList[count-1].role+"']").attr("selected",true);
	    			newLi.find("select").append("<input class='addedprojectid' type='hidden' name='list["+(count-1)+"].projectId' value='"+$("#projectId").val()+"' />");
	    			newLi.append("<input class='addeddeveloperid' type='hidden' name='list["+(count-1)+"].developerId' value='"+addedList[count-1].id+"' />");
	    			newLi.show();
	    			newLi.appendTo(".servicer-added-list");
	    			var name = getUserNameAuto(addedList[count-1]);
	    			newLi.find(".servicer-added-name").html(name);
	    			newLi.attr("uid",addedList[count-1].id);
	        	}
	        }
	   });
	});

	$("#select-all-addedservicer").click(function(){
		var text = $(this).html();
		$(".servicer-added-list").find(".servicer-added").each(function(){
			if($(this).attr("class").indexOf("servicer-model")<0){
				if((text=="全选"&&$(this).attr("selected")=='selected')||(text=="全不选"&&$(this).attr("selected")==undefined)){

				}
				else
				$(this).find(".servicer-added-name").trigger("click");
			}
		});
		if($(this).html()=="全不选")$(this).html("全选");
		else if($(this).html()=="全选")$(this).html("全不选");
	});
	$("#select-all-servicer").click(function(){
		var text = $(this).html();
		$(".servicer-list").find(".servicer").each(function(){
			if($(this).attr("class").indexOf("servicer-model")<0){
				if((text=="全选"&&$(this).attr("selected")=='selected')||(text=="全不选"&&$(this).attr("selected")==undefined)){

				}else
				$(this).trigger("click");
			}
		});
		if($(this).html()=="全不选")$(this).html("全选");
		else if($(this).html()=="全选")$(this).html("全不选");
	});
	$(".add-plan-btn").click(function(){
		$("#planDialog").find("input").val("");
		$("#planDialog").find("textarea").val("");
		$("#planDialog").find("#stepId").val(0);//
		$("#planDialog").removeAttr("isEdit");
		$("#planDialog").modal(true);
		loadInviter();
	});
	$(".addedrole").each(function(){
		$(this).find("option[value='"+$(this).attr("value")+"']").attr("selected",true);
	});
	$(".planUser-del").click(function(){
		delPlanUser($(this).parents(".developer").attr("uid"));
	});
	generateDatePickerWithoutStartDate(".planEndDateDiv","planEndDateDiv");
	generateDatePickerWithoutStartDate(".planStartDateDiv","planStartDateDiv");
	//日期验证
	$("#startTime").change(function(){
		dateCompare($(this),$("#endTime"),true);
	});
	$("#endTime").change(function(){
		dateCompare($("#startTime"), $(this),false);
	});
	$(".plan-delete").click(function(){
		var tr = $(this).parents(".plan-table-data");
		delPlan(tr);
	});
	$(".plan-edit").click(function(){
		var tr = $(this).parents(".plan-table-data");
		var stepId = tr.attr("stepid");
		var step = tr.find(".stepTd").html();
		var stepDesc = tr.find(".stepDescTd").html();
		var startTime = tr.find(".startTime").val();
		var endTime = tr.find(".endTimeTd").html();
		var executorId = tr.find(".executorTd").attr("executorId");
		var status = tr.find(".statusTd").attr("status");
		var price = tr.find(".cashTd").find(".price").html();
		//填充频道
		$("#planDialog").find("input[name='step']").val(step);
		$("#planDialog").find("input[name='startTime']").val(startTime);
		$("#planDialog").find("select[name='status']>option[value='"+status+"']").attr("selected",true);
		$("#planDialog").find("textarea[name='stepDesc']").val(stepDesc);
		$("#planDialog").find("input[name='endTime']").val(endTime);
		$("#planDialog").find("#stepId").val(stepId);
		$("#planDialog").find("#price").val(price);
		loadInviter(executorId);
		$("#planDialog").attr("isEdit","");
		$("#planDialog").modal(true);
	});
	loadPlan();
	$("#feedback").click(function(){
		$("#projectMonitorform").find("input[name='monitorDesc']").val("");
		$("#monitorFileList").find(".attach-group").each(function(){
			if($(this).attr("class").indexOf("attach-model")>=0)return;
			$(this).remove();
		});
		$("#projectMonitorform").find("input[name='stepId']").val("");
		$("#monitorFileList").find(".attachment").val("[]");
		$("#projectMonitorform").find("#fileselect-monitor").val("");
		$("#monitorDialog").removeAttr("isEdit");
		$("#monitorDialog").modal(true);
		$("#projectMonitorform").find(".info-close").trigger("click");
	});
	$(".monitor-delete").click(function(){
		var tr = $(this).parents(".monitor-table-data");
		delMonitor(tr);
	});
	$(".monitor-edit").click(function(){
		var monitorDesc = $(this).parents(".monitor-table-data").find(".monitorDesc").html();
		var attachment = $(this).parents(".monitor-table-data").find(".monitor-attach-value").val().replace(/&quot;/g,"\"");
		$("#monitorFileList").find(".attachment").val(attachment);
		$("#monitorDialog").find("input[name='monitorDesc']").val(monitorDesc);
		$("#projectMonitorform").find("input[name='stepId']").val($(this).parents(".monitor-table-data").attr("stepId"));
		$("#monitorDialog").attr("isEdit",true);
		$("#monitorDialog").modal(true);
		$("#projectMonitorform").find(".info-close").trigger("click");
		//移除旧附件
		$("#projectMonitorform").find("#monitorFileList").find(".attach-group").each(function(){
			if($(this).attr("class").indexOf("attach-model")>=0)return;
			$(this).remove();
		});
		$(this).parents(".monitor-table-data").find(".monitor-attach-group>.monitor-attach").each(function(){
			var monitor_attach = $(this);
			//对话框
			if($(this).attr("class").indexOf("monitor-attach-model")>=0)return;
			var newAttach = $("#projectMonitorform").find("#monitorFileList").find(".attach-model").clone(true,true);
			newAttach.removeClass("attach-model");
			var fileName = $(this).html();
			var path = $(this).attr("path");
			newAttach.attr("path",path);
			newAttach.find(".attach").html(fileName);
			$("#projectMonitorform").find("#monitorFileList").append(newAttach);
			newAttach.show();
			newAttach.find(".attach").click(function(){
				window.open($(this).attr("path"));
			});

			newAttach.find(".attach-del").click(function(){
				var fileListData = new Array();
				var fileAttach = {};
				var fileName = monitor_attach.html();
				var path = monitor_attach.attr("path");
				var count =0;
				$(this).parents(".attach-group").remove();
				$("#monitorFileList").find(".attach-group").each(function(){
					if($(this).attr("class").indexOf("attach-model")>=0)return;
					fileAttach['display'] = count;
					fileAttach['fileName'] = $(this).find(".attach").html();
					fileAttach['path'] = $(this).attr("path");
					fileListData[count] = fileAttach;
					count++;
				});
				$("#monitorFileList").find(".attachment").val(JSON.stringify(fileListData));
			});
		});
	});
	$(".dialog-op").click(function(){
		$(".dialog-op").removeClass("dialog-option-selected");
		$(this).addClass("dialog-option-selected");
		if($(this).attr("class").indexOf("push")>=0){
			$(".add-remove-panel").hide();
			$(".added-col").hide();
			$(".search-result").removeClass("col-lg-5");
			$(".search-result").addClass("col-md-12");
			$("#inviteDialog").attr("isPush",true);

		}else{
			$(".add-remove-panel").show();
			$(".added-col").show();
			$(".search-result").addClass("col-lg-5");
			$(".search-result").removeClass("col-md-12");
			$("#inviteDialog").removeAttr("isPush");

		}
	});
	$("#price").change(function(){

	});
	$(".planUser").each(function(){
		var roleName = $(".addedrole>option[value='"+$(this).attr("roleId")+"']").html();
		$(this).attr("title",roleName);
	});
	setCashValidator($("#price"));
	$("#province").change(function(){
		var pid = $(this).val();
		var param = "";
		$("#city").html("<option value=''>请选择</option>");
		if(pid!="")param = "id="+pid;
		else param = "id=-1";
		$.get("/api/region/citys",param,function(data){
			$.each(data,function(index,element){
				$("#city").append("<option value='"+element.id+"'>"+element.name+"</option>");
			});
		});
		if(pid==""){
			$("#search").trigger("click");
		}
	});
	$("#city").change(function(){
		$("#search").trigger("click");
	});
	$(".caseTag").click(function(){
		$(".caseTag").removeClass("tagActive");
		$(this).addClass("tagActive");
		$("#search").trigger("click");
	});
});

var app = new AngularApp("projectApp");
app.createService('joinerService',function(){
	return {
		query:{url:'/api/1/u/loadJoinerList',method:'POST'},
		setAlternative:{url:'/api/1/u/updateJoiner',method:'POST'}
	};
});
app.createController("remarkCtrl", function($scope,services){
	/**初始化**/
	app.setParam({projectId:$("#projectId").val()});
	services.get('joinerService').query(function(data){
		$scope.joinerList = [];
		var alterJoiner = [];
		var unAlterJoiner = [];
		$.each(data.joinerList,function(index,joiner){
			if(joiner.isAlternative==1)alterJoiner.push(joiner);
			else unAlterJoiner.push(joiner);
		});
		$.each(alterJoiner,function(index,joiner){
			joiner.isRemarking = false;
			$scope.joinerList.push(joiner);
		});
		$.each(unAlterJoiner,function(index,joiner){
			joiner.isRemarking = false;
			$scope.joinerList.push(joiner);
		});
	});
	$scope.isToggle = false;
	$scope.toggleJoiner = function(arrow){
		$scope.isToggle = !$scope.isToggle;
		if($scope.isToggle){
			$(".remark-panel").slideUp();
		}else $(".remark-panel").slideDown();
	}
	$scope.selectJoiner = function(joiner){
		joiner.isAlternative = joiner.isAlternative==1?0:1;
		app.setParam({
			developerId:joiner.developerId,
			projectId:joiner.projectId,
			remark:joiner.remark,
			isAlternative:joiner.isAlternative
		});
		services.get('joinerService').setAlternative(function(data){
			$scope.joinerList = [];
			var alterJoiner = [];
			var unAlterJoiner = [];
			$.each(data.joinerList,function(index,joiner){
				if(joiner.isAlternative==1)alterJoiner.push(joiner);
				else unAlterJoiner.push(joiner);
			});
			$.each(alterJoiner,function(index,joiner){
				joiner.isRemarking = false;
				$scope.joinerList.push(joiner);
			});
			$.each(unAlterJoiner,function(index,joiner){
				joiner.isRemarking = false;
				$scope.joinerList.push(joiner);
			});
		});
	}
	$scope.clickRemark = function(joiner){
		if(joiner.isFirst!=undefined)
		joiner.isRemarking = true;
		else joiner.isFirst = false;
	}
	$scope.updateRemark = function(joiner){
		joiner.isRemarking = false;
		app.setParam({
			developerId:joiner.developerId,
			projectId:joiner.projectId,
			remark:joiner.remark,
			isAlternative:joiner.isAlternative
		});
		services.get('joinerService').setAlternative(function(data){
			$scope.joinerList = [];
			var alterJoiner = [];
			var unAlterJoiner = [];
			$.each(data.joinerList,function(index,joiner){
				if(joiner.isAlternative==1)alterJoiner.push(joiner);
				else unAlterJoiner.push(joiner);
			});
			$.each(alterJoiner,function(index,joiner){
				joiner.isRemarking = false;
				if(joiner.remark==null)joiner.remark="";
				$scope.joinerList.push(joiner);
			});
			$.each(unAlterJoiner,function(index,joiner){
				joiner.isRemarking = false;
				if(joiner.remark==null)joiner.remark="";
				$scope.joinerList.push(joiner);
			});
		});
	}
	$scope.viewAttender = function(joiner){
		window.open("/public/m/"+joiner.name);
	}
    //渲染轮播回调监听
    $scope.$on('onFinishRenderEvent', function (onFinishRenderEvent) {
    	$(".hidden-text").each(function(){
    		$(this).trigger("click");
    	});
	});
});
