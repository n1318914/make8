package com.yundaren.support.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.support.po.IdentifyPo;
import com.yundaren.support.vo.IdentifyVo;

public class IdentifyDao extends SqlSessionDaoSupport {

	public long addIdentify(IdentifyPo identifyPo) {
		return getSqlSession().insert("addIdentify", identifyPo);
	}

	public List<IdentifyPo> getAcceptIdentifyList() {
		return getSqlSession().selectList("getAcceptIdentifyList");
	}

	public int updateIdentifyByUID(IdentifyPo identifyPo) {
		return getSqlSession().update("updateIdentifyByUID", identifyPo);
	}

	public IdentifyPo getIdentifyByUID(long uid) {
		return getSqlSession().selectOne("getIdentifyByUID", uid);
	}

	public IdentifyPo getIdentifyByIDCard(String idCard) {
		return getSqlSession().selectOne("getIdentifyByIDCard", idCard);
	}

	public IdentifyPo getIdentifyByCName(String companyName) {
		return getSqlSession().selectOne("getIdentifyByCName", companyName);
	}
}
