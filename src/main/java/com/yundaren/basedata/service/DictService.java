package com.yundaren.basedata.service;

import java.util.List;

import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.basedata.vo.RegionVo;

/**
 * @author kai.xu 数据字典
 */
public interface DictService {

	public List<DictItemVo> getAllDictItem();
	public List<DictItemVo> getDictItemByType(String type);
}
