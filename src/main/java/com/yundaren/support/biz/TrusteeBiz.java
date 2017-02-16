package com.yundaren.support.biz;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.support.dao.TrusteeDao;
import com.yundaren.support.po.TrusteeInfoPo;
import com.yundaren.support.vo.TrusteeInfoVo;

public class TrusteeBiz {

	@Setter
	private TrusteeDao trusteeDao;

	public long addTrusteeInfo(TrusteeInfoVo trusteeVo) {
		// TODO Auto-generated method stub
		TrusteeInfoPo TrusteePo = BeanMapper.map(trusteeVo, TrusteeInfoPo.class);
		return trusteeDao.addTrusteeInfo(TrusteePo);
	}

	public List<TrusteeInfoVo> getTrusteeInfoListByPID(String pid) {
		// TODO Auto-generated method stub
		List<TrusteeInfoVo> listTrusteeVo = new ArrayList<TrusteeInfoVo>();
		List<TrusteeInfoPo> listTrusteePo = trusteeDao.getTrusteeInfoListByPID(pid);
		if (!CollectionUtils.isEmpty(listTrusteePo)) {
			listTrusteeVo = BeanMapper.mapList(listTrusteePo, TrusteeInfoVo.class);
		}
		return listTrusteeVo;
	}
	
	/**
	 * 统计当前已托管的总金额
	 */
	public double getSumTrusteeMoney(String pid) {
		double total = 0;
		List<TrusteeInfoVo> listTrusteeVo = getTrusteeInfoListByPID(pid);
		for (TrusteeInfoVo tVo : listTrusteeVo) {
			total += tVo.getAmount();
		}
		return total;
	}
}
