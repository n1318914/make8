package com.yundaren.user.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 企业服务商项目经验
 */
@Data
public class EmployeeTeamProjectExperienceVo extends AbstractEmployeeVo {
	// 用户ID
	private long userId;
	// 标题
	private String projectName;
	// 描述
	private String description;
	// 项目开始时间
	private String startTime;
	// 项目结束时间
	private String endTime;
	// 链接
	private String link;
}
