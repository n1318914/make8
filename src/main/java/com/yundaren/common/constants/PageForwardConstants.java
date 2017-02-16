package com.yundaren.common.constants;

public class PageForwardConstants {

	public static final String DIR_HOME = "/home";
	public static final String DIR_ADMIN = "/admin";
	public static final String DIR_PUBLIC = "/public";
	public static final String DIR_ABOUT = "/about";
	public static final String DIR_MOBILE = "/mobile";
	public static final String DIR_SEO = "/infos";

	/** 首页 **/
	public static final String INDEX_PAGE = "/index";
	
	/**首页客户案例**/
	
	public static final String CUSTOMER_CASE_DETAIL = DIR_PUBLIC + "/case/detail";
	
	/*
	 * 首页服务内容
	 */
	/***解决方案详情**/
	public static final String SERVICE_SOLUTION_DETAIL = DIR_PUBLIC + "/solution";
	/**软件众包详情**/
	public static final String SERVICE_CROWDSOURCING_DETAIL = DIR_PUBLIC + "/crowdsourcing";
	/**运维服务**/
	public static final String SERVICE_MAINTAINANCE_DETAIL = DIR_PUBLIC + "/maintainance";

	/*
	 * 客户案例聚合页面
	 */
	public static final String CUSTOMER_USECASES = DIR_PUBLIC + "/customercases";
	/*
	 * 账号模块
	 */
	/** 注册成功提示页面 **/
	public static final String USERS_ACTIVE_NOTICE_PAGE = "/active/notice";
	/** 发送激活邮件 **/
	public static final String USERS_SEND_ACTIVE_MAIL = "/sendActiveMail";
	/** 找回密码邮箱链接 **/
	public static final String USERS_RESET = "/reset";
	/*
	 * 用户模块
	 */
	/** 获取认证信息 **/
	public static final String USERS_INFO_PAGE = DIR_HOME + "/userinfo";
	/** 用户财务中心 **/
	public static final String USERS_ACCOUNT_PAGE = DIR_HOME + "/useraccount";
	/** 认证信息修改跳转 **/
	public static final String USERS_IDENTIFY_REDIRECT = DIR_HOME + "/identify/modify";
	/** 主动修改认证信息跳转 **/
	public static final String USERS_A_IDENTIFY_REDIRECT = DIR_HOME + "/identify/amodify";
	/** 服务商个人主页 **/
	public static final String USERS_HOME_PAGE = DIR_PUBLIC + "/member";
	/*
	 * 需求模块
	 */
	/** 需求详情ftl页面 **/
	public static final String PROJECT_VIEW_PAGE = DIR_HOME + "/p/view";
	/** 修改发标信息页面 **/
	public static final String PROJECT_MODIFY_PAGE = DIR_HOME + "/p/modify";
	/** 需求列表页面 **/
	public static final String PROJECT_LIST = "/market";
	/** 发布需求页面 **/
	public static final String PROJECT_REQUEST = DIR_HOME + "/request";
	/** 推送项目详情页 **/
	public static final String PUSH_PROJDETAIL = DIR_HOME + "/push_projdetail";
	/** 项目列表 **/
	public static final String PROJECT_LIST_MANAGER_PAGE = DIR_HOME + "/projects_review";
	/*
	 * 服务商模块
	 */
	/** 服务商列表 **/
	public static final String MEMBER_LIST = DIR_PUBLIC + "/comp_list";
	/*
	 * 管理员操作
	 */
	/** 用户详情 **/
	public static final String USER_REVIEW_MANAGER_PAGE = DIR_ADMIN + "/users_review";
	/** 预约列表 **/
	public static final String RESERVE_LIST_PAGE = DIR_ADMIN + "/reserve_review";
	/** 预约顾问发布 **/
	public static final String RESERVE_PUBLISH = DIR_ADMIN + "/reserve_publish";
	/** 服务商录入 **/
	public static final String MEMBER_MODIFY = DIR_ADMIN + "/compinfo_modify";
	/**导入数据维护**/
	public static final String USER_INFO_IMPORT = DIR_ADMIN + "/user_info_import";
	/**后台数据分析**/
	public static final String DATA_ANYLIZE = DIR_ADMIN + "/dataAnylize";
	/**邮件群发页面**/
	public static final String MAIL_SEND_BATCH = DIR_ADMIN + "/mail_send";
	/**需求审核列表**/
	public static final String REQUEST_REVIEW_LIST = DIR_ADMIN + "/requests_review";
	/**需求审核详情**/
	public static final String REQUEST_REVIEW = DIR_ADMIN + "/request_review";
	
	/*
	 * About
	 */
	/** 关于我们 **/
	public static final String ABOUTUS_PAGE = DIR_ABOUT + "/aboutus";
	/** 联系我们 **/
	public static final String CONTACTUS_PAGE = DIR_ABOUT + "/contactus";
	/** 服务协议 **/
	public static final String CONTRACT_PAGE = DIR_ABOUT + "/contract";
	/** 友情链接 **/
	public static final String FLINK_PAGE = DIR_ABOUT + "/flink";
	/** 加入我们 **/
	public static final String JOINUS_PAGE = DIR_ABOUT + "/joinus";
	/**服务流程**/
	public static final String SERVICE_FLOW = DIR_ABOUT + "/serviceflow";
	
	/*
	 * 估价功能
	 */
	/** 估价首页 **/
	public static final String EVALUATE_INDEX = DIR_PUBLIC + "/evaluate";
	/** 获取报价 **/
	public static final String EVALUATE_DETAILS = DIR_PUBLIC + "/get_evaluate";

	/*
	 * 公共
	 */
	/** 找回密码页面 **/
	public static final String FIND_PASSWORD_PAGE = DIR_PUBLIC + "/find_password";
	/** 登录页面 **/
	public static final String LOGIN_PAGE = DIR_PUBLIC + "/login";
	/** 支付账号页面 **/
	public static final String PAY_PAGE = DIR_PUBLIC + "/pay";
	/** 如何使用页面 **/
	public static final String QA_PAGE = DIR_PUBLIC + "/qa";
	/** 注册页面 **/
	public static final String REGISTER_PAGE = DIR_PUBLIC + "/register";
	/** 重置提醒 **/
	public static final String RESET_NOTICE_PAGE = DIR_PUBLIC + "/reset_notice";
	/** 重置成功 **/
	public static final String RESET_OK_PAGE = DIR_PUBLIC + "/reset_ok";

	/*
	 * 移动端
	 */
	/** 需求发布 **/
	public static final String MOBILE_ADD_REQUEST = DIR_MOBILE + "/request";
	/** 预约顾问 **/
	public static final String MOBILE_RESERVE = DIR_MOBILE + "/reserve";
	/** 提示信息页面 **/
	public static final String MOBILE_NOTICE = DIR_MOBILE + "/notice";
	/** 移动端首页 **/
	public static final String MOBILE_INDEX_PAGE = DIR_MOBILE + "/index";
	/** 项目市场 **/
	public static final String MOBILE_PROJECT_MARKET = DIR_MOBILE + "/market";
	/** 如何使用 **/
	public static final String MOBILE_HOW_2_USE = DIR_MOBILE + "/how_to_use";
	
	/*
	 * 自营项目
	 */
	/** 需求详情ftl页面 **/
	public static final String PROJECT_IN_SELF_RUN_VIEW_PAGE = DIR_HOME + "/selfrun/p/view";
	public static final String PROJECT_IN_SELF_RUN_MODIFY = DIR_HOME + "/selfrun/p/modify";
	
	/** 代码托管页**/
	public static final String CODE_TMPL = DIR_HOME + "/code";
	
	/**SEO**/
	public static final String SEO_INDEX = DIR_SEO + "/index";
	public static final String SEO_PROVINCE = DIR_SEO + "/province";
	public static final String SEO_CITY = DIR_SEO + "/city";
	public static final String SEO_DISTRICT = DIR_SEO + "/district";
	public static final String SEO_SIBLING = DIR_SEO + "/sibling";
}
