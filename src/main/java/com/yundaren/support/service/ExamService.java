package com.yundaren.support.service;

import java.util.List;

import com.yundaren.support.vo.ExamAblityVo;
import com.yundaren.support.vo.ExamPaperVo;

/**
 * Edaice能力测试
 */
public interface ExamService {

	/**
	 * 获取试卷
	 */
	ExamPaperVo getExamPaperById(String paperId);

	/**
	 * 批量插入试卷
	 */
	void addBatchExamPaper(List<ExamPaperVo> listExamPaper);

	/**
	 * 创建考试
	 */
	String createExam(String skill, int grade);

	/**
	 * 获取当前用户的考试状态
	 */
	List<ExamAblityVo> getExamStatus();

}
