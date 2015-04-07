package com.base.controller;

import android.graphics.Bitmap;

/**
 * controller-model interface
 * 
 * @author Ly
 * @time 2014年5月6日下午1:05:46
 * @email liyuan6522151@gmail.com
 */
public interface IControlModelRequest {

	/**
	 * request data success (请求数据成功)
	 * 
	 * @param jsonString
	 */
	public void requestSuccess(String jsonString);

	/**
	 * request timeout(请求超时)
	 * 
	 * @param timeoutString
	 */
	public void requestTimeout(String timeoutString);

	/**
	 * request serverError(请求服务器错误)
	 * 
	 * @param serverError
	 */
	public void requestServerError(String serverError);

	/**
	 * 文件下载成功
	 * 
	 * @param filemessage
	 */
	public void requestFileSuccess(String filemessage,int updateprogressBar);

	/**
	 * 文件下载失败
	 * 
	 * @param iserror
	 */
	public void requestFileError(String iserror); 
	
	
	 
	/**下载图片成功
	 * @param imageBitmap
	 */
	public void requestImageSuccess(Bitmap imageBitmap);
	
	
	/**下载图片失败
	 * @param error
	 */
	public void requestImageFailure(String error);

}
