package com.yundaren.basedata.po;

import lombok.Data;

@Data
public class FlinkPo {
	private int id;
	private String name;
	private String link;
	private String logo;
	private int ranking = -1;
}
