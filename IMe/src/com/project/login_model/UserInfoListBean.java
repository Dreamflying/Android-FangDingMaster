package com.project.login_model;

import java.util.ArrayList;

public class UserInfoListBean {
	private String code;
	private String message;
	private ArrayList<UserInfoBean> array;

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

	public ArrayList<UserInfoBean> getArray() {
		return array;
	}

	public void setArray(ArrayList<UserInfoBean> array) {
		this.array = array;
	}

}
