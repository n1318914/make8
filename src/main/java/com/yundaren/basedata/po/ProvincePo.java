package com.yundaren.basedata.po;

import java.util.List;

import lombok.Data;

@Data
public class ProvincePo {
	private long id;
	private String name;
	private List<CityPo> cities;
}
