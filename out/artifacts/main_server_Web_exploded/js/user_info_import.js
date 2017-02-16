$(document).ready(function() {
	var projectId = $("#projectId").val();


	if(projectId != null){
		$("#programmer").removeClass("active");
		$("#programmerLibrary").hide();
		$("#make").addClass("active");
		$("#makeLibrary").show();
	}
});

function fieldInfoStatus(str){
	if(str.classList.contains("glyphicon-chevron-up"))
	{
		str.parentNode.parentNode.querySelector(".fieldInfo").style.display = "block"
		str.classList.remove("glyphicon-chevron-up");
		str.classList.add("glyphicon-chevron-down");
	}else{
		str.parentNode.parentNode.querySelector(".fieldInfo").style.display = "none";
		str.classList.remove("glyphicon-chevron-down");
		str.classList.add("glyphicon-chevron-up");
	}
}

function pushPageProject(){
	var view = document.getElementById("view");
	var fieldCheckBox = view.querySelectorAll(".fieldCheckBox");
	var fieldCheckBoxLen = fieldCheckBox.length;
	for(var i = 0;i<fieldCheckBoxLen;i++){
		fieldCheckBox[i].querySelector("img").classList.add("fieldActive");
	}
}

//初始化推送选择框
function clearPush(){
	var view = document.getElementById("view");
	var fieldCheckBox = view.querySelectorAll(".fieldCheckBox");
	var fieldCheckBoxLen = fieldCheckBox.length;
	for(var i = 0;i<fieldCheckBoxLen;i++){
		fieldCheckBox[i].querySelector("img").classList.remove("fieldActive");
	}
}

//检测推送人员
function checkPushList(){
	var view = document.getElementById("view");
	var checkBoxNum = 0;
	var fieldCheckBox = view.querySelectorAll(".fieldCheckBox");
	var fieldCheckBoxLen = fieldCheckBox.length;
	for(var i = 0;i<fieldCheckBoxLen;i++){
		if(fieldCheckBox[i].querySelector("img").classList.contains("fieldActive")==true){
			checkBoxNum++;
		}
	}
	if(checkBoxNum==0)
	{
		return false;
	}
}

//推送项目
function pushProject(str){
	var mail = [];
	var params = "";
	var mUrl = "";
	var view = document.getElementById("view");
	var fieldCheckBox = view.querySelectorAll(".fieldCheckBox");
	var fieldCheckBoxLen = fieldCheckBox.length;
	var notice = "确定推送?";
	var pushNum = 0;
	if(str==0){
		if(checkPushList()==false){
			showMessageBox("至少推送给一位开发者","提示")
			return;
		}
		for(var i = 0;i<fieldCheckBoxLen;i++){
			if(fieldCheckBox[i].querySelector("img").classList.contains("fieldActive")==true){
				mail.push(fieldCheckBox[i].getAttribute("developermail"));
				params += "&pushList["+ pushNum +"].projectId=" + $("#projectId").val() + "&pushList["+ pushNum +"].developerId=" + fieldCheckBox[i].getAttribute("developerid");
				pushNum++;
			}
		}
		mUrl = "/api/1/p/push/project?allMailStr=" + mail.join(",");
	}else if(str==1){
		pushPageProject();//推送当前页面所有开发者
		for(var i = 0;i<fieldCheckBoxLen;i++){
			mail.push(fieldCheckBox[i].getAttribute("developermail"));
			params += "&pushList["+ i +"].projectId=" + $("#projectId").val() + "&pushList["+ i +"].developerId=" + fieldCheckBox[i].getAttribute("developerid");
		}
		mUrl = "/api/1/p/push/project?allMailStr=" + mail.join(",");
	}else if(str==2){
		notice = "确定推送所有开发者?";
		mUrl = "/api/1/p/push/project";
		params = "&isPushAll=true";
	}

	showConfirmDialogCallback("提示", notice, function(){
		$.ajax({
			method:'post',
			url:mUrl,
			data:"projectId=" + $("#projectId").val() + params,
			success:function(data){
				clearPush();//清空推送target
				showMessageBox("推送成功","提示");
			}
		})
	},function(){
		clearPush();
	})
}

function fieldActive(str){
	str.querySelector("img").classList.toggle("fieldActive");
}

var pageSize;//每页显示条数
var currentPage;//当前页码
var totalPage;//总共显示页数
var totalRow;//总数
var selectDeleteId;//删除依据
var selectUrl;//记录url地址
function queryList(str){
currentPage = (currentPage == undefined) ? 1 : currentPage;
var murl;

	if($("#ability").attr("value") == undefined)
	{
		$("#ability").attr("value","")
	}

	if($("#cando").attr("value") == undefined){
		$("#cando").attr("value","")
	}

	if($("#cityId").attr("value") == undefined){
		$("#cityId").attr("value","")
	}

	if(selectDeleteId=='cityId_div')
	{
		$("#cityId").attr("value","");
		selectDeleteId=undefined;
		selectUrl="";
	}else if(selectDeleteId=='cando_div'){
		$("#cando").attr("value","");
		selectDeleteId=undefined;
	}else if(selectDeleteId=='ability_div'){
		$("#ability").attr("value","");
		selectDeleteId=undefined;
	}

	//条件搜索
	if(selectUrl=='condition'){
		murl = "/api/2/u/developer/list?pageSize=10&currentPage=" + currentPage +"&provinceId=&cityId="+$("#cityId").attr("value")+"&cando="+$("#cando").attr("value")+"&ability="+$("#ability").attr("value")+"&otherAbility=" + $("#otherAbility").val();
	}else{
		murl = "/api/2/u/developer/list?pageSize=10&currentPage=" + currentPage +"&provinceId="+$("#cityId").attr("value")+"&cityId=&cando="+$("#cando").attr("value")+"&ability="+$("#ability").attr("value")+"&otherAbility=" + $("#otherAbility").val();
	}

	$.ajax({
		type:"get",
		url:murl,
		async:false,
		success:function(data){
			pageSize = data.pageSize;
//			currentPage = data.currentPage;
			totalPage = data.totalPage;
			totalRow = data.totalRow;
			$("#startPage").html( ((currentPage-1)*10 == 0) ? 1 : ((currentPage-1)*10)+1);
			$("#endPage").html(parseInt($("#startPage").html())+9);
			$("#count").html(totalRow);
			//页码生成部分
			var pageNum = document.getElementById("pageNum");

			for(var i = 1;i<=totalPage;i++)
			{
				if(i==currentPage)
				{
					pageNum.innerHTML += "<div class='pageNumDiv pageActive' onclick='jumpPage(this)'>" + i +"</div>";
				}else{
					pageNum.innerHTML += "<div class='pageNumDiv' onclick='jumpPage(this)'>" + i +"</div>";
				}
			}
			//数据加载部分
			var view = document.getElementById("view");
//			view.innerHTML = "";
			if(totalRow == 0){return;}
			if(totalPage == currentPage)
			{
				if(pageSize > totalRow){
					pageSize = totalRow%pageSize;
			  }
			}
			for(var j = 0;j<pageSize;j++){

				//初始化数据
				var employeeProjectExperience = "";
				var employeeEduExperience = "";
				var employeeJobExperience = "";
				var employeeProduct = "";

				if(data.data[j].displayCando==""||data.data[j].displayCando==" ")
				{
					data.data[j].displayCando = "暂无";
				}

				if(data.data[j].weixin==null){
					data.data[j].weixin = "无";
				}

				if(data.data[j].qq == null){
					data.data[j].qq = "无";
				}

				switch(data.data[j].category){
					case 0:
						data.data[j].category = "个人";
					break;
					case 1:
						data.data[j].category = "公司";
					break;
					default:
					    data.data[j].category = "其他";
					break;
				}


				if(data.data[j].introduction==null||data.data[j].introduction=="null"){
					data.data[j].introduction = "";
				}

				var checkBoxBtn = $("#projectId").val() == undefined ? "" : "<div developerMail='"+ data.data[j].email +"' developerId='" +data.data[j].id+ "' class='fieldCheckBox' onclick='fieldActive(this)'><img src= "+ $("#topurl").val() +"/img/checkboxchecked.png /></div>";//

				if(data.data[j].category=='个人'){

					for(var a = 0;a<data.data[j].employeeEduExperience.length;a++){
						employeeEduExperience += "<div class='col-xs-12 col-sm-12 col-md-12 field'>" +
										"<div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>"+
										data.data[j].employeeEduExperience[a].schoolName+"</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>"+
										data.data[j].employeeEduExperience[a].discipline+"</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>"+
										data.data[j].employeeEduExperience[a].eduBackgroud+"</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>"+
										data.data[j].employeeEduExperience[a].graduationTime+"</div>" +
										"</div>"
					}

					for(var b = 0;b<data.data[j].employeeJobExperience.length;b++){
						employeeJobExperience += "<div class='col-xs-12 col-sm-12 col-md-12 field'>" +
										"<div class='col-xs-2 col-sm-2 col-md-2 text-left' style='color:#999'>"+
										data.data[j].employeeJobExperience[b].companyName+"</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>"+
										data.data[j].employeeJobExperience[b].startTime+" ~ "+data.data[j].employeeJobExperience[b].endTime+"</div><div class='col-xs-2 col-sm-2 col-md-2 text-left' style='color:#999'>"+
										data.data[j].employeeJobExperience[b].office+"</div><div class='col-xs-5 col-sm-5 col-md-5 text-left' style='color:#999'>"+
										data.data[j].employeeJobExperience[b].description+"</div>" +
        						"</div>"
					}

					for(var c = 0;c<data.data[j].employeeProduct.length;c++){
						employeeProduct += "<div class='col-xs-12 col-sm-12 col-md-12 field'>" +
										"<div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>"+
										data.data[j].employeeProduct[c].title+"</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999;word-break:break-all'>"+
										data.data[j].employeeProduct[c].link+"</div><div class='col-xs-6 col-sm-6 col-md-6 text-left' style='color:#999'>"+
										data.data[j].employeeProduct[c].description+"</div>" +
						        "</div>"
					}

					view.innerHTML += "<div class='row'>" +
						"<div class='col-xs-12 col-sm-12 col-md-12 field'>" +
							checkBoxBtn +
							"<span class='username'>"+data.data[j].name+"</span>" +
							"<div style='margin-right: 10px' class='tagbo mate_ajax_link ui compact tag label hidden_440px'>"+data.data[j].region+"</div>" +
							"<div style='margin-right: 10px' class='tagbo mate_ajax_link ui label compact hidden_440px'>"+data.data[j].category+"</div>" +
							"<div style='margin-right: 10px' class='tagbo mate_ajax_link ui label compact hidden_440px'>"+data.data[j].displayCando+"</div>" +
							"<button class='btn btn-primary' style='float:right;' onclick='window.open(\"/api/2/idntify/view?uid="+data.data[j].id+"\")'>查看</button>" +
						"<div/>" +
						"<div class='col-xs-12 col-sm-12 col-md-12 field' style='color:rgba(0, 0, 0, 0.6)'>" +
							"<span style='padding-right:15px;'><i class='fa fa-mobile fa-fw fa-lg' style='color:black;'></i><span>"+data.data[j].mobile+"</span></span>" +
							"<span style='padding-right:15px;'><i class='fa fa-envelope fa-fw' style='color:orange;'></i><span>"+data.data[j].email+"</span></span>" +
							"<span style='padding-right:15px;'><i class='fa fa-weixin fa-fw' style='color:green;'></i><span>"+data.data[j].weixin+"</span></span>" +
							"<span style='padding-right:15px;'><i class='fa fa-qq fa-fw' style='color:#337ab7;'></i><span>"+data.data[j].qq+"</span></span>" +
						"</div>" +
						"<div class='col-xs-12 col-sm-12 col-md-12 field'>" +
							data.data[j].introduction +
						"</div>" +
						"<div class='col-xs-12 col-sm-12 col-md-12 text-center'><div class='glyphicon glyphicon-chevron-up' onclick='fieldInfoStatus(this)'></div></div>" +
						"<div class='col-md-12 col-xs-12 col-sm-12 fieldInfo'>" +
						"<div class='col-xs-12 col-sm-12 col-md-12'><div style='float: left;display: inline-block;padding: 10px;'>教育背景</div><hr/></div>" +
						"<div class='col-md-12 col-xs-12 col-sm-12'><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>名称</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>专业</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>学历</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>毕业年份</div></div>" +
						employeeEduExperience +
						"<div class='col-xs-12 col-sm-12 col-md-12'><div style='float: left;display: inline-block;padding: 10px;'>工作经验</div><hr/></div>" +
						"<div class='col-md-12 col-xs-12 col-sm-12'><div class='col-xs-2 col-sm-2 col-md-2 text-left' style='color:#999'>公司</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>工作时间</div><div class='col-xs-2 col-sm-2 col-md-2 text-left' style='color:#999'>职位</div><div class='col-xs-5 col-sm-5 col-md-5 text-left' style='color:#999'>工作内容</div></div>" +
						employeeJobExperience +
						"<div class='col-xs-12 col-sm-12 col-md-12'><div style='float: left;display: inline-block;padding: 10px;'>项目作品</div><hr/></div>" +
						"<div class='col-md-12 col-xs-12 col-sm-12'><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>项目名称</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>项目链接</div><div class='col-xs-6 col-sm-6 col-md-6 text-left' style='color:#999'>项目描述</div></div>" +
						employeeProduct +
						"<div class='col-xs-12 col-sm-12 col-md-12' style='border-bottom:1px solid #ccc;'></div>"
						"</div>" +
					+ "</div>";
				}else{
					for(var i = 0;i<data.data[j].employeeProjectExperience.length;i++){
						employeeProjectExperience += "<div class='col-xs-12 col-sm-12 col-md-12 field'>" +
																"<div class='col-xs-2 col-sm-2 col-md-2 text-left' style='color:#999'>"+
										data.data[j].employeeProjectExperience[i].projectName+"</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>"+
										data.data[j].employeeProjectExperience[i].startTime+" ~ "+data.data[j].employeeProjectExperience[i].endTime+"</div><div class='col-xs-2 col-sm-2 col-md-2 text-left' style='color:#999'>"+
										data.data[j].employeeProjectExperience[i].link+"</div><div class='col-xs-5 col-sm-5 col-md-5 text-left' style='color:#999'>"+
										data.data[j].employeeProjectExperience[i].description+"</div>" +
										"</div>"
					}

					view.innerHTML += "<div class='row'>" +
						"<div class='col-xs-12 col-sm-12 col-md-12 field'>" +
						    checkBoxBtn +
							"<span class='username'>"+data.data[j].name+"</span>" +
							"<div style='margin-right: 10px' class='tagbo mate_ajax_link ui compact tag label hidden_440px'>"+data.data[j].region+"</div>" +
							"<div style='margin-right: 10px' class='tagbo mate_ajax_link ui label compact hidden_440px'>"+data.data[j].category+"</div>" +
							"<div style='margin-right: 10px' class='tagbo mate_ajax_link ui label compact hidden_440px'>"+data.data[j].displayCando+"</div>" +
							"<button class='btn btn-primary' style='float:right;' onclick='window.open(\"/api/2/idntify/view?uid="+data.data[j].id+"\")'>查看</button>" +
						"<div/>" +
						"<div class='col-xs-12 col-sm-12 col-md-12 field' style='color:rgba(0, 0, 0, 0.6)'>" +
							"<span style='padding-right:15px;'><i class='fa fa-mobile fa-fw fa-lg' style='color:black;'></i><span>"+data.data[j].mobile+"</span></span>" +
							"<span style='padding-right:15px;'><i class='fa fa-envelope fa-fw' style='color:orange;'></i><span>"+data.data[j].email+"</span></span>" +
							"<span style='padding-right:15px;'><i class='fa fa-weixin fa-fw' style='color:green;'></i><span>"+data.data[j].weixin+"</span></span>" +
							"<span style='padding-right:15px;'><i class='fa fa-qq fa-fw' style='color:#337ab7;'></i><span>"+data.data[j].qq+"</span></span>" +
						"</div>" +
						"<div class='col-xs-12 col-sm-12 col-md-12 field'>" +
							data.data[j].introduction +
						"</div>" +
						"<div class='col-xs-12 col-sm-12 col-md-12 text-center'><div class='glyphicon glyphicon-chevron-up' onclick='fieldInfoStatus(this)'></div></div>" +
						"<div class='col-md-12 col-xs-12 col-sm-12 fieldInfo'>" +
						"<div class='col-xs-12 col-sm-12 col-md-12'><div style='float: left;display: inline-block;padding: 10px;'>项目作品</div><hr/></div>" +
						"<div class='col-md-12 col-xs-12 col-sm-12'><div class='col-xs-2 col-sm-2 col-md-2 text-left' style='color:#999'>项目名称</div><div class='col-xs-3 col-sm-3 col-md-3 text-left' style='color:#999'>项目时间</div><div class='col-xs-2 col-sm-2 col-md-2 text-left' style='color:#999'>项目链接</div><div class='col-xs-5 col-sm-5 col-md-5 text-left' style='color:#999'>项目介绍</div></div>" +
							employeeProjectExperience +
						"<div class='col-xs-12 col-sm-12 col-md-12' style='border-bottom:1px solid #ccc;'></div>"
						"</div>" +
					+ "</div>";
				}
			}
		}
	});
}

function seachOtherAbility(){
	var view = document.getElementById("view");
	var pageNum = document.getElementById("pageNum");
	view.innerHTML = "";
	pageNum.innerHTML = "";
	queryList();
}

//页面跳转
var num = 1;
function Page(str){
	var view = document.getElementById("view");
	var pageNum = document.getElementById("pageNum");
	if(str.id == 'homePage')
	{
		num = 1;
	}else if(str.id == 'theLastPage')
	{
		num = totalPage;
	}else if(str.id == 'lastPage'){
		num = (num - 1 < 1) ? 1 : num - 1;
	}else if(str.id == 'nextPage'){
		num = (num + 1 > totalPage) ? totalPage : num + 1;
	}else if(str.id == 'jumpPageBtn'){
		if($("#targetPage").val() > totalPage){
				$("#targetPage").val("");
				num = 1;
		}else if($("#targetPage").val() == ""){
			num = 1;
		}else{
			num = $("#targetPage").val();
		}
	}
	currentPage = num;
	view.innerHTML = "";
	pageNum.innerHTML = "";
	queryList();
	scroll(0,0);//回到顶部
}

//页码跳转
function jumpPage(str){
	var view = document.getElementById("view");
	var pageNum = document.getElementById("pageNum");
	//页码置换
	currentPage = str.innerHTML;
	view.innerHTML = "";
	pageNum.innerHTML = "";
	queryList();
	scroll(0,0);//回到顶部
}

window.addEventListener("load",queryList)

function selectDelete(str){
	var view = document.getElementById("view");
	var pageNum = document.getElementById("pageNum");
	str.parentNode.querySelector("span").innerHTML = "";
	str.parentNode.style.display = "none";
	selectDeleteId = str.parentNode.id;
	view.innerHTML = "";
	pageNum.innerHTML = "";
	queryList();
	scroll(0,0);//回到顶部
}

function cityFocus(str){
	//移除所有下拉框样式
	var selectId = str.id;
	var select_div = document.querySelectorAll(".select_div")
	var select_divs = document.querySelectorAll(".options_div");
	var select_divsLength = select_divs.length;
	for(var i = 0;i<select_divsLength;i++)
	{
		if(selectId != select_div[i].id)
		{
			select_div[i].classList.remove("select_active");
			select_divs[i].classList.remove("options_div_active");
		}
	}

	str.classList.toggle("select_active")
	str.parentNode.querySelector('.options_div').classList.toggle("options_div_active");
}

function secondOptions(str){
		str.querySelector(".options_ul_ul").classList.toggle('options_ul_ul_active')
}

function selectLabelHover(str){
	if(str.classList.contains('selectLabel_active')==false)
	{
		str.classList.add('selectLabel_active');
		str.querySelector("i").style.color="#3498BD";
		str.querySelector("div:last-of-type").classList.add("select_div_active");
	}else{
		str.classList.remove('selectLabel_active');
		str.querySelector("i").style.color="#666";
		str.querySelector("div:last-of-type").classList.remove("select_div_active");
	}
}

function secondAddLabel(str,e){
	var view = document.getElementById("view");
	var pageNum = document.getElementById("pageNum");
	currentPage = undefined;
	if(e && e.stopPropagation){
	  //W3C取消冒泡事件
	  e.stopPropagation();
	}else{
	  //IE取消冒泡事件
	  window.event.cancelBubble = true;
	}

	str.parentNode.parentNode.parentNode.parentNode.parentNode.querySelector(".options_div").classList.remove("options_div_active");
	str.parentNode.parentNode.parentNode.parentNode.parentNode.querySelector(".select_div").classList.remove("select_active");
	$("#cityId_div").css("display","block");
	$("#cityId").html(str.innerHTML);
	$("#cityId").attr("value",str.getAttribute("value"));
	view.innerHTML = "";
	pageNum.innerHTML = "";
	selectUrl = 'condition';
	queryList();
}

function addLabel(str){
	var view = document.getElementById("view");
	var pageNum = document.getElementById("pageNum");
	var containDiv = str.parentNode.parentNode.parentNode.querySelector(".select_div");
	var targetId = containDiv.id;
	currentPage = undefined;
	if(targetId == "city")
	{
		str.parentNode.parentNode.parentNode.querySelector(".select_div").classList.remove("select_active");
		str.parentNode.parentNode.classList.remove("options_div_active");
		selectUrl = "";
		$("#cityId_div").css("display","block");
		$("#cityId").html(str.querySelector("span").innerHTML);
		$("#cityId").attr("value",str.querySelector("span").getAttribute("value"))
	}else if(targetId == "type"){
		str.parentNode.parentNode.parentNode.querySelector(".select_div").classList.remove("select_active");
		str.parentNode.parentNode.classList.remove("options_div_active");
		$("#cando_div").css("display","block");
		$("#cando").html(str.innerHTML)
		$("#cando").attr("value",str.getAttribute("value"))
	}else if(targetId == "technology"){
		str.parentNode.parentNode.parentNode.querySelector(".select_div").classList.remove("select_active");
		str.parentNode.parentNode.classList.remove("options_div_active");
		$("#ability_div").css("display","block");
		$("#ability").html(str.innerHTML)
		$("#ability").attr("value",str.getAttribute("value"))
	}

	view.innerHTML = "";
	pageNum.innerHTML = "";
	queryList();
}

function bodyTab(str){
	var tabOption = document.querySelectorAll(".tab-bg div");
	var activeOption = document.getElementById(str);
	for(var i = 0;i<tabOption.length;i++){
		tabOption[i].classList.remove("active");
	}
	if(str == 'programmer'){
		$("#makeLibrary").css("display","none");
		$("#programmerLibrary").css("display","block");
	}else{
		$("#makeLibrary").css("display","block");
		$("#programmerLibrary").css("display","none");
	}
	activeOption.classList.add('active');
}

function dataTransform($scope){
	var skillJson = eval('('+$scope.userImportVo.skill+')');
	var skillList = new Array();
	$.each(skillJson,function(key,value){
		var skill = {};
		skill.name = key;
		skill.level = value;
		skillList.push(skill);
	});
	$scope.userImportVo.skillList = skillList;
	var jobArrJson = eval('('+$scope.userImportVo.job+')');
	var jobList = new Array();
	$.each(jobArrJson,function(key,object){
		jobList.push(object);
	});
	$scope.userImportVo.jobList = jobList;

	var gitArrJson = eval('('+$scope.userImportVo.git+')');
	var gitList = new Array();
	$.each(gitArrJson,function(key,object){
		gitList.push(object);
	});
	$scope.userImportVo.gitList = gitList;

	var zhihuArrJson = eval('('+$scope.userImportVo.zhihu+')');
	var zhihuList = new Array();
	$.each(zhihuArrJson,function(key,object){
		zhihuList.push(object);
	});
	$scope.userImportVo.zhihuList = zhihuList;

	var stackArrJson = eval('('+$scope.userImportVo.stackoverflow+')');
	var stackList = new Array();
	$.each(stackArrJson,function(key,object){
		stackList.push(object);
	});
	$scope.userImportVo.stackList = stackList;
}
var app = new AngularApp('userInfoImportApp');
/**数据共享**/
app.createSpace('userImportSpace');
/**服务**/
app.createService('userImportService',function(){
	return{
		queryFuzzUserImport:{method:'GET',url:'/api/2/u/userImportList'},
		queryUserInfo:{method:'GET',url:'/api/2/u/userImportInfo'}
	};
});
/**列表模块**/
app.createController('userInfoListCtrl',function($scope,services){
	$scope.isListActive = true;
	/**初始化**/
	$scope.pageSize = 8;
	$scope.showLimit = 5;
	$scope.showPages = [];
	app.setParam({
		currentPage:1,
		query:$("#search").val(),
		skillQuery:$("#skill").val(),
		pageSize:$scope.pageSize
	});
	paginateFill($scope,services.get('userImportService').queryFuzzUserImport,function(data){
		$scope.dataList = data;
	});

    $scope.firstPage = function(){
    	var firstPage = 1;
    	if(firstPage!=$scope.currentPage){
    		app.setParam({
    			currentPage:firstPage,
    			query:$("#search").val(),
    			skillQuery:$("#skill").val(),
    			pageSize:$scope.pageSize
    		});
    		paginateFill($scope,services.get('userImportService').queryFuzzUserImport,function(data){
    			$scope.userImportList = data;
        	});
    	}
    }
    $scope.prevPage = function(){
    	if($scope.currentPage>1){
        	$scope.currentPage--;
        	app.setParam({
    			currentPage:$scope.currentPage,
    			query:$("#search").val(),
    			skillQuery:$("#skill").val(),
    			pageSize:$scope.pageSize
    		});
        	paginateFill($scope,services.get('userImportService').queryFuzzUserImport,function(data){

        	});
    	}
    }
    $scope.nextPage = function(){
    	if($scope.currentPage<$scope.totalPage.length){
        	$scope.currentPage++;
        	app.setParam({
    			currentPage:$scope.currentPage,
    			query:$("#search").val(),
    			skillQuery:$("#skill").val(),
    			pageSize:$scope.pageSize
    		});
        	paginateFill($scope,services.get('userImportService').queryFuzzUserImport,function(data){

        	});
    	}
    }
    $scope.lastPage = function(){
    	var lastPage = $scope.totalPage.length;
    	if(lastPage!=$scope.currentPage){
        	app.setParam({
    			currentPage:lastPage,
    			query:$("#search").val(),
    			skillQuery:$("#skill").val(),
    			pageSize:$scope.pageSize
    		});
    		paginateFill($scope,services.get('userImportService').queryFuzzUserImport,function(data){

        	});
    	}
    }
    //跳页
    $scope.jump = function(){
    	var page = $("#pageJump").val().trim();
    	if(page>0&&page<=$scope.totalPage.length){
    		app.setParam({
    			currentPage:page,
    			query:$("#search").val(),
    			skillQuery:$("#skill").val(),
    			pageSize:$scope.pageSize
    		});
    		paginateFill($scope,services.get('userImportService').queryFuzzUserImport,function(data){

        	});
    	}
    }

    //设置查询记录数
    $scope.setPageSize = function(){
    	app.setParam({
			currentPage:1,
			query:$("#search").val(),
			skillQuery:$("#skill").val(),
			pageSize:$scope.pageSize
		});
    	paginateFill($scope,services.get('userImportService').queryFuzzUserImport,function(data){

    	});
    }

    //分页
    $scope.pagenate = function(page){
    	if(page!=$scope.currentPage){
    		app.setParam({
    			query:$("#search").val(),
    			skillQuery:$("#skill").val(),
    			currentPage:page,
    			pageSize:$scope.pageSize
    		});
    		paginateFill($scope,services.get('userImportService').queryFuzzUserImport,function(data){

        	});
    	}
    }
    $scope.keySearch = function($event){
    	if($event.keyCode==13){
    		app.setParam({
    			currentPage:1,
    			query:$("#search").val(),
    			skillQuery:$("#skill").val(),
    			pageSize:$scope.pageSize
    		});
        	paginateFill($scope,services.get('userImportService').queryFuzzUserImport,function(data){

        	});
    	}
    }
    $scope.search = function(){
    	app.setParam({
			currentPage:1,
			query:$("#search").val(),
			skillQuery:$("#skill").val(),
			pageSize:$scope.pageSize
		});
    	paginateFill($scope,services.get('userImportService').queryFuzzUserImport,function(data){

    	});
    }
    $scope.view = function(id){
    	app.setParam({
    		uid:id
    	});
    	services.get('userImportService').queryUserInfo(function(data){
    		$scope.userImportVo = data.userInfoImportVo;
    		dataTransform($scope);
    	});
    	$scope.isListActive = false;
    }
    $scope.back = function(){
    	$scope.isListActive = true;
    }
    $scope.viewJobLink = function(href){
    	window.open(href);
    }
});
