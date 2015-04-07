package com.project.message_model;

import java.util.ArrayList;

public class AddFriendListBean {

	private String code;
	private String message;
	private ArrayList<AddFriendBean> array;
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
	public ArrayList<AddFriendBean> getArray() {
		return array;
	}
	public void setArray(ArrayList<AddFriendBean> array) {
		this.array = array;
	}
}
