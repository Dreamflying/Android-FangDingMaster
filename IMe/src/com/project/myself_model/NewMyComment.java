package com.project.myself_model;

import java.util.ArrayList;


public class NewMyComment {
	
	private int id;
	private String content;
	private int sum;
	private ArrayList<Comment> comment;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public ArrayList<Comment> getComment() {
		return comment;
	}
	public void setComment(ArrayList<Comment> comment) {
		this.comment = comment;
	}
	

}
