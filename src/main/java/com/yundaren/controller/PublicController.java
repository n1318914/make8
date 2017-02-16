package com.yundaren.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.HttpRequestDeviceUtils;
import com.yundaren.homepage.service.HomePageService;
import com.yundaren.homepage.vo.CustomerCaseVo;
import com.yundaren.news.service.NewsService;
import com.yundaren.news.vo.NewsVo;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.PublicConfig;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.service.ProjectInSelfRunService;
import com.yundaren.support.service.ProjectService;
import com.yundaren.support.vo.ProjectSelfRunPushVo;
import com.yundaren.user.service.UserExperienceService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
public class PublicController {

	@Setter
	private DomainConfig domainConfig;

	@Setter
	private ProjectService projectService;

	@Setter
	private PublicConfig publicConfig;

	@Setter
	private UserService userService;

	@Setter
	private UserExperienceService userExperienceService;

	@Setter
	private IdentifyService identifyService;

	@Setter
	private DictService dictService;
	
	@Setter
	private ProjectInSelfRunService projectInSelfRunService;
	
	@Setter
	private NewsService newsService;
	
	@Setter
	private HomePageService homepageService;

	/**
	 * 默认首页
	 */
	@RequestMapping(value = PageForwardConstants.INDEX_PAGE, method = {RequestMethod.GET, RequestMethod.POST,
			RequestMethod.HEAD})
	public String indexPage(HttpServletRequest request) throws IOException {

		Map<String, Object> result = projectService.getTotalCount();
		for (Map.Entry<String, Object> struct : result.entrySet()) {
			request.setAttribute(struct.getKey(), struct.getValue());
		}
//		// 如果是移动端则跳到移动端首页
//		if (HttpRequestDeviceUtils.isMobileDevice(request)) {
//			return "mobile/index";
//		}

		int userType = 0;
		int canJoinNum = 0;
		SsoUserVo ssoUser = CommonUtil.getCurrentLoginUser();
		if (ssoUser != null) {
			userType = ssoUser.getUserInfoVo().getUserType();

			// 显示未读消息
			List<ProjectSelfRunPushVo> listData = projectInSelfRunService.getInviteListByDID(ssoUser
					.getUserInfoVo().getId());
			canJoinNum = listData.size();
		}

		request.getSession().setAttribute(CommonConstants.SESSION_NOT_READ_MSG, canJoinNum);
		request.setAttribute("userType", userType);
		
	    List<NewsVo> newsVos = newsService.getNews();
	    request.setAttribute("news", newsVos);
	    
	    List<CustomerCaseVo> customerCases = homepageService.getCustomerCases(1); //1表示是在首页显示
	    request.setAttribute("customerCases", customerCases);

		return "index";
	}

	/**
	 * 关于我们页面
	 */
	@RequestMapping(value = PageForwardConstants.ABOUTUS_PAGE, method = RequestMethod.GET)
	public String aboutus_ftl(HttpServletRequest request) throws IOException {
		return "about/aboutus";
	}

	/**
	 * 联系我们页面
	 */
	@RequestMapping(value = PageForwardConstants.CONTACTUS_PAGE, method = RequestMethod.GET)
	public String contactus_ftl(HttpServletRequest request) throws IOException {
		return "about/contactus";
	}

	/**
	 * 服务协议页面
	 */
	@RequestMapping(value = PageForwardConstants.CONTRACT_PAGE, method = RequestMethod.GET)
	public String contract_ftl(HttpServletRequest request) throws IOException {
		return "about/contract";
	}

	/**
	 * 友情链接页面
	 */
	@RequestMapping(value = PageForwardConstants.FLINK_PAGE, method = RequestMethod.GET)
	public String flink_ftl(HttpServletRequest request) throws IOException {
		return "about/flink";
	}

	/**
	 * 加入我们页面
	 */
	@RequestMapping(value = PageForwardConstants.JOINUS_PAGE, method = RequestMethod.GET)
	public String joinus_ftl(HttpServletRequest request) throws IOException {
		return "about/joinus";
	}
	
	
	/**
	 * 服务协议页面
	 */
	@RequestMapping(value = PageForwardConstants.SERVICE_FLOW, method = RequestMethod.GET)
	public String service_flow_ftl(HttpServletRequest request) throws IOException {
		return "about/serviceflow";
	}

	/**
	 * 找回密码页面
	 */
	@RequestMapping(value = PageForwardConstants.FIND_PASSWORD_PAGE, method = RequestMethod.GET)
	public String find_password_ftl(HttpServletRequest request) {
		return "public/find_password";
	}

	/**
	 * 登录页面
	 */
	@RequestMapping(value = PageForwardConstants.LOGIN_PAGE, method = RequestMethod.GET)
	public String login_ftl(HttpServletRequest request) {
		int loginFailedTimes = 0;

		if (request.getSession().getAttribute(CommonConstants.USER_LOGIN_FAILED_TIMES) != null) {
			loginFailedTimes = (int) request.getSession().getAttribute(
					CommonConstants.USER_LOGIN_FAILED_TIMES);
		}

		if (loginFailedTimes > publicConfig.getUserLoginLimitedTimes()) {
			request.setAttribute("loginFailedTimeExceeded", 1);
		} else {
			request.setAttribute("loginFailedTimeExceeded", 0);
		}

		return "public/login";
	}

	/**
	 * 支付账号页面
	 */
	@RequestMapping(value = PageForwardConstants.PAY_PAGE, method = RequestMethod.GET)
	public String pay_ftl(HttpServletRequest request) {
		return "public/pay";
	}

	/**
	 * 如何使用页面
	 */
	@RequestMapping(value = PageForwardConstants.QA_PAGE, method = RequestMethod.GET)
	public String qa_ftl(HttpServletRequest request) {
		return "public/qa";
	}

	/**
	 * 注册页面
	 */
	@RequestMapping(value = PageForwardConstants.REGISTER_PAGE, method = RequestMethod.GET)
	public String register_ftl(HttpServletRequest request) {
		return "public/register";
	}

	/**
	 * 重置提醒
	 */
	@RequestMapping(value = PageForwardConstants.RESET_NOTICE_PAGE, method = RequestMethod.GET)
	public String reset_notice_ftl(HttpServletRequest request) {
		return "public/reset_notice";
	}

	/**
	 * 重置成功
	 */
	@RequestMapping(value = PageForwardConstants.RESET_OK_PAGE, method = RequestMethod.GET)
	public String reset_ok_ftl(HttpServletRequest request) {
		String resetStatus = request.getSession().getAttribute("resetStatus").toString();
		request.setAttribute("resetStatus", resetStatus);
		request.getSession().removeAttribute("resetStatus");
		return "public/reset_ok";
	}

	/**
	 * 服务商列表
	 */
	@RequestMapping(value = PageForwardConstants.MEMBER_LIST, method = RequestMethod.GET)
	public String viewMemberList(HttpServletRequest request) {
		int userType = 0;
		SsoUserVo ssoUserVo =  CommonUtil.getCurrentLoginUser();
		if(ssoUserVo==null)userType = 1;
//		else userType = CommonUtil.getCurrentLoginUser().getUserInfoVo().getUserType();
//		int totalCount = userService.getDisplayProviderListCount(userType, "", 2);
//		
//		request.setAttribute("totalCount", totalCount);
		UserInfoVo userInfo = new UserInfoVo();
		userInfo.setUserType(1);
		if(ssoUserVo!=null)userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		request.setAttribute("userInfo", userInfo);

		return "public/comp_list";
	}
	
	/**
	 * 首页案例详情
	 */
	@RequestMapping(value = PageForwardConstants.CUSTOMER_CASE_DETAIL, method = RequestMethod.GET)
	public String queryCustomerCaseDetail(HttpServletRequest request,long caseId) {
        CustomerCaseVo caseVo = homepageService.getCustomerCaseByCaseId(caseId);
        request.setAttribute("customerCase", caseVo);
        
		return "public/customercase";
	}
	
	/**
	 * 客户案例聚合页面
	 */
	@RequestMapping(value = PageForwardConstants.CUSTOMER_USECASES,method = RequestMethod.GET)
	public String queryCustomerCaseList(HttpServletRequest request){
		  List<CustomerCaseVo> customerCases = homepageService.getCustomerCases(0); //1表示是在首页显示
		  request.setAttribute("customerCases", customerCases);
		  
		  return "public/customercases";
	}
	
	/**
	 * 解决方案详情
	 */
	@RequestMapping(value = PageForwardConstants.SERVICE_SOLUTION_DETAIL,method = RequestMethod.GET)
	public String querySolutionDeatil(HttpServletRequest request){
		  return "public/solution";
	}
	
	/**
	 * 软件众包详情
	 */
	@RequestMapping(value = PageForwardConstants.SERVICE_CROWDSOURCING_DETAIL,method = RequestMethod.GET)
	public String queryCrowdsourcing(HttpServletRequest request){		  
		  return "public/crowdsourcing";
	}
	
	/**
	 * 客户案例聚合页面
	 */
	@RequestMapping(value = PageForwardConstants.SERVICE_MAINTAINANCE_DETAIL,method = RequestMethod.GET)
	public String queryMaintainance(HttpServletRequest request){		  
		  return "public/maintainance";
	}
	
	
}
