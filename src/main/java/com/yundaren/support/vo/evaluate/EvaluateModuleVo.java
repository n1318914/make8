package com.yundaren.support.vo.evaluate;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EvaluateModuleVo {

	// ID
	private int id;
	// 名称
	private String name;
	// 是否停靠栏展示，0不停靠 1停靠
	private int isDock;
	
	private List<EvaluateGroupVo> listEvaluateGroup = new ArrayList<EvaluateGroupVo>();
}
