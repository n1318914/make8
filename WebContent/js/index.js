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

//此处专业服务过渡效果
//	for(var i = 0;i<3;i++)
//	{
//		$$(".professionalServices ul li div")[i].onmouseover = function(){
//			this.querySelector("img:first-of-type").style.top="28px";
//			this.querySelector("img:last-of-type").style.top="60px";
//		}
//		$$(".professionalServices ul li div")[i].onmouseout = function(){
//			this.querySelector("img:first-of-type").style.top="-50px";
//			this.querySelector("img:last-of-type").style.top="-10px";
//		}
//	}
//
//	for(var i = 3;i<7;i++)
//	{
//		$$(".professionalServices ul li div")[i].onmouseover = function(){
//			this.querySelector("img:first-of-type").style.top="28px";
//			this.querySelector("img:last-of-type").style.top="60px";
//		}
//		$$(".professionalServices ul li div")[i].onmouseout = function(){
//			this.querySelector("img:first-of-type").style.top="-50px";
//			this.querySelector("img:last-of-type").style.top="-20px";
//		}
//	}

	showAnalysisNum();

    //init the validator for service reservation
	jQuery.validator.addMethod("isMobilePhoneNumber",function(){
			var mobile = $("#TelPhone").val();
			var chkResult =  isMobile(mobile);
			return chkResult;
	 },"手机号码不正确");

	 //validating the form
	$("#form-reservation").validate({
	 	 errorElement:"span",
	 	 messages:{
	 	 	TelPhone:{
	 	 		required:"请输入手机号"
	 	 	},
	 	 	name:{
	 	 		required:"请输入姓名"
	 	 	},
		 	demand:{
				required:"请输入需求描述"
			}
		 },
	 	 errorPlacement: function(error, element) {
	       	 if(element.is("#name")){
	       	  	 error.appendTo($("#nameWarning"));
	       	  }else if(element.is("#TelPhone")){
	       	  	 error.appendTo($("#telPhoneWarning"));
	       	  }else if(element.is("#demand")){
	       	  	error.appendTo($("#demandWarning"));
	       	  }
		 }
	});
	initialization();

	//news字数限制
//	newsTitle();

	//美恰
	/*var topURL = $("#topurl").val();
	var version = $("#version").val();

	(function(m, ei, q, i, a, j, s) {
		m[a] = m[a] || function() {
			(m[a].a = m[a].a || []).push(arguments)
		};
		j = ei.createElement(q),
		s = ei.getElementsByTagName(q)[0];
		j.async = true;
		j.charset = "UTF-8";
		j.src = i + "?v=" + new Date().getUTCDate();
		s.parentNode.insertBefore(j, s);
	})(window, document, "script", topURL + "thirdparty/meiqia/meiqia.js?v=" + version, "_MEIQIA");
	_MEIQIA("entId", 6145);
	// 在这里开启无按钮模式
	_MEIQIA("withoutBtn", true);*/
})

function initialization(){
	var demand = document.getElementById("demand");
	var TelPhone = document.getElementById("TelPhone");
	var name = document.getElementById("name");
	demand.value="";
	TelPhone.value="";
	name.value=""
}

var LoginButton = false;

function navLogin(str){
	if(LoginButton==false)
	{
		var id = str.id;
		if(id == "adminNav" || id == "consultantNav"){
			str.parentNode.style.height = "70px";
		}else if(id=='mgrMenu'){
			str.parentNode.style.height = "175px";
		}else{
			var h = $(str).parent().children("li").length;
			 str.parentNode.style.height = 35*h+"px";
			//str.parentNode.style.height = "106px";
		}
		$(str).find(".fa").removeClass("fa-angle-up");
		$(str).find(".fa").addClass("fa-angle-down");
		LoginButton=true;
	}else{
		str.parentNode.style.height = "35px";
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
	if(dropDownButton==false)
	{
		if(document.body.scrollTop>0||document.documentElement.scrollTop>0)
		{
			nav.css("height","380px")
		}else if(document.body.scrollTop==0||document.documentElement.scrollTop==0){
			logo.src="https://static.make8.com/img/v2/logo_black.png";
			for(var i = 0;i<navUl.length;i++)
			{
				navUl[i].style.color="black"
			}
			nav.css("background","rgba(255,255,255,0.85)").css("height","380px")
		}
		dropDownButton = true;
	}else{
		if(document.body.scrollTop>0||document.documentElement.scrollTop>0)
		{
			nav.css("height","80px")
		}else if(document.documentElement.scrollTop==0||document.body.scrollTop==0){
			logo.src="https://static.make8.com/img/v2/logo.png";
			for(var i = 0;i<navUl.length;i++)
			{
				navUl[i].style.color="white"
			}
			nav.css("background","transparent").css("height","80px")
		}
		dropDownButton = false;
	}

}

function inputBackground(str){
	str.style.background="white";
}

function inputBlur(str){
	if(str.value=="")
	{
		str.style.background = "white url(../img/v2/top_"+str.id+".png) no-repeat 10px center"
	}
}

//服务优势
var ps_num = 0;
function psSlide(){
	
	var parameter = {
		img:document.querySelectorAll('.ps-img > img')
	}
	if(ps_num<parameter.img.length){
		if(document.querySelector('.professionalServices').offsetTop-400 <= document.body.scrollTop)
		{
			parameter.img[ps_num].style.opacity = '1';
			parameter.img[ps_num].style.transform = 'translate3d(0,0,0)';
			ps_num++;
			setTimeout(function(){	
				if(ps_num == parameter.img.length)
				{
					return
				}
				psSlide();
			},800)
		}
	}
}

function navTop()
{	
	psSlide()
	var nav = document.getElementById("nav");
	var aIcon = document.querySelectorAll(".nav-ul li a");
	if(document.documentElement.scrollTop>0 || window.pageYOffset>0 || document.body.scrollTop>0)
	{
		nav.style.borderBottom="solid 1px #ccc";
		nav.style.background="rgba(255,255,255,0.85)";
		$(nav).css("box-shadow","0 4px 15px 0 rgba(0,0,0,0.1)").css("-webkit-box-shadow","0 4px 15px 0 rgba(0,0,0,0.1)").css("-moz-box-shadow","0 4px 15px 0 rgba(0,0,0,0.1)");
		for(var i = 0;i<aIcon.length;i++)
		{
			aIcon[i].style.color="black";
		}
		nav.querySelector("a>img").src="https://static.make8.com/img/v2/logo_black.png";
	}else if(document.body.scrollTop==0 || window.pageYOffset==0 || document.documentElement.scrollTop==0){
		nav.style.background="none";
		nav.style.borderBottom="none";
		$(nav).css("box-shadow","none");
		for(var i = 0;i<aIcon.length;i++)
		{
			aIcon[i].style.color="white";
		}
		nav.querySelector("a>img").src="https://static.make8.com/img/v2/logo.png";
	}
}

function showAnalysisNum(){
   var developerNum = $("#h_developerNum").val();
	var companyNum = $("#h_companyNum").val();
	var projectNum = $("#h_projectNum").val();

	var options = {
		useEasing : true,
		useGrouping : true,
		separator : ',',
		decimal : '.',
		prefix : '',
		suffix : ''
	};

	var duration = 3;
	var registerNumCP = new CountUp("developerNum", 0, developerNum, 0, duration, options);
	var projectNumCP = new CountUp("companyNum", 0, companyNum, 0, duration, options);
	var totalAmountCP = new CountUp("projectNum", 0, projectNum, 0, duration, options);

		$(window).scroll(function(){
			if($(window).scrollTop() >= 1700){
				registerNumCP.start();
				projectNumCP.start();
				totalAmountCP.start();
			}
		});
}

window.addEventListener("scroll",navTop);

function doServiceReservation(){
	var phoneNumber = $("#TelPhone").val();
	var name = $("#name").val();
	var requestDesc = $("#demand").val();

	var mURL = "/api/wx/request";
	var pData = "contactNumber=" + phoneNumber + "&contactsName=" + name  + "&content=" + requestDesc + "&type=pc";

	$.ajax({
		type:"POST",
		url:mURL,
		async:false,
        data:pData,
        success:function(data){
        	if(data.resultCode == 0){
        		showMessageBox("预约成功，我们的客服会尽快和您取得联系！","预约技术顾问","/");
        	}else{
        		$("#reservationMsg").html(data.errorMsg);
        	}
        }
	});
}

function heSaidBtnMOver(str){
	str.style.color="white";
}

function heSaidBtnMOut(str){
	str.style.color="#aeaeae";
}

//图片轮播
var PCSatrt;
var picId = 0;
function heSaidPic(){
	var li = document.getElementById("he-said-ul").getElementsByTagName("li");
	var liLength = li.length;
	if(picId==liLength)
	{
		picId=0;
	}
	li[picId<0?0:picId].style.left = "-100%";
	li[picId<0?0:picId].style.zIndex = "-1";
	li[(picId+1)==liLength?0:picId+1].style.left = "0px";
	li[(picId+1)==liLength?0:picId+1].style.zIndex = "2";
	li[(picId-1)<0?liLength-1:picId-1].style.left = "100%";
	li[(picId-1)<0?liLength-1:picId-1].style.zIndex = "-1";
	picId++;
}

function lastHeSaidPic(){
	var li = document.getElementById("he-said-ul").getElementsByTagName("li");
	var liLength = li.length;
	if(picId<0)
	{
		picId=liLength-1;
	}
	if(picId==liLength)
	{
		picId=0;
	}
	li[picId].style.left = "100%";
	li[picId].style.zIndex = "-1";
	li[(picId+1)==liLength?0:picId+1].style.left = "-100%";
	li[(picId+1)==liLength?0:picId+1].style.zIndex = "-1";
	li[(picId-1)<0?liLength-1:picId-1].style.left = "0px";
	li[(picId-1)<0?liLength-1:picId-1].style.zIndex = "2";
	picId--;
}

//轮播启动函数
var sildeWaitTime = 10000;
var PCStart = setInterval(heSaidPic,sildeWaitTime);

function heSaidUp(){
	clearInterval(PCStart);
	lastHeSaidPic();
	PCStart = setInterval(heSaidPic,sildeWaitTime);
}

function heSaidDown(){
	clearInterval(PCStart);
	heSaidPic();
	PCStart = setInterval(heSaidPic,sildeWaitTime);
}

//成功案例jshover效果
function successProjectMouseover(str){
	str.querySelector(".successProject-item-div").style.marginTop = "-80px";
}

function successProjectMouseout(str){
	str.querySelector(".successProject-item-div").style.marginTop = "0px";
}

//marquee
var marqueeNum = 0;
function marquee(){
	var heightStr = "24px";
	var lineHeightStr = "24px";

	var marquee = document.querySelector(".marquee");
	var marqueeDiv = document.querySelectorAll(".marqueeDiv");
	var marqueeDivLength = marqueeDiv.length - 1;
	//创建新的div
	var div = document.createElement("div");
	div.setAttribute("class","marqueeDiv");
	div.style.height = "0px";
	div.setAttribute("onclick",marqueeDiv[marqueeDivLength].getAttribute("onclick"));
	div.setAttribute("onmouseover","marqueeMouseOver(this)");
	div.setAttribute("onmouseout","marqueeMouseOut(this)");
	var divFloat_left = document.createElement("div");
	divFloat_left.setAttribute("class","col-md-2 col-xs-4 col-sm-4 padding marqueeTime hidden-xs");
	divFloat_left.innerHTML = marqueeDiv[marqueeDivLength].querySelector("div:first-of-type").innerHTML;
	var divFloat_right = document.createElement("div");
	divFloat_right.innerHTML = marqueeDiv[marqueeDivLength].querySelector("div:last-of-type").innerHTML;
	divFloat_right.setAttribute("class","col-md-10 col-xs-12 col-sm-8 padding marqueeTitle");
	div.appendChild(divFloat_left);
	div.appendChild(divFloat_right);
	marquee.insertBefore(div,marqueeDiv[0]);
	//删除最后一个节点
	marqueeDiv[marqueeDivLength].remove();
	setTimeout(function(){
		div.style.height = heightStr;
		div.style.lineHeight = lineHeightStr;
	},1000)
	//newsTitle字数调整
//	newsTitle();
}

setInterval(marquee,4000)

function marqueeMouseOver(str){
	str.style.color = "#398fcb";
}

function marqueeMouseOut(str){
	str.style.color = "black";
}

//news文字过滤
function newsTitle(){
	var marqueeTitle = document.querySelectorAll(".marqueeTitle");
	var marqueeTitleLength = marqueeTitle.length;
	var window_width = document.documentElement.clientWidth;
	for(var i = 0;i<marqueeTitleLength;i++)
	{
		if(window_width > 1366){
			if(marqueeTitle[i].innerHTML.length > 28)
			{
				marqueeTitle[i].innerHTML = marqueeTitle[i].innerHTML.substr(0,28) + "...";
			}
		}else if(window_width > 1200 && window_width < 1366){
			if(marqueeTitle[i].innerHTML.length > 26)
			{
				marqueeTitle[i].innerHTML = marqueeTitle[i].innerHTML.substr(0,26) + "...";
			}
		}else if(window_width > 1080 && window_width < 1200){
			if(marqueeTitle[i].innerHTML.length > 14)
			{
				marqueeTitle[i].innerHTML = marqueeTitle[i].innerHTML.substr(0,14) + "...";
			}
		}else if(window_width < 360){
			if(marqueeTitle[i].innerHTML.length > 14)
			{
				marqueeTitle[i].innerHTML = marqueeTitle[i].innerHTML.substr(0,5) + "...";
			}
		}else{
			if(marqueeTitle[i].innerHTML.length > 14)
			{
				marqueeTitle[i].innerHTML = marqueeTitle[i].innerHTML.substr(0,6) + "...";
			}
		}
	}
}

//愚人节效果
/*
function kid(){
	var Element = document.querySelector(".headImg");
	Element.style.webkitTransform = "rotate(0deg)";
	Element.style.MozTransform = "rotate(0deg)";
	Element.style.msTransform = "rotate(0deg)";
	Element.style.OTransform = "rotate(0deg)";
	Element.style.transform = "rotate(0deg)";
}
*/
