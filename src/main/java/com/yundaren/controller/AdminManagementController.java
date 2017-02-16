package com.yundaren.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.service.RegionService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.basedata.vo.RegionVo;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.LabelUtil;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.service.ProjectInSelfRunService;
import com.yundaren.support.service.ProjectService;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.WeixinProjectVo;
import com.yundaren.user.po.EmployeeTeamProjectExperiencePo;
import com.yundaren.user.service.UserExperienceService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.EmployeeTeamProjectExperienceVo;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
@Slf4j
public class AdminManagementController {
	
	@Setter
	private ProjectService projectService;
	@Setter
	private UserService userService;
	@Setter
	private IdentifyService identifyService;
	@Setter
	private DictService dictService;
	@Setter
	private UserExperienceService userExperienceService;	
	@Setter
	private ProjectInSelfRunService projectInSelfRunService;
	@Setter
	private RegionService regionService;

	/**
	 * 用户列表
	 */
	@RequestMapping(value = PageForwardConstants.USER_REVIEW_MANAGER_PAGE, method = RequestMethod.GET)
	public String users_review_ftl(HttpServletRequest request) throws IOException {	
		return "admin/users_review";
	}
	
	/**
	 * 预约列表
	 */
	@RequestMapping(value = PageForwardConstants.RESERVE_LIST_PAGE, method = RequestMethod.GET)
	public String reserve_list_ftl(HttpServletRequest request) throws IOException {
				return "admin/reserve_review";
	}
	
	/**
	 * 预约顾问发布
	 */
	@RequestMapping(value = PageForwardConstants.RESERVE_PUBLISH, method = RequestMethod.GET)
	public String detailReserve(HttpServletRequest request, long id) {
		WeixinProjectVo reserveInfo = projectService.getReserveByID(id);
		request.setAttribute("reserve", reserveInfo);		
		return "admin/reserve_publish";
	}
	
	/**
	 * 服务商录入页面分类跳转
	 */
	@RequestMapping(value = PageForwardConstants.MEMBER_MODIFY, method = RequestMethod.GET)
	public String viewMemberInfo(HttpServletRequest request) {
		List<DictItemVo> listDictItem = dictService.getAllDictItem();
		List<RegionVo> resultList = regionService.getAllProvinces();
		request.setAttribute("listDictItem", listDictItem);		
		String uid = request.getParameter("uid");
		if(uid!=null){
			UserInfoVo userInfo = userService.getUserInfoByID(Long.parseLong(uid));
			IdentifyVo identifyVo =  identifyService.getIdentifyByUID(Long.parseLong(uid));
			identifyVo = identifyVo==null?new IdentifyVo():identifyVo;
			userInfo.setIdentifyInfo(identifyVo);
			List<EmployeeTeamProjectExperienceVo> projList = userExperienceService.getUserExpListById(Long.parseLong(uid), EmployeeTeamProjectExperienceVo.class);
			projList = projList==null?new ArrayList<EmployeeTeamProjectExperienceVo>():projList;
			userInfo.setEmployeeProjectExperience(projList);
			request.setAttribute("userInfo", userInfo);
			for(RegionVo region:resultList){				
				if(region.getId()==userInfo.getRegionId())userInfo.setProvinceId(region.getParentId());
			}
			List<RegionVo> cityList = regionService.getCitysByProvinceId(userInfo.getProvinceId());
			request.setAttribute("cityList", cityList);
		}else {			
			UserInfoVo vo = new UserInfoVo();
			vo.setIdentifyInfo(new IdentifyVo());
			vo.setEmployeeProjectExperience(new ArrayList<EmployeeTeamProjectExperienceVo>());
			for(RegionVo region:resultList){				
				if(region.getId()==vo.getRegionId())vo.setProvinceId(region.getParentId());
			}		
			request.setAttribute("userInfo", vo);
		}
		request.setAttribute("provinceList", resultList);
		return "admin/compinfo_modify";
	}
	
	/***
	 * 导入用户数据维护
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = PageForwardConstants.USER_INFO_IMPORT, method = RequestMethod.GET)
	public String userInfoImport(HttpServletRequest request, String projectId) {
		
		// 分类列表
		List<DictItemVo> listDictItem = dictService.getAllDictItem();
		// 区域列表
		List<RegionVo> listRegion = LabelUtil.getRegionList();
		request.setAttribute("listDictItem", listDictItem);
		request.setAttribute("listRegion", listRegion);
		
		if (!StringUtils.isEmpty(projectId)) {
			ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjectInSelfRun(projectId);
			request.setAttribute("projectVo", projectVo);
		}
		
		return "admin/user_info_import";
	}
	
	/***
	 * 数据分析
	 * @param request
	 * @return
	 */
	@RequestMapping(value = PageForwardConstants.DATA_ANYLIZE, method = RequestMethod.GET)
	public String dataAnylize(HttpServletRequest request) {
		return "admin/dataAnylize";
	}
	
	/***
	 * 群发邮件
	 * @param request
	 * @return
	 */
	@RequestMapping(value = PageForwardConstants.MAIL_SEND_BATCH, method = RequestMethod.GET)
	public String mailSendBatch(HttpServletRequest request) {
		return "admin/mail_send";
	}

	/**
	 * 项目审核列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = PageForwardConstants.REQUEST_REVIEW_LIST, method = RequestMethod.GET)
	public String requestListReview(HttpServletRequest request) {
		return "admin/requests_review";
	}
	
	/**
	 * 项目审核详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = PageForwardConstants.REQUEST_REVIEW, method = RequestMethod.GET)
	public String requestDetailReview(HttpServletRequest request, String id) {
		ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjectInSelfRun(id);
		UserInfoVo creator = userService.getUserInfoByID(projectVo.getCreatorId());
		UserInfoVo consultant = userService.getUserInfoByID(projectVo.getConsultantId());
		projectVo.setConsultant(consultant);
		projectVo.setCreator(creator);
		request.setAttribute("projectInSelfRun", projectVo);
		
		// 分类列表
		List<DictItemVo> listDictItem = dictService.getAllDictItem();
		request.setAttribute("listDictItem", listDictItem);
		
		return "admin/request_review";
	}
	
}
