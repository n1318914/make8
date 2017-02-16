$(document).ready(function(){
	 var url = $("#topurl").val();
	 var isReload = $("#reload").val();

     var loginResult = false;
     var isAdmin = false;
     var userName;
     var userType;
     
     if(isLogin()){
     	loginResult = true;
     	userName = getLoginUserName();
     	userType = getLoginUserType();	
     }else{
     	
     }
     
     var headerHTML = generateHeaderHTML(url,loginResult,userName,userType);
     $("#navDiv").html(headerHTML);
});


function generateHeaderHTML(url,isLogin,userName,userType){
	var headerHTML = '<nav id="mainNav" class="navbar navbar-default navbar-fixed-top">' +
                     '<div class="container">' + 
                     '<div class="col-lg-5 col-md-5">' +
                     '<div class="navbar-header">' +
                     '<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">' +
                     '<span class="sr-only">Toggle navigation</span>' +
                     '<span class="icon-bar"></span>' +
                     '<span class="icon-bar"></span>' +
                     '<span class="icon-bar"></span>' +
                     '</button>' +
                     //'<a class="navbar-brand" href="' + url + '"><img src="' + url + '/img/sd/xmas/ding.gif"></img></a>' +
                     '<a class="navbar-brand" href="' + url + '"></a>' +
                     '</div>' +
                     '</div>' +
                     '<div class="col-lg-offset-1 col-lg-6 col-md-6">' + 
                     '<div class="collapse navbar-collapse page-scroll" id="bs-example-navbar-collapse-1">' +
                     '<ul class="nav navbar-nav navbar-right">';
                     
                     var isAdmin = false;
                     var isPublisher = false;
                     var isServicer = false;
                     
                     if(userType != null){
                       if(userType == "-1"){
                     	isAdmin = true;
                       }else if(userType == "0"){
                       	isPublisher = true;
                       }else if(userType == "1"){
                       	isServicer = true;
                       }
                     }
                     
                     headerHTML += '<li>' +
				                      '<a class="page-scroll" href="' + url + '">首页</a>' +
				                   '</li>';
				     
				    /*if(!isAdmin){
					    headerHTML += '<li>' +
				                      '<a class="page-scroll" target="_blank" href="http://waibaome.mikecrm.com/f.php?t=JPKnGs" style="padding-top:19px;vertical-align:middle;">' +
				                      '获取邀请码<img src="' + url + 'img/hot.gif" style="margin-top:-30px;margin-left:-20px;"/></a>' +
				                      '</li>';
			        }*/
			                      
	
			     	if(isPublisher || !isLogin){
				        headerHTML += '<li>' +
				                      '<a class="page-scroll" href="' + url + 'home/request">发布需求</a>' +
				                      '</li>';
				     }
			     	
			     	headerHTML += '<li>' +
			                      '<a class="page-scroll" href="' + url + 'market">项目市场</a>' +
			                      '</li>';
				                      
				                      
                     if(isAdmin){
                     	headerHTML += '<li>' +
				                      '<a class="page-scroll" href="' + url + 'admin/users_review">用户审核</a>' +
				                      '</li>';
				                      
                     	headerHTML += '<li>' +
				                      '<a class="page-scroll" href="' + url + 'admin/projects_review">项目审核</a>' +
				                      '</li>';
				                      
				        headerHTML += '<li>' +
				                      '<a class="page-scroll" href="' + url + 'admin/reserve_review">预约审核</a>' +
				                      '</li>';
				     }
                     
                     if(!isAdmin){
                    	headerHTML +=  '<li>' +
				                      '<a class="page-scroll" href="' + url + 'public/qa">如何运作</a>' +
				                      '</li>';
				        headerHTML += '<li class="dropdown">' +
				                      '<a class="dropdown-toggle login-nav" data-toggle="dropdown" href="#">关于' +
				                      '<b class="caret"></b>' + 
				                      '</a>' +
				                      '<ul class="dropdown-menu">' +
				                      '<li><a href="' + url + 'about/aboutus">关于我们</a></li>' + 
				                      '<li><a href="' + url + 'about/contactus">联系我们</a></li>' + 				                     
				                      '<li><a href="' + url + 'about/flink">友情链接</a></li>' +
				                      '<li><a href="' + url + 'about/contract">服务协议</a></li>' + 
				                      '</ul>' +
				                      '</li>';
				                      
                    }
			     
			        if(!isLogin){
	                     headerHTML += '<li>' +
	                                   '<a class="page-scroll" style="color:#3498DB;" href="' + url + 'public/login"><i class="fa fa-user" style="color:#3498DB;"></i>&nbsp;登录</a>' +
	                                   '</li>' +
	                                   '<li>' +
	                                   '<a class="page-scroll" style="color:#3498DB;" href="' + url + 'public/register"><i class="fa fa-pencil" style="color:#3498DB;"></i>&nbsp;注册</a>' +
	                                   '</li>';
                    }else{
		              var name = userName;
				                 
			           headerHTML += '<li class="dropdown"><a class="dropdown-toggle login-nav" data-toggle="dropdown" href="#"';
			           
			           var maxLength = 8;
			           
			           if(isServicer){
				           if(isMobile(userName)){
				           	 maxLength = 11;
				           }else if(isEmail(userName)){
				           	 maxLength = 18;
				           }else{
				           	 maxLength = 12;
				           }
			           }else{
			           	   if(isMobile(userName)){
				           	 maxLength = 11;
				           }else if(isEmail(userName)){
				           	 maxLength = 12;
				           }
			           }
			           
			           
			           var pLen = calculateStringLen(name);

					   if(name != null && name.trim() != ""){
					   	  if(pLen > maxLength){
					   	  	name = name.substring(0,maxLength-3);
					   	  	
					   	  	if(!isMobile(name)){
					   	  		name += "..."
					   	  	}
					   	  	
					   	  	headerHTML += 'title="' + userName + '"';
					   	  }
					   	  
					  	 headerHTML += '>' + name;
					   }
					  
					   headerHTML += '<b class="caret"></b></a><ul class="dropdown-menu">';
									 
					  if(!isAdmin){
		                //headerHTML += '<li><a href="' + url + 'home/userinfo.html">我的资料</a></li>';
		                headerHTML += '<li><a href="' + url + 'home/userinfo">我的资料</a></li>';
		                
		                if(isPublisher){
		                	headerHTML += '<li><a href="' + url + 'home/user_p_projects">我发布的项目</a></li>';
		                }
		                
		                if(isServicer){
		                	headerHTML += '<li><a href="' + url + 'home/user_j_projects">我竞标的项目</a></li>';
		                }                 
	                  }
					 
	                  headerHTML += '<li><a onclick="javascript:logout();" href="javascript:void(0);">退出登录</a></li>' + 
	                               '</ul></li>';        
	             }
	                
	                 
	      headerHTML  +=  '</ul></div></div></div></nav>';
	      return headerHTML;
}

    