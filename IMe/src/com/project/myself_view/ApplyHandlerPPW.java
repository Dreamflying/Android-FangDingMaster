package com.project.myself_view;

import java.util.List;

import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.utils.chat.ChatService;
import com.utils.chat.ChatServiceUtils;
import com.utils.io.SharePreferenceUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class ApplyHandlerPPW {
	@SuppressWarnings("unused")
	private static Context content;
	private static RelativeLayout mTrue, mFalse, mLoad,aLL,cll;

	@SuppressLint("InflateParams")
	public static void startPopWin(final Context mContent, View v,
			final String id,final String postsid, final String getuser,final String mContentStr,final String mName,final IApply iApply) {
		content = mContent;
		LayoutInflater inflater = LayoutInflater.from(mContent);
		View view = inflater.inflate(R.layout.apply_handler_ppw, null);
		final PopupWindow pop = new PopupWindow(view,
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, false);
		aLL=(RelativeLayout)view.findViewById(R.id.home_music_list_rly);
		cll=(RelativeLayout)view.findViewById(R.id.music_more_rly12);
		mTrue = (RelativeLayout) view.findViewById(R.id.apply_true);
		mFalse = (RelativeLayout) view.findViewById(R.id.apply_false);
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
		mTrue.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (arg1.getAction()==MotionEvent.ACTION_DOWN) {
					cll.setBackgroundResource(R.drawable.pingyue_press);
					pop.dismiss();
					// 同意
					String mContentStr1;
					if (mContentStr.length() < 20) {
						mContentStr1 = "同意了您关于“" + mContentStr
								+ "”的申请。";
					} else {
						mContentStr1 = "同意了您关于“"
								+ mContentStr.substring(0, 20)
								+ "……”的申请。";
					}
					// 发消息
					MessageBean messageBean=new MessageBean();
					messageBean.setContent(ChatServiceUtils.HAN+mContentStr1+"@"+SharePreferenceUtils.getIntance(mContent).getHeadImgUserName()+"@"+SharePreferenceUtils.getIntance(mContent).getCurrentNickName());
					messageBean.setUser(SharePreferenceUtils.getIntance(mContent).getCurrentUserName());
					messageBean.setToUser(getuser);
					ChatService.sendChatMessage(messageBean, false);
					/**刷新申请列表*/
					iApply.apply(id);
					Log.v("apply", "here");
				}
				if (arg1.getAction()==MotionEvent.ACTION_UP) {
					cll.setBackgroundResource(R.drawable.apply_piyue);
				}
				
				return true;
			}
		});
		mTrue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
			}
		});
		mFalse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		mFalse.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (arg1.getAction()==MotionEvent.ACTION_DOWN) {
					cll.setBackgroundResource(R.drawable.gun_press);
					pop.dismiss();
					String mContentStr1;
					// 拒绝
					if (mContentStr.length() < 20) {
						mContentStr1 = "拒绝了您关于“" + mContentStr
								+ "”的需求。";
					} else {
						mContentStr1 = "拒绝了您关于“"
								+ mContentStr.substring(0, 20)
								+ "……”的需求。";
					}
					// 发消息
					MessageBean messageBean=new MessageBean();
					messageBean.setContent(ChatServiceUtils.HAN+mContentStr1);
					messageBean.setUser(SharePreferenceUtils.getIntance(mContent).getCurrentUserName());
					messageBean.setToUser(getuser);
					ChatService.sendChatMessage(messageBean, false);
					
					/**刷新申请列表*/	
					Log.v("refuse", "here");
					iApply.refuse(id);
				}
				if (arg1.getAction()==MotionEvent.ACTION_UP) {
					cll.setBackgroundResource(R.drawable.apply_piyue);
				}
				
				return true;
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
		public void apply(String postid);
		public void refuse(String postid);
		
	}
}
