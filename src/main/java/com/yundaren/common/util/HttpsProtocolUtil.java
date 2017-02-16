package com.yundaren.common.util;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

@Slf4j
public class HttpsProtocolUtil {

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

			HttpClient httpsClient = getHttpsClient();

			HttpPost request = new HttpPost();
			request.setURI(URI.create(url));
			// 绑定到请求 Entry
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameterArray, HTTP.UTF_8);
			request.setEntity(entity);
			// 发送请求
			HttpResponse httpResponse = httpsClient.execute(request);
			// 得到应答的字符串
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			return retSrc;
		} catch (Exception e) {
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

			HttpClient httpsClient = getHttpsClient();

			HttpGet request = new HttpGet(url + (url.contains("?") ? "&" : "?") + sbParam);
			// 获取回传网页
			HttpResponse httpResponse = httpsClient.execute(request);
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			return retSrc;
		} catch (Exception e) {
			log.error("", e);
		} 
		return null;
	}
	
	private static HttpClient getHttpsClient() throws Exception {
		HttpParams httpParams = new BasicHttpParams();
		// 版本
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		// 编码
		HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
		HttpProtocolParams.setUseExpectContinue(httpParams, true);
		// 最大连接数
		ConnManagerParams.setMaxTotalConnections(httpParams, 1000);
		// 超时
		HttpConnectionParams.setConnectionTimeout(httpParams, 1000 * 60 * 3);
		HttpConnectionParams.setSoTimeout(httpParams, 1000 * 60 * 3);

		SSLContext ctx = SSLContext.getInstance("TLS");
		X509TrustManager tm = new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}
		};
		ctx.init(null, new TrustManager[] {tm}, null);
		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("https", 443, ssf));
		ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
		HttpClient httpsClient = new DefaultHttpClient(mgr, httpParams);
		return httpsClient;
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
