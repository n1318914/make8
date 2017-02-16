package com.yundaren.homepage.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.homepage.dao.HomePageDao;
import com.yundaren.homepage.po.CustomerCasePo;
import com.yundaren.homepage.vo.CustomerCaseVo;

import lombok.Setter;

public class HomePageBiz {
   @Setter
   private HomePageDao homepageDao;
   
   public List<CustomerCaseVo> getCustomerCases(int type){
	   List<CustomerCasePo> casesPo = homepageDao.getCustomerCases(type);
	   List<CustomerCaseVo> casesVo = new ArrayList<CustomerCaseVo>();
	   
	   if(!CollectionUtils.isEmpty(casesPo)){
		   casesVo = BeanMapper.mapList(casesPo, CustomerCaseVo.class);
	   }
	   
	   return casesVo;
   }
   
   public CustomerCaseVo getCustomerCaseByCaseId(long caseId){
	   CustomerCasePo casePo = homepageDao.getCustomerCaseByCaseId(caseId);
	   CustomerCaseVo caseVo = null;
	   
	   if(casePo != null){
		   caseVo = BeanMapper.map(casePo, CustomerCaseVo.class);
	   }
	   
	   return caseVo;
   }
}
