package com.yundaren.seo.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import lombok.Setter;

import com.yundaren.seo.po.SeoItemPo;
import com.yundaren.seo.vo.SeoItemVo;
import com.yundaren.seo.dao.SeoDao;

public class SeoBiz {
	@Setter
	private SeoDao seoDao;
	
	public SeoItemVo getSeoItemById(long itemId){
		SeoItemVo seoItemVo = null;
		
		SeoItemPo seoItemPo = seoDao.getSeoItemById(itemId);
		
		if(seoItemPo != null){
			seoItemVo = BeanMapper.map(seoItemPo,SeoItemVo.class);
		}
		
		return seoItemVo;
	}
	
	public List<SeoItemVo> getSeoItemByRangId(long startId,long endId){
		List<SeoItemVo> seoItemVoList = new ArrayList<SeoItemVo>();
		
		List<SeoItemPo> seoItemPoList = seoDao.getSeoItemByRangId(startId, endId);
		
		if(!CollectionUtils.isEmpty(seoItemPoList)){
			seoItemVoList = BeanMapper.mapList(seoItemPoList, SeoItemVo.class);
		}
		
		return seoItemVoList;
	}
	
	public int getAllSeoItemCount(){
		return seoDao.getAllSeoItemCount();
	}
	
	public List<SeoItemVo> getAllSeoItems(){
		List<SeoItemVo> seoItemVoList = new ArrayList<SeoItemVo>();
		
		List<SeoItemPo> seoItemPoList = seoDao.getAllSeoItems();
		
		if(!CollectionUtils.isEmpty(seoItemPoList)){
			seoItemVoList = BeanMapper.mapList(seoItemPoList, SeoItemVo.class);
		}
		
		return seoItemVoList;
	}
	
	public long addSeoItem(SeoItemVo seoItemVo){
		SeoItemPo seoItemPo = BeanMapper.map(seoItemVo,SeoItemPo.class);
		
		return seoDao.addSeoItem(seoItemPo);
	}
}
