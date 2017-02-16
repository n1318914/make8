<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>重置密码  - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
  	<#include "../common.ftl">
    <#include "../comm-includes.ftl">
	  ${simple_includes}
	 <link rel="stylesheet" type="text/css" href="${ctx}/css/public/reset.css?v=${version}"/>
   <script type="text/javascript">
	   function refreshVCode(){
    		var timestamp = Date.parse(new Date());
    		var murl = "${ctx}/api/common/captcha" + "?timestamp=" + timestamp;
    		$("#vcode_img").attr("src",murl);
    	}

    	function doModify(){
    		var password = $("#password").val();
    		var confirmPassword = $("#confirmPassword").val();
			  var type = $("#h_type").val();
			  var vcode = $("#vcode").val();
			  var activeCode = $("#active_code").val();

        var murl = "${ctx}/api/u/resetPwd";
        var paraData = "newPassword=" + password + "&confirmPassword=" + confirmPassword + "&code=" + activeCode + "&captcha=" + vcode  + "&type=" + type;

    		$.ajax({
                "dataType" : "json",
                "type" : "POST",
                "data" : paraData,
                "async": false,
                "url" : murl,
                "success" : function(data){
                	if(data.resultCode==1){
											$("#findPwdMessage").html(data.errorMsg);
					            $("#findPwdMessage").show();
					            refreshVCode();
					            $("#vcode").val("");
					        }else{
											window.location.href = data.msg;
											$("#findPwdMessage").hide();
					        }
               }
        });
    	}

		 $(document).ready(function(){
			  $("#findPwdForm").validate({
			  	errorElement:"span",
			  	messages : {
			  		password:{
			  			required:"请输入密码"
			  		},
			  		confirmPassword:{
			  			required: "请输入确认密码",
			  			equalTo : "两次输入的密码不一致"
			  		},
			  		vcode:{
			  			required:"请输入验证码",
			  		}
			  	},
			  	errorPlacement: function(error, element) {
           	  if(element.is("#password")){
           	  	 error.appendTo($("#passwordMsg"));
           	  }else if(element.is("#confirmPassword")){
           	  	 error.appendTo($("#confirmPasswordMsg"));
           	  }else if(element.is("#vcode")){
           	  	 error.appendTo($("#vcodeMsg"));
           	  }
			     }
			  });

			  var type = $("#h_type").val();
			  if(type == 0){
			  	$("#captchaDiv").show();
			  	$("#vcode").attr("placeholder","验证码");
			  }else{
			  	$("#captchaDiv").hide();
			  	$("#vcode").attr("placeholder","手机验证码");
			  }
		});

		function resetMessage(){
			 $("#pwdMessage").html("");
			 $("#pwdMessage").hide();
		}
    </script>
  </head>
  <body>
  	 <div>
  	 	<input type="hidden" id="active_code" value="${vcode}"/>
  	 	<input type="hidden" id="h_type" value="${type}"/>
  	 </div>

  	 <!--start of header-->
		  <#include "../simple-header.ftl">
		 <!--end of header-->

  	 	<div class="col-md-offset-1 col-xs-12 col-md-10 container join-form-marginbottom">
				<form id="findPwdForm" method="post" role="form" action="javascript:doModify()">
		   	<fieldset>
		   		<div class="col-md-offset-4 col-md-4 col-lg-4 join-bg join-overflow">
             	<div class="join-title-margintop">
             		<div class="col-xs-12 col-md-12 join-title text-center">重置密码</div>
             	</div>

             	<div class="col-lg-12 col-md-12 form-group-height join-phone-margintop">
             		<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg">
	             		<div class="col-xs-12 col-lg-12">
	             			<input class="form-input input-color" name="password" id="password" type="password" placeholder="新密码"  minlength="6" required/>
	             		</div>
             		</div>
             		<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="passwordMsg"></span>
             	</div>

             	<div class="col-lg-12 col-md-12 form-group-height">
             		<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg">
	             		<div class="col-xs-12 col-lg-12">
	             			<input class="form-input input-color" name="confirmPassword" id="confirmPassword" type="password" onchange="resetMessage();" placeholder="再次输入密码" required equalTo="#password"/>
	             		</div>
             		</div>
             		<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="confirmPasswordMsg"></span>
             	</div>

              <#if type==1>
               	<div class="col-lg-12 col-md-12 form-group-height">
               		<div class="col-lg-offset-1 col-xs-offset-1 col-xs-10 col-lg-10 form-group form-padding-right input-bg">
  	             		<div class="col-xs-12 col-lg-12">
  	             			<input class="form-input input-color" name="vcode" id="vcode"  required minlength="4" maxlength="4"/>
  	             		</div>
               		</div>
               		<span class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 msgspan" id="vcodeMsg"></span>
               	</div>
              </#if>

		        	<div class="col-lg-offset-1 col-xs-offset-1 col-lg-10 col-xs-10 btn-submit-container">
		        		<div class="btn-height">
		        			<button type="submit" class="btn btn-lg btn-primary btn-block">重置密码</button>
					    		<span class="col-lg-offset-1 col-lg-10 col-xs-10 msgspan" id="findPwdMessage"></span>
		        		</div>
					  	</div>
		   		</div>
        </fieldset>
		  	</form>
		  </div>

     <!--<div class="container">
  	  	<div class="container resetpassword-div">
				  <form id="findPwdForm" method="post" class="form-resetpassword" role="form" action="javascript:doModify()">
				  	<fieldset>
						  <div class="col-lg-12 col-md-12 form-group">
						    <div class="input-group">
	             	  <span class="input-group-addon"><p class="icon form-lock-icon"></p></span>
		        	 		<i class="vertical-divider"></i>
						  		<input class="form-input" name="password" id="password" type="password" placeholder="新密码"  minlength="6" required/>
						  	</div>
						  	<span class="msgspan" id="passwordMsg"></span>
						  </div>

						  <div class="col-lg-12 col-md-12 form-group">
						  	<div class="input-group">
	             	  <span class="input-group-addon"><p class="icon form-lock-icon"></p></span>
		        	 		<i class="vertical-divider"></i>
			            <input class="form-input" name="confirmPassword" id="confirmPassword" type="password" onchange="resetMessage();" placeholder="再次输入密码" required equalTo="#password"/>
						  	</div>
						  	<span class="msgspan" id="confirmPasswordMsg"></span>
							 </div>

							 <div class="col-lg-12 col-md-12 form-group vcode-view" id="vcode-div">
									<div class="col-lg-8 col-md-9 col-xs-8">
										<div class="input-group">
		             	  	<span class="input-group-addon"><p class="icon form-vcode-icon"></p></span>
		        	 				<i class="vertical-divider"></i>
											<input class="form-input-small" name="vcode" id="vcode"  required minlength="4" maxlength="4"/>
										</div>
										<span class="msgspan" id="vcodeMsg"></span>
									</div>
									<div id="captchaDiv" class="col-lg-4 col-md-3 col-xs-4" style="display:none;">
										<div class="captcha">
									  	<img id="vcode_img" src="/api/common/captcha" onclick="refreshVCode();">
									  </div>
									</div>
							</div>
	         </fieldset>

	          <div class="col-lg-12 col-md-12">
					 	 <button type="submit" class="btn btn-lg btn-primary btn-block">重置密码</button>
					 	 <span class="msgspan" id="findPwdMessage">
					 	</div>
				</form>
	  </div>
   </div>-->

   <!--start of footer-->
	 <#include "../simple-footer.ftl">
   <!--end of footer-->

    <!--CNZZ CODE-->
    ${cnzz_html}
    <!--END OF CNZZ CODE-->
  </body>
</html>
