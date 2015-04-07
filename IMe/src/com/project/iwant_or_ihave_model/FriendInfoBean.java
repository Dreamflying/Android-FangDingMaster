package com.project.iwant_or_ihave_model;

/**
 * 由于第三方头像上问题没能得到解决，所以把自己服务器上的头像路径已经昵称存到本地库。
 * @author liyuan
 *
 */
public class FriendInfoBean {

	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String username ;
	private String nickname;
	private String headimage;
	private String ido;
	public String getIdo() {
		return ido;
	}
	public void setIdo(String ido) {
		this.ido = ido;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadimage() {
		return headimage;
	}
	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}
	
}
