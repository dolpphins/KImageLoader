package com.mao.imageloader.core;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.mao.imageloader.cache.disk.BitmapDiskLruCache;
import com.mao.imageloader.cache.memory.LruMemoryCache;
import com.mao.imageloader.utils.IoUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

class ImageLoadActualizer {

	private final static String TAG = "ImageLoadActualizer";
	
	private final static LruMemoryCache sBitmapCache = LruMemoryCache.newMemoryCache(); 
	private BitmapDiskLruCache mLruDiskCache;
	
	private ImageLoaderConfiguration mConfig;
	
	public ImageLoadActualizer(ImageLoaderConfiguration config) {
		mConfig = config;
		if(mConfig == null) {
			throw new IllegalArgumentException("ImageLoaderConfiguration can't be null");
		}
		mLruDiskCache = new BitmapDiskLruCache(mConfig.getDiskCachePath(), mConfig.isAutoCreateCacheDir(), mConfig.getDiskCacheMaxSize());
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
		Bitmap bm = sBitmapCache.get(url);
		
//		if(bm != null) {
//			ByteArrayOutputStream baos = null;
//			try {
//				baos = new ByteArrayOutputStream();
//				bm.compress(CompressFormat.JPEG, 100, baos);
//				BitmapFactory.Options options = opts.getOptions();
//				return BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size(), options);
//			} catch(Exception e) {
//				e.printStackTrace();
//			} finally {
//				IoUtils.closeStream(baos);
//			}
//		}
//		return null;
		return bm;
	}
	
	private Bitmap tryLoadFromDiskCache(String url, ImageLoaderOptions opts) {
		
		BitmapDiskLruCache.EntryValue entryValue = getDiskCache(url, opts);
		if(entryValue != null) {
			return entryValue.getValue();
		}
		return null;
	}
	
	private BitmapDiskLruCache.EntryValue getDiskCache(String url, ImageLoaderOptions opts) {
		BitmapDiskLruCache.EntryKey entryKey = new BitmapDiskLruCache.EntryKey();
		entryKey.setKey(url);
		entryKey.setOptions(opts.getOptions());
		return mLruDiskCache.get(entryKey);
	}
	
	private Bitmap tryLoadFromFileSystem(String url, ImageLoaderOptions opts) {
		InputStream is = IoUtils.getInputStream(url);
		return download(is, opts.getOptions());
	}
	
	private Bitmap tryLoadFromNetwork(String url, ImageLoaderOptions opts) {
		InputStream is = null;
		try {
			URL networkUrl = new URL(url);
			URLConnection con = networkUrl.openConnection();
			con.setConnectTimeout(60 * 1000);
			is = con.getInputStream();
			if(is != null) {
				Bitmap bm = null;
				if((opts.isCacheInDisk())) {
					BitmapDiskLruCache.EntryKey entryKey = new BitmapDiskLruCache.EntryKey();
					entryKey.setKey(url);
					entryKey.setOptions(opts.getOptions());
					if(mLruDiskCache.copyIo(is, entryKey)) {
						BitmapDiskLruCache.EntryValue entryValue = mLruDiskCache.get(entryKey);
						if(entryValue != null) {
							bm = entryValue.getValue();
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
			sBitmapCache.put(url, bitmap);
		}
	}
}
