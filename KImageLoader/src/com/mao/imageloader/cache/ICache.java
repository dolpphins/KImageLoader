package com.mao.imageloader.cache;

/**
 * 缓存接口
 * 
 * @author mao
 *
 */
public interface ICache<K, V> {

	/**
	 * 添加缓存项
	 *
	 * @param key 键
	 * @param value 值
	 * @return 添加成功返回true，失败返回false
	 */
	boolean put(K key, V value);
	
	/**
	 * 按指定的key获取缓存项
	 * 
	 * @param key 要获取的缓存项的key
	 * @return 获取成功返回相应的缓存，失败返回null
	 */
	V get(K key);
	
	/**
	 * 清除所有缓存
	 * 
	 * @return 清除成功返回true，失败返回false
	 */
	boolean clear();
}
