package com.utils.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * 
 * @author
 * @version 1.0
 */
public class UIHelper {

	/**
	 * 自定义Toast 弹出Toast消息
	 * 
	 * @param msg
	 */
	// public static void ToastMsg(Context cont, String msg) {
	// View myToast = LayoutInflater.from(cont)
	// .inflate(R.layout.mytoast, null);
	// // View myToast =activity.getLayoutInflater().inflate(R.layout.mytoast,
	// // null);
	// Toast toast = new Toast(cont.getApplicationContext());
	// toast.setView(myToast);
	// TextView tView = (TextView) myToast.findViewById(R.id.toast_tv);
	// tView.setText(msg);
	// toast.setGravity(Gravity.CENTER, 0, 0);// 自定义显示位置效果:自定义屏幕中间显示
	// toast.setDuration(Toast.LENGTH_SHORT);
	// toast.show();
	// }

	/**
	 * 自定义Toast 弹出Toast消息
	 * 
	 * @param msg
	 */
	// public static void ToastMsg(Context cont, int msg) {
	// View myToast = LayoutInflater.from(cont)
	// .inflate(R.layout.mytoast, null);
	// // View myToast =activity.getLayoutInflater().inflate(R.layout.mytoast,
	// // null);
	// Toast toast = new Toast(cont.getApplicationContext());
	// toast.setView(myToast);
	// TextView tView = (TextView) myToast.findViewById(R.id.toast_tv);
	// tView.setText(msg);
	// toast.setGravity(Gravity.CENTER, 0, 0);// 自定义显示位置效果:自定义屏幕中间显示
	// toast.setDuration(Toast.LENGTH_SHORT);
	// toast.show();
	// }
	public static void ToastMsg(Context cont, int msg) {
		Toast toast = Toast.makeText(cont, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void ToastMsg(Context cont, String msg) {
		Toast toast = Toast.makeText(cont, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);// 自定义显示位置效果:自定义屏幕中间显示
		toast.show();
	}

	public static void ToastMessage(Context cont, String msg) {
		Toast toast = Toast.makeText(cont, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);// 自定义显示位置效果:自定义屏幕中间显示
		toast.show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast toast = Toast.makeText(cont, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast toast = Toast.makeText(cont, msg, time);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 点击返回监听事件
	 * 
	 * @param activity
	 * @return 关闭当前activity
	 */
	public static View.OnClickListener finish(final Activity activity) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				activity.finish();
			}
		};
	}

	/**
	 * 弹出dialogue消息提示 两种选择：确认、取消
	 */
	public static void ShowDialogue(final Activity cont, final String title,
			final String msg, final String Positivebuttmsg,
			final String Negativebuttmsg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(Positivebuttmsg,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.setNegativeButton(Negativebuttmsg,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	/**
	 * 弹出dialogue消息提示 两种选择：确认、取消
	 */
	public static void ShowDialogue_true(final Activity cont,
			final String title, final String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

}
