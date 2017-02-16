package com.yundaren.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.ProjectStatusEnum;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.service.ProjectService;
import com.yundaren.support.service.YunConnectService;
import com.yundaren.support.vo.ProjectJoinVo;
import com.yundaren.support.vo.ProjectVo;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.NoticeStruct;
import com.yundaren.user.vo.UserInfoVo;

@Controller
public class ProjectController {

	@Setter
	private ProjectService projectService;

	@Setter
	private UserService userService;

	@Setter
	private YunConnectService yunConnectService;

	@Setter
	private DomainConfig domainConfig;
	
	@Setter
	private DictService dictService;

	/**
	 * 需求详情页
	 */
	@RequestMapping(value = PageForwardConstants.PROJECT_VIEW_PAGE, method = RequestMethod.GET)
	public String projectDetails(HttpServletRequest request, String id) {
		ProjectVo projectVo = projectService.getProjectDetailsById(id);
		if (projectVo == null) {
			return projectNotFound(request);
		}

		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		// 是否自己发的需求，自己发的能看到竞标者列表，不是自己发的能看到竞标按钮
		boolean isSelf = (userInfo.getId() == projectVo.getCreatorId());
		boolean isAdmin = false;// 是否管理员
		isAdmin = (userInfo.getUserType() == -1);
		// 如果是打回或审核中状态不允许其他人访问
		if ((!isSelf && !isAdmin)
				&& (projectVo.getBackgroudStatus() == ProjectStatusEnum.BACK_CHECK_INVALID || projectVo
						.getBackgroudStatus() == ProjectStatusEnum.BACK_CHECKING)) {
			return projectNotFound(request);
		}

		// 当前用户是否重复竞标
		ProjectJoinVo joinVo = projectService.getJoinInfo(id, userInfo.getId());
		boolean isDuplicate = false;
		if (joinVo != null) {
			isDuplicate = true;
			request.setAttribute("joinInfo", joinVo);
		}

		// 是否已选标
		ProjectJoinVo selectJoinVo = projectService.getSelectedJoinInfo(id);
		projectVo.setSelectedJoin(selectJoinVo);
		boolean isSelected = false;
		boolean isSelectUser = false;
		boolean isService = false;// 是否是服务商

		if (selectJoinVo != null) {
			isSelected = true;
			// 当前用户是否为选中用户
			if (userInfo.getId() == selectJoinVo.getUserId()) {
				isSelectUser = true;
			}
		}
		isService = (userInfo.getUserType() == 1);
		boolean isIdentifiedUser = false;

		if (userInfo.getIdentifyInfo() != null && userInfo.getIdentifyInfo().getStatus() == 1) {
			isIdentifiedUser = true;
		}

		request.setAttribute("project", projectVo);
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("isSelf", isSelf);
		request.setAttribute("isDuplicate", isDuplicate);
		request.setAttribute("isSelected", isSelected);
		request.setAttribute("isSelectedUser", isSelectUser);
		request.setAttribute("isService", isService);
		request.setAttribute("isIdentifiedUser", isIdentifiedUser);

		return "home/project_detail";
	}

	/* 用户未找到页面*/
	private String projectNotFound(HttpServletRequest request) {
		NoticeStruct struct = new NoticeStruct();
		struct.setTitle("项目未找到");
		struct.setContent("<p><h2>项目未找到</h2></p>");
		request.setAttribute("notice", struct);
		return "public/notice_no_jump";
	}

//	/**
//	 * 跳转修改需求ftl页面
//	 */
//	@RequestMapping(value = PageForwardConstants.PROJECT_MODIFY_PAGE, method = RequestMethod.GET)
//	public String redirectProjectModifyPage(HttpServletResponse response, HttpServletRequest request,
//			String id) {
//		UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
//		boolean isAdmin = (userInfo.getUserType() == -1);
//		ProjectVo projectVo = projectService.getProjectInfoById(id);
//		int backgroudStatus = projectVo.getBackgroudStatus();
//		// 如果是审核通过的项目雇主不允许修改
//		if (!isAdmin
//				&& !(backgroudStatus == ProjectStatusEnum.BACK_CHECK_INVALID
//						|| backgroudStatus == ProjectStatusEnum.BACK_CHECKING || backgroudStatus == ProjectStatusEnum.BACK_CLOSED)) {
//			return projects_ftl(response);
//		}
//
//		UserInfoVo publisher = userService.getUserInfoByID(projectVo.getCreatorId());
//		projectVo.setPublisherInfo(publisher);
//
//		request.setAttribute("project", projectVo);
//		request.setAttribute("isAdmin", isAdmin);
//		return "home/request_modify";
//	}

	/**
	 * 我要发布页面
	 */
	/*@RequestMapping(value = PageForwardConstants.PROJECT_REQUEST, method = RequestMethod.GET)
	public String request_ftl(HttpServletRequest request, HttpServletResponse response) {
		/*UserInfoVo userInfo = CommonUtil.getCurrentLoginUser().getUserInfoVo();
		// 不是雇主不允许发布需求
		if (userInfo.getUserType() != 0) {
			return projects_ftl(response);
		}
		request.setAttribute("userInfo", userInfo);
		List<DictItemVo> listDictItem = dictService.getAllDictItem();
		request.setAttribute("listDictItem", listDictItem);

		return "home/request";
	}*/

	/**
	 * 项目市场
	 */
	@RequestMapping(value = PageForwardConstants.PROJECT_LIST, method = RequestMethod.GET)
	public String projects_ftl(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> result = projectService.getTotalCount();
		for (Map.Entry<String, Object> struct : result.entrySet()) {
			request.setAttribute(struct.getKey(), struct.getValue());
		}
		
		return "home/projects";
	}

	
}
