package com.mao.imageloader.network;

import java.io.InputStream;

/**
 * 下载器接口
 * 
 * @author mao
 *
 */
public interface Downloader {

	/**
	 * 获取指定url的输入流
	 * 
	 * @param url
	 * @return
	 */
	InputStream getInputStream(String url);
}
