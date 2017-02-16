package com.yundaren.support.biz;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.support.dao.ProjectEvaluateDao;
import com.yundaren.support.dao.TradeDao;
import com.yundaren.support.po.ProjectEvaluatePo;
import com.yundaren.support.po.TradeInfoPo;
import com.yundaren.support.vo.ProjectEvaluateVo;
import com.yundaren.support.vo.TradeInfoVo;

public class ProjectEvaluateBiz {

	@Setter
	private ProjectEvaluateDao projectEvaluateDao;

	public void addProjectEvaluate(ProjectEvaluateVo evaluateVo) {
		ProjectEvaluatePo evaluatePo = BeanMapper.map(evaluateVo, ProjectEvaluatePo.class);
		projectEvaluateDao.addProjectEvaluate(evaluatePo);
	}

	public List<ProjectEvaluateVo> getEvaluateListByUID(long uid) {
		List<ProjectEvaluateVo> listEvaluateVo = new ArrayList<ProjectEvaluateVo>();
		List<ProjectEvaluatePo> listEvaluatePo = projectEvaluateDao.getEvaluateListByUID(uid);
		if (!CollectionUtils.isEmpty(listEvaluatePo)) {
			listEvaluateVo = BeanMapper.mapList(listEvaluatePo, ProjectEvaluateVo.class);
		}
		return listEvaluateVo;
	}

	public ProjectEvaluateVo getEvaluateByPID(String pid) {
		ProjectEvaluateVo evaluateVo = null;
		ProjectEvaluatePo evaluatePo = projectEvaluateDao.getEvaluateByPID(pid);
		if (evaluatePo != null) {
			evaluateVo = BeanMapper.map(evaluatePo, ProjectEvaluateVo.class);
		}
		return evaluateVo;
	}
}
