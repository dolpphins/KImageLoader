package com.mao.test;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Utils {
	
	public static int getScreenWidthPixels(Activity context) {
		 DisplayMetrics metric = new DisplayMetrics();
		 context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
	}
	
	public static int getScreenHeightPixels(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}
}
