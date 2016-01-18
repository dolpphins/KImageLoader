package com.mao.imageloader.network.impl;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.mao.imageloader.network.Downloader;

/**
 * Http下载器
 * 
 * @author mao
 *
 */
public class HttpDownloader implements Downloader{

	@Override
	public InputStream getInputStream(String url) {
		try {
			URL networkUrl = new URL(url);
			URLConnection con = networkUrl.openConnection();
			con.setConnectTimeout(60 * 1000);
			return con.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			
		}
	}

}
