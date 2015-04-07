package com.project.login_view;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.gotye.api.listener.LoginListener;
import com.open_demo.GotyeService;
import com.project.iwant.NewMainTabActivity;
import com.project.iwant.R;
import com.project.login_controller.LoginController;
import com.project.login_model.UserInfoBean;
import com.project.login_model.UserInfoListBean;
import com.utils.app.StringUtils;
import com.utils.app.UIHelper;
import com.utils.db.SQLDatebaseUtils;
import com.utils.http.LocationUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.io.StorageEnvironmentUtils;
import com.utils.widght.DefineDisplayView;

/**
 * @description 登录界面
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-4上午11:19:42
 */
public class LoginActivity extends BaseActivity implements OnClickListener,LoginListener{
	public LoginActivity() {
		super("");
	}
	private EditText phoneEdit;
	private EditText passwordEdit;
	private ProgressDialog dialog;
	private Handler mHandler;

	private InputMethodManager manager;
	// ========第三方登录=========
	private Button loginBtn, registerBtn, login_loginforqq_btn,
			login_sinna_weibo_btn;
	private static String loginUsername = "", loginPassword = "",
			nickname = "", str_QZone = "false", str_SinaWeibo = "false";

	private UserInfoListBean userInfoListBean = new UserInfoListBean();
	private Configuration configuration;

	@Override
	public void onDestroy() {
		// 关闭当前activity时调用的销毁方法
		super.onDestroy();
		dialog = null;
		mHandler = null;
		manager = null;
		loginUsername = null;
		loginPassword = null;
		nickname = null;
		str_QZone = null;
		str_SinaWeibo = null;
		GotyeAPI.getInstance().removeListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//StorageEnvironmentUtils.getRoot();
		SQLDatebaseUtils.getInstance(LoginActivity.this).createDb();
		initView();
		setListener();
		initConfiguration(LoginActivity.this,"LoginActivity");
	    LocationUtils.getInstance(LoginActivity.this).getLocationUseBaiDuSDK();
		/*if (SharePreferenceUtils.getIntance(LoginActivity.this).getCurrentUserName().equals("default")) {
			
		}else {
			if (SharePreferenceUtils.getIntance(LoginActivity.this).getFirstShowOpen()) {
				Intent intent = new Intent(LoginActivity.this, EveryDayDisplayActivity.class);
				startActivity(intent);
				finish();
			}else {
				Intent intent = new Intent(LoginActivity.this,  NewMainTabActivity.class);
				startActivity(intent);
				finish();
				
			//}
			
		}*/
		/*Intent intent = new Intent(LoginActivity.this, ModifyUserInfoActivity.class);
		startActivity(intent);
		finish();*/
		/*MediaPlayer mp = new MediaPlayer();
		mp.reset();
		try {
			  AssetManager assetManager = this.getAssets();  
			  AssetFileDescriptor fileDescriptor = assetManager  
                        .openFd("ime.mp3");  
			  mp.setDataSource(fileDescriptor.getFileDescriptor(),  
                        fileDescriptor.getStartOffset(),  
                        fileDescriptor.getLength());  
			//mp.create(context, R.raw.ime);
			mp.setDataSource(context, RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
			mp.prepare();
		} catch (IllegalArgumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		mp.start();*/
		/*try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/**
		 * test db
		 * *//*
		ArrayList<MessageBean> list=new ArrayList<MessageBean>();
		MessageBean messageBean=new MessageBean();
		messageBean.setStatus("0");
		messageBean.setReadStatus("0");
		messageBean.setUser("1234");
		messageBean.setToUser("1236");
		messageBean.setContent("test");
		MessageBean messageBean1=new MessageBean();
		messageBean1.setStatus("1");
		messageBean1.setUser("1234");
		messageBean1.setToUser("1236");
		messageBean1.setContent("test1");
		messageBean1.setReadStatus("0");
		
		try {
			SQLDatebaseUtils.getInstance(LoginActivity.this).getDbUtils().saveBindingId(messageBean);
			SQLDatebaseUtils.getInstance(LoginActivity.this).getDbUtils().saveBindingId(messageBean1);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
/*		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();*/

		/*
		 * try { List<MessageBean>
		 * messageaList=SQLDatebaseUtils.getInstance(LoginActivity
		 * .this).getDbUtils().findAll(MessageBean.class); for (int i = 0; i <
		 * messageaList.size(); i++) { Log.v("mess",
		 * messageaList.get(i).getId()+""); } } catch (DbException e) { // TODO
		 * Auto-generated cat8ch block } //Log.v("sdpath",
		 * StorageEnvironmentUtils.getSDCardPath());
		 * //LocationUtils.getInstance(
		 * LoginActivity.this).getLocationUseBaiDuSDK(); final File file=new
		 * File(StorageEnvironmentUtils.getSDCardPath()+"/Test.png"); new
		 * Thread(){ public void run(){
		 * 
		 * ImageUpLoad.initBcsSdk(file); }
		 * 
		 * }.start();
		 * 
		 * 
		 * 
		 */
		
/*		new Thread(){
			public void run(){
				*//**test ftp **//*
				 FtpLoadTool.getInstance(new UploadInterface() {
					
					@Override
					public void onSuccess(int arg0, String arg1) {
						
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
					
					}
				}).ftpUpload(StorageEnvironmentUtils.getSDCardPath()+"/", "1234.jpg");
			}
		
	}.start();	*/	
	}

	@Override
	public void initConfiguration(BaseActivity viewActivity,String activityName) {
		super.initConfiguration(viewActivity,activityName);
		configuration = getConfiguration();
		configuration.setClassName(UserInfoListBean.class);
	}

	@Override
	public void initView() {
		// 获取控件
		manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		phoneEdit = (EditText) findViewById(R.id.login_username_edit);
		passwordEdit = (EditText) findViewById(R.id.login_password_btn);
		loginBtn = (Button) findViewById(R.id.login_login_btn);
		registerBtn = (Button) findViewById(R.id.login_register_btn);
		login_loginforqq_btn = (Button) findViewById(R.id.login_loginforqq_btn);
		login_sinna_weibo_btn = (Button) findViewById(R.id.login_sinna_weibo_btn);
	}

	@Override
	public void setListener() {
		loginBtn.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
		login_loginforqq_btn.setOnClickListener(this);
		login_sinna_weibo_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_login_btn:
			// 隐藏软键盘
			manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
			loginUsername = phoneEdit.getText().toString();
			loginPassword = passwordEdit.getText().toString();
			if (StringUtils.isEmpty(loginUsername)) {
				UIHelper.ToastMessage(LoginActivity.this, "亲，用户名不能为空哦");
				return;
			}
			if (StringUtils.isEmpty(loginPassword)) {
				UIHelper.ToastMessage(LoginActivity.this, "亲，密码不能为空哦");
				return;
			}
			/*if (loginUsername.length() < 6 || loginUsername.length() > 14) {
				UIHelper.ToastMessage(LoginActivity.this, "亲，用户名长度为6-14位哦");
				return;
			}*/
			if (loginPassword.length() < 6 || loginPassword.length() > 14) {
				UIHelper.ToastMessage(LoginActivity.this, "亲，密码长度为6-14位哦");
				return;
			}
			DefineDisplayView.showLoadingDialog(LoginActivity.this);
			ArrayList<UserInfoBean> list = new ArrayList<UserInfoBean>();
			UserInfoBean userInfoBean = new UserInfoBean();
			userInfoBean.setUsername(loginUsername);
			userInfoBean.setPassword(loginPassword);
			list.add(userInfoBean);
			userInfoListBean.setArray(list);
			configuration.setViewDataObject(userInfoListBean);
			new LoginController(getConfiguration()).getUserInfo();
			break;
		case R.id.login_loginforqq_btn:
			try {
				// if (str_QZone.equals("false")) {
				// Platform plat = platforms.get(1);
				// plat.setPlatformActionListener(LoginActivity.this);
				// plat.authorize();
				// } else {
				// getPDAServerData();
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case R.id.login_sinna_weibo_btn:
			try {
				// if (str_SinaWeibo.equals("false")) {
				// Platform plat1 = platforms.get(0);
				// plat1.setPlatformActionListener(LoginActivity.this);
				// plat1.authorize();
				// } else {
				// getPDAServerData();
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.login_register_btn:
			Intent reIntent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(reIntent);
			finish();
			break;
		}
	}

	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		userInfoListBean = (UserInfoListBean) object;
		SharePreferenceUtils.getIntance(LoginActivity.this)
				.saveCurrentUserName(
						loginUsername);
		SharePreferenceUtils.getIntance(LoginActivity.this)
		.saveCurrentNickName(
				userInfoListBean.getArray().get(0).getNickname());
		SharePreferenceUtils.getIntance(LoginActivity.this)
		.saveHeadImgUserName(
				userInfoListBean.getArray().get(0).getHeadimg());
		SharePreferenceUtils.getIntance(LoginActivity.this)
		.saveSex(userInfoListBean.getArray().get(0).getSex());
		Intent i = new Intent(LoginActivity.this, NewMainTabActivity.class);
		startActivity(i);
		this.finish();
		/*int i = GotyeAPI.getInstance().login(SharePreferenceUtils.getIntance(LoginActivity.this).getCurrentUserName(), null);
		// 根据返回的code判断
		if (i == GotyeStatusCode.CODE_OK) {
			// 已经登陆
			onLogin(i, null);
		}*/
	}

	@Override
	public void requestErrorCode(String eString) {
		super.requestErrorCode(eString);
		DefineDisplayView.closeLoadingDialog();
		UIHelper.ToastMessage(LoginActivity.this, eString);
	}

	/**
	 * 点击空白处隐藏软键盘
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onLogin(int code, GotyeUser user) {
		Log.v("login", code+"");
		// 判断登陆是否成功
		if (code == GotyeStatusCode.CODE_OK) {
			saveUser(loginUsername, loginPassword);
			Intent i = new Intent(LoginActivity.this, NewMainTabActivity.class);
			startActivity(i);
			Intent toService = new Intent(this, GotyeService.class);
			startService(toService);
			Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
			this.finish();
		} else {
			// 失败,可根据code定位失败原因
			Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLogout(int code) {
		// TODO Auto-generated method stub

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

}
