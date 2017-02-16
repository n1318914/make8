package com.yundaren.user.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 个人服务商项目作品
 */
@Data
public class EmployeeProductVo extends AbstractEmployeeVo {
	// 标题
	private String title;
	// 描述
	private String description;
	// 链接
	private String link;
}
