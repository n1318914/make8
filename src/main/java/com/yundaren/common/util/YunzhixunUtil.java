package com.yundaren.common.util;

import java.io.ByteArrayInputStream;
import java.util.Date;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Encoder;

@Slf4j
public class YunzhixunUtil {

	public static String sendTemplateSMS(String host, String accountSid, String authToken, String appId,
			String templateId, String to, String param) {
		String result = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			// 构造请求URL内容
			String timestamp = DateUtil.formatTime(new Date(), "yyyyMMddHHmmss");
			String signature = getSignature(accountSid, authToken, timestamp);
			signature = signature.toUpperCase();
			String url = host + "/Accounts/" + accountSid + "/Messages/templateSMS" + "?sig=" + signature;
			TemplateSMS templateSMS = new TemplateSMS();
			templateSMS.setAppId(appId);
			templateSMS.setTemplateId(templateId);
			templateSMS.setTo(to);
			templateSMS.setParam(param);
			JSONObject jsonObj = JSONObject.fromObject(templateSMS);
			String body = "{\"templateSMS\":" + jsonObj.toString() + "}";
			HttpResponse response = post("application/json", accountSid, authToken, timestamp, url,
					httpclient, body);
			log.info("sendTemplateSMS args: " + body + " status : " + response.getStatusLine());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			log.info("YunzhixunUtil send failed.",e);
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	private static String getSignature(String accountSid, String authToken, String timestamp)
			throws Exception {
		String sig = accountSid + authToken + timestamp;
		// MD5加密
		String signature = MD5Util.encodeByMD5(sig);
		return signature;
	}

	private static HttpResponse post(String cType, String accountSid, String authToken, String timestamp,
			String url, DefaultHttpClient httpclient, String body) throws Exception {
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Accept", cType);
		httppost.setHeader("Content-Type", cType + ";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		BASE64Encoder encoder = new BASE64Encoder();
		String auth = encoder.encode(src.getBytes("utf-8"));
		httppost.setHeader("Authorization", auth);
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
		requestBody.setContentLength(body.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		System.out.println(response.getStatusLine());
		return response;
	}

	@Data
	public static class TemplateSMS {
		private String appId;
		private String templateId;
		private String to;
		private String param;
	}
}
