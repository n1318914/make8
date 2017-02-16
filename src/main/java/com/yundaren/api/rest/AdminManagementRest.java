package com.yundaren.api.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
import com.yundaren.common.constants.ProjectInSelfRunConstants;
import com.yundaren.common.constants.UrlRewriteConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.DateUtil;
import com.yundaren.common.util.EdaiceUtil;
import com.yundaren.common.util.EmailSender;
import com.yundaren.common.util.GogsUtil;
import com.yundaren.common.util.PageResult;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.EmailSendConfig;
import com.yundaren.support.service.ExamService;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.service.ProjectInSelfRunService;
import com.yundaren.support.service.ProjectMailService;
import com.yundaren.support.service.ProjectOperationLogService;
import com.yundaren.support.service.ProjectService;
import com.yundaren.support.service.YunConnectService;
import com.yundaren.support.vo.ExamPaperVo;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.support.vo.ProjectAssignVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.ProjectOperationLogVo;
import com.yundaren.support.vo.ProjectVo;
import com.yundaren.support.vo.WeixinProjectVo;
import com.yundaren.user.biz.UserBiz;
import com.yundaren.user.service.SsoService;
import com.yundaren.user.service.UserExperienceService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.EmployeeEduExperienceVo;
import com.yundaren.user.vo.EmployeeJobExperienceVo;
import com.yundaren.user.vo.EmployeeProductVo;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserAccountVo;
import com.yundaren.user.vo.UserInfoImportVo;
import com.yundaren.user.vo.UserInfoVo;

/**
 * 后台管理API
 */
@Controller
@Slf4j
public class AdminManagementRest {

	@Setter
	private ProjectService projectService;

	@Setter
	private UserService userService;

	@Setter
	private SsoService ssoService;
	
	@Setter
	private DomainConfig domainConfig;

	@Setter
	private IdentifyService identifyService;

	@Setter
	private ProjectMailService projectMailService;

	@Setter
	private UserExperienceService userExperienceService;

	@Setter
	private DictService dictService;

	@Setter
	private ProjectInSelfRunService projectInSelfRunService;

	@Setter
	private EmailSendConfig emailSendConfig;

	@Autowired
	private ExamService examService;

	@Setter
	private YunConnectService yunConnectService;
	
	@Setter
	private ProjectOperationLogService projectOperationLogService;
	
	@Setter
	private UserBiz userBiz;

	/**
	 * 审核通过
	 */
	@RequestMapping(value = APIConstants.CHECK_PASS, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity checkPass(HttpServletRequest request, final String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjctInSelfRunWithoutDecorate(id);
		//创建GIT库
		int createSuccess =-1;
		boolean isSuccess = false;
		String repo_name = request.getParameter("repo_name");
		String description = request.getParameter("description");
		String enrollRole = request.getParameter("enrollRole");
		
		UserInfoVo consultantVo = userService.getUserInfoByID(projectVo.getConsultantId());
		if(consultantVo!=null&&repo_name!=null&&!repo_name.trim().equals("")&&!projectVo.getIsGogsAllocated().equals("1")){
			//验证是否存在此关联
			String consultMobile = consultantVo.getMobile();
			int existCount = projectInSelfRunService.getExistRepoNameCount(repo_name);	
			if(existCount==0) {
				isSuccess = GogsUtil.transferRepoByAdmin(consultMobile, repo_name, description);
			}
		}
		if(isSuccess){
			//更新仓库关联记录
			projectInSelfRunService.updateRepoAllocated("1", repo_name,projectVo.getId());
			//createSuccess = 1;
			//result.put("createSuccess", "1");
			
			if (projectVo != null) {
				projectVo.setStatus(0); // 0表示审核通过，项目进入到启动阶段
				projectVo.setEnrollRole(enrollRole);
				long checkerId = CommonUtil.getCurrentLoginUser().getUserInfoVo().getId();
				projectVo.setCheckerId(checkerId);
                
				//审核通过时间
				projectVo.setReviewTime(new Date());
				int affectedItem = projectInSelfRunService.updateProjectInSelfRun(projectVo);

				if (affectedItem == 1) {
					result = ResponseMapUtil.getSuccessResponseMap("审核成功");
					
					 //项目操作日志
			        projectOperationLogService.addProjectOperationLog2("项目审核通过" ,new Long(projectVo.getId()),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
			        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);

				
				} else {
					result = ResponseMapUtil.getFailedResponseMap("审核失败");
				}

				// 审核结果通知
				sendProjectInSelfRunCheckResult(projectVo, true);
			} else {
				result = ResponseMapUtil.getFailedResponseMap("没有找到该项目");
			}
		}else{
			//result.put("createSuccess", "-1");
			result = ResponseMapUtil.getFailedResponseMap("仓库别名已存在");
		}
		
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 审核不通过
	 */
	@RequestMapping(value = APIConstants.CHECK_INVALID, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity checkReject(HttpServletRequest request, String id, String reason) {
		Map<String, Object> result = new HashMap<String, Object>();
		/*UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		projectService.update2CheckStatus(1, userInfo.getId(), id, reason);*/

		ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjctInSelfRunWithoutDecorate(id);

		if (projectVo != null) {
			projectVo.setStatus(3); // 3表示审核不通过
			projectVo.setCheckResult(reason);
			projectVo.setCheckerId(CommonUtil.getCurrentLoginUser().getUserInfoVo().getId());

			int affectedItem = projectInSelfRunService.updateProjectInSelfRun(projectVo);

			if (affectedItem == 1) {
				result = ResponseMapUtil.getSuccessResponseMap("审核成功");
				
				 //项目操作日志
		        projectOperationLogService.addProjectOperationLog2("项目审核不通过" ,new Long(projectVo.getId()),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
		        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);

			} else {
				result = ResponseMapUtil.getFailedResponseMap("审核失败");
			}

			// 审核结果通知
			sendProjectInSelfRunCheckResult(projectVo, false);
		} else {
			result = ResponseMapUtil.getFailedResponseMap("没有找到该项目");
		}

		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 管理员给服务商备注
	 */
	@RequestMapping(value = APIConstants.MEMBER_REMARK, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modifyMemberRemark(HttpServletRequest request, long uid, String remark)
			throws UnsupportedEncodingException {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(remark)) {
			UserInfoVo userInfoVo = new UserInfoVo();
			userInfoVo.setId(uid);
			userInfoVo.setRemark(remark);
			userService.updateUserInfo(userInfoVo);
		}
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 用户列表
	 * 
	 * @param type
	 *            用户类型(-2所有，0雇主，服务商1)
	 * @param status
	 *            认证状态(-2所有，0审核中，1认证通过，2认证驳回)
	 * @param category
	 *            认证类型(0个人，1企业)
	 */
	@RequestMapping(value = APIConstants.ADMIN_USER_LIST, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<PageResult> listAllUser(HttpServletRequest request, int type, int status,
			String category, PageResult page) {
		List resultList = new ArrayList<>();
		List<UserInfoVo> userList = userService.getListAllUserInfo(type, status, category, page);
		setLettleDisplay(userList);

		page.setData(userList);
		int count = userService.getAllUserInfoCount(type, status, category);
		page.setTotalRow(count);
		return new ResponseEntity<PageResult>(page, HttpStatus.OK);
	}

	/**
	 * 跳转到审核页面
	 */
	@RequestMapping(value = APIConstants.CHECK_VIEW, method = RequestMethod.GET)
	public String checkProject(HttpServletRequest request, String id) throws IOException {
		ProjectVo projectVo = projectService.getProjectDetailsById(id);
		request.setAttribute("project", projectVo);
		// 跳转到审核页面
		return "admin/request_review";
	}

	/**
	 * 托管资金审核通过，修改托管状态至工作中
	 */
	@RequestMapping(value = APIConstants.CHECK_TRUSTEE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity trustee(HttpServletRequest request, String id, double amount) {
		Map<String, Object> result = new HashMap<String, Object>();
		projectService.updateTrusteeInfo(id, amount);
		result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),
				UrlRewriteConstants.REWRITE_PROJECT_DETAILS + id);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 管理员认证录入简历信息
	 */
	@RequestMapping(value = APIConstants.IDENTIFY_RESUME_INPUT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity identifyPass(HttpServletRequest request, UserInfoVo userInfo, int uid) {
		UserInfoVo user = userBiz.getUserInfoByID((int)uid);
		UserAccountVo userAccountVo = new UserAccountVo();
		
		//修改用户支付账号，如果没有则添加
		if(0 == user.getAccountId() || -1 == user.getAccountId()){
			userAccountVo.setAccountNum(userInfo.getAccountNum());
			userAccountVo.setAccountType(1);
			userAccountVo.setUserId(uid);
			int userAccountId = userBiz.addUserPayAccount(userAccountVo);
			userInfo.setAccountId(userAccountId);
		}else{
			userAccountVo.setAccountNum(userInfo.getAccountNum());
			userAccountVo.setId(user.getAccountId());
			userAccountVo.setUserId(uid);
			userBiz.editUserPayAccount(userAccountVo);
		}
		
		// 更新用户信息
		userInfo.setId(uid);
		userInfo.setResumeType("input");
		userService.updateUserInfo(userInfo);

		userExperienceService.deleteUserAllExp(uid);
		userExperienceService.addUserExperience(userInfo.getEmployeeEduExperience(), uid);
		userExperienceService.addUserExperience(userInfo.getEmployeeJobExperience(), uid);
		userExperienceService.addUserExperience(userInfo.getEmployeeProduct(), uid);
		userExperienceService.addUserExperience(userInfo.getEmployeeProjectExperience(), uid);

		return identifyPass(request, uid);
	}

	/**
	 * 认证审核操作--通过
	 */
	@RequestMapping(value = APIConstants.IDENTIFY_PASS, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity identifyPass(HttpServletRequest request, int uid) {
		Map<String, Object> result = new HashMap<String, Object>();
		IdentifyVo identifyVo = new IdentifyVo();
		identifyVo.setUserId(uid);
		identifyVo.setStatus(1);// 通过
		identifyVo.setPassTime(new Date());
		identifyService.updateIdentifyByUID(identifyVo);
		projectMailService.sendIdentifyResult(identifyVo);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 认证审核操作--不通过
	 */
	@RequestMapping(value = APIConstants.IDENTIFY_INVALID, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity identifyReject(HttpServletRequest request, int uid, String reason) {
		Map<String, Object> result = new HashMap<String, Object>();
		IdentifyVo identifyVo = new IdentifyVo();
		identifyVo.setUserId(uid);
		identifyVo.setStatus(2);// 不通过
		identifyVo.setFailReason(reason);
		identifyService.updateIdentifyByUID(identifyVo);
		projectMailService.sendIdentifyResult(identifyVo);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 跳转到认证审核页面
	 */
	@RequestMapping(value = APIConstants.IDENTIFY_CHECK, method = RequestMethod.GET)
	public String checkIdentify(HttpServletRequest request, long uid) throws IOException {
		UserInfoVo userInfo = userService.getUserInfoByID(uid);
		long userId = userInfo.getId();
		IdentifyVo identifyInfo = identifyService.getIdentifyByUID(userId);
		userInfo.setIdentifyInfo(identifyInfo);

		// 设置用户经历
		if (identifyInfo != null) {
			if (identifyInfo.getCategory() == 0) {
				userInfo.setEmployeeEduExperience(userExperienceService.getUserExpListById(userId,
						EmployeeEduExperienceVo.class));
				userInfo.setEmployeeJobExperience(userExperienceService.getUserExpListById(userId,
						EmployeeJobExperienceVo.class));
				userInfo.setEmployeeProduct(userExperienceService.getUserExpListById(userId,
						EmployeeProductVo.class));
			} else {
				userInfo.setEmployeeProjectExperience(userExperienceService.getUserExpListById(userId,
						EmployeeTeamProjectExperienceVo.class));
			}
		}
		
		// 分类列表
		List<DictItemVo> listDictItem = dictService.getAllDictItem();

		request.setAttribute("userInfo", userInfo);
		request.setAttribute("listDictItem", listDictItem);
		return "admin/user_review";
	}

	/**
	 * 确认工作完成，管理员审核付款
	 * 
	 * @param amount
	 *            最终实际交付金额
	 * @param peroid
	 *            最终实际开发周期
	 */
	@RequestMapping(value = APIConstants.CHECK_FINISH, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity finish(HttpServletRequest request, String id, double amount, int peroid) {
		Map<String, Object> result = new HashMap<String, Object>();
		projectService.update2Finish(id, amount, peroid);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 管理员查询用户接口
	 */
	@RequestMapping(value = APIConstants.QUERY_USERS, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<PageResult> queryUsers(HttpServletRequest request, String query,PageResult page)
			throws IOException {

		List resultList = new ArrayList<>();
		
	    UserInfoVo userVo = userService.getUserInfoByID(CommonUtil.getCurrentLoginUser().getUserId());
	    int userType = userVo.getUserType();
	    
	    List<UserInfoVo> userList = null;
	    int count = 0;
	    
	    if(userType == 2){
	    	userList = userService.queryUsers4Consultant(query,page);
	    	count = userService.queryUsersCount4Consultant(query);
	    }else{
	    	userList = userService.queryUsers(query,page);
	    	count = userService.queryUsersCount(query);
	    }
		
		setLettleDisplay(userList);

		page.setData(userList);
		page.setTotalRow(count);
		return new ResponseEntity<PageResult>(page, HttpStatus.OK);
	}

	/**
	 * 管理员查询服务商信息页面
	 */
	@RequestMapping(value = APIConstants.MEMBER_QUERY, method = RequestMethod.GET)
	public String viewMemberInfo(HttpServletRequest request, long uid) throws IOException {
		UserInfoVo userInfo = userService.getUserInfoByID(uid);
		// 生成公司图片数组
		String comPicList = userInfo.getComPicList();
		// 分解
		String[] comPicListContainer = new String[1];
		comPicListContainer[0] = "";
		if (comPicList != null && !"".equals(comPicList.trim())) {
			comPicListContainer = comPicList.split(",");
			for (int i = 0; i < comPicListContainer.length; i++) {
				comPicListContainer[i] = comPicListContainer[i].substring(1,
						comPicListContainer[i].length() - 1);
			}
		}
		userInfo.setComPicListContainer(comPicListContainer);
		IdentifyVo identifyInfo = identifyService.getIdentifyByUID(userInfo.getId());
		userInfo.setIdentifyInfo(identifyInfo);
		// 图片路径
		String picDomainPath = domainConfig.getBindDomain();
		request.setAttribute("picDomainPath", picDomainPath);
		request.setAttribute("userInfo", userInfo);
		return "admin/compinfo_modify";
	}

	/**
	 * 管理员保存服务商信息接口
	 */
	@RequestMapping(value = APIConstants.MEMBER_MODIFY, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity saveMembers(HttpServletRequest request,
			@ModelAttribute("com-info-form") UserInfoVo userInfoVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		int returnCode = userService
				.modifyEmployeeForUser(userInfoVo, identifyService, userExperienceService);
		if (returnCode > 0)
			result.put("message", "保存成功!");
		return new ResponseEntity(result, HttpStatus.OK);
	}

	private void setLettleDisplay(List<UserInfoVo> userList) {
		for (UserInfoVo uInfo : userList) {
			if (!StringUtils.isEmpty(uInfo.getName())) {
				String displayName = CommonUtil.getLittleStr(8, uInfo.getName());
				uInfo.setDisplayName(displayName);
			}
			if (!StringUtils.isEmpty(uInfo.getEmail())) {
				String displayEmail = CommonUtil.getLittleStr(18, uInfo.getEmail());
				uInfo.setDisplayEmail(displayEmail);
			}
		}
	}

	/**
	 * 需求列表
	 */
	@RequestMapping(value = APIConstants.PROJECT_ALL_LIST, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<PageResult> listAll(HttpServletRequest request, String type, int status,
			PageResult page) {
		List resultList = new ArrayList<>();
		List<ProjectVo> projectList = projectService.getAllProjectList(type, status, page);

		// 封装返回给前端
		for (ProjectVo project : projectList) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("id", project.getId());
			result.put("name", project.getName());
			result.put("type", project.getType());
			result.put("priceRange", project.getPriceRange());
			result.put("period", project.getPeriod());
			result.put("bidEndTime", DateUtil.formatTime(project.getBidEndTime(), ""));
			result.put("createTime", DateUtil.formatTime(project.getCreateTime(), ""));
			String displayStatus = project.getStatus() != null ? project.getStatus().getType() : "未知状态";
			result.put("displayStatus", displayStatus);
			result.put("backgroudStatus", project.getBackgroudStatus());
			result.put("ranking", project.getRanking());
			// 竞标人数
			result.put("jingbiaoNums", project.getJoinCount());
			result.put("region", project.getPublisherInfo().getRegion());
			result.put("checkResult", project.getCheckResult());
			result.put("acceptResult", project.getAcceptResult());

			resultList.add(result);
		}

		page.setData(resultList);
		int count = projectService.getAllProjectListCount(type, status);
		page.setTotalRow(count);
		return new ResponseEntity<PageResult>(page, HttpStatus.OK);
	}

	/**
	 * 关闭项目
	 */
	@RequestMapping(value = APIConstants.CLOSE_PROJECT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity closeProject(HttpServletRequest request, String id, String closeReason) {
		Map<String, Object> result = new HashMap<String, Object>();
		projectService.closeProject(id, closeReason);
		
		 //项目操作日志
        projectOperationLogService.addProjectOperationLog2("项目关闭" ,new Long(id),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ALL);

		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 调整项目排序等级
	 */
	@RequestMapping(value = APIConstants.RANKING_PROJECT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity rankingProject(HttpServletRequest request, String id, int ranking) {
		Map<String, Object> result = new HashMap<String, Object>();
		int index = projectService.rankingProject(id, ranking);
		result.put("index", index);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 项目软删除
	 */
	@RequestMapping(value = APIConstants.DELETE_PROJECT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity deleteProject(HttpServletRequest request, String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		projectService.deleteProject(id);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 预约顾问列表
	 */
	@RequestMapping(value = APIConstants.RESERVE_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PageResult> listReserve(HttpServletRequest request, int status, PageResult page) {
		List<WeixinProjectVo> projectList = projectService.getReserveList(status, page);

		page.setData(projectList);
		int count = projectService.getReserveListCount(status);
		page.setTotalRow(count);
		return new ResponseEntity<PageResult>(page, HttpStatus.OK);
	}

	/**
	 * 预约关闭/洽谈
	 */
	@RequestMapping(value = APIConstants.RESERVE_MODIFY, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity reserveModify(HttpServletRequest request, WeixinProjectVo reserveInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		projectService.updateReserveByID(reserveInfo);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 同步Edaice试卷
	 */
	@RequestMapping(value = APIConstants.SYNC_EDAICE_PAPERS, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity syncPapers(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ExamPaperVo> listExamPaper = EdaiceUtil.getExamPapers();
		// 更新数据库表
		examService.addBatchExamPaper(listExamPaper);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 预约项目发布
	 */
	@RequestMapping(value = APIConstants.RESERVE_PUBLISH, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity reservePublish(HttpServletRequest request, long reserveId, String userName,
			String contactType, long regionId, ProjectVo projectVo) {
		Map<String, Object> result = checkPublishArgs(userName, contactType, projectVo);
		// 如果检查通过
		if (null == result) {
			String attachmentUrl = (String) request.getSession().getAttribute(
					CommonConstants.FILE_PROJECT_ATTACHMENT);
			projectVo.setAttachment(attachmentUrl);
			projectVo.setPublicContact(contactType);

			// 管理员添加用户
			UserInfoVo userInfoVo = new UserInfoVo();
			userInfoVo.setName(userName);
			userInfoVo.setUserType(3);
			userInfoVo.setRegionId(regionId);
			userInfoVo.setMobile(projectVo.getContactMobile());
			userInfoVo.setEmail(projectVo.getContactEmail());
			userInfoVo.setQq(projectVo.getContactQq());
			userInfoVo.setWeixin(projectVo.getContactWeixin());
			userInfoVo.setFileSecretKey("ABCD");
			userInfoVo.setRemark("管理员添加的未注册用户，创建时间"
					+ DateUtil.formatTime(new Date(), DateUtil.DATA_STAND_FORMAT_STR));
			String pid = projectService.reservePublish(reserveId, projectVo, userInfoVo);

			result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(), APIConstants.CHECK_VIEW + "?id="
					+ pid);
		}
		return new ResponseEntity(result, HttpStatus.OK);
	}

	// 校验参数是否合法
	private Map checkPublishArgs(String userName, String contactType, ProjectVo projectVo) {
		String type = projectVo.getType();
		String priceRange = projectVo.getPriceRange();
		String name = projectVo.getName();
		String content = projectVo.getContent();
		String mobile = projectVo.getContactMobile();
		String email = projectVo.getContactEmail();
		String qq = projectVo.getContactQq();
		String weixin = projectVo.getContactWeixin();
		int period = projectVo.getPeriod();
		Date bidEndTime = projectVo.getBidEndTime();

		if (StringUtils.isEmpty(type) || StringUtils.isEmpty(priceRange) || StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(content) || period <= 0 || bidEndTime == null) {
			return ResponseMapUtil.getFailedResponseMap("项目必填参数不可为空");
		}
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(contactType)) {
			return ResponseMapUtil.getFailedResponseMap("用户必填参数不可为空");
		}

		// 雇主选择公开的联系方式(1手机 2邮箱 3QQ 4微信)用逗号分隔
		if (contactType.contains("1") && StringUtils.isEmpty(mobile)) {
			return ResponseMapUtil.getFailedResponseMap("手机号不可为空");
		}
		if (contactType.contains("2") && StringUtils.isEmpty(email)) {
			return ResponseMapUtil.getFailedResponseMap("邮箱不可为空");
		}
		if (contactType.contains("3") && StringUtils.isEmpty(qq)) {
			return ResponseMapUtil.getFailedResponseMap("QQ号不可为空");
		}
		if (contactType.contains("4") && StringUtils.isEmpty(weixin)) {
			return ResponseMapUtil.getFailedResponseMap("微信号不可为空");
		}

		// 返回空表示校验通过
		return null;
	}

	/**
	 * 加载导入用户列表信息
	 * 
	 * @param request
	 * @param reserveInfo
	 * @return
	 */
	@RequestMapping(value = APIConstants.USER_IMPORT_LIST, method = RequestMethod.GET)
	@ResponseBody
	public PageResult loadUserImportList(HttpServletRequest request, String query, String skillQuery) {
		query = query == null ? "" : query;
		skillQuery = skillQuery == null ? "" : skillQuery.trim().toUpperCase();
		PageResult page = new PageResult();
		int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request
				.getParameter("currentPage"));
		int pageSize = request.getParameter("pageSize") == null ? 12 : Integer.parseInt(request
				.getParameter("pageSize"));
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		List<UserInfoImportVo> data = userService.getUserInfoImportList(query, skillQuery, page);
		if (data == null)
			data = new ArrayList<>();
		int queryTotalCount = userService.getUserInfoImportCount(query, skillQuery, page);
		page.setTotalRow(queryTotalCount);
		page.setData(data);
		return page;
	}

	/**
	 * 查询导入用户信息
	 * 
	 * @param request
	 * @param reserveInfo
	 * @return
	 */
	@RequestMapping(value = APIConstants.USER_IMPORT_INFO, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity loadUserImportInfo(HttpServletRequest request, long uid) {

		Map<String, Object> result = new HashMap<>();
		result.put("userInfoImportVo", userService.getUserInfoImportInfo(uid));
		return new ResponseEntity(result, HttpStatus.OK);
	}

	/**
	 * 查询统计信息表
	 * 
	 * @param request
	 * @param reserveInfo
	 * @return
	 */
	@RequestMapping(value = APIConstants.LOAD_ANYLIZE_DATA, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity loadAnylizeData(HttpServletRequest request, String option, String timeOption,
			String startQueryTime, String endQueryTime) {
		String endTime = "";
		Date odate = new Date();
		SimpleDateFormat im = new SimpleDateFormat("yyyy-MM-dd");
		if (!DateUtil.isValidDate(endQueryTime)) {
			endQueryTime = im.format(odate);
		}
		if (!DateUtil.isValidDate(startQueryTime)) {
			startQueryTime = im.format(odate);
		}
		int gapDays = 0;
		try {
			gapDays = DateUtil.daysBetween(im.parse(startQueryTime), odate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (gapDays < 0) {
			startQueryTime = im.format(odate);
		} else if (gapDays > 90) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(odate);
			calendar.add(calendar.DATE, -90);
			startQueryTime = im.format(calendar.getTime());
		}
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> totalMap = new HashMap<>();
		totalMap.put("totalUserCount", 0);
		totalMap.put("totalProjectCount", 0);
		totalMap.put("totalWeixinCount", 0);
		totalMap.put("totalCompIdentifierCount", 0);
		totalMap.put("totalPersonIdentifierCount", 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startQueryDate = null;
		// 默认系统时间
		Date current = new Date();
		Date endDate = null;
		try {
			startQueryDate = sdf.parse(startQueryTime);
			endDate = current;
			if (endQueryTime != null) {
				endDate = sdf.parse(endQueryTime);
			}
			if (endDate.getTime() >= current.getTime())
				endDate = current;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		endQueryTime = sdf.format(endDate);
		if (startQueryDate.getTime() <= new Date().getTime()) {
			if (endDate.getTime() >= startQueryDate.getTime()) {
				if ("month".equals(option)) {
					// 周期
					int totalMonthGap = DateUtil.getMonth(endDate, startQueryDate);
					Map<String, Object> dataMap = null;
					Map<String, Integer> data = null;
					Calendar calender = Calendar.getInstance();
					calender.setTime(startQueryDate);
					for (int i = 0; i <= totalMonthGap; i++) {
						dataMap = new HashMap<>();
						data = new HashMap<>();
						startQueryTime = sdf.format(calender.getTime());
						startQueryTime = startQueryTime.substring(0, startQueryTime.lastIndexOf("-")) + "-01";
						calender.add(calender.MONTH, 1);
						endTime = sdf.format(calender.getTime());
						endTime = endTime.substring(0, endTime.lastIndexOf("-")) + "-01";
						if (i == totalMonthGap) {
							if (startQueryTime.equals(endQueryTime))
								break;
							endTime = endQueryTime;
						}
						queryAllData(dataList, dataMap, totalMap, startQueryTime, endTime);
					}
				} else if ("day".equals(option)) {
					Date startDate = null;
					Date nextDay = null;
					Map<String, Object> dataMap = null;
					Map<String, Integer> data = null;
					int totalDays = 0;
					try {
						startDate = sdf.parse(startQueryTime);
						nextDay = new Date(startDate.getTime());
						if ("1".equals(timeOption)) {
							nextDay = new Date();
						} else if ("2".equals(timeOption)) {
							nextDay = new Date();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(nextDay);
							calendar.add(calendar.DATE, 0);
							nextDay = calendar.getTime();
						} else if ("3".equals(timeOption)) {
							nextDay = new Date();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(nextDay);
							calendar.add(calendar.DATE, -2);
							nextDay = calendar.getTime();
						} else if ("4".equals(timeOption)) {
							nextDay = new Date();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(nextDay);
							calendar.add(calendar.DATE, -6);
							nextDay = calendar.getTime();
						}
						totalDays = DateUtil.daysBetween(nextDay, endDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (int i = 0; i <= totalDays; i++) {
						dataMap = new HashMap<>();
						data = new HashMap<>();
						String startTime = sdf.format(nextDay);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(nextDay);
						calendar.add(calendar.DATE, 1);
						nextDay = calendar.getTime();
						endTime = sdf.format(nextDay);
						queryAllData(dataList, dataMap, totalMap, startTime, endTime);
					}
				}
			}
		}
		result.put("data", dataList);
		result.put("total", totalMap);
		return new ResponseEntity(result, HttpStatus.OK);
	}

	private void queryAllData(List<Map<String, Object>> dataList, Map<String, Object> dataMap,
			Map<String, Object> totalMap, String startQueryTime, String endTime) {
		// 用户数
		int userCount = userService.getNewUsersCount(startQueryTime, endTime);
		dataMap.put("userCount", userCount);
		// 项目数
		int projectCount = projectInSelfRunService.getNewProjectsCount(startQueryTime, endTime);
		dataMap.put("projectCount", projectCount);
		// 预约(微信)项目数
		int weixinProjectCount = projectInSelfRunService.getNewWeixinProjectsCount(startQueryTime, endTime);
		dataMap.put("weixinProjectCount", weixinProjectCount);
		// 已认证通过数(个人)
		int personIdentifierCount = userService.getNewApprovedUsersCount(startQueryTime, endTime, "0");
		dataMap.put("personIdentifierCount", personIdentifierCount);
		// 已认证通过数(企业)
		int compIdentifierCount = userService.getNewApprovedUsersCount(startQueryTime, endTime, "1");
		dataMap.put("compIdentifierCount", compIdentifierCount);

		dataMap.put("time", startQueryTime);
		dataList.add(dataMap);
		totalMap.put("totalUserCount", Integer.parseInt(totalMap.get("totalUserCount").toString())
				+ userCount);
		totalMap.put("totalProjectCount", Integer.parseInt(totalMap.get("totalProjectCount").toString())
				+ projectCount);
		totalMap.put("totalWeixinCount", Integer.parseInt(totalMap.get("totalWeixinCount").toString())
				+ weixinProjectCount);
		totalMap.put("totalCompIdentifierCount",
				Integer.parseInt(totalMap.get("totalCompIdentifierCount").toString()) + compIdentifierCount);
		totalMap.put("totalPersonIdentifierCount",
				Integer.parseInt(totalMap.get("totalPersonIdentifierCount").toString())
						+ personIdentifierCount);
	}

	/***
	 * 群发邮件功能
	 * 
	 * @param request
	 * @param maillist
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = APIConstants.SEND_MAIL_BATCH, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity mailSendInBatch(HttpServletRequest request, String mailList, String mailBccList,
			String time, String copies, String title, String mailContent) throws InterruptedException {
		Map<String, Object> result = new HashMap<>();

		String[] mailListArray = null;
		boolean sendResult = true;
		StringBuffer resultBuffer = new StringBuffer();

		long interval = 5000;// 默认发送间隔5秒
		int copiesInt = 5;// 默认投递份数5份
		if (!StringUtils.isEmpty(time)) {
			interval = Long.parseLong(time);
		}
		if (!StringUtils.isEmpty(copies)) {
			copiesInt = Integer.parseInt(copies);
		}

		// 如果密送为空，则按顺序推送收件人
		if (StringUtils.isEmpty(mailBccList)) {
			if (mailList != null && !mailList.trim().isEmpty() && title != null && !title.trim().isEmpty()
					&& mailContent != null && !mailContent.trim().isEmpty()) {
				mailListArray = mailList.split(";");

				for (int i = 0; i < mailListArray.length; i++) {
					String receiver = mailListArray[i];
					EmailSendConfig mailConfig = emailSendConfig.clone();
					mailConfig.setNick("码客帮");
					mailConfig.setReceiver(receiver);
					sendResult = EmailSender.send(mailConfig, title, mailContent);

					if (sendResult) {
						resultBuffer.append(receiver + ":发送成功<br>");
					} else {
						resultBuffer.append(receiver + ":发送失败<br>");
					}
					Thread.sleep(interval);
				}
			}
		} else {
			// 拆分密送收件人，批量发送
			mailListArray = mailList.split(";");
			int totalSize = mailListArray.length;
			int loopSize = (copiesInt > totalSize) || (totalSize % copiesInt) != 0 ? (totalSize / copiesInt) + 1
					: (totalSize / copiesInt);

			// 群发每次发固定个数
			for (int i = 0; i < loopSize; i++) {
				String bcc = "";
				for (int j = 0; j < copiesInt; j++) {
					if ((i * copiesInt + j) >= totalSize) {
						break;
					}
					bcc += mailListArray[(i * copiesInt + j)] + ",";
				}
				bcc = bcc.substring(0, bcc.lastIndexOf(","));

				EmailSendConfig mailConfig = emailSendConfig.clone();
				mailConfig.setReceiver(emailSendConfig.getUserName());
				mailConfig.setBcc(bcc);
				sendResult = EmailSender.send(mailConfig, title, mailContent);
				if (sendResult) {
					resultBuffer.append(bcc + ":发送成功<br>");
				} else {
					resultBuffer.append(bcc + ":发送失败<br>");
				}
				// 发送间隔，防止垃圾邮件过滤
				Thread.sleep(mailConfig.getInterval());
			}
		}

		result = ResponseMapUtil.getSuccessResponseMap(resultBuffer.toString());
		return new ResponseEntity(result, HttpStatus.OK);
	}

	@RequestMapping(value = APIConstants.REQUEST_REVIEW, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity requestReview(HttpServletRequest request, ProjectInSelfRunVo projectVo) {
		Map<String, Object> result = new HashMap<>();

		if (projectVo != null) {
			// projectInSelfRunService.
		}
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	/**
	 * 项目审核列表
	 * 
	 */
	@RequestMapping(value = APIConstants.LOAD_PROJECT_LIST_4_ADMIN, method = RequestMethod.POST)
	@ResponseBody
	public PageResult loadProjectList(HttpServletRequest request,String query,String typeStr,String status,String consultantId) {
		query = query == null?"":query;		
		typeStr = typeStr ==null?"":typeStr.trim();
		status = status ==null?"":status.trim().equals("-2")?null:status;
		PageResult page = new PageResult();
		int currentPage = request.getParameter("currentPage")==null?1:Integer.parseInt(request.getParameter("currentPage"));
		int pageSize = request.getParameter("pageSize")==null?12:Integer.parseInt(request.getParameter("pageSize"));
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);	
		List<ProjectInSelfRunVo> listData = projectInSelfRunService.getProjectListForAdmin(page, query,typeStr,status,consultantId);
		int totalRow = projectInSelfRunService.getProjectListCountForAdmin(page, query,typeStr,status,consultantId);
		List<ProjectInSelfRunVo> deleteListData = new ArrayList<>();
		page.setTotalRow(totalRow);
		page.setData(listData);
		return page;
	}


	/**
	 * 审核结果通知 如果用户有设置邮箱，优先推送到用户邮箱；否则发送短信
	 * 
	 * @param projectVo
	 */
	private void sendProjectInSelfRunCheckResult(ProjectInSelfRunVo projectVo, boolean isPass) {
		UserInfoVo creator = userService.getUserInfoByID(projectVo.getCreatorId());
		String email = creator.getEmail();
		String mobile = creator.getMobile();
		String name = projectVo.getName();

		if (email != null && !email.trim().isEmpty()) {
			projectMailService.sendCheckResultMail(projectVo.getId());
		} else {
			if (isPass) {
				yunConnectService.sendCheckPassSMS(mobile,name);
			} else {
				yunConnectService.sendCheckRejectSMS(mobile,name);
			}
		}
	}
	
	/**
	 * 码客库查询
	 */
	@RequestMapping(value = APIConstants.QUERY_DEVELOPER, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PageResult> filterDeveloper(HttpServletRequest request, String provinceId,
			String cityId, String cando, String ability, String otherAbility, PageResult page) {

		List<UserInfoVo> listUser = userService.queryDevelopers(provinceId, cityId, cando, ability,
				otherAbility, page);
		int totalCount = userService.queryDevelopersTotalCount(provinceId, cityId, cando, ability,
				otherAbility);

		page.setData(listUser);
		page.setTotalRow(totalCount);
		return new ResponseEntity<PageResult>(page, HttpStatus.OK);
	}
	/**
	 * 项目转让
	 * @proId  项目id
	 * @mobile 转出者mobile
	 * @assignReason 转让备注
	 */
	@RequestMapping(value=APIConstants.PROJECTINSELFRUN_ASSIGN, method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> assignProject(String proId,String mobile,String assignReason)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//获取用户id
			UserInfoVo userVo = userService.getUserInfoByMobile(mobile);
			long userId = userVo.getId();
			
			//更新项目拥有者
			ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjectInSelfRun(proId);
			long creatorId = projectVo.getCreatorId();
			
			ProjectInSelfRunVo proVo = new ProjectInSelfRunVo();
			proVo.setId(proId);
			proVo.setCreatorId(userId);
			int update = projectInSelfRunService.updateProjectInSelfRun(proVo);
			if(update <= 0){
				result = ResponseMapUtil.getFailedResponseMap("转让失败！");
			}
			//保存项目转让记录proId creatorId   userId   assignReason  assignDate
			ProjectAssignVo projectAssignVo = new  ProjectAssignVo();
			projectAssignVo.setProjectId(Long.parseLong(proId));
			projectAssignVo.setCreatorId(creatorId);
			projectAssignVo.setAssignId(userId);
			projectAssignVo.setReason(assignReason);
			projectAssignVo.setAssignTime(new Date());
			
			int addRecord = projectInSelfRunService.addAssignmentRecord(projectAssignVo);
			if(update > 0 && addRecord > 0){
				result = ResponseMapUtil.getSuccessResponseMap("项目转让成功！");
				
				 //项目操作日志
		        projectOperationLogService.addProjectOperationLog2("项目转让:从" + creatorId + "转到" + userId,
		        	new Long(proId),CommonUtil.getCurrentLoginUser().getUserInfoVo().getId(),
		        	ProjectInSelfRunConstants.PROJECT_OPERATION_PERMISSION_ADMIN);
			}
		} catch (Exception e) {
			result = ResponseMapUtil.getFailedResponseMap("转让失败！");
			log.error(e.toString());
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	
	/**
	 * 获取所有的顾问
	 */
	@RequestMapping(value = APIConstants.GET_ALL_CONSULTANT, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> getAllConsultants(HttpServletRequest request){
		List<UserInfoVo> consultants = userService.getAllConsultants();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("consultants",consultants);
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
}
