<!DOCTYPE html>
<html>

	<head>
		<title>智能估价 - 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
		<#include "../comm-includes.ftl">
	  ${basic_includes}
	  ${tools_includes}
	  <link rel="stylesheet" href="${ctx}/css/public/evaluate.css?v=${version}">
	  <script type="text/javascript" src="${ctx}/js/public/evaluate.js?v=${version}"></script>
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->
			
			<div class="col-xs-12 col-sm-12 col-md-12 banner"></div>
			
			<!--tab页-->
			<div class="tabDiv hidden-xs" onmouseover="tabDivActive()">
				<div class="tab">
					<#list listData as module>
						<#if module.isDock == 1>
							<div pagePosition="module${module.id}" onclick="tabClickActive(this)">${module.name}</div>
						</#if>
					</#list>
					<div><a href="#"><img src="${store_location}/img/toTop.png"/></a></div>
				</div>
			</div>
			
			<!--控制台-->
			<div class="col-md-12 col-sm-12 col-xs-12 productController">
				<div class="container">
					<div class="productType">
						<div class="col-xs-12 col-sm-2 col-md-1 padding">产品类型：</div>
						<div class="col-xs-12 col-sm-10 col-md-11 padding-right">
							<div class="col-xs-6 col-sm-4 col-md-2 option padding-left">
								<div type="1" class="option_div" onclick="selected(this)">Android APP</div>
							</div>
							<div class="col-xs-6 col-sm-4 col-md-2 option padding-left">
								<div type="2" class="option_div" onclick="selected(this)">iOS APP</div>
							</div>
							<div class="col-xs-6 col-sm-4 col-md-2 option padding-left">
								<div type="3" class="option_div" onclick="selected(this)">HTML5</div>
							</div>
							<div class="col-xs-6 col-sm-4 col-md-2 option padding-left">
								<div type="4" class="option_div" onclick="selected(this)">网站</div>
							</div>
							<div class="col-xs-6 col-sm-4 col-md-2 option padding-left">
								<div type="5" class="option_div" onclick="selected(this)">微信</div>
							</div>
						</div>
					</div>
					<div class="productField">
						<div class="col-xs-12 col-sm-2 col-md-1 padding">产品领域：</div>
						<div class="col-xs-12 col-sm-10 col-md-11 padding-right">						
							<#list listData as module>
								<#if module.isDock == 0>
									<div class="col-xs-6 col-sm-4 col-md-2 option padding-left">
										<div moduleId="module${module.id}" class="option_div" onclick="selected(this)">${module.name}</div>
									</div>									
								</#if>
							</#list>
							<div class="col-xs-6 col-sm-4 col-md-2 option padding-left">
								<div other="false" class="option_div" onclick="selected(this)">其他</div>
							</div>
						</div>						
					</div>
					<div class="productScale">
						<div class="col-xs-12 col-sm-2 col-md-1 padding">产品规模：</div>
						<div class="col-xs-12 col-sm-10 col-md-11 padding-right">
							<div class="col-xs-6 col-sm-4 col-md-2 option padding-left">
								<div scale="1" class="option_div" onclick="selected(this)">小型</div>
							</div>
							<div class="col-xs-6 col-sm-4 col-md-2 option padding-left">
								<div scale="2" class="option_div" onclick="selected(this)">中型</div>
							</div>
							<div class="col-xs-6 col-sm-4 col-md-2 option padding-left">
								<div scale="3" class="option_div" onclick="selected(this)">大型</div>
							</div>
						</div>						
					</div>
				</div>
			</div>
			
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="container">
					<!--报价结果-->
					<div class="col-md-4 col-xs-12 col-sm-12 padding price">
						<div class="col-md-12 col-xs-12 col-sm-12 priceTitle"><div class="col-md-6 col-sm-6 col-xs-6 padding">报价结果</div><div class="col-md-6 col-sm-6 col-xs-6 padding text-right"><div><a href="javascript:createQuotation()"><img title="修改估价" src="${store_location}/img/product_modify.png"/></a></div></div></div>
						<div class="col-md-12 col-xs-12 col-sm-12 text-center priceContent padding">
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 padding priceDiv"><div class="col-md-6 col-sm-6 col-xs-6 text-right padding">产品类型：</div><div class="col-md-6 col-sm-6 col-xs-6 text-left price_style padding" id="resultProductType">未定</div></div>
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 padding priceDiv"><div class="col-md-6 col-sm-6 col-xs-6 text-right padding">产品领域：</div><div class="col-md-6 col-sm-6 col-xs-6 text-left price_style padding" id="resultProductField">其他</div></div>
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 padding priceDiv"><div class="col-md-6 col-sm-6 col-xs-6 text-right padding">产品规模：</div><div class="col-md-6 col-sm-6 col-xs-6 text-left price_style padding" id="resultProductScale">未定</div></div>
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 padding priceDiv"><div class="col-md-6 col-sm-6 col-xs-6 text-right padding">开发周期：</div><div class="col-md-6 col-sm-6 col-xs-6 text-left padding"><span class="price_style" id="resultPeroidMin"></span>-<span class="price_style" id="resultPeroidMax"></span> 天</div></div>
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 padding priceDiv"><div class="col-md-6 col-sm-6 col-xs-6 text-right padding">报价：</div><div class="col-md-6 col-sm-6 col-xs-6 text-left padding"><span class="price_style" id="resultPriceMin"></span>~<span class="price_style" id="resultPriceMax"></span> 元</div></div>
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 priceMsg">
								<div class="col-md-12 col-xs-12 col-sm-12 text-center">
									提示：智能报价结果仅供参考，发布后码客帮顾问会给您提供更多的报价详情
								</div>
								<button class="btn btn-primary assessmentBtnDiv" onclick="window.location.href='/home/request'">马上发布</button>
							</div>
						</div>
					</div>
					<!--类似的项目-->
					<div class="col-md-8 col-xs-12 col-sm-12 padding-right price">
						<div class="col-md-12 col-xs-12 col-sm-12 priceTitle"><div class="col-md-6 col-sm-6 col-xs-6 padding">类似项目</div><div class="col-md-6 col-sm-6 col-xs-6 padding text-right"><div><a href="javascript:similarProject()"><img title="换一批" src="${store_location}/img/change_icon.png"/></a></div></div></div>
						<div class="col-md-12 col-xs-12 col-sm-12 text-center similarProject padding">
						</div>
					</div>
				</div>
			</div>

			<!--产品功能选择-->
			<div class="col-md-12 col-sm-12 col-xs-12 datas">
				<div class="container">
					
					<#list listData as module>
						<#if module.isDock == 0>
							<div id="module${module.id}" class="col-md-12 col-xs-12 col-sm-12 module hide padding">
						<#else>
							<div id="module${module.id}" class="col-md-12 col-xs-12 col-sm-12 module padding">							
						</#if>
							<div class="tableTile col-md-12 col-sm-12 col-xs-12">${module.name}</div>
							<div class="tableTh col-md-12 col-sm-12 col-xs-12 padding">
								<div class="col-md-2 col-sm-2 col-xs-12">模块</div>
								<div class="col-md-3 col-sm-3 col-xs-4">功能点</div>
								<div class="col-md-3 col-sm-3 col-xs-4">工程量</div>
								<div class="col-md-4 col-sm-4 col-xs-4">描述</div>
							</div>	
							<#list module.listEvaluateGroup as group>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="tableTd tableTd_title col-md-2 col-sm-2 col-xs-12 padding">
										<div class="selectAll">
											<#if group.isRadio == 0>
												<div isRadio="${group.isRadio}" class="checkBoxAllBtn" onclick="checkBoxSelectAll(this)"><img src="${store_location}/img/checkboxchecked.png"/></div>
											<#else>
												<div isRadio="${group.isRadio}" class="checkBoxAllBtn" style="display: none;" onclick="checkBoxSelectAll(this)"><img src="${store_location}/img/checkboxchecked.png"/></div>
											</#if>
											<div>${group.name}</div>
										</div>							
									</div>		
									<div class="tableTd col-md-3 col-sm-3 col-xs-4 padding">
										<#list group.listEvaluateItemVo as item>
											<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
												<div class="radio">
													<div itemId="${item.id}" onclick="checkBoxSelect(this)" class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
													<div>${item.name}</div>
												</div>
											</div>
										</#list>
									</div>
									<div class="tableTd col-md-3 col-sm-3 col-xs-4 padding">
										<#list group.listEvaluateItemVo as item>
											<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
												<div class="number">
													<div numberId="${item.id}">￥<span>${item.getMinPrice}</span>~￥<span>${item.getMaxPrice}</span>(<span>${item.getMinPeroid}</span>~<span>${item.getMaxPeroid}</span>天)</div>
												</div>
											</div>											
										</#list>
									</div>
									<div class="tableTd col-md-4 col-sm-4 col-xs-4 padding">
										<#list group.listEvaluateItemVo as item>
											<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding" title="${item.remark!""}" onmouseover="mobileTile(this)" onmouseout="mobileTileRemove(this)">
												<div class="describe" describeId="${item.id}">
													${item.remark}
												</div>
											</div>										
										</#list>
									</div>
								</div>
							</#list>	
						</div>
					</#list>					
				</div>
			</div>
			
			<!--报价信息-->
			<div class="col-md-12 col-sm-12 col-xs-12 result">
				<div class="container padding">
					<div class="col-md-12">
						<div class="col-md-4 col-sm-4 col-xs-4 resultInfo">预估报价：<span class="defaultResult">0 </span><span class="resultSum"><span id="priceMinNum"></span>-<span id="priceMaxNum"></span>元</span></div>
						<div class="col-md-4 col-sm-4 col-xs-4 resultInfo">开发周期：<span class="defaultResult">0 </span><span class="resultSum"><span id="peroidMinNum"></span>~<span id="peroidMaxNum"></span>天</span></div>
						<div class="col-md-4 col-sm-4 col-xs-4 resultInfoDiv">项目平台：<span id="productType"></span></div>
						<div class="col-md-12 col-sm-12 col-xs-12 remind"><span class="remind_label">提示：</span>智能报价结果仅供参考，发布后码客帮顾问会给您提供更多的报价详情</div>
					</div>
					<div class="col-md-12 col-sm-12 col-xs-12 padding">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="col-md-6 col-sm-6 col-xs-6 text-left">
								<button class="btn btn-primary assessmentBtn" onclick="createQuotation(this)">生成报价单</button>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-6 text-right padding-right">
								<button class="btn btn-primary assessmentBtnDiv" onclick="window.location.href='/home/request'">马上发布</button>
							</div>						
						</div>
					</div>
				</div>
			</div>

			<div>
				<input type="hidden" id="topurl" value="${store_location}/" />
			</div>

			<!--start of footer-->
			<#include "../footer.ftl">
			<!--end of footer-->
			<!---start of help docker-->
			<!--<div id="top"></div>-->
			<!--end of help docker-->

			<!--CNZZ CODE-->
			<!--${cnzz_html}-->
			<!--END OF CNZZ CODE-->
	</body>

</html>