package com.project.iwant_or_ihave_model;

import java.util.ArrayList;

public class IgnoreListBean {
	private String code;
	private String message;
	private ArrayList<IgnoreBean> array;
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
	public ArrayList<IgnoreBean> getArray() {
		return array;
	}
	public void setArray(ArrayList<IgnoreBean> array) {
		this.array = array;
	}

}
