package com.project.login_view;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.project.iwant.R;
import com.project.login_controller.LoginController;
import com.project.login_model.UserInfoBean;
import com.project.login_model.UserInfoListBean;
import com.project.myself_view.MyselfInfoActivity;
import com.utils.app.StringUtils;
import com.utils.app.UIHelper;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;

public class ModifyUserInfoActivity extends BaseActivity implements
		OnClickListener {
	private LinearLayout btn_return_exit;
	private Button btn_publish_or_add;
	private EditText editText_nickname, editText_ido;
	private UserInfoListBean userInfoListBean;
	private Configuration configuration;
	private boolean isModify;
	private int get=0;

	public ModifyUserInfoActivity() {
		super("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modifyuserinfo);
		initConfiguration(ModifyUserInfoActivity.this, "ModifyUserInfoActivity");
		initView();
		setListener();
		getData();
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		get=0;
		userInfoListBean = new UserInfoListBean();
		DefineDisplayView.showLoadingDialog(ModifyUserInfoActivity.this);
		ArrayList<UserInfoBean> userInfoBeans = new ArrayList<UserInfoBean>();
		UserInfoBean userInfoBean = new UserInfoBean();
		userInfoBean.setUsername(SharePreferenceUtils.getIntance(
				ModifyUserInfoActivity.this).getCurrentUserName());
		userInfoBeans.add(userInfoBean);
		userInfoListBean.setArray(userInfoBeans);
		configuration.setClassName(UserInfoListBean.class);
		configuration.setViewDataObject(userInfoListBean);
		new LoginController(configuration).getUserInfo_New();
	}

	@Override
	public void initView() {
		btn_return_exit = (LinearLayout) findViewById(R.id.setup_return_btns);
		btn_publish_or_add = (Button) findViewById(R.id.modify_ok);
		editText_nickname=(EditText)findViewById(R.id.nickname);
		editText_ido=(EditText)findViewById(R.id.idiotext);
	}

	@Override
	public void setListener() {
		btn_return_exit.setOnClickListener(this);
		btn_publish_or_add.setOnClickListener(this);
	}

	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration = getConfiguration();
	}

	@Override
	public void onClick(View arg0) {   
		switch (arg0.getId()) {
		case R.id.modify_ok:
			if (StringUtils.isEmpty(editText_nickname.getText().toString())
					|| StringUtils.isEmpty(editText_ido.getText().toString())) {

				UIHelper.ToastMessage(ModifyUserInfoActivity.this, "数据不能空");
			} else {
				userInfoListBean = new UserInfoListBean();
				DefineDisplayView.showLoadingDialog(ModifyUserInfoActivity.this);
				ArrayList<UserInfoBean> userInfoBeans = new ArrayList<UserInfoBean>();
				UserInfoBean userInfoBean = new UserInfoBean();
				userInfoBean.setUsername(SharePreferenceUtils.getIntance(
						ModifyUserInfoActivity.this).getCurrentUserName());
				userInfoBean.setNickname(editText_nickname.getText().toString());
				userInfoBean.setIdiograph(editText_ido.getText().toString());
				userInfoBeans.add(userInfoBean);
				userInfoListBean.setArray(userInfoBeans);
				configuration.setClassName(UserInfoListBean.class);
				configuration.setViewDataObject(userInfoListBean);
				new LoginController(configuration).modifyUserInfo();
			           isModify=true;
				
			}

			break;

		case R.id.setup_return_btns:
			finish();
			break;

		}
	}

	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
			
	
	
      if (isModify) {
    	 MyselfInfoActivity.getNickName(SharePreferenceUtils.getIntance(ModifyUserInfoActivity.this).getCurrentUserName());
    	 SharePreferenceUtils.getIntance(ModifyUserInfoActivity.this).saveCurrentNickName(editText_nickname.getText().toString());
    	 SharePreferenceUtils.getIntance(ModifyUserInfoActivity.this).saveCurrentIdio(editText_ido.getText().toString());
		finish();
		isModify=false;
	}else {
		userInfoListBean = (UserInfoListBean) object;
		editText_nickname.setText(userInfoListBean.getArray().get(0)
				.getNickname());
		editText_ido.setText(userInfoListBean.getArray().get(0).getIdiograph());
		 SharePreferenceUtils.getIntance(ModifyUserInfoActivity.this).saveCurrentIdio(userInfoListBean.getArray().get(0).getIdiograph());
	}
	}
}
