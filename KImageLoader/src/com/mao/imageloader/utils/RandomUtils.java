package com.mao.imageloader.utils;

import java.util.Random;

import android.text.TextUtils;

public class RandomUtils {

	/**
	 * 将字符串转换为十六进制表示形式的字符串
	 * 
	 * @param url 要转换的字符串
	 * @return 如果要转换的字符串为空,那么将返回空字符串,否则返回十六进制表示形式的字符串.
	 */
	public static String toHex(String url) {
		if(TextUtils.isEmpty(url)) {
			return "";
		}
		char[] chars = url.toCharArray();
		StringBuffer sb = new StringBuffer(2 * chars.length);
		for(int i = 0; i < chars.length; i++) {
			sb.append(Integer.toHexString(chars[i]));	
		}
		return sb.toString();
	}
	
	/**
	 * 获取10位随机数字字符串
	 * 
	 * @return 返回10位随机数字字符串
	 */
	public static String randomDigits() {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		//随机生成两个数字
		sb.append(random.nextInt(10));
		sb.append(random.nextInt(10));
		long time = System.currentTimeMillis();
		//截取后面6位
		sb.append(time % 1000000);
		//随机生成两个数字
		sb.append(random.nextInt(10));
		sb.append(random.nextInt(10));
		
		return sb.toString();
	}
}
