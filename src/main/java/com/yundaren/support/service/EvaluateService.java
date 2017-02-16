package com.yundaren.support.service;

import java.util.List;

import com.yundaren.support.vo.evaluate.EvaluateModuleVo;
import com.yundaren.support.vo.evaluate.EvaluateSimilarVo;

/**
 * 估价功能
 */
public interface EvaluateService {

	/**
	 * 获取所有业务领域
	 */
	public List<EvaluateModuleVo> getAllModule();

	/**
	 * 获取相关案例
	 * 
	 * @param type
	 *            项目类型(iOS Andorid weixin web)
	 * @param industry
	 *            行业(企业门户 电子商务...)
	 * @param minPrice
	 *            最小价格
	 * @param maxPrice
	 *            最大价格
	 */
	public List<EvaluateSimilarVo> getCaseByQuery(String type, String industry, float minPrice, float maxPrice);
}
