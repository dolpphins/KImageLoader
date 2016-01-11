package com.mao.imageloader.core;

public class ImageLoaderConfiguration {

	/** 磁盘缓存文件夹路径 */
	private String diskCachePath;
	
	/** 某一磁盘文件夹缓存大小,单位:字节 */
	private long diskCacheMaxSize;
	
	/** 是否自动创建缓存目录（不存在的话） */
	private boolean isAutoCreateCacheDir;
	
	private ImageLoaderConfiguration(Builder builder) {
		this.diskCachePath = builder.diskCachePath;
		this.diskCacheMaxSize = builder.diskCacheMaxSize;
		this.isAutoCreateCacheDir = builder.isAutoCreateCacheDir;
	}
	
	public String getDiskCachePath() {
		return diskCachePath;
	}
	
	public long getDiskCacheMaxSize() {
		return diskCacheMaxSize;
	}
	
	public boolean isAutoCreateCacheDir() {
		return isAutoCreateCacheDir;
	}
	
	public static class Builder {
		
		private String diskCachePath;
		private long diskCacheMaxSize;
		private boolean isAutoCreateCacheDir;
		
		public Builder() {
			diskCachePath = "";
			diskCacheMaxSize = Integer.MAX_VALUE;
			isAutoCreateCacheDir = true;
		}
		
		public Builder setDiskCachePath(String diskCachePath) {
			this.diskCachePath = diskCachePath;
			return this;
		}
		
		public Builder setDiskCacheMaxSize(long diskCacheMaxSize) {
			this.diskCacheMaxSize = diskCacheMaxSize;
			return this;
		}
		
		public Builder isAutoCreateCacheDir(boolean autoCreate) {
			isAutoCreateCacheDir = autoCreate;
			return this;
		}
		
		public ImageLoaderConfiguration build() {
			return new ImageLoaderConfiguration(this);
		}
	}
}
