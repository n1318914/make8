package com.yundaren.support.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.yundaren.user.vo.UserInfoVo;

@Data
public class TrusteeInfoVo {
	// 项目ID
	private String projectId;
	// 托管费用
	private double amount;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime = new Date();
}
