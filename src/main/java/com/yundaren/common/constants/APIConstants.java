package com.yundaren.common.constants;

public class APIConstants {

	public static final String API = "/api";
	/** API访问控制，需要登录后才能访问 **/
	public static final String API_ALLOW_LOGIN = "/api/1";

	/** API访问控制，管理员才能访问 **/
	public static final String API_ALLOW_ADMIN = "/api/2";

	/*
	 * 账号模块
	 */
	/** 账号激活 **/
	public static final String USERS_ACTIVE = "/active";
	/** 账号注册 **/
	public static final String USERS_REGISTER = API + "/u/register";
	/** 检查用户是否存在 **/
	public static final String USERS_CHECK_EXIST = API + "/u/check/exist";
	/** 用户登录 **/
	public static final String USERS_LOGIN = API + "/u/login";
	/** 注销 **/
	public static final String USERS_LOGOUT = API + "/u/logout";
	/** 发送找回密码邮件 **/
	public static final String USERS_SEND_RESET = API + "/u/sendResetPwd";
	/** 找回密码修改操作 **/
	public static final String USERS_RESET_CHANGE = API + "/u/resetPwd";
	/** 修改密码 **/
	public static final String USERS_CHANGE_PASSWORD = API_ALLOW_LOGIN + "/u/changePassword";
	/** 当前访问者是否登录 **/
	public static final String USERS_IS_LOGIN = API + "/u/isLogin";
	/** 当前访问者是否登录 **/
	public static final String USER_AUTO_LOGIN = API + "/u/autoLogin";

	/*
	 * 用户模块
	 */
	/** 修改个人信息 **/
	public static final String USERS_INFO_MODIFY = API_ALLOW_LOGIN + "/u/info/modify";
	/** 获取个人信息API **/
	public static final String USERS_INFO = API_ALLOW_LOGIN + "/u/info";
	/** 检查个人信息是否完善 **/
	public static final String USERS_INFO_CHECK_COMPLETE = API_ALLOW_LOGIN + "/u/isComplete";
	/** 检查昵称是否存在 **/
	public static final String USERS_CHECK_NICKNAME = API_ALLOW_LOGIN + "/u/exist/nickname";
	/** 检查手机号是否存在 **/
	public static final String USERS_CHECK_MOBILE = API_ALLOW_LOGIN + "/u/exist/mobile";
	/** 认证点下一步缓存录入信息 **/
	public static final String USERS_CACHE_INPUT = API_ALLOW_LOGIN + "/u/next/save";
	/** 检查邮箱是否存在 **/
	public static final String USERS_CHECK_EMAIL = API_ALLOW_LOGIN + "/u/exist/email";
	/** 提交认证信息 **/
	public static final String USERS_IDENTIFY = API_ALLOW_LOGIN + "/u/identify";
	/** 服务商列表 **/
	public static final String PROVIDER_LIST_VIEW = API + "/u/providerList";
	/** 服务商关键字筛选 **/
	public static final String PROVIDER_FILTER = API + "/u/providerFilter";
	/** 服务商上架下架 **/
	public static final String PROVIDER_DISPLAY_STATUS = API_ALLOW_LOGIN + "/u/providerDisplay";
	/**服务商删除**/
	public static final String PROVIDER_DELETE = API_ALLOW_LOGIN + "/u/providerDelete";
	/**服务商邀约列表**/
	public static final String PRIVIDER_INVITE_QUERY = API_ALLOW_LOGIN + "/u/invitePprovider";
	/**服务商邀约列表**/
	public static final String SAVE_SELF_PRIVIDER = API_ALLOW_LOGIN + "/u/saveSelfprovider";
	/**更新项目附件**/
	public static final String UPDATE_SELF_ATTACH = API_ALLOW_LOGIN + "/u/updateAttachment";
	/**加载已添加服务商列表**/
	public static final String LOAD_PROJECT_USER = API_ALLOW_LOGIN + "/u/loadProjectUser";
	/**删除已添加角色**/
	public static final String DEL_PROJECT_USER = API_ALLOW_LOGIN + "/u/deleteProjectUser";
	/**保存项目计划**/
	public static final String SAVE_PROJECT_PLAN = API_ALLOW_LOGIN + "/u/saveProjectPlan";
	/**查询项目计划**/
	public static final String LOAD_PROJECT_PLAN = API_ALLOW_LOGIN + "/u/loadProjectPlan";
	/**删除项目计划**/
	public static final String DEL_PROJECT_PLAN = API_ALLOW_LOGIN + "/u/deleteProjectPlan";
	/**编辑项目计划**/
	public static final String EDIT_PROJECT_PLAN = API_ALLOW_LOGIN + "/u/editProjectPlan";
	/**项目计划阶段  ：  提交验收**/
	public static final String VER_PROJECT_PLAN_SUBMIT = API_ALLOW_LOGIN + "/u/submitVerify";
	/**项目计划阶段  ：  验收**/
	public static final String VER_PROJECT_PLAN_CHECK = API_ALLOW_LOGIN + "/u/checkVerify";
	/**新增项目反馈**/
	public static final String SAVE_PROJECT_MONITOR = API_ALLOW_LOGIN + "/u/saveProjectMonitor";
	/**加载项目反馈**/
	public static final String LOAD_PROJECT_MONITOR = API_ALLOW_LOGIN + "/u/loadProjectMonitor";
	/**删除项目反馈**/
	public static final String DEL_PROJECT_MONITOR = API_ALLOW_LOGIN + "/u/deleteProjectMonitor";
	/**修改项目反馈**/
	public static final String EDIT_PROJECT_MONITOR = API_ALLOW_LOGIN + "/u/editProjectMonitor";
	/**加载已参与项目(顾问)**/
	public static final String LOAD_ATTEND_PROJ = API_ALLOW_LOGIN + "/u/loadAttendProject";
	/**顾问备注**/
	public static final String LOAD_JOINER = API_ALLOW_LOGIN + "/u/loadJoinerList";
	/**顾问备选用户**/
	public static final String UPDATE_JOINER = API_ALLOW_LOGIN + "/u/updateJoiner";
	/**项目列表**/
	public static final String LOAD_PROJECT_LIST = API + "/u/loadProjectList";
	/**发起E代测能力测试**/
	public static final String CREATE_EDAICE_EXAM = API_ALLOW_LOGIN + "/u/exam";
	/** 获取历史成绩记录 **/
	public static final String GET_EDAICE_STATUS = API_ALLOW_LOGIN + "/u/exam/history";
	/** 获取用户出账入账记录 **/
	public static final String GET_ACCOUNT_DETAIL = API_ALLOW_LOGIN + "/u/accountdetail";
	/** 用户提现 **/
	public static final String GET_ACCOUNT_CASH = API_ALLOW_LOGIN + "/u/getcash";
	/** 用户更新支付账号**/
	public static final String UPDATE_PAY_ACCOUNT = API_ALLOW_LOGIN + "/u/updateaccount";
	/**获取所有的顾问**/
	public static final String GET_ALL_CONSULTANT = API_ALLOW_ADMIN + "/u/getallconsultants";
	/*
	 * 需求模块
	 */
	/** 新增发标 **/
	public static final String PROJECT_ADD = API_ALLOW_LOGIN + "/p/add";
	/** 发标列表 **/
	public static final String PROJECT_LIST = API + "/p/list";
	/** 所有发标-管理员接口 **/
	public static final String PROJECT_ALL_LIST = API_ALLOW_ADMIN + "/p/list";
	/** 显示我的发标列表 **/
	public static final String PROJECT_PUBLISH_LIST = API_ALLOW_LOGIN + "/list/published";
	/** 显示我的接标列表 **/
	public static final String PROJECT_JOIN_LIST = API_ALLOW_LOGIN + "/list/joined";
	/** 需求基本信息 **/
	public static final String PROJECT_INFO = API_ALLOW_LOGIN + "/p/info";
	/** 参与悬赏竞标 **/
	public static final String PROJECT_BID = API_ALLOW_LOGIN + "/p/join";
	/** 注册数据展现 **/
	@Deprecated
	public static final String PROJECT_COUNT = API + "/p/count";
	/** 选标 **/
	public static final String PROJECT_CHOICE = API_ALLOW_LOGIN + "/p/choice";
	/** 撤销选标 **/
	public static final String PROJECT_CANCEL_CHOICE = API_ALLOW_LOGIN + "/p/select/cancel";
	/** 修改发标信息 **/
	public static final String PROJECT_MODIFY = API_ALLOW_LOGIN + "/p/modify";
	/** 补充发标内容--审核通过后内容只能追加 **/
	public static final String PROJECT_SUPPLEMENT = API_ALLOW_LOGIN + "/p/supplement";
	/** 调整需求估价 **/
	public static final String PROJECT_ADJUSTMENT = API_ALLOW_LOGIN + "/p/adjust/price";
	/** 修改竞标信息 **/
	public static final String PROJECT_BID_MODIFY = API_ALLOW_LOGIN + "/p/join/modify";
	/** 服务商开发完工 **/
	public static final String PROJECT_DONE = API_ALLOW_LOGIN + "/p/done";
	/** 雇主开发验收 **/
	public static final String PROJECT_ACCEPT = API_ALLOW_LOGIN + "/p/accept";
	/** 微信-预约顾问 **/
	public static final String PROJECT_WEIXIN_REQUEST = API + "/wx/request";
	/** 修改竞标备注 **/
	public static final String MODIFY_JOIN_REMARK = API_ALLOW_LOGIN + "/p/remark";
	/** 淘汰竞标 **/
	public static final String KICK_JOIN = API_ALLOW_LOGIN + "/p/join/kick";
	/** 取消淘汰竞标 **/
	public static final String KICK_JOIN_CANCEL = API_ALLOW_LOGIN + "/p/join/cancelKick";
	/** 竞标延期 **/
	public static final String BID_EXTENSION = API_ALLOW_LOGIN + "/p/bid/extension";
	/** 推送项目信息 **/
	public static final String PUSH_PROJECT_INFO = API_ALLOW_LOGIN + "/p/push/project";
	/** 开发者参与推送的项目 **/
	public static final String JOIN_PROJECT_SELF = API_ALLOW_LOGIN + "/p/join/projectSelf";
	/** 项目推送统计列表 **/
	public static final String PUSH_STATUS_LIST = API_ALLOW_LOGIN + "/p/push/list";
	/** 服务商可参与项目列表 **/
	public static final String INVITE_LIST = API_ALLOW_LOGIN + "/u/invite/list";
	/** 服务商已参与项目列表 **/
	public static final String JOIN_LIST = API_ALLOW_LOGIN + "/u/join/list";
	/** 我发布的项目列表 **/
	public static final String REQUEST_LIST = API_ALLOW_LOGIN + "/u/request/list";
	/** 查询项目可用剩余金额 **/
	public static final String AVALID_PRICE = API_ALLOW_LOGIN + "/u/avalidprice";
	
	/*
	 * 评价服务
	 */
	/** 新增评价 **/
	public static final String EVALUATE_ADD = API_ALLOW_LOGIN + "/p/eval";
	/** 服务商评价列表 **/
	public static final String EVALUATE_ALL_LIST = API + "/eval/list";
	/** 当前项目服务评价 **/
	public static final String EVALUATE_ITEM = API + "/eval";
	/** 相似项目 **/
	public static final String EVALUATE_SIMILAR = API + "/same";
	/*
	 * 后台管理
	 */
	/** 审核通过 **/
	public static final String CHECK_PASS = API_ALLOW_ADMIN + "/p/pass";
	/** 审核不通过 **/
	public static final String CHECK_INVALID = API_ALLOW_ADMIN + "/p/reject";
	/** 预约顾问列表 **/
	public static final String RESERVE_LIST = API_ALLOW_ADMIN + "/p/reserve";
	/** 需求审核页面 **/
	public static final String CHECK_VIEW = API_ALLOW_ADMIN + "/p/view";
	/** 资金托管确认 **/
	public static final String CHECK_TRUSTEE = API_ALLOW_ADMIN + "/p/trustee";
	/** 确认工作完成，管理员审核付款 **/
	public static final String CHECK_FINISH = API_ALLOW_ADMIN + "/p/finish";
	/** 用户列表 **/
	public static final String ADMIN_USER_LIST = API_ALLOW_ADMIN + "/u/list";
	/** 认证审核通过 **/
	public static final String IDENTIFY_PASS = API_ALLOW_ADMIN + "/identify/pass";
	/** 管理员认证录入简历信息 **/
	public static final String IDENTIFY_RESUME_INPUT = API_ALLOW_ADMIN + "/identify/resume";
	/** 认证审核不通过 **/
	public static final String IDENTIFY_INVALID = API_ALLOW_ADMIN + "/identify/reject";
	/** 跳转认证审核页面 **/
	public static final String IDENTIFY_CHECK = API_ALLOW_ADMIN + "/idntify/view";
	/** 管理员给服务商备注 **/
	public static final String MEMBER_REMARK = API_ALLOW_ADMIN + "/member/remark";
	/** 管理员查询用户接口 **/
	public static final String QUERY_USERS = API_ALLOW_ADMIN + "/u/query";
	/** 管理员修改服务商信息接口 **/
	public static final String MEMBER_MODIFY = API_ALLOW_ADMIN + "/member/modify";
	/** 管理员查询服务商信息接口 **/
	public static final String MEMBER_QUERY = API_ALLOW_ADMIN + "/member/view";
	/** 预约洽谈/关闭修改 **/
	public static final String RESERVE_MODIFY = API_ALLOW_ADMIN + "/reserve/modify";
	/** 预约项目发布 **/
	public static final String RESERVE_PUBLISH = API_ALLOW_ADMIN + "/reserve/publish";
	/** 关闭项目 **/
	public static final String CLOSE_PROJECT = API_ALLOW_ADMIN + "/p/close";
	/** 调整项目排序 **/
	public static final String RANKING_PROJECT = API_ALLOW_ADMIN + "/p/ranking";
	/** 项目软删除 **/
	public static final String DELETE_PROJECT = API_ALLOW_ADMIN + "/p/delete";
	/** 用户导入列表信息 **/
	public static final String USER_IMPORT_LIST = API_ALLOW_ADMIN + "/u/userImportList";
	/** 码客库查询 **/
	public static final String QUERY_DEVELOPER = API_ALLOW_ADMIN + "/u/developer/list";
	/** 查询导入用户信息 **/
	public static final String USER_IMPORT_INFO = API_ALLOW_ADMIN + "/u/userImportInfo";
	/** 查询新增用户数 **/
	public static final String LOAD_ANYLIZE_DATA = API_ALLOW_ADMIN + "/u/loadAnylizeData";
	/**群发邮件**/
	public static final String SEND_MAIL_BATCH = API_ALLOW_ADMIN + "/u/emailsend";
	/**项目审核**/
	public static final String REQUEST_REVIEW = API_ALLOW_ADMIN + "/p/review";
	/**代码托管页面代理**/
	public static final String CODE_PAGE_PROXY = API_ALLOW_LOGIN + "/capi/proxy";
	/**代码托管页面代理**/
	public static final String VIEW_FILE = API_ALLOW_LOGIN + "/capi/viewfile";
	/**中断**/
	public static final String PAGE_ABORT = API_ALLOW_LOGIN + "/capi/abort";
	/** 同步E代测试卷 **/
	public static final String SYNC_EDAICE_PAPERS = API_ALLOW_ADMIN + "/sync/paper";
	public static final String LOAD_PROJECT_LIST_4_ADMIN = API_ALLOW_ADMIN + "/p/loadProjectList";
	
	/*
	 * 基础数据
	 */
	/** 获取所有省份 **/
	public static final String REGION_LIST = API + "/region/list";
	/** 根据省查找下属市 **/
	public static final String REGION_CITYS = API + "/region/citys";
	/** 查询领域列表 **/
	public static final String TAG_CASETYPE = API + "/tag/caseType";
	/** 查询所有友链 **/
	public static final String FLINK_LIST = API + "/flink/list";
	/** 新增友链 **/
	public static final String FLINK_ADD = API_ALLOW_ADMIN + "/flink/add";
	/** 删除友链 **/
	public static final String FLINK_DEL = API_ALLOW_ADMIN + "/flink/del";
	/** 修改友链 **/
	public static final String FLINK_MODIFY = API_ALLOW_ADMIN + "/flink/modify";
	/** 友链排序 **/
	public static final String FLINK_ORDER = API_ALLOW_ADMIN + "/flink/order";
	/*
	 * 公共
	 */
	/** 获取图片验证码 **/
	public static final String COMMON_CAPTCHA_IMG = API + "/common/captcha";
	/** Ajax校验图片验证码 **/
	public static final String CAPTCHA_IMG_CHECK = API + "/common/captcha/check";
	/** 发送短信验证码 **/
	public static final String COMMON_SEND_SMS_VCODE = API + "/common/sendMobileVerifyCode2";
	/** 服务器时间 **/
	public static final String COMMON_SERVER_TIME = API + "/common/time";
	/** 首页随机展现认证服务商和项目 **/
	public static final String COMMON_INDEX_RECOMMEND = API + "/common/recommend";
	/** 发送邮箱验证码 **/
	public static final String SEND_MAIL_VCODE = API + "/mail/validate";
	/** 获取所有标签信息 **/
	public static final String COMMON_LABEL_LIST = API + "/label/list";
	
	/**
	 * 自营项目
	 */
	public static final String PROJECTINSELFRUN_ADD = API + "/selfrun/p/add";
	public static final String PROJECTINSELFRUN_GET = API + "/selfrun/p/get";
	/** 项目转让**/
	public static final String PROJECTINSELFRUN_ASSIGN = API_ALLOW_ADMIN + "/selfrun/p/assign";
	/** 项目报名列表 **/
	public static final String PROJECTINSELFRUN_ENROLL_LIST = API_ALLOW_LOGIN + "/selfrun/p/enroll/list";
	/** 项目推送列表**/
	public static final String PROJECTINSELFRUN_PUSH_LIST = API_ALLOW_LOGIN + "/selfrun/p/push/list";
	/** 添加开发者 **/
	public static final String PROJECTINSELFRUN_ADD_DEV = API_ALLOW_LOGIN + "/selfrun/p/adddev";
	/** 删除开发者 **/
	public static final String PROJECTINSELFRUN_DELETE_DEV = API_ALLOW_LOGIN + "/selfrun/p/deletedev";
	/** 项目进入开发状态**/
	public static final String PROJECTINSELFRUN_GOTO_DEV_STAUTS = API_ALLOW_LOGIN + "/selfrun/p/go2dev";
	/** 项目进入完成状态**/
	public static final String PROJECTINSELFRUN_GOTO_COMPLETE_STAUTS = API_ALLOW_LOGIN + "/selfrun/p/go2complete";
	
	/*
	 * 微信 
	 */
	/**微信口令校验**/
	public static final String WEIXIN_SIGN = API + "/weixin/sign";
	
	/**
	 * 新闻
	 */
	public static final String NEWS_GET_LATEST = API + "/news/getlatest";
	
	/**
	 * 项目日志模块
	 */
	public static final String PROJECT_OPERATION_LOG_ADD = API_ALLOW_LOGIN + "/p/log/add";
	public static final String PROJECT_OPERATION_LOG_MODIFY = API_ALLOW_LOGIN + "/p/log/modify";
	public static final String PROJECT_OPERATION_LOG_DELETE = API_ALLOW_LOGIN + "/p/log/delete";
}
