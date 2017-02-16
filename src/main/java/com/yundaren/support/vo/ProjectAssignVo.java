package com.yundaren.support.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ProjectAssignVo {
	//项目id
	private long projectId;
	//创建者id
	private long creatorId;
	//转让者id
	private long assignId;
	//转让原因
	private String reason;
	//转让时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date assignTime;
}
