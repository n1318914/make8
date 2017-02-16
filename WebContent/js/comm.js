/**
 * this is the collection of the common functions used for the whole project
 * created by peton at 2015-10-26
 */
$(function(){
	//获取cookie为空，则直接返回
	var username = getCookieValue("username");
	var pwd = getCookieValue("password");
	var isLogged = $("#isLogged").val();
	if(null == username || null == pwd ||  "" == username || "" == pwd || isLogged == "true"){
		return;
	}
	//自动登陆
	var domain = "";
	var murl = domain + "/api/u/autoLogin";
	$.ajax({
		"dataType": "json",
		"type": "POST",
		"async": "false",
		"url": murl,
		"success": function(data) {
			//alert(JSON.stringify(data));
			//重新登录后不需要跳转，更新header
			if(null == document.getElementById("login_status") || data.resultCode != 0){
				return;
			}
			var url = location.href;
			if(url.match(/.*\/(.*)/)[1] == "login"){
				location.href = "/";
			}
			var displayName = getCookieValue("displayName");
			var username = getCookieValue("username");
			var accountCenter = "";
			if(username){
				accountCenter = "<li onclick=\"javascript:location.href='${ctx}/home/useraccount'\" onmouseover='loginMouseOver(this)' onmouseout='loginMouseOut(this)'>财务中心</li>";
			}
			displayName = decodeURI(displayName);
			document.getElementById("login_status").innerHTML = "<ul class='use'>"
								+"<li onclick='navLogin(this)'>"
								+"<i title='"+displayName+"'>"+displayName+"</i>"
								+"<i class='fa fa-angle-up'></i>"
								+"</li>"
								+"<li onclick=\"javascript:location.href='/home/userinfo'\" onmouseover='loginMouseOver(this)' onmouseout='loginMouseOut(this)'>"
								+"	个人中心"
								+"</li>"
								+ accountCenter
								+"<li onclick='javascript:logout();' onmouseover='loginMouseOver(this)' onmouseout='loginMouseOut(this)'>"
								+"	退出登录"
								+"</li>"
								+"</ul>";
		}
	});
})

function setCookie(cookieName,cookieValue,cookieExpires,cookiePath){
    cookieValue = escape(cookieValue);//编码latin-1

    if(cookieExpires == ""){
        var nowDate = new Date();
        nowDate.setMonth(nowDate.getMonth() + 6);
        cookieExpires = nowDate.toGMTString();
    }

    if(cookiePath != ""){
        cookiePath = ";Path=" + cookiePath;
    }

    document.cookie = cookieName + "=" + cookieValue + ";expires=" + cookieExpires + cookiePath;
}

function getCookieValue(cookieName){
    var cookieValue = document.cookie;
    var cookieStartAt = cookieValue.indexOf("" + cookieName + "=");

    if(cookieStartAt == -1){
        cookieStartAt = cookieValue.indexOf(cookieName + "=");
    }

    if(cookieStartAt == -1){
        cookieValue = null;
    }else{
        cookieStartAt = cookieValue.indexOf("=",cookieStartAt)+1;
        cookieEndAt = cookieValue.indexOf(";",cookieStartAt);

        if(cookieEndAt == -1){
            cookieEndAt = cookieValue.length;
        }

        //cookieValue = unescape(cookieValue.substring(cookieStartAt,cookieEndAt));//解码latin-1
        cookieValue = cookieValue.substring(cookieStartAt,cookieEndAt);
    }

    return cookieValue;
}

function getLoginCookieValue(key){
	return getCookieValue(key);
}

function isLogin(){
	var loginVal = getLoginStatus();

    if(loginVal == "1"){
    	return true;
    }else{
    	return false;
    }
}

function setLoginCookie(key,val){
	var nowDate = new Date();//当前日期
	var cookieExpires = "";
	//nowDate.setMinutes(nowDate.getMinutes() + 720);//将cookie的过期时间设置为之前的某个日期
	//var cookieExpires = nowDate.toGMTString();//过期时间的格式必须是GMT日期的格式

	//alert(cookieExpires);

	setCookie(key,val,cookieExpires,"/");//设置全站cook
}

function setLoginStatus(val){
	setLoginCookie("login",val);
}

function getLoginStatus(){
	return getCookieValue("login");
}

function setLoginUserType(val){
	setLoginCookie("userType",val);
}

function getLoginUserType(){
	return getCookieValue("userType");
}

function setLoginUserName(val){
	setLoginCookie("userName",val);
}

function getLoginUserName(){
	return getCookieValue("userName");
}

function removeLoginCookie(){
	var nowDate = new Date();//当前日期
	nowDate.setMonth(nowDate.getMonth() - 2);//将cookie的过期时间设置为之前的某个日期
	var cookieExpires = nowDate.toGMTString();//过期时间的格式必须是GMT日期的格式

	setCookie("login","0",cookieExpires,"/");//删除一个cookie只要将过期时间设置为过去的一个时间即可
	setCookie("userName","",cookieExpires,"/");
	setCookie("userType","",cookieExpires,"/");
}

function logout(){
	var mURL = "/api/u/logout";

	$.ajax({
	          "type" : "GET",
	          "async" : false,
	          "url" : mURL,
	          "success":function(data){
	          	if(data.resultCode == 0){
		          	location.href = data.msg;
	          	}
	          }
	  });
}

function isMobile(s) {
    //var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
    //支持170开头的手机号
    var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$/;

    if (!patrn.exec(s)) return false;

     return true;
}

function isPhoneNum(phoneNum){
    var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;

    if(isPhone.test(phoneNum)){
        return true;
    }else{
        return false;
    }
}

function isIdCardNo(idCard){
	var result = isIdCardNoInternal(idCard);

	if(result == "验证通过!"){
		return true;
	}else{
		return false;
	}
}


//增加身份证验证
function isIdCardNoInternal(idcard){
    var Errors = new Array("验证通过!", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!", "身份证号码校验错误!", "身份证地区非法!");
    var area = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "xinjiang", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外" }
    var Y, JYM;
    var S, M;
    var idcard_array = new Array();
    idcard_array = idcard.split("");

    if (area[parseInt(idcard.substr(0, 2))] == null) return Errors[4];

    switch (idcard.length) {
        case 15:
            if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
            }
            else {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
            }
            if (ereg.test(idcard))
                return Errors[0];
            else
                return Errors[2];
            break;
        case 18:
            if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
            }
            else {
                ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
            }
            if (ereg.test(idcard)) {
                S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
                Y = S % 11;
                M = "F";
                JYM = "10X98765432";
                M = JYM.substr(Y, 1);
                if (M == idcard_array[17])
                    return Errors[0];
                else
                    return Errors[3];
            }
            else
                return Errors[2];
            break;
        default:
            return Errors[1];
            break;
    }
}


function cleanPastedHTML(input) {
	// 1. remove line breaks / Mso classes
	var stringStripper = /(\n|\r| class=(")?Mso[a-zA-Z]+(")?)/g;
	var output = input.replace(stringStripper, ' ');
	// 2. strip Word generated HTML comments
	var commentSripper = new RegExp('<!--(.*?)-->', 'g');
	var output = output.replace(commentSripper, '');
	var tagStripper = new RegExp('<(/)*(meta|link|span|\\?xml:|st1:|o:|font)(.*?)>', 'gi');
	// 3. remove tags leave content if any
	output = output.replace(tagStripper, '');
	// 4. Remove everything in between and including tags '<style(.)style(.)>'
	var badTags = ['style', 'script', 'applet', 'embed', 'noframes', 'noscript'];
	for (var i = 0; i < badTags.length; i++) {
		tagStripper = new RegExp('<' + badTags[i] + '.*?' + badTags[i] + '(.*?)>', 'gi');
		output = output.replace(tagStripper, '');
	}
	// 5. remove attributes ' style="..."'
	var badAttributes = ['style', 'start'];
	for (var i = 0; i < badAttributes.length; i++) {
		var attributeStripper = new RegExp(' ' + badAttributes[i] + '="(.*?)"', 'gi');
		output = output.replace(attributeStripper, '');
	}
	return output;
}

function converHTML2PlainText(html){
	html = html.replace(/<style([\s\S]*?)<\/style>/gi, '');
	html = html.replace(/<script([\s\S]*?)<\/script>/gi, '');
	html = html.replace(/<\/div>/ig, '\n');
	html = html.replace(/<\/li>/ig, '\n');
	html = html.replace(/<li>/ig, '  *  ');
	html = html.replace(/<\/ul>/ig, '\n');
	html = html.replace(/<\/p>/ig, '\n');
	html = html.replace(/<br\s*[\/]?>/gi, "\n");
	html = html.replace(/<[^>]+>/ig, '');

	return html;
}


function checkSummernoteContent(summernoteText) {
	if (summernoteText == "" || summernoteText == "<br>" || summernoteText == "<p><br></p>") {
		return false;
	} else {
		return true;
	}
}

function initSummernote(){
	//initialize the editor
	$('#summernote').summernote({
	  toolbar: [
	    ['style', ['bold', 'italic', 'underline', 'clear']],
	    ['font', ['strikethrough']],
	    ['fontsize', ['fontsize']],
	  //  ['fontname',['fontname']],
	   // ['color', ['color']],
	    ['para', ['ul', 'ol', 'paragraph']],
	    //['table', ['table']],
	    ['height', ['height']]
	  ],
	  height:150,
	  lang:'zh-CN'
  });
}

function convertPlainText2HTML(pText){
	pText = pText.replace(/\n/g,"<br>");

	return pText;
}

var reqAttachementUploadResult = false;
function initFileIuput(eid,hiddenEl,uploadType,containerId){
	if(eid == null || eid.trim() == ""){
		return;
	}

	var uploadURL = "/uploadify?ext=file&type=";

	if(uploadType == null){
		uploadURL += "1";
	}else{
		uploadURL += uploadType;
	}

    var attachmentFileName = "";

    var elCaptionTxt = '<div class="form-control file-caption kv-fileinput-caption" tabindex="600">'
                       + '<div class="file-caption-name"></div></div>';

	$(eid).fileinput({
        allowedFileExtensions: ["doc", "docx", "pdf","zip"],
        maxFileSize: 5120,
        language:"zh",
        uploadUrl:uploadURL,
        showPreview:false,
        uploadAsync:false,
        showClose:false,
        showUpload:false,
        showCancel:false,
        showRemove:false,
        maxFileCount:1,
        dropZoneEnabled:false,
        elCaptionText:elCaptionTxt,
        elErrorContainer: "#fileUploadErrMsg"
	});

  	$(eid).on('filebatchuploaderror', function(event, data, previewId, index) {
  		reqAttachementUploadResult = false;
  		$("#fileUploadMsg").html("文件“" + attachmentFileName + "” 上传失败");
  		$("#fileUploadMsg").show();
	});

	$(eid).on('filebatchuploadsuccess', function(event, data, previewId, index) {
	    reqAttachementUploadResult = true;

        if(containerId != null && containerId.trim() != ""){
        	$("div#" + containerId + " .file-caption-name").html('<span class="glyphicon glyphicon-file kv-caption-icon"></span>' + attachmentFileName);
        }else{
        	$(".file-caption-name").html('<span class="glyphicon glyphicon-file kv-caption-icon"></span>' + attachmentFileName);
        }

	    $("#fileUploadMsg").html("文件“" + attachmentFileName + "” 上传成功");
	    $("#fileUploadMsg").show();

	    if(hiddenEl != null && hiddenEl.trim() != ""){
	    	$(hiddenEl).hide();
	    }
	});

    $(eid).on('filebatchselected', function(event, files) {
    	attachmentUploadAction = true;
	    $(eid).fileinput('upload');
	    $("#fileUploadMsg").hide();
	});

    $(eid).on('filebatchpreupload', function(event, data, previewId, index) {
      attachmentFileName = data.files[0].name;
    });
}



Date.prototype.format = function(format) {
   var o = {
       "M+": this.getMonth() + 1,
       // month
       "d+": this.getDate(),
       // day
       "h+": this.getHours(),
       // hour
       "m+": this.getMinutes(),
       // minute
       "s+": this.getSeconds(),
       // second
       "q+": Math.floor((this.getMonth() + 3) / 3),
       // quarter
       "S": this.getMilliseconds()
       // millisecond
   };
   if (/(y+)/.test(format) || /(Y+)/.test(format)) {
       format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
   }
   for (var k in o) {
       if (new RegExp("(" + k + ")").test(format)) {
           format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
       }
   }
   return format;
};

function showMessageBox(msg,msgTitle,nextLocation,type,size){

	var innerType = "";
	var innerSize = "";

	if(type != null && type.trim() != ""){
		innerType = "BootstrapDialog." + type;
	}else{
		innerType = "BootstrapDialog.TYPE_PRIMARY";
	}

	if(size != null &&  size.trim() != ""){
		innerSize = "BootstrapDialog." + size;
	}else{
		innerSize = "BootstrapDialog.SIZE_SMALL";
	}

	BootstrapDialog.show(
    {
        title: msgTitle,
        message: msg,
        animate:false,
        draggable:true,
        autodestroy:false,
        closable: false,
        type:innerType,
        size:innerSize,
        closeByBackdrop:false,
        closeByKeyboard:false,
        buttons:[{
        	label:"确定",
        	action: function(dialog) {
        		dialog.close();

        		if(nextLocation != null && nextLocation.trim() != ""){
        			location.href = nextLocation;
        		}

        	}
        }
       ]
   });
}
function showMessageDialog(title,message,callback){
	BootstrapDialog.show({
        title:title,
        message:message,
        animate:false,
        draggable:true,
        autodestroy:false,
        closable: false,
        type:"",
        size:"",
        closeByBackdrop:false,
        closeByKeyboard:false,
        buttons:[{
        	label:"确定",
        	action: function(dialog) {
        		dialog.close();

            if(callback && typeof(callback) === "function"){
              	callback();
            }
        	}
        }]
   });
}
function showConfirmDialog(title,message,callback){
	BootstrapDialog.show({
        title:title,
        message:message,
        animate:false,
        draggable:true,
        autodestroy:false,
        closable: false,
        type:"",
        size:"",
        closeByBackdrop:false,
        closeByKeyboard:false,
        buttons:[{
        	label:"确定",
        	action: function(dialog) {
        		dialog.close();
        		callback();
        	}
        },
        {
        	label:"取消",
        	action: function(dialog) {
        		dialog.close();
        	}
        }]
   });
}

function showConfirmDialogCallback(title,message,callback,cancleCallback){
	BootstrapDialog.show({
        title:title,
        message:message,
        animate:false,
        draggable:true,
        autodestroy:false,
        closable: false,
        type:"",
        size:"",
        closeByBackdrop:false,
        closeByKeyboard:false,
        buttons:[{
        	label:"确定",
        	action: function(dialog) {
        		dialog.close();
        		callback();
        	}
        },
        {
        	label:"取消",
        	action: function(dialog) {
        		dialog.close();
        		cancleCallback();
        	}
        }]
   });
}

function jsonDecode(json,noKey){
	if(json==null)return '/img/default_comp_pic.png'
	var comPicJsonData = eval("("+json.replace(/&quot;/g,"\"")+")");
	var first="";
	for(var gpID=1;gpID<5;gpID++){
		var data = comPicJsonData[gpID];
		if(data!=undefined){
			for(var index=0;index<data.length;index++){
				var src = data[index]['src'];
				if(src!=undefined&&src.trim()!=""){
					first = src;
					if(first.indexOf("img/1/5/")>=0){
						if(first.endsWith('!ABCD')&&noKey){
							first = first.substring(0, first.indexOf('!ABCD'));
						}
						return first;
					}
				}
			}
		}
	}
	first = first.trim()==""?'/img/default_comp_pic.png':first;
	return first;
}
function showRatingScore(eid,topURL,size,score,isReadOnly){
	var ratyImgPath = topURL + "thirdparty/jqueryraty/img";
	var ratyHints = ['很差', '不满', '一般', '满意', '非常满意'];

	if(score == null){
		score = 5;
	}

	if(isReadOnly == null){
		isReadOnly = false;
	}

	var width = 200;
	var supportHalf = true;

	if(size == "default"){
	   $('#' + eid).raty({
			score:score,
			path:ratyImgPath,
			hints: ratyHints,
			half:supportHalf,
			width:width,
			readOnly:isReadOnly
		});

	}else{
		$('#' + eid).raty({
			score:score,
			path:ratyImgPath,
			starHalf : 'star-half-big.png',
			starOff  : 'star-off-big.png',
			starOn   : 'star-on-big.png',
			hints: ratyHints,
			half:supportHalf,
			width:width,
			readOnly:isReadOnly
		});
	}
}


function addDate(bDate, step) {
		var bDateVal = new Date(bDate);
		var lDateVal = bDateVal.valueOf() + step * 24 * 3600 * 1000;
		var lDate = new Date(lDateVal);
		var Year = 0;
		var Month = 0;
		var Day = 0;

		//初始化时间
		//Year= day.getYear();//有火狐下2008年显示108的bug
		Year = lDate.getFullYear(); //ie火狐下都可以
		Month = lDate.getMonth() + 1;
		Day = lDate.getDate();
		//Hour = lDate.getHours();
		// Minute = lDate.getMinutes();
		// Second = lDate.getSeconds();

		var lDateStr = "";
		lDateStr += Year + "-";
		if(Month >= 10) {
			lDateStr += Month + "-";
		}else{
			lDateStr += "0" + Month + "-";
		}

		if (Day >= 10) {
			lDateStr += Day;
		}else{
			lDateStr += "0" + Day;
		}

		return lDateStr;
}

function generateDatePicker(id,containerId,pickerPosition,offset,orgStartDate){

	var startDate;

	if(orgStartDate == null || orgStartDate.trim() == ""){
		startDate = addDate(new Date(),offset);
	}else{
		startDate =  addDate(new Date(orgStartDate),offset);
	}

	$(id).datetimepicker({
		format: 'yyyy-mm-dd',
		language: 'zh-CN',
		//weekStart: 1,
		//todayBtn:  1,
		autoclose: 1,
		todayHighlight: 0,
		//startView: 2,
		minView: 2,
		//forceParse: 0,
		startDate: startDate,
		initialDate: startDate,
		pickerPosition: 'top-left',
		container:"#" + containerId
   });
}

function generateDatePickerWithoutStartDate(id,containerId,dateFormat,startView,minView){
	var dFormat = "yyyy-mm-dd";
	var dStartView = 2;
	var dMinView = 2;

	if(dateFormat != null && dateFormat.trim() != ""){
		dFormat = dateFormat;
	}

	if(startView != null){
		dStartView = startView;
	}

	if(minView != null){
		dMinView = minView;
	}

	$(id).datetimepicker({
		format: dFormat,
		language: 'zh-CN',
		//weekStart: 1,
		//todayBtn:  1,
		autoclose: 1,
		todayHighlight: 0,
		startView: dStartView,
	    minView: dMinView,
		//maxView:3,
		pickerPosition: 'bottom-left',
		container:"#" + containerId
   });
}


function isEmail(email){
　　var Regex = /^(?:\w+\.?)*\w+@(?:\w+\.)*\w+$/;

　　if(Regex.test(email)){
	return true;
　　}else{
	return false;
  }
}

function getClientHeight(){
	return window.screen.availHeight;
}

function isFooterFixed(divOffset,itemHeight,itemsNum){
	var clientHeight = getClientHeight();

	var availableHeight = clientHeight - divOffset;
	var footerHeight = 288;

	if(availableHeight - (itemHeight * itemsNum) > footerHeight){
		return 1;
	}else{
		return 0;
	}
}

function calculateStringLen(pString){
	if(pString == null || pString.trim() == ""){
		return;
	}
	var cNum = 0;

	for(var i = 0; i < pString.length;i++){
		var tmpStr = pString[i];

		if(isChinese(tmpStr)){
			cNum = cNum + 2;
		}else{
			cNum++;
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

function getBrowserType(){
	var explorer = navigator.userAgent ;

	if(explorer.indexOf("MSIE") >= 0){
	  return "IE";
	}else if (explorer.indexOf("Firefox") >= 0) {
	   return "FireFox";
	}else if(explorer.indexOf("Chrome") >= 0){
	   return "Chrome";
	}else if(explorer.indexOf("Opera") >= 0){
       return "Opera";
	}else if(explorer.indexOf("Safari") >= 0){
	   return "Safari";
	}else if(explorer.indexOf("Netscape")>= 0) {
	   return "Netscape";
	}

}
