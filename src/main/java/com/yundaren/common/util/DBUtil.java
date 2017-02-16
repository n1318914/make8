package com.yundaren.common.util;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.support.SqlSessionDaoSupport;

@Slf4j
public class DBUtil extends SqlSessionDaoSupport {

	public void excuteSql(String sql) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sql", sql);
		getSqlSession().selectList("doExceuteSql", paramMap);
	}

}
