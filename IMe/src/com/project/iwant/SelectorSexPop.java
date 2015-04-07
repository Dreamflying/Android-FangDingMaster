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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.common.Common;
import com.project.iwant_or_ihave_view.IwantAndIhaveManageActivity;
import com.project.myself_view.FriendActivity;
import com.project.myself_view.MyselfInfoActivity;
import com.utils.io.SharePreferenceUtils;

public class SelectorSexPop implements OnClickListener {
	private static SelectorSexPop morePopUpWindow;
	private PopupWindow mPop;
	private RelativeLayout rl_myself, rl_more;
	private Context context;
	private LinearLayout one,two,three;
	private TextView sex_man,sex_women;
	

	private SelectorSexPop() {

	}

	public static SelectorSexPop getIntance() {
		if (morePopUpWindow == null) {
			morePopUpWindow = new SelectorSexPop();
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
				R.layout.view_selector_sex, null);
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
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		sex_man.setOnClickListener(this);
		sex_women.setOnClickListener(this);
	}

	/**
	 * @function 装载UI，获取ID
	 * @time 2014-10-2下午1:39:38
	 * @param popupWindow
	 *            void
	 */
	private void initPopView(View popupWindow) {
		rl_more = (RelativeLayout) popupWindow
				.findViewById(R.id.home_more_ppw_rly);
		one=(LinearLayout)popupWindow.findViewById(R.id.one);
		two=(LinearLayout)popupWindow.findViewById(R.id.two);
		three=(LinearLayout)popupWindow.findViewById(R.id.three);
		sex_man=(TextView)popupWindow.findViewById(R.id.sex_man);
		sex_women=(TextView)popupWindow.findViewById(R.id.sex_woman);
		rl_more.setFocusableInTouchMode(true);

	}

	@Override
	public void onClick(View arg0) {
		mPop.dismiss();
		switch (arg0.getId()) {
		case R.id.home_more_ppw_rly:
			break;
		case R.id.two:
			Common.select=1;
			new NewMainFragment().getDataBySelection(1, 0);
			break; 
		case R.id.three:
			Common.select=2;
			new NewMainFragment().getDataBySelection(2, 0);
			break;
		case R.id.sex_man:
			new NewMainFragment().getDataBySelection(3, 1);
			break;
		case R.id.sex_woman:
			new NewMainFragment().getDataBySelection(3, 0);
			break;
		default:
			break;
		}

	}
}
