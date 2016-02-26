package com.mao.imageloader.network.impl;

import java.io.IOException;
import java.io.InputStream;

import com.mao.imageloader.network.Downloader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Http下载器
 * 
 * @author mao
 *
 */
public class HttpDownloader implements Downloader{

	private OkHttpClient okHttpClient;
	
	public HttpDownloader() {
		okHttpClient = new OkHttpClient();
	}
	
	@Override
	public InputStream getInputStream(String url) {
		try {
			Request request = new Request.Builder()
								.url(url)
								.build();
			Response response = okHttpClient.newCall(request).execute();
			if(response != null) {
				return response.body().byteStream();
			}
		} catch (IOException e) {
			
		} catch (Exception e) {
			
		}
		return null;
	}

}
