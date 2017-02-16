package com.yundaren.job;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yundaren.support.service.ProjectService;

@Slf4j
@Component
public class ProjectJob {

	@Setter
	private ProjectService projectService;

	// 更新竞标超时项目状态
	@Scheduled(cron = "0 0 0 * * ?")
	public void timingUpdateProjectStatus() {
//		log.info("UpdateProjectStatus job start");
//		projectService.updateTimeoutProject();
//		log.info("UpdateProjectStatus job end");
	}
	
	// 竞标截止日即将到期邮件提醒发标人
	@Scheduled(cron = "0 0 15 * * ?")
	public void timingNoticeBidTimeout() {
//		log.info("timingNoticeBidTimeout job start");
//		projectService.noticeBidTimeout();
//		log.info("timingNoticeBidTimeout job end");
	}
}
