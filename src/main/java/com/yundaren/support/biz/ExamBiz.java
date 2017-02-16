package com.yundaren.support.biz;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.support.dao.ExamDao;
import com.yundaren.support.po.ExamAblityPo;
import com.yundaren.support.po.ExamPaperPo;
import com.yundaren.support.vo.ExamAblityVo;
import com.yundaren.support.vo.ExamPaperVo;

@Slf4j
public class ExamBiz {

	@Setter
	private ExamDao examDao;

	/**
	 * 通过ID获取试卷
	 */
	public ExamPaperVo getExamPaperById(String paperId) {
		ExamPaperPo examPaperPo = examDao.getExamPaperById(paperId);
		ExamPaperVo examPaperVo = null;
		if (examPaperPo == null) {
			examPaperVo = BeanMapper.map(examPaperPo, ExamPaperVo.class);
		}
		return examPaperVo;
	}
	
	/**
	 * 通过等级和技能获取试卷
	 */
	public List<ExamPaperVo> getExamPaperBySkillAndGrade(String skill, int grade) {
		List<ExamPaperPo> listExamPaperPo = examDao.getExamPaperBySkillAndGrade(skill, grade);
		List<ExamPaperVo> examPaperVo = new ArrayList<ExamPaperVo>();
		if (!CollectionUtils.isEmpty(listExamPaperPo)) {
			examPaperVo = BeanMapper.mapList(listExamPaperPo, ExamPaperVo.class);
		}
		return examPaperVo;
	}

	/**
	 * 批量插入试卷
	 */
	public void addBatchExamPaper(List<ExamPaperVo> listExamPaper) {
		List<ExamPaperPo> listPaperPo = new ArrayList<ExamPaperPo>();
		if (!CollectionUtils.isEmpty(listExamPaper)) {
			listPaperPo = BeanMapper.mapList(listExamPaper, ExamPaperPo.class);
		}
		examDao.addBatchExamPaper(listPaperPo);
	}

	/**
	 * 新增考试记录
	 */
	public int addExamAblity(ExamAblityVo examAblityVo) {
		ExamAblityPo examPaperPo = BeanMapper.map(examAblityVo, ExamAblityPo.class);
		return examDao.addExamAblity(examPaperPo);
	}

	/**
	 * 获取当前用户的考试状态
	 */
	public List<ExamAblityVo> getExamStatusListByUID(long uid) {
		List<ExamAblityVo> listExamAblityVo = new ArrayList<ExamAblityVo>();
		List<ExamAblityPo> listExamAblity = examDao.getExamStatusListByUID(uid);
		if (!CollectionUtils.isEmpty(listExamAblity)) {
			listExamAblityVo = BeanMapper.mapList(listExamAblity, ExamAblityVo.class);
		}
		return listExamAblityVo;
	}
	
	/**
	 * 更新测试状态和成绩
	 */
	public int updateExamStatus(List<ExamAblityVo> examList) {
		List<ExamAblityPo> listExamAblity = new ArrayList<ExamAblityPo>();
		if (!CollectionUtils.isEmpty(examList)) {
			listExamAblity = BeanMapper.mapList(examList, ExamAblityPo.class);
		}
		return examDao.updateExamAblityStatus(listExamAblity);
	}
}
