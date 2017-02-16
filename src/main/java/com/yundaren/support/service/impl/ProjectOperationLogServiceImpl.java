package com.yundaren.support.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yundaren.common.constants.ProjectInSelfRunConstants;
import com.yundaren.common.constants.ProjectOperationLogPermissionEnum;
import com.yundaren.support.biz.ProjectInSelfRunBiz;
import com.yundaren.support.biz.ProjectOperationLogBiz;
import com.yundaren.support.service.ProjectOperationLogService;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.ProjectOperationLogVo;
import com.yundaren.user.biz.UserBiz;
import com.yundaren.user.vo.UserInfoVo;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProjectOperationLogServiceImpl implements ProjectOperationLogService {
	@Setter
	ProjectOperationLogBiz projectOperationLogBiz;
	@Setter
	UserBiz userBiz;
	@Setter
	ProjectInSelfRunBiz projectInSelfRunBiz;
	
	@Override
	public long addProjectOperationLog(ProjectOperationLogVo logVo) {
		return projectOperationLogBiz.addProjectOperationLog(logVo);
	}
	
	@Override
	public long addProjectOperationLog2(String log,long projectId,long creatorId,int logPermission){
		ProjectOperationLogVo logVo = new ProjectOperationLogVo();
        logVo.setCreatorId(creatorId);
        logVo.setLogContent(log);
        logVo.setLogPermission(logPermission);
        logVo.setProjectId(projectId);
		return addProjectOperationLog(logVo);
	}

	@Override
	public int updateProjectOperationLog(ProjectOperationLogVo logVo) {
		return projectOperationLogBiz.updateProjectOperationLog(logVo);
	}

	@Override
	public int deleteProjectOperationLog(long logId) {
		return projectOperationLogBiz.deleteProjectOperationLog(logId);
	}

	@Override
	public List<ProjectOperationLogVo> getProjectOperationLogByProjectId(long projectId) {
		List<ProjectOperationLogVo> logVos = projectOperationLogBiz.getProjectOperationLogByProjectId(projectId);
		decorateProjectOperationLogs(logVos);
		
		return logVos;
	}
	

	@Override
	public ProjectOperationLogVo getProjectOperationLogById(long logId){
		ProjectOperationLogVo logVo =  projectOperationLogBiz.getProjectOperationLogById(logId);
		decorateProjectOperationLog(logVo);
		
		return logVo;
	}

	@Override
	public List<ProjectOperationLogVo> getProjectOperationLog(long projectId, long userId) {
		List<ProjectOperationLogVo> logVos = getProjectOperationLogByProjectId(projectId);
		
		if(logVos == null || logVos.size() == 0){
			return null;
		}
		
		//管理员或者顾问可以查看所有的log
		if(userId == 1 || userId == 2){ 
			return logVos;
		}
		
		List<ProjectOperationLogVo> selectedLogVos = new ArrayList<ProjectOperationLogVo>();
		
		Iterator<ProjectOperationLogVo> ir = logVos.iterator();
		ProjectOperationLogVo logVo;
		long creatorId;
		
		ProjectInSelfRunVo projectVo = projectInSelfRunBiz.getProjectInSelfRunById(new Long(projectId).toString());
		
		while(ir.hasNext()){
			logVo = ir.next();
			creatorId = logVo.getCreatorId();
			
			if(creatorId == userId){
				selectedLogVos.add(logVo);
			}else if(logVo.getLogPermission() == ProjectOperationLogPermissionEnum.PERMISSION_ALL){
				selectedLogVos.add(logVo);
			}else if(projectVo.getCreatorId() == userId && logVo.getLogPermission() == ProjectOperationLogPermissionEnum.PERMISSION_CREATOR){
				selectedLogVos.add(logVo);
			}else if(projectVo.getCreatorId() != userId && logVo.getLogPermission() == ProjectOperationLogPermissionEnum.PERMISSION_DEV){
				selectedLogVos.add(logVo);
			}
		}
		
		return selectedLogVos;
	}
	
	
	private void decorateProjectOperationLogs(List<ProjectOperationLogVo> projectOperationLogVos){
		if(projectOperationLogVos == null || projectOperationLogVos.size() == 0){
			return;
		}
		
		Iterator<ProjectOperationLogVo> ir = projectOperationLogVos.iterator();
		ProjectOperationLogVo logVo;
		
		while(ir.hasNext()){
			logVo = ir.next();
			decorateProjectOperationLog(logVo);
		}
	}
	
	private void decorateProjectOperationLog(ProjectOperationLogVo logVo){
		long creatorId = logVo.getCreatorId();
		
		UserInfoVo creator = userBiz.getUserInfoByID(creatorId);
		logVo.setCreator(creator);
	}
}
