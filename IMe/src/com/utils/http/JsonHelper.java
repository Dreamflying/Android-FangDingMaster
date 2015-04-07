package com.utils.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * json 解析工具类
 * 
 * @author Ly
 * @time 2014-4-11 下午5:16:21
 * @Email 1522651962@qq.com
 */
public class JsonHelper {

	private static Gson gson;

	/**
	 * 将json字符串转换为对象
	 * 
	 * @param json
	 * @param className
	 * @return Object
	 */
	
	 public static Gson getInstance(){
		 if (gson==null) {
			return  gson = new Gson();
		}
		 return gson;
		 
	 }
	 
	 
	public static Object jsonToObject(String json, Class className)  {
		
		if (gson==null) {
			getInstance();
		}
		try {
			return gson.fromJson(json, className);
		} catch (Exception e) {
			return null;
		}
		
	}

	/**
	 * 将对象转换为json字符串
	 * 
	 * @param object
	 * @return String
	 */
	public static String objectToString(Object object) {
		if (gson==null) {
			getInstance();
		}
		try {
			return gson.toJson(object);
		} catch (Exception e) {
			return null;
		}
	
	}
	public static String objectToStringTree(Object object) {
		if (gson==null) {
			getInstance();
		}
		try {
			JsonElement element = gson.toJsonTree(object);
			return gson.toJson(element);
		} catch (Exception e) {
			return null;
		}
	
	}

}
