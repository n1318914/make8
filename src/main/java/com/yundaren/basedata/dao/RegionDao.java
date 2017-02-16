package com.yundaren.basedata.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.basedata.po.CityPo;
import com.yundaren.basedata.po.DistrictPo;
import com.yundaren.basedata.po.ProvincePo;
import com.yundaren.basedata.po.RegionPo;
import com.yundaren.filter.handler.cache.RedisCacheable;

public class RegionDao extends SqlSessionDaoSupport {

	@RedisCacheable(key = "getAllProvinces")
	public List<RegionPo> getAllProvinces() {
		return getSqlSession().selectList("getAllProvinces");
	}

	@RedisCacheable(key = "getCitysByProvinceId", field = "#provinceId")
	public List<RegionPo> getCitysByProvinceId(long provinceId) {
		return getSqlSession().selectList("getCitysByProvinceId", provinceId);
	}

	@RedisCacheable(key = "getAllProvinces2")
	public List<ProvincePo> getAllProvinces2() {
		return getSqlSession().selectList("getAllProvinces2");
	}

	@RedisCacheable(key = "getProvinceById", field = "#provinceId")
	public ProvincePo getProvinceById(long provinceId) {
		return getSqlSession().selectOne("getProviceById", provinceId);
	}

	@RedisCacheable(key = "getCityById", field = "#cityId")
	public CityPo getCityById(long cityId) {
		return getSqlSession().selectOne("getCityById", cityId);
	}

	@RedisCacheable(key = "getCitiesByProvinceId", field = "#provinceId")
	public List<CityPo> getCitiesByProvinceId(long provinceId) {
		return getSqlSession().selectList("getCitiesByProvinceId", provinceId);
	}

	@RedisCacheable(key = "getAllCities")
	public List<CityPo> getAllCities() {
		return getSqlSession().selectOne("getAllCities");
	}

	@RedisCacheable(key = "getDistrictById", field = "#districtId")
	public DistrictPo getDistrictById(long districtId) {
		return getSqlSession().selectOne("getDistrictById", districtId);
	}

	@RedisCacheable(key = "getDistrictsByCityId", field = "#cityId")
	public List<DistrictPo> getDistrictsByCityId(long cityId) {
		return getSqlSession().selectList("getDistrictsByCityId", cityId);
	}

	@RedisCacheable(key = "getAllDistricts")
	public List<DistrictPo> getAllDistricts() {
		return getSqlSession().selectList("getAllDistricts");
	}

	@RedisCacheable(key = "getProvinceByCityId", field = "#cityId")
	public ProvincePo getProvinceByCityId(long cityId) {
		return getSqlSession().selectOne("getProvinceByCityId", cityId);
	}

	@RedisCacheable(key = "getCityByDistrictId", field = "#districtId")
	public CityPo getCityByDistrictId(long districtId) {
		return getSqlSession().selectOne("getCityByDistrictId", districtId);
	}

	@RedisCacheable(key = "getListAll")
	public List<RegionPo> getListAll() {
		return getSqlSession().selectList("getListAll");
	}

}
