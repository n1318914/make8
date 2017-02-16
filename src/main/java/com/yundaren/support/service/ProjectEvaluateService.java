package com.yundaren.support.service;

import java.util.List;

import com.yundaren.support.vo.ProjectEvaluateVo;

/**
 * 
 * @author kai.xu 评价服务
 */
public interface ProjectEvaluateService {

	/**
	 * 新增评价
	 */
	void addProjectEvaluate(ProjectEvaluateVo evaluateVo);
	
	/**
	 * 根据服务商用户ID获取服务商评价列表
	 */
	List<ProjectEvaluateVo> getEvaluateListByUID(long uid);
	
	/**
	 * 根据项目ID获取评价信息
	 */
	ProjectEvaluateVo getEvaluateByPID(String pid);
}
