var comPicListData = {};
var picGroup = new Array();
var picData = {};
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
        		callback();
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

/**
 * 表单初始化(编辑)
 */
function fillData(){
	//radio
	var companySize = $(".radio-group").find("#companySize").val();	
	$(".radio-group").find(".radio-item[value="+companySize+"]").trigger("click");
	//checkbox
	var cando = $(".checkbox-group").find("#cando").val().split(",");
	$(".checkbox-group").find("#cando").val("");//清空
	for(var i =0;i<cando.length;i++){
		if(cando[0]=="")$(".checkbox-group").find(".checkbox-item").first().trigger("click");
		else $(".checkbox-group").find(".checkbox-item[value="+cando[i]+"]").trigger("click");	
	}
	//tag
	var mainAbility = $(".tag-group").find("#mainAbility").val().split(",");
	$(".tag-group").find("#mainAbility").val("");//清空
	for(var i=0;i<mainAbility.length;i++){
		if(mainAbility[i]=="")break;
		$(".tag-group").find(".skill-tag[value="+mainAbility[i]+"]").trigger("click");	
	}
	$("#other-skill-input").trigger("keyup");
	//scope tag
	var scope = $(".scope-list").find("#caseType").val().split(",");
	$(".scope-list").find("#caseType").val("");
	for(var i=0;i<scope.length;i++){
		$(".scope-list").find(".scope[value="+scope[i]+"]").trigger("click");	
	}
	$("#other-scope").trigger("keyup");
	//project
	if($("#proj-exprience").attr("count")!=undefined&&parseInt($("#proj-exprience").attr("count"))>0){
		 $(".add-proj-wrapper").hide();
		 $(".add-project-a").show();	
	}
	//piclist
	var comPicList = $("#comPicList").val();
	if(comPicList!=""){		
		comPicList = eval("("+comPicList+")");
		$(".pic-panel").each(function(){
			var groupid = $(this).attr("groupid");
			var picGroup = comPicList[groupid];
			for(var index = 0;index<picGroup.length;index++){
				var src = picGroup[index].src;
				$(this).find(".pic-line-list").append("<div class='pic-line'></div>");
				var newOper = $("#pic-row-operate-model").clone(true,true);			
				newOper.appendTo($(this).find(".pic-line-list"));
				newOper.removeAttr("id");
				$(this).find(".pic-line").last().removeClass("pic-line-model");
				$(this).find(".pic-line").last().fadeIn();			
				$(this).find(".pic-row-operate").last().after("<hr>");			
				$(this).find(".pic-line").last().find(".spinner").css("opacity","1");	
				$(this).find(".pic-line").last().html("<img src='"+src+"'/>");      
		       	$(this).find("pic-line").last().removeClass("edit-line");
			}
		});	
	}
}
/**
 * 表单提交
 */
function modifySubmit(){
	var param = $("#com-info-form").serialize();
	var murl = "/api/2/member/modify";
    //统计图片项
	//遍历组
	$(".pic-panel").each(function(){
		//遍历项
		var groupId = $(this).attr("groupid");
		var index = 0;
		$(this).find(".pic-line").each(function(){
			if($(this).attr("class").indexOf("pic-line-model")>=0)return;		
			if($(this).find("img").attr("src")!=undefined&&$(this).find("img").attr("src").trim()!="")picGroup[index++] = {"src":$(this).find("img").attr("src")};			
		});		
		comPicListData[groupId] = picGroup;
		picGroup = new Array();			
	});				
	var comPicList = JSON.stringify(comPicListData);
	comPicList = comPicList.replace("/\"/g","'");
	$.ajax({
        "dataType" : "json",
        "type" : "POST",
        "async": "false",
        "data" : param+"&comPicList="+comPicList+"&regionId="+$("#city").val(),
        "url" : murl,
        "success" : function(data){
        	var messsage = data.message==undefined?"保存失败,请联系管理员":data.message;
        	showMessageDialog("服务商信息录入",messsage,function(){
            	window.location.reload();
        	});
        }
   });
}
function isImage(fileName){
	if(fileName.trim()=="")return;
	var extStart=fileName.lastIndexOf(".");
	var ext=fileName.substring(extStart,fileName.length).toUpperCase();
	if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){		
		showMessageBox("图片限于png,gif,jpeg,jpg格式","提示");
		return false;
	}
	return true;
} 
function UpladFile(file,pic_template) {
    var fileObj = file.files[0]; // js 获取文件对象

    var FileController = "/uploadify?ext=image&type=6&needPath=1";                    // 接收上传文件的后台地址 

    // FormData 对象

    var form = new FormData();

    form.append("author", "hooyes");                        // 可以增加表单数据

    form.append("file", fileObj);                           // 文件对象
    // XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();

    xhr.open("post", FileController, true);

    xhr.onload = function () {

       // alert("上传完成!");
    };
//          xhr.upload.addEventListener("progress", progressFunction, false);
    xhr.send(form);
    xhr.onreadystatechange = function(){
    	if(xhr.readyState == 4 && xhr.status == 200){    
        var data = xhr.responseText;    
       	pic_template.html("<img src='"+data+"!ABCD'/>");      
       	pic_template.removeClass("edit-line");
	}  
    };
}
function setcustomRadioValidate(container,submitID){
	var radio = $("."+container).find("input[type='radio']");
}
function setcustomValidate(containerSelector,repeatElementSelector,submitSelector){		  
	  $(containerSelector).each(function(){
		  $(this).attr("count",$(this).attr("count")==undefined?0:parseInt($(this).attr("count")));
		  $(this).attr("min",$(this).attr("min")==undefined?1:parseInt($(this).attr("max")));
		  $(this).attr("min",$(this).attr("min")==undefined?1:parseInt($(this).attr("min")));
	  });
	  $(submitSelector).click(function(){
		 if($(this).attr("class").indexOf("disabled-submit")>=0)return;
 		 var iserror = false; 		 
         $(containerSelector).each(function(){
        	 var mincount = parseInt($(this).attr("min")==undefined?1:$(this).attr("min"));
        	 var maxcount = parseInt($(this).attr("max")==undefined?1:$(this).attr("max"));  
        	 var count = parseInt($(this).attr("count")==undefined?0:$(this).attr("count"));
        	 //必输
        	 if(count<mincount){
        		 iserror = true;
        		 $(this).css("border","1px solid red");
        		 window.location.href= '#'+$(this).attr("id");
        		 $(this).after("<span class='custom-error-msg' style='color:red;'>至少添加"+mincount+"个选项</span>");
        	 }else if(count>maxcount){
        		 iserror = true;
        		 $(this).css("border","1px solid red");
        		 window.location.href= '#'+$(this).attr("id");
        		 $(this).after("<span class='custom-error-msg' style='color:red;'>最多添加"+maxcount+"个选项</span>");
        	 }                   	         	
         });
         if($("#city").val()==""||$("#city").val()==undefined){
        	 if(!iserror)$(window).scrollTop(0);
        	 iserror = true;        	
         }
        if(!iserror){
        	showConfirmDialog("提示", "确认提交?", function(){ 
  		  		$("#submit-info").addClass("disabled-submit");
  		  		$(containerSelector).parents("form").submit();
  		  	});        	 
        }
     });
	  $(containerSelector).click(function(){			  		 
		  $(".custom-error-msg").remove();
	      $(this).css("border","1px solid #EEE5DE");
	  });	  
 }
$(document).ready(function(){
	$("input,textarea").focus(function(){
		$("#submit-info").removeClass("disabled-submit");
	});
	$.ajax("/api/label/list",function(data){

	});
	$(".li-menu").click(function(){
		if($(this).attr("class")=='li-menu-selected')return;
		$(".li-menu-selected").addClass("li-menu-unselected");
		$(".li-menu-selected").removeClass("li-menu-selected");
		$(this).addClass("li-menu-selected");
		$(this).removeClass("li-menu-unselected");	   			
		window.location.href = ""+$(this).attr("href");
	});
	
	$(".required > .remark").html("*"); 
	$(".required > .remark").css("color","red");
	$(".required > .remark").css("display","inline-block");
	$(".required > .remark").css("width","1em");
	$(".required").css("margin-left","1em");
	$(".radio-item-checked").find(".dotted").css("opacity","1");			   		
	$(".radio-item").click(function(){
		$(".radio-item-checked").find(".dotted").css("opacity","0");	  
		$(".radio-item-checked").removeClass("radio-item-checked"); 			
		$(this).addClass("radio-item-checked");
		$(this).find(".dotted").css("opacity","1");
		$(this).parents(".radio-group").find(".hidden-input").val($(this).attr("value"));
	});
	$(".sub-panel>label").click(function(){
		$(this).prev("div").trigger("click");
	});
	$(".checkbox-item").click(function(){	
		if($(this).attr("class")!='checkbox-item'){
			if($(".checkbox-item-checked").length==1)return;
			$(this).removeClass("checkbox-item-checked");
			$(this).find("i").hide();
			$(this).parents(".custom-count").attr("count",parseInt($(this).parents(".custom-count").attr("count"))-1);	
			var conscat = $("input[name='cando']").val();
			if(conscat!=undefined&&conscat.trim()!=""){
				var value = $("input[name='cando']").val();
				var prefix = $(this).attr("value");
				var sub = "";
				if(value.indexOf(prefix)==0)sub = value.substring(prefix.length+1);
				else sub = value.substring(0,value.indexOf(prefix)-1)+value.substring(value.indexOf(prefix)+prefix.length);
				$("input[name='cando']").val(sub);
			}
		}
		else {
			$(this).addClass("checkbox-item-checked");
			$(this).find("i").show();					
			var conscat ;
			if($("input[name='cando']").val()==undefined||$("input[name='cando']").val().trim()=="")conscat = $(this).attr("value");
			else conscat = $("input[name='cando']").val()+","+$(this).attr("value");
			$("input[name='cando']").val(conscat);			
			$(this).parents(".custom-count").attr("count",parseInt($(this).parents(".custom-count").attr("count"))+1);
		}
	});
	$(".skill-tag").click(function(){
		if($(this).attr("class")=="skill-tag"){
			$(this).addClass("skill-tag-selected");
			var conscat;
			if($("#mainAbility").val()==undefined||$("#mainAbility").val().trim()=="")conscat = $(this).attr("value");
			else conscat = $("#mainAbility").val()+","+$(this).attr("value");
			$("#mainAbility").val(conscat);
		}
		else {
			if($(".skill-tag-selected").length==1)return;
			$(this).removeClass("skill-tag-selected");
			
			var value = $("#mainAbility").val();
			var prefix = $(this).attr("value");
			var sub = "";
			if(value.indexOf(prefix)==0)sub = value.substring(prefix.length+1);
			else sub = value.substring(0,value.indexOf(prefix)-1)+value.substring(value.indexOf(prefix)+prefix.length);
			$("#mainAbility").val(sub);
		}
		if($("#mainAbility").val()=="")$("#mainAbility").val("0");
	});
	$(".scope").click(function(){
		if($(this).attr("class")=="scope"){
			$(this).addClass("scope-selected");
			if($("#caseType").val()==undefined||$("#caseType").val().trim()=="")conscat = $(this).attr("value");
			else conscat = $("#caseType").val()+","+$(this).attr("value");
			$("#caseType").val(conscat);
		}
		else {
			if($(".scope-selected").length==1)return;
			$(this).removeClass("scope-selected");
			
			var value = $("#caseType").val();
			var prefix = $(this).attr("value");
			var sub = "";
			if(value.indexOf(prefix)==0)sub = value.substring(prefix.length+1);
			else sub = value.substring(0,value.indexOf(prefix)-1)+value.substring(value.indexOf(prefix)+prefix.length);
			$("#caseType").val(sub);
		}
		if($("#caseType").val()=="")$("#caseType").val("0");
	});
	$("#other-skill-input").keyup(function(){
		if($(".tag-modify").length==0){				   			
   			$(".tag-group").append("<div class='skill-tag skill-tag-selected tag-modify'>HTML/CSS</div>");
   			$(".tag-group").append("<input type='hidden' id='otherAbility' name='otherAbility' />");
   			$(".tag-modify").click(function(){
   				if($(".skill-tag-selected").length==1)return;
   				$(this).remove();
   				$("#otherAbility").remove();
   				$("#other-skill-input").val("");
   			});
		}	   			
		$(".tag-modify").html($("#other-skill-input").val());
		$("#otherAbility").val($("#other-skill-input").val());
		if($("#other-skill-input").val()==""){
			$(".tag-modify").remove();
			$("#otherAbility").remove();
		}
	});
	$("#other-skill-input").change(function(){
//		alert(1);
	});
	$("#other-skill-input").blur(function(){$(this).val("");});
	$("#other-scope").keyup(function(){
		if($(".scope-modify").length==0){				   			
   			$(".scope-list").append("<div class='scope scope-selected scope-modify'>HTML/CSS</div>");
   			$(".scope-list").append("<input type='hidden' id='otherCaseType' name='otherCaseType' />");
   			$(".scope-modify").click(function(){
   				if($(".scope-selected").length==1)return;
   				$(this).remove();
   				$("#otherCaseType").remove();
   				$("#other-scope").val("");
   			});
		}	   			
		$(".scope-modify").html($("#other-scope").val().trim());	
		$("#otherCaseType").val($("#other-scope").val().trim());
		if($("#other-scope").val()==""){
			$(".scope-modify").remove();
			$("#otherCaseType").remove();
		}
	});
	$("#other-scope").blur(function(){$(this).val("");});
	$(".btn-file").each(function(){
		var i = $(this).find("i");
   		var input = $(this).find("input").clone(true,true);	   
   		$(this).html("");
   		i.appendTo($(this));
   		$(this).html($(this).html()+"&nbsp;选择");
   		input.appendTo($(this));	
	});
	$(".fileinput-remove").remove();
	$(".fileinput-upload").remove();	
	
	$(".picselect").change(function(){
		if(!isImage($(this).val()))return;
		if($(this).val()=="")return;

		if($(this).attr("class").indexOf("edit-pic")>=0){
			$(this).parents(".content-panel").find(".edit-line").html("");
			var newLoading = $(".loading-model").clone(true,true);
			newLoading.removeClass("loading-model");
			newLoading.appendTo($(this).parents(".content-panel").find(".edit-line"));
			$(this).parents(".content-panel").find(".edit-line").last().find(".spinner").css("opacity","1");	
			$(this).removeClass("edit-pic");
			UpladFile($(this)[0],$(this).parents(".content-panel").find(".edit-line"));
		}else{
			if(parseInt($(this).parents(".content-panel").attr("max"))<$(this).parents(".content-panel").find(".pic-line").length){
				showMessageBox("此栏目最多上传"+$(this).parents(".content-panel").attr("max")+"张图片","提示");
				return;
			}
			$(this).parents(".content-panel").find(".pic-line-list").append("<div class='pic-line'></div>");
			var newOper = $("#pic-row-operate-model").clone(true,true);			
			newOper.appendTo($(this).parents(".content-panel").find(".pic-line-list"));
			newOper.removeAttr("id");
			$(this).parents(".content-panel").find(".pic-line").last().removeClass("pic-line-model");
			$(this).parents(".content-panel").find(".pic-line").last().fadeIn();			
			$(this).parents(".content-panel").find(".pic-row-operate").last().after("<hr>");
			var newLoading = $(".loading-model").clone(true,true);
			newLoading.removeClass("loading-model");
			newLoading.appendTo($(this).parents(".content-panel").find(".pic-line").last());
			$(this).parents(".content-panel").find(".pic-line").last().find(".spinner").css("opacity","1");	
			UpladFile($(this)[0],$(this).parents(".content-panel").find(".pic-line").last());
		}				
	});	
	generateDatePickerWithoutStartDate(".proj-begin-date","projBeginDateDiv");
	generateDatePickerWithoutStartDate(".proj-end-date","projEndDateDiv");
	setcustomValidate(".custom-count",".auto-row","#submit-info");
	
	$(".datetimepicker").addClass("picker-model");
	$(".picker-model").each(function(index,element){
		$(this).appendTo($(".proj-date").eq(index));		
	});
	$(".proj-date").find("input").css("height","auto");
	$(".proj-date").find("input").addClass("custom-input");
	$(".proj-date").find("input").click(function(){
		$(".proj-date").find(".datetimepicker").css("display","none");
	});
	$(".input-group-addon").click(function(){
		var picker = $(this).parent(".proj-date").find(".datetimepicker");
		picker.css("left","0");
		picker.css("top","30px");
	});
	

     var dragging = false;
     var iX, iY;
     var org_pos_x = $(".custom-dialog").css("left");
     var org_pos_y = $(".custom-dialog").css("top");     
     $(".custom-dialog-header").mousedown(function(e) {
         dragging = true;
         var wholeDialog = $(this).parent(".custom-dialog")[0];
         iX = e.clientX - wholeDialog.offsetLeft;
         iY = e.clientY - wholeDialog.offsetTop;
         wholeDialog.setCapture && wholeDialog.setCapture();
         $(this).css("cursor","move");
         return false;
     });
     $(".custom-dialog-header").mouseup(function(){
    	 $(this).css("cursor","default");
     });
     document.onmousemove = function(e) {
         if (dragging) {
         var e = e || window.event;
         var oX = e.clientX - iX;
         var oY = e.clientY - iY;
         $(".custom-dialog").css({"left":oX + "px", "top":oY + "px"});
         return false;
         }
     };
     $(document).mouseup(function(e) {
         dragging = false;       
     });
     $(".add-project,.outer-circle,.add-project-a").click(function(){
    	 $("#project-dialog").modal(true);
    	 $("#project-dialog").attr("active","yes");
    	 $("#project-dialog").css("display","inline-block");
    	 $("#project-dialog").css("left",org_pos_x);
    	 $("#project-dialog").find("input,textarea").val("");
    	 $("#project-dialog-title").html("添加项目经验");
     });
     $(".form-cancel").click(function(){$(".custom-dialog").css("display","none");$("#project-dialog").removeAttr("active")});     
     $(".custom-dialog").find("input,textarea").focus(function(){
    	 $(this).css("border","1px solid #d7d5d5");
    	 $("span[class='error']").remove();
     });
     $(".custom-input").blur(function(){
    	 $(this).parents(".custom-input-wrapper").parent().find("span[class='error']").remove();
    	 $(this).css("border","1px solid #d7d5d5");
		 var limit = -1;
		 if($(this).attr("limit")!=undefined)limit = parseInt($(this).attr("limit"));    		     		 
		 if(limit<0)return;//无限制    		
		 if($(this).val().trim().length==0){
			 $(this).css("border","1px solid red");  
			 if($(this).parents(".custom-input-wrapper").attr("class").indexOf("no-info")<0)$(this).parents(".custom-input-wrapper").after("<span class='error'>这是必填字段</span>");
			 validate = false;
		 }
		 if($(this).val().trim().length>limit){
			 $(this).css("border","1px solid red");  
			 if($(this).parents(".custom-input-wrapper").attr("class").indexOf("no-info")<0)$(this).parents(".custom-input-wrapper").after("<span class='error'>超出"+limit+"字数限制</span>");
			 validate = false;
		 }
     });
     $(".custom-input").change(function(){
    	 $(this).trigger("blur");
     });
     $("#add-project-btn").click(function(){
    	 var validate = true;
    	 var name = null;
    	 var startTime = null;
    	 var endTime = null;
    	 var desc = null;
    	 var link = null;
    	 var index = parseInt($("#proj-exprience").attr("count"));
    	 var rowElement = null;
    	 $("#project-dialog").find("input").each(function(){
    		 $(this).css("border","1px solid #d7d5d5");
    		 var limit = -1;
    		 if($(this).attr("limit")!=undefined)limit = parseInt($(this).attr("limit"));    		     		 
    		 if(limit<0)return;//无限制    		 
    		 if($(this).val().length==0){
    			 $(this).css("border","1px solid red");  
    			 $(".error").remove();
    			 $(this).parents(".custom-dialog-line").after("<span class='error'>这是必填字段</span>");
    			 validate = false;
    		 }
    		 if($(this).val().length>limit){
    			 $(this).css("border","1px solid red");    
    			 validate = false;
    		 }
    	 });
    	 if(!validate)return;
    	 if($(this).attr("class").indexOf("edit-dialog")>=0){
    		 name = $("#projname-input").val();
	    	 startTime = "2015-10-1";
	    	 endTime = "2015-10-1";
	    	 desc = $("#projDesc").val();
	    	 link = $("#projlink-input").val();
	    	 rowElement = $(".proj-row-wrapper > .editing");
    	 }else{    		 
	    	 $(".proj-row-wrapper").append("<hr/>");
	    	 var row = $(".proj-row-model").clone(true,true);
	    	 row.removeClass("proj-row-model");   
	    	 row.appendTo($(".proj-row-wrapper")); 
	    	 name = $("#projname-input").val();
	    	 link = $("#projlink-input").val();
	    	 startTime = "2015-10-1";
	    	 endTime = "2015-10-1";
	    	 desc = $("#projDesc").val();
	    	 rowElement = $(".proj-row-wrapper > .proj-row").last();
	    	 rowElement.attr("active","true");
	    	 rowElement.append("<input type='hidden' class='projectName' name='employeeProjectExperience["+index+"].projectName' value=''/>");
	    	 rowElement.append("<input type='hidden' class='link' name='employeeProjectExperience["+index+"].link' value=''/>");
	    	 rowElement.append("<input type='hidden' class='startTime' name='employeeProjectExperience["+index+"].startTime' value=''/>");
	    	 rowElement.append("<input type='hidden' class='endTime' name='employeeProjectExperience["+index+"].endTime' value=''/>");
	    	 rowElement.append("<input type='hidden' class='description' name='employeeProjectExperience["+index+"].description' value=''/>");	    	
	    	 rowElement.append("<input type='hidden' class='ranking' name='employeeProjectExperience["+index+"].ranking' value='"+(index+1)+"'/>");	
	    	 $("#proj-exprience").attr("count",parseInt($("#proj-exprience").attr("count"))+1);//总数加一
    	 }       	 
    	 rowElement.find(".projectName").val(name);
    	 rowElement.find(".link").val(link);
    	 rowElement.find(".startTime").val(startTime);
    	 rowElement.find(".endTime").val(endTime);
    	 rowElement.find(".description").val(desc);
    	 
    	 rowElement.find(".projname-val").html(name);
    	 rowElement.find(".projdesc-val").html(desc);
    	 rowElement.find(".projlink-val").html(link);
    	 rowElement.css("display","inline-block");
    	 $("#project-dialog").modal('hide');
    	 $(".add-proj-wrapper").hide();
    	 $(".add-project-a").show();
    	 if($("#proj-exprience").attr("count")==undefined||$("#proj-exprience").attr("count").trim()=="")$("#proj-exprience").attr("count","0");
    	 $(".editing").removeClass("editing");
    	 $(this).removeClass("edit-dialog");
    	 $("#project-dialog").removeAttr("active");
     });
     $(".proj-row-edit").click(function(){  
    	 $(this).parents(".proj-row").addClass("editing");
    	 var name = $(this).parents(".proj-row").find(".projname-val").html();
    	 var link = $(this).parents(".proj-row").find(".projlink-val").html();
    	 var date = $(this).parents(".proj-row").find(".projdate-val").html();
    	 var desc = $(this).parents(".proj-row").find(".projdesc-val").html();
    	 $("#add-project-btn").addClass("edit-dialog");
    	 $("#project-dialog-title").html("修改项目经验");
    	 $("#project-dialog").modal(true);
    	 $("#project-dialog").attr("active","yes");
    	 $("#projname-input").val(name);
    	 $("#projlink-input").val(link);
    	 $("#projlink-input").val(link);
//    	 if(date.split("~").length>1){
//        	 $("#projBeginDate").val(date.split("~")[0]);
//        	 $("#projEndDate").val(date.split("~")[1]); 
//    	 }
    	 $("#projDesc").val(desc);
     });
     $(".proj-row-del").click(function(){
    	 $(this).parents(".proj-row").removeAttr("active");
    	 $(".proj-row[active='true']").each(function(index,element){      		
    		var name = $(this).find(".projectName").val();
	    	var startTime ="2015-10-1";
	    	var endTime ="2015-10-1";
	    	var desc = $(this).find(".description").val();
	    	var link =$(this).find(".link").val();
	    	$(this).find("input[type='hidden']").remove();
	    	var rowElement = $(this);
	    	rowElement.append("<input type='hidden' class='projectName' name='employeeProjectExperience["+index+"].projectName' value='"+name+"'/>");
	    	rowElement.append("<input type='hidden' class='link' name='employeeProjectExperience["+index+"].link' value='"+link+"'/>");
	    	rowElement.append("<input type='hidden' class='startTime' name='employeeProjectExperience["+index+"].startTime' value='"+startTime+"'/>");
	    	rowElement.append("<input type='hidden' class='endTime' name='employeeProjectExperience["+index+"].endTime' value='"+endTime+"'/>");
	    	rowElement.append("<input type='hidden' class='description' name='employeeProjectExperience["+index+"].description' value='"+desc+"'/>");	    	
	        rowElement.append("<input type='hidden' class='ranking' name='employeeProjectExperience["+index+"].ranking' value='"+(index+1)+"'/>");	 
    	 });
    	 $(this).parents(".proj-row").prev("hr").remove();
    	 $("#proj-exprience").attr("count",parseInt($("#proj-exprience").attr("count"))-1);
    	 $(this).parents(".proj-row").remove();
     });
     $(".pic-row-edit").click(function(){
    	 $(".pic-line").removeClass("edit-line");
    	 $(this).parents(".pic-row-operate").prev(".pic-line").addClass("edit-line");    	 
    	 $(this).parents(".content-panel").find(".picselect").addClass("edit-pic");
    	 $(this).parents(".content-panel").find(".picselect").trigger("click");
     });
     $(".pic-row-del").click(function(){ 
    	 $(this).parents(".pic-row-operate").prev(".pic-line").remove();
    	 $(this).parents(".pic-row-operate").remove();
     });

	$("#com-info-form").validate({
		errorElement: "span",
		ignore: [],
		errorPlacement: function(error, element) {
			error.insertAfter(element);
			$("#submit-info").removeClass("disabled-submit");
		}
	});
	//初始化表单数据
	fillData();
	
	$("#province").change(function(){
		if($(this).val()==""){
			$("#city").html("");
			$("#city").append("<option value=''>全部</option>");
		}else{
			$.ajax({ url: "/api/region/citys?id="+$(this).val(), success: function(data){
				$("#city").html("");
				$.each(data,function(index,element){
					$("#city").append("<option value='"+element.id+"'>"+element.name+"</option>");
				});
//				$("#city>option").first().attr("selected",true);
		    }});			
		}

	});
});