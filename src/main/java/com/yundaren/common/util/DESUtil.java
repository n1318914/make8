package com.yundaren.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

/**
 * desc加密/解密 工具类
 */
@Slf4j
public class DESUtil {

	/** 密钥，长度必须大于8 */
	public static final String secretKey = "Waibaome888";

	public static void main(String args[]) {
		String input = "18665983813";
		String secret = new DESUtil().encrypt(input);
		System.out.println(secret);
		System.out.println(new DESUtil().decrypt("42e4cc3718b824f043c934018b8c1aee"));
		System.out.println(secret.length());
	}

	/**
	 * 解密
	 * 
	 * @param strInput
	 * @return
	 */
	public static String decrypt(String strInput) {
		if (StringUtils.isNotBlank(strInput) && StringUtils.isNotBlank(secretKey) && secretKey.length() >= 8) {
			return new String(decrypt(strToByteArray(strInput), secretKey.getBytes()));
		} else {
			// 抛异常
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param strInput
	 * @return
	 */
	public static String encrypt(String strInput) {
		if (StringUtils.isNotBlank(secretKey) && secretKey.length() >= 8) {
			return byteArrayToStr(encrypt(strInput.getBytes(), secretKey.getBytes()));
		} else {
			// 抛异常
			return null;
		}
	}

	private static byte[] encrypt(byte[] src, byte[] key) {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks;
		try {
			dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory;
			keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey;
			securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			Cipher cipher;
			cipher = Cipher.getInstance("DES");
			// 密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			// 加密
			return cipher.doFinal(src);
		} catch (Exception e) {
			log.error("DES secret error.", e);
			return null;
		}
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            源信息
	 * @param key
	 *            解密key
	 * @return
	 * @throws CommonException
	 */
	private static byte[] decrypt(byte[] src, byte[] key) {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
			// 解密操作
			return cipher.doFinal(src);
		} catch (Exception e) {
			log.error("DES secret error.", e);
			return null;
		}
	}

	private static String byteArrayToStr(byte[] b) {
		int iLen = b.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = b[i];
			while (intTmp < 0) {
				intTmp += 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	private static byte[] strToByteArray(String strIn) {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i += 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
}
