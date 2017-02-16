package com.yundaren.user.service;

import javax.servlet.http.HttpServletRequest;

import com.yundaren.user.vo.InvitationVo;
import com.yundaren.user.vo.NoticeStruct;
import com.yundaren.user.vo.SsoUserVo;

/**
 * 
 * @author kai.xu 账号服务
 */
public interface SsoService {

	/**
	 * 创建账号
	 */
	long createSsoUser(SsoUserVo ssoUserVo);

	SsoUserVo getSsoUserByUserName(String userName);

	SsoUserVo getSsoUserById(long id);

	/**
	 * 发送激活邮件
	 */
	void sendActiveMail(String email);

	/**
	 * 账号激活
	 */
	NoticeStruct activeAccount(HttpServletRequest request, String activeCode, String email);

	/**
	 * 发送找回密码邮件
	 */
	void sendResetPassword(String loginName);

	/**
	 * 找回密码
	 */
	boolean resetPassword(String vCode, String newPassword);

	int updateSsoUser(SsoUserVo ssoUserVo);

	/**
	 * 更新USER关联的SSO信息
	 * 
	 * @param accountType
	 *            账户类型(0邮件1手机号)
	 */
	void updateSsoRelation(String loginName, long userId, int accountType);

	/**
	 * 根据邀请码获取VO对象
	 */
	InvitationVo getInvitationVo(String code);

	/**
	 * 更新登录名名关联的所有SSO账号密码
	 */
	int updateSsoUserPassword(String loginName, String newPassword);
	
	/**
	 * 修改当前登陆用户密码
	 */
	int updatePasswordByID(long uid, String newPassword);

	/**
	 * 更新邀请码使用状态
	 */
	void updateInvitation2Used(String invitationCode, long ssoId);
	
	boolean login(SsoUserVo ssoUser);
}
