package com.yundaren.user.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 个人服务商教育经历
 */
@Data
public class EmployeeTeamProjectExperiencePo {
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
		// 项目顺序
		private int ranking;
		// 创建时间
		private Date createTime;
}
