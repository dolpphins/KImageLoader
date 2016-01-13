package com.mao.imageloader.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;

public class BitmapUtils {

    @SuppressLint("NewApi")
	public static int sizeOfBitmap(Bitmap bitmap) {
    	if(bitmap == null) {
    		return 0;
    	}
    	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    		return bitmap.getAllocationByteCount();
    	} else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
    		return bitmap.getByteCount();
    	} else {
    		return bitmap.getRowBytes() * bitmap.getHeight();
    	}
    }
}
