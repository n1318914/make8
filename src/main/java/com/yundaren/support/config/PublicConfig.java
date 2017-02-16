package com.yundaren.support.config;

import lombok.Setter;

public class PublicConfig {
	/** 登录三次失败，第四次登录需要使用验证码 */
	@Setter
	private static int userLoginLimitedTimes = -1;

	/** 平台项目代发账号 */
	@Setter
	private static String publishProxyAccount;

	/** 客服联系手机号 */
	@Setter
	private static String customerServiceNumber;

	/** 注册人数初始值 */
	@Setter
	private static int startRegisterNum;

	/** 认证开发者初始值 */
	@Setter
	private static int startDeveloperNum;

	/** 交易项目初始值 */
	@Setter
	private static int startProjectNum;
	
	@Setter
	private static String newsSource;
	
	@Setter
	private static int newsRefreshRate;
	
	@Setter
	private static int newsDisplayItemCount;

	public static int getUserLoginLimitedTimes() {
		return userLoginLimitedTimes;
	}

	public static String getPublishProxyAccount() {
		return publishProxyAccount;
	}

	public static String getCustomerServiceNumber() {
		return customerServiceNumber;
	}

	public static int getStartRegisterNum() {
		return startRegisterNum;
	}

	public static int getStartDeveloperNum() {
		return startDeveloperNum;
	}

	public static int getStartProjectNum() {
		return startProjectNum;
	}
	
	public static String getNewsSource(){
		return newsSource;
	}
	
	public static int getNewsRefreshRate(){
		return newsRefreshRate;
	}
	
	public static int getNewsDisplayItemCount(){
		return newsDisplayItemCount;
	}

}
