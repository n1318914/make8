package com.yundaren.support.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.yundaren.common.constants.ProjectStatusEnum;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.PageResult;
import com.yundaren.support.biz.ProjectBiz;
import com.yundaren.support.biz.ProjectEvaluateBiz;
import com.yundaren.support.biz.ProjectInSelfRunBiz;
import com.yundaren.support.biz.TradeBiz;
import com.yundaren.support.biz.TrusteeBiz;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.PublicConfig;
import com.yundaren.support.service.ProjectMailService;
import com.yundaren.support.service.ProjectService;
import com.yundaren.support.vo.ProjectEvaluateVo;
import com.yundaren.support.vo.ProjectJoinVo;
import com.yundaren.support.vo.ProjectVo;
import com.yundaren.support.vo.TradeInfoVo;
import com.yundaren.support.vo.TrusteeInfoVo;
import com.yundaren.support.vo.WeixinProjectVo;
import com.yundaren.user.biz.SsoBiz;
import com.yundaren.user.biz.UserBiz;
import com.yundaren.user.vo.UserInfoImportVo;
import com.yundaren.user.vo.UserInfoVo;

@Slf4j
public class ProjectServiceImpl implements ProjectService {

	@Setter
	private ProjectBiz projectBiz;

	@Setter
	private SsoBiz ssoBiz;

	@Setter
	private UserBiz userBiz;

	@Setter
	private TrusteeBiz trusteeBiz;

	@Setter
	private TradeBiz tradeBiz;

	@Setter
	private ProjectMailService projectMailService;

	@Setter
	private ProjectEvaluateBiz projectEvaluateBiz;
	
	@Setter
	private ProjectInSelfRunBiz projectInSelfRunBiz;

	@Override
	public String addProject(UserInfoVo userInfo, ProjectVo projectVo) {
		// 项目ID太长，优化整改数据库从varchar类型变为bigint
//		String id = CommonUtil.getProjectSN();
//		projectVo.setId(id);
		projectVo.setCreatorId(userInfo.getId());
		projectVo.setPublisherInfo(userInfo);
		projectVo.setBackgroudStatus(ProjectStatusEnum.BACK_CHECKING);
		// 是否诚意项目
		if (projectVo.isRich()) {
			projectVo.setRanking(2);
			projectVo.setIsSincerity(1);
		} else {
			projectVo.setRanking(0);
			projectVo.setIsSincerity(0);
		}
		String pid = projectBiz.addProject(projectVo);
		projectVo.setId(pid);
		// 邮件通知审核人
		projectMailService.sendNoticeMailContent(projectVo);
		return pid;
	}

	@Override
	public long joinProject(ProjectJoinVo projectJoinVo) {
		long count = projectBiz.joinProject(projectJoinVo);
		// 发送通知邮件给发标人
		projectMailService.sendBidMailContent(projectJoinVo);
		return count;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean chooseProjectJoin(long joinUserId, ProjectVo projectVo) {
		// 更新项目状态
		ProjectVo pVo = new ProjectVo();
		pVo.setId(projectVo.getId());
		pVo.setBackgroudStatus(ProjectStatusEnum.BACK_PLANING);
		pVo.setEmployeeId(joinUserId);
		projectBiz.updateProject(pVo);

		// 更新项目竞标状态
		for (ProjectJoinVo joinVo : projectVo.getJoinList()) {
			if (joinVo.getUserId() == joinUserId) {
				ProjectJoinVo jVo = new ProjectJoinVo();
				jVo.setProjectId(joinVo.getProjectId());
				jVo.setUserId(joinUserId);
				jVo.setChoosedTime(new Date());
				jVo.setChoosed(1);
				projectBiz.updateProjectJoin(jVo);
				break;
			}
		}
		// 发送邮件给服务商
		projectMailService.sendChoiceMailContent(projectVo.getId());
		return true;
	}

	@Override
	public ProjectVo getProjectDetailsById(String id) {
		ProjectVo projectVo = projectBiz.getProjectById(id);
		if (projectVo == null) {
			log.info("getProjectDetails is null, pid=" + id);
			return null;
		}
		// 设置状态
		ProjectStatusEnum.setStatus(projectVo);
		// 设置发标人信息
		UserInfoVo publisher = userBiz.getUserInfoByID(projectVo.getCreatorId());
		setLittleDsiplay(publisher, projectVo);
		projectVo.setPublisherInfo(publisher);
		// 获取当前参与竞标的人
		List<ProjectJoinVo> joinList = projectBiz.getProjectJoinListByPID(id);
		String jPlanAttachmentUrl;

		for (ProjectJoinVo joinInfo : joinList) {
			// 设置竞标用户信息
			UserInfoVo userInfo = userBiz.getUserInfoByID(joinInfo.getUserId());
			setLittleDsiplay(userInfo, null);
			joinInfo.setUserInfo(userInfo);

			jPlanAttachmentUrl = StringUtils.isEmpty(joinInfo.getAttachment()) ? "" : DomainConfig
					.getBindDomain() + joinInfo.getAttachment();

			joinInfo.setAttachment(jPlanAttachmentUrl);
		}
		projectVo.setJoinList(joinList);
		// 交易详情信息
		TradeInfoVo tradeInfo = tradeBiz.getTradeInfoByPID(id);
		projectVo.setTradeInfo(tradeInfo);
		// 评价信息
		ProjectEvaluateVo evaluateVo = projectEvaluateBiz.getEvaluateByPID(id);
		projectVo.setEvaluateVo(evaluateVo);

		String attachmentUrl = StringUtils.isEmpty(projectVo.getAttachment()) ? "" : DomainConfig
				.getBindDomain() + projectVo.getAttachment();
		projectVo.setAttachment(attachmentUrl);
		return projectVo;
	}
	
	/*
	 * 名称和邮件文本太长，缩略显示
	 */
	private static void setLittleDsiplay(UserInfoVo uInfo, ProjectVo projectVo) {
		String displayName = CommonUtil.getLittleStr(8, uInfo.getName());
		uInfo.setDisplayName(displayName);

		if (!StringUtils.isEmpty(uInfo.getEmail())) {
			String displayEmail = CommonUtil.getLittleStr(18, uInfo.getEmail());
			uInfo.setDisplayEmail(displayEmail);
		}

		if (projectVo != null && !StringUtils.isEmpty(projectVo.getContactEmail())) {
			String displayContactEmail = CommonUtil.getLittleStr(18, projectVo.getContactEmail());
			projectVo.setDisplayContactEmail(displayContactEmail);
		}
	}

	@Override
	public ProjectVo getProjectInfoById(String id) {
		ProjectVo projectVo = projectBiz.getProjectById(id);
		String attachmentUrl = StringUtils.isEmpty(projectVo.getAttachment()) ? "" : DomainConfig
				.getBindDomain() + projectVo.getAttachment();
		projectVo.setAttachment(attachmentUrl);
		return projectVo;
	}

	@Override
	public List<ProjectVo> getProjectList(PageResult page, long userId, int opType, String type, int status) {
		String backgroundStatus = ProjectStatusEnum.getBackListStrByType(status);
		List<ProjectVo> listProject = projectBiz.getProjectList(page, userId, opType, type, backgroundStatus);
		for (ProjectVo project : listProject) {
			// 设置状态
			ProjectStatusEnum.setStatus(project);
			// 设置发标人信息
			UserInfoVo publisher = userBiz.getUserInfoByID(project.getCreatorId());
			project.setPublisherInfo(publisher);
		}
		return listProject;
	}

	@Override
	public List<ProjectVo> getAllProjectList(String type, int status, PageResult page) {
		String backgroundStatus = ProjectStatusEnum.getBackListStrByType(status);
		List<ProjectVo> listProject = projectBiz.getAllProjectList(type, backgroundStatus, page);
		for (ProjectVo project : listProject) {
			// 设置状态
			ProjectStatusEnum.setStatus(project);
			// 设置发标人信息
			UserInfoVo publisher = userBiz.getUserInfoByID(project.getCreatorId());
			project.setPublisherInfo(publisher);
		}
		return listProject;
	}

	@Override
	public int getAllProjectListCount(String type, int status) {
		String backgroundStatus = ProjectStatusEnum.getBackListStrByType(status);
		return projectBiz.getAllProjectListCount(type, backgroundStatus);
	}

	@Override
	public boolean update2CheckStatus(int operateType, long checkerId, String projectId, String reason) {
		ProjectVo pVo = new ProjectVo();
		pVo.setId(projectId);
		if (operateType == 0) {
			// 通过
			pVo.setBackgroudStatus(ProjectStatusEnum.BACK_BIDING);
			pVo.setCheckerId(checkerId);
			pVo.setCheckResult("OK");
			projectBiz.update2CheckStatus(pVo);
		} else if (operateType == 1) {
			// 不通过
			pVo.setBackgroudStatus(ProjectStatusEnum.BACK_CHECK_INVALID);
			pVo.setCheckerId(checkerId);
			pVo.setCheckResult(reason);
			projectBiz.update2CheckStatus(pVo);
		}

		// 审核结果邮件通知
		projectMailService.sendCheckResultMail(projectId);
		return true;
	}

	@Override
	public Map getTotalCount() {
		Map<String, String> resultMap = new HashMap<String, String>();
		NumberFormat formatter = new DecimalFormat("###,###");
		// 注册人数
		int storehouse = userBiz.getUserInfoImportCount("", "", new PageResult<>());
		int registersCount = PublicConfig.getStartRegisterNum() + userBiz.getAllUserCount() + storehouse;
		// 认证开发者
		int developersCount = PublicConfig.getStartDeveloperNum() + userBiz.getAllIdentifyCount();
		// 项目数
		int projectsCount = PublicConfig.getStartProjectNum() + projectBiz.getAllProjectSelfCount();
		// 招募中的项目数
		int totalRow = projectInSelfRunBiz.getProjectListCount(new PageResult<>(), "", "", "");

		// 技术大牛个数
		resultMap.put("registersCount", formatter.format(registersCount));
		// 外包企业个数
		resultMap.put("developersCount", formatter.format(developersCount));
		// 交易项目个数
		resultMap.put("projectNum", formatter.format(projectsCount));
		// 招募中的项目个数
		resultMap.put("applyCount", formatter.format(totalRow));

		return resultMap;
	}

	@Override
	public ProjectJoinVo getJoinInfo(String projectId, long userId) {
		ProjectJoinVo joinVo = projectBiz.getJoinedInfo(projectId, userId);
		if (joinVo != null) {
			// 设置竞标用户信息
			UserInfoVo userInfo = userBiz.getUserInfoByID(joinVo.getUserId());
			joinVo.setUserInfo(userInfo);

			// 设置竞标附件的路径
			String attachmentUrl = StringUtils.isEmpty(joinVo.getAttachment()) ? "" : DomainConfig
					.getBindDomain() + joinVo.getAttachment();

			joinVo.setAttachment(attachmentUrl);
		}
		return joinVo;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void cancelSelectedJoin(String projectId) {
		// 更新项目状态
		ProjectVo pVo = new ProjectVo();
		pVo.setId(projectId);
		pVo.setBackgroudStatus(ProjectStatusEnum.BACK_BIDING);
		pVo.setEmployeeId(0);
		projectBiz.updateProject(pVo);

		// 取消选标发送邮件给服务商
		projectMailService.sendCancelMailContent(projectId);

		// 更新项目竞标状态
		ProjectJoinVo joinVo = getSelectedJoinInfo(projectId);
		ProjectJoinVo jVo = new ProjectJoinVo();
		jVo.setProjectId(joinVo.getProjectId());
		jVo.setUserId(joinVo.getUserId());
		jVo.setChoosed(0);
		projectBiz.updateProjectJoin(jVo);
	}

	@Override
	public void modifyProjectInfo(ProjectVo projectVo) {
		// 是否诚意项目
		if (projectVo.isRich()) {
			projectVo.setRanking(2);
			projectVo.setIsSincerity(1);
		} else {
			projectVo.setRanking(0);
			projectVo.setIsSincerity(0);
		}
		projectBiz.updateProject(projectVo);
	}

	@Override
	public void modifyJoinInfo(ProjectJoinVo joinVo) {
		projectBiz.updateProjectJoin(joinVo);

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateTrusteeInfo(String projectId, double trusteeAmount) {
		ProjectJoinVo selectJoinVo = projectBiz.getSelectedJoinInfo(projectId);

		// 新增交易详情
		TradeInfoVo tradeVo = new TradeInfoVo();
		tradeVo.setProjectId(projectId);
		tradeVo.setProjectAmount(selectJoinVo.getPrice());
		tradeVo.setProjectPeriod(selectJoinVo.getPeriod());
		tradeBiz.addTradeInfo(tradeVo);

		// 新增资金托管明细
		TrusteeInfoVo trusteeInfo = new TrusteeInfoVo();
		trusteeInfo.setProjectId(projectId);
		trusteeInfo.setAmount(trusteeAmount);
		trusteeBiz.addTrusteeInfo(trusteeInfo);

		// 更新至工作中状态
		ProjectVo pVo = new ProjectVo();
		pVo.setId(projectId);
		pVo.setBackgroudStatus(ProjectStatusEnum.BACK_WORKING);
		projectBiz.updateProject(pVo);

		// 收到钱后发送邮件和短信通知接标人和发标人
		projectMailService.sendTrusteeOKMail(projectId);
		projectMailService.sendTrusteeMailContent(projectId);
	}

	@Override
	public ProjectJoinVo getSelectedJoinInfo(String projectId) {
		ProjectJoinVo selectJoinVo = projectBiz.getSelectedJoinInfo(projectId);

		// added by peton to add the userinfo
		if (selectJoinVo != null) {
			UserInfoVo userInfo = userBiz.getUserInfoByID(selectJoinVo.getUserId());
			selectJoinVo.setUserInfo(userInfo);

			// 设置竞标附件的路径
			String attachmentUrl = StringUtils.isEmpty(selectJoinVo.getAttachment()) ? "" : DomainConfig
					.getBindDomain() + selectJoinVo.getAttachment();

			selectJoinVo.setAttachment(attachmentUrl);
		}
		return selectJoinVo;
	}

	@Override
	public void updateTimeoutProject() {
		projectBiz.updateTimeoutProject();
	}

	@Override
	public long weixinRequest(HttpServletRequest request, WeixinProjectVo weixinProjectVo) {
		// 获取请求IP位置信息
		String ip = CommonUtil.getRealIP(request);
		String localInfo = "IP:" + ip + "," + CommonUtil.getIPLocalInfo(ip);
		
		weixinProjectVo.setIpAddress(localInfo);
		long id = projectBiz.weixinRequest(weixinProjectVo);

		// 微信找外包通知管理员处理
		projectMailService.sendWeixinRequestMail(weixinProjectVo);
		return id;
	}

	/**
	 * 服务商申请开发完工
	 */
	@Override
	public void update2WokingDone(String projectId) {
		// 更新项目状态
		ProjectVo pVo = new ProjectVo();
		pVo.setId(projectId);
		pVo.setBackgroudStatus(ProjectStatusEnum.BACK_ACCPETING);
		projectBiz.updateAcceptStatus(pVo);

		// 申请工作完成邮件发送
		projectMailService.sendWorkingDone(projectId);
	}

	/**
	 * 雇主开发验收
	 * 
	 * @param operate
	 *            0通过 1未通过
	 */
	@Override
	public void update2Accept(String projectId, int operate, String acceptResult) {
		ProjectVo pVo = new ProjectVo();
		pVo.setId(projectId);
		if (operate == 0) {
			pVo.setBackgroudStatus(ProjectStatusEnum.BACK_ACCPET_PASS);
			pVo.setAcceptResult("OK");
			pVo.setAcceptTime(new Date());
		} else if (operate == 1) {
			pVo.setBackgroudStatus(ProjectStatusEnum.BACK_ACCPET_FAILED);
			pVo.setAcceptResult(acceptResult);
		}
		// 更新项目状态
		projectBiz.updateAcceptStatus(pVo);

		// 邮件通知服务商验收结果
		projectMailService.sendAcceptResultMail(projectId);
	}

	/**
	 * 确认工作完成，管理员审核付款
	 * 
	 * @param amount
	 *            最终实际交付金额
	 * @param peroid
	 *            最终实际开发周期
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update2Finish(String projectId, double amount, int peroid) {
		// 更新项目状态
		ProjectVo pVo = new ProjectVo();
		pVo.setId(projectId);
		pVo.setFinishTime(new Date());
		pVo.setBackgroudStatus(ProjectStatusEnum.BACK_FINISHED);
		projectBiz.updateProject(pVo);

		// 更新交易详情
		TradeInfoVo tradeVo = new TradeInfoVo();
		tradeVo.setProjectId(projectId);
		tradeVo.setActuallyPayment(amount);
		tradeVo.setActuallyPeriod(peroid);;
		tradeBiz.updateTradeInfo(tradeVo);

		// 邮件通知
		projectMailService.sendFinishMail(projectId);
		projectMailService.sendEvaluateMail(projectId);
	}

	@Override
	public int getProjectCount(String type, int status) {
		String backgroundStatus = ProjectStatusEnum.getBackListStrByType(status);
		return projectBiz.getProjectCount(type, backgroundStatus);
	}

	@Override
	public int getPublishListCount(long userId) {
		return projectBiz.getPublishListCount(userId);
	}

	@Override
	public int getJoinListCount(long userId, String type, int status) {
		String backgroundStatus = ProjectStatusEnum.getBackListStrByType(status);
		return projectBiz.getJoinListCount(userId, type, backgroundStatus);
	}

	/**
	 * 补充发标内容
	 */
	@Override
	public void supplementProject(final String pid, final String content, String attachmentUrl) {
		if (!StringUtils.isEmpty(attachmentUrl)) {
			ProjectVo projectVo = new ProjectVo();
			projectVo.setId(pid);
			projectVo.setAttachment(attachmentUrl);
			projectBiz.updateProject(projectVo);
		}

		projectBiz.supplementProject(pid, content);
		// 补充内容重新发送邮件给竞标的服务商
		new Thread(new Runnable() {
			public void run() {
				projectMailService.sendSupplementNoticeEmployee(pid, content);
			}
		}, "t_supplementProject_notice").start();
	}

	@Override
	public void modifyJoinRemark(String pid, long employeeId, String remark) {
		ProjectJoinVo jVo = new ProjectJoinVo();
		jVo.setProjectId(pid);
		jVo.setUserId(employeeId);
		jVo.setRemark(remark);
		projectBiz.updateProjectJoin(jVo);
	}

	@Override
	public void kickJoin(String pid, long employeeId) {
		ProjectJoinVo jVo = new ProjectJoinVo();
		jVo.setProjectId(pid);
		jVo.setUserId(employeeId);
		jVo.setChoosed(-1);
		jVo.setKickTime(new Date());
		projectBiz.updateProjectJoin(jVo);
	}

	@Override
	public void kickJoinCancel(String pid, long employeeId) {
		ProjectJoinVo jVo = new ProjectJoinVo();
		jVo.setProjectId(pid);
		jVo.setUserId(employeeId);
		jVo.setChoosed(0);
		projectBiz.updateProjectJoin(jVo);
	}

	@Override
	public void noticeBidTimeout() {
		List<ProjectVo> listProject = projectBiz.getBidTimeoutList();
		projectMailService.sendBidTimeoutNotice(listProject);
		// TODO 发送短信通知
	}

	@Override
	public List<ProjectVo> getRecommendProjectList() {
//		List<ProjectVo> resultList = new ArrayList<ProjectVo>();
		List<ProjectVo> listProject = projectBiz.getRecommendProjectList();
//		// 首页展现个数
//		int displaySize = 5;
//		if (listProject.size() <= displaySize) {
//			resultList.addAll(listProject);
//		} else {
//			// 集合随机排序
//			Collections.shuffle(listProject);
//			resultList.addAll(listProject.subList(0, displaySize));
//		}
		return listProject;
	}

	@Override
	public List<WeixinProjectVo> getReserveList(int status, PageResult page) {
		List<WeixinProjectVo> listReserve = projectBiz.getReserveList(status, page);
		return listReserve;
	}

	@Override
	public int getReserveListCount(int status) {
		return projectBiz.getReserveListCount(status);
	}
	
	@Override
	public WeixinProjectVo getReserveByID(long id) {
		return projectBiz.getReserveByID(id);
	}

	@Override
	public int updateReserveByID(WeixinProjectVo reserve) {
		return projectBiz.updateReserveByID(reserve);
	}
	
	/**
	 * 预约项目发布
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String reservePublish(long reserveId, ProjectVo projectVo, UserInfoVo userInfoVo) {
		// 插入用户
		long userId = userBiz.createUser(userInfoVo);
		userInfoVo.setId(userId);
		// 插入项目
		String projectId = addProject(userInfoVo, projectVo);

		// 更新预约关联
		WeixinProjectVo reserve = new WeixinProjectVo();
		reserve.setId(reserveId);
		reserve.setProjectId(projectId);
		reserve.setStatus(2);
		updateReserveByID(reserve);
		
		return projectId;
	}

	@Override
	public void closeProject(String id, String closeReason) {
		ProjectVo projectVo = new ProjectVo();
		projectVo.setId(id);
		projectVo.setBackgroudStatus(8);
		projectVo.setRemark(closeReason);
		projectBiz.updateProject(projectVo);
	}

	@Override
	public int rankingProject(String id, int ranking) {
		ProjectVo projectVo = new ProjectVo();
		projectVo.setId(id);
		projectVo.setRanking(ranking);
		projectBiz.updateProject(projectVo);
		
		return projectBiz.getProjectTopIndex(id);
	}

	@Override
	@Deprecated
	public void deleteProject(String id) {
		ProjectVo projectVo = new ProjectVo();
		projectVo.setId(id);
		projectVo.setDeleted(1);
		projectBiz.updateProject(projectVo);
	}
}
