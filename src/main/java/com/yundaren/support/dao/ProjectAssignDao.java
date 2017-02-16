package com.yundaren.support.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.support.po.ProjectAssignPo;

public class ProjectAssignDao extends SqlSessionDaoSupport{
	
	public int addAssignmentRecord(ProjectAssignPo projectAssignPo){
		return getSqlSession().insert("addAssignRecord",projectAssignPo);
	}
	
	public List<ProjectAssignPo> getAssignRecord(){
		return getSqlSession().selectList("getAssignRecord");
	}
}
