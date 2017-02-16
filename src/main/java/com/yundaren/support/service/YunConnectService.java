package com.yundaren.support.service;

/**
 * 
 * @author kai.xu 云之讯平台服务
 */
public interface YunConnectService {

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 *            接收短信的手机号
	 * @return
	 */
	public String sendTemplateSMS(String mobile);

	/**
	 * 后台管理员发送推荐服务商短信
	 * 
	 * @param mobile
	 *            接收短信的手机号
	 * @return
	 */
	public String sendRecomendMemberSMS(String mobile);
	
	public String sendReserveSMS(String mobile) ;

	/**
	 * 审核成功短信通知
	 * 
	 * @param mobile
	 *            接收短信的手机号
	 * @return
	 */
	public String sendCheckPassSMS(String mobile, String projectName);

	/**
	 * 审核驳回短信通知
	 * 
	 * @param mobile
	 *            接收短信的手机号
	 * @return
	 */
	public String sendCheckRejectSMS(String mobile, String projectName);
	
	/**
	 * 开发者提交项目验证请求
	 * 
	 * @param mobile
	 * @param projectName
	 * @return
	 */
	public String sendApplyVerifySMS(String mobile, String projectName);
	
	/**
	 * 雇主审核验证通过
	 * 
	 * @param mobile
	 * @param projectName
	 * @param isPass
	 * @return
	 */
	public String sendVeridatePassSMS(String mobile, String projectName);
	
	/**
	 * 雇主审核验证驳回
	 * 
	 * @param mobile
	 * @param projectName
	 * @return
	 */
	public String sendVridateRejectSMS(String mobile, String projectName);
}
