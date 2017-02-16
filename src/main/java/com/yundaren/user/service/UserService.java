 package com.yundaren.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yundaren.common.util.PageResult;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;
import com.yundaren.user.vo.UserAccountInDetailVo;
import com.yundaren.user.vo.UserAccountOutDetailVo;
import com.yundaren.user.vo.UserAccountVo;
import com.yundaren.user.vo.UserInfoImportVo;
import com.yundaren.user.vo.UserInfoVo;

/**
 * 
 * @author kai.xu 普通用户信息服务
 */
public interface UserService {

	long createUserInfo(UserInfoVo userInfoVo);

	UserInfoVo getUserInfoByID(long userId);

	UserInfoVo getUserInfoByNickname(String nickname);

	UserInfoVo getUserInfoByMobile(String mobile);

	UserInfoVo getUserInfoByEmail(String email);
	
	int updateUserInfo(UserInfoVo userInfoVo);

	/**
	 * 个人信息是否完善
	 */
	boolean isUserInfoComplete(long userId);
	
	boolean isUserInfoIdentifiedPassed(long userId);

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
	List<UserInfoVo> getListAllUserInfo(int type, int status, String category, PageResult page);
	
	/**
	 * 搜索查询用户
	 */
	List<UserInfoVo> queryUsers(String query, PageResult page);
	List<UserInfoVo> queryUsers4Consultant(String query, PageResult page);
	
	int queryUsersCount(String query);
	int queryUsersCount4Consultant(String query);
	
	int getAllUserInfoCount(int type, int status, String category);

	/**
	 * 首页随机展示认证服务商
	 */
	List<UserInfoVo> getRecommendMemberList();
	/**
	 * 服务商信息录入
	 */
	int modifyEmployeeForUser(UserInfoVo userInfoVo, IdentifyService identifyService,
			UserExperienceService userExperienceService);

	/**
	 * 新增服务商认证
	 */
	int addUserIdentify(HttpServletRequest request, UserExperienceService userExperienceService,
			UserInfoVo userInfoVo, String accountNum);

	/**
	 * 服务商认证修改
	 */
	int modifyUserIdentify(HttpServletRequest request, UserExperienceService userExperienceService,
			UserInfoVo userInfoVo, String accountNum);
	
	/**
	 * 服务商展示列表查询	 
	 */

	List<UserInfoVo> getDisplayProviderListP(PageResult page,String query,int userType,int category,String regionQuery);
	
	/**
	 * 服务商展示总记录数
	 */
	int getDisplayProviderListCount(int userType,String query,int category,String regionQuery);
	
	/**
	 * 服务商上架/下架
	 */
	boolean setProviderDisplayStatus(long uid,int actionType);
	
	 /**
	 * 获取所有顾问
	 */
	List<UserInfoVo> getAllConsultants();

	int updateProviderDeleteStatusByID(long uid ,int isDelete);
	
	List<UserInfoVo> getInviteServicerFuzzList(String query,String projectId,String regionId);
	
	int getInviteServicerFuzzCount(String query,String projectId);
	
	List<UserInfoVo> getProjectInSelfRun(String projectId);
	
	List<UserInfoImportVo> getUserInfoImportList(String query,String skillQuery ,PageResult page);
	
	int getUserInfoImportCount(String query,String skillQuery,PageResult page);
	
	UserInfoImportVo getUserInfoImportInfo(long uid);
	
	int getNewUsersCount(String startQueryTime,String endQueryTime);
	
	int getNewApprovedUsersCount(String startQueryTime,String endQueryTime,String category);
	
	/**
	 * 码客库查询
	 */
	List<UserInfoVo> queryDevelopers(String provinceId, String cityId, String cando, String ability,
			String otherAbility, PageResult page);
	
	int queryDevelopersTotalCount(String provinceId, String cityId, String cando, String ability,
			String otherAbility);
	
	/**
	 * 添加用户个人支付账号
	 */
	int addUserPayAccount(UserAccountVo userAccount);
	
	/**
	 * 修改个人支付账号
	 */
	int editUserPayAccount();
	
	/**
	 * 删除用户个人支付账号
	 */
	int deleteUserPayAccount();
	
	/**
	 * 增加一条入账记录
	 */
	int addAccountInDetail(UserAccountInDetailVo detailVo);
	
	/**
	 * 查询入账记录（分页查询）
	 */
	List<UserAccountInDetailVo> getAccountInDetailVo(int userId,PageResult page);
	/**
	 * 查询用户入账记录数目
	 */
	int getAccountInDetailCount(int userId);
	/**
	 * 增加一条提现记录
	 */
	int addAccountOutDetail(UserAccountOutDetailVo detailVo);
	/**
	 * 查询提现记录（分页查询）
	 */
	List<UserAccountOutDetailVo> getAccountOutDetailVo(int userId,PageResult page);
	/**
	 * 获取用户提现记录数目
	 * @param userId
	 * @return
	 */
	int getAccountOutDetailCount(int userId);
	
	/**
	 * 获取用户账号信息
	 * @Param id
	 */
	UserAccountVo getUseraccountById(int id);
	
	/**
	 * 获得用户的总收入
	 */
	double getUserTotalIncome(int userId);
	/**
	 * 获得用户总支出(提现)
	 */
	double getUserTotalOutcome(int userId);
	
}
