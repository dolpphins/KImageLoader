package com.mao.imageloader.cache.disk;

import java.io.InputStream;

import com.mao.imageloader.cache.ICache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 磁盘缓存接口
 * 
 * @author mao
 *
 * @param <K>
 * @param <V>
 */
public interface DiskCache<K extends DiskCache.KeyEntry, V extends DiskCache.ValueEntry> extends ICache<K, V>{

	/**
	 * 将输入流的数据复制到key对应的输出流中
	 * 
	 * @param is 输入流
	 * @param key 输出流的key
	 * @return 复制成功返回true，失败返回false
	 */
	public abstract boolean copyIo(InputStream is, K key);
	
	public static class KeyEntry {
		
		private String key;
		private BitmapFactory.Options options;
		
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public BitmapFactory.Options getOptions() {
			return options;
		}
		
		public void setOptions(BitmapFactory.Options options) {
			this.options = options;
		}
	}
	
	
	public static class ValueEntry {
		
		private Bitmap value;
		
		public Bitmap getValue() {
			return value;
		}
		
		public void setValue(Bitmap value) {
			this.value = value;
		}
	}	
}
