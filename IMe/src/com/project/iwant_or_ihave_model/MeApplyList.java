package com.project.iwant_or_ihave_model;

import java.util.ArrayList;

public class MeApplyList {
	private String code;
	private String message;
	private ArrayList<MeApply> array;
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
	public ArrayList<MeApply> getArray() {
		return array;
	}
	public void setArray(ArrayList<MeApply> array) {
		this.array = array;
	}

}
