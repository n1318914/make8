package com.yundaren.homepage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.homepage.po.CustomerCasePo;

public class HomePageDao extends SqlSessionDaoSupport {
	public List<CustomerCasePo> getCustomerCases(int type){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("type","%" + type + "%");
		
		return getSqlSession().selectList("getCustomerCases",paramsMap);
	}
	
	public CustomerCasePo getCustomerCaseByCaseId(long caseId){
		return getSqlSession().selectOne("getCustomerCaseByCaseId",caseId);
	}

}
