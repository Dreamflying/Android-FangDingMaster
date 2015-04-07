package com.utils.os;
import android.graphics.Bitmap;

public class ContactsItem {
	private String contactName;
	private String phoneNumber;
	private Bitmap contactPhoto;
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Bitmap getContactPhoto() {
		return contactPhoto;
	}
	public void setContactPhoto(Bitmap contactPhoto) {
		this.contactPhoto = contactPhoto;
	}

}
