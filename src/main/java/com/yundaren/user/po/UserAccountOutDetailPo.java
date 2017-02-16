package com.yundaren.user.po;

import java.util.Date;

import lombok.Data;

@Data
public class UserAccountOutDetailPo {
	//记录id
	private int id;
	//用户id
	private int userId;
	//日期
	private Date date = new Date();
	//交易金额
	private double amount;
	//支付账号id
	private int accountId;
	//描述
	private String comment;
	//确认转账时间
	private Date confirmTime = new Date();
	//0申请中   1已汇款
	private int status = 0;
}
