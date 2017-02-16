package com.yundaren.api.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yundaren.cache.ActiveCodeCache;
import com.yundaren.cache.VerificationCodeCache;
import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.RegexUtil;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.DBUtil;
import com.yundaren.common.util.DateUtil;
import com.yundaren.common.util.GogsUtil;
import com.yundaren.common.util.PageResult;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.filter.handler.SensitivewordFilter;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.service.ExamService;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.service.ProjectMailService;
import com.yundaren.support.vo.ExamAblityVo;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.user.service.SsoService;
import com.yundaren.user.service.UserExperienceService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserAccountInDetailVo;
import com.yundaren.user.vo.UserAccountOutDetailVo;
import com.yundaren.user.vo.UserAccountVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
public class UserInfoRest {

	@Setter
	private DomainConfig domainConfig;

	@Setter
	private UserService userService;

	@Setter
	private SsoService ssoService;

	@Setter
	private IdentifyService identifyService;

	@Setter
	private ProjectMailService projectMailService;

	@Setter
	private UserExperienceService userExperienceService;
	
	@Setter
	private ExamService examService;
	
	@Autowired
	private DBUtil dbUtil;

	/**
	 * 修改个人信息
	 */
	@RequestMapping(value = APIConstants.USERS_INFO_MODIFY, method = RequestMethod.POST)
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Map> modifyUserInfo(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute UserInfoVo userInfoVo) throws IOException {
		Map<String, Object> result = checkModify(userInfoVo);

		// 如果检查通过
		if (null == result) {
			SsoUserVo ssoUser = CommonUtil.getCurrentLoginUser();
			userInfoVo.setId(ssoUser.getUserId());
			userService.updateUserInfo(userInfoVo);
			//更新Gogs平台昵称
			String gogsMobile = ssoUser.getUserInfoVo().getMobile();
			boolean status = GogsUtil.updateUserInfo(request, gogsMobile, gogsMobile, gogsMobile+"@gogs.com", "", "", userInfoVo.getName());
//			if(status)System.out.println("同步Gogs成功!");
//			else System.out.println("同步Gogs失败!");
			// 如果手机号码修改了，关联SSO_USER
			String mobile = userInfoVo.getMobile();
			UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
			if (!mobile.equals(userInfo.getMobile())) {
				// 更新关联的SSO信息
				ssoService.updateSsoRelation(mobile, userInfo.getId(), 1);
			}

			String email = userInfoVo.getEmail();
			// 如果邮箱修改了，需要重新验证
			if (!email.equals(userInfo.getEmail())) {
				// 更新关联的SSO信息
				ssoService.updateSsoRelation(email, userInfo.getId(), 0);
			}

			// 修改后更新SESSION对象
			UserInfoVo uInfo = userService.getUserInfoByID(ssoUser.getUserId());
			// 名称缩略显示
			setLetterName(ssoUser, uInfo);
			ssoUser.setUserInfoVo(uInfo);
			request.getSession().setAttribute(CommonConstants.USERINFO_OPERATION, 0);
			result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(), "/home/userinfo");
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
			displayName = CommonUtil.getLittleStr(10, name);
		} else {
			name = userInfo.getName();
			displayName = CommonUtil.getLittleStr(6, name);
		}
		userInfo.setDisplayName(displayName);
	}

	/**
	 * 个人信息页面
	 */
	@RequestMapping(value = APIConstants.USERS_INFO, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> view(HttpServletRequest request) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();

		// 封装返回给前端
		result.put("name", userInfo.getName() == null ? "" : userInfo.getName());
		result.put("mobile", userInfo.getMobile() == null ? "" : userInfo.getMobile());
		result.put("email", userInfo.getEmail() == null ? "" : userInfo.getEmail());
		result.put("introduction", userInfo.getIntroduction() == null ? "" : userInfo.getIntroduction());
		result.put("account", userInfo.getBankAccount() == null ? "" : userInfo.getBankAccount());
		result.put("ability", userInfo.getMainAbility() == null ? "" : userInfo.getMainAbility());
		result.put("cityId", userInfo.getRegionId());
		result.put("userType", userInfo.getUserType());
		result.put("provinceId", userInfo.getProvinceId());

		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 个人信息是否完善
	 */
	@RequestMapping(value = APIConstants.USERS_INFO_CHECK_COMPLETE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> checkComplete(HttpServletRequest request) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		if (userService.isUserInfoComplete(userInfo.getId())) {
			result = ResponseMapUtil.getSuccessResponseMap("");
		} else {
			result = ResponseMapUtil.getFailedResponseMap("");
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 认证点下一步缓存录入信息
	 */
	@RequestMapping(value = APIConstants.USERS_CACHE_INPUT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> nextSave(UserInfoVo userInfo) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();

		long uid = currentUser.getId();
		userInfo.setId(uid);
		
		userService.updateUserInfo(userInfo);
		IdentifyVo tmpIdentifyVo = identifyService.getIdentifyByUID(userInfo.getId());
		IdentifyVo newIdentifyVo = userInfo.getIdentifyInfo();
		newIdentifyVo.setUserId(uid);
		newIdentifyVo.setStatus(-1);
		
		if(tmpIdentifyVo == null){
			identifyService.addIdentify(newIdentifyVo);
		}else{
			identifyService.updateIdentifyByUID(newIdentifyVo);
		}

		userExperienceService.deleteUserAllExp(uid);
		userExperienceService.addUserExperience(userInfo.getEmployeeEduExperience(), uid);
		userExperienceService.addUserExperience(userInfo.getEmployeeJobExperience(), uid);
		userExperienceService.addUserExperience(userInfo.getEmployeeProduct(), uid);
		userExperienceService.addUserExperience(userInfo.getEmployeeProjectExperience(), uid);
		
		//刷新用户session
		UserInfoVo refreshUser = userService.getUserInfoByID(uid);
		CommonUtil.refreshLoginUser(refreshUser);
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 昵称是否存在
	 */
	@RequestMapping(value = APIConstants.USERS_CHECK_NICKNAME, method = {RequestMethod.GET,
			RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<Map> checkNickNameExist(String userName) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		// 用户名确认后不允许修改
		if (StringUtils.isEmpty(userInfo.getName())) {
			boolean isSensitive = SensitivewordFilter.getInstance().isContaintSensitiveWord(userName, 1);
			if (isSensitive) {
				result = ResponseMapUtil.getFailedResponseMap("昵称存在敏感词，请更换其他昵称");
			}

			UserInfoVo uInfo = userService.getUserInfoByNickname(userName);
			if (uInfo != null) {
				result = ResponseMapUtil.getFailedResponseMap("昵称已存在，请更换其他昵称");
			}
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 手机号是否存在
	 */
	@RequestMapping(value = APIConstants.USERS_CHECK_MOBILE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> checkMobileExist(String mobile) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		// 手机号码发生改变才需要检查
		if (currentUser.getMobile() == null || !currentUser.getMobile().equals(mobile)) {
			UserInfoVo userInfo = userService.getUserInfoByMobile(mobile);
			if (userInfo != null) {
				result = ResponseMapUtil.getFailedResponseMap("号码已存在，请更换其他号码");
			}
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 邮箱是号否存在
	 */
	@RequestMapping(value = APIConstants.USERS_CHECK_EMAIL, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> checkEmailExist(String email) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		// 邮箱发生改变才需要检查
		if (currentUser.getEmail() == null || !currentUser.getEmail().equals(email)) {
			UserInfoVo userInfo = userService.getUserInfoByEmail(email);
			if (userInfo != null) {
				result = ResponseMapUtil.getFailedResponseMap("邮箱已存在，请更换其他邮箱");
			}
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

//	/**
//	 * 用户认证
//	 * 
//	 * @param operate
//	 *            操作(add新增 modify修改)
//	 */
//	@RequestMapping(value = APIConstants.USERS_IDENTIFY, method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<Map> identify(HttpServletRequest request, IdentifyVo identifyVo, String operate) {
//		Map<String, Object> result = checkIdentifyArgs(request, identifyVo, operate);
//		// 如果检查通过
//		if (null == result) {
//			UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
//			identifyVo.setUserId(currentUser.getId());
//			if (identifyVo.getCategory() == 0) {// 个人认证
//				String idCardImg = (String) request.getSession().getAttribute(CommonConstants.UPLOAD_IMG_SFZ);
//				if (!StringUtils.isEmpty(idCardImg)) {
//					identifyVo.setIdCardImg(idCardImg);
//				}
//			} else {// 企业认证
//				String businessLicenseImg = (String) request.getSession().getAttribute(
//						CommonConstants.UPLOAD_IMG_YYZZ);
//				if (!StringUtils.isEmpty(businessLicenseImg)) {
//					identifyVo.setBusinessLicenseImg(businessLicenseImg);
//				}
//			}
//			// 如果是更新操作
//			if (operate.equals("add")) {
//				identifyService.addIdentify(identifyVo);
//			} else {
//				identifyVo.setStatus(0);// 审核中状态
//				identifyService.updateIdentifyByUID(identifyVo);
//			}
//			projectMailService.sendRequestIdentify(identifyVo);
//			request.getSession().setAttribute(CommonConstants.USERINFO_OPERATION, 1);
//			result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(), "/home/userinfo");
//		}
//
//		return new ResponseEntity<Map>(result, HttpStatus.OK);
//	}

	/**
	 * 用户认证
	 * 
	 * @param operate
	 *            操作(add新增 modify修改)
	 * @param resumeType
	 *            简历类型(file文件 link链接 input手动录入)
	 */
	@RequestMapping(value = APIConstants.USERS_IDENTIFY, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> identify(HttpServletRequest request, UserInfoVo userInfo, String operate,String accountNum) {
		Map<String, Object> result = checkIdentifyArgs(request, userInfo, operate);
		// 如果检查通过
		if (null == result) {
			request.getSession().setAttribute(CommonConstants.USERINFO_OPERATION, 1);
			result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(), "/home/userinfo");
			
			// 如果是更新操作
			if (operate.equals("add")) {
				userService.addUserIdentify(request, userExperienceService, userInfo, accountNum);
			} else {
				userService.modifyUserIdentify(request, userExperienceService, userInfo, accountNum);
			}
			projectMailService.sendRequestIdentify(userInfo.getIdentifyInfo());
			
			// 修改后更新SESSION对象
			UserInfoVo uInfo = userService.getUserInfoByID(CommonUtil.getCurrentLoginUser().getUserId());
			// 名称缩略显示
			setLetterName(CommonUtil.getCurrentLoginUser(), uInfo);
			CommonUtil.getCurrentLoginUser().setUserInfoVo(uInfo);
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	

	// 修改个人信息校验
	private Map checkModify(UserInfoVo userInfoVo) {
		String name = userInfoVo.getName();
		String email = userInfoVo.getEmail();
		String mobile = userInfoVo.getMobile();
		String vcode = userInfoVo.getVcode();
		String qq = userInfoVo.getQq();

		UserInfoVo currentLoginUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(email)
				|| StringUtils.isEmpty(qq)) {
			return ResponseMapUtil.getFailedResponseMap("必填项不能为空");
		}

		boolean isSensitive = SensitivewordFilter.getInstance().isContaintSensitiveWord(name, 1);
		if (isSensitive) {
			return ResponseMapUtil.getFailedResponseMap("昵称存在敏感词，请更换其他昵称");
		}

		// 如果手机号码修改了，需要重新验证
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		if (!mobile.equals(currentUser.getMobile())) {
			// 校验验证码
			String cacheCode = String.valueOf(VerificationCodeCache.getInstance().get(mobile));
			if (StringUtils.isEmpty(vcode) || !cacheCode.equals(vcode)) {
				return ResponseMapUtil.getFailedResponseMap("短信验证码错误");
			}
		}

		// 如果邮箱修改了，需要重新验证
		if (!email.equals(currentUser.getEmail())) {
			// 校验验证码
			String cacheCode = String.valueOf(ActiveCodeCache.getInstance().get(email));
			if (StringUtils.isEmpty(vcode) || !cacheCode.equals(vcode)) {
				return ResponseMapUtil.getFailedResponseMap("邮箱验证码错误");
			}
		}

		// 用户名确认后不允许修改
		if (!StringUtils.isEmpty(currentUser.getName())) {
			userInfoVo.setName(currentUser.getName());
		} else {
			// 昵称不能重复
			UserInfoVo uVo = userService.getUserInfoByNickname(name);
			if (uVo != null) {
				return ResponseMapUtil.getFailedResponseMap("昵称已被使用，请更换其他昵称");
			}
		}
		// 返回空表示校验通过
		return null;
	}

	// 校验参数是否合法
	private Map checkIdentifyArgs(HttpServletRequest request, UserInfoVo userInfo, String operate) {
		IdentifyVo identifyVo = userInfo.getIdentifyInfo();
		int category = identifyVo.getCategory();
		String cando = userInfo.getCando();
		String ability = userInfo.getMainAbility();
		String otherAbility = userInfo.getOtherAbility();
		
		String resumeUrl = userInfo.getResumeUrl();
		String resumeType = userInfo.getResumeType();

		if (category == 0) {// 个人认证参数校验
			int freelanceType = userInfo.getFreelanceType();
			List listEduExp = userInfo.getEmployeeEduExperience();
			List listJobExp = userInfo.getEmployeeJobExperience();
			List listProduct = userInfo.getEmployeeProduct();
			String realName = identifyVo.getRealName();
			String idCard = identifyVo.getIdCard();
			String idCardImg = (String) request.getSession().getAttribute(CommonConstants.UPLOAD_IMG_SFZ);
			
			// 简历类型
			if (resumeType.equalsIgnoreCase("input")) {
				if (StringUtils.isEmpty(cando)
						|| (StringUtils.isEmpty(ability) && StringUtils.isEmpty(otherAbility))
						|| CollectionUtils.isEmpty(listEduExp) || CollectionUtils.isEmpty(listProduct)) {
					return ResponseMapUtil.getFailedResponseMap("必填项参数不可为空");
				}
			} else if (resumeType.equalsIgnoreCase("link")) {
				if (StringUtils.isEmpty(resumeUrl)) {
					return ResponseMapUtil.getFailedResponseMap("必填项参数不可为空");
				}
			} else if(resumeType.equalsIgnoreCase("file")){
				resumeUrl = (String) request.getSession().getAttribute(CommonConstants.FILE_RESUME_ATTACHMENT);
				if (StringUtils.isEmpty(resumeUrl) && operate.equals("add")) {
					return ResponseMapUtil.getFailedResponseMap("等简历上传完成之后再试");
				}
			}
			
			if (StringUtils.isEmpty(realName) || StringUtils.isEmpty(idCard)) {
				return ResponseMapUtil.getFailedResponseMap("必填项参数不可为空");
			}

			if (!RegexUtil.isIDcard(idCard)) {
				return ResponseMapUtil.getFailedResponseMap("身份证号码不正确");
			}

			// 如果是修改，号码没变则不用做重复性检查
			boolean needCheckRepeat = true;
			if (operate.equals("modify")) {
				UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
				IdentifyVo currentIdentify = identifyService.getIdentifyByUID(currentUser.getId());
				String orgIdCard = currentIdentify.getIdCard();
				
				if (orgIdCard != null && orgIdCard.equals(idCard)) {
					needCheckRepeat = false;
				}
			}

			if (needCheckRepeat) {
				// 身份证号码不能重复
				IdentifyVo identifyInfo = identifyService.getIdentifyByIDCard(idCard);
				if (identifyInfo != null) {
					return ResponseMapUtil.getFailedResponseMap("身份证号码已被使用，请更换其他号码");
				}
			}

			if (StringUtils.isEmpty(idCardImg) && operate.equals("add")) {
				return ResponseMapUtil.getFailedResponseMap("等待图片上传完成之后再试");
			}

		} else {// 企业认证参数校验
			String companySize = userInfo.getCompanySize();
			String introduction = userInfo.getIntroduction();
			String caseType = userInfo.getCaseType();
			String otherCaseType = userInfo.getOtherCaseType();
			List listTeamProject = userInfo.getEmployeeProjectExperience();
			String companyName = identifyVo.getCompanyName();
			String companyAddr = identifyVo.getCompanyAddr();
			String companyPhone = identifyVo.getCompanyPhone();
			String businessLicense = identifyVo.getBusinessLicense();
			String legalRepresent = identifyVo.getLegalRepresent();

			String businessLicenseImg = (String) request.getSession().getAttribute(
					CommonConstants.UPLOAD_IMG_YYZZ);

			if (StringUtils.isEmpty(companyName) || StringUtils.isEmpty(companyAddr)
					|| StringUtils.isEmpty(businessLicense) || StringUtils.isEmpty(legalRepresent)
					|| StringUtils.isEmpty(companySize) || StringUtils.isEmpty(introduction)
					|| CollectionUtils.isEmpty(listTeamProject)
					|| (StringUtils.isEmpty(caseType) && StringUtils.isEmpty(otherCaseType))) {
				return ResponseMapUtil.getFailedResponseMap("必填项参数不可为空");
			}

			// 如果是修改，公司名没变则不用做重复性检查
			boolean needCheckRepeat = true;
			if (operate.equals("modify")) {
				UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
				IdentifyVo currentIdentify = identifyService.getIdentifyByUID(currentUser.getId());
				String orgCompanyName = currentIdentify.getCompanyName();
				
				if (orgCompanyName != null && orgCompanyName.equals(companyName)) {
					needCheckRepeat = false;
				}
			}

			if (needCheckRepeat) {
				// 公司名称不能重复
				IdentifyVo identifyInfo = identifyService.getIdentifyByCName(companyName);
				if (identifyInfo != null) {
					return ResponseMapUtil.getFailedResponseMap("公司名称已被使用，请使用其他名称");
				}
			}

			if (businessLicense.length() > 20) {
				return ResponseMapUtil.getFailedResponseMap("营业执照号不正确");
			}

			if (StringUtils.isEmpty(businessLicenseImg) && operate.equals("add")) {
				return ResponseMapUtil.getFailedResponseMap("等待图片上传完成之后再试");
			}
		}

		// 返回空表示校验通过
		return null;
	}

	/**
	 * 服务商列表
	 * 
	 * @param operate
	 *       
	 */
	@RequestMapping(value = APIConstants.PROVIDER_LIST_VIEW, method = RequestMethod.POST)
	@ResponseBody
	public PageResult providerListView(HttpServletRequest request, UserInfoVo userInfo,String query,String categoryStr,String regionId,String caseTypeStr) {
		int category = categoryStr==null?2:Integer.parseInt(categoryStr);
		int userType = 0;		
		SsoUserVo ssoUserVo =  CommonUtil.getCurrentLoginUser();
		if(ssoUserVo==null)userType = 1;
		else userType = CommonUtil.getCurrentLoginUser().getUserInfoVo().getUserType();
		
		Map<String, Object> result = new HashMap<String, Object>();		
		//服务商基本信息
		PageResult page = new PageResult();
		int currentPage = request.getParameter("currentPage")==null?1:Integer.parseInt(request.getParameter("currentPage"));
		int pageSize = request.getParameter("pageSize")==null?12:Integer.parseInt(request.getParameter("pageSize"));
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);		
		query = query == null?"":query;
		List<UserInfoVo> providerDisplayList = userService.getDisplayProviderListP(page,query,userType,category,regionId);
		int queryTotalCount = userService.getDisplayProviderListCount(userType, query,category,regionId);
		result.put("totalCount", queryTotalCount);
		page.setTotalRow(queryTotalCount);
		//服务商项目信息
		Map<Long,List<EmployeeTeamProjectExperienceVo>>  employeeTeamProjectExperienceMap =  userExperienceService.getAllDisplayProviderProjects(userType);	
		
		for(UserInfoVo vo:providerDisplayList){
			for(Map.Entry<Long,List<EmployeeTeamProjectExperienceVo>> entry:employeeTeamProjectExperienceMap.entrySet()){ 
		        long id = entry.getKey();
		        Object projectList = entry.getValue();
				if(projectList!=null&&id==vo.getId())vo.setEmployeeProjectExperience((List<EmployeeTeamProjectExperienceVo>)projectList);
			}
		}
		//筛选领域 caseTypeStr
		if(caseTypeStr!=null){
			List<UserInfoVo> deletedUserInfoVo = new ArrayList<>();
			for(UserInfoVo vo : providerDisplayList){
				if(vo.getCaseType()==null){
					deletedUserInfoVo.add(vo);
				}else{
					String []caseTypeArr = vo.getCaseType().split(",");
					boolean isContains = false;
					for(String caseObj:caseTypeArr){
						if(caseTypeStr.equals(caseObj)){
							isContains=true;
						}
					}
					if(!isContains)deletedUserInfoVo.add(vo);
				}
		
			}			
			page.setTotalRow(providerDisplayList.size()-deletedUserInfoVo.size());
			for(UserInfoVo vo :deletedUserInfoVo){
				providerDisplayList.remove(vo);
			}
		}
		page.setData(providerDisplayList);
		return page;
	}
	
	
	/**
	 * 服务商上架下架
	 * @param request
	 * @param uid
	 * @param actionType 1.上架 0.下架
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROVIDER_DISPLAY_STATUS, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> setProviderDisplayStatus(HttpServletRequest request,long uid,int actionType) {
		Map<String, Object> result = new HashMap<String, Object>();		
		String message = "";
		if(actionType==1||actionType==0){
			boolean isSuccess = userService.setProviderDisplayStatus(uid, actionType);
			if(isSuccess){
				message = actionType==1?"上架成功!":"下架成功!";
			}else{
				message = "操作失败";
			}
		}else message = "类型不合法";
		result.put("message", message);
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	/**
	 * 删除服务商
	 * @param request
	 * @param uid
	 * @param actionType
	 * @return
	 */
	@RequestMapping(value = APIConstants.PROVIDER_DELETE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> deleteProviderByUId(HttpServletRequest request,long uid) {
		Map<String, Object> result = new HashMap<String, Object>();		
		userService.updateProviderDeleteStatusByID(uid,1);
		result.put("message", "删除成功!");
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}	

	/**
	 * 发起能力测试
	 */
	@RequestMapping(value = APIConstants.CREATE_EDAICE_EXAM, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> createEdaiceExam(String skill, int grade) {
		Map<String, Object> result = new HashMap<String, Object>();
		String examId = examService.createExam(skill, grade);
		if (StringUtils.isEmpty(examId)) {
			result = ResponseMapUtil.getFailedResponseMap("系统维护中，暂时无法进行能力评测，请稍后再试");
		} else {
			result = ResponseMapUtil.getSuccessResponseMap("success");

			List<ExamAblityVo> listExam = examService.getExamStatus();
			result.put("listData", listExam);
			result.put("email", CommonUtil.getCurrentLoginUser().getUserInfoVo().getEmail());
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	/**
	 * 获取测试历史记录
	 */
	@RequestMapping(value = APIConstants.GET_EDAICE_STATUS, method = RequestMethod.GET)
	@ResponseBody		
	public ResponseEntity<List> getEdaiceExamHistory() {
		List<ExamAblityVo> listExam = examService.getExamStatus();
		return new ResponseEntity<List>(listExam, HttpStatus.OK);
	}
	
	/**
	 * 获取用户出账、入账记录 GET_ACCOUNT_DETAIL
	 */
	@RequestMapping(value = APIConstants.GET_ACCOUNT_DETAIL, method = RequestMethod.POST)
	@ResponseBody	
	public PageResult getAccountRecord(HttpServletRequest request,int typeStr){
		UserInfoVo userInfoVo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		PageResult page = new PageResult();
		int currentPage = request.getParameter("currentPage")==null?1:Integer.parseInt(request.getParameter("currentPage"));
		int pageSize = request.getParameter("pageSize")==null?12:Integer.parseInt(request.getParameter("pageSize"));
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);	
		if(typeStr == 0){   //入账记录
			List<UserAccountInDetailVo> detailList = userService.getAccountInDetailVo((int)userInfoVo.getId(), page);
			int totalRow = userService.getAccountInDetailCount((int)userInfoVo.getId());
			page.setTotalRow(totalRow);
			page.setData(detailList);
		}else{					//提现记录
			List<UserAccountOutDetailVo> detailList = userService.getAccountOutDetailVo((int)userInfoVo.getId(), page);
			int totalRow = userService.getAccountOutDetailCount((int)userInfoVo.getId());
			page.setTotalRow(totalRow);
			page.setData(detailList);
		}
		List<ProjectInSelfRunVo> deleteListData = new ArrayList<>();
		return page;
	}
	/**
	 * 用户提现
	 */
	@RequestMapping(value = APIConstants.GET_ACCOUNT_CASH, method = RequestMethod.POST)
	@ResponseBody	
	public ResponseEntity<Map> getCash(HttpServletRequest request,String amount){
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		Map<String, Object> result = new HashMap<String, Object>();
		//获取用户总收入
		double totalIncome = userService.getUserTotalIncome((int)userInfo.getId());
		//获取用户总支出
		double totalOutcome = userService.getUserTotalOutcome((int)userInfo.getId());
		if(null==amount || amount.isEmpty()){
			result = ResponseMapUtil.getSuccessResponseMap("提现金额不能为空");
			return new ResponseEntity<Map>(result, HttpStatus.OK);
		}else if(Double.valueOf(amount) > (totalIncome-totalOutcome) || Double.valueOf(amount) <= 0){
			result = ResponseMapUtil.getSuccessResponseMap("提现金额超出可用余额范围");
			return new ResponseEntity<Map>(result, HttpStatus.OK);
		}
		//增加一条提现记录
		UserInfoVo userInfoVo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		UserAccountOutDetailVo detailVo = new UserAccountOutDetailVo();
		
		//添加一条汇款记录
		detailVo.setUserId((int)userInfoVo.getId());//用户id
		detailVo.setAmount(Double.valueOf(amount));//金额
		detailVo.setAccountId(userInfoVo.getAccountId());//支付账号id
		
		UserAccountVo userAccountVo = userService.getUseraccountById(userInfoVo.getAccountId());
		detailVo.setComment("提现至" + userAccountVo.getAccountName()+":"+userAccountVo.getAccountNum());//备注
		
		int effectRow = userService.addAccountOutDetail(detailVo);
		if (effectRow > 0) {

			// 记录财务日志
			String logSql = "INSERT INTO `sys_operation_log` ( `module`, `operate`, `remark`,"
					+ " `create_time`, `creator`) " + "VALUES ('%s', '%s', '%s', '%s', '%s')";
			// 备注：申请提现500元
			String remark = "申请提现" + amount + "元";
			String creator = "开发者:" + userInfo.getIdentifyInfo().getRealName();
			logSql = logSql.format(logSql, "财务管理", "提现申请", remark, DateUtil.getNow(), creator);
			dbUtil.excuteSql(logSql);

			result = ResponseMapUtil.getSuccessResponseMap("申请成功，系统将会在3个工作日内处理您的请求");
			return new ResponseEntity<Map>(result, HttpStatus.OK);
		}
		result = ResponseMapUtil.getSuccessResponseMap("申请失败，系统维护中请稍后再试");
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	/**
	 * 用户更新、添加支付账号
	 */
	@RequestMapping(value = APIConstants.UPDATE_PAY_ACCOUNT, method = RequestMethod.POST)
	@ResponseBody	
	public ResponseEntity<Map> updatePayAccount(HttpServletRequest request,String payAccount){
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		Map<String, Object> result = new HashMap<String, Object>();
		
		UserAccountVo userAccountVo = new UserAccountVo();
		userAccountVo.setAccountNum(payAccount);
		//TODO  需要判断是银行还是支付宝
		userAccountVo.setAccountType(1);
		userAccountVo.setUserId((int)userInfo.getId());
		int userAccountId = userService.addUserPayAccount(userAccountVo);
		userInfo.setAccountId(userAccountId);
		int effectRow = userService.updateUserInfo(userInfo);
		
		if(effectRow > 0){
			result = ResponseMapUtil.getSuccessResponseMap("添加收款账号成功");
			return new ResponseEntity<Map>(result, HttpStatus.OK);
		}
		result = ResponseMapUtil.getSuccessResponseMap("添加收款账号失败，系统维护中请稍后再试");
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
}
