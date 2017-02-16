package com.yundaren.support.biz;

import java.util.List;

import org.springside.modules.mapper.BeanMapper;

import lombok.Setter;

import com.yundaren.support.dao.ProjectOperationLogDao;
import com.yundaren.support.po.ProjectOperationLogPo;
import com.yundaren.support.vo.ProjectOperationLogVo;

public class ProjectOperationLogBiz {
	@Setter
	private ProjectOperationLogDao projectOperationLogDao;
	
	public long addProjectOperationLog(ProjectOperationLogVo logVo){
		ProjectOperationLogPo logPo = BeanMapper.map(logVo, ProjectOperationLogPo.class);
		return projectOperationLogDao.addProjectOperationLog(logPo);
	}
	
	public int updateProjectOperationLog(ProjectOperationLogVo logVo){
		ProjectOperationLogPo logPo = BeanMapper.map(logVo, ProjectOperationLogPo.class);
		return projectOperationLogDao.updateProjectOperationLog(logPo);
	}
	
	public int deleteProjectOperationLog(long logId){
		return projectOperationLogDao.deleteProjectOperationLog(logId);
	}
	
	public List<ProjectOperationLogVo> getProjectOperationLogByProjectId(long projectId){
		List<ProjectOperationLogPo> projectOprationLogPoList =  projectOperationLogDao.getProjectOperationLogByProjectId(projectId);
		
		return BeanMapper.mapList(projectOprationLogPoList, ProjectOperationLogVo.class);
	}
	
	public ProjectOperationLogVo getProjectOperationLogById(long logId){
		return BeanMapper.map(projectOperationLogDao.getProjectOperationLogById(logId),ProjectOperationLogVo.class);
	}

}
