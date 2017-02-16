package com.yundaren.user.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.cache.annotation.Cacheable;

import com.google.common.collect.ImmutableMap;
import com.yundaren.user.po.SsoUserPo;
import com.yundaren.user.vo.SsoUserVo;

public class SsoDao extends SqlSessionDaoSupport {

	public long createSsoUser(SsoUserPo ssoUserPo) {
		getSqlSession().insert("createSsoUser", ssoUserPo);
		return ssoUserPo.getId();
	}

	public SsoUserPo getSsoUserByUserName(String userName) {
		return getSqlSession().selectOne("getSsoUserByUserName", userName);
	}

	public SsoUserPo getSsoUserById(long id) {
		return getSqlSession().selectOne("getSsoUserById", id);
	}

	public int update2ActiveStatus(String loginName) {
		return getSqlSession().update("update2ActiveStatus", ImmutableMap.of("loginName", loginName));
	}

	public int updateSsoUser(SsoUserPo ssoUserPo) {
		return getSqlSession().update("updateSsoUser", ssoUserPo);
	}
	
	public int updateSsoUserPassword(String loginName, String newPassword) {
		return getSqlSession().update("updateSsoUserPassword",
				ImmutableMap.of("loginName", loginName, "newPassword", newPassword));
	}
	
	public int updatePasswordByID(long uid, String newPassword) {
		return getSqlSession().update("updatePasswordByID",
				ImmutableMap.of("uid", uid, "newPassword", newPassword));
	}

	public int updateLoginName(String oldLoginName, String newLoginName) {
		return getSqlSession().update("updateLoginName",
				ImmutableMap.of("oldLoginName", oldLoginName, "newLoginName", newLoginName));
	}
	
	public SsoUserPo getSsoUserByAccountType(long userId, int accountType) {
		return getSqlSession().selectOne("getSsoUserByAccountType",
				ImmutableMap.of("userId", userId, "accountType", accountType));
	}
}
