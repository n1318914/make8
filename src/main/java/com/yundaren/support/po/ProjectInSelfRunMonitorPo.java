package com.yundaren.support.po;

import java.util.Date;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Data
public class ProjectInSelfRunMonitorPo{
	private String projectId;
	private String creatorId;
	private String name;
	private String companyName;
	private int category;
	private int stepId;
	private String monitorDesc;
	private String attachment;
	private String createTime;	
}
