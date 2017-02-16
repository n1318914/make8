package com.yundaren.basedata.po;

import java.util.List;

import lombok.Data;

@Data
public class CityPo {
	private long id;
	private String name;
	private long provinceId;
	private List<RegionPo> districts;
}
