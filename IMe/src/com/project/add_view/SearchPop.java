package com.project.add_view;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.base.common.Common;
import com.project.iwant.R;

public class SearchPop implements OnClickListener {
		private static SearchPop morePopUpWindow;
		private PopupWindow mPop;
		private RelativeLayout rl_myself, rl_more;
		private Context context;
		private LinearLayout two,three;
		ISearch iSearch;
		

		private SearchPop() {

		}

		public static SearchPop getIntance() {
			if (morePopUpWindow == null) {
				morePopUpWindow = new SearchPop();
			}
			return morePopUpWindow;
		}

		/**
		 * @function 显示pop
		 * @time 2014-10-2下午1:20:08
		 * @param view
		 *            void
		 */
		public void showMorePopWindow(Context context, View view,ISearch iSearch) {
			this.context=context;
			this.iSearch=iSearch;
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View popupWindow = layoutInflater.inflate(
					R.layout.select_search_pop, null);
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
			two=(LinearLayout)popupWindow.findViewById(R.id.two);
			three=(LinearLayout)popupWindow.findViewById(R.id.three);
			rl_more.setFocusableInTouchMode(true);

		}

		@Override
		public void onClick(View arg0) {
			mPop.dismiss();
			switch (arg0.getId()) {
			case R.id.home_more_ppw_rly:
				break;
			case R.id.two:
				iSearch.searchLocal();
				break; 
			case R.id.three:
				iSearch.searchCountry();
				break;
			default:
				break;
			}

			
			
		}
		public interface ISearch{
			public void searchLocal();
			public void searchCountry();
		}
}
