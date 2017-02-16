package com.yundaren.basedata.service;

import java.util.List;

import com.yundaren.basedata.vo.CityVo;
import com.yundaren.basedata.vo.DistrictVo;
import com.yundaren.basedata.vo.ProvinceVo;
import com.yundaren.basedata.vo.RegionVo;
import com.yundaren.basedata.vo.RegionVo2;

/**
 * 
 * @author kai.xu
 * 区域接口
 */
public interface RegionService {

	public List<RegionVo> getAllProvinces();
	
	public List<RegionVo> getCitysByProvinceId(long provinceId);

	
	//新增接口，2016-04-07 by Peton
	public List<ProvinceVo> getAllProvinces2();
	public ProvinceVo getProvinceById(long provinceId);
	public CityVo getCityById(long cityId);
	public List<CityVo> getCitiesByProvinceId(long provinceId);
	public List<CityVo> getAllCities();
	public DistrictVo getDistrictById(long districtId);
	public List<DistrictVo> getDistrictsByCityId(long cityId);
	public List<DistrictVo> getAllDistricts();
	public ProvinceVo getProvinceByCityId(long cityId);
	public CityVo getCityByDistricId(long districtId);
	public List<RegionVo> getListAll();

}
