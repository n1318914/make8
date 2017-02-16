package com.yundaren.support.po;

import java.util.Date;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class IdentifyPo {

	// 用户ID
	private long userId;
	// 真实姓名
	private String realName;
	// 身份证号码
	private String idCard;
	// 手持身份证图片
	private String idCardImg;
	// 公司名称
	private String companyName;
	// 公司地址
	private String companyAddr;
	// 公司电话
	private String companyPhone;
	// 组织机构号
	private String organization;
	// 组织机构号图片
	private String organizationImg;
	// 税务登记号
	private String tax;
	// 税务登记号图片
	private String taxImg;
	// 营业执照号
	private String businessLicense;
	// 营业执照号图片
	private String businessLicenseImg;
	// 法人代表
	private String legalRepresent;
	// 认证状态：0--等待审核; 1--通过认证；2--认证被驳回 3--审核中修改  4--认证完修改
	private int status = -1;
	// 认证类别：0--个人认证; 1--企业认证
	private int category = -1;
	// 认证不通过原因
	private String failReason;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime = new Date();
	// 文件访问密匙
	private String fileSecretKey;
	// 服务商（企业/个人）网站
	private String siteLink;
	private Date passTime;
	
	//用户支付账号id
	private int accountId;
}
