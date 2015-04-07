package com.utils.widght;


import com.project.iwant.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @author liyuan
 * @Description 自定义显示对话框
 * @Email 1522651962@qq.com
 * @date 2014年8月6日下午6:04:31
 */
public class DefineDialog {
	
	private static DefineDialogHelper dialog;
	
	public static final int TYPE_BLOCK = 0;
	public static final int TYPE_NONBLOCK = 1;

	/** 显示下载的对话框
	 *2014年8月6日下午6:05:38
	 * @param context
	 * @param msg
	 * @param blocktype
	 */
	public static void showLoadingMessage(Context context, String msg, int blocktype) {
		dismiss();
		setDialog(context, msg, R.drawable.simplehud_spinner, TYPE_BLOCK);
		dialog.show();
	}
	
	/**显示错误的对话框
	 *2014年8月6日下午6:05:49
	 * @param context
	 * @param msg
	 */
	public static void showErrorMessage(Context context, String msg) {
		dismiss();
		setDialog(context, msg, R.drawable.simplehud_error, TYPE_BLOCK);
		dialog.show();
		dismissAfter2s();
	}

	/**显示成功的信息对话框
	 *2014年8月6日下午6:06:03
	 * @param context
	 * @param msg
	 */
	public static void showSuccessMessage(Context context, String msg) {
		dismiss();
		setDialog(context, msg, R.drawable.simplehud_success, TYPE_BLOCK);
		dialog.show();
		dismissAfter2s();
	}
	
	/**显示信息的对话框
	 *2014年8月6日下午6:06:12
	 * @param context
	 * @param msg
	 */
	public static void showInfoMessage(Context context, String msg) {
		dismiss();
		setDialog(context, msg, R.drawable.simplehud_info, TYPE_BLOCK);
		dialog.show();
		dismissAfter2s();
	}
	

	
	/**设置对话的显示风格
	 *2014年8月6日下午6:06:28
	 * @param ctx
	 * @param msg
	 * @param resId
	 * @param blocktype
	 */
	private static void setDialog(Context ctx, String msg, int resId, int blocktype) {
		if(dialog==null)
			dialog = DefineDialogHelper.createDialog(ctx);
		dialog.setMessage(msg);
		dialog.setImage(ctx, resId);

		if(blocktype==TYPE_BLOCK)
			dialog.setCanceledOnTouchOutside(false);
	}

	public static void dismiss() {
		if(dialog!=null && dialog.isShowing())
			dialog.dismiss();
	}




	/**
	 * 计时关闭对话框
	 * 
	 */
	private static void dismissAfter2s() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					handler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	

	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what==0)
				dismiss();
		};
	};
	

}
