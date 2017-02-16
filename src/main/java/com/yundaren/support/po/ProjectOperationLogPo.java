package com.yundaren.support.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ProjectOperationLogPo {
	private long id;
	private long creatorId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date createTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date updateTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date deleteTime;
	
	private String logContent;
	
	//1所有人可见 2仅顾问可见 3仅开发可见 4仅雇主可见
	private int logPermission;
	
	private long projectId;
}
