package com.yundaren.filter.handler;

import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springside.modules.mapper.JsonMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
public class ApiLogger {

	private static final String OPEN_API = "OpenAPI";

	private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * 记录API日志 日志格式：@@@logType|logTime|serverName:serverPort|requestUri|remoteAddr|userAgent|httpMethod|
	 * requestContentLength|requestParams|responseStatus|responseContentLength|costTime@@@
	 * 
	 * @param request
	 * @param response
	 * @param costTime
	 */
	public static void log(HttpServletRequest request, HttpServletResponse response, Long costTime) {
		try {
			String serverName = request.getServerName();
			int serverPort = request.getServerPort();
			String requestUri = request.getRequestURI();
			String remoteAddr = request.getRemoteAddr();
			String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
			String httpMethod = request.getMethod();
			int requestContentLength = request.getContentLength();
			Map<String, String[]> requestParams = request.getParameterMap();

			String requestParamJson = JsonMapper.nonEmptyMapper().toJson(requestParams);
			int responseStatus = response.getStatus();
			int responseContentLength = -1;
			Collection<String> responseContentLengthCollection = response
					.getHeaders(HttpHeaders.CONTENT_LENGTH);
			if (!responseContentLengthCollection.isEmpty()) {
				responseContentLength = Integer.valueOf(responseContentLengthCollection.iterator().next());
			}
			log.trace("@@@{}|{}|{}:{}|{}|{}|{}|{}|{}|{}|{}|{}|{}@@@", OPEN_API,
					FORMATTER.print(System.currentTimeMillis()), serverName, serverPort, requestUri,
					remoteAddr, userAgent, httpMethod, requestContentLength, requestParamJson,
					responseStatus, responseContentLength, costTime);

		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}
}
