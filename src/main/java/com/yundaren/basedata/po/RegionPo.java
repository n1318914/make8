package com.yundaren.basedata.po;

import java.util.Date;

import lombok.Data;

@Data
public class RegionPo {
	private long id;
	private long parentId;
	private String name;
}
