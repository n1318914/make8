package com.yundaren.user.vo;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.yundaren.support.vo.IdentifyVo;

import lombok.Data;

@Data
public class UserInfoVo {
	// 用户ID
	private long id;
	// 姓名
	private String name;
	// 显示姓名--缩略
	private String displayName;
	// 性别(0女1男)
	private int sex;
	// 出生日期
	private String birthday;
	// 手机
	private String mobile;
	// 行业
	private String industry;
	// 邮箱
	private String email;
	// 显示邮箱--缩略
	private String displayEmail;
	// 简介
	private String introduction;
	// 所在城市ID
	private long regionId;
	private long provinceId;
	// 头像
	private String headPortrait;
	// 更新时间
	private Date updateTime = new Date();
	// 是否已认证0否1是
	private int isApprove;
	// 文件访问密匙
	private String fileSecretKey;
	// 用户类型 (-1管理员、0雇主、1服务商、2顾问、3管理员创建的无账号用户)
	private int userType = -2;

	// 擅长技术
	private String mainAbility;
	// 显示擅长技术 
	private String[] displayMainAbility;
	// 其他技术
	private String otherAbility;
	// 银行账户
	private String bankAccount;

	// 所在地
	private String region;

	// 短信验证码
	private String vcode;

	// 备注
	private String remark;

	// 用户认证信息
	private IdentifyVo identifyInfo;
	// 历史评价分数
	private double avgScore;

	// 认证状态
	private int identifyStatus = -1;
	// 认证类型
	private int identifyCategory = -1;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime = new Date();
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date identifyTime;
	private Date lastLoginTime;
	private String qq;
	private String weixin;

	// 公司规模
	private String companySize;
	// 能胜任的工作(关联数据字典表，用逗号分隔)
	private String cando;
	// 显示能胜任的工作
	private String[] displayCando;
	// 公司主营项目类型(关联数据字典表，用逗号分隔)
	private String caseType;
	// 显示公司主营项目类型
	private String[] displayCaseType;
	// 公司主营项目类型_其他
	private String otherCaseType;
	// 是否上架服务商列表展现(0否1是)
	private int isDisply;
	// 个人服务商全职状态(0兼职、1自由职业者、2在校学生)
	private int freelanceType = -1;

	private List<EmployeeEduExperienceVo> employeeEduExperience;
	private List<EmployeeJobExperienceVo> employeeJobExperience;
	private List<EmployeeProductVo> employeeProduct;
	private List<EmployeeTeamProjectExperienceVo> employeeProjectExperience;
	private String comPicList;
	private String[] comPicListContainer;

	// 公司名称
	private String companyName;
	// 公司地址
	private String companyAddr;
	// 公司官网
	private String siteLink;
	//删除标志
	private int isDelete;
	//类别
	private int category;
	//角色
	private int role;
	//个人简历URL
	private String resumeUrl;
	//简历类型(file link input)
	private String resumeType;
	
	//认证保存到第几步
	private int identifyStep = 1;
	
	private int isGogs;
	
	//报名项目次数
	private int joinCount;
	
	//项目选中次数
	private int chosenCount;
	
	//支付账号id
	private int accountId = -1;
	//支付账号名
	private String accountNum;
}
