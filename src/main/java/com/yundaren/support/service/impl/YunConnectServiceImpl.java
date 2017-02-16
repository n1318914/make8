package com.yundaren.support.service.impl;

import java.util.Map;
import java.util.Random;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.yundaren.cache.VerificationCodeCache;
import com.yundaren.common.util.YunzhixunUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.PublicConfig;
import com.yundaren.support.config.YunConnectConfig;
import com.yundaren.support.service.YunConnectService;

@Slf4j
public class YunConnectServiceImpl implements YunConnectService {
	private static final String SEND_FAILED_MSG = "发送短信失败，请稍后再试！";

	@Setter
	private YunConnectConfig yunConnectConfig;

	@Override
	public String sendTemplateSMS(String mobile) {
		// TODO 需要控制发送频率

		String msg = "";
		if (DomainConfig.getIsProduceEnvironment()) {

			// 生产随机验证码
			String param = String.valueOf(new Random().nextInt(9000) + 1000);
			// 发送短信
			String rsultJson = YunzhixunUtil.sendTemplateSMS(yunConnectConfig.getRemoteURL(),
					yunConnectConfig.getAccountSid(), yunConnectConfig.getToken(),
					yunConnectConfig.getAppId(), yunConnectConfig.getTemplateSMSId(), mobile, param);
			msg = checkResult(rsultJson);
			// 缓存验证码
			VerificationCodeCache.getInstance().add(mobile, param);
		} else {
			// 测试环境手机验证码固定为1111
			VerificationCodeCache.getInstance().add(mobile, "1111");
		}
		return msg;
	}

	@Override
	public String sendRecomendMemberSMS(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendReserveSMS(String mobile) {
		String msg = "";
		if (DomainConfig.getIsProduceEnvironment()) {

			// 客服联系方式
			String param = PublicConfig.getCustomerServiceNumber();
			// 发送短信
			String rsultJson = YunzhixunUtil
					.sendTemplateSMS(yunConnectConfig.getRemoteURL(), yunConnectConfig.getAccountSid(),
							yunConnectConfig.getToken(), yunConnectConfig.getReserveAppId(),
							yunConnectConfig.getReserveTempSMSId(), mobile, param);
			msg = checkResult(rsultJson);
		}
		return msg;
	}

	@Override
	public String sendCheckPassSMS(String mobile, String projectName) {
		String msg = "";
		if (DomainConfig.getIsProduceEnvironment()) {

			// 发送短信
			String rsultJson = YunzhixunUtil.sendTemplateSMS(yunConnectConfig.getRemoteURL(),
					yunConnectConfig.getAccountSid(), yunConnectConfig.getToken(),
					yunConnectConfig.getCheckPassAppId(), yunConnectConfig.getCheckPassTempSMSId(), mobile,
					projectName);
			msg = checkResult(rsultJson);
		}
		return msg;
	}

	@Override
	public String sendCheckRejectSMS(String mobile, String projectName) {
		String msg = "";
		if (DomainConfig.getIsProduceEnvironment()) {

			// 发送短信
			String rsultJson = YunzhixunUtil.sendTemplateSMS(yunConnectConfig.getRemoteURL(),
					yunConnectConfig.getAccountSid(), yunConnectConfig.getToken(),
					yunConnectConfig.getCheckRejectAppId(), yunConnectConfig.getCheckRejectTempSMSId(),
					mobile, projectName);
			msg = checkResult(rsultJson);
		}
		return msg;
	}

	private String checkResult(String resultJson) {
		// 如果结果为空
		if (StringUtils.isEmpty(resultJson)) {
			return SEND_FAILED_MSG;
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj = JSONObject.fromObject(resultJson);
		if (!jsonObj.containsKey("resp")) {
			log.error("error mesg:" + resultJson);
			return SEND_FAILED_MSG;
		}
		String resultCode = jsonObj.getJSONObject("resp").getString("respCode");
		if (!"000000".equals(resultCode)) {
			log.error("error mesg:" + resultJson);
			return SEND_FAILED_MSG;
		}
		return "";
	}
	@Override
	public String sendApplyVerifySMS(String mobile, String projectName) {
		String msg = "";
		if (DomainConfig.getIsProduceEnvironment()) {

			// 发送短信
			String rsultJson = YunzhixunUtil.sendTemplateSMS(yunConnectConfig.getRemoteURL(),
					yunConnectConfig.getAccountSid(), yunConnectConfig.getToken(),
					yunConnectConfig.getApplyVerifyAppId(), yunConnectConfig.getApplyVerifyTempSMSId(),
					mobile, projectName);
			msg = checkResult(rsultJson);
		}
		return msg;
	}
	
	@Override
	public String sendVeridatePassSMS(String mobile, String projectName) {
		String msg = "";
		if (DomainConfig.getIsProduceEnvironment()) {

			// 发送短信
			String rsultJson = YunzhixunUtil.sendTemplateSMS(yunConnectConfig.getRemoteURL(),
					yunConnectConfig.getAccountSid(), yunConnectConfig.getToken(),
					yunConnectConfig.getVeridatePassAppId(), yunConnectConfig.getVeridatePassTempSMSId(),
					mobile, projectName);
			msg = checkResult(rsultJson);
		}
		return msg;
	}
	
	@Override
	public String sendVridateRejectSMS(String mobile, String projectName) {
		String msg = "";
		if (DomainConfig.getIsProduceEnvironment()) {

			// 发送短信
			String rsultJson = YunzhixunUtil.sendTemplateSMS(yunConnectConfig.getRemoteURL(),
					yunConnectConfig.getAccountSid(), yunConnectConfig.getToken(),
					yunConnectConfig.getVeridateRejectAppId(), yunConnectConfig.getVeridateRejectTempSMSId(),
					mobile, projectName);
			msg = checkResult(rsultJson);
		}
		return msg;
	}

	
	public static void main(String[] args) {
		YunConnectServiceImpl yunService = new YunConnectServiceImpl();
		YunConnectConfig yunConnectConfig = new YunConnectConfig();
		yunConnectConfig.setRemoteURL("https://api.ucpaas.com/2014-06-30");
		yunConnectConfig.setToken("916c59072108a2eb9e03454f357ab927");
		yunConnectConfig.setAccountSid("6f9a887f31f0e63ef7d7d85636dc45ca");
		//申请验收
		yunConnectConfig.setApplyVerifyAppId("1af0f18d5a4e43a4acf51249557b891e");
		yunConnectConfig.setApplyVerifyTempSMSId("25855");
		
		//
		yunConnectConfig.setVeridatePassAppId("45cc904a8010439cb054b4756411925f");
		yunConnectConfig.setVeridatePassTempSMSId("25858");
		//
		yunConnectConfig.setVeridateRejectAppId("895979ca4e87488fbd14ca591dca3d12");
		yunConnectConfig.setVeridateRejectTempSMSId("25859");
		
		yunService.setYunConnectConfig(yunConnectConfig);
		
		yunService.sendApplyVerifySMS("13113045630", "测试项目");
		yunService.sendVeridatePassSMS("13113045630", "测试项目");
		yunService.sendVridateRejectSMS("13113045630", "测试项目");
	}


}
