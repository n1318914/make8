package com.yundaren.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MD5Util {
	protected static MessageDigest messagedigest = null;
	private static FileInputStream in;
	protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
			'd', 'e', 'f'};
	private final static String[] strDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b",
			"c", "d", "e", "f"};

	// 转换字节数组为16进制字串
	public static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	public static String encodeByMD5(String originString) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.update(secretKey.getBytes());
			md.update(originString.getBytes("utf-8"));
			// 该函数返回值为存放哈希值结果的byte数组
			return byteToString(md.digest());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			log.error("md5 secret error,", ex);
			return null;
		}
	}

	public static String encodeByMD5(File file) {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			FileChannel ch = in.getChannel();
			MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			messagedigest.update(byteBuffer);
			return bufferToHex(messagedigest.digest());
		} catch (NoSuchAlgorithmException | IOException e) {
			log.error("md5 secret error,", e);
		}
		return null;
	}

	public static String encodeByMD5(byte[] bytes) {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
			messagedigest.update(bytes);
			return bufferToHex(messagedigest.digest());
		} catch (NoSuchAlgorithmException e) {
			log.error("md5 secret error,", e);
		}
		return null;
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Util.encodeByMD5("Admin@123"));//0e7517141fb53f21ee439b355b5a1d0a
		System.out.println(MD5Util.encodeByMD5("t123456"));//59f2443a4317918ce29ad28a14e1bdb7
		System.out.println(MD5Util.encodeByMD5("A123456"));//507f513353702b50c145d5b7d138095c
		
		String htmlStr= "<p><span style='font-weight: bold; background-color: rgb(255, 0, 0);'>找我就对了。</span><span style='font-weight: bold; background-color: yellow;'>1";
		System.out.println(HtmlUtils.htmlEscape(htmlStr));
	}
}
