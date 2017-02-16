package com.yundaren.support.po.evaluate;

import lombok.Data;

@Data
public class EvaluateModulePo {

	// ID
	private int id;
	// 名称
	private String name;
	// 是否停靠栏展示，0不停靠 1停靠
	private int isDock;
}
