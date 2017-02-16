package com.yundaren.user.service.impl;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.yundaren.cache.ActiveCodeCache;
import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.RegexUtil;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.DESUtil;
import com.yundaren.common.util.EmailSender;
import com.yundaren.common.util.EmailTemplateUtil;
import com.yundaren.common.util.ImageCodeUtil;
import com.yundaren.common.util.MD5Util;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.EmailSendConfig;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.user.biz.InvitationBiz;
import com.yundaren.user.biz.SsoBiz;
import com.yundaren.user.service.SsoService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.InvitationVo;
import com.yundaren.user.vo.NoticeStruct;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

import freemarker.template.Template;

@Slf4j
public class SsoServiceImpl implements SsoService {
	@Setter
	private SsoBiz ssoBiz;

	@Setter
	private UserService userService;

	@Setter
	private DomainConfig domainConfig;

	@Setter
	private EmailSendConfig emailSendConfig;

	@Setter
	private InvitationBiz invitationBiz;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public long createSsoUser(SsoUserVo ssoUserVo) {
		log.info("register new user " + ssoUserVo.getLoginName());

		// 生成用户信息
		UserInfoVo userInfoVo = new UserInfoVo();
		if (RegexUtil.isEmail(ssoUserVo.getLoginName())) {
			userInfoVo.setEmail(ssoUserVo.getLoginName());
		} else {
			userInfoVo.setMobile(ssoUserVo.getLoginName());
		}
		// 设置文件访问密匙
		String fileSecretKey = CommonUtil.getRandomStr(4);
		userInfoVo.setFileSecretKey(fileSecretKey);
		userInfoVo.setUserType(ssoUserVo.getUserType());
		userInfoVo.setResumeType("input");
		long userId = userService.createUserInfo(userInfoVo);

		// 生成账户信息
		ssoUserVo.setUserId(userId);
		long ssoUserId = ssoBiz.createSsoUser(ssoUserVo, true);
		ssoUserVo.setUserInfoVo(userInfoVo);
		// 更新邀请码状态-邀请码已关闭
		// invitationBiz.updateInvitation2Used(ssoUserVo.getVcode(), ssoUserId);
		return ssoUserId;
	}

	@Override
	public SsoUserVo getSsoUserByUserName(String userName) {
		return ssoBiz.getSsoUserByUserName(userName);
	}

	@Override
	public SsoUserVo getSsoUserById(long id) {
		return ssoBiz.getSsoUserById(id);
	}

	@Override
	public void sendActiveMail(String email) {
		// 发送邮件认证
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(email);
		String mailContent = getActiveMailContent(email);
		EmailSender.send(mailConfig, "[码客帮]邮箱验证", mailContent);
	}

	@Override
	public NoticeStruct activeAccount(HttpServletRequest request, String activeCode, String email) {
		NoticeStruct resultStruct = new NoticeStruct();
		String title = "";
		String content = "";
		Object codeCache = ActiveCodeCache.getInstance().get(activeCode);
		log.info("active account email is " + email + " codeCache is " + codeCache);
		if (codeCache != null) {
			// 更新激活状态
			String loginName = String.valueOf(codeCache);
			ssoBiz.update2ActiveStatus(loginName);

			// 直接登录
			SsoUserVo ssoUserVo = ssoBiz.getSsoUserByUserName(loginName);
			ssoUserVo.setLastLoginTime(new Date());
			ssoUserVo.setPassword(null);
			ssoBiz.updateSsoUser(ssoUserVo);
			UserInfoVo userInfoVo = userService.getUserInfoByID(ssoUserVo.getUserId());
			// 名称缩略显示
			setLetterName(ssoUserVo, userInfoVo);
			ssoUserVo.setUserInfoVo(userInfoVo);
			request.getSession().setAttribute(CommonConstants.SESSION_LOGIN_USER, ssoUserVo);

			title = "激活成功 - 码客帮 - 互联网软件外包交易平台";
			String autoLoginJs = "<script>setLoginStatus('1'); setLoginUserName('" + loginName
					+ "');setLoginUserType(" + userInfoVo.getUserType() + ");</script>";
			content = autoLoginJs
					+ "<p>尊敬的用户，您已经激活成功，欢迎成为码客帮注册会员</p><p>此页面<span id='jumpTo'>10</span>秒后自动转至首页，<a href='"
					+ domainConfig.getHost() + "'>点击此处</a>直接跳转</p>";
		} else {
			title = "验证链接已失效 - 码客帮 - 互联网软件外包交易平台";
			content = "<p>尊敬的用户，激活链接已经失效，<a href='" + PageForwardConstants.USERS_SEND_ACTIVE_MAIL + "?email="
					+ email + "'>点此重新发送激活链接</a></p><p>此页面<span id='jumpTo'>10</span>秒后自动转至登录页，<a href='"
					+ domainConfig.getHost() + PageForwardConstants.LOGIN_PAGE + "'>点击此处</a>直接跳转</p>";
		}
		resultStruct.setTitle(title);
		resultStruct.setContent(content);
		return resultStruct;
	}

	/* 设置名称缩略显示 */
	private void setLetterName(SsoUserVo ssoUser, UserInfoVo userInfo) {
		// 名称显示缩略
		String name = "";
		String displayName = "";
		if (StringUtils.isEmpty(userInfo.getName())) {
			name = ssoUser.getLoginName();
			displayName = CommonUtil.getLittleStr(12, name);
		} else {
			name = userInfo.getName();
			displayName = CommonUtil.getLittleStr(8, name);
		}
		userInfo.setDisplayName(displayName);
	}

	@Override
	public void sendResetPassword(String loginName) {
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(loginName);
		String mailContent = getResetPwdMailContent(loginName);
		EmailSender.send(mailConfig, "[码客帮]找回密码", mailContent);
	}

	/* (non-Javadoc)
	 * @see com.yundaren.user.service.SsoService#resetPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean resetPassword(String vCode, String newPassword) {
		String loginName = "";
		//if (RegexUtil.isMobile(vCode)) {
			loginName = vCode;
		//} 
//     邮箱无需重新获取loginName，传进来的vCode已经做了转移
//		else {
//			loginName = String.valueOf(ActiveCodeCache.getInstance().get(vCode));
//		}
		
		String newPwd = MD5Util.encodeByMD5(newPassword);
		// 更新密码
		int effectedRows = ssoBiz.updateSsoUserPassword(loginName, newPwd);
		// 清除验证缓存
		ActiveCodeCache.getInstance().remove(vCode);
		
		if(effectedRows > 0){
			log.info("reset password success ,loginName=" + loginName);
			return true;
		}else{
			log.info("reset password failed ,loginName=" + loginName);
			return false;
		}
		
	}

	@Override
	public int updateSsoUser(SsoUserVo ssoUserVo) {
		return ssoBiz.updateSsoUser(ssoUserVo);
	}

	@Override
	public void updateSsoRelation(String loginName, long userId, int accountType) {
		log.info("updateSsoRelation loginName=" + loginName + ",userId=" + userId + ",accountType="
				+ accountType);

		SsoUserVo ssoUser = ssoBiz.getSsoUserByAccountType(userId, accountType);
		if (ssoUser == null) {
			if (accountType == 0) {
				// 关联邮箱，不存在则从手机账号拷贝
				ssoUser = ssoBiz.getSsoUserByAccountType(userId, 1);
				ssoUser.setAccountType(0);
			} else {
				// 关联手机号，不存在则从邮箱账号拷贝
				ssoUser = ssoBiz.getSsoUserByAccountType(userId, 0);
				ssoUser.setAccountType(1);
			}
			ssoUser.setLoginName(loginName);
			ssoBiz.createSsoUser(ssoUser, false);
		} else {
			// 存在则更新登录名
			ssoBiz.updateLoginName(ssoUser.getLoginName(), loginName);
		}
	}

	@Override
	public InvitationVo getInvitationVo(String code) {
		return invitationBiz.getInvitationCode(code);
	}

	@Override
	public int updateSsoUserPassword(String loginName, String newPassword) {
		return ssoBiz.updateSsoUserPassword(loginName, newPassword);
	}
	
	@Override
	public int updatePasswordByID(long uid, String newPassword) {
		return ssoBiz.updatePasswordByID(uid, newPassword);
	}

	@Override
	public void updateInvitation2Used(String invitationCode, long ssoId) {
		invitationBiz.updateInvitation2Used(invitationCode, ssoId);
	}
	
    @Override
    public boolean login(SsoUserVo ssoUserVo){
    	ssoUserVo.setLastLoginTime(new Date());
		ssoUserVo.setPassword(null);
	
    	int affectedRows = updateSsoUser(ssoUserVo);
    	
    	boolean result = false;
    	
    	if(affectedRows == 1){
    		result = true;
    	}
    	
    	if(result == true){
    		UserInfoVo userInfoVo = userService.getUserInfoByID(ssoUserVo.getUserId());
    		// 名称缩略显示
    		setLetterName(ssoUserVo, userInfoVo);
    		ssoUserVo.setUserInfoVo(userInfoVo);
    	}
    	
    	return result;
    }

	private String getActiveMailContent(String loginName) {
		// 生成激活码
		String activeCode = loginName + System.currentTimeMillis();
		activeCode = MD5Util.encodeByMD5(activeCode);
		ActiveCodeCache.getInstance().add(activeCode, loginName);
		String activeUrl = domainConfig.getHost() + APIConstants.USERS_ACTIVE + "?code=" + activeCode
				+ "&email=" + loginName;
//		StringBuffer sb = new StringBuffer();
//		sb.append("<p>尊敬的会员，欢迎来到码客帮！</p>");
//		sb.append("<p>请点击下面的链接完成邮箱验证:</p>");
//		sb.append("<p></p>");
//		sb.append("<p><a href='" + activeUrl + "' target='_blank'>" + activeUrl + "</a></p>");
//		sb.append("<p>链接将在24小时后失效，如果您无法点击以上链接，请复网址到浏览器里直接打开。</p>");

		// 读取邮件模板
		Template emailTemplate = EmailTemplateUtil.getTemplate("notice_new_user_active.ftl");
		Map map = new HashMap();
		map.put("activeUrl", activeUrl);
		String content = getTemplateStr(map, emailTemplate);
		return content;
	}

	private String getResetPwdMailContent(String loginName) {
		// 生成激活码
		//String activeCode = loginName + System.currentTimeMillis();
		//activeCode = MD5Util.encodeByMD5(activeCode);
		String activeCode = DESUtil.encrypt(loginName);
		//ActiveCodeCache.getInstance().add(activeCode, loginName);
		ActiveCodeCache.getInstance().add(loginName,activeCode);

		String activeUrl = domainConfig.getHost() + PageForwardConstants.USERS_RESET + "?code=" + activeCode
				+ "&type=email";
//		StringBuffer sb = new StringBuffer();
//		sb.append("<p>尊敬的会员，您提交了找回密码申请。</p>");
//		sb.append("<p>如果您没有提交修改密码的申请, 请忽略本邮件。</p>");
//		sb.append("<p>请点击下面的链接继续:</p>");
//		sb.append("<p></p>");
//		sb.append("<p><a href='" + activeUrl + "' target='_blank'>" + activeUrl + "</a></p>");
//		sb.append("<p>链接将在24小时后失效，如果您无法点击以上链接，请复网址到浏览器里直接打开。</p>");

		// 读取邮件模板
		Template emailTemplate = EmailTemplateUtil.getTemplate("notice_find_pwd.ftl");
		Map map = new HashMap();
		map.put("activeUrl", activeUrl);
		String content = getTemplateStr(map, emailTemplate);
		return content;
	}

	private String getTemplateStr(Map paramMap, Template template) {
		String resultStr = "";
		try {
			StringWriter stringWriter = new StringWriter();
			// 创建模版对象
			template.process(paramMap, stringWriter);
			stringWriter.flush();
			stringWriter.close();

			resultStr = stringWriter.toString();
		} catch (Exception e) {
			log.warn("getTemplateStr failed.", e);
		}

		return resultStr;
	}
	
	

}
