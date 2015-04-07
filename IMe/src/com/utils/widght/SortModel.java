package com.utils.widght;

public class SortModel {

	private String name = ""; // 别人的 nickname
	private String sortLetters; // 显示数据拼音的首字母
	// private String id="";
	private String uid = ""; // 本机username
	private String groupid = ""; // 别人的 username
	private String headimg = "";

	// private String sefusername=""; //本机username

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	/**
	 * author:ll
	 * 
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * author:ll
	 * 
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * author:ll
	 * 
	 * @return the groupid
	 */
	public String getGroupid() {
		return groupid;
	}

	/**
	 * author:ll
	 * 
	 * @param groupid
	 *            the groupid to set
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	/**
	 * author:ll
	 * 
	 * @return the headimg
	 */
	public String getHeadimg() {
		return headimg;
	}

	/**
	 * author:ll
	 * 
	 * @param headimg
	 *            the headimg to set
	 */
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String toString() {
		return "name=" + name + "sortLetters=" + sortLetters + "uid=" + uid
				+ "groupid=" + groupid + "headimg=" + headimg;
	}
	/**
	 * author:ll
	 * 
	 * @return the sefusername
	 */
	// public String getSefusername() {
	// return sefusername;
	// }
	// /**author:ll
	// * @param sefusername the sefusername to set
	// */
	// public void setSefusername(String sefusername) {
	// this.sefusername = sefusername;
	// }

}
