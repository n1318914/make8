package com.yundaren.support.service.impl;

import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.yundaren.support.biz.ProjectInSelfRunBiz;
import com.yundaren.support.biz.ProjectInSelfRunPlanBiz;
import com.yundaren.support.po.ProjectInSelfRunPlanPo;
import com.yundaren.support.po.ProjectInSelfRunPo;
import com.yundaren.support.service.ProjectInSelfRunPlanService;
import com.yundaren.support.vo.ProjectInSelfRunHandlerVo;
import com.yundaren.support.vo.ProjectInSelfRunMonitorVo;
import com.yundaren.support.vo.ProjectInSelfRunPlanVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;

@Slf4j
public class ProjectInSelfRunPlanServiceImpl implements ProjectInSelfRunPlanService{

	@Setter
	private ProjectInSelfRunPlanBiz projectInSelfRunPlanBiz;
	
	@Override
	public int saveProjectPlan(ProjectInSelfRunPlanVo projectInSelfRunPlanVo) {
		return projectInSelfRunPlanBiz.saveProjectPlan(projectInSelfRunPlanVo);
	}

	@Override
	public List<ProjectInSelfRunPlanVo> loadProjectPlanListByPId(String projectId) {
		return projectInSelfRunPlanBiz.loadProjectPlanListByPId(projectId);
	}

	@Override
	public int deleteProjectPlanByStepId(String projectId, String stepId) {
		return projectInSelfRunPlanBiz.deleteProjectPlanByStepId(projectId, stepId);
	}

	@Override
	public int updateProjectPlan(ProjectInSelfRunPlanVo projectInSelfRunPlanVo) {
		return projectInSelfRunPlanBiz.updateProjectPlan(projectInSelfRunPlanVo);
	}

	@Override
	public int saveProjectMoniterServicer(ProjectInSelfRunMonitorVo projectInSelfRunMonitorVo) {
		// TODO Auto-generated method stub
		return projectInSelfRunPlanBiz.saveProjectMoniterServicer(projectInSelfRunMonitorVo);
	}

	@Override
	public List<ProjectInSelfRunMonitorVo> loadProjectMonitorListByPId(String projectId) {
		// TODO Auto-generated method stub
		return projectInSelfRunPlanBiz.loadProjectMonitorListByPId(projectId);
	}

	@Override
	public int deleteProjectMonitorByStepId(String projectId, String stepId) {
		// TODO Auto-generated method stub
		return projectInSelfRunPlanBiz.deleteProjectMonitorByStepId(projectId, stepId);
	}

	@Override
	public int updateProjectMonitor(ProjectInSelfRunMonitorVo projectInSelfRunMonitorVo) {
		// TODO Auto-generated method stub
		return projectInSelfRunPlanBiz.updateProjectMonitor(projectInSelfRunMonitorVo);
	}

	@Override
	public ProjectInSelfRunMonitorVo getProjectMonitorByPidAndStepId(String projectId,int stepId) {
		return projectInSelfRunPlanBiz.getProjectMonitorByPidAndStepId(projectId, stepId);
	}

	@Override
	public ProjectInSelfRunPlanVo getProjectPlanByProjectIdAndStepId(String projectId,int stepId) {
		return projectInSelfRunPlanBiz.getProjectPlanByProjectIdAndStepId(projectId,stepId);
	}

	@Override
	public String getCreatorIdByProjectIdAndStepId(String projectId) {
		return projectInSelfRunPlanBiz.getCreatorIdByProjectIdAndStepId(projectId);
	}

	@Override
	public String getExcutorIdByProjectIdAndStepId(String projectId,String stepId) {
		return projectInSelfRunPlanBiz.getExcutorIdByProjectIdAndStepId(projectId,stepId);
	}
}
