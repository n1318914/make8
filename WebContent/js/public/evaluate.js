$(function(){
	//调整最左侧tableTd的高度
	tableSize();
	//调整描述的高度
	reDescribeMargin();
	//调整tab高度
	reTabHeight();
})

var productType = 1;//产品类型
var productScale = 1;//产品规模
function offerResult(){
	var priceMinSum = 0;
	var priceMaxSum = 0;
	var peroidMinSum = 0;
	var peroidMaxSum = 0;
	var productField = "未知";//记录产品的领域
	var price = {
		min:document.querySelectorAll(".datas .priceMin"),
		max:document.querySelectorAll(".datas .priceMax"),
		minLength:document.querySelectorAll(".datas .priceMin").length,
		maxLength:document.querySelectorAll(".datas .priceMax").length
	}
	var peroid = {
		min:document.querySelectorAll(".datas .peroidMin"),
		max:document.querySelectorAll(".datas .peroidMax"),
		minLength:document.querySelectorAll(".datas .peroidMin").length,
		maxLength:document.querySelectorAll(".datas .peroidMax").length
	}
	
	for(var i = 0;i<price.minLength;i++)
	{
		if(productType == 1){
			if(price.min[i].parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.classList.contains("hide"))
			{
				priceMinSum += 0;	
			}else{
				priceMinSum += parseInt(price.min[i].innerHTML);	
			}
		}else{
			if(price.min[i].parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.classList.contains("hide"))
			{
				priceMinSum += 0;
			}else{
				priceMinSum += parseInt(price.min[i].innerHTML)*productType;
			}
		}
	}
	
	for(var y = 0;y<price.maxLength;y++)
	{
		if(productType == 1){
			if(price.max[y].parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.classList.contains("hide"))
			{
				priceMaxSum += 0;
			}else{
				priceMaxSum += parseInt(price.max[y].innerHTML);				
			}
		}else{
			if(price.max[y].parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.classList.contains("hide"))
			{
				priceMaxSum += 0;
			}else{
				priceMaxSum += parseInt(price.max[y].innerHTML)*productType;
			}
		}
	}
	
	for(var z = 0;z<peroid.minLength;z++)
	{
		if(peroid.min[z].parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.classList.contains("hide"))
		{
			peroidMinSum += 0;
		}else{
	  		peroidMinSum += parseFloat(peroid.min[z].innerHTML);
		}
	}
	
	for(var x = 0;x<peroid.maxLength;x++)
	{
		if(peroid.max[x].parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.classList.contains("hide"))
		{
			peroidMaxSum += 0;
		}else{
  			peroidMaxSum += parseFloat(peroid.max[x].innerHTML);
		}
	}
	
	for(var f = 0;f<document.querySelectorAll(".productField .option_div").length;f++)
	{
		if(document.querySelectorAll(".productField .option_div")[f].classList.contains("option_active")==true){
			productField = document.querySelectorAll(".productField .option_div")[f].innerHTML;
		}
	}
	$("#resultProductField").html(productField)
	
	document.querySelectorAll(".defaultResult")[0].style.display = "none";
	document.querySelectorAll(".defaultResult")[1].style.display = "none";
	document.querySelectorAll(".resultSum")[0].style.display = "inline-block";
	document.querySelectorAll(".resultSum")[1].style.display = "inline-block";
	$("#priceMinNum").html(priceMinSum*productScale);
	$("#priceMaxNum").html(priceMaxSum*productScale);
	$("#peroidMinNum").html(peroidMinSum*productScale);
	$("#peroidMaxNum").html(peroidMaxSum*productScale);
	
	$("#resultPriceMin").html(priceMinSum*productScale);
	$("#resultPriceMax").html(priceMaxSum*productScale);
	$("#resultPeroidMin").html(peroidMinSum*productScale);
	$("#resultPeroidMax").html(peroidMaxSum*productScale);
}

var tabDivStatus = false;
function tabDivActive(){
	var tab = document.querySelector(".tab");
		tab.style.marginLeft = "0px";
}

function tabDivHide(){
	var tab = document.querySelector(".tab");
	if(tabDivStatus == true){
		tab.style.marginLeft = "-120px";
	}
}

function reTabHeight(){
	var pageClickHeight = document.documentElement.clientHeight;
	var tabDiv = document.querySelector(".tabDiv");
	var result_height = document.querySelector(".result").offsetHeight;
	var nav_height = document.getElementById("nav").offsetHeight;
	var tabDivHeight = pageClickHeight - result_height - nav_height; 
	tabDiv.style.height = tabDivHeight + "px";
	tabDiv.style.top = nav_height + "px";
	//调整datas的高度
	reDescribeMargin();
}

function reResultPosition(){
	var pageClickHeight = document.documentElement.clientHeight;
	var banner = document.querySelector(".banner").offsetHeight;
	var productController = document.querySelector(".productController").offsetHeight;
	var datas = document.querySelector(".datas").offsetHeight;
	var scrollHeight = document.documentElement.scrollTop + document.body.scrollTop + pageClickHeight;
	var result = document.querySelector(".result");
	var scroll = banner + productController + datas;
	if(scrollHeight > scroll){
		result.style.position = "static";
		result.style.background = "rgba(0,0,0,.68)";
	}else{
	 	result.style.position = "fixed";
		result.style.background = "rgba(0,0,0,.85)";
	}
	//滚动条滑动侧边栏收缩
	tabDivHide();
	//进页面时不隐藏
	tabDivStatus = true;
}

//调整报价结果位置
window.addEventListener("load",reResultPosition)
window.addEventListener("scroll",reResultPosition)
window.addEventListener("resize",reTabHeight)

function checkBoxSelect(str){
	var num = 0;
	var isRadio = str.parentNode.parentNode.parentNode.parentNode.querySelector(".tableTd_title .checkBoxAllBtn");//判断是否为单选
	var tableTd = str.parentNode.parentNode.parentNode.querySelectorAll(".radio img");
	var tableTd_num = str.parentNode.parentNode.parentNode.parentNode.querySelectorAll(".number div");
	var tableTd_num_length = tableTd_num.length;
	var tableTdLength = tableTd.length;
	var itemNum = str.getAttribute("itemId");
	if(str.querySelector("img").classList.contains("active")==false){
		
		if(isRadio.getAttribute("isradio")==1)
		{
			for(var a = 0;a<tableTdLength;a++)
			{
				tableTd[a].classList.remove("active");
				
				tableTd_num[a].querySelector("span:first-of-type").classList.remove("priceMin");
				tableTd_num[a].querySelector("span:nth-of-type(2)").classList.remove("priceMax");
				tableTd_num[a].querySelector("span:nth-of-type(3)").classList.remove("peroidMin");
				tableTd_num[a].querySelector("span:last-of-type").classList.remove("peroidMax");				
			}
		}
		str.querySelector("img").classList.add("active");
		
		for(var y = 0;y<tableTd_num_length;y++)
		{
			if(tableTd_num[y].getAttribute("numberid")==itemNum)
			{
				tableTd_num[y].querySelector("span:first-of-type").classList.add("priceMin");
				tableTd_num[y].querySelector("span:nth-of-type(2)").classList.add("priceMax");
				tableTd_num[y].querySelector("span:nth-of-type(3)").classList.add("peroidMin");
				tableTd_num[y].querySelector("span:last-of-type").classList.add("peroidMax");				
			}
		}
	}else{
		str.querySelector("img").classList.remove("active");
		for(var x = 0;x<tableTd_num_length;x++)
		{
			if(tableTd_num[x].getAttribute("numberid")==itemNum)
			{
				tableTd_num[x].querySelector("span:first-of-type").classList.remove("priceMin");
				tableTd_num[x].querySelector("span:nth-of-type(2)").classList.remove("priceMax");
				tableTd_num[x].querySelector("span:nth-of-type(3)").classList.remove("peroidMin");
				tableTd_num[x].querySelector("span:last-of-type").classList.remove("peroidMax");
			}
		}
	}
	
	//全选按钮样式控制
	for(var i = 0;i<tableTdLength;i++){
		if(tableTd[i].classList.contains("active")==true)
		{
			num++;
		}
	}
	if(num != tableTdLength)
	{
		str.parentNode.parentNode.parentNode.parentNode.querySelector(".selectAll img").classList.remove("active");
	}else{
		str.parentNode.parentNode.parentNode.parentNode.querySelector(".selectAll img").classList.add("active");
	}
	//总价
	offerResult();
}

function checkBoxSelectAll(str){
	//板块下全部选项
	var options = str.parentNode.parentNode.parentNode.querySelectorAll(".tableTd:nth-of-type(2) .radio img");
	var price = str.parentNode.parentNode.parentNode.querySelectorAll(".tableTd:nth-of-type(3) .number");
	var optionsLength = options.length;
	for(var i = 0;i<optionsLength;i++)
	{
		if(str.querySelector("img").classList.contains("active")==false){
			options[i].classList.add("active");
			
			//添加计算标签
			price[i].querySelector("div span:first-of-type").classList.add("priceMin");
			price[i].querySelector("div span:nth-of-type(2)").classList.add("priceMax");
			price[i].querySelector("div span:nth-of-type(3)").classList.add("peroidMin");
			price[i].querySelector("div span:last-of-type").classList.add("peroidMax");
		}else{
			options[i].classList.remove("active");
			
			price[i].querySelector("div span:first-of-type").classList.remove("priceMin");
			price[i].querySelector("div span:nth-of-type(2)").classList.remove("priceMax");
			price[i].querySelector("div span:nth-of-type(3)").classList.remove("peroidMin");
			price[i].querySelector("div span:last-of-type").classList.remove("peroidMax");
		}
	}
	
	if(str.querySelector("img").classList.contains("active")==false){
		str.querySelector("img").classList.add("active");
	}else{
		str.querySelector("img").classList.remove("active");
	}
	//总价
	offerResult();
}

function reDescribeMargin(){
	var numbers = document.querySelectorAll(".number");
	var numbersLength = numbers.length;
	var describes = document.querySelectorAll(".describe");
	var describesLength = describes.length;
	var radios = document.querySelectorAll(".radio");
	var radiosLength = radios.length;
	var window_width = document.documentElement.clientWidth;
	for(var i = 0;i<describesLength;i++){
		if(window_width < 992){
				describes[i].style.margin = "auto";
		}else{
			describes[i].style.margin = "10px auto auto auto";
		}
	}
	for(var j=0;j<radiosLength;j++)
	{
		if(window_width < 768){
		 	if(radios[j].querySelector("div:last-of-type").innerHTML.length > 4){
		 		radios[j].parentNode.classList.remove("radioDiv");
		 		if(radios[j].querySelector("div:last-of-type").innerHTML.length > 8)
		 		{
		 			radios[j].querySelector("div:last-of-type").innerHTML = radios[j].querySelector("div:last-of-type").innerHTML.substr(0,7) + "...";
		 		}
		 	}
		}
	}
	
	if(window_width > 359 && window_width < 768){
		for(var x = 0;x<numbersLength;x++){
			if(numbers[x].innerHTML.length > 110){
				numbers[x].style.margin = "auto";
			}
		}	
	}else if(window_width < 360){
		for(var z = 0;z<numbersLength;z++){
			numbers[z].style.margin = "auto";
		}		
	}
}
	
var oldModuleId = "";//记录添加的id
function selected(str){
	var parentNodes = str.parentNode.parentNode.querySelectorAll(".option_div");
	var parentNodesLength = parentNodes.length;
	var moduleId = str.getAttribute("moduleId");
	var datas = document.querySelectorAll(".datas > div > div");
	var datasLength = datas.length;
	for(var i = 0;i<parentNodesLength;i++){
		parentNodes[i].classList.remove("option_active");
	}
	
	//产品领域显示
	if(moduleId != null){
		for(var i = 0;i<datasLength;i++){
			if(i > 2)
			{
				datas[i].classList.add('hide');
			}
		}
		document.getElementById(moduleId).classList.remove("hide");
		removeTab(oldModuleId)
		addTab(moduleId,str.innerHTML);
		oldModuleId = moduleId;
		tableSize();
	}
	
	if(str.getAttribute("type") != null){
		switch(parseInt(str.getAttribute("type"))){
			case 1:
				productType = 1.3;
				$("#productType").html("Android APP");
				$("#resultProductType").html("Android APP");
			break;
			case 2:
				productType = 1.3;
				$("#productType").html("iOS APP");
				$("#resultProductType").html("iOS APP");
			break;
			case 3:
				productType = 1;
				$("#productType").html("HTML5");
				$("#resultProductType").html("HTML5");
			break;
			case 4:
				productType = 1;
				$("#productType").html("网站");
				$("#resultProductType").html("网站");
			break;
			case 5:
				productType = 1;
				$("#productType").html("微信");
				$("#resultProductType").html("微信");
			break;
		}
	}
	
	if(str.getAttribute("scale") != null){
		switch(parseInt(str.getAttribute("scale"))){
			case 1:
				productScale = 1;
				$("#resultProductScale").html("小型");
			break;
			case 2:
				productScale = 1.5;
				$("#resultProductScale").html("中型");
			break;
			case 3:
				productScale = 2;
				$("#resultProductScale").html("大型");
			break;
		}
	}
	
	if(str.getAttribute("other") == 'false'){
		for(var i = 0;i<datasLength;i++){
			if(i > 2)
			{
				datas[i].classList.remove('hide');
			}
		}
		oldModuleId = "all";
		removeTab(oldModuleId);
		str.getAttribute("other") == 'true';
		addTab(str.getAttribute("other"),str.innerHTML,true,datas);
		tableSize();
	}
	
	str.classList.add("option_active");
	//激活侧边栏
	tabDivActive();
	//重置价格
	offerResult();
}

function addTab(id,str,all,datas){
	if(all != true)
	{
		var tab = document.querySelector(".tab");
		var div = document.createElement("div");
		var mStr = document.getElementById(id);
		div.setAttribute("pagePosition",id);
		div.setAttribute("onclick","tabClickActive(this)");
		div.innerHTML = str;
		tab.insertBefore(div,tab.querySelector("div:last-of-type"));
	}else{
		for(var i = 0;i<datas.length;i++)
		{
			var tab = document.querySelector(".tab");
			var div = document.createElement("div");
			var mStr = document.getElementById(id);
			if(i>2){
				div.setAttribute("pagePosition",datas[i].id);
				div.setAttribute("onclick","tabClickActive(this)");
				div.innerHTML = datas[i].querySelector(".tableTile").innerHTML;
				tab.insertBefore(div,tab.querySelector("div:last-of-type"));
			}
		}
	}
}

function removeTab(id){
	var targetId = document.querySelector(".tab");
	var targetObj = document.querySelectorAll(".tab > div");
	var targetObjLength = targetObj.length;
	if(id != "")
	{
		if(id == "all"){
			for(var i = 0;i<targetObjLength;i++){
				if(i>2){
					if(i == targetObjLength - 1){
						return;
					}
					targetId.removeChild(targetObj[i]);
				}
			}
		}else{
			for(var i = 0;i<targetObjLength;i++){
				if(targetObj[i].getAttribute('pagePosition') == id)
				{
					targetId.removeChild(targetObj[i]);
				}
			}			
		}
	}else{
		return;
	}
}

function tableSize(){
	var tableTrs = document.querySelectorAll(".tableTr > .tableTd:nth-of-type(2)");
	var tableTr_left = document.querySelectorAll(".tableTr > .tableTd:first-of-type");
	var selectAll = document.querySelectorAll(".selectAll");
	var tableLength = tableTrs.length;
	for(var i = 0;i<tableLength;i++)
	{
		var tableTdHeight = 0;
		var tableTr_Tr = tableTrs[i].querySelectorAll(".tableTr");
		var tableTr_tr_Length = tableTr_Tr.length;
		for(var j = 0;j<tableTr_tr_Length;j++){
			tableTdHeight += tableTr_Tr[j].offsetHeight;
		}
		
		tableTr_left[i].style.height = tableTdHeight + "px";

		selectAll[i].style.paddingTop = (tableTr_left[i].offsetHeight - 20)/2 + "px";
	}
}

function pagePosition(id){
	scroll(0,document.getElementById(id).offsetTop + 300);
}

function tabClickActive(str){
	var tabs = document.querySelectorAll(".tab > div");
	var tabLength = tabs.length - 1;
	for(var i = 0;i<tabLength;i++)
	{
		tabs[i].classList.remove("tab_Active");
	}
	pagePosition(str.getAttribute("pagePosition"));
	str.classList.add("tab_Active");
}

function hideResult(str){
	//单选按钮
	var checkBoxBtn = document.querySelectorAll(".checkBoxBtn");
	var checkBoxBtnLength = checkBoxBtn.length;
	if(str=="modify"){
		//隐藏侧边栏、生成报价按钮可选项目移除
		for(var i = 0;i<checkBoxBtnLength;i++)
		{
			checkBoxBtn[i].setAttribute("onclick","checkBoxSelect(this)");
		}
		$(".result").css("display","block");
		$(".tabDiv").css("display","block");
		$(".productController").css("display","block");		
	}else{
		//隐藏侧边栏、生成报价按钮可选项目移除
		for(var j = 0;j<checkBoxBtnLength;j++)
		{
			checkBoxBtn[j].setAttribute("onclick","");
		}
		$(".result").css("display","none");
		$(".tabDiv").css("display","none");
		$(".productController").css("display","none");		
	}
}

//生成报价单
var isCreate = false;
function createQuotation(str){
	var datas = document.querySelectorAll(".datas > div > div");
	var datasLength = datas.length;
	//全选按钮
	var checkBoxAllBtn = document.querySelectorAll(".checkBoxAllBtn");
	var checkBoxAllBtnLength = checkBoxAllBtn.length;
	//单选按钮
	var checkBoxBtn = document.querySelectorAll(".checkBoxBtn");
	var checkBoxBtnLength = checkBoxBtn.length;
	//价格
	var numStr = document.querySelectorAll(".number");
	var numStrLength = numStr.length;
	//描述
	var describe = document.querySelectorAll(".describe");
	var describeLength = describe.length;
	
	if(isCreate == false)
	{
		//多选、单选按钮隐藏判断
		for(var x = 0;x<checkBoxBtnLength;x++)
		{
			if(checkBoxBtn[x].querySelector("img").classList.contains("active")==false){
				var itemid = checkBoxBtn[x].getAttribute("itemid");
				checkBoxBtn[x].parentNode.parentNode.classList.add("resultHide");
				var num = checkBoxBtn[x].parentNode.parentNode.parentNode.parentNode.querySelectorAll(".number");
				var numLength = num.length;
				var remark = checkBoxBtn[x].parentNode.parentNode.parentNode.parentNode.querySelectorAll(".describe");
				var remarkLength = remark.length;
				for(var y = 0;y<numLength;y++)
				{
					if(remark[y].getAttribute("describeId")==itemid)
					{
						remark[y].parentNode.classList.add("resultHide");
					}
					if(num[y].querySelector("div").getAttribute("numberid")==itemid)
					{
						num[y].parentNode.classList.add("resultHide");
					}
				}
			}
		}
		
		//根据单选选择情况判断全选是否显示
		for(var z = 0;z<checkBoxAllBtnLength;z++){
			var checkBoxsBtn = 0;
			for(var a = 0;a<checkBoxAllBtn[z].parentNode.parentNode.parentNode.querySelectorAll(".checkBoxBtn img").length;a++){
				if(checkBoxAllBtn[z].parentNode.parentNode.parentNode.querySelectorAll(".checkBoxBtn img")[a].classList.contains("active")==true)
				{
					checkBoxsBtn ++;
				}
			}
			if(checkBoxsBtn == 0)
			{
				checkBoxAllBtn[z].parentNode.parentNode.parentNode.classList.add("resultHide");
			}
			checkBoxAllBtn[z].classList.add("resultHide")
		}
		
		//根据全选按钮判断隐藏报价板块
		for(var i = 0;i<datasLength;i++)
		{
			var allBtnCount = 0;
			for(var j = 0;j<datas[i].querySelectorAll(".checkBoxBtn img").length;j++)
			{
				if(datas[i].querySelectorAll(".checkBoxBtn img")[j].classList.contains("active")==true)
				{
					allBtnCount ++;
				}
			}
			if(allBtnCount == 0){
				datas[i].classList.add("resultHide");
			}else{
				datas[i].style.borderBottom = "solid 1px #ccc";
			}
		}
		
		similarProject();
		$(".price").slideDown();
		scroll(0,200);
		hideResult("create");
		isCreate = true;
	}else{
		
		var resultHide = document.querySelectorAll(".resultHide");
		var resultHideLength = resultHide.length;
		for(var r = 0;r<resultHideLength;r++){
			resultHide[r].classList.remove("resultHide");
		}
		
		$(".price").slideUp();
		hideResult("modify");
		isCreate = false;
	}
	//重置全选框大小
	tableSize();
}

//类似的项目
function similarProject(){
	var type = $("#resultProductType").html();//类型
	var industry = $("#resultProductField").html();//领域
	var minPrice= $("#resultPriceMin").html();
	var maxPrice= $("#resultPriceMax").html();
	var similarProject = document.querySelector(".similarProject");
	
	if(type != "未定"){
		switch(type){
			case "Android APP":
				type = "app";
			break;
			case "iOS APP":
				type = "app";
			break;
			case "微信":
				type = "weixin";
			break;
			case "网站":
				type = "web";
			break;
			case "HTML5":
				type = "html5";
			break;
		}
	}else{
		type = "";
	}
	
	if(industry == "未知")
	{
		industry = "";
	}

	$.ajax({
		type:"get",
		url:"/api/same?type=" + type + "&industry=" + industry + "&minPrice=" + minPrice + "&maxPrice=" +maxPrice,
		async:false,
		success:function(data){
			similarProject.innerHTML = "<div class='col-md-12 col-sm-12 col-xs-12 similarProject_div'>" +
											"<div class='col-lg-4 col-md-5 col-sm-12 padding col-xs-12 similarProjectImg'>"+
												"<img src='"+ data[0].frontImg +"'/>" +
								    		"</div>" +
								    		"<div class='col-lg-8 col-md-7 col-sm-12 col-xs-12 similarProjectInfo'>" +
												"<div class='col-md-12 col-sm-12 col-xs-12 similarProjectTitle padding text-left'><a target='_blank' href='"+ data[0].link +"'>"+ data[0].name +"</a></div>" +
												"<div class='col-md-12 col-sm-12 col-xs-12 similarProjectContent padding text-left'>"+ data[0].description +"</div>" +
												"<div class='col-md-12 col-sm-12 col-xs-12 similarProjectMessage padding'>" +
													"<div class='col-md-5 col-xs-12 col-sm-5 text-left padding'><span>开发周期：</span><span>"+ data[0].peroid +"</span></div>" +
													"<div class='col-md-7 col-xs-12 col-sm-7 text-left padding'><span>项目金额：</span><span>"+ data[0].descPrice +"</span></div>" +
												"</div>" +
											"</div>" +
										"</div>"
			if (data.length > 1) {
				similarProject.innerHTML += "<div class='col-md-12 col-sm-12 col-xs-12 similarProject_div'>" +
											"<div class='col-lg-4 col-md-5 col-sm-12 padding col-xs-12 similarProjectImg'>"+
												"<img src='"+ data[1].frontImg +"'/>" +
											"</div>" +
											"<div class='col-lg-8 col-md-7 col-sm-12 col-xs-12 similarProjectInfo'>" +
													"<div class='col-md-12 col-sm-12 col-xs-12 similarProjectTitle padding text-left'><a target='_blank' href='"+ data[1].link +"'>"+ data[1].name +"</a></div>" +
													"<div class='col-md-12 col-sm-12 col-xs-12 similarProjectContent padding text-left'>"+ data[1].description +"</div>" +
													"<div class='col-md-12 col-sm-12 col-xs-12 similarProjectMessage padding'>" +
														"<div class='col-md-5 col-xs-12 col-sm-5 text-left padding'><span>开发周期：</span><span>"+ data[1].peroid +"</span></div>" +
														"<div class='col-md-7 col-xs-12 col-sm-7 text-left padding'><span>项目金额：</span><span>"+ data[1].descPrice +"</span></div>" +
												"</div>" +
												"</div>" +
											"</div>"
			}

		}
	});
}

function mobileTile(element){
	var div = document.createElement("div");
	var div_left = document.createElement("div");
	var div_right = document.createElement("div");
	div.classList.add("clickToShow");
	div_left.classList.add("clickToShowLeft");
	div_right.innerHTML = element.getAttribute("title");
	div_right.classList.add("clickToShowRight");
	div.appendChild(div_left);
	div.appendChild(div_right);
	element.insertBefore(div,element.querySelector(".describe"));
}

function mobileTileRemove(element){
	var obj = element.querySelector(".clickToShow");
	obj.remove();
}
