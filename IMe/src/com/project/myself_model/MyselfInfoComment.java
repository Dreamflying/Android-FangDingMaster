package com.project.myself_model;

import java.io.Serializable;


/**
 * MeDongtaicomment entity. @author MyEclipse Persistence Tools
 */
public class MyselfInfoComment implements Serializable{

	private Integer id;
	private Integer layer;
	private String user;
	private String name;
	private String dtid;
	private String content;
	private long time;
	private String origin_username;
	private String headimg;
	private String activityContent;
	private String activityId;
	private String activityHeadimg;
	private String activityNickname;
	private String activityUser;
	private int sum;
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getActivityUser() {
		return activityUser;
	}
	public void setActivityUser(String activityUser) {
		this.activityUser = activityUser;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getActivityHeadimg() {
		return activityHeadimg;
	}
	public void setActivityHeadimg(String activityHeadimg) {
		this.activityHeadimg = activityHeadimg;
	}
	public String getActivityNickname() {
		return activityNickname;
	}
	public void setActivityNickname(String activityNickname) {
		this.activityNickname = activityNickname;
	}
	public String getActivityContent() {
		return activityContent;
	}
	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}
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