package com.yundaren.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yundaren.common.constants.CommonConstants;
import com.yundaren.user.vo.SsoUserVo;
import com.yundaren.user.vo.UserInfoVo;

@Slf4j
public abstract class CommonUtil {

	// 用淘宝IP接口
	private static final String IP_LOOKUP_URL = "http://ip.taobao.com/service/getIpInfo.php";
	
	// 随机字符数组
	private static char[] charSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz"
			.toCharArray();

	// 随机字符数组_数字
	private static char[] charSequence_digt = "0123456789".toCharArray();

	private static Random random = new Random();

	// 随机生成一个字符
	private static String getRandomChar() {
		int index = random.nextInt(charSequence.length);
		return String.valueOf(charSequence[index]);
	}

	public static String getRandomStr(int length) {
		String resultStr = "";
		for (int i = 0; i < length; i++) {
			resultStr += getRandomChar();
		}
		return resultStr;
	}

	// 随机生成一个字符
	private static String getRandomDigt() {
		int index = random.nextInt(charSequence_digt.length);
		return String.valueOf(charSequence_digt[index]);
	}

	public static String getRandomDigt(int length) {
		String resultStr = "";
		for (int i = 0; i < length; i++) {
			resultStr += getRandomDigt();
		}
		return resultStr;
	}

	/**
	 * 获取当前登录的用户信息
	 */
	public static SsoUserVo getCurrentLoginUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		SsoUserVo ssoUserVo = (SsoUserVo) request.getSession().getAttribute(
				CommonConstants.SESSION_LOGIN_USER);
		return ssoUserVo;
	}

	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}
	
	public static void refreshLoginUser(UserInfoVo userInfo) {
		SsoUserVo ssoUserVo = getCurrentLoginUser();
		// 名称显示缩略
		String name = "";
		String displayName = "";
		if (StringUtils.isEmpty(userInfo.getName())) {
			name = ssoUserVo.getLoginName();
			displayName = getLittleStr(10, name);
		} else {
			name = userInfo.getName();
			displayName = getLittleStr(6, name);
		}
		userInfo.setDisplayName(displayName);
		ssoUserVo.setUserInfoVo(userInfo);

		// 刷新seesion
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		request.getSession().setAttribute(CommonConstants.SESSION_LOGIN_USER, ssoUserVo);
	}

	/**
	 * 上传图片保存SESSION
	 */
	public static void setUploadImgSession(HttpServletRequest request, String fileName, int type) {
		String sessionKey = CommonConstants.UPLOAD_IMG_SFZ;
		switch (type) {
			case 1:
				sessionKey = CommonConstants.UPLOAD_IMG_SFZ;
				break;
			case 2:
				sessionKey = CommonConstants.UPLOAD_IMG_ZZJG;
				break;
			case 3:
				sessionKey = CommonConstants.UPLOAD_IMG_SWDJ;
				break;
			case 4:
				sessionKey = CommonConstants.UPLOAD_IMG_YYZZ;
				break;
			case 5:// 序号为5的特殊处理，upyun图片压缩后在通过FTP上传无法显示的历史问题
				log.warn("not allow , upload image type is 5");
				;
				break;
			case 6:
				sessionKey = CommonConstants.UPLOAD_IMG_CMPIC;
				Object prev_fileList = request.getSession().getAttribute(sessionKey);
				String display = request.getParameter("display");
				log.info("NO." + display);
				fileName = (prev_fileList == null ? "" : (prev_fileList.toString() + ",")) + "{" + fileName
						+ "}";
				log.info(fileName);
				break;
			case 7:
				sessionKey = CommonConstants.UPLOAD_IMG_FLINK_LOGO;
				break;
		}
		request.getSession().setAttribute(sessionKey, fileName);
	}

	/**
	 * 上传文件保存SESSION
	 */
	public static void setUploadFileSession(HttpServletRequest request, String fileName, int type) {
		String sessionKey = CommonConstants.FILE_PROJECT_ATTACHMENT;
		switch (type) {
			case 1:
				sessionKey = CommonConstants.FILE_PROJECT_ATTACHMENT;
				break;
			case 2:
				sessionKey = CommonConstants.FILE_PROJECT_ATTACHMENT;
				break;
			case 3:
				sessionKey = CommonConstants.FILE_RESUME_ATTACHMENT;
				break;
		}
		request.getSession().setAttribute(sessionKey, fileName);
	}

	/**
	 * 获取项目ID编号
	 */
	public static String getProjectSN() {
		String now = String.valueOf(System.currentTimeMillis());
		String fixStr = getRandomDigt(3);
		return now + fixStr;
	}
	
	/**
	 * 获取请求真实IP
	 */
	public static String getRealIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();

	}
	
	/**
	 * 获取IP位置信息
	 */
	public static String getIPLocalInfo(String ip) {
		long startTime = System.currentTimeMillis();
		String info = "";

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("ip", ip);
		String resultJson = HttpProtocolUtil.getContent(IP_LOOKUP_URL, paramsMap, "GET");
		if (!StringUtils.isEmpty(resultJson)) {
			try {
				JSONObject jsonObject = JSONObject.fromObject(resultJson);
				jsonObject = jsonObject.getJSONObject("data");
				info = jsonObject.getString("country") + jsonObject.getString("region")
						+ jsonObject.getString("city");
			} catch (Exception e) {
				log.error("getIPLocalInfo failed. " + resultJson, e);
			}
		}
		long endTime = System.currentTimeMillis();
		log.info("lookup " + ip + "address use time " + (endTime - startTime) + "ms");
		return info;
	}
	
	/**
	 * 获取文字缩略
	 */
	public static String getLittleStr(int limit, String normalText) {
		String littleText = normalText;
		if (normalText.length() > limit) {
			littleText = normalText.substring(0, limit) + "...";
		}
		return littleText;
	}

	public static void main(String[] args) {
//		for (int i = 0; i < 10; i++) {
//			System.out.println(CommonUtil.getRandomStr(6));
//
//			System.out.println(getProjectSN());
//		}
		Map<String,String> paramsMap = new HashMap<String, String>();
		//https://www.edaice.com/mcgi/user/company_login?companyId=wuzhenghua@yundaren.com&password=0e7517141fb53f21ee439b355b5a1d0a
		System.out.println(HttpsProtocolUtil.getContent("https://kyfw.12306.cn/otn/index/init", paramsMap, "GET"));
//		System.out.println(getIPLocalInfo("119.9.94.229"));
	}
}
