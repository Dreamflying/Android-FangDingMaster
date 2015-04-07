package com.project.myself_model;

import java.util.ArrayList;
import java.util.List;

public class NearByInfoBean {
	private Integer id;
	private String user;
	private Integer time;
	private Double lng;
	private Double lat;
	private String content;
	private String address;
	private ArrayList<NearByInfoImage> imgs;
	private ArrayList<NearByInfoComment> commentGebicomments;
	
	
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
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
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
	public List<NearByInfoImage> getImgs() {
		return imgs;
	}
	public void setImgs(ArrayList<NearByInfoImage> imgs) {
		this.imgs = imgs;
	}
	public ArrayList<NearByInfoComment> getCommentGebicomments() {
		return commentGebicomments;
	}
	public void setCommentGebicomments(ArrayList<NearByInfoComment> commentGebicomments) {
		this.commentGebicomments = commentGebicomments;
	}

}
