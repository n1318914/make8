<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>项目审核 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    
    <script type="text/javascript">
   			var gURL = "/api/2/p/reserve";
   				
       
    	$(document).ready(function(){
    		initProjects();
    		initSearchView();
    		
    		//make the dialog draggable
    		$('.modal-dialog').draggable({
            handle: ".modal-header"
        });
        
        //
        $("#pReservationClosedForm").validate({
			  	errorElement : "span"
			  });
    	});
    	
    	
    	function initProjects(){
    			internalSearch(gURL,"-1",null,1);
    	}
    	
    	
    	var ITEMHEIGHT = 93;
    	var DIVOFFSET = 350;
    	
    	
    	function generateProjectView(data){
    		if(data == null){
    			return;
    		}
    		
    		var itemHTML;
    		   		
    		showFooter(isFooterFixed(DIVOFFSET,ITEMHEIGHT,data.length));
    		 
    		//empty the original content
    		$("#projectView").html("");
    		for(i = 0; i < data.length; i++){
    			itemHTML = generateReserveItem(data[i]);
    			$("#projectView").append(itemHTML);
    		}
    	}
		
    	
     var paginationView;
		 var gPageSize = 10;
		 
		 function initPaginationView(data,status,type){
		 	if(paginationView == null){
					paginationView = $("#paginationView").page({ 
															total: data.totalRow,
															showJump: true,
    													pageSize:gPageSize,
    													showInfo: true,
															showPageSizes: true,
    													firstBtnText: '首页',
													    lastBtnText: '尾页',
													    prevBtnText: '上一页',
													    nextBtnText: '下一页',
													    jumpBtnText:'跳转',
													    infoFormat: '{start} ~ {end}条，共{total}条'
				                     });
				                     
				  paginationView.on("pageClicked", function (event, pageNumber) {
														  internalSearch(gURL,status,type,pageNumber + 1,gPageSize);
														}).on("jumpClicked", function (event, pageNumber) {
															internalSearch(gURL,status,type,pageNumber + 1,gPageSize);
														}).on("pageSizeChanged", function (event, pageSize) {
										           //
										           destoryPaginationView();
										           gPageSize = pageSize;
										           internalSearch(gURL,status,type,1,pageSize)
														});
				}
		 }
		 
		 function destoryPaginationView(){
		 	if(paginationView != null){
		 		paginationView.page('destroy');
		 		paginationView = null;
		 	}
		 }
		 
    function generateReserveItem(pData){
   			var type = pData.type;
   			var id = pData.id;
   			var content = pData.content;
    		var jingbiaoNums = pData.jingbiaoNums;
    		var statusCode = pData.status;
    		var remark= pData.remark;
    	  var priceRange = pData.priceRange;
    	  var period = pData.period;
    		var contactsName = pData.contactsName;
    		var contactNumber = pData.contactNumber;
    		var createTime = pData.createTime;
    		var region = pData.ipAddress;
     	   		      		       		
    		var topURL = $("#topurl").val();
    		
    		var itemHTML = 	'<div class="row project-item-view">' +     		                  		                 
    		                 '<div class="col-lg-12 col-md-12">';
    		                              
    		itemHTML += '<div class="col-lg-1 col-md-1"><span class="badge ';
    		
    	  var displayStatus = "未知";
    	  
		    if(statusCode == 0){
    		  	itemHTML += ' badge-danger" ';
    		  	displayStatus = "未处理";
    		  }else if(statusCode == 1){
    		  	itemHTML += ' badge-info" ';    		  	
    		  	displayStatus = "洽谈中";
    		  }else if(statusCode == 2){
    		  	itemHTML += ' badge-primary" ';
    		  	displayStatus = "已发布";
    		  }
    		 else if(statusCode == 3){
    		  	itemHTML += ' badge-danger" ';
    		  	displayStatus = "已关闭";
    		  } 
    		  
    		  itemHTML += '>' + displayStatus + "</span></div>";
    		        
	    		itemHTML += '<div class="col-lg-9 col-md-9">'  +
	    		            '<div class="col-lg-2 col-md-2"><p>类型：' +  type + '</p></div>' +
	    		            '<div class="col-lg-2 col-md-2"><p>ID：' +  id + '</p></div>'  + 
	    		            '<div class="col-lg-3 col-md-3"><p>姓名：' + contactsName + "</p></div>" +    		                   		                    		           
	    		            '<div class="col-lg-3 col-md-3"><p>手机号：' + contactNumber + "</p></div>";
	    	

	    	  
	    	  if(priceRange == null || priceRange.trim() == ""){
	    	  	priceRange = "无";
	    	  }
	    	  
	    	  itemHTML += '<div class="col-lg-2 col-md-2"><p>价位：' + priceRange + '</p></div>';				
                       
          if(period == null && period.trim() == ""){
         	  period = "无";
          }
         
          itemHTML += '<div class="col-lg-2 col-md-2"><p>周期：' + period + '</p></div>' ;
         
         	var createTimeObj = new Date();
				  createTimeObj.setTime(createTime);
	    		var createTimeStr = createTimeObj.format("yyyy-MM-dd hh:mm");
	    		
	    	  var contentLen = calculateStringLen(content);
	    	  var maxContentLen = 52;
	    	  var contentAbbrStr = "";
	    	  
	    	  if(content.indexOf("<br/>") >= 0){
	    	  	//alert(content.indexOf("<br/>"));
	    	  	
	    	  	var tempContent = content.substr(0,content.indexOf("<br/>"));
	    	  	var tempContentLen = calculateStringLen(tempContent);
	    	  	
	    	  	if(tempContentLen > maxContentLen){
	    	  		contentAbbrStr = tempContent.substr(0,maxContentLen - 6);
	    	  	}else{
	    	  		contentAbbrStr = tempContent;
	    	  	}
	    	  	
	    	  	contentAbbrStr += '...<a id="' + id + '" class="btn btn-xs btn-primary" data-toggle="modal" onclick="showReservationDetail(this);">更多</a>';
	    	  }else{
		    	  if(contentLen > maxContentLen){
		    	  	contentAbbrStr = content.substr(0,maxContentLen - 6);
		    	  	contentAbbrStr += '...<a id="' + id + '" class="btn btn-xs btn-primary" data-toggle="modal" onclick="showReservationDetail(this);">更多</a>';
		    	  }else{
		    	  	contentAbbrStr = content;
		    	  }
	    	  }
	    	  	    	  
	    	  itemHTML += '<div class="col-lg-4 col-md-4"><p>创建时间：' + createTimeStr + '</p></div>' + 
        						  '<div class="col-lg-6 col-md-6"><p>地点：' + region + '</div>' + 
        						  '<div class="col-lg-12 col-md-12"><p>需求描述：' + contentAbbrStr + '</p>' +
        						  '<textarea id="u_' + id + '" style="display:none;">' + content + '</textarea>' +
        						  '</div>' +
        						  '</div>';
        
         itemHTML += '<div class="col-lg-2 col-md-2" style="margin-top:30px;">' +
                     '<div class="col-lg-5 col-md-5">';
         
         var operBtnText = "";
         
         if(statusCode == 0){
         	 itemHTML += '<a class="btn btn-primary" id="' + id + '" onclick="javascript:doDiscussReservation(this);">我要洽谈</a>';
         }else if(statusCode == 1){
         	 itemHTML += '<a class="btn btn-primary" id="' + id + '" onclick="javascript:doPublishReservation(this);">我要发布</a>';
         }
         
         itemHTML += '</div><div class="col-lg-offset-1 col-lg-5 col-md-5">';
         						 
         if(statusCode == 0 || statusCode == 1){
         	 itemHTML += '<a id="' + id + '" data-toggle="modal" class="btn btn-primary" onclick="javascript:launchCloseReservation(this);">我要关闭</a>';
         }else if(statusCode == 2){
         	var projectID = pData.projectId; 
         	 itemHTML += '<a class="btn btn-primary" href="/home/p/' + projectID + '" target="_blank">查看项目</a>';
         }else if(statusCode == 3){
         	 itemHTML += '<a id="' + id + '" data-toggle="modal" class="btn btn-primary" onclick="javascript:showClosedReason(this);">查看原因</a>' + 
         	            '<div style="display:none;"><textarea id="cr_' + id + '">' + pData.closeReason + '</textarea>';
         }
                     
   		   itemHTML += '</div></div></div></div>';  
   		   //alert(itemHTML);
   		  
			   return itemHTML;
    	}
     
     var selectedTypeID;
     var selectedStatusID;
     var selectedTypeVal;
     var selectedStatusVal;
     
     function initSearchView(){
     	  selectedTypeID = "p_all";
     	  selectedStatusID = "s_all";
     	  selectedTypeVal = "";
     	  selectedStatusVal = "0";
     	  
     	  refreshSearchInternalItemBG(selectedTypeID,"btn-link","btn-primary");
     	  refreshSearchInternalItemBG(selectedStatusID,"btn-link","btn-primary");
     }
     
     function refreshSearchInternalItemBG(eID,oldCSSClass,newCSSClass){
     	 var el = $("#" + eID);
     	 el.removeClass(oldCSSClass);
     	 el.addClass(newCSSClass);
     	 
     	 if(newCSSClass == "btn-primary"){
     	 	 el.addClass("search-item-blank");
     	 }else if(newCSSClass == "btn-link"){
     	 	 el.removeClass("search-item-blank");
     	 }
     	 
     }
     
     function refreshSearchItemBG(selectedID){
     	   
     	   if(selectedID != null && selectedID.trim() != ""){
     	   	
     	   	 if(selectedID.indexOf("p_") >= 0){
     	   	 	
     	   	 	 if(!isOldTypeItemSelected(selectedID)){
     	   	 	 	 refreshSearchInternalItemBG(selectedID,"btn-link","btn-primary");
     	   	 	 	 refreshSearchInternalItemBG(selectedTypeID,"btn-primary","btn-link");
     	   	 	 	 selectedTypeID = selectedID;
     	   	 	 	 selectedTypeVal = $("#" + selectedTypeID).val();
     	   	 	 }
     	   	 }else if(selectedID.indexOf("s_") >= 0){
     	   	 	 if(!isOldStatusItemSelected(selectedID)){
     	   	 	 	 refreshSearchInternalItemBG(selectedID,"btn-link","btn-primary");
     	   	 	 	 refreshSearchInternalItemBG(selectedStatusID,"btn-primary","btn-link");
     	   	 	 	 selectedStatusID = selectedID;
     	   	 	 	 selectedStatusVal = $("#" + selectedStatusID).val();
     	   	 	 }
     	   	 }
     	   }
     }
     
     function isOldTypeItemSelected(id){
     	if(id == selectedTypeID){
     		return true;
     	}
     	
     	return false;
     }
     
     function isOldStatusItemSelected(id){
     	if(id == selectedStatusID){
     		return true;
     	}
     	
     	return false;
     }
     
    function doSearch(el){
     	 var selectedID = $(el).attr("id");
     	 refreshSearchItemBG(selectedID);
     	 
     	 destoryPaginationView();
     	 
     	 //start to search here
     	 internalSearch(gURL,selectedStatusVal,selectedTypeVal,1,gPageSize);
     }
     
     function internalSearch(requestURL,status,type,currentPage,pageSize){
     	 var paraData = "status=" + status + "&currentPage=" + currentPage;
     	
    	 if(pageSize != null){
     		 paraData += "&pageSize=" + pageSize;
     	 }     
     
     	 if(type != null && type.trim() != ""){
     		 paraData += "&type=" + type.trim();
     	 }

     	 $.ajax({
    			type:"get",
    			url:requestURL,
    			data:paraData,
    			async:false,
    			success:function(data){
					//如果返回的数据非JSON就刷新当前页面
					try{
						//show search result number
						$("#projectNum").html(data.totalRow);
						generateProjectView(data.data);
						
						//生成分页
						if(paginationView == null){
								initPaginationView(data,status,type);
						}
					}catch(e){
						//location.reload(true);
					}
    			}
    		});
     }
     
     function showReservationDetail(el){
     	var uid = el.id;
     	
     	if(uid == null || uid.trim() == ""){
     		return;
     	}
     	
     	var content = $("#u_" + uid).val();
     	
     	if(content == null || content.trim() == ""){
     		return;
     	}
     
      content = content.replace(/\n/g,"<br>");
      
      $("#pDetailMessage").html(content); 	
      
      el.href = "#pDetailDialog";
     }
     
     function launchCloseReservation(el){
     	$("#selectedReservation").val(el.id);
     	
     	el.href = "#pReservationClosedDialog";
     }
     
     function doCloseReservation(){
     	var closedReason = $("#rClosedDesc").val();
     	var id = $("#selectedReservation").val();
     	
     	var requestURL = "/api/2/reserve/modify";
     	
     	var paraData = "id=" + id + "&status=3&closeReason=" + closedReason;
     	
     	$.ajax({
     		type:"POST",
     		url:requestURL,
     		data:paraData,
     		async:false,
     		success:function(data){
     			$("#pReservationClosedDialog").modal("hide");
     			showMessageBox("预约/需求关闭成功！","预约/需求关闭","/admin/reserve_review");
     		},
     		error:function(data){
     			showMessageBox(data.errorMsg,"预约/需求关闭");
     		}
     	});
     }
     
     function doDiscussReservation(el){
	     	var id = el.id;
	     	var requestURL = "/api/2/reserve/modify";
	     	
				$.confirm({
						title: '预约/需求洽谈',
						content: "您确定此项目进入洽谈中？",
						confirmButton: '确定',
						cancelButton: '取消',
						confirmButtonClass: 'btn-primary',
						cancelButtonClass: 'btn-danger',
						backgroundDismiss:false,
						animation: 'scale',
						confirm: function (){
						  var paraData = "id=" + id + "&status=1";
						  
				     	$.ajax({
				     		type:"POST",
				     		url:requestURL,
				     		data:paraData,
				     		async:false,
				     		success:function(data){
				     			showMessageBox("预约/需求洽谈操作成功！","预约/需求洽谈","/admin/reserve_review");
				     		},
				     		error:function(data){
				     			showMessageBox(data.errorMsg,"预约/需求洽谈");
				     		}
			     	});
					}
				});	     		
     }
     
     
      function doPublishReservation(el){
       	var id = el.id;
				location.href = "/admin/reserve_publish?id=" + id;
     	}
      
      function showClosedReason(el){
      	var reason = $("#cr_" + el.id).val();
      	
      	$("#pClosedReasonMessage").html(reason);
      	el.href = "#pClosedReasonShowDialog";
      }
    </script>
  </head>
  <body>
    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->
    
    <div class="body-offset"></div>
    
    <div class="container">
    	<div class="breadcrumb-container">
			  <ol class="breadcrumb">
				  <li><a href="/">首页</a></li>
				  <li><a href="#">预约审核</a></li>
			  </ol>
		  </div>
    </div>
    
    <div class="container">
    	<div class="row project-search-view">
			  <div class="search-panel">
	    	  <label class="search-label">项目状态:</label>
	    		<button class="btn btn-link search-item" id="s_all" value="-1"  onclick="doSearch(this);"/>全部</button>
	    		<button class="btn btn-link search-item" id="s_request" value="0" onclick="doSearch(this);"/>未处理</button>
	    		<button class="btn btn-link search-item" id="s_develop" value="1" onclick="doSearch(this);"/>洽谈中</button>
	    		<button class="btn btn-link search-item" id="s_conplete" value="2" onclick="doSearch(this);"/>已发布</button>
	    		<button class="btn btn-link search-item" id="s_review" value="3" onclick="doSearch(this);"/>已关闭</button>
				</div>
    </div>
  </div>
    
    <div class="container">
    	<div class = "row search-result-num-panel">相关需求<p id="projectNum" class="result-num"></p>个</div>
    </div>
    
    <div id="projectView" class="container"></div>
    
	 	<div class="container">
		 	 <div id="paginationView" class="pagination-view-container"></div>
	 </div>
	 
	 
	 <!--detail dialog-->
   <div class="modal" id="pDetailDialog">
	  <div class="modal-dialog modal-custom-class">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
	        <h4 class="modal-title">需求详情</h4>
	      </div>
	      <div class="modal-body">
			     <div id="pDetailMessage" class="show-reason-box"></div>
	      </div>
	    </div>
	  </div>
	</div>
   <!--end of detail dialog-->   
   
   <!--detail dialog-->
   <div class="modal" id="pClosedReasonShowDialog">
	  <div class="modal-dialog modal-custom-class">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
	        <h4 class="modal-title">关闭原因</h4>
	      </div>
	      <div class="modal-body">
			     <div id="pClosedReasonMessage" class="show-reason-box"></div>
	      </div>
	    </div>
	  </div>
	</div>
   <!--end of detail dialog-->   
   
   
   <!--Reservation closed dialog-->
	 <div class="modal" id="pReservationClosedDialog">
	  <div class="modal-dialog modal-custom-class">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
	        <h4 class="modal-title">关闭预约/需求</h4>
	      </div>
	      <div class="modal-body">
	        <form class="form-horizontal form-in-modal" id="pReservationClosedForm" action="javascript:doCloseReservation();" role="form"  method="post">
	        	<input type="hidden" id="selectedReservation"></input>
			     <div class="form-group">       	
        	    <div class="form-group">
		        	 <div class="col-lg-12 col-md-12">
			           <label class="control-label form-label"><i class="form-required">*</i>关闭原因（至少5个字符，150字以内）：</label>
			         </div>
			         <div class="col-lg-9 col-md-9">
		    			 <textarea id="rClosedDesc" name="rClosedDesc" class="form-control" rows="5" type="text" minlength="5" maxlength="150" required="true"></textarea>
		    		 </div>
			     </div>
	        	<div>
	        	<button type="button" class="btn btn-lg btn-default form-btn " data-dismiss="modal">取消</button>
	        	<button type="submit" class="btn btn-lg btn-primary form-btn">提交</button>
	        	</div>
	        </form>
	       </div>
	    </div>
	  </div>
	  </div>
	 </div>
   <!--end of Reservation closed dialog-->
   
  <!--start of footer-->   
  <#include "../footer.ftl">
  <!--end of footer-->
  <!---start of help docker-->
  <div id="top"></div>
  <!--end of help docker-->
  
   <div>
   	<input type="hidden" id="topurl" value="${ctx}/"/>
   	<input type="hidden" id="reload" value="1"/>
   	<input type="hidden" id="footerfixed" value="0"/>
   </div>
  </body>
</html>
