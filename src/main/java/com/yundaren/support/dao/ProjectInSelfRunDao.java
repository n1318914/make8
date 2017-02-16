package com.yundaren.support.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.google.common.collect.ImmutableMap;
import com.yundaren.common.util.PageResult;
import com.yundaren.support.po.ProjectAssignPo;
import com.yundaren.support.po.ProjectInSelfRunHandlerPo;
import com.yundaren.support.po.ProjectInSelfRunPo;
import com.yundaren.support.po.ProjectSelfRunPushPo;
import com.yundaren.support.vo.ProjectInSelfRunHandlerVo;
import com.yundaren.support.vo.ProjectSelfRunPushVo;

public class ProjectInSelfRunDao extends SqlSessionDaoSupport {
	public String addProjectInSelfRun(ProjectInSelfRunPo projectPo) {
		getSqlSession().insert("addProjectInSelfRun", projectPo);
		return projectPo.getId();
	}

	public ProjectInSelfRunPo getProjectInSelfRunById(String id) {
		return getSqlSession().selectOne("getProjectInSelfRunById", id);
	}

	public List<ProjectInSelfRunPo> getProjectInSelfRunByCreator(String creatorId) {
		return null;
	}

	public int saveInviteServicer(List<ProjectInSelfRunHandlerVo> ProjectInSelfRunHandlerList) {
		// TODO Auto-generated method stub
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("ProjectInSelfRunHandlerList", ProjectInSelfRunHandlerList);
		return getSqlSession().insert("insertInviteServicerBacth", paramsMap);
	}

	public int deleteInviteServicerBacth(String projectId) {
		return getSqlSession().delete("deleteInviteServicerBacth", ImmutableMap.of("projectId", projectId));
	}
	
	public int deletePushServicerBacth(String projectId) {
		return getSqlSession().delete("deletePushServicerBacth", ImmutableMap.of("projectId", projectId));
	}

	public int uploadProjectAttachment(String projectId, String attachment) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("attachment", attachment);
		return getSqlSession().update("uploadProjectAttachment", paramsMap);
	}

	public int addPushProjectInfo(List<ProjectSelfRunPushVo> projectInviteList, long creatorId, int status) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectInviteList", projectInviteList);
		paramsMap.put("creatorId", creatorId);
		paramsMap.put("status", status);
		return getSqlSession().insert("addPushProjectInfo", paramsMap);
	}
	
	public int deletePushProjectInfo(String projectId, String developerId) {
		return getSqlSession().delete("deletePushProjectInfo",
				ImmutableMap.of("projectId", projectId, "developerId", developerId));
	}

	public List<ProjectSelfRunPushPo> getDeveloperListByPID(String pid) {
		return getSqlSession().selectList("getDeveloperListByPID", pid);
	}

	public List<ProjectSelfRunPushPo> getInviteListByDID(long did) {
		return getSqlSession().selectList("getInviteListByDID", did);
	}

	public List<ProjectSelfRunPushPo> getJoinListByDID(long did) {
		return getSqlSession().selectList("getJoinListByDID", did);
	}
	
	public List<ProjectInSelfRunPo> getRequestList(long uid) {
		return getSqlSession().selectList("getRequestList", uid);
	}
	
	public int updateProjectSelfPush(ProjectSelfRunPushPo projectSelfRunPushPo) {
		return getSqlSession().update("updateProjectSelfPush", projectSelfRunPushPo);
	}
	
    public int deleteInviteServicerByUId(String projectId,String uid){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("uid", uid);
		return getSqlSession().delete("deleteInviteServicerByUId", paramsMap);
	}
	
	public int getUserCountInProjectPlaning(String projectId,String creatorId,String developerId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("creatorId", creatorId);
		paramsMap.put("developerId", developerId);
		return getSqlSession().selectOne("getUserCountInProjectPlaning",paramsMap);
	}
	
	public int getProjectRunHandlerCount(String projectId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		return getSqlSession().selectOne("getProjectRunHandlerCount",paramsMap);
	}
	
	public int getCondulterInProjectPlaning(String projectId,String consultantId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("consultantId", consultantId);
		return getSqlSession().selectOne("getCondulterInProjectPlaning",paramsMap);
	}
	
	public List<ProjectInSelfRunPo> getAttendProjSelfRunList(String consultantId,String userType,String projectType,String projectStatus,PageResult page,String query) {		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		
		if(query == null){
			query = "";
		}
		
		paramsMap.put("consultantId", consultantId);
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("userType", userType);
		paramsMap.put("projectType", "%" + projectType + "%");
		paramsMap.put("projectStatus",projectStatus);
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("getAttendProjSelfRunList", paramsMap);
	}
	
	public int getAttendProjSelfRunListCount(String consultantId,String userType,String projectType,String projectStatus,PageResult page,String query) {		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		
		if(query == null){
			query = "";
		}
		
		paramsMap.put("consultantId", consultantId);
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("userType", userType);
		paramsMap.put("projectType", "%" + projectType + "%");
		paramsMap.put("projectStatus",projectStatus);
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectOne("getAttendProjSelfRunListCount", paramsMap);
	}
	
	public List<ProjectSelfRunPushPo> getRemarkDeveloperListByPID(String projectId) {		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);	
		return getSqlSession().selectList("getRemarkDeveloperListByPID", paramsMap);
	}
	
	public int updateRemarkDeveloper(ProjectSelfRunPushVo projectSelfRunPushVo) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", projectSelfRunPushVo.getDeveloperId());
		paramsMap.put("isAlternative", projectSelfRunPushVo.getIsAlternative());
		paramsMap.put("projectId", projectSelfRunPushVo.getProjectId());
		if(projectSelfRunPushVo.getRemark()!=null)
		paramsMap.put("remark", projectSelfRunPushVo.getRemark());
		return getSqlSession().update("updateRemarkDeveloper", paramsMap);
	}
	
	public List<ProjectInSelfRunPo> getProjectList(PageResult page,String query,String typeStr,String status) {		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("status", status);
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("typeAllQuery", "%" + typeStr + "%");
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("getInselfRunProjectList", paramsMap);
	}
	
	public List<ProjectInSelfRunPo> getProjectListForAdmin(PageResult page,String query,String typeStr,String status,String consultantId) {		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("status", status);
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("typeAllQuery", "%" + typeStr + "%");
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		paramsMap.put("consultantId",consultantId);
		return getSqlSession().selectList("getInselfRunProjectListForAdmin", paramsMap);
	}
	
	public int getProjectListCount(PageResult page,String query,String typeStr,String status) {		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("status", status);
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("typeAllQuery", "%" + typeStr + "%");
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectOne("getInselfRunProjectListCount", paramsMap);
	}
	
	public int getProjectListCountForAdmin(PageResult page,String query,String typeStr,String status,String consultantId) {		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("status", status);
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("typeAllQuery", "%" + typeStr + "%");
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		paramsMap.put("consultantId",consultantId);
		return getSqlSession().selectOne("getInselfRunProjectListCountForAdmin", paramsMap);
	}

	public int getNewProjectsCount(String startQueryTime,String endQueryTime) {		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("startQueryTime", startQueryTime);
		paramsMap.put("endQueryTime", endQueryTime);
		return getSqlSession().selectOne("getNewProjectsCount", paramsMap);
	}
	public int getNewWeixinProjectsCount(String startQueryTime,String endQueryTime) {		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("startQueryTime", startQueryTime);
		paramsMap.put("endQueryTime", endQueryTime);
		return getSqlSession().selectOne("getNewWeixinProjectsCount", paramsMap);
	}

	
	public int getProjectPushByID(String projectId, long userId) {
		return getSqlSession().selectOne("getProjectPushByID", ImmutableMap.of("pid", projectId, "uid", userId));
	}
	
	public ProjectSelfRunPushPo getProjectInSelfRunPushInfoByID(long projectId, long userId) {
		return getSqlSession().selectOne("getProjectSelfRunPushInfoByID", ImmutableMap.of("pid", projectId, "uid", userId));
	}
	
	public int updateProjectInSelfRun(ProjectInSelfRunPo projectPo){
		return getSqlSession().update("updateProjectInSelfRun",projectPo);
	}

	public int updateRepoAllocated(String isGogsAllocated,String repoNick,String id){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("isGogsAllocated", isGogsAllocated);
		paramsMap.put("repoNick", repoNick);
		paramsMap.put("id", id);
		return getSqlSession().update("updateRepoAllocated",paramsMap);
	}
	
	public int getExistRepoNameCount(String repoNick) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("repoNick", repoNick);
		return getSqlSession().selectOne("getExistRepoNameCount", paramsMap);
	}
	
	public List<ProjectSelfRunPushPo> getRealProjectSelfRunPushsbyProjectId(long projectId, PageResult page) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("getRealProjectSelfRunPushsbyProjectId",paramsMap);
	}
	
	public int getRealProjectSelfRunPushsCount(long projectId){
		return getSqlSession().selectOne("getRealProjectSelfRunPushsCount", projectId);
	}
	
	public List<ProjectSelfRunPushPo> getEnrollDevlopersbyProjectId(long projectId){
		return getSqlSession().selectList("getEnrollDevlopersbyProjectId",projectId);
	}
	
	/**
	 * 更新报名列表
	 */
	public int updateProjectInSelfRunPush(ProjectSelfRunPushPo devPo) {
		return getSqlSession().update("updateProjectInSelfRunDev",devPo);
	}
	
	public ProjectInSelfRunHandlerPo getProjectRunHandlerByProjectIdAndDevId(long projectId,long developerId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		
		paramsMap.put("projectId", new Long(projectId).toString());
		paramsMap.put("developerId", new Long(developerId).toString());
		return getSqlSession().selectOne("getProjectRunHandlerByProjectIdAndDevId",paramsMap);
	}
	
	/**
	 * 获取项目选中的开发者列表
	 */
	public List<ProjectInSelfRunHandlerPo> getProjectSelfRunChosenDevlopers(long projectId) {
		return getSqlSession().selectList("getProjectSelfRunChosenDevlopers",projectId);
	}
	
	/**
	 * 增加一条 项目转让的记录
	 */
	public int addAssignmentRecord(ProjectAssignPo projectAssignPo){
		return getSqlSession().insert("addAssignRecord",projectAssignPo);
	}
	
	/**
	 * 查询所有项目转让记录
	 * @return 
	 */
	public List<ProjectAssignPo> getAssignRecord(){
		return getSqlSession().selectList("getAssignRecord");
	}

	public double getProjectPlanAvalibleAmount(int projectId) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectOne("getProjectPlanAvalibleAmount");
	}
}
