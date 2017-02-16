package com.yundaren.support.service;

import java.util.List;

import com.yundaren.common.util.PageResult;
import com.yundaren.support.po.ProjectInSelfRunPo;
import com.yundaren.support.po.ProjectSelfRunPushPo;
import com.yundaren.support.vo.ProjectAssignVo;
import com.yundaren.support.vo.ProjectInSelfRunHandlerVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.ProjectSelfRunPushVo;
import com.yundaren.user.vo.UserInfoVo;

public interface ProjectInSelfRunService {
	String addProjectInSelfRun(ProjectInSelfRunVo projectVo);

	ProjectInSelfRunVo getProjectInSelfRun(String id);

	/** 保存邀约服务商信息 **/
	int saveInviteServicer(List<ProjectInSelfRunHandlerVo> ProjectInSelfRunHandlerList);

	/** 删除所有 **/
	int deleteInviteServicerBacth(String projectId);

	int uploadProjectAttachment(String projectId, String attachment);

	/**
	 * 参与推送的项目
	 */
	int joinProject(String pid,String joinPlan,String enrollRole);
	
	/**
	 * 发送推送信息
	 * 
	 * @param status 0新增推送，4已邀请数据同步(PUSH表和handle表关联)
	 */
	int pushProjectInfo(List<ProjectSelfRunPushVo> projectInviteList, String projectId, int status);
	
	/**
	 * 删除推送记录
	 */
	int deletePushProjectInfo(String projectId, String developerId);

	/**
	 * 我发布的项目列表
	 */
	List<ProjectInSelfRunVo> getRequestList(long uid);
	
	/**
	 * 项目推送统计列表
	 * 
	 * @param pid
	 *            项目ID
	 */
	List<ProjectSelfRunPushVo> getDeveloperListByPID(String pid);

	/**
	 * 服务商可参与项目列表
	 * 
	 * @param did
	 *            开发者ID
	 */
	List<ProjectSelfRunPushVo> getInviteListByDID(long did);

	/**
	 * 服务商已参与项目列表
	 * 
	 * @param did
	 *            开发者ID
	 */
	List<ProjectSelfRunPushVo> getJoinListByDID(long did);
	int deleteInviteServicerByUId(String projectId,String uid);
	
	/**判断是否具备项目权限**/
	boolean isPropertyContaining(String projectId,String creatorId,String developerId);
	
	int getProjectRunHandlerCount(String projectId);
	
	int getCondulterInProjectPlaning(String projectId,String consultantId);
	

	List<ProjectInSelfRunVo> getAttendProjSelfRunList(String consultantId,String userType,String projectType,String projectStatus,PageResult page,String query);
	
	int getAttendProjSelfRunListCount(String consultantId,String userType,String projectType,String projectStatus,PageResult page,String query);
	
	List<ProjectSelfRunPushVo> getRemarkDeveloperListByPID(String projectId);
	
	int updateRemarkDeveloper(ProjectSelfRunPushVo projectSelfRunPushVo);
	
	List<ProjectInSelfRunVo> getProjectList(PageResult page,String query,String typeStr,String status);
	List<ProjectInSelfRunVo> getProjectListForAdmin(PageResult page,String query,String typeStr,String status,String consultantId);
	 
	int getProjectListCount(PageResult page,String query,String typeStr,String status);
	int getProjectListCountForAdmin(PageResult page,String query,String typeStr,String status,String consultantId);
	
	int getNewProjectsCount(String startQueryTime,String endQueryTime);
	
	int getNewWeixinProjectsCount(String startQueryTime,String endQueryTime);
	int updateProjectInSelfRun(ProjectInSelfRunVo projectVo);
	
	ProjectInSelfRunVo getProjctInSelfRunWithoutDecorate(String id);

	int updateRepoAllocated(String isGogsAllocated,String repoNick,String id);
	
	int getExistRepoNameCount(String repoNick);
	
    List<ProjectSelfRunPushVo> getRealProjectSelfRunPushsbyProjectId(long projectId,PageResult page);
	int getRealProjectSelfRunPushsCount(long projectId);
	List<ProjectSelfRunPushVo> getEnrollDevlopersbyProjectId(long projectId);
	
	int addProjectInSelfRunDev(ProjectSelfRunPushVo pushVo);
	int deleteProjectInSelfRunDev(ProjectSelfRunPushVo pushVo);
	ProjectSelfRunPushVo getProjectInSelfRunPushInfoByID(long projectId,long devId);
	ProjectInSelfRunHandlerVo getProjectRunHandlerByProjectIdAndDevId(long projectId,long devId);
	
	/**
	 * 查询选中开发人员列表
	 */
	List<ProjectInSelfRunHandlerVo> getProjectSelfRunChosenDevlopers(long projectId);

	/**
	 * 插入 项目转让数据
	 */
	public int addAssignmentRecord(ProjectAssignVo projectAssignVo);
	
	/**
	 * 查询 项目转让记录
	 */
	public List<ProjectAssignVo> getAssignRecord();
	
	/**
	 * 查询项目所剩金额（项目预算金额 - 项目阶段金额）  如果项目金额未指定具体金额则阶段金额不限制
	 */
	public double getProjectPlanAvalibleAmount(int projectId);
}
