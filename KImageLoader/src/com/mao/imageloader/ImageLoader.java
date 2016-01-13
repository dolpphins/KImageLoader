package com.mao.imageloader;

import java.util.ArrayList;
import java.util.List;

import com.mao.imageloader.core.ImageLoadTask;
import com.mao.imageloader.core.ImageLoaderAssistant;
import com.mao.imageloader.core.ImageLoaderConfiguration;
import com.mao.imageloader.core.ImageLoaderOptions;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * 图片加载器,支持一对多加载,支持指定优先级加载
 * 
 * @author mao
 *
 */
public class ImageLoader {

	private final static String TAG = "ImageLoader";
	
	/** 唯一实例 */
	private final static ImageLoader sImageLoader = new ImageLoader();
	
	/** ImageLoader帮助类 */
	private ImageLoaderAssistant mImageLoaderAssistant;
	
	private ImageLoader() {	
		mImageLoaderAssistant = new ImageLoaderAssistant();
	}
	
	/**
	 * 获取ImageLoader唯一实例对象
	 * 
	 * @return 返回ImageLoader唯一实例对象
	 */
	public static ImageLoader getInstance() {
		return sImageLoader;
	}
	
	/**
	 * 显示图片
	 * 
	 * @param context 上下文
	 * @param url 要加载的图片url地址,不能为空
	 * @param imageView 要显示的ImageView,不能为空
	 */
	public void displayImage(Context context, String url, ImageView imageView) {
		displayImage(context, url, imageView, null);
	}
	
	public void displayImage(Context context, String url, ImageView imageView, ImageLoaderOptions opts) {
		displayImage(context, url, imageView, opts, null);
	}
	
	/**
	 * 显示图片
	 * 
	 * @param context 上下文
	 * @param url 要加载的图片url地址,不能为空
	 * @param imageView 要显示的ImageView,不能为空
	 * @param listener 监听器
	 */
	public void displayImage(Context context, String url, ImageView imageView, ImageLoaderOptions opts, ImageLoaderListener listener) {
		if(imageView == null) {
			return;
		}
		List<ImageView> imageViews = new ArrayList<ImageView>(1);
		imageViews.add(imageView);
		displayImage(context, url, imageViews, opts, listener);
	}
	
	/**
	 * 显示图片
	 * 
	 * @param context 上下文
	 * @param url 要加载的图片url地址,不能为空
	 * @param imageViews 要显示的ImageView集合,不能为空且大小必须大于0
	 */
	public void displayImage(Context context, String url, List<ImageView> imageViews) {
		displayImage(context, url, imageViews, null);
	}
	
	public void displayImage(Context context, String url, List<ImageView> imageViews, ImageLoaderOptions opts) {
		displayImage(context, url, imageViews, opts, null);
	}
	
	/**
	 * 显示图片
	 * 
	 * @param context 上下文
	 * @param url 要加载的图片url地址,不能为空
	 * @param imageViews 要显示的ImageView集合,不能为空且大小必须大于0
	 * @param listener 监听器
	 */
	public void displayImage(Context context, String url, List<ImageView> imageViews, ImageLoaderOptions opts, ImageLoaderListener listener) {
		if(context == null || TextUtils.isEmpty(url)
						   || imageViews == null
						   || imageViews.size() <= 0) {
			return;
		}
		ImageLoadTask task = new ImageLoadTask(context, url, imageViews);
		task.setOptions(opts);
		task.setImageLoaderListener(listener);
		mImageLoaderAssistant.submit(task);	
	}
	
	/**
	 * 设置ImageLoader配置
	 * 
	 * @param config 要设置的ImageLoader配置
	 */
	public void setImageLoaderConfiguration(ImageLoaderConfiguration config) {
		mImageLoaderAssistant.setImageLoaderConfiguration(config);
	}
	
	/**
	 * 获取ImageLoader配置
	 * 
	 * @return 返回ImageLoader配置
	 */
	public ImageLoaderConfiguration getImageLoaderConfiguration() {
		return mImageLoaderAssistant.getImageLoaderConfiguration();
	}
}
