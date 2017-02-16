package com.yundaren.support.biz;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.google.common.collect.ImmutableMap;
import com.yundaren.common.util.PageResult;
import com.yundaren.support.dao.ProjectDao;
import com.yundaren.support.po.ProjectJoinPo;
import com.yundaren.support.po.ProjectPo;
import com.yundaren.support.po.WeixinProjectPo;
import com.yundaren.support.vo.ProjectJoinVo;
import com.yundaren.support.vo.ProjectVo;
import com.yundaren.support.vo.WeixinProjectVo;
import com.yundaren.user.po.UserInfoPo;
import com.yundaren.user.vo.UserInfoVo;

public class ProjectBiz {

	@Setter
	private ProjectDao projectDao;

	public String addProject(ProjectVo projectVo) {
		// TODO Auto-generated method stub
		ProjectPo projectPo = BeanMapper.map(projectVo, ProjectPo.class);
		return projectDao.addProject(projectPo);
	}

	public ProjectVo getProjectById(String id) {
		ProjectVo projectVo = null;
		ProjectPo projectPo = projectDao.getProjectById(id);
		if (projectPo != null) {
			projectVo = BeanMapper.map(projectPo, ProjectVo.class);
		}
		return projectVo;
	}

	public long joinProject(ProjectJoinVo projectJoinVo) {
		// TODO Auto-generated method stub
		ProjectJoinPo projectJoinPo = BeanMapper.map(projectJoinVo, ProjectJoinPo.class);
		return projectDao.joinProject(projectJoinPo);
	}

	public List<ProjectVo> getProjectList(PageResult page, long userId, int opType, String type, String status) {
		// TODO Auto-generated method stub
		List<ProjectVo> listProjectVo = new ArrayList<ProjectVo>();
		List<ProjectPo> listProjectPo = projectDao.getProjectList(page, userId, opType, type, status);
		if (!CollectionUtils.isEmpty(listProjectPo)) {
			listProjectVo = BeanMapper.mapList(listProjectPo, ProjectVo.class);
		}
		return listProjectVo;
	}

	public ProjectJoinVo getJoinedInfo(String projectId, long userId) {
		ProjectJoinVo projectVo = null;
		ProjectJoinPo projectPo = projectDao.getJoinedInfo(projectId, userId);
		if (projectPo != null) {
			projectVo = BeanMapper.map(projectPo, ProjectJoinVo.class);
		}
		return projectVo;
	}

	public List<ProjectVo> getAllProjectList(String type, String status, PageResult page) {
		// TODO Auto-generated method stub
		List<ProjectVo> listProjectVo = new ArrayList<ProjectVo>();
		List<ProjectPo> listProjectPo = projectDao.getAllProjectList(type, status, page);
		if (!CollectionUtils.isEmpty(listProjectPo)) {
			listProjectVo = BeanMapper.mapList(listProjectPo, ProjectVo.class);
		}
		return listProjectVo;
	}

	public int getAllProjectListCount(String type, String status) {
		return projectDao.getAllProjectListCount(type, status);
	}

	public int getAllProjectCount() {
		return projectDao.getAllProjectCount();
	}
	
	public int getAllProjectSelfCount() {
		return projectDao.getAllProjectSelfCount();
	}

	public int getAllReserveCount() {
		return projectDao.getAllReserveCount();
	}

	public int getProjectCount(String type, String status) {
		return projectDao.getProjectCount(type, status);
	}

	public int getPublishListCount(long userId) {
		return projectDao.getPublishListCount(userId);
	}

	public int getJoinListCount(long userId, String type, String status) {
		return projectDao.getJoinListCount(userId, type, status);
	}

	/**
	 * 根据项目ID获取竞标商列表
	 */
	public List<ProjectJoinVo> getProjectJoinListByPID(String pid) {
		List<ProjectJoinVo> listProjectVo = new ArrayList<ProjectJoinVo>();
		List<ProjectJoinPo> listProjectPo = projectDao.getProjectJoinListByPID(pid);
		if (!CollectionUtils.isEmpty(listProjectPo)) {
			listProjectVo = BeanMapper.mapList(listProjectPo, ProjectJoinVo.class);
		}
		return listProjectVo;
	}

	public ProjectJoinVo getSelectedJoinInfo(String pid) {
		ProjectJoinVo pVo = null;
		ProjectJoinPo projectPo = projectDao.getSelectedJoinInfo(pid);
		if (projectPo != null) {
			pVo = BeanMapper.map(projectPo, ProjectJoinVo.class);
		}
		return pVo;
	}

	public int updateProject(ProjectVo projectVo) {
		ProjectPo projectPo = BeanMapper.map(projectVo, ProjectPo.class);
		return projectDao.updateProject(projectPo);
	}

	public long weixinRequest(WeixinProjectVo weixinProjectVo) {
		WeixinProjectPo weixinProjectPo = BeanMapper.map(weixinProjectVo, WeixinProjectPo.class);
		return projectDao.weixinRequest(weixinProjectPo);
	}

	public int supplementProject(String pid, String content) {
		return projectDao.supplementProject(pid, content);
	}

	public int update2CheckStatus(ProjectVo projectVo) {
		ProjectPo projectPo = BeanMapper.map(projectVo, ProjectPo.class);
		return projectDao.update2CheckStatus(projectPo);
	}

	public int updateAcceptStatus(ProjectVo projectVo) {
		ProjectPo projectPo = BeanMapper.map(projectVo, ProjectPo.class);
		return projectDao.updateAcceptStatus(projectPo);
	}

	public int updateProjectJoin(ProjectJoinVo projectJoinVo) {
		ProjectJoinPo joinPo = BeanMapper.map(projectJoinVo, ProjectJoinPo.class);
		return projectDao.updateProjectJoin(joinPo);
	}

	public void updateTimeoutProject() {
		projectDao.updateTimeoutProject();
	}

	public List<ProjectVo> getBidTimeoutList() {
		List<ProjectVo> listProjectVo = new ArrayList<ProjectVo>();
		List<ProjectPo> listProjectPo = projectDao.getBidTimeoutList();
		if (!CollectionUtils.isEmpty(listProjectPo)) {
			listProjectVo = BeanMapper.mapList(listProjectPo, ProjectVo.class);
		}
		return listProjectVo;
	}

	public List<ProjectVo> getRecommendProjectList() {
		List<ProjectVo> listProjectVo = new ArrayList<ProjectVo>();
		List<ProjectPo> listProjectPo = projectDao.getRecommendProjectList();
		if (!CollectionUtils.isEmpty(listProjectPo)) {
			listProjectVo = BeanMapper.mapList(listProjectPo, ProjectVo.class);
		}
		return listProjectVo;
	}
	
	public WeixinProjectVo getReserveByID(long id) {
		WeixinProjectVo pVo = null;
		WeixinProjectPo projectPo = projectDao.getReserveByID(id);
		if (projectPo != null) {
			pVo = BeanMapper.map(projectPo, WeixinProjectVo.class);
		}
		return pVo;
	}

	public List<WeixinProjectVo> getReserveList(int status, PageResult page) {
		List<WeixinProjectVo> listProjectVo = new ArrayList<WeixinProjectVo>();
		List<WeixinProjectPo> listProjectPo = projectDao.getReserveList(status, page);
		if (!CollectionUtils.isEmpty(listProjectPo)) {
			listProjectVo = BeanMapper.mapList(listProjectPo, WeixinProjectVo.class);
		}
		return listProjectVo;
	}
	
	public int getProjectTopIndex(String id) {
		return projectDao.getProjectTopIndex(id);
	}
	
	public int getReserveListCount(int status) {
		return projectDao.getReserveListCount(status);
	}
	
	public int updateReserveByID(WeixinProjectVo reserve) {
		WeixinProjectPo projectPo = BeanMapper.map(reserve, WeixinProjectPo.class);
		return projectDao.updateReserveByID(projectPo);
	}
}
