package com.yundaren.common.util;

import java.util.HashMap;
import java.util.Map;

import com.yundaren.common.constants.CommonConstants;

public class ResponseMapUtil {

	public static Map<String, Object> getFailedResponseMap(String errorMsg) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("resultCode", CommonConstants.ERROR_CODE_FAILED);
		returnMap.put("errorMsg", errorMsg);
		return returnMap;
	}

	public static Map<String, Object> getSuccessResponseMap(String msg) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("resultCode", CommonConstants.ERROR_CODE_SUCCESS);
		returnMap.put("msg", msg);
		return returnMap;
	}
	
	public static Map<String, Object> getRedirectMap(String domain, String url) {
		return getSuccessResponseMap(domain + url);
	}

}
