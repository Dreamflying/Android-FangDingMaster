package com.utils.os;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.project.iwant.R;

import android.R.integer;
import android.content.ClipData.Item;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.Telephony.Mms.Addr;
import android.text.TextUtils;
import android.util.Log;

/**
 * 获取联系人
 * 
 * @author liyuan
 * @Description
 * @Email 1522651962@qq.com
 * @date 2014年7月17日上午10:37:22
 */
public class ImportContacts {

	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;
	/** 头像ID **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;
	/** 联系人的ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;
	private Context context;
	private ArrayList<ContactsItem> contacts = new ArrayList<ContactsItem>();
	private  int SUM=20;
	private  int COUNT=0;

	public ImportContacts(Context context) {
		this.context = context;
	}

	/**
	 * 获得手机的联系人 2014年7月17日上午10:43:07
	 * 
	 * @return
	 */

	public ArrayList<ContactsItem> getContacts(int count) {
		getPhoneContacts( count);
		getSIMContacts();
		return contacts;
	}

	public void getPhoneContacts(int count) {
		HashMap<Long, ArrayList<ContactsItem>> hashMap = new HashMap<Long, ArrayList<ContactsItem>>();
		ContentResolver resolver = context.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);
		int sum=phoneCursor.getCount();
		SUM=SUM+count;
		if (phoneCursor != null) {
			for (int i = count; i <SUM ; i++) {
				phoneCursor.moveToPosition(i);
				if (i<sum) {
					ContactsItem item = new ContactsItem();
					// 得到手机号码
					String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
					// 当手机号码为空的或者为空字段 跳过当前循环
					if (TextUtils.isEmpty(phoneNumber))
						continue;
					// 得到联系人名称
					String contactName = phoneCursor
							.getString(PHONES_DISPLAY_NAME_INDEX);
					// 得到联系人ID
					Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
					// 得到联系人头像ID

					Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

					// 得到联系人头像Bitamp

					Bitmap contactPhoto = null;

					// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
					if (photoid > 0) {

						Uri uri = ContentUris.withAppendedId(
								ContactsContract.Contacts.CONTENT_URI, contactid);

						InputStream input = ContactsContract.Contacts
								.openContactPhotoInputStream(resolver, uri);
						contactPhoto = BitmapFactory.decodeStream(input);
						contactPhoto =BitmapTool.toRoundCorner(
								BitmapTool.compressImage(contactPhoto), 20) ;

					} else {

						contactPhoto = BitmapFactory.decodeResource(
								context.getResources(), R.drawable.ic_launcher);
						contactPhoto =BitmapTool.toRoundCorner(
								BitmapTool.compressImage(contactPhoto), 20) ;

					}
					// 以contactId为主键，把同一人的所有电话都存到一起。
					ArrayList<ContactsItem> cs = hashMap.get(contactid);
					if (cs == null) {
						cs = new ArrayList<ContactsItem>();
						item.setContactName(contactName);
						item.setContactPhoto(contactPhoto);
						item.setPhoneNumber(phoneNumber);
						cs.add(item);
						hashMap.put(contactid, cs);
					} else {
						item.setContactName(contactName);
						item.setContactPhoto(contactPhoto);
						item.setPhoneNumber(phoneNumber);
						cs.add(item);

					}

					contacts.addAll(cs);
				}else {
					
					break;
				}
			}
			
			/*while (phoneCursor.moveToNext()) {
				
				
			}*/
			phoneCursor.close();

		}

	}

	/**
	 * 获取SIM的联系人 2014年7月17日上午10:49:38
	 */
	public void getSIMContacts() {
		ContentResolver resolver = context.getContentResolver();

		// 获取Sims卡联系人

		Uri uri = Uri.parse("content://icc/adn");

		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,

		null);

		if (phoneCursor != null) {

			while (phoneCursor.moveToNext()) {
				ContactsItem item = new ContactsItem();

				// 得到手机号码

				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);

				// 当手机号码为空的或者为空字段 跳过当前循环

				if (TextUtils.isEmpty(phoneNumber))

					continue;

				// 得到联系人名称

				String contactName = phoneCursor

				.getString(PHONES_DISPLAY_NAME_INDEX);

				// Sim卡中没有联系人头像

				item.setContactName(contactName);
				item.setPhoneNumber(phoneNumber);

				contacts.add(item);
			}

			phoneCursor.close();

		}

	}

}
