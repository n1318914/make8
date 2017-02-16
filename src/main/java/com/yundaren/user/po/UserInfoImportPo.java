package com.yundaren.user.po;

import java.util.Date;

import lombok.Data;

@Data
public class UserInfoImportPo {
	// 用户ID
	private long id;
	// 姓名
	private String name;
	// 手机
	private String mobile;
	// 地区
	private String location;
	// 邮箱
	private String mail;
	// 微博
	private String weibo;
	// 微信
	private String weixin;
	// qq
	private String qq;
	// 履历
	private String exp;
	// 技能
	private String skill;
	// 作品
	private String job;
	// git
	private String git;
	// 知乎
	private String zhihu;
	// stackoverflow
	private String stackoverflow;
	// 工作
	private String work;
	// 工作状态
	private String status;
	// 来源
	private String from;
	private Date createTime;
	// 简介
	private String introduction;
}
