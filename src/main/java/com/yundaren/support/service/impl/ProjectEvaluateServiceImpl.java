package com.yundaren.support.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.yundaren.common.constants.ProjectStatusEnum;
import com.yundaren.support.biz.ProjectBiz;
import com.yundaren.support.biz.ProjectEvaluateBiz;
import com.yundaren.support.service.ProjectEvaluateService;
import com.yundaren.support.service.ProjectMailService;
import com.yundaren.support.vo.ProjectEvaluateVo;
import com.yundaren.support.vo.ProjectJoinVo;
import com.yundaren.support.vo.ProjectVo;

@Slf4j
public class ProjectEvaluateServiceImpl implements ProjectEvaluateService {

	@Setter
	private ProjectEvaluateBiz projectEvaluateBiz;

	@Setter
	private ProjectBiz projectBiz;
	
	@Setter
	private ProjectMailService projectMailService;

	/**
	 * 新增评价
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addProjectEvaluate(ProjectEvaluateVo evaluateVo) {
		float qualityScore = evaluateVo.getQualityScore();
		float speedScore = evaluateVo.getSpeedScore();
		float attitudeScore = evaluateVo.getAttitudeScore();

		// 计算平均分，四舍五入保留一位小数
		float avgValue = (qualityScore + speedScore + attitudeScore) / 3;
		BigDecimal decimal = new BigDecimal(avgValue);
		avgValue = decimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		evaluateVo.setAverageScore(avgValue);

		// 设置服务商用户ID
		ProjectJoinVo joinVo = projectBiz.getSelectedJoinInfo(evaluateVo.getProjectId());
		evaluateVo.setEmployeeId(joinVo.getUserId());
		projectEvaluateBiz.addProjectEvaluate(evaluateVo);

		// 更新项目状态为已评价
		ProjectVo projectVo = new ProjectVo();
		projectVo.setId(evaluateVo.getProjectId());
		projectVo.setBackgroudStatus(ProjectStatusEnum.BACK_EVALUATION);
		projectBiz.updateProject(projectVo);
		
		// 评价完成告知结果给服务商
		projectMailService.sendEvaluateResultMail(evaluateVo.getProjectId());
	}

	/**
	 * 根据服务商用户ID获取服务商评价列表
	 */
	@Override
	public List<ProjectEvaluateVo> getEvaluateListByUID(long uid) {
		return projectEvaluateBiz.getEvaluateListByUID(uid);
	}

	/**
	 * 根据项目ID获取评价信息
	 */
	@Override
	public ProjectEvaluateVo getEvaluateByPID(String pid) {
		return projectEvaluateBiz.getEvaluateByPID(pid);
	}
}
