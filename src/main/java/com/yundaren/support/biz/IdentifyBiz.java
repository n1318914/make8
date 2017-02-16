package com.yundaren.support.biz;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.support.dao.IdentifyDao;
import com.yundaren.support.po.IdentifyPo;
import com.yundaren.support.vo.IdentifyVo;

public class IdentifyBiz {

	@Setter
	private IdentifyDao identifyDao;

	public long addIdentify(IdentifyVo identifyVo) {
		IdentifyPo identifyPo = BeanMapper.map(identifyVo, IdentifyPo.class);
		return identifyDao.addIdentify(identifyPo);
	}

	public List<IdentifyVo> getAcceptIdentifyList() {
		List<IdentifyVo> listIdentifyVo = new ArrayList<IdentifyVo>();
		List<IdentifyPo> listIdentifyPo = identifyDao.getAcceptIdentifyList();
		if (!CollectionUtils.isEmpty(listIdentifyPo)) {
			listIdentifyVo = BeanMapper.mapList(listIdentifyPo, IdentifyVo.class);
		}
		return listIdentifyVo;
	}

	public int updateIdentifyByUID(IdentifyVo identifyVo) {
		IdentifyPo identifyPo = BeanMapper.map(identifyVo, IdentifyPo.class);
		return identifyDao.updateIdentifyByUID(identifyPo);
	}

	public IdentifyVo getIdentifyByUID(long uid) {
		IdentifyVo identifyVo = null;
		IdentifyPo identifyPo = identifyDao.getIdentifyByUID(uid);
		if (identifyPo != null) {
			identifyVo = BeanMapper.map(identifyPo, IdentifyVo.class);
		}
		return identifyVo;
	}

	public IdentifyVo getIdentifyByIDCard(String idCard) {
		IdentifyVo identifyVo = null;
		IdentifyPo identifyPo = identifyDao.getIdentifyByIDCard(idCard);
		if (identifyPo != null) {
			identifyVo = BeanMapper.map(identifyPo, IdentifyVo.class);
		}
		return identifyVo;
	}

	public IdentifyVo getIdentifyByCName(String companyName) {
		IdentifyVo identifyVo = null;
		IdentifyPo identifyPo = identifyDao.getIdentifyByCName(companyName);
		if (identifyPo != null) {
			identifyVo = BeanMapper.map(identifyPo, IdentifyVo.class);
		}
		return identifyVo;
	}
}
