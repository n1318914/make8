package com.yundaren.basedata.vo;

import java.util.List;

import lombok.Data;

@Data
public class ProvinceVo extends RegionVo2 {
	private List<CityVo> cities;
}
