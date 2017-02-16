package com.yundaren.homepage.vo;

import lombok.Data;

@Data
public class CustomerCaseVo {
	/**客户案例ID**/
	private int caseId;
	
	/**客户案例展示图片URL**/
	private String mainPageUrl;
	
	/**客户案例LOGO图片URL**/
	private String logoUrl;
	
	/**客户案例开发周期**/
	private String devsCycle;
	
	/**客户案例名称**/
	private String caseName;
	
	/**客户案例展示链接**/
	private String siteURL;
	
	/**客户案例描述**/
	private String caseDesc;
	
	/**参与的角色**/
	private String roles;
	
	/**首页缩略图URL**/
	private String abbrPicUrl;
	
	/**简介**/
	private String caseIntroduction;
}
