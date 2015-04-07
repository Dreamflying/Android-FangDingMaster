package com.utils.http;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * 图片Lru 缓存机制
 * 
 * @author Ly
 * @time 2014年5月31日下午1:11:58
 * @email liyuan6522151@gmail.com
 */
public class BitmapCache implements ImageCache {

	private LruCache<String, Bitmap> mCache;

	public BitmapCache() {
		int maxSize = 10 * 1024 * 1024;// 10M 的缓存
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}

	@Override
	public Bitmap getBitmap(String arg0) {
		return mCache.get(arg0);
	}

	@Override
	public void putBitmap(String arg0, Bitmap arg1) {
		mCache.put(arg0, arg1);

	}

}
