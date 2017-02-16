package com.yundaren.common.util;

import java.security.MessageDigest;
import java.util.Arrays;

import ch.qos.logback.core.net.SyslogOutputStream;

/***
 * 微信消息接口认证token摘要类
 * 
 * 这个摘要类实现为单例，校验一个签名是否合法的例子如下
 * 
 * <pre>
 * 
 * WeixinMessageDigest wxDigest = WeixinMessageDigest.getInstance();
 * boolean bValid = wxDigest.validate(signature, timestamp, nonce);
 * </pre>
 * 
 * 
 * @author liguocai
 */
public final class WeixinMessageDigest {

//	/**
//	 * 单例持有类
//	 * 
//	 * @author liguocai
//	 * 
//	 */
//	private static class SingletonHolder {
//		static final WeixinMessageDigest INSTANCE = new WeixinMessageDigest();
//	}
//
//	/**
//	 * 获取单例
//	 * 
//	 * @return
//	 */
//	public static WeixinMessageDigest getInstance() {
//		return SingletonHolder.INSTANCE;
//	}
//
	private MessageDigest digest;

	public WeixinMessageDigest() {
		try {
			System.out.println("单利构造");
			digest = MessageDigest.getInstance("SHA-1");
			System.out.println("单利构造end");
		} catch (Exception e) {
			throw new InternalError("init MessageDigest error:" + e.getMessage());
		}
	}

	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder sbDes = new StringBuilder();
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				sbDes.append("0");
			}
			sbDes.append(tmp);
		}
		return sbDes.toString();
	}

	private String encrypt(String strSrc) {
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		digest.update(bt);
		strDes = byte2hex(digest.digest());
		return strDes;
	}

	/**
	 * 校验请求的签名是否合法
	 * 
	 * 加密/校验流程： 1. 将token、timestamp、nonce三个参数进行字典序排序 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public boolean validate(String signature, String timestamp, String nonce) {
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		System.out.println("validate方法进来了");
		String token = getToken();
		String[] arrTmp = { token, timestamp, nonce };		
		Arrays.sort(arrTmp);
		StringBuffer sb = new StringBuffer();
		// 2.将三个参数字符串拼接成一个字符串进行sha1加密
		System.out.println(arrTmp.length);
		for (int i = 0; i < arrTmp.length; i++) {
			sb.append(arrTmp[i]);
		}
		String expectedSignature = encrypt(sb.toString());
		System.out.println(expectedSignature);
		// 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		if (expectedSignature.equals(signature)) {
			return true;
		}
		return false;
	}

	private String getToken() {
		return "make8TokenStr";
	}

	public static void main(String[] args) {

		String signature = "d47308e6a60b83e6045152ac778248d2b0a0c642";// 加密需要验证的签名
		String timestamp = "1455966036";// 时间戳
		String nonce = "469299619";// 随机数

		WeixinMessageDigest wxDigest = new WeixinMessageDigest();
		boolean bValid = wxDigest.validate(signature, timestamp, nonce);
		if (bValid) {
			System.out.println("token 验证成功!");			
		} else {
			System.out.println("token 验证失败!");
		}
	}
}