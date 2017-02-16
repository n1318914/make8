/**Angular app**/
var app = angular.module('projectsReviewApp', []);

app.factory('reviewServiceFactory', function($http) {
   var factory = {};

   factory.httpService = function(url,method,params,callback) {
		 $http({
			 url:url,
			 params:params,
			 method:method
			 }).success(function(data){
				 callback(data);
			 }).error(function(data){
				showMessageDialog("错误信息", "系统内部错误,请联系管理员", function(){});
			 });
   };

	 factory.gScope = null;

   return factory;
});

var gScope = null;

app.service("paginationService",function(reviewServiceFactory,projectsReviewService){
	this.generatePaginatioView = function($scope){
		var paginationView = $scope.paginationView;
		var totalRows = $scope.totalRows;
		var pageSize = $scope.pageSize;

		if(paginationView == null){
				paginationView = $("#paginationView").page({
														total:totalRows,
														showJump: true,
														pageSize:pageSize,
														showInfo: true,
														showPageSizes: true,
														firstBtnText: '首页',
														lastBtnText: '尾页',
														prevBtnText: '上一页',
														nextBtnText: '下一页',
														jumpBtnText:'跳转',
														infoFormat: '{start}-{end}条，共{total}条'
													 });

				paginationView.on("pageClicked", function (event, pageNumber) {
														reviewServiceFactory.gScope.currentPage = pageNumber + 1;

														projectsReviewService.searchProjects(reviewServiceFactory.gScope,function(data){
															reviewServiceFactory.gScope.totalRows = data.totalRow;
															reviewServiceFactory.gScope.projectList = data.data;
															projectsReviewService.decorateProjects(reviewServiceFactory.gScope.projectList);
														});

													}).on("jumpClicked", function (event, pageNumber) {
														reviewServiceFactory.gScope.currentPage  = pageNumber + 1;

														projectsReviewService.searchProjects(reviewServiceFactory.gScope,function(data){
															reviewServiceFactory.gScope.totalRows = data.totalRow;
															reviewServiceFactory.gScope.projectList = data.data;
															projectsReviewService.decorateProjects(reviewServiceFactory.gScope.projectList);
														});
													}).on("pageSizeChanged", function (event, pageSize) {
														reviewServiceFactory.gScope.currentPage = 1;
														reviewServiceFactory.gScope.pageSize  = pageSize;

														 projectsReviewService.searchProjects(reviewServiceFactory.gScope,function(data){
																reviewServiceFactory.gScope.totalRows = data.totalRow;
																reviewServiceFactory.gScope.projectList = data.data;
																projectsReviewService.decorateProjects(reviewServiceFactory.gScope.projectList);
														});
													});
			}

			$scope.paginationView = paginationView;
	};

	this.destoryPaginationView = function($scope){
			var paginationView = $scope.paginationView;

			if(paginationView != null){
				paginationView.page('destroy');
				paginationView = null;
			}

			$scope.paginationView = paginationView;
	};

});

app.service('projectsReviewService', function(reviewServiceFactory) {
    this.projectTypeList = function(callback) {
				var method = 'GET';
				var url = '/api/label/list';
				return reviewServiceFactory.httpService(url,method,null,callback);
    };

		this.searchProjects = function($scope,callback){
			var method = 'POST';
			var url = '/api/1/u/loadAttendProject';

		  var chosenProjectType = $scope.chosenProjectType;
			var chosenProjectStatus = $scope.chosenProjectStatus;
			var currentPage = $scope.currentPage;
			var pageSize = $scope.pageSize;
			var keywords = $scope.keywords;

      var chosenProjectTypeVal = "";
			var chosenProjectStatusVal = "";

			if(chosenProjectType != null){
				chosenProjectTypeVal = chosenProjectType.value;
			}

			if(chosenProjectStatus != null){
				chosenProjectStatusVal = chosenProjectStatus.value;
			}

			// if(keywords != null && keywords.trim() != ""){
			// 	 chosenProjectTypeVal = "";
			// 	 chosenProjectStatusVal = "";
			// }

			var parasMap = {
				currentPage:currentPage,
				pageSize:pageSize,
				projectType:chosenProjectTypeVal,
				projectStatus:chosenProjectStatusVal,
				keywords:keywords
			}

			return reviewServiceFactory.httpService(url,method,parasMap,callback);
		};

		this.decorateProjects = function(projectList){
			if(projectList == null || projectList.length <= 0){
				return;
			}

      var projectTypeStr = "";
			var projectTypeList;
	    var tmpProjectTypeVal;

			for(i = 0; i < projectList.length; i++){
				projectTypeStr = projectList[i].type;
        projectTypeList = projectTypeStr.split(",");
				//projectList[i].projectTypeList = projectTypeList;
        projectTypeStr = "";

				for(j = 0; j < projectTypeList.length; j++){
						tmpProjectTypeVal = projectTypeList[j];

						if(tmpProjectTypeVal != null && tmpProjectTypeVal.trim() != ""){
							switch(tmpProjectTypeVal){
								case "1":
									projectTypeStr += "Andriod ";
									break;
								case "2":
									projectTypeStr += "iOS ";
									break;
								case "3":
								  projectTypeStr += "HTML5 "
									break;
								case "4":
									projectTypeStr += "网站 "
									break;
								case "5":
									projectTypeStr += "微信 "
									break;
								case "7":
									projectTypeStr += "UI "
									break;
								case "8":
									projectTypeStr += "其他 "
									break;
							}
						}
				}

				projectList[i].displayType = projectTypeStr;
			}
		}

});

app.controller('projectsReviewCtrl', function($scope,projectsReviewService,paginationService,reviewServiceFactory) {
	$scope.currentPage = 1;
	$scope.pageSize = 10;
	$scope.showPages = [];
  $scope.totalRows = 0;

	$scope.isAllType = true;
	$scope.isAllStatus = true;
	$scope.isAllConsultant = true;
	reviewServiceFactory.gScope = $scope;

	//初始化
	projectsReviewService.searchProjects($scope,function(data){
		$scope.projectList = data.data;
		$scope.totalRows = data.totalRow;
    projectsReviewService.decorateProjects($scope.projectList);

		paginationService.generatePaginatioView($scope);
	});

	//项目状态
  $scope.projectStatusList = new Array();
	$scope.projectStatusList.push({value:"-1",name:"待审核"});
	$scope.projectStatusList.push({value:"0",name:"待启动"});
	$scope.projectStatusList.push({value:"1",name:"开发中"});
	$scope.projectStatusList.push({value:"2",name:"已完成"});

  /**项目类型**/
	projectsReviewService.projectTypeList(function(data){
		$scope.projectTypeList = new Array();

	    $.each(data, function(index, dictItem) {
	      if (dictItem.dictGroupId == 4) {
	        $scope.projectTypeList.push(dictItem);
	      }
	    });
	});

	 $scope.searchType = function(projectType) {
		 $scope.currentPage = 1;
     //$scope.pageSize = 10;

		if (projectType == undefined) {
			$scope.isAllType = true;
			$scope.chosenProjectType = null;

			$.each($scope.projectTypeList, function(index, project) {
				project.isActive = false;
			});
		} else {
			$scope.chosenProjectType = projectType;

			$.each($scope.projectTypeList, function(index, project) {
				if (project.value != projectType.value){
					project.isActive = false;
				}
			});

			projectType.isActive = !projectType.isActive;

			if (projectType.isActive) {
				$scope.isAllType = false;
			} else {
				$scope.isAllType = true;
			}
		}

			projectsReviewService.searchProjects($scope,function(data){
					$scope.totalRows = data.totalRow;
					$scope.projectList = data.data;
          projectsReviewService.decorateProjects($scope.projectList);

					paginationService.destoryPaginationView($scope);
					paginationService.generatePaginatioView($scope);
			});
	};

	$scope.searchStatus = function(projectStatus) {
		 $scope.currentPage = 1;
     //$scope.pageSize = 10;

		if (projectStatus == undefined) {
			$scope.isAllStatus = true;
			$scope.chosenProjectStatus = null;

			$.each($scope.projectStatusList, function(index, project) {
				project.isActive = false;
			});
		} else {
			$scope.chosenProjectStatus = projectStatus;

			$.each($scope.projectStatusList, function(index, project) {
				if (projectStatus != project) project.isActive = false;
			});

			projectStatus.isActive = !projectStatus.isActive;

			if (projectStatus.isActive) {
				$scope.isAllStatus = false;
			} else {
				$scope.isAllStatus = true;
			}
		}

		projectsReviewService.searchProjects($scope,function(data){
			$scope.totalRows = data.totalRow;
			$scope.projectList = data.data;
      projectsReviewService.decorateProjects($scope.projectList);

			paginationService.destoryPaginationView($scope);
			paginationService.generatePaginatioView($scope);
		});
	};

	$scope.viewProjectDetail = function(projectId){
		window.open("/home/selfrun/p/view?id=" + projectId);
	}

	$scope.searchByKeywords = function(e){
		if(e != null){
			var keycode = window.event?e.keyCode:e.which;

       if(keycode!=13){
          return;
       }
		}

		var keywords = $("#keywords").val();

		if(keywords == null){
			return;
		}

		$scope.currentPage = 1;
		$scope.keywords = keywords;

		projectsReviewService.searchProjects($scope,function(data){
				 $scope.totalRows = data.totalRow;
				 $scope.projectList = data.data;
         projectsReviewService.decorateProjects($scope.projectList);

				 paginationService.destoryPaginationView($scope);
				 paginationService.generatePaginatioView($scope);
		 });
	}
});
