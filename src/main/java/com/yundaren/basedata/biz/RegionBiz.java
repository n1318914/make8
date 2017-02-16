package com.yundaren.basedata.biz;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.basedata.dao.RegionDao;
import com.yundaren.basedata.po.CityPo;
import com.yundaren.basedata.po.DistrictPo;
import com.yundaren.basedata.po.ProvincePo;
import com.yundaren.basedata.po.RegionPo;
import com.yundaren.basedata.vo.CityVo;
import com.yundaren.basedata.vo.DistrictVo;
import com.yundaren.basedata.vo.ProvinceVo;
import com.yundaren.basedata.vo.RegionVo;
import com.yundaren.basedata.vo.RegionVo2;

public class RegionBiz {

	@Setter
	private RegionDao regionDao;

	public List<RegionVo> getAllProvinces() {
		List<RegionVo> listRegionVo = new ArrayList<RegionVo>();
		List<RegionPo> listRegionPo = regionDao.getAllProvinces();
		if (!CollectionUtils.isEmpty(listRegionPo)) {
			listRegionVo = BeanMapper.mapList(listRegionPo, RegionVo.class);
		}
		return listRegionVo;
	}

	public List<RegionVo> getCitysByProvinceId(long provinceId) {
		List<RegionVo> listRegionVo = new ArrayList<RegionVo>();
		List<RegionPo> listRegionPo = regionDao.getCitysByProvinceId(provinceId);
		if (!CollectionUtils.isEmpty(listRegionPo)) {
			listRegionVo = BeanMapper.mapList(listRegionPo, RegionVo.class);
		}
		return listRegionVo;
	}
	
	public List<ProvinceVo> getAllProvinces2(){
		List<ProvinceVo> provinceVoList = new ArrayList<ProvinceVo>();
		List<ProvincePo> provincePoList = regionDao.getAllProvinces2();
		
		if(!CollectionUtils.isEmpty(provincePoList)){
			provinceVoList = BeanMapper.mapList(provincePoList, ProvinceVo.class);
		}
		
		return provinceVoList;
	}
	
	public ProvinceVo getProvinceById(long provinceId){
		ProvinceVo provinceVo = null;
		
		ProvincePo provincePo = regionDao.getProvinceById(provinceId);
		
		if(provincePo != null){
			provinceVo = BeanMapper.map(provincePo, ProvinceVo.class);
		}
		
		return provinceVo;
	}
	
	public CityVo getCityById(long cityId){
		CityVo cityVo = null;
		
		CityPo cityPo = regionDao.getCityById(cityId);
		
		if(cityPo != null){
			cityVo = BeanMapper.map(cityPo,CityVo.class);
		}
		
		return cityVo;
	}
	
	public List<CityVo> getCitiesByProvinceId(long provinceId){
		List<CityVo> cityVoList = new ArrayList<CityVo>();
		
		List<CityPo> cityPoList = regionDao.getCitiesByProvinceId(provinceId);
		
		if(!CollectionUtils.isEmpty(cityPoList)){
			cityVoList = BeanMapper.mapList(cityPoList, CityVo.class);
		}
		
		return cityVoList;
		
	}
	
	public List<CityVo> getAllCities(){
		List<CityVo> cityVoList = new ArrayList<CityVo>();
		
		List<CityPo> cityPoList = regionDao.getAllCities();
		
		if(!CollectionUtils.isEmpty(cityPoList)){
			cityVoList = BeanMapper.mapList(cityPoList, CityVo.class);
		}
		
		return cityVoList;
		
	}
	
	public DistrictVo getDistrictById(long districtId){
		DistrictVo districtVo = null;
		
		DistrictPo districtPo = regionDao.getDistrictById(districtId);
		
		if(districtPo != null){
			districtVo = BeanMapper.map(districtPo, DistrictVo.class);
		}
		
		return districtVo;
	}
	
	public List<DistrictVo> getDistrictsByCityId(long cityId){
		List<DistrictVo> districtVoList = new ArrayList<DistrictVo>();
		
		List<DistrictPo> districtPoList = regionDao.getDistrictsByCityId(cityId);
		
		if(districtPoList != null){
			districtVoList = BeanMapper.mapList(districtPoList, DistrictVo.class);
		}
		
		return districtVoList;
	}
	
	public List<DistrictVo> getAllDistricts(){
		List<DistrictVo> districtVoList = new ArrayList<DistrictVo>();
		
		List<DistrictPo> districtPoList = regionDao.getAllDistricts();
		
		if(districtPoList != null){
			districtVoList = BeanMapper.mapList(districtPoList, DistrictVo.class);
		}
		
		return districtVoList;
	}
	
	public ProvinceVo getProvinceByCityId(long cityId){
		ProvinceVo provinceVo = null;
		ProvincePo provincePo = regionDao.getProvinceByCityId(cityId);
		
		if(provincePo != null){
			provinceVo = BeanMapper.map(provincePo, ProvinceVo.class);
		}
		
		return provinceVo;
	}
	
	public CityVo getCityByDistrictId(long districtId){
		CityVo cityVo = null;
		CityPo cityPo = regionDao.getCityByDistrictId(districtId);
		
		if(cityPo != null){
			cityVo = BeanMapper.map(cityPo,CityVo.class);
		}
		
		return cityVo;
	}

	
	public List<RegionVo> getListAll() {
		List<RegionVo> listRegionVo = new ArrayList<RegionVo>();
		List<RegionPo> listRegionPo = regionDao.getListAll();
		if (!CollectionUtils.isEmpty(listRegionPo)) {
			listRegionVo = BeanMapper.mapList(listRegionPo, RegionVo.class);
		}
		return listRegionVo;
	}

}
