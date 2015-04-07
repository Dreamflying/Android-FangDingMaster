package com.project.iwant_or_ihave_model;

import java.util.ArrayList;

import android.R.integer;

public class IwantAndIhaveBean {

	private Integer id;
	private Integer type;
	private String address;
	private Integer nowpeople;
	private Integer allpeople;
	private String lat;
	private String lng;
	private String addtime;
	private Integer status;
	private Integer del;
	private Integer gpoint;
	private Integer top;
	private Integer hot;
	private String username;
	private String content;
	private String headimg;
	private String nickname;
	private String idiograph;
	private String sum;
	private String sex;
	private  ArrayList<GetPostImageBean> postImgs;
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public ArrayList<GetPostImageBean> getPostImgs() {
		return postImgs;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setPostImgs(ArrayList<GetPostImageBean> postImgs) {
		this.postImgs = postImgs;
	}
	public String getIdiograph() {
		return idiograph;
	}
	public void setIdiograph(String idiograph) {
		this.idiograph = idiograph;
	}
	private ArrayList<MeApply> Apply;
	public ArrayList<MeApply> getApply() {
		return Apply;
	}
	public void setApply(ArrayList<MeApply> apply) {
		Apply = apply;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getNowpeople() {
		return nowpeople;
	}
	public void setNowpeople(Integer nowpeople) {
		this.nowpeople = nowpeople;
	}
	public Integer getAllpeople() {
		return allpeople;
	}
	public void setAllpeople(Integer allpeople) {
		this.allpeople = allpeople;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public Integer getGpoint() {
		return gpoint;
	}
	public void setGpoint(Integer gpoint) {
		this.gpoint = gpoint;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getHot() {
		return hot;
	}
	public void setHot(Integer hot) {
		this.hot = hot;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
}
