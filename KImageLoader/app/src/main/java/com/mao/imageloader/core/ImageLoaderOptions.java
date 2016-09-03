package com.mao.imageloader.core;

import android.graphics.BitmapFactory;
import android.text.style.BulletSpan;

public class ImageLoaderOptions {

	private BitmapFactory.Options opts;
	
	private int loadingDrawableId;
	
	private int loadedfailDrawableId;
	
	private boolean cacheInMemory;
	
	private boolean cacheInDisk;
	
	/** 是否允许从内存缓存获取 */
	private boolean isLoadFromMemory;
	
	/** 是否允许从磁盘缓存获取 */
	private boolean isLoadFromDisk;
	
	/** 是否允许从网络获取 */
	private boolean isLoadFromNetwork;
	
	private ImageLoaderOptions(Builder builder) {
		this.opts = builder.opts;
		this.loadingDrawableId = builder.loadingDrawableId;
		this.loadedfailDrawableId = builder.loadedfailDrawableId;
		this.cacheInMemory = builder.cacheInMemory;
		this.cacheInDisk = builder.cacheInDisk;
		this.isLoadFromMemory = builder.isLoadFromMemory;
		this.isLoadFromDisk = builder.isLoadFromDisk;
		this.isLoadFromNetwork = builder.isLoadFromNetwork;
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
	
	public boolean isLoadFromMemory() {
		return isLoadFromMemory;
	}
	
	public boolean isLoadFromDisk() {
		return isLoadFromDisk;
	}
	
	public boolean isLoadFromNetwork() {
		return isLoadFromNetwork;
	}
	
	public static class Builder {
		
		private BitmapFactory.Options opts;
		private int loadingDrawableId;
		private int loadedfailDrawableId;
		private boolean cacheInMemory;
		private boolean cacheInDisk;
		private boolean isLoadFromMemory;
		private boolean isLoadFromDisk;
		private boolean isLoadFromNetwork;
		
		public Builder() {
			opts = new BitmapFactory.Options();
			cacheInMemory = false;
			cacheInDisk = false;
			isLoadFromMemory = true;
			isLoadFromDisk = true;
			isLoadFromNetwork = true;
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
		
		public Builder loadFromMemory(boolean enabled) {
			isLoadFromMemory = enabled;
			return this;
		}
		
		public Builder loadFromDisk(boolean enabled) {
			isLoadFromDisk = enabled;
			return this;
		}
		
		public Builder loadFromNetwork(boolean enabled) {
			isLoadFromNetwork = enabled;
			return this;
		}
		
		public ImageLoaderOptions build() {
			return new ImageLoaderOptions(this);
		}
	}
}
