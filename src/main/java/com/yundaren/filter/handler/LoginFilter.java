package com.yundaren.filter.handler;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.user.vo.SsoUserVo;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String refererHeader = request.getHeader("referer");
		String url = request.getRequestURI();
		// 登录后才允许访问的资源
		if ((url.contains("/api/1/") || url.contains("/home"))
				&& !(url.equals("/home/request") || url.contains("/css/") || url.contains("/js/"))) {
			String requestUrl = StringUtils.isEmpty(request.getQueryString()) ? url : url + "?"
					+ request.getQueryString();

			// 超时后跳到API JSON数据页面问题处理 BUGID:075
			if (url.contains("/api/1/") && refererHeader != null
					&& refererHeader.contains(DomainConfig.getHost())) {
				requestUrl = refererHeader.replace(DomainConfig.getHost(),"");
			}
			request.getSession().setAttribute(CommonConstants.REFERER_URL, requestUrl);

			SsoUserVo ssoUserVo = (SsoUserVo) request.getSession().getAttribute(
					CommonConstants.SESSION_LOGIN_USER);
			if (ssoUserVo == null) {
				response.sendRedirect(PageForwardConstants.LOGIN_PAGE);
				return;
			}
		}
		// 管理员才允许访问的资源
		else if (url.contains("/api/2/") || (url.contains("/admin"))) {
			String requestUrl = StringUtils.isEmpty(request.getQueryString()) ? url : url + "?"
					+ request.getQueryString();

//			// 超时后跳到API JSON数据页面问题处理 BUGID:075
//			if (url.contains("/api/2/") && refererHeader != null
//					&& refererHeader.contains(DomainConfig.getHost())) {
//				requestUrl = refererHeader.replace(DomainConfig.getHost(),"");
//			}
			request.getSession().setAttribute(CommonConstants.REFERER_URL, requestUrl);

			SsoUserVo ssoUserVo = (SsoUserVo) request.getSession().getAttribute(
					CommonConstants.SESSION_LOGIN_USER);
			if (ssoUserVo == null) {
				response.sendRedirect(PageForwardConstants.LOGIN_PAGE);
				return;
				// 管理员账号
			} else if (!(ssoUserVo.getUserInfoVo().getUserType() == -1 || ssoUserVo.getUserInfoVo()
					.getUserType() == 2)) {
				response.sendRedirect("/");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

//	private boolean isInterceptor(String requestUrl) {
//		if (null == noInterceptorRqes || null == requestUrl) {
//			return true;
//		}
//		int size = noInterceptorRqes.length;
//		for (int i = 0; i < size; i++) {
//			if (requestUrl.toLowerCase().contains(noInterceptorRqes[i])) {
//				return false;
//			}
//		}
//		return true;
//	}

}
