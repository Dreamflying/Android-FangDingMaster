package com.utils.chat;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lidroid.xutils.exception.DbException;
import com.project.message_model.CommentBean;
import com.project.message_model.MessageBean;
import com.utils.app.ActivityManagerUtils;
import com.utils.chat.ChatService.IMessageCallBack;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.Noti;

public class BaleOrSubpackage {
	/*
	 * 解包完成之后存入本地数据库之中然后通知ui更新（该操作还没写）
	 */
	public static void subpackage(int type, byte[] data, Context context,IMessageCallBack iCallBack) {
		Log.v("解包", "包类型:" + type);

		switch (type) {
		case 1:
			// 登录报，通常只有打包，不需要解包
			break;
		case 2:
			// 登录返回包，提示是否登录成功
			Pb.PbServerAcceptLogin serverPbServerAcceptLogin = unmarshalPbServerAcceptLogin(data);
			if (null == serverPbServerAcceptLogin) {
				Log.v("tcp", "" + "unmarshalPbServerAcceptLogin error");
				return;
			}
			Log.v("tcp",
					"true表示登陆成功，否则表示失败:" + serverPbServerAcceptLogin.getLogin());
			Log.v("tcp",
					"提示消息(不明白有什么用):" + serverPbServerAcceptLogin.getTipsMsg());
			Log.v("tcp", "秒为单位的时间戳:" + serverPbServerAcceptLogin.getTimestamp());
			break;
		case 3:
			// 下线包，告诉服务器我这边要下线了，通常不需要解包
			break;
		case 4:
			// 客户端心跳包,无需解包
			break;
		case 5:
			 Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
			  vib.vibrate(500);
			/*if (SharePreferenceUtils.getIntance(context).getSoundOpen()) {
				// 有消息近来，播放短信铃声
				ChatServiceUtils.SOUND_TIMES=1;
				MediaPlayer mp = new MediaPlayer();
				mp.reset();
				try {
					  AssetManager assetManager = context.getAssets();  
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
				mp.start();
			}*/
			
			// 消息包
			Pb.PbC2CTextChat serverPbC2CTextChat = unmarshalPbServerPbC2CTextChat(data);
			if (null == serverPbC2CTextChat) {
				Log.v("tcp", "" + "unmarshalPbServerAcceptLogin error");
				return;
			}
			int isType = 0;
			String msgStr = serverPbC2CTextChat.getTextMsg();
			if (isContain(msgStr, ChatServiceUtils.APPLY)) {
				// 接收到满足申请
				isType = 1;
			}
			if (isContain(msgStr, ChatServiceUtils.HAN)) {
				// 接收到申请批阅，同意或者拒绝
				isType = 2;
			}
			if (isContain(msgStr, ChatServiceUtils.COMMENT)) {
				// 接收到申请批阅，同意或者拒绝
				isType = 3;
			}
			Message msg1;
			switch (isType) {
			case 0:
				Log.e("tcp", "发送者username:" + serverPbC2CTextChat.getToUuid());
				Log.e("tcp", "接受者username:" + serverPbC2CTextChat.getFromUuid());
				Log.e("tcp", "消息内容:" + serverPbC2CTextChat.getTextMsg());
				Log.e("tcp", "秒为单位的时间戳:" + serverPbC2CTextChat.getTimestamp());
				/**
				 * 1.存储到本地数据库
				 * 2.保存显示的未读信息+1
				 * **/
				SharePreferenceUtils.getIntance(context).saveUnLookMessageFlag(SharePreferenceUtils.getIntance(context).getUnLookMessageFlag()+1);
				MessageBean messageBean=new MessageBean();
				messageBean.setTime((int) serverPbC2CTextChat.getTimestamp()+"");
				messageBean.setToUser(serverPbC2CTextChat.getFromUuid());
				messageBean.setUser(serverPbC2CTextChat.getToUuid());
				String []content_image=serverPbC2CTextChat.getTextMsg().split("@");
				if (content_image.length==1) {
					messageBean.setContent(content_image[0]);
				}
				if (content_image.length==2) {
					messageBean.setContent(content_image[0]);
					messageBean.setHeadImage(content_image[1]);
				}
				if(content_image.length==3){
					messageBean.setContent(content_image[0]);
					messageBean.setHeadImage(content_image[1]);
					messageBean.setToName(content_image[2]);
					
				}
				
				
				messageBean.setReadStatus("0");
				messageBean.setStatus("1");
				try {
					SQLDatebaseUtils.getInstance(context).getDbUtils().save(messageBean);
				} catch (DbException e) {
					e.printStackTrace();
				}
				Log.v("发送成功之后", "刷新Service 的handler");
				iCallBack.sendHandlerMessage(ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE);
				break;
			case 1:
				/**
				 * 1.保存到sharepreference
				 * 2.通知申请界面更新
				 * */
				SharePreferenceUtils.getIntance(context).setHomeUnApply(1);
				iCallBack.sendHandlerMessage(ChatServiceUtils.APPLY_UPDATE_MSG);
				break;
			case 2:
				/**
				 * 1.接收到申请评阅
				 * 2.保存到本地数据库
				 * */
				SharePreferenceUtils.getIntance(context).saveUnLookMessageFlag(SharePreferenceUtils.getIntance(context).getUnLookMessageFlag()+1);
				MessageBean appBean=new MessageBean();
				appBean.setTime((int) serverPbC2CTextChat.getTimestamp()+"");
				appBean.setToUser(serverPbC2CTextChat.getFromUuid());
				appBean.setUser(serverPbC2CTextChat.getToUuid());
				String []content=serverPbC2CTextChat.getTextMsg().replace(ChatServiceUtils.HAN, "").split("@");
				if (content.length==1) {
					appBean.setContent(content[0]);
				}
				if (content.length==2) {
					appBean.setContent(content[0]);
					appBean.setHeadImage(content[1]);
				}
				if(content.length==3){
					appBean.setContent(content[0]);
					appBean.setHeadImage(content[1]);
					appBean.setToName(content[2]);
				}
				appBean.setReadStatus("0");
				appBean.setStatus("1");
				try {
					SQLDatebaseUtils.getInstance(context).getDbUtils().save(appBean);
				} catch (DbException e) {
					e.printStackTrace();
				}
				iCallBack.sendHandlerMessage(ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE);
				break;
			case 3:
				/**
				 * 接收到评论信息
				 * 1.写入数据库
				 * 2.同样发出更新？
				 * */
				SharePreferenceUtils.getIntance(context).saveUnLookMessageFlag(SharePreferenceUtils.getIntance(context).getUnLookMessageFlag()+1);
				CommentBean commentMessageBean=new CommentBean();
				commentMessageBean.setTime((int) serverPbC2CTextChat.getTimestamp()+"");
				commentMessageBean.setToUser(serverPbC2CTextChat.getFromUuid());
				commentMessageBean.setUser(serverPbC2CTextChat.getToUuid());
				commentMessageBean.setToName("");
				commentMessageBean.setReadStatus("0");
				commentMessageBean.setStatus("1");
				commentMessageBean.setDtid(serverPbC2CTextChat.getTextMsg());
				String [] comment=serverPbC2CTextChat.getTextMsg().split("@");
				commentMessageBean.setContent(comment[0]);
				commentMessageBean.setDtid(comment[1]);
				commentMessageBean.setHeadImage(comment[2]);
				try {
					SQLDatebaseUtils.getInstance(context).getDbUtils().save(commentMessageBean);
				} catch (DbException e) {
					e.printStackTrace();
				}
				iCallBack.sendHandlerMessage(ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE);
				SharePreferenceUtils.getIntance(context).saveCommentItem(true);
				break;
			}


			/**
			 *通知栏显示 
			 * */
			if (ChatServiceUtils.isMainActivityPage) {
				
			}else {
				iCallBack.sendHandlerMessage(ChatServiceUtils.APPLY_NIT_MSG);
			}
				
			
		
			break;
		}
	}

	/*
	 * 判定是否包含字符
	 */
	public static boolean isContain(String s1, String s2) {
		return s1.contains(s2);
	}

	/*
	 * 响通知声音
	 */
	private static void playSongds(Context context) {
		MediaPlayer mp = new MediaPlayer();
		mp.reset();
		try {
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
		mp.start();
	}

	/*
	 * 打包
	 */
	public static byte[] baleLogin(String username) {
		// 登录包
		Pb.PbClientLogin.Builder clientLoginBuilder = Pb.PbClientLogin
				.newBuilder();
		clientLoginBuilder.setUuid(username);
		clientLoginBuilder.setVersion(3.14f);
		clientLoginBuilder
				.setTimestamp((int) (System.currentTimeMillis() / 1000));
		byte[] loginData = marshalPbClientLogin(clientLoginBuilder);
		return loginData;
	}

	public static byte[] balePing() {
		// 心跳包
		Pb.PbClientPing.Builder clientPingBuilder = Pb.PbClientPing
				.newBuilder();
		clientPingBuilder.setPing(true);
		clientPingBuilder
				.setTimestamp((int) (System.currentTimeMillis() / 1000));
		byte[] pingData = marshalPbClientPing(clientPingBuilder);
		return pingData;
	}

	public static byte[] baleLogout() {
		// 下线包
		Pb.PbClientLogout.Builder ClientLogoutBuilder = Pb.PbClientLogout
				.newBuilder();
		ClientLogoutBuilder.setLogout(true);
		ClientLogoutBuilder
				.setTimestamp((int) (System.currentTimeMillis() / 1000));
		byte[] logoutData = marshalPbClientLogout(ClientLogoutBuilder);
		return logoutData;
	}

	public static byte[] baleMessage(String user, String touser, String message) {
		// 消息包
		Pb.PbC2CTextChat.Builder clientLoginBuilder = Pb.PbC2CTextChat
				.newBuilder();
		clientLoginBuilder.setFromUuid(user);
		clientLoginBuilder.setToUuid(touser);
		clientLoginBuilder.setTextMsg(message);
		clientLoginBuilder
				.setTimestamp((int) (System.currentTimeMillis() / 1000));
		byte[] messageData = marshalPbC2CTextChat(clientLoginBuilder);
		return messageData;
	}

	/*
	 * 序列化包
	 */
	public static byte[] marshalPbClientLogin(Pb.PbClientLogin.Builder b) {
		AesHelper aes = new AesHelper();
		Pb.PbClientLogin info = b.build();
		byte[] encrypted = aes.encrypt(info.toByteArray());
		return new PacketHelper(PacketHelper.PK_ClientLogin, encrypted)
				.getBytes();
	}

	public static byte[] marshalPbClientPing(Pb.PbClientPing.Builder b) {
		AesHelper aes = new AesHelper();
		Pb.PbClientPing info = b.build();
		byte[] encrypted = aes.encrypt(info.toByteArray());
		return new PacketHelper(PacketHelper.PK_ClientPing, encrypted)
				.getBytes();
	}

	public static byte[] marshalPbClientLogout(Pb.PbClientLogout.Builder b) {
		AesHelper aes = new AesHelper();
		Pb.PbClientLogout info = b.build();
		byte[] encrypted = aes.encrypt(info.toByteArray());
		return new PacketHelper(PacketHelper.PK_ClientLogout, encrypted)
				.getBytes();
	}

	public static byte[] marshalPbC2CTextChat(Pb.PbC2CTextChat.Builder b) {
		AesHelper aes = new AesHelper();
		Pb.PbC2CTextChat info = b.build();
		byte[] encrypted = aes.encrypt(info.toByteArray());
		return new PacketHelper(PacketHelper.PK_C2CTextChat, encrypted)
				.getBytes();
	}

	/*
	 * 反序列化包
	 */
	public static Pb.PbC2CTextChat unmarshalPbServerPbC2CTextChat(byte[] data) {
		AesHelper aes = new AesHelper();
		byte[] decrypted = aes.decrypt(data);

		Pb.PbC2CTextChat rev = null;
		try {
			rev = Pb.PbC2CTextChat.parseFrom(decrypted);
		} catch (InvalidProtocolBufferException e) {
			return null;
		}
		return rev;
	}

	public static Pb.PbServerAcceptLogin unmarshalPbServerAcceptLogin(
			byte[] data) {
		AesHelper aes = new AesHelper();
		byte[] decrypted = aes.decrypt(data);

		Pb.PbServerAcceptLogin rev = null;
		try {
			rev = Pb.PbServerAcceptLogin.parseFrom(decrypted);
		} catch (InvalidProtocolBufferException e) {
			return null;
		}
		return rev;
	}
}
