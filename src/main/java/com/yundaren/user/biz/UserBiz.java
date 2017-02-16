package com.yundaren.user.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.common.util.PageResult;
import com.yundaren.user.dao.UserDao;
import com.yundaren.user.po.UserAccountInDetailPo;
import com.yundaren.user.po.UserAccountOutDetailPo;
import com.yundaren.user.po.UserAccountPo;
import com.yundaren.user.po.UserInfoImportPo;
import com.yundaren.user.po.UserInfoPo;
import com.yundaren.user.vo.UserAccountInDetailVo;
import com.yundaren.user.vo.UserAccountOutDetailVo;
import com.yundaren.user.vo.UserAccountVo;
import com.yundaren.user.vo.UserInfoImportVo;
import com.yundaren.user.vo.UserInfoVo;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserBiz {

	@Setter
	private UserDao userDao;

	public long createUser(UserInfoVo userInfoVo) {
		UserInfoPo userInfoPo = BeanMapper.map(userInfoVo, UserInfoPo.class);
		return userDao.createUser(userInfoPo);
	}

	public UserInfoVo getUserInfoByID(long userId) {
		UserInfoVo userInfoVo = null;
		UserInfoPo userInfoPo = userDao.getUserInfoByID(userId);
		if (userInfoPo != null) {
			userInfoVo = BeanMapper.map(userInfoPo, UserInfoVo.class);
		}
		return userInfoVo;
	}

	public int updateUserInfo(UserInfoVo userInfoVo) {
		UserInfoPo userInfoPo = BeanMapper.map(userInfoVo, UserInfoPo.class);
		return userDao.updateUserInfo(userInfoPo);
	}

	public UserInfoVo getUserInfoByNickname(String nickname) {
		UserInfoVo userInfoVo = null;
		List<UserInfoPo> listUserInfoPo = userDao.getUserInfoByNickname(nickname);

		if (listUserInfoPo.size() > 1) {
			log.warn("Warning! getUserInfoByNickname nickname=" + nickname + ", find "
					+ listUserInfoPo.size() + " record");
		}

		if (!CollectionUtils.isEmpty(listUserInfoPo)) {
			userInfoVo = BeanMapper.map(listUserInfoPo.get(0), UserInfoVo.class);
		}
		return userInfoVo;
	}

	public UserInfoVo getUserInfoByMobile(String mobile) {
		UserInfoVo userInfoVo = null;
		List<UserInfoPo> listInfoPo = userDao.getUserInfoByMobile(mobile);

		if (listInfoPo.size() > 1) {
			log.warn("Warning! getUserInfoByMobile mobile=" + mobile + ", find " + listInfoPo.size()
					+ " record");
		}

		if (!CollectionUtils.isEmpty(listInfoPo)) {
			userInfoVo = BeanMapper.map(listInfoPo.get(0), UserInfoVo.class);
		}
		return userInfoVo;
	}

	public UserInfoVo getUserInfoByEmail(String email) {
		UserInfoVo userInfoVo = null;
		List<UserInfoPo> listInfoPo = userDao.getUserInfoByEmail(email);

		if (listInfoPo.size() > 1) {
			log.warn("Warning! getUserInfoByEmail email=" + email + ", find " + listInfoPo.size() + " record");
		}

		if (!CollectionUtils.isEmpty(listInfoPo)) {
			userInfoVo = BeanMapper.map(listInfoPo.get(0), UserInfoVo.class);
		}
		return userInfoVo;
	}

	public List<UserInfoVo> getListAllUserInfo(int type, int status, String category, PageResult page) {
		List<UserInfoVo> listUserInfoVo = new ArrayList<UserInfoVo>();
		List<UserInfoPo> listUserInfoPo = userDao.getListAllUserInfo(type, status, category, page);

		if (!CollectionUtils.isEmpty(listUserInfoPo)) {
			listUserInfoVo = BeanMapper.mapList(listUserInfoPo, UserInfoVo.class);
		}
		return listUserInfoVo;
	}

	public int getAllUserInfoCount(int type, int status, String category) {
		return userDao.getAllUserInfoCount(type, status, category);
	}

	public List<UserInfoVo> queryUsers(String query,PageResult page) {
		List<UserInfoVo> listUserInfoVo = new ArrayList<UserInfoVo>();
		List<UserInfoPo> listUserInfoPo = userDao.queryUsers(query,page);

		if (!CollectionUtils.isEmpty(listUserInfoPo)) {
			listUserInfoVo = BeanMapper.mapList(listUserInfoPo, UserInfoVo.class);
		}
		return listUserInfoVo;
	}
	
	public List<UserInfoVo> queryUsers4Consultant(String query,PageResult page) {
		List<UserInfoVo> listUserInfoVo = new ArrayList<UserInfoVo>();
		List<UserInfoPo> listUserInfoPo = userDao.queryUsers4Consultant(query,page);

		if (!CollectionUtils.isEmpty(listUserInfoPo)) {
			listUserInfoVo = BeanMapper.mapList(listUserInfoPo, UserInfoVo.class);
		}
		return listUserInfoVo;
	}

	public int queryUsersCount(String query) {
		return userDao.queryUsersCount(query);
	}

	public int queryUsersCount4Consultant(String query) {
		return userDao.queryUsersCount4Consultant(query);
	}
	
	public List<String> getAllMembersEmail() {
		return userDao.getAllMembersEmail();
	}

	public List<String> getJoinersEmailByPID(String projectId) {
		return userDao.getJoinersEmailByPID(projectId);
	}

	public List<UserInfoVo> getRecommendMemberList() {
		List<UserInfoVo> listUserInfoVo = new ArrayList<UserInfoVo>();
		List<UserInfoPo> listUserInfoPo = userDao.getRecommendMemberList();

		if (!CollectionUtils.isEmpty(listUserInfoPo)) {
			listUserInfoVo = BeanMapper.mapList(listUserInfoPo, UserInfoVo.class);
		}
		return listUserInfoVo;
	}
	
	public int getAllDeveloperCount() {
		return userDao.getAllDeveloperCount();
	}

	public int getAllCompanyCount() {
		return userDao.getAllCompanyCount();
	}
	
	public int getAllIdentifyCount() {
		return userDao.getAllIdentifyCount();
	}

	public int getAllUserCount() {
		return userDao.getAllUserCount();
	}
	/**
	 * �������б�
	 * @param nickname
	 * @return
	 */
	public List<UserInfoVo> getDisplayProviderListP(PageResult page,String query,int userType,int category,String regionId) {
		List<UserInfoVo> listUserInfoVo = new ArrayList<UserInfoVo>();
		List<UserInfoPo> listUserInfoPo = userDao.getDisplayProviderListP(page,query,userType,category,regionId);

		if (!CollectionUtils.isEmpty(listUserInfoPo)) {
			listUserInfoVo = BeanMapper.mapList(listUserInfoPo, UserInfoVo.class);
		}
		return listUserInfoVo;
	}
	

	public int getDisplayProviderListCount(int userType,String query,int category,String regionId){
		return userDao.getDisplayProviderListCount(userType,query,category,regionId);
	}
	
	public int updateProviderDisplayByID(long uid,int isDisply){
		return userDao.updateProviderDisplayByID(uid,isDisply);
	}

	public List<UserInfoVo> getAllConsultants(){
    	List<UserInfoPo> consultantsPoList = userDao.getAllConsultants();
    	List<UserInfoVo> consultantsVoList = null;
    	
    	if(!CollectionUtils.isEmpty(consultantsPoList)){
    		consultantsVoList = BeanMapper.mapList(consultantsPoList, UserInfoVo.class);
    	}
    	
    	return consultantsVoList;
    }

	public int updateProviderDeleteStatusByID(long uid ,int isDelete) {
		return userDao.updateProviderDeleteStatusByID(uid,isDelete);
	}
	
	public List<UserInfoVo> getInviteServicerFuzzList(String query,String projectId,String regionId){
	  	List<UserInfoPo> consultantsPoList = userDao.getInviteServicerFuzzList(query,projectId,regionId);
    	List<UserInfoVo> consultantsVoList = null;
    	
    	if(!CollectionUtils.isEmpty(consultantsPoList)){
    		consultantsVoList = BeanMapper.mapList(consultantsPoList, UserInfoVo.class);
    	}
		return consultantsVoList;
	}
	
	public int getInviteServicerFuzzCount(String query,String projectId){
		return userDao.getInviteServicerFuzzCount(query,projectId);
	}
	
	public List<UserInfoVo> getProjectInSelfRun(String projectId) {
		List<UserInfoPo> consultantsPoList = userDao.getsProjSelfRunServicerList(projectId);
    	List<UserInfoVo> consultantsVoList = null;
    	
    	if(!CollectionUtils.isEmpty(consultantsPoList)){
    		consultantsVoList = BeanMapper.mapList(consultantsPoList, UserInfoVo.class);
    	}
		return consultantsVoList;
	}
	
	public List<UserInfoImportVo> getUserInfoImportList(String query,String skillQuery,PageResult page){
		List<UserInfoImportPo> userInfoImportPoList = userDao.getUserInfoImportList(query,skillQuery,page);
    	List<UserInfoImportVo> userInfoImportVoList = null;
    	
    	if(!CollectionUtils.isEmpty(userInfoImportPoList)){
    		userInfoImportVoList = BeanMapper.mapList(userInfoImportPoList, UserInfoImportVo.class);
    	}
		return userInfoImportVoList;
	}
	
	public int getUserInfoImportCount(String query,String skillQuery,PageResult page){
		return userDao.getUserInfoImportCount(query,skillQuery,page);
	}
	public UserInfoImportVo getUserInfoImportInfo(long uid){	
		UserInfoImportVo userInfoImportVo = null;
		UserInfoImportPo userInfoImportPo = userDao.getUserInfoImportInfo(uid);
		if(userInfoImportPo!=null)userInfoImportVo = BeanMapper.map(userInfoImportPo, UserInfoImportVo.class);
		return userInfoImportVo;
	}
	
	public int getNewUsersCount(String startQueryTime,String endQueryTime){
		return userDao.getNewUsersCount(startQueryTime, endQueryTime);
	}
	
	public int getNewApprovedUsersCount(String startQueryTime,String endQueryTime,String category){
		return userDao.getNewApprovedUsersCount(startQueryTime,endQueryTime,category);
	}

	public List<UserInfoVo> queryDevelopers(String provinceId, String cityId, String cando, String ability,
			String otherAbility, PageResult page) {
		List<UserInfoPo> poList = userDao.queryDevelopers(provinceId, cityId, cando, ability, otherAbility,
				page);
		List<UserInfoVo> voList = new ArrayList<UserInfoVo>();
		if (!CollectionUtils.isEmpty(poList)) {
			voList = BeanMapper.mapList(poList, UserInfoVo.class);
		}
		return voList;
	}
	
	public int queryDevelopersTotalCount(String provinceId, String cityId, String cando, String ability,
			String otherAbility) {
		return userDao.queryDevelopersTotalCount(provinceId, cityId, cando, ability, otherAbility);
	}
	
	public int addUserPayAccount(UserAccountVo userAccountVo){
		UserAccountPo userAccount = BeanMapper.map(userAccountVo, UserAccountPo.class);
		userDao.addUserPayAccount(userAccount);
		return userAccount.getId();
	};
	public int editUserPayAccount(UserAccountVo userAccountVo){
		UserAccountPo userAccount = BeanMapper.map(userAccountVo, UserAccountPo.class);
		return userDao.editUserPayAccount(userAccount);
	};
	public int deleteUserpayAccount(UserAccountVo userAccountVo){
		UserAccountPo userAccount = BeanMapper.map(userAccountVo, UserAccountPo.class);
		return userDao.deleteUserpayAccount(userAccount);
	};
	
	
	//添加用户入账记录
	public int addAccountInDetail(UserAccountInDetailVo detailVo){
		UserAccountInDetailPo detailPo = BeanMapper.map(detailVo, UserAccountInDetailPo.class);
		return userDao.addAccountInDetail(detailPo);
	}
	//获取用户入账记录
	public List<UserAccountInDetailVo> getAccountInDetail(int userId,PageResult page){
		List<UserAccountInDetailPo> poList = userDao.getAccountInDetail(userId,page);
		List<UserAccountInDetailVo> voList = new ArrayList<UserAccountInDetailVo>();
		if (!CollectionUtils.isEmpty(poList)) {
			voList = BeanMapper.mapList(poList, UserAccountInDetailVo.class);
		}
		return voList;
	}
	//获取用户入账记录数目
	public int getAccountInDetailCount(int userId){
		return userDao.getAccountInDetailCount(userId);
	}
	
	//添加用户出账记录
	public int addAccountOutDetail(UserAccountOutDetailVo detailVo){
		UserAccountOutDetailPo detailPo = BeanMapper.map(detailVo, UserAccountOutDetailPo.class);
		return userDao.addAccountOutDetail(detailPo);
	}
	//获取用户入账记录
	public List<UserAccountOutDetailVo> getAccountOutDetail(int userId,PageResult page){
		List poList = userDao.getAccountOutDetail(userId,page);
		List<UserAccountOutDetailVo> voList = new ArrayList<UserAccountOutDetailVo>();
		if (!CollectionUtils.isEmpty(poList)) {
			voList = BeanMapper.mapList(poList, UserAccountOutDetailVo.class);
		}
		return voList;
	}
	//获取用户提现记录数目
	public int getAccountOutDetailCount(int userId){
		return userDao.getAccountOutDetailCount(userId);
	}
	//获取用户账号信息
	public UserAccountVo getUseraccountById(int id){
		UserAccountVo accountVo = null;
		UserAccountPo accountPo = userDao.getUseraccountById(id);
		if(null != accountPo)accountVo = BeanMapper.map(accountPo, UserAccountVo.class);
		return accountVo;
	}
	
	//获取用户总收入
	public double getUserTotalIncome(int userId){
		return userDao.getUserTotalIncome(userId);
	} 
	//获取用户总支出(提现)
	public double getUserTotalOutcome(int userId){
		return userDao.getUserTotalOutcome(userId);
	}
}
