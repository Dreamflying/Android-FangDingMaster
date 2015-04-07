package com.project.iwant_or_ihave_model;


public class IgnoreBean {
	private String getuser;
	private String touser;
	private String content;
	private int status;
	private int postsid;
	public int getPostsid() {
		return postsid;
	}
	public void setPostsid(int postsid) {
		this.postsid = postsid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	private String keyword;
	private String time;
	public String getGetuser() {
		return getuser;
	}
	public void setGetuser(String getuser) {
		this.getuser = getuser;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
