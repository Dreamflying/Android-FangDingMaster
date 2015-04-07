package com.utils.widght;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.project.iwant.R;

public class DeleteIwantPop {
	@SuppressWarnings("unused")
	private static Context content;
	private static RelativeLayout mTrue, mFalse, mLoad,aLL,cll;

	@SuppressLint("InflateParams")
	public static void startPopWin(final Context mContent, View v,
			final IApply iApply,final int position) {
		content = mContent;
		LayoutInflater inflater = LayoutInflater.from(mContent);
		View view = inflater.inflate(R.layout.delete_iwant_item, null);
		final PopupWindow pop = new PopupWindow(view,
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, false);
		aLL=(RelativeLayout)view.findViewById(R.id.home_music_list_rly);
		cll=(RelativeLayout)view.findViewById(R.id.music_more_rly12);
		 mFalse= (RelativeLayout) view.findViewById(R.id.delete_false);
		 mTrue = (RelativeLayout) view.findViewById(R.id.delete_true);
		mLoad = (RelativeLayout) view.findViewById(R.id.apply_load);

		// }
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
		if (pop.isShowing()) {
			pop.dismiss();
		} else {
			int height = -(int) (v.getMeasuredHeight() * 2);
			pop.showAtLocation(v, Gravity.CENTER, 0, 0);
		}
		
		mTrue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iApply.apply(position);
				pop.dismiss();
				
			}
		});
mTrue.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (arg1.getAction()==MotionEvent.ACTION_DOWN) {
					mTrue.setBackgroundResource(R.drawable.new_title);
					iApply.apply(position);
					pop.dismiss();
				}
				return true;
			}
		});
mFalse.setOnTouchListener(new View.OnTouchListener() {
	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if (arg1.getAction()==MotionEvent.ACTION_DOWN) {
			mFalse.setBackgroundResource(R.drawable.new_title);
			pop.dismiss();
		}
		return true;
	}
});
		mFalse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});

		mLoad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				// 忽略

				// 处理
			}
		});
		aLL.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pop.dismiss();
				
			}
		});
	}
	
	/**
	 *  @description 
	 *  @author Ly
	 *  @email 1522651962@qq.com
	 *  @time 2014-10-15下午7:04:23
	 */
	public interface IApply{
		public void apply(int position);
		public void refuse(String postid);
		
	}
}
