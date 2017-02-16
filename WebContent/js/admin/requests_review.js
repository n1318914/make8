/**Angular app**/
var app = new AngularApp('projectsApp');
app.createService('projectService', function($http) {
	return {
		queryProjectList:{method:'POST',url:'/api/2/p/loadProjectList'},
		projectTypeList:{method:'GET',url:'/api/label/list'},
		queryConsultantList:{method:'GET',url:'/api/2/u/getallconsultants'}
	};
});
//转义字符串
app.createFilter("decodeStr",function(input){
	if(input.length == 0) return "";
	input = input.replace(/&amp;/g,"&");
	input = input.replace(/&lt;/g,"<");
	input = input.replace(/&gt;/g,">");
	input = input.replace(/&nbsp;/g," ");
	input = input.replace(/&#39;/g,"\'");
	input = input.replace(/&quot;/g,"\"");
	return input;
});

app.createController('projectsCtrl',function($scope,services){
	/**初始化**/
	$scope.pageSize = 8;
	$scope.showLimit = 5;
	$scope.showPages = [];
	$scope.projectType = "";
	$scope.consultant = "";
	$scope.isAllType = true;
	$scope.status = -2;
	$scope.isAllStatus = true;
	$scope.isAllConsultants = true;
	$scope.keywords = "";

	/**状态**/
	$scope.statusList = new Array();
	$scope.statusList.push({name:'待审核',value:'-1'});
	$scope.statusList.push({name:'待启动',value:'0'});
	$scope.statusList.push({name:'开发中',value:'1'});
	$scope.statusList.push({name:'已完成',value:'2'});
	$scope.statusList.push({name:'未通过',value:'3'});
	$scope.statusList.push({name:'已关闭',value:'4'});
	/**标签**/
	services.get('projectService').projectTypeList(function(data){
		$scope.projectTypeList = new Array();
		$.each(data,function(index,dictItem){
			if(dictItem.dictGroupId==4){
				$scope.projectTypeList.push(dictItem);
			}
		});
	});
	/**顾问**/
	services.get('projectService').queryConsultantList(function(data){
		$scope.consultantList = data.consultants;
	});
	/**列表**/
	app.setParam({
		currentPage:1,
		query:$scope.keywords,
		typeStr:$scope.projectType,
		status:$scope.status,
		pageSize:$scope.pageSize,
		consultantId:$scope.consultant
	});
	paginateFill($scope,services.get('projectService').queryProjectList,function(data){
		$.each($scope.dataList,function(index,project){
			if(project.type.indexOf(",")>=0){
				var firstType = project.type.split(",");
				project.type = firstType[0]==''?firstType[1]:firstType[0];
			}
		});
	});

    $scope.firstPage = function(){
    	var firstPage = 1;
    	if(firstPage!=$scope.currentPage){
    		app.setParam({
    			currentPage:firstPage,
    			query:$scope.keywords,
    			typeStr:$scope.projectType,
    			status:$scope.status,
					consultantId:$scope.consultant,
    			pageSize:$scope.pageSize
    		});
    		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
    			$.each($scope.dataList,function(index,project){
    				if(project.type.indexOf(",")>=0){
    					var firstType = project.type.split(",");
        				project.type = firstType[0]==''?firstType[1]:firstType[0];
    				}
    			});
    		});
    	}
    }
    $scope.prevPage = function(){
    	if($scope.currentPage>1){
        	$scope.currentPage--;
        	app.setParam({
    			currentPage:$scope.currentPage,
    			query:$scope.keywords,
    			typeStr:$scope.projectType,
    			status:$scope.status,
					consultantId:$scope.consultant,
    			pageSize:$scope.pageSize
    		});
    		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
    			$.each($scope.dataList,function(index,project){
    				if(project.type.indexOf(",")>=0){
    					var firstType = project.type.split(",");
        				project.type = firstType[0]==''?firstType[1]:firstType[0];
    				}
    			});
    		});
    	}
    }
    $scope.nextPage = function(){
    	if($scope.currentPage<$scope.totalPage.length){
        	$scope.currentPage++;
        	app.setParam({
    			currentPage:$scope.currentPage,
    			query:$scope.keywords,
    			typeStr:$scope.projectType,
    			status:$scope.status,
					consultantId:$scope.consultant,
    			pageSize:$scope.pageSize
    		});
    		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
    			$.each($scope.dataList,function(index,project){
    				if(project.type.indexOf(",")>=0){
    					var firstType = project.type.split(",");
        				project.type = firstType[0]==''?firstType[1]:firstType[0];
    				}
    			});
    		});
    	}
    }
    $scope.lastPage = function(){
    	var lastPage = $scope.totalPage.length;
    	if(lastPage!=$scope.currentPage){
        	app.setParam({
    			currentPage:lastPage,
    			typeStr:$scope.projectType,
    			query:$scope.keywords,
    			status:$scope.status,
					consultantId:$scope.consultant,
    			pageSize:$scope.pageSize
    		});
    		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
    			$.each($scope.dataList,function(index,project){
    				if(project.type.indexOf(",")>=0){
    					var firstType = project.type.split(",");
        				project.type = firstType[0]==''?firstType[1]:firstType[0];
    				}
    			});
    		});
    	}
    }
    //跳页
    $scope.jump = function(){
    	var page = $("#pageJump").val().trim();
    	if(page>0&&page<=$scope.totalPage.length){
    		app.setParam({
    			currentPage:page,
    			typeStr:$scope.projectType,
    			query:$scope.keywords,
    			status:$scope.status,
					consultantId:$scope.consultant,
    			pageSize:$scope.pageSize
    		});
    		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
    			$.each($scope.dataList,function(index,project){
    				if(project.type.indexOf(",")>=0){
    					var firstType = project.type.split(",");
        				project.type = firstType[0]==''?firstType[1]:firstType[0];
    				}
    			});
    		});
    	}
    }

    //设置查询记录数
    $scope.setPageSize = function(){
    	app.setParam({
			currentPage:1,
			typeStr:$scope.projectType,
			query:$scope.keywords,
			status:$scope.status,
			pageSize:$scope.pageSize,
			consultantId:$scope.consultant
		});
		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
			$.each($scope.dataList,function(index,project){
				if(project.type.indexOf(",")>=0){
					var firstType = project.type.split(",");
    				project.type = firstType[0]==''?firstType[1]:firstType[0];
				}
			});
		});
    }

    //分页
    $scope.pagenate = function(page){
    	if(page!=$scope.currentPage){
    		app.setParam({
    			query:$scope.keywords,
    			currentPage:page,
    			typeStr:$scope.projectType,
    			status:$scope.status,
    			pageSize:$scope.pageSize,
					consultantId:$scope.consultant
    		});
    		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
    			$.each($scope.dataList,function(index,project){
    				if(project.type.indexOf(",")>=0){
    					var firstType = project.type.split(",");
        				project.type = firstType[0]==''?firstType[1]:firstType[0];
    				}
    			});
    		});
    	}
    }

    // $scope.search = function(){
    // 	app.setParam({
		// 	query:$scope.keywords,
		// 	currentPage:1,
		// 	typeStr:$scope.projectType,
		// 	status:$scope.status,
		// 	pageSize:$scope.pageSize,
		// 	consultantId:$scope.consultant
		// });
		// paginateFill($scope,services.get('projectService').queryProjectList,function(data){
		// 	$.each($scope.dataList,function(index,project){
		// 		if(project.type.indexOf(",")>=0){
		// 			var firstType = project.type.split(",");
    // 				project.type = firstType[0]==''?firstType[1]:firstType[0];
		// 		}
		// 	});
		// });
    // }

    $scope.keySearch = function(e){
			if(e != null){
				var keycode = window.event?e.keyCode:e.which;

				 if(keycode != 13){
						return;
				 }
			}
        $scope.keywords = $("#keywords").val();

    		app.setParam({
    			query:$scope.keywords,
    			currentPage:1,
    			typeStr:$scope.projectType,
    			status:$scope.status,
    			pageSize:$scope.pageSize,
					consultantId:$scope.consultant
    		});
    		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
    			$.each($scope.dataList,function(index,project){
    				if(project.type.indexOf(",")>=0){
    					var firstType = project.type.split(",");
        				project.type = firstType[0]==''?firstType[1]:firstType[0];
    				}
    			});
    		});
    }

    $scope.searchType = function(projectType){
    	var typeStr = "";
    	if(projectType==undefined){
    		$scope.isAllType = true;
        	$.each($scope.projectTypeList,function(index,project){
        		project.isActive = false;
        	});
    	}else{
        	$scope.projectType=  projectType.value;
        	typeStr = projectType.value;
        	$.each($scope.projectTypeList,function(index,project){
        		if(projectType!=project)project.isActive = false;
        	});
        	projectType.isActive = !projectType.isActive;
        	if(projectType.isActive){
        		$scope.isAllType = false;
        	}else {
        		$scope.isAllType = true;
        		typeStr = "";
        	}
    	}
    	$scope.projectType = typeStr;
    	app.setParam({
			query:$scope.keywords,
			currentPage:1,
			typeStr:typeStr,
			status:$scope.status,
			pageSize:$scope.pageSize,
			consultantId:$scope.consultant
		});
		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
			$.each($scope.dataList,function(index,project){
				if(project.type.indexOf(",")>=0){
					var firstType = project.type.split(",");
    				project.type = firstType[0]==''?firstType[1]:firstType[0];
				}
			});
		});
    }

    $scope.findStatus = function(statusObj){
    	var status = -2;
    	if(statusObj==undefined){
    		$scope.isAllStatus = true;
        	$.each($scope.statusList,function(index,s){
        		s.isActive = false;
        	});
    	}else{
        	$scope.status=  statusObj.value;
        	status = statusObj.value;
        	$.each($scope.statusList,function(index,s){
        		if(statusObj!=s)s.isActive = false;
        	});
        	statusObj.isActive = !statusObj.isActive;
        	if(statusObj.isActive){
        		$scope.isAllStatus = false;
        	}else {
        		$scope.isAllStatus = true;
        		status = -2;
        	}
    	}
    	$scope.status = status;

    	app.setParam({
			query:$scope.keywords,
			currentPage:1,
			status:status,
			typeStr:$scope.projectType,
			pageSize:$scope.pageSize,
			consultantId:$scope.consultant
		});

		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
			$.each($scope.dataList,function(index,project){
				if(project.type.indexOf(",")>=0){
					var firstType = project.type.split(",");
    				project.type = firstType[0]==''?firstType[1]:firstType[0];
				}
			});
		});
    }


		$scope.searchConsultants = function(consultant){
    	var status = -2;
    	if(consultant==undefined){
    		$scope.isAllConsultants = true;
        $scope.consultant = "";
        	$.each($scope.consultantList,function(index,consultant){
        		consultant.isActive = false;
        	});
    	}else{
        	$scope.consultant =  consultant.id;

        	$.each($scope.consultantList,function(index,consultantF){
        		if(consultantF != consultant){
							consultantF.isActive = false;
						}
        	});

        	consultant.isActive = !consultant.isActive;

        	if(consultant.isActive){
        		$scope.isAllConsultants = false;
        	}else {
        		$scope.isAllStatus = true;
        	}
    	}

    	app.setParam({
			query:$scope.keywords,
			currentPage:1,
			status:$scope.status,
			consultantId:$scope.consultant,
			typeStr:$scope.projectType,
			pageSize:$scope.pageSize
		});

		paginateFill($scope,services.get('projectService').queryProjectList,function(data){
			$.each($scope.dataList,function(index,project){
				if(project.type.indexOf(",")>=0){
					var firstType = project.type.split(",");
    				project.type = firstType[0]==''?firstType[1]:firstType[0];
				}
			});
		});
    }

    $scope.viewProject = function(project){
    	window.open("./request_review?id="+project.id);
    }

		$scope.viewProgress = function(project){
			window.open("../home/selfrun/p/view?id="+project.id);
		}
});
