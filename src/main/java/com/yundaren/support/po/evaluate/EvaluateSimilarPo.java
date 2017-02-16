package com.yundaren.support.po.evaluate;

import lombok.Data;

@Data
public class EvaluateSimilarPo {

	// ID
	private int id;
	// 名称
	private String name;
	// 描述
	private String description;
	// 封面图片URL
	private String frontImg;
	// 超链接
	private String link;
	// 项目类型(iOS、Andorid、weixin、web)
	private String type;
	// 价格 (0:小于1000 1:1000-3000 2:3000-5000 3:5000-10000 4:10000-20000 5:20000-30000 6:30000-50000
	// 7:50000-10000 8:100000-200000 9:200000-500000 10:大于500000)
	private int price;
	private String descPrice;
	// 周期
	private String peroid;
	// 行业
	private String industry;
}
