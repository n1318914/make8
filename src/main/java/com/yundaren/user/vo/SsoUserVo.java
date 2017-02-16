package com.yundaren.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SsoUserVo {

	private long id;
	// 登录名
	private String loginName;
	// 登录密码
	private String password;
	// 用户信息ID
	private long userId;
	// 账户类型(0邮箱、1手机号)
	private int accountType;
	// 最后登录时间
	private Date lastLoginTime;
	// 创建时间
	private Date createTime = new Date();
	// 是否激活(0已激活1未激活)
	private int isActive = 1;
	// 扩展属性
	private String extAttrs;
	// 验证码
	private String vcode;
	// 用户类型 (-1管理员、0雇主、1服务商)
	private int userType = -2;
	private String ipAddress;

	// 用户信息
	private UserInfoVo userInfoVo;
}
