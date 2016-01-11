package com.mao.imageloader.core;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import com.mao.imageloader.cache.disk.LruDiskCache;
import com.mao.imageloader.cache.memory.LruMemoryCache;
import com.mao.imageloader.core.ImageLoaderExecutor.Result;
import com.mao.imageloader.utils.IoUtils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

class ImageLoadActualizer {

	private final static String TAG = "ImageLoadActualizer";
	
	private final static LruMemoryCache sBitmapCache = LruMemoryCache.newMemoryCache(); 
	private LruDiskCache mLruDiskCache;
	
	private ImageLoadTask mTask;
	private ImageLoaderConfiguration mConfig;
	
	public ImageLoadActualizer(ImageLoaderConfiguration config) {
		mConfig = config;
		if(mConfig == null) {
			throw new IllegalArgumentException("ImageLoaderConfiguration can't be null");
		}
		mLruDiskCache = new LruDiskCache(mConfig.getDiskCachePath(), mConfig.isAutoCreateCacheDir(), mConfig.getDiskCacheMaxSize());
	}
	
	public ImageLoaderExecutor.Result load(ImageLoadTask task) {
		if(task != null) {
			mTask = task;
			String url = task.getUrl();
			ImageLoaderOptions opts = task.getOptions();
			if(!TextUtils.isEmpty(url) && opts != null) {
				return startLoadImage(url, opts);
			}
		}
		return null;
	}
	
	private ImageLoaderExecutor.Result startLoadImage(String url, ImageLoaderOptions opts) {
		ImageLoaderExecutor.Result result = null;
		
		result = tryLoadFromMemoryCache(url, opts);
		if(result == null) {
			result = tryLoadFromDiskCache(url, opts);
		}
		if(result == null) {
			result = tryLoadFromNetwork(url, opts);
		}
		
		//写缓存
		writeCache(url, result, opts);
		
		return result;
	}
	
	private ImageLoaderExecutor.Result tryLoadFromMemoryCache(String url, ImageLoaderOptions opts) {
		Bitmap bm = sBitmapCache.get(url);
		if(bm != null) {
			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				bm.compress(CompressFormat.JPEG, 100, baos);
				BitmapFactory.Options options = opts.getOptions();
				bm = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size(), options);
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				IoUtils.closeStream(baos);
			}
			
			if(bm != null) {
				return buildResult(bm);
			}
		}
		return null;
	}
	
	private ImageLoaderExecutor.Result tryLoadFromDiskCache(String url, ImageLoaderOptions opts) {
		
		LruDiskCache.EntryValue entryValue = getDiskCache(url, opts);
		if(entryValue != null) {
			Bitmap bm = entryValue.getValue();
			if(bm != null) {
				return buildResult(bm);
			}
		}
		return null;
	}
	
	private LruDiskCache.EntryValue getDiskCache(String url, ImageLoaderOptions opts) {
		LruDiskCache.EntryKey entryKey = new LruDiskCache.EntryKey();
		entryKey.setKey(url);
		entryKey.setOptions(opts.getOptions());
		return mLruDiskCache.get(entryKey);
	}
	
	private ImageLoaderExecutor.Result tryLoadFromNetwork(String url, ImageLoaderOptions opts) {
		InputStream is = null;
		try {
			URL networkUrl = new URL(url);
			URLConnection con = networkUrl.openConnection();
			con.setConnectTimeout(60 * 1000);//设置连接超时时间60s
			is = con.getInputStream();
			if(is != null) {
				Bitmap bm = null;
				if((opts.isCacheInDisk())) {
					LruDiskCache.EntryKey entryKey = new LruDiskCache.EntryKey();
					entryKey.setKey(url);
					entryKey.setOptions(opts.getOptions());
					if(mLruDiskCache.copyIo(is, entryKey)) {
						LruDiskCache.EntryValue entryValue = mLruDiskCache.get(entryKey);
						if(entryValue != null) {
							bm = entryValue.getValue();
						}
					}
				} else if(!opts.isCacheInDisk()) {
					bm = download(is, opts.getOptions());
				}
				
				if(bm != null) {
					return buildResult(bm);
				}
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
		return BitmapFactory.decodeStream(is, null, options);
	}
	
	private ImageLoaderExecutor.Result buildResult(Bitmap bm) {
		if(bm != null) {
			ImageLoaderExecutor.Result result = new ImageLoaderExecutor.Result();
			result.setTask(mTask);
			result.setBm(bm);
			return result;
		}
		return null;
	}
	
	private void writeCache(String url, Result result, ImageLoaderOptions opts) {
		//需要缓存在内存中
		if(opts != null && opts.isCacheInMemory() && result != null) {
			Bitmap bitmap = result.getBm();
			trySaveToMemoryCache(url, bitmap);
		}
	}
	
	private void trySaveToMemoryCache(String url, Bitmap bitmap) {
		if(!TextUtils.isEmpty(url) && bitmap != null) {
			sBitmapCache.put(url, bitmap);
		}
	}
}
