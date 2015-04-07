package com.project.myself_model;

import java.util.ArrayList;


/**
 *  @description  上传图片信息集合
 *  @author Ly
 *  @email 1522651962@qq.com
 *  @time 2014-10-7下午9:27:14
 */
public class ImageListBean {
	private String code;
	private String message;
	private ArrayList<ImageBean> array;
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
	public ArrayList<ImageBean> getArray() {
		return array;
	}
	public void setArray(ArrayList<ImageBean> array) {
		this.array = array;
	}

}
