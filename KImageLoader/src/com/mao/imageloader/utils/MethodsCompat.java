package com.mao.imageloader.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class MethodsCompat {

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static Drawable getDrawable(Context context, int resId) {
		if(context == null || resId == 0) {
			return null;
		}
		try {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				return context.getResources().getDrawable(resId, context.getTheme());
			} else {
				return context.getResources().getDrawable(resId);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
		
	}
}
