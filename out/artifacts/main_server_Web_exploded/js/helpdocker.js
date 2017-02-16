$(function(){
	var url = $("#topurl").val();
	var tophtml="<div id=\"izl_rmenu\" class=\"izl-rmenu\" style=\"z-index:2000;\">"+
					"<div><a onclick=\"_MEIQIA._SHOWPANEL()\" class=\"btn btn-feedback\"></a></div>"+
					"<div><a href=\"tencent://Message/?Uin=993330755&websiteName=sc.chinaz.com=&Menu=yes\" class=\"btn btn-qq\"></a></div>"+
					"<div class=\"btn btn-wx\">"+
						"<img class=\"pic\" src=\"" + url + "img/v2/erweima.jpg\"/>" +
					"</div><br/>"+
					"<div class=\"btn btn-phone\">"+
					"<span class=\"phone\" style=\"width:'0px'\">4000-818-530</span>"+
					"</div>" + 
					"<div class=\"btn btn-top\"></div>" +
				    "</div>";
				    
	//alert(tophtml);
	
	function slidePhone(ele,width,time,state){
		var base = {
			//每次增加距离的间隔时间
			time:time,
			//fps
			framerate:25,
			width:width,
			//每次增加的宽度与左移动距离
			ADD:function(){return parseFloat(this.width/this.framerate)}
		}

		if(ele.offsetWidth >= width){
			return;
		}
		
		setTimeout(function(){
			ele.style.display = 'block';
			ele.style.width = ele.offsetWidth + base.ADD() + "px";
			ele.style.left = ele.offsetLeft + -base.ADD() + "px";
			slidePhone(ele,width,base.time)
		},base.time)	
	}
	
	$("#top").html(tophtml);
	$("#izl_rmenu").each(function(){
		$(this).find(".btn-feedback").mouseenter(function(){
			$(this).find(".pic").fadeIn("fast");
		});
		$(this).find(".btn-wx").mouseenter(function(){
			$(this).find(".pic").fadeIn("fast");
		});
		$(this).find(".btn-wx").mouseleave(function(){
			$(this).find(".pic").fadeOut("fast");
		});
		
		$(this).find(".btn-phone").mouseenter(function(){
//			$(this).find(".phone").fadeIn("fast");
			slidePhone(this.querySelector('.phone'),180,40,true);
		});
		$(this).find(".btn-phone").mouseleave(function(){
			$(this).find(".phone").fadeOut("fast").css('width','0px').css('left','15px');
		});
		
		$(this).find(".btn-top").click(function(){
			$("html, body").animate({
				"scroll-top":0
			},"fast");
		});
	});
	var lastRmenuStatus=false;
	$(window).scroll(function(){//bug
		var _top=$(window).scrollTop();
		if(_top>200){
			$("#izl_rmenu").data("expanded",true);
		}else{
			$("#izl_rmenu").data("expanded",false);
		}
		if($("#izl_rmenu").data("expanded")!=lastRmenuStatus){
			lastRmenuStatus=$("#izl_rmenu").data("expanded");
			if(lastRmenuStatus){
				$("#izl_rmenu .btn-top").slideDown();
			}else{
				$("#izl_rmenu .btn-top").slideUp();
			}
		}
	});
});