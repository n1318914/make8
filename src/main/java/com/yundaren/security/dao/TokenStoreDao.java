package com.yundaren.security.dao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Collection;

@Slf4j
public class TokenStoreDao implements TokenStore {
	@Setter
	private TokenStore tokenStore;

	@Cacheable(value = "oauth.access.token", key = "'oauth.access.token.'+#tokenValue")
	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		return tokenStore.readAccessToken(tokenValue);
	}

	@Cacheable(value = "oauth.authentication", key = "'oauth.authentication.'+#token")
	@Override
	public OAuth2Authentication readAuthentication(String token) {
		return tokenStore.readAuthentication(token);
	}

	@Caching(evict = {@CacheEvict(value = "oauth.access.token", allEntries = true),
			@CacheEvict(value = "oauth.authentication", allEntries = true)})
	public OAuth2RefreshToken readRefreshToken(String token) {
		return tokenStore.readRefreshToken(token);
	}

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return tokenStore.readAuthentication(token);
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		try {
			tokenStore.storeAccessToken(token, authentication);
		} catch (Exception e) {
			log.error("Repeat operation.again,please.", e);
			throw new RuntimeException();
		}
	}

	@Caching(evict = {@CacheEvict(value = "oauth.access.token", key = "'oauth.access.token.'+#token.value"),
			@CacheEvict(value = "oauth.authentication", key = "'oauth.authentication.'+#token.value")})
	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		tokenStore.removeAccessToken(token);
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		tokenStore.storeRefreshToken(refreshToken, authentication);
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return tokenStore.readAuthenticationForRefreshToken(token);
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		tokenStore.removeRefreshToken(token);
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		tokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		return tokenStore.getAccessToken(authentication);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		return tokenStore.findTokensByClientIdAndUserName(clientId, userName);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		return tokenStore.findTokensByClientId(clientId);
	}
}
