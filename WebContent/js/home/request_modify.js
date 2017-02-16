$(document).ready(function(){
	initFileIuput("#attachment","",1);

});
//定义 select 原值
var oldValue ="";
var oldText = "";
var oldSelect;
var unit = '元';
//select下拉框的onkeydown事件，修改下拉框的值
function catch_keydown(sel){
    switch(event.keyCode) {
    case 13: //回车键
    sel.options[0].selected = true;
    event.returnValue = false;
    break;
    case 27: //Esc键
    sel.options[oldSelect].selected = true;
    event.returnValue = false;
    break;
	case 8:  //←键，回删键
    var s = sel.options[sel.selectedIndex].text;
    s = s.substr(0,s.length-1);
    if (sel.options[0].value==sel.options[sel.selectedIndex].text){
	    sel.options[sel.selectedIndex].value=s;
	    sel.options[sel.selectedIndex].text=s;
    }
    event.returnValue = false;
    break;
    }
	if (!event.returnValue && sel.onchange)
    sel.onchange(sel)
}

//select下拉框的onkeypress事件，修改下拉框的值
function catch_press(sel){
if(sel.selectedIndex>=0){
    var s = sel.options[sel.selectedIndex].text + String.fromCharCode(event.keyCode);
    if (sel.options[sel.selectedIndex].value==sel.options[sel.selectedIndex].text){
    sel.options[sel.selectedIndex].value=s;
    sel.options[sel.selectedIndex].text=s;
//  $scope.startBudget = s;
    sel.options[0].selected = true;
    }
    event.returnValue = false;
    if (!event.returnValue && sel.onchange)
   		sel.onchange(sel)
	}
}

//select下拉框的onfocus事件，保存下拉框原来的值
function catch_focus(sel) {
    oldText = sel.options[sel.selectedIndex].text;
    oldValue = sel.options[sel.selectedIndex].value;
    oldSelect = sel.selectedIndex;
}


var app = angular.module("projectApp",['summernote']);

app.controller('projectController',function($scope,$http){
	var pid = $("#h_projectId").val();

	//summernote options
	$scope.summernoteOptions = {
	height: 300,
	// focus: true,
	//airMode: true,
	toolbar: [
					['edit',['undo','redo']],
					['headline', ['style']],
					['style', ['bold', 'italic', 'underline']],
					['fontface', ['fontname']],
					['textsize', ['fontsize']],
					['fontclr', ['color']],
					['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
					['height', ['height']],
					['table', ['table']],
					// ['insert', ['link','picture','video','hr']],
					['view', ['fullscreen']],
					['help', ['help']]
			],
			callbacks: {
        onPaste: function (e) {
            var bufferText = ((e.originalEvent || e).clipboardData || window.clipboardData).getData('Text');

            e.preventDefault();

            // Firefox fix
            setTimeout(function () {
                document.execCommand('insertText', false, bufferText);
            }, 10);
        }
			}
    };

	//遍历出用户选择类型并选中
	function checkprojectTypes(projectType,dictItemVal){
		var projectTypeArray = projectType.split(",");

		for(var i = 0; i < projectTypeArray.length; i++){
			if(projectTypeArray[i] == dictItemVal){
				return true;
			}
		}
	}

	function getProjectType(projectType,projectTypeList){
       if(projectType == null || projectTypeList == null || projectTypeList.length == 0){
       	return;
       }

       for(var i = 0; i < projectTypeList.length;i++){
       	if(projectType.value == projectTypeList[i].value){
       		return projectTypeList[i];
       	}
       }
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
	return str + "元";
	}
	//获得选择框中的值
	function getSelectText(){
         var sel =document.getElementById("budget");
         var option=sel.getElementsByTagName("option");
         var str = "" ;
         for(var i=0;i<option.length;++i)
         {
         if(option[i].selected)
         {
         str = option[i].text;
         }
         }
         return str;
	}
	//手动更新budget的值
	$scope.updateVal = function(){
		var s = getSelectText();
		$scope.budget = s;
	}
	//管理员修改招募对象
	$scope.modifyEnroll = function(tmpEnroll){
		if(tmpEnroll.active == true)
		{
			tmpEnroll.active = false;

			var enrollRole = getProjectType(tmpEnroll,$scope.enrollList);

			enrollRole.active = false;
		}else{
			tmpEnroll.active = true;

			var enrollRole = getProjectType(tmpEnroll,$scope.enrollList);

			enrollRole.active = true;
		}
	}
	//管理员修改诚意项目
	$scope.modifyFaithProject = function(faithProject){
		if(faithProject == 1)
		{
			$scope.faithProject = 0;
		}else{
			$scope.faithProject = 1;
		}
	}

	$scope.modifyIsNeedBuyDomain = function(isNeedBuyDomain){
		if(isNeedBuyDomain == 1)
		{
			$scope.isNeedBuyDomain = 0;
		}else{
			$scope.isNeedBuyDomain = 1;
		}
	}

	$scope.modifyIsNeedBuyServerAndDB = function(isNeedBuyServerAndDB){
		if(isNeedBuyServerAndDB == 1)
		{
			$scope.isNeedBuyServerAndDB = 0;
		}else{
			$scope.isNeedBuyServerAndDB = 1;
		}
	}

	//管理员点击类型
	$scope.modifyProjectTypes = function(tmpProjectType){

		if(tmpProjectType.active==true)
		{
			tmpProjectType.active=false;
			//$scope.projectType = tmpProjectType.name;
			var projectType = getProjectType(tmpProjectType,$scope.projectTypes);

			projectType.active = false;
		}else{
			tmpProjectType.active=true;
//			if(projectArr(Ustr)>-1)
//			{
//				$scope.projectType = $scope.projectType.split(",").splice(projectArr(tmpProjectType),1).join();
//			}
			var projectType = getProjectType(tmpProjectType,$scope.projectTypes);

			projectType.active = true;
		}
	}

	$scope.checkProjectNameNull = function(str){
		if(str=="")
		{
			document.getElementById("projectNameWarning").classList.remove("divactive");
		}else{
			document.getElementById("projectNameWarning").classList.add("divactive");
		}
	}

	$scope.checkProjectPeriodNull = function(str){
		var check = str.match(/^[1-9]\d{0,2}$/);
		if(str=="" || check == null)
		{
			document.getElementById("projectPeriodWarning").classList.remove("divactive");
		}else{
			document.getElementById("projectPeriodWarning").classList.add("divactive");
		}
	}

	$scope.checkprojectStatusNull = function(str){
		if(str < -1 || str > 3 || str=="" || str==null)
		{
			document.getElementById("projectStatusWarning").classList.remove("divactive");
		}else{
			document.getElementById("projectStatusWarning").classList.add("divactive");
		}
	}

	$scope.checkActualAmountsNull = function(str){
		if(str=="")
		{
			document.getElementById("actualAmountsWarning").classList.remove("divactive");
		}else{
			document.getElementById("actualAmountsWarning").classList.add("divactive");
		}
	}

	$scope.checkEnrollNull = function(){
		var submittedEnroll = [];
		for(var i = 0;i<$scope.enrollList.length;i++)
		{
			if($scope.enrollList[i].active==false){
				submittedEnroll.push($scope.enrollList[i].value)
			}
		}
		if($("#userType").val()==-1){
			if($scope.projectStatus != -1 && $scope.projectStatus != 3){
				if(submittedEnroll.length==0)
				{
					document.getElementById("enrollWarning").classList.remove("divactive")
				}else{
					document.getElementById("enrollWarning").classList.add("divactive")
				}
			}
		}
	}

	$scope.checkProjectTypeNull = function(){
		var submittedProjectType = [];
		for(var i = 0;i<$scope.projectTypes.length;i++){
			if($scope.projectTypes[i].active==false)
			{
			 	submittedProjectType.push($scope.projectTypes[i].value)
			}
		}
		if(submittedProjectType.length==0)
		{
			document.getElementById("projectTypeWarning").classList.remove("divactive")
		}else{
			document.getElementById("projectTypeWarning").classList.add("divactive")
		}
	}

	$scope.checkRequirementNull = function(str){
		if(str=="")
		{
			document.getElementById("requirementWarning").classList.remove("divactive");
		}else{
			document.getElementById("requirementWarning").classList.add("divactive");
		}
	}

	$scope.checkReasonNull = function(str){
		if(str=="")
		{
			document.getElementById("reasontWarning").classList.remove("divactive");
		}else{
			document.getElementById("reasontWarning").classList.add("divactive");
		}
	}

  //获取所有的顾问信息
	$http({
		method:'get',
		url:'/api/2/u/getallconsultants'
	}).success(function(response){
      $scope.allConsultants = response.consultants;
	});
	$http({
		method:'get',
		url:'/api/selfrun/p/get',
		params:{
			'id':pid
		}
	}).success(function(response){
		$scope.projectInSelfRun = response.projectInSelfRun;
		$scope.projectId = response.projectInSelfRun.id;
		$scope.projectName = decodeStr(response.projectInSelfRun.name);
		$scope.requirement = decodeStr(response.projectInSelfRun.content);
		var requirementContent = $scope.requirement;
		if($scope.requirement.indexOf("<br/>")!=-1)
		{
			$scope.requirement = requirementContent.replace(/<br\/>/g,"\n")
		}
		$scope.actualAmounts = response.projectInSelfRun.dealCost;
		$scope.projectType = response.projectInSelfRun.type;
		$scope.startTime = response.projectInSelfRun.startTime;
		$scope.startBudget = response.projectInSelfRun.budget;
		$scope.budget = response.projectInSelfRun.budget;
		$scope.faithProject = response.projectInSelfRun.faithProject;
		$scope.attachment = response.projectInSelfRun.attachment;
		$scope.projectStatus = response.projectInSelfRun.status;
		$scope.projectStatusTag = response.projectInSelfRun.statusTag;
		$scope.repoNick = response.projectInSelfRun.repoNick;
		$scope.period = response.projectInSelfRun.period;
		$scope.projectTypes = [];
		$scope.projectTimes = [];
		var obj = {"name":$scope.startBudget};
		$scope.projectSums = [];
		$scope.projectSums.push(obj);
		$scope.enrollList = [];
		$scope.dictItemList = response.listDictItem;
		$scope.reason = "";
		$scope.isNeedBuyDomain = response.projectInSelfRun.isNeedBuyDomain;
		$scope.isNeedBuyServerAndDB = response.projectInSelfRun.isNeedBuyServerAndDB;

		//项目状态的list
		if($scope.repoNick==""||$scope.repoNick==null){
			$scope.projectStatusList = [{name:"待启动",value:"0"},{name:"开发中",value:"1"},{name:"已完成",value:"2"},{name:"未通过",value:"3"},{name:"已关闭",value:"4"},{name:"审核中",value:"-1"}];
		}else{
			$scope.projectStatusList = [{name:"待启动",value:"0"},{name:"开发中",value:"1"},{name:"已完成",value:"2"},{name:"未通过",value:"3"},{name:"已关闭",value:"4"}];
		}
		//若有附件有值
		var attachmentStatus = $scope.attachment;
		var attachmentsObj;
		if(attachmentStatus==null)
		{
			attachmentsObj = [];
		}else{
			attachmentsObj = eval("("+$scope.attachment.replace(/&quot;/g,"\"")+")");
		}
		if(attachmentsObj)attachList = attachmentsObj;

		$scope.projectAttachment = attachmentsObj;

		var maxDisplay = 0;
		$.each(attachList,function(index,element){
			var newAttach = $(".attach-model").first().clone(true,true);
			$("#monitorFileList").append(newAttach);
			maxDisplay = maxDisplay<parseInt(element.display)?parseInt(element.display):maxDisplay;
			$(".attach-model").last().find(".attach").attr("display",element.display);
			$(".attach-model").last().find(".attach").html(element.fileName);
			$(".attach-model").last().find(".attach").bind("click",function(){
				window.open(element.path);
			});
			$(".attach-model").last().find(".attach-del").bind("click",function(){
				$(this).parent().remove();
				var delElement = $(this).parent().find(".attach").attr("display");
				$(attachList).each(function(index,element){
					if(element.display==delElement)
					{
						attachList.splice(index,1);
					}
				})
			});
			totalIndex = maxDisplay+1;
			$(".attach-model").last()[0].classList.remove("opacityHide");
		});
		/*if(attachmentsObj.length!=0)
		{	//attachList
			for(var i = 0;i<attachmentsObj.length;i++)
			{
				attachList.push(attachmentsObj[i])
			}
		}*/
		$scope.downLoadAttach = function(attach){
			window.open(attach.path);
		}

		$scope.delAttach = function(attach){
				$(attachList).each(function(index,element){
					if(attach.display==element.display)
					{
						$scope.attachModelList.splice(index,1);
						attachList.splice(index,1);
					}
				})
		}
		//遍历出项目类型列表
		$(response.listDictItem).each(function(index,dictItem){
			if(dictItem.dictGroupId==4)
			{
				var imgType=true;

				if(checkprojectTypes($scope.projectType,dictItem.value)==true)
				{
					imgType=false;
				}

				dictItem.active = imgType;

				$scope.projectTypes.push(dictItem);
			}
		})

		//遍历出招募人员
		$(response.listDictItem).each(function(index,dictItem){
			if(dictItem.dictGroupId==7)
			{
				var imgType=true;

				if(typeof $scope.projectInSelfRun.enrollRole != 'object'){
					if(checkprojectTypes($scope.projectInSelfRun.enrollRole,dictItem.value)==true)
					{
						imgType=false;
					}
				}
				dictItem.active = imgType;

				$scope.enrollList.push(dictItem);
			}
		})

		//遍历时间下拉选项
		$(response.listDictItem).each(function(index,dictItem){
			if(dictItem.dictGroupId==6)
			{
				$scope.projectTimes.push(dictItem);
			}
		})

		$(response.listDictItem).each(function(index,dictItem){
			if(dictItem.dictGroupId==5)
			{
				$scope.projectSums.push(dictItem);
			}
		})
	}).error(function(response){
		showMessageDialog("错误信息", "系统内部错误,请联系管理员", function(){window.location.reload()});
	});

	var content = "";

	//表单提交
	$scope.Sendform = function(){
		var submittedProjectType = [];
		var submittedEnroll = [];

		for(var i = 0;i<$scope.projectTypes.length;i++){
			if($scope.projectTypes[i].active==false)
			{
			 	submittedProjectType.push($scope.projectTypes[i].value)
			}
		}

		for(var i = 0;i<$scope.enrollList.length;i++)
		{
			if($scope.enrollList[i].active==false){
				submittedEnroll.push($scope.enrollList[i].value)
			}
		}

		if($scope.projectName=="")
		{
			document.getElementById("projectNameWarning").classList.remove("divactive");
			return false;
		}

		if($scope.projectStatus==2||$scope.projectStatus==1)
		{
			if($scope.actualAmounts==0)
			{
				document.getElementById("actualAmountsWarning").classList.remove("divactive");
				return false;
			}
		}
		
		if($("#userType").val()==-1){
			//alert($scope.projectStatus);

			if($scope.projectStatus != -1 && $scope.projectStatus != 3 && $scope.projectStatus != 4){
				if(submittedEnroll.length==0)
				{
					document.getElementById("enrollWarning").classList.remove("divactive");
					return false;
				}
			}
		}

		if(submittedProjectType.length==0)
		{
			document.getElementById("projectTypeWarning").classList.remove("divactive");
			return false;
		}

		content = $scope.requirement;

		if(content==""){
			document.getElementById("requirementWarning").classList.remove("divactive");
			return false;
		}else{
			//content = encodeURIComponent(content);
		//	content = encodeURIComponent(content);
		}
		console.log(content);

		reason = encodeURI($("#reason").val());
		if($scope.projectStatus == 4 && reason==""){
			document.getElementById("reasontWarning").classList.remove("divactive");
			return false;
		}

		/*var projectStatus = parseInt($scope.projectStatus);
		if(projectStatus < -1 || projectStatus > 3 || projectStatus=="" || projectStatus==null)
		{
			document.getElementById("projectStatusWarning").classList.remove("divactive");
			return false;
		}*/
		var budget = $scope.budget;
		//如果全为数字，  则转化为'200,000元' 的形式
		if(/^\d+$/.test(budget)){
			budget = cuter(budget);
		}

		var data = {
		    "id":$scope.projectId,
			"name":$scope.projectName,
			"budget":budget,
//			"startTime":$scope.startTime,
			"type":submittedProjectType.join(","),
			"content":content,
			"status":$scope.projectStatus,
			"dealCost":$scope.actualAmounts,
			"attachment":JSON.stringify(attachList),//提交附件表单
			"enrollRole":submittedEnroll.join(","),
			"faithProject":$scope.faithProject,
			"reason":$scope.reason,
			"period":$scope.period,
			"consultantId":$scope.projectInSelfRun.consultant.id,
			"isNeedBuyDomain":$scope.isNeedBuyDomain,
			"isNeedBuyServerAndDB":$scope.isNeedBuyServerAndDB
		}

		$http({
			method:"post",
			url:"/api/1/p/modify",
			params:data
		}).success(function(data){
			if(data.resultCode == 0){
				showMessageDialog("需求修改","修改成功",
			    function(){
						 var userType = $("#userType").val();
			       var pid = $("#h_projectId").val();

						 if(userType == -1){
							  window.location.href="/admin/request_review?id=" + pid;
						 }else{
							  window.location.href="/home/selfrun/p/view?id=" + pid;
						 }

				});
			}else{
				showMessageDialog("需求修改","修改失败");
			}

		})
	}

});

//文件上传list
var attachList = [];
var totalIndex =0;

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
        	//附件对象
        	var str={};
        	str['display']=totalIndex;
        	str['path']=data;
        	str['fileName']=fileName;
        	attachList.push(str);
        	totalIndex++;
        	fileselector.removeAttr("disabled");
			$(".btn-file").css("background-color","#3498DB");
			$(".btn-file").css("border","1px solid #3498DB");
			fileUploadInfo.fadeOut();
			var newAttach = $(".attach-model").first().clone(true,true);
			$("#monitorFileList").append(newAttach);
			$(".attach-model").last().find(".attach").attr("display",totalIndex++);
			$(".attach-model").last().find(".attach").html(fileName);
			$(".attach-model").last().find(".attach").bind("click",function(){
				window.open(data);
			});
			$(".attach-model").last().find(".attach-del").bind("click",function(){
				$(this).parent().remove();
				var delElement = $(this).parent().find(".attach").attr("display");
				$(attachList).each(function(index,element){
					if(element.display==delElement)
					{
						attachList.splice(index,1);
					}
				})
			});
			$(".attach-model").last()[0].classList.remove("opacityHide");
//      	fillAttach(fileselector,fileListSelector,fileUploadInfo,fileName,data,updateCall);

        }
	}
    };
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
        	showUploadMessageBox($("#fileselect"),fileUploadInfo,"文件大小不符合要求!");
            return false;
        }else if(filesize>maxsize){
			showUploadMessageBox($("#fileselect"),fileUploadInfo,"文件大小不能超过"+size+"M!");
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
}

//加载附件列表
function loadAttachmentList(fileselector,fileListSelector,updateCall){
	var fileListJson = fileListSelector.find(".attachment").val();
	var fileListData = eval('('+fileListJson+')');
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

$(document).ready(function(){
//	$("#searchText").keydown(function(event){
//		alert(event.keyCode);
//		return false;
//	});
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
	generateFileUploadModel($("#fileselect"),$("#projectList"),$("#projectFileInfo"),"/uploadify?ext=file&type=1&needPath=1",uploadProjectAttachment);
//	generateFileUploadModel($("#fileselect-monitor"),$("#monitorFileList"),$("#monitorFileInfo"),"/uploadify?ext=file&type=2&needPath=1",uploadMonitorAttachment);
//	loadAttachmentList($("#fileselect"),$("#projectList"),uploadProjectAttachment);
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

});

//转义字符串
function decodeStr(input){
	if(input.length == 0) return "";
	// input = input.replace(/&amp;/g,"&");
	// input = input.replace(/&lt;/g,"<");
	// input = input.replace(/&gt;/g,">");
	// input = input.replace(/&nbsp;/g," ");
	// input = input.replace(/&#39;/g,"\'");
	// input = input.replace(/&quot;/g,"\"");
	return input;
};
