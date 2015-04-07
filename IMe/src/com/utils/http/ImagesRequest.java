package com.utils.http;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.base.model.Configuration;

/**
 * 下载图片
 * 
 * @author Ly
 * @time 2014年5月31日上午10:44:55
 * @email liyuan6522151@gmail.com
 */
public class ImagesRequest {

	// ImageLoader 和ImageRequest

	private Configuration configuration;
	private RequestQueue queue;

	public ImagesRequest(Configuration configuration) {
		queue = configuration.getRequestQueue();
		this.configuration = configuration;
	}

	/**
	 * 利用 volley imageRequest 请求下载图片
	 */
	public void useImageRequest() {
		ImageRequest imageRequest = new ImageRequest(configuration.getUrl(),
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						// 下载成功回调
						configuration.getControl_modelRequest()
								.requestImageSuccess(response);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// 下载失败回调
						configuration.getControl_modelRequest()
								.requestImageFailure(error.toString());
					}
				});

		queue.add(imageRequest);

	}

	/**
	 * 利用 volley imageLoader 请求下载图片,针对加载多张图片
	 * 
	 * @param image
	 * @param defaultImage
	 * @param failureImage
	 */
	public void useImageLoader(ImageView image, int defaultImage,
			int failureImage) {
		ImageLoader imageLoader = new ImageLoader(queue, new BitmapCache());
		ImageListener listener = ImageLoader.getImageListener(image,
				defaultImage, failureImage);
		imageLoader.get(configuration.getUrl(), listener);
	}

}
