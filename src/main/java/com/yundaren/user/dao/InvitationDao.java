package com.yundaren.user.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.google.common.collect.ImmutableMap;
import com.yundaren.user.po.InvitationPo;

public class InvitationDao extends SqlSessionDaoSupport {

	public InvitationPo getInvitationCode(String invitationCode) {
		return getSqlSession().selectOne("getInvitationCode", invitationCode);
	}

	public int updateInvitation2Used(String invitationCode,long ssoId) {
		return getSqlSession().update("updateInvitation2Used",
				ImmutableMap.of("invitationCode", invitationCode, "ssoId", ssoId));
	}
}
