package com.yundaren.seo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.filter.handler.cache.RedisCacheable;
import com.yundaren.seo.po.SeoItemPo;

public class SeoDao extends SqlSessionDaoSupport {

	@RedisCacheable(key = "getSeoItemById", field = "#itemId")
	public SeoItemPo getSeoItemById(long itemId) {
		return getSqlSession().selectOne("getSeoItemById", itemId);
	}

	@RedisCacheable(key = "getSeoItemByRangId", field = "#startId+''+#endId")
	public List<SeoItemPo> getSeoItemByRangId(long startId, long endId) {
		Map paraMap = new HashMap();
		paraMap.put("startId", startId);
		paraMap.put("endId", endId);

		return getSqlSession().selectList("getSeoItemByRangId", paraMap);
	}

	public int getAllSeoItemCount() {
		return getSqlSession().selectOne("getAllSeoItemCount");
	}

	@RedisCacheable(key = "getAllSeoItems")
	public List<SeoItemPo> getAllSeoItems() {
		return getSqlSession().selectList("getAllSeoItems");
	}

	public long addSeoItem(SeoItemPo seoItem) {
		getSqlSession().insert("addSeoItem", seoItem);

		return seoItem.getId();
	}
}
