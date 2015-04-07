package com.utils.widght;


import com.project.iwant.R;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**自定义	VIEW
 * @author Ly
 * @time 2014年6月4日下午7:39:28
 * @email liyuan6522151@gmail.com
 */
public class DefineDisplayView {
	
	private static String TOAST_TimeOut = "连接超时";
	private static String TOAST_SERVER_ERROR = "网络不给力哦!";
	private static String TOAST_NoDataTAG = "数据为空";
	private static String TOAST_PutFailureTAG = "上传数据失败....";
	private static String TOAST_PutSuccessTAG = "上传数据成功....";
	private static String TOAST_EDITTEXTISNULL="数据不能为空";
	private static String Request_progressDialogShow_Title = "加载数据";
	private static String Request_progressDialogShow_Message = "加载数据中...";
	private static String Response_progressDialogShow_Title = "上传数据";
	private static String Response_progressDialogShow_Message = "上传数据中...";
	private static ProgressDialog progressDialog;
	public static Dialog dialog;

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param key
	 */
	public DefineDisplayView(Context context, String key) {
	}

	/**
	 * 显示没有数据的Toast
	 */
	public static void showNoDataToast(Context context) {
		Toast.makeText(context,  TOAST_NoDataTAG, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 显示连接超时的Toast
	 */
	public static void showTimeoutToast(Context context) {
		Toast.makeText(context,  TOAST_TimeOut, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 显示服务器错误的Toast
	 */
	public static void showServerErrorToast(Context context) {
		Toast.makeText(context, TOAST_SERVER_ERROR, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 显示上传失败的Toast
	 */
	public static void showResponseFailToast(Context context) {

		Toast.makeText(context,  TOAST_PutFailureTAG, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 显示上传成功的Toast
	 */
	public static void showResponseSuccessToast(Context context) {

		Toast.makeText(context, TOAST_PutSuccessTAG, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 显示下载数据的processbar
	 */
	public static void showRequestProcessBar(Context context) {

		progressDialog = ProgressDialog.show(context,
				Request_progressDialogShow_Title,
				Request_progressDialogShow_Message);

	}

	/**
	 * 显示上传数据的processbar
	 */
	public static void showResponseProcessBar(Context context) {

		progressDialog = ProgressDialog.show(context,
				Response_progressDialogShow_Title,
				Response_progressDialogShow_Message);

	}

	/**
	 * 关闭processbar
	 */
	public static void closeProcessBar() {

		progressDialog.dismiss();

	}
	
	/**
	 * 显示登录失败的Toast
	 */
	public static void showFailToast(Context context,String msg) {
		Toast.makeText(context,  msg, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 显示EditText输入不能为空
	 */
	public static void showEditNotNull(Context context) {
		Toast.makeText(context,  TOAST_EDITTEXTISNULL, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	private static Dialog createLoadingDialog(Context context) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.view_loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		//tipTextView.setText("");// 设置加载信息

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialogStyle);// 创建自定义样式dialog
		loadingDialog.setCanceledOnTouchOutside(true);
		loadingDialog.setCancelable(true);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		return loadingDialog;

	}

	
	/**显示自定义进度条
	 * @time 2014-4-24 下午3:33:24
	 * @param context
	 * @param msg
	 */
	public static void showLoadingDialog(Context context){
		
		dialog=createLoadingDialog(context);
		dialog.show();
	}
	/**关闭自定义进度条
	 * @time 2014-4-24 下午3:33:38
	 */
	public static void closeLoadingDialog(){
		dialog.cancel();
	}
}
