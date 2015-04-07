package com.project.iwant_or_ihave_model;

/**
 * MeApply entity. @author MyEclipse Persistence Tools
 */

public class MeApply{

	// Fields

	private Integer id;
	private String getuser;
	private String touser;
	private String content;
	private Integer postsid;
	private Integer status;
	private String keyword;
	private String time;
	private String username;
	private String headimg;
	private String nickname;

	// Constructors

	
	/** default constructor */
	public MeApply() {
	}

	/** minimal constructor */
	public MeApply(String getuser, String touser, String content,
			Integer postsid, Integer status, String time) {
		this.getuser = getuser;
		this.touser = touser;
		this.content = content;
		this.postsid = postsid;
		this.status = status;
		this.time = time;
	}

	/** full constructor */
	public MeApply(String getuser, String touser, String content,
			Integer postsid, Integer status, String keyword, String time) {
		this.getuser = getuser;
		this.touser = touser;
		this.content = content;
		this.postsid = postsid;
		this.status = status;
		this.keyword = keyword;
		this.time = time;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGetuser() {
		return this.getuser;
	}

	public void setGetuser(String getuser) {
		this.getuser = getuser;
	}

	public String getTouser() {
		return this.touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPostsid() {
		return this.postsid;
	}

	public void setPostsid(Integer postsid) {
		this.postsid = postsid;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}