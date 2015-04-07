package com.project.login_view;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.project.iwant.R;
import com.project.login_controller.LoginController;
import com.project.login_model.ModifyPsdBean;
import com.project.login_model.ModifyPsdListBean;
import com.utils.app.StringUtils;
import com.utils.app.UIHelper;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;

/**
 * @description 修改密码
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-5下午2:05:05
 */
public class ModifyActivity extends BaseActivity implements OnClickListener {
	private EditText modifypwd_oldpwd_edit, modifypwd_password_one_btn,
			modifypwd_password_two_btn;
	private Button modifypwd_success_btn, modifypwd_success_btn1;
	private Configuration configuration;

	public ModifyActivity() {
		super("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modifypwd);
		initConfiguration(ModifyActivity.this,"ModifyActivity");
		initView();
		setListener();
	}
  @Override
public void initConfiguration(BaseActivity viewActivity, String activityName) {
	super.initConfiguration(viewActivity, activityName);
	configuration=getConfiguration();
}
	
	@Override
	public void initView() {
		modifypwd_oldpwd_edit = (EditText) this
				.findViewById(R.id.modifypwd_oldpwd_edit);
		modifypwd_password_one_btn = (EditText) this
				.findViewById(R.id.modifypwd_password_one_btn);
		modifypwd_password_two_btn = (EditText) this
				.findViewById(R.id.modifypwd_password_two_btn);

		modifypwd_success_btn = (Button) this
				.findViewById(R.id.modifypwd_success_btn);
		modifypwd_success_btn1 = (Button) this
				.findViewById(R.id.modifypwd_success_btn1);
	}

	@Override
	public void setListener() {
		modifypwd_success_btn.setOnClickListener(this);
		modifypwd_success_btn1.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.modifypwd_success_btn:
			String old=modifypwd_oldpwd_edit.getText().toString();
			String old_1=modifypwd_password_one_btn.getText().toString();
			String old_2=modifypwd_password_two_btn.getText().toString();
			if (StringUtils.isEmpty(old)||StringUtils.isEmpty(old_1)||StringUtils.isEmpty(old_2)) {
				
				UIHelper.ToastMessage(ModifyActivity.this, "数据不能为空");
				return ;
			}
			if (!old_1.equals(old_2)) {
				
				UIHelper.ToastMessage(ModifyActivity.this, "新密码不一致");
				return;
			}
			
			DefineDisplayView.showLoadingDialog(ModifyActivity.this);
			ArrayList<ModifyPsdBean> modifyPsdBeans=new ArrayList<ModifyPsdBean>();
			ModifyPsdListBean modifyPsdListBean=new ModifyPsdListBean();
			ModifyPsdBean modifyPsdBean=new ModifyPsdBean();
			modifyPsdBean.setOldpassword(old);
			modifyPsdBean.setNewpassword(old_2);
			modifyPsdBean.setUsername(SharePreferenceUtils.getIntance(ModifyActivity.this).getCurrentUserName());
			modifyPsdBeans.add(modifyPsdBean);
			modifyPsdListBean.setArray(modifyPsdBeans);
			configuration.setClassName(ModifyPsdListBean.class);
			configuration.setViewDataObject(modifyPsdListBean);
			new LoginController(configuration).modifyPassWord();
			
			break;
		case R.id.modifypwd_success_btn1:
            finish();
			break;
		default:
			break;
		}

	}
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		finish();
	}
}
