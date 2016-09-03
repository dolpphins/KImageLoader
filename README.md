# KImageLoader
具有某些新特性的图片加载框架

![Screenshot](https://github.com/dolpphins/KImageLoader/raw/master/images/ui.png)

特点
------

* 同时为多个ImageView加载同一张图片
* 指定图片加载来源
* 指定图片加载质量
* 指定是否需要缓存到内存中或者磁盘中
* 设置图片加载任务优先级
* 监听图片加载过程

用法
------

```

	imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder()
						.setDiskCacheMaxSize(2 * 1024 * 1024 * 1024L) //指定最大磁盘缓存为2GB
						.setDiskCachePath("/sdcard/KImageLoader") //指定磁盘缓存目录
						.isAutoCreateCacheDir(true) //如果磁盘缓存目录不存在自动创建
						.setMemoryCacheManager(null) //指定内存缓存管理器
						.setDiskCacheManager(null)   //指定磁盘缓存管理器
						.setDownloader(null)         //指定网络下载器
						.build();
		imageLoader.setImageLoaderConfiguration(config);
		ImageLoaderOptions opts = new ImageLoaderOptions.Builder()
				.cacheInMemory(true) //允许缓存到内存缓存中
				.cacheInDisk(true)   //允许缓存到磁盘缓存中
				.setLoadingDrawableId(R.drawable.ic_launcher) //加载图片过程中显示的图片
				.setLoadedfailDrawableId(R.drawable.image_emoticon10) //加载失败时显示的图片
				.loadFromMemory(true) //允许从内存缓存中加载
				.loadFromDisk(true)   //允许从磁盘缓存中加载
				.loadFromNetwork(true) //允许从网络上加载
				.setBitmapOptions(null) //指定加载的图片质量
				.build();
		String url = "http://img2.imgtn.bdimg.com/it/u=2702123953,998736265&fm=21&gp=0.jpg";
		ImageView imageView = (ImageView) findViewById(R.id.iv);
		imageLoader.displayImage(getApplicationContext(), url, imageView, opts);

```

License
------

Copyright 2016 MAO

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
