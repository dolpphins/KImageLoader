package com.mao.imageloader.core;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.mao.imageloader.utils.RandomUtils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

class ImageLoaderExecutor {

	private final static String TAG = "ImageLoaderExecutor";
	
	private final static int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	private final static int corePoolSize = CPU_COUNT + 1;
	private final static int maximumPoolSize = 2 * CPU_COUNT + 1;
	
	private final static BlockingQueue<Runnable> workQueue = new PriorityBlockingQueue<Runnable>(128);
	
	private final static ThreadFactory threadFactory = new ThreadFactory() {
		
		private final AtomicInteger counter = new AtomicInteger(0);
		
		@Override
		public Thread newThread(Runnable r) {
			
			return new Thread(r, "Task#" + counter.incrementAndGet());
		}
	};
	
	private final static ThreadPoolExecutor sDefaultExecutor = 
			new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 
					10, TimeUnit.SECONDS, workQueue, threadFactory);

	
	private final static ImageLoaderConfiguration DEFAULT_CONFIGURATION = new ImageLoaderConfiguration.Builder().build();
	private final static ImageLoaderOptions DEFAULT_OPTIONS = new ImageLoaderOptions.Builder().build();
	
	
	private OnImageLoaderExecutorListener mExecutorListener;
	private Map<String, ImageLoadTask> mTaskMap;
	private Map<String, FutureTask<Result>> mFutureMap;
	private ImageLoaderHandler mHandler;
	private ImageLoaderConfiguration mImageLoaderConfiguration;
	
	public ImageLoaderExecutor() {
		mTaskMap = new HashMap<String, ImageLoadTask>();
		mFutureMap = new HashMap<String, FutureTask<Result>>();
		mHandler = new ImageLoaderHandler(this);
		mImageLoaderConfiguration = DEFAULT_CONFIGURATION;
	}
	
	public void submit(ImageLoadTask task) {
		
		//
		ImageLoaderOptions opts = task.getOptions();
		if(opts == null) {
			opts = DEFAULT_OPTIONS; 
		}
//		if(TextUtils.isEmpty(opts.getDiskCachePath())) {
//			opts.setDiskCachePath(mImageLoaderConfiguration.getDiskCachePath());
//		}
		
		String taskID = RandomUtils.randomDigits();
		Callable<Result> callableTask = new ImageLoadCallable(taskID, task);
		FutureTask<Result> future = new ImageLoadFuture(taskID, callableTask);
		mTaskMap.put(taskID, task);
		mFutureMap.put(taskID, future);
		
		sDefaultExecutor.execute(future);
	}
	
	private class ImageLoadCallable implements Callable<Result> {

		private String taskID;
		private ImageLoadTask task;
		
		public ImageLoadCallable(String taskID, ImageLoadTask task) {
			this.taskID = taskID;
			this.task = task;
		}
		
		@Override
		public Result call() throws Exception {
			Message msg = Message.obtain();
			msg.what = ImageLoaderHandler.IMAGE_LOADER_START;
			msg.obj = taskID;
			mHandler.sendMessage(msg);
			
			ImageLoadActualizer loader = new ImageLoadActualizer(mImageLoaderConfiguration);
			return loader.load(task);			
		}
	}
	
	private class ImageLoadFuture extends FutureTask<Result>{
		
		private String taskID;
		
		public ImageLoadFuture(String taskID, Callable<Result> callable) {
			super(callable);
			this.taskID = taskID;
		}
		
		@Override
		protected void done() {
			Message msg = Message.obtain();
			msg.what = ImageLoaderHandler.IMAGE_LOADER_FINISH;
			msg.obj = taskID;
			mHandler.sendMessage(msg);
		}
	}
	
	public void setImageLoaderConfiguration(ImageLoaderConfiguration config) {
		mImageLoaderConfiguration = config;
	}
	
	public ImageLoaderConfiguration getImageLoaderConfiguration() {
		return mImageLoaderConfiguration;
	}
	
	public void setOnImageLoaderExecutorListener(OnImageLoaderExecutorListener listener) {
		mExecutorListener = listener;
	}
	
	public OnImageLoaderExecutorListener getOnImageLoaderExecutorListener() {
		return mExecutorListener;
	}
	
	private static class ImageLoaderHandler extends Handler {
		
		private final static int IMAGE_LOADER_START = 1;
		private final static int IMAGE_LOADER_FINISH = 2;
		
		private WeakReference<ImageLoaderExecutor> mExecutor;
		
		public ImageLoaderHandler(ImageLoaderExecutor executor) {
			mExecutor = new WeakReference<ImageLoaderExecutor>(executor);	
		}
		
		@Override
		public void handleMessage(Message msg) {
			String taskID = (String) msg.obj;
			
			switch (msg.what) {
			case IMAGE_LOADER_START:
				handleForImageLoadTask(IMAGE_LOADER_START, taskID);
				break;
			case IMAGE_LOADER_FINISH:
				handleForImageLoadTask(IMAGE_LOADER_FINISH, taskID);
				break;
			default:
				break;
			}
		}
		
		private void handleForImageLoadTask(int type, String taskID) {
			if(mExecutor != null) {
				ImageLoaderExecutor executor = mExecutor.get();
				if(executor != null) {
					switch (type) {
					case IMAGE_LOADER_START:
						executor.handleForImageLoadTaskStart(taskID);
						break;
					case IMAGE_LOADER_FINISH:
						executor.handleForImageLoadTaskFinish(taskID);
						break;
					default:
						break;
					}
				}
			}
		}
	}
	
	private void handleForImageLoadTaskStart(String taskID) {
		ImageLoadTask task = mTaskMap.get(taskID);
		if(task != null) {
			if(mExecutorListener != null) {
				mExecutorListener.onImageLoadTaskStart(task);
			}
		}
	}
	
	private void handleForImageLoadTaskFinish(String taskID) {
		FutureTask<Result> future = mFutureMap.get(taskID);
		if(future != null) {
			try {
				ImageLoadTask task = null;
				Bitmap bitmap = null;
				
				Result result = future.get(1, TimeUnit.SECONDS);
				if(result != null) {
					task = result.getTask();
					bitmap = result.getBm();
				}
				if(mExecutorListener != null) {
					if(bitmap != null) {
						mExecutorListener.onImageLoadTaskSuccess(task, bitmap);
					} else {
						mExecutorListener.onImageLoadTaskFail(task);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	static class Result {
		
		private ImageLoadTask task;
		
		private Bitmap bm;

		public ImageLoadTask getTask() {
			return task;
		}

		public void setTask(ImageLoadTask task) {
			this.task = task;
		}

		public Bitmap getBm() {
			return bm;
		}

		public void setBm(Bitmap bm) {
			this.bm = bm;
		}
	}
	
	static interface OnImageLoaderExecutorListener {
		
		void onImageLoadTaskStart(ImageLoadTask task);
		
		void onImageLoadTaskSuccess(ImageLoadTask task, Bitmap bitmap);
		
		void onImageLoadTaskFail(ImageLoadTask task);
	}
}
