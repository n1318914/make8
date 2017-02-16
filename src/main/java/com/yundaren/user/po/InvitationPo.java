package com.yundaren.user.po;

import lombok.Data;

import java.util.Date;

@Data
public class InvitationPo {

	// 邀请码
	private String invitationCode;
	// 是否使用(0未使用，1已使用)
	private int isUsed;
	// 账号ID
	private long ssoId;
	// 使用时间
	private Date usedTime;
}
