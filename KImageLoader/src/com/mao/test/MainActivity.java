package com.mao.test;

import java.util.ArrayList;
import java.util.List;

import com.mao.imageloader.ImageLoader;
import com.mao.imageloader.ImageLoaderListener;
import com.mao.imageloader.core.ImageLoaderConfiguration;
import com.mao.imageloader.core.ImageLoaderOptions;
import com.mao.kimageloader.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private final static String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView iv1 = (ImageView) findViewById(R.id.iv1);
		ImageView iv2 = (ImageView) findViewById(R.id.iv2);
		ImageView iv3 = (ImageView) findViewById(R.id.iv3);
		ImageView iv4 = (ImageView) findViewById(R.id.iv4);
		List<ImageView> imageViews = new ArrayList<ImageView>(4);
		imageViews.add(iv1);
		imageViews.add(iv2);
		imageViews.add(iv3);
		imageViews.add(iv4);
		String url = "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1209/14/c2/13855271_1347613148393.jpg";
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder()
						.setDiskCacheMaxSize(2 * 1024 * 1024 * 1024L) //2G
						.setDiskCachePath("/sdcard/KImageLoader")
						.build();
		
		imageLoader.setImageLoaderConfiguration(config);
		
		ImageLoaderOptions opts = new ImageLoaderOptions.Builder()
					.cacheInMemory(true)
					.cacheInDisk(true)
					.setLoadingDrawableId(R.drawable.ic_launcher)
					.setLoadedfailDrawableId(R.drawable.image_emoticon10)
					.loadFromMemory(true)
					.loadFromDisk(true)
					.loadFromNetwork(true)
					.build();
		
		imageLoader.displayImage(getApplicationContext(), url, imageViews, opts, new ImageLoaderListener() {
			
			@Override
			public void onImageLoadTaskSuccess(String url, List<ImageView> imageViews, Bitmap bitmap) {
				System.out.println("onImageLoadTaskSuccess");
			}
			
			@Override
			public void onImageLoadTaskStart(String url, List<ImageView> imageViews) {
				System.out.println("onImageLoadTaskStart");
			}
			
			@Override
			public void onImageLoadTaskFail(String url, List<ImageView> imageViews) {
				System.out.println("onImageLoadTaskFail");
			}
		});
		
	}
}
