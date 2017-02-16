package com.yundaren.job;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yundaren.common.util.EdaiceUtil;
import com.yundaren.support.service.ExamService;
import com.yundaren.support.vo.ExamPaperVo;

/**
 * E代测试卷同步
 */
@Slf4j
@Component
public class EdaiceExamPaperUpdateJob {

	@Autowired
	private ExamService examService;

	// 同步试卷
	@Scheduled(cron = "0 0 0 * * ?")
	public void timingUpdateExamPaper() {
		log.info("timingUpdateExamPaper job start");

		List<ExamPaperVo> listExamPaper = EdaiceUtil.getExamPapers();
		// 更新数据库表
		examService.addBatchExamPaper(listExamPaper);

		log.info("timingUpdateExamPaper job end");
	}
}
