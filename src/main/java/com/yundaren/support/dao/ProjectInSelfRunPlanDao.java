package com.yundaren.support.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yundaren.support.po.ProjectInSelfRunMonitorPo;
import com.yundaren.support.po.ProjectInSelfRunPlanPo;

public class ProjectInSelfRunPlanDao extends SqlSessionDaoSupport {
	
	public int saveProjectPlan(ProjectInSelfRunPlanPo projectInSelfRunPlanPo) {	
		Integer newStepId = getSqlSession().selectOne("getNewStepId",projectInSelfRunPlanPo.getProjectId());
		newStepId = newStepId==null?1:newStepId;
		projectInSelfRunPlanPo.setStepId(newStepId);
		int effectedRows = getSqlSession().insert("saveProjectPlan", projectInSelfRunPlanPo);	
		
		if(effectedRows > 0){
			return newStepId;
		}
		
		return 0;
	}
	
	public List<ProjectInSelfRunPlanPo> loadProjectPlanListByPId(String projectId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		return 	getSqlSession().selectList("getProjectPlanByPId", paramsMap);
	}
	
	public int deleteProjectPlanByStepId(String projectId,String stepId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("stepId", stepId);
		return getSqlSession().delete("deleteProjectPlanByStepId", paramsMap);
	}
	
	public int updateProjectPlan(ProjectInSelfRunPlanPo projectInSelfRunPlanPo){
		return getSqlSession().update("updateProjectPlanByStepId", projectInSelfRunPlanPo);
	}
	
	public int saveProjectMoniterServicer(ProjectInSelfRunMonitorPo projectInSelfRunMonitorPo) {	
		Integer newStepId = getSqlSession().selectOne("getNewMonitorStepId",projectInSelfRunMonitorPo.getProjectId());
		newStepId = newStepId==null?1:newStepId;
		projectInSelfRunMonitorPo.setStepId(newStepId);
		int effectedRows = getSqlSession().insert("saveProjectMonitor", projectInSelfRunMonitorPo);	
		
		if(effectedRows > 0){
			return newStepId;
		}
		
		return 0;
	}
	
	public List<ProjectInSelfRunMonitorPo> loadProjectMonitorListByPId(String projectId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		return 	getSqlSession().selectList("getProjectMonitorByPId", paramsMap);
	}	
	
	public int deleteProjectMonitorByStepId(String projectId,String stepId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("stepId", stepId);
		return getSqlSession().delete("deleteProjectMonitorByStepId", paramsMap);
	}
	
	public int updateProjectMonitor(ProjectInSelfRunMonitorPo projectInSelfRunMonitorPo){
		return getSqlSession().update("updateProjectMonitorByStepId", projectInSelfRunMonitorPo);
	}
	
    public ProjectInSelfRunMonitorPo getProjectMonitorByPidAndStepId(String projectId,int stepId) {
    	Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("stepId", stepId);
    	return getSqlSession().selectOne("getProjectMonitorByPidAndStepId", paramsMap);
	}
    
    public ProjectInSelfRunPlanPo getProjectPlanByProjectIdAndStepId(String projectId,int stepId){
    	Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("stepId", stepId);
    	return getSqlSession().selectOne("getProjectPlanByProjectIdAndStepId", paramsMap);
    }

	public String getCreatorIdByProjectIdAndStepId(String projectId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		return getSqlSession().selectOne("getCreatorIdByProjectIdAndStepId", paramsMap);
	}

	public String getExcutorIdByProjectIdAndStepId(String projectId,String stepId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("stepId", stepId);
		return getSqlSession().selectOne("getExcutorIdByProjectIdAndStepId", paramsMap);
	}
}
