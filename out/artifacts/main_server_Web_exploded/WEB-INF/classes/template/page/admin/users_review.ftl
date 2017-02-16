<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>用户审核 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}

    <script type="text/javascript">


    	$(document).ready(function(){
    		initUsers();

        var userType = $("#h_UserType").val();
        if(userType == -1){
          	initSearchView();
        }


    		//support enter key down to do fuzzy search
    		$("#pKey").keydown(function(event){
    			if(event && event.keyCode==13){ // enter key
              doFuzzySearch();
           }
    		});
    	});


    	function initUsers(){
          var userType = $("#h_UserType").val();

    		  var gURL = "/api/2/u/list";

          if(userType == -1){
            internalSearch(gURL,"-2","-2",1);
          }else{
            //显示已经认证的用户
            internalSearch(gURL,"1","-2",1);
          }

    	}

    	var ITEMHEIGHT = 63;
    	var DIVOFFSET = 450;

    	function generateUsersView(data){
    		if(data == null){
    			return;
    		}

    		var itemHTML;

    	 /*if(data.length < 3){
    			showFooter(1);
    		}else{
    			showFooter(0);
    		}*/

    	  showFooter(isFooterFixed(DIVOFFSET,ITEMHEIGHT,data.length));

    		//empty the original content
    		$("#usersView").html("");

    		for(i = 0; i < data.length; i++){
    			itemHTML = generateUserItem(data[i]);
    			//alert(itemHTML);

    			$("#usersView").append(itemHTML);
    		}
    	}

	   var paginationView;
		 var gPageSize = 10;

		 function initPaginationView(requestURL,data,status,type,pKey){
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
													    infoFormat: '{start}-{end}条，共{total}条'
				                     });

				  paginationView.on("pageClicked", function (event, pageNumber) {
														  internalSearch(requestURL,status,type,pageNumber + 1,gPageSize,pKey);
														}).on("jumpClicked", function (event, pageNumber) {
															internalSearch(requestURL,status,type,pageNumber + 1,gPageSize,pKey);
														}).on("pageSizeChanged", function (event, pageSize) {
										           //
										           destoryPaginationView();
										           gPageSize = pageSize;
										           internalSearch(requestURL,status,type,1,pageSize,pKey)
														});
				}
		 }

		 function destoryPaginationView(){
		 	if(paginationView != null){
		 		paginationView.page('destroy');
		 		paginationView = null;
		 	}
		 }

   function generateUserItem(pData){
   	    var email = pData.email;
		    var displayEmail = pData.displayEmail;
    		var userType = pData.userType;
    		var name = pData.name;
			  var displayName = pData.displayName;
    		var identifyInfo = pData.identifyInfo;
    		var uid = pData.id;
    		var identifyStatus = pData.identifyStatus;
    		var identifyCategory = pData.identifyCategory;
    		var createTime = pData.createTime;
   			var lastLoginTime = pData.lastLoginTime;
    		var identifyTime = pData.identifyTime;
    		var mobile = pData.mobile;
			  var remark = pData.remark;
			  var region = pData.region;
    		//var isActive = pData.


    		var itemHTML;
    		var displayStatus = "";
    		var displayType = "";

    		itemHTML = '<div class="row project-item-view" style="height:63px;padding-top:10px;">' +
    		           	'<div class="col-lg-2">' +
    		            '<p>';


    		if(userType == 0){
    			displayType = "雇主";
    		}else if(userType == 1){
    			if(identifyStatus != -1){
    				if(identifyCategory == 0){
    					displayType = "服务商-个人";
    				}else if(identifyCategory == 1){
    					displayType = "服务商-企业";
    				}
    			}else{
    				displayType = "服务商";
    			}
    		}

    		itemHTML += '<span class="badge ';

    		if(userType == 1 || userType == 0){
    				if(identifyStatus == 0 ){
    					displayStatus = "审核中";
    					itemHTML += ' badge-warning">';
    				}else if(identifyStatus == 3){
    					displayStatus = "审核修改";
    					itemHTML += ' badge-warning">';
    				}else if(identifyStatus == 1){
    					displayStatus = "已通过";
    					itemHTML += ' badge-success">';
    				}else if(identifyStatus == 4){
    					displayStatus = "认证修改";
    					itemHTML += ' badge-success">';
    				} if(identifyStatus == 2){
    					displayStatus = "已驳回";
    					itemHTML += ' badge-danger">';
    				}else if(identifyStatus == -1){
	    				displayStatus = "未认证";
	    				itemHTML += ' badge-info">';
    			  }
    		}else{
    			displayStatus = "无认证";
    			itemHTML += ' badge-primary">';
    		}

    		itemHTML += displayStatus + '</span>' + '&nbsp;<strong>' + displayType + '</strong></p></div>' +
    		            '<div class="col-lg-8">';

    		var isEmailExisting = false;

    		if(email != null && email.trim() != ""){
    			isEmailExisting = true;
    			itemHTML += '<div class="wbm-user-eml"><p class="text-default">邮箱:&nbsp;<a href="/api/2/idntify/view?uid=' + uid + '" target="_blank" title="' + email  + '">' + displayEmail + '</a></p></div>';
    		}

    		if(mobile != null && mobile.trim() != ""){
    			if(!isEmailExisting){
    				itemHTML += '<div class="wbm-user-tel"><p class="text-default">手机:&nbsp;<a href="/api/2/idntify/view?uid=' + uid + '" target="_blank">' + mobile + '</a></p></div>';
    			}else{
    				itemHTML += '<div class="wbm-user-tel"><p class="text-default">手机:&nbsp;' + mobile + '</a></p></div>';
    			}
    		}

    		if(name != null && name.trim() != ""){
    			itemHTML += '<div class="wbm-user-names"><p class="text-default" title="' + name + '">昵称:&nbsp;' + displayName + '</p></div>';
    		}

    		if(region != null && region.trim() != ""){
    			itemHTML += '<div class="wbm-user-region"><p class="text-default">区域:&nbsp;' + region + '</p></div>';
    		}

    	/*用户创建时间 */
    		var createTimeObj = new Date();
			  createTimeObj.setTime(createTime);
    		var createTimeStr = createTimeObj.format("yyyy-MM-dd hh:mm");
    		if(createTimeStr != null && createTimeStr.trim() != ""){
    			itemHTML += '<div class="wbm-user-ct"><p class="text-default">创建时间:&nbsp;' + createTimeStr + '</p></div>';
    		}

    	//用户最后登录时间
    	//alert("lastlogintime:" + lastLoginTime);

    	if(lastLoginTime != null){
	    	 	var lastLoginTimeObj = new Date();
	    		lastLoginTimeObj.setTime(lastLoginTime);
	    		var lastLoginTimeStr = lastLoginTimeObj.format("yyyy-MM-dd hh:mm");
	    		if(lastLoginTimeStr !=null && lastLoginTimeStr.trim() != "") {
	    			itemHTML += '<div class="wbm-user-lt"><p class="text-abotrt">最后登录时间:&nbsp;' + lastLoginTimeStr + '</p></div>';
	    		}
    	 }

    		if(identifyTime != null){
    			var identifyTimeObj = new Date();
    			identifyTimeObj.setTime(identifyTime);
    			var identifyTimeStr = identifyTimeObj.format("yyyy-MM-dd hh:mm");
    			itemHTML += '<div class="wbm-user-idtime"><p class="text-default">认证时间:&nbsp;' + identifyTimeStr + '</p></div>';
    		}


    	itemHTML += '</div>'
			//备注
			var remarkButtonName;

			if(remark != null && remark.trim() != ""){
    			remarkButtonName = "查看备注";
    	}else{
				remark = "";
				remarkButtonName = "新增备注";
			}


			itemHTML += '<div class="col-lg-2" style="padding-top:5px;"><a class="btn btn-primary" id="remarkBtn_' + uid + '" data-toggle="modal" href="javascript:void(0);" onclick="javascript:launchRemark(this,'+uid+');">' + remarkButtonName + '</a>';
			itemHTML += '<p id="remark_' + uid + '" style="display:none">' + remark + '</p></div>';

			return itemHTML;
   }

	 var selectedJoinUserId;

	 function launchRemark(el,uId){
		selectedJoinUserId = uId;
		var remark = $("#remark_" + uId).html();

		if(remark != null && remark.trim() != ""){
				remark = remark.replace(/<br\/>|<br>/g,"\n");
				$("#jNote").val(remark);
		}else{
			  $("#jNote").val("");
		}

		el.href="#noteDialog";
	 }

	 function doRemark(){
		  var mURL = "/api/2/member/remark";
		  var remark = $("#jNote").val();

		  var paraData = "uid=" + selectedJoinUserId + "&remark=" + remark;

			$.ajax({
				type:"POST",
				data:paraData,
				url:mURL,
				async:false,
				success:function(data){
					  $("#remark_"+selectedJoinUserId).html(remark);
					  $('#noteDialog').modal('hide');
					  $("#remarkBtn_" + selectedJoinUserId).html("查看备注");
				}
		  });
	 }


     var selectedTypeID;
     var selectedStatusID;
     var selectedTypeVal;
     var selectedStatusVal;

     function initSearchView(){
     	  selectedTypeID = "u_all";
     	  selectedStatusID = "s_all";
     	  selectedTypeVal = "-2";
     	  selectedStatusVal = "-2";

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

     	   	 if(selectedID.indexOf("u_") >= 0){
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
     	 var gURL = "/api/2/u/list";

     	 internalSearch(gURL,selectedStatusVal,selectedTypeVal,1,gPageSize);
     }

     function internalSearch(requestURL,status,type,currentPage,pageSize,pKey){

     	var paraData = "";

     	if(pKey != null){
     		 paraData = "query=" + pKey;
     	}else{
     		 paraData = "status=" + status;

     		 if(type == 1 || type == 2){
	     		 paraData += "&type=1&category=";

	         if(type == 1){
	         	paraData += "0";
	         }else if(type == 2){
	         	paraData += "1";
	         }
	     	}else{
	     		paraData += "&type=" + type;
	     	}
     	}

      if(pageSize != null){
     		paraData += "&pageSize=" + pageSize;
     	}

     	if(currentPage != null){
     			paraData += "&currentPage=" + currentPage;
     	}


     	 $.ajax({
    			type:"POST",
    			url:requestURL,
    			data:paraData,
    			async:false,
    			success:function(data){
					//如果返回的数据非JSON就刷新当前页面
					try{
						//show search result number
						$("#usersNum").html(data.totalRow);
						generateUsersView(data.data);

						//生成分页
						if(paginationView == null){
							initPaginationView(requestURL,data,status,type,pKey);
						}
					}catch(e){
						//location.reload(true);
					}
    			}
    		});
     }

     function doFuzzySearch(){
     	var pKey = $("#pKey").val();

     	if(pKey == null){
     		pKey = "";
     	}

     	var requestURL = "/api/2/u/query";

      destoryPaginationView();

      var userType = $("#h_UserType").val();

      if(userType == -1){
        internalSearch(requestURL,null,null,1,gPageSize,pKey);
      }else{
        internalSearch(requestURL,"1","-2",1,gPageSize,pKey);
      }

     }
    </script>
  </head>
  <body>
    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->

    <!--hidden parameters-->
    <input type="hidden" id="h_UserType" value="${Session.SESSION_LOGIN_USER.userInfoVo.userType}"/>
    <!--end of hidden parameters-->
    <div class="body-offset"></div>
    <div class="container">
     <div class="breadcrumb-container">
			  <ol class="breadcrumb">
				  <li><a href="/">首页</a></li>
           <#if Session.SESSION_LOGIN_USER.userInfoVo.userType = -1>
				  <li><a href="#">用户审核列表</a></li>
          <#elseif Session.SESSION_LOGIN_USER.userInfoVo.userType = 2>
          <li><a href="#">用户查询</a></li>
          </#if>
			  </ol>
		 </div>
   </div>
   <#if Session.SESSION_LOGIN_USER.userInfoVo.userType = -1>
      <div class="container">
      	<div class="row project-search-view">
  	    <div class="search-panel">
  	    	  <label class="search-label">用户类型:</label>
  	    		<button class="btn btn-link search-item" id="u_all" value="-2" onclick="doSearch(this);"/>全部</button>
  	    		<button class="btn btn-link search-item" id="u_publisher" value="0"  onclick="doSearch(this);"/>雇主</button>
            <button class="btn btn-link search-item" id="u_servicer_personal" value="1"  onclick="doSearch(this);"/>服务商-个人</button>
            <button class="btn btn-link search-item" id="u_servicer_company" value="2"  onclick="doSearch(this);"/>服务商-企业</button>
  	    </div>
  	    <hr>
  	  <div class="search-panel">
  		  <label class="search-label">审核状态:</label>
  			<button class="btn btn-link search-item" id="s_all" value="-2"  onclick="doSearch(this);"/>全部</button>
  			<button class="btn btn-link search-item" id="s_authen_nosubmit" value="-1" onclick="doSearch(this);"/>未提交</button>
  			<button class="btn btn-link search-item" id="s_authen_reviewing" value="0" onclick="doSearch(this);"/>待审核</button>
  			<button class="btn btn-link search-item" id="s_authen_passed" value="1" onclick="doSearch(this);"/>认证通过</button>
  			<button class="btn btn-link search-item" id="s_authen_rejected" value="2" onclick="doSearch(this);"/>认证驳回</button>
  		</div>
      </div>
     </div>
   </#if>

    <div class="row">
    <div class="container">
    	<div class="row">
    		<div class="col-lg-4 wbm-search">
		      <div class="input-group">
		       <input type="text" class="form-control" placeholder="关键词" id="pKey">
			      <span class="input-group-btn">
			        <button class="btn btn-default" type="button" onclick="javascript:doFuzzySearch();">搜索</button>
			      </span>
		      </div>
	      </div>
     </div>
     <div class ="row search-result-num-panel">共筛选出<p id="usersNum" class="result-num"></p>条满足条件</div>
</div>
    </div>

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

    <div id="usersView" class="container">
    </div>

 	  <div class="container">
	 	 <div id="paginationView" class="pagination-view-container"></div>
    </div>

	  <!--start of footer-->
	  <#include "../footer.ftl">
	  <!--end of footer-->
	  <!---start of help docker-->
	  <div id="top"></div>
	  <!--end of help docker-->

   <div>
   	<input type="hidden" id="topurl" value="${ctx}/"/>
   </div>
  </body>
</html>
