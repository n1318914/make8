package com.yundaren.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.service.RegionService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.basedata.vo.RegionVo;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.support.service.ProjectInSelfRunService;
import com.yundaren.support.service.ProjectOperationLogService;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.ProjectOperationLogVo;
import com.yundaren.support.vo.ProjectSelfRunPushVo;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.NoticeStruct;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
public class ProjectInSelfRunController {
	@Setter
	private UserService userService;

	@Setter
	private DictService dictService;
	
	@Setter 
	private ProjectInSelfRunService projectInSelfRunService;
	
	@Setter
	private RegionService regionService;
	
	@Setter
	private ProjectOperationLogService projectOperationLogService;
	
	@RequestMapping(value = PageForwardConstants.PROJECT_IN_SELF_RUN_VIEW_PAGE, method = RequestMethod.GET)
	public String getProjectInSelfRun(HttpServletRequest request,HttpServletResponse response, String id) {
		int userType = CommonUtil.getCurrentLoginUser().getUserInfoVo().getUserType();
		int accessType = 1;// -1. 管理员   0 . 创建人   1. 开发人员(默认)    2.顾问
		boolean isAdmin = userType==-1;		
		boolean isAccess = true;
		String commonUserId = CommonUtil.getCurrentLoginUser().getUserInfoVo().getId()+"";	
		//查找项目需求
		ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjectInSelfRun(id);
		if(!isAdmin){
			String consultantorId = "-1";
			boolean isConsultantor =  userType == 2;
			//2.顾问
			if(isConsultantor){
				/*accessType  = 2;
				consultantorId = CommonUtil.getCurrentLoginUser().getUserInfoVo().getId()+"";
				isAccess = projectInSelfRunService.getCondulterInProjectPlaning(id, consultantorId)>0;	*/
				isAccess = true;
			}else{
				isAccess = projectInSelfRunService.isPropertyContaining(id,commonUserId,commonUserId);	
			}		
		}else{
			//-1.管理员
			accessType = -1;
		}	
		//0.创建人
		if(Long.parseLong(commonUserId)==projectVo.getCreatorId()){
			accessType = 0;
			if(isAdmin)accessType = -1;
			isAccess = true;
		}
		if(isAccess){
			List<RegionVo> provinceList = regionService.getAllProvinces();
			// 获取服务商服务领域信息
			List<DictItemVo> caseTypeList = dictService.getDictItemByType(CommonConstants.SERVICE_FIELD);
			
			UserInfoVo creator = userService.getUserInfoByID(projectVo.getCreatorId());
			UserInfoVo consultant = userService.getUserInfoByID(projectVo.getConsultantId());
			
			projectVo.setCreator(creator);
			projectVo.setConsultant(consultant);
			//角色
			List<DictItemVo> dictItemList =  dictService.getAllDictItem();
			request.setAttribute("dictItemList", dictItemList);
			request.setAttribute("projectInSelfRun", projectVo);
			//已添加角色
			List<UserInfoVo> planUserInfoList = userService.getProjectInSelfRun(id); 	
//			System.out.println(planUserInfoList);
			request.setAttribute("planUserInfoList", planUserInfoList);
			request.setAttribute("accessType", accessType);
			//顾问
			request.setAttribute("consultant", consultant);
			//省份
			request.setAttribute("provinceList", provinceList);
			//服务领域
			request.setAttribute("caseTypeList", caseTypeList);
			//当前用户ID
			request.setAttribute("commonUserId", Integer.parseInt(commonUserId));
			
			//项目日志信息
			List<ProjectOperationLogVo> projectLogs = projectOperationLogService.getProjectOperationLog(new Long(projectVo.getId()),new Long(commonUserId));
			request.setAttribute("projectLogs",projectLogs);
			
			//项目报名列表
			List<ProjectSelfRunPushVo> enrollDevelopers = projectInSelfRunService.getEnrollDevlopersbyProjectId(new Long(projectVo.getId()));
			request.setAttribute("enrollDevelopers",enrollDevelopers);
			
			//可以选择的角色
			List<DictItemVo> projectDevRoles = dictService.getDictItemByType("projectDevRole");
			request.setAttribute("projectDevRoles",projectDevRoles);
			
			//选中的开发人员
			if(projectVo.getStatus() == 1 || projectVo.getStatus() == 2){ //开发中
				request.setAttribute("chosenDevList", projectInSelfRunService.getProjectSelfRunChosenDevlopers(Long.parseLong(projectVo.getId())));
			}
	
			return "home/request_handle";	
		}else{
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "/about/aboutus";
		}
	}
	
	/**
	 * 推送项目详情页
	 */
	@RequestMapping(value = PageForwardConstants.PUSH_PROJDETAIL, method = RequestMethod.GET)
	public String push_projdetail_ftl(HttpServletRequest request,String projectId) {
		UserInfoVo currentUser = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		long uid = currentUser.getId();

		boolean isJoined = false;
		// 判断当前用户是否已经报名过
		/*List<ProjectSelfRunPushVo> listData = projectInSelfRunService.getJoinListByDID(uid);
		for (ProjectSelfRunPushVo obj : listData) {
			if (obj.getProjectId().equals(projectId) && (obj.getStatus() == 2 || obj.getStatus() == 4 ||
				obj.getStatus() == 5 || obj.getStatus() == 6)) {
				isJoined = true;
				break;
			}
		}*/
		ProjectSelfRunPushVo devVo = projectInSelfRunService.getProjectInSelfRunPushInfoByID(Long.parseLong(projectId),uid);
		
		if(devVo != null){
			if(devVo.getStatus() == 2 || devVo.getStatus() == 4 || devVo.getStatus() == 5 || devVo.getStatus() == 6){
					isJoined = true;
			}
		}

		ProjectInSelfRunVo projectVo = projectInSelfRunService.getProjectInSelfRun(projectId);
		
		//当用户为普通用户时，项目状态为待启动才允许推送该项目(避免普通用户能够访问到其他项目信息)
		if(currentUser.getUserType()==0 || currentUser.getUserType()==1){
			if(projectVo.getStatus()!=0 && !isJoined){
				return projectNotFound(request);
			}
		}
		
		//增加浏览次数
		ProjectInSelfRunVo projectVoWithoutDecoration = projectInSelfRunService.getProjctInSelfRunWithoutDecorate(projectVo.getId());
		
		int viewCount = projectVoWithoutDecoration.getViewCount() + 1;
		projectVoWithoutDecoration.setViewCount(viewCount);
		projectInSelfRunService.updateProjectInSelfRun(projectVoWithoutDecoration);
		
		
		request.setAttribute("projectInSelfRun", projectVo);
		request.setAttribute("isJoined", isJoined);
		
		
		//用户当前的资料和认证情况
		long userId = CommonUtil.getCurrentLoginUser().getUserId();
		boolean isUserInfoComplete = userService.isUserInfoComplete(userId);
		boolean isIdentifiedPassed = userService.isUserInfoIdentifiedPassed(userId);
		request.setAttribute("isUserInfoComplete", isUserInfoComplete);
		request.setAttribute("isIdentifiedPassed",isIdentifiedPassed);
		
		//招募角色
		List<DictItemVo> enrollRoleList = dictService.getDictItemByType("projectDevRole");
		request.setAttribute("enrollRoleList", enrollRoleList);
		
		return "home/push_projdetail";
	}
	
	/**
	 * 项目未找到页面
	 * @param request
	 * @param response
	 * @return
	 */
	private String projectNotFound(HttpServletRequest request){
		NoticeStruct struct = new NoticeStruct();
		struct.setTitle("项目未找到");
		struct.setContent("<p><h2>项目未找到</h2></p>");
		request.setAttribute("notice", struct);
		return "public/notice_no_jump";
	}
	@RequestMapping(value = PageForwardConstants.PROJECT_REQUEST, method = RequestMethod.GET)
	public String request_ftl(HttpServletRequest request, HttpServletResponse response) {
		SsoUserVo ssoUser = CommonUtil.getCurrentLoginUser();
	   
		request.setAttribute("ssoUser", ssoUser);
		List<DictItemVo> listDictItem = dictService.getAllDictItem();
		request.setAttribute("listDictItem", listDictItem);

		return "home/request";
	}
	
	/**
	 * 审核项目列表
	 */
	@RequestMapping(value = PageForwardConstants.PROJECT_LIST_MANAGER_PAGE, method = RequestMethod.GET)
	public String projects_review_ftl(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SsoUserVo ssoUserVo = CommonUtil.getCurrentLoginUser();
		String  userType = "-2";
		if(ssoUserVo!=null){
			userType = ssoUserVo.getUserInfoVo().getUserType()+"";
		}
		if(Integer.parseInt(userType)!=-1&&Integer.parseInt(userType)!=2){
			try {
				response.sendRedirect("/");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "/about/aboutus";
		}else return "home/projects_review";
	}
	
	/**
	 * 项目修改
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = PageForwardConstants.PROJECT_MODIFY_PAGE, method = RequestMethod.GET)
	public String projectModify(HttpServletRequest request,String id){
		request.setAttribute("projectId", id);
	    SsoUserVo ssoUserVo = CommonUtil.getCurrentLoginUser();
	    UserInfoVo userVo = userService.getUserInfoByID(ssoUserVo.getUserId());
	    List<UserInfoVo> consultants = userService.getAllConsultants();
	    request.setAttribute("userType", userVo.getUserType());
	    request.setAttribute("consultants", consultants);
	    
		return "home/request_modify";
	}
	
}
