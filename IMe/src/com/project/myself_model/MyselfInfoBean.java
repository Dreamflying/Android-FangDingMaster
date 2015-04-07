package com.project.myself_model;

import java.util.ArrayList;
import java.util.List;

public class MyselfInfoBean {
	
	private Integer id;
	private String user;
	private String content;
	private String address;
	private long time;
	private ArrayList<MyselfInfoImage> img;
	private ArrayList<MyselfInfoComment> comment;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public List<MyselfInfoImage> getImg() {
		return img;
	}
	public void setImg(ArrayList<MyselfInfoImage> img) {
		this.img = img;
	}
	public ArrayList<MyselfInfoComment> getComment() {
		return comment;
	}
	public void setComment(ArrayList<MyselfInfoComment> comment) {
		this.comment = comment;
	}

}