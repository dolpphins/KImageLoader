package com.mao.imageloader.core;

import java.util.List;

import com.mao.imageloader.ImageLoaderListener;
import com.mao.imageloader.core.ImageLoaderExecutor.OnImageLoaderExecutorListener;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageLoaderAssistant {

	private final static String TAG = "ImageLoaderAssistant";
		
	private ImageLoaderExecutor sExecutor;
	
	public ImageLoaderAssistant() {
		sExecutor = new ImageLoaderExecutor();
		sExecutor.setOnImageLoaderExecutorListener(new ImageLoaderExecutorListener());
	}
	
	public void submit(ImageLoadTask task) {
		sExecutor.submit(task);
	}
	
	public void setImageLoaderConfiguration(ImageLoaderConfiguration config) {
		sExecutor.setImageLoaderConfiguration(config);
	}
	
	public ImageLoaderConfiguration getImageLoaderConfiguration() {
		return sExecutor.getImageLoaderConfiguration();
	}
	
	static class ImageLoaderExecutorListener implements OnImageLoaderExecutorListener {

		@Override
		public void onImageLoadTaskStart(ImageLoadTask task) {
			if(task != null) {
				ImageLoaderListener listener = task.getImageLoaderListener();
				if(listener != null) {
					listener.onImageLoadTaskStart(task.getUrl(), task.getImageViews());
				}
			}
		}

		@Override
		public void onImageLoadTaskSuccess(ImageLoadTask task, Bitmap bitmap) {
			if(task != null && bitmap != null) {
				List<ImageView> imageViews = task.getImageViews();
				if(imageViews != null) {
					for(ImageView imageView : imageViews) {
						imageView.setImageBitmap(bitmap);
					}
				}
				
				ImageLoaderListener listener = task.getImageLoaderListener();
				if(listener != null) {
					listener.onImageLoadTaskSuccess(task.getUrl(), task.getImageViews(), bitmap);
				}
			}
		}

		@Override
		public void onImageLoadTaskFail(ImageLoadTask task) {
			if(task != null) {
				ImageLoaderListener listener = task.getImageLoaderListener();
				if(listener != null) {
					listener.onImageLoadTaskFail(task.getUrl(), task.getImageViews());
				}
			}
		}
	}
}
