package com.project.login_model;

import java.util.ArrayList;

public class ModifyPsdListBean {
	private String code;
	private String message;
	private ArrayList<ModifyPsdBean> array;
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
	public ArrayList<ModifyPsdBean> getArray() {
		return array;
	}
	public void setArray(ArrayList<ModifyPsdBean> array) {
		this.array = array;
	}

}
