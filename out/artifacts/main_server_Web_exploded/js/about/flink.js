$(function(){
	//判断用户登录身份
	if($("#userType").val()!=undefined && $("#userType").val()!='' && $("#userType").val()!=null && $("#userType").val()==-1){
		$("#wbmFriendLink").sortable();
		$("#wbmFriendLink").disableSelection();//禁止文字影响拖动		
	}

	$("#addFlinkForm").validate({
		errorElement: "span",
		ignore: [],
		messages: {
			workinghoursstart: {
				required: "请输入开始日期"
			},
			workinghoursend: {
				required: "请输入结束日期"
			}
		}
	});

})

//初始化时可直接加载不用预加载
initPicUploadWidgit("companyLogo", "/uploadify?ext=image&type=7","");

var logoPicUploadResult = false;

function initPicUploadWidgit(id, uploadURL, initImgHTML) {
	var eid = "#" + id;
    
	if (initImgHTML != null && initImgHTML.trim() != "") {
		$(eid).fileinput({
			allowedFileExtensions: ["jpg", "png", "gif"],
			maxFileSize: 5120,
			language: "zh",
			uploadUrl: uploadURL,
			showPreview: true,
			uploadAsync: false,
			maxFileCount: 1,
			showClose: false,
			showUpload: false,
			showCancel: false,
			showRemove: false,
			autoReplace: true,
			initialPreviewCount: 1,
			dropZoneEnabled: false,
			initialPreviewShowDelete: false,
			layoutTemplates: {
				actionDelete: ""
			},
			initialPreview: [initImgHTML]
			//initialPreviewShowDelete:true
			//initialPreview:["<img style='height:160px' src='http://loremflickr.com/200/150/people?random=1'>"]
		});
	} else {
//		alert("ooooo");
		
		$(eid).fileinput({
			allowedFileExtensions: ["jpg", "png", "gif"],
			maxFileSize: 5120,
			language: "zh",
			uploadUrl: uploadURL,
			showPreview: true,
			uploadAsync: false,
			maxFileCount: 1,
			showClose: false,
			showUpload: false,
			showCancel: false,
			showRemove: false,
			autoReplace: true,
			dropZoneEnabled: false,
			initialPreviewShowDelete: false,
			layoutTemplates: {
				actionDelete: ""
			}
		});
	}


	if (id == "companyLogo") {
		$(eid).on('filebatchuploaderror', function(event, data, previewId, index) {
			logoPicUploadResult = false;
			$("#companyLogoMsg").html("图片“" + idCardPicName + "” 上传失败");
		});

		$(eid).on('filebatchuploadsuccess', function(event, data, previewId, index) {
			logoPicUploadResult = true;
			$("#companyLogoMsg").html("图片“" + idCardPicName + "” 上传成功");
		});

		$(eid).on('filecleared', function(event, key) {
			logoPicUploadResult = false;

		});

		$(eid).on('filebrowse', function(event, key) {
			//
			$(eid).fileinput('clear');
		});

		$(eid).on('filebatchselected', function(event, key) {
			//
			$(eid).fileinput('upload');
			$("#companyLogoMsg").html("");
		});

		$(eid).on('filebatchpreupload', function(event, data, previewId, index) {
			idCardPicName = data.files[0].name;
		});

	}
}

//恢复默认排序
function refresh(){
	window.location.reload();
}

//添加友情链接
function addFlink(){
  	var murl = "/api/2/flink/add?name=" + $("#companyName").val() + "&link=" + $("#companyUrl").val();
  	$("#add").hide();
  	$.ajax({
  		type:"post",
  		url:murl,
  		async: false,
  		success:function(data){
  			showMessageDialog("友情链接","添加成功",function(){window.location.reload()})
  		}
  	})
}

var app = angular.module("flinkApp",[]);
app.controller("flinkController",function($scope,$http){
	$http({
		method:'get',
		url:'/api/flink/list'
	}).success(function(response){
		$scope.flinkList = response;
	})
	
	$scope.deleteFlink = function(str){
		var index = $scope.flinkList.indexOf(str);
		$scope.flinkList.splice(index,1);
		$http({
			method:'post',
			url:'/api/2/flink/del?id=' + str.id
		}).success(function(data){
			showMessageBox("删除成功","友情链接")	
		})
	}

	//修改排序
	$scope.addForm = function(){
		var arrStr = $("#wbmFriendLink").sortable('toArray')
		var data = "";
		$($scope.flinkList).each(function(index,element){
			data += "list["+index+"].id="+arrStr[index]+"&list["+index+"].ranking="+index+"&";
		})
		$http({
			method:'post',
			url:"/api/2/flink/order?" + data
		}).success(function(data){
			showMessageDialog("友情链接","修改成功",function(){window.location.reload()})
		})
	}

})