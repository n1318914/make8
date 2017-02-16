package com.yundaren.support.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.google.common.collect.ImmutableMap;
import com.yundaren.support.po.ExamAblityPo;
import com.yundaren.support.po.ExamPaperPo;

public class ExamDao extends SqlSessionDaoSupport {

	public ExamPaperPo getExamPaperById(String paperId) {
		return getSqlSession().selectOne("getExamPaperById", paperId);
	}

	public int addBatchExamPaper(List<ExamPaperPo> listExamPaper) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("listExamPaper", listExamPaper);
		return getSqlSession().insert("addBatchExamPaper", paramsMap);
	}

	public List<ExamPaperPo> getExamPaperBySkillAndGrade(String skill, int grade) {
		return getSqlSession().selectList("getExamPaperBySkillAndGrade",
				ImmutableMap.of("skill", skill, "grade", grade));
	}

	public List<ExamAblityPo> getExamStatusListByUID(long uid) {
		return getSqlSession().selectList("getExamStatusListByUID", uid);
	}

	public int addExamAblity(ExamAblityPo examAblityPo) {
		return getSqlSession().insert("addExamAblity", examAblityPo);
	}

	public int updateExamAblityStatus(List<ExamAblityPo> examList) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("examList", examList);
		return getSqlSession().update("updateExamAblityStatus", paramsMap);
	}
}
