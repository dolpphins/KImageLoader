package com.mao.imageloader.core;

import android.graphics.BitmapFactory;
import android.text.style.BulletSpan;

/**
 * 图片加载选项
 * 
 * @author mao
 *
 */
public class ImageLoaderOptions {

	/** 图片设置选项 */
	private BitmapFactory.Options opts;
	
	/** 加载中显示的图片Drawable ID */
	private int loadingDrawableId;
	
	/** 加载失败时显示的图片Drawable ID */
	private int loadedfailDrawableId;
	
	/** 是否缓存在内存中 */
	private boolean cacheInMemory;
	
	/** 是否缓存在磁盘中 */
	private boolean cacheInDisk;
	
//	/** 磁盘缓存文件夹路径 */
//	private String diskCachePath;
//	
//	/** 是否自动创建缓存目录（如果不存在的话） */
//	private boolean isAutoCreateCacheDir;
	
	private ImageLoaderOptions(Builder builder) {
		this.opts = builder.opts;
		this.loadingDrawableId = builder.loadingDrawableId;
		this.loadedfailDrawableId = builder.loadedfailDrawableId;
		this.cacheInMemory = builder.cacheInMemory;
		this.cacheInDisk = builder.cacheInDisk;
//		this.diskCachePath = builder.diskCachePath;
//		this.isAutoCreateCacheDir = builder.isAutoCreateCacheDir;
	}
	
	public BitmapFactory.Options getOptions() {
		return opts;
	}
	
	public int getLoadingDrawableId() {
		return loadingDrawableId;
	}
	
	public int getLoadedFailDrawableId() {
		return loadedfailDrawableId;
	}
	
	public boolean isCacheInMemory() {
		return cacheInMemory;
	}
	
	public boolean isCacheInDisk() {
		return cacheInDisk;
	}
	
//	public String getDiskCachePath() {
//		return diskCachePath;
//	}
//	
//	public void setDiskCachePath(String path) {
//		diskCachePath = path;
//	}
//	
//	public boolean isAutoCreateCacheDir() {
//		return isAutoCreateCacheDir;
//	}
	
	public static class Builder {
		
		private BitmapFactory.Options opts;
		private int loadingDrawableId;
		private int loadedfailDrawableId;
		private boolean cacheInMemory;
		private boolean cacheInDisk;
//		private String diskCachePath;
//		private boolean isAutoCreateCacheDir;
		
		public Builder() {
			opts = new BitmapFactory.Options();
			cacheInMemory = false;
			cacheInDisk = false;
			//isAutoCreateCacheDir = false;
		}
		
		public Builder setBitmapOptions(BitmapFactory.Options opts) {
			this.opts = opts;
			return this;
		}
		
		public Builder setLoadingDrawableId(int loadingDrawableId) {
			this.loadingDrawableId = loadingDrawableId;
			return this;
		}
		
		public Builder setLoadedfailDrawableId(int loadedfailDrawableId) {
			this.loadedfailDrawableId = loadedfailDrawableId;
			return this;
		}
		
		public Builder cacheInMemory(boolean enabled) {
			this.cacheInMemory = enabled;
			return this;
		}
		
		public Builder cacheInDisk(boolean enabled) {
			this.cacheInDisk = enabled;
			return this;
		}
		
//		public Builder setDiskCachePath(String path) {
//			this.diskCachePath = path;
//			return this;
//		}
//		
//		public Builder isAutoCreateCacheDir(boolean autoCreate) {
//			isAutoCreateCacheDir = autoCreate;
//			return this;
//		}
		
		public ImageLoaderOptions build() {
			return new ImageLoaderOptions(this);
		}
	}
}
