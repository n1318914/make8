package com.yundaren.support.po.evaluate;

import lombok.Data;

@Data
public class EvaluateItemPo {

	// ID
	private int id;
	// 名称
	private String name;
	// 模块ID
	private int groupId;
	// 在什么产品类型下显示(all、android、ios、web、weixin)，多个用逗号分隔
	private String showOn;
	// 价格范围(100-1000)，范围用`-`号分隔
	private String defaultPrice;
	// 周期范围(1-10)，范围用`-`号分隔
	private String defaultPeroid;
	// 安卓价格周期系数
	private float androidRate;
	// IOS价格周期系数
	private float iosRate;
	// 网站价格周期系数
	private float webRate;
	// 微信价格周期系数
	private float weixinRate;
	// 备注
	private String remark;
}
