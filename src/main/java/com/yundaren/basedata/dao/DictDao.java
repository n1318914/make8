package com.yundaren.basedata.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.basedata.po.DictItemPo;
import com.yundaren.common.util.RedisDaoUtil;
import com.yundaren.filter.handler.cache.RedisCacheable;

public class DictDao extends SqlSessionDaoSupport {

	@RedisCacheable(key = "getAllDictItem")
	public List<DictItemPo> getAllDictItem() {
		return getSqlSession().selectList("getAllDictItem");
	}
}
