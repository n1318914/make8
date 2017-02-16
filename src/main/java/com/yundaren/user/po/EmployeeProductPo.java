package com.yundaren.user.po;

import java.util.Date;

import lombok.Data;

/**
 * 个人服务商项目作品
 */
@Data
public class EmployeeProductPo {
	// 用户ID
	private long userId;
	// 标题
	private String title;
	// 描述
	private String description;
	// 链接
	private String link;
	// 项目顺序
	private int ranking;
	// 创建时间
	private Date createTime;
}
