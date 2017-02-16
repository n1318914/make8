package com.yundaren.support.po;

import java.util.Date;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Data
public class ProjectInSelfRunPlanPo{
	private String projectId;
	private int stepId;
	private String step;
	private String stepName;
	private String stepDesc;
	// 计划开始时间
	private String startTime;
	// 计划完成时间
	private String endTime;
	
	private String creatorId;
	private int creatorCategory;
	private String creatorName;
	private String creatorCompanyName;
	private String executorId;
	private int executorCategory;
	private String executorName;
	private String executorCompanyName;
	
	private int status;	
	private String statusName;
	
	private double price;
	
	//2016-05-20 项目是否延迟
	private int isDelayed;
	
	//2016-6-16项目该阶段的验收状态
	private int verifyStatus = 1;
	private String verifyStatusName;
	
}
