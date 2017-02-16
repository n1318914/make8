package com.yundaren.support.po;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.yundaren.user.vo.UserInfoVo;

@Data
public class TrusteeInfoPo {
	// 项目ID
	private String projectId;
	// 托管费用
	private double amount;
	private Date createTime = new Date();
}
