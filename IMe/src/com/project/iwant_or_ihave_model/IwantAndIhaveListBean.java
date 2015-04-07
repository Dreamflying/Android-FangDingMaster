package com.project.iwant_or_ihave_model;

import java.util.ArrayList;

public class IwantAndIhaveListBean {
	
	private String code;
	private String message;
	private ArrayList<IwantAndIhaveBean> array;
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
	public ArrayList<IwantAndIhaveBean> getArray() {
		return array;
	}
	public void setArray(ArrayList<IwantAndIhaveBean> array) {
		this.array = array;
	}


}
