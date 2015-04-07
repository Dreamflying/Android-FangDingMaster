package com.utils.widght;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.project.iwant.MainActivity;
import com.project.iwant.NewMainTabActivity;
import com.project.iwant.R;
import com.utils.io.SharePreferenceUtils;

public class Noti {
	public static void openNoti(Context mContext, String title, String content) {
		// 消息通知栏
		// 定义NotificationManager
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) mContext
				.getSystemService(ns);
		// 定义通知栏展现的内容信息
		int icon = R.drawable.logo_logo;
		CharSequence tickerText = title;
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);

		// 定义下拉通知栏时要展现的内容信息
		notification.flags=Notification.FLAG_AUTO_CANCEL;
		Context context = mContext;
		CharSequence contentTitle = title;
		CharSequence contentText = content;
		Bundle bundle = new Bundle();
		Intent notificationIntent = new Intent(mContext, NewMainTabActivity.class);
		bundle.putString("control", "noti");
		notificationIntent.putExtras(bundle);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		// 用mNotificationManager的notify方法通知用户生成标题栏消息通知
			mNotificationManager.notify(1, notification);
	}
}
