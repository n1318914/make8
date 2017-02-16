package com.yundaren.support.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;

import com.yundaren.support.biz.EvaluateBiz;
import com.yundaren.support.service.EvaluateService;
import com.yundaren.support.vo.evaluate.EvaluateGroupVo;
import com.yundaren.support.vo.evaluate.EvaluateItemVo;
import com.yundaren.support.vo.evaluate.EvaluateModuleVo;
import com.yundaren.support.vo.evaluate.EvaluateSimilarVo;

@Slf4j
public class EvaluateServiceImpl implements EvaluateService {

	// 初始化加载一次即可
	private static List<EvaluateSimilarVo> listAllSimilar;

	// 按类型分组
	private static Map<String, List<EvaluateSimilarVo>> typeMap = new HashMap<String, List<EvaluateSimilarVo>>();

	// 按行业分组
	private static Map<String, List<EvaluateSimilarVo>> industryMap = new HashMap<String, List<EvaluateSimilarVo>>();

	// 按价格分组
	private static Map<Integer, List<EvaluateSimilarVo>> priceMap = new HashMap<Integer, List<EvaluateSimilarVo>>();

	@Setter
	private EvaluateBiz evaluateBiz;

	static {
		typeMap.put("app", new ArrayList<EvaluateSimilarVo>());
		typeMap.put("html5", new ArrayList<EvaluateSimilarVo>());
		typeMap.put("weixin", new ArrayList<EvaluateSimilarVo>());
		typeMap.put("web", new ArrayList<EvaluateSimilarVo>());

		industryMap.put("企业门户", new ArrayList<EvaluateSimilarVo>());
		industryMap.put("电子商务", new ArrayList<EvaluateSimilarVo>());
		industryMap.put("互联网金融", new ArrayList<EvaluateSimilarVo>());
		industryMap.put("在线教育", new ArrayList<EvaluateSimilarVo>());
		industryMap.put("企业管理系统", new ArrayList<EvaluateSimilarVo>());
		industryMap.put("社交应用", new ArrayList<EvaluateSimilarVo>());

		priceMap.put(0, new ArrayList<EvaluateSimilarVo>());// 小于1000
		priceMap.put(1, new ArrayList<EvaluateSimilarVo>());// 1000-3000
		priceMap.put(2, new ArrayList<EvaluateSimilarVo>());// 3000-5000
		priceMap.put(3, new ArrayList<EvaluateSimilarVo>());// 5000-10000
		priceMap.put(4, new ArrayList<EvaluateSimilarVo>());// 10000-20000
		priceMap.put(5, new ArrayList<EvaluateSimilarVo>());// 20000-30000
		priceMap.put(6, new ArrayList<EvaluateSimilarVo>());// 30000-50000
		priceMap.put(7, new ArrayList<EvaluateSimilarVo>());// 50000-100000
		priceMap.put(8, new ArrayList<EvaluateSimilarVo>());// 100000-200000
		priceMap.put(9, new ArrayList<EvaluateSimilarVo>());// 200000-500000
		priceMap.put(10, new ArrayList<EvaluateSimilarVo>());// 大于500000
	}

	@Override
	public List<EvaluateModuleVo> getAllModule() {
		// 获取所有领域
		List<EvaluateModuleVo> listModuleVo = evaluateBiz.getAllModule();
		// 获取所有模块
		List<EvaluateGroupVo> listGroupVo = evaluateBiz.getAllGroup();
		// 获取所有功能点
		List<EvaluateItemVo> listItemVo = evaluateBiz.getAllItem();

		// 封装领域对应的模块和功能点
		for (EvaluateGroupVo group : listGroupVo) {
			for (EvaluateItemVo itme : listItemVo) {
				if (itme.getGroupId() == group.getId()) {
					group.getListEvaluateItemVo().add(itme);
				}
			}
		}
		for (EvaluateModuleVo module : listModuleVo) {
			for (EvaluateGroupVo group : listGroupVo) {
				if (group.getModuleId() == module.getId()) {
					module.getListEvaluateGroup().add(group);
				}
			}
		}

		// 初始化数据
		if (CollectionUtils.isEmpty(listAllSimilar)) {
			listAllSimilar = evaluateBiz.getAllEvaluateSimilar();

			for (EvaluateSimilarVo similarVo : listAllSimilar) {
				// 按类型分组
				switch (similarVo.getType().toLowerCase()) {
					case "app":
						typeMap.get("app").add(similarVo);
						break;
					case "html5":
						typeMap.get("html5").add(similarVo);
						break;
					case "web":
						typeMap.get("web").add(similarVo);
						break;
					case "weixin":
						typeMap.get("weixin").add(similarVo);
						break;
				}

				// 按行业分组
				switch (similarVo.getIndustry()) {
					case "企业门户":
						industryMap.get("企业门户").add(similarVo);
						break;
					case "电子商务":
						industryMap.get("电子商务").add(similarVo);
						break;
					case "互联网金融":
						industryMap.get("互联网金融").add(similarVo);
						break;
					case "在线教育":
						industryMap.get("在线教育").add(similarVo);
						break;
					case "企业管理系统":
						industryMap.get("企业管理系统").add(similarVo);
						break;
					case "社交应用":
						industryMap.get("社交应用").add(similarVo);
						break;
				}

				// 按价格分组
				switch (similarVo.getPrice()) {
					case 0:
						priceMap.get(0).add(similarVo);
						break;
					case 1:
						priceMap.get(1).add(similarVo);
						break;
					case 2:
						priceMap.get(2).add(similarVo);
						break;
					case 3:
						priceMap.get(3).add(similarVo);
						break;
					case 4:
						priceMap.get(4).add(similarVo);
						break;
					case 5:
						priceMap.get(5).add(similarVo);
						break;
					case 6:
						priceMap.get(6).add(similarVo);
						break;
					case 7:
						priceMap.get(7).add(similarVo);
						break;
					case 8:
						priceMap.get(8).add(similarVo);
						break;
					case 9:
						priceMap.get(9).add(similarVo);
						break;
					case 10:
						priceMap.get(10).add(similarVo);
						break;
				}
			}
		}

		return listModuleVo;
	}

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
	@Override
	public List<EvaluateSimilarVo> getCaseByQuery(String type, String industry, float minPrice, float maxPrice) {

		List<EvaluateSimilarVo> listResult = new ArrayList<EvaluateSimilarVo>();

		List<EvaluateSimilarVo> listPick = new ArrayList<EvaluateSimilarVo>();
		List<EvaluateSimilarVo> listType = typeMap.get(type);
		List<EvaluateSimilarVo> listIndustry = industryMap.get(industry);

		// 先根据行业匹配
		if (CollectionUtils.isEmpty(listIndustry)) {
			// 为空再匹配类型
			if (CollectionUtils.isEmpty(listType)) {
				listPick = listAllSimilar;
				log.info("query case listPick : all , args{type=" + type + ",industry=" + industry + "}");
			} else {
				listPick = listType;
				log.info("query case listPick : type , args{type=" + type + ",industry=" + industry + "}");
			}
		} else {
			listPick = listIndustry;
			log.info("query case listPick : industry , args{type=" + type + ",industry=" + industry + "}");
		}

		// 根据价格过滤一遍
		float avgPrice = (minPrice + maxPrice) / 2;
		int level = 3;
		if (avgPrice < 1000) {
			level = 0;
		} else if (avgPrice >= 1000 && avgPrice <= 2000) {
			level = 1;
		} else if (avgPrice >= 2000 && avgPrice <= 5000) {
			level = 2;
		} else if (avgPrice >= 5000 && avgPrice <= 10000) {
			level = 3;
		} else if (avgPrice >= 10000 && avgPrice <= 20000) {
			level = 4;
		} else if (avgPrice >= 20000 && avgPrice <= 30000) {
			level = 5;
		} else if (avgPrice >= 30000 && avgPrice <= 50000) {
			level = 6;
		} else if (avgPrice >= 50000 && avgPrice <= 100000) {
			level = 7;
		} else if (avgPrice >= 100000 && avgPrice <= 200000) {
			level = 8;
		} else if (avgPrice >= 200000 && avgPrice <= 500000) {
			level = 9;
		} else if (avgPrice >= 500000) {
			level = 10;
		}
		listResult = getByPrice(level, listPick);

		return listResult;
	}

	// 根据价格过滤
	private List<EvaluateSimilarVo> getByPrice(int level, List<EvaluateSimilarVo> listInput) {
		List<EvaluateSimilarVo> resultList = new ArrayList<>(listInput);
		List<EvaluateSimilarVo> swapList = new ArrayList<EvaluateSimilarVo>();
		if (level > 0 && level < 10) {
			swapList.addAll(priceMap.get(level));
			swapList.addAll(priceMap.get(level + 1));
			swapList.addAll(priceMap.get(level - 1));
		} else if (level == 0) {
			swapList.addAll(priceMap.get(level));
			swapList.addAll(priceMap.get(level + 1));
		} else if (level == 10) {
			swapList.addAll(priceMap.get(level));
			swapList.addAll(priceMap.get(level - 1));
		}

		resultList.retainAll(swapList);
		if (CollectionUtils.isEmpty(resultList)) {
			resultList.addAll(listInput);
		}
		log.info("price level is " + level + ", list retain price size is " + resultList.size());

		// 推荐展现个数
		int displaySize = 2;
		if (resultList.size() > displaySize) {
			// 集合随机排序
			Collections.shuffle(resultList);
			resultList = resultList.subList(0, 2);
		}

		return resultList;
	}
}
