var iconOF = false;
function showMore(str){
	if(iconOF)
	{
		str.querySelector(".dropDownIcon").style.transform="rotate(225deg)";
		document.querySelector(".dropDownContent").style.height="50px"
		iconOF = false
	}else{
		str.querySelector(".dropDownIcon").style.transform="rotate(45deg)";
		document.querySelector(".dropDownContent").style.height="200px"
		iconOF = true
	}
}
/**
 * angular common func
 */
function generatePaginateParam($scope,query,currentPage,pageSize,categoryStr){
	var filterParam = {};
	filterParam['query'] = query;
	filterParam['currentPage'] = currentPage;
	filterParam['pageSize'] = pageSize;
	filterParam['categoryStr'] = categoryStr;
	//省市过滤
	if($scope.province!=undefined&&$scope.city!=undefined){
		var regionQuery = $scope.province.name+"省"+$scope.city.name+"市";
		var regionId = $scope.city.id;
		if($scope.cityList.length==1){
			regionQuery = $scope.city.name+"市";
		}
		filterParam['regionId'] = regionId; 
	}
	//标签
	if($scope.caseTypeStr!=""){
		filterParam['caseTypeStr'] = $scope.caseTypeStr; 
	}
	app.setParam(filterParam);
}
/**
 * angular app
 */
var app = new AngularApp("serverListApp");
app.createService('memberService', function($http) {	
	return {
		query:{method:'POST',url:'/api/u/providerList'},
		update:{method:'POST',url:'/api/1/u/providerDisplay'},
		remove:{method:'POST',url:'/api/1/u/providerDelete'}
	};
});
app.createService('regionService', function($http) {	
	return {
			queryProvince:{method:'GET',url:'/api/region/list'},
			queryCity:{method:'GET',url:'/api/region/citys'},
			queryCaseType:{method:'GET',url:'/api/tag/caseType'}
	};
});
app.createController('serverListCtrl',function($scope,services){
	//初始化
	$scope.isAllSelected =true;
	$scope.caseTypeStr = "";
	/**分页**/
	$scope.showPages = [];
	$scope.showLimit = 5;
	$scope.pageSize = 8;
	generatePaginateParam($scope,$("#pKey").val().trim(), 1, $scope.pageSize, $("#category").val());
	paginateFill($scope,services.get('memberService').query,function(data){		
		$.each(data,function(index,serverData){

			serverData.firstPic = jsonDecode(serverData.comPicList,true);			
		});	
	});
	/**地区省**/
	services.get('regionService').queryProvince(function(data){
		$scope.provinceList = data;
	});
	/**擅长领域**/
	services.get('regionService').queryCaseType(function(data){
		$scope.caseTypeList = data;
		$.each($scope.caseTypeList,function(index,element){
			element.active = false;
		})
	});
	//编辑服务商
    $scope.editMember = function(uid){
		window.open('/admin/compinfo_modify?uid='+uid);
	}
    //服务商上下架
    $scope.memberDisplay = function($index,uid,isDisplay){
		var actionType = isDisplay==0?1:0;		
//		var member = $scope.serverList[$index];
		app.setParam({uid:uid,actionType:actionType});
		services.get('memberService').update(function(data){
			app.setParam({query:$("#pKey").val().trim(),currentPage:$scope.currentPage,pageSize:$scope.pageSize,categoryStr:$("#category").val()});
			paginateFill($scope,services.get('memberService').query,function(data){
				$.each(data,function(index,serverData){
					serverData.firstPic = jsonDecode(serverData.comPicList,true);
				});	
	    	});
		});
    }
    //删除服务商
    $scope.deleteMember = function(index,uid){
		showConfirmDialog("确认信息", "确定删除?",function(){
			app.setParam({uid:uid});
			services.get('memberService').remove(function(data){	
				showMessageDialog("提示", data.message, function(){
					app.setParam({query:$("#pKey").val().trim(),currentPage:$scope.currentPage,pageSize:$scope.pageSize,categoryStr:$("#category").val()});
					paginateFill($scope,services.get('memberService').query,function(data){
						$.each(data,function(index,serverData){
							serverData.firstPic = jsonDecode(serverData.comPicList,true);
						});	
			    	});
				});
			});
		});
    }	
    //服务商查看
    $scope.openLink = function(name){
		window.open('/public/m/'+name);
	}
  //设置查询记录数
    $scope.setPageSize = function(){ 
    	generatePaginateParam($scope,$("#pKey").val().trim(), 1, $scope.pageSize, $("#category").val());
    	paginateFill($scope,services.get('memberService').query,function(data){
    		$.each(data,function(index,serverData){
    			serverData.firstPic = jsonDecode(serverData.comPicList,true);
    		});	
    	});
    }
    //分页
    $scope.pagenate = function(page){
    	if(page!=$scope.currentPage){
    		generatePaginateParam($scope,$("#pKey").val().trim(), page, $scope.pageSize, $("#category").val());
    		paginateFill($scope,services.get('memberService').query,function(data){
        		$.each(data,function(index,serverData){
        			serverData.firstPic = jsonDecode(serverData.comPicList,true);
        		});	
        	});
    	}
    }
    //跳页
    $scope.jump = function(){
    	var page = $("#pageJump").val().trim();
    	if(page>0&&page<=$scope.totalPage.length){
    		generatePaginateParam($scope,$("#pKey").val().trim(), page, $scope.pageSize, $("#category").val());
    		paginateFill($scope,services.get('memberService').query,function(data){
    			$.each(data,function(index,serverData){
    				serverData.firstPic = jsonDecode(serverData.comPicList,true);
    			});	
        	});
    	}
    }
    //模糊查询
    $scope.doFuzzySearch = function(){
    	generatePaginateParam($scope,$("#pKey").val().trim(), 1, $scope.pageSize, $("#category").val());
    	paginateFill($scope,services.get('memberService').query,function(data){
    		$.each(data,function(index,serverData){
    			serverData.firstPic = jsonDecode(serverData.comPicList,true);
    		});	
    	});
    }
    //省份选择
    $scope.selectProvince = function(){
    	if($scope.province!=undefined){
        	var pId = $scope.province.id;
	    	app.setParam({id:pId});
	    	services.get('regionService').queryCity(function(data){
	    		$scope.cityList = data;    			    	
	    	});
    	}else{
    		$scope.cityList = null;
    		generatePaginateParam($scope,$("#pKey").val().trim(), 1, $scope.pageSize, $("#category").val());
        	paginateFill($scope,services.get('memberService').query,function(data){
        		$.each(data,function(index,serverData){
        			serverData.firstPic = jsonDecode(serverData.comPicList,true);
        		});	
        	});
    	}
    }
    //城市选择
    $scope.selectCity = function(){
    	if($scope.city!=undefined){
	    	var provinceName = $scope.province.name;
			var cityName = $scope.city.name;
			var regionQuery = provinceName+"省"+cityName+"市";
			if($scope.cityList.length==1){
				regionQuery = cityName+"市";
			}

			generatePaginateParam($scope, $("#pKey").val().trim(),1, $scope.pageSize, $("#category").val());
			paginateFill($scope,services.get('memberService').query,function(data){
	    		$.each(data,function(index,serverData){
	    			serverData.firstPic = jsonDecode(serverData.comPicList,true);
	    		});	
	    	});
    	}
    }
    //领域选择
    $scope.selectCaseType = function(tag){
    	tag.active = !tag.active;
    	if(tag.active){
    		$scope.isAllSelected = false;
    		$.each($scope.caseTypeList,function(index,element){
        		if(tag.value!=element.value)element.active = false;    		
        	});
        	$scope.caseTypeStr = tag.value;
        	generatePaginateParam($scope, $("#pKey").val().trim(),1, $scope.pageSize, $("#category").val());
        	paginateFill($scope,services.get('memberService').query,function(data){
        		$.each(data,function(index,serverData){
        			serverData.firstPic = jsonDecode(serverData.comPicList,true);
        		});	
        	});
    	}
    	else {
    		$scope.isAllSelected = true;
        	$.each($scope.caseTypeList,function(index,element){
        		element.active = false;
        		$scope.isAllSelected = true;
        	});
        	$scope.caseTypeStr = "";
        	generatePaginateParam($scope, $("#pKey").val().trim(),1, $scope.pageSize, $("#category").val());
        	paginateFill($scope,services.get('memberService').query,function(data){
        		$.each(data,function(index,serverData){
        			serverData.firstPic = jsonDecode(serverData.comPicList,true);
        		});	
        	});
    	}
    }
    $scope.selectAllCaseType = function(){
    	$scope.isAllSelected = true;
    	$.each($scope.caseTypeList,function(index,element){
    		element.active = false;
    		$scope.isAllSelected = true;
    	});
    	$scope.caseTypeStr = "";
    	generatePaginateParam($scope, $("#pKey").val().trim(),1, $scope.pageSize, $("#category").val());
    	paginateFill($scope,services.get('memberService').query,function(data){
    		$.each(data,function(index,serverData){
    			serverData.firstPic = jsonDecode(serverData.comPicList,true);
    		});	
    	});
    }
    $scope.firstPage = function(){
    	var firstPage = 1;
    	if(firstPage!=$scope.currentPage){
    		generatePaginateParam($scope,$("#pKey").val().trim(), firstPage, $scope.pageSize, $("#category").val());
    		paginateFill($scope,services.get('memberService').query,function(data){
        		$.each(data,function(index,serverData){
        			serverData.firstPic = jsonDecode(serverData.comPicList,true);
        		});	
        	});
    	}
    }
    $scope.prevPage = function(){
    	if($scope.currentPage>1){
        	$scope.currentPage--;
        	generatePaginateParam($scope, $("#pKey").val().trim(),$scope.currentPage, $scope.pageSize, $("#category").val());
        	paginateFill($scope,services.get('memberService').query,function(data){
        		$.each(data,function(index,serverData){
        			serverData.firstPic = jsonDecode(serverData.comPicList,true);
        		});	
        	});	
    	}
    }
    $scope.nextPage = function(){
    	if($scope.currentPage<$scope.totalPage.length){
        	$scope.currentPage++;
        	generatePaginateParam($scope, $("#pKey").val().trim(),$scope.currentPage, $scope.pageSize, $("#category").val());
        	paginateFill($scope,services.get('memberService').query,function(data){
        		$.each(data,function(index,serverData){
        			serverData.firstPic = jsonDecode(serverData.comPicList,true);
        		});	
        	});	
    	}
    }
    $scope.lastPage = function(){    	
    	var lastPage = $scope.totalPage.length;
    	if(lastPage!=$scope.currentPage){
    		generatePaginateParam($scope,$("#pKey").val().trim(), lastPage, $scope.pageSize, $("#category").val());
    		paginateFill($scope,services.get('memberService').query,function(data){
        		$.each(data,function(index,serverData){
        			serverData.firstPic = jsonDecode(serverData.comPicList,true);
        		});	
        	});
    	}
    }
    //渲染轮播回调监听
    $scope.$on('onFinishRenderEvent', function (onFinishRenderEvent) {
    	 jQuery(".proj-text").slide({ mainCell:".bd ul",effect:"topLoop",autoPlay:true,vis:1,scroll:1,trigger:"click"});
	});
});
