<!DOCTYPE html>
<html>
	<head>
	  <#include "../common.ftl">
	  <#include "../comm-includes.ftl">
	  <title>码客帮 - 互联网软件外包交易平台</title>
	  <meta http-equiv="X-UA-Compatible" content="IE=edge">
	  <meta name="keywords" content="软件外包,项目外包,互联网软件外包,网站外包,安卓APP外包,Andriod APP,iOS APP外包,微信开发外包">
	  <meta name="description" content="码客帮(www.make8.com)是国内第一家互联网软件外包交易平台，专注于帮助互联网早期创业团队找到靠谱的软件外包服务商。">
	  ${mobile_includes}
      <script type="text/javascript" src="${ctx}/js/bootstrap.min.js?v=${version}"></script>
      
      <script type="text/javascript">
        var gURL = "/api/p/list";
        var PAGESIZE = 10;
        var CURRENTPAGE = 1;
        var TOTALPAGES = 1;
        
      	$(document).ready(function(){
      		initSearchView();
      

            //loading data when scrolling window
      		var range = 50; //距下边界长度/单位px
            //var maxnum = 50; //设置加载最多次数
            var num = 1;
            var totalheight = 0;
            var itemHTML = $(".project-item"); //主体元素
            
            $(window).scroll(function(){
        		var srollPos = $(window).scrollTop(); //滚动条距顶部距离(页面超出窗口的高度)
                totalheight = parseFloat($(window).height()) + parseFloat(srollPos);
                
                if(($(document).height() - range) <= totalheight){
	                if(CURRENTPAGE >= 1 && CURRENTPAGE < TOTALPAGES){
	                  ++CURRENTPAGE;
	                  internalSearch(gURL,selectedStatusVal,selectedTypeVal,CURRENTPAGE,PAGESIZE);
	                }
            	}
             });
      	});
      	
      	
	     var selectedTypeID;
	     var selectedStatusID;
	     var selectedTypeVal;
	     var selectedStatusVal;
	     
	     function initSearchView(){
	     	  selectedTypeID = "p_all";
	     	  selectedStatusID = "s_all";
	     	  selectedTypeVal = "";
	     	  selectedStatusVal = "0";
	     	  
	     	  refreshSearchInternalItemBG(selectedTypeID,"","selected");
	     	  refreshSearchInternalItemBG(selectedStatusID,"","selected");
	     	  
	     	  internalSearch(gURL, "0", "", CURRENTPAGE,PAGESIZE);
	     }
	     
	     function refreshSearchInternalItemBG(eID,oldCSSClass,newCSSClass){
	     	 var el = $("#" + eID);
	     	 el.removeClass(oldCSSClass);
	     	 el.addClass(newCSSClass);
	     }
	     
	     function refreshSearchItemBG(selectedID){
	     	   
	     	   if(selectedID != null && selectedID.trim() != ""){
	     	   	
	     	   	 if(selectedID.indexOf("p_") >= 0){
	     	   	 	
	     	   	 	 if(!isOldTypeItemSelected(selectedID)){
	     	   	 	 	 refreshSearchInternalItemBG(selectedID,"","selected");
	     	   	 	 	 refreshSearchInternalItemBG(selectedTypeID,"selected","");
	     	   	 	 	 selectedTypeID = selectedID;
	     	   	 	 	 selectedTypeVal = $("#" + selectedTypeID).attr("value");
	     	   	 	 }
	     	   	 }else if(selectedID.indexOf("s_") >= 0){
	     	   	 	 if(!isOldStatusItemSelected(selectedID)){
	     	   	 	 	 refreshSearchInternalItemBG(selectedID,"","selected");
	     	   	 	 	 refreshSearchInternalItemBG(selectedStatusID,"selected","");
	     	   	 	 	 selectedStatusID = selectedID;
	     	   	 	 	 selectedStatusVal = $("#" + selectedStatusID).attr("value");
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
	     
	     
	     function calculateStringLen(pString){
				if(pString == null || pString.trim() == ""){
					return;
				}
				var cNum = 0;
				
				for(var i = 0; i < pString.length;i++){
					var tmpStr = pString[i];
					
					if(isChinese(tmpStr)){
						cNum++;
					}else{
						cNum += 0.5;
					}
				}
				
				return cNum;
			}
			
			function isChinese(pCharacter){
				if(pCharacter == null || pCharacter.trim() == ""){
					return;
				}
				
				if(escape(pCharacter).indexOf( "%u" ) >= 0){
				 	return true;
				}else{
				 	return false;
				}
				
		 }
	     
	     function generateProjectItem(pData){
	    		var type = pData.type;
	    		var id = pData.id;
	    		var priceRange = pData.priceRange;
	    		var name = pData.name;
	    		var period = pData.period;
	    		var bidEndTime = pData.bidEndTime;
	    		var displayStatus = pData.displayStatus;
	    		var createTime = pData.createTime;
	    		var plocation = pData.region;
	    		var pViews = pData.pviews;
	    		var jingbiaoNums = pData.jingbiaoNums;
	    		
	    		
	    		var abrrIcon = "";
	    		
	    		if(type == "微信开发"){
	    			abrrIcon = "weixin.png";
	    		}else if(type == "HTML5应用"){
	    			abrrIcon = "html5.png";
	    		}else if(type == "iOS APP"){
	    			abrrIcon = "ios.png";
	    		}else if(type == "Android APP"){
	    			abrrIcon = "Android.png";
	    		}else if(type == "网站"){
	    			abrrIcon = "website.png";
	    		}else{
	    			abrrIcon = "other.png";
	    		}
	    		
	    		
	    		var maxNameLength = 11;
	    		var abbrName;
	    		
	    		var nameLength = calculateStringLen(name);
	    		
	    		if(nameLength > maxNameLength){
	    			abbrName = name.substring(0,maxNameLength - 1);
	    			abbrName += "...";
	    		}
	    		
	    		var statusAbbrIcon = "";
	    		
	    		if(displayStatus == "竞标中"){
	    			 statusAbbrIcon = "joining.png";
	    		}else if(displayStatus == "工作中"){
	    			statusAbbrIcon = "working.png";
	    		}else if(displayStatus == "已完成"){
	    			statusAbbrIcon = "completed.png";
	    		}
	    		
	    	
	    	
                	
	    		var itemHTML = '<li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left project-item">' + 
	    		                       '<div class="am-u-sm-3 am-list-thumb">' +
	    		                      	 '<img src="${store_location}/img/mobile/' + abrrIcon + '"></img>' + 
	    		                      	'</div>' + 
	    		                      	'<div class=" am-u-sm-9 am-list-main">' +
	    		                      	"<h3 class=\"am-list-item-hd\"><a class=\"item-name-kit\" data-am-modal=\"{target:'#alertMsgBox'}\" ";
	    		                      	
	    		                      	if(abbrName != null && abbrName.trim() != ""){
	    		                      		itemHTML += ' title="' + name + '">' + abbrName + '</a>';
	    		                      	}else{
	    		                      		itemHTML += '>' + name + '</a>';
	    		                      	}
	    		                      	
	    		                      	
         itemHTML +=  '<a class="project-status-img"><img src="${store_location}/img/mobile/' + statusAbbrIcon +'" style="width:58px;height:28px;"/></a></h3>' +
	                  	'<div class="am-list-item"><span class="am-u-sm-11 item-shows">' +
	                  	'预算:' + priceRange + ' 周期:' + period + "天 地点:" +  plocation  + 
	                  	' 竞标截止:' + bidEndTime + ' 竞标人数:' + jingbiaoNums + 
	                  	'</div>' +
	                  	'</li>';
	                  	
				  return itemHTML;
				  num++;
	     }
	               
	     function generateProjectView(data){
	    		if(data == null){
	    			return;
	    		}
	    		
	    		var itemHTML;
	    		
	    		//empty the original content
	    		//$("#projectView").html("");
	    		
	    		for(i = 0; i < data.length; i++){
	    			itemHTML = generateProjectItem(data[i]);
	    			$("#projectView").append(itemHTML);
	    		}
	    	}
	     
	     function doSearch(el){
	     	 var selectedID = $(el).attr("id");
	     	 refreshSearchItemBG(selectedID);
	     	 //start to search here
	     	 CURRENTPAGE = 1;
	     	 $("#projectView").html("");
	     	 
	     	 internalSearch(gURL,selectedStatusVal,selectedTypeVal,CURRENTPAGE,PAGESIZE);
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
    			type:"POST",
    			url:requestURL,
    			data:paraData,
    			async:false,
    			success:function(data){
					//show search result number	
					TOTALPAGES = data.totalPage;
					generateProjectView(data.data);
    			}
    		});
	    }
   </script>
	</head>

	<body style="background-color:#F6F6F6;">
		<header data-am-widget="header" class="am-header am-header-default">
			<div class="am-header-left am-header-nav">
				<a href="/mobile" class="">
					<i class="am-header-icon am-icon-home"></i>
				</a>
			</div>
			<div class="am-header-title">项目市场</div>
		</header>
		
	    <nav data-am-widget="menu" class="am-menu  am-menu-offcanvas1" data-am-menu-offcanvas>
		    <a href="javascript: void(0)" class="am-menu-toggle">
		        <i class="am-menu-toggle-icon am-icon-bars">
		        </i>
		    </a>
		    
		    <div class="am-offcanvas">
		        <div class="am-offcanvas-bar am-offcanvas-bar-flip">
		            <ul class="am-menu-nav sm-block-grid-1">
		                <li>
		                    <a href="/mobile" class="main-title">首页 </a>
		                </li>
				        <li>
							<a href="/mobile/request">发布需求</a>
						</li>
		                <li class="">
		                    <a href="/mobile/reserve">预约顾问 </a>
		                </li>
		               	<li class="">
							<a href="#">项目市场</a>
						</li>
		                <li class="">
		                    <a href="/mobile/how_to_use">如何使用</a>
		                </li>
		            </ul>
		        </div>
		    </div>
       </nav>

		<div class="am-g search-type-panel">
			<div class="am-u-sm-12">
				<div class="wbm-items">
					<div class="wbm-item-style">
						<div class="am-u-sm-2">
							<span class="item-title1">类型:</span>
						</div>
						<div class="am-u-sm-10 search-type-content">
							<a class="wbm-item-style" id="p_all" value="" href="javascript:void(0);" onclick="javascript:doSearch(this);">全部</a>
							<a class="wbm-item-style" id="p_website" value="网站" href="javascript:void(0);" onclick="javascript:doSearch(this);">网站</a>
							<a class="wbm-item-style" id="p_ios" value="iOS APP" href="javascript:void(0);" onclick="javascript:doSearch(this);">iOS APP</a>
							<a class="wbm-item-style" id="p_andriod" value="Android APP" href="javascript:void(0);" onclick="javascript:doSearch(this);">安卓APP</a>
							<a class="wbm-item-style" id="p_weixin" value="微信开发" href="javascript:void(0);" onclick="javascript:doSearch(this);">微信开发</a>
							<br>
							<a class="wbm-item-style" id="p_html5" value="HTML5应用" href="javascript:void(0);" onclick="javascript:doSearch(this);">HTML5应用</a>
							<a class="wbm-item-style" id="p_other" value="其它" href="javascript:void(0);" onclick="javascript:doSearch(this);">其他</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<div class="am-g search-status-panel">
			<div class="am-u-sm-12">
				<div class="wbm-items">
					<div class="wbm-item-style">
						<div class="am-u-sm-2">
						<span class="item-title1">状态:</span>
						</div>
						<div class="am-u-sm-10">
							<a class="item-style-mc" value="0" id="s_all" href="javascript:void(0);" onclick="javascript:doSearch(this);">全部</a>
							<a class="item-style-mc" value="1" id="s_joining" href="javascript:void(0);" onclick="javascript:doSearch(this);">竞标中</a>
							<a class="item-style-mc" value="2" id="s_working" href="javascript:void(0);" onclick="javascript:doSearch(this);">工作中</a>
							<a class="item-style-mc" value="3" id="s_complete" href="javascript:void(0);" onclick="javascript:doSearch(this);">已完成</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<div data-am-widget="list_news" class="am-list-news am-list-news-default project-view">
			<div class="am-list-news-bd">
				<ul id="projectView" class="am-list">
					<!--缩略图在标题左边-->
	            </ul>
			</div>
		</div>
	

		<div class="am-modal am-modal-alert" tabindex="-1" id="alertMsgBox">
		  <div class="am-modal-dialog">
		    <div class="am-modal-hd">温馨提示</div>
		    <div class="am-modal-bd">
		            如您需要查看详细信息<br>
		            请在PC端登录后查看
		    </div>
		    <div class="am-modal-footer">
		      <span class="am-modal-btn">确定</span>
		    </div>
		  </div>
		</div>
		
		<!--CNZZ CODE-->
		${cnzz_html}
		
		<!--END OF CNZZ CODE-->
</body>
</html>