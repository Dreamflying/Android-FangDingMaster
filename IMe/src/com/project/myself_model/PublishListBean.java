package com.project.myself_model;

import java.util.ArrayList;

public class PublishListBean {

	private String code;
	private String message;
	private ArrayList<PublishBean> array;
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
	public ArrayList<PublishBean> getArray() {
		return array;
	}
	public void setArray(ArrayList<PublishBean> array) {
		this.array = array;
	}
}
