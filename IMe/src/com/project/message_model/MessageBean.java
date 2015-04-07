package com.project.message_model;

import java.io.Serializable;

/**
 * @description 聊天bean类
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-7下午3:33:33
 */
public class MessageBean implements Serializable  {
    private int id;
	private String user;
	private String toUser;
	private String headImage;
	private String content;
	private String toName;
	private String time;
	private byte[] thumbnailData;
	private byte[] imageData;
	private String imageUrl;
	private String status;
	private String readStatus;
	
	public byte[] getThumbnailData() {
		return thumbnailData;
	}

	public void setThumbnailData(byte[] thumbnailData) {
		this.thumbnailData = thumbnailData;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

}
