/*package com.project.message_view;

import java.util.List;
import java.util.UUID;

import android.os.Bundle;

import com.base.view.BaseActivity;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeUser;
import com.gotye.api.WhineMode;
import com.open_demo.util.TimeUtil;
import com.utils.app.UIHelper;

*//**
 * 聊天界面
 * 
 * @author liyuan
 * 
 *//*
public class NewMessageActivity extends BaseActivity implements
		GotyeChatListener, GotyeLoginListener {

	public NewMessageActivity() {
		super("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
		setListener();
		initChat();
	}

	private void initChat() {
		GotyeAPI api = Gotye.getInstance().makeGotyeAPIForUser("15527907118");
		GotyeUser gotyeUser = new GotyeUser("123456789");
		GotyeTextMessage message = new GotyeTextMessage(UUID.randomUUID()
				.toString(), TimeUtil.getCurrentTime(), gotyeUser, gotyeUser);
		message.setText("发送的内容哦");
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		super.setListener();
	}

	@Override
	public void onGetHistoryMessages(String arg0, String arg1,
			GotyeTargetable arg2, String arg3, List<GotyeMessage> arg4,
			boolean arg5, int arg6) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetOfflineMessage(String arg0, String arg1,
			List<GotyeMessage> arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceiveMessage(String arg0, String arg1, GotyeMessage arg2) {
		// 接受到消息回调
		UIHelper.ToastMessage(NewMessageActivity.this, "收到消息：" + arg1
				+ ((GotyeTextMessage) arg2).getText()
				+ arg2.getSender().getUsername());
	}

	@Override
	public void onReceiveVoiceMessage(String arg0, String arg1,
			GotyeTargetable arg2, GotyeTargetable arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSendMessage(String arg0, String arg1, GotyeMessage arg2,
			int arg3) {
		// 发送成功回调,刷新数据库
		UIHelper.ToastMessage(NewMessageActivity.this, "发送成功");

	}

	@Override
	public void onStartTalkTo(String arg0, String arg1, GotyeTargetable arg2,
			WhineMode arg3, boolean arg4) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTalkTo(String arg0, String arg1, GotyeTargetable arg2,
			WhineMode arg3, boolean arg4, GotyeVoiceMessage arg5, long arg6,
			int arg7) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVoiceMessageEnd(String arg0, String arg1,
			GotyeTargetable arg2, GotyeTargetable arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLogin(String arg0, String arg1, int arg2) {
		UIHelper.ToastMessage(NewMessageActivity.this, "登陆成功");

	}

	@Override
	public void onLogout(String arg0, String arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
*/