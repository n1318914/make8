package com.yundaren.api.rest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.ProjectStatusEnum;
import com.yundaren.common.constants.RegexUtil;
import com.yundaren.common.constants.UrlRewriteConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.DateUtil;
import com.yundaren.common.util.ImageCodeUtil;
import com.yundaren.common.util.PageResult;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.filter.handler.SensitivewordFilter;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.PublicConfig;
import com.yundaren.support.service.ProjectMailService;
import com.yundaren.support.service.ProjectService;
import com.yundaren.support.service.YunConnectService;
import com.yundaren.support.vo.ProjectJoinVo;
import com.yundaren.support.vo.ProjectVo;
import com.yundaren.support.vo.WeixinProjectVo;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
public class ProjectRest {

	@Setter
	private ProjectService projectService;

	@Setter
	private UserService userService;

	@Setter
	private YunConnectService yunConnectService;

	@Setter
	private DomainConfig domainConfig;

	@Setter
	private ProjectMailService projectMailService;

	/**
	 * 发送短信息
	 */
	@RequestMapping(value = APIConstants.COMMON_SEND_SMS_VCODE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity sendMobileVerifyCode(HttpServletRequest request, String mobile) {
		Map<String, Object> result = new HashMap<String, Object>();

		// 校验验证码
		Object iRet = request.getSession().getAttribute("ischeckCaptchaOK");
		if (iRet == null) {
			result = ResponseMapUtil.getFailedResponseMap("图片验证码错误");
			return new ResponseEntity(result, HttpStatus.OK);
		}

		// 校验电话号码
		boolean validate = RegexUtil.isMobile(mobile);
		if (!validate) {
			result = ResponseMapUtil.getFailedResponseMap("无效的手机号");
			return new ResponseEntity(result, HttpStatus.OK);
		}

		// 发送短信
		String retMsg = yunConnectService.sendTemplateSMS(mobile);
		// 发送失败返回失败信息
		if (!retMsg.isEmpty()) {
			result = ResponseMapUtil.getFailedResponseMap(retMsg);
		} else {
			result = ResponseMapUtil.getSuccessResponseMap("SUCCESS");
		}
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	/**
	 * 新增需求
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = APIConstants.PROJECT_ADD, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> addProject(HttpServletRequest request, @ModelAttribute ProjectVo projectVo)
			throws UnsupportedEncodingException {
		Map<String, Object> result = checkPublishArgs(request, projectVo);
		// 如果检查通过
		if (null == result) {
			UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();

			String attachmentUrl = (String) request.getSession().getAttribute(
					CommonConstants.FILE_PROJECT_ATTACHMENT);
			projectVo.setAttachment(attachmentUrl);
			String pid = projectService.addProject(userInfo, projectVo);

			result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),
					UrlRewriteConstants.REWRITE_PROJECT_DETAILS + pid);

			// 正则表达式简单过滤恶搞标题和内容
			String regex = "^[\\w ]+$";
			if (RegexUtil.match(regex, projectVo.getName()) || RegexUtil.match(regex, projectVo.getContent())) {
				result.put("recommends", 0);
			} else {
				// 匹配到的服务商个数，现阶段直接返回5-10之间随机数
				int recommends = (int) (5 * Math.random() + 6);
				result.put("recommends", recommends);
			}
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

//	/**
//	 * 微信免登陆发布需求
//	 */
//	@RequestMapping(value = APIConstants.WEIXIN_PROJECT_ADD, method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<Map> addWeixinProject(HttpServletRequest request,
//			@ModelAttribute ProjectVo projectVo, String contactsName, String contactNumber) {
//		projectVo.setName("移动端项目发布_" + DateUtil.formatTime(new Date(), DateUtil.DATA_STAND_FORMAT_STR));
//		// 默认竞标日期一周
//		projectVo.setBidEndTime(DateUtil.getAfter7DaysDate());
//		Map<String, Object> result = checkPublishArgs(request, projectVo);
//		// 如果检查通过
//		if (null == result) {
//			
//			// 获取请求IP位置信息
//			String ip = CommonUtil.getRealIP(request);
//			String localInfo = "IP:" + ip + "," + CommonUtil.getIPLocalInfo(ip);
//			
//			String remark = "--来自于移动版发布的需求，联系人:" + contactsName + ",联系方式:" + contactNumber + "," + localInfo;
//			projectVo.setRemark(remark);
//			// 外包么项目代发账号
//			UserInfoVo userInfo = userService.getUserInfoByEmail(PublicConfig.getPublishProxyAccount());
//			String pid = projectService.addProject(userInfo, projectVo);
//			result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),
//					PageForwardConstants.MOBILE_NOTICE + "?operate=request");
//		}
//		return new ResponseEntity<Map>(result, HttpStatus.OK);
//	}

	/**
	 * 竞标
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = APIConstants.PROJECT_BID, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> addProjectJoin(HttpServletRequest request, @ModelAttribute ProjectJoinVo joinVo)
			throws UnsupportedEncodingException {
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		Map<String, Object> result = checkJoinArgs(joinVo, userInfo.getId(), true);
		if (result == null) {
			String plan = joinVo.getPlan();
			// 替换竞标描述中的敏感词
			plan = SensitivewordFilter.getInstance().replaceSensitiveWord(plan, 1, "*");
			joinVo.setPlan(plan);
			joinVo.setUserInfo(userInfo);
			joinVo.setUserId(userInfo.getId());

			String attachmentUrl = (String) request.getSession().getAttribute(
					CommonConstants.FILE_PROJECT_ATTACHMENT);
			joinVo.setAttachment(attachmentUrl);
			projectService.joinProject(joinVo);

			result = ResponseMapUtil.getSuccessResponseMap("参与竞标成功,等待发标人选标");
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 选标
	 */
	@RequestMapping(value = APIConstants.PROJECT_CHOICE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> chooseJoin(HttpServletRequest request, String projectId, long userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		SsoUserVo ssoUser = CommonUtil.getCurrentLoginUser();
		ProjectVo projectVo = projectService.getProjectDetailsById(projectId);
		// 判断是否为当前用户发布的项目，管理员也能选标
		if (projectVo.getCreatorId() == ssoUser.getUserInfoVo().getId() || isAdmin()) {
			// 是否已经有过选标
			if (projectService.getSelectedJoinInfo(projectId) != null) {
				result = ResponseMapUtil.getFailedResponseMap("不能重复选标");
			} else {
				projectService.chooseProjectJoin(userId, projectVo);
				result = ResponseMapUtil.getSuccessResponseMap("选标成功");
			}
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 预约顾问
	 */
	@RequestMapping(value = APIConstants.PROJECT_WEIXIN_REQUEST, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> weixinRequest(HttpServletRequest request, WeixinProjectVo weixinProjectVo) {
		Map<String, Object> result = checkWeixinRequestArgs(weixinProjectVo);
		if (result == null) {
			projectService.weixinRequest(request, weixinProjectVo);
			// PC预约顾问
			result = ResponseMapUtil.getSuccessResponseMap("预约顾问提交成功，码客帮专业顾问会尽快与您联系。");
			// 预约成功发送短信通知
			yunConnectService.sendReserveSMS(weixinProjectVo.getContactNumber());
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 需求列表
	 */
	@RequestMapping(value = APIConstants.PROJECT_LIST, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResult> listProject(HttpServletRequest request, PageResult page, String type,
			int status) {
		List<ProjectVo> projectList = projectService.getProjectList(page, 0, 0, type, status);
		List resultList = getTransList(projectList);

		page.setData(resultList);
		int count = projectService.getProjectCount(type, status);
		page.setTotalRow(count);
		return new ResponseEntity(page, HttpStatus.OK);
	}

	/**
	 * 我的发标列表
	 */
	@RequestMapping(value = APIConstants.PROJECT_PUBLISH_LIST, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PageResult> listPublish(PageResult page) {
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		List<ProjectVo> projectList = projectService.getProjectList(page, userInfo.getId(), 1, "", 0);
		List resultList = getTransList(projectList);

		page.setData(resultList);
		int count = projectService.getPublishListCount(userInfo.getId());
		page.setTotalRow(count);
		return new ResponseEntity<PageResult>(page, HttpStatus.OK);
	}

	/**
	 * 我的接标列表
	 */
	@RequestMapping(value = APIConstants.PROJECT_JOIN_LIST, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<PageResult> listJoin(HttpServletRequest request, PageResult page, String type,
			int status) {
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		List<ProjectVo> projectList = projectService.getProjectList(page, userInfo.getId(), 2, type, status);
		List resultList = getTransList(projectList);

		page.setData(resultList);
		int count = projectService.getJoinListCount(userInfo.getId(), type, status);
		page.setTotalRow(count);
		return new ResponseEntity<PageResult>(page, HttpStatus.OK);
	}

	/**
	 * 需求基本信息
	 */
	@RequestMapping(value = APIConstants.PROJECT_INFO, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ProjectVo> projectInfo(String id) {
		ProjectVo pVo = projectService.getProjectInfoById(id);
		return new ResponseEntity<ProjectVo>(pVo, HttpStatus.OK);
	}

	/**
	 * 需求数据统计
	 */
	@RequestMapping(value = APIConstants.PROJECT_COUNT, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> projectCount() {
		Map<String, Object> result = projectService.getTotalCount();
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 首页随机展现认证服务商和项目
	 */
	@RequestMapping(value = APIConstants.COMMON_INDEX_RECOMMEND, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> projectRecommend() {
		List<ProjectVo> listProject = projectService.getRecommendProjectList();
//		List<UserInfoVo> listUserInfo = userService.getRecommendMemberList();

		Map<String, Object> result = getTransDispalyList(listProject, new ArrayList<UserInfoVo>());
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 撤销选标
	 */
	@RequestMapping(value = APIConstants.PROJECT_CANCEL_CHOICE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> cancelSelected(String projectId) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (isAdmin()) {
			projectService.cancelSelectedJoin(projectId);
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 修改审核未通过的发标信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
/*	@RequestMapping(value = APIConstants.PROJECT_MODIFY, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> modifyProject(HttpServletRequest request, @ModelAttribute ProjectVo projectVo)
			throws UnsupportedEncodingException {
		Map<String, Object> result = checkPublishArgs(request, projectVo);
		// 如果检查通过
		if (null == result && (isSelfPublish(projectVo.getId()) || isAdmin())) {
			if (!isAdmin()) {
				projectVo.setBackgroudStatus(ProjectStatusEnum.BACK_CHECKING);
				projectVo.setCreateTime(new Date());
			}

			String attachmentUrl = (String) request.getSession().getAttribute(
					CommonConstants.FILE_PROJECT_ATTACHMENT);
			if (!StringUtils.isEmpty(attachmentUrl)) {
				projectVo.setAttachment(attachmentUrl);
			}
			projectService.modifyProjectInfo(projectVo);

			result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),
					UrlRewriteConstants.REWRITE_PROJECT_DETAILS + projectVo.getId());
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}*/

	/**
	 * 补充发标内容
	 */
	@RequestMapping(value = APIConstants.PROJECT_SUPPLEMENT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> supplement(HttpServletRequest request, String id, String content)
			throws UnsupportedEncodingException {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(content)) {
			// 如果检查通过
			if (isSelfPublish(id)) {
				// 补充内容可以上传附件
				String attachmentUrl = (String) request.getSession().getAttribute(
						CommonConstants.FILE_PROJECT_ATTACHMENT);

				projectService.supplementProject(id, content, attachmentUrl);
				result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),
						UrlRewriteConstants.REWRITE_PROJECT_DETAILS + id);
			}
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 调整需求估价
	 */
	@RequestMapping(value = APIConstants.PROJECT_ADJUSTMENT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> adjustPrice(HttpServletRequest request, String id, final String priceRange)
			throws UnsupportedEncodingException {
		Map<String, Object> result = new HashMap<String, Object>();
		// 如果检查通过
		if (isSelfPublish(id)) {
			ProjectVo projectVo = new ProjectVo();
			projectVo.setId(id);
			projectVo.setPriceRange(priceRange);

			final ProjectVo oldProjectInfo = projectService.getProjectInfoById(id);
			projectService.modifyProjectInfo(projectVo);

			// 价格调整重新发送邮件给竞标的服务商
			new Thread(new Runnable() {
				public void run() {
					projectMailService.sendAdjustPriceNoticeEmployee(oldProjectInfo, priceRange);
				}
			}, "t_adjustProject_notice").start();

			result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(),
					UrlRewriteConstants.REWRITE_PROJECT_DETAILS + id);
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 增加竞标备注
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = APIConstants.MODIFY_JOIN_REMARK, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modifyJoinRemark(HttpServletRequest request, String pid, long userId, String remark)
			throws UnsupportedEncodingException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		ProjectVo projectVo = projectService.getProjectInfoById(pid);
		// 判断是否为当前用户发布的项目
		if (projectVo.getCreatorId() == userInfo.getId()) {
			projectService.modifyJoinRemark(pid, userId, remark);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	/**
	 * 淘汰竞标
	 */
	@RequestMapping(value = APIConstants.KICK_JOIN, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity kickJoin(HttpServletRequest request, String pid, long userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		ProjectVo projectVo = projectService.getProjectInfoById(pid);
		// 判断是否为当前用户发布的项目
		if (projectVo.getCreatorId() == userInfo.getId()) {
			projectService.kickJoin(pid, userId);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	/**
	 * 撤销淘汰竞标
	 */
	@RequestMapping(value = APIConstants.KICK_JOIN_CANCEL, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity kickJoinCancel(HttpServletRequest request, String pid, long userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		ProjectVo projectVo = projectService.getProjectInfoById(pid);
		// 判断是否为当前用户发布的项目
		if (projectVo.getCreatorId() == userInfo.getId()) {
			projectService.kickJoinCancel(pid, userId);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	/**
	 * 修改竞标信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = APIConstants.PROJECT_BID_MODIFY, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map> modifyJoin(HttpServletRequest request, @ModelAttribute ProjectJoinVo joinVo)
			throws UnsupportedEncodingException {
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		Map<String, Object> result = checkJoinArgs(joinVo, userInfo.getId(), false);
		if (result == null) {
			joinVo.setUserId(userInfo.getId());
			String plan = joinVo.getPlan();
			// 替换竞标描述中的敏感词
			plan = SensitivewordFilter.getInstance().replaceSensitiveWord(plan, 1, "*");
			joinVo.setPlan(plan);

			String attachmentUrl = (String) request.getSession().getAttribute(
					CommonConstants.FILE_PROJECT_ATTACHMENT);
			joinVo.setAttachment(attachmentUrl);

			projectService.modifyJoinInfo(joinVo);
			result = ResponseMapUtil.getSuccessResponseMap("修改成功");
		}
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 服务商开发完工
	 */
	@RequestMapping(value = APIConstants.PROJECT_DONE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity workingDone(HttpServletRequest request, String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		ProjectVo projectVo = projectService.getProjectInfoById(id);
		// 判断是否为当前用户接的项目
		if (projectVo.getEmployeeId() == userInfo.getId()) {
			projectService.update2WokingDone(id);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	/**
	 * 竞标延期
	 */
	@RequestMapping(value = APIConstants.BID_EXTENSION, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity bidExtension(HttpServletRequest request, String id, String date) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		ProjectVo projectVo = projectService.getProjectInfoById(id);
		// 判断是否为当前用户接的项目
		if (projectVo.getCreatorId() == userInfo.getId()) {
			Date newDate = DateUtil.parseDateFromString(date, DateUtil.DATA_STAND_FORMAT_STR);
			if (newDate.after(projectVo.getBidEndTime())) {
				ProjectVo pVo = new ProjectVo();
				pVo.setId(id);
				pVo.setBidEndTime(newDate);
				projectService.modifyProjectInfo(pVo);
			}
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	/**
	 * 雇主开发验收
	 * 
	 * @param operate
	 *            0通过 1未通过
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = APIConstants.PROJECT_ACCEPT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity accept(HttpServletRequest request, String id, int operate, String result)
			throws UnsupportedEncodingException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		ProjectVo projectVo = projectService.getProjectInfoById(id);
		// 判断是否为当前用户发布的项目
		if (projectVo.getCreatorId() == userInfo.getId()) {
			if (!StringUtils.isEmpty(result)) {
//				result = java.net.URLDecoder.decode(result, "UTF-8");
				result = result.replaceAll("\n", "<br/>");
			}
			projectService.update2Accept(id, operate, result);
		}
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	// 校验参数是否合法
	private Map checkPublishArgs(HttpServletRequest request, ProjectVo projectVo) {
		String type = projectVo.getType();
		String priceRange = projectVo.getPriceRange();
		String name = projectVo.getName();
		String content = projectVo.getContent();
		int period = projectVo.getPeriod();
		Date bidEndTime = projectVo.getBidEndTime();
		String publicContact = projectVo.getPublicContact();

		if (StringUtils.isEmpty(type) || StringUtils.isEmpty(priceRange) || StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(content) || period <= 0 || bidEndTime == null
				|| StringUtils.isEmpty(publicContact)) {
			return ResponseMapUtil.getFailedResponseMap("必填项参数不可为空");
		}

		// 返回空表示校验通过
		return null;
	}

	private Map checkWeixinRequestArgs(WeixinProjectVo weixinProjectVo) {

		String type = weixinProjectVo.getType();
		String content = weixinProjectVo.getContent();
		String contactsName = weixinProjectVo.getContactsName();
		String contactNumber = weixinProjectVo.getContactNumber();

		if (StringUtils.isEmpty(type) || StringUtils.isEmpty(content) || StringUtils.isEmpty(contactsName)
				|| StringUtils.isEmpty(contactNumber)) {
			return ResponseMapUtil.getFailedResponseMap("必填项参数不可为空");
		}
		// 返回空表示校验通过
		return null;
	}

	// 校验参数是否合法
	private Map checkJoinArgs(ProjectJoinVo joinVo, long userId, boolean isCreated) {
		try {
			double price = joinVo.getPrice();
			long period = joinVo.getPeriod();
			String plan = joinVo.getPlan();

			// 选标后不能修改竞标信息
			ProjectJoinVo selectJoinVo = projectService.getSelectedJoinInfo(joinVo.getProjectId());
			if (selectJoinVo != null) {
				return ResponseMapUtil.getFailedResponseMap("项目已经选标，不能再修改竞标信息");
			}

			if (price <= 1 || period <= 1 || StringUtils.isEmpty(plan)) {
				return ResponseMapUtil.getFailedResponseMap("参数不正确");
			}

		} catch (Exception e) {
			return ResponseMapUtil.getFailedResponseMap("参数不正确");
		}

		// 重复选标控制
		if (isCreated) { // changed by peton for join modification
			ProjectJoinVo joinInfo = projectService.getJoinInfo(joinVo.getProjectId(), userId);
			boolean isDuplicate = (joinInfo != null);
			if (isDuplicate) {
				return ResponseMapUtil.getFailedResponseMap("不能重复参与竞标");
			}
		}

		// 返回空表示校验通过
		return null;
	}

	// 是否自己发布的项目
	private boolean isSelfPublish(String pid) {
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		ProjectVo projectVo = projectService.getProjectInfoById(pid);
		// 判断是否为当前用户发布的项目
		if (projectVo.getCreatorId() != userInfo.getId()) {
			return false;
		}
		return true;
	}

	private boolean isAdmin() {
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();

		return (userInfo.getUserType() == -1);
	}

	private List getTransList(List<ProjectVo> projectList) {
		List resultList = new ArrayList<>();
		// 封装返回给前端
		for (ProjectVo project : projectList) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("id", project.getId());
			result.put("name", project.getName());
			result.put("type", project.getType());
			result.put("priceRange", project.getPriceRange());
			result.put("period", project.getPeriod());
			result.put("bidEndTime", DateUtil.formatTime(project.getBidEndTime(), ""));
			result.put("createTime", DateUtil.formatTime(project.getCreateTime(), ""));
			String displayStatus = project.getStatus() != null ? project.getStatus().getType() : "未知状态";
			result.put("displayStatus", displayStatus);
			result.put("backgroudStatus", project.getBackgroudStatus());
			// 竞标人数
			result.put("jingbiaoNums", project.getJoinCount());
			result.put("region", project.getPublisherInfo().getRegion());
			result.put("checkResult", project.getCheckResult());
			result.put("acceptResult", project.getAcceptResult());
			result.put("isSincerity", project.getIsSincerity());
			result.put("ranking", project.getRanking());

			resultList.add(result);
		}
		return resultList;
	}

	private Map getTransDispalyList(List<ProjectVo> projectList, List<UserInfoVo> listUserInfo) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		List pList = new ArrayList<>();
		List uList = new ArrayList<>();
		// 封装返回给前端
		for (ProjectVo project : projectList) {
			UserInfoVo publisher = userService.getUserInfoByID(project.getCreatorId());
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("id", project.getId());
			result.put("name", project.getName());
			result.put("type", project.getType());
			result.put("priceRange", project.getPriceRange());
			result.put("bidEndTime", project.getBidEndTime());
			result.put("createTime", project.getCreateTime());
			result.put("region", publisher.getRegion());
			result.put("period", project.getPeriod());
			result.put("isSincerity", project.getIsSincerity());
			result.put("ranking", project.getRanking());

			// 设置状态
			ProjectStatusEnum.setStatus(project);
			String displayStatus = project.getStatus() != null ? project.getStatus().getType() : "未知状态";
			result.put("status", displayStatus);
			pList.add(result);
		}
		for (UserInfoVo userInfo : listUserInfo) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("id", userInfo.getId());
			result.put("name", userInfo.getName());
			result.put("mainAbility", userInfo.getMainAbility());
			result.put("avgScore", userInfo.getAvgScore());
			uList.add(result);
		}
		returnMap.put("projectList", pList);
		returnMap.put("memberList", uList);
		return returnMap;
	}
}
