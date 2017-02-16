package com.yundaren.support.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.Setter;

import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springside.modules.mapper.BeanMapper;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.ProjectSelfStatusEnum;
import com.yundaren.common.util.LabelUtil;
import com.yundaren.common.util.PageResult;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.dao.ProjectInSelfRunDao;
import com.yundaren.support.po.ProjectAssignPo;
import com.yundaren.support.po.ProjectInSelfRunPo;
import com.yundaren.support.po.ProjectSelfRunPushPo;
import com.yundaren.support.vo.ProjectAssignVo;
import com.yundaren.support.vo.ProjectInSelfRunHandlerVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.ProjectSelfRunPushVo;
import com.yundaren.support.vo.UploadFileVo;
import com.yundaren.support.po.ProjectInSelfRunHandlerPo;

public class ProjectInSelfRunBiz {
	@Setter
	private ProjectInSelfRunDao projectInSelfRunDao;

	public String addProjectInSelfRun(ProjectInSelfRunVo projectInSelfRunVo) {
		// TODO Auto-generated method stub
		ProjectInSelfRunPo projectPo = BeanMapper.map(projectInSelfRunVo, ProjectInSelfRunPo.class);
		
		// 设置项目市场每个项目的缩略图
		String abbrImagePath = "/img/projects/";
		String[] typeTags = projectPo.getType().split(",");
		int rand = new Random().nextInt(4) + 1;
		TOF: for (String firstTag : typeTags) {
			if (!StringUtils.isEmpty(firstTag)) {
				switch (firstTag) {
					case "1":
						abbrImagePath += "APP" + rand + ".png";
						break TOF;
					case "2":
						abbrImagePath += "APP" + rand + ".png";
						break TOF;
					case "3":
						abbrImagePath += "HTML" + rand + ".png";
						break TOF;
					case "4":
						abbrImagePath += "WEB" + rand + ".png";
						break TOF;
					case "5":
						abbrImagePath += "weixin" + rand + ".png";
						break TOF;
					case "7":
						abbrImagePath += "UI" + rand + ".png";
						break TOF;
					default:
						abbrImagePath += "other" + rand + ".png";
						break TOF;
				}
			}
		}
		projectPo.setAbbrImagePath(abbrImagePath);
		
		return projectInSelfRunDao.addProjectInSelfRun(projectPo);
	}

	public ProjectInSelfRunVo getProjectInSelfRunById(String id) {
		ProjectInSelfRunPo projectPo = projectInSelfRunDao.getProjectInSelfRunById(id);
		if (projectPo == null) {
			return null;
		}
		ProjectInSelfRunVo projectVo = BeanMapper.map(projectPo, ProjectInSelfRunVo.class);

		// 获取类型、预算和开始时间所对应的描述
//		String budgetDesc = LabelUtil.getSingleItemName(CommonConstants.PROJECT_IN_SELF_RUN_BUDGET_TAG,
//				projectPo.getBudget());
		String typeDesc = "";
		String[] items = projectPo.getType().split(",");
		for (int i = 0; i < items.length; i++) {
			if (StringUtils.isEmpty(items[i])) {
				continue;
			}
			if (i == items.length - 1) {
				typeDesc += LabelUtil.getSingleItemName(CommonConstants.PROJECT_IN_SELF_RUN_TYPE_TAG,
						items[i]);
			} else {
				typeDesc += LabelUtil.getSingleItemName(CommonConstants.PROJECT_IN_SELF_RUN_TYPE_TAG,
						items[i]) + " / ";
			}
		}
		String startTimeDesc = LabelUtil.getSingleItemName(CommonConstants.PROJECT_IN_SELF_RUN_STARTTIME_TAG,
				new Integer(projectPo.getStartTime()).toString());

//		projectVo.setBudget(budgetDesc);
		projectVo.setType(typeDesc);
		projectVo.setStartTime(startTimeDesc);
		
		//设置项目状态
		projectVo.setStatusTag(ProjectSelfStatusEnum.getStatusName(projectVo.getStatus()));

		// 处理上传文件的链接
		String attachmentJsonStr = projectPo.getAttachment();
		
		if(attachmentJsonStr != null && attachmentJsonStr.trim().length() > 0){
			attachmentJsonStr = attachmentJsonStr.replaceAll("&quot;", "\"");
			UploadFileVo[] fileVoArray = decorateProjectFileAttachments(attachmentJsonStr);
		    projectVo.setAttachments(fileVoArray);
		}
	
		return projectVo;
	}
 
	public ProjectInSelfRunVo getProjctInSelfRunWithoutDecorate(String id){
		ProjectInSelfRunPo projectPo = projectInSelfRunDao.getProjectInSelfRunById(id);
		
		if (projectPo == null) {
			return null;
		}
		
		ProjectInSelfRunVo projectVo = BeanMapper.map(projectPo, ProjectInSelfRunVo.class);
		
		//唯一处理上传文件的链接
		String attachmentJsonStr = projectPo.getAttachment();
		
		if(attachmentJsonStr != null && attachmentJsonStr.trim().length() > 0){
			attachmentJsonStr = attachmentJsonStr.replaceAll("&quot;", "\"");
			UploadFileVo[] fileVoArray = decorateProjectFileAttachments(attachmentJsonStr);
		    projectVo.setAttachments(fileVoArray);
		}
		
		//设置项目状态
		projectVo.setStatusTag(ProjectSelfStatusEnum.getStatusName(projectVo.getStatus()));

		return projectVo;
	}
	
	public int saveInviteServicer(List<ProjectInSelfRunHandlerVo> ProjectInSelfRunHandlerList) {
		// TODO Auto-generated method stub
		return projectInSelfRunDao.saveInviteServicer(ProjectInSelfRunHandlerList);
	}

	public int deleteInviteServicerBacth(String projectId) {
		return projectInSelfRunDao.deleteInviteServicerBacth(projectId);
	}
	
	public int deletePushServicerBacth(String projectId) {
		return projectInSelfRunDao.deletePushServicerBacth(projectId);
	}

	public int uploadProjectAttachment(String projectId, String attachment) {
		return projectInSelfRunDao.uploadProjectAttachment(projectId, attachment);
	}

	public int addPushProjectInfo(List<ProjectSelfRunPushVo> projectInviteList, long creatorId, int status) {
		return projectInSelfRunDao.addPushProjectInfo(projectInviteList, creatorId, status);
	}
	
	public int deletePushProjectInfo(String projectId, String developerId){
		return projectInSelfRunDao.deletePushProjectInfo(projectId, developerId);
	}

	public List<ProjectSelfRunPushVo> getDeveloperListByPID(String pid) {
		List<ProjectSelfRunPushVo> listDataVo = new ArrayList<ProjectSelfRunPushVo>();
		List<ProjectSelfRunPushPo> listDataPo = projectInSelfRunDao.getDeveloperListByPID(pid);
		if(!CollectionUtils.isEmpty(listDataPo)){
			listDataVo = BeanMapper.mapList(listDataPo, ProjectSelfRunPushVo.class);
		}
		return listDataVo;
	}

	public List<ProjectSelfRunPushVo> getInviteListByDID(long did) {
		List<ProjectSelfRunPushVo> listDataVo = new ArrayList<ProjectSelfRunPushVo>();
		List<ProjectSelfRunPushPo> listDataPo = projectInSelfRunDao.getInviteListByDID(did);
		if (!CollectionUtils.isEmpty(listDataPo)) {
			listDataVo = BeanMapper.mapList(listDataPo, ProjectSelfRunPushVo.class);
		}
		return listDataVo;
	}
	
	public List<ProjectSelfRunPushVo> getJoinListByDID(long did) {
		List<ProjectSelfRunPushVo> listDataVo = new ArrayList<ProjectSelfRunPushVo>();
		List<ProjectSelfRunPushPo> listDataPo = projectInSelfRunDao.getJoinListByDID(did);
		if (!CollectionUtils.isEmpty(listDataPo)) {
			listDataVo = BeanMapper.mapList(listDataPo, ProjectSelfRunPushVo.class);
		}
		return listDataVo;
	}
	
	public List<ProjectInSelfRunVo> getRequestList(long uid) {
		List<ProjectInSelfRunVo> listDataVo = new ArrayList<ProjectInSelfRunVo>();
		List<ProjectInSelfRunPo> listDataPo = projectInSelfRunDao.getRequestList(uid);
		if (!CollectionUtils.isEmpty(listDataPo)) {
			listDataVo = BeanMapper.mapList(listDataPo, ProjectInSelfRunVo.class);
		}
		return listDataVo;
	}

	public void updateProjectSelfPush(ProjectSelfRunPushVo projectSelfRunPushVo) {
		ProjectSelfRunPushPo projectPo = BeanMapper.map(projectSelfRunPushVo, ProjectSelfRunPushPo.class);
		projectInSelfRunDao.updateProjectSelfPush(projectPo);
	}
	
	
	public int deleteInviteServicerByUId(String projectId,String uid){
		return projectInSelfRunDao.deleteInviteServicerByUId(projectId, uid);
	}
	
	public boolean getUserCountInProjectPlaning(String projectId,String creatorId,String developerId){
		return projectInSelfRunDao.getUserCountInProjectPlaning(projectId,creatorId,developerId)>0;
	}
	public int getProjectRunHandlerCount(String projectId){
		return projectInSelfRunDao.getProjectRunHandlerCount(projectId);
	}
	
	public int getCondulterInProjectPlaning(String projectId,String consultantId){
		return projectInSelfRunDao.getCondulterInProjectPlaning(projectId, consultantId);
	}
	public List<ProjectInSelfRunVo> getAttendProjSelfRunList(String consultantId,String userType,String projectType,String projectStatus,PageResult pageResult,String query) {
		List<ProjectInSelfRunVo> listDataVo = new ArrayList<ProjectInSelfRunVo>();
		List<ProjectInSelfRunPo> listDataPo = projectInSelfRunDao.getAttendProjSelfRunList(consultantId,userType,projectType,projectStatus,pageResult,query);
		if (!CollectionUtils.isEmpty(listDataPo)) {	
			listDataVo = BeanMapper.mapList(listDataPo, ProjectInSelfRunVo.class);
			for(ProjectInSelfRunVo vo:listDataVo)vo.setStatusTag(ProjectSelfStatusEnum.getStatusName(vo.getStatus()));
		}
		return listDataVo;
	}
	
	public int getAttendProjSelfRunListCount(String consultantId,String userType,String projectType,String projectStatus,PageResult page,String query) {				
		return projectInSelfRunDao.getAttendProjSelfRunListCount(consultantId,userType, projectType,projectStatus,page ,query);
	}
	
	public List<ProjectSelfRunPushVo> getRemarkDeveloperListByPID(String projectId) {			
		List<ProjectSelfRunPushVo> listDataVo = new ArrayList<ProjectSelfRunPushVo>();
		List<ProjectSelfRunPushPo> listDataPo = projectInSelfRunDao.getRemarkDeveloperListByPID(projectId);
		if(!CollectionUtils.isEmpty(listDataPo)){
			listDataVo = BeanMapper.mapList(listDataPo, ProjectSelfRunPushVo.class);
		}
		return listDataVo;
	}
	
	public int updateRemarkDeveloper(ProjectSelfRunPushVo projectSelfRunPushVo) {
		return projectInSelfRunDao.updateRemarkDeveloper(projectSelfRunPushVo);
	}
	
	public List<ProjectInSelfRunVo> getProjectList(PageResult page,String query,String typeStr,String status) {		
		List<ProjectInSelfRunPo> projectInSelfRunPos = projectInSelfRunDao.getProjectList(page, query,typeStr,status);
		List<ProjectInSelfRunVo> projectInSelfRunVos = new ArrayList<>();
		if(!CollectionUtils.isEmpty(projectInSelfRunPos)){
			projectInSelfRunVos = BeanMapper.mapList(projectInSelfRunPos, ProjectInSelfRunVo.class);
		}
		return projectInSelfRunVos;
	}
	
	public List<ProjectInSelfRunVo> getProjectListForAdmin(PageResult page,String query,String typeStr,String status,String consultantId) {		
		List<ProjectInSelfRunPo> projectInSelfRunPos = projectInSelfRunDao.getProjectListForAdmin(page, query,typeStr,status,consultantId);
		List<ProjectInSelfRunVo> projectInSelfRunVos = new ArrayList<>();
		if(!CollectionUtils.isEmpty(projectInSelfRunPos)){
			projectInSelfRunVos = BeanMapper.mapList(projectInSelfRunPos, ProjectInSelfRunVo.class);
		}
		return projectInSelfRunVos;
	}
	
	public int getProjectListCount(PageResult page,String query,String typeStr,String status) {		
		return projectInSelfRunDao.getProjectListCount(page, query,typeStr,status);
	}
	
	public int getProjectListCountForAdmin(PageResult page,String query,String typeStr,String status,String consultantId) {		
		return projectInSelfRunDao.getProjectListCountForAdmin(page, query,typeStr,status,consultantId);
	}
	
	public int getNewProjectsCount(String startQueryTime,String endQueryTime) {		
		return projectInSelfRunDao.getNewProjectsCount(startQueryTime, endQueryTime);
	}
	
	public int getNewWeixinProjectsCount(String startQueryTime,String endQueryTime) {	
		return projectInSelfRunDao.getNewWeixinProjectsCount(startQueryTime, endQueryTime);
	}

	//根据PID和UID获取项目报名信息
	public  int getProjectPushByID(String projectId, long userId) {
		return projectInSelfRunDao.getProjectPushByID(projectId, userId);
	}
	
	
	public  ProjectSelfRunPushVo getProjectInSelfRunPushInfoByID(long projectId, long userId) {
		ProjectSelfRunPushPo pushPo =  projectInSelfRunDao.getProjectInSelfRunPushInfoByID(projectId, userId);
		
		if(pushPo != null){
			return BeanMapper.map(pushPo,ProjectSelfRunPushVo.class);
		}
		
		return null;
	}
	
	public int updatePorjectInSelfRun(ProjectInSelfRunVo projectInSelfRunVo){
		ProjectInSelfRunPo projectInSelfRunPo = BeanMapper.map(projectInSelfRunVo, ProjectInSelfRunPo.class);
		return projectInSelfRunDao.updateProjectInSelfRun(projectInSelfRunPo);
	}
	
	//解析项目附件json数据
	private UploadFileVo[] decorateProjectFileAttachments(String attachment){
		JSONArray jsonArray = JSONArray.fromObject(attachment);
		
		UploadFileVo[] uploadFileVoList = new UploadFileVo[jsonArray.size()];
		
		for(int i = 0; i < jsonArray.size();i++){
			UploadFileVo fileVo = new UploadFileVo();
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			String fileName = jsonObj.getString("fileName");
			fileVo.setName(fileName);
			//fileVo.setDisplay(jsonObj.getInt("display"));
			fileVo.setPath(jsonObj.getString("path"));
			
			if(fileName != null && fileName.trim().length() > 0){
				String displayName = "";
				fileName = fileName.trim();
				int nameLen = fileName.trim().length();
				
				if(nameLen >= 10){
					displayName = fileName.substring(0,7);
					displayName += "...";
				}else{
					displayName = fileName;
				}
				
				fileVo.setDisplayName(displayName);
				uploadFileVoList[i] = fileVo;
			}
		}
		
		return uploadFileVoList;
	}

	public int updateRepoAllocated(String isGogsAllocated,String repoNick,String id){
		return projectInSelfRunDao.updateRepoAllocated(isGogsAllocated, repoNick,id);
	}
	
	public int getExistRepoNameCount(String repoNick) {
		return projectInSelfRunDao.getExistRepoNameCount(repoNick);
	}
	
	public List<ProjectSelfRunPushVo> getRealProjectSelfRunPushsbyProjectId(long projectId, PageResult page){
		return BeanMapper.mapList(projectInSelfRunDao.getRealProjectSelfRunPushsbyProjectId(projectId, page),ProjectSelfRunPushVo.class);
	}
	
	public int getRealProjectSelfRunPushsCount(long projectId){
		return projectInSelfRunDao.getRealProjectSelfRunPushsCount(projectId);
	}
	
	public List<ProjectSelfRunPushVo> getEnrollDevlopersbyProjectId(long projectId){
		return BeanMapper.mapList(projectInSelfRunDao.getEnrollDevlopersbyProjectId(projectId),ProjectSelfRunPushVo.class);
	}
	
	/**
	 * 更新报名列表
	 */
	public int updateProjectInSelfRunPush(ProjectSelfRunPushVo pushVo) {
	     ProjectSelfRunPushPo pushPo = BeanMapper.map(pushVo, ProjectSelfRunPushPo.class);
	     return projectInSelfRunDao.updateProjectInSelfRunPush(pushPo); 
	}
	
	/***
	 * 获取选中程序员
	 */
	public ProjectInSelfRunHandlerVo getProjectRunHandlerByProjectIdAndDevId(long projectId,long developerId){
		ProjectInSelfRunHandlerPo handlerPo = projectInSelfRunDao.getProjectRunHandlerByProjectIdAndDevId(projectId,developerId);
		
		if(handlerPo !=  null){
			return BeanMapper.map(handlerPo,ProjectInSelfRunHandlerVo.class);
		}
		
		return null;
	}
	
	/**
	 * 获取选中程序员列表
	 * @param projectId
	 * @return
	 */
	public List<ProjectInSelfRunHandlerVo> getProjectSelfRunChosenDevlopers(long projectId) {
		List<ProjectInSelfRunHandlerPo> devsPo = projectInSelfRunDao.getProjectSelfRunChosenDevlopers(projectId);
		List<ProjectInSelfRunHandlerVo> devsVo = new ArrayList<ProjectInSelfRunHandlerVo>();
		
		if(devsPo != null && !CollectionUtils.isEmpty(devsPo)){
			devsVo = BeanMapper.mapList(devsPo, ProjectInSelfRunHandlerVo.class);
		}
		
		return devsVo;
	}
	
	/**
	 * 增加一条 项目转让的记录
	 */
	public int addAssignmentRecord(ProjectAssignVo projectAssignVo){
		ProjectAssignPo projectAssignPo = BeanMapper.map(projectAssignVo, ProjectAssignPo.class);
		return projectInSelfRunDao.addAssignmentRecord(projectAssignPo);
	}
	
	/**
	 * 查询所有项目转让记录
	 * @return 
	 */
	public List<ProjectAssignVo> getAssignRecord(){
		List<ProjectAssignPo> projectAssignPoList = projectInSelfRunDao.getAssignRecord();
		List<ProjectAssignVo> projectAssignVoList = new ArrayList<ProjectAssignVo>();
		
		if(projectAssignPoList != null && !CollectionUtils.isEmpty(projectAssignPoList)){
			projectAssignVoList = BeanMapper.mapList(projectAssignPoList, ProjectAssignVo.class);
		}
		
		return projectAssignVoList;
	}

	public double getProjectPlanAvalibleAmount(int projectId) {
		// TODO Auto-generated method stub
		return projectInSelfRunDao.getProjectPlanAvalibleAmount(projectId);
	}
}
