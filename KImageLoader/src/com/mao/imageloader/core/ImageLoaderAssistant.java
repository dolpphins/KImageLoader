package com.mao.imageloader.core;

import java.lang.ref.WeakReference;
import java.util.List;

import com.mao.imageloader.ImageLoaderListener;
import com.mao.imageloader.core.ImageLoaderExecutor.OnImageLoaderExecutorListener;
import com.mao.imageloader.utils.MethodsCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
				//
				ImageLoaderOptions options = task.getOptions();
				if(options != null) {
					setImageResources(task, options.getLoadingDrawableId());
				}
				
				//回调
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
						if(imageView != null) {
							imageView.setImageBitmap(bitmap);
						}
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
				//
				ImageLoaderOptions options = task.getOptions();
				if(options != null) {
					setImageResources(task, options.getLoadedFailDrawableId());
				}
				
				//回调
				ImageLoaderListener listener = task.getImageLoaderListener();
				if(listener != null) {
					listener.onImageLoadTaskFail(task.getUrl(), task.getImageViews());
				}
			}
		}
		
		private void setImageResources(ImageLoadTask task, int resId) {
			if(task != null) {
				WeakReference<Context> weakContext = task.getContext();
				if(weakContext == null) {
					return;
				}
				Context context = weakContext.get();
				ImageLoaderOptions options = task.getOptions();
				List<ImageView> imageViewsList = task.getImageViews();
				if(context == null || options == null
								   || imageViewsList == null
								   || imageViewsList.size() <= 0) {
					return;
				}
				Drawable drawable = MethodsCompat.getDrawable(context, resId);
				if(drawable != null) {
					for(ImageView imageView : imageViewsList) {
						if(imageView != null) {
							imageView.setImageDrawable(drawable);
						}
					}
				}
			}
		}	
	}
}
