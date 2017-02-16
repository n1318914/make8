package com.yundaren.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.PublicConfig;
import com.yundaren.support.service.ProjectService;
import com.yundaren.user.service.UserService;

public class MobileController {

	@Setter
	private DomainConfig domainConfig;

	@Setter
	private ProjectService projectService;

	@Setter
	private PublicConfig publicConfig;

	@Setter
	private UserService userService;

	/**
	 * 移动端首页
	 */
	@RequestMapping(value = PageForwardConstants.MOBILE_INDEX_PAGE, method = RequestMethod.GET)
	public String index_ftl(HttpServletRequest request) throws IOException {
		Map<String, Object> result = projectService.getTotalCount();
		for (Map.Entry<String, Object> struct : result.entrySet()) {
			request.setAttribute(struct.getKey(), struct.getValue());
		}
		return "mobile/index";
	}

	/**
	 * 项目市场
	 */
	@RequestMapping(value = PageForwardConstants.MOBILE_PROJECT_MARKET, method = RequestMethod.GET)
	public String market_ftl(HttpServletRequest request) throws IOException {
		return "mobile/market";
	}

	/**
	 * 需求发布页面
	 */
	@RequestMapping(value = PageForwardConstants.MOBILE_ADD_REQUEST, method = RequestMethod.GET)
	public String request_ftl(HttpServletRequest request) throws IOException {
		return "mobile/request";
	}

	/**
	 * 预约顾问
	 */
	@RequestMapping(value = PageForwardConstants.MOBILE_RESERVE, method = RequestMethod.GET)
	public String reserve_ftl(HttpServletRequest request) throws IOException {
		return "mobile/reserve";
	}

	/**
	 * 如何使用
	 */
	@RequestMapping(value = PageForwardConstants.MOBILE_HOW_2_USE, method = RequestMethod.GET)
	public String how_to_use_ftl(HttpServletRequest request) throws IOException {
		return "mobile/how_to_use";
	}

	/**
	 * 发布/预约-跳转提示信息页面
	 */
	@RequestMapping(value = PageForwardConstants.MOBILE_NOTICE, method = RequestMethod.GET)
	public String notice_ftl(HttpServletRequest request, String operate) throws IOException {
		String title = "";
		String content = "";
		// 如果是需求发布
		if (operate.equals("request")) {
			title = "需求发布";
			content = "需求提交成功，我们审核后会尽快与您联系。";
		} else {
			title = "预约顾问";
			content = "预约顾问提交成功，码客帮专业顾问会尽快与您联系。";
		}
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		return "mobile/notice";
	}
}
