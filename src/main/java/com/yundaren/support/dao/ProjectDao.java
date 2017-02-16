package com.yundaren.support.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.google.common.collect.ImmutableMap;
import com.sun.org.apache.bcel.internal.generic.IMUL;
import com.yundaren.common.util.DateUtil;
import com.yundaren.common.util.PageResult;
import com.yundaren.support.po.ProjectJoinPo;
import com.yundaren.support.po.ProjectPo;
import com.yundaren.support.po.WeixinProjectPo;
import com.yundaren.support.vo.WeixinProjectVo;

public class ProjectDao extends SqlSessionDaoSupport {

	public String addProject(ProjectPo projectPo) {
		getSqlSession().insert("addProject", projectPo);
		return projectPo.getId();
	}

	public long joinProject(ProjectJoinPo projectJoinPo) {
		return getSqlSession().insert("joinProject", projectJoinPo);
	}

	public ProjectPo getProjectById(String id) {
		return getSqlSession().selectOne("getProjectByID", id);
	}

	public int getProjectCount(String type, String status) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("category", type);
		String[] statusArray = StringUtils.isEmpty(status) ? null : status.split(",");
		paramsMap.put("status", statusArray);
		return getSqlSession().selectOne("getProjectListCount", paramsMap);
	}
	
	public int getProjectTopIndex(String id) {
		return getSqlSession().selectOne("getProjectTopIndex", id);
	}

	// 首页项目个数展现
	public int getAllProjectCount() {
		return getSqlSession().selectOne("getAllProjectCount");
	}
	
	public int getAllProjectSelfCount() {
		return getSqlSession().selectOne("getAllProjectSelfCount");
	}
	
	public int getAllReserveCount() {
		return getSqlSession().selectOne("getAllReserveCount");
	}

	public int getPublishListCount(long userId) {
		return getSqlSession().selectOne("getPublishListCount", ImmutableMap.of("userId", userId));
	}

	public int getJoinListCount(long userId, String type, String status) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("category", type);
		String[] statusArray = StringUtils.isEmpty(status) ? null : status.split(",");
		paramsMap.put("status", statusArray);
		paramsMap.put("userId", userId);
		return getSqlSession().selectOne("getJoinListCount", paramsMap);
	}

	public List<ProjectPo> getProjectList(PageResult page, long userId, int opType, String type, String status) {
		List<ProjectPo> listProject = new ArrayList<>();

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("category", type);
		String[] statusArray = StringUtils.isEmpty(status) ? null : status.split(",");
		paramsMap.put("status", statusArray);
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		// opType 0查询所有，1查询我的发包，2查询我的接包
		if (opType == 1) {
			listProject = getSqlSession().selectList("getPublishList", paramsMap);
		} else if (opType == 2) {
			listProject = getSqlSession().selectList("getJoinList", paramsMap);
		} else {
			listProject = getSqlSession().selectList("getProjectList", paramsMap);
		}
		return listProject;
	}

	public List<ProjectPo> getAllProjectList(String type, String status, PageResult page) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("category", type);
		String[] statusArray = StringUtils.isEmpty(status) ? null : status.split(",");
		paramsMap.put("status", statusArray);
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("getAllProjectList", paramsMap);
	}

	public int getAllProjectListCount(String type, String status) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("category", type);
		String[] statusArray = StringUtils.isEmpty(status) ? null : status.split(",");
		paramsMap.put("status", statusArray);
		return getSqlSession().selectOne("getAllProjectListCount", paramsMap);
	}

	public List<ProjectJoinPo> getProjectJoinListByPID(String pid) {
		return getSqlSession().selectList("getProjectJoinListByPID", pid);
	}

	public ProjectJoinPo getSelectedJoinInfo(String pid) {
		return getSqlSession().selectOne("getSelectedJoinInfo", pid);
	}

	public int updateProject(ProjectPo projectPo) {
		return getSqlSession().update("updateProject", projectPo);
	}

	public int supplementProject(String pid, String content) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", pid);
		/*String title = "<p>&nbsp;</p><p>" + DateUtil.formatTime(new Date(), DateUtil.DATA_STAND_FORMAT_STR)
				+ "项目补充内容:</p>";*/
		String title = "<div class=\"append_content_header\">"
				+ DateUtil.formatTime(new Date(), DateUtil.DATA_STAND_FORMAT_STR) + "&#12288;补充内容:</div>";

		paramsMap.put("title", title);
		paramsMap.put("content", content);
		return getSqlSession().update("supplementProject", paramsMap);
	}

	public int update2CheckStatus(ProjectPo projectPo) {
		return getSqlSession().update("updateCheckStatus", projectPo);
	}

	public int updateAcceptStatus(ProjectPo projectPo) {
		return getSqlSession().update("updateAcceptStatus", projectPo);
	}

	public int updateProjectJoin(ProjectJoinPo projectJoinPo) {
		return getSqlSession().update("updateProjectJoin", projectJoinPo);
	}

	public long weixinRequest(WeixinProjectPo weixinProjectPo) {
		getSqlSession().insert("addWeixinRequest", weixinProjectPo);
		return weixinProjectPo.getId();
	}

	public ProjectJoinPo getJoinedInfo(String projectId, long userId) {
		return getSqlSession().selectOne("getJoinedInfo", ImmutableMap.of("pid", projectId, "uid", userId));
	}

	public int updateTimeoutProject() {
		String now = DateUtil.formatTime(new Date(), DateUtil.DATA_STAND_FORMAT_STR);
		return getSqlSession().update("updateTimeoutProject", now);
	}
	
	public List<ProjectPo> getBidTimeoutList() {
		String now = DateUtil.formatTime(new Date(), DateUtil.DATA_STAND_FORMAT_STR);
		return getSqlSession().selectList("getBidTimeoutList", now);
	}
	
	public List<ProjectPo> getRecommendProjectList() {
		return getSqlSession().selectList("getRecommendProjectList");
	}
	
	public WeixinProjectPo getReserveByID(long id) {
		return getSqlSession().selectOne("getReserveByID", id);
	}

	public List<WeixinProjectPo> getReserveList(int status, PageResult page) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("status", status);
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("getReserveList", paramsMap);
	}

	public int getReserveListCount(int status) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("status", status);
		return getSqlSession().selectOne("getReserveListCount", paramsMap);
	}
	
	public int updateReserveByID(WeixinProjectPo reserve) {
		return getSqlSession().update("updateReserveByID", reserve);
	}
}
