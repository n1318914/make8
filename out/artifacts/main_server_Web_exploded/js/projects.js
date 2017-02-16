/**Angular app**/
var app = new AngularApp('projectsApp');
app.createService('projectService', function($http) {
	return {
		queryProjectList: {
			method: 'POST',
			url: '/api/u/loadProjectList'
		},
		projectTypeList: {
			method: 'GET',
			url: '/api/label/list'
		}
	};
});

app.createController('projectsCtrl', function($scope, services) {
	/**初始化**/
	$scope.nowPage = 1;
	$scope.pageSize = 9;
	$scope.showLimit = 5;
	$scope.showPages = [];
	$scope.projectType = "";
	$scope.isAllType = true;
	$scope.status = 0;//-2
	$scope.isAllStatus = true;
	/**状态**/
	$scope.statusList = new Array();
	$scope.statusList.push({
		name: '待启动',
		value: '0'
	});
	$scope.statusList.push({
		name: '开发中',
		value: '1'
	});
	$scope.statusList.push({
		name: '已完成',
		value: '2'
	});
	/**标签**/
	services.get('projectService').projectTypeList(function(data) {
		$scope.projectTypeList = new Array();
		$.each(data, function(index, dictItem) {
			if (dictItem.dictGroupId == 4) {
				$scope.projectTypeList.push(dictItem);
			}
		});
	});

//	/**招募人员**/
//	services.get('projectService').queryProjectList(function(data) {
//		$.each(data,function(index,element){
//			if(element.dictGroupId == 7){
//				$scope.dataList.recruiter = element.name;
//			}
//		})
//	});
//
	/**列表**/
	app.setParam({
		currentPage: 1,
		query: '',
		typeStr: $scope.projectType,
		status: $scope.status,
		pageSize: $scope.pageSize
	});

	paginateFill($scope, services.get('projectService').queryProjectList, function(data) {
		$.each($scope.dataList, function(index, project) {
			decorateProject(project)
		});
	});

	$scope.firstPage = function() {
		var firstPage = 1;
		if (firstPage != $scope.currentPage) {
			app.setParam({
				currentPage: firstPage,
				query: $("#search").val(),
				typeStr: $scope.projectType,
				status: $scope.status,
				pageSize: $scope.pageSize
			});
			paginateFill($scope, services.get('projectService').queryProjectList, function(data) {
				$.each($scope.dataList, function(index, project) {
					decorateProject(project)
				});
			});
		}
	}
	$scope.prevPage = function() {
		if ($scope.currentPage > 1) {
			$scope.currentPage--;
			app.setParam({
				currentPage: $scope.currentPage,
				query: $("#search").val(),
				typeStr: $scope.projectType,
				status: $scope.status,
				pageSize: $scope.pageSize
			});
			paginateFill($scope, services.get('projectService').queryProjectList, function(data) {
				$.each($scope.dataList, function(index, project) {
					decorateProject(project)
				});
			});
		}
	}

	$scope.nextPage = function() {
		if ($scope.currentPage < $scope.totalPage.length) {
			$scope.currentPage++;
			app.setParam({
				currentPage: $scope.currentPage,
				query: $("#search").val(),
				typeStr: $scope.projectType,
				status: $scope.status,
				pageSize: $scope.pageSize
			});

			paginateFill($scope, services.get('projectService').queryProjectList, function(data) {
				$.each($scope.dataList, function(index, project) {
					decorateProject(project)
				});
			});
		}
	}

	$scope.loadMore = function(){
		if ($scope.nowPage < $scope.totalPage.length) {
			$scope.nowPage++;
			app.setParam({
				currentPage: $scope.nowPage,
				query: $("#search").val(),
				typeStr: $scope.projectType,
				status: $scope.status,
				//每页显示多少条
				pageSize: $scope.pageSize,
			});

			services.get('projectService').queryProjectList(function(data){
				var loadProjectList = data.data;

				if(loadProjectList == null || loadProjectList.length <= 0){
					return;
				}

				var alreadyLoadProjectList = $scope.dataList;

				$.each(data.data, function(index,project) {
            decorateProject(project);
						alreadyLoadProjectList.push(project);
				});
			})
		}else{
			$(".m-pagination-page > li").css("display","none")
		}
	}


	$scope.search = function() {
		app.setParam({
			query: $("#search").val(),
			currentPage: 1,
			typeStr: $scope.projectType,
			status: $scope.status,
			pageSize: $scope.pageSize
		});
		paginateFill($scope, services.get('projectService').queryProjectList, function(data) {
			$.each($scope.dataList, function(index, project) {
				decorateProject(project)
			});
		});
	}

	$scope.keySearch = function($event) {
		if ($event.keyCode == 13) {
			app.setParam({
				query: $("#search").val(),
				currentPage: 1,
				typeStr: $scope.projectType,
				status: $scope.status,
				pageSize: $scope.pageSize
			});
			paginateFill($scope, services.get('projectService').queryProjectList, function(data) {
				$.each($scope.dataList, function(index, project) {
					decorateProject(project)
				});
			});
		}
	}

	$scope.searchType = function(projectType) {
		var typeStr = "";
		if (projectType == undefined) {
			$scope.isAllType = true;
			$.each($scope.projectTypeList, function(index, project) {
				project.isActive = false;
			});
		} else {
			$scope.projectType = projectType.value;
			typeStr = projectType.value;
			$.each($scope.projectTypeList, function(index, project) {
				if (projectType != project) project.isActive = false;
			});
			projectType.isActive = !projectType.isActive;
			if (projectType.isActive) {
				$scope.isAllType = false;
			} else {
				$scope.isAllType = true;
				typeStr = "";
			}
		}
		$scope.projectType = typeStr;
		app.setParam({
			query: $("#search").val(),
			currentPage: 1,
			typeStr: typeStr,
			status: $scope.status,
			pageSize: $scope.pageSize
		});
		paginateFill($scope, services.get('projectService').queryProjectList, function(data) {
			$.each($scope.dataList, function(index, project) {
				decorateProject(project)
			});
		});
	}

	$scope.findStatus = function(statusObj) {
		var status = -2;
		if (statusObj == undefined) {
			$scope.isAllStatus = true;
			$.each($scope.statusList, function(index, s) {
				s.isActive = false;
			});
		} else {
			$scope.status = statusObj.value;
			status = statusObj.value;
			$.each($scope.statusList, function(index, s) {
				if (statusObj != s) s.isActive = false;
			});
			statusObj.isActive = !statusObj.isActive;
			if (statusObj.isActive) {
				$scope.isAllStatus = false;
			} else {
				$scope.isAllStatus = true;
				status = -2;
			}
		}
		$scope.status = status;
		app.setParam({
			query: $("#search").val(),
			currentPage: 1,
			status: status,
			typeStr: $scope.projectType,
			pageSize: $scope.pageSize
		});
		paginateFill($scope, services.get('projectService').queryProjectList, function(data) {
			$.each($scope.dataList, function(index, project) {
				decorateProject(project)
			});
		});
	}

	$scope.viewProject = function(project) {
		window.open("./home/p/detail/" + project.id);
	}
});

function decorateProject(project) {
	if (project.name.length > 12) {
		project.abbrName = shortStr(project.name);
	}else{
			project.abbrName = project.name;
	}

	if (project.type.indexOf(",") >= 0) {
		var firstType = project.type.split(",");
		if (firstType[0] == '') {
			firstType.shift();
			project.userType = firstType;
		} else {
			project.userType = firstType;
		}
	} else {
		project.userType = new Array(project.type);
	}

	if(project.enrollRoleList != null){
		var enrollRoleListStr = "";

		for(var i=0; i < project.enrollRoleList.length;++i){
			enrollRoleListStr += project.enrollRoleList[i].name;

			if(i < project.enrollRoleList.length - 1){
				enrollRoleListStr += " ";
			}
		}

		//招募对象字符串过长处理

		project.enrollRoleListAllStr = enrollRoleListStr;//注释部分

		if(enrollRoleListStr.split(" ").length > 3)
		{
			enrollRoleListStr = enrollRoleListStr.split(" ")[0]+" "+enrollRoleListStr.split(" ")[1]+" "+enrollRoleListStr.split(" ")[2]+"...";
		}

		project.enrollRoleListStr = enrollRoleListStr;
	}
}

function toNewView(str){
	window.open("./home/p/detail/" + str)
}

//判断字符串的Unicode长度,并截取
function shortStr(str) {
	if (str == null) return "";
	if (typeof str != "string") {
		str += "";
	}
	var newstr = "";
	var len = 25;
	for (i = 0; i < str.length; i++) {
		if ((str.charCodeAt(i) & 0xff00) != 0) {
			len--;
		}
		len--;
		if(len >= 0){newstr += str.charAt(i);}
		if(len < 0){return newstr+"..."};
	}
	return newstr;
}

function doServiceReservation(){
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
        		showMessageBox("发布成功，我们的客服会尽快和您取得联系！","极速发布","/market");
        	}else{
        		$("#reservationMsg").html(data.errorMsg);
        	}
        }
	});
}


$(document).ready(function(){
	 //validating the form
	jQuery.validator.addMethod("isMobilePhoneNumber",function(){
		var mobile = $("#telPhone").val();
		var chkResult =  isMobile(mobile);
		return chkResult;
    },"手机号码不正确");
	
	$("#form-reservation").validate({
	 	 errorElement:"span",
	 	 messages:{
	 	 	telPhone:{
	 	 		required:"请输入手机号"
	 	 	},
	 	 	name:{
	 	 		required:"请输入姓名"
	 	 	},
		 	demand:{
				required:"请输入需求描述"
			}
		 },
	 	 errorPlacement: function(error, element) {
	       	 if(element.is("#name")){
	       	  	 error.appendTo($("#nameWarning"));
	       	  }else if(element.is("#telPhone")){
	       	  	 error.appendTo($("#telPhoneWarning"));
	       	  }else if(element.is("#demand")){
	       	  	error.appendTo($("#demandWarning"));
	       	  }
		 }
	});
});