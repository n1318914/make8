<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>用户认证审核 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
    <#include "../comm-includes.ftl">
    ${basic_includes}
    ${tools_includes}
    <link href="/css/compinfo_modify.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/js/compinfo_modify.js"></script>
  </head>
  <body>
    <!--header-->
    <#include "../header.ftl">
    <!--end of header-->
    
    <div class="body-offset"></div>

    <!---end of hidden parameters-->
    <div class="container">
    	<div class="breadcrumb-container">
			  <ol class="breadcrumb">
				  <li><a href="/">首页</a></li>
				  <li><a href="#">服务商录入</a></li>
			  </ol>
		  </div>
    </div>
    <form id='com-info-form' name='com-info-form' action='javascript:modifySubmit();' role='form' method='POST'>
    <input type='hidden' name='id' value='${userInfo.id}'/>
  	<div class="container center-container">  			  
	  		  <!--右侧面板-->
	  		  <div class='content-wrapper'>
	  		  	  <!--公司信息-->
	  		  	  <div class='content-panel base-info-panel'>
	  		  	  		<div class='input-wrapper required custom-input-wrapper'><div class='remark'></div><label class='input-prevtfont'>公司名称 :</label><input class='custom-input' id='comp_name' name='identifyInfo.companyName' limit type='text' value='${userInfo.identifyInfo.companyName!""}'/></div>
	  		  	  		<div class='input-wrapper custom-input-wrapper required'><div class='remark'></div>
	  		  	  			<label class='input-prevtfont'>区域:</label>
	  		  	  			<select class="custom-input" name="province" style='width:20%;margin-left:-0.7em;' id="province" ng-model='province' ng-options="province.name for province in provinceList" ng-change='selectProvince()'>
			     	 			<option value=''>全部</option>
			     	 			<#list provinceList as province>
			     	 				<option value='${province.id}' <#if province.id==userInfo.provinceId>selected='true'</#if>>${province.name}</option>
			     	 			</#list>
			     	 		</select>	
	  		  	  			<select class="custom-input city" name="province" id="city" style='width:20%;' ng-model='province' ng-options="province.name for province in provinceList" ng-change='selectProvince()' limit>			     	 			
			     	 			<#if cityList??>
			     	 				<#list cityList as city>
			     	 					<option value='${city.id}'>${city.name}</option>
			     	 				</#list>
			     	 				<#else>
			     	 				<option value=''>全部</option>
			     	 			</#if>
			     	 		</select>
	  		  	  		</div>
	  		  	  		<div class='input-wrapper custom-input-wrapper'><div class='remark'></div><label class='input-prevtfont'>详细地址 :</label><input class='custom-input' name='identifyInfo.companyAddr' type='text' value='${userInfo.identifyInfo.companyAddr!""}'/></div>	  		  	  	
	  		  	  		<div class='input-wrapper custom-input-wrapper'><div class='remark'></div><label class='input-prevtfont'>公司官网 :</label><input class='custom-input'  type='text' name='identifyInfo.siteLink' value='${userInfo.identifyInfo.siteLink!""}'/></div>
	  		  	  </div>
	  		  	  <!--公司规模-->
	  		  	  <div class='content-panel com-size-panel'>	  
	  		  	  		<div class='panel-title required'><div class='remark'></div><label>公司规模 :</label></div>	
	  		  	  		<div class='sub-panel radio-group'>	
		  		  	  		<div class='radio-item radio-item-checked auto-row' checked value='1'><div class='dotted'></div></div><label class='font-item'>小于10人</label>
		  		  	  		<div class='radio-item auto-row' value='2'><div class='dotted'></div></div><label class='font-item'>10-30人</label>
		  		  	  		<div class='radio-item auto-row' value='3'><div class='dotted'></div></div><label class='font-item'>31-100人</label>
		  					<div class='radio-item auto-row' value='4'><div class='dotted'></div></div><label class='font-item'>100人以上</label>
		  					<input type='hidden' id='companySize' class='hidden-input' name='companySize' value='${userInfo.companySize!1}'/>
	  		  	  		</div>	  		  	  		
	  		  	  </div>
	  		  	  <!--工作类型-->
	  		  	  <div class='content-panel com-type-panel' count=1>
	  		  	  		<div class='panel-title required'><div class='remark'></div><label>能胜任的工作类型 :</label></div>	
	  		  	  		<div class='sub-panel checkbox-group'>	
	  		  	  			<#list listDictItem as tag>
	  		  	  				<#if tag.type=="cando">
	  		  	  					<div class='checkbox-item' value="${tag.value}">
				  		  	  			<i class='fa fa-check'></i>		  		  	  			
				  		  	  		</div>
				  		  	  		<label class='font-item'>${tag.name}</label>	
	  		  	  				</#if>
							</#list>	
							<input type='hidden' id='cando' name='cando' value="${userInfo.cando!"1"}"/>	    		  	  				  		  	  				  				
	  		  	  		</div>		  		  	  		
	  		  	  </div>
	  		  	  <!--擅长技术-->
	  		  	  <div class='content-panel com-skill-panel'>
	  		  	  		<div class='panel-title skill-ensure required'><div class='remark'></div><label>擅长技术 :</label></div>	
	  		  	  		<div class='tag-group'>
	  		  	  			<#list listDictItem as tag>
	  		  	  				<#if tag.type=="ability">
	  		  	  					<div class='skill-tag' value='${tag.value}'>${tag.name}</div>
	  		  	  				</#if>
							</#list>
							<input type='hidden' id='mainAbility' name='mainAbility' value="${userInfo.mainAbility!"1"}"/>	  		  	  			  		  	  			  		  	  		
	  		  	  		</div>	  		  	  			  		 
	  		  	  		<div class='input-wrapper other-skill'><div class='remark'></div><input type='text' class='custom-input' id='other-skill-input' placeholder='其它技术' value='${userInfo.otherAbility!""}'/></div>	 	  		
	  		  	  </div>
	  		  	  <!--服务领域-->
	  		  	  <div class='content-panel scope-panel'>
	  		  	  		<div class='panel-title required'><div class='remark'></div><label>服务领域 :</label></div>
	  		  	  		<div class='scope-list'>
	  		  	  			<#list listDictItem as tag>
	  		  	  				<#if tag.type=="caseType">
	  		  	  					<div class='scope' value='${tag.value}'>${tag.name}</div>	
	  		  	  				</#if>
							</#list>	
							<input type='hidden' id='caseType' name='caseType' value="${userInfo.caseType!"1"}"/>	  								  		  	  		
	  		  	  		</div>	
	  		  	  		<div class='input-wrapper other-scope'><div class='remark'></div><input type='text' class='custom-input' id='other-scope' placeholder='其它领域' value='${userInfo.otherCaseType!""}'/></div>	
	  		  	  </div>
	  		  	  <!--项目经验-->
	  		  	  <div id='proj-exprience' class='content-panel proj-container custom-count' max="10" count='${userInfo.employeeProjectExperience?size}'>
	  		  	  		<div  class='panel-title required'>
	  		  	  			<div class='remark'></div><label>项目经验 :</label>
	  		  	  			<span>
	  		  	  				<a class='add-project-a' title='添加'>
	  		  	  					<i class='fa fa-plus-circle' style='float:right;margin-right:2em;font-size:1.5em;color:#3498DB;cursor:pointer;'></i>
	  		  	  				</a>
	  		  	  			</span>
	  		  	  		</div>
	  		  	  		<div class='proj-row-wrapper'>
		  		  	  		<div class='proj-row  proj-row-model auto-row'>
		  		  	  			<div class='proj-row-td projname-val'>1</div>
		  		  	  			<div class='proj-row-td projlink-val'>3</div>
		  		  	  			<div class='proj-row-td projdesc-val'>3</div>
		  		  	  			<div class='row-operate proj-row-operate'>		  		  	  				
		  		  	  				<a title='修改' class='proj-row-edit'><i class="fa fa-pencil fa-1x"></i></a>
		  		  	  				<a title='删除' class='proj-row-del'><i class='fa fa-times-circle fa-1x'></i></a>
		  		  	  			</div>
		  		  	  		</div>			  		  	  		
		  		  	  		<hr/>
		  		  	  		<#list userInfo.employeeProjectExperience as project>
		  		  	  		<div class='proj-row  auto-row' active='true'>
		  		  	  			<div class='proj-row-td projname-val'>${project.projectName}</div>
		  		  	  			<div class='proj-row-td projlink-val'>${project.link}</div>
		  		  	  			<div class='proj-row-td projdesc-val'>${project.description}</div>
		  		  	  			<div class='row-operate proj-row-operate'>		  		  	  				
		  		  	  				<a title='修改' class='proj-row-edit'><i class="fa fa-pencil fa-1x"></i></a>
		  		  	  				<a title='删除' class='proj-row-del'><i class='fa fa-times-circle fa-1x'></i></a>
		  		  	  			</div>
		  		  	  			<input type='hidden' class="projectName" name='employeeProjectExperience[${project_index?c}].projectName' value='${project.projectName}'/>
		  		  	  			<input type='hidden' class="link" name='employeeProjectExperience[${project_index?c}].link' value='${project.link}'/>
		  		  	  			<input type='hidden' class="startTime" name='employeeProjectExperience[${project_index?c}].startTime' value='${project.startTime}'/>
		  		  	  			<input type='hidden' class="endTime" name='employeeProjectExperience[${project_index?c}].endTime' value='${project.endTime}'/>
		  		  	  			<input type='hidden' class="description" name='employeeProjectExperience[${project_index?c}].description' value='${project.description}'/>
		  		  	  			<input type='hidden' class="ranking" name='employeeProjectExperience[${project_index?c}].ranking' value='${project.ranking}'/>
		  		  	  		</div>	
		  		  	  		<hr/>
		  		  	  		</#list>
	  		  	  		</div>	  		  	  		  		  	  		
	  		  	  		<div class='add-proj'>
	  		  	  			<div class='add-proj-wrapper'>
	  		  	  				<div class='add-project'>添加项目经验</div>	  
		  		  	  			<div class='outer-circle'>
			  		  	  			<div class='inner-circle'>
			  		  	  				<label class='plus-symbol'>+</label>		  		  	  					  		  	  			
			  		  	  			</div>
		  		  	  			</div>		  		  	  					  	  		
	  		  	  			</div>	  	  		  	  			
	  		  	  		</div>	  		  	  				
	  		  	  </div>
	  		  	   <!--公司简介-->
	  		  	  <div class='content-panel'>
	  		  	  		<div class='panel-title required'><div class='remark'></div><label>公司简介 :</label></div>
	  		  	  		<div class='com-descript-wrapper'>
	  		  	  			<textarea wrap="off | hard | virtual | physical" class='com-descript'  name='introduction' maxlength="500" minlength="11" required>${userInfo.introduction!""}</textarea>
	  		  	  		</div> 
	  		  	  </div>
	  		  	   <!--公司官网-->
	  		  	  <div class='content-panel pic-panel' max='1' groupid='1'>
	  		  	  		<div class='panel-title'><div class='remark'></div><label>公司官网 :</label></div>	  		  	  		
	  		  	  		<div class='pic-line-list'>
	  		  	  			<div class='pic-line pic-line-model'></div>
	  		  	  			<div id='pic-row-operate-model' class='row-operate pic-row-operate'>
	  		  	  				<a title='修改' class='pic-row-edit'><i class="fa fa-pencil fa-1x"></i></a>
	  		  	  				<a title='删除' class='pic-row-del'><i class='fa fa-times-circle fa-1x'></i></a>
		  		  	  		</div>
	  		  	  		</div>
	  		  	  		<div class="picselect-container">	  		  	  			
				           <input id="picselect" name="pic" class="picselect file" type="file"></input>
				        </div>
				        <div class="" >
				           <span id="idCardPicMsg" class="error-message"></span>
				        </div>
	  		  	  </div>
	  		  	   <!--公司前台-->	  		  	   
	  		  	  <div class='content-panel pic-panel' max='1' groupid='2'>
	  		  	  		<div class='panel-title'><div class='remark'></div><label>公司前台 :</label></div>
	  		  	  		<div class='pic-line-list'>
	  		  	  			<div class='pic-line pic-line-model'></div>
	  		  	  		</div>
	  		  	  		<div class="picselect-container">
				           <input id="picselect" name="pic" class="picselect file" type="file"></input>
				        </div>
				        <div class="" >
				           <span id="idCardPicMsg" class="error-message"></span>
				        </div>
	  		  	  </div>
	  		  	  <!--办公环境(最多可添加4张图片)-->
	  		  	  <div class='content-panel pic-panel' max='4' groupid='3'>
	  		  	  		<div class='panel-title'><div class='remark'></div><label>办公环境(最多可添加4张图片) :</label></div>
	  		  	  		<div class='pic-line-list'>
	  		  	  			<div class='pic-line pic-line-model'></div>
	  		  	  		</div>
	  		  	  		<div class="picselect-container">
				           <input id="picselect" name="pic" class="picselect file" type="file"></input>
				        </div>
				        <div class="" >
				           <span id="idCardPicMsg" class="error-message"></span>
				        </div>
	  		  	  </div>
	  		  	  <!--公司团队(最多可添加2张图片)-->
	  		  	  <div class='content-panel pic-panel' max='2' groupid='4'>
	  		  	  		<div class='panel-title'><div class='remark'></div><label>公司团队(最多可添加2张图片) :</label></div>
	  		  	  		<div class='pic-line-list'>
	  		  	  			<div class='pic-line pic-line-model'></div>
	  		  	  		</div>
	  		  	  		<div class="picselect-container">
				           <input id="picselect" name="pic" class="picselect file" type="file"></input>
				        </div>
				        <div class="" >
				           <span id="idCardPicMsg" class="error-message"></span>
				        </div>
	  		  	  </div>
	  		  	  <input type='hidden' id="comPicList" value='${userInfo.comPicList!""}'/>
	  		  	  <div id='submit-info' class='finish'>完成</div>	  		  	  		
	  		  </div>
	  		  <!--右侧面板结束-->	  	
  		  </div>  		  
  	</div>
  	</form>	  	
	 <!--项目对话框-->
	<div id="project-dialog" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<input type='hidden' name='stepId' value=''/>
    	<div class="modal-dialog">
        		<div class="container dialog-container">
		            <div class='row dialog-header'>
		            	<!--header-->
		            	<div class='col-md-12 dialog-option dialog-option-selected header-left'>添加项目经验</div>
		            	<!--header end-->
		            </div> 
					<div class='row row-bg'>
		            	<!--header-->
		            	<div class='col-md-12' style='text-align:left;'>
		            		<label class="control-label form-label" for="workinghours">项目名称 </label>
		            	</div>
		            	<div class='col-md-12 custom-input-wrapper'>
		            		<input class='custom-input form-control' id='projname-input' type='text' limit/>
		            	</div>
		            	<!--header end-->
		            </div> 
					<div class='row row-bg'>
		            	<!--header-->
		            	<div class='col-md-12' style='text-align:left;'>
		            		<label class="control-label form-label" for="workinghours">项目链接 </label>
		            	</div>
		            	<div class='col-md-12 custom-input-wrapper'>
		            		<input class='custom-input form-control' id='projlink-input' type='text' limit/>
		            	</div>
		            	<!--header end-->
	           		 </div> 	
	           		 <div class='row row-bg'>
		            	<!--header-->
		            	<div class='col-md-12' style='text-align:left;'>
		            		<label class="control-label form-label" for="workinghours">项目描述 </label>
		            	</div>
		            	<div class='col-md-12 custom-input-wrapper'>
		            		<textarea wrap="off | hard | virtual | physical" class='com-descript proj-textarea form-control' id='projDesc' limit='500'></textarea>
		            	</div>
		            	<!--header end-->
	           		 </div> 							            			            	
					<!-- footer-->
					<div class='row row-bg'>
					<div class='col-md-12'>
						<hr>
					</div>
					<div class='col-md-12' style='text-align:center;padding:5px;'>
						<button id="add-project-btn" class="btn btn-primary">确定</button>
						<button type="button" id="form-cancel" class="btn btn-default" onclick='$("#project-dialog").modal("hide")'>取消</button>
					</div>
				</div>	
				<!--footer end-->
	            </div> 						 
   		  	 </div>
   		   <!--container end-->
   	   </div>
		<!--项目对话框 end--> 
  	<!--特效代码模版-->
  	<div class="spinner loading-model">
		  <div class="spinner-container container1">
		    <div class="circle1"></div>
		    <div class="circle2"></div>
		    <div class="circle3"></div>
		    <div class="circle4"></div>
		  </div>
		  <div class="spinner-container container2">
		    <div class="circle1"></div>
		    <div class="circle2"></div>
		    <div class="circle3"></div>
		    <div class="circle4"></div>
		  </div>
		  <div class="spinner-container container3">
		    <div class="circle1"></div>
		    <div class="circle2"></div>
		    <div class="circle3"></div>
		    <div class="circle4"></div>
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
