package com.yundaren.support.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.common.constants.ProjectSelfStatusEnum;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.PageResult;
import com.yundaren.support.biz.ProjectInSelfRunBiz;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.po.ProjectInSelfRunPo;
import com.yundaren.support.po.ProjectSelfRunPushPo;
import com.yundaren.support.service.ProjectInSelfRunService;
import com.yundaren.support.service.ProjectMailService;
import com.yundaren.support.vo.ProjectAssignVo;
import com.yundaren.support.vo.ProjectInSelfRunHandlerVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.ProjectSelfRunPushVo;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.UserInfoVo;

@Slf4j
public class ProjectInSelfRunServiceImpl implements ProjectInSelfRunService {

	@Setter
	private ProjectInSelfRunBiz projectInSelfRunBiz;

	@Setter
	private ProjectMailService projectMailService;
	
	@Setter
	private DictService dictService;
	
	@Setter
	private UserService userService;

	@Override
	public String addProjectInSelfRun(final ProjectInSelfRunVo projectVo) {
		final String id = projectInSelfRunBiz.addProjectInSelfRun(projectVo);
		//发送邮件通知管理员
		new Thread(new Runnable() {
			public void run() {
				projectMailService.sendSelfProjectNoticeMail2Admin(id,projectVo);
			}
		},"t_notify_newProject").start();
		return id;
	}

	@Override
	public ProjectInSelfRunVo getProjectInSelfRun(String id) {
		ProjectInSelfRunVo projectVo =  projectInSelfRunBiz.getProjectInSelfRunById(id);
		decorateProjectWithEnrollRole(projectVo);
		
		return projectVo;
	}

	@Override
	public int saveInviteServicer(List<ProjectInSelfRunHandlerVo> ProjectInSelfRunHandlerList) {
		// TODO Auto-generated method stub
		return projectInSelfRunBiz.saveInviteServicer(ProjectInSelfRunHandlerList);
	}

	@Override
	public int deleteInviteServicerBacth(String projectId) {
		// TODO Auto-generated method stub
		// 删除邀请
		projectInSelfRunBiz.deleteInviteServicerBacth(projectId);
		// 删除推送
		projectInSelfRunBiz.deletePushServicerBacth(projectId);
		return 0;
	}

	@Override
	public int uploadProjectAttachment(String projectId, String attachment) {
		// TODO Auto-generated method stub
		return projectInSelfRunBiz.uploadProjectAttachment(projectId, attachment);
	}

	@Override
	public int joinProject(final String pid,final String joinPlan,String enrollRole) {
		final UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();

		// 先查询是否有报名信息
		int iRet = projectInSelfRunBiz.getProjectPushByID(pid, currentUser.getId());

		ProjectSelfRunPushVo projectSelfRunPushVo = new ProjectSelfRunPushVo();
		projectSelfRunPushVo.setDeveloperId(currentUser.getId());
		projectSelfRunPushVo.setProjectId(pid);
		projectSelfRunPushVo.setStatus(2);
		projectSelfRunPushVo.setJoinTime(new Date());
		projectSelfRunPushVo.setJoinPlan(joinPlan);
		projectSelfRunPushVo.setEnrollRole(enrollRole);
		
		// 有则更新
		if (iRet > 0) {
			projectInSelfRunBiz.updateProjectSelfPush(projectSelfRunPushVo);
		} else {// 无则插入
			List<ProjectSelfRunPushVo> projectInviteList = new ArrayList<ProjectSelfRunPushVo>();
			projectInviteList.add(projectSelfRunPushVo);
			projectInSelfRunBiz.addPushProjectInfo(projectInviteList, currentUser.getId(), 5); //5代表主动报名
		}

		// 发送邮件通知管理员
		new Thread(new Runnable() {
			public void run() {
				projectMailService.sendJoinMail2Admin(pid, currentUser);
			}
		}, "t_notify_newProject").start();
		return iRet;
	}

	/**
	 * 项目推送
	 */
	@Override
	public int pushProjectInfo(List<ProjectSelfRunPushVo> projectInviteList, String projectId, int status) {
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		// 信息入库
		projectInSelfRunBiz.addPushProjectInfo(projectInviteList, currentUser.getId(), status);

		return 0;
	}

	@Override
	public int deletePushProjectInfo(String projectId, String developerId) {
		return projectInSelfRunBiz.deletePushProjectInfo(projectId, developerId);
	}

	@Override
	public List<ProjectSelfRunPushVo> getDeveloperListByPID(String pid) {
		return projectInSelfRunBiz.getDeveloperListByPID(pid);
	}

	/**
	 * 可参与的项目列表
	 */
	@Override
	public List<ProjectSelfRunPushVo> getInviteListByDID(long did) {
		List<ProjectSelfRunPushVo> listData = projectInSelfRunBiz.getInviteListByDID(did);
		setProjectStatus(listData);
		return listData;
	}

	/**
	 * 已参与的项目列表
	 */
	@Override
	public List<ProjectSelfRunPushVo> getJoinListByDID(long did) {
		List<ProjectSelfRunPushVo> listData = projectInSelfRunBiz.getJoinListByDID(did);
		setProjectStatus(listData);
		return listData;
	}

	private void setProjectStatus(List<ProjectSelfRunPushVo> listData) {
		for (ProjectSelfRunPushVo obj : listData) {
			String displayStatus = ProjectSelfStatusEnum.getStatusName(Integer.parseInt(obj
					.getProjectStatus()));
			obj.setProjectStatus(displayStatus);
		}
	}

	/**
	 * 我发布的项目列表
	 */
	@Override
	public List<ProjectInSelfRunVo> getRequestList(long uid) {
		List<ProjectInSelfRunVo> listData = projectInSelfRunBiz.getRequestList(uid);
		// 设置项目显示状态
		for (ProjectInSelfRunVo obj : listData) {
			String displayStatus = ProjectSelfStatusEnum.getStatusName(obj.getStatus());
			obj.setDisplayStatus(displayStatus);
		}
		return listData;
	}

	@Override
	public int deleteInviteServicerByUId(String projectId, String uid) {
		return projectInSelfRunBiz.deleteInviteServicerByUId(projectId, uid);
	}

	@Override
	public boolean isPropertyContaining(String projectId, String creatorId, String developerId) {
		return projectInSelfRunBiz.getUserCountInProjectPlaning(projectId, creatorId, developerId);
	}

	@Override
	public int getProjectRunHandlerCount(String projectId) {
		return projectInSelfRunBiz.getProjectRunHandlerCount(projectId);
	}

	@Override
	public int getCondulterInProjectPlaning(String projectId, String consultantId) {
		return projectInSelfRunBiz.getCondulterInProjectPlaning(projectId, consultantId);
	}

	@Override
	public List<ProjectInSelfRunVo> getAttendProjSelfRunList(String consultantId, String userType,
			String projectType,String projectStatus,PageResult page, String query) {
		return projectInSelfRunBiz.getAttendProjSelfRunList(consultantId, userType,projectType,projectStatus, page, query);
	}

	@Override
	public int getAttendProjSelfRunListCount(String consultantId, String userType, String projectType,String projectStatus,
			PageResult page,String query) {
		return projectInSelfRunBiz.getAttendProjSelfRunListCount(consultantId, userType, projectType,projectStatus,page, query);
	}

	@Override
	public List<ProjectSelfRunPushVo> getRemarkDeveloperListByPID(String projectId) {
		return projectInSelfRunBiz.getRemarkDeveloperListByPID(projectId);
	}

	@Override
	public int updateRemarkDeveloper(ProjectSelfRunPushVo projectSelfRunPushVo) {
		return projectInSelfRunBiz.updateRemarkDeveloper(projectSelfRunPushVo);
	}

	@Override
	public List<ProjectInSelfRunVo> getProjectList(PageResult page, String query,String typeStr,String status) {
		// TODO Auto-generated method stub
		List<ProjectInSelfRunVo> projectInSelfRunList =  projectInSelfRunBiz.getProjectList(page, query,typeStr,status);
		
		if(projectInSelfRunList == null || projectInSelfRunList.size() == 0){
			return null;
		}
		
		Iterator<ProjectInSelfRunVo> ir = projectInSelfRunList.iterator();
		
		while(ir.hasNext()){
			ProjectInSelfRunVo projectVo = ir.next();
			
			decorateProjectWithEnrollRole(projectVo);
		}
		
		return projectInSelfRunList;
	}
	
	@Override
	public List<ProjectInSelfRunVo> getProjectListForAdmin(PageResult page, String query,String typeStr,String status,String consultantId) {
		// TODO Auto-generated method stub
		return projectInSelfRunBiz.getProjectListForAdmin(page, query,typeStr,status,consultantId);
	}

	@Override
	public int getProjectListCount(PageResult page, String query,String typeStr,String status) {
		// TODO Auto-generated method stub
		return projectInSelfRunBiz.getProjectListCount(page, query,typeStr,status);
	}
	
	@Override
	public int getProjectListCountForAdmin(PageResult page, String query,String typeStr,String status,String consultantId) {
		// TODO Auto-generated method stub
		return projectInSelfRunBiz.getProjectListCountForAdmin(page, query,typeStr,status,consultantId);
	}

	@Override
	public int getNewProjectsCount(String startQueryTime, String endQueryTime) {
		// TODO Auto-generated method stub
		return projectInSelfRunBiz.getNewProjectsCount(startQueryTime, endQueryTime);
	}

	@Override
	public int getNewWeixinProjectsCount(String startQueryTime, String endQueryTime) {
		// TODO Auto-generated method stub
		return projectInSelfRunBiz.getNewWeixinProjectsCount(startQueryTime, endQueryTime);
	}
	
	@Override
	public int updateProjectInSelfRun(ProjectInSelfRunVo projectVo){
		return projectInSelfRunBiz.updatePorjectInSelfRun(projectVo);
	}
	
	/***
	 * 返回没有包装过的projectInSelfVO
	 */
	@Override
	public ProjectInSelfRunVo getProjctInSelfRunWithoutDecorate(String id){
		ProjectInSelfRunVo projectVo = projectInSelfRunBiz.getProjctInSelfRunWithoutDecorate(id);
		decorateProjectWithEnrollRole(projectVo);
				
		return projectVo;
	}

	@Override
	public int updateRepoAllocated(String isGogsAllocated, String repoNick,String id) {
		return projectInSelfRunBiz.updateRepoAllocated(isGogsAllocated, repoNick,id);
	}

	@Override
	public int getExistRepoNameCount(String repoNick) {
		return projectInSelfRunBiz.getExistRepoNameCount(repoNick);
	}
	
	/**
	 * 获取项目推送的程序员的列表
	 */
	@Override
	public List<ProjectSelfRunPushVo> getRealProjectSelfRunPushsbyProjectId(long projectId, PageResult page) {
		List<ProjectSelfRunPushVo> pushDevelopers =  projectInSelfRunBiz.getRealProjectSelfRunPushsbyProjectId(projectId, page);
		
		if(pushDevelopers != null){
			Iterator<ProjectSelfRunPushVo> ir = pushDevelopers.iterator();
			ProjectSelfRunPushVo developer;
			
			while(ir.hasNext()){
				developer = ir.next();
				
				decorateDeveloper(developer);
			}
		}
		
		return pushDevelopers;
	}
	
	@Override
	public int getRealProjectSelfRunPushsCount(long projectId) {
		// TODO Auto-generated method stub
		return projectInSelfRunBiz.getRealProjectSelfRunPushsCount(projectId);
	}
	
	/**
	 * 获取项目报名的程序员列表
	 */
	@Override
	public List<ProjectSelfRunPushVo> getEnrollDevlopersbyProjectId(long projectId){
		List<ProjectSelfRunPushVo> enrollDevelopers = projectInSelfRunBiz.getEnrollDevlopersbyProjectId(projectId);
		
		if(enrollDevelopers != null){
			Iterator<ProjectSelfRunPushVo> ir = enrollDevelopers.iterator();
			ProjectSelfRunPushVo developer;
			
			while(ir.hasNext()){
				developer = ir.next();
				
				decorateDeveloper(developer);
			}
		}
		
		return enrollDevelopers;
	}
	
	/**
	 * 选择开发者
	 */
	@Override
	public int addProjectInSelfRunDev(ProjectSelfRunPushVo devVo) {
	    int status = devVo.getStatus();
	    
	    if(status == 5){
	    	devVo.setStatus(6); //主动报名被选中
	    }else{
	    	devVo.setStatus(4); //推送接受邀请，然后被选中
	    }
	   
	    //保存选中的开发者
	    ProjectInSelfRunHandlerVo chosenDevVo = new ProjectInSelfRunHandlerVo();
	    chosenDevVo.setDeveloperId(new Long(devVo.getDeveloperId()).toString());
	    chosenDevVo.setProjectId(devVo.getProjectId());
	    chosenDevVo.setRole(devVo.getChosenRole());
	    List<ProjectInSelfRunHandlerVo> chosenDevs = new ArrayList<ProjectInSelfRunHandlerVo>();
	    chosenDevs.add(chosenDevVo);
	    
	   int effectedResult = projectInSelfRunBiz.saveInviteServicer(chosenDevs);
	   
	   if(effectedResult > 0){
		   effectedResult = projectInSelfRunBiz.updateProjectInSelfRunPush(devVo);
	   }
	   
	   return effectedResult;
	}

	
	/**
	 * 删除开发者
	 */
	@Override
	public int deleteProjectInSelfRunDev(ProjectSelfRunPushVo devVo) {
		int status = devVo.getStatus();
		
		if(status == 6){
			devVo.setStatus(7); //主动报名选中后被撤销
		}else{
			devVo.setStatus(2); //接受邀请被选中，然后被撤销
		}
		
		//删除选中的开发者
	    ProjectInSelfRunHandlerVo chosenDevVo = new ProjectInSelfRunHandlerVo();
	    chosenDevVo.setDeveloperId(new Long(devVo.getDeveloperId()).toString());
	    chosenDevVo.setProjectId(devVo.getProjectId());
	    chosenDevVo.setRole(devVo.getChosenRole());
	    
	    int effectedResult = projectInSelfRunBiz.deleteInviteServicerByUId(devVo.getProjectId(),new Long(devVo.getDeveloperId()).toString());
	    
	    if(effectedResult > 0){
	    	effectedResult =  projectInSelfRunBiz.updateProjectInSelfRunPush(devVo);
	    }
	    
	    return effectedResult;
		
	}
	
	@Override
	public ProjectSelfRunPushVo getProjectInSelfRunPushInfoByID(long projectId, long devId) {
		ProjectSelfRunPushVo pushVo = projectInSelfRunBiz.getProjectInSelfRunPushInfoByID(projectId, devId);
		decorateDeveloper(pushVo);
		
		return pushVo;
	}
	

	@Override
	public ProjectInSelfRunHandlerVo getProjectRunHandlerByProjectIdAndDevId(long projectId, long devId) {
		return projectInSelfRunBiz.getProjectRunHandlerByProjectIdAndDevId(projectId,devId);
	}
	
	/**
	 * 设置项目招募角色
	 * @param projectVo
	 * @return
	 */
	private void decorateProjectWithEnrollRole(ProjectInSelfRunVo projectVo){
		//设置项目招募角色		
		if(projectVo == null){
			return;
		}
		
		List<DictItemVo> selectedItemVoList = transalteRoleString2ItemList(projectVo.getEnrollRole());
		
		projectVo.setEnrollRoleList(selectedItemVoList);
	}
	
	/***
	 * 装饰开发者
	 */
	private void decorateDeveloper(ProjectSelfRunPushVo developerVo){
		if(developerVo == null){
			return;
		}
		
		List<DictItemVo> enrollRoleList = transalteRoleString2ItemList(developerVo.getEnrollRole());
		developerVo.setEnrollRoleList(enrollRoleList);
		
		UserInfoVo developerInfo = userService.getUserInfoByID(developerVo.getDeveloperId());
		developerVo.setDeveloper(developerInfo);
		
		//设置是否选中
		ProjectInSelfRunHandlerVo handlerVo = projectInSelfRunBiz.getProjectRunHandlerByProjectIdAndDevId(Long.parseLong(developerVo.getProjectId()),developerVo.getDeveloperId());
		
		if(handlerVo != null && handlerVo.getDeveloperId() != null && !handlerVo.getDeveloperId().trim().isEmpty()){
			developerVo.setIsChosen(1);
		}
		
		if(developerVo.getIsChosen() == 1){ //已经被选中
			String chosenRoleStr = developerVo.getChosenRole();
			
			if(chosenRoleStr != null && !chosenRoleStr.trim().isEmpty()){
				List<DictItemVo> chosenRoleList = transalteRoleString2ItemList(chosenRoleStr);
				developerVo.setChosenRoleList(chosenRoleList);
			}
		}
	}
	
	/***
	 * 装饰开发者
	 */
	private void decorateDeveloper2(ProjectInSelfRunHandlerVo developerVo){
		if(developerVo == null){
			return;
		}
		
		List<DictItemVo> enrollRoleList = transalteRoleString2ItemList(developerVo.getRole());
		developerVo.setRoleList(enrollRoleList);
		
		UserInfoVo developerInfo = userService.getUserInfoByID(Long.parseLong(developerVo.getDeveloperId()));
		developerVo.setDeveloper(developerInfo);
	}
	
	/**
	 * 将保存在数据中的string类型的角色翻译成List
	 * @param role
	 * @return
	 */
	private List<DictItemVo> transalteRoleString2ItemList(String role){
		String[] roleStrList = null;
		
		if(role != null && !role.trim().isEmpty()){
			roleStrList = role.split(",");
		}else{
			return null;
		}
		
		List<DictItemVo> dictItemVoList = dictService.getDictItemByType("projectDevRole");
		List<DictItemVo> selectedItemVoList = new ArrayList<DictItemVo>();
		
		if(roleStrList != null && roleStrList.length > 0){
			Iterator<DictItemVo> ir = dictItemVoList.iterator();
			
			for(int i = 0; i < roleStrList.length; i++){
				while(ir.hasNext()){
					DictItemVo itemVo = ir.next();
					
					if(new Integer(itemVo.getValue()).toString().equals(roleStrList[i])){
						selectedItemVoList.add(itemVo);
						break;
					}
				}
			}
		}
		
		return selectedItemVoList;
	}

	/**
	 * 获取项目选中开发
	 */
	@Override
	public List<ProjectInSelfRunHandlerVo> getProjectSelfRunChosenDevlopers(long projectId) {
		List<ProjectInSelfRunHandlerVo> devsList = projectInSelfRunBiz.getProjectSelfRunChosenDevlopers(projectId);
		
		if(devsList != null){
			Iterator<ProjectInSelfRunHandlerVo> devIterator = devsList.iterator();
			
			while(devIterator.hasNext()){
				decorateDeveloper2(devIterator.next());
			}
		}
		
		return devsList;
	}

	@Override
	public int addAssignmentRecord(ProjectAssignVo projectAssignVo) {
		return projectInSelfRunBiz.addAssignmentRecord(projectAssignVo);
	}

	@Override
	public List<ProjectAssignVo> getAssignRecord() {
		return projectInSelfRunBiz.getAssignRecord();
	}

	@Override
	public double getProjectPlanAvalibleAmount(int projectId) {
		return projectInSelfRunBiz.getProjectPlanAvalibleAmount(projectId);
	}
	
}
