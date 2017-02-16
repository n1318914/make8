package com.yundaren.seo.service;

import java.util.List;
import com.yundaren.seo.vo.SeoItemVo;

public interface SeoService {
	public SeoItemVo getSeoItemById(long itemId);
	public List<SeoItemVo> getSeoItemByRangId(long startId,long endId);
	public int getAllSeoItemCount();
	public List<SeoItemVo> getSeoItemsByProvince(long provinceId,int index);
	public List<SeoItemVo> getSeoItemsByCity(long cityId,int index);
	public List<SeoItemVo> getSeoItemsByDistrict(long districtId,int index);
	public List<SeoItemVo> getAllSeoItems();
	public long addSeoItem(SeoItemVo seoItemVo);
}
