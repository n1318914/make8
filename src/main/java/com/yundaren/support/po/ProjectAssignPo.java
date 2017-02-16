package com.yundaren.support.po;

import java.util.Date;

import lombok.Data;

@Data
public class ProjectAssignPo {
	//项目id
	private int projectId;
	//创建者id
	private int creatorId;
	//转让者id
	private int assignId;
	//转让原因
	private String reason;
	//转让时间
	private Date assignTime;
}
