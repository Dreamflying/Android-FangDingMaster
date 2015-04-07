package com.project.message_model;

/**
 *  @description  好友类
 *  @author Ly
 *  @email 1522651962@qq.com
 *  @time 2014-12-9下午10:18:49
 */
public class FriendsBean {
	private int id;
	private String username;
	private String friendUserName;
	private String friendHeadImage;
	private String friendNickName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFriendUserName() {
		return friendUserName;
	}
	public void setFriendUserName(String friendUserName) {
		this.friendUserName = friendUserName;
	}
	public String getFriendHeadImage() {
		return friendHeadImage;
	}
	public void setFriendHeadImage(String friendHeadImage) {
		this.friendHeadImage = friendHeadImage;
	}
	public String getFriendNickName() {
		return friendNickName;
	}
	public void setFriendNickName(String friendNickName) {
		this.friendNickName = friendNickName;
	}
	
	

}
