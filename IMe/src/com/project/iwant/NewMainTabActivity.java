package com.project.iwant;

import java.io.File;
import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import cn.waps.AppConnect;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeNotify;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.gotye.api.PathUtil;
import com.gotye.api.listener.LoginListener;
import com.gotye.api.listener.NotifyListener;
import com.gotye.api.listener.UserListener;
import com.open_demo.GotyeService;
import com.open_demo.main.MessageFragment;
import com.open_demo.util.BeepManager;
import com.open_demo.util.BitmapUtil;
import com.open_demo.util.FileUtil;
import com.project.add_view.NewAddActivity;
import com.project.add_view.NewSearchFragment;
import com.project.login_view.LoginActivity;
import com.project.myself_view.NewMyselfFragment;
import com.utils.app.ActivityManagerUtils;
import com.utils.chat.ChatServiceUtils;
import com.utils.http.LocationGpsUtils;
import com.utils.http.LocationUtils;
import com.utils.io.SharePreferenceUtils;

public class NewMainTabActivity extends FragmentActivity implements
		OnClickListener, OnTabChangeListener, LoginListener, NotifyListener ,UserListener{
	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;
	private RelativeLayout home_title_pop_rl;
	private String displayPointsText;
	// 定义一个布局
	private LayoutInflater layoutInflater;
	ImageView[] tabimageView = new ImageView[4];
	TextView[] tabtextView = new TextView[4];
	static TextView[] home_myall_tex = new TextView[4];
	private LinearLayout new_tab_bg;
	private int click_times=0;

	// 定义数组来存放Fragment界面
	private Class fragmentArray[] = { NewMainFragment.class,
			MessageFragment.class, NewSearchFragment.class,
			NewMyselfFragment.class };

	// 定义数组来存放按钮图片
	private int mImageViewArray[] = { R.drawable.new_tab_home_f,
			R.drawable.new_tab_message_f, R.drawable.new_tab_search_f,
			R.drawable.new_tab_me_f };
	private int mImageViewArrayClick[] = { R.drawable.new_tab_home,
			R.drawable.new_tab_message, R.drawable.new_tab_search,
			R.drawable.new_tab_me };

	// Tab选项卡的文字
	private String mTextviewArray[] = { "首页", "消息", "搜索", "我" };

	private Button btn_more, btn_add;
	private TextView select_sex;
	private static Context context;
	private BeepManager beep;
	private static GotyeAPI api;
	private GotyeUser user;
	private boolean returnNotify = false;

	/** 添加一个总的handler 去控制页面消息信号的刷新 */
	public static Handler mMainControl = new Handler() {
		Message msg1;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ChatServiceUtils.FLASH_MAIN_MESSAGE:
				//setHomeMyAllTv("1");
				updateUnReadTip();
				break;
			default:
				break;
			}

		}

	};

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppConnect.getInstance(this);
		ActivityManagerUtils.getIntance().addActivity("NewMainTabActivity",
				NewMainTabActivity.this);
		ChatServiceUtils.isNewMainActivityPage = true;
		api = GotyeAPI.getInstance();
		setContentView(R.layout.new_main_tab_layout);
		context = NewMainTabActivity.this;
		// 登录的时候要传入登录监听，当重复登录时会直接返回登录状态
			Log.v("Login", "out");
		GotyeAPI.getInstance().addListerer(NewMainTabActivity.this);
		if (GotyeAPI.getInstance().isOnline()) {
			
		}else {
			int i = GotyeAPI.getInstance().login(SharePreferenceUtils.getIntance(NewMainTabActivity.this).getCurrentUserName(), null);
			// 根据返回的code判断
			if (i == GotyeStatusCode.CODE_OK) {
				// 已经登陆
				onLogin(i, null);
			}
		}
	//	LocationGpsUtils.startGPS(context);
		LocationUtils.getInstance(context)
		.getLocationUseBaiDuSDK();
		initView();
		api.addListerer(this);
		beep = new BeepManager(NewMainTabActivity.this);
		beep.updatePrefs();
		// Intent toService=new Intent(this, GotyeService.class);
		// toService.setAction(GotyeService.ACTION_RUN_ON_UI);
		// startService(toService);
		// 清理掉通知栏
		clearNotify();
	}

	/**
	 * 初始化组件
	 */
	@SuppressLint("ResourceAsColor")
	private void initView() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);
		btn_more = (Button) findViewById(R.id.main_more);
		btn_add = (Button) findViewById(R.id.main_submit);
		select_sex = (TextView) findViewById(R.id.select_sex);
		home_title_pop_rl = (RelativeLayout) findViewById(R.id.home_title_pop_rl);
		new_tab_bg = (LinearLayout) findViewById(R.id.new_tab_bg);
		btn_add.setVisibility(View.GONE);
		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			/*
			 * mTabHost.getTabWidget().getChildAt(i)
			 * .setBackgroundResource(R.drawable.selector_tab_background);
			 */
		}

		tabimageView[0].setImageResource(mImageViewArrayClick[0]);
		mTabHost.setCurrentTab(0);
		mTabHost.setOnTabChangedListener(this);
		btn_more.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		select_sex.setOnClickListener(this);
		tabimageView[0].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mTabHost.setCurrentTab(0);
				click_times++;
				Log.v("click_times",click_times+"");
				if(click_times==2){
					//刷新首页
					Log.v("click", "2 ci");
					if(NewMainFragment.iwantBeanslist.size()!=0){
						NewMainFragment.mlv_iwant.setSelection(0);
					}
					
					click_times=0;
				}
			}
		});
		// new_tab_bg.setBackgroundResource(R.drawable.new_bg_false);
		setHomeMyAllTv("1");
	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		tabimageView[index] = (ImageView) view.findViewById(R.id.imageview);
		tabimageView[index].setImageResource(mImageViewArray[index]);
		home_myall_tex[index] = (TextView) view
				.findViewById(R.id.home_myall_text);

		return view;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//AppConnect.getInstance(this).close();
		ChatServiceUtils.isNewMainActivityPage = false;
		GotyeAPI.getInstance().removeListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.main_more:
			Intent intent = new Intent(NewMainTabActivity.this,
					NewAddActivity.class);
			intent.putExtra("flag", 3);
			startActivity(intent);
			break;
		case R.id.select_sex:
			SelectorSexPop.getIntance().showMorePopWindow(
					NewMainTabActivity.this, arg0);
		default:
			break;
		}

	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onTabChanged(String arg0) {
		if (arg0.equals(mTextviewArray[0])) {
			btn_more.setVisibility(View.VISIBLE);
			select_sex.setVisibility(View.VISIBLE);
			btn_add.setVisibility(View.GONE);
			tabimageView[0].setImageResource(mImageViewArrayClick[0]);
			tabimageView[1].setImageResource(mImageViewArray[1]);
			tabimageView[2].setImageResource(mImageViewArray[2]);
			tabimageView[3].setImageResource(mImageViewArray[3]);
			/*
			 * tabtextView[0].setTextColor(Color.GREEN);
			 * tabtextView[1].setTextColor(R.color.false_color);
			 * tabtextView[2].setTextColor(R.color.false_color);
			 * tabtextView[3].setTextColor(R.color.false_color);
			 */
			home_title_pop_rl.setVisibility(View.VISIBLE);
			
		

		}
		if (arg0.equals(mTextviewArray[1])) {
			click_times=0;
			btn_more.setVisibility(View.GONE);
			btn_add.setVisibility(View.GONE);
			select_sex.setVisibility(View.GONE);
			tabimageView[1].setImageResource(mImageViewArrayClick[1]);
			tabimageView[0].setImageResource(mImageViewArray[0]);
			tabimageView[2].setImageResource(mImageViewArray[2]);
			tabimageView[3].setImageResource(mImageViewArray[3]);
			/*
			 * tabtextView[0].setTextColor(R.color.false_color);
			 * tabtextView[1].setTextColor(R.color.click_color);
			 * tabtextView[2].setTextColor(R.color.false_color);
			 * tabtextView[3].setTextColor(R.color.false_color);
			 */
			home_title_pop_rl.setVisibility(View.VISIBLE);
			MessageFragment.refresh();
			//setHomeMyAllTv("1");
		}
		if (arg0.equals(mTextviewArray[2])) {
			btn_more.setVisibility(View.GONE);
			btn_add.setVisibility(View.GONE);
			select_sex.setVisibility(View.GONE);
			home_title_pop_rl.setVisibility(View.GONE);
			tabimageView[2].setImageResource(mImageViewArrayClick[2]);
			tabimageView[0].setImageResource(mImageViewArray[0]);
			tabimageView[1].setImageResource(mImageViewArray[1]);
			tabimageView[3].setImageResource(mImageViewArray[3]);
			/*
			 * tabtextView[0].setTextColor(R.color.false_color);
			 * tabtextView[1].setTextColor(R.color.false_color);
			 * tabtextView[2].setTextColor(R.color.click_color);
			 * tabtextView[3].setTextColor(R.color.false_color);
			 */
			click_times=0;

		}
		if (arg0.equals(mTextviewArray[3])) {
			btn_more.setVisibility(View.GONE);
			select_sex.setVisibility(View.GONE);
			home_title_pop_rl.setVisibility(View.VISIBLE);
			tabimageView[3].setImageResource(mImageViewArrayClick[3]);
			tabimageView[0].setImageResource(mImageViewArray[0]);
			tabimageView[1].setImageResource(mImageViewArray[1]);
			tabimageView[2].setImageResource(mImageViewArray[2]);
			/*
			 * tabtextView[0].setTextColor(R.color.false_color);
			 * tabtextView[1].setTextColor(R.color.false_color);
			 * tabtextView[2].setTextColor(R.color.false_color);
			 * tabtextView[3].setTextColor(R.color.click_color);
			 */
			click_times=0;
		}

	}

	/*
	 * 显示我的列表上面的红色数字
	 */
	public static void setHomeMyAllTv(String mStr) {
		if (mStr.equals("0")) {
			SharePreferenceUtils.getIntance(context).saveUnLookMessageFlag(0);
			home_myall_tex[1].setVisibility(View.GONE);
		} else {
			int num = SharePreferenceUtils.getIntance(context)
					.getUnLookMessageFlag()
					+ SharePreferenceUtils.getIntance(context).getLinkNum()+SharePreferenceUtils.getIntance(context).getCommetnNum();
			Log.v("NUM", "set" + num);
			if (num == 0) {
				home_myall_tex[1].setVisibility(View.GONE);
			} else {
				home_myall_tex[1].setVisibility(View.VISIBLE);
				home_myall_tex[1].setText(num + "");
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		returnNotify = false;
		ChatServiceUtils.isNewMainActivityPage = true;
		mainRefresh();
	}

	@Override
	protected void onPause() {
		ChatServiceUtils.isNewMainActivityPage = false;
		returnNotify = true;
		super.onPause();
	}

	@Override
	protected void onStop() {
		ChatServiceUtils.isNewMainActivityPage = false;
		super.onPause();
	}

	private void clearNotify() {
		NotificationManager notificationManager = (NotificationManager) this
				.getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
	}

	// 收到消息（此处只是单纯的更新聊天历史界面，不涉及聊天消息处理，当然你也可以处理，若你非要那样做）
	@Override
	public void onReceiveMessage(int code, GotyeMessage message, boolean unRead) {
		if (returnNotify) {
			return;
		}
		MessageFragment.refresh();
		if (unRead) {
			updateUnReadTip();

			if (!api.isNewMsgNotify()) {
				return;
			}
			if (message.getReceiverType() == 2) {
				if (api.isNotReceiveGroupMsg()) {
					return;
				}
				if (api.isGroupDontdisturb(((GotyeGroup) message.getReceiver())
						.getGroupID())) {
					return;
				}
			}
			beep.playBeepSoundAndVibrate();
		}
	}

	// 自己发送的信息统一在此处理
	@Override
	public void onSendMessage(int code, GotyeMessage message) {
		if (returnNotify) {
			return;
		}
		MessageFragment.refresh();
	}

	// 收到群邀请信息
	@Override
	public void onReceiveNotify(int code, GotyeNotify notify) {
		if (returnNotify) {
			return;
		}
		MessageFragment.refresh();
		updateUnReadTip();
		if (!api.isNotReceiveGroupMsg()) {
			beep.playBeepSoundAndVibrate();
		}
	}

	@Override
	public void onRemoveFriend(int code, GotyeUser user) {
		if (returnNotify) {
			return;
		}
		api.deleteSession(user);
		MessageFragment.refresh();
		// contactsFragment.refresh();
	}

	@Override
	public void onAddFriend(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		if (returnNotify) {
			return;
		}
		/*
		 * if (currentPosition == 1) { contacstsFragment.refresh(); }
		 */
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 选取图片的返回值
		if (resultCode == RESULT_OK) {
			if (data != null) {
				Uri selectedImage = data.getData();
				if (selectedImage != null) {
					String path = FileUtil.uriToPath(this, selectedImage);
					setPicture(path);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setPicture(String path) {
		File f = new File(PathUtil.getAppFIlePath());
		if (!f.isDirectory()) {
			f.mkdirs();
		}
		File file = new File(PathUtil.getAppFIlePath()
				+ System.currentTimeMillis() + "jpg");
		if (file.exists()) {
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Bitmap smaillBit = BitmapUtil.getSmallBitmap(path, 50, 50);
		String smallPath = BitmapUtil.saveBitmapFile(smaillBit);
		// settingFragment.modifyUserIcon(smallPath);
	}

	@Override
	public void onNotifyStateChanged() {
		// TODO Auto-generated method stub
		mainRefresh();
	}

	// 更新提醒
	public static void updateUnReadTip() {
		int unreadCount = api.getTotalUnreadMsgCount();
		int unreadNotifyCount = api.getUnreadNotifyCount();
		unreadCount += unreadNotifyCount;
		unreadCount = unreadCount
				+ SharePreferenceUtils.getIntance(context).getLinkNum()+SharePreferenceUtils.getIntance(context).getCommetnNum();
		home_myall_tex[1].setVisibility(View.VISIBLE);
		if (unreadCount > 0 && unreadCount < 100) {
			home_myall_tex[1].setText(String.valueOf(unreadCount));
		} else if (unreadCount >= 100) {
			home_myall_tex[1].setText("99");
		} else {
			home_myall_tex[1].setVisibility(View.GONE);
		}
	}

	// 页面刷新
	private void mainRefresh() {
		updateUnReadTip();
		MessageFragment.refresh();
		// 联系人刷新
		/*
		 * if (contactsFragment != null) { contactsFragment.refresh(); }
		 */

	}

	// 此处处理账号在另外设备登陆造成的被动下线
	@Override
	public void onLogout(int code) {
		if (code == GotyeStatusCode.CODE_FORCELOGOUT) {
			Toast.makeText(this, "您的账号在另外一台设备上登录了！", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		} else if (code == GotyeStatusCode.CODE_NETWORD_DISCONNECTED) {
			Toast.makeText(this, "您的账号掉线了！", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		} else {
			Intent i = new Intent(this, LoginActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			Toast.makeText(this, "退出登陆！", Toast.LENGTH_SHORT).show();
			startActivity(i);
			finish();
		}
		finish();
	}

	@Override
	public void onLogin(int code, GotyeUser currentLoginUser) {
		// 判断登陆是否成功
		if (code == GotyeStatusCode.CODE_OK) {
			 saveUser(SharePreferenceUtils.getIntance(context).getCurrentUserName(), null);
			Intent toService = new Intent(this, GotyeService.class);
			startService(toService);
			//Toast.makeText(this, "聊天系统登录成功", Toast.LENGTH_SHORT).show();
		} else {
			// 失败,可根据code定位失败原因
			//Toast.makeText(this, "聊天系统登录失败", Toast.LENGTH_SHORT).show();
		}

	}

	private static final String CONFIG = "login_config";

	public void saveUser(String name, String password) {
		SharedPreferences sp = getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString("username", name);
		edit.putString("password", password);
		edit.commit();
	}

	public static String[] getUser(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		String name = sp.getString("username", null);
		String password = sp.getString("password", null);
		String[] user = new String[2];
		user[0] = name;
		user[1] = password;
		return user;
	}
	
	@Override
	public void onModifyUserInfo(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		if (code == 0) {
			// ToastUtil.show(getActivity(), "修改成功!");
		} else {
			//修改失败
		}
	}

	@Override
	public void onRequestUserInfo(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSearchUserList(int code, List<GotyeUser> mList, int pagerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetFriendList(int code, List<GotyeUser> mList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddBlocked(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveBlocked(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetBlockedList(int code, List<GotyeUser> mList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetProfile(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}
}
