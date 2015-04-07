package com.project.message_model;

import java.util.ArrayList;

public class GetFriendListBean {
	
	private String code;
	private String message;
	private ArrayList<GetFriendBean> array;
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
	public ArrayList<GetFriendBean> getArray() {
		return array;
	}
	public void setArray(ArrayList<GetFriendBean> array) {
		this.array = array;
	}

}
