package com.yundaren.homepage.service.impl;

import java.util.List;

import lombok.Setter;

import com.yundaren.homepage.biz.HomePageBiz;
import com.yundaren.homepage.service.HomePageService;
import com.yundaren.homepage.vo.CustomerCaseVo;

public class HomePageServiceImpl implements HomePageService{

	@Setter
	private HomePageBiz homepageBiz;
	
	@Override
	public List<CustomerCaseVo> getCustomerCases(int type) {
		return homepageBiz.getCustomerCases(type);
	}

	@Override
	public CustomerCaseVo getCustomerCaseByCaseId(long caseId) {
		return homepageBiz.getCustomerCaseByCaseId(caseId);
	}
}
