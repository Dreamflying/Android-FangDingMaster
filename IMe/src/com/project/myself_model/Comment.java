package com.project.myself_model;

public class Comment {
	private Integer id;
	private Integer layer;
	private String user;
	private String name;
	private String dtid;
	private String content;
	private long time;
	private String origin_username;
	private String headimg;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDtid() {
		return dtid;
	}
	public void setDtid(String dtid) {
		this.dtid = dtid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getOrigin_username() {
		return origin_username;
	}
	public void setOrigin_username(String origin_username) {
		this.origin_username = origin_username;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
}
