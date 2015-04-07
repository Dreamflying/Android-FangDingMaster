package com.utils.widght;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.project.message_view.MsgExpGv;
import com.project.message_view.MsgExpGv.IaddExp;
import com.project.myself_model.MyselfInfoComment;
import com.project.myself_model.NearByInfoComment;
import com.project.myself_view.Constants;
import com.utils.app.TimestampTool;
import com.utils.chat.ChatService;
import com.utils.chat.ChatServiceUtils;
import com.utils.chat.NewChatService;
import com.utils.io.SharePreferenceUtils;

/**
 * @description 输入message 框
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-10上午11:23:28
 */
public class InputMessageView extends PopupWindow implements OnClickListener {
	private static InputMessageView inputMessageView;
	private static Context context;
	private Button mSendBtn, mFaceBtn;
	private static EditText mContentEt;
	private static ViewPager viewPager;// 页卡内容
	private List<View> views;// Tab页面列表
	private View exp1, exp2, exp3;// 各个页卡
	private static GridView mExpGv1, mExpGv2, mExpGv3;
	private int currIndex = 0;// 当前页卡编号
	private InputMethodManager imm;
	private MyselfInfoComment messageBean;
	private PopupWindow pop;
IsendMessage  isendMessage;
private int flag=0;
	private InputMessageView() {

	}

	public static InputMessageView getIntance() {
		if (inputMessageView == null) {
			inputMessageView = new InputMessageView();
		}
		return inputMessageView;
	}

	/**
	 * @function 显示pop
	 * @time 2014-10-2下午1:20:08
	 * @param view
	 *            void
	 */
	public InputMessageView showInputMessageView(Context context, View view,MyselfInfoComment messageBean,IsendMessage isendMessage,int flag) {
		Log.d("--", "postion");
		this.isendMessage=isendMessage;
		this.context = context;
		this.messageBean=messageBean;
		this.flag=flag;
		imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		 PopupWindow pop =null ;
		if (pop==null) {
			final View popupWindow = layoutInflater.inflate(
					R.layout.view_inputmessage_popupwindow, null);
			pop= new PopupWindow(view,
					LayoutParams.FILL_PARENT, 70,true);
			// 设置SelectPicPopupWindow弹出窗体可点击
			pop.setContentView(popupWindow);   
			pop.setBackgroundDrawable(new BitmapDrawable());
			pop.setOutsideTouchable(true);
			pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED); 
			pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
	                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			//pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			if (pop.isShowing()) {
				pop.dismiss();
			} else {
				pop.showAsDropDown(view);
				
			}
			initPopView(popupWindow);
			setListener();
			this.pop=pop;
		}
		
		return inputMessageView;
		
	}

	public interface IsendMessage{
		
		public void sendMyselfComment(MyselfInfoComment myselfInfoComment);
		public void sendNearComment(NearByInfoComment nearByInfoComment);
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.message_send_btn:
			pop.dismiss();
			imm.hideSoftInputFromWindow(((Button)arg0).getWindowToken(), 0);
			String mContent = mContentEt.getText().toString();
			// 聊天
			mContentEt.setText("");
			hideFace();
			imm.hideSoftInputFromWindow(mContentEt.getWindowToken(), 0);
		    // 发送评论，同时聊天信息
			messageBean.setContent(mContent);
			//ChatService.sendChatMessage(messageBean,false);
		//	NewChatService.sendMessage(messageBean, false);
			isendMessage.sendMyselfComment(messageBean);
			
		/*	
			if (flag==1) {
				MyselfInfoComment myselfInfoComment =new MyselfInfoComment();
				myselfInfoComment.setContent(mContent);
				myselfInfoComment.setDtid(messageBean.getId()+"");
				myselfInfoComment.setUser(messageBean.getUser());
				Log.v("TimestampTool.getCurrentTime()", TimestampTool.getCurrentTime()+"");
				myselfInfoComment.setTime(TimestampTool.getCurrentTime());
				//myselfInfoComment.setOrigin_username("");
				myselfInfoComment.setName(SharePreferenceUtils.getIntance(context).getCurrentNickName());
				
			}else {
				
				NearByInfoComment nearByInfoComment =new NearByInfoComment();
				nearByInfoComment.setContent(mContent);
				nearByInfoComment.setGbid(messageBean.getId()+"");
				nearByInfoComment.setUser(messageBean.getUser());
				nearByInfoComment.setTime(Integer.parseInt(TimestampTool.getCurrentTime()+""));
				nearByInfoComment.setOrigin_username("  ");
				nearByInfoComment.setOrigin_username(messageBean.getToName());
				isendMessage.sendNearComment(nearByInfoComment);
			}*/
			
			break;
		case R.id.message_content_edit:
			hideFace();
			break;
//		case R.id.message_face_btn1:
//			if (getFaceStatus()) {
//				hideFace();
//			} else {
//				showFace();
//			}
//			break;

		default:
			break;
		}

	}

	/**
	 * @function 设置监听事件
	 * @time 2014-10-2下午1:39:49 void
	 */
	private void setListener() {
		mSendBtn.setOnClickListener(this);
		//mFaceBtn.setOnClickListener(this);
		mContentEt.setOnClickListener(this);
	}

	/**
	 * @function 装载UI，获取ID
	 * @time 2014-10-2下午1:39:38
	 * @param popupWindow
	 *            void
	 */
	private void initPopView(View popupWindow) {
		viewPager = (ViewPager) popupWindow.findViewById(R.id.vPager1);
		mSendBtn = (Button) popupWindow.findViewById(R.id.message_send_btn);
		mContentEt = (EditText) popupWindow
				.findViewById(R.id.message_content_edit);
		/*mFaceBtn = (Button) popupWindow.findViewById(R.id.message_face_btn1);*/
		/*views = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(context);
		exp1 = inflater.inflate(R.layout.msg_exp1, null);
		exp2 = inflater.inflate(R.layout.msg_exp2, null);
		exp3 = inflater.inflate(R.layout.msg_exp3, null);
		views.add(exp1);
		views.add(exp2);
		views.add(exp3);
		viewPager.setAdapter(new MessageViewPager(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mExpGv1 = (GridView) exp1.findViewById(R.id.msg_exp_gv1);
		mExpGv2 = (GridView) exp2.findViewById(R.id.msg_exp_gv2);
		mExpGv3 = (GridView) exp3.findViewById(R.id.msg_exp_gv3);
		showExp();*/
	}

	private void showExp() {
MsgExpGv mMsgExpGv1 = new MsgExpGv(context, 0,new IaddExp() {
			
			@Override
			public void addIExp(String str, int positon) {
				addExp(str, positon);
				
			}
		});
		MsgExpGv mMsgExpGv2 = new MsgExpGv(context,1, new IaddExp() {
			
			@Override
			public void addIExp(String str, int positon) {
				addExp(str, positon);
				
			}
		});
		MsgExpGv mMsgExpGv3 = new MsgExpGv(context, 2,new IaddExp() {
			
			@Override
			public void addIExp(String str, int positon) {
				addExp(str, positon);
				
			}
		});
		mExpGv1.setAdapter(mMsgExpGv1);
		mExpGv2.setAdapter(mMsgExpGv2);
		mExpGv3.setAdapter(mMsgExpGv3);
	}

	public class MessageViewPager extends PagerAdapter {
		private List<View> mListViews;

		public MessageViewPager(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			currIndex = arg0;
		}
	}

	private void showFace() {
		imm.hideSoftInputFromWindow(mContentEt.getWindowToken(), 0);
		viewPager.setVisibility(View.VISIBLE);
		// 强行隐藏输入法
	}

	private void hideFace() {
		viewPager.setVisibility(View.GONE);
	}

	private boolean getFaceStatus() {
		if (viewPager.getVisibility() == View.VISIBLE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @description 输入结果接口
	 * @author Ly
	 * @email 1522651962@qq.com
	 * @time 2014-10-10上午11:31:20
	 */
	public interface ISendResult {
		public void sendSuccess();

		public void sendFail();

	}
	/*
	 * 添加表情
	 */
	public static void addExp(String str, int postion) {
		if (postion <= 21) {
			// 根据资源ID获得资源图像的Bitmap对象
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), Constants.face1[postion - 1]);
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(context, bitmap);
			// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
			SpannableString spannableString = new SpannableString(str);
			// 用ImageSpan对象替换face
			if (postion <= 9) {
				spannableString.setSpan(imageSpan, 0, 3,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else {
				spannableString.setSpan(imageSpan, 0, 3,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			// 将随机获得的图像追加到EditText控件的最后
			mContentEt.append(spannableString);

		} else if (postion <= 43 && postion > 21) {
			// 根据资源ID获得资源图像的Bitmap对象
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), Constants.face2[postion - 22]);
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(context, bitmap);
			// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
			SpannableString spannableString = new SpannableString(str);
			// 用ImageSpan对象替换face
			spannableString.setSpan(imageSpan, 0, 3,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// 将随机获得的图像追加到EditText控件的最后
			mContentEt.append(spannableString);

		} else {
			// 根据资源ID获得资源图像的Bitmap对象
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), Constants.face3[postion - 44]);
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(context, bitmap);
			// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
			SpannableString spannableString = new SpannableString(str);
			// 用ImageSpan对象替换face
			spannableString.setSpan(imageSpan, 0, 3,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// 将随机获得的图像追加到EditText控件的最后
			mContentEt.append(spannableString);

		}

		mContentEt.setText(mContentEt.getText());
	}
}
