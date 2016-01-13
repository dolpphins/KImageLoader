package com.mao.imageloader.core;

import java.lang.ref.WeakReference;
import java.util.List;

import com.mao.imageloader.ImageLoaderListener;

import android.content.Context;
import android.widget.ImageView;

public class ImageLoadTask implements Comparable<ImageLoadTask>{
	
	/** 任务默认优先级别 */
	private final static int DEFAULT_PRIORITY = 5;
	
	private String url;
	
	private List<ImageView> imageViews;
	
	private int priority = DEFAULT_PRIORITY;
	
	private ImageLoaderListener imageLoaderListener;
	
	private ImageLoaderOptions options;
	
	private WeakReference<Context> context;
	
	public ImageLoadTask() {}
	
	public ImageLoadTask(Context context, String url, List<ImageView> imageViews) {
		this.context = new WeakReference<Context>(context);
		this.url = url;
		this.imageViews = imageViews;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param url
	 * @param imageView
	 * @param priority 任务优先级别,必须在[0, 10]范围内,如果不在该范围内都会被按默认优先级处理
	 */
	public ImageLoadTask(String url, List<ImageView> imageViews, int priority) {
		this.url = url;
		this.imageViews = imageViews;
		if(priority < 0 || priority > 10) {
			this.priority = priority;
		}
	}

	@Override
	public int compareTo(ImageLoadTask another) {
		if(another == null) {
			return 1;
		}
		return priority - another.priority;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ImageView> getImageViews() {
		return imageViews;
	}

	public void setImageViews(List<ImageView> imageViews) {
		this.imageViews = imageViews;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ImageLoaderListener getImageLoaderListener() {
		return imageLoaderListener;
	}

	public void setImageLoaderListener(ImageLoaderListener imageLoaderListener) {
		this.imageLoaderListener = imageLoaderListener;
	}

	public ImageLoaderOptions getOptions() {
		return options;
	}

	public void setOptions(ImageLoaderOptions options) {
		this.options = options;
	}

	public WeakReference<Context> getContext() {
		return context;
	}

	public void setContext(WeakReference<Context> context) {
		this.context = context;
	}
	
	
}
