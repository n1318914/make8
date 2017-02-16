package com.yundaren.basedata.vo;

import java.util.List;

import lombok.Data;


@Data
public class CityVo extends RegionVo2 {
	private long cityId;
	private List<DistrictVo> districts;
}
