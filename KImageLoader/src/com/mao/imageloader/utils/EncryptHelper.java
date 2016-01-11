package com.mao.imageloader.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密解密工具类
 * 
 * @author mao
 *
 */
public class EncryptHelper {

	/**
	 * MD5加密
	 * 
	 * @param raw 要加密的字符串
	 * @return 加密成功返回加密后的字符串，加密失败返回null
	 */
	public static String md5(String raw) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] data = md5.digest(raw.getBytes(Charset.defaultCharset()));
			String result = StringUtils.bytes2String(data);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
