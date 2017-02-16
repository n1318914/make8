package com.yundaren.user.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 用户经历
 */
@Data
public class EmployeeEduExperienceVo extends AbstractEmployeeVo {
	// 学校名
	private String schoolName;
	// 专业
	private String discipline;
	// 学历
	private String eduBackgroud;
	// 毕业时间
	private String graduationTime;
}
