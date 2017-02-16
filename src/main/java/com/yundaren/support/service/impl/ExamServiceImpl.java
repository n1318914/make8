package com.yundaren.support.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.EdaiceUtil;
import com.yundaren.support.biz.ExamBiz;
import com.yundaren.support.service.ExamService;
import com.yundaren.support.vo.ExamAblityVo;
import com.yundaren.support.vo.ExamPaperVo;
import com.yundaren.user.vo.UserInfoVo;

@Slf4j
public class ExamServiceImpl implements ExamService {

	@Setter
	private ExamBiz examBiz;

	/**
	 * 获取试卷
	 */
	@Override
	public ExamPaperVo getExamPaperById(String paperId) {
		return examBiz.getExamPaperById(paperId);
	}

	/**
	 * 批量插入试卷
	 */
	@Override
	public void addBatchExamPaper(List<ExamPaperVo> listExamPaper) {
		examBiz.addBatchExamPaper(listExamPaper);
	}

	/**
	 * 创建考试
	 */
	@Override
	public String createExam(String skill, int grade) {
		String examId = "";
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();

		// 试卷ID，随机取一个
		String testPaperId = "";
		List<ExamPaperVo> listExamPaperVo = examBiz.getExamPaperBySkillAndGrade(skill, grade);
		if (CollectionUtils.isEmpty(listExamPaperVo)) {
			log.warn("not found any exam paper,skill={} grade={}", skill, grade);
			return null;
		} else {
			Collections.shuffle(listExamPaperVo);
			testPaperId = listExamPaperVo.get(0).getId();
		}

		// 调用E代测创建试卷接口
		examId = EdaiceUtil.createTest(testPaperId, currentUser.getName(), currentUser.getMobile(),
				currentUser.getEmail());
		if (StringUtils.isEmpty(examId)) {
			return null;
		}

		ExamAblityVo examAblityVo = new ExamAblityVo();
		examAblityVo.setId(examId);
		examAblityVo.setPaperId(testPaperId);
		examAblityVo.setUserId(currentUser.getId());
		examAblityVo.setStatus("noStarting");
		examBiz.addExamAblity(examAblityVo);
		return examId;
	}

	/**
	 * 获取当前用户的考试状态
	 */
	@Override
	public List<ExamAblityVo> getExamStatus() {
		List<ExamAblityVo> listResult = new ArrayList<ExamAblityVo>();

		// 获取考试历史记录
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		List<ExamAblityVo> listExam = examBiz.getExamStatusListByUID(currentUser.getId());
		List<ExamAblityVo> listNeedQuery = new ArrayList<ExamAblityVo>();

		// 如果历史记录全部是已完成，则不用更新状态直接返回
		for (ExamAblityVo item : listExam) {
			if (!item.getStatus().equals("completing")) {
				listNeedQuery.add(item);
			}
		}

		// 如果存在未完成的状态，则调用E代测接口更新
		if (!CollectionUtils.isEmpty(listNeedQuery)) {
			listExam.removeAll(listNeedQuery);
			// 调用E代测更新考试状态
			EdaiceUtil.getTestResult(listNeedQuery);
			// 更新数据
			examBiz.updateExamStatus(listNeedQuery);
		}
		
		// 合并两部分结果返回
		listResult.addAll(listNeedQuery);
		listResult.addAll(listExam);
		return listResult;
	}
}
