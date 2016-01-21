package com.mao.imageloader.core;

import java.io.InputStream;

import com.mao.imageloader.cache.disk.BitmapDiskLruCache;
import com.mao.imageloader.cache.disk.DiskCache;
import com.mao.imageloader.cache.memory.LruMemoryCache;
import com.mao.imageloader.cache.memory.MemoryCache;
import com.mao.imageloader.network.Downloader;
import com.mao.imageloader.network.impl.HttpDownloader;
import com.mao.imageloader.utils.IoUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

class ImageLoadActualizer {

	private final static String TAG = "ImageLoadActualizer";
	
	private MemoryCache<String, Bitmap> mMemoryCache;  
	private DiskCache<DiskCache.KeyEntry, DiskCache.ValueEntry> mDiskCache;
	private Downloader mDownloader;
	
	private ImageLoaderConfiguration mConfig;
	
	public ImageLoadActualizer(ImageLoaderConfiguration config) {
		mConfig = config;
		if(mConfig == null) {
			throw new IllegalArgumentException("ImageLoaderConfiguration can't be null");
		}
		init();
	}
	
	private void init() {
		if(mConfig.getMemoryCacheManager() != null) {
			mMemoryCache = mConfig.getMemoryCacheManager();
		} else {
			//默认内存缓存
			mMemoryCache = LruMemoryCache.getInstance();
		}
		if(mConfig.getDiskCacheManager() != null) {
			mDiskCache = mConfig.getDiskCacheManager();
		} else {
			//默认磁盘缓存
			mDiskCache = new BitmapDiskLruCache(mConfig.getDiskCachePath(), mConfig.isAutoCreateCacheDir(), mConfig.getDiskCacheMaxSize());
		}
		if(mConfig.getDownloader() != null) {
			mDownloader = mConfig.getDownloader();
		} else {
			//默认下载器
			mDownloader = new HttpDownloader();
		}
	}
	
	public ImageLoaderExecutor.Result load(ImageLoadTask task) {
		Bitmap bitmap = null;
		if(task != null) {
			String url = task.getUrl();
			ImageLoaderOptions opts = task.getOptions();
			if(!TextUtils.isEmpty(url) && opts != null) {
				bitmap = startLoadImage(url, opts);
			}
		}
		
		return buildResult(task, bitmap);
	}
	
	private Bitmap startLoadImage(String url, ImageLoaderOptions opts) {
		Bitmap bitmap = null;
		
		if(opts.isLoadFromMemory()) {
			bitmap = tryLoadFromMemoryCache(url, opts);
		}
		if(bitmap == null && opts.isLoadFromDisk()) {
			bitmap = tryLoadFromDiskCache(url, opts);
		}
		if(bitmap == null) {
			bitmap = tryLoadFromFileSystem(url, opts);
		}
		if(bitmap == null && opts.isLoadFromNetwork()) {
			bitmap = tryLoadFromNetwork(url, opts);
		}
		
		writeCache(url, bitmap, opts);
		
		return bitmap;
	}
	
	private Bitmap tryLoadFromMemoryCache(String url, ImageLoaderOptions opts) {
		return mMemoryCache.get(url);
	}
	
	private Bitmap tryLoadFromDiskCache(String url, ImageLoaderOptions opts) {
		
		DiskCache.ValueEntry valueEntry = getDiskCache(url, opts);
		if(valueEntry != null) {
			return valueEntry.getValue();
		}
		return null;
	}
	
	private DiskCache.ValueEntry getDiskCache(String url, ImageLoaderOptions opts) {
		DiskCache.KeyEntry keyEntry = new DiskCache.KeyEntry();
		keyEntry.setKey(url);
		keyEntry.setOptions(opts.getOptions());
		return mDiskCache.get(keyEntry);
	}
	
	private Bitmap tryLoadFromFileSystem(String url, ImageLoaderOptions opts) {
		InputStream is = IoUtils.getInputStream(url);
		return download(is, opts.getOptions());
	}
	
	private Bitmap tryLoadFromNetwork(String url, ImageLoaderOptions opts) {
		if(mDownloader == null) {
			return null;
		}
		InputStream is = null;
		try {
			is = mDownloader.getInputStream(url);
			if(is != null) {
				Bitmap bm = null;
				if((opts.isCacheInDisk())) {
					DiskCache.KeyEntry entryKey = new DiskCache.KeyEntry();
					entryKey.setKey(url);
					entryKey.setOptions(opts.getOptions());
					if(mDiskCache.copyIo(is, entryKey)) {
						DiskCache.ValueEntry valueEntry = mDiskCache.get(entryKey);
						if(valueEntry != null) {
							bm = valueEntry.getValue();
						}
					}
				} else if(!opts.isCacheInDisk()) {
					bm = download(is, opts.getOptions());
				}
				
				return bm;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			IoUtils.closeStream(is);
		}
	}
	
	private Bitmap download(InputStream is, BitmapFactory.Options options) {
		if(is == null) {
			return null;
		}
		return BitmapFactory.decodeStream(is, null, options);
	}
	
	private ImageLoaderExecutor.Result buildResult(ImageLoadTask task, Bitmap bm) {
		ImageLoaderExecutor.Result result = new ImageLoaderExecutor.Result();
		result.setTask(task);
		result.setBm(bm);
		return result;
	}
	
	private void writeCache(String url, Bitmap bitmap, ImageLoaderOptions opts) {
		if(opts != null && opts.isCacheInMemory() && bitmap != null) {
			trySaveToMemoryCache(url, bitmap);
		}
	}
	
	private void trySaveToMemoryCache(String url, Bitmap bitmap) {
		if(!TextUtils.isEmpty(url) && bitmap != null) {
			mMemoryCache.put(url, bitmap);
		}
	}

	public MemoryCache<String, Bitmap> getMemoryCache() {
		return mMemoryCache;
	}

	public void setMemoryCache(MemoryCache<String, Bitmap> memoryCache) {
		this.mMemoryCache = memoryCache;
	}

	public DiskCache<DiskCache.KeyEntry, DiskCache.ValueEntry> getLruDiskCache() {
		return mDiskCache;
	}

	public void setLruDiskCache(DiskCache<DiskCache.KeyEntry, DiskCache.ValueEntry> diskCache) {
		this.mDiskCache = diskCache;
	}

	public Downloader getDownloader() {
		return mDownloader;
	}

	public void setDownloader(Downloader downloader) {
		this.mDownloader = downloader;
	}
}
