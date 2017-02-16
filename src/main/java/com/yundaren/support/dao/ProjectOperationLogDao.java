package com.yundaren.support.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.support.po.ProjectOperationLogPo;


public class ProjectOperationLogDao extends SqlSessionDaoSupport  {
	public long addProjectOperationLog(ProjectOperationLogPo logPo){
		getSqlSession().insert("addProjectOperationLog", logPo); 
		return logPo.getId();
	}
	
	public int updateProjectOperationLog(ProjectOperationLogPo logPo){
		return getSqlSession().update("updateProjectOperationLog", logPo); 
	}
	
	public int deleteProjectOperationLog(long logId){
		return getSqlSession().delete("deleteProjectOperationLog", logId); 
	}
	
	public List<ProjectOperationLogPo> getProjectOperationLogByProjectId(long projectId){
		return getSqlSession().selectList("getProjectOperationLogByProjectId",projectId);
	}
	
	public ProjectOperationLogPo getProjectOperationLogById(long logId){
		return getSqlSession().selectOne("getProjectOperationLogById",logId);
	}
}
