package com.mao.imageloader;

import java.util.List;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 图片加载监听接口
 * 
 * @author mao
 *
 */
public interface ImageLoaderListener {

	/**
	 * 当图片开始加载时回调该方法,注意图片开始加载并不是指客户端代码调用加载方法时，
	 * 而是当真正要加载图片时.
	 * 
	 * @param url 图片url地址
	 * @param imageViews 显示图片的ImageView集合
	 */
	void onImageLoadTaskStart(String url, List<ImageView> imageViews);
	
	/**
	 * 当图片加载成功时回调该方法.
	 * 
	 * @param url 图片url地址
	 * @param imageViews 显示图片的ImageView集合
	 * @param bitmap 获取到的位图
	 */
	void onImageLoadTaskSuccess(String url, List<ImageView> imageViews, Bitmap bitmap);
	
	/**
	 * 当图片加载失败时回调该方法.
	 * 
	 * @param url 图片url地址
	 * @param imageViews 显示图片的ImageView集合
	 */
	void onImageLoadTaskFail(String url, List<ImageView> imageViews);
}
