package com.yundaren.user.po;

import lombok.Data;

@Data
public class UserAccountPo {
	//账号id
	private int id;
	//用户id
	private int userId;
	//账号
	private String accountNum;
	//账号类型
	private int accountType;
	//类型名
	private String accountName;
}
