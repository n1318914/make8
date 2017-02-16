package com.yundaren.support.po.evaluate;

import lombok.Data;

@Data
public class EvaluateGroupPo {

	// ID
	private int id;
	// 模块名
	private String name;
	// 领域ID
	private int moduleId;
	// 在什么产品类型下显示(all、android、ios、web、weixin)，多个用逗号分隔
	private String showOn;
	// 是否单选，0多选 1单选
	private int isRadio;
}
