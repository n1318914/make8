<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>项目详情 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    ${tools_includes}

    <script type="text/javascript">
      
    	$(document).ready(function(){
    		//initialize the project progress
    		var pStatus = $("#projectStatus").val();
    		var pInternalStatus = $("#projectInternalStatus").val();
    		var pInternalStatusCode = $("#projectInternalStatusCode").val();
        var pStepContent = ["项目发布","项目竞标","资金托管","开发验收","完成付款"];
        
    		var pDate = ['${project.createTime?string("yyyy-MM-dd HH:mm")}'];
        var pChoosedDate = $("#joinChoosedDate").val();
        var pAmountInTrustDate = $("#amountInTrustDate").val();
        var pAcceptDate = $("#acceptDate").val();
        var pFinishDate = $("#finishDate").val();
        
        if(pChoosedDate != null && pChoosedDate.trim() != ""){
        	pDate.push(pChoosedDate);
        }
        
    		if(pAmountInTrustDate != null && pAmountInTrustDate.trim() != ""){
    			pDate.push(pAmountInTrustDate);
    		}
    		
    		if(pAcceptDate != null && pAcceptDate.trim() != ""){
    			pDate.push(pAcceptDate);
    		}
    		
    		if(pFinishDate != null && pFinishDate.trim() != ""){
    			pDate.push(pFinishDate);
    		}
    		
    		var topURL = $("#topurl").val();
    		
    		drawProjectProgress("pProgressDiv",pInternalStatusCode,pStepContent,pDate,topURL);
    		
    		$("#jingbiaoForm").validate({
    			  errorElement : "span",
    			  ignore:[]
    		});
    		
    		 $("#acceptanceCheckForm").validate({
    			  errorElement : "span",
    			  ignore:[],
		        errorPlacement: function(error, element) {
		       	 if(element.is("#acceptanceCheck_ok") || element.is("#acceptanceCheck_unok")){
	           	  error.appendTo($("#acceptanceChekMsg"));
	           	}else{
		       	  	 error.insertAfter(element);
		       	  }
		       	}
    		});
    		
    		$("#pContentAppendForm").validate({
    			  errorElement : "span"
    		});
    		
    		$("#noteForm").validate({
    			errorElement:"span"
    		})
    		
    		
    		if(pStatus == "竞标中"){
    		 	showCountdown();
    		}
    		
    		//make the dialog draggable
    		$('.modal-dialog').draggable({
            handle: ".modal-header"
        });
        
        
        checkProjectExpired();
        
        //show project rating start
        if(pInternalStatusCode == 11){
        	 initProjectRatingStar();
        }
        
        if(pInternalStatusCode == 9){
        	showRatingScores();
        	
        	$("#pEvaluateForm").validate({
					  errorElement : "span"
				  });
        }
        
        
        //init project note
        var isSelf = $("#h_isSelf").val();
        var isAdmin = $("#h_isAdmin").val();
        
        if(isSelf == 1 || isAdmin == 1){
        	 initProjectNote();
        }
        
        if(pInternalStatusCode == 1){
        	var jingbiaoDateStr = $("#jingbiaoDateShow").html();
        	generateDatePicker(".jingbiao_date",1,jingbiaoDateStr);
        }
    	});
    	
	  function isServicer(){
			var isService = $("#h_isService").val();
			
			if(isService == 1){
				return true;
			}else{
				return false;
			}
	  }
		
		function checkInfoComplete(obj){
			// 个人信息是否完善
			$.ajax({
	          "type" : "GET",
	          "async" : false,
	          "url" : "/api/1/u/isComplete",
	          "success" : function(data){
               if(data.resultCode == 1){
				          var contentStr = '您的个人信息尚未完善，需要完善后才能参与竞标';
				          
									$.confirm({
											title: '完善信息',
											content: contentStr,
											confirmButton: '立即完善',
											cancelButton: '稍后再说',
											confirmButtonClass: 'btn-primary',
											cancelButtonClass: 'btn-danger',
											backgroundDismiss:false,
											animation: 'scale',
											confirm: function () {
											  location.href='${ctx}/home/userinfo';
											},
											cancel: function () {
											  location.href='${ctx}/home/userinfo';
											}
									});
						}else{
							initJMPeriod();
							obj.href='#jingbiaoDialog';
						}
			   }
       });
		}
		  
    	function doJBModify(){
    		 var requestURL = "/api/1/p/join/modify";
    		 doJBInternal(requestURL,2);
    	}
    	
    	function doJBCreate(){
    		 var requestURL = "/api/1/p/join";
    		 doJBInternal(requestURL,1);
    	}
    	
    	function doJBInternal(requestURL,type){
    	  var projectID = $("#projectID").val();
    	  var jPrice = $("#jPrice").val();
    	  var jPeriod = $("#jPeriod").val();
    	  var jDesc = $("#jDesc").val().trim();
    	  
    	  var jMPeriod = $("#jMPeriod option:selected").val();

    	  jDesc = encodeURIComponent(jDesc);
    	  jDesc = encodeURIComponent(jDesc);
    	  
    	  var topurl = $("#topurl").val();
    	  
    	  var paraData = "projectId=" + projectID + "&price=" + jPrice + "&period=" + jPeriod + "&plan=" + jDesc + 
    	                 "&matainancePeriod=" + jMPeriod;
    	             
    	  var msgTitle;
    	  
    	  if(type == 1){
    	  	msgTitle = "我要竞标";
    	  }else{
    	  	msgTitle = "修改竞标";
    	  }
    	
    	  
    	  $.ajax({
    	  	type:"post",
    	  	url:requestURL,
    	  	data:paraData,
    	  	async:false,
    	  	success:function(data){
    	  		if(data.resultCode){
    	  			//alert(data.errorMsg);
    	  			showMessageBox(data.errorMsg,msgTitle);
    	  		}else{
    	  		  var nextLocation = topurl + "home/p/" + projectID;
    	  		  $("#jingbiaoDialog").modal('hide');
    	  			showMessageBox(data.msg,msgTitle,nextLocation);
    	  		}
    	  	}
    	  });
    	}
    	
    
    	function doChoose(userName,useID,price,period){
    		var contentStr = '确认选择<strong>' + userName + '</strong>?<br>竞标价格：<strong>' + price  +
    		                 '</strong><br>竞标周期：<strong>' + period + '</strong><br><font color=red><strong>温馨提示：选标后不能撤销。</strong></font>';
    		                 
    		$.confirm({
                    title: '选标',
                    content: contentStr,
                    confirmButton: '确认',
                    cancelButton: '取消',
                    confirmButtonClass: 'btn-primary',
                    cancelButtonClass: 'btn-danger',
                    //icon: 'fa fa-question-circle',
                    animation: 'scale',
                    confirm: function () {
                      doChooseSubmit(useID);
                    }
                 });
    	}
    	
    	function doChooseSubmit(userID){
    		var requestURL = "/api/1/p/choice";
    		var projectID = $("#projectID").val();
    		var paraData = "projectId=" + projectID + "&userId=" + userID;
    		var topurl = $("#topurl").val();
    		
    		$.ajax({
    			type:"post",
    	  	url:requestURL,
    	  	data:paraData,
    	  	async:false,
    	  	success:function(data){
    	  		if(data.resultCode){
    	  			//alert(data.errorMsg);
    	  			showMessageBox(data.errorMsg,"项目选标");
    	  		}else{
    	  			var nextLocation = topurl + "home/p/" + projectID;
    	  			showMessageBox("您已选标成功，请联系客服进行资金托管！","项目选标",nextLocation);
    	  		}
    	  	}
    		});
    	}
    	
    	function doCancelChoice(userName){
    		$.confirm({
                    title: '撤销选标',
                    content: '确认撤销<strong>' + userName + '</strong>的竞标?',
                    confirmButton: '确认',
                    cancelButton: '取消',
                    confirmButtonClass: 'btn-primary',
                    cancelButtonClass: 'btn-danger',
                    //icon: 'fa fa-question-circle',
                    animation: 'scale',
                    confirm: function () {
                      doCancleChoiceSubmit();
                    }
                 });
    	}
    	
    	
    	function doCancleChoiceSubmit(){
    		var requestURL = "/api/1/p/select/cancel";
    		var projectID = $("#projectID").val();
    		var paraData = "projectId=" + projectID;
    		var topurl = $("#topurl").val();
    		
    		$.ajax({
    			type:"post",
    	  	url:requestURL,
    	  	data:paraData,
    	  	async:false,
    	  	success:function(data){
    	  		if(data.resultCode){
    	  			//alert(data.errorMsg);
    	  			showMessageBox(data.errorMsg,"撤销选标");
    	  		}else{
    	  			var nextLocation = topurl + "home/p/" + projectID;
    	  			showMessageBox("撤销竞标成功","撤销选标",nextLocation);
    	  		}
    	  	}
    		});
    	}
     
     function projectExpireOps(){
     		//var message="竞标已过期";
				$("#countdown").hide();
				$("#countdown_label").hide();
				$("#pDisplayStatus").html("已完成");
				
				disableJoinBtn();
				$("#pExpireTag").show();
     }
     
     function disableJoinBtn(){
     	  $("#pJoinBtn").attr("disabled","true");
				$("#pJoinBtn").attr("onclick","javascript:void(0);");
     }
     
      function showCountdown(){
      	  $("#countdown_label").show();
      	  $("#countdown").html("");
      	  $("#countdownnote").html("");
      	  
      	  var jingbiaoDateStr = $("#jingbiaoDateShow").html();
      	  
      	  jingbiaoDateStr = jingbiaoDateStr.replace(/-/g,"/");
      	  jingbiaoDateStr = jingbiaoDateStr + " 23:59:59";
      	  
					var jingbiaoDate = new Date(jingbiaoDateStr);
				  
				  //retrieve the current from the server not local date time.
				  var mURL = "/api/common/time";
				  
				  $.ajax({
				  	type:"get",
				  	url:mURL,
				  	async:true,
				  	success:function(data){
				  		 var dataVal = data.replace(/-/g,"/");
				  		
				  		 var currentDate = new Date(dataVal);
				  		 //var ts = jinbiaoDate.valueOf() - currentDate.valueOf();
				  		 var ts = Math.floor(jingbiaoDate.getTime() - currentDate.getTime());
               
								$('#countdown').countdown({
									timestamp	: ts,
									callback	: function(days, hours, minutes, seconds){
										if(days == 0 && hours == 0 && minutes == 0 && seconds == 0){
												projectExpireOps();
										}else{
											var message = "";
											if(days != 0){
												message += days + " 天 ";
											}
											
											message += hours + " 小时  ";
											message += minutes + " 分 ";
											message += seconds + " 秒<br/>";
											
											$("#countdownnote").html(message);
										}
									}
								});
				  	}
				  });
		
				 
	
      }
      
      function doAcceptanceCheck(){
      	var topurl = $("#topurl").val();
      	
      	$.confirm({
                    title: '启动验收',
                    content: '确认启动项目验收给<strong>${project.userName!""}</strong>?',
                    confirmButton: '确认',
                    cancelButton: '取消',
                    confirmButtonClass: 'btn-primary',
                    cancelButtonClass: 'btn-danger',
                    //icon: 'fa fa-question-circle',
                    animation: 'scale',
                    confirm: function (){
                    	var projectID = $("#projectID").val();
                    	var paraData = "id=" + projectID;
                    	var mURL = "/api/1/p/done";
                    	
                      $.ajax({
                      	type:"POST",
                      	data:paraData,
                      	url:mURL,
                      	async:false,
                      	success:function(){
                      			/*if(data.resultCode){
									    	  			alert(data.errorMsg);
									    	  	}else{*/
									    	  			location.href = topurl + "home/p/" + projectID;
									    	  	//}
                      	}
                      });
                    }
                 });
      }
            
    function checkProjectExpired(){
    	var statusCode = $("#projectInternalStatusCode").val();
    	
    	if(statusCode == 8){
    		projectExpireOps();
    	}
    }
    
    function acceptanceCheckPass(){
       $.confirm({
                    title: '验收通过',
                    content: '确认通过此项目的验收？如果确定通过，交易金额的<strong><font color="red">90%</font></strong>将由平台支付给服务商，剩下<font color="red"><strong>10%</strong></font>将在一个月后支付给服务商。',
                    confirmButton: '确认',
                    cancelButton: '取消',
                    confirmButtonClass: 'btn-primary',
                    cancelButtonClass: 'btn-danger',
                    animation: 'scale',
                    confirm: function (){
                    	 var topurl = $("#topurl").val();
								       var mURL = "/api/1/p/accept";
								       var projectID = $("#projectID").val();
								       var paraData = "id=" + projectID + "&operate=0";
								       
	                      $.ajax({
	                      	type:"POST",
	                      	data:paraData,
	                      	url:mURL,
	                      	async:false,
	                      	success:function(){
	                      			/*if(data.resultCode){
										    	  			alert(data.errorMsg);
										    	  	}else{*/
										    	  			location.href = topurl + "home/p/" + projectID;
										    	  	//}
	                      	}
	                      });
                    }
                 });
    }
    
    function doAcceptanceReject(){
    	  $.confirm({
                    title: '验收驳回',
                    content: '确认驳回此项目的验收?',
                    confirmButton: '确认',
                    cancelButton: '取消',
                    confirmButtonClass: 'btn-primary',
                    cancelButtonClass: 'btn-danger',
                    animation: 'scale',
                    confirm: function (){
                    	 var topurl = $("#topurl").val();
								       var mURL = "/api/1/p/accept";
								       var projectID = $("#projectID").val();
								       var reason = $("#acceptanceRejectReason").val().trim();
						           
								       var paraData = "id=" + projectID + "&operate=1&result=" + reason;
								       
	                      $.ajax({
	                      	type:"POST",
	                      	data:paraData,
	                      	url:mURL,
	                      	async:false,
	                      	success:function(){
	                      			/*if(data.resultCode){
										    	  			alert(data.errorMsg);
										    	  	}else{*/
										    	  			location.href = topurl + "home/p/" + projectID;
										    	  	//}
	                      	}
	                      });
                    }
                 });
    }
    
    function initProjectRatingStar(){
    	var qualityScore = $("#qualityScoreVal").val();
    	var speedScore = $("#speedScoreVal").val();
    	var attitudeScore = $("#attitudeScoreVal").val();
    	
    	var topURL = $("#topurl").val();
    		
			showRatingScore("p_attitudeScore",topURL,"default",attitudeScore,true);
			showRatingScore("p_qualityScore",topURL,"default",qualityScore,true);
			showRatingScore("p_speedScore",topURL,"default",speedScore,true);
    }
    
    
     function initRequestPriceRange(){
	 			var options= ["100-1000元","1000-5000元","5000-10000元","10000-30000元","3万-5万元","5万-10万","10万以上","待商议"];
	 			var priceRange = $("#projectPriceRange").val();
	 			
        initRequestSelectBox(options,priceRange,"priceRangeReselect");
	   }
	 
		 function initRequestSelectBox(options,selectedVal,eid){
			 	var oHTML = "";
			 	
			 	$(eid).html("");
			 	
			 	for(var i = 1; i <= options.length; i++){
			 		oHTML += '<option value="' + i + '"';
			 		
			 		if(selectedVal == options[i-1]){
			 			oHTML += " selected";
			 		}
			 		
			 		oHTML += ">" + options[i-1] + "</option>";
			 	}
			 	
			 	//alert(oHTML);
			 	$("#" + eid).html(oHTML);
		 }
		 
		 function launchModifyPrice(){
		 	 initRequestPriceRange();
		 	 
		 	 $("#launchModifyPriceBtn").hide();
		   $("#priceRangeReselect").show();
		   $("#modifyPriceBtn").show();
		   $("#modifyPriceCancelBtn").show();
		 }
		 
		 
		 function doModifyPrice(){
		   var orgPriceRange = $("#projectPriceRange").val();
		   var selectedPriceRange = $("#priceRangeReselect").find("option:selected").text();
		   
		   if(orgPriceRange == selectedPriceRange){
		   		showMessageBox('新选择的价位和之前相同，请重新选择！','价位修改');
		   		return;
		   }
		   
		  
		   var mURL = "/api/1/p/adjust/price";
		   var pid = $("#projectID").val();
		   
		   paraData = "id=" + pid + "&priceRange=" + selectedPriceRange;
		   
		   $.ajax({
	       	type:"POST",
      	  data:paraData,
      	  url:mURL,
      	  async:false,
      	  success:function(){
              $("#projectPriceRange").val(selectedPriceRange);
              $("#priceRangeShow").html(selectedPriceRange);
              
              $("#priceRangeReselect").hide();
		 	        $("#modifyPriceCancelBtn").hide();
		 	        $("#modifyPriceBtn").hide();
		 	        $("#launchModifyPriceBtn").show();
      	  }
		   });
		   
		 }
		 
		 function doModifyPriceCancel(){
		 	 $("#priceRangeReselect").hide();
		 	 $("#modifyPriceCancelBtn").hide();
		 	 $("#modifyPriceBtn").hide();
		 	 $("#launchModifyPriceBtn").show();
		 }
		 
		 function doContentAppend(){
		 	  var mURL = "/api/1/p/supplement";
		 	  
		    var pid = $("#projectID").val();
		    
		    var content = $("#newRequestDesc").val().trim();
		    content = encodeURIComponent(content);
		    content = encodeURIComponent(content);
		    
		    var paraData = "id=" + pid + "&content=" + content;
		    
		    $.ajax({
		    	type:"POST",
      	  data:paraData,
      	  url:mURL,
      	  async:false,
      	  success:function(data){
      	  	if(data.resultCode == 0){
      	  		location.href = data.msg;
      	  	}
      	  }
		    });
		 }
		 
		 var jBOps = 0;
		 	  
		 function launchModifyJoinInfo(el){
	 		initJMPeriod($("#h_JMPeriod").val());
		 	     		 	
		 	var joinPlan = $("#jDesc").val().trim();
		 	 
		 	 joinPlan = joinPlan.replace(/<br\/>/g,"\n");
		 	 //alert(joinPlan);
		 	 $("#jDesc").val(joinPlan);
		 	 
		 	 el.href="#jingbiaoDialog";
		 }
		 
		 function initJMPeriod(jMPeriodVal){		 	 
		 	 var optionHTML = "";
		 	 //$("#jMPeriod").html("");
		 	 
		 	 if(jMPeriodVal == null){
		 	 	 optionHTML += '<option value="">请选择</option>';
		 	 }
		 	 
		 	 if(jMPeriodVal == 0){
		 	 	  optionHTML += '<option value="0" selected>不提供</option>';
		 	 }else{
		 	  	optionHTML += '<option value="0">不提供</option>';
		 	 }
		 	 
		 	 
		 	 for(var i = 1; i <= 12; i++){
		 	 	 if(i == jMPeriodVal){
		 	 	 	 optionHTML += '<option value="' + i + '" selected>' + i + '个月</option>';
		 	 	 }else{
		 	 	 	 optionHTML += '<option value="' + i + '">' + i + '个月</option>';
		 	 	 }
		 	 }
		 	 
		 	 $("#jMPeriod").append(optionHTML);
		 	 
		 }
		 
		 function doKick(pId,uId,userName){
		 	 $.confirm({
                title: '竞标淘汰',
                content: '确认淘汰竞标者<strong>' + userName + '</strong>?',
                confirmButton: '确认',
                cancelButton: '取消',
                confirmButtonClass: 'btn-primary',
                cancelButtonClass: 'btn-danger',
                animation: 'scale',
                confirm: function (){
                	 var topurl = $("#topurl").val();
						       var mURL = "/api/1/p/join/kick";
				           
						       var paraData = "pid=" + pId + "&userId=" + uId;
						       
	                  $.ajax({
	                  	type:"POST",
	                  	data:paraData,
	                  	url:mURL,
	                  	async:false,
	                  	success:function(data){
							    	  		location.href = topurl + "home/p/" + pId;
	                  	}
	                  });
	                }
         });
		 }
		 
		 function doCancelKick(pId,uId,userName){
		 	 	 $.confirm({
                title: '撤销竞标淘汰',
                content: '确认撤销被淘汰的竞标者<strong>' + userName + '</strong>?',
                confirmButton: '确认',
                cancelButton: '取消',
                confirmButtonClass: 'btn-primary',
                cancelButtonClass: 'btn-danger',
                animation: 'scale',
                confirm: function (){
                	 var topurl = $("#topurl").val();
							       var mURL = "/api/1/p/join/cancelKick";
					           
							       var paraData = "pid=" + pId + "&userId=" + uId;
							       
                      $.ajax({
                      	type:"POST",
                      	data:paraData,
                      	url:mURL,
                      	async:false,
                      	success:function(data){
							    	  		  location.href = topurl + "home/p/" + pId;
                      	}
                      });
                }
         });
		 }
		 
		 var selectedJoinUserId;
		 var selectedProjectId;
		 
		 function launchRemark(el,pId,uId){
		 	selectedProjectId = pId;
		 	selectedJoinUserId = uId;
		 	
		 	var remark = $("#p_" + uId).html();
		  
		 	if(remark != null && remark.trim() != ""){
		 		 	remark = remark.replace(/<br\/>|<br>/g,"\n");
		 		 	$("#jNote").val(remark);
		 	}else{
		 		  $("#jNote").val("");
		 	}

		 	el.href="#noteDialog";
		 }
		 
		 function doRemark(){
		 	  var mURL = "/api/1/p/remark";
		 	  var remark = $("#jNote").val();
		 	  
		 	  var paraData = "pid=" + selectedProjectId + "&userId=" + selectedJoinUserId + "&remark=" + remark;
		 	  var topurl = $("#topurl").val();
		 	  
	 	    $.ajax({
          	type:"POST",
          	data:paraData,
          	url:mURL,
          	async:false,
          	success:function(data){
				    	  //location.href = topurl + "home/p/" + selectedProjectId;
				    	  generateProjectNoteInternal("div_" + selectedJoinUserId,selectedJoinUserId,remark,0);
				    	  $('#noteDialog').modal('hide');
          	}
          });
		 }
		 
		 function initProjectNote(){
		 	var isAdmin = $("#h_isAdmin").val();
		 	var id,userId,remark;
		 	
		 	$("textarea").each(function(){
        id = this.id;
        
        if(id.indexOf("t_") >= 0){
        	userId = id.replace(/t_/g,"");
        	remark = $("#"+id).val();
        	
        	generateProjectNoteInternal("div_" + userId,userId,remark,isAdmin);
        }
     });
		 }
		 
		 function generateProjectNoteInternal(divId,userId,remark,isAdmin){
     	
     	if(remark == null || remark.trim() == ""){
     		return;
     	}
     	
     	 var noteHTML = "";
     	 
     	 if(isAdmin == 1){
     	 	noteHTML += '<p class="i-label">雇主备注：<br></p>';
     	 }else{
     	 	noteHTML += '<p class="i-label">我的备注：<br></p>';
     	 }
				
		    var remarkHTML = remark.replace(/\n/g,"<br/>");
		   
			  noteHTML += '<p id="p_' + userId + '">' + remarkHTML + '</p>';
			  
			  $("#"+divId).html(noteHTML);
     }
		 
		 
		 function doModifyRequest(pid){
     	var murl = "/home/p/modify?id=" + pid;
     	location.href = murl;
     	//window.open(murl);
     }
		 
		 	function showRatingScores(){
    		var topURL = $("#topurl").val();
    		showRatingScore("attitudeScore",topURL);
    		showRatingScore("qualityScore",topURL);
    		showRatingScore("speedScore",topURL);
    	}
		 
     
     function doPEvaluate(){
     	var topURL = $("#topurl").val();
     	var pid = $("#projectID").val();
     	var speedScore = $("#speedScore").raty('getScore');
     	var qualityScore = $("#qualityScore").raty('getScore');
     	var attitudeScore = $("#attitudeScore").raty('getScore');
     	var desc = $("#evaluateDesc").val().trim();
     	
     	var mURL = "/api/1/p/eval";
     	var paraData = "projectId=" + pid + "&qualityScore=" + qualityScore + "&speedScore=" + 
     	               speedScore + "&attitudeScore=" + attitudeScore + "&description=" + desc;
     	               
     	$.ajax({
     		type:"post",
     		url:mURL,
     		data:paraData,
     		async:false,
     		success:function(data){
     			showMessageBox("谢谢您的评价!","项目评价",topURL + "home/p/" + pid);
     		},
     		error:function(data){
     			showMessageBox("您的评价提交失败!","项目评价");
     		}
     		
     	});
     }
     
     function launchModifyJionDate(){
     	 $("#jingbiaoDateShow").hide();
     	 $("#launchModifyJionDateBtn").hide();
		   $("#jingbiaoDateEditDiv").show();
		   $("#modifyJoinDateCancelBtn").show();
		   $("#modifyJoinDateBtn").show();
		   $("#jingbiaoDate").val($("#jingbiaoDateShow").html());
     }
     
     function doModifyJoinDate(){
       var mURL = "/api/1/p/bid/extension";
       var pid = $("#projectID").val();
       var topurl = $("#topurl").val();
       var bidEndTime = $("#jingbiaoDate").val() + " 23:59:59";
       var paraData = "id=" + pid +  "&date=" + bidEndTime;
      
       $.ajax({
	     		type:"post",
	     		url:mURL,
	     		data:paraData,
	     		async:false,
	     		success:function(data){
	     			/*doModifyJoinDateCancel();
	     			$("#jingbiaoDateShow").html($("#jingbiaoDate").val());
	     			showCountdown();*/
	     			location.href = topurl + "home/p/" + pid;
	     		}
     	});
     }
     
     function doModifyJoinDateCancel(){
     	 $("#jingbiaoDateShow").show();
     	 $("#launchModifyJionDateBtn").show();
		   $("#jingbiaoDateEditDiv").hide();
		   $("#modifyJoinDateCancelBtn").hide();
		   $("#modifyJoinDateBtn").hide();
     }
    </script>
  </head>
  <body>
    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->
    <div class="body-offset"></div>
    <div style="margin-top:120px;"></div>
 
    <!--<div class="container breadcrumb-container">
		  <ol class="breadcrumb">
			  <li><a href="/">首页</a></li>
			  <#--<#if opType == "0">
			  	<li><a href="/market">项目市场</a></li>
			  <#elseif opType == "1">
			  	<li><a href="/home/user_p_projects">我发布的项目</a></li>
			  <#elseif opType == "2">
			  	<li><a href="/home/user_j_projects">我竞标的项目</a></li>
			  </#if
			  <li><a href="#">项目详情</a></li>
		  </ol>
    </div>>-->
    
    <!--hidden parameter -->
    <div>
   		 <input id="pAttachementURL" type="hidden" value="${project.attachment}"/>
    </div>
    <!--end of hidden parameter-->
    
    <div class="container">
    	<div class="row project-detail-view">
    		<div class="project-detail-header">
    			<div class="col-lg-1">
    				<#if project.type = "微信开发">
    					<i class="weixin-icon"></i>
    				<#elseif project.type = "网站">
    					<i class="website-icon"></i>
    				<#elseif project.type = "iOS APP">
    					<i class="ios-icon"></i>
    				<#elseif project.type = "HTML5应用">
    					<i class="html5-icon"></i>
    				<#elseif project.type = "Android APP">
    					<i class="andriod-icon"></i>
    				<#elseif project.type = "其它">
    					<i class="others-icon"></i>
    				</#if>
    			</div>
    			<div class="col-lg-10 header-content">
    				<div class="project-name">
    						<h3>${project.name}</h3>
    				</div>
    			  <div id="pExpireTag" class="project-join-expired-icon" style="display:none;">
    			  	<div class="text">竞标超期</div>
    			  </div>
    			  <#if project.backgroudStatus == 3 || project.backgroudStatus == 4 || 
    				  project.backgroudStatus == 5 || project.backgroudStatus == 6>
	    			  <div id="pFundInTrustTag" class="project-fund-intrust-icon">
	    			  	<div class="text">资金已托管</div>
	    			  </div>
	    			</#if>
    			  <#if project.isSincerity == 1>
	    			  <div id="pInSincerityTag" class="project-in-sincerity-icon">
	    			  	<div class="text">诚意项目</div>
	    			  </div>
    			  </#if>
    			</div>
    		</div>
    		
    
				<div id="pProgressDiv" class="row"></div>
        
        <div class="project-status-4-sm">
        	<p>${project.status.type}</p>
        </div>
        
        <div>
        	<input id="projectStatus" value="${project.status.type}" type="hidden"></input>
        	<input id="projectID" value="${project.id}" type="hidden"></input>
        	<input id="projectInternalStatus" value="${project.status.sName}" type="hidden"></input>
        	<input id="projectInternalStatusCode" value="${project.backgroudStatus}" type="hidden"></input>
        	<input id="projectPriceRange" value="${project.priceRange}" type="hidden"></input>
        	<input id="projectContactType" value="${project.publicContact}" type="hidden"></input>
        	
        	
        	<#if isService>
        		<input id="h_isService" value="1" type="hidden"></input>
        	<#else>
        		<input id="h_isService" value="0" type="hidden"></input>
        	</#if>
        	
        	<#if isAdmin>
        		<input id="h_isAdmin" value="1" type="hidden"></input>
        	<#else>
						<input id="h_isAdmin" value="0" type="hidden"></input>
        	</#if>
        	
        	<#if isSelf>
        		<input id="h_isSelf" value="1" type="hidden"></input>
        	<#else>
						<input id="h_isSelf" value="0" type="hidden"></input>
        	</#if>
        	
        	<#if project.evaluateVo??>
	        	<input id="qualityScoreVal" value="${project.evaluateVo.qualityScore!""}" type="hidden"></input>
	        	<input id="speedScoreVal" value="${project.evaluateVo.speedScore!""}" type="hidden"></input>
	        	<input id="attitudeScoreVal" value="${project.evaluateVo.speedScore!""}" type="hidden"></input>
        	</#if>
        	
        	<#if project.backgroudStatus != 8>
	        	<#if project.backgroudStatus gt 1>
	        		<input id="joinChoosedDate" value='${project.selectedJoin.choosedTime?string("yyyy-MM-dd HH:mm")}' type="hidden"></input>
	        	</#if>
	        	
	        	<#if project.backgroudStatus gt 2>
	        		<input id="amountInTrustDate" value='${project.tradeInfo.createTime?string("yyyy-MM-dd HH:mm")}' type="hidden"></input>
	        	</#if>
	        	
	        	<#if project.backgroudStatus gt 5>
	        		<input id="acceptDate" value='${project.acceptTime?string("yyyy-MM-dd HH:mm")}' type="hidden"></input>
	        	</#if>
	        	
	        	<#if project.backgroudStatus == 9 || project.backgroudStatus == 11>
	        		<input id="finishDate" value='${project.finishTime?string("yyyy-MM-dd HH:mm")}' type="hidden"></input>
	        	</#if>
	        </#if>
        </div>
   
        <div class="row project-countdown">
        	<div class="col-lg-12 col-md-12" style="display:none" id="countdown_label">
        		<p class="countdown-label">离竞标截止还有：</p>
        	</div>
        	<div id="countdown" class="col-lg-12 col-md-12"></div>
        	<div id="countdownnote" class="col-lg-12 col-md-12 text-center"></div>
        </div>
        <#if project.backgroudStatus == 3 || project.backgroudStatus == 4 || 
    				  project.backgroudStatus == 5 || project.backgroudStatus == 6>
	        <div class="row">
							  <div class="col-lg-offset-4 col-lg-4 col-md-4 text-center">
							  	<div style="margin-left:-25px;">
										<span class="badge badge-md badge-warning">
											<p style="padding-top:10px;">托管金额：${project.tradeInfo.trusteeMoney!""}元</p>
										</span>
									</div>
								</div>
					</div>
				</#if>
        <hr>
    		<div class="row project-detail-abbr">	
    			<div class="col-lg-3 col-md-3">
    				<p class="i-label">项目预算：</p><p class="i-content" id="priceRangeShow">${project.priceRange}</p>
    				<#if isSelf>
    					<#if project.backgroudStatus == 1>
		    				<select style="display:none"  name="priceRangeReselect" id="priceRangeReselect">
				        </select>
		    				<a onclick="javascript:launchModifyPrice();" id="launchModifyPriceBtn" class="btn btn-xs btn-primary">修改</a>
		    				<a onclick="javascript:doModifyPrice();" id="modifyPriceBtn" style="display:none" class="btn btn-xs btn-primary">保存</a>
		    				<a onclick="javascript:doModifyPriceCancel();" id="modifyPriceCancelBtn" style="display:none" class="btn btn-xs btn-danger">取消</a>
	    				</#if>
    				</#if>
    				
    			</div>
    			<div class="col-lg-3 col-md-3">
    				<p class="i-label">发布时间：</p><p class="i-content">${project.createTime?string("yyyy-MM-dd HH:mm")} </p>
    			</div>
    			<div class="col-lg-3 col-md-3">
    				<p class="i-label">竞标截止：</p><p class="i-content" id="jingbiaoDateShow">${project.bidEndTime?string("yyyy-MM-dd")}</p>
    				<#if isSelf && project.backgroudStatus == 1>
	    				<div class="input-group date jingbiao_date" id="jingbiaoDateEditDiv" style="display:none">
									<input class="form-control  form-input-small" id="jingbiaoDate" name="jingbiaoDate" type="text" value="${project.bidEndTime?string("yyyy-MM-dd")}" readonly="true" required="true" />
									<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
									<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
							</div>
							<a onclick="javascript:launchModifyJionDate();" id="launchModifyJionDateBtn" class="btn btn-xs btn-primary">延期</a>
		    			<a onclick="javascript:doModifyJoinDate();" id="modifyJoinDateBtn" style="display:none" class="btn btn-xs btn-primary">保存</a>
		    			<a onclick="javascript:doModifyJoinDateCancel();" id="modifyJoinDateCancelBtn" style="display:none" class="btn btn-xs btn-danger">取消</a>
						</#if>
    			</div>
    			<div class="col-lg-3 col-md-3">
    				<p class="i-label">当前状态：</p><p id="pDisplayStatus" class="i-content">${project.status.sName!""}
	    				<#if isSelf || isSelectedUser>
	    					<#if project.backgroudStatus == 5>
	    						<a data-toggle="modal" href="#pDetailMessageDialog">查看</a>
	    					</#if>
	    				</#if>
	    				<#if isSelf || isAdmin>
	    					<#if project.backgroudStatus == -1>
	    						<a data-toggle="modal" href="#pDetailMessageDialog">查看</a>
	    					</#if>
	    				</#if>
    				</p>
    			</div>
    			<div class="col-lg-3 col-md-3">
    				<p class="i-label">项目周期：</p><p class="i-content">${project.period}天</p>
    			</div>
    			<div class="col-lg-3 col-md-3">
    				<p class="i-label">项目地点：</p><p class="i-content">${project.publisherInfo.region!""}</p>
    			</div>
    			<div class="col-lg-3 col-md-3">
    				<p class="i-label">竞标人数：</p><p class="i-content">${project.joinCount}</p>
    			</div>
    			<div class="col-lg-3 col-md-3">
    				<p class="i-label">雇主名称：</p><p class="i-content" title="${project.publisherInfo.name}">${project.publisherInfo.displayName}</p>
    			</div>
    			
    			
    			<#if isSelectedUser || isAdmin || (isDuplicate && isIdentifiedUser)>   
    					<#if project.publicContact?index_of("1") != -1>
    						<div class="col-lg-3 col-md-3">
    							<p class="i-label">雇主手机号：</p><p class="i-content">${project.contactMobile!""}</p>
    						</div>
    					</#if>
    						
    					<#if project.publicContact?index_of("2") != -1>
    						<div class="col-lg-3 col-md-3">
    							<p class="i-label">雇主邮箱：</p>
    							<p class="i-content" title="${project.contactEmail!""}">${project.displayContactEmail!""}</p>
    						</div>
    					</#if>
    					
    					<#if project.publicContact?index_of("3") != -1>
    						<div class="col-lg-3 col-md-3">
    							<p class="i-label">雇主QQ：</p><p class="i-content">${project.contactQq!""}</p>
    						</div>
    					</#if>
    					
    					<#if project.publicContact?index_of("4") != -1>
    					  <div class="col-lg-3 col-md-3">
    						   <p class="i-label">雇主微信：</p><p class="i-content">${project.contactWeixin!""}</p>
    						</div>
    					</#if>
    					
    					<#if project.backgroudStatus == 1 && isService>
	    					<div class="col-lg-12 col-md-12">
	    					 	<p class="text-danger"><i class="fa fa-info-circle"></i>&nbsp;联系雇主时，请说明是在码客帮网站上看到的,谢谢合作！</p> 
	    					</div>   					
    				  </#if>
    			<#else>
    				<#if project.backgroudStatus == 1 && isService>
	    				<div class="col-lg-5 col-md-5">
	    					<p class="text-danger"><i class="fa fa-info-circle"></i>&nbsp;只有认证并参与竞标的服务商才可以浏览客户联系方式</p>
	    					<#if !isIdentifiedUser>
	    						<a href="/home/userinfo" class="btn btn-xs btn-primary">立即认证</a>
	    					</#if>
	    				</div>	    			 
    				</#if>
    			</#if>
    			
    			<#if project.backgroudStatus == 9 || project.backgroudStatus == 11>
    				<div class="col-lg-3 col-md-3">
    				<p class="i-label">实际成交金额：</p><p class="i-content">${project.tradeInfo.actuallyPayment}元</p>
    			</div>
    			<div class="col-lg-3 col-md-3">
    				<p class="i-label">实际开发周期：</p><p class="i-content">${project.tradeInfo.actuallyPeriod}天</p>
    			</div>
    			</#if>
    		</div>
    		<hr>
    		
    	 <#if isSelf>
    	 	<#if project.backgroudStatus == -1 || project.backgroudStatus == 0  ||
    	 	     project.backgroudStatus == 8>
    	 		<div class="row project-operation-view">
		    			<div class="col-lg-offset-4 col-lg-4 col-md-4">
		    				<button class="btn btn-block btn-primary" onclick="javascript:doModifyRequest(${project.id});">我要修改</button>
		    			</div>
			    </div>
    	 	</#if>
    	 </#if>
    			
    		<#if isSelf>
    			<#if project.backgroudStatus == 4>
		    		<div class="row project-operation-view">
		    			<div class="col-lg-offset-4 col-lg-2 col-md-2">
		    					<a onclick="javascript:acceptanceCheckPass();" class="btn btn-primary btn-block">验收通过</a>
		    			</div>
		    			<div class="col-lg-2 col-md-2">
		    					<a data-toggle="modal" href="#acceptanceCheckDialog" class="btn btn-primary btn-block">验收驳回</a>
		    			</div>
		    		</div>
	    		</#if>
    		</#if>
    		
    	  <#if isSelf>
					<#if project.backgroudStatus == 1>
						<div class="row project-operation-view">
						 <div class="col-lg-offset-4 col-lg-4 col-md-4">
							<a href="#pContentAppendDiag" class="btn btn-primary btn-block" data-toggle="modal">我要完善</a>
						 </div>
					 </div>
					</#if>
			  </#if>
			  
		    <#if isSelf || isAdmin>
				  <#if project.backgroudStatus == 2>
				  	<div class="row">
				  		<div class="col-lg-offset-4 col-lg-4 col-md-4 text-center">
					  		<span class="badge badge-lg badge-warning">
					  			<p style="padding-top:10px;">您已选标，请联系客服进行资金托管</p>
					  		</span>
				  		</div>
				  	</div>
				  </#if>
				 </#if>
				 
			  <#if isService>
					<#if project.backgroudStatus == 1>
						<div class="row project-operation-view">
						  <div class="col-lg-offset-4 col-lg-4 col-md-4">
									<#if !isDuplicate>
										<a data-toggle="modal" id="pJoinBtn" onclick="javascript:checkInfoComplete(this);" class="btn btn-primary btn-block">我要竞标</a>
									<#else>
										<a data-toggle="modal" id="pJoinModifyBtn" onclick="javascript:launchModifyJoinInfo(this);"  class="btn btn-primary btn-block">修改竞标信息 </a>
									</#if>
							</div>
					 </div>
					</#if>
				</#if>	 
				
				 <#if isSelf>
					<#if project.backgroudStatus == 9>
						<div class="row project-operation-view">
						 <div class="col-lg-offset-4 col-lg-4 col-md-4">
							<a href="#pEvaluateDialog" class="btn btn-primary btn-block" data-toggle="modal">我要评价</a>
						 </div>
					 </div>
					</#if>
			  </#if>
			 
    		<#if isSelf>
    			<#if project.backgroudStatus == 4>
    			  <div class="modal" id="acceptanceCheckDialog">
					  <div class="modal-dialog modal-custom-class">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
					        <h4 class="modal-title">验收驳回</h4>
					      </div>
					      <div class="modal-body">
						        <form class="form-horizontal form-in-modal" id="acceptanceCheckForm" action="javascript:doAcceptanceReject();" role="form"  method="post">
						        	<div class="form-group">
						        		<div class="col-lg-12 col-md-12">
						        			<label class="control-label form-label" for="jPrice"><i class="form-required">*</i>驳回原因(至少10个字符):</label>
						        		</div>
						        		<div class="col-lg-10 col-md-10">
						        			<textarea id="acceptanceRejectReason" name="acceptanceRejectReason" class="form-control form-input-area" type="text" rows="5" required minlength="10" maxlength="500"></textarea>
						        		</div>
						        	</div>
						        	<div>
						        	<button type="button" class="btn btn-lg btn-default form-btn " data-dismiss="modal">取消</button>
						        	<button type="submit" class="btn btn-lg btn-primary form-btn">保存</button>
						        	</div>
						        </form>
					       </div>
					    </div>
					  </div>
           </div>
         </#if>
    		</#if>
    		
    		<#if isDuplicate>
    			<#if isSelectedUser>
	    			<#if project.backgroudStatus == 3>
	    				<div class="row">
			    			<div class="col-lg-offset-4 col-lg-4 col-md-4">
			    				<button class="btn btn-block btn-primary" onclick="doAcceptanceCheck();">申请验收</button>
			    			</div>
			    		</div>
			    	</#if>
	    			<#if project.backgroudStatus == 5>
	    			<div class="row">
			    			<div class="col-lg-offset-4 col-lg-4 col-md-4">
			    				<button class="btn btn-block btn-primary" onclick="doAcceptanceCheck();">重新申请验收</button>
			    			</div>
			    		</div>
	    			</#if> 
	    		</#if>
    	  </#if>
    	  
    	  <#if project.backgroudStatus == 11>
			  	<div class="row project-evaluator">
			  		<h4><strong>项目评价:</strong></h4>
				  	 <!--<div class="col-lg-offset-1 col-lg-12 col-md-12">
								 <span class="badge badge-md badge-danger"><i class="fa fa-pencil"></i>项目评价</span>
							</div>-->
						<div class="row project-evaluator-box">
					  	<div class="col-lg-3 col-md-3"><p class="i-label">完成质量：</p><p id="p_qualityScore"></p></div>
					  	<div class="col-lg-3 col-md-3"><p class="i-label">完成速度：</p><p id="p_speedScore"></p></div>
					  	<div class="col-lg-3 col-md-3"><p class="i-label">服务质量：</p><p id="p_attitudeScore"></p></div>
					  	<#if project.evaluateVo.description??>
						  	<div class="col-lg-12 col-md-12"><p class="i-label">评价描述：</p></div>
						  	<div class="col-lg-12 col-md-12">${project.evaluateVo.description!""}</div>
					  	</#if>
				  	</div>
				  </div>
        </#if>
        
    		<div class="row project-detail-content">
    			<div>
    					<h4><strong>项目详细描述:</strong></h4>
    			</div>
    			<div id="pDetailDiv" class="row detail-content-box">
    				${project.content}
    			</div>
    		</div>
    		
    		<#if project.attachment?? && project.attachment != "">
    			<div class="row project-attachment-view">
    				<h4><strong>项目附件:</strong></h4>
    				<p>附件：
    					<a href="${project.attachment!""}" target="_blank">
    					<#if project.attachment?index_of(".doc") != -1>
    						<i class="fa fa-file-word-o"></i>
    					<#elseif  project.attachment?index_of(".pdf") != -1>
    						<i class="fa fa-file-pdf-o"></i>
    					</#if>
    					点此查看
    				 </a>
    				</p>
    			</div>
    		</#if>
    		
    		<#if isSelf>
    			<#if project.backgroudStatus == 1>
    				<div class="modal" id="pContentAppendDiag">
					  	<div class="modal-dialog modal-custom-class">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
						        <h4 class="modal-title">完善需求</h4>
						      </div>
						      <div class="modal-body">
							        <form class="form-horizontal form-in-modal" id="pContentAppendForm" action="javascript:doContentAppend();" role="form"  method="post">
							        	<div class="form-group">
							        		<div class="col-lg-12 col-md-12">
							        			<label class="control-label form-label" for="jPeriod"><i class="form-required">*</i>补充需求描述(至少10个字符):</label>
							        		</div>
							        		<div class="col-lg-11 col-md-11">
							        			<textarea id="newRequestDesc" name="newRequestDesc" class="form-control form-input-area" type="text" rows="8" required minlength="10" maxlength="1000"></textarea>
							        		</div>
							        	</div>
							        	
							        	<div class="form-group">
							        		<div class="col-lg-12 col-md-12">
							        			<label class="control-label form-label" for="jPeriod">附件上传(<i><font color="red">新附件将会替换原来的附件，请谨慎操作</font></i>):</label>
							        		</div>
							        		<div class="col-lg-11 col-md-11">
							        			<input id="fileUpload" name="fileUpload" class="file" type="file"></input>
							        		</div>
							        				<div class="col-lg-11 col-md-11">
									         	<p>将需求描述附件上传，文件支持5M以内的doc、docx、pdf格式;Mac OS系统请使用新版Firefox浏览器上传，也可联系码客帮客服协助上传。</p>
									        </div>
								         <div class="col-lg-12 col-md-12" >
						         	 			<span id="fileUploadErrMsg" class="error-message"></span>
							         	</div>
								         <div class="col-lg-12 col-md-12" >
								         	 <span id="fileUploadMsg" class="error-message"></span>
								         </div>
							        	</div>
						        	<div>
						        	<button type="button" class="btn btn-lg btn-default form-btn" data-dismiss="modal">取消</button>
						        	<button type="submit" class="btn btn-lg btn-primary form-btn">保存</button>
						        	</div>
						        </form>
					      </div>
					    </div>
					  </div>
            </div>
    			</#if>
    		</#if>
    		
		<#if isService>
			 <#if isDuplicate>
			  <div class="modal" id="jingbiaoDialog">
					  <div class="modal-dialog modal-custom-class">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
					        <h4 class="modal-title">修改竞标信息</h4>
					      </div>
					      <div class="modal-body">
						        <form class="form-horizontal form-in-modal" id="jingbiaoForm" action="javascript:doJBModify();" role="form"  method="post">
						        	<div class="form-group">
						        		<div class="col-lg-2 col-md-2">
						        			<label class="control-label form-label" for="jPrice"><i class="form-required">*</i>竞标费用:</label>
						        		</div>
						        		<div class="col-lg-4 col-md-4">
						        			<input class="form-control form-input" id="jPrice" name="jPrice" type="text" value="${joinInfo.price!""}" required digits="true" ></input>
						        		</div>
						        		<div class="col-lg-4 col-md-4"><p class="modal-dialog-tip">元&nbsp;&nbsp;&nbsp;【项目预算：${project.priceRange}】</p></div>
						        	</div>
						        		<div class="form-group">
						        		<div class="col-lg-2 col-md-2">
						        			<label class="control-label form-label" for="jPeriod"><i class="form-required" >*</i>开发周期:</label>
						        		</div>
						        		<div class="col-lg-4 col-md-4">
						        			<input id="jPeriod" name="jPeriod" class="form-control form-input" type="text" value="${joinInfo.period!""}" required digits="true" maxlength="3"></input>
						        		</div>
						        		<div class="col-lg-4 col-md-4"><p class="modal-dialog-tip">天&nbsp;&nbsp;&nbsp;【项目预估周期：${project.period}天】</p></div>
						        	</div>
						        
						        	<div>
						        		<input type="hidden" id="h_JMPeriod" value="${joinInfo.matainancePeriod!""}"></input>
						        	</div>
						        	
						        	<div class="form-group">
						        		<div class="col-lg-2 col-md-2">
						        			<label class="control-label form-label" for="jMPeriod"><i class="form-required">*</i>免费售后:</label>
						        		</div>
						        		<div class="col-lg-4 col-md-4">
						        			<select class="form-control form-select" id="jMPeriod" name="jMPeriod" required>
						        			</select>
						        		</div>
						        		
					           	 <#if joinInfo.attachment?? && joinInfo.attachment != "">
							        		<div id="orgFileUploadDiv" class="col-lg-6 col-md-6">
			                     	<label class="control-label form-label">原附件：</label>
														<a href="${joinInfo.attachment!""}" target="_blank">
															<#if joinInfo.attachment?index_of(".doc") != -1>
								    						<i class="fa fa-file-word-o"></i>
								    					<#elseif joinInfo.attachment?index_of(".pdf") != -1>
								    						<i class="fa fa-file-pdf-o"></i>
								    					</#if>
															点击查看
														</a>
		                      </div> 
                       </#if>
                    </div>
                      
						        	<div class="form-group">
						        		<div class="col-lg-12 col-md-12">
						        			<label class="control-label form-label" for="jPeriod"><i class="form-required">*</i>竞标方案（至少10个字符）:</label>
						        		</div>
						        		<div class="col-lg-11 col-md-11">
						        			<textarea id="jDesc" name="jDesc" class="form-control form-input-area" type="text" rows="5" required minlength="10" maxlength="1000">${joinInfo.plan!""}</textarea>
						        		</div>
						        	</div>
						        	
						        	<div class="form-group">
							        		<div class="col-lg-2 col-md-2">
							        			<label class="control-label form-label" for="jPeriod">上传附件:</label>
							        		</div>
							        		<div class="col-lg-9 col-md-9">
							        			<input id="fileUpload" name="fileUpload" class="file" type="file"></input>
							        		</div>
					        				<div class="col-lg-11 col-md-11">
							          	<p>附件上传，文件支持5M以内的doc、docx、pdf格式;Mac OS系统请使用新版Firefox浏览器上传，也可联系码客帮客服协助上传。(<i><font color="red">温馨提示：新附件将会替换原来的附件，请谨慎操作</font></i>)</p>
							           </div>
								         <div class="col-lg-12 col-md-12" >
						         	 			<span id="fileUploadErrMsg" class="error-message"></span>
							         	</div>
								         <div class="col-lg-12 col-md-12" >
								         	 <span id="fileUploadMsg" class="error-message"></span>
								         </div>
							        </div>
							        
						        	<div>
						        	<button type="button" class="btn btn-lg btn-default form-btn " data-dismiss="modal">取消</button>
						        	<button type="submit" class="btn btn-lg btn-primary form-btn">保存</button>
						        	</div>
						        </form>
					       </div>
					      <!--<div class="modal-footer">
					      </div>-->
					    </div>
					  </div>
         </div><!-- /.modal -->
         <#else>
         	<div class="modal" id="jingbiaoDialog">
					  <div class="modal-dialog modal-custom-class">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					        <h4 class="modal-title">我要竞标</h4>
					      </div>
					      <div class="modal-body">
						        <form class="form-horizontal form-in-modal" id="jingbiaoForm" action="javascript:doJBCreate();" role="form"  method="post">
						        	<div class="form-group">
						        		<div class="col-lg-2 col-md-2">
						        			<label class="control-label form-label" for="jPrice"><i class="form-required">*</i>竞标费用:</label>
						        		</div>
						        		<div class="col-lg-4 col-md-4">
						        			<input class="form-control form-input" id="jPrice" name="jPrice" type="text"  required digits="true" ></input>
						        		</div>
						        		<div class="col-lg-4 col-md-4"><p class="modal-dialog-tip">元&nbsp;&nbsp;&nbsp;【项目预算：${project.priceRange}】</p></div>
						        	</div>
						        		<div class="form-group">
						        		<div class="col-lg-2 col-md-2">
						        			<label class="control-label form-label" for="jPeriod"><i class="form-required" >*</i>开发周期:</label>
						        		</div>
						        		<div class="col-lg-4 col-md-4">
						        			<input id="jPeriod" name="jPeriod" class="form-control form-input" type="text" required digits="true" maxlength="3"></input>
						        		</div>
						        		<div class="col-lg-4 col-md-4"><p class="modal-dialog-tip">天&nbsp;&nbsp;&nbsp;【项目预估周期：${project.period}天】</p></div>
						        	</div>
						        	
						        	<div class="form-group">
						        		<div class="col-lg-2 col-md-2">
						        			<label class="control-label form-label" for="jMPeriod"><i class="form-required">*</i>免费售后:</label>
						        		</div>
						        		<div class="col-lg-4 col-md-4">
						        			<select class="form-control form-select" id="jMPeriod" name="jMPeriod" required>
						        			</select>
						        		</div>
						        	</div>
						        	
						        	<div class="form-group">
						        		<div class="col-lg-12 col-md-12">
						        			<label class="control-label form-label" for="jPeriod"><i class="form-required">*</i>竞标方案（至少10个字符）:</label>
						        		</div>
						        		<div class="col-lg-11 col-md-11">
						        			<textarea id="jDesc" name="jDesc" class="form-control form-input-area" type="text" rows="5" required minlength="10" maxlength="1000"></textarea>
						        		</div>
						        	</div>
						        	
						        	<div class="form-group">
							        		<div class="col-lg-2 col-md-2">
							        			<label class="control-label form-label" for="jPeriod">上传附件:</label>
							        		</div>
							        		<div class="col-lg-9 col-md-9">
							        			<input id="fileUpload" name="joinPlanUpload" class="file" type="file"></input>
							        		</div>
					        				<div class="col-lg-11 col-md-11">
							          	<p>附件上传，文件支持5M以内的doc、docx、pdf格式;Mac OS系统请使用新版Firefox浏览器上传，也可联系码客帮客服协助上传。</p>
							           </div>
								         <div class="col-lg-12 col-md-12" >
						         	 			<span id="fileUploadErrMsg" class="error-message"></span>
							         	</div>
								         <div class="col-lg-12 col-md-12" >
								         	 <span id="fileUploadMsg" class="error-message"></span>
								         </div>
							        </div>
						        	<div>
						        	<button type="button" class="btn btn-lg btn-default form-btn " data-dismiss="modal">取消</button>
						        	<button type="submit" class="btn btn-lg btn-primary form-btn">保存</button>
						        	</div>
						        </form>
					       </div>
					      <!--<div class="modal-footer">
					      </div>-->
					    </div>
					  </div>
         </div>
         </#if>
       </#if>	  
         
				 <#if isSelf || isAdmin>
			      <div class="row project-join-view">
			      	<h4><strong>竞标列表:</strong></h4>
			  	  <#if (project.joinList?size>0)>
							<#list project.joinList as item> 
							<div class="row join-view-box">
							<div class="col-lg-9 col-md-9">
								<#if isSelected>
									<#if item.choosed == 1>
										<div class="project-selected-icon"></div>
									</#if>
								</#if>
								
								<#if item.choosed == -1>
									<div class="project-wentout-icon"></div>
							  </#if>
                
                <div id="div_${item.userInfo.id!""}" class="col-lg-12 col-md-12 join-note-view" style="padding-bottom: 10px;">
						    </div>  
						    
						    <#if item.remark?? && item.remark != "">   
								  <div style="display:none">
								  	<textarea id="t_${item.userInfo.id!""}">${item.remark}</textarea>
								  </div>
						     </#if>
						    
							  <div class="col-lg-3 col-md-3">
							  	<p class="i-label">联系方式：</p><p>${item.userInfo.mobile!""}</p>
							  </div>
							  <div class="col-lg-4 col-md-4">
							  	<p class="i-label">E-mail：</p><p title="${item.userInfo.email!""}">${item.userInfo.displayEmail!""}</p>
							  </div>
							  
							  <div class="col-lg-5 col-md-5">
							    <p class="i-label">竞标者：</p>
							    <p>
							    	<a href="/public/m/${item.userInfo.name!""}" title="${item.userInfo.name!""}">${item.userInfo.displayName!""}</a>
									<#if item.userInfo.identifyStatus == 1>
							    		<#if item.userInfo.identifyCategory == 0>
							    			<img src="${ctx}/img/v-personal.png" class="verification-img" title="已通过平台个人认证">
							    		<#elseif item.userInfo.identifyCategory == 1>
							    			<img src="${ctx}/img/v-company.png" class="verification-img" title="已通过平台企业认证">
							    		</#if>
							    	</#if>
							    </p>
							  </div>
							 
							 <div class="col-lg-3 col-md-3">
							     <p class="i-label">周期：</p><p>${item.period}天</p>
							  </div>
							  <div class="col-lg-4 col-md-4">
							   <p class="i-label">竞标价格：</p><p>${item.price}元</p>
							  </div>							  
							  <div class="col-lg-4 col-md-4">
							  	 <p class="i-label">竞标时间：</p><p>${item.createTime?string("yyyy-MM-dd HH:mm")}</p>
							  </div>
							   <div class="col-lg-3 col-md-3">
							  	 <p class="i-label">地点：</p><p>${item.userInfo.region!""}</p>
							  </div>
							  <div class="col-lg-4 col-md-4">
							  	 <p class="i-label">免费维护：</p>
							  	 <#if item.matainancePeriod == 0>
							  	 	<p>不提供</p>
							  	 <#else>
							  	 	${item.matainancePeriod!""}个月
							  	 </#if>
							  </div>
							  <#if item.attachment?? && item.attachment != "">
					      	<div class="col-lg-3 col-md-3">
							  	 <p class="i-label">附件：</p>
							  	 <p><a href="${item.attachment!""}" target="_blank">
							  	 	<#if item.attachment?index_of(".doc") != -1>
			    						<i class="fa fa-file-word-o"></i>
			    					<#elseif item.attachment?index_of(".pdf") != -1>
			    						<i class="fa fa-file-pdf-o"></i>
			    					</#if>
							  	 	点此查看
							  	 </a>
							  	</p>
							  	</div>
						   </#if>
							 </div>
							 <div class="col-lg-3 col-md-3">
							 	<br>
							 	 <#if isSelf>
							    <#if project.backgroudStatus == 1>
									  <div class="col-lg-12 col-md-12 btn-group">
									  	<#if item.choosed != -1>
										  	<a class="btn btn-primary" onclick="doChoose('${item.userInfo.name!""}','${item.userInfo.id!""}','${item.price!""}','${item.period!""}');">选标</a>
										  	<a class="btn btn-primary" onclick="doKick('${item.projectId!""}','${item.userInfo.id!""}','${item.userInfo.name!""}');">淘汰</a>
										  	<a class="btn btn-primary" data-toggle="modal" href="javascript:void(0);" onclick="javascript:launchRemark(this,'${item.projectId!""}','${item.userInfo.id!""}');">备注</a>
		                  <#else>
										  	<a class="btn btn-primary" onclick="doCancelKick('${item.projectId!""}','${item.userInfo.id!""}','${item.userInfo.name!""}');">撤销淘汰</a>
										  	<a class="btn btn-primary" data-toggle="modal" href="javascript:void(0);" onclick="javascript:launchRemark(this,'${item.projectId!""}','${item.userInfo.id!""}');">备注</a>
										  </#if>
									  </div>
									 </#if>
								 </#if>
								 <#if isAdmin>
								 	<#if project.backgroudStatus == 1>
								 		<div class="col-lg-2 col-md-2">
								  	 		<a class="btn btn-primary" onclick="doChoose('${item.userInfo.name!""}','${item.userInfo.id!""}','${item.price!""}','${item.period!""}');">选标</a>
								  	 </div>
								  </#if>
						  	  <#if project.backgroudStatus == 2 && item.choosed == 1>
								  	 <div class="col-lg-2 col-md-2">
								  	 		<button class="btn btn-primary" onclick="doCancelChoice('${item.userInfo.name!""}');">撤销竞标</button>
								  	 </div>
							  	 </#if>
								 </#if>
							 </div>
							  <br>
							  <div class="col-lg-12 col-md-12 join-plan-view">
							  	<div class="col-lg-10 col-md-10">
							     <p class="i-label">竞标方案：</p><br><p>${item.plan}</p>
							     </div>
							  </div> 
							 </div>
							</#list>	
							<#else>
							  <p class="message">目前还没有任何竞标信息</p>
						 </#if>
				  </div>
			  <#else>
			  	<#if !isSelected && isDuplicate>
					  	<div class="row project-join-view">
					      	<h4><strong>竞标列表:</strong></h4>
									<div class="row join-view-box">
									  <div class="col-lg-3 col-md-3">
									   <p class="i-label">竞标价格：</p><p>${joinInfo.price}元</p>
									  </div>
									  <div class="col-lg-3 col-md-3">
									     <p class="i-label">周期：</p><p>${joinInfo.period}天</p>
									  </div>
									   <div class="col-lg-5 col-md-5">
									    <p class="i-label">竞标者：</p>
									    <p>
									    	<a href="/public/m/${joinInfo.userInfo.name!""}">${joinInfo.userInfo.name!""}</a>
									    	<#if joinInfo.userInfo.identifyStatus == 1>
									    		<#if joinInfo.userInfo.identifyCategory == 0>
									    			<img src="${ctx}/img/v-personal.png" class="verification-img" title="已通过平台个人认证">
									    		<#elseif joinInfo.userInfo.identifyCategory == 1>
									    			<img src="${ctx}/img/v-company.png" class="verification-img" title="已通过平台企业认证">
									    		</#if>
									    	</#if>
							   		 </p>
									  </div>
									  <div class="col-lg-3 col-md-3">
									  	 <p class="i-label">竞标时间：</p><p>${joinInfo.createTime?string("yyyy-MM-dd HH:mm")}</p>
									  </div>
									   <div class="col-lg-3 col-md-3">
									  	 <p class="i-label">免费维护：</p>
									  	 <#if joinInfo.matainancePeriod == 0>
									  	 	<p>不提供</p>
									  	 <#else>
									  	 	${joinInfo.matainancePeriod!""}个月
									  	 </#if>
							      </div>
							      <#if joinInfo.attachment?? && joinInfo.attachment != "">
							      	<div class="col-lg-3 col-md-3">
									  	 <p class="i-label">附件：</p>
									  	 <p><a href="${joinInfo.attachment!""}" target="_blank">
									  	 	<#if joinInfo.attachment?index_of(".doc") != -1>
					    						<i class="fa fa-file-word-o"></i>
					    					<#elseif joinInfo.attachment?index_of(".pdf") != -1>
					    						<i class="fa fa-file-pdf-o"></i>
					    					</#if>
									  	 	点此查看</a></p>
									  	</div>
							      </#if>
									  <div class="col-lg-11 col-md-11 join-plan-view">
									     <p class="i-label">竞标方案：</p><br><p>${joinInfo.plan}</p>
									  </div>    
									 </div>  
					  	</div>
					 <#elseif isSelected || project.backgroudStatus == 8>
					 	<div class="row project-join-view">
			      	    <h4><strong>竞标列表:</strong></h4>
					  	<#if project.joinList??>
							<#list project.joinList as item> 
							<div class="row join-view-box">
							 <div class="col-lg-9 col-md-9">
								<#if item.choosed == 1>
									<div class="project-selected-icon"></div>
							  </#if>
							  
							  <#if isSelf || isAdmin>
								   <div class="col-lg-3 col-md-3">
								  	<p class="i-label">联系方式：</p><p>${item.userInfo.mobile!""}</p>
								  </div>
								  <div class="col-lg-4 col-md-4">
								  	<p class="i-label">E-mail：</p><p title="${item.userInfo.email!""}">${item.userInfo.displayEmail!""}</p>
								  </div>
								</#if>
								
							 
							  <div class="col-lg-3 col-md-3">
							     <p class="i-label">周期：</p><p>${item.period}天</p>
							  </div>
							   <div class="col-lg-4 col-md-4">
							   <p class="i-label">竞标价格：</p><p>${item.price}元</p>
							  </div>
							  <div class="col-lg-4 col-md-4">
							  	 <p class="i-label">竞标时间：</p><p>${item.createTime?string("yyyy-MM-dd HH:mm")}</p>
							  </div>
							   <div class="col-lg-3 col-md-3">
							  	 <p class="i-label">免费维护：</p>
							  	 <#if item.matainancePeriod == 0>
							  	 	<p>不提供</p>
							  	 <#else>
							  	 	${item.matainancePeriod!""}个月
							  	 </#if>
							  </div>
							  <div class="col-lg-4 col-md-4">
							  	 <p class="i-label">地点：</p><p>${item.userInfo.region!""}</p>
							  </div>
							   <div class="col-lg-5 col-md-5">
							    <p class="i-label">竞标者：</p>
							    <p>
							    	<a href="/public/m/${item.userInfo.name!""}" title="${item.userInfo.name!""}">${item.userInfo.displayName!""}</a>
									<#if item.userInfo.identifyStatus == 1>
							    		<#if item.userInfo.identifyCategory == 0>
							    			<img src="${ctx}/img/v-personal.png" class="verification-img" title="已通过平台个人认证">
							    		<#elseif item.userInfo.identifyCategory == 1>
							    			<img src="${ctx}/img/v-company.png" class="verification-img" title="已通过平台企业认证">
							    		</#if>
							    	</#if>
							    </p>
							  </div>
							  
							  <#if item.attachment?? && item.attachment != "">
					      	<div class="col-lg-3 col-md-3">
							  	 <p class="i-label">附件：</p>
							  	 <p><a href="${item.attachment!""}" target="_blank">
							  	 	<#if item.attachment?index_of(".doc") != -1>
			    						<i class="fa fa-file-word-o"></i>
			    					<#elseif item.attachment?index_of(".pdf") != -1>
			    						<i class="fa fa-file-pdf-o"></i>
			    					</#if>
							  	 	点此查看</a></p>
							  	</div>
						    </#if>
							  <div class="col-lg-11 col-md-11 join-plan-view">
							     <p class="i-label">竞标方案：</p><br><p>${item.plan}</p>
							  </div>    
							 </div>
							 </div>
							</#list>
						</#if>
						</div>
			  </#if>
			 </#if>
			 
			 <#if isSelf || isAdmin>
			 	<div class="modal" id="noteDialog">
					  <div class="modal-dialog modal-custom-class">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					        <h4 class="modal-title">我要备注</h4>
					      </div>
					      <div class="modal-body">
						        <form class="form-horizontal form-in-modal" id="noteForm" action="javascript:doRemark();" role="form"  method="post">
						        	<div class="form-group">
						        	<div class="form-group">
						        		<div class="col-lg-12 col-md-12">
						        			<label class="control-label form-label" for="jPeriod"><i class="form-required">*</i>备注内容（至少5个字符）:</label>
						        		</div>
						        		<div class="col-lg-11 col-md-11">
						        			<textarea id="jNote" name="jNote" class="form-control form-input-area" type="text" rows="5" required minlength="5" maxlength="500"></textarea>
						        		</div>
						        	</div>
						  
						        	<div>
						        	<button type="button" class="btn btn-lg btn-default form-btn " data-dismiss="modal">取消</button>
						        	<button type="submit" class="btn btn-lg btn-primary form-btn">保存</button>
						        	</div>
						        </form>
					       </div>
					   </div>
					  </div>
					</div>
				 </div>
			 </#if>
			 
			 <#if isSelf || isAdmin || isSelectedUser>
			 	<#if project.backgroudStatus == 5 || project.backgroudStatus == -1>
			 		<div class="modal" id="pDetailMessageDialog">
					  <div class="modal-dialog modal-custom-class">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
					        <h4 class="modal-title">
					        	<#if project.backgroudStatus == 5>
					        		项目验收驳回
					        	<#elseif project.backgroudStatus == -1>
					        		项目审核驳回
					        	</#if>	
					        </h4>
					      </div>
					      <div class="modal-body">
									<div id="pDetailMessage" class="show-reason-box">
										<#if project.backgroudStatus == 5>
										  ${project.acceptResult!""}
										<#elseif project.backgroudStatus == -1>
											${project.checkResult!""}
										</#if>
						      </div>
					       </div>
					      <!--<div class="modal-footer">
					      </div>-->
					    </div>
					  </div>
         </div><!-- /.modal -->
			 	</#if>
			 </#if>
			 
			 <#if isSelf>
			 	<#if project.backgroudStatus == 9>
			 		<!--evaluating dialog-->
					 <div class="modal" id="pEvaluateDialog">
					  <div class="modal-dialog modal-custom-class">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-remove"></i></button>
					        <h4 class="modal-title">我要评价</h4>
					      </div>
					      <div class="modal-body">
					        <form class="form-horizontal form-in-modal" id="pEvaluateForm" action="javascript:doPEvaluate();" role="form"  method="post">
					        	<div>
					        		<input type="hidden" id="pidInEvaluator"></input>
					        	</div>
							     <div class="form-group star-rating-box">
						        	 <div class="col-lg-3 col-md-3">
							           <label class="control-label form-label"><i class="form-required">*</i>完成质量：</label>
							         </div>
							         <div class="col-lg-6 col-md-6">
							    		<div id="qualityScore" class="star-rating-div"></div>
							    	</div>
							     </div>
							     
							     <div class="form-group star-rating-box">
						        	 <div class="col-lg-3 col-md-3">
							           <label class="control-label form-label"><i class="form-required">*</i>完成速度：</label>
							         </div>
							         <div class="col-lg-6 col-md-6">
						    			 <div id="speedScore" class="star-rating-div"></div>
						    		 </div>
							     </div>
							     
							   <div class="form-group star-rating-box">
						        	 <div class="col-lg-3 col-md-3">
							           <label class="control-label form-label"><i class="form-required">*</i>服务态度：</label>
							         </div>
							         <div class="col-lg-6 col-md-6 star-rating-div">
						    			 <div id="attitudeScore"></div>
						    		 </div>
							     </div>
				        	
				        	    <div class="form-group">
						        	 <div class="col-lg-12 col-md-12">
							           <label class="control-label form-label">&#12288;评价（150字以内）：</label>
							         </div>
							         <div class="col-lg-9 col-md-9">
						    			 <textarea id="evaluateDesc" name="evaluateDesc" class="form-control" rows="5" type="text" maxlength="150"></textarea>
						    		 </div>
							     </div>
					        	<div>
					        	<button type="button" class="btn btn-lg btn-default form-btn " data-dismiss="modal">取消</button>
					        	<button type="submit" class="btn btn-lg btn-primary form-btn">提交</button>
					        	</div>
					        </form>
					       </div>
					      <!--<div class="modal-footer">
					      </div>-->
					    </div>
					  </div>
					 </div>
				   <!--end of evaluator dialog-->
			 	</#if>
			 </#if>
		</div>
  </div>

  <!--start of footer-->   
  <#include "../footer.ftl">
  <!--end of footer-->
  <!---start of help docker-->
  <div id="top"></div>
  <!--end of help docker-->
  
   <div>
   	<input type="hidden" id="topurl" value="${ctx}/"/>
   	<input type="hidden" id="reload" value="1"/>
   </div>
   
   <script type="text/javascript">
      var uploadType = 1;
   
      if(isServicer()){
      	uploadType = 2;
      }
      
		  initFileIuput("#fileUpload","#orgFileUploadDiv",uploadType);
	 </script>
	 
	  <!--CNZZ CODE-->
      ${cnzz_html}
    <!--END OF CNZZ CODE-->
  </body>
</html>
