package com.yundaren.api.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.cookie.CookieOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.bcel.internal.generic.DMUL;
import com.yundaren.cache.ActiveCodeCache;
import com.yundaren.cache.VerificationCodeCache;
import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.RegexUtil;
import com.yundaren.common.constants.UrlRewriteConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.DESUtil;
import com.yundaren.common.util.GitHttpClient;
import com.yundaren.common.util.GogsUtil;
import com.yundaren.common.util.ImageCodeUtil;
import com.yundaren.common.util.MD5Util;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.PublicConfig;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.service.YunConnectService;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.user.service.SsoService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.NoticeStruct;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
@Slf4j
public class AccountsRest {

	@Setter
	private SsoService ssoService;

	@Setter
	private DomainConfig domainConfig;

	@Setter
	private UserService userService;

	@Setter
	private PublicConfig publicConfig;

	@Setter
	private YunConnectService yunConnectService;

	@Setter
	private IdentifyService identifyService;

	/**
	 * 账号注册
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = APIConstants.USERS_REGISTER, method = RequestMethod.POST)
	public ResponseEntity<Map> registerUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute SsoUserVo ssoUserVo) throws IOException {
		Map<String, Object> result = checkRegister(request, ssoUserVo);
		// 如果检查通过
		if (null == result) {
			String loginName = ssoUserVo.getLoginName();
			String ip = CommonUtil.getRealIP(request);
			ssoUserVo.setIpAddress(ip);
			// 邮件注册
			if (RegexUtil.isEmail(loginName)) {
				long ssoId = ssoService.createSsoUser(ssoUserVo);
				ssoService.sendActiveMail(ssoUserVo.getLoginName());

				// 注册完成后跳转到注册成功页面
				request.getSession().setAttribute("ACTIVE_EMAIL", ssoUserVo.getLoginName());
				result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(), "/active/notice");
			} else {// 手机注册
				ssoUserVo.setIsActive(0);
				ssoUserVo.setAccountType(1);
				long ssoId = ssoService.createSsoUser(ssoUserVo);
				GitHttpClient gitHttpClient = GogsUtil.register(ssoUserVo.getLoginName(), ssoUserVo);
				if(gitHttpClient !=null){
					ssoUserVo.getUserInfoVo().setId(ssoUserVo.getUserId());	
					ssoUserVo.getUserInfoVo().setIsGogs(1);
					userService.updateUserInfo(ssoUserVo.getUserInfoVo());
					return login(request, response, ssoUserVo, false);
				}				
				else {
					result = ResponseMapUtil.getFailedResponseMap("注册异常");
				}
				/*result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(), "/public/login");
				request.getSession().setAttribute("loginName", ssoUserVo.getLoginName());*/
			}
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 账号激活
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = APIConstants.USERS_ACTIVE, method = RequestMethod.GET)
	public String active(HttpServletRequest request, HttpServletResponse response, String code, String email)
			throws IOException {
		NoticeStruct struct = ssoService.activeAccount(request, code, email);
		// 激活成功后跳转到登录页
		request.setAttribute("notice", struct);
		return "public/notice";
	}

	/**
	 * 检查用户是否存在
	 */
	@RequestMapping(value = APIConstants.USERS_CHECK_EXIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> checkExist(String userName) {
		Map<String, Object> result = new HashMap<String, Object>();
		SsoUserVo ssoUserVo = ssoService.getSsoUserByUserName(userName);
		if (ssoUserVo != null) {
			result = ResponseMapUtil.getFailedResponseMap("用户已注册");
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 获取图片验证码
	 */
	@RequestMapping(value = APIConstants.COMMON_CAPTCHA_IMG, method = RequestMethod.GET)
	@ResponseBody
	public void getCaptchaImage(HttpServletRequest request, HttpServletResponse response) {
		ImageCodeUtil.createRandomCode(request, response);
	}

	/**
	 * 用户登录
	 */
	@RequestMapping(value = APIConstants.USERS_LOGIN, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> login(HttpServletRequest request,
			HttpServletResponse response, SsoUserVo ssoUserVo, boolean isAutoLogin) {
		Map<String, Object> result = new HashMap<String, Object>();
		SsoUserVo ssoVo = CommonUtil.getCurrentLoginUser();
		
		// 如果用户已经在线，再次登录就跳转到首页
		if (ssoVo != null) {
			result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(), "/");
			return new ResponseEntity<Map>(result, HttpStatus.OK);
		}
		
		result = checkLogin(ssoUserVo, request, isAutoLogin);
		// 如果检查通过
		if (result == null) {
			// 重置登录失败的次数为0
			request.getSession().setAttribute(CommonConstants.USER_LOGIN_FAILED_TIMES, 0);

			// 更新最后登录时间和IP
			ssoUserVo.setLastLoginTime(new Date());
			if(!isAutoLogin){
				ssoUserVo.setPassword(MD5Util.encodeByMD5(ssoUserVo.getPassword()));
			}else{
				ssoUserVo.setPassword(ssoUserVo.getPassword());
			}
			String ip = CommonUtil.getRealIP(request);
			ssoUserVo.setIpAddress(ip);
			ssoService.updateSsoUser(ssoUserVo);
			// 用户信息存session
			UserInfoVo userInfoVo = userService.getUserInfoByID(ssoUserVo.getUserId());
			if (userInfoVo.getUserType() == 1) {
				IdentifyVo identifyVo = identifyService.getIdentifyByUID(userInfoVo.getId());
				userInfoVo.setIdentifyInfo(identifyVo);
			}
			// 名称缩略显示
			setLetterName(ssoUserVo, userInfoVo);
			ssoUserVo.setUserInfoVo(userInfoVo);
			request.getSession().setAttribute(CommonConstants.SESSION_LOGIN_USER, ssoUserVo);
			// 登录成功后跳回
			if(request.getSession().getAttribute(CommonConstants.PROJECT_IN_SELF_CREATE_FLAG) != null){
				String projectId = (String)request.getSession().getAttribute(CommonConstants.PROJECT_IN_SELF_CREATE_ID);
				
				result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),UrlRewriteConstants.REWRITE_PROJECT_IN_SELF_RUN_DETAILS + projectId);
				
				request.getSession().removeAttribute(CommonConstants.PROJECT_IN_SELF_CREATE_FLAG);
				request.getSession().removeAttribute(CommonConstants.PROJECT_IN_SELF_CREATE_ID);
				request.getSession().removeAttribute(CommonConstants.PROJECT_IN_SELF_CREATE_FAILED_MSG);
				request.getSession().removeAttribute(CommonConstants.PROJECT_IN_SELF_CREATE_LOGIN_NAME);
			}else{
				String refererUrl = String.valueOf(request.getSession().getAttribute(CommonConstants.REFERER_URL));
				
				if (refererUrl.equalsIgnoreCase("null")) {
					refererUrl = "/";
				}
				result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(), refererUrl);
			}
			
			//登陆后设置COOKIE
			Cookie name = new Cookie("username",ssoUserVo.getLoginName());
			name.setMaxAge(7*24*60*60);
			name.setPath("/");
			Cookie pwd = new Cookie("password",ssoUserVo.getPassword());
			pwd.setMaxAge(7*24*60*60);
			pwd.setPath("/");
			Cookie displayName;
			try {
				displayName = new Cookie("displayName",URLEncoder.encode(userInfoVo.getDisplayName(), "utf-8"));
				displayName.setMaxAge(7*24*60*60);
				displayName.setPath("/");
				response.addCookie(name);
				response.addCookie(pwd);
				response.addCookie(displayName);
			} catch (UnsupportedEncodingException e) {
			}
			
		} else {

			int loginFailedTimeExceeded = 0;

			if (isLoginFailedTimeExceeded(request)) {
				loginFailedTimeExceeded = 1;
			}

			result.put("loginFailedTimeExceeded", loginFailedTimeExceeded);
		}

		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/* 设置名称缩略显示 */
	private void setLetterName(SsoUserVo ssoUser, UserInfoVo userInfo) {
		// 名称显示缩略
		String name = "";
		String displayName = "";
		if (StringUtils.isEmpty(userInfo.getName())) {
			name = ssoUser.getLoginName();
			displayName = CommonUtil.getLittleStr(10, name);
		} else {
			name = userInfo.getName();
			displayName = CommonUtil.getLittleStr(6, name);
		}
		userInfo.setDisplayName(displayName);
	}

	/**
	 * 找回密码邮件发送
	 */
	@RequestMapping(value = APIConstants.USERS_SEND_RESET, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> sendResetPassword(HttpServletRequest request, String loginName, String vcode)
			throws IOException {
		log.info("find password notice, loginName=" + loginName);
		Map<String, Object> result = checkSendResetMail(request, loginName, vcode);
		if (result == null) {
			if (RegexUtil.isEmail(loginName)) {// 邮件找回密码
				ssoService.sendResetPassword(loginName);
				// 找回密码，跳转到提示登录邮箱找回页面
				result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),
						PageForwardConstants.RESET_NOTICE_PAGE);
			} else {
				// 发送短信验证码
				String retMsg = yunConnectService.sendTemplateSMS(loginName);
				// 发送失败返回失败信息
				if (!retMsg.isEmpty()) {
					result = ResponseMapUtil.getFailedResponseMap(retMsg);
				} else {
					String code = DESUtil.encrypt(loginName);
					result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),
							PageForwardConstants.USERS_RESET + "?code=" + code + "&type=mobile");
				}
			}
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 找回密码
	 */
	@RequestMapping(value = APIConstants.USERS_RESET_CHANGE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> resetPassword(HttpServletRequest request, String newPassword,
			String confirmPassword, String code, String captcha, int type) throws IOException {
		// TODO 限制访问频率

		//if (type == 1) {
			code = DESUtil.decrypt(code);
		//}
		log.info("find reset password code=" + code + ",captcha=" + captcha + ",type=" + type);
		Map<String, Object> result = checkResetPwd(request, newPassword, confirmPassword, code, captcha);
		boolean resetResult = false;
		
		if (result == null) {
			
			//同步gogs平台帐号密
			SsoUserVo ssoUser = ssoService.getSsoUserByUserName(code);
			UserInfoVo userInfoVo = userService.getUserInfoByID(ssoUser.getUserId());
			String mobile = userInfoVo.getMobile();
			boolean resetSuccess = GogsUtil.resetPassword(mobile, MD5Util.encodeByMD5(newPassword));
			if(resetSuccess){
				// 修改密码
				resetResult = ssoService.resetPassword(code, newPassword);
			}
			
			if(resetResult){
				request.getSession().setAttribute("resetStatus", 1);
			}else{
				request.getSession().setAttribute("resetStatus", -1);
			}
			
			result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),PageForwardConstants.RESET_OK_PAGE);			
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 注销
	 */
	@RequestMapping(value = APIConstants.USERS_LOGOUT, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(), "/");
		request.getSession().removeAttribute(CommonConstants.REFERER_URL);
		request.getSession().removeAttribute(CommonConstants.SESSION_LOGIN_USER);
		request.getSession().invalidate();
		
		//退出登录时，清除cookie
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			String cookieName = cookie.getName();
			if("username".equals(cookieName) || "password".equals(cookieName) || "displayName".equals(cookieName)){
				cookie.setValue("");
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 用户是否登录
	 */
	@RequestMapping(value = APIConstants.USERS_IS_LOGIN, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> isLogin() {
		Map<String, Object> result = new HashMap<String, Object>();
		SsoUserVo ssoUser = CommonUtil.getCurrentLoginUser();
		if (ssoUser == null) {
			result = ResponseMapUtil.getFailedResponseMap("未登录");
		} else {
			result = ResponseMapUtil.getSuccessResponseMap("已登录");
			// 是否管理员
			result.put("admin", ssoUser.getUserInfoVo().getUserType() == -1 ? 0 : 1);
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 自动登录
	 */
	@RequestMapping(value = APIConstants.USER_AUTO_LOGIN, method = RequestMethod.POST)
	public ResponseEntity autoLogin(HttpServletRequest request,
			HttpServletResponse response) {
		SsoUserVo ssoVo = CommonUtil.getCurrentLoginUser();

		if (ssoVo != null) {
			return new ResponseEntity(HttpStatus.OK);
		}

		String userName = "";
		String pwd = "";
		
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if("username".equals(cookie.getName())){
				userName = cookie.getValue();
			}
			if("password".equals(cookie.getName())){
				pwd = cookie.getValue();
			}
		}
		SsoUserVo ssoUserVo = new SsoUserVo();
		ssoUserVo.setLoginName(userName);
		ssoUserVo.setPassword(pwd);
		if(userName.isEmpty() || pwd.isEmpty()){
			return new ResponseEntity(HttpStatus.OK);
		}
		return login(request, response, ssoUserVo, true);
	}
	/**
	 * 修改密码
	 */
	@RequestMapping(value = APIConstants.USERS_CHANGE_PASSWORD, method = RequestMethod.POST)
	public ResponseEntity modifyPassword(HttpServletRequest request, HttpServletResponse response,
			String oldPassword, String newPassword, String confirmPassword) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean isOK = true;
		SsoUserVo ssoUser = CommonUtil.getCurrentLoginUser();

		// 参数校验
		if (StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword)
				|| StringUtils.isEmpty(oldPassword)) {
			result = ResponseMapUtil.getFailedResponseMap("必填项不能为空");
			isOK = false;
		}
		if (!newPassword.equals(confirmPassword)) {
			result = ResponseMapUtil.getFailedResponseMap("输入的密码不一致");
			isOK = false;
		}

		String encryptPwd = MD5Util.encodeByMD5(oldPassword);
		String oldPwd = ssoUser.getPassword();
		if (StringUtils.isEmpty(oldPwd) || !ssoUser.getPassword().equals(encryptPwd)) {
			result = ResponseMapUtil.getFailedResponseMap("老密码错误");
			isOK = false;
		}

		if (isOK) {
			String newPwd = MD5Util.encodeByMD5(newPassword);
			//同步gogs平台帐号密码
			UserInfoVo userInfoVo = userService.getUserInfoByID(ssoUser.getUserId());
			String mobile = userInfoVo.getMobile();
			boolean resetSuccess = GogsUtil.resetPassword(mobile, newPwd);
			if(resetSuccess){
				ssoService.updatePasswordByID(ssoUser.getUserId(), newPwd);
				result = ResponseMapUtil.getSuccessResponseMap("success");	
			}else{
				result = ResponseMapUtil.getFailedResponseMap("系统异常");
			}
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	// 注册校验
	private Map checkRegister(HttpServletRequest request, SsoUserVo ssoUserVo) {
		String loginName = ssoUserVo.getLoginName();
		String password = ssoUserVo.getPassword();
		String vCode = ssoUserVo.getVcode();

		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
			return ResponseMapUtil.getFailedResponseMap("必填项不能为空");
		}

		if (password.length() < 6 || ssoUserVo.getUserType() == -1) {
			return ResponseMapUtil.getFailedResponseMap("参数格式不正确");
		}

		// 邮件注册
		if (RegexUtil.isEmail(loginName)) {
			// 校验验证码
			boolean iRet = ImageCodeUtil.checkRandomCode(request, vCode);
			if (!iRet) {
				return ResponseMapUtil.getFailedResponseMap("验证码错误");
			}
		} else if (RegexUtil.isMobile(loginName)) {// 手机注册
			// 校验验证码
			String cacheCode = String.valueOf(VerificationCodeCache.getInstance().get(loginName));
			if (StringUtils.isEmpty(vCode) || !cacheCode.equals(vCode)) {
				return ResponseMapUtil.getFailedResponseMap("短信验证码错误");
			}
		} else {
			return ResponseMapUtil.getFailedResponseMap("参数格式不正确");
		}
		// 检查邀请码,完成使命。
		/*String invitationCode = ssoUserVo.getVcode();
		InvitationVo invitationVo = ssoService.getInvitationVo(invitationCode);
		if (invitationVo == null) {
			return ResponseMapUtil.getFailedResponseMap("邀请码错误，如有需要请联系客服获取");
		}
		if (invitationVo.getIsUsed() == 1) {
			return ResponseMapUtil.getFailedResponseMap("邀请码已失效，如有需要请联系客服重新获取");
		}*/

		// 返回空表示校验通过
		return null;
	}

	// 发送重置密码邮件校验
	private Map checkSendResetMail(HttpServletRequest request, String loginName, String vcode) {
		// 用户名密码格式校验
		SsoUserVo ssoUserVo = ssoService.getSsoUserByUserName(loginName);
		if (ssoUserVo == null) {
			return ResponseMapUtil.getFailedResponseMap("不存在此用户");
		}
		// 校验验证码
		boolean iRet = ImageCodeUtil.checkRandomCode(request, vcode);
		if (!iRet) {
			return ResponseMapUtil.getFailedResponseMap("验证码错误");
		}
		// 返回空表示校验通过
		return null;
	}

	// 重置密码校验
	private Map checkResetPwd(HttpServletRequest request, String newPassword, String confirmPassword,
			String vcode, String captcha) {

		if (RegexUtil.isMobile(vcode)) {
			if (VerificationCodeCache.getInstance().get(vcode) == null) {
				return ResponseMapUtil.getFailedResponseMap("短信验证码已失效，请重新找回密码");
			}
			String cacheCode = String.valueOf(VerificationCodeCache.getInstance().get(vcode));
			if (!captcha.equals(cacheCode)) {
				return ResponseMapUtil.getFailedResponseMap("短信验证错误");
			}

		} else {
			// 校验验证码
			// 邮件找回无需输入验证码
			/*boolean iRet = ImageCodeUtil.checkRandomCode(request, captcha);
			if (!iRet) {
				return ResponseMapUtil.getFailedResponseMap("验证码错误");
			}*/
			if (ActiveCodeCache.getInstance().get(vcode) == null) {
				return ResponseMapUtil.getFailedResponseMap("链接已失效，请重新找回密码");
			}
		}

		if (!newPassword.equals(confirmPassword)) {
			return ResponseMapUtil.getFailedResponseMap("两次输入的密码不一致");
		}
		// 返回空表示校验通过
		return null;
	}



	private boolean isLoginFailedTimeExceeded(HttpServletRequest request) {
		int loginFailedTimes = 0;

		if (request.getSession().getAttribute(CommonConstants.USER_LOGIN_FAILED_TIMES) != null) {
			loginFailedTimes = (int) request.getSession().getAttribute(
					CommonConstants.USER_LOGIN_FAILED_TIMES);
		}

		if (loginFailedTimes > publicConfig.getUserLoginLimitedTimes()) {
			return true;
		} else {
			return false;
		}
	}
	

	// 登录校验
	private Map checkLogin(SsoUserVo ssoUser, HttpServletRequest request, boolean isAutoLogin) {
		String loginName = ssoUser.getLoginName();
		String password = ssoUser.getPassword();

		// 参数检查
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
			return ResponseMapUtil.getFailedResponseMap("必填项不能为空");
		}

		// 如果用户登录超过规定测试，需要验证码
		if (isLoginFailedTimeExceeded(request)) {
			// 校验验证码
			boolean iRet = ImageCodeUtil.checkRandomCode(request, ssoUser.getVcode());

			if (!iRet) {
				return ResponseMapUtil.getFailedResponseMap("验证码错误");
			}
		}

		// 登录验证
		SsoUserVo ssoUserVo = ssoService.getSsoUserByUserName(loginName);
		if(!isAutoLogin){
			password = MD5Util.encodeByMD5(password);
		}
		if (ssoUserVo == null || !password.equals(ssoUserVo.getPassword())) {
			//
			int loginFailedTimes = 0;

			if (request.getSession().getAttribute(CommonConstants.USER_LOGIN_FAILED_TIMES) != null) {
				loginFailedTimes = (int) request.getSession().getAttribute(
						CommonConstants.USER_LOGIN_FAILED_TIMES);
			}

			loginFailedTimes++;
			request.getSession().setAttribute(CommonConstants.USER_LOGIN_FAILED_TIMES, loginFailedTimes);

			return ResponseMapUtil.getFailedResponseMap("用户名或密码错误");
		}

		if (ssoUserVo.getIsActive() != 0) {
			return ResponseMapUtil.getFailedResponseMap("*用户未激活，请登录注册邮箱激活后登录");
		}

		// 返回空表示校验通过
		ssoUser.setId(ssoUserVo.getId());
		ssoUser.setUserId(ssoUserVo.getUserId());
		return null;
	}
}
