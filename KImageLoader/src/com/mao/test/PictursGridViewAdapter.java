package com.mao.test;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class PictursGridViewAdapter extends BaseAdapter{

	private WeakReference<Activity> mActivity;
	private List<String> mUrlList;

	private OnLoadImageListener listener;
	
	public PictursGridViewAdapter(WeakReference<Activity> at, List<String> urlList) {
		mActivity = at;
		mUrlList = urlList;
	}
	
	@Override
	public int getCount() {
		if(mUrlList != null) {
			return mUrlList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return mUrlList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv = null;
		if(convertView == null) {
			Activity at = mActivity.get();
			if(at == null) {
				return null;
			}
			iv = new ImageView(at.getApplicationContext());
			int screenWidth = Utils.getScreenWidthPixels(at);
			int side = screenWidth / 3; 
			LayoutParams lp = new AbsListView.LayoutParams(side, side);
			iv.setLayoutParams(lp);
			iv.setScaleType(ScaleType.CENTER_CROP);
		} else {
			iv = (ImageView)convertView;
		}
		iv.setImageBitmap(null);
		String url = mUrlList.get(position);
		iv.setTag(url);
		loadImage(iv, url);
		return iv;
	}
	
	private void loadImage(ImageView imageView, String url) {
		if(listener != null) {
			listener.onPreLoadImage(imageView, url);
		}
	}
	
	public void setOnLoadImageListener(OnLoadImageListener l) {
		listener = l; 
	}
	
	public OnLoadImageListener getOnLoadImageListener() {
		return listener;
	}
	
	public static interface OnLoadImageListener {
		
		void onPreLoadImage(ImageView imageView, String url);
	}
}
