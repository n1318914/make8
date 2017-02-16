package com.yundaren.security.dao;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.yundaren.user.po.OauthAccessTokenPo;

public class OauthDao extends SqlSessionDaoSupport {
	@Cacheable(value = "oauth.access.token.loginName", key = "'oauth.access.token..loginName.'+#accessToken")
	public OauthAccessTokenPo selectOauthAccessTokenById(String accessToken) {
		return getSqlSession().selectOne("selectOauthAccessTokenById", accessToken);
	}

	public OauthAccessTokenPo selectOauthAccessTokenByUserName(String userName) {
		return getSqlSession().selectOne("selectOauthAccessTokenByUserName", userName);
	}

	@Caching(evict = {
			@CacheEvict(value = "oauth.access.token.loginName", key = "'oauth.access.token..loginName.'+#accessToken", beforeInvocation = true),
			@CacheEvict(value = "oauth.access.token", key = "'oauth.access.token.'+#accessToken", beforeInvocation = true),
			@CacheEvict(value = "user.sso.cache", allEntries = true, beforeInvocation = true),
			@CacheEvict(value = "oauth.authentication", key = "'oauth.authentication.'+#accessToken", beforeInvocation = true)})
	public int deleteOauthAccessByToken(String accessToken) {
		OauthAccessTokenPo accessTokenPo = getSqlSession().selectOne("selectOauthAccessTokenById",
				accessToken);
		if (null != accessTokenPo) {
			String refreshToken = accessTokenPo.getRefreshToken();
			if (StringUtils.isNotBlank(refreshToken)) {
				getSqlSession().delete("deleteRefreshOauthByToken", refreshToken);
			}
		}
		return getSqlSession().delete("deleteOauthAccessByToken", accessToken);
	}
}
