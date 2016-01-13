package com.mao.imageloader.utils;

public class StringUtils {

	public static String bytes2String(byte[] data) {
		if(data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(4 * data.length);
		for(byte b : data) {
			sb.append(Integer.toHexString(b & 0x00FF));
		}
		return sb.toString();
	}
}
