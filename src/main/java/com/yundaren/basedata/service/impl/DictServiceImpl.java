package com.yundaren.basedata.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import lombok.Setter;

import com.yundaren.basedata.biz.DictBiz;
import com.yundaren.basedata.biz.RegionBiz;
import com.yundaren.basedata.dao.DictDao;
import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.service.RegionService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.basedata.vo.RegionVo;

public class DictServiceImpl implements DictService {

	@Setter
	private DictBiz dictBiz;

	@Override
	public List<DictItemVo> getAllDictItem() {
		return dictBiz.getAllDictItem();
	}
	
	
	@Override
	public List<DictItemVo> getDictItemByType(String type){
		List<DictItemVo> itemVoList = new ArrayList<DictItemVo>();
		
		List<DictItemVo> allItemVos = getAllDictItem();
		
		for(DictItemVo item : allItemVos){
			if(type.equalsIgnoreCase(item.getType())){
				itemVoList.add(item);
			}
		}
		
		return itemVoList;
	}
}
