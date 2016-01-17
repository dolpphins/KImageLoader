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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private final static String TAG = "MainActivity";
	
	private GridView gv;
	
	private List<String> mUrlList;
	
	private ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initData();
		
		imageLoader = ImageLoader.getInstance();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder()
						.setDiskCacheMaxSize(2 * 1024 * 1024 * 1024L) //2G
						.setDiskCachePath("/sdcard/KImageLoader")
						.build();
		
		imageLoader.setImageLoaderConfiguration(config);
		
		gv = (GridView) findViewById(R.id.gv);
		gv.setAdapter(new PicturesGridViewAdapter());
//		LinearLayout main = (LinearLayout) findViewById(R.id.main);
//		for(int i = 0; i < 10; i++) {
//			ImageView iv = new ImageView(this);
//			
//			ImageLoaderOptions opts = new ImageLoaderOptions.Builder()
//					.cacheInMemory(true)
//					.cacheInDisk(true)
//					.setLoadingDrawableId(R.drawable.ic_launcher)
//					.setLoadedfailDrawableId(R.drawable.image_emoticon10)
//					.loadFromMemory(false)
//					.loadFromDisk(false)
//					.loadFromNetwork(true)
//					.build();
//			
//			imageLoader.displayImage(getApplicationContext(), mUrlList.get(i), iv, opts, null);
//			
//			main.addView(iv);
//		}
	}
	
	private class PicturesGridViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mUrlList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = new ImageView(getApplicationContext());
				AbsListView.LayoutParams params = new AbsListView.LayoutParams(150, 150);
				convertView.setLayoutParams(params);
			}
			ImageView imageView = ((ImageView)convertView);
			imageView.setImageBitmap(null);
			
			String url = mUrlList.get(position);
			
			System.out.println(position);
			System.out.println(url);
			
			displayImage(url, imageView);
			
			return convertView;
		}	
	}
	
	private void initData() {
		mUrlList = new ArrayList<String>();
		
		mUrlList.add("http://attach2.scimg.cn/forum/201503/17/172006zr3762gfgdr5tmu9.jpg");
		mUrlList.add("http://img2.imgtn.bdimg.com/it/u=2702123953,998736265&fm=21&gp=0.jpg");
		mUrlList.add("http://img0.imgtn.bdimg.com/it/u=2038146181,1011322607&fm=21&gp=0.jpg");
		mUrlList.add("http://img2.imgtn.bdimg.com/it/u=2331818416,622318846&fm=21&gp=0.jpg");
		mUrlList.add("http://img3.imgtn.bdimg.com/it/u=948177287,3309637870&fm=21&gp=0.jpg");
		mUrlList.add("http://imgsrc.baidu.com/forum/pic/item/d1da4aa3ab0641a2fe037f2b.jpg");
		mUrlList.add("http://imgsrc.baidu.com/forum/pic/item/ac4b8c504fc2d562da57192fe71190ef77c66c0c.jpg");
		mUrlList.add("http://img3.ph.126.net/yWRAM4x-TBGCYL2gTjd1iA==/2745506922852812363.jpg");
		mUrlList.add("http://s7.sinaimg.cn/middle/5c69ce18hb6dda6b5b4c6&690");
		mUrlList.add("http://imgsrc.baidu.com/forum/pic/item/0e2442a7d933c895a325d5c4d11373f082020031.jpg");
		mUrlList.add("http://img0.imgtn.bdimg.com/it/u=3146285871,4053944809&fm=21&gp=0.jpg");
		
		
	}
	
	private void displayImage(String url, ImageView imageView) {
		
		ImageLoaderOptions opts = new ImageLoaderOptions.Builder()
				.cacheInMemory(true)
				.cacheInDisk(true)
				.setLoadingDrawableId(R.drawable.ic_launcher)
				.setLoadedfailDrawableId(R.drawable.image_emoticon10)
				.loadFromMemory(false)
				.loadFromDisk(false)
				.loadFromNetwork(true)
				.build();
		
		imageLoader.displayImage(getApplicationContext(), url, imageView, opts, new ImageLoaderListener() {
			
			@Override
			public void onImageLoadTaskSuccess(String url, List<ImageView> imageViews, Bitmap bitmap) {
				System.out.println(url + " onImageLoadTaskSuccess");
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
