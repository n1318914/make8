package com.yundaren.support.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import com.yundaren.user.vo.UserInfoVo;

@Data
public class ProjectEvaluateVo {
	// 需求id
	private String projectId;
	// 服务商用户ID
	private long employeeId;
	// 完成质量分数
	private float qualityScore;
	// 完成速度分数
	private float speedScore;
	// 服务态度分数
	private float attitudeScore;
	// 平均分
	private float averageScore;
	// 评价描述
	private String description;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime = new Date();
}
