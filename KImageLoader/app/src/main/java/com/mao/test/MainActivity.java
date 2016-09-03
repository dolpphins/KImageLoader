package com.mao.test;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.mao.imageloader.ImageLoader;
import com.mao.imageloader.core.ImageLoaderConfiguration;
import com.mao.imageloader.core.ImageLoaderOptions;
import com.mao.kimageloader.R;
import com.mao.test.PictursGridViewAdapter.OnLoadImageListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity{
	
	private GridView gv;
	private List<String> mUrlList;
	private PictursGridViewAdapter mAdapter;
	
	private ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initData();
		initView();
		
		imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder()
						.setDiskCacheMaxSize(2 * 1024 * 1024 * 1024L) //磁盘缓存大小
						.setDiskCachePath("/sdcard/KImageLoader") //磁盘缓存目录
						.build();
		imageLoader.setImageLoaderConfiguration(config);
	}
	
	private void initView() {
		gv = (GridView) findViewById(R.id.gv);
		mAdapter = new PictursGridViewAdapter(new WeakReference<Activity>(this), mUrlList);
		mAdapter.setOnLoadImageListener(new OnLoadImageListener() {
			
			@Override
			public void onPreLoadImage(ImageView imageView, String url) {
				ImageLoaderOptions opts = buildImageLoaderOptions(false);
				ImageLoader.getInstance().displayImage(getApplicationContext(), url, imageView, opts);
			}
		});
		gv.setAdapter(mAdapter);
	}
	
	private ImageLoaderOptions buildImageLoaderOptions(boolean fromNetwork) {
		ImageLoaderOptions opts = new ImageLoaderOptions.Builder()
				.cacheInMemory(true)
				.cacheInDisk(true)
				//.setLoadingDrawableId(R.drawable.ic_launcher) //加载图片时显示的图片
				//.setLoadedfailDrawableId(R.drawable.image_emoticon10) //加载图片失败显示的图片
				.loadFromMemory(true) //是否允许从内存缓存中读取
				.loadFromDisk(true)   //是否允许从磁盘缓存中读取
				.loadFromNetwork(true) //是否允许通过网络加载图片
				.build();
		return opts;
	}
	
	private void initData() {
		mUrlList = new ArrayList<String>();
		
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1cypd13b5j30zk0m8gsy.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1cypck1ruj31hc0u0k38.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1cypc2u0cj318g0rsjsi.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1cypbh2nkj30zk0m80vp.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1cypb4hcjj31hc0xcmzw.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1cypapdyhj309s064aa6.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1cypaf3otj309s064jrg.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1cypa4cb8j30dc08cq3f.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1cyp9p0gaj309s064t8l.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1cyp95w3ij31ao0t64qp.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1cyp8iqi3j31hc0xc7ab.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1cyp81i4dj31ao0t64dy.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1cyp7628jj31hc0u0gzq.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1cyp6j9sqj30sg0lcqar.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1cyp5zen1j30sg0lcjz1.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1cyp5g9rdj30sg0lc43e.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1cyp4z8e8j30zk0k0q6v.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1cyp4inkjj31hc0xcmzw.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1cyp3zfcgj30zk0m87ny.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1dpfquc2ej31hc0xch3u.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1dpfqimq7j30xc0irdja.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1dpfpqcmyj31hc0xc12q.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1dpfpg7t6j30sg0hsad8.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1dpfp011pj31hc0u0ngt.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1dpfof6l9j31hc0xc7ec.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1dpfnyqbwj31400p0q7i.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1dpfnbp5uj31hc0xc7f5.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1dpfmrdhyj30sg0hswfn.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1dpfmb0zqj30sg0lctcx.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1dpflt8thj31hc0xckh2.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1dpfks3e7j31hc0xce1m.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1dpfk4ax5j31hc0xcqna.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1dpfjkcwij31hc0xcqco.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1dpfitotsj31hc0xc7gx.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1dpfia4wvj31hc0xc4qp.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1dpfhr6d3j30zk0lc797.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1dpfhe7p1j315o0ngagb.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1dpfh0q4sj31ao0t648m.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1dpfg5n5aj31hc0u07cr.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1dpffwyg0j31hc0xcb0b.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1dpffcg3hj318g0xcarm.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1dpfel2iej31z418gwxd.jpg");
		
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1e7uw1j41j318g0xcayd.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7uvj6cwj31hc0xctn0.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1e7uuxsflj31hc0xcayx.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1e7uuo0m2j31hc0xcwml.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1e7uu7f79j31hc0xc122.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1e7ut8f7rj31hc0xc41x.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7ut12qrj318g0p0q9d.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1e7usl73pj318g0xcatj.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7urr00uj30im0bn76s.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7uroo71j311y0nqdjf.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1e7ur12vdj30sg0lc45i.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1e7uqi0bgj30zk0m840w.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1e7upsnatj31hc0u07wh.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7uoiymaj31hc0xchcu.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7uo4pcej30qo0godip.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7unh8nij30qo0go10t.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1e7un34rqj31ao0t67b7.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7umoxgtj31hc0xc7ad.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1e7um6o9vj30sg0ipjug.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1e7ulvb6qj31400p0aw7.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1e7ulaka3j31hc0xc7g7.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7ukkvopj30sg0lcab3.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1e7ukeralj31z418gak0.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1e7ujk5svj31hc0xc4qp.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1e7uik4qmj31400p041j.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7ujb8mwj30zk0m8aez.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7ui2r1uj31hc0u0k1v.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1e7uhr1qyj311y0lctl6.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1e7ue2x8rj318g0p07wh.jpg");
		mUrlList.add("http://ww4.sinaimg.cn/mw690/005MGt8egw1f1e7ucrq8dj318g0xckd8.jpg");
		mUrlList.add("http://ww1.sinaimg.cn/mw690/005MGt8egw1f1e7uc7qd0j31hc0xcnn3.jpg");
		mUrlList.add("http://ww3.sinaimg.cn/mw690/005MGt8egw1f1e7ubkhjjj31400p00vw.jpg");
		mUrlList.add("http://ww2.sinaimg.cn/mw690/005MGt8egw1f1e7ub3zwmj31400p07c1.jpg");
	}
	
	
}
