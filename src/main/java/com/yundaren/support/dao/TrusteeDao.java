package com.yundaren.support.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.support.po.TrusteeInfoPo;

public class TrusteeDao extends SqlSessionDaoSupport {

	public long addTrusteeInfo(TrusteeInfoPo trusteePo) {
		return getSqlSession().insert("addTrusteeInfo", trusteePo);
	}

	public List<TrusteeInfoPo> getTrusteeInfoListByPID(String pid) {
		return getSqlSession().selectList("getTrusteeInfoListByPID", pid);
	}
}
