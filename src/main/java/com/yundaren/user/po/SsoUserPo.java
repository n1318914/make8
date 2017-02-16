package com.yundaren.user.po;

import lombok.Data;

import java.util.Date;

@Data
public class SsoUserPo {

	private long id;
	// 登录名
	private String loginName;
	// 用户信息ID
	private long userId;
	// 账户类型(0邮箱、1手机号)
	private int accountType;
	// 登录密码
	private String password;
	// 最后登录时间
	private Date lastLoginTime;
	// 创建时间
	private Date createTime = new Date();
	// 是否激活(0已激活1未激活)
	private int isActive = 1;
	// 扩展属性
	private String extAttrs;
	private String ipAddress;
}
