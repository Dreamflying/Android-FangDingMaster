/*package com.open_demo.main;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeNotify;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.gotye.api.PathUtil;
import com.gotye.api.listener.LoginListener;
import com.gotye.api.listener.NotifyListener;
import com.open_demo.LoginPage;
import com.open_demo.util.BeepManager;
import com.open_demo.util.BitmapUtil;
import com.open_demo.util.FileUtil;
import com.project.iwant.NewMainFragment;
import com.project.iwant.R;
@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener,
		LoginListener, NotifyListener {
	private MessageFragment messageFragment;
	private ContactsFragment contactsFragment;
	private SettingFragment settingFragment;
	private NewMainFragment  mainFragment;
	private View messageLayout;
	private View contactsLayout;
	private View settingLayout;
	private View myselfLayout;
	private ImageView messageImage;
	private ImageView contactsImage;
	private ImageView settingImage;
	private ImageView myselfImage;
	private FragmentManager fragmentManager;

	private TextView msgTip;
	private int currentPosition = 0;
	private BeepManager beep;
	private GotyeAPI api;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		api = GotyeAPI.getInstance();
		setContentView(R.layout.layout_main);
		api.addListerer(this);
		beep = new BeepManager(MainActivity.this);
		beep.updatePrefs();
		initViews();
		fragmentManager = getFragmentManager();
		setTabSelection(1);
		// Intent toService=new Intent(this, GotyeService.class);
		// toService.setAction(GotyeService.ACTION_RUN_ON_UI);
		// startService(toService);
		// 清理掉通知栏
		clearNotify();
	}

	private boolean returnNotify = false;

	@Override
	protected void onResume() {
		super.onResume();
		returnNotify = false;
		mainRefresh();
	}

	@Override
	protected void onPause() {
		returnNotify = true;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// 保持好习惯，销毁时请移除监听
		api.removeListener(this);
		// 告诉service已经处于后台运行状态
		// Intent toService=new Intent(this, GotyeService.class);
		// toService.setAction(GotyeService.ACTION_RUN_BACKGROUND);
		// startService(toService);
		super.onDestroy();
	}

	private void initViews() {
		messageLayout = findViewById(R.id.message_layout);
		contactsLayout = findViewById(R.id.contacts_layout);
		settingLayout = findViewById(R.id.setting_layout);
		myselfLayout=findViewById(R.id.new_myself_layout);
		msgTip = (TextView) findViewById(R.id.new_msg_tip);

		messageImage = (ImageView) findViewById(R.id.message_image);
		contactsImage = (ImageView) findViewById(R.id.contacts_image);
		settingImage = (ImageView) findViewById(R.id.setting_image);
		myselfImage=(ImageView)findViewById(R.id.new_myself_image);
		messageLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		myselfLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contacts_layout:
			setTabSelection(0);
			break;
		case R.id.message_layout:
			setTabSelection(1);
			break;
		case R.id.setting_layout:
			setTabSelection(2);
			break;
		case R.id.new_myself_layout:
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	@SuppressLint("NewApi")
	private void setTabSelection(int index) {
		currentPosition = index;
		clearSelection();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			contactsImage.setImageResource(R.drawable.new_tab_home);
			if (contactsFragment == null) {
				mainFragment = new NewMainFragment();
				transaction.replace(R.id.content, mainFragment);
			} else {
				transaction.show(mainFragment);
			}
			break;
		case 1:
			messageImage.setImageResource(R.drawable.new_tab_message);
			if (messageFragment == null) {
				messageFragment = new MessageFragment();
				transaction.replace(R.id.content, messageFragment);
			} else {
				transaction.show(messageFragment);
			}
			break;
		case 2:
			settingImage.setImageResource(R.drawable.new_tab_search);
			if (settingFragment == null) {
				settingFragment = new SettingFragment();
				transaction.replace(R.id.content, settingFragment);
			} else {
				transaction.show(settingFragment);
			}
			break;
			
		case 3:
			myselfImage.setImageResource(R.drawable.new_tab_me);
		break;
		}
		transaction.commit();
		
	}

	private void clearSelection() {
		messageImage.setImageResource(R.drawable.new_tab_message_f);
		contactsImage.setImageResource(R.drawable.new_tab_home_f);
		settingImage.setImageResource(R.drawable.new_tab_search_f);
		myselfImage.setImageResource(R.drawable.new_tab_me_f);
	}

	@SuppressLint("NewApi")
	private void hideFragments(FragmentTransaction transaction) {
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (contactsFragment != null) {
			transaction.hide(contactsFragment);
		}
		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
	}

	// 更新提醒
	public void updateUnReadTip() {
		int unreadCount = api.getTotalUnreadMsgCount();
		int unreadNotifyCount = api.getUnreadNotifyCount();
		unreadCount += unreadNotifyCount;
		msgTip.setVisibility(View.VISIBLE);
		if (unreadCount > 0 && unreadCount < 100) {
			msgTip.setText(String.valueOf(unreadCount));
		} else if (unreadCount >= 100) {
			msgTip.setText("99");
		} else {
			msgTip.setVisibility(View.GONE);
		}
	}

	// 页面刷新
	private void mainRefresh() {
		updateUnReadTip();
		messageFragment.refresh();
		if (contactsFragment != null) {
			contactsFragment.refresh();
		}

	}

	// 此处处理账号在另外设备登陆造成的被动下线
	@Override
	public void onLogout(int code) {
		if (code == GotyeStatusCode.CODE_FORCELOGOUT) {
			Toast.makeText(this, "您的账号在另外一台设备上登录了！", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getBaseContext(), LoginPage.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
		} else if (code == GotyeStatusCode.CODE_NETWORD_DISCONNECTED) {
			Toast.makeText(this, "您的账号掉线了！", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getBaseContext(), LoginPage.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
		}else{
			Intent i = new Intent(this, LoginPage.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			Toast.makeText(this, "退出登陆！", Toast.LENGTH_SHORT).show();
			startActivity(i);
		}
		finish();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent != null) {
			int tab = intent.getIntExtra("tab", -1);
			if (tab == 1) {
				contactsFragment.refresh();
			}
			int notify=intent.getIntExtra("notify", 0);
			if(notify==1){
				clearNotify();
			}
			
			int selection_index=intent.getIntExtra("selection_index", -1);
			if(selection_index==1){
				setTabSelection(1);
			}
		}

	}
	private void clearNotify(){
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
		messageFragment.refresh();
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
		messageFragment.refresh();
	}

	// 收到群邀请信息
	@Override
	public void onReceiveNotify(int code,GotyeNotify notify) {
		if (returnNotify) {
			return;
		}
		messageFragment.refresh();
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
		messageFragment.refresh();
		contactsFragment.refresh();
	}

	@Override
	public void onAddFriend(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		if (returnNotify) {
			return;
		}
		if (currentPosition == 1) {
			contactsFragment.refresh();
		}
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
		settingFragment.modifyUserIcon(smallPath);
	}

	@Override
	public void onNotifyStateChanged() {
		// TODO Auto-generated method stub
		mainRefresh();
	}

	@Override
	public void onLogin(int code, GotyeUser currentLoginUser) {
		// TODO Auto-generated method stub

	}
}
*/