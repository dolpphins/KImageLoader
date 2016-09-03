package com.mao.imageloader.utils;

import android.util.Log;

/**
 * Log日志管理类
 * 
 * @author mao
 *
 */
public class L {

	/** 是否为Debug模式 */
	public static boolean DEBUG = true;
	
	private L() {
		throw new UnsupportedOperationException("can't instantiate class L");
	}
	
	public static void d(String tag, String msg) {
		if(DEBUG) {
			Log.d(tag, msg);
		}
	}
	
	public static void e(String tag, String msg) {
		if(DEBUG) {
			Log.e(tag, msg);
		}
	}
	
	public static void i(String tag, String msg) {
		if(DEBUG) {
			Log.i(tag, msg);
		}
	}
	
	public static void v(String tag, String msg) {
		if(DEBUG) {
			Log.v(tag, msg);
		}
	}
	
	public static void w(String tag, String msg) {
		if(DEBUG) {
			Log.w(tag, msg);
		}
	}
}
