package com.mao.imageloader.utils;

import java.util.Random;

import android.text.TextUtils;

public class RandomUtils {

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
	
	public static String randomDigits() {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		sb.append(random.nextInt(10));
		sb.append(random.nextInt(10));
		long time = System.currentTimeMillis();
		sb.append(time % 1000000);
		sb.append(random.nextInt(10));
		sb.append(random.nextInt(10));
		
		return sb.toString();
	}
}
