package com.mao.imageloader.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;

public class BitmapUtils {

	/**
     * <p>获取Bitmap大小,该方法为版本兼容的.</p>
     * 
     * @param bitmap 指定的位图,注意不可为null.
     * 
     * @return 如果bitmap为null那么将返回0,否则返回指定位图的大小,单位为字节.
     * 
     * @author 麦灿标
     * */
    @SuppressLint("NewApi")
	public static int sizeOfBitmap(Bitmap bitmap) {
    	if(bitmap == null) {
    		return 0;
    	}
    	//API 19及以上版本
    	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    		return bitmap.getAllocationByteCount();
    	//API 12 ~ API 18
    	} else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
    		return bitmap.getByteCount();
    	//API 12以下
    	} else {
    		return bitmap.getRowBytes() * bitmap.getHeight();
    	}
    }
}
