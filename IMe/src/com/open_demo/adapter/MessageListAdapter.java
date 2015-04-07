package com.open_demo.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeChatTargetType;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeMessageType;
import com.gotye.api.GotyeRoom;
import com.gotye.api.GotyeUser;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open_demo.activity.ChatPage;
import com.open_demo.main.MessageFragment;
import com.open_demo.util.ImageCache;
import com.open_demo.util.TimeUtil;
import com.project.iwant.R;
import com.project.iwant_or_ihave_model.FriendInfoBean;
import com.utils.app.StringUtils;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.os.InitDisplayImageOptions;

public class MessageListAdapter extends BaseAdapter {
	private MessageFragment messageFragment;
	private List<GotyeChatTarget> sessions;
	private GotyeAPI api;
	String []message;

	public MessageListAdapter(MessageFragment messageFragment,
			List<GotyeChatTarget> sessions) {
		Log.v("adapter", "is ok");
		this.messageFragment = messageFragment;
		this.sessions = sessions;
		api = GotyeAPI.getInstance();
	}

	static class ViewHolder {
		ImageView icon;
		TextView title, content, time, count;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sessions.size();
	}

	@Override
	public GotyeChatTarget getItem(int arg0) {
		// TODO Auto-generated method stub
		return sessions.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		GotyeChatTarget t = sessions.get(position);
		if (t.name.equals(MessageFragment.fixName)) {
			return 0;
		} else {
			return 1;
		}
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (view == null) {
			view = LayoutInflater.from(messageFragment.getActivity()).inflate(
					R.layout.item_delete, null);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
			viewHolder.title = (TextView) view.findViewById(R.id.title_tx);
			viewHolder.content = (TextView) view.findViewById(R.id.content_tx);
			viewHolder.time = (TextView) view.findViewById(R.id.time_tx);
			viewHolder.count = (TextView) view.findViewById(R.id.count);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		final GotyeChatTarget session =  getItem(arg0);
		Log.d("offLine", "session" + session);
		if (getItemViewType(arg0)==0) {
			Log.v("getItemViewType", "is here");
			viewHolder.title.setText(session.name);
			viewHolder.content.setVisibility(View.GONE);
			viewHolder.icon.setImageResource(R.drawable.contact_group);
			viewHolder.time.setVisibility(View.GONE);
			int count = api.getUnreadNotifyCount();
			if (count > 0) {
				viewHolder.count.setVisibility(View.VISIBLE);
				viewHolder.count.setText(String.valueOf(count));
			} else {
				viewHolder.count.setVisibility(View.GONE);
			}

		} else {
			Log.v("getItemViewType", "is not 0");
			String title = "", content = "";
			viewHolder.content.setVisibility(View.VISIBLE);
			GotyeMessage lastMsg = api.getLastMessage(session);
			String lastMsgTime = TimeUtil
					.dateToMessageTime(lastMsg.getDate() * 1000);
			viewHolder.time.setText(lastMsgTime);
			//setIcon(viewHolder.icon, session);
		
			if (lastMsg.getType() == GotyeMessageType.GotyeMessageTypeText) {
				message=lastMsg.getText().split("@");
				Log.v("message",lastMsg.getText());
				Log.v("api", api.getCurrentLoginUser().getName());
				Log.v("session",session.name);
					Log.v("message", "is Hrree");
		        if (!lastMsg.getText().contains(SharePreferenceUtils.getIntance(messageFragment.getActivity()).getHeadImgUserName())) {

					if (message.length==3) {
						if (!StringUtils.isEmpty(message[1])) {
							FriendInfoBean friendsBean=new FriendInfoBean();
							friendsBean.setNickname(message[1]);
							friendsBean.setHeadimage(message[2]);
							friendsBean.setUsername(session.name);
							//friendsBean.setIdo(message[3]);
							try {
								SQLDatebaseUtils.getInstance(messageFragment.getActivity()).getDbUtils().save(friendsBean);
							} catch (DbException e) {
								e.printStackTrace();
							}
						}
						
					}
					if (message.length==4) {
						if (!StringUtils.isEmpty(message[0])) {
							FriendInfoBean friendsBean=new FriendInfoBean();
							friendsBean.setNickname(message[1]);
							friendsBean.setHeadimage(message[2]);
							friendsBean.setUsername(session.name);
							friendsBean.setIdo(message[3]);
							try {
								SQLDatebaseUtils.getInstance(messageFragment.getActivity()).getDbUtils().save(friendsBean);
							} catch (DbException e) {
								e.printStackTrace();
							}
						}
					}
				
				}
					
				
		    	content = "消息：" + message[0];
				
				
			} else if (lastMsg.getType() == GotyeMessageType.GotyeMessageTypeImage) {
				Log.v("here", "图片");
				if (lastMsg.getExtraData()!=null) {
					message=StringUtils.byteArrayToString(lastMsg.getExtraData()).split("@");
					Log.v("message", message[0]+ message[1]);
				}else {
					message=new String[1];
				}
				Log.v("getText", session.name);
				 if (!StringUtils.byteArrayToString(lastMsg.getExtraData()).contains(SharePreferenceUtils.getIntance(messageFragment.getActivity()).getHeadImgUserName()))  {
					
					if (message.length==2) {
						if (!StringUtils.isEmpty(message[0])) {
							FriendInfoBean friendsBean=new FriendInfoBean();
							friendsBean.setNickname(message[0]);
							friendsBean.setHeadimage(message[1]);
							friendsBean.setUsername(session.name);
							//friendsBean.setIdo(message[2]);
							try {
								SQLDatebaseUtils.getInstance(messageFragment.getActivity()).getDbUtils().save(friendsBean);
							} catch (DbException e) {
								e.printStackTrace();
							}
						}
						
					}
					if (message.length==3) {
						if (!StringUtils.isEmpty(message[0])) {
							FriendInfoBean friendsBean=new FriendInfoBean();
							friendsBean.setNickname(message[0]);
							friendsBean.setHeadimage(message[1]);
							friendsBean.setUsername(session.name);
							friendsBean.setIdo(message[2]);
							try {
								SQLDatebaseUtils.getInstance(messageFragment.getActivity()).getDbUtils().save(friendsBean);
							} catch (DbException e) {
								e.printStackTrace();
							}
						}
					}
					}
				//Log.v("pic -message", ""+message[0]+"-----"+message[1]);
				content = "图片消息";
			} else if (lastMsg.getType() == GotyeMessageType.GotyeMessageTypeAudio) {
				if (lastMsg.getExtraData()!=null) {
					message=StringUtils.byteArrayToString(lastMsg.getExtraData()).split("@");
					Log.v("message", message[0]+ message[1]);
				}else {
					message=new String[1];
				}
				Log.v("getText", session.name);
				Log.v("message", message.length+"  ");
				if (!StringUtils.byteArrayToString(lastMsg.getExtraData()).contains(SharePreferenceUtils.getIntance(messageFragment.getActivity()).getHeadImgUserName())) {
					if (message.length==2) {
						if (!StringUtils.isEmpty(message[0])) {
						FriendInfoBean friendsBean=new FriendInfoBean();
						friendsBean.setNickname(message[0]);
						friendsBean.setHeadimage(message[1]);
						friendsBean.setUsername(session.name);
						//friendsBean.setIdo(message[2]);
						try {
							SQLDatebaseUtils.getInstance(messageFragment.getActivity()).getDbUtils().save(friendsBean);
						} catch (DbException e) {
							e.printStackTrace();
						}
						}
					}
					if (message.length==3) {
						if (!StringUtils.isEmpty(message[0])) {
							FriendInfoBean friendsBean=new FriendInfoBean();
							friendsBean.setNickname(message[0]);
							friendsBean.setHeadimage(message[1]);
							friendsBean.setUsername(session.name);
							friendsBean.setIdo(message[2]);
							try {
								SQLDatebaseUtils.getInstance(messageFragment.getActivity()).getDbUtils().save(friendsBean);
							} catch (DbException e) {
								e.printStackTrace();
							}
						}
					}
				}
				content = "语音消息";
				
			} else if (lastMsg.getType() == GotyeMessageType.GotyeMessageTypeUserData) {
				content = "自定义消息";
			} else if (lastMsg.getType() == GotyeMessageType.GotyeMessageTypeInviteGroup) {
				content = "邀请消息";
			}

			if (session.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
					GotyeUser user = api.requestUserInfo(session.name, false);
					Log.v("session2", session.name);
					if (user != null) {
						
						if (TextUtils.isEmpty(user.getNickname())) {
							//title = "" + user.name;
							title=SQLDatebaseUtils.getInstance(messageFragment.getActivity()).getNickNameByUsername(user.name);
						} else {
							//title = "" + user.getNickname();
							title=SQLDatebaseUtils.getInstance(messageFragment.getActivity()).getNickNameByUsername(user.name);
							if (title==null||title.equals("")) {
								title="陌生人";
							}
						}
					} else {
						title = "好友：" + session.name;
					}
			
			} else if (session.type == GotyeChatTargetType.GotyeChatTargetTypeRoom) {
				GotyeRoom room = api.requestRoomInfo(session.Id, false);
				if (room != null) {
					if (TextUtils.isEmpty(room.getRoomName())) {
						title = "聊天室：" + room.Id;
					} else {
						title = "聊天室：" + room.getRoomName();
					}
				} else {
					title = "聊天室：" + session.Id;
				}

			} else if (session.type == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
				GotyeGroup group = api.requestGroupInfo(session.Id, false);
				if (group != null) {
					if (TextUtils.isEmpty(group.getGroupName())) {
						title = "群：" + group.Id;
					} else {
						title = "群：" + group.getGroupName();
					}
				} else {
					title = "群：" + session.Id;
				}

			}
			Log.v("title", title+"");
			viewHolder.title.setText(title);
			viewHolder.content.setText(content);
			int count = api.getUnreadMsgcounts(session);
			if (count > 0) {
				viewHolder.count.setVisibility(View.VISIBLE);
				viewHolder.count.setText(String.valueOf(count));
			} else {
				viewHolder.count.setVisibility(View.GONE);
			}
		}
			ImageLoader.getInstance().displayImage(
					SQLDatebaseUtils.getInstance(messageFragment.getActivity()).getHeadImageByUsername(session.name),
					viewHolder.icon,
					InitDisplayImageOptions.getInstance().getHeadOptions(true,
							true, 0),
					InitDisplayImageOptions.getInstance()
					.getImageLoadingListener());	
		return view;
	}

	private void setIcon(ImageView imgView, GotyeChatTarget target) {
		if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
			GotyeUser user = api.requestUserInfo(target.name, true);
			if (user == null) {
				return;
			} else if (user.getIcon() != null) {
				ImageCache.getInstance().setIcom(imgView,
						user.getIcon().getPath(), user.getIcon().getUrl());
			}
		} else if (target.type == GotyeChatTargetType.GotyeChatTargetTypeRoom) {
			GotyeRoom room = api.requestRoomInfo(target.Id, false);
			if (room != null && room.getIcon() != null) {
				ImageCache.getInstance().setIcom(imgView,
						room.getIcon().getPath(), room.getIcon().getUrl());
			}
		} else {
			GotyeGroup group = api.requestGroupInfo(target.Id, false);
			if (group == null) {
				return;
			} else if (group.getIcon() != null) {
				ImageCache.getInstance().setIcom(imgView,
						group.getIcon().getPath(), group.getIcon().getUrl());
			}
		}

	}

	public void setData(List<GotyeChatTarget> sessions) {
		// TODO Auto-generated method stub
		this.sessions = sessions;
		notifyDataSetChanged();
	}
}
