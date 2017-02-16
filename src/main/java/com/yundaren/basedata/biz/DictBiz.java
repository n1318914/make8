package com.yundaren.basedata.biz;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.basedata.dao.DictDao;
import com.yundaren.basedata.dao.RegionDao;
import com.yundaren.basedata.po.DictItemPo;
import com.yundaren.basedata.po.RegionPo;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.basedata.vo.RegionVo;

public class DictBiz {

	@Setter
	private DictDao dictDao;

	@Cacheable(value = "dict.cache")
	public List<DictItemVo> getAllDictItem() {
		List<DictItemVo> listDictVo = new ArrayList<DictItemVo>();
		List<DictItemPo> listDictPo = dictDao.getAllDictItem();
		if (!CollectionUtils.isEmpty(listDictPo)) {
			listDictVo = BeanMapper.mapList(listDictPo, DictItemVo.class);
		}
		return listDictVo;
	}
}
