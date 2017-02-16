function parseData(data){
	
	return lineChartData;
}
function resetChart(data){
	var randomScalingFactor = function(){ return Math.round(Math.random()*100)};
	var lineLabels = [];
	var lineDatasets = [];
	
	var userCountSet = {};
	var projectCountSet = {};
	var weixinProjectCountSet = {};
	var personIdentifierCountSet = {};
	var compIdentifierCountSet = {};
	
	userCountSet.label = "用户数";
	userCountSet.fillColor = "rgba(220,220,220,0.2)";
	userCountSet.strokeColor = "rgba(220,220,220,1)";
	userCountSet.pointColor = "rgba(220,220,220,1)";
	userCountSet.pointStrokeColor = "#fff";
	userCountSet.pointHighlightFill = "#fff";
	userCountSet.pointHighlightStroke = "rgba(220,220,220,1)";
	userCountSet.data = [];
	
	projectCountSet.label = "项目数";
	projectCountSet.fillColor = "rgba(151,187,205,0.2)";
	projectCountSet.strokeColor = "rgba(151,187,205,1)";
	projectCountSet.pointColor = "rgba(151,187,205,1)";
	projectCountSet.pointStrokeColor = "#fff";
	projectCountSet.pointHighlightFill = "#fff";
	projectCountSet.pointHighlightStroke = "rgba(151,187,205,1)";
	projectCountSet.data = [];
	
	weixinProjectCountSet.label = "新预约";
	weixinProjectCountSet.fillColor = "rgba(213,166,189,0.2)";
	weixinProjectCountSet.strokeColor = "rgba(213,166,189,1)";
	weixinProjectCountSet.pointColor = "rgba(213,166,189,1)";
	weixinProjectCountSet.pointStrokeColor = "#fff";
	weixinProjectCountSet.pointHighlightFill = "#fff";
	weixinProjectCountSet.pointHighlightStroke = "rgba(213,166,189,1)";
	weixinProjectCountSet.data = [];
	
	personIdentifierCountSet.label = "认证总数(个人)";
	personIdentifierCountSet.fillColor = "rgba(103,63,204,0.2)";
	personIdentifierCountSet.strokeColor = "rgba(103,63,204,1)";
	personIdentifierCountSet.pointColor = "rgba(103,63,204,1)";
	personIdentifierCountSet.pointStrokeColor = "#fff";
	personIdentifierCountSet.pointHighlightFill = "#fff";
	personIdentifierCountSet.pointHighlightStroke = "rgba(103,63,204,1)";
	personIdentifierCountSet.data = [];
	
	compIdentifierCountSet.label = "认证总数(企业)";
	compIdentifierCountSet.fillColor = "rgba(123,171,150,0.2)";
	compIdentifierCountSet.strokeColor = "rgba(123,171,150,1)";
	compIdentifierCountSet.pointColor = "rgba(123,171,150,1)";
	compIdentifierCountSet.pointStrokeColor = "#fff";
	compIdentifierCountSet.pointHighlightFill = "#fff";
	compIdentifierCountSet.pointHighlightStroke = "rgba(123,171,150,1)";
	compIdentifierCountSet.data = [];
	
	$.each(data,function(index,rowCount){
		lineLabels.push(rowCount.time);
		userCountSet.data.push(rowCount.userCount);
		projectCountSet.data.push(rowCount.projectCount);
		weixinProjectCountSet.data.push(rowCount.weixinProjectCount);
		personIdentifierCountSet.data.push(rowCount.personIdentifierCount);
		compIdentifierCountSet.data.push(rowCount.compIdentifierCount);
	});	
	lineDatasets.push(userCountSet);
	lineDatasets.push(projectCountSet);
	lineDatasets.push(weixinProjectCountSet);
	lineDatasets.push(personIdentifierCountSet);
	lineDatasets.push(compIdentifierCountSet);
	var lineChartData = {
		labels : lineLabels,
		datasets : lineDatasets
	}
	$("#lineCanvas").remove();
	$("#chart").append('<canvas id="lineCanvas" height="350" width="600"></canvas>');
	var ctx = document.getElementById("lineCanvas").getContext("2d");
	window.lineChart = new Chart(ctx).Line(lineChartData, {
		responsive: true
	});
}
var app = new AngularApp('dataAnylizeApp');
/**服务**/
app.createService('dataAnylizeService',function(){
	return{
		queryDataByTimeGap:{method:'GET',url:'/api/2/u/loadAnylizeData'}
	};
});
/**列表模块**/
app.createController('dataAnylizeCtrl',function($scope,services){	
	generateDatePickerWithoutStartDate(".endDateDiv","endDateDiv");
	generateDatePickerWithoutStartDate(".startDateDiv","startDateDiv");
	/**初始化**/
	$scope.optionList = new Array();
	$scope.optionList.push({name:'按天',value:'day'});
	$scope.optionList.push({name:'按月',value:'month'});
	$scope.option = $scope.optionList[0];
	$scope.timeOptionList = new Array();
	$scope.timeOptionList.push({name:'时间选择',value:'0'});
	$scope.timeOptionList.push({name:'今天',value:'1'});
	$scope.timeOptionList.push({name:'最近三天',value:'3'});
	$scope.timeOptionList.push({name:'最近一周',value:'4'});
	$scope.timeOption = $scope.timeOptionList[0];
	
	$scope.startTime = new Date().format("yyyy-MM-dd");
	app.setParam({
		startQueryTime:$scope.startTime,
		option:$scope.option.value
	});
	services.get('dataAnylizeService').queryDataByTimeGap(function(data){		
		$scope.totalTableList = data.data;
		$scope.total = data.total;
		resetChart(data.data);
	});
	$scope.setTimeQuery = function(time){		
		var startTime = $scope.startTime;
		var endTime = $scope.endTime;		
		var params = {};
		if(time==$scope.startTime){
			if(new Date(time).getTime()>new Date().getTime()){
				$scope.startTime=new Date().parse("yyyy-MM-dd");
				return;
			}
		}else{
			if(new Date(startTime).getTime()>new Date(endTime).getTime()){
				$scope.endTime = $scope.startTime;
				return;
			}
		}
		if($scope.endTime!=undefined)params.endQueryTime = $scope.endTime;
		if($scope.startTime!=undefined)params.startQueryTime = $scope.startTime;		
		params.option = $scope.option.value;
		app.setParam(params);
		services.get('dataAnylizeService').queryDataByTimeGap(function(data){		
			$scope.totalTableList = data.data;
			$scope.total = data.total;
			resetChart(data.data);
		});
	}
	$scope.selectOption = function(option){
		$scope.option = option;
		$scope.timeOption = $scope.timeOptionList[0];
		var startTime = $scope.startTime;
		var endTime = $scope.endTime;
		var params = {};
		if($scope.endTime!=undefined)params.endQueryTime = $scope.endTime;
		if($scope.startTime!=undefined)params.startQueryTime = $scope.startTime;
		params.option = $scope.option.value;
		app.setParam(params);
		services.get('dataAnylizeService').queryDataByTimeGap(function(data){		
			$scope.totalTableList = data.data;
			$scope.total = data.total;
			resetChart(data.data);
		});
	}
	$scope.selectTimeOption = function(timeOption){
		$scope.timeOption = timeOption;
		$scope.option = $scope.optionList[0];
		var params = {};			
		params.option = "day";
		params.timeOption = timeOption.value;
		app.setParam(params);
		services.get('dataAnylizeService').queryDataByTimeGap(function(data){			
			$scope.totalTableList = data.data;
			$scope.total = data.total;
			resetChart(data.data);
		});
	}
});