package com.yundaren.user.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 个人服务商工作经验
 */
@Data
public class EmployeeJobExperienceVo extends AbstractEmployeeVo {
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
}
