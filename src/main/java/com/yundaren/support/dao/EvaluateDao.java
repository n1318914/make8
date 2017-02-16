package com.yundaren.support.dao;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.po.evaluate.EvaluateGroupPo;
import com.yundaren.support.po.evaluate.EvaluateItemPo;
import com.yundaren.support.po.evaluate.EvaluateModulePo;
import com.yundaren.support.po.evaluate.EvaluateSimilarPo;
import com.yundaren.support.vo.evaluate.EvaluateSimilarVo;

@Slf4j
public class EvaluateDao extends SqlSessionDaoSupport {

	public List<EvaluateModulePo> getAllModule() {
		return getSqlSession().selectList("getAllModule");
	}

	public List<EvaluateGroupPo> getAllGroup() {
		return getSqlSession().selectList("getAllGroup");
	}

	public List<EvaluateItemPo> getAllItem() {
		return getSqlSession().selectList("getAllItem");
	}
	
	public List<EvaluateSimilarPo> getAllEvaluateSimilar() {
		return getSqlSession().selectList("getAllEvaluateSimilar", DomainConfig.getHost());
	}

}
