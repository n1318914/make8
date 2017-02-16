package com.yundaren.api.rest;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.ProjectInSelfRunConstants;
import com.yundaren.common.constants.UrlRewriteConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.DBUtil;
import com.yundaren.common.util.DateUtil;
import com.yundaren.common.util.GitHttpClient;
import com.yundaren.common.util.GogsUtil;
import com.yundaren.common.util.MD5Util;
import com.yundaren.common.util.PageResult;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.service.ProjectInSelfRunPlanService;
import com.yundaren.support.service.ProjectInSelfRunService;
import com.yundaren.support.service.ProjectMailService;
import com.yundaren.support.service.ProjectOperationLogService;
import com.yundaren.support.service.YunConnectService;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.support.vo.ProjectInSelfRunHandlerVo;
import com.yundaren.support.vo.ProjectInSelfRunMonitorVo;
import com.yundaren.support.vo.ProjectInSelfRunPlanVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.ProjectOperationLogVo;
import com.yundaren.support.vo.ProjectSelfRunPushVo;
import com.yundaren.user.service.SsoService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserAccountInDetailVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
@Slf4j
public class ProjectInSelfRunRest {
	@Setter
	private SsoService ssoService;

	@Setter
	private UserService userService;

	@Setter
	private ProjectInSelfRunService projectInSelfRunService;

	@Setter
	private DomainConfig domainConfig;
	
	@Setter 
	private ProjectInSelfRunPlanService projectInSelfRunPlanService;
	
	@Setter
	private ProjectMailService projectMailService;

	@Setter
	private IdentifyService identifyService;
	
	@Setter
	private DictService dictService;
	
	@Setter
	private ProjectOperationLogService projectOperationLogService;
	
	@Setter
	private YunConnectService yunConnectService;
	
	@Autowired
	private DBUtil dbUtil;
	
	/***
	 * 发布需求
	 * @param request
	 * @param projectVo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = APIConstants.PROJECTINSELFRUN_ADD, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> addProjectInSelfRun(HttpServletRequest request, @ModelAttribute ProjectInSelfRunVo projectVo)
			throws UnsupportedEncodingException {
		
		Map<String, Object> result = checkPublishArgs(request, projectVo);

		// 如果检查通过
		if (null == result) {
			// 获取用户登录信息
			SsoUserVo ssoUser = projectVo.getSsoCreator();
			String loginName = ssoUser.getLoginName();
			String password = ssoUser.getPassword();

			// 判断当前的用户是否有注册
			SsoUserVo existingSsoUser = ssoService.getSsoUserByUserName(loginName);
            boolean isNewUser = false;
            
			if (existingSsoUser == null) {
				log.debug("user " + loginName + " doesn't exist,try to create it.");
				ssoUser.setIsActive(0); //激活用户
				ssoUser.setUserType(1); //设置用户默认类型
				ssoUser.setAccountType(1); //手机注册
				ssoService.createSsoUser(ssoUser);
				isNewUser = true;
				GitHttpClient gitHttpClient = GogsUtil.register(ssoUser.getLoginName(), ssoUser);				
				if(gitHttpClient !=null){
					ssoUser.getUserInfoVo().setId(ssoUser.getUserId());	
					ssoUser.getUserInfoVo().setIsGogs(1);				
					userService.updateUserInfo(ssoUser.getUserInfoVo());
				}	
			} else {
				log.debug("user " + loginName + " exists.");
				ssoUser = existingSsoUser;
			}

			// 选取一名顾问
			List<UserInfoVo> consultantList = userService.getAllConsultants();

			if (consultantList == null || consultantList.size() == 0) {
				log.info("can't get any consultant");
			} else {
				int consultantSize = consultantList.size();

				Object currentIndexObject = request.getSession().getAttribute(
						CommonConstants.CURRENT_CONSULTANT_INDEX);
				int currentIndex = 0;

				if (currentIndexObject != null) {
					currentIndex = (int) currentIndexObject;
					++currentIndex;
				}

				currentIndex = currentIndex % consultantSize;
				UserInfoVo selectedConsultant = consultantList.get(currentIndex);
				projectVo.setConsultantId(selectedConsultant.getId());
				projectVo.setConsultant(selectedConsultant);

				request.getSession().setAttribute(CommonConstants.CURRENT_CONSULTANT_INDEX, currentIndex);
			}

			projectVo.setCreatorId(ssoUser.getUserId());
			projectVo.setStatus(-1);//初始状态
			//随机一个浏览量给新建的项目(50~100)
			int randomViewCount = (int) Math.floor(50 + Math.random()*50);
			projectVo.setViewCount(randomViewCount);
			String projectId = projectInSelfRunService.addProjectInSelfRun(projectVo);
            
			if (projectId == null || projectId.isEmpty()) {
				log.info("creating project with name '" + projectVo.getName() + "' failed!");
				result = ResponseMapUtil.getFailedResponseMap("项目创建失败");
			} else {
				log.info("creating project with name '" + projectVo.getName() + "' successfully!");
				
		        ProjectOperationLogVo logVo = new ProjectOperationLogVo();
		        
		        //项目操作日志
		        projectOperationLogService.addProjectOperationLog2("项目创建成功",new Long(projectId),ssoUser.getUserId(),
		        		ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
				
				result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),UrlRewriteConstants.REWRITE_PROJECT_IN_SELF_RUN_DETAILS + projectId);
				
			}
			
			boolean loginResult = false;
		    boolean isAlreadyLogin = false;
			
			if(projectId != null && !projectId.isEmpty()){				
				//判断用户是否已经登录
				Object loginSsoUser = request.getSession().getAttribute(CommonConstants.SESSION_LOGIN_USER);				
				
				if(loginSsoUser == null){
					String ip = CommonUtil.getRealIP(request);
					ssoUser.setIpAddress(ip);		
					
					
					SsoUserVo checkedSsoUser = ssoService.getSsoUserByUserName(loginName);
										
					if(!isNewUser){
						if(password != null && MD5Util.encodeByMD5(password).equals(checkedSsoUser.getPassword())){
							loginResult = ssoService.login(ssoUser);
						}else{
							loginResult = false;
						}
					}else{
						loginResult = ssoService.login(ssoUser);
					}
				}else{
					//用户已经登录
					loginResult = true;
					isAlreadyLogin = true;
				}
			}
				
	        
		    String failedMsg = "";
		    
			if(loginResult){				
				if(!isAlreadyLogin){
					request.getSession().setAttribute(CommonConstants.SESSION_LOGIN_USER, ssoUser);
					// 名称缩略显示
					UserInfoVo userInfoVo = userService.getUserInfoByID(ssoUser.getUserId());
					setLetterName(ssoUser, userInfoVo);
				}	
			}else{
				log.debug("user " + loginName + " login failed!");
				failedMsg = "用户名或密码错误";
				request.getSession().setAttribute(CommonConstants.PROJECT_IN_SELF_CREATE_FLAG, "1");
				request.getSession().setAttribute(CommonConstants.PROJECT_IN_SELF_CREATE_FAILED_MSG, failedMsg);
				request.getSession().setAttribute(CommonConstants.PROJECT_IN_SELF_CREATE_ID,projectId);
				request.getSession().setAttribute(CommonConstants.PROJECT_IN_SELF_CREATE_LOGIN_NAME,loginName);
				result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),PageForwardConstants.LOGIN_PAGE);
			}
		}

		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	// 校验参数是否合法
	private Map<String, Object> checkPublishArgs(HttpServletRequest request, ProjectInSelfRunVo projectVo) {
		String type = projectVo.getType();
		String buget = projectVo.getBudget();
		String name = projectVo.getName();
		String content = projectVo.getContent();
		String startTime = projectVo.getStartTime();
		String loginName = projectVo.getSsoCreator().getLoginName();

		if (StringUtils.isEmpty(buget) || StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(content) || StringUtils.isEmpty(type) || StringUtils.isEmpty(startTime)
	            || StringUtils.isEmpty(loginName)) {
			return ResponseMapUtil.getFailedResponseMap("必填项参数不可为空");
		}

		// 返回空表示校验通过
		return null;
	}
	

	/**
	 * 查询所有服务商(邀约)
	 * 
	 * @param request
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = APIConstants.PRIVIDER_INVITE_QUERY, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> queryAllServicer(HttpServletRequest request, String query, String projectId,String regionId,String caseTypeStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		query = query == null ? "" : query;
		List<UserInfoVo> inviteServicerFuzzList = userService.getInviteServicerFuzzList(query, projectId,regionId);
		if(caseTypeStr!=null){
			List<UserInfoVo> deleteServicerFuzzList = new ArrayList<>();		
			if(inviteServicerFuzzList!=null)
			for(UserInfoVo userInfoVo:inviteServicerFuzzList){
				if(userInfoVo.getCaseType()==null)deleteServicerFuzzList.add(userInfoVo);
				else{
					boolean exist = false;
					String caseTypes[] = userInfoVo.getCaseType().split(",");
					for(String caseType:caseTypes){
						if(caseTypeStr.equals(caseType)){
							exist = true;
							break;
						}
					}
					if(!exist)deleteServicerFuzzList.add(userInfoVo);
				}						
			}
			for(UserInfoVo deleteUser:deleteServicerFuzzList)inviteServicerFuzzList.remove(deleteUser);
		}
		result.put("inviteServicerFuzzList", inviteServicerFuzzList);
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 保存邀约服务商信息
	 * 
	 * @param request
	 * @param projectSelfListParam
	 * @return
	 */
	@RequestMapping(value = APIConstants.SAVE_SELF_PRIVIDER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> addSelfRunServicer(HttpServletRequest request, String developerId,String chosenRole,
			String projectId) {
		Map<String, Object> result = new HashMap<String, Object>();
		//projectInSelfRunService.deleteInviteServicerBacth(projectId);
		
		
		/*if (projectSelfListParam.getList() != null) {
			projectInSelfRunService.saveInviteServicer(projectSelfListParam.getList());

			// 保存推送信息
			List<ProjectSelfRunPushVo> projectInviteList = new ArrayList<ProjectSelfRunPushVo>();
			projectInviteList = BeanMapper
					.mapList(projectSelfListParam.getList(), ProjectSelfRunPushVo.class);
			for (ProjectSelfRunPushVo pushVo : projectInviteList) {
				pushVo.setJoinTime(new Date());
			}
			projectInSelfRunService.pushProjectInfo(projectInviteList, projectId, 4);
		}*/
		
		
		//已添加角色
		List<UserInfoVo> planUserInfoList = userService.getProjectInSelfRun(projectId); 	
		result.put("planUserInfoList", planUserInfoList);
		//项目详情
		ProjectInSelfRunVo projectInSelfRunVo = projectInSelfRunService.getProjectInSelfRun(projectId);
		
		//添加了项目成员，项目进入到开发中状态
		if(projectInSelfRunVo.getStatus() != 1){
			ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjctInSelfRunWithoutDecorate(projectId);
			projectVo.setStatus(1);
			projectInSelfRunService.updateProjectInSelfRun(projectVo);
		}
		
		UserInfoVo consultantVo = userService.getUserInfoByID(projectInSelfRunVo.getConsultantId());
		if(projectInSelfRunVo.getStatus()!=-1&&projectInSelfRunVo.getStatus()!=3&&!projectInSelfRunVo.getIsGogsAllocated().equals("1")){
		}else{
			//分配角色GIT权限
			GitHttpClient gitHttpClient = new GitHttpClient();
			gitHttpClient.loginAdmin();
			String repoUser = consultantVo.getMobile();
			String repoName = projectInSelfRunVo.getRepoNick();
			for(UserInfoVo vo:planUserInfoList){
				if(vo.getMobile()!=null){
					if(!vo.getMobile().trim().equals("")){
						boolean status = gitHttpClient.addUserForProject(vo.getMobile(), repoUser, repoName);
					}			
				}
			}	
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	} 
	
	@RequestMapping(value = APIConstants.UPDATE_SELF_ATTACH, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> uploadProjectAttachment(HttpServletRequest request,String projectId,String attachment) {
		Map<String, Object> result = new HashMap<String, Object>();		
		projectInSelfRunService.uploadProjectAttachment(projectId, attachment);
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 推送项目给开发者
	 * 
	 * @param allMailStr
	 *            所有开发者邮箱用','号分隔
	 */
	@RequestMapping(value = APIConstants.PUSH_PROJECT_INFO, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> pushProjectInfo(HttpServletRequest request, ListParam projectSelfListParam,
			final String projectId, final String allMailStr, String isPushAll) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 是否推送所有
		List<ProjectSelfRunPushVo> pushAllList = new ArrayList<ProjectSelfRunPushVo>();
		String pushAllMailStr = "";
		boolean pushAll = Boolean.valueOf(isPushAll);
		if (pushAll) {
			PageResult page = new PageResult<>();
			page.setPageSize(10000);
			List<UserInfoVo> listUser = userService.queryDevelopers("", "", "", "", "", page);
			for (UserInfoVo userInfo : listUser) {
				pushAllMailStr += userInfo.getEmail() + ",";
				
				ProjectSelfRunPushVo pushVo = new ProjectSelfRunPushVo();
				pushVo.setProjectId(projectId);
				pushVo.setDeveloperId(userInfo.getId());
				pushAllList.add(pushVo);
			}
			pushAllMailStr = pushAllMailStr.substring(0, pushAllMailStr.length() - 1);
		}
		
		// 选择推送数据
		final String pushMail = pushAll ? pushAllMailStr : allMailStr;
		List<ProjectSelfRunPushVo> pushList = pushAll ? pushAllList : projectSelfListParam.getPushList();

		if (!CollectionUtils.isEmpty(pushList)) {
			projectInSelfRunService.pushProjectInfo(pushList, projectId, 0);

			if (!StringUtils.isEmpty(pushMail)) {
				// 发送邮件推送
				new Thread(new Runnable() {
					@Override
					public void run() {
						projectMailService.pushProjectSelf2Developer(pushMail, projectId);
					}
				}, "t_push_mail").start();
				
				//项目操作日志
		        projectOperationLogService.addProjectOperationLog2("项目推送给开发者",new Long(projectId),CommonUtil.getCurrentLoginUser().getUserId(),
		        		ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_CONSULTANT);
				
			}
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 报名项目
	 */
	@RequestMapping(value = APIConstants.JOIN_PROJECT_SELF, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> joinProject(HttpServletRequest request, String projectId,String joinPlan,String enrollRole) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置用户认证信息
		IdentifyVo identifyInfo = identifyService.getIdentifyByUID(CommonUtil.getCurrentLoginUser()
				.getUserInfoVo().getId());

		if (identifyInfo == null || identifyInfo.getStatus() != 1) {
			result.put("success", "-1");
		} else {
			result.put("success", "0");

			int operate = projectInSelfRunService.joinProject(projectId,joinPlan,enrollRole);
			// 更新未读消息
			if (operate > 0) {
				Object obj = request.getSession().getAttribute(CommonConstants.SESSION_NOT_READ_MSG);
				int canJoinNum = obj == null ? 0 : (int) obj;
				if (canJoinNum > 0) {
					request.getSession().setAttribute(CommonConstants.SESSION_NOT_READ_MSG, --canJoinNum);
				}
			}
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 项目推送统计列表
	 */
	@RequestMapping(value = APIConstants.PUSH_STATUS_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List> pushList(HttpServletRequest request, String projectId) {
		List<ProjectSelfRunPushVo> listData = new ArrayList<ProjectSelfRunPushVo>();

		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjectInSelfRun(projectId);
		// 只有管理员和创建者能查看开发者列表
		if (projectVo != null
				&& (userInfo.getUserType() == -1 || projectVo.getCreatorId() == userInfo.getId())) {
			listData = projectInSelfRunService.getDeveloperListByPID(projectId);
		}
		
		return new ResponseEntity<List>(listData, HttpStatus.OK);
	}

	/**
	 * 服务商可参与项目列表
	 */
	@RequestMapping(value = APIConstants.INVITE_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List> inviteList(HttpServletRequest request) {
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		List<ProjectSelfRunPushVo> listData = projectInSelfRunService.getInviteListByDID(userInfo.getId());
		return new ResponseEntity<List>(listData, HttpStatus.OK);
	}
	
	/**
	 * 服务商已参与项目列表
	 */
	@RequestMapping(value = APIConstants.JOIN_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List> joinList(HttpServletRequest request, String userId) {
		long uid = 0;
		if (!StringUtils.isEmpty(userId)) {
			uid = Long.parseLong(userId);
		} else {
			UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
			uid = userInfo.getId();
		}
		List<ProjectSelfRunPushVo> listData = projectInSelfRunService.getJoinListByDID(uid);
		return new ResponseEntity<List>(listData, HttpStatus.OK);
	}

	/**
	 * 我发布的项目列表
	 */
	@RequestMapping(value = APIConstants.REQUEST_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List> requestList(HttpServletRequest request, String userId) {
		long uid = 0;
		if (!StringUtils.isEmpty(userId)) {
			uid = Long.parseLong(userId);
		} else {
			UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
			uid = userInfo.getId();
		}

		List<ProjectInSelfRunVo> listData = projectInSelfRunService.getRequestList(uid);
		return new ResponseEntity<List>(listData, HttpStatus.OK);
	}
	
		/**
	 * 加载已添加服务商列表
	 * @param request
	 * @param projectId
	 * @param attachment
	 * @return
	 */
	@RequestMapping(value = APIConstants.LOAD_PROJECT_USER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> loadProjectSelfRunList(HttpServletRequest request,String projectId) {
		Map<String, Object> result = new HashMap<String, Object>();		
		//已添加角色
		List<UserInfoVo> planUserInfoList = userService.getProjectInSelfRun(projectId); 		
		result.put("planUserInfoList", planUserInfoList);
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	} 
	
	/**
	 * 删除已添加角色
	 * @param request
	 * @param proSelfRunHandlerVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.DEL_PROJECT_USER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> delProjectSelfRun(HttpServletRequest request,String uid,String projectId) {
		Map<String, Object> result = new HashMap<String, Object>();		
		//移除Git关联
		ProjectInSelfRunVo projectInSelfRunVo = projectInSelfRunService.getProjectInSelfRun(projectId);
		UserInfoVo consultantVo = userService.getUserInfoByID(projectInSelfRunVo.getConsultantId());
		UserInfoVo deletedVo = userService.getUserInfoByID(Long.parseLong(uid));
		String deletedRepoUser = deletedVo.getMobile();
		boolean removeSuccess = true;
		if("1".equals(projectInSelfRunVo.getIsGogsAllocated())){
			GogsUtil.removeUserForProject(deletedRepoUser, consultantVo.getMobile(), projectInSelfRunVo.getRepoNick());	
		}		 
		if(removeSuccess){
			//删除已添加角色
			projectInSelfRunService.deleteInviteServicerByUId(projectId, uid);
			// 删除PUSH表关联
			projectInSelfRunService.deletePushProjectInfo(projectId, uid);
			//已添加角色
			List<UserInfoVo> planUserInfoList = userService.getProjectInSelfRun(projectId);
			
			//如果删掉了所有的开发人员，项目的状态自动变成为"待启动"
			if(planUserInfoList == null || planUserInfoList.size() == 0){
				if(projectInSelfRunVo.getStatus() != 0){
					ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjctInSelfRunWithoutDecorate(projectId);
					projectVo.setStatus(0);
					projectInSelfRunService.updateProjectInSelfRun(projectVo);
					
				    //项目操作日志
			        projectOperationLogService.addProjectOperationLog2("项目重新进入待启动",new Long(projectId),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
			        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
			        
				}
			}
			
			result.put("planUserInfoList", planUserInfoList);	
			result.put("status", "1");
		}else{
			result.put("status", "-1");
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	} 
	
	/**
	 * 新增项目计划
	 * @param request
	 * @param projectInSelfRunPlanVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.SAVE_PROJECT_PLAN, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> savePorjectPlan(HttpServletRequest request,ProjectInSelfRunPlanVo projectInSelfRunPlanVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		int newPlanStepId = projectInSelfRunPlanService.saveProjectPlan(projectInSelfRunPlanVo);
		
		if(newPlanStepId > 0){
			String projectId = projectInSelfRunPlanVo.getProjectId();
			
			ProjectInSelfRunPlanVo planVo = projectInSelfRunPlanService.getProjectPlanByProjectIdAndStepId(projectId, newPlanStepId);
			
			result.put("projectInSelfRunPlanVo",planVo);
			
		    //项目操作日志
	        projectOperationLogService.addProjectOperationLog2("新增项目计划【" + newPlanStepId + "】" ,new Long(projectId),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
	        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_CONSULTANT);
		}else{
			result = ResponseMapUtil.getFailedResponseMap("1");
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	} 
	
	/**
	 * 查询当前项目计划列表
	 * @param request
	 * @param projectInSelfRunPlanVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.LOAD_PROJECT_PLAN, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> loadPorjectPlanList(HttpServletRequest request,String projectId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProjectInSelfRunPlanVo>  projectInSelfRunPlanVoList = projectInSelfRunPlanService.loadProjectPlanListByPId(projectId);
		result.put("projectInSelfRunPlanVoList", projectInSelfRunPlanVoList);
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	/**
	 * 删除项目计划
	 * @param request
	 * @param projectId
	 * @param stepId
	 * @return
	 */
	@RequestMapping(value = APIConstants.DEL_PROJECT_PLAN, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> deleteProjectPlanByStepId(HttpServletRequest request,String projectId,String stepId) {
		Map<String, Object> result = new HashMap<String, Object>();
		int effectedRows = projectInSelfRunPlanService.deleteProjectPlanByStepId(projectId, stepId);
		
		if(effectedRows > 0){
			result = ResponseMapUtil.getSuccessResponseMap("0");
			
		    //项目操作日志
	        projectOperationLogService.addProjectOperationLog2("删除项目计划【" + stepId + "】" ,new Long(projectId),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
	        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_CONSULTANT);
		}else{
			result = ResponseMapUtil.getFailedResponseMap("1");
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	/**
	 * 修改项目计划
	 * @param request
	 * @param projectInSelfRunPlanVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.EDIT_PROJECT_PLAN, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> updateProjectPlanByStepId(HttpServletRequest request,ProjectInSelfRunPlanVo projectInSelfRunPlanVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		int effectedRows = projectInSelfRunPlanService.updateProjectPlan(projectInSelfRunPlanVo);
		
		if(effectedRows > 0){
			String projectId = projectInSelfRunPlanVo.getProjectId();
			int stepId = projectInSelfRunPlanVo.getStepId();
			
			ProjectInSelfRunPlanVo planVo = projectInSelfRunPlanService.getProjectPlanByProjectIdAndStepId(projectId, stepId);
			
			result.put("projectInSelfRunPlanVo",planVo);
			
		    //项目操作日志
	        projectOperationLogService.addProjectOperationLog2("修改项目计划【" + stepId + "】" ,new Long(projectId),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
	        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_CONSULTANT);
		}else{
			result = ResponseMapUtil.getFailedResponseMap("1");
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
    
	/**
	 * 项目计划：提交验收
	 */
	@RequestMapping(value = APIConstants.VER_PROJECT_PLAN_SUBMIT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> submitVerify(HttpServletRequest request,String projectId,String stepId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String creatorId = projectInSelfRunPlanService.getCreatorIdByProjectIdAndStepId(projectId);
			UserInfoVo userInfoVo = userService.getUserInfoByID(Long.valueOf(creatorId));
			ProjectInSelfRunVo projectInSelfRunVo = projectInSelfRunService.getProjectInSelfRun(projectId);
			
			//更新项目阶段进展的验证审核状态      验收中
			ProjectInSelfRunPlanVo projectInSelfRunPlanvo = projectInSelfRunPlanService.getProjectPlanByProjectIdAndStepId(projectId, Integer.parseInt(stepId));
			projectInSelfRunPlanvo.setVerifyStatus(2);
			int effectedRows = projectInSelfRunPlanService.updateProjectPlan(projectInSelfRunPlanvo);
			if(effectedRows>0){
				//邮件通知雇主 项目阶段验证
				projectMailService.sendSubmitVerify(userInfoVo,projectInSelfRunVo,Integer.valueOf(stepId));
				//手机短信通知   TODO
				 yunConnectService.sendApplyVerifySMS(userInfoVo.getMobile(), projectInSelfRunVo.getName());
				
				//增加一条记录    项目操作日志
				projectOperationLogService.addProjectOperationLog2("提交项目计划【" + stepId + "】验收" ,new Long(projectId),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
						ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
				result = ResponseMapUtil.getSuccessResponseMap("已申请，请等待雇主验收");
			}else{
				result = ResponseMapUtil.getFailedResponseMap("申请验收失败，系统维护中请稍后再试");
				log.error("updateProjectPlan  failed.");
			}
		}catch(Exception e){
			result = ResponseMapUtil.getFailedResponseMap("申请验收失败，系统维护中请稍后再试");
			log.error("submitVerify failed."+e);
			return new ResponseEntity<Map>(result, HttpStatus.OK);
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	/**
	 * 项目计划：验收通过
	 * @isPass  是否通过验证0通过   1不通过
	 */
	//TODO
	@RequestMapping(value = APIConstants.VER_PROJECT_PLAN_CHECK, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> checkVerify(HttpServletRequest request,String projectId,String stepId,Boolean isPass,String reason) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			ProjectInSelfRunVo projectInSelfRunVo = projectInSelfRunService.getProjectInSelfRun(projectId);
			// 先验证是否项目发布人验收
			if (CommonUtil.getCurrentLoginUser().getUserInfoVo().getId() != projectInSelfRunVo.getCreatorId()) {
				result = ResponseMapUtil.getFailedResponseMap("非法操作");
				return new ResponseEntity<Map>(result, HttpStatus.OK);
			}
			
			String developerId = projectInSelfRunPlanService.getExcutorIdByProjectIdAndStepId(projectId, stepId);
			UserInfoVo userInfoVo = userService.getUserInfoByID(Long.valueOf(developerId));
			ProjectInSelfRunPlanVo projectInSelfRunPlanvo = projectInSelfRunPlanService.getProjectPlanByProjectIdAndStepId(projectId, Integer.parseInt(stepId));
			
			//如果项目已经验收过了， 则提示用户(避免多次验收，导致给用户多次转账)
			if(projectInSelfRunPlanvo.getVerifyStatus() == 3){
				result = ResponseMapUtil.getFailedResponseMap("阶段验收失败，系统维护中请稍后再试");
				return new ResponseEntity<Map>(result, HttpStatus.OK);
			}
			String msg="";
			if(isPass){
				projectInSelfRunPlanvo.setVerifyStatus(3);
				projectInSelfRunPlanvo.setStatus(3);
				msg = "审核项目计划【" + stepId + "】验收通过";
			}else{
				projectInSelfRunPlanvo.setVerifyStatus(1);
				msg = "审核项目计划【" + stepId + "】验收驳回,原因:"+reason;
			}
			//更新项目阶段进展的验证审核状态  
			int effectedRows = projectInSelfRunPlanService.updateProjectPlan(projectInSelfRunPlanvo);
			if(effectedRows > 0){
				//邮件通知开发者 验收状态
				projectMailService.sendCheckVerify(userInfoVo,projectInSelfRunVo,isPass,Integer.valueOf(stepId));
				//增加一条验收记录
				projectOperationLogService.addProjectOperationLog2(msg ,new Long(projectId),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
						ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
				
				if(isPass){
					//手机短信通知   
					yunConnectService.sendVeridatePassSMS(userInfoVo.getMobile(), projectInSelfRunVo.getName());
					
					//TODO  将该项目阶段的金额汇入开发者的个人账号(后台执行)
//					在审核通过后，并且项目更新成功的情况下 执行        
//					double price = projectInSelfRunPlanvo.getPrice();
					
					//添加一条汇款记录
					UserAccountInDetailVo detailVo = new UserAccountInDetailVo();
					detailVo.setUserId(Integer.valueOf(developerId));//用户id
					detailVo.setProjectId(Integer.valueOf(projectInSelfRunPlanvo.getProjectId()));//项目id
					detailVo.setStepId(projectInSelfRunPlanvo.getStepId());//阶段id
					detailVo.setAmount(projectInSelfRunPlanvo.getPrice());//金额
					detailVo.setAccountId(userInfoVo.getAccountId());//支付账号id
					detailVo.setComment(projectInSelfRunVo.getName()+"项目第"+projectInSelfRunPlanvo.getStepId()+"阶段转入");//备注
					
					userService.addAccountInDetail(detailVo);
				}else{
					yunConnectService.sendVridateRejectSMS(userInfoVo.getMobile(), projectInSelfRunVo.getName());
				}
				
				IdentifyVo identifyVo = identifyService.getIdentifyByUID(userInfoVo.getId());
				// 记录财务日志
				String logSql = "INSERT INTO `sys_operation_log` ( `module`, `operate`, `remark`,"
						+ " `create_time`, `creator`) " + "VALUES ('%s', '%s', '%s', '%s', '%s')";
				// 备注：阶段1打款500元给开发者XX
				String remark = projectInSelfRunVo.getName() + "阶段" + projectInSelfRunPlanvo.getStepId() + "客户验收完成，系统内部转款给开发者:"
						+ identifyVo.getRealName();
				String creator = "客户:" + CommonUtil.getCurrentLoginUser().getUserInfoVo().getDisplayName();
				logSql = logSql.format(logSql, "财务管理", "内部转账", remark, DateUtil.getNow(), creator);
				dbUtil.excuteSql(logSql);

				result = ResponseMapUtil.getSuccessResponseMap("阶段已通过验收，验收结果系统将会以短信形式通知开发人员");
			}else{
				result = ResponseMapUtil.getFailedResponseMap("阶段验收失败，系统维护中请稍后再试");
				log.error("checkVerify failed.");
			}
		}catch(Exception e){
			result = ResponseMapUtil.getFailedResponseMap("阶段验收失败，系统维护中请稍后再试");
			log.error("checkVerify failed."+e);
			return new ResponseEntity<Map>(result, HttpStatus.OK);
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/***
	 * 新增项目反馈
	 * @param request
	 * @param projectInSelfRunMonitorVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.SAVE_PROJECT_MONITOR, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> savePorjectMonitor(HttpServletRequest request,ProjectInSelfRunMonitorVo projectInSelfRunMonitorVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		projectInSelfRunMonitorVo.setCreatorId(CommonUtil.getCurrentLoginUser().getUserInfoVo().getId()+"");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		projectInSelfRunMonitorVo.setCreateTime(format.format(new Date())+"");
		int stepId = projectInSelfRunPlanService.saveProjectMoniterServicer(projectInSelfRunMonitorVo);
		
		if(stepId > 0){
			String projectId = projectInSelfRunMonitorVo.getProjectId();
			
			ProjectInSelfRunMonitorVo monitorVo = projectInSelfRunPlanService.getProjectMonitorByPidAndStepId(projectId,stepId);
			projectInSelfRunMonitorVo.setStepId(stepId);
			
			result.put("projectInSelfRunMonitorVo",monitorVo);
			
			 //项目操作日志
	        projectOperationLogService.addProjectOperationLog2("新增项目反馈【" + stepId + "】" ,new Long(projectId),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
	        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
			
		}else{
			result = ResponseMapUtil.getFailedResponseMap("1");
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	} 
	
	/***
	 * 查询项目反馈
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = APIConstants.LOAD_PROJECT_MONITOR, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> loadPorjectMonitor(HttpServletRequest request,String projectId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProjectInSelfRunMonitorVo> projectInSelfRunMonitorList = projectInSelfRunPlanService.loadProjectMonitorListByPId(projectId);
		result.put("projectInSelfRunMonitorList", projectInSelfRunMonitorList);
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	} 
	
	/***
	 * 删除项目反馈
	 * @param request
	 * @param projectId
	 * @param stepId
	 * @return
	 */
	@RequestMapping(value = APIConstants.DEL_PROJECT_MONITOR, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> deletePorjectMonitor(HttpServletRequest request,String projectId,String stepId) {
		Map<String, Object> result = new HashMap<String, Object>();
		int effectedRows = projectInSelfRunPlanService.deleteProjectMonitorByStepId(projectId, stepId);
		
		if(effectedRows > 0){
			result = ResponseMapUtil.getSuccessResponseMap("0");
			
			 //项目操作日志
	        projectOperationLogService.addProjectOperationLog2("删除项目反馈【" + stepId + "】" ,new Long(projectId),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
	        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
		}else{
			result = ResponseMapUtil.getFailedResponseMap("1");
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	} 
	
	/**
	 * 修改项目反馈
	 * @param request
	 * @param projectInSelfRunMonitorVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.EDIT_PROJECT_MONITOR, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> updatePorjectMonitor(HttpServletRequest request,ProjectInSelfRunMonitorVo projectInSelfRunMonitorVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		int effectedRows = projectInSelfRunPlanService.updateProjectMonitor(projectInSelfRunMonitorVo);
		
		if(effectedRows > 0){
			result.put("projectInSelfRunMonitorVo", projectInSelfRunMonitorVo);
			
			 //项目操作日志
	        projectOperationLogService.addProjectOperationLog2("修改项目反馈【" + projectInSelfRunMonitorVo.getStepId() + "】" ,new Long(projectInSelfRunMonitorVo.getProjectId()),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
	        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
		}else{
			result = ResponseMapUtil.getFailedResponseMap("1");
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	} 
	
	/* 设置名称缩略显示 */
	private void setLetterName(SsoUserVo ssoUser, UserInfoVo userInfo) {
		// 名称显示缩略
		String name = "";
		String displayName = "";
		if (StringUtils.isEmpty(userInfo.getName())) {
			name = ssoUser.getLoginName();
			displayName = CommonUtil.getLittleStr(12, name);
		} else {
			name = userInfo.getName();
			displayName = CommonUtil.getLittleStr(8, name);
		}
		userInfo.setDisplayName(displayName);
	}


	/**
	 * 顾问审核项目列表
	 */
	@RequestMapping(value = APIConstants.LOAD_ATTEND_PROJ, method = RequestMethod.POST)
	@ResponseBody
	public PageResult listProject(HttpServletRequest request,String projectType,String projectStatus,String keywords) {
		Map<String, Object> result = new HashMap<String, Object>();
		SsoUserVo ssoUserVo = CommonUtil.getCurrentLoginUser();
		String  userType = "0";
        String consultantId = "";
        
		boolean isAccess = true;
		
		if(ssoUserVo != null){
			userType = ssoUserVo.getUserInfoVo().getUserType() + "";
			
			if(Integer.parseInt(userType)==2||Integer.parseInt(userType) == -1){
				consultantId = ssoUserVo.getUserInfoVo().getId() + "";
			}else{
				isAccess = false;
			}
		}else{
			isAccess = false;
		}
		
		//query = query ==null?"":query;
		PageResult page = new PageResult();
		
		int currentPage = request.getParameter("currentPage")==null?1:Integer.parseInt(request.getParameter("currentPage"));
		int pageSize = request.getParameter("pageSize")==null?10:Integer.parseInt(request.getParameter("pageSize"));
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);		
		
		List<ProjectInSelfRunVo> projectInSelfRunList = new ArrayList<ProjectInSelfRunVo>();
		if(isAccess){
			//if(currentPage==1){
				int queryTotalCount = projectInSelfRunService.getAttendProjSelfRunListCount(consultantId,userType,projectType,projectStatus,page,keywords);
				//result.put("totalRow", queryTotalCount);
				page.setTotalRow(queryTotalCount);
			//}
				
			projectInSelfRunList = projectInSelfRunService.getAttendProjSelfRunList(consultantId,userType,projectType,projectStatus,page,keywords);
			
			page.setData(projectInSelfRunList);
		}
		//result.put("data", projectInSelfRunList);
		//return new ResponseEntity(result, HttpStatus.OK);
		return page;
	}
	
	/**
	 * 顾问备注列表
	 */
	@RequestMapping(value = APIConstants.LOAD_JOINER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResult> remarkJoiner(HttpServletRequest request,String projectId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProjectSelfRunPushVo> joinerList = projectInSelfRunService.getRemarkDeveloperListByPID(projectId);
		result.put("joinerList", joinerList);
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	/**
	 * 顾问备选用户 
	 */
	@RequestMapping(value = APIConstants.UPDATE_JOINER, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResult> alterJoiner(HttpServletRequest request,ProjectSelfRunPushVo projectSelfRunPushVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		int returnCode = projectInSelfRunService.updateRemarkDeveloper(projectSelfRunPushVo);
		List<ProjectSelfRunPushVo> joinerList = projectInSelfRunService.getRemarkDeveloperListByPID(projectSelfRunPushVo.getProjectId());
		result.put("joinerList", joinerList);
		result.put("returnCode", returnCode);
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	/**
	 * 项目详情列表
	 */
	@RequestMapping(value = APIConstants.LOAD_PROJECT_LIST, method = RequestMethod.POST)
	@ResponseBody
	public PageResult loadProjectList(HttpServletRequest request,String query,String typeStr,String status) {
		query = query == null?"":query;		
		typeStr = typeStr ==null?"":typeStr.trim();
		status = status ==null?"":status.trim().equals("-2")?null:status;
		PageResult page = new PageResult();
		int currentPage = request.getParameter("currentPage")==null?1:Integer.parseInt(request.getParameter("currentPage"));
		int pageSize = request.getParameter("pageSize")==null?12:Integer.parseInt(request.getParameter("pageSize"));
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);	
		List<ProjectInSelfRunVo> listData = projectInSelfRunService.getProjectList(page, query,typeStr,status);
		int totalRow = projectInSelfRunService.getProjectListCount(page, query,typeStr,status);
		List<ProjectInSelfRunVo> deleteListData = new ArrayList<>();
		page.setTotalRow(totalRow);
		page.setData(listData);
		return page;
	}
	
	/***
	 * 修改项目需求
	 * @param request
	 * @param ProjectInSelfRunVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROJECT_MODIFY, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modifyProjectInSelfRun(HttpServletRequest request,ProjectInSelfRunVo projectVo,String reason) {
		SsoUserVo ssoUserVo = CommonUtil.getCurrentLoginUser();
		Map<String, Object> result;
		
		if(ssoUserVo != null){
			UserInfoVo userVo = userService.getUserInfoByID(ssoUserVo.getUserId());
			
			int userType = userVo.getUserType();
			
			if(userType != -1 && userType != 2){
				result = ResponseMapUtil.getFailedResponseMap("您没有权限修改");
				
				return new ResponseEntity(result, HttpStatus.OK);
			}				
		}
		
		result = checkPublishArgs4Modification(request, projectVo);
        //获得项目当前状态
		ProjectInSelfRunVo projectInSelfRun = projectInSelfRunService.getProjectInSelfRun(projectVo.getId());
		UserInfoVo consultant = userService.getUserInfoByID(projectInSelfRun.getConsultantId());
		String repoOwner = consultant.getMobile();
		String repoOwnerName =  consultant.getName();
		UserInfoVo newConsultant = userService.getUserInfoByID(projectVo.getConsultantId());
		String newRepoOwner = newConsultant.getMobile();
		String newRepoOwnerName = newConsultant.getName();
		String repoName = projectInSelfRun.getRepoNick();
		
		int oldStatus = projectInSelfRun.getStatus();
		
//		if(projectVo.getStatus() == oldStatus){
//			result = ResponseMapUtil.getSuccessResponseMap("更新成功");
//			return new ResponseEntity(result, HttpStatus.OK);
//		}
		
		if(result == null){
			if(projectVo != null){
				//SsoUserVo ssoUserVo = CommonUtil.getCurrentLoginUser();
				//ssoUserVo = ssoService.getSsoUserById(ssoUserVo.getId());

				if(ssoUserVo.getUserId() != 1 && projectVo.getStatus() == 3){
					projectVo.setStatus(-1); //项目重新进入审核状态
				}
				
				int affectedItem = projectInSelfRunService.updateProjectInSelfRun(projectVo);
				
				if(affectedItem == 1){		
					//如果是关闭项目，则邮件通知参与者关闭理由           -1审核中0待启动1开发中2已完成3未通过4已关闭
					if(projectVo.getStatus() == 4){
						List<String> mailList = new ArrayList<String>();
						if(oldStatus == 0){
							//所有报名的开发者
							List<ProjectSelfRunPushVo> signDevList = projectInSelfRunService.getEnrollDevlopersbyProjectId(new Long(projectVo.getId()));
							for(int i=0;i<signDevList.size();i++){
								String email = signDevList.get(i).getDeveloper().getEmail();
								mailList.add(email);
							}
							projectMailService.sendProjectClose(mailList,projectInSelfRun,reason);
						}else{
							//被选中的开发者
							List<ProjectInSelfRunHandlerVo> choosenDevList = projectInSelfRunService.getProjectSelfRunChosenDevlopers(Long.parseLong(projectVo.getId()));
							for(int i=0;i<choosenDevList.size();i++){
								String email = choosenDevList.get(i).getDeveloper().getEmail();
								mailList.add(email);
							}
							projectMailService.sendProjectClose(mailList,projectInSelfRun,reason);
						}
						//项目操作日志
						projectOperationLogService.addProjectOperationLog2("项目关闭成功" ,new Long(projectVo.getId()),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
								ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
					}else{
						//项目操作日志
						projectOperationLogService.addProjectOperationLog2("项目修改成功" ,new Long(projectVo.getId()),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
								ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
					}
					
					//顾问切换
					if(!newRepoOwner.equals(repoOwner)){
						//项目操作日志
						projectOperationLogService.addProjectOperationLog2("项目顾问切换：从'" + repoOwnerName +  "'到'" + newRepoOwnerName+"'",new Long(projectVo.getId()),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
								ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
						GogsUtil.transferRepoOwner(repoName, repoOwner, newRepoOwner);
					}
					result = ResponseMapUtil.getSuccessResponseMap("更新成功");
				}else{
					result = ResponseMapUtil.getFailedResponseMap("更新失败");
				}
			}
		}	
		
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	/***
	 * 获取项目信息
	 * @param request
	 * @param ProjectInSelfRunVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROJECTINSELFRUN_GET, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getProjectInSelfRun(HttpServletRequest request,String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjctInSelfRunWithoutDecorate(id);
		UserInfoVo consultant = userService.getUserInfoByID(projectVo.getConsultantId());
		projectVo.setConsultant(consultant);
		
		List<DictItemVo> listDictItem = dictService.getAllDictItem();
		result.put("listDictItem", listDictItem);
		result.put("projectInSelfRun", projectVo);
		
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	// 校验参数是否合法
	private Map<String, Object> checkPublishArgs4Modification(HttpServletRequest request, ProjectInSelfRunVo projectVo) {
		String type = projectVo.getType();
		String buget = projectVo.getBudget();
		String name = projectVo.getName();
		String content = projectVo.getContent();
		String startTime = projectVo.getStartTime();

		if (StringUtils.isEmpty(buget) || StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(content) || StringUtils.isEmpty(type) || StringUtils.isEmpty(startTime)) {
			return ResponseMapUtil.getFailedResponseMap("必填项参数不可为空");
		}

		// 返回空表示校验通过
		return null;
	}
	
	
	/***
	 * 新增项目日志
	 * @param request
	 * @param ProjectInSelfRunVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROJECT_OPERATION_LOG_ADD, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addProjectOperationLog(HttpServletRequest request,ProjectOperationLogVo logVo) {
		long operationLogId = projectOperationLogService.addProjectOperationLog(logVo);
		
		if(operationLogId > 0){
			logVo = projectOperationLogService.getProjectOperationLogById(operationLogId);
			return new ResponseEntity(logVo, HttpStatus.OK);
		}else{
			return new ResponseEntity(null, HttpStatus.OK);
		}
		
	}
	
	/***
	 * 修改项目日志
	 * @param request
	 * @param ProjectOperationLogVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROJECT_OPERATION_LOG_MODIFY, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modifyProjectOperationLog(HttpServletRequest request,ProjectOperationLogVo logVo) {
		int effectedRow = projectOperationLogService.updateProjectOperationLog(logVo);
		
		if(effectedRow > 0){
			logVo = projectOperationLogService.getProjectOperationLogById(logVo.getId());
		    return new ResponseEntity(logVo, HttpStatus.OK);
		}else{
			return new ResponseEntity(null, HttpStatus.OK);
		}
	}
	
	
	/***
	 * 删除项目日志
	 * @param request
	 * @param ProjectOperationLogVo
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROJECT_OPERATION_LOG_DELETE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity deleteProjectOperationLog(HttpServletRequest request,long logId) {
		Map<String, Object> result = new HashMap<String, Object>();
		int effectedRow = projectOperationLogService.deleteProjectOperationLog(logId);
		
		if(effectedRow > 0){
			result = ResponseMapUtil.getSuccessResponseMap("0");
		}else{
			result = ResponseMapUtil.getFailedResponseMap("1");
		}
		
		return new ResponseEntity(result, HttpStatus.OK);
	}
    
	/**
	 * 项目报名列表
	 * 
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROJECTINSELFRUN_ENROLL_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getProjectEnrollList(HttpServletRequest request, long projectId) {
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();

		if (userInfo.getUserType() == 2) {
			List<ProjectSelfRunPushVo> pushVos = projectInSelfRunService
					.getEnrollDevlopersbyProjectId(projectId);

			return new ResponseEntity(pushVos, HttpStatus.OK);
		} else {
			Map<String, Object> result = ResponseMapUtil.getFailedResponseMap("没有权限查询");

			return new ResponseEntity(result, HttpStatus.OK);
		}
	}
	 
	/**
	 * 项目推送列表
	 * 
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROJECTINSELFRUN_PUSH_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PageResult> getProjectPushList(HttpServletRequest request, long projectId,
			PageResult page) {
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();

		if (userInfo.getUserType() == 2) {
			List<ProjectSelfRunPushVo> pushVos = projectInSelfRunService
					.getRealProjectSelfRunPushsbyProjectId(projectId, page);
			page.setData(pushVos);
			int count = projectInSelfRunService.getRealProjectSelfRunPushsCount(projectId);
			page.setTotalRow(count);
			return new ResponseEntity<PageResult>(page, HttpStatus.OK);
		} else {
			Map<String, Object> result = ResponseMapUtil.getFailedResponseMap("没有权限查询");

			return new ResponseEntity(result, HttpStatus.OK);
		}
	}
	
	/**
	 * 选中开发者
	 */
	@RequestMapping(value = APIConstants.PROJECTINSELFRUN_ADD_DEV, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> addProjectSelfRunDev(HttpServletRequest request, long developerId,String chosenRole,
			long projectId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
	   ProjectSelfRunPushVo devVo = projectInSelfRunService.getProjectInSelfRunPushInfoByID(projectId,developerId);
	   devVo.setChosenRole(chosenRole);
	   
	   int effectedResult = projectInSelfRunService.addProjectInSelfRunDev(devVo);
	   
	   if(effectedResult > 0){
			ProjectInSelfRunVo projectInSelfRunVo = projectInSelfRunService.getProjectInSelfRun(new Long(projectId).toString());
			
			UserInfoVo consultantVo = userService.getUserInfoByID(projectInSelfRunVo.getConsultantId());
			if(projectInSelfRunVo.getStatus()== 0 && projectInSelfRunVo.getIsGogsAllocated().equals("1")){
				//分配角色GIT权限
				GitHttpClient gitHttpClient = new GitHttpClient();
				gitHttpClient.loginAdmin();
				String repoUser = consultantVo.getMobile();
				String repoName = projectInSelfRunVo.getRepoNick();
				UserInfoVo userVo = userService.getUserInfoByID(devVo.getDeveloperId());
				
				boolean gitAddResult = gitHttpClient.addUserForProject(userVo.getMobile(), repoUser, repoName);
				
				if(gitAddResult){
					//result = ResponseMapUtil.getSuccessResponseMap("0");
					devVo = projectInSelfRunService.getProjectInSelfRunPushInfoByID(projectId,developerId);
					result.put("chosenDev", devVo);
					
					//   选中开发者，邮件通知
					projectMailService.sendAddChooseDeveloper(userVo,projectInSelfRunVo);
					
					 //项目操作日志
			        projectOperationLogService.addProjectOperationLog2("选中开发者:" + devVo.getName(),projectId,CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
			        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_CONSULTANT);
					
				}
			}
	   }else{
		   result = ResponseMapUtil.getFailedResponseMap("1");
	   }
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	} 
	
	/**
	 * 撤销开发者
	 * @param request
	 * @param developerId
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROJECTINSELFRUN_DELETE_DEV, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> deleteProjectSelfRunDev(HttpServletRequest request, long developerId,long projectId) {
		Map<String, Object> result = new HashMap<String, Object>();		
		
		//移除Git关联
		ProjectInSelfRunVo projectInSelfRunVo = projectInSelfRunService.getProjectInSelfRun(new Long(projectId).toString());
		UserInfoVo consultantVo = userService.getUserInfoByID(projectInSelfRunVo.getConsultantId());
		UserInfoVo deletedVo = userService.getUserInfoByID(developerId);
		String deleteVoMobile = deletedVo.getMobile();
		boolean removeSuccess = false;
		
		if("1".equals(projectInSelfRunVo.getIsGogsAllocated())){
			removeSuccess = GogsUtil.removeUserForProject(deleteVoMobile, consultantVo.getMobile(), projectInSelfRunVo.getRepoNick());	
		}		 
		
		if(removeSuccess){
			//已添加角色
			List<UserInfoVo> planUserInfoList = userService.getProjectInSelfRun(new Long(projectId).toString());
			
			//撤销开发者
			ProjectSelfRunPushVo pushVo = projectInSelfRunService.getProjectInSelfRunPushInfoByID(projectId, developerId);
			int effectedRows = projectInSelfRunService.deleteProjectInSelfRunDev(pushVo);
			
			if(effectedRows > 0){
				result = ResponseMapUtil.getSuccessResponseMap("0");
				//   撤销开发者后，邮件通知
				projectMailService.sendRemoveChoosenDeveloper(deletedVo,projectInSelfRunVo);
				 //项目操作日志
		        projectOperationLogService.addProjectOperationLog2("撤销开发者:" + pushVo.getDeveloper().getName() ,projectId,CommonUtil.getCurrentLoginUser().getUserId(),
		        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_CONSULTANT);
			}else{
				result = ResponseMapUtil.getFailedResponseMap("1");
			}
			
			//如果删掉了所有的开发人员，项目的状态自动变成为"待启动"
			if(planUserInfoList == null || planUserInfoList.size() == 0){
				if(projectInSelfRunVo.getStatus() != 0){
					ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjctInSelfRunWithoutDecorate(new Long(projectId).toString());
					projectVo.setStatus(0);
					projectInSelfRunService.updateProjectInSelfRun(projectVo);
					
					 //项目操作日志
			        projectOperationLogService.addProjectOperationLog2("项目进入待启动",projectId,CommonUtil.getCurrentLoginUser().getUserId(),
			        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
				}
			}
		}else{
			result = ResponseMapUtil.getFailedResponseMap("1");
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	/**
	 * 项目进入开发状态
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROJECTINSELFRUN_GOTO_DEV_STAUTS, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> projectGo2DevStatus(HttpServletRequest request,long projectId,String proAccount) {
		Map<String, Object> result = new HashMap<String, Object>();		
		
		int chosenDevCount = projectInSelfRunService.getProjectRunHandlerCount(new Long(projectId).toString());
		
		if(chosenDevCount <= 0){
			result = ResponseMapUtil.getFailedResponseMap("2"); //此项目没有开发人员
			
			return new ResponseEntity<Map>(result,HttpStatus.OK);
		}
		
		ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjctInSelfRunWithoutDecorate(new Long(projectId).toString());
		
		if(projectVo != null){
			projectVo.setStatus(1); //项目进入开发状态
			
			//更新数据
			projectVo.setDealCost(proAccount);
			int effectedRows = projectInSelfRunService.updateProjectInSelfRun(projectVo);
			if(effectedRows > 0){
				List<String> mailList = new ArrayList<String>();
				//所有报名的开发者
				List<ProjectSelfRunPushVo> signDevList = projectInSelfRunService.getEnrollDevlopersbyProjectId(new Long(projectVo.getId()));
				for(int i=0;i<signDevList.size();i++){
					String email = signDevList.get(i).getDeveloper().getEmail();
					mailList.add(email);
				}
				//被选中的开发者
				List<ProjectInSelfRunHandlerVo> choosenDevList = projectInSelfRunService.getProjectSelfRunChosenDevlopers(Long.parseLong(projectVo.getId()));
				for(int i=0;i<choosenDevList.size();i++){
					if(mailList.contains(choosenDevList.get(i).getDeveloper().getEmail())){
						mailList.remove(choosenDevList.get(i).getDeveloper().getEmail());
					}
				}
				//邮件 通知没有被选中的开发人员
				projectMailService.sendNotChoosen(mailList,projectVo);
				 //项目操作日志
		        projectOperationLogService.addProjectOperationLog2("项目进入开发",projectId,CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
		        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
				
				result = ResponseMapUtil.getSuccessResponseMap("0");
			}else{
				result = ResponseMapUtil.getFailedResponseMap("1");
			}
		}else{
			result = ResponseMapUtil.getFailedResponseMap("1");
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	
	/**
	 * 项目进入完成状态
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROJECTINSELFRUN_GOTO_COMPLETE_STAUTS, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> projectGo2CompleteStatus(HttpServletRequest request,long projectId) {
		Map<String, Object> result = new HashMap<String, Object>();		
		
		ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjctInSelfRunWithoutDecorate(new Long(projectId).toString());
		
		if(projectVo != null){
			projectVo.setStatus(2); //项目进入已完成状态
			
			//更新数据
			int effectedRows = projectInSelfRunService.updateProjectInSelfRun(projectVo);
			
			if(effectedRows > 0){		
				 //项目操作日志
		        projectOperationLogService.addProjectOperationLog2("项目完成",projectId,CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
		        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);
				
				result = ResponseMapUtil.getSuccessResponseMap("0");
			}else{
				result = ResponseMapUtil.getFailedResponseMap("1");
			}
		}else{
			result = ResponseMapUtil.getFailedResponseMap("1");
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	/**
	 * 获取项目可用资金(项目资金 - 项目各个阶段总资金)
	 * @param request
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = APIConstants.AVALID_PRICE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> getAvaliblePlanPrice(HttpServletRequest request,long projectId) {
		Map<String, Object> result = new HashMap<String, Object>();		
		
		ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjctInSelfRunWithoutDecorate(new Long(projectId).toString());
		String budget = projectVo.getDealCost();
		budget = budget.replace("元", "").replace(",","");
		if(budget.contains("-") || budget.contains("万") || budget.contains("待商议")){
			result.put("avaliPrice", -1);
		}else{
			double usedAmount = projectInSelfRunService.getProjectPlanAvalibleAmount((int)projectId);
			double totalAmount = Double.valueOf(budget);
			result.put("avaliPrice", totalAmount - usedAmount);
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
}

@Data
class ListParam {
	private List<ProjectInSelfRunHandlerVo> list;
	private List<ProjectSelfRunPushVo> pushList;
}