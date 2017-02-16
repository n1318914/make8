package com.yundaren.support.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.support.po.TradeInfoPo;

public class TradeDao extends SqlSessionDaoSupport {

	public long addTradeInfo(TradeInfoPo tradePo) {
		return getSqlSession().insert("addTradeInfo", tradePo);
	}

	public TradeInfoPo getTradeInfoByPID(String pid) {
		return getSqlSession().selectOne("getTradeInfoByPID", pid);
	}
	
	public long updateTradeInfo(TradeInfoPo tradePo) {
		return getSqlSession().update("updateTradeInfo", tradePo);
	}
	
	public double getTotalProjectAmount() {
		return getSqlSession().selectOne("getTotalProjectAmount");
	}
	
	public List<String> getTransactionAmountList() {
		return getSqlSession().selectList("getTransactionAmountList");
	}
}
