package com.mao.imageloader.utils;

import java.util.Map;

import android.util.Log;

public class Debugger {

	public static final String TAG = "Debugger";
	
	public static void printStackTrace() {
		Map<Thread, StackTraceElement[]> elementsMap = Thread.getAllStackTraces();
		StackTraceElement[] elements = elementsMap.get(Thread.currentThread());
		for(StackTraceElement element : elements) {
			L.d(TAG, "" + element);
		}
		
	}
}
