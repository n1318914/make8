package com.yundaren.basedata.service.impl;

import java.util.List;

import lombok.Setter;

import com.yundaren.basedata.biz.RegionBiz;
import com.yundaren.basedata.service.RegionService;
import com.yundaren.basedata.vo.CityVo;
import com.yundaren.basedata.vo.DistrictVo;
import com.yundaren.basedata.vo.ProvinceVo;
import com.yundaren.basedata.vo.RegionVo;
import com.yundaren.basedata.vo.RegionVo2;

public class RegionServiceImpl implements RegionService {

	@Setter
	private RegionBiz regionBiz;

	@Override
	public List<RegionVo> getAllProvinces() {
		return regionBiz.getAllProvinces();
	}

	@Override
	public List<RegionVo> getCitysByProvinceId(long provinceId) {
		return regionBiz.getCitysByProvinceId(provinceId);
	}
	
	@Override
	public List<ProvinceVo> getAllProvinces2() {
		return regionBiz.getAllProvinces2();
	}

	@Override
	public ProvinceVo getProvinceById(long provinceId) {
		return regionBiz.getProvinceById(provinceId);
	}

	@Override
	public CityVo getCityById(long cityId) {
		return regionBiz.getCityById(cityId);
	}

	@Override
	public List<CityVo> getCitiesByProvinceId(long provinceId) {
		return regionBiz.getCitiesByProvinceId(provinceId);
	}

	@Override
	public List<CityVo> getAllCities() {
		return regionBiz.getAllCities();
	}

	@Override
	public DistrictVo getDistrictById(long districtId) {
		return regionBiz.getDistrictById(districtId);
	}

	@Override
	public List<DistrictVo> getDistrictsByCityId(long cityId) {
		return regionBiz.getDistrictsByCityId(cityId);
	}

	@Override
	public List<DistrictVo> getAllDistricts() {
		return regionBiz.getAllDistricts();
	}

	@Override
	public ProvinceVo getProvinceByCityId(long cityId) {
		return regionBiz.getProvinceByCityId(cityId);
	}

	@Override
	public CityVo getCityByDistricId(long districtId) {
		return regionBiz.getCityByDistrictId(districtId);
	}
	
	@Override
	public List<RegionVo> getListAll() {
		return regionBiz.getListAll();
	}

}
