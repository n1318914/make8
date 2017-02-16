package com.yundaren.support.service;

import java.util.List;

import com.yundaren.support.vo.ProjectOperationLogVo;


/**
 * 项目日志服务
 * @author PetonWu
 *
 */
public interface ProjectOperationLogService {
	public long addProjectOperationLog(ProjectOperationLogVo logVo);
	public long addProjectOperationLog2(String log,long projectId,long creatorId,int logPermission);
	public int updateProjectOperationLog(ProjectOperationLogVo logVo);
	public int deleteProjectOperationLog(long logId);
	public List<ProjectOperationLogVo> getProjectOperationLogByProjectId(long projectId);
	public ProjectOperationLogVo getProjectOperationLogById(long logId);
	public List<ProjectOperationLogVo> getProjectOperationLog(long projectId,long userId);
}
