package com.yundaren.support.service;

import java.util.List;

import com.yundaren.support.po.ProjectInSelfRunMonitorPo;
import com.yundaren.support.po.ProjectInSelfRunPo;
import com.yundaren.support.vo.ProjectInSelfRunMonitorVo;
import com.yundaren.support.vo.ProjectInSelfRunPlanVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;

public interface ProjectInSelfRunPlanService {
	/**保存项目计划**/
	int saveProjectPlan(ProjectInSelfRunPlanVo projectInSelfRunPlanVo);
	
	List<ProjectInSelfRunPlanVo> loadProjectPlanListByPId(String projectId);
	
	int deleteProjectPlanByStepId(String projectId,String stepId);
	
	int updateProjectPlan(ProjectInSelfRunPlanVo projectInSelfRunPlanVo);
	
	int saveProjectMoniterServicer(ProjectInSelfRunMonitorVo projectInSelfRunMonitorVo);
	
	List<ProjectInSelfRunMonitorVo> loadProjectMonitorListByPId(String projectId);
	
	int deleteProjectMonitorByStepId(String projectId,String stepId);
	
    int updateProjectMonitor(ProjectInSelfRunMonitorVo projectInSelfRunMonitorVo);
    ProjectInSelfRunMonitorVo getProjectMonitorByPidAndStepId(String projectId,int stepId);
    ProjectInSelfRunPlanVo getProjectPlanByProjectIdAndStepId(String projectId,int stepId);
    
    /**
     * 获得项目计划 阶段中的雇主信息
     * @param projectId  项目id
     * @param stepId  计划阶段id
     * @return
     */
    String getCreatorIdByProjectIdAndStepId(String projectId);
    /**
     * 获得项目计划 阶段中的开发者信息
     * @param projectId  项目id
     * @param stepId  计划阶段id
     * @return
     */
    String getExcutorIdByProjectIdAndStepId(String projectId,String stepId);
    
}
