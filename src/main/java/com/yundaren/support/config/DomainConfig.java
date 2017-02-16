package com.yundaren.support.config;

import lombok.Setter;

public class DomainConfig {
	/** 外包么域名 */
	@Setter
	private static String host;
	/** 是否是生产环境 */
	@Setter
	private static Boolean isProduceEnvironment;
	/** 根目录 */
	@Setter
	private static String uploadImgRoot;
	/** 绑定的upyun域名 */
	@Setter
	private static String bindDomain;
	@Setter
	private static String gogsBaseUrl;
	@Setter
	private static String gogsAdmin;
	@Setter
	private static String gogsAdminPwd;
	
	public static String getHost() {
		return host;
	}

	public static Boolean getIsProduceEnvironment() {
		return isProduceEnvironment;
	}

	public static String getUploadImgRoot() {
		return uploadImgRoot;
	}

	public static String getBindDomain() {
		return bindDomain;
	}
	
	public static String getGogsBaseUrl() {
		return gogsBaseUrl;
	}
	public static String getGogsAdmin() {
		return gogsAdmin;
	}
	public static String getGogsAdminPwd() {
		return gogsAdminPwd;
	}
	
}
