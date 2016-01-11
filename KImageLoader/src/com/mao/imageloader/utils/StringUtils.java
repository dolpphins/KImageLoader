package com.mao.imageloader.utils;

/**
 * 字符串工具类
 * 
 * @author mao
 *
 */
public class StringUtils {

	/**
	 * 字节数组转十六进制表示形式字符串
	 * 
	 * @param b 要转换的字节数组
	 * 
	 * @return 如果data为null那么返回null，否则返回该字节数组的十六进制表示形式字符串
	 */
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
