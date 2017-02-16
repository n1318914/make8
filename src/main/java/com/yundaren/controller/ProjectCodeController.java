package com.yundaren.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yundaren.basedata.service.DictService;
import com.yundaren.basedata.vo.DictItemVo;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.ProjectStatusEnum;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.GitHttpClient;
import com.yundaren.common.util.GogsUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.service.ProjectService;
import com.yundaren.support.service.YunConnectService;
import com.yundaren.support.vo.ProjectJoinVo;
import com.yundaren.support.vo.ProjectVo;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.NoticeStruct;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
public class ProjectCodeController {

	@Setter
	private UserService userService;
	/**
	 * 代码托管
	 */
	@RequestMapping(value = PageForwardConstants.CODE_TMPL, method = RequestMethod.GET)
	public String codeTmpl(HttpServletRequest request, String url) {
		GitHttpClient gitHttpClient = GogsUtil.refreshLoginStatus(request,userService);
		String page = "";		
		if (gitHttpClient == null) {
			request.setAttribute("status", -1);
		} else {
			boolean onlyGatach = false;
			String urlcomb[] = url.split("/");
			int count = 0 ;
			for(int i=0;i<urlcomb.length;i++){
				if(!urlcomb[i].equals(""))count++;
			}
			onlyGatach = count<=1;
			if(onlyGatach){
				page = gitHttpClient.viewPage("/404.html");
				request.setAttribute("status", 0);
			}else{
				String requestUrl = "/"+url;
				page = gitHttpClient.viewPage(requestUrl);				
				request.setAttribute("status", 0);	
			}		
		}
		request.setAttribute("page", page);
		request.setAttribute("url", url);
		return "home/project_code";
	}
}
