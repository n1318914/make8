package com.yundaren.support.po;

import java.util.Date;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Data
public class ProjectInSelfRunHandlerPo{
	private String projectId;
	private String developerId;
	private String role;
}
