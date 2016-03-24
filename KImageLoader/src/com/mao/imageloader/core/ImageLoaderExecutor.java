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

import com.mao.imageloader.utils.L;
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
	
	private ImageLoadActualizer mImageLoadActualizer;
	
	public ImageLoaderExecutor() {
		mTaskMap = new HashMap<String, ImageLoadTask>();
		mFutureMap = new HashMap<String, FutureTask<Result>>();
		mHandler = new ImageLoaderHandler(this);
		mImageLoaderConfiguration = DEFAULT_CONFIGURATION;
		mImageLoadActualizer = new ImageLoadActualizer(mImageLoaderConfiguration);
	}
	
	public void submit(ImageLoadTask task) {
		
		//先判断是否存在相同任务
		if(mTaskMap.containsValue(task)) { //containsValue底部使用equals比较是否相等
			//L.i(TAG, "存在相同的任务啦...");
			return;
		}
		
		ImageLoaderOptions opts = task.getOptions();
		if(opts == null) {
			opts = DEFAULT_OPTIONS; 
		}
		
		String taskID = RandomUtils.randomDigits();
		Callable<Result> callableTask = new ImageLoadCallable(taskID, task);
		FutureTask<Result> future = new ImageLoadFuture(taskID, callableTask, task);
		//当前线程为UI线程，无须同步
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
			
			return mImageLoadActualizer.load(task);			
		}
	}
	
	private class ImageLoadFuture extends FutureTask<Result> implements Comparable<ImageLoadFuture> {
		
		private String taskID;
		private ImageLoadTask mTask;
		
		public ImageLoadFuture(String taskID, Callable<Result> callable, ImageLoadTask task) {
			super(callable);
			this.taskID = taskID;
			mTask = task;
		}
		
		@Override
		protected void done() {
			Message msg = Message.obtain();
			msg.what = ImageLoaderHandler.IMAGE_LOADER_FINISH;
			msg.obj = taskID;
			mHandler.sendMessage(msg);
		}

		@Override
		public int compareTo(ImageLoadFuture another) {
			if(mTask == null || another == null) {
				return 0;
			}
			return mTask.compareTo(another.mTask);
		}
	}
	
	public void setImageLoaderConfiguration(ImageLoaderConfiguration config) {
		mImageLoaderConfiguration = config;
		mImageLoadActualizer = new ImageLoadActualizer(mImageLoaderConfiguration);
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
		ImageLoadTask task = null;
		boolean isSuccess = false;
		if(future != null) {
			try {
				
				Bitmap bitmap = null;
				
				Result result = future.get(1, TimeUnit.SECONDS);
				if(result != null) {
					task = result.getTask();
					bitmap = result.getBm();
				}
				if(mExecutorListener != null) {
					if(bitmap != null) {
						mExecutorListener.onImageLoadTaskSuccess(task, bitmap);
						isSuccess = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//没有成功一切按失败处理
		if(!isSuccess) {
			mExecutorListener.onImageLoadTaskFail(task);
		}
		
		//移除掉任务及记录，当前线程为UI线程
		mTaskMap.remove(taskID);
		mFutureMap.remove(taskID);
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
