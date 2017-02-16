var pageMapParam = {};
/**分页查询**/
function paginateFill($scope,serviceFn,dataProcess){
	if(typeof serviceFn!='function')return;
	serviceFn(function(data){
		 $scope.currentPage = data.currentPage;
		 $scope.totalRow = data.totalRow;
		 $scope.totalPage = new Array();
		 for(var i=0;i<data.totalPage;i++)$scope.totalPage[i]=i+1;

		 $scope.startRow = $scope.totalRow==undefined||$scope.totalRow==0?0:(data.currentPage > 0 ? data.pageSize * (data.currentPage - 1)+1 : 0);
		 $scope.currentRow = $scope.startRow==0?0:$scope.startRow+data.data.length-1;
		 $scope.dataList = data.data;
		 /**隐藏分页**/
		 if($scope.showPages!=undefined){
			 var index = 0;
			 $scope.showPages = [];
			 for(var i=0;i<$scope.totalPage.length;i++){
				 var prefix = parseInt(i/$scope.showLimit);
				 var currentPrefix = parseInt($scope.currentPage/$scope.showLimit);
				 if($scope.currentPage%$scope.showLimit==0)currentPrefix = $scope.currentPage/$scope.showLimit-1;
				 if(prefix==currentPrefix)$scope.showPages[index++] = $scope.totalPage[i];
				 if(prefix>currentPrefix)break;
			 }
		 }
		 dataProcess(data.data);
	});
}
/**HTTP**/
function createHttpService($http,paramMap,callback,errorFn){
	if(paramMap.get('url') == undefined)return;
	var method = paramMap.get('method')==undefined?'GET':paramMap.get('method');
	var url = paramMap.get('url');
	var params = pageMapParam;
	pageMapParam = {};
	$.each(params,function(key,data){
//		params[key] = encodeURI(data);
		params[key] = data;
	});
	$http({
		url:url,
		params:params,
		method:method
		}).success(function(data,header,config,status){
			callback(data);
		}).error(function(data,header,config,status){
			if(errorFn==undefined)
				showMessageDialog("错误信息", "系统内部错误,请联系管理员", function(){});
			else
				errorFn();
		});
}
/**
 * Angular
 * @param appName
 */
function AngularApp(appName,dependency){
	if(dependency==undefined)dependency = 'ngResource';
	var app = angular.module(appName,[dependency]);
	var services = new Array();
	var servicesMap = new Map();
	/**创建共享区域**/
	this.createSpace = function(spaceName){
		app.factory(spaceName, function(){
			var factoryFn = {};
			factoryFn.map = new Map();
//			factoryFn.arr = new Array();
			factoryFn.set = function(key,obj){
				factoryFn.map.put(key, obj);
			}
			factoryFn.get = function(key){
				return factoryFn.map.get(key);
			}
//			factoryFn.push = function(element){
//				factoryFn.arr.push(element);
//			}
//			factoryFn.pop = function(key){
//				factoryFn.arr[];
//			}
			return factoryFn;
		});
		services.push(spaceName);
	}
	/**创建服务**/
	this.createService = function(serviceName,serviceProcessFn){
		if(typeof serviceProcessFn!='function')return;
		var serviceProcessFn = serviceProcessFn;
		app.factory(serviceName, ['$http', function($http){
			var httpParamMap = new Map();
			var servicePackTmp = {};
			httpParamMap.convertJson(serviceProcessFn($http));
			httpParamMap.each(function(key,data,i){
				servicePackTmp[key] = function(callBack){
					var callBack = callBack;
					if(data==undefined)return;
					createHttpService($http, new Map(data),callBack);
				};
			});
			return servicePackTmp;
		}]);
		services.push(serviceName);
	}
	/**创建控制器**/
	this.createController = function(controllerName,controllerProcFn){
		if(typeof controllerProcFn!='function')return;
		var ctrlParam = new Array();
		ctrlParam.push('$scope');
		var servicesParam = "";
		var servicesPutCode = "";
		for(var i=0;i<services.length;i++){
			ctrlParam.push(services[i]);
			servicesParam +=","+services[i];
			servicesPutCode +="servicesMap.put('"+services[i]+"',"+services[i]+");";
		}
		var func = "function func($scope"+servicesParam+"){"
			+servicesPutCode
			+"controllerProcFn($scope,servicesMap);"
		+"}";
		eval(func);
		ctrlParam.push(func);
		//创建并绑定带有html解析能力的控制器
		app.controller(controllerName,ctrlParam).filter(
		    'to_trusted', ['$sce', function ($sce) {
		        return function (text) {
		            return $sce.trustAsHtml(text);
		        }
		    }]
		);
	}
	/**视图渲染完成指令**/

	this.bindRenderFinishedEvent = function($scope,renderFilterName,finishedProcessFn){
		app.directive(renderFilterName, function ($timeout) {
			return {
				restrict : 'A',
				link : function(scope, element, attr) {
					if (scope.$last === true) {
						$timeout(function() {
							//触发回调监听事件
							scope.$emit('onFinishRenderEvent');
						});
					}
				}
			}
		});
	};
	/**创建路由**/
	this.createRouter = function(routerProcessFn){
		app.config(routerProcessFn);
	}

	app.directive('onFinishRenderFilters', function ($timeout) {
		return {
			restrict : 'A',
			link : function(scope, element, attr) {
				if (scope.$last === true) {
					$timeout(function() {
						//触发回调监听事件
						scope.$emit('onFinishRenderEvent');
					});
				}
			}
		}
	});
	/**创建过滤器**/
	this.createFilter = function(filterName,callback){
		app.filter(filterName,function(){
			return function(input){
				return callback(input);
			}
		});
	}

	/**设置请求参数**/
	this.setParam = function(map){
		pageMapParam = {};
		pageMapParam = map;
	}
}
