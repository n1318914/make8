package com.yundaren.basedata.vo;

import lombok.Data;

@Data
public class FlinkVo {
	private int id;
	private String name;
	private String link;
	private String logo;
	private int ranking = -1;
}
