<!DOCTYPE html>
<html lang="zh-CN">
	<#assign arr=("/img/slider1.jpg,/img/slider2.jpg,/img/slider3.jpg"?split(","))>
	<#assign remark="3.5">
  <head>
    <title>服务商信息 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
	<link href="${ctx}/css/home/usercenter.css?v=${version}" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="${ctx}/js/usercenter.js?v=${version}"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.DB_rotateRollingBanner.min.js?v=${version}" ></script>
  </head>
  <body>
    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->
    
    <div class="body-offset"></div>    
    <!--<div class="container breadcrumb-container">
		  <ol class="breadcrumb">
			  <li><a href="/">首页</a></li>
			  <li><a href="/market">项目市场</a></li>
			  <li><a href="javascript:history.go(-1);">项目详情</a></li>
			  <li><a href="#">服务商信息</a></li>
		  </ol>
   </div>-->
   
    <!--hidden parameters-->
    <div style="display:none">
    	<textarea type="hidden" id="h_introduction">${userInfo.introduction!""}</textarea>
    	<textarea type="hidden" id="h_skill">${userInfo.mainAbility!""}</textarea>
    </div>
    <!---end of hidden parameters transform:rotate(-45deg);-->
	<div class="container">
    	<div class="col-lg-offset-1 col-lg-10 col-md-10 nav-list">	
			  <ol class="breadcrumb">
				  <li><a href="/">首页</a></li>
				  <li><a href="/public/comp_list">服务商列表</a></li>
				  <li><a href="#">服务商信息</a></li>
			  </ol>
		  </div>
    </div>
  	<div class="container info-container">
	  	<div class="col-lg-offset-1 col-lg-10 col-md-10 form useritem-top">	
	  		<!--sub title-->		   					       
        	<div class='comp-name'>
        		<#if userInfo.identifyInfo.category==1>        	
	        		<#if userInfo.identifyInfo??&&userInfo.identifyInfo.companyName??>
	        			${userInfo.identifyInfo.companyName}
	        			<#else>
	        			某公司
	        		</#if>
	        		<#else>
	        		${userInfo.name!"姓名"}
        		</#if>
        	</div>
        	<div class='info-wrapper'>
        		<#if userInfo.identifyInfo.category==1>
	        		<i class='fa fa-user fa-1x'>
		        		<#if userInfo.companySize??>
		        			<#switch userInfo.companySize>
								<#case '1'>小于10人<#break>
								<#case '2'>10-30人<#break>
								<#case '3'>31-100人<#break>
								<#case '4'>100人以上<#break>
								<#default>小于10人
							</#switch>
							<#else>
								小于10人
	        			</#if>        			
	        		</i>
	        		<i class='fa fa-map-marker fa-1x'>
	        			<#if userInfo.identifyInfo??&&userInfo.identifyInfo.companyAddr??&&userInfo.identifyInfo.companyAddr?trim!="">
	        				${userInfo.identifyInfo.companyAddr}
	        				<#else>
	        				暂无地址
	        			</#if>
	        		</i>
	        		<i class='fa fa-link fa-1x' style='cursor:pointer;' onclick='window.open("${userInfo.identifyInfo.siteLink!"#"}")'>
	        			<#if userInfo.identifyInfo??&&userInfo.identifyInfo.siteLink??&&userInfo.identifyInfo.siteLink?trim!="">
	        				${userInfo.identifyInfo.siteLink}  
	        				<#else>
	        				暂无网址  				        			
	        			</#if>
	        		</i>
	        		<#else>
	        		<i class='fa fa-desktop fa-1x'>&nbsp;<#if userInfo.freelanceType==0>兼职<#elseif userInfo.freelanceType==1>自由职业者<#elseif userInfo.freelanceType==2>在校学生</#if></i>
	        		<i class='fa fa-envelope-o fa-1x'>&nbsp;${userInfo.email!"暂无邮箱"}</i>
	        		<i class='fa fa-tablet fa-1x'>&nbsp;${userInfo.mobile!"暂无手机"}</i>
	        		<i class='fa fa-map-marker fa-1x'>&nbsp;${userInfo.region!"暂无地址"}</i>
        		</#if>
        	</div>
        	<#if userInfo.identifyInfo.category==1>
	  		<!--sub title-->		   					       
        	<div class='sub-title'>
        		<div class='sub-title-name'>公司介绍</div><hr>
        	</div>
        	<div class='sub-info'>
        		<p class='introduction'>
        			<#if userInfo.introduction??>
        				${userInfo.introduction}
        				<#else>
        				暂无介绍
        			</#if>
        		</p>
        		<div class='com-slider-box'>        			
        			<div class='slide-box-grp'>
	        			<img class='slider-box-pic' src=''/>
	        			<JSONDATA value='${userInfo.comPicList!"[]"}'> 							        		
					</div>   
					<script>						
						var display=1;
						var comPicJsonData = eval("("+$('JSONDATA').attr('value')+")");		
						var first="/img/loading.gif";
						$(".slider-box-pic").first().addClass("loading");				
						for(var gpID=1;gpID<5;gpID++){							
							var data = comPicJsonData[gpID];					
							if(data!=undefined){									
								for(var index=0;index<data.length;index++){
									var src = data[index]['src'];
									if(src==undefined||src.trim()=="")src = "/img/default_comp_pic.png";	
									if(src.indexOf("img/1/5/")>=0&&src.endsWith("!ABCD")){
										src = src.substring(0, src.indexOf('!ABCD'));
									}
									$(".slide-box-grp").append("<input type='hidden' class='stack_slider_pic' id='display_"+display+"' value='"+src+"'/>  ");
									display++;
									if(first=="")first = src;									
								}			
								$(".slider-box-pic").attr("src",first);								
							}					
						}						
						var count = $(".stack_slider_pic").length;
						if(count==0){			
							$(".slider-box-pic").removeClass("loading");				
							$(".slider-box-pic").first().attr("src","/img/default_comp_pic.png");						
						}else if(count==1){
							$(".slider-box-pic").removeClass("loading");	
						}
						function slideBox(){		
							if(index<count-1)index++;
							else index=0;								
							$(".slider-option-active").addClass("slider-option-unactive");
							$(".slider-option-active").removeClass("slider-option-active");
							$(".slider-option[idx='"+index+"']").removeClass("slider-option-unactive");
							$(".slider-option[idx='"+index+"']").addClass("slider-option-active");													
							if($(".slider-box-pic").attr("class").indexOf("loading")>=0){
								$(".slider-box-pic").removeClass("loading");
								$(".slider-box-pic").attr("src",$("#display_"+(index+1)).val());
							}else{
								$(".slider-box-pic").fadeOut(500,function(){													
									$(".slider-box-pic").attr("src",$("#display_"+(index+1)).val());
									$(".slider-box-pic").fadeIn(500);														
								});		
							}																																																
						}												
						if(count<=1)$(".slider-box-pic").attr("src",$("#display_1").val());
						else setInterval(slideBox,3000);
					</script>		
        			<div class='slider-option-wrapper'>					        			
        			</div>
			</div>        		        		
        		<script>
					for(var i=0;i<count;i++)$(".slider-option-wrapper").append("<div class='slider-option slider-option-unactive' idx='"+i+"'></div>");
    				$(".slider-option").first().removeClass("slider-option-unactive")
    				$(".slider-option").first().addClass("slider-option-active");
    				$(".slider-option").hover(function(){
    					$(".slider-option").removeClass("slider-option-active");
    					$(".slider-option").addClass("slider-option-unactive");
    					$(this).removeClass("slider-option-unactive");
    					$(this).addClass("slider-option-active");
    					index = parseInt($(this).attr("idx"));
    					$(".slider-box-pic").attr("src",$("#display_"+(index+1)).val());	        					
    				});
    				$(".prev-slider-box").click(function(){
    					var current = parseInt($(".slider-option-active").attr("idx"))-1;    					
    					$(".slider-option").removeClass("slider-option-active");
    					$(".slider-option").addClass("slider-option-unactive");
    					index = current<0?count-1:current;    					
    					$(".slider-option").eq(index).removeClass("slider-option-unactive");
    					$(".slider-option").eq(index).addClass("slider-option-active");	
    					$(".slider-box-pic").attr("src",$("#display_"+(index+1)).val());
    				});
    				$(".next-slider-box").click(function(){
    					var current = parseInt($(".slider-option-active").attr("idx"))+1;    					
    					$(".slider-option").removeClass("slider-option-active");
    					$(".slider-option").addClass("slider-option-unactive");    					
    					index = current>=count?0:current;
    					$(".slider-option").eq(index).removeClass("slider-option-unactive");
    					$(".slider-option").eq(index).addClass("slider-option-active");    					
    					$(".slider-box-pic").attr("src",$("#display_"+(index+1)).val());
    				});
	        	</script>       		
        	</div>
        	<!--sub title-->	
        	</#if>	   					       
        	<div class='sub-title'>
        		<div class='sub-title-name'>能胜任的工作类型</div><hr>
        		<div class='tag-group'>
					<#if skillItemList??>
	        			<#if skillItemList?size==1&&skillItemList[0]=="">
	        				<div class='sub-info'>暂无</div>
	        			</#if>
	        			<#if skillItemList[0]!="">
			        		<#list skillItemList as tag>
			  	  				<div class='skill-tag' value='222'>${tag}</div>
							</#list> 
						</#if>
					</#if> 
        		</div>
        	</div>
        	<div class='sub-info'>
        		<p id="p_skill"></p>
        	</div>
        	<!--sub title-->		   					       
        	<div class='sub-title'>
        		<div class='sub-title-name'>擅长技术</div><hr>
        		<div class='tag-group'>
        			<#if abilityItemList??>						
	        			<#if abilityItemList[0]!="">
        					<#list abilityItemList as tag>
			  	  				<div class='skill-tag' value='222'>${tag}</div>
							</#list>	
						</#if>
						<#if userInfo.otherAbility??&&userInfo.otherAbility!=""><div class='skill-tag' value='222'>${userInfo.otherAbility}</div>
	        				<#elseif abilityItemList?size==1&&abilityItemList[0]==""><div class='sub-info'>暂无</div>
	        			</#if>										
					</#if>      		
        		</div>
        	</div>
        	<div class='sub-info'>
        		<p id="p_skill"></p>
        	</div>
        	<#if userInfo.identifyInfo.category==0>
        		<!--sub title-->		   					       
        		<div class='sub-title'>
        			<div class='sub-title-name'>工作经验</div><hr>
        		</div>
        		<#if userInfo.employeeJobExperience??>
        			<#list userInfo.employeeJobExperience as job>        		
        				<div class='proj-row job-row'>
        					<div class='job-row-data'>${job.companyName}</div>
							<div class='job-row-data'>${job.office}</div>
							<div class='job-row-data'>${job.startTime}~${job.endTime}</div>
							<div class='job-row-data job-row-description'>${job.description}</div>
        				</div>        		
        			</#list>
        		</#if>
        	</#if>        	
        	<#if userInfo.identifyInfo.category==1>
        	<!--sub title-->		   					       
        	<div class='sub-title'>
        		<div class='sub-title-name'>服务领域</div><hr>
        		<div class='tag-group'>
					<#if caseTypeItemList??>						
	        			<#if caseTypeItemList[0]!="">
        					<#list caseTypeItemList as tag>
			  	  				<div class='scope' value='222'>${tag}</div>
							</#list>	
						</#if>
						<#if userInfo.otherCaseType??&&userInfo.otherCaseType!=""><div class='scope' value='222'>${userInfo.otherCaseType}</div>
	        				<#elseif caseTypeItemList?size==1&&caseTypeItemList[0]==""><div class='sub-info'>暂无</div>
	        			</#if>										
					</#if> 
        		</div>
        	</div>
        	</#if>
        	<!-- sub title-->        	
        	<div class='sub-title'>
        		<div class='sub-title-name'><#if userInfo.identifyInfo.category==1>项目经验<#else>项目作品</#if></div><hr>
        	</div>        	
        	<#if userInfo.identifyInfo.category==1>
	        	<#if userInfo.employeeProjectExperience??>
					<#list userInfo.employeeProjectExperience as project>
						<div class='proj-row row'>
							<div class='proj-name col-md-12' onclick='window.open("${project.link!"#"}")'>
								<#if project.projectName??>
									${project.projectName}
								</#if>
							</div>					
							<div class='proj-desc'>
								<#if project.description??>
									${project.description}
								</#if>
							</div>
							<hr>
						</div>
					</#list> 
					<#else>
					暂无数据 		        			
				</#if>
				<#else>
					<#if userInfo.employeeProduct??>
						<#list userInfo.employeeProduct as project>
							<div class='proj-row row'>
								<div class='proj-name col-md-12' onclick='window.location.href="${project.link!"#"}"'>
									<#if project.title??>
										${project.title}
									</#if>
								</div>					
								<div class='proj-desc'>
									<#if project.description??>
										${project.description}
									</#if>
								</div>
								<hr>
							</div>
						</#list>
					</#if>
			</#if>
			<#if userInfo.identifyInfo.category==0>
			    <!-- sub title-->        	
        		<div class='sub-title'>
					<div class='sub-title-name'>教育背景</div><hr>
				</div>
				<#if userInfo.employeeEduExperience??>
					<#list userInfo.employeeEduExperience as edu>
						<div class='proj-row edu-row'>
							<div class='edu-row-data'>${edu.schoolName}</div>
							<div class='edu-row-data'>${edu.discipline}</div>
							<div class='edu-row-data'>${edu.eduBackgroud}</div>
							<div class='edu-row-data'>${edu.graduationTime}年毕业</div>
						</div>
					</#list>
				</#if>
			</#if>
        	<!--sub title 评论-->		   					       
        	<#if comment??>
        	<div class='sub-title'>
        		<div class='sub-title-name'>综合评价</div><hr>
        	</div>
        	<div class='sub-info'>
        		<div class='sub-remark'>
        			<div class='general-remark'><span class='general-remark-title'>综合评分:</span></div>
        			<script>
        				var remark = "${remark}";        				
        				var whole_rate = remark;
        				var offset = 0;
        				if(remark.indexOf(".")>0){
        					whole_rate = remark.substring(0,remark.indexOf("."));
        					if(remark.indexOf(".")<remark.length-1)offset = remark.substring(remark.indexOf(".")+1,remark.indexOf(".")+2);   
        				}        				        		
        				for(var i=1;i<=5;i++){
        					if(whole_rate>=i)$(".general-remark").append("<img class='remark-star' src='/thirdparty/jqueryraty/img/star-on-big.png'/>");
        					else $(".general-remark").append("<img class='remark-star empty-star' src='/thirdparty/jqueryraty/img/star-off-big.png'/>");
        				}
        				if(offset>0){        				
        					$(".empty-star").first().attr("src","/thirdparty/jqueryraty/img/star-half-big.png");
        				}
        				$(".general-remark").append("<span class='remark'>"+remark+"</span><span class='remark-count-from'>(来自26份评价)</span>");
        			</script>			
        		</div>
        	</div>
        	
        	<!--comment-->
        	<div class='sub-info'>
        		<div class='cut-line' style='width:95%;'></div> 
        		<div class='comment-wrapper'>
        			<div class='comment-headimg-wrapper'><img src='/img/head.jpg'/></div>
        			<div class='comment-user-name'>匿名</div>
        			<div class='comment-rate'>综合评分:</div>
        			<div class='comment-proj-name'>基于微信平台的管理系统</div>
        			<div class='comment-date'>2015-11-23</div>
        			<div class='comment-buddle'>
        				<div class="bubble-box arrow-top"> 
							<div class="wrap">内容内容</div> 
						</div> 
        			</div>
        		</div>
        		<div class='cut-line' style='width:95%;'></div> 
        		<div class='comment-wrapper'>
        			<div class='comment-headimg-wrapper'><img src='/img/head.jpg'/></div>
        			<div class='comment-user-name'>匿名</div>
        			<div class='comment-rate'>综合评分:</div>
        			<div class='comment-proj-name'>基于微信平台的管理系统</div>
        			<div class='comment-date'>2015-11-23</div>
        			<div class='comment-buddle'>
        				<div class="bubble-box arrow-top"> 
							<div class="wrap">内容内容</div> 
						</div> 
        			</div>
        		</div>
        		<div class='cut-line' style='width:95%;'></div> 
        		<div class='comment-wrapper'>
        			<div class='comment-headimg-wrapper'><img src='/img/head.jpg'/></div>
        			<div class='comment-user-name'>匿名</div>
        			<div class='comment-rate'>综合评分:</div>
        			<div class='comment-proj-name'>基于微信平台的管理系统</div>
        			<div class='comment-date'>2015-11-23</div>
        			<div class='comment-buddle'>
        				<div class="bubble-box arrow-top"> 
							<div class="wrap">内容内容</div> 
						</div> 
        			</div>
        		</div>	
        	</div>     	
	    </div>
	   </#if>
	   
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
   	<input type="hidden" id="reload" value="1"/>
   </div>
   
     <!--CNZZ CODE-->
      ${cnzz_html}
     <!--END OF CNZZ CODE-->
  </body>
</html>
