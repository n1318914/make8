package com.yundaren.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.LabelUtil;
import com.yundaren.common.util.PageResult;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.service.ProjectInSelfRunService;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.support.vo.ProjectSelfRunPushVo;
import com.yundaren.user.service.UserExperienceService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.EmployeeEduExperienceVo;
import com.yundaren.user.vo.EmployeeJobExperienceVo;
import com.yundaren.user.vo.EmployeeProductVo;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;
import com.yundaren.user.vo.NoticeStruct;
import com.yundaren.user.vo.UserAccountVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
public class UserInfoController {

	@Setter
	private DomainConfig domainConfig;

	@Setter
	private UserService userService;

	@Setter
	private IdentifyService identifyService;

	@Setter
	private UserExperienceService userExperienceService;

	@Setter
	private DictService dictService;

	/**
	 * 用户信息模板页面
	 */
	@RequestMapping(value = PageForwardConstants.USERS_INFO_PAGE, method = RequestMethod.GET)
	public String userInfo(HttpServletRequest request) {
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		currentUser = userService.getUserInfoByID(currentUser.getId());
		
		long userId = currentUser.getId();
		
		Object fromNavigation = request.getParameter("nav");
		
		if(fromNavigation != null && ((String)fromNavigation).equals("1")){
			request.setAttribute("showAvaliableJoinProject", 1);
		}

		// 设置用户认证信息
		IdentifyVo identifyInfo = identifyService.getIdentifyByUID(userId);

		if (identifyInfo == null) {
			identifyInfo = new IdentifyVo();
		}
		currentUser.setIdentifyInfo(identifyInfo);

		// 设置用户经历
		if (identifyInfo.getCategory() == 0) {
			currentUser.setEmployeeEduExperience(userExperienceService.getUserExpListById(userId,
					EmployeeEduExperienceVo.class));
			currentUser.setEmployeeJobExperience(userExperienceService.getUserExpListById(userId,
					EmployeeJobExperienceVo.class));
			currentUser.setEmployeeProduct(userExperienceService.getUserExpListById(userId,
					EmployeeProductVo.class));
		} else if (identifyInfo.getCategory() == 1){
			currentUser.setEmployeeProjectExperience(userExperienceService.getUserExpListById(userId,
					EmployeeTeamProjectExperienceVo.class));
		} else{
			currentUser.setEmployeeEduExperience(userExperienceService.getUserExpListById(userId,
					EmployeeEduExperienceVo.class));
			currentUser.setEmployeeJobExperience(userExperienceService.getUserExpListById(userId,
					EmployeeJobExperienceVo.class));
			currentUser.setEmployeeProduct(userExperienceService.getUserExpListById(userId,
					EmployeeProductVo.class));
			currentUser.setEmployeeProjectExperience(userExperienceService.getUserExpListById(userId,
					EmployeeTeamProjectExperienceVo.class));
		}
		
		// 用户信息修改中的操作，用于前台定位TAB页 (0代表基本信息 1代表认证信息)
		Object operateSession = request.getSession().getAttribute(CommonConstants.USERINFO_OPERATION);
		int userinfoOperate = (operateSession == null) ? 0 : (int) operateSession;
		request.getSession().removeAttribute(CommonConstants.USERINFO_OPERATION);

		// 是否修改用户认证，用于前台展现认证页面
		/*Object identifyModifySession = request.getSession().getAttribute(CommonConstants.IS_IDENTIFY_MODIFY);
		boolean isIdentifyModify = (identifyModifySession == null) ? false : (boolean) identifyModifySession;
		request.getSession().removeAttribute(CommonConstants.IS_IDENTIFY_MODIFY);*/

		boolean isIdentifyModify = false;
		int showIdentifyInfo = 0;

		if (identifyInfo != null) {
			if (identifyInfo.getStatus() == 2 || identifyInfo.getStatus() == 3 || 
			    identifyInfo.getStatus() == 4 || currentUser.getIdentifyStep() > 1) {
				isIdentifyModify = true;
			}

			if (userinfoOperate == 1 || identifyInfo.getStatus() == 2 ||
				identifyInfo.getStatus() == 3 || identifyInfo.getStatus() == 4) {
				showIdentifyInfo = 1;
			}
		}

		// 分类列表
		List<DictItemVo> listDictItem = dictService.getAllDictItem();

		// 用户基本信息是否完善
		boolean isComplete = userService.isUserInfoComplete(userId);
		request.setAttribute("listDictItem", listDictItem);
		request.setAttribute("userInfo", currentUser);
		request.setAttribute("isComplete", isComplete);
		request.setAttribute("isIdentifyModify", isIdentifyModify);
		request.setAttribute("operation", userinfoOperate);
		request.setAttribute("showIdentifyInfo", showIdentifyInfo);
		
		//用户可参与项目
		return "home/userinfo";
	}
	
	/**
	 * 用户财务中心模块
	 */
	@RequestMapping(value = PageForwardConstants.USERS_ACCOUNT_PAGE, method = RequestMethod.GET)
	public String useraccount(HttpServletRequest request,HttpServletResponse resp) {
		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		//TODO  获取用户提现账号， 用户总收入， 用户可用余额
		UserAccountVo accountVo = userService.getUseraccountById((int)userInfo.getAccountId());
		
		request.setAttribute("userInfo", userInfo);  //用户信息
		request.setAttribute("accountInfo", accountVo);        //用户支付账号信息
		
		//获取用户总收入
		double totalIncome = userService.getUserTotalIncome((int)userInfo.getId());
		//获取用户总支出
		double totalOutcome = userService.getUserTotalOutcome((int)userInfo.getId());
		BigDecimal totalIncomeBD = new BigDecimal(Double.toString(totalIncome));
		BigDecimal totalOutcomeBD = new BigDecimal(Double.toString(totalOutcome));
		BigDecimal totalAvalible = new BigDecimal("0");
		//使用bigdecimal 计算可用余额
		totalAvalible = totalIncomeBD.subtract(totalOutcomeBD);
		NumberFormat formatter = new DecimalFormat("###,###");
//		formatter.setMaximumFractionDigits(2);     
//		formatter.setRoundingMode(RoundingMode.FLOOR);
		request.setAttribute("totalIncome", formatter.format(totalIncome));    //总收入
		request.setAttribute("totalAvalible", formatter.format(totalAvalible.doubleValue()));   //可用余额
		//用户可参与项目
		return "home/useraccount";
	}
	
	/**
	 * 跳转服务商个人首页
	 */
	@RequestMapping(value = PageForwardConstants.USERS_HOME_PAGE, method = RequestMethod.GET)
	public String userHomePage(HttpServletRequest request, String name) {

		int uid ;
		UserInfoVo userInfo = userService.getUserInfoByNickname(name);
		if (userInfo == null || userInfo.getIsDelete() == 1) {
			// 非服务商不显示
			return userNotFound(request);
		}
		uid = Integer.parseInt(userInfo.getId()+"");
		IdentifyVo identifyInfo = identifyService.getIdentifyByUID(userInfo.getId());
		userInfo.setIdentifyInfo(identifyInfo);
		if (userInfo.getIdentifyInfo().getCategory() == 0) {
			// 工作经验
			List<EmployeeJobExperienceVo> employeeJobExperienceList = userExperienceService
					.getUserExpListById(uid, EmployeeJobExperienceVo.class);
			userInfo.setEmployeeJobExperience(employeeJobExperienceList);
			// 教育背景
			List<EmployeeEduExperienceVo> employeeEduExperienceList = userExperienceService
					.getUserExpListById(uid, EmployeeEduExperienceVo.class);
			userInfo.setEmployeeEduExperience(employeeEduExperienceList);
			// 项目作品
			List<EmployeeProductVo> employeeProductList = userExperienceService.getUserExpListById(uid,
					EmployeeProductVo.class);
			userInfo.setEmployeeProduct(employeeProductList);
		} else {
			// 项目经验
			List<EmployeeTeamProjectExperienceVo> employeeTeamProjectExperienceList = userExperienceService
					.getUserExpListById(userInfo.getId(), EmployeeTeamProjectExperienceVo.class);
			userInfo.setEmployeeProjectExperience(employeeTeamProjectExperienceList);
		}
		request.setAttribute("userInfo", userInfo);
		// 胜任工作
		String[] abilityItemList = LabelUtil.getItemName("ability", userInfo.getMainAbility());
		request.setAttribute("abilityItemList", abilityItemList);
		// 服务领域
		String[] caseTypeItemList = LabelUtil.getItemName("caseType", userInfo.getCaseType());
		request.setAttribute("caseTypeItemList", caseTypeItemList);
		// 擅长技能
		String[] skillItemList = LabelUtil.getItemName("cando", userInfo.getCando());
		request.setAttribute("skillItemList", skillItemList);
		return "home/usercenter";
	}

	/* 用户未找到页面*/
	private String userNotFound(HttpServletRequest request) {
		NoticeStruct struct = new NoticeStruct();
		struct.setTitle("用户未找到");
		struct.setContent("<p><h2>用户未找到</h2></p>");
		request.setAttribute("notice", struct);
		return "public/notice_no_jump";
	}

	/**
	 * 认证信息修改完成后的跳转
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = PageForwardConstants.USERS_IDENTIFY_REDIRECT, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> redirectModifyIdentify(HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		request.getSession().setAttribute(CommonConstants.USERINFO_OPERATION, 1);
		request.getSession().setAttribute(CommonConstants.IS_IDENTIFY_MODIFY, true);
		Map<String, Object> result = ResponseMapUtil.getRedirectMap(domainConfig.getHost(), "/home/userinfo");
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	/**
	 * 认证信息修改的跳转
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = PageForwardConstants.USERS_A_IDENTIFY_REDIRECT, method = RequestMethod.GET)
	public String redirectModifyIdentify02(HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		String typeObject = request.getParameter("mtype");
	
		UserInfoVo loginUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		IdentifyVo identifyVo = identifyService.getIdentifyByUID(loginUser.getId());
		
		//tricky of not updating image path
		identifyVo.setBusinessLicenseImg(null);
		identifyVo.setIdCardImg(null);
		
		
		UserInfoVo userInfoVo = userService.getUserInfoByID(loginUser.getId());
		
		
		if(typeObject != null){
			String mType = (String)typeObject;
			
			if(mType.equals("11")){ //审核中修改
				identifyVo.setStatus(3); 
			}else if(mType.equals("12")){ //认证完修改
				identifyVo.setStatus(4);
			}
			
			userInfoVo.setIdentifyStep(1); //修改步骤重置为1
			userService.updateUserInfo(userInfoVo);
			identifyService.updateIdentifyByUID(identifyVo);
		}
		
		//request.getSession().setAttribute(CommonConstants.IS_IDENTIFY_MODIFY, true);
		
		try {
			response.sendRedirect(PageForwardConstants.USERS_INFO_PAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/about/aboutus";
	}
}
