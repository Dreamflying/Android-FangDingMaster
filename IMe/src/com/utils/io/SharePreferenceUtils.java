package com.utils.io;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.project.iwant.IMeApplication;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * @description sp 工具类
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-5下午2:35:03
 */
public class SharePreferenceUtils {
	private static final int TCP_PING = 150;// 设置心跳包的间隔时间，这里暂时是250秒，通常是5分钟
	private static SharePreferenceUtils sharePreferenceUtils = new SharePreferenceUtils();
	private static Context context;

	private SharePreferenceUtils() {

	}

	public static SharePreferenceUtils getIntance(Context thiscontext) {
		context = thiscontext;
		return sharePreferenceUtils;
	}

	/**
	 * @function 保存用户名到sp 中
	 * @time 2014-10-5下午3:03:00
	 * @param username
	 *            void
	 */
	public void saveCurrentUserName(String username) {
		SharedPreferences usernamesPreferences = context.getSharedPreferences(
				"username", Context.MODE_PRIVATE);
		usernamesPreferences.edit().putString("username", username).commit();
	}

	/**
	 * @function 获取当前用户
	 * @time 2014-10-5下午3:05:23
	 * @return String
	 */
	public String getCurrentUserName() {
		SharedPreferences usernamesPreferences = context.getSharedPreferences(
				"username", Context.MODE_PRIVATE);
		return usernamesPreferences.getString("username", "default");
	}

	/**
	 * @function 保存用户名到sp 中
	 * @time 2014-10-5下午3:03:00
	 * @param username
	 *            void
	 */
	public void saveHeadImgUserName(String headimage) {
		SharedPreferences usernamesPreferences = context.getSharedPreferences(
				"headimage", Context.MODE_PRIVATE);
		usernamesPreferences.edit().putString("headimage", headimage).commit();
	}

	public void clearHeadImage() {
		SharedPreferences usernamesPreferences = context.getSharedPreferences(
				"headimage", Context.MODE_PRIVATE);
		usernamesPreferences.edit().clear().commit();
	}

	/**
	 * @function 获取当前用户
	 * @time 2014-10-5下午3:05:23
	 * @return String
	 */
	public String getHeadImgUserName() {
		SharedPreferences usernamesPreferences = context.getSharedPreferences(
				"headimage", Context.MODE_PRIVATE);
		return usernamesPreferences.getString("headimage", "default");
	}

	/**
	 * @function 注销用户
	 * @time 2014-10-5下午3:07:32 void
	 */
	public void deleteCurrentUserName() {
		SharedPreferences usernamesPreferences = context.getSharedPreferences(
				"username", 0);
		usernamesPreferences.edit().clear().commit();
		;
	}

	/**
	 * @function 保存用户名到sp 中
	 * @time 2014-10-5下午3:03:00
	 * @param nickname
	 *            void
	 */
	public void saveCurrentNickName(String nickname) {
		SharedPreferences usernamesPreferences = context.getSharedPreferences(
				"nickname", Context.MODE_PRIVATE);
		usernamesPreferences.edit().putString("nickname", nickname).commit();
	}

	/**
	 * @function 获取当前用户签名
	 * @time 2014-10-5下午3:05:23
	 * @return String
	 */
	public String getCurrentIdio() {
		SharedPreferences usernamesPreferences = context.getSharedPreferences(
				"idio", Context.MODE_PRIVATE);
		return usernamesPreferences.getString("idio", "");
	}

	/**
	 * @function 保存用户名到sp 中
	 * @time 2014-10-5下午3:03:00
	 * @param nickname
	 *            void
	 */
	public void saveCurrentIdio(String idio) {
		SharedPreferences usernamesPreferences = context.getSharedPreferences(
				"idio", Context.MODE_PRIVATE);
		usernamesPreferences.edit().putString("idio", idio).commit();
	}

	/**
	 * @function 获取当前用户
	 * @time 2014-10-5下午3:05:23
	 * @return String
	 */
	public String getCurrentNickName() {
		SharedPreferences usernamesPreferences = context.getSharedPreferences(
				"nickname", Context.MODE_PRIVATE);
		return usernamesPreferences.getString("nickname", "default");
	}

	/**
	 * @function 保存Tcp ping 时间
	 * @time 2014-10-7下午3:15:05
	 * @param context
	 *            void
	 */
	public static void setTCPPingTime(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"tcp_ping", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("tcp_ping", (int) (System.currentTimeMillis() / 1000));
		editor.commit();
	}

	/**
	 * @function 得到
	 * @time 2014-10-7下午3:15:08
	 * @param context
	 * @return boolean
	 */
	public static boolean getTCPPingTime(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"tcp_ping", Context.MODE_PRIVATE);
		int lastTime = preferences.getInt("tcp_ping", 0);
		int nowTime = (int) (System.currentTimeMillis() / 1000);
		if ((nowTime - lastTime) >= TCP_PING) {
			Editor editor = preferences.edit();
			editor.putInt("tcp_ping", (int) (System.currentTimeMillis() / 1000));
			editor.commit();
			return true;
		} else {

			return false;
		}
	}

	/**
	 * @function 保存用户定位的经纬度信息和地址信息
	 * @time 2014-10-12上午12:13:11
	 * @param mLat
	 * @param mLng
	 * @param mAddress
	 *            void
	 */
	public void setLocation(String mLat, String mLng, String mAddress) {
		SharedPreferences preferences = context.getSharedPreferences(
				"me_location_info", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("lat", mLat);// 纬度
		editor.putString("lng", mLng);// 经度
		editor.putString("address", mAddress);// 地址
		editor.commit();
	}

	/**
	 * @function 得到经度
	 * @time 2014-10-12上午12:13:38
	 * @return String
	 */
	public String getLocationLat() {
		SharedPreferences preferences = context.getSharedPreferences(
				"me_location_info", Context.MODE_PRIVATE);
		String mStr = preferences.getString("lat", "0");
		return mStr;
	}

	/**
	 * @function 获取到纬度
	 * @time 2014-10-12上午12:13:55
	 * @return String
	 */
	public String getLocationLng() {
		SharedPreferences preferences = context.getSharedPreferences(
				"me_location_info", Context.MODE_PRIVATE);
		String mStr = preferences.getString("lng", "0");
		return mStr;
	}

	/**
	 * @function 获取到位置
	 * @time 2014-10-12上午12:14:03
	 * @return String
	 */
	public String getLocationAddress() {
		SharedPreferences preferences = context.getSharedPreferences(
				"me_location_info", Context.MODE_PRIVATE);
		String mStr = preferences.getString("address", "");
		return mStr;
	}

	public static String getLocationAddress(Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				"me_location_info", mContext.MODE_PRIVATE);
		String mStr = preferences.getString("address", "");
		return mStr;
	}

	/**
	 * @function 保存未读信息数目
	 * @time 2014-10-13上午9:21:23
	 * @param num
	 *            void
	 */
	public void saveUnLookMessageFlag(int num) {
		SharedPreferences preferences = context.getSharedPreferences(
				"me_home_unread_message", context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("num", num);
		editor.commit();
	}

	/**
	 * @function 获取未读数目
	 * @time 2014-10-13上午9:22:14
	 * @return int
	 */
	public int getUnLookMessageFlag() {
		SharedPreferences preferences = context.getSharedPreferences(
				"me_home_unread_message", context.MODE_PRIVATE);
		int num = preferences.getInt("num", 0);
		return num;
	}

	/*
	 * 主页未读申请小红点 0为隐藏，1为显示
	 */
	public void setHomeUnApply(int num) {
		SharedPreferences preferences = context.getSharedPreferences(
				"me_home_unread_apply", context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("num", num);
		editor.commit();
	}

	public int getHomeUnApply() {
		SharedPreferences preferences = context.getSharedPreferences(
				"me_home_unread_apply", context.MODE_PRIVATE);
		int num = preferences.getInt("num", 0);
		return num;
	}

	public void saveCommentItem(boolean isComment) {
		SharedPreferences preferences = context.getSharedPreferences(
				"isComment", context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("isComment", isComment);
		editor.commit();
	}

	public boolean getCommentItem() {
		SharedPreferences preferences = context.getSharedPreferences(
				"isComment", context.MODE_PRIVATE);
		return preferences.getBoolean("isComment", false);
	}

	public void saveSex(int sex) {
		SharedPreferences preferences = context.getSharedPreferences("saveSex",
				context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("saveSex", sex);
		editor.commit();

	}

	public int getSex() {
		SharedPreferences preferences = context.getSharedPreferences("saveSex",
				context.MODE_PRIVATE);
		return preferences.getInt("saveSex", 2);

	}

	public void saveLinkNum(int num) {
		SharedPreferences preferences = context.getSharedPreferences(
				"saveLinkNum", context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("saveLinkNum", num);
		editor.commit();

	}

	public int getLinkNum() {
		SharedPreferences preferences = context.getSharedPreferences(
				"saveLinkNum", context.MODE_PRIVATE);
		return preferences.getInt("saveLinkNum", 0);

	}

	public void saveCommetnNum(int num) {
		SharedPreferences preferences = context.getSharedPreferences(
				"saveCommetnNum", context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("saveCommetnNum", num);
		editor.commit();

	}

	public int getCommetnNum() {
		SharedPreferences preferences = context.getSharedPreferences(
				"saveCommetnNum", context.MODE_PRIVATE);
		return preferences.getInt("saveCommetnNum", 0);

	}

	/**
	 * @function 设置铃声开关
	 * @time 2014-10-19下午10:22:18
	 * @param isOpen
	 *            void
	 */
	public void saveSoundOpen(boolean isOpen) {
		SharedPreferences preferences = context.getSharedPreferences("isOpen",
				context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("isOpen", isOpen);
		editor.commit();

	}

	public boolean getSoundOpen() {
		SharedPreferences preferences = context.getSharedPreferences("isOpen",
				context.MODE_PRIVATE);
		return preferences.getBoolean("isOpen", true);

	}

	public void saveFirstOpen(boolean isOpen) {
		SharedPreferences preferences = context.getSharedPreferences(
				"saveFirstOpen", context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("saveFirstOpen", isOpen);
		editor.commit();

	}

	public boolean getFirstOpen() {
		SharedPreferences preferences = context.getSharedPreferences(
				"saveFirstOpen", context.MODE_PRIVATE);
		return preferences.getBoolean("saveFirstOpen", false);

	}

	public void saveCurrentDay() {
		final Calendar c = Calendar.getInstance();
		SharedPreferences preferences = context.getSharedPreferences("day",
				context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("day", c.get(Calendar.DAY_OF_MONTH));
		editor.putInt("next_day", c.get(Calendar.DAY_OF_MONTH));
		editor.commit();
	}

	public int getCurrentDay() {

		SharedPreferences preferences = context.getSharedPreferences("day",
				context.MODE_PRIVATE);
		return preferences.getInt("day", 0);
	}

	/**
	 * 每天进入一次
	 * 
	 * @param isOpen
	 */
	public void saveFirstShowOpen(boolean isOpen) {
		SharedPreferences preferences = context.getSharedPreferences("isOpen",
				context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("isOpen", isOpen);
		editor.commit();

	}

	public boolean getFirstShowOpen() {
		SharedPreferences preferences = context.getSharedPreferences("isOpen",
				context.MODE_PRIVATE);
		final Calendar c = Calendar.getInstance();
		saveStartTime(getStartTime());
		
		if (!(getStartTime()== 0)) {
			if (c.get(Calendar.DAY_OF_MONTH)>getStartTime() ){
				return preferences.getBoolean("isOpen", true);
			} else {
				saveStartTime(c.get(Calendar.DAY_OF_MONTH));
				return preferences.getBoolean("isOpen", false);
			}

		} else {
			saveStartTime(c.get(Calendar.DAY_OF_MONTH));
			return preferences.getBoolean("isOpen", true);

		}

	}

	@SuppressLint("NewApi")
	public void saveStartTime(int day) {
		SharedPreferences preferences = context.getSharedPreferences(
				"day", context.MODE_PRIVATE);
		preferences.edit().putInt("time", day).commit();
	}

	@SuppressLint("NewApi")
	public int getStartTime() {
		SharedPreferences preferences = context.getSharedPreferences(
				"day", context.MODE_PRIVATE);
		return preferences.getInt("time", 0);
	}
	
	public void saveLookMessage(boolean message){
		SharedPreferences preferences = context.getSharedPreferences(
				"message", context.MODE_PRIVATE);
		preferences.edit().putBoolean("message", message).commit();
	}
	public  boolean getLookMessage(){
		SharedPreferences preferences = context.getSharedPreferences(
				"message", context.MODE_PRIVATE);
		return preferences.getBoolean("message", false);
	}

}
