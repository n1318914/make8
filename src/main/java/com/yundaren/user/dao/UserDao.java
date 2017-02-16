package com.yundaren.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.cache.annotation.Cacheable;

import com.yundaren.common.util.PageResult;
import com.yundaren.filter.handler.cache.RedisCacheEvict;
import com.yundaren.filter.handler.cache.RedisCacheable;
import com.yundaren.user.po.UserAccountInDetailPo;
import com.yundaren.user.po.UserAccountOutDetailPo;
import com.yundaren.user.po.UserAccountPo;
import com.yundaren.user.po.UserInfoImportPo;
import com.yundaren.user.po.UserInfoPo;

public class UserDao extends SqlSessionDaoSupport {

	public long createUser(UserInfoPo userInfoPo) {
		getSqlSession().insert("createUser", userInfoPo);
		return userInfoPo.getId();
	}

	@RedisCacheable(key = "getUserInfoByNickname", field = "#nickname")
	public List<UserInfoPo> getUserInfoByNickname(String nickname) {
		return getSqlSession().selectList("getUserInfoByNickname", nickname);
	}

	@RedisCacheable(key = "getUserInfoByMobile", field = "#mobile")
	public List<UserInfoPo> getUserInfoByMobile(String mobile) {
		return getSqlSession().selectList("getUserInfoByMobile", mobile);
	}

	@RedisCacheable(key = "getUserInfoByEmail", field = "#email")
	public List<UserInfoPo> getUserInfoByEmail(String email) {
		return getSqlSession().selectList("getUserInfoByEmail", email);
	}

	@RedisCacheable(key = "getUserInfoByID", field = "#userId")
	public UserInfoPo getUserInfoByID(long userId) {
		return getSqlSession().selectOne("getUserInfoByID", userId);
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
	public List<UserInfoPo> getListAllUserInfo(int type, int status, String category, PageResult page) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("type", type);
		paramsMap.put("status", status);
		paramsMap.put("category", category);
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("getListAllUserInfo", paramsMap);
	}
	/**
	 * 服务商展示列表查询
	 */
	public List<UserInfoPo> getDisplayProviderListP(PageResult page,String query,int userType,int category,String regionId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		paramsMap.put("userType",userType);
		paramsMap.put("category",category);
		if(regionId!=null){
			paramsMap.put("regionId",regionId);
		}
		return getSqlSession().selectList("getDisplayProviderListP", paramsMap);
	}
	public int getAllUserInfoCount(int type, int status, String category) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("type", type);
		paramsMap.put("status", status);
		paramsMap.put("category", category);
		return getSqlSession().selectOne("getAllUserInfoCount", paramsMap);
	}

	public List<UserInfoPo> queryUsers(String query,PageResult page) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("queryUsers", paramsMap);
	}
	
	public List<UserInfoPo> queryUsers4Consultant(String query,PageResult page) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("queryUsers4Consultant", paramsMap);
	}
	
	public int queryUsersCount(String query) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		return getSqlSession().selectOne("queryUsersCount", paramsMap);
	}
	
	public int queryUsersCount4Consultant(String query) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		return getSqlSession().selectOne("queryUsersCount4Consultant", paramsMap);
	}

	@RedisCacheEvict(key = {"getUserInfoByNickname", "getUserInfoByMobile", "getUserInfoByEmail",
			"getUserInfoByID"}, field = {"#userInfoPo.name", "#userInfoPo.mobile","#userInfoPo.email","#userInfoPo.id"})
	public int updateUserInfo(UserInfoPo userInfoPo) {
		return getSqlSession().update("updateUserByID", userInfoPo);
	}
	
	public int updateProviderDeleteStatusByID(long uid ,int isDelete) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", uid);
		paramsMap.put("isDelete", isDelete);
		return getSqlSession().update("updateProviderDeleteStatusByID", paramsMap);
	}

	public List<String> getAllMembersEmail(){
		return getSqlSession().selectList("getAllMembersEmail");
	}
	
	public List<String> getJoinersEmailByPID(String projectId) {
		return getSqlSession().selectList("getJoinersEmailByPID", projectId);
	}

	public List<UserInfoPo> getRecommendMemberList() {
		return getSqlSession().selectList("getRecommendMemberList");
	}
	
	public int getAllUserCount() {
		return getSqlSession().selectOne("getAllUserCount");
	}

	public int getAllDeveloperCount() {
		return getSqlSession().selectOne("getAllDeveloperCount");
	}

	public int getAllCompanyCount() {
		return getSqlSession().selectOne("getAllCompanyCount");
	}
	
	public int getAllIdentifyCount() {
		return getSqlSession().selectOne("getAllIdentifyCount");
	}
	
	public int getDisplayProviderListCount(int userType,String query,int category,String regionId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("category", category);
		paramsMap.put("userType", userType);
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		if(regionId!=null){		
			paramsMap.put("regionId",regionId);
		}
		return getSqlSession().selectOne("getDisplayProviderListCount",paramsMap);
	}
	
	public int updateProviderDisplayByID(long uid,int actionType) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		int isDisply = actionType;
		paramsMap.put("id", uid);
		paramsMap.put("isDisply", isDisply);
		return getSqlSession().update("updateProviderDisplayByID", paramsMap);
    }
   
   
    public UserInfoPo getConsultantByCondition(String condition){
		return getSqlSession().selectOne("getConsultant", condition);
	}
	
	public UserInfoPo getConsultantById(long id){
		return null;
	}
	
	public List<UserInfoPo> getAllConsultants(){
		return getSqlSession().selectList("getAllConsultants");
	}
	
	public List<UserInfoPo> getInviteServicerFuzzList(String query,String projectId,String regionId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		if(regionId!=null){
			paramsMap.put("regionId", regionId);
		}
		return getSqlSession().selectList("getInviteServicerFuzzList",paramsMap);
	}
	
	public int getInviteServicerFuzzCount(String query,String projectId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		return getSqlSession().selectOne("getInviteServicerFuzzCount",paramsMap);
	}
	
	public List<UserInfoPo> getsProjSelfRunServicerList(String projectId){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("projectId", projectId);
		return getSqlSession().selectList("getsProjSelfRunServicerList",paramsMap);
	}
	
	public List<UserInfoImportPo> getUserInfoImportList(String query,String skillQuery,PageResult pageResult){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("skillQuery", "%" + skillQuery + "%");
		paramsMap.put("beginPage", pageResult.startRow());
		paramsMap.put("pageSize", pageResult.getPageSize());
		return getSqlSession().selectList("getUserImportList",paramsMap);
	}
	
	public int getUserInfoImportCount(String query,String skillQuery,PageResult pageResult){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("query", query + "%");
		paramsMap.put("matchAllQuery", "%" + query + "%");
		paramsMap.put("skillQuery", skillQuery);
		paramsMap.put("beginPage", pageResult.startRow());
		paramsMap.put("pageSize", pageResult.getPageSize());
		return getSqlSession().selectOne("getUserImportCount",paramsMap);
	}
	
	public UserInfoImportPo getUserInfoImportInfo(long uid){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("uid", uid);
		return getSqlSession().selectOne("getUserImportInfo",paramsMap);
	}
	
	public int getNewUsersCount(String startQueryTime,String endQueryTime){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("startQueryTime", startQueryTime);
		paramsMap.put("endQueryTime", endQueryTime);
		return getSqlSession().selectOne("getNewUserCount",paramsMap);
	}
	
	public int getNewApprovedUsersCount(String startQueryTime,String endQueryTime,String category){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("startQueryTime", startQueryTime);
		paramsMap.put("endQueryTime", endQueryTime);
		paramsMap.put("category", category);
		return getSqlSession().selectOne("getNewApprovedUserCount",paramsMap);
	}
	
	public List<UserInfoPo> queryDevelopers(String provinceId, String cityId, String cando, String ability,
			String otherAbility, PageResult page) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();

		paramsMap.put("provinceId", provinceId);
		paramsMap.put("cityId", cityId);

		if (StringUtils.isEmpty(cando)) {
			paramsMap.put("cando", cando);
		} else {
			paramsMap.put("cando", "%" + cando + "%");
		}

		if (StringUtils.isEmpty(ability)) {
			paramsMap.put("ability", ability);
		} else {
			paramsMap.put("ability", "%" + ability + "%");
		}

		if (StringUtils.isEmpty(otherAbility)) {
			paramsMap.put("otherAbility", otherAbility);
		} else {
			paramsMap.put("otherAbility", "%" + otherAbility.toUpperCase() + "%");
		}
		
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("queryDevelopers", paramsMap);
	}
	
	public int queryDevelopersTotalCount(String provinceId, String cityId, String cando, String ability,
			String otherAbility) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();

		paramsMap.put("provinceId", provinceId);
		paramsMap.put("cityId", cityId);

		if (StringUtils.isEmpty(cando)) {
			paramsMap.put("cando", cando);
		} else {
			paramsMap.put("cando", "%" + cando + "%");
		}

		if (StringUtils.isEmpty(ability)) {
			paramsMap.put("ability", ability);
		} else {
			paramsMap.put("ability", "%" + ability  + "%");
		}

		if (StringUtils.isEmpty(otherAbility)) {
			paramsMap.put("otherAbility", otherAbility);
		} else {
			paramsMap.put("otherAbility", "%" + otherAbility.toUpperCase() + "%");
		}

		return getSqlSession().selectOne("queryDevelopersTotalCount", paramsMap);
	}

	public int addUserPayAccount(UserAccountPo userAccount){
		return getSqlSession().insert("addUserPayAccount", userAccount);
	};
	public int editUserPayAccount(UserAccountPo userAccount){
		return getSqlSession().update("editUserPayAccount", userAccount);
	};
	public int deleteUserpayAccount(UserAccountPo userAccount){
		return getSqlSession().delete("deleteUserpayAccount", userAccount);
	};
	
	public int addAccountInDetail(UserAccountInDetailPo detailPo){
		return getSqlSession().insert("addAccountInDetail",detailPo);
	}
	public List<UserAccountInDetailPo> getAccountInDetail(int userId,PageResult page){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("getAccountInDetail",paramsMap);
	}
	public int getAccountInDetailCount(int userId){
		return getSqlSession().selectOne("getAccountInDetailCount",userId);
	}
	
	
	public int addAccountOutDetail(UserAccountOutDetailPo detailPo){
		return getSqlSession().insert("addAccountOutDetail",detailPo);
	}
	public List<UserAccountOutDetailPo> getAccountOutDetail(int userId,PageResult page){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("beginPage", page.startRow());
		paramsMap.put("pageSize", page.getPageSize());
		return getSqlSession().selectList("getAccountOutDetail",paramsMap);
	}
	public int getAccountOutDetailCount(int userId){
		return getSqlSession().selectOne("getAccountOutDetailCount",userId);
	}
	public UserAccountPo getUseraccountById(int id){
		return getSqlSession().selectOne("getUseraccountById",id);
	}
	
	public double getUserTotalIncome(int userId){
		return getSqlSession().selectOne("getUserTotalIncome",userId);
	}
	public double getUserTotalOutcome(int userId){
		return getSqlSession().selectOne("getUserTotalOutcome",userId);
	}
	
}