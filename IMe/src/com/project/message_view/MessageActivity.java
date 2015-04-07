package com.project.message_view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.base.view.BaseActivity;
import com.gotye.api.GotyeMessage;
import com.gotye.api.WhineMode;
import com.open_demo.util.ImageUtils;
import com.open_demo.util.UriImage;
import com.project.iwant.NewMainTabActivity;
import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.project.message_view.MsgExpGv.IaddExp;
import com.project.myself_view.Constants;
import com.utils.app.TimestampTool;
import com.utils.chat.ChatServiceUtils;
import com.utils.chat.NewChatService;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;

/**
 * @description 聊天信息页面
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-3下午7:27:26
 */
public class MessageActivity extends BaseActivity implements OnClickListener {
	// ===========页面切换============
	private static ViewPager viewPager;// 页卡内容
	private List<View> views;// Tab页面列表
	private int currIndex = 0;// 当前页卡编号
	private View exp1, exp2, exp3;// 各个页卡
	// ===========页面切换============
	static List<Map<String, Object>> mMessageList = new ArrayList<Map<String, Object>>();
	private static Context mContext;
	private static String mUsername = "";
	private static String mTouser;
	private static String headImage;
	private String mName;
	private static ListView mMsgLv;
	private static TextView mNameTv;
	private Button mSendBtn, mFaceBtn,message_pic_btn2;
	private static EditText mContentEt;
	private InputMethodManager imm;
	private LinearLayout mClose;
	private static MessageListAdapterS messageListAdapter;
	private static ArrayList<MessageBean> list=new ArrayList<MessageBean>();
	MessageBean messageBean;
	private static final int REQUEST_PIC = 1;
	private static final int REQUEST_CAMERA = 2;

	
	public MessageActivity() {
		super("");
	}
	
	/**
	 * 总控制中心，接受并相应各种控制请求
	 */
	public static Handler mControl = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ChatServiceUtils.FLUSH_MESSAGE:
				Log.v("刷新界面", "从解包信息哪儿");
				getMessageList();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		ChatServiceUtils.isMessageActivityPage=true;
				mContext=MessageActivity.this;
		imm = (InputMethodManager) getSystemService(MessageActivity.this.INPUT_METHOD_SERVICE);
		initView();
		setListener();
		InitViewPager();
		showExp();
		getMessageList();
	}

	private static void getMessageList() {
	list=SQLDatebaseUtils.getInstance(mContext).getMsgList(mUsername, mTouser);
	messageListAdapter=new MessageListAdapterS(mContext, list,headImage);
	mMsgLv.setAdapter(messageListAdapter);
	mMsgLv.requestFocusFromTouch();
	mMsgLv.setSelection(list.size()-1);
	messageListAdapter.notifyDataSetChanged();
	mMsgLv.post(new Runnable() {
	        @Override
	        public void run() {
	            // Select the last row so it will scroll into view...
	        	mMsgLv.setSelection(messageListAdapter.getCount() - 1);
	        }
	    });
	/*mMsgLv.setItemChecked(list.size()-1, true);
	mMsgLv.setSelection(list.size()-1);
	mMsgLv.smoothScrollToPosition(list.size()-1);*/

	if (ChatServiceUtils.isMessageFragmentPage) {
	Message message=new Message();
	message.what=ChatServiceUtils.FLASH_MAIN_MESSAGE;
	NewMainTabActivity.mMainControl.sendMessage(message);
	Message myFMessage=new Message();
	myFMessage.what=ChatServiceUtils.FLUSH_MESSAGE;
	NewMessageFragment.mMainFragmentControl.sendMessage(myFMessage);
	}
	mContentEt.setFocusable(true);
	 mContentEt.setFocusableInTouchMode(true);
	    mContentEt.requestFocus();
	}

	@Override
	public void initView() {
		/**接收传个来的信息*/
		
		Bundle bundle = this.getIntent().getExtras();
		mTouser = bundle.getString("username");
		mName = bundle.getString("name");
		headImage=bundle.getString("headimage");
		SQLDatebaseUtils.getInstance(mContext).changeMessageReadStatus(mTouser);
		SharePreferenceUtils.getIntance(mContext).saveUnLookMessageFlag(0);
		Log.v("intent-data", mTouser+"  "+mName+" "+headImage);
		mUsername = SharePreferenceUtils.getIntance(mContext).getCurrentUserName();
		mMsgLv = (ListView) findViewById(R.id.message_content_lv);
		mNameTv = (TextView) findViewById(R.id.message_friend_name_tv);
		mSendBtn = (Button) findViewById(R.id.message_send_btn);
		mContentEt = (EditText) findViewById(R.id.message_content_edit);
		mFaceBtn = (Button) findViewById(R.id.message_face_btn1);
	    mClose = (LinearLayout) findViewById(R.id.message_title_logo_img);
	    message_pic_btn2=(Button)findViewById(R.id.message_pic_btn2);
	    mNameTv.setText(mName);
	    if (ChatServiceUtils.isMessageFragmentPage) {
	    	Message message=new Message();
	    	message.what=ChatServiceUtils.FLASH_MAIN_MESSAGE;
	    	NewMainTabActivity.mMainControl.sendMessage(message);
	    	Message myFMessage=new Message();
	    	myFMessage.what=ChatServiceUtils.FLUSH_MESSAGE;
	    	NewMessageFragment.mMainFragmentControl.sendMessage(myFMessage);
	    	}

	}

	@Override
	public void setListener() {
		mSendBtn.setOnClickListener(this);
		mFaceBtn.setOnClickListener(this);
		mContentEt.setOnClickListener(this);
		mClose.setOnClickListener(this);
		message_pic_btn2.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.message_send_btn:
			mContentEt.setFocusable(true);
			 mContentEt.setFocusableInTouchMode(true);
			    mContentEt.requestFocus();
			String mContent = mContentEt.getText().toString();
			mContentEt.setText("");
			// 聊天
		    messageBean=new MessageBean();
			messageBean.setUser(mUsername);
			messageBean.setToUser(mTouser);
			messageBean.setToName(mName);
			messageBean.setHeadImage(headImage);
			messageBean.setTime((TimestampTool.getCurrentTime())+"");
			messageBean.setContent(mContent);
			messageBean.setStatus("0");
			messageBean.setReadStatus("1");
			NewChatService.sendMessage(messageBean, true);
			//ChatService.sendChatMessage(messageBean, true);
			Message myFMessage=new Message();
	    	myFMessage.what=ChatServiceUtils.FLUSH_MESSAGE;
	    	mControl.sendMessage(myFMessage);
     		hideFace();
			//imm.hideSoftInputFromWindow(mContentEt.getWindowToken(), 0);
			break;
		case R.id.message_title_logo_img:
			imm.hideSoftInputFromWindow(mContentEt.getWindowToken(), 0);
			finish();
			break;
		case R.id.message_content_edit:
			hideFace();
			break;
		case R.id.message_face_btn1:
			if (getFaceStatus()) {
				hideFace();
			} else {
				showFace();
			}
			break;
		case R.id.message_pic_btn2:
			takePic();
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}
	/*
	 * 显示图片
	 */
	private static GridView mExpGv1, mExpGv2, mExpGv3;

	private void showExp() {
		MsgExpGv mMsgExpGv1 = new MsgExpGv(mContext, 0,new IaddExp() {
			
			@Override
			public void addIExp(String str, int positon) {
				addExp(str, positon);
				
			}
		});
		MsgExpGv mMsgExpGv2 = new MsgExpGv(mContext,1, new IaddExp() {
			
			@Override
			public void addIExp(String str, int positon) {
				addExp(str, positon);
				
			}
		});
		MsgExpGv mMsgExpGv3 = new MsgExpGv(mContext, 2,new IaddExp() {
			
			@Override
			public void addIExp(String str, int positon) {
				addExp(str, positon);
				
			}
		});
		mExpGv1.setAdapter(mMsgExpGv1);
		mExpGv2.setAdapter(mMsgExpGv2);
		mExpGv3.setAdapter(mMsgExpGv3);
	}

	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.vPager1);
		views = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		exp1 = inflater.inflate(R.layout.msg_exp1, null);
		exp2 = inflater.inflate(R.layout.msg_exp2, null);
		exp3 = inflater.inflate(R.layout.msg_exp3, null);
		views.add(exp1);
		views.add(exp2);
		views.add(exp3);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mExpGv1 = (GridView) exp1.findViewById(R.id.msg_exp_gv1);
		mExpGv2 = (GridView) exp2.findViewById(R.id.msg_exp_gv2);
		mExpGv3 = (GridView) exp3.findViewById(R.id.msg_exp_gv3);
	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
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

	/*
	 * 添加表情
	 */
	public static void addExp(String str, int postion) {
		if (postion <= 21) {
			// 根据资源ID获得资源图像的Bitmap对象
			Bitmap bitmap = BitmapFactory.decodeResource(
					mContext.getResources(), Constants.face1[postion - 1]);
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
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
					mContext.getResources(), Constants.face2[postion - 22]);
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
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
					mContext.getResources(), Constants.face3[postion - 44]);
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
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
  @Override
protected void onResume() {
	super.onResume();
	mMsgLv.setSelection(mMsgLv.getBottom());
	ChatServiceUtils.isMessageActivityPage=true;
}
  
  @Override
protected void onPause() {
	super.onPause();
	ChatServiceUtils.isMessageActivityPage=false;
}

	
private void takePic(){
	Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
	intent.setType("image/*");
	startActivityForResult(intent, REQUEST_PIC);
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// 选取图片的返回值
	if (requestCode == REQUEST_PIC) {
		//
		if (resultCode == RESULT_OK) {
			Uri originalUri = data.getData();
			if(handlePic(originalUri, 0)){
				return;
			}
		}
	}else if (requestCode == REQUEST_CAMERA) {
		if (resultCode == RESULT_OK) {
			File cameraTmp = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/gotye/gotyecamera");
			int degree = ImageUtils.getBitmapOritation(cameraTmp.getAbsolutePath());
			if(handlePic(Uri.fromFile(cameraTmp), degree)){
				return;
			}
		}
	}
	//TODO 获取图片失败
	super.onActivityResult(requestCode, resultCode, data);
}

private boolean handlePic(Uri originalUri, int degree){
	// 照片的原始资源地址
	WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

	int width = wm.getDefaultDisplay().getWidth();//屏幕宽度
	int height = wm.getDefaultDisplay().getHeight();//屏幕高度
	UriImage uriImage = new UriImage(this, originalUri);
	byte[] imageData = uriImage.getResizedImage(width, height, 10240 * 6);
	// 使用ContentProvider通过URI获取原始图片
	Bitmap photo = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
	
	photo = ImageUtils.ratoteBitmap(photo, degree);
	
	if (photo != null) {
		// 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
		// 释放原始图片占用的内存，防止out of memory异常发生
		imm.hideSoftInputFromWindow(mContentEt.getWindowToken(), 0);
		messageBean=new MessageBean();
		messageBean.setToUser(mTouser);
		  messageBean=new MessageBean();
			messageBean.setUser(mUsername);
			messageBean.setToUser(mTouser);
			messageBean.setToName(SharePreferenceUtils.getIntance(mContext).getCurrentNickName());
			messageBean.setHeadImage(SharePreferenceUtils.getIntance(mContext).getHeadImgUserName());
			messageBean.setTime((TimestampTool.getCurrentTime())+"");
			messageBean.setContent("#image#");
			messageBean.setStatus("0");
			messageBean.setReadStatus("0");
		NewChatService.sendImageMessage(photo,messageBean);
		
		return true;
	}
	return false;
}

/**
 * 发送图片消息
 * @param bitmap
 */


}
