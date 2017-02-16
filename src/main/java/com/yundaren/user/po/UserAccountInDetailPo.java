package com.yundaren.user.po;

import java.util.Date;

import lombok.Data;

@Data
public class UserAccountInDetailPo {
	//记录id
	private int id;
	//用户id
	private int userId;
	//日期
	private Date date = new Date();
	//计划id
	private int planId;
	//项目id
	private int projectId;
	//阶段id
	private int stepId;
	//交易金额
	private double amount;
	//支付账号id
	private int accountId;
	//描述
	private String comment;
}
