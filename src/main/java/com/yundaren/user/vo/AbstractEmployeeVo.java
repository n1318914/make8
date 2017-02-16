package com.yundaren.user.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 个人服务商教育经历
 */
@Data
public class AbstractEmployeeVo {
	// 用户ID
	private long userId;
	// 项目顺序
	private int ranking;
	// 创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
}
