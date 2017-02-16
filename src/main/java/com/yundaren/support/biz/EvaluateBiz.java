package com.yundaren.support.biz;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springside.modules.mapper.BeanMapper;

import com.yundaren.support.dao.EvaluateDao;
import com.yundaren.support.po.evaluate.EvaluateGroupPo;
import com.yundaren.support.po.evaluate.EvaluateItemPo;
import com.yundaren.support.po.evaluate.EvaluateModulePo;
import com.yundaren.support.po.evaluate.EvaluateSimilarPo;
import com.yundaren.support.vo.evaluate.EvaluateGroupVo;
import com.yundaren.support.vo.evaluate.EvaluateItemVo;
import com.yundaren.support.vo.evaluate.EvaluateModuleVo;
import com.yundaren.support.vo.evaluate.EvaluateSimilarVo;

@Slf4j
public class EvaluateBiz {

	@Setter
	private EvaluateDao evaluateDao;

	public List<EvaluateModuleVo> getAllModule() {
		List<EvaluateModuleVo> listVo = new ArrayList<EvaluateModuleVo>();
		List<EvaluateModulePo> listPo = evaluateDao.getAllModule();
		if (listPo != null) {
			listVo = BeanMapper.mapList(listPo, EvaluateModuleVo.class);
		}
		return listVo;
	}

	public List<EvaluateGroupVo> getAllGroup() {
		List<EvaluateGroupVo> listVo = new ArrayList<EvaluateGroupVo>();
		List<EvaluateGroupPo> listPo = evaluateDao.getAllGroup();
		if (listPo != null) {
			listVo = BeanMapper.mapList(listPo, EvaluateGroupVo.class);
		}
		return listVo;
	}

	public List<EvaluateItemVo> getAllItem() {
		List<EvaluateItemVo> listVo = new ArrayList<EvaluateItemVo>();
		List<EvaluateItemPo> listPo = evaluateDao.getAllItem();
		if (listPo != null) {
			listVo = BeanMapper.mapList(listPo, EvaluateItemVo.class);
		}
		return listVo;
	}
	
	public List<EvaluateSimilarVo> getAllEvaluateSimilar() {
		List<EvaluateSimilarVo> listVo = new ArrayList<EvaluateSimilarVo>();
		List<EvaluateSimilarPo> listPo = evaluateDao.getAllEvaluateSimilar();
		if (listPo != null) {
			listVo = BeanMapper.mapList(listPo, EvaluateSimilarVo.class);
		}
		return listVo;
	}

}
