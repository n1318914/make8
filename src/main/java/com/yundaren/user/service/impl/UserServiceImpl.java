package com.yundaren.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.LabelUtil;
import com.yundaren.common.util.PageResult;
import com.yundaren.support.biz.IdentifyBiz;
import com.yundaren.support.biz.ProjectInSelfRunBiz;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.user.biz.UserBiz;
import com.yundaren.user.biz.UserExperienceBiz;
import com.yundaren.user.service.UserExperienceService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.EmployeeEduExperienceVo;
import com.yundaren.user.vo.EmployeeJobExperienceVo;
import com.yundaren.user.vo.EmployeeProductVo;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;
import com.yundaren.user.vo.UserAccountInDetailVo;
import com.yundaren.user.vo.UserAccountOutDetailVo;
import com.yundaren.user.vo.UserAccountVo;
import com.yundaren.user.vo.UserInfoImportVo;
import com.yundaren.user.vo.UserInfoVo;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceImpl implements UserService {

	@Setter
	private IdentifyBiz identifyBiz;

	@Setter
	private UserBiz userBiz;

	@Setter
	private ProjectInSelfRunBiz projectInSelfRunBiz;
	
	@Setter
	private UserExperienceBiz userExperienceBiz;
	
	@Override
	public long createUserInfo(UserInfoVo userInfoVo) {
		// TODO Auto-generated method stub
		return userBiz.createUser(userInfoVo);
	}

	@Override
	public UserInfoVo getUserInfoByID(long userId) {
		// TODO Auto-generated method stub
		UserInfoVo userInfo = userBiz.getUserInfoByID(userId);
		
		if(userInfo == null){
			return null;
		}
		
		if (!StringUtils.isEmpty(userInfo.getResumeType())
				&& userInfo.getResumeType().equalsIgnoreCase("file")) {
			String attachmentUrl = DomainConfig.getBindDomain() + userInfo.getResumeUrl();
			userInfo.setResumeUrl(attachmentUrl);
		}
		
		setUserDisplayLabel(userInfo);
		
		return userInfo;
	}

	@Override
	public int updateUserInfo(UserInfoVo userInfoVo) {
		// TODO Auto-generated method stub
		return userBiz.updateUserInfo(userInfoVo);
	}

	@Override
	public UserInfoVo getUserInfoByNickname(String nickname) {
		// TODO Auto-generated method stub
		return userBiz.getUserInfoByNickname(nickname);
	}

	@Override
	public UserInfoVo getUserInfoByMobile(String mobile) {
		// TODO Auto-generated method stub
		return userBiz.getUserInfoByMobile(mobile);
	}

	@Override
	public UserInfoVo getUserInfoByEmail(String email) {
		// TODO Auto-generated method stub
		return userBiz.getUserInfoByEmail(email);
	}

	/**
	 * 查询用户列表
	 * 
	 * @param type
	 *            用户类型(-2所有，0雇主，服务商1)
	 * @param status
	 *            认证状态(-2所有，0审核中，1认证通过，2认证驳回)
	 * @param category
	 *            认证类型(0个人，1企业)
	 */
	@Override
	public List<UserInfoVo> getListAllUserInfo(int type, int status, String category, PageResult page) {
		return userBiz.getListAllUserInfo(type, status, category, page);
	}

	@Override
	public int getAllUserInfoCount(int type, int status, String category) {
		return userBiz.getAllUserInfoCount(type, status, category);
	}

	@Override
	public List<UserInfoVo> queryUsers(String query,PageResult page) {
		return userBiz.queryUsers(query,page);
	}
	
	@Override
	public List<UserInfoVo> queryUsers4Consultant(String query,PageResult page) {
		return userBiz.queryUsers4Consultant(query,page);
	}

	@Override
	public int queryUsersCount(String query) {
		return userBiz.queryUsersCount(query);
	}
	
	@Override
	public int queryUsersCount4Consultant(String query){
		return userBiz.queryUsersCount4Consultant(query);
	}

	/**
	 * 个人信息是否完善
	 */
	@Override
	public boolean isUserInfoComplete(long userId) {
		UserInfoVo userInfoVo = getUserInfoByID(userId);
		if (StringUtils.isEmpty(userInfoVo.getName()) || StringUtils.isEmpty(userInfoVo.getMobile())
				|| StringUtils.isEmpty(userInfoVo.getQq())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 个人是否已经认证通过
	 */
	@Override
	public boolean isUserInfoIdentifiedPassed(long userId){
		IdentifyVo identifyVo = identifyBiz.getIdentifyByUID(userId);
		
		if(identifyVo != null && identifyVo.getStatus() == 1){
			return true;
		}
		return false;
		
	}

	/**
	 * 首页随机展示认证服务商
	 */
	@Override
	public List<UserInfoVo> getRecommendMemberList() {
		List<UserInfoVo> resultList = new ArrayList<UserInfoVo>();
		List<UserInfoVo> listUserInfo = userBiz.getRecommendMemberList();
		// 首页展现个数
		int displaySize = 5;
		if (listUserInfo.size() <= displaySize) {
			resultList.addAll(listUserInfo);
		} else {
			// 集合随机排序
			Collections.shuffle(listUserInfo);
			resultList.addAll(listUserInfo.subList(0, displaySize));
		}
		return resultList;
	}

	/**
	 * 修改录入服务商信息
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int modifyEmployeeForUser(UserInfoVo userInfoVo, IdentifyService identifyService,
			UserExperienceService userExperienceService) {
		int returnCode = 1;
		boolean isCreate = userInfoVo.getId()==0;
		try {
			if(isCreate){
				// 基本信息
				userInfoVo.setName(userInfoVo.getIdentifyInfo().getCompanyName());
				userInfoVo.setIsApprove(1);// 审批通过
				userInfoVo.setIsDisply(1);// 展示服务商
				userInfoVo.setUserType(1);// 类别:1.服务商
				userInfoVo.setFileSecretKey("ABCD");
				userInfoVo.getIdentifyInfo().setStatus(1);
				userInfoVo.getIdentifyInfo().setCategory(1);
				userInfoVo.getIdentifyInfo().setFileSecretKey("ABCD");
				//创建新用户
				long uid = createUserInfo(userInfoVo);
				// 认证入驻
				userInfoVo.getIdentifyInfo().setUserId(uid);
				userInfoVo.getIdentifyInfo().setStatus(1);
				identifyService.preAddIdentify(userInfoVo.getIdentifyInfo());
				//项目经验
				userExperienceService.addUserExperience(userInfoVo.getEmployeeProjectExperience(), uid);	
			}else {
				long uid = userInfoVo.getId();
				//更新用户信息
				this.updateUserInfo(userInfoVo);
				//更新认证信息
				userInfoVo.getIdentifyInfo().setUserId(uid);
				identifyService.updateIdentifyByUID(userInfoVo.getIdentifyInfo());
				//更新项目
				userExperienceService.updateProviderExperienceBatch(userInfoVo.getEmployeeProjectExperience(),uid);
			}
		} catch (Exception e) {
			returnCode = -1;
			// e.printStackTrace();
			//System.out.println(e.getMessage());
		}	
		return returnCode;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int addUserIdentify(HttpServletRequest request, UserExperienceService userExperienceService,
			UserInfoVo userInfo, String accountNum) {
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		long userId = currentUser.getId();
		//TODO   1.添加用户支付账号信息                2.更新用户支付账号id
		UserAccountVo userAccount = new UserAccountVo();
		userAccount.setAccountNum(accountNum);
		userAccount.setAccountType(1);
		userAccount.setUserId((int)userId);
		int userAccountId = userBiz.addUserPayAccount(userAccount);
		// 更新用户信息
		userInfo.setId(userId);
		userInfo.setAccountId(userAccountId);
		String resumeUrl = (String) request.getSession().getAttribute(CommonConstants.FILE_RESUME_ATTACHMENT);
		if (!StringUtils.isEmpty(resumeUrl) && userInfo.getResumeType().equalsIgnoreCase("file")) {
			userInfo.setResumeUrl(resumeUrl);
		}
		userBiz.updateUserInfo(userInfo);

		// 更新服务商经历
		userExperienceService.deleteUserAllExp(userId);
		userExperienceService.addUserExperience(userInfo.getEmployeeEduExperience(), userId);
		userExperienceService.addUserExperience(userInfo.getEmployeeJobExperience(), userId);
		userExperienceService.addUserExperience(userInfo.getEmployeeProduct(), userId);
		userExperienceService.addUserExperience(userInfo.getEmployeeProjectExperience(), userId);

		// 增加认证信息
		IdentifyVo identifyVo = userInfo.getIdentifyInfo();
		identifyVo.setUserId(userId);
		identifyVo.setStatus(0);
		if (identifyVo.getCategory() == 0) {// 个人认证
			String idCardImg = (String) request.getSession().getAttribute(CommonConstants.UPLOAD_IMG_SFZ);
			if (!StringUtils.isEmpty(idCardImg)) {
				identifyVo.setIdCardImg(idCardImg);
			}
			
		} else {// 企业认证
			String businessLicenseImg = (String) request.getSession().getAttribute(
					CommonConstants.UPLOAD_IMG_YYZZ);
			if (!StringUtils.isEmpty(businessLicenseImg)) {
				identifyVo.setBusinessLicenseImg(businessLicenseImg);
			}
		}
		
		IdentifyVo tmpIdentifyVo = identifyBiz.getIdentifyByUID(userInfo.getId());
		
		if(tmpIdentifyVo == null){
			identifyBiz.addIdentify(identifyVo);
		}else{
			identifyBiz.updateIdentifyByUID(identifyVo);
		}
		
		return 0;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int modifyUserIdentify(HttpServletRequest request, UserExperienceService userExperienceService,
			UserInfoVo userInfo,String accountNum) {
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		long userId = currentUser.getId();
		//currentUser = getUserInfoByID(userId);
		//TODO   1.添加用户支付账号信息                2.更新用户支付账号id
		UserAccountVo userAccount = new UserAccountVo();
		userAccount.setId(currentUser.getAccountId());
		userAccount.setAccountNum(accountNum);
		userAccount.setAccountType(1);
		userAccount.setUserId((int)userId);
		//如果验证没有通过，
		if(currentUser.getAccountId() != -1){
			userBiz.editUserPayAccount(userAccount);
			userInfo.setAccountId(currentUser.getAccountId());
		}else{
			int accountId = userBiz.addUserPayAccount(userAccount);
			userInfo.setAccountId(accountId);
		}
		// 更新用户信息
		userInfo.setId(userId);
		String resumeUrl = (String) request.getSession().getAttribute(CommonConstants.FILE_RESUME_ATTACHMENT);
		if (!StringUtils.isEmpty(resumeUrl) && userInfo.getResumeType().equalsIgnoreCase("file")) {
			userInfo.setResumeUrl(resumeUrl);
		}
		userBiz.updateUserInfo(userInfo);

		// 更新用户经历，先删除老数据在新增
		userExperienceService.deleteUserAllExp(userId);
		userExperienceService.addUserExperience(userInfo.getEmployeeEduExperience(), userId);
		userExperienceService.addUserExperience(userInfo.getEmployeeJobExperience(), userId);
		userExperienceService.addUserExperience(userInfo.getEmployeeProduct(), userId);
		userExperienceService.addUserExperience(userInfo.getEmployeeProjectExperience(), userId);

		// 更新认证信息
		IdentifyVo identifyVo = userInfo.getIdentifyInfo();
		identifyVo.setUserId(userId);
		if (identifyVo.getCategory() == 0) {// 个人认证
			String idCardImg = (String) request.getSession().getAttribute(CommonConstants.UPLOAD_IMG_SFZ);
			if (!StringUtils.isEmpty(idCardImg)) {
				identifyVo.setIdCardImg(idCardImg);
			}
		} else {// 企业认证
			String businessLicenseImg = (String) request.getSession().getAttribute(
					CommonConstants.UPLOAD_IMG_YYZZ);
			if (!StringUtils.isEmpty(businessLicenseImg)) {
				identifyVo.setBusinessLicenseImg(businessLicenseImg);
			}
		}
		
		if(identifyVo.getStatus() == 4){
			identifyVo.setStatus(1);// 已认证
		}else{
			identifyVo.setStatus(0);
		}
		
		identifyBiz.updateIdentifyByUID(identifyVo);
		return 0;
	}
	
	@Override
	public List<UserInfoVo> getDisplayProviderListP(PageResult page,String query,int userType,int category,String regionId){
		return userBiz.getDisplayProviderListP(page,query,userType,category,regionId);
	}
	

	@Override
	public int getDisplayProviderListCount(int userType,String query,int category,String regionId) {
		return userBiz.getDisplayProviderListCount(userType,query,category,regionId);
	}

	/**
	 * 获取顾问
	 */
	@Override
	public List<UserInfoVo> getAllConsultants(){
		return userBiz.getAllConsultants();
	}


	@Override
	public boolean setProviderDisplayStatus(long uid, int actionType) {
		boolean result = false;
		if(actionType==0||actionType==1){
			userBiz.updateProviderDisplayByID(uid, actionType);
			result = true;
		}
		return result;
	}

	@Override
	public int updateProviderDeleteStatusByID(long uid ,int isDelete) {
		return userBiz.updateProviderDeleteStatusByID(uid,isDelete);
	}

	@Override
	public List<UserInfoVo> getInviteServicerFuzzList(String query,String projectId,String regionId) {
		return userBiz.getInviteServicerFuzzList(query,projectId,regionId);
	}

	@Override
	public int getInviteServicerFuzzCount(String query,String projectId) {
		return userBiz.getInviteServicerFuzzCount(query,projectId);
	}

	@Override
	public List<UserInfoVo> getProjectInSelfRun(String projectId) {
		return userBiz.getProjectInSelfRun(projectId);
	}

	@Override
	public List<UserInfoImportVo> getUserInfoImportList(String query,String skillQuery,PageResult page) {
		return userBiz.getUserInfoImportList(query,skillQuery,page);
	}

	@Override
	public int getUserInfoImportCount(String query,String skillQuery,PageResult page) {
		return userBiz.getUserInfoImportCount(query,skillQuery,page);
	}

	@Override
	public UserInfoImportVo getUserInfoImportInfo(long uid) {
		return userBiz.getUserInfoImportInfo(uid);
	}

	@Override
	public int getNewUsersCount(String startQueryTime, String endQueryTime) {
		// TODO Auto-generated method stub
		return userBiz.getNewUsersCount(startQueryTime, endQueryTime);
	}

	@Override
	public int getNewApprovedUsersCount(String startQueryTime,String endQueryTime,String category) {
		// TODO Auto-generated method stub
		return userBiz.getNewApprovedUsersCount(startQueryTime,endQueryTime,category);
	}

	@Override
	public List<UserInfoVo> queryDevelopers(String provinceId, String cityId, String cando, String ability,
			String otherAbility, PageResult page) {

		List<UserInfoVo> listVo = userBiz.queryDevelopers(provinceId, cityId, cando, ability, otherAbility,
				page);
		for (UserInfoVo uVo : listVo) {
			long uid = uVo.getId();
			// 擅长技能
			String[] abilityItemList = LabelUtil.getItemName("ability", uVo.getMainAbility());
			uVo.setDisplayMainAbility(abilityItemList);
			// 胜任工作
			String[] skillItemList = LabelUtil.getItemName("cando", uVo.getCando());
			uVo.setDisplayCando(skillItemList);

			uVo.setEmployeeEduExperience(userExperienceBiz.getEduExpListByUID(uid));
			uVo.setEmployeeJobExperience(userExperienceBiz.getJobExpListByUID(uid));
			uVo.setEmployeeProduct(userExperienceBiz.getEmployeeProListByUID(uid));
			uVo.setEmployeeProjectExperience(userExperienceBiz.getTeamProjectListByUID(uid));
		}
		return listVo;
	}

	@Override
	public int queryDevelopersTotalCount(String provinceId, String cityId, String cando, String ability,
			String otherAbility) {
		// TODO Auto-generated method stub
		return userBiz.queryDevelopersTotalCount(provinceId, cityId, cando, ability, otherAbility);
	}
	
	/*
	 * 显示用户标签文本
	 */
	private void setUserDisplayLabel(UserInfoVo userInfo) {
		// 擅长技能
		String[] abilityItemList = LabelUtil.getItemName("ability", userInfo.getMainAbility());
		userInfo.setDisplayMainAbility(abilityItemList);
		// 服务领域
		String[] caseTypeItemList = LabelUtil.getItemName("caseType", userInfo.getCaseType());
		userInfo.setDisplayCaseType(caseTypeItemList);
		// 胜任工作
		String[] skillItemList = LabelUtil.getItemName("cando", userInfo.getCando());
		userInfo.setDisplayCando(skillItemList);
	}

	@Override
	public int addUserPayAccount(UserAccountVo userAccount) {
		return userBiz.addUserPayAccount(userAccount);
	}

	@Override
	public int editUserPayAccount() {
		return 0;
	}

	@Override
	public int deleteUserPayAccount() {
		return 0;
	}

	@Override
	public int addAccountInDetail(UserAccountInDetailVo detailVo) {
		// TODO Auto-generated method stub
		return userBiz.addAccountInDetail(detailVo);
	}

	@Override
	public int addAccountOutDetail(UserAccountOutDetailVo detailVo) {
		// TODO Auto-generated method stub
		return userBiz.addAccountOutDetail(detailVo);
	}
	

	@Override
	public List<UserAccountInDetailVo> getAccountInDetailVo(int userId,
			PageResult page) {
		return userBiz.getAccountInDetail(userId, page);
	}

	@Override
	public List<UserAccountOutDetailVo> getAccountOutDetailVo(int userId,
			PageResult page) {
		return userBiz.getAccountOutDetail(userId, page);
	}
	@Override
	public int getAccountInDetailCount(int userId) {
		return userBiz.getAccountInDetailCount(userId);
	}
	@Override
	public int getAccountOutDetailCount(int userId) {
		return userBiz.getAccountOutDetailCount(userId);
	}

	@Override
	public UserAccountVo getUseraccountById(int id) {
		return userBiz.getUseraccountById(id);
	}

	@Override
	public double getUserTotalIncome(int userId) {
		return userBiz.getUserTotalIncome(userId);
	}

	@Override
	public double getUserTotalOutcome(int userId) {
		return userBiz.getUserTotalOutcome(userId);
	}
}
