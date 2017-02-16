package com.yundaren.api.rest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.yundaren.cache.ActiveCodeCache;
import com.yundaren.cache.VerificationCodeCache;
import com.yundaren.common.constants.APIConstants;
import com.yundaren.common.constants.CommonConstants;
import com.yundaren.common.constants.PageForwardConstants;
import com.yundaren.common.constants.RegexUtil;
import com.yundaren.common.constants.UrlRewriteConstants;
import com.yundaren.common.util.CommonUtil;
import com.yundaren.common.util.DESUtil;
import com.yundaren.common.util.ImageCodeUtil;
import com.yundaren.common.util.MD5Util;
import com.yundaren.common.util.ResponseMapUtil;
import com.yundaren.common.util.WeixinMessageDigest;
import com.yundaren.support.config.DomainConfig;
import com.yundaren.support.config.PublicConfig;
import com.yundaren.support.service.IdentifyService;
import com.yundaren.support.service.YunConnectService;
import com.yundaren.support.vo.IdentifyVo;
import com.yundaren.user.service.SsoService;
import com.yundaren.user.service.UserService;
import com.yundaren.user.vo.NoticeStruct;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Controller
@Slf4j
public class WeixinRest {
	/**
	 * 微信口令校验
	 */
	@RequestMapping(value = APIConstants.WEIXIN_SIGN, method = RequestMethod.GET)
	@ResponseBody
	public String weixinSign(HttpServletRequest request) throws IOException {
		String returnEcho = "";
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		boolean isFromWeixin = false;
		System.out.println("<微信口令校验>");
		System.out.println("signature="+signature);
		System.out.println("timestamp="+timestamp);
		System.out.println("nonce="+nonce);
		System.out.println("echostr="+echostr);
		System.out.println(signature!=null&&timestamp!=null&&nonce!=null);
		if(signature!=null&&timestamp!=null&&nonce!=null){
			System.out.println("............qwdwdwq");
			isFromWeixin = new WeixinMessageDigest().validate(signature, timestamp, nonce);	
			System.out.println("isFromWeixin="+isFromWeixin);
			System.out.println("hdnenter>>>>>>>>>>");
		}
		if(isFromWeixin){
			returnEcho = echostr;
			System.out.println("<微信回调>"+returnEcho);
		}
		return returnEcho;
	}
}
