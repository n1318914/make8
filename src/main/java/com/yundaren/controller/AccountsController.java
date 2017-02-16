package com.yundaren.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.RegexUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.user.service.SsoService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.NoticeStruct;

@Controller
public class AccountsController {

	@Setter
	private SsoService ssoService;

	@Setter
	private DomainConfig domainConfig;

	@Setter
	private UserService userService;

	/**
	 * 注册成功后提示页面
	 */
	@RequestMapping(value = PageForwardConstants.USERS_ACTIVE_NOTICE_PAGE, method = RequestMethod.GET)
	public String registerSuccessNotice(HttpServletRequest request) {
		String email = (String) request.getSession().getAttribute("ACTIVE_EMAIL");
		
		NoticeStruct struct = new NoticeStruct();
		struct.setTitle("码客帮");
		struct.setContent("<p><h2>账号已创建，请激活账号！</h2></p><p>激活邮件已发送至您的邮箱," + "请进入邮箱 <b>" + email
				+ "</b> 完成激活</p><p>请在24小时内点击邮件中的确认链接,"
				+ "激活您的账号</p><p><h3>没有收到<span>邮件</span>？</h3></p><p>可以到垃圾邮件目录中找找</p>"
				+ "<p>若长时间未收到，请点此&nbsp;&nbsp;<a href='" + PageForwardConstants.USERS_SEND_ACTIVE_MAIL
				+ "?email=" + email + "' target='_blank'>重新发送</a></p>");
		request.setAttribute("notice", struct);
		return "public/notice_no_jump";
	}
	
	/**
	 * 发送激活邮件
	 */
	@RequestMapping(value = PageForwardConstants.USERS_SEND_ACTIVE_MAIL, method = RequestMethod.GET)
	public String sendActiveMail(HttpServletRequest request, String email) {
		ssoService.sendActiveMail(email);

		NoticeStruct struct = new NoticeStruct();
		struct.setTitle("邮箱验证 - 码客帮 - 互联网软件外包交易平台");
		struct.setContent("<p>尊敬的用户，验证码已发送，请登录邮箱验证激活</p><p>此页面<span id='jumpTo'>10</span>秒后自动转至首页，<a href='"
				+ domainConfig.getHost() + "'>点击此处</a>直接跳转</p>");
		request.setAttribute("notice", struct);
		return "public/notice";
	}

	/**
	 * 找回密码跳转到找回页面
	 */
	@RequestMapping(value = PageForwardConstants.USERS_RESET, method = RequestMethod.GET)
	public String resetLink(HttpServletRequest request, String code, String type) throws IOException {
		request.setAttribute("vcode", code);
		// 账号类型(0邮箱1手机号)
		int operateType = 0;
		if (type.equalsIgnoreCase("mobile")) {
			operateType = 1;
		}
		request.setAttribute("type", operateType);
		// 跳转到找回密码页面
		return "public/reset_password";
	}
}
