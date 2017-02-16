package com.yundaren.api.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.BeanMapper;

import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.UrlRewriteConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.GitHttpClient;
import com.yundaren.common.util.GogsUtil;
import com.yundaren.common.util.MD5Util;
import com.yundaren.common.util.PageResult;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.service.ProjectInSelfRunPlanService;
import com.yundaren.support.service.ProjectInSelfRunService;
import com.yundaren.support.service.ProjectMailService;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.support.vo.ProjectInSelfRunHandlerVo;
import com.yundaren.support.vo.ProjectInSelfRunMonitorVo;
import com.yundaren.support.vo.ProjectInSelfRunPlanVo;
import com.yundaren.support.vo.ProjectInSelfRunVo;
import com.yundaren.support.vo.ProjectSelfRunPushVo;
import com.yundaren.user.service.SsoService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
@Slf4j
public class ProjectCodeRest {

	@Setter
	private UserService userService;

	/**
	 * 页面代理
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = APIConstants.CODE_PAGE_PROXY, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> codeTmpl(HttpServletRequest request, String url ,String type) throws UnsupportedEncodingException {
		Map<String, Object> result = new HashMap<String, Object>();
		if("download".equals(type)){
			url = GitHttpClient.BASE_URL+url;
			result.put("url", url);
		}else{
			GitHttpClient gitHttpClient = GogsUtil.refreshLoginStatus(request,userService);
			String page = "";
			if (gitHttpClient == null) {
				request.setAttribute("status", -1);
			} else {
				String requestUrl = "/"+url;
				boolean onlyGatach = false;
				String urlcomb[] = requestUrl.split("/");
				int count = 0 ;
				for(int i=0;i<urlcomb.length;i++){
					if(!urlcomb[i].equals(""))count++;
				}
				onlyGatach = count<=1;
				if(onlyGatach){
					page = gitHttpClient.viewPage("/404.html");
					request.setAttribute("status", 0);
				}else{
					String mobile = CommonUtil.getCurrentLoginUser().getUserInfoVo().getMobile();
//					url = new String(url.getBytes("ISO-8859-1"),"UTF-8");
					page = gitHttpClient.viewPage(requestUrl);
					request.setAttribute("status", 0);
				}
			}
			result.put("page", page);
		}
		result.put("status", "1");
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}

	/**
	 * 中断
	 */
	@RequestMapping(value = APIConstants.PAGE_ABORT, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> abortReq(HttpServletRequest request, String url ,String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		GitHttpClient gitHttpClient = GogsUtil.refreshLoginStatus(request,userService);
		String page = "";
		if (gitHttpClient == null) {
			request.setAttribute("status", -1);
		} else {
			String requestUrl = "/"+url;
			boolean onlyGatach = false;
			String urlcomb[] = requestUrl.split("/");
			int count = 0 ;
			for(int i=0;i<urlcomb.length;i++){
				if(!urlcomb[i].equals(""))count++;
			}
			onlyGatach = count<=1;
			if(onlyGatach){
				page = gitHttpClient.viewPage("/404.html");
				request.setAttribute("status", 0);
			}else{
				String mobile = CommonUtil.getCurrentLoginUser().getUserInfoVo().getMobile();
				page = gitHttpClient.viewPage(requestUrl);
				request.setAttribute("status", 0);
			}
		}
		request.setAttribute("page", page);
		result.put("page", page);
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
	
	/**
	 * 查看文件
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = APIConstants.VIEW_FILE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> viewFile(HttpServletRequest request, String url ,String type) throws UnsupportedEncodingException {
		Map<String, Object> result = new HashMap<String, Object>();
		GitHttpClient gitHttpClient = GogsUtil.refreshLoginStatus(request,userService);
		String page = "";
		if (gitHttpClient == null) {
			request.setAttribute("status", -1);
		} else {
			String requestUrl = new String(url.getBytes("ISO-8859-1"),"UTF-8").replaceAll(" ", "%20");		
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
				String mobile = CommonUtil.getCurrentLoginUser().getUserInfoVo().getMobile();
//				url = new String(url.getBytes("ISO-8859-1"),"UTF-8");
//				String requestUrl = "/13878754845/heheda/src/master/新建%20Microsoft%20Office%20Word%202007%20文档.docx";
	
				page = gitHttpClient.viewPage(requestUrl);
				request.setAttribute("status", 0);
			}
			
		}
		result.put("page", page);
		result.put("status", "1");
		return new ResponseEntity<Map>(result, HttpStatus.OK);
	}
}
