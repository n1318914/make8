package com.yundaren.basedata.vo;

import java.util.Date;

import lombok.Data;

@Data
public class DictItemVo {
	private long dictGroupId;
	private int value;
	private String name;
	
	private String type;
}
