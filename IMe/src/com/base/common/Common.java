package com.base.common;

import android.R.integer;


/**
 * 一些全局变量的配置
 * 
 * @author Ly
 * @time 2014年5月6日下午1:10:13
 * @email liyuan6522151@gmail.com
 */
public class Common {
	
	public static String RequstTag="QUEUE";

	/**
	 * 请求失败响应码
	 */
	public static String REQUEST_ERROR_CODE = "E01";
	/**
	 * 请求成功响应码
	 */
	public static String REQUEST_NORMAL_CODE = "N01";
	/**
	 * 提交成功响应码
	 */
	public static String RESPONSE_SUCCESS = "N01";
	/**
	 * 提交失败响应码
	 */
	public static String RESPONSE_FAILURE = "E01";
	/**
	 * 服务器的公用URL
	 */
	public static String URL_SERVER = "http://api.iwantme.com:8080/";
	/**test*/
	//public static String URL_SERVER = "http://121.40.104.223/IWantService/";
	/**test*/
	//public static String URL_SERVER = "http://59.68.29.19:8080/MeServer/";
	
	/**图片下载Url*/
	public static String URL_IMG = "http://121.40.104.223/IWantService/img/";
	/**
	 * 全局的Token
	 */
	public static String TOKEN_STRING = "";
	/**
	 * HTTP-POST 方式
	 */
	public final static int POST = 0;
	/**
	 * HTTP-GET 方式
	 */
	public final static int GET = 1;
	/**
	 * 下载成功标志
	 */
	public final static int DOWNLOADFILE_SUCCESS = 0;

	/**
	 * 下载失败标志
	 */
	public final static int DOWNLOADFILE_ERROR = -1;
	
	
	/**
	 * 筛选标志
	 */
	public static int select=0;


}
