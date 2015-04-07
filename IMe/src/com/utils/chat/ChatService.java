package com.utils.chat;

import java.util.Calendar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.lidroid.xutils.exception.DbException;
import com.project.iwant.MainActivity;
import com.project.login_view.LoginActivity;
import com.project.message_model.MessageBean;
import com.project.message_view.MessageActivity;
import com.project.myself_view.MyselfFragment;
import com.utils.app.NetworkConnected;
import com.utils.app.UIHelper;
import com.utils.db.SQLDatebaseUtils;
import com.utils.http.LocationUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.Noti;

/**
 * @description 聊天后台服务
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-13上午8:52:10
 */
public class ChatService extends Service {
	static int msgInt;// 刷新界面message
	private static TcpHelper clientTcpHelper;
	private static String CHAR_SERVER_IP = "121.40.151.32";
	private static int CHAR_SERVER_PORT = 8989;
	private boolean isConnectTcp = false;
	private boolean isLogoutTCP = false;
	/** 连接 */
	private int connectTcpAgain = 0;// 重新连接Tcp 服务器次数
	private int connectTcpAgainSum = 3;// 重新连接Tcp 服务器总数
	private byte[] bufferData = new byte[4096];// 数据缓冲区
	private int bufferLen = 0;// 缓冲区总长度
	private static String Username = "";
	private static Context context;

	// 刷新聊天页面或者是主页面

	public static Handler mAllControl = new Handler() {
		Message msg1;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE:
				msg1 = new Message();
				msg1.what = ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE;
				MessageActivity.mControl.sendMessage(msg1);
				/*MainActivity.mMainControl.sendMessage(msg1);
				MyselfFragment.mMyselfControl.sendMessage(msg1);*/
				break;
			default:
				break;
			}

		}

	};

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = ChatService.this;
		Username=SharePreferenceUtils.getIntance(context).getCurrentUserName();
		if (!SharePreferenceUtils.getIntance(ChatService.this)
				.getCurrentUserName().equals("default")) {
			/** 启动定位 */
			LocationUtils.getInstance(ChatService.this)
					.getLocationUseBaiDuSDK();
			/** 启动聊天 */
			/**  先检测网络*/
			if (NetworkConnected.isNetworkConnected(context)) {
				connectChatTcp();
			}else {
				UIHelper.ToastMessage(context, "网络异常，请检查网络");
			}
			

		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * @function 登录
	 * @time 2014-10-7下午2:34:43 void
	 */
	public void loginChatTcp() {
		new Thread() {
			public void run() {
				byte[] loginData = BaleOrSubpackage.baleLogin(Username);// 当前用户的用户名，这里暂且用test作为测试
				boolean re = clientTcpHelper.send(loginData);
				// 这里需要注意一点，只有client。close（）执行了之后才会出现发送失败，也就是哪怕链接中断了，这里也会显示发送成功，暂时没有解决办法，
				// 所以只能是通过在消息接受线程以及心跳包维持链接的畅通。正常情况下是没什么问题的。
				if (!re) {
					// 如果链接畅通，这一步都很少会出错的，如果这里发送失败了，则再链接一次链接试试看
					connectAgainHandler.postDelayed(connectAgainThread, 6000);// 延迟六秒时候重新链接服务器
				} else {
					// 发送成功，更新心跳包发送的时间
					SharePreferenceUtils.setTCPPingTime(context);
				}

			}

		}.start();

	}

	/**
	 * @function 开始接收数据
	 * @time 2014-10-7下午2:34:46 void
	 */
	public void startChatTcp() {
		Log.v("iwantservice", "接收数据线程开启");
		new Thread() {
			public void run() {

				while (true) {
					byte[] readData = new byte[1024];
					int readSize = clientTcpHelper.read(readData);
					if (-1 == readSize) {
						Log.v("tcp", "" + "链接已断开，正在重新链接！");
						// 链接断开了之后再从新链接,但是要注意链接的次数，提醒了一次再到一次，否则会无线重新链接
						if (isConnectTcp) {
							connectTcpAgain = 0;
							connectAgainHandler.postDelayed(connectAgainThread,
									6000);// 延迟六秒时候重新链接服务器
							isConnectTcp = false;
							break;
						}
						return;
					} else {
						// 将数据写入缓冲区内
						System.arraycopy(readData, 0, bufferData, bufferLen,
								readSize);
						bufferLen += readSize;
						while (true) {
							if (bufferLen >= 8) {
								// 这里需要循环判定解包过程
								byte[] lenByte = new byte[4];
								byte[] typeByte = new byte[4];
								System.arraycopy(bufferData, 0, lenByte, 0, 4);
								System.arraycopy(bufferData, 4, typeByte, 0, 4);
								int len = ConvHelper.bytesToInt(lenByte);
								int type = ConvHelper.bytesToInt(typeByte);
								if (bufferLen >= len) {
									// 够一个数组，开始解包
									// 先获取包
									byte[] data = new byte[len - 8];
									System.arraycopy(bufferData, 8, data, 0,
											len - 8);
									// 刷新缓存区
									System.arraycopy(bufferData, len,
											bufferData, 0, bufferLen - len);
									// 刷新缓冲区长度
									bufferLen -= len;
									Log.v("data", data.length + "");
									/*
									 * 解包
									 */
									BaleOrSubpackage.subpackage(type, data,
											context,new IMessageCallBack() {
												
												@Override
												public void sendHandlerMessage(int msg) {
													Message msg1;
													switch (msg) {
													case ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE:
														if (ChatServiceUtils.isMessageActivityPage) {
															msg1 = new Message();
															msg1.what = ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE;
															MessageActivity.mControl.sendMessage(msg1);
														}
														if (ChatServiceUtils.isMainActivityPage) {
															msg1 = new Message();
															msg1.what = ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE;
															MainActivity.mMainControl.sendMessage(msg1);
															msg1 = new Message();
															msg1.what = ChatServiceUtils.APPLY_UPDATE_MSG;
															MyselfFragment.mMyselfControl.sendMessage(msg1);
															
														}else {
															Noti.openNoti(context, "我", "您有" + SharePreferenceUtils.getIntance(context).getUnLookMessageFlag()
																	+ "条未读消息");
														}
														break;
													case ChatServiceUtils.APPLY_UPDATE_MSG:
														if (ChatServiceUtils.isMainActivityPage) {
														msg1 = new Message();
														msg1.what = ChatServiceUtils.APPLY_UPDATE_MSG;
														MyselfFragment.mMyselfControl.sendMessage(msg1);
														}
														break;
													case ChatServiceUtils.APPLY_NIT_MSG:
														// 利用广播进行发动消息
														Noti.openNoti(context, "我", "您有" + SharePreferenceUtils.getIntance(context).getUnLookMessageFlag()
																+ "条未读消息");
														break;
													}
													
												}
											});
								} else {
									break;
								}
							} else {
								break;
							}
						}
					}
				}
			}

		}.start();

	}

	/**
	 * @function 断掉接收下线包
	 * @time 2014-10-7下午2:34:48 void
	 */
	public void loginoutChatTcp() {
		new Thread() {
			public void run() {

				byte[] logoutData = BaleOrSubpackage.baleLogout();
				boolean re = clientTcpHelper.send(logoutData);
				if (!re) {
					Log.v("tcp", "" + "send error");
				}
				clientTcpHelper.close();
				isLogoutTCP = true;
			}

		}.start();

	}

	/**
	 * @function 连接
	 * @time 2014-10-7下午2:34:52 void
	 */
	public void connectChatTcp() {
		Log.v("iwantservice", "tcp启动");
		new Thread() {
			public void run() {
				clientTcpHelper = new TcpHelper();
				boolean isconnect = clientTcpHelper.connect(CHAR_SERVER_IP,
						CHAR_SERVER_PORT);
				if (isconnect) {
					Log.v("tcp", "连接成功");
					isConnectTcp = true;
				} else {
					Log.v("tcp", "" + "connect error");
					connectTcpAgain++;
					if (connectTcpAgain <= connectTcpAgainSum) {
						connectAgainHandler.postDelayed(connectAgainThread,
								6000);// 延迟六秒时候重新链接服务器
					} else {
						isConnectTcp = false;
					}

				}
				if (isConnectTcp) {
					// 链接成功
         		    startChatTcp();
					loginChatTcp();
				} else {
					// 连接不上则通知发送消息告知用户
				}
				isConnectTcp = true;
			}

		}.start();

	}

	/**
	 * @function ping 服务器
	 * @time 2014-10-7下午2:34:54 void
	 */
	public void pingChatTcp() {
		Log.v("iwantservice", "心跳包");
		new Thread() {
			public void run() {
				byte[] pingData = BaleOrSubpackage.balePing();
				boolean re = clientTcpHelper.send(pingData);
				if (!re) {
					Log.v("tcp", "" + "send error");
				}
			}

		}.start();

	}

	/**
	 * @function 发送聊天信息
	 * @time 2014-10-7下午2:55:14
	 * @param msg
	 * @param isSaveLocalDb
	 *            是否保存在本地数据库 void
	 */
	public static void sendChatMessage(final MessageBean msg, final boolean isSaveLocalDb) {
		if (isSaveLocalDb) {
			try {
				SQLDatebaseUtils.getInstance(context).getDbUtils().save(msg);
			} catch (DbException e) {
				e.printStackTrace();
			}
		}

		new Thread() {
			@Override
			public void run() {
				byte[] messageData = BaleOrSubpackage.baleMessage(
						msg.getUser(), msg.getToUser(), msg.getContent());
				boolean re = clientTcpHelper.send(messageData);
				Log.v("sendmsg", "here");
				if (!re) {
					Log.v("sendmsg", "失败");
					// 发送数据失败
				}
				
				if (isSaveLocalDb) {
					Message message=new Message();
					message.what=ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE;
					MessageActivity.mControl.sendMessage(message);
				}
				
			//  在这里刷新界面如何？
				
			}
		}.start();

	}

	/**
	 * @function 周期ping 服务器
	 * @time 2014-10-7下午3:23:36 void
	 */
	public void pingTcpForAgain() {
		handlerlisten.post(updateThread);
	}

	/**
	 * 定时发送再次连接服务器
	 */
	Handler connectAgainHandler = new Handler();
	final Runnable connectAgainThread = new Runnable() {
		// 重新链接聊天服务器专用
		public void run() {
			connectChatTcp();
		}
	};

	Handler handlerlisten = new Handler();
	final Runnable updateThread = new Runnable() {
		// 每500毫秒执行一次
		public void run() {
			// ==聊天===
			// 如果当前时间减去心跳包刷新时间大于心跳包的发送周期（五分钟），则发送心跳包
			if (SharePreferenceUtils.getTCPPingTime(context)) {
				if (isConnectTcp) {// 这个判定主要是避免尚未链接的时候就开始发送心跳包
					// 发送心跳包
					pingChatTcp();
					// 心跳包周期主动刷新一次主界面列表
					Message message = new Message();
					message.what = ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE;
					ChatService.mAllControl.sendMessage(message);
				}
			}
			// ==聊天===
			handlerlisten.postDelayed(updateThread, 500);
		}
	};
	
	
	public interface IMessageCallBack{
		public  void sendHandlerMessage(int publishDynamicStaticChange);
	}
}
