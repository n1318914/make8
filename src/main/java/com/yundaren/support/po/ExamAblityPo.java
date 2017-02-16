package com.yundaren.support.po;

import java.util.Date;

import lombok.Data;

@Data
public class ExamAblityPo {
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
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
}
