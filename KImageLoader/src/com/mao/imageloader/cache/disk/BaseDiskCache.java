package com.mao.imageloader.cache.disk;

import java.io.InputStream;

import com.mao.imageloader.cache.ICache;

public abstract class BaseDiskCache<K, V> implements ICache<K, V>{

	
	/**
	 * 将输入流的数据复制到key对应的输出流中
	 * 
	 * @param is 输入流
	 * @param key 输出流的key
	 * @return 复制成功返回true，失败返回false
	 */
	public abstract boolean copyIo(InputStream is, K key);
	
}
