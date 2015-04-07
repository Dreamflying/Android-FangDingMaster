/*package com.utils.chat;

import java.util.List;
import java.util.UUID;

import android.util.Log;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeUser;
import com.gotye.api.WhineMode;
import com.open_demo.util.TimeUtil;


*//** 最新聊天util，接入亲加聊天API
 * @author liyuan
 *
 *//*
public class NewChatUtils {

	private static GotyeAPI api;
	
	
	*//** 获取api 单列
	 * @return
	 *//*
	public static GotyeAPI getGotyeAPI(String username){
	    api = Gotye.getInstance().makeGotyeAPIForUser(username);
	    if (api!=null) {
			//api.addChatListener(new ResultListener());
		}
		return api;
	}
	
	
	*//**
	 *  发送消息
	 *//*
	public static void sendMessage(String content,String toUser){
		GotyeUser gotyeUser=new GotyeUser(toUser);
		gotyeUser.setUserIcon("");
		gotyeUser.setNickName("");
		GotyeTextMessage message = new GotyeTextMessage(UUID.randomUUID().toString(), TimeUtil.getCurrentTime(), new GotyeUser(toUser), gotyeUser);
		message.setText(content);
		if (api!=null) {
			//api.sendMessageToTarget(message);
		}
	
	}
	
	public static class ResultListener implements GotyeChatListener{

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
			// 接受到消息进行处理 参数说明：1.appkey 2.usernae 3.gotmessage 
			Log.d("username", arg1);
			GotyeTextMessage message=(GotyeTextMessage) arg2;
			message.getText();
			message.getSender().getUserIcon();
			Log.d("content", message.getText());
			Log.d("toUser", message.getSender().getUsername());
		}

		@Override
		public void onReceiveVoiceMessage(String arg0, String arg1,
				GotyeTargetable arg2, GotyeTargetable arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSendMessage(String arg0, String arg1, GotyeMessage arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStartTalkTo(String arg0, String arg1,
				GotyeTargetable arg2, WhineMode arg3, boolean arg4) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTalkTo(String arg0, String arg1,
				GotyeTargetable arg2, WhineMode arg3, boolean arg4,
				GotyeVoiceMessage arg5, long arg6, int arg7) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onVoiceMessageEnd(String arg0, String arg1,
				GotyeTargetable arg2, GotyeTargetable arg3) {
			// TODO Auto-generated method stub
			
		}}
	
}
*/