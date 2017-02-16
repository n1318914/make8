package com.yundaren.basedata.po;

import java.util.Date;

import lombok.Data;

@Data
public class DictItemPo {
	private long dictGroupId;
	private int value;
	private String name;

	private String type;
}
