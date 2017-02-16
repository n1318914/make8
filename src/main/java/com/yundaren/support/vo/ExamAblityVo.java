package com.yundaren.support.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ExamAblityVo {

	// 测试ID
	private String id;
	// 试卷ID
	private String paperId;
	private String paperName; 
	// 用户ID
	private long userId;
	// 试卷状态状态(completing测试完成，noStarting测试未开始，starting正在测试)
	private String status;
	// 成绩
	private float score;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// 开始时间
	private Date startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	// 结束时间
	private Date endTime;
}
