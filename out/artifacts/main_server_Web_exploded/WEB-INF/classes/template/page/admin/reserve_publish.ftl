<!DOCTYPE html>
<html>
	<head>
		<title>发布需求 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
		<#include "../common.ftl">
		<#include "../comm-includes.ftl">
	    ${basic_includes}
        ${tools_includes}
            
		<script type="text/javascript">
		    function initCheck(){
		    	var userType = getLoginUserType();
				if(userType == 1){
					location.href = "/";
				}
		    }
		    
		    initCheck();
		    
			$(document).ready(function() {
				showFooter(0);				
				
				generateDatePicker(".jingbiao_date",1);
				
				$("#publishRequestForm").validate({
					errorElement: "span",
					ignore: [],
					errorPlacement: function(error, element){
						if (element.is("#jingbiaoDate")) {
							error.appendTo($("#jinbiaoMsg"));
						}else if($(element).attr("name") == "projectOwnerContactType"){
							error.appendTo($("#projectOwnerContactTypeMsg"));
						}else {
							error.insertAfter(element);
						}
					}
				});
		
				//initial price range
				initRequestPriceRange();
				
				//init the location
				initDistrictCity(1,1);
				
				//init the project email value
				//$("#projectOwnerEmail").val($("#h_ProjectOwnerMobile").val() + "@163.com");
			});

			function doPublish() {		
				var rId = $("#h_ReservationID").val();
				var type = $("#typeId").find("option:selected").text();
				var priceRange = $("#priceRangeId").find("option:selected").text();
				var pName = $("#projectName").val();
				var period = $("#jiaofuDuration").val();
				var bidEndTime = $("#jingbiaoDate").val() + " 23:59:59";
				var poName = $("#projectOwnerName").val();
				var poContactType = $('input:radio:checked').val();
				var poContactNumber = $("#projectOwnerContactNumber").val();
				var regionID = $("#city option:selected").val();
		        var poMobile = $("#h_ProjectOwnerMobile").val();
				
				var content = $("#reqDesc").val().trim();
				content = encodeURIComponent(content);
				content = encodeURIComponent(content);
				
				
				
				var murl = "/api/2/reserve/publish";
				var paraData = "type=" + type + "&priceRange=" + priceRange + "&name=" + pName + "&content=" + content + "&period=" + period +
				                "&bidEndTime=" + bidEndTime + "&reserveId=" + rId + "&userName=" + poName + "&regionId=" +
				                regionID;
				                
				
				paraData += "&contactType=";
				
				if(isMobilePulished){
					paraData += "1";
				}
				
				if(isEmailPublished){
					paraData += ",2";
				}
				
				if(isQQPublished){
					paraData += ",3";
				}
				
				if(isWeixinPublished){
					paraData += ",4";
				}
				
				if(isMobilePulished){
					paraData += "&contactMobile=" + $("#poContactMobile").val();
				}
				
				if(isEmailPublished){
					paraData += "&contactEmail=" + $("#poContactEmail").val();
				}else{
					paraData += "&contactEmail=" + $("#h_ProjectOwnerMobile").val() + "@163.com";
				}
				
				if(isQQPublished){
					paraData += "&contactQq=" + $("#poContactQQ").val();
				}
				
				if(isWeixinPublished){
					paraData += "&contactWeixin=" + $("#poContactWeixin").val();
				}
				
				//alert(paraData);
				
				$.ajax({
					//"dataType" : "json",
					"type": "POST",
					"data": paraData,
					"async": "false",
					"url": murl,
					"success": function(data) {
						if (data.resultCode == 0) {
							if(data.recommends > 0){
								var recommends = "匹配到<span style='color:red'><strong>" + data.recommends + "</strong></span>个服务商适合开发该项目，已经短信通知前来参与竞标。";
								showMessageBox(recommends,"发布需求",data.msg);
							}else{
								location.href = data.msg;
							}
						} else {
							//alert(data.errorMsg);
							showMessageBox(data.errorMsg,"发布需求");
							//refreshVCode();
						}
					}
				});
			}

			function initRequestPriceRange() {
				var options = ["请选择", "100-1000元", "1000-5000元", "5000-10000元", "10000-30000元", "3万-5万元", "5万-10万", "10万以上", "待商议"];
				initRequestSelectBox(options, "priceRangeId");
			}

			function initRequestSelectBox(options, eid) {
				var oHTML = "";
				$(eid).html("");
				for (var i = 0; i < options.length; i++) {
					if (i == 0) {
						oHTML += '<option value=""';
					} else {
						oHTML += '<option value="' + i + '"';
					}
					oHTML += ">" + options[i] + "</option>";
				}
				$("#" + eid).append(oHTML);
			}
			
		function initDistrictCity(pid,cid){
			if(pid == 0){
				pid = 1;
				cid = 1;
			}
			
			requestURL = "/api/region/list";

			$.ajax({
				type:"GET",
				url:requestURL,
				async:false,
				success:function(data){
					if(data != null){
						$("#province").html("");
						
						for(i=0; i < data.length; i++){
							optionHTML = '<option value="' + data[i].id + '" ';
							
							if(pid != null  && data[i].id == pid){
								optionHTML += 'selected';
							}
							
							optionHTML += '>' + data[i].name + '</option>';
							
							$("#province").append(optionHTML);
						}
						
						retriveCitiesInternal(pid,cid);
					}
				}
			});
		}
		
		function retriveCities(){
			//
			var pOption = $("#province option:selected");
			var pId = pOption.val();
			
			retriveCitiesInternal(pId);
		}
		
		function retriveCitiesInternal(pId,cId){
			
			if(pId != null){
				var requestURL = "/api/region/citys";
				var paraData = "id=" + pId;
				
				$.ajax({
					type:"get",
					url:requestURL,
					data:paraData,
					async:true,
					success:function(data){
						if(data != null){
							$("#city").html("");
							
							for(i=0; i < data.length; i++){
								optionHTML = '<option value="' + data[i].id + '" ';
								
								if(cId != null && cId == data[i].id){
									optionHTML += ' selected';
								}
								
								optionHTML += '>' + data[i].name + '</option>';
								$("#city").append(optionHTML);
							}
					  }
					}
				});
			}
		}
				
	    var isMobilePulished = false;
		var isEmailPublished = false;
		var isQQPublished = false;
		var isWeixinPublished = false;
		
		function choosePOContactType(el){
			var typeVal = $(el).val();
			var isChecked = $(el).prop('checked');
			//alert(isChecked);
			
			if(typeVal == 1){
				showPOContactTypeInternal("poContatctMobileDiv","poContactMobile",isChecked);
				
				if(isChecked){
					isMobilePulished = true;
				}else{
					isMobilePulished = false;
				}
			}else if(typeVal == 2){
				showPOContactTypeInternal("poContatctEmailDiv","poContactEmail",isChecked);
				
			    if(isChecked){
					isEmailPublished = true;
				}else{
					isEmailPublished = false;
				}
			}else if(typeVal == 3){
				showPOContactTypeInternal("poContatctQQDiv","poContactQQ",isChecked);
				
				if(isChecked){
					isQQPublished = true;
				}else{
					isQQPublished = false;
				}
			}else if(typeVal == 4){
				showPOContactTypeInternal("poContatctWeixinDiv","poContactWeixin",isChecked);
				
				if(isChecked){
					isWeixinPublished = true;
				}else{
					isWeixinPublished = false;
				}
			}
		}
		
		function showPOContactTypeInternal(id,inputId,isChecked){
			if(isChecked){
				$("#" + id).show();
				$("#" + inputId).attr("required",true);
			}else{
				$("#" + id).hide();
				$("#" + inputId).attr("required",false);
			}
		}
		</script>
	</head>

	<body>
		<!--hidden parameters-->
		<div>
		 <input type="hidden" id="h_ReservationID" value="${reserve.id}"></input>
		 <input type="hidden" id="h_ProjectOwnerMobile" value="${reserve.contactNumber}"></input>
		</div>
		
		<!--header-->
    <#include "../header.ftl">
    <!--end of header-->
    
		<div class="body-offset"></div>
		<div class="request-banner"></div>
		
        <div class="container">
         <div class="breadcrumb-container-4-request">
			  <ol class="breadcrumb">
				  <li><a href="/">首页</a></li>
				  <li><a href="/admin/reserve_review">预约审核</a></li>
				  <li><a href="#">发布需求</a></li>
			  </ol>
		  </div>
         </div>
         
		<div class="container">
			<div class="col-lg-offset-1 col-lg-10 col-md-10 form">
				<div class="col-lg-offset-1">
					<form class="form-horizontal" id="publishRequestForm" action="javascript:doPublish();" role="form" method="post">
						<fieldset>
							<div class="form-header">
								<h3>填写您的需求</h3></div>

							<div class="form-group form-group-4-input col-lg-12 col-md-12">
								<div>
									<label class="control-label form-label" for="projectName"><i class="form-required">*</i>项目名称(至少输入2个字符)：</label>
								</div>
								<div class="col-lg-11 col-md-11">
									<input type="text" name="projectName" id="projectName" value="" class="form-control form-input" required minlength="2" maxlength="500">
								</div>
							</div>
                            
                            <div class="form-group form-group-4-input col-lg-6 col-md-6">
								<div>
									<label class="control-label form-label" for="typeId"><i class="form-required">*</i>选择您的项目类型：</label>
								</div>
								<div class="col-lg-11 col-md-11">
									<select class="form-control form-select" name="typeId" id="typeId" required>
										<option value="">请选择</option>
										<option value="1">网站</option>
										<option value="2">iOS APP</option>
										<option value="3">Android APP</option>
										<option value="4">微信开发</option>
										<option value="5">HTML5应用</option>
										<option value="6">其它</option>
									</select>
								</div>
							</div>
							
                           <div class="form-group form-group-4-input col-lg-6 col-md-6">
								<div>
									<label class="control-label form-label" for="priceRangeId"><i class="form-required">*</i>您的心理价位大概是：</label>
								</div>
								<div class="col-lg-11 col-md-11">
									<select class="form-control form-select" name="priceRangeId" id="priceRangeId" required>
									</select>
								</div>
							</div>
   
						  <div class="form-group form-group-4-input col-lg-6 col-md-6">
								<div>
									<label class="control-label form-label" for="jingbiaoDate"><i class="form-required">*</i>竞标截止时间：</label>
								</div>

								<div class="col-lg-11 col-md-11">
									<!--<div class="input-group date jingbiao_date" data-date="" data-date-format="yyyy-mm-dd hh:ii" data-link-field="jingbiaoDate" data-link-format="yyyy-mm-dd">-->
									<div class="input-group date jingbiao_date">
										<input class="form-control  form-input-small" id="jingbiaoDate" name="jingbiaoDate" type="text" readonly="true" required="true" />
										<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
										<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
									</div>
									<div id="jinbiaoMsg"></div>
								</div>
							</div>

							<div class="form-group form-group-4-input col-lg-6 col-md-6">
								<div>
									<label class="control-label form-label" for="jiaofuDuration"><i class="form-required">*</i>交付周期（天数）：</lable>
								</div>
								<div class="col-lg-11 col-md-11">
									<input type="text" class="form-control form-input-small" name="jiaofuDuration" id="jiaofuDuration" value="" required digits="true" maxlength="3"></input>
								</div>
							</div>
							
							
							<div class="form-group form-group-4-input col-lg-6 col-md-6">
								<div>
									<label class="control-label form-label" for="projectOwnerName"><i class="form-required">*</i>雇主用户名：</label>
								</div>
								<div class="col-lg-11 col-md-11">
									<input type="text" class="form-control form-input-small" name="projectOwnerName" id="projectOwnerName" value="${reserve.contactsName}" required></input>
								</div>
						    </div>
						    
			               <div class="form-group form-group-4-input col-lg-6 col-md-6">
								<div>
									<label class="control-label form-label" for="projectOwnerLocation"><i class="form-required">*</i>雇主地点：</label>
								</div>
								 <div class="col-lg-5 col-md-5">
						         	 <select class="form-control form-select" name="province" id="province" onchange="retriveCities();" required>
						         	 </select>
						         </div>
						         <div class="col-lg-5 col-md-5">
						         	 <select class="form-control form-select" name="city" id="city" required>
						         	 </select>
						         </div>
						    </div>
						 
						 	<div class="form-group col-lg-12 col-md-12">
								<div>
									<label class="control-label form-label" for="projectOwnerContactType"><i class="form-required">*</i>请选择可以公开的联系方式：</label>
								</div>
								<div class="col-lg-11 col-md-11">
									<label class="control-label form-label"><input type="checkbox" name="projectOwnerContactType" id="projectOwnerContactType_Phone" onclick="javascript:choosePOContactType(this);" value="1" required></input>手机&nbsp;&nbsp;</label>
									<label class="control-label form-label"><input type="checkbox" name="projectOwnerContactType" id="projectOwnerContactType_Email" onclick="javascript:choosePOContactType(this);" value="2" required></input>邮箱&nbsp;&nbsp;</label>
									<label class="control-label form-label"><input type="checkbox" name="projectOwnerContactType" id="projectOwnerContactType_QQ" onclick="javascript:choosePOContactType(this);" value="3" required></input>QQ&nbsp;&nbsp;</label>
									<label class="control-label form-label"><input type="checkbox" name="projectOwnerContactType" id="projectOwnerContactType_Weixin" onclick="javascript:choosePOContactType(this);" value="4" required></input>微信&nbsp;&nbsp;</label>	
									<div id="projectOwnerContactTypeMsg"></div>
								</div>
						 
								 <div class="col-lg-12 col-md-12 form-input-in-one-row">
									<div class="form-group form-group-4-input-xsmall" id="poContatctMobileDiv" style="display:none;">
										<div>
											<label class="control-label form-label form-inner-label text-right">手机：</lable>
										</div>
									     <div>
											  <input type="text" name="poContactMobile" id="poContactMobile" class="form-control form-input-small form-inner-input-small" maxlength="11" minlength="11" isMobilePhoneNumber="true" value="${reserve.contactNumber}"></input>
										  </div>
									</div>
									<div class="form-group form-group-4-input-small" id="poContatctEmailDiv" style="display:none;">
										<div>
											<label class="control-label form-label form-inner-label text-right">邮箱：</lable>
										</div>
									     <div>
										  <input type="email" name="poContactEmail" id="poContactEmail" class="form-control form-input-small form-inner-input"></input>
										 </div>
									</div>
									<div class="form-group form-group-4-input-xsmall" id="poContatctQQDiv" style="display:none;">
										<div>
											<label class="control-label form-label form-inner-label text-right">QQ：</lable>
										</div>
									     <div>
										  <input type="text" name="pContentQQ" id="poContactQQ" class="form-control form-input-small  form-inner-input-small" digits="true"></input>
										 </div>
									</div>
									<div class="form-group form-group-4-input-xsmall" id="poContatctWeixinDiv" style="display:none;">
										<div>
											<label class="control-label form-label form-inner-label text-right">微信：</lable>
										</div>
									     <div>
										  <input type="text" name="poContactWeixin" id="poContactWeixin" class="form-control form-input-small form-inner-input-small" ></input>
										  </div>
									</div>
								</div>
							</div>
							
							<div class="form-group col-lg-12 col-md-12">
								<div>
									<label class="control-label form-label" for="projectDesc"><i class="form-required">*</i>描述您的需求(至少输入20个字符)：</label>
								</div>
								<div class="col-lg-11 col-md-11">
									<textarea class="form-control" rows="10" name="reqDesc" id="reqDesc" minlength="20" maxlength="2000"></textarea>
								</div>
							</div>
							
							<div class="form-group col-lg-12 col-md-12">
								<div>
									<label class="control-label form-label" for="projectDesc">&#12288;上传附件：</label>
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
						</fieldset>

						<div class="col-lg-12 col-md-12 col-xs-12">
							<button type="submit" class="btn btn-lg btn-primary">提交</button>
						</div>

					</form>
				</div>
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
			<input type="hidden" id="reload" value="1" />
		</div>
		<script type="text/javascript">
		    initFileIuput("#fileUpload");
		</script>
		
		 <!--CNZZ CODE-->
	      ${cnzz_html}
	     <!--END OF CNZZ CODE-->
 </body>
</html>