package com.yundaren.user.vo;

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
}
