package com.mao.imageloader.cache.disk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.DiskLruCache.Snapshot;
import com.mao.imageloader.utils.EncryptHelper;
import com.mao.imageloader.utils.IoUtils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

/**
 * 基于DiskLruCache实现的Bitmap磁盘缓存类
 * 
 * @author mao
 *
 */
public class BitmapDiskLruCache extends BaseDiskCache<DiskCache.KeyEntry, DiskCache.ValueEntry>{

	private DiskLruCache mDiskLruCache;
	
	public BitmapDiskLruCache(String path, boolean autoCreate, long maxSize) {
		initDiskCache(path, autoCreate, maxSize);
	}
	
	private void initDiskCache(String path, boolean autoCreate, long maxSize) {
		if(mDiskLruCache == null && !TextUtils.isEmpty(path)) {
			File f = new File(path);
			if(!f.exists() || !f.isDirectory()) {
				boolean b = false;
				if(autoCreate && f.mkdirs()) {
					b = true;
				}
				if(!b) {
					return;
				}
			}
			
			if(maxSize <= 0) {
				return;
			}
			try {
				mDiskLruCache = DiskLruCache.open(f, 1, 1, maxSize);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	@Override
	public boolean put(KeyEntry key, ValueEntry value) {
		if(key == null || value == null) {
			return false;
		}
		return putVal(key, value);
	}

	private boolean putVal(KeyEntry key, ValueEntry value) {
		if(value.getValue() == null) {
			return false;
		}
		DiskLruCache.Editor editor = getDiskEditor(key.getKey());
		if(editor != null) {
			OutputStream os = null;
			try {
				os = editor.newOutputStream(0);
				if(value.getValue().compress(CompressFormat.JPEG, 100, os)) {
					editor.commit();
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IoUtils.closeStream(os);
			}
		}
		return false;
	}
	
	@Override
	public ValueEntry get(KeyEntry key) {
		ValueEntry entryValue = new ValueEntry();
		if(key != null) {
			Bitmap bm = decodeBitmapFromDisk(key.getKey(), key.getOptions());
			entryValue.setValue(bm);
		}
		return entryValue;
	}

	private Bitmap decodeBitmapFromDisk(String key, BitmapFactory.Options options) {
		InputStream is = getDiskInputStream(key);
		return decodeBitmapStream(is, options);
	}
	
	//从指定流读
	private Bitmap decodeBitmapStream(InputStream is, BitmapFactory.Options options) {
		if(is != null) {
			return BitmapFactory.decodeStream(is, null, options);
		}
		return null;
	}
	
	@Override
	public boolean copyIo(InputStream is, KeyEntry key) {
		if(key != null) {
			return writeBitmapToDisk(is, key.getKey());
		} else {
			return false;
		}
	}
	
	private boolean writeBitmapToDisk(InputStream is, String key) {
		OutputStream os = null;
		try {
			DiskLruCache.Editor editor = getDiskEditor(key);
			
			if(editor != null) {
				os = editor.newOutputStream(0);
				
				if(IoUtils.copy(is, os)) {
					editor.commit();
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IoUtils.closeStream(os);
		}
		return false;
	}
	
	private InputStream getDiskInputStream(String key) {
		Snapshot snapshot = getDiskLruCacheSnapshot(key);
		if(snapshot != null) {
			return snapshot.getInputStream(0);
		}
		return null;
	}
	
	private Snapshot getDiskLruCacheSnapshot(String key) {
		if(mDiskLruCache != null) {
			key = generateDiskLruCacheKey(key);
			try {
				return mDiskLruCache.get(key);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private DiskLruCache.Editor getDiskEditor(String key) {
		if(mDiskLruCache != null) {
			try {
				key = generateDiskLruCacheKey(key);
				return mDiskLruCache.edit(key);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private String generateDiskLruCacheKey(String s) {
		return EncryptHelper.md5(s);
	}

	@Override
	public boolean clear() {
		if(mDiskLruCache != null) {
			try {
				mDiskLruCache.delete();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}	
}
