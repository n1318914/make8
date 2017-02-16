package com.yundaren.user.po;

import java.util.Date;

import lombok.Data;

/**
 * 个人服务商工作经验
 */
@Data
public class EmployeeJobExperiencePo {
	// 用户ID
	private long userId;
	// 公司名
	private String companyName;
	// 职位
	private String office;
	// 工作开始时间
	private String startTime;
	// 工作结束时间
	private String endTime;
	// 工作内容
	private String description;
	// 项目顺序
	private int ranking;
	// 创建时间
	private Date createTime;
}
