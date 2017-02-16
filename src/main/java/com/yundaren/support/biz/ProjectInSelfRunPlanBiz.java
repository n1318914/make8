package com.yundaren.support.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.support.dao.ProjectInSelfRunPlanDao;
import com.yundaren.support.po.ProjectInSelfRunMonitorPo;
import com.yundaren.support.po.ProjectInSelfRunPlanPo;
import com.yundaren.support.vo.ProjectInSelfRunMonitorVo;
import com.yundaren.support.vo.ProjectInSelfRunPlanVo;

public class ProjectInSelfRunPlanBiz{
	@Setter
	private ProjectInSelfRunPlanDao projectInSelfRunPlanDao;
	
	public int saveProjectPlan(ProjectInSelfRunPlanVo projectInSelfRunPlanVo) {
		ProjectInSelfRunPlanPo projectInSelfRunPlanPo = BeanMapper.map(projectInSelfRunPlanVo, ProjectInSelfRunPlanPo.class);
		return projectInSelfRunPlanDao.saveProjectPlan(projectInSelfRunPlanPo);
	}
	
	public List<ProjectInSelfRunPlanVo> loadProjectPlanListByPId(String projectId){
		List<ProjectInSelfRunPlanVo> listPorjectPlanVo = new ArrayList<ProjectInSelfRunPlanVo>();
		List<ProjectInSelfRunPlanPo> listPorjectPlanPo = projectInSelfRunPlanDao.loadProjectPlanListByPId(projectId);

		if (!CollectionUtils.isEmpty(listPorjectPlanPo)) {
			listPorjectPlanVo = BeanMapper.mapList(listPorjectPlanPo, ProjectInSelfRunPlanVo.class);
		}
		return 	listPorjectPlanVo;
	}
	
	public int deleteProjectPlanByStepId(String projectId,String stepId){
		return projectInSelfRunPlanDao.deleteProjectPlanByStepId(projectId, stepId);
	}
	
	public int updateProjectPlan(ProjectInSelfRunPlanVo projectInSelfRunPlanVo){
		ProjectInSelfRunPlanPo projectInSelfRunPlanPo = BeanMapper.map(projectInSelfRunPlanVo, ProjectInSelfRunPlanPo.class);
		return projectInSelfRunPlanDao.updateProjectPlan(projectInSelfRunPlanPo);
	}
	
	public int saveProjectMoniterServicer(ProjectInSelfRunMonitorVo projectInSelfRunMonitorVo) {	
		ProjectInSelfRunMonitorPo projectInSelfRunMonitorPo = BeanMapper.map(projectInSelfRunMonitorVo, ProjectInSelfRunMonitorPo.class);
		return projectInSelfRunPlanDao.saveProjectMoniterServicer(projectInSelfRunMonitorPo);		
	}
	
	public List<ProjectInSelfRunMonitorVo> loadProjectMonitorListByPId(String projectId){
		List<ProjectInSelfRunMonitorVo> listPorjectPlanVo = new ArrayList<ProjectInSelfRunMonitorVo>();
		List<ProjectInSelfRunMonitorPo> listPorjectPlanPo = projectInSelfRunPlanDao.loadProjectMonitorListByPId(projectId);
		if (!CollectionUtils.isEmpty(listPorjectPlanPo)) {
			listPorjectPlanVo = BeanMapper.mapList(listPorjectPlanPo, ProjectInSelfRunMonitorVo.class);
		}
		return 	listPorjectPlanVo;
	}
	
	public int deleteProjectMonitorByStepId(String projectId,String stepId){
		return projectInSelfRunPlanDao.deleteProjectMonitorByStepId(projectId, stepId);
	}
	
	public int updateProjectMonitor(ProjectInSelfRunMonitorVo projectInSelfRunMonitorVo){
		ProjectInSelfRunMonitorPo projectInSelfRunMonitorPo = BeanMapper.map(projectInSelfRunMonitorVo, ProjectInSelfRunMonitorPo.class);
		return projectInSelfRunPlanDao.updateProjectMonitor(projectInSelfRunMonitorPo);
	}
	
	public ProjectInSelfRunMonitorVo getProjectMonitorByPidAndStepId(String projectId,int stepId) {
		ProjectInSelfRunMonitorPo projectInSelfRunMonitorPo = projectInSelfRunPlanDao.getProjectMonitorByPidAndStepId(projectId,stepId);
		return BeanMapper.map(projectInSelfRunMonitorPo,ProjectInSelfRunMonitorVo.class);	
	}
	
	public ProjectInSelfRunPlanVo getProjectPlanByProjectIdAndStepId(String projectId,int stepId){
		ProjectInSelfRunPlanPo planPo = projectInSelfRunPlanDao.getProjectPlanByProjectIdAndStepId(projectId,stepId);
		return BeanMapper.map(planPo, ProjectInSelfRunPlanVo.class);
	}

	public String getCreatorIdByProjectIdAndStepId(String projectId) {
		return projectInSelfRunPlanDao.getCreatorIdByProjectIdAndStepId(projectId);
	}

	public String getExcutorIdByProjectIdAndStepId(String projectId,String stepId) {
		return projectInSelfRunPlanDao.getExcutorIdByProjectIdAndStepId(projectId,stepId);
	}
}
