package com.yundaren.support.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.support.po.IdentifyPo;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.support.vo.ProjectEvaluateVo;

/**
 * 
 * @author kai.xu 认证
 */
public interface IdentifyService {

	/**
	 * 发起认证
	 */
	long addIdentify(IdentifyVo identifyVo);

	/**
	 * 获得审核中的认证列表
	 */
	List<IdentifyVo> getAcceptIdentifyList();

	/**
	 * 更新认证信息
	 */
	int updateIdentifyByUID(IdentifyVo identifyVo);

	/**
	 * 根据项目ID获取认证信息
	 */
	IdentifyVo getIdentifyByUID(long uid);

	/**
	 * 根据身份证号码获取认证信息
	 */
	IdentifyVo getIdentifyByIDCard(String idCard);

	/**
	 * 根据公司名称获取认证信息
	 */
	IdentifyVo getIdentifyByCName(String companyName);
	
	/**
	 * 录入认证
	 */
	long preAddIdentify(IdentifyVo identifyVo);
}
