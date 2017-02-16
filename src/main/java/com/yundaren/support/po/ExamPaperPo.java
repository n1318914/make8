package com.yundaren.support.po;

import java.util.Date;

import lombok.Data;

@Data
public class ExamPaperPo {
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
	private Date createTime;
}
