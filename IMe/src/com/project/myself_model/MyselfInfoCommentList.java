package com.project.myself_model;

import java.util.ArrayList;

public class MyselfInfoCommentList {

	private String code;
	private String message;
	private ArrayList<MyselfInfoComment> array;
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
	public ArrayList<MyselfInfoComment> getArray() {
		return array;
	}
	public void setArray(ArrayList<MyselfInfoComment> array) {
		this.array = array;
	}
}
