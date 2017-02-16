package com.yundaren.support.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.support.po.ProjectEvaluatePo;

public class ProjectEvaluateDao extends SqlSessionDaoSupport {

	public long addProjectEvaluate(ProjectEvaluatePo evaluatePo) {
		return getSqlSession().insert("addProjectEvaluate", evaluatePo);
	}

	public List<ProjectEvaluatePo> getEvaluateListByUID(long uid) {
		return getSqlSession().selectList("getEvaluateListByUID", uid);
	}

	public ProjectEvaluatePo getEvaluateByPID(String pid) {
		return getSqlSession().selectOne("getEvaluateByPID", pid);
	}
}
