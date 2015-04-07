package com.project.login_view;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.base.common.Common;
import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.iwant.MainActivity;
import com.project.iwant.R;
import com.project.login_controller.LoginController;
import com.project.login_model.UserInfoBean;
import com.project.login_model.UserInfoListBean;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_model.ImageBean;
import com.project.myself_model.ImageListBean;
import com.project.myself_view.IPhotoChoice;
import com.project.myself_view.MyselfInfoActivity;
import com.project.myself_view.PhotoPopupWindow;
import com.utils.app.PhotoUtils;
import com.utils.app.StringUtils;
import com.utils.app.UIHelper;
import com.utils.db.SQLDatebaseUtils;
import com.utils.http.FtpLoadTool;
import com.utils.http.LocationUtils;
import com.utils.http.UploadInterface;
import com.utils.io.SharePreferenceUtils;
import com.utils.io.StorageEnvironmentUtils;
import com.utils.os.ImgAction;
import com.utils.os.InitDisplayImageOptions;
import com.utils.security.MD5;
import com.utils.widght.DefineDisplayView;

/**
 *  @description 注册界面
 *  @author Ly
 *  @email 1522651962@qq.com
 *  @time 2014-10-4上午11:31:54
 */
public class RegisterActivity extends BaseActivity {

	public RegisterActivity() {
		super("");
	}

	private EditText phoneEdit;
	private EditText passwordOneEdit;
	private EditText nameEdit,idorEditText;
	private EditText passwordTwoEdit;
    private ImageView headImageView;
	private Button registerBtn;

	private String registerUsername;
	private String registerName;
	private String registerPassword;
	private String registerIdo;
	private String headImageNameString = "";
	// ===getdata==========
	private ProgressDialog dialog;
	private Handler mHandler;
	String purview = "0";// 注册类型0表示普通会员
	Intent intent = null;
	private static String openId = null, nickname = null, QZone = "false", SinaWeibo = "false", sex = "1";
	private RadioGroup register_sexchoose_rl;
	private RadioButton register_nan_btn, register_nv_btn;
	private UserInfoListBean userInfoListBean=new UserInfoListBean();
	private Configuration configuration;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static String FilePath = StorageEnvironmentUtils.getSDCardPath()
			+ "/iwantme/img/headimg/";
	@Override
	protected void onDestroy() {
		super.onDestroy();
		registerUsername = null;
		registerName = null;
		registerPassword = null;
		purview = null;
		intent = null;
		dialog = null;
		mHandler = null;
		openId = null;
		nickname = null;
		QZone = null;
		SinaWeibo = null;
		sex = null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		LocationUtils.getInstance(RegisterActivity.this).getLocationUseBaiDuSDK();
		SQLDatebaseUtils.getInstance(RegisterActivity.this).createDb();
		initView();
		setListener();
		initConfiguration(RegisterActivity.this,"RegisterActivity");
	}

	

	@Override
	public void initConfiguration(BaseActivity viewActivity,String activityName) {
		super.initConfiguration(viewActivity,activityName);
		 configuration = getConfiguration();
		 configuration.setClassName(UserInfoListBean.class);
	}
	
	@Override
	public void initView() {
		register_nan_btn = (RadioButton) findViewById(R.id.register_nan_btn);
		register_nv_btn = (RadioButton) findViewById(R.id.register_nv_btn);
		phoneEdit = (EditText) findViewById(R.id.register_phone_edit);
		nameEdit = (EditText) findViewById(R.id.register_name_edit);
		passwordOneEdit = (EditText) findViewById(R.id.register_password_one_btn);
		passwordTwoEdit = (EditText) findViewById(R.id.register_password_two_btn);
		registerBtn = (Button) findViewById(R.id.register_success_btn);
		register_sexchoose_rl = (RadioGroup) findViewById(R.id.register_sexchoose_rl);
		headImageView=(ImageView)findViewById(R.id.edit_headimage);
		idorEditText=(EditText)findViewById(R.id.register_name_ido);
	}
	
	@Override
	public void setListener() {
		
		registerBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				registerUsername = phoneEdit.getText().toString();
				registerName = nameEdit.getText().toString();
				registerPassword = passwordOneEdit.getText().toString();
				registerIdo=idorEditText.getText().toString();
				String one = passwordOneEdit.getText().toString();
				String two = passwordTwoEdit.getText().toString();
				if (registerUsername.length()<11) {
					UIHelper.ToastMessage(RegisterActivity.this, "用户名不能少于11位");
				}else {
					
				if (StringUtils.isEmpty(registerName)) {
					UIHelper.ToastMessage(RegisterActivity.this, "昵称不能为空");
					return;
				}
				if (StringUtils.isEmpty(registerIdo)) {
					UIHelper.ToastMessage(RegisterActivity.this, "签名不能为空");
					return;
				}
				
				if (StringUtils.isEmpty(one)&&(one.length()>=6)) {
					UIHelper.ToastMessage(RegisterActivity.this, "密码不能为空");
					return;
				}
				if (StringUtils.isEmpty(two)) {
					UIHelper.ToastMessage(RegisterActivity.this, "确认密码不能为空");
					return;
				}
				if (one.equals(two)) {
					// 正在注册
						DefineDisplayView.showLoadingDialog(RegisterActivity.this);
						ArrayList<UserInfoBean> userInfoBeans=new ArrayList<UserInfoBean>();
						UserInfoBean userInfoBean=new UserInfoBean();
						userInfoBean.setUsername(registerUsername);
						userInfoBean.setPassword(registerPassword);
						userInfoBean.setNickname(registerName);
						userInfoBean.setIdiograph(registerIdo);
						userInfoBean.setSex(Integer.parseInt(sex));
						userInfoBeans.add(userInfoBean);
						userInfoListBean.setArray(userInfoBeans);
						configuration.setViewDataObject(userInfoListBean);
						new LoginController(configuration).registerUserInfo();
					
					
				} else {
					UIHelper.ToastMessage(RegisterActivity.this, "两次密码输入不一致！");
					return;
				}
			}
			}
		});
		register_nan_btn.setBackgroundResource(R.drawable.register_nan_ture);
		register_nan_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				register_nan_btn.setBackgroundResource(R.drawable.register_nan_ture);
				register_nv_btn.setBackgroundResource(R.drawable.register_nv_false);
				sex="1";
			}
		});
		register_nv_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				register_nv_btn.setBackgroundResource(R.drawable.register_nv_true);
				register_nan_btn.setBackgroundResource(R.drawable.register_nan_false);
				sex="0";
			}
		});
		
		
	}
	

	@Override
	public void requestDataIsNull(String objectString) {
		super.requestDataIsNull(objectString);
	}
	
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		
			UIHelper.ToastMessage(RegisterActivity.this, "注册成功");
			SharePreferenceUtils.getIntance(RegisterActivity.this).saveCurrentUserName(registerUsername);
			SharePreferenceUtils.getIntance(RegisterActivity.this).saveCurrentNickName(registerName);
			SharePreferenceUtils.getIntance(RegisterActivity.this).saveCurrentIdio(registerIdo);
			LocationUtils.getInstance(RegisterActivity.this).getLocationUseBaiDuSDK();
			Intent intent =new Intent(RegisterActivity.this,UpLoadHeadActivity.class);
			startActivity(intent);
			finish();
		
	}
	@Override
	public void requestServerError(String serverError) {
		super.requestServerError(serverError);
	}
	@Override
	public void requestTimeout(String timeoutString) {
		super.requestTimeout(timeoutString);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
			startActivity(intent);
			RegisterActivity.this.finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	
}
