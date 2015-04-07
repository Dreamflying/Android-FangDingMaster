package com.base.controller;

import android.graphics.Bitmap;

/**
 * view-controller callback interface
 * 
 * @author Ly
 * @time 2014年5月6日下午1:05:39
 * @email liyuan6522151@gmail.com
 */
public interface IViewControlRequst {

	/**
	 * request data success (请求数据成功)
	 * 
	 * @param object
	 */
	public void requestSuccess(Object object);

	/**
	 * request data is null (数据为空)
	 * 
	 * @param objectString
	 */
	public void requestDataIsNull(String objectString);

	/**
	 * request ErrorCode(请求错误Code)
	 * 
	 * @param errorMessage
	 */

	public void requestErrorCode(String errorMessage);

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
	 * submit data success(提交数据成功)
	 * 
	 * @param responseSucess
	 */
	public void responseSuccess(String responseSucess);

	/**
	 * submit data failure (提交数据失败)
	 * 
	 * @param responseFailure
	 */
	public void responseFailure(String responseFailure);

	/**
	 * 文件下载成功
	 * 
	 * @param filemessage
	 */
	public void requestFileSuccess(String filemessage, int updateprogressBar);

	/**
	 * 文件下载失败
	 * 
	 * @param iserror
	 */
	public void requestFileError(String iserror);

	/**
	 * 下载图片成功
	 * 
	 * @param imageBitmap
	 */
	public void requestImageSuccess(Bitmap imageBitmap);

	/**
	 * 下载图片失败
	 * 
	 * @param error
	 */
	public void requestImageFailure(String error);
}
