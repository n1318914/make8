package com.yundaren.common.constants;

public class CommonConstants {
	public static final int ERROR_CODE_SUCCESS = 0;
	public static final int ERROR_CODE_FAILED = 1;

	public static final String SESSION_LOGIN_USER = "SESSION_LOGIN_USER";
	public static final String REFERER_URL = "REFERER_URL";
	/** 用户信息修改中的操作，用于前台定位TAB页 (0代表基本信息 1代表认证信息) **/
	public static final String USERINFO_OPERATION = "USERINFO_OPERATION";
	
	/** 是否修改用户认证，用于前台展现认证页面  **/
	public static final String IS_IDENTIFY_MODIFY = "IS_IDENTIFY_MODIFY";
	
	/** 身份证图片 type=1 **/
	public static final String UPLOAD_IMG_SFZ = "UPLOAD_IMG_SFZ";
	/** 组织机构图片 type=2 **/
	public static final String UPLOAD_IMG_ZZJG = "UPLOAD_IMG_ZZJG";
	/** 税务登记图片 type=3**/
	public static final String UPLOAD_IMG_SWDJ = "UPLOAD_IMG_SWDJ";
	/** 营业执照图片 type=4**/
	public static final String UPLOAD_IMG_YYZZ = "UPLOAD_IMG_YYZZ";
	/** 公司图片 type=5**/
	public static final String UPLOAD_IMG_CMPIC = "UPLOAD_IMG_CMPIC";
	/** 友链LOGO图片 type=7 **/
	public static final String UPLOAD_IMG_FLINK_LOGO = "UPLOAD_IMG_FLINK_LOGO";
	
	/** 项目附件 type=1 **/
	public static final String FILE_PROJECT_ATTACHMENT = "FILE_PROJECT_ATTACHMENT";
	/** 个人简历 type=3 **/
	public static final String FILE_RESUME_ATTACHMENT = "FILE_RESUME_ATTACHMENT";

	// 默认管理员账号
	public static final String DEFAULT_ADMIN = "admin@yundaren.com";
	
	//用户登录次数
	public static final String USER_LOGIN_FAILED_TIMES = "USER_LOGIN_FAILED_TIMES";
	
	//选取顾问的序号
	public static final String CURRENT_CONSULTANT_INDEX = "CURRENT_CONSULTANT_INDEX";
	
	/** 未读消息 **/
	public static final String SESSION_NOT_READ_MSG = "canJoinNum";
	
	// 数据字典分类名称
	public static final String PROJECT_IN_SELF_RUN_TYPE_TAG = "projectType";
	public static final String PROJECT_IN_SELF_RUN_BUDGET_TAG = "projectBudget";
	public static final String PROJECT_IN_SELF_RUN_STARTTIME_TAG = "projectStartTime";
	public static final String PROJECT_IN_SELF_RUN_DEV_ROLE = "projectDevRole";
	public static final String PLAN_STATUS = "planStatus";
	
	public static final String SERVICE_FIELD = "caseType";
	public static final String FAVORITE_SKILL = "ability";
	public static final String FAVORITE_JOB = "cando";
	
	//自营项目
	public static final String PROJECT_IN_SELF_CREATE_FLAG = "projectInSelfCreateFlag";
	public static final String PROJECT_IN_SELF_CREATE_ID = "projectInSelfCreateID";
	public static final String PROJECT_IN_SELF_CREATE_FAILED_MSG = "projectInSelfCreateFailedMsg";
	public static final String PROJECT_IN_SELF_CREATE_LOGIN_NAME = "projectInSelfCreateLoginName";
	
	//个人认证信息修改操作
	public static final String USER_IDENTIFY_MODIFICATION_TAG = "userIdentifyModificationTag";
	
	//GOGS返回码
	/**登录**/
	public static final String LOGIN_NO_STATUS = "0";
	public static final String LOGIN_SUCCESS = "1";
	public static final String LOGIN_FAIL = "-1";
	public static final String LOGIN_USER_NOT_EXIST = "-2";
	/**注册**/
	public static final String REGISTER_SUCCESS = "1";
	public static final String REGISTER_ERR = "-1";
	public static final String REGISTER_USER_NAME_EXIST = "-2";
	public static final String REGISTER_USER_EMAIL_EXIST = "-3";
	public static final String REGISTER_USER_NAME_RESERVED = "-4";
	public static final String REGISTER_USER_NAME_PATTERN_ERR = "-5";
	public static final String REGISTER_USER_PWD_RETYPE_ERR = "-6";
	/**创建仓库**/
	public static final String CREATE_REPO_SUCCESS = "1";
	public static final String CREATE_REPO_FAIL = "-1";
	/**仓库成员分配**/
	public static final String COLLABORATION_SUCCESS = "1";
	public static final String COLLABORATION_FAIL = "-1";
	/**删除成员**/
	public static final String COLLABORATION_DELETE = "1";
	/**修改密码**/
	public static final String RESET_PASSWORD_SUCCESS = "1";
	public static final String RESET_PASSWORD_FAIL = "-1";
	/**修改信息**/
	public static final String UPDATE_INFO_SUCCESS = "1";
	public static final String UPDATE_INFO_FAIL = "-1";
}
