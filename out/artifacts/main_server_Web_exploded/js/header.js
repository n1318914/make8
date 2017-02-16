$(function(){
	//导航栏
	var $$ = function(str){return document.querySelectorAll(str)}
	for(var z = 0;z<$$("nav ul li a").length;z++)
	{
		$$("nav ul li")[z].onmouseover = function(){
			this.lastChild.style.width="50px";
		}
		$$("nav ul li")[z].onmouseout = function(){
			this.lastChild.style.width="0px";
		}
	}

	for(var i = 0 ;i<$$(".professionalServices ul li").length;i++)
	{
		$$(".professionalServices ul li div")[i].onmouseover = function(){
			this.style.background = "url(http://make8.b0.upaiyun.com/img/v2/merit.png)";
		}
		$$(".professionalServices ul li div")[i].onmouseout = function(){
			this.style.background = "#f4f4f4";
		}
	}

	//获取cookie为空，则直接返回
	var username = getCookieValue("username");
	var pwd = getCookieValue("password");
	if("" == username || "" == pwd){
		return;
	}
})

var LoginButton = false
function navLogin(str){
	if(LoginButton==false)
	{
		var id = str.id;

		if(id == "adminNav" || id == "consultantNav"){
			str.parentNode.style.height = "70px";
		}else if(id == 'mgrMenu'){
			str.parentNode.style.height = "175px";
		}else{
			var h = $(str).parent().children("li").length;
			str.parentNode.style.height = 35*h+"px";
			//str.parentNode.style.height = "106px";
		}

		//str.querySelector("i:last-of-type").innerHTML="﹀";
		$(str).find(".fa").removeClass("fa-angle-up");
		$(str).find(".fa").addClass("fa-angle-down");
		LoginButton=true;
	}else{
		str.parentNode.style.height = "35px";
		//str.querySelector("i:last-of-type").innerHTML="︿";
		$(str).find(".fa").removeClass("fa-angle-down");
		$(str).find(".fa").addClass("fa-angle-up");
		LoginButton=false;
	}
}

function loginMouseOver(str){
	str.style.background="#3191d1";
	str.style.color="white"
}

function loginMouseOut(str){
	str.style.background="white";
	str.style.color="black"
}

var dropDownButton = false;
function dropDownMenu(){
	var nav = $("#nav");
	var navUl = document.querySelectorAll(".nav-ul li a");
	var logo = document.querySelector("nav h1 img");
	var url = location.pathname;
	if(dropDownButton==false)
	{
//		if(document.body.scrollTop>0||document.documentElement.scrollTop>0)
//		{
//			nav.css("height","350px")
//			if(/\/register|\/login/.exec(url)!=null){
//				logo.src="http://make8.b0.upaiyun.com/img/v2/logo_black.png";
//			}
//		}else if(document.body.scrollTop==0||document.documentElement.scrollTop==0){
			logo.src="http://make8.b0.upaiyun.com/img/v2/logo_black.png";
			for(var i = 0;i<navUl.length;i++)
			{
				navUl[i].style.color="black"
			}
			nav.css("background","rgba(255,255,255,0.85)").css("height","380px")
//		}
		dropDownButton = true;
	}else{
//		if(document.body.scrollTop>0||document.documentElement.scrollTop>0)
//		{
//			nav.css("height","80px")
//		}else if(document.documentElement.scrollTop==0||document.body.scrollTop==0){
			if(/\/register|\/login/.exec(url)!=null){
				logo.src="http://make8.b0.upaiyun.com/img/v2/logo.png";
				nav.css("background","rgba(255,255,255,0.2)").css("height","80px")
			}else{
				nav.css("background","rgba(255,255,255,0.85)").css("height","80px")
			}
//
//		}
		dropDownButton = false;
	}

}


function showErWeiMa()
{
	document.querySelector(".erweima").style.display="block"
}

function hideErWeiMa()
{
	document.querySelector(".erweima").style.display="none"
}

function navTop()
{
	var nav = document.getElementById("nav");
	var aIcon = document.querySelectorAll(".nav-ul li a");
	if(document.body.scrollTop>0||document.documentElement.scrollTop>0)
	{
		nav.style.borderBottom="solid 1px #ccc";
		nav.style.background="rgba(255,255,255,0.85)";
		$(nav).css("box-shadow","0 4px 15px 0 rgba(0,0,0,0.1)").css("-webkit-box-shadow","0 4px 15px 0 rgba(0,0,0,0.1)").css("-moz-box-shadow","0 4px 15px 0 rgba(0,0,0,0.1)");
		for(var i = 0;i<aIcon.length;i++)
		{
			aIcon[i].style.color="black";
		}
		nav.querySelector("a>img").src="http://make8.b0.upaiyun.com/img/v2/logo_black.png";
	}else if(document.body.scrollTop==0||document.documentElement.scrollTop==0){
		nav.style.background="none";
		nav.style.borderBottom="none";
		$(nav).css("box-shadow","0 0 0 0");
		for(var i = 0;i<aIcon.length;i++)
		{
			aIcon[i].style.color="white";
		}
		nav.querySelector("a>img").src="http://make8.b0.upaiyun.com/img/v2/logo.png";
	}
}
