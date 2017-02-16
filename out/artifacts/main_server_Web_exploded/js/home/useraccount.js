$(document).ready(function(){
	
	//make the dialog draggable
	$('.modal-dialog').draggable({
			handle: ".modal-header"
	});
	
	
	jQuery.validator.addMethod("isMobilePhoneNumber", function() {
		var mobile = $("#contactNumber").val();
		
		return isMobile(mobile);
	}, "手机号码不正确");
		
	jQuery.validator.addMethod("isAlipayAccount",function(value,element,param){
		var account = value;
		return isEmail(account) || isMobile(account);
	},"请输入正确的支付账号");
		
	$("#checkAccount").validate({
		ingnor:[],
		rules:{
			amount:{
				required:true,
				digits:true,
				min:100
			}
		},
		messages: {
			amount: {
				required: "请输入提现金额",
				digits:"请输入合法的整数",
				min:"提现金额不能少于100元"
			}
		},
		errorElement: "span",
		errorPlacement: function(error, element) {
			error.appendTo(element);
			$("#accountErr").append(error);
		}
	});
	
	$("#checkPayAccount").validate({
		ingnor:[],
		rules:{
			payAccount:{
				required:true,
				isAlipayAccount:true
			}
		},
		messages: {
			payAccount: {
				required: "请输入支付账号",
				isAlipayAccount:"请填写正确的支付账号"
			}
		},
		errorElement: "span",
		errorPlacement: function(error, element) {
			error.appendTo(element);
			$("#payAccountErr").append(error);
		}
	});
	
})

/**Angular app**/
var app = new AngularApp('useraccountApp');
app.createService('recordService', function($http) {
	return {
		queryAccountRecord: {
			method: 'POST',
			url: '/api/1/u/accountdetail'
		}
	};
});
app.createController('useraccountController', function($scope, services) {
	/**初始化**/
	$scope.pageSize = 8;
	$scope.showLimit = 5;
	$scope.showPages = [];
	$scope.recordType = 0;    //0 入账    1提现

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
		typeStr: $scope.recordType,
		pageSize: $scope.pageSize
	});

	paginateFill($scope, services.get('recordService').queryAccountRecord, function(data) {
		$.each($scope.dataList, function(index, project) {
			decorateProject(project)
		});
	});
	
	$scope.getRecord = function(){
		var ele = document.querySelectorAll(".content-line")[0];
		var eles = $(ele).children();
		for(var i=0;i<eles.length;i++){
			if($(eles[i]).hasClass("active")){
				eles[i].classList.remove("active");
			}else{
				eles[i].classList.add("active");
				$scope.recordType = $(eles[i]).attr("type");
			}
		}
		app.setParam({
			currentPage: 1,
			typeStr: $scope.recordType,
			pageSize: $scope.pageSize
		});
	
		paginateFill($scope, services.get('recordService').queryAccountRecord, function(data) {
			$.each($scope.dataList, function(index, project) {
				decorateProject(project)
			});
		});
	}
	
	$scope.firstPage = function() {
		var firstPage = 1;
		if (firstPage != $scope.currentPage) {
			app.setParam({
				currentPage: firstPage,
				typeStr: $scope.recordType,
				pageSize: $scope.pageSize
			});
			paginateFill($scope, services.get('recordService').queryAccountRecord, function(data) {
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
				typeStr: $scope.recordType,
				pageSize: $scope.pageSize
			});
			paginateFill($scope, services.get('recordService').queryAccountRecord, function(data) {
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
				typeStr: $scope.recordType,
				pageSize: $scope.pageSize
			});
			paginateFill($scope, services.get('recordService').queryAccountRecord, function(data) {
				$.each($scope.dataList, function(index, project) {
					decorateProject(project)
				});
			});
		}
	}
	$scope.lastPage = function() {
			var lastPage = $scope.totalPage.length;
			if (lastPage != $scope.currentPage) {
				app.setParam({
					currentPage: lastPage,
					typeStr: $scope.recordType,
					pageSize: $scope.pageSize
				});
				paginateFill($scope, services.get('recordService').queryAccountRecord, function(data) {
					$.each($scope.dataList, function(index, project) {
						decorateProject(project)
					});
				});
			}
		}
		//跳页
	$scope.jump = function() {
		var page = $("#pageJump").val().trim();
		if (page > 0 && page <= $scope.totalPage.length) {
			app.setParam({
				currentPage: page,
				typeStr: $scope.recordType,
				pageSize: $scope.pageSize
			});
			paginateFill($scope, services.get('recordService').queryAccountRecord, function(data) {
				$.each($scope.dataList, function(index, project) {
					decorateProject(project)
				});
			});
		}
	}

	//设置查询记录数
	$scope.setPageSize = function() {
		app.setParam({
			currentPage: 1,
			typeStr: $scope.recordType,
			pageSize: $scope.pageSize
		});
		paginateFill($scope, services.get('recordService').queryAccountRecord, function(data) {
			$.each($scope.dataList, function(index, project) {
				decorateProject(project)
			});
		});
	}

	//分页
	$scope.pagenate = function(page) {
		if (page != $scope.currentPage) {
			app.setParam({
				currentPage: page,
				typeStr: $scope.recordType,
				pageSize: $scope.pageSize
			});
			paginateFill($scope, services.get('recordService').queryAccountRecord, function(data) {
				$.each($scope.dataList, function(index, project) {
					decorateProject(project)
				});
			});
		}
	}
});

//提现
function getCash(){
	var amount = $("#amount").val();
	var param = "amount=" + amount;
	$.ajax({
		type:"POST",
		url:"/api/1/u/getcash",
		data:param,
		async:false,
		success:function(data){
			var message = data.msg?data.msg:data.errorMsg;
			showMessageDialog("提示", message, function(){
	    			window.location.reload();
			});
		}
	});
}
//录入支付宝账号
function updatePayAccount(){
	var payAccount = $("#payAccount").val();
	var param = "payAccount=" + payAccount;
	$.ajax({
		type:"POST",
		url:"/api/1/u/updateaccount",
		data:param,
		async:false,
		success:function(data){
			var message = data.msg?data.msg:data.errorMsg;
			showMessageDialog("提示", message, function(){
	    			window.location.reload();
			});
		}
	});
}
function decorateProject(data) {
	data.date = new Date(data.date).Format("yyyy-MM-dd hh:mm");
	data.confirmTime = data.status==0?"---":new Date(data.date).Format("yyyy-MM-dd hh:mm");
	data.status = data.status==1?"已汇款":"申请中";
	
}
// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  