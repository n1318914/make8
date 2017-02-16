package com.yundaren.support.service.impl;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.ProjectSelfStatusEnum;
import com.yundaren.common.constants.ProjectStatusEnum;
import com.yundaren.common.constants.UrlRewriteConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.DateUtil;
import com.yundaren.common.util.EmailSender;
import com.yundaren.common.util.EmailTemplateUtil;
import com.yundaren.common.util.LabelUtil;
import com.yundaren.support.biz.IdentifyBiz;
import com.yundaren.support.biz.ProjectBiz;
import com.yundaren.support.biz.ProjectEvaluateBiz;
import com.yundaren.support.biz.ProjectInSelfRunBiz;
import com.yundaren.support.biz.TrusteeBiz;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.EmailSendConfig;
import com.yundaren.support.service.ProjectMailService;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.support.vo.ProjectEvaluateVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.ProjectJoinVo;
import com.yundaren.support.vo.ProjectVo;
import com.yundaren.support.vo.WeixinProjectVo;
import com.yundaren.user.biz.UserBiz;
import com.yundaren.user.vo.UserInfoVo;

import freemarker.template.Template;

@Slf4j
public class ProjectMailServiceImpl implements ProjectMailService {

	@Setter
	private ProjectBiz projectBiz;

	@Setter
	private UserBiz userBiz;

	@Setter
	private TrusteeBiz trusteeBiz;

	@Setter
	private IdentifyBiz identifyBiz;

	@Setter
	private ProjectInSelfRunBiz projectInSelfRunBiz;

	@Setter
	private ProjectEvaluateBiz projectEvaluateBiz;

	@Setter
	private EmailSendConfig emailSendConfig;

	// 审核结果邮件通知
	@Override
	public void sendCheckResultMail(String projectId) {
		ProjectInSelfRunVo projectVo = projectInSelfRunBiz.getProjectInSelfRunById(projectId);
		// 发标人信息
		UserInfoVo publisher = userBiz.getUserInfoByID(projectVo.getCreatorId());
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
		String title = "";
		StringBuffer sb = new StringBuffer();
		// 审核未通过
		if (projectVo.getStatus() == ProjectSelfStatusEnum.STATUS_NOTPASSED) {
			sb.append("<p>尊敬的会员，您发起的项目《" + projectVo.getName() + "》，审核暂未通过。</p>");
			sb.append("<p><strong>未通过原因：</strong></p>");
			sb.append("<p>" + projectVo.getCheckResult() + "</p>");
			sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
			title = "[码客帮]项目审核未通过";
		} else {
			sb.append("<p>尊敬的会员，您发起的项目《" + projectVo.getName() + "》，审核已经通过，码客帮会及时给您处理！</p>");
			sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
			title = "[码客帮]项目审核已通过";
		}
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(publisher.getEmail());
		EmailSender.send(mailConfig, title, sb.toString());
	}

	// 服务商竞标通知发标人
	@Override
	public void sendBidMailContent(ProjectJoinVo projectJoinVo) {
		UserInfoVo joiner = projectJoinVo.getUserInfo();
		ProjectVo projectInfo = projectBiz.getProjectById(projectJoinVo.getProjectId());
		// 发标人信息
		UserInfoVo publisher = userBiz.getUserInfoByID(projectInfo.getCreatorId());
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS
				+ projectJoinVo.getProjectId();
		StringBuffer sb = new StringBuffer();
		sb.append("<p>尊敬的会员，您发布的《" + projectInfo.getName() + "》项目，有外包服务商参与了竞标。</p>");
		sb.append("<p></p>");
		sb.append("<p><strong>服务商名称：</strong>" + joiner.getName() + "</p>");
		sb.append("<p><strong>竞标报价：</strong>" + projectJoinVo.getPrice() + "元\t<strong>周期：</strong>"
				+ projectJoinVo.getPeriod() + "天</p>");
		sb.append("<p><strong>Email：</strong>" + joiner.getEmail() + "\t<strong>电话：</strong>"
				+ joiner.getMobile() + "</p>");
		sb.append("<p></p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");

		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(publisher.getEmail());
		EmailSender.send(mailConfig, "[码客帮]您的项目有新竞标", sb.toString());
	}

	// 发标人选标后通知竞标人
	@Override
	public void sendChoiceMailContent(String projectId) {
		ProjectVo projectVo = projectBiz.getProjectById(projectId);
		// 发标人信息
		UserInfoVo publisher = userBiz.getUserInfoByID(projectVo.getCreatorId());
		// 竞标信息
		ProjectJoinVo selectJoinVo = projectBiz.getSelectedJoinInfo(projectId);
		UserInfoVo joiner = userBiz.getUserInfoByID(selectJoinVo.getUserId());
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
		StringBuffer sb = new StringBuffer();
		sb.append("<p>尊敬的会员，您参与竞标的项目《" + projectVo.getName() + "》已成功中标，等待雇主托管项目款。</p>");
		sb.append("<p></p>");
		sb.append("<p><strong>发标方名称：</strong>" + publisher.getName() + "</p>");
		sb.append("<p><strong>Email：</strong>" + publisher.getEmail() + "\t<strong>电话：</strong>"
				+ publisher.getMobile() + "</p>");
		sb.append("<p><strong>项目总价：</strong>" + selectJoinVo.getPrice() + "元\t<strong>交付周期：</strong>"
				+ selectJoinVo.getPeriod() + "天</p>");
		sb.append("<p></p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");

		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(joiner.getEmail());
		EmailSender.send(mailConfig, "[码客帮]您有竞标成功的项目", sb.toString());
	}

	// 发标人取消选标后通知竞标人
	@Override
	public void sendCancelMailContent(String projectId) {
		ProjectVo projectVo = projectBiz.getProjectById(projectId);
		// 发标人信息
		UserInfoVo publisher = userBiz.getUserInfoByID(projectVo.getCreatorId());
		// 竞标信息
		ProjectJoinVo selectJoinVo = projectBiz.getSelectedJoinInfo(projectId);
		UserInfoVo joiner = userBiz.getUserInfoByID(selectJoinVo.getUserId());
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
		StringBuffer sb = new StringBuffer();
		sb.append("<p>尊敬的会员，您参与竞标的项目《" + projectVo.getName() + "》被雇主取消选标。</p>");
		sb.append("<p></p>");
		sb.append("<p><strong>发标方名称：</strong>" + publisher.getName() + "</p>");
		sb.append("<p><strong>Email：</strong>" + publisher.getEmail() + "\t<strong>电话：</strong>"
				+ publisher.getMobile() + "</p>");
		sb.append("<p><strong>项目预算：</strong>" + projectVo.getPriceRange() + "\t<strong>周期：</strong>"
				+ projectVo.getPeriod() + "天</p>");
		sb.append("<p></p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");

		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(joiner.getEmail());
		EmailSender.send(mailConfig, "[码客帮]发标方取消选标", sb.toString());
	}

	// 发标人托管资金后通知竞标人
	@Override
	public void sendTrusteeMailContent(String projectId) {
		ProjectVo projectVo = projectBiz.getProjectById(projectId);
		// 发标人信息
		UserInfoVo publisher = userBiz.getUserInfoByID(projectVo.getCreatorId());
		// 竞标信息
		ProjectJoinVo selectJoinVo = projectBiz.getSelectedJoinInfo(projectId);
		UserInfoVo joiner = userBiz.getUserInfoByID(selectJoinVo.getUserId());
		double trusteeCost = trusteeBiz.getSumTrusteeMoney(projectId);
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
		StringBuffer sb = new StringBuffer();
		sb.append("<p>尊敬的会员，您参与竞标的项目《" + projectVo.getName() + "》雇主已托管项目款" + trusteeCost
				+ "元，工作日期从当前开始计算。</p>");
		sb.append("<p></p>");
		sb.append("<p><strong>发标方名称：</strong>" + publisher.getName() + "</p>");
		sb.append("<p><strong>Email：</strong>" + publisher.getEmail() + "\t<strong>电话：</strong>"
				+ publisher.getMobile() + "</p>");
		sb.append("<p><strong>项目总价：</strong>" + selectJoinVo.getPrice() + "元\t<strong>交付周期：</strong>"
				+ selectJoinVo.getPeriod() + "天</p>");
		sb.append("<p></p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");

		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(joiner.getEmail());
		EmailSender.send(mailConfig, "[码客帮]发标方托管项目款", sb.toString());
	}

	@Override
	public void sendTrusteeOKMail(String projectId) {
		ProjectVo projectVo = projectBiz.getProjectById(projectId);
		// 发标人信息
		UserInfoVo publisher = userBiz.getUserInfoByID(projectVo.getCreatorId());
		// 竞标信息
		double trusteeCost = trusteeBiz.getSumTrusteeMoney(projectId);
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
		StringBuffer sb = new StringBuffer();
		sb.append("<p>尊敬的会员，您发布的《" + projectVo.getName() + "》项目托管工程款" + trusteeCost + "元已确认到账。</p>");
		sb.append("<p>码客帮稍后会委派专业监理对项目进行跟踪</p>");
		sb.append("<p></p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看发标详情</a></p>");

		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(publisher.getEmail());
		EmailSender.send(mailConfig, "[码客帮]项目款托管已确认", sb.toString());
	}

	// 通知管理员审核邮件
	@Override
	public void sendNoticeMailContent(ProjectVo projectVo) {
		// 生产环境才群发邮件
		if (DomainConfig.getIsProduceEnvironment()) {
			UserInfoVo publisher = projectVo.getPublisherInfo();

			// 详情链接
			String viewUrl = DomainConfig.getHost() + APIConstants.CHECK_VIEW + "?id=" + projectVo.getId();
			StringBuffer sb = new StringBuffer();
			sb.append("<p><strong>需求类型：</strong>" + projectVo.getType() + "</p>");
			sb.append("<p><strong>项目预算：</strong>" + projectVo.getPriceRange() + "</p>");
			sb.append("<p><strong>交付周期：</strong>" + projectVo.getPeriod() + "天</p>");
			sb.append("<p><strong>竞标截止时间：</strong>"
					+ DateUtil.formatTime(projectVo.getBidEndTime(), DateUtil.DATA_STAND_FORMAT_STR) + "</p>");
			sb.append("<p><strong>联系人姓名：</strong>" + publisher.getName() + "</p>");
			sb.append("<p><strong>联系人手机号：</strong>" + publisher.getMobile() + "</p>");
			sb.append("<p><strong>项目名称：</strong>" + projectVo.getName() + "</p>");
			sb.append("<p><strong>需求详述：</strong></p>");
			sb.append("<p>" + projectVo.getContent() + "</p>");
			sb.append("<p></p>");
			if (!StringUtils.isEmpty(projectVo.getRemark())) {
				sb.append("<p><strong>备注：</strong>" + projectVo.getRemark() + "</p>");
			}
			sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");

			EmailSendConfig mailConfig = emailSendConfig.clone();
			EmailSender.send(mailConfig, "[码客帮]活儿来了!", sb.toString());
		}
	}

	// 服务商完工通知雇主
	@Override
	public void sendWorkingDone(String projectId) {
		ProjectVo projectVo = projectBiz.getProjectById(projectId);
		// 发标人信息
		UserInfoVo publisher = userBiz.getUserInfoByID(projectVo.getCreatorId());
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
		StringBuffer sb = new StringBuffer();
		sb.append("<p>尊敬的会员，您发布的《" + projectVo.getName() + "》项目，外包服务商申请了完工验收。</p>");
		sb.append("<p>请登录码客帮对项目完成情况进行验收，如果您未作出选择，系统将会在一周内默认通过验收</p>");
		sb.append("<p></p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看发标详情</a></p>");

		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(publisher.getEmail());
		EmailSender.send(mailConfig, "[码客帮]外包服务商申请了完工验收", sb.toString());
	}

	// 雇主验收结果通知服务商
	@Override
	public void sendAcceptResultMail(String projectId) {
		ProjectVo projectVo = projectBiz.getProjectById(projectId);
		// 竞标信息
		ProjectJoinVo selectJoinVo = projectBiz.getSelectedJoinInfo(projectId);
		UserInfoVo joiner = userBiz.getUserInfoByID(selectJoinVo.getUserId());
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
		// 更多项目
		String listUrl = DomainConfig.getHost() + PageForwardConstants.PROJECT_LIST;
		String title = "";
		StringBuffer sb = new StringBuffer();
		// 验收未通过
		if (projectVo.getBackgroudStatus() == ProjectStatusEnum.BACK_ACCPET_FAILED) {
			sb.append("<p>尊敬的会员，您发起完成验收的项目《" + projectVo.getName() + "》，雇主验收暂未通过。</p>");
			sb.append("<p><strong>未通过原因：</strong></p>");
			sb.append("<p>" + projectVo.getAcceptResult() + "</p>");
			sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
			title = "[码客帮]开发验收暂未通过";
		} else {
			sb.append("<p>尊敬的会员，您发起完成验收的项目《" + projectVo.getName() + "》，雇主验收已经通过。</p>");
			sb.append("<p>感谢您的辛苦付出，码客帮会在七个工作日内将项目款汇入您指定的账户内。</p>");
			sb.append("<p><a href='" + listUrl + "' target='_blank'>更多好项目</a></p>");
			title = "[码客帮]开发验收已通过";
		}
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(joiner.getEmail());
		EmailSender.send(mailConfig, title, sb.toString());
	}

	// 管理员审核付款通知服务商
	@Override
	public void sendFinishMail(String projectId) {
		ProjectVo projectVo = projectBiz.getProjectById(projectId);
		// 竞标信息
		ProjectJoinVo selectJoinVo = projectBiz.getSelectedJoinInfo(projectId);
		UserInfoVo joiner = userBiz.getUserInfoByID(selectJoinVo.getUserId());
		// 更多项目
		String listUrl = DomainConfig.getHost() + PageForwardConstants.PROJECT_LIST;
		StringBuffer sb = new StringBuffer();
		sb.append("<p>尊敬的会员，您完成的项目《" + projectVo.getName() + "》，项目款已汇至您指定的账户。</p>");
		sb.append("<p>请于24小时后查看收款账户到账情况，具体到账时间以收款行系统为准。</p>");
		sb.append("<p><a href='" + listUrl + "' target='_blank'>更多好项目</a></p>");
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(joiner.getEmail());
		EmailSender.send(mailConfig, "[码客帮]项目完成付款", sb.toString());
	}

	// 审核付款完成通知雇主评价服务商
	@Override
	public void sendEvaluateMail(String projectId) {
		ProjectVo projectVo = projectBiz.getProjectById(projectId);
		// 发标人信息
		UserInfoVo publisher = userBiz.getUserInfoByID(projectVo.getCreatorId());
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
		StringBuffer sb = new StringBuffer();
		sb.append("<p>尊敬的会员，您的项目《" + projectVo.getName() + "》已经完成了，为了让我们能为您提供更好的服务，请给您的外包服务商进行评价打分。</p>");
		sb.append("<p></p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>评价打分</a></p>");
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(publisher.getEmail());
		EmailSender.send(mailConfig, "[码客帮]服务评价打分", sb.toString());
	}

	/**
	 * 雇主评价完成通知服务商
	 */
	@Override
	public void sendEvaluateResultMail(String projectId) {
		ProjectVo projectVo = projectBiz.getProjectById(projectId);
		// 评价信息
		ProjectEvaluateVo evaluationVo = projectEvaluateBiz.getEvaluateByPID(projectId);
		UserInfoVo joiner = userBiz.getUserInfoByID(evaluationVo.getEmployeeId());
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
		StringBuffer sb = new StringBuffer();
		sb.append("<p>尊敬的会员，您完成的项目《" + projectVo.getName() + "》雇主评价打分了。</p>");
		sb.append("<p>完成质量:" + evaluationVo.getQualityScore() + "星</p>");
		sb.append("<p>完成速度:" + evaluationVo.getSpeedScore() + "星</p>");
		sb.append("<p>服务态度:" + evaluationVo.getAttitudeScore() + "星</p>");
		sb.append("<p>描述:</p>");
		sb.append("<p>" + evaluationVo.getDescription() + "</p>");
		sb.append("<p></p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(joiner.getEmail());
		EmailSender.send(mailConfig, "[码客帮]服务评价打分", sb.toString());
	}

	/**
	 * 预约顾问通知管理员处理
	 */
	@Override
	public void sendWeixinRequestMail(WeixinProjectVo weixinProjectVo) {
		// 生产环境才群发邮件
		if (DomainConfig.getIsProduceEnvironment()) {

			String type = "PC版预约顾问";
			// 详情链接
			StringBuffer sb = new StringBuffer();
			sb.append("<p><strong>联系人姓名：</strong>" + weixinProjectVo.getContactsName() + "</p>");
			sb.append("<p><strong>联系人手机号：</strong>" + weixinProjectVo.getContactNumber() + "</p>");
			if (!StringUtils.isEmpty(weixinProjectVo.getRemark())) {
				sb.append("<p><strong>项目预算：</strong>" + weixinProjectVo.getPriceRange() + "</p>");
				sb.append("<p><strong>交付周期：</strong>" + weixinProjectVo.getPeriod() + "</p>");
			}
			sb.append("<p><strong>需求描述：</strong></p>");
			sb.append("<p>" + weixinProjectVo.getContent() + "</p>");
			sb.append("<p></p>");
			sb.append("<p>--来自于" + type + "</p>");
			sb.append("<p>--" + weixinProjectVo.getIpAddress() + "--</p>");

			EmailSendConfig mailConfig = emailSendConfig.clone();
			EmailSender.send(mailConfig, "[码客帮]活儿来了，预约顾问!", sb.toString());
		}
	}

	/**
	 * 雇主发布项目审核通过后推送所有服务商
	 */
	@Override
	public void send2AllMembers(String projectId) {
		if (DomainConfig.getIsProduceEnvironment()) {
			ProjectVo projectVo = projectBiz.getProjectById(projectId);

			// 获取所有服务商邮箱地址
			List<String> listEmail = userBiz.getAllMembersEmail();
			log.info("project [" + projectVo.getName() + "], send2AllMembers size is " + listEmail.size());

			// 详情链接
			String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
//			StringBuffer sb = new StringBuffer();
//			sb.append("<p><strong>需求类型：</strong>" + projectVo.getType() + "</p>");
//			sb.append("<p><strong>项目预算：</strong>" + projectVo.getPriceRange() + "</p>");
//			sb.append("<p><strong>交付周期：</strong>" + projectVo.getPeriod() + "天</p>");
//			sb.append("<p><strong>竞标截止时间：</strong>"
//					+ DateUtil.formatTime(projectVo.getBidEndTime(), DateUtil.DATA_STAND_FORMAT_STR) + "</p>");
//			sb.append("<p><strong>项目名称：</strong>" + projectVo.getName() + "</p>");
//			sb.append("<p><strong>需求详述：</strong></p>");
//			sb.append("<p>" + projectVo.getContent() + "</p>");
//			sb.append("<p></p>");
//			sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
//			sb.append("<p><strong>联系我时，请说明是在码客帮网站看到的。</strong></p>");

			// 读取邮件模板
			Template emailTemplate = EmailTemplateUtil.getTemplate("notice_new_project.ftl");
			Map map = new HashMap();
			map.put("projectVo", projectVo);
			map.put("viewUrl", viewUrl);
			String content = getTemplateStr(map, emailTemplate);
			splitSendMail(listEmail, "[码客帮]新项目上架", content);
		}
	}

	/**
	 * 服务商申请认证通知管理员审核
	 */
	@Override
	public void sendRequestIdentify(IdentifyVo identifyVo) {
		if (DomainConfig.getIsProduceEnvironment()) {
			// 详情链接
			String category = identifyVo.getCategory() == 0 ? "个人" : "企业";
			String viewUrl = DomainConfig.getHost() + APIConstants.IDENTIFY_CHECK + "?uid="
					+ identifyVo.getUserId();
			StringBuffer sb = new StringBuffer();
			sb.append("<p><a href='" + viewUrl + "' target='_blank'>查看认证详情</a></p>");

			EmailSendConfig mailConfig = emailSendConfig.clone();
			EmailSender.send(mailConfig, "[码客帮]服务商提交" + category + "认证，等待审核", sb.toString());
		}
	}

	/**
	 * 管理员认证结果通知服务商
	 */
	@Override
	public void sendIdentifyResult(IdentifyVo identifyVo) {
		UserInfoVo userInfo = userBiz.getUserInfoByID(identifyVo.getUserId());

		// 用户信息详情链接
		String viewUrl = DomainConfig.getHost() + PageForwardConstants.USERS_INFO_PAGE;
		// 更多项目
		String listUrl = DomainConfig.getHost();
		String title = "";
		StringBuffer sb = new StringBuffer();
		// 验收未通过
		if (identifyVo.getStatus() == 2) {
			sb.append("<p>尊敬的会员，您提交认证的资料审核暂未通过。</p>");
			sb.append("<p><strong>未通过原因：</strong></p>");
			sb.append("<p>" + identifyVo.getFailReason() + "</p>");
			sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
			title = "[码客帮]您的认证审核未通过";
		} else {
			sb.append("<p><b>尊敬的会员，您的认证审核已经通过。</b></p>");
			sb.append("<p><a href='" + listUrl + "' target='_blank'>前往码客帮</a></p>");
			title = "[码客帮]您的认证审核已通过";
		}
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(userInfo.getEmail());
		EmailSender.send(mailConfig, title, sb.toString());
	}

	@Override
	public void sendBidTimeoutNotice(List<ProjectVo> listProject) {
		try {
			UserInfoVo publisher = null;
			for (ProjectVo pVo : listProject) {
				// 发标人信息
				publisher = userBiz.getUserInfoByID(pVo.getCreatorId());
				// 详情链接
				String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS
						+ pVo.getId();
				StringBuffer sb = new StringBuffer();
				// 审核未通过
				sb.append("<p><strong>尊敬的会员，您发起的项目《" + pVo.getName() + "》，竞标即将超期，请选标或延长竞标截止时间。</strong></p>");
				sb.append("<p>如未操作，竞标超期后项目将自动结束关闭</p>");
				sb.append("<p></p>");
				sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
				EmailSendConfig mailConfig = emailSendConfig.clone();
				mailConfig.setReceiver(publisher.getEmail());
				EmailSender.send(mailConfig, "[码客帮]竞标即将超期", sb.toString());

				Thread.sleep(emailSendConfig.getInterval());
			}
		} catch (Exception e) {
			log.error("sendBidTimeoutNotice failed. ", e);
		}
	}

	/**
	 * 雇主补充项目内容通知所有参与竞标的服务商
	 */
	@Override
	public void sendAdjustPriceNoticeEmployee(ProjectVo projectVo, String newPriceRange) {
		List<String> listJoinersMail = userBiz.getJoinersEmailByPID(projectVo.getId());
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS
				+ projectVo.getId();
		StringBuffer sb = new StringBuffer();
		sb.append("<p><strong>尊敬的会员，您参与竞标的项目《" + projectVo.getName() + "》雇主修改了项目预算。<strong></p>");
		sb.append("<p>原有预算: " + projectVo.getPriceRange() + "</p>");
		sb.append("<p>调整后预算: " + newPriceRange + "</p>");
		sb.append("<p></p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");

		splitSendMail(listJoinersMail, "[码客帮]发标方修改预算", sb.toString());
	}

	/**
	 * 雇主调整项目预算通知所有参与竞标的服务商
	 */
	@Override
	public void sendSupplementNoticeEmployee(String projectId, String supppleContent) {
		List<String> listJoinersMail = userBiz.getJoinersEmailByPID(projectId);
		ProjectVo projectVo = projectBiz.getProjectById(projectId);
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectId;
		StringBuffer sb = new StringBuffer();
		sb.append("<p><strong>尊敬的会员，您参与竞标的项目《" + projectVo.getName() + "》雇主补充了项目描述。<strong></p>");
		sb.append("<p>项目描述:</p>");
		sb.append(supppleContent);
		sb.append("<p></p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");

		splitSendMail(listJoinersMail, "[码客帮]发标方补充项目描述", sb.toString());
	}

	@Override
	public void pushProjectSelf2Developer(String chooseMailStr, String projectId) {

//		if (DomainConfig.getIsProduceEnvironment()) {
		ProjectInSelfRunVo projectVo = projectInSelfRunBiz.getProjectInSelfRunById(projectId);
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_IN_SELF_RUN_DETAILS
				+ projectId;

		// 读取邮件模板
		Template emailTemplate = EmailTemplateUtil.getTemplate("push_project_self.ftl");
		Map map = new HashMap();
		map.put("projectVo", projectVo);
		map.put("viewUrl", viewUrl);
		String content = getTemplateStr(map, emailTemplate);
		String[] mailArray = chooseMailStr.split(",");

		splitSendMail(Arrays.asList(mailArray), "[码客帮]项目推荐", content);
//		}
	}

	@Override
	public void sendJoinMail2Admin(String projectId, UserInfoVo userInfo) {
		// 生产环境才群发邮件
		if (DomainConfig.getIsProduceEnvironment()) {
			ProjectInSelfRunVo projectVo = projectInSelfRunBiz.getProjectInSelfRunById(projectId);

			// 详情链接
			String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_IN_SELF_RUN_DETAILS
					+ projectId;
			StringBuffer sb = new StringBuffer();

			sb.append("<p><strong>报名者姓名：</strong>" + userInfo.getName() + "</p>");
			sb.append("<p><strong>报名者电话：</strong>" + userInfo.getMobile() + "</p>");
			sb.append("<p><strong>项目名称：</strong>" + projectVo.getName() + "</p>");
			sb.append("<p><strong>需求详述：</strong></p>");
			sb.append("<p>" + projectVo.getContent() + "</p>");
			sb.append("<p></p>");
			sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");

			EmailSendConfig mailConfig = emailSendConfig.clone();
			// TODO 邮件还需发送分配的顾问
			EmailSender.send(mailConfig, "[码客帮]" + projectVo.getName() + "有人报名了!", sb.toString());
		}
	}

	// 自营项目通知管理员
	@Override
	public void sendSelfProjectNoticeMail2Admin(String projectId,ProjectInSelfRunVo projectVo) {
		// 生产环境才群发邮件
		if (DomainConfig.getIsProduceEnvironment()) {
			UserInfoVo publisher = userBiz.getUserInfoByID(projectVo.getCreatorId());

			// 详情链接
			String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_IN_SELF_RUN_DETAILS
					+ projectId;
			StringBuffer sb = new StringBuffer();

			String type = "";
			for (String typeItem : projectVo.getType().split(",")) {
				type += LabelUtil.getSingleItemName(CommonConstants.PROJECT_IN_SELF_RUN_TYPE_TAG, typeItem) + " ";
			}
			String budget = LabelUtil.getSingleItemName(CommonConstants.PROJECT_IN_SELF_RUN_BUDGET_TAG,
					projectVo.getBudget());
			String startTime = LabelUtil.getSingleItemName(CommonConstants.PROJECT_IN_SELF_RUN_STARTTIME_TAG,
					projectVo.getStartTime());
			
			sb.append("<p><strong>需求类型：</strong>" + type + "</p>");
			sb.append("<p><strong>项目预算：</strong>" + budget + "</p>");
			sb.append("<p><strong>开始时间：</strong>" + startTime + "天</p>");

			String userName = "";
			if (StringUtils.isEmpty(publisher.getName())) {
				userName = "(新需求发布，无用户名)";
			} else {
				userName = publisher.getName();
			}
			sb.append("<p><strong>联系人姓名：</strong>" + userName + "</p>");
			sb.append("<p><strong>联系人手机号：</strong>" + publisher.getMobile() + "</p>");
			sb.append("<p><strong>项目名称：</strong>" + projectVo.getName() + "</p>");
			sb.append("<p><strong>需求详述：</strong></p>");
			sb.append("<p>" + projectVo.getContent() + "</p>");
			sb.append("<p></p>");
			sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");

			EmailSendConfig mailConfig = emailSendConfig.clone();
			//TODO 邮件还需发送分配的顾问
			EmailSender.send(mailConfig, "[码客帮]活儿来了!", sb.toString());
		}
	}

	/** 批量发送，需要分时分段，防止被当成垃圾邮件拒发 */
	private void splitSendMail(List<String> listReceiveEmail, String title, String content) {
		try {
			int totalSize = listReceiveEmail.size();
			if (totalSize == 0) {
				return;
			}
			int copies = emailSendConfig.getCopies();
			int loopSize = (copies > totalSize) || (totalSize % copies) != 0 ? (totalSize / copies) + 1
					: (totalSize / copies);

			// 群发每次发固定个数
			for (int i = 0; i < loopSize; i++) {
				String bcc = "";
				for (int j = 0; j < copies; j++) {
					if ((i * copies + j) >= totalSize) {
						break;
					}
					bcc += listReceiveEmail.get(i * copies + j) + ",";
				}
				bcc = bcc.substring(0, bcc.lastIndexOf(","));

				EmailSendConfig mailConfig = emailSendConfig.clone();
				mailConfig.setReceiver(emailSendConfig.getUserName());
				mailConfig.setBcc(bcc);
				EmailSender.send(mailConfig, title, content);
				// 发送间隔，防止垃圾邮件过滤
				Thread.sleep(mailConfig.getInterval());
			}
		} catch (Exception e) {
			log.error("splitSendMail failed. ", e);
		}
	}

	private String getTemplateStr(Map paramMap, Template template) {
		String resultStr = "";
		try {
			StringWriter stringWriter = new StringWriter();
			// 创建模版对象
			template.process(paramMap, stringWriter);
			stringWriter.flush();
			stringWriter.close();

			resultStr = stringWriter.toString();
		} catch (Exception e) {
			log.warn("getTemplateStr failed.", e);
		}

		return resultStr;
	}

	@Override
	public void sendAddChooseDeveloper(UserInfoVo userInfo,ProjectInSelfRunVo projectVo) {
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_IN_SELF_RUN_REQUIRE + projectVo.getId();
		
		String title = "";
		StringBuffer sb = new StringBuffer();
		// 被选择为参与者
		sb.append("<p>尊敬的会员，您报名的项目《" + projectVo.getName() + "》，已被选择为开发者</p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
		title = "[码客帮]加入开发";
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(userInfo.getEmail());
		EmailSender.send(mailConfig, title, sb.toString());
		
	}

	@Override
	public void sendRemoveChoosenDeveloper(UserInfoVo userInfo,ProjectInSelfRunVo projectVo) {
		// TODO Auto-generated method stub
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_IN_SELF_RUN_REQUIRE + projectVo.getId();
		String title = "";
		StringBuffer sb = new StringBuffer();
		// 被选择为参与者
		sb.append("<p>尊敬的会员，您参与的项目《" + projectVo.getName() + "》，已经结束。</p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
		title = "[码客帮]结束开发";
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(userInfo.getEmail());
		EmailSender.send(mailConfig, title, sb.toString());
	}

	@Override
	public void sendSubmitVerify(UserInfoVo userInfo,ProjectInSelfRunVo projectVo,int step) {
		// TODO Auto-generated method stub
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_IN_SELF_RUN_REQUIRE + projectVo.getId();
		String title = "";
		StringBuffer sb = new StringBuffer();
		// 被选择为参与者
		sb.append("<p>尊敬的会员，您发布的项目" + projectVo.getName() + "第"+step+"阶段,已经开发完成，请确认验收。</p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
		title = "[码客帮]验收";
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(userInfo.getEmail());
		EmailSender.send(mailConfig, title, sb.toString());
		
	}

	@Override
	public void sendCheckVerify(UserInfoVo userInfo,ProjectInSelfRunVo projectVo,Boolean isPass,int step) {
		// TODO Auto-generated method stub
		// 详情链接
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_IN_SELF_RUN_REQUIRE + projectVo.getId();
		String title = "";
		StringBuffer sb = new StringBuffer();
		// 被选择为参与者
		if(isPass){
			sb.append("<p>尊敬的会员，您参与的项目《" + projectVo.getName() + "》第"+step+"阶段，已验收通过。</p>");
			sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
			title = "[码客帮]验收通过";
		}else{
			sb.append("<p>尊敬的会员，您参与的项目《" + projectVo.getName() + "》第"+step+"阶段，客户验收未通过。</p>");
			sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
			title = "[码客帮]验收未通过";
		}
		EmailSendConfig mailConfig = emailSendConfig.clone();
		mailConfig.setReceiver(userInfo.getEmail());
		EmailSender.send(mailConfig, title, sb.toString());
		
	}

	@Override
	public void sendProjectClose(List<String> listEmails,ProjectInSelfRunVo projectVo,String reason) {
		String viewUrl = DomainConfig.getHost() + UrlRewriteConstants.REWRITE_PROJECT_IN_SELF_RUN_REQUIRE + projectVo.getId();
		String title = "";
		StringBuffer sb = new StringBuffer();
		// 被选择为参与者
		sb.append("<p>尊敬的会员，您参与的项目《" + projectVo.getName() + "》由于："+ reason +"，已经关闭。</p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看详情</a></p>");
		title = "[码客帮]项目关闭";

		splitSendMail(listEmails, title , sb.toString());
	}

	@Override
	public void sendNotChoosen(List<String> listEmails,ProjectInSelfRunVo projectVo) {
		String viewUrl = DomainConfig.getHost() + "/market";
		String title = "";
		StringBuffer sb = new StringBuffer();
		// 被选择为参与者
		sb.append("<p>非常抱歉，您报名的项目《" + projectVo.getName() + "》没有被选中。期待与您的下次合作。</p>");
		sb.append("<p><a href='" + viewUrl + "' target='_blank'>点此查看更多项目</a></p>");
		title = "[码客帮]项目报名未选中";

		splitSendMail(listEmails, title , sb.toString());
	}
}
