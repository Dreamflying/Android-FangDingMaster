package com.project.myself_model;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * MeImg entity. @author MyEclipse Persistence Tools
 */

public class MyselfInfoImage implements java.io.Serializable {

	// Fields
	@Id
//	private Integer id;
	private String username;
	private String headname;
//	private String headimg;
	private String headurl;
	private String DT_id;
	
	
	public String getDT_id() {
		return DT_id;
	}

	public void setDT_id(String dT_id) {
		DT_id = dT_id;
	}

	
	
	// Constructors



	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	/** default constructor */
	public MyselfInfoImage() {
	}

	/** full constructor */
	public MyselfInfoImage(String username, String headname, String headurl) {
		this.username = username;
		this.headname = headname;
		this.headurl = headurl;
	}

	// Property accessors

//	public Integer getId() {
//		return this.id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHeadname() {
		return this.headname;
	}

	public void setHeadname(String headname) {
		this.headname = headname;
	}

//	public String getHeadimg() {
//		return this.headimg;
//	}
//
//	public void setHeadimg(String headimg) {
//		this.headimg = headimg;
//	}

}