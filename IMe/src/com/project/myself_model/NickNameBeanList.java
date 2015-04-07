package com.project.myself_model;

import java.util.ArrayList;

public class NickNameBeanList {

	private String code;
	private String message;
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
	public ArrayList<NickNameBean> getArray() {
		return array;
	}
	public void setArray(ArrayList<NickNameBean> array) {
		this.array = array;
	}
	private ArrayList<NickNameBean> array;
}
