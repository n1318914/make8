package com.yundaren.support.vo;

import java.util.Date;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.yundaren.user.vo.UserInfoVo;

@Data
public class ProjectOperationLogVo {
		private long id;
		private long creatorId;
		private UserInfoVo creator;
		
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
