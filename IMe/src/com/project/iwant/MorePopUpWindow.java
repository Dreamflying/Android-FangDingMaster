package com.project.iwant;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.iwant_or_ihave_view.IwantAndIhaveManageActivity;
import com.project.myself_view.FriendActivity;
import com.project.myself_view.MyselfInfoActivity;
import com.utils.io.SharePreferenceUtils;

/**
 * @description 更多界面
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-2下午1:16:35
 */
public class MorePopUpWindow implements OnClickListener {

	private static MorePopUpWindow morePopUpWindow;
	private PopupWindow mPop;
	private TextView tv_pop_username;
	private ImageView iv_friendimg;
	private Button btn_address;
	private Button btn_feedback;
	private Button home_more_ppw_contentmanage_btn;
	private Button btn_exit, btn_setUp;
	private RelativeLayout rl_myself, rl_more;
	private Context context;
	

	private MorePopUpWindow() {

	}

	public static MorePopUpWindow getIntance() {
		if (morePopUpWindow == null) {
			morePopUpWindow = new MorePopUpWindow();
		}
		return morePopUpWindow;
	}

	/**
	 * @function 显示pop
	 * @time 2014-10-2下午1:20:08
	 * @param view
	 *            void
	 */
	public void showMorePopWindow(Context context, View view) {
		this.context=context;
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View popupWindow = layoutInflater.inflate(
				R.layout.view_main_more_popupwindow, null);
		if (mPop == null) {
			mPop = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
		}
		if (mPop.isShowing()) {
			mPop.dismiss();
		} else {
			mPop.showAsDropDown(view, 0, 0);
			mPop.setFocusable(true);
			mPop.setBackgroundDrawable(new BitmapDrawable());
			mPop.setOutsideTouchable(true);
			initPopView(popupWindow);
			setListener();
		}
	}

	/**
	 * @function 设置监听事件
	 * @time 2014-10-2下午1:39:49 void
	 */
	private void setListener() {
		rl_more.setOnClickListener(this);
		rl_myself.setOnClickListener(this);
		btn_address.setOnClickListener(this);
		btn_setUp.setOnClickListener(this);
		btn_feedback.setOnClickListener(this);
		btn_exit.setOnClickListener(this);
		home_more_ppw_contentmanage_btn.setOnClickListener(this);
	}

	/**
	 * @function 装载UI，获取ID
	 * @time 2014-10-2下午1:39:38
	 * @param popupWindow
	 *            void
	 */
	private void initPopView(View popupWindow) {
		tv_pop_username = (TextView) popupWindow
				.findViewById(R.id.popupwindow_username_tv);
		Log.v("NICK", SharePreferenceUtils.getIntance(context).getCurrentNickName());
		tv_pop_username.setText(SharePreferenceUtils.getIntance(context).getCurrentNickName());
		rl_more = (RelativeLayout) popupWindow
				.findViewById(R.id.home_more_ppw_rly);
		iv_friendimg = (ImageView) popupWindow.findViewById(R.id.friend_img);
		rl_myself = (RelativeLayout) popupWindow
				.findViewById(R.id.home_more_ppw_my_rly);
		btn_address = (Button) popupWindow
				.findViewById(R.id.home_more_ppw_adls_btn);
		btn_setUp = (Button) popupWindow
				.findViewById(R.id.home_more_ppw_setup_btn);
		btn_feedback = (Button) popupWindow
				.findViewById(R.id.home_more_ppw_feelback_btn);
		btn_exit = (Button) popupWindow
				.findViewById(R.id.home_more_ppw_quit_btn);
		home_more_ppw_contentmanage_btn= (Button) popupWindow
				.findViewById(R.id.home_more_ppw_contentmanage_btn);
		rl_more.setFocusableInTouchMode(true);

	}

	@Override
	public void onClick(View arg0) {
		mPop.dismiss();
		switch (arg0.getId()) {
		case R.id.home_more_ppw_rly:
			break;
		case R.id.home_more_ppw_my_rly:
			Intent myselfInfoActivity =new Intent(context,MyselfInfoActivity.class);
			context.startActivity(myselfInfoActivity);
			break;
		case R.id.home_more_ppw_adls_btn:
			Intent adInfoActivity =new Intent(context,FriendActivity.class);
			context.startActivity(adInfoActivity);

			break; 
		case R.id.home_more_ppw_setup_btn:
			Intent setActivity =new Intent(context,SetActivity.class);
			context.startActivity(setActivity);

			break;
		case R.id.home_more_ppw_feelback_btn:
			Intent feedActivity =new Intent(context,FeedBackActivity.class);
			context.startActivity(feedActivity);
			break;
		case R.id.home_more_ppw_quit_btn:
			System.exit(0);
			break;
		case R.id.home_more_ppw_contentmanage_btn:
			Intent home_more_ppw_contentmanage_btn =new Intent(context,IwantAndIhaveManageActivity.class);
			context.startActivity(home_more_ppw_contentmanage_btn);
			break;
		default:
			break;
		}

	}

}
