package com.yundaren.security.biz;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yundaren.common.util.MD5Util;
import com.yundaren.security.dao.OauthDao;
import com.yundaren.user.biz.SsoBiz;
import com.yundaren.user.po.OauthAccessTokenPo;
import com.yundaren.user.vo.SsoUserVo;

/**
 * 查询oauth信息
 */
@Slf4j
public class OauthBiz implements UserDetailsService {

	@Setter
	private SsoBiz ssoBiz;
	@Setter
	private OauthDao oauthDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SsoUserVo ssoUserVo = ssoBiz.getSsoUserByUserName(username);
		if (StringUtils.isBlank(ssoUserVo.getLoginName())) {
			log.error("Login failed, not the current user in the database, user name = " + username);
			throw new UsernameNotFoundException(
					"Login failed, not the current user in the database, user name = " + username);
		}
		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new User(ssoUserVo.getLoginName(), ssoUserVo.getPassword(), roles);
	}

	public OauthAccessTokenPo getOauthAccessMsgByToken(String accessToken) {
		if (StringUtils.isBlank(accessToken)) {
			throw new RuntimeException("accessToken is null");
		}
		OauthAccessTokenPo OauthAccessTokenPo = oauthDao.selectOauthAccessTokenById(MD5Util
				.encodeByMD5(accessToken));
		if (null == OauthAccessTokenPo) {
			throw new RuntimeException("accessToken error");
		}
		return OauthAccessTokenPo;
	}

	public int deleteOauthAccessByToken(String accessToken) {
		if (StringUtils.isBlank(accessToken)) {
			throw new RuntimeException("accessToken is null");
		}
		if (1 != oauthDao.deleteOauthAccessByToken(MD5Util.encodeByMD5(accessToken))) {
			throw new RuntimeException("accessToken error");
		}
		return 0;
	}
}