package com.mao.test;

import java.util.ArrayList;
import java.util.List;

import com.mao.imageloader.ImageLoader;
import com.mao.imageloader.core.ImageLoaderConfiguration;
import com.mao.imageloader.core.ImageLoaderOptions;
import com.mao.kimageloader.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private final static String TAG = "MainActivity";
	
	private GridView gv;
	
	private List<String> mUrlList;
	
	private ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder()
						.setDiskCacheMaxSize(2 * 1024 * 1024 * 1024L) //磁盘缓存大小
						.setDiskCachePath("/sdcard/KImageLoader") //磁盘缓存目录
						.isAutoCreateCacheDir(true) //磁盘缓存目不存在时是否自动创建录，默认为false
						.setMemoryCacheManager(null) //指定内存缓存管理器
						.setDiskCacheManager(null)   //指定磁盘缓存管理器
						.setDownloader(null)         //指定下载器
						.build();
		imageLoader.setImageLoaderConfiguration(config);
		
		ImageLoaderOptions opts = new ImageLoaderOptions.Builder()
				.cacheInMemory(true) //允许内存缓存
				.cacheInDisk(true)   //运行磁盘缓存
				.setLoadingDrawableId(R.drawable.ic_launcher) //加载图片时显示的图片
				.setLoadedfailDrawableId(R.drawable.image_emoticon10) //加载图片失败显示的图片
				.loadFromMemory(false) //是否允许从内存缓存中读取
				.loadFromDisk(false)   //是否允许从磁盘缓存中读取
				.loadFromNetwork(true) //是否允许通过网络加载图片
				.setBitmapOptions(null) //指定要加载的图片的质量等
				.build();
		
		String url = "http://img2.imgtn.bdimg.com/it/u=2702123953,998736265&fm=21&gp=0.jpg";
		
		ImageView imageView = (ImageView) findViewById(R.id.iv);
		imageLoader.displayImage(getApplicationContext(), url, imageView, opts);
		
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
}
