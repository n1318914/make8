<!DOCTYPE html>
<html>

	<head>
		<title>智能估价- 码客帮 - 让靠谱的工程师为你开发 | 互联网软件众包平台</title>
    <#include "../common.ftl">
		<#include "../comm-includes.ftl">
	  ${basic_includes}
	  ${tools_includes}
	  <link rel="stylesheet" href="${store_location}/css/public/evaluate.css?v=${version}">
	</head>

	<body>
		<!--header-->
		<#include "../header.ftl">
			<!--end of header-->
			
			<div class="col-xs-12 col-sm-12 col-md-12 banner"></div>
			
			<!--产品功能选择-->
			<div class="col-md-12 col-sm-12 col-xs-12 data">
				<div class="container">
					
					<div class="col-md-12 col-xs-12 col-sm-12 padding-left price">
						<div class="col-md-12 col-xs-12 col-sm-12 text-center priceTitle">报价结果</div>
						<div class="col-md-12 col-xs-12 col-sm-12 text-center priceContent">
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 priceDiv"><div class="col-md-6 text-right">产品类型：</div><div class="col-md-6 text-left price_style">Android APP</div></div>
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 priceDiv"><div class="col-md-6 text-right">产品领域：</div><div class="col-md-6 text-left price_style">其他</div></div>
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 priceDiv"><div class="col-md-6 text-right">产品规模：</div><div class="col-md-6 text-left price_style">大型</div></div>
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 priceDiv"><div class="col-md-6 text-right">开发周期：</div><div class="col-md-6 text-left"><span class="price_style">51</span> 天</div></div>
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 priceDiv"><div class="col-md-6 text-right">报价：</div><div class="col-md-6 text-left"><span class="price_style">18,600</span> - <span class="price_style">2,5400</span></div></div>
							<div class="col-md-10 col-xs-10 col-sm-10 col-md-offset-1 col-sm-offset-1 col-xs-offset-1 priceMsg">
								<div class="col-md-12 col-xs-12 col-sm-12 text-center">
									提示：智能报价结果仅供参考，发布后码客帮顾问会给您提供更多的报价详情
								</div>							
							</div>
						</div>
					</div>
					
					<div id="baseFunction" class="col-md-12 col-xs-12 col-sm-12 baseFunction padding-left">
						<div class="tableTile col-md-12 col-sm-12 col-xs-12">基础功能</div>
						<div class="tableTh col-md-12 col-sm-12 col-xs-12 padding">
							<div class="col-md-2">模块</div>
							<div class="col-md-3">功能点</div>
							<div class="col-md-3">工程量</div>
							<div class="col-md-4">描述</div>
						</div>
						<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
							<div class="tableTd tableTd_title col-md-2 padding">
								<div class="selectAll">
									<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
									<div>用户管理</div>
								</div>							
							</div>
							<div class="tableTd col-md-3 padding">
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>用户信息查看</div>
									</div>
								</div>
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>用户审核</div>	
									</div>
								</div>
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>平台角色管理</div>	
									</div>
								</div>
							</div>
							<div class="tableTd col-md-3 padding">
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
							</div>
							<div class="tableTd col-md-4 padding">
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>用户基本信息查看，新增修改删除</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>用户审核</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>平台角色管理</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
							<div class="tableTd tableTd_title col-md-2 padding">
								<div class="selectAll">
									<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
									<div>权限管理</div>
								</div>							
							</div>
							<div class="tableTd col-md-3 padding">
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>功能权限管理</div>
									</div>
								</div>
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>权限和角色分组</div>	
									</div>
								</div>
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>站内指定信息发送</div>	
									</div>
								</div>
							</div>
							<div class="tableTd col-md-3 padding">
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
							</div>
							<div class="tableTd col-md-4 padding">
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>功能权限管理</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>权限和角色分组</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>站内指定信息发送</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
							<div class="tableTd tableTd_title col-md-2 padding">
								<div class="selectAll">
									<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
									<div>消息管理</div>
								</div>							
							</div>
							<div class="tableTd col-md-3 padding">
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>邮件群发</div>
									</div>
								</div>
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>用户审核</div>	
									</div>
								</div>
							</div>
							<div class="tableTd col-md-3 padding">
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
							</div>
							<div class="tableTd col-md-4 padding">
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>邮件群发</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>用户审核</div>
									</div>
								</div>
							</div>
						</div>
						<div class="tableTr col-md-12 col-sm-12 col-xs-12 text-right sum">小计：<span>100</span> 元</div>
					</div>
					
					<div id="other" class="col-md-12 col-xs-12 col-sm-12 other padding-left">
						<div class="tableTile col-md-12 col-sm-12 col-xs-12">其他</div>
						<div class="tableTh col-md-12 col-sm-12 col-xs-12 padding">
							<div class="col-md-2">模块</div>
							<div class="col-md-3">功能点</div>
							<div class="col-md-3">工程量</div>
							<div class="col-md-4">描述</div>
						</div>
						<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
							<div class="tableTd tableTd_title col-md-2 padding">
								<div class="selectAll">
									<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
									<div>部署</div>
								</div>							
							</div>
							<div class="tableTd col-md-3 padding">
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>应用部署上线</div>
									</div>
								</div>
							</div>
							<div class="tableTd col-md-3 padding">
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
							</div>
							<div class="tableTd col-md-4 padding">
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>包括域名配置支持、服务器环境配置、应用部署上线调试</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
							<div class="tableTd tableTd_title col-md-2 padding">
								<div class="selectAll">
									<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
									<div>兼容性</div>
								</div>							
							</div>
							<div class="tableTd col-md-3 padding">
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>桌面端分析支持</div>
									</div>
								</div>
								<div class="tableTr radioDiv col-md-12 col-sm-12 col-xs-12 padding">
									<div class="radio">
										<div class="checkBoxBtn"><img src="${store_location}/img/checkboxchecked.png"/></div>
										<div>WEB移动端应用</div>	
									</div>
								</div>
							</div>
							<div class="tableTd col-md-3 padding">
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="number">
										<div>￥500~￥1000 (2~3天)</div>
									</div>
								</div>
							</div>
							<div class="tableTd col-md-4 padding">
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>桌面自适应</div>
									</div>
								</div>
								<div class="tableTr col-md-12 col-sm-12 col-xs-12 padding">
									<div class="describe">
										<div>移动端自适应</div>
									</div>
								</div>
							</div>
						</div>
						<div class="tableTr col-md-12 col-sm-12 col-xs-12 text-right sum">小计：<span>100</span> 元</div>
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
			<div id="top"></div>
			<!--end of help docker-->

			<!--CNZZ CODE-->
			<!--${cnzz_html}-->
			<!--END OF CNZZ CODE-->
	</body>

</html>