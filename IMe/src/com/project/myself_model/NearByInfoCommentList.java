package com.project.myself_model;

import java.util.ArrayList;

public class NearByInfoCommentList {
	private String code;
	private String message;
	private ArrayList<NearByInfoComment> array;
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
	public ArrayList<NearByInfoComment> getArray() {
		return array;
	}
	public void setArray(ArrayList<NearByInfoComment> array) {
		this.array = array;
	}
}
