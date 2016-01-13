package com.mao.imageloader.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptHelper {

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
