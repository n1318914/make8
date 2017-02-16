package com.yundaren.filter.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.yundaren.common.constants.APIConstants;

public class XSSFilter implements Filter {
	
	// 接口白名单列表
	static List<String> listAPI = new ArrayList<String>();
	static {
		listAPI.add(APIConstants.SEND_MAIL_BATCH);
		listAPI.add(APIConstants.PROJECT_MODIFY);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 只有POST提交请求才过滤
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		if (httpServletRequest.getMethod().equalsIgnoreCase("POST")) {
			//白名单过滤
			if(inWhiteList(httpServletRequest.getRequestURI())){
				chain.doFilter(request, response);
			}else{
				chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean inWhiteList(String requestUrl) {
		for (String api : listAPI) {
			if (requestUrl.contains(api)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
