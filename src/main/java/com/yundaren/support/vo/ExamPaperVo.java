package com.yundaren.support.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ExamPaperVo {

	// 试卷ID
	private String id;
	// 试卷名称
	private String name;
	// 技能点(关联dict_item表)
	private String skill;
	// 难度等级(1初级2中级3高级)
	private int grade;
	// 试卷状态(1启用2停用)
	private int status;
	// 创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
}
