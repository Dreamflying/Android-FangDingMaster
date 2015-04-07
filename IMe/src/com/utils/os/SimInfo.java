package com.utils.os;

import android.content.Context;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 判断SIM卡的移动运营商情况
 * 
 * @author liyuan
 * @Description
 * @Email 1522651962@qq.com
 * @date 2014年7月22日上午10:46:19
 */
public class SimInfo {
	/** 移动商区分号 */
	public final static String China_Mobile_1 = "46000";
	public final static String China_Mobile_2 = "46002";
	public final static String China_Unicom = "46001";
	public final static String China_Telecom = "46003";

	/**
	 * 发送余额短信指令
	 * 
	 * */
	public final static String China_MobileA = "YECX";
	public final static String China_UnicomA = "CXYE";
	public final static String China_TelecomA = "YE";

	/** 三大运营商的号码 */

	public final static String China_MobileN = "10086";
	public final static String China_UnicomN = "10010";
	public final static String China_TelecomN = "10001";

	private Context context;

	public SimInfo(Context context) {

		this.context = context;
	}

	/**
	 * 判断运营商 2014年7月22日上午11:02:11
	 * 
	 * @return
	 */
	public int judgeSim() {
		TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = telManager.getSimOperator();
		if (operator.equals(China_Mobile_1) || operator.equals(China_Mobile_2)) {
			return 86;

		} else if (operator.equals(China_Telecom)) {
			return 1;
		} else if (operator.equals(China_Unicom)) {
			return 10;
		}
		return 0;

	}

	/**
	 * 查询余额 2014年7月22日上午11:03:18
	 */
	public void searchBalance() {
		switch (judgeSim()) {
		case 86:
			sendMessage(China_MobileN,China_MobileA);
			Log.v("China_MobileN", China_MobileN);
			break;
		case 10:
			sendMessage(China_UnicomN,China_UnicomA);
			Log.v("China_UnicomN", China_UnicomN);
			break;
		case 1:
			sendMessage(China_TelecomN,China_TelecomA);
			Log.v("China_TelecomN", China_TelecomN);
			break;
		default:
			break;
		}

	}

	/**
	 * 发送短信 2014年7月22日上午11:08:12
	 * 
	 * @param num
	 * @param text
	 */
	public void sendMessage(String num, String text) {

		SmsManager smsMgr = SmsManager.getDefault();
		smsMgr.sendTextMessage(num, null, text, null, null);
	}
}
