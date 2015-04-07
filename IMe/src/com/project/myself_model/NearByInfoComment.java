package com.project.myself_model;

/**
 * MeGebicomment entity. @author MyEclipse Persistence Tools
 */
public class NearByInfoComment {

	// Fields
	private Integer id;
	private Integer layer;
	private String user;
	private String gbid;
	private String content;
	private Integer time;
	private String origin_username;

	// Constructors

	public String getOrigin_username() {
		return origin_username;
	}

	public void setOrigin_username(String origin_username) {
		this.origin_username = origin_username;
	}

	/** default constructor */
	public NearByInfoComment() {
	}

	/** full constructor */
	public NearByInfoComment(Integer layer, String user, String gbid,
			String content, Integer time) {
		this.layer = layer;
		this.user = user;
		this.gbid = gbid;
		this.content = content;
		this.time = time;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLayer() {
		return this.layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGbid() {
		return this.gbid;
	}

	public void setGbid(String gbid) {
		this.gbid = gbid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTime() {
		return this.time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

}