package com.yundaren.seo.service.impl;

import java.util.List;
import lombok.Setter;
import com.yundaren.seo.biz.SeoBiz;
import com.yundaren.seo.service.SeoService;
import com.yundaren.seo.vo.SeoItemVo;

public class SeoServiceImpl implements SeoService{
	@Setter
	private SeoBiz seoBiz;
	
	@Override
	public SeoItemVo getSeoItemById(long itemId) {
		return seoBiz.getSeoItemById(itemId);
	}
	
	@Override
	public List<SeoItemVo> getSeoItemByRangId(long startId, long endId) {
		return seoBiz.getSeoItemByRangId(startId, endId);
	}
	
	@Override
	public int getAllSeoItemCount() {
		return seoBiz.getAllSeoItemCount();
	}

	@Override
	public List<SeoItemVo> getSeoItemsByProvince(long provinceId, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SeoItemVo> getSeoItemsByCity(long cityId, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SeoItemVo> getSeoItemsByDistrict(long districtId, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SeoItemVo> getAllSeoItems() {
		return seoBiz.getAllSeoItems();
	}

	@Override
	public long addSeoItem(SeoItemVo seoItemVo){
		return seoBiz.addSeoItem(seoItemVo);
	}
	
}
