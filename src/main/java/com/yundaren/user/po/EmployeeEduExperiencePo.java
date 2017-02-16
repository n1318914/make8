package com.yundaren.user.po;

import java.util.Date;

import lombok.Data;

/**
 * 个人服务商教育经历
 */
@Data
public class EmployeeEduExperiencePo {
	// 用户ID
	private long userId;
	// 学校名
	private String schoolName;
	// 专业
	private String discipline;
	// 学历
	private String eduBackgroud;
	// 毕业时间
	private String graduationTime;
	// 项目顺序
	private int ranking;
	// 创建时间
	private Date createTime;
}
