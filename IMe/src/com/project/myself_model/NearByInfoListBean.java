package com.project.myself_model;

import java.util.ArrayList;

/**
 * @description 隔壁信息bean 类
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-12上午2:12:17
 */
public class NearByInfoListBean {
	private String code;
	private String message;
	private ArrayList<NearByInfoBean> array;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<NearByInfoBean> getArray() {
		return array;
	}
	public void setArray(ArrayList<NearByInfoBean> array) {
		this.array = array;
	}

}
