package com.yundaren.support.service;

import java.util.List;

import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.util.DateUtil;
import com.yundaren.common.util.EmailSender;
import com.yundaren.support.config.EmailSendConfig;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.ProjectJoinVo;
import com.yundaren.support.vo.ProjectVo;
import com.yundaren.support.vo.WeixinProjectVo;
import com.yundaren.user.vo.UserInfoVo;

/**
 * 
 * @author kai.xu Project邮件服务
 */
public interface ProjectMailService {

	/**
	 * 项目审核结果知会雇主
	 */
	void sendCheckResultMail(String projectId);
	
	/**
	 * 服务商竞标通知发标人
	 */
	void sendBidMailContent(ProjectJoinVo projectJoinVo);

	/**
	 * 发标人选标后通知竞标人
	 */
	void sendChoiceMailContent(String projectId);

	/**
	 * 发标人取消选标后通知竞标人
	 */
	void sendCancelMailContent(String projectId);

	/**
	 * 发标人托管资金后通知竞标人
	 */
	void sendTrusteeMailContent(String projectId);
	
	/**
	 * 发标人托管资金后通知发标人已确认收到款
	 */
	void sendTrusteeOKMail(String projectId);

	/**
	 * 通知管理员审核邮件
	 */
	void sendNoticeMailContent(ProjectVo projectVo);
	
	/**
	 * 服务商完工通知雇主
	 */
	void sendWorkingDone(String projectId);
	
	/**
	 * 雇主验收结果通知服务商
	 */
	void sendAcceptResultMail(String projectId);
	
	/**
	 * 管理员审核付款通知服务商
	 */
	void sendFinishMail(String projectId);
	
	/**
	 * 审核付款完成通知雇主评价服务商
	 */
	void sendEvaluateMail(String projectId);
	
	/**
	 * 评价完成通知服务商
	 */
	void sendEvaluateResultMail(String projectId);
	
	/**
	 * 微信找外包通知管理员处理
	 */
	void sendWeixinRequestMail(WeixinProjectVo weixinProjectVo);

	/**
	 * 雇主发布项目后推送所有服务商
	 */
	void send2AllMembers(String projectId);

	/**
	 * 服务商申请认证通知管理员审核
	 */
	void sendRequestIdentify(IdentifyVo identifyVo);

	/**
	 * 管理员认证结果通知服务商
	 */
	void sendIdentifyResult(IdentifyVo identifyVo);
	
	/**
	 * 项目竞标即将到期邮件通知雇主
	 */
	void sendBidTimeoutNotice(List<ProjectVo> listProject);

	/**
	 * 雇主补充项目内容通知所有参与竞标的服务商
	 */
	void sendSupplementNoticeEmployee(String projectId, String supppleContent);

	/**
	 * 雇主调整项目预算通知所有参与竞标的服务商
	 */
	void sendAdjustPriceNoticeEmployee(ProjectVo projectVo, String newPriceRange);
	
	/**
	 * 推送项目给开发者
	 */
	void pushProjectSelf2Developer(String chooseMailStr, String projectId);
	
	/**
	 * 自营项目通知管理员
	 */
	void sendSelfProjectNoticeMail2Admin(String projectId,ProjectInSelfRunVo projectVo);
	
	/**
	 * 项目报名推送管理员
	 */
	void sendJoinMail2Admin(String projectId, UserInfoVo userInfo);
	
	/**
	 * 选择开发者项目报名列表
	 */
	void sendAddChooseDeveloper(UserInfoVo userInfo,ProjectInSelfRunVo projectInSelfRunVo);
	
	/**
	 * 移除开发者项目报名列表
	 */
	void sendRemoveChoosenDeveloper(UserInfoVo userInfo,ProjectInSelfRunVo projectInSelfRunVo);
	
	/**
	 * 项目计划：提交验收
	 */
	void sendSubmitVerify(UserInfoVo userInfo,ProjectInSelfRunVo projectInSelfRunVo,int step);
	
	/**
	 * 项目计划：验收
	 */
	void sendCheckVerify(UserInfoVo userInfo,ProjectInSelfRunVo projectInSelfRunVo,Boolean isPass,int step);
	
	/**
	 * 项目关闭：通知
	 */
	void sendProjectClose(List<String> memberEmails,ProjectInSelfRunVo projectVo,String reason);
	
	/**
	 * 项目启动：通知违背选中的开发者
	 */
	void sendNotChoosen(List<String> memberEmails,ProjectInSelfRunVo projectVo);
}
