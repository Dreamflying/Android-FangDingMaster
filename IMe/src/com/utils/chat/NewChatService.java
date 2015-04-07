package com.utils.chat;

import java.util.List;
import java.util.UUID;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeNotify;
import com.gotye.api.listener.GotyeListener;
import com.gotye.api.listener.GroupListener;
import com.project.message_model.MessageBean;
import com.utils.app.NetworkConnected;
import com.utils.app.UIHelper;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.Noti;

public class NewChatService extends Service implements GotyeListener,
		 GroupListener {

	public static GotyeAPI api;

	private NetworkReceiver networkReceiver;

	private static final String CONFIG = "login_config";

	public static final String ACTION_LOGIN = "com.gotye.action_login";
	public static final String ACTION_LOGOUT = "com.gotye.action_logout";
	public static final String EXTRA_USERNAME = "extra_username";
	public static final String EXTRA_USERPSD = "extra_userpsd";

	private static String mPsd;

	private String packageName;
	private static Context context;
    private static boolean isSave;
	 
	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

/*	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent == null) {
			return super.onStartCommand(intent, flags, startId);
		}
		String action = intent.getAction();
		mPsd = intent.getStringExtra(EXTRA_USERPSD);
		if (ACTION_LOGIN.equals(action)) {
			if (api != null) {
				api.addLoginListener(this);
				// api.login();
				api.login(mPsd);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}*/

	@Override
	public void onCreate() {
		super.onCreate();
		context=NewChatService.this;
	
		packageName = getPackageName();
		// 1. 获取地理位置   2. 用户聊天用户进行登录  3.进行消息的发送与接收，然后刷新各自，页面进行通知
		if (!SharePreferenceUtils.getIntance(NewChatService.this)
				.getCurrentUserName().equals("default")) {
			/** 启动定位 */
			/*LocationUtils.getInstance(NewChatService.this)
					.getLocationUseBaiDuSDK();*/
			/** 启动聊天 */
			/**  先检测网络*/
			if (NetworkConnected.isNetworkConnected(NewChatService.this)) {
				login(context, SharePreferenceUtils.getIntance(context).getCurrentUserName(), null);
				if (api != null) {
					// api.login();
					/*api.login(mPsd);
					api.addLoginListener(this);*/
				}
				
			}else {
				UIHelper.ToastMessage(NewChatService.this, "网络异常，请检查网络");
			}
			

		}
		
		if (networkReceiver == null) {
			networkReceiver = new NetworkReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			registerReceiver(networkReceiver, filter);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (api != null) {
			/*api.removeLoginListener(this);
			api.removeGroupListener(this);
			api.removeChatListener(this);*/
		}
		if (networkReceiver != null) {
			unregisterReceiver(networkReceiver);
		}
	}

	/**
	 * 应用启动，如果登陆过则直接登录创建 api 对象
	 * 
	 * @param context
	 */
	public static void appStart(Context context) {
		// String lastUser = getUser(context);
		String[] mLastUser = getUser(context);
		String lastUser = mLastUser[0];
		String lastPsd = mLastUser[1];

		if (TextUtils.isEmpty(lastUser)) {
			return;
		}
		if (TextUtils.isEmpty(lastPsd)) {
			return;
		}
		// login(context, lastUser);
		login(context, lastUser, lastPsd);

	}

	/**
	 * 登录
	 * 
	 * @param context
	 * @param username
	 */
	public static GotyeAPI login(Context context, String username,
			String password) {
		/*// public static GotyeAPI login(Context context, String username){
		GotyeAPI lastApi = api;
		// 踢掉上一个用户
		api = Gotye.getInstance().makeGotyeAPIForUser(username);
		if (lastApi != null && lastApi.getUsername().equals(username)) {
			// 删除所有回调
			lastApi.removeAllChatListener();
			lastApi.removeAllLoginListener();
			lastApi.removeAllRoomListener();
			lastApi.removeAllUserListener();

			lastApi.logout();
		}*/
		saveUser(context, username, password);
		// saveUser(context, username);
		/*Intent intent = new Intent(context, NewChatService.class);
		intent.setAction(ACTION_LOGIN);
		intent.putExtra(EXTRA_USERNAME, username);
		intent.putExtra(EXTRA_USERPSD, password);
		context.startService(intent);*/
		return api;
	}
	
	

	/**
	 * 退出登录
	 * 
	 * @param context
	 */
	public static void logout(Context context) {
		if (api != null) {
			// 删除所有回调
			/*api.removeAllChatListener();
			api.removeAllLoginListener();
			api.removeAllRoomListener();s
			api.removeAllUserListener();*/
			api.logout();
			api = null;
		}
		clearUser(context);
		Intent intent = new Intent(context, NewChatService.class);
		intent.setAction(ACTION_LOGOUT);
		context.startService(intent);

	}

	/**
	 * 清除用户
	 * 
	 * @param context
	 */
	public static void clearUser(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.clear();
		ed.commit();
	}

	/**
	 * 保存用户
	 * 
	 * @param context
	 * @param username
	 */
	public static void saveUser(Context context, String username,
			String password) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.putString("username", username);
		ed.putString("password", password);
		ed.commit();
	}

	// public static void saveUser(Context context, String username){
	// SharedPreferences sp = context.getSharedPreferences(CONFIG,
	// Context.MODE_PRIVATE);
	// Editor ed = sp.edit();
	// ed.putString("username", username);
	// ed.commit();
	// }

	/**
	 * 获取用户
	 * 
	 * @param context
	 * @return
	 */
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

	/**
	 * @function  发送消息
	 * @time 2014-12-9下午10:25:46
	 * @param bean
	 * void
	 */
	public static void sendMessage(MessageBean bean,boolean save){
		/*Log.v("sendMss", "isHere");
		GotyeUser gotyeUser=new GotyeUser(bean.getToUser());
		GotyeTextMessage message = new GotyeTextMessage(UUID.randomUUID().toString(), TimeUtil.getCurrentTime(), gotyeUser, gotyeUser);
		message.setText(bean.getContent()+"@"+SharePreferenceUtils.getIntance(context).getHeadImgUserName()+"@"+SharePreferenceUtils.getIntance(context).getCurrentNickName());
		if (api!=null) {
			api.sendMessageToTarget(message);
		}
		isSave=save;
		if (isSave) {s
		try {
			SQLDatebaseUtils.getInstance(context).getDbUtils().save(bean);
		} catch (DbException e) {
			Log.e("sql-log", e.toString());
			e.printStackTrace();
		}
		}*/
	}
	/** 
	 * @param bitmap
	 */
	public static  void sendImageMessage(Bitmap bitmap,MessageBean messageBean){ 
		/*//构造图片消息并发送
		Log.v("here", "image");
		GotyeUser gotyeUser=new GotyeUser(messageBean.getToUser());
		GotyeImageMessage imageMessage = new GotyeImageMessage(UUID.randomUUID().toString(), TimeUtil.getCurrentTime(),gotyeUser,gotyeUser );
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 80, bout);
		imageMessage.setImageData(bout.toByteArray());
		imageMessage.setExtraData("image".getBytes());
		imageMessage.setMessageID(messageBean.getHeadImage()+"@"+messageBean.getToName());
		//api.sendMessageToTarget(imageMessage);
		
		try {
			SQLDatebaseUtils.getInstance(context).getDbUtils().save(messageBean);
		} catch (DbException e) {
			Log.e("sql-log", e.toString());
			e.printStackTrace();
		}
		*/
		
	}
	// public static String getUser(Context context){
	// SharedPreferences sp = context.getSharedPreferences(CONFIG,
	// Context.MODE_PRIVATE);
	//
	// return sp.getString("username",null);
	// }


	private void checkLogin(int code) {
		if (!isNetworkAvailable(this)) {
			// 没有网络停止检查登录状态
			KeepAlive.stopKeepAlive(this);
			return;
		}
/*
		if (code != GotyeStatusCode.STATUS_OK) {
			KeepAlive.startKeepAlive(this);
		} else {
			KeepAlive.stopKeepAlive(this);
		}*/
	}

	private class NetworkReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (api == null) {
				return;
			}
			boolean success = false;
			// 获得网络连接服务
			ConnectivityManager connManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			// State state = connManager.getActiveNetworkInfo().getState();
			State state = connManager.getNetworkInfo(
					ConnectivityManager.TYPE_WIFI).getState(); // 获取网络连接状态
			if (State.CONNECTED == state) { // 判断是否正在使用WIFI网络
				success = true;
			}

			state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
					.getState(); // 获取网络连接状态
			if (State.CONNECTED != state) { // 判断是否正在使用GPRS网络
				success = true;
			}
			if (success) {
				if (api.isOnline()) {
					// 在线就停止
					KeepAlive.stopKeepAlive(context);
				} else {
					KeepAlive.startKeepAlive(context);
					// api.login();
					//api.login(mPsd);
				}
			} else {
				// 没有网络停止检查登录状态
				KeepAlive.stopKeepAlive(context);
			}
		}
	}

	/**
	 * @author Administrator
	 * 
	 */
	public static class KeepAlive extends BroadcastReceiver {

		/**
		 * @param name
		 */
		public KeepAlive() {
			// super("keep-alive");
		}

		public static String ACTION_STAMP = UUID.randomUUID().toString();
		public static boolean isStart = false;
		public static final String ACTION_KEEP_ALIVE = "com.gotye.sdk.action_keep_alive";

		private static KeepAlive keep;

		public static void startKeepAlive(Context context) {
			if (isStart) {
				return;
			}
			if (keep == null) {
				keep = new KeepAlive();
			}
			isStart = true;
			AlarmManager am = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			// 实例化intent
			IntentFilter filter = new IntentFilter(ACTION_KEEP_ALIVE + "."
					+ ACTION_STAMP);
			context.registerReceiver(keep, filter);
			Intent intent = new Intent();
			intent.setAction(ACTION_KEEP_ALIVE + "." + ACTION_STAMP);
			// 实例化pendingintent
			PendingIntent pi = PendingIntent
					.getBroadcast(context, 0, intent, 0);
			am.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis(), 10000, pi);
			Log.d("heart", "START keepalive");
		}

		public static void stopKeepAlive(Context context) {
			if (isStart == false) {
				return;
			}
			isStart = false;
			if (keep != null) {
				context.unregisterReceiver(keep);
				keep = null;
			}
			AlarmManager am = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			// 实例化intent
			Intent intent = new Intent();
			intent.setAction(ACTION_KEEP_ALIVE + "." + ACTION_STAMP);
			// 实例化pendingintent
			PendingIntent pi = PendingIntent
					.getBroadcast(context, 0, intent, 0);
			am.cancel(pi);
			Log.d("heart", "stop keepalive");
		}

		// @Override
		protected void onHandleIntent(Intent intent) {
			String action = intent.getAction();
			if ((ACTION_KEEP_ALIVE + "." + ACTION_STAMP).equals(action)
					&& api != null) {
				// api.login();
				//api.login(mPsd);
				Log.d("", "login alarm");
			}
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			onHandleIntent(intent);
		}
	}

	/**
	 * 对网络连接状态进行判断
	 * 
	 * @return true, 可用； false， 不可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}

		return false;
	}

	private void notify(String msg) {
		if (packageName.equals(getTopAppPackage(this))) {
			return;
		}
		Noti.openNoti(NewChatService.this, "我", "您有" + SharePreferenceUtils.getIntance(NewChatService.this).getUnLookMessageFlag()
				+ "条未读消息");
	}

	public static String getTopAppPackage(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getApplicationContext().getSystemService("activity");
		RunningTaskInfo currentRun = activityManager.getRunningTasks(1).get(0);
		ComponentName nowApp = currentRun.topActivity;
		String packname = nowApp.getPackageName();
		return packname;
	}

/*	@Override
	public void onSendMessage(String appKey, String username,
			GotyeMessage message, int errorCode) {
		Log.v("send", "Success");
		if (isSave) {
			Message messs=new Message();
			messs.what=ChatServiceUtils.FLUSH_MESSAGE;
			MessageActivity.mControl.sendMessage(messs);
		}

	}*/

/*	@Override
	public void onReceiveMessage(String appKey, String username,
			GotyeMessage message) {
		Log.d("recevice", "Success");
		Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(500);
		if (message instanceof GotyeTextMessage) {
		GotyeTextMessage textMessage=(GotyeTextMessage)message;
		Log.d("内容", textMessage.getText());
		Log.d("时间", textMessage.getCreateTime()+"");
		Log.d("发送者的ID", textMessage.getSender().getUsername());
		Log.d("本人ID", username);
		String[] spitContent=textMessage.getText().split("@");
		
		*//**添加friend 表 和数据*//*
		FriendsBean friendsBean=new FriendsBean();
		friendsBean.setFriendHeadImage(spitContent[1]);
		friendsBean.setFriendNickName(spitContent[2]);
		friendsBean.setFriendUserName(textMessage.getSender().getUsername());
		friendsBean.setUsername(username);
		try {
			SQLDatebaseUtils.getInstance(context).getDbUtils().save(friendsBean);
		} catch (DbException e1) {
			e1.printStackTrace();
		}
		
		
		if (textMessage.getText().contains(ChatServiceUtils.COMMENT)) {
			
			SharePreferenceUtils.getIntance(context).saveUnLookMessageFlag(SharePreferenceUtils.getIntance(context).getUnLookMessageFlag()+1);
			CommentBean commentMessageBean=new CommentBean();
			commentMessageBean.setContent(spitContent[0].replace(ChatServiceUtils.COMMENT, ""));
			commentMessageBean.setHeadImage(spitContent[1]);
			commentMessageBean.setTime(message.getCreateTime()+"");
			commentMessageBean.setToName(spitContent[2]);
			commentMessageBean.setToUser(textMessage.getSender().getUsername());
			commentMessageBean.setUser(username);
			commentMessageBean.setReadStatus("0");
			commentMessageBean.setStatus("1");	
			try {
				SQLDatebaseUtils.getInstance(context).getDbUtils().save(commentMessageBean);
			} catch (DbException e) {
				e.printStackTrace();
			}
			notify(message.getSender().getUsername() + "来新消息了");
			
		}
		else {
			
		
			
	  
		MessageBean messageBean=new MessageBean();
		messageBean.setContent(spitContent[0]);
		messageBean.setHeadImage(spitContent[1]);
		messageBean.setTime(message.getCreateTime()+"");
		messageBean.setToName(spitContent[2]);
		messageBean.setToUser(textMessage.getSender().getUsername());
		messageBean.setUser(username);
		messageBean.setToUser(textMessage.getSender().getUsername());
		messageBean.setUser(username);
		if (ChatServiceUtils.isMessageActivityPage) {
			messageBean.setReadStatus("1");
		}else {
			messageBean.setReadStatus("0");
			  SharePreferenceUtils.getIntance(context).saveUnLookMessageFlag(SharePreferenceUtils.getIntance(context).getUnLookMessageFlag()+1);
		}
	
		messageBean.setStatus("1");
		try {
			SQLDatebaseUtils.getInstance(context).getDbUtils().save(messageBean);
		} catch (DbException e) {
			e.printStackTrace();
		}
		}
		
		Message msg1;
		if (ChatServiceUtils.isMessageActivityPage) {
			msg1 = new Message();
			msg1.what = ChatServiceUtils.FLUSH_MESSAGE;
			MessageActivity.mControl.sendMessage(msg1);
		}else {
			
			if (ChatServiceUtils.isNewMainActivityPage) {
				msg1 = new Message();
				msg1.what = ChatServiceUtils.FLASH_MAIN_MESSAGE;
				NewMainTabActivity.mMainControl.sendMessage(msg1);
				msg1 = new Message();
				msg1.what = ChatServiceUtils.FLUSH_MESSAGE;
				NewMessageFragment.mMainFragmentControl.sendMessage(msg1);
			}else {
				Noti.openNoti(context, "我", "您有" + SharePreferenceUtils.getIntance(context).getUnLookMessageFlag()
						+ "条未读消息");
			}
		}*/
		/*}else if(message instanceof GotyeImageMessage) {
			GotyeImageMessage imageMessage = (GotyeImageMessage) message;
			byte[] thumbnailData = imageMessage.getThumbnailData();
			if(thumbnailData == null){
				thumbnailData = new byte[0];
			}
	           Log.v("username", username);
	           Log.v("imageMessage.getMessageID()", imageMessage.getMessageID());
	           Log.v("imageMessage.getSender().getUsername()", imageMessage.getSender().getUsername());
	           Log.v("imageMessage.getDownloadUrl()", imageMessage.getDownloadUrl());
			 String []extral=imageMessage.getMessageID().split("@");
			 SharePreferenceUtils.getIntance(context).saveUnLookMessageFlag(SharePreferenceUtils.getIntance(context).getUnLookMessageFlag()+1);
				MessageBean messageBean=new MessageBean();
				messageBean.setHeadImage(extral[0]);
				messageBean.setTime(message.getCreateTime()+"");
				messageBean.setToName(extral[1]);
				messageBean.setToUser(imageMessage.getSender().getUsername());
				messageBean.setUser(username);
				messageBean.setReadStatus("0");
				messageBean.setImageData(imageMessage.getImageData());
				messageBean.setThumbnailData( thumbnailData);
				messageBean.setImageUrl(imageMessage.getDownloadUrl());
				messageBean.setStatus("1");
				try {
					SQLDatebaseUtils.getInstance(context).getDbUtils().save(messageBean);
				} catch (DbException e) {
					e.printStackTrace();
				}
		}
		
		
		
	}*/





	@Override
	public void onCreateGroup(int code, com.gotye.api.GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJoinGroup(int code, com.gotye.api.GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveGroup(int code, com.gotye.api.GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDismissGroup(int code, com.gotye.api.GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKickOutUser(int code, com.gotye.api.GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetGroupList(int code,
			List<com.gotye.api.GotyeGroup> grouplist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestGroupInfo(int code, com.gotye.api.GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetGroupMemberList(int code,
			List<com.gotye.api.GotyeUser> allList,
			List<com.gotye.api.GotyeUser> curList,
			com.gotye.api.GotyeGroup group, int pagerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveGroupInvite(int code, com.gotye.api.GotyeGroup group,
			com.gotye.api.GotyeUser sender, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveRequestJoinGroup(int code,
			com.gotye.api.GotyeGroup group, com.gotye.api.GotyeUser sender,
			String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveReplayJoinGroup(int code,
			com.gotye.api.GotyeGroup group, com.gotye.api.GotyeUser sender,
			String message, boolean isAgree) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetOfflineMessageList(int code,
			List<com.gotye.api.GotyeMessage> messagelist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSearchGroupList(int code,
			List<com.gotye.api.GotyeGroup> mList,
			List<com.gotye.api.GotyeGroup> curList, int pageIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onModifyGroupInfo(int code, com.gotye.api.GotyeGroup gotyeGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChangeGroupOwner(int code, com.gotye.api.GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserJoinGroup(com.gotye.api.GotyeGroup group,
			com.gotye.api.GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserLeaveGroup(com.gotye.api.GotyeGroup group,
			com.gotye.api.GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserDismissGroup(com.gotye.api.GotyeGroup group,
			com.gotye.api.GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserKickdFromGroup(com.gotye.api.GotyeGroup group,
			com.gotye.api.GotyeUser kicked, com.gotye.api.GotyeUser actor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSendNotify(int code, GotyeNotify notify) {
		// TODO Auto-generated method stub
		
	}
}
