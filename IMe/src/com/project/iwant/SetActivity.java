package com.project.iwant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import cn.waps.AppConnect;

import com.base.view.BaseActivity;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeUser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.login_view.LoginActivity;
import com.project.login_view.ModifyActivity;
import com.utils.app.ActivityManagerUtils;
import com.utils.app.UIHelper;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;

/**
 * @description 设置界面
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-2下午3:34:15
 */
public class SetActivity extends BaseActivity implements OnClickListener {
	private LinearLayout setup_return_btns;
	private RelativeLayout setup_message_rl;
	private boolean isMessage = true;
	private Button setup_bind_btn, setup_logout_btn, setup_huancun_btn,
			setup_about_me_btn, setup_share_btn, setup_message_btn,
			setup_update_btn, setup_quit_btn, setup_modifypwd_btn;
	private ToggleButton soundSet;
	private ImageView setup_message_choose_img, setup_sound_choose_img;
	private GotyeUser user;
	private GotyeAPI api;
	public SetActivity() {
		super("设置");

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		api = GotyeAPI.getInstance();
		api.addListerer(this);
		user = api.getCurrentLoginUser();
		api.requestUserInfo(user.name, true);
		initView();
		setListener();
		initConfiguration(SetActivity.this, "SetActivity");

	}

	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
	}

	@Override
	public void initView() {
		setup_message_rl = (RelativeLayout) this
				.findViewById(R.id.setup_message_rl);
		setup_return_btns = (LinearLayout) this
				.findViewById(R.id.setup_return_btns);
		setup_bind_btn = (Button) this.findViewById(R.id.setup_bind_btn);
		setup_logout_btn = (Button) this.findViewById(R.id.setup_logout_btn);
		setup_huancun_btn = (Button) this.findViewById(R.id.setup_huancun_btn);
		setup_about_me_btn = (Button) this
				.findViewById(R.id.setup_about_me_btn);
		setup_share_btn = (Button) this.findViewById(R.id.setup_share_btn);
		setup_message_btn = (Button) this.findViewById(R.id.setup_message_btn);
		setup_update_btn = (Button) this.findViewById(R.id.setup_update_btn);
		setup_quit_btn = (Button) this.findViewById(R.id.setup_quit_btn);
		setup_modifypwd_btn = (Button) this
				.findViewById(R.id.setup_modifypwd_btn);
		setup_message_choose_img = (ImageView) this
				.findViewById(R.id.setup_message_choose_img);
		setup_sound_choose_img = (ImageView) this
				.findViewById(R.id.setup_sound_choose_img);
		soundSet = (ToggleButton) this.findViewById(R.id.setup_message_toggle);
	}

	@Override
	public void setListener() {
		setup_bind_btn.setOnClickListener(this);
		setup_logout_btn.setOnClickListener(this);
		setup_huancun_btn.setOnClickListener(this);
		setup_about_me_btn.setOnClickListener(this);
		setup_share_btn.setOnClickListener(this);
		setup_update_btn.setOnClickListener(this);
		setup_quit_btn.setOnClickListener(this);
		setup_return_btns.setOnClickListener(this);
		setup_message_rl.setOnClickListener(this);
		setup_modifypwd_btn.setOnClickListener(this);

		if (SharePreferenceUtils.getIntance(SetActivity.this).getSoundOpen()) {

			setup_message_choose_img
					.setBackgroundResource(R.drawable.choose_checked);

		} else {

			setup_message_choose_img.setBackgroundResource(R.drawable.touming);
		}
		soundSet.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					setup_message_choose_img
							.setBackgroundResource(R.drawable.choose_checked);
					SharePreferenceUtils.getIntance(SetActivity.this).saveSoundOpen(true);
					UIHelper.ToastMessage(SetActivity.this, "开启消息推送");
					api.setNewMsgNotify(arg1);
				} else {
					api.setNewMsgNotify(arg1);
					setup_message_choose_img
							.setBackgroundResource(R.drawable.touming);
					SharePreferenceUtils.getIntance(SetActivity.this).saveSoundOpen(false);
					UIHelper.ToastMessage(SetActivity.this, "关闭消息推送");
				}

			}
		});
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.setup_bind_btn:

			break;
		case R.id.setup_logout_btn:
			SharePreferenceUtils.getIntance(SetActivity.this)
					.deleteCurrentUserName();
			ActivityManagerUtils.getIntance().finishAllActivity();
			api.logout();
			SharePreferenceUtils.getIntance(SetActivity.this).saveLookMessage(false);
		    SQLDatebaseUtils.getInstance(SetActivity.this).deleteAllTable();
			Intent intent = new Intent(SetActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.setup_huancun_btn:
			ImageLoader.getInstance().clearMemoryCache();
			ImageLoader.getInstance().clearDiscCache();
			UIHelper.ToastMessage(SetActivity.this, "清除缓存成功");
			api.clearCache();
			break;
		case R.id.setup_about_me_btn:
			Intent abIntent = new Intent(SetActivity.this,
					AboutUsActivity.class);
			startActivity(abIntent);
			break;
		case R.id.setup_share_btn:
			Intent shaIntent = new Intent(Intent.ACTION_SEND);
			// 分享的数据类型
			shaIntent.setType("text/plain");
			// 分享的主题
			shaIntent.putExtra(Intent.EXTRA_SUBJECT, "好友分享");
			// 分享的内容
			shaIntent.putExtra(Intent.EXTRA_TEXT,
					"我下载了一个好牛的软件，好好玩！ http://www.iwantme.com");
			// 允许启动新的Activity
			shaIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// 目标应用寻找对话框的标题
			startActivity(Intent.createChooser(shaIntent, getTitle()));
			break;
		case R.id.setup_update_btn:
			AppConnect.getInstance(SetActivity.this).checkUpdate(SetActivity.this);
			break;
		case R.id.setup_quit_btn:
			ActivityManagerUtils.getIntance().finishAllActivity();
			break;
		case R.id.setup_return_btns:
			finish();
			break;

		case R.id.setup_modifypwd_btn:
			Intent moIntent = new Intent(SetActivity.this, ModifyActivity.class);
			startActivity(moIntent);
			break;

		default:
			break;
		}

	}
	@Override
	public void onDestroy() {
		api.removeListener(this);
		super.onDestroy();
	}
}
