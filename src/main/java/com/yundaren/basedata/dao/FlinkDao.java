package com.yundaren.basedata.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.basedata.po.FlinkPo;
import com.yundaren.filter.handler.cache.RedisCacheEvict;
import com.yundaren.filter.handler.cache.RedisCacheable;
import com.yundaren.support.config.DomainConfig;

public class FlinkDao extends SqlSessionDaoSupport {

	@RedisCacheable(key = "getAllFlink")
	public List<FlinkPo> getAllFlink() {
		String domian = DomainConfig.getBindDomain();
		return getSqlSession().selectList("getAllFlink", domian);
	}

	@RedisCacheEvict(key = "getAllFlink")
	public int addFlink(FlinkPo flinkPo) {
		getSqlSession().insert("addFlink", flinkPo);
		return flinkPo.getId();
	}

	@RedisCacheEvict(key = "getAllFlink")
	public int deleteFlink(int id) {
		return getSqlSession().delete("deleteFlink", id);
	}

	public int modifyFlink(FlinkPo flinkPo) {
		return getSqlSession().update("modifyFlink", flinkPo);
	}

}
