package com.mao.imageloader.core;

import com.mao.imageloader.cache.disk.DiskCache;
import com.mao.imageloader.cache.memory.MemoryCache;
import com.mao.imageloader.network.Downloader;

import android.graphics.Bitmap;

public class ImageLoaderConfiguration {

	private String diskCachePath;
	
	private long diskCacheMaxSize;
	
	private boolean isAutoCreateCacheDir;
	
	private MemoryCache<String, Bitmap> memoryCacheManager;
	
	private DiskCache<DiskCache.KeyEntry, DiskCache.ValueEntry> diskCacheManager;
	
	private Downloader downloader;
	
	private ImageLoaderConfiguration(Builder builder) {
		this.diskCachePath = builder.diskCachePath;
		this.diskCacheMaxSize = builder.diskCacheMaxSize;
		this.isAutoCreateCacheDir = builder.isAutoCreateCacheDir;
		this.memoryCacheManager = builder.memoryCacheManager;
		this.diskCacheManager = builder.diskCacheManager;
		this.downloader = builder.downloader;
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
	
	public MemoryCache<String, Bitmap> getMemoryCacheManager() {
		return memoryCacheManager;
	}
	
	public DiskCache<DiskCache.KeyEntry, DiskCache.ValueEntry> getDiskCacheManager() {
		return diskCacheManager;
	}
	
	public Downloader getDownloader() {
		return downloader;
	}
	
	public static class Builder {
		
		private String diskCachePath;
		private long diskCacheMaxSize;
		private boolean isAutoCreateCacheDir;
		private MemoryCache<String, Bitmap> memoryCacheManager;
		private DiskCache<DiskCache.KeyEntry, DiskCache.ValueEntry> diskCacheManager;
		private Downloader downloader;
		
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
		
		public Builder setMemoryCacheManager(MemoryCache<String, Bitmap> manager) {
			memoryCacheManager = manager;
			return this;
		}
		
		public Builder setDiskCacheManager(DiskCache<DiskCache.KeyEntry, DiskCache.ValueEntry> manager) {
			diskCacheManager = manager;
			return this;
		}
		
		public Builder setDownloader(Downloader downloader) {
			this.downloader = downloader;
			return this;
		}

		public ImageLoaderConfiguration build() {
			return new ImageLoaderConfiguration(this);
		}
	}
}
