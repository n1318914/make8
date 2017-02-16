package com.yundaren.homepage.service;

import java.util.List;

import com.yundaren.homepage.vo.CustomerCaseVo;

public interface HomePageService {
	public List<CustomerCaseVo> getCustomerCases(int type);
	public CustomerCaseVo getCustomerCaseByCaseId(long caseId);
}
