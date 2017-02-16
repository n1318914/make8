package com.yundaren.common.util;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

@Slf4j
public class HttpProtocolUtil {

	private static final int CONNECT_TIMEOUT = 15 * 1000;// 默认超时15秒

	public static String getContent(String remoteURL, Map<String, String> parameter, String method) {
		// get方式提交
		if (method.equalsIgnoreCase("GET")) {
			return doHttpGet(remoteURL, parameter);
		} else {
			return doHttpPost(remoteURL, parameter);
		}
	}

	@SuppressWarnings("deprecation")
	private static String doHttpPost(String url, Map<String, String> parameter) {
		try {
			if (StringUtils.isBlank(url) || null == parameter) {
				return null;
			}
			buiderParameterMap(url, parameter);

			List<NameValuePair> parameterArray = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> entrySet = parameter.entrySet();

			String[] parameterNames = new String[parameter.size()];
			int i = 0;
			for (Entry<String, String> entry : entrySet) {
				parameterArray.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				parameterNames[i] = entry.getKey();
				i++;
			}

			HttpPost request = new HttpPost();
			request.setURI(URI.create(url));
			// 绑定到请求 Entry
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameterArray, HTTP.UTF_8);
			request.setEntity(entity);
			// 发送请求
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECT_TIMEOUT);
			HttpResponse httpResponse = httpClient.execute(request);
			// 得到应答的字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			return retSrc;
		} catch (ClientProtocolException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	private static String doHttpGet(String url, Map<String, String> params) {
		try {
			if (StringUtils.isBlank(url) || params == null)
				return null;

			buiderParameterMap(url, params);

			StringBuffer sbParam = new StringBuffer();
			Set<Entry<String, String>> entrySet = params.entrySet();
			String[] parameterNames = new String[params.size()];
			int i = 0;
			for (Entry<String, String> entry : entrySet) {
				sbParam.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
				parameterNames[i] = entry.getKey();
				i++;
			}

			HttpGet request = new HttpGet(url + (url.contains("?") ? "&" : "?") + sbParam);
			// 获取回传网页
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECT_TIMEOUT);
			HttpResponse httpResponse = httpClient.execute(request);
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			return retSrc;
		} catch (ClientProtocolException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	public static void buiderParameterMap(String url, Map<String, String> params) {
		String str[] = url.split("\\?");
		if (str.length > 1) {
			String parameter = str[1];
			String parameters[] = parameter.split("&");
			for (String pstr : parameters) {
				String keyValue[] = pstr.split("=");
				params.put(keyValue[0], keyValue[1]);
			}
		}
	}
}
