package com.yundaren.support.config;

import lombok.Data;

@Data
public class YunConnectConfig {
	// 云之讯URL
	private String remoteURL;
	// 应用id串
	private String appId;
	// 主账号id
	private String accountSid;
	// 云之讯token
	private String token;
	// 云之讯短信模板ID
	private String templateSMSId;;

	// 预约顾问短信通知
	private String reserveAppId;
	private String reserveTempSMSId;

	// 审核通过短信通知
	private String checkPassAppId;
	private String checkPassTempSMSId;

	// 审核拒绝短信通知
	private String checkRejectAppId;
	private String checkRejectTempSMSId;
	
	// 开发申请验证
	private String applyVerifyAppId;
	private String applyVerifyTempSMSId;
	
	// 雇主验收通过
	private String veridatePassAppId;
	private String veridatePassTempSMSId;
	
	// 雇主验收驳回
	private String veridateRejectAppId;
	private String veridateRejectTempSMSId;
}
